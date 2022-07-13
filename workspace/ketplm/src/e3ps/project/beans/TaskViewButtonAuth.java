package e3ps.project.beans;

import java.util.Hashtable;

import wt.enterprise.RevisionControlled;
import wt.fc.QueryResult;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProjectOutput;
import e3ps.project.TaskMemberLink;
import ext.ket.shared.log.Kogger;

public class TaskViewButtonAuth {
//	산출물 추가 -> 상태:작업중, 재작업, AT완료, 중지됨, 진행중 PM, Admin, 최신
//	직접작성 -> 상태:진행중, AT왼료, 중지됨 최신 담당자 PM Admin
//	링크등록 -> 상태:진행중, AT완료, 중지됨 최신 담당자 PM Admin
//	%입력 -> 상태:진행중, AT완료, 중지됨 최신 , 자식이 없을 경우 , Task가 진행중일때
//	선행태스크 -> 상태:작업중 재작업 PM, Admin 최신
//	Task책임자 -> 상태:작업중, 재작업, AT완료, 중지됨, 진행중 PM, Admin, 최신
//	Issue등록 -> 상태:진행중, AT완료, 중지됨 최신

//	Task정보 수정 -> 상태:작업중 재작업 ERPSENDERROR  PM , Task담당자, admin 최신
//	Task편집 -> 상태:작업중, 재작업, ERPSENDERROR PM, Task담당자, admin 최신

	public boolean isOuputDocAdd;
	public boolean isOutputDocCreate;
	public boolean isOutputDocLink;
	public boolean isOutputCompletion;
	public boolean isDependency;
	public boolean isIssue;
	public boolean isTaskManager;
	public boolean isTaskInfoModify;
	public boolean isTaskModify;
	public boolean isComplatedButton;
	public boolean isCompletionButton;
	public boolean isEditTask;
	public boolean isExStartInputButton;
	public boolean isRoleChangeSelected;
	public boolean isTaskMasterModify;
	public boolean isTaskMemberModify;


	private static String INWORK = "INWORK";  //작업중
	private static String REWORK = "REWORK"; //재작업
	private static String ERPSENDERROR = "ERPSENDERROR"; //ERP 전송 에러
	//private static String PLANWORK = "PLANWORK"; //AT완료
	private static String CANCELLED = "CANCELLED"; //취소됨
	private static String STOPED = "STOPED"; //중지됨
	private static String PROGRESS = "PROGRESS"; //진행중
	private static String COMPLETED = "COMPLETED"; //완료됨
	private static String PLANCHANGE = "PLANCHANGE"; //일정수립
	private static String PMOINWORK = "PMOINWORK";

	String state = "";

	boolean isAdmin;

	E3PSTask task;
	public boolean isStarted;
	boolean isERP;
	boolean isOutputDoc;
	boolean isPrimaryOutPut;

	public boolean isPM;
	public boolean isLatest;
	public boolean isChild;
	public boolean isComplated;
	public boolean isTaskMaster;
	public boolean isMember;
	public boolean isProgress;
	public boolean isCheckOutOrg;

	Hashtable members = new Hashtable();

