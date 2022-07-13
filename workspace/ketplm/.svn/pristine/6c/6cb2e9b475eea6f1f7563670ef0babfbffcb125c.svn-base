package e3ps.project.beans;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ScheduleData;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.util.WTException;

public class E3PSTaskData {

    // TemplateTask Attribute Values
    public TemplateTask tempTask; // TemplateTask Object
    public String taskNo = ""; // 테스크 NO
    public String taskName = ""; // 테스크 이름
    public String taskNameEn = ""; // 테스크 이름(영문)
    public int taskSeq; // 테스크 순서
    public String taskDesc = ""; // 테스크 설명
    public int taskType; // 테스크 종류
    public int level;
    public String taskTypeName = ""; // 테스크 종류 이름
    public int taskHistory; // 테스크 이력

    // ExtendSchedule Attribute Values
    public ScheduleData scheduleData; // ScheduleData Object
    public ExtendScheduleData schedule; // ExtendScheduleData Object(프로젝트 일정)
    public int taskDuration; // TASK 기간
    public int taskStdWork; // TASK 표준공수
    public Timestamp taskPlanStartDate; // 계획 시작일자
    public Timestamp taskPlanEndDate; // 계획 종료일자

    public Timestamp taskExecStartDate; // 실제 시작일자
    public Timestamp taskExecEndDate; // 실제 종료일자
    public Timestamp taskExecEndDate_ex; // 예상 종료일자

    // JELTask Attribute Values
    public E3PSTask e3psTask; // e3psTask Object
    public String e3psTaskOID = "";
    public E3PSProject e3psProject;
    public String e3psProjectOID = "";
    public int taskCompletion; // 프로젝트 완료율
    public int taskState; // 프로젝트 상태
    public String taskStateName = ""; // 프로젝트 상태(String Values)
    public Timestamp taskCreateDate; // 최초 등록일자
    public Timestamp taskModifyDate; // 최종 수정일자
    public String taskCode = ""; // TaskCode

    // Member Attribute Values
    public WTUser taskPm; // PM
    public String taskPmName = ""; // PM 이름
    public WTUser taskPl; // TASK 책임자 (PL)
    public String taskPlName = ""; // TASK 책임자 이름(PL)
    public String taskRoleMember = ""; // ROLE Member
    public String taskEtcMember = "";
    public double differDateGap;
    private double preferComp = 0;
    private String preferCompStr = "0";

    public String optiontype = ""; // 필수여부
    public String milestone = ""; // milestone view
    public String tasktype = ""; // task 종류
    public String scheduleType = ""; // Schedule 구분
    public String priorityControl = ""; //중점관리 task 여부
    public String drvalue = ""; // DR 목표 값
    public String drvalueCondition = ""; // DR 목표 조건부 값 (최소조건)

    String cashPreferCompStr = null;
    String cashStateStr = null;
    String cashStateStrImg = null;

    Calendar currentDate = null;
    NumberFormat nf;

    // ETC Member
                          
