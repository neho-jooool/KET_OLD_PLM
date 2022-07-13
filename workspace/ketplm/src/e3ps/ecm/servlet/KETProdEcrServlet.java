/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : KETProdEcrServlet.java
 * 작성자 : 오승연
 * 작성일자 : 2010. 10. 14.
 */
package e3ps.ecm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.StringUtils;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.State;
import wt.util.WTException;
import wt.util.WTRuntimeException;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.ecm.beans.ProdEcrBeans;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETMeetingMinutes;
import e3ps.ecm.entity.KETProdECRIssueLink;
import e3ps.ecm.service.KETEcmHelper;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : KETProdEcrServlet 설명 : 제품 ECR Servlet 작성자 : 오승연 작성일자 : 2010. 10. 14.
 */
public class KETProdEcrServlet extends CommonServlet {

    private static final PrintWriter rtn_msg = null;

    /*
     * (non-Javadoc)
     * 
     * @see e3ps.common.web.CommonServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	boolean isSuccess = false;

	RequestDispatcher rd = null;

	String ALERT_MESSAGE = "";
	String ERROR_MESSAGE = "";

	String command = "";
	String tabName = "";
	Vector attachFiles = null;
	wt.change2.WTChangeRequest2 prodEcr = null;

	Hashtable<String, String> reqData = new Hashtable<String, String>();
	String[] partList = null;
	ArrayList<Hashtable<String, String>> delPartLinkList = null;
	String[] reqCommentList = null;
	String[] issueList = null;

	// 관련품질문제
	String[] dqmList = null;

	ArrayList<KETProdECRIssueLink> delIssueList = null;

	Hashtable partHash = new Hashtable();
	Hashtable<String, Object> issueHash = new Hashtable<String, Object>();

	Hashtable ecrData = null;

	boolean isDelete = false;

	String ecrOid = "";

	String contentType = req.getContentType();

	// Form 데이터 받기
	FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
	KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

	if (contentType != null && contentType.indexOf("multipart/form-data") >= 0) {
	    command = paramMap.getString("cmd");

	    /*
	     * tabName => ecrBasic : 기본사항 ecrCompetent : 주관부서 ecrMeeting : 회의록
	     */
	    tabName = StringUtils.stripToEmpty(paramMap.getString("tabName"));

