package ext.ket.sales.project.controller;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.Base64;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.dms.service.KETDmsHelper;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.groupware.company.PeopleData;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.edm.stamping.util.ZipUtil;
import ext.ket.issue.util.IssueUtil;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesCSMeetingManage;
import ext.ket.sales.entity.KETSalesCSMeetingManageLink;
import ext.ket.sales.entity.KETSalesCompanyLink;
import ext.ket.sales.entity.KETSalesPartClassLink;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sales.entity.KETSalesProjectDTO;
import ext.ket.sales.entity.KETSalesTask;
import ext.ket.sales.entity.KETSalesTaskHistory;
import ext.ket.sales.entity.KETSalesTaskHistoryLink;
import ext.ket.sales.service.SalesHelper;
import ext.ket.sales.service.internal.salesDao;
import ext.ket.sales.util.CSProjectSortEnum;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.drm.DRMHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.shared.util.SearchUtil;
import ext.ket.shared.util.SessionUtil;

/**
 * @클래스명 : SalesController
 * @작성자 : 황정태
 * @작성일 : 2016. 9. 05.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
@Controller
@RequestMapping("/sales/project/*")
public class SalesController {

    private salesDao dao = new salesDao();

    @RequestMapping("/salesProjectList")
    public void salesProjectList(KETSalesProjectDTO paramObject, Model model) throws Exception {
	model.addAttribute("isAdmin", CommonUtil.isAdmin());

	model.addAttribute("createDateFrom", paramObject.getCreateDateFrom()); // 시작년월
	model.addAttribute("salesStateName", paramObject.getSalesStateName()); // 프로젝트 상태(코드)
	model.addAttribute("mainCategoryName", paramObject.getMainCategoryName()); // 제품군 대분류
	model.addAttribute("nationName", paramObject.getNationName()); // 지역(국가)
	model.addAttribute("failtypecode", paramObject.getFailtypecode()); // 실패유형
	model.addAttribute("obtainCompany", paramObject.getObtainCompany()); // 수주사
	model.addAttribute("salesStateOid", paramObject.getSalesStateOid()); // 프로젝트 상태(oid)
    }

    @RequestMapping("/salesCSProjectList")
    public void salesCSProjectList() throws Exception {
    }

    @RequestMapping("/listSalesProjectPopup")
    public void listSalesProjectPopup(@RequestParam(value = "fncall", required = false) String fncall, String mode, boolean isCLock,
	    Model model) throws Exception {
	model.addAttribute("mode", mode);
	model.addAttribute("fncall", fncall);

	if (isCLock && !CommonUtil.isAdmin() && !CommonUtil.isMember("영업CS관리")) {
	    WTUser sessionUser = (WTUser) SessionHelper.manager.getPrincipal();
	    model.addAttribute("lockCreatorName", sessionUser.getFullName());
	    model.addAttribute("lockCreatorOid", CommonUtil.getOIDString(sessionUser));
	}
    }

    @RequestMapping("/createSalesProjectForm")
    public void createSalesProjectForm() throws Exception {
    }

    @RequestMapping("/createSalesCSForm")
    public void createSalesCSForm() throws Exception {
    }

    @RequestMapping("/salesTaskHistoryForm")
    public void salesTaskHistoryForm(String oid, Model model) throws Exception {

	KETSalesTask task = (KETSalesTask) CommonUtil.getObject(oid);
	// KETSalesTaskHistoryLink taskLink = null;
	KETSalesTaskHistory taskHistory = null;
	QueryResult qr = PersistenceHelper.manager.navigate(task, "Taskhistory", KETSalesTaskHistoryLink.class);

	HashMap<String, String> salesTaskMap = null;
	List<Map<String, String>> salesTaskList = new ArrayList<Map<String, String>>();
	while (qr.hasMoreElements()) {
	    taskHistory = (KETSalesTaskHistory) qr.nextElement();
	    // taskHistory = (KETSalesTaskHistory)taskLink.getTaskhistory();

	    salesTaskMap = new HashMap<String, String>();

	    salesTaskMap.put("planwebEditor", (String) taskHistory.getPlanwebEditor());
	    salesTaskMap.put("ProblemwebEditor", (String) taskHistory.getProblemwebEditor());
	    salesTaskMap.put("PropelwebEditor", (String) taskHistory.getPropelwebEditor());
	    salesTaskMap.put("version", (String) taskHistory.getTaskVersion());
	    salesTaskMap.put("stepNo", (String) taskHistory.getTaskVersion());// 정렬용 temp 변수
	    salesTaskList.add(salesTaskMap);
	}
	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();

	Collections.sort(salesTaskList, ComparatorUtil.SALESPROJECTSTEP);
	Collections.reverse(salesTaskList);// 내림차순

	SalesDTO.setSalesTaskList(salesTaskList);

	model.addAttribute("sales", SalesDTO);
    }

    @RequestMapping("/CSdateUpdate")
    @ResponseBody
    public String CSdateUpdate(String csStartDate, String csEndDate, String csNextStartDate, String oid) throws Exception {

	try {
	    KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(oid);

	    Timestamp csStartDate_ = DateUtil.getTimestampFormat(csStartDate, "yyyy-MM-dd");
	    Timestamp csEndDate_ = DateUtil.getTimestampFormat(csEndDate, "yyyy-MM-dd");
	    Timestamp csNextStartDate_ = DateUtil.getTimestampFormat(csNextStartDate, "yyyy-MM-dd");

	    manage.setCsStartDate(csStartDate_);
	    manage.setCsEndDate(csEndDate_);
	    manage.setNextStartdate(csNextStartDate_);

	    PersistenceHelper.manager.save(manage);
	} catch (Exception e) {
	    return "Fail";
	}

	return "S";
    }

    @RequestMapping("/getCSDeptList")
    @ResponseBody
    public List<Map<String, Object>> getCSDeptList(String oid, String deptNo) throws Exception {

	List<Map<String, Object>> rsltList = new ArrayList<Map<String, Object>>();
	Map<String, Object> rslt = null;

	KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(oid);
	KETSalesCSMeetingManageLink manageLink = null;

	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();
	KETSalesProject sales = null;
	// QueryResult qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);

	QueryResult qr = SalesHelper.service.getCsprojectSortedResult(CommonUtil.getFullOIDString(manage));

	KETSalesCSMeetingApproval approve = SalesHelper.service.getCSApprovalTarget(manage, deptNo);
	String stateName = "";

	if (approve != null) {
	    stateName = approve.getLifeCycleState().toString();
	}

	Department targetDept;

	while (qr.hasMoreElements()) {

	    Object[] obj = (Object[]) qr.nextElement();
	    manageLink = (KETSalesCSMeetingManageLink) obj[1];

	    if (!deptNo.equals("ALL") && !deptNo.equals(manageLink.getDeptSortNo())) {
		continue;
	    }
	    sales = manageLink.getCsProject();
	    targetDept = (Department) sales.getSalesTeam();
	    rslt = new HashMap<String, Object>();
	    rslt.put("projectNo", sales.getProjectNo());
	    rslt.put("projectName", sales.getProjectName());

	    NumberCode num = (NumberCode) sales.getRankType();// 중요도
	    String rankName = num.getName();
	    rslt.put("rankName", rankName);
	    num = (NumberCode) sales.getNationType(); // 국가
	    String nationName = num.getName();
	    rslt.put("nationName", nationName);
	    OEMProjectType oem = (OEMProjectType) sales.getOemType(); // 차종
	    String oemName = oem.getName();
	    rslt.put("oemName", oemName);
	    rslt.put("oid", CommonUtil.getOIDString(sales));
	    rslt.put("sortNo", manageLink.getDeptSortNo());// 부서별로 순서가정해져있다. CSProjectSortEnum.java 참고
	    rslt.put("mysortNo", manageLink.getSortNo());
	    rslt.put("oids", CommonUtil.getOIDString(manageLink));
	    rslt.put("dept", targetDept.getName());

	    num = (NumberCode) sales.getSalesState();// 프로젝트 상태
	    String salesStateName = "";

	    if (num != null) {
		salesStateName = num.getName();
	    }
	    rslt.put("state", salesStateName);
	    rslt.put("stateName", stateName);
	    rslt.put("approveOid", CommonUtil.getOIDString(approve));

	    rsltList.add(rslt);

	}

	// Collections.sort(rsltList, ComparatorUtil.SALESMANGESORT);
	SalesDTO.setCsPorjectList(rsltList);

	return rsltList;
    }

    @RequestMapping("/viewSalesCSForm")
    public void viewSalesCSForm(String oid, Model model) throws Exception {

	WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	PeopleData people = new PeopleData((user));
	Department dept = (Department) people.department;

	boolean chiefCheckFlag = false;
	boolean adminCheckFlag = false;
	boolean approveAuthFlag = false;

	KETSalesCSMeetingManage manage = null;
	KETSalesCSMeetingApproval approve = null;
	String approveOid = "";
	String stateName = "";
	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();
	String approveDept = "";
	if (CommonUtil.getObject(oid) instanceof KETSalesCSMeetingApproval) {
	    approve = (KETSalesCSMeetingApproval) CommonUtil.getObject(oid);

	    approveDept = approve.getDeptSort();
	    manage = (KETSalesCSMeetingManage) CommonUtil.getObject(approve.getMeetingManageLinkInfo());
	} else {
	    manage = (KETSalesCSMeetingManage) CommonUtil.getObject(oid);

	    if (!CommonUtil.isAdmin() && !CommonUtil.isMember("영업CS관리")) {
		approve = SalesHelper.service.getCSApprovalTarget(manage, "");// 결재요청자의 부서에 해당하는 데이터만 가져옴
	    } else {
		adminCheckFlag = true;
	    }

	    if (StringUtils.isNotEmpty(people.chief)) {
		chiefCheckFlag = true;
	    }

	}
	if (approve != null) {
	    stateName = approve.getLifeCycleState().toString();
	    approveOid = CommonUtil.getOIDString(approve);

	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(approve);
	    if (master != null && stateName.equals("UNDERREVIEW")) {// 마지막 결재자의 검토대상일때는 CS회의순서를 변경할수있도록 (센터장용)
		QuerySpec query = new QuerySpec();
		SearchCondition sc = null;

		int idxApprovalHistory = query.appendClassList(KETWfmApprovalHistory.class, true);

		sc = new SearchCondition(KETWfmApprovalHistory.class, "appMasterReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(master));
		query.appendWhere(sc, new int[] { idxApprovalHistory });

		SearchUtil
		        .setOrderBy(query, KETWfmApprovalHistory.class, idxApprovalHistory, "thePersistInfo.theObjectIdentifier.id", true);

		QueryResult qr = PersistenceHelper.manager.find(query);
		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) tempObj[0];
		    String activityName = "";

		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토") && history.isLast()) {
			WTUser activityUser = (WTUser) history.getOwner().getPrincipal();
			if (CommonUtil.getOIDString(activityUser).equals(CommonUtil.getOIDString(user))) {
			    approveAuthFlag = true;
			}
		    }
		}

	    }

	} else {
	    // stateName = "ADMIN";
	}

	SalesDTO.setDegree(manage.getDegree());
	SalesDTO.setYear(manage.getYear());
	SalesDTO.setMonth(manage.getMonth());
	SalesDTO.setCsClose(manage.getCsClose());
	SalesDTO.setCsStartDate(DateUtil.getDateString(manage.getCsStartDate(), "date"));
	SalesDTO.setCsEndDate(DateUtil.getDateString(manage.getCsEndDate(), "date"));
	SalesDTO.setCsNextStartDate(DateUtil.getDateString(manage.getNextStartdate(), "date"));
	SalesDTO.setOid(oid);
	SalesDTO.setApproveOid(approveOid);
	SalesDTO.setStateName(stateName);

	QueryResult qr = SalesHelper.service.getCsprojectSortedResult(CommonUtil.getFullOIDString(manage));
	KETSalesProject sales = null;

	List<Map<String, Object>> rsltList = new ArrayList<Map<String, Object>>();

	Map<String, Object> rslt = null;
	KETSalesCSMeetingManageLink manageLink = null;

	ArrayList<Department> childDept = DepartmentHelper.manager.getChildDeptTree(dept);
	Department targetDept;

	boolean VeiwAuth = false;

	TreeSet<String> tSet = new TreeSet<String>();// 중복제거위함

	while (qr.hasMoreElements()) {

	    Object[] obj = (Object[]) qr.nextElement();

	    manageLink = (KETSalesCSMeetingManageLink) obj[1];

	    tSet.add(manageLink.getDeptSortNo());

	    sales = manageLink.getCsProject();
	    targetDept = (Department) sales.getSalesTeam();

	    if (CommonUtil.getObject(oid) instanceof KETSalesCSMeetingApproval && StringUtils.isNotEmpty(approveDept) && !adminCheckFlag) {
		if (!manageLink.getDeptSortNo().equals(approveDept)) {
		    continue;
		}
	    } else {

		if (targetDept != null && !adminCheckFlag) {
		    if (targetDept.getCode().equals(dept.getCode())) {
			VeiwAuth = true;
		    } else {
			VeiwAuth = false;

			for (Department child : childDept) {
			    if (child.getCode().equals(targetDept.getCode())) {
				VeiwAuth = true;
			    }
			}
		    }

		}

		// 내 하위 조직에 속한 프로젝트만을 보여준다
		if (!VeiwAuth && !adminCheckFlag) {
		    continue;
		}
	    }

	    rslt = new HashMap<String, Object>();
	    rslt.put("projectNo", sales.getProjectNo());
	    rslt.put("projectName", sales.getProjectName());

	    NumberCode num = (NumberCode) sales.getRankType();// 중요도
	    String rankName = num.getName();
	    rslt.put("rankName", rankName);
	    num = (NumberCode) sales.getNationType(); // 국가
	    String nationName = num.getName();
	    rslt.put("nationName", nationName);
	    OEMProjectType oem = (OEMProjectType) sales.getOemType(); // 차종
	    String oemName = oem.getName();
	    rslt.put("oemName", oemName);
	    rslt.put("oid", CommonUtil.getOIDString(sales));
	    rslt.put("sortNo", manageLink.getDeptSortNo());// 부서별로 순서가정해져있다. CSProjectSortEnum.java 참고
	    rslt.put("mysortNo", manageLink.getSortNo());
	    rslt.put("oids", CommonUtil.getOIDString(manageLink));
	    rslt.put("fileOid", manageLink.getCsFile());
	    rslt.put("fileSortOption", manageLink.getFileSortOption());
	    rslt.put("dept", targetDept.getName());

	    num = (NumberCode) sales.getSalesState();// 프로젝트 상태
	    String salesStateName = "";

	    if (num != null) {
		salesStateName = num.getName();
	    }

	    rslt.put("state", salesStateName);

	    rsltList.add(rslt);

	}

	// Collections.sort(rsltList, ComparatorUtil.SALESMANGESORT);
	SalesDTO.setCsPorjectList(rsltList);
	SalesDTO.setOid(oid);

	List<Map<String, String>> deptList = new ArrayList<Map<String, String>>();
	Map<String, String> deptMap = null;

	Iterator<String> it = tSet.iterator();
	String sortNo = "";
	String teamName = "";

	while (it.hasNext()) {

	    sortNo = it.next();
	    CSProjectSortEnum team = CSProjectSortEnum.toTeamTypeEnumBySort(sortNo);
	    teamName = team.getTeam();

	    deptMap = new HashMap<String, String>();
	    deptMap.put("sortNo", sortNo);
	    deptMap.put("team", teamName);
	    deptList.add(deptMap);
	}

	Collections.sort(deptList, ComparatorUtil.SALESMANGESORT);
	SalesDTO.setCsDeptList(deptList);

	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(manage));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(manage));

	model.addAttribute("sales", SalesDTO);
	model.addAttribute("chiefCheckFlag", chiefCheckFlag);
	model.addAttribute("adminCheckFlag", adminCheckFlag);
	model.addAttribute("approveAuthFlag", approveAuthFlag);

    }

    public KETSalesProjectDTO getTaskInfo(KETSalesProjectDTO SalesDTO) {
	int idx = Integer.parseInt(SalesDTO.getIdx()) - 1;

	String propelwebEditor = "";
	String propelwebEditorText = "";

	String problemwebEditor = "";
	String problemwebEditorText = "";

	String planwebEditor = "";
	String planwebEditorText = "";

	String plancheck_arr[] = SalesDTO.getPlanCheck();
	String plancheck = "";

	String propelwebEditor_arr[] = SalesDTO.getPropelwebEditor();
	String propelwebEditorText_arr[] = SalesDTO.getPropelwebEditorText();

	String problemwebEditor_arr[] = SalesDTO.getProblemwebEditor();
	String problemwebEditorText_arr[] = SalesDTO.getProblemwebEditorText();

	String planwebEditor_arr[] = SalesDTO.getPlanwebEditor();
	String planwebEditorText_arr[] = SalesDTO.getPlanwebEditorText();

	for (int i = 0; i < plancheck_arr.length; i++) {

	    if (i == idx) {

		if (StringUtils.isNotEmpty(propelwebEditor_arr[i])) {
		    propelwebEditor = propelwebEditor_arr[i];
		}

		if (StringUtils.isNotEmpty(propelwebEditorText_arr[i])) {
		    propelwebEditorText = propelwebEditorText_arr[i];
		}

		if (StringUtils.isNotEmpty(problemwebEditor_arr[i])) {
		    problemwebEditor = problemwebEditor_arr[i];
		}

		if (StringUtils.isNotEmpty(problemwebEditorText_arr[i])) {
		    problemwebEditorText = problemwebEditorText_arr[i];
		}

		if (StringUtils.isNotEmpty(planwebEditor_arr[i])) {
		    planwebEditor = planwebEditor_arr[i];
		}

		if (StringUtils.isNotEmpty(planwebEditorText_arr[i])) {
		    planwebEditorText = planwebEditorText_arr[i];
		}

		plancheck = plancheck_arr[i];
	    }
	}

	/*
	 * String textFileName = "D:\\ptc\\Windchill_10.2\\Windchill\\codebase\\editorFiles\\sss.txt"; String contentFromSample = "";
	 * BufferedReader bufferedTextFileReader = null; try { StringBuilder contentReceiver = new StringBuilder(); bufferedTextFileReader =
	 * new BufferedReader(new FileReader(textFileName));
	 * 
	 * char[] buf = new char[1024];
	 * 
	 * while (bufferedTextFileReader.read(buf) > 0) { contentReceiver.append(buf); }
	 * 
	 * contentFromSample = contentReceiver.toString(); } catch (FileNotFoundException e) { System.out.println(textFileName +
	 * " 파일이 존재하지 않습니다.");
	 * 
	 * e.printStackTrace(); } catch (IOException e) { System.out.println(textFileName + " 파일을 읽을 수 없습니다.");
	 * 
	 * e.printStackTrace(); } finally{ bufferedTextFileReader.close(); }
	 */

	SalesDTO.setPropelwebEditor_1(propelwebEditor);
	SalesDTO.setPropelwebEditorText_1(propelwebEditorText);

	SalesDTO.setProblemwebEditor_1(problemwebEditor);
	SalesDTO.setProblemwebEditorText_1(problemwebEditorText);

	SalesDTO.setPlanwebEditor_1(planwebEditor);
	SalesDTO.setPlanwebEditorText_1(planwebEditorText);

	SalesDTO.setPlanCheck_1(plancheck);

	return SalesDTO;
    }

    @RequestMapping("/salesTaskCreateForm")
    public void salesTaskCreateForm(KETSalesProjectDTO SalesDTO, Model model) throws Exception {
	SalesDTO = this.getTaskInfo(SalesDTO);
	model.addAttribute("sales", SalesDTO);
    }

    @RequestMapping("/salesTaskViewForm")
    public void salesTaskViewForm(KETSalesProjectDTO SalesDTO, Model model) throws Exception {
	SalesDTO = this.getTaskInfo(SalesDTO);
	model.addAttribute("sales", SalesDTO);
    }

    @RequestMapping("/salesProjectCreate")
    public void salesProjectCreate(KETSalesProjectDTO SalesDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {

	boolean isRevise = false;

	if ("R".equals(SalesDTO.getRevise())) {// 개정
	    isRevise = true;
	    SalesDTO.setRevise("");
	}
	String oid = SalesDTO.getOid();
	String errMsg = "";

	KETSalesProject salespjt = null;
	salespjt = (KETSalesProject) CommonUtil.getObject(oid);

	KETSalesProject latest = SalesHelper.service.getLatestSalesProject(salespjt);

	if (StringUtils.isNotEmpty(oid) && !oid.equals(CommonUtil.getOIDString(latest))) {
	    errMsg = "최신 버전이 아닙니다!";
	}

	if (StringUtils.isEmpty(errMsg)) {

	    KETSalesProject source = (KETSalesProject) CommonUtil.getObject(oid);

	    if (isRevise) {
		SalesDTO = SalesHelper.service.salesProjectRevise(SalesDTO);
		oid = SalesDTO.getOid();
		KETSalesProject target = (KETSalesProject) CommonUtil.getObject(oid);
		SalesHelper.service.copyProjectChild(source, target);
	    }

	    oid = SalesHelper.service.saveSales(SalesDTO);
	}

	String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */
	if (StringUtils.isNotEmpty(errMsg)) {
	    msg = errMsg;
	}
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	String str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
	        + "\n try{ parent.opener.sales.search();  } catch(error){ }  \n";
	str += "\n parent.location.href='/plm/ext/sales/project/viewSalesProjectForm.do?oid=" + oid + "';" + "\n </script>";

	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/pageShowSalesCSPage")
    @ResponseBody
    public List<Map<String, Object>> pageShowSalesCSPage(String oid, boolean isFile, Model model) throws Exception {
	List<Map<String, Object>> rsltList = SalesHelper.service.getCsprojectSortedList(isFile);

	return rsltList;
    }

    @RequestMapping("/pageShowSalesCSDetailPage")
    @ResponseBody
    public List<Map<String, String>> pageShowSalesCSDetailPage(String type, String dept, String parent) throws Exception {
	List<Map<String, String>> rsltList = dao.getSalesPresentConditionDetailInfo(type, dept, parent);

	return rsltList;
    }

    @RequestMapping("/viewSalesCSProjectForm")
    public void viewSalesCSProjectForm(String oid, String csYN, Model model) throws Exception {

	boolean authCheckFlag = true;
	boolean csEmpty = false;

	if ("Y".equals(csYN)) {
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	    PeopleData people = new PeopleData((user));
	    Department dept = (Department) people.department;

	    if (!CommonUtil.isAdmin() && !CommonUtil.isMember("영업CS관리") && !CommonUtil.isMember("CS영업")) {
		authCheckFlag = false;
	    }
	}

	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();
	if (StringUtils.isEmpty(oid)) {

	    List<Map<String, Object>> rsltList = SalesHelper.service.getCsprojectSortedList(false);

	    int i = 0;
	    for (Map<String, Object> list : rsltList) {
		if (i == 0) {
		    oid = "ext.ket.sales.entity.KETSalesProject:" + (String) list.get("oid");
		    model.addAttribute("manageOid", (String) list.get("manageOid"));
		    i++;
		} else {
		    break;
		}

	    }
	}
	model.addAttribute("authCheckFlag", authCheckFlag);

	if (StringUtils.isNotEmpty(oid)) {
	    SalesDTO.setOid(oid);
	    SalesDTO = SalesHelper.service.viewSalesProjectForm(SalesDTO);

	    SalesDTO.setCsYN(csYN);
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(SalesDTO.getSalesPjt()));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(SalesDTO.getSalesPjt()));

	    String hasCheckedNewEvent = "";
	    String workShopYN = SalesDTO.getSalesPjt().getWorkShopYN();

	    if (DateUtil.isDaysBeetween(SalesDTO.getSalesPjt().getCreateTimestamp(), 14)) {
		hasCheckedNewEvent = "New";
		if ("Y".equals(workShopYN)) {
		    hasCheckedNewEvent = hasCheckedNewEvent + " / " + "WorkShop";
		}
	    } else {
		if ("Y".equals(workShopYN)) {
		    hasCheckedNewEvent = "WorkShop";
		}
	    }

	    model.addAttribute("hasCheckedNewEvent", hasCheckedNewEvent);

	} else {// CS회의 대상 프로젝트가 하나도 없다면
	    csEmpty = true;
	    model.addAttribute("csEmpty", csEmpty);
	}

	List<Map<String, String>> salesPresentList = null;
	if ("Y".equals(csYN)) {
	    salesPresentList = SalesHelper.service.salesPresentList();
	}
	String classKey = dao.searchCSLastManageInfo("");

	KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(classKey);
	SalesDTO.setDegree(manage.getDegree());
	SalesDTO.setYear(manage.getYear().substring(2));
	SalesDTO.setMonth(manage.getMonth());

	if ("Y".equals(csYN)) {
	    SalesDTO.setSalesPresentList(salesPresentList);
	}

	model.addAttribute("sales", SalesDTO);

	String color = IssueUtil.manager.getIssueStateColor(SalesDTO);
	model.addAttribute("issueLinkColor", color);
    }

    @RequestMapping("/viewsalesCSMeetingImgForm")
    public void viewsalesCSMeetingImgForm(String oid, String csYN, Model model) throws Exception {
	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();

	boolean authCheckFlag = true;

	WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	PeopleData people = new PeopleData((user));
	Department dept = (Department) people.department;

	if (!CommonUtil.isAdmin() && !CommonUtil.isMember("영업CS관리")) {
	    authCheckFlag = false;
	}

	if (authCheckFlag) {
	    String csFileName = "";
	    if (StringUtils.isEmpty(oid)) {

		List<Map<String, Object>> rsltList = SalesHelper.service.getCsprojectSortedList(false);

		int i = 0;
		for (Map<String, Object> list : rsltList) {
		    if (i == 0) {
			oid = "ext.ket.sales.entity.KETSalesProject:" + (String) list.get("oid");
			csFileName = (String) list.get("year") + "년_" + (String) list.get("month") + "월_" + (String) list.get("degree")
			        + "차_CS회의";
			i++;
		    } else {
			break;
		    }

		}
	    }

	    SalesDTO.setOid(oid);
	    SalesDTO.setCsFileName(csFileName);
	    SalesDTO = SalesHelper.service.viewSalesProjectForm(SalesDTO);
	    SalesDTO.setCsYN(csYN);
	    model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(SalesDTO.getSalesPjt()));
	    model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(SalesDTO.getSalesPjt()));
	    model.addAttribute("sales", SalesDTO);
	    model.addAttribute("authCheckFlag", authCheckFlag);
	} else {
	    model.addAttribute("authCheckFlag", authCheckFlag);
	}

    }

    @RequestMapping("/salesProjectSetCSmeeting")
    public void salesProjectSetCSmeeting(String oid, String gubun, HttpServletResponse response, HttpServletRequest request)
	    throws Exception {

	/*
	 * if ("C".equals(gubun)) { KETSalesProject salesPjt = (KETSalesProject) CommonUtil.getObject(oid); salesPjt.setCsYn(""); //
	 * Iteration 증가 없이 속성 수정 PersistenceServerHelper.manager.update(salesPjt);
	 * 
	 * salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt); } else { KETSalesProject source = (KETSalesProject)
	 * CommonUtil.getObject(oid); KETSalesProject target = null;
	 * 
	 * target = this.appointCS(source, target); oid = CommonUtil.getOIDString(target);
	 * 
	 * }
	 */

	String classKey = dao.searchCSLastManageInfo("");
	KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(classKey);
	String meetingYn = manage.getCsClose();
	String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */
	String nextDay = "";
	if ("Y".equals(meetingYn) && !dao.isPossibleCSMeeting()) {// 최신 cs회의가 마감되었고 아직 차수가능일자가 도래하지 않았다면 cs회의등록불가
	    nextDay = DateUtil.getDateString(manage.getNextStartdate(), "date");
	    msg = "CS회의가 마감되었습니다.다음 회의등록가능일자 : " + nextDay;
	} else {
	    SalesHelper.service.CSMeetingChasuCreate(oid, gubun);
	}

	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
	        + "\n try{ parent.opener.sales.search();  } catch(error){ }  \n";
	str += "\n parent.location.href='/plm/ext/sales/project/viewSalesProjectForm.do?oid=" + oid + "';" + "\n </script>";

	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/viewSalesProjectForm")
    public void viewSalesProjectForm(String oid, Model model) throws Exception {
	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();
	SalesDTO.setOid(oid);
	KETSalesProject pjt = (KETSalesProject) CommonUtil.getObject(oid);

	boolean authCheckFlag = SalesHelper.service.getProjectViewAuthInfo(pjt);

	if (authCheckFlag) {
	    SalesDTO = SalesHelper.service.viewSalesProjectForm(SalesDTO);
	}

	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(SalesDTO.getSalesPjt()));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(SalesDTO.getSalesPjt()));
	model.addAttribute("authCheckFlag", authCheckFlag);
	model.addAttribute("sales", SalesDTO);
    }

    @RequestMapping("/updateSalesProjectForm")
    public void updateSalesProjectForm(String oid, String revise, Model model) throws Exception {
	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();
	SalesDTO.setOid(oid);
	SalesDTO.setRevise(revise);

	SalesDTO = SalesHelper.service.viewSalesProjectForm(SalesDTO);

	model.addAttribute("primaryFile", KETContentHelper.manager.getPrimaryContent(SalesDTO.getSalesPjt()));
	model.addAttribute("secondaryFiles", KETContentHelper.manager.getSecondaryContents(SalesDTO.getSalesPjt()));
	model.addAttribute("sales", SalesDTO);
    }

    @RequestMapping("/updateAfterState")
    @ResponseBody
    public String updateAfterState(@RequestParam(value = "oid") String oid, @RequestParam(value = "stateCode") String stateCode, Model model)
	    throws Exception {
	try {

	    String flag = null;

	    KETSalesProject salesPjt = (KETSalesProject) CommonUtil.getObject(oid);
	    salesPjt.setAfterSalesState(NumberCodeHelper.manager.getNumberCode("SALESPJTSTATE", stateCode));// 결재가 완료됐을 시 변경되어야할 상태를 미리 지정한다
	    // Iteration 증가 없이 속성 수정
	    PersistenceServerHelper.manager.update(salesPjt);

	    salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);

	    try {

		if (salesPjt.getAfterSalesState() != null && salesPjt.getAfterSalesState().getCode().equals(stateCode)) {
		    flag = "Y";
		} else {
		    flag = "N";
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		flag = "E";
	    }

	    return flag;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    return "Fail";
	}
    }

    @RequestMapping("/salesProjectRevise")
    public void salesProjectRevise(KETSalesProjectDTO SalesDTO, HttpServletResponse response, HttpServletRequest request) throws Exception {
	SalesDTO = SalesHelper.service.salesProjectRevise(SalesDTO);
	String str = "";
	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	str = "\n <script language=\"javascript\">\n alert(\""
	        + KETMessageService.getMessageService().getString("e3ps.message.ket_message", "03405")/* "개정되었습니다." */+ "\");"
	        + "\n try{ parent.opener.sales.search();  } catch(error){ }  \n";
	str += "\n parent.location.href='/plm/ext/sales/project/viewSalesProjectForm.do?oid=" + SalesDTO.getOid() + "';" + "\n </script>";

	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/findPagingList")
    @ResponseBody
    public Map<String, Object> findPagingList(KETSalesProjectDTO SalesDTO) throws Exception {
	if (SalesDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = SalesHelper.service.findPaging(SalesDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();

	List<KETSalesProjectDTO> ketSalesDTOList = new ArrayList<KETSalesProjectDTO>();
	KETMessageService messageService = KETMessageService.getMessageService();
	boolean authCheckFlag = false;
	String isDelay = "";

	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();

	    KETSalesProjectDTO rsltKETSalesDTO = new KETSalesProjectDTO();
	    String mainSignal = dao.searchMainCheck(CommonUtil.getOIDLongValue2Str((KETSalesProject) objArr[0]));
	    String temp[] = mainSignal.split("\\|");
	    mainSignal = temp[0];
	    isDelay = StringUtil.checkNull(temp[2]);
	    if (StringUtils.isEmpty(mainSignal) || mainSignal == null || "null".equals(mainSignal)) {
		mainSignal = "none";
	    }

	    WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	    PeopleData people = new PeopleData((user));
	    Department dept = (Department) people.department;

	    if (CommonUtil.isAdmin() || CommonUtil.isMember("영업CS관리")) {
		authCheckFlag = true;
	    }
	    rsltKETSalesDTO = rsltKETSalesDTO.KETSalesDTOGrid((KETSalesProject) objArr[0], rsltKETSalesDTO, messageService, mainSignal,
		    authCheckFlag, isDelay);
	    ketSalesDTOList.add(rsltKETSalesDTO);

	}

	return EjsConverUtil.convertToDto(ketSalesDTOList, pageControl);
    }

    @RequestMapping("/getShowonlyOneSalesView")
    @ResponseBody
    public List<Map<String, Object>> getShowonlyOneSalesView(String oid, String option, Model model) throws Exception {

	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();
	SalesDTO.setOid(oid);

	List<Map<String, Object>> resultList = null;

	Map<String, Object> rslt = new HashMap<String, Object>();

	if ("list".equals(option)) {
	    resultList = dao.getTaskLineUpList(CommonUtil.getOIDLongValue2Str(SalesDTO.getOid()), "");
	    if (resultList.size() == 0) {
		resultList = dao.getTaskLineUpList(CommonUtil.getOIDLongValue2Str(SalesDTO.getOid()), "Y");
	    }
	} else {
	    resultList = new ArrayList<Map<String, Object>>();
	    KETSalesProject salesPjt = (KETSalesProject) CommonUtil.getObject(oid);

	    NumberCode num = null;
	    String rankName = "";

	    if (salesPjt.getRankType() != null) {
		num = (NumberCode) salesPjt.getRankType();// 중요도
		rankName = num.getName();
	    }

	    String projectNo = salesPjt.getProjectNo();
	    String projectName = salesPjt.getProjectName();

	    QueryResult inQr = PersistenceHelper.manager.navigate(salesPjt, "partClass", KETSalesPartClassLink.class, false);
	    KETSalesPartClassLink clzLink = null;
	    KETPartClassification partClz = null;
	    String mainPartClz = "";
	    String applyArea = salesPjt.getApplyArea();
	    while (inQr.hasMoreElements()) {

		clzLink = (KETSalesPartClassLink) inQr.nextElement();
		partClz = (KETPartClassification) clzLink.getPartClass();

		if ("Y".equals(clzLink.getMainYn())) {
		    mainPartClz = (String) partClz.getClassKrName();
		}

	    }

	    num = (NumberCode) salesPjt.getSalesState();// 프로젝트 상태
	    String salesStateName = "";

	    if (num != null) {
		salesStateName = num.getName();
	    }

	    String webEditor = (String) salesPjt.getWebEditor();

	    WTUser wtuser = (WTUser) salesPjt.getIterationInfo().getCreator().getPrincipal();
	    Department userDept = DepartmentHelper.manager.getDepartment(wtuser);

	    String pmdept = userDept.getName();
	    String lastBuyerName = "";

	    String expectSalesTotal = salesPjt.getExpectSalesTotal(); // main 총매출
	    String expectSalesYear = salesPjt.getExpectSalesYear(); // main 연간매출

	    KETSalesCompanyLink CompanyLink = null;
	    String customerName = "";
	    String competitorName = "";
	    QueryResult qr = PersistenceHelper.manager.navigate(salesPjt, "company", KETSalesCompanyLink.class, false);

	    while (qr.hasMoreElements()) {
		CompanyLink = (KETSalesCompanyLink) qr.nextElement();
		if ("customer".equals(CompanyLink.getCodeType())) {
		    customerName += CompanyLink.getCompany().getName() + ",";
		}
		if ("buyer".equals(CompanyLink.getCodeType())) {
		    lastBuyerName += CompanyLink.getCompany().getName();
		}
		if ("competitor".equals(CompanyLink.getCodeType())) {
		    competitorName += CompanyLink.getCompany().getName() + ",";
		}
	    }
	    customerName = StringUtils.removeEnd(customerName, ",");
	    competitorName = StringUtils.removeEnd(competitorName, ",");

	    String bigo = salesPjt.getBigo();

	    if (StringUtils.isEmpty(bigo)) {
		bigo = "";
	    }

	    if (StringUtils.isEmpty(customerName)) {
		customerName = "";
	    }
	    if (StringUtils.isEmpty(expectSalesYear)) {
		expectSalesYear = "";
	    }
	    if (StringUtils.isEmpty(expectSalesTotal)) {
		expectSalesTotal = "";
	    }

	    if (StringUtils.isEmpty(lastBuyerName)) {
		lastBuyerName = "";
	    }
	    if (StringUtils.isEmpty(pmdept)) {
		pmdept = "";
	    }
	    if (StringUtils.isEmpty(webEditor)) {
		webEditor = "";
	    }
	    if (StringUtils.isEmpty(salesStateName)) {
		salesStateName = "";
	    }
	    if (StringUtils.isEmpty(competitorName)) {
		competitorName = "";
	    }
	    if (StringUtils.isEmpty(applyArea)) {
		applyArea = "";
	    }
	    if (StringUtils.isEmpty(mainPartClz)) {
		mainPartClz = "";
	    }
	    if (StringUtils.isEmpty(projectName)) {
		projectName = "";
	    }
	    if (StringUtils.isEmpty(projectNo)) {
		projectNo = "";
	    }
	    if (StringUtils.isEmpty(rankName)) {
		rankName = "";
	    }
	    rslt.put("customerName", customerName);
	    rslt.put("expectSalesYear", expectSalesYear);
	    rslt.put("expectSalesTotal", expectSalesTotal);
	    rslt.put("lastBuyerName", lastBuyerName);
	    rslt.put("pmdept", pmdept);
	    rslt.put("webEditor", webEditor);
	    rslt.put("salesStateName", salesStateName);
	    rslt.put("competitorName", competitorName);
	    rslt.put("applyArea", applyArea);
	    rslt.put("mainPartClz", mainPartClz);
	    rslt.put("projectName", projectName);
	    rslt.put("projectNo", projectNo);
	    rslt.put("rankName", rankName);
	    rslt.put("bigo", bigo);
	    Department center = DepartmentHelper.manager.getDepartment(salesPjt.getCenterCode());
	    if (center != null) {
		rslt.put("centerName", center.getName());
	    }
	    String hasCheckedNewEvent = "";
	    String workShopYN = salesPjt.getWorkShopYN();

	    if (DateUtil.isDaysBeetween(salesPjt.getCreateTimestamp(), 14)) {
		hasCheckedNewEvent = "New";
		if ("Y".equals(workShopYN)) {
		    hasCheckedNewEvent = hasCheckedNewEvent + " / " + "WorkShop";
		}
	    } else {
		if ("Y".equals(workShopYN)) {
		    hasCheckedNewEvent = "WorkShop";
		}
	    }
	    rslt.put("hasCheckedNewEvent", hasCheckedNewEvent);

	    String mainSignal = dao.searchMainCheck(CommonUtil.getOIDLongValue2Str(oid));

	    String temp[] = mainSignal.split("\\|");
	    mainSignal = temp[0];
	    String ObjectKey = temp[1];
	    if (StringUtils.isEmpty(mainSignal) || mainSignal == null || "null".equals(mainSignal)) {
		mainSignal = "none";
	    }
	    rslt.put("mainSignal", mainSignal);
	    KETSalesTask TaskEditortarget = (KETSalesTask) CommonUtil.getObject(ObjectKey);

	    if (TaskEditortarget != null) {// 계획일자가 기입되지 않은경우

		rslt.put("propelwebEditor", StringUtil.checkNull((String) TaskEditortarget.getPropelwebEditor()));
		rslt.put("problemwebEditor", StringUtil.checkNull(((String) TaskEditortarget.getProblemwebEditor())));
		rslt.put("planwebEditor", StringUtil.checkNull(((String) TaskEditortarget.getPlanwebEditor())));

	    } else {
		rslt.put("propelwebEditor", "");
		rslt.put("problemwebEditor", "");
		rslt.put("planwebEditor", "");
	    }

	    resultList.add(rslt);

	}
	Collections.sort(resultList, ComparatorUtil.SALESPROJECTSTEP);

	return resultList;
    }

    // CS회의 일괄지정
    @RequestMapping("/csMultiUpdate")
    @ResponseBody
    public String csMultiUpdate(@RequestParam(value = "arroid") String arroid, String gubun, Model model) throws Exception {

	try {
	    /*
	     * arroid = StringUtils.removeEnd(arroid, ","); String arr[] = arroid.split(",");
	     * 
	     * KETSalesProject source = null; KETSalesProject target = null; KETSalesProjectDTO dto = new KETSalesProjectDTO(); for (int i =
	     * 0; i < arr.length; i++) { if ("Y".equals(gubun)) {// 일괄지정
	     * 
	     * source = (KETSalesProject) CommonUtil.getObject(arr[i]); source.setCsYn("Y"); // Iteration 증가 없이 속성 수정
	     * PersistenceServerHelper.manager.update(source);
	     * 
	     * source = (KETSalesProject) PersistenceHelper.manager.refresh(source); // CS회의 차수 마감시 이터레이션을 증가하기로 함 여기서는 그냥 수정만함 //
	     * this.appointCS(source, target); } else {// 일괄취소 source = (KETSalesProject) CommonUtil.getObject(arr[i]); source.setCsYn("");
	     * // Iteration 증가 없이 속성 수정 PersistenceServerHelper.manager.update(source);
	     * 
	     * source = (KETSalesProject) PersistenceHelper.manager.refresh(source); }
	     * 
	     * }
	     */
	    String classKey = dao.searchCSLastManageInfo("");

	    KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(classKey);
	    if (manage != null) {
		String meetingYn = manage.getCsClose();
		if ("Y".equals(meetingYn) && !dao.isPossibleCSMeeting()) {// 최신 cs회의가 마감되었고 아직 차수가능일자가 도래하지 않았다면 cs회의등록불가
		    String nextDay = DateUtil.getDateString(manage.getNextStartdate(), "date");
		    return "N" + nextDay;
		}
	    }

	    SalesHelper.service.CSMeetingChasuCreate(arroid, gubun);
	    return "S";

	} catch (Exception e) {
	    e.printStackTrace();
	    return "Fail";
	}
    }

    // CS회의 종료처리
    @RequestMapping("/csMeetingClose")
    @ResponseBody
    public String csMeetingClose(KETSalesProjectDTO paramObject, Model model) throws Exception {
	try {
	    String oid = paramObject.getOid();

	    KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(oid);
	    KETSalesCSMeetingManageLink manageLink = null;
	    KETSalesCSMeetingApproval approve = null;
	    CSProjectSortEnum team = null;

	    String dept = "";
	    boolean goFlag = true;
	    QueryResult qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);

	    while (qr.hasMoreElements()) {

		manageLink = (KETSalesCSMeetingManageLink) qr.nextElement();
		approve = manageLink.getCsMeetingApprove();

		if (!approve.getState().toString().equals("APPROVED")) {
		    team = CSProjectSortEnum.toTeamTypeEnumBySort(approve.getDeptSort());

		    if (!StringUtils.contains(dept, team.getTeam())) {
			dept += team.getTeam() + ",";
		    }

		    goFlag = false;
		}

	    }

	    if (goFlag) {
		paramObject.setCsCloseTartget(manage);
		SalesHelper.service.csMeetingClose(paramObject);
	    } else {
		dept = "D(" + StringUtils.removeEnd(dept, ",") + ")";
		return dept;
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    return "Fail";
	}
	return "S";
    }

    // CS회의 순서지정
    // @RequestMapping("/csMeetingSortUpdate")
    // public void csMeetingSortUpdate(KETSalesProjectDTO paramObject, HttpServletResponse response, HttpServletRequest request) {
    // try {
    //
    // String sortNo[] = paramObject.getSortNo();
    // String oids[] = paramObject.getOids();
    // KETSalesCSMeetingManageLink manageLink = null;
    //
    // for (int i = 0; i < sortNo.length; i++) {
    // manageLink = (KETSalesCSMeetingManageLink) CommonUtil.getObject(oids[i]);
    // manageLink.setSortNo(sortNo[i]);
    //
    // PersistenceHelper.manager.save(manageLink);
    // }
    //
    // String str = "";
    // response.setContentType("text/html;charset=KSC5601");
    // PrintWriter pwriter = response.getWriter();
    // str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");"
    // + "\n parent.location.href='/plm/ext/sales/project/viewSalesCSForm.do?oid="
    // + CommonUtil.getOIDString(manageLink.getCsDegree()) + "';" + "\n </script>";
    // pwriter.println(str);
    // pwriter.close();
    //
    // } catch (Exception e) {
    // e.printStackTrace();
    //
    // }
    //
    // }

    // CS회의 순서지정
    @RequestMapping("/csMeetingSortUpdate")
    @ResponseBody
    public String csMeetingSortUpdate(KETSalesProjectDTO paramObject) {
	try {

	    String sortNo[] = paramObject.getSortNo();
	    String oids[] = paramObject.getOids();
	    KETSalesCSMeetingManageLink manageLink = null;
	    for (int i = 0; i < sortNo.length; i++) {
		manageLink = (KETSalesCSMeetingManageLink) CommonUtil.getObject(oids[i]);
		manageLink.setSortNo(sortNo[i]);

		PersistenceHelper.manager.save(manageLink);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Fail";
	}
	return "S";
    }

    // CS회의 순서 지정(파일 순서포함)
    @RequestMapping("/csMeetingFileSortUpdate")
    public void csMeetingFileSortUpdate(KETSalesProjectDTO paramObject, HttpServletResponse response, HttpServletRequest request) {
	try {

	    String sortNo[] = paramObject.getSortNo();
	    String oids[] = paramObject.getOids();
	    KETSalesCSMeetingManageLink manageLink = null;

	    List<Map<String, String>> rsltList = SalesHelper.service.csMeetingFileVaultUpload(paramObject);

	    for (int i = 0; i < sortNo.length; i++) {
		manageLink = (KETSalesCSMeetingManageLink) CommonUtil.getObject(oids[i]);

		// String deptSort = SalesHelper.service.getCSprojectSortInfo(manageLink.getCsProject().getSalesTeam());
		// manageLink.setSortNo(deptSort + sortNo[i]);
		manageLink.setSortNo(sortNo[i]);
		manageLink.setCsFile("");

		for (Map<String, String> rslt : rsltList) {

		    if (rslt.get("sortNo").equals("0.5") && sortNo[i].equals("1")) {
			manageLink.setFileSortOption(rslt.get("appOid"));
		    }

		    if (rslt.get("sortNo").equals(sortNo[i])) {
			manageLink.setCsFile(rslt.get("appOid"));
		    }
		}

		PersistenceHelper.manager.save(manageLink);
	    }

	    String str = "";
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter pwriter = response.getWriter();
	    str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");"
		    + "\n parent.location.href='/plm/ext/sales/project/viewSalesCSForm.do?oid="
		    + CommonUtil.getOIDString(manageLink.getCsDegree()) + "';" + "\n </script>";
	    pwriter.println(str);
	    pwriter.close();

	} catch (Exception e) {
	    e.printStackTrace();

	}

    }

    public KETSalesProject appointCS(KETSalesProject source, KETSalesProject target) throws Exception {// cs회의 지정시 원본 KETSalesProject
	                                                                                               // 이터레이션증가시키고, 기존 관련 테이블들을 복사한다

	target = (KETSalesProject) ObjectUtil.checkout(source);
	target.setCsYn("Y");
	target = (KETSalesProject) PersistenceHelper.manager.save(target);
	target = (KETSalesProject) ObjectUtil.checkin(target);
	target = (KETSalesProject) PersistenceHelper.manager.refresh(target);

	SalesHelper.service.copyProjectChild(source, target);
	return target;
    }

    // CS 회의 차수 생성
    @RequestMapping("/csDegreeCreate")
    public void csDegreeCreate(KETSalesProjectDTO paramObject, HttpServletResponse response, HttpServletRequest request) throws Exception {

	String errMsg = "";

	QueryResult qr = SalesHelper.service.getCSlist();
	String oid = "";
	if (qr.size() < 1) {
	    errMsg = "지정된 CS회의가 없습니다.";
	} else {

	    paramObject.setQueryResult(qr);
	    List<Map<String, String>> csmeetingInfo = dao.searchCSInfo();

	    String CS_DEGREE = "";

	    for (Map<String, String> info : csmeetingInfo) {
		CS_DEGREE = info.get("CS_DEGREE");
	    }

	    paramObject.setDegree(CS_DEGREE);

	    oid = SalesHelper.service.CSDegreeCreate(paramObject);
	}
	String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */
	if (StringUtils.isNotEmpty(errMsg)) {
	    msg = errMsg;
	}

	response.setContentType("text/html;charset=KSC5601");
	PrintWriter pwriter = response.getWriter();
	String str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
	        + "\n try{ parent.opener.sales.search();} catch(error){ }  \n";
	if (StringUtils.isNotEmpty(errMsg)) {
	    str += "\n parent.location.href='/plm/ext/sales/project/createSalesCSForm.do';" + "\n </script>";
	} else {
	    str += "\n parent.location.href='/plm/ext/sales/project/viewSalesCSForm.do?oid=" + oid + "';" + "\n </script>";
	}

	pwriter.println(str);
	pwriter.close();
	return;
    }

    @RequestMapping("/findPagingCSList")
    @ResponseBody
    public Map<String, Object> findPagingCSList(KETSalesProjectDTO SalesDTO) throws Exception {
	if (SalesDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// methodserver로 부터 데이터를 조회한다.
	PageControl pageControl = SalesHelper.service.findPagingCSList(SalesDTO);
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");

	// DTO 객체로 변환 한다
	QueryResult queryResult = pageControl.getResult();

	List<KETSalesProjectDTO> ketSalesDTOList = new ArrayList<KETSalesProjectDTO>();
	KETMessageService messageService = KETMessageService.getMessageService();
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    KETSalesProjectDTO rsltKETSalesDTO = new KETSalesProjectDTO();
	    rsltKETSalesDTO = rsltKETSalesDTO.KETSalesCSDTOGrid((KETSalesCSMeetingManage) objArr[0], rsltKETSalesDTO, messageService);
	    ketSalesDTOList.add(rsltKETSalesDTO);
	}

	return EjsConverUtil.convertToDto(ketSalesDTOList, pageControl);
    }

    public void checkDir(File file) {
	if (!file.exists()) {
	    file.mkdir();
	}
    }

    // CS회의 자료 폴더 생성
    @RequestMapping("/csMeetingCreateDir")
    @ResponseBody
    public String csMeetingCreateDir(KETSalesProjectDTO paramObject) {

	String csDir = "";

	int dirCnt = 0;
	try {

	    WTProperties prop = WTProperties.getServerProperties();
	    String wthome = prop.getProperty("wt.home");
	    csDir = wthome + "\\csMeeting";

	    checkDir(new File(csDir));
	    checkDir(new File(csDir + "\\zip"));

	    String currentDate = DateUtil.getCurrentDateString("date");
	    csDir += "\\" + currentDate;

	    File targetDir = new File(csDir);

	    checkDir(targetDir);

	    File[] files = targetDir.listFiles();

	    if (files == null) {

	    } else {
		for (int i = 0; i < files.length; ++i) {
		    File file = files[i];

		    if (file.isDirectory()) {
			dirCnt++;
		    }
		}
	    }

	    dirCnt += 1;

	    csDir += "\\" + Integer.toString(dirCnt);

	    checkDir(new File(csDir));

	} catch (Exception e) {
	    e.printStackTrace();
	    return "Fail";
	}
	return csDir;
    }

    // CS회의 자료 저장
    @RequestMapping("/csMeetingDataCreate")
    @ResponseBody
    public String csMeetingDataCreate(KETSalesProjectDTO paramObject) {
	FileOutputStream stream = null;
	String filename = "";
	String csDir = paramObject.getCsDir();

	try {

	    File createDir = new File(csDir);
	    if (!createDir.exists()) {
		createDir.mkdir();
	    }

	    String binaryData = paramObject.getCsimgSrc();

	    binaryData = binaryData.replaceAll("data:image/png;base64,", "");
	    byte[] file = Base64.decode(binaryData);
	    filename = "csMeeting" + paramObject.getCsimgCnt() + ".png";

	    File imgfile = new File(csDir + "\\" + filename);
	    if (imgfile.exists()) {
		imgfile.delete();
	    }

	    stream = new FileOutputStream(csDir + "\\" + filename);
	    stream.write(file);

	} catch (Exception e) {
	    e.printStackTrace();
	    return "Fail";
	} finally {
	    try {
		stream.close();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return "S";
    }

    public boolean isExist(File image) {

	return image.exists();
    }

    @RequestMapping("/csMeetingCreatePPTX")
    @ResponseBody
    public String csMeetingCreatePPTX(KETSalesProjectDTO paramObject) throws Exception {
	String csfileName = paramObject.getCsFileName();
	File file = new File(paramObject.getCsDir() + "\\" + csfileName + ".pptx");
	if (file.exists()) {
	    file.delete();
	    // pptin = new FileInputStream(file);
	    // ppt = new XMLSlideShow(pptin);
	    XMLSlideShow ppt = new XMLSlideShow();
	    java.awt.Dimension pgsize = ppt.getPageSize();

	    int pgw = pgsize.width;
	    int pgh = pgsize.height;
	    System.out.println("ppt 슬라이드 가로 : " + pgw);
	    System.out.println("ppt 슬라이드 세로 : " + pgh);

	    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
	    ppt.write(out);
	    out.close();

	}
	try {
	    String imgfileName = paramObject.getCsimgNaming();
	    // fileName = StringUtils.removeEnd(fileName, ",");

	    int fileCnt = Integer.parseInt(imgfileName);
	    if (fileCnt == 0) {
		fileCnt = 1;
	    }
	    for (int i = 0; i < fileCnt; i++) {
		this.createPPTX("csMeeting" + i + ".png", paramObject.getCsDir(), csfileName + ".pptx");
	    }

	    /*
	     * String fileNames[] = fileName.split(","); for (int i = 0; i < fileNames.length; i++) { this.createPPTX(fileNames[i]); }
	     */
	} catch (Exception e) {
	    e.printStackTrace();
	    return "Fail";
	}
	return "S";
    }

    public void createPPTX(String imgfileName, String csDir, String pptFileName) throws Exception, IOException {
	File image = new File(csDir + "\\" + imgfileName);

	/*
	 * while (true) { if (isExist(image)) { break; }
	 * 
	 * }
	 */
	File file = new File(csDir + "\\" + pptFileName);

	Image img = new ImageIcon(csDir + "\\" + imgfileName).getImage();
	System.out.println(imgfileName + "의 가로 사이즈 : " + img.getWidth(null));
	System.out.println(imgfileName + "의 세로 사이즈 : " + img.getHeight(null));

	BufferedInputStream pptin = null;
	XMLSlideShow ppt = null;
	if (file.exists()) {
	    pptin = new BufferedInputStream(new FileInputStream(file));
	    ppt = new XMLSlideShow(pptin);

	    pptin.close();
	} else {
	    ppt = new XMLSlideShow();

	    java.awt.Dimension pgsize = ppt.getPageSize();

	    int pgw = pgsize.width;
	    int pgh = pgsize.height;

	    ppt.setPageSize(new java.awt.Dimension(720, 405));// 슬라이드 비율 16:9 (cm는 대략 28.34 으로 환산됨)
	}

	// XMLSlideShow ppt = new XMLSlideShow();

	// creating a slide in it
	XSLFSlide slide = ppt.createSlide();

	// reading an image

	BufferedInputStream in = new BufferedInputStream(new FileInputStream(image));

	// converting it into a byte array
	byte[] picture = IOUtils.toByteArray(in);

	// adding the image to the presentation
	int idx = ppt.addPicture(picture, XSLFPictureData.PICTURE_TYPE_PNG);

	// creating a slide with given picture on it
	XSLFPictureShape pic = slide.createPicture(idx);
	pic.setAnchor(new Rectangle(30, 50, 650, 325));
	// creating a file object

	// FileOutputStream out = new FileOutputStream(file);

	// saving the changes to a file
	BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
	ppt.write(out);
	System.out.println("image added successfully");
	in.close();
	out.close();

    }

    @RequestMapping("/salesCSMeetingImgViewForm")
    public void salesCSMeetingImgViewForm(String oid, Model model) throws Exception {
	KETSalesProjectDTO SalesDTO = new KETSalesProjectDTO();
	KETSalesCSMeetingManage manage = null;
	manage = (KETSalesCSMeetingManage) CommonUtil.getObject(oid);

	// QueryResult qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);

	QueryResult qr = SalesHelper.service.getCsprojectSortedResult(CommonUtil.getFullOIDString(manage));

	Department targetDept;

	KETSalesProject sales = null;

	List<Map<String, Object>> rsltList = new ArrayList<Map<String, Object>>();
	Map<String, Object> rslt = null;
	KETSalesCSMeetingManageLink manageLink = null;

	while (qr.hasMoreElements()) {
	    Object[] obj = (Object[]) qr.nextElement();
	    manageLink = (KETSalesCSMeetingManageLink) obj[1];

	    sales = manageLink.getCsProject();
	    rslt.put("sortNo", manageLink.getDeptSortNo());// 부서별로 순서가정해져있다. CSProjectSortEnum.java 참고
	    rslt.put("mysortNo", manageLink.getSortNo());

	    rsltList.add(rslt);
	}

	// Collections.sort(rsltList, ComparatorUtil.SALESMANGESORT);
	SalesDTO.setCsPorjectList(rsltList);
    }

    @RequestMapping("/csFileOpen")
    public void csFileOpen(HttpServletResponse response, KETSalesProjectDTO paramObject) throws Exception {
	String csDir = paramObject.getCsDir();
	String csFileName = paramObject.getCsFileName();
	String filename = "";

	if ("zip".equals(paramObject.getCsFileType())) {
	    WTProperties prop = WTProperties.getServerProperties();
	    String wthome = prop.getProperty("wt.home");
	    String zipDir = "";
	    zipDir = wthome + "\\csMeeting\\zip\\" + DateUtil.getCurrentDateString("date");
	    checkDir(new File(zipDir));

	    File targetDir = new File(zipDir);

	    checkDir(targetDir);
	    int dirCnt = 0;

	    File[] files = targetDir.listFiles();

	    if (files == null) {

	    } else {
		for (int i = 0; i < files.length; ++i) {
		    File file = files[i];

		    if (file.isDirectory()) {
			dirCnt++;
		    }
		}
	    }

	    dirCnt += 1;

	    zipDir += "\\" + Integer.toString(dirCnt);
	    checkDir(new File(zipDir));
	    csFileName = csFileName + ".zip";

	    filename = zipDir + "\\" + csFileName;
	    if (DRMHelper.useDrm) {
		encryptFile(csDir);
	    }
	    ZipUtil.zip(csDir, filename);
	} else {
	    csFileName = csFileName + ".pptx";
	    filename = csDir + "\\" + csFileName;
	    if (DRMHelper.useDrm) {
		encryptFile(csDir);
	    }

	}

	/*
	 * System.out.println(filename);
	 * 
	 * String userAgent = request.getHeader("User-Agent"); boolean ie = userAgent.indexOf("MSIE") > -1; // file path // String sWtHome =
	 * wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	 * 
	 * // String folder = "D:/ptc/Windchill_10.2/Windchill/codebase/extcore/jsp/bom/tmp/"; String sWtHome =
	 * wt.util.WTProperties.getLocalProperties().getProperty("wt.home", ""); String folder = sWtHome +
	 * "\\codebase\\extcore\\jsp\\bom\\"; String ketHost = request.getServerName();
	 * 
	 * String downFilePath = folder + filename;
	 * 
	 * System.out.println("downFilePath==>" + downFilePath);
	 */

	// URI fileUrl = new URI("http://"+ketHost + filepath);
	// File excelFileObj = new File(filename);

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Added by MJOH, 2011-04-18
	// 엑셀 다운로드 파일 DRM 암호화 적용//e3ps.common.drm
	// String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
	// excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////

	/*
	 * FileInputStream fin = new FileInputStream(filename);
	 * 
	 * int ifilesize = (int) excelFileObj.length(); byte b[] = new byte[ifilesize];
	 * 
	 * response.setContentLength(ifilesize);
	 * response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation;charset=UTF-8");
	 * response.setHeader("Content-Disposition", "attachment; filename=\"" + excelFileObj.getName() + "\";");
	 * response.setHeader("Content-Transfer-Encoding", "binary");
	 * 
	 * OutputStream oout = response.getOutputStream();
	 * 
	 * fin.read(b); oout.write(b, 0, ifilesize); oout.close(); fin.close();
	 */

	/*
	 * byte fileByte[] = FileUtils.readFileToByteArray(new File(filename));
	 * 
	 * response.setContentType("application/octet-stream"); response.setContentLength(fileByte.length);
	 * response.setHeader("Content-Disposition", "attachment; fileName=\"" + excelFileObj.getName()+"\";");
	 * response.setHeader("Content-Transfer-Encoding", "binary"); response.getOutputStream().write(fileByte);
	 * 
	 * response.getOutputStream().flush(); response.getOutputStream().close();
	 */
	csFileName = java.net.URLEncoder.encode(csFileName, "UTF-8").replaceAll("\\+", "%20");

	response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
	response.setHeader("Content-Disposition", "attachment; filename=" + csFileName);
	response.setHeader("Content-Transfer-Encoding", "binary");
	response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	OutputStream out = response.getOutputStream();
	FileInputStream fis = null;
	try {
	    File file = new File(filename);

	    fis = new FileInputStream(file);
	    byte[] buffer = new byte[4096];
	    int bytesRead = -1;

	    // write bytes read from the input stream into the output stream
	    while ((bytesRead = fis.read(buffer)) != -1) {
		out.write(buffer, 0, bytesRead);
	    }

	    fis.close();
	    out.close();
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (out != null) {
		try {
		    out.close();
		} catch (Exception e) {
		}
	    }
	    if (fis != null) {
		try {
		    fis.close();
		} catch (Exception e) {
		}
	    }

	}

	out.flush();

    }

    public void encryptFile(String path) throws Exception {

	WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();

	String id = CommonUtil.getOIDLongValue2Str(user);

	File dirFile = new File(path);
	File[] fileList = dirFile.listFiles();
	for (File tempFile : fileList) {
	    if (tempFile.isFile()) {
		drmCall(path + "\\" + tempFile.getName(), id);
	    }
	}
    }

    public void drmCall(String fileName, String id) throws Exception {

	File file = new File(fileName);

	File org_file = null;
	File tar_file = null;

	try {
	    HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

	    String clientIP = req.getHeader("X-FORWARDED-FOR");
	    if (StringUtils.isEmpty(clientIP)) {
		clientIP = req.getRemoteAddr();
	    }

	    org_file = DRMHelper.encryptDownLoad(file, id, clientIP, "Y", "csMeeting");
	    tar_file = new File(fileName);
	    copyFile(org_file, tar_file);
	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /**
     * <pre>
     * 파일 복사
     * </pre>
     * 
     * @param orgFile
     *            File
     * @param tarFile
     *            File
     * @return void
     * @throws WTException
     */
    public void copyFile(File orgFile, File tarFile) throws WTException {
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;

	try {
	    bis = new BufferedInputStream(new FileInputStream(orgFile));
	    bos = new BufferedOutputStream(new FileOutputStream(tarFile));

	    int len = 0;
	    byte[] b = new byte[2048];

	    while ((len = bis.read(b)) > 0) {
		bos.write(b, 0, len);
		bos.flush();
	    }
	} catch (Exception e) {
	    throw new WTException(e.toString());
	} finally {
	    if (bis != null) {
		try {
		    bis.close();
		} catch (Exception e) {
		}
	    }

	    if (bos != null) {
		try {
		    bos.close();
		} catch (Exception e) {
		}
	    }
	}
    }

    // CS회의 일괄삭제
    @RequestMapping("/deleteLastProject")
    @ResponseBody
    public String deleteLastProject(@RequestParam(value = "arroid") String arroid, Model model) throws Exception {

	try {
	    SalesHelper.service.lastProjectDelete(arroid);
	    return "S";

	} catch (Exception e) {
	    e.printStackTrace();
	    return "Fail";
	}
    }

    @RequestMapping("/feignTest")
    @ResponseBody
    public List<Map<String, Object>> feignTest(@RequestParam Map<String, Object> reqMap) throws Exception {

	String value = (String) reqMap.get("test");

	List<Map<String, Object>> rsltList = new ArrayList<Map<String, Object>>();
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("test", "testValue");
	rsltList.add(map);

	Hashtable paramMap = new Hashtable();

	for (String key : reqMap.keySet()) {
	    paramMap.put(key, reqMap.get(key));
	}

	KETDmsHelper.service.insertProjectDoc(paramMap);

	return rsltList;
    }

    @RequestMapping(value = "/feignTest2", method = RequestMethod.POST)
    @ResponseBody
    public String feignTest2(UploadBO paramObject) {

	try {

	    String filePath = "D:\\ptc\\yesone\\";
	    File dir = new File(filePath);
	    if (!dir.isDirectory()) {
		dir.mkdirs();
	    }

	    MultipartFile file = paramObject.getPrimaryFile();

	    String fileName = file.getOriginalFilename();
	    File uploadFile = new File(filePath + "\\" + fileName);

	    file.transferTo(uploadFile);

	} catch (IOException ex) {

	    ex.printStackTrace();
	}

	return "S";
    }

    @RequestMapping("/getDocCategoryList")
    @ResponseBody
    public List<Object> getDocCategoryList(@RequestParam Map<String, Object> reqMap) throws Exception {

	List<Object> list = KETDmsHelper.service.selectDocCategoryForTree("PD100001", false);

	System.out.println("list : ");
	return list;

    }

}
