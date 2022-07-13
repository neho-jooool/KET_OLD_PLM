package e3ps.project.beans;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.content.ContentHelper;
import wt.fc.ObjectNoLongerExistsException;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.AssessTemplateTaskLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ElectronTemplateProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.ProductProject;
import e3ps.project.ProductTemplateProject;
import e3ps.project.ProjectDeptRole;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectIssueAnswer;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.QuestionAnswerLink;
import e3ps.project.ReviewProject;
import e3ps.project.ScheduleData;
import e3ps.project.TaskAssessResult;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TaskMemberLink;
import e3ps.project.TaskTrustResult;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateProjectTemplateTaskLink;
import e3ps.project.TemplateTask;
import e3ps.project.TrustTemplateTaskLink;
import e3ps.project.WorkProject;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.project.gate.entity.GateAssRsltTaskLink;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.log.Kogger;

public class TaskHelper {
    public static final TaskHelper manager = new TaskHelper();

    private TaskHelper() {
    }

    public void copyRojectDeptRole(TemplateProject toProject, TemplateProject fromProject) throws Exception {

	QueryResult rs = getDeptRoleFromProject(fromProject);

	while (rs.hasMoreElements()) {
	    ProjectDeptRole projectDeptRole = (ProjectDeptRole) rs.nextElement();

	    ProjectDeptRole newRole = (ProjectDeptRole) HistoryHelper.duplicate(projectDeptRole);

	    newRole.setProject(toProject);

	    PersistenceHelper.manager.save(newRole);
	}

    }