	    // 제품 ECR 등록 화면에서 온 경우(ECR 기본사항이 단독으로 넘어온다.
	    if (command.equals("Create")) {

		attachFiles = uploader.getFiles();

		try {

		    // ecrOid = KETEcmHelper.service.createProdEcr(reqData, partList, reqCommentList, issueList, attachFiles);
		    ecrOid = KETEcmHelper.service.createProdEcr(paramMap, attachFiles);

		    if (ecrOid.length() > 0) {
			isSuccess = true;

		    }

		} catch (WTException wte) {
		    Kogger.error(getClass(), wte);
		    ecrOid = "";

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		    ecrOid = "";

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		}

		String msg = "";
		if (!ERROR_MESSAGE.equals("")) {
		    msg += "작업에 실패하였습니다.\\n" + ERROR_MESSAGE;

		    try {
			res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + msg + "\");"
			        + "\n   parent.lfn_feedback_after_ecr();" + "\n </script>";
			out.println(rtn_msg);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }

		}

	    }
	    // 제품 ECR 상세조회 화면에서 '수정' 버튼으로 전환된 수정 화면에서 각각 넘어온다.
	    else if (command.equals("Modify")) {

		// tabName에 따라 넘어오는 정보가 각각 다르다.
		// 기본사항
		if (tabName == null || tabName.equals("") || tabName.equals("ecrBasic")) {

		    attachFiles = uploader.getFiles();

		    try {

			// ecrOid = KETEcmHelper.service.modifyProdEcr(reqData, partHash, issueHash, attachFiles);

			ecrOid = (String) paramMap.getString("oid");
			ALERT_MESSAGE = KETEcmHelper.service.modifyProdEcr(ecrOid, paramMap, attachFiles);

			isSuccess = true;

		    } catch (WTException wte) {
			Kogger.error(getClass(), wte);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    }

		}
		// 주관부서
		else if (tabName.equals("ecrCompetent")) {

		    ecrOid = (String) paramMap.getString("oid");

		    reqData.put("oid", paramMap.getString("oid"));

		    reqData.put("subjectDisplayName", paramMap.getString("subjectDisplayName"));
		    reqData.put("subjectOid", paramMap.getString("subjectOid"));
		    reqData.put("subjectCode", paramMap.getString("subjectCode"));

		    reqData.put("chargeDisplayName", paramMap.getString("chargeDisplayName"));
		    reqData.put("chargeOid", paramMap.getString("chargeOid"));
		    reqData.put("chargeName", paramMap.getString("chargeName"));

		    reqData.put("reviewRequestDate", paramMap.getString("reviewRequestDate"));

		    reqData.put("reviewResultOid", paramMap.getString("reviewResultOid"));
		    
		    reqData.put("mReqOid", paramMap.getString("mReqOid"));

		    reqData.put("webEditor", paramMap.getString("webEditor"));
		    reqData.put("webEditorText", paramMap.getString("webEditorText"));

		    reqData.put("delFileList", paramMap.getString("deleteFileList"));

		    attachFiles = uploader.getFiles();

		    try {
			String competentOid = KETEcmHelper.service.modifyEcrCompetentDepartment(reqData, attachFiles);
			if (competentOid.length() > 0) {
			    isSuccess = true;

			}

		    } catch (WTException wte) {
			Kogger.error(getClass(), wte);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    }

		}
		// 회의록
		else if (tabName.equals("ecrMeeting")) {

		    ecrOid = (String) paramMap.getString("oid");

		    reqData.put("oid", paramMap.getString("oid"));
		    reqData.put("meetingName", paramMap.getString("meetingName"));
		    reqData.put("meetingDate", paramMap.getString("meetingDate"));
		    reqData.put("meetingTime", paramMap.getString("meetingTime"));
		    reqData.put("meetingLocation", paramMap.getString("meetingLocation"));
		    reqData.put("attendance", paramMap.getString("attendance"));

		    reqData.put("subjectDisplayName", paramMap.getString("subjectDisplayName"));
		    reqData.put("subjectOid", paramMap.getString("subjectOid"));
		    reqData.put("subjectCode", paramMap.getString("subjectCode"));

		    reqData.put("chargeDisplayName", paramMap.getString("chargeDisplayName"));
		    reqData.put("chargeOid", paramMap.getString("chargeOid"));
		    reqData.put("chargeName", paramMap.getString("chargeName"));

		    reqData.put("webEditor", paramMap.getString("webEditor"));
		    reqData.put("webEditorText", paramMap.getString("webEditorText"));

		    reqData.put("delFileList", paramMap.getString("deleteFileList"));

		    // ECN 추가
		    Hashtable<String, String[]> ecnList = new Hashtable<String, String[]>();
		    ecnList.put("chkPostActArr", paramMap.getStringArray("chkPostAct"));
		    ecnList.put("ecaUniqueKeyArr", paramMap.getStringArray("ecaUniqueKey"));
		    ecnList.put("docActFlagArr", paramMap.getStringArray("docActFlag"));
		    ecnList.put("codenameArr", paramMap.getStringArray("codename"));
		    ecnList.put("relEcaDocWorkerOidArr", paramMap.getStringArray("relEcaDocWorkerOid"));
		    ecnList.put("completeRequestDateArr", paramMap.getStringArray("completeRequestDate"));

		    attachFiles = uploader.getFiles();

		    try {
			String meetingOid = KETEcmHelper.service.modifyEcrMeetingMinutes(reqData, attachFiles, ecnList);
			if (meetingOid.length() > 0) {
			    isSuccess = true;

			}

		    } catch (WTException wte) {
			Kogger.error(getClass(), wte);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    }

		}

	    }
	    // 기각
	    else if (command.equalsIgnoreCase("Reject")) {

		// 주관부서
		if (tabName.equals("ecrCompetent")) {

		    ecrOid = (String) paramMap.getString("oid");

		    reqData.put("cmd", command);
		    reqData.put("oid", paramMap.getString("oid"));

		    reqData.put("subjectDisplayName", paramMap.getString("subjectDisplayName"));
		    reqData.put("subjectOid", paramMap.getString("subjectOid"));
		    reqData.put("subjectCode", paramMap.getString("subjectCode"));

		    reqData.put("chargeDisplayName", paramMap.getString("chargeDisplayName"));
		    reqData.put("chargeOid", paramMap.getString("chargeOid"));
		    reqData.put("chargeName", paramMap.getString("chargeName"));

		    reqData.put("reviewRequestDate", paramMap.getString("reviewRequestDate"));

		    reqData.put("reviewResultOid", paramMap.getString("reviewResultOid"));
		    reqData.put("mReqOid", paramMap.getString("mReqOid"));

		    reqData.put("webEditor", paramMap.getString("webEditor"));
		    reqData.put("webEditorText", paramMap.getString("webEditorText"));

		    reqData.put("delFileList", paramMap.getString("deleteFileList"));

		    attachFiles = uploader.getFiles();

		    try {
			String competentOid = KETEcmHelper.service.modifyEcrCompetentDepartment(reqData, attachFiles);
			if (competentOid.length() > 0) {
			    isSuccess = true;
			}

		    } catch (WTException wte) {
			Kogger.error(getClass(), wte);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    } catch (Exception e) {
			Kogger.error(getClass(), e);
			ecrOid = "";

			// 에러 메세지 처리
			ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
			ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		    }

		}

	    }

