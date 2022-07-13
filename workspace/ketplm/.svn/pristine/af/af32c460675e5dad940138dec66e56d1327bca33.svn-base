package e3ps.project.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.mpxj.ConstraintType;
import net.sf.mpxj.Day;
import net.sf.mpxj.Duration;
import net.sf.mpxj.ProjectCalendar;
import net.sf.mpxj.ProjectCalendarHours;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ProjectHeader;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.TaskField;
import net.sf.mpxj.TimeUnit;
import net.sf.mpxj.mpx.MPXWriter;
import net.sf.mpxj.mspdi.MSPDIWriter;
import net.sf.mpxj.utility.NumberUtility;
import net.sf.mpxj.writer.ProjectWriter;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.project.Role;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.team.TeamHelper;
import wt.team.TeamTemplate;
import wt.util.WTException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DownloadUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateTask;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectTaskScheduleHelper;
import e3ps.project.beans.ProjectUserData;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.beans.RoleComparator;
import e3ps.project.beans.TaskUserHelper;
import e3ps.project.dao.ProjectScheduleDao;
import e3ps.project.outputtype.ModelPlan;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.shared.log.Kogger;

@SuppressWarnings("serial")
public class ProjectScheduleDownloadServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String cmd = ParamUtil.getStrParameter(req.getParameter("cmd"));

	if (cmd.equalsIgnoreCase("mpp_download")) {
	    mppDownload(req, res);
	} else if (cmd.equalsIgnoreCase("mpp_upload")) {
	    mppUpload(req, res);
	}
    }

    private void mppUpload(HttpServletRequest req, HttpServletResponse res) {

	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;
	ProjectScheduleDao projectScheduleDao = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    paramMap.put("locale", messageService.getLocale());

	    String oid = ParamUtil.getStrParameter(paramMap.getString("oid"));
	    Object obj = CommonUtil.getObject(oid); // OID로부터 Object return
	    E3PSProject project = null;
	    if (obj instanceof E3PSProject) {
		project = (E3PSProject) obj;
	    } else if (obj instanceof E3PSTask) {
		E3PSTask e3psTask = (E3PSTask) obj;
		project = (E3PSProject) e3psTask.getProject();
	    }

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    projectScheduleDao = new ProjectScheduleDao(conn);

	    // Project 일정 조회를 위한 Parameter 설정
	    paramMap.put("oidLong", CommonUtil.getOIDLongValue(oid)); // project oid(ida2a2)
	    paramMap.put("project", project); // project 객체

	    Kogger.debug(getClass(), paramMap);

	    // Project Task 일정 조회
	    ArrayList<KETParamMapUtil> projectTaskScheduleList = projectScheduleDao.getProjectTaskSchedule(paramMap);
	    paramMap.put("projectTaskScheduleList", projectTaskScheduleList);

	    ProjectTaskScheduleHelper.uploadTaskSchedule(paramMap);

	    // listProjectHeader(mpx);

	    // listResources(mpx);
	    //
	    // listTasks(mpx);
	    //
	    // listAssignments(mpx);
	    //
	    // listAssignmentsByTask(mpx);
	    //
	    // listAssignmentsByResource(mpx);
	    //
	    // listHierarchy(mpx);
	    //
	    // listTaskNotes(mpx);
	    //
	    // listResourceNotes(mpx);
	    //
	    // listRelationships(mpx);
	    //
	    // listSlack(mpx);
	    //
	    // listCalendars(mpx);
	    gotoResult(req, res, "/servlet/e3ps/ProjectScheduleServlet?oid=" + oid + "&cmd=search");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    PlmDBUtil.close(conn);
	}
    }

    /**
     * This method lists all resources defined in the file.
     * 
     * @param file
     *            MPX file
     */
    private static void listResources(ProjectFile file) {
	for (Resource resource : file.getAllResources()) {
	    Kogger.debug(ProjectScheduleDownloadServlet.class, "Resource: " + resource.getName() + " (Unique ID=" + resource.getUniqueID() + ") Start="
		    + resource.getStart() + " Finish=" + resource.getFinish());
	}
	Kogger.debug(ProjectScheduleDownloadServlet.class, "");
    }

    /**
     * This method lists all resource assignments defined in the file.
     * 
     * @param file
     *            MPX file
     */
    private static void listAssignments(ProjectFile file) {
	Task task;
	Resource resource;
	String taskName;
	String resourceName;

	for (ResourceAssignment assignment : file.getAllResourceAssignments()) {
	    task = assignment.getTask();
	    if (task == null) {
		taskName = "(null task)";
	    } else {
		taskName = task.getName();
	    }

	    resource = assignment.getResource();
	    if (resource == null) {
		resourceName = "(null resource)";
	    } else {
		resourceName = resource.getName();
	    }

	    Kogger.debug(ProjectScheduleDownloadServlet.class, "Assignment: Task=" + taskName + " Resource=" + resourceName);
	}

	Kogger.debug(ProjectScheduleDownloadServlet.class, "");
    }

    /**
     * This method displays the resource assignments for each task. This time rather than just iterating through the list of all assignments
     * in the file, we extract the assignments on a task-by-task basis.
     * 
     * @param file
     *            MPX file
     */
    private static void listAssignmentsByTask(ProjectFile file) {
	for (Task task : file.getAllTasks()) {
	    Kogger.debug(ProjectScheduleDownloadServlet.class, "Assignments for task " + task.getName() + ":");

	    for (ResourceAssignment assignment : task.getResourceAssignments()) {
		Resource resource = assignment.getResource();
		String resourceName;

		if (resource == null) {
		    resourceName = "(null resource)";
		} else {
		    resourceName = resource.getName();
		}

		Kogger.debug(ProjectScheduleDownloadServlet.class, "   " + resourceName);
	    }
	}

	Kogger.debug(ProjectScheduleDownloadServlet.class, "");
    }

    /**
     * This method displays the resource assignments for each resource. This time rather than just iterating through the list of all
     * assignments in the file, we extract the assignments on a resource-by-resource basis.
     * 
     * @param file
     *            MPX file
     */
    private static void listAssignmentsByResource(ProjectFile file) {
	for (Resource resource : file.getAllResources()) {
	    Kogger.debug(ProjectScheduleDownloadServlet.class, "Assignments for resource " + resource.getName() + ":");

	    for (ResourceAssignment assignment : resource.getTaskAssignments()) {
		Task task = assignment.getTask();
		Kogger.debug(ProjectScheduleDownloadServlet.class, "   " + task.getName());
	    }
	}

	Kogger.debug(ProjectScheduleDownloadServlet.class, "");
    }

    private void mppDownload(HttpServletRequest req, HttpServletResponse res) {
	try {

	    String oid = "";
	    String projectName = "";
	    String partName = "";
	    String taskId = "";
	    String taskName = "";
	    String planStartDate = "";
	    String planEndDate = "";
	    String taskCompletion = "";
	    String taskPreferComp = "";
	    String planManHour = "";
	    String personRole = "";
	    String taskMaster = "";
	    String taskMasterId = "";
	    String taskMember = "";
	    String taskMemberId = "";
	    String milestoneType = "";
	    String optionType = "";
	    String taskType = "";
	    String drValue = "";
	    String parentHierarchy = "";
	    String parentTaskOid = "";
	    String isLeaf = "";
	    String taskOid = "";
	    String taskAncestors = "";
	    String oemEndDate = "";
	    String oemName = "";
	    String personRoleEnumKey = "";
	    String personRoleEnum = "";
	    String milestoneTypeEnumKey = "";
	    String milestoneTypeEnum = "";
	    String optionTypeEnumKey = "";
	    String optionTypeEnum = "";
	    String taskTypeEnumKey = "";
	    String taskTypeEnum = "";
	    String teamName = "";
	    String roleKey = "";
	    String roleName = "";
	    String loingUserId = "";
	    int planDuration = 0;
	    int taskLevel = 0;
	    int preTaskLevel = 0;
	    int taskSeq = 0;
	    int idx = 0;
	    long oidLong = 0;
	    long carTypeOidLong = 0;
	    long modelPlanOidLong = 0;
	    Object obj = null;
	    KETParamMapUtil projectInfoMap = KETParamMapUtil.getMap();
	    KETParamMapUtil projectRootScheduleMap = null;
	    ArrayList<KETParamMapUtil> projectTaskScheduleList = null;
	    Connection conn = null;
	    E3PSProject project = null;
	    E3PSTask e3psTask = null;
	    E3PSTaskData e3psTaskData = null;
	    TemplateTask tempTask = null;
	    ProjectScheduleDao projectScheduleDao = null;
	    OEMProjectType carType = null;
	    Object resultObj[] = null;
	    SearchCondition sCond = null;
	    QuerySpec qSpec = null;
	    QueryResult qResult = null;
	    QueryResult masterListResult = null;
	    QueryResult memberListResult = null;
	    ModelPlan modelPlan = null;
	    OEMPlan oemPlan = null;
	    TaskMemberLink templateTaskLink = null;
	    ProjectMemberLink templateProjectLink = null;
	    WTUser projectUser = null;
	    Vector<Role> vecTeamStd = new Vector<Role>();
	    TeamTemplate tempTeam = null;
	    Role role = null;
	    Locale locale = null;
	    KETMessageService messageService = KETMessageService.getMessageService(req);
	    KETParamMapUtil enumMap = null;
	    ArrayList<KETParamMapUtil> enumList = new ArrayList<KETParamMapUtil>();
	    Map<String, Object> parameter = new HashMap<String, Object>();
	    List<Map<String, Object>> taskTypeNumCode = new ArrayList<Map<String, Object>>();

	    String mppFileName = null;
	    try {

		// 로그인 사용자 정보
		try {
		    loingUserId = ((WTUser) SessionHelper.manager.getPrincipal()).getName();
		} catch (WTException e) {
		    Kogger.error(getClass(), e);
		}

		// [START] 1. Project 정보 조회

		oid = ParamUtil.getStrParameter(req.getParameter("oid"));
		oidLong = CommonUtil.getOIDLongValue(oid); // OID로부터 IDA2A2 return

		obj = CommonUtil.getObject(oid); // OID로부터 Object return

		if (obj instanceof E3PSProject) {

		    project = (E3PSProject) obj;
		} else if (obj instanceof E3PSTask) {

		    e3psTask = (E3PSTask) obj;
		    project = (E3PSProject) e3psTask.getProject();
		}

		if (project != null) {

		    if (project instanceof MoldProject) { // 금형 Project Name - PJT_NO (PART_NAME)

			partName = StringUtil.checkNull(((MoldProject) project).getMoldInfo().getPartName());

			projectName = project.getPjtNo() + "(" + partName + ")";
		    } else { // 검토, 제품 Project Name - PJT_NO (PJT_NAME)
			projectName = project.getPjtNo() + "(" + project.getPjtName() + ")";
		    }
		}

		// [END] 1. Project 정보 조회

		try {

		    // [START] 3. Body Data 구성 - Project 일정

		    // 3-1. 커넥션 생성

		    conn = PlmDBUtil.getConnection();

		    projectScheduleDao = new ProjectScheduleDao(conn);

		    // 3-2. Project 일정 조회를 위한 Parameter 설정

		    projectInfoMap.put("oidLong", oidLong); // project oid(ida2a2)
		    projectInfoMap.put("project", project); // project 객체

		    // 3-3. Project 일정(Root) 조회
		    projectRootScheduleMap = projectScheduleDao.getProjectRootSchedule(projectInfoMap);

		    // 3-4. Project 일정(Root) Grid Data 구성

		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		    ProjectFile file = new ProjectFile();

		    //
		    // Configure the file to automatically generate identifiers for tasks.
		    //
		    file.setAutoTaskID(true);
		    file.setAutoTaskUniqueID(true);

		    //
		    // Configure the file to automatically generate identifiers for resources.
		    //
		    file.setAutoResourceID(true);
		    file.setAutoResourceUniqueID(true);

		    //
		    // Configure the file to automatically generate outline levels
		    // and outline numbers.
		    //
		    file.setAutoOutlineLevel(true);
		    file.setAutoOutlineNumber(true);

		    //
		    // Configure the file to automatically generate WBS labels
		    //
		    file.setAutoWBS(true);

		    //
		    // Configure the file to automatically generate identifiers for calendars
		    // (not strictly necessary here, but required if generating MSPDI files)
		    //
		    file.setAutoCalendarUniqueID(true);

		    //
		    // Add a default calendar called "Standard"
		    //
		    ProjectCalendar pc = file.addDefaultBaseCalendar();
		    pc.setName("KET_Calender");
		    pc.setWorkingDay(Day.SUNDAY, true);
		    pc.setWorkingDay(Day.MONDAY, true);
		    pc.setWorkingDay(Day.TUESDAY, true);
		    pc.setWorkingDay(Day.WEDNESDAY, true);
		    pc.setWorkingDay(Day.THURSDAY, true);
		    pc.setWorkingDay(Day.FRIDAY, true);
		    pc.setWorkingDay(Day.SATURDAY, true);

		    ProjectCalendarHours h1 = pc.addCalendarHours(Day.SUNDAY);
		    h1.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
		    h1.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);

		    ProjectCalendarHours h2 = pc.addCalendarHours(Day.MONDAY);
		    h2.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
		    h2.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);

		    ProjectCalendarHours h3 = pc.addCalendarHours(Day.TUESDAY);
		    h3.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
		    h3.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);

		    ProjectCalendarHours h4 = pc.addCalendarHours(Day.WEDNESDAY);
		    h4.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
		    h4.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);

		    ProjectCalendarHours h5 = pc.addCalendarHours(Day.THURSDAY);
		    h5.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
		    h5.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);

		    ProjectCalendarHours h6 = pc.addCalendarHours(Day.FRIDAY);
		    h6.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
		    h6.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);

		    ProjectCalendarHours h7 = pc.addCalendarHours(Day.SATURDAY);
		    h7.addRange(ProjectCalendar.DEFAULT_WORKING_MORNING);
		    h7.addRange(ProjectCalendar.DEFAULT_WORKING_AFTERNOON);

		    ProjectHeader header = file.getProjectHeader();
		    header.setCalendarName(pc.getName());

		    if (projectRootScheduleMap != null) {

			planStartDate = projectRootScheduleMap.getString("planStartDate");
			planEndDate = projectRootScheduleMap.getString("planEndDate");
			planDuration = projectRootScheduleMap.getInt("planDuration");
			taskOid = projectRootScheduleMap.getString("taskOid");
			taskPreferComp = projectRootScheduleMap.getString("preferComp");

			// Project Header
			header.setStartDate(df.parse(planStartDate));
		    }

		    Task projectTask = file.addTask();
		    projectTask.setName(projectName);
		    projectTask.setStart(df.parse(planStartDate));
		    projectTask.setDuration(Duration.getInstance(planDuration, TimeUnit.DAYS));

		    // 3-5. Project Task 일정 조회
		    projectTaskScheduleList = projectScheduleDao.getProjectTaskSchedule(projectInfoMap);

		    // 3-6. Project Task 일정 조회 결과로 Grid Data 구성

		    // [START] Project Task Grid Data 구성 for loop
		    Task task = null;
		    for (KETParamMapUtil taskInfoMap : projectTaskScheduleList) {

			// Grid Data 구성을 위한 Task 정보
			taskId = taskInfoMap.getString("id");
			taskName = taskInfoMap.getString("taskName");
			planStartDate = taskInfoMap.getString("planStartDate");
			planEndDate = taskInfoMap.getString("planEndDate");
			planDuration = taskInfoMap.getInt("planDuration");
			taskCompletion = taskInfoMap.getString("taskCompletion");
			planManHour = taskInfoMap.getString("planManHour");
			personRole = taskInfoMap.getString("personRole");
			milestoneType = taskInfoMap.getString("milestoneType");
			optionType = taskInfoMap.getString("optionType");
			taskType = taskInfoMap.getString("taskType");
			drValue = taskInfoMap.getString("drValue");
			taskLevel = taskInfoMap.getInt("taskLevel");
			parentHierarchy = taskInfoMap.getString("parentHierarchy");
			parentTaskOid = taskInfoMap.getString("parentTaskOid");
			isLeaf = taskInfoMap.getString("isLeaf");
			taskSeq = taskInfoMap.getInt("taskSeq");
			taskOid = taskInfoMap.getString("taskOid");
			taskAncestors = taskInfoMap.getString("taskAncestors");

			if (taskLevel == 1) {
			    task = projectTask.addTask();
			    task.setNotes(taskId);
			    task.setName(taskName);
			    task.setStart(df.parse(planStartDate));
			    task.setDuration(Duration.getInstance(planDuration, TimeUnit.DAYS));
			} else {
			    task = getParentTask(task, taskLevel).addTask();
			    task.setNotes(taskId);
			    task.setName(taskName);
			    task.setStart(df.parse(planStartDate));
			    task.setFinish(df.parse(planEndDate));

			    if (!taskInfoMap.getString("execStartDate").equals("")) {
				task.setActualStart(df.parse(taskInfoMap.getString("execStartDate")));
			    }
			    if (!taskInfoMap.getString("execEndDate").equals("")) {
				task.setActualFinish(df.parse(taskInfoMap.getString("execEndDate")));
			    }

			    task.setDuration(Duration.getInstance(planDuration, TimeUnit.DAYS));

			    task.set(TaskField.TEXT1, taskId);

			    if (isLeaf.equals("1")) {
				task.setConstraintType(ConstraintType.START_NO_EARLIER_THAN);
			    }
			}

			// Milestone
			if (milestoneType.equals("1")) {
			    task.setMilestone(true);
			}

			// Task OID로부터 Task 객체 가져오기
			tempTask = (TemplateTask) CommonUtil.getObject(taskOid);

			// Task 책임자, 참여자 초기화
			taskMaster = "";
			taskMasterId = "";
			taskMember = "";
			taskMemberId = "";

			if (tempTask != null) {

			    // Task 책임자 가져오기

			    masterListResult = TaskUserHelper.manager.getMaster(tempTask);

			    while (masterListResult != null && masterListResult.hasMoreElements()) {

				templateTaskLink = (TaskMemberLink) ((Object[]) masterListResult.nextElement())[0];
				templateProjectLink = templateTaskLink.getMember();
				projectUser = templateProjectLink.getMember();

				if (projectUser != null) {

				    taskMaster = projectUser.getFullName();
				    taskMasterId = CommonUtil.getOIDString(projectUser);
				}
			    }

			    // Task 참여자 가져오기

			    memberListResult = TaskUserHelper.manager.getMember(tempTask);

			    while (memberListResult != null && memberListResult.hasMoreElements()) {

				templateTaskLink = (TaskMemberLink) ((Object[]) memberListResult.nextElement())[0];
				projectUser = templateTaskLink.getMember().getMember();

				if (projectUser != null) {

				    if (StringUtil.checkString(taskMemberId)) {

					taskMember = taskMember + ",";
					taskMemberId = taskMemberId + ",";
				    }

				    taskMember = taskMember + projectUser.getFullName();
				    taskMemberId = taskMemberId + CommonUtil.getOIDString(projectUser);
				}
			    }

			    // Task 정보 bean 가져오기
			    if (tempTask instanceof E3PSTask) {

				e3psTaskData = new E3PSTaskData((E3PSTask) tempTask);

				// Task 적정 진행율 가져오기
				taskPreferComp = e3psTaskData.getPreferCompStr();
			    }
			}

			// 다음 Task Data 비교 구성을 위한 Task 정보 저장
			preTaskLevel = taskLevel;

		    }
		    // [END] Project Task Grid Data 구성 for loop

		    // [START] 4. Enumeration Type 구성

		    // 4-1. Role

		    // Team 정보
		    if (project != null) {

			teamName = ProjectHelper.getProjectTeam(project.getPjtType());
			tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);
		    }

		    // Team Role List
		    if (tempTeam != null) {
			vecTeamStd = tempTeam.getRoles();
		    }

		    if (vecTeamStd != null) {

			Collections.sort(vecTeamStd, new RoleComparator());

			// Locale 정보
			if (messageService != null) {
			    locale = messageService.getLocale();
			}

			// PM(금형개발) Role 추가

			enumMap = KETParamMapUtil.getMap();

			if (project instanceof MoldProject) { // 금형 Project : 금형개발 (화면에 표시되는 값만 '금형개발'로 표시)

			    enumMap.put("key", "PM");
			    enumMap.put("value", "금형개발");
			} else { // 검토/제품 Project : PM
			    enumMap.put("key", "PM");
			    enumMap.put("value", "PM");
			}

			enumList.add(enumMap);

			for (int i = 0; i < vecTeamStd.size(); i++) {

			    // Role

			    role = (Role) vecTeamStd.get(i);

			    roleKey = role.toString();
			    roleName = role.getDisplay(locale);

			    // Person Role Map List 구성

			    enumMap = KETParamMapUtil.getMap();

			    enumMap.put("key", roleKey);
			    enumMap.put("value", roleName);

			    enumList.add(enumMap);
			}
		    }

		    if (enumList != null) {
			for (int i = 0; i < enumList.size(); i++) {
			    Resource resource = file.addResource();
			    resource.setName("Role-" + enumList.get(i).get("value").toString());
			}
		    }

		    QueryResult list = ProjectUserHelper.manager.getMember(project);
		    while (list.hasMoreElements()) {
			Object[] o = (Object[]) list.nextElement();
			ProjectUserData data = new ProjectUserData((ProjectMemberLink) o[0]);

			Resource resource = file.addResource();
			resource.setName(data.name);
		    }

		    for (KETParamMapUtil taskInfoMap : projectTaskScheduleList) {

			// Grid Data 구성을 위한 Task 정보
			taskId = taskInfoMap.getString("id");
			taskName = taskInfoMap.getString("taskName");
			planStartDate = taskInfoMap.getString("planStartDate");
			planEndDate = taskInfoMap.getString("planEndDate");
			planDuration = taskInfoMap.getInt("planDuration");
			taskCompletion = taskInfoMap.getString("taskCompletion");
			planManHour = taskInfoMap.getString("planManHour");
			personRole = taskInfoMap.getString("personRole");
			milestoneType = taskInfoMap.getString("milestoneType");
			optionType = taskInfoMap.getString("optionType");
			taskType = taskInfoMap.getString("taskType");
			drValue = taskInfoMap.getString("drValue");
			taskLevel = taskInfoMap.getInt("taskLevel");
			parentHierarchy = taskInfoMap.getString("parentHierarchy");
			parentTaskOid = taskInfoMap.getString("parentTaskOid");
			isLeaf = taskInfoMap.getString("isLeaf");
			taskSeq = taskInfoMap.getInt("taskSeq");
			taskOid = taskInfoMap.getString("taskOid");
			taskAncestors = taskInfoMap.getString("taskAncestors");

			if (!taskAncestors.equals("")) {

			    String[] link = taskAncestors.split(";");

			    for (int i = 0; i < link.length; i++) {

				if (getTask(file, taskId) != null && getTask(file, link[i]) != null) {
				    getTask(file, taskId).addPredecessor(getTask(file, link[i]), RelationType.FINISH_START, null);
				}
			    }
			}
		    }

		    // for (Task task : file.getChildTasks()) {
		    // Kogger.debug(getClass(), "Task: " + task.getName() + "--" + task.getNotes() );
		    // listHierarchy(task, " ");
		    // }

		    File dir = new File("D:/ptc/Windchill_10.2/Windchill/temp/mpp/");
		    if (!dir.isDirectory()) {
			dir.mkdir();
		    }
		    dir = new File("D:/ptc/Windchill_10.2/Windchill/temp/mpp/DownloadTemp/");
		    if (!dir.isDirectory()) {
			dir.mkdir();
		    }

		    if (dir.isDirectory()) {
			mppFileName = "D:/ptc/Windchill_10.2/Windchill/temp/mpp/DownloadTemp/" + project.getPjtNo() + ".XML";
			ProjectWriter writer = getWriter(mppFileName);
			writer.write(file, mppFileName);

			File fileObj = new File(mppFileName);
			DownloadUtil.download(req, res, fileObj);
		    }
		} catch (ServletException e) {
		    Kogger.error(getClass(), e);
		} catch (IOException e) {
		    Kogger.error(getClass(), e);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		} finally {
		    PlmDBUtil.close(conn);

		    File fileObj = new File(mppFileName);
		    if (fileObj.isFile()) {
			fileObj.delete();
		    }
		}
	    } catch (Exception ex) {
		Kogger.error(getClass(), ex);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private Task getParentTask(Task task, int level) {
	Task returnTask = task;

	if (task.getOutlineLevel().equals(level)) {
	    returnTask = task;
	} else {
	    returnTask = getParentTask(task.getParentTask(), level);
	}

	return returnTask;
    }

    boolean check = false;

    private Task getTask(ProjectFile file, String taskId) {
	check = false;
	Task returnTask = null;
	for (Task task : file.getChildTasks()) {
	    if (task.getNotes().equals(taskId)) {
		returnTask = task;
		check = true;
		break;
	    } else {
		returnTask = getChildTask(task, taskId);
	    }

	    if (check) {
		break;
	    }
	}
	return returnTask;
    }

    private Task getChildTask(Task task, String taskId) {
	Task returnTask = null;
	for (Task child : task.getChildTasks()) {
	    if (child.getNotes().equals(taskId)) {
		returnTask = child;
		check = true;
		break;
	    } else {
		returnTask = getChildTask(child, taskId);
	    }

	    if (check) {
		break;
	    }
	}

	return returnTask;
    }

    private static void create(String filename) throws Exception {
	//
	// Create a simple date format to allow us to
	// easily set date values.
	//
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	//
	// Create an empty MPX or MSPDI file. The filename is passed to
	// this method purely to allow it to determine the type of
	// file to create.
	//
	ProjectFile file = new ProjectFile();

	//
	// Uncomment these lines to test the use of alternative
	// delimiters and separators for MPX file output.
	//
	// file.setDelimiter(';');
	// file.setDecimalSeparator(',');
	// file.setThousandsSeparator('.');

	//
	// Add a default calendar called "Standard"
	//
	// ProjectCalendar calendar = file.addDefaultBaseCalendar();

	//
	// Add a holiday to the calendar to demonstrate calendar exceptions
	//
	// calendar.addCalendarException(df.parse("13/03/2006"), df.parse("13/03/2006"));

	//
	// Retrieve the project header and set the start date. Note Microsoft
	// Project appears to reset all task dates relative to this date, so this
	// date must match the start date of the earliest task for you to see
	// the expected results. If this value is not set, it will default to
	// today's date.
	//
	ProjectHeader header = file.getProjectHeader();
	header.setStartDate(df.parse("01/01/2003"));

	//
	// Add resources
	//
	// Resource resource1 = file.addResource();
	// resource1.setName("Resource1");
	//
	// Resource resource2 = file.addResource();
	// resource2.setName("Resource2");

	//
	// This next line is not required, it is here simply to test the
	// output file format when alternative separators and delimiters
	// are used.
	//
	// resource2.setMaxUnits(Double.valueOf(50.0));

	//
	// Create a summary task
	//
	Task task1 = file.addTask();
	task1.setName("Summary Task");

	//
	// Create the first sub task
	//
	Task task2 = task1.addTask();
	task2.setName("First Sub Task");
	task2.setDuration(Duration.getInstance(10.5, TimeUnit.DAYS));
	task2.setStart(df.parse("01/01/2003"));

	//
	// We'll set this task up as being 50% complete. If we have no resource
	// assignments for this task, this is enough information for MS Project.
	// If we do have resource assignments, the assignment record needs to
	// contain the corresponding work and actual work fields set to the
	// correct values in order for MS project to mark the task as complete
	// or partially complete.
	//
	task2.setPercentageComplete(NumberUtility.getDouble(50.0));
	task2.setActualStart(df.parse("01/01/2003"));

	//
	// Create the second sub task
	//
	Task task3 = task1.addTask();
	task3.setName("Second Sub Task");
	task3.setStart(df.parse("11/01/2003"));
	task3.setDuration(Duration.getInstance(10, TimeUnit.DAYS));

	//
	// Link these two tasks
	//
	// task3.addPredecessor(task2, RelationType.FINISH_START, null);

	//
	// Add a milestone
	//
	Task milestone1 = task1.addTask();
	milestone1.setName("Milestone");
	milestone1.setStart(df.parse("21/01/2003"));
	milestone1.setDuration(Duration.getInstance(0, TimeUnit.DAYS));
	// milestone1.addPredecessor(task3, RelationType.FINISH_START, null);

	//
	// This final task has a percent complete value, but no
	// resource assignments. This is an interesting case it it requires
	// special processing to generate the MSPDI file correctly.
	//
	Task task4 = file.addTask();
	task4.setName("Next Task");
	task4.setDuration(Duration.getInstance(8, TimeUnit.DAYS));
	task4.setStart(df.parse("01/01/2003"));
	task4.setPercentageComplete(NumberUtility.getDouble(70.0));
	task4.setActualStart(df.parse("01/01/2003"));

	//
	// Assign resources to tasks
	//
	// ResourceAssignment assignment1 = task2.addResourceAssignment(resource1);
	// ResourceAssignment assignment2 = task3.addResourceAssignment(resource2);

	//
	// As the first task is partially complete, and we are adding
	// a resource assignment, we must set the work and actual work
	// fields in the assignment to appropriate values, or MS Project
	// won't recognise the task as being complete or partially complete
	//
	// assignment1.setWork(Duration.getInstance(80, TimeUnit.HOURS));
	// assignment1.setActualWork(Duration.getInstance(40, TimeUnit.HOURS));

	//
	// If we were just generating an MPX file, we would already have enough
	// attributes set to create the file correctly. If we want to generate
	// an MSPDI file, we must also set the assignment start dates and
	// the remaining work attribute. The assignment start dates will normally
	// be the same as the task start dates.
	//
	// assignment1.setRemainingWork(Duration.getInstance(40, TimeUnit.HOURS));
	// assignment2.setRemainingWork(Duration.getInstance(80, TimeUnit.HOURS));
	// assignment1.setStart(df.parse("01/01/2003"));
	// assignment2.setStart(df.parse("11/01/2003"));

	//
	// Write a 100% complete task
	//
	Task task5 = file.addTask();
	task5.setName("Last Task");
	task5.setDuration(Duration.getInstance(3, TimeUnit.DAYS));
	task5.setStart(df.parse("01/01/2003"));
	task5.setPercentageComplete(NumberUtility.getDouble(100.0));
	task5.setActualStart(df.parse("01/01/2003"));

	//
	// Write the file
	//
	ProjectWriter writer = getWriter(filename);
	writer.write(file, filename);
    }

    private static ProjectWriter getWriter(String filename) {
	ProjectWriter result;
	String suffix;

	if (filename.length() < 4) {
	    suffix = ".MPX";
	} else {
	    suffix = filename.substring(filename.length() - 4).toUpperCase();
	}

	if (suffix.equals(".XML") == true) {
	    result = new MSPDIWriter();
	} else {
	    result = new MPXWriter();
	}

	return (result);
    }
}