    public E3PSTaskData(E3PSTask e3psTask) throws Exception {
        if(e3psTask == null) return;
	// TemplateTask Attribute Values

	nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(1);
	nf.setMinimumFractionDigits(1);
	this.tempTask = e3psTask;
	if (StringUtil.checkString(e3psTask.getTaskNo()))
	    this.taskNo = e3psTask.getTaskNo().trim();
	if (StringUtil.checkString(e3psTask.getTaskName()))
	    this.taskName = e3psTask.getTaskName().trim();
	if (StringUtil.checkString(e3psTask.getTaskNameEn()))
	    this.taskNameEn = e3psTask.getTaskNameEn().trim();
	if (StringUtil.checkString(String.valueOf(e3psTask.getTaskSeq())))
	    this.taskSeq = e3psTask.getTaskSeq();
	if (StringUtil.checkString(e3psTask.getTaskDesc()))
	    this.taskDesc = e3psTask.getTaskDesc().trim();

	if (tempTask.isOptionType())
	    this.optiontype = "Y";
	else
	    this.optiontype = "N";

	if (tempTask.isMileStone())
	    this.milestone = "Y";
	else
	    this.milestone = "N";

	this.tasktype = tempTask.getTaskType();
	this.scheduleType = StringUtil.checkNull(tempTask.getScheduleType());
	this.priorityControl = StringUtil.checkNull(tempTask.getPriorityControl());
	
	KETMessageService messageService = KETMessageService.getMessageService();
	
	String lang = messageService.getLocale().toString();
	boolean isEnlang = "en".equals(lang) && !StringUtil.isEmpty(this.taskNameEn);
	
	if(isEnlang){
	    this.taskName = this.taskNameEn;
	}
	

    this.drvalue = StringUtil.checkNull(tempTask.getDrValue());

	if (StringUtil.checkString(String.valueOf(e3psTask.getTaskType()))) {
	    if (this.taskType == 1)
		this.taskTypeName = "개발검토";
	    else if (this.taskType == 2)
		this.taskTypeName = "제품";
	    else if (this.taskType == 3)
		this.taskTypeName = "금형";
	    else if (this.taskType == 4)
		this.taskTypeName = "전자";
	}
	if (StringUtil.checkString(String.valueOf(e3psTask.getTaskHistory())))
	    this.taskHistory = e3psTask.getTaskHistory();

	// ExtendSchedule Attribute Values
	if (e3psTask.getTaskSchedule() != null) {
	    this.schedule = (ExtendScheduleData) e3psTask.getTaskSchedule().getObject();
	    this.taskPlanStartDate = this.schedule.getPlanStartDate();
	    this.taskPlanEndDate = this.schedule.getPlanEndDate();
	    this.taskDuration = DateUtil.getDuration(taskPlanStartDate, taskPlanEndDate) + 1;
	    this.taskStdWork = this.schedule.getStdWork();
	    this.taskExecStartDate = this.schedule.getExecStartDate();
	    this.taskExecEndDate = this.schedule.getExecEndDate();
	}

	// Kogger.debug(getClass(), "kkkk = " + taskPlanStartDate.toString());

	// Attribute Values
	this.e3psTask = e3psTask;
	this.e3psTaskOID = StringUtil.checkNull(CommonUtil.getOIDString(this.e3psTask));
	if (this.e3psTask.getProject() != null) {
	    this.e3psProject = (E3PSProject) this.e3psTask.getProject();
	    this.e3psProjectOID = StringUtil.checkNull(CommonUtil.getOIDString(this.e3psProject));
	}

	if (tempTask.getDrValueCondition() != null) {
	    this.drvalueCondition = tempTask.getDrValueCondition();
	} else if ("DR".equalsIgnoreCase(tempTask.getTaskType())) {
	    if (this.e3psProject != null) {
		QueryResult rs = null;
		if (StringUtils.isNotEmpty(this.e3psProject.getTemplateCode())) {
		    TemplateProject tempProject = ProjectHelper.getTemplate(this.e3psProject.getTemplateCode());
		    if (tempProject != null) {
			try{
			    rs = ProjectTaskHelper.manager.getChildList(tempProject);
			}catch(Exception e){
			    e.printStackTrace();
			}
			
			if (rs != null) {
			    while (rs.hasMoreElements()) {
				TemplateTask templateTask = (TemplateTask) rs.nextElement();
				if (templateTask != null && "DR".equalsIgnoreCase(templateTask.getTaskType()) && this.taskName.equals(templateTask.getTaskName())) {
				    if (templateTask.getDrValueCondition() != null) {
					this.drvalueCondition = templateTask.getDrValueCondition();
					break;
				    }
				}
			    }
			}
		    }

		}

	    }
	}
	this.taskCompletion = this.e3psTask.getTaskCompletion();
	if (StringUtil.checkString(String.valueOf(this.e3psTask.getTaskState()))) {
	    this.taskState = this.e3psTask.getTaskState();
	    this.taskStateName = ProjectStateHelper.manager.getStateName(this.taskState);
	}
	this.taskCreateDate = this.e3psTask.getPersistInfo().getCreateStamp();
	this.taskModifyDate = this.e3psTask.getPersistInfo().getModifyStamp();
	this.taskCode = StringUtil.checkNull(this.e3psTask.getTaskCode());

	// Member Attribute Values
	if (this.e3psTask.getProject() != null) {
	    this.taskPm = ProjectUserHelper.manager.getPM(this.e3psProject);

	    PeopleData peopleData = null;
	    if (this.taskPm != null) {
		peopleData = new PeopleData(this.taskPm);
	    }
	    if (peopleData != null) {
		this.taskPmName = StringUtil.checkNull(peopleData.people.getName());
	    }
	}
	currentDate = Calendar.getInstance();
	Calendar startDate = Calendar.getInstance();
	startDate.setTime(this.taskPlanStartDate);
	int compPer = this.taskCompletion;
	double d = ((double) compPer) / 100D;
	d *= taskDuration * 0x5265c00L;

	// startDate.add(5, (int)d);
	double realLength = startDate.getTimeInMillis() + d;

	double continueTimeGap = realLength - (double) currentDate.getTime().getTime();

	differDateGap = ((double) continueTimeGap) / ((double) 0x5265c00L);

	preferComp = 0;
	long time = currentDate.getTime().getTime() - (taskPlanEndDate.getTime() + 0x5265c00L);

	if (time >= 0) {
	    preferComp = 100;
	} else {
	    long reallength = taskDuration * 0x5265c00L;
	    long currentGap = currentDate.getTime().getTime() - taskPlanStartDate.getTime();
	    preferComp = ((double) currentGap / (double) reallength) * 100d;
	}

	if (currentDate.getTime().before(taskPlanStartDate)) {
	    preferComp = 0;
	}
	preferCompStr = nf.format(preferComp);
    }

