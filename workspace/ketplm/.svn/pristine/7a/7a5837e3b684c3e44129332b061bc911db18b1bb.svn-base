package e3ps.wfm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTPrincipalReference;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.util.WTRuntimeException;
import wt.workflow.work.WorkItem;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WFMProperties;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.shared.log.Kogger;

public class CommonWorkflowServlet extends CommonServlet {
	/**
     * 
     */
	private static final long serialVersionUID = 7275964974299369380L;

	KETWfmApprovalMaster masObj = new KETWfmApprovalMaster();

	Config conf = ConfigImpl.getInstance();

	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String cmd = ParamUtil.getParameter(req, "cmd");
		String pboOid = ParamUtil.getParameter(req, "pbo");
		String popup = ParamUtil.getParameter(req, "popup", "");
		// [START] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
		String mode = ParamUtil.getParameter(req, "mode");
		// [END] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
		boolean flag = false;
		String url = "";
		String success = WFMProperties.getInstance().getString("wfm.msg.success");
		String fail = WFMProperties.getInstance().getString("wfm.msg.fail");
		Kogger.debug(getClass(), "========> CommonWorkflowServlet :: cmd : " + cmd);
		Kogger.debug(getClass(), "========> CommonWorkflowServlet :: pboOid : " + pboOid);
		Kogger.debug(getClass(), "========> CommonWorkflowServlet :: popup : " + popup);
		if (cmd.equals("create")) {
			flag = create(req, res); // 결재마스터 생성
		} else if (cmd.equals("update")) {
			flag = update(req, res); // 결재마스터 업데이트
		} else if (cmd.equals("start")) {
			Kogger.biz("결재 상신 시작");
			flag = startProcess(req, res); // 결재 시작

			Kogger.debug(getClass(), ">>>>>>>>WFM SERVLET IN>>>>>>>>>");
			Kogger.debug(getClass(), ">>>>>>>>POPUP VALUE IS : " + popup + ">>>>>>>>>");
		} else if (cmd.equals("complete")) {
			flag = complete(req, res); // 결재 수행
			url = "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
		} else if (cmd.equals("updateRestart")) {
			flag = updateRestart(req, res); // 재작업 상태에서의 결재 시작
			url = "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchWaitingAppr";
		} else if (cmd.equals("createMulti")) {
			String targetOid = createMulti(req, res); // 일괄결재 요청서 생성
			if (!targetOid.equals("")) {
				url = "/plm/jsp/wfm/ViewMultiApproval.jsp?oid=" + targetOid;
				flag = true;
			}
		}
		// shkim@e3ps.com
		else if (cmd.equals("createMulti2")) {
			String targetOid = createMulti2(req, res);

			if (!targetOid.equals("")) {
				url = "/plm/jsp/dms/ViewDocMultiApproval.jsp?oid=" + targetOid;
				flag = true;
			}
		} else if (cmd.equals("updateMulti2")) {
			String targetOid = updateMulti2(req, res); // 일괄결재 요청서 수정

			if (!targetOid.equals("")) {
				url = "/plm/jsp/dms/ViewDocMultiApproval.jsp?oid=" + targetOid;
				flag = true;
			}
		} else if (cmd.equals("deleteMulti2")) {
			flag = deleteMulti(req, res); // 일괄결재 요청서의 삭제
			url = "/plm/jsp/dms/SearchDocMultiApproval.jsp";
		} else if (cmd.equals("updateMulti")) {
			String targetOid = updateMulti(req, res); // 일괄결재 요청서 수정

			if (!targetOid.equals("")) {
				url = "/plm/jsp/wfm/ViewMultiApproval.jsp?oid=" + targetOid;
				flag = true;
			}
		} else if (cmd.equals("deleteMulti")) {
			flag = deleteMulti(req, res); // 일괄결재 요청서의 삭제
			url = "/plm/jsp/wfm/SearchMultiApproval.jsp";
		}
		if (!popup.equals("")) // 일반 프로젝트의 결재 상신 후 return url
		{
			url = "/plm/jsp/project/ProjectViewFrm.jsp?popup=popup&oid=" + pboOid;
			if (!"".equals(mode) && (mode.equals("cancelStart") || mode.equals("stopStart"))) {// 프로젝트 취소 / 중지 결재요청시
				if (flag) {
					alertNgoParentPage(res, success, url);
				} else {
					alertNgoParentPage(res, success, url);
				}
			} else {
				if (StringUtil.checkString(mode)) {
					if (flag) {
						alertNCallFunction(res, success, mode);
					} else {
						alertNCallFunction(res, fail, mode);
					}
				} else {
					if (flag) {
						alertNRefreshProjectParentPage2(res, success);
					} else {
						alertNRefreshProjectParentPage2(res, fail);
					}
				}
			}
		} else if (popup.equals("")) {
			if (pboOid.matches(".*ProductProject.*")) // 제품프로젝트의 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES ProductProject>>>>>>>>>");
				// url = "/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=" + pboOid + "'," + "'/plm/jsp/project/ProjectView.jsp?oid=" + pboOid
				// + "&cmd=";
				// [START] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
				if (flag) {
					alertNRefreshProjectParentPage2(res, success);
				} else {
					alertNRefreshProjectParentPage2(res, fail);
				}
				// [END] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
			} else if (pboOid.matches(".*MoldProject.*")) // 금형프로젝트의 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES MoldProject>>>>>>>>>");
				// url = "/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=" + pboOid + "'," + "'/plm/jsp/project/MoldProjectView.jsp?oid=" +
				// pboOid + "&cmd=";
				// [START] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
				if (flag) {
					if ("ChangeSchedule".equals(mode)) { // 일정변경사유 입력 팝업에서 결재요청 시 화면 처리
						alertNRefreshProjectParentPage2(res, success);
					} else {
						alertNRefreshProjectParentPage2(res, success);
					}
				} else {
					if ("ChangeSchedule".equals(mode)) { // 일정변경사유 입력 팝업에서 결재요청 시 화면 처리
						alertNRefreshProjectParentPage2(res, fail);
					} else {
						alertNRefreshProjectParentPage2(res, fail);
					}
				}
				// [END] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
			} else if (pboOid.matches(".*ReviewProject.*")) // 검토프로젝트의 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES ReviewProject>>>>>>>>>");
				// url = "/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=" + pboOid + "'," + "'/plm/jsp/project/ReviewProjectView.jsp?oid=" +
				// pboOid + "&cmd=";
				// [START] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
				if (flag) {

					if ("ChangeSchedule".equals(mode)) { // 일정변경사유 입력 팝업에서 결재요청 시 화면 처리
						alertNRefreshProjectParentPage2(res, success);
					} else {
						alertNRefreshProjectParentPage2(res, success);
					}
				} else {
					if ("ChangeSchedule".equals(mode)) { // 일정변경사유 입력 팝업에서 결재요청 시 화면 처리
						alertNRefreshProjectParentPage2(res, fail);
					} else {
						alertNRefreshProjectParentPage2(res, fail);
					}
				}
				// [END] [PLM System 1차개선] 일정변경사유 입력 팝업에서 결재요청 시 처리, 2013-07-08, BoLee
			} else if (pboOid.startsWith("e3ps.ecm.entity.KETProdChangeRequest")) // 제품 ECR 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES KETProdChangeRequest >>>>>>>>>");
				// url = "/plm/servlet/e3ps/ProdEcrServlet?prePage=Search";
				url = "/plm/servlet/e3ps/ProdEcrServlet";
				if (flag) {
					alertNRefreshECMParentPage(res, success, url, pboOid, "View");
				} else {
					alertNRefreshECMParentPage(res, fail, url, pboOid, "View");
				}
			} else if (pboOid.startsWith("e3ps.ecm.entity.KETMoldChangeRequest")) // 금형 ECR 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES KETMoldChangeRequest >>>>>>>>>");

				url = "/plm/servlet/e3ps/MoldEcrServlet";

				if (flag) {
					alertNRefreshECMParentPage(res, success, url, pboOid, "view");
				} else {
					alertNRefreshECMParentPage(res, fail, url, pboOid, "view");
				}
			} else if (pboOid.startsWith("e3ps.ecm.entity.KETProdChangeOrder")) // 제품 ECO 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES KETProdChangeOrder >>>>>>>>>");
				// url = "/plm/servlet/e3ps/ProdEcoServlet?prePage=Search";
				url = "/plm/servlet/e3ps/ProdEcoServlet";

				if (flag) {
					alertNRefreshECMParentPage(res, success, url, pboOid, "View");
				} else {
					alertNRefreshECMParentPage(res, fail, url, pboOid, "View");
				}
			} else if (pboOid.startsWith("e3ps.ecm.entity.KETMoldChangeOrder")) // 금형 ECR 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES KETMoldChangeOrder >>>>>>>>>");
				url = "/plm/servlet/e3ps/MoldEcoServlet";
				if (flag) {
					alertNRefreshECMParentPage(res, success, url, pboOid, "view");
				} else {
					alertNRefreshECMParentPage(res, fail, url, pboOid, "view");
				}
			} else if (pboOid.startsWith("wt.epm.EPMDocument")) // EPMDocument(도면) 결재 상신 후 return url
			{
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES EPMDocument >>>>>>>>>");
				url = "/plm/jsp/edm/ViewEPMDocument.jsp?oid=" + pboOid;
				if (flag) {
					alertNRefreshEDMParentPage(res, success, url);
				} else {
					alertNRefreshEDMParentPage(res, fail, url);
				}
			} else {
				Kogger.debug(getClass(), ">>>>>>>>PBO MATCHES ELSE!!!>>>>>>>>>");
				if (flag && url.equals("")) {
					if ("complete".equals(cmd)) {
						alertNMenuReloadNclose(res, success);
					} else {
						alertNMenuReloadNclose(res, success);
					}
				} else if (!flag && url.equals("")) {
					alertNclose(res, fail);
				} else if (flag && !url.equals("")) {
					// alertNgo(res, success, url);
					if ("complete".equals(cmd)) {
						alertNMenuReloadNclose(res, success);
					} else {
						alertNMenuReloadNclose(res, success);
					}
				} else if (!flag && !url.equals("")) {
					// alertNgo(res, fail, url);
					alertNclose(res, fail);
				}
			}
		}
	}

	private void alertNCallFunction(HttpServletResponse res, String msg, String mode) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");" + "\n   try { " + "\n       opener." + mode + "();" + "\n   } catch(e) { "
					+ "\n   } " + "\n   self.close();" + "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	private void alertNMenuReloadNclose(HttpServletResponse res, String msg) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");" + "\n   try { "
					+ "\n       opener.parent.Ext.getCmp('workspace').getStore().reload();" + "\n   } catch(e) { " + "\n   } "
					+ "\n   try {\n  opener.lfn_feedback_after_startProcess();  \n} catch(e) {\n  try{opener.location.reload();} catch(e) {}  \n}\n   window.close();"
					+ "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	private void alertNRefreshProjectParentPage2(HttpServletResponse res, String success) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(success) + "\");" + "\n   try{"
					+ "\n   	if(opener.opener != null) opener.opener.parent.parent.location.reload();" + "\n   	opener.close();" + "\n   	self.close();" + "\n   }catch(e){"
					+ "\n   	self.close()" + "\n   }" + "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	public boolean create(HttpServletRequest req, HttpServletResponse res) {

		boolean flag = false;
		String[] beforeReview = req.getParameter("bReview").split(","); // 합의전검토 단계에 지정된 사용자 id
		String[] discuss = req.getParameter("discuss").split(","); // 합의 단계에 지정된 사용자 id
		String[] afterReview = req.getParameter("aReview").split(",");// 검토및승인 단계에 지정된 사용자 id
		String[] receiver = req.getParameter("receiver").split(",");// 수신자 단계에 지정된 사용자 id
		String[] reference = req.getParameter("reference").split(",");// 참조자 단계에 지정된 사용자 id
		String discussType = req.getParameter("discussType");
		// 합의유형값(noDiscuss:합의없음/sequential:순차합의/parallel:병렬합의/reqReceiver:의뢰접수)
		String comment = req.getParameter("textfield");// 결재 요청자의 코멘트
		String pboOid = ParamUtil.getParameter(req, "pbo");// 결재를 요청할 대상 객체 OID
		boolean startFlag = Boolean.parseBoolean(ParamUtil.getParameter(req, "startFlag"));
		// 일괄결재 요청
		String[] pboOids = req.getParameterValues("pboOids");
		ReferenceFactory rf = new ReferenceFactory();
		try {
			WTPrincipalReference creatorID = SessionHelper.manager.getPrincipalReference();

			if (pboOids != null && pboOids.length > 0) {
				for (String str : pboOids) {
					Persistable pbo = rf.getReference(str).getObject();
					if (pbo instanceof KETDevelopmentRequest || pbo instanceof KETAnalysisRequestMaster) {
						discussType = "reqReceiver";
					}
					KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);
					if (master != null) {
						req.setAttribute("oid", CommonUtil.getOIDString(master));
						flag = update(req, res);
					} else {
						Hashtable<String, Object> hash = new Hashtable<String, Object>();
						hash.put("agreementType", discussType);
						hash.put("creator", creatorID);
						hash.put("comment", comment);
						hash.put("pbo", pbo);
						hash.put("startFlag", startFlag);
						master = (KETWfmApprovalMaster) KETWfmHelper.service.createMaster(hash); // 결재 마스터 생성
						if (master != null) {
							masObj = (KETWfmApprovalMaster) master; // 각 단계에서 사용자를 저장함
							if (beforeReview.length > 0)
								saveApprovalLine("beforeReview", beforeReview, master);
							if (discuss.length > 0)
								saveApprovalLine("discuss", discuss, master);
							if (afterReview.length > 0)
								saveApprovalLine("afterReview", afterReview, master);
							if (receiver.length > 0)
								saveApprovalLine("receiver", receiver, master);
							if (reference.length > 0)
								saveApprovalLine("reference", reference, master);
							flag = true;
						} else {
							flag = false;
						}
					}
				}
			} else {
				Persistable pbo = rf.getReference(pboOid).getObject();
				if (pbo instanceof KETDevelopmentRequest || pbo instanceof KETAnalysisRequestMaster) {
					discussType = "reqReceiver";
				}
				KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);
				if (master != null) {
					req.setAttribute("oid", CommonUtil.getOIDString(master));
					flag = update(req, res);
				} else {
					Hashtable<String, Object> hash = new Hashtable<String, Object>();
					hash.put("agreementType", discussType);
					hash.put("creator", creatorID);
					hash.put("comment", comment);
					hash.put("pbo", pbo);
					hash.put("startFlag", startFlag);
					master = (KETWfmApprovalMaster) KETWfmHelper.service.createMaster(hash); // 결재 마스터 생성
					if (master != null) {
						masObj = (KETWfmApprovalMaster) master; // 각 단계에서 사용자를 저장함
						if (beforeReview.length > 0)
							saveApprovalLine("beforeReview", beforeReview, master);
						if (discuss.length > 0)
							saveApprovalLine("discuss", discuss, master);
						if (afterReview.length > 0)
							saveApprovalLine("afterReview", afterReview, master);
						if (receiver.length > 0)
							saveApprovalLine("receiver", receiver, master);
						if (reference.length > 0)
							saveApprovalLine("reference", reference, master);
						flag = true;
					} else {
						flag = false;
					}
				}
			}
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		return flag;
	}

	public boolean update(HttpServletRequest req, HttpServletResponse res) {

		boolean flag = false;
		String[] beforeReview = req.getParameter("bReview").split(",");
		String[] discuss = req.getParameter("discuss").split(",");
		String[] afterReview = req.getParameter("aReview").split(",");
		String[] receiver = req.getParameter("receiver").split(",");
		String[] reference = req.getParameter("reference").split(",");
		String discussType = req.getParameter("discussType");
		String comment = req.getParameter("textfield");
		String oid = ParamUtil.getParameter(req, "oid");
		if (!StringUtil.checkString(oid))
			oid = (String) req.getAttribute("oid");

		ReferenceFactory rf = new ReferenceFactory();
		KETWfmApprovalMaster master = null;

		try {
			KETWfmHelper.service.updateMaster(oid, discussType, comment);
			master = (KETWfmApprovalMaster) rf.getReference(oid).getObject();

			if (beforeReview.length > 0)
				saveApprovalLine("beforeReview", beforeReview, master);

			if (discuss.length > 0)
				saveApprovalLine("discuss", discuss, master);

			if (afterReview.length > 0)
				saveApprovalLine("afterReview", afterReview, master);

			if (receiver.length > 0)
				saveApprovalLine("receiver", receiver, master);

			if (reference.length > 0)
				saveApprovalLine("reference", reference, master);

			flag = true;
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}

		return flag;
	}

	public String getArticleInfo(Persistable pbo) {

		String articleUser = "";
		try {
			if (pbo instanceof KETProjectDocument) {
				KETProjectDocument docu = (KETProjectDocument) pbo;
				KETDocumentCategory docCate = null;
				QueryResult r = PersistenceHelper.manager.navigate(docu, "documentCategory", KETDocumentCategoryLink.class);
				if (r.hasMoreElements()) {
					docCate = (KETDocumentCategory) r.nextElement();

					if ("물품사양서".equals(docCate.getCategoryName())) {
						Hashtable DesignPeopleTab = null;
						ArrayList<Hashtable> list = CommonUtil.findUser("물품사양관리", "userId");

						for (Hashtable articleHash : list) {
							articleUser = (String) articleHash.get("userId");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return articleUser;
	}

	public boolean startProcess(HttpServletRequest req, HttpServletResponse res) {

		Kogger.info(getClass(), "##################################");
		Kogger.info(getClass(), "##### 결재 ( WorkFlow ) 시작");
		Kogger.info(getClass(), "##################################");

		boolean flag = false;
		String pboOid = ParamUtil.getParameter(req, "pbo");// 결재를 요청할 대상 객체

		String[] pboOids = req.getParameterValues("pboOids");
		boolean startFlag = Boolean.parseBoolean(ParamUtil.getParameter(req, "startFlag"));

		Kogger.info(getClass(), "pboOid:" + pboOid);
		Kogger.info(getClass(), "pboOids:" + pboOids);
		Kogger.info(getClass(), "startFlag:" + startFlag);

		try {
			String[] beforeReview = req.getParameter("bReview").split(",");
			String[] discuss = req.getParameter("discuss").split(",");
			String[] afterReview = req.getParameter("aReview").split(",");
			String[] receiver = req.getParameter("receiver").split(",");
			String[] reference = req.getParameter("reference").split(",");
			String discussType = req.getParameter("discussType");
			String comment = req.getParameter("textfield");

			Kogger.info(getClass(), "beforeReview:" + beforeReview);
			Kogger.info(getClass(), "discuss:" + discuss);
			Kogger.info(getClass(), "afterReview:" + afterReview);
			Kogger.info(getClass(), "receiver:" + receiver);
			Kogger.info(getClass(), "reference:" + reference);
			Kogger.info(getClass(), "discussType:" + discussType);
			Kogger.info(getClass(), "comment:" + comment);

			String articleUser = "";
			if (pboOids != null && pboOids.length > 0) {

				for (String str : pboOids) {

					Persistable pbo = CommonUtil.getObject(str);
					// 개발의뢰처럼 해석의뢰 KETAnalysisRequestMaster 추가
					if (pbo instanceof KETDevelopmentRequest || pbo instanceof KETAnalysisRequestMaster) {
						Kogger.info(getClass(), "[startProcess] 개발의뢰 해석의뢰 discussType → reqReceiver");
						discussType = "reqReceiver";
					}

					KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);

					if (master != null) {

						Kogger.info(getClass(), "KETWfmApprovalMaster 기존 oid :" + CommonUtil.getOIDString(master));
						KETWfmHelper.service.updateMaster(CommonUtil.getOIDString(master), discussType, comment);
						master = (KETWfmApprovalMaster) PersistenceHelper.manager.refresh(master);
						if (beforeReview.length > 0)
							saveApprovalLine("beforeReview", beforeReview, master);
						if (discuss.length > 0)
							saveApprovalLine("discuss", discuss, master);
						if (afterReview.length > 0)
							saveApprovalLine("afterReview", afterReview, master);
						if (receiver.length > 0)
							saveApprovalLine("receiver", receiver, master);
						if (reference.length > 0)
							saveApprovalLine("reference", reference, master);
						master.setStartFlag(true);
						master = (KETWfmApprovalMaster) PersistenceHelper.manager.save(master);
						flag = KETWfmHelper.service.startProcess(str, master);

					} else {

						Kogger.info(getClass(), "결재마스터가 없어서 신규 결재마스터 생성");
						Hashtable<String, Object> hash = new Hashtable<String, Object>();
						hash.put("agreementType", discussType);
						hash.put("creator", SessionHelper.manager.getPrincipalReference());
						hash.put("comment", comment);
						hash.put("pbo", pbo);
						hash.put("startFlag", true);

						Kogger.info(getClass(), "agreementType:" + discussType);
						Kogger.info(getClass(), "creator:" + SessionHelper.manager.getPrincipalReference().getFullName());
						Kogger.info(getClass(), "comment:" + comment);
						Kogger.info(getClass(), "pbo:" + pbo);
						Kogger.info(getClass(), "startFlag:" + true);

						master = (KETWfmApprovalMaster) KETWfmHelper.service.createMaster(hash); // 결재 마스터 생성
						if (beforeReview.length > 0)
							saveApprovalLine("beforeReview", beforeReview, master);
						if (discuss.length > 0)
							saveApprovalLine("discuss", discuss, master);
						if (afterReview.length > 0)
							saveApprovalLine("afterReview", afterReview, master);
						if (receiver.length > 0)
							saveApprovalLine("receiver", receiver, master);
						if (reference.length > 0)
							saveApprovalLine("reference", reference, master);

						flag = KETWfmHelper.service.startProcess(str, master);
						flag = true;

					} // if (master != null) {
				} // for (String str : pboOids) {

			} else {

				Persistable pbo = CommonUtil.getObject(pboOid);
				if (pbo instanceof KETDevelopmentRequest || pbo instanceof KETAnalysisRequestMaster) {
					Kogger.info(getClass(), "[startProcess] 개발의뢰 해석의뢰 discussType → reqReceiver");
					discussType = "reqReceiver";
				}
				KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);

				articleUser = this.getArticleInfo(pbo);
				boolean articleFlag = true;

				if (!"".equals(articleUser)) {
					/*
					 * for (int j = 0; j < receiver.length; j++) { if (articleUser.equals(receiver[j])) { articleFlag = false; continue; } }
					 */
					if (StringUtils.contains(req.getParameter("receiver"), articleUser)) {
						articleFlag = false;
					}
					if (articleFlag) {
						receiver = (articleUser + "," + req.getParameter("receiver")).split(",");
					}
				}

				if (master != null) {

					Kogger.info(getClass(), "KETWfmApprovalMaster 기존 oid :" + CommonUtil.getOIDString(master));

					KETWfmHelper.service.updateMaster(CommonUtil.getOIDString(master), discussType, comment);
					master = (KETWfmApprovalMaster) PersistenceHelper.manager.refresh(master);
					if (beforeReview.length > 0)
						saveApprovalLine("beforeReview", beforeReview, master);
					if (discuss.length > 0)
						saveApprovalLine("discuss", discuss, master);
					if (afterReview.length > 0)
						saveApprovalLine("afterReview", afterReview, master);
					if (receiver.length > 0)
						saveApprovalLine("receiver", receiver, master);
					if (reference.length > 0)
						saveApprovalLine("reference", reference, master);
					master.setStartFlag(true);
					master = (KETWfmApprovalMaster) PersistenceHelper.manager.save(master);

					flag = KETWfmHelper.service.startProcess(pboOid, master);

				} else {

					Kogger.info(getClass(), "결재마스터가 없어서 신규 결재마스터 생성");
					Hashtable<String, Object> hash = new Hashtable<String, Object>();
					hash.put("agreementType", discussType);
					hash.put("creator", SessionHelper.manager.getPrincipalReference());
					hash.put("comment", comment);
					hash.put("pbo", pbo);
					hash.put("startFlag", true);

					Kogger.info(getClass(), "agreementType:" + discussType);
					Kogger.info(getClass(), "creator:" + SessionHelper.manager.getPrincipalReference().getFullName());
					Kogger.info(getClass(), "comment:" + comment);
					Kogger.info(getClass(), "pbo:" + pbo);
					Kogger.info(getClass(), "startFlag:" + true);

					master = (KETWfmApprovalMaster) KETWfmHelper.service.createMaster(hash); // 결재 마스터 생성

					if (beforeReview.length > 0)
						saveApprovalLine("beforeReview", beforeReview, master);
					if (discuss.length > 0)
						saveApprovalLine("discuss", discuss, master);
					if (afterReview.length > 0)
						saveApprovalLine("afterReview", afterReview, master);
					if (receiver.length > 0)
						saveApprovalLine("receiver", receiver, master);
					if (reference.length > 0)
						saveApprovalLine("reference", reference, master);

					flag = KETWfmHelper.service.startProcess(pboOid, master);

					flag = true;
				}
			}
		} catch (WTRuntimeException e) {
			Kogger.error(getClass(), e);
			flag = false;
		} catch (WTException e) {
			Kogger.error(getClass(), e);
			flag = false;
		} catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
			flag = false;
		}

		return flag;
	}

	public boolean complete(HttpServletRequest req, HttpServletResponse res) {
		boolean flag = false;
		// WorkItem OID
		String item = ParamUtil.getParameter(req, "item");
		// 결재 수행시 결과 값 (accept:승인/conAccept:조건부승인/reject:반려/cancel:취소/taskComplete:작업활동완료)
		String condition = ParamUtil.getParameter(req, "condition");
		// 결재 마스터 OID
		String master = ParamUtil.getParameter(req, "master");
		// 결재 수행 시 코멘트
		String comment = ParamUtil.getParameter(req, "acomment");
		boolean riskCheck = Boolean.parseBoolean(StringUtil.checkReplaceStr(ParamUtil.getParameter(req, "riskCheck"), "false"));

		// 결재 WorkItem
		WorkItem workItem = (WorkItem) CommonUtil.getObject(item);

		Kogger.debug("결재 수행 결과값 :::" + condition);
		try {
			Hashtable aHash = new Hashtable();
			aHash.put("item", item);
			aHash.put("condition", condition);
			aHash.put("master", master);
			aHash.put("comment", comment);
			aHash.put("riskCheck", riskCheck);
			KETWfmHelper.service.completeActivity(aHash); // 결재 수행 완료
			flag = true;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			flag = false;
		}

		return flag;
	}

	public boolean updateRestart(HttpServletRequest req, HttpServletResponse res) {
		boolean flag = false;

		String masterOid = ParamUtil.getParameter(req, "master");
		String itemOid = ParamUtil.getParameter(req, "item");

		try {
			KETWfmHelper.service.updateProcess(itemOid, masterOid);
			// 반려확인 이후 작업 (상태를 재작업상태로 변경 후 연관 WfAssignedActivity를 삭제함)
			flag = true;
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}

		return flag;
	}

	public String createMulti(HttpServletRequest req, HttpServletResponse res) {
		String targetOid = "";

		String title = ParamUtil.getParameter(req, "title"); // 일괄결재요청서 제목
		String desc = ParamUtil.getParameter(req, "desc"); // 일괄결재요청서 내용
		String[] epmOid = req.getParameter("sendList").split(",");
		// 일괄결재요청서에 링크될 도면 OID 목록

		ArrayList<Object> target = new ArrayList<Object>();
		target.add(0, title);
		target.add(1, desc);
		target.add(2, epmOid);

		try {
			targetOid = KETWfmHelper.service.createMultiReq(target); // 일괄결재요청서 생성
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		return targetOid;
	}

	// //// shkim@e3ps.com
	public String createMulti2(HttpServletRequest req, HttpServletResponse res) {
		String targetOid = "";

		String title = ParamUtil.getParameter(req, "title"); // 일괄결재요청서 제목
		String desc = ParamUtil.getParameter(req, "desc"); // 일괄결재요청서 내용
		String[] docOid = req.getParameterValues("docOid"); // 일괄결재요청서에 링크될 문서 OID 목록
		String taskoid = req.getParameter("taskoid"); // 일괄결재 문서를 등록한 task

		ArrayList<Object> target = new ArrayList<Object>();
		target.add(0, title);
		target.add(1, desc);
		target.add(2, docOid);
		target.add(3, taskoid);

		try {
			targetOid = KETWfmHelper.service.createMultiReq(target); // 일괄결재요청서 생성
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		return targetOid;
	}

	public String updateMulti2(HttpServletRequest req, HttpServletResponse res) {
		String targetOid = "";

		String title = ParamUtil.getParameter(req, "title");
		String desc = ParamUtil.getParameter(req, "desc");
		String[] docOid = req.getParameter("sendList").split(",");
		String oid = ParamUtil.getParameter(req, "oid");
		String taskoid = ParamUtil.getParameter(req, "taskoid");

		ArrayList<Object> target = new ArrayList<Object>();
		target.add(0, title);
		target.add(1, desc);
		target.add(2, docOid);
		target.add(3, oid);
		target.add(4, taskoid);

		try {
			targetOid = KETWfmHelper.service.updateMultiReq(target);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		return targetOid;
	}

	// /////////////////////////////////

	public String updateMulti(HttpServletRequest req, HttpServletResponse res) {
		String targetOid = "";

		String title = ParamUtil.getParameter(req, "title");
		String desc = ParamUtil.getParameter(req, "desc");
		String[] epmOid = req.getParameter("sendList").split(",");
		String oid = ParamUtil.getParameter(req, "oid");

		ArrayList<Object> target = new ArrayList<Object>();
		target.add(0, title);
		target.add(1, desc);
		target.add(2, epmOid);
		target.add(3, oid);

		try {
			targetOid = KETWfmHelper.service.updateMultiReq(target);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		return targetOid;
	}

	public boolean deleteMulti(HttpServletRequest req, HttpServletResponse res) {
		boolean flag = false;
		String targetOid = ParamUtil.getParameter(req, "oid");

		try {
			KETWfmHelper.service.deleteMultiReq(targetOid);
			flag = true;
		} catch (Exception e) {
			Kogger.error(getClass(), e);
			Kogger.error(getClass(), "#############################Delete MultiRequest Fail###############################");
		}
		return flag;
	}

	protected void saveApprovalLine(String type, String[] target, Persistable obj) {

		try {

			for (int i = 0; i < target.length; i++) {
				if (!target[i].equals("")) {
					Hashtable hash = new Hashtable();
					hash.put("order", i); // 결재선 지정 시 순서
					hash.put("type", type); // 결재 단계 (합의전검토/합의/검토및승인)
					hash.put("userID", target[i]); // 해당 사용자 id
					hash.put("master", obj); // 결재마스터

					Kogger.info(getClass(), "결재선 order:" + i + " / type:" + type + " / userID:" + target[i] + " / obj:" + CommonUtil.getOIDString(obj));

					KETWfmHelper.service.createLine(hash);

				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	private String replaceMsg(String msg) {
		msg = msg.replaceAll("\"", "&quot;");
		return msg.replaceAll("\n", " ");
	}

	protected void alertNgoMovePage(HttpServletResponse res, String msg, String url) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");" + "\n   opener.parent.movePaage('" + url + "');" + "\n   window.close();"
					+ "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	protected void alertNgoMovePage2(HttpServletResponse res, String msg, String url) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");" + "\n   opener.parent.parent.movePaage('" + url + "');"
					+ "\n   window.close();" + "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	protected void alertNgoParentPage(HttpServletResponse res, String msg, String url) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");" + "\n   opener.parent.document.location.href='" + url + "';"
					+ "\n   window.close();" + "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	protected void alertNRefreshECMParentPage(HttpServletResponse res, String msg, String url, String oid, String cmd) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";

			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");" + "\n   opener.document.forms[0].oid.value='" + oid + "';"
					+ "\n   opener.document.forms[0].cmd.value='" + cmd + "';" + "\n   opener.document.forms[0].target='_self';" + "\n   opener.document.forms[0].action='" + url
					+ "';" + "\n   opener.document.forms[0].method='post';" + "\n   opener.document.forms[0].submit();" + "\n   window.close();" + "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	protected void alertNRefreshEDMParentPage(HttpServletResponse res, String msg, String url) {
		try {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";

			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");" + "\n   opener.document.forms[0].target='_self';"
					+ "\n   opener.document.forms[0].action='" + url + "';" + "\n   opener.document.forms[0].method='post';" + "\n   opener.document.forms[0].submit();"
					+ "\n   window.close();" + "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	/**
	 * 함수명 : alertNRefreshProjectParentPage 설명 : [PLM System 1차개선] Project 일정 변경 및 일정변경사유 입력 화면에서 결재요청 후 화면 처리
	 * 
	 * @param res
	 * @param msg
	 * @param url
	 * @param cmd
	 *            작성자 : BoLee 작성일자 : 2013. 7. 8.
	 */
	protected void alertNRefreshProjectParentPage(HttpServletResponse res, String msg, String url, String cmd) {
		try {

			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";

			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");";

			if ("start".equals(cmd)) {
				rtn_msg += "\n   opener.opener.parent.parent.movePaage('" + url + "');" + "\n   opener.close();";
			}

			rtn_msg += "\n   window.close();" + "\n </script>";

			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	/**
	 * 함수명 : alertNRefreshProjectPopupParentPage 설명 : [PLM System 1차개선] Project 상세정보 팝업에서 오픈된 Project 일정 변경 및 일정변경사유 입력 화면에서 결재요청 후 화면 처리
	 * 
	 * @param res
	 * @param msg
	 * @param url
	 * @param cmd
	 * @param mode
	 *            작성자 : BoLee 작성일자 : 2013. 8. 23.
	 */
	protected void alertNRefreshProjectPopupParentPage(HttpServletResponse res, String msg, String url, String cmd, String mode) {
		try {

			res.setContentType("text/html;charset=UTF-8");
			PrintWriter out = res.getWriter();
			String rtn_msg = "";

			rtn_msg = "\n <script language=\"javascript\">" + "\n   alert(\"" + replaceMsg(msg) + "\");";

			if ("ChangeSchedule".equals(mode)) {

				if ("start".equals(cmd)) {
					rtn_msg += "\n   if ( opener.opener.parent.parent.opener.parent != null ) opener.opener.parent.parent.opener.parent.document.location.href = opener.opener.parent.parent.opener.parent.document.location.href;"
							+ "\n   opener.opener.parent.parent.document.location.href ='" + url + "';" + "\n   opener.close();";
				}
			} else {
				rtn_msg += "\n   if ( opener.parent.opener != null ) opener.parent.opener.document.location.href = opener.parent.opener.document.location.href;"
						+ "\n   opener.parent.document.location.href ='" + url + "';";
			}

			rtn_msg += "\n   window.close();" + "\n </script>";

			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
}
