package e3ps.project.beans;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import e3ps.project.CheckoutLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProductProject;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.ReviewProject;
import e3ps.project.TaskMemberLink;
import e3ps.project.TemplateProject;
import e3ps.project.TemplateTask;
import e3ps.project.historyprocess.HistoryHelper;
import ext.ket.shared.log.Kogger;

public class ProjectUserHelper {

	public static final int PM = 0; // PM(Project Manager), JELProject 등록자
	public static final int PL = 1; // PL(Project Leader), TASK 책임자
	public static final int ROLEMEMBER = 2; // ROLEMEMBER, 각 사업부 산출물 담당자
	public static final int MEMBER = 3; // MEMBER, ROLEMEMBER 이외에 프로젝트 구성
	public static final int ONLY_VIEW_MEMBER = 4; // 단지 View만 한다. 공수에서 제외

	public static final int TASK_MEMBER = 5; // 테스크 사용자

	public static final String[] DUTYNAME = { "PM", "TASK 책임자(PL)", "ROLE 구성원", "구성원", "View권한" };
	
	public static final String CAR_QA = "Team_PRODUCT15";	//자동차사업부 선행품질보증 Role
	public static final String ELEC_QA = "Team_ELECTRON10"; //전자사업부 선행품질보증 Role
	public static final String ELEC_QC = "Team_ELECTRON12"; //전자사업부 선행품질관리 Role
	public static final String CAR_QC = "Team_PRODUCT49"; //자동차사업부 선행품질관리 Role

	public static final ProjectUserHelper manager = new ProjectUserHelper();

	private ProjectUserHelper() {
	}

	public void setPM(TemplateProject templateProject, WTUser wtuser, int historyType) {
		setProjectUser(templateProject, wtuser, ProjectUserHelper.PM, historyType);
	}

	public void setPL(TemplateProject templateProject, WTUser wtuser, int historyType) {
		setProjectUser(templateProject, wtuser, ProjectUserHelper.PL, historyType);
	}

	public void setRoleMember(TemplateProject templateProject, String roleName, WTUser wtuser, int historyType) {
		try {
			saveProjectMember(templateProject, wtuser, roleName, ProjectUserHelper.ROLEMEMBER);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	public void setMember(TemplateProject templateProject, WTUser wtuser, int historyType) {
		setProjectUser(templateProject, wtuser, ProjectUserHelper.MEMBER, historyType);
	}

	public void setViewMember(TemplateProject templateProject, WTUser wtuser, int historyType) {
		setProjectUser(templateProject, wtuser, ProjectUserHelper.ONLY_VIEW_MEMBER, historyType);
	}

	protected void setProjectUser(TemplateProject project, WTUser wtuser, int memberType, int historyType) {
		try {
			saveProjectMember(project, wtuser, null, memberType);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	public ProjectMemberLink saveProjectMember(TemplateProject project, WTUser wtuser, String roleName, int memberType) {
		ProjectMemberLink link = null;
		try {
			QueryResult oldMembers = null;
			if (memberType == ProjectUserHelper.ROLEMEMBER) {
				oldMembers = getProjectRoleMember(project, wtuser, roleName);
			} else {
				oldMembers = getQueryForTeamUsers(project, wtuser, memberType);
			}

			WTUser oldUser = null;
			while (oldMembers.hasMoreElements()) {
				link = (ProjectMemberLink) oldMembers.nextElement();
				oldUser = link.getMember();
				if ((oldUser.getName()).equals(wtuser.getName())) {
					return link;
				}
			}

			link = ProjectMemberLink.newProjectMemberLink(project, wtuser);
			link.setPjtMemberType(memberType);
			link.setPjtHistory(project.getPjtHistory());
			link.setPjtRole(roleName);
			link = (ProjectMemberLink) PersistenceHelper.manager.save(link);

		} catch (WTException e) {
			Kogger.error(getClass(), e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		}

		return link;
	}

	public QueryResult getAllProjectUser(TemplateProject project) throws Exception {
		QuerySpec qs = new QuerySpec();
		Class targetClass = ProjectMemberLink.class;
		int targetClassPos = qs.addClassList(targetClass, true);

		long oidValue = CommonUtil.getOIDLongValue(project);

		qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { targetClassPos });

		QueryResult result = PersistenceHelper.manager.find(qs);
		return result;

	}

	public QueryResult getProjectUser(TemplateProject project) throws Exception {

		QuerySpec qs = new QuerySpec();
		Class targetClass = ProjectMemberLink.class;
		int targetClassPos = qs.addClassList(targetClass, true);

		long oidValue = CommonUtil.getOIDLongValue(project);

		qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { targetClassPos });
		qs.appendAnd();
		// qs.appendOpenParen();
		// qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PM), new int[]{targetClassPos});
		// qs.appendOr();
		// qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PL), new int[] { targetClassPos });
		// qs.appendOr();
		// qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.ROLEMEMBER), new int[] { targetClassPos });
		// qs.appendOr();
		qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.NOT_EQUAL, ProjectUserHelper.ONLY_VIEW_MEMBER),
				new int[] { targetClassPos });
		// qs.appendCloseParen();
		SearchUtil.setOrderBy(qs, targetClass, targetClassPos, ProjectMemberLink.PJT_MEMBER_TYPE, "pjtMemberType", false);
		QueryResult result = PersistenceHelper.manager.find(qs);
		return result;

	}

	public Hashtable getProjectUserForMail(TemplateProject project) throws Exception {
		QueryResult rs = getProjectUser(project);
		Hashtable ht = new Hashtable();
		while (rs.hasMoreElements()) {
			Object[] o = (Object[]) rs.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) o[0];
			WTUser user = link.getMember();
			// if(!data.projectDuty.equals("구성원")){
			ht.put(user.getName(), user);
			// }
		}
		return ht;
	}

	/*
	 * 메일 발송 위해 변경된 task들의 책임자, 참여자 목록 구함 (ProjectHistoryView.jsp 참조)
	 */
	public Hashtable getChangedTaskUserForMail(E3PSProject project) throws Exception {
		Hashtable ht = new Hashtable();

		System.out.println("=============( Project 삭제되는 현상에 대한 원인 검증용 Logging!! )=============================");
		System.out.println("2-1. 메일 발송 위해 변경된 task들의 책임자, 참여자 목록 구함");
		System.out.println("2-1. Before API Call : PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class) ");
		
		QueryResult rs = PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class);
		