    public String getPreferCompStr() throws Exception {

	if (cashPreferCompStr != null) {
	    return cashPreferCompStr;
	} else {
	    settingProgress();
	    return cashPreferCompStr;
	}
    }

    public String getStateStr() throws Exception {
	if (cashStateStr != null) {
	    return cashStateStr;
	} else {
	    settingProgress();
	    return cashStateStr;
	}
    }

    public String getStateStr2() throws Exception {
	if (cashStateStrImg != null) {
	    return cashStateStrImg;
	} else {
	    settingProgress();
	    return cashStateStrImg;
	}
    }

    private void settingProgress() throws Exception {

	long startl = System.currentTimeMillis();

	boolean isChild = ProjectTaskHelper.manager.isChild(e3psTask);

	boolean isNotStart = currentDate.getTime().before(taskPlanStartDate);

	cashStateStrImg = getEndNodeStateStr2();

	if (!isChild || ProjectStateFlag.TASK_STATE_COMPLETE == taskState || isNotStart) {
	    cashPreferCompStr = preferCompStr;
	    cashStateStr = getEndNodeStateStr();
	    return;
	}

	ProjectTreeNode root = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot(e3psTask, false);

	Vector endTasks = new Vector();

	settingEnds(root, endTasks);

	double totalDuration = 0;
	double currentDuration = 0;

	Calendar currentDate = Calendar.getInstance();

	// boolean isDelay = false;

	int delayState = -1;

	// Kogger.debug(getClass(), "endTasks.size() = " + endTasks.size());
	for (int i = 0; i < endTasks.size(); i++) {

	    ProjectTreeNode endNode = (ProjectTreeNode) endTasks.get(i);

	    ProjectTreeNodeData td = (ProjectTreeNodeData) endNode.getUserObject();

	    E3PSTask childTask = (E3PSTask) td.getData();

	    long time = td.getPlanStartDate().getTime() - currentDate.getTime().getTime();

	    long duration = (td.getPlanEndDate().getTime() + 0x5265c00L) - td.getPlanStartDate().getTime();

	    // (taskPlanEndDate.getTime() + 0x5265c00L) - taskPlanStartDate.getTime();

	    // Kogger.debug(getClass(), duration / 0x5265c00L);

	    long currentGap = 0;

	    if (time < 0) {

		long etime = currentDate.getTime().getTime() - (td.getPlanEndDate().getTime() + 0x5265c00L);

		if (etime >= 0) {
		    currentGap = duration;
		} else {
		    currentGap = currentDate.getTime().getTime() - td.getPlanStartDate().getTime();
		}
	    }

	    currentDuration += currentGap;
	    totalDuration += duration;

	    int childTaskState = E3PSTaskData.getStateBarType(childTask);
	    if (childTaskState > delayState) {
		delayState = childTaskState;
	    }

	}// end for

	// Kogger.debug(getClass(), " totalDuration = " + totalDuration + " currentDuration" + currentDuration);

	double performComplection = (currentDuration / totalDuration) * 100d;

	cashPreferCompStr = nf.format((currentDuration / totalDuration) * 100d);

	if (ProjectScheduleHelper.ISCOMPLECTION) {
	    double differDateGap = (double) taskCompletion - performComplection;
	    delayState = getStateBarType(e3psTask, differDateGap);
	}

	cashStateStr = getStateBarTypeString(delayState);
	// Kogger.debug(getClass(), "time ==========  " + (System.currentTimeMillis() - startl));
    }

