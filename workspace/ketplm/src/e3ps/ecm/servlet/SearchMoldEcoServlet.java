package e3ps.ecm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.fc.WTObject;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.ecm.dao.MoldChangeOrderDao;
import e3ps.ecm.dao.SearchMoldChangeOrderDao;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETSearchEcoDetailVO;
import e3ps.ecm.entity.KETSearchEcoVO;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.edm.approval.internal.EpmApprovalHistoryHandler;
import ext.ket.shared.log.Kogger;

public class SearchMoldEcoServlet extends CommonServlet {
    private static final long serialVersionUID = 8419728326730565994L;

    private String cmd = null; // command
    private String partOid = null; // PART OID
    private String partNo = null; // Part Number
    private String projectOid = null; // 관련 프로젝트oid
    private String projectNo = null; // 관련 프로젝트번호
    private String projectName = null; // 관련 프로젝트명
    private String orgOid = null; // ECO 작성부서 OID
    private String orgName = null; // ECO 작성부서명
    private String creatorOid = null; // ECO 작성자 OID
    private String ecoId = null; // ECO ID
    private String ecoName = null; // ECO 제목
    private String divisionFlag = null; // 사업부구분
    private String createStartDate = null; // 작성시작일자
    private String createEndDate = null; // 작성종료일자
    private String prodMoldCls = null; // 제품/금형
    private String devYn = null; // 개발/양산구분
    private String sancStateFlag = null; // 상태
    private String fromPage = null; // 요청페이지
    private String active = null; // 활동계획(도면)

    // 2013.03.13 e3ps shkim add
    private String ecrNumber = null;

    // ECO의 기본사항탭의 Die No 정보
    private String pNums = null;

