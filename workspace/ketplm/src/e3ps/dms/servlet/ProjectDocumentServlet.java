/*
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProjectDocumentServlet.java
 * 작성자 : 김경종
 * 작성일자 : 2010. 10
 */
package e3ps.dms.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.lifecycle.State;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.query.ClassAttribute;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.content.ContentUtil;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.drm.E3PSDRMHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TgPagingControl;
import e3ps.common.treegrid.TgStringBuffer;
import e3ps.common.treegrid.TreeGridUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.dms.dao.DocumentDao;
import e3ps.dms.dao.ProjectDocDao;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.service.KETDmsHelper;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldECADocLink;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECADocLink;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectOutput;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.log.Kogger;

/*
 * 클래스명 : ProjectDocumentServlet
 * 설명 : 문서생성수정삭제
 * 작성자 : 김경종
 * 작성일자 : 2010. 10
 */
@SuppressWarnings("serial")
public class ProjectDocumentServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	String ALERT_MESSAGE = "";

	String cmd = req.getParameter("cmd");
	String isRefresh = req.getParameter("isRefresh");
	String from = req.getParameter("from");

	String msg = null;
	String param = "";

	Kogger.debug(getClass(), "cmd : " + cmd);
	Kogger.debug(getClass(), "from : " + from);

	if (cmd.equals("createPop")) {

	    msg = create(req, res);
	    if (msg == "f") {
		msg = "문서생성에 실패하였습니다.";
		alertNclose(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);
		alertNgoParent(res, msg, "/plm/jsp/dms/ViewDocumentPop.jsp" + param + "&buttenView=T");
	    }
	} else if (cmd.equals("create")) {

	    msg = create(req, res);
	    if (msg == "f") {
		msg = "문서생성에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));// + "&isPop=Y";
		msg = msg.substring(0, msg.indexOf(".") + 1);
		if (StringUtil.checkString(from) && "MyTaskOutput".equals(from)) {
		    // My Task 동일 산출물 등록 시
		    alertNreloadNclose(res, msg);
		} else if (StringUtil.checkString(from) && "TaskOutput".equals(from)) {
		    // 태스크 산출물에서 직접 등록 시
		    // alertNparentReloadNclose(res, msg);
		    try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			res.setContentType("text/html;charset=UTF-8");
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + msg + "\");"
			        + "\n   parent.opener.goReloadPage();" + "\n   parent.self.close();" + "\n </script>";
			out.println(rtn_msg);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		} else {
		    // 일반 개발산출문서 등록 시
		    alertNgo(res, msg, "/plm/jsp/dms/ViewDocument.jsp" + param);
		}
	    }
	} else if (cmd.equals("update")) {
	    msg = update(req, res);
	    if (msg == "f") {
		msg = "문서수정에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);
		alertNgo(res, msg, "/plm/jsp/dms/ViewDocument.jsp" + param);
	    }
	} else if (cmd.equals("updatePop")) {
	    msg = update(req, res);
	    if (msg == "f") {
		msg = "문서수정에 실패하였습니다.";
		alert(res, msg);
	    } else {
		param = "?" + msg.substring(msg.indexOf("oid"));
		msg = msg.substring(0, msg.indexOf(".") + 1);
		alertNgo(res, msg, "/plm/jsp/dms/ViewDocument.jsp" + param + "&buttenView=T" + "&isRefresh=" + isRefresh);
	    }
	} else if (cmd.equals("delete")) {

	    try {
		msg = delete(req, res);

		if (msg == "f") {
		    msg = "문서삭제에 실패하였습니다.";
		    alert(res, msg);
		} else {
		    // alertNgo(res, msg, "/plm/jsp/dms/SearchDocument.jsp");
		    alertNparentReloadNclose(res, msg);
		}

	    } catch (WTException wte) {
		Kogger.error(getClass(), wte);

		// 에러 메세지 처리
		ALERT_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
		ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("\"", "\\\\\"");

		alert(res, msg);
	    }

	} else if (cmd.equals("deletePop")) {

	    try {
		msg = delete(req, res);

		if (msg == "f") {
		    msg = "문서삭제에 실패하였습니다.";
		    alert(res, msg);
		} else {
		    alertNparentReloadNclose(res, msg);
		}

	    } catch (WTException wte) {
		Kogger.error(getClass(), wte);

		// 에러 메세지 처리
		ALERT_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
		ALERT_MESSAGE = ALERT_MESSAGE.replaceAll("\"", "\\\\\"");

		alert(res, msg);
	    }

	}
	/*
	 * Start [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용, 2013. 6. 4, 김종호
	 */
	else if (cmd.equals("searchGridData")) {
	    searchGrid(req, res, false);
	} else if (cmd.equals("searchGridPage")) {
	    searchGrid(req, res, true);
	} else if (cmd.equals("excelDown")) {
	    excelDown(req, res);
	} else if (cmd.equals("searchMultiApp")) {
	    searchMultiApp(req, res);
	} else if (cmd.equals("excelDownMultiApp")) {
	    excelDownMultiApp(req, res);
	} else if (cmd.equals("searchDocumentPop")) {
	    searchDocumentPop(req, res);
	}
    }

    /*
     * 함수명 : create 설명 : 문서생성 작성자 : 김경종 작성일자 : 2010. 9 [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가 수정일자 : 2013. 6. 3 수정자 : 김종호
     */
    @SuppressWarnings("unchecked")
    private String create(HttpServletRequest req, HttpServletResponse res) {
	KETProjectDocument docu = null;
	String returnTemp = null;
	String categoryCode = null;
	String documentName = null;
	String analysisCode = null;
	String analysisCodeTxt = null;
	String costComment = null;
	Hashtable tempHash = new Hashtable();

	String tmpStr = null;

	try {
	    // WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();
	    WTUser user1 = (WTUser) SessionHelper.manager.getPrincipal();

	    // PeopleData peoData = new PeopleData(user1);
	    // String title = peoData.departmentName;
	    // People people = getPeople(user.getName());

	} catch (WTException e2) {
	    Kogger.error(getClass(), e2);
	}

	FormUploader uploader = null;
	uploader = FormUploader.newFormUploader(req);
	// 화면에서 받은 Parameter를 Hashtable에 저장한다.
	Hashtable param = uploader.getFormParameters();

	Kogger.debug(getClass(), "ProjectDocumentServlet.create()");
	Kogger.debug(getClass(), param);

	try {
	    categoryCode = (String) param.get("categoryCode");
	    documentName = (String) param.get("documentName");
	    analysisCode = (String) param.get("analysisCode");
	    analysisCodeTxt = (String) param.get("analysisCodeTxt");
	    costComment = (String) param.get("costComment");
	    // projectOid
	    // Param Hashtable에 데이터를 저장용 임시 Hashtable에 저장한다.
	    Object outputOidObj = param.get("outputOid");
	    if (outputOidObj != null) {
		if (outputOidObj instanceof Vector) {
		    tempHash.put("outputOid", new Vector((Vector) outputOidObj));
		} else {
		    tempHash.put("outputOid", new String((String) outputOidObj));
		}
	    }

	    /*
	     * tmpStr = (String) param.get("outputOid"); tempHash.put("outputOid", new String(tmpStr));
	     */

	    tempHash.put("categoryCode", new String(categoryCode));

	    tmpStr = (String) param.get("analysisCode");

	    if (StringUtils.isNotEmpty(tmpStr)) {
		tempHash.put("analysisCode", new String(tmpStr));
	    }
	    tmpStr = (String) param.get("analysisCodeTxt");

	    if (StringUtils.isNotEmpty(tmpStr)) {
		tempHash.put("analysisCodeTxt", new String(tmpStr));
	    }
	    tempHash.put("documentName", new String(documentName));
	    tempHash.put("divisionCode", new String((String) param.get("divisionCodeStr")));
	    tempHash.put("isBuyerSummit", new String((String) param.get("isBuyerSummit")));
	    tempHash.put("firstRegistrationStage", new String((String) param.get("firstRegistrationStage")));

	    tmpStr = (String) param.get("buyerCodeStr");
	    tempHash.put("buyerCode", new String(tmpStr));
	    tmpStr = (String) param.get("dRCheckPoint");
	    tmpStr = StringUtil.checkNull(tmpStr);
	    tempHash.put("dRCheckPoint", new String(tmpStr));
	    tempHash.put("documentDescription", new String((String) param.get("documentDescription")));
	    tmpStr = StringUtil.checkNull(new String((String) param.get("security")));
	    tempHash.put("security", new String(tmpStr));
	    tempHash.put("devDeptCode", (String) param.get("devDeptCode"));
	    tempHash.put("isDeptACL", (String) param.get("isDeptACL"));
	    tempHash.put("duty", (String) param.get("duty"));

	    tempHash.put("pubDateYn", (String) param.get("pubDateYn"));
	    tempHash.put("pubDate", (String) param.get("pubDate"));
	    tempHash.put("pubCycle", (String) param.get("pubCycle"));

	    tempHash.put("costComment", (String) param.get("costComment"));
	    /*
	     * Start [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */
	    tempHash.put("webEditor", new String((String) param.get("webEditor")));
	    tempHash.put("webEditorText", new String((String) param.get("webEditorText")));
	    /*
	     * End [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */

	    // 부품정보는 Vector로 처리한다. String은 "0"이 입력되어 있음
	    Object partObj = param.get("partOid");
	    if (partObj instanceof Vector) {
		tempHash.put("partOid", new Vector((Vector) partObj));
	    } else {
		tempHash.put("partOid", new String((String) partObj));
	    }

	    // 품질표준문서 Start
	    /*
	     * if (param.get("quiltyNo") != null) { Object partNumberObj = param.get("quiltyNo"); if (partNumberObj instanceof Vector) {
	     * tempHash.put("partNumber", new Vector((Vector) partNumberObj)); } else { tempHash.put("partNumber", new String((String)
	     * partNumberObj)); } }
	     */

	    tmpStr = (String) param.get("relatedPart");

	    if (StringUtils.isNotEmpty(tmpStr)) {
		tempHash.put("relatedPart", new String(tmpStr));
	    }

	    // 품질표준문서 End

	    Object projObj = param.get("projectOid");
	    if (projObj instanceof Vector) {
		tempHash.put("projectOid", new Vector((Vector) projObj));
	    } else {
		tempHash.put("projectOid", new String((String) projObj));
	    }

	    // 최종입력을 위해 저장용 임시 Hashtable을 Helper.service에 넘긴다.
	    docu = KETDmsHelper.service.insertProjectDoc(tempHash);
	    if (docu != null) {
		// 첨부파일 정보는 문서저장후 attach시킨다./
		Vector files = uploader.getFiles();
		if (files != null) {
		    boolean isPrimary = false;
		    for (int i = 0; i < files.size(); i++) {
			WBFile file = (WBFile) files.elementAt(i);
			Kogger.debug(getClass(), "file1=====>" + files.elementAt(i).toString());
			Kogger.debug(getClass(), "file2=====>" + file.toString());

			isPrimary = false;
			System.out.println("by hooni ::: ProjectDocumentServlet ::: " + file.getName());
			if ("iFile0".equalsIgnoreCase(file.getFieldName())) {
			    isPrimary = true;
			}
			E3PSContentHelper.service.attach(docu, file, "", isPrimary);
		    }
		}

		returnTemp = CommonUtil.getOIDString(docu);
		returnTemp = "문서가 생성되었습니다.oid=" + returnTemp;
		Kogger.debug(getClass(), "Servlet Create DocumentOID=====>" + returnTemp);
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
     * 함수명 : update 설명 : 문서수정 작성자 : 김경종 작성일자 : 2010. 9 [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가 수정일자 : 2013. 6. 3 수정자 : 김종호
     */
    @SuppressWarnings("unchecked")
    private String update(HttpServletRequest req, HttpServletResponse res) {
	KETProjectDocument docu = null;
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

	    sfiles = (String) param.get("isFileDel");
	    Kogger.debug(getClass(), "delfiles=====>" + sfiles);
	    tempHash.put("isFileDel", new String(sfiles));

	    docuOid = (String) param.get("docuOid");
	    tmpStr = (String) param.get("categoryCode");
	    tempHash.put("categoryCode", new String(tmpStr));

	    tmpStr = (String) param.get("analysisCode");

	    if (StringUtils.isNotEmpty(tmpStr)) {
		tempHash.put("analysisCode", new String(tmpStr));
	    }
	    tmpStr = (String) param.get("analysisCodeTxt");

	    if (StringUtils.isNotEmpty(tmpStr)) {
		tempHash.put("analysisCodeTxt", new String(tmpStr));
	    }

	    tmpStr = (String) param.get("outputOid");
	    tempHash.put("outputOid", new String(tmpStr));
	    // Param Hashtable에 데이터를 저장용 임시 Hashtable에 저장한다.
	    tempHash.put("docuOid", new String(docuOid));
	    docu = (KETProjectDocument) CommonUtil.getObject(docuOid);
	    tempHash.put("isBuyerSummit", new String((String) param.get("isBuyerSummit")));

	    tmpStr = (String) param.get("documentName");
	    tempHash.put("documentName", new String(tmpStr));
	    tmpStr = (String) param.get("buyerCodeStr");
	    tempHash.put("buyerCode", new String(tmpStr));
	    tmpStr = (String) param.get("dRCheckPoint");
	    tmpStr = StringUtil.checkNull(tmpStr);
	    tempHash.put("dRCheckPoint", new String(tmpStr));
	    tempHash.put("documentDescription", new String((String) param.get("documentDescription")));
	    tmpStr = StringUtil.checkNull(new String((String) param.get("security")));
	    tempHash.put("security", new String(tmpStr));
	    tempHash.put("attribute1", new String((String) param.get("attribute1")));
	    tempHash.put("devDeptCode", (String) param.get("devDeptCode"));
	    tempHash.put("isDeptACL", (String) param.get("isDeptACL"));
	    tempHash.put("duty", (String) param.get("duty"));

	    tempHash.put("pubDateYn", (String) param.get("pubDateYn"));
	    tempHash.put("pubDate", (String) param.get("pubDate"));
	    tempHash.put("pubCycle", (String) param.get("pubCycle"));

	    tempHash.put("costComment", (String) param.get("costComment"));
	    /*
	     * Start [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */
	    tempHash.put("webEditor", new String((String) param.get("webEditor")));
	    tempHash.put("webEditorText", new String((String) param.get("webEditorText")));
	    /*
	     * End [PLM System 1차개선] 수정내용 : webEditor 관련 column 추가, 2013. 6. 3, 김종호
	     */

	    // 품질표준문서 Start
	    /*
	     * if (param.get("partNumber") != null) { Object partNumberObj = param.get("partNumber"); if (partNumberObj instanceof Vector) {
	     * tempHash.put("partNumber", new Vector((Vector) partNumberObj)); } else { tempHash.put("partNumber", new String((String)
	     * partNumberObj)); } }
	     */

	    // 품질표준문서 End

	    // 화면상의 수정된 부품정보는 Vector로 처리한다. String은 "0"이 입력되어 있음
	    Object partObj = param.get("partOid");
	    if (partObj instanceof Vector) {
		tempHash.put("partOid", new Vector((Vector) partObj));
	    } else {
		tempHash.put("partOid", new String((String) partObj));
	    }

	    Object projObj = param.get("projectOid");
	    if (projObj instanceof Vector) {
		tempHash.put("projectOid", new Vector((Vector) projObj));
	    } else {
		tempHash.put("projectOid", new String((String) projObj));
	    }
	    // 첨부파일정보는 추가된것과 삭제해야 할것을 구분하여 처리한다.
	    // 삭제정보를 따로 String형태로 받아 삭제해준다.

	    // 추가된 첨부파일정보가 있다면 주첨부파일변경 포함하여 attach시킨다.
	    Vector files = uploader.getFiles();

	    if (files == null) {
		tempHash.put("files", new String("0"));
	    } else {
		tempHash.put("files", new Vector(files));
	    }

	    // 최종수정을 위해 저장용 임시 Hashtable을 Helper.service에 넘긴다.
	    if (cmd.equals("revice")) {

		tmpStr = (String) param.get("relatedPart");

		if (StringUtils.isNotEmpty(tmpStr)) {
		    tempHash.put("relatedPart", new String(tmpStr));
		}

		docu = KETDmsHelper.service.reviceProjectDoc(tempHash);
		if (docu != null) {
		    returnTemp = CommonUtil.getOIDString(docu);
		    Kogger.debug(getClass(), "Servlet revice DocumentOID=====>" + returnTemp);
		    returnTemp = "개정되었습니다.oid=" + returnTemp;
		} else {
		    returnTemp = "f";
		}
	    } else {
		docu = KETDmsHelper.service.updateProjectDoc(tempHash);
		if (docu != null) {
		    returnTemp = CommonUtil.getOIDString(docu);
		    Kogger.debug(getClass(), "Servlet Updated DocumentOID=====>" + returnTemp);
		    returnTemp = "수정되었습니다.oid=" + returnTemp;
		} else {
		    returnTemp = "f";
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}

	return returnTemp;
    }

    private String delete(HttpServletRequest req, HttpServletResponse res) throws WTException {
	String returnTemp = null;
	String docuOid = null;
	KETProjectDocument docu = null;
	String docuNo = null;
	try {
	    WTPrincipalReference user = SessionHelper.manager.getPrincipalReference();
	} catch (WTException e2) {
	    Kogger.error(getClass(), e2);
	}

	try {
	    docuOid = req.getParameter("oid");
	    docu = (KETProjectDocument) CommonUtil.getObject(docuOid);
	    docuNo = docu.getDocumentNo();

	    returnTemp = KETDmsHelper.service.deleteProjectDoc(docuOid);
	    if (returnTemp != null) {
		Kogger.debug(getClass(), "Servlet Delete DocumentOID=====>" + returnTemp);
		returnTemp = docuNo + ": 삭제되었습니다.";
	    } else {
		returnTemp = "f";
	    }
	} catch (WTException wte) {
	    // wtKogger.error(getClass(), e);
	    // returnTemp = "f";

	    throw wte;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    returnTemp = "f";
	}

	return returnTemp;
    }

    /*
     * [PLM System 1차개선] 수정내용 : 개발산출문서 검색 서블릿 적용 수정일자 : 2013. 6. 14 수정자 : 김종호
     */
    private void searchGrid(HttpServletRequest req, HttpServletResponse res, boolean isPageNotData) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	TgStringBuffer strBuffer = new TgStringBuffer();

	String fileDoownloadUrl = null;
	String iconPath = "";

	try {

	    ProjectDocDao documentDao = new ProjectDocDao();

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    TgPagingControl pager = new TgPagingControl(isPageNotData, paramMap);

	    paramMap.put("locale", messageService.getLocale());

	    // categoryCode 초기화
	    paramMap.put("categoryCode", "");
	    if (paramMap.getStringArray("category3").length > 0) {
		paramMap.put("categoryCode", paramMap.getStringArray("category3"));
	    } else if (paramMap.getStringArray("category2").length > 0) {
		paramMap.put("categoryCode", paramMap.getStringArray("category2"));
	    }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("projectDocumentSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("projectDocumentSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("projectDocumentSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("projectDocumentSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    // 목록 결과
	    if (isPageNotData) {

		// pager.replaceSortCols("DocNo", "DOCUMENTNO");
		// pager.replaceSortCols("DocType", "CATEGORYNAME");
		// pager.replaceSortCols("DocName", "TITLE");
		// pager.replaceSortCols("Status", "STATESTATE");
		// pager.replaceSortCols("Ver", "VERSION");
		// pager.replaceSortCols("Creator", "CREATORNAME");

		// pager.replaceSortCols("CreateDate", "CREATEDATE");
		// pager.replaceSortCols("BuyerSummit", "BUYERSUMMIT");

		List<Map<String, Object>> list = documentDao.searchProjectDocumentList(conditionList, pager);
		final String DocumentNo = "DocumentNo";
		for (int i = 0; i < list.size(); i++) {

		    Map<String, Object> projectDoc = list.get(i);

		    // 주 첨부파일
		    if (projectDoc.get("holderOid") != null && projectDoc.get("appDataOid") != null) {
			// fileDoownloadUrl = "/plm/servlet/e3ps/DownLoadContentServlet?holderOid=" + projectDoc.get("holderOid") +
			// "&adOid=" + projectDoc.get("appDataOid");
			fileDoownloadUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + projectDoc.get("holderOid")
			        + "&cioids=" + projectDoc.get("appDataOid") + "&role=PRIMARY";
			iconPath = ContentUtil.getContentIconPath((String) projectDoc.get("extension"));
		    } else {
			fileDoownloadUrl = "";
			iconPath = "";
		    }

		    String state = State.toState(projectDoc.get("stateState").toString()).getDisplay(messageService.getLocale());

		    strBuffer.append("<I NoColor=\"2\" CanDelete=\"0\"");
		    strBuffer.append(" RowNum=\"" + pager.DEFAULT_ROW_NUM + "\"");
		    strBuffer
			    .append(" " + DocumentNo + "=\"")
			    .appendRepl((String) projectDoc.get("documentNo"))
			    .append("\"")
			    .append(" " + DocumentNo + "OnClick=\"location.href='/plm/jsp/dms/ViewDocument.jsp?oid="
			            + projectDoc.get("oid"))
			    .append("'\"" + " " + DocumentNo + "HtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor()
			            + "'>\"").append(" " + DocumentNo + "HtmlPostfix=\"</font>\" ");
		    strBuffer.append(" CategoryName=\"").appendRepl((String) projectDoc.get("categoryName")).append("\"");
		    strBuffer.append(" Title=\"").appendRepl((String) projectDoc.get("title")).append("\"");
		    strBuffer.append(" StateState=\"").appendRepl(state).append("\"");
		    strBuffer.append(" Version=\"").appendRepl((String) projectDoc.get("ver")).append("\"");
		    strBuffer.append(" CreatorName=\"").appendRepl((String) projectDoc.get("creatorName")).append("\"");
		    strBuffer.append(" CreateDate=\"").appendRepl((String) projectDoc.get("createDate")).append("\"");
		    strBuffer.append(" FileType=\"Img\"" + " FileIcon=\"" + iconPath + "\"" + " FileIconAlign=\"Center\""
			    + " FileOnClick=\"location.href='" + fileDoownloadUrl + "'\"");
		    strBuffer.append(" BuyerSummit=\"").appendRepl((String) projectDoc.get("buyerSummit")).append("\"");
		    strBuffer.append("/>");

		}

		req.setAttribute("currentPageNo", String.valueOf(pager.getCurrentPageNo()));
		req.setAttribute("tgData", strBuffer.toString());
		req.setAttribute("pageSize", String.valueOf(pager.getPageSize()));
		gotoResult(req, res, "/jsp/common/treegrid/SearchGridPage.jsp");

	    } else {

		int totalCount = documentDao.searchProjectDocumentListCount(conditionList);

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

    /*
     * [PLM System 1차개선] 수정내용 : Excel 다운 서블릿 적용 수정일자 : 2013. 7. 24 수정자 : 김종호
     */
    private void excelDown(HttpServletRequest req, HttpServletResponse res) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	Connection conn = null;

	try {
	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    ProjectDocDao documentDao = new ProjectDocDao();

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
	    }

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("projectDocumentSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("projectDocumentSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("projectDocumentSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("projectDocumentSearchConditionList", conditionList);
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
	    String sFileName = "SearchProjectDocumentList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    try {

		WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
		WritableSheet s1 = workbook.createSheet("문서검색목록", 0);

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		// 문서제목
		Label lr = new jxl.write.Label(0, 0, "개발산출문서 검색목록");
		s1.addCell(lr);

		int row = 2;

		lr = new jxl.write.Label(0, row, "사업부", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(1, row, "문서번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(2, row, "버전", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(3, row, "결재상태", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(4, row, "대분류", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(5, row, "중분류", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(6, row, "소분류", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(7, row, "문서명", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(8, row, "문서설명", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(9, row, "작성자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(10, row, "작성부서", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(11, row, "작성일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(12, row, "상신일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(13, row, "승인일자", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(14, row, "최초 등록시점", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(15, row, "고객 제출자료", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(16, row, "고객사", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(17, row, "DR CheckSheet 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(18, row, "DR평가점수", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(19, row, "승인결과", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(20, row, "최종승인 의견", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(21, row, "APQP 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(22, row, "PSO10 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(23, row, "ESIR 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(24, row, "ISIR(Car) 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(25, row, "ISIR(Elec) 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(26, row, "ANPQP 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(27, row, "SYMC 대상", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(28, row, "프로젝트 번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(29, row, "부품 번호", cellFormat);
		s1.addCell(lr);

		lr = new jxl.write.Label(30, row, "설계변경 번호", cellFormat);
		s1.addCell(lr);

		QueryResult r = null;
		KETDocumentCategory docCate = null;
		// String docCatePath = StringUtils.EMPTY;
		String categoryCode = StringUtils.EMPTY;
		// String docCatePath1 = StringUtils.EMPTY;
		// String docCatePath2 = StringUtils.EMPTY;
		// String docCatePath3 = StringUtils.EMPTY;
		String divisionCode = StringUtils.EMPTY;

		String activityName = StringUtils.EMPTY;
		String completeDate = StringUtils.EMPTY;
		String approvalDate = StringUtils.EMPTY;
		String requestDate = StringUtils.EMPTY;
		String stateState = StringUtils.EMPTY;

		Integer tmpInt = 0;

		// 목록 결과
		KETProjectDocument prodDocObj = null;
		List<Map<String, Object>> list = documentDao.searchProjectDocumentListByExcel(conditionList);
		for (Map<String, Object> projectDoc : list) {

		    prodDocObj = (KETProjectDocument) CommonUtil.getObject(projectDoc.get("oid").toString());

		    row++;

		    divisionCode = StringUtil.checkNull(prodDocObj.getDivisionCode());
		    if (divisionCode.equals("CA")) {
			divisionCode = "자동차";
		    } else if (divisionCode.equals("ER")) {
			divisionCode = "전자";
		    } else {
			divisionCode = "-";
		    }

		    // stateState = projectDoc.get("stateState").toString();

		    // 사업부
		    lr = new jxl.write.Label(0, row, divisionCode, cellFormat);
		    s1.addCell(lr);
		    // 문서번호
		    lr = new jxl.write.Label(1, row, projectDoc.get("documentNo").toString(), cellFormat);
		    s1.addCell(lr);
		    // 버전
		    lr = new jxl.write.Label(2, row, StringUtil.checkNull((String) projectDoc.get("ver")), cellFormat);
		    s1.addCell(lr);
		    // 결재상태
		    lr = new jxl.write.Label(3, row, StringUtil.checkNull((String) projectDoc.get("state")), cellFormat);
		    s1.addCell(lr);

		    String isDRCheckSheet = "비대상";
		    String isAPQP = "비대상";
		    String isPSO10 = "비대상";
		    String isESIR = "비대상";
		    String isISIRCar = "비대상";
		    String isISIRElec = "비대상";
		    String isANPQP = "비대상";
		    String isSYMC = "비대상";

		    try {
			docCate = null;
			r = PersistenceHelper.manager.navigate(prodDocObj, "documentCategory", KETDocumentCategoryLink.class);
			if (r.hasMoreElements()) {
			    docCate = (KETDocumentCategory) r.nextElement();
			    categoryCode = docCate.getCategoryCode();

			    if (docCate.getIsDRCheckSheet())
				isDRCheckSheet = "대상";
			    if (docCate.getIsAPQP())
				isAPQP = "대상";
			    if (docCate.getIsPSO10())
				isPSO10 = "대상";
			    if (docCate.getIsESIR())
				isESIR = "대상";
			    if (docCate.getIsISIRCar())
				isISIRCar = "대상";
			    if (docCate.getIsISIRElec())
				isISIRElec = "대상";
			    if (docCate.getIsANPQP())
				isANPQP = "대상";
			    if (docCate.getIsSYMC())
				isSYMC = "대상";

			    // docCatePath = KETDmsHelper.service.selectCategoryPath(categoryCode);
			    // docCatePath = docCatePath.substring(1);
			    //
			    // StringTokenizer st = new StringTokenizer(docCatePath, "/");
			    // if (st.hasMoreTokens()) {
			    // docCatePath1 = st.nextToken();
			    // }
			    // if (st.hasMoreTokens()) {
			    // docCatePath2 = st.nextToken();
			    // }
			    // if (st.hasMoreTokens()) {
			    // docCatePath3 = st.nextToken();
			    // }
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }

		    // 대분류
		    lr = new jxl.write.Label(4, row, StringUtil.checkNull((String) projectDoc.get("docCatePath1")), cellFormat);
		    s1.addCell(lr);
		    // 중분류
		    lr = new jxl.write.Label(5, row, StringUtil.checkNull((String) projectDoc.get("docCatePath2")), cellFormat);
		    s1.addCell(lr);
		    // 소분류
		    lr = new jxl.write.Label(6, row, StringUtil.checkNull((String) projectDoc.get("docCatePath3")), cellFormat);
		    s1.addCell(lr);
		    // 문서명
		    lr = new jxl.write.Label(7, row, StringUtil.checkNull((String) projectDoc.get("title")), cellFormat);
		    s1.addCell(lr);
		    // 문서설명
		    lr = new jxl.write.Label(8, row, (String) prodDocObj.getWebEditorText(), cellFormat);
		    s1.addCell(lr);
		    // 작성자
		    lr = new jxl.write.Label(9, row, StringUtil.checkNull((String) projectDoc.get("creatorName")), cellFormat);
		    s1.addCell(lr);
		    // 작성부서
		    lr = new jxl.write.Label(10, row, StringUtil.checkNull((String) projectDoc.get("deptName")), cellFormat);
		    s1.addCell(lr);
		    // 작성일자
		    lr = new jxl.write.Label(11, row, StringUtil.checkNull((String) projectDoc.get("createDate")), cellFormat);
		    s1.addCell(lr);

		    // =========결재 요청일,승인일 관련 시작====================//
		    // requestDate = StringUtils.EMPTY;
		    // approvalDate = StringUtils.EMPTY;
		    // if (!"REWORK".equals(stateState) && !"REJECTED".equals(stateState) && !"INWORK".equals(stateState)) {
		    Object obj = CommonUtil.getObject(projectDoc.get("oid").toString());
		    KETWfmApprovalHistory history1 = new KETWfmApprovalHistory();
		    KETWfmApprovalHistory history2 = new KETWfmApprovalHistory();
		    KETWfmApprovalMaster master = null;
		    Object temp = new Object();
		    Vector vec = null;
		    // WTObject targetObj = KETCommonHelper.service.getPBO((WTObject)
		    // CommonUtil.getObject(projectDoc.get("oid").toString()));
		    // if (targetObj != null)
		    // master = WorkflowSearchHelper.manager.getMaster(targetObj);
		    String appMastOid = (String) projectDoc.get("appMastOid");
		    if (StringUtils.isNotEmpty(appMastOid)) {
			master = (KETWfmApprovalMaster) CommonUtil.getObject(appMastOid);
		    }
		    if (master != null) {

			vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

			for (int k = 0; k < vec.size() - 1; k++) {
			    for (int j = k + 1; j < vec.size(); j++) {
				history1 = (KETWfmApprovalHistory) vec.get(k);
				history2 = (KETWfmApprovalHistory) vec.get(k);
				if (history1.getSeqNum() < history2.getSeqNum()) {
				    temp = vec.get(k);
				    vec.set(k, vec.get(j));
				    vec.set(j, temp);
				}
			    }
			}
		    }
		    // =========결재 요청일,승인일 관련 끝====================//
		    if (vec != null) {
			boolean isApprover = true;
			activityName = "";
			int iComplet = 0;
			for (int x = 0; x < vec.size(); x++) {
			    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x);
			    activityName = StringUtil.checkNull(history.getActivityName());
			    if (activityName.equals("검토")) {
				iComplet++;
			    }
			}

			for (int x = 0; x < vec.size(); x++) {
			    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(x);
			    activityName = StringUtil.checkNull(history.getActivityName());

			    if (activityName.equals("합의")) {
				iComplet++;
			    }

			    if (x == iComplet && isApprover && activityName.equals("검토")) {
				activityName = "승인";
				isApprover = false;
			    }
			    if (history.getCompletedDate() != null)
				completeDate = DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd");
			    if (activityName.equals("승인"))
				approvalDate = completeDate;
			    if (activityName.equals("요청"))
				requestDate = completeDate;

			}
		    }
		    // }

		    // 상신일자
		    lr = new jxl.write.Label(12, row, requestDate, cellFormat);
		    s1.addCell(lr);
		    // 승인일자
		    lr = new jxl.write.Label(13, row, approvalDate, cellFormat);
		    s1.addCell(lr);
		    // 최초 등록시점
		    tmpInt = prodDocObj.getFirstRegistrationStage();
		    String firstRegistrationStage = tmpInt.toString();

		    if (firstRegistrationStage.equals("1")) {
			firstRegistrationStage = "개발단계";
		    }
		    if (firstRegistrationStage.equals("2")) {
			firstRegistrationStage = "양산단계";
		    }

		    lr = new jxl.write.Label(14, row, StringUtil.checkNull(firstRegistrationStage), cellFormat);
		    s1.addCell(lr);

		    String buyerCode = "-";
		    if ("1".equals(StringUtil.checkNull(prodDocObj.getIsBuyerSummit()))) {
			buyerCode = StringUtil.checkNull(prodDocObj.getBuyerCode());

			if (!StringUtil.checkNull(buyerCode).equals("")) {
			    StringTokenizer st = new StringTokenizer(buyerCode, ",");
			    String ct = "";
			    String bName = "";
			    while (st.hasMoreTokens()) {
				ct = st.nextToken();
				NumberCode nCode1 = (NumberCode) CommonUtil.getObject(ct);

				if (nCode1 == null) {
				    bName = bName + "," + ct;
				} else {
				    bName = bName + "," + nCode1.getName();
				}
			    }
			    if (!bName.equals("")) {
				buyerCode = bName.substring(1);
			    }
			}

		    }

		    // 고객 제출자료
		    lr = new jxl.write.Label(15, row, StringUtil.checkNull(projectDoc.get("buyerSummit").toString()), cellFormat);
		    s1.addCell(lr);
		    // 고객사
		    lr = new jxl.write.Label(16, row, buyerCode, cellFormat);
		    s1.addCell(lr);
		    // DR CheckSheet 대상
		    lr = new jxl.write.Label(17, row, isDRCheckSheet, cellFormat);
		    s1.addCell(lr);
		    // DR평가점수
		    tmpInt = prodDocObj.getDRCheckPoint();
		    lr = new jxl.write.Label(18, row, tmpInt.toString(), cellFormat);
		    s1.addCell(lr);
		    // 승인결과
		    lr = new jxl.write.Label(19, row, StringUtil.checkNull(prodDocObj.getApprovalResult()), cellFormat);
		    s1.addCell(lr);
		    // 최종승인 의견
		    lr = new jxl.write.Label(20, row, StringUtil.checkNull(prodDocObj.getLastApprovalComment()), cellFormat);
		    s1.addCell(lr);

		    // APQP 대상
		    lr = new jxl.write.Label(21, row, isAPQP, cellFormat);
		    s1.addCell(lr);
		    // PSO10 대상
		    lr = new jxl.write.Label(22, row, isPSO10, cellFormat);
		    s1.addCell(lr);
		    // ESIR 대상
		    lr = new jxl.write.Label(23, row, isESIR, cellFormat);
		    s1.addCell(lr);
		    // ISIR(Car) 대상
		    lr = new jxl.write.Label(24, row, isISIRCar, cellFormat);
		    s1.addCell(lr);
		    // ISIR(Elec) 대상
		    lr = new jxl.write.Label(25, row, isISIRElec, cellFormat);
		    s1.addCell(lr);
		    // ANPQP 대상
		    lr = new jxl.write.Label(26, row, isANPQP, cellFormat);
		    s1.addCell(lr);
		    // SYMC 대상
		    lr = new jxl.write.Label(27, row, isSYMC, cellFormat);
		    s1.addCell(lr);

		    String pjtNo = "0";
		    try {
			E3PSProject project = null;
			ProjectOutput po;
			QueryResult r4 = PersistenceHelper.manager.navigate(prodDocObj, "output", KETDocumentOutputLink.class);
			if (r4.hasMoreElements()) {
			    po = (ProjectOutput) r4.nextElement();
			    E3PSTask task = (E3PSTask) po.getTask();

			    project = (E3PSProject) task.getProject();
			    pjtNo = StringUtil.checkNull(project.getPjtNo());
			}

			QueryResult r5 = PersistenceHelper.manager.navigate(prodDocObj, "project", KETDocumentProjectLink.class);
			while (r5.hasMoreElements()) {
			    project = (E3PSProject) r5.nextElement();
			    if (pjtNo.equals("0")) {
				pjtNo = StringUtil.checkNull(project.getPjtNo());
			    } else {
				pjtNo = pjtNo + "," + StringUtil.checkNull(project.getPjtNo());
			    }
			}
			if (pjtNo.equals("0")) {
			    pjtNo = "";
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		    // 프로젝트번호
		    lr = new jxl.write.Label(28, row, pjtNo, cellFormat);
		    s1.addCell(lr);

		    String partNo = "";
		    try {
			WTPart wtPart = null;
			QueryResult qr = PersistenceHelper.manager.navigate(prodDocObj, "part", KETDocumentPartLink.class);
			if (qr.hasMoreElements()) {
			    while (qr.hasMoreElements()) {
				wtPart = (WTPart) qr.nextElement();
				partNo = partNo + "," + wtPart.getNumber();
			    }
			    partNo = partNo.substring(1);
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		    // 부품 번호
		    lr = new jxl.write.Label(29, row, partNo, cellFormat);
		    s1.addCell(lr);

		    String ecoId = "";
		    try {
			KETProdChangeActivity pca;
			QueryResult qr = PersistenceHelper.manager.navigate(prodDocObj, "prodECA", KETProdECADocLink.class);
			while (qr.hasMoreElements()) {
			    pca = (KETProdChangeActivity) qr.nextElement();
			    QueryResult qr1 = PersistenceHelper.manager.navigate(pca, "prodECO", KETProdChangeActivityLink.class);
			    if (qr1.hasMoreElements()) {
				KETProdChangeOrder ecoObj = (KETProdChangeOrder) qr1.nextElement();
				ecoId = ecoId + "," + ecoObj.getEcoId();
			    }
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }

		    try {
			KETMoldChangeActivity mca;
			QueryResult qr = PersistenceHelper.manager.navigate(prodDocObj, "moldECA", KETMoldECADocLink.class);
			while (qr.hasMoreElements()) {
			    mca = (KETMoldChangeActivity) qr.nextElement();
			    QueryResult qr1 = PersistenceHelper.manager.navigate(mca, "moldECO", KETMoldChangeActivityLink.class);
			    if (qr1.hasMoreElements()) {
				KETMoldChangeOrder ecoObj = (KETMoldChangeOrder) qr1.nextElement();
				// ecoId = ecoId + "," + CommonUtil.getOIDString(ecoObj);
				ecoId = ecoId + "," + ecoObj.getEcoId();
			    }
			}
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		    // 설계변경 번호
		    lr = new jxl.write.Label(30, row, ecoId, cellFormat);
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
	    // 커넥션 종료
	    PlmDBUtil.close(conn);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 일괄결재요청 검색 서블릿 적용 수정일자 : 2013. 6. 14 수정자 : 김종호
     */
    private void searchMultiApp(HttpServletRequest req, HttpServletResponse res) {

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	QueryResult result = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("docMultiApprovalSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("docMultiApprovalSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("docMultiApprovalSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("docMultiApprovalSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    QuerySpec qs = new QuerySpec();
	    Class<KETWfmMultiApprovalRequest> targetClass = KETWfmMultiApprovalRequest.class;

	    int reqMultiIdx = qs.appendClassList(targetClass, true);
	    SearchCondition sc1 = new SearchCondition(KETWfmMultiApprovalRequest.class, KETWfmMultiApprovalRequest.ATTRIBUTE2, "=", "DOC");
	    qs.appendWhere(sc1, new int[] { reqMultiIdx });

	    for (Map<String, Object> condistion : conditionList) {
		String reqName = (String) condistion.get("reqName");
		String creator = (String) condistion.get("creator");
		String lcState = (String) condistion.get("lcState");
		String predate = (String) condistion.get("predate");
		String postdate = (String) condistion.get("postdate");

		if (!reqName.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    KETQueryUtil.setQuerySpecForMultiSearch(qs, targetClass, reqMultiIdx, "reqName", reqName, true);
		}
		if (!creator.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    qs.appendOpenParen();
		    StringTokenizer creatorToken = new StringTokenizer(creator, ", ");
		    while (creatorToken.hasMoreTokens()) {
			qs.appendWhere(
			        new SearchCondition(targetClass, "owner.key.id", SearchCondition.EQUAL, CommonUtil
			                .getOIDLongValue(creatorToken.nextToken())), new int[] { reqMultiIdx });
			if (creatorToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		if (lcState != null && lcState.trim().length() > 0 && !lcState.equalsIgnoreCase("null")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    qs.appendOpenParen();
		    StringTokenizer stateTypeToken = new StringTokenizer(lcState, ",");
		    while (stateTypeToken.hasMoreTokens()) {
			qs.appendWhere(new SearchCondition(targetClass, "state.state", SearchCondition.EQUAL, stateTypeToken.nextToken()),
			        new int[] { reqMultiIdx });
			if (stateTypeToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		if (!predate.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(
			    new SearchCondition(targetClass, "requestDate", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil
			            .convertStartDate2(predate)), new int[] { reqMultiIdx });
		}
		if (!postdate.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(
			    new SearchCondition(targetClass, "requestDate", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil
			            .convertEndDate2(postdate)), new int[] { reqMultiIdx });
		}
	    }

	    result = PersistenceHelper.manager.find(qs);

	    if (result.size() > 0) {

		while (result.hasMoreElements()) {
		    Object[] obj = (Object[]) result.nextElement();
		    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) obj[0];
		    String reqDate = DateUtil.getDateString(multiReq.getRequestDate(), "d");
		    if (reqDate.equals("") || reqDate == null)
			reqDate = "&nbsp;";

		    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		    strBuffer.append(" ReqName=&quot;" + multiReq.getReqName() + "&quot;"
			    + " ReqNameOnClick=&quot;javascript:gotoView(&apos;" + multiReq.toString() + "&apos;);&quot;"
			    + " ReqNameHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor()
			    + "&apos;&gt;&quot;" + " ReqNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		    strBuffer.append(" UserName=&quot;" + multiReq.getOwner().getFullName() + "&quot;");
		    strBuffer.append(" ReqDate=&quot;" + reqDate + "&quot;");
		    strBuffer.append(" AppStatus=&quot;" + multiReq.getLifeCycleState().getDisplay() + "&quot;");
		    strBuffer.append("/>");
		}
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/dms/SearchDocMultiApproval.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 일괄결재요청 검색 서블릿 적용 수정일자 : 2013. 6. 14 수정자 : 김종호
     */
    private void excelDownMultiApp(HttpServletRequest req, HttpServletResponse res) {

	QueryResult result = null;

	try {
	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("docMultiApprovalSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("docMultiApprovalSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("docMultiApprovalSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("docMultiApprovalSearchConditionList", conditionList);
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
	    String sFileName = "SearchMultiApprovalList_" + ff.format(new Date()) + ".xls";

	    // 파일명 한글 깨짐 방지
	    if (ie) {
		sFileName = URLEncoder.encode(sFileName, "utf-8");
	    } else {
		sFileName = new String(sFileName.getBytes("utf-8"), "iso-8859-1");
	    }

	    // Excel File Object
	    File excelFileObj = new File(sFilePath + "/" + sFileName);

	    QuerySpec qs = new QuerySpec();
	    Class<KETWfmMultiApprovalRequest> targetClass = KETWfmMultiApprovalRequest.class;

	    int reqMultiIdx = qs.appendClassList(targetClass, true);
	    SearchCondition sc1 = new SearchCondition(KETWfmMultiApprovalRequest.class, KETWfmMultiApprovalRequest.ATTRIBUTE2, "=", "DOC");
	    qs.appendWhere(sc1, new int[] { reqMultiIdx });

	    for (Map<String, Object> condistion : conditionList) {
		String reqName = (String) condistion.get("reqName");
		String creator = (String) condistion.get("creator");
		String lcState = (String) condistion.get("lcState");
		String predate = (String) condistion.get("predate");
		String postdate = (String) condistion.get("postdate");

		if (!reqName.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    KETQueryUtil.setQuerySpecForMultiSearch(qs, targetClass, reqMultiIdx, "reqName", reqName, true);
		}
		if (!creator.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    qs.appendOpenParen();
		    StringTokenizer creatorToken = new StringTokenizer(creator, ", ");
		    while (creatorToken.hasMoreTokens()) {
			qs.appendWhere(
			        new SearchCondition(targetClass, "owner.key.id", SearchCondition.EQUAL, CommonUtil
			                .getOIDLongValue(creatorToken.nextToken())), new int[] { reqMultiIdx });
			if (creatorToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		if (lcState != null && lcState.trim().length() > 0 && !lcState.equalsIgnoreCase("null")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();

		    qs.appendOpenParen();
		    StringTokenizer stateTypeToken = new StringTokenizer(lcState, ",");
		    while (stateTypeToken.hasMoreTokens()) {
			qs.appendWhere(new SearchCondition(targetClass, "state.state", SearchCondition.EQUAL, stateTypeToken.nextToken()),
			        new int[] { reqMultiIdx });
			if (stateTypeToken.hasMoreTokens())
			    qs.appendOr();
		    }
		    qs.appendCloseParen();
		}

		if (!predate.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(
			    new SearchCondition(targetClass, "requestDate", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil
			            .convertStartDate2(predate)), new int[] { reqMultiIdx });
		}
		if (!postdate.equals("")) {
		    if (qs.getConditionCount() > 0)
			qs.appendAnd();
		    qs.appendWhere(
			    new SearchCondition(targetClass, "requestDate", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil
			            .convertEndDate2(postdate)), new int[] { reqMultiIdx });
		}
	    }
	    qs.appendOrderBy(new OrderBy(new ClassAttribute(targetClass, "thePersistInfo.createStamp"), true), new int[] { reqMultiIdx });

	    result = PersistenceHelper.manager.find(qs);

	    try {

		WritableWorkbook workbook = Workbook.createWorkbook(excelFileObj);
		WritableSheet s1 = workbook.createSheet("일괄결재요청리스트", 0);

		s1.setName("일괄결재요청리스트");

		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 셀의 스타일을 지정

		// 문서제목
		Label lr = new jxl.write.Label(0, 0, "일괄결재요청리스트");
		s1.addCell(lr);

		int row = 2;

		// 타이틀
		lr = new Label(0, row, "번호");
		s1.addCell(lr);

		lr = new Label(1, row, "요청제목");
		s1.addCell(lr);

		lr = new Label(2, row, "요청자");
		s1.addCell(lr);

		lr = new Label(3, row, "요청일자");
		s1.addCell(lr);

		lr = new Label(4, row, "결재상태");
		s1.addCell(lr);

		if (result.size() > 0) {

		    int cnt = result.size();
		    while (result.hasMoreElements()) {
			row++;
			cnt--;

			lr = new Label(0, row, Integer.toString(cnt));
			s1.addCell(lr);

			Object[] obj = (Object[]) result.nextElement();
			KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) obj[0];
			String reqDate = DateUtil.getDateString(multiReq.getRequestDate(), "d");
			if (reqDate.equals("") || reqDate == null)
			    reqDate = "&nbsp;";

			lr = new Label(1, row, multiReq.getReqName());
			s1.addCell(lr);

			lr = new Label(2, row, multiReq.getOwner().getFullName());
			s1.addCell(lr);

			lr = new Label(3, row, reqDate);
			s1.addCell(lr);

			lr = new Label(4, row, multiReq.getLifeCycleState().getDisplay());
			s1.addCell(lr);
		    }
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
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    /*
     * [PLM System 1차개선] 수정내용 : 개발산출문서 검색 서블릿 적용 수정일자 : 2013. 6. 14 수정자 : 김종호
     */
    private void searchDocumentPop(HttpServletRequest req, HttpServletResponse res) {

	// 다국어 처리
	KETMessageService messageService = KETMessageService.getMessageService(req);

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	KETProjectDocument document = null;
	try {
	    // 커넥션 생성
	    DocumentDao documentDao = new DocumentDao();

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    paramMap.put("locale", messageService.getLocale());

	    // 사업부 구분 때문에 TO-DO 에서 ECN 검색시 조회가 안되는 제약이 있어 주석처리한다 2021.07.29

	    // if (CommonUtil.isMember("전자사업부")) {
	    // paramMap.put("divisionCode", "ER");
	    // } else if (CommonUtil.isMember("자동차사업부")) {
	    // paramMap.put("divisionCode", "CA");
	    // }

	    paramMap.put("isBuyerSummit", "0");
	    paramMap.put("islastversion", "LATEST");

	    // Search Query 사용하기 때문에 변경
	    List<Map<String, Object>> conditionList = new ArrayList<Map<String, Object>>();
	    conditionList.add(paramMap);

	    // 목록 결과
	    List<Map<String, Object>> list = documentDao.searchProjectDocumentList(conditionList);
	    for (Map<String, Object> projectDoc : list) {

		document = (KETProjectDocument) CommonUtil.getObject(projectDoc.get("oid").toString());

		String pjtNo = "0";
		E3PSProject project = null;
		ProjectOutput po;
		QueryResult r3 = PersistenceHelper.manager.navigate(document, "output", KETDocumentOutputLink.class);
		if (r3.hasMoreElements()) {
		    po = (ProjectOutput) r3.nextElement();
		    E3PSTask task = (E3PSTask) po.getTask();
		    project = (E3PSProject) task.getProject();
		    pjtNo = StringUtil.checkNull(project.getPjtNo());
		}
		QueryResult qr = PersistenceHelper.manager.navigate(document, "project", KETDocumentProjectLink.class);
		if (qr.hasMoreElements()) {
		    project = (E3PSProject) qr.nextElement();
		    if (pjtNo.equals("0")) {
			pjtNo = StringUtil.checkNull(project.getPjtNo());
		    } else {
			pjtNo = pjtNo + "...";
		    }
		}
		if (pjtNo.equals("0")) {
		    pjtNo = "";
		}

		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" DocNo=&quot;" + document.getDocumentNo() + "&quot;");
		strBuffer.append(" DocName=&quot;" + TreeGridUtil.replaceContentForI(document.getTitle().toString()) + "&quot;");
		strBuffer.append(" DocVer=&quot;" + document.getVersionIdentifier().getValue() + "&quot;");
		strBuffer.append(" Status=&quot;" + document.getLifeCycleState().getDisplay() + "&quot;");
		strBuffer.append(" IDocuOid=&quot;" + CommonUtil.getOIDString(document) + "&quot;");
		strBuffer.append(" DeptName=&quot;" + document.getDeptName() + "&quot;");
		strBuffer.append(" CreatorName=&quot;" + document.getCreatorFullName() + "&quot;");
		strBuffer.append(" PjtNo=&quot;" + pjtNo + "&quot;");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    gotoResult(req, res, "/jsp/dms/SearchDocumentPop.jsp");
	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	}
    }
}
