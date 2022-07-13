/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : EarlyWarningResultServlet.java
 * 작성자 : 김경희
 * 작성일자 : 2010. 10. 12.
 */
package e3ps.ews.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.util.WTException;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.ews.beans.EWSHelper;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.ews.service.KETEwsHelper;
import e3ps.sap.ProblemCodeInterface;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : EarlyWarningResultServlet 설명 : 초기유동관리 실적보고서 서블릿 작성자 : 김경희 작성일자 : 2010. 10. 12.
 */
public class EarlyWarningResultServlet extends CommonServlet {

    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	String cmd = ParamUtil.getParameter(req, "cmd");

	if (cmd.equals("create")) {
	    // 실적보고서 생성
	    create(req, res);
	} else if (cmd.equals("update")) {
	    // 실적보고서 수정
	    update(req, res);
	} else if (cmd.equals("delete")) {
	    // 실적보고서 삭제
	    delete(req, res);
	} else if (cmd.equals("interface")) {
	    // 불량유형 인터페이스
	    problemCodeInterface(req, res);
	} else if (cmd.equals("excel")) {
	    // 실적 Excel
	    excel(req, res);
	}
    }

    /**
     * 함수명 : create 설명 : 실적보고서 등록
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 10. 12.
     */
    private void create(HttpServletRequest req, HttpServletResponse res) {

	try {

	    Kogger.debug(getClass(), "********************" + "서블릿 저장 시작입니다");

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String isTodo = (String) hash.get("isTodo");
	    String msg = messageService.getString("e3ps.message.ket_message", "02460");// 저장되었습니다.

	    // 실적보고서 저장 실행
	    KETEarlyWarningResult ketEarlyWarningResult = KETEwsHelper.service.createEarlyWarningResult(hash, files);

	    if (ketEarlyWarningResult != null) {
		// 저장 성공 후 화면 이동
		if (isTodo != null && isTodo.equals("Y")) {
		    alertNgo(res, msg,
			    "/plm/jsp/ews/ViewEarlyWarningResult.jsp?resultOid=" + CommonUtil.getOIDString(ketEarlyWarningResult));
		} else {
		    alertNgo(res, msg,
			    "/plm/jsp/ews/ViewEarlyWarningResult.jsp?resultOid=" + CommonUtil.getOIDString(ketEarlyWarningResult));
		}
	    } else {
		alert(res, "저장 실패!");
	    }

	    Kogger.debug(getClass(), "********************" + "서블릿 저장 끝입니다");
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 함수명 : update 설명 : 실적보고서 수정
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 10. 12.
     */
    private void update(HttpServletRequest req, HttpServletResponse res) {

	try {

	    Kogger.debug(getClass(), "********************" + "서블릿 수정 시작입니다");

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = messageService.getString("e3ps.message.ket_message", "01947");// 수정되었습니다.

	    // 실적보고서 수정 실행
	    KETEarlyWarningResult ketEarlyWarningResult = KETEwsHelper.service.updateEarlyWarningResult(hash, files);

	    if (ketEarlyWarningResult != null) {
		// 수정 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/ews/ViewEarlyWarningResult.jsp?resultOid=" + CommonUtil.getOIDString(ketEarlyWarningResult));
	    } else {
		alert(res, "수정 실패!");
	    }

	    Kogger.debug(getClass(), "********************" + "서블릿 수정 끝입니다");
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 함수명 : delete 설명 : 실적보고서 삭제
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 10. 12.
     */
    private void delete(HttpServletRequest req, HttpServletResponse res) {

	try {

	    Kogger.debug(getClass(), "********************" + "서블릿 삭제 시작입니다");

	    KETMessageService messageService = KETMessageService.getMessageService(req);

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());

	    Hashtable hash = uploader.getFormParameters();
	    Vector files = uploader.getFiles();

	    String msg = messageService.getString("e3ps.message.ket_message", "01699");// 삭제되었습니다.

	    // 실적보고서 삭제 실행
	    boolean flag = KETEwsHelper.service.deleteEarlyWarningResult(hash, files);

	    if (flag) {
		// 삭제 성공 후 화면 이동
		alertNgo(res, msg, "/plm/jsp/ews/ViewEarlyWarning.jsp?oid=" + (String) hash.get("oid"));
	    } else {
		alert(res, "삭제 실패!");
	    }

	    Kogger.debug(getClass(), "********************" + "서블릿 삭제 끝입니다");
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 함수명 : problemCodeInterface 설명 : 불량유형코드 인터페이스
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 11. 26.
     */
    private void problemCodeInterface(HttpServletRequest req, HttpServletResponse res) {

	ProblemCodeInterface codeif = new ProblemCodeInterface();
	ArrayList codeList = codeif.getProblemCode();
	String oid = null;
	String parentOid = null;
	String msg = null;
	boolean flag = false;

	HashMap<String, String> codeInfo = null;

	try {
	    for (int inx = 0; inx < codeList.size(); inx++) {
		codeInfo = (HashMap) codeList.get(inx);

		if (codeInfo.get("group") == null || codeInfo.get("group").equals("")) {
		    codeInfo.put("type", "PROBLEMTYPE");
		    codeInfo.put("checkSapSubmit", "false");

		    oid = NumberCodeHelper.manager.getNumberCodeOid("PROBLEMTYPE", codeInfo.get("code"));
		    codeInfo.put("oid", oid);

		    msg = NumberCodeHelper.saveNumberCode(codeInfo);
		    if (!msg.equals("저장  되었습니다.")) {
			flag = true;
		    }
		}
	    }

	    for (int inx = 0; inx < codeList.size(); inx++) {
		codeInfo = (HashMap) codeList.get(inx);

		if (codeInfo.get("group") != null && !codeInfo.get("group").equals("")) {
		    codeInfo.put("type", "PROBLEMTYPE");
		    codeInfo.put("checkSapSubmit", "false");

		    oid = NumberCodeHelper.manager.getNumberCodeOid("PROBLEMTYPE", codeInfo.get("code"));
		    codeInfo.put("oid", oid);

		    parentOid = NumberCodeHelper.manager.getNumberCodeOid("PROBLEMTYPE", codeInfo.get("group"));
		    codeInfo.put("parentOid", parentOid);

		    msg = NumberCodeHelper.saveNumberCode(codeInfo);
		    if (!msg.equals("저장  되었습니다.")) {
			flag = true;
		    }
		}
	    }

	    if (!flag) {
		alertNgo(res, "인터페이스 성공!", "/plm/jsp/ews/interfaceTest.jsp");
	    } else {
		alert(res, "인터페이스 실패!");
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    /**
     * 함수명 : excel 설명 : 실적 엑셀
     * 
     * @param HttpServletRequest
     *            req , HttpServletResponse res 작성자 : 김경희 작성일자 : 2010. 12. 08.
     */
    private void excel(HttpServletRequest req, HttpServletResponse res) {

	Kogger.debug(getClass(), "----------------------서블릿시작");

	Hashtable<String, String> param = null;
	Hashtable<String, String> part = new Hashtable<String, String>();
	ArrayList<Hashtable<String, String>> resultList = null;
	ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();

	try {

	    // Form 데이터 받기
	    FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	    Hashtable hash = uploader.getFormParameters();

	    Kogger.debug(getClass(), "----------------------oid : " + (String) hash.get("oid"));
	    KETEarlyWarning ketEarlyWarning = (KETEarlyWarning) CommonUtil.getObject((String) hash.get("oid"));
	    ArrayList partList = EWSHelper.manager.getPartTarget(ketEarlyWarning);
	    Kogger.debug(getClass(), "----------------------partList.size()  : " + partList.size());

	    for (int inx = 0; inx < partList.size(); inx++) {
		part = (Hashtable) partList.get(inx);

		param = new Hashtable<String, String>();
		param.put("startDate", DateUtil.getTimeFormat(ketEarlyWarning.getStartDate(), "yyyyMM"));
		param.put("endDate", DateUtil.getTimeFormat(ketEarlyWarning.getEndDate(), "yyyyMM"));
		param.put("partNo", StringUtil.checkNull((String) part.get("partNo")));
		Kogger.debug(getClass(), "----------------------partNo  : " + (String) part.get("partNo"));
		param.put("partName", StringUtil.checkNull((String) part.get("partName")));
		param.put("inout", StringUtil.checkNull(ketEarlyWarning.getInOut()));

		resultList = new ArrayList<Hashtable<String, String>>();
		resultList = EWSHelper.manager.getPartResult(param);

		for (int jnx = 0; jnx < resultList.size(); jnx++) {
		    list.add(resultList.get(jnx));
		}
	    }

	    // 검색조건 셋팅
	    req.setAttribute("condition", hash);
	    // 목록 결과 셋팅
	    req.setAttribute("list", list);

	    // 검색화면으로 이동
	    gotoResult(req, res, "/jsp/ews/EarlyWarningResultExcel.jsp");

	    Kogger.debug(getClass(), "----------------------서블릿종료");

	} catch (ServletException e) {
	    Kogger.error(getClass(), e);
	} catch (IOException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