		System.out.println("2-1. API Execution Done : PersistenceHelper.manager.navigate(project, CheckoutLink.ORIGINAL_COPY_ROLE, CheckoutLink.class) ");
		
		E3PSProject oldProject = null;
		if (rs.hasMoreElements()) {
			oldProject = (E3PSProject) rs.nextElement();
		}
		DefaultProjectTreeNode root = (DefaultProjectTreeNode) HistoryHelper.getCompareProject(project, oldProject, new HashMap());
		Vector vector = new Vector();
		makeVector(root, vector);

		System.out.println("=============( Project 삭제되는 현상에 대한 원인 검증용 Logging!! )=============================");
		System.out.println("2-7. Project내 포함되어 있는 Task정보들에 대한 Tree Node별 정보 설정부분");
		System.out.println("2-7. Before For Loop : vector.size() ");
		
		for (int i = 0; i < vector.size(); i++) {
			DefaultProjectTreeNode node = (DefaultProjectTreeNode) vector.get(i);

			TreeNodeData td = (TreeNodeData) node.getUserObject();
			if (td.getData() instanceof E3PSProject) {
				/* TemplateProject 이다 */
				continue;
			}

			int compareResult = node.getCompareResult();
			// 변경(추가, 수정, 삭제)된 task 대상
			if (compareResult == DefaultProjectTreeNode.ADD || compareResult == DefaultProjectTreeNode.MODIFY || compareResult == DefaultProjectTreeNode.DELTE) {

				E3PSTask childTask = (E3PSTask) td.getData();
				// Kogger.debug(getClass(), "changed ----> " + node.getLevel() + "  " + childTask.getTaskName());

				rs = TaskUserHelper.manager.getMaster(childTask);
				extractTaskUsers(rs, ht);

				rs = TaskUserHelper.manager.getMember(childTask);
				extractTaskUsers(rs, ht);
			}
		}

		System.out.println("2-7. End For Loop : vector.size() ");
		System.out.println("===========================================================================================");
		
