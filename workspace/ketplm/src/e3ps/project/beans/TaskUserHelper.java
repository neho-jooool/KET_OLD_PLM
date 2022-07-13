package e3ps.project.beans;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.BaseTableInfo;
import wt.introspection.ClassInfo;
import wt.introspection.DatabaseInfo;
import wt.introspection.WTIntrospector;
import wt.method.RemoteAccess;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.KeywordExpression;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.query.SetOperator;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTAttributeNameIfc;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.groupware.company.Department;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateTask;
import ext.ket.shared.log.Kogger;

public class TaskUserHelper implements RemoteAccess {
    public static final int PM = 0; // PM(Project Manager), KCTProject 등록자
    public static final int PL = 1; // PL(Project Leader), TASK 책임자
    public static final int ROLEMEMBER = 2; // ROLEMEMBER, 각 사업부 산출물 담당자
    public static final int MEMBER = 3; // MEMBER, ROLEMEMBER 이외에 프로젝트 구성

    public static final String[] DUTYNAME = { "PM", "TASK 책임자(PL)", "ROLE 구성원", "구성원" };

    public static final TaskUserHelper manager = new TaskUserHelper();
    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

    private TaskUserHelper() {
    }

    /**
     * 템플릿 태스크의 구서원 정보를 타켓 태스크에 복사한다.
     * 
     * @param task
     *            타켓 태스크
     * @param template
     *            원본 템플릿 태스크
     */
    /*
     * public void copyTaskUserInfo(TemplateTask task, TemplateTask template) { try { // ROLEMEMBER 영역 카피 QueryResult masterList =
     * getMaster(template); while ( masterList != null && masterList.hasMoreElements() ) { Object
     * objArr[]=(Object[])masterList.nextElement(); TaskMemberLink templateTaskLink = (TaskMemberLink)objArr[0]; ProjectMemberLink
     * templateProjectLink = templateTaskLink.getMember(); WTUser projectUser = templateProjectLink.getMember();
     * 
     * setMaster(task, projectUser); }
     * 
     * // Add MEMBER 영역 카피 QueryResult memberList = getMember(template); while ( memberList != null && memberList.hasMoreElements() ) {
     * Object objArr[]=(Object[])memberList.nextElement(); TaskMemberLink templateTaskLink = (TaskMemberLink)objArr[0]; ProjectMemberLink
     * templateProjectLink = templateTaskLink.getMember(); WTUser projectUser = templateProjectLink.getMember();
     * 
     * setMember(task,projectUser); } } catch (Exception e ) { Kogger.error(getClass(), e); } }
     */

    public void deleteTaskUser(TemplateTask task) {
	QueryResult qr = null;
	try {
	    qr = getTaskAllUser(task);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}
	if (qr != null) {
	    while (qr.hasMoreElements()) {
		Object o[] = (Object[]) qr.nextElement();
		TaskMemberLink link = (TaskMemberLink) o[0];
		deleteTaskUser(link);
	    }
	}
    }

    public void deleteTaskMember(TemplateTask task, WTUser wtuser) throws Exception {

	QueryResult rs = getTaskUser(task, TaskUserHelper.MEMBER, wtuser);
	// Kogger.debug(getClass(), "delete Member ==>" + rs.size() + "  ## "+wtuser.getFullName());

	while (rs.hasMoreElements()) {
	    Object o[] = (Object[]) rs.nextElement();
	    TaskMemberLink link = (TaskMemberLink) o[0];
	    ProjectMemberLink mlink = link.getMember();
	    // Kogger.debug(getClass(), "TaskMemberLink =>");
	    PersistenceHelper.manager.delete(link);
	    if (!isUse(mlink)) {
		// Kogger.debug(getClass(), "ProjectMemberLink =>");
		PersistenceHelper.manager.delete(mlink);
	    }

	}

    }

