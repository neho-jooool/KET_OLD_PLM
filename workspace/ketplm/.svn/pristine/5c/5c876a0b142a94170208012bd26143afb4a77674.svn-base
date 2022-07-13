package e3ps.common.version;

/**
 * @(#) PasswordChangeServlet.java Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.6.18
 * @author Oh Myung Jae, mjoh@lgcns.com
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeOrder2;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.WTObject;
import wt.httpgw.WTContextBean;
import wt.lifecycle.State;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.util.WTException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.edm.EDMUserData;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.VersionHelper;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.edm.entity.KETEpmApprovalHis;
import ext.ket.part.base.service.internal.PartBaseDao;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.log.Kogger;

public class VersionHistoryServlet extends CommonServlet {
    private static final long serialVersionUID = 1L;

    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	WTContextBean wtcontextbean = new WTContextBean();
	wtcontextbean.setRequest(req);

	ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
	String oid = ParamUtil.getStrParameter(req.getParameter("oid"));

	RevisionControlled rc = null;
	String version = "";
	String department = "";
	String creator = "";
	String createdDate = "";
	String approver = "";
	String description = "";
	String url = "";

	try {
	    ArrayList<RevisionControlled> list = KETCommonHelper.service.getVersionHistory(oid);

	    if (list.size() > 0) {
		for (int i = 0; i < list.size(); i++) {
		    HashMap<String, String> tempMap = new HashMap<String, String>();
		    rc = list.get(i);

		    if (oid != null && oid.indexOf("WTPart") != -1) {
			version = StringUtil.checkNull(PartSpecGetter.getPartSpec((WTPart) rc, PartSpecEnum.SpPartRevision));
		    } else if (oid != null && oid.indexOf("EPMDocument") != -1) {
			HashMap ibaValues = EDMAttributes.getIBAValues((EPMDocument) rc, Locale.KOREAN);
			version = StringUtil.checkNull((String) ibaValues.get(EDMHelper.IBA_MANUFACTURING_VERSION));
		    } else if (oid != null && oid.indexOf("KETSalesProject") != -1) {
			KETSalesProject salesPjt = (KETSalesProject) rc;

			version = salesPjt.getVersionInfo().getIdentifier().getValue() + "."
			        + salesPjt.getIterationInfo().getIdentifier().getValue();

		    } else {
			version = VersionControlHelper.getVersionIdentifier(rc).getSeries().getValue();
		    }
		    department = getDepartment(rc);
		    creator = rc.getCreatorFullName();
		    createdDate = DateUtil.getDateString(rc.getPersistInfo().getCreateStamp(), "D");
		    description = getDescription(rc);

		    if (State.toState("APPROVED") == rc.getLifeCycleState()) {

			Persistable pbo = null;

			if (rc instanceof EPMDocument) {

			    EPMDocument epm = (EPMDocument) rc;
			    KETEpmApprovalHis history = ext.ket.edm.approval.KetEpmApprovalHelper.service.getApprovalHis(epm); // getSavedPbo
				                                                                                               // getPbo
			    approver = (history == null) ? "-" : history.getApproverName();

			} else if (rc instanceof WTPart) {

			    PartBaseDao partBaseDao = new PartBaseDao();
			    long branchId = VersionControlHelper.getBranchIdentifier((WTPart) rc);
			    String ecoNo = partBaseDao.searchEONo(branchId);
			    if (StringUtils.isNotEmpty(ecoNo)) {

				ProdEcoBeans beans = new ProdEcoBeans();
				WTChangeOrder2 eco = beans.getEcoByNo(ecoNo);
				if (eco != null) {
				    WTUser approverUser = WorkflowSearchHelper.manager.getLastApprover(eco);
				    if (approverUser == null) {
					approver = "-";
				    } else {
					approver = approverUser.getFullName();
				    }
				} else {
				    approver = "-";
				}
			    } else {
				approver = "-";
			    }

			} else {

			    pbo = KETCommonHelper.service.getPBO(rc);

			    if (pbo != null) {
				WTUser approverUser = WorkflowSearchHelper.manager.getLastApprover(pbo);
				if (approverUser == null) {
				    approver = "-";
				} else {
				    approver = approverUser.getFullName();
				}
			    } else {
				approver = "-";
			    }
			}

		    } else {
			if (rc instanceof KETSalesProject) {

			    Versioned vs = (Versioned) rc;
					
			    vs = VersionHelper.getLatestRevision(vs);
			    KETSalesProject salesProject = (KETSalesProject)vs;
			    if (State.toState("APPROVED") == salesProject.getLifeCycleState()) {
				Persistable pbo = KETCommonHelper.service.getPBO(salesProject);

				if (pbo != null) {
				    WTUser approverUser = WorkflowSearchHelper.manager.getLastApprover(pbo);
				    if (approverUser == null) {
					approver = "-";
				    } else {
					approver = approverUser.getFullName();
				    }
				} else {
				    approver = "-";
				}
			    }

			} else {
			    approver = "미승인상태";
			}

		    }

		    url = getDetailInfoURL(rc);

		    tempMap.put("version", KETStringUtil.nvl(version, "-"));
		    tempMap.put("department", KETStringUtil.nvl(department, "-"));
		    tempMap.put("creator", KETStringUtil.nvl(creator, "-"));
		    tempMap.put("createdDate", KETStringUtil.nvl(createdDate, "-"));
		    tempMap.put("approver", KETStringUtil.nvl(approver, "-"));
		    tempMap.put("description", KETStringUtil.nvl(description, "-"));
		    tempMap.put("url", KETStringUtil.nvl(url, "-"));

		    resultList.add(tempMap);
		}
	    }

	    req.setAttribute("resultData", resultList);
	    gotoResult(req, res, "/jsp/common/ViewVersionHistoryPopup.jsp");
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    private String getDepartment(WTObject obj) throws Exception {
	String returnStr = "-";

	if (obj instanceof KETProjectDocument) // 프로젝트 산출문서
	{
	    returnStr = ((KETProjectDocument) obj).getDeptName();
	} else if (obj instanceof KETTechnicalDocument) // 기술문서
	{
	    returnStr = ((KETTechnicalDocument) obj).getDeptName();
	} else if (obj instanceof EPMDocument) // 도면
	{
	    EDMUserData ud = EDMHelper.getEDMUserData((EPMDocument) obj);

	    if ((ud != null) && (ud.getCreatorDeptName() != null)) {
		returnStr = ud.getCreatorDeptName();
	    }
	} else if (obj instanceof WTPart) // 부품
	{
	    WTPart part = (WTPart) obj;
	    WTUser wtuser = (WTUser) part.getIterationInfo().getCreator().getPrincipal();
	    Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	    if (userDept != null)
		returnStr = userDept.getName();
	} else if (obj instanceof KETEarlyWarning) // 초기유동관리지정
	{
	    returnStr = "미관리";
	} else if (obj instanceof KETEarlyWarningPlan) // 초기유동계획서
	{
	    returnStr = "미관리";
	} else if (obj instanceof KETSalesProject) // 영업프로젝트
	{
	    KETSalesProject salesPjt = (KETSalesProject) obj;
	    WTUser wtuser = (WTUser) salesPjt.getIterationInfo().getCreator().getPrincipal();
	    Department userDept = DepartmentHelper.manager.getDepartment(wtuser);
	    if (userDept != null)
		returnStr = userDept.getName();
	}

	return returnStr;
    }

    private String getDescription(RevisionControlled obj) throws WTException {
	String returnStr = "-";

	if (obj instanceof KETProjectDocument) // 프로젝트 산출문서
	{
	    returnStr = ((KETProjectDocument) obj).getDescription();
	} else if (obj instanceof KETTechnicalDocument) // 기술문서
	{
	    returnStr = ((KETTechnicalDocument) obj).getDescription();
	} else if (obj instanceof EPMDocument) // 도면
	{
	    returnStr = ((EPMDocument) obj).getDescription();
	} else if (obj instanceof WTPart) // 부품
	{
	    returnStr = "미관리";
	} else if (obj instanceof KETEarlyWarning) // 초기유동관리지정
	{
	    returnStr = ((KETEarlyWarning) obj).getDescription();
	} else if (obj instanceof KETEarlyWarningPlan) // 초기유동계획서
	{
	    returnStr = ((KETEarlyWarningPlan) obj).getDescription();
	}

	return returnStr;
    }

    private String getDetailInfoURL(RevisionControlled obj) throws WTException {
	StringBuffer strBuffer = new StringBuffer();

	if (obj instanceof KETProjectDocument) // 프로젝트 산출문서
	{
	    strBuffer.append("javascript:openWindow3(");
	    strBuffer.append("'/plm/jsp/dms/ViewDocumentPop.jsp?oid=").append(KETObjectUtil.getOidString(obj)).append("',");
	    strBuffer.append("'프로젝트산출문서상세정보',");
	    strBuffer.append("'820',");
	    strBuffer.append("'640')");
	} else if (obj instanceof KETTechnicalDocument) // 기술문서
	{
	    strBuffer.append("javascript:openWindow3(");
	    strBuffer.append("'/plm/jsp/dms/ViewTechDocumentPop.jsp?oid=").append(KETObjectUtil.getOidString(obj)).append("',");
	    strBuffer.append("'기술문서상세정보',");
	    strBuffer.append("'810',");
	    strBuffer.append("'400')");
	} else if (obj instanceof EPMDocument) // 도면
	{
	    strBuffer.append("javascript:openWindow3(");
	    strBuffer.append("'/plm/jsp/edm/ViewEPMDocument.jsp?oid=").append(KETObjectUtil.getOidString(obj)).append("',");
	    strBuffer.append("'도면상세정보',");
	    strBuffer.append("'820',");
	    strBuffer.append("'800')");
	} else if (obj instanceof WTPart) // 부품
	{
	    strBuffer.append("javascript:openWindow3(");
	    strBuffer.append("'/plm/jsp/bom/ViewPart.jsp?poid=").append(KETObjectUtil.getOidString(obj)).append("',");
	    strBuffer.append("'부품상세정보',");
	    strBuffer.append("'1150',");
	    strBuffer.append("'800')");
	} else if (obj instanceof KETEarlyWarning) // 초기유동관리지정
	{
	    strBuffer.append("javascript:openWindow3(");
	    strBuffer.append("'/plm/jsp/ews/ViewEarlyWarningPopup.jsp?oid=").append(KETObjectUtil.getOidString(obj)).append("',");
	    strBuffer.append("'초기유동관리지정상세정보',");
	    strBuffer.append("'830',");
	    strBuffer.append("'600')");
	} else if (obj instanceof KETEarlyWarningPlan) // 초기유동계획서
	{
	    strBuffer.append("javascript:openWindow3(");
	    strBuffer.append("'/plm/jsp/ews/ViewEarlyWarningPlanPopup.jsp?planOid=").append(KETObjectUtil.getOidString(obj)).append("',");
	    strBuffer.append("'초기유동계획서상세정보',");
	    strBuffer.append("'830',");
	    strBuffer.append("'600')");
	} else if (obj instanceof KETSalesProject) {
	    strBuffer.append("javascript:openWindow3(");
	    strBuffer.append("'/plm/ext/sales/project/viewSalesProjectForm.do?oid=").append(KETObjectUtil.getOidString(obj)).append("',");
	    strBuffer.append("'영업프로젝트상세정보',");
	    strBuffer.append("'1150',");
	    strBuffer.append("'800')");
	}

	return strBuffer.toString();
    }
}
