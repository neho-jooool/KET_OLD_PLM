package ext.ket.project.task.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import wt.change2.WTChangeOrder2;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.content.FormatContentHolder;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.method.MethodContext;
import wt.org.WTUser;
import wt.pdmlink.PDMLinkProduct;
import wt.pds.StatementSpec;
import wt.pom.DBProperties;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.services.StandardManager;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.content.ContentInfo;
import e3ps.common.content.ContentUtil;
import e3ps.common.db.DBCPManager;
import e3ps.common.message.KETMessageService;
import e3ps.common.obj.ObjectData;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.beans.DMSUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETStandardTemplate;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.PeopleData;
import e3ps.project.AssessProjectOutputLink;
import e3ps.project.AssessTemplateTaskLink;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.KETDqmRaiseOutputLink;
import e3ps.project.KETMoldChangeOrderOutputLink;
import e3ps.project.KETProdChangeOrderOutputLink;
import e3ps.project.KETTryMoldOutputLink;
import e3ps.project.KETTryPressOutputLink;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.OutputDocumentLink;
import e3ps.project.ProductProject;
import e3ps.project.ProjectOutput;
import e3ps.project.ReviewProject;
import e3ps.project.TaskAssessResult;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TaskMemberLink;
import e3ps.project.TaskTrustResult;
import e3ps.project.TemplateProject;
import e3ps.project.TrustProjectOutputLink;
import e3ps.project.TrustTemplateTaskLink;
import e3ps.project.WorkProject;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.ProjectOutputData;
import e3ps.project.beans.ProjectOutputHelper;
import e3ps.project.beans.ProjectStateFlag;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.TaskHelper;
import e3ps.project.beans.TaskUserHelper;
import ext.ket.dqm.entity.KETDqmClose;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.project.gate.entity.AssSheetGateChkSheetLink;
import ext.ket.project.gate.entity.AssessResultDqmHeaderLink;
import ext.ket.project.gate.entity.AssessResultGateCheckLink;
import ext.ket.project.gate.entity.AssessResultOutputLink;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.GateAssRsltPjtLink;
import ext.ket.project.gate.entity.GateAssRsltTaskLink;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.gate.entity.GateAttribute;
import ext.ket.project.gate.entity.GateCheckSheet;
import ext.ket.project.gate.entity.GateCheckSheetDTO;
import ext.ket.project.gate.entity.ProjectAssSheetLink;
import ext.ket.project.purchase.util.purchaseUtil;
import ext.ket.project.task.entity.AssessTaskResultDTO;
import ext.ket.project.task.entity.GateTaskOutputDTO;
import ext.ket.project.task.entity.ProjectTaskDTO;
import ext.ket.project.task.entity.TrustTaskResultDTO;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SessionUtil;
import ext.ket.sysif.sap.service.SapService;

public class StandardProjectTaskCompService extends StandardManager implements ProjectTaskCompService {
    private static final long serialVersionUID = 1L;

    public static StandardProjectTaskCompService newStandardProjectTaskCompService() throws WTException {
	StandardProjectTaskCompService instance = new StandardProjectTaskCompService();
	instance.initialize();
	return instance;
    }

    /**
     * 프로젝트 평가시트 조회하는 쿼리스펙을 리턴하는 함수(미사용)
     * 
     * @param paramObject
     * @return
     * @throws Exception
     * @메소드명 : getQuery
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQuery(ProjectTaskDTO paramObject) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx = query.appendClassList(E3PSTask.class, true);
	// if (!StringUtil.isTrimToEmpty(paramObject.getDevType())) {
	// sc = new SearchCondition(AssessSheet.class, AssessSheet.DEV_TYPE,
	// SearchCondition.LIKE, "%" + paramObject.getDevType());
	// query.appendWhere(sc, new int[] { idx });
	// }
	//
	return query;
    }

    /**
     * 프로젝트 평가시트 조회하는 함수(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : find
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<E3PSTask> find(ProjectTaskDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<E3PSTask> assList = new ArrayList<E3PSTask>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assList.add((E3PSTask) objArr[0]);
	}
	return assList;
    }

    /**
     * 프로젝트 평가시트 조회하는 함수. 서버 페이징 쿼리처리(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : findPaging
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PageControl findPaging(ProjectTaskDTO paramObject) throws Exception {
	QuerySpec query = getQuery(paramObject);
	PageControl pageControl = null;
	String pageSessionId = (String) SessionUtil.getAttribute("pageSessionId");
	// if (StringUtil.isEmpty(pageSessionId)) {
	// pageControl = CommonSearchHelper.find(paramObject.getFormPage(),
	// paramObject.getFormPage(), query);
	// } else {
	// pageControl = CommonSearchHelper.find(paramObject.getFormPage(),
	// paramObject.getFormPage(), paramObject.getPage() + 1, pageSessionId);
	// }
	return pageControl;
    }

    /**
     * 프로젝트 평가시트 수정하는 함수(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : modify
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public E3PSTask modify(ProjectTaskDTO paramObject) throws Exception {
	E3PSTask modify = (E3PSTask) CommonUtil.getObject("");

	return modify;
    }

    /**
     * 프로젝트 평가시트 저장하는 함수(현재 미사용)
     * 
     * @param AssessSheet
     * @return
     * @throws Exception
     * @메소드명 : save
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public E3PSTask save(ProjectTaskDTO paramObject) throws Exception {
	E3PSTask modify = E3PSTask.newE3PSTask();

	// ..............
	modify = (E3PSTask) PersistenceHelper.manager.save(modify);

	// KETContentHelper.service.updateContent(modify, paramObject);

	return modify;
    }

    /**
     * 프로젝트 평가시트를 삭제하는 함수(현재 미사용)
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : delete
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @Override
    public E3PSTask delete(ProjectTaskDTO paramObject) throws Exception {
	E3PSTask ass = (E3PSTask) CommonUtil.getObject(paramObject.getOid());
	return (E3PSTask) PersistenceHelper.manager.delete(ass);
    }

    /**
     * 프로젝트 테스크 완료처리 실제작업시간, 지연사유유형, 지연사유, 근거사유 등 입력값을 저장 및 실제종료일에 오늘날짜를 입력후 테스크를 종료처리한다
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : completeProjectTaskSchedule
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public E3PSTask completeProjectTaskSchedule(ProjectTaskDTO paramObject) throws Exception {
	// E3PSTask modify = E3PSTask.newE3PSTask();

	E3PSTask task = (E3PSTask) CommonUtil.getObject(paramObject.getOid());

	// 계획작업시간 업데이트
	// String pWorkTime = StringUtil.checkNullZero(paramObject.getPlanWorkTime());
	// task.setPlanWorkTime(Integer.parseInt(pWorkTime));
	// 실제작업시간 업데이트
	String eWorkTime = StringUtil.checkNullZero(paramObject.getExecWorkTime());
	task.setExecWorkTime(Float.parseFloat(eWorkTime));

	// 하위 태스크가 있다면 하위태스크들의 모든 실제작업시간을 취합해서 자동 세팅해준다
	if (!task.isLeaf()) {
	    QueryResult result = ProjectTaskHelper.manager.getChildList(task);
	    float fl_all = 0;
	    while (result != null && result.hasMoreElements()) {
		E3PSTask currentTask = (E3PSTask) result.nextElement();
		if (currentTask != null) {
		    float fl_cur = currentTask.getExecWorkTime();
		    fl_all += fl_cur;
		}
	    }
	    task.setExecWorkTime(fl_all);
	}

	// 지연사유유형
	task.setDelayType(paramObject.getDelayType());
	// 지연사유
	task.setDelayReason(paramObject.getDelayReason());

	// 완료100%사유
	task.setCompReason(paramObject.getCompReason());
	// 완료100%설정
	task.setTaskCompletion(100);

	// 오늘날짜를 실제종료일에 저장
	ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
	Timestamp ts = DateUtil.getCurrentTimestamp();
	// String execEndDate = paramObject.getExecEndDate();
	// if (execEndDate != null) {
	// ts = (new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(execEndDate, new ParsePosition(0)).getTime()));
	// }
	schedule.setExecEndDate(ts);
	schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

	// 상태 변경
	task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);

	// task 저장
	task = (E3PSTask) TaskHelper.manager.modifyTask(task);
	// Kogger.debug(getClass(), "modi = " + task);

	// 상위 Task가 있는 경우 상위 태스크의 실제작업시간 업데이트
	setUpperExecStartDate(task);

	task = (E3PSTask) ProjectTaskHelper.updateCompletion(task);

	// 평가관리일 경우 최종점수 반영
	this.EndAllAssessResultByTask(paramObject.getOid());

	String taskCategory = task.getTaskTypeCategory();

	if ("COST013".equals(taskCategory) || "COST001".equals(taskCategory)) {// 원가요청 Task일 경우 신규costpart생성하지 않기 위해
	    task.setCostRequest(true);
	    task = (E3PSTask) PersistenceHelper.manager.save(task);
	}

	// **************후행테스크 당당자에게 메일발송*****************//
	E3PSTask dependancyTask = null; // TODO 후행태스크 가져오기
	E3PSTaskData dependTaskData = null;

	QueryResult dependListResult = e3ps.project.beans.TaskDependencyHelper.manager.getDependTaskList1(task);

	if (dependListResult != null) {
	    while (dependListResult.hasMoreElements()) {
		TaskDependencyLink dependLink = (TaskDependencyLink) dependListResult.nextElement();
		dependancyTask = (E3PSTask) dependLink.getDependSource();// .getDependTarget();
		dependTaskData = new E3PSTaskData(dependancyTask);
	    }
	    if (dependancyTask != null) {
		// From 관리자( 선행테스크 책임자)
		WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");// 기본 발송자는 관리자
		QueryResult masterList = TaskUserHelper.manager.getMaster(task);
		if (masterList != null) {
		    while (masterList.hasMoreElements()) {
			Object o[] = (Object[]) masterList.nextElement();
			TaskMemberLink link = (TaskMemberLink) o[0];
			PeopleData data = new PeopleData(link.getMember().getMember());

			wtUserFrom = data.wtuser;
		    }
		}

		// To 책임자조회
		List<WTUser> listToUser = new ArrayList<WTUser>();
		masterList = TaskUserHelper.manager.getMaster(dependancyTask);
		if (masterList != null) {
		    while (masterList.hasMoreElements()) {
			Object o[] = (Object[]) masterList.nextElement();
			TaskMemberLink link = (TaskMemberLink) o[0];
			PeopleData data = new PeopleData(link.getMember().getMember());
			Kogger.debug(getClass(),
			        "From Email:" + ((WTUser) data.wtuser).getEMail() + ", name:" + ((WTUser) data.wtuser).getName());

			WTUser wtUserTo = data.wtuser;
			listToUser.add(wtUserTo);
		    }
		}
		// WTUser wtUserIMSI = KETObjectUtil.getUser("PLMTFT");
		// listToUser.add(wtUserIMSI);
		KETMailHelper.service.sendFormMail("08010", "ProjectDependancyTaskNoticeMail.html", task, wtUserFrom, listToUser);

	    }
	}
	// **************후행테스크 당당자에게 메일발송*****************//

	syncMainSchedule(task);

	E3PSTask tempTask = task;
	tempTask = (E3PSTask) PersistenceHelper.manager.refresh(tempTask);
	while (tempTask != null) {

	    tempTask = (E3PSTask) tempTask.getParent();
	    if (tempTask != null) {
		tempTask = (E3PSTask) PersistenceHelper.manager.refresh(tempTask);
		syncMainSchedule(tempTask);
	    }
	}

