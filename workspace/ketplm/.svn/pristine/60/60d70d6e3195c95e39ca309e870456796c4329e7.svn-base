package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.enterprise.Managed;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.session.SessionMgr;
import wt.session.SessionServerHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.mail.MailHtmlContentTemplate;
import e3ps.common.mail.MailUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProjectOutput;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TaskMemberLink;
import ext.ket.common.dashboard.service.KETMailReceiveHelper;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.wfm.service.KETWorkspaceHelper;

public class ProjectScheduler implements Job, RemoteAccess {
    static final boolean SERVER;

    static {
	SERVER = RemoteMethodServer.ServerFlag;
    }

    static SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");

    public ProjectScheduler() {
    }

    public void execute(JobExecutionContext cntxt) throws JobExecutionException {

	// 2015-01-07 쿼츠 스케쥴러 사용안함으로 수정
	// 윈도우 스케쥴러로 변경함. mail 메소드 사용
	/*
	 * Kogger.debug(getClass(), "execute date call" + Calendar.getInstance().toString());
	 * 
	 * try { RemoteMethodServer.getDefault().invoke("projectSetting", "e3ps.project.beans.ProjectScheduler", null, null, null); } catch
	 * (InvocationTargetException e) { Kogger.error(getClass(), e); } catch (RemoteException e) { Kogger.error(getClass(), e); }
	 * 
	 * try { RemoteMethodServer.getDefault().invoke("sendMailForTask", "e3ps.project.beans.ProjectScheduler", null, null, null); } catch
	 * (InvocationTargetException e) { Kogger.error(getClass(), e); } catch (RemoteException e) { Kogger.error(getClass(), e); }
	 */
	// 2015-01-07 쿼츠 스케쥴러 사용안함으로 수정

    }

    public static void projectSetting() {

	if (!SERVER) {

	    try {
		RemoteMethodServer.getDefault().invoke("projectSetting", "e3ps.project.beans.ProjectScheduler", null, null, null);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectScheduler.class, e);
	    } catch (RemoteException e) {
		Kogger.error(ProjectScheduler.class, e);
	    }
	    return;
	}
	Kogger.debug(ProjectScheduler.class, "function Call => projectSetting()");
	// SessionContext sessioncontext = SessionContext.newContext();
	try {

	    // SessionHelper.manager.setAdministrator();

	    // 2015-01-07 추가적인 로그인을 요청하는 것을 방지하기 위해 추가함.
	    // 어드민 세션으로 스왑하는 구문 일괄 주석처리.
	    SessionServerHelper.manager.setAccessEnforced(true);
	    // 2015-01-07 추가적인 로그인을 요청하는 것을 방지하기 위해 추가함.
	    QuerySpec spec = new QuerySpec(E3PSProject.class);

	    spec.appendWhere(new SearchCondition(E3PSProject.class, E3PSProject.LASTEST, SearchCondition.IS_TRUE, true), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(spec);

	    while (rs.hasMoreElements()) {
		E3PSProject project = (E3PSProject) rs.nextElement();
		ProjectScheduleHelper.settingProgress(project);
	    }

	    Kogger.debug(ProjectScheduler.class, "projectSetting change coutn = " + rs.size());

	} catch (Exception ex) {
	    Kogger.error(ProjectScheduler.class, ex);
	} finally {
	    // SessionContext.setContext(sessioncontext);
	}
    }