	public TaskViewButtonAuth(E3PSTask task)throws Exception{
		this.task = task;
		E3PSProject project = (E3PSProject)task.getProject();
	    state = project.getLifeCycleState().toString();


	    //long syst = System.currentTimeMillis();
		isAdmin = CommonUtil.isAdmin();
		isPM = ProjectUserHelper.manager.isPM(project);
	    isLatest = project.isLastest();
	    isProgress = state.equals(PROGRESS);
	    String checkOutState = project.getCheckOutState();
		isCheckOutOrg = "c/o".equals(checkOutState);
		//Kogger.debug(getClass(), "syst initl = " + (System.currentTimeMillis() - syst));
		//syst = System.currentTimeMillis();

		isChild  =  ProjectTaskHelper.isChild(task);
		isEditTask = true;

		//isEditTask = ProjectTaskHelper.isEditTask(task);
		//Kogger.debug(getClass(), "syst isEditTask = " + (System.currentTimeMillis() - syst));

		if(!isChild){
			isPrimaryOutPut = ProjectOutputHelper.manager.isPrimaryOutPut(task);
		}

		//syst = System.currentTimeMillis();

		QueryResult rs = TaskUserHelper.manager.getTaskUser(task);

		while(rs.hasMoreElements()){
			Object o[] = (Object[])rs.nextElement();
			TaskMemberLink link = (TaskMemberLink)o[0];
			members.put(link.getMember().getMember().getName(), link);
		}

		isTaskMaster = TaskUserHelper.isMaster(task);

		String currentId = SessionHelper.getPrincipal().getName();

		if(members.containsKey(currentId)){
			isMember = true;
		}
		//Kogger.debug(getClass(), "syst members = " + (System.currentTimeMillis() - syst));

		//syst = System.currentTimeMillis();

		isComplated = (task.getTaskCompletion() == 100 && task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE);

		ExtendScheduleData extendScheduleData = (ExtendScheduleData)task.getTaskSchedule().getObject();

		QueryResult result = ProjectOutputHelper.manager.getTaskOutput(task);
		//Kogger.debug(getClass(), "syst getTaskOutput = " + (System.currentTimeMillis() - syst));
		while(result.hasMoreElements()){

			Object object[] = (Object[])result.nextElement();
			ProjectOutput output = (ProjectOutput)object[0];

			RevisionControlled doc = ProjectOutputHelper.manager.getDocMasterForOutput(output);
			if(doc != null){
				isOutputDoc = true;
				break;
			}
		}

		//if(extendScheduleData.getExecStartDate() != null){
			isStarted = true;
		//}

		if(task.getParent() == null){
			//QueryResult rss = null;
//			try {
//				rss = PersistenceHelper.manager.navigate(task, TaskWBSItemLink.WBS_ROLE, TaskWBSItemLink.class);
//			} catch (WTException e) {
				// TODO Auto-generated catch block
//				Kogger.error(getClass(), e);
//			}

		   // if(rss.hasMoreElements()){
//	    		WBSItem wbsItem = (WBSItem)rss.nextElement();

//	    		isERP = wbsItem.isIsERP();
	    	//}
		}

		//Kogger.debug(getClass(), "isChild = " + isChild);

		isOuputDocAdd = (isPM) && isLatest;

		isOutputDocCreate = (state.equals(PROGRESS) || state.equals(PLANCHANGE)
				|| state.equals(STOPED)) && isLatest && !isChild;   // taskView output 마다  사용자 권한 체크

		isOutputDocLink = (state.equals(PROGRESS) || state.equals(PLANCHANGE)
				|| state.equals(STOPED)) && isLatest && !isChild;  // taskView output 마다  사용자 권한 체크

		isOutputCompletion = (state.equals(PROGRESS) || state.equals(PLANCHANGE)
				|| state.equals(STOPED)) && isLatest && !isChild && isStarted; // taskView output 마다  사용자 권한 체크

		isDependency = (state.equals(INWORK) || state.equals(REWORK) || state.equals(PLANCHANGE) || state.equals(PMOINWORK))&& !isChild && (isPM) && isLatest && !isComplated;

		//&& task.getTaskCompletion() == 0 && !isOutputDoc;





		isTaskManager = (state.equals(ERPSENDERROR) || state.equals(INWORK) || state.equals(REWORK)
				|| state.equals(PLANCHANGE) || state.equals(STOPED) || state.equals(PROGRESS))
				&& (isAdmin || isPM /*|| isEditTask*/) && isLatest;

		isComplatedButton = (state.equals(PROGRESS) || state.equals(STOPED)) && (isAdmin || isPM /*|| isEditTask*/)
		&& isLatest && (task.getTaskCompletion() == 100 && task.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE);

		isCompletionButton = (state.equals(PROGRESS)) && (isAdmin || isPM || isMember)
		&& isLatest && !isChild && !isPrimaryOutPut && (task.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE) && isStarted;

		isExStartInputButton = (state.equals(PROGRESS) || state.equals(STOPED)) && (isAdmin || isPM /*|| isEditTask*/)
		&& isLatest && !isChild;

		isIssue = (state.equals(PROGRESS) || state.equals(PLANCHANGE)
				|| state.equals(STOPED)) && isLatest;


		//isTaskInfoModify = (state.equals(INWORK) || state.equals(REWORK) || state.equals(PLANWORK)
			//	|| state.equals(ERPSENDERROR))  && (isAdmin || isPM || isEditTask) && isLatest;

		isTaskInfoModify = isTaskMaster && isLatest;
		//boolean isEditTask = ProjectTaskHelper.isEditTask(task);

		isTaskMasterModify = isPM && isLatest;

		isTaskMemberModify = (isPM || isTaskMaster) && isLatest;

		isTaskModify = (state.equals(INWORK) || state.equals(REWORK) || state.equals(PLANCHANGE)
				|| state.equals(ERPSENDERROR))  && (isAdmin || isPM /*|| isEditTask*/ || isTaskManager) && isLatest;
		isRoleChangeSelected = isLatest && isPM;
	}

	public final static int NOT_TASK_MEMBER = 2; // 담당자 지정 안됨
	public final static int NOT_PROGRESS = 4;  // 미진행 상태
	public final static int NOT_CREATE = 3;    // 등록 안됨
	public final static int CREATEORLINK = 1;  // 등록 OR Link