	return task;

    }

    @Override
    public E3PSTask syncMainSchedule(E3PSTask task) throws Exception, WTException {
	// 주요일정 동기화 start (2018.08.29 황정태추가)
	TemplateProject pjt = task.getProject();
	pjt = (TemplateProject) PersistenceHelper.manager.refresh(pjt);
	if (pjt instanceof ProductProject && task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE
	        && StringUtils.isNotEmpty(task.getMainScheduleCode())) {
	    try {
		ProjectTaskHelper.manager.mainSchedulUpdate(pjt, task.getMainScheduleCode(), false);
	    } catch (Exception e) {
		e.printStackTrace();
		System.out.println(pjt.getPjtNo() + " 의 타스크 " + task.getTaskName() + " 주요일정동기화 중 오류가 발생했습니다.");
	    }
	}

	return task;

	// 주요일정 동기화 end
    }

    /**
     * 태스크 완료시 상위태스크의 실제 작업 시간 저장
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : saveCompleteProjectTaskSchedule
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void setUpperExecStartDate(E3PSTask task) throws Exception {
	// 상위 Task가 있는 경우
	E3PSTask parentTask = (E3PSTask) task.getParent();
	if (parentTask != null) {
	    QueryResult result = ProjectTaskHelper.manager.getChildList(parentTask);
	    float fl_all = 0;
	    while (result != null && result.hasMoreElements()) {
		E3PSTask currentTask = (E3PSTask) result.nextElement();
		if (currentTask != null) {
		    float fl_cur = currentTask.getExecWorkTime();
		    fl_all += fl_cur;
		}
	    }
	    parentTask.setExecWorkTime(fl_all);

	    parentTask = (E3PSTask) PersistenceHelper.manager.modify(parentTask);

	    E3PSTask parentParentTask = (E3PSTask) parentTask.getParent();
	    if (parentParentTask != null && !StringUtil.isEmpty(CommonUtil.getOIDLongValue2Str(parentParentTask))) {
		setUpperExecStartDate(parentTask);
	    }
	}
    }

    /**
     * 프로젝트 테스크 완료팝업화면에서 저장처리 실제작업시간, 지연사유유형, 지연사유 등 입력값을 저장한다
     * 
     * @param ProjectTaskDTO
     * @return
     * @throws Exception
     * @메소드명 : saveCompleteProjectTaskSchedule
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public E3PSTask saveCompleteProjectTaskSchedule(ProjectTaskDTO paramObject) throws Exception {
	// E3PSTask modify = E3PSTask.newE3PSTask();

	E3PSTask task = (E3PSTask) CommonUtil.getObject(paramObject.getOid());

	// 실제작업시간 업데이트
	String eWorkTime = StringUtil.checkNullZero(paramObject.getExecWorkTime());
	task.setExecWorkTime(Float.parseFloat(eWorkTime));

	// 근거사유(완료100%사유)
	task.setCompReason(paramObject.getCompReason());

	// 지연사유유형
	task.setDelayType(paramObject.getDelayType());
	// 지연사유
	task.setDelayReason(paramObject.getDelayReason());

	// task 저장
	task = (E3PSTask) PersistenceHelper.manager.modify(task);
	// Kogger.debug(getClass(), "modi = " + task);

	return task;
    }

    /**
     * 프로젝트 테스크 산출물리스트 조회
     * 
     * @param taskOid
     * @return
     * @throws Exception
     * @메소드명 : getProjectOutputList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateTaskOutputDTO> getProjectOutputList(String taskOid, GateAssessResult gateRslt, int gateDegree) throws Exception {
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer(0);

	StringBuffer basicsb = new StringBuffer(0);

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String e3psTaskOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(task));
	String gateTaskName = task.getTaskName();

	E3PSProject project = (E3PSProject) task.getProject();
	String e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(project));

	boolean isGateAssResultInwork = false;
	boolean isWork = false;
	if (gateRslt == null) {
	    isGateAssResultInwork = true;
	} else {
	    if (gateRslt.getState().toString().equals("") || gateRslt.getState().toString().equals("INWORK")
		    || gateRslt.getState().toString().equals("REWORK")) {
		isGateAssResultInwork = false;
		isWork = true;
	    }
	}
	String taskTypeCategory = task.getTaskTypeCategory();

	List<GateTaskOutputDTO> rstList = new ArrayList<GateTaskOutputDTO>();

	basicsb.append("		SELECT											\n");
	basicsb.append("		     TASK.TASK_OID        									\n");
	basicsb.append("		    , o.outputName          OUTPUT_NAME        									\n");
	basicsb.append("		    , o.ida2a2              OUTPUT_OID        									\n");
	basicsb.append("		    , wu.FULLNAME            CHARGE_NAME									\n");
	basicsb.append("		    , '' OUTPUT_CHECK									\n");
	basicsb.append("		    , '' OUTPUT_COMMENT									\n");
	basicsb.append("		    , TASK.RNUM             OUTPUT_RNUM									\n");
	basicsb.append("		    , TASK.LEV      OUTPUT_LEVEL									\n");
	basicsb.append("		    , o.subjectType            OUTPUT_SUBJECTTYPE    									\n");
	basicsb.append("		    , TASK.OUTPUT_TASKTYPE            OUTPUT_TASKTYPE        									\n");
	basicsb.append("		    , TASK.OUTPUT_TASKNAME            OUTPUT_TASKNAME        									\n");
	basicsb.append("		    , TASK.CREATESTAMPA2        CREATESTAMPA2        									\n");
	basicsb.append("		    , '' OUTPUT_STATE									\n");
	basicsb.append("		    , '' OBJECT_OID									\n");
	basicsb.append("		    , o.gateCheckType GATE_CHECK_TYPE									\n");
	basicsb.append("		    , TASKTYPECATEGORY									\n");
	basicsb.append("		  FROM (    SELECT rownum RNUM, t.ida2a2 TASK_OID,									\n");
	basicsb.append("		                   T.TASKNAME,									\n");
	basicsb.append("		                   t.taskseq,									\n");
	basicsb.append("		                   p.ida2a2 PJT_OID,									\n");
	basicsb.append("		                   T.TASKTYPE OUTPUT_TASKTYPE,									\n");
	basicsb.append("		                   LEVEL LEV,									\n");
	basicsb.append("		                   T.TASKNAME OUTPUT_TASKNAME,									\n");
	basicsb.append("		                   T.CREATESTAMPA2 CREATESTAMPA2,									\n");
	basicsb.append("		                   T.TASKTYPECATEGORY								\n");
	basicsb.append("		              FROM E3PSTask t									\n");
	if (project instanceof MoldProject) {
	    basicsb.append("	, (SELECT * FROM MoldProject WHERE IDA2A2 = '" + e3psProjectOID + "') p			\n");
	} else if (project instanceof ProductProject) {
	    basicsb.append("	, (SELECT * FROM ProductProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
	} else if (project instanceof ReviewProject) {
	    basicsb.append("	, (SELECT * FROM ReviewProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
	}
	basicsb.append("		             WHERE     t.ida3b4 = p.ida2a2									\n");
	basicsb.append("		        START WITH t.ida3parentreference = 0 									\n");
	basicsb.append("		        CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference									\n");
	basicsb.append("		        ORDER SIBLINGS BY t.taskseq									\n");
	basicsb.append("		) TASK									\n");
	basicsb.append("		,  projectOutput o               									\n");
	basicsb.append("		, TASKMEMBERLINK tml                    									\n");
	basicsb.append("		, PROJECTMEMBERLINK pml                    									\n");
	basicsb.append("		, WTUSER wu                    									\n");
	basicsb.append("		where TASK.TASK_OID = o.idA3b5(+)									\n");
	basicsb.append("		AND TASK.TASK_OID = tml.ida3a5(+)                									\n");
	basicsb.append("		AND tml.TASKMEMBERTYPE(+) = '1'                									\n");
	basicsb.append("		AND tml.ida3b5 = pml.ida2a2(+)                									\n");
	basicsb.append("		AND pml.ida3b5 = wu.ida2a2(+) 									\n");
	basicsb.append("		ORDER BY TASK.RNUM 									\n");

	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    if (isGateAssResultInwork) {

		rs = stmt.executeQuery(basicsb.toString());
		// Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

		while (rs.next()) {
		    String taskType = rs.getString("OUTPUT_TASKTYPE");
		    String taskCategoryType = rs.getString("TASKTYPECATEGORY");
		    // p-Gate3, pGate4.1, pGate4.2는 무시한다
		    if ("Gate".equals(taskType)
			    && ("p-3".equals(taskCategoryType) || "p-4.1".equals(taskCategoryType) || "p-4.2".equals(taskCategoryType))) {
			taskType = "";
		    }
		    // 테스크 타입이 Gate인 경우
		    if ("Gate".equals(taskType)) {
			if (gateTaskName.equals(rs.getString("OUTPUT_TASKNAME"))) {
			    // Gate테스크 명과 검색한 테스크명이 같으면 break;
			    break;
			} else {
			    // Gate테스크 명과 테스크명이 같지 않으면 초기화
			    rstList = new ArrayList<GateTaskOutputDTO>();
			}
		    } else {

			if (StringUtil.isEmpty(rs.getString("OUTPUT_OID"))) {
			    continue;
			}
			if ("대상".equals(rs.getString("OUTPUT_SUBJECTTYPE"))) {

			    GateTaskOutputDTO rsDto = new GateTaskOutputDTO();

			    rsDto.setTaskOid(rs.getString("TASK_OID"));
			    rsDto.setOutputOid(rs.getString("OUTPUT_OID"));
			    rsDto.setOutputName(rs.getString("OUTPUT_NAME"));
			    rsDto.setChargeName(rs.getString("CHARGE_NAME"));
			    rsDto.setOutputCheck(rs.getString("OUTPUT_CHECK"));
			    rsDto.setOutputComment(rs.getString("OUTPUT_COMMENT"));
			    rsDto.setOutputLevel(rs.getString("OUTPUT_LEVEL"));
			    rsDto.setOutputTaskType(rs.getString("OUTPUT_TASKTYPE"));
			    rsDto.setOutputTaskName(rs.getString("OUTPUT_TASKNAME"));
			    rsDto.setOutputState(rs.getString("OUTPUT_STATE"));
			    rsDto.setObjectOid(rs.getString("OBJECT_OID"));
			    rstList.add(rsDto);
			}
		    }
		}

		rs.close();

	    } else {
		sb = new StringBuffer(0);
		sb.append("	SELECT * FROM (					\n");
		sb.append("	select 						\n");
		sb.append("	 t.ida2a2 			TASK_OID		\n");
		sb.append("	, assLink.outputName  		OUTPUT_NAME		\n");
		sb.append("	, o.ida2a2	  		OUTPUT_OID		\n");
		sb.append("	, assLink.outputCharge		CHARGE_NAME		\n");
		sb.append("	, assLink.outputCheck		OUTPUT_CHECK		\n");
		sb.append("	, assLink.outputComment		OUTPUT_COMMENT		\n");
		sb.append("	, ROWNUM 			OUTPUT_RNUM		\n");
		sb.append("	, o.subjectType			OUTPUT_SUBJECTTYPE	\n");
		sb.append("	, T.TASKTYPE			OUTPUT_TASKTYPE		\n");
		sb.append("	, T.TASKNAME			OUTPUT_TASKNAME		\n");
		sb.append("	, assLink.ida2a2		OUTPUTLINK_OID		\n");
		sb.append("	, T.CREATESTAMPA2		CREATESTAMPA2		\n");
		sb.append("	, assLink.outputState		OUTPUT_STATE		\n");
		sb.append("	, assLink.outputOid		OBJECT_OID		\n");
		sb.append("	from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') T	\n");
		sb.append("	, ProductProject  p		\n");
		sb.append("	, projectOutput o					\n");
		sb.append("	, ketGateAssessResult r				\n");
		sb.append("	, ketGateAssRsltTaskLink gLink				\n");
		sb.append("	, TASKMEMBERLINK tml					\n");
		sb.append("	, PROJECTMEMBERLINK pml					\n");
		sb.append("	, WTUSER wu						\n");
		sb.append("	, KETAssessResultOutputLink assLink			\n");
		sb.append("	WHERE t.ida2a2 = gLink.idA3B5				\n");
		sb.append("	AND gLink.idA3A5 = r.ida2a2				\n");
		sb.append("	AND t.ida3b4 =  p.ida2a2				\n");
		// sb.append("	AND p.LASTEST = 1					\n");
		// sb.append("	AND p.CHECKOUTSTATE <> 'c/o'				\n");
		sb.append("	AND t.ida2a2 = tml.ida3a5(+)				\n");
		sb.append("	AND tml.TASKMEMBERTYPE(+) = '1'				\n");
		sb.append("	AND tml.ida3b5 = pml.ida2a2(+)				\n");
		sb.append("	AND pml.ida3b5 = wu.ida2a2(+)				\n");
		sb.append("	AND r.ida2a2 = assLink.idA3A5(+)			\n");
		sb.append("	AND assLink.idA3B5 = o.ida2a2(+)			\n");
		sb.append("	and o.idA3A5 = p.ida2a2					\n");
		sb.append("	AND assLink.rev = " + gateDegree + "		\n");
		sb.append("	AND EXISTS						\n");
		sb.append("		(SELECT 'T'					\n");
		sb.append("		   FROM PRODUCTPROJECT PJT, E3PSTASK TASK	\n");
		sb.append("		WHERE TASK.IDA3B4 = PJT.IDA2A2			\n");
		sb.append("		AND PJT.IDA2A2 = " + e3psProjectOID + "		\n");
		sb.append("		AND TASK.IDA2A2 = t.ida2a2)			\n");
		sb.append("	) ORDER BY OUTPUT_RNUM, CREATESTAMPA2			\n");

		rs = stmt.executeQuery(sb.toString());
		// Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

		while (rs.next()) {
		    GateTaskOutputDTO rsDto = new GateTaskOutputDTO();

		    rsDto.setTaskOid(rs.getString("TASK_OID"));
		    rsDto.setOutputOid(rs.getString("OUTPUT_OID"));
		    rsDto.setOutputName(rs.getString("OUTPUT_NAME"));
		    rsDto.setChargeName(rs.getString("CHARGE_NAME"));
		    rsDto.setOutputCheck(rs.getString("OUTPUT_CHECK"));
		    rsDto.setOutputComment(rs.getString("OUTPUT_COMMENT"));
		    rsDto.setOutputLevel("");
		    rsDto.setOutputTaskType(rs.getString("OUTPUT_TASKTYPE"));
		    rsDto.setOutputTaskName(rs.getString("OUTPUT_TASKNAME"));
		    rsDto.setOutputState(rs.getString("OUTPUT_STATE"));
		    rsDto.setObjectOid(rs.getString("OBJECT_OID"));
		    rsDto.setOutputLinkOid(rs.getString("OUTPUTLINK_OID"));
		    rstList.add(rsDto);
		}
		rs.close();

		if (isWork) {
		    List<GateTaskOutputDTO> rstList2 = new ArrayList<GateTaskOutputDTO>();
		    sb = new StringBuffer(0);
		    sb.append("		SELECT											\n");
		    sb.append("		     TASK.TASK_OID        									\n");
		    sb.append("		    , o.outputName          OUTPUT_NAME        									\n");
		    sb.append("		    , o.ida2a2              OUTPUT_OID        									\n");
		    sb.append("		    , wu.FULLNAME            CHARGE_NAME									\n");
		    sb.append("		    , '' OUTPUT_CHECK									\n");
		    sb.append("		    , '' OUTPUT_COMMENT									\n");
		    sb.append("		    , TASK.RNUM             OUTPUT_RNUM									\n");
		    sb.append("		    , TASK.LEV              OUTPUT_LEVEL								\n");
		    sb.append("		    , o.subjectType            OUTPUT_SUBJECTTYPE    									\n");
		    sb.append("		    , TASK.OUTPUT_TASKTYPE            OUTPUT_TASKTYPE        									\n");
		    sb.append("		    , TASK.OUTPUT_TASKNAME            OUTPUT_TASKNAME        									\n");
		    sb.append("		    , TASK.CREATESTAMPA2        CREATESTAMPA2        									\n");
		    sb.append("		    , '' OUTPUT_STATE									\n");
		    sb.append("		    , '' OBJECT_OID									\n");
		    sb.append("		    , TASKTYPECATEGORY									\n");
		    sb.append("		  FROM (    SELECT rownum RNUM, t.ida2a2 TASK_OID,									\n");
		    sb.append("		                   T.TASKNAME,									\n");
		    sb.append("		                   t.taskseq,									\n");
		    sb.append("		                   p.ida2a2 PJT_OID,									\n");
		    sb.append("		                   LEVEL LEV,									\n");
		    sb.append("		                   T.TASKTYPE OUTPUT_TASKTYPE,									\n");
		    sb.append("		                   T.TASKNAME OUTPUT_TASKNAME,									\n");
		    sb.append("		                   T.CREATESTAMPA2 CREATESTAMPA2,									\n");
		    sb.append("		                   T.TASKTYPECATEGORY									\n");
		    sb.append("		              FROM E3PSTask t									\n");
		    if (project instanceof MoldProject) {
			sb.append("	, (SELECT * FROM MoldProject WHERE IDA2A2 = '" + e3psProjectOID + "') p			\n");
		    } else if (project instanceof ProductProject) {
			sb.append("	, (SELECT * FROM ProductProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
		    } else if (project instanceof ReviewProject) {
			sb.append("	, (SELECT * FROM ReviewProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
		    }
		    sb.append("		             WHERE     t.ida3b4 = p.ida2a2									\n");
		    sb.append("		        START WITH t.ida3parentreference = 0 									\n");
		    sb.append("		        CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference									\n");
		    sb.append("		        ORDER SIBLINGS BY t.taskseq									\n");
		    sb.append("		) TASK									\n");
		    sb.append("		,  projectOutput o               									\n");
		    sb.append("		, TASKMEMBERLINK tml                    									\n");
		    sb.append("		, PROJECTMEMBERLINK pml                    									\n");
		    sb.append("		, WTUSER wu                    									\n");
		    sb.append("		where TASK.TASK_OID = o.idA3b5(+)									\n");
		    sb.append("		AND TASK.TASK_OID = tml.ida3a5(+)                									\n");
		    sb.append("		AND tml.TASKMEMBERTYPE(+) = '1'                									\n");
		    sb.append("		AND tml.ida3b5 = pml.ida2a2(+)                									\n");
		    sb.append("		AND pml.ida3b5 = wu.ida2a2(+) 									\n");
		    sb.append("		ORDER BY TASK.RNUM 									\n");

		    rs = stmt.executeQuery(sb.toString());
		    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

		    rstList2 = new ArrayList<GateTaskOutputDTO>();
		    while (rs.next()) {
			String taskType = rs.getString("OUTPUT_TASKTYPE");
			String taskCategoryType = rs.getString("TASKTYPECATEGORY");
			// p-Gate3, pGate4.1, pGate4.2는 무시한다
			if ("Gate".equals(taskType)
			        && ("p-3".equals(taskCategoryType) || "p-4.1".equals(taskCategoryType) || "p-4.2".equals(taskCategoryType))) {
			    taskType = "";
			}
			// 테스크 타입이 Gate인 경우
			if ("Gate".equals(taskType)) {
			    if (gateTaskName.equals(rs.getString("OUTPUT_TASKNAME"))) {
				// Gate테스크 명과 검색한 테스크명이 같으면 break;
				break;
			    } else {
				// Gate테스크 명과 테스크명이 같지 않으면 초기화
				rstList2 = new ArrayList<GateTaskOutputDTO>();
			    }
			} else {

			    if (StringUtil.isEmpty(rs.getString("OUTPUT_OID"))) {
				continue;
			    }
			    if ("대상".equals(rs.getString("OUTPUT_SUBJECTTYPE"))) {

				GateTaskOutputDTO rsDto = new GateTaskOutputDTO();

				rsDto.setTaskOid(rs.getString("TASK_OID"));
				rsDto.setOutputOid(rs.getString("OUTPUT_OID"));
				rsDto.setOutputName(rs.getString("OUTPUT_NAME"));
				rsDto.setChargeName(rs.getString("CHARGE_NAME"));
				rsDto.setOutputCheck(rs.getString("OUTPUT_CHECK"));
				rsDto.setOutputComment(rs.getString("OUTPUT_COMMENT"));
				rsDto.setOutputLevel(rs.getString("OUTPUT_LEVEL"));
				rsDto.setOutputTaskType(rs.getString("OUTPUT_TASKTYPE"));
				rsDto.setOutputTaskName(rs.getString("OUTPUT_TASKNAME"));
				rsDto.setOutputState(rs.getString("OUTPUT_STATE"));
				rsDto.setObjectOid(rs.getString("OBJECT_OID"));

				boolean isDupliOutput = false;
				if (rstList != null) {
				    for (int k = 0; k < rstList.size(); k++) {
					GateTaskOutputDTO rsDto2 = new GateTaskOutputDTO();
					rsDto2 = rstList.get(k);
					if (rs.getString("OUTPUT_OID") != null && rs.getString("OUTPUT_OID").equals(rsDto2.getOutputOid())) {
					    isDupliOutput = true;
					}
				    }
				}
				if (isDupliOutput)
				    continue;
				rstList2.add(rsDto);
			    }
			}
		    }
		    rs.close();
		    if (rstList2 != null)
			rstList.addAll(rstList2);
		}
	    }
	    // Gate 추가 점검 대상 add 시작 2017.06.29 by 황정태
	    String GATE_CHECK_TYPE[] = {};

	    rs = stmt.executeQuery(basicsb.toString());
	    String GateCheckType = "";

	    while (rs.next()) {

		E3PSTask targetTask = (E3PSTask) CommonUtil.getObject("e3ps.project.E3PSTask:" + rs.getString("TASK_OID"));

		if ("Gate".equals(rs.getString("OUTPUT_TASKTYPE")) && !taskTypeCategory.startsWith("p-")
		        && !targetTask.getTaskTypeCategory().startsWith("p-")
		        && Integer.parseInt(targetTask.getTaskTypeCategory()) >= Integer.parseInt(taskTypeCategory)) {// 현재 단계의 게이트 이전 산출물만
		    // 점검하는 것임
		    break;
		}

		if (StringUtil.isEmpty(rs.getString("OUTPUT_OID")) || !"대상".equals(rs.getString("OUTPUT_SUBJECTTYPE"))) {
		    continue;
		}

		GateCheckType = rs.getString("GATE_CHECK_TYPE");

		if (StringUtils.isNotEmpty(StringUtil.checkNull(GateCheckType))) {
		    boolean isGateCheck = false;
		    GATE_CHECK_TYPE = rs.getString("GATE_CHECK_TYPE").split(",");

		    for (int i = 0; i < GATE_CHECK_TYPE.length; i++) {
			if (taskTypeCategory.equals(GATE_CHECK_TYPE[i])) {
			    isGateCheck = true;
			}
		    }

		    if (isGateCheck) {
			boolean isDup = false;
			if (rstList.size() > 0) {
			    for (int k = 0; k < rstList.size(); k++) {
				GateTaskOutputDTO rsDto = new GateTaskOutputDTO();
				rsDto = rstList.get(k);
				if (rs.getString("OUTPUT_OID") != null && rs.getString("OUTPUT_OID").equals(rsDto.getOutputOid())) {
				    isDup = true;
				}
			    }
			}

			if (isDup) {
			    continue;
			}

			GateTaskOutputDTO rsDto = new GateTaskOutputDTO();

			rsDto.setTaskOid(rs.getString("TASK_OID"));
			rsDto.setOutputOid(rs.getString("OUTPUT_OID"));
			rsDto.setOutputName(rs.getString("OUTPUT_NAME"));
			rsDto.setChargeName(rs.getString("CHARGE_NAME"));
			rsDto.setOutputCheck(rs.getString("OUTPUT_CHECK"));
			rsDto.setOutputComment(rs.getString("OUTPUT_COMMENT"));
			rsDto.setOutputLevel(rs.getString("OUTPUT_LEVEL"));
			rsDto.setOutputTaskType(rs.getString("OUTPUT_TASKTYPE"));
			rsDto.setOutputTaskName(rs.getString("OUTPUT_TASKNAME"));
			rsDto.setOutputState(rs.getString("OUTPUT_STATE"));
			rsDto.setObjectOid(rs.getString("OBJECT_OID"));
			rstList.add(rsDto);

		    }

		}
	    }
	    rs.close();
	    // Gate 추가 점검 대상 add 종료

	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return rstList;
    }

    /**
     * Gate 테스크에 연결된 프로젝트의 Gate 평가항목을 조회하고 결과에서 해당 Gate정보만 가져오기
     * 
     * @param taskOid
     * @return
     * @throws Exception
     * @메소드명 : getProjectTaskGateCheckLinkList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateCheckSheetDTO> getProjectTaskGateCheckLinkList(String taskOid, GateAssessResult gateRslt, int gateDegree)
	    throws Exception {
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String gateTaskName = task.getTaskName();
	E3PSProject project = (E3PSProject) task.getProject();
	String e3psTaskOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(task));

	boolean isGateAssResultInwork = false;
	if (gateRslt == null) {
	    isGateAssResultInwork = true;
	} else {
	    if (gateRslt.getState().toString().equals("") || gateRslt.getState().toString().equals("INWORK")
		    || gateRslt.getState().toString().equals("REWORK")) {
		isGateAssResultInwork = true;
	    }
	}

	List<GateCheckSheetDTO> gateCheckSheetList = new ArrayList<GateCheckSheetDTO>();
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);

	    if (gateRslt == null) {
		sb.append("	SELECT N3.NAME TARGETTYPENAME               \n");
		sb.append("	, G.CHECKSHEETNAME                     \n");
		sb.append("	, G.ACHIEVEBASE                            \n");
		sb.append("	, G.UNIT                            \n");
		sb.append("	, G.CRITERIA                            \n");
		sb.append("	, G.ORDERNO                            \n");
		sb.append("	, G.IDA2A2    GATEOID                        \n");
		sb.append("	, ''          RESULTOID                        \n");
		sb.append("	, T.ida2a2 TASKOID                \n");
		sb.append("	, NVL(DECODE(N2.CODE,'G1',G.SELECT1B4,'G2',G.SELECT2B4                            \n");
		sb.append("	     , 'G3',G.SELECT3B4,'G4',G.SELECT4B4,'G5',G.SELECT5B4,'G6',G.SELECT6B4                    \n");
		sb.append("	        , 'G7',G.SELECT7B4, 'G8',G.SELECT8B4,'G9',G.SELECT9B4,'G10',G.SELECT10B4                    \n");
		sb.append("	        , 'G11',G.SELECT11B4, 'G12',G.SELECT12B4,'G13',G.SELECT13B4,'G14',G.SELECT14B4                    \n");
		sb.append("	        , 'G15',G.SELECT15B4, 'G16',G.SELECT16B4,'G17',G.SELECT17B4,'G18',G.SELECT18B4                    \n");
		sb.append("	        , 'G19',G.SELECT19B4, 'G20',G.SELECT20B4) ,'')    SELECTBASE                    \n");
		sb.append("	, NVL(DECODE(N2.CODE,'G1',G.TARGET1B4,'G2',G.TARGET2B4                            \n");
		sb.append("	     , 'G3',G.TARGET3B4,'G4',G.TARGET4B4,'G5',G.TARGET5B4,'G6',G.TARGET6B4                    \n");
		sb.append("	        , 'G7',G.TARGET7B4, 'G8',G.TARGET8B4,'G9',G.TARGET9B4,'G10',G.TARGET10B4                    \n");
		sb.append("	        , 'G11',G.TARGET11B4, 'G12',G.TARGET12B4,'G13',G.TARGET13B4,'G14',G.TARGET14B4                    \n");
		sb.append("	        , 'G15',G.TARGET15B4, 'G16',G.TARGET16B4,'G17',G.TARGET17B4,'G18',G.TARGET18B4                    \n");
		sb.append("	        , 'G19',G.TARGET19B4, 'G20',G.TARGET20B4) ,'')    TARGETBASE                    \n");
		sb.append("	, NVL(DECODE(N2.CODE,'G1',G.RESULT1B4,'G2',G.RESULT2B4                            \n");
		sb.append("	     , 'G3',G.RESULT3B4,'G4',G.RESULT4B4,'G5',G.RESULT5B4,'G6',G.RESULT6B4                    \n");
		sb.append("	     , 'G7',G.RESULT7B4, 'G8',G.RESULT8B4,'G9',G.RESULT9B4,'G10',G.RESULT10B4                    \n");
		sb.append("	     , 'G11',G.RESULT11B4, 'G12',G.RESULT12B4,'G13',G.RESULT13B4,'G14',G.RESULT14B4                    \n");
		sb.append("	     , 'G15',G.RESULT15B4, 'G16',G.RESULT16B4,'G17',G.RESULT17B4,'G18',G.RESULT18B4                    \n");
		sb.append("	         , 'G19',G.RESULT19B4, 'G20',G.RESULT20B4) ,'')    RESULTBASE                    \n");
		sb.append("	, DECODE(N2.CODE, 'G1', SELECT1B4, 'G2', SELECT2B4, 'G3', SELECT3B4, 'G4', SELECT4B4, 'G5', SELECT5B4                \n");
		sb.append("	        , 'G6', SELECT6B4, 'G7', SELECT7B4, 'G8', SELECT8B4, 'G9', SELECT9B4, 'G10', SELECT10B4                \n");
		sb.append("	        , 'G11', SELECT11B4, 'G12', SELECT12B4, 'G13', SELECT13B4, 'G14', SELECT14B4, 'G15', SELECT15B4                \n");
		sb.append("	        , 'G16', SELECT16B4, 'G17', SELECT17B4, 'G18', SELECT18B4, 'G19', SELECT19B4, 'G20', SELECT20B4, ''                \n");
		sb.append("	        ) AS SELECT_VAL                \n");
		sb.append("	, ''          CHECKBASE                        \n");
		sb.append("	, ''          CHECKRESULTBASE                        \n");
		sb.append("	    from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') T                         \n");
		sb.append("	    , ProductProject P                             \n");
		sb.append("	        , PROJECTASSSHEETLINK L                                \n");
		sb.append("	        , KETASSESSSHEET A                                \n");
		sb.append("	        , ASSSHEETGATECHKSHEETLINK S                                \n");
		sb.append("	        , KETGATECHECKSHEET G                  \n");
		sb.append("	        , NumberCode N2                \n");
		sb.append("	        , NumberCode N3                \n");
		sb.append("	    WHERE T.IDA3B4 = P.IDA2A2                            \n");
		sb.append("	        AND P.IDA2A2 = L.IDA3B5                            \n");
		sb.append("	        AND L.IDA3A5 = A.IDA2A2                            \n");
		sb.append("	        AND A.IDA2A2 = S.idA3B5                            \n");
		sb.append("	        AND S.IDA3A5 = G.IDA2A2                      \n");
		sb.append("	        AND T.TASKTYPE = 'Gate'                            \n");
		sb.append("	        AND N2.CODETYPE='GATELEVELNAME'                        \n");
		sb.append("		AND A.STATESTATE='APPROVED'		\n");
		sb.append("	        AND T.TASKTYPECATEGORY = REPLACE(N2.NAME,'Gate','')                 \n");
		sb.append("	        AND N3.CODETYPE='ASSESSTARGETTYPE'                        \n");
		sb.append("	        AND G.IDA3A4  = N3.IDA2A2                  \n");
		sb.append("	        ORDER BY G.ORDERNO                  \n");

		rs = stmt.executeQuery(sb.toString());
		// Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

		while (rs.next()) {
		    if (gateRslt == null || isGateAssResultInwork) {
			// 프로젝트 평가항목 리스트에서 해당 Gate명에 해당하는 체크항목이 체크된 것만 가져온다
			if (!"1".equals(rs.getString("SELECTBASE")))
			    continue;
		    }
		    GateCheckSheetDTO rsDto = new GateCheckSheetDTO();

		    rsDto.setGateCheckSheetOid(rs.getString("GATEOID"));
		    rsDto.setResultOid(rs.getString("RESULTOID"));
		    rsDto.setTargetTypeName(rs.getString("TARGETTYPENAME"));
		    rsDto.setCheckSheetName(rs.getString("CHECKSHEETNAME"));
		    rsDto.setAchieveBase(rs.getString("ACHIEVEBASE"));
		    rsDto.setUnit(rs.getString("UNIT"));
		    rsDto.setCriteria(rs.getString("CRITERIA"));
		    rsDto.setTaskTarget(rs.getString("TARGETBASE"));
		    rsDto.setTaskResult(rs.getString("CHECKRESULTBASE"));
		    rsDto.setTaskCheck(rs.getString("CHECKBASE"));
		    rsDto.setTaskSelect(rs.getString("SELECTBASE"));

		    gateCheckSheetList.add(rsDto);
		}

	    } else {
		if (gateDegree > 1 && gateDegree != this.getMaxGateDegree(taskOid)) {
		    isGateAssResultInwork = false;
		}
		if (isGateAssResultInwork) {

		    sb.append("	SELECT    NVL(MAIN.NAME,'') TARGETTYPENAME                 \n");
		    sb.append("	        , MAIN.CHECKSHEETNAME                        \n");
		    sb.append("	        , MAIN.ACHIEVEBASE                            \n");
		    sb.append("	        , MAIN.UNIT                            \n");
		    sb.append("	        , MAIN.CRITERIA                            \n");
		    sb.append("	        , MAIN.ORDERNO                            \n");
		    sb.append("	        , MAIN.GATEOID    GATEOID                        \n");
		    sb.append("	        , LINK.IDA3B5    RESULTOID                \n");
		    sb.append("	        , LINK.IDA2A2    GATELINK_OID                \n");
		    sb.append("	        , MAIN.SELECTBASE                    \n");
		    sb.append("	        , MAIN.TARGETBASE                    \n");
		    sb.append("	        , MAIN.RESULTBASE                    \n");
		    sb.append("	        , NVL(DECODE(MAIN.CODE, 'G1',LINK.sheetCheck1, 'G2',LINK.sheetCheck2                            \n");
		    sb.append("	             , 'G3',LINK.sheetCheck3, 'G4',LINK.sheetCheck4, 'G5',LINK.sheetCheck5, 'G6',LINK.sheetCheck6                    \n");
		    sb.append("	             , 'G7',LINK.sheetCheck7, 'G8',LINK.sheetCheck8, 'G9',LINK.sheetCheck9, 'G10',LINK.sheetCheck10                    \n");
		    sb.append("	             , 'G11',LINK.sheetCheck11, 'G12',LINK.sheetCheck12, 'G13',LINK.sheetCheck13, 'G14',LINK.sheetCheck14                    \n");
		    sb.append("	             , 'G15',LINK.sheetCheck15, 'G16',LINK.sheetCheck16, 'G17',LINK.sheetCheck17, 'G18',LINK.sheetCheck18                    \n");
		    sb.append("	                 , 'G19',LINK.sheetCheck19, 'G20',LINK.sheetCheck20) ,'')    CHECKBASE                    \n");
		    sb.append("	        , NVL(DECODE(MAIN.CODE, 'G1',LINK.sheetResult1, 'G2',LINK.sheetResult2                            \n");
		    sb.append("	             , 'G3',LINK.sheetResult3, 'G4',LINK.sheetResult4, 'G5',LINK.sheetResult5, 'G6',LINK.sheetResult6                    \n");
		    sb.append("	             , 'G7',LINK.sheetResult7, 'G8',LINK.sheetResult8, 'G9',LINK.sheetResult9, 'G10',LINK.sheetResult10                    \n");
		    sb.append("	             , 'G11',LINK.sheetResult11, 'G12',LINK.sheetResult12, 'G13',LINK.sheetResult13, 'G14',LINK.sheetResult14                    \n");
		    sb.append("	             , 'G15',LINK.sheetResult15, 'G16',LINK.sheetResult16, 'G17',LINK.sheetResult17, 'G18',LINK.sheetResult18                    \n");
		    sb.append("	                 , 'G19',LINK.sheetResult19, 'G20',LINK.sheetResult20) ,'')    CHECKRESULTBASE,LINK.MANAGERDEPTCODE   ,LINK.MANAGERDEPTNAME                    \n");
		    sb.append("	FROM                 \n");
		    sb.append("	(                \n");
		    sb.append("	SELECT N2.CODE                \n");
		    sb.append("	, N3.NAME                \n");
		    sb.append("	, G.CHECKSHEETNAME                     \n");
		    sb.append("	, G.ACHIEVEBASE                            \n");
		    sb.append("	, G.UNIT                            \n");
		    sb.append("	, G.CRITERIA                            \n");
		    sb.append("	, G.ORDERNO                            \n");
		    sb.append("	, G.IDA2A2    GATEOID                        \n");
		    sb.append("	, NVL(DECODE(N2.CODE,'G1',G.SELECT1B4,'G2',G.SELECT2B4                            \n");
		    sb.append("	     , 'G3',G.SELECT3B4,'G4',G.SELECT4B4,'G5',G.SELECT5B4,'G6',G.SELECT6B4                    \n");
		    sb.append("	        , 'G7',G.SELECT7B4, 'G8',G.SELECT8B4,'G9',G.SELECT9B4,'G10',G.SELECT10B4                    \n");
		    sb.append("	        , 'G11',G.SELECT11B4, 'G12',G.SELECT12B4,'G13',G.SELECT13B4,'G14',G.SELECT14B4                    \n");
		    sb.append("	        , 'G15',G.SELECT15B4, 'G16',G.SELECT16B4,'G17',G.SELECT17B4,'G18',G.SELECT18B4                    \n");
		    sb.append("	        , 'G19',G.SELECT19B4, 'G20',G.SELECT20B4) ,'')    SELECTBASE                    \n");
		    sb.append("	, NVL(DECODE(N2.CODE,'G1',G.TARGET1B4,'G2',G.TARGET2B4                            \n");
		    sb.append("	     , 'G3',G.TARGET3B4,'G4',G.TARGET4B4,'G5',G.TARGET5B4,'G6',G.TARGET6B4                    \n");
		    sb.append("	        , 'G7',G.TARGET7B4, 'G8',G.TARGET8B4,'G9',G.TARGET9B4,'G10',G.TARGET10B4                    \n");
		    sb.append("	        , 'G11',G.TARGET11B4, 'G12',G.TARGET12B4,'G13',G.TARGET13B4,'G14',G.TARGET14B4                    \n");
		    sb.append("	        , 'G15',G.TARGET15B4, 'G16',G.TARGET16B4,'G17',G.TARGET17B4,'G18',G.TARGET18B4                    \n");
		    sb.append("	        , 'G19',G.TARGET19B4, 'G20',G.TARGET20B4) ,'')    TARGETBASE                    \n");
		    sb.append("	, NVL(DECODE(N2.CODE,'G1',G.RESULT1B4,'G2',G.RESULT2B4                            \n");
		    sb.append("	     , 'G3',G.RESULT3B4,'G4',G.RESULT4B4,'G5',G.RESULT5B4,'G6',G.RESULT6B4                    \n");
		    sb.append("	     , 'G7',G.RESULT7B4, 'G8',G.RESULT8B4,'G9',G.RESULT9B4,'G10',G.RESULT10B4                    \n");
		    sb.append("	     , 'G11',G.RESULT11B4, 'G12',G.RESULT12B4,'G13',G.RESULT13B4,'G14',G.RESULT14B4                    \n");
		    sb.append("	     , 'G15',G.RESULT15B4, 'G16',G.RESULT16B4,'G17',G.RESULT17B4,'G18',G.RESULT18B4                    \n");
		    sb.append("	         , 'G19',G.RESULT19B4, 'G20',G.RESULT20B4) ,'')    RESULTBASE                    \n");
		    sb.append("	, T.ida2a2 TASKOID                \n");
		    sb.append("	, DECODE(N2.CODE, 'G1', SELECT1B4, 'G2', SELECT2B4, 'G3', SELECT3B4, 'G4', SELECT4B4, 'G5', SELECT5B4                \n");
		    sb.append("	        , 'G6', SELECT6B4, 'G7', SELECT7B4, 'G8', SELECT8B4, 'G9', SELECT9B4, 'G10', SELECT10B4                \n");
		    sb.append("	        , 'G11', SELECT11B4, 'G12', SELECT12B4, 'G13', SELECT13B4, 'G14', SELECT14B4, 'G15', SELECT15B4                \n");
		    sb.append("	        , 'G16', SELECT16B4, 'G17', SELECT17B4, 'G18', SELECT18B4, 'G19', SELECT19B4, 'G20', SELECT20B4, ''                \n");
		    sb.append("	        ) AS SELECT_VAL                \n");
		    sb.append("	    from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') T                         \n");
		    sb.append("	    , ProductProject P                             \n");
		    sb.append("	        , PROJECTASSSHEETLINK L                                \n");
		    sb.append("	        , KETASSESSSHEET A                                \n");
		    sb.append("	        , ASSSHEETGATECHKSHEETLINK S                                \n");
		    sb.append("	        , KETGATECHECKSHEET G                  \n");
		    sb.append("	        , NumberCode N2                \n");
		    sb.append("	        , NumberCode N3                \n");
		    sb.append("	    WHERE T.IDA3B4 = P.IDA2A2                            \n");
		    sb.append("	        AND P.IDA2A2 = L.IDA3B5                            \n");
		    sb.append("	        AND L.IDA3A5 = A.IDA2A2                            \n");
		    sb.append("	        AND A.IDA2A2 = S.idA3B5                            \n");
		    sb.append("	        AND S.IDA3A5 = G.IDA2A2                      \n");
		    sb.append("	        AND T.TASKTYPE = 'Gate'                            \n");
		    sb.append("	        AND N2.CODETYPE='GATELEVELNAME'                        \n");
		    sb.append("		AND A.STATESTATE='APPROVED'		\n");
		    sb.append("	        AND T.TASKTYPECATEGORY = REPLACE(N2.NAME,'Gate','')                  \n");
		    sb.append("	        AND N3.CODETYPE='ASSESSTARGETTYPE'                        \n");
		    sb.append("	        AND G.IDA3A4  = N3.IDA2A2                  \n");
		    sb.append("	) MAIN                \n");
		    sb.append("	, (                  \n");
		    sb.append("	        select LINK.* from (SELECT * FROM E3PSTask WHERE IDA2A2 = " + e3psTaskOID + ") T     \n");
		    sb.append("	            , KETGATEASSESSRESULT R                                              \n");
		    sb.append("		, ketGateAssRsltTaskLink gLink				\n");
		    sb.append("		, productProject p				\n");
		    sb.append("	            , KETAssessResultGateCheckLink LINK                       \n");
		    sb.append("	            where  t.ida2a2 = gLink.idA3B5				\n");
		    sb.append("			AND gLink.idA3A5 = r.ida2a2				\n");
		    sb.append("			AND t.ida3b4 =  p.ida2a2				\n");
		    // sb.append("			AND p.CHECKOUTSTATE <> 'c/o'				\n");
		    // sb.append("			AND p.LASTEST = 1					\n");
		    sb.append("		    AND R.IDA2A2 = LINK.idA3B5(+)                        \n");
		    sb.append("	            AND LINK.REV = " + gateDegree + "                    \n");
		    sb.append("	    ) LINK                  \n");
		    sb.append("	    WHERE 1=1                                   \n");
		    sb.append("	    AND MAIN.GATEOID = LINK.idA3A5(+)                      \n");
		    sb.append("	    AND MAIN.SELECT_VAL=1                        \n");
		    sb.append("	    ORDER BY MAIN.ORDERNO                        \n");

		} else {

		    sb.append("	   SELECT    NVL(N.NAME,'') TARGETTYPENAME                     \n");
		    sb.append("	       , G.CHECKSHEETNAME                            \n");
		    sb.append("	       , G.ACHIEVEBASE                                \n");
		    sb.append("	       , G.UNIT                                \n");
		    sb.append("	       , G.CRITERIA                                \n");
		    sb.append("	       , G.ORDERNO                                \n");
		    sb.append("	       , G.IDA2A2    GATEOID                            \n");
		    sb.append("	       , LINK.IDA3B5    RESULTOID                        \n");
		    sb.append("	       , LINK.IDA2A2    GATELINK_OID                        \n");
		    sb.append("	       , NVL(DECODE(N2.CODE,'G1',G.SELECT1B4,'G2',G.SELECT2B4                                \n");
		    sb.append("	            , 'G3',G.SELECT3B4,'G4',G.SELECT4B4,'G5',G.SELECT5B4,'G6',G.SELECT6B4                        \n");
		    sb.append("	               , 'G7',G.SELECT7B4, 'G8',G.SELECT8B4,'G9',G.SELECT9B4,'G10',G.SELECT10B4                        \n");
		    sb.append("	               , 'G11',G.SELECT11B4, 'G12',G.SELECT12B4,'G13',G.SELECT13B4,'G14',G.SELECT14B4                        \n");
		    sb.append("	               , 'G15',G.SELECT15B4, 'G16',G.SELECT16B4,'G17',G.SELECT17B4,'G18',G.SELECT18B4                        \n");
		    sb.append("	               , 'G19',G.SELECT19B4, 'G20',G.SELECT20B4) ,'')    SELECTBASE                        \n");
		    sb.append("	       , NVL(DECODE(N2.CODE,'G1',G.TARGET1B4,'G2',G.TARGET2B4                                \n");
		    sb.append("	            , 'G3',G.TARGET3B4,'G4',G.TARGET4B4,'G5',G.TARGET5B4,'G6',G.TARGET6B4                        \n");
		    sb.append("	               , 'G7',G.TARGET7B4, 'G8',G.TARGET8B4,'G9',G.TARGET9B4,'G10',G.TARGET10B4                        \n");
		    sb.append("	               , 'G11',G.TARGET11B4, 'G12',G.TARGET12B4,'G13',G.TARGET13B4,'G14',G.TARGET14B4                        \n");
		    sb.append("	               , 'G15',G.TARGET15B4, 'G16',G.TARGET16B4,'G17',G.TARGET17B4,'G18',G.TARGET18B4                        \n");
		    sb.append("	               , 'G19',G.TARGET19B4, 'G20',G.TARGET20B4) ,'')    TARGETBASE                        \n");
		    sb.append("	       , NVL(DECODE(N2.CODE,'G1',G.RESULT1B4,'G2',G.RESULT2B4                                \n");
		    sb.append("	            , 'G3',G.RESULT3B4,'G4',G.RESULT4B4,'G5',G.RESULT5B4,'G6',G.RESULT6B4                        \n");
		    sb.append("	            , 'G7',G.RESULT7B4, 'G8',G.RESULT8B4,'G9',G.RESULT9B4,'G10',G.RESULT10B4                        \n");
		    sb.append("	            , 'G11',G.RESULT11B4, 'G12',G.RESULT12B4,'G13',G.RESULT13B4,'G14',G.RESULT14B4                        \n");
		    sb.append("	            , 'G15',G.RESULT15B4, 'G16',G.RESULT16B4,'G17',G.RESULT17B4,'G18',G.RESULT18B4                        \n");
		    sb.append("	                , 'G19',G.RESULT19B4, 'G20',G.RESULT20B4) ,'')    RESULTBASE                        \n");
		    sb.append("	       , NVL(DECODE(N2.CODE, 'G1',LINK.sheetCheck1, 'G2',LINK.sheetCheck2                                \n");
		    sb.append("	            , 'G3',LINK.sheetCheck3, 'G4',LINK.sheetCheck4, 'G5',LINK.sheetCheck5, 'G6',LINK.sheetCheck6                        \n");
		    sb.append("	            , 'G7',LINK.sheetCheck7, 'G8',LINK.sheetCheck8, 'G9',LINK.sheetCheck9, 'G10',LINK.sheetCheck10                        \n");
		    sb.append("	            , 'G11',LINK.sheetCheck11, 'G12',LINK.sheetCheck12, 'G13',LINK.sheetCheck13, 'G14',LINK.sheetCheck14                        \n");
		    sb.append("	            , 'G15',LINK.sheetCheck15, 'G16',LINK.sheetCheck16, 'G17',LINK.sheetCheck17, 'G18',LINK.sheetCheck18                        \n");
		    sb.append("	                , 'G19',LINK.sheetCheck19, 'G20',LINK.sheetCheck20) ,'')    CHECKBASE                       \n");
		    sb.append("	       , NVL(DECODE(N2.CODE, 'G1',LINK.sheetResult1, 'G2',LINK.sheetResult2                                                \n");
		    sb.append("	            , 'G3',LINK.sheetResult3, 'G4',LINK.sheetResult4, 'G5',LINK.sheetResult5, 'G6',LINK.sheetResult6                    \n");
		    sb.append("	            , 'G7',LINK.sheetResult7, 'G8',LINK.sheetResult8, 'G9',LINK.sheetResult9, 'G10',LINK.sheetResult10                    \n");
		    sb.append("	            , 'G11',LINK.sheetResult11, 'G12',LINK.sheetResult12, 'G13',LINK.sheetResult13, 'G14',LINK.sheetResult14                    \n");
		    sb.append("	            , 'G15',LINK.sheetResult15, 'G16',LINK.sheetResult16, 'G17',LINK.sheetResult17, 'G18',LINK.sheetResult18                    \n");
		    sb.append("	                , 'G19',LINK.sheetResult19, 'G20',LINK.sheetResult20) ,'')    CHECKRESULTBASE,LINK.MANAGERDEPTCODE   ,LINK.MANAGERDEPTNAME                    \n");
		    sb.append("	   from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') T                                \n");
		    sb.append("	       , NumberCode N2                                    \n");
		    sb.append("	       , KETGATEASSESSRESULT R                    \n");
		    sb.append("		, ketGateAssRsltTaskLink gLink				\n");
		    sb.append("		, productProject p				\n");
		    sb.append("	       , KETAssessResultGateCheckLink LINK                        \n");
		    sb.append("	       , KETGATECHECKSHEET G                          \n");
		    sb.append("	       , NumberCode N                          \n");
		    sb.append("	   WHERE T.TASKTYPE = 'Gate'                                \n");
		    sb.append("	       AND N2.CODETYPE='GATELEVELNAME'                            \n");
		    sb.append("	       AND T.TASKTYPECATEGORY = REPLACE(N2.NAME(+),'Gate','')                        \n");
		    sb.append("	       AND  t.ida2a2 = gLink.idA3B5				\n");
		    sb.append("		AND gLink.idA3A5 = r.ida2a2				\n");
		    sb.append("		AND t.ida3b4 =  p.ida2a2				\n");
		    // sb.append("		AND p.CHECKOUTSTATE <> 'c/o'				\n");
		    // sb.append("		AND p.LASTEST = 1					\n");
		    sb.append("	       AND R.IDA2A2 = LINK.IDA3B5                    \n");
		    sb.append("	       AND LINK.IDA3A5 = G.IDA2A2                    \n");
		    sb.append("	       AND LINK.REV = " + gateDegree + "                    \n");
		    sb.append("	       AND G.idA3A4 = N.IDA2A2(+)                     \n");
		    sb.append("	AND EXISTS						\n");
		    sb.append("		(SELECT 'T'					\n");
		    sb.append("		   FROM PRODUCTPROJECT PJT, E3PSTASK TASK	\n");
		    sb.append("		WHERE TASK.IDA3B4 = PJT.IDA2A2			\n");
		    sb.append("		AND PJT.IDA2A2 = " + CommonUtil.getOIDLongValue(project) + "		\n");
		    sb.append("		AND TASK.IDA2A2 = t.IDA2A2)			\n");

		    sb.append("	  ORDER BY G.ORDERNO                     \n");
		}
		rs = stmt.executeQuery(sb.toString());
		// Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

		while (rs.next()) {

		    GateCheckSheetDTO rsDto = new GateCheckSheetDTO();

		    rsDto.setGateCheckSheetOid(rs.getString("GATEOID"));
		    rsDto.setResultOid(rs.getString("RESULTOID"));
		    rsDto.setTargetTypeName(rs.getString("TARGETTYPENAME"));
		    rsDto.setCheckSheetName(rs.getString("CHECKSHEETNAME"));
		    rsDto.setAchieveBase(rs.getString("ACHIEVEBASE"));
		    rsDto.setUnit(rs.getString("UNIT"));
		    rsDto.setCriteria(rs.getString("CRITERIA"));
		    rsDto.setTaskTarget(rs.getString("TARGETBASE"));
		    rsDto.setTaskResult(rs.getString("CHECKRESULTBASE"));
		    rsDto.setTaskCheck(rs.getString("CHECKBASE"));
		    rsDto.setTaskSelect(rs.getString("SELECTBASE"));
		    rsDto.setGateLinkOid(rs.getString("GATELINK_OID"));
		    rsDto.setManagerDeptCode(rs.getString("MANAGERDEPTCODE"));
		    rsDto.setManagerDeptName(rs.getString("MANAGERDEPTNAME"));

		    gateCheckSheetList.add(rsDto);
		}
	    }

	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return gateCheckSheetList;
    }

    /**
     * 프로젝트에 링크된 평가시트를 조회하는 쿼리스펙을 리턴하는 함수
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getQueryAssessSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryAssessSheet(String taskOid) throws Exception {

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_link = query.appendClassList(ProjectAssSheetLink.class, false);
	int idx_check = query.appendClassList(AssessSheet.class, true);

	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;

	ca2 = new ClassAttribute(AssessSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(ProjectAssSheetLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	if (taskOid != null) {
	    E3PSTask eTask = (E3PSTask) CommonUtil.getObject(taskOid);
	    E3PSProject tProject = (E3PSProject) eTask.getProject();
	    sc = new SearchCondition(ProjectAssSheetLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, tProject.getPersistInfo()
		    .getObjectIdentifier().getId());
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	return query;
    }

    /**
     * 평가시트 하위의 평가항목들을 조회하는 쿼리스펙을 리턴하는 함수
     * 
     * @param assOid
     * @param GateCheckSheetDTO
     * @return
     * @throws Exception
     * @메소드명 : getQueryGateCheckSheet
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryGateCheckSheet(AssessSheet sheet) throws Exception {

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	// int idx_assess = query.appendClassList(AssessSheet.class, true);
	int idx_link = query.appendClassList(AssSheetGateChkSheetLink.class, false);
	int idx_check = query.appendClassList(GateCheckSheet.class, true);
	int idx_number = query.appendClassList(NumberCode.class, false);

	// ClassAttribute ca1 = null;
	ClassAttribute ca2 = null;
	ClassAttribute ca3 = null;
	// ClassAttribute ca4 = null;
	// ClassAttribute ca5 = null;
	// ClassAttribute ca6 = null;
	ClassAttribute ca7 = null;
	ClassAttribute ca8 = null;

	// ca1 = new ClassAttribute(AssessSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca2 = new ClassAttribute(GateCheckSheet.class, "thePersistInfo.theObjectIdentifier.id");
	ca3 = new ClassAttribute(AssSheetGateChkSheetLink.class, "roleAObjectRef.key.id");
	// ca4 = new ClassAttribute(AssSheetGateChkSheetLink.class, "roleBObjectRef.key.id");
	ca7 = new ClassAttribute(GateCheckSheet.class, "targetTypeReference.key.id");
	ca8 = new ClassAttribute(NumberCode.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_check, idx_link });

	sc = new SearchCondition(ca7, SearchCondition.EQUAL, ca8);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idx_check, idx_number });

	if (sheet != null) {
	    long pjtOidLong = CommonUtil.getOIDLongValue(sheet);
	    sc = new SearchCondition(AssSheetGateChkSheetLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOidLong);
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_link });
	}

	SearchUtil.setOrderBy(query, GateCheckSheet.class, idx_check, GateCheckSheet.CHECK_SHEET_NAME, true);

	return query;
    }

    /**
     * 프로젝트에 링크된 평가시트를 조회하는 쿼리스펙을 리턴하는 함수
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getQueryAssessResultLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private QuerySpec getQueryAssessResultLink(GateAssessResult result, String outputOid) throws Exception {

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_result = query.appendClassList(GateAssessResult.class, false);
	int idx_outputLink = query.appendClassList(AssessResultOutputLink.class, false);
	int idx_output = query.appendClassList(ProjectOutput.class, true);

	ClassAttribute ca2 = new ClassAttribute(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca3 = new ClassAttribute(AssessResultOutputLink.class, "roleAObjectRef.key.id");
	ClassAttribute ca4 = new ClassAttribute(ProjectOutput.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca5 = new ClassAttribute(AssessResultOutputLink.class, "roleBObjectRef.key.id");

	sc = new SearchCondition(ca2, SearchCondition.EQUAL, ca3);
	query.appendWhere(sc, new int[] { idx_result, idx_outputLink });

	sc = new SearchCondition(ca4, SearchCondition.EQUAL, ca5);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idx_output, idx_outputLink });

	if (outputOid != null) {
	    ProjectOutput pjtOutObject = (ProjectOutput) CommonUtil.getObject(outputOid);
	    sc = new SearchCondition(AssessResultOutputLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, result.getPersistInfo()
		    .getObjectIdentifier().getId());
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_output });

	    sc = new SearchCondition(AssessResultOutputLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, pjtOutObject
		    .getPersistInfo().getObjectIdentifier().getId());
	    query.appendAnd();
	    query.appendWhere(sc, new int[] { idx_outputLink });
	}

	return query;
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 저장처리(산출물 저장) AssessResultOutputLink 링크 모두 삭제 + GateAssessResult와 산출물ProjectOutput 객체간 링크 생성하기
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : saveTaskOutputResult
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveTaskOutputResult(String oid, ProjectTaskDTO dto) throws Exception {

	String tgOutputOids[] = dto.getTgOutputOids();
	String tgTaskChecks[] = dto.getTgTaskChecks();
	String tgTaskComments[] = dto.getTgTaskComments();

	E3PSTask task = null;
	try {
	    ReferenceFactory rf = new ReferenceFactory();
	    task = (E3PSTask) rf.getReference(oid).getObject();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	String taskTypeCategory = task.getTaskTypeCategory();
	boolean isNonTarget = taskTypeCategory.equals("p-3") || taskTypeCategory.equals("p-4.1") || taskTypeCategory.equals("p-4.2");
	if (isNonTarget) {
	    QueryResult qr = ProjectOutputHelper.manager.getOutputByProject(task.getProject());
	    ProjectOutput output = null;
	    while (qr.hasMoreElements()) {
		Object obj[] = (Object[]) qr.nextElement();
		output = (ProjectOutput) obj[0];
	    }
	    tgOutputOids = new String[] { CommonUtil.getOIDString(output) };
	    tgTaskChecks = new String[] { "G" };
	    tgTaskComments = new String[] { "시스템자동설정" };
	}

	// 1. E3PSTask 객체에 링크된 GateAssessResult 객체가 있는지 조회한다 없는 경우 생성해준다
	GateAssessResult gAssRslt = checkGateResultExist(oid);

	if (gAssRslt != null) {
	    // GateAssessResult 와 E3PSTask와의 관계를 확인하여 없으면 링크를 걸어준다
	    QueryResult r = PersistenceHelper.navigate(gAssRslt, "task", GateAssRsltTaskLink.class, false);
	    boolean isTaskLink = false;
	    if (r.hasMoreElements()) {
		isTaskLink = true;
	    }
	    if (!isTaskLink) {
		GateAssRsltTaskLink assRstGateLink = GateAssRsltTaskLink.newGateAssRsltTaskLink(gAssRslt, task);
		assRstGateLink = (GateAssRsltTaskLink) PersistenceHelper.manager.save(assRstGateLink);
	    }

	    // 3. AssessResultOutputLink 링크 모두 삭제
	    // deleteResultOutputLink(gAssRslt);
	    AssessResultOutputLink assOutLink = null;
	    r = PersistenceHelper.navigate(gAssRslt, "output", AssessResultOutputLink.class, false);
	    while (r.hasMoreElements()) {
		assOutLink = (AssessResultOutputLink) r.nextElement();
		if (dto.getRev() == assOutLink.getRev()) {
		    PersistenceHelper.manager.delete(assOutLink);
		}

	    }
	}

	int rev = this.getMaxGateDegree(oid) + 1;

	// 4. GateAssessResult와 산출물ProjectOutput 객체간 링크 생성하기
	if (tgOutputOids != null) {
	    for (int i = 0; i < tgOutputOids.length; i++) {
		String outputOidPer = tgOutputOids[i];
		ProjectOutput output = (ProjectOutput) CommonUtil.getObject(outputOidPer);
		ProjectOutputData outputData = new ProjectOutputData(output);
		String objType = "";
		if (output.getObjType() != null) {
		    objType = output.getObjType();
		}
		boolean isEtc = "ETC".equals(objType);

		// 각 산출물의 산출물명, 작성자, 상태를 가져온다
		String outputNameStr = "";
		String outputCreatorStr = "";
		String outputStateStr = "";
		String outputOidStr = "";

		if (!isEtc && outputData.isRegistry) {
		    ObjectData data = null;

		    if ("ECO".equals(objType)) {
			KETProdChangeOrder prodChangeOrderObj = null;
			KETMoldChangeOrder moldChangeOrderObj = null;
			QueryResult qr = PersistenceHelper.manager.navigate(output, "change", KETProdChangeOrderOutputLink.class);

			while (qr.hasMoreElements()) {
			    prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
			}

			qr = PersistenceHelper.manager.navigate(output, "change", KETMoldChangeOrderOutputLink.class);

			while (qr.hasMoreElements()) {
			    moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
			}

			if (prodChangeOrderObj != null) {
			    outputOidStr = CommonUtil.getOIDLongValue2Str(prodChangeOrderObj);
			    outputNameStr = prodChangeOrderObj.getEcoName();
			    outputCreatorStr = prodChangeOrderObj.getCreatorFullName();
			    outputStateStr = prodChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			} else if (moldChangeOrderObj != null) {
			    outputOidStr = CommonUtil.getOIDLongValue2Str(moldChangeOrderObj);
			    outputNameStr = moldChangeOrderObj.getEcoName();
			    outputCreatorStr = moldChangeOrderObj.getCreatorFullName();
			    outputStateStr = moldChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			} else {

			}

			// out.println(isSecu);
		    } else if ("TRY".equals(objType)) {

			KETTryMold moldTryConditionObj = null;
			KETTryPress pressTryConditionObj = null;

			QueryResult qr = PersistenceHelper.manager.navigate(output, "tryMold", KETTryMoldOutputLink.class);

			while (qr.hasMoreElements()) {
			    moldTryConditionObj = (KETTryMold) qr.nextElement();
			}

			qr = PersistenceHelper.manager.navigate(output, "tryPress", KETTryPressOutputLink.class);

			while (qr.hasMoreElements()) {
			    pressTryConditionObj = (KETTryPress) qr.nextElement();
			}

			outputNameStr = outputData.tryCondition.getName();
			if (moldTryConditionObj != null) {
			    outputOidStr = CommonUtil.getOIDLongValue2Str(moldTryConditionObj);
			    outputNameStr = moldTryConditionObj.getName();
			    outputCreatorStr = moldTryConditionObj.getCreatorFullName();
			    outputStateStr = moldTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			} else if (pressTryConditionObj != null) {
			    outputOidStr = CommonUtil.getOIDLongValue2Str(pressTryConditionObj);
			    outputNameStr = pressTryConditionObj.getVersionDisplayIdentifier().toString();
			    outputCreatorStr = pressTryConditionObj.getCreatorFullName();
			    outputStateStr = pressTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			}

		    } else if ("QLP".equals(objType)) {

			ext.ket.dqm.entity.KETDqmDTO dqmDto = ext.ket.project.task.service.ProjectTaskCompHelper.service
			        .getOutputQLP(output);
			String raiseOidStr = dqmDto.getRaiseOid();
			KETDqmRaise dqmRaiseObj = (raiseOidStr == null || "".equals(raiseOidStr)) ? null : (KETDqmRaise) CommonUtil
			        .getObject(raiseOidStr);
			KETDqmHeader dqmHeaderObj = (dqmDto == null || "".equals(dqmDto.getOid())) ? null : (KETDqmHeader) CommonUtil
			        .getObject(dqmDto.getOid());

			outputOidStr = CommonUtil.getOIDLongValue2Str(dqmHeaderObj);
			outputNameStr = dqmHeaderObj.getProblem();
			outputCreatorStr = dqmRaiseObj.getCreatorFullName();
			outputStateStr = dqmHeaderObj.getDqmStateName();

		    } else if ("ETC".equals(objType) || "COST".equals(objType) || "SALES".equals(objType)) { // ETC인 경우
			outputNameStr = outputData.name;
			WTUser duser = (WTUser) output.getOwner().getPrincipal();
			if (duser != null) {
			    PeopleData peopleData = new PeopleData(duser);
			    outputCreatorStr = peopleData.name;
			}
			outputStateStr = "";
		    } else { // 문서, 도면인 경우
			     // out.println(outputData.LastDocument);
			if (outputData.LastDocument instanceof KETProjectDocument) {
			    outputNameStr = StringUtil.checkNull(((KETProjectDocument) outputData.LastDocument).getTitle());
			} else if (outputData.LastDocument instanceof EPMDocument) {
			    outputNameStr = StringUtil.checkNull(((EPMDocument) outputData.LastDocument).getName());
			}
			outputOidStr = CommonUtil.getOIDLongValue2Str(outputData.currentDocument);
			outputCreatorStr = outputData.currentDocument.getCreatorFullName();
			outputStateStr = outputData.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);

		    }

		}
		if ("ETC".equals(objType)) { // ETC인 경우
		    outputNameStr = outputData.name;
		    WTUser duser = (WTUser) output.getOwner().getPrincipal();
		    if (duser != null) {
			PeopleData peopleData = new PeopleData(duser);
			outputCreatorStr = peopleData.name;
		    }
		    outputStateStr = "";
		}

		AssessResultOutputLink assRstOutLink = null;
		// query = getQueryAssessResultLink(gAssRslt, outputOidPer);
		// qr = PersistenceHelper.manager.find((StatementSpec) query);
		// if(qr.hasMoreElements()) {
		// assRstOutLink = (AssessResultOutputLink)qr.nextElement();
		// }
		//
		// //링크가 없을 경우 링크를 생성해 준다
		// if(assRstOutLink==null) {
		// assRstOutLink = AssessResultOutputLink.newAssessResultOutputLink(gAssRslt, pjtOutputObj);
		// }

		// 현재 모든 링크를 저장하기위해 기존 링크를 모두 삭제후 링크를 추가하는 로직.
		assRstOutLink = AssessResultOutputLink.newAssessResultOutputLink(gAssRslt, output);
		assRstOutLink.setOutputCheck(tgTaskChecks[i]);
		assRstOutLink.setOutputComment(tgTaskComments[i]);
		assRstOutLink.setOutputName(outputNameStr);
		assRstOutLink.setOutputCharge(outputCreatorStr);
		assRstOutLink.setOutputState(outputStateStr);
		assRstOutLink.setOutputOid(outputOidStr);
		assRstOutLink.setRev(rev);
		assRstOutLink = (AssessResultOutputLink) PersistenceHelper.manager.save(assRstOutLink);
	    }
	}

    }

    /**
     * 프로젝트 Gate 테스크 산출물 , 평가항목, 품질문제 모두 저장하기
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : saveTaskRelatedAllResult
     * @작성자 : jackey88
     * @작성일 : 2014. 11. 21.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveTaskRelatedAllResult(String oid, ProjectTaskDTO dto) throws Exception {
	int rev = this.getMaxGateDegree(oid);
	dto.setRev(rev);

	// 산출물저장
	saveTaskOutputResult(oid, dto);

	// 평가항목 저장
	saveTaskGateCheckResult(oid, dto);

	// 품질문제 저장
	saveTaskQualityResult(oid, dto);

    }

    /**
     * 프로젝트 Gate 테스크 산출물 , 평가항목, 품질문제 모두 rev 생성
     * 
     * @throws Exception
     * 
     * @메소드명 : createDegreeTaskRelatedAllResult
     * @작성자 : 황정태
     * @작성일 : 2017. 3. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public int createDegreeTaskRelatedAllResult(String oid) throws Exception {

	Transaction transaction = new Transaction();
	try {
	    transaction.start();

	    E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);// 1. target task를 가져온다

	    GateAssessResult gateRslt = this.getAssesResultByTask(task);

	    LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		    "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
	    gateRslt = (GateAssessResult) LifeCycleHelper.service.reassign(gateRslt, paramLifeCycleTemplateReference);

	    createDegreeTaskOutputResult(gateRslt); // 산출물 차수생성

	    AssessSheet assSheet = null;

	    QuerySpec query = getQueryAssessSheet(oid);
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		assSheet = (AssessSheet) objArr[0];
	    }

	    String assSheetOid = CommonUtil.getOIDString(assSheet);

	    createDegreeTaskGateCheckResult(gateRslt, assSheetOid); // 평가항목 차수생성

	    createDegreeTaskQualityResult(gateRslt, assSheetOid);// 품질문제 차수 생성

	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    transaction.rollback();
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

	return this.getMaxGateDegree(oid);

    }

    public void createDegreeTaskOutputResult(GateAssessResult gateRslt) throws Exception {

	QueryResult qr = null;

	AssessResultOutputLink assOutLink = null;
	AssessResultOutputLink newAssOutLink = null;

	QuerySpec query = new QuerySpec();
	int idxA = query.appendClassList(GateAssessResult.class, false);
	int idxB = query.appendClassList(AssessResultOutputLink.class, false);

	ClassAttribute rev = new ClassAttribute(AssessResultOutputLink.class, "rev");
	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, rev);
	query.appendSelect(max, new int[] { idxB }, false);

	query.appendWhere(
	        new SearchCondition(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id", "=", CommonUtil
	                .getOIDLongValue(gateRslt)), new int[] { idxA });
	query.appendAnd();
	ClassAttribute gateOid = new ClassAttribute(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute roleA = new ClassAttribute(AssessResultOutputLink.class, "roleAObjectRef.key.id");
	SearchCondition sc = new SearchCondition(gateOid, "=", roleA);
	query.appendWhere(sc, new int[] { idxA, idxB });
	query.setAdvancedQueryEnabled(true);
	QueryResult rs = PersistenceServerHelper.manager.query((StatementSpec) query);

	int old_rev = 0;
	int new_rev = 0;

	while (rs.hasMoreElements()) {
	    Object[] object = (Object[]) rs.nextElement();
	    old_rev = Integer.parseInt(String.valueOf(object[0]));
	}

	new_rev = old_rev + 1;

	if (gateRslt != null) {
	    qr = PersistenceHelper.navigate(gateRslt, "output", AssessResultOutputLink.class, false);// 3. GateAssessResult에 따른
	    // AssessResultOutputLink를 가져와서 복사생성한다.
	    // rev ++
	    while (qr.hasMoreElements()) {

		assOutLink = (AssessResultOutputLink) qr.nextElement();
		if (assOutLink.getRev() == old_rev) {
		    newAssOutLink = AssessResultOutputLink.newAssessResultOutputLink(gateRslt, assOutLink.getOutput());

		    newAssOutLink.setOutputCharge(assOutLink.getOutputCharge());
		    newAssOutLink.setOutputCheck(assOutLink.getOutputCheck());
		    newAssOutLink.setOutputComment(assOutLink.getOutputComment());
		    newAssOutLink.setOutputName(assOutLink.getOutputName());
		    newAssOutLink.setOutputState(assOutLink.getOutputState());
		    newAssOutLink.setOutputOid(CommonUtil.getOIDString(assOutLink.getOutput()));
		    newAssOutLink.setRev(new_rev);
		    PersistenceServerHelper.manager.insert(newAssOutLink);
		}

	    }
	}
    }

    public void createDegreeTaskGateCheckResult(GateAssessResult gateRslt, String assSheetOid) throws Exception {

	QueryResult qr = null;

	AssessResultGateCheckLink GateCheckLink = null;
	AssessResultGateCheckLink newGateCheckLink = null;

	QuerySpec query = new QuerySpec();
	int idxA = query.appendClassList(GateAssessResult.class, false);
	int idxB = query.appendClassList(AssessResultGateCheckLink.class, false);

	ClassAttribute rev = new ClassAttribute(AssessResultGateCheckLink.class, "rev");
	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, rev);
	query.appendSelect(max, new int[] { idxB }, false);

	query.appendWhere(
	        new SearchCondition(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id", "=", CommonUtil
	                .getOIDLongValue(gateRslt)), new int[] { idxA });
	query.appendAnd();
	ClassAttribute gateOid = new ClassAttribute(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute roleB = new ClassAttribute(AssessResultGateCheckLink.class, "roleBObjectRef.key.id");
	SearchCondition sc = new SearchCondition(gateOid, "=", roleB);
	query.appendWhere(sc, new int[] { idxA, idxB });
	query.setAdvancedQueryEnabled(true);
	QueryResult rs = PersistenceServerHelper.manager.query((StatementSpec) query);

	int old_rev = 0;
	int new_rev = 0;

	while (rs.hasMoreElements()) {
	    Object[] object = (Object[]) rs.nextElement();
	    old_rev = Integer.parseInt(String.valueOf(object[0]));
	}

	new_rev = old_rev + 1;

	if (gateRslt != null) {
	    qr = PersistenceHelper.navigate(gateRslt, "check", AssessResultGateCheckLink.class, false);// 3. GateAssessResult에 따른
	    // AssessResultOutputLink를 가져와서
	    // 복사생성한다. rev ++
	    while (qr.hasMoreElements()) {

		GateCheckLink = (AssessResultGateCheckLink) qr.nextElement();
		if (GateCheckLink.getRev() == old_rev) {
		    newGateCheckLink = AssessResultGateCheckLink.newAssessResultGateCheckLink(GateCheckLink.getCheck(), gateRslt);

		    newGateCheckLink.setSheetCheck(GateCheckLink.getSheetCheck());
		    newGateCheckLink.setSheetCheck1(GateCheckLink.getSheetCheck1());
		    newGateCheckLink.setSheetCheck2(GateCheckLink.getSheetCheck2());
		    newGateCheckLink.setSheetCheck3(GateCheckLink.getSheetCheck3());
		    newGateCheckLink.setSheetCheck4(GateCheckLink.getSheetCheck4());
		    newGateCheckLink.setSheetCheck5(GateCheckLink.getSheetCheck5());
		    newGateCheckLink.setSheetCheck6(GateCheckLink.getSheetCheck6());
		    newGateCheckLink.setSheetCheck7(GateCheckLink.getSheetCheck7());
		    newGateCheckLink.setSheetCheck8(GateCheckLink.getSheetCheck8());
		    newGateCheckLink.setSheetCheck9(GateCheckLink.getSheetCheck9());
		    newGateCheckLink.setSheetCheck10(GateCheckLink.getSheetCheck10());
		    newGateCheckLink.setSheetCheck11(GateCheckLink.getSheetCheck11());
		    newGateCheckLink.setSheetCheck12(GateCheckLink.getSheetCheck12());
		    newGateCheckLink.setSheetCheck13(GateCheckLink.getSheetCheck13());
		    newGateCheckLink.setSheetCheck14(GateCheckLink.getSheetCheck14());
		    newGateCheckLink.setSheetCheck15(GateCheckLink.getSheetCheck15());
		    newGateCheckLink.setSheetCheck16(GateCheckLink.getSheetCheck16());
		    newGateCheckLink.setSheetCheck17(GateCheckLink.getSheetCheck17());
		    newGateCheckLink.setSheetCheck18(GateCheckLink.getSheetCheck18());
		    newGateCheckLink.setSheetCheck19(GateCheckLink.getSheetCheck19());
		    newGateCheckLink.setSheetCheck20(GateCheckLink.getSheetCheck20());

		    newGateCheckLink.setSheetResult(GateCheckLink.getSheetResult());
		    newGateCheckLink.setSheetResult1(GateCheckLink.getSheetResult1());
		    newGateCheckLink.setSheetResult2(GateCheckLink.getSheetResult2());
		    newGateCheckLink.setSheetResult3(GateCheckLink.getSheetResult3());
		    newGateCheckLink.setSheetResult4(GateCheckLink.getSheetResult4());
		    newGateCheckLink.setSheetResult5(GateCheckLink.getSheetResult5());
		    newGateCheckLink.setSheetResult6(GateCheckLink.getSheetResult6());
		    newGateCheckLink.setSheetResult7(GateCheckLink.getSheetResult7());
		    newGateCheckLink.setSheetResult8(GateCheckLink.getSheetResult8());
		    newGateCheckLink.setSheetResult9(GateCheckLink.getSheetResult9());
		    newGateCheckLink.setSheetResult10(GateCheckLink.getSheetResult10());
		    newGateCheckLink.setSheetResult11(GateCheckLink.getSheetResult11());
		    newGateCheckLink.setSheetResult12(GateCheckLink.getSheetResult12());
		    newGateCheckLink.setSheetResult13(GateCheckLink.getSheetResult13());
		    newGateCheckLink.setSheetResult14(GateCheckLink.getSheetResult14());
		    newGateCheckLink.setSheetResult15(GateCheckLink.getSheetResult15());
		    newGateCheckLink.setSheetResult16(GateCheckLink.getSheetResult16());
		    newGateCheckLink.setSheetResult17(GateCheckLink.getSheetResult17());
		    newGateCheckLink.setSheetResult18(GateCheckLink.getSheetResult18());
		    newGateCheckLink.setSheetResult19(GateCheckLink.getSheetResult19());
		    newGateCheckLink.setSheetResult20(GateCheckLink.getSheetResult20());

		    newGateCheckLink.setRev(new_rev);

		    PersistenceServerHelper.manager.insert(newGateCheckLink);

		    GateCheckLink.setAssessSheetOid(assSheetOid);
		    PersistenceServerHelper.manager.update(GateCheckLink);
		}

	    }
	}
    }

    public void createDegreeTaskQualityResult(GateAssessResult gateRslt, String assSheetOid) throws Exception {
	QueryResult qr = null;

	AssessResultDqmHeaderLink assRstDqmLink = null;
	AssessResultDqmHeaderLink newAssRstDqmLink = null;

	QuerySpec query = new QuerySpec();
	int idxA = query.appendClassList(GateAssessResult.class, false);
	int idxB = query.appendClassList(AssessResultDqmHeaderLink.class, false);

	ClassAttribute rev = new ClassAttribute(AssessResultDqmHeaderLink.class, "rev");
	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, rev);
	query.appendSelect(max, new int[] { idxB }, false);

	query.appendWhere(
	        new SearchCondition(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id", "=", CommonUtil
	                .getOIDLongValue(gateRslt)), new int[] { idxA });
	query.appendAnd();
	ClassAttribute gateOid = new ClassAttribute(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute roleB = new ClassAttribute(AssessResultDqmHeaderLink.class, "roleBObjectRef.key.id");
	SearchCondition sc = new SearchCondition(gateOid, "=", roleB);
	query.appendWhere(sc, new int[] { idxA, idxB });
	query.setAdvancedQueryEnabled(true);
	QueryResult rs = PersistenceServerHelper.manager.query((StatementSpec) query);

	int old_rev = 0;
	int new_rev = 0;

	while (rs.hasMoreElements()) {
	    Object[] object = (Object[]) rs.nextElement();
	    if (object[0] != null) {
		old_rev = Integer.parseInt(String.valueOf(object[0]));
	    }
	}

	new_rev = old_rev + 1;

	if (gateRslt != null) {
	    qr = PersistenceHelper.navigate(gateRslt, "dqm", AssessResultDqmHeaderLink.class, false);// 3. GateAssessResult에 따른
		                                                                                     // AssessResultDqmHeaderLink를 가져와서
		                                                                                     // 복사생성한다. rev ++
	    while (qr.hasMoreElements()) {

		assRstDqmLink = (AssessResultDqmHeaderLink) qr.nextElement();
		if (assRstDqmLink.getRev() == old_rev) {
		    newAssRstDqmLink = AssessResultDqmHeaderLink.newAssessResultDqmHeaderLink(assRstDqmLink.getDqm(), gateRslt);

		    newAssRstDqmLink.setRev(new_rev);
		    newAssRstDqmLink.setDqmCheck(assRstDqmLink.getDqmCheck());
		    newAssRstDqmLink.setDqmCompDate(assRstDqmLink.getDqmCompDate());
		    newAssRstDqmLink.setDqmState(assRstDqmLink.getDqm().getDqmStateName());

		    PersistenceServerHelper.manager.insert(newAssRstDqmLink);

		}
	    }
	}

    }

    /**
     * 
     * 
     * @param WTChangeOrder2
     * @param output
     * @param type
     * @return
     * @throws Exception
     * @메소드명 : updateEcoProjectOutputLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateEcoProjectOutputLink(WTChangeOrder2 obj, ProjectOutput output, String type) throws Exception {
	KETProdChangeOrderOutputLink changeOutputLink = null;
	KETMoldChangeOrderOutputLink changeOutputLink2 = null;
	try {

	    if (obj != null && output != null) {
		if ("1".equals(type)) {

		    KETProdChangeOrder prodChangeOrderObj = (KETProdChangeOrder) obj;
		    changeOutputLink = KETProdChangeOrderOutputLink.newKETProdChangeOrderOutputLink(prodChangeOrderObj, output);
		    changeOutputLink = (KETProdChangeOrderOutputLink) PersistenceHelper.manager.save(changeOutputLink);
		} else {
		    KETMoldChangeOrder moldChangeOrderObj = (KETMoldChangeOrder) obj;
		    changeOutputLink2 = KETMoldChangeOrderOutputLink.newKETMoldChangeOrderOutputLink(moldChangeOrderObj, output);
		    changeOutputLink2 = (KETMoldChangeOrderOutputLink) PersistenceHelper.manager.save(changeOutputLink2);
		}
		E3PSTask task = (E3PSTask) output.getTask();
		ProjectTaskHelper.updateCompletionFromOutput(task);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 산출물 품질문제 등록시 링크 연결
     * 
     * @param WTChangeOrder2
     * @param output
     * @param type
     * @return
     * @throws Exception
     * @메소드명 : updateDqmProjectOutputLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateDqmProjectOutputLink(KETDqmRaise dqm, ProjectOutput output) throws Exception {
	KETDqmRaiseOutputLink raiseOutputLink = null;
	try {
	    if (dqm != null && output != null) {

		raiseOutputLink = KETDqmRaiseOutputLink.newKETDqmRaiseOutputLink(dqm, output);
		raiseOutputLink = (KETDqmRaiseOutputLink) PersistenceHelper.manager.save(raiseOutputLink);

		E3PSTask task = (E3PSTask) output.getTask();
		ProjectTaskHelper.updateCompletionFromOutput(task);

	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 품질문제 종결시 완료처리
     * 
     * @param WTChangeOrder2
     * @param output
     * @param type
     * @return
     * @throws Exception
     * @메소드명 : completeDqmProjectOutput
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void completeDqmProjectOutput(KETDqmRaise dqm) throws Exception {
	KETDqmRaiseOutputLink raiseOutputLink = null;
	try {
	    if (dqm != null) {
		QueryResult qr = PersistenceHelper.manager.navigate(dqm, "output", KETDqmRaiseOutputLink.class);
		if (qr != null) {
		    while (qr.hasMoreElements()) {
			ProjectOutput output = (ProjectOutput) qr.nextElement();

			output.setCompletion(100);

			output = (ProjectOutput) PersistenceHelper.manager.save(output);
			E3PSTask task = (E3PSTask) output.getTask();

			ProjectTaskHelper.manager.updateCompletionFromOutput(task);

		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 저장처리(평가항목 저장) AssessResultOutputLink 링크 모두 삭제 + GateAssessResult와 산출물ProjectOutput 객체간 링크 생성하기
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : saveTaskGateCheckResult
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveTaskGateCheckResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = null;
	try {
	    ReferenceFactory rf = new ReferenceFactory();
	    task = (E3PSTask) rf.getReference(oid).getObject();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	String tgGateResults[] = dto.getTgGateResults();
	String tgGateCheckOids[] = dto.getTgGateCheckOids();
	String tgGateChecks[] = dto.getTgGateChecks();
	String managerDeptOids[] = dto.getManagerDeptOids();

	// 1. E3PSTask 객체에 링크된 GateAssessResult 객체가 있는지 조회한다 없는 경우 생성해준다
	GateAssessResult gAssRslt = checkGateResultExist(oid);

	if (gAssRslt != null) {
	    GateAssRsltTaskLink taskLink = null;
	    // GateAssessResult 와 E3PSTask와의 관계를 확인하여 없으면 링크를 걸어준다
	    QueryResult r = PersistenceHelper.navigate(gAssRslt, "task", GateAssRsltTaskLink.class, false);
	    boolean isTaskLink = false;
	    if (r.hasMoreElements()) {
		isTaskLink = true;
	    }
	    if (!isTaskLink) {
		GateAssRsltTaskLink assRstGateLink = GateAssRsltTaskLink.newGateAssRsltTaskLink(gAssRslt, task);
		assRstGateLink = (GateAssRsltTaskLink) PersistenceHelper.manager.save(assRstGateLink);
	    }

	    // 5. AssessResultGateCheckLink 링크 모두 삭제
	    // deleteResultGateCheckLink(gAssRslt);
	    AssessResultGateCheckLink assOutLink = null;
	    r = PersistenceHelper.navigate(gAssRslt, "check", AssessResultGateCheckLink.class, false);
	    while (r.hasMoreElements()) {
		assOutLink = (AssessResultGateCheckLink) r.nextElement();
		if (dto.getRev() == assOutLink.getRev()) {
		    PersistenceHelper.manager.delete(assOutLink);
		}

	    }
	}
	int rev = this.getMaxGateDegree(oid);

	// 6. GateAssessResult와 Gate 객체간 링크 생성하고 체크항목의 변경사항을 저장 적용하기
	// GateCheckSheet객체의 실적정보 Result 항목의 값도 동시에 세팅해준다
	if (tgGateCheckOids != null) {
	    for (int i = 0; i < tgGateCheckOids.length; i++) {
		String gateChkOidPer = tgGateCheckOids[i];
		String gateRsltPer = StringUtil.checkNullZero(tgGateResults[i]);
		String gateChkPer = StringUtil.checkNullZero(tgGateChecks[i]);
		String managerDeptCode = "";
		String managerDeptName = "";

		String managerDeptOidArr[] = managerDeptOids[i].split(",");

		if (managerDeptOidArr != null) {
		    for (String managerDeptOid : managerDeptOidArr) {
			managerDeptOid = managerDeptOid.replaceAll("\\p{Space}", ""); // 공백제거
			Department dept = (Department) CommonUtil.getObject(managerDeptOid);
			if (dept == null) {
			    continue;
			}
			managerDeptCode += dept.getCode() + ",";
			managerDeptName += dept.getName() + ",";
		    }

		    managerDeptCode = StringUtils.removeEnd(managerDeptCode, ",");
		    managerDeptName = StringUtils.removeEnd(managerDeptName, ",");
		}

		GateCheckSheet gateChkObj = (GateCheckSheet) CommonUtil.getObject(gateChkOidPer);

		AssessResultGateCheckLink assRstGateLink = null;

		// 현재 모든 링크를 저장하기위해 기존 링크를 모두 삭제후 링크를 추가하는 로직.
		assRstGateLink = AssessResultGateCheckLink.newAssessResultGateCheckLink(gateChkObj, gAssRslt);
		assRstGateLink.setSheetCheck1(gateChkPer);

		// 테스크 유형에 일치하는 GateCheckSheet의 실적항목을 찾아서 저장한다
		String taskTypeCategoryStr = task.getTaskTypeCategory();
		String taskCodeName = "";
		Vector gateLevelVec = NumberCodeHelper.manager.getNumberCodeForQuery("GATELEVELNAME");
		if (gateLevelVec != null) {
		    for (int k = 0; k < gateLevelVec.size(); k++) {
			NumberCode code = (NumberCode) gateLevelVec.get(k);

			if (taskTypeCategoryStr != null && taskTypeCategoryStr.equals(code.getName().replaceAll("Gate", ""))) {
			    taskCodeName = code.getCode();
			}
		    }
		}

		GateAttribute gateAttrObj = gateChkObj.getAttr();
		if (("G1").equals(taskCodeName)) {
		    gateAttrObj.setResult1(gateRsltPer);
		    assRstGateLink.setSheetCheck1(gateChkPer);
		    assRstGateLink.setSheetResult1(gateRsltPer);
		} else if (("G2").equals(taskCodeName)) {
		    gateAttrObj.setResult2(gateRsltPer);
		    assRstGateLink.setSheetCheck2(gateChkPer);
		    assRstGateLink.setSheetResult2(gateRsltPer);
		} else if (("G3").equals(taskCodeName)) {
		    gateAttrObj.setResult3(gateRsltPer);
		    assRstGateLink.setSheetCheck3(gateChkPer);
		    assRstGateLink.setSheetResult3(gateRsltPer);
		} else if (("G4").equals(taskCodeName)) {
		    gateAttrObj.setResult4(gateRsltPer);
		    assRstGateLink.setSheetCheck4(gateChkPer);
		    assRstGateLink.setSheetResult4(gateRsltPer);
		} else if (("G5").equals(taskCodeName)) {
		    gateAttrObj.setResult5(gateRsltPer);
		    assRstGateLink.setSheetCheck5(gateChkPer);
		    assRstGateLink.setSheetResult5(gateRsltPer);
		} else if (("G6").equals(taskCodeName)) {
		    gateAttrObj.setResult6(gateRsltPer);
		    assRstGateLink.setSheetCheck6(gateChkPer);
		    assRstGateLink.setSheetResult6(gateRsltPer);
		} else if (("G7").equals(taskCodeName)) {
		    gateAttrObj.setResult7(gateRsltPer);
		    assRstGateLink.setSheetCheck7(gateChkPer);
		    assRstGateLink.setSheetResult7(gateRsltPer);
		} else if (("G8").equals(taskCodeName)) {
		    gateAttrObj.setResult8(gateRsltPer);
		    assRstGateLink.setSheetCheck8(gateChkPer);
		    assRstGateLink.setSheetResult8(gateRsltPer);
		} else if (("G9").equals(taskCodeName)) {
		    gateAttrObj.setResult9(gateRsltPer);
		    assRstGateLink.setSheetCheck9(gateChkPer);
		    assRstGateLink.setSheetResult9(gateRsltPer);
		} else if (("G10").equals(taskCodeName)) {
		    gateAttrObj.setResult10(gateRsltPer);
		    assRstGateLink.setSheetCheck10(gateChkPer);
		    assRstGateLink.setSheetResult10(gateRsltPer);
		} else if (("G11").equals(taskCodeName)) {
		    gateAttrObj.setResult11(gateRsltPer);
		    assRstGateLink.setSheetCheck11(gateChkPer);
		    assRstGateLink.setSheetResult11(gateRsltPer);
		} else if (("G12").equals(taskCodeName)) {
		    gateAttrObj.setResult12(gateRsltPer);
		    assRstGateLink.setSheetCheck12(gateChkPer);
		    assRstGateLink.setSheetResult12(gateRsltPer);
		} else if (("G13").equals(taskCodeName)) {
		    gateAttrObj.setResult13(gateRsltPer);
		    assRstGateLink.setSheetCheck13(gateChkPer);
		    assRstGateLink.setSheetResult13(gateRsltPer);
		} else if (("G14").equals(taskCodeName)) {
		    gateAttrObj.setResult14(gateRsltPer);
		    assRstGateLink.setSheetCheck14(gateChkPer);
		    assRstGateLink.setSheetResult14(gateRsltPer);
		} else if (("G15").equals(taskCodeName)) {
		    gateAttrObj.setResult15(gateRsltPer);
		    assRstGateLink.setSheetCheck15(gateChkPer);
		    assRstGateLink.setSheetResult15(gateRsltPer);
		} else if (("G16").equals(taskCodeName)) {
		    gateAttrObj.setResult16(gateRsltPer);
		    assRstGateLink.setSheetCheck16(gateChkPer);
		    assRstGateLink.setSheetResult16(gateRsltPer);
		} else if (("G17").equals(taskCodeName)) {
		    gateAttrObj.setResult17(gateRsltPer);
		    assRstGateLink.setSheetCheck17(gateChkPer);
		    assRstGateLink.setSheetResult17(gateRsltPer);
		} else if (("G18").equals(taskCodeName)) {
		    gateAttrObj.setResult18(gateRsltPer);
		    assRstGateLink.setSheetCheck18(gateChkPer);
		    assRstGateLink.setSheetResult18(gateRsltPer);
		} else if (("G19").equals(taskCodeName)) {
		    gateAttrObj.setResult19(gateRsltPer);
		    assRstGateLink.setSheetCheck19(gateChkPer);
		    assRstGateLink.setSheetResult19(gateRsltPer);
		} else if (("G20").equals(taskCodeName)) {
		    gateAttrObj.setResult20(gateRsltPer);
		    assRstGateLink.setSheetCheck20(gateChkPer);
		    assRstGateLink.setSheetResult20(gateRsltPer);
		}
		assRstGateLink.setRev(rev);
		assRstGateLink.setManagerDeptCode(managerDeptCode);
		assRstGateLink.setManagerDeptName(managerDeptName);
		assRstGateLink = (AssessResultGateCheckLink) PersistenceHelper.manager.save(assRstGateLink);

		gateChkObj.setAttr(gateAttrObj);
		gateChkObj = (GateCheckSheet) PersistenceHelper.manager.save(gateChkObj);
	    }
	}

    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 저장처리(품질문제 저장) AssessResultDqmHeaderLink 링크 모두 삭제 + GateAssessResult와 품질문제 객체간 링크 생성하기
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : saveTaskQualityResult
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveTaskQualityResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = null;
	try {
	    ReferenceFactory rf = new ReferenceFactory();
	    task = (E3PSTask) rf.getReference(oid).getObject();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	String tgDqmHeaderOids[] = dto.getTgDqmHeaderOids();
	String tgDqmHeaderChecks[] = dto.getTgDqmHeaderChecks();

	// 1. E3PSTask 객체에 링크된 GateAssessResult 객체가 있는지 조회한다 없는 경우 생성해준다
	GateAssessResult gAssRslt = checkGateResultExist(oid);

	if (gAssRslt != null) {
	    // GateAssessResult 와 E3PSTask와의 관계를 확인하여 없으면 링크를 걸어준다
	    QueryResult r = PersistenceHelper.navigate(gAssRslt, "task", GateAssRsltTaskLink.class, false);
	    boolean isTaskLink = false;
	    if (r.hasMoreElements()) {
		isTaskLink = true;
	    }
	    if (!isTaskLink) {
		GateAssRsltTaskLink assRstGateLink = GateAssRsltTaskLink.newGateAssRsltTaskLink(gAssRslt, task);
		assRstGateLink = (GateAssRsltTaskLink) PersistenceHelper.manager.save(assRstGateLink);
	    }

	    // 7. AssessResultDqmHeaderLink 링크 모두 삭제
	    deleteResultDqmHeaderLink(gAssRslt, dto);
	}
	int rev = this.getMaxGateDegree(oid);
	// 8. GateAssessResult와 품질문제 객체간 링크 생성하기
	if (tgDqmHeaderOids != null) {
	    for (int i = 0; i < tgDqmHeaderOids.length; i++) {
		String dqmOidPer = tgDqmHeaderOids[i];

		KETDqmHeader dqmHeaderObj = (KETDqmHeader) CommonUtil.getObject(dqmOidPer);
		KETDqmClose dqmCloseObj = dqmHeaderObj.getClose();

		String outputCompDate = "";

		if (dqmCloseObj != null && dqmCloseObj.getReviewDate() != null) {
		    outputCompDate = DateUtil.getDateString(dqmCloseObj.getReviewDate(), "d");
		}

		String outputStateStr = dqmHeaderObj.getDqmStateName();

		AssessResultDqmHeaderLink assRstDqmLink = null;

		// 현재 모든 링크를 저장하기위해 기존 링크를 모두 삭제후 링크를 추가하는 로직.
		assRstDqmLink = AssessResultDqmHeaderLink.newAssessResultDqmHeaderLink(dqmHeaderObj, gAssRslt);
		assRstDqmLink.setDqmCheck(tgDqmHeaderChecks[i]);
		assRstDqmLink.setDqmCompDate(outputCompDate);
		assRstDqmLink.setDqmState(outputStateStr);
		assRstDqmLink.setRev(rev);
		assRstDqmLink = (AssessResultDqmHeaderLink) PersistenceHelper.manager.save(assRstDqmLink);
	    }
	}

    }

    /**
     * E3PSTask 객체에 링크된 GateAssessResult 객체가 있는지 조회한다 없는 경우 생성해준다
     * 
     * @param oid
     * @return
     * @throws Exception
     * @메소드명 : checkGateResultExist
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public GateAssessResult checkGateResultExist(String oid) throws Exception {
	long taskOidNum = 0;
	String taskOidStr = "";
	if (oid != null && !"".equals(oid)) {
	    taskOidStr = oid.substring(oid.indexOf(":") + 1);
	}

	E3PSTask task = null;
	E3PSProject project = null;
	try {
	    ReferenceFactory rf = new ReferenceFactory();
	    task = (E3PSTask) rf.getReference(oid).getObject();
	    if (task != null) {
		taskOidNum = CommonUtil.getOIDLongValue(task);
		project = (E3PSProject) task.getProject();

	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	project.getDivision();
	// 1. E3PSTask 객체에 링크된 GateAssessResult 객체가 있는지 조회한다 없는 경우 생성해준다
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx_gateRslt = query.appendClassList(GateAssessResult.class, true);
	int idx_gateRsltLink = query.appendClassList(GateAssRsltTaskLink.class, true);

	ClassAttribute ca1 = new ClassAttribute(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca2 = new ClassAttribute(GateAssRsltTaskLink.class, "roleAObjectRef.key.id");

	sc = new SearchCondition(ca1, SearchCondition.EQUAL, ca2);
	query.appendWhere(sc, new int[] { idx_gateRslt, idx_gateRsltLink });

	if (task == null) {
	    // idA3B11
	    sc = new SearchCondition(GateAssRsltTaskLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, Long.parseLong(taskOidStr));
	} else {
	    sc = new SearchCondition(GateAssRsltTaskLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, task.getPersistInfo()
		    .getObjectIdentifier().getId());
	}

	query.appendAnd();
	query.appendWhere(sc, new int[] { idx_gateRsltLink });

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

	// GateAssessResult 초기화
	GateAssessResult gAssRslt = null;
	GateAssRsltTaskLink gAssRsltLink = null;
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    gAssRslt = (GateAssessResult) objArr[0];
	    gAssRsltLink = (GateAssRsltTaskLink) objArr[1];
	}

	// Task에 연결된 평가결과 GateAssessResult 객체가 없을 경우 GateAssessResult객체를 만들고 Task와 링크걸어 준다
	if (gAssRslt == null) {
	    gAssRslt = GateAssessResult.newGateAssessResult();

	    // gAssRslt.setTask(task);
	    if (StringUtil.isEmpty(gAssRslt.getTitle())) {
		if (task != null)
		    gAssRslt.setTitle(task.getTaskName());
		else
		    gAssRslt.setTitle("" + taskOidNum);
	    }

	    if (StringUtil.isEmpty(gAssRslt.getName())) {
		if (task != null)
		    gAssRslt.setName(task.getTaskName());
		else
		    gAssRslt.setName("" + taskOidNum);
	    }

	    // 폴더지정
	    String teamTypeStr = "";
	    if (project != null) {
		E3PSProjectData projectData = new E3PSProjectData(project);
		teamTypeStr = projectData.teamType;
	    }

	    String folderDir = "";
	    if (teamTypeStr != null && teamTypeStr.indexOf("전자") >= 0) {
		folderDir = "/전자사업부/프로젝트/GATE";
	    } else {
		folderDir = "/자동차사업부/프로젝트/GATE";
	    }
	    PDMLinkProduct wtProduct = null;
	    WTContainerRef wtContainerRef = null;
	    // assignLocation.
	    QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
	    SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, "KET");
	    qs.appendSearchCondition(sc1);
	    QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
	    if (results.hasMoreElements()) {
		wtProduct = (PDMLinkProduct) results.nextElement();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }
	    if (wtProduct == null) {
		wtProduct = WCUtil.getPDMLinkProduct();
		wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
	    }

	    Folder folder = FolderHelper.service.getFolder("/Default" + folderDir, wtContainerRef);
	    FolderHelper.assignLocation((FolderEntry) gAssRslt, folder);

	    gAssRslt = (GateAssessResult) PersistenceHelper.manager.save(gAssRslt);

	    gAssRsltLink = GateAssRsltTaskLink.newGateAssRsltTaskLink(gAssRslt, task);
	    gAssRsltLink = (GateAssRsltTaskLink) PersistenceHelper.manager.save(gAssRsltLink);
	} else {

	    if (gAssRsltLink == null) {

		GateAssRsltTaskLink assRsltTaskImsiLink = GateAssRsltTaskLink.newGateAssRsltTaskLink(gAssRslt, task);
		assRsltTaskImsiLink = (GateAssRsltTaskLink) PersistenceHelper.manager.save(assRsltTaskImsiLink);
	    }
	}
	return gAssRslt;
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 저장처리
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : saveTaskTrustResult
     * @작성자 : jackey88
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void saveTaskTrustResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	E3PSProject project = (E3PSProject) task.getProject();

	String tgEstimateDates[] = dto.getEstimateDates();
	String tgOkCnts[] = dto.getOkCnts();
	String tgNgCnts[] = dto.getNgCnts();
	String tgNgReasons[] = dto.getNgReasons();
	String tgTrustOids[] = dto.getTrustOids();

	// 3. Gate task 산출물 대상을 추가 저장
	if (tgEstimateDates != null) {
	    for (int i = 0; i < tgEstimateDates.length; i++) {
		String trustOidPer = "";
		if (tgTrustOids != null && tgTrustOids.length > i)
		    trustOidPer = tgTrustOids[i];

		String estimatePer = tgEstimateDates[i];

		String okCntPer = "";
		if (tgOkCnts != null)
		    okCntPer = tgOkCnts[i];
		int okCntPerInt = Integer.parseInt(StringUtil.checkNullZero(okCntPer));

		String ngCntPer = "";
		if (tgNgCnts != null)
		    ngCntPer = tgNgCnts[i];
		int ngCntPerInt = Integer.parseInt(StringUtil.checkNullZero(ngCntPer));

		String ngReasonPer = "";
		if (tgNgReasons != null)
		    ngReasonPer = StringUtil.checkNull(tgNgReasons[i]);
		ngReasonPer = ngReasonPer.trim();

		if (e3ps.common.util.StringUtil.isEmpty(trustOidPer)) {
		    // 신규 차수 추가
		    TaskTrustResult taskTrust = TaskTrustResult.newTaskTrustResult();
		    taskTrust.setEstimateDate(estimatePer);
		    taskTrust.setOkCnt(okCntPerInt);
		    taskTrust.setNgCnt(ngCntPerInt);
		    taskTrust.setDescription(ngReasonPer.trim());
		    // int maxRev = getNextTrustRev(oid);
		    taskTrust.setRev(i + 1);
		    taskTrust = (TaskTrustResult) PersistenceHelper.manager.save(taskTrust);

		    TrustTemplateTaskLink trustTaskLink = TrustTemplateTaskLink.newTrustTemplateTaskLink(taskTrust, task);
		    trustTaskLink = (TrustTemplateTaskLink) PersistenceHelper.manager.save(trustTaskLink);

		    // 신규차수인 경우 결과가 ‘NG’인 경우 이상발생보고서, 신뢰성시험보고서를 추가하고, 'OK'인 경우 신뢰성시험보고서 만 추가한다
		    if (ngCntPerInt == 0) {
			addNewProjectOutputLink(task, CommonUtil.getOIDString(taskTrust), "OK");
		    } else {
			addNewProjectOutputLink(task, CommonUtil.getOIDString(taskTrust), "NG");
		    }
		} else {
		    // 기존 차수 정보 업데이트
		    TaskTrustResult taskTrust = (TaskTrustResult) CommonUtil.getObject(trustOidPer);
		    taskTrust.setEstimateDate(estimatePer);
		    taskTrust.setOkCnt(okCntPerInt);
		    taskTrust.setNgCnt(ngCntPerInt);
		    taskTrust.setDescription(ngReasonPer.trim());
		    taskTrust.setRev(i + 1);
		    taskTrust = (TaskTrustResult) PersistenceHelper.manager.modify(taskTrust);

		}

	    }
	}

    }

    /**
     * 
     * 
     * @param Task
     *            TaskTrustResult outputFlag
     * @return
     * @throws Exception
     * @메소드명 : addNewProjectOutputLink
     * @작성자 : jackey88
     * @작성일 : 2014. 10. 08.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void addNewProjectOutputLink(E3PSTask task, String selectTrustOid, String outputFlag) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService();

	// 개발산출물/개발/이상발생보고서
	// 개발산출물/개발/신뢰성시험보고서

	HashMap map = new HashMap();

	ProjectOutput output1 = null;
	ProjectOutput output2 = null;
	String docTypeOid = "";
	try {
	    if ("NG".equals(outputFlag)) {
		docTypeOid = getDocTypeLevelOid("개발", "이상발생보고서");

		map = new HashMap();
		map.put("oid", "");
		map.put("taskOid", CommonUtil.getOIDString(task));
		map.put("name", "이상발생보고서");
		map.put("description", "이상발생보고서");
		map.put("docTypeOid", StringUtil.checkNull(docTypeOid));
		map.put("role", "");
		map.put("outputUser", "");
		map.put("objType", "DOC");
		map.put("outputtype", "");
		map.put("isPrimary", "1");
		map.put("docOid", "");
		map.put("selectTrustOid", selectTrustOid);
		map.put("subjectType", "대상");
		output2 = ProjectOutputHelper.saveDefProjectOutput(map);

		map = new HashMap();
		map.put("oid", "");
		map.put("taskOid", CommonUtil.getOIDString(task));
		map.put("name", "개발품질문제");
		map.put("description", "개발품질문제");
		map.put("docTypeOid", "");
		map.put("role", "");
		map.put("outputUser", "");
		map.put("objType", "QLP");
		map.put("outputtype", "");
		map.put("isPrimary", "1");
		map.put("docOid", "");
		map.put("selectTrustOid", selectTrustOid);
		map.put("subjectType", "대상");
		output2 = ProjectOutputHelper.saveDefProjectOutput(map);

		// ProjectOutputHelper.manager.setSelectTrustOid(output2, selectTrustOid);
	    }

	    docTypeOid = getDocTypeLevelOid("개발", "신뢰성시험보고서");

	    map.put("oid", "");
	    map.put("taskOid", CommonUtil.getOIDString(task));
	    map.put("name", "신뢰성시험보고서");
	    map.put("description", "신뢰성시험보고서");
	    map.put("docTypeOid", StringUtil.checkNull(docTypeOid));
	    map.put("role", "");
	    map.put("outputUser", "");
	    map.put("objType", "DOC");
	    map.put("outputtype", "");
	    map.put("isPrimary", "1");
	    map.put("docOid", "");
	    map.put("selectTrustOid", selectTrustOid);
	    map.put("subjectType", "대상");
	    output1 = ProjectOutputHelper.saveDefProjectOutput(map);
	    // ProjectOutputHelper.manager.setSelectTrustOid(output1, selectTrustOid);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 
     * 
     * @param Task
     *            TaskTrustResult outputFlag
     * @return
     * @throws Exception
     * @메소드명 : getDocTypeLevelOid
     * @작성자 : jackey88
     * @작성일 : 2014. 10. 08.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String getDocTypeLevelOid(String level2Name, String level3Name) {

	String docTypeOid = "";
	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService();

	// 개발산출물/개발/이상발생보고서
	// 개발산출물/개발/신뢰성시험보고서

	Map<String, Object> parameter = new HashMap<String, Object>();
	// 1 Level 분류체계 찾기
	parameter.clear();
	parameter.put("docTypeCode", "PD");
	parameter.put("locale", messageService.getLocale().toString());
	parameter.put("parentCode", "ROOT");

	try {

	    List<Map<String, Object>> categoryList = DMSUtil.getDocumentCategory(parameter);
	    String categoryCode = "";
	    if (categoryList.size() > 0) {
		categoryCode = categoryList.get(0).get("categoryCode").toString();
	    }

	    // 2 Level 분류체계 찾기
	    parameter.clear();
	    parameter.put("docTypeCode", "PD");
	    parameter.put("locale", messageService.getLocale().toString());
	    parameter.put("parentCode", categoryCode);

	    categoryCode = "";
	    categoryList = DMSUtil.getDocumentCategory(parameter);
	    if (categoryList.size() > 0) { // PD201003
		for (int k = 0; k < categoryList.size(); k++) {
		    if (level2Name.equals(categoryList.get(k).get("categoryName").toString())) {
			categoryCode = categoryList.get(k).get("categoryCode").toString();
		    }
		}

	    }

	    // 3 Level 분류체계 찾기
	    parameter.clear();
	    parameter.put("docTypeCode", "PD");
	    parameter.put("locale", messageService.getLocale().toString());
	    parameter.put("parentCode", categoryCode);

	    categoryCode = "";
	    categoryList = DMSUtil.getDocumentCategory(parameter);
	    if (categoryList.size() > 0) { // PD201003
		for (int k = 0; k < categoryList.size(); k++) {
		    if (level3Name.equals(categoryList.get(k).get("categoryName").toString())) {
			docTypeOid = categoryList.get(k).get("categoryCode").toString();
		    }
		}

	    }

	} catch (Exception e) {

	}

	return docTypeOid;
    }

    /**
     * 프로젝트 Gate 테스크 산출물 평가결과 삭제처리
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : deleteTaskTrustResult
     * @작성자 : jackey88
     * @작성일 : 2014. 10. 08.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void deleteTaskTrustResult(String oid, ProjectTaskDTO dto) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	E3PSProject project = (E3PSProject) task.getProject();

	String tgTrustOids[] = dto.getTrustOids();
	String tgTrustOidChks[] = dto.getTrustOidChks();

	// 3. Gate task 산출물 대상을 추가 저장
	if (tgTrustOidChks != null) {
	    for (int i = 0; i < tgTrustOidChks.length; i++) {
		String trustOidPer = "";
		if (tgTrustOids != null && tgTrustOids.length > i)
		    trustOidPer = tgTrustOids[i];

		if ("1".equals(tgTrustOidChks[i])) {
		    TaskTrustResult taskTrust = (TaskTrustResult) CommonUtil.getObject(trustOidPer);
		    int prevLev = taskTrust.getRev();

		    TrustTemplateTaskLink taskLink = null;
		    QueryResult r = PersistenceHelper.navigate(taskTrust, "task", TrustTemplateTaskLink.class, false);
		    while (r.hasMoreElements()) {
			taskLink = (TrustTemplateTaskLink) r.nextElement();
			PersistenceHelper.manager.delete(taskLink);
		    }

		    ProjectOutput projectOutput = null;
		    List<ProjectOutput> listProjectOut = new ArrayList<ProjectOutput>();
		    r = PersistenceHelper.manager.navigate(taskTrust, "output", TrustProjectOutputLink.class);
		    while (r.hasMoreElements()) {
			projectOutput = (ProjectOutput) r.nextElement();

			ProjectOutputHelper.manager.deleteProjectOutput(projectOutput);
			// PersistenceHelper.manager.delete(projectOutput);
		    }

		    TrustProjectOutputLink taskOutputLink = null;
		    r = PersistenceHelper.navigate(taskTrust, "output", TrustProjectOutputLink.class, false);
		    while (r.hasMoreElements()) {
			taskOutputLink = (TrustProjectOutputLink) r.nextElement();
			PersistenceHelper.manager.delete(taskOutputLink);
		    }

		    PersistenceHelper.manager.delete(taskTrust);

		    TaskTrustResult taskRst = null;
		    // r = PersistenceHelper.navigate(task, "trust", TrustTemplateTaskLink.class, false);
		    r = PersistenceHelper.manager.navigate(task, "trust", TrustTemplateTaskLink.class);
		    while (r.hasMoreElements()) {
			taskRst = (TaskTrustResult) r.nextElement();
			int tgRev = taskRst.getRev();
			if (prevLev < tgRev) {
			    taskRst.setRev(tgRev - 1);
			    taskRst = (TaskTrustResult) PersistenceHelper.manager.modify(taskRst);
			}
		    }

		}
	    }
	}

    }

    /**
     * 다음 차수 가져오기
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getNextTrustRev
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public int getNextTrustRev(String taskOid) throws Exception {
	int maxRev = 0;
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	// E3PSProject project = (E3PSProject) task.getProject();
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);
	    sb.append("	SELECT DECODE(M_VAL, NULL,1, M_VAL) M_VAL		\n");
	    sb.append("	 FROM							\n");
	    sb.append("	 (							\n");
	    sb.append("	    SELECT MAX(A2.REV)+1 M_VAL				\n");
	    sb.append("	    FROM E3PSTask A0					\n");
	    sb.append("	        , TrustTemplateTaskLink A1			\n");
	    sb.append("	        , KETTaskTrustResult A2				\n");
	    sb.append("	   WHERE A0.idA2A2 = A1.idA3B5				\n");
	    sb.append("	     AND A1.idA3A5 = A2.idA2A2				\n");
	    sb.append("	     AND A0.idA2A2 = '" + task.getPersistInfo().getObjectIdentifier().getId() + "'		\n");
	    sb.append("	 )							\n");
	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    if (rs.next()) {
		//
		maxRev = rs.getInt("M_VAL");
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}
	return maxRev;
    }

    /**
     * GateAssessResult객체에 연결된 AssessResultOutputLink 링크 객체 모두 삭제하기
     * 
     * @param GateAssessResult
     * @return
     * @throws Exception
     * @메소드명 : deleteResultOutputLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void deleteResultOutputLink(GateAssessResult gAssRslt) throws Exception {
	Vector vec = new Vector();
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	ResultSet rs = null;
	try {

	    /*
	     * //1. SQL conn = DBCPManager.getConnection("plm"); // conn.setAutoCommit(false); stmt = conn.createStatement();
	     * 
	     * StringBuffer sb = new StringBuffer(0); sb.append("	select b.ida2a2	LINK_OID		\n");
	     * sb.append("	  from KETGateAssessResult a,		\n"); sb.append("	       ketAssessResultOutputLink b	\n");
	     * sb.append("	 where a.ida2a2 =  b.idA3A5		\n"); sb.append("	   and a.ida2a2 ='" +
	     * gAssRslt.getPersistInfo().getObjectIdentifier().getId() + "'		\n");
	     * 
	     * rs = stmt.executeQuery(sb.toString()); // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());
	     * 
	     * while (rs.next()) { vec.addElement(rs.getString("LINK_OID")); } rs.close(); stmt.close(); conn.close();
	     * 
	     * if (vec != null) { for (int i = 0; i < vec.size(); i++) { AssessResultOutputLink assRstOutLink = (AssessResultOutputLink)
	     * CommonUtil .getObject("ext.ket.project.gate.entity.AssessResultOutputLink:" + vec.elementAt(i));
	     * 
	     * // 링크 삭제(링크만) PersistenceHelper.manager.delete(assRstOutLink); } }
	     */

	    // 2.
	    AssessResultOutputLink assOutLink = null;
	    QueryResult r = PersistenceHelper.navigate(gAssRslt, "output", AssessResultOutputLink.class, false);
	    while (r.hasMoreElements()) {
		assOutLink = (AssessResultOutputLink) r.nextElement();
		PersistenceHelper.manager.delete(assOutLink);
	    }

	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

    }

    /**
     * GateAssessResult객체에 연결된 AssessResultGateCheckLink 링크 객체 모두 삭제하기
     * 
     * @param
     * @return
     * @throws Exception
     * @메소드명 : deleteResultGateCheckLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void deleteResultGateCheckLink(GateAssessResult gAssRslt) throws Exception {

	AssessResultGateCheckLink assOutLink = null;
	QueryResult r = PersistenceHelper.navigate(gAssRslt, "check", AssessResultGateCheckLink.class, false);
	while (r.hasMoreElements()) {
	    assOutLink = (AssessResultGateCheckLink) r.nextElement();
	    PersistenceHelper.manager.delete(assOutLink);
	}
    }

    /**
     * GateAssessResult객체에 연결된 AssessResultDqmHeaderLink 링크 객체 모두 삭제하기
     * 
     * @param
     * @return
     * @throws Exception
     * @메소드명 : deleteResultDqmHeaderLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void deleteResultDqmHeaderLink(GateAssessResult gAssRslt, ProjectTaskDTO dto) throws Exception {

	AssessResultDqmHeaderLink assOutLink = null;
	QueryResult r = PersistenceHelper.navigate(gAssRslt, "dqm", AssessResultDqmHeaderLink.class, false);
	while (r.hasMoreElements()) {
	    assOutLink = (AssessResultDqmHeaderLink) r.nextElement();
	    if (dto.getRev() == assOutLink.getRev()) {
		PersistenceHelper.manager.delete(assOutLink);
	    }

	}
    }

    /**
     * 
     * 품질문제
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getQualityProblemList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<KETDqmDTO> getQualityProblemList(String taskOid, GateAssessResult gateRslt, int gateDegree) throws Exception {
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer(0);

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String e3psTaskOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(task));
	String gateTaskName = task.getTaskName();

	E3PSProject project = (E3PSProject) task.getProject();
	String e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(project));

	boolean isGateAssResultInwork = false;
	if (gateRslt == null) {
	    isGateAssResultInwork = true;
	} else {
	    if (gateRslt.getState().toString().equals("") || gateRslt.getState().toString().equals("INWORK")
		    || gateRslt.getState().toString().equals("REWORK")) {
		isGateAssResultInwork = true;
	    }
	}

	List<KETDqmDTO> ketDqmDTOList = new ArrayList<KETDqmDTO>();
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    if (gateRslt == null) {

		sb.append("	SELECT A0.IDA2A2 DQM_OID					\n");
		sb.append("		, A0.PROBLEM			\n");
		sb.append("		, A0.PROBLEMNO			\n");
		sb.append("		, A0.DQMSTATECODE			\n");
		sb.append("		, A0.DQMSTATENAME			\n");
		sb.append("		, A1.IDA2A2 RAISE_OID			\n");
		sb.append("		, TO_CHAR(A1.CREATESTAMPA2, 'YYYY-MM-DD') DQM_CREATESTAMP			\n");
		sb.append("		, RUSER.LAST  DQM_CREATOR			\n");
		sb.append("		, A1.CUSTOMERCODE			\n");
		sb.append("		, A1.CARTYPECODE			\n");
		sb.append("		, A1.PROBLEMTYPECODE			\n");
		sb.append("		, A1.URGENCYCODE			\n");
		sb.append("		, A1.PARTCATEGORYCODE			\n");
		sb.append("		, A1.CUSTOMERNAME			\n");
		sb.append("		, A1.CARTYPENAME			\n");
		sb.append("		, A1.PROBLEMTYPENAME			\n");
		sb.append("		, A1.URGENCYNAME			\n");
		sb.append("		, A1.PARTCATEGORYNAME			\n");
		sb.append("		, A1.OCCURDIVCODE			\n");
		sb.append("		, A1.OCCURSTEPCODE			\n");
		sb.append("		, A1.OCCURCODE			\n");
		sb.append("		, A1.OCCURDIVNAME			\n");
		sb.append("		, A1.OCCURPLACENAME			\n");
		sb.append("		, A1.OCCURSTEPNAME			\n");
		sb.append("		, A1.OCCURNAME			\n");
		sb.append("		, TO_CHAR(A1.OCCURDATE, 'YYYY-MM-DD') OCCURDATE			\n");
		sb.append("		, A1.DEFECTDIVNAME			\n");
		sb.append("		, A1.DEFECTDIVCODE			\n");
		sb.append("		, A1.DEFECTTYPENAME			\n");
		sb.append("		, A1.DEFECTTYPECODE			\n");
		sb.append("		, RPMAST.WTPARTNUMBER PART_NUM			\n");
		sb.append("		, A1.IDA2A2 PART_OID			\n");
		sb.append("		, '' ECR_NO			\n");
		sb.append("		, TO_CHAR(HCLOSE.REVIEWDATE, 'YYYY-MM-DD') REVIEWDATE			\n");
		sb.append("		, A2.DQMCHECK			\n");
		sb.append("		, '' AS DQMLINK_OID			\n");
		sb.append("	 FROM ProductProject A6			\n");
		sb.append("		 ,KETDqmRaise A1			\n");
		sb.append("		 ,KETDqmHeader A0			\n");
		sb.append("		 ,WTUSER RUSER			\n");
		sb.append("		 ,WTPart RPART			\n");
		sb.append("		 ,WTPartMaster RPMAST			\n");
		sb.append("		 ,KETDqmAction HACTION			\n");
		sb.append("		 ,KETDqmClose HCLOSE			\n");
		sb.append("		 ,(SELECT * FROM E3PSTask WHERE idA2A2 = " + e3psTaskOID + " )  A5			\n");
		sb.append("		 ,ketGateAssessResult A3			\n");
		sb.append("		, ketGateAssRsltTaskLink gLink				\n");
		sb.append("		 ,ketAssessResultDqmHeaderLink A2			\n");
		sb.append("		 , E3PSProjectMaster A7			\n");
		sb.append("	 WHERE (A7.PJTNO = A1.PJTNO) 			\n"); // (A6.idA2A2 = A1.idA3D8)
		sb.append("		 AND (A1.idA3A7 = RUSER.IDA2A2) 			\n");
		sb.append("		 AND (A1.idA3B8 = RPART.IDA2A2)  			\n");
		sb.append("		 AND (RPART.IDA3MASTERREFERENCE = RPMAST.IDA2A2) 			\n");
		sb.append("		 AND (A1.idA2A2 = A0.idA3C3) 			\n");
		sb.append("		 AND (A0.idA3B3 = HACTION.idA2A2(+))			\n");
		sb.append("		 AND (A0.idA3A3 = HCLOSE.idA2A2(+))			\n");
		sb.append("		 AND (A6.idA2A2 = A5.idA3B4(+))			\n");
		sb.append("	       AND  a5.ida2a2 = gLink.idA3B5(+)				\n");
		sb.append("		AND gLink.idA3A5 = A3.ida2a2(+)				\n");
		sb.append("		AND a5.ida3b4 =  a6.ida2a2				\n");
		// sb.append("		AND a6.CHECKOUTSTATE <> 'c/o'				\n");
		// sb.append("		AND a6.LASTEST = 1					\n");
		sb.append("		 AND (A3.idA2A2 = A2.idA3B5(+)) 			\n");
		sb.append("		 AND (A6.IDA3B8  = A7.IDA2A2) 			\n");
		sb.append("		 AND ((A0.dqmStateCode = 'ACTIONINWORK')   OR (A0.dqmStateCode = 'RAISEAPPROVED') 			\n");
		sb.append("			 OR (A0.dqmStateCode = 'ACTIONUNDERREVIEW') OR (A0.dqmStateCode = 'ACTIONAPPROVED') 			\n");
		sb.append("			 OR (A0.dqmStateCode = 'END')) 			\n");
		sb.append("		 AND (A6.idA2A2 = " + e3psProjectOID + " ) 			\n");
		sb.append("		 AND (A1.ISSUECODE = 'DQMIS8' ) 			\n");
		sb.append("	 ORDER BY A0.problemNo DESC			\n");
	    } else {

		if (isGateAssResultInwork) {

		    sb.append("		SELECT AA.*			\n");
		    sb.append("		,( SELECT A2.DQMCHECK  FROM    ketAssessResultDqmHeaderLink A2			\n");
		    sb.append("			         	, ketGateAssessResult A3			\n");
		    sb.append("					, ketGateAssRsltTaskLink gLink				\n");
		    sb.append("					, ProductProject  p		\n");
		    sb.append("			         	,(SELECT * FROM E3PSTask WHERE idA2A2 = " + e3psTaskOID + " )  A5			\n");
		    sb.append("			    WHERE (AA.DQM_OID = A2.idA3A5)			\n");
		    sb.append("		              AND (A2.rev = " + gateDegree + ") 			\n");
		    sb.append("			    AND (A2.idA3B5 = A3.idA2A2) 			\n");
		    sb.append("			AND A5.ida2a2 = gLink.idA3B5				\n");
		    sb.append("			AND gLink.idA3A5 = A3.ida2a2				\n");
		    sb.append("			AND A5.ida3b4 =  p.ida2a2				\n");
		    sb.append("			AND EXISTS						\n");
		    sb.append("				(SELECT 'T'					\n");
		    sb.append("				   FROM PRODUCTPROJECT PJT, E3PSTASK TASK	\n");
		    sb.append("				WHERE TASK.IDA3B4 = PJT.IDA2A2			\n");
		    sb.append("				AND PJT.IDA2A2 = " + e3psProjectOID + "		\n");
		    sb.append("				AND TASK.IDA2A2 = A5.ida2a2)			\n");
		    sb.append("			)	DQMCHECK			\n");
		    sb.append("		,( SELECT A2.IDA2A2  FROM    ketAssessResultDqmHeaderLink A2			\n");
		    sb.append("			         	, ketGateAssessResult A3			\n");
		    sb.append("					, ketGateAssRsltTaskLink gLink				\n");
		    sb.append("					, ProductProject  p		\n");
		    sb.append("			         	,(SELECT * FROM E3PSTask WHERE idA2A2 = " + e3psTaskOID + " )  A5			\n");
		    sb.append("			    WHERE (AA.DQM_OID = A2.idA3A5)			\n");
		    sb.append("		 AND (A2.rev = " + gateDegree + ") 			\n");
		    sb.append("			    AND (A2.idA3B5 = A3.idA2A2) 			\n");
		    sb.append("			AND A5.ida2a2 = gLink.idA3B5				\n");
		    sb.append("			AND gLink.idA3A5 = A3.ida2a2				\n");
		    sb.append("			AND A5.ida3b4 =  p.ida2a2				\n");
		    sb.append("			AND EXISTS						\n");
		    sb.append("				(SELECT 'T'					\n");
		    sb.append("				   FROM PRODUCTPROJECT PJT, E3PSTASK TASK	\n");
		    sb.append("				WHERE TASK.IDA3B4 = PJT.IDA2A2			\n");
		    sb.append("				AND PJT.IDA2A2 = " + e3psProjectOID + "		\n");
		    sb.append("				AND TASK.IDA2A2 = A5.ida2a2)			\n");
		    sb.append("			)	DQMLINK_OID			\n");
		    sb.append("		FROM 			\n");
		    sb.append("		(			\n");
		    sb.append("			SELECT    A0.IDA2A2 DQM_OID			\n");
		    sb.append("				, A0.PROBLEM			\n");
		    sb.append("				, A0.PROBLEMNO			\n");
		    sb.append("				, A0.DQMSTATECODE			\n");
		    sb.append("				, A0.DQMSTATENAME			\n");
		    sb.append("				, A1.IDA2A2 RAISE_OID			\n");
		    sb.append("				, TO_CHAR(A1.CREATESTAMPA2, 'YYYY-MM-DD') DQM_CREATESTAMP			\n");
		    sb.append("				, RUSER.LAST  DQM_CREATOR			\n");
		    sb.append("				, A1.CUSTOMERCODE			\n");
		    sb.append("				, A1.CARTYPECODE			\n");
		    sb.append("				, A1.PROBLEMTYPECODE			\n");
		    sb.append("				, A1.URGENCYCODE			\n");
		    sb.append("				, A1.PARTCATEGORYCODE			\n");
		    sb.append("				, A1.CUSTOMERNAME			\n");
		    sb.append("				, A1.CARTYPENAME			\n");
		    sb.append("				, A1.PROBLEMTYPENAME			\n");
		    sb.append("				, A1.URGENCYNAME			\n");
		    sb.append("				, A1.PARTCATEGORYNAME			\n");
		    sb.append("				, A1.OCCURDIVCODE			\n");
		    sb.append("				, A1.OCCURSTEPCODE			\n");
		    sb.append("				, A1.OCCURCODE			\n");
		    sb.append("				, A1.OCCURDIVNAME			\n");
		    sb.append("				, A1.OCCURPLACENAME			\n");
		    sb.append("				, A1.OCCURSTEPNAME			\n");
		    sb.append("				, A1.OCCURNAME			\n");
		    sb.append("				, TO_CHAR(A1.OCCURDATE, 'YYYY-MM-DD') OCCURDATE			\n");
		    sb.append("				, A1.DEFECTDIVNAME			\n");
		    sb.append("				, A1.DEFECTDIVCODE			\n");
		    sb.append("				, A1.DEFECTTYPENAME			\n");
		    sb.append("				, A1.DEFECTTYPECODE			\n");
		    sb.append("				, RPMAST.WTPARTNUMBER PART_NUM		\n");
		    sb.append("				, A1.IDA2A2 PART_OID			\n");
		    sb.append("				, '' ECR_NO				\n");
		    sb.append("				, TO_CHAR(HCLOSE.REVIEWDATE, 'YYYY-MM-DD') REVIEWDATE		\n");
		    sb.append("			     FROM ProductProject A6              			\n");
		    sb.append("			         ,KETDqmRaise A1            			\n");
		    sb.append("			         ,KETDqmHeader A0    			\n");
		    sb.append("			         ,WTUSER RUSER				\n");
		    sb.append("			         ,WTPart RPART				\n");
		    sb.append("			         ,WTPartMaster RPMAST			\n");
		    sb.append("			         ,KETDqmAction HACTION			\n");
		    sb.append("			         ,KETDqmClose HCLOSE			\n");
		    sb.append("		 		 ,E3PSProjectMaster A7			\n");
		    sb.append("			WHERE 1=1			\n");
		    sb.append("			    AND (A6.idA2A2 = " + e3psProjectOID + " )	\n");
		    // sb.append("			    AND (A6.idA2A2 = A1.idA3D8)			\n");
		    sb.append("		 	    AND (A6.IDA3B8  = A7.IDA2A2) 			\n");
		    sb.append("			    AND (A7.PJTNO = A1.PJTNO)					\n");
		    // sb.append("			    AND a6.CHECKOUTSTATE <> 'c/o'				\n");
		    // sb.append("			    AND a6.LASTEST = 1					\n");
		    sb.append("			    AND (A1.idA2A2 = A0.idA3C3)			\n");
		    sb.append("			    AND (A0.idA3B3 = HACTION.idA2A2(+))			\n");
		    sb.append("			    AND (A0.idA3A3 = HCLOSE.idA2A2(+))			\n");
		    sb.append("			    AND (A1.idA3A7 = RUSER.IDA2A2) 			\n");
		    sb.append("			    AND (A1.idA3B8 = RPART.IDA2A2) 			\n");
		    sb.append("			    AND (RPART.IDA3MASTERREFERENCE = RPMAST.IDA2A2) 	\n");
		    sb.append("			    AND (RPART.IDA3MASTERREFERENCE = RPMAST.IDA2A2) 	\n");
		    sb.append("		 	    AND ((A0.dqmStateCode = 'ACTIONINWORK')   OR (A0.dqmStateCode = 'RAISEAPPROVED') 		\n");
		    sb.append("			 	OR (A0.dqmStateCode = 'ACTIONUNDERREVIEW') OR (A0.dqmStateCode = 'ACTIONAPPROVED') 	\n");
		    sb.append("			 	OR (A0.dqmStateCode = 'END')) 			\n");
		    sb.append("		            AND (A1.ISSUECODE = 'DQMIS8' ) 			\n");
		    sb.append("		) AA			\n");

		} else {

		    sb.append("	SELECT A0.IDA2A2 DQM_OID					\n");
		    sb.append("		, A0.PROBLEM			\n");
		    sb.append("		, A0.PROBLEMNO			\n");
		    sb.append("		, A0.DQMSTATECODE			\n");
		    sb.append("		, A0.DQMSTATENAME			\n");
		    sb.append("		, A1.IDA2A2 RAISE_OID			\n");
		    sb.append("		, TO_CHAR(A1.CREATESTAMPA2, 'YYYY-MM-DD') DQM_CREATESTAMP			\n");
		    sb.append("		, RUSER.LAST  DQM_CREATOR			\n");
		    sb.append("		, A1.CUSTOMERCODE			\n");
		    sb.append("		, A1.CARTYPECODE			\n");
		    sb.append("		, A1.PROBLEMTYPECODE			\n");
		    sb.append("		, A1.URGENCYCODE			\n");
		    sb.append("		, A1.PARTCATEGORYCODE			\n");
		    sb.append("		, A1.CUSTOMERNAME			\n");
		    sb.append("		, A1.CARTYPENAME			\n");
		    sb.append("		, A1.PROBLEMTYPENAME			\n");
		    sb.append("		, A1.URGENCYNAME			\n");
		    sb.append("		, A1.PARTCATEGORYNAME			\n");
		    sb.append("		, A1.OCCURDIVCODE			\n");
		    sb.append("		, A1.OCCURSTEPCODE			\n");
		    sb.append("		, A1.OCCURCODE			\n");
		    sb.append("		, A1.OCCURDIVNAME			\n");
		    sb.append("		, A1.OCCURPLACENAME			\n");
		    sb.append("		, A1.OCCURSTEPNAME			\n");
		    sb.append("		, A1.OCCURNAME			\n");
		    sb.append("		, TO_CHAR(A1.OCCURDATE, 'YYYY-MM-DD') OCCURDATE			\n");
		    sb.append("		, A1.DEFECTDIVNAME			\n");
		    sb.append("		, A1.DEFECTDIVCODE			\n");
		    sb.append("		, A1.DEFECTTYPENAME			\n");
		    sb.append("		, A1.DEFECTTYPECODE			\n");
		    sb.append("		, RPMAST.WTPARTNUMBER PART_NUM			\n");
		    sb.append("		, A1.IDA2A2 PART_OID			\n");
		    sb.append("		, '' ECR_NO			\n");
		    sb.append("		, TO_CHAR(HCLOSE.REVIEWDATE, 'YYYY-MM-DD') REVIEWDATE			\n");
		    sb.append("		, A2.DQMCHECK			\n");
		    sb.append("		, A2.IDA2A2 DQMLINK_OID			\n");
		    sb.append("	 FROM KETDqmRaise A1			\n");
		    sb.append("		 ,KETDqmHeader A0			\n");
		    sb.append("		 ,WTUSER RUSER			\n");
		    sb.append("		 ,WTPart RPART			\n");
		    sb.append("		 ,WTPartMaster RPMAST			\n");
		    sb.append("		 ,KETDqmAction HACTION			\n");
		    sb.append("		 ,KETDqmClose HCLOSE			\n");
		    sb.append("		 ,(SELECT * FROM E3PSTask WHERE idA2A2 = " + e3psTaskOID + " )  A5			\n");
		    sb.append("		 ,ketGateAssessResult A3			\n");
		    sb.append("		 , ketGateAssRsltTaskLink gLink				\n");
		    sb.append("		 , ProductProject  p		\n");
		    sb.append("		 ,ketAssessResultDqmHeaderLink A2			\n");
		    sb.append("	 WHERE (A1.idA3A7 = RUSER.IDA2A2) 			\n");
		    sb.append("		 AND (A1.idA3B8 = RPART.IDA2A2)  			\n");
		    sb.append("		 AND (RPART.IDA3MASTERREFERENCE = RPMAST.IDA2A2) 			\n");
		    sb.append("		 AND (A1.idA2A2 = A0.idA3C3) 			\n");
		    sb.append("		 AND (A0.idA3B3 = HACTION.idA2A2(+))			\n");
		    sb.append("		 AND (A0.idA3A3 = HCLOSE.idA2A2(+))			\n");
		    sb.append("		 AND A5.ida2a2 = gLink.idA3B5				\n");
		    sb.append("		 AND gLink.idA3A5 = A3.ida2a2				\n");
		    sb.append("		 AND A5.ida3b4 =  p.ida2a2				\n");
		    // sb.append("		 AND p.LASTEST = 1					\n");
		    // sb.append("		 AND p.CHECKOUTSTATE <> 'c/o'				\n");
		    sb.append("		 AND (A3.idA2A2 = A2.idA3B5) 			\n");
		    sb.append("		 AND (A2.idA3A5 = A0.idA2A2) 			\n");
		    sb.append("		 AND (A2.rev = " + gateDegree + ") 			\n");
		    sb.append("		 AND (A1.ISSUECODE = 'DQMIS8' ) 			\n");

		    sb.append("	AND EXISTS						\n");
		    sb.append("		(SELECT 'T'					\n");
		    sb.append("		   FROM PRODUCTPROJECT PJT, E3PSTASK TASK	\n");
		    sb.append("		WHERE TASK.IDA3B4 = PJT.IDA2A2			\n");
		    sb.append("		AND PJT.IDA2A2 = " + e3psProjectOID + "		\n");
		    sb.append("		AND TASK.IDA2A2 = A5.ida2a2)			\n");

		    sb.append("	 ORDER BY A0.problemNo DESC			\n");
		}
	    }
	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    while (rs.next()) {
		KETDqmDTO ketDqmDTO = new KETDqmDTO();
		// ketDqmDTO.setOid(rs.getString("DQM_OID"));
		// ketDqmDTO.setProblem(rs.getString("PROBLEM"));
		// ketDqmDTO.setProblemNo(rs.getString("PROBLEMNO"));
		// ketDqmDTO.setDqmStateCode(rs.getString("DQMSTATECODE"));
		// ketDqmDTO.setDqmStateName(rs.getString("DQMSTATENAME"));
		// ketDqmDTO.setRaiseOid(rs.getString("RAISE_OID"));
		// ketDqmDTO.setCreateStamp(rs.getString("DQM_CREATESTAMP"));
		// ketDqmDTO.setRaiseCreateUserName(rs.getString("DQM_CREATOR"));
		// ketDqmDTO.setCustomerCode(rs.getString("CUSTOMERCODE"));
		// ketDqmDTO.setCartypeCode(rs.getString("CARTYPECODE"));
		// ketDqmDTO.setProblemTypeCode(rs.getString("PROBLEMTYPECODE"));
		// ketDqmDTO.setUrgencyCode(rs.getString("URGENCYCODE"));
		// ketDqmDTO.setPartCategoryCode(rs.getString("PARTCATEGORYCODE"));
		//
		// ketDqmDTO.setCustomerName(rs.getString("CUSTOMERNAME"));
		// ketDqmDTO.setCartypeName(rs.getString("CARTYPENAME"));
		// ketDqmDTO.setProblemTypeName(rs.getString("PROBLEMTYPENAME"));
		// ketDqmDTO.setUrgencyName(rs.getString("URGENCYNAME"));
		// ketDqmDTO.setPartCategoryName(rs.getString("PARTCATEGORYNAME"));
		// ketDqmDTO.setOccurDivCode(rs.getString("OCCURDIVCODE"));
		// ketDqmDTO.setOccurStepCode(rs.getString("OCCURSTEPCODE"));
		// ketDqmDTO.setOccurCode(rs.getString("OCCURCODE"));
		//
		// ketDqmDTO.setOccurDivName(rs.getString("OCCURDIVNAME"));
		// ketDqmDTO.setOccurPlaceName(rs.getString("OCCURPLACENAME"));
		// ketDqmDTO.setOccurStepName(rs.getString("OCCURSTEPNAME"));
		// ketDqmDTO.setOccurName(rs.getString("OCCURNAME"));
		//
		// ketDqmDTO.setOccurDate(rs.getString("OCCURDATE"));
		// ketDqmDTO.setDefectDivName(rs.getString("DEFECTDIVNAME"));
		// ketDqmDTO.setDefectDivCode(rs.getString("DEFECTDIVCODE"));
		// ketDqmDTO.setDefectTypeName(rs.getString("DEFECTTYPENAME"));
		// ketDqmDTO.setDefectTypeCode(rs.getString("DEFECTTYPECODE"));
		//
		// ketDqmDTO.setRelatedPart(rs.getString("PART_NUM"));
		// ketDqmDTO.setRelatedPartOid(rs.getString("PART_OID"));
		//
		// ketDqmDTO.setRelatedEcrNo(rs.getString("ECR_NO"));
		//
		// ketDqmDTO.setReviewDate(rs.getString("REVIEWDATE"));

		ketDqmDTO.setOutputCheck(rs.getString("DQMCHECK"));
		ketDqmDTO.setReviewDate(rs.getString("REVIEWDATE"));
		ketDqmDTO.setDqmLinkOid(rs.getString("DQMLINK_OID"));

		String headerOid = (String) rs.getString("DQM_OID");
		String raiseOid = (String) rs.getString("RAISE_OID");

		if (!StringUtil.isEmpty(headerOid)) {
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject("ext.ket.dqm.entity.KETDqmHeader:" + headerOid);
		    ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
		}
		if (!StringUtil.isEmpty(raiseOid)) {
		    KETDqmRaise ketDqmRaise = (KETDqmRaise) CommonUtil.getObject("ext.ket.dqm.entity.KETDqmRaise:" + raiseOid);
		    ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmRaise, ketDqmDTO);
		}

		ketDqmDTOList.add(ketDqmDTO);
	    }

	    rs.close();

	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return ketDqmDTOList;
    }

    public List<AssessTaskResultDTO> getAssessQueryList(E3PSTask task, ProductProject project) throws Exception {
	List<AssessTaskResultDTO> queryList = new ArrayList<AssessTaskResultDTO>();

	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);

	    sb.append("	SELECT										\n");
	    sb.append("	MAIN.IDA2A2 TASK_OID, SUB.DOCLINK_OID, SUB.DOCCLASSNAME, SUB.OUTPUT_OID, SUB.OUTPUTNAME, SUB.OBJTYPE, SUB.ISPRIMARY, SUB.CREATESTAMPA2  									\n");
	    sb.append("	, MAIN.RESULT_OID, MAIN.REV, MAIN.DESCRIPTION              									\n");
	    sb.append("	FROM									\n");
	    sb.append("	  (                									\n");
	    sb.append("	    SELECT A0.IDA2A2, A2.IDA2A2 ASSESS_OID, A2.IDA2A2 RESULT_OID, A2.REV, A2.DESCRIPTION									\n");
	    sb.append("	     FROM E3PSTask A0									\n");
	    sb.append("	     , AssessTemplateTaskLink A1									\n");
	    sb.append("	     , KETTaskAssessResult A2                									\n");
	    sb.append("	    WHERE A0.idA2A2 = " + task.getPersistInfo().getObjectIdentifier().getId() + "									\n");
	    sb.append("	    AND (A0.idA2A2 = A1.idA3B5)									\n");
	    sb.append("	    AND (A1.idA3A5 = A2.idA2A2)									\n");
	    sb.append("	    ) MAIN									\n");
	    sb.append("	    , (SELECT A7.IDA3B5 ASSESS_OID, A0.IDA2A2 TASK_OID, A5.IDA2A2 DOCLINK_OID, A5.DOCCLASSNAME, A4.IDA2A2 OUTPUT_OID, A4.OUTPUTNAME, A4.OBJTYPE, A4.ISPRIMARY, A4.CREATESTAMPA2									\n");
	    sb.append("	     FROM E3PSTask A0,      									\n");
	    sb.append("	     AssessTemplateTaskLink A1,									\n");
	    sb.append("	     KETTaskAssessResult A2,									\n");
	    sb.append("	     ProductProject A6,                 									\n");
	    sb.append("	     AssessProjectOutputLink A7,                 									\n");
	    sb.append("	     ProjectOutput A4,                									\n");
	    sb.append("	     OutputDocumentLink A5									\n");
	    sb.append("	     WHERE 1=1                									\n");
	    sb.append("	     AND (A0.idA2A2 = " + task.getPersistInfo().getObjectIdentifier().getId() + " )                									\n");
	    sb.append("	     AND (A0.idA2A2 = A1.idA3B5)									\n");
	    sb.append("	     AND (A1.idA3A5 = A2.idA2A2)									\n");
	    sb.append("	     AND (A0.idA3B4 = A6.idA2A2)									\n");
	    sb.append("	     AND (A2.IDA2A2 = A7.IDA3B5(+))									\n");
	    sb.append("	     AND (A7.IDA3A5 = A4.IDA2A2(+))									\n");
	    sb.append("	     AND (A4.idA2A2 = A5.idA3A5(+))                 									\n");
	    sb.append("	    AND EXISTS                        									\n");
	    sb.append("	        (SELECT 'T'                    									\n");
	    sb.append("	           FROM PRODUCTPROJECT PJT, E3PSTASK TASK    									\n");
	    sb.append("	        WHERE TASK.IDA3B4 = PJT.IDA2A2            									\n");
	    sb.append("	        AND PJT.IDA2A2 = " + CommonUtil.getOIDLongValue(project) + "    									\n");
	    sb.append("	        AND TASK.IDA2A2 = A0.IDA2A2)									\n");
	    sb.append("	     ) SUB									\n");
	    sb.append("	WHERE MAIN.Assess_OID = SUB.Assess_OID(+)									\n");
	    sb.append("	ORDER BY MAIN.REV, SUB.CREATESTAMPA2								\n");

	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    while (rs.next()) {
		AssessTaskResultDTO rsDto = new AssessTaskResultDTO();

		rsDto.setTaskOid(rs.getString("TASK_OID"));
		rsDto.setOutputOid(rs.getString("OUTPUT_OID"));
		rsDto.setOutputName(rs.getString("OUTPUTNAME"));
		rsDto.setObjType(rs.getString("OBJTYPE"));
		rsDto.setIsPrimary(rs.getString("ISPRIMARY"));
		rsDto.setAssessOid(rs.getString("RESULT_OID"));
		rsDto.setRev(rs.getString("REV"));
		rsDto.setDesc(rs.getString("DESCRIPTION"));
		rsDto.setDocClassName(rs.getString("DOCCLASSNAME"));
		rsDto.setDocLinkOid(rs.getString("DOCLINK_OID"));

		queryList.add(rsDto);
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}
	return queryList;
    }

    public AssessTaskResultDTO setAssResult(AssessTaskResultDTO assResultDto, OutputDocumentLink link) {

	try {
	    E3PSTask task = (E3PSTask) CommonUtil.getObject(assResultDto.getTaskOid());
	    E3PSTaskData taskData = new E3PSTaskData(task);
	    int targetScore = 0;
	    int resultScore = 0;
	    int conditionScore = (taskData != null && !StringUtil.isEmpty(taskData.drvalueCondition)) ? Integer
		    .parseInt(taskData.drvalueCondition) : 0;
	    String docOID = "VR:" + link.getDocClassName() + ":" + link.getBranchIdentifier();
	    RevisionControlled document = (RevisionControlled) CommonUtil.getObject(docOID);

	    boolean isDrcheck = false;

	    if (document instanceof KETProjectDocument) {
		// 문서분류체계의 DRCHECKSHEET대상여부를 가지고 DR관리 대상인지 판단
		KETDocumentCategory docCate = null;
		Kogger.debug(getClass(), "KETProjectDocument////////////");

		QueryResult r = PersistenceHelper.manager.navigate(document, "documentCategory", KETDocumentCategoryLink.class);
		if (r != null) {
		    if (r.hasMoreElements()) {
			docCate = (KETDocumentCategory) r.nextElement();
			if (docCate.getIsDRCheckSheet()) {
			    KETProjectDocument drDocument = (KETProjectDocument) document;
			    targetScore = (taskData != null && !StringUtil.isEmpty(taskData.drvalue)) ? Integer.parseInt(taskData.drvalue)
				    : 0;
			    resultScore = drDocument.getDRCheckPoint();
			    isDrcheck = true;
			}
		    }
		}

	    }
	    if (isDrcheck) {
		String assResult = "NONE";
		if (resultScore > 0 && resultScore >= targetScore) {
		    assResult = "GOOD";
		} else if (conditionScore > 0 && resultScore >= conditionScore) {
		    assResult = "조건부승인";
		} else if (resultScore < targetScore) {
		    assResult = "NG";
		}
		assResultDto.setAssResult(assResult);
		assResultDto.setTargetScore(Integer.toString(targetScore));
		assResultDto.setResultScore(Integer.toString(resultScore));
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return assResultDto;
    }

    @Override
    public List<AssessTaskResultDTO> getOnlyTaskAssessResultByTask(String taskOid) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	List<AssessTaskResultDTO> assessDTOList = new ArrayList<AssessTaskResultDTO>();
	ProductProject project = (ProductProject) task.getProject();

	List<AssessTaskResultDTO> assessResultTempDTOList = new ArrayList<AssessTaskResultDTO>();

	List<AssessTaskResultDTO> queryList = new ArrayList<AssessTaskResultDTO>();

	queryList = this.getAssessQueryList(task, project);

	int rev = 0;
	AssessTaskResultDTO assResultDto = null;

	if (queryList != null) {

	    for (int idx = 0; idx < queryList.size(); idx++) {

		AssessTaskResultDTO assessDTO = new AssessTaskResultDTO();

		AssessTaskResultDTO rsltDto = new AssessTaskResultDTO();

		rsltDto = (AssessTaskResultDTO) queryList.get(idx);
		String assessOid = rsltDto.getAssessOid();
		String docLinkOid = rsltDto.getDocLinkOid();
		TaskAssessResult assessRstObj = new TaskAssessResult();
		OutputDocumentLink outputLinkObj = null;
		if (!StringUtil.isEmpty(assessOid)) {
		    assessRstObj = (TaskAssessResult) CommonUtil.getObject("e3ps.project.TaskAssessResult:" + assessOid);
		}

		if (rev != assessRstObj.getRev()) {
		    rev = assessRstObj.getRev();
		    assResultDto = new AssessTaskResultDTO();
		    assResultDto.setRev("" + rev);
		}

		if (!StringUtil.isEmpty(docLinkOid)) {

		    outputLinkObj = (OutputDocumentLink) CommonUtil.getObject("e3ps.project.OutputDocumentLink:" + docLinkOid);
		    assessDTO.setTaskOid(taskOid);
		    assResultDto.setTaskOid(taskOid);
		    assResultDto = this.setAssResult(assResultDto, outputLinkObj);
		    assessResultTempDTOList.add(assResultDto);
		}

		assessDTO.setAssResult("NONE");
		assessDTO.setTargetScore("0");
		assessDTO.setResultScore("0");

		assessDTO.setTaskOid(task.getPersistInfo().getObjectIdentifier().getStringValue());
		assessDTO.setAssessOid(assessRstObj.getPersistInfo().getObjectIdentifier().getStringValue());
		assessDTO.setRev("" + assessRstObj.getRev());

		assessDTOList.add(assessDTO);

	    }
	}
	for (int i = 0; i < assessDTOList.size(); i++) {
	    AssessTaskResultDTO gDto = assessDTOList.get(i);

	    for (int j = 0; j < assessResultTempDTOList.size(); j++) {
		if (StringUtils.isNotEmpty(assessResultTempDTOList.get(j).getAssResult())
		        && gDto.getRev().equals(assessResultTempDTOList.get(j).getRev())) {
		    assessDTOList.get(i).setAssResult(assessResultTempDTOList.get(j).getAssResult());
		    assessDTOList.get(i).setTargetScore(assessResultTempDTOList.get(j).getTargetScore());
		    assessDTOList.get(i).setResultScore(assessResultTempDTOList.get(j).getResultScore());
		}
	    }

	}
	return assessDTOList;
    }

    /**
     * 평가관리 Task 결과 조회
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getTaskAssessResultList
     * @작성자 : 황정태
     * @작성일 : 2017. 7. 19.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<AssessTaskResultDTO> getTaskAssessResultList(String taskOid) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	// E3PSProject project = (E3PSProject) task.getProject();

	if (!(task.getProject() instanceof ProductProject)) {
	    return null;
	}

	List<AssessTaskResultDTO> assessDTOList = new ArrayList<AssessTaskResultDTO>();
	ProductProject project = (ProductProject) task.getProject();

	List<AssessTaskResultDTO> assessResultTempDTOList = new ArrayList<AssessTaskResultDTO>();

	List<AssessTaskResultDTO> queryList = new ArrayList<AssessTaskResultDTO>();

	QueryResult qr = null;

	if (this.getNextAssessRev(taskOid) == 1) {
	    /*
	     * 평가관리(구DR) 에 차수관리기능 적용 이전에 생성된 타스크에 TaskAssessResult를 생성하고 문서링크를 생성하기 위함
	     */

	    Transaction trx = new Transaction();
	    try {
		trx.start();
		// qr = ProjectOutputHelper.manager.getTaskOutputDocLink(task);// 해당 타스크에 연결된 산출물을 검색한다
		qr = ProjectOutputHelper.manager.getTaskOutput(task);// 해당 타스크에 연결된 산출물을 검색한다
		TaskAssessResult taskAssess = null;
		if (qr != null && qr.size() > 0) {// 기 연결된 산출물이 있다면 TaskAssessResult 생성
		    taskAssess = this.saveTaskAssessResult(taskOid);

		}

		while (qr.hasMoreElements()) {// TaskAssessResult에 해당 TASK에 연계된 산출물 링크를 생성한다
		    Object[] object = (Object[]) qr.nextElement();
		    ProjectOutput output = (ProjectOutput) object[0];
		    ProjectOutputHelper.setSelectAssessOid(output, CommonUtil.getOIDString(taskAssess));
		    // OutputDocumentLink docLink = (OutputDocumentLink) object[0];
		    // ProjectOutputHelper.setSelectAssessOid(docLink.getOutput(), CommonUtil.getOIDString(taskAssess));
		}

		trx.commit();
	    } catch (Exception e) {
		e.printStackTrace();
		System.out.println("평가관리 차수 객체 생성 실패 !");
		trx.rollback();
	    } finally {
		if (trx != null) {
		    trx = null;
		}
	    }

	}

	queryList = this.getAssessQueryList(task, project);

	int rev = 0;
	AssessTaskResultDTO assResultDto = null;

	if (queryList != null) {

	    for (int idx = 0; idx < queryList.size(); idx++) {
		// Object[] objArr = (Object[]) qr.nextElement();

		AssessTaskResultDTO assessDTO = new AssessTaskResultDTO();

		AssessTaskResultDTO rsltDto = new AssessTaskResultDTO();

		rsltDto = (AssessTaskResultDTO) queryList.get(idx);
		String assessOid = rsltDto.getAssessOid();
		String outputOid = rsltDto.getOutputOid();
		String docLinkOid = rsltDto.getDocLinkOid();
		TaskAssessResult assessRstObj = new TaskAssessResult();
		ProjectOutput outputRstObj = null;
		OutputDocumentLink outputLinkObj = null;
		if (!StringUtil.isEmpty(assessOid)) {
		    assessRstObj = (TaskAssessResult) CommonUtil.getObject("e3ps.project.TaskAssessResult:" + assessOid);
		}
		if (!StringUtil.isEmpty(outputOid)) {
		    outputRstObj = (ProjectOutput) CommonUtil.getObject("e3ps.project.ProjectOutput:" + outputOid);
		}

		if (rev != assessRstObj.getRev()) {
		    rev = assessRstObj.getRev();
		    assResultDto = new AssessTaskResultDTO();
		    assResultDto.setRev("" + rev);
		}

		if (!StringUtil.isEmpty(docLinkOid)) {
		    String docClassName = rsltDto.getDocClassName();
		    // if (docClassName != null && (docClassName.indexOf("EPMDocument") >= 0 || docClassName.indexOf("KETProjectDocument")
		    // >= 0)) {
		    // }
		    outputLinkObj = (OutputDocumentLink) CommonUtil.getObject("e3ps.project.OutputDocumentLink:" + docLinkOid);
		    assessDTO.setTaskOid(taskOid);
		    assResultDto.setTaskOid(taskOid);
		    assResultDto = this.setAssResult(assResultDto, outputLinkObj);
		    assessResultTempDTOList.add(assResultDto);
		}

		assessDTO.setAssResult("NONE");
		assessDTO.setTargetScore("0");
		assessDTO.setResultScore("0");

		assessDTO.setTaskOid(task.getPersistInfo().getObjectIdentifier().getStringValue());
		assessDTO.setAssessOid(assessRstObj.getPersistInfo().getObjectIdentifier().getStringValue());
		assessDTO.setRev("" + assessRstObj.getRev());
		/*
	         * int okCntInt = assessRstObj.getOkCnt(); int ngCntInt = assessRstObj.getNgCnt(); if (ngCntInt > 0) {
	         * trustDTO.setAssResult("NG"); } else { trustDTO.setAssResult("OK"); }
	         */

		if (outputRstObj != null) {
		    assessDTO.setOutputOid(outputRstObj.getPersistInfo().getObjectIdentifier().getStringValue());
		    // 문서제목
		    assessDTO.setObjName(outputRstObj.getOutputName());
		    if (outputRstObj.getObjType().equals("DOC")) {
			// 문서타입
			assessDTO.setObjType("문서");
		    } else if (outputRstObj.getObjType().equals("DWG")) {
			assessDTO.setObjType("도면");
		    } else if (outputRstObj.getObjType().equals("TRY")) {
			assessDTO.setObjType("Try조건표");
		    } else if (outputRstObj.getObjType().equals("GATE")) {
			assessDTO.setObjType("Gate");
		    } else if (outputRstObj.getObjType().equals("ECO")) {
			assessDTO.setObjType("ECO");
		    } else if (outputRstObj.getObjType().equals("QLP")) {
			assessDTO.setObjType("품질");
		    } else if (outputRstObj.getObjType().equals("ETC")) {
			assessDTO.setObjType("기타");
		    }

		    // 필수세팅
		    if (outputRstObj.isIsPrimary()) {
			assessDTO.setIsPrimary("필수");
		    } else {
			assessDTO.setIsPrimary("");
		    }
		} else {
		    assessDTO.setOutputOid("");
		    assessDTO.setObjName("");
		    assessDTO.setObjType("");
		    assessDTO.setIsPrimary("");
		}

		PeopleData peopleData = null;
		if (outputRstObj != null && outputLinkObj != null) {
		    ProjectOutputData outputData = new ProjectOutputData(outputRstObj);

		    // if (document instanceof WTDocument || document instanceof EPMDocument) {
		    if (outputRstObj.getObjType().equals("DOC") || outputRstObj.getObjType().equals("DWG")) {
			String objOID = "VR:" + outputLinkObj.getDocClassName() + ":" + outputLinkObj.getBranchIdentifier();
			RevisionControlled document = (RevisionControlled) CommonUtil.getObject(objOID);
			WTUser duser = (WTUser) document.getCreator().getPrincipal();
			// out.println(duser);
			if (duser != null) {
			    // peopleData = new PeopleData(outputUser);
			    peopleData = new PeopleData(duser);
			    // String peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
			}

			assessDTO.setLastModifier(peopleData.name);

			String lastVer = document.getVersionDisplayIdentifier().toString();
			assessDTO.setObjVer(lastVer);

			String state = document.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			assessDTO.setObjStatus(state);

			String modifyDate = DateUtil.getDateString(document.getPersistInfo().getModifyStamp(), "D");
			modifyDate = modifyDate.substring(2, 10);
			assessDTO.setObjCreateDate(modifyDate);

			ObjectData data = new ObjectData(document);
			String fileUrl = data.getFileUrl();

			String icon = data.getIcon();
			FormatContentHolder holder = (FormatContentHolder) ContentHelper.service.getContents((ContentHolder) document);
			ContentItem item = holder.getPrimary();
			ContentInfo info = null;
			if (holder != null) {
			    info = ContentUtil.getContentInfo(holder, item);
			    if (item != null) {
				icon = info.getIconURLStr();
				icon = icon.substring(icon.indexOf("<img"));
			    }
			}
			if (fileUrl == null && data.getSecondary() != null) {
			    for (int i = 0; i < data.getSecondary().length; i++) {
				Object oo[] = (Object[]) data.getSecondary()[i];
				if (oo == null) {
				    continue;
				} else {
				    fileUrl = (String) oo[0];
				    break;
				}
			    }
			}
			if (fileUrl == null) {
			    fileUrl = "";
			    icon = "&nbsp;";
			}
			assessDTO.setObjFileIcon(icon);

			String progressRatio = outputRstObj.getCompletion() + "%";
			assessDTO.setProgressRatio(progressRatio);

			assessDTO.setGateTarget(outputRstObj.getSubjectType());
			assessDTO.setGateTemplate(fileUrl);

		    } else if (outputRstObj.getObjType().equals("ECO")) {
			KETProdChangeOrder prodChangeOrderObj = null;
			KETMoldChangeOrder moldChangeOrderObj = null;
			qr = PersistenceHelper.manager.navigate(outputRstObj, "change", KETProdChangeOrderOutputLink.class);

			WTUser duser = new WTUser();
			String lastVer = "";
			String state = "";
			String modifyDate = "";

			while (qr.hasMoreElements()) {
			    prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
			    duser = (WTUser) prodChangeOrderObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(prodChangeOrderObj);
			    state = prodChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(prodChangeOrderObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);
			}

			qr = PersistenceHelper.manager.navigate(outputRstObj, "change", KETMoldChangeOrderOutputLink.class);

			while (qr.hasMoreElements()) {
			    moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
			    duser = (WTUser) moldChangeOrderObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(moldChangeOrderObj);
			    state = moldChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(moldChangeOrderObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);
			}

			if (duser != null) {
			    peopleData = new PeopleData(duser);
			}
			assessDTO.setLastModifier(peopleData.name);

			assessDTO.setObjVer(lastVer);

			assessDTO.setObjStatus(state);

			assessDTO.setObjCreateDate(modifyDate);

			assessDTO.setObjFileIcon("");

			String progressRatio = outputRstObj.getCompletion() + "%";
			assessDTO.setProgressRatio(progressRatio);

			assessDTO.setGateTarget(outputRstObj.getSubjectType());
			assessDTO.setGateTemplate("");

			// out.println(isSecu);
		    } else if (outputRstObj.getObjType().equals("QLP")) {
			KETDqmRaise raise = null;
			qr = PersistenceHelper.manager.navigate(outputRstObj, "dqm", KETDqmRaiseOutputLink.class);

			WTUser duser = new WTUser();
			String lastVer = "";
			String state = "";
			String modifyDate = "";

			while (qr.hasMoreElements()) {
			    raise = (KETDqmRaise) qr.nextElement();
			    duser = (WTUser) raise.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(raise);
			    state = raise.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(raise.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);
			}

			if (duser != null) {
			    peopleData = new PeopleData(duser);
			}
			assessDTO.setLastModifier(peopleData.name);

			assessDTO.setObjVer(lastVer);

			assessDTO.setObjStatus(state);

			assessDTO.setObjCreateDate(modifyDate);

			assessDTO.setObjFileIcon("");

			String progressRatio = outputRstObj.getCompletion() + "%";
			assessDTO.setProgressRatio(progressRatio);

			assessDTO.setGateTarget(outputRstObj.getSubjectType());
			assessDTO.setGateTemplate("");

			// out.println(isSecu);

		    } else if (outputRstObj.getObjType().equals("TRY")) {

			KETTryMold moldTryConditionObj = null;
			KETTryPress pressTryConditionObj = null;
			qr = PersistenceHelper.manager.navigate(outputRstObj, "tryMold", KETTryMoldOutputLink.class);

			WTUser duser = new WTUser();
			String lastVer = "";
			String state = "";
			String modifyDate = "";
			String fileUrl = "";
			String icon = "";

			while (qr.hasMoreElements()) {
			    moldTryConditionObj = (KETTryMold) qr.nextElement();
			    duser = (WTUser) moldTryConditionObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(moldTryConditionObj);
			    state = moldTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(moldTryConditionObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);

			    ObjectData data = new ObjectData(moldTryConditionObj);
			    fileUrl = data.getFileUrl();

			    icon = data.getIcon();
			    FormatContentHolder holder = (FormatContentHolder) ContentHelper.service
				    .getContents((ContentHolder) moldTryConditionObj);
			    ContentItem item = holder.getPrimary();
			    ContentInfo info = null;
			    if (holder != null) {
				info = ContentUtil.getContentInfo(holder, item);
				if (item != null) {
				    icon = info.getIconURLStr();
				    icon = icon.substring(icon.indexOf("<img"));
				}
			    }
			    if (fileUrl == null && data.getSecondary() != null) {
				for (int i = 0; i < data.getSecondary().length; i++) {
				    Object oo[] = (Object[]) data.getSecondary()[i];
				    if (oo == null) {
					continue;
				    } else {
					fileUrl = (String) oo[0];
					break;
				    }
				}
			    }
			    if (fileUrl == null) {
				fileUrl = "";
				icon = "&nbsp;";
			    }
			}

			qr = PersistenceHelper.manager.navigate(outputRstObj, "tryPress", KETTryPressOutputLink.class);

			while (qr.hasMoreElements()) {
			    pressTryConditionObj = (KETTryPress) qr.nextElement();
			    duser = (WTUser) pressTryConditionObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(pressTryConditionObj);
			    state = pressTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(pressTryConditionObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);

			    ObjectData data = new ObjectData(moldTryConditionObj);
			    fileUrl = data.getFileUrl();

			    icon = data.getIcon();
			    FormatContentHolder holder = (FormatContentHolder) ContentHelper.service
				    .getContents((ContentHolder) moldTryConditionObj);
			    ContentItem item = holder.getPrimary();
			    ContentInfo info = null;
			    if (holder != null) {
				info = ContentUtil.getContentInfo(holder, item);
				if (item != null) {
				    icon = info.getIconURLStr();
				    icon = icon.substring(icon.indexOf("<img"));
				}
			    }
			    if (fileUrl == null && data.getSecondary() != null) {
				for (int i = 0; i < data.getSecondary().length; i++) {
				    Object oo[] = (Object[]) data.getSecondary()[i];
				    if (oo == null) {
					continue;
				    } else {
					fileUrl = (String) oo[0];
					break;
				    }
				}
			    }
			    if (fileUrl == null) {
				fileUrl = "";
				icon = "&nbsp;";
			    }
			}

			if (duser != null) {
			    peopleData = new PeopleData(duser);
			}
			assessDTO.setLastModifier(peopleData.name);

			assessDTO.setObjVer(lastVer);

			assessDTO.setObjStatus(state);

			assessDTO.setObjCreateDate(modifyDate);

			assessDTO.setObjFileIcon(icon);

			String progressRatio = outputRstObj.getCompletion() + "%";
			assessDTO.setProgressRatio(progressRatio);

			assessDTO.setGateTarget(outputRstObj.getSubjectType());
			assessDTO.setGateTemplate(fileUrl);

		    } else {
			assessDTO.setLastModifier("");
			assessDTO.setObjVer("");
			assessDTO.setObjStatus("");
			assessDTO.setObjCreateDate("");
			assessDTO.setObjFileIcon("");
			assessDTO.setProgressRatio("");
			assessDTO.setGateTarget("");
			assessDTO.setGateTemplate("");
		    }
		} else {
		    assessDTO.setLastModifier("");
		    assessDTO.setObjVer("");
		    assessDTO.setObjStatus("");
		    assessDTO.setObjCreateDate("");
		    assessDTO.setObjFileIcon("");
		    assessDTO.setProgressRatio("");
		    assessDTO.setGateTarget("");
		    assessDTO.setGateTemplate("");

		}

		// 객체를 가져와야함

		assessDTOList.add(assessDTO);

	    }
	}
	for (int i = 0; i < assessDTOList.size(); i++) {
	    AssessTaskResultDTO gDto = assessDTOList.get(i);

	    for (int j = 0; j < assessResultTempDTOList.size(); j++) {
		if (StringUtils.isNotEmpty(assessResultTempDTOList.get(j).getAssResult())
		        && gDto.getRev().equals(assessResultTempDTOList.get(j).getRev())) {
		    assessDTOList.get(i).setAssResult(assessResultTempDTOList.get(j).getAssResult());
		    assessDTOList.get(i).setTargetScore(assessResultTempDTOList.get(j).getTargetScore());
		    assessDTOList.get(i).setResultScore(assessResultTempDTOList.get(j).getResultScore());
		}
	    }

	}
	return assessDTOList;
    }

    public void EndAssessResultByRev(String taskOid, TaskAssessResult oldtaskAssess) throws Exception {// 이전 차수 점수확정
	List<AssessTaskResultDTO> assessDTOList = getTaskAssessResultList(taskOid);

	int old_rev = oldtaskAssess.getRev();

	for (int i = 0; i < assessDTOList.size(); i++) {

	    if (Integer.parseInt(assessDTOList.get(i).getRev()) == old_rev) {
		oldtaskAssess.setResult(assessDTOList.get(i).getAssResult());
		oldtaskAssess.setResultScore(assessDTOList.get(i).getResultScore());
		oldtaskAssess.setTargetScore(assessDTOList.get(i).getTargetScore());
		PersistenceHelper.manager.save(oldtaskAssess);

		break;
	    } else {
		continue;
	    }
	}
    }

    public void EndAllAssessResultByTask(String taskOid) throws Exception {// 전 차수 점수확정

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	TaskAssessResult assRst = null;

	List<AssessTaskResultDTO> assessDTOList = getTaskAssessResultList(taskOid);

	QueryResult qr = PersistenceHelper.manager.navigate(task, AssessTemplateTaskLink.ASSESS_ROLE, AssessTemplateTaskLink.class, false);

	while (qr.hasMoreElements()) {
	    AssessTemplateTaskLink taskLink = (AssessTemplateTaskLink) qr.nextElement();
	    assRst = taskLink.getAssess();

	    for (int i = 0; i < assessDTOList.size(); i++) {
		if (StringUtils.isEmpty(assRst.getResult())) {
		    assRst.setResult(assessDTOList.get(i).getAssResult());
		    assRst.setResultScore(assessDTOList.get(i).getResultScore());
		    assRst.setTargetScore(assessDTOList.get(i).getTargetScore());
		    PersistenceHelper.manager.save(assRst);
		}

	    }
	}

    }

    @Override
    public void deleteAssessByTask(E3PSTask task) throws Exception {// 연결된 산출물링크가 없으면 해당 차수를 삭제한다
	QueryResult qr = PersistenceHelper.manager.navigate(task, AssessTemplateTaskLink.ASSESS_ROLE, AssessTemplateTaskLink.class, false);
	QueryResult qr2 = null;

	TaskAssessResult assRst = null;

	while (qr.hasMoreElements()) {
	    AssessTemplateTaskLink taskLink = (AssessTemplateTaskLink) qr.nextElement();
	    assRst = taskLink.getAssess();
	    qr2 = PersistenceHelper.manager.navigate(assRst, AssessProjectOutputLink.OUTPUT_ROLE, AssessProjectOutputLink.class, false);
	    if (qr2.size() == 0) {
		PersistenceHelper.manager.delete(assRst);
	    }
	}
    }

    @Override
    public QueryResult getOutPutByTask(E3PSTask task) throws Exception {

	QuerySpec query = new QuerySpec();
	int idxA = query.appendClassList(TaskAssessResult.class, false);
	int idxB = query.appendClassList(AssessTemplateTaskLink.class, false);
	int idxC = query.appendClassList(AssessProjectOutputLink.class, true);

	query.appendWhere(
	        new SearchCondition(AssessTemplateTaskLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(task)),
	        new int[] { idxB });
	query.appendAnd();
	ClassAttribute assessOid = new ClassAttribute(TaskAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute roleA = new ClassAttribute(AssessTemplateTaskLink.class, "roleAObjectRef.key.id");
	SearchCondition sc = new SearchCondition(assessOid, "=", roleA);
	query.appendWhere(sc, new int[] { idxA, idxB });

	query.appendAnd();
	ClassAttribute roleB = new ClassAttribute(AssessProjectOutputLink.class, "roleBObjectRef.key.id");
	SearchCondition sc2 = new SearchCondition(assessOid, "=", roleB);
	query.appendWhere(sc2, new int[] { idxA, idxC });

	QueryResult rs = PersistenceServerHelper.manager.query((StatementSpec) query);

	return rs;
    }

    @Override
    public TaskAssessResult saveTaskAssessResult(String taskOid) throws Exception {
	TaskAssessResult taskAssess = TaskAssessResult.newTaskAssessResult();
	int rev = getNextAssessRev(taskOid);

	if (rev > 1) {
	    TaskAssessResult oldtaskAssess = this.getTaskByAssessResultRev((E3PSTask) CommonUtil.getObject(taskOid), rev - 1);

	    if (StringUtils.isEmpty(oldtaskAssess.getResult())) {
		EndAssessResultByRev(taskOid, oldtaskAssess);// 이전 차수 점수확정처리
	    }

	}
	taskAssess.setRev(rev);
	PersistenceHelper.manager.save(taskAssess);

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	AssessTemplateTaskLink assessTaskLink = AssessTemplateTaskLink.newAssessTemplateTaskLink(taskAssess, task);
	PersistenceHelper.manager.save(assessTaskLink);
	return taskAssess;
    }

    public int getNextAssessRev(String oid) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);

	int assess_rev = 0;

	QuerySpec query = new QuerySpec();
	int idxA = query.appendClassList(TaskAssessResult.class, false);
	int idxB = query.appendClassList(AssessTemplateTaskLink.class, false);

	ClassAttribute rev = new ClassAttribute(TaskAssessResult.class, "rev");
	SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, rev);
	query.appendSelect(max, new int[] { idxA }, false);

	query.appendWhere(
	        new SearchCondition(AssessTemplateTaskLink.class, "roleBObjectRef.key.id", "=", CommonUtil.getOIDLongValue(task)),
	        new int[] { idxB });
	query.appendAnd();
	ClassAttribute assessOid = new ClassAttribute(TaskAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute roleA = new ClassAttribute(AssessTemplateTaskLink.class, "roleAObjectRef.key.id");
	SearchCondition sc = new SearchCondition(assessOid, "=", roleA);
	query.appendWhere(sc, new int[] { idxA, idxB });
	query.setAdvancedQueryEnabled(true);
	QueryResult rs = PersistenceServerHelper.manager.query((StatementSpec) query);

	while (rs.hasMoreElements()) {
	    Object[] object = (Object[]) rs.nextElement();
	    if (object[0] == null) {
		assess_rev = 0;
	    } else {
		assess_rev = Integer.parseInt(String.valueOf(object[0]));
	    }

	}
	assess_rev = assess_rev + 1;
	// TODO Auto-generated method stub
	return assess_rev;
    }

    @Override
    public E3PSTask getAssessTask(TaskAssessResult assessResult) throws Exception {
	E3PSTask task = null;
	try {
	    // if (task != null) {
	    QueryResult qr = PersistenceHelper.manager.navigate(assessResult, AssessTemplateTaskLink.TASK_ROLE,
		    AssessTemplateTaskLink.class, false);
	    while (qr.hasMoreElements()) {
		AssessTemplateTaskLink taskLink = (AssessTemplateTaskLink) qr.nextElement();
		task = (E3PSTask) taskLink.getTask();
	    }
	    // }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return task;
    }

    public TaskAssessResult getTaskByAssessResultRev(E3PSTask task, int rev) throws Exception {
	TaskAssessResult assRst = null;
	try {
	    // if (task != null) {
	    QueryResult qr = PersistenceHelper.manager.navigate(task, AssessTemplateTaskLink.ASSESS_ROLE, AssessTemplateTaskLink.class,
		    false);
	    while (qr.hasMoreElements()) {
		AssessTemplateTaskLink taskLink = (AssessTemplateTaskLink) qr.nextElement();
		if (taskLink.getAssess().getRev() == rev) {
		    assRst = taskLink.getAssess();
		}

	    }
	    // }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return assRst;
    }

    public List<TrustTaskResultDTO> getTaskAssessList(AssessTaskResultDTO dto) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(dto.getTaskOid());
	// E3PSProject project = (E3PSProject) task.getProject();
	ProductProject project = (ProductProject) task.getProject();

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTask = query.appendClassList(E3PSTask.class, false);
	int idxTaskLink = query.appendClassList(AssessTemplateTaskLink.class, false);
	int idxTrust = query.appendClassList(TaskAssessResult.class, true);

	ClassAttribute ca1 = new ClassAttribute(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca2 = new ClassAttribute(AssessTemplateTaskLink.class, "roleBObjectRef.key.id"); // E3PSTask idA3B5
	ClassAttribute ca3 = new ClassAttribute(AssessTemplateTaskLink.class, "roleAObjectRef.key.id"); // TaskAssessResult idA3A5
	ClassAttribute ca4 = new ClassAttribute(TaskAssessResult.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(ca1, SearchCondition.EQUAL, ca2);
	if (query.getConditionCount() > 0) {
	    query.appendAnd();
	}
	query.appendWhere(sc, new int[] { idxTask, idxTaskLink });

	sc = new SearchCondition(ca3, SearchCondition.EQUAL, ca4);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idxTaskLink, idxTrust });

	sc = new SearchCondition(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL, task.getPersistInfo()
	        .getObjectIdentifier().getId());
	query.appendAnd();
	query.appendWhere(sc, new int[] { idxTask });

	sc = new SearchCondition(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL, task.getPersistInfo()
	        .getObjectIdentifier().getId());
	query.appendAnd();
	query.appendWhere(sc, new int[] { idxTask });

	SearchUtil.setOrderBy(query, TaskTrustResult.class, idxTrust, TaskTrustResult.REV, false);

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

	List<TrustTaskResultDTO> trustDTOList = new ArrayList<TrustTaskResultDTO>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    TrustTaskResultDTO trustDTO = new TrustTaskResultDTO();

	    TaskTrustResult trustRstObj = (TaskTrustResult) objArr[0];

	    trustDTO.setTaskOid(task.getPersistInfo().getObjectIdentifier().getStringValue());
	    trustDTO.setTrustOid(trustRstObj.getPersistInfo().getObjectIdentifier().getStringValue());
	    trustDTO.setRev("" + trustRstObj.getRev());
	    int okCntInt = trustRstObj.getOkCnt();
	    int ngCntInt = trustRstObj.getNgCnt();
	    if (ngCntInt > 0) {
		trustDTO.setAssResult("NG");
	    } else {
		trustDTO.setAssResult("OK");
	    }
	    trustDTO.setEstimateDate(trustRstObj.getEstimateDate());
	    trustDTO.setTotCnt("" + (okCntInt + ngCntInt));
	    trustDTO.setOkCnt("" + okCntInt);
	    trustDTO.setNgCnt("" + ngCntInt);
	    trustDTO.setNgReason(trustRstObj.getDescription());

	    trustDTOList.add(trustDTO);
	}
	return trustDTOList;
    }

    /**
     * 신뢰성 Task 평가 차수 조회
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getTaskTrustLevelList
     * @작성자 : jackey88
     * @작성일 : 2014. 10. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<TrustTaskResultDTO> getTaskTrustLevelList(String taskOid) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	// E3PSProject project = (E3PSProject) task.getProject();
	ProductProject project = (ProductProject) task.getProject();

	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTask = query.appendClassList(E3PSTask.class, false);
	int idxTaskLink = query.appendClassList(TrustTemplateTaskLink.class, false);
	int idxTrust = query.appendClassList(TaskTrustResult.class, true);

	ClassAttribute ca1 = new ClassAttribute(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute ca2 = new ClassAttribute(TrustTemplateTaskLink.class, "roleBObjectRef.key.id"); // E3PSTask idA3B5
	ClassAttribute ca3 = new ClassAttribute(TrustTemplateTaskLink.class, "roleAObjectRef.key.id"); // TaskTrustResult idA3A5
	ClassAttribute ca4 = new ClassAttribute(TaskTrustResult.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(ca1, SearchCondition.EQUAL, ca2);
	if (query.getConditionCount() > 0) {
	    query.appendAnd();
	}
	query.appendWhere(sc, new int[] { idxTask, idxTaskLink });

	sc = new SearchCondition(ca3, SearchCondition.EQUAL, ca4);
	query.appendAnd();
	query.appendWhere(sc, new int[] { idxTaskLink, idxTrust });

	sc = new SearchCondition(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL, task.getPersistInfo()
	        .getObjectIdentifier().getId());
	query.appendAnd();
	query.appendWhere(sc, new int[] { idxTask });

	SearchUtil.setOrderBy(query, TaskTrustResult.class, idxTrust, TaskTrustResult.REV, false);

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

	List<TrustTaskResultDTO> trustDTOList = new ArrayList<TrustTaskResultDTO>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    TrustTaskResultDTO trustDTO = new TrustTaskResultDTO();

	    TaskTrustResult trustRstObj = (TaskTrustResult) objArr[0];

	    trustDTO.setTaskOid(task.getPersistInfo().getObjectIdentifier().getStringValue());
	    trustDTO.setTrustOid(trustRstObj.getPersistInfo().getObjectIdentifier().getStringValue());
	    trustDTO.setRev("" + trustRstObj.getRev());
	    int okCntInt = trustRstObj.getOkCnt();
	    int ngCntInt = trustRstObj.getNgCnt();
	    if (ngCntInt > 0) {
		trustDTO.setAssResult("NG");
	    } else {
		trustDTO.setAssResult("OK");
	    }
	    trustDTO.setEstimateDate(trustRstObj.getEstimateDate());
	    trustDTO.setTotCnt("" + (okCntInt + ngCntInt));
	    trustDTO.setOkCnt("" + okCntInt);
	    trustDTO.setNgCnt("" + ngCntInt);
	    trustDTO.setNgReason(trustRstObj.getDescription());

	    trustDTOList.add(trustDTO);
	}
	return trustDTOList;
    }

    /**
     * 
     * 프로젝트 하위의 모든 Gate정보를 가져와서 유형정보(A,B,1,...10)만 리턴함
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getProjectGateCheckSheetList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateTaskOutputDTO> getProjectGateCheckSheetList(String pjtOid) throws Exception {
	E3PSProject project = (E3PSProject) CommonUtil.getObject(pjtOid);
	// E3PSProject project = (E3PSProject) task.getProject();

	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	ResultSet rs = null;

	String e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(project));

	List<GateTaskOutputDTO> rstList = new ArrayList<GateTaskOutputDTO>();
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);
	    sb.append("	select 							\n");
	    sb.append("	 t.ida2a2 			TASK_OID		\n");
	    sb.append("	, level 			OUTPUT_LEVEL		\n");
	    sb.append("	, T.TASKTYPE			OUTPUT_TASKTYPE		\n");
	    sb.append("	, T.TASKNAME			OUTPUT_TASKNAME		\n");
	    sb.append("	, T.TASKTYPECATEGORY    	OUTPUT_TASKCATEGORY	\n");
	    sb.append("	from E3PSTask t						\n");
	    if (project instanceof MoldProject) {
		sb.append("	,(SELECT * FROM MoldProject WHERE ida2a2 ='" + e3psProjectOID + "') p	\n"); // 893393238
	    } else if (project instanceof ProductProject) {
		sb.append("	,(SELECT * FROM ProductProject WHERE ida2a2 ='" + e3psProjectOID + "') p	\n"); // 893393238
	    } else if (project instanceof ReviewProject) {
		sb.append("	,(SELECT * FROM ReviewProject WHERE ida2a2 ='" + e3psProjectOID + "') p	\n"); // 893393238
	    }
	    sb.append("	WHERE t.ida3b4 = p.ida2a2				\n");
	    sb.append("	AND t.TASKTYPE = 'Gate' 				\n"); // 테스크 타입이 Gate인 경우
	    sb.append("	START WITH t.ida3parentreference = 0   			\n");
	    sb.append("	CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference	\n");
	    sb.append("	ORDER SIBLINGS BY t.taskseq				\n");
	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    while (rs.next()) {

		GateTaskOutputDTO rsDto = new GateTaskOutputDTO();

		rsDto.setTaskOid(rs.getString("TASK_OID"));
		rsDto.setOutputLevel(rs.getString("OUTPUT_LEVEL"));
		rsDto.setOutputTaskType(rs.getString("OUTPUT_TASKTYPE"));
		rsDto.setOutputTaskName(rs.getString("OUTPUT_TASKNAME"));
		rsDto.setOutputTaskCategory(rs.getString("OUTPUT_TASKCATEGORY"));
		rstList.add(rsDto);
		// rstList.add(rs.getString("OUTPUT_TASKCATEGORY"));
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return rstList;
    }

    /**
     * 
     * 프로젝트 하위의 모든 Task 정보를 리턴함
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getProjectTaskTotalList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateTaskOutputDTO> getProjectTaskTotalList(String pjtOid) throws Exception {
	E3PSProject project = (E3PSProject) CommonUtil.getObject(pjtOid);
	// E3PSProject project = (E3PSProject) task.getProject();

	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	ResultSet rs = null;

	String e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(project));

	List<GateTaskOutputDTO> rstList = new ArrayList<GateTaskOutputDTO>();
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);
	    sb.append("	select 							\n");
	    sb.append("	 t.ida2a2 			TASK_OID		\n");
	    sb.append("	, level 			OUTPUT_LEVEL		\n");
	    sb.append("	, T.TASKTYPE			OUTPUT_TASKTYPE		\n");
	    sb.append("	, T.TASKNAME			OUTPUT_TASKNAME		\n");
	    sb.append("	, T.TASKTYPECATEGORY    	OUTPUT_TASKCATEGORY	\n");
	    sb.append("	from E3PSTask t						\n");
	    if (project instanceof MoldProject) {
		sb.append("	,(SELECT * FROM MoldProject WHERE ida2a2 ='" + e3psProjectOID + "') p	\n"); // 893393238
	    } else if (project instanceof ProductProject) {
		sb.append("	,(SELECT * FROM ProductProject WHERE ida2a2 ='" + e3psProjectOID + "') p	\n"); // 893393238
	    } else if (project instanceof ReviewProject) {
		sb.append("	,(SELECT * FROM ReviewProject WHERE ida2a2 ='" + e3psProjectOID + "') p	\n"); // 893393238
	    }
	    sb.append("	WHERE t.ida3b4 = p.ida2a2				\n");
	    sb.append("	START WITH t.ida3parentreference = 0   			\n");
	    sb.append("	CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference	\n");
	    sb.append("	ORDER SIBLINGS BY t.taskseq				\n");
	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    while (rs.next()) {

		GateTaskOutputDTO rsDto = new GateTaskOutputDTO();

		rsDto.setTaskOid(rs.getString("TASK_OID"));
		rsDto.setOutputLevel(rs.getString("OUTPUT_LEVEL"));
		rsDto.setOutputTaskType(rs.getString("OUTPUT_TASKTYPE"));
		rsDto.setOutputTaskName(rs.getString("OUTPUT_TASKNAME"));
		rsDto.setOutputTaskCategory(rs.getString("OUTPUT_TASKCATEGORY"));
		rstList.add(rsDto);
		// rstList.add(rs.getString("OUTPUT_TASKCATEGORY"));
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return rstList;
    }

    /**
     * 평가 결과 정보를 List 배열로 리턴
     * 
     * @param taskOid
     * @return
     * @throws Exception
     * @메소드명 : getGateAssessResultInfoList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Hashtable getGateAssessResultInfoList(String taskOid, boolean isList, int gateDegree) throws Exception {
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	ResultSet rs = null;

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String e3psTaskOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(task));
	String gateTaskName = task.getTaskName();
	E3PSProject project = (E3PSProject) task.getProject();

	String resultOutputVal = "";
	String resultAssVal = "";
	String resultDqmVal = "";
	double resultOutputDouble = 0;
	double resultAssDouble = 0;
	double resultDqmDouble = 0;
	String resultTotalVal = "";
	String resultTotalStr = "";
	String resultState = "";
	String resultStateName = "";
	String resultTemplateUrl = "";
	String projectOutputOid = "";
	String gateResultOid = "";

	Hashtable gateResultTotalHash = new Hashtable();
	GateAssessResult gateRslt = null;
	int recoveryCnt = 0;
	try {
	    List<GateTaskOutputDTO> resultDtoList1 = null;
	    List<GateCheckSheetDTO> resultDtoList2 = null;
	    List<KETDqmDTO> resultDtoList3 = null;

	    QueryResult qr = PersistenceHelper.manager.navigate(task, "assess", GateAssRsltTaskLink.class);

	    if (qr.hasMoreElements()) {
		gateRslt = (GateAssessResult) qr.nextElement();
		if (gateRslt != null) {
		    ArrayList<ContentDTO> secondArrList = ext.ket.shared.content.service.KETContentHelper.manager
			    .getSecondaryContents(gateRslt);
		    if (secondArrList != null) {
			recoveryCnt = secondArrList.size();
		    }
		}
		gateResultOid = CommonUtil.getOIDLongValue2Str(gateRslt);
		resultState = gateRslt.getState().toString();
		resultStateName = gateRslt.getLifeCycleState().getDisplay(Locale.KOREA);
	    }

	    if (isList) {
		String taskTypeCategory = task.getTaskTypeCategory();
		boolean isNonTarget = taskTypeCategory.equals("p-3") || taskTypeCategory.equals("p-4.1")
		        || taskTypeCategory.equals("p-4.2");

		if (!isNonTarget) {
		    // 산출물 연계객체 조회(평가결과GateAssessResult객체가 없을 경우도 조회)
		    resultDtoList1 = getProjectOutputList(taskOid, gateRslt, gateDegree);
		    gateResultTotalHash.put("resultGateTaskOutputList", resultDtoList1);

		    // 산출물 연계객체 조회(평가결과GateAssessResult객체가 없을 경우도 조회)
		    resultDtoList3 = getQualityProblemList(taskOid, gateRslt, gateDegree);
		    gateResultTotalHash.put("resultKETDqmList", resultDtoList3);
		} else {
		    gateResultTotalHash.put("resultGateTaskOutputList", new ArrayList<GateTaskOutputDTO>());
		    gateResultTotalHash.put("resultKETDqmList", new ArrayList<KETDqmDTO>());
		}

		// 산출물 연계객체 조회(평가결과GateAssessResult객체가 없을 경우도 조회)
		resultDtoList2 = getProjectTaskGateCheckLinkList(taskOid, gateRslt, gateDegree);
		gateResultTotalHash.put("resultGateCheckSheetList", resultDtoList2);

	    }

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);

	    // sb.append("	select 						\n");
	    // sb.append("	   gateRst.statestate		OUTPUT_STATE		\n");
	    // sb.append("	 , gateRst.ida2a2		RESULT_OID		\n");
	    // sb.append("	from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') t		\n");
	    // sb.append("	, KETGateAssessResult gateRst				\n");
	    // sb.append("	WHERE t.ida2a2 = gateRst.idA3B11				\n");
	    // rs = stmt.executeQuery(sb.toString());
	    // // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());
	    // while (rs.next()) {
	    // resultState = rs.getString("OUTPUT_STATE");
	    // gateResultOid = rs.getString("RESULT_OID");
	    // }
	    // rs.close();

	    if (gateRslt != null) {

		sb = new StringBuffer(0);
		sb.append("	select 						\n");
		sb.append("	 assLink.outputCheck		OUTPUT_CHECK		\n");
		sb.append("	from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') t		\n");
		sb.append("	, ProductProject  p		\n");
		sb.append("	, KETGateAssessResult gateRst				\n");
		sb.append("	, ketGateAssRsltTaskLink gLink				\n");
		sb.append("	, KETAssessResultOutputLink assLink			\n");
		sb.append("	, projectOutput o					\n");
		sb.append("	WHERE 1=1						\n");
		sb.append("	AND t.ida2a2 = gLink.idA3B5				\n");
		sb.append("	AND gLink.idA3A5 = gateRst.ida2a2				\n");
		sb.append("	AND t.ida3b4 =  p.ida2a2				\n");
		// sb.append("	AND p.LASTEST = 1					\n");
		// sb.append("	AND p.CHECKOUTSTATE <> 'c/o'				\n");
		sb.append("	AND gateRst.ida2a2 = assLink.idA3A5			\n");
		sb.append("	AND assLink.idA3B5 = o.ida2a2(+)			\n");
		sb.append("	AND assLink.rev = " + gateDegree + "			\n");
		sb.append("	and p.ida2a2 = o.idA3a5					\n");
		rs = stmt.executeQuery(sb.toString());
		// Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());
		int yCnt = 0;
		int gCnt = 0;
		boolean isR = false;
		boolean isRow = false;
		while (rs.next()) {

		    isRow = true;
		    String checkType = rs.getString("OUTPUT_CHECK");
		    if (!StringUtil.isEmpty(checkType)) {
			if ("G".equals(checkType)) {
			    gCnt++;
			} else if ("Y".equals(checkType)) {
			    yCnt++;
			} else if ("R".equals(checkType)) {
			    isR = true;
			    break;
			}
		    }
		}

		if (isR) {
		    resultOutputVal = "Non-Pass";
		} else {
		    if (isRow) {
			resultOutputDouble = ((1 - (0.5 * yCnt) / (gCnt + yCnt)) * 0.3 * 100);
			if (gCnt == 0 && yCnt == 0)
			    resultOutputDouble = 0;
			resultOutputVal = String.format("%.2f", resultOutputDouble);
		    } else {
			resultOutputVal = "-";
		    }
		}

		rs.close();

		sb = new StringBuffer(0);
		sb.append("	select 							\n");
		sb.append("	  NVL(DECODE(N.CODE, 'G1',LINK.sheetCheck1, 'G2',LINK.sheetCheck2			\n");
		sb.append("	  , 'G3',LINK.sheetCheck3, 'G4',LINK.sheetCheck4, 'G5',LINK.sheetCheck5, 'G6',LINK.sheetCheck6	\n");
		sb.append("	  , 'G7',LINK.sheetCheck7, 'G8',LINK.sheetCheck8, 'G9',LINK.sheetCheck9, 'G10',LINK.sheetCheck10	\n");
		sb.append("	  , 'G11',LINK.sheetCheck11, 'G12',LINK.sheetCheck12, 'G13',LINK.sheetCheck13, 'G14',LINK.sheetCheck14	\n");
		sb.append("	  , 'G15',LINK.sheetCheck15, 'G16',LINK.sheetCheck16, 'G17',LINK.sheetCheck17, 'G18',LINK.sheetCheck18	\n");
		sb.append("	  , 'G20',LINK.sheetCheck17, 'G20',LINK.sheetCheck20) ,'')	SHEET_CHECK	\n");
		sb.append("	from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') t		\n");
		sb.append("	, ProductProject  p		\n");
		sb.append("	, KETGateAssessResult gateRst				\n");
		sb.append("	, ketGateAssRsltTaskLink gLink				\n");
		sb.append("	, KETAssessResultGateCheckLink link			\n");

		sb.append("	, NUMBERCODE N						\n");
		sb.append("	WHERE 1=1						\n");
		sb.append("	AND t.ida2a2 = gLink.idA3B5				\n");
		sb.append("	AND gLink.idA3A5 = gateRst.ida2a2				\n");
		sb.append("	AND t.ida3b4 =  p.ida2a2				\n");
		// sb.append("	AND p.LASTEST = 1					\n");
		// sb.append("	AND p.CHECKOUTSTATE <> 'c/o'				\n");

		sb.append("	AND gateRst.ida2a2 = link.idA3B5			\n");
		sb.append("	AND link.rev = " + gateDegree + "			\n");
		sb.append("	AND T.TASKTYPECATEGORY = REPLACE(N.NAME,'Gate','')				\n");
		sb.append("	AND N.CODETYPE='GATELEVELNAME'				\n");

		rs = stmt.executeQuery(sb.toString());
		AssessResultOutputLink a1 = null;
		yCnt = 0;
		gCnt = 0;
		isR = false;
		isRow = false;
		while (rs.next()) {
		    isRow = true;
		    String checkType = rs.getString("SHEET_CHECK");
		    if (!StringUtil.isEmpty(checkType)) {
			if ("G".equals(checkType)) {
			    gCnt++;
			} else if ("Y".equals(checkType)) {
			    yCnt++;
			} else if ("R".equals(checkType)) {
			    isR = true;
			    break;
			}
		    }
		}

		if (isR) {
		    resultAssVal = "Non-Pass";
		} else {
		    if (isRow) {
			resultAssDouble = ((1 - (0.5 * yCnt) / (gCnt + yCnt)) * 0.4 * 100);
			if (gCnt == 0 && yCnt == 0)
			    resultAssDouble = 0;
			resultAssVal = String.format("%.2f", resultAssDouble);
		    } else {
			resultAssVal = "-";
		    }

		}

		rs.close();

		sb = new StringBuffer(0);
		sb.append("	select 							\n");
		sb.append("	 link.dqmCheck		DQM_CHECK			\n");
		sb.append("	from (SELECT * FROM E3PSTask WHERE IDA2A2 = '" + e3psTaskOID + "') t		\n");
		sb.append("	, ProductProject  p		\n");
		sb.append("	, KETGateAssessResult gateRst				\n");
		sb.append("	, ketGateAssRsltTaskLink gLink				\n");
		sb.append("	, KETAssessResultDqmHeaderLink link			\n");
		sb.append("	WHERE 1=1						\n");
		sb.append("	AND t.ida2a2 = gLink.idA3B5				\n");
		sb.append("	AND gLink.idA3A5 = gateRst.ida2a2				\n");
		sb.append("	AND t.ida3b4 =  p.ida2a2				\n");
		sb.append("	AND gateRst.ida2a2 = link.idA3B5			\n");
		sb.append("	AND link.rev = " + gateDegree + "			\n");
		rs = stmt.executeQuery(sb.toString());
		AssessResultDqmHeaderLink a = null;
		yCnt = 0;
		gCnt = 0;
		isR = false;
		isRow = false;
		while (rs.next()) {
		    isRow = true;
		    String checkType = rs.getString("DQM_CHECK");
		    if (!StringUtil.isEmpty(checkType)) {
			if ("G".equals(checkType)) {
			    gCnt++;
			} else if ("Y".equals(checkType)) {
			    yCnt++;
			} else if ("R".equals(checkType)) {
			    isR = true;
			    break;
			}
		    }
		}

		if (isR) {
		    resultDqmVal = "Non-Pass";
		} else {
		    if (isRow) {
			resultDqmDouble = ((1 - (0.5 * yCnt) / (gCnt + yCnt)) * 0.3 * 100);
			if (gCnt == 0 && yCnt == 0)
			    resultDqmDouble = 0;
			resultDqmVal = String.format("%.2f", resultDqmDouble);
		    } else {
			if (!"None".equals(resultOutputVal) && !"None".equals(resultAssVal)) {
			    if (resultDtoList3 != null && resultDtoList3.size() > 0) {

				resultDqmDouble = 0;
				resultDqmVal = "-";
			    } else {
				resultDqmDouble = 30.00;
				resultDqmVal = "30.00";
			    }
			}
		    }
		}

		rs.close();

		if ("Non-Pass".equals(resultOutputVal) || "Non-Pass".equals(resultAssVal) || "Non-Pass".equals(resultDqmVal)
		        || "None".equals(resultOutputVal) || "None".equals(resultAssVal)) {
		    if (gateRslt != null) {
			resultTotalVal = "-";
			resultTotalStr = "R";
			double db1 = 0;
			if (!"Non-Pass".equals(resultOutputVal) && !"None".equals(resultOutputVal) && !"".equals(resultOutputVal)
			        && !"-".equals(resultOutputVal)) {
			    db1 = Double.parseDouble(resultOutputVal);
			}
			double db2 = 0;
			if (!"Non-Pass".equals(resultAssVal) && !"None".equals(resultAssVal) && !"".equals(resultAssVal)
			        && !"-".equals(resultAssVal)) {
			    db2 = Double.parseDouble(resultAssVal);
			}
			double db3 = 0;
			if (!"Non-Pass".equals(resultDqmVal) && !"None".equals(resultDqmVal) && !"".equals(resultDqmVal)
			        && !"-".equals(resultDqmVal)) {
			    db3 = Double.parseDouble(resultDqmVal);
			}
			resultTotalVal = String.format("%.2f", db1 + db2 + db3);
			if (recoveryCnt > 0) {
			    resultTotalStr = "Y"; // ‘결과’ 항목이 ‘R’ 인 경우 ‘Recovery Plan’에 파일이 1개 이상 첨부 시 ‘Y’로 변경
			}
		    }
		} else {
		    double imsiDoubleTotalSum = 0;
		    imsiDoubleTotalSum = Double.parseDouble(String.format("%.2f", resultOutputDouble))
			    + Double.parseDouble(String.format("%.2f", resultAssDouble))
			    + Double.parseDouble(String.format("%.2f", resultDqmDouble));

		    resultTotalVal = String.format("%.2f", imsiDoubleTotalSum);
		    if (imsiDoubleTotalSum >= 95) {
			resultTotalStr = "G";
		    } else if (imsiDoubleTotalSum >= 85) {
			resultTotalStr = "Y";
		    } else {
			resultTotalStr = "R";
			if (recoveryCnt > 0) {
			    resultTotalStr = "Y"; // ‘결과’ 항목이 ‘R’ 인 경우 ‘Recovery Plan’에 파일이 1개 이상 첨부 시 ‘Y’로 변경
			}
		    }
		}

		sb = new StringBuffer(0);
		sb.append("        select 		");
		sb.append("         	  o.ida2a2 oid		");
		sb.append("        		, T.TASKNAME		");
		sb.append("        		, o.outputName    		");
		sb.append("        FROM  (SELECT * FROM E3PSTask WHERE ida2a2 ='" + e3psTaskOID + "') t		");
		sb.append("        		, projectOutput o		");
		sb.append("        WHERE t.ida2a2 = o.idA3b5		");
		rs = stmt.executeQuery(sb.toString());
		// Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());
		while (rs.next()) {
		    projectOutputOid = "e3ps.project.ProjectOutput:" + rs.getString("oid");
		}
		rs.close();

		if (!StringUtil.isEmpty(projectOutputOid)) {
		    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(projectOutputOid);
		    KETStandardTemplate ketStTmpl = (KETStandardTemplate) CommonUtil.getObject(output.getOutputDocOid());

		    if (ketStTmpl instanceof FormatContentHolder) {
			FormatContentHolder holder = (FormatContentHolder) ketStTmpl;
			ContentInfo info2 = ContentUtil.getPrimaryContent(holder);
			// ContentItem ctf = (ContentItem) CommonUtil.getObject(info.getContentOid());
			if (info2 != null && info2.getDownURL() != null) {
			    resultTemplateUrl = info2.getDownURL().toString();
			}
		    }

		    if (ketStTmpl != null && "".equals(resultTemplateUrl)) {
			QueryResult qrRs = ContentHelper.service.getContentsByRole((ContentHolder) ketStTmpl, ContentRoleType.SECONDARY);
			while (qrRs.hasMoreElements()) {
			    ContentHolder holder = (ContentHolder) ketStTmpl;
			    ContentItem fileContent = (ContentItem) qrRs.nextElement();
			    ContentInfo info3 = ContentUtil.getContentInfo(holder, fileContent);
			    if (info3 != null && info3.getDownURL() != null)
				resultTemplateUrl = info3.getDownURL().toString();
			}
		    }

		}

		if ("R".equals(resultTotalStr) && !StringUtil.isEmpty(resultTemplateUrl)) {
		    // resultTotalStr = "Y"; // ‘결과’ 항목이 ‘R’ 인 경우 ‘Recovery Plan’에 파일이 1개 이상 첨부 시 ‘Y’로 변경
		}

		stmt.close();
		conn.close();
	    }

	    gateResultTotalHash.put("resultOutputVal", StringUtil.checkNull(resultOutputVal));
	    gateResultTotalHash.put("resultAssVal", StringUtil.checkNull(resultAssVal));
	    gateResultTotalHash.put("resultDqmVal", StringUtil.checkNull(resultDqmVal));
	    gateResultTotalHash.put("resultTotalVal", resultTotalVal);
	    gateResultTotalHash.put("resultTotalStr", resultTotalStr);
	    gateResultTotalHash.put("resultState", StringUtil.checkNull(resultState));
	    gateResultTotalHash.put("resultStateName", StringUtil.checkNull(resultStateName));
	    gateResultTotalHash.put("resultTemplateUrl", resultTemplateUrl);
	    gateResultTotalHash.put("gateResultOid", gateResultOid);

	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return gateResultTotalHash;
    }

    /**
     * 테스크Oid정보를 가지고 연관 GateAssessResult 객체 리턴
     * 
     * @param taskOid
     * @return
     * @throws Exception
     * @메소드명 : getGateAssessResultObj
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public GateAssessResult getGateAssessResultObj(String taskOid) throws Exception {

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	GateAssessResult assSheet = null;
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idxTask = query.appendClassList(E3PSTask.class, false);
	int idxLink = query.appendClassList(GateAssRsltTaskLink.class, false);
	int idxResult = query.appendClassList(GateAssessResult.class, true);

	ClassAttribute caTask = new ClassAttribute(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id");
	ClassAttribute caBLink = new ClassAttribute(GateAssRsltTaskLink.class, "roleBObjectRef.key.id");
	ClassAttribute caALink = new ClassAttribute(GateAssRsltTaskLink.class, "roleAObjectRef.key.id");
	ClassAttribute caResult = new ClassAttribute(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id");

	sc = new SearchCondition(caTask, SearchCondition.EQUAL, caBLink);

	query.appendWhere(sc, new int[] { idxTask, idxLink });

	query.appendAnd();
	sc = new SearchCondition(caALink, SearchCondition.EQUAL, caResult);
	query.appendWhere(sc, new int[] { idxLink, idxResult });

	query.appendAnd();

	sc = new SearchCondition(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL,
	        CommonUtil.getOIDLongValue(taskOid));
	query.appendWhere(sc, new int[] { idxTask });

	SearchUtil.setOrderBy(query, E3PSTask.class, idxTask, E3PSTask.TASK_NAME, true);

	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

	if (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    assSheet = (GateAssessResult) objArr[0];
	}

	return assSheet;

    }

    /**
     * 프로젝트 하위 TRY 금형 테스크 차수 조회 try 금형 테스크 차수정보란? ㄱ. 테스크 명이 try금형_[T0]인 경우 차수는 0이된다 ㄴ. 프로젝트 하위의 모든 테스크에서 1레밸의 테스크중 "디버깅모드"란 이름의 태스크 하위에
     * (try테스크 중 "try금형"이란 이름의 테스크)가 붙는데 이중 첫번째인 경우가 1차가된다 ㄷ. 순차적으로 1레벨 테스크 명칭이 "디버깅모드"란 테스크가 나오면 하위의 try테스크 중 "try금형"이란 이름의 태스크는 2차수가 되고 ,
     * 그 다음은 순차적으로 3, 4...가 된다
     * 
     * @param taskOid
     * @return
     * @throws Exception
     * @메소드명 : getProjectTryTaskLevel
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String getProjectTryTaskLevel(String taskOid) throws Exception {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// 차수정보는 1부터 시작(최초의 try테스크의 차수는 0
	int levelNum = 0;

	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	String tryTaskName = task.getTaskName();
	E3PSProject project = (E3PSProject) task.getProject();
	String e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(project));

	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);
	    sb.append("	select 						\n");
	    sb.append("	 t.ida2a2 			TASK_OID		\n");
	    sb.append("	, o.outputName  		OUTPUT_NAME		\n");
	    sb.append("	, o.ida2a2  			OUTPUT_OID		\n");
	    sb.append("	, wu.FULLNAME			CHARGE_NAME		\n");
	    sb.append("	, assLink.outputCheck		OUTPUT_CHECK		\n");
	    sb.append("	, assLink.outputComment		OUTPUT_COMMENT		\n");
	    sb.append("	, level 			OUTPUT_LEVEL		\n");
	    sb.append("	, o.subjectType			OUTPUT_SUBJECTTYPE	\n");
	    sb.append("	, T.TASKTYPE			OUTPUT_TASKTYPE		\n");
	    sb.append("	, T.TASKNAME			OUTPUT_TASKNAME		\n");
	    sb.append("	from E3PSTask t		\n");

	    if (project instanceof MoldProject) {
		sb.append("	, (SELECT * FROM MoldProject WHERE IDA2A2 = '" + e3psProjectOID + "') p			\n");
	    } else if (project instanceof ProductProject) {
		sb.append("	, (SELECT * FROM ProductProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
	    } else if (project instanceof ReviewProject) {
		sb.append("	, (SELECT * FROM ReviewProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
	    }
	    sb.append("	, projectOutput o					\n");
	    sb.append("	, TASKMEMBERLINK tml					\n");
	    sb.append("	, PROJECTMEMBERLINK pml					\n");
	    sb.append("	, WTUSER wu						\n");
	    sb.append("	, KETAssessResultOutputLink assLink			\n");
	    // sb.append("	, KETGateAssessResult gateRst				\n");
	    sb.append("	WHERE t.ida3b4 = p.ida2a2				\n");
	    sb.append("	AND t.ida2a2 = o.idA3b5(+)				\n");
	    sb.append("	AND t.ida2a2 = tml.ida3a5(+)				\n");
	    sb.append("	AND tml.TASKMEMBERTYPE(+) = '1'				\n");
	    sb.append("	AND tml.ida3b5 = pml.ida2a2(+)				\n");
	    sb.append("	AND pml.ida3b5 = wu.ida2a2(+)				\n");
	    sb.append("	AND o.ida2a2 = assLink.idA3B5(+)			\n");
	    // sb.append("	AND assLink.idA3A5 = gateRst.ida2a2(+) 			\n");
	    sb.append("	START WITH t.ida3parentreference = 0   			\n");
	    sb.append("	CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference	\n");
	    sb.append("	ORDER SIBLINGS BY t.taskseq				\n");
	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    while (rs.next()) {

		// 테스크 타입이 Try인 경우
		if ("Try".equals(rs.getString("OUTPUT_TASKTYPE"))) {
		    String outputTaskName = rs.getString("OUTPUT_TASKNAME");
		    if (outputTaskName != null && outputTaskName.indexOf("Try금형") > 0) {
			if (outputTaskName != null && outputTaskName.indexOf("Try금형_[T0]") > 0) {
			    levelNum = 0; // 태스크명이 Try금형_[T0]는 0차수
			} else {
			    levelNum++; // 태스크명이 Try금형인 경우 1부터 순차적으로 차수가 매겨진다
			}
			if (tryTaskName.equals(rs.getString("OUTPUT_TASKNAME"))) {
			    break;
			}
		    }
		}
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return "" + levelNum;
    }

    /**
     * 프로젝트 하위의 태스크 들의 예상작업시간과 실제작업시간의 총합을 구해서 결과를 리턴한다
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : getProjectWorkTimeSumList
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<String> getProjectWorkTimeSumList(String projectOid) throws Exception {
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;

	E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);
	String e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(project));

	List<String> rstList = new ArrayList<String>();
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);
	    sb.append("	select 						\n");
	    sb.append("	 NVL(SUM(A.PLANWORKTIME),0)  PLANWORKTIME		\n");
	    sb.append("	, NVL(SUM(A.EXECWORKTIME),0) EXECWORKTIME		\n");

	    sb.append("	FROM (				\n");
	    sb.append("	select LEVEL LEV				\n");
	    sb.append("	, NVL(T.PLANWORKTIME,0)  PLANWORKTIME		\n");
	    sb.append("	, NVL(T.EXECWORKTIME,0) EXECWORKTIME		\n");
	    sb.append("	from E3PSTask t		\n");
	    if (project instanceof MoldProject) {
		sb.append("	, (SELECT * FROM MoldProject WHERE IDA2A2 = '" + e3psProjectOID + "') p			\n");
	    } else if (project instanceof ProductProject) {
		sb.append("	, (SELECT * FROM ProductProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
	    } else if (project instanceof ReviewProject) {
		sb.append("	, (SELECT * FROM ReviewProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
	    } else if (project instanceof WorkProject) {
		sb.append("	, (SELECT * FROM WorkProject WHERE IDA2A2 = '" + e3psProjectOID + "') p		\n");
	    }
	    sb.append("	WHERE t.ida3b4 = p.ida2a2				\n");
	    sb.append("	START WITH t.ida3parentreference = 0   			\n");
	    sb.append("	CONNECT BY PRIOR t.ida2a2 = t.ida3parentreference	\n");
	    sb.append("	ORDER SIBLINGS BY t.taskseq				\n");
	    sb.append("	) A							\n");
	    sb.append("	where A.LEV=1						\n");

	    rs = stmt.executeQuery(sb.toString());

	    if (rs.next()) {

		rstList.add((String) rs.getString("PLANWORKTIME"));
		rstList.add((String) rs.getString("EXECWORKTIME"));
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return rstList;
    }

    /**
     * Task 계획완료 3일전
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : getTaskThreeBefore
     * @작성자 : 황정태
     * @작성일 : 2015. 7. 02.
     * @설명 : 굳이 일수를 가지고 여러개의 메써드를 만들필요가 있나싶지만, 기존 방식을 따름
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<E3PSTask> getTaskThreeBefore() throws Exception {

	String today = getDayAgoDate(3);
	Timestamp currentDate = DateUtil.getTimestampFormat(today, "yyyy/MM/dd");

	QuerySpec qs = new QuerySpec();
	QuerySpec qs2 = new QuerySpec();
	QuerySpec qs3 = new QuerySpec();
	QuerySpec qs4 = new QuerySpec();
	// qs.setDistinct(true);
	int index1 = qs.addClassList(E3PSTask.class, true);
	int index2 = qs.addClassList(ProductProject.class, false);
	int index3 = qs.addClassList(ExtendScheduleData.class, false);

	// Product Project 검색
	SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs.appendWhere(tpsc, new int[] { index1, index2 });

	qs.appendAnd();

	SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs.appendWhere(exsc, new int[] { index1, index3 });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs);

	// Review Project 검색
	index1 = qs2.addClassList(E3PSTask.class, true);
	index2 = qs2.addClassList(ReviewProject.class, false);
	index3 = qs2.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ReviewProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs2.appendWhere(tpsc, new int[] { index1, index2 });

	qs2.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs2.appendWhere(exsc, new int[] { index1, index3 });

	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs2.appendAnd();

	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs2);

	// Mold Project 검색
	index1 = qs3.addClassList(E3PSTask.class, true);
	index2 = qs3.addClassList(MoldProject.class, false);
	index3 = qs3.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        MoldProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs3.appendWhere(tpsc, new int[] { index1, index2 });

	qs3.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs3.appendWhere(exsc, new int[] { index1, index3 });

	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs3.appendAnd();

	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs3 = " + qs3);

	// Work Project 검색
	index1 = qs4.addClassList(E3PSTask.class, true);
	index2 = qs4.addClassList(WorkProject.class, false);
	index3 = qs4.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        WorkProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs4.appendWhere(tpsc, new int[] { index1, index2 });

	qs4.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs4.appendWhere(exsc, new int[] { index1, index3 });

	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs4.appendAnd();

	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs4);

	CompoundQuerySpec compound = new CompoundQuerySpec();
	compound.setSetOperator(SetOperator.UNION_ALL);
	compound.addComponent(qs);
	compound.addComponent(qs2);
	compound.addComponent(qs3);
	compound.addComponent(qs4);

	List<E3PSTask> taskListRst = new ArrayList<E3PSTask>();
	QueryResult qr = PersistenceHelper.manager.find(compound);
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    E3PSTask task = (E3PSTask) o[0];
	    taskListRst.add(task);
	}

	return taskListRst;
    }

    /**
     * Task 계획완료 7일전
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : getTaskSevenDelay
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<E3PSTask> getTaskSevenBefore() throws Exception {

	String today = getDayAgoDate(7);
	Timestamp currentDate = DateUtil.getTimestampFormat(today, "yyyy/MM/dd");

	QuerySpec qs = new QuerySpec();
	QuerySpec qs2 = new QuerySpec();
	QuerySpec qs3 = new QuerySpec();
	QuerySpec qs4 = new QuerySpec();
	// qs.setDistinct(true);
	int index1 = qs.addClassList(E3PSTask.class, true);
	int index2 = qs.addClassList(ProductProject.class, false);
	int index3 = qs.addClassList(ExtendScheduleData.class, false);

	// Product Project 검색
	SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs.appendWhere(tpsc, new int[] { index1, index2 });

	qs.appendAnd();

	SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs.appendWhere(exsc, new int[] { index1, index3 });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs);

	// Review Project 검색
	index1 = qs2.addClassList(E3PSTask.class, true);
	index2 = qs2.addClassList(ReviewProject.class, false);
	index3 = qs2.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ReviewProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs2.appendWhere(tpsc, new int[] { index1, index2 });

	qs2.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs2.appendWhere(exsc, new int[] { index1, index3 });

	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs2.appendAnd();

	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs2);

	// Mold Project 검색
	index1 = qs3.addClassList(E3PSTask.class, true);
	index2 = qs3.addClassList(MoldProject.class, false);
	index3 = qs3.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        MoldProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs3.appendWhere(tpsc, new int[] { index1, index2 });

	qs3.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs3.appendWhere(exsc, new int[] { index1, index3 });

	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs3.appendAnd();

	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs3 = " + qs3);

	// Work Project 검색
	index1 = qs4.addClassList(E3PSTask.class, true);
	index2 = qs4.addClassList(WorkProject.class, false);
	index3 = qs4.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        WorkProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs4.appendWhere(tpsc, new int[] { index1, index2 });

	qs4.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs4.appendWhere(exsc, new int[] { index1, index3 });

	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs4.appendAnd();

	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs4);

	CompoundQuerySpec compound = new CompoundQuerySpec();
	compound.setSetOperator(SetOperator.UNION_ALL);
	compound.addComponent(qs);
	compound.addComponent(qs2);
	compound.addComponent(qs3);
	compound.addComponent(qs4);

	List<E3PSTask> taskListRst = new ArrayList<E3PSTask>();
	QueryResult qr = PersistenceHelper.manager.find(compound);
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    E3PSTask task = (E3PSTask) o[0];
	    taskListRst.add(task);
	}

	return taskListRst;
    }

    // 오늘부터 몇일전
    public String getDayAgoDate(int beforeDay) {

	GregorianCalendar today = new GregorianCalendar();

	int year = today.get(today.YEAR);
	int month = today.get(today.MONTH) + 1;
	int day = today.get(today.DATE);

	Calendar cal = Calendar.getInstance();

	cal.set(year, month - 1, day);

	cal.add(Calendar.DATE, beforeDay);

	java.util.Date weekago = cal.getTime();

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd",

	Locale.getDefault());

	return formatter.format(weekago);

    }

    /**
     * Task 계획완료 하루전
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : getTaskPlanEndDelay
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<E3PSTask> getTaskPlanEndBefore() throws Exception {

	String oneDayBefore = getDayAgoDate(1);
	Timestamp currentDate = DateUtil.getTimestampFormat(oneDayBefore, "yyyy/MM/dd");

	// 오늘 날자
	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
	// String today = formatter.format(new Date());
	//
	// Timestamp currentDate = DateUtil.getTimestampFormat(today, "yyyy/MM/dd");

	QuerySpec qs = new QuerySpec();
	QuerySpec qs2 = new QuerySpec();
	QuerySpec qs3 = new QuerySpec();
	QuerySpec qs4 = new QuerySpec();
	// qs.setDistinct(true);
	int index1 = qs.addClassList(E3PSTask.class, true);
	int index2 = qs.addClassList(ProductProject.class, false);
	int index3 = qs.addClassList(ExtendScheduleData.class, false);

	// Product Project 검색
	SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs.appendWhere(tpsc, new int[] { index1, index2 });

	qs.appendAnd();

	SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs.appendWhere(exsc, new int[] { index1, index3 });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs);

	// Review Project 검색
	index1 = qs2.addClassList(E3PSTask.class, true);
	index2 = qs2.addClassList(ReviewProject.class, false);
	index3 = qs2.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ReviewProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs2.appendWhere(tpsc, new int[] { index1, index2 });

	qs2.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs2.appendWhere(exsc, new int[] { index1, index3 });

	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs2.appendAnd();

	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs2);

	// Mold Project 검색
	index1 = qs3.addClassList(E3PSTask.class, true);
	index2 = qs3.addClassList(MoldProject.class, false);
	index3 = qs3.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        MoldProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs3.appendWhere(tpsc, new int[] { index1, index2 });

	qs3.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs3.appendWhere(exsc, new int[] { index1, index3 });

	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs3.appendAnd();

	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs3 = " + qs3);

	// Work Project 검색
	index1 = qs4.addClassList(E3PSTask.class, true);
	index2 = qs4.addClassList(WorkProject.class, false);
	index3 = qs4.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        WorkProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs4.appendWhere(tpsc, new int[] { index1, index2 });

	qs4.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs4.appendWhere(exsc, new int[] { index1, index3 });

	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs4.appendAnd();

	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "=", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs4);

	CompoundQuerySpec compound = new CompoundQuerySpec();
	compound.setSetOperator(SetOperator.UNION_ALL);
	compound.addComponent(qs);
	compound.addComponent(qs2);
	compound.addComponent(qs3);

	List<E3PSTask> taskListRst = new ArrayList<E3PSTask>();
	QueryResult qr = PersistenceHelper.manager.find(compound);
	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    E3PSTask task = (E3PSTask) o[0];
	    taskListRst.add(task);
	}

	return taskListRst;
    }

    /**
     * Task 계획완료일이전
     * 
     * @param projectOid
     * @return
     * @throws Exception
     * @메소드명 : getTaskPlanEndBeforeDelay
     * @작성자 : jackey88
     * @작성일 : 2014. 9. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<E3PSTask> getTaskPlanEndDelay() throws Exception {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
	String today = formatter.format(new Date());

	Timestamp currentDate = DateUtil.getTimestampFormat(today, "yyyy/MM/dd");

	QuerySpec qs = new QuerySpec();
	QuerySpec qs2 = new QuerySpec();
	QuerySpec qs3 = new QuerySpec();
	QuerySpec qs4 = new QuerySpec();
	// qs.setDistinct(true);
	int index1 = qs.addClassList(E3PSTask.class, true);
	int index2 = qs.addClassList(ProductProject.class, false);
	int index3 = qs.addClassList(ExtendScheduleData.class, false);

	// Product Project 검색
	SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs.appendWhere(tpsc, new int[] { index1, index2 });

	qs.appendAnd();

	SearchCondition exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs.appendWhere(exsc, new int[] { index1, index3 });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs.appendAnd();
	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "<", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs);

	// Review Project 검색
	index1 = qs2.addClassList(E3PSTask.class, true);
	index2 = qs2.addClassList(ReviewProject.class, false);
	index3 = qs2.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        ReviewProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs2.appendWhere(tpsc, new int[] { index1, index2 });

	qs2.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs2.appendWhere(exsc, new int[] { index1, index3 });

	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs2.appendAnd();

	qs2.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs2.appendAnd();
	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs2.appendAnd();

	qs2.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "<", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs2);

	// Mold Project 검색
	index1 = qs3.addClassList(E3PSTask.class, true);
	index2 = qs3.addClassList(MoldProject.class, false);
	index3 = qs3.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        MoldProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs3.appendWhere(tpsc, new int[] { index1, index2 });

	qs3.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs3.appendWhere(exsc, new int[] { index1, index3 });

	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs3.appendAnd();
	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs3.appendAnd();

	qs3.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "<", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs3 = " + qs3);

	// Work Project 검색
	index1 = qs4.addClassList(E3PSTask.class, true);
	index2 = qs4.addClassList(WorkProject.class, false);
	index3 = qs4.addClassList(ExtendScheduleData.class, false);

	tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        WorkProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
	tpsc.setFromIndicies(new int[] { index1, index2 }, 0);
	tpsc.setOuterJoin(0);
	qs4.appendWhere(tpsc, new int[] { index1, index2 });

	qs4.appendAnd();

	exsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "taskSchedule.key.id"), "=", new ClassAttribute(
	        ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME));
	exsc.setFromIndicies(new int[] { index1, index3 }, 0);
	exsc.setOuterJoin(0);
	qs4.appendWhere(exsc, new int[] { index1, index3 });

	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", "<>", "c/o"), new int[] { index2 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs4.appendAnd();

	qs4.appendWhere(new SearchCondition(E3PSTask.class, "leaf", SearchCondition.IS_TRUE, true), new int[] { index1 });
	qs4.appendAnd();
	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs4.appendAnd();

	qs4.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "<", currentDate),
	        new int[] { index3 });
	Kogger.debug(getClass(), "qs = " + qs4);

	CompoundQuerySpec compound = new CompoundQuerySpec();
	compound.setSetOperator(SetOperator.UNION_ALL);
	compound.addComponent(qs);
	compound.addComponent(qs2);
	compound.addComponent(qs3);
	compound.addComponent(qs4);

	List<E3PSTask> taskListRst = new ArrayList<E3PSTask>();
	QueryResult qr = PersistenceHelper.manager.find(compound);

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    E3PSTask task = (E3PSTask) o[0];
	    taskListRst.add(task);
	}

	return taskListRst;

    }

    /**
     * 
     * 산출물 링크 품질문제 조회
     * 
     * @param output
     * @return
     * @throws Exception
     * @메소드명 : getOutputQLP
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public KETDqmDTO getOutputQLP(ProjectOutput output) throws Exception {
	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer(0);

	E3PSTask task = (E3PSTask) output.getTask();
	String e3psTaskOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(task));

	E3PSProject project = (E3PSProject) task.getProject();
	String e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDLongValue2Str(project));

	KETDqmDTO ketDqmDTORtn = new KETDqmDTO();
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    sb.append("	SELECT A0.IDA2A2 DQM_OID					\n");
	    sb.append("		, A0.PROBLEM			\n");
	    sb.append("		, A0.PROBLEMNO			\n");
	    sb.append("		, A0.DQMSTATECODE			\n");
	    sb.append("		, A0.DQMSTATENAME			\n");
	    sb.append("		, A1.IDA2A2 RAISE_OID			\n");
	    sb.append("		, TO_CHAR(A1.CREATESTAMPA2, 'YYYY-MM-DD') DQM_CREATESTAMP			\n");
	    sb.append("		, RUSER.LAST  DQM_CREATOR			\n");
	    sb.append("		, A1.CUSTOMERCODE			\n");
	    sb.append("		, A1.CARTYPECODE			\n");
	    sb.append("		, A1.PROBLEMTYPECODE			\n");
	    sb.append("		, A1.URGENCYCODE			\n");
	    sb.append("		, A1.PARTCATEGORYCODE			\n");
	    sb.append("		, A1.CUSTOMERNAME			\n");
	    sb.append("		, A1.CARTYPENAME			\n");
	    sb.append("		, A1.PROBLEMTYPENAME			\n");
	    sb.append("		, A1.URGENCYNAME			\n");
	    sb.append("		, A1.PARTCATEGORYNAME			\n");
	    sb.append("		, A1.OCCURDIVCODE			\n");
	    sb.append("		, A1.OCCURSTEPCODE			\n");
	    sb.append("		, A1.OCCURCODE			\n");
	    sb.append("		, A1.OCCURDIVNAME			\n");
	    sb.append("		, A1.OCCURPLACENAME			\n");
	    sb.append("		, A1.OCCURSTEPNAME			\n");
	    sb.append("		, A1.OCCURNAME			\n");
	    sb.append("		, TO_CHAR(A1.OCCURDATE, 'YYYY-MM-DD') OCCURDATE			\n");
	    sb.append("		, A1.DEFECTDIVNAME			\n");
	    sb.append("		, A1.DEFECTDIVCODE			\n");
	    sb.append("		, A1.DEFECTTYPENAME			\n");
	    sb.append("		, A1.DEFECTTYPECODE			\n");
	    sb.append("		, RPMAST.WTPARTNUMBER PART_NUM			\n");
	    sb.append("		, A1.IDA2A2 PART_OID			\n");
	    sb.append("		, '' ECR_NO			\n");
	    sb.append("		, TO_CHAR(HCLOSE.REVIEWDATE, 'YYYY-MM-DD') REVIEWDATE			\n");
	    sb.append("		, '' DQMCHECK			\n");
	    sb.append("	 FROM ProjectOutput A6			\n");
	    sb.append("		 ,KETDqmRaiseOutputLink A2	\n");
	    sb.append("		 ,KETDqmRaise A1			\n");
	    sb.append("		 ,KETDqmHeader A0			\n");
	    sb.append("		 ,WTUSER RUSER			\n");
	    sb.append("		 ,WTPart RPART			\n");
	    sb.append("		 ,WTPartMaster RPMAST			\n");
	    sb.append("		 ,KETDqmAction HACTION			\n");
	    sb.append("		 ,KETDqmClose HCLOSE			\n");
	    sb.append("	 WHERE (A6.idA2A2 = A2.idA3B5) 			\n");
	    sb.append("		 AND (A2.idA3A5 = A1.IDA2A2) 			\n");
	    sb.append("		 AND (A1.idA3A7 = RUSER.IDA2A2) 			\n");
	    sb.append("		 AND (A1.idA3B8 = RPART.IDA2A2)  			\n");
	    sb.append("		 AND (RPART.IDA3MASTERREFERENCE = RPMAST.IDA2A2) 			\n");
	    sb.append("		 AND (A1.idA2A2 = A0.idA3C3) 			\n");
	    sb.append("		 AND (A0.idA3B3 = HACTION.idA2A2(+))			\n");
	    sb.append("		 AND (A0.idA3A3 = HCLOSE.idA2A2(+))			\n");
	    // sb.append("		 AND ((A0.dqmStateCode = 'ACTIONINWORK')   OR (A0.dqmStateCode = 'RAISEAPPROVED') 			\n");
	    // sb.append("			 OR (A0.dqmStateCode = 'ACTIONUNDERREVIEW') OR (A0.dqmStateCode = 'ACTIONAPPROVED') 			\n");
	    // sb.append("			 OR (A0.dqmStateCode = 'END')) 			\n");
	    sb.append("		 AND (A6.idA2A2 = " + CommonUtil.getOIDLongValue(output) + " ) 			\n");
	    sb.append("	 ORDER BY A0.problemNo DESC			\n");

	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    if (rs != null && rs.next()) {
		KETDqmDTO ketDqmDTO = new KETDqmDTO();
		// ketDqmDTO.setOid(rs.getString("DQM_OID"));
		// ketDqmDTO.setProblem(rs.getString("PROBLEM"));
		// ketDqmDTO.setProblemNo(rs.getString("PROBLEMNO"));
		// ketDqmDTO.setDqmStateCode(rs.getString("DQMSTATECODE"));
		// ketDqmDTO.setDqmStateName(rs.getString("DQMSTATENAME"));
		// ketDqmDTO.setRaiseOid(rs.getString("RAISE_OID"));
		// ketDqmDTO.setCreateStamp(rs.getString("DQM_CREATESTAMP"));
		// ketDqmDTO.setRaiseCreateUserName(rs.getString("DQM_CREATOR"));
		// ketDqmDTO.setCustomerCode(rs.getString("CUSTOMERCODE"));
		// ketDqmDTO.setCartypeCode(rs.getString("CARTYPECODE"));
		// ketDqmDTO.setProblemTypeCode(rs.getString("PROBLEMTYPECODE"));
		// ketDqmDTO.setUrgencyCode(rs.getString("URGENCYCODE"));
		// ketDqmDTO.setPartCategoryCode(rs.getString("PARTCATEGORYCODE"));
		//
		// ketDqmDTO.setCustomerName(rs.getString("CUSTOMERNAME"));
		// ketDqmDTO.setCartypeName(rs.getString("CARTYPENAME"));
		// ketDqmDTO.setProblemTypeName(rs.getString("PROBLEMTYPENAME"));
		// ketDqmDTO.setUrgencyName(rs.getString("URGENCYNAME"));
		// ketDqmDTO.setPartCategoryName(rs.getString("PARTCATEGORYNAME"));
		// ketDqmDTO.setOccurDivCode(rs.getString("OCCURDIVCODE"));
		// ketDqmDTO.setOccurStepCode(rs.getString("OCCURSTEPCODE"));
		// ketDqmDTO.setOccurCode(rs.getString("OCCURCODE"));
		//
		// ketDqmDTO.setOccurDivName(rs.getString("OCCURDIVNAME"));
		// ketDqmDTO.setOccurPlaceName(rs.getString("OCCURPLACENAME"));
		// ketDqmDTO.setOccurStepName(rs.getString("OCCURSTEPNAME"));
		// ketDqmDTO.setOccurName(rs.getString("OCCURNAME"));
		//
		// ketDqmDTO.setOccurDate(rs.getString("OCCURDATE"));
		// ketDqmDTO.setDefectDivName(rs.getString("DEFECTDIVNAME"));
		// ketDqmDTO.setDefectDivCode(rs.getString("DEFECTDIVCODE"));
		// ketDqmDTO.setDefectTypeName(rs.getString("DEFECTTYPENAME"));
		// ketDqmDTO.setDefectTypeCode(rs.getString("DEFECTTYPECODE"));
		//
		// ketDqmDTO.setRelatedPart(rs.getString("PART_NUM"));
		// ketDqmDTO.setRelatedPartOid(rs.getString("PART_OID"));
		//
		// ketDqmDTO.setRelatedEcrNo(rs.getString("ECR_NO"));
		//
		// ketDqmDTO.setReviewDate(rs.getString("REVIEWDATE"));

		ketDqmDTO.setOutputCheck(rs.getString("DQMCHECK"));
		ketDqmDTO.setReviewDate(rs.getString("REVIEWDATE"));

		String headerOid = (String) rs.getString("DQM_OID");
		String raiseOid = (String) rs.getString("RAISE_OID");
		if (!StringUtil.isEmpty(headerOid)) {
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject("ext.ket.dqm.entity.KETDqmHeader:" + headerOid);
		    ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmHeader, ketDqmDTO);
		}
		if (!StringUtil.isEmpty(raiseOid)) {
		    KETDqmRaise ketDqmRaise = (KETDqmRaise) CommonUtil.getObject("ext.ket.dqm.entity.KETDqmRaise:" + raiseOid);
		    ketDqmDTO = ketDqmDTO.KETDqmDTO(ketDqmRaise, ketDqmDTO);
		}

		ketDqmDTORtn = ketDqmDTO;
	    }

	    rs.close();

	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	return ketDqmDTORtn;
    }

    /**
     * 프로젝트의 연결된 GateAssessResult정보 가져오기
     * 
     * @param WTChangeOrder2
     * @param output
     * @param type
     * @return
     * @throws Exception
     * @메소드명 : getProjectLinkAssesResult
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<GateAssessResult> getProjectLinkAssesResult(E3PSProject pjtObj) throws Exception {
	List<GateAssessResult> rtnResultList = new ArrayList<GateAssessResult>();
	try {
	    if (pjtObj != null) {
		QueryResult qr = PersistenceHelper.manager.navigate(pjtObj, GateAssRsltPjtLink.RESULT_ROLE, GateAssRsltPjtLink.class);
		if (qr != null) {
		    while (qr.hasMoreElements()) {
			GateAssessResult rtnResult = (GateAssessResult) qr.nextElement();
			rtnResultList.add(rtnResult);
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return rtnResultList;
    }

    public GateAssessResult getAssesResultByTask(E3PSTask task) throws Exception {
	GateAssessResult assessResult = null;
	try {
	    if (task != null) {
		QueryResult qr = PersistenceHelper.manager.navigate(task, GateAssRsltTaskLink.ASSESS_ROLE, GateAssRsltTaskLink.class);
		while (qr.hasMoreElements()) {
		    assessResult = (GateAssessResult) qr.nextElement();
		    return assessResult;
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return assessResult;
    }

    public E3PSTask getTask(GateAssessResult assessResult) throws Exception {
	E3PSTask task = null;
	try {
	    // if (task != null) {
	    QueryResult qr = PersistenceHelper.manager.navigate(assessResult, GateAssRsltTaskLink.TASK_ROLE, GateAssRsltTaskLink.class,
		    false);
	    while (qr.hasMoreElements()) {
		GateAssRsltTaskLink taskLink = (GateAssRsltTaskLink) qr.nextElement();
		task = taskLink.getTask();
		ProductProject productProject = (ProductProject) task.getProject();
		if (!productProject.isCheckOut() && productProject.isLastest()) {
		    return task;
		}
	    }
	    // }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return task;
    }

    /**
     * 프로젝트 Gate 테스크 산출물,평가항목, 품질문제 결과 항목 모두 초기화
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : initiateGateResultLink
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void initiateGateResultLink(String outputLinkDiv, String gateLinkDiv, String dqmLinkDiv) throws Exception {

	E3PSTask task = null;

	String outputLinkOids[] = null;
	if (!StringUtil.isEmpty(outputLinkDiv)) {
	    outputLinkOids = outputLinkDiv.split(",");
	    for (int i = 0; i < outputLinkOids.length; i++) {
		String outputLinkOidStr = outputLinkOids[i];
		if (!StringUtil.isEmpty(outputLinkOidStr) && outputLinkOidStr.indexOf("AssessResultOutputLink") > 0) {
		    AssessResultOutputLink assOutLink = (AssessResultOutputLink) CommonUtil.getObject(outputLinkOidStr);
		    PersistenceHelper.manager.delete(assOutLink);
		}
	    }
	}
	String gateLinkOids[] = null;
	if (!StringUtil.isEmpty(gateLinkDiv)) {
	    gateLinkOids = gateLinkDiv.split(",");
	    for (int i = 0; i < gateLinkOids.length; i++) {
		String gateLinkOidStr = gateLinkOids[i];
		if (!StringUtil.isEmpty(gateLinkOidStr) && gateLinkOidStr.indexOf("AssessResultGateCheckLink") > 0) {
		    AssessResultGateCheckLink assOutLink = (AssessResultGateCheckLink) CommonUtil.getObject(gateLinkOidStr);
		    PersistenceHelper.manager.delete(assOutLink);
		}
	    }
	}
	String dqmLinkOids[] = null;
	if (!StringUtil.isEmpty(dqmLinkDiv)) {
	    dqmLinkOids = dqmLinkDiv.split(",");
	    for (int i = 0; i < dqmLinkOids.length; i++) {
		String dqmLinkOidStr = dqmLinkOids[i];
		if (!StringUtil.isEmpty(dqmLinkOidStr) && dqmLinkOidStr.indexOf("AssessResultDqmHeaderLink") > 0) {
		    AssessResultDqmHeaderLink assOutLink = (AssessResultDqmHeaderLink) CommonUtil.getObject(dqmLinkOidStr);
		    PersistenceHelper.manager.delete(assOutLink);
		}
	    }
	}

	// AssessResultGateCheckLink assOutLink = null;
	// r = PersistenceHelper.navigate(gAssRslt, "check", AssessResultGateCheckLink.class, false);
	// while (r.hasMoreElements()) {
	// assOutLink = (AssessResultGateCheckLink) r.nextElement();
	// PersistenceHelper.manager.delete(assOutLink);
	// }

    }

    @Override
    public List<GateTaskOutputDTO> getProjectOutputList(String taskOid, GateAssessResult ass) throws Exception {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int getMaxGateDegree(String oid) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(oid);
	GateAssessResult gateRslt_ = ProjectTaskCompHelper.service.getAssesResultByTask(task);

	int gate_rev = 1;

	if (gateRslt_ != null) {
	    QueryResult qr = null;

	    AssessResultOutputLink assOutLink = null;
	    AssessResultOutputLink newAssOutLink = null;

	    QuerySpec query = new QuerySpec();
	    int idxA = query.appendClassList(GateAssessResult.class, false);
	    int idxB = query.appendClassList(AssessResultOutputLink.class, false);

	    ClassAttribute rev = new ClassAttribute(AssessResultOutputLink.class, "rev");
	    SQLFunction max = SQLFunction.newSQLFunction(SQLFunction.MAXIMUM, rev);
	    query.appendSelect(max, new int[] { idxB }, false);

	    query.appendWhere(
		    new SearchCondition(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id", "=", CommonUtil
		            .getOIDLongValue(gateRslt_)), new int[] { idxA });
	    query.appendAnd();
	    ClassAttribute gateOid = new ClassAttribute(GateAssessResult.class, "thePersistInfo.theObjectIdentifier.id");
	    ClassAttribute roleA = new ClassAttribute(AssessResultOutputLink.class, "roleAObjectRef.key.id");
	    SearchCondition sc = new SearchCondition(gateOid, "=", roleA);
	    query.appendWhere(sc, new int[] { idxA, idxB });
	    query.setAdvancedQueryEnabled(true);
	    QueryResult rs = PersistenceServerHelper.manager.query((StatementSpec) query);

	    while (rs.hasMoreElements()) {
		Object[] object = (Object[]) rs.nextElement();
		if (object[0] == null) {
		    gate_rev = 0;
		} else {
		    gate_rev = Integer.parseInt(String.valueOf(object[0]));
		}

	    }
	}

	// TODO Auto-generated method stub
	return gate_rev;
    }

    /**
     * 신뢰성 Task 평가 결과 조회
     * 
     * @param pjtOid
     * @return
     * @throws Exception
     * @메소드명 : getTaskTrustResultList
     * @작성자 : jackey88
     * @작성일 : 2014. 7. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public List<TrustTaskResultDTO> getTaskTrustResultList(String taskOid) throws Exception {
	E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
	// E3PSProject project = (E3PSProject) task.getProject();
	ProductProject project = (ProductProject) task.getProject();

	List<TrustTaskResultDTO> trustDTOList = new ArrayList<TrustTaskResultDTO>();

	List<TrustTaskResultDTO> queryList = new ArrayList<TrustTaskResultDTO>();
	QueryResult qr = null;

	Connection conn = null;
	Statement stmt = null;
	// PreparedStatement pstmt = null; //preparedSt
	PreparedStatement pstmt = null; // preparedSt
	ResultSet rs = null;
	try {

	    conn = DBCPManager.getConnection("plm");
	    // conn.setAutoCommit(false);
	    stmt = conn.createStatement();

	    StringBuffer sb = new StringBuffer(0);
	    /*
	     * sb.append(
	     * "    SELECT MAIN.*, RSLT.IDA2A2 RESULT_OID, RSLT.REV, RSLT.OKCNT, RSLT.NGCNT, RSLT.ESTIMATEDATE, RSLT.DESCRIPTION				\n");
	     * sb.append("    FROM (				\n"); sb.append(
	     * "    SELECT A0.IDA2A2 TASK_OID, A5.IDA2A2 DOCLINK_OID, A5.DOCCLASSNAME, A4.IDA2A2 OUTPUT_OID, A4.OUTPUTNAME, A4.OBJTYPE, A4.ISPRIMARY, A4.CREATESTAMPA2				\n"
	     * ); sb.append("     FROM E3PSTask A0,				\n"); sb.append("     ProjectOutput A4,				\n");
	     * sb.append("     ProductProject A6, 				\n"); sb.append("     OutputDocumentLink A5				\n");
	     * sb.append("     WHERE 1=1				\n"); sb.append("     AND (A0.idA2A2 = " + task.getPersistInfo().getObjectIdentifier().getId() +
	     * " )				\n"); sb.append("     AND (A0.idA3B4 = A6.idA2A2) 				\n"); //
	     * sb.append("     AND (A6.checkOutState <> 'c/o') 				\n"); // sb.append("     AND (A6.lastest = 1) 				\n");
	     * sb.append("     AND (A0.IDA2A2 = A4.idA3B5)				\n"); sb.append("     AND (A4.idA2A2 = A5.idA3A5(+)) 				\n");
	     * sb.append("	AND EXISTS						\n"); sb.append("		(SELECT 'T'					\n");
	     * sb.append("		   FROM PRODUCTPROJECT PJT, E3PSTASK TASK	\n"); sb.append("		WHERE TASK.IDA3B4 = PJT.IDA2A2			\n");
	     * sb.append("		AND PJT.IDA2A2 = " + CommonUtil.getOIDLongValue(project) + "		\n");
	     * sb.append("		AND TASK.IDA2A2 = A0.IDA2A2)			\n"); sb.append("    ) MAIN				\n");
	     * sb.append("    , KETTaskTrustResult RSLT				\n"); sb.append("    , TrustProjectOutputLink TRUSTLINK				\n");
	     * sb.append("    WHERE  RSLT.IDA2A2 = TRUSTLINK.IDA3B5				\n"); sb.append("    AND TRUSTLINK.IDA3A5 = MAIN.OUTPUT_OID				\n");
	     * sb.append("    ORDER BY RSLT.REV, MAIN.CREATESTAMPA2				\n");
	     * 
	     * // sb.append("    , (				\n"); // sb.append("    SELECT A0.IDA2A2, MAX(A1.idA3A5) TRUST_OID				\n"); //
	     * sb.append("     FROM E3PSTask A0,				\n"); // sb.append("     TrustTemplateTaskLink A1				\n"); //
	     * sb.append("    WHERE A0.idA2A2 = " + task.getPersistInfo().getObjectIdentifier().getId() + "				\n"); //
	     * sb.append("    AND (A0.idA2A2 = A1.idA3B5)				\n"); // sb.append("    GROUP BY A0.IDA2A2				\n"); //
	     * sb.append("    ) SUB				\n"); // sb.append("    , KETTaskTrustResult RSLT				\n"); //
	     * sb.append("    WHERE MAIN.TASK_OID = SUB.IDA2A2				\n"); // sb.append("    AND SUB.TRUST_OID = RSLT.idA2A2				\n");
	     */

	    sb.append("	SELECT										\n");
	    sb.append("	MAIN.IDA2A2 TASK_OID, SUB.DOCLINK_OID, SUB.DOCCLASSNAME, SUB.OUTPUT_OID, SUB.OUTPUTNAME, SUB.OBJTYPE, SUB.ISPRIMARY, SUB.CREATESTAMPA2  									\n");
	    sb.append("	, MAIN.RESULT_OID, MAIN.REV, MAIN.OKCNT, MAIN.NGCNT, MAIN.ESTIMATEDATE, MAIN.DESCRIPTION              									\n");
	    sb.append("	FROM									\n");
	    sb.append("	  (                									\n");
	    sb.append("	    SELECT A0.IDA2A2, A2.IDA2A2 TRUST_OID, A2.IDA2A2 RESULT_OID, A2.REV, A2.OKCNT, A2.NGCNT, A2.ESTIMATEDATE, A2.DESCRIPTION									\n");
	    sb.append("	     FROM E3PSTask A0									\n");
	    sb.append("	     , TrustTemplateTaskLink A1									\n");
	    sb.append("	     , KETTaskTrustResult A2                									\n");
	    sb.append("	    WHERE A0.idA2A2 = " + task.getPersistInfo().getObjectIdentifier().getId() + "									\n");
	    sb.append("	    AND (A0.idA2A2 = A1.idA3B5)									\n");
	    sb.append("	    AND (A1.idA3A5 = A2.idA2A2)									\n");
	    sb.append("	    ) MAIN									\n");
	    sb.append("	    , (SELECT A7.IDA3B5 TRUST_OID, A0.IDA2A2 TASK_OID, A5.IDA2A2 DOCLINK_OID, A5.DOCCLASSNAME, A4.IDA2A2 OUTPUT_OID, A4.OUTPUTNAME, A4.OBJTYPE, A4.ISPRIMARY, A4.CREATESTAMPA2									\n");
	    sb.append("	     FROM E3PSTask A0,      									\n");
	    sb.append("	     TrustTemplateTaskLink A1,									\n");
	    sb.append("	     KETTaskTrustResult A2,									\n");
	    sb.append("	     ProductProject A6,                 									\n");
	    sb.append("	     TrustProjectOutputLink A7,                 									\n");
	    sb.append("	     ProjectOutput A4,                									\n");
	    sb.append("	     OutputDocumentLink A5									\n");
	    sb.append("	     WHERE 1=1                									\n");
	    sb.append("	     AND (A0.idA2A2 = " + task.getPersistInfo().getObjectIdentifier().getId() + " )                									\n");
	    sb.append("	     AND (A0.idA2A2 = A1.idA3B5)									\n");
	    sb.append("	     AND (A1.idA3A5 = A2.idA2A2)									\n");
	    sb.append("	     AND (A0.idA3B4 = A6.idA2A2)									\n");
	    sb.append("	     AND (A2.IDA2A2 = A7.IDA3B5(+))									\n");
	    sb.append("	     AND (A7.IDA3A5 = A4.IDA2A2(+))									\n");
	    sb.append("	     AND (A4.idA2A2 = A5.idA3A5(+))                 									\n");
	    sb.append("	    AND EXISTS                        									\n");
	    sb.append("	        (SELECT 'T'                    									\n");
	    sb.append("	           FROM PRODUCTPROJECT PJT, E3PSTASK TASK    									\n");
	    sb.append("	        WHERE TASK.IDA3B4 = PJT.IDA2A2            									\n");
	    sb.append("	        AND PJT.IDA2A2 = " + CommonUtil.getOIDLongValue(project) + "    									\n");
	    sb.append("	        AND TASK.IDA2A2 = A0.IDA2A2)									\n");
	    sb.append("	     ) SUB									\n");
	    sb.append("	WHERE MAIN.TRUST_OID = SUB.TRUST_OID(+)									\n");
	    sb.append("	ORDER BY MAIN.REV, SUB.CREATESTAMPA2								\n");

	    rs = stmt.executeQuery(sb.toString());
	    // Kogger.debug(getClass(), "---------------------- sb 쿼리 \n" + sb.toString());

	    while (rs.next()) {
		TrustTaskResultDTO rsDto = new TrustTaskResultDTO();

		rsDto.setTaskOid(rs.getString("TASK_OID"));
		rsDto.setOutputOid(rs.getString("OUTPUT_OID"));
		rsDto.setOutputName(rs.getString("OUTPUTNAME"));
		rsDto.setObjType(rs.getString("OBJTYPE"));
		rsDto.setIsPrimary(rs.getString("ISPRIMARY"));
		rsDto.setTrustOid(rs.getString("RESULT_OID"));
		rsDto.setRev(rs.getString("REV"));
		rsDto.setOkCnt(rs.getString("OKCNT"));
		rsDto.setNgCnt(rs.getString("NGCNT"));
		rsDto.setEstimateDate(rs.getString("ESTIMATEDATE"));
		rsDto.setDesc(rs.getString("DESCRIPTION"));
		rsDto.setDocClassName(rs.getString("DOCCLASSNAME"));
		rsDto.setDocLinkOid(rs.getString("DOCLINK_OID"));

		queryList.add(rsDto);
	    }
	    rs.close();
	    stmt.close();
	    conn.close();
	} catch (Exception e) {
	    if (rs != null)
		rs.close();
	    if (stmt != null)
		stmt.close();
	    if (conn != null)
		conn.close();
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(rs);
	    PlmDBUtil.close(stmt);
	    PlmDBUtil.close(conn);
	}

	/*
	 * QuerySpec query = new QuerySpec(); SearchCondition sc = null; int idxTask = query.appendClassList(E3PSTask.class, false); int
	 * idxTaskLink = query.appendClassList(TrustTemplateTaskLink.class, false); int idxTrust =
	 * query.appendClassList(TaskTrustResult.class, true); int idxOutputLink = query.appendClassList(TrustProjectOutputLink.class,
	 * false); int idxOutput = query.appendClassList(ProjectOutput.class, true); int idxObjLink =
	 * query.appendClassList(OutputDocumentLink.class, true); int idxPjt = query.appendClassList(ProductProject.class, false);
	 * 
	 * ClassAttribute ca1 = new ClassAttribute(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id"); ClassAttribute ca2 = new
	 * ClassAttribute(TrustTemplateTaskLink.class, "roleBObjectRef.key.id"); // E3PSTask idA3B5 ClassAttribute ca3 = new
	 * ClassAttribute(TrustTemplateTaskLink.class, "roleAObjectRef.key.id"); // TaskTrustResult idA3A5 ClassAttribute ca4 = new
	 * ClassAttribute(TaskTrustResult.class, "thePersistInfo.theObjectIdentifier.id"); ClassAttribute ca5 = new
	 * ClassAttribute(TrustProjectOutputLink.class, "roleBObjectRef.key.id"); // TaskTrustResult idA3B5 ClassAttribute ca6 = new
	 * ClassAttribute(TrustProjectOutputLink.class, "roleAObjectRef.key.id"); // ProjectOutput idA3A5 ClassAttribute ca7 = new
	 * ClassAttribute(ProjectOutput.class, "thePersistInfo.theObjectIdentifier.id"); ClassAttribute ca8 = new
	 * ClassAttribute(OutputDocumentLink.class, "roleAObjectRef.key.id"); ClassAttribute ca9 = new ClassAttribute(E3PSTask.class,
	 * "projectReference.key.id"); ClassAttribute ca10 = new ClassAttribute(ProductProject.class,
	 * "thePersistInfo.theObjectIdentifier.id");
	 * 
	 * sc = new SearchCondition(ca1, SearchCondition.EQUAL, ca2); if (query.getConditionCount() > 0) { query.appendAnd(); }
	 * query.appendWhere(sc, new int[] { idxTask, idxTaskLink });
	 * 
	 * sc = new SearchCondition(ca3, SearchCondition.EQUAL, ca4); query.appendAnd(); query.appendWhere(sc, new int[] { idxTaskLink,
	 * idxTrust });
	 * 
	 * sc = new SearchCondition(ca4, SearchCondition.EQUAL, ca5); sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN); query.appendAnd();
	 * query.appendWhere(sc, new int[] { idxTrust, idxOutputLink });
	 * 
	 * sc = new SearchCondition(ca6, SearchCondition.EQUAL, ca7); sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN); query.appendAnd();
	 * query.appendWhere(sc, new int[] { idxOutputLink, idxOutput });
	 * 
	 * sc = new SearchCondition(ca7, SearchCondition.EQUAL, ca8); sc.setOuterJoin(SearchCondition.RIGHT_OUTER_JOIN); query.appendAnd();
	 * query.appendWhere(sc, new int[] { idxOutput, idxObjLink });
	 * 
	 * sc = new SearchCondition(ca9, SearchCondition.EQUAL, ca10); query.appendAnd(); query.appendWhere(sc, new int[] { idxTask, idxPjt
	 * });
	 * 
	 * query.appendAnd(); query.appendWhere(new SearchCondition(ProductProject.class, "checkOutState", "<>", "c/o"), new int[] { idxPjt
	 * }); query.appendAnd(); query.appendWhere(new SearchCondition(ProductProject.class, "lastest", SearchCondition.IS_TRUE, true), new
	 * int[] { idxPjt });
	 * 
	 * sc = new SearchCondition(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL,
	 * task.getPersistInfo().getObjectIdentifier().getId()); query.appendAnd(); query.appendWhere(sc, new int[] { idxTask });
	 * 
	 * SearchUtil.setOrderBy(query, TaskTrustResult.class, idxTrust, TaskTrustResult.REV, false);
	 * 
	 * SearchUtil.setOrderBy(query, ProjectOutput.class, idxOutput, "thePersistInfo.createStamp", "createStamp", false);
	 * 
	 * qr = PersistenceHelper.manager.find((StatementSpec) query); Kogger.debug(getClass(), "qr >> " + query);
	 */

	if (queryList != null) {

	    for (int idx = 0; idx < queryList.size(); idx++) {
		// Object[] objArr = (Object[]) qr.nextElement();

		TrustTaskResultDTO trustDTO = new TrustTaskResultDTO();

		TrustTaskResultDTO rsltDto = new TrustTaskResultDTO();
		rsltDto = (TrustTaskResultDTO) queryList.get(idx);
		String trustOid = rsltDto.getTrustOid();
		String outputOid = rsltDto.getOutputOid();
		String docLinkOid = rsltDto.getDocLinkOid();
		TaskTrustResult trustRstObj = new TaskTrustResult();
		ProjectOutput outputRstObj = null;
		OutputDocumentLink outputLinkObj = null;
		if (!StringUtil.isEmpty(trustOid)) {
		    trustRstObj = (TaskTrustResult) CommonUtil.getObject("e3ps.project.TaskTrustResult:" + trustOid);
		}
		if (!StringUtil.isEmpty(outputOid)) {
		    outputRstObj = (ProjectOutput) CommonUtil.getObject("e3ps.project.ProjectOutput:" + outputOid);
		}
		if (!StringUtil.isEmpty(docLinkOid)) {
		    String docClassName = rsltDto.getDocClassName();
		    // if (docClassName != null && (docClassName.indexOf("EPMDocument") >= 0 || docClassName.indexOf("KETProjectDocument")
		    // >= 0)) {
		    // }
		    outputLinkObj = (OutputDocumentLink) CommonUtil.getObject("e3ps.project.OutputDocumentLink:" + docLinkOid);
		}

		trustDTO.setTaskOid(task.getPersistInfo().getObjectIdentifier().getStringValue());
		trustDTO.setTrustOid(trustRstObj.getPersistInfo().getObjectIdentifier().getStringValue());
		trustDTO.setRev("" + trustRstObj.getRev());
		int okCntInt = trustRstObj.getOkCnt();
		int ngCntInt = trustRstObj.getNgCnt();
		if (ngCntInt > 0) {
		    trustDTO.setAssResult("NG");
		} else {
		    trustDTO.setAssResult("OK");
		}
		trustDTO.setEstimateDate(trustRstObj.getEstimateDate());
		trustDTO.setTotCnt("" + (okCntInt + ngCntInt));
		trustDTO.setOkCnt("" + okCntInt);
		trustDTO.setNgCnt("" + ngCntInt);
		trustDTO.setNgReason(trustRstObj.getDescription());
		if (outputRstObj != null) {
		    trustDTO.setOutputOid(outputRstObj.getPersistInfo().getObjectIdentifier().getStringValue());
		    // 문서제목
		    trustDTO.setObjName(outputRstObj.getOutputName());
		    if (outputRstObj.getObjType().equals("DOC")) {
			// 문서타입
			trustDTO.setObjType("문서");
		    } else if (outputRstObj.getObjType().equals("DWG")) {
			trustDTO.setObjType("도면");
		    } else if (outputRstObj.getObjType().equals("TRY")) {
			trustDTO.setObjType("Try조건표");
		    } else if (outputRstObj.getObjType().equals("GATE")) {
			trustDTO.setObjType("Gate");
		    } else if (outputRstObj.getObjType().equals("ECO")) {
			trustDTO.setObjType("ECO");
		    } else if (outputRstObj.getObjType().equals("QLP")) {
			trustDTO.setObjType("품질");
		    } else if (outputRstObj.getObjType().equals("ETC")) {
			trustDTO.setObjType("기타");
		    }

		    // 필수세팅
		    if (outputRstObj.isIsPrimary()) {
			trustDTO.setIsPrimary("필수");
		    } else {
			trustDTO.setIsPrimary("");
		    }
		} else {
		    trustDTO.setOutputOid("");
		    trustDTO.setObjName("");
		    trustDTO.setObjType("");
		    trustDTO.setIsPrimary("");
		}

		PeopleData peopleData = null;
		if (outputRstObj != null && outputLinkObj != null) {
		    ProjectOutputData outputData = new ProjectOutputData(outputRstObj);

		    // if (document instanceof WTDocument || document instanceof EPMDocument) {
		    if (outputRstObj.getObjType().equals("DOC") || outputRstObj.getObjType().equals("DWG")) {
			String objOID = "VR:" + outputLinkObj.getDocClassName() + ":" + outputLinkObj.getBranchIdentifier();
			RevisionControlled document = (RevisionControlled) CommonUtil.getObject(objOID);
			WTUser duser = (WTUser) document.getCreator().getPrincipal();
			// out.println(duser);
			if (duser != null) {
			    // peopleData = new PeopleData(outputUser);
			    peopleData = new PeopleData(duser);
			    // String peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
			}

			trustDTO.setLastModifier(peopleData.name);

			String lastVer = document.getVersionDisplayIdentifier().toString();
			trustDTO.setObjVer(lastVer);

			String state = document.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			trustDTO.setObjStatus(state);

			String modifyDate = DateUtil.getDateString(document.getPersistInfo().getModifyStamp(), "D");
			modifyDate = modifyDate.substring(2, 10);
			trustDTO.setObjCreateDate(modifyDate);

			ObjectData data = new ObjectData(document);
			String fileUrl = data.getFileUrl();

			String icon = data.getIcon();
			FormatContentHolder holder = (FormatContentHolder) ContentHelper.service.getContents((ContentHolder) document);
			ContentItem item = holder.getPrimary();
			ContentInfo info = null;
			if (holder != null) {
			    info = ContentUtil.getContentInfo(holder, item);
			    if (item != null) {
				icon = info.getIconURLStr();
				icon = icon.substring(icon.indexOf("<img"));
			    }
			}
			if (fileUrl == null && data.getSecondary() != null) {
			    for (int i = 0; i < data.getSecondary().length; i++) {
				Object oo[] = (Object[]) data.getSecondary()[i];
				if (oo == null) {
				    continue;
				} else {
				    fileUrl = (String) oo[0];
				    break;
				}
			    }
			}
			if (fileUrl == null) {
			    fileUrl = "";
			    icon = "&nbsp;";
			}
			trustDTO.setObjFileIcon(icon);

			String progressRatio = outputRstObj.getCompletion() + "%";
			trustDTO.setProgressRatio(progressRatio);

			trustDTO.setGateTarget(outputRstObj.getSubjectType());
			trustDTO.setGateTemplate(fileUrl);

		    } else if (outputRstObj.getObjType().equals("ECO")) {
			KETProdChangeOrder prodChangeOrderObj = null;
			KETMoldChangeOrder moldChangeOrderObj = null;
			qr = PersistenceHelper.manager.navigate(outputRstObj, "change", KETProdChangeOrderOutputLink.class);

			WTUser duser = new WTUser();
			String lastVer = "";
			String state = "";
			String modifyDate = "";

			while (qr.hasMoreElements()) {
			    prodChangeOrderObj = (KETProdChangeOrder) qr.nextElement();
			    duser = (WTUser) prodChangeOrderObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(prodChangeOrderObj);
			    state = prodChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(prodChangeOrderObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);
			}

			qr = PersistenceHelper.manager.navigate(outputRstObj, "change", KETMoldChangeOrderOutputLink.class);

			while (qr.hasMoreElements()) {
			    moldChangeOrderObj = (KETMoldChangeOrder) qr.nextElement();
			    duser = (WTUser) moldChangeOrderObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(moldChangeOrderObj);
			    state = moldChangeOrderObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(moldChangeOrderObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);
			}

			if (duser != null) {
			    peopleData = new PeopleData(duser);
			}
			trustDTO.setLastModifier(peopleData.name);

			trustDTO.setObjVer(lastVer);

			trustDTO.setObjStatus(state);

			trustDTO.setObjCreateDate(modifyDate);

			trustDTO.setObjFileIcon("");

			String progressRatio = outputRstObj.getCompletion() + "%";
			trustDTO.setProgressRatio(progressRatio);

			trustDTO.setGateTarget(outputRstObj.getSubjectType());
			trustDTO.setGateTemplate("");

			// out.println(isSecu);
		    } else if (outputRstObj.getObjType().equals("QLP")) {
			KETDqmRaise raise = null;
			qr = PersistenceHelper.manager.navigate(outputRstObj, "dqm", KETDqmRaiseOutputLink.class);

			WTUser duser = new WTUser();
			String lastVer = "";
			String state = "";
			String modifyDate = "";

			while (qr.hasMoreElements()) {
			    raise = (KETDqmRaise) qr.nextElement();
			    duser = (WTUser) raise.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(raise);
			    state = raise.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(raise.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);
			}

			if (duser != null) {
			    peopleData = new PeopleData(duser);
			}
			trustDTO.setLastModifier(peopleData.name);

			trustDTO.setObjVer(lastVer);

			trustDTO.setObjStatus(state);

			trustDTO.setObjCreateDate(modifyDate);

			trustDTO.setObjFileIcon("");

			String progressRatio = outputRstObj.getCompletion() + "%";
			trustDTO.setProgressRatio(progressRatio);

			trustDTO.setGateTarget(outputRstObj.getSubjectType());
			trustDTO.setGateTemplate("");

			// out.println(isSecu);

		    } else if (outputRstObj.getObjType().equals("TRY")) {

			KETTryMold moldTryConditionObj = null;
			KETTryPress pressTryConditionObj = null;
			qr = PersistenceHelper.manager.navigate(outputRstObj, "tryMold", KETTryMoldOutputLink.class);

			WTUser duser = new WTUser();
			String lastVer = "";
			String state = "";
			String modifyDate = "";
			String fileUrl = "";
			String icon = "";

			while (qr.hasMoreElements()) {
			    moldTryConditionObj = (KETTryMold) qr.nextElement();
			    duser = (WTUser) moldTryConditionObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(moldTryConditionObj);
			    state = moldTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(moldTryConditionObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);

			    ObjectData data = new ObjectData(moldTryConditionObj);
			    fileUrl = data.getFileUrl();

			    icon = data.getIcon();
			    FormatContentHolder holder = (FormatContentHolder) ContentHelper.service
				    .getContents((ContentHolder) moldTryConditionObj);
			    ContentItem item = holder.getPrimary();
			    ContentInfo info = null;
			    if (holder != null) {
				info = ContentUtil.getContentInfo(holder, item);
				if (item != null) {
				    icon = info.getIconURLStr();
				    icon = icon.substring(icon.indexOf("<img"));
				}
			    }
			    if (fileUrl == null && data.getSecondary() != null) {
				for (int i = 0; i < data.getSecondary().length; i++) {
				    Object oo[] = (Object[]) data.getSecondary()[i];
				    if (oo == null) {
					continue;
				    } else {
					fileUrl = (String) oo[0];
					break;
				    }
				}
			    }
			    if (fileUrl == null) {
				fileUrl = "";
				icon = "&nbsp;";
			    }
			}

			qr = PersistenceHelper.manager.navigate(outputRstObj, "tryPress", KETTryPressOutputLink.class);

			while (qr.hasMoreElements()) {
			    pressTryConditionObj = (KETTryPress) qr.nextElement();
			    duser = (WTUser) pressTryConditionObj.getCreator().getPrincipal();
			    lastVer = CommonUtil.getOIDString(pressTryConditionObj);
			    state = pressTryConditionObj.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
			    modifyDate = DateUtil.getDateString(pressTryConditionObj.getPersistInfo().getModifyStamp(), "D");
			    modifyDate = modifyDate.substring(2, 10);

			    ObjectData data = new ObjectData(moldTryConditionObj);
			    fileUrl = data.getFileUrl();

			    icon = data.getIcon();
			    FormatContentHolder holder = (FormatContentHolder) ContentHelper.service
				    .getContents((ContentHolder) moldTryConditionObj);
			    ContentItem item = holder.getPrimary();
			    ContentInfo info = null;
			    if (holder != null) {
				info = ContentUtil.getContentInfo(holder, item);
				if (item != null) {
				    icon = info.getIconURLStr();
				    icon = icon.substring(icon.indexOf("<img"));
				}
			    }
			    if (fileUrl == null && data.getSecondary() != null) {
				for (int i = 0; i < data.getSecondary().length; i++) {
				    Object oo[] = (Object[]) data.getSecondary()[i];
				    if (oo == null) {
					continue;
				    } else {
					fileUrl = (String) oo[0];
					break;
				    }
				}
			    }
			    if (fileUrl == null) {
				fileUrl = "";
				icon = "&nbsp;";
			    }
			}

			if (duser != null) {
			    peopleData = new PeopleData(duser);
			}
			trustDTO.setLastModifier(peopleData.name);

			trustDTO.setObjVer(lastVer);

			trustDTO.setObjStatus(state);

			trustDTO.setObjCreateDate(modifyDate);

			trustDTO.setObjFileIcon(icon);

			String progressRatio = outputRstObj.getCompletion() + "%";
			trustDTO.setProgressRatio(progressRatio);

			trustDTO.setGateTarget(outputRstObj.getSubjectType());
			trustDTO.setGateTemplate(fileUrl);

		    } else {
			trustDTO.setLastModifier("");
			trustDTO.setObjVer("");
			trustDTO.setObjStatus("");
			trustDTO.setObjCreateDate("");
			trustDTO.setObjFileIcon("");
			trustDTO.setProgressRatio("");
			trustDTO.setGateTarget("");
			trustDTO.setGateTemplate("");
		    }
		} else {
		    trustDTO.setLastModifier("");
		    trustDTO.setObjVer("");
		    trustDTO.setObjStatus("");
		    trustDTO.setObjCreateDate("");
		    trustDTO.setObjFileIcon("");
		    trustDTO.setProgressRatio("");
		    trustDTO.setGateTarget("");
		    trustDTO.setGateTemplate("");

		}

		// 객체를 가져와야함

		trustDTOList.add(trustDTO);
	    }
	}
	return trustDTOList;
    }

    @Override
    public void makeCostInfoAuto() throws Exception {

	resetCostInfo();

	List<Map<String, Object>> list = getMakeCostInfoTargetList();

	SapService sap = new SapService();

	for (Map<String, Object> map : list) {
	    String dieNo = (String) map.get("DIENO");
	    String partNo = (String) map.get("PARTNO");
	    String partName = (String) map.get("PARTNAME");
	    String PRODPJTOID = (String) map.get("PRODPJTOID");
	    String MOLDINFOOID = (String) map.get("MOLDINFOOID");
	    Map<String, String> invest = sap.getOrderByDieNo(dieNo);
	    String orderNo = invest.get("I_AUFNR");

	    if (StringUtils.isNotEmpty(orderNo)) {
		String decideCost = invest.get("E_WOGBTR"); // 집행가
		String executionCost = invest.get("E_WTGES_R"); // 초기예산(기초)
		String targetCost = invest.get("E_WTGES_B"); // 초기예산(계획)
		String editCost = invest.get("E_WTGES_S"); // 추가예산

		CostInfo costInfo = CostInfo.newCostInfo();
		costInfo.setCostType("금형");
		costInfo.setPartNo(partNo);
		costInfo.setDieNo(dieNo);
		costInfo.setCostName(partName);
		costInfo.setOrderInvest(orderNo);
		costInfo.setTargetCost(targetCost);
		costInfo.setDecideCost(decideCost);
		costInfo.setExecutionCost(executionCost);
		costInfo.setEditCost(editCost);

		ProductProject pjt = (ProductProject) CommonUtil.getObject(PRODPJTOID);

		if (pjt == null) {
		    continue;
		}

		costInfo.setProject(pjt);

		costInfo = (CostInfo) PersistenceHelper.manager.save(costInfo);

		MoldItemInfo moldItemInfo = (MoldItemInfo) CommonUtil.getObject(MOLDINFOOID);

		if (moldItemInfo == null) {
		    continue;
		}
		moldItemInfo.setCostInfo(costInfo);
		PersistenceHelper.manager.save(moldItemInfo);
	    }
	}

    }

    public void resetCostInfo() throws Exception {// sap에서 매핑 투자오더가 바뀌어서 금형번호에 매칭된 투자오더가 없는 경우, 그리고 기존 costinfo에 투자오더번호가 없는 경우 해당 데이터는 삭제한다

	SapService sap = new SapService();

	QuerySpec query = new QuerySpec();
	query.addClassList(CostInfo.class, true);
	query.appendWhere(new SearchCondition(CostInfo.class, CostInfo.COST_TYPE, SearchCondition.EQUAL, "금형"), new int[] { 0 });

	QueryResult qr = PersistenceHelper.manager.find(query);

	CostInfo costInfo = null;

	while (qr.hasMoreElements()) {
	    Object[] o = (Object[]) qr.nextElement();
	    costInfo = (CostInfo) o[0];

	    Map<String, String> invest = sap.getOrderByDieNo(costInfo.getDieNo());
	    String orderNo = invest.get("I_AUFNR");

	    if (StringUtil.isEmpty(orderNo) || StringUtil.isEmpty(costInfo.getOrderInvest())) {
		PersistenceHelper.manager.delete(costInfo);
	    }

	}
    }

    public List<Map<String, Object>> getMakeCostInfoTargetList() throws Exception {

	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();

	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT DIENO,PRODPJT.CLASSNAMEA2A2||':'||PRODPJT.IDA2A2 AS PRODPJTOID,PJT.CLASSNAMEA2A2||':'||PJT.IDA2A2 AS MOLDPJTOID,   \n");
	    sb.append("        MOLDINFO.CLASSNAMEA2A2||':'||MOLDINFO.IDA2A2 AS MOLDINFOOID, MOLDINFO.PARTNO, MOLDINFO.PARTNAME   \n");
	    sb.append("   FROM MOLDPROJECT         PJT                                                                                                                                                       \n");
	    sb.append("       ,MOLDITEMINFO        MOLDINFO                                                                                                                                                  \n");
	    sb.append("       ,EXTENDSCHEDULEDATA  SCHEDULE                                                                                                                                                  \n");
	    sb.append("       ,PRODUCTPROJECT      PRODPJT                                                                                                                                                   \n");
	    sb.append("       ,E3PSPROJECTMASTER   PRODPJTMST                                                                                                                                                \n");
	    sb.append("  WHERE 1=1                                                                                                                                                                           \n");
	    sb.append("    AND PJT.LASTEST       = 1                                                                                                                                                         \n");
	    sb.append("    AND PJT.CHECKOUTSTATE <> 'c/o'                                                                                                                                                    \n");
	    sb.append("    AND PJT.IDA3A10       = MOLDINFO.IDA2A2                                                                                                                                           \n");
	    sb.append("    AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                                                                                                                           \n");
	    sb.append("    AND MOLDINFO.IDA3A3   = PRODPJT.IDA2A2                                                                                                                                            \n");
	    sb.append("    AND PRODPJT.IDA3B8    = PRODPJTMST.IDA2A2                                                                                                                                         \n");
	    sb.append("    AND MOLDINFO.ITEMTYPE != '구매품'                                                                                                                                                 \n");
	    sb.append("    AND PJT.STATESTATE != 'PMOINWORK'                                                                                                                                                 \n");
	    sb.append("    AND DIENO IN (SELECT PJTNO FROM E3PSPROJECTMASTER WHERE PJTTYPENAME = '금형'                                                                                                      \n");
	    sb.append("                   MINUS                                                                                                                                                              \n");
	    sb.append("                  SELECT DIENO FROM COSTINFO)                                                                                                                                         \n");
	    sb.append("  ORDER BY PJTNO                                                                                                                                                                      \n");

	    rs = stat.executeQuery(sb.toString());

	    list = ext.ket.common.util.ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return list;
    }

    @Override
    public void saveKqisTrust(Map<String, Object> reqMap) throws Exception {
	Transaction transaction = new Transaction();

	try {

	    ObjectMapper mapper = new ObjectMapper();
	    String jsonDataStr = (String) reqMap.get("jsonData");
	    String taskOid = (String) reqMap.get("taskOid");

	    E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);

	    ProductProject project = (ProductProject) task.getProject();

	    transaction.start();

	    List<Map<String, Object>> jsonData = mapper.readValue(jsonDataStr, new TypeReference<List<Map<String, Object>>>() {
	    });

	    List<ProjectOutput> checkList = ProjectOutputHelper.manager.getTaskOutputByKqisTrust(task);

	    if (jsonData != null) {
		int updateCnt = 0;
		for (Map<String, Object> data : jsonData) {

		    String adminNo = (String) data.get("adminNo");

		    boolean isNew = true;

		    for (ProjectOutput oldOutput : checkList) {
			if (adminNo.equals(oldOutput.getOutputName())) {
			    isNew = false;
			    break;
			}
		    }

		    if (!isNew) {
			continue;
		    }

		    ProjectOutput output = ProjectOutput.newProjectOutput();
		    output = ProjectOutput.newProjectOutput();
		    output.setProject(project);
		    output.setTask(task);
		    output.setObjType("KQISTRUST");
		    output.setCompletion(100);
		    output.setIsPrimary(true);
		    output.setOutputName(adminNo);
		    output.setOwner(SessionHelper.manager.getPrincipalReference());
		    PersistenceHelper.manager.save(output);
		    updateCnt++;
		}
		if (updateCnt > 0) {
		    task.setTaskCode("KQISTRUST");
		    PersistenceHelper.manager.save(task);
		}

	    }

	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    transaction.rollback();
	    e.printStackTrace();
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
    }

    @Override
    public Map<String, Object> addKqisSearchList(Map<String, Object> reqMap) throws Exception {
	purchaseUtil pcsUtil = new purchaseUtil();
	List<Map<String, Object>> resultList = pcsUtil.getKQISResultList(pcsUtil.getKQISTrustQuery(reqMap));
	for (Map<String, Object> dataMap : resultList) {
	    dataMap.put("CanDelete", "0");
	    dataMap.put("CanSelect", "1");
	    dataMap.put("kqisCode", dataMap.get("CODE"));
	    dataMap.put("adminNo", dataMap.get("ADMIN_NO"));
	    dataMap.put("pjtNo", dataMap.get("PJT_NO"));
	    dataMap.put("testGubun", dataMap.get("TEST_GUBUN"));
	    dataMap.put("testPurpose", dataMap.get("TEST_PURPOSE"));
	    dataMap.put("reqId", dataMap.get("REQ_ID"));
	    dataMap.put("tester", dataMap.get("TEST_ID"));
	    dataMap.put("reqDay", dataMap.get("REQ_DAY"));
	}
	return EjsConverUtil.convertToDto(resultList);
    }

}
