/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package e3ps.ecm.service;

import java.io.Serializable;
import java.sql.Connection; // Preserved unmodeled dependency
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Preserved unmodeled dependency
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import wt.change2.ChangeNoticeComplexity;
import wt.change2.WTChangeOrder2;
import wt.change2.WTChangeRequest2;
import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.enterprise.Managed;
import wt.enterprise.Master;
import wt.enterprise.RevisionControlled;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleServerHelper;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.lifecycle.State;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.part.WTPartMaster;
import wt.pom.Transaction;
import wt.pom.WTConnection;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.services.ManagerException;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.VersionControlHelper;
import wt.vc.Versioned;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfTemplateObject;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.code.NumberCode;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.VersionUtil;
import e3ps.common.util.WCUtil;
import e3ps.cost.util.DBUtil;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETDocumentOutputLink;
import e3ps.dms.entity.KETDocumentPartLink;
import e3ps.dms.entity.KETDocumentProjectLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.ecm.beans.ECMProperties;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.beans.MoldEcoBeans;
import e3ps.ecm.beans.MoldEcrBeans;
import e3ps.ecm.beans.MoldPlanBeans;
import e3ps.ecm.beans.MoldStdPartBeans;
import e3ps.ecm.beans.ProdEcoBeans;
import e3ps.ecm.beans.ProdEcrBeans;
import e3ps.ecm.dao.EcmComDao; // Preserved unmodeled dependency
import e3ps.ecm.dao.MoldChangeRequestDao;
import e3ps.ecm.dao.ProdEcoDao;
import e3ps.ecm.entity.KETChangeActivity;
import e3ps.ecm.entity.KETChangeNotice;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETEcnEcaLink;
import e3ps.ecm.entity.KETEcnWeightHistory;
import e3ps.ecm.entity.KETEcoDqmLink;
import e3ps.ecm.entity.KETEcoEcnLink;
import e3ps.ecm.entity.KETEcrCallLink;
import e3ps.ecm.entity.KETEcrCompetentLink;
import e3ps.ecm.entity.KETEcrDqmLink;
import e3ps.ecm.entity.KETEcrEcnLink;
import e3ps.ecm.entity.KETEcrMeetingLink;
import e3ps.ecm.entity.KETMeetingCall;
import e3ps.ecm.entity.KETMeetingMinutes;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeOrderVO;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldChangeRequestVO;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETMoldECADocLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldECAEpmDocLink;
import e3ps.ecm.entity.KETMoldECALinkVO; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldPlanRefDocLink;
import e3ps.ecm.entity.KETMoldStdPartLine; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETMoldStdPartLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeActivity; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeActivityLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeOrder; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.entity.KETProdECABomLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdECADocLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdECAEpmDocLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdECOPartLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdECOPlanLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdECRIssueLink; // Preserved unmodeled dependency
import e3ps.ecm.entity.KETProdECRPartLink; // Preserved unmodeled dependency
import e3ps.ecm.servlet.MoldEcoChangeServlet;
import e3ps.edm.beans.EDMHelper;
import e3ps.edm.util.EDMAttributes;
import e3ps.edm.util.EDMEnumeratedTypeUtil;
import e3ps.edm.util.VersionHelper;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.PeopleData;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.ProjectIssue;
import e3ps.project.ProjectOutput;
import e3ps.project.TemplateProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectUserHelper;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.service.KETWfmHelper;
import e3ps.wfm.util.WFMProperties;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.bom.util.KETBomPartUtil;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.edm.service.base.EpmBaseHelper;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.base.service.internal.RelatedEpmHandler;
import ext.ket.part.entity.dto.PartValidationDTO;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.shared.log.KogDbUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
// Preserved unmodeled dependency
// Preserved unmodeled dependency
// Preserved unmodeled dependency
import ext.ket.wfm.service.KETWorkspaceHelper;