    public boolean isUse(ProjectMemberLink link) throws Exception {
	QuerySpec spec = new QuerySpec(TaskMemberLink.class);
	long linkId = link.getPersistInfo().getObjectIdentifier().getId();
	spec.appendWhere(new SearchCondition(TaskMemberLink.class, "roleBObjectRef.key.id", "=", linkId), new int[] { 0 });
	QueryResult rs = PersistenceHelper.manager.find(spec);
	return rs.hasMoreElements();
    }

    public void deleteTaskMaster(TemplateTask task, WTUser wtuser) {
	if (task.getPersonRole() != null && task.getPersonRole().length() > 0) {
	    return;
	}

	try {
	    QueryResult rs = getTaskUser(task, TaskUserHelper.PL, wtuser);
	    while (rs.hasMoreElements()) {
		Object o[] = (Object[]) rs.nextElement();
		TaskMemberLink link = (TaskMemberLink) o[0];
		PersistenceHelper.manager.delete(link);
	    }
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}

    }

    protected void deleteTaskUser(TaskMemberLink link) {
	try {
	    // ProjectMemberLink에서 pjtMemberLink 변경
	    // ProjectMemberLink pjtLink = link.getMember();
	    // pjtLink.setPjtMemberType(ProjectUserHelper.manager.MEMBER);
	    // pjtLink = (ProjectMemberLink) PersistenceHelper.manager.modify(pjtLink);

	    PersistenceHelper.manager.delete(link);

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    // } catch (WTPropertyVetoException e) {
	    // Kogger.error(getClass(), e);
	} finally {
	    // if ( memberType == TaskUserHelper.PM
	    // || memberType == TaskUserHelper.PL ) {
	    // // 메일 발송 해야 함...
	    // }
	}
    }

    public QueryResult getCreater(TemplateTask task) {
	return getTaskUser(task, TaskUserHelper.PM);
    }

    public QueryResult getMaster(TemplateTask task) {
	return getTaskUser(task, TaskUserHelper.PL);
    }

    public QueryResult getMember(TemplateTask task) {
	return getTaskUser(task, TaskUserHelper.MEMBER);
    }

    public QueryResult getTaskAllUser(TemplateTask task) throws Exception {

	QuerySpec qs = new QuerySpec();
	Class taskPeopleLinkClass = TaskMemberLink.class;
	int taskPeopleLinkClassPos = qs.addClassList(taskPeopleLinkClass, true);

	SearchUtil.appendEQUAL(qs, taskPeopleLinkClass, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(task), taskPeopleLinkClassPos);
	QueryResult result = PersistenceHelper.manager.find(qs);
	return result;

    }

    public QueryResult getTaskPeopleLink(ProjectMemberLink link) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class taskPeopleLinkClass = TaskMemberLink.class;
	    int taskPeopleLinkClassPos = qs.addClassList(taskPeopleLinkClass, true);

	    qs.appendWhere(new SearchCondition(taskPeopleLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.NOT_EQUAL,
		    TaskUserHelper.PM), new int[] { taskPeopleLinkClassPos });

