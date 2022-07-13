/* 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : TechDocumentServlet.java
 * 작성자 : 김경종
 * 작성일자 : 2010. 12
 */
package e3ps.dms.servlet;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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

import org.json.JSONObject;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentServerHelper;
import wt.fc.ObjectIdentifier;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.content.ContentUtil;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.dms.dao.DocumentDao;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETTechnicalCategoryLink;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.dms.props.PropertyHandler;
import e3ps.dms.service.KETDmsHelper;
import e3ps.dms.service.internal.TechdocUpload;
import e3ps.groupware.company.CompanyState;
import e3ps.groupware.company.PeopleData;
import ext.ket.shared.log.Kogger;

@SuppressWarnings("serial")
public class TechDocumentServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	String cmd = req.getParameter("cmd");
	System.out.println("CMD = " + cmd);
	String msg = null;
	String param = "";

	if (cmd.equals("create")) {

	    msg = create(req, res);
	    if (msg == "f") {
		msg = "문서생성에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);
		alertNgo(res, msg, "/plm/jsp/dms/ViewTechDocument.jsp" + param);
	    }
	} else if (cmd.equals("update")) {
	    msg = update(req, res);
	    if (msg == "f") {
		msg = "문서수정에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);
		alertNgo(res, msg, "/plm/jsp/dms/ViewTechDocument.jsp" + param);
	    }
	} else if (cmd.equals("updatePop")) {
	    msg = update(req, res);
	    if (msg == "f") {
		msg = "문서수정에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);
		alertNgo(res, msg, "/plm/jsp/dms/ViewTechDocumentPop.jsp" + param + "&buttenView=T");
	    }
	} else if (cmd.equals("delete")) {
	    msg = delete(req, res);
	    if (msg == "f") {
		msg = "문서삭제에 실패하였습니다.";
		alert(res, msg);
	    } else {
		msg = "문서가 삭제되었습니다.";
		alertNclose(res, msg);
	    }
	}
	/*
	 * Start [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용, 2013. 5. 31, 오명재
	 */
	else if (cmd.equals("search")) {

	    search(req, res);
	}
	/*
	 * End [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용, 2013. 5. 31, 오명재
	 */
	else if (cmd.equals("excelDown")) {

	    excelDown(req, res);
	} else if (cmd.equals("designFileOpen")) {
	    designFileOpen(req, res);
	}
    }

    /*
     * 함수명 : create 설명 : 문서생성 작성자 : 김경종 작성일자 : 2010. 12
     * 
     * 
     * [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가 수정일자 : 2013. 6. 3 수정자 : 김종호
     */
    private String create(HttpServletRequest req, HttpServletResponse res) {
	KETTechnicalDocument docu = null;
	String returnTemp = null;
	String categoryCode = null;
	String documentName = null;
	Hashtable tempHash = new Hashtable();

	String tmpStr = null;

	FormUploader uploader = null;
	uploader = FormUploader.newFormUploader(req);
	// 화면에서 받은 Parameter를 Hashtable에 저장한다.
	Hashtable param = uploader.getFormParameters();

	try {
	    categoryCode = (String) param.get("categoryCode");
	    documentName = (String) param.get("documentName");

	    Kogger.debug(getClass(), "t categoryCode=====>" + categoryCode);

	    tempHash.put("categoryCode", new String(categoryCode));
	    tempHash.put("documentName", new String(documentName));
	    tempHash.put("divisionCode", new String((String) param.get("divisionCodeStr")));
	    tempHash.put("documentDescription", new String((String) param.get("documentDescription")));
	    tmpStr = StringUtil.checkNull(new String((String) param.get("security")));
	    tempHash.put("security", new String(tmpStr));
	    tempHash.put("isDesign", new String((String) param.get("isDesign")));
	    if ("Y".equals((String) param.get("isDesign"))) {
		tempHash.put("techDeptCode", new String((String) param.get("techDeptCode")));
		tempHash.put("techDeptName", new String((String) param.get("techDeptName")));
		tempHash.put("duty", new String((String) param.get("duty")));
	    }
	    /*
	     * Start [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */
	    tempHash.put("webEditor", new String((String) param.get("webEditor")));
	    tempHash.put("webEditorText", new String((String) param.get("webEditorText")));
	    /*
	     * End [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */

	    // 최종입력을 위해 저장용 임시 Hashtable을 Helper.service에 넘긴다.
	    docu = KETDmsHelper.service.insertTechDoc(tempHash);
	    if (docu != null) {
		// 첨부파일 정보는 문서저장후 attach시킨다.
		Vector files = uploader.getFiles();
		if (files != null) {
		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);

			isPrimary = false;
			if ("iFile0".equalsIgnoreCase(file.getFieldName())) {
			    isPrimary = true;
			}
			E3PSContentHelper.service.attach(docu, file, "", isPrimary);
		    }
		}

		ContentHolder contentHolder;
		try {
		    System.out.println("시작합니다..");
		    contentHolder = ContentHelper.service.getContents(docu);
		    System.out.println("끝...");
		} catch (PropertyVetoException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		returnTemp = CommonUtil.getOIDString(docu);
		String oid = returnTemp;
		returnTemp = "문서가 생성되었습니다.oid=" + returnTemp;
		Kogger.debug(getClass(), "Servlet Create DocumentOID=====>" + returnTemp);
		if ("Y".equals(docu.getAttribute1())) {// 설계가이드 일때 파일 업로드
		    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
		    HashMap<String, String> paramMap = new HashMap<String, String>();
		    try {
			PeopleData peoData = new PeopleData(user);
			String userOid = CommonUtil.getOIDLongValue2Str(peoData.wtuserOID);
			String ip = req.getRemoteAddr();
			paramMap.put("oid", oid);
			paramMap.put("userOid", userOid);
			paramMap.put("ip", ip);
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    TechdocUpload techUp = new TechdocUpload();
		    techUp.disignFileUpload(paramMap);
		}
	    } else {
		returnTemp = "f";
	    }
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}

	return returnTemp;
    }

    /*
     * 함수명 : update 설명 : 문서수정 작성자 : 김경종 작성일자 : 2010. 12
     * 
     * 
     * [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가 수정일자 : 2013. 6. 3 수정자 : 김종호
     */
    private String update(HttpServletRequest req, HttpServletResponse res) {
	KETTechnicalDocument docu = null;
	String returnTemp = null;
	String docuOid = null;
	String sfiles = null;
	Hashtable tempHash = new Hashtable();

	String tmpStr = null;

	try {
	    WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();
	} catch (WTException e2) {
	    Kogger.error(getClass(), e2);
	}

	FormUploader uploader = null;
	// 화면에서 받은 Parameter를 Hashtable에 저장한다.
	uploader = FormUploader.newFormUploader(req);
	Hashtable param = uploader.getFormParameters();
	try {
	    String cmd = (String) param.get("cmd");

	    docuOid = (String) param.get("docuOid");
	    docu = (KETTechnicalDocument) CommonUtil.getObject(docuOid);
	    tmpStr = (String) param.get("categoryCode");
	    Kogger.debug(getClass(), "upd categoryCode=====>" + tmpStr);
	    tempHash.put("categoryCode", new String(tmpStr));
	    tempHash.put("docuOid", new String(docuOid));

	    tmpStr = (String) param.get("documentName");
	    tempHash.put("documentName", new String(tmpStr));
	    tempHash.put("documentDescription", new String((String) param.get("documentDescription")));

	    /*
	     * Start [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */
	    tempHash.put("webEditor", new String((String) param.get("webEditor")));
	    tempHash.put("webEditorText", new String((String) param.get("webEditorText")));
	    /*
	     * End [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */

	    // 첨부파일정보는 추가된것과 삭제해야 할것을 구분하여 처리한다.
	    // 삭제정보를 따로 String형태로 받아 삭제해준다.
	    sfiles = (String) param.get("isFileDel");
	    Kogger.debug(getClass(), "delfiles=====>" + sfiles);
	    tempHash.put("isFileDel", new String(sfiles));
	    tempHash.put("isDesign", new String((String) param.get("isDesign")));
	    if ("Y".equals((String) param.get("isDesign"))) {
		tempHash.put("techDeptCode", new String((String) param.get("techDeptCode")));
		tempHash.put("techDeptName", new String((String) param.get("techDeptName")));
		tempHash.put("duty", new String((String) param.get("duty")));
	    }

	    // 추가된 첨부파일정보가 있다면 주첨부파일변경 포함하여 attach시킨다.
	    Vector files = uploader.getFiles();
	    if (files == null) {
		tempHash.put("files", new String("0"));
	    } else {
		tempHash.put("files", new Vector(files));
	    }
	    String oid = "";
	    // 최종수정을 위해 저장용 임시 Hashtable을 Helper.service에 넘긴다.
	    if (cmd.equals("revice")) {
		docu = KETDmsHelper.service.reviceTechDoc(tempHash);
		if (docu != null) {
		    returnTemp = CommonUtil.getOIDString(docu);
		    oid = returnTemp;
		    Kogger.debug(getClass(), "Servlet revice DocumentOID=====>" + returnTemp);
		    returnTemp = "개정되었습니다.oid=" + returnTemp;
		} else {
		    returnTemp = "f";
		}
	    } else {
		docu = KETDmsHelper.service.updateTechDoc(tempHash);
		if (docu != null) {
		    returnTemp = CommonUtil.getOIDString(docu);
		    oid = returnTemp;
		    Kogger.debug(getClass(), "Servlet Updated DocumentOID=====>" + returnTemp);
		    returnTemp = "수정되었습니다.oid=" + returnTemp;
		} else {
		    returnTemp = "f";
		}
	    }

	    boolean useDefenseCode = false; // 설계가이드 파일업로드 기능 비활성 2019.07.10 by 황정태

	    if (useDefenseCode && "Y".equals(docu.getAttribute1())) {// 설계가이드 일때 파일 업로드
		WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
		HashMap<String, String> paramMap = new HashMap<String, String>();
		try {
		    PeopleData peoData = new PeopleData(user);
		    String userOid = CommonUtil.getOIDLongValue2Str(peoData.wtuserOID);
		    String ip = req.getRemoteAddr();
		    paramMap.put("oid", oid);
		    paramMap.put("userOid", userOid);
		    paramMap.put("ip", ip);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		TechdocUpload techUp = new TechdocUpload();
		techUp.disignFileUpload(paramMap);
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}

	return returnTemp;
    }

    /*
     * 함수명 : delete 설명 : 문서수정 작성자 : 김경종 작성일자 : 2010. 12
     */
    private String delete(HttpServletRequest req, HttpServletResponse res) {
	String returnTemp = null;
	String docuOid = null;
	KETTechnicalDocument docu = null;
	String docuNo = null;
	try {
	    WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();
	} catch (WTException e2) {
	    Kogger.error(getClass(), e2);
	}

	try {
	    docuOid = req.getParameter("oid");
	    docu = (KETTechnicalDocument) CommonUtil.getObject(docuOid);
	    docuNo = docu.getNumber();

	    returnTemp = KETDmsHelper.service.deleteTechDoc(docuOid);
	    if (returnTemp != null) {
		Kogger.debug(getClass(), "Servlet Delete DocumentOID=====>" + returnTemp);
		returnTemp = docuNo + ": 삭제되었습니다.";
	    } else {
		returnTemp = "f";
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}
	return returnTemp;
    }

    /*
     * [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용 수정일자 : 2013. 5. 31 수정자 : 오명재
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	String fileDoownloadUrl = null;
	String iconPath = "";

	try {

	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    DocumentDao documentDao = new DocumentDao();

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    paramMap.put("locale", messageService.getLocale());

	    // categoryCode 초기화
	    // paramMap.put("categoryCode", "");
	    // if (paramMap.getStringArray("category3").length > 0) {
	    // paramMap.put("categoryCode", paramMap.getStringArray("category3"));
	    // } else if (paramMap.getStringArray("category2").length > 0) {
	    // paramMap.put("categoryCode", paramMap.getStringArray("category2"));
	    // } else if (paramMap.getStringArray("category1").length > 0) {
	    // paramMap.put("categoryCode", paramMap.getStringArray("category1"));
	    // }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("techDocumentSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("techDocumentSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("techDocumentSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("techDocumentSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 목록 결과
	    Vector dutyCodeList = CompanyState.dutyCodeList;
	    HashMap<String, String> dutyOrder = new HashMap<String, String>();
	    String key = "";
	    for (int i = 0; i < dutyCodeList.size(); i++) {
		key = (String) dutyCodeList.get(i);
		dutyOrder.put(key, String.valueOf(dutyCodeList.size() - i));
	    }
	    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

	    PeopleData peoData = new PeopleData(user);

	    String sessionOrder = dutyOrder.get(peoData.dutycode);

	    String dataOrder = null;

	    List<Map<String, Object>> list = documentDao.searchTechDocumentList(conditionList);

	    for (Map<String, Object> techDoc : list) {
		if ("Y".equals(techDoc.get("ISDESIGN")) && !CommonUtil.isAdmin() && !CommonUtil.isMember("설계가이드관리") && !CommonUtil.isMember("설계가이드임원")) {
		    dataOrder = dutyOrder.get(techDoc.get("DUTY"));
		    if (Integer.parseInt(sessionOrder) < Integer.parseInt(dataOrder)) {
			continue;
		    }
		}
		// 주 첨부파일
		if (techDoc.get("holderOid") != null && techDoc.get("appDataOid") != null) {
		    // fileDoownloadUrl = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + techDoc.get("holderOid") + "&adOid=" +
		    // techDoc.get("appDataOid");
		    fileDoownloadUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + techDoc.get("holderOid") + "&cioids=" + techDoc.get("appDataOid") + "&role=PRIMARY";
		    iconPath = ContentUtil.getContentIconPath((String) techDoc.get("extension"));
		} else {
		    fileDoownloadUrl = "";
		    iconPath = "";
		}

		String state = State.toState(techDoc.get("stateState").toString()).getDisplay(messageService.getLocale());

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" DocNo=&quot;" + techDoc.get("documentNo") + "&quot;" + " DocNoOnClick=&quot;javascript:openView(&apos;" + techDoc.get("oid") + "&apos;);&quot;"
		        + " DocNoHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;" + " DocNoHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" DocType=&quot;" + techDoc.get("categoryName") + "&quot;");
		strBuffer.append(" DocName=&quot;" + TreeGridUtil.replaceContentForI(techDoc.get("title").toString()) + "&quot;");
		strBuffer.append(" Status=&quot;" + state + "&quot;");
		strBuffer.append(" Ver=&quot;" + techDoc.get("ver") + "&quot;");
		strBuffer.append(" Creator=&quot;" + techDoc.get("creatorName") + "&quot;");
		strBuffer.append(" DeptName=&quot;" + techDoc.get("deptName") + "&quot;");
		strBuffer.append(" CreateDate=&quot;" + techDoc.get("createDate") + "&quot;");
		if ("Y".equals(techDoc.get("ISDESIGN"))) {
		    // strBuffer.append(" FileType=&quot;Img&quot;" + " FileIcon=&quot;" + iconPath + "&quot;" +
		    // " FileIconAlign=&quot;Center&quot;"
		    // + " FileOnClick=&quot;javascript:fileOpenAjax(&apos;" + techDoc.get("oid") + "&apos;" + ",&apos;" +
		    // techDoc.get("appDataOid") + "&apos;);&quot;");

		    // 바로 열기 방식은 사용자 PC환경에 따라 영향도가 심하여 일반 다운로드 형식으로 바꿨음 원복이 필요하면 아래 주석 처리하고 위의 주석을 풀면됨 2019.07.10 황정태
		    strBuffer.append(" FileType=&quot;Img&quot;" + " FileIcon=&quot;" + iconPath + "&quot;" + " FileIconAlign=&quot;Center&quot;" + " FileOnClick=&quot;javascript:goOpen(&apos;"
			    + fileDoownloadUrl + "&apos;);&quot;");
		} else {
		    strBuffer.append(" FileType=&quot;Img&quot;" + " FileIcon=&quot;" + iconPath + "&quot;" + " FileIconAlign=&quot;Center&quot;"
		    // + " FileOnClick=&quot;<a href=&apos;" + fileDoownloadUrl + "&apos; target='download'>&quot;");
			    + " FileOnClick=&quot;javascript:goOpen(&apos;" + fileDoownloadUrl + "&apos;);&quot;");
		}
		// + " FileOnClick=&quot;javascript:goOpen(&apos;" + fileDoownloadUrl + "&apos;" + "," + "&apos;_blank&apos;);&quot;");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/dms/SearchTechDocument.jsp");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : Excel 다운 서블릿 적용 수정일자 : 2013. 7. 24 수정자 : 김종호
     */
    private void excelDown(HttpServletRequest req, HttpServletResponse res) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	try {
	    // 커넥션 생성
	    DocumentDao documentDao = new DocumentDao();

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    paramMap.put("locale", messageService.getLocale());

	    // categoryCode 초기화
	    paramMap.put("categoryCode", "");
	    if (paramMap.getStringArray("category3").length > 0) {
		paramMap.put("categoryCode", paramMap.getStringArray("category3"));
	    } else if (paramMap.getStringArray("category2").length > 0) {
		paramMap.put("categoryCode", paramMap.getStringArray("category2"));
	    } else if (paramMap.getStringArray("category1").length > 0) {
		paramMap.put("categoryCode", paramMap.getStringArray("category1"));
	    }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("techDocumentSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("techDocumentSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("techDocumentSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("techDocumentSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // Excel File 위치 설정
	    String userAgent = req.getHeader("User-Agent");
	    boolean ie = userAgent.indexOf("MSIE") > -1;

	    SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	    String sWtHome = wt.util.WTProperties.getLocalProperties().getProperty("wt.home", "");
	    String sFilePath = sWtHome + "/temp/download";
	    File downPath = new File(sFilePath);
	    if (!downPath.isDirectory()) {
		downPath.mkdir();
	    }
	    String sFileName = "SearchTechDocumentList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    try {

		WritableWorkbook workbook = Workbook.createWorkbook(new File(sFilePath + "/" + sFileName));
		WritableSheet s1 = workbook.createSheet("First Sheet", 0);

		s1.setName("문서검색목록");

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		// 문서제목
		Label lr = new jxl.write.Label(0, 0, "문서검색목록");
		s1.addCell(lr);

		int row = 2;

		// 사업부
		lr = new jxl.write.Label(0, row, "사업부", cellFormat);
		s1.addCell(lr);
		// 문서번호
		lr = new jxl.write.Label(1, row, "문서번호", cellFormat);
		s1.addCell(lr);
		// 버전
		lr = new jxl.write.Label(2, row, "버전", cellFormat);
		s1.addCell(lr);
		// 결재상태
		lr = new jxl.write.Label(3, row, "결재상태", cellFormat);
		s1.addCell(lr);
		// 대분류
		lr = new jxl.write.Label(4, row, "대분류", cellFormat);
		s1.addCell(lr);
		// 중분류
		lr = new jxl.write.Label(5, row, "중분류", cellFormat);
		s1.addCell(lr);
		// 소분류
		lr = new jxl.write.Label(6, row, "소분류", cellFormat);
		s1.addCell(lr);
		// 문서명
		lr = new jxl.write.Label(7, row, "문서명", cellFormat);
		s1.addCell(lr);
		// 문서설명
		lr = new jxl.write.Label(8, row, "문서설명", cellFormat);
		s1.addCell(lr);
		// 작성자
		lr = new jxl.write.Label(9, row, "작성자", cellFormat);
		s1.addCell(lr);
		// 작성부서
		lr = new jxl.write.Label(10, row, "작성부서", cellFormat);
		s1.addCell(lr);
		// 작성일자
		lr = new jxl.write.Label(11, row, "작성일자", cellFormat);
		s1.addCell(lr);

		QueryResult r = null;
		KETDocumentCategory docCate = null;
		String docCatePath = "";
		String categoryCode = "";
		String docCatePath1 = "";
		String docCatePath2 = "";
		String docCatePath3 = "";
		String divisionCode = "";

		// 목록 결과
		KETTechnicalDocument techDocObj = null;
		List<Map<String, Object>> list = documentDao.searchTechDocumentList(conditionList);
		for (Map<String, Object> techDoc : list) {

		    techDocObj = (KETTechnicalDocument) CommonUtil.getObject(techDoc.get("oid").toString());

		    row++;

		    divisionCode = StringUtil.checkNull(techDocObj.getDivisionCode());
		    if (divisionCode.equals("CA")) {
			divisionCode = "자동차";
		    } else if (divisionCode.equals("ER")) {
			divisionCode = "전자";
		    } else {
			divisionCode = "-";
		    }

		    // 사업부
		    lr = new jxl.write.Label(0, row, divisionCode, cellFormat);
		    s1.addCell(lr);
		    // 문서번호
		    lr = new jxl.write.Label(1, row, techDocObj.getNumber(), cellFormat);
		    s1.addCell(lr);
		    // 버전
		    lr = new jxl.write.Label(2, row, StringUtil.checkNull(techDoc.get("ver").toString()), cellFormat);
		    s1.addCell(lr);
		    // 결재상태
		    lr = new jxl.write.Label(3, row, StringUtil.checkNull(techDoc.get("state").toString()), cellFormat);
		    s1.addCell(lr);

		    try {
			docCate = null;
			r = PersistenceHelper.manager.navigate(techDocObj, "documentCategory", KETTechnicalCategoryLink.class);
			if (r.hasMoreElements()) {
			    docCate = (KETDocumentCategory) r.nextElement();
			    categoryCode = docCate.getCategoryCode();

			    docCatePath = KETDmsHelper.service.selectCategoryPath(categoryCode);
			    docCatePath = docCatePath.substring(1);

			    StringTokenizer st = new StringTokenizer(docCatePath, "/");
			    if (st.hasMoreTokens()) {
				docCatePath1 = st.nextToken();
			    }
			    if (st.hasMoreTokens()) {
				docCatePath1 = st.nextToken();
			    }
			    if (st.hasMoreTokens()) {
				docCatePath2 = st.nextToken();
			    }
			    if (st.hasMoreTokens()) {
				docCatePath3 = st.nextToken();
			    }
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }

		    // 대분류
		    lr = new jxl.write.Label(4, row, StringUtil.checkNull(docCatePath1), cellFormat);
		    s1.addCell(lr);
		    // 중분류
		    lr = new jxl.write.Label(5, row, StringUtil.checkNull(docCatePath2), cellFormat);
		    s1.addCell(lr);
		    // 소분류
		    lr = new jxl.write.Label(6, row, StringUtil.checkNull(docCatePath3), cellFormat);
		    s1.addCell(lr);
		    // 문서명
		    lr = new jxl.write.Label(7, row, StringUtil.checkNull(techDoc.get("title").toString()), cellFormat);
		    s1.addCell(lr);
		    // 문서설명
		    lr = new jxl.write.Label(8, row, StringUtil.checkNull((String) techDocObj.getWebEditorText()), cellFormat);
		    s1.addCell(lr);
		    // 작성자
		    lr = new jxl.write.Label(9, row, StringUtil.checkNull(techDoc.get("creatorName").toString()), cellFormat);
		    s1.addCell(lr);
		    // 작성부서
		    lr = new jxl.write.Label(10, row, StringUtil.checkNull(techDoc.get("deptName").toString()), cellFormat);
		    s1.addCell(lr);
		    // 작성일
		    lr = new jxl.write.Label(11, row, techDoc.get("createDate").toString(), cellFormat);
		    s1.addCell(lr);
		}

		workbook.write();
		workbook.close();
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		throw e;
	    }

	    try {
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-18
		// 엑셀 다운로드 파일 DRM 암호화 적용
		String contentID = sFileName.substring(0, sFileName.lastIndexOf("."));
		excelFileObj = E3PSDRMHelper.downloadExcel(excelFileObj, sFileName, contentID, req.getRemoteAddr());
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////

		FileInputStream fin = new FileInputStream(excelFileObj);
		int ifilesize = (int) excelFileObj.length();
		byte b[] = new byte[ifilesize];

		res.setContentLength(ifilesize);
		res.setContentType("application/vnd.ms-excel;charset=UTF-8");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + sFileName + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");

		ServletOutputStream oout = res.getOutputStream();
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
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {

	}
    }

    /*
     * 함수명 : designFileOpen 설명 : 설계가이드문서 open 메써드 작성자 : 황정태 작성일자 : 2015. 03
     */
    private void designFileOpen(HttpServletRequest req, HttpServletResponse res) {
	try {
	    req.setCharacterEncoding("UTF-8");

	    // 다국어 처리
	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    JSONObject jsonObject = new JSONObject();
	    String oid = req.getParameter("oid");
	    String appDataOid = req.getParameter("appDataOid");
	    System.out.println("=============oid : " + oid);
	    System.out.println("=============appDataOid : " + appDataOid);
	    KETTechnicalDocument dms = (KETTechnicalDocument) CommonUtil.getObject(oid);
	    // FormatContentHolder holder = (FormatContentHolder) dms;
	    // ContentInfo info = null;

	    /*
	     * if("primary".equals(gubun)){ info = ContentUtil.getPrimaryContent(holder); }else{ secondaryFiles =
	     * ContentUtil.getSecondaryContents(holder); for (int i = 0; i < secondaryFiles.size(); i++) { ContentInfo info2 = (ContentInfo)
	     * secondaryFiles.elementAt(i);
	     * 
	     * if (info2 == null) { } else { if(filename.equals(info2.getName())){
	     * 
	     * } } } }
	     */

	    ApplicationData appData = ContentServerHelper.service.getApplicationData(ObjectIdentifier.newObjectIdentifier(appDataOid));
	    String fileName = appData.getFileName();
	    System.out.println("file==" + fileName);
	    String lastVersion = dms.getVersionInfo().getIdentifier().getValue();
	    String documentNumber = dms.getNumber();
	    // String fileName = info.getName();
	    if ("".equals(fileName) || fileName == null) {
		fileName = "file not found";
	    }

	    WTProperties properties = WTProperties.getLocalProperties();
	    String hostName = properties.getProperty("java.rmi.server.hostname");

	    Map<String, Object> parameter = new HashMap<String, Object>();
	    String start_con = "";
	    String filePath = "";
	    String end_con = "";

	    if ("plmapwas".equals(hostName)) {
		start_con = PropertyHandler.getInstance().getString("start_con_ap");
		filePath = PropertyHandler.getInstance().getString("filePath_ap");
		end_con = PropertyHandler.getInstance().getString("end_con_ap");
	    } else {
		start_con = PropertyHandler.getInstance().getString("start_con_dev");
		filePath = PropertyHandler.getInstance().getString("filePath_dev");
		end_con = PropertyHandler.getInstance().getString("end_con_dev");
	    }
	    String bat_file = PropertyHandler.getInstance().getString("bat_file");
	    filePath = filePath + documentNumber + "\\" + lastVersion + "\\";
	    System.out.println("filePath : " + filePath);
	    start_con = e3ps.common.util.Base64.encodeString(start_con);
	    bat_file = e3ps.common.util.Base64.encodeString(bat_file);
	    filePath = e3ps.common.util.Base64.encodeString(filePath);
	    end_con = e3ps.common.util.Base64.encodeString(end_con);

	    System.out.println("filePath : " + filePath);
	    parameter.put("start_con", start_con);
	    parameter.put("bat_file", bat_file);
	    parameter.put("filePath", filePath);
	    parameter.put("end_con", end_con);
	    parameter.put("fileName", fileName);

	    List<Map<String, Object>> returnObj = new ArrayList<Map<String, Object>>();
	    returnObj.add(parameter);
	    try {
		jsonObject.put("returnObj", returnObj);

	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    res.setContentType("application/x-json; charset=UTF-8");
	    res.getWriter().print(jsonObject);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	}

	/*
	 * KETTechnicalDocument docu = null; String returnTemp = null; String docuOid = null; String sfiles = null; Hashtable tempHash = new
	 * Hashtable();
	 * 
	 * String tmpStr = null;
	 * 
	 * try { WTPrincipalReference user = SessionHelper.manager.getPrincipalReference(); } catch (WTException e2) {
	 * Kogger.error(getClass(), e2); }
	 * 
	 * FormUploader uploader = null; // 화면에서 받은 Parameter를 Hashtable에 저장한다. uploader = FormUploader.newFormUploader(req); Hashtable
	 * param = uploader.getFormParameters(); try { String cmd = (String) param.get("cmd");
	 * 
	 * docuOid = (String) param.get("docuOid"); docu = (KETTechnicalDocument) CommonUtil.getObject(docuOid); tmpStr = (String)
	 * param.get("categoryCode"); Kogger.debug(getClass(), "upd categoryCode=====>" + tmpStr); tempHash.put("categoryCode", new
	 * String(tmpStr)); tempHash.put("docuOid", new String(docuOid));
	 * 
	 * tmpStr = (String) param.get("documentName"); tempHash.put("documentName", new String(tmpStr));
	 * tempHash.put("documentDescription", new String((String) param.get("documentDescription")));
	 * 
	 * tempHash.put("webEditor", new String((String) param.get("webEditor"))); tempHash.put("webEditorText", new String((String)
	 * param.get("webEditorText")));
	 * 
	 * 
	 * // 첨부파일정보는 추가된것과 삭제해야 할것을 구분하여 처리한다. // 삭제정보를 따로 String형태로 받아 삭제해준다. sfiles = (String) param.get("isFileDel");
	 * Kogger.debug(getClass(), "delfiles=====>" + sfiles); tempHash.put("isFileDel", new String(sfiles));
	 * 
	 * // 추가된 첨부파일정보가 있다면 주첨부파일변경 포함하여 attach시킨다. Vector files = uploader.getFiles(); if (files == null) { tempHash.put("files", new
	 * String("0")); } else { tempHash.put("files", new Vector(files)); }
	 * 
	 * // 최종수정을 위해 저장용 임시 Hashtable을 Helper.service에 넘긴다. if (cmd.equals("revice")) { docu =
	 * KETDmsHelper.service.reviceTechDoc(tempHash); if (docu != null) { returnTemp = CommonUtil.getOIDString(docu);
	 * Kogger.debug(getClass(), "Servlet revice DocumentOID=====>" + returnTemp); returnTemp = "개정되었습니다.oid=" + returnTemp; } else {
	 * returnTemp = "f"; } } else { docu = KETDmsHelper.service.updateTechDoc(tempHash); if (docu != null) { returnTemp =
	 * CommonUtil.getOIDString(docu); Kogger.debug(getClass(), "Servlet Updated DocumentOID=====>" + returnTemp); returnTemp =
	 * "수정되었습니다.oid=" + returnTemp; } else { returnTemp = "f"; } } } catch (Exception e) { Kogger.error(getClass(), e); returnTemp =
	 * "f"; }
	 * 
	 * return returnTemp;
	 */
    }
}
