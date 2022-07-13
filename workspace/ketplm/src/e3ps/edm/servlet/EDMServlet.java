package e3ps.edm.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;
import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.HolderToContent;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.representation.Representation;
import wt.util.WTProperties;

import com.ptc.wpcfg.hadb.ContentDownload;
import com.ptc.wvs.server.util.PublishUtils;

import e3ps.common.content.ContentUtil;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.treegrid.TgStringBuffer;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.edm.CADAppType;
import e3ps.edm.CADCategory;
import e3ps.edm.DevStage;
import e3ps.edm.beans.EDMExcelModel;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.dao.EPMDocumentDao;
import e3ps.edm.util.EDMProperties;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.log.Kogger;

public class EDMServlet extends CommonServlet {

    /*
     * [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용 수정일자 : 2013. 5. 29 수정자 : 오명재
     */
    /*
     * (non-Javadoc)
     * 
     * @see e3ps.common.web.CommonServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	String command = req.getParameter("command");
	Kogger.debug(getClass(), "=====> EDMServlet: command=[" + command + "]");

	if ("downloadSearchResult".equals(command)) {

	    excelPrintSearchResult(req, res);
	}
	/*
	 * Start [PLM System 1차개선] 수정내용 : 도면검색 서블릿 적용, 2013. 6. 5, 오명재
	 */
	else if ("openSearch".equals(command)) {
	    openSearch(req, res);
	} else if ("searchGridData".equals(command)) {
	    searchGrid(req, res, false);
	} else if ("searchGridPage".equals(command)) {
	    searchGrid(req, res, true);
	} else if ("openSearchPopup".equalsIgnoreCase(command)) {
	    openSearchPopup(req, res);
	} else if ("searchGridDataPopup".equals(command)) {
	    searchGridPopup(req, res, false);
	} else if ("searchGridPagePopup".equals(command)) {
	    searchGridPopup(req, res, true);
	} else if ("openMySearch".equals(command)) {
	    openMySearch(req, res);
	} else if ("thumbview".equalsIgnoreCase(command)) {
	    thumbview(req, res);
	}
	/*
	 * End [PLM System 1차개선] 수정내용 : 도면검색 서블릿 적용, 2013. 6. 5, 오명재
	 */
    }

    private void openSearchPopup(HttpServletRequest req, HttpServletResponse res) {
	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    gotoResult(req, res, "/jsp/edm/SearchEpmPopup.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void searchGridPopup(HttpServletRequest req, HttpServletResponse res, boolean isPageNotData) {
	boolean allDataNotPagingData = true;
	searchGridExecute(req, res, isPageNotData, allDataNotPagingData);
    }

    private void searchGrid(HttpServletRequest req, HttpServletResponse res, boolean isPageNotData) {
	boolean allDataNotPagingData = false;
	searchGridExecute(req, res, isPageNotData, allDataNotPagingData);
    }

    private void searchGridExecute(HttpServletRequest req, HttpServletResponse res, boolean isPageNotData, boolean allDataNotPagingData) {

	/*
	 * [PLM System 1차개선] 수정내용 : 다중검색/멀티콤보 적용, 2013. 06. 24, 김무준
	 */
	KETMessageService messageService = KETMessageService.getMessageService(req);
	Locale locale = messageService.getLocale();

	TgStringBuffer strBuffer = new TgStringBuffer();
	int rowCount = 1;
	Connection conn = null;

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);
	    Kogger.debug(getClass(), "EDMServlet.search: paramMap=" + paramMap);

	    // paging 처리
	    TgPagingControl pager = new TgPagingControl(isPageNotData, paramMap);

	    Kogger.debug(getClass(), "sortCol :" + pager.getSortCol());
	    Kogger.debug(getClass(), "sortType :" + pager.getSortType());

	    String latest = StringUtil.trimToEmpty(paramMap.getString("latest"));
	    if (latest.length() == 0) {
		latest = "true";
		paramMap.remove("latest");
		paramMap.put("latest", latest);
	    }

	    paramMap.put("className", wt.epm.EPMDocument.class.getName());

	    String[] devStageAry = paramMap.getStringArray("devStage");
	    String[] categoryAry = paramMap.getStringArray("category");
	    String[] cadAppTypeAry = paramMap.getStringArray("cadAppType");
	    String isDummyFile = paramMap.getString("isDummyFile");

	    // IBA 값 처리
	    if (devStageAry != null && devStageAry.length > 0) {
		String[] sary = new String[devStageAry.length];
		for (int i = 0; i < devStageAry.length; ++i) {
		    if (StringUtil.checkString(devStageAry[i])) {
			sary[i] = DevStage.toDevStage(devStageAry[i]).getDisplay(locale);
		    }
		}
		paramMap.put(EDMHelper.IBA_DEV_STAGE, sary);
	    }

	    if (categoryAry != null && categoryAry.length > 0) {
		String[] sary = new String[categoryAry.length];
		for (int i = 0; i < categoryAry.length; ++i) {
		    if (StringUtil.checkString(categoryAry[i])) {
			sary[i] = CADCategory.toCADCategory(categoryAry[i]).getDisplay(locale);
		    }
		}
		paramMap.put(EDMHelper.IBA_CAD_CATEGORY, sary);
	    }

	    if (cadAppTypeAry != null && cadAppTypeAry.length > 0) {
		String[] sary = new String[cadAppTypeAry.length];
		for (int i = 0; i < cadAppTypeAry.length; ++i) {
		    if (StringUtil.checkString(cadAppTypeAry[i])) {
			sary[i] = CADAppType.toCADAppType(cadAppTypeAry[i]).getDisplay(locale);
		    }
		}
		paramMap.put(EDMHelper.IBA_CAD_APP_TYPE, sary);
	    }

	    if (isDummyFile.length() > 0) {

		paramMap.put(EDMHelper.IBA_DUMMY_FILE, isDummyFile);
	    }

	    // [Start] 결과 내 재검색
	    /*
	     * result = EDMQueryHelper.find(paramMap); // 기존 검색 주석처리
	     */
	    boolean searchInResult = false; // ("searchInSearch".equals(paramMap.getString("searchInSearch")));
	    // 결과내 검색 처리 필요.
	    List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchEPMDocument", searchInResult, req);
	    conditionList.add(paramMap);
	    // result = EDMQueryHelper.findSIR(conditionList);
	    // [End] 결과 내 재검색

	    String creator = "";
	    String drawingNo = "";
	    String drawingName = "";
	    String version = "";
	    String manufactureVer = "";
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
	    String security = "";
	    String requestDate = "";
	    String approvalDate = "";

	    String oid = "";
	    String masterOid = "";

	    String resultStr = ""; // 검색 결과 제한 건수 초과 여부
	    int resultSize = 0; // 검색 결과 표시 건수

	    // [PLM System 1차개선] 도면 검색 속도 개선, 2013-08-25, BoLee
	    EPMDocumentDao epmDocumentDao = new EPMDocumentDao();

	    if (isPageNotData) {

		List<Map<String, Object>> edmList = epmDocumentDao.searchEPMDocument(conditionList, pager, allDataNotPagingData);

		/* RowNum(화면 Grid의 No)을 처리하는 경우 */
		// 1. RowNum을 client에서 처리 (RDB일 경우 추천) : Empty로 넘기고 client Grid_KET_S.js 로 No 처리
		// 2. RowNum을 서버에서 처리 (Windchill Paging 쿼리일 경우 추천): total Count를 가져와서 계산 해줌
		// int totalCount = epmDocumentDao.searchEPMDocumentCount(conditionList);
		// int startNo = pager.getRowNumStartNo(totalCount);

		// if (!PropertiesUtil.getSearchGridCountLimitFlag() || totalCount <= PropertiesUtil.getSearchGridCountLimit()) {
		// // 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
		// resultSize = totalCount;
		// } else {
		// // 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
		// resultSize = PropertiesUtil.getSearchGridCountLimit();
		// resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		// }

		Map<String, Object> edmObj = null;
		for (int i = 0; i < edmList.size(); i++) {

		    edmObj = edmList.get(i);
		    drawingOid = (String) edmObj.get("drawingOid");
		    masterOid = (String) edmObj.get("drawingMasterOid");
		    drawingNo = (String) edmObj.get("drawingNo");
		    drawingName = (String) edmObj.get("drawingName");
		    version = (String) edmObj.get("ver"); //
		    manufactureVer = StringUtil.checkNull((String) edmObj.get("manufactureVer")); //
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
			// fileDoownloadUrl = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + holderOid + "&adOid=" + appDataOid;
			fileDoownloadUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + holderOid + "&cioids=" + appDataOid
			        + "&role=PRIMARY" + "&ketCustomFlag=Y";
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
		    security = StringUtil.checkNull((String) edmObj.get("security")); // Category

		    requestDate = (String) edmObj.get("requestDate"); // RequestDate
		    approvalDate = (String) edmObj.get("approvalDate"); // ApprovalDate

		    strBuffer.append("<I ");
		    // 1. RowNum을 client에서 처리 : Empty로 넘기고 client Grid_KET_S.js 로 No 처리
		    // strBuffer.append(" RowNum=\"" + pager.DEFAULT_ROW_NUM + "\"");
		    // 2. RowNum을 서버에서 처리 : total Count를 가져와서 계산 해줌
		    // strBuffer.append(" RowNum=\"" + startNo-- + "\"");

		    // Oid 추가
		    strBuffer.append(" Oid=\"" + drawingOid + "\"");
		    // MastOid 추가
		    strBuffer.append(" OidMaster=\"" + masterOid + "\"");
		    strBuffer.append(" NoColor=\"2\" CanDelete=\"0\"");
		    strBuffer.append(" DrawingNo=\"" + drawingNo + "\""
			    // edited by tklee - 화면에서 onClick 이벤트로 처리
			    + " DrawingNoOnClick=\"" + viewUrl + "\"" + " DrawingNoHtmlPrefix=\"<font color='"
			    + PropertiesUtil.getSearchGridLinkColor() + "'>\" DrawingNoHtmlPostfix=\"</font>\"");
		    // strBuffer.append(" DrawingName=\"").appendContent(drawingName).append("\"");
		    strBuffer.append(" DrawingName=\"").appendRepl(drawingName).append("\"");
		    strBuffer.append(" FileType=\"Img\"" + " FileIcon=\"" + iconPath + "\"" + " FileIconAlign=\"Center\""
			    + " FileOnClick=\"javascript:downCadFile('" + fileDoownloadUrl + "');\"" + " FileHtmlPrefix=\"<font color='"
			    + PropertiesUtil.getSearchGridLinkColor() + "'>\" FileHtmlPostfix=\"</font>\"");
		    strBuffer.append(" Ver=\"" + version + "\"");
		    strBuffer.append(" Security=\"" + security + "\"");
		    strBuffer.append(" ManufactureVer=\"" + manufactureVer + "\"");
		    strBuffer.append(" CADType=\"" + cadType + "\"");
		    strBuffer.append(" Creator=\"" + creator + "\"");
		    strBuffer.append(" CreateDate=\"" + DateUtil.getDateString(createStamp, "d") + "\"");
		    strBuffer.append(" Status=\"" + State.toState(state).getDisplay(locale) + "\"");
		    strBuffer.append(" Dummy=\"" + dummy + "\"");
		    strBuffer.append(" ProjectNo=\"" + projectNo + "\"");
		    strBuffer.append(" ProjectName=\"").appendRepl(projectName).append("\"");
		    strBuffer.append(" CreateDeptName=\"").appendRepl(createDeptName).append("\"");
		    strBuffer.append(" partNumber=\"" + partNumber + "\"");
		    strBuffer.append(" devStage=\"" + devStage + "\"");
		    strBuffer.append(" category=\"" + category + "\"");
		    strBuffer.append(" RequestDate=\"" + requestDate + "\"");
		    strBuffer.append(" ApprovalDate=\"" + approvalDate + "\"");
		    strBuffer.append("/>");

		}

		// req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
		// req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
		// req.setAttribute("resultStr", resultStr); // 검색 결과 건수 제한 여부(제한될 경우 T)

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strBuffer.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));
		// gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");

	    } else {

		int totalCount = epmDocumentDao.searchEPMDocumentCount(conditionList);

		// if (!PropertiesUtil.getSearchGridCountLimitFlag() || totalCount <= PropertiesUtil.getSearchGridCountLimit()) {
		// // 검색 결과 건수를 제한하지 않거나, 검색 결과 건수가 검색 결과 제한 건수 이하일 경우 검색 결과 모두 표시
		// resultSize = totalCount;
		// } else {
		// // 검색 결과 개수가 검색 결과 제한 건수 초과일 경우 제한 건수만 표시
		// resultSize = PropertiesUtil.getSearchGridCountLimit();
		// resultStr = "T"; // 검색 결과 건수 제한 (alert message 표시 용도)
		// }

		req.setAttribute("rootCount", String.valueOf(totalCount));
		req.setAttribute("pagingLength", String.valueOf(pager.getPagingLength(totalCount)));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridData.jsp");
	    }

	} catch (ServletException e) {

	    Kogger.error(getClass(), e);
	} catch (IOException e) {

	    Kogger.error(getClass(), e);
	} catch (Exception e) {

	    Kogger.error(getClass(), e);
	} finally {
	}
    }

    private String getPartNumber(EPMDocument epm, String category) {
	try {
	    ArrayList relateds = EDMHelper.getReferencedByParts(epm, EDMProperties.getInstance().getReferenceType(category),
		    EDMHelper.REQUIRED_STANDARD);
	    if ((relateds != null) && (relateds.size() > 0)) {
		return ((WTPart) ((Object[]) relateds.get(0))[1]).getNumber();
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
	return "";
    }

    private void openSearch(HttpServletRequest req, HttpServletResponse res) {
	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	    req.setAttribute("openSearch", "Y");
	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    gotoResult(req, res, "/jsp/edm/SearchEPMDocument.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void openMySearch(HttpServletRequest req, HttpServletResponse res) {
	try {
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
	    req.setAttribute("openSearch", "Y");
	    req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
	    gotoResult(req, res, "/extcore/jsp/wfm/workspace/listMyDrawings.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private void excelPrintSearchResult(HttpServletRequest req, HttpServletResponse res) {

	String command = StringUtil.trimToEmpty(req.getParameter("command"));
	String number = StringUtil.trimToEmpty(req.getParameter("number"));
	String name = StringUtil.trimToEmpty(req.getParameter("name"));
	String partNumber = StringUtil.trimToEmpty(req.getParameter("partNumber"));

	String state = StringUtil.trimToEmpty(req.getParameter("state"));
	String latest = StringUtil.trimToEmpty(req.getParameter("latest"));

	String businessType = StringUtil.trimToEmpty(req.getParameter("businessType"));
	String devStage = StringUtil.trimToEmpty(req.getParameter("devStage"));
	String category = StringUtil.trimToEmpty(req.getParameter("category"));
	String cadAppType = StringUtil.trimToEmpty(req.getParameter("cadAppType"));
	String isDummyFile = StringUtil.trimToEmpty(req.getParameter("isDummyFile"));

	String createStart = StringUtil.trimToEmpty(req.getParameter("create_start"));
	String createEnd = StringUtil.trimToEmpty(req.getParameter("create_end"));
	String modifyStart = StringUtil.trimToEmpty(req.getParameter("modify_start"));
	String modifyEnd = StringUtil.trimToEmpty(req.getParameter("modify_end"));

	String creator = StringUtil.trimToEmpty(req.getParameter("creator"));
	String modifier = StringUtil.trimToEmpty(req.getParameter("modifier"));

	String edmCreateDeptName = StringUtil.trimToEmpty(req.getParameter("edmCreateDeptName"));
	String edmModifyDeptName = StringUtil.trimToEmpty(req.getParameter("edmModifyDeptName"));

	// Added by MJOH
	String projectNo = StringUtil.trimToEmpty(req.getParameter("projectNo"));

	if (latest.length() == 0) {
	    latest = "true";
	}

	HashMap map = new HashMap();
	map.put("command", command);
	map.put("number", number);
	map.put("name", name);
	map.put("partNumber", partNumber);
	map.put("state", state);
	map.put("latest", latest);

	map.put("businessType", businessType);

	map.put("create_start", createStart);
	map.put("create_end", createEnd);
	map.put("modify_start", modifyStart);
	map.put("modify_end", modifyEnd);

	map.put("creator", creator);
	map.put("modifier", modifier);

	map.put("edmCreateDeptName", edmCreateDeptName);
	map.put("edmModifyDeptName", edmModifyDeptName);

	map.put("className", wt.epm.EPMDocument.class.getName());

	// IBA 값 처리
	if (devStage.length() > 0) {
	    map.put(EDMHelper.IBA_DEV_STAGE, DevStage.toDevStage(devStage).getDisplay(Locale.KOREA));
	}
	if (category.length() > 0) {
	    map.put(EDMHelper.IBA_CAD_CATEGORY, CADCategory.toCADCategory(category).getDisplay(Locale.KOREA));
	}
	if (cadAppType.length() > 0) {
	    map.put(EDMHelper.IBA_CAD_APP_TYPE, CADAppType.toCADAppType(cadAppType).getDisplay(Locale.KOREA));
	}
	if (isDummyFile.length() > 0) {
	    map.put(EDMHelper.IBA_DUMMY_FILE, isDummyFile);
	}

	map.put("projectNo", projectNo);

	Vector sorts = new Vector();
	Object[] so = new Object[3];
	so[0] = "number";
	so[1] = wt.epm.EPMDocumentMaster.class;
	so[2] = new Boolean(true);
	sorts.add(so);
	map.put("sort", sorts);

	// Excel file download ...
	java.util.Date date = new java.util.Date();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
	String fileName = "SearchEDMList_" + formatter.format(date) + ".xls";

	res.setContentType("application/vnd.ms-excel;charset=euc-kr");

	String strClient = req.getHeader("user-agent");
	if (strClient.indexOf("MSIE 5.5") != -1) {
	    // explorer 5.5
	    res.setHeader("Content-Disposition", "filename=" + fileName);

	} else {
	    res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	}
	res.setHeader("Content-Description", "JSP Generated Data");

	res.setHeader("Content-Transfer-Encoding", "binary;");
	res.setHeader("Pragma", "no-cache;");
	res.setHeader("Expires", "-1;");

	EDMExcelModel em = new EDMExcelModel(map, req);
	WritableWorkbook workbook = null;

	try {
	    // workbook = em.getWorkbook(res);
	    workbook = em.getWorkbook(new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName));
	    workbook.write();
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (workbook != null) {
		    workbook.close();
		}
	    } catch (IOException ie) {
		workbook = null;
	    }
	}

	File drmFile = new File(E3PSDRMHelper.downloadFileTempFolder + File.separator + fileName);

	try {
	    drmFile = E3PSDRMHelper.downloadExcel(drmFile, fileName, fileName.substring(0, fileName.lastIndexOf(".")), req.getRemoteAddr());

	    FileInputStream fin = new FileInputStream(drmFile);
	    int ifilesize = (int) drmFile.length();
	    byte b[] = new byte[ifilesize];

	    ServletOutputStream sos = res.getOutputStream();

	    fin.read(b);
	    sos.write(b, 0, ifilesize);

	    sos.flush();
	    sos.close();
	    fin.close();
	} catch (Exception wte) {
	    Kogger.error(getClass(), wte);
	}
    }

    // 썸네일 가져오기
    private void thumbview(HttpServletRequest req, HttpServletResponse res) throws IOException {

	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);
	String partOid = paramMap.getString("oid");

	Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");
	Kogger.debug(getClass(), "oid is " + partOid);
	Kogger.debug(getClass(), "HEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEEHEENEE");

	OutputStream out = res.getOutputStream();
	InputStream is = null;

	boolean isDefaultImage = false;

	try {

	    if (partOid != null && partOid.indexOf("wt.part.WTPart:") == -1) {
		partOid = "wt.part.WTPart:" + partOid;
	    }

	    /*
	     * WTPart wtPart = (WTPart) CommonUtil.getObject(partOid); EPMDocument epm =
	     * PartBaseHelper.service.getThumbViewEPMDocByPart(wtPart);
	     */
	    EPMDocument epm = PartBaseHelper.service.getThumbViewEPMDocByPart(partOid);

	    String imgPath = "\\codebase\\portal\\innoditor_u\\image\\blank.gif"; // "\\codebase\\extcore\\jsp\\bom\\img\\part.gif";

	    if (epm == null) {

		isDefaultImage = true;

		// URL oracle = new URL(req.getScheme() + "://" + req.getServerName() + "/plm/extcore/jsp/bom/img/part.gif");
		// oracle.openStream();
		WTProperties prop = WTProperties.getServerProperties();
		String wthome = prop.getProperty("wt.home");

		String filePath = wthome + imgPath;
		File file = new File(filePath);
		is = new FileInputStream(file);

		String mimeType = ".gif";
		byte[] bytes = new byte[1024];
		int bytesRead;

		res.setContentType(mimeType);

		while ((bytesRead = is.read(bytes)) != -1) {
		    out.write(bytes, 0, bytesRead);
		}

		is.close();
		out.close();

	    } else {

		ApplicationData ad = null;
		Representation representation = PublishUtils.getRepresentation(epm);

		Kogger.debug(getClass(), "representation is " + ((representation != null) ? representation.toString() : null));

		if (representation != null) {
		    representation = (Representation) ContentHelper.service.getContents(representation);
		    ad = ContentHelper.service.getThumbnail(representation); // Small
		}

		Kogger.debug(getClass(), "ad is " + ((ad != null) ? ad.toString() : null));

		if (ad != null) {

		    ad.setHolderLink(getHolderToContent(representation, ad));
		    ContentDownload down = new ContentDownload();
		    down.addContentStream(ad);
		    down.execute();

		    is = down.getInputStream();

		} else {

		    isDefaultImage = true;

		    WTProperties prop = WTProperties.getServerProperties();
		    String wthome = prop.getProperty("wt.home");

		    String filePath = wthome + imgPath;
		    File file = new File(filePath);
		    is = new FileInputStream(file);

		    String mimeType = ".gif";
		    byte[] bytes = new byte[1024];
		    int bytesRead;

		    res.setContentType(mimeType);

		    while ((bytesRead = is.read(bytes)) != -1) {
			out.write(bytes, 0, bytesRead);
		    }

		    is.close();
		    out.close();
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	if (is != null && !isDefaultImage) {
	    byte[] buf = new byte[1024];
	    int count = 0;
	    while ((count = is.read(buf)) >= 0) {
		out.write(buf, 0, count);
	    }
	    out.close();
	    is.close();

	} else {
	    // Do nothing..!!
	}
    }

    private HolderToContent getHolderToContent(ContentHolder contentHolder, ApplicationData ad) throws Exception {

	QuerySpec spec = new QuerySpec(ApplicationData.class, wt.content.HolderToContent.class);
	spec.appendWhere(new SearchCondition(ApplicationData.class, "thePersistInfo.theObjectIdentifier.id", "=", ad.getPersistInfo()
	        .getObjectIdentifier().getId()));

	QueryResult queryresult = PersistenceHelper.manager.navigate(contentHolder, "theContentItem", spec, false); // (pp,
	                                                                                                            // "theContentItem",
	                                                                                                            // wt.content.HolderToContent.class,
	                                                                                                            // false);

	HolderToContent holdertocontent = (HolderToContent) queryresult.nextElement();

	return holdertocontent;
    }
}