    public QueryResult getDeptRoleFromProject(TemplateProject fromProject) throws Exception {
	long projectId = CommonUtil.getOIDLongValue(fromProject);

	QuerySpec spec = new QuerySpec(ProjectDeptRole.class);

	spec.appendWhere(new SearchCondition(ProjectDeptRole.class, "projectReference.key.id", "=", projectId), new int[] { 0 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	return rs;
    }

    public QueryResult getDeptRole(ProjectDeptRole projectdeptRole) {

	QueryResult rs = null;
	try {
	    TemplateProject project = projectdeptRole.getProject();
	    String deptRole = projectdeptRole.getDeptRole();

	    long projectId = CommonUtil.getOIDLongValue(project);
	    QuerySpec spec = new QuerySpec(TemplateTask.class);
	    spec.appendWhere(new SearchCondition(TemplateTask.class, "projectReference.key.id", "=", projectId), new int[] { 0 });
	    spec.appendAnd();
	    // spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.DEPT_ROLE, "=", deptRole), new int[]{0});
	    rs = PersistenceHelper.manager.find(spec);

	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}

	return rs;
    }

    public TemplateTask modifyTask(TemplateTask task) {
	try {
	    return (TemplateTask) PersistenceHelper.manager.modify(task);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public ProjectDeptRole getProjectDeptRole(TemplateProject project, String deptRole) {
	ProjectDeptRole deptrole = null;
	try {
	    long projectId = CommonUtil.getOIDLongValue(project);

	    QuerySpec spec = new QuerySpec(ProjectDeptRole.class);

	    spec.appendWhere(new SearchCondition(ProjectDeptRole.class, "projectReference.key.id", "=", projectId), new int[] { 0 });

	    spec.appendAnd();

	    spec.appendWhere(new SearchCondition(ProjectDeptRole.class, ProjectDeptRole.DEPT_ROLE, "=", deptRole), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(spec);

	    if (rs.hasMoreElements()) {
		deptrole = (ProjectDeptRole) rs.nextElement();
	    }

	} catch (Exception ex) {
	    Kogger.error(getClass(), ex);
	}

	return deptrole;
    }

    public Hashtable getRoleDept(TemplateProject project) {

	Hashtable hash = new Hashtable();

	try {
	    long projectId = CommonUtil.getOIDLongValue(project);

	    QuerySpec spec = new QuerySpec(ProjectDeptRole.class);
	    spec.appendWhere(new SearchCondition(ProjectDeptRole.class, "projectReference.key.id", "=", projectId), new int[] { 0 });

	    QueryResult rs = PersistenceHelper.manager.find(spec);

	    while (rs.hasMoreElements()) {

		ProjectDeptRole projectDeptRole = (ProjectDeptRole) rs.nextElement();
		hash.put(projectDeptRole.getDeptRole(), projectDeptRole.getDepartment());
	    }

	} catch (Exception e) {

	    Kogger.error(getClass(), e);
	}

	return hash;

    }

    public void checkCompletion(E3PSProject project) {

	try {
	    QueryResult qr = ProjectTaskHelper.manager.getChild(project);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		E3PSTask task = (E3PSTask) objArr[0];
		if (!ProjectTaskHelper.manager.isLast(task)) {
		    setCompletionLogic(task);
		}
	    }
	} catch (ObjectNoLongerExistsException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void checkCompleteTask(E3PSProject project) {
	try {
	    QueryResult qr = ProjectTaskHelper.manager.getChild(project);
	    while (qr != null && qr.hasMoreElements()) {
		Object[] objArr = (Object[]) qr.nextElement();
		E3PSTask task = (E3PSTask) objArr[0];
		if (ProjectTaskHelper.manager.isLast(task)) {
		    task = setCompleteTask(task, task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE);
		    task = (E3PSTask) PersistenceHelper.manager.refresh(task);
		} else {
		    QueryResult subQr = ProjectTaskHelper.manager.getChild(task);
		    while (subQr != null && subQr.hasMoreElements()) {
			Object[] subObjArr = (Object[]) subQr.nextElement();
			E3PSTask subTask = (E3PSTask) subObjArr[0];
			subTask = setCompleteTask(subTask, subTask.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE);
			subTask = (E3PSTask) PersistenceHelper.manager.refresh(subTask);
		    }
		}
	    }
	} catch (ObjectNoLongerExistsException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public E3PSTask setCompleteTask(E3PSTask task, boolean state) {
	try {

	    // Kogger.debug(getClass(), task.getTaskState() + ":" + ProjectStateFlag.TASK_STATE_COMPLETE);
	    // Kogger.debug(getClass(), PersistenceHelper.isPersistent(task));
	    if (task.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE)
		return task;

	    Calendar cal = Calendar.getInstance();
	    if (state) {
		// Task 종료시에 진행률을 100으로 재정의 한다.
		task.setTaskCompletion(100);
		task = (E3PSTask) PersistenceHelper.manager.modify(task);

		ExtendScheduleData scheduler = (ExtendScheduleData) task.getTaskSchedule().getObject();
		scheduler.setExecEndDate(new java.sql.Timestamp(cal.getTime().getTime()));
		PersistenceHelper.manager.modify(scheduler);

		task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
		task = (E3PSTask) PersistenceHelper.manager.modify(task);

		// Task의 종료시 상위 Task를 체크해서 필요시 같이 종료 시킴
		if (task.getParent() != null) {
		    // 상위 Task가 있는 경우
		    E3PSTask parentTask = (E3PSTask) task.getParent();
		    QueryResult result = ProjectTaskHelper.manager.getChildList(parentTask);
		    boolean parentState = true;
		    while (result != null && result.hasMoreElements()) {
			E3PSTask currentTask = (E3PSTask) result.nextElement();
			if (currentTask.getTaskState() == ProjectStateFlag.TASK_STATE_PROGRESS) {
			    parentState = false;
			    break;
			}
		    }

		    if (parentState) {
			scheduler = (ExtendScheduleData) parentTask.getTaskSchedule().getObject();
			scheduler.setExecEndDate(new java.sql.Timestamp(cal.getTime().getTime()));
			PersistenceHelper.manager.modify(scheduler);
			parentTask.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
			parentTask = (E3PSTask) PersistenceHelper.manager.modify(parentTask);
		    }
		}

		// 후행 테스크 체크하여 메일 발송
		QueryResult qr = PersistenceHelper.manager.navigate(task, TaskDependencyLink.DEPEND_SOURCE_ROLE, TaskDependencyLink.class);
		while (qr.hasMoreElements()) {
		    E3PSTask btask = (E3PSTask) qr.nextElement();
		    // TaskMailData data = new TaskMailData(btask);
		    try {
			ProjectScheduler.sendMail(btask);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		}

	    } else {
		task.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);
		task = (E3PSTask) PersistenceHelper.manager.modify(task);
		if (task.getParent() != null) {
		    E3PSTask parentTask = (E3PSTask) task.getParent();
		    parentTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);
		    parentTask = (E3PSTask) PersistenceHelper.manager.modify(parentTask);
		}
	    }
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return task;
    }

    public void setCompletion(E3PSTask _task, int compValue) throws Exception {

	_task = (E3PSTask) PersistenceHelper.manager.refresh(_task);

	// 기존에 진행률이 100%가 되었을 경우 Task를 자동 종료 시키는 기능을 제거함...
	if (compValue == 100) {
	    // _task.setTaskState(ProjectStateFlag.TASK_STATE_COMPLETE);
	    setCompleteTask(_task, true);
	    _task = (E3PSTask) PersistenceHelper.manager.refresh(_task);
	} else {
	    _task.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);
	}
	_task.setTaskCompletion(compValue);
	_task = (E3PSTask) PersistenceHelper.manager.modify(_task);

	Persistable persistable = ProjectTaskHelper.manager.getParent(_task);
	if (persistable instanceof E3PSTask) { // LEVEL1 JELTASK

	    setCompletionLogic((E3PSTask) persistable);
	} else if (persistable instanceof E3PSProject) { // LEVEL1 JELTASK
	    setCompletionLogic((E3PSProject) persistable);
	}

    }

    public void setCompletionLogic(E3PSTask task) throws Exception {

	QueryResult level1TaskQr = ProjectTaskHelper.manager.getChildList(task);
	int totalDuration = 0; // LEVEL1 JELTASK에 총 기간
	int completeDuration = 0; // LEVEL2 JELTASK 중 완료된 LEVEL2 JELTASK에 기간
	double currentComplete = 0.0D; // LEVEL2 JELTASK 진행률
	double compIntValue = 0.0D;
	int count = 0;
	E3PSTask level2TaskArr[] = new E3PSTask[level1TaskQr.size()];

	while (level1TaskQr != null && level1TaskQr.hasMoreElements()) {
	    E3PSTask level2Task = (E3PSTask) level1TaskQr.nextElement();
	    E3PSTaskData level2TaskData = new E3PSTaskData(level2Task);
	    level2TaskArr[count] = level2Task;
	    if (level2TaskData.taskState == ProjectStateFlag.TASK_STATE_COMPLETE) {
		completeDuration += level2TaskData.taskDuration;
	    }
	    totalDuration += level2TaskData.taskDuration;
	    count++;
	}

	for (int i = 0; i < level2TaskArr.length; i++) {
	    E3PSTaskData currentTaskData = new E3PSTaskData(level2TaskArr[i]);
	    currentComplete = currentTaskData.taskCompletion;
	    compIntValue += ((double) completeDuration / (double) totalDuration) * currentComplete;
	}

	if (compIntValue > 99D)
	    compIntValue = 100D;

	if (level1TaskQr != null) {
	    ((E3PSTask) task).setTaskCompletion((int) compIntValue);
	    PersistenceHelper.manager.save(task);
	}
    }

    public void setCompletionLogic(E3PSProject task) throws Exception {

	QueryResult level1TaskQr = ProjectTaskHelper.manager.getChildList(task);
	int totalDuration = 0; // LEVEL1 JELTASK에 총 기간
	int completeDuration = 0; // LEVEL2 JELTASK 중 완료된 LEVEL2 JELTASK에 기간
	double currentComplete = 0.0D; // LEVEL2 JELTASK 진행률
	double compIntValue = 0.0D;
	int count = 0;
	E3PSTask level2TaskArr[] = new E3PSTask[level1TaskQr.size()];

	while (level1TaskQr != null && level1TaskQr.hasMoreElements()) {
	    E3PSTask level2Task = (E3PSTask) level1TaskQr.nextElement();
	    E3PSTaskData level2TaskData = new E3PSTaskData(level2Task);
	    level2TaskArr[count] = level2Task;
	    if (level2TaskData.taskState == ProjectStateFlag.TASK_STATE_COMPLETE) {
		completeDuration += level2TaskData.taskDuration;
	    }
	    totalDuration += level2TaskData.taskDuration;
	    count++;
	}

	for (int i = 0; i < level2TaskArr.length; i++) {
	    E3PSTaskData currentTaskData = new E3PSTaskData(level2TaskArr[i]);
	    currentComplete = currentTaskData.taskCompletion;
	    compIntValue += ((double) completeDuration / (double) totalDuration) * currentComplete;
	}

	if (compIntValue > 99D)
	    compIntValue = 100D;

	if (level1TaskQr != null) {
	    ((E3PSProject) task).setPjtCompletion((int) compIntValue);
	    PersistenceHelper.manager.save(task);
	}
    }

    /**
     * project에서 TemplateProject 저장시 또는 Template 에서 Template으로 복사시 사용
     * 
     * @param project
     * @param fromTemplate
     * @throws Exception
     */
    public void copyTemplateProjectFromTemplateProject(TemplateProject project, TemplateProject fromTemplate) throws Exception {

	DefaultProjectTreeNode root = DefaultProjectTreeNode.getLoadTree(fromTemplate);
	makeTemplateTaskTree(project, root);
	// Kogger.debug(getClass(), "count = " + root.getChildCount());

	makeDependency(root);
    }

    public void makeTemplateTaskTree(TemplateProject project, DefaultProjectTreeNode node) throws Exception {

	makeTemplateTask(project, node);

	for (int i = 0; i < node.getChildCount(); i++) {
	    makeTemplateTaskTree(project, (DefaultProjectTreeNode) node.getChildAt(i));
	}
    }

    public void makeTemplateTask(TemplateProject project, DefaultProjectTreeNode node) throws Exception {
	TreeNodeData data = (TreeNodeData) node.getUserObject();
	if (data.getData() instanceof TemplateTask) {

	    TemplateTask fromTask = (TemplateTask) data.getData();

	    ScheduleData taskScheduleData = (ScheduleData) HistoryHelper.duplicate((ScheduleData) fromTask.getTaskSchedule().getObject());
	    taskScheduleData = (ScheduleData) PersistenceHelper.manager.save(taskScheduleData);

	    TemplateTask toTask = null;
	    WTUser master = null;
	    /*
	     * Kogger.debug(getClass(), "makeTemplateTask222222222222#####################WBS 태스크 attr 카피 비교  #######################");
	     * Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() ); Kogger.debug(getClass(),
	     * "#####taskSeq :"+fromTask.getTaskSeq() ); Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );
	     */

	    if (fromTask instanceof E3PSTask) {
		toTask = TemplateTask.newTemplateTask();
		toTask.setTaskName(fromTask.getTaskName());
		toTask.setTaskNameEn(fromTask.getTaskNameEn());
		toTask.setTaskNo(fromTask.getTaskNo());
		toTask.setTaskSeq(fromTask.getTaskSeq());
		// toTask.setTaskType(fromTask.getTaskSeq());
		toTask.setTaskHistory(fromTask.getTaskHistory());
		toTask.setTaskDesc(fromTask.getTaskDesc());
		// 추가
		toTask.setDrValue(fromTask.getDrValue());
		toTask.setDrValueCondition(fromTask.getDrValueCondition());
		toTask.setScheduleType(fromTask.getScheduleType());
		toTask.setPriorityControl(fromTask.getPriorityControl());
		toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
		toTask.setOptionType(fromTask.isOptionType());
		toTask.setMileStone(fromTask.isMileStone());
		toTask.setTaskType(fromTask.getTaskType());
		toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
		toTask.setProject(project);
		toTask.setPersonRole(fromTask.getPersonRole());

		if (fromTask.getDepartment() != null) {

		    toTask.setDepartment(fromTask.getDepartment());

		}

	    } else {
		toTask = (TemplateTask) HistoryHelper.duplicate(fromTask);

		OEMProjectType fromOEM = fromTask.getProject().getOemType();

		OEMProjectType toOEM = toTask.getProject().getOemType();

		long fromOEMId = 0L;
		long toOEMId = 0L;

		if (toOEM != null) {

		    toOEMId = toOEM.getPersistInfo().getObjectIdentifier().getId();
		}
		if (fromOEM != null) {

		    fromOEMId = fromOEM.getPersistInfo().getObjectIdentifier().getId();
		}

		if (toOEMId != fromOEMId) {
		    toTask.setOemType(null);
		}

	    }

	    // 불필요 로직으로 판단되어 주석처리함 2022.05.13
	    // TemplateProject fromProject = (TemplateProject) fromTask.getProject();
	    // String toTeamType = this.getTeamTypeByProject(project);
	    // String fromTeamType = this.getTeamTypeByProject(fromProject);
	    // if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
	    // toTask.setPersonRole(fromTask.getPersonRole());
	    // } else {
	    // toTask.setPersonRole("");
	    // }
	    toTask.setPersonRole(fromTask.getPersonRole());

	    toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
	    toTask.setTaskDesc(fromTask.getTaskDesc());
	    toTask.setProject(project);

	    DefaultProjectTreeNode pnode = (DefaultProjectTreeNode) node.getParent();

	    TreeNodeData pnode_data = (TreeNodeData) pnode.getUserObject();

	    boolean isLevelOne = false;

	    if (pnode_data.getData() instanceof TemplateTask) {
		toTask.setParent(pnode.getCopyTask());
	    } else {
		isLevelOne = true;
	    }

	    toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
	    toTask = (TemplateTask) PersistenceHelper.manager.save(toTask);

	    if (master != null) {
		TaskUserHelper.manager.setMaster(toTask, master);
	    }

	    // Kogger.debug(getClass(), "save = " + toTask);

	    copyWBSItemLink(toTask, fromTask);

	    node.setCopyTask(toTask);

	    // copyTaskUser(userMap, toTask, fromTask);

	    ProjectOutputHelper.manager.copyTaskOutputInfo(new HashMap(), new HashMap(), toTask, fromTask);
	}
    }

    public void copyTaskMaster(TemplateTask toTask, TemplateTask fromTask) throws Exception {
	WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());

	if (master != null) {
	    if (!ProjectUserHelper.manager.isProjectUser(toTask.getProject(), master)) {
		ProjectUserHelper.manager.setMember(toTask.getProject(), master);
	    }
	    TaskUserHelper.manager.setMaster(toTask, master);
	}
    }

    private String getTeamTypeByProject(TemplateProject project) {
	String division = "";
	if (project instanceof TemplateProject) {
	    division = StringUtil.checkNull(((TemplateProject) project).getDivision());
	} else if (project instanceof ProductTemplateProject) {
	    division = StringUtil.checkNull(((ProductTemplateProject) project).getDivision());
	} else if (project instanceof MoldTemplateProject) {
	    division = StringUtil.checkNull(((MoldTemplateProject) project).getDivision());
	} else if (project instanceof ElectronTemplateProject) {
	    division = StringUtil.checkNull(((ElectronTemplateProject) project).getDivision());
	}
	return division.trim();
    }

    private String getTeamTypeByProjectNew(TemplateProject project) {
	String division = "";
	if (project instanceof ProductProject) {
	    division = StringUtil.checkNull(((ProductProject) project).getTeamType());
	} else if (project instanceof MoldProject) {
	    division = StringUtil.checkNull(((MoldProject) project).getMoldInfo().getProject().getTeamType());
	} else if (project instanceof ReviewProject) {
	    division = StringUtil.checkNull(((ReviewProject) project).getAttr1());
	} else if (project instanceof WorkProject) {
	    division = "자동차사업부";
	}
	return division.trim().replaceAll(" ", "");
    }

    public HashMap getRoleMap(TemplateProject project) {
	HashMap map = new HashMap();
	QueryResult qr = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.ROLEMEMBER);
	while (qr != null && qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    ProjectMemberLink temp = (ProjectMemberLink) obj[0];
	    map.put(temp.getPjtRole(), temp.getMember());
	}
	return map;
    }

    public HashMap getUserMap(TemplateProject project) throws Exception {
	QueryResult list = ProjectUserHelper.manager.getAllProjectUser(project);
	HashMap map = new HashMap();

	while (list.hasMoreElements()) {
	    Object[] objArr = (Object[]) list.nextElement();
	    ProjectMemberLink link = (ProjectMemberLink) objArr[0];
	    int type = link.getPjtMemberType();

	    WTUser user = link.getMember();
	    long userID = user.getPersistInfo().getObjectIdentifier().getId();
	    map.put(userID + "$" + type, link);
	}
	return map;
    }

    public HashMap getViewDepMap(TemplateProject project) throws Exception {
	QueryResult list = PersistenceHelper.manager.navigate(project, ProjectViewDepartmentLink.DEPARTMENT_ROLE,
	        ProjectViewDepartmentLink.class, false);
	HashMap map = new HashMap();
	while (list.hasMoreElements()) {
	    ProjectViewDepartmentLink link = (ProjectViewDepartmentLink) list.nextElement();
	    long id = link.getDepartment().getPersistInfo().getObjectIdentifier().getId();
	    map.put(String.valueOf(id), link);
	}
	return map;

    }

    /**
     * 함수명 : copyProjectFromProject 수정내용 : [PLM System 1차개선] Project 정보 복사 시 선후행 관계 설정 로직 추가
     * 
     * @param toProject
     * @param fromProject
     * @throws Exception
     *             수정자 : BoLee 수정일자 : 2013. 7. 10.
     */
    public void copyProjectFromProject(E3PSProject toProject, E3PSProject fromProject) throws Exception {

	// [START] [PLM System 1차개선] 선후행 관계 설정 로직 추가, 2013-07-10, BoLee

	ProjectTreeModel model = null;
	ProjectTreeNode root = null;

	model = new ProjectTreeModel(fromProject);
	model.setTree();
	model.checkAllSchedule();
	model.setDependency(); // 선후행 관계 설정
	root = model.getRoot();

	// [END] [PLM System 1차개선] 선후행 관계 설정 로직 추가, 2013-07-10, BoLee

	// Task 정보 복사
	makeTaskProjectTree(toProject, getUserMap(toProject), getViewDepMap(toProject), root);

	// 선후행 정보 복사
	makeDependency(root);
    }

    public void makeTaskProjectTree(E3PSProject project, HashMap userMap, HashMap dviewMap, ProjectTreeNode node) throws Exception {

	makeTaskFromTask(project, userMap, dviewMap, node);

	for (int i = 0; i < node.getChildCount(); i++) {
	    makeTaskProjectTree(project, userMap, dviewMap, (ProjectTreeNode) node.getChildAt(i));
	}
    }

    /**
     * 함수명 : makeTaskFromTask 수정내용 : [PLM System 1차개선] Task 정보 복사 시 계획공수(hr) 복사 추가
     * 
     * @param project
     * @param userMap
     * @param dviewMap
     * @param node
     * @throws Exception
     *             수정자 : BoLee 수정일자 : 2013. 7. 10.
     */
    private void makeTaskFromTask(E3PSProject project, HashMap userMap, HashMap dviewMap, ProjectTreeNode node) throws Exception {

	ProjectTreeNodeData data = (ProjectTreeNodeData) node.getUserObject();
	if (data.getData() instanceof E3PSTask) {
	    E3PSTask fromTask = (E3PSTask) data.getData();
	    ExtendScheduleData taskScheduleData = new ExtendScheduleData();
	    taskScheduleData.setDuration(data.getDuration());
	    taskScheduleData.setPlanStartDate(data.getPlanStartDate());
	    taskScheduleData.setPlanEndDate(data.getPlanEndDate());
	    // taskScheduleData.setExecStartDate(data.getPlanStartDate());
	    taskScheduleData.setExecStartDate(data.getSchedule().getExecStartDate());
	    taskScheduleData.setExecEndDate(data.getSchedule().getExecEndDate());
	    taskScheduleData.setStdWork(data.getSchedule().getStdWork());
	    // [START] [PLM System 1차개선] Task 정보 복사 시 계획공수(hr) 복사 추가, 2013-07-10, BoLee
	    taskScheduleData.setPlanManHour(data.getSchedule().getPlanManHour());
	    // [END] [PLM System 1차개선] Task 정보 복사 시 계획공수(hr) 복사 추가, 2013-07-10, BoLee
	    taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
	    E3PSTask toTask = (E3PSTask) HistoryHelper.duplicate(fromTask);
	    toTask.setTaskName(fromTask.getTaskName());
	    toTask.setTaskNameEn(fromTask.getTaskNameEn());
	    toTask.setTaskSeq(fromTask.getTaskSeq());
	    toTask.setTaskState(fromTask.getTaskState());
	    // 추가
	    toTask.setDrValue(fromTask.getDrValue());
	    toTask.setDrValueCondition(fromTask.getDrValueCondition());
	    toTask.setScheduleType(fromTask.getScheduleType());
	    toTask.setPriorityControl(fromTask.getPriorityControl());
	    toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
	    toTask.setOptionType(fromTask.isOptionType());
	    toTask.setMileStone(fromTask.isMileStone());
	    toTask.setTaskType(fromTask.getTaskType());
	    toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
	    toTask.setProject(project);
	    toTask.setPersonRole(fromTask.getPersonRole());

	    // task.setTaskState();
	    toTask.setTaskDesc(fromTask.getTaskDesc());
	    toTask.setProject(project);
	    toTask.setProject(project);
	    ProjectTreeNode pnode = (ProjectTreeNode) node.getParent();
	    if (pnode.getCopyTask() != null) {
		toTask.setParent(pnode.getCopyTask());
	    }

	    toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
	    toTask.setOldTaskOid(CommonUtil.getOIDString(fromTask));
	    toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);
	    node.setCopyTask(toTask);

	    // Gate 평가결과 복사
	    copyGate(toTask, fromTask);
	    // 이슈관리
	    copyIssue(toTask, fromTask);
	    copyTaskUser(userMap, toTask, fromTask);
	    ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, dviewMap, toTask, fromTask);
	}
    }

    // Gate 평가결과 복사
    private void copyGate(E3PSTask toTask, E3PSTask fromTask) throws WTException, WTPropertyVetoException {
	QueryResult rs = PersistenceHelper.manager.navigate(fromTask, GateAssRsltTaskLink.ASSESS_ROLE, GateAssRsltTaskLink.class);
	while (rs.hasMoreElements()) {
	    GateAssessResult assessResult = (GateAssessResult) rs.nextElement();
	    // assessResult.setTask((E3PSTask) toTask);
	    GateAssRsltTaskLink assRsltTaskLink = GateAssRsltTaskLink.newGateAssRsltTaskLink(assessResult, toTask);
	    PersistenceServerHelper.manager.insert(assRsltTaskLink);
	}

	// 신뢰성 차수 정보 복사
	rs = PersistenceHelper.manager.navigate(fromTask, TrustTemplateTaskLink.TRUST_ROLE, TrustTemplateTaskLink.class);
	while (rs.hasMoreElements()) {
	    TaskTrustResult trustResult = (TaskTrustResult) rs.nextElement();
	    TaskTrustResult newTrustResult = TaskTrustResult.newTaskTrustResult();
	    newTrustResult.setRev(trustResult.getRev());
	    newTrustResult.setOkCnt(trustResult.getOkCnt());
	    newTrustResult.setNgCnt(trustResult.getNgCnt());
	    newTrustResult.setEstimateDate(trustResult.getEstimateDate());
	    newTrustResult.setDescription(trustResult.getDescription());
	    newTrustResult = (TaskTrustResult) PersistenceHelper.manager.save(newTrustResult);

	    // 신뢰성 차수 Task Link 복사
	    TrustTemplateTaskLink trustTemplateTaskLink = TrustTemplateTaskLink.newTrustTemplateTaskLink(newTrustResult, toTask);
	    PersistenceServerHelper.manager.insert(trustTemplateTaskLink);
	}

	// 평가관리 차수 정보 복사
	if ("DR".equalsIgnoreCase(fromTask.getTaskType())) {

	    rs = PersistenceHelper.manager.navigate(fromTask, AssessTemplateTaskLink.ASSESS_ROLE, AssessTemplateTaskLink.class);
	    while (rs.hasMoreElements()) {
		TaskAssessResult assessResult = (TaskAssessResult) rs.nextElement();
		TaskAssessResult newAssessResult = TaskAssessResult.newTaskAssessResult();
		newAssessResult.setRev(assessResult.getRev());
		newAssessResult.setDescription(assessResult.getDescription());
		newAssessResult.setResult(assessResult.getResult());
		newAssessResult.setResultScore(assessResult.getResultScore());
		newAssessResult.setTargetScore(assessResult.getTargetScore());
		newAssessResult = (TaskAssessResult) PersistenceHelper.manager.save(newAssessResult);

		// 평가관리 차수 Task Link 복사
		AssessTemplateTaskLink assessTemplateTaskLink = AssessTemplateTaskLink.newAssessTemplateTaskLink(newAssessResult, toTask);
		PersistenceServerHelper.manager.insert(assessTemplateTaskLink);
	    }
	}

	// 신뢰성 Task 링크 관계
	// rs = PersistenceHelper.manager.navigate(fromTask, TrustTemplateTaskLink.TRUST_ROLE, TrustTemplateTaskLink.class);
	// while (rs.hasMoreElements()) {
	// TaskTrustResult trustResult = (TaskTrustResult) rs.nextElement();
	// TrustTemplateTaskLink trustTemplateTaskLink = TrustTemplateTaskLink.newTrustTemplateTaskLink(trustResult, toTask);
	// PersistenceServerHelper.manager.insert(trustTemplateTaskLink);
	// }

	// QueryResult rs = PersistenceHelper.manager.navigate(fromTask, GateAssRsltTaskLink.ASSESS_ROLE, GateAssRsltTaskLink.class,true);
	// while (rs.hasMoreElements()) {
	// Kogger.debug(getClass(), ">>> Gate 평가결과와 Task link 변경");
	// GateAssessResult assessResult = (GateAssessResult) rs.nextElement();
	// assessResult.setTask((E3PSTask) toTask);
	// PersistenceServerHelper.manager.update(assessResult);
	// }
    }

    /*
     * public static QueryResult getIssue(TemplateTask task)throws Exception{
     * 
     * QuerySpec spec = new QuerySpec(Issue.class); spec.appendWhere(new SearchCondition(Issue.class, "taskReference.key.id", "=",
     * PersistenceHelper.getObjectIdentifier(task).getId()), new int[]{0}); return PersistenceHelper.manager.find(spec);
     * 
     * }
     */

    /*
     * public static QueryResult getEcr(Issue issue)throws Exception{
     * 
     * QueryResult rs = PersistenceHelper.manager.navigate(issue, IssueEcrLink.ECR_ROLE, IssueEcrLink.class); return rs; }
     */

    private static void copyIssue(TemplateTask toTask, TemplateTask fromTask) throws Exception {

	QueryResult qr = ProjectIssueHelper.manager.getTaskIssue((E3PSTask) fromTask);

	// Kogger.debug(getClass(), "copyISSUE>>>>> "+qr.size());

	E3PSTaskData data = new E3PSTaskData((E3PSTask) toTask);
	E3PSProject project = data.e3psProject;
	E3PSTask task = data.e3psTask;

	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    ProjectIssue fromIssue = (ProjectIssue) objArr[0];

	    // Kogger.debug(getClass(), "OLD ProjectISSUE<<<< "+CommonUtil.getOIDString(fromIssue));

	    ProjectIssue toIssue = (ProjectIssue) HistoryHelper.duplicate(fromIssue);
	    toIssue.setProject(project);
	    toIssue.setTask(task);
	    toIssue = (ProjectIssue) PersistenceHelper.manager.save(toIssue);

	    ContentHelper.service.copyContent(fromIssue, toIssue);
	    // KETContentHelper.service.copyContent(fromIssue, toIssue);

	    // Kogger.debug(getClass(), "NEW ProjectISSUE<<<< "+CommonUtil.getOIDString(toIssue));

	    Vector answerVec = ProjectIssueHelper.manager.getIssueAnswer(fromIssue);
	    for (int i = 0; i < answerVec.size(); i++) {
		ProjectIssueAnswerData fromAnswerData = (ProjectIssueAnswerData) answerVec.get(i);
		ProjectIssueAnswer fromAnswer = fromAnswerData.answer;

		// Kogger.debug(getClass(), "OLD ProjectIssueAnswer>>>>> "+CommonUtil.getOIDString(fromAnswer));

		ProjectIssueAnswer toAnswer = (ProjectIssueAnswer) HistoryHelper.duplicate(fromAnswer);
		toAnswer = (ProjectIssueAnswer) PersistenceHelper.manager.save(toAnswer);

		// ContentHelper.service.copyContent(fromAnswer, toAnswer);
		KETContentHelper.service.copyContent(fromAnswer, toAnswer);
		// Kogger.debug(getClass(), "NEW ProjectIssueAnswer>>>> "+CommonUtil.getOIDString(toAnswer));

		QuestionAnswerLink toLink = QuestionAnswerLink.newQuestionAnswerLink(toAnswer, toIssue);
		toLink = (QuestionAnswerLink) PersistenceHelper.manager.save(toLink);
		// Kogger.debug(getClass(), "NEW QuestionAnswerLink>>>> "+CommonUtil.getOIDString(toLink));
	    }
	}
    }

    private static void copyTaskUser(HashMap userMap, TemplateTask toTask, TemplateTask fromTask) throws Exception {

	QueryResult rs = TaskUserHelper.manager.getTaskAllUser(fromTask);
	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    TaskMemberLink link = (TaskMemberLink) o[0];
	    long userId = link.getMember().getMember().getPersistInfo().getObjectIdentifier().getId();
	    int memberType = link.getMember().getPjtMemberType();
	    ProjectMemberLink projectMemberLink = (ProjectMemberLink) userMap.get(userId + "$" + memberType);
	    TaskUserHelper.manager.setTaskUser(toTask, projectMemberLink, link.getTaskMemberType());
	}
    }

    private static void copyWBSItemLink(TemplateTask toTask, TemplateTask fromTask) throws Exception {
    }

    public void copyProjectFromTemplate(E3PSProject project, TemplateProject template) throws Exception {
	ScheduleData sdata = (ScheduleData) project.getPjtSchedule().getObject();

	TemplateProjectTreeNode root = TemplateProjectModel.getLoadTree(template, sdata.getPlanStartDate());
	if (template instanceof MoldTemplateProject) {
	    for (int i = (root.getChildCount() - 1); i > -1; i--) {
		TemplateProjectTreeNode node = (TemplateProjectTreeNode) root.getChildAt(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData) node.getUserObject();
		TemplateTask task = (TemplateTask) td.getData();
		if ("디버깅단계".equals(task.getTaskName())) {
		    task.setTaskType("디버깅");
		}
	    }
	    checkDebug(root, false);
	}

	makeTaskProjectTree(project, getRoleMap(project), root);
	makeDependency(root);
    }

    public void copyProjectFromTemplateNew(E3PSProject project, TemplateProject template, String[] category) throws Exception {
	ScheduleData sdata = (ScheduleData) project.getPjtSchedule().getObject();

	TemplateProjectTreeNode root = TemplateProjectModel.getLoadTree(template, sdata.getPlanStartDate());
	if (template instanceof MoldTemplateProject) {
	    for (int i = (root.getChildCount() - 1); i > -1; i--) {
		TemplateProjectTreeNode node = (TemplateProjectTreeNode) root.getChildAt(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData) node.getUserObject();
		TemplateTask task = (TemplateTask) td.getData();
		if ("디버깅단계".equals(task.getTaskName())) {
		    task.setTaskType("디버깅");
		}
	    }
	    checkDebug(root, false);
	}

	makeTaskProjectTreeNew(project, getRoleMap(project), root, category);
	makeDependency(root);
    }

    public void copyProjectFromTemplateNew(E3PSProject project, TemplateProject template, String[] category, String productType)
	    throws Exception {
	ScheduleData sdata = (ScheduleData) project.getPjtSchedule().getObject();

	TemplateProjectTreeNode root = TemplateProjectModel.getLoadTree(template, sdata.getPlanStartDate());
	if (template instanceof MoldTemplateProject) {
	    for (int i = (root.getChildCount() - 1); i > -1; i--) {
		TemplateProjectTreeNode node = (TemplateProjectTreeNode) root.getChildAt(i);
		TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData) node.getUserObject();
		TemplateTask task = (TemplateTask) td.getData();
		if ("디버깅단계".equals(task.getTaskName())) {
		    task.setTaskType("디버깅");
		}
	    }
	    checkDebug(root, false);
	}

	makeTaskProjectTreeNew(project, getRoleMap(project), root, category, productType);
	makeDependency(root);
    }