	public int isOutputDocCreateOrLink(ProjectOutput output){

		boolean isOwner = false;

		String currentId = "";
		try {
			currentId = SessionHelper.getPrincipal().getName();
		} catch (WTException e) {

			Kogger.error(getClass(), e);
		}

		if(members.size() == 0){
			return NOT_TASK_MEMBER;
		}

		if(!state.equals(PROGRESS) ){
			return NOT_PROGRESS;
		}

		if(members.containsKey(currentId)){
			isOwner = true;
		}


			//(state.equals(PROGRESS) || state.equals(PLANWORK)
			//	|| state.equals(STOPED)) && isLatest && isStarted;

//		Kogger.debug(getClass(), "isOutputDocLink=> " + isOutputDocLink);
//		Kogger.debug(getClass(), "isOwner=> " + isOwner);
//		Kogger.debug(getClass(), "state = " + (state.equals(PROGRESS) || state.equals(PLANWORK) || state.equals(STOPED)));
		//Kogger.debug(getClass(), "isStarted = " + isStarted);

		isOutputDocLink = isLatest &&  (isAdmin || isPM || isOwner); //isEditTask

		if(!isOutputDocLink){
			return NOT_CREATE;
		}

//		if(!isOutputDocLink){
//			try{
//				E3PSTaskData jd = new E3PSTaskData((E3PSTask)output.getTask());
//				if(jd.taskExecStartDate == null){
//					return 4;
//				}
//
//			}catch(Exception e) {
//				Kogger.error(getClass(), e);
//			}
//			return 3;
//		}


//		if(!state.equals(PROGRESS) ){
////			try{
////				E3PSTaskData jd = new E3PSTaskData((E3PSTask)output.getTask());
////				if(jd.taskExecStartDate == null){
////					return 4;
////				}
////
////			}catch(Exception e) {
////				Kogger.error(getClass(), e);
////			}
////			return 3;
//		}

		return CREATEORLINK;
	}


	public static final int NOTEXISTCOMPLETION = 2;
	public static final int NOTACTIONCOMPLECTION = 3;
	public static final int ACTIONCOMPLECTION = 1;


	public int isOutputCompletion(ProjectOutput output){


		boolean isOwner = false;
		boolean isNotCompletion = ((output.getCompletion() < 0) || isChild || !output.isIsPrimary());
		//Kogger.debug(getClass(), "output.getCompletion() = " + output.getCompletion());

		if(isNotCompletion){
			return NOTEXISTCOMPLETION;
		}

		String currentId = null;
		try {
			currentId = SessionHelper.getPrincipal().getName();
		} catch (WTException e) {
			// TODO Auto-generated catch block
			Kogger.error(getClass(), e);
		}

//		if(reference != null){
//			outputCreator = (WTUser)reference.getObject();
//			if(outputCreator == null){
//				return 2;
//			}
//		}
//
//		if(outputCreator != null && outputCreator.getName().equals(currentId)){
//			isOwner = true;
//		}

		if(members.containsKey(currentId)){
			isOwner = true;
		}


		if(!state.equals(PROGRESS)){
			return NOT_PROGRESS;
		}

		isOutputCompletion =  isLatest && !isChild;

		isOutputCompletion = isOutputCompletion &&  (isAdmin || isPM || isOwner) && !isComplated && output.getCompletion() < 100;

		if(!isOutputCompletion){
			return NOTACTIONCOMPLECTION;
		}
		/*
		RevisionControlled doc = ProjectOutputHelper.manager.getDocMasterForOutput(output);
		if(doc != null){
			if(doc.getState().toString().equals("APPROVED")){
				return 0;
			}
		}*/
		/*Kogger.debug(getClass(), " Commpletion  ==> : " + state.equals(PROGRESS));
		if(!state.equals(PROGRESS) ){
			try{
//				E3PSTaskData jd = new E3PSTaskData((E3PSTask)output.getTask());
//				if(jd.taskExecStartDate == null){
					return 4;
//				}

			}catch(Exception e) {
				Kogger.error(getClass(), e);
			}
			return 3;
		}*/


		return 1;
	}

	public boolean isETCReson(ProjectOutput output)throws Exception{
		boolean isOwner = false;
		String currentId = null;
		try {
			currentId = SessionHelper.getPrincipal().getName();
		} catch (WTException e) {
			// TODO Auto-generated catch block
			Kogger.error(getClass(), e);
		}

		if(members.containsKey(currentId)){
			isOwner = true;
		}
		if(isLatest && (isChild ||!output.isIsPrimary()) && (isPM || isOwner) && isProgress){
			return true;
		}
		if(isLatest && !isChild && output.isIsPrimary() && (isPM || isOwner) && isProgress && output.getCompletion() < 100 && !isComplated){
			return true;
		}
		if(members.containsKey(currentId)){
			return true;
		}
		if(isAdmin){
			return true;
		}

		return false;

	}

	public boolean isDeleteOutput(ProjectOutput output)throws Exception{
		if(isLatest && (isAdmin || isPM) && isChild){
			return true;
		}

		if(isLatest && (isAdmin || isPM) && output.getCompletion() < 0){
			return true;
		}

		if((isAdmin || isPM) && !isComplated){
			return true;
		}
		return false;
	}


}