    public static void sendMailForTask() {
	if (!SERVER) {

	    try {
		RemoteMethodServer.getDefault().invoke("sendMailForTask", "e3ps.project.beans.ProjectScheduler", null, null, null);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectScheduler.class, e);
	    } catch (RemoteException e) {
		Kogger.error(ProjectScheduler.class, e);
	    }
	    return;
	}
	Kogger.debug(ProjectScheduler.class, "function Call => sendMailForTask ()");
	// 어드민 세션으로 스왑하는 구문 일괄 주석처리.
	// SessionContext sessioncontext = SessionContext.newContext();
	try {
	    // *********************2015.10.15 추가**************************//
	    // *********************개발품질문제 지연메일 발송추가**************************//
//	    DqmHelper.service.dqmDelayMailSend("1");

	    // *********************2016.05.31 추가**************************//
	    // *********************대쉬보드 상황판 메일 발송추가**************************//
	    List<WTUser> dashBoardUserlist = KETMailReceiveHelper.service.dashboardSendMail();

	    // *********************2014.11.28 추가**************************//
	    // *********************설계변경 메일추가**************************//
	    
	    //아래 메일 발송은 시간이 너무 많이 걸려 KETScheduleJob 에서 백그라운드로 처리 2018.07.12
	    
//	    HashMap<WTChangeOrder2, ArrayList<Managed>> ecnDelayMap = KETEcmHelper.service.getEcoRelatedEcnDelay();
//
//	    Iterator<WTChangeOrder2> ecnDelayKeys = ecnDelayMap.keySet().iterator();
//	    while (ecnDelayKeys.hasNext()) {
//		WTChangeOrder2 wtChangeOrder2 = ecnDelayKeys.next();
//
//		ArrayList<Managed> ecaManagedList = ecnDelayMap.get(wtChangeOrder2);
//
//		for (int i = 0; i < ecaManagedList.size(); i++) {
//		    List<WTUser> toUserList = new ArrayList<WTUser>();
//		    Managed ecaManaged = ecaManagedList.get(i);
//		    if (ecaManaged instanceof KETProdChangeActivity) {
//			KETProdChangeActivity prodCA = (KETProdChangeActivity) ecaManaged;
//			String workerId = prodCA.getWorkerId();
//			if (!workerId.equals("")) {
//			    WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
//			    toUserList.add(ecaCharge);
//			    
//			    PeopleData peopleData = new PeopleData(ecaCharge);
//			    // 팀장 추가
//			    if (peopleData.department != null) {
//				WTUser chief = PeopleHelper.manager.getChiefUser(peopleData.department);
//				if (chief == null) {
//				    System.out.println("chief is null : " + peopleData.department.getName());
//				}
//				if (chief != null && !chief.equals(ecaCharge)) {
//				    toUserList.add(PeopleHelper.manager.getChiefUser(peopleData.department));
//				}
//
//			    } else {
//				System.out.println("peopleData.department is null : " + ecaCharge.getFullName());
//			    }
//			    
//			    KETMailHelper.service.sendFormMail("08030", "NoticeMailLine2.html", ecaManaged, (WTUser) wtChangeOrder2.getCreator().getPrincipal(), toUserList);
//			}
//
//		    } else if (ecaManaged instanceof KETMoldChangeActivity) {
//			KETMoldChangeActivity moldCA = (KETMoldChangeActivity) ecaManaged;
//			String workerId = moldCA.getWorkerId();
//			if (!workerId.equals("")) {
//			    WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
//			    toUserList.add(ecaCharge);
//			    
//			    PeopleData peopleData = new PeopleData(ecaCharge);
//			    // 팀장 추가
//			    if (peopleData.department != null) {
//				WTUser chief = PeopleHelper.manager.getChiefUser(peopleData.department);
//				if (chief == null) {
//				    System.out.println("chief is null : " + peopleData.department.getName());
//				}
//				if (chief != null && !chief.equals(ecaCharge)) {
//				    toUserList.add(PeopleHelper.manager.getChiefUser(peopleData.department));
//				}
//
//			    } else {
//				System.out.println("peopleData.department is null : " + ecaCharge.getFullName());
//			    }
//			    
//			    KETMailHelper.service.sendFormMail("08030", "NoticeMailLine2.html", ecaManaged, (WTUser) wtChangeOrder2.getCreator().getPrincipal(), toUserList);
//			}
//		    }
//		}
//	    }
//	    
//	    
//	    HashMap<WTChangeRequest2, ArrayList<Managed>> meetingDelayMap = KETEcmHelper.service.getEcrRelatedMeetingDelay();
//
//	    Iterator<WTChangeRequest2> meetingDelayKeys = meetingDelayMap.keySet().iterator();
//	    while (meetingDelayKeys.hasNext()) {
//		WTChangeRequest2 wtChangeRequest2 = meetingDelayKeys.next();
//
//		QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
//		spec.appendWhere(
//		        new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil
//		                .getOIDLongValue((WTChangeRequest2) wtChangeRequest2)), new int[] { 0 });
//		QueryResult result = PersistenceHelper.manager.find(spec);
//		if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
//		    // 제품, 금형 ECR 확장팩
//		    KETChangeRequestExpansion expansion = (KETChangeRequestExpansion) result.nextElement();
//		    WTUser charge = (expansion != null) ? expansion.getCharge() : null;
//		    // 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
//		    if (charge != null) {
//			List<WTUser> toUserList = new ArrayList<WTUser>();
//			WTUser member = (WTUser) expansion.getCharge();
//			toUserList.add(member);
//			PeopleData peopleData = new PeopleData(member);
//			// 팀장 추가
//			if (peopleData.department != null) {
//			    WTUser chief = PeopleHelper.manager.getChiefUser(peopleData.department);
//			    if(chief == null){
//				System.out.println("chief is null : "+peopleData.department.getName());
//			    }
//			    if(chief != null && !chief.equals(member)){
//				toUserList.add(PeopleHelper.manager.getChiefUser(peopleData.department));
//			    }
//			    
//			}else{
//			    System.out.println("peopleData.department is null : "+member.getFullName());
//			}
//			// toUserList set해야함
//			KETMailHelper.service.sendFormMail("08025", "NoticeMailLine.html", wtChangeRequest2,KETObjectUtil.getUser("wcadmin"), toUserList);
//
//		    }
//		}
//	    }

	    // *********************설계변경 메일추가 끝**************************//
	    
//	    KETWorkspaceHelper.service.sendMailDelayWorkItem(); //결재지연(3일이상) 알람

	    // SessionHelper.manager.setAdministrator();
	    Calendar ca = Calendar.getInstance();

	    String today = sd.format(ca.getTime());
	    QueryResult qr = searchDelayTask();

	    /*
	     * 
	     * 
	     * Kogger.debug(getClass(), "sendMailForTask() today  : " + today + " mail Task Size() = " + qr.size());
	     * 
	     * while (qr.hasMoreElements()) { Object[] o = (Object[]) qr.nextElement(); E3PSTask task = (E3PSTask) o[0];
	     * 
	     * Timestamp toDay = new Timestamp(ca.getTimeInMillis());
	     * 
	     * E3PSTaskData taskData = new E3PSTaskData(task);
	     * 
	     * int result = DateUtil.getDuration(taskData.taskPlanEndDate, toDay) + 1; // 지연일 if (result % 2 == 1) { // 지연 2일 간격으로 메일 발송 //
	     * sendMail(task); } }
	     */

	    // 계획종료일 3일전
	    List<E3PSTask> taskThreeDelayList = ProjectTaskCompHelper.service.getTaskThreeBefore();

	    if (taskThreeDelayList != null) {
		for (int i = 0; i < taskThreeDelayList.size(); i++) {

		    Timestamp toDay = new Timestamp(ca.getTimeInMillis());
		    E3PSTask taskImsi1 = (E3PSTask) taskThreeDelayList.get(i);
		    // E3PSTaskData taskData = new E3PSTaskData(taskImsi1);

		    // From 관리자
		    WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

		    // To 책임자조회
		    List<WTUser> listToUser = new ArrayList<WTUser>();
		    QueryResult masterList = TaskUserHelper.manager.getMaster(taskImsi1);
		    if (masterList != null) {
			while (masterList.hasMoreElements()) {
			    Object o[] = (Object[]) masterList.nextElement();
			    TaskMemberLink link = (TaskMemberLink) o[0];
			    PeopleData data = new PeopleData(link.getMember().getMember());
			    WTUser wtUserTo = data.wtuser;
			    listToUser.add(wtUserTo);
			    if (PeopleHelper.manager.getChiefUser(data.department) != null) {
				WTUser ChiefUser = PeopleHelper.manager.getChiefUser(data.department);
				boolean isSend = true;
				for (WTUser dashboardUser : dashBoardUserlist) {
				    if (CommonUtil.getOIDString(dashboardUser).equals(CommonUtil.getOIDString(ChiefUser))) {
					isSend = false;
					break;
				    }
				}
				if (isSend) {//팀장에게 보내는 메일은 주석처리한다 2019.04.25 by 황정태 경영혁신팀 박상수 차장 요청
				    //listToUser.add(ChiefUser);// 팀장
				}
			    }
			}
		    }
		    // 임시로 모든 배치 메일을 한사람에게 보내기
		    // listToUser = new ArrayList<WTUser>();
		    // wtUserFrom = KETObjectUtil.getUser("PLMTFT");
		    // listToUser.add(wtUserFrom);

		    Kogger.debug(ProjectScheduler.class, ">>계획 종료일 3일전 태스크 :" + taskImsi1.getTaskName() + ", " + toDay.toString());
		    KETMailHelper.service.sendFormMail("08008", "ProjectTaskNoticeMail.html", taskImsi1, wtUserFrom, listToUser);
		}
	    }

	    // *********************2014.10.31 추가**************************//
	    // 계획종료일 7일전
	    String mailTypeCode = "";
	    List<E3PSTask> taskSevenDelayList = ProjectTaskCompHelper.service.getTaskSevenBefore();

	    if (taskSevenDelayList != null) {
		for (int i = 0; i < taskSevenDelayList.size(); i++) {

		    Timestamp toDay = new Timestamp(ca.getTimeInMillis());
		    E3PSTask taskImsi1 = (E3PSTask) taskSevenDelayList.get(i);
		    // E3PSTaskData taskData = new E3PSTaskData(taskImsi1);

		    // From 관리자
		    WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

		    // To 책임자조회
		    List<WTUser> listToUser = new ArrayList<WTUser>();
		    QueryResult masterList = TaskUserHelper.manager.getMaster(taskImsi1);
		    if (masterList != null) {
			while (masterList.hasMoreElements()) {
			    Object o[] = (Object[]) masterList.nextElement();
			    TaskMemberLink link = (TaskMemberLink) o[0];
			    PeopleData data = new PeopleData(link.getMember().getMember());
			    WTUser wtUserTo = data.wtuser;
			    listToUser.add(wtUserTo);
			    if (PeopleHelper.manager.getChiefUser(data.department) != null) {
				// listToUser.add(PeopleHelper.manager.getChiefUser(data.department));//팀장

				WTUser ChiefUser = PeopleHelper.manager.getChiefUser(data.department);
				boolean isSend = true;
				for (WTUser dashboardUser : dashBoardUserlist) {
				    if (CommonUtil.getOIDString(dashboardUser).equals(CommonUtil.getOIDString(ChiefUser))) {
					isSend = false;
					break;
				    }
				}
				if (isSend) {//팀장에게 보내는 메일은 주석처리한다 2019.04.25 by 황정태 경영혁신팀 박상수 차장 요청
				    //listToUser.add(ChiefUser);// 팀장
				}

			    }
			}
		    }
		    // 임시로 모든 배치 메일을 한사람에게 보내기
		    // listToUser = new ArrayList<WTUser>();
		    // wtUserFrom = KETObjectUtil.getUser("PLMTFT");
		    // listToUser.add(wtUserFrom);

		    Kogger.debug(ProjectScheduler.class, ">>계획 종료일 7일전 태스크 :" + taskImsi1.getTaskName() + ", " + toDay.toString());
		    KETMailHelper.service.sendFormMail("08008", "ProjectTaskNoticeMail.html", taskImsi1, wtUserFrom, listToUser);
		}
	    }

	    // 계획종료일 하루전
	    List<E3PSTask> taskPlanEndList = ProjectTaskCompHelper.service.getTaskPlanEndBefore();

	    if (taskPlanEndList != null) {
		for (int i = 0; i < taskPlanEndList.size(); i++) {

		    Timestamp toDay = new Timestamp(ca.getTimeInMillis());
		    E3PSTask taskImsi1 = (E3PSTask) taskPlanEndList.get(i);

		    // From 관리자
		    WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");

		    // To 책임자조회
		    List<WTUser> listToUser = new ArrayList<WTUser>();
		    QueryResult masterList = TaskUserHelper.manager.getMaster(taskImsi1);
		    if (masterList != null) {
			while (masterList.hasMoreElements()) {
			    Object o[] = (Object[]) masterList.nextElement();
			    TaskMemberLink link = (TaskMemberLink) o[0];
			    PeopleData data = new PeopleData(link.getMember().getMember());
			    WTUser wtUserTo = data.wtuser;
			    listToUser.add(wtUserTo);
			    if (PeopleHelper.manager.getChiefUser(data.department) != null) {
				// listToUser.add(PeopleHelper.manager.getChiefUser(data.department));//팀장
				WTUser ChiefUser = PeopleHelper.manager.getChiefUser(data.department);
				boolean isSend = true;
				for (WTUser dashboardUser : dashBoardUserlist) {
				    if (CommonUtil.getOIDString(dashboardUser).equals(CommonUtil.getOIDString(ChiefUser))) {
					isSend = false;
					break;
				    }
				}
				if (isSend) {//팀장에게 보내는 메일은 주석처리한다 2019.04.25 by 황정태 경영혁신팀 박상수 차장 요청
				    //listToUser.add(ChiefUser);// 팀장
				}
			    }
			}
		    }
		    // 임시로 모든 배치 메일을 한사람에게 보내기
		    // listToUser = new ArrayList<WTUser>();
		    // wtUserFrom = KETObjectUtil.getUser("PLMTFT");
		    // listToUser.add(wtUserFrom);

		    Kogger.debug(ProjectScheduler.class, ">>계획 종료일 하루전 태스크 :" + taskImsi1.getTaskName() + ", " + toDay.toString());
		    KETMailHelper.service.sendFormMail("08008", "ProjectTaskNoticeMail.html", taskImsi1, wtUserFrom, listToUser);
		}
	    }

	    // 계획종료일이 지난 경우
	    List<E3PSTask> taskPlanEndDelayList = ProjectTaskCompHelper.service.getTaskPlanEndDelay();

	    if (taskPlanEndDelayList != null) {
		for (int i = 0; i < taskPlanEndDelayList.size(); i++) {

		    Timestamp toDay = new Timestamp(ca.getTimeInMillis());
		    E3PSTask taskImsi1 = (E3PSTask) taskPlanEndDelayList.get(i);

		    // **********************체크로직 : 선행태스크 미완료로 인한 지연의 경우 메일 발송하지 않음 ****************//
		    QueryResult dependListResult = TaskDependencyHelper.manager.getDependTaskList(taskImsi1);
		    TaskDependencyLink dependLink = null;
		    E3PSTask ancestorTask = null;
		    E3PSTaskData dependTaskData = null;
		    ExtendScheduleData taskSchedule = null;
		    boolean dependCheck = false;

		    while (dependListResult.hasMoreElements()) {

			dependLink = (TaskDependencyLink) dependListResult.nextElement();
			ancestorTask = (E3PSTask) dependLink.getDependTarget(); // 선행태스크 조회

			dependTaskData = new E3PSTaskData(ancestorTask);

			// 선행태스크가 미완료인 경우 체크
			if (ancestorTask.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE) {
			    dependCheck = true;
			}
			// taskSchedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
		    }

		    // 선행태스크 미완료인 경우 제외
		    if (dependCheck)
			continue;
		    // **********************체크로직 : 선행태스크가 완료되지 않은 경우 지연건이라도 ****************//

		    // From 관리자
		    WTUser wtUserFrom = KETObjectUtil.getUser("wcadmin");
		    // PeopleHelper.manager.getChiefUser(peopleData.department)
		    // To 책임자조회
		    List<WTUser> listToUser = new ArrayList<WTUser>();
		    // To 책임자의 팀장조회
		    List<WTUser> listChiefUser = new ArrayList<WTUser>();
		    // To 책임자의 부서장 조회(센터장, 본부장)
		    List<WTUser> listOfficerUser = new ArrayList<WTUser>();
		    ArrayList OfficerList = new ArrayList();
		    Department depart = null;

		    QueryResult masterList = TaskUserHelper.manager.getMaster(taskImsi1);
		    if (masterList != null) {
			while (masterList.hasMoreElements()) {
			    Object o[] = (Object[]) masterList.nextElement();
			    TaskMemberLink link = (TaskMemberLink) o[0];
			    PeopleData data = new PeopleData(link.getMember().getMember());
			    WTUser wtUserTo = data.wtuser;
			    listToUser.add(wtUserTo);// 책임자
			    try {
				if (PeopleHelper.manager.getChiefUser(data.department) != null) {
				    //listChiefUser.add(PeopleHelper.manager.getChiefUser(data.department));// 팀장

				    WTUser ChiefUser = PeopleHelper.manager.getChiefUser(data.department);
				    /*boolean isSend = true;
				    for (WTUser dashboardUser : dashBoardUserlist) {
					if (CommonUtil.getOIDString(dashboardUser).equals(CommonUtil.getOIDString(ChiefUser))) {
					    isSend = false;
					    break;
					}
				    }
				    if (isSend) {
					listChiefUser.add(ChiefUser);// 팀장
				    }*/
				    
				    //팀장에게 보내는 메일은 주석처리한다 2019.04.25 by 황정태 경영혁신팀 박상수 차장 요청
				    //listChiefUser.add(ChiefUser);// 팀장

				}

				OfficerList = DepartmentHelper.manager.getParentsList(data.department, OfficerList);
				if (OfficerList != null && OfficerList.size() > 0) {
				    for (int j = 0; j < OfficerList.size(); j++) {
					depart = (Department) OfficerList.get(j);
					if (depart.getParent() == null) {
					    continue;
					}
					if (PeopleHelper.manager.getChiefUser(depart) != null) {
					    //listOfficerUser.add(PeopleHelper.manager.getChiefUser(depart));// 부서장(센터장, 본부장)
					    
					    WTUser ChiefUser = PeopleHelper.manager.getChiefUser(depart);
					   /* boolean isSend = true;
					    for (WTUser dashboardUser : dashBoardUserlist) {
						if (CommonUtil.getOIDString(dashboardUser).equals(CommonUtil.getOIDString(ChiefUser))) {
						    isSend = false;
						    break;
						}
					    }
					    if (isSend) {
						listOfficerUser.add(ChiefUser);// 부서장(센터장, 본부장)장
					    }*/
					    //팀장에게 보내는 메일은 주석처리한다 2019.04.25 by 황정태 경영혁신팀 박상수 차장 요청
					    //listOfficerUser.add(ChiefUser);// 부서장(센터장, 본부장)장
					}
				    }
				}
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			}
		    }
		    // 임시로 모든 배치 메일을 한사람에게 보내기
		    // listToUser = new ArrayList<WTUser>();
		    // wtUserFrom = KETObjectUtil.getUser("PLMTFT");
		    // listToUser.add(wtUserFrom);

		    E3PSTaskData taskData = new E3PSTaskData(taskImsi1);

		    int result = DateUtil.getDuration(taskData.taskPlanEndDate, toDay) + 1; // 지연일
		    if (result % 3 == 1) { // 책임자에게 발송 지연 3일 간격으로 메일 발송
			KETMailHelper.service.sendFormMail("08009", "ProjectTaskNoticeMail.html", taskImsi1, wtUserFrom, listToUser);
		    }
		    
		    //팀장에게 보내는 메일은 주석처리한다 2019.04.25 by 황정태 경영혁신팀 박상수 차장 요청
		    /*if (result > 5) {// 지연일로부터 5일 이후 팀장에게 발송
			if (result % 3 == 0) { // 지연 3일 간격으로 메일 발송
			    KETMailHelper.service.sendFormMail("08009", "ProjectTaskNoticeMail.html", taskImsi1, wtUserFrom, listChiefUser);
			}
		    }
		    if (result > 10) {// 지연일로부터 10일 이후 임원(센터장,본부장)에게 발송
			if (result % 3 == 2) { // 지연 3일 간격으로 메일 발송
			    KETMailHelper.service.sendFormMail("08009", "ProjectTaskNoticeMail.html", taskImsi1, wtUserFrom,
				    listOfficerUser);
			}
		    }*/
		}
	    }

	    Kogger.debug(ProjectScheduler.class, "sendMailForTask() today  : " + today + " mail Task Size() = " + qr.size());

	    // *********************2014.10.31 추가**************************//

	} catch (Exception ex) {
	    Kogger.error(ProjectScheduler.class, ex);
	} finally {
	    // SessionContext.setContext(sessioncontext);
	}
    }