    private void checkDebug(TemplateProjectTreeNode node, boolean remove) {

	if (remove) {
	    node.removeAllPreTask();
	    node.removeAllAfterTask();
	    node.removeFromParent();
	}

	for (int i = (node.getChildCount() - 1); i > -1; i--) {
	    TemplateProjectTreeNode childNode = (TemplateProjectTreeNode) node.getChildAt(i);
	    TemplateProjectTreeNodeData td = (TemplateProjectTreeNodeData) childNode.getUserObject();
	    TemplateTask task = (TemplateTask) td.getData();
	    if (remove) {
		checkDebug(childNode, true);
	    } else if ("디버깅(1~N차)".equals(task.getTaskName())) {
		checkDebug(childNode, true);
	    } else {
		checkDebug(childNode, remove);
	    }
	}

    }

    private void makeDependency(DefaultProjectTreeNode node) throws Exception {

	Vector preTasks = node.getPreTasks();

	for (int i = 0; i < preTasks.size(); i++) {
	    DefaultProjectTreeNode preNode = (DefaultProjectTreeNode) preTasks.get(i);
	    int delay = node.getPreTaskDelayDuration(preNode);
	    if (node.getCopyTask() != null && preNode.getCopyTask() != null) {
		TaskDependencyLink link = TaskDependencyLink.newTaskDependencyLink(node.getCopyTask(), preNode.getCopyTask());
		link.setDelayDuration(delay);
		PersistenceHelper.manager.save(link);
	    }
	}

	for (int i = 0; i < node.getChildCount(); i++) {
	    makeDependency((DefaultProjectTreeNode) node.getChildAt(i));

	}
    }

