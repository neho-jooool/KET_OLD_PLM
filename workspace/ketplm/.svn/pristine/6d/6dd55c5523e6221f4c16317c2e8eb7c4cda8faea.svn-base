package ext.ket.part.migration.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.session.SessionContext;
import wt.util.WTException;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TaskMemberLink;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.ProjectStateFlag;
import e3ps.project.beans.TaskDependencyHelper;
import e3ps.project.beans.TaskUserHelper;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.log.Kogger;

public class MigMailDelayWhy1 implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static MigMailDelayWhy1 manager = new MigMailDelayWhy1();

    public MigMailDelayWhy1() {

    }

    // windchill ext.ket.part.migration.base.MigEpmNumber TEST_1102-01_2D H793280-3
    public static void main(String[] args) throws Exception {

	Kogger.debug(MigMailDelayWhy1.class, "@start");
	MigMailDelayWhy1.manager.saveFromExcel();
	Kogger.debug(MigMailDelayWhy1.class, "@end");

    }

    public void saveFromExcel() throws Exception {
	Kogger.debug(getClass(), SERVER);
	if (!SERVER) {
	    try {
		Class aclass[] = {};
		Object aobj[] = {};

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration();
	}
    }

    public void executeMigration() throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	ReferenceFactory rf = new ReferenceFactory();
	try {

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    Kogger.debug(getClass(), "function Call => sendMailForTask ()");
	    Calendar ca = Calendar.getInstance();
	    try {
		// 계획종료일이 지난 경우
		List<E3PSTask> taskPlanEndDelayList = ProjectTaskCompHelper.service.getTaskPlanEndDelay();
		Kogger.debug(getClass(), "taskPlanEndDelayList Size ====>" + taskPlanEndDelayList.size());
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

			// To 책임자조회
			List<WTUser> listToUser = new ArrayList<WTUser>();
			QueryResult masterList = TaskUserHelper.manager.getMaster(taskImsi1);
			Kogger.debug(getClass(), "masterList =====>" + masterList.size());
			if (masterList != null) {
			    while (masterList.hasMoreElements()) {
				Object o[] = (Object[]) masterList.nextElement();
				TaskMemberLink link = (TaskMemberLink) o[0];
				PeopleData data = new PeopleData(link.getMember().getMember());
				WTUser wtUserTo = data.wtuser;
				listToUser.add(wtUserTo);
			    }
			}
			// 임시로 모든 배치 메일을 한사람에게 보내기
			// listToUser = new ArrayList<WTUser>();
			// wtUserFrom = KETObjectUtil.getUser("PLMTFT");
			// listToUser.add(wtUserFrom);

			E3PSTaskData taskData = new E3PSTaskData(taskImsi1);

			int result = DateUtil.getDuration(taskData.taskPlanEndDate, toDay) + 1; // 지연일
			if ("23839000".equals(taskImsi1.getProject().getPjtNo())) { // 지연 3일 간격으로 메일 발송
			    Kogger.debug(getClass(), "지연TASK 프로젝트 넘버 =======>" + taskImsi1.getTaskName());
			    // KETMailHelper.service.sendFormMail("08009", "ProjectTaskNoticeMail.html", taskImsi1, wtUserFrom, listToUser);
			}
		    }
		}

		// *********************2014.10.31 추가**************************//

	    } catch (Exception ex) {
		Kogger.error(getClass(), ex);
	    } finally {
		SessionContext.setContext(sessioncontext);
	    }

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }

}