	    if (isSuccess) {

		/*
	         * alertNgo(res, "정상적으로 처리되었습니다.", "/plm/servlet/e3ps/ProdEcrServlet", ecrOid, "CN");
	         */

		try {

		    res.setContentType("text/html;charset=KSC5601");

		    PrintWriter out = res.getWriter();
		    StringBuffer rtn_msg = new StringBuffer("");

		    if (command.equals("Create")) {
			String url = "/plm/jsp/ecm/reform/ViewEcrForm.jsp";
			rtn_msg.append("\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>");

			String detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?prePage=CN&cmd=View&oid=" + ecrOid; // URLCodec urlCodec =
			URLCodec urlCodec = new URLCodec();
			detailInfoUrl = urlCodec.encode(detailInfoUrl);
			rtn_msg.append("\n   <input type='hidden' name='redirectURL' value='" + detailInfoUrl + "'>");

			rtn_msg.append("\n </form>");

			rtn_msg.append("\n <script language=\"javascript\">");

			String msg = "정상적으로 처리되었습니다.";
			if (!ALERT_MESSAGE.equals(""))
			    msg += "\\n" + ALERT_MESSAGE;

			rtn_msg.append("\n   alert(\"" + msg + "\");");

			// rtn_msg.append("\n   parent.hideProcessing();");
			rtn_msg.append("\n   document.frmProc.submit();");
			rtn_msg.append("\n </script>");

		    } else if (command.equals("Modify") || command.equalsIgnoreCase("Reject")) {
			// 기본사항
			if (tabName == null || tabName.equals("") || tabName.equals("ecrBasic")) {

			    rtn_msg.append("\n <script language=\"javascript\">");

			    String msg = "정상적으로 처리되었습니다.";
			    if (!ALERT_MESSAGE.equals(""))
				msg += "\\n" + ALERT_MESSAGE;

			    rtn_msg.append("\n   alert(\"" + msg + "\");");

			    // rtn_msg.append("\n   parent.hideProcessing();");
			    rtn_msg.append("\n   parent.lfn_reloadWhole();");
			    rtn_msg.append("\n </script>");

			}
			// 주관부서, 회의록
			else if (tabName.equals("ecrCompetent") || tabName.equals("ecrMeeting")) {

			    String url = "/plm/servlet/e3ps/ProdEcrServlet";
			    rtn_msg.append("\n <form name='frmProc' method='post' target='_parent' action='" + url + "'>");

			    rtn_msg.append("\n   <input type='hidden' name='prePage' value='CN'>");
			    rtn_msg.append("\n   <input type='hidden' name='cmd' value='View'>");
			    rtn_msg.append("\n   <input type='hidden' name='oid' value='" + ecrOid + "'>");
			    rtn_msg.append("\n   <input type='hidden' name='tabName' value='" + tabName + "'>");

			    rtn_msg.append("\n </form>");

			    rtn_msg.append("\n <script language=\"javascript\">");

			    String msg = "정상적으로 처리되었습니다.";
			    if (!ALERT_MESSAGE.equals(""))
				msg += "\\n" + ALERT_MESSAGE;

			    rtn_msg.append("\n   alert(\"" + msg + "\");");

			    // rtn_msg.append("\n   parent.hideProcessing();");
			    rtn_msg.append("\n   document.frmProc.submit();");
			    rtn_msg.append("\n </script>");

			}

		    }

		    out.println(rtn_msg);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);

		}

	    } else {
		// rd = getServletContext().getRequestDispatcher("/jsp/ecm/CreateProdEcr.jsp");
		// rd.forward( req, res );

		String msg = "작업에 실패하였습니다.";
		if (!ERROR_MESSAGE.equals(""))
		    msg += "\\n" + ERROR_MESSAGE;

		histroyBack(res, msg);
	    }
	} else {
	    command = StringUtil.checkNull(req.getParameter("cmd"));
	    tabName = StringUtil.checkNull(req.getParameter("tabName"));

	    if (command.equals("View") || command.equals("ModifyView") || command.equalsIgnoreCase("PopupView")
		    || command.equalsIgnoreCase("ViewCall") || command.equalsIgnoreCase("PrintView")) {
		try {
		    ecrOid = req.getParameter("oid");

		    if (command.equalsIgnoreCase("PopupView")) {

			/*
		         * rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewProdEcrPopup.jsp");
		         */
			String detailInfoUrl = "";

			ReferenceFactory rf = new ReferenceFactory();
			Persistable persistable = rf.getReference(ecrOid).getObject();
			if (persistable instanceof e3ps.ecm.entity.KETCompetentDepartment) {
			    KETCompetentDepartment competent = (e3ps.ecm.entity.KETCompetentDepartment) persistable;
			    QueryResult qr = PersistenceHelper.manager.navigate(competent, "ecr",
				    e3ps.ecm.entity.KETEcrCompetentLink.class, false);
			    while (qr.hasMoreElements()) {
				e3ps.ecm.entity.KETEcrCompetentLink link = (e3ps.ecm.entity.KETEcrCompetentLink) qr.nextElement();
				persistable = link.getEcr();

				ecrOid = CommonUtil.getOIDString(persistable);
				req.setAttribute("tabId", "2");

				if (persistable instanceof e3ps.ecm.entity.KETProdChangeRequest) {
				    detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search&cmd=View&oid=" + ecrOid; //
				} else {
				    detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + ecrOid; // 금형
				}

			    }

			} else if (persistable instanceof e3ps.ecm.entity.KETMeetingMinutes) {
			    KETMeetingMinutes meeting = (e3ps.ecm.entity.KETMeetingMinutes) persistable;
			    QueryResult qr = PersistenceHelper.manager.navigate(meeting, "ecr", e3ps.ecm.entity.KETEcrMeetingLink.class,
				    false);
			    while (qr.hasMoreElements()) {
				e3ps.ecm.entity.KETEcrMeetingLink link = (e3ps.ecm.entity.KETEcrMeetingLink) qr.nextElement();
				persistable = link.getEcr();

				ecrOid = CommonUtil.getOIDString(persistable);
				req.setAttribute("tabId", "3");

				if (persistable instanceof e3ps.ecm.entity.KETProdChangeRequest) {
				    detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search&cmd=View&oid=" + ecrOid; //
				} else {
				    detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + ecrOid; // 금형
				}

			    }

			} else {

			    String tabId = StringUtil.checkNull(req.getParameter("tabId"));
			    if (tabId.equals(""))
				tabId = "1";
			    req.setAttribute("tabId", tabId);

			    if (persistable instanceof e3ps.ecm.entity.KETProdChangeRequest) {
				detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search&cmd=View&oid=" + ecrOid; //
			    } else {
				detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + ecrOid; // 금형
			    }

			}

			URLCodec urlCodec = new URLCodec();
			detailInfoUrl = "/jsp/ecm/reform/ViewEcrForm.jsp?redirectURL=" + urlCodec.encode(detailInfoUrl);

			rd = getServletContext().getRequestDispatcher(detailInfoUrl);

		    } else {

			/*
		         * prodEcr = KETEcmHelper.service.getProdEcr(ecrOid);
		         */

			ReferenceFactory rf = new ReferenceFactory();
			prodEcr = (wt.change2.WTChangeRequest2) rf.getReference(ecrOid).getObject();
			if (prodEcr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
			    prodEcr = (e3ps.ecm.entity.KETProdChangeRequest) prodEcr;
			} else if (prodEcr instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
			    prodEcr = (e3ps.ecm.entity.KETMoldChangeRequest) prodEcr;
			}

			ProdEcrBeans beans = new ProdEcrBeans();
			Hashtable<String, Object> ecrHash = beans.getProdEcr(KETObjectUtil.getIda2a2(ecrOid));

			req.setAttribute("prodEcr", prodEcr);
			req.setAttribute("ecrHash", ecrHash);

			if (command.equals("View")) {
			    req.setAttribute("prePage", req.getParameter("prePage"));

			    // 주관부서
			    if (tabName.equals("ecrCompetent")) {

				KETCompetentDepartment competent = beans.getKETCompetentDepartment(ecrOid);
				req.setAttribute("competent", competent);

				rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ViewEcrCompetent.jsp");

			    }
			    // 회의록
			    else if (tabName.equals("ecrMeeting")) {

				KETMeetingMinutes meeting = beans.getKETMeetingMinutes(ecrOid);
				req.setAttribute("meeting", meeting);

				rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ViewEcrMeeting.jsp");

			    }
			    // 기본사항
			    else {
				rd = getServletContext().getRequestDispatcher("/jsp/ecm/ViewProdEcr.jsp");

			    }

			} else if (command.equals("ModifyView")) {

			    // 주관부서
			    if (tabName.equals("ecrCompetent")) {

				KETCompetentDepartment competent = beans.getKETCompetentDepartment(ecrOid);
				req.setAttribute("competent", competent);

				rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ModifyEcrCompetent.jsp");

			    }
			    // 회의록
			    else if (tabName.equals("ecrMeeting")) {

				KETMeetingMinutes meeting = beans.getKETMeetingMinutes(ecrOid);
				req.setAttribute("meeting", meeting);

				rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ModifyEcrMeeting.jsp");

			    }
			    // 기본사항
			    else {
				rd = getServletContext().getRequestDispatcher("/jsp/ecm/ModifyProdEcr.jsp");

			    }

			} else if (command.equalsIgnoreCase("ViewCall")) {
			    // 회의소집
			    KETMeetingMinutes meeting = beans.getKETMeetingMinutes(ecrOid);
			    req.setAttribute("meeting", meeting);

			    rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/ModifyEcrCallMeeting.jsp");

			} else if (command.equalsIgnoreCase("PrintView")) {
			    // 회의록 프리트
			    KETMeetingMinutes meeting = beans.getKETMeetingMinutes(ecrOid);
			    req.setAttribute("meeting", meeting);

			    rd = getServletContext().getRequestDispatcher("/jsp/ecm/reform/PrintEcrMeeting.jsp");

			}
		    }

		    rd.forward(req, res);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

	    } else if (command.equals("Delete")) {
		ecrOid = req.getParameter("oid");
		try {
		    isDelete = KETEcmHelper.service.deleteProdEcr(ecrOid);

		    if (isDelete) {

			/*
		         * alertNSearch(res, "정상적으로 처리되었습니다.", "/plm/jsp/ecm/SearchEcrForm.jsp");
		         */

			try {

			    res.setContentType("text/html;charset=KSC5601");

			    PrintWriter out = res.getWriter();
			    StringBuffer rtn_msg = new StringBuffer("");

			    rtn_msg.append("\n <script language=\"javascript\">");

			    String msg = "정상적으로 처리되었습니다.";
			    rtn_msg.append("\n   alert(\"" + msg + "\");");

			    // rtn_msg.append("\n   parent.hideProcessing();");
			    rtn_msg.append("\n   try {");

			    rtn_msg.append("\n   	parent.parent.opener.feedbackAfterPopup('doReSearching');");
			    rtn_msg.append("\n   	parent.parent.window.close();");

			    rtn_msg.append("\n   } catch(e) { alert(e); }");
			    rtn_msg.append("\n </script>");

			    out.println(rtn_msg);

			} catch (Exception e) {
			    Kogger.error(getClass(), e);

			}

		    } else {
			alertNgo(res, "실패하였습니다.", "/plm/servlet/e3ps/ProdEcrServlet", ecrOid, "D");
		    }
		} catch (WTException e) {
		    Kogger.error(getClass(), e);
		}

	    }
	    // ECO 작성
	    else if (command.equals("CreateECO")) {
		ecrOid = req.getParameter("oid");
		try {
		    ProdEcrBeans beans = new ProdEcrBeans();
		    Hashtable<String, Object> ecrHash = beans.getProdEcr(KETObjectUtil.getIda2a2(ecrOid));

		    req.setAttribute("ecrHash", ecrHash);

		    rd = getServletContext().getRequestDispatcher("/jsp/ecm/CreateProdEcoForm.jsp");
		    rd.forward(req, res);

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

	    }
	    // 회의소집
	    else if (command.equals("CallMeeting")) {

		ecrOid = (String) paramMap.getString("oid");

		try {
		    ALERT_MESSAGE = KETEcmHelper.service.callMeeting(ecrOid, paramMap);

		    isSuccess = true;

		} catch (WTException wte) {
		    Kogger.error(getClass(), wte);
		    ecrOid = "";

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(wte.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		    ecrOid = "";

		    // 에러 메세지 처리
		    ERROR_MESSAGE = StringUtils.trim(e.getLocalizedMessage());
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("(\r\n|\n)", "\\\\n");
		    ERROR_MESSAGE = ERROR_MESSAGE.replaceAll("\"", "\\\\\"");

		}

		String msg = "";
		if (!ERROR_MESSAGE.equals("")) {
		    msg += "작업에 실패하였습니다.\\n" + ERROR_MESSAGE;
		} else {
		    msg = "정상적으로 처리되었습니다.";
		    if (!ALERT_MESSAGE.equals(""))
			msg += "\\n" + ALERT_MESSAGE;

		}

		try {
		    res.setContentType("text/html;charset=KSC5601");
		    PrintWriter out = res.getWriter();
		    String rtn_msg = "";
		    rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + msg + "\");"
			    + "\n   parent.lfn_feedback_after_callMeeting();" + "\n </script>";
		    out.println(rtn_msg);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}
	    }
	    // 디버깅
	    else if (command.equalsIgnoreCase("ApprovedReg")) {

		ecrOid = (String) paramMap.getString("oid");

		String detailInfoUrl = "";
		try {
		    ReferenceFactory rf = new ReferenceFactory();
		    prodEcr = (wt.change2.WTChangeRequest2) rf.getReference(ecrOid).getObject();

		    if (prodEcr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
			prodEcr = (e3ps.ecm.entity.KETProdChangeRequest) prodEcr;
		    } else if (prodEcr instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
			prodEcr = (e3ps.ecm.entity.KETMoldChangeRequest) prodEcr;
		    }

		    State state = null;
		    if (prodEcr.getLifeCycleState().toString().equals("APPROVED")) {
			LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodEcr, State.toState("UNDERREVIEW"), true);

		    }
		    state = State.toState("APPROVED");

		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodEcr, state, true);

		    res.setContentType("text/html;charset=KSC5601");
		    PrintWriter out = res.getWriter();
		    StringBuffer rtn_msg = new StringBuffer("");

		    rtn_msg.append("\n <script language=\"javascript\">");

		    String msg = "정상적으로 처리되었습니다.";
		    rtn_msg.append("\n   alert(\"" + msg + "\");");

		    // rtn_msg.append("\n   parent.hideProcessing();");
		    rtn_msg.append("\n   parent.lfn_reloadWhole();");
		    rtn_msg.append("\n </script>");

		    out.println(rtn_msg);

		} catch (WTRuntimeException e) {
		    // TODO Auto-generated catch block
		    Kogger.error(getClass(), e);
		} catch (WTException e) {
		    // TODO Auto-generated catch block
		    Kogger.error(getClass(), e);
		}

	    }

	}
    }

    protected void alertNgo(HttpServletResponse res, String msg, String url, String oid, String prePage) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>"
		    + "\n <input type='hidden' name='cmd' value='View'>" + "\n <input type='hidden' name='oid' value='" + oid + "'>"
		    + "\n <input type='hidden' name='prePage' value='" + prePage + "'>" + "\n </form>"
		    + "\n <script language=\"javascript\">"
		    // + "\n   parent.hideProcessing();"
		    // + "\n   parent.enabledAllBtn();"
		    + "\n   alert(\"" + msg + "\");" + "\n   document.frmProc.submit();" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    protected void alertNSearch(HttpServletResponse res, String msg, String url) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <form name='frmProc' method='post' target='contName' action='" + url + "'>"
		    + "\n <input type='hidden' name='cmd' value='Search'>" + "\n </form>" + "\n <script language=\"javascript\">"
		    // + "\n   parent.hideProcessing();"
		    // + "\n   parent.enabledAllBtn();"
		    + "\n   alert(\"" + msg + "\");" + "\n   document.frmProc.submit();" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    public void histroyBack(HttpServletResponse res, String msg) {
	try {
	    res.setContentType("text/html;charset=KSC5601");
	    PrintWriter out = res.getWriter();
	    String rtn_msg = "";
	    rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + msg + "\");" + "\n   history.back();" + "\n </script>";
	    out.println(rtn_msg);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

}