    public static int getDelayType(ProjectTreeNode root, E3PSTask task) {
	Vector endTasks = new Vector();

	settingEnds(root, endTasks);

	double totalDuration = 0;
	double currentDuration = 0;

	Calendar currentDate = Calendar.getInstance();

	// boolean isDelay = false;

	int delayState = -1;

	// Kogger.debug(getClass(), "endTasks.size() = " + endTasks.size());
	for (int i = 0; i < endTasks.size(); i++) {

	    ProjectTreeNode endNode = (ProjectTreeNode) endTasks.get(i);

	    ProjectTreeNodeData td = (ProjectTreeNodeData) endNode.getUserObject();

	    E3PSTask childTask = (E3PSTask) td.getData();

	    long time = td.getPlanStartDate().getTime() - currentDate.getTime().getTime();

	    long duration = (td.getPlanEndDate().getTime() + 0x5265c00L) - td.getPlanStartDate().getTime();

	    // (taskPlanEndDate.getTime() + 0x5265c00L) - taskPlanStartDate.getTime();

	    // Kogger.debug(getClass(), duration / 0x5265c00L);

	    long currentGap = 0;

	    if (time < 0) {

		long etime = currentDate.getTime().getTime() - (td.getPlanEndDate().getTime() + 0x5265c00L);

		if (etime >= 0) {
		    currentGap = duration;
		} else {
		    currentGap = currentDate.getTime().getTime() - td.getPlanStartDate().getTime();
		}
	    }

	    currentDuration += currentGap;
	    totalDuration += duration;

	    int childTaskState = E3PSTaskData.getStateBarType(childTask);

	    // Kogger.debug(getClass(), childTask.getTaskName() + " state = " + childTaskState);
	    if (childTaskState > delayState) {
		delayState = childTaskState;
	    }

	}// end for

	// Kogger.debug(getClass(), " totalDuration = " + totalDuration + " currentDuration" + currentDuration);

	double performComplection = (currentDuration / totalDuration) * 100d;

	if (ProjectScheduleHelper.ISCOMPLECTION) {
	    double differDateGap = (double) task.getTaskCompletion() - performComplection;
	    delayState = getStateBarType(task, differDateGap);
	}

	return delayState;

    }

    public static int getStateBarType(E3PSTask task, double differDateGap) {

	int state = task.getTaskState();
	if (ProjectStateFlag.TASK_STATE_COMPLETE == state) {
	    return STATE_BAR_COMPLATE;
	}

	Calendar currentDate = Calendar.getInstance();

	Timestamp taskPlanStartDate = ((ExtendScheduleData) task.getTaskSchedule().getObject()).getPlanStartDate();
	Timestamp taskPlanEndDate = ((ExtendScheduleData) task.getTaskSchedule().getObject()).getPlanEndDate();

	if (currentDate.getTime().before(taskPlanStartDate)) {
	    return STATE_BAR_NORMAL;
	}

	if (differDateGap >= 0) {
	    return STATE_BAR_NORMAL;
	} else {
	    Calendar today = Calendar.getInstance();

	    String todayStr = DateUtil.getDateString(today.getTime(), "d");

	    String planEndDateStr = DateUtil.getTimeFormat(taskPlanEndDate, "yyyy-MM-dd");

	    boolean todayBefore = todayStr.compareTo(planEndDateStr) > 0;

	    if (todayBefore) {
		return STATE_BAR_DELAY;
	    } else {
		return STATE_BAR_EXDELAY;
	    }

	}

    }

