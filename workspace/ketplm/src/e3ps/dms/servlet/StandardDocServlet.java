/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : StandardDocServlet.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 12. 16.
 */
package e3ps.dms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import wt.fc.PersistenceHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.dms.dao.StandardDocDao;
import e3ps.dms.entity.KETStandardTemplate;
import e3ps.dms.service.KETDmsHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : StandardDocServlet 설명 : 표준양식 서블릿 작성자 : 김경희 작성일자 : 2010. 12. 16.
 */
public class StandardDocServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String cmd = ParamUtil.getParameter(req, "cmd");

	if (cmd.equals("create")) {
	    // 표준양식 생성
	    create(req, res);
	} else if (cmd.equals("update")) {
	    // 표준양식 수정
	    update(req, res);
	} else if (cmd.equals("delete")) {
	    // 표준양식 삭제
	    delete(req, res);
	} else if (cmd.equals("search")) {
	    // 표준양식 검색
	    search(req, res);
	} else if (cmd.equals("revision")) {
	    revision(req, res);
	}
    }

    /**
     * 함수명 : create 설명 : 표준양식 등록
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 12. 16.
     */
    private void create(HttpServletRequest req, HttpServletResponse res) {
	try {

	    Kogger.debug(getClass(), "********************" + "서블릿 저장 시작입니다");

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = "저장되었습니다.";

	    // 표준양식 저장 실행
	    KETStandardTemplate ketStandardTemplate = KETDmsHelper.service.createStandardTemplate(hash, files);

	    if (ketStandardTemplate != null) {
		// 저장 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/dms/ViewStandardDoc.jsp?oid=" + CommonUtil.getOIDString(ketStandardTemplate));
	    } else {
		alert(res, "저장 실패!");
	    }

	    Kogger.debug(getClass(), "********************" + "서블릿 저장 끝입니다");
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : update 설명 : 표준양식 수정
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 12. 16.
     */
    private void update(HttpServletRequest req, HttpServletResponse res) {
	try {

	    Kogger.debug(getClass(), "********************" + "서블릿 수정 시작입니다");

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = "수정되었습니다.";

	    // 표준양식 수정 실행
	    KETStandardTemplate ketStandardTemplate = KETDmsHelper.service.updateStandardTemplate(hash, files);

	    if (ketStandardTemplate != null) {
		// 수정 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/dms/ViewStandardDoc.jsp?oid=" + CommonUtil.getOIDString(ketStandardTemplate));
	    } else {
		alert(res, "수정 실패!");
	    }

	    Kogger.debug(getClass(), "********************" + "서블릿 수정 끝입니다");
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : delete 설명 : 표준양식 삭제
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 12. 16.
     */
    private void delete(HttpServletRequest req, HttpServletResponse res) {
	try {

	    Kogger.debug(getClass(), "********************" + "서블릿 삭제 시작입니다");

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = "삭제되었습니다.";

	    // 표준양식 삭제 실행
	    boolean flag = KETDmsHelper.service.deleteStandardTemplate(hash, files);

	    if (flag) {
		// 삭제 성공 후 화면 이동
		alertNgo(res, msg, "/plm/servlet/e3ps/StandardDocServlet?cmd=search");
	    } else {
		alert(res, "삭제 실패!");
	    }

	    Kogger.debug(getClass(), "********************" + "서블릿 삭제 끝입니다");
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}

    }

    /**
     * 함수명 : search 설명 : 표준양식 검색
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 12. 16.
     */
    private void search(HttpServletRequest req, HttpServletResponse res) {

	StringBuffer strBuffer = new StringBuffer();
	int rowCount = 1;

	// 커넥션
	Connection conn = null;

	try {
	    // 커넥션 생성
	    conn = PlmDBUtil.getConnection();
	    StandardDocDao standardDocDao = new StandardDocDao(conn);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	    // [Start] 결과내 검색 조건 처리 //
	    HttpSession session = req.getSession();
	    List<Map<String, Object>> conditionList = null;

	    if (!paramMap.getString("searchInSearch").equalsIgnoreCase("searchInSearch")) {

		conditionList = new ArrayList<Map<String, Object>>();
		session.setAttribute("standardDocSearchConditionList", conditionList);
	    } else {

		if (session.getAttribute("standardDocSearchConditionList") != null) {

		    conditionList = (ArrayList<Map<String, Object>>) session.getAttribute("standardDocSearchConditionList");
		} else {

		    conditionList = new ArrayList<Map<String, Object>>();
		}
	    }

	    conditionList.add(paramMap);
	    session.setAttribute("standardDocSearchConditionList", conditionList);
	    // [End] 결과내 검색 조건 처리 //

	    String division = null;
	    // 목록 결과
	    List<Map<String, Object>> list = standardDocDao.ViewStandardDocList(conditionList);
	    for (Map<String, Object> standarDoc : list) {

		division = (String) standarDoc.get("divisionCode");
		if (division == null || division == "") {
		    division = "공통";
		}
		strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
		strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
		strBuffer.append(" Division=&quot;" + division + "&quot;");
		strBuffer.append(" Dept=&quot;" + standarDoc.get("deptCode") + "&quot;");
		strBuffer.append(" DocName=&quot;" + standarDoc.get("docName") + "&quot;"
		        + " DocNameOnClick=&quot;javascript:searchPopup(&apos;/plm/jsp/dms/ViewStandardDoc.jsp?oid="
		        + standarDoc.get("oid") + "&apos;);&quot;" + " DocNameHtmlPrefix=&quot;&lt;font color=&apos;"
		        + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;" + " DocNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" DocDesc=&quot;" + standarDoc.get("docDesc") + "&quot;");
		strBuffer.append(" DocOid=&quot;" + standarDoc.get("oid") + "&quot;");
		strBuffer.append(" DocRev=&quot;" + standarDoc.get("rev") + "&quot;"
		        + " DocRevOnClick=&quot;javascript:historySearch(&apos;" + standarDoc.get("oid") + "&apos;);&quot;"
		        + " DocRevHtmlPrefix=&quot;&lt;font color=&apos;" + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;"
		        + " DocRevHtmlPostfix=&quot;&lt;/font&gt;&quot;");
		strBuffer.append(" DocLastDate=&quot;" + standarDoc.get("lastdate") + "&quot;");
		strBuffer.append("/>");
	    }

	    req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
	    req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
	    req.setAttribute("popup", req.getParameter("popup"));
	    req.setAttribute("invokeMethod", req.getParameter("invokeMethod"));
	    gotoResult(req, res, "/jsp/dms/SearchStandardDoc.jsp");

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

    private void revision(HttpServletRequest req, HttpServletResponse res) {
	// TODO Auto-generated method stub

	String oid = ParamUtil.getParameter(req, "oid");
	KETStandardTemplate ketStandardTemplate = (KETStandardTemplate) CommonUtil.getObject(oid);
	try {
	    Versioned obj = VersionControlHelper.service.newVersion(ketStandardTemplate);
	    ketStandardTemplate = (KETStandardTemplate) PersistenceHelper.manager.save(obj);
	    String msg = "개정되었습니다";
	    if (ketStandardTemplate != null) {
		// 수정 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/dms/UpdateStandardDoc.jsp?type=refresh&oid=" + CommonUtil.getOIDString(ketStandardTemplate));

	    } else {
		alert(res, "수정 실패!");
	    }

	} catch (VersionControlException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (WTPropertyVetoException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (WTException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    Kogger.error(getClass(), e);
	}
    }
}