    public static void sendMail(E3PSTask task) throws Exception {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSTask.class };
	    Object args[] = new Object[] { task };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("sendMail", "e3ps.project.beans.ProjectScheduler", null, argTypes,
		        args);
	    } catch (RemoteException e) {
		Kogger.error(ProjectScheduler.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectScheduler.class, e);
		throw new WTException(e);
	    }
	    return;
	}

	E3PSTaskData taskData = null;

	try {
	    taskData = new E3PSTaskData(task);

	    WTUser fromUser = (WTUser) SessionHelper.manager.getAdministrator();
	    HashMap fromMap = new HashMap();
	    fromMap.put("EMAIL", fromUser.getEMail());
	    fromMap.put("NAME", fromUser.getFullName());

	    QueryResult rs = TaskUserHelper.manager.getTaskUser(task);
	    Hashtable userH = new Hashtable();
	    while (rs.hasMoreElements()) {
		Object o[] = (Object[]) rs.nextElement();
		TaskMemberLink link = (TaskMemberLink) o[0];
		userH.put(link.getMember().getMember().getName(), link.getMember().getMember());
	    }
	    E3PSProject project = (E3PSProject) task.getProject();
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    if (pm != null) {
		userH.put(pm.getName(), pm);
	    }

	    String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
	    String host = "http://" + hostName;
	    Enumeration e = userH.keys();

	    while (e.hasMoreElements()) {
		String userId = (String) e.nextElement();
		WTUser emailUser = (WTUser) userH.get(userId);
		HashMap toHash = new HashMap();

		if (emailUser.getEMail() == null) {
		    continue;
		} else {
		    // toHash.put(emailUser.getEMail(), emailUser.getFullName());
		    toHash.put("yhchoi@ket.com", "이영주");
		    toHash.put("thhwang@e3ps.com", "황태혁");
		}

		Hashtable hash = new Hashtable();
		hash.put("FROM", fromMap);
		hash.put("SUBJECT", "PLM 알림메일입니다.");

		MailHtmlContentTemplate contentTemplate = MailHtmlContentTemplate.getInstance();
		Hashtable key = new Hashtable();
		key.put("subject", "Task가 지연되었습니다.");
		key.put("projectNo", project.getPjtNo());
		key.put("projectName", project.getPjtName());
		key.put("taskName", task.getTaskName());
		key.put("planStartDate", DateUtil.getDateString(taskData.taskPlanStartDate, "D"));
		key.put("planEndDate", DateUtil.getDateString(taskData.taskPlanEndDate, "D"));
		key.put("oid", CommonUtil.getOIDString(task));
		key.put("host", host);
		key.put("userId", userId);
		key.put("approvalHistory", MailUtil.getApprovalHistory(CommonUtil.getOIDString(task)));

		/*
	         * [PLM System 1차개선] 수정내용 : 메일 다국어 적용 수정일자 : 2013. 7. 21 수정자 : 오명재
	         */
		String templateName = CommonUtil.getMailTemplateName(userId, "TaskDelayMail");

		// String content = contentTemplate.htmlContent(key, "TaskDelayMail.html");
		String content = contentTemplate.htmlContent(key, templateName);
		// //////////////////////////////////////////////////////////////////////

		hash.put("TO", toHash);
		hash.put("CONTENT", content);

		int i = 0;
		try {

		    e3ps.common.mail.MailUtil.sendMail3(hash);
		} catch (Exception ex) {
		    // TODO Auto-generated catch block
		    Kogger.error(ProjectScheduler.class, ex);
		}
		Kogger.debug(ProjectScheduler.class, "메일발송");
		i++;
	    }

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    Kogger.error(ProjectScheduler.class, e);
	}

	// File f = new File("c://kkk", i + ".html");
	// int i = 0;
	// try {
	// FileWriter writer = new FileWriter(new File("c://kkk", i + ".html"));
	// writer.write(content);
	// writer.flush();
	// writer.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// Kogger.error(ProjectScheduler.class, e);
	// }

	// boolean result = e3ps.common.mail.MailUtil.manager.sendMail(hash);

	// contentTemplate.htmlContent(new Hashtable(), "tttt.html");
	// boolean result = e3ps.common.mail.MailUtil.manager.sendMail(hash);
	// Kogger.debug(getClass(), "Project Task Mail 발송 : " + result);
    }

    /*
     * public static void sendMailTest(){ E3PSTask task = (E3PSTask)CommonUtil.getObject(""); sendMail(task);
     * 
     * }
     */

    public static QueryResult searchDelayTask() throws Exception {

	String today = DateUtil.getToDay();
	Timestamp currentDate = DateUtil.getTimestampFormat(today, "yyyy/MM/dd");

	QuerySpec qs = new QuerySpec();
	// qs.setDistinct(true);
	int index1 = qs.addClassList(E3PSTask.class, true);
	int index2 = qs.addClassList(E3PSProject.class, false);
	int index3 = qs.addClassList(ExtendScheduleData.class, false);

	SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=", new ClassAttribute(
	        E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME));
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
	qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, true), new int[] { index2 });
	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_END_DATE, true), new int[] { index3 });
	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, "<", currentDate),
	        new int[] { index3 });
	Kogger.debug(ProjectScheduler.class, "qs = " + qs);
	return PersistenceHelper.manager.find(qs);
    }

    public static QueryResult searchTodayActionTask(String today) throws Exception {

	Calendar ca = Calendar.getInstance();

	java.util.Date date = sd.parse(today);
	ca.setTime(date);
	ca.set(Calendar.HOUR, 0);
	Timestamp start = new Timestamp(ca.getTime().getTime());
	ca.add(Calendar.DATE, 1);
	Timestamp end = new Timestamp(ca.getTime().getTime());

	QuerySpec qs = new QuerySpec();

	int index1 = qs.addClassList(E3PSTask.class, true);
	int index2 = qs.addClassList(E3PSProject.class, false);
	int index3 = qs.addClassList(ExtendScheduleData.class, false);

	qs.appendWhere(new SearchCondition(E3PSTask.class, "projectReference.key.id", E3PSProject.class,
	        "thePersistInfo.theObjectIdentifier.id"), new int[] { index1, index2 });
	qs.appendAnd();

	qs.appendOpenParen();

	// private static String INWORK = "INWORK"; //작업중
	// private static String REWORK = " REWORK"; //재작업
	// private static String ERPSENDERROR = "ERPSENDERROR"; //ERP 전송 에러
	// private static String ATCOMPLATED = "ATCOMPLATED"; //AT완료
	// private static String CANCELLED = "CANCELLED"; //취소됨
	// private static String STOPED = "STOPED"; //중지됨
	// private static String PROGRESS = "PROGRESS"; //진행중
	// private static String COMPLETED = "COMPLETED"; //완료됨
	//
	qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "PROGRESS"), new int[] { index2 });
	qs.appendOr();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "ATCOMPLATED"), new int[] { index2 });
	qs.appendOr();
	qs.appendWhere(new SearchCondition(E3PSProject.class, "state.state", "=", "STOPED"), new int[] { index2 });

	qs.appendCloseParen();

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
	        new int[] { index2 });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(E3PSTask.class, "taskSchedule.key.id", ExtendScheduleData.class,
	        "thePersistInfo.theObjectIdentifier.id"), new int[] { index1, index3 });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, false), new int[] { index3 });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, ">=", start), new int[] { index3 });

	qs.appendAnd();

	qs.appendWhere(new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.EXEC_START_DATE, "<", end), new int[] { index3 });

	return PersistenceHelper.manager.find(qs);
    }

    private static boolean connected = false;

    public static void logonToServer(String usr, String pass) throws Exception {
	int i = 1;
	boolean blnStarted = false;
	// Will try to connect 10 times, sleeping 1 minute between times
	while (i < 10) {
	    blnStarted = isSystemStarted();
	    if (blnStarted) {
		if (!connected) {
		    RemoteMethodServer remotemethodserver = RemoteMethodServer.getDefault();
		    remotemethodserver.setUserName(usr);
		    remotemethodserver.setPassword(pass);
		    SessionMgr.getPrincipal();
		    connected = true;
		    return;
		}
	    } else {
		i++;
		// Sleep for 1 minute
		Kogger.debug(ProjectScheduler.class, "Attempt " + i + " could not connect to Windchill, sleeping for 1 minute");
		try {
		    Thread.sleep(1000 * 60);
		} catch (InterruptedException ex) {
		    // Just ignore
		}
	    }
	}
	if (!blnStarted) {
	    Kogger.debug(ProjectScheduler.class, "Could not connect after 10 minutes, giving up");
	    System.exit(1);
	}
    }

    /**
     * Checks to see if system is started
     */
    public static final boolean isSystemStarted() {
	try {
	    RemoteMethodServer.ping();
	    return true;
	} catch (final RemoteException ex) {
	    // Just ignore
	    return false;
	}
    }

    public static void main(String args[]) throws Exception {

	// ProjectScheduler schedule = new ProjectScheduler();

	// schedule.execute(null);
	// wt.method.RemoteMethodServer.getDefault().setUserName("wcadmin");
	// wt.method.RemoteMethodServer.getDefault().setPassword("wcadmin");

	String login = args[0];
	String passwd = args[1];
	logonToServer(login, passwd);
	projectSetting();
	sendMailForTask();
	// E3PSTask task = (E3PSTask) CommonUtil.getObject("e3ps.project.E3PSTask:196954");
	// sendMail(task);

	// sendMailTest();
	// QueryResult rs = searchDelayTask();
	//
	// while(rs.hasMoreElements()){
	// Object[] o = (Object[])rs.nextElement();
	// E3PSTask task = (E3PSTask)o[0];
	// Kogger.debug(getClass(), "task = " + task.getTaskName());
	// ExtendScheduleData data = (ExtendScheduleData)task.getTaskSchedule().getObject();
	//
	// Kogger.debug(getClass(), "data.getExecEndDate() = " + data.getExecEndDate());
	// Kogger.debug(getClass(), "data.getPlanEndDate() = " + data.getPlanEndDate());
	//
	// }

	// SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	// Scheduler sched = schedFact.getScheduler();
	// sched.start();
	// JobDetail jobDetail = new JobDetail(
	// "PDFMS","SCHEDULER",
	// ProjectScheduler.class
	// );
	//
	// CronTrigger trigger = new CronTrigger(
	// "PDFMS",
	// "SCHEDULER"
	// );
	//
	//
	// trigger.setCronExpression("0 45 " + 5 + " * * ?");
	// sched.scheduleJob(jobDetail, trigger);
	// Kogger.debug(getClass(), "ProjectSchduler registry...");

    }
}