    /**
     * 
     * 함수명 : doService 설명 : 요청된 파라미터를 처리한 후 command에 따라서 기능을 호출한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    @SuppressWarnings("rawtypes")
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	processParameter(request, response);

	if (cmd == null || cmd.equals("")) {
	    cmd = "search";
	}

	if (cmd.equals("search")) {
	    search(request, response);
	    gotoResult(request, response, "/jsp/ecm/SearchEcoForm.jsp");
	} else if (cmd.equals("excel")) {
	    excel(request, response);
	} else if (cmd.equals("searchEca")) {
	    search(request, response);
	    gotoResult(request, response, "/jsp/ecm/SearchEcaListForm.jsp");
	} else if (cmd.equals("moldCostexcel")) {
	    moldCostexcel(request, response);
	}

	// ECO 리스트
	else if ("searchGridData".equals(cmd)) {
	    searchGrid4Eco(request, response, false);
	} else if ("searchGridPage".equals(cmd)) {
	    searchGrid4Eco(request, response, true);
	}

	// ECN 리스트
	else if ("searchGridData4Ecn".equals(cmd)) {
	    searchGrid4Ecn(request, response, false);
	} else if ("searchGridPage4Ecn".equals(cmd)) {
	    searchGrid4Ecn(request, response, true);
	}

	// MyEcm 리스트
	else if ("searchGridData4MyEcm".equals(cmd)) {
	    searchGrid4MyEcm(request, response, false);
	} else if ("searchGridPage4MyEcm".equals(cmd)) {
	    searchGrid4MyEcm(request, response, true);
	}

	else if (cmd.equals("searchMoldPlanPopup")) {
	    request.setAttribute("data", searchMoldPlanPopup(request, response));
	    gotoResult(request, response, "/jsp/ecm/SearchMoldPlanPopupListForm.jsp");
	} else if (cmd.equals("searchPartPopup")) {
	    request.setAttribute("data", searchPartPopup(request, response));
	    gotoResult(request, response, "/jsp/ecm/reform/SearchActivityBomSearchResultForm.jsp");
	} else if (cmd.equals("searchEpmPopup")) {
	    request.setAttribute("data", searchEpmPopup(request, response));
	    gotoResult(request, response, "/jsp/ecm/SearchActivityEpmPopupListForm.jsp");
	} else if (cmd.equals("popupEcoSearchInit")) {
	    popupEcoSearchInit(request, response);
	    gotoResult(request, response, "/jsp/ecm/SearchEcoPopupForm.jsp");
	} else if (cmd.equals("popupEcoSearch")) {
	    popupEcoSearch(request, response);
	    gotoResult(request, response, "/jsp/ecm/SearchEcoPopupForm.jsp");
	} else if (cmd.equals("searchEcmPartPopup")) {
	    request.setAttribute("data", searchEcmPartPopup(request, response));
	    gotoResult(request, response, "/jsp/ecm/SearchEcmPartPopupListForm.jsp");
	} else if (cmd.equals("searchProjectVendor")) {
	    HashMap hash = null;
	    String vendorFlag = "";
	    String prodVendor = "";
	    String prodVendorName = "";
	    try {
		hash = MoldProjectHelper.getInoutInfo(projectNo);
		vendorFlag = hash.get("TypeCode").toString().toLowerCase();
		prodVendor = hash.get("PlaceCode").toString();
		prodVendorName = hash.get("Place").toString();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = response.getWriter();
	    String rtn_msg = "";
	    rtn_msg += "\n <script language=\"javascript\">";
	    rtn_msg += "\n   parent.enabledAllBtn();";
	    rtn_msg += "\n   parent.hideProcessing();";
	    rtn_msg += "\n   var form = parent.document.forms[0];";
	    rtn_msg += "\n   form.vendorFlag.value = '" + vendorFlag + "';";
	    rtn_msg += "\n   form.prodVendor.value = '" + prodVendor + "';";
	    rtn_msg += "\n   form.prodVendorName.value = '" + prodVendorName + "';";
	    rtn_msg += "\n </script>";
	    out.println(rtn_msg);
	}
    }

    /**
     * 
     * 함수명 : moldCostexcel 설명 : 제품/금형 ECO 검색결과 목록을 엑셀 파일에 저장한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 황정태 작성일자 : 2020.03.12
     */
    private void moldCostexcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = request.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ecoSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ecoSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ecoSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }
	    conditionList.add(hash);
	    session.setAttribute("ecoSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // Excel File 위치 설정
	    String userAgent = request.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    // file path
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";

	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }

	    // file name
	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sFileName = "SearchECOList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    try {

		WritableWorkbook writebook = Workbook.createWorkbook(new File(sFilePath + "/" + sFileName));
		WritableSheet s1 = writebook.createSheet("ECO 목록", 0);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		int row = 2;
		int rowCount = 0;

		// 문서제목
		Label lr = new Label(0, 0, "ECO 목록");
		s1.addCell(lr);

		lr = new Label(0, row, "No", cellFormat);
		s1.addCell(lr);

		lr = new Label(1, row, "ECOID", cellFormat);
		s1.addCell(lr);

		lr = new Label(2, row, "부품번호", cellFormat);
		s1.addCell(lr);

		lr = new Label(3, row, "DIENO", cellFormat);
		s1.addCell(lr);

		lr = new Label(4, row, "설계변경사유", cellFormat);
		s1.addCell(lr);

		lr = new Label(5, row, "예상비용", cellFormat);
		s1.addCell(lr);

		lr = new Label(6, row, "예산확보", cellFormat);
		s1.addCell(lr);

		lr = new Label(7, row, "사업부", cellFormat);
		s1.addCell(lr);

		lr = new Label(8, row, "작성자", cellFormat);
		s1.addCell(lr);

		lr = new Label(9, row, "작성부서", cellFormat);
		s1.addCell(lr);

		lr = new Label(10, row, "상태", cellFormat);
		s1.addCell(lr);

		List<Map<String, Object>> list = dao.getMoldEcoCostList(false, hash, conditionList);

		for (Map<String, Object> ketSearchEcoDetailVO : list) {

		    row++;
		    // No
		    rowCount++;
		    lr = new Label(0, row, rowCount + "", cellFormat);
		    s1.addCell(lr);

		    // ECO No
		    lr = new Label(1, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("ECOID")), cellFormat);
		    s1.addCell(lr);

		    // PARTNO
		    lr = new Label(2, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("PARTNO")), cellFormat);
		    s1.addCell(lr);

		    // DIENO
		    lr = new Label(3, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("DIENO")), cellFormat);
		    s1.addCell(lr);

		    // 설계변경사유
		    lr = new Label(4, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CHANGEREASON")), cellFormat);
		    s1.addCell(lr);

		    // 예상비용
		    lr = new Label(5, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("EXPECTCOST")), cellFormat);
		    s1.addCell(lr);

		    // 예산확보
		    lr = new Label(6, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("SECUREBUDGETYN")), cellFormat);
		    s1.addCell(lr);

		    // 사업부
		    lr = new Label(7, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("DIVISIONFLAG")), cellFormat);
		    s1.addCell(lr);

		    // 작성자
		    lr = new Label(8, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CREATORNAME")), cellFormat);
		    s1.addCell(lr);

		    // 부서
		    lr = new Label(9, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("DEPTNAME")), cellFormat);
		    s1.addCell(lr);

		    // 상태
		    lr = new Label(10, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("STATESTATENAME")), cellFormat);
		    s1.addCell(lr);

		}
		writebook.write();
		writebook.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		// String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
		// excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////

		FileInputStream fin = new FileInputStream(excelFileObj);
		int ifilesize = (int) excelFileObj.length();
		byte b[] = new byte[ifilesize];

		response.setContentLength(ifilesize);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = response.getOutputStream();
		fin.read(b);
		oout.write(b, 0, ifilesize);
		oout.close();
		fin.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (excelFileObj.isFile()) {
		    excelFileObj.delete();
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-excel

    /**
     * 
     * 함수명 : processParameter 설명 : 요청된 파라미터를 인스턴스 변수에 저장한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 30.
     */
    private void processParameter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	cmd = StringUtil.checkNull(request.getParameter("cmd"));
	partOid = StringUtil.checkNull(request.getParameter("partOid"));
	partNo = StringUtil.checkNull(request.getParameter("partNo"));
	projectNo = StringUtil.checkNull(request.getParameter("projectNo"));
	projectOid = StringUtil.checkNull(request.getParameter("projectOid"));
	projectName = StringUtil.checkNull(request.getParameter("projectName"));
	orgOid = StringUtil.checkNull(request.getParameter("orgOid"));
	orgName = StringUtil.checkNull(request.getParameter("orgName"));
	creatorOid = StringUtil.checkNull(request.getParameter("creatorOid"));
	ecoId = StringUtil.checkNull(request.getParameter("ecoId"));
	ecoName = StringUtil.checkNull(request.getParameter("ecoName"));
	divisionFlag = StringUtil.checkNull(request.getParameter("divisionFlag"));
	createStartDate = EcmUtil.getServerDateFormat(StringUtil.checkNull(request.getParameter("createStartDate")));
	createEndDate = EcmUtil.getServerDateFormat(StringUtil.checkNull(request.getParameter("createEndDate")));
	if ("".equals(createStartDate) && !"".equals(createEndDate)) {
	    createStartDate = "19000101";
	} else if (!"".equals(createStartDate) && "".equals(createEndDate)) {
	    createEndDate = "99991231";
	}
	prodMoldCls = StringUtil.checkNull(request.getParameter("prodMoldCls"));
	devYn = StringUtil.checkNull(request.getParameter("devYn"));
	sancStateFlag = StringUtil.checkNull(request.getParameter("sancStateFlag"));
	fromPage = StringUtil.checkNull(request.getParameter("fromPage"));
	active = StringUtil.checkNull(request.getParameter("acti"));
	// 2013.03.13 e3ps shkim add
	ecrNumber = StringUtil.checkNull(request.getParameter("ecrNumber"));

	pNums = StringUtil.checkNull(request.getParameter("pNums"));
    }

    /**
     * 
     * 함수명 : excel 설명 : 제품/금형 ECO 검색결과 목록을 엑셀 파일에 저장한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 남현승 작성일자 : 2013.07.30
     */
    private void excel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();
	ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = request.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ecoSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ecoSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ecoSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("ecoSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // Excel File 위치 설정
	    String userAgent = request.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    // file path
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";

	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }

	    // file name
	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sFileName = "SearchECOList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    String sortCol1 = "ECO No";
	    String sortCol2 = "ECO 제목";
	    String sortCol6 = "예산확보여부";
	    String sortCol7 = "작성자";
	    String sortCol8 = "승인일자";
	    String sortCol9 = "결재상태";

	    String activityName = "";
	    String completeDate = "";
	    String requestDate = "";

	    try {

		WritableWorkbook writebook = Workbook.createWorkbook(new File(sFilePath + "/" + sFileName));
		WritableSheet s1 = writebook.createSheet("ECO 목록", 0);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		int row = 2;
		int rowCount = 0;

		// 문서제목
		Label lr = new Label(0, 0, "ECO 목록");
		s1.addCell(lr);

		// 타이틀
		lr = new Label(0, row, "No", cellFormat);
		s1.addCell(lr);

		lr = new Label(1, row, sortCol1, cellFormat);
		s1.addCell(lr);

		lr = new Label(2, row, "연계 ECR No", cellFormat);
		s1.addCell(lr);

		lr = new Label(3, row, "연계 ECO No", cellFormat);
		s1.addCell(lr);

		lr = new Label(4, row, "ECO구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(5, row, "단계구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(6, row, "Project No", cellFormat);
		s1.addCell(lr);

		lr = new Label(7, row, "Part No/Die No", cellFormat);
		s1.addCell(lr);

		lr = new Label(8, row, "Part Name", cellFormat);
		s1.addCell(lr);

		lr = new Label(9, row, "고객사", cellFormat);
		s1.addCell(lr);

		lr = new Label(10, row, "차종", cellFormat);
		s1.addCell(lr);

		lr = new Label(11, row, sortCol2, cellFormat);
		s1.addCell(lr);

		lr = new Label(12, row, "설계변경 사유", cellFormat);
		s1.addCell(lr);

		lr = new Label(13, row, "생산성 향상 유형", cellFormat);
		s1.addCell(lr);

		lr = new Label(14, row, "생산처", cellFormat);
		s1.addCell(lr);

		lr = new Label(15, row, sortCol6, cellFormat);
		s1.addCell(lr);

		lr = new Label(16, row, "고객확인 구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(17, row, "조정 및 변경사항", cellFormat);
		s1.addCell(lr);

		lr = new Label(18, row, "CU도면 변경여부", cellFormat);
		s1.addCell(lr);

		lr = new Label(19, row, "작성부서", cellFormat);
		s1.addCell(lr);

		lr = new Label(20, row, sortCol7, cellFormat);
		s1.addCell(lr);

		lr = new Label(21, row, "상신일자", cellFormat);
		s1.addCell(lr);

		lr = new Label(22, row, sortCol8, cellFormat);
		s1.addCell(lr);

		lr = new Label(23, row, sortCol9, cellFormat);
		s1.addCell(lr);

		lr = new Label(24, row, "작성일자", cellFormat);
		s1.addCell(lr);

		lr = new Label(25, row, "사업부", cellFormat);
		s1.addCell(lr);

		lr = new Label(26, row, "원가변동", cellFormat);
		s1.addCell(lr);

		lr = new Label(27, row, "원가증감비율", cellFormat);
		s1.addCell(lr);

		lr = new Label(28, row, "불량구분", cellFormat);
		s1.addCell(lr);

		lr = new Label(29, row, "불량유형", cellFormat);
		s1.addCell(lr);

		List<Map<String, Object>> list = dao.searchEcoList(false, hash, conditionList);

		for (Map<String, Object> ketSearchEcoDetailVO : list) {

		    // =========결재 요청일 관련 시작====================//
		    // Object obj = CommonUtil.getObject(ketSearchEcoDetailVO.getOid());
		    // ReferenceFactory rf = new ReferenceFactory();
		    KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
		    KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
		    KETWfmApprovalMaster master = null;
		    Object temp = new Object();
		    Vector vec = null;
		    WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject((String) ketSearchEcoDetailVO.get("Oid")));
		    if (targetObj != null)
			master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
		    if (master != null) {

			vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
			for (int k = 0; k < vec.size() - 1; k++) {
			    for (int i = k + 1; i < vec.size(); i++) {
				history1 = (KETWfmApprovalHistory) vec.get(k);
				history2 = (KETWfmApprovalHistory) vec.get(k);
				if (history1.getSeqNum() < history2.getSeqNum()) {
				    temp = vec.get(k);
				    vec.set(k, vec.get(i));
				    vec.set(i, temp);
				}
			    }
			}

		    }

		    if (vec != null) {
			boolean isApprover = true;
			activityName = "";
			for (int x = 0; x < vec.size(); x++) {
			    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x);
			    activityName = StringUtil.checkNull(history.getActivityName());
			    if (isApprover && activityName.equals("검토")) {
				activityName = "승인";
				isApprover = false;
			    }
			    if (history.getCompletedDate() != null)
				completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
			    if (activityName.equals("요청"))
				requestDate = completeDate;

			}
		    }

		    row++;
		    // No
		    rowCount++;
		    lr = new Label(0, row, rowCount + "", cellFormat);
		    s1.addCell(lr);

		    // ECO No
		    lr = new Label(1, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("EcoId")), cellFormat);
		    s1.addCell(lr);

		    // 연계 ECR No
		    lr = new Label(2, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("RelatedECRStr")), cellFormat);
		    s1.addCell(lr);

		    // 연계 ECO No
		    lr = new Label(3, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("RelatedECOStr")), cellFormat);
		    s1.addCell(lr);

		    // ECO 구분
		    lr = new Label(4, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("ProdMoldClsName")), cellFormat);
		    s1.addCell(lr);

		    // 단계구분
		    lr = new Label(5, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("DevFlag")), cellFormat);
		    s1.addCell(lr);

		    // Project No
		    lr = new Label(6, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("ProjectNo")), cellFormat);
		    s1.addCell(lr);

		    // Part No/Die No
		    lr = new Label(7, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("PartNumber")), cellFormat);
		    s1.addCell(lr);

		    // Part Name
		    lr = new Label(8, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("PartName")), cellFormat);
		    s1.addCell(lr);

		    // 고객사
		    lr = new Label(9, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CarMaker")), cellFormat);
		    s1.addCell(lr);

		    // 차종
		    lr = new Label(10, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CarCategory")), cellFormat);
		    s1.addCell(lr);

		    // ECO 제목
		    lr = new Label(11, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("EcoName")), cellFormat);
		    s1.addCell(lr);

		    // 설계변경 사유
		    lr = new Label(12, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("ChangeReason") + ketSearchEcoDetailVO.get("OtherChgReason")), cellFormat);
		    s1.addCell(lr);

		    // 생산성 향상 유형
		    lr = new Label(13, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("IncProdType") + ketSearchEcoDetailVO.get("OtherIncProdType")), cellFormat);
		    s1.addCell(lr);

		    // 생산처
		    lr = new Label(14, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("ProdVendor")), cellFormat);
		    s1.addCell(lr);

		    // 예산확보여부
		    lr = new Label(15, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("SecureBudgetYn")), cellFormat);
		    s1.addCell(lr);

		    // 고객확인 구분
		    lr = new Label(16, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CustomChk") + (String) ketSearchEcoDetailVO.get("CustomChkDesc")), cellFormat);
		    s1.addCell(lr);

		    // 조정 및 변경사항
		    lr = new Label(17, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("ControlFlag")), cellFormat);
		    s1.addCell(lr);

		    // CU도면 변경여부
		    lr = new Label(18, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CuDrawingChk")), cellFormat);
		    s1.addCell(lr);

		    // 작성부서
		    lr = new Label(19, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CreateDeptName")), cellFormat);
		    s1.addCell(lr);

		    // 작성자
		    lr = new Label(20, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CreatorName")), cellFormat);
		    s1.addCell(lr);

		    // 상신일자
		    lr = new Label(21, row, StringUtil.checkNull(requestDate), cellFormat);
		    s1.addCell(lr);

		    // 승인일자
		    lr = new Label(22, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("ApproveDate")), cellFormat);
		    s1.addCell(lr);

		    // 결재상태
		    lr = new Label(23, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("SancStateFlag")), cellFormat);
		    s1.addCell(lr);

		    // 작성일자
		    lr = new Label(24, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("CreateDate")), cellFormat);
		    s1.addCell(lr);

		    // 사업부
		    lr = new Label(25, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("divisonFlagName")), cellFormat);
		    s1.addCell(lr);

		    // 원가변동
		    lr = new Label(26, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("costchange")), cellFormat);
		    s1.addCell(lr);

		    // 원가증감비율
		    lr = new Label(27, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("costvariationrate")), cellFormat);
		    s1.addCell(lr);

		    // 불량유형
		    lr = new Label(28, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("defectDivName")), cellFormat);
		    s1.addCell(lr);

		    // 불량구분
		    lr = new Label(29, row, StringUtil.checkNull((String) ketSearchEcoDetailVO.get("defectTypeName")), cellFormat);
		    s1.addCell(lr);
		}
		writebook.write();
		writebook.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		// String contentID = sFileName.substring( 0, sFileName.lastIndexOf(".") );
		// excelFileObj = E3PSDRMHelper.downloadExcel( excelFileObj, sFileName, contentID, req.getRemoteAddr() );
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////

		FileInputStream fin = new FileInputStream(excelFileObj);
		int ifilesize = (int) excelFileObj.length();
		byte b[] = new byte[ifilesize];

		response.setContentLength(ifilesize);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = response.getOutputStream();
		fin.read(b);
		oout.write(b, 0, ifilesize);
		oout.close();
		fin.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    } finally {
		if (excelFileObj.isFile()) {
		    excelFileObj.delete();
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-excel

    /**
     * 
     * 함수명 : search 설명 : 제품/금형 ECO 자료를 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();

	int rowCount = 1;

	String detailInfoUrl = null;
	String detailProjectPopup = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    hash.put("prodMoldCls", KETParamMapUtil.toString(hash.getStringArray("prodMoldCls")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = request.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ecoSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ecoSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ecoSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("ecoSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    List<Map<String, Object>> list = dao.searchEcoList(false, hash, conditionList);

	    // ketSearchEcoVO = dao.searchEcoList(ketSearchEcoVO, false, hash );

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

		if (prodMold.equals("제품")) {
		    detailInfoUrl = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid=" + oid;
		} else {
		    detailInfoUrl = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid=" + oid; // 금형
		}

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" ProdMoldCls=&quot;" + prodMold + "&quot;");
		strBuffer.append(" EcoNo=&quot;" + ecoNo + "&quot;" + "EcoNoOnClick=&quot;location.href=&apos;" + detailInfoUrl + "&apos;&quot;" + " EcoNoHtmlPrefix=&quot;&lt;font color=&apos;"
		        + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot; EcoNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" EcoName=&quot;" + TreeGridUtil.replaceContentForI(ecoName) + "&quot;");
		strBuffer.append(" ProjectNo=&quot;" + projectNo + "&quot;" + " ProjectNoOnClick=&quot;javascript:openProject(&apos;" + projectNo + "&apos;);&quot;"
		        + " ProjectNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot; ProjectNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" ChangeReason=&quot;" + reason + "&quot;"); // 변경사유
		strBuffer.append(" BudgetYn=&quot;" + budget + "&quot;"); // 예산
		strBuffer.append(" Creator=&quot;" + creator + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + createdate + "&quot;");
		strBuffer.append(" StateAppro=&quot;" + stateFlag + "&quot;");
		strBuffer.append("/>");
	    }

	    // 검색조건 셋팅
	    request.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    request.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-search

    /**
     * 
     * ECO 리스트
     * 
     * @param request
     * @param response
     * @param isPageNotData
     * @throws ServletException
     * @throws IOException
     * @메소드명 : searchGrid4Eco
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void searchGrid4Eco(HttpServletRequest request, HttpServletResponse response, boolean isPageNotData) throws ServletException, IOException {

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();

	int rowCount = 1;

	String detailInfoUrl = null;
	String detailProjectPopup = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPageNotData, hash);

	    //
	    pager.replaceSortCols("BudgetYn", "secureBudgetYn");
	    pager.replaceSortCols("ProdMoldCls", "ProdMoldClsName");
	    pager.replaceSortCols("EcoNo", "EcoId");
	    pager.replaceSortCols("Creator", "CreatorName");
	    pager.replaceSortCols("StateAppro", "stateStateName");

	    Kogger.debug(getClass(), "sortCol :" + pager.getSortCol());
	    Kogger.debug(getClass(), "sortType :" + pager.getSortType());

	    hash.put("prodMoldCls", KETParamMapUtil.toString(hash.getStringArray("prodMoldCls")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = request.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ecoSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ecoSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ecoSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("ecoSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    if (isPageNotData) {
		List<Map<String, Object>> list = dao.searchEcoList(false, hash, conditionList, pager);

		// ketSearchEcoVO = dao.searchEcoList(ketSearchEcoVO, false, hash );

		// strBuffer.append("<B Pos=\"").append((pager.getCurrentPageNo() + 1)).append("\">");

		for (Map<String, Object> ecoVo : list) {

		    String prodMold = (String) ecoVo.get("ProdMoldClsName"); // 구분
		    String ecoNo = (String) ecoVo.get("EcoId"); // ECO No
		    String ecoName = (String) ecoVo.get("EcoName"); // ECO 제목

		    String projectNo = (String) ecoVo.get("ProjectNo"); // Project No
		    String reason = (String) ecoVo.get("ChangeReason"); // 사유
		    String budget = (String) ecoVo.get("SecureBudgetYn"); // 예산

		    String creator = (String) ecoVo.get("CreatorName"); // 작성자
		    String orgName = (String) ecoVo.get("OrgName"); // 작성부서
		    String createdate = (String) ecoVo.get("CreateDate"); // 작성일자
		    String approveDate = (String) ecoVo.get("ApproveDate"); // 승인일자
		    String stateFlag = (String) ecoVo.get("SancStateFlag"); // 결재 상태
		    String costvariationrate = (String) ecoVo.get("costvariationrate"); // 원가변동
		    String costchange = (String) ecoVo.get("costchange"); // 원가증감비율
		    String ecoApplyPoint = (String) ecoVo.get("ecoApplyPoint"); // eco적용시점

		    String oid = (String) ecoVo.get("Oid");
		    String proOid = (String) ecoVo.get("ProjectOid");

		    if (prodMold.equals("제품")) {
			detailInfoUrl = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid=" + oid;
		    } else {
			detailInfoUrl = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid=" + oid; // 금형
		    }

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\"");

		    // 1. RowNum을 client에서 처리 : Empty로 넘기고 client Grid_KET_S.js 로 No 처리
		    strBuffer.append(" RowNum=\"" + pager.DEFAULT_ROW_NUM + "\"");
		    // 2. RowNum을 서버에서 처리 : total Count를 가져와서 계산 해줌
		    // strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;" );
		    strBuffer.append(" ProdMoldCls=\"" + prodMold + "\"");
		    strBuffer.append(" EcoNo=\"" + ecoNo + "\""

		    // + " EcoNoOnClick=\"location.href='" + detailInfoUrl + "'\""
			    + " EcoNoOnClick=\"javascript:doView('" + detailInfoUrl + "');\""

			    + " EcoNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" EcoNoHtmlPostfix=\"</font>\"");
		    strBuffer.append(" EcoName=\"" + TreeGridUtil.replaceContentForI(ecoName) + "\"");

		    if (proOid != null && !proOid.equals("")) {
			strBuffer.append(" ProjectNo=\"" + projectNo + "\"" + " ProjectNoOnClick=\"javascript:openProject('" + projectNo + "');\"" + " ProjectNoHtmlPrefix=\"<font color='"
			        + PropertiesUtil.getSearchGridLinkColor() + "'>\" ProjectNoHtmlPostfix=\"</font>\"");
		    } else {
			strBuffer.append(" ProjectNo=\"" + projectNo + "\"");
		    }

		    strBuffer.append(" ChangeReason=\"" + reason + "\""); // 변경사유
		    strBuffer.append(" BudgetYn=\"" + budget + "\""); // 예산
		    strBuffer.append(" Creator=\"" + creator + "\"");
		    strBuffer.append(" OrgName=\"" + orgName + "\"");
		    strBuffer.append(" CreateDate=\"" + createdate + "\"");
		    strBuffer.append(" StateAppro=\"" + stateFlag + "\"");
		    strBuffer.append(" ApproveDate=\"" + approveDate + "\"");
		    strBuffer.append(" CostChange=\"" + costchange + "\"");
		    strBuffer.append(" CostVariation=\"" + costvariationrate + "\"");
		    strBuffer.append(" EcoApplyPoint=\"" + ecoApplyPoint + "\"");
		    strBuffer.append("/>");
		}

		// strBuffer.append("</B>");

		// 검색조건 셋팅
		// request.setAttribute( "searchCondition", hash ); // 검색 화면에서 받은 검색조건
		// request.setAttribute( "tgData", strBuffer.toString() ); // 검색 결과 데이터

		request.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		request.setAttribute("tgData", strBuffer.toString());
		request.setAttribute("pageSize", String.valueOf(pager.getPageSize()));
		// gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
		gotoResult(request, response, "/jsp/common/treegrid/SearchGridPage.jsp");

	    } else {

		int totalCount = dao.searchEcoListCount(false, hash, conditionList);

		// if (!PropertiesUtil.getSearchGridCountLimitFlag() || totalCount <= PropertiesUtil.getSearchGridCountLimit()) {
		// // 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
		// resultSize = totalCount;
		// } else {
		// // 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
		// resultSize = PropertiesUtil.getSearchGridCountLimit();
		// resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		// }

		request.setAttribute("rootCount", String.valueOf(totalCount));
		request.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(request, response, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-search

    /**
     * 
     * ECN 리스트
     * 
     * @param request
     * @param response
     * @param isPageNotData
     * @throws ServletException
     * @throws IOException
     * @메소드명 : searchGrid4Ecn
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 9. 11.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void searchGrid4Ecn(HttpServletRequest request, HttpServletResponse response, boolean isPageNotData) throws ServletException, IOException {

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();

	int rowCount = 1;

	String detailInfoUrl = null;
	String detailProjectPopup = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPageNotData, paramMap);

	    //
	    pager.replaceSortCols("EcnNo", "ecnNo");
	    pager.replaceSortCols("StateAppro", "stateAppro");
	    pager.replaceSortCols("ReceiveConfirm", "receiveConfirm");
	    pager.replaceSortCols("JobComplete", "jobComplete");
	    pager.replaceSortCols("JobCompleteDate", "jobCompleteDate");
	    pager.replaceSortCols("EcoNo", "ecoNo");
	    pager.replaceSortCols("EcoName", "ecoName");
	    pager.replaceSortCols("ChangeReason", "changeReason");
	    pager.replaceSortCols("Creator", "creator");
	    pager.replaceSortCols("CreateDate", "createDate");
	    pager.replaceSortCols("ProjectNo", "projectNo");

	    Kogger.debug(getClass(), "sortCol :" + pager.getSortCol());
	    Kogger.debug(getClass(), "sortType :" + pager.getSortType());

	    if (isPageNotData) {
		List<Map<String, Object>> list = dao.searchEcnList(false, paramMap, pager);

		// ketSearchEcoVO = dao.searchEcoList(ketSearchEcoVO, false, hash );

		// strBuffer.append("<B Pos=\"").append((pager.getCurrentPageNo() + 1)).append("\">");

		for (Map<String, Object> ecnVo : list) {

		    String ecnNo = (String) ecnVo.get("EcnNo");
		    String ecnOid = (String) ecnVo.get("EcnOid");
		    String stateAppro = (String) ecnVo.get("StateAppro");
		    String stateApproName = (String) ecnVo.get("StateApproName");
		    String receiveConfirm = (String) ecnVo.get("ReceiveConfirm");
		    String jobComplete = (String) ecnVo.get("JobComplete");
		    String jobCompleteDate = (String) ecnVo.get("JobCompleteDate");

		    String ecoOid = (String) ecnVo.get("EcoOid");
		    String ecoNo = (String) ecnVo.get("EcoNo");
		    String ecoName = (String) ecnVo.get("EcoName");
		    String changeReason = (String) ecnVo.get("ChangeReason");
		    String creator = (String) ecnVo.get("Creator");
		    String createDate = (String) ecnVo.get("CreateDate");

		    String projectOid = (String) ecnVo.get("ProjectOid");
		    String projectNo = (String) ecnVo.get("ProjectNo");
		    // String projectName = (String) ecnVo.get("ProjectName");

		    String WorkerName = (String) ecnVo.get("WorkerName");
		    String DeptName = (String) ecnVo.get("DeptName");
		    String RequestDate = (String) ecnVo.get("RequestDate");
		    String CustomName = (String) ecnVo.get("CustomName");
		    // 제품
		    if (ecoOid.lastIndexOf("KETProdChangeOrder") > -1) {
			detailInfoUrl = "/plm/servlet/e3ps/ProdEcoServlet?prePage=S&cmd=View&oid=" + ecoOid;
		    }
		    // 금형
		    else {
			detailInfoUrl = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&prePage=Search&oid=" + ecoOid;
		    }

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\"");

		    // 1. RowNum을 client에서 처리 : Empty로 넘기고 client Grid_KET_S.js 로 No 처리
		    strBuffer.append(" RowNum=\"" + pager.DEFAULT_ROW_NUM + "\"");
		    // 2. RowNum을 서버에서 처리 : total Count를 가져와서 계산 해줌
		    // strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;" );

		    strBuffer.append(" EcnNo=\"" + ecnNo + "\""
			    // + " EcnNoOnClick=\"location.href='" + detailInfoUrl + "'\""
			    + " EcnNoOnClick=\"javascript:doView('" + (detailInfoUrl + "&tabName=tabEcn") + "');\"" + " EcnNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			    + "'>\" EcnNoHtmlPostfix=\"</font>\"");

		    strBuffer.append(" EcnOid=\"" + ecnOid + "\"");
		    strBuffer.append(" CustomName=\"" + CustomName + "\"");
		    strBuffer.append(" StateAppro=\"" + stateApproName + "\"");
		    strBuffer.append(" ReceiveConfirm=\"" + receiveConfirm + "\"");
		    strBuffer.append(" JobComplete=\"" + jobComplete + "\"");
		    strBuffer.append(" RequestDate=\"" + RequestDate + "\"");
		    strBuffer.append(" JobCompleteDate=\"" + jobCompleteDate + "\"");
		    strBuffer.append(" WorkerName=\"" + WorkerName + "\"");
		    strBuffer.append(" DeptName=\"" + DeptName + "\"");
		    strBuffer.append(" EcoNo=\"" + ecoNo + "\""
			    // + " EcoNoOnClick=\"location.href='" + detailInfoUrl + "'\""
			    + " EcoNoOnClick=\"javascript:doView('" + detailInfoUrl + "');\"" + " EcoNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			    + "'>\" EcoNoHtmlPostfix=\"</font>\"");

		    strBuffer.append(" EcoName=\"" + TreeGridUtil.replaceContentForI(ecoName) + "\"");
		    strBuffer.append(" ChangeReason=\"" + changeReason + "\"");
		    strBuffer.append(" Creator=\"" + creator + "\"");
		    strBuffer.append(" CreateDate=\"" + createDate + "\"");

		    if (projectOid != null && !projectOid.equals("")) {
			strBuffer.append(" ProjectNo=\"" + projectNo + "\"" + " ProjectNoOnClick=\"javascript:openProject('" + projectNo + "');\"" + " ProjectNoHtmlPrefix=\"<font color='"
			        + PropertiesUtil.getSearchGridLinkColor() + "'>\" ProjectNoHtmlPostfix=\"</font>\"");
		    } else {
			strBuffer.append(" ProjectNo=\"" + projectNo + "\"");
		    }

		    strBuffer.append("/>");
		}

		// strBuffer.append("</B>");

		// 검색조건 셋팅
		// request.setAttribute( "searchCondition", hash ); // 검색 화면에서 받은 검색조건
		// request.setAttribute( "tgData", strBuffer.toString() ); // 검색 결과 데이터

		request.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		request.setAttribute("tgData", strBuffer.toString());
		request.setAttribute("pageSize", String.valueOf(pager.getPageSize()));
		// gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
		gotoResult(request, response, "/jsp/common/treegrid/SearchGridPage.jsp");

	    } else {

		int totalCount = dao.searchEcnListCount(false, paramMap);

		// if (!PropertiesUtil.getSearchGridCountLimitFlag() || totalCount <= PropertiesUtil.getSearchGridCountLimit()) {
		// // 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
		// resultSize = totalCount;
		// } else {
		// // 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
		// resultSize = PropertiesUtil.getSearchGridCountLimit();
		// resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		// }

		request.setAttribute("rootCount", String.valueOf(totalCount));
		request.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(request, response, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-search

    /**
     * 
     * MyEcm 리스트
     * 
     * @param request
     * @param response
     * @param isPageNotData
     * @throws ServletException
     * @throws IOException
     * @메소드명 : searchGrid4MyEcm
     * @작성자 : kimtaehyun
     * @작성일 : 2014. 10. 2.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    private void searchGrid4MyEcm(HttpServletRequest request, HttpServletResponse response, boolean isPageNotData) throws ServletException, IOException {

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();

	int rowCount = 1;

	String detailInfoUrl = null;
	String detailProjectPopup = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	    KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPageNotData, hash);

	    //
	    pager.replaceSortCols("BudgetYn", "secureBudgetYn");
	    pager.replaceSortCols("ProdMoldCls", "ProdMoldClsName");
	    pager.replaceSortCols("EcoNo", "EcoId");
	    pager.replaceSortCols("ChangeReason", "ChangeReason2");
	    pager.replaceSortCols("Creator", "CreatorName");
	    pager.replaceSortCols("StateAppro", "stateStateName");

	    Kogger.debug(getClass(), "sortCol :" + pager.getSortCol());
	    Kogger.debug(getClass(), "sortType :" + pager.getSortType());

	    hash.put("prodMoldCls", KETParamMapUtil.toString(hash.getStringArray("prodMoldCls")));

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = request.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!hash.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("ecoSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("ecoSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("ecoSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(hash);
	    session.setAttribute("ecoSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    if (isPageNotData) {
		List<Map<String, Object>> list = dao.searchMyEcmList(false, hash, conditionList, pager);

		// ketSearchEcoVO = dao.searchEcoList(ketSearchEcoVO, false, hash );

		// strBuffer.append("<B Pos=\"").append((pager.getCurrentPageNo() + 1)).append("\">");

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

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\"");

		    // 1. RowNum을 client에서 처리 : Empty로 넘기고 client Grid_KET_S.js 로 No 처리
		    strBuffer.append(" RowNum=\"" + pager.DEFAULT_ROW_NUM + "\"");
		    // 2. RowNum을 서버에서 처리 : total Count를 가져와서 계산 해줌
		    // strBuffer.append( " RowNum=&quot;" + rowCount++ + "&quot;" );
		    strBuffer.append(" ProdMoldCls=\"" + prodMold + "\"");
		    strBuffer.append(" EcoNo=\"" + ecoNo + "\""

		    // + " EcoNoOnClick=\"location.href='" + detailInfoUrl + "'\""
			    + " EcoNoOnClick=\"javascript:doView('" + detailInfoUrl + "');\""

			    + " EcoNoHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" EcoNoHtmlPostfix=\"</font>\"");
		    strBuffer.append(" EcoName=\"" + TreeGridUtil.replaceContentForI(ecoName) + "\"");
		    strBuffer.append(" ProjectNo=\"" + projectNo + "\"" + " ProjectNoOnClick=\"javascript:openProject('" + projectNo + "');\"" + " ProjectNoHtmlPrefix=\"<font color='"
			    + PropertiesUtil.getSearchGridLinkColor() + "'>\" ProjectNoHtmlPostfix=\"</font>\"");
		    strBuffer.append(" ChangeReason=\"" + reason + "\""); // 변경사유
		    strBuffer.append(" BudgetYn=\"" + budget + "\""); // 예산
		    strBuffer.append(" Creator=\"" + creator + "\"");
		    strBuffer.append(" CreateDate=\"" + createdate + "\"");
		    strBuffer.append(" StateAppro=\"" + stateFlag + "\"");
		    strBuffer.append("/>");
		}

		// strBuffer.append("</B>");

		// 검색조건 셋팅
		// request.setAttribute( "searchCondition", hash ); // 검색 화면에서 받은 검색조건
		// request.setAttribute( "tgData", strBuffer.toString() ); // 검색 결과 데이터

		request.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		request.setAttribute("tgData", strBuffer.toString());
		request.setAttribute("pageSize", String.valueOf(pager.getPageSize()));
		// gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
		gotoResult(request, response, "/jsp/common/treegrid/SearchGridPage.jsp");

	    } else {

		int totalCount = dao.searchMyEcmListCount(false, hash, conditionList);

		// if (!PropertiesUtil.getSearchGridCountLimitFlag() || totalCount <= PropertiesUtil.getSearchGridCountLimit()) {
		// // 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
		// resultSize = totalCount;
		// } else {
		// // 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
		// resultSize = PropertiesUtil.getSearchGridCountLimit();
		// resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		// }

		request.setAttribute("rootCount", String.valueOf(totalCount));
		request.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(request, response, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-search

    /**
     * 
     * 함수명 : searchMoldPlanPopup 설명 : 양산 금형변경 일정 팝업 목록을 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private KETSearchEcoVO searchMoldPlanPopup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETSearchEcoVO ketSearchEcoVO = new KETSearchEcoVO();
	ketSearchEcoVO.setDieNo(StringUtil.checkNull(request.getParameter("dieNo")));
	ketSearchEcoVO.setPartNumber(StringUtil.checkNull(request.getParameter("partNumber")));
	ketSearchEcoVO.setEcoOid(StringUtil.checkNull(request.getParameter("oid")));
	SearchMoldChangeOrderDao dao = new SearchMoldChangeOrderDao();
	try {
	    ketSearchEcoVO = dao.searchMoldPlanPopupList(ketSearchEcoVO);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return ketSearchEcoVO;
    }// end-of-searchMoldPlanPopup

    /**
     * 
     * 함수명 : searchMoldPlanPopup 설명 : 활동추가-부품 팝업 목록을 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private KETSearchEcoVO searchPartPopup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETSearchEcoVO ketSearchEcoVO = new KETSearchEcoVO();
	ketSearchEcoVO.setPagingYn(1);// 페이지처리
	ketSearchEcoVO.setPage(StringUtil.getIntParameter(request.getParameter("page"), 1));
	ketSearchEcoVO.setPerPage(StringUtil.getIntParameter(request.getParameter("perPage"), 10));
	ketSearchEcoVO.setTotalCount(StringUtil.getIntParameter(request.getParameter("totalCount"), 0));
	ketSearchEcoVO.setSortColumn(StringUtil.checkReplaceStr(request.getParameter("sortColumn"), "2 ASC"));
	ketSearchEcoVO.setParam(StringUtil.checkNull(request.getParameter("param")));
	ketSearchEcoVO.setPartNumber(ParamUtil.getParameter(request, "partNumber"));
	ketSearchEcoVO.setPartName(ParamUtil.getParameter(request, "partName"));
	ketSearchEcoVO.setEpmNumber(ParamUtil.getParameter(request, "epmNumber"));
	ketSearchEcoVO.setEpmName(ParamUtil.getParameter(request, "epmName"));
	ketSearchEcoVO.setPartType(ParamUtil.getParameter(request, "partType", "A"));
	SearchMoldChangeOrderDao dao = new SearchMoldChangeOrderDao();
	try {
	    ketSearchEcoVO = dao.searchPartPopupList(ketSearchEcoVO);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return ketSearchEcoVO;
    }// end-of-searchPartPopup

    /**
     * 
     * 함수명 : searchEcmPartPopup 설명 : 활동추가-도면 팝업 목록을 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private KETSearchEcoVO searchEpmPopup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETSearchEcoVO ketSearchEcoVO = new KETSearchEcoVO();
	ketSearchEcoVO.setPagingYn(1);// 페이지처리
	ketSearchEcoVO.setPage(StringUtil.getIntParameter(request.getParameter("page"), 1));
	ketSearchEcoVO.setPerPage(StringUtil.getIntParameter(request.getParameter("perPage"), 10));
	ketSearchEcoVO.setTotalCount(StringUtil.getIntParameter(request.getParameter("totalCount"), 0));
	ketSearchEcoVO.setSortColumn(StringUtil.checkReplaceStr(request.getParameter("sortColumn"), "2 ASC"));
	ketSearchEcoVO.setParam(StringUtil.checkNull(request.getParameter("param")));
	ketSearchEcoVO.setPartNumber(ParamUtil.getParameter(request, "docNumber"));
	ketSearchEcoVO.setPartName(ParamUtil.getParameter(request, "docName"));
	SearchMoldChangeOrderDao dao = new SearchMoldChangeOrderDao();
	try {
	    ketSearchEcoVO = dao.searchEpmPopupList(ketSearchEcoVO);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return ketSearchEcoVO;
    }// end-of-searchEpmPopup

    private void popupEcoSearchInit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();

	List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
	;
	conditionList.add(hash);

	try {
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();
	    EpmApprovalHistoryHandler history = new EpmApprovalHistoryHandler();

	    int totalCnt = 0;
	    String fastCreatedate = DateUtil.getCurrentDateString("d");

	    String[] pNumArr = StringUtils.splitPreserveAllTokens(this.pNums, ",");
	    int pNumArrLength = (pNumArr != null) ? pNumArr.length : 0;
	    for (int i = 0; i < pNumArrLength; i++) {

		String pNum = pNumArr[i];
		ProductProject productProject = null;
		try {
		    MoldProject moldProject = MoldProjectHelper.getMoldProject(pNum);
		    MoldItemInfo moldItemInfo = (moldProject != null) ? moldProject.getMoldInfo() : null;
		    productProject = (moldItemInfo != null) ? moldItemInfo.getProject() : null;
		} catch (wt.util.WTRuntimeException wtre) {
		    Kogger.error(getClass(), wtre);
		}
		String projectOid = CommonUtil.getOIDString(productProject);
		ArrayList<WTChangeOrder2> ecoList = prodEcoBeans.getEcoListByProjectOid(projectOid);

		int ecoListSize = (ecoList != null) ? ecoList.size() : 0;
		for (int j = 0; j < ecoListSize; j++) {
		    KETProdChangeOrder eco = (KETProdChangeOrder) ecoList.get(j);
		    String deptName = ""; // 작성부서
		    String ecoNo = eco.getEcoId(); // ECO No
		    String ecoName = eco.getEcoName(); // ECO 제목
		    String budgetyn = ""; // 예산 확보
		    String creator = eco.getCreatorFullName(); // 작성자
		    String createdate = DateUtil.getDateString(eco.getCreateTimestamp(), "d"); // 작성일자

		    // 승인일자
		    history.getApprovalHistory(eco);
		    String compDate = DateUtil.getDateString(history.getApprovalDate(), "d");

		    String oId = CommonUtil.getOIDString(eco); // oid

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		    strBuffer.append(" Oid=&quot;" + oId + "&quot;");
		    strBuffer.append(" EcoNo=&quot;" + ecoNo + "&quot;");
		    strBuffer.append(" EcoName=&quot;" + TreeGridUtil.replaceContentForQuotHtml(ecoName) + "&quot;"); //
		    strBuffer.append(" DeptName=&quot;" + deptName + "&quot;"); //
		    strBuffer.append(" Creator=&quot;" + creator + "&quot;");
		    strBuffer.append(" CreateDate=&quot;" + createdate + "&quot;");
		    strBuffer.append(" CompDate=&quot;" + StringUtil.checkNull(compDate) + "&quot;");
		    strBuffer.append(" Budgetyn=&quot;" + budgetyn + "&quot;");
		    strBuffer.append("/>");

		    int r = createdate.compareTo(fastCreatedate);
		    if (r < 0)
			fastCreatedate = createdate;

		    totalCnt++;
		}

	    }

	    // 검색조건 셋팅
	    if (totalCnt > 0) {
		hash.put("createStartDate", fastCreatedate);
		// hash.put("createEndDate", "");
	    }
	    request.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    request.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-popupEcoSearch

    /**
     * 
     * 함수명 : popupEcoSearch 설명 : 금형 ECO 팝업 목록을 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private void popupEcoSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
	KETParamMapUtil hash = KETParamMapUtil.getMap(uploader);

	MoldChangeOrderDao dao = new MoldChangeOrderDao();

	StringBuffer strBuffer = new StringBuffer();

	List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
	;
	conditionList.add(hash);

	try {

	    List<Map<String, Object>> list = dao.searchEcoList(false, hash, conditionList);

	    for (Map<String, Object> ecoVo : list) {

		String deptName = (String) ecoVo.get("CreateDeptName"); // 작성부서
		String ecoNo = (String) ecoVo.get("EcoId"); // ECO No
		String ecoName = (String) ecoVo.get("EcoName"); // ECO 제목
		String budgetyn = (String) ecoVo.get("SecureBudgetYn"); // 예산 확보
		String creator = (String) ecoVo.get("CreatorName"); // 작성자
		String createdate = (String) ecoVo.get("CreateDate"); // 작성일자
		String compDate = (String) ecoVo.get("CompleteReqDate"); // 승인일자
		String oId = (String) ecoVo.get("Oid"); // oid

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" Oid=&quot;" + oId + "&quot;");
		strBuffer.append(" EcoNo=&quot;" + ecoNo + "&quot;");
		strBuffer.append(" EcoName=&quot;" + TreeGridUtil.replaceContentForQuotHtml(ecoName) + "&quot;"); //
		strBuffer.append(" DeptName=&quot;" + deptName + "&quot;"); //
		strBuffer.append(" Creator=&quot;" + creator + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + createdate + "&quot;");
		strBuffer.append(" CompDate=&quot;" + StringUtil.checkNull(compDate) + "&quot;");
		strBuffer.append(" Budgetyn=&quot;" + budgetyn + "&quot;");
		strBuffer.append("/>");
	    }

	    // 검색조건 셋팅
	    request.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
	    request.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }// end-of-popupEcoSearch

    /**
     * 
     * 함수명 : searchEcmPartPopup 설명 : 설계변경용 부품 팝업 목록을 검색한다.
     * 
     * @param request
     *            : HTTP 요청 객체
     * @param response
     *            : HTTP 응답 객체
     * @return KETSearchEcoVO : 금형 ECO 검색 VO
     * @throws ServletException
     * @throws IOException
     *             작성자 : 안수학 작성일자 : 2010. 12. 31.
     */
    private KETSearchEcoVO searchEcmPartPopup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	KETSearchEcoVO ketSearchEcoVO = new KETSearchEcoVO();
	ketSearchEcoVO.setPagingYn(1);// 페이지처리
	ketSearchEcoVO.setPage(StringUtil.getIntParameter(request.getParameter("page"), 1));
	ketSearchEcoVO.setPerPage(StringUtil.getIntParameter(request.getParameter("perPage"), 10));
	ketSearchEcoVO.setTotalCount(StringUtil.getIntParameter(request.getParameter("totalCount"), 0));
	ketSearchEcoVO.setSortColumn(StringUtil.checkReplaceStr(request.getParameter("sortColumn"), "2 ASC"));
	ketSearchEcoVO.setParam(StringUtil.checkNull(request.getParameter("param")));
	ketSearchEcoVO.setPartNumber(ParamUtil.getParameter(request, "partNumber"));
	ketSearchEcoVO.setPartName(ParamUtil.getParameter(request, "partName"));
	ketSearchEcoVO.setPartType(ParamUtil.getParameter(request, "partType"));
	SearchMoldChangeOrderDao dao = new SearchMoldChangeOrderDao();
	try {
	    ketSearchEcoVO = dao.searchEcmPartPopupList(ketSearchEcoVO);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return ketSearchEcoVO;
    }// end-of-searchEcmPartPopup

}
