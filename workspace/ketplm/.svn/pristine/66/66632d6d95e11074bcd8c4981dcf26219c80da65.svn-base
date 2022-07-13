package e3ps.project.beans;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTException;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.util.DateUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.WCUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ScheduleData;
import e3ps.project.TaskDependencyLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class ProjectScheduleHelper implements Serializable, RemoteAccess {

    public static final ProjectScheduleHelper manager = new ProjectScheduleHelper();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    public static final boolean ISCOMPLECTION = false; // 하위 진행율에 따라 상태 저장 //false 이면 하위 테스크에 상황에 따라

    private ProjectScheduleHelper() {

    }

    /**
     * 프로젝트 생성시 템플릿 프로젝트를 이용한 경우에..전체적인 스케줄을 잡기 위한 메소드
     * 
     * @param project
     */

    public void post_modify_Schedule(E3PSProject project) throws WTException {

	post_modify_Schedule(project, true);

    }

    public void post_modify_Schedule(E3PSProject project, boolean isDependencyCheck) throws WTException {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSProject.class, boolean.class };
	    Object args[] = new Object[] { project, new Boolean(isDependencyCheck) };
	    try {
		RemoteMethodServer.getDefault().invoke("post_modify_Schedule", null, this, argTypes, args);
		return;
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    ProjectTreeModel model = new ProjectTreeModel(project);
	    model.setTree();
	    model.updateSchedule(isDependencyCheck);

	    ProjectTreeNode root = model.getRoot();

	    System.out.println("=============( Project 삭제되는 현상에 대한 원인 검증용 Logging!! )=============================");
	    System.out.println("@@ post_modify_Schedule()");
	    System.out.println("@@ Before API Call : (E3PSProject) PersistenceHelper.manager.refresh(project) ");
		
	    project = (E3PSProject) PersistenceHelper.manager.refresh(project);
	    System.out.println("@@ API Execution Done : (E3PSProject) PersistenceHelper.manager.refresh(project) ");
	    
	    System.out.println("@@ Before API Call : settingProgress(root, project) ");
	    settingProgress(root, project);
	    System.out.println("@@ API Execution Done : settingProgress(root, project) ");
	    
	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
    }

    public void post_modify_Schedule(E3PSTask task) throws WTException {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSTask.class };
	    Object args[] = new Object[] { task };
	    try {
		RemoteMethodServer.getDefault().invoke("post_modify_Schedule", null, this, argTypes, args);
		return;
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	E3PSProject project = (E3PSProject) task.getProject();
	post_modify_Schedule(project);

    }

    /**
     * task completion change시 사
     * 
     * @param task
     * @throws WTException
     */
    public void post_modify_completion(E3PSTask task) throws WTException {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSTask.class };
	    Object args[] = new Object[] { task };
	    try {
		RemoteMethodServer.getDefault().invoke("post_modify_completion", null, this, argTypes, args);
		return;
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    ProjectTreeModel model = new ProjectTreeModel((E3PSProject) task.getProject());
	    model.setTree();
	    model.updateCompletion();

	    ProjectTreeNode root = model.getRoot();

	    settingProgress(root, (E3PSProject) task.getProject());

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}

    }

    public static void settingProgress(E3PSProject jelProject) throws Exception {
	ProjectTreeNode root = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot(jelProject, false);
	settingProgress(root, jelProject);
    }

    public static void settingProgress(ProjectTreeNode root, E3PSProject project) throws Exception {

	ExtendScheduleData schedule = (ExtendScheduleData) project.getPjtSchedule().getObject();

	// Timestamp pjtPlanStartDate = schedule.getPlanStartDate();
	// Timestamp pjtPlanEndDate = schedule.getPlanEndDate();

	Vector endTasks = new Vector();

	settingEnds(root, endTasks);

	double totalDuration = 0;
	double currentDuration = 0;

	Calendar currentDate = Calendar.getInstance();

	boolean isDelay = false;
	int delayState = 0;
	for (int i = 0; i < endTasks.size(); i++) {

	    ProjectTreeNode endNode = (ProjectTreeNode) endTasks.get(i);

	    ProjectTreeNodeData td = (ProjectTreeNodeData) endNode.getUserObject();

	    if (!(td.getData() instanceof E3PSTask)) {
		continue;
	    }
	    E3PSTask childTask = (E3PSTask) td.getData();

	    long time = td.getPlanStartDate().getTime() - currentDate.getTime().getTime();

	    long duration = (td.getPlanEndDate().getTime() + 0x5265c00L) - td.getPlanStartDate().getTime();

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

	    /*
	     * if(childTask.getTaskState() == ProjectStateFlag.TASK_STATE_COMPLETE){ continue; }
	     * 
	     * if(delay == 1) { continue; }
	     * 
	     * if ((E3PSTaskData.getDifferDateGap(childTask) < 0)){ if( (childTask.getTaskCompletion() == 0) ) { delay = 1; // red } else {
	     * if(delay == 0) { delay = 2;// yellow } } }
	     */

	}

	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);

	// Kogger.debug(getClass(), ">>>>>>>>>> currentDuration : " + currentDuration);
	// Kogger.debug(getClass(), ">>>>>>>>>> totalDuration : " + totalDuration);

	double preferComplection = (currentDuration / totalDuration) * 100d;
	if (preferComplection > 0) {
	    String cashPreferCompStr = nf.format((currentDuration / totalDuration) * 100d);
	    project.setPreferComp(cashPreferCompStr);
	} else {
	    project.setPreferComp("0");
	}

	if (ISCOMPLECTION) {
	    int currentComplection = project.getPjtCompletion();
	    double gap = (double) currentComplection - preferComplection;

	    int pjtState = getDelayType(project, gap);
	    project.setPjtState(pjtState);

	} else {
	    if (delayState == E3PSTaskData.STATE_BAR_NORMAL) {
		project.setPjtState(ProjectStateFlag.PROJECT_STATE_PROGRESS);
	    } else if (delayState == E3PSTaskData.STATE_BAR_DELAY) {
		project.setPjtState(ProjectStateFlag.PROJECT_STATE_DELAY);
	    } else if (delayState == E3PSTaskData.STATE_BAR_EXDELAY) {
		project.setPjtState(ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS);
	    }
	}

	PersistenceHelper.manager.save(project);
    }

    private static void settingEnds(DefaultProjectTreeNode root, Vector endTasks) {

	for (int i = 0; i < root.getChildCount(); i++) {
	    settingEnds((DefaultProjectTreeNode) root.getChildAt(i), endTasks);
	}

	if (root.getChildCount() == 0) {
	    endTasks.add(root);
	    root.setTempObj(new Integer(endTasks.size()), false);
	}

    }

    private static int getDelayType(E3PSProject project, double differDateGap) {

	Calendar currentDate = Calendar.getInstance();

	Timestamp pjtPlanStartDate = ((ExtendScheduleData) project.getPjtSchedule().getObject()).getPlanStartDate();
	Timestamp pjtPlanEndDate = ((ExtendScheduleData) project.getPjtSchedule().getObject()).getPlanEndDate();

	if (currentDate.getTime().before(pjtPlanStartDate)) {
	    return ProjectStateFlag.PROJECT_STATE_PROGRESS;
	}

	if (differDateGap >= 0) {
	    return ProjectStateFlag.PROJECT_STATE_PROGRESS;
	} else {
	    Calendar today = Calendar.getInstance();

	    String todayStr = DateUtil.getDateString(today.getTime(), "d");

	    String planEndDateStr = DateUtil.getTimeFormat(pjtPlanEndDate, "yyyy-MM-dd");

	    boolean todayBefore = todayStr.compareTo(planEndDateStr) > 0;

	    if (todayBefore) {
		return ProjectStateFlag.PROJECT_STATE_DELAY;
	    } else {
		return ProjectStateFlag.PROJECT_STATE_DELAY_PROGRESS;
	    }
	}

    }

    /**
     * 템플릿 프로젝트의 모든 일정(기간)에 대한 체크 메소드 task로 인한 모든 일정변화에 대해서 전체적인 템플릿 프로젝트의 일정을 체크(수정)한다.
     * 
     * @param task
     */
    public void post_modify_template_duration(TemplateTask task) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateTask.class };
	    Object args[] = new Object[] { task };
	    try {
		RemoteMethodServer.getDefault().invoke("post_modify_template_duration", null, this, argTypes, args);
		return;
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	}

	TemplateProject project = (TemplateProject) task.getProject();
	post_modify_template_duration(project);

    }

    public void post_modify_template_duration(TemplateProject project) {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class };
	    Object args[] = new Object[] { project };
	    try {
		RemoteMethodServer.getDefault().invoke("post_modify_template_duration", null, this, argTypes, args);
		return;
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    }
	}

	Transaction transaction = new Transaction();
	try {
	    transaction.start();

	    TemplateProjectModel model = new TemplateProjectModel(project);
	    model.setTree();
	    model.updateDuration();
	    transaction.commit();
	    transaction = null;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (transaction != null) {
		// Kogger.debug(getClass(), "rollback");
		transaction.rollback();
	    }
	}
    }

    /**
     * 두 날짜의 기간을 꺼내준다.
     * 
     * @param startStr
     *            시작일
     * @param endStr
     *            종료일
     * @return 두 날짜간은 기간을 리턴
     */
    public int getDuration(String startStr, String endStr) {
	java.util.Date startDate = null;
	java.util.Date endDate = null;
	try {
	    startDate = ProjectScheduleHelper.dateFormat.parse(startStr);
	    endDate = ProjectScheduleHelper.dateFormat.parse(endStr);
	} catch (ParseException e) {
	    Kogger.error(getClass(), e);
	    return -1;
	}

	long duration = endDate.getTime() - startDate.getTime();
	if (duration < 0) {
	    return -1;
	} else {
	    return (int) (duration / (1000 * 60 * 60 * 24)) + 1;
	}
    }

    public Object getRoot(TemplateTask task, boolean isDependency, boolean isReverse) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateTask.class, boolean.class, boolean.class };
	    Object args[] = new Object[] { task, Boolean.valueOf(isDependency), Boolean.valueOf(isReverse) };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getRoot", null, this, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    }
	    return obj;
	}

	if (task instanceof E3PSTask) {
	    ProjectTreeModel model = new ProjectTreeModel((E3PSTask) task, isReverse);
	    model.setTree();
	    if (isDependency) {
		model.setDependency();
	    }
	    return model.getRoot();

	} else {
	    /*
	     * TemplateProjectModel model = new TemplateProjectModel(project); model.setTree(); if(isDependency){ model.setDependency(); }
	     * 
	     * TemplateProjectTreeNode root = model.getRoot();
	     * 
	     * Kogger.debug(getClass(), "root = " + root); return root;
	     */
	    return null;
	}
    }

    public Object getRoot(TemplateTask task, boolean isDependency) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateTask.class, boolean.class };
	    Object args[] = new Object[] { task, Boolean.valueOf(isDependency) };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getRoot", null, this, argTypes, args);

	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    }
	    return obj;
	}
	return getRoot(task, isDependency, false);
    }

    public Object getRoot(TemplateProject project, boolean isDependency) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateProject.class, boolean.class };
	    Object args[] = new Object[] { project, Boolean.valueOf(isDependency) };
	    Object obj = null;
	    try {
		obj = RemoteMethodServer.getDefault().invoke("getRoot", null, this, argTypes, args);

	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		// throw new WTException(e);
	    }
	    return obj;
	}
	// Kogger.debug(getClass(), "className == " + project.getClass().getName());

	if (project instanceof E3PSProject) {
	    ProjectTreeModel model = new ProjectTreeModel((E3PSProject) project);
	    model.setTree();
	    if (isDependency) {
		model.setDependency();
	    }
	    return model.getRoot();

	} else {

	    TemplateProjectModel model = new TemplateProjectModel(project);
	    model.setTree();
	    if (isDependency) {
		model.setDependency();
	    }

	    TemplateProjectTreeNode root = model.getRoot();

	    // Kogger.debug(getClass(), "root = " + root);
	    return root;
	}
    }

    public static void excelCFTSetting(E3PSProject project, WritableSheet sheet) throws Exception {
	String teamName = "";
	if (project instanceof MoldProject) {
	    teamName = ProjectHelper.getProjectTeam(3);
	} else {
	    teamName = ProjectHelper.getProjectTeam(project.getPjtType());
	}

	TeamTemplate tempTeam = null;

	tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);

	Vector vecTeamStd = new Vector();

	if (tempTeam != null) {
	    vecTeamStd = tempTeam.getRoles();
	}

	Collections.sort(vecTeamStd, new RoleComparator());

	int row = 0;
	int columnIndex = 0;

	WritableCellFormat titleformat = JExcelUtil.getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	String titles[] = new String[] { "Role", "이름", "Role", "이름", "Role", "이름" };

	for (int i = 0; i < titles.length; i++) {
	    sheet.addCell(new Label(i, row, titles[i], titleformat));

	}

	sheet.setColumnView(0, 25);
	sheet.setColumnView(2, 25);
	sheet.setColumnView(4, 25);

	for (int i = 0; i < vecTeamStd.size(); i++) {

	    if (((i + 1) % 3) == 1) {
		row++;
		columnIndex = 0;
	    }

	    Role role = (Role) vecTeamStd.get(i);
	    String roleName_en = role.toString();
	    String displayName = role.getDisplay(Locale.KOREA);
	    QueryResult roleQr = ProjectUserHelper.manager.getProjectRoleMember(project, null, roleName_en);
	    String userName = "";
	    if (roleQr.hasMoreElements()) {
		ProjectMemberLink link = (ProjectMemberLink) roleQr.nextElement();
		PeopleData data = new PeopleData(link.getMember());
		userName = data.name;
	    }
	    sheet.addCell(new Label(columnIndex++, row, displayName));
	    sheet.addCell(new Label(columnIndex++, row, userName));

	}
    }

    public static void excelCFTDown(E3PSProject project, OutputStream out) throws Exception {
	WritableWorkbook workbook = Workbook.createWorkbook(out);
	WritableSheet sheet = workbook.createSheet("CFT ROLE", 1);
	excelCFTSetting(project, sheet);
	workbook.write();
	workbook.close();
    }

    public static void excelGantt(E3PSProject project, OutputStream out) throws Exception {

	ProjectTreeNode root = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot((E3PSProject) project, true);
	Vector endTasks = new Vector();
	settingEnds(root, endTasks);

	WritableWorkbook workbook = Workbook.createWorkbook(out);
	WritableSheet sheet = workbook.createSheet("일정", 1);
	int maxLevel = getMaxLevel(endTasks);

	settingProjectGanntTitle(sheet, maxLevel);
	settingProjectGanntExcel(root, sheet, maxLevel);

	workbook.write();
	workbook.close();
    }

    public static int getMaxLevel(Vector endTasks) {
	int maxLevel = -1;

	for (int i = 0; i < endTasks.size(); i++) {
	    DefaultProjectTreeNode endNode = (DefaultProjectTreeNode) endTasks.get(i);
	    TreeNode nodes[] = endNode.getPath();
	    if (maxLevel < nodes.length) {
		maxLevel = nodes.length;
	    }
	}
	return maxLevel;
    }

    public static void excelWBS(TemplateProject project, OutputStream out) throws Exception {
	TemplateProjectTreeNode root = (TemplateProjectTreeNode) ProjectScheduleHelper.manager.getRoot(project, true);
	Vector endTasks = new Vector();
	settingEnds(root, endTasks);

	int maxLevel = getMaxLevel(endTasks);
	maxLevel--;
	WritableWorkbook workbook = Workbook.createWorkbook(out);
	WritableSheet sheet = workbook.createSheet("일정", 1);

	settingWBSGanntTitle(sheet, maxLevel);

	for (int i = 0; i < root.getChildCount(); i++) {
	    TemplateProjectTreeNode childNode = (TemplateProjectTreeNode) root.getChildAt(i);
	    int index = i + 1;

	    childNode.setTempObj(String.valueOf(index), false);

	    settingWBSGanntExcel(childNode, sheet, maxLevel);
	}
	workbook.write();
	workbook.close();

    }

    private static void settingProjectGanntTitle(WritableSheet sheet, int maxLev) throws Exception {
	WritableCellFormat titleformat = JExcelUtil.getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	Vector titles = new Vector();
	sheet.setColumnView(titles.size(), 5);
	titles.add("NO");
	for (int i = 0; i < maxLev; i++) {
	    sheet.setColumnView(titles.size(), 17);
	    titles.add("Level" + (i + 1));
	}
	sheet.setColumnView(titles.size(), 7);
	titles.add("ID");
	sheet.setColumnView(titles.size(), 15);
	titles.add("Role");

	sheet.setColumnView(titles.size(), 10);
	titles.add("선행Task");
	sheet.setColumnView(titles.size(), 7);
	titles.add("기간");

	sheet.setColumnView(titles.size(), 17);
	titles.add("계획시작일");
	sheet.setColumnView(titles.size(), 17);
	titles.add("계획종료일");
	sheet.setColumnView(titles.size(), 17);
	titles.add("실제시작일");
	sheet.setColumnView(titles.size(), 17);
	titles.add("실제종료일");
	sheet.setColumnView(titles.size(), 7);
	titles.add("진행율");

	for (int i = 0; i < titles.size(); i++) {
	    sheet.addCell(new Label(i, 0, (String) titles.get(i), titleformat));
	}

    }

    private static void settingProjectGanntExcel(ProjectTreeNode node, WritableSheet sheet, int maxLev) throws Exception {

	WritableCellFormat levelformat = new WritableCellFormat();
	levelformat.setAlignment(jxl.format.Alignment.LEFT);
	ProjectTreeNodeData nodeData = (ProjectTreeNodeData) node.getUserObject();
	TreeNode nodes[] = node.getPath();
	int depth = nodes.length;
	Object obj = nodeData.getData();

	String name = "";
	String planStartDate = "";
	String planEndDate = "";
	String exStartDate = "";
	String exEndDate = "";
	String preTaskIds = "";
	String id = "";
	if (node.tempObj != null) {
	    id = node.tempObj.toString();
	}
	Vector pvector = node.getPreTasks();
	for (int i = 0; i < pvector.size(); i++) {
	    if (i > 0) {
		preTaskIds += ",";
	    }
	    ProjectTreeNode preTaskNode = (ProjectTreeNode) pvector.get(i);
	    preTaskIds += preTaskNode.tempObj;

	}

	int completion = 0;

	ExtendScheduleData sdata = null;

	String roleName = "";
	if (obj instanceof E3PSProject) {

	    E3PSProject project = (E3PSProject) nodeData.getData();
	    name = project.getPjtNo();
	    sdata = (ExtendScheduleData) project.getPjtSchedule().getObject();
	    completion = project.getPjtCompletion();

	} else if (obj instanceof E3PSTask) {

	    E3PSTask task = (E3PSTask) nodeData.getData();
	    name = task.getTaskName();
	    sdata = (ExtendScheduleData) task.getTaskSchedule().getObject();
	    completion = task.getTaskCompletion();
	    String personRole = task.getPersonRole();
	    if (personRole != null && personRole.length() > 0) {
		Role role = null;
		try {
		    role = Role.toRole(personRole);
		} catch (Exception ex) {

		}
		if (role != null) {
		    roleName = role.getDisplay(Locale.KOREA);
		} else {
		    roleName = personRole;

		}
		boolean isMold = false;
		TemplateProject project = task.getProject();
		if (project instanceof MoldTemplateProject || project instanceof MoldProject) {
		    isMold = true;
		}
		if ("PM".equals(roleName) && isMold) {
		    roleName = "금형개발";
		}
	    }

	}

	planStartDate = DateUtil.getDateString(sdata.getPlanStartDate(), "d");
	planEndDate = DateUtil.getDateString(sdata.getPlanEndDate(), "d");

	if (sdata.getExecStartDate() != null) {
	    exStartDate = DateUtil.getDateString(sdata.getExecStartDate(), "d");
	}

	if (sdata.getExecEndDate() != null) {
	    exEndDate = DateUtil.getDateString(sdata.getExecEndDate(), "d");
	}
	int row = sheet.getRows();
	sheet.addCell(new Label(0, row, String.valueOf(row)));
	sheet.addCell(new Label(depth, row, name, levelformat));
	int columnIndex = maxLev + 1;
	sheet.addCell(new Label(columnIndex++, row, id));
	sheet.addCell(new Label(columnIndex++, row, roleName));

	int duration = DateUtil.getDuration(sdata.getPlanStartDate(), sdata.getPlanEndDate()) + 1;

	sheet.addCell(new Label(columnIndex++, row, preTaskIds));
	sheet.addCell(new Label(columnIndex++, row, String.valueOf(duration)));

	sheet.addCell(new DateTime(columnIndex++, row, DateUtil.getADDHour(sdata.getPlanStartDate(), 9)));
	sheet.addCell(new DateTime(columnIndex++, row, DateUtil.getADDHour(sdata.getPlanEndDate(), 9)));
	if (sdata.getExecStartDate() != null) {
	    sheet.addCell(new DateTime(columnIndex++, row, DateUtil.getADDHour(sdata.getExecStartDate(), 9)));
	} else {
	    columnIndex++;
	}

	if (sdata.getExecEndDate() != null) {
	    sheet.addCell(new DateTime(columnIndex++, row, DateUtil.getADDHour(sdata.getExecEndDate(), 9)));
	} else {
	    columnIndex++;
	}

	sheet.addCell(new Number(columnIndex++, row, completion));

	for (int i = 0; i < node.getChildCount(); i++) {

	    ProjectTreeNode childNode = (ProjectTreeNode) node.getChildAt(i);
	    int index = i + 1;
	    String childId = "";
	    if (childNode.getLevel() == 1) {
		childId = String.valueOf(index);
	    } else {
		childId = id + "." + String.valueOf(index);
	    }
	    childNode.setTempObj(childId, false);
	    settingProjectGanntExcel(childNode, sheet, maxLev);
	}

    }

    private static void settingWBSGanntTitle(WritableSheet sheet, int maxLev) throws Exception {

	WritableCellFormat titleformat = JExcelUtil.getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);
	Vector titles = new Vector();
	sheet.setColumnView(titles.size(), 5);
	titles.add("NO");
	for (int i = 0; i < maxLev; i++) {
	    sheet.setColumnView(titles.size(), 17);
	    titles.add("Level" + (i + 1));
	}
	sheet.setColumnView(titles.size(), 7);
	titles.add("ID");
	sheet.setColumnView(titles.size(), 15);
	titles.add("Role");

	sheet.setColumnView(titles.size(), 10);
	titles.add("선행Task");
	sheet.setColumnView(titles.size(), 7);
	titles.add("기간");

	sheet.setColumnView(titles.size(), 7);
	titles.add("필수");

	sheet.setColumnView(titles.size(), 8);
	titles.add("MileStone");

	/*
	 * sheet.setColumnView(titles.size(), 17); titles.add("계획시작일"); sheet.setColumnView(titles.size(), 17); titles.add("계획종료일");
	 * sheet.setColumnView(titles.size(), 17); titles.add("실제시작일"); sheet.setColumnView(titles.size(), 17); titles.add("실제종료일");
	 * sheet.setColumnView(titles.size(), 7); titles.add("진행율");
	 */

	for (int i = 0; i < titles.size(); i++) {
	    sheet.addCell(new Label(i, 0, (String) titles.get(i), titleformat));
	}
    }

    private static void settingWBSGanntExcel(TemplateProjectTreeNode node, WritableSheet sheet, int maxLev) throws Exception {

	WritableCellFormat levelformat = new WritableCellFormat();
	levelformat.setAlignment(jxl.format.Alignment.LEFT);

	TemplateProjectTreeNodeData nodeData = (TemplateProjectTreeNodeData) node.getUserObject();
	TreeNode nodes[] = node.getPath();
	int depth = nodes.length - 1;
	Object obj = nodeData.getData();

	String name = "";

	String preTaskIds = "";
	String id = "";
	if (node.tempObj != null) {
	    id = node.tempObj.toString();
	}
	Vector pvector = node.getPreTasks();
	for (int i = 0; i < pvector.size(); i++) {
	    if (i > 0) {
		preTaskIds += ",";
	    }
	    TemplateProjectTreeNode preTaskNode = (TemplateProjectTreeNode) pvector.get(i);
	    preTaskIds += preTaskNode.tempObj;

	}

	// int completion = 0;

	ScheduleData sdata = null;

	String roleName = "";
	String option = "";
	String mileStone = "";
	if (obj instanceof TemplateProject) {

	    TemplateProject project = (TemplateProject) nodeData.getData();
	    name = project.getPjtNo();
	    sdata = (ScheduleData) project.getPjtSchedule().getObject();

	} else if (obj instanceof TemplateTask) {

	    TemplateTask task = (TemplateTask) nodeData.getData();
	    name = task.getTaskName();
	    sdata = (ScheduleData) task.getTaskSchedule().getObject();

	    String personRole = task.getPersonRole();
	    // Kogger.debug(getClass(), "personRole == " + personRole);
	    if (personRole != null && personRole.length() > 0) {
		Role role = null;
		try {
		    role = Role.toRole(personRole);
		} catch (Exception ex) {

		}
		if (role != null) {
		    // Kogger.debug(getClass(), "role === " + role);
		    roleName = role.getDisplay(Locale.KOREA);
		} else {
		    roleName = personRole;

		}

		boolean isMold = false;
		TemplateProject project = task.getProject();
		if (project instanceof MoldTemplateProject || project instanceof MoldProject) {
		    isMold = true;
		}
		if ("PM".equals(roleName) && isMold) {
		    roleName = "금형개발";
		}
	    }
	    if (task.isOptionType()) {
		option = "Y";
	    }
	    if (task.isMileStone()) {
		mileStone = "Y";
	    }

	}

	// planStartDate = DateUtil.getDateString(sdata.getPlanStartDate(), "d");
	// planEndDate = DateUtil.getDateString(sdata.getPlanEndDate(), "d");

	// if(sdata.getExecStartDate() != null){
	// exStartDate = DateUtil.getDateString(sdata.getExecStartDate(), "d");
	// }

	// if(sdata.getExecEndDate() != null){
	// exEndDate = DateUtil.getDateString(sdata.getExecEndDate(), "d");
	// }
	int row = sheet.getRows();
	sheet.addCell(new Label(0, row, String.valueOf(row)));
	sheet.addCell(new Label(depth, row, name, levelformat));
	int columnIndex = maxLev + 1;
	sheet.addCell(new Label(columnIndex++, row, id));
	sheet.addCell(new Label(columnIndex++, row, roleName));

	int duration = sdata.getDuration();

	sheet.addCell(new Label(columnIndex++, row, preTaskIds));
	sheet.addCell(new Label(columnIndex++, row, String.valueOf(duration)));
	sheet.addCell(new Label(columnIndex++, row, option));
	sheet.addCell(new Label(columnIndex++, row, mileStone));

	for (int i = 0; i < node.getChildCount(); i++) {

	    TemplateProjectTreeNode childNode = (TemplateProjectTreeNode) node.getChildAt(i);
	    int index = i + 1;
	    String childId = "";
	    if (childNode.getLevel() == 1) {
		childId = String.valueOf(index);
	    } else {
		childId = id + "." + String.valueOf(index);
	    }
	    childNode.setTempObj(childId, false);

	    settingWBSGanntExcel(childNode, sheet, maxLev);
	}

    }

    public static void excelDown(E3PSProject project, OutputStream out) throws Exception {

	ProjectTreeNode root = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot((E3PSProject) project, true);

	Vector endTasks = new Vector();
	settingEnds(root, endTasks);

	int row = 0;

	WritableWorkbook workbook = Workbook.createWorkbook(out);
	WritableSheet sheet = workbook.createSheet("일정", 1);

	int maxLevel = -1;
	for (int i = 0; i < endTasks.size(); i++) {

	    ProjectTreeNode endNode = (ProjectTreeNode) endTasks.get(i);
	    TreeNode nodes[] = endNode.getPath();
	    if (maxLevel < nodes.length) {
		maxLevel = nodes.length;
	    }
	}

	maxLevel += -1;

	for (int i = 0; i < maxLevel; i++) {
	    sheet.setColumnView(i, 20);
	}

	WritableCellFormat titleformat = JExcelUtil.getCellFormat(jxl.format.Alignment.CENTRE, jxl.format.Colour.LIGHT_GREEN);

	Vector titles = new Vector();
	titles.add("ID");
	titles.add("ROLE");
	titles.add("계획시작일");
	titles.add("계획종료일");
	titles.add("선행Task");

	for (int i = 0; i < maxLevel; i++) {
	    titles.add(i, "Level" + (i + 1));
	}

	for (int i = 0; i < titles.size(); i++) {
	    sheet.addCell(new Label(i, row, (String) titles.get(i), titleformat));
	}

	for (int i = 0; i < endTasks.size(); i++) {
	    row++;
	    int columnIndex = 0;

	    ProjectTreeNode endNode = (ProjectTreeNode) endTasks.get(i);
	    ProjectTreeNodeData nodeData = (ProjectTreeNodeData) endNode.getUserObject();
	    E3PSTask endTask = (E3PSTask) nodeData.getData();

	    ExtendScheduleData schedule = (ExtendScheduleData) endTask.getTaskSchedule().getObject();
	    Timestamp taskPlanStartDate = schedule.getPlanStartDate();
	    Timestamp taskPlanEndDate = schedule.getPlanEndDate();

	    Timestamp taskExecStartDate = schedule.getExecStartDate();
	    Timestamp taskExecEndDate = schedule.getExecEndDate();

	    TreeNode nodes[] = endNode.getPath();

	    for (int j = 1; j < nodes.length; j++) {
		ProjectTreeNode parentNode = (ProjectTreeNode) nodes[j];
		ProjectTreeNodeData td = (ProjectTreeNodeData) parentNode.getUserObject();

		/*
	         * if(!(td.getData() instanceof E3PSTaskData)){ continue; }
	         */

		E3PSTask task = (E3PSTask) td.getData();

		String name = task.getTaskName();
		sheet.addCell(new Label(columnIndex++, row, name));
	    }

	    columnIndex = maxLevel;

	    sheet.addCell(new Label(columnIndex++, row, String.valueOf(i + 1)));

	    String role = endTask.getPersonRole();

	    if (role == null || role.length() == 0) {

		role = "";

	    } else {

		Role cftRole = Role.toRole(role);
		role = cftRole.getDisplay(Locale.KOREA);

	    }

	    sheet.setColumnView(columnIndex, 25);
	    sheet.addCell(new Label(columnIndex++, row, role));
	    sheet.setColumnView(columnIndex, 15);
	    sheet.addCell(new DateTime(columnIndex++, row, DateUtil.getADDHour(taskPlanStartDate, 9)));
	    sheet.setColumnView(columnIndex, 15);
	    sheet.addCell(new DateTime(columnIndex++, row, DateUtil.getADDHour(taskPlanEndDate, 9)));

	    Vector tasks = endNode.getPreTasks();
	    String preStr = "";

	    // Kogger.debug(getClass(), "tasks.size() = " + tasks.size());

	    for (int k = 0; k < tasks.size(); k++) {
		ProjectTreeNode preNode = (ProjectTreeNode) tasks.get(k);
		// Kogger.debug(getClass(), "preNode.tempObj = " + preNode.tempObj);
		if (preNode.tempObj != null) {
		    Integer index = (Integer) preNode.tempObj;
		    if (preStr.length() > 0) {
			preStr += ",";
		    }
		    preStr += index.toString();
		}

	    }

	    sheet.addCell(new Label(columnIndex++, row, preStr));
	}
	WritableSheet sheet2 = workbook.createSheet("CFT ROLE", 2);
	excelCFTSetting(project, sheet2);

	workbook.write();
	workbook.close();
    }

    /**
     * 함수명 : checkSheet 수정 내용 : [PLM System 1차개선] 후행 Task 계획시작일은 선행 Task 계획시작일 이후로 유연하게 지정 가능하도록 변경
     * 
     * @param project
     * @param endTask
     * @param sheet
     * @param maxLev
     * @return
     * @throws Exception
     *             수정자 : BoLee 수정일자 : 2013. 8. 22.
     */
    public static String checkSheet(E3PSProject project, Vector endTask, Sheet sheet, int maxLev) throws Exception {

	Hashtable roleHash = getRoleHash(project);
	roleHash.put("PM", "PM");
	roleHash.put("금형개발", "PM");
	int rows = sheet.getRows();

	int taskSize = endTask.size();

	if ((rows - 1) != taskSize) {
	    return "엑셀파일 테스크의 수가 맞지 않습니다.";
	}

	Hashtable endPlanH = new Hashtable();
	for (int i = 1; i < rows; i++) {
	    Cell[] cell = sheet.getRow(i);

	    ProjectTreeNode endNode = (ProjectTreeNode) endTask.get(i - 1);
	    Vector info = getNodeCellData(endNode, maxLev);
	    int totalSize = info.size() + 4;
	    if (totalSize > cell.length) {
		return "엑셀파일 " + (i + 1) + " 라인의 컬럼 수가 맞지 않습니다.";
	    }
	    if (!checkTaskName(cell, info)) {
		return "엑셀파일 " + (i + 1) + " 라인의 태스크명이 맞지 않습니다.";
	    }
	    int size = info.size();

	    String id = e3ps.common.util.JExcelUtil.getContent(cell, size).trim();

	    if (!id.equals(String.valueOf(i))) {
		return "엑셀파일 " + (i + 1) + " 라인의  ID가 맞지 않습니다.";
	    }
	    int columnIndex = size + 1;

	    String roleName = e3ps.common.util.JExcelUtil.getContent(cell, columnIndex++).trim();
	    /*
	     * if (roleName.length() > 0) { if (!roleHash.containsKey(roleName)) { // Kogger.debug(getClass(), "roleName = " + roleName); return
	     * "엑셀파일 " + (i + 1) + " 라인의  Role 명이 적합하지 않습니다."; } }
	     */

	    Timestamp startStamp = e3ps.common.util.JExcelUtil.getTimestamp(cell, columnIndex++);
	    Timestamp endStamp = e3ps.common.util.JExcelUtil.getTimestamp(cell, columnIndex++);

	    if (startStamp == null) {
		return "엑셀파일 " + (i + 1) + " 라인의  계획시작일이  적합하지 않습니다.";
	    }

	    if (endStamp == null) {
		return "엑셀파일 " + (i + 1) + " 라인의  계획종료일이  적합하지 않습니다.";
	    }

	    if (startStamp.after(endStamp)) {
		return "엑셀파일 " + (i + 1) + " 라인의  계획종료일이  계획시작일 이전입니다.";
	    }

	    ProjectTreeNodeData td = (ProjectTreeNodeData) endNode.getUserObject();
	    td.setPlanStartDate(startStamp);
	    td.setPlanEndDate(endStamp);

	    endPlanH.put(id, endNode);
	    endNode.removeAllPreTask();

	    String preTasks = e3ps.common.util.JExcelUtil.getContent(cell, columnIndex++).trim();
	    StringTokenizer st = new StringTokenizer(preTasks, ",");

	    if (preTasks.length() > 0) {
		while (st.hasMoreTokens()) {
		    String preId = st.nextToken();
		    try {
			int pId = Integer.parseInt(preId);
			if (pId >= Integer.parseInt(id)) {
			    // Kogger.debug(getClass(), "pId = " + pId);
			    return "엑셀파일 " + (i + 1) + " 라인의 선행 태스크 ID가 적합하지 않습니다.";
			}
			ProjectTreeNode preNode = (ProjectTreeNode) endPlanH.get(preId);
			ProjectTreeNodeData pretd = (ProjectTreeNodeData) preNode.getUserObject();

			// [START] [PLM System 1차개선] 후행 Task 계획시작일은 선행 Task 계획시작일 이후로 유연하게 지정 가능하도록 변경, 2013-08-22, BoLee
			Timestamp time = (Timestamp) pretd.getPlanStartDate();

			if (startStamp.before(time)) {

			    return "엑셀파일 " + (i + 1) + " 라인의 실제 시작일은 선행 Task 계획시작일(" + DateUtil.getDateString(time, "d")
				    + ") 이후로 입력되어야 합니다.";
			}
			// [END] [PLM System 1차개선] 후행 Task 계획시작일은 선행 Task 계획시작일 이후로 유연하게 지정 가능하도록 변경, 2013-08-22, BoLee

			endNode.addPreTask(preNode);
		    } catch (Exception ex) {
			Kogger.error(ProjectScheduleHelper.class, ex);
			return "엑셀파일 " + (i + 1) + " 라인의 선행 태스크 ID가 적합하지 않습니다.";
		    }
		}
	    }
	}

	return "";

    }

    public static boolean checkTaskName(Cell[] cell, Vector info) {
	boolean result = true;
	if (cell.length < info.size()) {
	    result = false;
	}

	for (int i = 0; i < info.size(); i++) {
	    String name = e3ps.common.util.JExcelUtil.getContent(cell, i).trim();
	    String infoName = (String) info.get(i);
	    infoName = infoName.trim();
	    if (!name.equals(infoName)) {
		// Kogger.debug(getClass(), "name = " + name + " infoName = " + infoName);
		result = false;
		break;
	    }
	}
	return result;
    }

    public static Vector getNodeCellData(ProjectTreeNode endNode, int maxLev) {
	Vector vector = new Vector();
	TreeNode nodes[] = endNode.getPath();
	for (int i = 1; i < nodes.length; i++) {
	    ProjectTreeNode parentNode = (ProjectTreeNode) nodes[i];
	    ProjectTreeNodeData td = (ProjectTreeNodeData) parentNode.getUserObject();
	    E3PSTask task = (E3PSTask) td.getData();
	    String name = task.getTaskName();
	    vector.add(name);
	}

	if (nodes.length < maxLev) {
	    for (int i = 0; i < (maxLev - nodes.length); i++) {
		vector.add("");
	    }
	}

	return vector;
    }

    public static Hashtable getRoleHash(E3PSProject project) throws Exception {
	String teamName = ProjectHelper.getProjectTeam(project.getPjtType());

	TeamTemplate tempTeam = null;

	tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);

	Vector vecTeamStd = new Vector();

	if (tempTeam != null) {
	    vecTeamStd = tempTeam.getRoles();
	}

	// Collections.sort(vecTeamStd, new RoleComparator());

	Hashtable roleHash = new Hashtable();
	for (int i = 0; i < vecTeamStd.size(); i++) {

	    Role role = (Role) vecTeamStd.get(i);
	    String roleName_en = role.toString();
	    String displayName = role.getDisplay(Locale.KOREA);
	    roleHash.put(displayName, role.toString());

	}
	return roleHash;
    }

    public static Hashtable getRoleHashforEn(E3PSProject project) throws Exception {
	String teamName = ProjectHelper.getProjectTeam(project.getPjtType());

	TeamTemplate tempTeam = null;

	tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);

	Vector vecTeamStd = new Vector();

	if (tempTeam != null) {
	    vecTeamStd = tempTeam.getRoles();
	}

	// Collections.sort(vecTeamStd, new RoleComparator());

	Hashtable roleHash = new Hashtable();
	for (int i = 0; i < vecTeamStd.size(); i++) {

	    Role role = (Role) vecTeamStd.get(i);
	    String roleName_en = role.toString();
	    String displayName = role.getDisplay(Locale.KOREA);
	    roleHash.put(role.toString(), displayName);

	}
	return roleHash;
    }

    /**
     * 함수명 : uploadProjectSchedule 수정 내용 : [PLM System 1차개선] 후행 Task 계획시작일은 선행 Task 계획시작일 이후로 유연하게 지정 가능하도록 변경
     * 
     * @param project
     * @param file
     * @return
     * @throws Exception
     *             수정자 : BoLee 수정일자 : 2013. 8. 22.
     */
    public static String uploadProjectSchedule(E3PSProject project, File file) throws Exception {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSProject.class, File.class };
	    Object args[] = new Object[] { project, file };
	    try {

		Object obj = RemoteMethodServer.getDefault().invoke("uploadProjectSchedule", ProjectScheduleHelper.class.getName(), null,
		        argTypes, args);
		return (String) obj;

	    } catch (RemoteException e) {
		Kogger.error(ProjectScheduleHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ProjectScheduleHelper.class, e);
		throw new WTException(e);
	    }
	}

	Transaction transaction = new Transaction();
	String error = "";
	try {
	    transaction.start();
	    ProjectTreeNode root = (ProjectTreeNode) ProjectScheduleHelper.manager.getRoot((E3PSProject) project, true);
	    Vector endTasks = new Vector();
	    settingEnds(root, endTasks);

	    int maxLevel = -1;

	    for (int i = 0; i < endTasks.size(); i++) {

		ProjectTreeNode endNode = (ProjectTreeNode) endTasks.get(i);
		TreeNode nodes[] = endNode.getPath();
		if (maxLevel < nodes.length) {
		    maxLevel = nodes.length;
		}
	    }
	    // Kogger.debug(getClass(), "file = " + file.getAbsolutePath());
	    file = E3PSDRMHelper.upload(file, file.getName());

	    Workbook wb = Workbook.getWorkbook(file);
	    Sheet[] sheets = wb.getSheets();

	    error = checkSheet(project, endTasks, sheets[0], maxLevel);

	    if (error.length() == 0) {
		for (int i = 0; i < endTasks.size(); i++) {
		    ProjectTreeNode node = (ProjectTreeNode) endTasks.get(i);
		    ProjectTreeNodeData td = (ProjectTreeNodeData) node.getUserObject();
		    td.persist();
		    E3PSTask task = (E3PSTask) td.getData();

		    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);

		    while (dependList.hasMoreElements()) {
			TaskDependencyLink link = (TaskDependencyLink) dependList.nextElement();
			PersistenceHelper.manager.delete(link);
		    }

		    for (int j = 0; j < node.getPreTasks().size(); j++) {
			ProjectTreeNode preNode = (ProjectTreeNode) node.getPreTasks().get(j);
			ProjectTreeNodeData pretd = (ProjectTreeNodeData) preNode.getUserObject();
			E3PSTask dependTask = (E3PSTask) pretd.getData();
			TaskDependencyLink link = TaskDependencyLink.newTaskDependencyLink(task, dependTask);
			PersistenceHelper.manager.save(link);
		    }
		}
	    } else {
		Kogger.debug(ProjectScheduleHelper.class, "error = " + error);
	    }
	    transaction.commit();

	    // [PLM System 1차개선] 선후행 관계 체크하지 않도록 parameter(false) 추가, 2013-08-22, BoLee
	    ProjectScheduleHelper.manager.post_modify_Schedule(project, false);

	    transaction = null;
	} catch (Exception e) {
	    Kogger.error(ProjectScheduleHelper.class, e);
	    throw new WTException(e);
	} finally {
	    if (transaction != null) {
		transaction.rollback();
	    }
	}
	return error;
    }

    public static void test() throws Exception {
	QuerySpec spec = new QuerySpec(E3PSProject.class);
	spec.addClassList(ExtendScheduleData.class, false);

	ClassAttribute ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id");
	ClassAttribute ca2 = new ClassAttribute(ExtendScheduleData.class, wt.util.WTAttributeNameIfc.ID_NAME);
	SearchCondition sc = new SearchCondition(ca1, "=", ca2);
	sc.setFromIndicies(new int[] { 0, 1 }, 0);
	sc.setOuterJoin(0);
	spec.appendWhere(sc, new int[] { 0, 1 });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();

	sc = new SearchCondition(ExtendScheduleData.class, ExtendScheduleData.PLAN_END_DATE, true);
	spec.appendWhere(sc, new int[] { 1 });

	QueryResult rs = PersistenceHelper.manager.find(spec);

	while (rs.hasMoreElements()) {
	    Object[] o = (Object[]) rs.nextElement();
	    E3PSProject project = (E3PSProject) o[0];
	    Kogger.debug(ProjectScheduleHelper.class, "kkkkk = " + project.getPjtNo());
	    ExtendScheduleData data = (ExtendScheduleData) project.getPjtSchedule().getObject();
	    if (data.getPlanEndDate() == null) {
		data.setPlanEndDate(data.getPlanStartDate());
		PersistenceHelper.manager.save(data);
	    }

	    project = (E3PSProject) PersistenceHelper.manager.refresh(project);
	    ProjectScheduleHelper.manager.post_modify_Schedule(project);
	}
    }

    public static void main(String args[]) throws Exception {

	// FileOutputStream out = new FileOutputStream("c://kkkk.xls");

	// excelGantt(project, out);

    }

}