/**
 * 
 * <p>
 * Use the <code>newStandardKETEcmService</code> static factory method(s), not the <code>StandardKETEcmService</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

public class StandardKETEcmService extends StandardManager implements KETEcmService, Serializable {

    private static final String RESOURCE = "e3ps.ecm.service.serviceResource";
    private static final String CLASSNAME = StandardKETEcmService.class.getName();

    /**
     * Returns the conceptual (modeled) name for the class.
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated
     * 
     * @return String
     **/
    public String getConceptualClassname() {

	return CLASSNAME;
    }

    /**
     * @exception wt.services.ManagerException
     **/
    @Override
    protected void performStartupProcess() throws ManagerException {

	super.performStartupProcess();

    }

    /**
     * Default factory for the class.
     * 
     * @return StandardKETEcmService
     * @exception wt.util.WTException
     **/
    public static StandardKETEcmService newStandardKETEcmService() throws WTException {

	StandardKETEcmService instance = new StandardKETEcmService();
	instance.initialize();
	return instance;
    }

    @Override
    public String createMoldPlan(Hashtable<String, String> moldPlan, KETProjectDocument[] refDocs, Vector attachFiles) throws WTException {
	String planOid = "";

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    KETMoldChangePlan plan = KETMoldChangePlan.newKETMoldChangePlan();

	    plan.setScheduleId(moldPlan.get("scheduleId"));
	    plan.setDieNo(moldPlan.get("dieNo"));
	    plan.setPartNo(moldPlan.get("partNo"));
	    plan.setPartName(moldPlan.get("partName"));

	    plan.setPlanType(moldPlan.get("type"));
	    plan.setProdDeptName(moldPlan.get("prodDeptName"));
	    plan.setVendorFlag(moldPlan.get("vendorFlag"));
	    plan.setVendorCode(moldPlan.get("vendorCode"));
	    plan.setRegBasis(moldPlan.get("regBasis"));
	    plan.setBasisDate(DateUtil.convertDate2(moldPlan.get("basisDate")));

	    plan.setProdEcoOwner((WTUser) KETObjectUtil.getObject(moldPlan.get("prodEcoOwnerOid")));
	    plan.setMoldEcoOwner((WTUser) KETObjectUtil.getObject(moldPlan.get("moldEcoOwnerOid")));

	    plan.setModifyDesc(moldPlan.get("modifyDesc"));

	    plan.setProdDwgPlanDate(DateUtil.convertDate2(moldPlan.get("prodPlanDate")));
	    plan.setMoldChangePlanDate(DateUtil.convertDate2(moldPlan.get("moldEcoPlanDate")));
	    plan.setStorePlanDate(DateUtil.convertDate2(moldPlan.get("storePlanDate")));
	    plan.setWorkPlanDate(DateUtil.convertDate2(moldPlan.get("workPlanDate")));
	    plan.setAssemblePlanDate(DateUtil.convertDate2(moldPlan.get("assPlanDate")));
	    plan.setTryPlanDate(DateUtil.convertDate2(moldPlan.get("tryPlanDate")));
	    plan.setTestPlanDate(DateUtil.convertDate2(moldPlan.get("testPlanDate")));
	    plan.setApprovePlanDate(DateUtil.convertDate2(moldPlan.get("approvePlanDate")));
	    plan.setPlanDesc(moldPlan.get("planDesc"));

	    plan.setOwner(SessionHelper.manager.getPrincipalReference());
	    PeopleData pData = new PeopleData();
	    plan.setDeptName(pData.departmentName);

	    plan.setMeasureType(moldPlan.get("measureType"));
	    plan.setFailAction(moldPlan.get("failAction"));
	    plan.setResult(moldPlan.get("result"));
	    plan.setMeasureDate(DateUtil.convertDate2(moldPlan.get("measureDate")));
	    plan.setAttribute1(moldPlan.get("ATTRIBUTE1"));

	    plan = (KETMoldChangePlan) PersistenceHelper.manager.save(plan);

	    // KETProjectDocument Relation
	    KETMoldPlanRefDocLink docRef = null;
	    if (refDocs != null && refDocs.length > 0) {
		for (int dCnt = 0; dCnt < refDocs.length; dCnt++) {
		    docRef = KETMoldPlanRefDocLink.newKETMoldPlanRefDocLink(plan, refDocs[dCnt]);
		    PersistenceHelper.manager.save(docRef);
		}
	    }

	    // Attachfile Relation
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    plan = (KETMoldChangePlan) E3PSContentHelper.service.attach((ContentHolder) plan, (WBFile) attachFiles.get(fileCnt),
			    "", ContentRoleType.SECONDARY);
		}
	    }

	    planOid = CommonUtil.getOIDString(plan);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return planOid;

    }

    @Override
    public String modifyMoldPlan(Hashtable<String, String> moldPlan, KETProjectDocument[] refDocs, Vector attachFiles) throws WTException {
	String planOid = "";
	String currentProcess = "";
	KETMoldChangePlan plan = null;
	KETMoldPlanRefDocLink delDocLink = null;
	KETMoldPlanRefDocLink docLink = null;
	MoldPlanBeans beans = new MoldPlanBeans();

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    ReferenceFactory rf = new ReferenceFactory();
	    plan = (KETMoldChangePlan) rf.getReference(moldPlan.get("oid")).getObject();

	    plan.setPartNo(moldPlan.get("partNo"));
	    plan.setPartName(moldPlan.get("partName"));

	    plan.setPlanType(moldPlan.get("type"));
	    plan.setProdDeptName(moldPlan.get("prodDeptName"));
	    plan.setVendorFlag(moldPlan.get("vendorFlag"));
	    plan.setVendorCode(moldPlan.get("vendorCode"));
	    plan.setRegBasis(moldPlan.get("regBasis"));
	    plan.setBasisDate(DateUtil.convertDate2(moldPlan.get("basisDate")));

	    plan.setProdEcoOwner((WTUser) KETObjectUtil.getObject(moldPlan.get("prodEcoOwnerOid")));
	    plan.setMoldEcoOwner((WTUser) KETObjectUtil.getObject(moldPlan.get("moldEcoOwnerOid")));

	    plan.setModifyDesc(moldPlan.get("modifyDesc"));

	    plan.setProdDwgPlanDate(DateUtil.convertDate2(moldPlan.get("prodPlanDate")));
	    plan.setProdDwgActualDate(DateUtil.convertDate2(moldPlan.get("prodActualDate")));
	    plan.setMoldChangePlanDate(DateUtil.convertDate2(moldPlan.get("moldEcoPlanDate")));
	    plan.setMoldChangeActualDate(DateUtil.convertDate2(moldPlan.get("moldEcoActualDate")));
	    plan.setStorePlanDate(DateUtil.convertDate2(moldPlan.get("storePlanDate")));
	    plan.setStoreActualDate(DateUtil.convertDate2(moldPlan.get("storeActualDate")));
	    plan.setWorkPlanDate(DateUtil.convertDate2(moldPlan.get("workPlanDate")));
	    plan.setWorkActualDate(DateUtil.convertDate2(moldPlan.get("workActualDate")));
	    plan.setAssemblePlanDate(DateUtil.convertDate2(moldPlan.get("assPlanDate")));
	    plan.setAssembleActualDate(DateUtil.convertDate2(moldPlan.get("assActualDate")));
	    plan.setTryPlanDate(DateUtil.convertDate2(moldPlan.get("tryPlanDate")));
	    plan.setTryActualDate(DateUtil.convertDate2(moldPlan.get("tryActualDate")));
	    plan.setTestPlanDate(DateUtil.convertDate2(moldPlan.get("testPlanDate")));
	    plan.setTestActualDate(DateUtil.convertDate2(moldPlan.get("testActualDate")));
	    plan.setApprovePlanDate(DateUtil.convertDate2(moldPlan.get("approvePlanDate")));
	    plan.setApproveActualDate(DateUtil.convertDate2(moldPlan.get("approveActualDate")));
	    plan.setPlanDesc(StringUtil.checkNull(moldPlan.get("planDesc")));

	    plan.setAttribute1(moldPlan.get("m_customer"));
	    plan.setAttribute2(moldPlan.get("m_date"));

	    currentProcess = beans.getCurrentProcess(plan);
	    plan.setCurrentProcess(currentProcess);
	    plan.setCurrentProcPlanDate(beans.getCurrentProcPlanDate(plan, currentProcess));
	    plan.setScheduleStatus(beans.getCurrentPlanStatus(plan));
	    if (plan.getScheduleStatus().equals("C")) {
		plan.setCurrentProcess("");
		plan.setCurrentProcPlanDate(DateUtil.getCurrentTimestamp());
	    }

	    plan.setMeasureType(moldPlan.get("measureType"));
	    plan.setFailAction(moldPlan.get("failAction"));
	    plan.setResult(moldPlan.get("result"));
	    plan.setMeasureDate(DateUtil.convertDate2(moldPlan.get("measureDate")));

	    plan = (KETMoldChangePlan) PersistenceHelper.manager.modify(plan);

	    // Delete All RelatedDocumentList
	    QueryResult qr = PersistenceHelper.manager.navigate(plan, "refDoc", KETMoldPlanRefDocLink.class, false);

	    while (qr.hasMoreElements()) {
		delDocLink = (KETMoldPlanRefDocLink) qr.nextElement();
		PersistenceHelper.manager.delete(delDocLink);
	    }

	    // Add All RelatedDocumentList
	    if (refDocs != null && refDocs.length > 0) {
		for (int dCnt = 0; dCnt < refDocs.length; dCnt++) {
		    docLink = KETMoldPlanRefDocLink.newKETMoldPlanRefDocLink(plan, refDocs[dCnt]);
		    PersistenceHelper.manager.save(docLink);
		}
	    }

	    // Delete AttachFile
	    if (moldPlan.get("delFileList").length() > 0) {
		String strDelFileList = moldPlan.get("delFileList");

		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}

		QueryResult rs = ContentHelper.service.getContentsByRole(plan, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String itemStr = allObj.toString();
		    for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			if (delFileList.get(delFileCnt).equals(itemStr)) {
			    plan = (KETMoldChangePlan) E3PSContentHelper.service.delete(plan, allContentItem);
			}
		    }
		}
	    }

	    // Add AttachFile
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    plan = (KETMoldChangePlan) E3PSContentHelper.service.attach((ContentHolder) plan, (WBFile) attachFiles.get(fileCnt),
			    "", ContentRoleType.SECONDARY);
		}
	    }

	    plan = (KETMoldChangePlan) PersistenceHelper.manager.refresh(plan);

	    planOid = CommonUtil.getOIDString(plan);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    trx = null;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return planOid;
    }

    @Override
    public KETMoldChangeOrderVO createMoldEcoBasicInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	return null;
    }

    @Override
    public KETMoldChangeOrderVO createMoldEcoActivitycInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {

	return null;
    }

    @Override
    public KETMoldChangeOrderVO createMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    MoldEcoBeans moldEcoBeans = new MoldEcoBeans();
	    ketMoldChangeOrderVO = moldEcoBeans.createMoldEcoInfo(ketMoldChangeOrderVO);
	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    transaction = null;
	}

	return ketMoldChangeOrderVO;
    }

    @Override
    public KETMoldChangeOrderVO updateMoldEcoBasicInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
	// Transaction transaction = new Transaction();
	try {
	    // transaction.start();
	    LifeCycleServerHelper.service.setState((LifeCycleManaged) ketMoldChangeOrderVO.getKetMoldChangeOrder(),
		    State.toState("EXCUTEACTIVITY"));
	    // transaction.commit();
	} catch (Exception e) {
	    // if(transaction != null) {
	    // transaction.rollback();
	    // }
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    // transaction = null;
	}

	return ketMoldChangeOrderVO;
    }

    @Override
    public KETMoldChangeOrderVO updateMoldEcoActivityInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    MoldEcoBeans moldEcoBeans = new MoldEcoBeans();
	    moldEcoBeans.deleteMoldEcaInfo(ketMoldChangeOrderVO);
	    moldEcoBeans.deleteMoldPlanInfo(ketMoldChangeOrderVO);
	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    transaction = null;
	}

	return ketMoldChangeOrderVO;
    }

    @Override
    public KETMoldChangeOrderVO updateMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    MoldEcoBeans moldEcoBeans = new MoldEcoBeans();
	    ketMoldChangeOrderVO = moldEcoBeans.updateMoldEcoInfo(ketMoldChangeOrderVO);
	    transaction.commit();
	} catch (WTException wte) {
	    if (transaction != null) {
		transaction.rollback();
	    }

	    Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }

	    Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    transaction = null;
	}
	return ketMoldChangeOrderVO;
    }

    @Override
    public int deleteMoldEcoInfo(KETMoldChangeOrderVO ketMoldChangeOrderVO) throws WTException {
	Transaction transaction = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    transaction.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    conn.setAutoCommit(false);

	    WTPrincipal session = null;
	    // Set temporary Session - Administrator
	    session = SessionHelper.manager.getPrincipal();
	    SessionHelper.manager.setAdministrator();

	    MoldEcoBeans moldEcoBeans = new MoldEcoBeans();
	    int rtn = moldEcoBeans.deleteMoldEcoInfo(ketMoldChangeOrderVO, conn);

	    SessionHelper.manager.setPrincipal(session.getName());

	    if (rtn > 0) {
		conn.commit();
	    } else {
		conn.rollback();
	    }

	    transaction.commit();
	    transaction = null;
	} catch (Exception e) {
	    try {
		if (conn != null)
		    conn.rollback();
	    } catch (Exception e1) {
		Kogger.error(getClass(), e1);
	    }

	    transaction.rollback();
	    transaction = null;

	    Kogger.error(getClass(), e);

	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (transaction != null) {
		transaction.rollback();
		transaction = null;
	    }
	}
	return 0;
    }

    @Override
    public KETMoldChangePlan getMoldPlan(String planOid) throws WTException {
	ReferenceFactory rf = new ReferenceFactory();
	KETMoldChangePlan plan = (KETMoldChangePlan) rf.getReference(planOid).getObject();

	return plan;
    }

    @Override
    public boolean deleteMoldPlan(String planOid) throws WTException {

	boolean isSuccess = false;
	Transaction trx = new Transaction();

	try {
	    trx.start();

	    MoldPlanBeans beans = new MoldPlanBeans();

	    if (planOid != null) {
		ReferenceFactory rf = new ReferenceFactory();
		KETMoldChangePlan plan = (KETMoldChangePlan) rf.getReference(planOid).getObject();

		beans.modifyMoldEpmDocLinkPlanId(plan.getScheduleId(), "");
		beans.modifyProdEpmDocLinkPlanId(plan.getScheduleId(), "");

		PersistenceHelper.manager.delete(plan);

		isSuccess = true;
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    trx.rollback();
	    trx = null;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return isSuccess;
    }

    @Override
    public KETMoldChangeRequestVO createMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    MoldEcrBeans moldEcrBeans = new MoldEcrBeans();
	    ketMoldChangeRequestVO = moldEcrBeans.createMoldEcrInfo(ketMoldChangeRequestVO);

	    // 제품 ECR 확장팩
	    KETMoldChangeRequest moldEcr = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	    Hashtable<String, String> reqData = ketMoldChangeRequestVO.getReqData();
	    this.modifyEcrExpansion(moldEcr, reqData);

	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    transaction = null;
	}

	return ketMoldChangeRequestVO;
    }

    @Override
    public KETMoldChangeRequestVO updateMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    MoldEcrBeans moldEcrBeans = new MoldEcrBeans();
	    ketMoldChangeRequestVO = moldEcrBeans.updateMoldEcrInfo(ketMoldChangeRequestVO);

	    // 제품 ECR 확장팩
	    KETMoldChangeRequest moldEcr = ketMoldChangeRequestVO.getKetMoldChangeRequest();
	    Hashtable<String, String> reqData = ketMoldChangeRequestVO.getReqData();
	    this.modifyEcrExpansion(moldEcr, reqData);

	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    Kogger.error(getClass(), e);
	    throw new WTException(e);
	} finally {
	    transaction = null;
	}
	return ketMoldChangeRequestVO;
    }

    @Override
    public int deleteMoldEcrInfo(KETMoldChangeRequestVO ketMoldChangeRequestVO) throws WTException {
	Transaction transaction = new Transaction();
	try {
	    transaction.start();
	    MoldEcrBeans moldEcrBeans = new MoldEcrBeans();
	    moldEcrBeans.deleteMoldEcrInfo(ketMoldChangeRequestVO);
	    transaction.commit();
	} catch (Exception e) {
	    if (transaction != null) {
		transaction.rollback();
	    }
	    throw new WTException(e);
	} finally {
	    transaction = null;
	}
	return 0;
    }

    @Override
    @Deprecated
    public String createProdEcr(Hashtable<String, String> reqData, String[] partList, String[] reqCommentList, String[] issueList,
	    Vector attachFiles) throws WTException {
	String ecrOid = "";

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    WTPart[] refParts = null;
	    ProjectIssue[] refIssues = null;

	    KETProdChangeRequest prodEcr = KETProdChangeRequest.newKETProdChangeRequest();

	    prodEcr.setEcrId(reqData.get("ecr_id"));
	    prodEcr.setEcrName(reqData.get("ecr_title"));
	    prodEcr.setDevYn(reqData.get("dev_yn"));
	    prodEcr.setDivisionFlag(reqData.get("div_flag"));
	    prodEcr.setProjectOid(reqData.get("project_oid"));
	    // prodEcr.setChangeType( reqData.get( "chg_type" ) );
	    prodEcr.setChangeReason(reqData.get("chg_reason"));
	    prodEcr.setOtherChangeReasonDesc(reqData.get("other_reason"));
	    prodEcr.setEcrDesc(reqData.get("ecr_desc"));
	    prodEcr.setExpectEffect(reqData.get("ecr_effect"));

	    PeopleData pData = new PeopleData();
	    prodEcr.setDeptName(pData.departmentName);// ??? ???????? ???

	    // [START] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현
	    // ecrPartLink.setChangeReqComment( reqCommentList[pCnt] );
	    prodEcr.setName(prodEcr.getEcrName());
	    // [END] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.ecr");
	    LifeCycleHelper.setLifeCycle(prodEcr, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    // Folder Setting
	    String folderPath = "";
	    if (prodEcr.getDivisionFlag().equals("C")) {
		// ?????????
		folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
	    } else if (prodEcr.getDivisionFlag().equals("K")) {
		folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");// KETS
	    } else {
		// ????????
		folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
	    }

	    // folderPath += DateUtil.getThisYear();
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) prodEcr, folder);

	    prodEcr = (KETProdChangeRequest) PersistenceHelper.manager.save(prodEcr);

	    // WTPart Relation
	    ReferenceFactory rfTmp = new ReferenceFactory();
	    refParts = new WTPart[partList.length];

	    for (int i = 0; i < partList.length; i++) {
		refParts[i] = (WTPart) rfTmp.getReference(partList[i]).getObject();
	    }

	    KETProdECRPartLink partRef = null;
	    if (refParts != null) {
		for (int pCnt = 0; pCnt < refParts.length; pCnt++) {
		    partRef = KETProdECRPartLink.newKETProdECRPartLink(refParts[pCnt], prodEcr);
		    if (reqCommentList != null && reqCommentList.length > 0)
			partRef.setChangeReqComment(reqCommentList[pCnt]);

		    PersistenceHelper.manager.save(partRef);
		}
	    }

	    // ProjectIssue Relation
	    if (issueList != null && issueList.length > 0) {
		refIssues = new ProjectIssue[issueList.length];

		for (int i = 0; i < issueList.length; i++) {
		    refIssues[i] = (ProjectIssue) rfTmp.getReference(issueList[i]).getObject();
		}

		KETProdECRIssueLink issueRef = null;
		if (refIssues != null) {
		    for (int iCnt = 0; iCnt < refIssues.length; iCnt++) {
			issueRef = KETProdECRIssueLink.newKETProdECRIssueLink(refIssues[iCnt], prodEcr);
			PersistenceHelper.manager.save(issueRef);
		    }
		}
	    }

	    // Attachfile Relation
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    prodEcr = (KETProdChangeRequest) E3PSContentHelper.service.attach((ContentHolder) prodEcr,
			    (WBFile) attachFiles.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    // 제품 ECR 확장팩
	    this.modifyEcrExpansion(prodEcr, reqData);

	    ecrOid = CommonUtil.getOIDString(prodEcr);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ecrOid;
    }

    @Override
    @Deprecated
    public String modifyProdEcr(Hashtable<String, String> reqData, Hashtable partHash, Hashtable issueHash, Vector attachFiles)
	    throws WTException {
	String ecrOid = "";
	KETProdChangeRequest prodEcr = null;
	String[] partList = null;
	ArrayList<Hashtable<String, String>> delPartLinkList = null;
	String[] reqCommentList = null;

	KETProdECRPartLink ecrPartLink = null;
	WTPart[] refParts = null;

	String[] issueList = null;
	ArrayList<KETProdECRIssueLink> delIssueLinkList = null;
	ProjectIssue[] refIssues = null;

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    ReferenceFactory rf = new ReferenceFactory();
	    prodEcr = (KETProdChangeRequest) rf.getReference(reqData.get("oid")).getObject();

	    prodEcr.setEcrName(reqData.get("ecr_title"));
	    prodEcr.setDevYn(reqData.get("dev_yn"));
	    prodEcr.setDivisionFlag(reqData.get("div_flag"));
	    prodEcr.setProjectOid(reqData.get("project_oid"));
	    // prodEcr.setChangeType( reqData.get( "chg_type" ) );
	    prodEcr.setChangeReason(reqData.get("chg_reason"));
	    prodEcr.setOtherChangeReasonDesc(reqData.get("other_reason"));
	    prodEcr.setEcrDesc(reqData.get("ecr_desc"));
	    prodEcr.setExpectEffect(reqData.get("ecr_effect"));

	    prodEcr = (KETProdChangeRequest) PersistenceHelper.manager.modify(prodEcr);

	    partList = (String[]) partHash.get("partList");

	    delPartLinkList = (ArrayList<Hashtable<String, String>>) partHash.get("delPartLinkList");

	    reqCommentList = (String[]) partHash.get("reqCommentList");

	    // Delete All RelatedPartList
	    Hashtable<String, String> partLink = null;
	    for (int dPartCnt = 0; dPartCnt < delPartLinkList.size(); dPartCnt++) {
		partLink = delPartLinkList.get(dPartCnt);

		ecrPartLink = (KETProdECRPartLink) CommonUtil.getObject(partLink.get("partlink_oid"));
		PersistenceHelper.manager.delete(ecrPartLink);
	    }

	    // Add All RelatedPartList
	    ReferenceFactory rfTmp = new ReferenceFactory();
	    refParts = new WTPart[partList.length];

	    for (int pCnt = 0; pCnt < partList.length; pCnt++) {
		refParts[pCnt] = (WTPart) rfTmp.getReference(partList[pCnt]).getObject();
	    }

	    if (refParts != null) {
		for (int pCnt = 0; pCnt < refParts.length; pCnt++) {
		    ecrPartLink = KETProdECRPartLink.newKETProdECRPartLink(refParts[pCnt], prodEcr);

		    // [START] [PLM System 1차 고도화] 관련부품의 요청사항을 입력하지 않을 경우를 대비한 방어코드, 2014-06-16, 김태현
		    // ecrPartLink.setChangeReqComment( reqCommentList[pCnt] );
		    try {
			ecrPartLink.setChangeReqComment(reqCommentList[pCnt]);
		    } catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
			Kogger.error(getClass(), aioobe);
		    }
		    // [END] [PLM System 1차 고도화] 관련부품의 요청사항을 입력하지 않을 경우를 대비한 방어코드, 2014-06-16, 김태현

		    PersistenceHelper.manager.save(ecrPartLink);
		}
	    }

	    delIssueLinkList = (ArrayList<KETProdECRIssueLink>) issueHash.get("delIssueList");

	    // Delete All Issue Link
	    KETProdECRIssueLink issueLink = null;
	    if (delIssueLinkList != null && delIssueLinkList.size() > 0) {
		for (int dIssueCnt = 0; dIssueCnt < delIssueLinkList.size(); dIssueCnt++) {
		    issueLink = delIssueLinkList.get(dIssueCnt);

		    PersistenceHelper.manager.delete(issueLink);
		}
	    }

	    issueList = (String[]) issueHash.get("issueList");

	    if (issueList != null) {
		refIssues = new ProjectIssue[issueList.length];

		for (int pCnt = 0; pCnt < issueList.length; pCnt++) {
		    refIssues[pCnt] = (ProjectIssue) rfTmp.getReference(issueList[pCnt]).getObject();
		}

		for (int pCnt = 0; pCnt < refIssues.length; pCnt++) {
		    issueLink = KETProdECRIssueLink.newKETProdECRIssueLink(refIssues[pCnt], prodEcr);
		    PersistenceHelper.manager.save(issueLink);
		}
	    }

	    // Delete AttachFile
	    if (reqData.get("delFileList").length() > 0) {
		String strDelFileList = reqData.get("delFileList");

		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}

		QueryResult rs = ContentHelper.service.getContentsByRole(prodEcr, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String itemStr = allObj.toString();
		    for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			if (delFileList.get(delFileCnt).equals(itemStr)) {
			    prodEcr = (KETProdChangeRequest) E3PSContentHelper.service.delete(prodEcr, allContentItem);
			}
		    }
		}
	    }

	    // Add AttachFile
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    prodEcr = (KETProdChangeRequest) E3PSContentHelper.service.attach((ContentHolder) prodEcr,
			    (WBFile) attachFiles.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    prodEcr = (KETProdChangeRequest) PersistenceHelper.manager.refresh(prodEcr);

	    // 제품 ECR 확장팩
	    this.modifyEcrExpansion(prodEcr, reqData);

	    ecrOid = CommonUtil.getOIDString(prodEcr);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    trx = null;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ecrOid;
    }

    @Override
    public boolean deleteProdEcr(String ecrOid) throws WTException {
	boolean isSuccess = false;
	Transaction trx = new Transaction();

	try {
	    trx.start();

	    if (ecrOid != null) {
		ReferenceFactory rf = new ReferenceFactory();
		KETProdChangeRequest prodEcr = (KETProdChangeRequest) rf.getReference(ecrOid).getObject();

		// Expansion
		// 제품, 금형 ECR 확장팩
		KETChangeRequestExpansion expansion = null;
		// ECR 로 찾는다.
		QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
		spec.appendWhere(
		        new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil
		                .getOIDLongValue(prodEcr)), new int[] { 0 });
		QueryResult result = PersistenceHelper.manager.find(spec);
		if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
		    expansion = (KETChangeRequestExpansion) result.nextElement();
		    PersistenceHelper.manager.delete(expansion);

		}

		// 주관부서
		result = PersistenceHelper.manager.navigate(prodEcr, "competent", KETEcrCompetentLink.class, false);
		while (result.hasMoreElements()) {
		    KETEcrCompetentLink link = (KETEcrCompetentLink) result.nextElement();
		    KETCompetentDepartment competent = link.getCompetent();

		    PersistenceHelper.manager.delete(link);
		    PersistenceHelper.manager.delete(competent);
		}

		// 회의록
		result = PersistenceHelper.manager.navigate(prodEcr, "meeting", KETEcrMeetingLink.class, false);
		while (result.hasMoreElements()) {
		    KETEcrMeetingLink link = (KETEcrMeetingLink) result.nextElement();
		    KETMeetingMinutes meeting = link.getMeeting();

		    PersistenceHelper.manager.delete(link);
		    PersistenceHelper.manager.delete(meeting);
		}

		// ECN
		result = PersistenceHelper.manager.navigate(prodEcr, "ecn", KETEcrEcnLink.class, false);
		while (result.hasMoreElements()) {
		    KETEcrEcnLink link = (KETEcrEcnLink) result.nextElement();
		    KETChangeNotice ecn = link.getEcn();

		    QueryResult result2 = PersistenceHelper.manager.navigate(ecn, "eca", KETEcnEcaLink.class, false);
		    while (result2.hasMoreElements()) {
			KETEcnEcaLink link2 = (KETEcnEcaLink) result2.nextElement();
			KETChangeActivity eca = link2.getEca();

			PersistenceHelper.manager.delete(link2);
			PersistenceHelper.manager.delete(eca);
		    }

		    PersistenceHelper.manager.delete(link);
		    PersistenceHelper.manager.delete(ecn);
		}

		// 품질관리
		result = PersistenceHelper.manager.navigate(prodEcr, "dqm", KETEcrDqmLink.class, false);
		while (result.hasMoreElements()) {
		    KETEcrDqmLink link = (KETEcrDqmLink) result.nextElement();
		    KETDqmAction dqm = link.getDqm();

		    PersistenceHelper.manager.delete(link);
		    PersistenceHelper.manager.delete(dqm);
		}

		// ECR
		PersistenceHelper.manager.delete(prodEcr);

		isSuccess = true;
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    trx.rollback();
	    trx = null;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return isSuccess;
    }

    @Override
    public KETProdChangeRequest getProdEcr(String ecrOid) throws WTException {
	ReferenceFactory rf = new ReferenceFactory();
	KETProdChangeRequest prodEcr = (KETProdChangeRequest) rf.getReference(ecrOid).getObject();

	return prodEcr;
    }

    @Override
    public String createProdEco(Hashtable<String, String> reqData, String[] refEcrList, ArrayList<Hashtable<String, String>> refPartList,
	    ArrayList<Hashtable<String, String>> refEpmDocList, ArrayList<Hashtable<String, String>> refBomList,
	    ArrayList<Hashtable<String, String>> refDocList, ArrayList<Hashtable<String, String>> refDqmList, Vector attachFileList)
	    throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();

	String ecoOid = "";
	ProdEcoBeans beans = new ProdEcoBeans();
	WTConnection wtConn = null;
	Connection conn = null;

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    KETProdChangeOrder prodEco = KETProdChangeOrder.newKETProdChangeOrder();

	    prodEco.setEcoId(reqData.get("eco_id"));
	    prodEco.setEcoName(reqData.get("eco_name"));
	    prodEco.setDevYn(reqData.get("dev_yn"));
	    prodEco.setDivisionFlag(reqData.get("div_flag"));
	    prodEco.setProjectOid(reqData.get("project_oid"));
	    prodEco.setDomesticYn(reqData.get("domestic_yn"));
	    prodEco.setCarMaker(reqData.get("car_maker"));
	    prodEco.setCarCategory(reqData.get("car_category"));
	    prodEco.setChangeReason(reqData.get("change_reason"));
	    prodEco.setOtherChangeReason(reqData.get("other_reason"));
	    prodEco.setCustormerFlag(reqData.get("custom_flag"));
	    prodEco.setOtherCustFlagDesc(reqData.get("other_cust_flag"));
	    prodEco.setChangeFlag(reqData.get("change_fact"));
	    prodEco.setCuDrawingChangeYn(reqData.get("change_cu_dwg"));
	    prodEco.setEcoApplyPoint(reqData.get("ecoApplyPoint"));
	    prodEco.setEffectiveDateFlag(reqData.get("effective_date"));
	    prodEco.setInventoryClear(reqData.get("inventory_process"));
	    prodEco.setChangeNoticeComplexity(ChangeNoticeComplexity.BASIC);

	    prodEco.setChangeType(reqData.get("changeType")); // 설계변경 유형
	    prodEco.setReviewResult(reqData.get("reviewResult")); // 검토결과
	    prodEco.setDesignGuideYn(reqData.get("design_guide_yn")); // 설계가이드 반영
	    prodEco.setDesignChecksheetYn(reqData.get("design_sheet_yn")); // 설계검증체크시트 반영

	    prodEco.setDefectDivCode((reqData.get("defectDiv"))); // 불량구분 코드
	    prodEco.setDefectTypeCode((reqData.get("defectType"))); // 불량구분명
	    prodEco.setDefectDivName(reqData.get("defectDivName")); // 불량유형 코드
	    prodEco.setDefectTypeName(reqData.get("defectTypeName")); // 불량유형명
	    prodEco.setCostChangeCode(reqData.get("costChange")); // 원가변동
	    prodEco.setCostVariationRate(reqData.get("costVariation")); // 원가증감비율(수주대비)

	    prodEco.setPointYn(reqData.get("point_yn")); // 중요포인트 반영/변경
	    prodEco.setSpecChangeYn(reqData.get("spec_changet_yn")); // 사양변경 식별표기

	    prodEco.setWebEditor(reqData.get("webEditor")); // 변경 전
	    prodEco.setWebEditorText(reqData.get("webEditorText")); // 변경 전
	    prodEco.setWebEditor1(reqData.get("webEditor1")); // 변경 후
	    prodEco.setWebEditorText1(reqData.get("webEditorText1")); // 변경 후

	    PeopleData pData = new PeopleData();
	    prodEco.setDeptName(pData.departmentName);

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.eco");
	    LifeCycleHelper.setLifeCycle(prodEco, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    prodEco.setContainerReference(containerRef);

	    // Folder Setting
	    String folderPath = "";
	    if (prodEco.getDivisionFlag().equals("C")) {
		// Car Division
		folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
	    } else if (prodEco.getDivisionFlag().equals("K")) {
		// Car Division
		folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");
	    } else {
		// Electronic Division
		folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
	    }

	    folderPath += DateUtil.getThisYear();
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) prodEco, folder);

	    // [START] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현
	    // ecrPartLink.setChangeReqComment( reqCommentList[pCnt] );
	    prodEco.setName(prodEco.getEcoName());
	    // [END] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현

	    prodEco = (KETProdChangeOrder) PersistenceHelper.manager.save(prodEco);

	    // WTPart Relation
	    ReferenceFactory rfTmp = new ReferenceFactory();
	    Hashtable<String, String> partHash = null;
	    WTPart refPart = null;
	    KETProdECOPartLink partRef = null;

	    for (int pCnt = 0; pCnt < refPartList.size(); pCnt++) {
		partHash = refPartList.get(pCnt);
		refPart = (WTPart) rfTmp.getReference(partHash.get("partOid")).getObject();

		partRef = KETProdECOPartLink.newKETProdECOPartLink(refPart, prodEco);
		partRef.setRelatedDieNo(partHash.get("dieNo"));
		partRef.setExpectCost(partHash.get("expectCost"));
		partRef.setSecureBudgetYn(StringUtil.checkReplaceStr(partHash.get("secureBudgetYn"), "N"));

		PersistenceHelper.manager.save(partRef);
	    }

	    // ProdECR Relation
	    KETProdChangeRequest mainEcr = null;
	    KETProdChangeRequest refEcr = null;
	    KETProdChangeLink ecrRef = null;

	    if (refEcrList != null) {
		for (int ecrCnt = 0; ecrCnt < refEcrList.length; ecrCnt++) {
		    refEcr = (KETProdChangeRequest) rfTmp.getReference(refEcrList[ecrCnt]).getObject();

		    ecrRef = KETProdChangeLink.newKETProdChangeLink(prodEco, refEcr);
		    PersistenceHelper.manager.save(ecrRef);

		    if (ecrCnt == 0)
			mainEcr = refEcr;

		}
	    }

	    // 품질문제 Relation
	    int refDqmListSize = (refDqmList != null) ? refDqmList.size() : 0;
	    for (int dqmCnt = 0; dqmCnt < refDqmListSize; dqmCnt++) {
		Hashtable<String, String> dqmHash = refDqmList.get(dqmCnt);

		KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(dqmHash.get("relDqmOid"));
		KETDqmAction ketDqmAction = ketDqmHeader.getAction();
		if (ketDqmAction == null) {

		    // 관련품질문제 정보가 온전하지 않습니다.
		    throw new WTException(messageService.getString("e3ps.message.ket_message", "04670"));

		} else {
		    KETEcoDqmLink ketEcoDqmLink = KETEcoDqmLink.newKETEcoDqmLink(prodEco, ketDqmAction);
		    PersistenceHelper.manager.save(ketEcoDqmLink);
		    ketEcoDqmLink = (KETEcoDqmLink) PersistenceHelper.manager.refresh(ketEcoDqmLink);
		}

	    }

	    // Attachfile Relation
	    if (attachFileList != null) {
		for (int fileCnt = 0; fileCnt < attachFileList.size(); fileCnt++) {
		    prodEco = (KETProdChangeOrder) E3PSContentHelper.service.attach((ContentHolder) prodEco,
			    (WBFile) attachFileList.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    // EPMDocument Change Activity
	    beans.createProdEpmDocEcaLink(prodEco, refEpmDocList);

	    // BOM Change Activity
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    ArrayList<KETProdChangeActivity> ecaList = beans.createProdBomHeaderEcaLink(prodEco, refBomList, conn);

	    // KETProjectDocument Change Activity
	    beans.createProdPjtDocEcaLink(prodEco, refDocList);
	    ecoOid = CommonUtil.getOIDString(prodEco);

	    // ECN
	    KETChangeNotice ecn = null;
	    // ECR에서 먼저 찾는다.
	    if (mainEcr != null) {
		QueryResult qr = PersistenceHelper.manager.navigate(mainEcr, "ecn", KETEcrEcnLink.class, false);
		if (qr.hasMoreElements()) { // while (qr.hasMoreElements()) {
		    KETEcrEcnLink ketEcrEcnLink = (KETEcrEcnLink) qr.nextElement();
		    ecn = ketEcrEcnLink.getEcn();

		}
	    }

	    // Link 처리
	    KETEcoEcnLink ketEcoEcnLink = null;
	    /*
	     * if (ecn == null) { // ECO 로 찾는다. QueryResult qr = PersistenceHelper.manager.navigate(prodEco, "ecn", KETEcoEcnLink.class,
	     * false); if (qr.hasMoreElements()) { // while (qr.hasMoreElements()) { ketEcoEcnLink = (KETEcoEcnLink) qr.nextElement(); ecn =
	     * ketEcoEcnLink.getEcn();
	     * 
	     * } }
	     */
	    // ECR이나 ECR에 ECN이 없을 경우
	    if (!PersistenceHelper.isPersistent(ecn)) {
		ecn = KETChangeNotice.newKETChangeNotice();

		// ECN Number
		try {
		    MoldChangeRequestDao moldChangeRequestDao = new MoldChangeRequestDao();
		    String ecnNumber = moldChangeRequestDao.getEcnNumber();
		    ecn.setEcnNumber(ecnNumber);

		} catch (Exception e) {
		    String ecnNumber = String.valueOf(Math.random());
		    ecn.setEcnNumber(ecnNumber);

		}
		// 저장
		WTContainerRef ecnContainerRef = WCUtil.getWTContainerRef();
		String ecnLcName = ECMProperties.getInstance().getString("ecm.lifecycle.ecn");
		LifeCycleHelper.setLifeCycle(ecn, LifeCycleHelper.service.getLifeCycleTemplate(ecnLcName, ecnContainerRef));

		ecn.setContainerReference(ecnContainerRef);

		// Folder Setting
		// ECO것을 그대로 사용한다.
		SubFolder ecnfolder = (SubFolder) FolderHelper.service.getFolder(folderPath, ecnContainerRef);
		FolderHelper.assignLocation((FolderEntry) ecn, ecnfolder);

		ecn = (KETChangeNotice) PersistenceHelper.manager.save(ecn);
		ketEcoEcnLink = KETEcoEcnLink.newKETEcoEcnLink(prodEco, ecn);
		ketEcoEcnLink = (KETEcoEcnLink) PersistenceHelper.manager.save(ketEcoEcnLink);

	    }
	    // ECR에 ECN이 있을 경우
	    else {
		// 저장
		ketEcoEcnLink = KETEcoEcnLink.newKETEcoEcnLink(prodEco, ecn);
		ketEcoEcnLink = (KETEcoEcnLink) PersistenceHelper.manager.save(ketEcoEcnLink);

	    }

	    // 프로젝트에서 산출물로 ECO 직접작성
	    try {
		String projectOutputOid = StringUtils.stripToEmpty(reqData.get("projectOutputOid")); // 프로젝트 - 산출물 관리 OID
		ProjectOutput output = (!projectOutputOid.equals("")) ? (ProjectOutput) CommonUtil.getObject(projectOutputOid) : null;
		if (output != null) {
		    ProjectTaskCompHelper.service.updateEcoProjectOutputLink(prodEco, output, "1");
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (ecaList != null && ecaList.size() > 0) {
		conn.commit();
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    try {
		if (conn != null)
		    conn.rollback();
	    } catch (SQLException e1) {
		Kogger.error(getClass(), e1);
	    }
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ecoOid;
    }

    @Override
    @Deprecated
    public String modifyProdEco(Hashtable reqData, String[] ecrList,
	    Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash, Hashtable<String, ArrayList<String>> delObjListHash,
	    Vector attachFileList) throws WTException {
	String ecoOid = "";
	Transaction trx = new Transaction();
	ProdEcoBeans beans = new ProdEcoBeans();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();

	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();

	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(reqData.get("eco_oid").toString());

	    prodEco.setEcoName(reqData.get("eco_name").toString());
	    prodEco.setDevYn(reqData.get("dev_yn").toString());
	    prodEco.setDivisionFlag(reqData.get("div_flag").toString());
	    prodEco.setProjectOid(reqData.get("project_oid").toString());
	    prodEco.setDomesticYn(reqData.get("domestic_yn").toString());
	    prodEco.setCarMaker(reqData.get("car_maker").toString());
	    prodEco.setCarCategory(reqData.get("car_category").toString());
	    prodEco.setChangeReason(reqData.get("change_reason").toString());
	    prodEco.setOtherChangeReason(reqData.get("other_reason").toString());
	    prodEco.setCustormerFlag(reqData.get("custom_flag").toString());
	    prodEco.setOtherCustFlagDesc(reqData.get("other_cust_flag").toString());
	    prodEco.setChangeFlag(reqData.get("change_fact").toString());
	    prodEco.setCuDrawingChangeYn(reqData.get("change_cu_dwg").toString());
	    prodEco.setEcoApplyPoint(reqData.get("ecoApplyPoint").toString());
	    prodEco.setEffectiveDateFlag(reqData.get("effective_date").toString());
	    prodEco.setInventoryClear(reqData.get("inventory_process").toString());

	    prodEco = (KETProdChangeOrder) PersistenceHelper.manager.modify(prodEco);

	    // Delete All Related ECR List
	    ArrayList<String> delEcrOidList = delObjListHash.get("ecrLinkOidList");
	    KETProdChangeLink ecrLink = null;

	    for (int dEcrCnt = 0; dEcrCnt < delEcrOidList.size(); dEcrCnt++) {
		ecrLink = (KETProdChangeLink) CommonUtil.getObject(delEcrOidList.get(dEcrCnt));
		PersistenceHelper.manager.delete(ecrLink);
	    }

	    // Delete All Related Part List
	    ArrayList<String> delPartOidList = delObjListHash.get("partLinkOidList");
	    KETProdECOPartLink partLink = null;

	    for (int dPartCnt = 0; dPartCnt < delPartOidList.size(); dPartCnt++) {
		partLink = (KETProdECOPartLink) CommonUtil.getObject(delPartOidList.get(dPartCnt));
		PersistenceHelper.manager.delete(partLink);
	    }

	    // Delete AttachFile
	    String strDelFileList = (String) reqData.get("delFileList");
	    if (strDelFileList.length() > 0) {
		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}

		QueryResult rs = ContentHelper.service.getContentsByRole(prodEco, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String itemStr = allObj.toString();
		    for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			if (delFileList.get(delFileCnt).equals(itemStr)) {
			    prodEco = (KETProdChangeOrder) E3PSContentHelper.service.delete(prodEco, allContentItem);
			}
		    }
		}
	    }

	    // Delete All Related BOM List
	    // Set temporary Session - Administrator
	    WTPrincipal session = null;
	    session = SessionHelper.manager.getPrincipal();
	    SessionHelper.manager.setAdministrator();

	    int failDeleteBom = 0;
	    boolean isDeleteBom = false;
	    if (reqData.get("activityType").toString().equals("2") || reqData.get("activityType").toString().equals("")) {
		ArrayList<String> delBomHeaderOidList = delObjListHash.get("bomLinkOidList");

		KETProdChangeActivity eca = null;

		KETProdECABomLink[] relatedBomLinkList = null;
		KETBomEcoHeader bomEcoHeader = null;
		WTPart part = null;

		RENEW: for (int dBomCnt = 0; dBomCnt < delBomHeaderOidList.size(); dBomCnt++) {

		    eca = (KETProdChangeActivity) CommonUtil.getObject(delBomHeaderOidList.get(dBomCnt));
		    if (eca == null)
			continue RENEW;

		    // Link 처리
		    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETProdECABomLink bomLink = (KETProdECABomLink) queryResult.nextElement();

			/*
		         * // Mass Bom Change Delete if (bomLink.getMassChangeYn().equals("Y") &&
		         * bomLink.getRelatedParentPartList().length() > 0) { relatedBomLinkList =
		         * EcmSearchHelper.manager.getRelatedMassBomLink(KETObjectUtil.getOidString(bomLink.getProdECA()),
		         * bomLink.getRelatedParentPartList());
		         * 
		         * for (int linkCnt = 0; linkCnt < relatedBomLinkList.length; linkCnt++) { bomEcoHeader =
		         * relatedBomLinkList[linkCnt].getBom();
		         * 
		         * if (StringUtil.checkNull(relatedBomLinkList[linkCnt].getAfterVersion()).length() > 0) { part =
		         * beans.getLastestPart(bomEcoHeader.getEcoItemCode(), conn); PersistenceHelper.manager.delete(part); }
		         * 
		         * isDeleteBom = beans.deleteBomSubComponent(bomEcoHeader.getEcoHeaderNumber(), bomEcoHeader.getEcoItemCode(),
		         * conn); PersistenceHelper.manager.delete(bomEcoHeader);
		         * PersistenceHelper.manager.delete(relatedBomLinkList[linkCnt]);
		         * 
		         * if (!isDeleteBom) { failDeleteBom++; } } } else {
		         */
			// Standard Bom Change Delete
			bomEcoHeader = bomLink.getBom();

			if (StringUtil.checkNull(bomLink.getAfterVersion()).length() > 0) {
			    part = beans.getLastestPart(bomEcoHeader.getEcoItemCode(), conn);
			    PersistenceHelper.manager.delete(part);
			}

			isDeleteBom = beans.deleteBomSubComponent(bomEcoHeader.getEcoHeaderNumber(), bomEcoHeader.getEcoItemCode(), conn);
			PersistenceHelper.manager.delete(bomEcoHeader);
			PersistenceHelper.manager.delete(bomLink);

			if (!isDeleteBom) {
			    failDeleteBom++;
			}

			/*
		         * }
		         */

		    }

		    PersistenceHelper.manager.delete(eca);
		    WorkflowSearchHelper.manager.deleteWorkItem(eca);

		}
	    }

	    SessionHelper.manager.setPrincipal(session.getName());

	    // Delete All Related EPMDocument List
	    if (reqData.get("activityType").toString().equals("1") || reqData.get("activityType").toString().equals("")) {
		ArrayList<String> delEpmDocOidList = delObjListHash.get("epmDocLinkOidList");
		KETProdECAEpmDocLink epmDocLink = null;
		EPMDocument epmDoc = null;

		for (int dEpmCnt = 0; dEpmCnt < delEpmDocOidList.size(); dEpmCnt++) {
		    epmDocLink = (KETProdECAEpmDocLink) CommonUtil.getObject(delEpmDocOidList.get(dEpmCnt));

		    if (StringUtil.checkNull(epmDocLink.getAfterVersion()).length() > 0) {
			epmDoc = beans.getLastestEPMDoc(epmDocLink.getEpmDoc());
			beans.cancelRevEpm(KETObjectUtil.getOidString(epmDoc));
		    }

		    PersistenceHelper.manager.delete(epmDocLink);
		}

		QueryResult planQr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class, false);
		KETProdECOPlanLink planLink = null;
		while (planQr.hasMoreElements()) {
		    planLink = (KETProdECOPlanLink) planQr.nextElement();
		    PersistenceHelper.manager.delete(planLink);
		}
	    }

	    // Delete All Related Document List
	    if (reqData.get("activityType").toString().equals("3") || reqData.get("activityType").toString().equals("4")
		    || reqData.get("activityType").toString().equals("")) {
		KETProdECADocLink docLink = null;

		ArrayList<String> delDocOidList = delObjListHash.get("docLinkOidList");
		int delDocOidListSize = (delDocOidList != null) ? delDocOidList.size() : 0;
		for (int dDocCnt = 0; dDocCnt < delDocOidListSize; dDocCnt++) {
		    docLink = (KETProdECADocLink) CommonUtil.getObject(delDocOidList.get(dDocCnt));
		    PersistenceHelper.manager.delete(docLink);
		}
	    }

	    // Delete Activity
	    ArrayList<KETProdChangeActivity> activityList = beans.getNotRelatedActivityList(prodEco);
	    KETProdChangeActivity prodEca = null;
	    for (int ecaCnt = 0; ecaCnt < activityList.size(); ecaCnt++) {
		prodEca = activityList.get(ecaCnt);

		PersistenceHelper.manager.delete(prodEca);
		WorkflowSearchHelper.manager.deleteWorkItem(prodEca);
	    }

	    // WTPart Relation
	    ReferenceFactory rfTmp = new ReferenceFactory();
	    Hashtable<String, String> partHash = null;
	    WTPart refPart = null;
	    KETProdECOPartLink partRef = null;
	    ArrayList<Hashtable<String, String>> refPartList = addObjListHash.get("refPartList");

	    for (int pCnt = 0; pCnt < refPartList.size(); pCnt++) {
		partHash = refPartList.get(pCnt);
		refPart = (WTPart) rfTmp.getReference(partHash.get("partOid")).getObject();

		partRef = KETProdECOPartLink.newKETProdECOPartLink(refPart, prodEco);
		partRef.setRelatedDieNo(partHash.get("dieNo"));
		partRef.setExpectCost(partHash.get("expectCost"));
		partRef.setSecureBudgetYn(partHash.get("secureBudgetYn"));

		PersistenceHelper.manager.save(partRef);
	    }

	    // ProdECR Relation
	    KETProdChangeRequest refEcr = null;
	    KETProdChangeLink ecrRef = null;

	    if (ecrList != null) {
		for (int ecrCnt = 0; ecrCnt < ecrList.length; ecrCnt++) {
		    refEcr = (KETProdChangeRequest) rfTmp.getReference(ecrList[ecrCnt]).getObject();

		    ecrRef = KETProdChangeLink.newKETProdChangeLink(prodEco, refEcr);
		    PersistenceHelper.manager.save(ecrRef);
		}
	    }

	    // Attachfile Relation
	    if (attachFileList != null) {
		for (int fileCnt = 0; fileCnt < attachFileList.size(); fileCnt++) {
		    prodEco = (KETProdChangeOrder) E3PSContentHelper.service.attach((ContentHolder) prodEco,
			    (WBFile) attachFileList.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    // EPMDocument Change Activity
	    if (reqData.get("activityType").toString().equals("1") || reqData.get("activityType").toString().equals("")) {
		ArrayList<Hashtable<String, String>> refEpmDocList = addObjListHash.get("refEpmDocList");
		beans.createProdEpmDocEcaLink(prodEco, refEpmDocList);
		// beans.modifyMoldPlanLink( prodEco );

	    }

	    // BOM Change Activity
	    boolean isCreated = true;
	    if (reqData.get("activityType").toString().equals("2") || reqData.get("activityType").toString().equals("")) {
		ArrayList<Hashtable<String, String>> refBomList = addObjListHash.get("refBomList");
		ArrayList<KETProdChangeActivity> ecaList = beans.createProdBomHeaderEcaLink(prodEco, refBomList, conn);
		if (ecaList == null || ecaList.size() == 0)
		    isCreated = false;
	    }

	    // KETProjectDocument Change Activity
	    if (reqData.get("activityType").toString().equals("3") || reqData.get("activityType").toString().equals("4")
		    || reqData.get("activityType").toString().equals("")) {
		ArrayList<Hashtable<String, String>> refDocList = addObjListHash.get("refDocList");
		beans.createProdPjtDocEcaLink(prodEco, refDocList);
	    }

	    ecoOid = CommonUtil.getOIDString(prodEco);

	    // Create New WorkItem
	    if (prodEco.getLifeCycleState().getStringValue().indexOf("EXCUTEACTIVITY") > -1
		    || prodEco.getLifeCycleState().getStringValue().indexOf("APPROVED") > -1) {
		KETWfmHelper.service.createWorkItem(prodEco);
	    }

	    if (isCreated && failDeleteBom == 0) {
		conn.commit();
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    try {
		if (conn != null)
		    conn.rollback();
	    } catch (SQLException e1) {
		Kogger.error(getClass(), e1);
	    }

	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}
	return ecoOid;
    }

    @Override
    public String reviseEpmDocInProdEco(String prodEcoOid, String[] epmDocLinkOidList, String[] epmDocOidList, String[] reviseChkFlagList,
	    String[] epmDocTypeList) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    WTPrincipal session = null;
	    WTGroup group = null;
	    String preVersionObj = "";
	    String afterVersion = "";
	    String reviseChk = "";
	    // ProdEcoBeans beans = new ProdEcoBeans();
	    KETProdECAEpmDocLink epmDocLink = null;

	    for (int objCnt = 0; objCnt < epmDocOidList.length; objCnt++) {
		preVersionObj = epmDocOidList[objCnt];
		reviseChk = reviseChkFlagList[objCnt];

		if (reviseChk.equals("Y")) {

		    ReferenceFactory rf = new ReferenceFactory();
		    Versioned versioned = (Versioned) rf.getReference(preVersionObj).getObject();

		    if (!VersionHelper.isLatestRevision(versioned)) {

			EPMDocument latestEPMDocument = (EPMDocument) VersionHelper.getLatestRevision(versioned);
			String latestEPMDocumentNumber = latestEPMDocument.getNumber();
			String latestEPMDocumentRevision = latestEPMDocument.getVersionInfo().getIdentifier().getValue();

			// <entry key="04014">상위 버전[{0}]이 이미 존재합니다.</entry>
			throw new WTException(messageService.getString("e3ps.message.ket_message", "04014", latestEPMDocumentNumber + " ("
			        + latestEPMDocumentRevision + ")"));
			// throw new WTException("최신버전이 아닙니다.");
		    }

		    /*
	             * 1) 도면 개정 시점에 => 연계 부품이 먼저 개정되지 않으면 Validation 걸어서 Message 전달 파라미터1 : EPMDocument : 개정 전의 리비전 파라미터2 : ecoOid return :
	             * String : null 이거나 empty가 아니면 message 뿌려주세요. 호출 api : PartBaseHelper.service.validRelatedPartRevised(EPMDocument
	             * epmDoc, String ecoOid);
	             */
		    EPMDocument epmDoc = (EPMDocument) versioned;
		    String ecoOid = prodEcoOid;
		    String ERROR_MESSAGE = PartBaseHelper.service.validRelatedPartRevised(epmDoc, ecoOid);
		    if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {
			throw new WTException(ERROR_MESSAGE);
		    }
		    ERROR_MESSAGE = EpmBaseHelper.service.validCheckoutInfoEpm(epmDoc);
		    if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {
			throw new WTException(ERROR_MESSAGE);
		    }

		    // ChangeSession set Administrator
		    session = SessionHelper.manager.getPrincipal();
		    SessionHelper.manager.setAdministrator();
		    group = KETObjectUtil.getGroup("Revise Administrators");
		    group.addMember(session);
		    SessionHelper.manager.setPrincipal(session.getName());

		    // 2차고도화 수정 TKLEE
		    KETProdChangeOrder prodEco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
		    String changeType = prodEco.getChangeType();
		    if (changeType != null) {
			String[] changeTypes = StringUtils.splitPreserveAllTokens(changeType, "|");
			int changeTypesLength = (changeTypes != null) ? changeTypes.length : 0;
			for (int k = 0; k < changeTypesLength; k++) {
			    if (changeTypes[k].equalsIgnoreCase("CDR_113^REASON_10")) {
				changeType = changeTypes[k];
			    }
			}
		    }

		    Kogger.debug(getClass(), "Revision Before:" + changeType);

		    // afterVersion = beans.reviseEpmDoc(preVersionObj);
		    afterVersion = PartBaseHelper.service.reviseEpmDocNGetVersion((EPMDocument) versioned, changeType);

		    // Restore Session
		    SessionHelper.manager.setAdministrator();
		    group.removeMember(session);
		    SessionHelper.manager.setPrincipal(session.getName());

		    epmDocLink = (KETProdECAEpmDocLink) KETObjectUtil.getObject(epmDocLinkOidList[objCnt]);

		    if (epmDocTypeList[objCnt].equals("3D")) {
			epmDocLink.setChangeYn("Y");
		    }

		    epmDocLink.setAfterVersion(afterVersion);

		    PersistenceHelper.manager.save(epmDocLink);

		    // epmDoc = PartBaseHelper.service.getLatestEPM(epmDoc.getNumber().toUpperCase());
		    /*
	             * KETProdChangeOrder prodEco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid); String changeType =
	             * prodEco.getChangeType(); if (changeType != null) { String[] changeTypes =
	             * StringUtils.splitPreserveAllTokens(changeType, "|"); int changeTypesLength = (changeTypes != null) ?
	             * changeTypes.length : 0; for (int k = 0; k < changeTypesLength; k++) { if
	             * (changeTypes[k].equalsIgnoreCase("CDR_113^REASON_10")) { changeType = changeTypes[k]; } } if
	             * ("CDR_113^REASON_10".equals(changeType)) { try { e3ps.common.iba.IBAUtil.changeIBAValue(epmDoc,
	             * EDMHelper.IBA_DEV_STAGE, "양산단계"); e3ps.common.iba.IBAUtil.changeIBAValue(epmDoc, EDMHelper.IBA_MANUFACTURING_VERSION,
	             * afterVersion); } catch (Exception e) { Kogger.error(getClass(), e); } } // PartUtil.doRevise(versioned, afterVersion,
	             * null, null, null, null, null, changeType); }
	             */

		}

	    }

	    trx.commit();
	    trx = null;
	} catch (WTException wte) {
	    trx.rollback();
	    trx = null;
	    prodEcoOid = "";

	    throw wte;

	} catch (Exception e) {
	    trx.rollback();
	    trx = null;
	    prodEcoOid = "";

	    Kogger.error(getClass(), e);

	} finally {
	    if (trx != null) {
		prodEcoOid = "";
		trx.rollback();
		trx = null;
	    }
	}

	return prodEcoOid;
    }

    @Override
    @Deprecated
    public String reviseBomInProdEco(String prodEcoOid, String bomHeaderLinkOid, String partNo, String reviseChkFlag, String massChgYn,
	    String parentPartListStr) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();

	    WTPart part = null;
	    String afterVersion = "";
	    ProdEcoBeans beans = new ProdEcoBeans();
	    KETBomEcoHeader ecoBomHeader = null;
	    KETProdECABomLink bomLink = null;
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(prodEcoOid);

	    if (reviseChkFlag.equals("Y")) {

		/*
	         * if (massChgYn.equals("Y") && parentPartListStr.length() > 0) { StringTokenizer st = new
	         * StringTokenizer(parentPartListStr, ","); String parentPartNo = ""; while (st.hasMoreTokens()) { parentPartNo =
	         * st.nextToken(); part = beans.getLastestPart(parentPartNo, conn);
	         * 
	         * afterVersion = beans.reviseBom(part);
	         * 
	         * ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), parentPartNo);
	         * ecoBomHeader.setBOMVersion(afterVersion); ecoBomHeader.setAttribute1("Y"); PersistenceHelper.manager.save(ecoBomHeader);
	         * 
	         * bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);
	         * 
	         * bomLink.setAfterVersion(afterVersion); PersistenceHelper.manager.save(bomLink); } } else {
	         */

		part = beans.getLastestPart(partNo, conn);

		afterVersion = beans.reviseBom(part);

		ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), partNo);
		ecoBomHeader.setBOMVersion(afterVersion);
		ecoBomHeader.setAttribute1("Y");
		PersistenceHelper.manager.save(ecoBomHeader);

		bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);

		bomLink.setAfterVersion(afterVersion);
		PersistenceHelper.manager.save(bomLink);

		//
		// INSERT => select * from ketbomecocomponent;

		/*
	         * }
	         */

	    }

	    trx.commit();
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String changeEpmDoc(String prodEcoOid, String[] epmDocListLink, String[] changeFlagList) throws WTException {
	Transaction trx = new Transaction();

	try {
	    trx.start();

	    KETProdECAEpmDocLink epmDocLink = null;

	    for (int objCnt = 0; objCnt < epmDocListLink.length; objCnt++) {
		epmDocLink = (KETProdECAEpmDocLink) KETObjectUtil.getObject(epmDocListLink[objCnt]);

		epmDocLink.setChangeYn(changeFlagList[objCnt]);
		PersistenceHelper.manager.save(epmDocLink);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;
	    prodEcoOid = "";

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		prodEcoOid = "";
		trx.rollback();
		trx = null;
	    }
	}

	return prodEcoOid;
    }

    @Override
    public boolean deleteProdEco(String ecoOid) throws WTException {
	Transaction trx = new Transaction();
	QueryResult qr = null;
	WTConnection wtConn = null;
	Connection conn = null;
	boolean isDelete = false;
	boolean isDeleteBomCoWorker = false;
	boolean isDeleteBomComp = false;
	boolean isDeleteBomTmpComp = false;
	try {
	    trx.start();

	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    conn.setAutoCommit(false);

	    ProdEcoBeans beans = new ProdEcoBeans();

	    WTPrincipal session = null;
	    // Set temporary Session - Administrator
	    session = SessionHelper.manager.getPrincipal();
	    SessionHelper.manager.setAdministrator();

	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(ecoOid);

	    // Delete All Part Link
	    qr = PersistenceHelper.manager.navigate(prodEco, "part", KETProdECOPartLink.class, false);
	    while (qr.hasMoreElements()) {
		PersistenceHelper.manager.delete((KETProdECOPartLink) qr.nextElement());
	    }

	    // Delete All ECR Link
	    qr = PersistenceHelper.manager.navigate(prodEco, "prodECR", KETProdChangeLink.class, false);
	    while (qr.hasMoreElements()) {
		PersistenceHelper.manager.delete((KETProdChangeLink) qr.nextElement());
	    }

	    // Delete All 품질문제 Link
	    qr = PersistenceHelper.manager.navigate(prodEco, "dqm", KETEcoDqmLink.class, false);
	    while (qr.hasMoreElements()) {
		PersistenceHelper.manager.delete((KETEcoDqmLink) qr.nextElement());
	    }

	    // Get All Activities
	    ArrayList<KETProdChangeActivity> tempArr = new ArrayList<KETProdChangeActivity>();
	    KETProdChangeActivity[] prodECAList = null;
	    qr = PersistenceHelper.manager.navigate(prodEco, "prodECA", KETProdChangeActivityLink.class);
	    while (qr.hasMoreElements()) {
		tempArr.add((KETProdChangeActivity) qr.nextElement());
	    }

	    prodECAList = new KETProdChangeActivity[tempArr.size()];
	    tempArr.toArray(prodECAList);

	    KETProdECAEpmDocLink epmDocLink = null;
	    EPMDocument epmDoc = null;
	    KETProdECABomLink bomLink = null;
	    WTPart part = null;

	    for (int ecaCnt = 0; ecaCnt < prodECAList.length; ecaCnt++) {
		// Delete All EPMDocument Link
		if (prodECAList[ecaCnt].getActivityType().equals("1")) {
		    qr = PersistenceHelper.manager.navigate(prodECAList[ecaCnt], "epmDoc", KETProdECAEpmDocLink.class, false);
		    while (qr.hasMoreElements()) {
			epmDocLink = (KETProdECAEpmDocLink) qr.nextElement();
			/*
		         * if (StringUtil.checkNull(epmDocLink.getAfterVersion()).length() > 0) { epmDoc =
		         * beans.getLastestEPMDoc(epmDocLink.getEpmDoc()); beans.cancelRevEpm(KETObjectUtil.getOidString(epmDoc)); }
		         */

			PersistenceHelper.manager.delete(epmDocLink);
		    }

		    QueryResult planQr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class, false);
		    KETProdECOPlanLink planLink = null;
		    while (planQr.hasMoreElements()) {
			planLink = (KETProdECOPlanLink) planQr.nextElement();
			PersistenceHelper.manager.delete(planLink);
		    }
		}
		// Delete All BOMHeader Link And BOM ECO Header
		else if (prodECAList[ecaCnt].getActivityType().equals("2")) {
		    qr = PersistenceHelper.manager.navigate(prodECAList[ecaCnt], "bom", KETProdECABomLink.class, false);
		    while (qr.hasMoreElements()) {
			bomLink = (KETProdECABomLink) qr.nextElement();
			KETBomEcoHeader bomHeader = bomLink.getBom();
			/*
		         * if (StringUtil.checkNull(bomLink.getAfterVersion()).length() > 0) { part =
		         * beans.getLastestPart(bomLink.getBom().getEcoItemCode(), conn); PersistenceHelper.manager.delete(part); }
		         */

			PersistenceHelper.manager.delete(bomLink);
			PersistenceHelper.manager.delete(bomHeader);
		    }
		}
		// Delete All Document Link
		else if (prodECAList[ecaCnt].getActivityType().equals("3") || prodECAList[ecaCnt].getActivityType().equals("4")) {
		    qr = PersistenceHelper.manager.navigate(prodECAList[ecaCnt], "doc", KETProdECADocLink.class, false);
		    while (qr.hasMoreElements()) {
			PersistenceHelper.manager.delete((KETProdECADocLink) qr.nextElement());
		    }
		}

		PersistenceHelper.manager.delete(prodECAList[ecaCnt]);
		WorkflowSearchHelper.manager.deleteWorkItem(prodECAList[ecaCnt]);
	    }

	    // Delete BOM ECO Header
	    /*
	     * QuerySpec spec = new QuerySpec(KETBomEcoHeader.class); SearchUtil.appendEQUAL(spec, KETBomEcoHeader.class,
	     * KETBomEcoHeader.ECO_HEADER_NUMBER, prodEco.getEcoId(), 0); QueryResult result = PersistenceHelper.manager.find(spec);
	     * 
	     * while (result.hasMoreElements()) { PersistenceHelper.manager.delete((KETBomEcoHeader) result.nextElement()); }
	     */

	    EcmComDao comDao = new EcmComDao(conn);
	    isDeleteBomCoWorker = comDao.deleteAllBomCoWorker(prodEco.getEcoId());
	    isDeleteBomComp = comDao.deleteAllBomComponent(prodEco.getEcoId());
	    isDeleteBomTmpComp = comDao.deleteAllBomTempComponent(prodEco.getEcoId());

	    PersistenceHelper.manager.delete(prodEco);
	    WorkflowSearchHelper.manager.deleteWorkItem(prodEco);

	    SessionHelper.manager.setPrincipal(session.getName());

	    if (isDeleteBomCoWorker && isDeleteBomComp && isDeleteBomTmpComp) {
		conn.commit();
	    } else {
		conn.rollback();
	    }

	    isDelete = true;

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    try {
		if (conn != null)
		    conn.rollback();
	    } catch (Exception e1) {
		Kogger.error(getClass(), e1);
	    }

	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return isDelete;
    }

    @Override
    public boolean cancelReviseBom(String partNo) throws WTException {
	boolean isSuccess = false;
	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	// Backup User Session
	SessionContext sessioncontext = SessionContext.newContext();

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    ProdEcoBeans beans = new ProdEcoBeans();

	    WTPart lastestPart = beans.getLastestPart(partNo, conn);

	    // Set temporary Session - Administrator
	    SessionHelper.manager.setAdministrator();

	    PersistenceHelper.manager.delete(lastestPart);

	    isSuccess = true;

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }

	    Kogger.error(getClass(), e);

	} finally {
	    // Restore User Session
	    SessionContext.setContext(sessioncontext);

	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}
	return isSuccess;
    }

    @Override
    @Deprecated
    public String cancelReviseBomInProdEco(String prodEcoOid, String bomHeaderLinkOid, String partNo, String reviseChkFlag,
	    String massChgYn, String parentPartListStr) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    conn.setAutoCommit(false);

	    WTPart part = null;
	    String preVersion = "";
	    KETBomEcoHeader ecoBomHeader = null;
	    KETProdECABomLink bomLink = null;
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(prodEcoOid);
	    ProdEcoBeans beans = new ProdEcoBeans();
	    EcmComDao comDao = new EcmComDao(conn);
	    boolean isDeleteComp = false;
	    boolean isDeleteTempComp = false;
	    boolean isUpdateCoWorker = false;

	    if (reviseChkFlag.equals("N")) {
		WTPrincipal session = null;

		/*
	         * if (massChgYn.equals("Y") && parentPartListStr.length() > 0) { StringTokenizer st = new
	         * StringTokenizer(parentPartListStr, ","); String parentPartNo = "";
	         * 
	         * while (st.hasMoreTokens()) { parentPartNo = st.nextToken(); part = beans.getLastestPart(parentPartNo, conn);
	         * 
	         * // Set temporary Session - Administrator session = SessionHelper.manager.getPrincipal();
	         * SessionHelper.manager.setAdministrator(); PersistenceHelper.manager.delete(part);
	         * SessionHelper.manager.setPrincipal(session.getName());
	         * 
	         * part = beans.getLastestPart(parentPartNo, conn); preVersion = VersionUtil.getMajorVersion(part);
	         * 
	         * ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), parentPartNo);
	         * ecoBomHeader.setBOMVersion(preVersion); ecoBomHeader.setAttribute1("N");
	         * ecoBomHeader.setBoxQuantity(ecoBomHeader.getAttribute2());
	         * 
	         * PersistenceHelper.manager.save(ecoBomHeader);
	         * 
	         * bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);
	         * 
	         * bomLink.setAfterVersion(""); PersistenceHelper.manager.save(bomLink);
	         * 
	         * // Delete BomSubComponent isDeleteComp = comDao.deleteBomComponent(prodEco.getEcoId(), parentPartNo); isDeleteTempComp =
	         * comDao.deleteBomTempComponent(prodEco.getEcoId(), parentPartNo); isUpdateCoWorker =
	         * comDao.updateBomCoWorker(prodEco.getEcoId(), parentPartNo); } } else {
	         */

		part = beans.getLastestPart(partNo, conn);

		// Set temporary Session - Administrator
		session = SessionHelper.manager.getPrincipal();
		SessionHelper.manager.setAdministrator();
		PersistenceHelper.manager.delete(part);
		SessionHelper.manager.setPrincipal(session.getName());

		part = beans.getLastestPart(partNo, conn);
		preVersion = VersionUtil.getMajorVersion(part);

		ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), partNo);
		ecoBomHeader.setBOMVersion(preVersion);
		ecoBomHeader.setAttribute1("N");
		ecoBomHeader.setBoxQuantity(ecoBomHeader.getAttribute2());
		PersistenceHelper.manager.save(ecoBomHeader);

		bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);

		bomLink.setAfterVersion("");
		PersistenceHelper.manager.save(bomLink);

		// Delete BomSubComponent
		isDeleteComp = comDao.deleteBomComponent(prodEco.getEcoId(), partNo);
		isDeleteTempComp = comDao.deleteBomTempComponent(prodEco.getEcoId(), partNo);
		isUpdateCoWorker = comDao.updateBomCoWorker(prodEco.getEcoId(), partNo);

		/*
	         * }
	         */

	    }

	    if (isDeleteComp && isDeleteTempComp && isUpdateCoWorker) {
		conn.commit();
	    } else {
		conn.rollback();
	    }

	    trx.commit();
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }

	}

	return ALERT_MESSAGE;
    }

    @Override
    public String modifyProdEcoNotOwner(String ecoOid, Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash,
	    Hashtable<String, ArrayList<String>> delObjListHash, String activityType) throws WTException {

	ecoOid = this.modifyProdEcoNotOwner2(ecoOid, addObjListHash, delObjListHash, activityType, null);
	return ecoOid;
    }

    @Override
    public KETMoldChangeOrder createMoldEcoByProdEco(String ecoOid) throws WTException {
	KETMoldChangeOrder moldEco = null;
	Transaction trx = new Transaction();

	try {
	    trx.start();

	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(ecoOid);

	    moldEco = new KETMoldChangeOrder();
	    moldEco.setDevYn(prodEco.getDevYn());
	    moldEco.setDivisionFlag(prodEco.getDivisionFlag());
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return moldEco;
    }

    @Override
    public String cancelReviseEpmDocInProdEco(String prodEcoOid, String[] epmDocLinkOidList, String[] epmDocOidList,
	    String[] cancelChkFlagList) throws WTException {
	Transaction trx = new Transaction();

	try {
	    trx.start();

	    String preVersionObj = "";
	    String afterVersion = "";
	    String cancelChk = "";
	    ProdEcoBeans beans = new ProdEcoBeans();
	    KETProdECAEpmDocLink epmDocLink = null;
	    EPMDocument epmDoc = null;

	    for (int objCnt = 0; objCnt < epmDocOidList.length; objCnt++) {
		preVersionObj = epmDocOidList[objCnt];
		cancelChk = cancelChkFlagList[objCnt];

		if (cancelChk.equals("Y")) {
		    epmDoc = (EPMDocument) KETObjectUtil.getObject(preVersionObj);
		    epmDoc = (EPMDocument) VersionUtil.getLatestObject((Master) epmDoc.getMaster());
		    Kogger.debug(getClass(), "@@@@@epmDoc@@@@@@@@ ===> " + epmDoc);
		    afterVersion = beans.cancelRevEpm(KETObjectUtil.getOidString(epmDoc));
		    Kogger.debug(getClass(), "@@@@@afterVersion@@@@@@@@ ===> " + afterVersion);
		    epmDocLink = (KETProdECAEpmDocLink) KETObjectUtil.getObject(epmDocLinkOidList[objCnt]);
		    Kogger.debug(getClass(), "@@@@@epmDocLink@@@@@@@@ ===> " + epmDocLink);
		    epmDocLink.setAfterVersion(afterVersion);
		    epmDocLink.setChangeYn("");

		    PersistenceHelper.manager.save(epmDocLink);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;
	    prodEcoOid = "";

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		prodEcoOid = "";
		trx.rollback();
		trx = null;
	    }
	}

	return prodEcoOid;
    }

    @Override
    public String modifyProdEcoBasic(Hashtable reqData, String[] ecrList, ArrayList<Hashtable<String, String>> refPartList,
	    Hashtable<String, ArrayList<String>> delObjListHash, Vector attachFileList) throws WTException {
	String ecoOid = "";
	Transaction trx = new Transaction();
	ProdEcoBeans beans = new ProdEcoBeans();

	try {
	    trx.start();

	    Kogger.debug(getClass(), "modifyProdEco -------------------->1 ");
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(reqData.get("eco_oid").toString());

	    prodEco.setEcoName(reqData.get("eco_name").toString());
	    prodEco.setDevYn(reqData.get("dev_yn").toString());
	    prodEco.setDivisionFlag(reqData.get("div_flag").toString());
	    prodEco.setProjectOid(reqData.get("project_oid").toString());
	    prodEco.setDomesticYn(reqData.get("domestic_yn").toString());
	    prodEco.setCarMaker(reqData.get("car_maker").toString());
	    prodEco.setCarCategory(reqData.get("car_category").toString());
	    prodEco.setChangeReason(reqData.get("change_reason").toString());
	    prodEco.setOtherChangeReason(reqData.get("other_reason").toString());
	    prodEco.setCustormerFlag(reqData.get("custom_flag").toString());
	    prodEco.setOtherCustFlagDesc(reqData.get("other_cust_flag").toString());
	    prodEco.setChangeFlag(reqData.get("change_fact").toString());
	    prodEco.setCuDrawingChangeYn(reqData.get("change_cu_dwg").toString());
	    prodEco.setEcoApplyPoint(reqData.get("ecoApplyPoint").toString());
	    prodEco.setEffectiveDateFlag(reqData.get("effective_date").toString());
	    prodEco.setInventoryClear(reqData.get("inventory_process").toString());

	    prodEco = (KETProdChangeOrder) PersistenceHelper.manager.modify(prodEco);
	    Kogger.debug(getClass(), "modifyProdEco(Delete ECR) -------------------->2 ");
	    // Delete All Related ECR List
	    ArrayList<String> delEcrOidList = delObjListHash.get("ecrLinkOidList");
	    KETProdChangeLink ecrLink = null;

	    for (int dEcrCnt = 0; dEcrCnt < delEcrOidList.size(); dEcrCnt++) {
		ecrLink = (KETProdChangeLink) CommonUtil.getObject(delEcrOidList.get(dEcrCnt));
		PersistenceHelper.manager.delete(ecrLink);
	    }
	    Kogger.debug(getClass(), "modifyProdEco(Delete Part) -------------------->3 ");
	    // Delete All Related Part List
	    ArrayList<String> delPartOidList = delObjListHash.get("partLinkOidList");
	    KETProdECOPartLink partLink = null;

	    for (int dPartCnt = 0; dPartCnt < delPartOidList.size(); dPartCnt++) {
		partLink = (KETProdECOPartLink) CommonUtil.getObject(delPartOidList.get(dPartCnt));
		PersistenceHelper.manager.delete(partLink);
	    }

	    // Delete AttachFile
	    String strDelFileList = (String) reqData.get("delFileList");
	    if (strDelFileList.length() > 0) {
		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}

		QueryResult rs = ContentHelper.service.getContentsByRole(prodEco, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String itemStr = allObj.toString();
		    for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			if (delFileList.get(delFileCnt).equals(itemStr)) {
			    Kogger.debug(getClass(), "???????????");
			    prodEco = (KETProdChangeOrder) E3PSContentHelper.service.delete(prodEco, allContentItem);
			}
		    }
		}
	    }

	    Kogger.debug(getClass(), "modifyProdEco(Add Part) -------------------->7 ");
	    // WTPart Relation
	    ReferenceFactory rfTmp = new ReferenceFactory();
	    Hashtable<String, String> partHash = null;
	    WTPart refPart = null;
	    KETProdECOPartLink partRef = null;

	    for (int pCnt = 0; pCnt < refPartList.size(); pCnt++) {
		partHash = refPartList.get(pCnt);
		refPart = (WTPart) rfTmp.getReference(partHash.get("partOid")).getObject();

		partRef = KETProdECOPartLink.newKETProdECOPartLink(refPart, prodEco);
		partRef.setRelatedDieNo(partHash.get("dieNo"));
		partRef.setExpectCost(partHash.get("expectCost"));
		partRef.setSecureBudgetYn(partHash.get("secureBudgetYn"));

		PersistenceHelper.manager.save(partRef);
	    }
	    Kogger.debug(getClass(), "modifyProdEco(Add ECR) -------------------->8 ");
	    // ProdECR Relation
	    KETProdChangeRequest refEcr = null;
	    KETProdChangeLink ecrRef = null;

	    if (ecrList != null) {
		for (int ecrCnt = 0; ecrCnt < ecrList.length; ecrCnt++) {
		    refEcr = (KETProdChangeRequest) rfTmp.getReference(ecrList[ecrCnt]).getObject();

		    ecrRef = KETProdChangeLink.newKETProdChangeLink(prodEco, refEcr);
		    PersistenceHelper.manager.save(ecrRef);

		}
	    }
	    Kogger.debug(getClass(), "modifyProdEco(Add File) -------------------->9 ");
	    // Attachfile Relation
	    if (attachFileList != null) {
		for (int fileCnt = 0; fileCnt < attachFileList.size(); fileCnt++) {
		    prodEco = (KETProdChangeOrder) E3PSContentHelper.service.attach((ContentHolder) prodEco,
			    (WBFile) attachFileList.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    ecoOid = CommonUtil.getOIDString(prodEco);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}
	return ecoOid;
    }

    @Override
    public String completeRegistMoldEco(KETMoldChangeOrder moldEco, ArrayList ketMoldECALinkVOList) throws WTException {
	String ecoOid = "";
	Transaction trx = new Transaction();
	try {
	    trx.start();

	    /*
	     * ECA 없이 등록완료를 할 경우 도면으로 ECO담당자를 담당자로 하여 빈 껍데기를 만들고 있다. 왜?
	     */
	    KETMoldECALinkVO ketMoldECALinkVO = null;
	    MoldEcoBeans beans = new MoldEcoBeans();
	    int size = ketMoldECALinkVOList.size();
	    for (int i = 0; i < size; i++) {
		ketMoldECALinkVO = (KETMoldECALinkVO) ketMoldECALinkVOList.get(i);
		// 아래 로직으로 보면 IF절은 필요도 없다. 빈 껍데기를 만들때 1로 무조건 set 하고 있기때문이다.
		if (ketMoldECALinkVO.getActivityType().equals("1")) {
		    beans.saveMoldChangeActivity(moldEco, ketMoldECALinkVO);
		}
	    }

	    // 금형 ECO 결재 상태를 EXCUTEACTIVITY=활동수행으로 변경한다.
	    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldEco, State.toState("EXCUTEACTIVITY"), true);

	    // 금형 ECO와 관련된 모든 ECA 결재상태를 INWORK=작업중으로 변경한다.
	    /*
	     * StandardKETWfmService.java 에서 PBO의 상태변경시 이벤트 처리로 위 주석 작업을 하고 있다.
	     */

	    ecoOid = KETObjectUtil.getOidString(moldEco);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ecoOid;
    }

    @Override
    public ArrayList<Hashtable<String, String>> saveMoldStdPartLine(String docLinkOid, String[] partOidList, String[] partNoList,
	    String[] chgTypeList, String[] descList, String ecoOid) throws WTException {
	ArrayList<Hashtable<String, String>> partLineList = new ArrayList<Hashtable<String, String>>();

	Transaction trx = new Transaction();
	MoldStdPartBeans beans = new MoldStdPartBeans();
	QueryResult qr = null;
	KETMoldStdPartLink partLink = null;
	KETMoldStdPartLine partLine = null;

	try {
	    trx.start();

	    KETMoldECADocLink docLink = (KETMoldECADocLink) KETObjectUtil.getObject(docLinkOid);

	    // Check Exist Line
	    boolean isExist = beans.isExistStdPartLine(docLinkOid);

	    // Delete All Line
	    if (isExist) {
		qr = PersistenceHelper.manager.navigate(docLink, "part", KETMoldStdPartLink.class);
		while (qr.hasMoreElements()) {
		    PersistenceHelper.manager.delete((KETMoldStdPartLine) qr.nextElement());
		}
	    }

	    // Add Line
	    if (partOidList != null) {
		for (int addCnt = 0; addCnt < partOidList.length; addCnt++) {
		    partLine = KETMoldStdPartLine.newKETMoldStdPartLine();
		    partLine.setPartOid(partOidList[addCnt]);
		    partLine.setPartNo(partNoList[addCnt]);
		    partLine.setChangeType(chgTypeList[addCnt]);
		    partLine.setDescription(StringUtil.checkNull(descList[addCnt]));
		    partLine.setMoldEcoOid(ecoOid);

		    PersistenceHelper.manager.save(partLine);

		    partLink = KETMoldStdPartLink.newKETMoldStdPartLink(docLink, partLine);

		    PersistenceHelper.manager.save(partLink);
		}
	    }

	    partLineList = beans.getStdPartLineList(docLinkOid);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}
	return partLineList;
    }

    @Override
    public void updateDailyMoldPlanStatus(KETMoldChangePlan plan) throws WTException {

	MoldPlanBeans beans = new MoldPlanBeans();

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    String currentProcess = "";

	    currentProcess = beans.getCurrentProcess(plan);

	    plan.setCurrentProcess(currentProcess);
	    plan.setCurrentProcPlanDate(beans.getCurrentProcPlanDate(plan, currentProcess));
	    plan.setScheduleStatus(beans.getCurrentPlanStatus(plan));

	    PersistenceHelper.manager.modify(plan);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

    }

    @Override
    public String createProdEcr(KETParamMapUtil paramMap, Vector attachFiles) throws WTException {
	String ECR_OID = null;

	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    Hashtable<String, String> reqData = new Hashtable<String, String>();

	    reqData.put("ecr_title", paramMap.getString("ecr_title"));
	    reqData.put("dev_yn", paramMap.getString("dev_yn"));
	    reqData.put("div_flag", paramMap.getString("div_flag"));
	    reqData.put("project_oid", paramMap.getString("project_oid"));
	    // reqData.put("chg_type", paramMap.getString("cmd") uploader.getFormParameter("chg_type") );
	    reqData.put("chg_reason", paramMap.getString("chg_reason"));
	    reqData.put("other_reason", paramMap.getString("other_reason"));
	    reqData.put("ecr_desc", paramMap.getString("ecr_desc"));
	    reqData.put("ecr_effect", paramMap.getString("ecr_effect"));

	    reqData.put("reviewRequestDate", paramMap.getString("reviewRequestDate"));
	    reqData.put("carTypeOid", paramMap.getString("carTypeOid"));
	    reqData.put("carTypeCode", paramMap.getString("carTypeCode"));
	    reqData.put("subjectOid", paramMap.getString("subjectOid"));
	    reqData.put("subjectCode", paramMap.getString("subjectCode"));
	    reqData.put("chargeOid", paramMap.getString("chargeOid"));
	    reqData.put("chargeName", paramMap.getString("chargeName"));
	    reqData.put("moldChangeOid", paramMap.getString("moldChangeOid"));
	    reqData.put("costChangeOid", paramMap.getString("costChangeOid"));
	    reqData.put("emergencyPositionOid", paramMap.getString("emergencyPositionOid"));
	    // 차종
	    reqData.put("carTypeCode", paramMap.getString("carTypeCode"));
	    reqData.put("carTypeName", paramMap.getString("carTypeName"));

	    reqData.put("webEditor", paramMap.getString("webEditor"));
	    reqData.put("webEditorText", paramMap.getString("webEditorText"));
	    reqData.put("webEditor1", paramMap.getString("webEditor1"));
	    reqData.put("webEditorText1", paramMap.getString("webEditorText1"));

	    String[] partList = paramMap.getStringArray("relPartOid");

	    String[] reqCommentList = paramMap.getStringArray("req_comment");
	    String[] issueList = paramMap.getStringArray("relIssueOid");

	    // 관련품질문제
	    String[] dqmList = paramMap.getStringArray("relDqmOid");

	    ProdEcrBeans beans = new ProdEcrBeans();
	    reqData.put("ecr_id", beans.getNewProdEcrId());

	    WTPart[] refParts = null;
	    ProjectIssue[] refIssues = null;

	    KETProdChangeRequest prodEcr = KETProdChangeRequest.newKETProdChangeRequest();

	    prodEcr.setEcrId(reqData.get("ecr_id"));
	    prodEcr.setEcrName(reqData.get("ecr_title"));
	    prodEcr.setDevYn(reqData.get("dev_yn"));
	    prodEcr.setDivisionFlag(reqData.get("div_flag"));
	    prodEcr.setProjectOid(reqData.get("project_oid"));
	    // prodEcr.setChangeType( reqData.get( "chg_type" ) );
	    prodEcr.setChangeReason(reqData.get("chg_reason"));
	    prodEcr.setOtherChangeReasonDesc(reqData.get("other_reason"));
	    prodEcr.setEcrDesc(reqData.get("ecr_desc"));
	    prodEcr.setExpectEffect(reqData.get("ecr_effect"));

	    PeopleData pData = new PeopleData();
	    prodEcr.setDeptName(pData.departmentName);// ??? ???????? ???

	    // [START] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현
	    // ecrPartLink.setChangeReqComment( reqCommentList[pCnt] );
	    prodEcr.setName(prodEcr.getEcrName());
	    // [END] [PLM System 1차 고도화] Windchill 10.2로 업그레이드 시 Master.Name의 NotNull에 대한 처리, 2014-06-16, 김태현

	    // ECR 전체 상태를 '작성중'으로 set 한다.
	    prodEcr.setEcrStateState(State.toState("INWORK"));

	    WTContainerRef containerRef = WCUtil.getWTContainerRef();
	    String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.ecr");
	    LifeCycleHelper.setLifeCycle(prodEcr, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

	    // Folder Setting
	    String folderPath = "";
	    if (prodEcr.getDivisionFlag().equals("C")) {
		// ?????????
		folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
	    } else if (prodEcr.getDivisionFlag().equals("K")) {
		// Car Division
		folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");
	    } else {
		// ????????
		folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
	    }

	    // folderPath += DateUtil.getThisYear();
	    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
	    FolderHelper.assignLocation((FolderEntry) prodEcr, folder);

	    prodEcr = (KETProdChangeRequest) PersistenceHelper.manager.save(prodEcr);

	    // WTPart Relation
	    ReferenceFactory rfTmp = new ReferenceFactory();
	    refParts = new WTPart[partList.length];

	    for (int i = 0; i < partList.length; i++) {
		refParts[i] = (WTPart) rfTmp.getReference(partList[i]).getObject();
	    }

	    KETProdECRPartLink partRef = null;
	    if (refParts != null) {
		for (int pCnt = 0; pCnt < refParts.length; pCnt++) {
		    partRef = KETProdECRPartLink.newKETProdECRPartLink(refParts[pCnt], prodEcr);
		    if (reqCommentList != null && reqCommentList.length > 0)
			partRef.setChangeReqComment(reqCommentList[pCnt]);

		    PersistenceHelper.manager.save(partRef);
		}
	    }

	    // ProjectIssue Relation
	    if (issueList != null && issueList.length > 0) {
		refIssues = new ProjectIssue[issueList.length];

		for (int i = 0; i < issueList.length; i++) {
		    refIssues[i] = (ProjectIssue) rfTmp.getReference(issueList[i]).getObject();
		}

		KETProdECRIssueLink issueRef = null;
		if (refIssues != null) {
		    for (int iCnt = 0; iCnt < refIssues.length; iCnt++) {
			issueRef = KETProdECRIssueLink.newKETProdECRIssueLink(refIssues[iCnt], prodEcr);
			PersistenceHelper.manager.save(issueRef);
		    }
		}
	    }

	    // 관련품질문제 Relation
	    if (dqmList != null && dqmList.length > 0) {

		for (int i = 0; i < dqmList.length; i++) {
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(dqmList[i]);
		    KETDqmAction ketDqmAction = ketDqmHeader.getAction();
		    if (ketDqmAction == null) {

			// 관련품질문제 정보가 온전하지 않습니다.
			throw new WTException(messageService.getString("e3ps.message.ket_message", "04670"));

		    } else {
			KETEcrDqmLink ketEcrDqmLink = KETEcrDqmLink.newKETEcrDqmLink(prodEcr, ketDqmAction);
			PersistenceHelper.manager.save(ketEcrDqmLink);
		    }

		}
	    }

	    // Attachfile Relation
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    prodEcr = (KETProdChangeRequest) E3PSContentHelper.service.attach((ContentHolder) prodEcr,
			    (WBFile) attachFiles.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    // 제품 ECR 확장팩
	    this.modifyEcrExpansion(prodEcr, reqData);

	    ECR_OID = CommonUtil.getOIDString(prodEcr);

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ECR_OID;
    }

    @Override
    public String modifyProdEcr(String ecrOid, KETParamMapUtil paramMap, Vector attachFiles) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    Hashtable<String, String> reqData = new Hashtable<String, String>();

	    reqData.put("oid", paramMap.getString("oid"));
	    reqData.put("ecr_title", paramMap.getString("ecr_title"));
	    reqData.put("dev_yn", paramMap.getString("dev_yn"));
	    reqData.put("div_flag", paramMap.getString("div_flag"));
	    reqData.put("project_oid", paramMap.getString("project_oid"));
	    // reqData.put("chg_type", paramMap.getString("chg_type") );
	    reqData.put("chg_reason", paramMap.getString("chg_reason"));
	    reqData.put("other_reason", paramMap.getString("other_reason"));
	    reqData.put("ecr_desc", paramMap.getString("ecr_desc"));
	    reqData.put("ecr_effect", paramMap.getString("ecr_effect"));
	    reqData.put("delFileList", paramMap.getString("deleteFileList"));

	    reqData.put("reviewRequestDate", paramMap.getString("reviewRequestDate"));
	    reqData.put("carTypeOid", paramMap.getString("carTypeOid"));
	    reqData.put("carTypeCode", paramMap.getString("carTypeCode"));
	    reqData.put("subjectOid", paramMap.getString("subjectOid"));
	    reqData.put("subjectCode", paramMap.getString("subjectCode"));
	    reqData.put("chargeOid", paramMap.getString("chargeOid"));
	    reqData.put("chargeName", paramMap.getString("chargeName"));
	    reqData.put("moldChangeOid", paramMap.getString("moldChangeOid"));
	    reqData.put("costChangeOid", paramMap.getString("costChangeOid"));
	    reqData.put("emergencyPositionOid", paramMap.getString("emergencyPositionOid"));
	    // 차종
	    reqData.put("carTypeCode", paramMap.getString("carTypeCode"));
	    reqData.put("carTypeName", paramMap.getString("carTypeName"));

	    reqData.put("webEditor", paramMap.getString("webEditor"));
	    reqData.put("webEditorText", paramMap.getString("webEditorText"));
	    reqData.put("webEditor1", paramMap.getString("webEditor1"));
	    reqData.put("webEditorText1", paramMap.getString("webEditorText1"));

	    String[] partList = paramMap.getStringArray("relPartOid");

	    String[] reqCommentList = paramMap.getStringArray("req_comment");

	    Hashtable<String, Object> issueHash = new Hashtable<String, Object>();
	    String[] issueList = paramMap.getStringArray("relIssueOid");
	    if (issueList != null) {
		issueHash.put("issueList", issueList);
	    }

	    ProdEcrBeans beans = new ProdEcrBeans();
	    ArrayList<Hashtable<String, String>> delPartLinkList = beans.getProdEcrPartList(reqData.get("oid"));
	    ArrayList<KETProdECRIssueLink> delIssueList = beans.getProdEcrIssueList(reqData.get("oid"));

	    Hashtable partHash = new Hashtable();
	    partHash.put("partList", partList);
	    partHash.put("delPartLinkList", delPartLinkList);
	    partHash.put("reqCommentList", reqCommentList);

	    issueHash.put("delIssueList", delIssueList);

	    // 관련품질문제
	    String[] dqmList = paramMap.getStringArray("relDqmOid");

	    ReferenceFactory rf = new ReferenceFactory();
	    KETProdChangeRequest prodEcr = (KETProdChangeRequest) rf.getReference(reqData.get("oid")).getObject();

	    prodEcr.setEcrName(reqData.get("ecr_title"));
	    prodEcr.setDevYn(reqData.get("dev_yn"));
	    prodEcr.setDivisionFlag(reqData.get("div_flag"));
	    prodEcr.setProjectOid(reqData.get("project_oid"));
	    // prodEcr.setChangeType( reqData.get( "chg_type" ) );
	    prodEcr.setChangeReason(reqData.get("chg_reason"));
	    prodEcr.setOtherChangeReasonDesc(reqData.get("other_reason"));
	    prodEcr.setEcrDesc(reqData.get("ecr_desc"));
	    prodEcr.setExpectEffect(reqData.get("ecr_effect"));

	    // ECR 전체 상태를 '작성중'으로 set 한다.
	    prodEcr.setEcrStateState(State.toState("INWORK"));

	    prodEcr = (KETProdChangeRequest) PersistenceHelper.manager.save(prodEcr);
	    prodEcr = (KETProdChangeRequest) PersistenceHelper.manager.refresh(prodEcr);

	    partList = (String[]) partHash.get("partList");

	    delPartLinkList = (ArrayList<Hashtable<String, String>>) partHash.get("delPartLinkList");

	    reqCommentList = (String[]) partHash.get("reqCommentList");

	    // Delete All RelatedPartList
	    Hashtable<String, String> partLink = null;
	    for (int dPartCnt = 0; dPartCnt < delPartLinkList.size(); dPartCnt++) {
		partLink = delPartLinkList.get(dPartCnt);

		KETProdECRPartLink ecrPartLink = (KETProdECRPartLink) CommonUtil.getObject(partLink.get("partlink_oid"));
		PersistenceHelper.manager.delete(ecrPartLink);
	    }

	    // Add All RelatedPartList
	    ReferenceFactory rfTmp = new ReferenceFactory();
	    WTPart[] refParts = new WTPart[partList.length];

	    for (int pCnt = 0; pCnt < partList.length; pCnt++) {
		refParts[pCnt] = (WTPart) rfTmp.getReference(partList[pCnt]).getObject();
	    }

	    if (refParts != null) {
		for (int pCnt = 0; pCnt < refParts.length; pCnt++) {
		    KETProdECRPartLink ecrPartLink = KETProdECRPartLink.newKETProdECRPartLink(refParts[pCnt], prodEcr);

		    // [START] [PLM System 1차 고도화] 관련부품의 요청사항을 입력하지 않을 경우를 대비한 방어코드, 2014-06-16, 김태현
		    // ecrPartLink.setChangeReqComment( reqCommentList[pCnt] );
		    try {
			ecrPartLink.setChangeReqComment(reqCommentList[pCnt]);
		    } catch (java.lang.ArrayIndexOutOfBoundsException aioobe) {
			Kogger.error(getClass(), aioobe);
		    }
		    // [END] [PLM System 1차 고도화] 관련부품의 요청사항을 입력하지 않을 경우를 대비한 방어코드, 2014-06-16, 김태현

		    PersistenceHelper.manager.save(ecrPartLink);
		}
	    }

	    ArrayList<KETProdECRIssueLink> delIssueLinkList = (ArrayList<KETProdECRIssueLink>) issueHash.get("delIssueList");

	    // Delete All Issue Link
	    KETProdECRIssueLink issueLink = null;
	    if (delIssueLinkList != null && delIssueLinkList.size() > 0) {
		for (int dIssueCnt = 0; dIssueCnt < delIssueLinkList.size(); dIssueCnt++) {
		    issueLink = delIssueLinkList.get(dIssueCnt);

		    PersistenceHelper.manager.delete(issueLink);
		}
	    }

	    issueList = (String[]) issueHash.get("issueList");

	    if (issueList != null) {
		ProjectIssue[] refIssues = new ProjectIssue[issueList.length];

		for (int pCnt = 0; pCnt < issueList.length; pCnt++) {
		    refIssues[pCnt] = (ProjectIssue) rfTmp.getReference(issueList[pCnt]).getObject();
		}

		for (int pCnt = 0; pCnt < refIssues.length; pCnt++) {
		    issueLink = KETProdECRIssueLink.newKETProdECRIssueLink(refIssues[pCnt], prodEcr);
		    PersistenceHelper.manager.save(issueLink);
		}
	    }

	    // 관련품질문제 Relation
	    // Delete All
	    // ECR 로 찾는다.
	    QueryResult qr = PersistenceHelper.manager.navigate(prodEcr, "dqm", KETEcrDqmLink.class, false);
	    while (qr.hasMoreElements()) {
		KETEcrDqmLink ketEcrDqmLink = (KETEcrDqmLink) qr.nextElement();
		PersistenceHelper.manager.delete(ketEcrDqmLink);

	    }
	    // Create All
	    if (dqmList != null && dqmList.length > 0) {

		for (int i = 0; i < dqmList.length; i++) {
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(dqmList[i]);
		    KETDqmAction ketDqmAction = ketDqmHeader.getAction();
		    if (ketDqmAction == null) {

			// 관련품질문제 정보가 온전하지 않습니다.
			ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04670");

		    } else {
			KETEcrDqmLink ketEcrDqmLink = KETEcrDqmLink.newKETEcrDqmLink(prodEcr, ketDqmAction);
			PersistenceHelper.manager.save(ketEcrDqmLink);
		    }

		}
	    }

	    // Delete AttachFile
	    if (reqData.get("delFileList").length() > 0) {
		String strDelFileList = reqData.get("delFileList");

		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}

		QueryResult rs = ContentHelper.service.getContentsByRole(prodEcr, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String itemStr = allObj.toString();
		    for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			if (delFileList.get(delFileCnt).equals(itemStr)) {
			    prodEcr = (KETProdChangeRequest) E3PSContentHelper.service.delete(prodEcr, allContentItem);
			}
		    }
		}
	    }

	    // Add AttachFile
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    prodEcr = (KETProdChangeRequest) E3PSContentHelper.service.attach((ContentHolder) prodEcr,
			    (WBFile) attachFiles.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    prodEcr = (KETProdChangeRequest) PersistenceHelper.manager.refresh(prodEcr);

	    // 제품 ECR 확장팩
	    this.modifyEcrExpansion(prodEcr, reqData);

	    /*
	     * ecrOid = CommonUtil.getOIDString(prodEcr);
	     */

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String modifyEcrCompetentDepartment(Hashtable<String, String> reqData, Vector attachFiles) throws WTException {
	String competentOid = "";
	WTChangeRequest2 ecr = null;

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    // ECR 기본사항
	    ReferenceFactory rf = new ReferenceFactory();
	    ecr = (WTChangeRequest2) rf.getReference(reqData.get("oid")).getObject();

	    // 주관부서
	    KETCompetentDepartment competent = null;
	    // Link 처리
	    KETEcrCompetentLink link = null;
	    // ECR 로 찾는다.
	    QueryResult qr = PersistenceHelper.manager.navigate(ecr, "competent", KETEcrCompetentLink.class, false);
	    while (qr.hasMoreElements()) {
		link = (KETEcrCompetentLink) qr.nextElement();
		competent = link.getCompetent();

	    }

	    // Insert
	    if (!PersistenceHelper.isPersistent(competent)) {

		competent = KETCompetentDepartment.newKETCompetentDepartment();

		WTContainerRef containerRef = WCUtil.getWTContainerRef();
		String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.competent");
		LifeCycleHelper.setLifeCycle(competent, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

		// Folder Setting
		String folderPath = "";
		if (ecr instanceof KETProdChangeRequest) {

		    if (((KETProdChangeRequest) ecr).getDivisionFlag().equals("C")) {
			// /Default/자동차사업부/설계변경/
			folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
		    } else if (((KETProdChangeRequest) ecr).getDivisionFlag().equals("K")) {
			// /Default/KETS/설계변경/
			folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");
		    } else {
			// /Default/전자사업부/설계변경/
			folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
		    }

		} else {

		    if (KETObjectUtil.getCurrentUserGroup().equals("자동차사업부") || KETObjectUtil.getCurrentUserGroup().equals("자동차사업부_구매")) {
			// Car Division
			folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
		    } else if (KETObjectUtil.getCurrentUserGroup().equals("KETS")) {
			// KETS Division
			folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");
		    } else { // if (KETObjectUtil.getCurrentUserGroup().equals("전자사업부")) {
			     // Electronic Division
			folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
		    }

		}

		folderPath += DateUtil.getThisYear();
		SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
		FolderHelper.assignLocation((FolderEntry) competent, folder);

	    }
	    // Update
	    else {
		// Do nothing..!!

	    }

	    // 주관부서
	    String subjectOid = (String) reqData.get("subjectOid");
	    Department subject = (subjectOid != null && !subjectOid.equals("")) ? (Department) rf.getReference(subjectOid).getObject()
		    : null;
	    competent.setSubject(subject);

	    String subjectCode = (String) reqData.get("subjectCode");
	    subjectCode = ((subjectCode == null || subjectCode.equals("")) && subject != null) ? subject.getCode() : subjectCode;
	    competent.setSubjectCode(subjectCode);

	    // 담당자
	    // 서버에 저장되어 있는 현재 담당자
	    WTUser currentCharge = competent.getCharge();
	    // 클라이언트에서 넘어온 변경 담당자
	    String chargeOid = (String) reqData.get("chargeOid");
	    WTUser charge = (chargeOid != null && !chargeOid.equals("")) ? (WTUser) rf.getReference(chargeOid).getObject() : null;
	    competent.setCharge(charge);

	    String chargeName = (String) reqData.get("chargeName");
	    chargeName = ((chargeName == null || chargeName.equals("")) && charge != null) ? charge.getName() : chargeName;
	    competent.setChargeName(chargeName);

	    // 회신기한
	    String reviewRequestDate = (String) reqData.get("reviewRequestDate");
	    competent.setReplyDeadline(DateUtil.convertStartDate2(reviewRequestDate));

	    // 검토결과
	    String reviewResultOid = (String) reqData.get("reviewResultOid");
	    NumberCode numberCode = (reviewResultOid != null && !reviewResultOid.equals("")) ? (NumberCode) rf
		    .getReference(reviewResultOid).getObject() : null;
	    competent.setReview(numberCode);

	    String reviewCode = (numberCode != null) ? numberCode.getCode() : null;
	    competent.setReviewCode(reviewCode);

	    // 회의필요여부
	    String mReqOid = (String) reqData.get("mReqOid");
	    numberCode = (mReqOid != null && !mReqOid.equals("")) ? (NumberCode) rf.getReference(mReqOid).getObject() : null;

	    String meetingCode = (numberCode != null) ? numberCode.getCode() : null;
	    competent.setMeetingCode(meetingCode);

	    // WebEditor
	    competent.setWebEditor((String) reqData.get("webEditor"));
	    competent.setWebEditorText((String) reqData.get("webEditorText"));

	    // 저장
	    competent = (KETCompetentDepartment) PersistenceHelper.manager.save(competent);

	    // Link
	    if (!PersistenceHelper.isPersistent(link)) {
		link = KETEcrCompetentLink.newKETEcrCompetentLink(ecr, competent);
		PersistenceHelper.manager.save(link);
	    }

	    // 첨부파일
	    // Delete AttachFile
	    if (reqData.get("delFileList").length() > 0) {
		String strDelFileList = reqData.get("delFileList");

		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}

		QueryResult rs = ContentHelper.service.getContentsByRole(competent, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String itemStr = allObj.toString();
		    for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			if (delFileList.get(delFileCnt).equals(itemStr)) {
			    competent = (KETCompetentDepartment) E3PSContentHelper.service.delete(competent, allContentItem);
			}
		    }
		}
	    }

	    // Add AttachFile
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    competent = (KETCompetentDepartment) E3PSContentHelper.service.attach((ContentHolder) competent,
			    (WBFile) attachFiles.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    // 마무리
	    competent = (KETCompetentDepartment) PersistenceHelper.manager.refresh(competent);
	    competentOid = CommonUtil.getOIDString(competent);

	    // 제품, 금형 ECR 확장팩
	    KETChangeRequestExpansion expansion = null;
	    // ECR 로 찾는다.
	    QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
	    spec.appendWhere(
		    new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(ecr)),
		    new int[] { 0 });
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
		expansion = (KETChangeRequestExpansion) result.nextElement();
		expansion.setCharge(competent.getCharge());

		expansion = (KETChangeRequestExpansion) PersistenceHelper.manager.save(expansion);
		expansion = (KETChangeRequestExpansion) PersistenceHelper.manager.refresh(expansion);

	    }

	    // 기각일 경우
	    String command = StringUtils.stripToEmpty(reqData.get("cmd"));
	    if (command.equalsIgnoreCase("Reject")) {

		// 기각도 승인완료로 처리한다.
		// StandardKETWfmService.java 에서 상태 변경 이벤트 처리 메쏘드에서 ECR.ecrStateState 에 '검토완료(CONSIDERED)'를 set 할 것이다.
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) competent, State.toState("APPROVED"), true);

		// 메일 발송 김근우
		WTChangeRequest2 wtChangeRequest2 = (WTChangeRequest2) CommonUtil.getObject(reqData.get("oid"));
		List<WTUser> toUserList = new ArrayList<WTUser>();

		if (wtChangeRequest2 instanceof KETProdChangeRequest) {
		    KETProdChangeRequest mailEcr = (KETProdChangeRequest) wtChangeRequest2;
		    Vector vec = WorkflowSearchHelper.manager.getApprovalHistory(WorkflowSearchHelper.manager.getMaster(mailEcr));
		    for (int i = 0; i < vec.size(); i++) {
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
			toUserList.add((WTUser) history.getOwner().getObject());

		    }
		}
		// 금형 ECR
		else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
		    KETMoldChangeRequest mailEcr = (KETMoldChangeRequest) wtChangeRequest2;
		    Vector vec = WorkflowSearchHelper.manager.getApprovalHistory(WorkflowSearchHelper.manager.getMaster(mailEcr));
		    for (int i = 0; i < vec.size(); i++) {
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
			toUserList.add((WTUser) history.getOwner().getObject());

		    }
		}
		KETMailHelper.service.sendFormMail("08023", "NoticeMailLine2.html", wtChangeRequest2, (WTUser) SessionHelper.manager
		        .getPrincipalReference().getPrincipal(), toUserList);

	    } else {

		if (expansion != null) {

		    // 현재 ECR 전체 상태가 '검토'일 경우에만
		    State ecrStateState = null;
		    if (ecr instanceof KETProdChangeRequest) {
			KETProdChangeRequest ketProdChangeRequest = (KETProdChangeRequest) ecr;
			ecrStateState = ketProdChangeRequest.getEcrStateState();
		    } else if (ecr instanceof KETMoldChangeRequest) {
			KETMoldChangeRequest ketMoldChangeRequest = (KETMoldChangeRequest) ecr;
			ecrStateState = ketMoldChangeRequest.getEcrStateState();
		    }

		    if (ecrStateState != null && ecrStateState.equals(State.toState("CONSIDER"))) {

			// 담당자가 바뀌었을 경우 Todo를 다시 처리하여야 한다.
			if (competent.getCharge() != null && currentCharge != null && !competent.getCharge().equals(currentCharge)) {

			    // 기존 To-Do 제거
			    WorkflowSearchHelper.manager.taskComplete(expansion);

			    // To-Do 생성
			    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			    WfTemplateObject paramWfTemplateObject = null;

			    while (allTemplates.hasMoreElements()) {
				WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

				if (wfTemplate.getName().equals("KET_TODO_WF")) {
				    paramWfTemplateObject = wfTemplate;
				}
			    }

			    Team team = (Team) expansion.getTeamId().getObject();
			    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
				    expansion.getContainerReference());
			    ProcessData data = process.getContext();
			    WfEngineServerHelper.service.setPrimaryBusinessObject(process, expansion);
			    data.setValue("charge", expansion.getCharge());
			    WfEngineHelper.service.startProcess(process, data, 1);

			    // 담당자 변경 메일발송
			    // 김근우 mail 발송 위해 담당자 변경시 map set한다.
			    HashMap<String, Object> map = new HashMap<String, Object>();
			    map.put("type", WFMProperties.getInstance().getString("wfm.type.ecr.competent"));

			    WTChangeRequest2 wtChangeRequest2 = expansion.getEcr();
			    // 제품 ECR
			    if (wtChangeRequest2 instanceof KETProdChangeRequest) {
				map.put("mailPboName", WFMProperties.getInstance().getString("wfm.type.prodco"));
				KETProdChangeRequest inecr = (KETProdChangeRequest) wtChangeRequest2;
				map.put("subject1", inecr.getEcrName());
			    }
			    // 금형 ECR
			    else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
				map.put("mailPboName", WFMProperties.getInstance().getString("wfm.type.moldco"));
				KETMoldChangeRequest inecr = (KETMoldChangeRequest) wtChangeRequest2;
				map.put("subject1", inecr.getEcrName());

			    }

			    PeopleData beforeUser = new PeopleData(currentCharge);
			    PeopleData afterUser = new PeopleData(charge);
			    map.put("column1", beforeUser.departmentName + "&nbsp;" + beforeUser.name + "&nbsp;" + beforeUser.duty);
			    map.put("column2", afterUser.departmentName + "&nbsp;" + afterUser.name + "&nbsp;" + afterUser.duty);
			    map.put("subject3Date", DateUtil.getCurrentDateString("d"));
			    map.put("pbo", expansion);
			    map.put("from", currentCharge);
			    List<WTUser> to = new ArrayList();
			    to.add((WTUser) charge);
			    map.put("to", to);

			    KETMailHelper.service.sendFormMail("08022", "ChargerChangeNoticeMail.html", map);
			    // 메일 발송 끝

			    // // To-Do 공지
			    // try {
			    // WTUser member = (WTUser) expansion.getCharge();
			    // WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
			    // WTPrincipalReference toUser = WTPrincipalReference.newWTPrincipalReference(prin);
			    // Hashtable contentHash = MailUtil.getMailContent("approval", expansion, toUser.getName());
			    // String templateName = CommonUtil.getMailTemplateName(toUser.getName(), "TaskNoticeMail");
			    // String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
			    // Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents);
			    // if (hash != null)
			    // MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
			    //
			    // } catch (Exception e) {
			    //
			    // }
			}

		    }

		}

	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return competentOid;
    }

    @Override
    public String modifyEcrMeetingMinutes(Hashtable<String, String> reqData, Vector attachFiles, Hashtable<String, String[]> ecnList)
	    throws WTException {
	String meetingOid = "";
	WTChangeRequest2 ecr = null;

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    // ECR 기본사항
	    ReferenceFactory rf = new ReferenceFactory();
	    ecr = (WTChangeRequest2) rf.getReference(reqData.get("oid")).getObject();

	    // 회의록
	    KETMeetingMinutes meeting = null;
	    // Link 처리
	    KETEcrMeetingLink link = null;
	    // ECR 로 찾는다.
	    QueryResult qr = PersistenceHelper.manager.navigate(ecr, "meeting", KETEcrMeetingLink.class, false);
	    while (qr.hasMoreElements()) {
		link = (KETEcrMeetingLink) qr.nextElement();
		meeting = link.getMeeting();

	    }

	    // Insert
	    // Folder Setting
	    String folderPath = "";
	    if (!PersistenceHelper.isPersistent(meeting)) {

		meeting = KETMeetingMinutes.newKETMeetingMinutes();

		WTContainerRef containerRef = WCUtil.getWTContainerRef();
		String lcName = ECMProperties.getInstance().getString("ecm.lifecycle.meeting");
		LifeCycleHelper.setLifeCycle(meeting, LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

		if (ecr instanceof KETProdChangeRequest) {

		    if (((KETProdChangeRequest) ecr).getDivisionFlag().equals("C")) {
			// /Default/자동차사업부/설계변경/
			folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
		    } else if (((KETProdChangeRequest) ecr).getDivisionFlag().equals("K")) {
			// /Default/KETS/설계변경/
			folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");
		    } else {
			// /Default/전자사업부/설계변경/
			folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
		    }

		} else {

		    if (KETObjectUtil.getCurrentUserGroup().equals("자동차사업부") || KETObjectUtil.getCurrentUserGroup().equals("자동차사업부_구매")) {
			// Car Division
			folderPath = ECMProperties.getInstance().getString("ecm.folder.car");
		    } else if (KETObjectUtil.getCurrentUserGroup().equals("KETS")) {
			// KETS Division
			folderPath = ECMProperties.getInstance().getString("ecm.folder.kets");
		    } else { // if (KETObjectUtil.getCurrentUserGroup().equals("전자사업부")) {
			     // Electronic Division
			folderPath = ECMProperties.getInstance().getString("ecm.folder.electronic");
		    }

		}

		folderPath += DateUtil.getThisYear();
		SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
		FolderHelper.assignLocation((FolderEntry) meeting, folder);

	    }
	    // Update
	    else {
		// Do nothing..!!

	    }

	    // 회의명
	    meeting.setMeetingName((String) reqData.get("meetingName"));

	    // 회의일시
	    String meetingDate = (String) reqData.get("meetingDate");
	    meeting.setMeetingDate(DateUtil.convertStartDate2(meetingDate));
	    // 회의시간
	    meeting.setMeetingTime((String) reqData.get("meetingTime"));
	    // 회의장소
	    meeting.setMeetingLocation((String) reqData.get("meetingLocation"));

	    // 회의참석자
	    meeting.setAttendance((String) reqData.get("attendance"));

	    // 주관부서
	    String subjectOid = (String) reqData.get("subjectOid");
	    Department subject = (subjectOid != null && !subjectOid.equals("")) ? (Department) rf.getReference(subjectOid).getObject()
		    : null;
	    meeting.setSubject(subject);

	    String subjectCode = (String) reqData.get("subjectCode");
	    subjectCode = ((subjectCode == null || subjectCode.equals("")) && subject != null) ? subject.getCode() : subjectCode;
	    meeting.setSubjectCode(subjectCode);

	    // 담당자
	    String chargeOid = (String) reqData.get("chargeOid");
	    WTUser charge = (chargeOid != null && !chargeOid.equals("")) ? (WTUser) rf.getReference(chargeOid).getObject() : null;
	    meeting.setCharge(charge);

	    String chargeName = (String) reqData.get("chargeName");
	    chargeName = ((chargeName == null || chargeName.equals("")) && charge != null) ? charge.getName() : chargeName;
	    meeting.setChargeName(chargeName);

	    // WebEditor
	    meeting.setWebEditor((String) reqData.get("webEditor"));
	    meeting.setWebEditorText((String) reqData.get("webEditorText"));

	    // 저장
	    meeting = (KETMeetingMinutes) PersistenceHelper.manager.save(meeting);

	    // Link
	    if (!PersistenceHelper.isPersistent(link)) {
		link = KETEcrMeetingLink.newKETEcrMeetingLink(ecr, meeting);
		PersistenceHelper.manager.save(link);
	    }

	    // 첨부파일
	    // Delete AttachFile
	    if (reqData.get("delFileList").length() > 0) {
		String strDelFileList = reqData.get("delFileList");

		Vector delFileList = new Vector();
		StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		while (tokens.hasMoreElements()) {
		    delFileList.addElement(tokens.nextElement());
		}

		QueryResult rs = ContentHelper.service.getContentsByRole(meeting, ContentRoleType.SECONDARY);
		while (rs.hasMoreElements()) {
		    ContentItem allContentItem = (ContentItem) rs.nextElement();
		    Object allObj = allContentItem;
		    String itemStr = allObj.toString();
		    for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			if (delFileList.get(delFileCnt).equals(itemStr)) {
			    meeting = (KETMeetingMinutes) E3PSContentHelper.service.delete(meeting, allContentItem);
			}
		    }
		}
	    }

	    // Add AttachFile
	    if (attachFiles != null) {
		for (int fileCnt = 0; fileCnt < attachFiles.size(); fileCnt++) {
		    meeting = (KETMeetingMinutes) E3PSContentHelper.service.attach((ContentHolder) meeting,
			    (WBFile) attachFiles.get(fileCnt), "", ContentRoleType.SECONDARY);
		}
	    }

	    // ECN
	    KETChangeNotice ecn = null;
	    // Link 처리
	    KETEcrEcnLink ketEcrEcnLink = null;
	    // ECR 로 찾는다.
	    qr = PersistenceHelper.manager.navigate(ecr, "ecn", KETEcrEcnLink.class, false);
	    if (qr.hasMoreElements()) { // while (qr.hasMoreElements()) {
		ketEcrEcnLink = (KETEcrEcnLink) qr.nextElement();
		ecn = ketEcrEcnLink.getEcn();

	    }

	    // Insert
	    if (!PersistenceHelper.isPersistent(ecn)) {
		ecn = KETChangeNotice.newKETChangeNotice();

		// ECN Number
		try {
		    MoldChangeRequestDao moldChangeRequestDao = new MoldChangeRequestDao();
		    String ecnNumber = moldChangeRequestDao.getEcnNumber();
		    ecn.setEcnNumber(ecnNumber);

		} catch (Exception e) {
		    String ecnNumber = String.valueOf(Math.random());
		    ecn.setEcnNumber(ecnNumber);

		}

		// 저장
		WTContainerRef ecnContainerRef = WCUtil.getWTContainerRef();
		String ecnLcName = ECMProperties.getInstance().getString("ecm.lifecycle.ecn");
		LifeCycleHelper.setLifeCycle(ecn, LifeCycleHelper.service.getLifeCycleTemplate(ecnLcName, ecnContainerRef));

		ecn.setContainerReference(ecnContainerRef);

		// Folder Setting
		// ECR것을 그대로 사용한다.
		SubFolder ecnfolder = (SubFolder) FolderHelper.service.getFolder(folderPath, ecnContainerRef);
		FolderHelper.assignLocation((FolderEntry) ecn, ecnfolder);

		ecn = (KETChangeNotice) PersistenceHelper.manager.save(ecn);
		ketEcrEcnLink = KETEcrEcnLink.newKETEcrEcnLink(ecr, ecn);
		ketEcrEcnLink = (KETEcrEcnLink) PersistenceHelper.manager.save(ketEcrEcnLink);

	    }
	    // Update
	    else {
		// Do nothing..!!

	    }

	    // ECA (Update = Delete + Insert)
	    KETChangeActivity eca = null;
	    // Link 처리
	    KETEcnEcaLink ketEcnEcaLink = null;
	    // ECR 로 찾는다.
	    qr = PersistenceHelper.manager.navigate(ecn, "eca", KETEcnEcaLink.class, false);
	    while (qr.hasMoreElements()) {
		ketEcnEcaLink = (KETEcnEcaLink) qr.nextElement();
		eca = ketEcnEcaLink.getEca();

		PersistenceHelper.manager.delete(ketEcnEcaLink);
		PersistenceHelper.manager.delete(eca);
	    }

	    String[] chkPostActArr = ecnList.get("chkPostActArr");
	    String[] ecaUniqueKeyArr = ecnList.get("ecaUniqueKeyArr");
	    String[] docActFlagArr = ecnList.get("docActFlagArr");
	    String[] codenameArr = ecnList.get("codenameArr");
	    String[] relEcaDocWorkerOidArr = ecnList.get("relEcaDocWorkerOidArr");
	    String[] completeRequestDateArr = ecnList.get("completeRequestDateArr");

	    int chkPostActArrLength = (chkPostActArr != null) ? chkPostActArr.length : 0;
	    for (int i = 0; i < chkPostActArrLength; i++) {
		String chkPostAct = chkPostActArr[i];

		int ecaUniqueKeyArrLength = (ecaUniqueKeyArr != null) ? ecaUniqueKeyArr.length : 0;
		for (int j = 0; j < ecaUniqueKeyArrLength; j++) {
		    String ecaUniqueKey = ecaUniqueKeyArr[j];

		    if (ecaUniqueKey != null && !ecaUniqueKey.equals("") && ecaUniqueKey.equals(chkPostAct)) {

			String docActFlag = docActFlagArr[j];
			String codename = codenameArr[j];
			String relEcaDocWorkerOid = relEcaDocWorkerOidArr[j];
			String completeRequestDate = completeRequestDateArr[j];

			eca = KETChangeActivity.newKETChangeActivity();

			// 타입, 후속업무
			NumberCode activity = null;
			try {
			    activity = (NumberCode) rf.getReference(chkPostAct).getObject();
			} catch (Exception e) {
			    // Do nothing..!!
			}
			// 기준정보일 경우
			if (activity != null) {
			    eca.setActivity(activity);
			    eca.setActivityCode(activity.getCode());
			}
			// 사용자 입력일 경우
			else {
			    eca.setCustomType(docActFlag);
			    eca.setCustomCode(chkPostAct);
			    eca.setCustomName(codename);
			}

			// 담당자
			WTUser activityCharge = (relEcaDocWorkerOid != null && !relEcaDocWorkerOid.equals("")) ? (WTUser) rf.getReference(
			        relEcaDocWorkerOid).getObject() : null;
			eca.setCharge(activityCharge);
			String activityChargeName = (activityCharge != null) ? activityCharge.getName() : null;
			eca.setChargeName(activityChargeName);

			// 완료요청일
			eca.setCompleteRequestDate(DateUtil.convertStartDate2(completeRequestDate));

			// 저장
			eca = (KETChangeActivity) PersistenceHelper.manager.save(eca);
			ketEcnEcaLink = KETEcnEcaLink.newKETEcnEcaLink(ecn, eca);
			ketEcnEcaLink = (KETEcnEcaLink) PersistenceHelper.manager.save(ketEcnEcaLink);

			break;
		    }
		} // for (int j = 0; j < ecaUniqueKeyArrLength; j++) {
	    } // for (int i = 0; i < chkPostActArrLength; i++) {

	    // 마무리
	    meeting = (KETMeetingMinutes) PersistenceHelper.manager.refresh(meeting);
	    meetingOid = CommonUtil.getOIDString(meeting);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return meetingOid;
    }

    @Override
    public String modifyEcrExpansion(WTChangeRequest2 ecr, Hashtable<String, String> reqData) throws WTException {
	String expansionOid = "";

	Transaction trx = new Transaction();

	try {
	    trx.start();

	    ReferenceFactory rf = new ReferenceFactory();

	    // 제품, 금형 ECR 확장팩
	    KETChangeRequestExpansion expansion = null;

	    // ECR 로 찾는다.
	    QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
	    spec.appendWhere(
		    new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil.getOIDLongValue(ecr)),
		    new int[] { 0 });
	    QueryResult result = PersistenceHelper.manager.find(spec);
	    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
		expansion = (KETChangeRequestExpansion) result.nextElement();
	    }

	    // Insert
	    if (!PersistenceHelper.isPersistent(expansion)) {
		expansion = KETChangeRequestExpansion.newKETChangeRequestExpansion();

		WTContainerRef containerRef = WCUtil.getWTContainerRef();
		String lcName = "KET_ECA_LC";
		LifeCycleHelper.setLifeCycle((LifeCycleManaged) expansion,
		        LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

		Folder folder = FolderHelper.getFolder((FolderEntry) ecr);
		FolderHelper.assignLocation((FolderEntry) expansion, folder);

	    }
	    // Update
	    else {
		// Do nothing..!!

	    }

	    // ECR
	    expansion.setEcr(ecr);

	    if (reqData != null) {

		// 검토요청기한
		String reviewRequestDate = (String) reqData.get("reviewRequestDate");
		expansion.setReviewRequestDate(DateUtil.convertStartDate2(reviewRequestDate));

		// 차종
		String carTypeOid = (String) reqData.get("carTypeCode");
		OEMProjectType carType = (carTypeOid != null && !carTypeOid.equals("")) ? (OEMProjectType) rf.getReference(carTypeOid)
		        .getObject() : null;
		expansion.setCarType(carType);

		String carTypeCode = (carType != null) ? carType.getCode() : null;
		expansion.setCarTypeCode(carTypeCode);

		// 주관부서
		String subjectOid = (String) reqData.get("subjectOid");
		Department subject = (subjectOid != null && !subjectOid.equals("")) ? (Department) rf.getReference(subjectOid).getObject()
		        : null;
		expansion.setSubject(subject);

		String subjectCode = (String) reqData.get("subjectCode");
		subjectCode = ((subjectCode == null || subjectCode.equals("")) && subject != null) ? subject.getCode() : subjectCode;
		expansion.setSubjectCode(subjectCode);

		// 담당자
		String chargeOid = (String) reqData.get("chargeOid");
		WTUser charge = (chargeOid != null && !chargeOid.equals("")) ? (WTUser) rf.getReference(chargeOid).getObject() : null;
		expansion.setCharge(charge);
		String chargeName = (String) reqData.get("chargeName");
		chargeName = ((chargeName == null || chargeName.equals("")) && charge != null) ? charge.getName() : chargeName;
		expansion.setChargeName(chargeName);

		// 금형변경
		String moldChangeOid = (String) reqData.get("moldChangeOid");
		NumberCode numberCode = (moldChangeOid != null && !moldChangeOid.equals("")) ? (NumberCode) rf.getReference(moldChangeOid)
		        .getObject() : null;
		expansion.setMoldChange(numberCode);

		String moldChangeCode = (numberCode != null) ? numberCode.getCode() : null;
		expansion.setMoldChangeCode(moldChangeCode);

		// 원가변동
		String costChangeOid = (String) reqData.get("costChangeOid");
		numberCode = (costChangeOid != null && !costChangeOid.equals("")) ? (NumberCode) rf.getReference(costChangeOid).getObject()
		        : null;
		expansion.setCostChange(numberCode);

		String costChangeCode = (numberCode != null) ? numberCode.getCode() : null;
		expansion.setCostChangeCode(costChangeCode);

		// 긴급도
		String emergencyPositionOid = (String) reqData.get("emergencyPositionOid");
		numberCode = (emergencyPositionOid != null && !emergencyPositionOid.equals("")) ? (NumberCode) rf.getReference(
		        emergencyPositionOid).getObject() : null;
		expansion.setEmergencyPosition(numberCode);

		String emergencyPositionCode = (numberCode != null) ? numberCode.getCode() : null;
		expansion.setEmergencyPositionCode(emergencyPositionCode);

		// WebEditor
		expansion.setWebEditor((String) reqData.get("webEditor"));
		expansion.setWebEditorText((String) reqData.get("webEditorText"));

		// WebEditor1
		expansion.setWebEditor1((String) reqData.get("webEditor1"));
		expansion.setWebEditorText1((String) reqData.get("webEditorText1"));

	    }

	    expansion = (KETChangeRequestExpansion) PersistenceHelper.manager.save(expansion);

	    // 마무리
	    expansion = (KETChangeRequestExpansion) PersistenceHelper.manager.refresh(expansion);
	    expansionOid = CommonUtil.getOIDString(expansion);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	    trx = null;
	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return expansionOid;
    }

    @Override
    public String modifyProdEco(String ecoOid, KETParamMapUtil paramMap, Vector attachFileList) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    conn.setAutoCommit(false);

	    // ECO
	    String eco_name = paramMap.getString("eco_name");
	    String dev_yn = paramMap.getString("dev_yn");
	    String div_flag = paramMap.getString("div_flag");
	    String project_oid = paramMap.getString("project_oid");
	    String domestic_yn = paramMap.getString("domestic_yn");
	    String car_maker = paramMap.getString("car_maker");
	    String car_category = paramMap.getString("car_category");
	    String change_reason = paramMap.getString("changeReason"); // 설계변경 사유
	    String other_reason = paramMap.getString("other_reason"); // 기타 Description
	    String custom_flag = paramMap.getString("custom_flag"); // 고객확인 구분
	    String other_cust_flag = paramMap.getString("other_cust_flag"); // 기타 고객확인 Desc
	    String change_fact = paramMap.getString("chg_fact"); // 조정 및 변경사항
	    String change_cu_dwg = paramMap.getString("chk_cu"); // CU 도면 변경여부
	    String ecoApplyPoint = paramMap.getString("ecoApplyPoint"); // ECO적용시점
	    String effective_date = paramMap.getString("effective_date"); // 적용시기
	    String inventory_process = paramMap.getString("inv_process"); // 재고처리

	    String changeType = paramMap.getString("changeType"); // 설계변경 유형
	    String reviewResult = paramMap.getString("reviewResult"); // 검토결과
	    String webEditor = paramMap.getString("webEditor"); // 변경 전
	    String webEditorText = paramMap.getString("webEditorText"); // 변경 전
	    String webEditor1 = paramMap.getString("webEditor1"); // 변경 후
	    String webEditorText1 = paramMap.getString("webEditorText1"); // 변경 후

	    String design_guide_yn = paramMap.getString("chk_design_guide"); // 설계가이드 반영
	    String design_sheet_yn = paramMap.getString("chk_design_sheet"); // 설계검증체크시트 반영

	    String defectDiv = paramMap.getString("defectDiv"); // 불량구분 코드
	    String defectType = paramMap.getString("defectType"); // 불량유형 코드
	    String defectDivName = paramMap.getString("defectDivName"); // 불량구분
	    String defectTypeName = paramMap.getString("defectTypeName"); // 불량유형
	    String costChange = paramMap.getString("costChange"); // 원가변동
	    String costVariation = paramMap.getString("costVariation"); // 원가증감비율(수주대비)

	    String point_yn = paramMap.getString("chk_point"); // 중요포인트 반영/변경
	    String spec_changet_yn = paramMap.getString("chk_spec_change"); // 사양변경 식별표기

	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(ecoOid);

	    // ECO 의 1:N 처리
	    ProdEcoBeans beans = new ProdEcoBeans();

	    // 제거한 객체 처리
	    Hashtable<String, ArrayList<String>> delObjListHash = null;

	    // 제거해야할 Object List Hashtable에 추가
	    boolean isCanDeleteDoc = false;
	    /*
	     * if (activityType.equals("")) { isCanDeleteDoc = true; }
	     */

	    String ecoIda2a2 = KETObjectUtil.getIda2a2(prodEco);
	    String delBomList = paramMap.getString("deleteRelBomList");
	    String delEpmDocList = paramMap.getString("deleteRelEpmList");
	    String deleteRelDocList = paramMap.getString("deleteRelDocList");

	    delObjListHash = beans.getAllDelObjLinkOidListHash(ecoIda2a2, delBomList, delEpmDocList, deleteRelDocList, isCanDeleteDoc);
	    // Kogger.debug(getClass(), delObjListHash.toString());

	    String prePageFlag = paramMap.getString("prePage");
	    if (prePageFlag != null && prePageFlag.equalsIgnoreCase("ToDo")) {

		/*
	         * ToDo에서 기본사항을 View로 수정하였다. ToDo에서 왔을 경우 기본사항 관련 정보를 업데이트하지 않아야 한다.
	         */

	    } else {

		prodEco.setEcoName(eco_name);
		prodEco.setDevYn(dev_yn);
		prodEco.setDivisionFlag(div_flag);
		prodEco.setProjectOid(project_oid);
		prodEco.setDomesticYn(domestic_yn);
		prodEco.setCarMaker(car_maker);
		prodEco.setCarCategory(car_category);
		prodEco.setChangeReason(change_reason);
		prodEco.setOtherChangeReason(other_reason);
		prodEco.setCustormerFlag(custom_flag);
		prodEco.setOtherCustFlagDesc(other_cust_flag);
		prodEco.setChangeFlag(change_fact);
		prodEco.setCuDrawingChangeYn(change_cu_dwg);
		prodEco.setEcoApplyPoint(ecoApplyPoint);
		prodEco.setEffectiveDateFlag(effective_date);
		prodEco.setInventoryClear(inventory_process);

		prodEco.setChangeType(changeType); // 설계변경 유형
		prodEco.setReviewResult(reviewResult); // 검토결과
		prodEco.setWebEditor(webEditor); // 변경 전
		prodEco.setWebEditorText(webEditorText); // 변경 전
		prodEco.setWebEditor1(webEditor1); // 변경 후
		prodEco.setWebEditorText1(webEditorText1); // 변경 후

		prodEco.setDesignGuideYn(design_guide_yn); // 설계가이드 반영
		prodEco.setDesignChecksheetYn(design_sheet_yn); // 설계검증체크시트 반영

		prodEco.setDefectDivCode(defectDiv); // 불량구분코드
		prodEco.setDefectDivName(defectDivName); // 불량구분
		prodEco.setDefectTypeCode(defectType); // 불량유형코드
		prodEco.setDefectTypeName(defectTypeName); // 불량유형
		prodEco.setCostChangeCode(costChange); // 원가변동
		prodEco.setCostVariationRate(costVariation);// 원가증감비율(수주대비)
		prodEco.setPointYn(point_yn); // 중요포인트 반영/변경
		prodEco.setSpecChangeYn(spec_changet_yn); // 사양변경 식별표기

		prodEco = (KETProdChangeOrder) PersistenceHelper.manager.save(prodEco);
		prodEco = (KETProdChangeOrder) PersistenceHelper.manager.refresh(prodEco);

		// 첨부파일
		// Delete AttachFile
		String strDelFileList = paramMap.getString("deleteFileList");
		if (strDelFileList.length() > 0) {
		    Vector delFileList = new Vector();
		    StringTokenizer tokens = new StringTokenizer(strDelFileList, "*");
		    while (tokens.hasMoreElements()) {
			delFileList.addElement(tokens.nextElement());
		    }

		    QueryResult rs = ContentHelper.service.getContentsByRole(prodEco, ContentRoleType.SECONDARY);
		    while (rs.hasMoreElements()) {
			ContentItem allContentItem = (ContentItem) rs.nextElement();
			Object allObj = allContentItem;
			String itemStr = allObj.toString();
			for (int delFileCnt = 0; delFileCnt < delFileList.size(); delFileCnt++) {
			    if (delFileList.get(delFileCnt).equals(itemStr)) {
				prodEco = (KETProdChangeOrder) E3PSContentHelper.service.delete(prodEco, allContentItem);
			    }
			}
		    }
		}

		// Attachfile Relation
		if (attachFileList != null) {
		    for (int fileCnt = 0; fileCnt < attachFileList.size(); fileCnt++) {
			prodEco = (KETProdChangeOrder) E3PSContentHelper.service.attach((ContentHolder) prodEco,
			        (WBFile) attachFileList.get(fileCnt), "", ContentRoleType.SECONDARY);
		    }
		}

		// I. 관련 ECR
		// Delete All Related ECR List
		ArrayList<String> delEcrOidList = delObjListHash.get("ecrLinkOidList");
		Kogger.debug(getClass(), "삭제대상 ECR is \n" + delEcrOidList.toString());

		int delEcrOidListSize = (delEcrOidList != null) ? delEcrOidList.size() : 0;
		for (int dEcrCnt = 0; dEcrCnt < delEcrOidListSize; dEcrCnt++) {
		    KETProdChangeLink ecrLink = (KETProdChangeLink) CommonUtil.getObject(delEcrOidList.get(dEcrCnt));

		    PersistenceHelper.manager.delete(ecrLink);
		}

		// Create ECR List
		String[] ecrList = paramMap.getStringArray("relEcrOid"); // 관련ECR oid 추가목록
		Kogger.debug(getClass(), "관련 ECR is \n" + ((ecrList != null) ? ecrList.toString() : null));

		int ecrListLength = (ecrList != null) ? ecrList.length : 0;
		for (int ecrCnt = 0; ecrCnt < ecrListLength; ecrCnt++) {
		    KETProdChangeRequest ketProdChangeRequest = (KETProdChangeRequest) CommonUtil.getObject(ecrList[ecrCnt]);

		    KETProdChangeLink ketProdChangeLink = KETProdChangeLink.newKETProdChangeLink(prodEco, ketProdChangeRequest);
		    PersistenceHelper.manager.save(ketProdChangeLink);
		    Kogger.debug(getClass(), "생성된 ECR Link is \n" + ketProdChangeLink.toString());

		}

		// II. 관련 품질문제
		// Delete All Related DQM List
		ArrayList<String> delDqmOidList = delObjListHash.get("dqmLinkOidList");
		Kogger.debug(getClass(), "삭제대상 DQM is \n" + delDqmOidList.toString());

		int delDqmOidListSize = (delDqmOidList != null) ? delDqmOidList.size() : 0;
		for (int dDqmCnt = 0; dDqmCnt < delDqmOidListSize; dDqmCnt++) {
		    KETEcoDqmLink dqmLink = (KETEcoDqmLink) CommonUtil.getObject(delDqmOidList.get(dDqmCnt));

		    PersistenceHelper.manager.delete(dqmLink);
		}

		// Create DQM List
		String[] dqmList = paramMap.getStringArray("relDqmOid"); // 관련DQM oid 추가목록
		Kogger.debug(getClass(), "관련 DQM is \n" + ((dqmList != null) ? dqmList.toString() : null));

		int dqmListLength = (dqmList != null) ? dqmList.length : 0;
		for (int dqmCnt = 0; dqmCnt < dqmListLength; dqmCnt++) {
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) CommonUtil.getObject(dqmList[dqmCnt]);
		    KETDqmAction ketDqmAction = ketDqmHeader.getAction();
		    if (ketDqmAction == null) {

			// 관련품질문제 정보가 온전하지 않습니다.
			throw new WTException(messageService.getString("e3ps.message.ket_message", "04670"));

		    } else {
			KETEcoDqmLink ketEcoDqmLink = KETEcoDqmLink.newKETEcoDqmLink(prodEco, ketDqmAction);
			PersistenceHelper.manager.save(ketEcoDqmLink);
			ketEcoDqmLink = (KETEcoDqmLink) PersistenceHelper.manager.refresh(ketEcoDqmLink);

			Kogger.debug(getClass(), "생성된 DQM Link is \n" + ketEcoDqmLink.toString());
		    }

		}

		// III. 관련 제품
		// Delete All Related Part List
		ArrayList<String> delPartOidList = delObjListHash.get("partLinkOidList");
		Kogger.debug(getClass(), "삭제대상 제품 is \n" + delPartOidList.toString());

		int delPartOidListSize = (delPartOidList != null) ? delPartOidList.size() : 0;
		for (int dPartCnt = 0; dPartCnt < delPartOidListSize; dPartCnt++) {
		    KETProdECOPartLink partLink = (KETProdECOPartLink) CommonUtil.getObject(delPartOidList.get(dPartCnt));
		    PersistenceHelper.manager.delete(partLink);
		}

		// Create Related Part List
		String[] activityType = paramMap.getStringArray("activityType");
		String[] relPartOidList = paramMap.getStringArray("relPartOid"); // 제품 oid 추가목록
		String[] relDieNoList = paramMap.getStringArray("relDieNo"); // 관련 dieno
		String[] expectCostList = paramMap.getStringArray("expectCost"); // 예상비용
		String[] budgetFlagList = paramMap.getStringArray("secureBudgetYn"); // 비용확보여부

		ArrayList<Hashtable<String, String>> refPartList = beans.getRefPartList(relPartOidList, relDieNoList, expectCostList,
		        budgetFlagList);
		Kogger.debug(getClass(), "관련 제품 is \n" + ((refPartList != null) ? refPartList.toString() : null));

		int refPartListSize = (refPartList != null) ? refPartList.size() : 0;
		for (int pCnt = 0; pCnt < refPartListSize; pCnt++) {
		    Hashtable<String, String> partHash = refPartList.get(pCnt);
		    WTPart wtPart = (WTPart) CommonUtil.getObject(partHash.get("partOid"));

		    KETProdECOPartLink ketProdECOPartLink = KETProdECOPartLink.newKETProdECOPartLink(wtPart, prodEco);
		    ketProdECOPartLink.setRelatedDieNo(partHash.get("dieNo"));
		    ketProdECOPartLink.setExpectCost(partHash.get("expectCost"));
		    ketProdECOPartLink.setSecureBudgetYn(partHash.get("secureBudgetYn"));
		    PersistenceHelper.manager.save(ketProdECOPartLink);
		    Kogger.debug(getClass(), "생성된 제품 Link is \n" + ketProdECOPartLink.toString());
		}

	    }

	    // IV. 설변부품/도면, ECN
	    String[] actTypeList = paramMap.getStringArray("relEcaActivityType"); // 활동계획구분(1:도면, 2:BOM)
	    String[] prodEcaOidList = paramMap.getStringArray("prodEcaOid"); // KETProdChangeActivity OID

	    String[] refObjLinkOidList = paramMap.getStringArray("relEcaLinkOid"); // 관련Link Oid
	    String[] refObjOidList = paramMap.getStringArray("relObjOid"); // 관련 BOM/EPMDoc Oid
	    String[] refPreVerList = paramMap.getStringArray("relObjPreRev"); // 관련 도면/BOM 변경전 버전

	    // 안보낸다.
	    /*
	     * String[] refObjAfterVersionList = paramMap.getStringArray("relObjAfterRev"); // 관련 도면/BOM/문서 변경후 버전
	     */

	    String[] workerOidList = paramMap.getStringArray("relEcaWorkerOid"); // 활동 도면/BOM담당자 oid
	    String[] massChgYnList = paramMap.getStringArray("masschange_yn"); // BOM 일괄변경 여부
	    String[] parentPartList = paramMap.getStringArray("parentPart"); // BOM 일괄변경 모부품
	    String[] planDateList = paramMap.getStringArray("relEcaPlanDate"); // 관련 활동 변경예정일
	    String[] commentList = paramMap.getStringArray("relEcaActivityComment"); // 관련활동변경내용
	    String[] dieNoScheduleList = paramMap.getStringArray("dieNo"); // 금형 변경일정 Die No
	    String[] scheduleIdList = paramMap.getStringArray("scheduleId"); // 금형변경일정 Id
	    String[] epmDocTypeList = paramMap.getStringArray("relEcaEpmDocType"); // 도면 Type

	    // Die No
	    String[] relDieNoEcaList = paramMap.getStringArray("relDieNo_eca");
	    String[] expectCostEcaList = paramMap.getStringArray("expectCost_eca");
	    String[] secureBudgetYnEcaList = paramMap.getStringArray("secureBudgetYn_eca");
	    String[] budgetEcaList = paramMap.getStringArray("budget_eca");

	    Hashtable<String, ArrayList<Hashtable<String, String>>> refObjHash = beans.getAddRefObjList2(actTypeList, refObjLinkOidList,
		    refObjOidList, refPreVerList, workerOidList, massChgYnList, parentPartList, planDateList, commentList,
		    dieNoScheduleList, scheduleIdList, epmDocTypeList, prodEcaOidList, relDieNoEcaList, expectCostEcaList,
		    secureBudgetYnEcaList);

	    // Hashtable<String, ArrayList<Hashtable<String, String>>> refObjHash = beans.getAddRefObjList(paramMap);
	    // refObjHash = beans.getAddRefObjList( actTypeList, refObjLinkList, refObjList, refObjVersionList, refObjAfterVersionList,
	    // ecaWorkerOidList, massChgYnList, parentPartList, ecaPlanDateList, ecaCommentList, moldPlanDieNoList, moldPlanIdList,
	    // epmDocTypeList );
	    Kogger.debug(getClass(), refObjHash.toString());

	    // V. 설변부품
	    // Delete Targetted Related BOM List
	    // Set temporary Session - Administrator
	    WTPrincipal session = null;
	    session = SessionHelper.manager.getPrincipal();
	    SessionHelper.manager.setAdministrator();

	    int failDeleteBom = 0;
	    boolean isDeleteBom = false;
	    // if (reqData.get("activityType").toString().equals("2") || reqData.get("activityType").toString().equals("")) {
	    ArrayList<String> delBomHeaderOidList = delObjListHash.get("bomLinkOidList");
	    Kogger.debug(getClass(), "삭제대상 설변부품 is \n" + delBomHeaderOidList.toString());

	    int delBomHeaderOidListSize = (delBomHeaderOidList != null) ? delBomHeaderOidList.size() : 0;
	    RENEW: for (int dBomCnt = 0; dBomCnt < delBomHeaderOidListSize; dBomCnt++) {

		KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(delBomHeaderOidList.get(dBomCnt));
		if (eca == null)
		    continue RENEW;

		// Link 처리
		QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
		if (queryResult.hasMoreElements()) {
		    KETProdECABomLink bomLink = (KETProdECABomLink) queryResult.nextElement();
		    KETBomEcoHeader bomEcoHeader = bomLink.getBom();

		    if (StringUtil.checkNull(bomLink.getAfterVersion()).length() > 0) {

			ALERT_MESSAGE += "개정된 부품은 삭제할 수 없습니다.[" + bomEcoHeader.getEcoItemCode() + "]";
			continue RENEW;

		    }

		    /*
	             * WTPart part = beans.getLastestPart(bomEcoHeader.getEcoItemCode(), conn); PersistenceHelper.manager.delete(part)
	             */

		    isDeleteBom = beans.deleteBomSubComponent(bomEcoHeader.getEcoHeaderNumber(), bomEcoHeader.getEcoItemCode(), conn);
		    PersistenceHelper.manager.delete(bomEcoHeader);
		    PersistenceHelper.manager.delete(bomLink);

		    if (!isDeleteBom) {
			failDeleteBom++;
		    }
		}

		PersistenceHelper.manager.delete(eca);
		WorkflowSearchHelper.manager.deleteWorkItem(eca);

	    }
	    // }

	    SessionHelper.manager.setPrincipal(session.getName());

	    // Create Related BOM List
	    ArrayList<Hashtable<String, String>> refBomList = refObjHash.get("refBomList");
	    Kogger.debug(getClass(), "설변부품 is \n" + ((refBomList != null) ? refBomList.toString() : null));

	    int refBomListSize = (refBomList != null) ? refBomList.size() : 0;
	    if (refBomListSize > 0) {
		refBomList.get(0).put("prePageFlag", prePageFlag);
	    }
	    ArrayList<KETProdChangeActivity> ecaBomList = beans.createProdBomHeaderEcaLink(prodEco, refBomList, conn);
	    Kogger.debug(getClass(), "생성/수정된 설변부품 is \n" + ((ecaBomList != null) ? ecaBomList.toString() : null));

	    // VI. 설변도면
	    // Delete Targetted Related EPMDocument List
	    ArrayList<String> delEpmDocOidList = delObjListHash.get("epmDocLinkOidList");
	    Kogger.debug(getClass(), "삭제대상 설변도면 is \n" + delEpmDocOidList.toString());

	    int delEpmDocOidListSize = (delEpmDocOidList != null) ? delEpmDocOidList.size() : 0;
	    RENEW: for (int dEpmCnt = 0; dEpmCnt < delEpmDocOidListSize; dEpmCnt++) {

		KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(delEpmDocOidList.get(dEpmCnt));
		if (eca == null)
		    continue RENEW;

		// Link 처리
		QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETProdECAEpmDocLink.class, false);
		if (queryResult.hasMoreElements()) {

		    KETProdECAEpmDocLink ketProdECAEpmDocLink = (KETProdECAEpmDocLink) queryResult.nextElement();
		    EPMDocument empDocument = ketProdECAEpmDocLink.getEpmDoc();

		    if (StringUtil.checkNull(ketProdECAEpmDocLink.getAfterVersion()).length() > 0) {

			ALERT_MESSAGE += "개정된 도면은 삭제할 수 없습니다.[" + empDocument.getName() + ", " + empDocument.getNumber() + " "
			        + empDocument.getVersionInfo().toString() + "]";
			continue RENEW;

		    }

		    /*
	             * EPMDocument epmDoc = beans.getLastestEPMDoc(ketProdECAEpmDocLink.getEpmDoc());
	             * beans.cancelRevEpm(KETObjectUtil.getOidString(epmDoc));
	             */

		    PersistenceHelper.manager.delete(ketProdECAEpmDocLink);
		}

		PersistenceHelper.manager.delete(eca);
		WorkflowSearchHelper.manager.deleteWorkItem(eca);
	    }

	    /*
	     * QueryResult planQr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class, false);
	     * KETProdECOPlanLink planLink = null; while (planQr.hasMoreElements()) { planLink = (KETProdECOPlanLink) planQr.nextElement();
	     * PersistenceHelper.manager.delete(planLink); }
	     */

	    // Create Related EPMDocument List
	    ArrayList<Hashtable<String, String>> refEpmDocList = refObjHash.get("refEpmDocList");
	    Kogger.debug(getClass(), "설변도면 is \n" + ((refEpmDocList != null) ? refEpmDocList.toString() : null));

	    ArrayList<KETProdChangeActivity> ecaEpmList = beans.createProdEpmDocEcaLink(prodEco, refEpmDocList);
	    Kogger.debug(getClass(), "생성/수정된 설변도면 is \n" + ((ecaEpmList != null) ? ecaEpmList.toString() : null));

	    /*
	     * beans.modifyMoldPlanLink( prodEco );
	     */

	    // ECN
	    String prePage = paramMap.getString("prePage");
	    if (prePage != null && prePage.equalsIgnoreCase("ToDo")) {

		/*
	         * ECO등록/수정과 Todo로 나뉘는데 Todo일 경우 View모드이기때문에 어떠한 관련 정보도 넘어오지 않는다. ECN TODO 저장시 cmd 는 ModifyNotOwner 이기 때문에 이 메쏘드가 호출되지 않는다.
	         */

	    } else {

		// Delete All Related ECN List
		QueryResult queryResult = PersistenceHelper.manager.navigate(prodEco, "prodECA", KETProdChangeActivityLink.class, false);

		while (queryResult.hasMoreElements()) {
		    KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) queryResult.nextElement();
		    KETProdChangeActivity eca = (KETProdChangeActivity) ketProdChangeActivityLink.getProdECA();

		    // 문서일 경우
		    if (eca.getActivityType().equalsIgnoreCase("3")) {

			boolean isPass = true;
			// Link 처리
			QueryResult queryResult2 = PersistenceHelper.manager.navigate(eca, "doc", KETProdECADocLink.class, false);
			if (queryResult2.hasMoreElements()) {

			    KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) queryResult2.nextElement();
			    KETProjectDocument ketProjectDocument = ketProdECADocLink.getDoc();

			    if (StringUtil.checkNull(ketProdECADocLink.getAfterVersion()).length() > 0) {

				ALERT_MESSAGE += "개정된 문서는 삭제할 수 없습니다.[" + ketProjectDocument.getName() + ", "
				        + ketProjectDocument.getNumber() + " " + ketProjectDocument.getVersionInfo().toString() + "]";
				isPass = false;

			    } else {

				/*
		                 * EPMDocument epmDoc = beans.getLastestEPMDoc(ketProdECAEpmDocLink.getEpmDoc());
		                 * beans.cancelRevEpm(KETObjectUtil.getOidString(epmDoc));
		                 */

				PersistenceHelper.manager.delete(ketProdECADocLink);

			    }

			}

			if (isPass) {
			    Kogger.debug(getClass(), "삭제된 ECN is \n" + eca.toString());

			    PersistenceHelper.manager.delete(eca);
			    WorkflowSearchHelper.manager.deleteWorkItem(eca);

			    PersistenceHelper.manager.delete(ketProdChangeActivityLink);

			}

		    }
		    // 활동일 경우
		    else if (eca.getActivityType().equalsIgnoreCase("4")) {

			Kogger.debug(getClass(), "삭제된 ECN is \n" + eca.toString());

			PersistenceHelper.manager.delete(eca);
			WorkflowSearchHelper.manager.deleteWorkItem(eca);

			PersistenceHelper.manager.delete(ketProdChangeActivityLink);
		    }

		}

		// Create ECN List
		ArrayList<Hashtable<String, String>> refDocList = beans.getRefDocList(paramMap.getString("doc_list"));
		Kogger.debug(getClass(), "ECN is \n" + ((refDocList != null) ? refDocList.toString() : null));
		ArrayList<KETProdChangeActivity> ecaEcnList = beans.createProdPjtDocEcaLink(prodEco, refDocList);
		Kogger.debug(getClass(), "생성된 ECN is \n" + ((ecaEcnList != null) ? ecaEcnList.toString() : null));

	    }

	    /*
	     * '활동수행'인 것은 도면/BOM이 해당하고, '승인완료'인 것은 ECN이 해당된다.
	     */
	    // Create New WorkItem
	    Kogger.debug(getClass(), "현재 ECO L/C State is \n" + prodEco.getLifeCycleState().getStringValue());
	    if (prodEco.getLifeCycleState().getStringValue().indexOf("EXCUTEACTIVITY") > -1
		    || prodEco.getLifeCycleState().getStringValue().indexOf("APPROVED") > -1) {
		// Create Todo List
		KETWfmHelper.service.createWorkItem(prodEco);

	    }

	    QueryResult qr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class);
	    KETMoldChangePlan plan = null;
	    while (qr.hasMoreElements()) {
		plan = (KETMoldChangePlan) qr.nextElement();
		try {
		    plan.setProdEcoOwner((WTUser) prodEco.getCreator().getPrincipal());
		    plan.setProdDeptName(prodEco.getDeptName());

		    KETEcmHelper.service.updateDailyMoldPlanStatus(plan);
		} catch (WTPropertyVetoException e) {
		    Kogger.error(getClass(), e);
		}

	    }

	    ecoOid = CommonUtil.getOIDString(prodEco);

	    conn.commit();
	    trx.commit();

	    conn = null;
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {

	    trx.rollback();
	    trx = null;

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    try {
		if (conn != null)
		    conn.rollback();
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (SQLException sqe) {
		Kogger.error(getClass(), sqe);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String reviseRelDocInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	trx.start();

	try {

	    // ProjectDocument LinkOid
	    String[] relEcaLinkOids = paramMap.getStringArray("relEcaLinkOid");
	    // ProjectDocument Oid
	    String[] refObjList = paramMap.getStringArray("relObjOid");
	    // 개정 여부 flag list
	    // String[] reviseChkList = paramMap.getStringArray("relDocReviseYn");
	    String[] chkPostActArr = paramMap.getStringArray("chkPostAct");
	    String[] ecaUniqueKeyArr = paramMap.getStringArray("ecaUniqueKey");

	    int chkPostActArrLength = (chkPostActArr != null) ? chkPostActArr.length : 0;
	    Boolean isSecu = false;

	    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
	    String loginOid = "";

	    for (int j = 0; j < chkPostActArrLength; j++) {
		String chkPostAct = chkPostActArr[j];

		int ecaUniqueKeyArrLength = (ecaUniqueKeyArr != null) ? ecaUniqueKeyArr.length : 0;
		RE_NEW: for (int i = 0; i < ecaUniqueKeyArrLength; i++) {
		    String ecaUniqueKey = ecaUniqueKeyArr[i];

		    if (ecaUniqueKey != null && !ecaUniqueKey.equals("") && ecaUniqueKey.equals(chkPostAct)) {

			String refObj = refObjList[i];
			if (refObj == null || refObj.equals("")) {
			    // 개정할 문서가 존재하지 않습니다.
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04210");
			    continue RE_NEW;
			}

			// ChangeSession set Administrator
			/*
		         * WTPrincipal session = null; WTGroup group = null;
		         * 
		         * session = SessionHelper.manager.getPrincipal(); SessionHelper.manager.setAdministrator(); group =
		         * KETObjectUtil.getGroup("Revise Administrators"); group.addMember(session);
		         * SessionHelper.manager.setPrincipal(session.getName());
		         */

			// PBO 인스턴스화
			KETProjectDocument currentKETProjectDocument = (KETProjectDocument) CommonUtil.getObject(refObj);

			String security = currentKETProjectDocument.getSecurity();

			/*
		         * [Start] 대내비,대외비 권한 체크 시작 2015.07.28 by 황정태
		         */
			loginOid = CommonUtil.getOIDString(loginUser);
			if (loginOid.equals(currentKETProjectDocument.getIterationInfo().getCreator().toString())) {
			    isSecu = true;
			}

			if (isSecu == false) {
			    isSecu = WorkflowSearchHelper.manager.userInApproval(currentKETProjectDocument, loginUser.getName());// 결재선에
			    // 포함되어 있는지
			    // 확인
			}

			if (!security.equals("S2")) {// 대외비인 경우 패스
			    isSecu = true;
			}

			if (isSecu == false) {// 대내비인 경우 연계된 프로젝트의 CFT인원이면 패스
			    E3PSProject proj1 = null;
			    QueryResult r1 = PersistenceHelper.manager.navigate(currentKETProjectDocument, "project",
				    KETDocumentProjectLink.class);
			    while (r1.hasMoreElements()) {
				proj1 = (E3PSProject) r1.nextElement();
				proj1 = ProjectHelper.getLastProject(proj1.getMaster());
				isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject) proj1);
			    }
			}

			if (isSecu == false) {// 대내비인 경우 연계된 프로젝트 타스크를 찾고, 타스크가 있다면 그 프로젝트의 CFT인원일 경우 패스
			    ProjectOutput po = null;
			    String outputOid = null;
			    QueryResult r2 = PersistenceHelper.manager.navigate(currentKETProjectDocument, "output",
				    KETDocumentOutputLink.class);
			    if (r2.hasMoreElements()) {
				while (r2.hasMoreElements()) {
				    po = (ProjectOutput) r2.nextElement();
				    outputOid = CommonUtil.getOIDString(po);

				    E3PSTask task = (E3PSTask) po.getTask();
				    E3PSProject project = (E3PSProject) task.getProject();
				    project = ProjectHelper.getLastProject(project.getMaster());
				    isSecu = ProjectUserHelper.manager.isProjectRoleUser((TemplateProject) project);

				}
			    }
			}

			if (!isSecu) {
			    // 권한이 없습니다
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "00990");
			    continue RE_NEW;
			}
			/*
		         * [End] 대내비,대외비 권한 체크 종료 2015.07.28 by 황정태
		         */

			if (!currentKETProjectDocument.getState().getState().equals(State.toState("APPROVED"))) {
			    String msg = "문서 " + currentKETProjectDocument.getDocumentNo() + " 는 이미 개정되었습니다.";
			    ALERT_MESSAGE += msg;
			    continue RE_NEW;
			}

			boolean isLastest = VersionHelper.isLatestRevision(currentKETProjectDocument);

			/*
		         * if (security != null && security.equals("S2")) { // 대내비 문서는 개정할 수 없습니다. ALERT_MESSAGE +=
		         * messageService.getString("e3ps.message.ket_message", "04200"); continue RE_NEW; }
		         */
			// 버전 처리
			Versioned newVersioned = null;
			Versioned currentVersioned = (Versioned) currentKETProjectDocument;

			/*
		         * String lifecycle = ((LifeCycleManaged) currentVersioned).getLifeCycleName(); Folder folder =
		         * FolderHelper.service.getFolder((FolderEntry) currentVersioned); newVersioned =
		         * VersionControlHelper.service.newVersion(currentVersioned); FolderHelper.assignLocation((FolderEntry)
		         * newVersioned, folder); KETProjectDocument newKETProjectDocument = (KETProjectDocument) newVersioned;
		         * 
		         * LifeCycleHelper.setLifeCycle(newKETProjectDocument, LifeCycleHelper.service.getLifeCycleTemplate(lifecycle,
		         * WCUtil.getWTContainerRef())); // Lifecycle
		         */
			/*
		         * 현재 버전의 바로 상위버전이 존재할 경우 하나 더 위의 상위버전을 생성한다.
		         */
			RevisionControlled latest = VersionUtil.getLatestObject((Master) currentKETProjectDocument.getMaster());

			KETProjectDocument newKETProjectDocument = null;

			if (isLastest) {// 링크되어 있는 문서가 마지막 버전이면 정상적으로 개정처리한다
			    newVersioned = VersionControlHelper.service.newVersion(latest);
			    newKETProjectDocument = (KETProjectDocument) newVersioned;
			    // Copy 처리
			    newKETProjectDocument.setDocumentNo(currentKETProjectDocument.getDocumentNo());
			    newKETProjectDocument.setDocumentDescription(currentKETProjectDocument.getDocumentDescription());
			    newKETProjectDocument.setDivisionCode(currentKETProjectDocument.getDivisionCode());
			    newKETProjectDocument.setDeptName(currentKETProjectDocument.getDeptName());
			    newKETProjectDocument.setFirstRegistrationStage(currentKETProjectDocument.getFirstRegistrationStage());
			    newKETProjectDocument.setIsBuyerSummit(currentKETProjectDocument.getIsBuyerSummit());
			    newKETProjectDocument.setBuyerCode(currentKETProjectDocument.getBuyerCode());
			    newKETProjectDocument.setDRCheckPoint(currentKETProjectDocument.getDRCheckPoint());
			    // newKETProjectDocument.setApprovalResult(currentKETProjectDocument.getApprovalResult());
			    // newKETProjectDocument.setLastApprovalComment(currentKETProjectDocument.getLastApprovalComment());
			    newKETProjectDocument.setSecurity(currentKETProjectDocument.getSecurity());
			    newKETProjectDocument.setWebEditor(currentKETProjectDocument.getWebEditor());
			    newKETProjectDocument.setWebEditorText(currentKETProjectDocument.getWebEditorText());

			    newKETProjectDocument = (KETProjectDocument) PersistenceHelper.manager.save(newKETProjectDocument);

			    // 관련 부품
			    KETDocumentPartLink DpLink;
			    QueryResult r = PersistenceHelper.manager.navigate(currentKETProjectDocument, "part",
				    KETDocumentPartLink.class, false);
			    while (r.hasMoreElements()) {
				DpLink = (KETDocumentPartLink) r.nextElement();

				KETDocumentPartLink ketDocumentPartLink = KETDocumentPartLink.newKETDocumentPartLink(newKETProjectDocument,
				        DpLink.getPart());
				PersistenceHelper.manager.save(ketDocumentPartLink);
			    }

			    // 관련 프로젝트
			    KETDocumentProjectLink DPrLink;
			    QueryResult r2 = PersistenceHelper.manager.navigate(currentKETProjectDocument, "project",
				    KETDocumentProjectLink.class, false);
			    while (r2.hasMoreElements()) {
				DPrLink = (KETDocumentProjectLink) r2.nextElement();

				KETDocumentProjectLink ketDocumentProjectLink = KETDocumentProjectLink.newKETDocumentProjectLink(
				        newKETProjectDocument, DPrLink.getProject());
				PersistenceHelper.manager.save(ketDocumentProjectLink);
			    }

			    // 관련 ???
			    KETDocumentOutputLink DoLink;
			    QueryResult r3 = PersistenceHelper.manager.navigate(currentKETProjectDocument, "output",
				    KETDocumentOutputLink.class, false);
			    if (r3.hasMoreElements()) {
				DoLink = (KETDocumentOutputLink) r3.nextElement();

				KETDocumentOutputLink ketDocumentOutputLink = KETDocumentOutputLink.newKETDocumentOutputLink(
				        newKETProjectDocument, DoLink.getOutput());
				PersistenceHelper.manager.save(ketDocumentOutputLink);
			    }

			    // 관련 문서분류
			    KETDocumentCategoryLink DCLink;
			    QueryResult r1 = PersistenceHelper.manager.navigate(currentKETProjectDocument, "documentCategory",
				    KETDocumentCategoryLink.class, false);
			    if (r1.hasMoreElements()) {
				DCLink = (KETDocumentCategoryLink) r1.nextElement();

				KETDocumentCategoryLink ketDocumentCategoryLink = KETDocumentCategoryLink.newKETDocumentCategoryLink(
				        DCLink.getDocumentCategory(), newKETProjectDocument);
				PersistenceHelper.manager.save(ketDocumentCategoryLink);
			    }
			} else {// 링크되어 있는 문서가 다른 곳에서 개정되었다면 최신정보를 가져온다
			    newKETProjectDocument = (KETProjectDocument) latest;
			}

			refObj = CommonUtil.getOIDString(newKETProjectDocument);
			Kogger.debug(getClass(), "revised newKETProjectDocument refObj =====>" + refObj);

			// Restore Session
			/*
		         * SessionHelper.manager.setAdministrator(); group.removeMember(session);
		         * SessionHelper.manager.setPrincipal(session.getName());
		         */

			// Link 처리
			KETProdECADocLink ketProdECADocLink = null;
			String relEcaLinkOid = relEcaLinkOids[i];
			if (relEcaLinkOid == null || relEcaLinkOid.equals("")) {
			    KETProdChangeActivity prodECA = (KETProdChangeActivity) KETObjectUtil.getObject(ecaUniqueKey);

			    ketProdECADocLink = KETProdECADocLink.newKETProdECADocLink(currentKETProjectDocument, prodECA);
			    ketProdECADocLink.setDocType(prodECA.getCustomCode());
			    String preVersion = VersionControlHelper.getVersionIdentifier(currentKETProjectDocument).getSeries().getValue();
			    ketProdECADocLink.setPreVersion(preVersion);
			    ketProdECADocLink.setDocTypeDesc(null);
			} else {
			    ketProdECADocLink = (KETProdECADocLink) KETObjectUtil.getObject(relEcaLinkOid);
			}

			String afterVersion = VersionControlHelper.getVersionIdentifier(newKETProjectDocument).getSeries().getValue();
			ketProdECADocLink.setAfterVersion(afterVersion);

			PersistenceHelper.manager.save(ketProdECADocLink);

		    }

		}

	    }

	    trx.commit();

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String cancelReviseRelDocInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	trx.start();

	try {

	    // ProjectDocument LinkOid
	    String[] relEcaLinkOids = paramMap.getStringArray("relEcaLinkOid");
	    // ProjectDocument Oid
	    String[] refObjList = paramMap.getStringArray("afterProjectDocumentOid");
	    // 개정 여부 flag list
	    // String[] reviseChkList = paramMap.getStringArray("relDocCancelRevYn");
	    String[] chkPostActArr = paramMap.getStringArray("chkPostAct");
	    String[] ecaUniqueKeyArr = paramMap.getStringArray("ecaUniqueKey");

	    int chkPostActArrLength = (chkPostActArr != null) ? chkPostActArr.length : 0;
	    for (int j = 0; j < chkPostActArrLength; j++) {
		String chkPostAct = chkPostActArr[j];

		int ecaUniqueKeyArrLength = (ecaUniqueKeyArr != null) ? ecaUniqueKeyArr.length : 0;
		RE_NEW: for (int i = 0; i < ecaUniqueKeyArrLength; i++) {
		    String ecaUniqueKey = ecaUniqueKeyArr[i];

		    if (ecaUniqueKey != null && !ecaUniqueKey.equals("") && ecaUniqueKey.equals(chkPostAct)) {

			String refObj = refObjList[i];
			if (refObj == null || refObj.equals("")) {
			    // 개정취소할 문서가 존재하지 않습니다.
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04190");
			    continue RE_NEW;
			}

			// ChangeSession set Administrator
			/*
		         * WTPrincipal session = null; WTGroup group = null;
		         * 
		         * session = SessionHelper.manager.getPrincipal(); SessionHelper.manager.setAdministrator(); group =
		         * KETObjectUtil.getGroup("Revise Administrators"); group.addMember(session);
		         * SessionHelper.manager.setPrincipal(session.getName());
		         */

			// PBO 인스턴스화
			KETProjectDocument ketProjectDocument = (KETProjectDocument) CommonUtil.getObject(refObj);
			if (PersistenceHelper.isPersistent(ketProjectDocument)) {

			    /*
		             * 같은 문서가 같은 ECO에 있었을 경우 개정취소시 삭제후 또 이 로직을 돌 때 객체화 못하는 경우
		             */

			    String state = ketProjectDocument.getLifeCycleState().toString();
			    if (state != null && state.equals("UNDERREVIEW")) {
				// 결재중인 문서는 개정취소할 수 없습니다.
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04180");
				continue RE_NEW;
			    }

			    // 관련 삭제 처리
			    // 관련 부품
			    KETDocumentPartLink DpLink;
			    QueryResult r = PersistenceHelper.manager
				    .navigate(ketProjectDocument, "part", KETDocumentPartLink.class, false);
			    while (r.hasMoreElements()) {
				DpLink = (KETDocumentPartLink) r.nextElement();

				PersistenceHelper.manager.delete(DpLink);
			    }

			    // 관련 프로젝트
			    KETDocumentProjectLink DPrLink;
			    QueryResult r2 = PersistenceHelper.manager.navigate(ketProjectDocument, "project",
				    KETDocumentProjectLink.class, false);
			    while (r2.hasMoreElements()) {
				DPrLink = (KETDocumentProjectLink) r2.nextElement();

				PersistenceHelper.manager.delete(DPrLink);
			    }

			    // 관련 ???
			    KETDocumentOutputLink DoLink;
			    QueryResult r3 = PersistenceHelper.manager.navigate(ketProjectDocument, "output", KETDocumentOutputLink.class,
				    false);
			    if (r3.hasMoreElements()) {
				DoLink = (KETDocumentOutputLink) r3.nextElement();

				PersistenceHelper.manager.delete(DoLink);
			    }

			    // 관련 문서분류
			    KETDocumentCategoryLink DCLink;
			    QueryResult r1 = PersistenceHelper.manager.navigate(ketProjectDocument, "documentCategory",
				    KETDocumentCategoryLink.class, false);
			    if (r1.hasMoreElements()) {
				DCLink = (KETDocumentCategoryLink) r1.nextElement();

				PersistenceHelper.manager.delete(DCLink);
			    }

			    // PBO 삭제 처리
			    PersistenceHelper.manager.delete(ketProjectDocument);

			    // Restore Session
			    /*
		             * SessionHelper.manager.setAdministrator(); group.removeMember(session);
		             * SessionHelper.manager.setPrincipal(session.getName());
		             */

			}

			// Link 처리
			String relEcaLinkOid = relEcaLinkOids[i];
			KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) KETObjectUtil.getObject(relEcaLinkOid);

			ketProdECADocLink.setAfterVersion(null);

			PersistenceHelper.manager.save(ketProdECADocLink);

		    }

		}

	    }

	    trx.commit();

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String receiveConfirmInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	trx.start();

	try {

	    // PBO 인스턴스화
	    WTChangeOrder2 wtChangeOrder2 = (WTChangeOrder2) CommonUtil.getObject(ecoOid);
	    if (PersistenceHelper.isPersistent(wtChangeOrder2)) {

		QueryResult queryResult = PersistenceHelper.manager.navigate(wtChangeOrder2, "prodECA", KETProdChangeActivityLink.class,
		        false);
		while (queryResult.hasMoreElements()) {
		    KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) queryResult.nextElement();

		    KETProdChangeActivity ketProdChangeActivity = ketProdChangeActivityLink.getProdECA();
		    ketProdChangeActivity.setReceiveConfirmedDate(DateUtil.getCurrentTimestamp());

		    PersistenceHelper.manager.save(ketProdChangeActivity);
		}

	    }

	    trx.commit();

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String modifyProdEcoNotOwner2(String ecoOid, Hashtable<String, ArrayList<Hashtable<String, String>>> addObjListHash,
	    Hashtable<String, ArrayList<String>> delObjListHash, String activityType, KETParamMapUtil paramMap) throws WTException {
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	ProdEcoBeans beans = new ProdEcoBeans();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();

	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    Kogger.debug(getClass(), "modifyProdEcoNotOwner -------------------->1 ");
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(ecoOid);

	    WTPrincipal session = null;
	    // Set temporary Session - Administrator
	    session = SessionHelper.manager.getPrincipal();
	    SessionHelper.manager.setAdministrator();

	    Kogger.debug(getClass(), "modifyProdEcoNotOwner(Delete BOM) -------------------->4 ");
	    // Delete All Related BOM List
	    int failDeleteBom = 0;
	    if (activityType.equals("2")) {
		ArrayList<String> delBomHeaderOidList = delObjListHash.get("bomLinkOidList");
		KETProdECABomLink bomLink = null;
		KETProdECABomLink[] relatedBomLinkList = null;
		KETBomEcoHeader bomEcoHeader = null;
		WTPart part = null;

		boolean isDeleteBom = false;
		for (int dBomCnt = 0; dBomCnt < delBomHeaderOidList.size(); dBomCnt++) {
		    bomLink = (KETProdECABomLink) CommonUtil.getObject(delBomHeaderOidList.get(dBomCnt));

		    // Mass Bom Change Delete
		    if (bomLink.getMassChangeYn().equals("Y") && bomLink.getRelatedParentPartList().length() > 0) {
			relatedBomLinkList = EcmSearchHelper.manager.getRelatedMassBomLink(
			        KETObjectUtil.getOidString(bomLink.getProdECA()), bomLink.getRelatedParentPartList());

			for (int linkCnt = 0; linkCnt < relatedBomLinkList.length; linkCnt++) {
			    bomEcoHeader = relatedBomLinkList[linkCnt].getBom();

			    if (StringUtil.checkNull(relatedBomLinkList[linkCnt].getAfterVersion()).length() > 0) {
				part = beans.getLastestPart(bomEcoHeader.getEcoItemCode(), conn);
				PersistenceHelper.manager.delete(part);
			    }

			    PersistenceHelper.manager.delete(relatedBomLinkList[linkCnt]);
			    isDeleteBom = beans.deleteBomSubComponent(bomEcoHeader.getEcoHeaderNumber(), bomEcoHeader.getEcoItemCode(),
				    conn);
			    PersistenceHelper.manager.delete(bomEcoHeader);

			    if (!isDeleteBom) {
				failDeleteBom++;
			    }
			}
		    } else { // Standard Bom Change Delete
			bomEcoHeader = bomLink.getBom();

			if (StringUtil.checkNull(bomLink.getAfterVersion()).length() > 0) {
			    part = beans.getLastestPart(bomEcoHeader.getEcoItemCode(), conn);
			    PersistenceHelper.manager.delete(part);
			}

			PersistenceHelper.manager.delete(bomLink);
			isDeleteBom = beans.deleteBomSubComponent(bomEcoHeader.getEcoHeaderNumber(), bomEcoHeader.getEcoItemCode(), conn);
			PersistenceHelper.manager.delete(bomEcoHeader);

			if (!isDeleteBom) {
			    failDeleteBom++;
			}
		    }
		}
	    }
	    Kogger.debug(getClass(), "modifyProdEcoNotOwner(Delete EPMDoc) -------------------->5 ");
	    // Delete All Related EPMDocument List
	    if (activityType.equals("1")) {
		ArrayList<String> delEpmDocOidList = delObjListHash.get("epmDocLinkOidList");
		KETProdECAEpmDocLink epmDocLink = null;
		EPMDocument epmDoc = null;

		for (int dEpmCnt = 0; dEpmCnt < delEpmDocOidList.size(); dEpmCnt++) {
		    epmDocLink = (KETProdECAEpmDocLink) CommonUtil.getObject(delEpmDocOidList.get(dEpmCnt));

		    if (StringUtil.checkNull(epmDocLink.getAfterVersion()).length() > 0) {
			epmDoc = beans.getLastestEPMDoc(epmDocLink.getEpmDoc());
			beans.cancelRevEpm(KETObjectUtil.getOidString(epmDoc));
		    }

		    PersistenceHelper.manager.delete(epmDocLink);
		}
	    }

	    Kogger.debug(getClass(), "modifyProdEcoNotOwner(Delete Activity) -------------------->6-1 ");
	    /*
	     * 기존 로직은 'ToDo'의 '활동수행' Update(수정)처리를 Delete(삭제) + Insert(다시 생성)이였다. 그런데 이번에 'ECN'으로 스펙수정되면서 같은 로직으로 처리하면 매우 복잡하게 되었다. 그래서
	     * 'ToDo'의 '활동수행' Update(수정)처리를 Select(가져와서) + Update(수정)하는 식으로 바꾼다.
	     */
	    // Delete Activity
	    // ECN일 경우
	    if (activityType.equals("3") || activityType.equals("4")) {

		// Do nothing..!!

		/*
	         * Kogger.debug(getClass(), "modifyProdEcoNotOwner(Delete KETProjectDocument) -------------------->6 "); // Delete All
	         * Related Document List if (activityType.equals("3") || activityType.equals("4")) { ArrayList<String> delDocOidList =
	         * delObjListHash.get("docLinkOidList"); KETProdECADocLink docLink = null;
	         * 
	         * for (int dDocCnt = 0; dDocCnt < delDocOidList.size(); dDocCnt++) { docLink = (KETProdECADocLink)
	         * CommonUtil.getObject(delDocOidList.get(dDocCnt)); PersistenceHelper.manager.delete(docLink); } }
	         */

	    }
	    // 부품/도면일 경우
	    else {
		ArrayList<KETProdChangeActivity> activityList = beans.getNotRelatedActivityList(prodEco);
		KETProdChangeActivity prodEca = null;
		KETProdChangeActivityLink ecaLink = null;
		QueryResult qr = null;
		for (int ecaCnt = 0; ecaCnt < activityList.size(); ecaCnt++) {
		    prodEca = activityList.get(ecaCnt);

		    PersistenceHelper.manager.delete(prodEca);
		    WorkflowSearchHelper.manager.deleteWorkItem(prodEca);
		}
	    }
	    SessionHelper.manager.setPrincipal(session.getName());

	    Kogger.debug(getClass(), "modifyProdEcoNotOwner(Add EPMDoc) -------------------->10 ");
	    // EPMDocument Change Activity
	    if (activityType.equals("1")) {
		ArrayList<Hashtable<String, String>> refEpmDocList = addObjListHash.get("refEpmDocList");
		beans.createProdEpmDocEcaLink(prodEco, refEpmDocList);
	    }

	    Kogger.debug(getClass(), "modifyProdEcoNotOwner(Add BOM) -------------------->11");
	    // BOM Change Activity
	    boolean isCreated = true;
	    if (activityType.equals("2")) {
		ArrayList<Hashtable<String, String>> refBomList = addObjListHash.get("refBomList");
		ArrayList<KETProdChangeActivity> ecaList = beans.createProdBomHeaderEcaLink(prodEco, refBomList, conn);
		if (ecaList == null || ecaList.size() == 0)
		    isCreated = false;
	    }

	    Kogger.debug(getClass(), "modifyProdEcoNotOwner(Add KETProjectDocument) -------------------->12");
	    // KETProjectDocument Change Activity
	    if (activityType.equals("3") || activityType.equals("4")) {

		ArrayList<Hashtable<String, String>> refDocList = addObjListHash.get("refDocList");
		/*
	         * beans.modifyNotOwnerProdPjtDocEcaLink(prodEco, refDocList);
	         */
		ALERT_MESSAGE = beans.modifyNotOwnerProdPjtDocEcaLink2(prodEco, refDocList, paramMap);

	    }

	    ecoOid = CommonUtil.getOIDString(prodEco);

	    // Create New WorkItem
	    if (prodEco.getLifeCycleState().getStringValue().indexOf("EXCUTEACTIVITY") > -1
		    || prodEco.getLifeCycleState().getStringValue().indexOf("APPROVED") > -1) {
		KETWfmHelper.service.createWorkItem(prodEco);
	    }

	    if (isCreated && failDeleteBom == 0) {
		conn.commit();
	    }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    try {
		if (conn != null)
		    conn.rollback();
	    } catch (SQLException e1) {
		Kogger.error(getClass(), e1);
	    }

	    trx.rollback();
	    trx = null;

	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ALERT_MESSAGE;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String reviseBomInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";
	String revised_message = "";

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();

	    String[] chkSelectRelBoms = paramMap.getStringArray("chkSelectRelBom");
	    String[] ecaUniqueKeys = paramMap.getStringArray("ecaUniqueKey");

	    String[] prodEcaOids = paramMap.getStringArray("prodEcaOid"); // KETProdChangeActivity OID
	    String[] bomHeaderLinkOids = paramMap.getStringArray("relEcaLinkOid"); // 관련 EPMDoc LinkOid
	    String[] beforePartOids = paramMap.getStringArray("beforePartOid"); // 부품 OID
	    String[] partNos = paramMap.getStringArray("relEcaBomNo"); // 부품 No
	    String[] relObjPreRevs = paramMap.getStringArray("relObjPreRev"); // 부품 이전 버전(개정할 부품 버전)
	    String[] reviseChkFlags = paramMap.getStringArray("relEcaBomReviseYn"); // 개정 여부 flag list
	    String[] relEcaActivityComments = paramMap.getStringArray("relEcaActivityComment"); // 변경내용

	    ProdEcoBeans beans = new ProdEcoBeans();

	    // KETProdChangeOrder 객체화
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);

	    int chkSelectRelBomsLength = (chkSelectRelBoms != null) ? chkSelectRelBoms.length : 0;
	    for (int i = 0; i < chkSelectRelBomsLength; i++) {
		String chkPostAct = chkSelectRelBoms[i];

		int ecaUniqueKeysLength = (ecaUniqueKeys != null) ? ecaUniqueKeys.length : 0;
		RENEW: for (int j = 0; j < ecaUniqueKeysLength; j++) {
		    String ecaUniqueKey = ecaUniqueKeys[j];

		    if (ecaUniqueKey != null && !ecaUniqueKey.equals("") && ecaUniqueKey.equals(chkPostAct)) {

			// 생성/수정 처리

			// KETProdChangeActivity 객체화
			String prodEcaOid = prodEcaOids[j];
			if (prodEcaOid == null || prodEcaOid.equals("")) {
			    // 없을 경우 생성한다.
			    ArrayList<Hashtable<String, String>> refBomList = new ArrayList<Hashtable<String, String>>();
			    Hashtable<String, String> refBomHash = new Hashtable<String, String>();
			    refBomList.add(refBomHash);

			    // 부품 이전 버전(개정할 부품 버전)
			    refBomHash.put("preVersion", relObjPreRevs[j]);
			    // 부품 OID
			    refBomHash.put("beforPartOid", beforePartOids[j]);
			    // 부품 OID
			    refBomHash.put("partOid", beforePartOids[j]);

			    // 담당자
			    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
			    String workerOid = KETObjectUtil.getOidString(loginUser);
			    refBomHash.put("workerOid", workerOid);
			    // 변경내용 처리
			    refBomHash.put("comment", relEcaActivityComments[j]);
			    // 일괄여부(현재 사용하지 않는 것으로 스펙이 수정되어 무조건 'N'으로 세팅한다.
			    refBomHash.put("massChgYn", "N");

			    ArrayList<KETProdChangeActivity> ecaList = beans.createProdBomHeaderEcaLink(prodEco, refBomList, conn);
			    if (ecaList != null && ecaList.size() > 0) {
				KETProdChangeActivity eca = ecaList.get(0);

				// ToDo 에서 보이도록 Workflow 을 시작시킨다.
				if (!WorkflowSearchHelper.manager.IsRuninningTodo(eca)) {
				    if (!eca.getActivityType().equals("3") && !eca.getActivityType().equals("4")) {
					WTUser ecaCharge = (WTUser) CommonUtil.getObject(eca.getWorkerId());
					Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
					WfTemplateObject paramWfTemplateObject = null;

					while (allTemplates.hasMoreElements()) {
					    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

					    if (wfTemplate.getName().equals("KET_TODO_WF")) {
						paramWfTemplateObject = wfTemplate;
					    }
					}

					if (paramWfTemplateObject != null) {
					    Team team = (Team) eca.getTeamId().getObject();
					    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
						    eca.getContainerReference());
					    ProcessData data = process.getContext();
					    WfEngineServerHelper.service.setPrimaryBusinessObject(process, eca);
					    data.setValue("charge", ecaCharge);
					    WfEngineHelper.service.startProcess(process, data, 1);
					}
				    }
				}

			    }

			} else {
			    // 있을 경우 가져온다.
			    KETProdChangeActivity eca = (KETProdChangeActivity) CommonUtil.getObject(prodEcaOid);

			    // 변경내용 처리
			    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
			    if (queryResult.hasMoreElements()) {
				KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult.nextElement();
				ketProdECABomLink.setActivityComment(relEcaActivityComments[j]);

				PersistenceHelper.manager.save(ketProdECABomLink);

				// 개정유무 Validation 처리
				// 개정되어 있으면 또 개정할 수 없다.
				String afterVersion = ketProdECABomLink.getAfterVersion();
				if (afterVersion != null && !afterVersion.equals("")) {

				    KETBomEcoHeader ecoBomHeader = ketProdECABomLink.getBom();
				    revised_message += ecoBomHeader.getEcoItemCode() + "\\n";

				    continue RENEW;
				}

			    }

			}

			// 개정처리
			KETBomEcoHeader ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), partNos[j]);
			/*
		         * // 이미 개정한 것은 Skip한다. String hasRevised = ecoBomHeader.getAttribute1(); if (hasRevised != null &&
		         * hasRevised.equalsIgnoreCase("Y")) { revised_message += ecoBomHeader.getEcoItemCode() + "\\n";
		         * 
		         * continue RENEW; }
		         */

			WTPart part = null;
			String afterVersion = "";
			KETProdECABomLink bomLink = null;

			/*
		         * part = beans.getLastestPart(partNos[j], conn);
		         */
			part = (WTPart) CommonUtil.getObject(beforePartOids[j]);

			if (part.getLifeCycleState().equals(State.toState("INWORK")) && KETObjectUtil.getVersion(part).equals("0")) {// 0버전의
				                                                                                                     // 작업중
				                                                                                                     // 부품은
				                                                                                                     // 개정할수없다
				                                                                                                     // 스킵
				                                                                                                     // 2017.06.01
				                                                                                                     // by
				                                                                                                     // 황정태
			    continue RENEW;
			}

			/*
		         * 이응진의 글... 부품 개정시에 사용된 곳
		         * 
		         * StandardKETEcmService > 1603 라인 아래 소스를 afterVersion = beans.reviseBom(part);
		         * 
		         * => 아래로 변경 바랍니다. => 참고로 리비전시에 부품 상태 INWORK으로 변경됩니다.
		         */
			// afterVersion = beans.reviseBom(part);

			String changeType = prodEco.getChangeType();
			/*
		         * CDR_111^REASON_10 Proto이관 CDR_112^REASON_10 Pilot이관 CDR_113^REASON_10 양산이관
		         */
			String has100 = "";
			String has110 = "";
			String has120 = "";
			String[] changeTypes = StringUtils.splitPreserveAllTokens(changeType, "|");
			int changeTypesLength = (changeTypes != null) ? changeTypes.length : 0;
			for (int k = 0; k < changeTypesLength; k++) {
			    if (changeTypes[k].equalsIgnoreCase("CDR_111^REASON_10")) {
				has100 = changeTypes[k];
			    } else if (changeTypes[k].equalsIgnoreCase("CDR_112^REASON_10")) {
				has110 = changeTypes[k];
			    } else if (changeTypes[k].equalsIgnoreCase("CDR_113^REASON_10")) {
				has120 = changeTypes[k];
			    }
			}
			if (has120 != null && !has120.equals(""))
			    changeType = has120;
			else if (has110 != null && !has110.equals(""))
			    changeType = has110;
			else if (has100 != null && !has100.equals(""))
			    changeType = has100;
			else
			    changeType = null;
			afterVersion = PartBaseHelper.service.reviseWTPartNGetVersion(part, changeType);

			ecoBomHeader.setBOMVersion(afterVersion);
			ecoBomHeader.setAttribute1("Y");
			PersistenceHelper.manager.save(ecoBomHeader);

			// 링크처리
			bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);

			bomLink.setAfterVersion(afterVersion);
			PersistenceHelper.manager.save(bomLink);

			/*
		         * 김윤배의 글... ext.ket.bom.query.KETBOMQueryBean
		         * 
		         * //개정시 호출 public void reviseInsertComponentData(String ecoNumber, String oldPartOid, String newVersion)
		         * 
		         * //개정취소시 호출 public void reviseCancelComponentData(String ecoNumber, String oldPartOid)
		         */
			String ecoNumber = prodEco.getEcoId();
			String oldPartOid = beforePartOids[j];
			String newVersion = afterVersion;

			KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
			ketBOMQueryBean.reviseInsertComponentData(ecoNumber, oldPartOid, newVersion, wtConn);

		    }
		}
	    }

	    trx.commit();
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	// 이미 개정되어 Skip 된 부품에 대한 메세지 처리
	if (!revised_message.equals("")) {
	    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04690") + "\\n" + revised_message + "\\n";
	}
	return ALERT_MESSAGE;
    }

    @Override
    public String cancelReviseBomInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    conn.setAutoCommit(false);

	    String[] chkSelectRelBoms = paramMap.getStringArray("chkSelectRelBom");
	    String[] ecaUniqueKeys = paramMap.getStringArray("ecaUniqueKey");

	    String[] prodEcaOids = paramMap.getStringArray("prodEcaOid"); // KETProdChangeActivity OID
	    // String[] bomHeaderLinkOids = paramMap.getStringArray("relEcaLinkOid"); // 관련 EPMDoc LinkOid
	    String[] beforePartOids = paramMap.getStringArray("beforePartOid"); // 부품 OID

	    /**
	     * tklee 수정 13.1.14 : revise ObjectNoLongerExistsException 에러 처리
	     */
	    List<Map> beforePartArray = new ArrayList<Map>();
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	    if (beforePartOids == null) {
	    } else if (beforePartOids.length == 0) {
	    } else {
		KETBomPartUtil pUtil = new KETBomPartUtil();
		for (int k = 0; k < beforePartOids.length; k++) {
		    String beforePartOid = beforePartOids[k];
		    if (StringUtils.isEmpty(beforePartOid)) {
			Map partInfoMap = new HashMap();
			partInfoMap.put("partNumber", "");
			partInfoMap.put("version", "");
			partInfoMap.put("partType", "");
			partInfoMap.put("partOid", "");

			beforePartArray.add(partInfoMap);

		    } else {
			WTPart part = pUtil.getPart(beforePartOid);
			Kogger.debug(getClass(), "beforePartInfo *************>>>>>>>" + part.getNumber());
			Map partInfoMap = new HashMap();
			partInfoMap.put("partNumber", part.getNumber());
			partInfoMap.put("version", part.getVersionIdentifier().getValue().toString());

			String oldPartOid2 = ketBOMQueryBean.getPartOid2(part.getNumber(), part.getVersionIdentifier().getValue()
			        .toString(), wtConn);
			if (!oldPartOid2.equals(""))
			    part = pUtil.getPart(oldPartOid2);

			Kogger.debug(getClass(), "oldVersion-------$$$$$$$$$****************************>>>>>>>"
			        + part.getVersionIdentifier().getValue().toString());
			partInfoMap.put("partType", PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType));
			long id = pUtil.getPartLongId(part);
			String partOid = Long.toString(id);
			partInfoMap.put("partOid", partOid);

			beforePartArray.add(partInfoMap);
		    }
		}
	    }

	    String[] partNos = paramMap.getStringArray("relEcaBomNo"); // 부품 No
	    // String[] relObjPreRevs = paramMap.getStringArray("relObjPreRev"); // 부품 이전 버전(개정할 부품 버전)
	    // String[] reviseChkFlags = paramMap.getStringArray("relEcaBomReviseYn"); // 개정 여부 flag list
	    // String[] relEcaActivityComments = paramMap.getStringArray("relEcaActivityComment"); // 변경내용

	    // KETProdChangeOrder 객체화
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);

	    int chkSelectRelBomsLength = (chkSelectRelBoms != null) ? chkSelectRelBoms.length : 0;
	    for (int i = 0; i < chkSelectRelBomsLength; i++) {
		String chkPostAct = chkSelectRelBoms[i];

		int ecaUniqueKeysLength = (ecaUniqueKeys != null) ? ecaUniqueKeys.length : 0;
		for (int j = 0; j < ecaUniqueKeysLength; j++) {
		    String ecaUniqueKey = ecaUniqueKeys[j];

		    if (ecaUniqueKey != null && !ecaUniqueKey.equals("") && ecaUniqueKey.equals(chkPostAct)) {

			WTPart part = null;
			String preVersion = "";
			KETBomEcoHeader ecoBomHeader = null;
			KETProdECABomLink bomLink = null;
			// KETProdChangeOrder prodEco = (KETProdChangeOrder) KETObjectUtil.getObject(prodEcoOid);
			ProdEcoBeans beans = new ProdEcoBeans();
			EcmComDao comDao = new EcmComDao(conn);
			boolean isDeleteComp = false;
			boolean isDeleteTempComp = false;
			boolean isUpdateCoWorker = false;

			/*
		         * if (reviseChkFlags[j].equals("N")) { WTPrincipal session = null;
		         * 
		         * 
		         * if (massChgYn.equals("Y") && parentPartListStr.length() > 0) { StringTokenizer st = new
		         * StringTokenizer(parentPartListStr, ","); String parentPartNo = "";
		         * 
		         * while (st.hasMoreTokens()) { parentPartNo = st.nextToken(); part = beans.getLastestPart(parentPartNo, conn);
		         * 
		         * // Set temporary Session - Administrator session = SessionHelper.manager.getPrincipal();
		         * SessionHelper.manager.setAdministrator(); PersistenceHelper.manager.delete(part);
		         * SessionHelper.manager.setPrincipal(session.getName());
		         * 
		         * part = beans.getLastestPart(parentPartNo, conn); preVersion = VersionUtil.getMajorVersion(part);
		         * 
		         * ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), parentPartNo);
		         * ecoBomHeader.setBOMVersion(preVersion); ecoBomHeader.setAttribute1("N");
		         * ecoBomHeader.setBoxQuantity(ecoBomHeader.getAttribute2());
		         * 
		         * PersistenceHelper.manager.save(ecoBomHeader);
		         * 
		         * bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);
		         * 
		         * bomLink.setAfterVersion(""); PersistenceHelper.manager.save(bomLink);
		         * 
		         * // Delete BomSubComponent isDeleteComp = comDao.deleteBomComponent(prodEco.getEcoId(), parentPartNo);
		         * isDeleteTempComp = comDao.deleteBomTempComponent(prodEco.getEcoId(), parentPartNo); isUpdateCoWorker =
		         * comDao.updateBomCoWorker(prodEco.getEcoId(), parentPartNo); } } else {
		         */

			part = beans.getLastestPart(partNos[j], conn);

			/*
		         * 1)부품 개정취소 시점에 => 연계 도면이 먼저 개정취소되지 않으면 Validation 걸어서 Message 전달 파라미터1 : WTPart : 개정 후의 리비전 파라미터2 : ecoOid return
		         * : String : null 이거나 empty가 아니면 message 뿌려주세요. 호출 api : PartBaseHelper.service.validRelatedEpmCancelRevised(WTPart
		         * wtPart, String ecoOid);
		         */
			String ERROR_MESSAGE = PartBaseHelper.service.validRelatedEpmCancelRevised(part, ecoOid);
			if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {
			    throw new WTException(ERROR_MESSAGE);
			}

			// Set temporary Session - Administrator
			WTPrincipal session = SessionHelper.manager.getPrincipal();
			SessionHelper.manager.setAdministrator();
			PersistenceHelper.manager.delete(part);
			SessionHelper.manager.setPrincipal(session.getName());

			part = beans.getLastestPart(partNos[j], conn);
			preVersion = VersionUtil.getMajorVersion(part);

			ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(prodEco.getEcoId(), partNos[j]);
			ecoBomHeader.setBOMVersion(preVersion);
			ecoBomHeader.setAttribute1("N");
			ecoBomHeader.setBoxQuantity(ecoBomHeader.getAttribute2());
			PersistenceHelper.manager.save(ecoBomHeader);

			bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);

			bomLink.setAfterVersion("");
			PersistenceHelper.manager.save(bomLink);

			// Delete BomSubComponent
			isDeleteComp = comDao.deleteBomComponent(prodEco.getEcoId(), partNos[j]);
			isDeleteTempComp = comDao.deleteBomTempComponent(prodEco.getEcoId(), partNos[j]);
			isUpdateCoWorker = comDao.updateBomCoWorker(prodEco.getEcoId(), partNos[j]);

			/*
		         * }
		         * 
		         * 
		         * }
		         */

			/*
		         * 김윤배의 글... ext.ket.bom.query.KETBOMQueryBean
		         * 
		         * //개정시 호출 public void reviseInsertComponentData(String ecoNumber, String oldPartOid, String newVersion)
		         * 
		         * //개정취소시 호출 public void reviseCancelComponentData(String ecoNumber, String oldPartOid)
		         * 
		         * 2차고도화 유지보수 tklee 2013.1.13 삭제된 부품을 찾는 부분이 있어 앞에서 수정함
		         */
			String ecoNumber = prodEco.getEcoId();
			Map oldPartInfoMap = beforePartArray.get(j);

			ketBOMQueryBean.reviseCancelComponentData(ecoNumber, oldPartInfoMap, wtConn);

			if (isDeleteComp && isDeleteTempComp && isUpdateCoWorker) {
			    conn.commit();
			} else {
			    conn.rollback();
			}

		    }
		}
	    }

	    trx.commit();
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }

	}

	return ALERT_MESSAGE;
    }

    @Override
    public String completeInProdEco(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    conn.setAutoCommit(false);

	    // KETProdChangeOrder 객체화
	    KETProdChangeOrder prodEco = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);
	    String status = prodEco.getLifeCycleState().toString();

	    // 초도인지 아닌지
	    String changeReason = StringUtils.stripToEmpty(prodEco.getChangeReason());
	    boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);

	    WTPrincipal session = SessionHelper.manager.getPrincipal();

	    // ECO 에 연결된 KETProdChangeActivity 를 actityType 에 따라 가져온다.
	    long ecoIda2a2 = Long.parseLong(KETObjectUtil.getIda2a2(ecoOid));
	    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
	    String workerOid = KETObjectUtil.getOidString(loginUser);

	    String activityType = paramMap.getString("ecaType");
	    String[] activityTypes = (activityType == null || activityType.equals("")) ? paramMap.getStringArray("ecaTypes") : null;
	    if (activityTypes == null) {
		if (activityType != null && (activityType.equalsIgnoreCase("3") || activityType.equalsIgnoreCase("4"))) {
		    activityTypes = new String[] { "3", "4" };
		} else {
		    activityTypes = new String[] { activityType };
		}
	    }

	    // 1. 로그인한 사용자가 담당자로 있는 ECA를 현 activityType대로 가져온다.
	    ProdEcoBeans beans = new ProdEcoBeans();

	    ArrayList<String> checkEcaList = new ArrayList<String>();

	    ArrayList<KETProdChangeActivity> prodEcaList = beans.getProdEcaList(ecoIda2a2, workerOid, activityTypes);
	    int prodEcaListSize = (prodEcaList != null) ? prodEcaList.size() : 0;
	    if (prodEcaListSize == 0) {

		// <entry key="04990">작업완료 대상 활동이 없을 경우 진행하실 수 없습니다.</entry>
		throw new WTException(messageService.getString("e3ps.message.ket_message", "04990"));

	    } else {

		// 2. 자신의 ECA의 유효성 체크를 먼저 한다.
		for (int i = 0; i < prodEcaListSize; i++) {
		    KETProdChangeActivity eca = (KETProdChangeActivity) prodEcaList.get(i);
		    if (PersistenceHelper.isPersistent(eca)) {

			String ecaActivityType = StringUtils.stripToEmpty(eca.getActivityType());

			// 수신확인 Validation 처리
			/*
		         * yyyy-MM-dd 형태로 리턴, 수신일자 등록은 작업공간 > My Todo 화면에서 수행합니다.
		         */
			String receiptDate = KETWorkspaceHelper.service.getReceiptDate((LifeCycleManaged) eca);
			if (receiptDate == null || receiptDate.equals("")) {

			    // <entry key="04370">My Todo 리스트에서 수신확인을 하시기 바랍니다.</entry>
			    /*
		             * throw new WTException(messageService.getString("e3ps.message.ket_message", "04370"));
		             */
			}

			if (ecaActivityType.equals("2")) {
			    // BOM
			    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
			    if (queryResult.hasMoreElements()) {
				KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult.nextElement();
				KETBomEcoHeader ketBomEcoHeader = ketProdECABomLink.getBom();

				// 초도일 경우 제외한다.
				if (isTheFirst) {
				    // Do nothing..!!

				} else {

				    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
				    String partRevision = "";
				    String beforePartOid = ketProdECABomLink.getBeforePartOid();
				    WTPart beforeWTPart = (WTPart) CommonUtil.getObject(beforePartOid);
				    partRevision = (beforeWTPart != null) ? beforeWTPart.getVersionIdentifier().getValue() : "";
				    if (beforeWTPart.getLifeCycleState().equals(State.toState("INWORK"))
					    && (partRevision.equals("") || partRevision.equals("0"))) {
					// 통과
				    } else {
					String bomflag = PartSpecGetter.getPartSpec(beforeWTPart, PartSpecEnum.SpBOMFlag);
					if (bomflag != null && bomflag.equalsIgnoreCase("OLD")) {

					    // 개정유무 Validation 처리
					    String afterVersion = ketProdECABomLink.getAfterVersion();
					    if (afterVersion == null || afterVersion.equals("")) {
						// <entry key="04390">개정하지 않은 부품이 있습니다.</entry>
						throw new WTException(messageService.getString("e3ps.message.ket_message", "04390"));

					    }

					}

				    }

				}

				// BOM Editor에서 저장유무 Validation 처리
				String changeYn = StringUtils.stripToEmpty(ketProdECABomLink.getChangeYn());
				if (changeYn == null || changeYn.equals("") || !changeYn.equalsIgnoreCase("Y")) {
				    // <entry key="04016">부품[{0}]을 BOM Editor에서 저장하시기 바랍니다.</entry>
				    // throw new WTException(messageService.getString("e3ps.message.ket_message", "04016",
				    // ketBomEcoHeader.getEcoItemCode()));

				}

			    }
			}

			if (ecaActivityType.equals("1")) {
			    // 도면
			    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETProdECAEpmDocLink.class, false);
			    if (queryResult.hasMoreElements()) {
				KETProdECAEpmDocLink ketProdECAEpmDocLink = (KETProdECAEpmDocLink) queryResult.nextElement();

				// 초도일 경우 제외한다.
				if (isTheFirst || (changeReason.lastIndexOf("REASON_13") > -1)) {
				    // Do nothing..!!

				} else {

				    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
				    String epmDocRevision = "";
				    EPMDocument epmDoc = ketProdECAEpmDocLink.getEpmDoc();
				    epmDocRevision = (epmDoc != null) ? epmDoc.getVersionIdentifier().getValue() : "";
				    if (epmDoc.getLifeCycleState().equals(State.toState("INWORK"))
					    && (epmDocRevision.equals("") || epmDocRevision.equals("0"))) {
					// 통과
				    } else {

					// 개정유무 Validation 처리
					String afterVersion = ketProdECAEpmDocLink.getAfterVersion();
					if (afterVersion == null || afterVersion.equals("")) {
					    // <entry key="04380">개정되지 않은 도면이 있습니다.</entry>
					    throw new WTException(messageService.getString("e3ps.message.ket_message", "04380"));

					}

				    }

				}

			    }
			}
			// if( activityType.equals("1") )
			// {
			// beans.updateMoldPlan( ecoOid );
			// }

			if (ecaActivityType.equals("3")) {
			    // ECN의 문서일 경우
			    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "doc", KETProdECADocLink.class, false);
			    if (queryResult.hasMoreElements()) {
				KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) queryResult.nextElement();

				KETProjectDocument ketProjectDocument = ketProdECADocLink.getDoc();

				// 개정유무 Validation 처리
				String afterVersion = ketProdECADocLink.getAfterVersion();
				if (afterVersion == null || afterVersion.equals("")) {

				    State StateFlag = ketProjectDocument.getState().getState();

				    if (!("0".equals(ketProjectDocument.getVersionIdentifier().getValue()) && StateFlag.equals(State
					    .toState("INWORK")))) {
					// Revision No : 0 & 결재상태:진행중인 경우는 개정없이 등록가능하게 변경
					// <entry key="04400">개정되지 않은 문서가 있습니다.</entry>
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04400") + "\\n\\n";
					checkEcaList.add(CommonUtil.getOIDString(eca));
					// throw new WTException(messageService.getString("e3ps.message.ket_message", "04400"));
				    }

				} else {

				    // 결재 승인 유무

				    String documentOid = EcmUtil.getDocumentOid(ketProjectDocument.getNumber(), afterVersion);
				    if (documentOid != null && !documentOid.equals("")) {
					KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument) CommonUtil
					        .getObject(documentOid);
					// KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument)
					// ObjectUtil.getLatestVersion(ketProjectDocument);

					State ketProjectDocumentLatestVersionState = ketProjectDocumentLatestVersion.getState().getState();
					// if (ketProjectDocumentLatestVersionState.equals(State.toState("INWORK")) ||
					// ketProjectDocumentLatestVersionState.equals(State.toState("UNDERREVIEW"))) {
					if (!ketProjectDocumentLatestVersionState.equals(State.toState("APPROVED"))) {
					    // <entry key="05000">결재승인되지 않은 문서가 있습니다.</entry>
					    // throw new WTException(messageService.getString("e3ps.message.ket_message", "05000"));
					    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "05000") + "\\n\\n";
					    checkEcaList.add(CommonUtil.getOIDString(eca));
					}
				    } else {
					// <entry key="04001">개정한 문서가 삭제되었습니다.\n관리자에게 문의하여 주십시오.</entry>
					// throw new WTException(messageService.getString("e3ps.message.ket_message", "04001"));
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04001") + "\\n\\n";
					checkEcaList.add(CommonUtil.getOIDString(eca));

				    }
				}
			    } else {
				// <entry key="04006">ECN의 후속업무[{0}] 타입이 문서일 경우 개정할 문서를 추가하여 주십시오.</entry>
				// throw new WTException(messageService.getString("e3ps.message.ket_message", "04006",
				// eca.getCustomName()));
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04006") + "\\n\\n";
				checkEcaList.add(CommonUtil.getOIDString(eca));

			    }
			}

			if (ecaActivityType.equals("4")) {
			    if (StringUtils.isEmpty(eca.getActivityTypeDesc())) {
				// throw new WTException("ECN의 후속업무[" + eca.getCustomName() + "] 타입이 활동일 경우 내용을 입력하십시오.");
				ALERT_MESSAGE += "ECN의 후속업무[" + eca.getCustomName() + "] 타입이 활동일 경우 내용을 입력하십시오.\\n\\n";
				checkEcaList.add(CommonUtil.getOIDString(eca));
			    }
			}

		    } // if (PersistenceHelper.isPersistent(eca)) {
		} // for (int i = 0; i < prodEcaListSize; i++) {

		// 3. 가져온 KETProdChangeActivity 의 워크플로우를 완료시킨다.
		String isActivityReg = paramMap.getString("isActivityReg");

		for (int i = 0; i < prodEcaListSize; i++) {
		    boolean goFlag = true;
		    KETProdChangeActivity eca = (KETProdChangeActivity) prodEcaList.get(i);
		    if (PersistenceHelper.isPersistent(eca)) {
			for (String ecaOid : checkEcaList) {
			    if (CommonUtil.getOIDString(eca).equals(ecaOid)) {
				goFlag = false;
				break;
			    }
			}
			// BOM
			QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
			while (queryResult.hasMoreElements()) {
			    KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult.nextElement();

			    // 초도일 경우 개정된 리비전이 없을 경우 이전(초기) 리비전을 set 한다.
			    /*
		             * if (isTheFirst) { String afterVersion = StringUtils.stripToEmpty(ketProdECABomLink.getAfterVersion()); String
		             * preVersion = StringUtils.stripToEmpty(ketProdECABomLink.getPreVersion()); if (afterVersion.equals("") &&
		             * !preVersion.equals("")) { ketProdECABomLink.setAfterVersion(preVersion);
		             * 
		             * PersistenceHelper.manager.save(ketProdECABomLink); ketProdECABomLink = (KETProdECABomLink)
		             * PersistenceHelper.manager.refresh(ketProdECABomLink);
		             * 
		             * } }
		             */

			    KETBomEcoHeader ketBomEcoHeader = ketProdECABomLink.getBom();

			    // BOM Editor에서의 작업유무 확인
			    /*
		             * FROM ketprodchangeactivity t1 , ketprodecabomlink t2 , ketbomecoheader h , ketbomecocomponent c WHERE
		             * h.ecoheadernumber = c.ecoheadernumber AND h.ecoitemcode = c.parentitemcode AND h.ecoitemcode = c.ecoitemcode
		             * AND t1.ida3a8 = 100000388834 AND t2.ida3b5 = t1.ida2a2 AND t2.ida3a5 = h.ida2a2 AND t1.completedate IS NOT
		             * NULL AND c.ecocode is not null
		             */
			    /*
		             * StringBuffer sql = new StringBuffer(); sql.append("SELECT COUNT(*) AS CNT \n");
		             * sql.append("  FROM KETBomEcoComponent \n"); sql.append(" WHERE ecoheadernumber = ? \n");
		             * //sql.append("   AND parentitemcode = ? \n"); //sql.append("   AND ecoitemcode = ? \n");
		             * sql.append("   AND ecocode is not null \n");
		             * 
		             * PreparedStatement preparedStatement = conn.prepareStatement(sql.toString()); preparedStatement.setString(1,
		             * ketBomEcoHeader.getEcoHeaderNumber()); //preparedStatement.setString(2, ketBomEcoHeader.getEcoItemCode());
		             * //preparedStatement.setString(3, ketBomEcoHeader.getEcoItemCode());
		             * 
		             * ResultSet resultSet = preparedStatement.executeQuery(); if (resultSet.next()) { String cnt =
		             * resultSet.getString("CNT"); if (cnt.equals("0")) { // <entry key="04500">변경하지 않은 부품이 있습니다.</entry> throw new
		             * WTException(messageService.getString("e3ps.message.ket_message", "04500"));
		             * 
		             * } }
		             */

			    // 1. BOMHeader 의 Attribute3에 'Y'를 넣어준다.
			    // Todo에서 '작업완료'했음을 나타내게 한다.
			    ketBomEcoHeader.setAttribute3("Y");
			    ketBomEcoHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(ketBomEcoHeader);

			    // 2. AS-IS를 위하여 아래 코드를 실행한다.
			    /*
		             * count 가 1이상일 경우 작업완료 할 수 없도록 하고 있었다.
		             * 
		             * SELECT COUNT(*) FROM ketbomecocoworker w WHERE w.ecoheadernumber = 'ECO-1409-054' AND w.ecoitemcode =
		             * '610008R' AND w.endworkingflag <> '4'
		             */
			    String ecoHeaderNo = ketBomEcoHeader.getEcoHeaderNumber();
			    String ecoItemCode = ketBomEcoHeader.getEcoItemCode();
			    String coworkerId = session.getName();
			    String flag = "endWorking";

			    BOMSearchUtilDao bomSearchUtilDao = new BOMSearchUtilDao();
			    bomSearchUtilDao.updateEndWorkingFlag(ecoHeaderNo, ecoItemCode, coworkerId, flag);

			}
			if (!goFlag || "WORKCOMPLETED".equals(eca.getActivityStatus())) {
			    continue;
			}

			// Todo의 '작업완료'에서 왔을 경우
			if (isActivityReg == null || isActivityReg.equals("")) {

			    // 가져온 KETProdChangeActivity 의 워크플로우를 완료시킨다.
			    WorkflowSearchHelper.manager.taskComplete(eca);

			} else {

			    Timestamp completeDate = DateUtil.getCurrentTimestamp();
			    eca.setCompleteDate(completeDate);
			    // Added by MJOH, 2011-03-30
			    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
			    eca.setActivityStatus("WORKCOMPLETED");
			    PersistenceHelper.manager.save(eca);
			    eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);
			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("WORKCOMPLETED"));

			}

			// HEENEETODO : 여러 후처리들이 남아있다.
			/*
		         * 모든 ECN의 후속작업 작업완료 시, ECN 자동 완료 및 ECN 완료 여부 자동 메일 공지 메일 수신자 : 모든 ECN 담당자
		         */

		    }

		}

	    }

	    trx.commit();
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }

	}

	return ALERT_MESSAGE;
    }

    @Override
    public String stopEcn(KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();

	    String[] ecnOids = paramMap.getStringArray("ecnOid");

	    int ecnOidsLength = (ecnOids != null) ? ecnOids.length : 0;
	    RENEW: for (int i = 0; i < ecnOidsLength; i++) {

		// ECN
		KETChangeNotice ecn = (KETChangeNotice) CommonUtil.getObject(ecnOids[i]);
		State ecnState = ecn.getLifeCycleState();
		if (ecnState.equals(State.toState("ACTIVITYCOMPLETED")) || ecnState.equals(State.toState("STOPED"))) {

		    // <entry key="04020">ECN[{0}]는 중지할 수 있는 상태가 아닙니다.</entry>
		    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04020", ecn.getEcnNumber()) + "\\n";

		    continue RENEW;
		}

		// ECA
		QueryResult result = PersistenceHelper.manager.navigate(ecn, "eco", KETEcoEcnLink.class, false);
		while (result.hasMoreElements()) {
		    KETEcoEcnLink link = (KETEcoEcnLink) result.nextElement();
		    WTChangeOrder2 eco = link.getEco();
		    WTUser ecoCreator = (WTUser) eco.getCreator().getPrincipal();
		    if (!loginUser.equals(ecoCreator)) {

			// ECN[{0}]는 삭제권한이 없습니다.
			ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04019", ecn.getEcnNumber()) + "\\n";

			continue RENEW;
		    }

		    if (eco instanceof KETProdChangeOrder) {
			QueryResult result1 = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class, false);
			while (result1.hasMoreElements()) {
			    KETProdChangeActivityLink link1 = (KETProdChangeActivityLink) result1.nextElement();
			    KETProdChangeActivity eca = link1.getProdECA();

			    String activityType = eca.getActivityType();
			    if ((activityType.equals("3") || activityType.equals("4")) && eca.getCompleteDate() == null) {
				eca.setActivityStatus("REJECTED");
				PersistenceHelper.manager.save(eca);
				eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);

				LifeCycleServerHelper.service.setState((LifeCycleManaged) eca, State.toState("REJECTED"));

				// 가져온 KETProdChangeActivity 의 워크플로우를 완료시킨다.
				// WorkflowSearchHelper.manager.taskComplete(eca);
				WorkflowSearchHelper.manager.deleteWorkItem(eca);
			    }

			}
		    } else if (eco instanceof KETMoldChangeOrder) {
			QueryResult result1 = PersistenceHelper.manager.navigate(eco, "moldECA", KETMoldChangeActivityLink.class, false);
			while (result1.hasMoreElements()) {
			    KETMoldChangeActivityLink link1 = (KETMoldChangeActivityLink) result1.nextElement();
			    KETMoldChangeActivity eca = link1.getMoldECA();

			    String activityType = eca.getActivityType();
			    if (activityType.equals("4") && eca.getCompleteDate() == null) {
				eca.setActivityStatus("REJECTED");
				PersistenceHelper.manager.save(eca);
				eca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(eca);
				LifeCycleServerHelper.service.setState((LifeCycleManaged) eca, State.toState("REJECTED"));

				// 가져온 KETProdChangeActivity 의 워크플로우를 완료시킨다.
				// WorkflowSearchHelper.manager.taskComplete(eca);
				WorkflowSearchHelper.manager.deleteWorkItem(eca);
			    }

			}
		    }

		}

		LifeCycleServerHelper.service.setState((LifeCycleManaged) ecn, State.toState("STOPED"));

	    }

	    trx.commit();

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String restartEcn(KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();

	    String[] ecnOids = paramMap.getStringArray("ecnOid");

	    int ecnOidsLength = (ecnOids != null) ? ecnOids.length : 0;
	    RENEW: for (int i = 0; i < ecnOidsLength; i++) {
		State ecoState = null;

		// ECN
		KETChangeNotice ecn = (KETChangeNotice) CommonUtil.getObject(ecnOids[i]);
		State ecnState = ecn.getLifeCycleState();
		if (!ecnState.equals(State.toState("STOPED"))) {

		    // <entry key="04070">ECN[{0}]는 재시작할 수 있는 상태가 아닙니다.</entry>
		    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04070", ecn.getEcnNumber()) + "\\n";

		    continue RENEW;
		}

		// ECA
		QueryResult result = PersistenceHelper.manager.navigate(ecn, "eco", KETEcoEcnLink.class, false);
		while (result.hasMoreElements()) {
		    KETEcoEcnLink link = (KETEcoEcnLink) result.nextElement();
		    WTChangeOrder2 eco = link.getEco();
		    ecoState = eco.getLifeCycleState();

		    if (!KETObjectUtil.isAdmin()) {

			// ECN[{0}]는 삭제권한이 없습니다.
			ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04019", ecn.getEcnNumber()) + "\\n";

			continue RENEW;
		    }

		    if (eco instanceof KETProdChangeOrder) {
			QueryResult result1 = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class, false);
			while (result1.hasMoreElements()) {
			    KETProdChangeActivityLink link1 = (KETProdChangeActivityLink) result1.nextElement();
			    KETProdChangeActivity eca = link1.getProdECA();

			    String activityType = eca.getActivityType();
			    if ((activityType.equals("3") || activityType.equals("4")) && eca.getCompleteDate() == null) {
				eca.setActivityStatus("INWORK");
				PersistenceHelper.manager.save(eca);
				eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);
				LifeCycleServerHelper.service.setState((LifeCycleManaged) eca, State.toState("INWORK"));

				// 가져온 KETProdChangeActivity 의 Todo를 재가동 시킨다.
				String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
				// 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
				if (!workerId.equals("")) {
				    WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
				    if (ecaCharge != null) {
					Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
					WfTemplateObject paramWfTemplateObject = null;

					while (allTemplates.hasMoreElements()) {
					    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

					    if (wfTemplate.getName().equals("KET_TODO_WF")) {
						paramWfTemplateObject = wfTemplate;
					    }
					}

					if (paramWfTemplateObject != null) {
					    Team team = (Team) eca.getTeamId().getObject();
					    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
						    eca.getContainerReference());
					    ProcessData data = process.getContext();
					    WfEngineServerHelper.service.setPrimaryBusinessObject(process, eca);
					    data.setValue("charge", ecaCharge);
					    WfEngineHelper.service.startProcess(process, data, 1);
					}

					try {
					    // ECN 후속활동 지정시(ECN 상태: 계획수립-->활동수행) 김근우 Mail 처리 (재시작시에도 이메일사용)
					    List<WTUser> toUserList = new ArrayList<WTUser>();
					    toUserList.add(ecaCharge);
					    KETMailHelper.service.sendFormMail("08027", "NoticeMailLine2.html", eca, (WTUser) eca
						    .getCreator().getPrincipal(), toUserList);
					} catch (Exception e) {
					    // TODO Auto-generated catch block
					    Kogger.error(getClass(), e);
					}

					// To-Do 공지
					// try {
					// WTUser member = CommonUtil.findUserID(eca.getCreatorName());
					// WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
					// WTPrincipalReference toUser = WTPrincipalReference.newWTPrincipalReference(prin);
					// Hashtable contentHash = MailUtil.getMailContent("approval", eca, toUser.getName());
					// String templateName = CommonUtil.getMailTemplateName(toUser.getName(), "TaskNoticeMail");
					// String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
					// Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents);
					// if (hash != null)
					// MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
					//
					// } catch (Exception e) {
					//
					// }
				    }
				}

			    }
			}
		    } else if (eco instanceof KETMoldChangeOrder) {
			QueryResult result1 = PersistenceHelper.manager.navigate(eco, "moldECA", KETMoldChangeActivityLink.class, false);
			while (result1.hasMoreElements()) {
			    KETMoldChangeActivityLink link1 = (KETMoldChangeActivityLink) result1.nextElement();
			    KETMoldChangeActivity eca = link1.getMoldECA();

			    String activityType = eca.getActivityType();
			    if (activityType.equals("4") && eca.getCompleteDate() == null) {
				eca.setActivityStatus("INWORK");
				PersistenceHelper.manager.save(eca);
				eca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(eca);
				LifeCycleServerHelper.service.setState((LifeCycleManaged) eca, State.toState("INWORK"));

				// 가져온 KETProdChangeActivity 의 Todo를 재가동 시킨다.
				String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
				// 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
				if (!workerId.equals("")) {
				    WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
				    if (ecaCharge != null) {
					Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
					WfTemplateObject paramWfTemplateObject = null;

					while (allTemplates.hasMoreElements()) {
					    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

					    if (wfTemplate.getName().equals("KET_TODO_WF")) {
						paramWfTemplateObject = wfTemplate;
					    }
					}

					if (paramWfTemplateObject != null) {
					    Team team = (Team) eca.getTeamId().getObject();
					    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
						    eca.getContainerReference());
					    ProcessData data = process.getContext();
					    WfEngineServerHelper.service.setPrimaryBusinessObject(process, eca);
					    data.setValue("charge", ecaCharge);
					    WfEngineHelper.service.startProcess(process, data, 1);
					}

					try {
					    // ECN 후속활동 지정시(ECN 상태: 계획수립-->활동수행) 김근우 Mail 처리 (재시작시에도 이메일사용)
					    List<WTUser> toUserList = new ArrayList<WTUser>();
					    toUserList.add(ecaCharge);
					    KETMailHelper.service.sendFormMail("08027", "NoticeMailLine2.html", eca, (WTUser) eca
						    .getCreator().getPrincipal(), toUserList);
					} catch (Exception e) {
					    // TODO Auto-generated catch block
					    Kogger.error(getClass(), e);
					}

					// To-Do 공지
					// try {
					// WTUser member = CommonUtil.findUserID(eca.getCreatorName());
					// WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
					// WTPrincipalReference toUser = WTPrincipalReference.newWTPrincipalReference(prin);
					// Hashtable contentHash = MailUtil.getMailContent("approval", eca, toUser.getName());
					// String templateName = CommonUtil.getMailTemplateName(toUser.getName(), "TaskNoticeMail");
					// String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
					// Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents);
					// if (hash != null)
					// MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
					//
					// } catch (Exception e) {
					//
					// }
				    }
				}

			    }

			}
		    }

		}

		if (ecoState != null && ecoState.equals(State.toState("APPROVED"))) {
		    LifeCycleServerHelper.service.setState((LifeCycleManaged) ecn, State.toState("EXCUTEACTIVITY"));
		} else {
		    LifeCycleServerHelper.service.setState((LifeCycleManaged) ecn, State.toState("PLANNING"));
		}

	    }

	    trx.commit();

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @SuppressWarnings("unused")
    @Override
    public String callMeeting(String ecrOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	try {
	    trx.start();

	    String oid = paramMap.getString("oid");
	    String callMeetingName = paramMap.getString("callMeetingName");
	    String callMeetingDate = paramMap.getString("callMeetingDate");
	    String callMeetingTime = paramMap.getString("callMeetingTime");
	    String callMeetingLocation = paramMap.getString("callMeetingLocation");
	    String callNumber = paramMap.getString("callNumber");

	    String[] callDepartmentDisplayNames = paramMap.getStringArray("callDepartmentDisplayName");
	    String[] callDepartmentOids = paramMap.getStringArray("callDepartmentOid");
	    String[] callDepartmentCodes = paramMap.getStringArray("callDepartmentCode");

	    String[] callAttendanceDisplayNames = paramMap.getStringArray("callAttendanceDisplayName");
	    String[] callAttendanceOids = paramMap.getStringArray("callAttendanceOid");
	    String[] callAttendanceNames = paramMap.getStringArray("callAttendanceName");

	    // ECR 객체화
	    ReferenceFactory rf = new ReferenceFactory();
	    WTChangeRequest2 ecr = (WTChangeRequest2) rf.getReference(ecrOid).getObject();

	    // 보낸 횟수를 찾는다.
	    /*
	     * String callNumber = null; QueryResult qr = PersistenceHelper.manager.navigate(ecr, "call", KETEcrCallLink.class); while
	     * (qr.hasMoreElements()) { KETMeetingCall ketMeetingCall = (KETMeetingCall) qr.nextElement(); callNumber =
	     * ketMeetingCall.getCallNumber();
	     * 
	     * }
	     */
	    if (callNumber == null || callNumber.equals(""))
		callNumber = "0";
	    callNumber = String.valueOf((Integer.valueOf(callNumber).intValue() + 1));

	    int callAttendanceOidLength = (callAttendanceOids != null) ? callAttendanceOids.length : 0;
	    for (int i = 0; i < callAttendanceOidLength; i++) {
		// Insert
		// 회의소집
		KETMeetingCall call = KETMeetingCall.newKETMeetingCall();

		// 회의명
		call.setCallMeetingName(callMeetingName);

		// 회의일시
		call.setCallMeetingDate(DateUtil.convertStartDate2(callMeetingDate));
		// 회의시간
		call.setCallMeetingTime(callMeetingTime);
		// 회의장소
		call.setCallMeetingLocation(callMeetingLocation);

		// 발신일
		call.setCallSendDate(DateUtil.getCurrentTimestamp());

		// 요청 횟수
		call.setCallNumber(callNumber);

		// 참석요청 대상부서
		String callDepartmentOid = callDepartmentOids[i];
		Department callDepartment = (callDepartmentOid != null && !callDepartmentOid.equals("")) ? (Department) rf.getReference(
		        callDepartmentOid).getObject() : null;
		call.setCallDepartment(callDepartment);

		String callDepartmentCode = callDepartmentCodes[i];
		callDepartmentCode = ((callDepartmentCode == null || callDepartmentCode.equals("")) && callDepartment != null) ? callDepartment
		        .getCode() : callDepartmentCode;
		call.setCallDepartmentCode(callDepartmentCode);

		// 참석요청 대상자
		String callAttendanceOid = callAttendanceOids[i];
		WTUser callAttendance = (callAttendanceOid != null && !callAttendanceOid.equals("")) ? (WTUser) rf.getReference(
		        callAttendanceOid).getObject() : null;
		call.setCallAttendance(callAttendance);

		String callAttendanceName = callAttendanceNames[i];
		callAttendanceName = ((callAttendanceName == null || callAttendanceName.equals("")) && callAttendance != null) ? callAttendance
		        .getName() : callAttendanceName;
		call.setCallAttendanceName(callAttendanceName);

		// 저장
		call = (KETMeetingCall) PersistenceHelper.manager.save(call);

		// Link
		KETEcrCallLink link = KETEcrCallLink.newKETEcrCallLink(ecr, call);
		PersistenceHelper.manager.save(link);
	    }

	    // HEENEETODO : Send Mail
	    WTChangeRequest2 wtChangeRequest2 = (WTChangeRequest2) CommonUtil.getObject(ecrOid);
	    List<WTUser> toUserList = new ArrayList<WTUser>();

	    // toUserList set해야함
	    try {
		for (int i = 0; i < callAttendanceOidLength; i++) {

		    String callAttendanceOid = callAttendanceOids[i];
		    WTUser callAttendance = (callAttendanceOid != null && !callAttendanceOid.equals("")) ? (WTUser) rf.getReference(
			    callAttendanceOid).getObject() : null;
		    toUserList.add(callAttendance);
		}
		KETMailHelper.service.sendFormMail("08024", "NoticeMailLine2.html", wtChangeRequest2, (WTUser) SessionHelper.manager
		        .getPrincipalReference().getPrincipal(), toUserList);
	    } catch (WTException e) {
		// TODO Auto-generated catch block
		Kogger.error(getClass(), e);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		Kogger.error(getClass(), e);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    trx = null;

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String resendERP(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();

	boolean isTheFirstStart = false;
	boolean isTheChangeStart = false;
	try {
	    trx.start();

	    /*
	     * String ecoLongValue = KETObjectUtil.getIda2a2(ecoOid); boolean isSucessSapInterface =
	     * KETBomHelper.service.getBomEcoInterface(ecoLongValue); if (!isSucessSapInterface) { throw new WTException("작업을 실패하였습니다."); }
	     */

	    // BOM, ERP와의 인터페이스
	    String ecoLongValue = "";
	    boolean isProd = false;

	    String changeReason = "";
	    boolean isTheFirst = false;
	    WTChangeOrder2 wtChangeOrder2 = (WTChangeOrder2) CommonUtil.getObject(ecoOid);
	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {
		KETProdChangeOrder ketProdChangeOrder = (KETProdChangeOrder) wtChangeOrder2;

		ecoLongValue = KETObjectUtil.getIda2a2(ketProdChangeOrder.toString());
		isProd = true;

		changeReason = StringUtils.stripToEmpty(ketProdChangeOrder.getChangeReason());
		isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);
	    } else { // if(wtChangeOrder2 instanceof KETProdChangeOrder) {
		KETMoldChangeOrder ketMoldChangeOrder = (KETMoldChangeOrder) wtChangeOrder2;

		ecoLongValue = KETObjectUtil.getIda2a2(ketMoldChangeOrder.toString());
		isProd = false;

		changeReason = StringUtils.stripToEmpty(ketMoldChangeOrder.getChangeReason());
		isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);
	    }

	    boolean infResult = false;

	    // 초도일 경우
	    if (isTheFirst) {
		infResult = KETBomHelper.service.getBomInterface2(ecoLongValue, isProd)[0];
		if (infResult) {
		    KogDbUtil.log("ECO 초도 I/F", "Success", ecoOid, (String) null, null, null);
		    isTheFirstStart = true;
		}

	    }
	    // 설변일 경우
	    else {
		infResult = KETBomHelper.service.getBomEcoInterface(ecoLongValue, isProd)[0];
		if (infResult) {
		    KogDbUtil.log("ECO 설변 I/F", "Success", ecoOid, (String) null, null, null);
		    isTheChangeStart = true;
		}
	    }

	    // 실패시 메일을 보낸다.
	    /*
	     * if (!infResult) { try { WTPrincipalReference toUser = wtChangeOrder2.getCreator(); Hashtable contentHash =
	     * MailUtil.getMailContent("infFail", wtChangeOrder2, toUser.getName()); String templateName =
	     * CommonUtil.getMailTemplateName(toUser.getName(), "InterfaceFailNotice"); String contents =
	     * MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName); Hashtable hash =
	     * MailUtil.prepareMailInfoHash(toUser, contents); if (hash != null) MailUtil.sendMail2(hash, (String)
	     * contentHash.get("mailTitle"));
	     * 
	     * } catch (Exception e) {
	     * 
	     * } }
	     */

	    if (!infResult) {

		// <entry key="04490">ERP I/F에서 에러가 발생하였습니다.</entry>
		ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04490") + "\\n";

	    }

	    trx.commit();

	} catch (WTException wte) {
	    trx.rollback();

	    if (isTheFirstStart) {
		KogDbUtil.log("ECO 초도 I/F", "Fail", ecoOid, wte, null, null);
	    }

	    if (isTheChangeStart) {
		KogDbUtil.log("ECO 설변 I/F", "Fail", ecoOid, wte, null, null);
	    }

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    if (isTheFirstStart) {
		KogDbUtil.log("ECO 초도 I/F", "Fail", ecoOid, e, null, null);
	    }

	    if (isTheChangeStart) {
		KogDbUtil.log("ECO 설변 I/F", "Fail", ecoOid, e, null, null);
	    }

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public KETMoldChangeOrderVO updateMoldEcoInfo(MoldEcoChangeServlet MOLD_ECO_CHANGE_SERVLET) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";
	String revised_message = "";

	KETMoldChangeOrderVO ketMoldChangeOrderVO = new KETMoldChangeOrderVO();

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();

	    KETMoldChangeOrder ketMoldChangeOrder = null;
	    ketMoldChangeOrder = (KETMoldChangeOrder) CommonUtil.getObject(MOLD_ECO_CHANGE_SERVLET.oid);

	    if (MOLD_ECO_CHANGE_SERVLET.isToDo != null && MOLD_ECO_CHANGE_SERVLET.isToDo.equalsIgnoreCase("Y")) {

		// Todo에서 왔을 경우 '기본사항'을 저장하지 않는다.
		ketMoldChangeOrderVO.setIsToDO(MOLD_ECO_CHANGE_SERVLET.isToDo);
		ketMoldChangeOrderVO.setIsCompleteModify(MOLD_ECO_CHANGE_SERVLET.isCompleteModify);
		ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);

	    } else {
		ketMoldChangeOrder.setEcoName(MOLD_ECO_CHANGE_SERVLET.ecoName);
		ketMoldChangeOrder.setDevYn(MOLD_ECO_CHANGE_SERVLET.devYn);// 개발/양산구분
		ketMoldChangeOrder.setDivisionFlag(MOLD_ECO_CHANGE_SERVLET.divisionFlag);// 사업부구분
		ketMoldChangeOrder.setProjectOid(MOLD_ECO_CHANGE_SERVLET.projectOid);// 관련 프로젝트
		ketMoldChangeOrder.setProdVendor(MOLD_ECO_CHANGE_SERVLET.prodVendor);
		ketMoldChangeOrder.setVendorFlag(MOLD_ECO_CHANGE_SERVLET.vendorFlag);
		ketMoldChangeOrder.setChangeReason(MOLD_ECO_CHANGE_SERVLET.changeReason);
		ketMoldChangeOrder.setOtherReasonDesc(MOLD_ECO_CHANGE_SERVLET.otherReasonDesc);
		ketMoldChangeOrder.setIncreaseProdType(MOLD_ECO_CHANGE_SERVLET.increaseProdType);
		ketMoldChangeOrder.setOtherIncreaseProdType(MOLD_ECO_CHANGE_SERVLET.otherIncreaseProdType);
		ketMoldChangeOrder.setEcoWorkerId(MOLD_ECO_CHANGE_SERVLET.ecoWorkerId);// ECO담당자id
		ketMoldChangeOrderVO.setOldEcoWorkerId(MOLD_ECO_CHANGE_SERVLET.oldEcoWorkerId);// 기존ECO담당자id
		ketMoldChangeOrderVO.setKetMoldChangeOrder(ketMoldChangeOrder);

		java.util.Vector files = MOLD_ECO_CHANGE_SERVLET.uploader.getFiles();
		ketMoldChangeOrderVO.setFiles(files);
		ketMoldChangeOrderVO.setDeleteFileList(MOLD_ECO_CHANGE_SERVLET.deleteFileList);
		ketMoldChangeOrderVO.setIsToDO(MOLD_ECO_CHANGE_SERVLET.isToDo);
		ketMoldChangeOrderVO.setIsCompleteModify(MOLD_ECO_CHANGE_SERVLET.isCompleteModify);

		ketMoldChangeOrderVO.setDeleteRelEcrList(MOLD_ECO_CHANGE_SERVLET.deleteRelEcrList);// 삭제대상 연계 ECR
		ketMoldChangeOrderVO.setDeleteRelProdEcoList(MOLD_ECO_CHANGE_SERVLET.deleteRelProdEcoList);// 삭제대상 연계 제품ECO
		ketMoldChangeOrderVO.setDeleteRelPartList(MOLD_ECO_CHANGE_SERVLET.deleteRelPartList);// 삭제대상 관련부품oid

		ketMoldChangeOrderVO.setKetMoldEcoEcrLinkVOList(MOLD_ECO_CHANGE_SERVLET.createKETMoldEcoEcrLink());// 연계 ECR
		ketMoldChangeOrderVO.setKetMoldEcoProdEcoLinkVOList(MOLD_ECO_CHANGE_SERVLET.createKETMoldEcoProdEcoLink());// 연계 제품ECO
		ketMoldChangeOrderVO.setKetMoldECOPartLinkVOList(MOLD_ECO_CHANGE_SERVLET.createKETMoldECOPartLink());// 관련부품

	    }

	    ketMoldChangeOrderVO.setDeleteRelEpmList(MOLD_ECO_CHANGE_SERVLET.deleteRelEpmList);// 삭제대상 관련도면oid
	    ketMoldChangeOrderVO.setDeleteRelBomList(MOLD_ECO_CHANGE_SERVLET.deleteRelBomList);// 삭제대상 관련BOMoid
	    ketMoldChangeOrderVO.setDeleteRelDocList(MOLD_ECO_CHANGE_SERVLET.deleteRelDocList);// 삭제대상 관련문서oid

	    ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList = new ArrayList<KETMoldECALinkVO>();// 금형ECA List
	    ketMoldECALinkVOList = MOLD_ECO_CHANGE_SERVLET.createKetMoldECALinkVOForDoc(ketMoldECALinkVOList);// 표준품목록 VO생성
	    ketMoldECALinkVOList = MOLD_ECO_CHANGE_SERVLET.createKetMoldECALinkVOForEpm(ketMoldECALinkVOList);// 도면목록 VO생성

	    ketMoldECALinkVOList = this.createKetMoldECALinkVOForBom(ketMoldECALinkVOList, MOLD_ECO_CHANGE_SERVLET, ketMoldChangeOrder,
		    wtConn);// BOM목록 VO생성

	    ketMoldChangeOrderVO.setKetMoldECALinkVOList(ketMoldECALinkVOList);// 변경활동 VO 세팅

	    ketMoldChangeOrderVO = KETEcmHelper.service.updateMoldEcoInfo(ketMoldChangeOrderVO);

	    trx.commit();
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }
	}

	// 이미 개정되어 Skip 된 부품에 대한 메세지 처리
	if (!revised_message.equals("")) {
	    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04690") + "\\n" + revised_message + "\\n";
	}
	return ketMoldChangeOrderVO;
    }

    private ArrayList<KETMoldECALinkVO> createKetMoldECALinkVOForBom(ArrayList<KETMoldECALinkVO> ketMoldECALinkVOList,
	    MoldEcoChangeServlet MOLD_ECO_CHANGE_SERVLET, KETMoldChangeOrder ketMoldChangeOrder, WTConnection wtConn) throws Exception {
	if (MOLD_ECO_CHANGE_SERVLET.relEcaBomOid == null) {
	    return ketMoldECALinkVOList;
	}

	KETMoldECALinkVO ketMoldECALinkVO = null;
	int size = MOLD_ECO_CHANGE_SERVLET.relEcaBomOid.length;
	String afterRev = "";
	boolean isSuccess = false;
	try {

	    Connection conn = wtConn.getConnection();

	    /**
	     * tklee 수정 13.1.14 : revise ObjectNoLongerExistsException 에러 처리
	     */
	    List<Map> beforePartArray = new ArrayList<Map>();
	    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
	    if (MOLD_ECO_CHANGE_SERVLET.beforePartOid == null) {
	    } else if (MOLD_ECO_CHANGE_SERVLET.beforePartOid.length == 0) {
	    } else {
		KETBomPartUtil pUtil = new KETBomPartUtil();
		for (int k = 0; k < MOLD_ECO_CHANGE_SERVLET.beforePartOid.length; k++) {
		    String beforePartOid = MOLD_ECO_CHANGE_SERVLET.beforePartOid[k];
		    if (StringUtils.isEmpty(beforePartOid)) {
			Map partInfoMap = new HashMap();
			partInfoMap.put("partNumber", "");
			partInfoMap.put("version", "");
			partInfoMap.put("partType", "");
			partInfoMap.put("partOid", "");

			beforePartArray.add(partInfoMap);

		    } else {
			WTPart part = pUtil.getPart(beforePartOid);
			Kogger.debug(getClass(), "beforePartInfo *************>>>>>>>" + part.getNumber());
			Map partInfoMap = new HashMap();
			partInfoMap.put("partNumber", part.getNumber());
			partInfoMap.put("version", part.getVersionIdentifier().getValue().toString());

			String oldPartOid2 = ketBOMQueryBean.getPartOid2(part.getNumber(), part.getVersionIdentifier().getValue()
			        .toString(), wtConn);
			if (!oldPartOid2.equals(""))
			    part = pUtil.getPart(oldPartOid2);

			Kogger.debug(getClass(), "oldVersion-------$$$$$$$$$****************************>>>>>>>"
			        + part.getVersionIdentifier().getValue().toString());
			partInfoMap.put("partType", PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType));
			long id = pUtil.getPartLongId(part);
			String partOid = Long.toString(id);
			partInfoMap.put("partOid", partOid);

			beforePartArray.add(partInfoMap);
		    }
		}
	    }

	    ProdEcoBeans beans = new ProdEcoBeans();
	    EcmComDao comDao = new EcmComDao(conn);
	    RENEW: for (int i = 0; i < size; i++) {
		ketMoldECALinkVO = new KETMoldECALinkVO();
		ketMoldECALinkVO.setActivityType(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomActivityType[i]));
		ketMoldECALinkVO.setRelEcaObjectLinkOid(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomLinkOid[i]));// BOMlinkoid
		ketMoldECALinkVO.setRelEcaObjectOid(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomOid[i]));// BOMoid
		ketMoldECALinkVO.setRelEcaObjectNo(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]));// BOM번호
		ketMoldECALinkVO.setRelEcaObjectName(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomName[i]));// BOM명
		ketMoldECALinkVO.setRelEcaObjectPreRev(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomPreRev[i]));// BOM이전버전
		ketMoldECALinkVO.setRelEcaOid(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.moldEcaBomOid[i]));// ECAoid
		ketMoldECALinkVO.setBeforePartOid(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.beforePartOid[i]));// Before부품oid
		afterRev = StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomAfterRev[i]);

		EcmUtil.logging("createKetMoldECALinkVOForBom:relEcaBomReviseYn[" + i + "]:"
		        + StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomReviseYn[i]));
		if ("Y".equals(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomReviseYn[i]))) {

		    /*
	             * afterRev = reviseBom(MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]);
	             */

		    // 개정처리

		    // 이미 개정한 것은 Skip한다.
		    KETBomEcoHeader ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(ketMoldChangeOrder.getEcoId(),
			    MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]);
		    String hasRevised = ecoBomHeader.getAttribute1();
		    if (hasRevised != null && hasRevised.equalsIgnoreCase("Y")) {
			// revised_message += ecoBomHeader.getEcoItemCode() + "\\n";

			continue RENEW;
		    }

		    WTPart part = null;
		    String afterVersion = "";
		    KETMoldECABomLink bomLink = null;

		    part = beans.getLastestPart(MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i], conn);

		    /*
	             * 이응진의 글... 부품 개정시에 사용된 곳
	             * 
	             * StandardKETEcmService > 1603 라인 아래 소스를 afterVersion = beans.reviseBom(part);
	             * 
	             * => 아래로 변경 바랍니다. => 참고로 리비전시에 부품 상태 INWORK으로 변경됩니다.
	             */
		    // afterVersion = beans.reviseBom(part);

		    String changeType = null;
		    afterVersion = PartBaseHelper.service.reviseWTPartNGetVersion(part, changeType);

		    ecoBomHeader.setBOMVersion(afterVersion);
		    ecoBomHeader.setAttribute1("Y");
		    PersistenceHelper.manager.save(ecoBomHeader);

		    /*
	             * bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);
	             */
		    QueryResult qr = PersistenceHelper.manager.navigate(ecoBomHeader, "moldECA", KETMoldECABomLink.class, false);
		    while (qr.hasMoreElements()) {
			bomLink = (KETMoldECABomLink) qr.nextElement();
		    }

		    bomLink.setAfterVersion(afterVersion);
		    PersistenceHelper.manager.save(bomLink);

		    /*
	             * 김윤배의 글... ext.ket.bom.query.KETBOMQueryBean
	             * 
	             * //개정시 호출 public void reviseInsertComponentData(String ecoNumber, String oldPartOid, String newVersion)
	             * 
	             * //개정취소시 호출 public void reviseCancelComponentData(String ecoNumber, String oldPartOid)
	             */
		    String ecoNumber = ketMoldChangeOrder.getEcoId();
		    String oldPartOid = MOLD_ECO_CHANGE_SERVLET.beforePartOid[i];
		    String newVersion = afterVersion;

		    // KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
		    ketBOMQueryBean.reviseInsertComponentData(ecoNumber, oldPartOid, newVersion, wtConn);

		    afterRev = afterVersion;
		}

		if (StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomReviseCancel[i]).equals("Y")) {

		    WTPart part = null;
		    String afterVersion = "";
		    KETMoldECABomLink bomLink = null;

		    part = beans.getLastestPart(MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i], conn);

		    /*
	             * 1)부품 개정취소 시점에 => 연계 도면이 먼저 개정취소되지 않으면 Validation 걸어서 Message 전달 파라미터1 : WTPart : 개정 후의 리비전 파라미터2 : ecoOid return :
	             * String : null 이거나 empty가 아니면 message 뿌려주세요. 호출 api : PartBaseHelper.service.validRelatedEpmCancelRevised(WTPart
	             * wtPart, String ecoOid);
	             */
		    String ecoOid = CommonUtil.getOIDString(ketMoldChangeOrder);
		    String ERROR_MESSAGE = PartBaseHelper.service.validRelatedEpmCancelRevised(part, ecoOid);
		    if (ERROR_MESSAGE != null && !ERROR_MESSAGE.equals("")) {
			throw new WTException(ERROR_MESSAGE);
		    }

		    /*
	             * isSuccess = KETEcmHelper.service.cancelReviseBom(MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]);
	             */

		    // Set temporary Session - Administrator
		    WTPrincipal session = SessionHelper.manager.getPrincipal();
		    SessionHelper.manager.setAdministrator();
		    PersistenceHelper.manager.delete(part);
		    SessionHelper.manager.setPrincipal(session.getName());

		    part = beans.getLastestPart(MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i], conn);
		    String preVersion = VersionUtil.getMajorVersion(part);

		    KETBomEcoHeader ecoBomHeader = EcmSearchHelper.manager.getProdEcoBomHeader(ketMoldChangeOrder.getEcoId(),
			    MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]);
		    ecoBomHeader.setBOMVersion(preVersion);
		    ecoBomHeader.setAttribute1("N");
		    ecoBomHeader.setBoxQuantity(ecoBomHeader.getAttribute2());
		    PersistenceHelper.manager.save(ecoBomHeader);

		    /*
	             * bomLink = EcmSearchHelper.manager.getProdEcaBomLink(ecoBomHeader);
	             */
		    QueryResult qr = PersistenceHelper.manager.navigate(ecoBomHeader, "moldECA", KETMoldECABomLink.class, false);
		    while (qr.hasMoreElements()) {
			bomLink = (KETMoldECABomLink) qr.nextElement();
		    }

		    bomLink.setAfterVersion("");
		    PersistenceHelper.manager.save(bomLink);

		    // Delete BomSubComponent
		    boolean isDeleteComp = comDao.deleteBomComponent(ketMoldChangeOrder.getEcoId(), MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]);
		    boolean isDeleteTempComp = comDao.deleteBomTempComponent(ketMoldChangeOrder.getEcoId(),
			    MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]);
		    boolean isUpdateCoWorker = comDao.updateBomCoWorker(ketMoldChangeOrder.getEcoId(),
			    MOLD_ECO_CHANGE_SERVLET.relEcaBomNo[i]);

		    /*
	             * }
	             * 
	             * 
	             * }
	             */

		    /*
	             * 김윤배의 글... ext.ket.bom.query.KETBOMQueryBean
	             * 
	             * //개정시 호출 public void reviseInsertComponentData(String ecoNumber, String oldPartOid, String newVersion)
	             * 
	             * //개정취소시 호출 public void reviseCancelComponentData(String ecoNumber, String oldPartOid)
	             */
		    String ecoNumber = ketMoldChangeOrder.getEcoId();
		    // String oldPartOid = MOLD_ECO_CHANGE_SERVLET.beforePartOid[i];
		    Map oldPartInfoMap = beforePartArray.get(i);

		    // KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();
		    ketBOMQueryBean.reviseCancelComponentData(ecoNumber, oldPartInfoMap, wtConn);

		    /*
	             * if (isSuccess) { afterRev = ""; }
	             */
		    afterRev = "";

		}

		ketMoldECALinkVO.setRelEcaObjectAfterRev(afterRev);// BOM이후버전
		Kogger.debug(getClass(), "TEST9");
		ketMoldECALinkVO.setWorkerId(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomWorkerOid[i]));// BOM담당자oid
		Kogger.debug(getClass(), "TEST10");
		ketMoldECALinkVO.setRelEcaEpmChangeYn(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomChangeYn[i]));// BOM변경여부
		Kogger.debug(getClass(), "TEST11");
		ketMoldECALinkVO.setRelEcaObjectPlanDate(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomPlanDate[i]));// BOM변경예정일
		Kogger.debug(getClass(), "TEST12");
		ketMoldECALinkVO.setRelEcaObjectActivityComment(StringUtil.checkNull(MOLD_ECO_CHANGE_SERVLET.relEcaBomActivityComment[i]));// BOM변경내용
		Kogger.debug(getClass(), "TEST13");
		ketMoldECALinkVOList.add(ketMoldECALinkVO);

	    }
	} catch (Exception e1) {
	    Kogger.error(getClass(), e1);
	    throw new Exception(e1);
	}
	return ketMoldECALinkVOList;
    }// end-of-createKetMoldECALinkVOForBom

    @Override
    public String validateBeforeCompleteReg(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";
	String budgetYn = paramMap.getString("budgetYn");
	ErpPartHandler erpPartHandler = new ErpPartHandler();

	try {
	    boolean hasECA = false;

	    WTChangeOrder2 wtChangeOrder2 = (WTChangeOrder2) CommonUtil.getObject(ecoOid);

	    if (!wtChangeOrder2.getLifeCycleState().equals(State.toState("APPROVED"))) {
		ALERT_MESSAGE = this.validateEcoPartRelatedEpmDoc(wtChangeOrder2);
		if (ALERT_MESSAGE != null && !ALERT_MESSAGE.equals("")) {
		    return ALERT_MESSAGE;
		}
	    } else {
		hasECA = true;
	    }

	    int cuDrawingCnt = 0;

	    // ECA 체크
	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) wtChangeOrder2;

		if (!eco.getLifeCycleState().equals(State.toState("APPROVED"))) {
		    // rtnHash = EcmSearchHelper.manager.getCanRequestApproveFlag(eco);

		    // 초도인지 아닌지
		    String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
		    boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);

		    // 계발단계이관
		    boolean isMass = (changeReason.lastIndexOf("REASON_10") > -1);
		    // cu도면변경여부
		    String cuYn = StringUtils.stripToEmpty(eco.getCuDrawingChangeYn());

		    // 비용확보 체크
		    // 초도가 아닐 경우에만
		    if (!isTheFirst) {
			boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget(ecoOid);
			if (!budgetFlag && !"Y".equals(budgetYn)) {// 화면단에서 비용확보가 안되었으나 진행하겠다는 플래그를 넘겨받고 진행, 01651를 key로 사용한다
			    // 비용이 확보되지 않았습니다.
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "01651") + "01651" + "\\n";
			}

		    }

		    boolean is3D = false;
		    boolean is2D = false;
		    QueryResult qr = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class);
		    while (qr.hasMoreElements()) {
			KETProdChangeActivity eca = (KETProdChangeActivity) qr.nextElement();
			String activityType = StringUtils.stripToEmpty(eca.getActivityType());
			if (activityType.equals("1") || activityType.equals("2")) {

			    hasECA = true;

			}

			QueryResult queryDocResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETProdECAEpmDocLink.class, false);
			if (queryDocResult.size() > 0) {
			    while (queryDocResult.hasMoreElements()) {

				KETProdECAEpmDocLink ketProdECAEpmDocLink = (KETProdECAEpmDocLink) queryDocResult.nextElement();

				/*
		                 * System.out.println(ketProdECAEpmDocLink.getRoleBObject().getPersistInfo().getObjectIdentifier()
		                 * .getStringValue()); System.out.println(ketProdECAEpmDocLink.getRoleBObjectId());
		                 * System.out.println(ketProdECAEpmDocLink.getEpmDoc().getCADName());
		                 * System.out.println(ketProdECAEpmDocLink.getEpmDoc().getDocType());
		                 * System.out.println(ketProdECAEpmDocLink.getEpmDoc().getNumber());
		                 */
				EPMDocument epm = ketProdECAEpmDocLink.getEpmDoc();

				HashMap ibaValues = EDMAttributes.getIBAValues(epm, Locale.KOREAN);

				String category = "";
				if (ibaValues.get(EDMHelper.IBA_CAD_CATEGORY) != null) {
				    category = EDMEnumeratedTypeUtil.getCADCategory((String) ibaValues.get(EDMHelper.IBA_CAD_CATEGORY),
					    Locale.KOREAN);
				}

				String cadName = epm.getCADName();

				/*
		                 * if (is2D == false && "CUSTOMER_DRAWING".equals(category)) { is2D = cadName.toUpperCase().endsWith(".DWG")
		                 * || cadName.toUpperCase().endsWith(".ASM"); } if (is3D == false && "CUSTOMER_3D_DRAWING".equals(category))
		                 * { is3D = cadName.toUpperCase().endsWith(".PRT"); } if (is3D == false &&
		                 * "CUSTOMER_DRAWING".equals(category)) { is3D = cadName.toUpperCase().endsWith(".PRT"); }
		                 */
				// 기존 로직 잘못됨.. 수정함. 2016.05.16 by 황정태
				if (is2D == false && "CUSTOMER_DRAWING".equals(category)) {
				    is2D = "2D".equals(ketProdECAEpmDocLink.getEpmDocType().toUpperCase());
				}
				if (is3D == false && "CUSTOMER_DRAWING".equals(category)) {
				    is3D = "3D".equals(ketProdECAEpmDocLink.getEpmDocType().toUpperCase());
				}

				if ("Y".equals(cuYn) && "CUSTOMER_DRAWING".equals(category)) {
				    cuDrawingCnt++;
				}

			    }
			}
		    }

		    if ("Y".equals(cuYn) && cuDrawingCnt < 1) {
			ALERT_MESSAGE += "CU도면이 등록되지 않았습니다.\\n";
		    }

		    qr = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class);
		    while (qr.hasMoreElements()) {
			KETProdChangeActivity eca = (KETProdChangeActivity) qr.nextElement();
			// BOM
			QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
			if (queryResult.size() > 0) {
			    while (queryResult.hasMoreElements()) {

				KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult.nextElement();

				// 초도일 경우
				if (isTheFirst) {
				    String beforePartOid = StringUtils.stripToEmpty(ketProdECABomLink.getBeforePartOid());
				    WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid);
				    String partNo = beforePart.getNumber();
				    if (erpPartHandler.existErpPart(partNo)) {
					// <entry key="04009">부품[{0}]은 ERP에 존재합니다.</entry>
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04009", partNo) + "\\n";
				    }
				}

				// part

				// ECO 의 부품정보에 속성중 HOMEPAGE, 3D, 2D 값이 YES 이면
				// 부품의 도면 정보를 찾아 그의 산출물이 3D,2D 가 있으며
				// 혹은 부품의 IMAGE 가 있으면 확인하여

				// 1. HOMEPAGE YES 이면
				// 2. 2D 가 YES 이면
				// 3. 도면의 2D 산출물을 찾는다. 존재하면 TRUE
				// 4. 3D 가 YES 이면
				// 5. 도면의 2D 산출물을 찾는다. 존재하면 TRUE 이고 FALSE 이면 부품의 IMAGE 산출물을 확인하여 존재하면 YES 처리한다.
				// 6. 부품이 양산단계(PC004) 이면 3D, 2D 필수 홈페이지 등록 여부와 상관없이 필수처리
				// 7/ ECO 의 변경사유에 양산이관이면 무조건 필수

				String partOid = StringUtils.stripToEmpty(ketProdECABomLink.getBeforePartOid());
				WTPart part = (WTPart) CommonUtil.getObject(partOid);

				String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

				String divisionGubun = part.getNumber().substring(0, 2);

				String partNo = part.getNumber();
				String hpType = PartSpecGetter.getPartSpec(part, PartSpecEnum.HomepageIF);
				String threedType = PartSpecGetter.getPartSpec(part, PartSpecEnum.Hompage3DIF);
				String twodType = PartSpecGetter.getPartSpec(part, PartSpecEnum.Homepage2DIF);
				String imgType = PartSpecGetter.getPartSpec(part, PartSpecEnum.HompageImgIF);
				String devType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartDevLevel);

				if ("PC004".equals(devType) && "Y".equals(cuYn) && threedType.equals("YES")) {
				    if (is3D == false)
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04143", partNo) + "\\n";
				}
				if ("PC004".equals(devType) && "Y".equals(cuYn) && twodType.equals("YES")) {
				    if (is2D == false)
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04144", partNo) + "\\n";
				}
				if (isMass && threedType.equals("YES")) {
				    if (is3D == false)
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04143", partNo) + "\\n";
				}
				if (isMass && twodType.equals("YES")) {
				    if (is2D == false)
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04144", partNo) + "\\n";
				}

			    }
			}

		    }
		}

	    } else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) wtChangeOrder2;

		if (!eco.getLifeCycleState().equals(State.toState("APPROVED"))) {
		    // rtnHash = EcmSearchHelper.manager.getCanReqApproveFlagForMoldEco(eco);

		    // 초도인지 아닌지
		    String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
		    boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

		    // 비용확보 체크
		    // 초도가 아닐 경우에만
		    if (!isTheFirst) {
			boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget(ecoOid);
			if (!budgetFlag) {
			    // 비용이 확보되지 않았습니다.
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "01651") + "\\n";

			}

		    }

		    QueryResult qr = PersistenceHelper.manager.navigate(eco, "moldECA", KETMoldChangeActivityLink.class);
		    while (qr.hasMoreElements()) {
			KETMoldChangeActivity eca = (KETMoldChangeActivity) qr.nextElement();
			String activityType = StringUtils.stripToEmpty(eca.getActivityType());
			if (activityType.equals("1") || activityType.equals("2")) {

			    hasECA = true;

			}
			QueryResult queryResult  = null;
			// BOM
//			QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
//			if (queryResult.hasMoreElements()) {
//			    KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) queryResult.nextElement();
//
//			    // 초도일 경우
//			    if (isTheFirst) {
//				String beforePartOid = StringUtils.stripToEmpty(ketMoldECABomLink.getBeforePartOid());
//				WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid);
//				String partNo = beforePart.getNumber();
//				if (erpPartHandler.existErpPart(partNo)) {
//				    // <entry key="04009">부품[{0}]은 ERP에 존재합니다.</entry>
//				    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04009", partNo) + "\\n";
//
//				}
//
//			    } else {
//				// Do nothing..!!
//
//			    }
//
//			}

			// 도면
//			queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETMoldECAEpmDocLink.class, false);
//			if (queryResult.hasMoreElements()) {
//			    KETMoldECAEpmDocLink ketMoldECAEpmDocLink = (KETMoldECAEpmDocLink) queryResult.nextElement();
//
//			    // 초도일 경우 제외한다.
//			    if (isTheFirst) {
//				// Do nothing..!!
//
//			    } else {
//				// Do nothing..!!
//
//			    }
//
//			}
			// if( activityType.equals("1") )
			// {
			// beans.updateMoldPlan( ecoOid );
			// }

			// 표준품리스트일 경우
			if (activityType.equals("3")) {

			    queryResult = PersistenceHelper.manager.navigate(eca, "doc", KETMoldECADocLink.class, false);
			    if (queryResult.hasMoreElements()) {
				KETMoldECADocLink ketMoldECADocLink = (KETMoldECADocLink) queryResult.nextElement();

				// 문서 유무
				KETProjectDocument ketProjectDocument = ketMoldECADocLink.getDoc();
				if (ketProjectDocument == null) {
				    // <entry key="04010">표준품에 개정할 문서를 추가하여 주십시오.</entry>
				    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04010") + "\\n";

				}

			    } else {
				// <entry key="04010">표준품에 개정할 문서를 추가하여 주십시오.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04010") + "\\n";

			    }

			    String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			    if (workerId.equals("")) {
				// <entry key="04011">표준품에 담당자를 추가하여 주십시오.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04011") + "\\n";

			    }

			}
			// 활동(4M변경)일 경우
			else if (activityType.equals("4")) {
			    String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			    if (workerId.equals("")) {
				// <entry key="04007">ECN의 후속업무[{0}]에 담당자를 추가하여 주십시오.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04007", eca.getCustomName()) + "\\n";

			    }

			    Timestamp completeRequestDate = eca.getCompleteRequestDate();
			    if (completeRequestDate == null) {
				// <entry key="04008">ECN의 후속업무[{0}]에 완료요청일을 추가하여 주십시오.</entry>

				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04008", eca.getCustomName()) + "\\n";

			    }

			}

		    }
		}

	    }

	    if (!hasECA) {
		// <entry key="00198">ECO를 수정하여 도면 또는 BOM 대상을 추가하십시오.</entry>
		ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "00198") + "\\n";

	    }

	    /*
	     * if (!ALERT_MESSAGE.equals("")) { //그래도 작업을 계속 진행하시겠습니까? ALERT_MESSAGE += "\\n" +
	     * messageService.getString("e3ps.message.ket_message", "04260");
	     * 
	     * }
	     */

	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String validateBeforeActivityReg(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	ErpPartHandler erpPartHandler = new ErpPartHandler();
	try {

	    WTChangeOrder2 wtChangeOrder2 = (WTChangeOrder2) CommonUtil.getObject(ecoOid);

	    ALERT_MESSAGE = this.validateEcoPartRelatedEpmDoc(wtChangeOrder2);
	    if (ALERT_MESSAGE != null && !ALERT_MESSAGE.equals(""))
		return ALERT_MESSAGE;

	    boolean hasECA = false;

	    // ECA 체크
	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) wtChangeOrder2;
		// rtnHash = EcmSearchHelper.manager.getCanRequestApproveFlag(eco);

		// 초도인지 아닌지
		String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
		boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);

		// 비용확보 체크
		// 초도가 아닐 경우에만
		if (!isTheFirst) {
		    boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget(ecoOid);
		    if (!budgetFlag) {
			// 비용이 확보되지 않았습니다.
			ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "01651") + "\\n";

		    }

		}

		QueryResult qr = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class);
		while (qr.hasMoreElements()) {
		    KETProdChangeActivity eca = (KETProdChangeActivity) qr.nextElement();
		    String activityType = StringUtils.stripToEmpty(eca.getActivityType());
		    if (activityType.equals("1") || activityType.equals("2")) {

			hasECA = true;

		    }

		    // BOM
		    QueryResult queryResult = null;
		    queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
		    /*
	             * if (queryResult.hasMoreElements()) { KETProdECABomLink ketProdECABomLink = (KETProdECABomLink)
	             * queryResult.nextElement();
	             * 
	             * // 초도일 경우 if (isTheFirst) { String beforePartOid = StringUtils.stripToEmpty(ketProdECABomLink.getBeforePartOid());
	             * WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid); String partNo = beforePart.getNumber(); if
	             * (erpPartHandler.existErpPart(partNo)) { // <entry key="04009">부품[{0}]은 ERP에 존재합니다.</entry> ALERT_MESSAGE +=
	             * messageService.getString("e3ps.message.ket_message", "04009", partNo) + "\\n";
	             * 
	             * }
	             * 
	             * } else { // Do nothing..!!
	             * 
	             * }
	             * 
	             * }
	             */

		    // 도면
		    /*
	             * queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETProdECAEpmDocLink.class, false); if
	             * (queryResult.hasMoreElements()) { KETProdECAEpmDocLink ketProdECAEpmDocLink = (KETProdECAEpmDocLink)
	             * queryResult.nextElement();
	             * 
	             * // 초도일 경우 제외한다. if (isTheFirst) { // Do nothing..!!
	             * 
	             * } else { // Do nothing..!!
	             * 
	             * }
	             * 
	             * }
	             */
		    // if( activityType.equals("1") )
		    // {
		    // beans.updateMoldPlan( ecoOid );
		    // }

		    // ECN일 경우
		    // 문서일 경우
		    if (activityType.equals("3")) {

			queryResult = PersistenceHelper.manager.navigate(eca, "doc", KETProdECADocLink.class, false);
			if (queryResult.hasMoreElements()) {
			    KETProdECADocLink ketProdECADocLink = (KETProdECADocLink) queryResult.nextElement();

			    // 문서 유무
			    KETProjectDocument ketProjectDocument = ketProdECADocLink.getDoc();
			    if (ketProjectDocument == null) {
				// <entry key="04006">ECN의 후속업무[{0}] 타입이 문서일 경우 개정할 문서를 추가하여 주십시오.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04006", eca.getCustomName()) + "\\n";

			    }

			} else {
			    // <entry key="04006">ECN의 후속업무[{0}] 타입이 문서일 경우 개정할 문서를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04006", eca.getCustomName()) + "\\n";

			}

			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			if (workerId.equals("")) {
			    // <entry key="04007">ECN의 후속업무[{0}]에 담당자를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04007", eca.getCustomName()) + "\\n";

			}

			Timestamp completeRequestDate = eca.getCompleteRequestDate();
			if (completeRequestDate == null) {
			    // <entry key="04008">ECN의 후속업무[{0}]에 완료요청일을 추가하여 주십시오.</entry>

			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04008", eca.getCustomName()) + "\\n";

			}

		    }
		    // 활동일 경우
		    else if (activityType.equals("4")) {
			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			if (workerId.equals("")) {
			    // <entry key="04007">ECN의 후속업무[{0}]에 담당자를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04007", eca.getCustomName()) + "\\n";

			}

			Timestamp completeRequestDate = eca.getCompleteRequestDate();
			if (completeRequestDate == null) {
			    // <entry key="04008">ECN의 후속업무[{0}]에 완료요청일을 추가하여 주십시오.</entry>

			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04008", eca.getCustomName()) + "\\n";

			}

		    }

		}

	    } else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) wtChangeOrder2;
		// rtnHash = EcmSearchHelper.manager.getCanReqApproveFlagForMoldEco(eco);

		// 초도인지 아닌지
		String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
		boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

		// 비용확보 체크
		// 초도가 아닐 경우에만
		if (!isTheFirst) {
		    boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget(ecoOid);
		    if (!budgetFlag) {
			// 비용이 확보되지 않았습니다.
			ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "01651") + "\\n";

		    }

		}

		QueryResult qr = PersistenceHelper.manager.navigate(eco, "moldECA", KETMoldChangeActivityLink.class);
		while (qr.hasMoreElements()) {
		    KETMoldChangeActivity eca = (KETMoldChangeActivity) qr.nextElement();
		    String activityType = StringUtils.stripToEmpty(eca.getActivityType());
		    if (activityType.equals("1") || activityType.equals("2")) {

			hasECA = true;

		    }

		    // BOM
		    QueryResult queryResult = null;
		    /*
	             * queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false); if
	             * (queryResult.hasMoreElements()) { KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink)
	             * queryResult.nextElement();
	             * 
	             * // 초도일 경우 if (isTheFirst) { String beforePartOid = StringUtils.stripToEmpty(ketMoldECABomLink.getBeforePartOid());
	             * WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid); String partNo = beforePart.getNumber(); if
	             * (erpPartHandler.existErpPart(partNo)) { // <entry key="04009">부품[{0}]은 ERP에 존재합니다.</entry> ALERT_MESSAGE +=
	             * messageService.getString("e3ps.message.ket_message", "04009", partNo) + "\\n";
	             * 
	             * }
	             * 
	             * } else { // Do nothing..!!
	             * 
	             * }
	             * 
	             * }
	             * 
	             * // 도면 queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETMoldECAEpmDocLink.class, false); if
	             * (queryResult.hasMoreElements()) { KETMoldECAEpmDocLink ketMoldECAEpmDocLink = (KETMoldECAEpmDocLink)
	             * queryResult.nextElement();
	             * 
	             * // 초도일 경우 제외한다. if (isTheFirst) { // Do nothing..!!
	             * 
	             * } else { // Do nothing..!!
	             * 
	             * }
	             * 
	             * }
	             */
		    // if( activityType.equals("1") )
		    // {
		    // beans.updateMoldPlan( ecoOid );
		    // }

		    // 표준품리스트일 경우
		    if (activityType.equals("3")) {

			queryResult = PersistenceHelper.manager.navigate(eca, "doc", KETMoldECADocLink.class, false);
			if (queryResult.hasMoreElements()) {
			    KETMoldECADocLink ketMoldECADocLink = (KETMoldECADocLink) queryResult.nextElement();

			    // 문서 유무
			    KETProjectDocument ketProjectDocument = ketMoldECADocLink.getDoc();
			    if (ketProjectDocument == null) {
				// <entry key="04010">표준품에 개정할 문서를 추가하여 주십시오.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04010") + "\\n";

			    }

			} else {
			    // <entry key="04010">표준품에 개정할 문서를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04010") + "\\n";

			}

			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			if (workerId.equals("")) {
			    // <entry key="04011">표준품에 담당자를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04011") + "\\n";

			}

		    }
		    // 활동(4M변경)일 경우
		    else if (activityType.equals("4")) {
			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			if (workerId.equals("")) {
			    // <entry key="04007">ECN의 후속업무[{0}]에 담당자를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04007", eca.getCustomName()) + "\\n";

			}

			Timestamp completeRequestDate = eca.getCompleteRequestDate();
			if (completeRequestDate == null) {
			    // <entry key="04008">ECN의 후속업무[{0}]에 완료요청일을 추가하여 주십시오.</entry>

			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04008", eca.getCustomName()) + "\\n";

			}

		    }

		}

	    }

	    if (!hasECA) {
		// <entry key="00198">ECO를 수정하여 도면 또는 BOM 대상을 추가하십시오.</entry>
		ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "00198") + "\\n";

	    }

	    /*
	     * if (!ALERT_MESSAGE.equals("")) { //그래도 작업을 계속 진행하시겠습니까? ALERT_MESSAGE += "\\n" +
	     * messageService.getString("e3ps.message.ket_message", "04260");
	     * 
	     * }
	     */

	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String validateBeforeRequestApprove(String ecoOid, KETParamMapUtil paramMap) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";
	ErpPartHandler erpPartHandler = new ErpPartHandler();
	KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

	try {

	    WTChangeOrder2 wtChangeOrder2 = (WTChangeOrder2) CommonUtil.getObject(ecoOid);

	    // ECA 체크
	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) wtChangeOrder2;
		// rtnHash = EcmSearchHelper.manager.getCanRequestApproveFlag(eco);

		// 초도인지 아닌지
		String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());

		// 계발단계이관
		String isMass = "N";
		if ((changeReason.lastIndexOf("REASON_10") > -1)) {
		    isMass = "Y";
		}

		HashMap<String, String> EcoValidParam = new HashMap<String, String>();
		EcoValidParam.put("isMass", isMass);

		// ECR 연계여부 체크 (설변 사유가 고객요청,생산성 향상,품질문제,원가절감 중 하나일때)
		String reason[] = changeReason.split("\\|");
		boolean isEcrCheck = false;

		for (int i = 0; i < reason.length; i++) {
		    if ("REASON_1".equals(reason[i])) {
			isEcrCheck = true;
		    } else if ("REASON_2".equals(reason[i])) {
			isEcrCheck = true;
		    } else if ("REASON_3".equals(reason[i])) {
			isEcrCheck = true;
		    } else if ("REASON_11".equals(reason[i])) {
			isEcrCheck = true;
		    }
		}

		Connection conn = null;
		if (isEcrCheck) {
		    try {

			conn = PlmDBUtil.getConnection();
			ProdEcoDao dao = new ProdEcoDao(conn);
			ArrayList<Hashtable<String, String>> ecrList = new ArrayList<Hashtable<String, String>>();
			String oid = CommonUtil.getOIDLongValue2Str(ecoOid);
			ecrList = dao.getRefEcrList(oid);

			if (ecrList.size() < 1) {
			    ALERT_MESSAGE += "설계변경사유에 고객요청,생산성 향상,품질문제,원가절감이 포함되는 경우\\n ECR연계는 필수입니다." + "\\n\\n";
			}
		    } catch (Exception e) {
			throw new WTException(e);
		    } finally {
			PlmDBUtil.close(conn);
		    }

		}

		boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);

		/*
	         * // 비용확보 체크 // 초도가 아닐 경우에만 if (!isTheFirst) { boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget(ecoOid); if
	         * (!budgetFlag) { // 비용이 확보되지 않았습니다. ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "01651") +
	         * "\\n";
	         * 
	         * }
	         * 
	         * }
	         */

		QueryResult qr = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class);
		while (qr.hasMoreElements()) {
		    KETProdChangeActivity eca = (KETProdChangeActivity) qr.nextElement();

		    // 수신확인 Validation 처리
		    /*
	             * yyyy-MM-dd 형태로 리턴, 수신일자 등록은 작업공간 > My Todo 화면에서 수행합니다.
	             */
		    /*String receiptDate = KETWorkspaceHelper.service.getReceiptDate((LifeCycleManaged) eca);
		    if (receiptDate == null || receiptDate.equals("")) {

			// <entry key="04370">My Todo 리스트에서 수신확인을 하시기 바랍니다.</entry>
			
		         * throw new WTException(messageService.getString("e3ps.message.ket_message", "04370"));
		         
		    }*/

		    // BOM
		    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult.nextElement();
			KETBomEcoHeader ketBomEcoHeader = ketProdECABomLink.getBom();
			String beforePartOid = StringUtils.stripToEmpty(ketProdECABomLink.getBeforePartOid());
			WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid);
			String partNo = beforePart.getNumber();

			// 초도일 경우
			if (isTheFirst) {
			    if (erpPartHandler.existErpPart(partNo)) {
				// <entry key="04009">부품[{0}]은 ERP에 존재합니다.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04009", partNo) + "\\n";

			    }

			    /*
		             * // 작업완료유무 Validation 처리 String attribute3 = ketBomEcoHeader.getAttribute3(); if (attribute3 == null ||
		             * !attribute3.equalsIgnoreCase("Y")) { // <entry key="04015">부품[{0}]을 작업완료하시기 바랍니다.</entry> ALERT_MESSAGE +=
		             * messageService.getString("e3ps.message.ket_message", "04015", partNo) + "\\n";
		             * 
		             * }
		             */

			} else {
			    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
			    String partRevision = "";
			    partRevision = (beforePart != null) ? beforePart.getVersionIdentifier().getValue() : "";
			    if (beforePart.getLifeCycleState().equals(State.toState("INWORK"))
				    && (partRevision.equals("") || partRevision.equals("0"))) {
				// 통과
			    } else {
				String bomflag = PartSpecGetter.getPartSpec(beforePart, PartSpecEnum.SpBOMFlag);
				if (bomflag != null && bomflag.equalsIgnoreCase("OLD")) {

				    // 개정유무 Validation 처리
				    String afterVersion = ketProdECABomLink.getAfterVersion();
				    if (afterVersion == null || afterVersion.equals("")) {
					// <entry key="04390">개정하지 않은 부품이 있습니다.</entry>
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04390") + "\\n";

				    }

				}
			    }

			}

			// EO 결재상신 Validation - 체크 속성 누락 여부 체크
			List<WTPart> targetPartList = new ArrayList<WTPart>();
			String afterVersion = ketProdECABomLink.getAfterVersion();
			if (afterVersion == null || afterVersion.equals("")) {
			    afterVersion = ketProdECABomLink.getPreVersion();
			}
			String afterPartOid = ketBOMQueryBean.getPartOid(partNo, afterVersion);
			WTPart afterPart = (WTPart) CommonUtil.getObject(afterPartOid);
			targetPartList.add(afterPart);
			List<PartValidationDTO> resultPartList = PartBaseHelper.service.validCheckedProps(targetPartList, EcoValidParam);

			int resultPartListSize = (resultPartList != null) ? resultPartList.size() : 0;
			for (int i = 0; i < resultPartListSize; i++) {
			    PartValidationDTO dto = resultPartList.get(i);

			    String aLinkPartNo = "<a href=\"javascript:openViewPart('" + dto.getValidPartNumber() + "');\">"
				    + dto.getValidPartNumber() + "</a>";
			    // <entry key="04017">부품[{0}]은 아래 속성들이 입력누락되었습니다.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04017",
				    StringEscapeUtils.escapeJavaScript(aLinkPartNo))
				    + "\\n";
			    ALERT_MESSAGE += "&nbsp;&nbsp;" + StringEscapeUtils.escapeJavaScript(dto.getValidContent()) + "\\n\\n";

			}

		    }

		    // 도면
		    queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETProdECAEpmDocLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETProdECAEpmDocLink ketProdECAEpmDocLink = (KETProdECAEpmDocLink) queryResult.nextElement();
			EPMDocument epmDoc = null;
			// 초도일 경우 제외한다.
			if (isTheFirst || (changeReason.lastIndexOf("REASON_13") > -1)) {
			    // Do nothing..!!

			} else {

			    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
			    String epmDocRevision = "";
			    epmDoc = ketProdECAEpmDocLink.getEpmDoc();
			    epmDocRevision = (epmDoc != null) ? epmDoc.getVersionIdentifier().getValue() : "";
			    if (epmDoc.getLifeCycleState().equals(State.toState("INWORK"))
				    && (epmDocRevision.equals("") || epmDocRevision.equals("0"))) {
				// 통과
			    } else {

				// 개정유무 Validation 처리
				String afterVersion = ketProdECAEpmDocLink.getAfterVersion();
				if (afterVersion == null || afterVersion.equals("")) {
				    // <entry key="04380">개정되지 않은 도면이 있습니다.</entry>
				    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04380") + "\\n";

				}

			    }

			}
			// 체크인 유무 Validation 처리
			String IScheckIn = EpmBaseHelper.service.validCheckoutInfoEpm(epmDoc);
			if (!"".equals(IScheckIn)) {
			    ALERT_MESSAGE += EpmBaseHelper.service.validCheckoutInfoEpm(epmDoc) + "\\n";
			}

		    }
		    // if( activityType.equals("1") )
		    // {
		    // beans.updateMoldPlan( ecoOid );
		    // }

		    // ECN일 경우
		    String activityType = StringUtils.stripToEmpty(eca.getActivityType());
		    // 문서일 경우
		    if (activityType.equals("3")) {

			/*
		         * queryResult = PersistenceHelper.manager.navigate(eca, "doc", KETProdECADocLink.class, false); if
		         * (queryResult.hasMoreElements()) { KETProdECADocLink ketProdECADocLink = (KETProdECADocLink)
		         * queryResult.nextElement();
		         * 
		         * // 문서 유무 KETProjectDocument ketProjectDocument = ketProdECADocLink.getDoc(); if (ketProjectDocument == null) { //
		         * <entry key="04006">ECN의 후속업무[{0}] 타입이 문서일 경우 개정할 문서를 추가하여 주십시오.</entry> ALERT_MESSAGE +=
		         * messageService.getString("e3ps.message.ket_message", "04006", eca.getCustomName()) + "\\n";
		         * 
		         * }
		         * 
		         * } else { // <entry key="04006">ECN의 후속업무[{0}] 타입이 문서일 경우 개정할 문서를 추가하여 주십시오.</entry> ALERT_MESSAGE +=
		         * messageService.getString("e3ps.message.ket_message", "04006", eca.getCustomName()) + "\\n";
		         * 
		         * }
		         */

			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			if (workerId.equals("")) {
			    // <entry key="04007">ECN의 후속업무[{0}]에 담당자를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04007", eca.getCustomName()) + "\\n";

			}

			Timestamp completeRequestDate = eca.getCompleteRequestDate();
			if (completeRequestDate == null) {
			    // <entry key="04008">ECN의 후속업무[{0}]에 완료요청일을 추가하여 주십시오.</entry>

			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04008", eca.getCustomName()) + "\\n";

			}

		    }
		    // 활동일 경우
		    else if (activityType.equals("4")) {
			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			if (workerId.equals("")) {
			    // <entry key="04007">ECN의 후속업무[{0}]에 담당자를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04007", eca.getCustomName()) + "\\n";

			}

			Timestamp completeRequestDate = eca.getCompleteRequestDate();
			if (completeRequestDate == null) {
			    // <entry key="04008">ECN의 후속업무[{0}]에 완료요청일을 추가하여 주십시오.</entry>

			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04008", eca.getCustomName()) + "\\n";

			}

		    }

		}

	    } else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) wtChangeOrder2;
		// rtnHash = EcmSearchHelper.manager.getCanReqApproveFlagForMoldEco(eco);

		// 초도인지 아닌지
		String changeReason = StringUtils.stripToEmpty(eco.getChangeReason());
		boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

		// 계발단계이관 (금형 eco는 의미가없다. 형식을 맞춰주기 위한용도임..)
		String isMass = "N";
		if ((changeReason.lastIndexOf("REASON_10") > -1)) {
		    isMass = "Y";
		}

		HashMap<String, String> EcoValidParam = new HashMap<String, String>();
		EcoValidParam.put("isMass", isMass);

		/*
	         * // 비용확보 체크 // 초도가 아닐 경우에만 if (!isTheFirst) { boolean budgetFlag = EcmSearchHelper.manager.isSecureBudget(ecoOid); if
	         * (!budgetFlag) { // 비용이 확보되지 않았습니다. ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "01651") +
	         * "\\n";
	         * 
	         * }
	         * 
	         * }
	         */

		QueryResult qr = PersistenceHelper.manager.navigate(eco, "moldECA", KETMoldChangeActivityLink.class);
		while (qr.hasMoreElements()) {
		    KETMoldChangeActivity eca = (KETMoldChangeActivity) qr.nextElement();

		    // 수신확인 Validation 처리
		    /*
	             * yyyy-MM-dd 형태로 리턴, 수신일자 등록은 작업공간 > My Todo 화면에서 수행합니다.
	             */
		    String receiptDate = KETWorkspaceHelper.service.getReceiptDate((LifeCycleManaged) eca);
		    if (receiptDate == null || receiptDate.equals("")) {

			// <entry key="04370">My Todo 리스트에서 수신확인을 하시기 바랍니다.</entry>
			/*
		         * throw new WTException(messageService.getString("e3ps.message.ket_message", "04370"));
		         */
		    }

		    // BOM
		    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) queryResult.nextElement();
			KETBomEcoHeader ketBomEcoHeader = ketMoldECABomLink.getBom();
			String beforePartOid = StringUtils.stripToEmpty(ketMoldECABomLink.getBeforePartOid());
			WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid);
			String partNo = beforePart.getNumber();

			// 초도일 경우
			if (isTheFirst) {
//			    if (erpPartHandler.existErpPart(partNo)) {
//				// <entry key="04009">부품[{0}]은 ERP에 존재합니다.</entry>
//				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04009", partNo) + "\\n";
//
//			    }

			} else {

			    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
			    String partRevision = "";
			    partRevision = (beforePart != null) ? beforePart.getVersionIdentifier().getValue() : "";
			    if (beforePart.getLifeCycleState().equals(State.toState("INWORK"))
				    && (partRevision.equals("") || partRevision.equals("0"))) {
				// 통과
			    } else {
				String bomflag = PartSpecGetter.getPartSpec(beforePart, PartSpecEnum.SpBOMFlag);
				if (bomflag != null && bomflag.equalsIgnoreCase("OLD")) {

				    // 개정유무 Validation 처리
				    String afterVersion = ketMoldECABomLink.getAfterVersion();
				    if (afterVersion == null || afterVersion.equals("")) {
					// <entry key="04390">개정하지 않은 부품이 있습니다.</entry>
					ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04390") + "\\n";

				    }

				}
			    }

			}

			// EO 결재상신 Validation - 체크 속성 누락 여부 체크
			List<WTPart> targetPartList = new ArrayList<WTPart>();
			String afterVersion = ketMoldECABomLink.getAfterVersion();
			if (afterVersion == null || afterVersion.equals("")) {
			    afterVersion = ketMoldECABomLink.getPreVersion();
			}
			String afterPartOid = ketBOMQueryBean.getPartOid(partNo, afterVersion);
			WTPart afterPart = (WTPart) CommonUtil.getObject(afterPartOid);
			targetPartList.add(afterPart);
			List<PartValidationDTO> resultPartList = PartBaseHelper.service.validCheckedProps(targetPartList, EcoValidParam);

			int resultPartListSize = (resultPartList != null) ? resultPartList.size() : 0;
			for (int i = 0; i < resultPartListSize; i++) {
			    PartValidationDTO dto = resultPartList.get(i);
			    // <entry key="04017">부품[{0}]은 아래 속성들이 입력누락되었습니다.</entry>

			    String aLinkPartNo = "<a href=\"javascript:openViewPart('" + dto.getValidPartNumber() + "');\">"
				    + dto.getValidPartNumber() + "</a>";

			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04017",
				    StringEscapeUtils.escapeJavaScript(aLinkPartNo))
				    + "\\n";
			    ALERT_MESSAGE += "&nbsp;&nbsp;" + StringEscapeUtils.escapeJavaScript(dto.getValidContent()) + "\\n\\n";

			}

			// EO 결재상신 Validation - 반제품 ERP에 존재하는지 체크
			resultPartList = PartBaseHelper.service.validHalbErpExistByDie(targetPartList);
			resultPartListSize = (resultPartList != null) ? resultPartList.size() : 0;
			for (int i = 0; i < resultPartListSize; i++) {
			    PartValidationDTO dto = resultPartList.get(i);
			    // <entry key="04018">부품[{0}]은 관련제품[{1}]이 ERP에 존재하지 않습니다.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04018", dto.getValidPartNumber(),
				    StringEscapeUtils.escapeJavaScript(dto.getValidContent())) + "\\n";

			}

		    }

		    // 도면
		    queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETMoldECAEpmDocLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETMoldECAEpmDocLink ketMoldECAEpmDocLink = (KETMoldECAEpmDocLink) queryResult.nextElement();

			// 초도일 경우 제외한다.
			if (isTheFirst || (changeReason.lastIndexOf("REASON_13") > -1)) {
			    // Do nothing..!!
			} else {

			    // 초도배포가 아닐 경우 OOTB Revision이 '0'일 경우 통과
			    String epmDocRevision = "";
			    EPMDocument epmDoc = ketMoldECAEpmDocLink.getEpmDoc();
			    epmDocRevision = (epmDoc != null) ? epmDoc.getVersionIdentifier().getValue() : "";
			    if (epmDoc.getLifeCycleState().equals(State.toState("INWORK"))
				    && (epmDocRevision.equals("") || epmDocRevision.equals("0"))) {
				// 통과
			    } else {

				// 개정유무 Validation 처리
				String afterVersion = ketMoldECAEpmDocLink.getAfterVersion();
				if (afterVersion == null || afterVersion.equals("")) {
				    // <entry key="04380">개정되지 않은 도면이 있습니다.</entry>
				    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04380") + "\\n";

				}

			    }

			}

		    }
		    // if( activityType.equals("1") )
		    // {
		    // beans.updateMoldPlan( ecoOid );
		    // }

		    // 표준품 또는 4M변경일 경우
		    String activityType = StringUtils.stripToEmpty(eca.getActivityType());
		    if (activityType.equals("3")) {
			// 표준품리스트의 문서일 경우

			queryResult = PersistenceHelper.manager.navigate(eca, "doc", KETMoldECADocLink.class, false);
			if (queryResult.hasMoreElements()) {
			    KETMoldECADocLink ketMoldECADocLink = (KETMoldECADocLink) queryResult.nextElement();

			    // 개정유무 Validation 처리
			    String afterVersion = ketMoldECADocLink.getAfterVersion();
			    if (afterVersion == null || afterVersion.equals("")) {
				// <entry key="04400">개정되지 않은 문서가 있습니다.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04400");

			    }

			    // 결재 승인 유무
			    KETProjectDocument ketProjectDocument = ketMoldECADocLink.getDoc();
			    String documentOid = EcmUtil.getDocumentOid(ketProjectDocument.getNumber(), afterVersion);
			    if (documentOid != null && !documentOid.equals("")) {
				KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument) CommonUtil.getObject(documentOid);
				// KETProjectDocument ketProjectDocumentLatestVersion = (KETProjectDocument)
				// ObjectUtil.getLatestVersion(ketProjectDocument);

				State ketProjectDocumentLatestVersionState = ketProjectDocumentLatestVersion.getState().getState();
				// if (ketProjectDocumentLatestVersionState.equals(State.toState("INWORK")) ||
				// ketProjectDocumentLatestVersionState.equals(State.toState("UNDERREVIEW"))) {
				if (!ketProjectDocumentLatestVersionState.equals(State.toState("APPROVED"))) {
				    // <entry key="05000">결재승인되지 않은 문서가 있습니다.</entry>
				    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "05000");
				}
			    } else {
				// <entry key="04001">개정한 문서가 삭제되었습니다.\n관리자에게 문의하여 주십시오.</entry>
				ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04001");

			    }

			}
		    }
		    // 활동(4M변경)일 경우
		    else if (activityType.equals("4")) {
			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			if (workerId.equals("")) {
			    // <entry key="04007">ECN의 후속업무[{0}]에 담당자를 추가하여 주십시오.</entry>
			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04007", eca.getCustomName()) + "\\n";

			}

			Timestamp completeRequestDate = eca.getCompleteRequestDate();
			if (completeRequestDate == null) {
			    // <entry key="04008">ECN의 후속업무[{0}]에 완료요청일을 추가하여 주십시오.</entry>

			    ALERT_MESSAGE += messageService.getString("e3ps.message.ket_message", "04008", eca.getCustomName()) + "\\n";

			}

		    }

		}

	    }

	    /*
	     * if (!ALERT_MESSAGE.equals("")) { //그래도 작업을 계속 진행하시겠습니까? ALERT_MESSAGE += "\\n" +
	     * messageService.getString("e3ps.message.ket_message", "04260");
	     * 
	     * }
	     */

	} catch (Exception e) {
	    Kogger.error(getClass(), e);

	    throw new WTException(e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String updateEcoAfterSaveBomEditor(String ecoNumber, String partNumber) throws WTException {
	String ALERT_MESSAGE = "";

	try {
	    ProdEcoBeans prodEcoBeans = new ProdEcoBeans();
	    WTChangeOrder2 wtChangeOrder2 = prodEcoBeans.getEcoByNo(ecoNumber);

	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {

		QueryResult queryResult = PersistenceHelper.manager.navigate(wtChangeOrder2, "prodECA", KETProdChangeActivityLink.class,
		        false);
		if (queryResult.hasMoreElements()) {
		    KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) queryResult.nextElement();
		    KETProdChangeActivity eca = ketProdChangeActivityLink.getProdECA();

		    QueryResult queryResult2 = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
		    if (queryResult2.hasMoreElements()) {
			KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult2.nextElement();
			ketProdECABomLink.setChangeYn("Y");

			PersistenceHelper.manager.save(ketProdECABomLink);

		    }
		}
	    } else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {

		QueryResult queryResult = PersistenceHelper.manager.navigate(wtChangeOrder2, "moldECA", KETMoldChangeActivityLink.class,
		        false);
		if (queryResult.hasMoreElements()) {
		    KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) queryResult.nextElement();
		    KETMoldChangeActivity eca = ketMoldChangeActivityLink.getMoldECA();

		    QueryResult queryResult2 = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
		    if (queryResult2.hasMoreElements()) {
			KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) queryResult2.nextElement();
			ketMoldECABomLink.setChangeYn("Y");

			PersistenceHelper.manager.save(ketMoldECABomLink);

		    }
		}
	    }

	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public ArrayList<WTPart> getWTPartListUnrevisedFromEco(String ecoOid) throws WTException {
	ArrayList<WTPart> partList = new ArrayList<WTPart>();

	try {
	    WTChangeOrder2 wtChangeOrder2 = (WTChangeOrder2) CommonUtil.getObject(ecoOid);

	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {

		QueryResult queryResult = PersistenceHelper.manager.navigate(wtChangeOrder2, "prodECA", KETProdChangeActivityLink.class,
		        false);
		while (queryResult.hasMoreElements()) {
		    KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) queryResult.nextElement();
		    KETProdChangeActivity eca = ketProdChangeActivityLink.getProdECA();

		    QueryResult queryResult2 = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
		    if (queryResult2.hasMoreElements()) {
			KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult2.nextElement();

			String afterVersion = StringUtils.stripToEmpty(ketProdECABomLink.getAfterVersion());
			if (afterVersion.equals("")) {
			    String beforePartOid = StringUtils.stripToEmpty(ketProdECABomLink.getBeforePartOid());
			    WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
			    // 0버전이면서 승인완료되지않았으면 개정대상이 아니므로 제외한다 2015.09.30 by 황정태
			    if (wtPart.getVersionIdentifier().getValue().equals("0")
				    && State.toState("APPROVED") != wtPart.getLifeCycleState()) {
				continue;
			    }
			    if (wtPart != null && !partList.contains(wtPart))
				partList.add(wtPart);
			}

		    }
		}
	    } else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {

		QueryResult queryResult = PersistenceHelper.manager.navigate(wtChangeOrder2, "moldECA", KETMoldChangeActivityLink.class,
		        false);
		while (queryResult.hasMoreElements()) {
		    KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) queryResult.nextElement();
		    KETMoldChangeActivity eca = ketMoldChangeActivityLink.getMoldECA();

		    QueryResult queryResult2 = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
		    if (queryResult2.hasMoreElements()) {
			KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) queryResult2.nextElement();

			String afterVersion = StringUtils.stripToEmpty(ketMoldECABomLink.getAfterVersion());
			if (afterVersion.equals("")) {
			    String beforePartOid = StringUtils.stripToEmpty(ketMoldECABomLink.getBeforePartOid());
			    WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
			    // 0버전이면서 승인완료되지않았으면 개정대상이 아니므로 제외한다 2015.09.30 by 황정태
			    if (wtPart.getVersionIdentifier().getValue().equals("0")
				    && State.toState("APPROVED") != wtPart.getLifeCycleState()) {
				continue;
			    }

			    if (wtPart != null && !partList.contains(wtPart))
				partList.add(wtPart);
			}

		    }
		}
	    }

	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return partList;
    }

    @Override
    public void saveKETEcnWeightHistory(String ecoOid, String[] part_no, String[] part_sp_net_weight, String[] part_sp_total_weight,
	    String[] part_oid, String[] part_sp_scrab_weight) throws WTException {
	try {

	    WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid);

	    int part_oidLength = (part_oid != null) ? part_oid.length : 0;
	    for (int i = 0; i < part_oidLength; i++) {
		KETEcnWeightHistory ketEcnWeightHistory = KETEcnWeightHistory.newKETEcnWeightHistory();

		WTPart part = (WTPart) CommonUtil.getObject(part_oid[i]);

		ketEcnWeightHistory.setEco(eco);
		ketEcnWeightHistory.setPart(part);
		ketEcnWeightHistory.setSpTotalWeight(part_sp_total_weight[i]);
		ketEcnWeightHistory.setSpNetWeight(part_sp_net_weight[i]);
		ketEcnWeightHistory.setSpScrabWeight(part_sp_scrab_weight[i]);

		// 저장
		ketEcnWeightHistory = (KETEcnWeightHistory) PersistenceHelper.manager.save(ketEcnWeightHistory);

	    }
	} catch (WTException wte) {
	    Kogger.error(getClass(), wte);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}
    }

    @Override
    public HashMap<WTChangeRequest2, ArrayList<Managed>> getEcrRelatedMeetingDelay() throws WTException {
	HashMap<WTChangeRequest2, ArrayList<Managed>> result = new HashMap<WTChangeRequest2, ArrayList<Managed>>();
	ArrayList<Managed> values = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    // con = PlmDBUtil.getConnection();
	    con = DBUtil.getConnection();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT * FROM ( \n");

	    sql.append(" SELECT ecr.classnameA2A2||':'||ecr.ida2a2 AS ecrOid \n");
	    sql.append("      , meeting.classnameA2A2||':'||meeting.ida2a2 AS meetingOid \n");
	    sql.append("   FROM KETPRODCHANGEREQUEST ecr \n");
	    sql.append("      , KETChangeRequestExpansion ecrex \n");
	    sql.append("      , KETEcrMeetingLink mlink \n");
	    sql.append("      , KETMEETINGMINUTES meeting \n");
	    sql.append("      , KETWfmApprovalMaster appr \n");
	    sql.append("  WHERE ecr.IDA2A2 = ecrex.ida3a8 \n");
	    sql.append("    AND ecr.ida2a2 = mlink.ida3a5(+) \n");
	    sql.append("    AND mlink.ida3b5 = meeting.ida2a2(+) \n");
	    sql.append("    AND ecr.ida2a2 = appr.ida3b4 \n");
	    sql.append("    AND ecr.statestate = 'APPROVED' \n");
	    sql.append("    AND meeting.ida2a2 IS NULL \n");
	    sql.append("    AND TO_CHAR(appr.completeddate + 7, 'yyyyMMdd') < TO_CHAR(SYSDATE, 'yyyyMMdd') \n");
	    sql.append("  UNION ALL \n");
	    sql.append(" SELECT ecr.classnameA2A2||':'||ecr.ida2a2 AS ecrOid \n");
	    sql.append("      , meeting.classnameA2A2||':'||meeting.ida2a2 AS meetingOid \n");
	    sql.append("   FROM KETMOLDCHANGEREQUEST ecr \n");
	    sql.append("      , KETChangeRequestExpansion ecrex \n");
	    sql.append("      , KETEcrMeetingLink mlink \n");
	    sql.append("      , KETMEETINGMINUTES meeting \n");
	    sql.append("      , KETWfmApprovalMaster appr \n");
	    sql.append("  WHERE ecr.IDA2A2 = ecrex.ida3a8 \n");
	    sql.append("    AND ecr.ida2a2 = mlink.ida3a5(+) \n");
	    sql.append("    AND mlink.ida3b5 = meeting.ida2a2(+) \n");
	    sql.append("    AND ecr.ida2a2 = appr.ida3b4 \n");
	    sql.append("    AND ecr.statestate = 'APPROVED' \n");
	    sql.append("    AND meeting.ida2a2 IS NULL \n");
	    sql.append("    AND TO_CHAR(appr.completeddate + 7, 'yyyyMMdd') < TO_CHAR(SYSDATE, 'yyyyMMdd') \n");

	    sql.append(" ) \n");
	    sql.append("ORDER BY ecrOid \n");

	    pstmt = con.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		String ecrOid = rs.getString(1);
		String meetingOid = rs.getString(2);

		WTChangeRequest2 ecr = (WTChangeRequest2) CommonUtil.getObject(ecrOid);
		Managed meeting = null; // (Managed) CommonUtil.getObject(meetingOid);

		if (!result.containsKey(ecr)) {
		    values = new ArrayList<Managed>();
		    values.add(meeting);

		    result.put(ecr, values);
		} else {
		    values = result.get(ecr);
		    if (!values.contains(meeting))
			values.add(meeting);
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (pstmt != null)
		    pstmt.close();
		PlmDBUtil.close(con);
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		Kogger.error(getClass(), e);
	    }

	}

	return result;
    }

    @Override
    public HashMap<WTChangeOrder2, ArrayList<Managed>> getEcoRelatedEcnDelay() throws WTException {
	HashMap<WTChangeOrder2, ArrayList<Managed>> result = new HashMap<WTChangeOrder2, ArrayList<Managed>>();
	ArrayList<Managed> values = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    // con = PlmDBUtil.getConnection();
	    con = DBUtil.getConnection();

	    StringBuffer sql = new StringBuffer();
	    sql.append("SELECT * FROM ( \n");

	    sql.append(" SELECT eco.classnameA2A2||':'||eco.ida2a2 AS ecoOid \n");
	    sql.append("      , eca.classnameA2A2||':'||eca.ida2a2 AS ecaOid \n");
	    sql.append("   FROM KETPRODCHANGEORDER eco \n");
	    sql.append("      , KETPRODCHANGEACTIVITY eca \n");
	    sql.append("  WHERE eco.ida2a2 = eca.ida3a8 \n");
	    sql.append("    AND eco.statestate = 'APPROVED' \n");
	    sql.append("    AND eca.activitytype in ('3', '4') \n");
	    sql.append("    AND eca.COMPLETEDATE IS NULL \n");
	    sql.append("    AND TO_CHAR(eca.COMPLETEREQUESTDATE, 'yyyyMMdd')  < TO_CHAR(SYSDATE, 'yyyyMMdd') \n");
	    sql.append("  UNION ALL \n");
	    sql.append(" SELECT eco.classnameA2A2||':'||eco.ida2a2 AS ecoOid \n");
	    sql.append("      , eca.classnameA2A2||':'||eca.ida2a2 AS ecaOid \n");
	    sql.append("   FROM KETMOLDCHANGEORDER eco \n");
	    sql.append("      , KETMOLDCHANGEACTIVITY eca \n");
	    sql.append("  WHERE eco.ida2a2 = eca.ida3a8 \n");
	    sql.append("    AND eco.statestate = 'APPROVED' \n");
	    sql.append("    AND eca.activitytype = '4' \n");
	    sql.append("    AND eca.COMPLETEDATE IS NULL \n");
	    sql.append("    AND TO_CHAR(eca.COMPLETEREQUESTDATE, 'yyyyMMdd')  < TO_CHAR(SYSDATE, 'yyyyMMdd') \n");

	    sql.append(" ) \n");
	    sql.append("ORDER BY ecoOid \n");

	    pstmt = con.prepareStatement(sql.toString());

	    rs = pstmt.executeQuery();
	    while (rs.next()) {
		String ecoOid = rs.getString(1);
		String ecaOid = rs.getString(2);

		WTChangeOrder2 eco = (WTChangeOrder2) CommonUtil.getObject(ecoOid);
		Managed eca = (Managed) CommonUtil.getObject(ecaOid);

		if (!result.containsKey(eco)) {
		    values = new ArrayList<Managed>();
		    values.add(eca);

		    result.put(eco, values);
		} else {
		    values = result.get(eco);
		    if (!values.contains(eca))
			values.add(eca);
		}
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    try {
		if (rs != null)
		    rs.close();
		if (pstmt != null)
		    pstmt.close();
		PlmDBUtil.close(con);
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		Kogger.error(getClass(), e);
	    }

	}

	return result;
    }

    @Override
    public String validateEcoPartRelatedEpmDoc(WTChangeOrder2 wtChangeOrder2) throws WTException {
	String ALERT_MESSAGE = "";

	RelatedEpmHandler relatedEpmHandler = new RelatedEpmHandler();
	try {
	    ArrayList<WTPart> partList = new ArrayList<WTPart>();
	    ArrayList<EPMDocument> epmList = new ArrayList<EPMDocument>();

	    if (wtChangeOrder2 instanceof KETProdChangeOrder) {
		KETProdChangeOrder eco = (KETProdChangeOrder) wtChangeOrder2;

		QueryResult qr = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class);
		while (qr.hasMoreElements()) {
		    KETProdChangeActivity eca = (KETProdChangeActivity) qr.nextElement();

		    // BOM
		    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) queryResult.nextElement();
			String beforePartOid = StringUtils.stripToEmpty(ketProdECABomLink.getBeforePartOid());
			WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid);

			if (!partList.contains(beforePart))
			    partList.add(beforePart);
		    }

		    // 도면
		    queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETProdECAEpmDocLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETProdECAEpmDocLink ketProdECAEpmDocLink = (KETProdECAEpmDocLink) queryResult.nextElement();
			EPMDocument epmDoc = ketProdECAEpmDocLink.getEpmDoc();

			if (!epmList.contains(epmDoc))
			    epmList.add(epmDoc);
		    }

		}

	    } else if (wtChangeOrder2 instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder eco = (KETMoldChangeOrder) wtChangeOrder2;

		QueryResult qr = PersistenceHelper.manager.navigate(eco, "moldECA", KETMoldChangeActivityLink.class);
		while (qr.hasMoreElements()) {
		    KETMoldChangeActivity eca = (KETMoldChangeActivity) qr.nextElement();

		    // BOM
		    QueryResult queryResult = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) queryResult.nextElement();
			String beforePartOid = StringUtils.stripToEmpty(ketMoldECABomLink.getBeforePartOid());
			WTPart beforePart = (WTPart) CommonUtil.getObject(beforePartOid);

			if (!partList.contains(beforePart))
			    partList.add(beforePart);
		    }

		    // 도면
		    queryResult = PersistenceHelper.manager.navigate(eca, "epmDoc", KETMoldECAEpmDocLink.class, false);
		    if (queryResult.hasMoreElements()) {
			KETMoldECAEpmDocLink ketMoldECAEpmDocLink = (KETMoldECAEpmDocLink) queryResult.nextElement();
			EPMDocument epmDoc = ketMoldECAEpmDocLink.getEpmDoc();

			if (!epmList.contains(epmDoc))
			    epmList.add(epmDoc);
		    }

		}

	    }

	    String msgEpmNo = "";
	    int partListSize = (partList != null) ? partList.size() : 0;
	    int epmListSize = (epmList != null) ? epmList.size() : 0;
	    RENEW: for (int i = 0; i < epmListSize; i++) {
		EPMDocument epmDoc = epmList.get(i);

		String msgPartNo = "";

		// 도면에 연계된 부품을 가져온다.
		List<WTPartMaster> relatedPartListByEpm = relatedEpmHandler.getRelPartByEcoPMDoc(epmDoc);
		int relatedPartListByEpmSize = (relatedPartListByEpm != null) ? relatedPartListByEpm.size() : 0;
		for (int j = 0; j < relatedPartListByEpmSize; j++) {
		    WTPartMaster wtPartMaster = relatedPartListByEpm.get(j);
		    String masterPartNo = wtPartMaster.getNumber();

		    boolean hasPart = false;
		    for (int k = 0; k < partListSize; k++) {
			WTPart wtPart = partList.get(k);
			if (masterPartNo.equals(wtPart.getNumber())) {
			    hasPart = true;
			    continue RENEW;
			}
		    }

		    if (!hasPart)
			msgPartNo += masterPartNo + ", ";

		}

		if (!msgPartNo.equals("")) {
		    msgPartNo = msgPartNo.substring(0, msgPartNo.lastIndexOf(", "));
		    msgEpmNo += "도면 : " + epmDoc.getNumber() + "\\n - 부품 :" + msgPartNo + "\\n\\n";
		}

	    }

	    if (!msgEpmNo.equals("")) {
		ALERT_MESSAGE += "아래 도면에 각각 연계된 부품을 하나이상 추가하십시오.\\n\\n" + msgEpmNo;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return ALERT_MESSAGE;
    }

    @Override
    public String initEco(String ecoOid) throws WTException {
	KETMessageService messageService = KETMessageService.getMessageService();
	String ALERT_MESSAGE = "";

	Transaction trx = new Transaction();
	WTConnection wtConn = null;
	Connection conn = null;

	WTPrincipal orgPrincipal = SessionHelper.manager.getPrincipal();
	SessionContext current = SessionContext.getContext();
	SessionHelper.manager.setAdministrator();

	try {
	    trx.start();
	    wtConn = EcmUtil.getWTConnection();
	    conn = wtConn.getConnection();
	    conn.setAutoCommit(false);

	    // KETProdChangeOrder 객체화
	    KETProdChangeOrder prodCO = (KETProdChangeOrder) CommonUtil.getObject(ecoOid);

	    QueryResult qr = WorkflowSearchHelper.manager.getCompletedTodoList(prodCO);

	    while (qr.hasMoreElements()) {
		Object[] itemObj = (Object[]) qr.nextElement();
		WorkItem item = (WorkItem) itemObj[0];
		WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
		activity.deleteActivity();
	    }

	    Object[] eca = EcmUtil.getChangeActivities(prodCO);

	    if (eca != null) {
		for (int cnt = 0; cnt < eca.length; cnt++) {
		    KETProdChangeActivity prodCA = (KETProdChangeActivity) eca[cnt];

		    qr = WorkflowSearchHelper.manager.getCompletedTodoList(prodCA);

		    while (qr.hasMoreElements()) {
			Object[] itemObj = (Object[]) qr.nextElement();
			WorkItem item = (WorkItem) itemObj[0];
			WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
			activity.deleteActivity();
		    }

		    // 반려후 재상신 프로세스를 탈 경우 ECN이 생성되지 않는 문제로 반려시 모든 ECA의 WorkItem을 삭제처리 한다.
		    if (WorkflowSearchHelper.manager.IsRuninningTodo(prodCA)) {
			WorkflowSearchHelper.manager.deleteWorkItem(prodCA);
		    }

		    prodCA.setCompleteDate(null);
		    PersistenceHelper.manager.save(prodCA);

		}
	    }

	    LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		    "KET_ECO_LC", WTContainerHelper.service.getExchangeRef());
	    LifeCycleHelper.service.reassign(prodCO, paramLifeCycleTemplateReference);

	    trx.commit();
	    trx = null;

	} catch (WTPropertyVetoException wtpve) {
	    trx.rollback();

	    // Kogger.error(getClass(), wtpve);
	    throw new WTException(wtpve);

	} catch (WTException wte) {
	    trx.rollback();

	    // Kogger.error(getClass(), wte);
	    throw wte;

	} catch (Exception e) {
	    trx.rollback();

	    // Kogger.error(getClass(), e);
	    throw new WTException(e);

	} finally {
	    SessionHelper.manager.setPrincipal(orgPrincipal.getName());
	    SessionContext.setContext(current);
	    try {
		if (wtConn != null)
		    EcmUtil.freeWTConnection(wtConn);
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
	    }

	    if (trx != null) {
		trx.rollback();
		trx = null;
	    }

	}

	return ALERT_MESSAGE;
    }
}
