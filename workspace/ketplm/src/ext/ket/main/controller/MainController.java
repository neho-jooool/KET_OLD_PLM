package ext.ket.main.controller;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import wt.fc.PagingSessionHelper;
import wt.fc.Persistable;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.workflow.work.WorkItem;
import e3ps.common.content.ContentUtil;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.util.Base64;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.web.PageControl;
import e3ps.cost.util.StringUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.dao.MoldChangeOrderDao;
import e3ps.edm.dao.EPMDocumentDao;
import e3ps.groupware.board.Notify;
import e3ps.project.E3PSTask;
import e3ps.project.IssueType;
import e3ps.project.dao.IssueDao;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.ClassifyPBOUtil;
import ext.ket.cost.entity.CostReport;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.issue.service.IssueHelper;
import ext.ket.main.controller.service.MainService;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.dto.PartSearchMainDTO;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.util.SessionUtil;
import ext.ket.shared.util.TimerUtil;
import ext.ket.wfm.entity.MyDocumentDTO;
import ext.ket.wfm.entity.WorkItemDTO;
import ext.ket.wfm.service.KETWorkflowHelper;
import ext.ket.wfm.service.KETWorkspaceHelper;

@Controller
public class MainController {

    @Inject
    private MainService service;

    /**
     * 메인 화면
     * 
     * @param lang
     * @param model
     * @throws Exception
     * @메소드명 : main
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main")
    public void main(@RequestParam(defaultValue = "ko") String lang, @CookieValue(value = "kms_linkID", defaultValue = "0") String kms_linkID, Model model, HttpServletRequest request)
	    throws Exception {

	String isLoginCheck = "ok";

	// ep의 접속 로그인 아이디와 plm의 접속 아이디가 같은지 판단, 같지 않다면 main.jsp에서 접속불가처리한다. 단 ep접속자가 시스템 관리자일 경우는 제외. 2017.09.22 by 황정태 적용
	String kmsLinkId = new String(Base64.decode(kms_linkID), "UTF-8");
	if (e3ps.common.util.StringUtil.checkString(kmsLinkId)) {
	    String[] kmsLinkArr = kmsLinkId.split("[|]");
	    String kmsId = kmsLinkArr[0];
	    // String kmsPw = kmsLinkArr[1];
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	    boolean isadmin = false;
	    if (kmsId.equals("neho") || kmsId.equals("babiss") || kmsId.equals("sspark") || kmsId.equals("cbs")) {
		isadmin = true;
	    }
	    if (!kmsId.equalsIgnoreCase(user.getName()) && !(isadmin)) {
		isLoginCheck = "no";

		List<Map<String, String>> retire_list = KETWorkspaceHelper.service.getMyRetireUserList(kmsId);

		for (Map<String, String> retire : retire_list) {// ep의 접속계정(쿠키)이 현재 접속하려는 계정에 대한 권한을 가지고 있는지 체크
		    if (retire.get("RETIRE_ID").equalsIgnoreCase(user.getName())) {
			isLoginCheck = "ok";
			break;
		    }
		}

	    }

	    if (user != null && user.getName().startsWith("kplus")) {// 조회권한일 경우 일단 접속 허용
		isLoginCheck = "ok";
	    }
	}

	String url = request.getRequestURL().toString();

	if (StringUtils.contains(url, "plm.ket.com") || StringUtils.contains(url, "plmapdev.ket.com")) {
	} else {
	    isLoginCheck = "no";
	}

	int limit = 100;
	String notiOid = "";
	Timestamp currentDate = new Timestamp(new Date().getTime());
	QuerySpec qsaa = new QuerySpec(Notify.class);
	if (qsaa.getConditionCount() > 0) {
	    qsaa.appendAnd();
	}
	// 공지사항 자동삭제 기능을 삭제함에 따라 게시기한일을 쿼리 조건으로 추가함 2015.06.25 황정태
	qsaa.appendWhere(new SearchCondition(Notify.class, "deadline", ">=", currentDate), new int[] { 0 });

	qsaa.appendOrderBy(new OrderBy(new ClassAttribute(Notify.class, "thePersistInfo.createStamp"), true), new int[] { 0 });
	QueryResult resultaa = PagingSessionHelper.openPagingSession(0, limit, qsaa);
	while (resultaa.hasMoreElements()) {
	    Object[] o = (Object[]) resultaa.nextElement();
	    Notify noti = (Notify) o[0];
	    if (notiOid.length() > 0)
		notiOid = notiOid + ",";
	    notiOid = notiOid + CommonUtil.getOIDString(noti);
	}

	model.addAttribute("user", SessionHelper.manager.getPrincipal());
	model.addAttribute("notiOid", notiOid);
	model.addAttribute("isLoginCheck", isLoginCheck);
    }

    /**
     * home 화면
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : mainContents
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/mainContents")
    public String mainContents(Model model) throws Exception {
	TimerUtil timer = new TimerUtil(getClass().getName());
	timer.setStartPoint("myTask");
	model.addAttribute("myTask", KETWorkspaceHelper.service.getMainContentsMyTask());
	timer.setEndPoint();
	timer.setStartPoint("myProject");
	model.addAttribute("myProject", service.getMainContentsMyProject(CommonUtil.getOIDLongFromSessionUser()));
	timer.setEndPoint();
	timer.setStartPoint("myTodo");
	model.addAttribute("myTodo", KETWorkflowHelper.service.getMyTodoCnt());
	timer.setEndPoint();
	timer.display();
	// model.addAttribute("myTodo", service.getMainContentsMyTodo(CommonUtil.getOIDLongFromSessionUser()));
	return "noExtends:/main/mainContents";
    }

    /**
     * 메뉴 data
     * 
     * @param menuCode
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : menuData
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/menuData")
    public String menuData(String menuCode, Model model) throws Exception {

	int listWorkItemCnt = 0;
	int listTodoCnt = 0;
	int issueTodoCnt = 0;
	if ("workspace".equals(menuCode)) {
	    listWorkItemCnt = KETWorkflowHelper.service.getFilterUncompletedWorkItemCount();
	    listTodoCnt = KETWorkspaceHelper.service.getFilterUncompletedTodoCount();
	    issueTodoCnt = IssueHelper.service.getIssueTodoListCnt();
	}
	model.addAttribute("menuCode", menuCode);
	model.addAttribute("listWorkItemCnt", listWorkItemCnt);
	model.addAttribute("listTodoCnt", listTodoCnt);
	model.addAttribute("issueTodoCnt", issueTodoCnt);

	return "noExtends:/main/menuData";
    }

    /**
     * 공지메일 클릭 시 해당 화면으로 이동시키는 컨트롤러
     * 
     * @param pbo
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : mailRedirect
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/mailRedirect")
    public String mailRedirect(String pboOid, Model model) throws Exception {

	Persistable pbo = null;
	String oid = "";
	String redirectURL = "";
	boolean isTODO = false;

	if (pboOid.indexOf("KETDashBoardMailSetting") > 0) {
	    oid = pboOid;
	} else if ("KETMyWorkDelay".equals(pboOid)) {
	    oid = pboOid;
	} else if ("KETMYTODO".equals(pboOid)) {
	    oid = pboOid;
	} else if ("KETMYTASK".equals(pboOid)) {
	    oid = pboOid;
	} else if ("KETRetireWorkDelay".equals(pboOid)) {
	    oid = pboOid;
	} else {
	    pbo = CommonUtil.getObject(pboOid);
	    if (pbo == null) {
		oid = "notExist";
	    } else {
		WorkItem item = KETWorkflowHelper.service.getWorkItem(pbo, (WTUser) SessionHelper.manager.getPrincipal());

		if (item != null) {
		    String itemOid = StringUtil.checkNull(CommonUtil.getOIDString(item.getPrimaryBusinessObject().getObject()));
		    String itemVROid = StringUtil.checkNull(CommonUtil.getVROID(item.getPrimaryBusinessObject().getObject()));
		    String pboVROid = StringUtil.checkNull(CommonUtil.getVROID(pboOid));
		    // System.out.println("pboOid : "+pboOid);
		    // System.out.println("itemOid : "+itemOid);
		    // System.out.println("itemVROid : "+itemVROid);
		    // System.out.println("pboVROid : "+pboVROid);
		    if (!pboOid.equals(itemOid) && !pboVROid.equals(itemVROid)) {
			// oid = pboOid;
			// if (pbo instanceof KETSamplePart) {
			// redirectURL = ClassifyPBOUtil.getTaskUrl(pbo);
			// isTODO = true;
			// }
			oid = "notExist";// 상신취소된 oid가 링크된 메일을 클릭했을 가능성이 매우 높다
		    } else {
			WorkItemDTO dto = new WorkItemDTO(item);
			oid = dto.getOid();
			redirectURL = dto.getViewTaskUrl();
			isTODO = dto.isTodo();
		    }

		} else {
		    oid = pboOid;
		    if (pbo instanceof KETSamplePart) {
			redirectURL = ClassifyPBOUtil.getTaskUrl(pbo);
			isTODO = true;
		    }

		    if (pbo instanceof CostReport) {
			CostReport obj = (CostReport) pbo;
			E3PSTask task = obj.getTask();
			String taskOid = CommonUtil.getOIDString(task);
			redirectURL = "/plm/ext/cost/costReportPopup.do?taskOid=" + taskOid;
		    }

		    if (pbo instanceof KETInvestTask) {
			KETInvestTask obj = (KETInvestTask) pbo;

			oid = CommonUtil.getOIDString(obj);
			redirectURL = "/plm/ext/invest/investTaskPopup?oid=" + oid;
		    }

		    if (pbo instanceof KETProjectDocument) {
			KETProjectDocument obj = (KETProjectDocument) pbo;

			oid = CommonUtil.getOIDString(obj);
			redirectURL = "/plm/jsp/dms/ViewDocument.jsp?oid=" + oid;
		    }

		}
	    }

	}
	model.addAttribute("oid", oid);
	model.addAttribute("isTODO", isTODO);
	model.addAttribute("redirectURL", redirectURL);

	return "noExtends:/main/mailRedirect";
    }

    /**
     * 결재 완료함
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : listCompletedWorkItem
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main/listCompletedWorkItem")
    public String listCompletedWorkItem(Model model) throws Exception {
	WorkItemDTO workItemDTO = new WorkItemDTO();
	workItemDTO.setFormPage(20);
	workItemDTO.setCommand("listCompletedWorkItem");
	if (workItemDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	TimerUtil timer = new TimerUtil(getClass().getName());
	timer.setStartPoint("listCompletedWorkItem Query");
	PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
	timer.setEndPoint();
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	QueryResult queryResult = pageControl.getResult();
	List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
	timer.setStartPoint("create workitem dto");
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    list.add(new WorkItemDTO((KETWfmApprovalMaster) objArr[0], true));
	}
	timer.setEndPoint();
	timer.display();
	model.addAttribute("workItemList", list);
	return "noExtends:/main/listWorkItem";
    }

    /**
     * 결재 대기함
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : listWorkItem
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main/listWorkItem")
    public String listWorkItem(Model model) throws Exception {

	WorkItemDTO workItemDTO = new WorkItemDTO();
	workItemDTO.setCommand("listWorkItem");
	workItemDTO.setFormPage(20);
	if (workItemDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	// TimerUtil timer = new TimerUtil(getClass().getName());
	// timer.setStartPoint("main/listWorkItem");
	PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
	// timer.setEndPoint();
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	QueryResult queryResult = pageControl.getResult();
	List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
	// List<WorkItemDTO> list = KETWorkflowHelper.service.getUncompletedWorkItems();
	// timer.setStartPoint("create workitem dto");
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    list.add(new WorkItemDTO((WorkItem) objArr[0]));
	}
	// timer.setEndPoint();
	// timer.display();
	model.addAttribute("workItemList", list);
	return "noExtends:/main/listWorkItem";
    }

    /**
     * 결재 진행함
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : listInProgressWorkItem
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main/listInProgressWorkItem")
    public String listInProgressWorkItem(Model model) throws Exception {

	WorkItemDTO workItemDTO = new WorkItemDTO();
	workItemDTO.setCommand("listInProgressWorkItem");
	workItemDTO.setFormPage(20);
	if (workItemDTO.getPage() == 0) {
	    SessionUtil.removeAttribute("pageSessionId");
	}
	TimerUtil timer = new TimerUtil(getClass().getName());
	timer.setStartPoint("listInProgressWorkItem Query");
	PageControl pageControl = KETWorkflowHelper.service.findPaging(workItemDTO);
	timer.setEndPoint();
	SessionUtil.setAttribute("pageSessionId", pageControl.getSessionId() + "");
	QueryResult queryResult = pageControl.getResult();
	List<WorkItemDTO> list = new ArrayList<WorkItemDTO>();
	timer.setStartPoint("create workitem dto");
	while (queryResult.hasMoreElements()) {
	    Object[] objArr = (Object[]) queryResult.nextElement();
	    list.add(new WorkItemDTO((KETWfmApprovalMaster) objArr[0], false));
	}
	timer.setEndPoint();
	timer.display();
	model.addAttribute("workItemList", list);
	return "noExtends:/main/listWorkItem";
    }

    /**
     * My Document
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : listMyDocument
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main/listMyDocument")
    public String listMyDocument(Model model) throws Exception {

	MyDocumentDTO dto = new MyDocumentDTO();
	dto.setCommand("listMyDocument");
	dto.setRowNum(20);
	List<BaseDTO> list = KETWorkspaceHelper.service.find(dto);
	int size = 20;
	if (list.size() < 20)
	    size = list.size();
	list = list.subList(0, size);
	model.addAttribute("myDocuments", list);
	return "noExtends:/main/listMyDocument";
    }

    /**
     * MyDrawing
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : listMyDrawing
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main/listMyDrawing")
    public String listMyDrawing(Model model) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	KETParamMapUtil paramMap = new KETParamMapUtil();
	paramMap.put("businessType", CommonUtil.getDivisionNumberCodeOidFromSessionUser());
	paramMap.put("creator", CommonUtil.getOIDString(SessionHelper.manager.getPrincipal()));
	paramMap.put(
	        "TGData",
	        "&lt;Grid&gt;&lt;Cfg Sort=\"-CreateDate\" SortCols=\"CreateDate\" SortTypes=\"1\" Group=\"\" GroupCols=\"\" SearchAction=\"\" TimeZone=\"-540\"/&gt;&lt;Filters&gt;&lt;/Filters&gt;&lt;IO/&gt;&lt;/Grid&gt;");
	paramMap.put("perPage", 20);
	paramMap.put("latest", true);

	TgPagingControl pager = new TgPagingControl(true, paramMap);
	boolean allDataNotPagingData = false;
	List<Map> conditionList = new ArrayList<Map>();// KETParamMapUtil.getConditionListForSearchInResult("SearchEPMDocument",
	                                               // searchInResult, req);
	conditionList.add(paramMap);
	EPMDocumentDao epmDocumentDao = new EPMDocumentDao();
	List<Map<String, Object>> edmList = epmDocumentDao.searchEPMDocument(conditionList, pager, allDataNotPagingData);

	String creator = "";
	String drawingNo = "";
	String drawingName = "";
	String version = "";
	String state = "";
	String drawingOid = "";
	String dummy = "";
	String cadType = "";
	String holderOid = "";
	String appDataOid = "";
	String extension = "";
	Timestamp createStamp = null;
	String iconPath = "";
	String fileDoownloadUrl = "";
	String projectNo = "";
	String projectName = "";
	String createDeptName = "";
	String partNumber = "";
	String devStage = "";
	String category = "";
	String oid = "";
	String masterOid = "";

	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	Map<String, Object> edmObj = null;
	for (int i = 0; i < edmList.size(); i++) {
	    edmObj = edmList.get(i);
	    drawingOid = (String) edmObj.get("drawingOid");
	    masterOid = (String) edmObj.get("drawingMasterOid");
	    drawingNo = (String) edmObj.get("drawingNo");
	    drawingName = (String) edmObj.get("drawingName");
	    version = (String) edmObj.get("ver"); //
	    state = (String) edmObj.get("status"); //
	    createStamp = (Timestamp) edmObj.get("createDate"); //
	    holderOid = (String) edmObj.get("holderOid");
	    appDataOid = (String) edmObj.get("appDataOid");
	    extension = (String) edmObj.get("extension");
	    creator = (String) edmObj.get("creator");
	    cadType = (String) edmObj.get("cadType");
	    dummy = (String) edmObj.get("dummy"); //
	    dummy = "Y".equals(dummy) ? dummy : "";
	    // 주 첨부파일
	    if (holderOid != null && appDataOid != null) {
		fileDoownloadUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + holderOid + "&cioids=" + appDataOid + "&role=PRIMARY";
		iconPath = ContentUtil.getContentIconPath(extension);
	    } else {
		fileDoownloadUrl = "";
		iconPath = "";
	    }
	    String viewUrl = "javascript:openView('" + drawingOid + "');";
	    projectNo = (String) edmObj.get("projectNo"); // ProjectNo
	    projectName = (String) edmObj.get("projectName"); // ProjectName
	    createDeptName = (String) edmObj.get("createDeptName"); // CreateDeptName
	    partNumber = (String) edmObj.get("partNumber"); // PartNumber
	    devStage = (String) edmObj.get("devStage"); // DevStage
	    category = (String) edmObj.get("category"); // Category

	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("Oid", drawingOid);
	    map.put("OidMaster", masterOid);
	    map.put("DrawingNo", drawingNo);
	    map.put("viewUrl", viewUrl);
	    map.put("FileIcon", iconPath);
	    map.put("fileDoownloadUrl", fileDoownloadUrl);
	    map.put("DrawingName", drawingName);
	    map.put("Ver", version);
	    map.put("CADType", cadType);
	    map.put("Creator", creator);
	    map.put("CreateDate", DateUtil.getDateString(createStamp, "d"));
	    map.put("Status", State.toState(state).getDisplay(messageService.getLocale()));
	    map.put("Dummy", dummy);
	    map.put("ProjectNo", projectNo);
	    map.put("ProjectName", projectName);
	    map.put("CreateDeptName", createDeptName);
	    map.put("partNumber", partNumber);
	    map.put("devStage", devStage);
	    map.put("category", category);

	    list.add(map);
	}
	model.addAttribute("myDrawings", list);
	return "noExtends:/main/listMyDrawing";
    }

    /**
     * MyPart
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : listMyPart
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 10.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main/listMyPart")
    public String listMyPart(Model model) throws Exception {
	PartSearchMainDTO partSearchMainDTO = new PartSearchMainDTO();
	partSearchMainDTO.setFromMyPart("Y");
	partSearchMainDTO.setSortName("-createDate");
	// partSearchMainDTO.setPerPage(20);
	partSearchMainDTO.setFormPage(20);
	PageControl pageControl = PartBaseHelper.service.searchMainPartList(partSearchMainDTO, "-1");
	List<?> list = pageControl.getResltList();
	model.addAttribute("myParts", list);

	return "noExtends:/main/listMyPart";
    }

    /**
     * My ECM
     * 
     * @param model
     * @return
     * @throws Exception
     * @메소드명 : listMyECM
     * @작성자 : sw775.park
     * @작성일 : 2014. 9. 12.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    @RequestMapping("/main/listMyECM")
    public String listMyECM(Model model) throws Exception {

	MoldChangeOrderDao dao = new MoldChangeOrderDao();
	String detailInfoUrl = null;
	String detailProjectPopup = null;
	KETParamMapUtil hash = new KETParamMapUtil();
	hash.put("TGData", "<Grid><Cfg Sort=\"-CreateDate\" SortCols=\"CreateDate\" SortTypes=\"1\" Group=\"\" GroupCols=\"\" SearchAction=\"\" TimeZone=\"-540\"/><Filters></Filters><IO/></Grid>");
	hash.put("perPage", 20);
	TgPagingControl pager = new TgPagingControl(true, hash);
	List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
	Map<String, Object> paramMap = new HashMap<String, Object>();
	paramMap.put("creatorOid", CommonUtil.getOIDLongValue(SessionHelper.manager.getPrincipal()));
	conditionList.add(paramMap);

	List<Map<String, Object>> list = dao.searchMyEcmList(false, hash, conditionList, pager);
	conditionList.add(hash);

	List<Map<String, Object>> ecmList = new ArrayList<Map<String, Object>>();
	for (Map<String, Object> ecoVo : list) {
	    String prodMold = (String) ecoVo.get("ProdMoldClsName"); // 구분
	    String ecoNo = (String) ecoVo.get("EcoId"); // ECO No
	    String ecoName = (String) ecoVo.get("EcoName"); // ECO 제목
	    String projectNo = (String) ecoVo.get("ProjectNo"); // Project No
	    String reason = (String) ecoVo.get("ChangeReason"); // 사유
	    String budget = (String) ecoVo.get("SecureBudgetYn"); // 예산
	    String creator = (String) ecoVo.get("CreatorName"); // 작성자
	    String createdate = (String) ecoVo.get("CreateDate"); // 작성일자
	    String stateFlag = (String) ecoVo.get("SancStateFlag"); // 결재 상태
	    String oid = (String) ecoVo.get("Oid");
	    String proOid = (String) ecoVo.get("ProjectOid");

	    // 제품 ECO
	    if (oid.lastIndexOf("KETProdChangeOrder") > 0) {
		detailInfoUrl = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid=" + oid;
	    }
	    // 금형 ECO
	    else if (oid.lastIndexOf("KETMoldChangeOrder") > 0) {
		detailInfoUrl = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid=" + oid;
	    }
	    // 제품 ECR
	    else if (oid.lastIndexOf("KETProdChangeRequest") > 0) {
		detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?cmd=PopupView&oid=" + oid;
	    }
	    // 금형 ECR
	    else {
		detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?cmd=PopupView&oid=" + oid;
	    }
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("ProdMoldCls", prodMold);
	    map.put("EcoNo", ecoNo);
	    map.put("detailInfoUrl", detailInfoUrl);
	    map.put("EcoName", ecoName);
	    map.put("ProjectNo", projectNo);
	    map.put("proOid", proOid);
	    map.put("ChangeReason", reason);
	    map.put("BudgetYn", budget);
	    map.put("CreateDate", createdate);
	    map.put("StateAppro", stateFlag);
	    ecmList.add(map);
	}
	model.addAttribute("myECMs", ecmList);

	return "noExtends:/main/listMyECM";
    }

    @RequestMapping("/main/listMyIssue")
    public String listMyIssue(Model model) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	Connection conn = null;
	KETParamMapUtil paramMap = new KETParamMapUtil();
	paramMap.put("locale", messageService.getLocale());
	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	paramMap.put("user", CommonUtil.getOIDLongValue(user));
	paramMap.put("myAnswerList", "myAnswerList");
	paramMap.put("myIssueType", "MyAnswerIssue");
	paramMap.put("command", "searchMyIssue");
	paramMap.put("perPage", 20);
	List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
	conditionList.add(paramMap);
	conn = PlmDBUtil.getConnection();
	IssueDao dao = new IssueDao(conn);
	List<Map<String, Object>> list = dao.searchIssueList(conditionList);
	List<Map<String, Object>> issueList = new ArrayList<Map<String, Object>>();
	for (int i = 0; i < list.size(); i++) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    Map<String, Object> obj = list.get(i);
	    String isDone = "";
	    if (obj.get("isDone").equals("1")) {
		isDone = messageService.getString("e3ps.message.ket_message", "02171");// 완료
	    } else {
		if (obj.get("answerPlanDate") != null && !obj.get("answerPlanDate").toString().equalsIgnoreCase("null") && !obj.get("answerPlanDate").equals("")) {
		    if (DateUtil.getCurrentTimestamp().compareTo(Timestamp.valueOf(obj.get("answerPlanDate").toString())) < 0) {
			// 지연
			isDone = "&lt;font color=&apos;red&apos;&gt;" + messageService.getString("e3ps.message.ket_message", "02703") + "&lt;/font&gt;";
		    } else {
			isDone = messageService.getString("e3ps.message.ket_message", "02726");// 진행
		    }
		} else {
		    isDone = messageService.getString("e3ps.message.ket_message", "02726");// 진행
		}
	    }
	    String issueType = "";
	    IssueType[] issueTypeList = IssueType.getIssueTypeSet();
	    for (int j = 0; j < issueTypeList.length; j++) {
		if (issueTypeList[j].getDisplay(Locale.ENGLISH).equals(obj.get("issueType").toString())) {
		    issueType = issueTypeList[j].getDisplay(messageService.getLocale());
		    break;
		}
	    }
	    String pjtOid = obj.get("pjtOid").toString();
	    String taskOid = obj.get("taskOid").toString();
	    int type = 0;
	    if (taskOid.equals(":0")) {
		taskOid = pjtOid;
		if (obj.get("pjtTypeName").equals("금형")) {
		    type = 3;
		}
		if (obj.get("pjtTypeName").equals("제품")) {
		    type = 2;
		} else {
		    type = 1;
		}
	    }
	    map.put("pjtNo", obj.get("pjtNo"));
	    map.put("taskOid", taskOid);
	    map.put("type", type);
	    map.put("pjtName", obj.get("pjtName"));
	    map.put("issueType", issueType);
	    map.put("subject", obj.get("subject"));
	    map.put("issueOid", obj.get("issueOid"));
	    map.put("urgency", obj.get("urgency"));
	    map.put("importance", obj.get("importance"));
	    map.put("ownerName", obj.get("ownerName"));
	    map.put("createDate", DateUtil.getDateString(DateUtil.getTimestampFormat((String) obj.get("createDate"), "yyyy-MM-dd HH:mm:ss"), "d"));
	    map.put("isDone", isDone);
	    issueList.add(map);
	}
	model.addAttribute("myIssueList", issueList);
	return "noExtends:/main/listMyIssue";
    }
}