    private static void settingEnds(ProjectTreeNode root, Vector endTasks) {

	for (int i = 0; i < root.getChildCount(); i++) {
	    settingEnds((ProjectTreeNode) root.getChildAt(i), endTasks);
	}

	if (root.getChildCount() == 0) {
	    endTasks.add(root);
	}

    }

    public static double getPreferComp(E3PSTask task) {

	ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
	// this.taskDuration = this.schedule.getDuration();
	Timestamp taskPlanStartDate = schedule.getPlanStartDate();
	Timestamp taskPlanEndDate = schedule.getPlanEndDate();

	int taskDuration = DateUtil.getDuration(taskPlanStartDate, taskPlanEndDate) + 1;

	Calendar currentDate = Calendar.getInstance();

	Calendar startDate = Calendar.getInstance();
	startDate.setTime(taskPlanStartDate);

	double preferComp = 0;

	long time = currentDate.getTime().getTime() - (taskPlanEndDate.getTime() + 0x5265c00L);

	if (time >= 0) {
	    preferComp = 100;
	} else {
	    long reallength = taskDuration * 0x5265c00L;
	    long currentGap = currentDate.getTime().getTime() - taskPlanStartDate.getTime();
	    preferComp = ((double) currentGap / (double) reallength) * 100d;
	}

	if (currentDate.getTime().before(taskPlanStartDate)) {
	    preferComp = 0;
	}

	return preferComp;
    }

    public static double getDifferDateGap(E3PSTask task) {

	ExtendScheduleData schedule = (ExtendScheduleData) task.getTaskSchedule().getObject();
	// this.taskDuration = this.schedule.getDuration();
	Timestamp taskPlanStartDate = schedule.getPlanStartDate();
	Timestamp taskPlanEndDate = schedule.getPlanEndDate();

	Calendar currentDate = Calendar.getInstance();

	if (currentDate.getTime().before(taskPlanStartDate)) {
	    return 0;
	}

	Calendar startDate = Calendar.getInstance();
	startDate.setTime(taskPlanStartDate);
	int compPer = task.getTaskCompletion();
	int taskDuration = DateUtil.getDuration(taskPlanStartDate, taskPlanEndDate) + 1;// this.schedule.getDuration();
	double d = ((double) compPer) / 100D;
	d *= taskDuration * 0x5265c00L;

	// startDate.add(5, (int)d);
	double realLength = startDate.getTimeInMillis() + d;

	double continueTimeGap = realLength - (double) currentDate.getTime().getTime();
	double differDateGap = ((double) continueTimeGap) / ((double) 0x5265c00L);
	return differDateGap;
    }

    public E3PSTaskAccessData getAccessState() {
	boolean isComplete = ProjectStateFlag.TASK_STATE_COMPLETE == this.taskState;
	boolean isContinue = ProjectStateFlag.TASK_STATE_PROGRESS == this.taskState;
	// boolean isCompleting = ProjectStateFlag.TASK_STATE_COMPLETE == this.taskState;
	boolean isStop = ProjectStateFlag.TASK_STATE_HOLD == this.taskState;
	// boolean isLast = ProjectTaskHelper.manager.isLast(this.tempTask);

	if (isComplete) {
	    isContinue = false;
	} else {
	    // 선행 태스크 체크
	    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(this.tempTask);
	    if (dependList != null) {
		while (dependList.hasMoreElements()) {
		    TaskDependencyLink link = (TaskDependencyLink) dependList.nextElement();
		    isContinue = (((E3PSTask) link.getDependTarget()).getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE);
		    if (!isContinue)
			break;
		}
	    }

	    isContinue = isContinue && (this.e3psProject.getPjtState() == ProjectStateFlag.PROJECT_STATE_PROGRESS);
	    if (this.e3psTask.getParent() != null) {
		isContinue = isContinue && (ProjectStateFlag.TASK_STATE_PROGRESS == ((E3PSTask) this.e3psTask.getParent()).getTaskState());
	    }
	}

	E3PSTaskAccessData accessData = new E3PSTaskAccessData();
	accessData.setComplete(isComplete);
	// accessData.setCompleting(isCompleting);
	accessData.setContinue(isContinue);
	// accessData.setLast(isLast);
	accessData.setStop(isStop);

	return accessData;
    }

