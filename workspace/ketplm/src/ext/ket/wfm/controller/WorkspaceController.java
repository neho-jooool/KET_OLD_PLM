package ext.ket.wfm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.lifecycle.LifeCycleManaged;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.pds.StatementSpec;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTAttributeNameIfc;
import wt.workflow.work.WorkItem;
import e3ps.common.code.NumberCode;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectMemberLink;
import e3ps.project.ProjectOutput;
import e3ps.project.TemplateProject;
import e3ps.project.beans.ProjectOutputHelper;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.ProjectUserHelper;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.ReflectUtil;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;
import ext.ket.shared.util.TimerUtil;
import ext.ket.wfm.entity.KETFavorApprovalUser;
import ext.ket.wfm.entity.KETFavorApprovalUserLink;
import ext.ket.wfm.entity.KETTodoDelegateHistory;
import ext.ket.wfm.entity.KETTodoDelegateHistoryDTO;
import ext.ket.wfm.entity.MyDocumentDTO;
import ext.ket.wfm.entity.MyProjectDTO;
import ext.ket.wfm.entity.MyTaskDTO;
import ext.ket.wfm.entity.ProjectOutputDTO;
import ext.ket.wfm.entity.WorkItemDTO;
import ext.ket.wfm.service.KETWorkflowHelper;
import ext.ket.wfm.service.KETWorkspaceHelper;
import ext.ket.wfm.util.WorkSpaceUtil;