		return ht;
	}

	// (ProjectHistoryView.jsp 참조)
	private static void makeVector(DefaultProjectTreeNode node, Vector vector) {
		vector.add(node);
		for (int i = 0; i < node.getChildCount(); i++) {
			makeVector((DefaultProjectTreeNode) node.getChildAt(i), vector);
		}
	}

	// task user 관련 QueryResult에서 WTUser 추출
	private static void extractTaskUsers(QueryResult rs, Hashtable ht) {
		if (rs.hasMoreElements()) {
			Object[] object = (Object[]) rs.nextElement();
			TaskMemberLink link = (TaskMemberLink) object[0];
			WTUser user = link.getMember().getMember();
			if (ht.containsKey(user.getName()) == false) {
				ht.put(user.getName(), user);
			}
		}
	}

	public Hashtable getPMO2() throws Exception {
		Hashtable toHash = new Hashtable();
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup("KETS_PMO");
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					toHash.put(memberUser.getName(), memberUser);
				}
			}
		}
		return toHash;
	}

	public Hashtable getPMO() throws Exception {
		Hashtable toHash = new Hashtable();
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup("자동차PMO");
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					toHash.put(memberUser.getName(), memberUser);
				}
			}
		}
		return toHash;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Hashtable getPMO(String group) throws Exception {
		Hashtable toHash = new Hashtable();
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup(group);
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					toHash.put(memberUser.getName(), memberUser);
				}
			}
		}
		return toHash;
	}

	@SuppressWarnings("rawtypes")
	public List<WTUser> getPMOListOfCarDiv() throws Exception {
		List<WTUser> pmoList = new ArrayList<WTUser>();
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup("자동차PMO");
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					pmoList.add(memberUser);
				}
			}
		}
		return pmoList;
	}

	@SuppressWarnings("rawtypes")
	public List<WTUser> getPMOlistOfEelecDiv() throws Exception {
		List<WTUser> pmoList = new ArrayList<WTUser>();
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup("전자PMO");
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					pmoList.add(memberUser);
				}
			}
		}
		return pmoList;
	}

	@SuppressWarnings("rawtypes")
	public List<WTUser> getPMOlistOfKETSDiv() throws Exception {
		List<WTUser> pmoList = new ArrayList<WTUser>();
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup("KETS_PMO");
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					pmoList.add(memberUser);
				}
			}
		}
		return pmoList;
	}

	public Hashtable getNotice() throws Exception {
		Hashtable toHash = new Hashtable();
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup("메일공지");
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					toHash.put(memberUser.getName(), memberUser);
				}
			}
		}
		return toHash;
	}

	public Hashtable getBusiness() throws Exception {
		Hashtable toHash = new Hashtable();
		// 경영기획
		WTGroup pmoGroup = OrganizationServicesHelper.manager.getGroup("경영기획");
		if (pmoGroup != null) {
			Enumeration pmoMembers = OrganizationServicesHelper.manager.members(pmoGroup);
			while (pmoMembers.hasMoreElements()) {
				WTPrincipal wtprincipal = (WTPrincipal) pmoMembers.nextElement();
				if (wtprincipal instanceof WTUser) {
					WTUser memberUser = (WTUser) wtprincipal;
					toHash.put(memberUser.getName(), memberUser);
				}
			}
		}
		return toHash;
	}

	public Vector getProjectDsignMember(TemplateProject project) throws Exception {

		QuerySpec qs = new QuerySpec();
		Class targetClass = ProjectMemberLink.class;
		int targetClassPos = qs.addClassList(targetClass, true);

		long oidValue = CommonUtil.getOIDLongValue(project);

		qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { targetClassPos });

		qs.appendAnd();

		// qs.appendOpenParen();

		// qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PL), new int[] { targetClassPos });
		// qs.appendOr();
		// qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.ROLEMEMBER), new int[] { targetClassPos });
		// qs.appendOr();
		qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.MEMBER), new int[] { targetClassPos });
		// qs.appendCloseParen();

		SearchUtil.setOrderBy(qs, targetClass, targetClassPos, ProjectMemberLink.PJT_MEMBER_TYPE, "pjtMemberType", false);
		QueryResult result = PersistenceHelper.manager.find(qs);

		Hashtable hash = new Hashtable();
		Vector v = new Vector();
		while (result.hasMoreElements()) {
			Object obj[] = (Object[]) result.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) obj[0];
			WTUser user = link.getMember();
			if (hash.containsKey(user.getName())) {
				continue;
			} else {
				hash.put(user.getName(), "");
				v.add(user);
			}

		}
		return v;

	}

	public boolean isPM(TemplateProject project) {
		WTUser wtuser = null;
		try {
			wtuser = (WTUser) SessionHelper.manager.getPrincipal();
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return isPM(project, wtuser);
	}

	public boolean isPM(TemplateProject eproject, WTUser wtuser) {
		return isProjectUser(eproject, wtuser, ProjectUserHelper.PM);
	}

	public void replacePM(TemplateProject templateProject, WTUser user) throws Exception {
		// PM 변경 메일 발송
		ProjectHelper.manager.sendMailPMChange(templateProject, user);

		Config conf = ConfigImpl.getInstance();
		// boolean isERPCheck = conf.getBoolean("ERPCHECK");

		QuerySpec spec = new QuerySpec(ProjectMemberLink.class);
		SearchUtil.appendEQUAL(spec, ProjectMemberLink.class, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(templateProject), 0);
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtMemberType", SearchCondition.EQUAL, ProjectUserHelper.manager.PM), new int[] { 0 });
		QueryResult rs = PersistenceHelper.manager.find(spec);

		if (rs.hasMoreElements()) {
			ProjectMemberLink link = (ProjectMemberLink) rs.nextElement();
			deleteProjectUser(link);
		}

		ProjectUserHelper.manager.setPM(templateProject, user, 0);

		// Team team = TeamHelper.service.getTeam(templateProject);

		// team.deleteRole(Role.toRole("ASSIGNEE"));
		// team.addPrincipal(Role.toRole("ASSIGNEE"), user);

		// JELProject project = (JELProject)templateProject;
		// String state = project.getLifeCycleState().toString();
		// boolean isLatest = project.isLastest();

		// if(isLatest && (state.equals("PROGRESS") || state.equals("ATCOMPLATED"))){
		// if(!PJTInfoERPInterface.sendPMInfo((JELProject)templateProject)){
		// Kogger.debug(getClass(), "ProjectUserHelper.java = " + "funtion:replacePM error");
		// if(isERPCheck){
		// throw new Exception("ERP 전송 실패");
		// }
		// }
		// }

	}
	
	public WTUser getCOST(TemplateProject templateProject) {

		WTUser wtUser = null;
		QuerySpec spec = null;
		QueryResult rs = null;

		try {

			rs = getCOSTProjectMemberLink(templateProject);

			if (rs.hasMoreElements()) {
				Object obj = (Object) rs.nextElement();
				wtUser = ((ProjectMemberLink) obj).getMember();
			}
			return wtUser;

		} catch (Exception e) {
			// Kogger.error(getClass(), e);
		}
		return null;
	}

	public WTUser getPM(TemplateProject templateProject) {

		WTUser wtUser = null;
		QuerySpec spec = null;
		QueryResult rs = null;

		try {

			rs = getPMProjectMemberLink(templateProject);

			if (rs.hasMoreElements()) {
				Object obj = (Object) rs.nextElement();
				wtUser = ((ProjectMemberLink) obj).getMember();
			}
			return wtUser;

		} catch (Exception e) {
			// Kogger.error(getClass(), e);
		}
		return null;
	}
	
	public WTUser getQA(TemplateProject templateProject) {//선행품질보증 ROLE 찾기

		WTUser wtUser = null;
		QuerySpec spec = null;
		QueryResult rs = null;

		try {

			rs = getQAProjectMemberLink(templateProject);

			if (rs.hasMoreElements()) {
				Object obj = (Object) rs.nextElement();
				wtUser = ((ProjectMemberLink) obj).getMember();
			}
			return wtUser;

		} catch (Exception e) {
			// Kogger.error(getClass(), e);
		}
		return null;
	}
	
	public WTUser getElecQC(TemplateProject templateProject) {//전자품질관리 ROLE 찾기

		WTUser wtUser = null;
		QuerySpec spec = null;
		QueryResult rs = null;

		try {

			rs = getQCProjectMemberLink(templateProject);

			if (rs.hasMoreElements()) {
				Object obj = (Object) rs.nextElement();
				wtUser = ((ProjectMemberLink) obj).getMember();
			}
			return wtUser;

		} catch (Exception e) {
			// Kogger.error(getClass(), e);
		}
		return null;
	}
	
	public QueryResult getQCProjectMemberLink(TemplateProject templateProject) throws Exception {
	    	boolean isCarDivisionMode = true;
	    	
	    	if (templateProject instanceof ProductProject) {
		    String teamType = ((ProductProject) templateProject).getTeamType();
		    if ("전자 사업부".equals(teamType)) {
			isCarDivisionMode = false;
		    }
		}
		QuerySpec spec = new QuerySpec(ProjectMemberLink.class);
		SearchUtil.appendEQUAL(spec, ProjectMemberLink.class, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(templateProject), 0);
		spec.appendAnd();
		
		if(isCarDivisionMode){
		    spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtRole", SearchCondition.EQUAL, ProjectUserHelper.manager.CAR_QC), new int[] { 0 });
		}else{
		    spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtRole", SearchCondition.EQUAL, ProjectUserHelper.manager.ELEC_QC), new int[] { 0 });
		}
		
		QueryResult rs = PersistenceHelper.manager.find(spec);

		return rs;
	}
	
	public QueryResult getQAProjectMemberLink(TemplateProject templateProject) throws Exception {

		QuerySpec spec = new QuerySpec(ProjectMemberLink.class);
		QueryResult rs = null;
		SearchUtil.appendEQUAL(spec, ProjectMemberLink.class, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(templateProject), 0);
		boolean isCarDivisionMode = true;
		
		if (templateProject instanceof ProductProject) {
		    String teamType = ((ProductProject) templateProject).getTeamType();
		    if ("전자 사업부".equals(teamType)) {
			isCarDivisionMode = false;
		    }
		}
		
		spec.appendAnd();
		if(isCarDivisionMode){//자동차사업부
		    spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtRole", SearchCondition.EQUAL, ProjectUserHelper.manager.CAR_QA ), new int[] { 0 });
		}else if(isCarDivisionMode){//전자사업부
		    spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtRole", SearchCondition.EQUAL, ProjectUserHelper.manager.ELEC_QA ), new int[] { 0 });
		}else{
		    return rs;
		}
		rs = PersistenceHelper.manager.find(spec);

		return rs;
	}

	public QueryResult getPMProjectMemberLink(TemplateProject templateProject) throws Exception {

		QuerySpec spec = new QuerySpec(ProjectMemberLink.class);
		SearchUtil.appendEQUAL(spec, ProjectMemberLink.class, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(templateProject), 0);
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtMemberType", SearchCondition.EQUAL, ProjectUserHelper.manager.PM), new int[] { 0 });
		QueryResult rs = PersistenceHelper.manager.find(spec);

		return rs;
	}
	
	public QueryResult getCOSTProjectMemberLink(TemplateProject templateProject) throws Exception {
	    	String role = "Team_PRODUCT07";
	    	if(templateProject instanceof ReviewProject){
	    	    role = "Team_REVIEW12";
	    	}
		QuerySpec spec = new QuerySpec(ProjectMemberLink.class);
		SearchUtil.appendEQUAL(spec, ProjectMemberLink.class, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(templateProject), 0);
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtRole", SearchCondition.EQUAL, role), new int[] { 0 });
		QueryResult rs = PersistenceHelper.manager.find(spec);

		return rs;
	}

	public boolean isPL(TemplateProject project) {
		WTUser wtuser = null;
		try {
			wtuser = (WTUser) SessionHelper.manager.getPrincipal();
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return isPL(project, wtuser);
	}

	public boolean isPL(TemplateProject project, WTUser wtuser) {
		return isProjectUser(project, wtuser, ProjectUserHelper.PL);
	}

	public QueryResult getQueryForTeamUsers(TemplateProject templateProject, WTUser wtuser, int roleType) {
		QueryResult result = null;
		try {
			Class targetClass = ProjectMemberLink.class;
			QuerySpec qs = new QuerySpec(targetClass);

			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", roleType), new int[] { 0 });

			if (roleType == ProjectUserHelper.ROLEMEMBER) {
				qs.appendAnd();
				qs.appendWhere(new SearchCondition(targetClass, "pjtRole", false), new int[] { 0 });
			}

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}
			long oidValue = CommonUtil.getOIDLongValue(templateProject);
			qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { 0 });

			if (wtuser != null) {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}

				qs.appendWhere(new SearchCondition(targetClass, "roleBObjectRef.key.id", "=", wtuser.getPersistInfo().getObjectIdentifier().getId()), new int[] { 0 });

			}
			// Kogger.debug(getClass(), "getQueryForTeamUsers :"+qs);
			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public QueryResult getQueryForTeamUsers(TemplateProject templateProject, String roleType) {
		QueryResult result = null;
		try {
			int type = -1;
			if (roleType.equals("PM")) {
				type = ProjectUserHelper.PM;
			} else if (roleType.equals("ROLEMEMBER")) {
				type = ProjectUserHelper.ROLEMEMBER;
			} else if (roleType.equals("MEMBER")) {
				type = ProjectUserHelper.MEMBER;
			} else if (roleType.equals("ONLY_VIEW_MEMBER")) {
				type = ProjectUserHelper.ONLY_VIEW_MEMBER;
			}

			result = getQueryForTeamUsers(templateProject, null, type);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public QueryResult getOnlyAppendMember(TemplateProject templateProject) throws Exception {

		QueryResult rs = new QueryResult();

		QuerySpec qs = new QuerySpec();
		Class targetClass = ProjectMemberLink.class;
		int targetClassPos = qs.addClassList(targetClass, true);

		long oidValue = CommonUtil.getOIDLongValue(templateProject);

		qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { targetClassPos });
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.MEMBER), new int[] { targetClassPos });

		// Kogger.debug(getClass(), qs);
		// SearchUtil.setOrderBy(qs, targetClass, targetClassPos, ProjectMemberLink.PJT_MEMBER_TYPE, "pjtMemberType", false);
		QueryResult result = PersistenceHelper.manager.find(qs);

		/*
		 * Hashtable notMemberType = new Hashtable(); Vector vector = new Vector(); while(result.hasMoreElements()){ Object o[] = (Object[])result.nextElement(); ProjectMemberLink
		 * link = (ProjectMemberLink)o[0]; if(link.getPjtMemberType() != ProjectUserHelper.MEMBER){ notMemberType.put(link.getMember().getName(), link); }else{ String key =
		 * link.getMember().getName(); if(!notMemberType.containsKey(key)){ vector.add(link); } } } for(int i = 0; i < vector.size(); i++){
		 * rs.getObjectVector().addElement(vector.get(i)); }
		 */

		return result;

	}

	public ProjectMemberLink getProjectMemberLink(TemplateProject project, WTUser wtuser) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			oidValue = CommonUtil.getOIDLongValue(wtuser);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);

			QueryResult qr = PersistenceHelper.manager.find(qs);
			// Kogger.debug(getClass(), "######### getProjectMemberLink"+ qs);
			if (qr != null && qr.size() > 0) {
				if (qr.hasMoreElements()) {
					Object aobj[] = (Object[]) qr.nextElement();
					return (ProjectMemberLink) aobj[0];
				}
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTIntrospectionException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}

	public ProjectMemberLink getProjectMemberLink(TemplateProject project, WTUser wtuser, int memberType) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			oidValue = CommonUtil.getOIDLongValue(wtuser);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();

			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType), peopleProjectLinkClassPos);

			QueryResult qr = PersistenceHelper.manager.find(qs);
			// Kogger.debug(getClass(), "######### getProjectMemberLink"+ qs);
			if (qr != null && qr.size() > 0) {
				if (qr.hasMoreElements()) {
					Object aobj[] = (Object[]) qr.nextElement();
					return (ProjectMemberLink) aobj[0];
				}
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTIntrospectionException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}

	public ProjectMemberLink getProjectPeopleLink(TemplateProject project, WTUser wtuser) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			long oidValue = CommonUtil.getOIDLongValue(project);

			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), new int[] { peopleProjectLinkClassPos });

			qs.appendAnd();
			oidValue = CommonUtil.getOIDLongValue(wtuser);

			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), new int[] { peopleProjectLinkClassPos });
			// Kogger.debug(getClass(), "getProjectPeopleLink ==> " + qs );

			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (qr.hasMoreElements()) {
				Object aobj[] = (Object[]) qr.nextElement();
				return (ProjectMemberLink) aobj[0];
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTIntrospectionException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}

	public void deleteProjectUser(TemplateProject project, WTUser user) throws Exception {
		ProjectMemberLink link = getProjectPeopleLink(project, user);
		if (link != null)
			deleteProjectUser(link);
	}

	public void deleteProjectUser(TemplateProject project) {
		QueryResult qr = getProjectAllUser(project);
		if (qr != null && qr.size() > 0) {
			while (qr.hasMoreElements()) {
				Object o[] = (Object[]) qr.nextElement();
				ProjectMemberLink link = (ProjectMemberLink) o[0];
				deleteProjectUser(link);
			}
		}
	}

	public QueryResult getProjectAllUser(TemplateProject project) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			SearchUtil.setOrderBy(qs, peopleProjectLinkClass, peopleProjectLinkClassPos, ProjectMemberLink.PJT_MEMBER_TYPE, "pjtMemberType", false);
			QueryResult result = PersistenceHelper.manager.find(qs);
			if (result != null && result.size() > 0) {
				return result;
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		} catch (WTIntrospectionException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}

	public void deleteProjectUser(ProjectMemberLink link) {

		try {
			PersistenceHelper.manager.delete(link);
		} catch (WTException e) {
			// TODO Auto-generated catch block
			Kogger.error(getClass(), e);
		}

		// int memberType = link.getPjtMemberType();

		// try {
		// /*
		// QueryResult qr = TaskUserHelper.manager.getTaskPeopleLink(link);
		// if(qr != null && qr.size() > 0) {
		// while ( qr.hasMoreElements() ) {
		// Object[] objArr = (Object[])qr.nextElement();
		// TaskMemberLink tpLink = (TaskMemberLink)objArr[0];
		// TaskUserHelper.manager.deleteTaskUser(tpLink);
		// }
		// }
		// */
		//
		// if(memberType == ProjectUserHelper.MEMBER) {
		// WTUser wtuser = link.getMember();
		// TemplateProject project = link.getProject();
		//
		// QueryResult qr = getMemberLinks(project, wtuser);
		// ProjectMemberLink delLink = null;
		// while(qr.hasMoreElements()) {
		// delLink = (ProjectMemberLink)qr.nextElement();
		// PersistenceHelper.manager.delete(delLink);
		// }
		//
		// ProjectHelper.manager.setOutputOwner(project, null, null, wtuser);
		//
		// } else {
		// WTUser wtuser = link.getMember();
		// String roleName = link.getPjtRole();
		//
		// TemplateProject project = link.getProject();
		//
		// PersistenceHelper.manager.delete(link);
		//
		// if(memberType == ProjectUserHelper.ROLEMEMBER){
		//
		// ProjectHelper.manager.setOutputOwner(project, null, roleName);
		//
		// }else if(memberType == ProjectUserHelper.MEMBER){
		//
		// }
		//
		// if(memberType == ProjectUserHelper.ONLY_VIEW_MEMBER){
		// return;
		// }
		//
		//
		//
		// QueryResult rs = getRoleOrPMPLMember(project, wtuser);
		//
		//
		// if(rs.size() == 0){
		// QueryResult qr = getMemberLinks(project, wtuser);
		// ProjectMemberLink delLink = null;
		// while(qr.hasMoreElements()) {
		// delLink = (ProjectMemberLink)qr.nextElement();
		// PersistenceHelper.manager.delete(delLink);
		// }
		//
		// ProjectHelper.manager.setOutputOwner(project, null, null, wtuser);
		// }
		// }
		// TemplateProject project = link.getProject();
		// String state = project.getLifeCycleState().toString();
		// boolean isLatest = link.getProject().isLastest();
		// Config conf = ConfigImpl.getInstance();
		// boolean isERPCheck = conf.getBoolean("ERPCHECK");
		// if(isLatest && (state.equals("PROGRESS") || state.equals("ATCOMPLATED"))){
		// Kogger.debug(getClass(), "sendMemberInfo");
		// //PJTInfoERPInterface.sendMemberInfo((JELProject)project);
		// if(!PJTInfoERPInterface.sendMemberInfo((JELProject)project)){
		// Kogger.debug(getClass(), "ProjectUserHelper.java = " + "funtion:deleteProjectUser error");
		//
		// if(isERPCheck){
		// throw new Exception(" ERP error");
		// }
		//
		// }
		// }
		// } catch (Exception e) {
		// Kogger.error(getClass(), e);
		// } finally {
		// if ( memberType == ProjectUserHelper.PM
		// || memberType == ProjectUserHelper.PL
		// || memberType == ProjectUserHelper.MEMBER ) {
		// // 메일 발송 해야 함...
		// }
		// }
	}

	public boolean isProjectRoleUser(TemplateProject project) {
		WTUser wtuser = null;
		try {
			wtuser = (WTUser) SessionHelper.manager.getPrincipal();
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return isProjectRoleUser(project, wtuser);
	}

	public boolean isProjectRoleUser(TemplateProject project, WTUser wtuser) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			oidValue = CommonUtil.getOIDLongValue(wtuser);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			qs.appendOpenParen();
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PM), peopleProjectLinkClassPos);
			qs.appendOr();
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PL), peopleProjectLinkClassPos);
			qs.appendOr();
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.MEMBER),
					peopleProjectLinkClassPos);
			qs.appendOr();
			// ROlE 유저 추가
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.ROLEMEMBER),
					peopleProjectLinkClassPos);
			qs.appendCloseParen();
			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (qr.size() > 0)
				return true;
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return false;
	}

	public boolean isProjectUser(TemplateProject project) {
		WTUser wtuser = null;
		try {
			wtuser = (WTUser) SessionHelper.manager.getPrincipal();
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return isProjectUser(project, wtuser);
	}

	public boolean isProjectUser(TemplateProject project, WTUser wtuser) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			oidValue = CommonUtil.getOIDLongValue(wtuser);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			qs.appendOpenParen();
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PM), peopleProjectLinkClassPos);
			qs.appendOr();
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PL), peopleProjectLinkClassPos);
			qs.appendOr();
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.MEMBER),
					peopleProjectLinkClassPos);
			qs.appendOr();
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.ROLEMEMBER),
					peopleProjectLinkClassPos);

			qs.appendCloseParen();
			// Kogger.debug(getClass(), ">>>>>>>>>> isProjectUser qs = " + qs);
			QueryResult qr = PersistenceHelper.manager.find(qs);
			Kogger.debug(getClass(), ">>>>>>>>>> isProjectUser qr.size() = " + qr.size());
			if (qr.size() > 0)
				return true;
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return false;
	}

	public boolean isProjectUserAll(TemplateProject project, WTUser wtuser) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			oidValue = CommonUtil.getOIDLongValue(wtuser);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();

			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.NOT_EQUAL, ProjectUserHelper.ONLY_VIEW_MEMBER),
					new int[] { peopleProjectLinkClassPos });

			QueryResult qr = PersistenceHelper.manager.find(qs);
			if (qr.size() > 0)
				return true;
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return false;
	}

	public boolean isProjectUser(TemplateProject project, WTUser wtuser, int memberType) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);

			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType), peopleProjectLinkClassPos);
			qs.appendAnd();
			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			qs.appendAnd();
			oidValue = CommonUtil.getOIDLongValue(wtuser);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleBObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			QueryResult queryresult = PersistenceHelper.manager.find(qs);
			if (queryresult != null && queryresult.size() > 0) {
				return true;
			}
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return false;
	}

	/*
	 * public void copyProjectUserInfo(TemplateProject project, TemplateProject template) { QueryResult members = getMember(template); while ( members != null &&
	 * members.hasMoreElements() ) { Object o[]=(Object[])members.nextElement(); ProjectMemberLink link = (ProjectMemberLink)o[0]; setMember(project, link.getMember()); } }
	 */
	public void copyProjectViewDepartMentInfo(TemplateProject toProject, TemplateProject fromProject) throws Exception {
		QueryResult rs = PersistenceHelper.manager.navigate(fromProject, ProjectViewDepartmentLink.DEPARTMENT_ROLE, ProjectViewDepartmentLink.class);
		while (rs.hasMoreElements()) {
			Department department = (Department) rs.nextElement();
			ProjectViewDepartmentLink viewLink = ProjectViewDepartmentLink.newProjectViewDepartmentLink(toProject, department);
			PersistenceHelper.manager.save(viewLink);
		}
	}

	public HashMap copyProjectUserInfo(TemplateProject toProject, TemplateProject fromProject) throws Exception {

		QueryResult list = ProjectUserHelper.manager.getAllProjectUser(fromProject);
		HashMap map = new HashMap();

		while (list.hasMoreElements()) {
			Object[] objArr = (Object[]) list.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) objArr[0];
			WTUser user = link.getMember();

			ProjectMemberLink newLink = ProjectMemberLink.newProjectMemberLink(toProject, user);
			newLink.setPjtMemberType(link.getPjtMemberType());
			newLink.setPjtRole(link.getPjtRole());
			newLink = (ProjectMemberLink) PersistenceHelper.manager.save(newLink);
			map.put(String.valueOf(PersistenceHelper.getObjectIdentifier(link).getId()), newLink);
		}
		return map;
	}
	
	public HashMap copyProjectUserInfoNotPm(TemplateProject toProject, TemplateProject fromProject) throws Exception {

		QueryResult list = ProjectUserHelper.manager.getAllProjectUser(fromProject);
		HashMap map = new HashMap();

		while (list.hasMoreElements()) {
			Object[] objArr = (Object[]) list.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) objArr[0];
			if(link.getPjtMemberType() == 0){
			    continue;
			}
			WTUser user = link.getMember();

			ProjectMemberLink newLink = ProjectMemberLink.newProjectMemberLink(toProject, user);
			newLink.setPjtMemberType(link.getPjtMemberType());
			newLink.setPjtRole(link.getPjtRole());
			newLink = (ProjectMemberLink) PersistenceHelper.manager.save(newLink);
			map.put(String.valueOf(PersistenceHelper.getObjectIdentifier(link).getId()), newLink);
		}
		return map;
	}

	public List<WTUser> getCFTMemeberList(TemplateProject project) throws Exception {
		List<WTUser> memberList = new ArrayList<WTUser>();
		QueryResult result = ProjectUserHelper.manager.getProjectUser(project, ProjectUserHelper.ROLEMEMBER);
		while (result.hasMoreElements()) {
			Object[] objArr = (Object[]) result.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) objArr[0];
			WTUser member = link.getMember();
			if (!memberList.contains(member))
				memberList.add(link.getMember());
		}
		return memberList;
	}

	public QueryResult getMember(TemplateProject project) {
		return getProjectUser(project, ProjectUserHelper.MEMBER);
	}

	public void setMember(TemplateProject project, WTUser wtuser) {
		setProjectUser(project, wtuser, ProjectUserHelper.MEMBER, project.getPjtHistory());
		/*
		 * ProjectMemberLink link = getProjectPeopleLink(project, wtuser); if ( link != null ) { if ( link.getPjtMemberType() == ProjectUserHelper.PM ) { // 프로젝트 생성자 일 경우에 Member
		 * Role을 추가한다. setProjectUser(project, wtuser, ProjectUserHelper.MEMBER); } } else { setProjectUser(project, wtuser, ProjectUserHelper.MEMBER); }
		 */

	}

	public QueryResult getProjectUser(TemplateProject project, int memberType) {
		try {
			QuerySpec qs = new QuerySpec();
			Class peopleProjectLinkClass = ProjectMemberLink.class;
			int peopleProjectLinkClassPos = qs.addClassList(peopleProjectLinkClass, true);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, memberType), peopleProjectLinkClassPos);
			qs.appendAnd();
			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(peopleProjectLinkClass, "roleAObjectRef.key.id", SearchCondition.EQUAL, oidValue), peopleProjectLinkClassPos);
			SearchUtil.setOrderBy(qs, peopleProjectLinkClass, peopleProjectLinkClassPos, ProjectMemberLink.PJT_MEMBER_TYPE, "pjtMemberType", false);
			QueryResult result = PersistenceHelper.manager.find(qs);
			return result;
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		} catch (WTIntrospectionException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}

	protected void setProjectUser(TemplateProject project, WTUser wtuser, int memberType) {
		try {
			ProjectMemberLink link = ProjectMemberLink.newProjectMemberLink(project, wtuser);
			link.setPjtMemberType(memberType);
			PersistenceHelper.manager.save(link);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		} finally {
			// if ( memberType == ProjectUserHelper.PM
			// || memberType == ProjectUserHelper.PL
			// || memberType == ProjectUserHelper.MEMBER
			// || memberType == ProjectUserHelper.CHECKER
			// || memberType == ProjectUserHelper.SALER ) {
			// // 메일 발송 해야 함...
			// }
		}
	}

	public void checkRoleNLink(TemplateProject project, String roleName) {
		try {
			QueryResult qr = getTeamUsers(project);
			while (qr.hasMoreElements()) {
				ProjectMemberLink legacyLink = (ProjectMemberLink) qr.nextElement();
				if (roleName.equals(legacyLink.getPjtRole())) {
					QueryResult result = getTeamUsers(project);
					int count = 0;
					while (result != null && result.hasMoreElements()) {
						ProjectMemberLink tempLink = (ProjectMemberLink) result.nextElement();
						if (CommonUtil.getOIDString(legacyLink.getMember()).equals(CommonUtil.getOIDString(tempLink.getMember()))) {
							count++;
						}
					}
					if (count > 1) {
						PersistenceHelper.manager.delete(legacyLink);
					} else {
						legacyLink.setPjtRole(null);
						PersistenceHelper.manager.modify(legacyLink);
					}
				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	public QueryResult getTeamUsers(TemplateProject project) {
		return getTeamUsers(project, "");
	}

	public QueryResult getTeamUsers(TemplateProject project, String withPM) {
		QueryResult result = null;
		try {
			Class targetClass = ProjectMemberLink.class;
			QuerySpec qs = new QuerySpec(targetClass);

			qs.appendOpenParen();
			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.MEMBER), new int[] { 0 });
			qs.appendOr();
			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.ROLEMEMBER), new int[] { 0 });
			if (withPM.length() > 0) {
				qs.appendOr();
				qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.PM), new int[] { 0 });
			}
			qs.appendCloseParen();

			qs.appendAnd();
			long oidValue = CommonUtil.getOIDLongValue(project);
			qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { 0 });
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(targetClass, "pjtRole", false), new int[] { 0 });
			result = PersistenceHelper.manager.find(qs);
			Kogger.debug(getClass(), qs);
			if (result != null)
				return result;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public void deleteRefMember(TemplateProject project, WTUser user) throws Exception {
		QueryResult qr = getQueryForTeamUsers(project, user, ProjectUserHelper.ONLY_VIEW_MEMBER);
		if (qr.hasMoreElements()) {
			deleteProjectUser((ProjectMemberLink) qr.nextElement());
		}
	}

	public QueryResult getRoleOrPMPLMember(TemplateProject project, WTUser wtuser) {
		QueryResult result = null;
		try {

			Class targetClass = ProjectMemberLink.class;
			QuerySpec qs = new QuerySpec(targetClass);
			int targetClassPos = 0;
			SearchCondition sc = null;

			sc = new SearchCondition(ProjectMemberLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
			qs.appendWhere(sc, new int[] { 0 });

			qs.appendAnd();
			sc = new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, wtuser.getPersistInfo().getObjectIdentifier().getId());
			qs.appendWhere(sc, new int[] { 0 });

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}
			qs.appendOpenParen();
			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PM), new int[] { targetClassPos });
			qs.appendOr();
			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PL), new int[] { targetClassPos });
			qs.appendOr();
			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.ROLEMEMBER), new int[] { targetClassPos });
			qs.appendCloseParen();

			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public QueryResult getMemberLinks(TemplateProject project, WTUser wtuser) {
		QueryResult result = null;
		try {
			QuerySpec qs = new QuerySpec(ProjectMemberLink.class);
			SearchCondition sc = null;

			sc = new SearchCondition(ProjectMemberLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
			qs.appendWhere(sc, new int[] { 0 });

			qs.appendAnd();
			sc = new SearchCondition(ProjectMemberLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, wtuser.getPersistInfo().getObjectIdentifier().getId());
			qs.appendWhere(sc, new int[] { 0 });

			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public QueryResult getViewDepartmentLink(TemplateProject project, Department dept) {
		QueryResult result = null;
		try {
			QuerySpec qs = new QuerySpec();
			int i = qs.appendClassList(ProjectViewDepartmentLink.class, true);

			SearchCondition sc = null;
			if (project != null) {
				sc = new SearchCondition(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id", SearchCondition.EQUAL, project.getPersistInfo().getObjectIdentifier().getId());
				qs.appendWhere(sc, new int[] { i });
			}

			if (dept != null) {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}

				sc = new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", SearchCondition.EQUAL, dept.getPersistInfo().getObjectIdentifier().getId());
				qs.appendWhere(sc, new int[] { i });

			}

			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public QueryResult getProjectRoleMember(TemplateProject templateProject, WTUser wtuser, String role) {
		QueryResult result = null;
		try {
			Class targetClass = ProjectMemberLink.class;
			QuerySpec qs = new QuerySpec(targetClass);

			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.ROLEMEMBER), new int[] { 0 });

			if (role == null || role.length() == 0) {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}
				qs.appendWhere(new SearchCondition(targetClass, "pjtRole", false), new int[] { 0 });
			} else {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}
				qs.appendWhere(new SearchCondition(targetClass, "pjtRole", "=", role), new int[] { 0 });
			}

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}
			long oidValue = CommonUtil.getOIDLongValue(templateProject);
			qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { 0 });

			if (wtuser != null) {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}

				qs.appendWhere(new SearchCondition(targetClass, "roleBObjectRef.key.id", "=", wtuser.getPersistInfo().getObjectIdentifier().getId()), new int[] { 0 });

			}

			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	/**
	 * 복수 role로 member 검색
	 * 
	 * @param templateProject
	 * @param roles
	 *            - 다중쿼리 지원(구분자:',')
	 * @return ProjectMemberLink objects
	 */
	public QueryResult getProjectRoleMember(TemplateProject templateProject, String roles) {
		QueryResult result = null;
		try {
			Class targetClass = ProjectMemberLink.class;
			QuerySpec qs = new QuerySpec(targetClass);

			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.ROLEMEMBER), new int[] { 0 });

			if (roles == null || roles.length() == 0) {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}
				qs.appendWhere(new SearchCondition(targetClass, "pjtRole", false), new int[] { 0 });
			} else {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}
				KETQueryUtil.setQuerySpecForMultiSearch(qs, targetClass, 0, "pjtRole", roles, true);
			}

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}
			long oidValue = CommonUtil.getOIDLongValue(templateProject);
			qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { 0 });

			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public QueryResult getProjectRoleMember(TemplateProject templateProject, boolean sort) {
		QueryResult result = null;
		try {
			Class targetClass = ProjectMemberLink.class;
			QuerySpec qs = new QuerySpec(targetClass);
			qs.appendWhere(new SearchCondition(targetClass, ProjectMemberLink.PJT_MEMBER_TYPE, "=", ProjectUserHelper.ROLEMEMBER), new int[] { 0 });
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(targetClass, "pjtRole", false), new int[] { 0 });
			qs.appendAnd();
			long oidValue = CommonUtil.getOIDLongValue(templateProject);
			qs.appendWhere(new SearchCondition(targetClass, "roleAObjectRef.key.id", "=", oidValue), new int[] { 0 });
			SearchUtil.setOrderBy(qs, targetClass, 0, ProjectMemberLink.PJT_ROLE, sort);
			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public QueryResult autoCompleteMember(TemplateProject templateProject, String name, int roleType) {
		QueryResult result = null;
		try {
			QuerySpec qs = new QuerySpec();
			int idx_link = qs.appendClassList(ProjectMemberLink.class, true);

			qs.appendWhere(new SearchCondition(ProjectMemberLink.class, ProjectMemberLink.PJT_MEMBER_TYPE, "=", roleType), new int[] { 0 });

			if (roleType == ProjectUserHelper.ROLEMEMBER) {
				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}
				qs.appendWhere(new SearchCondition(ProjectMemberLink.class, "pjtRole", false), new int[] { 0 });
			}

			if (qs.getConditionCount() > 0) {
				qs.appendAnd();
			}

			long oidValue = CommonUtil.getOIDLongValue(templateProject);
			qs.appendWhere(new SearchCondition(ProjectMemberLink.class, "roleAObjectRef.key.id", "=", oidValue), new int[] { 0 });

			if (name != null && name.length() > 0) {
				int idx_people = qs.appendClassList(People.class, false);

				if (qs.getConditionCount() > 0) {
					qs.appendAnd();
				}

				SearchCondition sc = new SearchCondition(new ClassAttribute(ProjectMemberLink.class, "roleBObjectRef.key.id"), "=", new ClassAttribute(People.class,
						"userReference.key.id"));
				sc.setFromIndicies(new int[] { idx_link, idx_people }, 0);
				sc.setOuterJoin(0);
				qs.appendWhere(sc, new int[] { idx_link, idx_people });

				qs.appendAnd();
				qs.appendWhere(new SearchCondition(People.class, "name", SearchCondition.LIKE, "%" + name + "%"), new int[] { idx_people });
			}

			result = PersistenceHelper.manager.find(qs);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return result;
	}

	public static void syncTaskMemberUser(TemplateProject project) throws Exception {

		QueryResult rs = ProjectUserHelper.manager.getAllProjectUser(project);
		Hashtable hash = new Hashtable();

		while (rs.hasMoreElements()) {
			Object o[] = (Object[]) rs.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) o[0];
			if (link.getPjtMemberType() == ProjectUserHelper.TASK_MEMBER) {
				hash.put(link.getMember().getName(), link);
			}
		}

		rs.reset();

		while (rs.hasMoreElements()) {
			Object o[] = (Object[]) rs.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) o[0];

			if (link.getPjtMemberType() != ProjectUserHelper.TASK_MEMBER) {
				String key = link.getMember().getName();
				hash.remove(key);
			}
		}

		Enumeration e = hash.elements();
		while (e.hasMoreElements()) {
			ProjectMemberLink link = (ProjectMemberLink) e.nextElement();
			PersistenceHelper.manager.delete(link);
		}
	}

	public static void settingTaskMaster(ProjectMemberLink link) throws Exception {

		String roleName = link.getPjtRole();

		if (link.getPjtMemberType() != ProjectUserHelper.PM && link.getPjtMemberType() != ProjectUserHelper.ROLEMEMBER) {
			return;
		}

		if (link.getPjtMemberType() == ProjectUserHelper.PM) {
			roleName = "PM";
		}

		QuerySpec spec = new QuerySpec(TemplateTask.class);
		long projectId = link.getProject().getPersistInfo().getObjectIdentifier().getId();

		spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PROJECT_REFERENCE + ".key.id", "=", projectId), new int[] { 0 });
		spec.appendAnd();
		spec.appendWhere(new SearchCondition(TemplateTask.class, TemplateTask.PERSON_ROLE, "=", roleName), new int[] { 0 });

		QueryResult rs = PersistenceHelper.manager.find(spec);

		while (rs.hasMoreElements()) {
			TemplateTask task = (TemplateTask) rs.nextElement();
			TaskUserHelper.deleteMaster(task);
			TaskMemberLink taskMemberLink = TaskMemberLink.newTaskMemberLink(task, link);
			taskMemberLink.setTaskRoleName(TaskUserHelper.DUTYNAME[TaskUserHelper.PL]);
			taskMemberLink.setTaskMemberType(TaskUserHelper.PL);
			PersistenceHelper.manager.save(taskMemberLink);
		}

	}

	public static void settingRoleTaskMember(E3PSProject project) throws Exception {
		QueryResult rs = ProjectUserHelper.manager.getAllProjectUser(project);
		while (rs.hasMoreElements()) {
			Object o[] = (Object[]) rs.nextElement();
			ProjectMemberLink link = (ProjectMemberLink) o[0];
			ProjectUserHelper.settingTaskMaster(link);
		}

	}

}