    private void makeTaskProjectTree(E3PSProject project, HashMap userMap, TemplateProjectTreeNode node) throws Exception {

	makeTaskFromTemplate(project, userMap, node);

	for (int i = 0; i < node.getChildCount(); i++) {
	    makeTaskProjectTree(project, userMap, (TemplateProjectTreeNode) node.getChildAt(i));
	}
    }

    private void makeTaskProjectTreeNew(E3PSProject project, HashMap userMap, TemplateProjectTreeNode node, String[] category)
	    throws Exception {

	makeTaskFromTemplateNew(project, userMap, node, category);

	for (int i = 0; i < node.getChildCount(); i++) {
	    makeTaskProjectTreeNew(project, userMap, (TemplateProjectTreeNode) node.getChildAt(i), category);
	}
    }

    private void makeTaskProjectTreeNew(E3PSProject project, HashMap userMap, TemplateProjectTreeNode node, String[] category,
	    String productType) throws Exception {

	makeTaskFromTemplateNew(project, userMap, node, category, productType);

	for (int i = 0; i < node.getChildCount(); i++) {
	    makeTaskProjectTreeNew(project, userMap, (TemplateProjectTreeNode) node.getChildAt(i), category, productType);
	}
    }

    private void makeTaskFromTemplate(E3PSProject project, HashMap userMap, TemplateProjectTreeNode node) throws Exception {
	TemplateProjectTreeNodeData data = (TemplateProjectTreeNodeData) node.getUserObject();
	if (data.getData() instanceof TemplateTask) {
	    TemplateTask fromTask = (TemplateTask) data.getData();

	    ExtendScheduleData taskScheduleData = new ExtendScheduleData();
	    taskScheduleData.setDuration(data.getDuration());
	    // taskScheduleData.setStdWork(data.getStdWork());
	    taskScheduleData.setPlanStartDate(data.getPlanStartDate());
	    taskScheduleData.setPlanEndDate(data.getPlanEndDate());
	    // taskScheduleData.setExecStartDate(data.getPlanStartDate());

	    taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
	    // Kogger.debug(getClass(), "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
	    // Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
	    // Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
	    // Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

	    E3PSTask toTask = E3PSTask.newE3PSTask();
	    toTask.setTaskName(fromTask.getTaskName());
	    toTask.setTaskNameEn(fromTask.getTaskNameEn());
	    toTask.setTaskSeq(fromTask.getTaskSeq());
	    toTask.setTaskDesc(fromTask.getTaskDesc());
	    toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
	    toTask.setTaskType(fromTask.getTaskType());
	    toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
	    // 변경 항목 추가
	    toTask.setDrValue(fromTask.getDrValue());
	    toTask.setDrValueCondition(fromTask.getDrValueCondition());
	    toTask.setScheduleType(fromTask.getScheduleType());
	    toTask.setPriorityControl(fromTask.getPriorityControl());
	    toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
	    toTask.setOptionType(fromTask.isOptionType());
	    toTask.setMileStone(fromTask.isMileStone());

	    toTask.setPersonRole(fromTask.getPersonRole());

	    TemplateProject fromProject = (TemplateProject) fromTask.getProject();
	    // 불필요한 로직같아서 주석처리함 2022.05.13
	    // String toTeamType = this.getTeamTypeByProjectNew(project);
	    // String fromTeamType = this.getTeamTypeByProject(fromProject);
	    // if(project instanceof WorkProject || project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)){
	    // toTask.setPersonRole(fromTask.getPersonRole());
	    // }else {
	    // toTask.setPersonRole("");
	    // }

	    toTask.setProject(project);

	    // TempateTask 의 부서 정보 추가
	    if (fromTask.getDepartment() != null) {
		toTask.setDepartment(fromTask.getDepartment());
		WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
		if (master != null) {
		    if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
			ProjectUserHelper.manager.setMember(project, master);
		    }
		    TaskUserHelper.manager.setMaster(toTask, master);
		}
	    }
	    TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
	    if (pnode.getCopyTask() != null) {
		toTask.setParent(pnode.getCopyTask());
	    }
	    toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
	    toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

	    toTask.setDepartment(fromTask.getDepartment());
	    toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

	    if (fromTask.getDepartment() != null) {

		WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
		if (master != null) {
		    if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
			ProjectUserHelper.manager.setMember(project, master);
		    }
		    TaskUserHelper.manager.setMaster(toTask, master);
		}
	    }
	    node.setCopyTask(toTask);
	    copyTaskUser(userMap, toTask, fromTask);
	    ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
	}
    }

    private void makeTaskFromTemplateNew(E3PSProject project, HashMap userMap, TemplateProjectTreeNode node, String[] category)
	    throws Exception {
	TemplateProjectTreeNodeData data = (TemplateProjectTreeNodeData) node.getUserObject();
	if (data.getData() instanceof TemplateTask) {
	    TemplateTask fromTask = (TemplateTask) data.getData();

	    for (int i = 0; i < category.length; i++) {
		if ("common".equals(category[i])) {
		    if (fromTask.getCommon() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			toTask.setPersonRole(fromTask.getPersonRole());

			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);

			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		} else if ("mdraw".equals(category[i])) {
		    if (fromTask.getMdraw() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());

			toTask.setPersonRole(fromTask.getPersonRole());
			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);

			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		} else if ("hw".equals(category[i])) {
		    if (fromTask.getHw() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());

			toTask.setPersonRole(fromTask.getPersonRole());
			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);

			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		} else if ("sw".equals(category[i])) {
		    if (fromTask.getSw() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());

			toTask.setPersonRole(fromTask.getPersonRole());
			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);

			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		} else if ("m".equals(category[i])) {
		    if (fromTask.getM() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());

			toTask.setPersonRole(fromTask.getPersonRole());
			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);
			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		} else if ("p".equals(category[i])) {
		    if (fromTask.getP() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());

			toTask.setPersonRole(fromTask.getPersonRole());
			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);
			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		} else if ("buy".equals(category[i])) {
		    if (fromTask.getBuy() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());

			toTask.setPersonRole(fromTask.getPersonRole());
			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);

			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		} else if ("system".equals(category[i])) {
		    if (fromTask.getSystem() != 0) {
			ExtendScheduleData taskScheduleData = new ExtendScheduleData();
			taskScheduleData.setDuration(data.getDuration());
			// taskScheduleData.setStdWork(data.getStdWork());
			taskScheduleData.setPlanStartDate(data.getPlanStartDate());
			taskScheduleData.setPlanEndDate(data.getPlanEndDate());
			// taskScheduleData.setExecStartDate(data.getPlanStartDate());

			taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
			// Kogger.debug(getClass(),
			// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
			// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
			// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

			E3PSTask toTask = E3PSTask.newE3PSTask();
			toTask.setTaskName(fromTask.getTaskName());
			toTask.setTaskNameEn(fromTask.getTaskNameEn());
			toTask.setTaskSeq(fromTask.getTaskSeq());
			toTask.setTaskDesc(fromTask.getTaskDesc());
			toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
			// 변경 항목 추가
			toTask.setDrValue(fromTask.getDrValue());
			toTask.setDrValueCondition(fromTask.getDrValueCondition());
			toTask.setScheduleType(fromTask.getScheduleType());
			toTask.setPriorityControl(fromTask.getPriorityControl());
			toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
			boolean flag = false;
			for (int index = 0; index < category.length; index++) {
			    if ("common".equals(category[index])) {
				if (fromTask.getCommon() == 2) {
				    flag = true;
				}
			    }
			    if ("mdraw".equals(category[index])) {
				if (fromTask.getMdraw() == 2) {
				    flag = true;
				}
			    }
			    if ("sw".equals(category[index])) {
				if (fromTask.getSw() == 2) {
				    flag = true;
				}
			    }
			    if ("hw".equals(category[index])) {
				if (fromTask.getHw() == 2) {
				    flag = true;
				}
			    }
			    if ("m".equals(category[index])) {
				if (fromTask.getM() == 2) {
				    flag = true;
				}
			    }
			    if ("p".equals(category[index])) {
				if (fromTask.getP() == 2) {
				    flag = true;
				}
			    }
			    if ("buy".equals(category[index])) {
				if (fromTask.getBuy() == 2) {
				    flag = true;
				}
			    }
			    if ("system".equals(category[index])) {
				if (fromTask.getSystem() == 2) {
				    flag = true;
				}
			    }
			}
			if (flag) {
			    toTask.setOptionType(true);
			} else {
			    toTask.setOptionType(false);
			}
			toTask.setMileStone(fromTask.isMileStone());
			toTask.setTaskType(fromTask.getTaskType());
			toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());

			toTask.setPersonRole(fromTask.getPersonRole());
			// 불필요 로직으로 판단되어 주석처리함 2022.05.13
			// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
			// String toTeamType = this.getTeamTypeByProjectNew(project);
			// String fromTeamType = this.getTeamTypeByProject(fromProject);
			// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
			// toTask.setPersonRole(fromTask.getPersonRole());
			// } else {
			// toTask.setPersonRole("");
			// }

			toTask.setProject(project);
			// TempateTask 의 부서 정보 추가
			if (fromTask.getDepartment() != null) {
			    toTask.setDepartment(fromTask.getDepartment());
			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
			if (pnode.getCopyTask() != null) {
			    toTask.setParent(pnode.getCopyTask());
			}
			toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
			toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

			toTask.setDepartment(fromTask.getDepartment());
			toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

			if (fromTask.getDepartment() != null) {

			    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
			    if (master != null) {
				if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
				    ProjectUserHelper.manager.setMember(project, master);
				}
				TaskUserHelper.manager.setMaster(toTask, master);
			    }
			}
			node.setCopyTask(toTask);
			copyTaskUser(userMap, toTask, fromTask);
			ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
			break;
		    }
		}

	    }
	}
    }

    private void makeTaskFromTemplateNew(E3PSProject project, HashMap userMap, TemplateProjectTreeNode node, String[] category,
	    String productType) throws Exception {
	TemplateProjectTreeNodeData data = (TemplateProjectTreeNodeData) node.getUserObject();
	if (data.getData() instanceof TemplateTask) {
	    TemplateTask fromTask = (TemplateTask) data.getData();
	    boolean flag = false;

	    if ("newType".equals(productType)) {
		if (fromTask.getNewType() == 1) {
		    for (int i = 0; i < category.length; i++) {

			if ("common".equals(category[i])) {
			    if (fromTask.getCommon() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("mdraw".equals(category[i])) {
			    if (fromTask.getMdraw() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProject(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("hw".equals(category[i])) {
			    if (fromTask.getHw() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProject(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("sw".equals(category[i])) {
			    if (fromTask.getSw() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProject(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);
				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("m".equals(category[i])) {
			    if (fromTask.getM() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProject(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("p".equals(category[i])) {
			    if (fromTask.getP() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProject(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("buy".equals(category[i])) {
			    if (fromTask.getBuy() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProject(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("system".equals(category[i])) {
			    if (fromTask.getSystem() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProject(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			}

		    }
		}
	    } else if ("modifyType".equals(productType)) {
		if (fromTask.getModifyType() == 1) {
		    for (int i = 0; i < category.length; i++) {

			if ("common".equals(category[i])) {
			    if (fromTask.getCommon() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("mdraw".equals(category[i])) {
			    if (fromTask.getMdraw() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);
				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("hw".equals(category[i])) {
			    if (fromTask.getHw() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("sw".equals(category[i])) {
			    if (fromTask.getSw() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("m".equals(category[i])) {
			    if (fromTask.getM() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("p".equals(category[i])) {
			    if (fromTask.getP() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("buy".equals(category[i])) {
			    if (fromTask.getBuy() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			} else if ("system".equals(category[i])) {
			    if (fromTask.getSystem() != 0) {
				ExtendScheduleData taskScheduleData = new ExtendScheduleData();
				taskScheduleData.setDuration(data.getDuration());
				// taskScheduleData.setStdWork(data.getStdWork());
				taskScheduleData.setPlanStartDate(data.getPlanStartDate());
				taskScheduleData.setPlanEndDate(data.getPlanEndDate());
				// taskScheduleData.setExecStartDate(data.getPlanStartDate());

				taskScheduleData = (ExtendScheduleData) PersistenceHelper.manager.save(taskScheduleData);
				// Kogger.debug(getClass(),
				// "makeTaskFromTemplate11111111#####################WBS 태스크 attr 카피 비교  #######################");
				// Kogger.debug(getClass(), "#####setTaskName :"+fromTask.getTaskName() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.getTaskSeq() );
				// Kogger.debug(getClass(), "#####taskSeq :"+fromTask.isMileStone() );

				E3PSTask toTask = E3PSTask.newE3PSTask();
				toTask.setTaskName(fromTask.getTaskName());
				toTask.setTaskNameEn(fromTask.getTaskNameEn());
				toTask.setTaskSeq(fromTask.getTaskSeq());
				toTask.setTaskDesc(fromTask.getTaskDesc());
				toTask.setPlanWorkTime(fromTask.getPlanWorkTime());
				toTask.setTaskType(fromTask.getTaskType());
				// 변경 항목 추가
				toTask.setDrValue(fromTask.getDrValue());
				toTask.setDrValueCondition(fromTask.getDrValueCondition());
				toTask.setScheduleType(fromTask.getScheduleType());
				toTask.setPriorityControl(fromTask.getPriorityControl());
				toTask.setMainScheduleCode(fromTask.getMainScheduleCode());
				for (int index = 0; index < category.length; index++) {
				    if ("common".equals(category[index])) {
					if (fromTask.getCommon() == 2) {
					    flag = true;
					}
				    }
				    if ("mdraw".equals(category[index])) {
					if (fromTask.getMdraw() == 2) {
					    flag = true;
					}
				    }
				    if ("sw".equals(category[index])) {
					if (fromTask.getSw() == 2) {
					    flag = true;
					}
				    }
				    if ("hw".equals(category[index])) {
					if (fromTask.getHw() == 2) {
					    flag = true;
					}
				    }
				    if ("m".equals(category[index])) {
					if (fromTask.getM() == 2) {
					    flag = true;
					}
				    }
				    if ("p".equals(category[index])) {
					if (fromTask.getP() == 2) {
					    flag = true;
					}
				    }
				    if ("buy".equals(category[index])) {
					if (fromTask.getBuy() == 2) {
					    flag = true;
					}
				    }
				    if ("system".equals(category[index])) {
					if (fromTask.getSystem() == 2) {
					    flag = true;
					}
				    }
				}
				if (flag) {
				    toTask.setOptionType(true);
				} else {
				    toTask.setOptionType(false);
				}
				toTask.setMileStone(fromTask.isMileStone());
				toTask.setTaskType(fromTask.getTaskType());
				toTask.setTaskTypeCategory(fromTask.getTaskTypeCategory());
				toTask.setPersonRole(fromTask.getPersonRole());
				// 불필요 로직으로 판단되어 주석처리함 2022.05.13
				// TemplateProject fromProject = (TemplateProject) fromTask.getProject();
				// String toTeamType = this.getTeamTypeByProjectNew(project);
				// String fromTeamType = this.getTeamTypeByProject(fromProject);
				// if (project.getPjtType() == fromProject.getPjtType() && toTeamType.equals(fromTeamType)) {
				// toTask.setPersonRole(fromTask.getPersonRole());
				// } else {
				// toTask.setPersonRole("");
				// }

				toTask.setProject(project);

				// TempateTask 의 부서 정보 추가
				if (fromTask.getDepartment() != null) {
				    toTask.setDepartment(fromTask.getDepartment());
				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				TemplateProjectTreeNode pnode = (TemplateProjectTreeNode) node.getParent();
				if (pnode.getCopyTask() != null) {
				    toTask.setParent(pnode.getCopyTask());
				}
				toTask.setTaskSchedule(ObjectReference.newObjectReference(taskScheduleData));
				toTask.setTaskState(ProjectStateFlag.TASK_STATE_PROGRESS);

				toTask.setDepartment(fromTask.getDepartment());
				toTask = (E3PSTask) PersistenceHelper.manager.save(toTask);

				if (fromTask.getDepartment() != null) {

				    WTUser master = PeopleHelper.manager.getChiefUser(fromTask.getDepartment());
				    if (master != null) {
					if (!ProjectUserHelper.manager.isProjectUser(project, master)) {
					    ProjectUserHelper.manager.setMember(project, master);
					}
					TaskUserHelper.manager.setMaster(toTask, master);
				    }
				}
				node.setCopyTask(toTask);
				copyTaskUser(userMap, toTask, fromTask);
				ProjectOutputHelper.manager.copyTaskOutputInfo(userMap, new HashMap(), toTask, fromTask);
				break;
			    }
			}

		    }
		}
	    }

	}
    }

    public void updateLeafTaskByProject(String projectOid) throws Exception {

	E3PSProject project = (E3PSProject) CommonUtil.getObject(projectOid);
	QueryResult qr = PersistenceHelper.manager.navigate(project, "task", TemplateProjectTemplateTaskLink.class, true);
	if (qr != null && qr.size() > 0) {
	    while (qr.hasMoreElements()) {
		// Object[] objects = (Object[]) qr.nextElement();
		E3PSTask task = (E3PSTask) qr.nextElement();
		if (ProjectTaskHelper.manager.isLast(task)) {
		    task.setLeaf(true);
		} else {
		    task.setLeaf(false);
		}
		task = (E3PSTask) PersistenceHelper.manager.save(task);
	    }
	}
    }
}