	    qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(link);
	    qs.appendWhere(new SearchCondition(taskPeopleLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    new int[] { taskPeopleLinkClassPos });
	    SearchUtil
		    .setOrderBy(qs, taskPeopleLinkClass, taskPeopleLinkClassPos, TaskMemberLink.TASK_MEMBER_TYPE, "taskMemberType", false);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    if (result != null && result.size() > 0) {
		return result;
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public TaskMemberLink getTaskPeopleLink(TemplateTask task, ProjectMemberLink link) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class taskPeopleLinkClass = TaskMemberLink.class;
	    int taskPeopleLinkClassPos = qs.addClassList(taskPeopleLinkClass, true);

	    // qs.appendWhere(new SearchCondition(taskPeopleLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.NOT_EQUAL,
	    // TaskUserHelper.PM), taskPeopleLinkClassPos);
	    // qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(task);
	    qs.appendWhere(new SearchCondition(taskPeopleLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    taskPeopleLinkClassPos);
	    qs.appendAnd();
	    oidValue = CommonUtil.getOIDLongValue(link);
	    qs.appendWhere(new SearchCondition(taskPeopleLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    taskPeopleLinkClassPos);
	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    if (qr != null && qr.hasMoreElements()) {
		Object aobj[] = (Object[]) qr.nextElement();
		return (TaskMemberLink) aobj[0];
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public TaskMemberLink getTaskPeopleLink(TemplateTask task, WTUser wtuser) {
	ProjectMemberLink link = ProjectUserHelper.manager.getProjectPeopleLink(task.getProject(), wtuser);
	if (link == null) {
	    return null;
	}
	return getTaskPeopleLink(task, link);
    }

    public QueryResult getTaskUser(TemplateTask task) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class taskMemberLinkClass = TaskMemberLink.class;
	    int taskMemberLinkClassPos = qs.addClassList(taskMemberLinkClass, true);

	    long oidValue = CommonUtil.getOIDLongValue(task);

	    qs.appendWhere(new SearchCondition(taskMemberLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    taskMemberLinkClassPos);

	    // qs.appendAnd();
	    // qs.appendOpenParen();
	    // qs.appendWhere(new SearchCondition(taskMemberLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.EQUAL,
	    // TaskUserHelper.PL), taskMemberLinkClassPos);
	    // qs.appendOr();
	    // qs.appendWhere(new SearchCondition(taskMemberLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.EQUAL,
	    // TaskUserHelper.MEMBER), taskMemberLinkClassPos);
	    // qs.appendCloseParen();
	    SearchUtil
		    .setOrderBy(qs, taskMemberLinkClass, taskMemberLinkClassPos, TaskMemberLink.TASK_MEMBER_TYPE, "taskMemberType", false);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getTaskUser(TemplateTask task, WTUser user) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class taskMemberLinkClass = TaskMemberLink.class;
	    int idx0 = qs.addClassList(taskMemberLinkClass, true);
	    int idx1 = qs.addClassList(ProjectMemberLink.class, false);

	    ClassAttribute ca0 = new ClassAttribute(taskMemberLinkClass, "roleBObjectRef.key.id");
	    ClassAttribute ca1 = new ClassAttribute(ProjectMemberLink.class, "thePersistInfo.theObjectIdentifier.id");

	    SearchCondition sc = new SearchCondition(ca0, "=", ca1);
	    sc.setFromIndicies(new int[] { idx0, idx1 }, 0);
	    sc.setOuterJoin(0);
	    qs.appendWhere(sc, new int[] { idx0, idx1 });

	    qs.appendAnd();

	    long userId = CommonUtil.getOIDLongValue(user);

	    qs.appendWhere(new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, userId),
		    new int[] { idx1 });

	    qs.appendAnd();

	    // qs.appendWhere(new SearchCondition(taskMemberLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.EQUAL, memberType),
	    // idx0);
	    // qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(task);
	    qs.appendWhere(new SearchCondition(taskMemberLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    new int[] { idx0 });
	    SearchUtil.setOrderBy(qs, taskMemberLinkClass, idx0, TaskMemberLink.TASK_MEMBER_TYPE, "taskMemberType", false);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    // Kogger.debug(getClass(), " getTaskUser == " + qs);
	    if (result != null)
		return result;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getTaskUser(TemplateTask task, int memberType) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class taskMemberLinkClass = TaskMemberLink.class;
	    int taskMemberLinkClassPos = qs.addClassList(taskMemberLinkClass, true);

	    qs.appendWhere(new SearchCondition(taskMemberLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.EQUAL, memberType),
		    taskMemberLinkClassPos);
	    qs.appendAnd();
	    long oidValue = CommonUtil.getOIDLongValue(task);
	    qs.appendWhere(new SearchCondition(taskMemberLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    taskMemberLinkClassPos);
	    SearchUtil
		    .setOrderBy(qs, taskMemberLinkClass, taskMemberLinkClassPos, TaskMemberLink.TASK_MEMBER_TYPE, "taskMemberType", false);
	    QueryResult result = PersistenceHelper.manager.find(qs);
	    // Kogger.debug(getClass(), " getTaskUser == " + qs);
	    if (result != null)
		return result;
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return null;
    }

    public QueryResult getTaskUser(TemplateTask task, int memberType, WTUser user) throws Exception {

	QuerySpec qs = new QuerySpec();
	Class taskMemberLinkClass = TaskMemberLink.class;
	int taskMemberLinkClassPos = qs.addClassList(taskMemberLinkClass, true);
	int pmemberLinkIndex = qs.addClassList(ProjectMemberLink.class, false);

	SearchCondition tpsc = new SearchCondition(new ClassAttribute(TaskMemberLink.class, "roleBObjectRef.key.id"), "=",
	        new ClassAttribute(ProjectMemberLink.class, "thePersistInfo.theObjectIdentifier.id"));
	tpsc.setFromIndicies(new int[] { taskMemberLinkClassPos, pmemberLinkIndex }, 0);
	tpsc.setOuterJoin(0);
	qs.appendWhere(tpsc, new int[] { taskMemberLinkClassPos, pmemberLinkIndex });

	qs.appendAnd();

	long userId = user.getPersistInfo().getObjectIdentifier().getId();

	qs.appendWhere(new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", "=", userId), new int[] { pmemberLinkIndex });

	qs.appendAnd();
	qs.appendWhere(new SearchCondition(taskMemberLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.EQUAL, memberType),
	        taskMemberLinkClassPos);
	qs.appendAnd();
	long oidValue = CommonUtil.getOIDLongValue(task);
	qs.appendWhere(new SearchCondition(taskMemberLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue),
	        taskMemberLinkClassPos);
	SearchUtil.setOrderBy(qs, taskMemberLinkClass, taskMemberLinkClassPos, TaskMemberLink.TASK_MEMBER_TYPE, "taskMemberType", false);

	QueryResult rs = PersistenceHelper.manager.find(qs);

	return rs;
    }

    public boolean isTaskUser(TemplateTask task, ProjectMemberLink link) {
	try {
	    QuerySpec qs = new QuerySpec();
	    Class taskPeopleLinkClass = TaskMemberLink.class;
	    int taskPeopleLinkClassPos = qs.addClassList(taskPeopleLinkClass, true);

	    long oidValue = CommonUtil.getOIDLongValue(task);
	    qs.appendWhere(new SearchCondition(taskPeopleLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    new int[] { taskPeopleLinkClassPos });
	    qs.appendAnd();
	    oidValue = CommonUtil.getOIDLongValue(link);
	    qs.appendWhere(new SearchCondition(taskPeopleLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue),
		    new int[] { taskPeopleLinkClassPos });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(taskPeopleLinkClass, TaskMemberLink.TASK_MEMBER_TYPE, SearchCondition.NOT_EQUAL, 4),
		    new int[] { taskPeopleLinkClassPos });

	    // Kogger.debug(getClass(), "isTaskUser==> " + qs);
	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    if (qr != null && qr.size() > 0) {
		return true;
	    }
	} catch (QueryException e) {
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
	return false;
    }

    public boolean isTaskUser(TemplateTask task) {
	WTUser wtuser = null;
	try {
	    wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	} catch (WTException e) {
	}
	return isTaskUser(task, wtuser);
    }

    public boolean isTaskUser(TemplateTask task, WTUser wtuser) {

	QueryResult rs = getTaskUser(task, wtuser);

	return rs.size() > 0;

    }

    protected void modifyTaskUser(TaskMemberLink link, int memberType) {
	try {
	    // String oldRole = TaskUserHelper.DUTYNAME[link.getTaskMemberType()];
	    // String newRole = TaskUserHelper.DUTYNAME[memberType];

	    if (link != null) {
		Kogger.debug(getClass(), "modifyTaskUser[Link]>>>>>" + link.getTaskMemberType());

		link.setTaskMemberType(memberType);
		PersistenceHelper.manager.modify(link);
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // if ( memberType == TaskUserHelper.MASTER
	    // || memberType == TaskUserHelper.MEMBER ) {
	    // // 메일 발송 해야 함...
	    // }
	}
    }

    protected void modifyTaskUser(TemplateTask task, ProjectMemberLink link, int memberType) {
	try {
	    TaskMemberLink tpLink = getTaskPeopleLink(task, link);
	    modifyTaskUser(tpLink, memberType);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // if ( memberType == TaskUserHelper.MASTER
	    // || memberType == TaskUserHelper.MEMBER ) {
	    // // 메일 발송 해야 함...
	    // }
	}
    }

    protected void modifyTaskUser(TemplateTask task, WTUser wtuser, int memberType) {
	try {
	    ProjectMemberLink link = ProjectUserHelper.manager.getProjectPeopleLink(task.getProject(), wtuser);

	    modifyTaskUser(task, link, memberType);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void setCreater(TemplateTask task, WTUser wtuser) {
	if (getCreater(task).size() > 0) {
	    return;
	} else {
	    setTaskUser(task, wtuser, TaskUserHelper.PM);
	}
    }

    /**
     * 함수명 : setMaster 수정내용 : [PLM System 1차개선] Task 책임자로 지정하려는 인원이 Task 참여자일 경우 삭제 처리, Task Task 책임자를 Project 전체 인원 중에서 선택 가능하도록 변경
     * 
     * @param task
     * @param wtuser
     *            수정자 : BoLee 수정일자 : 2013. 8. 8.
     */
    public void setMaster(TemplateTask task, WTUser wtuser) {

	if (isTaskUser(task, wtuser)) {

	    // [START] [PLM System 1차개선] Task 참여자로 지정되어 있던 인원일 경우 삭제 처리, 2013-08-09, BoLee
	    try {
		QueryResult qResult = TaskUserHelper.manager.getTaskUser(task, TaskUserHelper.MEMBER, wtuser);
		TaskMemberLink memberLink = null;
		Object obj = null;

		while (qResult.hasMoreElements()) {

		    obj = ((Object[]) qResult.nextElement())[0];
		    memberLink = (TaskMemberLink) obj;

		    try {
			PersistenceHelper.manager.delete(memberLink);
		    } catch (WTException e) {
			Kogger.error(getClass(), e);
		    }
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	    // [END] [PLM System 1차개선] Task 참여자로 지정되어 있던 인원일 경우 삭제 처리, 2013-08-09, BoLee
	}

	QueryResult rs = getMaster(task);
	while (rs.hasMoreElements()) {
	    Object o = ((Object[]) rs.nextElement())[0];
	    TaskMemberLink link = (TaskMemberLink) o;
	    try {
		PersistenceHelper.manager.delete(link);
	    } catch (WTException e) {
		Kogger.error(getClass(), e);
	    }
	}

	// [PLM System 1차개선] Task 책임자를 Project 전체 인원 중에서 선택 가능하도록 변경 (ProjectUserHelper.MEMBER parameter 삭제), 2013-08-08, BoLee
	ProjectMemberLink memberlink = ProjectUserHelper.manager.getProjectMemberLink(task.getProject(), wtuser);

	setTaskUser(task, memberlink, TaskUserHelper.PL);
	// if ( isTaskUser(task, wtuser) ) {
	//
	// QueryResult result = getTaskUser(task, wtuser);
	//
	// while(result.hasMoreElements()){
	// Object o[] = (Object[])result.nextElement();
	// TaskMemberLink link = (TaskMemberLink)o[0];
	// try {
	// PersistenceHelper.manager.delete(link);
	// } catch (WTException e) {
	// Kogger.error(getClass(), e);
	// }
	// }
	//
	// setTaskUser(task, wtuser, TaskUserHelper.PL);
	//
	// } else {
	//
	// setTaskUser(task, wtuser, TaskUserHelper.PL);
	// }
    }

    public void setMember(TemplateTask task, WTUser wtuser) throws Exception {
	// Kogger.debug(getClass(), "setMember = ");
	if (isTaskUser(task, wtuser)) {
	    return;
	}

	ProjectMemberLink link = ProjectUserHelper.manager.getProjectMemberLink(task.getProject(), wtuser, ProjectUserHelper.TASK_MEMBER);
	if (link == null) {
	    link = ProjectMemberLink.newProjectMemberLink(task.getProject(), wtuser);
	    link.setPjtMemberType(ProjectUserHelper.TASK_MEMBER);
	    // link.setPjtHistory(project.getPjtHistory());
	    link = (ProjectMemberLink) PersistenceHelper.manager.save(link);

	    ProjectMemberLink link2 = ProjectMemberLink.newProjectMemberLink(task.getProject(), wtuser);
	    link2.setPjtMemberType(ProjectUserHelper.MEMBER);
	    // link.setPjtHistory(project.getPjtHistory());
	    link2 = (ProjectMemberLink) PersistenceHelper.manager.save(link2);

	}

	setTaskUser(task, link, TaskUserHelper.MEMBER);

	/*
	 * TaskMemberLink link = getTaskPeopleLink(task, wtuser); if ( link != null ) { if ( link.getTaskMemberType() == TaskUserHelper.PL )
	 * return; else if ( link.getTaskMemberType() == TaskUserHelper.MEMBER ){ setTaskUser(task, wtuser, TaskUserHelper.MEMBER); } } else
	 * { setTaskUser(task, wtuser, TaskUserHelper.MEMBER); }
	 */
    }

    protected void setTaskUser(TemplateTask task, ProjectMemberLink link, int memberType) {
	try {
	    // TaskMemberLink Create
	    String role = TaskUserHelper.DUTYNAME[memberType];
	    // Kogger.debug(getClass(), "link========== " + link);
	    TaskMemberLink tpLink = TaskMemberLink.newTaskMemberLink(task, link);
	    tpLink.setActor(WTPrincipalReference.newWTPrincipalReference(link.getMember()));
	    tpLink.setTaskMemberType(memberType);
	    tpLink.setTaskRoleName(role);
	    tpLink.setTaskHistory(task.getTaskHistory() + 1);
	    tpLink = (TaskMemberLink) PersistenceHelper.manager.save(tpLink);
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // if ( memberType == TaskUserHelper.MASTER
	    // || memberType == TaskUserHelper.MEMBER ) {
	    // 메일 발송 해야 함...
	    // }
	}
    }

    protected void setTaskUser(TemplateTask task, WTUser wtuser, int memberType) {
	try {
	    ProjectMemberLink link = ProjectUserHelper.manager.getProjectMemberLink(task.getProject(), wtuser);
	    if (link == null) {

		link = ProjectMemberLink.newProjectMemberLink(task.getProject(), wtuser);
		link.setPjtMemberType(ProjectUserHelper.MEMBER);
		// link.setPjtHistory(project.getPjtHistory());
		link = (ProjectMemberLink) PersistenceHelper.manager.save(link);

	    }
	    // link = (ProjectMemberLink) PersistenceHelper.manager.refresh(link);
	    setTaskUser(task, link, memberType);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public QueryResult getViewDepartmentLink(TemplateTask task, Department dept) {
	QueryResult result = null;

	return result;
    }

    public boolean isUserTask(E3PSProject project, WTUser wtuser) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { E3PSProject.class, WTUser.class };
	    Object args[] = new Object[] { project, wtuser };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("isUserTask", TaskUserHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(getClass(), e);
		throw new WTException(e);
	    }
	    return (boolean) Boolean.getBoolean(obj.toString());
	}
	boolean isCheck = false;
	try {

	    String initType = "produceproject";
	    QuerySpec qs = new QuerySpec();
	    int taskMemberlink_idx = qs.addClassList(TaskMemberLink.class, false);
	    int project_idxx = qs.addClassList(E3PSProject.class, false);
	    int task_idxx = qs.addClassList(E3PSTask.class, false);

	    ClassInfo classinfo = WTIntrospector.getClassInfo(TaskMemberLink.class);
	    DatabaseInfo databaseinfo = classinfo.getDatabaseInfo();
	    BaseTableInfo basetableinfo = databaseinfo.getBaseTableInfo();

	    String objname = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid = basetableinfo.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke = new KeywordExpression(qs.getFromClause().getAliasAt(task_idxx) + "." + objname + "||':'||"
		    + qs.getFromClause().getAliasAt(task_idxx) + "." + objid);
	    ke.setColumnAlias("taskOid");
	    qs.appendSelect(ke, new int[] { task_idxx }, false);

	    SearchCondition tpsc = new SearchCondition(new ClassAttribute(E3PSTask.class, "projectReference.key.id"), "=",
		    new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id"));
	    tpsc.setFromIndicies(new int[] { task_idxx, project_idxx }, 0);
	    tpsc.setOuterJoin(0);
	    qs.appendWhere(tpsc, new int[] { task_idxx, project_idxx });

	    qs.appendAnd();

	    SearchCondition ttl_sc = new SearchCondition(new ClassAttribute(E3PSTask.class, "thePersistInfo.theObjectIdentifier.id"), "=",
		    new ClassAttribute(TaskMemberLink.class, "roleAObjectRef.key.id"));
	    ttl_sc.setFromIndicies(new int[] { task_idxx, taskMemberlink_idx }, 0);
	    ttl_sc.setOuterJoin(0);
	    qs.appendWhere(ttl_sc, new int[] { task_idxx, taskMemberlink_idx });

	    long oidValue_t = CommonUtil.getOIDLongValue(wtuser);

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(TaskMemberLink.class, "actor.key.id", SearchCondition.EQUAL, oidValue_t),
		        new int[] { taskMemberlink_idx });
	    }

	    if (project != null) {
		if (qs.getConditionCount() > 0)
		    qs.appendAnd();
		// qs.appendWhere(new SearchCondition(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL,
		// CommonUtil.getOIDLongValue(project) ),
		// new int[] {project_idxx});
	    }

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { project_idxx });

	    if (qs.getConditionCount() > 0) {
		qs.appendAnd();
	    }
	    qs.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
		    new int[] { project_idxx });

	    // ################################################################################################################### UNIO

	    QuerySpec spec = null;

	    spec = new QuerySpec();
	    int output_idx = spec.addClassList(ProjectOutput.class, false);
	    int task_idx = spec.appendClassList(E3PSTask.class, false);
	    int project_idx = spec.addClassList(E3PSProject.class, false);

	    ClassInfo classinfo2 = WTIntrospector.getClassInfo(ProjectOutput.class);
	    DatabaseInfo databaseinfo2 = classinfo2.getDatabaseInfo();
	    BaseTableInfo basetableinfo2 = databaseinfo2.getBaseTableInfo();
	    String objname2 = basetableinfo2.getColumnDescriptor("thePersistInfo.theObjectIdentifier.classname").getColumnName();
	    String objid2 = basetableinfo2.getColumnDescriptor("thePersistInfo.theObjectIdentifier.id").getColumnName();

	    KeywordExpression ke2 = new KeywordExpression(spec.getFromClause().getAliasAt(task_idx) + "." + objname2 + "||':'||"
		    + spec.getFromClause().getAliasAt(task_idx) + "." + objid2);
	    ke2.setColumnAlias("taskOid");
	    spec.appendSelect(ke2, new int[] { task_idx }, false);

	    SearchCondition sc = new SearchCondition(new ClassAttribute(ProjectOutput.class, "projectReference.key.id"), "=",
		    new ClassAttribute(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id"));
	    sc.setFromIndicies(new int[] { output_idx, project_idx }, 0);
	    sc.setOuterJoin(0);
	    spec.appendWhere(sc, new int[] { output_idx, project_idx });

	    spec.appendAnd();

	    SearchCondition tasksc = new SearchCondition(new ClassAttribute(e3ps.project.E3PSTask.class, WTAttributeNameIfc.ID_NAME), "=",
		    new ClassAttribute(ProjectOutput.class, "taskReference.key.id"));
	    tasksc.setFromIndicies(new int[] { task_idx, output_idx }, 0);
	    tasksc.setOuterJoin(0);
	    spec.appendWhere(tasksc, new int[] { task_idx, output_idx });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(ProjectOutput.class, "owner.key.id", SearchCondition.EQUAL, oidValue_t), output_idx);

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(E3PSProject.class, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),
		    new int[] { project_idx });

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(E3PSProject.class, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),
		    new int[] { project_idx });

	    if (project != null) {
		if (spec.getConditionCount() > 0)
		    spec.appendAnd();
		// spec.appendWhere(new SearchCondition(E3PSProject.class, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL,
		// CommonUtil.getOIDLongValue(project) ),
		// new int[] {project_idx});
	    }

	    QuerySpec myQs = new QuerySpec();
	    myQs.setAdvancedQueryEnabled(true);

	    CompoundQuerySpec query = new CompoundQuerySpec();

	    query.setSetOperator(SetOperator.UNION);
	    query.addComponent(qs);
	    query.addComponent(spec);

	    SubSelectExpression subfrom = new SubSelectExpression(query);
	    subfrom.setFromAlias(new String[] { "SUB1" }, 0);
	    int idx = myQs.appendFrom(subfrom);

	    KeywordExpression kexpp = new KeywordExpression(myQs.getFromClause().getAliasAt(idx) + "." + "taskOid");
	    myQs.appendSelect(kexpp, new int[] { idx }, false);

	    QueryResult qr = PersistenceHelper.manager.find(myQs);

	    if (qr.size() > 0) {
		isCheck = true;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return isCheck;
    }

    public static TemplateTask setTaskRole(TemplateTask task, String role) throws Exception {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { TemplateTask.class, String.class };
	    Object args[] = new Object[] { task, role };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("setTaskRole", TaskUserHelper.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(TaskUserHelper.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(TaskUserHelper.class, e);
		throw new WTException(e);
	    }
	    return (TemplateTask) obj;
	}

	task.setPersonRole(role);
	task = (TemplateTask) PersistenceHelper.manager.modify(task);

	if (task instanceof E3PSTask) {

	    deleteMaster(task);

	    QueryResult rs = new QueryResult();

	    if ("PM".equals(role)) {
		rs = ProjectUserHelper.manager.getPMProjectMemberLink(task.getProject());

	    } else if (role != null && role.length() > 0) {

		rs = ProjectUserHelper.manager.getProjectRoleMember(task.getProject(), null, role);

	    }

	    if (rs.hasMoreElements()) {

		ProjectMemberLink link = (ProjectMemberLink) rs.nextElement();
		TaskMemberLink taskMemberLink = TaskMemberLink.newTaskMemberLink(task, link);
		taskMemberLink.setTaskRoleName(TaskUserHelper.DUTYNAME[TaskUserHelper.PL]);
		taskMemberLink.setTaskMemberType(TaskUserHelper.PL);
		PersistenceHelper.manager.save(taskMemberLink);
	    }
	}

	return task;
    }

    public static void deleteMaster(TemplateTask task) throws Exception {
	QueryResult rs = TaskUserHelper.manager.getMaster(task);
	while (rs.hasMoreElements()) {
	    Object o = ((Object[]) rs.nextElement())[0];
	    TaskMemberLink link = (TaskMemberLink) o;
	    PersistenceHelper.manager.delete(link);
	}
    }

    public static boolean isMaster(TemplateTask task) throws Exception {

	WTPrincipalReference re = SessionHelper.manager.getPrincipalReference();
	WTUser user = (WTUser) re.getPrincipal();
	QueryResult rs = TaskUserHelper.manager.getTaskUser(task, TaskUserHelper.PL, user);
	return rs.size() > 0;
    }
}