/**
 * @클래스명 : WorkspaceController
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/wfm/workspace/*")
public class WorkspaceController {

    @RequestMapping("/listMyTask")
    public void listMyTask(String opt, Model model) throws Exception {

	String searchType = "";

	// NumberCode pmoinwork = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PMOINWORK");
	NumberCode progress = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PROGRESS");
	NumberCode planchange = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PLANCHANGE");
	String searchPjtState = CommonUtil.getOIDLongValue2Str(progress) + "," + CommonUtil.getOIDLongValue2Str(planchange);

	NumberCode taskState = CodeHelper.manager.getNumberCode("MYTASKCOMPLETE", "0");

	String searchState = CommonUtil.getOIDLongValue2Str(taskState);
	if ("plan".equals(opt)) {
	    searchType = opt;
	} else if ("progress".equals(opt)) {
	    searchType = opt;
	} else if ("delay".equals(opt)) {
	    searchType = opt;
	}

	String searchPjtType = "";
	if ("review".equals(opt)) {
	    searchPjtType = "검토";
	} else if ("product".equals(opt)) {
	    searchPjtType = "제품";
	} else if ("mold".equals(opt)) {
	    searchPjtType = "금형";
	} else if ("work".equals(opt)) {
	    searchPjtType = "업무";
	}
	model.addAttribute("searchPjtType", searchPjtType);
	model.addAttribute("searchType", searchType);
	model.addAttribute("searchPjtState", searchPjtState);
	model.addAttribute("searchState", searchState);
    }

    @RequestMapping("/listMyTaskData")
    @ResponseBody
    public Map<String, Object> listMyTaskData(MyTaskDTO dto, HttpServletRequest request) throws Exception {

	// if (dto.getPage() == 0) {
	// SessionUtil.removeAttribute("pageSessionId");
	// }
	// PageControl pageControl = KETWorkspaceHelper.service.findPaging(dto);
	// SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	// QueryResult queryResult = pageControl.getResult();
	// List<MyTaskDTO> list = new ArrayList<MyTaskDTO>();
	// while (queryResult.hasMoreElements()) {
	// Object[] objArr = (Object[]) queryResult.nextElement();
	// list.add(new MyTaskDTO((E3PSTask) objArr[0]));
	// }
	// return EjsConverUtil.convertToDto(list, pageControl);

	// ReflectUtil.toString(dto);

	List<BaseDTO> list = KETWorkspaceHelper.service.find(dto);
	return EjsConverUtil.convertToDto(list);
    }

    @RequestMapping("/listMyTodo")
    public void listMyTodo(String opt, Model model) throws Exception {

	String filterClass = "";
	if ("project".equals(opt)) {
	    filterClass = "e3ps.project";
	} else if ("ecm".equals(opt)) {
	    filterClass = "e3ps.ecm.entity";
	} else if ("dqm".equals(opt)) {
	    filterClass = "ext.ket.dqm.entity,e3ps.ews.entity";
	}
	model.addAttribute("filterClass", filterClass);
    }

    @RequestMapping("/listMyTodoData")
    @ResponseBody
    public Map<String, Object> listMyTodoData(WorkItemDTO workItemDTO) throws Exception {

	ReflectUtil.toString(workItemDTO);
	if (workItemDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	TimerUtil timer = new TimerUtil(getClass().getName());
	timer.setStartPoint("listMyTodoData Query");
	PageControl pageControl = KETWorkspaceHelper.service.findPaging(workItemDTO);
	timer.setEndPoint();
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	QueryResult qr = pageControl.getResult();
	List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
	timer.setStartPoint("create workitem dto");

	System.out.println("queryResult === " + qr.size());
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    String oid = (String) objArr[0];
	    Persistable obj = CommonUtil.getObject(oid);

	    if (obj instanceof WorkItem) {
		list.add(new WorkItemDTO((WorkItem) obj));
	    } else if (obj instanceof KETIssueMaster) {
		list.add(new WorkItemDTO((KETIssueMaster) obj));
	    } else if (obj instanceof KETIssueTask) {
		list.add(new WorkItemDTO((KETIssueTask) obj));
	    } else if (obj instanceof KETInvestMaster) {
		list.add(new WorkItemDTO((KETInvestMaster) obj));
	    } else if (obj instanceof KETInvestTask) {
		list.add(new WorkItemDTO((KETInvestTask) obj));
	    }
	}
	timer.setEndPoint();
	timer.display();
	return EjsConverUtil.convertToDto(list, pageControl);
    }

    /**
     * 동일 산출물 등록 팝업
     * 
     * @param request
     * @return
     * @throws Exception
     * @메소드명 : registerProjectOutputPopup
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 17.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/registerProjectOutputPopup")
    public void registerProjectOutputPopup(HttpServletRequest request, Model model) throws Exception {

	String[] taskoids = request.getParameterValues("taskoids");
	String taskname = "";
	List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
	for (String taskoid : taskoids) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    E3PSTask task = (E3PSTask) CommonUtil.getObject(taskoid);
	    taskname = task.getTaskName();
	    E3PSProject project = (E3PSProject) task.getProject();
	    List<ProjectOutputDTO> outputDTOs = new ArrayList<ProjectOutputDTO>();
	    QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);
	    while (outputQr.hasMoreElements()) {
		Object[] objArr = (Object[]) outputQr.nextElement();
		ProjectOutput output = (ProjectOutput) objArr[0];
		ProjectOutputDTO outputDTO = new ProjectOutputDTO(output);
		outputDTOs.add(outputDTO);
	    }
	    map.put("outputs", outputDTOs);
	    map.put("pjtNo", project.getPjtNo());
	    map.put("pjtName", project.getPjtName());
	    tasks.add(map);
	}
	model.addAttribute("taskname", taskname);
	model.addAttribute("tasks", tasks);
	model.addAttribute("tasksize", tasks.size());
    }

    @RequestMapping("/listMyProject")
    public void listMyProject(String opt, Model model) throws Exception {

	String searchPjtType = "";
	// NumberCode pmoinwork = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PMOINWORK");
	NumberCode progress = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PROGRESS");
	NumberCode planchange = CodeHelper.manager.getNumberCode("MYTASKPJTSTATE", "PLANCHANGE");
	String searchPjtState = CommonUtil.getOIDLongValue2Str(progress) + "," + CommonUtil.getOIDLongValue2Str(planchange);
	if ("review".equals(opt)) {
	    searchPjtType = "검토";
	    // searchPjtState = "";
	} else if ("product".equals(opt)) {
	    searchPjtType = "제품";
	    // searchPjtState = "";
	} else if ("mold".equals(opt)) {
	    searchPjtType = "금형";
	    // searchPjtState = "";
	} else if ("work".equals(opt)) {
	    searchPjtType = "업무";
	    // searchPjtState = "";
	}
	model.addAttribute("searchPjtType", searchPjtType);
	model.addAttribute("searchPjtState", searchPjtState);
    }

    @RequestMapping("/listMyProjectData")
    @ResponseBody
    public Map<String, Object> listMyProjectData(MyProjectDTO dto, HttpServletRequest request) throws Exception {

	ReflectUtil.toString(dto);
	//
	// if (dto.getPage() == 0) {
	// SessionUtil.removeAttribute("pageSessionId");
	// }
	// PageControl pageControl = KETWorkspaceHelper.service.findPaging(dto);
	// SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	// QueryResult queryResult = pageControl.getResult();
	// List<MyProjectDTO> list = new ArrayList<MyProjectDTO>();
	// while (queryResult.hasMoreElements()) {
	// Object[] objArr = (Object[]) queryResult.nextElement();
	// String className = (String) objArr[0];
	// BigDecimal decimal = (BigDecimal) objArr[1];
	// long id = decimal.longValue();
	// E3PSProject project = (E3PSProject) CommonUtil.getObject(className + ":" + id);
	// list.add(new MyProjectDTO((E3PSProject) project));
	// }
	// return EjsConverUtil.convertToDto(list, pageControl);

	List<BaseDTO> list = KETWorkspaceHelper.service.find(dto);
	return EjsConverUtil.convertToDto(list);
    }

    @RequestMapping("/listMyPart")
    public void listMyPart() throws Exception {
    }

    @RequestMapping("/listMyDocument")
    public void listMyDocument() throws Exception {
    }

    @RequestMapping("/listMyDocumentData")
    @ResponseBody
    public Map<String, Object> listMyDocumentData(MyDocumentDTO dto) throws Exception {

	List<BaseDTO> list = KETWorkspaceHelper.service.find(dto);
	return EjsConverUtil.convertToDto(list);
    }

    @RequestMapping("/linkProjectDocumentPopup")
    public void linkProjectDocumentPopup(String outputoid, Model model) throws Exception {

	String[] outputoids = outputoid.split(",");
	String categoryCode = "";
	String categoryName = "";
	for (String poid : outputoids) {
	    ProjectOutput output = (ProjectOutput) CommonUtil.getObject(poid);
	    QuerySpec query = new QuerySpec(KETDocumentCategory.class);
	    query.appendWhere(new SearchCondition(KETDocumentCategory.class, "categoryCode", "=", output.getOutputKeyType()), new int[] { 0 });
	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	    if (qr.hasMoreElements()) {
		KETDocumentCategory category = (KETDocumentCategory) qr.nextElement();
		categoryCode = category.getCategoryCode();
		categoryName = category.getCategoryName();
	    }
	}
	model.addAttribute("outputoids", outputoids);
	model.addAttribute("categoryCode", categoryCode);
	model.addAttribute("categoryName", categoryName);
    }

    @RequestMapping("/linkProjectDocument")
    @ResponseBody
    public boolean linkProjectDocument(String docoid, String outputoid) throws Exception {

	boolean flag = true;
	try {
	    KETProjectDocument document = (KETProjectDocument) CommonUtil.getObject(docoid);
	    String[] outputoids = outputoid.split(",");
	    for (String poid : outputoids) {
		ProjectOutput output = (ProjectOutput) CommonUtil.getObject(poid);
		KETDocumentOutputLink link = KETDocumentOutputLink.newKETDocumentOutputLink(document, output);
		PersistenceHelper.manager.save(link);
		ProjectOutputHelper.manager.registryProjectOutput(output, document);

		// 태스크 현재 진행률 정보 업데이트
		output = (ProjectOutput) PersistenceHelper.manager.save(output);
		E3PSTask task = (E3PSTask) output.getTask();
		ProjectTaskHelper.updateCompletionFromOutput(task);

		// int sum_completion = 0;
		// int continueCount = 0;
		// QueryResult rs = ProjectOutputHelper.manager.getTaskOutput(task);
		// while (rs.hasMoreElements()) {
		// Object o[] = (Object[]) rs.nextElement();
		// ProjectOutput po = (ProjectOutput) o[0];
		// if (po.getCompletion() < 0 || !po.isIsPrimary()) {
		// continueCount++;
		// continue;
		// }
		// sum_completion += po.getCompletion();
		// }
		//
		// int completion = 0;
		// if (rs.size() - continueCount != 0) {
		// completion = sum_completion / (rs.size() - continueCount);
		// }
		//
		// task.setTaskCompletion(completion);
		// task = (E3PSTask) PersistenceHelper.manager.save(task);

	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}

	return flag;
    }

    @RequestMapping("/deleteProjectOutput")
    @ResponseBody
    public boolean deleteProjectOutput(String outputoid) throws Exception {

	boolean flag = true;
	try {
	    ReferenceFactory rf = new ReferenceFactory();
	    ProjectOutput output = (ProjectOutput) rf.getReference(outputoid).getObject();
	    ProjectOutputHelper.deleteProjectOutput(output);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    flag = false;
	}
	return flag;
    }

    @RequestMapping("/searchUser")
    @ResponseBody
    public List<PeopleData> searchUser(String paramUserName) throws Exception {

	List<PeopleData> list = new ArrayList<PeopleData>();
	if (StringUtil.checkString(paramUserName)) {
	    QuerySpec spec = new QuerySpec();
	    int u_idx = spec.appendClassList(WTUser.class, false);
	    int p_idx = spec.appendClassList(People.class, true);
	    spec.appendWhere(new SearchCondition(new ClassAttribute(WTUser.class, WTAttributeNameIfc.ID_NAME), "=", new ClassAttribute(People.class, "userReference.key.id")),
		    new int[] { u_idx, p_idx });
	    SearchUtil.appendBOOLEAN(spec, WTUser.class, WTUser.DISABLED, SearchCondition.IS_FALSE, u_idx);
	    SearchUtil.appendBOOLEAN(spec, People.class, People.IS_DISABLE, SearchCondition.IS_FALSE, p_idx);
	    SearchUtil.appendSLIKE(spec, WTUser.class, WTUser.FULL_NAME, paramUserName, u_idx, false);
	    SearchUtil.setOrderBy(spec, WTUser.class, u_idx, WTUser.FULL_NAME, false);
	    Kogger.debug(getClass(), spec);
	    QueryResult result = PersistenceHelper.manager.find((StatementSpec) spec);
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		PeopleData data = new PeopleData(objects[0]);
		list.add(data);
	    }
	}
	return list;
    }

    @RequestMapping("/searchMyProject")
    @ResponseBody
    public List<Map<String, String>> searchMyProject() throws Exception {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	MyProjectDTO dto = new MyProjectDTO();
	dto.setCommand("listMyProject");
	dto.setSearchPjtState("671979022");
	QueryResult result = KETWorkspaceHelper.service.getMyProjectList(dto);
	while (result.hasMoreElements()) {
	    Object[] objArr = (Object[]) result.nextElement();
	    String className = String.valueOf(objArr[0]);
	    String id = String.valueOf(objArr[1]);
	    String oid = className + ":" + id;
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(oid);
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("oid", oid);
	    map.put("projectname", project.getPjtName());
	    WTUser pm = ProjectUserHelper.manager.getPM(project);
	    map.put("pm", (pm != null) ? pm.getFullName() : "");
	    list.add(map);
	}
	Collections.sort(list, ComparatorUtil.MYPROJECTNAMESORT);
	return list;
    }

    @RequestMapping("/searchProjectCFTMember")
    @ResponseBody
    public List<Map<String, String>> searchProjectCFTMember(String projectoid) throws Exception {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	if (StringUtil.checkString(projectoid)) {
	    E3PSProject project = (E3PSProject) CommonUtil.getObject(projectoid);
	    // QueryResult result = ProjectUserHelper.manager.getProjectRoleMember(project, false);
	    QueryResult result = ProjectUserHelper.manager.getProjectUser(project);
	    while (result.hasMoreElements()) {
		Object[] objects = (Object[]) result.nextElement();
		ProjectMemberLink memberLink = (ProjectMemberLink) objects[0];
		PeopleData data = new PeopleData(memberLink.getMember());
		String roleName = memberLink.getPjtRole();
		int memberType = memberLink.getPjtMemberType();
		// logger.debug(roleName);
		if (!StringUtil.checkString(roleName)) {
		    if (memberType == ProjectUserHelper.PM)
			roleName = "PM";
		    else if (memberType == ProjectUserHelper.PL)
			roleName = "PL";
		    else
			roleName = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02757");
		} else {
		    roleName = Role.toRole(roleName).getDisplay(KETMessageService.getMessageService().getLocale());
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", data.id);
		map.put("name", data.name);
		map.put("departmentName", data.departmentName);
		map.put("duty", roleName);
		map.put("memberType", String.valueOf(memberType));
		list.add(map);
	    }
	    Collections.sort(list, ComparatorUtil.PJTMEMBERSORT);
	}
	return list;
    }

    @RequestMapping("/searchProjectAllMember")
    @ResponseBody
    public List<Map<String, String>> searchProjectAllMember(String projectoid) throws Exception {
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	if (StringUtil.checkString(projectoid)) {
	    TemplateProject project = (TemplateProject) CommonUtil.getObject(projectoid);
	    QueryResult result = KETWorkspaceHelper.service.getTeamUsers(project, "");
	    while (result.hasMoreElements()) {
		ProjectMemberLink memberLink = (ProjectMemberLink) result.nextElement();
		PeopleData data = new PeopleData(memberLink.getMember());
		// String roleName = memberLink.getPjtRole();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", data.id);
		map.put("name", data.name);
		map.put("departmentName", data.departmentName);
		// map.put("duty", Role.toRole(roleName).getDisplay(KETMessageService.getMessageService().getLocale()));
		list.add(map);
	    }
	}
	return list;
    }

    @RequestMapping("/searchMyFavorApprovalLine")
    @ResponseBody
    public List<PeopleData> searchMyFavorApprovalLine() throws Exception {

	List<PeopleData> list = new ArrayList<PeopleData>();
	KETFavorApprovalUser favorApprovalUser = KETWorkflowHelper.service.getMyFavorApprovalUser();
	if (favorApprovalUser != null) {
	    QueryResult result = PersistenceHelper.manager.navigate(favorApprovalUser, KETFavorApprovalUserLink.PEOPLE_ROLE, KETFavorApprovalUserLink.class, true);
	    while (result.hasMoreElements()) {
		People people = (People) result.nextElement();
		PeopleData data = new PeopleData(people);
		list.add(data);
	    }
	}
	return list;
    }

    @RequestMapping("/addFavorApprovalUser")
    @ResponseBody
    public boolean addFavorApprovalUser(String[] userids) throws Exception {

	return KETWorkflowHelper.service.createMyFavorApprovalUser(userids);// .split(","));
    }

    @RequestMapping("/deleteFavorApprovalUser")
    @ResponseBody
    public boolean deleteFavorApprovalUser(String[] userids) throws Exception {

	return KETWorkflowHelper.service.deleteMyFavorApprovalUser(userids);
    }

    /**
     * TO-DO 수신확인
     * 
     * @param workItemoids
     * @return
     * @throws Exception
     * @메소드명 : doAcknowledgement
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/doAcknowledgement")
    @ResponseBody
    public String doAcknowledgement(String[] workItemoids) throws Exception {

	String msg = "";
	try {
	    for (String workItemoid : workItemoids) {
		WorkItem item = (WorkItem) CommonUtil.getObject(workItemoid);
		LifeCycleManaged lcm = (LifeCycleManaged) item.getPrimaryBusinessObject().getObject();
		KETWorkspaceHelper.service.saveReceiptDate(lcm, DateUtil.getCurrentTimestamp());
	    }
	} catch (Exception e) {
	    msg = e.getClass().getName() + ": " + e.getLocalizedMessage();
	    Kogger.error(getClass(), e);
	}
	return msg;
    }

    /**
     * TO-DO 작업위임
     * 
     * @param workItemoids
     * @param model
     * @throws Exception
     * @메소드명 : delegateTodoWorkItemForm
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/delegateTodoWorkItemForm")
    public void delegateTodoWorkItemForm(String[] workItemoids, Model model) throws Exception {

	model.addAttribute("workItemoids", workItemoids);
    }

    /**
     * TO-DO 작업위임
     * 
     * @param workItemoids
     * @param delegateUser
     * @param reason
     * @return
     * @throws Exception
     * @메소드명 : doDelegateTodoWorkItem
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/doDelegateTodoWorkItem")
    @ResponseBody
    public String doDelegateTodoWorkItem(@RequestParam(value = "workItemoids[]") String[] workItemoids, String delegateUser, String reason) throws Exception {

	String msg = "";
	try {
	    WTPrincipal newOwner = (WTPrincipal) CommonUtil.getObject(delegateUser);
	    for (String workItemoid : workItemoids) {
		WorkItem workItem = (WorkItem) CommonUtil.getObject(workItemoid);
		Persistable pbo = workItem.getPrimaryBusinessObject().getObject();
		if (pbo != null) {
		    if (pbo instanceof KETProdChangeActivity) {
			KETProdChangeActivity tempEca = (KETProdChangeActivity) pbo;
			// 설변 도면, 부품은 한꺼번에 담당자 변경한다.
			if ("1".equals(tempEca.getActivityType()) || "2".equals(tempEca.getActivityType())) {
			    List<WorkItem> list = KETWorkspaceHelper.service.getRelatedWorkItem(workItem, "PROD");
			    for (WorkItem relatedWorkItem : list) {
				KETWorkspaceHelper.service.delegateTodoWorkItem(relatedWorkItem, newOwner, reason);
			    }
			} else {
			    KETWorkspaceHelper.service.delegateTodoWorkItem(workItem, newOwner, reason);
			}
		    } else if (pbo instanceof KETMoldChangeActivity) {
			KETMoldChangeActivity tempEca = (KETMoldChangeActivity) pbo;
			// 설변 도면, 부품은 한꺼번에 담당자 변경한다.
			if ("1".equals(tempEca.getActivityType()) || "2".equals(tempEca.getActivityType())) {
			    List<WorkItem> list = KETWorkspaceHelper.service.getRelatedWorkItem(workItem, "MOLD");
			    for (WorkItem relatedWorkItem : list) {
				KETWorkspaceHelper.service.delegateTodoWorkItem(relatedWorkItem, newOwner, reason);
			    }
			} else {
			    KETWorkspaceHelper.service.delegateTodoWorkItem(workItem, newOwner, reason);
			}
		    } else {
			KETWorkspaceHelper.service.delegateTodoWorkItem(workItem, newOwner, reason);
		    }
		} else {
		    KETWorkspaceHelper.service.delegateTodoWorkItem(workItem, newOwner, reason);
		}
	    }
	} catch (Exception e) {
	    msg = e.getClass().getName() + ": " + e.getLocalizedMessage();
	    Kogger.error(getClass(), e);
	}
	return msg;
    }

    /**
     * TO-DO 작업위임 이력조회
     * 
     * @param workItemoid
     * @param model
     * @throws Exception
     * @메소드명 : viewDelegateHistoryPopup
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/viewDelegateHistoryPopup")
    public void viewDelegateHistoryPopup(String workItemoid, Model model) throws Exception {

	QuerySpec spec = new QuerySpec();
	int idx = spec.appendClassList(KETTodoDelegateHistory.class, true);
	SearchUtil.appendEQUAL(spec, KETTodoDelegateHistory.class, "workitemReference.key.id", CommonUtil.getOIDLongValue(workItemoid), idx);
	SearchUtil.setOrderBy(spec, KETTodoDelegateHistory.class, idx, "thePersistInfo.createStamp", true);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) spec);
	ArrayList<KETTodoDelegateHistoryDTO> list = new ArrayList<KETTodoDelegateHistoryDTO>();
	while (qr.hasMoreElements()) {
	    Object[] objects = (Object[]) qr.nextElement();
	    KETTodoDelegateHistory delegateHistory = (KETTodoDelegateHistory) objects[0];
	    list.add(new KETTodoDelegateHistoryDTO(delegateHistory));
	}
	model.addAttribute("list", list);
    }

    @RequestMapping("/listTotalWorkData")
    @ResponseBody
    public Map<String, Object> listTotalWorkData(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) throws Exception {

	Map<String, Object> resMap = new HashMap<String, Object>();

	try {
	    List<Map<String, Object>> totalList = WorkSpaceUtil.manager.getTotalWorkList(reqMap);
	    resMap.put("totalList", totalList);
	    resMap.put("result", true);

	} catch (Exception e) {
	    resMap.put("message", e.getLocalizedMessage());
	    resMap.put("result", false);
	}

	return resMap;
    }

    @ResponseBody
    @RequestMapping("/createWorkListExcel")
    public Map<String, Object> createWorkListExcel(@RequestParam Map<String, Object> reqMap, HttpServletRequest req) {

	Map<String, Object> resMap = new HashMap<String, Object>();
	try {

	    resMap = WorkSpaceUtil.manager.createWorkListExcel(reqMap);
	    resMap.put("result", true);
	} catch (Exception e) {
	    e.printStackTrace();
	    resMap.put("result", false);
	    resMap.put("message", e.getLocalizedMessage());
	}

	return resMap;
    }

    @RequestMapping("/viewListTotalWork")
    public void viewListTotalWork(Model model) throws Exception {
    }
}