class TaskMailData {
    E3PSProject project;
    E3PSTask task;

    WTUser from;
    HashMap toHash = new HashMap();
    String subject;
    String content;

    static String host;

    static {
	try {
	    String hostName = WTProperties.getLocalProperties().getProperty("wt.httpgw.hostname");
	    host = "http://" + hostName;
	} catch (java.io.IOException ex) {
	    Kogger.error(ProjectScheduler.class, ex);
	}
    }

    public TaskMailData(E3PSTask task) {
	this.task = task;
	// this.project = (E3PSProject)task.getProject();

	init();
    }

    public void init() {
	try {
	    // if(task.getTaskState() != 0){
	    // return;
	    // }
	    from = (WTUser) SessionHelper.manager.getPrincipal();

	    toHash = new HashMap();
	    QueryResult qr = ProjectOutputHelper.manager.getTaskOutput(task);
	    while (qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		ProjectOutput output = (ProjectOutput) objArr[0];
		ProjectOutputData outputdata = new ProjectOutputData(output);

		WTPrincipalReference toUserRef = output.getOwner();
		if (toUserRef == null) {
		    continue;
		}
		wt.org.WTUser toUser = (wt.org.WTUser) toUserRef.getObject();
		if (toUser == null) {
		    continue;
		}
		if (toUser.getEMail() == null) {
		    continue;
		}
		if (outputdata.isRegistry && (!outputdata.isWorking)) {
		    continue;
		}

		toHash.put(toUser.getEMail(), toUser.getFullName());
	    }

	    subject = getSubject();
	    // content = getContent();
	} catch (WTException ex) {
	    Kogger.error(getClass(), ex);
	}
    }

    public String getSubject() {

	return "담당 프로젝트 테스크가 시작되었습니다.";
    }

    // public String getContent(){
    //
    // String projectNo = project.getPjtNo();
    // String taskName = task.getTaskName();
    //
    //
    //
    // String link = host;
    // String text = project.getPjtNo() + "["+ project.getPjtName() + "] 프로젝트의 <br> " + task.getTaskName() + " 이 시작되었습니다.<br>"
    // + "담당 산출물을 작성을 시작해 주시기 바랍니다.";
    //
    // MailHtmlContentTemplate contentTemplate = MailHtmlContentTemplate.getInstance();
    //
    // Hashtable hash = new Hashtable();
    // hash.put("subject", subject);
    // hash.put("text", text);
    // hash.put("link", link);
    // hash.put("projectNo", projectNo);
    // hash.put("taskName", taskName);
    // return contentTemplate.htmlContent(hash, "ProjectDependencyMail.html");
    // }

}