    public String getTaskUserStr() {
	String returnStr = "";
	QueryResult qr = TaskUserHelper.manager.getMaster(this.tempTask);

	try {
	    if (qr != null && qr.hasMoreElements()) {
		Object objArr[] = (Object[]) qr.nextElement();
		TaskMemberLink link = (TaskMemberLink) objArr[0];
		// WTUser master = (WTUser)link.getActor().getPrincipal();
		WTUser master = link.getMember().getMember();

		returnStr = master.getFullName() + "(" + DepartmentHelper.manager.getDepartmentName(master) + ")";
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return returnStr;
    }

    public WTUser getTaskWTUser() {
	WTUser returnUser = null;
	QueryResult qr = TaskUserHelper.manager.getMaster(this.tempTask);

	try {
	    if (qr != null && qr.hasMoreElements()) {
		Object objArr[] = (Object[]) qr.nextElement();
		TaskMemberLink link = (TaskMemberLink) objArr[0];
		// WTUser master = (WTUser)link.getActor().getPrincipal();
		returnUser = link.getMember().getMember();

		// returnStr = master.getFullName() + "(" + DepartmentHelper.manager.getDepartmentName(master) + ")";
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return returnUser;
    }

    public WTUser getTaskMaster() {

	String returnStr = "";
	QueryResult qr = TaskUserHelper.manager.getMaster(this.tempTask);
	WTUser master = null;

	try {
	    if (qr != null && qr.hasMoreElements()) {
		Object objArr[] = (Object[]) qr.nextElement();
		TaskMemberLink link = (TaskMemberLink) objArr[0];
		master = (WTUser) link.getActor().getPrincipal();
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

	return master;
    }

    public String getStateMessage() {
	if (ProjectStateFlag.TASK_STATE_COMPLETE == taskState) {
	    double resultTime = taskPlanEndDate.getTime() - taskExecEndDate.getTime();
	    resultTime /= (double) 0x5265c00L;
	    String endMessg = "";
	    if (resultTime > 0.5) {
		endMessg = "<b><font color=\"blue\">" + nf.format(resultTime) + "일 초과 달성</font></b>";
	    } else if (0.5 >= resultTime && resultTime >= -0.5) {
		endMessg = "<b<font color=\"blue\">정상 종료</font></b>";
	    } else {
		endMessg = "<b><font color=\"red\">" + nf.format(resultTime) + "일 지연 종료됨</font></b>";
	    }

	    return "<font color=\"green\">" + DateUtil.getDateString(taskExecEndDate, "D") + "일 종료됨.</font>&nbsp;" + endMessg;

	}

	if (currentDate.getTime().before(taskPlanStartDate)) {
	    return "<b><font color=\"blue\">시작전 </font></b>";
	}

	return "";

	// if ( differDateGap > 0.2 ) {
	// return "<B><font color=\"blue\"> " +
	// nf.format(differDateGap) + " 일 초과. 적정 %은 " + nf.format(preferComp)+ " %입니다. </font></B>";
	// } else if(0.2 >= differDateGap && differDateGap >= -0.2){
	//
	// return "<B><font color=\"blue\"> " + " 정상 진행중. 적정 %은 " + nf.format(preferComp)+ " %입니다. </font></B>";
	//
	// }else{
	// return "<B><font color=\"red\"> " +
	// nf.format(differDateGap) + " 일 지연. 적정 %은 " + nf.format(preferComp)+ " %입니다. </font></B>";
	//
	// }
    }

    public static final int STATE_BAR_COMPLATE = 1;
    // public static final int STATE_BAR_NONSTART = 2;
    public static final int STATE_BAR_NORMAL = 3;
    public static final int STATE_BAR_EXDELAY = 4;
    public static final int STATE_BAR_DELAY = 5;

    public int getStateBarType() {
	if (ProjectStateFlag.TASK_STATE_COMPLETE == taskState) {
	    return STATE_BAR_COMPLATE;
	}

	if (currentDate.getTime().before(taskPlanStartDate)) {
	    return STATE_BAR_NORMAL;
	}

	if (differDateGap >= 0) {

	    return STATE_BAR_NORMAL;
	} else {

	    Calendar today = Calendar.getInstance();

	    String todayStr = DateUtil.getDateString(today.getTime(), "d");

	    String planEndDateStr = DateUtil.getTimeFormat(taskPlanEndDate, "yyyy-MM-dd");

	    boolean todayBefore = todayStr.compareTo(planEndDateStr) > 0;

	    if (todayBefore) {
		return STATE_BAR_DELAY;
	    } else {
		return STATE_BAR_EXDELAY;
	    }

	}

    }

    public static int getStateBarType(E3PSTask task) {

	int state = task.getTaskState();
	if (ProjectStateFlag.TASK_STATE_COMPLETE == state) {
	    return STATE_BAR_COMPLATE;
	}
	Calendar currentDate = Calendar.getInstance();

	Timestamp taskPlanStartDate = ((ExtendScheduleData) task.getTaskSchedule().getObject()).getPlanStartDate();
	Timestamp taskPlanEndDate = ((ExtendScheduleData) task.getTaskSchedule().getObject()).getPlanEndDate();

	if (currentDate.getTime().before(taskPlanStartDate)) {
	    return STATE_BAR_NORMAL;
	}

	double differDateGap = getDifferDateGap(task);

	if (differDateGap >= 0) {

	    return STATE_BAR_NORMAL;
	} else {

	    Calendar today = Calendar.getInstance();

	    String todayStr = DateUtil.getDateString(today.getTime(), "d");

	    String planEndDateStr = DateUtil.getTimeFormat(taskPlanEndDate, "yyyy-MM-dd");

	    boolean todayBefore = todayStr.compareTo(planEndDateStr) > 0;

	    if (todayBefore) {
		return STATE_BAR_DELAY;
	    } else {
		return STATE_BAR_EXDELAY;
	    }

	}

    }

    public String getEndNodeStateStr() throws Exception {

	int stateBarType = getStateBarType();

	return getStateBarTypeString(stateBarType);

    }

    public String getEndNodeStateStr2() throws Exception {

	int stateBarType = getStateBarType();

	return getStateBarTypeString2(stateBarType);

    }

    public String getStateBarTypeString2(int stateBarType) {
	if (stateBarType == STATE_BAR_COMPLATE) {
	    return "<table><tr><td><img src=" + ProjectStateFlag.iconUrl + "/state_green_bar.gif></td></tr></table>";
	}

	/*
	 * if(stateBarType == STATE_BAR_NONSTART){ return "<b><font color=\"blue\">시작전 </font></b>"; }
	 */

	if (stateBarType == STATE_BAR_NORMAL) {
	    return "<table><tr><td><img src=" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif></td></tr></table>";
	}

	if (stateBarType == STATE_BAR_DELAY) {
	    return "<table><tr>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_red_bar.gif></td></tr></table>";

	}

	if (stateBarType == STATE_BAR_EXDELAY) {
	    return "<table><tr><td><img src=" + ProjectStateFlag.iconUrl + "/state_yellow_bar.gif></td></tr></table>";
	}

	return "";
    }

    public String getStateBarTypeString(int stateBarType) {
	if (stateBarType == STATE_BAR_COMPLATE) {
	    return "<center><table><tr title='종료되었습니다'><td><img src=" + ProjectStateFlag.iconUrl + "/state_green_bar.gif></td>"
		    + "</tr></table></center>";

	    // <td><img src=" + ProjectStateFlag.iconUrl
	    // + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
	    // + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
	    // + "/state_blank_bar.gif></td>"

	}

	/*
	 * if(stateBarType == STATE_BAR_NONSTART){ return "<b><font color=\"blue\">시작전 </font></b>"; }
	 */

	if (stateBarType == STATE_BAR_NORMAL) {
	    return "<center><table><tr title='정상적으로 진행중입니다'><td><img src=" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif></td>"
		    + "</tr></table></center>";

	    // + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src="
	    // + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl
	    // + "/state_blank_bar.gif></td>
	}

	if (stateBarType == STATE_BAR_DELAY) {
	    return "<center><table><tr title='지연되고 있습니다.'><td><img src=" + ProjectStateFlag.iconUrl + "/state_red_bar.gif></td>"
		    + "</tr></table></center>";
	}

	if (stateBarType == STATE_BAR_EXDELAY) {
	    return "<center><table><tr title='예상 지연 입니다.'><td><img src=" + ProjectStateFlag.iconUrl + "/state_yellow_bar.gif></td>"
		    + "</tr></table></center>";
	}

	return "";
    }

    /*
     * public String getEndNodeStateStr() throws Exception {
     * 
     * 
     * if (ProjectStateFlag.TASK_STATE_COMPLETE == taskState) { return "<table><tr title='종료되었습니다'><td><img src=" + ProjectStateFlag.iconUrl
     * + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_green_bar.gif></td>" + "<td><img src=" +
     * ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"+"<td><img src=" + ProjectStateFlag.iconUrl +"/state_blank_bar.gif></td>"+
     * "</td></tr></table>"; }
     * 
     * if(preferComp < 15){
     * 
     * return "<table><tr title='정상적으로 진행중입니다'>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blue_bar.gif></td>" +
     * "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_blank_bar.gif></td></tr></table>"; }
     * 
     * if(currentDate.getTime().before(taskPlanStartDate)){ return "<b><font color=\"blue\">시작전 </font></b>"; }
     * 
     * 
     * if ( differDateGap >= 0) { return "<table><tr title='정상적으로 진행중입니다'>" + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_blue_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" +
     * ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_blank_bar.gif></td></tr></table>"; } else{
     * 
     * Calendar today = Calendar.getInstance();
     * 
     * 
     * String todayStr = DateUtil.getDateString(today.getTime(), "d");
     * 
     * String planEndDateStr = DateUtil.getTimeFormat(taskPlanEndDate, "yyyy-MM-dd");
     * 
     * boolean todayBefore = todayStr.compareTo(planEndDateStr) > 0;
     * 
     * 
     * //DateUtil.getToDay(format)
     * 
     * if(todayBefore){ return "<table><tr title='지연되고 있습니다.'>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
     * + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_red_bar.gif></td></tr></table>"; }else{ return
     * "<table><tr title='지연 예정 중입니다.'>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" +
     * ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_yellow_bar.gif></td>"
     * + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td></tr></table>"; }
     * 
     * if(Double.parseDouble(this.getPreferCompStr()) != 100 && this.taskState != ProjectStateFlag.TASK_STATE_COMPLETE &&
     * e3psTask.getTaskCompletion()>0 ){ return "<table><tr title='지연 진행중입니다.'>" + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" +
     * ProjectStateFlag.iconUrl + "/state_yellow_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_blank_bar.gif></td></tr></table>"; }else { return "<table><tr title='지연되고 있습니다.'>" + "<td><img src=" +
     * ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" +
     * "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_red_bar.gif></td></tr></table>"; }
     * 
     * }
     * 
     * 
     * // if ( differDateGap > 0.2 ) { // return "<table><tr title='" + nf.format(differDateGap) + "일 초과상태입니다'>" // + "<td><img src=" +
     * ProjectStateFlag.iconUrl + "/state_blue_bar.gif></td>" // + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>"
     * // + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td></tr></table>"; // } else if ( 0.2 >= differDateGap &&
     * differDateGap >= -0.2 ) { // return "<table><tr title='정상적으로 진행중입니다'>" // + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_blue_bar.gif></td>" // + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" // + "<td><img src=" +
     * ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td></tr></table>"; // } else { // return "<table><tr title='" +
     * nf.format(differDateGap) + "일 늦어지고 있습니다'>" // + "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" // +
     * "<td><img src=" + ProjectStateFlag.iconUrl + "/state_blank_bar.gif></td>" // + "<td><img src=" + ProjectStateFlag.iconUrl +
     * "/state_red_bar.gif></td></tr></table>"; // } }
     */

    public String getDifferDateGapStr() {

	if (ProjectStateFlag.TASK_STATE_COMPLETE == taskState) {
	    double resultTime = taskPlanEndDate.getTime() - taskExecEndDate.getTime();
	    resultTime /= (double) 0x5265c00L;
	    return nf.format(resultTime);

	}
	return nf.format(differDateGap);
    }
}
