package e3ps.project.historyprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.doc.WTDocument;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.events.KeyedEvent;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.QueryResult;
import wt.inf.container.WTContainerHelper;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleServiceEvent;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ServiceEventListenerAdapter;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlServiceEvent;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.project.CheckoutLink;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSProjectHelper;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.E3PSTask;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.OutputDocumentLink;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProductProjectMoldInfoLink;
import e3ps.project.ProjectChangeStop;
import e3ps.project.ProjectCostInfoLink;
import e3ps.project.ProjectDeptRole;
import e3ps.project.ProjectMaster;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.ReviewProject;
import e3ps.project.ScheduleData;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.WorkProject;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProductHelper;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectIssueHelper;
import e3ps.project.beans.ProjectOutputHelper;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.TaskHelper;
import e3ps.project.moldPart.MoldPartManager;
import e3ps.project.moldPart.beans.MoldPartHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.wfm.service.KETWorkflowHelper;

public class ProjectModuleEventListener extends ServiceEventListenerAdapter {

    public ProjectModuleEventListener(String manager_name) {
	super(manager_name);
    }

    public void notifyVetoableEvent(Object obj) throws Exception {
	if (!(obj instanceof KeyedEvent)) {
	    return;
	}

	KeyedEvent event = (KeyedEvent) obj;

	Object eventObj = ((KeyedEvent) obj).getEventTarget();

	if (eventObj instanceof E3PSProjectMaster) {

	    E3PSProjectMaster project = (E3PSProjectMaster) eventObj;
	    if (event.getEventType().equals(PersistenceManagerEvent.PRE_STORE)) {

		if (existProjectMaster(project.getPjtNo())) {
		    throw new WTException(project.getPjtNo() + " -> 프로젝트 마스터가 이미 존재합니다.");
		}
	    }
	}

	if (eventObj instanceof TemplateProject) {
	    TemplateProject project = (TemplateProject) eventObj;

	    if (event.getEventType().equals(PersistenceManagerEvent.PRE_STORE)) {

		newMaster(project);
	    }

	    if (event.getEventType().equals(PersistenceManagerEvent.PRE_DELETE)) {

		try {
		    if (!project.isWorkingCopy() && project.isCheckOut()) {
			throw new Exception("Checkout 된 객체 입니다.");
		    }

		    boolean isWorkingCopy = project.isWorkingCopy();

		    if (isWorkingCopy) {
			QueryResult rs = PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);
			TemplateProject original = null;
			if (rs.hasMoreElements()) {
			    original = (TemplateProject) rs.nextElement();
			}

			if (original == null) {
			    throw new Exception("원본 객체가 존재하지 않습니다.");
			}
			original.setCheckOut(false);
			original.setCheckOutState("c/i");
			PersistenceHelper.manager.save(original);

			if (project instanceof ProductProject) {
			    QueryResult rs3 = PersistenceHelper.manager.navigate(project, ProductProjectMoldInfoLink.MOLD_INFO_ROLE, ProductProjectMoldInfoLink.class);
			    while (rs3.hasMoreElements()) {
				MoldItemInfo moldItemInfo = (MoldItemInfo) rs3.nextElement();
				moldItemInfo.setProject((ProductProject) original);
				PersistenceHelper.manager.save(moldItemInfo);
			    }

			    QueryResult rs5 = PersistenceHelper.manager.navigate(project, ProjectCostInfoLink.COST_INFO_ROLE, ProjectCostInfoLink.class);
			    while (rs5.hasMoreElements()) {
				CostInfo costInfo = (CostInfo) rs5.nextElement();
				costInfo.setProject((ProductProject) original);
				costInfo = (CostInfo) PersistenceHelper.manager.save(costInfo);
			    }
			}
		    }

		    deleteScheduleData(project);
		    deleteChildTask(project);
		    deleteDeptRole(project);
		    deleteProductInfo(project);
		    if (project instanceof E3PSProject)
			ProductHelper.syncProjectCostIF((E3PSProject) project);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		    throw new WTException(e);
		}
	    }

	    if (event.getEventType().equals(PersistenceManagerEvent.POST_DELETE)) {
		try {
		    boolean isLastest = project.isLastest() && !project.isCheckOut();
		    if (isLastest) {
			if (project instanceof MoldProject) {
			    if (project.getState().toString().equals("PMOINWORK")) {

			    } else {
				throw new WTException("E3PSProjectMaster(금형) 은 [등록중] 상태 외 에는 삭제될 수 없습니다.");
			    }
			} else {

			    String pjtClazName = project.getClass().getSimpleName();

			    if (!(StringUtils.contains(pjtClazName, "Template")) && !(project.getState().toString().equals("PMOINWORK") || project.getState().toString().equals("STOPINWORK"))) {
				throw new WTException("E3PSProjectMaster는 [등록중] , [중지검토] 인 상태 외에는 삭제될 수 없습니다.");
			    }
			}

			deleteAllHistoryProject(project);
		    }
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		    throw new WTException(e);
		}
	    }

	    if (event.getEventType().equals(LifeCycleServiceEvent.SET_STATE)) {
		boolean isCheckReview = false;

		if (project.getState().toString().equals("STOPINWORK")) {
		    return;
		}

		if (project instanceof ProductProject && project.getState().toString().equals("PROGRESS")) {
		    try {
			ProjectTaskHelper.manager.mainSchedulUpdate(project, "", true);
		    } catch (Exception e) {
			System.out.println(project.getPjtNo() + " 의 주요일정 동기화 중 오류가 발생했습니다.");
			e.printStackTrace();
		    }
		}

		if (project instanceof ProductProject && project.getState().toString().equals("REJECTED")) {
		    ProjectChangeStop ps;
		    try {
			ps = ProjectHelper.getStopProject((ProductProject) project);
			if (ps != null && "중지검토".equals(ps.getChangeType())) {
			    ps.setChangeType("중지");
			    PersistenceHelper.manager.save(ps);

			    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC", WTContainerHelper.service.getExchangeRef());
			    project = (ProductProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
			    project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project, wt.lifecycle.State.toState("STOPINWORK"), true);

			}
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}

		if (!(project.getState().toString().equals("APPROVED") || project.getState().toString().equals("COMPLETED") || project.getState().toString().equals("WITHDRAWN") || project.getState()
		        .toString().equals("STOPED"))) {
		    return;
		}
		if (project.getState().toString().equals("APPROVED") || project.getState().toString().equals("WITHDRAWN") || project.getState().toString().equals("STOPED")) {
		    if (project.isWorkingCopy()) {
			try {
			    project = HistoryHelper.checkIn(project);
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			    throw new WTException(e);
			}
		    }
		    if (project instanceof E3PSProject) {
			try {
			    ProjectHelper.manager.approveMail((E3PSProject) project);
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
		    }
		    if (project.getState().toString().equals("APPROVED")) {
			project = (TemplateProject) LifeCycleHelper.service.setLifeCycleState(project, wt.lifecycle.State.toState("PROGRESS"), true);
		    }

		    // ProjectHelper.manager.projectSendMail(project, "setState");
		}
		Kogger.debug(getClass(), "LifeCycleServiceEvent.SET_STATE :   " + project.getState().toString());
		if (project instanceof ReviewProject) {
		    isCheckReview = true;
		}

		// 프로젝트의 상태가 완료이며 리뷰프로젝트가 아닐경우 성과관리 이벤트 발생..
		/*
	         * if (project.getState().toString().equals("COMPLETED") && !isCheckReview) { ProductProject productProject = null;
	         * MoldProject moldProject = null; MoldItemInfo moldInfo = null; if (project instanceof ProductProject) { // 제품 프로젝트 일경우
	         * Kogger.debug(getClass(), "제품프로젝트 성과관리 이벤트 발생..."); productProject = (ProductProject) project; if
	         * (productProject.getProcess().getName().equals("Pilot")) { // Pilot 프로젝트일 경우 if
	         * (productProject.getDevelopentType().getName().equals("전략개발") ||
	         * productProject.getDevelopentType().getName().equals("수주개발")) { try { if (true) { String msg =
	         * PerformanceHelper.manager.eventPerformance(project); Kogger.debug(getClass(), " 성과관리 완료 확인 메세지 :" + msg); String msgCheck
	         * = msg.substring(0, 1); if ("F".equals(msgCheck)) { throw new Exception(" 성과관리 이벤트 처리중 오류 발생 : " + msg); } } } catch
	         * (Exception e) { Kogger.error(getClass(), e); throw new WTException(e); } } } Kogger.debug(getClass(),
	         * "제품프로젝트 성과관리 이벤트 끝..."); } else if (project instanceof MoldProject) { // 금형 프로젝트 일경우 Kogger.debug(getClass(),
	         * "금형프로젝트 성과관리 이벤트 발생..."); moldProject = (MoldProject) project; moldInfo = moldProject.getMoldInfo(); productProject =
	         * moldInfo.getProject(); if (productProject.getProcess().getName().equals("Pilot")) { // Pilot 프로젝트 일경우 if
	         * (productProject.getDevelopentType().getName().equals("전략개발") ||
	         * productProject.getDevelopentType().getName().equals("수주개발") ||
	         * productProject.getDevelopentType().getName().equals("추가금형")) { // 전략개발 프로젝트이거나 수주개발 프로젝트일 경우 try { if (true) { String msg
	         * = PerformanceHelper.manager.eventPerformance(project); Kogger.debug(getClass(), " 성과관리 완료 확인 메세지 :" + msg); String
	         * msgCheck = msg.substring(0, 1); if ("F".equals(msgCheck)) { throw new Exception(" 성과관리 이벤트 처리중 오류 발생 : " + msg); } } }
	         * catch (Exception e) { Kogger.error(getClass(), e); throw new WTException(e); } } } Kogger.debug(getClass(),
	         * "금형프로젝트 성과관리 이벤트 끝..."); } }
	         */

		/*
	         * if(project.getState().toString().equals("COMPLETED") && !isCheckReview){ try { if( true ) { String msg =
	         * PerformanceHelper.manager.eventPerformance(project); Kogger.debug(getClass(), " 성과관리 완료 확인 메세지 :" + msg); String msgCheck
	         * = msg.substring(0,1); if("F".equals(msgCheck)){ throw new Exception(" 성과관리 이벤트 처리중 오류 발생 : " +msg); } }
	         * 
	         * }catch (Exception e) { Kogger.error(getClass(), e); throw new WTException(e); } }
	         */
		if (project.getState().toString().equals("WITHDRAWN") || project.getState().toString().equals("STOPED")) {
		    try {
			// ProjectHelper.manager.projectSendMail(project, "setState");
			ProjectHelper.manager.sendMailStopCancelProject((E3PSProject) project);
			// 관련 금형 프로젝트 취소 진행 (상태가 진행중 금형프로젝트만 취소 시킨다)
			if (project instanceof ProductProject) {
			    ProjectHelper.manager.setCancelRefMoldProjectByProduct((E3PSProject) project);
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		    }
		}

	    }
	}

	if (eventObj instanceof ProjectMemberLink) {
	    if (event.getEventType().equals(PersistenceManagerEvent.POST_STORE) || event.getEventType().equals(PersistenceManagerEvent.POST_MODIFY)) {

		ProjectMemberLink link = (ProjectMemberLink) eventObj;
		try {
		    ProjectUserHelper.settingTaskMaster(link);
		} catch (Exception e) {
		    throw new WTException(e);
		}

	    }
	}

	if (eventObj instanceof TaskMemberLink) {
	    /*
	     * TaskMemberLink link = (TaskMemberLink)eventObj; if(event.getEventType().equals(PersistenceManagerEvent.POST_DELETE)){
	     * PersistenceManagerEvent aa = null;
	     * 
	     * ProjectMemberLink plink = link.getMember(); if(plink.getPjtMemberType() == ProjectUserHelper.PM){ return; } WTUser user =
	     * link.getMember().getMember(); TemplateProject project = link.getTask().getProject(); try {
	     * if(!TaskUserHelper.manager.isUserTask((E3PSProject)project, user)){ PersistenceHelper.manager.delete(plink); } } catch
	     * (Exception e) { Kogger.error(getClass(), e); } }
	     */

	}

	if (eventObj instanceof E3PSTask) {
	    // DrProgress 이벤트
	    if (event.getEventType().equals(PersistenceManagerEvent.POST_MODIFY)) {
		E3PSTask task = (E3PSTask) eventObj;
		String taskName = task.getTaskName();
		E3PSProject project = (E3PSProject) task.getProject();
		// if ("DR".equals(task.getTaskType())) { //불필요로직(기 원가시스템 사용안함)
		//
		// if (task.getTaskCompletion() == 100) {
		// try {
		// ProductHelper.syncProjectDRCostIF(project, taskName);
		// } catch (Exception e) {
		// Kogger.error(getClass(), e);
		// }
		// }
		// }
		// DR1 태스크가 완료되면 전자사업부만 성과관리 목표 등록 공지 필요
		if ("DR1".equals(task.getTaskName())) {
		    try {
			boolean isElec = CommonUtil.isMember("전자사업부");
			if (isElec) {

			    Hashtable ht = new Hashtable();
			    E3PSProject ep = (E3PSProject) task.getProject();
			    E3PSProjectData data = new E3PSProjectData(ep);
			    String pmName = data.pjtPmName;
			    WTUser wtuser = data.pjtPm;
			    String pilotCheck = "";
			    if (ep.getProcess() == null) {
				pilotCheck = project.getProcess().getName();
			    }
			    if (wtuser != null && "Pilot".equals(pilotCheck)) {
				ht.put(pmName, wtuser);
				// 다국어 처리
				KETMessageService messageService = new KETMessageService(KETMessageService.getUserLocale(wtuser));
				/* 프로젝트의 성과관리 목표를 등록하시길 바랍니다. */
				String text = messageService.getString("e3ps.message.mail.message_mail_msg",
				        "msg.mail.ProjectModuleEventListener.Please_register_Performance_management_objectives_of_the_project");

				/*
		                 * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
		                 */
				// ProjectHelper.sendProjectInfoMail(project, ht, text, "ProjectMailNoti.html");
				ProjectHelper.sendProjectInfoMail(project, ht, text, "ProjectMailNoti");
				// ///////////////////////////////////////////////////////////////////////////
			    }
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }

		}

		if (!"신뢰성평가".equals(task.getTaskType()) && "KQISTRUST".equals(task.getTaskCode())) {
		    try {
			deleteTaskOutputByKqisTrust(task);
			task.setTaskCode("");
			PersistenceHelper.manager.save(task);
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}

	    }

	    if (event.getEventType().equals(PersistenceManagerEvent.PRE_DELETE)) {

		TemplateTask task = (TemplateTask) eventObj;
		try {
		    deleteScheduleData(task);
		    deleteTaskOutput(task);
		    if (task instanceof E3PSTask) {
			deleteTaskIssue(task);
		    }
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		    throw new WTException(e);
		}

	    }

	    if (event.getEventType().equals(PersistenceManagerEvent.PRE_DELETE)) {

	    }
	}

	if (eventObj instanceof ProjectOutput) {

	    if (event.getEventType().equals(PersistenceManagerEvent.POST_STORE)) {
		// Kogger.debug(getClass(), "ProjectOutput  = " + "POST_STORE");
	    }
	    if (event.getEventType().equals(PersistenceManagerEvent.PRE_DELETE)) {

	    }

	}

	if (eventObj instanceof ProjectDeptRole) {
	    if (event.getEventType().equals(PersistenceManagerEvent.POST_MODIFY) || event.getEventType().equals(PersistenceManagerEvent.POST_STORE)) {
		/*
	         * ProjectDeptRole deptRole = (ProjectDeptRole)eventObj;
	         * 
	         * QueryResult rs = TaskHelper.manager.getDeptRole(deptRole);
	         * 
	         * 
	         * 
	         * if(rs == null){
	         * 
	         * return; }
	         * 
	         * 
	         * while(rs.hasMoreElements()){ TemplateTask task = (TemplateTask)rs.nextElement(); try {
	         * task.setDepartment(deptRole.getDepartment()); } catch (WTPropertyVetoException e) { Kogger.error(getClass(), e); }
	         * PersistenceHelper.manager.save(task); }
	         */
	    }
	}

	if (eventObj instanceof WTDocument) {
	    if (event.getEventType().equals(VersionControlServiceEvent.NEW_VERSION)) {

		VersionControlServiceEvent versionE = (VersionControlServiceEvent) event;

		WTDocument doc = (WTDocument) versionE.getIteration();

		if (doc.getIterationInfo().getPredecessor() != null) {

		    try {
			// QueryResult rs = ProjectOutputHelper.getOutputDocumentLink((WTDocumentMaster)doc.getMaster());
			// Kogger.debug(getClass(), "rs.size==== " + rs.size());
			// while(rs.hasMoreElements()){
			// Object o[] = (Object[])rs.nextElement();
			// OutputDocumentLink outlink = (OutputDocumentLink)o[0];
			//
			// outlink.setBranchIdentifier(doc.getBranchIdentifier());
			// outlink = (OutputDocumentLink)PersistenceHelper.manager.save(outlink);
			//
			// Kogger.debug(getClass(), "save............update... = " + outlink.getDocument().getName() + "= " +
			// outlink.getOutput().getCompletion());
			// }
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			throw new WTException(e);
		    }
		}

	    }

	    if (event.getEventType().equals(VersionControlServiceEvent.POST_MERGE)) {
		VersionControlServiceEvent versionE = (VersionControlServiceEvent) event;

		// Kogger.debug(getClass(), "POST_MERGE versionE.getAuxEventData() = " + versionE.getAuxEventData());

		// try{
		// Kogger.debug(getClass(), versionE.getMergeSourceIteration());
		// }catch(Exception ex){
		// Kogger.debug(getClass(), "POST_MERGE..POST_MERGE.. ");
		// }

	    }

	}
	// if(eventObj instanceof AsmApproval){
	// if(event.getEventType().equals(LifeCycleServiceEvent.SET_STATE)){
	// try {
	// AsmApproval asm = (AsmApproval)eventObj;
	// boolean isEvent = false;
	// State newState = null;
	// // Kogger.debug(getClass(), "AsmApproval State event=======================> " + asm.getState());
	//
	// if(asm.getState().toString().equals("COMPLETED") || asm.getState().toString().equals("Completed")){
	// newState = State.toState("COMPLETED");
	// isEvent = true;
	// }
	// if(asm.getState().toString().equals("ERPSENDERROR")){
	// newState = State.toState("ERPSENDERROR");
	// isEvent = true;
	// }
	//
	// if(asm.getState().toString().equals("APPROVING")){
	// newState = State.toState("APPROVING");
	// isEvent = true;
	// }
	//
	// if(asm.getState().toString().equals("RETURN")){
	// newState = State.toState("RETURN");
	// isEvent = true;
	// }
	// // Kogger.debug(getClass(), "isevent ==> " + isEvent);
	// if(isEvent){
	// WFItemHelper.manager.changeASMState((WTObject)eventObj, asm.getState().toString());
	// }
	//
	//
	// }catch (Exception e){
	// Kogger.error(getClass(), e);
	// throw new WTException(e);
	// }
	// }
	// }

	if (eventObj instanceof WTDocument || eventObj instanceof EPMDocument) {
	    if (event.getEventType().equals(LifeCycleServiceEvent.SET_STATE)) {
		try {

		    boolean isAutoReceiver = false;
		    String receiver = "jbhong";

		    if (eventObj instanceof WTDocument) {

			WTDocument doc = (WTDocument) eventObj;

			if (eventObj instanceof KETProjectDocument) {
			    if (doc.getState().toString().equals("APPROVED") || doc.getState().toString().equals("UNDERREVIEW") || doc.getState().toString().equals("REJECTED")) {
				KETProjectDocument docu = (KETProjectDocument) eventObj;
				KETDocumentCategory docCate = null;

				QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);

				if (r.hasMoreElements()) {
				    docCate = (KETDocumentCategory) r.nextElement();
				    if ("프로젝트완료보고(제품)".equals(docCate.getCategoryName())) {
					isAutoReceiver = true;
				    }
				}
			    }
			}

			if (!isAutoReceiver && !doc.getState().toString().equals("APPROVED")) {
			    return;
			}

			if (doc.getState().toString().equals("APPROVED")) {
			    updateOutput(eventObj);
			}
		    } else if (eventObj instanceof EPMDocument) {
			EPMDocument epm = (EPMDocument) eventObj;
			if (!epm.getState().toString().equals("APPROVED")) {
			    return;
			}
			updateOutput(eventObj);
		    }

		    if ((eventObj instanceof KETProjectDocument) && isAutoReceiver) {// 프로젝트완료보고(제품) 일 경우

			String state = ((KETProjectDocument) eventObj).getState().toString();

			if (isAutoReceiver) {

			    KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster((Persistable) eventObj);
			    Vector<KETWfmApprovalHistory> historyVec = WorkflowSearchHelper.manager.getApprovalHistory(approvalMaster);

			    for (int i = 0; i < historyVec.size(); i++) {
				KETWfmApprovalHistory history = (KETWfmApprovalHistory) historyVec.get(i);
				if (receiver.equals(history.getOwner().getName())) {// 이미 수신처에 지정되어 있다면 제외한다
				    isAutoReceiver = false;
				    break;
				}
			    }
			}
			if (isAutoReceiver && "APPROVED".equals(state)) {// 승인완료 이벤트 발생시 (사장님 최종결재) 홍종범 이사님 자동으로 수신처에 지정 2020.11.09
			    HashMap<String, Object> reqMap = new HashMap<String, Object>();
			    String[] pboOids = { CommonUtil.getOIDString((Persistable) eventObj) };
			    reqMap.put("pboOids", pboOids);
			    reqMap.put("receiver", receiver);
			    reqMap.put("doNotCreate", true);// 불필요한 수동지정 이력을 남기지 않는다

			    KETWorkflowHelper.service.doRequestDistribute(reqMap);
			} else if (isAutoReceiver && ("UNDERREVIEW".equals(state) || "REJECTED".equals(state))) {// 사장님 반려 또는 최초 결재요청시 홍종범
				                                                                                 // 이사님 관련 메일 발송 2020.11.25

			    if ("REJECTED".equals(state)) {
				WTPrincipalReference appUser = WorkflowSearchHelper.manager.getApprovalRejectUser((Persistable) eventObj);
				isAutoReceiver = "ketcolwj".equals(appUser.getName());
			    }

			    if (isAutoReceiver) {
				WTUser wtUserFrom = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
				ArrayList<WTUser> listToUser = new ArrayList<WTUser>();
				WTUser toUser = KETObjectUtil.getUser(receiver);
				listToUser.add(toUser);

				String mailNumber = "";

				if ("UNDERREVIEW".equals(state)) {
				    mailNumber = "08003";
				} else if ("REJECTED".equals(state)) {
				    mailNumber = "08007";
				}

				KETMailHelper.service.sendFormMail(mailNumber, "ApprovalNoticeMail.html", eventObj, wtUserFrom, listToUser);
			    }

			}

		    }
		} catch (Exception e) {

		    Kogger.error(getClass(), e);
		    throw new WTException(e);
		}
	    }
	}

	if (eventObj instanceof MoldItemInfo) {

	    if (event.getEventType().equals(PersistenceManagerEvent.POST_MODIFY) || event.getEventType().equals(PersistenceManagerEvent.POST_STORE)) {

		MoldItemInfo itemInfo = (MoldItemInfo) eventObj;

		try {
		    MoldProject mProject = MoldProjectHelper.getMoldProject(itemInfo);

		    if (mProject != null) {
			ProjectMaster master = mProject.getMaster();
			master.setPjtName(itemInfo.getPartName());
			master.setPjtNo(itemInfo.getDieNo());
			PersistenceHelper.manager.save(master);

			Vector vector = MoldPartHelper.getMoldPartManager(mProject);
			for (int i = 0; i < vector.size(); i++) {
			    MoldPartManager manager = (MoldPartManager) vector.get(i);
			    manager.setDieNo(itemInfo.getDieNo());
			    PersistenceHelper.manager.save(manager);
			}

		    }
		} catch (Exception e) {

		    Kogger.error(getClass(), e);
		}

	    }
	}

    }

    private void updateOutput(Object eventObj) throws Exception {
	// Kogger.debug(getClass(), "doc ok=================================================== ");
	QueryResult rs = ProjectOutputHelper.getProjectOutput((RevisionControlled) eventObj, true);
	Kogger.debug(getClass(), "ProjectModuleEvent.......========================================== " + rs.size());
	while (rs.hasMoreElements()) {

	    Kogger.debug(getClass(), "ProjectModuleEvent--------------------->1 @@@@@@@@@@@@@@@");
	    OutputDocumentLink olink = (OutputDocumentLink) rs.nextElement();
	    Kogger.debug(getClass(), "ProjectModuleEvent--------------------->2 @@@@@@@@@@@@@@@");
	    ProjectOutput output = olink.getOutput();
	    Kogger.debug(getClass(), "ProjectModuleEvent--------------------->3 @@@@@@@@@@@@@@@");
	    if (!output.isIsPrimary()) {
		continue;
	    }
	    Kogger.debug(getClass(), "ProjectModuleEvent--------------------->4 @@@@@@@@@@@@@@@");
	    E3PSTask task = (E3PSTask) output.getTask();
	    Kogger.debug(getClass(), "ProjectModuleEvent--------------------->5 @@@@@@@@@@@@@@@");
	    // taskState 완료 (5) 진행중 (0)
	    if (output.getCompletion() >= 0) {
		Kogger.debug(getClass(), "ProjectModuleEvent--------------------->6 @@@@@@@@@@@@@@@");
		output.setCompletion(100);
		// 실제 종료일 등록
		Kogger.debug(getClass(), "ProjectModuleEvent--------------------->7 @@@@@@@@@@@@@@@");
		ProjectTaskHelper.manager.updateCompletion(output);
		Kogger.debug(getClass(), "ProjectModuleEvent--------------------->8 @@@@@@@@@@@@@@@");
	    }
	}
    }

    private void deleteTaskIssue(TemplateTask task) throws Exception {

	// QueryResult rs = ProjectIssueHelper.manager.getTaskIssue((JELTask)task);
	// while(rs.hasMoreElements()){
	// //ProjectIssue
	// Object[] issueObj = (Object[]) rs.nextElement();
	// ProjectIssue issue = (ProjectIssue) issueObj[0];
	//
	// Vector issueVec = ProjectIssueHelper.manager.getIssueAnswer(issue);
	// for(int i = 0; i < issueVec.size(); i++) {
	// //ProjectIssueAnswer
	// ProjectIssueAnswerData data = (ProjectIssueAnswerData) issueVec.get(i);
	// ProjectIssueAnswer answer = data.answer;
	//
	// Vector linkVec = ProjectIssueHelper.manager.getIssueLink(issue, answer);
	// for(int j = 0; j < linkVec.size(); j++) {
	// //QuestionAnswerLink
	// QuestionAnswerLink link = (QuestionAnswerLink) linkVec.get(j);
	//
	// PersistenceHelper.manager.delete(link);
	// }
	//
	// PersistenceHelper.manager.delete(answer);
	// }
	// PersistenceHelper.manager.delete(issue);
	// }
    }

    private boolean existProjectMaster(String pjtNo) throws Exception {
	E3PSProjectMaster master = null;
	QuerySpec spec = new QuerySpec(E3PSProjectMaster.class);
	spec.appendWhere(new SearchCondition(E3PSProjectMaster.class, "pjtNo", "=", pjtNo), new int[] { 0 });
	QueryResult rs = PersistenceHelper.manager.find(spec);
	while (rs.hasMoreElements()) {
	    master = (E3PSProjectMaster) rs.nextElement();
	}

	return master != null;
    }

    private void deleteAllHistoryProject(TemplateProject project) throws Exception {
	long masterId = project.getMaster().getPersistInfo().getObjectIdentifier().getId();

	// Kogger.debug(getClass(), "deleteAllHistoryProject!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

	QuerySpec spec = new QuerySpec(TemplateProject.class);
	spec.appendWhere(new SearchCondition(TemplateProject.class, "masterReference.key.id", "=", masterId), new int[] { 0 });
	spec.appendAnd();
	spec.appendWhere(new SearchCondition(TemplateProject.class, TemplateProject.LASTEST, SearchCondition.IS_FALSE, false), new int[] { 0 });
	QueryResult rs = PersistenceHelper.manager.find(spec);

	while (rs.hasMoreElements()) {
	    TemplateProject oldProject = (TemplateProject) rs.nextElement();

	    // Kogger.debug(getClass(), "ProjectIssue Delete!!!");
	    ProjectIssueHelper.manager.deleteProjectIssue(CommonUtil.getOIDString(oldProject));

	    // Kogger.debug(getClass(), "OLD Project Delete!!!");
	    PersistenceHelper.manager.delete(oldProject);

	    deleteProductInfo(oldProject);
	}
	// if(project instanceof JELProject){
	// e3ps.project.beans.ProjectERPConMgr.getInstance().getERPTableUpdate(project.getPjtNo(),"0");
	// }
	if (project instanceof ProductProject) {
	    E3PSProjectHelper.service.deleteLinkProject((E3PSProject) project);
	}

	deleteProductInfo(project);
	PersistenceHelper.manager.delete(project.getMaster());
	ProductHelper.deleteProjectCostIF(project.getPjtNo());
	// Kogger.debug(getClass(), "ProjectMaster Delete!!!!");

    }

    // private void deleteAllHistoryProjectProecess(ProcessControl process)throws Exception{
    // long masterId = process.getMaster().getPersistInfo().getObjectIdentifier().getId();
    //
    //
    // QuerySpec spec = new QuerySpec(ProcessControl.class);
    // spec.appendWhere(new SearchCondition(ProcessControl.class, "masterReference.key.id", "=", masterId), new int[]{0});
    // spec.appendAnd();
    // spec.appendWhere(new SearchCondition(ProcessControl.class, ProcessControl.LASTEST, SearchCondition.IS_FALSE, false), new int[]{0});
    // QueryResult rs = PersistenceHelper.manager.find(spec);
    //
    // while(rs.hasMoreElements()){
    // ProcessControl oldProcess = (ProcessControl)rs.nextElement();
    // PersistenceHelper.manager.delete(oldProcess);
    // }
    // PersistenceHelper.manager.delete(process.getMaster());
    //
    //
    // }

    private void deleteScheduleData(TemplateProject project) throws Exception {
	ScheduleData scheduleData = null;
	try {
	    scheduleData = (ScheduleData) project.getPjtSchedule().getObject();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	if (scheduleData != null) {
	    PersistenceHelper.manager.delete(scheduleData);
	}
    }

    private void deleteTaskOutput(TemplateTask task) throws Exception {
	QueryResult rs = ProjectOutputHelper.manager.getTaskOutput(task);
	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    ProjectOutput output = (ProjectOutput) o[0];
	    PersistenceHelper.manager.delete(output);
	}
    }

    private void deleteTaskOutputByKqisTrust(TemplateTask task) throws Exception {
	List<ProjectOutput> list = ProjectOutputHelper.manager.getTaskOutputByKqisTrust(task);
	for (ProjectOutput output : list) {
	    PersistenceHelper.manager.delete(output);
	}
    }

    private void deleteScheduleData(TemplateTask task) throws Exception {
	ScheduleData scheduleData = null;
	try {
	    scheduleData = (ScheduleData) task.getTaskSchedule().getObject();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	if (scheduleData != null) {
	    PersistenceHelper.manager.delete(scheduleData);
	}

    }

    private void deleteChildTask(TemplateProject project) throws Exception {
	QuerySpec spec = new QuerySpec(TemplateTask.class);
	spec.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=", PersistenceHelper.getObjectIdentifier(project).getId()), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	while (rs.hasMoreElements()) {
	    TemplateTask task = (TemplateTask) rs.nextElement();
	    PersistenceHelper.manager.delete(task);
	}

    }

    private void deleteDeptRole(TemplateProject project) throws Exception {
	QueryResult rs = TaskHelper.manager.getDeptRoleFromProject(project);
	while (rs.hasMoreElements()) {

	    ProjectDeptRole projectDeptRole = (ProjectDeptRole) rs.nextElement();
	    PersistenceHelper.manager.delete(projectDeptRole);
	}

    }

    private void deleteProductInfo(TemplateProject project) throws Exception {
	QuerySpec qs = new QuerySpec();
	int idxpi = qs.appendClassList(ProductInfo.class, true);

	SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(project));
	qs.appendWhere(cs, new int[] { idxpi });

	QueryResult qrpi = PersistenceHelper.manager.find(qs);
	while (qrpi.hasMoreElements()) {
	    Object o[] = (Object[]) qrpi.nextElement();
	    ProductInfo pi = (ProductInfo) o[0];
	    PersistenceHelper.manager.delete(pi);
	}
    }

    private static ProductProject getFromMaster(E3PSProjectMaster master) throws Exception {

	QuerySpec spec = new QuerySpec(ProductProject.class);
	long masterId = master.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(ProductProject.class, MoldProject.MASTER_REFERENCE + ".key.id", "=", masterId), new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")), new int[] { 0 });

	if (spec.getConditionCount() > 0) {
	    spec.appendAnd();
	}
	spec.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);
	ProductProject project = null;
	if (rs.hasMoreElements()) {
	    project = (ProductProject) rs.nextElement();
	}
	return project;

    }

    private static QueryResult getRefMoldProject(E3PSProjectMaster master) throws Exception {

	ProductProject project = getFromMaster(master);
	QueryResult rs = new QueryResult();
	if (project != null) {
	    rs = MoldProjectHelper.getRelationMoldProject(project);
	}
	return rs;

    }

    // newMasterProcess 공정 마스터
    // private void newMasterProcess(ProcessControl process)throws WTException{
    // ProcessControlMaster master = process.getMaster();
    // if(PersistenceHelper.isPersistent(master)){
    // return;
    // }
    //
    //
    // try {
    // process.setCheckOut(false);
    // process.setLastest(true);
    // process.setPcHistory(0);
    //
    //
    //
    // } catch (WTPropertyVetoException e1) {
    // Kogger.error(getClass(), e1);
    // }
    //
    //
    // master = (ProcessControlMaster)PersistenceHelper.manager.save(master);
    // // Kogger.debug(getClass(), "new Master............");
    // try {
    //
    // process.setMaster(master);
    // } catch (WTPropertyVetoException e) {
    // throw new WTException(e);
    // }
    // }

    /**
     * 함수명 : newMaster 수정내용 : [PLM System 1차개선] Project 이력 Iteration 설정 추가
     * 
     * @param project
     * @throws WTException
     *             수정자 : BoLee 수정일자 : 2013. 7. 4.
     */
    private void newMaster(TemplateProject project) throws WTException {
	ProjectMaster master = project.getMaster();
	if (PersistenceHelper.isPersistent(master)) {
	    return;
	}

	// OLD - Start
	// if(project instanceof JELProject){
	// e3ps.project.beans.ProjectERPConMgr.getInstance().getERPTableUpdate(project.getPjtNo(), "1");
	// }
	// OLD - End

	try {
	    project.setCheckOut(false);
	    project.setLastest(true);
	    project.setHistoryNote("");
	    project.setHistoryNoteType(0);
	    project.setPjtHistory(0);
	    // [START] [PLM System 1차개선] Project 이력 Iteration 설정 추가, 2013-07-04, BoLee
	    project.setPjtIteration(0);
	    // [END] [PLM System 1차개선] Project 이력 Iteration 설정 추가, 2013-07-04, BoLee

	    if (project instanceof ReviewProject) {
		project.setPjtTypeName("검토");
	    } else if (project instanceof ProductProject) {
		project.setPjtTypeName("제품");
	    } else if (project instanceof MoldProject) {
		project.setPjtTypeName("금형");
	    } else if (project instanceof WorkProject) {
		project.setPjtTypeName("업무");
	    }

	} catch (WTPropertyVetoException e1) {
	    Kogger.error(getClass(), e1);
	}

	master = (ProjectMaster) PersistenceHelper.manager.save(master);

	try {
	    project.setMaster(master);
	} catch (WTPropertyVetoException e) {
	    throw new WTException(e);
	}
    }

    public static void main(String args[]) throws Exception {

	/*
	 * QuerySpec spec = new QuerySpec(E3PSTask.class); QueryResult rs = PersistenceHelper.manager.find(spec); //20443968
	 * 
	 * PersistenceHelper.manager.delete(CommonUtil.getObject("e3ps.project.JELTask:20442425"));
	 * 
	 * while(rs.hasMoreElements()){ E3PSTask task = (E3PSTask)rs.nextElement(); // Kogger.debug(getClass(), "task = " +
	 * CommonUtil.getOIDString(task)); //Kogger.debug(getClass(), task.getTaskSchedule().getObject());
	 * 
	 * PersistenceHelper.manager.delete(task); }
	 */
	// Properties prop = System.getProperties();
	//
	//
	//
	// Enumeration e = prop.keys();
	//
	//
	//
	// while(e.hasMoreElements()){
	// Kogger.debug(getClass(), e.nextElement());
	// }

	// Kogger.debug(getClass(), "java.library.path : " + prop.get("java.library.path"));

	// Runtime rt = Runtime.getRuntime();
	// Process p = rt.exec("cmd");
	// OutputStream out = p.getOutputStream();
	// PrintWriter writer = new PrintWriter(out);
	//
	// InputStream in = p.getInputStream();
	// BufferedReader br = new BufferedReader(new InputStreamReader(in));
	//
	// String s = null;
	// while((s = br.readLine())!= null){
	// Kogger.debug(getClass(), s);
	// }

	// TemplateProject project = (TemplateProject)CommonUtil.getObject("e3ps.project.TemplateProject:20439616");

	// Kogger.debug(getClass(), project.getOldHistory());

	RemoteMethodServer server = RemoteMethodServer.getDefault();
	server.setUserName("wcadmin");
	server.setPassword("wcadmin");
	ProductProject proudct = (ProductProject) CommonUtil.getObject("e3ps.project.ProductProject:95615");
	proudct.getMaster();

	QueryResult rs = getRefMoldProject((E3PSProjectMaster) proudct.getMaster());

	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    MoldProject p = (MoldProject) o[0];
	    Kogger.debug(ProjectModuleEventListener.class, "kkk = " + p.getPjtNo());
	}

    }

}
