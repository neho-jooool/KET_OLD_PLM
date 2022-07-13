/*
 * bcwti
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 * ecwti
 */

package e3ps.wfm.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import wt.change2.WTChangeRequest2;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.epm.EPMDocument;
import wt.epm.EPMDocumentMaster;
import wt.events.KeyedEvent;
import wt.events.KeyedEventListener;
import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceManagerEvent;
import wt.fc.PersistenceServerHelper;
import wt.fc.PersistentReference;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.folder.SubFolder;
import wt.iba.value.StringValue;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.introspection.WTIntrospector;
import wt.lifecycle.LifeCycleHelper;
import wt.lifecycle.LifeCycleManaged;
import wt.lifecycle.LifeCycleServiceEvent;
import wt.lifecycle.LifeCycleTemplate;
import wt.lifecycle.LifeCycleTemplateReference;
import wt.lifecycle.Phase;
import wt.lifecycle.PhaseTemplate;
import wt.lifecycle.State;
import wt.org.OrganizationServicesHelper;
import wt.org.WTGroup;
import wt.org.WTPrincipal;
import wt.org.WTPrincipalReference;
import wt.org.WTUser;
import wt.ownership.Ownership;
import wt.part.WTPart;
import wt.pom.Transaction;
import wt.project.Role;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.ExistsExpression;
import wt.query.NegatedExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.services.ManagerException;
import wt.services.ServiceEventListenerAdapter;
import wt.services.StandardManager;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.team.Team;
import wt.team.TeamHelper;
import wt.team.TeamManaged;
import wt.team.TeamTemplate;
import wt.team.TeamTemplateReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.views.ViewManageable;
import wt.workflow.definer.WfDefinerHelper;
import wt.workflow.definer.WfProcessDefinition;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.definer.WfTemplateObject;
import wt.workflow.engine.ProcessData;
import wt.workflow.engine.WfEngineHelper;
import wt.workflow.engine.WfEngineServerHelper;
import wt.workflow.engine.WfProcess;
import wt.workflow.engine.WfState;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WfAssignmentState;
import wt.workflow.work.WorkItem;
import wt.workflow.work.WorkflowHelper;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigImpl;
import e3ps.common.mail.MailHtmlContentTemplate;
import e3ps.common.mail.MailUtil;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.beans.EcmUtil;
import e3ps.ecm.entity.KETChangeNotice;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETEcoEcnLink;
import e3ps.ecm.entity.KETMeetingMinutes;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeActivityLink;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangePlan;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETMoldECABomLink;
import e3ps.ecm.entity.KETMoldECOPartLink;
import e3ps.ecm.entity.KETMoldECOPlanLink;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivityLink;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ecm.entity.KETProdECABomLink;
import e3ps.ecm.entity.KETProdECOPlanLink;
import e3ps.ecm.service.KETEcmHelper;
import e3ps.edm.beans.EDMQueryHelper;
import e3ps.edm.util.VersionHelper;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEnd;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningPlanLink;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.ews.entity.KETEarlyWarningResultLink;
import e3ps.ews.entity.KETEarlyWarningStep;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleData;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.KETMoldChangeOrderOutputLink;
import e3ps.project.KETProdChangeOrderOutputLink;
import e3ps.project.KETTryMoldOutputLink;
import e3ps.project.KETTryPressOutputLink;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ProjectChangeStop;
import e3ps.project.ProjectOutput;
import e3ps.project.ReviewProject;
import e3ps.project.TaskDependencyLink;
import e3ps.project.WorkProject;
import e3ps.project.beans.E3PSTaskData;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.ProjectTaskHelper;
import e3ps.project.beans.TaskDependencyHelper;
import e3ps.project.beans.TaskHelper;
import e3ps.project.cancel.CancelProject;
import e3ps.project.cancel.ProjectCancelLink;
import e3ps.project.historyprocess.HistoryHelper;
import e3ps.wfm.dao.WFMSearchUtilDao;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalLine;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMasterProcessLink;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.entity.KETWfmMultiReqDocLink;
import e3ps.wfm.entity.KETWfmMultiReqEpmLink;
import e3ps.wfm.util.ClassifyPBOUtil;
import e3ps.wfm.util.WFMProperties;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.bom.query.KETBOMQueryBean;
import ext.ket.cost.entity.CostReport;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmDTO;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.dqm.entity.KETDqmTodoAtivity;
import ext.ket.dqm.service.DqmHelper;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.util.InvestUtil;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.GateAssRsltTaskLink;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesCSMeetingManage;
import ext.ket.sales.entity.KETSalesCSMeetingManageLink;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.sample.entity.KETSamplePartLink;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.shared.log.KogDbUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.mail.service.KETMailHelper;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.wfm.entity.KETWfmApprovalHistoryDTO;
import ext.ket.wfm.service.KETWorkflowHelper;

/**
 * <p>
 * Use the <code>newStandardKETWfmService</code> static factory method(s), not the <code>StandardKETWfmService</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * @version 1.0
 **/

public class StandardKETWfmService extends StandardManager implements KETWfmService, Serializable {

    private static final String RESOURCE = "e3ps.wfm.service.serviceResource";
    private static final String CLASSNAME = StandardKETWfmService.class.getName();

    private KeyedEventListener listener;
    private static int mailCount = 0;
    Config conf = ConfigImpl.getInstance();
    boolean mailEnable = conf.getBoolean("e3ps.mail.enable");
    boolean VERBOSE = conf.getBoolean("develop.verbose");
    Vector check_vec = new Vector();

    /**
     * Returns the conceptual (modeled) name for the class. <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated
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
	listener = new WorkflowEventListener(this.getConceptualClassname());
	getManagerService().addEventListener(listener, LifeCycleServiceEvent.generateEventKey(LifeCycleServiceEvent.STATE_CHANGE));
	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_STORE));
	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.UPDATE));
	getManagerService().addEventListener(listener, PersistenceManagerEvent.generateEventKey(PersistenceManagerEvent.POST_MODIFY));
    }

    /**
     * Default factory for the class.
     * 
     * @return StandardKETWfmService
     * @exception wt.util.WTException
     **/
    public static StandardKETWfmService newStandardKETWfmService() throws WTException {
	StandardKETWfmService instance = new StandardKETWfmService();
	instance.initialize();
	return instance;
    }

    @Override
    public Persistable createMaster(Hashtable master) throws WTException {

	Transaction trx = null;
	Persistable result = null;

	String discussType = master.get("agreementType").toString();

	Kogger.info(getClass(), "discussType first:" + discussType);

	if (discussType == null || discussType.equals(""))
	    discussType = "sequential";

	WTPrincipalReference creator = (WTPrincipalReference) master.get("creator");
	String comment = master.get("comment").toString();
	Persistable pbo = (Persistable) master.get("pbo");
	boolean startFlag = Boolean.parseBoolean(master.get("startFlag").toString());
	ReferenceFactory rf = new ReferenceFactory();

	try {
	    trx = new Transaction();
	    trx.start();

	    if (WorkflowSearchHelper.manager.getMaster(pbo) == null) {

		Kogger.info(getClass(), "discussType second:" + discussType);
		Kogger.info(getClass(), "creator:" + creator.getName());
		Kogger.info(getClass(), "pbo:" + pbo.toString());
		Kogger.info(getClass(), "startFlag:" + startFlag);

		KETWfmApprovalMaster approvalMaster = new KETWfmApprovalMaster();
		approvalMaster.setAgreementType(discussType);
		approvalMaster.setOwner(creator);
		approvalMaster.setComments(comment);
		approvalMaster.setBusinessobjectRef(PersistentReference.newPersistentReference(pbo));
		approvalMaster.setPbo(pbo);
		approvalMaster.setStartFlag(startFlag);
		Persistable obj = PersistenceHelper.manager.save(approvalMaster);

		Kogger.info(getClass(), "approvalMaster:" + CommonUtil.getOIDString(approvalMaster));

		result = obj;
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}

	return result;
    }

    @Override
    public void createLine(Hashtable approvalLine) throws WTException {
	Transaction trx = null;

	String order = approvalLine.get("order").toString();
	String type = approvalLine.get("type").toString();
	String approvalID = approvalLine.get("userID").toString();
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) approvalLine.get("master");

	try {
	    trx = new Transaction();
	    trx.start();

	    KETWfmApprovalLine line = new KETWfmApprovalLine();
	    line.setApprovalOrder(Integer.parseInt(order));
	    line.setApprovalType(type);
	    line.setApproverID(approvalID);
	    line.setAppMaster(master);
	    PersistenceHelper.manager.save(line);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    @Override
    public void updateMaster(String oid, String agreementType, String comment) throws WTException {
	Transaction trx = null;
	ReferenceFactory rf = new ReferenceFactory();
	try {

	    trx = new Transaction();
	    trx.start();

	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) rf.getReference(oid).getObject();
	    WTPrincipalReference creatorID = SessionHelper.manager.getPrincipalReference();

	    master.setOwner(creatorID);
	    master.setComments(comment);

	    Kogger.info(getClass(), "agreementType :" + agreementType);

	    master.setAgreementType(agreementType);

	    PersistenceServerHelper.manager.update(master);

	    Vector vec = WorkflowSearchHelper.manager.getApprovalLine(master);

	    for (int i = 0; i < vec.size(); i++) {

		KETWfmApprovalLine link = (KETWfmApprovalLine) vec.get(i);

		Kogger.info(getClass(),
		        "## 결재선 삭제:" + link.getApproverID() + " / " + link.getApprovalType() + " / " + link.getApprovalOrder());

		PersistenceHelper.manager.delete(link);
	    }

	    // Vector historyVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	    // for (int m = 0; m < historyVec.size(); m++) {
	    // KETWfmApprovalHistory history = (KETWfmApprovalHistory) historyVec.get(m);
	    // PersistenceHelper.manager.delete(history);
	    // }

	    trx.commit();
	    trx = null;

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    @SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
    @Override
    public boolean startProcess(String pboOid, KETWfmApprovalMaster master) throws WTException {

	Kogger.path("e3ps.wfm.service.StandardKETWfmService::startProcess");
	Kogger.info(getClass(), "[startProcess] PBO : " + pboOid + " / APPROVALMASTER : " + CommonUtil.getOIDString(master)
	        + " / master.isStartFlag() : " + master.isStartFlag());

	Transaction trx = null;
	HashMap bReview = new HashMap();
	HashMap discuss = new HashMap();
	HashMap aReview = new HashMap();
	HashMap receiver = new HashMap();
	HashMap reference = new HashMap();
	ReferenceFactory rf = new ReferenceFactory();
	Persistable obj = rf.getReference(pboOid).getObject();
	WfProcess process = null;

	boolean start = false;
	String request_p = WFMProperties.getInstance().getString("wfm.request");
	String review_p = WFMProperties.getInstance().getString("wfm.review");
	String discuss_p = WFMProperties.getInstance().getString("wfm.discuss");
	String receiver_p = WFMProperties.getInstance().getString("wfm.receiver");
	String reference_p = WFMProperties.getInstance().getString("wfm.reference");
	String reqRecipient_p = WFMProperties.getInstance().getString("wfm.reqrecipient");
	String rework_p = WFMProperties.getInstance().getString("wfm.rework");
	String reject_p = WFMProperties.getInstance().getString("wfm.reject");
	String cancel_p = WFMProperties.getInstance().getString("wfm.cancel");
	String stop_p = WFMProperties.getInstance().getString("wfm.stop");

	Kogger.info(getClass(), "request_p : " + request_p);
	Kogger.info(getClass(), "review_p : " + review_p);
	Kogger.info(getClass(), "discuss_p : " + discuss_p);
	Kogger.info(getClass(), "receiver_p : " + receiver_p);
	Kogger.info(getClass(), "reference_p : " + reference_p);
	Kogger.info(getClass(), "reqRecipient_p : " + reqRecipient_p);
	Kogger.info(getClass(), "rework_p : " + rework_p);
	Kogger.info(getClass(), "reject_p : " + reject_p);

	SessionContext sessioncontext = SessionContext.newContext();
	WTPrincipalReference currentUserRef = SessionHelper.manager.getPrincipalReference();
	boolean IsCancel = false;
	boolean IsStop = false;
	try {
	    trx = new Transaction();
	    trx.start();
	    if (obj instanceof E3PSProject) {
		E3PSProject project = (E3PSProject) obj;

		QueryResult qr = ProjectHelper.getCancelProject(project);

		if (qr.size() > 0) {
		    CancelProject cpProject = null;
		    Object[] ProjectObj = null;
		    while (qr.hasMoreElements()) {
			ProjectObj = (Object[]) qr.nextElement();
			ProjectCancelLink ps = (ProjectCancelLink) ProjectObj[0];
			cpProject = ps.getCancle();
		    }
		    if (cpProject != null && "취소요청".equals(cpProject.getCancelType())) {
			LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
			        "KET_PRODUCT_PMS_LC_NEW", WTContainerHelper.service.getExchangeRef());
			project = (E3PSProject) LifeCycleHelper.service.reassign(project, paramLifeCycleTemplateReference);
			project = (E3PSProject) PersistenceHelper.manager.refresh(project);
			project = (E3PSProject) LifeCycleHelper.service.setLifeCycleState(project, wt.lifecycle.State.toState("CANCELING"),
			        true);
			project = (E3PSProject) PersistenceHelper.manager.refresh(project);
			Kogger.info(getClass(), "취소 결재 Assigned L/C : KET_PRODUCT_PMS_LC_NEW");

			cpProject.setCancelType("취소중");
			PersistenceHelper.manager.modify(cpProject);
			IsCancel = true;
		    }
		}
		if (!IsCancel) {
		    LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
			    "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		    project = (E3PSProject) LifeCycleHelper.service.reassign(project, paramLifeCycleTemplateReference);
		    Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

		    ProjectChangeStop ps = ProjectHelper.getStopProject(project);

		    if (ps != null && "중지".equals(ps.getChangeType())) {
			ps.setChangeType("중지검토");
			PersistenceHelper.manager.save(ps);
			IsStop = true;
		    }
		}

	    } else if (obj instanceof KETDqmRaise) {
		KETDqmRaise project = (KETDqmRaise) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		project = (KETDqmRaise) LifeCycleHelper.service.reassign(project, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETDqmAction) {
		KETDqmAction project = (KETDqmAction) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		project = (KETDqmAction) LifeCycleHelper.service.reassign(project, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETDrawingDistRequest) {
		KETDrawingDistRequest project = (KETDrawingDistRequest) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		project = (KETDrawingDistRequest) LifeCycleHelper.service.reassign(project, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETSampleRequest) {
		KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		ketSampleRequest = (KETSampleRequest) LifeCycleHelper.service.reassign(ketSampleRequest, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETProjectDocument) {
		KETProjectDocument document = (KETProjectDocument) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (KETProjectDocument) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETTechnicalDocument) {
		KETTechnicalDocument document = (KETTechnicalDocument) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (KETTechnicalDocument) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof AssessSheet) {
		AssessSheet document = (AssessSheet) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (AssessSheet) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof GateAssessResult) {
		GateAssessResult document = (GateAssessResult) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (GateAssessResult) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETTryMold) {
		KETTryMold document = (KETTryMold) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (KETTryMold) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETTryPress) {
		KETTryPress document = (KETTryPress) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (KETTryPress) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETProdChangeOrder) {
		KETProdChangeOrder document = (KETProdChangeOrder) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (KETProdChangeOrder) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if (obj instanceof KETMoldChangeOrder) {
		KETMoldChangeOrder document = (KETMoldChangeOrder) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		document = (KETMoldChangeOrder) LifeCycleHelper.service.reassign(document, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof EPMDocument)) {
		EPMDocument Edocument = (EPMDocument) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		Edocument = (EPMDocument) LifeCycleHelper.service.reassign(Edocument, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof KETAnalysisRequestInfo)) {
		KETAnalysisRequestInfo analysisInfo = (KETAnalysisRequestInfo) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		analysisInfo = (KETAnalysisRequestInfo) LifeCycleHelper.service.reassign(analysisInfo, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof KETDevelopmentRequest)) {
		KETDevelopmentRequest devReq = (KETDevelopmentRequest) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_DRR_LC_NEW", WTContainerHelper.service.getExchangeRef());
		devReq = (KETDevelopmentRequest) LifeCycleHelper.service.reassign(devReq, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_DRR_LC_NEW");

	    } else if ((obj instanceof KETAnalysisRequestMaster)) {
		KETAnalysisRequestMaster analysisMaster = (KETAnalysisRequestMaster) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_DRR_LC_NEW", WTContainerHelper.service.getExchangeRef());
		analysisMaster = (KETAnalysisRequestMaster) LifeCycleHelper.service.reassign(analysisMaster,
		        paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_DRR_LC_NEW");

	    } else if ((obj instanceof KETSalesProject)) {
		KETSalesProject salesProject = (KETSalesProject) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		salesProject = (KETSalesProject) LifeCycleHelper.service.reassign(salesProject, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof KETSalesCSMeetingApproval)) {

		/*
	         * KETSalesCSMeetingApproval csApprove = null;
	         * 
	         * csApprove = SalesHelper.service.getCSApprovalTarget(manage);//결재요청자의 부서에 해당하는 데이터만 가져옴
	         */
		KETSalesCSMeetingApproval csApprove = (KETSalesCSMeetingApproval) obj;

		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		csApprove = (KETSalesCSMeetingApproval) LifeCycleHelper.service.reassign(csApprove, paramLifeCycleTemplateReference);

		String deptInfo = csApprove.getDeptSort();// 결제데이터에서 해당차수의 부서sortno를 가져온다

		String manageOid = csApprove.getMeetingManageLinkInfo();

		KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(manageOid);

		KETSalesCSMeetingManageLink manageLink = null;

		QueryResult qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);

		while (qr.hasMoreElements()) {// 해당 차수의 해당 부서만 결재상태 업데이트
		    manageLink = (KETSalesCSMeetingManageLink) qr.nextElement();

		    String deptSort = manageLink.getDeptSortNo();

		    if (deptInfo.equals(deptSort)) {
			manageLink.setCsState(csApprove.getLifeCycleState().toString());
			PersistenceHelper.manager.save(manageLink);
		    }
		}
		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof CostReport)) {
		CostReport report = (CostReport) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		report = (CostReport) LifeCycleHelper.service.reassign(report, paramLifeCycleTemplateReference);

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof KETProdChangeRequest)) {
		KETProdChangeRequest pbo = (KETProdChangeRequest) obj;
		LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC_PMS", WCUtil.getWTContainerRef());
		LifeCycleHelper.service.reassign((LifeCycleManaged) pbo, lct.getLifeCycleTemplateReference(), WCUtil.getWTContainerRef());

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof KETCompetentDepartment)) {
		KETCompetentDepartment pbo = (KETCompetentDepartment) obj;

		LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC_PMS", WCUtil.getWTContainerRef());
		LifeCycleHelper.service.reassign((LifeCycleManaged) pbo, lct.getLifeCycleTemplateReference(), WCUtil.getWTContainerRef());

		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    } else if ((obj instanceof KETInvestMaster)) {
		KETInvestMaster im = (KETInvestMaster) obj;
		LifeCycleTemplateReference paramLifeCycleTemplateReference = LifeCycleHelper.service.getLifeCycleTemplateReference(
		        "KET_COMMON_LC_PMS", WTContainerHelper.service.getExchangeRef());
		im = (KETInvestMaster) LifeCycleHelper.service.reassign(im, paramLifeCycleTemplateReference);
		im.setInvestState(InvestUtil.UNDERREVIEW);
		PersistenceServerHelper.manager.update(im);
		im = (KETInvestMaster) PersistenceHelper.manager.refresh(im);
		Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_LC_PMS");

	    }

	    Phase currentPhase = LifeCycleHelper.service.getCurrentPhase((LifeCycleManaged) obj);
	    if (currentPhase != null) {
		Kogger.info(getClass(), "[startProcess] LC Phase check : " + currentPhase.getName());
		PhaseTemplate phaseTemp = (PhaseTemplate) currentPhase.getPhaseTemplateId().getObject();
		if (!phaseTemp.getPhaseState().equals(State.toState("INWORK"))
		        && !phaseTemp.getPhaseState().equals(State.toState("REWORK"))
		        && !phaseTemp.getPhaseState().equals(State.toState("ACTIVITYCOMPLETED"))
		        && !phaseTemp.getPhaseState().equals(State.toState("PLANCHANGE"))
		        && !phaseTemp.getPhaseState().equals(State.toState("CANCELING"))
		        && !phaseTemp.getPhaseState().equals(State.toState("PMOINWORK"))) {
		    throw new Exception(CommonUtil.getOIDString(obj) + " state is " + phaseTemp.getPhaseState().getDisplay());
		}
	    }
	    // if ((obj instanceof EPMDocument)) {
	    // if (!master.isStartFlag()) {
	    // String templateName = WFMProperties.getInstance().getString("wfm.template.common");
	    // LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate(templateName,
	    // WTContainerHelper.service.getExchangeRef());
	    // obj = LifeCycleHelper.service.reassign((LifeCycleManaged) obj, LCtemplate.getLifeCycleTemplateReference());
	    // }
	    // } else
	    if (obj instanceof KETDevelopmentRequest) {
	    } else if ((obj instanceof KETEarlyWarningPlan) || (obj instanceof KETEarlyWarningResult)
		    || (obj instanceof KETEarlyWarningEndReq) || (obj instanceof KETMoldChangeOrder) || (obj instanceof KETProdChangeOrder)) {

		Kogger.info(getClass(), "결재 taskComplete :" + CommonUtil.getOIDString(obj));
		WorkflowSearchHelper.manager.taskComplete(obj); // todo???????? ??????????? ??? todo??? ???

	    } else if (obj instanceof KETWfmMultiApprovalRequest) {
		KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) obj;
		Vector epmList = WorkflowSearchHelper.manager.getEPMList(multiReq);
		Kogger.info(getClass(), "결재 KETWfmMultiApprovalRequest :" + CommonUtil.getOIDString(obj));
		for (int epmidx = 0; epmidx < epmList.size(); epmidx++) {
		    EPMDocument targetEpm = (EPMDocument) epmList.elementAt(epmidx);
		    targetEpm = (EPMDocument) ObjectUtil.getLatestVersion(targetEpm);
		    if (!KETObjectUtil.getVersion(targetEpm).equals("0")) {
			throw new Exception(targetEpm.getNumber() + "version is" + KETObjectUtil.getVersion(targetEpm));
		    } else if (!targetEpm.getLifeCycleState().equals(State.toState("INWORK"))
			    && !targetEpm.getLifeCycleState().equals(State.toState("REWORK"))) {
			throw new Exception(targetEpm.getNumber() + "state is" + targetEpm.getLifeCycleState().getDisplay());
		    }
		}
	    }
	    if (!master.isStartFlag()) {
		process = (WfProcess) LifeCycleHelper.service.getCurrentWorkflow((LifeCycleManaged) obj).getObject();
		if (WfState.OPEN_RUNNING != process.getState()) {
		    Kogger.info(getClass(), "결재 상태 != OPEN_RUNNING :" + process.getState() + " / " + CommonUtil.getOIDString(obj));
		    // Kogger.info(getClass(), "[startProcess] master.isStartFlag() false / WfProcess : " + process.getState());
		    LifeCycleManaged pbo = (LifeCycleManaged) obj;
		    WTContainerRef containerRef = null;
		    if (pbo instanceof WTContained) {
			containerRef = ((WTContained) pbo).getContainerReference();
		    } else {
			containerRef = WTContainerHelper.service.getExchangeRef();
		    }
		    WfProcessDefinition processDefinition = WfDefinerHelper.service.getProcessDefinition("KET_COMMON_WF_NEW", containerRef);
		    process = WfEngineHelper.service.createAdHocProcess(processDefinition.getProcessTemplate(),
			    pbo.getTeamId().getObject(), containerRef);
		    WfEngineServerHelper.service.setPrimaryBusinessObject(process, (WTObject) pbo);
		    Kogger.info(getClass(), "결재 Assigned L/C : KET_COMMON_WF_NEW");
		}
		// Kogger.info(getClass(), "[startProcess] master.isStartFlag() false / WfProcess : " + process.toString());
	    } else {
		process = (WfProcess) LifeCycleHelper.service.getCurrentWorkflow((LifeCycleManaged) obj).getObject();
		if (process == null)
		    process = WorkflowSearchHelper.manager.getProcess(master);
		// Kogger.info(getClass(), "[startProcess] master.isStartFlag() true / WfProcess : " + process.toString());
	    }
	    // process owner setting
	    Ownership paramOwnership = Ownership.newOwnership(currentUserRef);
	    process.setOwnership(paramOwnership);
	    process.setCreator(currentUserRef);

	    Vector vec = WorkflowSearchHelper.manager.getApprovalLine(master);
	    for (int i = 0; i < vec.size(); i++) {
		KETWfmApprovalLine line = (KETWfmApprovalLine) vec.get(i);
		WTUser user = OrganizationServicesHelper.manager.getUser(line.getApproverID());
		if (line.getApprovalType().equals("beforeReview")) {
		    Kogger.info(getClass(), "결재라인 beforeReview : " + line.getApprovalOrder() + " / " + user.getName());
		    bReview.put(line.getApprovalOrder(), user);
		} else if (line.getApprovalType().equals("discuss")) {
		    Kogger.info(getClass(), "결재라인 discuss : " + line.getApprovalOrder() + " / " + user.getName());
		    discuss.put(line.getApprovalOrder(), user);
		} else if (line.getApprovalType().equals("afterReview")) {
		    Kogger.info(getClass(), "결재라인 afterReview : " + line.getApprovalOrder() + " / " + user.getName());
		    aReview.put(line.getApprovalOrder(), user);
		} else if (line.getApprovalType().equals("receiver")) {
		    Kogger.info(getClass(), "결재라인 receiver : " + line.getApprovalOrder() + " / " + user.getName());
		    receiver.put(line.getApprovalOrder(), user);
		} else {
		    Kogger.info(getClass(), "결재라인 else : " + line.getApprovalOrder() + " / " + user.getName());
		    reference.put(line.getApprovalOrder(), user);
		}
	    }
	    Kogger.info(getClass(), "[startProcess] 결재선 정보 / 합의전검토 : " + bReview.size());
	    Kogger.info(getClass(), "[startProcess] 결재선 정보 / 합의 : " + discuss.size());
	    Kogger.info(getClass(), "[startProcess] 결재선 정보 / 검토및승인 : " + aReview.size());
	    Kogger.info(getClass(), "[startProcess] 결재선 정보 / 수신 : " + receiver.size());
	    Kogger.info(getClass(), "[startProcess] 결재선 정보 / 참조 : " + reference.size());
	    Kogger.info(getClass(), "[startProcess] 합의유형 : " + master.getAgreementType());
	    ProcessData pdata = process.getContext();
	    if (master.getAgreementType().equals("noDiscuss")) {
		Kogger.info(getClass(), "결재 agreement_type 0");
		pdata.setValue("agreement_type", 0);
	    } else if (master.getAgreementType().equals("sequential")) {
		Kogger.info(getClass(), "결재 agreement_type 1");
		pdata.setValue("agreement_type", 1);
	    } else if (master.getAgreementType().equals("parallel")) {
		Kogger.info(getClass(), "결재 agreement_type 2");
		pdata.setValue("agreement_type", 2);
	    }
	    int seq = 1;
	    if (IsCancel) {
		request_p = cancel_p + " " + request_p;
		review_p = cancel_p + " " + review_p;
		discuss_p = cancel_p + " " + discuss_p;
		receiver_p = cancel_p + " " + receiver_p;
		reference_p = cancel_p + " " + reference_p;
		Vector<KETWfmApprovalHistory> hisVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
		seq = hisVec.size() + 1;
	    }

	    if (IsStop) {
		request_p = stop_p + " " + request_p;
		review_p = stop_p + " " + review_p;
		discuss_p = stop_p + " " + discuss_p;
		receiver_p = stop_p + " " + receiver_p;
		reference_p = stop_p + " " + reference_p;
		Vector<KETWfmApprovalHistory> hisVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
		seq = hisVec.size() + 1;
	    }

	    if (obj instanceof KETSalesProject) {
		KETSalesProject salesPjt = (KETSalesProject) obj;

		request_p = salesPjt.getAfterSalesState().getName() + " " + request_p;
	    }
	    if (obj instanceof KETSalesCSMeetingApproval) {

		request_p = "CS회의순서확정 " + request_p;
	    }

	    int gateSeq = 0;

	    if (obj instanceof GateAssessResult) {
		GateAssessResult gateAssRslt = (GateAssessResult) obj;
		E3PSTask gate = ProjectTaskCompHelper.service.getTask(gateAssRslt);
		String oid = CommonUtil.getOIDString(gate);
		gateSeq = ProjectTaskCompHelper.service.getMaxGateDegree(oid);
	    }

	    String req_str = request_p;
	    // ECO에서는 재작업 시 활동완료 상태에서 재작업에 대한 결재가 상신되므로 history에서 반려이력이 있는 지도 확인해야함.
	    if ((currentPhase != null && currentPhase.getName().equals(rework_p)) || KETWorkflowHelper.manager.hasRejectHistory(master)) {
		Kogger.info(getClass(), " ECO에서는 재작업 시 활동완료 상태에서 재작업에 대한 결재가 상신되므로 history에서 반려이력이 있는 지도 확인");
		Vector<KETWfmApprovalHistory> hisVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
		Collections.sort(hisVec, ComparatorUtil.APPROVALLINESORT);
		int rejectPos = 1;
		for (KETWfmApprovalHistory his : hisVec) {
		    rejectPos++;
		    if (StringUtil.checkNull(his.getDecision()).equals(reject_p))
			seq = rejectPos;
		}
		// seq = rejectPos;
		req_str = rework_p;
		for (int i = seq - 1; i < hisVec.size(); i++) {
		    KETWfmApprovalHistory his = hisVec.elementAt(i);

		    if (obj instanceof GateAssessResult) {
			if (his.getDegree() != gateSeq) {
			    continue;
			}
		    }

		    Kogger.info(getClass(),
			    "[startProcess] 재작업 시 KETWfmApprovalHistory 정리 : " + his.getSeqNum() + ". " + his.getActivityName() + "/"
			            + his.getOwner().getFullName());
		    PersistenceHelper.manager.delete(his);
		}
	    }

	    Kogger.info(getClass(), "[startProcess] 결재이력 작성 / " + req_str);
	    KETWfmApprovalHistory hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
	    hisAttr.setActivityName(req_str);
	    Timestamp completeDate = DateUtil.getCurrentTimestamp();
	    hisAttr.setCompletedDate(completeDate);
	    hisAttr.setComments(master.getComments());
	    hisAttr.setDecision(request_p);
	    hisAttr.setAppMaster(master);
	    hisAttr.setOwner(SessionHelper.manager.getPrincipalReference());
	    Kogger.info(getClass(), "[startProcess] " + seq + ". " + SessionHelper.manager.getPrincipalReference().getFullName());
	    hisAttr.setSeqNum(seq++);
	    hisAttr.setDegree(gateSeq);
	    createHistory(hisAttr);

	    Kogger.info(getClass(), "[startProcess] 결재선 작성 / 합의전 검토");
	    for (int i = 1; i < bReview.size() + 1; i++) {
		pdata.setValue("reviewer" + i, bReview.get(i - 1));
		WTUser userRf = (WTUser) bReview.get(i - 1);
		hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
		hisAttr.setActivityName(review_p);
		hisAttr.setCompletedDate(null);
		hisAttr.setDecision("");
		hisAttr.setAppMaster(master);
		WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(userRf.getName());
		hisAttr.setOwner(owner);
		Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
		hisAttr.setSeqNum(seq++);
		if ((i == bReview.size()) && (aReview.size() == 0)) {
		    hisAttr.setLast(true);
		}
		hisAttr.setDegree(gateSeq);
		createHistory(hisAttr);
	    }

	    Kogger.info(getClass(), "[startProcess] 결재선 작성 / 합의");
	    for (int i = 1; i < discuss.size() + 1; i++) {
		if (master.getAgreementType().equals("sequential")) {
		    pdata.setValue("sequential" + i, discuss.get(i - 1));
		} else if (master.getAgreementType().equals("parallel")) {
		    pdata.setValue("parallel" + i, discuss.get(i - 1));
		}
		WTUser userRf = (WTUser) discuss.get(i - 1);
		hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
		hisAttr.setActivityName(discuss_p);
		hisAttr.setCompletedDate(null);
		hisAttr.setDecision("");
		hisAttr.setAppMaster(master);
		WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(userRf.getName());
		hisAttr.setOwner(owner);
		Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
		hisAttr.setSeqNum(seq++);
		hisAttr.setDegree(gateSeq);
		createHistory(hisAttr);
	    }

	    Kogger.info(getClass(), "[startProcess] 결재선 작성 / 검토및승인");
	    for (int i = 1; i < aReview.size() + 1; i++) {
		pdata.setValue("submitter" + i, aReview.get(i - 1));
		WTUser userRf = (WTUser) aReview.get(i - 1);
		hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
		hisAttr.setActivityName(review_p);
		hisAttr.setCompletedDate(null);
		hisAttr.setDecision("");
		hisAttr.setAppMaster(master);
		WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(userRf.getName());
		hisAttr.setOwner(owner);
		Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
		hisAttr.setSeqNum(seq++);
		hisAttr.setDegree(gateSeq);
		if (i == aReview.size())
		    hisAttr.setLast(true);
		createHistory(hisAttr);
	    }

	    SessionHelper.manager.setAdministrator();
	    Team team = (Team) ClassifyPBOUtil.extractPBOInfo(obj).get("team");

	    // 개발검토의뢰서 지정 결재선 추가
	    if (master.getAgreementType().equals("reqReceiver")) {
		if (obj instanceof KETDevelopmentRequest) {
		    Kogger.info(getClass(), "[startProcess] 개발검토의뢰서 지정 결재선 추가");
		    KETDevelopmentRequest dr = (KETDevelopmentRequest) obj;
		    // 전자사업부의 개발검토의뢰서는 의뢰접수자로 지정된 사람에게 보냄
		    if (dr.getDivisionCode().equals("ER")) {
			Kogger.info(getClass(), "[startProcess] 전자사업부 개발검토의뢰서  결재선 작성 / 의뢰접수");
			String groupName = WFMProperties.getInstance().getString("wfm.drr5.group"); // 전자사업부_의뢰접수 그룹
			WTGroup group1 = OrganizationServicesHelper.manager.getGroup(groupName);
			Enumeration enumer1 = group1.members();
			while (enumer1.hasMoreElements()) {
			    WTUser member = (WTUser) enumer1.nextElement();
			    WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
			    TeamHelper.service.addRolePrincipalMap(Role.toRole("REQUEST_RECEIVER"), prin, team);
			    hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
			    hisAttr.setActivityName(reqRecipient_p);
			    hisAttr.setCompletedDate(null);
			    hisAttr.setDecision("");
			    hisAttr.setAppMaster(master);
			    WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(member.getName());
			    Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
			    hisAttr.setOwner(owner);
			    hisAttr.setSeqNum(seq++);
			    createHistory(hisAttr);
			}
		    } else {
			Kogger.info(getClass(), "[startProcess] 개발검토의뢰서 의뢰담당자 : " + dr.getAttribute1());
			if (dr.getAttribute1() != null) {
			    if (dr.getAttribute1().equals("1")) {
				Kogger.info(getClass(), "[startProcess] 자동차사업부 개발검토의뢰서 결재선 작성 / 전장모듈 의뢰접수");
				String groupName = WFMProperties.getInstance().getString("wfm.drr4.group"); // 자동차사업부_기획4 (전장모듈)
				WTGroup group1 = OrganizationServicesHelper.manager.getGroup(groupName);
				Enumeration enumer1 = group1.members();
				while (enumer1.hasMoreElements()) {
				    WTUser member = (WTUser) enumer1.nextElement();
				    WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
				    TeamHelper.service.addRolePrincipalMap(Role.toRole("REQUEST_RECEIVER"), prin, team);
				    hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
				    hisAttr.setActivityName(reqRecipient_p);
				    hisAttr.setCompletedDate(null);
				    hisAttr.setDecision("");
				    hisAttr.setAppMaster(master);
				    WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(member.getName());
				    Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
				    hisAttr.setOwner(owner);
				    hisAttr.setSeqNum(seq++);
				    createHistory(hisAttr);
				}
			    } else {
				String toUserId = "";
				String fromUserId = (String) ClassifyPBOUtil.extractPBOInfo(obj).get("creatorID"); // 메일 발송자?
				String mailGroup = WFMProperties.getInstance().getString("wfm.drr3.group"); // 자동차사업부_기획3 (메일만 받는 사람들)
				WTGroup group = OrganizationServicesHelper.manager.getGroup(mailGroup);
				Kogger.info(getClass(), "[startProcess] 자동차사업부 메일 발송 그룹 : " + group.getName());
				Enumeration enumer = group.members();
				while (enumer.hasMoreElements()) {
				    WTUser member = (WTUser) enumer.nextElement();
				    WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
				    WTPrincipalReference pRef = WTPrincipalReference.newWTPrincipalReference(prin);
				    toUserId = member.getName();
				    Kogger.info(getClass(), "[startProcess] 자동차사업부_기획3 (메일만 받는 사람들) from/to : " + fromUserId + "/"
					    + toUserId);
				    Kogger.info(getClass(), "[startProcess] 메일 발송은 안함. 공지 메일 발송 기능 재정의 되었음.");
				    Hashtable contentHash = MailUtil.getMailContent("approval", (KETDevelopmentRequest) obj, toUserId);
				    String templateName = CommonUtil.getMailTemplateName(toUserId, "KETDevelopmentNoticeMail");
				    String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
				    Hashtable hash = null;
				    hash = MailUtil.prepareMailInfoHash(pRef, contents, fromUserId);
				    if (hash != null) {
					MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
				    }
				}
				Kogger.info(getClass(), "[startProcess] 자동차사업부 메일 발송 끝");
				// 개발의뢰서
				if ("D".equals(dr.getDevelopmentStep())) {
				    Kogger.info(getClass(), "[startProcess] 자동차사업부 개발의뢰서 결재선 작성 / 자동차사업부_기획1 의뢰접수");
				    String groupName = WFMProperties.getInstance().getString("wfm.drr1.group"); // 자동차사업부_기획1
				    WTGroup group1 = OrganizationServicesHelper.manager.getGroup(groupName);
				    Enumeration enumer1 = group1.members();
				    while (enumer1.hasMoreElements()) {
					WTUser member = (WTUser) enumer1.nextElement();
					WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
					TeamHelper.service.addRolePrincipalMap(Role.toRole("REQUEST_RECEIVER"), prin, team);
					hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
					hisAttr.setActivityName(reqRecipient_p);
					hisAttr.setCompletedDate(null);
					hisAttr.setDecision("");
					hisAttr.setAppMaster(master);
					WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(member
					        .getName());
					Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
					hisAttr.setOwner(owner);
					hisAttr.setSeqNum(seq++);
					createHistory(hisAttr);
				    }
				}
				// 검토의뢰서
				if ("R".equals(dr.getDevelopmentStep())) {
				    Kogger.info(getClass(), "[startProcess] 자동차사업부 검토의뢰서 결재선 작성 / 자동차사업부_기획2 의뢰접수");
				    String groupName = WFMProperties.getInstance().getString("wfm.drr2.group"); // 자동차사업부_기획2
				    WTGroup group2 = OrganizationServicesHelper.manager.getGroup(groupName);
				    Enumeration enumer2 = group2.members();
				    while (enumer2.hasMoreElements()) {
					WTUser member = (WTUser) enumer2.nextElement();
					WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
					TeamHelper.service.addRolePrincipalMap(Role.toRole("REQUEST_RECEIVER"), prin, team);
					hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
					hisAttr.setActivityName(reqRecipient_p);
					hisAttr.setCompletedDate(null);
					hisAttr.setDecision("");
					hisAttr.setAppMaster(master);
					WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(member
					        .getName());
					Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
					hisAttr.setOwner(owner);
					hisAttr.setSeqNum(seq++);
					createHistory(hisAttr);
				    }
				}
			    }
			} else {
			    // 이쪽으로 오는 경우가 있는 지는 모르지만 소스는 살려둠.
			    Kogger.info(getClass(), "Attribute1 ?????? null...");
			    Kogger.info(getClass(), "??? ??? ????????.???");
			    String toUserId = "";
			    String fromUserId = (String) ClassifyPBOUtil.extractPBOInfo(obj).get("creatorID"); // ??? ????????..
			    String mailGroup = WFMProperties.getInstance().getString("wfm.drr3.group"); // ???????? ???..
			    WTGroup group = OrganizationServicesHelper.manager.getGroup(mailGroup);
			    Kogger.info(getClass(), "mailGroup == " + mailGroup);
			    Enumeration enumer = group.members();
			    while (enumer.hasMoreElements()) {
				WTUser member = (WTUser) enumer.nextElement();
				WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
				WTPrincipalReference pRef = WTPrincipalReference.newWTPrincipalReference(prin);
				toUserId = member.getName();
				Kogger.info(getClass(), "toUserId" + toUserId);
				Kogger.info(getClass(), "fullName == " + member.getFullName());
				Kogger.info(getClass(), "fromUserId == " + fromUserId);
				Hashtable contentHash = MailUtil.getMailContent("approval", (KETDevelopmentRequest) obj, toUserId);
				/*
		                 * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
		                 */
				String templateName = CommonUtil.getMailTemplateName(toUserId, "KETDevelopmentNoticeMail");
				// String contents =
				// MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"KETDevelopmentNoticeMail.html");
				String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
				// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				Hashtable hash = MailUtil.prepareMailInfoHash(pRef, contents, fromUserId);
				if (hash != null) {
				    MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
				}
			    }
			    // ?????? ?????..
			    if ("D".equals(dr.getDevelopmentStep())) {
				String groupName = WFMProperties.getInstance().getString("wfm.drr1.group"); // ?????? ??? ??? ???
				WTGroup group1 = OrganizationServicesHelper.manager.getGroup(groupName);
				Enumeration enumer1 = group1.members();
				while (enumer1.hasMoreElements()) {
				    Kogger.info(getClass(), "???????????..");
				    WTUser member = (WTUser) enumer1.nextElement();
				    WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
				    TeamHelper.service.addRolePrincipalMap(Role.toRole("REQUEST_RECEIVER"), prin, team);
				    hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
				    hisAttr.setActivityName(reqRecipient_p);
				    hisAttr.setCompletedDate(null);
				    hisAttr.setDecision("");
				    hisAttr.setAppMaster(master);
				    WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(member.getName());
				    hisAttr.setOwner(owner);
				    hisAttr.setSeqNum(seq++);
				    createHistory(hisAttr);
				}
			    }
			    // ?????? ?????..
			    if ("R".equals(dr.getDevelopmentStep())) {
				String groupName = WFMProperties.getInstance().getString("wfm.drr2.group"); // ?????? ??? ??? ???
				WTGroup group2 = OrganizationServicesHelper.manager.getGroup(groupName);
				Enumeration enumer2 = group2.members();
				while (enumer2.hasMoreElements()) {
				    Kogger.info(getClass(), "???????????..");
				    WTUser member = (WTUser) enumer2.nextElement();
				    WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
				    TeamHelper.service.addRolePrincipalMap(Role.toRole("REQUEST_RECEIVER"), prin, team);
				    hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
				    hisAttr.setActivityName(reqRecipient_p);
				    hisAttr.setCompletedDate(null);
				    hisAttr.setDecision("");
				    hisAttr.setAppMaster(master);
				    WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(member.getName());
				    hisAttr.setOwner(owner);
				    hisAttr.setSeqNum(seq++);
				    createHistory(hisAttr);
				}
			    }
			}
		    }
		} else if (obj instanceof KETAnalysisRequestMaster) {
		    Kogger.info(getClass(), "[startProcess] 해석의뢰서 결재선 작성 / 해석의뢰 의뢰접수");
		    // codebase/e3ps/wfm.properties 파일의 정보
		    String groupName = WFMProperties.getInstance().getString("wfm.drr6.group"); // 해석의뢰접수 그룹
		    WTGroup group1 = OrganizationServicesHelper.manager.getGroup(groupName);
		    Enumeration enumer1 = group1.members();
		    while (enumer1.hasMoreElements()) {
			WTUser member = (WTUser) enumer1.nextElement();
			WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
			TeamHelper.service.addRolePrincipalMap(Role.toRole("REQUEST_RECEIVER"), prin, team);
			hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
			hisAttr.setActivityName(reqRecipient_p);
			hisAttr.setCompletedDate(null);
			hisAttr.setDecision("");
			hisAttr.setAppMaster(master);
			WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(member.getName());
			Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
			hisAttr.setOwner(owner);
			hisAttr.setSeqNum(seq++);
			createHistory(hisAttr);
		    }
		}
	    }

	    Kogger.info(getClass(), "[startProcess] 결재선 작성 / 수신");
	    for (int i = 0; i < receiver.size(); i++) {
		WTUser userRf = (WTUser) receiver.get(i);
		WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(userRf.getName());
		TeamHelper.service.addRolePrincipalMap(Role.toRole("RECIPIENT"), prin, team);
		hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
		hisAttr.setActivityName(receiver_p);
		hisAttr.setCompletedDate(null);
		hisAttr.setDecision("");
		hisAttr.setAppMaster(master);
		WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(userRf.getName());
		hisAttr.setOwner(owner);
		Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
		hisAttr.setSeqNum(seq++);
		hisAttr.setDegree(gateSeq);
		createHistory(hisAttr);
	    }
	    Kogger.info(getClass(), "[startProcess] 결재선 작성 / 참조");
	    for (int i = 0; i < reference.size(); i++) {
		WTUser userRf = (WTUser) reference.get(i);
		WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(userRf.getName());
		TeamHelper.service.addRolePrincipalMap(Role.toRole("REFERENCE"), prin, team);
		hisAttr = KETWfmApprovalHistory.newKETWfmApprovalHistory();
		hisAttr.setActivityName(reference_p);
		hisAttr.setCompletedDate(null);
		hisAttr.setDecision("");
		hisAttr.setAppMaster(master);
		WTPrincipalReference owner = OrganizationServicesHelper.manager.getPrincipalReference(userRf.getName());
		hisAttr.setOwner(owner);
		Kogger.info(getClass(), "[startProcess] " + seq + ". " + owner.getFullName());
		hisAttr.setSeqNum(seq++);
		hisAttr.setDegree(gateSeq);
		createHistory(hisAttr);
	    }

	    String workName = process.getName().substring(0, 7);
	    boolean isCan = workName.equals("KET_CAN");
	    // if (!isCan) {
	    // Kogger.info(getClass(), "[startProcess] PBO Change State : UNDERREVIEW");
	    // LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) obj, State.toState("UNDERREVIEW"));
	    // }
	    // master.setStartFlag(true);
	    // PersistenceServerHelper.manager.update(master);
	    // Kogger.info(getClass(), "[startProcess] ApprovalMaster Update startFlag set to true");

	    // pdata.setValue("start_flag", true);
	    // process.setContext(pdata);
	    // PersistenceServerHelper.manager.update(process);
	    // process = (WfProcess) PersistenceHelper.manager.refresh(process);

	    if (!master.isStartFlag()) {
		process = WfEngineHelper.service.startProcess(process, pdata, 1);
		process.setContext(pdata);
		PersistenceServerHelper.manager.update(process);
		PersistenceHelper.manager.refresh(process);
		Kogger.info(getClass(), "[startProcess] ApprovalMaster Update startFlag false : 임시저장");
		// master.setStartFlag(true);
		// PersistenceServerHelper.manager.update(master);
	    } else {

		Kogger.info(getClass(), "[startProcess] Process Variable start_flag set to true : 결재요청");
		if (isCan) {

		    /*
	             * Vector<KETWfmApprovalHistory> vec3 = WorkflowSearchHelper.manager.getApprovalHistory(master); for
	             * (KETWfmApprovalHistory history : vec3) { System.out.println(history.getActivityName() + " " + history.getSeqNum()); }
	             * reNumberingApprovalHistory(WorkflowSearchHelper.manager.getApprovalHistory(master)); Vector<KETWfmApprovalHistory>
	             * vec4 = WorkflowSearchHelper.manager.getApprovalHistory(master); for (KETWfmApprovalHistory history : vec4) {
	             * System.out.println(history.getActivityName() + " " + history.getSeqNum()); }
	             */
		    /*
	             * pdata.setValue("start_flag", true); process.setContext(pdata); WfEngineHelper.service.startProcess(process, pdata,
	             * 1); PersistenceServerHelper.manager.update(process); PersistenceHelper.manager.refresh(process);
	             */

		    // Vector vec1 = new Vector();
		    // vec1.add(WfTransition.COMPLETE);
		    // vec1.add(WfTransition.START);
		    // PMServer.completeActivity(wt.fc.PersistenceHelper.getObjectIdentifier( (wt.fc.Persistable)process)) , vec1);
		    // PersistenceHelper.manager.refresh(process);
		    // COMPLETED
		    /*
	             * qspec.appendWhere(new wt.query.SearchCondition(wt.workflow.engine.WfActivity.class, "parentProcessRef.key.id" , "=",
	             * wt.fc.PersistenceHelper.getObjectIdentifier((wt.fc.Persistable)process))); wt.fc.QueryResult
	             * qr1=wt.fc.PersistenceHelper.manager.find(qspec);
	             */

		    /*
	             * WfActivity activity = null; QuerySpec qspec = new wt.query.QuerySpec(WfActivity.class);
	             * 
	             * qspec.appendWhere( new SearchCondition(WfActivity.class, "parentProcessRef.key" , "=",
	             * wt.fc.PersistenceHelper.getObjectIdentifier((wt.fc.Persistable)process)), new int[] { 0 }); QueryResult qr1 =
	             * PersistenceHelper.manager.find(qspec);
	             * 
	             * while(qr1.hasMoreElements()) {
	             * 
	             * //WfEngineHelper.service.complete(activity,vec1); activity=(WfActivity)qr1.nextElement(); QueryResult
	             * qr2=wt.fc.PersistenceHelper
	             * .manager.navigate(activity,"assignment",wt.workflow.work.ActivityAssignmentLink.class,true);
	             * while(qr2.hasMoreElements()) { Vector vector = new Vector(); WfAssignedActivity wfassignedactivity1 = null;
	             * WfAssignment assignment=(WfAssignment)qr2.nextElement(); QueryResult
	             * qr3=wt.fc.PersistenceHelper.manager.navigate(assignment,"workItem",wt.workflow.work.WorkItemLink.class,true);
	             * while(qr3.hasMoreElements()) { WorkItem workitem=(wt.workflow.work.WorkItem)qr3.nextElement();
	             * if(workitem.getStatus().toString().equals("POTENTIAL") || workitem.getStatus().toString().equals("ACCEPTED") ) {
	             * wfassignedactivity1 = (wt.workflow.work.WfAssignedActivity)workitem.getSource().getObject();
	             * wt.fc.PersistenceHelper.manager.delete(workitem); vector = wfassignedactivity1.getAllEvents(); } if
	             * (wfassignedactivity1 != null) { WfEngineHelper.service.complete(wfassignedactivity1,vector); } } }
	             * 
	             * }
	             */

		    // PMServer.completeActivity(CommonUtil.getOIDString(activity), vec1);
		    // PersistenceHelper.manager.refresh(process);
		    // WfEngineHelper.service.complete(wt.fc.PersistenceHelper.getObjectIdentifier((wt.fc.Persistable)process), vec1);

		    /*
	             * String result = null; boolean terminate=false;
	             * 
	             * String terminateVote="Reject"; wt.fc.collections.WTArrayList voting = (wt.fc.collections.WTArrayList)
	             * wt.workflow.engine.WfEngineHelper.service.getVotingEvents(process, null, null, null); if (voting != null &&
	             * !voting.isEmpty()) { java.util.Iterator voteIterator = voting.iterator(); while(!terminate &&
	             * voteIterator.hasNext()){ Object anItem = ((wt.fc.ObjectReference)voteIterator.next()).getObject(); if (anItem
	             * instanceof wt.workflow.engine.WfVotingEventAudit){ wt.workflow.engine.WfVotingEventAudit audit =
	             * (wt.workflow.engine.WfVotingEventAudit)anItem; Vector votes = audit.getEventList(); java.util.Enumeration
	             * votesenum=votes.elements(); while(!terminate && votesenum.hasMoreElements()){ java.lang.String vote =
	             * (java.lang.String) votesenum.nextElement(); if(terminateVote.equals(vote)){ result=terminateVote; break; } } } } }
	             */

		}
		pdata.setValue("start_flag", true);
		process.setContext(pdata);

		PersistenceServerHelper.manager.update(process);
		PersistenceHelper.manager.refresh(process);

	    }
	    Kogger.info(getClass(), "[startProcess] WfProcess : " + process.toString());

	    QueryResult qr = PersistenceHelper.manager.navigate(process, KETWfmMasterProcessLink.APP_MASTER_ROLE,
		    KETWfmMasterProcessLink.class, false);
	    if (qr != null && qr.size() > 0) {
		Object object = qr.nextElement();
		Kogger.info(getClass(), "[startProcess] exist KETWfmMasterProcessLink : " + object.getClass().getName());
	    } else {
		KETWfmMasterProcessLink link = KETWfmMasterProcessLink.newKETWfmMasterProcessLink(process, master);
		link = (KETWfmMasterProcessLink) PersistenceHelper.manager.save(link);
		Kogger.info(getClass(), "[startProcess] Create KETWfmMasterProcessLink : " + link.toString());
	    }

	    trx.commit();
	    Kogger.info(getClass(), "[startProcess] trx.commit");

	    if (!isCan && master.isStartFlag()) {
		Kogger.info(getClass(), "[startProcess] PBO Change State : UNDERREVIEW");
		LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) obj, State.toState("UNDERREVIEW"));
	    }
	    Kogger.info(getClass(), "[startProcess] INVOKE END -------------------------------------------------------");
	    start = true;
	    trx = null;
	} catch (Exception e) {
	    Phase currentPhase = LifeCycleHelper.service.getCurrentPhase((LifeCycleManaged) obj);
	    if (currentPhase != null) {
		PhaseTemplate phaseTemp = (PhaseTemplate) currentPhase.getPhaseTemplateId().getObject();
		if (phaseTemp.getPhaseState().equals(State.toState("INWORK")) || phaseTemp.getPhaseState().equals(State.toState("REWORK"))
		        || phaseTemp.getPhaseState().equals(State.toState("ACTIVITYCOMPLETED"))
		        || phaseTemp.getPhaseState().equals(State.toState("PLANCHANGE"))) {
		    start = false;
		}
	    }
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);
	    if (trx != null) {
		trx.rollback();
	    }
	}
	return start;
    }

    @Override
    public void completeActivity(Hashtable aHash) throws WTException {

	String workOid = aHash.get("item").toString();
	String condition = StringUtil.checkNull(aHash.get("condition").toString());
	String comment = aHash.get("comment").toString();
	String masterOid = aHash.get("master").toString();
	WorkItem item = (WorkItem) CommonUtil.getObject(workOid);
	String accept_p = WFMProperties.getInstance().getString("wfm.accept");
	String conAccept_p = WFMProperties.getInstance().getString("wfm.conaccept");
	String reject_p = WFMProperties.getInstance().getString("wfm.reject");
	String cancel_p = WFMProperties.getInstance().getString("wfm.cancel");
	String confirm_p = WFMProperties.getInstance().getString("wfm.confirm");

	Vector vec = new Vector();
	Transaction trx = new Transaction();

	Timestamp completeDate = DateUtil.getCurrentTimestamp();
	boolean isDelegate = false;
	Kogger.info(getClass(), "[completeActivity] condition : " + condition);
	// 위임여부 체크 (현재는 사용안함)
	if (item.getOrigOwner().getObject() != null)
	    isDelegate = true;
	try {
	    trx.start();
	    if ("taskComplete".equals(condition)) {
		Kogger.info(getClass(), "[completeActivity] TODO Action");
		Object pbo = item.getPrimaryBusinessObject().getObject();
		if (pbo instanceof KETMoldChangeActivity) {
		    KETMoldChangeActivity eca = (KETMoldChangeActivity) pbo;
		    eca.setCompleteDate(completeDate);
		    // Added by MJOH, 2011-03-30
		    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    eca.setActivityStatus("WORKCOMPLETED");
		    PersistenceHelper.manager.save(eca);
		    eca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(eca);
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("WORKCOMPLETED"));
		    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		} else if (pbo instanceof KETProdChangeActivity) {
		    KETProdChangeActivity eca = (KETProdChangeActivity) pbo;
		    eca.setCompleteDate(completeDate);
		    // Added by MJOH, 2011-03-30
		    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    eca.setActivityStatus("WORKCOMPLETED");
		    PersistenceHelper.manager.save(eca);
		    eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("WORKCOMPLETED"));
		    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		}
	    } else {
		boolean riskCheck = false;
		try {
		    riskCheck = (boolean) aHash.get("riskCheck");
		} catch (Exception e) {
		    riskCheck = false;
		}

		KETWfmApprovalMaster master = (KETWfmApprovalMaster) CommonUtil.getObject(masterOid);
		Vector historyVec = null;

		if (master.getPbo() instanceof GateAssessResult) {
		    int gateDegree = 0;
		    GateAssessResult gateAssRslt = (GateAssessResult) master.getPbo();
		    E3PSTask gate = ProjectTaskCompHelper.service.getTask(gateAssRslt);
		    gateDegree = ProjectTaskCompHelper.service.getMaxGateDegree(CommonUtil.getOIDString(gate));

		    historyVec = WorkflowSearchHelper.manager.getApprovalHistory(master, gateDegree);
		} else {
		    historyVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
		}

		Collections.sort(historyVec, ComparatorUtil.APPROVALLINESORT);
		KETWfmApprovalHistory history = null;
		Object pbo = item.getPrimaryBusinessObject().getObject();
		WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
		String itemOwner = item.getOwnership().toString();
		// if (isDelegate) {
		// itemOwner = item.getOrigOwner().toString();
		// }
		Kogger.info(getClass(), "[completeActivity] itemOwner : " + itemOwner);
		for (int i = 0; i < historyVec.size(); i++) {
		    history = (KETWfmApprovalHistory) historyVec.get(i);
		    if (history.getCompletedDate() == null && history.getOwner().toString().equals(itemOwner)) {
			Kogger.info(getClass(), "[completeActivity]" + history.getSeqNum() + "." + history.getActivityName() + "/["
			        + history.getOwner().toString() + "]/" + itemOwner);
			if ("accept".equals(condition) || "conAccept".equals(condition)) {
			    Kogger.info(getClass(), "[completeActivity] 승인 Action");
			    if ("conAccept".equals(condition)) {
				history.setDecision(conAccept_p);
				history.setConditionalAccept(true);
			    } else {
				history.setDecision(accept_p);
			    }
			    history.setCompletedDate(completeDate);
			    history.setComments(comment);
			    history.setRiskCheck(riskCheck);
			    if (isDelegate) {
				history.setDelegate(item.getOwnership().getOwner().getFullName());
			    }
			    vec.addElement("accept");
			    history = (KETWfmApprovalHistory) PersistenceHelper.manager.modify(history);
			    if (pbo instanceof KETDevelopmentRequest) {
				if ("의뢰서접수".equals(activity.getName())) {
				    // 개발검토의뢰서는 접수/반려/취소 시 다른 접수자의 결재이력 삭제함.
				    Vector historyVec2 = WorkflowSearchHelper.manager.getApprovalHistory(master);
				    for (Object object : historyVec2) {
					KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
					if (KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(delHistory.getActivityName())
					        && delHistory.getCompletedDate() == null) {
					    if (CommonUtil.getOIDLongValue(history) != CommonUtil.getOIDLongValue(delHistory)) {
						Kogger.info(getClass(),
						        "[completeActivity][KETDevelopmentRequest Action : 접수] KETDevelopmentRequest 타 의뢰접수자 결재이력 삭제 : "
						                + delHistory.getOwner().getFullName());
						PersistenceHelper.manager.delete(delHistory);
					    }
					}
				    }
				    // 결재이력 전체 시퀀스 번호 정렬.
				    reNumberingApprovalHistory(WorkflowSearchHelper.manager.getApprovalHistory(master));
				    // 승인시 KETWfmApprovalMaster에 completedate 입력
				    master.setCompletedDate(completeDate);
				    master = (KETWfmApprovalMaster) PersistenceHelper.manager.modify(master);
				    Kogger.info(getClass(), "[completeActivity] KETWfmApprovalMaster : " + masterOid + "/ condition : "
					    + condition + " / setCompletedDate : " + completeDate.toString());
				}
			    }
			    if (pbo instanceof KETAnalysisRequestMaster) {
				if ("의뢰서접수".equals(activity.getName())) {
				    // 개발검토의뢰서는 접수/반려/취소 시 다른 접수자의 결재이력 삭제함.
				    Vector historyVec2 = WorkflowSearchHelper.manager.getApprovalHistory(master);
				    for (Object object : historyVec2) {
					KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
					if (KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(delHistory.getActivityName())
					        && delHistory.getCompletedDate() == null) {
					    if (CommonUtil.getOIDLongValue(history) != CommonUtil.getOIDLongValue(delHistory)) {
						Kogger.info(getClass(),
						        "[completeActivity][KETAnalysisRequestMaster Action : 접수] KETAnalysisRequestMaster 타 의뢰접수자 결재이력 삭제 : "
						                + delHistory.getOwner().getFullName());
						PersistenceHelper.manager.delete(delHistory);
					    }
					}
				    }
				    // 결재이력 전체 시퀀스 번호 정렬.
				    reNumberingApprovalHistory(WorkflowSearchHelper.manager.getApprovalHistory(master));
				    // 승인시 KETWfmApprovalMaster에 completedate 입력
				    master.setCompletedDate(completeDate);
				    master = (KETWfmApprovalMaster) PersistenceHelper.manager.modify(master);
				    Kogger.info(getClass(), "[completeActivity] KETWfmApprovalMaster : " + masterOid + "/ condition : "
					    + condition + " / setCompletedDate : " + completeDate.toString());
				}
			    }
			    if (history.isLast() && !(pbo instanceof KETDevelopmentRequest || pbo instanceof KETAnalysisRequestMaster)) {
				// 승인시 KETWfmApprovalMaster에 completedate 입력
				master.setCompletedDate(completeDate);
				master = (KETWfmApprovalMaster) PersistenceHelper.manager.modify(master);
				Kogger.info(getClass(), "[completeActivity] KETWfmApprovalMaster : " + masterOid + "/ condition : "
				        + condition + " / setCompletedDate : " + completeDate.toString());

				if (pbo instanceof KETBomHeader) {
				    // KETBomHeader ERP I/F 결과 메일 발송부분
				    String bomOid = KETObjectUtil.getIda2a2(pbo.toString());
				    Kogger.info(getClass(), "bomOid === " + bomOid);
				    boolean infResult = KETBomHelper.service.getBomInterface(bomOid);
				    Kogger.info(getClass(), "[completeActivity][PRECOMPLETE Action] KETBomHeader " + bomOid
					    + " / interface result : " + infResult);
				    if (!infResult && mailEnable) {
					Kogger.info(getClass(), "[completeActivity][PRECOMPLETE Action] KETBomHeader " + bomOid
					        + " / interface Error mail send ");
					KETBomHeader bomHeader = (KETBomHeader) pbo;
					WTPrincipalReference toUser = bomHeader.getCreator();
					Hashtable contentHash = MailUtil.getMailContent("infFail", bomHeader, toUser.getName());
					/*
			                 * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
			                 */
					String templateName = CommonUtil.getMailTemplateName(toUser.getName(), "InterfaceFailNotice");
					// String contents =
					// MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"InterfaceFailNotice.html");
					String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
					// //////////////////////////////////////////////////////////////////////////////////////////////////////////
					Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents);
					if (hash != null)
					    MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
				    }
				} else if (pbo instanceof KETProjectDocument) {
				    // 개발산출문서의 경우 문서에 승인정보 업데이트
				    Kogger.info(getClass(),
					    "[completeActivity][PRECOMPLETE Action] KETProjectDocument approval comment/result update ");
				    KETProjectDocument projDoc = (KETProjectDocument) pbo;
				    projDoc.setLastApprovalComment(history.getComments());
				    projDoc.setApprovalResult(history.getDecision());
				    PersistenceServerHelper.manager.update(projDoc);
				}

				if (pbo instanceof KETProdChangeOrder) {
				    // Kogger.info(getClass(), "[completeActivity][PRECOMPLETE Action] Qms Interface ::: " +
				    // item.getPrimaryBusinessObject().getObject().toString());
				    // QmsCtl qms = new QmsCtl();
				    // qms.InsertPLMEcoPjt(item.getPrimaryBusinessObject().getObject().toString(), "C");
				}

				// PBO별 승인 메일 발송 부분
				if (mailEnable) {
				    // String fromUserID = (String)ClassifyPBOUtil.extractPBOInfo(pbo).get( "creatorID" );
				    Team team = TeamHelper.service.getTeam((TeamManaged) pbo);
				    HashMap teamMap = TeamHelper.service.findAllParticipantsByRole(team);
				    Kogger.info(getClass(), "team ==  " + team.getName());
				    ArrayList refMemberList = (ArrayList) teamMap.get(Role.toRole("REFERENCE"));

				    WTUser fromUser = KETObjectUtil.getUser((String) ClassifyPBOUtil.extractPBOInfo(pbo).get("creatorID"));
				    WTUser admin = (WTUser) SessionHelper.manager.getAdministrator();
				    String fromUserID = fromUser.toString();
				    if (fromUser.isDisabled()) {
					fromUserID = admin.toString();
				    }
				    People checkP = null;
				    if (refMemberList != null) {
					for (int memberIdx = 0; memberIdx < refMemberList.size(); memberIdx++) {
					    WTPrincipalReference refUser = (WTPrincipalReference) refMemberList.get(memberIdx);
					    checkP = PeopleHelper.manager.getPeople(refUser.getName());
					    if (!checkP.isIsDisable()) {
						String toUserID = refUser.getName();
						Hashtable contentHash = MailUtil.getMailContent("reference", pbo, toUserID);
						/*
			                         * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
			                         */
						String templateName = CommonUtil.getMailTemplateName(toUserID, "ApprovalNoticeMail");
						// String contents =
						// MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"ApprovalNoticeMail.html");
						String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash,
						        templateName);
						// /////////////////////////////////////////////////////////////////////////////////////////////////////////
						Hashtable hash = MailUtil.prepareMailInfoHash(refUser, contents, fromUserID);
						if (hash != null) {
						    MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
						}
					    }
					}
				    }
				    // ?????? ?????? ?????? ?????## 2012.03.23 ???????? LGCNS
				    Kogger.info(getClass(), ":::::::::::::::::::::: APPROVAL MAIL START :::::::::::::::::::::::::");
				    String contents = "";
				    String approveId = "";
				    WTUser ToUser = KETObjectUtil.getUser((String) ClassifyPBOUtil.extractPBOInfo(pbo).get("creatorID"));
				    checkP = PeopleHelper.manager.getPeople(ToUser.getName());
				    if (!checkP.isIsDisable()) {
					String toUserID = (String) ClassifyPBOUtil.extractPBOInfo(pbo).get("creatorID");
					KETWfmApprovalMaster mm = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
					// mm.get
					Vector vec2 = WorkflowSearchHelper.manager.getApprovalLine(mm);
					for (int v = 0; v < vec2.size(); v++) {
					    KETWfmApprovalLine line = (KETWfmApprovalLine) vec2.get(v);
					    People checkP2 = PeopleHelper.manager.getPeople(line.getApproverID());
					    Kogger.info(getClass(), ">>>>>>> Approval User ID == " + line.getApproverID());
					    Kogger.info(getClass(), ">>>>>>> Approval User isDisable?? == " + checkP2.isIsDisable());
					    if (!checkP2.isIsDisable()) {
						approveId = line.getApproverID().toString();
						WTPrincipalReference toUser = (WTPrincipalReference) wt.org.OrganizationServicesHelper.manager
						        .getPrincipalReference(line.getApproverID());
						Hashtable contentHash = MailUtil.getMailContent("approve_reference", pbo, approveId);
						/*
			                         * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
			                         */
						String templateName = CommonUtil.getMailTemplateName(approveId, "ApprovalNoticeMail");
						// contents =
						// MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"ApprovalNoticeMail.html");
						contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
						// //////////////////////////////////////////////////////////////////////////////////////////////////
						Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents, fromUserID);
						if (hash != null) {
						    MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
						}
					    }
					}
					// ?????????????? ?????## shkim e3ps
					WTPrincipalReference toUser = (WTPrincipalReference) wt.org.OrganizationServicesHelper.manager
					        .getPrincipalReference(toUserID);
					Hashtable contentHash = MailUtil.getMailContent("approve_reference", pbo, toUserID);
					/*
			                 * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
			                 */
					String templateName = CommonUtil.getMailTemplateName(toUserID, "ApprovalNoticeMail");
					// contents =
					// MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"ApprovalNoticeMail.html");
					contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
					// ///////////////////////////////////////////////////////////////////////////////////////////////////
					Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents, fromUserID);
					if (hash != null) {
					    MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
					}
				    }
				    Kogger.info(getClass(), ":::::::::::::::::::::: APPROVAL MAIL END :::::::::::::::::::::::::");
				}
			    }

			    if (pbo instanceof E3PSProject) {
				E3PSProject project = (E3PSProject) pbo;

				QueryResult qr = ProjectHelper.getCancelProject(project);

				if (qr.size() > 0 && project.getLifeCycleState().toString().equals("CANCELING")) {
				    CancelProject cpProject = null;
				    Object[] ProjectObj = null;
				    while (qr.hasMoreElements()) {
					ProjectObj = (Object[]) qr.nextElement();
					ProjectCancelLink ps = (ProjectCancelLink) ProjectObj[0];
					cpProject = ps.getCancle();
				    }
				    cpProject.setCancelType("취소완료");
				    PersistenceHelper.manager.modify(cpProject);
				}
			    }

			    if (pbo instanceof KETSalesProject) {// 영업 프로젝트 상태 코드 update
				KETSalesProject salesPjt = (KETSalesProject) pbo;
				salesPjt.setSalesState(salesPjt.getAfterSalesState());
				salesPjt.setApproveDate(DateUtil.getCurrentTimestamp());
				// Iteration 증가 없이 속성 수정
				PersistenceServerHelper.manager.update(salesPjt);

				salesPjt = (KETSalesProject) PersistenceHelper.manager.refresh(salesPjt);
			    }

			    if (pbo instanceof KETInvestMaster) {// 고객 투자비 회수 관리 완료
				KETInvestMaster im = (KETInvestMaster) pbo;
				im.setInvestState(InvestUtil.COMPLETE);
				im.setCompleteDate(DateUtil.getCurrentTimestamp());
				PersistenceServerHelper.manager.update(im);
				im = (KETInvestMaster) PersistenceHelper.manager.refresh(im);
			    }

			    break;
			} else if ("reject".equals(condition)) { // 반려 시 처리 부분
			    Kogger.info(getClass(), "[completeActivity] 반려 Action");
			    history.setDecision(reject_p);
			    history.setCompletedDate(completeDate);
			    history.setComments(comment);
			    history.setRiskCheck(riskCheck);
			    if (isDelegate)
				history.setDelegate(item.getOwnership().getOwner().getFullName());
			    vec.addElement("reject");
			    history = (KETWfmApprovalHistory) PersistenceHelper.manager.modify(history);
			    if (pbo instanceof KETProjectDocument) {
				KETProjectDocument projDoc = (KETProjectDocument) pbo;
				projDoc.setLastApprovalComment(history.getComments());
				projDoc.setApprovalResult(history.getDecision());
				PersistenceServerHelper.manager.update(projDoc);
			    }

			    if (pbo instanceof E3PSProject) {
				E3PSProject project = (E3PSProject) pbo;

				QueryResult qr = ProjectHelper.getCancelProject(project);

				if (qr.size() > 0 && project.getLifeCycleState().toString().equals("CANCELING")) {
				    CancelProject cpProject = null;
				    Object[] ProjectObj = null;
				    while (qr.hasMoreElements()) {
					ProjectObj = (Object[]) qr.nextElement();
					ProjectCancelLink ps = (ProjectCancelLink) ProjectObj[0];
					cpProject = ps.getCancle();
				    }
				    cpProject.setCancelType("취소요청");
				    PersistenceHelper.manager.modify(cpProject);
				}
			    }
			    // 반려 시 이후 결재선 / 이력 정보 삭제
			    // 합의에는 반려의사만 표시되므로 삭제하지 않는다.
			    if (!KETWfmApprovalHistoryDTO.DISCUSS.equals(history.getActivityName())) {
				int delSeq = history.getSeqNum();
				for (Object object : historyVec) {
				    KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
				    if (delHistory.getSeqNum() > delSeq) {
					Kogger.info(getClass(),
					        "[completeActivity][반려] " + delHistory.getSeqNum() + "." + delHistory.getActivityName()
					                + "/" + delHistory.getOwner().getFullName());
					PersistenceHelper.manager.delete(delHistory);
				    }
				}
				if (pbo instanceof KETDevelopmentRequest) {
				    // WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
				    if ("의뢰서접수".equals(activity.getName())) {
					// 개발검토의뢰서는 접수/반려/취소 시 다른 접수자의 결재이력 삭제함.
					Vector historyVec2 = WorkflowSearchHelper.manager.getApprovalHistory(master);
					for (Object object : historyVec2) {
					    KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
					    if (KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(delHistory.getActivityName())
						    && delHistory.getCompletedDate() == null) {
						if (CommonUtil.getOIDLongValue(history) != CommonUtil.getOIDLongValue(delHistory)) {
						    Kogger.info(getClass(),
							    "[completeActivity][KETDevelopmentRequest Action : 반려] KETDevelopmentRequest 타 의뢰접수자 결재이력 삭제 : "
							            + delHistory.getOwner().getFullName());
						    PersistenceHelper.manager.delete(delHistory);
						}
					    }
					}
					// 결재이력 전체 시퀀스 번호 정렬.
					reNumberingApprovalHistory(WorkflowSearchHelper.manager.getApprovalHistory(master));
					// 반려시 COMPLETEDDATE를 초기화
					master.setCompletedDate(null);
					master = (KETWfmApprovalMaster) PersistenceHelper.manager.modify(master);
				    }
				}
				if (pbo instanceof KETAnalysisRequestMaster) {
				    // WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
				    if ("의뢰서접수".equals(activity.getName())) {
					// 개발검토의뢰서는 접수/반려/취소 시 다른 접수자의 결재이력 삭제함.
					Vector historyVec2 = WorkflowSearchHelper.manager.getApprovalHistory(master);
					for (Object object : historyVec2) {
					    KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
					    if (KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(delHistory.getActivityName())
						    && delHistory.getCompletedDate() == null) {
						if (CommonUtil.getOIDLongValue(history) != CommonUtil.getOIDLongValue(delHistory)) {
						    Kogger.info(getClass(),
							    "[completeActivity][KETAnalysisRequestMaster Action : 반려] KETAnalysisRequestMaster 타 의뢰접수자 결재이력 삭제 : "
							            + delHistory.getOwner().getFullName());
						    PersistenceHelper.manager.delete(delHistory);
						}
					    }
					}
					// 결재이력 전체 시퀀스 번호 정렬.
					reNumberingApprovalHistory(WorkflowSearchHelper.manager.getApprovalHistory(master));
					// 반려시 COMPLETEDDATE를 초기화
					master.setCompletedDate(null);
					master = (KETWfmApprovalMaster) PersistenceHelper.manager.modify(master);
				    }
				}
			    }
			    break;
			} else if ("cancel".equals(condition)) { // 취소 시 처리 부분
			    Kogger.info(getClass(), "[completeActivity] 취소 Action");
			    history.setDecision(cancel_p);
			    history.setCompletedDate(completeDate);
			    history.setComments(comment);
			    history.setRiskCheck(riskCheck);
			    if (isDelegate)
				history.setDelegate(item.getOwnership().getOwner().getFullName());
			    vec.addElement("cancel");
			    PersistenceHelper.manager.modify(history);
			    // 취소 시 이후 결재선 / 이력 정보 삭제
			    int delSeq = history.getSeqNum();
			    for (Object object : historyVec) {
				KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
				if (delHistory.getSeqNum() > delSeq)
				    PersistenceHelper.manager.delete(delHistory);
			    }
			    if (pbo instanceof KETDevelopmentRequest) {
				if ("의뢰서접수".equals(activity.getName())) {
				    // 개발검토의뢰서는 접수/반려/취소 시 다른 접수자의 결재이력 삭제함.
				    Vector historyVec2 = WorkflowSearchHelper.manager.getApprovalHistory(master);
				    for (Object object : historyVec2) {
					KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
					if (KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(delHistory.getActivityName())
					        && delHistory.getCompletedDate() == null) {
					    if (CommonUtil.getOIDLongValue(history) != CommonUtil.getOIDLongValue(delHistory)) {
						Kogger.info(getClass(),
						        "[completeActivity][KETDevelopmentRequest Action : 취소] KETDevelopmentRequest 타 의뢰접수자 결재이력 삭제 : "
						                + delHistory.getOwner().getFullName());
						PersistenceHelper.manager.delete(delHistory);
					    }
					}
				    }
				    // 결재이력 전체 시퀀스 번호 정렬.
				    reNumberingApprovalHistory(WorkflowSearchHelper.manager.getApprovalHistory(master));
				}
			    }
			    if (pbo instanceof KETAnalysisRequestMaster) {
				if ("의뢰서접수".equals(activity.getName())) {
				    // 개발검토의뢰서는 접수/반려/취소 시 다른 접수자의 결재이력 삭제함.
				    Vector historyVec2 = WorkflowSearchHelper.manager.getApprovalHistory(master);
				    for (Object object : historyVec2) {
					KETWfmApprovalHistory delHistory = (KETWfmApprovalHistory) object;
					if (KETWfmApprovalHistoryDTO.REQRECIPIENT.equals(delHistory.getActivityName())
					        && delHistory.getCompletedDate() == null) {
					    if (CommonUtil.getOIDLongValue(history) != CommonUtil.getOIDLongValue(delHistory)) {
						Kogger.info(getClass(),
						        "[completeActivity][KETAnalysisRequestMaster Action : 취소] KETAnalysisRequestMaster 타 의뢰접수자 결재이력 삭제 : "
						                + delHistory.getOwner().getFullName());
						PersistenceHelper.manager.delete(delHistory);
					    }
					}
				    }
				    // 결재이력 전체 시퀀스 번호 정렬.
				    reNumberingApprovalHistory(WorkflowSearchHelper.manager.getApprovalHistory(master));
				}
			    }
			    // 취소 시 KETWfmApprovalMaster에 completedate 입력
			    master.setCompletedDate(completeDate);
			    master = (KETWfmApprovalMaster) PersistenceHelper.manager.modify(master);
			    Kogger.info(getClass(), "[completeActivity] KETWfmApprovalMaster : " + masterOid + "/ condition : " + condition
				    + " / setCompletedDate : " + completeDate.toString());
			    break;
			} else if ("confirm".equals(condition)) { // 확인 시 처리 부분
			    Kogger.info(getClass(), "[completeActivity] 확인 Action");
			    history.setDecision(confirm_p);
			    history.setCompletedDate(completeDate);
			    if (isDelegate)
				history.setDelegate(item.getOwnership().getOwner().getFullName());
			    vec.addElement("confirm");
			    PersistenceHelper.manager.modify(history);
			    // 반려확인에서 재작업 버튼 누를 경우 마스터의 startFlag를 false로 바꾸고, completedDate를 초기화 한다.
			    if (KETWfmApprovalHistoryDTO.CONFIRM_REJECT.equals(activity.getName())) {
				master.setStartFlag(false);
				master.setCompletedDate(null);
				master = (KETWfmApprovalMaster) PersistenceHelper.manager.modify(master);
			    }
			    break;
			}
		    }
		}
	    }
	    WorkflowHelper.service.workComplete(item, item.getOwnership().getOwner(), vec);
	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null)
		trx.rollback();
	}
    }

    @Override
    public void updateProcess(String itemOid, String masterOid) throws WTException {
	Transaction trx = null;

	SessionContext sessioncontext = SessionContext.newContext();

	try {
	    trx = new Transaction();
	    trx.start();

	    KETWfmApprovalMaster master = (KETWfmApprovalMaster) CommonUtil.getObject(masterOid);
	    WorkItem item = (WorkItem) CommonUtil.getObject(itemOid);
	    Persistable target = master.getBusinessobjectRef().getObject();

	    if (target == null) {
		target = master.getPbo();
	    }

	    SessionHelper.manager.setAdministrator();

	    Team team = (Team) ClassifyPBOUtil.extractPBOInfo(target).get("team");
	    HashMap teamMap = TeamHelper.service.findAllParticipantsByRole(team);
	    ArrayList refMemberList = (ArrayList) teamMap.get(Role.toRole("REFERENCE"));

	    if (refMemberList != null) {
		TeamHelper.service.deleteRole(Role.toRole("REFERENCE"), team);
	    }

	    refMemberList = (ArrayList) teamMap.get(Role.toRole("RECIPIENT"));

	    if (refMemberList != null) {
		TeamHelper.service.deleteRole(Role.toRole("RECIPIENT"), team);
	    }

	    // [START][KET PLM 고도화 프로젝트] 반려확인 재작업 시 관련 결재 이력 삭제 하지 않도록 수정, 2014. 8. 18. Jason, Han
	    // Vector historyVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	    // for (int i = 0; i < historyVec.size(); i++) {
	    // KETWfmApprovalHistory history = (KETWfmApprovalHistory) historyVec.get(i);
	    // PersistenceHelper.manager.delete(history);
	    // }
	    // [END][KET PLM 고도화 프로젝트] 반려확인 재작업 시 관련 결재 이력 삭제 하지 않도록 수정, 2014. 8. 18. Jason, Han

	    // 반려확인 - 재작업 시 KETWfmApprovalMaster의 completeDate를 삭제한다.
	    master.setCompletedDate(null);
	    master = (KETWfmApprovalMaster) PersistenceHelper.manager.save(master);

	    Vector routingEvent = new Vector();

	    if (!item.isComplete()) {
		WorkflowHelper.service.workComplete(item, item.getOwnership().getOwner(), routingEvent);
	    }

	    WfProcess process = WorkflowSearchHelper.manager.getProcess(master);
	    WorkflowSearchHelper.manager.removeWfAssignedActivity(process);
	    process.resetValues();
	    process = WfEngineServerHelper.service.setPrimaryBusinessObject(process, (WTObject) target);
	    PersistenceHelper.manager.modify(process);

	    if (target instanceof KETBomHeader) {
		if (VERBOSE)
		    Kogger.info(getClass(), "###############??? BOM ????????  ???!!");

		KETBomHelper.service.updateEndWorkingFlagNew(((KETBomHeader) target).getNewBOMCode(), "1");

		if (VERBOSE)
		    Kogger.info(getClass(), "###############??? BOM ????????   ???!!");
	    } else if (target instanceof KETMoldChangeOrder) {
		if (VERBOSE)
		    Kogger.info(getClass(), "###############??? ??? BOM ????????   ???!!");

		KETBomHelper.service.updateEndWorkingFlag(((KETMoldChangeOrder) target).getEcoId(), "1");

		if (VERBOSE)
		    Kogger.info(getClass(), "###############??? ??? BOM ????????  ???!!");
	    } else if (target instanceof KETProdChangeOrder) {
		if (VERBOSE)
		    Kogger.info(getClass(), "###############??? ??? BOM ????????  ???!!");

		KETBomHelper.service.updateEndWorkingFlag(((KETProdChangeOrder) target).getEcoId(), "1");

		if (VERBOSE)
		    Kogger.info(getClass(), "###############??? ??? BOM ????????   ???!!");
	    } else if (target instanceof E3PSProject) {// 취소중 상태에서 반려 후 재작업처리했을 때 reassign 처리

		String lifeCycleName = "KET_PRODUCT_PMS_LC";

		if (target instanceof MoldProject) {
		    lifeCycleName = "KET_MOLD_PMS_LC";
		}

		if (process != null) {
		    WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		    boolean isCanWF = false;
		    if (template != null)
			isCanWF = "KET_CAN_WF_NEW".equals(template.getName());
		    if (isCanWF) {

			// WorkflowSearchHelper.manager.deleteWorkItem(target);
			E3PSProject project = (E3PSProject) target;

			State reassignState = State.toState("CANCELING");
			LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference(lifeCycleName,
			        WTContainerHelper.service.getExchangeRef());
			project = (E3PSProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
			Kogger.info(getClass(), "[updateProcess] ProductProject reassign KET_PRODUCT_PMS_LC");
			project = (E3PSProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
			Kogger.info(getClass(), "[updateProcess] ProductProject setLifeCycleState PROGRESS");
		    }
		}
		E3PSProject project = (E3PSProject) target;
		ProjectChangeStop ps = ProjectHelper.getStopProject(project);
		if (ps != null && "중지검토".equals(ps.getChangeType())) {
		    State reassignState = State.toState("STOPINWORK");
		    LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference(lifeCycleName,
			    WTContainerHelper.service.getExchangeRef());
		    project = (E3PSProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    Kogger.info(getClass(), "[updateProcess] Project reassign " + lifeCycleName);
		    project = (E3PSProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    Kogger.info(getClass(), "[updateProcess] Project setLifeCycleState PROGRESS");
		}

	    } else if (target instanceof KETInvestMaster) {
		KETInvestMaster im = (KETInvestMaster) target;
		im.setInvestState(InvestUtil.INPROGRESS);
		PersistenceServerHelper.manager.update(im);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    SessionContext.setContext(sessioncontext);

	    if (trx != null)
		trx.rollback();
	}
    }

    @Override
    public void createWorkItem(Object obj) throws WTException {
	Transaction trx = null;

	try {
	    trx = new Transaction();
	    trx.start();

	    if (obj instanceof KETEarlyWarning) // ??????
	    {
		KETEarlyWarning ew = (KETEarlyWarning) obj;

		if ((ew.getLifeCycleState().equals(State.toState("APPROVED"))) && (KETObjectUtil.getVersion(ew).equals("0"))) {
		    KETEarlyWarningStep step = WorkflowSearchHelper.manager.getEWStep(ew);
		    step = (KETEarlyWarningStep) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) step,
			    State.toState("PLANNING"));
		    PersistenceHelper.manager.refresh(step);
		    // ????????? ??? ?????? ????????

		    WTUser charge = (WTUser) CommonUtil.getObject(ew.getFstCharge());
		    // ????????????????
		    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    WfTemplateObject paramWfTemplateObject = null;

		    while (allTemplates.hasMoreElements()) {
			WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			if (wfTemplate.getName().equals("KET_TODO_WF")) {
			    paramWfTemplateObject = wfTemplate;
			}
		    }

		    if (paramWfTemplateObject != null) {
			Team team = (Team) ew.getTeamId().getObject();
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			        ew.getContainerReference());
			ProcessData data = process.getContext();
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, ew);
			data.setValue("charge", charge);
			WfEngineHelper.service.startProcess(process, data, 1);
		    }

		    try {
			// 초기유동관리 지정 승인 완료시 Mail 처리
			List<WTUser> toUserList = new ArrayList<WTUser>();
			toUserList.add(charge);
			KETMailHelper.service.sendFormMail("08042", "NoticeMailLine2.html", ew, (WTUser) ew.getCreator().getPrincipal(),
			        toUserList);
		    } catch (Exception e) {

		    }
		}
	    } else if (obj instanceof KETEarlyWarningPlan) // ??????
	    {
		KETEarlyWarningPlan ew = (KETEarlyWarningPlan) obj;
		KETEarlyWarningStep step = WorkflowSearchHelper.manager.getEWStep(ew);

		if ((ew.getLifeCycleState().equals(State.toState("APPROVED")))
		        && (step.getLifeCycleState().equals(State.toState("PLANNING")))) {
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) step, State.toState("EWRREPORT"));

		    // ????????? ??? ?????? ????????
		    KETEarlyWarning earlyWarning = WorkflowSearchHelper.manager.getEW(ew);

		    if (VERBOSE)
			Kogger.info(getClass(), "????? " + earlyWarning.getEndDate() + " / ?????: " + step.getEndDate());

		    if (step.getEndDate().equals(earlyWarning.getEndDate())) {
			WTUser chargeUser = (WTUser) CommonUtil.getObject(step.getFstCharge());
			// ????????????????
			Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			WfTemplateObject paramWfTemplateObject = null;

			while (allTemplates.hasMoreElements()) {
			    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			    if (wfTemplate.getName().equals("KET_TODO_WF")) {
				paramWfTemplateObject = wfTemplate;
			    }
			}

			if (paramWfTemplateObject != null) {
			    Team team = (Team) ew.getTeamId().getObject();
			    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
				    ew.getContainerReference());
			    ProcessData data = process.getContext();
			    WfEngineServerHelper.service.setPrimaryBusinessObject(process, ew);
			    data.setValue("charge", chargeUser);
			    WfEngineHelper.service.startProcess(process, data, 1);
			}
			try {
			    // 초기유동관리 계획수립 완료시 Mail 처리
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add(chargeUser);
			    KETMailHelper.service.sendFormMail("08043", "NoticeMailLine1.html", ew,
				    (WTUser) ew.getCreator().getPrincipal(), toUserList);
			} catch (Exception e) {

			}
		    }
		}
	    } else if (obj instanceof KETEarlyWarningEnd) // ?????? ???
	    {
		KETEarlyWarningEnd ew = (KETEarlyWarningEnd) obj;

		if (ew.getState().getState().equals(State.toState("APPROVED"))) {
		    KETEarlyWarningStep step = WorkflowSearchHelper.manager.getEWStep(ew);
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) step, State.toState("PLANNING"));
		    // ????????? ??? ?????? ????????

		    WTUser chargeUser = (WTUser) CommonUtil.getObject(step.getFstCharge());
		    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    WfTemplateObject paramWfTemplateObject = null;

		    while (allTemplates.hasMoreElements()) {
			WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			if (wfTemplate.getName().equals("KET_TODO_WF")) {
			    paramWfTemplateObject = wfTemplate;
			}
		    }

		    if (paramWfTemplateObject != null) {
			Team team = (Team) ew.getTeamId().getObject();
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			        ew.getContainerReference());
			ProcessData data = process.getContext();
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, ew);
			data.setValue("charge", chargeUser);
			WfEngineHelper.service.startProcess(process, data, 1);
		    }
		}
	    } else if (obj instanceof KETEarlyWarningResult) // ??????
	    {
		KETEarlyWarningResult ew = (KETEarlyWarningResult) obj;
		KETEarlyWarningStep step = WorkflowSearchHelper.manager.getEWStep(ew);

		Timestamp compareTime = step.getEndDate();
		compareTime.setHours(0);
		compareTime.setMinutes(0);
		compareTime.setSeconds(0);

		if (DateUtil.getCurrentTimestamp().getTime() - compareTime.getTime() >= 0) {
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) step, State.toState("EWRREQUEST"));
		    // ????????? ??? ?????? ????????

		    WTUser chargeUser = (WTUser) CommonUtil.getObject(step.getFstCharge());
		    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    WfTemplateObject paramWfTemplateObject = null;

		    while (allTemplates.hasMoreElements()) {
			WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			if (wfTemplate.getName().equals("KET_TODO_WF")) {
			    paramWfTemplateObject = wfTemplate;
			}
		    }

		    if (paramWfTemplateObject != null) {
			Team team = (Team) ew.getTeamId().getObject();
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			        ew.getContainerReference());
			ProcessData data = process.getContext();
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, ew);
			data.setValue("charge", chargeUser);
			WfEngineHelper.service.startProcess(process, data, 1);
		    }

		    try {
			// 초기유동관리 계획수립 완료시 Mail 처리
			List<WTUser> toUserList = new ArrayList<WTUser>();
			toUserList.add(chargeUser);
			KETMailHelper.service.sendFormMail("08044", "NoticeMailLine1.html", ew, (WTUser) ew.getCreator().getPrincipal(),
			        toUserList);
		    } catch (Exception e) {

		    }
		}
	    } else if (obj instanceof KETMoldChangeOrder) // ????????Workitem ???
	    {
		KETMoldChangeOrder moldCO = (KETMoldChangeOrder) obj;

		if (moldCO.getLifeCycleState().equals(State.toState("ACTIVITYCOMPLETED"))) {
		    /*
	             * Commented by MJOH, 2011-03-31 if( !WorkflowSearchHelper.manager.IsRuninningTodo(moldCO) ) { WTUser charger =
	             * CommonUtil.findUserID(moldCO.getCreatorName()); Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
	             * WfTemplateObject paramWfTemplateObject = null; while( allTemplates.hasMoreElements() ) { WfTemplateObject wfTemplate
	             * = (WfTemplateObject)allTemplates.nextElement(); if( wfTemplate.getName().equals("KET_TODO_WF") ) {
	             * paramWfTemplateObject = wfTemplate; } } if( paramWfTemplateObject != null ) { Team team =
	             * (Team)moldCO.getTeamId().getObject(); WfProcess process =
	             * WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team, moldCO.getContainerReference()); ProcessData
	             * data = process.getContext(); WfEngineServerHelper.service.setPrimaryBusinessObject(process, moldCO);
	             * data.setValue("charge", charger); WfEngineHelper.service.startProcess(process, data, 1); } }
	             */

		    boolean isCreateWorkItem = true; // ECO??? ???????? ECA????????(WORKCOMPLETED) ???????? true????????, ??????
		    // WorkItem????????????
		    Object[] ecaObj = EcmUtil.getChangeActivities(moldCO);
		    KETMoldChangeActivity eca = null;

		    for (int index = 0; index < ecaObj.length; index++) {
			eca = (KETMoldChangeActivity) ecaObj[index];

			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			// 담당자가 있는 ECA만을 계산로직에 포함시킨다.
			if (!workerId.equals("")) {
			    if (!eca.getLifeCycleState().equals(State.toState("WORKCOMPLETED")) && !eca.getActivityType().equals("4")) {
				isCreateWorkItem = false;
				break;
			    }
			}

		    }

		    if (isCreateWorkItem) // ??? ECA???????? ????????
		    {
			WTUser charger = CommonUtil.findUserID(moldCO.getCreatorName());
			Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			WfTemplateObject paramWfTemplateObject = null;

			while (allTemplates.hasMoreElements()) {
			    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			    if (wfTemplate.getName().equals("KET_TODO_WF")) {
				paramWfTemplateObject = wfTemplate;
			    }
			}

			if (paramWfTemplateObject != null) {
			    Team team = (Team) moldCO.getTeamId().getObject();
			    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
				    moldCO.getContainerReference());
			    ProcessData data = process.getContext();
			    WfEngineServerHelper.service.setPrimaryBusinessObject(process, moldCO);
			    data.setValue("charge", charger);
			    WfEngineHelper.service.startProcess(process, data, 1);
			}

			try {
			    // ECO 상태가 활동수행-->활동완료로 변경되어 최종 승인시 김근우 Mail 처리
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add(charger);
			    KETMailHelper.service.sendFormMail("08029", "NoticeMailLine.html", moldCO,
				    (WTUser) SessionHelper.manager.getPrincipal(), toUserList);
			} catch (Exception e) {

			}

			// // To-Do 공지
			// try {
			// WTUser member = CommonUtil.findUserID(moldCO.getCreatorName());
			// WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
			// WTPrincipalReference toUser = WTPrincipalReference.newWTPrincipalReference(prin);
			// Hashtable contentHash = MailUtil.getMailContent("approval", moldCO, toUser.getName());
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
		} else if (moldCO.getLifeCycleState().equals(State.toState("EXCUTEACTIVITY"))) {

		    WfTemplateObject paramWfTemplateObject = null;

		    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    while (allTemplates.hasMoreElements()) {
			WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			if (wfTemplate.getName().equals("KET_TODO_WF")) {
			    paramWfTemplateObject = wfTemplate;
			    break;
			}
		    }

		    if (paramWfTemplateObject != null) {
			// ECO 상태가 계획수립-->활동수행으로 변경시 김근우 Mail 처리
			List<WTUser> toUserList = new ArrayList<WTUser>();

			Object[] ecaObj = EcmUtil.getChangeActivities(moldCO);
			int ecaObjLength = (ecaObj != null) ? ecaObj.length : 0;
			for (int index = 0; index < ecaObjLength; index++) {
			    KETMoldChangeActivity eca = (KETMoldChangeActivity) ecaObj[index];

			    // 금형인 경우 활동(4)은 승인(결재)후 Todo에 가도록 활동수행일 때는 제외한다.
			    if (!eca.getActivityType().equals("4")) {
				if (!WorkflowSearchHelper.manager.IsRuninningTodo(eca)) {
				    String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
				    // 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
				    if (!workerId.equals("")) {
					WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
					if (ecaCharge != null) {
					    Team team = (Team) eca.getTeamId().getObject();
					    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
						    eca.getContainerReference());
					    ProcessData data = process.getContext();
					    WfEngineServerHelper.service.setPrimaryBusinessObject(process, eca);
					    data.setValue("charge", ecaCharge);
					    WfEngineHelper.service.startProcess(process, data, 1);

					    // 같은사람한테는 한번만 보내게 한다.
					    if (!toUserList.contains(ecaCharge)) {
						toUserList.add(ecaCharge);
					    }

					}
				    }
				}
			    }
			} // for (int index = 0; index < ecaObj.length; index++) {

			try {
			    KETMailHelper.service.sendFormMail("08026", "NoticeMailLineTodoEco.html", moldCO, (WTUser) moldCO.getCreator()
				    .getPrincipal(), toUserList);
			} catch (Exception e) {
			    // TODO Auto-generated catch block
			    Kogger.error(getClass(), e);
			}

		    } // if (paramWfTemplateObject != null) {

		} else if (moldCO.getLifeCycleState().equals(State.toState("APPROVED"))) {
		    Object[] ecaObj = EcmUtil.getChangeActivities(moldCO);

		    for (int index = 0; index < ecaObj.length; index++) {
			KETMoldChangeActivity moldCA = (KETMoldChangeActivity) ecaObj[index];

			// activity type??1,2????????(EPM, BOM)
			if (!WorkflowSearchHelper.manager.IsRuninningTodo(moldCA)) {
			    if ((moldCA.getActivityType().equals("4"))) // ???????????
			    {
				String workerId = StringUtils.stripToEmpty(moldCA.getWorkerId());
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
					    Team team = (Team) moldCA.getTeamId().getObject();
					    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
						    moldCA.getContainerReference());
					    ProcessData data = process.getContext();
					    WfEngineServerHelper.service.setPrimaryBusinessObject(process, moldCA);
					    data.setValue("charge", ecaCharge);
					    WfEngineHelper.service.startProcess(process, data, 1);
					}

					try {
					    // ECN 후속활동 지정시(ECN 상태: 계획수립-->활동수행) 김근우 Mail 처리
					    List<WTUser> toUserList = new ArrayList<WTUser>();
					    toUserList.add(ecaCharge);
					    KETMailHelper.service.sendFormMail("08027", "NoticeMailLine2.html", moldCA, (WTUser) moldCO
						    .getCreator().getPrincipal(), toUserList);
					} catch (Exception e) {
					    // TODO Auto-generated catch block
					    Kogger.error(getClass(), e);
					}

					// To-Do 공지
					// try {
					// WTUser member = CommonUtil.findUserID(moldCA.getCreatorName());
					// WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
					// WTPrincipalReference toUser = WTPrincipalReference.newWTPrincipalReference(prin);
					// Hashtable contentHash = MailUtil.getMailContent("approval", moldCA, toUser.getName());
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
		} else if (moldCO.getLifeCycleState().equals(State.toState("REWORK"))) {

		    // ECA 처리
		    Object[] ecas = EcmUtil.getChangeActivities(moldCO);
		    if (ecas != null) {
			KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

			for (int cnt = 0; cnt < ecas.length; cnt++) {

			    // ECA
			    KETMoldChangeActivity eca = (KETMoldChangeActivity) ecas[cnt];

			    String activityType = StringUtils.stripToEmpty(eca.getActivityType());
			    if (!activityType.equals("4")) {

				// BOM
				Vector<KETBomEcoHeader> bomVec = new Vector<KETBomEcoHeader>();
				QueryResult qrLink = PersistenceHelper.manager.navigate(eca, "bom", KETMoldECABomLink.class, false);
				while (qrLink.hasMoreElements()) {
				    KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qrLink.nextElement();

				    KETBomEcoHeader bomHeader = ketMoldECABomLink.getBom();
				    bomVec.addElement(bomHeader);

				    // 부품 상태도 검토중으로 변경
				    String beforePartOid = ketMoldECABomLink.getBeforePartOid();
				    WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
				    String partNumber = wtPart.getNumber();
				    String preVersion = ketMoldECABomLink.getPreVersion();
				    String afterVersion = ketMoldECABomLink.getAfterVersion();
				    // 초도라는 얘기
				    if (afterVersion == null || preVersion.equals(afterVersion)) {
					String partOid = ketBOMQueryBean.getPartOid(partNumber, preVersion);
					wtPart = (WTPart) CommonUtil.getObject(partOid);
				    }
				    // 설변이라는 얘기
				    else {
					String partOid = ketBOMQueryBean.getPartOid(partNumber, afterVersion);
					wtPart = (WTPart) CommonUtil.getObject(partOid);
				    }

				    if (wtPart != null) {
					// HEENEETODO : 방어코드 Part에 L/C를 reassign 한다.
					LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC",
					        wtPart.getContainerReference());
					wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart,
					        partLifeCycle.getLifeCycleTemplateReference());

					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) wtPart, State.toState("INWORK"));
				    }
				}

				for (int i = 0; i < bomVec.size(); i++) {
				    KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));

				}

				// 도면
				Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(eca);

				for (int i = 0; i < epmVec.size(); i++) {
				    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("INWORK"));
				}

				// ECA
				Timestamp completeDate = DateUtil.getCurrentTimestamp();
				eca.setCompleteDate(completeDate);
				// Added by MJOH, 2011-03-30
				// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
				eca.setActivityStatus("INWORK");
				PersistenceHelper.manager.save(eca);
				eca = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(eca);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("INWORK"));

			    }

			}

		    }

		    // ECO 처리
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldCO, State.toState("EXCUTEACTIVITY"));

		    /*
	             * WTUser charger = CommonUtil.findUserID(moldCO.getCreatorName()); Enumeration allTemplates =
	             * WfDefinerHelper.service.getAllTemplates(); WfTemplateObject paramWfTemplateObject = null;
	             * 
	             * while (allTemplates.hasMoreElements()) { WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();
	             * 
	             * if (wfTemplate.getName().equals("KET_TODO_WF")) { paramWfTemplateObject = wfTemplate; } }
	             * 
	             * if (paramWfTemplateObject != null) { Team team = (Team) moldCO.getTeamId().getObject(); WfProcess process =
	             * WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team, moldCO.getContainerReference()); ProcessData
	             * data = process.getContext(); WfEngineServerHelper.service.setPrimaryBusinessObject(process, moldCO);
	             * data.setValue("charge", charger); WfEngineHelper.service.startProcess(process, data, 1); }
	             * 
	             * // To-Do 공지 try { WTUser member = CommonUtil.findUserID(moldCO.getCreatorName()); WTPrincipal prin =
	             * OrganizationServicesHelper.manager.getPrincipal(member.getName()); WTPrincipalReference toUser =
	             * WTPrincipalReference.newWTPrincipalReference(prin); Hashtable contentHash = MailUtil.getMailContent("approval",
	             * moldCO, toUser.getName()); String templateName = CommonUtil.getMailTemplateName(toUser.getName(), "TaskNoticeMail");
	             * String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName); Hashtable hash =
	             * MailUtil.prepareMailInfoHash(toUser, contents); if (hash != null) MailUtil.sendMail2(hash, (String)
	             * contentHash.get("mailTitle"));
	             * 
	             * } catch (Exception e) {
	             * 
	             * }
	             */
		}

	    } else if (obj instanceof KETProdChangeOrder) {
		KETProdChangeOrder prodCO = (KETProdChangeOrder) obj;
		// ??????????? ?????? ECO ????????todo ???(????????
		// EPM,BOM?????ECO????? - flag ??????????

		if (prodCO.getLifeCycleState().equals(State.toState("ACTIVITYCOMPLETED"))) {
		    // if( !WorkflowSearchHelper.manager.IsRuninningTodo(prodCO) )
		    // {
		    // WTUser charger = CommonUtil.findUserID(prodCO.getCreatorName());
		    // Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    // WfTemplateObject paramWfTemplateObject = null;
		    //
		    // while( allTemplates.hasMoreElements() )
		    // {
		    // WfTemplateObject wfTemplate = (WfTemplateObject)allTemplates.nextElement();
		    //
		    // if( wfTemplate.getName().equals("KET_TODO_WF") )
		    // {
		    // paramWfTemplateObject = wfTemplate;
		    // }
		    // }
		    //
		    // if( paramWfTemplateObject != null )
		    // {
		    // Team team = (Team)prodCO.getTeamId().getObject();
		    // WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
		    // prodCO.getContainerReference());
		    // ProcessData data = process.getContext();
		    // WfEngineServerHelper.service.setPrimaryBusinessObject(process, prodCO);
		    // data.setValue("charge", charger);
		    // WfEngineHelper.service.startProcess(process, data, 1);
		    // }
		    // }

		    boolean isCreateWorkItem = true; // ECO??? ???????? ECA????????(WORKCOMPLETED) ???????? true????????, ??????
		    // WorkItem????????????
		    Object[] ecaObj = EcmUtil.getChangeActivities(prodCO);
		    KETProdChangeActivity eca = null;

		    for (int index = 0; index < ecaObj.length; index++) {
			eca = (KETProdChangeActivity) ecaObj[index];

			if (!eca.getLifeCycleState().equals(State.toState("WORKCOMPLETED"))
			        && (!eca.getActivityType().equals("3") && !eca.getActivityType().equals("4"))) {
			    isCreateWorkItem = false;
			    break;
			}
		    }

		    if (isCreateWorkItem) {
			WTUser charger = CommonUtil.findUserID(prodCO.getCreatorName());
			Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			WfTemplateObject paramWfTemplateObject = null;

			while (allTemplates.hasMoreElements()) {
			    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			    if (wfTemplate.getName().equals("KET_TODO_WF")) {
				paramWfTemplateObject = wfTemplate;
			    }
			}

			if (paramWfTemplateObject != null) {
			    Team team = (Team) prodCO.getTeamId().getObject();
			    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
				    prodCO.getContainerReference());
			    ProcessData data = process.getContext();
			    WfEngineServerHelper.service.setPrimaryBusinessObject(process, prodCO);
			    data.setValue("charge", charger);
			    WfEngineHelper.service.startProcess(process, data, 1);
			}
			try {
			    // ECO 상태가 활동수행-->활동완료로 변경되어 최종 승인시 김근우 Mail 처리
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add(charger);
			    KETMailHelper.service.sendFormMail("08029", "NoticeMailLine.html", prodCO,
				    (WTUser) SessionHelper.manager.getPrincipal(), toUserList);
			} catch (Exception e) {

			}
			// }
			// // To-Do 공지
			// try {
			// WTUser member = CommonUtil.findUserID(prodCO.getCreatorName());
			// WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
			// WTPrincipalReference toUser = WTPrincipalReference.newWTPrincipalReference(prin);
			// Hashtable contentHash = MailUtil.getMailContent("approval", prodCO, toUser.getName());
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

		} else if (prodCO.getLifeCycleState().equals(State.toState("EXCUTEACTIVITY"))) {

		    WfTemplateObject paramWfTemplateObject = null;

		    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		    while (allTemplates.hasMoreElements()) {
			WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			if (wfTemplate.getName().equals("KET_TODO_WF")) {
			    paramWfTemplateObject = wfTemplate;
			    break;
			}
		    }

		    if (paramWfTemplateObject != null) {
			// ECO 상태가 계획수립-->활동수행으로 변경시 김근우 Mail 처리
			List<WTUser> toUserList = new ArrayList<WTUser>();

			Object[] ecaObj = EcmUtil.getChangeActivities(prodCO);
			int ecaObjLength = (ecaObj != null) ? ecaObj.length : 0;
			for (int index = 0; index < ecaObjLength; index++) {
			    KETProdChangeActivity eca = (KETProdChangeActivity) ecaObj[index];

			    // 제품인 경우 문서(3)와 활동(4)은 승인(결재)후 Todo에 가도록 활동수행일 때는 제외한다.
			    if (!eca.getActivityType().equals("3") && !eca.getActivityType().equals("4")) {
				// activity type??1,2????????(EPM, BOM)
				if (!WorkflowSearchHelper.manager.IsRuninningTodo(eca)) {

				    if (paramWfTemplateObject != null) {

					String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
					// 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
					if (!workerId.equals("")) {
					    WTUser ecaCharge = (WTUser) CommonUtil.getObject(workerId);
					    if (ecaCharge != null) {
						Team team = (Team) eca.getTeamId().getObject();
						WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
						        eca.getContainerReference());
						ProcessData data = process.getContext();
						WfEngineServerHelper.service.setPrimaryBusinessObject(process, eca);
						data.setValue("charge", ecaCharge);
						WfEngineHelper.service.startProcess(process, data, 1);

						// 같은사람한테는 한번만 보내게 한다.
						if (!toUserList.contains(ecaCharge)) {
						    toUserList.add(ecaCharge);
						}

					    }

					}
				    }
				}
			    }
			} // for (int index = 0; index < ecaObjLength; index++) {

			try {
			    KETMailHelper.service.sendFormMail("08026", "NoticeMailLineTodoEco.html", prodCO, (WTUser) prodCO.getCreator()
				    .getPrincipal(), toUserList);
			} catch (Exception e) {
			    // TODO Auto-generated catch block
			    Kogger.error(getClass(), e);
			}

		    } // if (paramWfTemplateObject != null) {

		} else if (prodCO.getLifeCycleState().equals(State.toState("APPROVED"))) {
		    Object[] ecaObj = EcmUtil.getChangeActivities(prodCO);

		    for (int index = 0; index < ecaObj.length; index++) {
			KETProdChangeActivity prodCA = (KETProdChangeActivity) ecaObj[index];

			// activity type??1,2????????(EPM, BOM)
			if (!WorkflowSearchHelper.manager.IsRuninningTodo(prodCA)) {
			    if ((prodCA.getActivityType().equals("3")) || (prodCA.getActivityType().equals("4"))) // ???????????
			    {
				String workerId = StringUtils.stripToEmpty(prodCA.getWorkerId());
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
					    Team team = (Team) prodCA.getTeamId().getObject();
					    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
						    prodCA.getContainerReference());
					    ProcessData data = process.getContext();
					    WfEngineServerHelper.service.setPrimaryBusinessObject(process, prodCA);
					    data.setValue("charge", ecaCharge);
					    WfEngineHelper.service.startProcess(process, data, 1);
					}

					try {
					    // ECN 후속활동 지정시(ECN 상태: 계획수립-->활동수행) 김근우 Mail 처리
					    List<WTUser> toUserList = new ArrayList<WTUser>();
					    toUserList.add(ecaCharge);
					    KETMailHelper.service.sendFormMail("08027", "NoticeMailLine2.html", prodCA, (WTUser) prodCA
						    .getCreator().getPrincipal(), toUserList);
					} catch (Exception e) {
					    // TODO Auto-generated catch block
					    Kogger.error(getClass(), e);
					}

					// To-Do 공지
					// try {
					// WTUser member = CommonUtil.findUserID(prodCA.getCreatorName());
					// WTPrincipal prin = OrganizationServicesHelper.manager.getPrincipal(member.getName());
					// WTPrincipalReference toUser = WTPrincipalReference.newWTPrincipalReference(prin);
					// Hashtable contentHash = MailUtil.getMailContent("approval", prodCA, toUser.getName());
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
		    KogDbUtil.log("제품 ECO 승인완료 action", "Success", prodCO, (String) null, null, null);
		    // 홈페이지 이관을 위한 서비스지만 현재 사용안함으로 주석처리 ECN 처리시 문제가됨
		    // DrawingDistHelper.service.backgroundSave(prodCO);

		} else if (prodCO.getLifeCycleState().equals(State.toState("REWORK"))) {

		    // ECA 처리
		    Object[] ecas = EcmUtil.getChangeActivities(prodCO);
		    if (ecas != null) {
			KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

			for (int cnt = 0; cnt < ecas.length; cnt++) {

			    // ECA
			    KETProdChangeActivity eca = (KETProdChangeActivity) ecas[cnt];

			    String activityType = StringUtils.stripToEmpty(eca.getActivityType());
			    if (!activityType.equals("3") && !activityType.equals("4")) {

				// BOM
				Vector<KETBomEcoHeader> bomVec = new Vector<KETBomEcoHeader>();
				QueryResult qrLink = PersistenceHelper.manager.navigate(eca, "bom", KETProdECABomLink.class, false);
				while (qrLink.hasMoreElements()) {
				    KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qrLink.nextElement();

				    KETBomEcoHeader bomHeader = ketProdECABomLink.getBom();
				    bomVec.addElement(bomHeader);

				    // 부품 상태도 작업중으로 변경
				    String beforePartOid = ketProdECABomLink.getBeforePartOid();
				    WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
				    String partNumber = wtPart.getNumber();
				    String preVersion = ketProdECABomLink.getPreVersion();
				    String afterVersion = ketProdECABomLink.getAfterVersion();
				    // 초도라는 얘기
				    if (afterVersion == null || preVersion.equals(afterVersion)) {
					String partOid = ketBOMQueryBean.getPartOid(partNumber, preVersion);
					wtPart = (WTPart) CommonUtil.getObject(partOid);
				    }
				    // 설변이라는 얘기
				    else {
					String partOid = ketBOMQueryBean.getPartOid(partNumber, afterVersion);
					wtPart = (WTPart) CommonUtil.getObject(partOid);
				    }

				    if (wtPart != null) {
					// HEENEETODO : 방어코드 Part에 L/C를 reassign 한다.
					LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC",
					        wtPart.getContainerReference());
					wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart,
					        partLifeCycle.getLifeCycleTemplateReference());

					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) wtPart, State.toState("INWORK"));
				    }
				}

				for (int i = 0; i < bomVec.size(); i++) {
				    KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));

				}

				// 도면
				Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(eca);

				for (int i = 0; i < epmVec.size(); i++) {
				    EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("INWORK"));
				}

				// ECA
				Timestamp completeDate = DateUtil.getCurrentTimestamp();
				eca.setCompleteDate(null);
				// Added by MJOH, 2011-03-30
				// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
				eca.setActivityStatus("INWORK");
				PersistenceHelper.manager.save(eca);
				eca = (KETProdChangeActivity) PersistenceHelper.manager.refresh(eca);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eca, State.toState("INWORK"));

			    }

			}

		    }

		    // ECO 처리
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodCO, State.toState("EXCUTEACTIVITY"));

		    /*
	             * WTUser charger = CommonUtil.findUserID(prodCO.getCreatorName()); Enumeration allTemplates =
	             * WfDefinerHelper.service.getAllTemplates(); WfTemplateObject paramWfTemplateObject = null;
	             * 
	             * while (allTemplates.hasMoreElements()) { WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();
	             * 
	             * if (wfTemplate.getName().equals("KET_TODO_WF")) { paramWfTemplateObject = wfTemplate; } }
	             * 
	             * if (paramWfTemplateObject != null) { Team team = (Team) prodCO.getTeamId().getObject(); WfProcess process =
	             * WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team, prodCO.getContainerReference()); ProcessData
	             * data = process.getContext(); WfEngineServerHelper.service.setPrimaryBusinessObject(process, prodCO);
	             * data.setValue("charge", charger); WfEngineHelper.service.startProcess(process, data, 1); }
	             * 
	             * // To-Do 공지 try { WTUser member = CommonUtil.findUserID(prodCO.getCreatorName()); WTPrincipal prin =
	             * OrganizationServicesHelper.manager.getPrincipal(member.getName()); WTPrincipalReference toUser =
	             * WTPrincipalReference.newWTPrincipalReference(prin); Hashtable contentHash = MailUtil.getMailContent("approval",
	             * prodCO, toUser.getName()); String templateName = CommonUtil.getMailTemplateName(toUser.getName(), "TaskNoticeMail");
	             * String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName); Hashtable hash =
	             * MailUtil.prepareMailInfoHash(toUser, contents); if (hash != null) MailUtil.sendMail2(hash, (String)
	             * contentHash.get("mailTitle"));
	             * 
	             * } catch (Exception e) {
	             * 
	             * }
	             */

		}

	    } else if (obj instanceof KETSampleRequest) {
		// todo workItem 하나만 보내기
		// KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;
		// QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);
		//
		// Vector<WTUser> wtUserVector = new Vector<WTUser>();
		// Vector<KETSamplePart> KETSampePartVector = new Vector<KETSamplePart>();
		// if (qr.hasMoreElements()) {
		// while (qr.hasMoreElements()) {
		// KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();
		//
		// try {
		// ketSamplePart.setSamplePartStateCode("REQUEST");
		// ketSamplePart.setSamplePartStateName("요청");
		//
		// // 메일 발송
		// List<WTUser> toUserList = new ArrayList<WTUser>();
		// toUserList.add(ketSamplePart.getUser());
		// KETMailHelper.service.sendFormMail("08032", "NoticeMailLine2.html", ketSamplePart, (WTUser) ketSamplePart
		// .getCreator().getPrincipal(), toUserList);
		//
		// PersistenceHelper.manager.modify(ketSamplePart);
		// } catch (WTPropertyVetoException e) {
		// Kogger.error(getClass(), e);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// Kogger.error(getClass(), e);
		// }
		//
		// boolean addFlag = true;
		//
		// for (int i = 0; i < wtUserVector.size(); i++) {
		// if (wtUserVector.get(i).equals(ketSamplePart.getUser())) {
		// addFlag = false;
		// }
		// }
		// if (addFlag) {
		// wtUserVector.add(ketSamplePart.getUser());
		// KETSampePartVector.add(ketSamplePart);
		// }
		// }
		// }
		//
		// for (int i = 0; i < KETSampePartVector.size(); i++) {
		// Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
		// WfTemplateObject paramWfTemplateObject = null;
		//
		// while (allTemplates.hasMoreElements()) {
		// WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();
		//
		// if (wfTemplate.getName().equals("KET_TODO_WF")) {
		// paramWfTemplateObject = wfTemplate;
		// }
		// }
		//
		// Team team = (Team) ((KETSamplePart) KETSampePartVector.get(i)).getTeamId().getObject();
		// WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
		// ((KETSamplePart) KETSampePartVector.get(i)).getContainerReference());
		// ProcessData data = process.getContext();
		// WfEngineServerHelper.service.setPrimaryBusinessObject(process, ((KETSamplePart) KETSampePartVector.get(i)));
		// data.setValue("charge", ((KETSamplePart) KETSampePartVector.get(i)).getUser());
		// WfEngineHelper.service.startProcess(process, data, 1);
		//
		// }
		// todo 다보내기
		KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;
		QueryResult qr = PersistenceHelper.manager.navigate(ketSampleRequest, "part", KETSamplePartLink.class);

		if (qr.hasMoreElements()) {
		    while (qr.hasMoreElements()) {
			KETSamplePart ketSamplePart = (KETSamplePart) qr.nextElement();

			try {
			    ketSamplePart.setSamplePartStateCode("REQUEST");
			    ketSamplePart.setSamplePartStateName("요청");

			    // 메일 발송
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add(ketSamplePart.getUser());
			    KETMailHelper.service.sendFormMail("08032", "NoticeMailLine2.html", ketSamplePart, (WTUser) ketSamplePart
				    .getCreator().getPrincipal(), toUserList);

			    PersistenceHelper.manager.modify(ketSamplePart);
			} catch (WTPropertyVetoException e) {
			    Kogger.error(getClass(), e);
			} catch (Exception e) {
			    // TODO Auto-generated catch block
			    Kogger.error(getClass(), e);
			}

			Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			WfTemplateObject paramWfTemplateObject = null;

			while (allTemplates.hasMoreElements()) {
			    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			    if (wfTemplate.getName().equals("KET_TODO_WF")) {
				paramWfTemplateObject = wfTemplate;
			    }
			}

			Team team = (Team) ketSamplePart.getTeamId().getObject();
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			        ketSamplePart.getContainerReference());
			ProcessData data = process.getContext();
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, ketSamplePart);
			data.setValue("charge", ketSamplePart.getUser());
			WfEngineHelper.service.startProcess(process, data, 1);
		    }
		}

	    } else if (obj instanceof KETDqmHeader) {
		KETDqmHeader ketDqmHeader = (KETDqmHeader) obj;

		if (ketDqmHeader.getDqmStateCode().equals("RAISEAPPROVED")) {
		    if (ketDqmHeader.getAction() == null) {
			SessionContext current = SessionContext.getContext();
			try {
			    // 작성자로 세션수정
			    SessionHelper.manager.setPrincipal(ketDqmHeader.getRaise().getCreator().getPrincipal().getName());

			    KETDqmTodoAtivity ketDqmTodoAtivity = KETDqmTodoAtivity.newKETDqmTodoAtivity();

			    // 세션 원복
			    SessionContext.setContext(current);

			    ketDqmTodoAtivity.setAction(ketDqmHeader.getAction());
			    ketDqmTodoAtivity.setHeader(ketDqmHeader);
			    ketDqmTodoAtivity.setTaskCode("REVIEW_USER_CHOISE");
			    ketDqmTodoAtivity.setTaskName("검토담당자지정");

			    WTContainerRef containerRef = WCUtil.getWTContainerRef();
			    String lcName = "KET_ECA_LC";
			    LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmTodoAtivity,
				    LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

			    String folderPath = "/Default/자동차사업부/초기유동/";

			    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
			    FolderHelper.assignLocation((FolderEntry) ketDqmTodoAtivity, folder);

			    ketDqmTodoAtivity = (KETDqmTodoAtivity) PersistenceHelper.manager.save(ketDqmTodoAtivity);

			    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			    WfTemplateObject paramWfTemplateObject = null;

			    while (allTemplates.hasMoreElements()) {
				WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

				if (wfTemplate.getName().equals("KET_TODO_WF")) {
				    paramWfTemplateObject = wfTemplate;
				}
			    }

			    Team team = (Team) ketDqmTodoAtivity.getTeamId().getObject();
			    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
				    ketDqmTodoAtivity.getContainerReference());
			    ProcessData data = process.getContext();
			    WfEngineServerHelper.service.setPrimaryBusinessObject(process, ketDqmTodoAtivity);
			    data.setValue("charge", ketDqmHeader.getRaise().getPmUser());
			    WfEngineHelper.service.startProcess(process, data, 1);

			    // 메일 발송
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add(ketDqmHeader.getRaise().getPmUser());
			    KETMailHelper.service.sendFormMail("08035", "DQMNoticeMail.html", ketDqmTodoAtivity, (WTUser) ketDqmHeader
				    .getRaise().getCreator().getPrincipal(), toUserList);

			} catch (WTPropertyVetoException e) {
			    Kogger.error(getClass(), e);
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			} finally {
			    SessionContext.setContext(current);
			}

		    } else {
			try {
			    KETDqmTodoAtivity ketDqmTodoAtivity = KETDqmTodoAtivity.newKETDqmTodoAtivity();
			    ketDqmTodoAtivity.setAction(ketDqmHeader.getAction());
			    ketDqmTodoAtivity.setHeader(ketDqmHeader);
			    ketDqmTodoAtivity.setTaskCode("DQM_ACTION");
			    ketDqmTodoAtivity.setTaskName("ISSUE 검토");

			    WTContainerRef containerRef = WCUtil.getWTContainerRef();
			    String lcName = "KET_ECA_LC";
			    LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmTodoAtivity,
				    LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

			    String folderPath = "/Default/자동차사업부/초기유동/";

			    SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
			    FolderHelper.assignLocation((FolderEntry) ketDqmTodoAtivity, folder);

			    ketDqmTodoAtivity = (KETDqmTodoAtivity) PersistenceHelper.manager.save(ketDqmTodoAtivity);

			    Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			    WfTemplateObject paramWfTemplateObject = null;

			    while (allTemplates.hasMoreElements()) {
				WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

				if (wfTemplate.getName().equals("KET_TODO_WF")) {
				    paramWfTemplateObject = wfTemplate;
				}
			    }

			    Team team = (Team) ketDqmTodoAtivity.getTeamId().getObject();
			    WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
				    ketDqmTodoAtivity.getContainerReference());
			    ProcessData data = process.getContext();
			    WfEngineServerHelper.service.setPrimaryBusinessObject(process, ketDqmTodoAtivity);
			    data.setValue("charge", ketDqmHeader.getAction().getUser());
			    WfEngineHelper.service.startProcess(process, data, 1);

			    // 메일 발송
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add(ketDqmHeader.getAction().getUser());
			    KETMailHelper.service.sendFormMail("08036", "DQMNoticeMail.html", ketDqmTodoAtivity,
				    (WTUser) SessionHelper.manager.getPrincipal(), toUserList);

			} catch (WTPropertyVetoException e) {
			    Kogger.error(getClass(), e);
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
		    }

		} else if (ketDqmHeader.getDqmStateCode().equals("ACTIONAPPROVED")) {
		    SessionContext current = SessionContext.getContext();
		    try {
			// 작성자로 세션수정
			SessionHelper.manager.setPrincipal(ketDqmHeader.getAction().getUser().getName());

			KETDqmTodoAtivity ketDqmTodoAtivity = KETDqmTodoAtivity.newKETDqmTodoAtivity();

			// 세션 원복
			SessionContext.setContext(current);

			ketDqmTodoAtivity.setClose(ketDqmHeader.getClose());
			ketDqmTodoAtivity.setHeader(ketDqmHeader);
			ketDqmTodoAtivity.setTaskCode("DQM_CLOSE");
			ketDqmTodoAtivity.setTaskName("ISSUE 종결");

			WTContainerRef containerRef = WCUtil.getWTContainerRef();
			String lcName = "KET_ECA_LC";
			LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmTodoAtivity,
			        LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

			String folderPath = "/Default/자동차사업부/초기유동/";

			SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
			FolderHelper.assignLocation((FolderEntry) ketDqmTodoAtivity, folder);

			ketDqmTodoAtivity = (KETDqmTodoAtivity) PersistenceHelper.manager.save(ketDqmTodoAtivity);

			Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			WfTemplateObject paramWfTemplateObject = null;

			while (allTemplates.hasMoreElements()) {
			    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			    if (wfTemplate.getName().equals("KET_TODO_WF")) {
				paramWfTemplateObject = wfTemplate;
			    }
			}

			Team team = (Team) ketDqmTodoAtivity.getTeamId().getObject();
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			        ketDqmTodoAtivity.getContainerReference());
			ProcessData data = process.getContext();
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, ketDqmTodoAtivity);
			// data.setValue("charge", ketDqmHeader.getRaise().getPmUser());
			/*
		         * 종결 TO-DO 대상자 변경 : 기존 PM => Project의 특정 role로 변경됨( 자동차사업부 : 선행품질보증, 전자사업부 : 선행품질보증 or 전자품질관리 ) 연구기획팀 전상우 대리요청
		         * 2015.11.16
		         */
			data.setValue("charge", ketDqmHeader.getRaise().getCloseUser());
			WfEngineHelper.service.startProcess(process, data, 1);

			// 메일 발송
			List<WTUser> toUserList = new ArrayList<WTUser>();
			toUserList.add(ketDqmHeader.getRaise().getCloseUser());
			WTUser actionUser = ketDqmHeader.getAction().getUser();
			PeopleData peo = new PeopleData(actionUser);

			KETMailHelper.service.sendFormMail("08038", "DQMNoticeMail.html", ketDqmTodoAtivity, actionUser, toUserList);

			if (PeopleHelper.manager.getChiefUser(peo.department) != null) {// 검토 담당자의 팀장에게 개선안진행완료 메일 발송
			    WTUser chief = PeopleHelper.manager.getChiefUser(peo.department);
			    if (!chief.isDisabled()) {
				toUserList = new ArrayList<WTUser>();
				toUserList.add(chief);// 팀장
				KETMailHelper.service.sendFormMail("08038-1", "DQMNoticeMail.html", ketDqmTodoAtivity, actionUser,
				        toUserList);
			    }
			}

		    } catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    } finally {
			SessionContext.setContext(current);
		    }
		} else if (ketDqmHeader.getDqmStateCode().equals("ACTIONINWORK")) {
		    try {
			KETDqmTodoAtivity ketDqmTodoAtivity = KETDqmTodoAtivity.newKETDqmTodoAtivity();
			ketDqmTodoAtivity.setAction(ketDqmHeader.getAction());
			ketDqmTodoAtivity.setHeader(ketDqmHeader);
			ketDqmTodoAtivity.setTaskCode("DQM_ACTION");
			ketDqmTodoAtivity.setTaskName("ISSUE 검토");

			WTContainerRef containerRef = WCUtil.getWTContainerRef();
			String lcName = "KET_ECA_LC";
			LifeCycleHelper.setLifeCycle((LifeCycleManaged) ketDqmTodoAtivity,
			        LifeCycleHelper.service.getLifeCycleTemplate(lcName, containerRef));

			String folderPath = "/Default/자동차사업부/초기유동/";

			SubFolder folder = (SubFolder) FolderHelper.service.getFolder(folderPath, containerRef);
			FolderHelper.assignLocation((FolderEntry) ketDqmTodoAtivity, folder);

			ketDqmTodoAtivity = (KETDqmTodoAtivity) PersistenceHelper.manager.save(ketDqmTodoAtivity);

			Enumeration allTemplates = WfDefinerHelper.service.getAllTemplates();
			WfTemplateObject paramWfTemplateObject = null;

			while (allTemplates.hasMoreElements()) {
			    WfTemplateObject wfTemplate = (WfTemplateObject) allTemplates.nextElement();

			    if (wfTemplate.getName().equals("KET_TODO_WF")) {
				paramWfTemplateObject = wfTemplate;
			    }
			}

			Team team = (Team) ketDqmTodoAtivity.getTeamId().getObject();
			WfProcess process = WfEngineHelper.service.createAdHocProcess(paramWfTemplateObject, team,
			        ketDqmTodoAtivity.getContainerReference());
			ProcessData data = process.getContext();
			WfEngineServerHelper.service.setPrimaryBusinessObject(process, ketDqmTodoAtivity);
			data.setValue("charge", ketDqmHeader.getAction().getUser());
			WfEngineHelper.service.startProcess(process, data, 1);

			// 메일 발송
			List<WTUser> toUserList = new ArrayList<WTUser>();
			toUserList.add(ketDqmHeader.getAction().getUser());
			KETMailHelper.service.sendFormMail("08036", "DQMNoticeMail.html", ketDqmTodoAtivity,
			        (WTUser) SessionHelper.manager.getPrincipal(), toUserList);

		    } catch (WTPropertyVetoException e) {
			Kogger.error(getClass(), e);
		    } catch (Exception e) {
			Kogger.error(getClass(), e);
		    }
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    @Override
    public void changeStatePBO(Object obj) throws WTException {
	Transaction trx = null;

	try {
	    trx = new Transaction();
	    trx.start();

	    Kogger.info(getClass(), "[changeStatePBO] eventObject : " + obj);
	    // 제품, 금형 ECR, 주간부서 // , 회의록
	    if (obj instanceof WTChangeRequest2 || obj instanceof KETCompetentDepartment) { // || obj instanceof KETMeetingMinutes) {

		try {

		    // 현 PBO의 단계
		    State state = ((LifeCycleManaged) obj).getLifeCycleState();

		    // 검토중
		    if (state.equals(State.toState("UNDERREVIEW"))) {

			// 기본정보
			if (obj instanceof WTChangeRequest2) {
			    // 기본정보가 검토중이 되었을 경우 '제기승인'으로 Set 한다.
			    // 제품 ECR
			    if (obj instanceof KETProdChangeRequest) {
				KETProdChangeRequest ecr = (KETProdChangeRequest) obj;
				ecr.setEcrStateState(State.toState("APPROVEDFILING"));

				ecr = (KETProdChangeRequest) PersistenceHelper.manager.save(ecr);

			    }
			    // 금형 ECR
			    else if (obj instanceof KETMoldChangeRequest) {
				KETMoldChangeRequest ecr = (KETMoldChangeRequest) obj;
				ecr.setEcrStateState(State.toState("APPROVEDFILING"));

				ecr = (KETMoldChangeRequest) PersistenceHelper.manager.save(ecr);
			    }

			}

			// 주간부서
			if (obj instanceof KETCompetentDepartment) {

			    // 주간부서가 검토중이 되었을 경우 '검토중'으로 Set 한다.
			    KETCompetentDepartment competent = (KETCompetentDepartment) obj;
			    QueryResult qr = PersistenceHelper.manager.navigate(competent, "ecr",
				    e3ps.ecm.entity.KETEcrCompetentLink.class, false);
			    while (qr.hasMoreElements()) {
				e3ps.ecm.entity.KETEcrCompetentLink link = (e3ps.ecm.entity.KETEcrCompetentLink) qr.nextElement();
				wt.change2.WTChangeRequest2 wtChangeRequest2 = link.getEcr();

				// 제품 ECR
				if (wtChangeRequest2 instanceof KETProdChangeRequest) {
				    KETProdChangeRequest ecr = (KETProdChangeRequest) wtChangeRequest2;
				    ecr.setEcrStateState(State.toState("UNDERREVIEW"));

				    ecr = (KETProdChangeRequest) PersistenceHelper.manager.save(ecr);
				}
				// 금형 ECR
				else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
				    KETMoldChangeRequest ecr = (KETMoldChangeRequest) wtChangeRequest2;
				    ecr.setEcrStateState(State.toState("UNDERREVIEW"));

				    ecr = (KETMoldChangeRequest) PersistenceHelper.manager.save(ecr);
				}
			    }

			}

		    }
		    // 승인완료
		    else if (state.equals(State.toState("APPROVED"))) {

			// 기본정보
			if (obj instanceof WTChangeRequest2) {
			    // 기본정보가 승인완료가 되었을 경우 '검토'으로 Set 한다.
			    // 제품 ECR
			    if (obj instanceof KETProdChangeRequest) {
				KETProdChangeRequest ecr = (KETProdChangeRequest) obj;
				ecr.setEcrStateState(State.toState("CONSIDER"));

				ecr = (KETProdChangeRequest) PersistenceHelper.manager.save(ecr);
			    }
			    // 금형 ECR
			    else if (obj instanceof KETMoldChangeRequest) {
				KETMoldChangeRequest ecr = (KETMoldChangeRequest) obj;
				ecr.setEcrStateState(State.toState("CONSIDER"));

				ecr = (KETMoldChangeRequest) PersistenceHelper.manager.save(ecr);
			    }

			    // 주관부서 담당자에게 Todo 처리한다.
			    // ECR 로 찾는다.
			    QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
			    spec.appendWhere(
				    new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=", CommonUtil
				            .getOIDLongValue((WTChangeRequest2) obj)), new int[] { 0 });
			    QueryResult result = PersistenceHelper.manager.find(spec);
			    if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
				// 제품, 금형 ECR 확장팩
				KETChangeRequestExpansion expansion = (KETChangeRequestExpansion) result.nextElement();
				WTUser charge = (expansion != null) ? expansion.getCharge() : null;
				// 담당자가 set 되어 있지 않을 경우 생성하지 않는다.
				if (charge != null) {
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

				    // To-Do 공지
				    try {
					List<WTUser> toUserList = new ArrayList<WTUser>();
					WTUser member = (WTUser) expansion.getCharge();
					toUserList.add(member);
					// toUserList set해야함
					KETMailHelper.service.sendFormMail("08020", "NoticeMailLine2.html", expansion, (WTUser) expansion
					        .getCreator().getPrincipal(), toUserList);

					/*
			                 * WTUser member = (WTUser) expansion.getCharge(); WTPrincipal prin =
			                 * OrganizationServicesHelper.manager.getPrincipal(member.getName()); WTPrincipalReference toUser =
			                 * WTPrincipalReference.newWTPrincipalReference(prin); Hashtable contentHash =
			                 * MailUtil.getMailContent("approval", expansion, toUser.getName()); String templateName =
			                 * CommonUtil.getMailTemplateName(toUser.getName(), "TaskNoticeMail"); String contents =
			                 * MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName); Hashtable hash =
			                 * MailUtil.prepareMailInfoHash(toUser, contents); if (hash != null) MailUtil.sendMail2(hash,
			                 * (String) contentHash.get("mailTitle"));
			                 */

				    } catch (Exception e) {

				    }
				}
			    }

			}

			// 주간부서
			if (obj instanceof KETCompetentDepartment) {
			    // 주간부서가 승인완료가 되었을 경우 '검토완료'으로 Set 한다.
			    KETCompetentDepartment competent = (KETCompetentDepartment) obj;
			    QueryResult qr = PersistenceHelper.manager.navigate(competent, "ecr",
				    e3ps.ecm.entity.KETEcrCompetentLink.class, false);
			    if (qr.hasMoreElements()) {
				e3ps.ecm.entity.KETEcrCompetentLink link = (e3ps.ecm.entity.KETEcrCompetentLink) qr.nextElement();
				wt.change2.WTChangeRequest2 wtChangeRequest2 = link.getEcr();

				// 제품 ECR
				if (wtChangeRequest2 instanceof KETProdChangeRequest) {
				    KETProdChangeRequest ecr = (KETProdChangeRequest) wtChangeRequest2;
				    ecr.setEcrStateState(State.toState("CONSIDERED"));

				    ecr = (KETProdChangeRequest) PersistenceHelper.manager.save(ecr);
				}
				// 금형 ECR
				else if (wtChangeRequest2 instanceof KETMoldChangeRequest) {
				    KETMoldChangeRequest ecr = (KETMoldChangeRequest) wtChangeRequest2;
				    ecr.setEcrStateState(State.toState("CONSIDERED"));

				    ecr = (KETMoldChangeRequest) PersistenceHelper.manager.save(ecr);
				}

				// 주관부서 담당자에서 Todo 를 완료 처리한다.
				// ECR 로 찾는다.
				QuerySpec spec = new QuerySpec(KETChangeRequestExpansion.class);
				spec.appendWhere(new SearchCondition(KETChangeRequestExpansion.class, "ecrReference.key.id", "=",
				        CommonUtil.getOIDLongValue(wtChangeRequest2)), new int[] { 0 });
				QueryResult result = PersistenceHelper.manager.find(spec);
				if (result.hasMoreElements()) { // while (result.hasMoreElements()) {
				    // 제품, 금형 ECR 확장팩
				    KETChangeRequestExpansion expansion = (KETChangeRequestExpansion) result.nextElement();
				    WorkflowSearchHelper.manager.taskComplete(expansion);
				}

			    }

			}

		    }

		} catch (WTPropertyVetoException pve) {
		    Kogger.error(getClass(), pve);
		} catch (WTException we) {
		    Kogger.error(getClass(), we);
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

	    } else if (obj instanceof KETMoldChangeOrder) // ??? ?????? ?????????????
	    {
		KETMoldChangeOrder moldCO = (KETMoldChangeOrder) obj;

		boolean isPartErpInterFaceFail = false;
		if (moldCO.getLifeCycleState().equals(State.toState("APPROVED"))) {

		    Kogger.info(getClass(), "####################################################");
		    Kogger.info(getClass(), "############ ECO -> SAP Interface START ############");
		    Kogger.info(getClass(), "## KETMoldChangeOrder is " + moldCO.getEcoId() + ", " + moldCO.toString());

		    // BOM, ERP와의 인터페이스
		    String changeReason = StringUtils.stripToEmpty(moldCO.getChangeReason());
		    boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);
		    Kogger.info(getClass(), "## changeReason is " + changeReason);
		    Kogger.info(getClass(), "## isTheFirst is " + isTheFirst);

		    boolean infResult = false;
		    String ecoLongValue = KETObjectUtil.getIda2a2(moldCO.toString());

		    try {

			// 초도일 경우
			if (isTheFirst) {
			    Boolean[] bomInterfaceResult = KETBomHelper.service.getBomInterface2(ecoLongValue, false);
			    infResult = bomInterfaceResult[0];
			    isPartErpInterFaceFail = bomInterfaceResult[1];

			}
			// 설변일 경우
			else {
			    Boolean[] bomInterfaceResult = KETBomHelper.service.getBomEcoInterface(ecoLongValue, false);
			    infResult = bomInterfaceResult[0];
			    isPartErpInterFaceFail = bomInterfaceResult[1];

			}

		    } catch (Exception e) {
			infResult = false;
		    }

		    Kogger.info(getClass(), "infResult is " + infResult);
		    Kogger.info(getClass(), "############ ECO -> SAP Interface FINISH ###########");
		    Kogger.info(getClass(), "####################################################");

		    // 실패시 메일을 보낸다.
		    if (!infResult) {
			try {
			    /*
		             * WTPrincipalReference toUser = moldCO.getCreator(); Hashtable contentHash = MailUtil.getMailContent("infFail",
		             * moldCO, toUser.getName()); String templateName = CommonUtil.getMailTemplateName(toUser.getName(),
		             * "InterfaceFailNotice"); String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash,
		             * templateName); Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents); if (hash != null)
		             * MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
		             */

			    // ECO 상태가 활동수행-->활동완료로 변경되어 최종 승인시 김근우 Mail 처리
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add((WTUser) moldCO.getCreator().getPrincipal());
			    WTUser admin = (WTUser) SessionHelper.manager.getAdministrator();

			    Kogger.info(getClass(), admin);
			    People pp = PeopleHelper.manager.getPeople(admin.getName());
			    Kogger.info(getClass(), pp.getEmail());

			    toUserList.add(admin);
			    KETMailHelper.service.sendFormMail("08124", "InterfaceFailNotice.html", moldCO,
				    (WTUser) SessionHelper.manager.getPrincipal(), toUserList);

			    KogDbUtil.log("금형 ECO 승인완료 실패 메일 발송", "Success", moldCO, (String) null, null, null);

			} catch (Exception e) {

			    KogDbUtil.log("금형 ECO 승인완료 실패 메일 발송", "Fail", moldCO, e, null, null);
			}

		    } else {
			KogDbUtil.log("금형 ECO ECN전 ERP 및 후처리 완료", "Success", moldCO, (String) null, null, null);
		    }

		    // Commented by MJOH, 2011-04-03
		    // ??????????? ????? ????????????????????????????? ?????BOM ?????? ?????
		    // if( infResult )
		    // {
		    // Vector<KETBomEcoHeader> bomVec = WorkflowSearchHelper.manager.getBOMfromMoldECA(moldCA);
		    // KETBomEcoHeader bomEcoHeader = null;
		    //
		    // for( int i = 0; i < bomVec.size(); i++ )
		    // {
		    // bomEcoHeader = bomVec.elementAt( i );
		    // LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)bomEcoHeader, moldCA.getLifeCycleState());
		    // }
		    // }

		    // Commented by MJOH, 2011-04-03
		    // ???????????????? ???????????????????????? ??????????? ?????BOM ?????? ?????
		    // if( !infResult && mailEnable )
		    // {
		    // if( VERBOSE )
		    // Kogger.info(getClass(), ">>>>>>>>>>>>>>??? ??? BOM ??????????? ??? ??? ???!!");
		    //
		    // WTPrincipalReference toUser = bomHeader.getCreator();
		    // String contents = "";
		    // Hashtable contentHash = MailUtil.getMailContent("infFail", bomHeader, toUser.getName());
		    // contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, "InterfaceFailNotice.html");
		    // Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents);
		    //
		    // if( hash != null )
		    // MailUtil.sendMail2( hash, (String)contentHash.get("mailTitle") );
		    // }

		    if (!isPartErpInterFaceFail) {

			try {

			    // ECN 처리
			    boolean hasMoreEcaWorking2nd = false;
			    QueryResult qr = PersistenceHelper.manager.navigate(moldCO, "moldECA", KETMoldChangeActivityLink.class, false);
			    while (qr.hasMoreElements()) {
				KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) qr.nextElement();
				KETMoldChangeActivity eca = ketMoldChangeActivityLink.getMoldECA();
				String ecaActivityType = StringUtils.stripToEmpty(eca.getActivityType());

				String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
				// 담당자가 있는 ECA와 ECN만을 계산로직에 포함시킨다.
				if (!workerId.equals("")) {

				    // 4M변경일 경우
				    if (ecaActivityType.equals("4")) {
					hasMoreEcaWorking2nd = true;
					break;

				    }

				}

			    }

			    // ECN 처리
			    qr = PersistenceHelper.manager.navigate(moldCO, "ecn", KETEcoEcnLink.class, false);
			    if (qr.hasMoreElements()) { // while (qr.hasMoreElements()) {
				KETEcoEcnLink ketEcoEcnLink = (KETEcoEcnLink) qr.nextElement();
				KETChangeNotice ecn = ketEcoEcnLink.getEcn();

				String ecnLifeCycleTemplateName = (ecn.getLifeCycleTemplate() != null) ? ecn.getLifeCycleTemplate()
				        .getName() : null;
				if (ecnLifeCycleTemplateName != null && ecnLifeCycleTemplateName.equalsIgnoreCase("KET_ECN_LC")) {

				    // 4M변경이 없을 경우
				    if (!hasMoreEcaWorking2nd) {
					// 완료일을 set 한다.
					ecn.setCompleteDate(DateUtil.getCurrentTimestamp());
					PersistenceHelper.manager.save(ecn);
					ecn = (KETChangeNotice) PersistenceHelper.manager.refresh(ecn);

					// ECN의 L/C를 '활동완료'으로 바꾼다.
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ecn,
					        State.toState("ACTIVITYCOMPLETED"));

				    } else {
					// ECN의 L/C를 '활동수행'으로 바꾼다.
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ecn, State.toState("EXCUTEACTIVITY"));

				    }

				}

			    }

			    KogDbUtil.log("금형 ECO ECN 시작 완료", "Success", moldCO, (String) null, null, null);

			} catch (Exception e) {

			    KogDbUtil.log("금형 ECO - ECN 처리 실패", "Fail", moldCO, e, null, null);
			}
		    }

		}

		if (moldCO.getLifeCycleState().equals(State.toState("REWORK"))) {

		    // todo 생성
		    KETWfmHelper.service.createWorkItem(moldCO);

		}

		// ECA 처리
		Object[] eca = EcmUtil.getChangeActivities(moldCO);
		if (!isPartErpInterFaceFail && eca != null) {
		    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

		    for (int cnt = 0; cnt < eca.length; cnt++) {
			KETMoldChangeActivity moldCA = (KETMoldChangeActivity) eca[cnt];

			// if (moldCO.getLifeCycleState().equals(State.toState("EXCUTEACTIVITY"))) {
			if ((moldCO.getLifeCycleState().equals(State.toState("EXCUTEACTIVITY"))) && (!moldCA.getActivityType().equals("4"))) {

			    // 상신후 반려받고 재작업하였을 경우
			    Vector<KETBomEcoHeader> bomVec = WorkflowSearchHelper.manager.getBOMfromMoldECA(moldCA);
			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				String attribute3 = bomHeader.getAttribute3();
				if (attribute3 != null && attribute3.equalsIgnoreCase("Y")) {
				    // 1. BOMHeader 의 Attribute3에 'N'를 넣어준다.
				    bomHeader.setAttribute3("N");
				    bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(bomHeader);
				    bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.refresh(bomHeader);

				    // 2. BOMHeader 의 상태를 'INWORK'로 바꾸어준다.
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));
				}

			    }

			    moldCA.setActivityStatus("INWORK");
			    PersistenceHelper.manager.save(moldCA);
			    moldCA = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(moldCA);

			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldCA, State.toState("INWORK"));
			}
			// Commented by MJOH, 2011-03-30
			// ECA???????completeActivity()??? ?????????????
			// else if( moldCO.getLifeCycleState().equals(State.toState("ACTIVITYCOMPLETED")) )
			// {
			// moldCA.setActivityStatus("WORKCOMPLETED");
			// PersistenceHelper.manager.modify(moldCA);
			// PersistenceHelper.manager.refresh(moldCA);
			//
			// LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)moldCA, State.toState("WORKCOMPLETED"));
			// }
			// else if (moldCO.getLifeCycleState().equals(State.toState("UNDERREVIEW"))) {
			else if ((moldCO.getLifeCycleState().equals(State.toState("UNDERREVIEW")))
			        && (!moldCA.getActivityType().equals("4"))) {

			    moldCA.setActivityStatus(moldCO.getLifeCycleState().toString());
			    PersistenceHelper.manager.modify(moldCA);

			    moldCA = (KETMoldChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldCA,
				    moldCO.getLifeCycleState());
			    PersistenceHelper.manager.refresh(moldCA);

			    if (VERBOSE)
				Kogger.info(getClass(), ">>>>>>>>>>>>>>>>>>> ??? ECA????? :" + moldCA.getLifeCycleState().getDisplay());

			    /*
		             * Vector<KETBomEcoHeader> bomVec = WorkflowSearchHelper.manager.getBOMfromMoldECA(moldCA);
		             */
			    Vector<KETBomEcoHeader> bomVec = new Vector<KETBomEcoHeader>();
			    QueryResult qrLink = PersistenceHelper.manager.navigate(moldCA, "bom", KETMoldECABomLink.class, false);
			    while (qrLink.hasMoreElements()) {
				KETMoldECABomLink ketMoldECABomLink = (KETMoldECABomLink) qrLink.nextElement();

				KETBomEcoHeader bomHeader = ketMoldECABomLink.getBom();
				bomVec.addElement(bomHeader);

				// 부품 상태도 검토중으로 변경
				String beforePartOid = ketMoldECABomLink.getBeforePartOid();
				WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
				String partNumber = wtPart.getNumber();
				String preVersion = ketMoldECABomLink.getPreVersion();
				String afterVersion = ketMoldECABomLink.getAfterVersion();
				// 초도라는 얘기
				if (afterVersion == null || preVersion.equals(afterVersion)) {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, preVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}
				// 설변이라는 얘기
				else {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, afterVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}

				if (wtPart != null) {
				    // HEENEETODO : 방어코드 Part에 L/C를 reassign 한다.
				    LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC",
					    wtPart.getContainerReference());
				    wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart,
					    partLifeCycle.getLifeCycleTemplateReference());

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) wtPart, moldCA.getLifeCycleState());
				}
			    }
			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, moldCA.getLifeCycleState());

			    }

			    Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA);

			    for (int i = 0; i < epmVec.size(); i++) {
				EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, moldCA.getLifeCycleState());
			    }
			} else if (moldCO.getLifeCycleState().equals(State.toState("APPROVED"))) {
			    if (!moldCA.getActivityType().equals("4")) {
				moldCA.setActivityStatus(moldCO.getLifeCycleState().toString());
				PersistenceHelper.manager.modify(moldCA);

				moldCA = (KETMoldChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldCA,
				        moldCO.getLifeCycleState());
				PersistenceHelper.manager.refresh(moldCA);
				if (VERBOSE)
				    Kogger.info(getClass(), ">>>>>>>>>>>>>>>>>>> ??? ECA????? :" + moldCA.getLifeCycleState().getDisplay());

				// 도면처리
				/*
		                 * Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA); for (int i = 0; i <
		                 * epmVec.size(); i++) { EPMDocument epmDoc = (EPMDocument)
		                 * ObjectUtil.getLatestVersion(epmVec.elementAt(i));
		                 * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, moldCA.getLifeCycleState()); }
		                 */
			    } else {
				moldCA.setActivityStatus("INWORK");
				PersistenceHelper.manager.modify(moldCA);
				PersistenceHelper.manager.refresh(moldCA);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldCA, State.toState("INWORK"));
			    }
			}
			// else if (moldCO.getLifeCycleState().equals(State.toState("REWORK"))) {
			else if ((moldCO.getLifeCycleState().equals(State.toState("REWORK"))) && (!moldCA.getActivityType().equals("4"))) {

			    // 초도인지 아닌지
			    String changeReason = StringUtils.stripToEmpty(moldCO.getChangeReason());
			    boolean isTheFirst = (changeReason.lastIndexOf("REASON_10") > -1);

			    // 상신후 반려받고 재작업하였을 경우
			    // 부품
			    Vector<KETBomEcoHeader> bomVec = WorkflowSearchHelper.manager.getBOMfromMoldECA(moldCA);

			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);

				if (isTheFirst) {

				    String attribute3 = bomHeader.getAttribute3();
				    if (attribute3 != null && attribute3.equalsIgnoreCase("Y")) {
					// 1. BOMHeader 의 Attribute3에 'N'를 넣어준다.
					bomHeader.setAttribute3("N");
					bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(bomHeader);
					bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.refresh(bomHeader);

					// 2. BOMHeader 의 상태를 'INWORK'로 바꾸어준다.
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));
				    }

				} else {

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, moldCA.getLifeCycleState());

				}
			    }

			    // 도면
			    Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromMoldECA(moldCA);
			    for (int i = 0; i < epmVec.size(); i++) {
				EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));

				if (isTheFirst) {

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("INWORK"));

				} else {

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, moldCA.getLifeCycleState());

				}

			    }

			    // ECA
			    if (isTheFirst) {

				moldCA.setActivityStatus(State.toState("INWORK").toString());

			    } else {

				moldCA.setActivityStatus(moldCO.getLifeCycleState().toString());

			    }

			    moldCA.setCompleteDate(null);
			    PersistenceHelper.manager.save(moldCA);
			    moldCA = (KETMoldChangeActivity) PersistenceHelper.manager.refresh(moldCA);

			    if (isTheFirst) {

				moldCA = (KETMoldChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldCA,
				        State.toState("INWORK"));

			    } else {

				moldCA = (KETMoldChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) moldCA,
				        moldCO.getLifeCycleState());

			    }
			    // if (VERBOSE) Kogger.info(getClass(), ">>>>>>>>>>>>>>>>>>> ??? ECA????? :" +
			    // moldCA.getLifeCycleState().getDisplay());

			}
		    }
		}

	    } else if (obj instanceof KETProdChangeOrder) // ??? ?????? ?????????????
	    {
		KETProdChangeOrder prodCO = (KETProdChangeOrder) obj;

		boolean isPartErpInterFaceFail = false;
		if (prodCO.getLifeCycleState().equals(State.toState("APPROVED"))) {

		    Kogger.info(getClass(), "####################################################");
		    Kogger.info(getClass(), "############ ECO -> SAP Interface START ############");
		    Kogger.info(getClass(), "## KETProdChangeOrder is " + prodCO.getEcoId() + ", " + prodCO.toString());

		    // BOM, ERP와의 인터페이스
		    String changeReason = StringUtils.stripToEmpty(prodCO.getChangeReason());
		    boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);
		    Kogger.info(getClass(), "## changeReason is " + changeReason);
		    Kogger.info(getClass(), "## isTheFirst is " + isTheFirst);

		    boolean infResult = false;
		    String ecoLongValue = KETObjectUtil.getIda2a2(prodCO.toString());

		    try {

			// 초도일 경우
			if (isTheFirst) {
			    Boolean[] bomInterfaceResult = KETBomHelper.service.getBomInterface2(ecoLongValue, true);
			    infResult = bomInterfaceResult[0];
			    isPartErpInterFaceFail = bomInterfaceResult[1];

			}
			// 설변일 경우
			else {
			    Boolean[] bomInterfaceResult = KETBomHelper.service.getBomEcoInterface(ecoLongValue, true);
			    infResult = bomInterfaceResult[0];
			    isPartErpInterFaceFail = bomInterfaceResult[1];

			}

			Kogger.info(getClass(), "infResult is " + infResult);
			Kogger.info(getClass(), "############ ECO -> SAP Interface FINISH ###########");
			Kogger.info(getClass(), "####################################################");

		    } catch (Exception e) {
			infResult = false;
		    }

		    // 실패시 메일을 보낸다.
		    if (!infResult) {

			try {
			    /*
		             * WTPrincipalReference toUser = prodCO.getCreator(); Hashtable contentHash = MailUtil.getMailContent("infFail",
		             * prodCO, toUser.getName()); String templateName = CommonUtil.getMailTemplateName(toUser.getName(),
		             * "InterfaceFailNotice"); String contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash,
		             * templateName); Hashtable hash = MailUtil.prepareMailInfoHash(toUser, contents); if (hash != null)
		             * MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
		             */

			    // ECO 상태가 활동수행-->활동완료로 변경되어 최종 승인시 김근우 Mail 처리
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    toUserList.add((WTUser) prodCO.getCreator().getPrincipal());
			    WTUser admin = (WTUser) SessionHelper.manager.getAdministrator();

			    Kogger.info(getClass(), admin);
			    People pp = PeopleHelper.manager.getPeople(admin.getName());
			    Kogger.info(getClass(), pp.getEmail());

			    toUserList.add(admin);
			    KETMailHelper.service.sendFormMail("08124", "InterfaceFailNotice.html", prodCO,
				    (WTUser) SessionHelper.manager.getPrincipal(), toUserList);

			    KogDbUtil.log("제품 ECO 승인완료 실패 메일 발송", "Success", prodCO, (String) null, null, null);

			} catch (Exception e) {

			    KogDbUtil.log("제품 ECO 승인완료 실패 메일 발송", "Fail", prodCO, e, null, null);
			}
		    } else {
			KogDbUtil.log("제품 ECO ECN전 ERP 및 후처리 완료", "Success", prodCO, (String) null, null, null);
		    }

		    if (!isPartErpInterFaceFail) {

			try {

			    // ECN 처리
			    boolean hasMoreEcaWorking2nd = false;
			    QueryResult qr = PersistenceHelper.manager.navigate(prodCO, "prodECA", KETProdChangeActivityLink.class, false);
			    while (qr.hasMoreElements()) {
				KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) qr.nextElement();
				KETProdChangeActivity eca = ketProdChangeActivityLink.getProdECA();
				String ecaActivityType = StringUtils.stripToEmpty(eca.getActivityType());

				String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
				// 담당자가 있는 ECA와 ECN만을 계산로직에 포함시킨다.
				if (!workerId.equals("")) {

				    // ECN(문서, 활동)일 경우
				    if (ecaActivityType.equals("3") || ecaActivityType.equals("4")) {
					hasMoreEcaWorking2nd = true;
					break;

				    }

				}

			    }

			    qr = PersistenceHelper.manager.navigate(prodCO, "ecn", KETEcoEcnLink.class, false);
			    if (qr.hasMoreElements()) { // while (qr.hasMoreElements()) {
				KETEcoEcnLink ketEcoEcnLink = (KETEcoEcnLink) qr.nextElement();
				KETChangeNotice ecn = ketEcoEcnLink.getEcn();

				String ecnLifeCycleTemplateName = (ecn.getLifeCycleTemplate() != null) ? ecn.getLifeCycleTemplate()
				        .getName() : null;
				if (ecnLifeCycleTemplateName != null && ecnLifeCycleTemplateName.equalsIgnoreCase("KET_ECN_LC")) {

				    // ECN(문서, 활동)이 없을 경우
				    if (!hasMoreEcaWorking2nd) {
					// 완료일을 set 한다.
					ecn.setCompleteDate(DateUtil.getCurrentTimestamp());
					PersistenceHelper.manager.save(ecn);
					ecn = (KETChangeNotice) PersistenceHelper.manager.refresh(ecn);

					// ECN의 L/C를 '활동완료'으로 바꾼다.
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ecn,
					        State.toState("ACTIVITYCOMPLETED"));

				    } else {
					// ECN의 L/C를 '활동수행'으로 바꾼다.
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ecn, State.toState("EXCUTEACTIVITY"));

				    }

				}

			    }

			    KogDbUtil.log("제품 ECO ECN 시작 완료", "Success", prodCO, (String) null, null, null);

			} catch (Exception e) {

			    KogDbUtil.log("제품 ECO - ECN 처리 실패", "Fail", prodCO, e, null, null);
			}
		    }

		}

		if (prodCO.getLifeCycleState().equals(State.toState("REWORK"))) {

		    // todo 생성
		    KETWfmHelper.service.createWorkItem(prodCO);

		}

		// ECA 처리
		Object[] eca = EcmUtil.getChangeActivities(prodCO);
		if (!isPartErpInterFaceFail && eca != null) {
		    KETBOMQueryBean ketBOMQueryBean = new KETBOMQueryBean();

		    for (int cnt = 0; cnt < eca.length; cnt++) {
			KETProdChangeActivity prodCA = (KETProdChangeActivity) eca[cnt];
			if ((prodCO.getLifeCycleState().equals(State.toState("EXCUTEACTIVITY")))
			        && (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4"))) {

			    // 상신후 반려받고 재작업하였을 경우
			    Vector<KETBomEcoHeader> bomVec = WorkflowSearchHelper.manager.getBOMfromProdECA(prodCA);
			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				String attribute3 = bomHeader.getAttribute3();
				if (attribute3 != null && attribute3.equalsIgnoreCase("Y")) {
				    // 1. BOMHeader 의 Attribute3에 'N'를 넣어준다.
				    bomHeader.setAttribute3("N");
				    bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(bomHeader);
				    bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.refresh(bomHeader);

				    // 2. BOMHeader 의 상태를 'INWORK'로 바꾸어준다.
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));
				}

			    }

			    prodCA.setActivityStatus("INWORK");
			    PersistenceHelper.manager.save(prodCA);
			    prodCA = (KETProdChangeActivity) PersistenceHelper.manager.refresh(prodCA);

			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodCA, State.toState("INWORK"));
			}
			// Commented by MJOH, 2011-03-30
			// ECA???????completeActivity()??? ?????????????
			// else if( (prodCO.getLifeCycleState().equals(State.toState("ACTIVITYCOMPLETED"))) &&
			// (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4")) )
			// {
			// prodCA.setActivityStatus("WORKCOMPLETED");
			// PersistenceHelper.manager.modify(prodCA);
			// PersistenceHelper.manager.refresh(prodCA);
			//
			// LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)prodCA, State.toState("WORKCOMPLETED"));
			// }
			else if ((prodCO.getLifeCycleState().equals(State.toState("UNDERREVIEW")))
			        && (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4"))) {

			    prodCA.setActivityStatus(prodCO.getLifeCycleState().toString());
			    PersistenceHelper.manager.modify(prodCA);

			    prodCA = (KETProdChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodCA,
				    prodCO.getLifeCycleState());
			    PersistenceHelper.manager.refresh(prodCA);

			    if (VERBOSE)
				Kogger.info(getClass(), ">>>>>>>>>>>>>>>>>>> ??? ECA????? :" + prodCA.getLifeCycleState().getDisplay());

			    /*
		             * Vector<KETBomEcoHeader> bomVec = WorkflowSearchHelper.manager.getBOMfromProdECA(prodCA);
		             */
			    Vector<KETBomEcoHeader> bomVec = new Vector<KETBomEcoHeader>();
			    QueryResult qrLink = PersistenceHelper.manager.navigate(prodCA, "bom", KETProdECABomLink.class, false);
			    while (qrLink.hasMoreElements()) {
				KETProdECABomLink ketProdECABomLink = (KETProdECABomLink) qrLink.nextElement();

				KETBomEcoHeader bomHeader = ketProdECABomLink.getBom();
				bomVec.addElement(bomHeader);

				// 부품 상태도 검토중으로 변경
				String beforePartOid = ketProdECABomLink.getBeforePartOid();
				WTPart wtPart = (WTPart) CommonUtil.getObject(beforePartOid);
				String partNumber = wtPart.getNumber();
				String preVersion = ketProdECABomLink.getPreVersion();
				String afterVersion = ketProdECABomLink.getAfterVersion();
				// 초도라는 얘기
				if (afterVersion == null || preVersion.equals(afterVersion)) {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, preVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}
				// 설변이라는 얘기
				else {
				    String partOid = ketBOMQueryBean.getPartOid(partNumber, afterVersion);
				    wtPart = (WTPart) CommonUtil.getObject(partOid);
				}

				if (wtPart != null) {
				    // HEENEETODO : 방어코드 Part에 L/C를 reassign 한다.
				    LifeCycleTemplate partLifeCycle = LifeCycleHelper.service.getLifeCycleTemplate("KET_PART_LC",
					    wtPart.getContainerReference());
				    wtPart = (WTPart) LifeCycleHelper.service.reassign(wtPart,
					    partLifeCycle.getLifeCycleTemplateReference());

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) wtPart, prodCA.getLifeCycleState());
				}
			    }
			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, prodCA.getLifeCycleState());
			    }

			    Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA);

			    for (int i = 0; i < epmVec.size(); i++) {
				EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, prodCA.getLifeCycleState());
			    }
			} else if (prodCO.getLifeCycleState().equals(State.toState("APPROVED"))) {
			    if (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4")) {
				prodCA.setActivityStatus(prodCO.getLifeCycleState().toString());
				PersistenceHelper.manager.modify(prodCA);
				prodCA = (KETProdChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodCA,
				        prodCO.getLifeCycleState());
				PersistenceHelper.manager.refresh(prodCA);

				if (VERBOSE)
				    Kogger.info(getClass(), ">>>>>>>>>>>>>>>>>>> ??? ECA????? :" + prodCA.getLifeCycleState().getDisplay());

				// 도면 처리
				/*
		                 * Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA); for (int i = 0; i <
		                 * epmVec.size(); i++) { EPMDocument epmDoc = (EPMDocument)
		                 * ObjectUtil.getLatestVersion(epmVec.elementAt(i));
		                 * LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, prodCA.getLifeCycleState()); }
		                 */
			    } else { // activity type ??3????? ?????? ??? ?????? ???(??????)
				prodCA.setActivityStatus("INWORK");
				PersistenceHelper.manager.modify(prodCA);
				prodCA = (KETProdChangeActivity) PersistenceHelper.manager.refresh(prodCA);
				LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodCA, State.toState("INWORK"));
			    }
			} else if ((prodCO.getLifeCycleState().equals(State.toState("REWORK")))
			        && (!prodCA.getActivityType().equals("3") && !prodCA.getActivityType().equals("4"))) {

			    // 초도인지 아닌지
			    String changeReason = StringUtils.stripToEmpty(prodCO.getChangeReason());
			    boolean isTheFirst = (changeReason.lastIndexOf("REASON_12") > -1);

			    // 상신후 반려받고 재작업하였을 경우
			    // 부품
			    Vector<KETBomEcoHeader> bomVec = WorkflowSearchHelper.manager.getBOMfromProdECA(prodCA);
			    for (int i = 0; i < bomVec.size(); i++) {
				KETBomEcoHeader bomHeader = bomVec.elementAt(i);
				// KETBomHelper.service.updateEndWorkingFlag(bomHeader.getEcoHeaderNumber(), "1");

				if (isTheFirst) {
				    String attribute3 = bomHeader.getAttribute3();
				    if (attribute3 != null && attribute3.equalsIgnoreCase("Y")) {
					// 1. BOMHeader 의 Attribute3에 'N'를 넣어준다.
					bomHeader.setAttribute3("N");
					bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.save(bomHeader);
					bomHeader = (KETBomEcoHeader) PersistenceHelper.manager.refresh(bomHeader);

					// 2. BOMHeader 의 상태를 'INWORK'로 바꾸어준다.
					LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, State.toState("INWORK"));
				    }

				} else {

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) bomHeader, prodCA.getLifeCycleState());

				}

			    }

			    // 도면
			    Vector<EPMDocument> epmVec = WorkflowSearchHelper.manager.getEPMfromProdECA(prodCA);
			    for (int i = 0; i < epmVec.size(); i++) {
				EPMDocument epmDoc = (EPMDocument) ObjectUtil.getLatestVersion(epmVec.elementAt(i));

				if (isTheFirst) {

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, State.toState("INWORK"));

				} else {

				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epmDoc, prodCA.getLifeCycleState());

				}

			    }

			    // ECA
			    if (isTheFirst) {

				prodCA.setActivityStatus(State.toState("INWORK").toString());

			    } else {

				prodCA.setActivityStatus(prodCO.getLifeCycleState().toString());

			    }

			    prodCA.setCompleteDate(null);
			    PersistenceHelper.manager.save(prodCA);
			    prodCA = (KETProdChangeActivity) PersistenceHelper.manager.refresh(prodCA);

			    if (isTheFirst) {

				prodCA = (KETProdChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodCA,
				        State.toState("INWORK"));

			    } else {

				prodCA = (KETProdChangeActivity) LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) prodCA,
				        prodCO.getLifeCycleState());

			    }

			    // if (VERBOSE) Kogger.info(getClass(), ">>>>>>>>>>>>>>>>>>> ??? ECA????? :" +
			    // prodCA.getLifeCycleState().getDisplay());

			}
		    }
		}
	    } else if (obj instanceof KETMoldChangeActivity) {
		KETMoldChangeActivity moldCA = (KETMoldChangeActivity) obj;

		// Todo에서 '작업완료'하였을 경우
		if (moldCA.getLifeCycleState().equals(State.toState("WORKCOMPLETED"))) {

		    String activityType = StringUtils.stripToEmpty(moldCA.getActivityType());

		    KETMoldChangeOrder eco = moldCA.getMoldECO();

		    /*
	             * QueryResult qr = PersistenceHelper.manager.navigate(moldCA, "moldECO", KETMoldChangeActivityLink.class, false); if
	             * (qr.hasMoreElements()) { KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink)
	             * qr.nextElement(); eco = ketMoldChangeActivityLink.getMoldECO(); }
	             */
		    boolean hasMoreEcaWorking1st = false;
		    boolean hasMoreEcaWorking2nd = false;
		    QueryResult qr = PersistenceHelper.manager.navigate(eco, "moldECA", KETMoldChangeActivityLink.class, false);
		    while (qr.hasMoreElements()) {
			KETMoldChangeActivityLink ketMoldChangeActivityLink = (KETMoldChangeActivityLink) qr.nextElement();
			KETMoldChangeActivity eca = ketMoldChangeActivityLink.getMoldECA();
			String ecaActivityType = StringUtils.stripToEmpty(eca.getActivityType());

			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			// 담당자가 있는 ECA와 ECN만을 계산로직에 포함시킨다.
			if (!workerId.equals("")) {

			    // 도면, 부품, 표준품리스트일 경우
			    if (activityType.equals("1") || activityType.equals("2") || activityType.equals("3")) {

				if (ecaActivityType.equals("1") || ecaActivityType.equals("2") || ecaActivityType.equals("3")) {

				    if (!eca.getState().getState().equals(State.toState("WORKCOMPLETED"))) {
					hasMoreEcaWorking1st = true;
					break;
				    }

				}

			    }
			    // 4M변경일 경우
			    else if (activityType.equals("4")) {

				if (ecaActivityType.equals("4")) {

				    if (!eca.getState().getState().equals(State.toState("WORKCOMPLETED"))) {
					hasMoreEcaWorking2nd = true;
					break;
				    }

				}

			    }

			}

		    }

		    // ECA가 모두 끝났을 경우 ECN을 '활동완료'로 바꾼다.
		    // 도면, 부품, 표준품리스트일 경우
		    if (activityType.equals("1") || activityType.equals("2") || activityType.equals("3")) {
			if (!hasMoreEcaWorking1st) {
			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eco, State.toState("ACTIVITYCOMPLETED"));

			}

		    }
		    // 4M변경일 경우
		    else if (activityType.equals("4")) {

			// 금형일 경우 현재 ECA는 4M변경밖에 없다. 금형부품관리에 완료일을 추가한다.
			qr = PersistenceHelper.manager.navigate(eco, "part", KETMoldECOPartLink.class, false);
			while (qr.hasMoreElements()) {
			    KETMoldECOPartLink ketMoldECOPartLink = (KETMoldECOPartLink) qr.nextElement();
			    WTPart diePart = ketMoldECOPartLink.getPart();

			    // TODO : 하지않아도 될지도 모른다.

			}

			// ECN처리
			if (!hasMoreEcaWorking2nd) {

			    List<WTUser> toUserList = new ArrayList<WTUser>();

			    qr = PersistenceHelper.manager.navigate(eco, "ecn", KETEcoEcnLink.class, false);
			    if (qr.hasMoreElements()) {
				KETEcoEcnLink ketEcoEcnLink = (KETEcoEcnLink) qr.nextElement();
				KETChangeNotice ecn = ketEcoEcnLink.getEcn();

				// 완료일을 set 한다.
				ecn.setCompleteDate(DateUtil.getCurrentTimestamp());
				PersistenceHelper.manager.save(ecn);
				ecn = (KETChangeNotice) PersistenceHelper.manager.refresh(ecn);

				String ecnLifeCycleTemplateName = (ecn.getLifeCycleTemplate() != null) ? ecn.getLifeCycleTemplate()
				        .getName() : null;
				if (ecnLifeCycleTemplateName != null && ecnLifeCycleTemplateName.equalsIgnoreCase("KET_ECN_LC")) {

				    // ECN의 L/C를 '활동완료'로 바꾼다.
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ecn, State.toState("ACTIVITYCOMPLETED"));
				    // toUserList set
				    WTUser ecaCharge = (WTUser) CommonUtil.getObject(moldCA.getWorkerId());
				    toUserList.add(ecaCharge);

				}

			    }
			    toUserList.add((WTUser) eco.getCreator().getPrincipal());
			    // ECN완료 메일발송 김근우
			    KETMailHelper.service.sendFormMail("08028", "NoticeMailLine1.html", moldCA, (WTUser) SessionHelper.manager
				    .getPrincipalReference().getPrincipal(), toUserList);
			}
		    }

		} // if (moldCA.getLifeCycleState().equals(State.toState("WORKCOMPLETED"))) {

	    } else if (obj instanceof KETProdChangeActivity) {
		KETProdChangeActivity prodCA = (KETProdChangeActivity) obj;

		// Todo에서 '작업완료'하였을 경우
		if (prodCA.getLifeCycleState().equals(State.toState("WORKCOMPLETED"))) {

		    String activityType = StringUtils.stripToEmpty(prodCA.getActivityType());

		    KETProdChangeOrder eco = prodCA.getProdECO();

		    boolean hasMoreEcaWorking1st = false;
		    boolean hasMoreEcaWorking2nd = false;
		    QueryResult qr = PersistenceHelper.manager.navigate(eco, "prodECA", KETProdChangeActivityLink.class, false);
		    while (qr.hasMoreElements()) {
			KETProdChangeActivityLink ketProdChangeActivityLink = (KETProdChangeActivityLink) qr.nextElement();
			KETProdChangeActivity eca = ketProdChangeActivityLink.getProdECA();
			String ecaActivityType = StringUtils.stripToEmpty(eca.getActivityType());

			String workerId = StringUtils.stripToEmpty(eca.getWorkerId());
			// 담당자가 있는 ECA와 ECN만을 계산로직에 포함시킨다.
			if (!workerId.equals("")) {

			    // 도면, 부품일 경우
			    if (activityType.equals("1") || activityType.equals("2")) {

				if (ecaActivityType.equals("1") || ecaActivityType.equals("2")) {

				    if (!eca.getState().getState().equals(State.toState("WORKCOMPLETED"))) {
					hasMoreEcaWorking1st = true;
					break;
				    }

				}

			    }
			    // ECN(문서, 활동)일 경우
			    else if (activityType.equals("3") || activityType.equals("4")) {

				if (ecaActivityType.equals("3") || ecaActivityType.equals("4")) {

				    if (!eca.getState().getState().equals(State.toState("WORKCOMPLETED"))) {
					hasMoreEcaWorking2nd = true;
					break;
				    }

				}

			    }

			}

		    }

		    // ECA가 모두 끝났을 경우 ECN을 '활동완료'로 바꾼다.
		    // 도면, 부품일 경우
		    if (activityType.equals("1") || activityType.equals("2")) {
			if (!hasMoreEcaWorking1st) {
			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) eco, State.toState("ACTIVITYCOMPLETED"));

			}

		    }
		    // ECN(문서, 활동)일 경우
		    else if (activityType.equals("3") || activityType.equals("4")) {

			// ECN처리
			if (!hasMoreEcaWorking2nd) {
			    List<WTUser> toUserList = new ArrayList<WTUser>();
			    qr = PersistenceHelper.manager.navigate(eco, "ecn", KETEcoEcnLink.class, false);
			    if (qr.hasMoreElements()) {
				KETEcoEcnLink ketEcoEcnLink = (KETEcoEcnLink) qr.nextElement();
				KETChangeNotice ecn = ketEcoEcnLink.getEcn();

				// 완료일을 set 한다.
				ecn.setCompleteDate(DateUtil.getCurrentTimestamp());
				PersistenceHelper.manager.save(ecn);
				ecn = (KETChangeNotice) PersistenceHelper.manager.refresh(ecn);

				String ecnLifeCycleTemplateName = (ecn.getLifeCycleTemplate() != null) ? ecn.getLifeCycleTemplate()
				        .getName() : null;
				if (ecnLifeCycleTemplateName != null && ecnLifeCycleTemplateName.equalsIgnoreCase("KET_ECN_LC")) {

				    // ECN의 L/C를 '활동완료'로 바꾼다.
				    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) ecn, State.toState("ACTIVITYCOMPLETED"));
				    // toUserList set
				    WTUser ecaCharge = (WTUser) CommonUtil.getObject(prodCA.getWorkerId());
				    toUserList.add(ecaCharge);

				}

			    }

			    toUserList.add((WTUser) eco.getCreator().getPrincipal());
			    // ECN완료 메일발송 김근우
			    KETMailHelper.service.sendFormMail("08028", "NoticeMailLine1.html", prodCA, (WTUser) SessionHelper.manager
				    .getPrincipalReference().getPrincipal(), toUserList);
			}
		    }

		} // if (moldCA.getLifeCycleState().equals(State.toState("WORKCOMPLETED"))) {

	    } else if (obj instanceof KETEarlyWarningEndReq) {
		KETEarlyWarningEndReq ew = (KETEarlyWarningEndReq) obj;
		if (ew.getLifeCycleState().equals(State.toState("APPROVED"))) {
		    KETEarlyWarningStep step = WorkflowSearchHelper.manager.getEWStep(ew);
		    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) step, State.toState("EWRDECISION"));

		    try {
			// 초기유동관리 해제신청 등록시
			List<WTUser> toUserList = new ArrayList<WTUser>();
			toUserList.add((WTUser) ew.getCreator().getPrincipal());
			KETMailHelper.service.sendFormMail("08045", "NoticeMailLine1.html", ew, (WTUser) ew.getCreator().getPrincipal(),
			        toUserList);
		    } catch (Exception e) {

		    }

		    // 2010.02.09 kkh start
		    if (mailEnable) {
			KETEarlyWarning ketEarlyWarning = WorkflowSearchHelper.manager.getEW(ew);
			String toUserID = ketEarlyWarning.getCreatorName();
			String contents = "";

			try {
			    Hashtable contentHash = MailUtil.getMailContent("ewsend", ketEarlyWarning, toUserID);

			    /*
		             * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
		             */
			    String templateName = CommonUtil.getMailTemplateName(toUserID, "ApprovalNoticeMail");

			    // contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"ApprovalNoticeMail.html");
			    contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
			    // //////////////////////////////////////////////////////////////////////////////////////////////////

			    Hashtable hash = MailUtil.prepareMailInfoHash(ketEarlyWarning.getCreator(), contents);

			    if (hash != null)
				MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
		    }
		    // 2010.02.09 kkh end
		}
	    } else if (obj instanceof KETWfmMultiApprovalRequest) { // ?????? ??? ???
		KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) obj;
		Vector newVec = WorkflowSearchHelper.manager.getEPMList(multiReq);
		Vector docVec = WorkflowSearchHelper.manager.getDocList(multiReq);

		if (newVec != null && newVec.size() != 0) {
		    for (int cnt = 0; cnt < newVec.size(); cnt++) {
			EPMDocument epm = (EPMDocument) newVec.get(cnt);
			epm = (EPMDocument) ObjectUtil.getLatestVersion(epm);
			LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) epm, multiReq.getLifeCycleState());
		    }
		} else if (docVec != null && docVec.size() != 0) {
		    for (int cnt = 0; cnt < docVec.size(); cnt++) {
			WTDocument doc = (WTDocument) docVec.get(cnt);
			doc = (WTDocument) ObjectUtil.getLatestVersion(doc);
			LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) doc, multiReq.getLifeCycleState());
		    }
		}

		// Vector docVec = WorkflowSearchHelper.manager.getDocList(multiReq);
		// if(docVec != null){
		// for(int cnt=0; cnt<docVec.size(); cnt++){
		// KETProjectDocument d = (KETProjectDocument)docVec.get(cnt);
		// LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)d, multiReq.getLifeCycleState());
		// }
		// }
	    }
	    // DQM
	    else if (obj instanceof KETDqmRaise || obj instanceof KETDqmAction || obj instanceof KETDrawingDistRequest
		    || obj instanceof KETSampleRequest) {

		// 후처리 및 메일 발송 처리
		try {
		    State state = ((LifeCycleManaged) obj).getLifeCycleState();

		    if (state.equals(State.toState("REJECTED"))) {
			if (obj instanceof KETDqmRaise) {
			    KETDqmRaise ketDqmRaise = (KETDqmRaise) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmRaise));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);

			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];

				ketDqmHeader.setDqmStateCode("RAISEREJECTED");
				ketDqmHeader.setDqmStateName("반려됨");

				PersistenceHelper.manager.modify(ketDqmHeader);
			    }

			} else if (obj instanceof KETDqmAction) {
			    KETDqmAction ketDqmAction = (KETDqmAction) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmAction));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);
			    KETDqmHeader ketDqmHeader = null;
			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				ketDqmHeader = (KETDqmHeader) tempObj[0];

				ketDqmHeader.setDqmStateCode("ACTIONREJECTED");
				ketDqmHeader.setDqmStateName("반려됨");

				PersistenceHelper.manager.modify(ketDqmHeader);
			    }

			    // todo 종료
			    query = new QuerySpec();
			    sc = null;
			    int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class, true);

			    sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmHeader));
			    query.appendWhere(sc, new int[] { idxTodoActivity });
			    query.appendAnd();
			    sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE, SearchCondition.EQUAL,
				    "DQM_ACTION");

			    query.appendWhere(sc, new int[] { idxTodoActivity });

			    qr = PersistenceHelper.manager.find(query);
			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
				WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity);
			    }

			} else if (obj instanceof KETSampleRequest) {
			    KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;

			    ketSampleRequest.setSampleRequestStateCode("REJECTED");
			    ketSampleRequest.setSampleRequestStateName("반려됨");
			    PersistenceHelper.manager.modify(ketSampleRequest);
			}

		    } else if (state.equals(State.toState("UNDERREVIEW"))) {
			if (obj instanceof KETDqmRaise) {
			    KETDqmRaise ketDqmRaise = (KETDqmRaise) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmRaise));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);

			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];

				ketDqmHeader.setDqmStateCode("RAISEUNDERREVIEW");
				ketDqmHeader.setDqmStateName("제기승인");

				ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);
				DqmHelper.service.deleteDQMWorkItemByInwork(ketDqmHeader); // 등록중 TO-DO 삭제
			    }

			} else if (obj instanceof KETDqmAction) {
			    KETDqmAction ketDqmAction = (KETDqmAction) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmAction));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);

			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];

				ketDqmHeader.setDqmStateCode("ACTIONUNDERREVIEW");
				ketDqmHeader.setDqmStateName("검토승인");

				PersistenceHelper.manager.modify(ketDqmHeader);
			    }

			} else if (obj instanceof KETSampleRequest) {
			    KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;

			    ketSampleRequest.setSampleRequestStateCode("UNDERREVIEW");
			    ketSampleRequest.setSampleRequestStateName("검토중");
			    PersistenceHelper.manager.modify(ketSampleRequest);
			}

		    } else if (state.equals(State.toState("REWORK"))) {
			if (obj instanceof KETDqmRaise) {
			    KETDqmRaise ketDqmRaise = (KETDqmRaise) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmRaise));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);

			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];

				ketDqmHeader.setDqmStateCode("RAISEINWORK");
				ketDqmHeader.setDqmStateName("등록중");

				PersistenceHelper.manager.modify(ketDqmHeader);
				DqmHelper.service.createDQMWorkItemByInwork(ketDqmHeader); // 등록중 TO-DO 생성
			    }

			} else if (obj instanceof KETDqmAction) {

			    KETDqmAction ketDqmAction = (KETDqmAction) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmAction));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);
			    KETDqmHeader ketDqmHeader = null;
			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				ketDqmHeader = (KETDqmHeader) tempObj[0];

				ketDqmHeader.setDqmStateCode("ACTIONINWORK");
				ketDqmHeader.setDqmStateName("담당자접수");

				PersistenceHelper.manager.modify(ketDqmHeader);
			    }

			    // todo 생성
			    KETWfmHelper.service.createWorkItem(ketDqmHeader);

			} else if (obj instanceof KETSampleRequest) {
			    KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;

			    ketSampleRequest.setSampleRequestStateCode("REWORK");
			    ketSampleRequest.setSampleRequestStateName("재작업");
			    PersistenceHelper.manager.modify(ketSampleRequest);
			}

		    } else if (state.equals(State.toState("APPROVED"))) {

			if (obj instanceof KETDqmRaise) {
			    KETDqmRaise ketDqmRaise = (KETDqmRaise) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmRaise));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);
			    KETDqmHeader ketDqmHeader = null;
			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				ketDqmHeader = (KETDqmHeader) tempObj[0];

				ketDqmHeader.setDqmStateCode("RAISEAPPROVED");
				ketDqmHeader.setDqmStateName("담당자접수");

				ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);
			    }

			    // PM이 담당자지정하는 TO-DO 절차를 생략하도록 변경함, 바로 검토담당자에게 TO-DO를 생성해준다 2021.10.05
			    WTUser wtActionUser = ketDqmHeader.getRaise().getActionUser();

			    if (wtActionUser != null && ketDqmHeader.getAction() == null) {
				KETDqmDTO ketDqmDTO = new KETDqmDTO();
				ketDqmDTO.setActionUserOid(CommonUtil.getOIDString(wtActionUser));
				ketDqmDTO.setToDate("");
				ketDqmDTO.setDrawingOutDate("");
				ketDqmDTO.setMoldModifyDate("");
				ketDqmDTO.setTrustTestDate("");
				ketDqmDTO.setOid(CommonUtil.getOIDString(ketDqmHeader));
				DqmHelper.service.saveAction(ketDqmDTO);
			    }
			    // ketDqmHeader = (KETDqmHeader)PersistenceHelper.manager.refresh(ketDqmHeader);
			    // todo 생성
			    // KETWfmHelper.service.createWorkItem(ketDqmHeader); //saveAction에서 to-do생성을 하므로 주석처리 2021.10.05
			    if (mailEnable) {
				// 메일 발송
			    }

			} else if (obj instanceof KETDqmAction) {
			    KETDqmAction ketDqmAction = (KETDqmAction) obj;

			    QuerySpec query = new QuerySpec();
			    SearchCondition sc = null;
			    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

			    sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
				    CommonUtil.getOIDLongValue(ketDqmAction));
			    query.appendWhere(sc, new int[] { idxHeaer });

			    QueryResult qr = PersistenceHelper.manager.find(query);
			    KETDqmHeader ketDqmHeader = null;
			    while (qr.hasMoreElements()) {
				Object[] tempObj = (Object[]) qr.nextElement();
				ketDqmHeader = (KETDqmHeader) tempObj[0];

				// ketDqmHeader.setDqmStateCode("ACTIONAPPROVED");
				// ketDqmHeader.setDqmStateName("검토확인");

				ketDqmHeader.setDqmStateCode("ACTIONPROGRESS");
				ketDqmHeader.setDqmStateName("개선안진행");

				ketDqmHeader = (KETDqmHeader) PersistenceHelper.manager.modify(ketDqmHeader);
			    }
			    // todo 종료

			    /*
		             * query = new QuerySpec(); sc = null; int idxTodoActivity = query.appendClassList(KETDqmTodoAtivity.class,
		             * true);
		             * 
		             * sc = new SearchCondition(KETDqmTodoAtivity.class, "headerReference.key.id", SearchCondition.EQUAL,
		             * CommonUtil.getOIDLongValue(ketDqmHeader)); query.appendWhere(sc, new int[] { idxTodoActivity });
		             * query.appendAnd(); sc = new SearchCondition(KETDqmTodoAtivity.class, KETDqmTodoAtivity.TASK_CODE,
		             * SearchCondition.EQUAL, "DQM_ACTION");
		             * 
		             * query.appendWhere(sc, new int[] { idxTodoActivity });
		             * 
		             * qr = PersistenceHelper.manager.find(query); while (qr.hasMoreElements()) { Object[] tempObj = (Object[])
		             * qr.nextElement(); KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) tempObj[0];
		             * WorkflowSearchHelper.manager.taskComplete(ketDqmTodoAtivity); }
		             * 
		             * // todo 생성 KETWfmHelper.service.createWorkItem(ketDqmHeader);
		             */

			    if (mailEnable) {
				// 메일 발송
			    }

			} else if (obj instanceof KETDrawingDistRequest) {
			    KETDrawingDistRequest ketDrawingDistRequest = (KETDrawingDistRequest) obj;

			    // 15일 뒤 다운로드 종료일 계산
			    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			    java.util.Calendar c = java.util.Calendar.getInstance();
			    java.util.Date date = null;

			    try {
				date = fmt.parse(fmt.format(new Date()));
				c.setTime(date);
				c.add(java.util.Calendar.DAY_OF_YEAR, 15);
			    } catch (ParseException e) {
				Kogger.error(getClass(), e);
			    }

			    Timestamp timestamp = new Timestamp(c.getTimeInMillis());

			    ketDrawingDistRequest.setDownloadExpireDate(timestamp);

			    PersistenceServerHelper.update(ketDrawingDistRequest);
			} else if (obj instanceof KETSampleRequest) {
			    KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;

			    ketSampleRequest.setSampleRequestStateCode("REQUEST");
			    ketSampleRequest.setSampleRequestStateName("요청");
			    // todo 생성 부품
			    KETWfmHelper.service.createWorkItem(ketSampleRequest);
			    PersistenceHelper.manager.modify(ketSampleRequest);
			}
		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		}

	    } else if (obj instanceof E3PSProject) {
		// E3PSProject
		// TODO
		Kogger.info(getClass(), "[changeStatePBO] E3PSProject changeStatePBO Action.........");
		State state = ((LifeCycleManaged) obj).getLifeCycleState();
		Kogger.info(getClass(), "[changeStatePBO] E3PSProject getLifeCycleState : " + state);
		if (state.equals(State.toState("APPROVED"))) {
		    Kogger.info(getClass(), "[changeStatePBO] E3PSProject changeStatePBO APPROVED.........");
		    if (obj instanceof ReviewProject) {
			ReviewProject project = (ReviewProject) obj;
			State reassignState = State.toState("PROGRESS");
			LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_REVIEW_PMS_LC",
			        WTContainerHelper.service.getExchangeRef());
			project = (ReviewProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
			Kogger.info(getClass(), "[changeStatePBO] ReviewProject reassign KET_REVIEW_PMS_LC");
			project = (ReviewProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
			Kogger.info(getClass(), "[changeStatePBO] ReviewProject setLifeCycleState PROGRESS");
		    } else if (obj instanceof ProductProject) {
			ProductProject project = (ProductProject) obj;
			State reassignState = State.toState("PROGRESS");

			ProjectChangeStop ps = ProjectHelper.getStopProject(project);
			if (ps != null && "중지검토".equals(ps.getChangeType())) {
			    ps.setChangeType("중지완료");
			    PersistenceHelper.manager.save(ps);
			    reassignState = State.toState("STOPED");
			}

			LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC",
			        WTContainerHelper.service.getExchangeRef());
			project = (ProductProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
			Kogger.info(getClass(), "[changeStatePBO] ProductProject reassign KET_PRODUCT_PMS_LC");
			project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
			Kogger.info(getClass(), "[changeStatePBO] ProductProject setLifeCycleState PROGRESS");

		    } else if (obj instanceof MoldProject) {
			MoldProject project = (MoldProject) obj;
			State reassignState = State.toState("PROGRESS");
			LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_MOLD_PMS_LC",
			        WTContainerHelper.service.getExchangeRef());
			project = (MoldProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
			Kogger.info(getClass(), "[changeStatePBO] MoldProject reassign KET_MOLD_PMS_LC");
			project = (MoldProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
			Kogger.info(getClass(), "[changeStatePBO] MoldProject setLifeCycleState PROGRESS");

		    } else if (obj instanceof WorkProject) {
			WorkProject project = (WorkProject) obj;
			State reassignState = State.toState("PROGRESS");

			LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC",
			        WTContainerHelper.service.getExchangeRef());
			project = (WorkProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
			Kogger.info(getClass(), "[changeStatePBO] WorkProject reassign KET_PRODUCT_PMS_LC");
			project = (WorkProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
			Kogger.info(getClass(), "[changeStatePBO] WorkProject setLifeCycleState PROGRESS");

		    }
		}

	    } else if (obj instanceof KETSalesCSMeetingApproval) {// 영업 프로젝트 cs순서 상태 코드 update
		KETSalesCSMeetingApproval approve = (KETSalesCSMeetingApproval) obj;
		String meetingManageOid = approve.getMeetingManageLinkInfo();
		String targetDept = approve.getDeptSort();
		KETSalesCSMeetingManage manage = (KETSalesCSMeetingManage) CommonUtil.getObject(meetingManageOid);

		KETSalesCSMeetingManageLink manageLink = null;

		QueryResult qr = PersistenceHelper.manager.navigate(manage, "csProject", KETSalesCSMeetingManageLink.class, false);

		while (qr.hasMoreElements()) {// 해당 차수의 해당 부서만 결재상태 업데이트
		    manageLink = (KETSalesCSMeetingManageLink) qr.nextElement();

		    String deptSort = manageLink.getDeptSortNo();

		    if (targetDept.equals(deptSort)) {
			manageLink.setCsState(approve.getLifeCycleState().toString());
			PersistenceHelper.manager.save(manageLink);
		    }
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    @Override
    public String createMultiReq(ArrayList target) throws WTException {
	Transaction trx = null;

	String oid = "";
	String title = target.get(0).toString();
	String desc = target.get(1).toString();
	String[] epmoid = (String[]) target.get(2);
	String taskoid = "";
	if (epmoid[0].indexOf("KETProjectDocument") > -1) {
	    taskoid = (String) target.get(3);
	}

	Kogger.info(getClass(), "  :::::::::::::::: " + epmoid.toString());

	try {
	    trx = new Transaction();
	    trx.start();

	    KETWfmMultiApprovalRequest multiReq = new KETWfmMultiApprovalRequest();
	    multiReq.setReqName(title);
	    multiReq.setDescription(desc);
	    multiReq.setOwner(SessionHelper.manager.getPrincipalReference());
	    multiReq.setCreator(SessionHelper.manager.getPrincipalReference());
	    multiReq.setContainer(WCUtil.getPDMLinkProduct());
	    TeamTemplate tTemplate = TeamHelper.service.getTeamTemplate(WCUtil.getPDMLinkProduct().getContainerReference(), "Default");
	    multiReq.setTeamTemplateId(TeamTemplateReference.newTeamTemplateReference(tTemplate));

	    String reqNumber = "WFM-" + DateUtil.getCurrentDateString("all").substring(2, 4)
		    + DateUtil.getCurrentDateString("all").substring(5, 7) + "-";
	    reqNumber += ManageSequence.getSeqNo(reqNumber, "000", "KETWfmMultiApprovalRequest", "reqNumber");
	    multiReq.setReqNumber(reqNumber);

	    Folder folder = FolderHelper.service.getFolder("/Default", WCUtil.getWTContainerRef());
	    FolderHelper.assignLocation((FolderEntry) multiReq, folder);

	    String templateName = WFMProperties.getInstance().getString("wfm.template.common");
	    LifeCycleTemplate commonTemplate = LifeCycleHelper.service.getLifeCycleTemplate(templateName, WCUtil.getSiteContainer()
		    .getContainerReference());
	    LifeCycleHelper.setLifeCycle((LifeCycleManaged) multiReq, commonTemplate);

	    // ??? ???, ?????
	    if (epmoid[0].indexOf("EPMDocument") > -1) {
		multiReq.setAttribute2("EPM");
	    } else if (epmoid[0].indexOf("KETProjectDocument") > -1) {
		multiReq.setAttribute2("DOC");
	    }

	    multiReq = (KETWfmMultiApprovalRequest) PersistenceHelper.manager.save(multiReq);

	    oid = CommonUtil.getFullOIDString(multiReq);

	    for (int i = 0; i < epmoid.length; i++) {
		if (epmoid[i].indexOf("EPMDocument") > -1) {
		    createEpmLink(epmoid[i], multiReq);
		} else if (epmoid[i].indexOf("KETProjectDocument") > -1) {
		    createDocLink(epmoid[i], multiReq, taskoid);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    oid = "";
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}

	return oid;
    }

    @Override
    public String updateMultiReq(ArrayList target) throws WTException {
	Transaction trx = null;
	String title = target.get(0).toString();
	String desc = target.get(1).toString();
	String[] epmoid = (String[]) target.get(2);
	String oid = target.get(3).toString();
	String taskoid = "";
	if (epmoid[0].indexOf("KETProjectDocument") > -1) {
	    taskoid = target.get(4).toString();
	}

	try {
	    trx = new Transaction();
	    trx.start();

	    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) CommonUtil.getObject(oid);
	    multiReq.setReqName(title);
	    multiReq.setDescription(desc);

	    QueryResult qr = null;
	    if (epmoid[0].indexOf("EPMDocument") > -1) {
		qr = PersistenceHelper.manager.navigate(multiReq, "epmDoc", KETWfmMultiReqEpmLink.class, false);

		while (qr.hasMoreElements()) {
		    KETWfmMultiReqEpmLink link = (KETWfmMultiReqEpmLink) qr.nextElement();
		    PersistenceHelper.manager.delete(link);
		}
	    } else if (epmoid[0].indexOf("KETProjectDocument") > -1) {
		qr = PersistenceHelper.manager.navigate(multiReq, "doc", KETWfmMultiReqDocLink.class, false);

		while (qr.hasMoreElements()) {
		    KETWfmMultiReqDocLink link = (KETWfmMultiReqDocLink) qr.nextElement();
		    PersistenceHelper.manager.delete(link);
		}
	    }

	    // QueryResult qr = PersistenceHelper.manager.navigate(multiReq, "epmDoc", KETWfmMultiReqEpmLink.class,false);

	    // while( qr.hasMoreElements() )
	    // {
	    // KETWfmMultiReqEpmLink link = (KETWfmMultiReqEpmLink)qr.nextElement();
	    // PersistenceHelper.manager.delete(link);
	    // }

	    PersistenceHelper.manager.modify(multiReq);

	    for (int i = 0; i < epmoid.length; i++) {
		if (epmoid[i].indexOf("EPMDocument") > -1) {
		    createEpmLink(epmoid[i], multiReq);
		} else if (epmoid[i].indexOf("KETProjectDocument") > -1) {
		    createDocLink(epmoid[i], multiReq, taskoid);
		}
	    }

	    PersistenceHelper.manager.refresh(multiReq);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}

	return oid;
    }

    @Override
    public void deleteMultiReq(String targetOid) throws WTException {
	Transaction trx = null;

	if (CommonUtil.getObject(targetOid) != null) {
	    trx = new Transaction();
	    trx.start();

	    if (VERBOSE)
		Kogger.info(getClass(), "#############################Delete MultiRequest Start###############################");

	    try {
		KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) CommonUtil.getObject(targetOid);
		QueryResult qr = PersistenceHelper.manager.navigate(multiReq, "epmDoc", KETWfmMultiReqEpmLink.class, false);

		while (qr.hasMoreElements()) {
		    KETWfmMultiReqEpmLink link = (KETWfmMultiReqEpmLink) qr.nextElement();
		    EPMDocument epm = (EPMDocument) ObjectUtil.getLatestVersion(link.getEpmDoc());

		    if (epm.getLifeCycleState().equals(State.toState("REWORK")) && KETObjectUtil.getVersion(epm).equals("0")) {
			LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) link.getEpmDoc(), State.toState("INWORK"), false);
		    }

		    PersistenceHelper.manager.delete(link);
		}

		KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(multiReq);

		if (master != null) {
		    Vector historyVec = WorkflowSearchHelper.manager.getApprovalHistory(master);

		    for (int i = 0; i < historyVec.size(); i++) {
			KETWfmApprovalHistory history = (KETWfmApprovalHistory) historyVec.get(i);
			PersistenceHelper.manager.delete(history);
		    }

		    Vector lineVec = WorkflowSearchHelper.manager.getApprovalLine(master);

		    for (int i = 0; i < lineVec.size(); i++) {
			KETWfmApprovalLine line = (KETWfmApprovalLine) lineVec.get(i);
			PersistenceHelper.manager.delete(line);
		    }
		}

		PersistenceHelper.manager.delete(multiReq);

		trx.commit();
		trx = null;

		if (VERBOSE)
		    Kogger.info(getClass(), "############################Delete MultiRequest Complete##############################");
	    } catch (Exception e) {
		Kogger.error(getClass(), e);

		if (VERBOSE)
		    Kogger.info(getClass(), "##############################Delete MultiRequest Fail################################");
	    } finally {
		if (trx != null) {
		    trx.rollback();
		}
	    }
	}
    }

    @Override
    public PagingQueryResult getEDMQuerySpec(HashMap map) throws WTException {
	PagingQueryResult pagingQuery = null;
	QuerySpec spec = null;

	try {
	    String className = StringUtil.trimToEmpty((String) map.get("className"));
	    String command = StringUtil.trimToEmpty((String) map.get("command"));
	    String number = StringUtil.trimToEmpty((String) map.get("number"));
	    String name = StringUtil.trimToEmpty((String) map.get("name"));
	    String creator = StringUtil.trimToEmpty((String) map.get("creator"));
	    // String modifier = StringUtil.trimToEmpty((String)map.get("modifier"));
	    String createStart = StringUtil.trimToEmpty((String) map.get("create_start"));
	    String createEnd = StringUtil.trimToEmpty((String) map.get("create_end"));
	    String latest = StringUtil.trimToEmpty((String) map.get("latest"));
	    String devType = StringUtil.trimToEmpty((String) map.get("devType"));
	    String sessionid = ParamUtil.getStrParameter((String) map.get("sessionid"), "");

	    int pageNo = ParamUtil.getIntParameter((String) map.get("page"), 1);
	    int perPage = ParamUtil.getIntParameter((String) map.get("perPage"), 100);

	    latest = String.valueOf(1);
	    ReferenceFactory rf = new ReferenceFactory();

	    Class cls_iter = Class.forName(className);
	    Class cls_master = WTIntrospector.getClassInfo(cls_iter).getOtherSideRole("master").getValidClassInfo().getBusinessClass();

	    spec = new QuerySpec();
	    // spec.getFromClause().setAliasPrefix("S");

	    spec.setAdvancedQueryEnabled(true);
	    spec.setDescendantQuery(false);

	    int idx_m = spec.addClassList(cls_master, false);
	    int idx_cls = spec.addClassList(cls_iter, true);

	    SQLFunction upper = null;
	    ColumnExpression ce = null;
	    SearchCondition sc = null;
	    String sCondition = "";

	    if (number.length() > 0) {
		sCondition = SearchCondition.EQUAL;

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		if (number.indexOf("*") > -1) {
		    sCondition = SearchCondition.LIKE;
		    number = EDMQueryHelper.likeCharConvert(number);
		}

		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(cls_master, EPMDocumentMaster.NUMBER));
		spec.appendWhere(new SearchCondition(upper, sCondition, ConstantExpression.newExpression(number.toUpperCase())),
		        new int[] { idx_m });
	    }

	    if (name.length() > 0) {
		sCondition = SearchCondition.EQUAL;

		if (spec.getConditionCount() > 0)
		    spec.appendAnd();

		if (name.indexOf("*") > -1) {
		    sCondition = SearchCondition.LIKE;
		    name = EDMQueryHelper.likeCharConvert(name);
		}

		upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, new ClassAttribute(cls_master, EPMDocumentMaster.NAME));
		spec.appendWhere(new SearchCondition(upper, sCondition, ConstantExpression.newExpression(name.toUpperCase())),
		        new int[] { idx_m });
	    }

	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    spec.appendWhere(new SearchCondition(cls_master, "thePersistInfo.theObjectIdentifier.id", cls_iter, "masterReference.key.id"),
		    new int[] { idx_m, idx_cls });

	    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // if(spec.getConditionCount() > 0) { spec.appendAnd(); }
	    // spec.appendWhere(wt.vc.VersionControlHelper.getSearchCondition(cls_iter, true),new int[]{idx_cls});
	    //
	    // if(Boolean.parseBoolean(latest)) {
	    // if(spec.getConditionCount() > 0) { spec.appendAnd(); }
	    //
	    // Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_iter));
	    // spec.appendWhere(new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_iter,
	    // paramBoolean, spec, idx_cls))), null);
	    // }
	    //
	    // if(spec.getConditionCount() > 0)
	    // spec.appendAnd();
	    // spec.appendWhere( new SearchCondition(cls_iter, "iterationInfo.latest", SearchCondition.IS_TRUE,
	    // Boolean.getBoolean("true")),new int[] { idx_cls } );
	    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // Added by MJOH, 2011-03-19
	    // ?????? ??? ?????? ?? ??? ??????????????????????????QuerySpec ???
	    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }
	    Boolean paramBoolean = new Boolean(ViewManageable.class.isAssignableFrom(cls_iter));
	    spec.appendWhere(
		    new NegatedExpression(new ExistsExpression(VersionHelper.getSuccessorVersionQuerySpec(cls_iter, paramBoolean, spec,
		            idx_cls))), null);
	    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	    if (creator.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		wt.org.WTUser user = (wt.org.WTUser) rf.getReference(creator).getObject();
		spec.appendWhere(new SearchCondition(cls_iter, "iterationInfo.creator.key.id", "=", user.getPersistInfo()
		        .getObjectIdentifier().getId()), new int[] { idx_cls });
	    }

	    if (createStart.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}

		spec.appendWhere(
		        new SearchCondition(cls_iter, "thePersistInfo.createStamp", ">=", DateUtil.convertStartDate2(createStart.trim())),
		        new int[] { idx_cls });
	    }

	    if (createEnd.length() > 0) {
		if (spec.getConditionCount() > 0) {
		    spec.appendAnd();
		}
		spec.appendWhere(
		        new SearchCondition(cls_iter, "thePersistInfo.createStamp", "<", DateUtil.convertEndDate2(createEnd.trim())),
		        new int[] { idx_cls });
	    }

	    // VersionHelper.appendFilterTerminalsAndWorkingCopies(cls_iter, spec, new int[]{idx_cls});

	    // ??? ???.
	    if (LifeCycleManaged.class.isAssignableFrom(cls_iter)) {
		String state = StringUtil.trimToEmpty((String) map.get("state"));

		if (state.length() > 0) {
		    if (spec.getConditionCount() > 0) {
			spec.appendAnd();
		    }

		    sc = new SearchCondition(cls_iter, "state.state", SearchCondition.EQUAL, State.toState(state));
		    spec.appendWhere(sc, new int[] { idx_cls });
		}
	    }

	    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    // Commented by MJOH, 2011-03-19
	    // ??? 0??? ??????????? ??? ?????????????
	    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    if (spec.getConditionCount() > 0) {
		spec.appendAnd();
	    }

	    spec.appendOpenParen();
	    spec.appendWhere(new SearchCondition(cls_iter, "versionInfo.identifier.versionId", SearchCondition.EQUAL, "0"),
		    new int[] { idx_cls });
	    spec.appendOr();
	    spec.appendOpenParen();

	    QuerySpec subQuery = new QuerySpec();
	    Class subclass = StringValue.class;
	    int subIdx = subQuery.appendClassList(subclass, false);

	    subQuery.appendSelect(new ClassAttribute(subclass, "theIBAHolderReference.key.id"), new int[] { subIdx }, false);

	    if (subQuery.getConditionCount() > 0)
		subQuery.appendAnd();
	    subQuery.appendWhere(new SearchCondition(subclass, "value", SearchCondition.EQUAL, devType), new int[] { subIdx });

	    if (subQuery.getConditionCount() > 0)
		subQuery.appendAnd();
	    subQuery.appendWhere(new SearchCondition(subclass, "theIBAHolderReference.key.classname", SearchCondition.EQUAL, className),
		    new int[] { subIdx });

	    ClassAttribute targetExpression = new ClassAttribute(cls_iter, "thePersistInfo.theObjectIdentifier.id");
	    spec.appendWhere(new SearchCondition(targetExpression, SearchCondition.IN, new SubSelectExpression(subQuery)),
		    new int[] { idx_cls });

	    spec.appendCloseParen();
	    spec.appendCloseParen();
	    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	    // spec.setAdvancedQueryEnabled(true);
	    spec.appendOrderBy(new OrderBy(new ClassAttribute(cls_iter, "thePersistInfo.modifyStamp"), true), new int[] { idx_cls });

	    if (!sessionid.equals("")) {
		pagingQuery = PagingSessionHelper.fetchPagingSession(perPage * (pageNo - 1), perPage, Long.parseLong(sessionid));
	    } else {
		pagingQuery = PagingSessionHelper.openPagingSession(0, perPage, spec);

		if (VERBOSE)
		    Kogger.info(getClass(), spec.toString());
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	}

	return pagingQuery;
    }

    @Override
    public QueryResult getRefWorklistQuery(String predate, String postdate) throws WTException {
	QueryResult result = new QueryResult();
	WTPrincipalReference prinRef = SessionHelper.manager.getPrincipalReference();

	QuerySpec subQuery = new QuerySpec();
	Class subClass = KETWfmApprovalHistory.class;
	String actType = WFMProperties.getInstance().getString("wfm.reference");
	int historyIdx = subQuery.appendClassList(subClass, false);

	subQuery.appendSelect(new ClassAttribute(subClass, "appMasterReference.key.id"), new int[] { historyIdx }, false);
	subQuery.appendWhere(
	        new SearchCondition(subClass, "owner.key.id", SearchCondition.EQUAL, Long.parseLong(KETObjectUtil.getIda2a2(prinRef
	                .getObject()))), new int[] { historyIdx });

	if (subQuery.getConditionCount() > 0)
	    subQuery.appendAnd();
	subQuery.appendWhere(new SearchCondition(subClass, "activityName", SearchCondition.EQUAL, actType), new int[] { historyIdx });

	QuerySpec spec = new QuerySpec();
	Class targetClass = KETWfmApprovalMaster.class;
	int masterIdx = spec.appendClassList(targetClass, true);
	ClassAttribute targetExpression = new ClassAttribute(targetClass, "thePersistInfo.theObjectIdentifier.id");

	spec.appendWhere(new SearchCondition(targetClass, "completedDate", SearchCondition.NOT_NULL, true), new int[] { masterIdx });

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(targetClass, "pboReference.key.classname", SearchCondition.NOT_NULL, true),
	        new int[] { masterIdx });

	if (!predate.equals("")) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(targetClass, "completedDate", SearchCondition.GREATER_THAN_OR_EQUAL, DateUtil
		            .convertStartDate2(predate)), new int[] { masterIdx });
	}

	if (!postdate.equals("")) {
	    if (spec.getConditionCount() > 0)
		spec.appendAnd();
	    spec.appendWhere(
		    new SearchCondition(targetClass, "completedDate", SearchCondition.LESS_THAN_OR_EQUAL, DateUtil
		            .convertEndDate2(postdate)), new int[] { masterIdx });
	}

	if (spec.getConditionCount() > 0)
	    spec.appendAnd();
	spec.appendWhere(new SearchCondition(targetExpression, SearchCondition.IN, new SubSelectExpression(subQuery)),
	        new int[] { masterIdx });

	spec.setAdvancedQueryEnabled(true);

	if (VERBOSE)
	    Kogger.info(getClass(), spec.toString());

	result = PersistenceServerHelper.manager.query(spec);

	return result;
    }

    @Override
    public void deleteHistory(Persistable pbo) throws WTException {
	Transaction trx = null;

	try {
	    trx = new Transaction();
	    trx.start();

	    KETWfmApprovalMaster master = WorkflowSearchHelper.manager.getMaster(pbo);

	    if (master != null) {
		Vector historyVec = WorkflowSearchHelper.manager.getApprovalHistory(master);

		for (int i = 0; i < historyVec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) historyVec.get(i);
		    PersistenceHelper.manager.delete(history);
		}
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    /**
     * 결재히스토리 작성
     * 
     * @param history
     **/
    private void createHistory(KETWfmApprovalHistory history) {
	Transaction trx = null;

	try {
	    trx = new Transaction();
	    trx.start();

	    PersistenceHelper.manager.save(history);

	    trx.commit();
	    trx = null;
	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null) {
		trx.rollback();
	    }
	}
    }

    /**
     * @param epmoid
     * @param multiReq
     **/
    private void createEpmLink(String epmoid, KETWfmMultiApprovalRequest multiReq) {
	Transaction trx = null;

	KETWfmMultiReqEpmLink multiEpm = new KETWfmMultiReqEpmLink();

	try {
	    trx = new Transaction();
	    trx.start();

	    EPMDocument epmDoc = (EPMDocument) CommonUtil.getObject(epmoid);

	    if (!epmDoc.getLifeCycleTemplate().getName().equals("KET_EPM_LC")) {
		LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_EPM_LC", WCUtil.getPDMLinkProduct()
		        .getContainerReference());

		if (LCtemplate != null) {
		    epmDoc = (EPMDocument) LifeCycleHelper.service.reassign((LifeCycleManaged) epmDoc,
			    LCtemplate.getLifeCycleTemplateReference());
		}
	    }

	    multiEpm.setEpmDoc(epmDoc);
	    multiEpm.setRequest(multiReq);
	    PersistenceHelper.manager.save(multiEpm);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null)
		trx.rollback();
	}
    }

    /**
     * @param docoid
     * @param multiReq
     * @param taskoid
     **/
    private void createDocLink(String docoid, KETWfmMultiApprovalRequest multiReq, String taskoid) {
	Transaction trx = null;

	KETWfmMultiReqDocLink multiDoc = new KETWfmMultiReqDocLink();

	try {
	    trx = new Transaction();
	    trx.start();

	    WTDocument wtDoc = (WTDocument) CommonUtil.getObject(docoid);

	    if (!wtDoc.getLifeCycleTemplate().getName().equals("KET_EPM_LC")) {
		LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_EPM_LC", WCUtil.getPDMLinkProduct()
		        .getContainerReference());

		if (LCtemplate != null) {
		    wtDoc = (WTDocument) LifeCycleHelper.service.reassign((LifeCycleManaged) wtDoc,
			    LCtemplate.getLifeCycleTemplateReference());
		}
	    }

	    multiDoc.setTaskoid(taskoid);
	    multiDoc.setDoc(wtDoc);
	    multiDoc.setRequest(multiReq);
	    PersistenceHelper.manager.save(multiDoc);

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (trx != null)
		trx.rollback();
	}
    }

    class WorkflowEventListener extends ServiceEventListenerAdapter {
	public WorkflowEventListener(String name) {
	    super(name);
	}

	public void notifyVetoableEvent(Object obj) throws WTException {
	    if (!(obj instanceof KeyedEvent)) {
		return;
	    }

	    KeyedEvent keyEvent = (KeyedEvent) obj;
	    Object eventObj = keyEvent.getEventTarget();
	    String eventTypeStr = keyEvent.getEventType();
	    // Kogger.info(getClass(), "WorkflowEventListener eventObj : " + eventObj.getClass().getName());

	    if (!(eventObj instanceof KETMoldChangeOrder) && !(eventObj instanceof KETProdChangeOrder)
		    && !(eventObj instanceof KETWfmMultiApprovalRequest) && !(eventObj instanceof KETEarlyWarning)
		    && !(eventObj instanceof KETEarlyWarningPlan)
		    && !(eventObj instanceof KETEarlyWarningResult)
		    && !(eventObj instanceof KETEarlyWarningEnd)
		    && !(eventObj instanceof KETEarlyWarningEndReq)
		    && !(eventObj instanceof KETProdChangeRequest)
		    && !(eventObj instanceof KETMoldChangeRequest)
		    && !(eventObj instanceof KETCompetentDepartment)
		    && !(eventObj instanceof KETMeetingMinutes)
		    // ECA
		    && !(eventObj instanceof KETProdChangeActivity)
		    && !(eventObj instanceof KETMoldChangeActivity)
		    // DQM
		    && !(eventObj instanceof KETDqmRaise)
		    && !(eventObj instanceof KETDqmAction)
		    // DDR
		    && !(eventObj instanceof KETSampleRequest) && !(eventObj instanceof KETDrawingDistRequest)
		    && !(eventObj instanceof KETTryMold) && !(eventObj instanceof KETTryPress) && !(eventObj instanceof GateAssessResult)
		    && !(eventObj instanceof WorkItem)
		    // PMS
		    && !(eventObj instanceof ReviewProject) && !(eventObj instanceof ProductProject) && !(eventObj instanceof MoldProject)
		    && !(eventObj instanceof WorkProject) && !(eventObj instanceof KETSalesCSMeetingApproval)) {
		return;
	    }

	    State UNDERREVIEW = State.toState("UNDERREVIEW");
	    State APPROVED = State.toState("APPROVED");
	    State REJECTED = State.toState("REJECTED");
	    State REWORK = State.toState("REWORK");
	    State PLANNING = State.toState("PLANNING");
	    State EWRCOMPLETED = State.toState("EWRCOMPLETED");
	    State EWRCANCELED = State.toState("EWRCANCELED");
	    State EWRREQUEST = State.toState("EWRREQUEST");
	    State EXCUTEACTIVITY = State.toState("EXCUTEACTIVITY");
	    State ACTIVITYCOMPLETED = State.toState("ACTIVITYCOMPLETED");
	    State WORKCOMPLETED = State.toState("WORKCOMPLETED");
	    State PROGRESS = State.toState("PROGRESS");

	    if (eventTypeStr.equals("STATE_CHANGE")) {
		changeStatePBO(eventObj);

		State tState = ((LifeCycleManaged) eventObj).getLifeCycleState();

		if (tState.equals(APPROVED)) {
		    if ((eventObj instanceof KETEarlyWarning) || (eventObj instanceof KETEarlyWarningPlan)
			    || (eventObj instanceof KETEarlyWarningEndReq) || (eventObj instanceof KETEarlyWarningResult)) {
			KETWfmHelper.service.createWorkItem(eventObj);
		    } else if (eventObj instanceof KETEarlyWarningEnd) {
			KETEarlyWarningEnd warningEnd = (KETEarlyWarningEnd) eventObj;

			if (warningEnd.getEndresult().equals("N")) // ???
			{
			    KETEarlyWarningStep step = WorkflowSearchHelper.manager.getEWStep(eventObj);
			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) step, PLANNING);
			    KETWfmHelper.service.createWorkItem(eventObj);
			} else // ???
			{
			    KETEarlyWarningStep step = WorkflowSearchHelper.manager.getEWStep(eventObj);
			    LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged) step, EWRCOMPLETED);
			}
		    } else if ((eventObj instanceof KETProdChangeOrder) || (eventObj instanceof KETMoldChangeOrder)) {
			if (eventObj instanceof KETProdChangeOrder) {
			    KETProdChangeOrder prodEco = (KETProdChangeOrder) eventObj;
			    QueryResult qr = PersistenceHelper.manager.navigate(prodEco, "moldPlan", KETProdECOPlanLink.class);
			    KETMoldChangePlan plan = null;

			    while (qr.hasMoreElements()) {
				plan = (KETMoldChangePlan) qr.nextElement();
				try {
				    plan.setProdDwgActualDate(DateUtil.getCurrentTimestamp());
				    plan.setProdEcoOwner((WTUser) prodEco.getCreator().getPrincipal());
				    plan.setProdDeptName(prodEco.getDeptName());

				    KETEcmHelper.service.updateDailyMoldPlanStatus(plan);
				} catch (WTPropertyVetoException e) {
				    Kogger.error(getClass(), e);
				}

			    }

			    KETWfmHelper.service.createWorkItem(eventObj);

			    // 승인완료시 제품ECO의 경우 산출물 100% 처리
			    qr = PersistenceHelper.manager.navigate(prodEco, "output", KETProdChangeOrderOutputLink.class);
			    ProjectOutput pOutput = null;

			    if (qr != null) {

				while (qr.hasMoreElements()) {
				    try {
					pOutput = (ProjectOutput) qr.nextElement();
					pOutput.setCompletion(100);

					pOutput = (ProjectOutput) PersistenceHelper.manager.save(pOutput);
					E3PSTask task = (E3PSTask) pOutput.getTask();

					ProjectTaskHelper.manager.updateCompletionFromOutput(task);
				    } catch (Exception e) {
					Kogger.error(getClass(), e);
				    }
				}
			    }

			} else if (eventObj instanceof KETMoldChangeOrder) {
			    KETMoldChangeOrder moldEco = (KETMoldChangeOrder) eventObj;
			    QueryResult qr = PersistenceHelper.manager.navigate(moldEco, "moldPlan", KETMoldECOPlanLink.class);
			    KETMoldChangePlan plan = null;
			    while (qr.hasMoreElements()) {
				plan = (KETMoldChangePlan) qr.nextElement();
				try {
				    plan.setMoldChangeActualDate(DateUtil.getCurrentTimestamp());
				    plan.setMoldEcoOwner((WTUser) moldEco.getCreator().getPrincipal());
				    KETEcmHelper.service.updateDailyMoldPlanStatus(plan);
				} catch (WTPropertyVetoException e) {
				    Kogger.error(getClass(), e);
				}

			    }

			    KETWfmHelper.service.createWorkItem(eventObj);

			    // 승인완료시 금형ECO의 경우 산출물 100% 처리
			    qr = PersistenceHelper.manager.navigate(moldEco, "output", KETMoldChangeOrderOutputLink.class);
			    ProjectOutput pOutput = null;

			    if (qr != null) {
				while (qr.hasMoreElements()) {
				    try {
					pOutput = (ProjectOutput) qr.nextElement();
					pOutput.setCompletion(100);

					pOutput = (ProjectOutput) PersistenceHelper.manager.save(pOutput);
					E3PSTask task = (E3PSTask) pOutput.getTask();

					ProjectTaskHelper.manager.updateCompletionFromOutput(task);
				    } catch (Exception e) {
					Kogger.error(getClass(), e);
				    }
				}
			    }
			}
		    } else if ((eventObj instanceof KETTryMold) || (eventObj instanceof KETTryPress)) {

			KETTryMold moldTryConditionObj = null;
			KETTryPress pressTryConditionObj = null;

			if (eventObj instanceof KETTryMold) {
			    KETTryMold tryMold = (KETTryMold) eventObj;
			    QueryResult qr = PersistenceHelper.manager.navigate(tryMold, "output", KETTryMoldOutputLink.class);
			    ProjectOutput pOutput = null;

			    if (qr != null) {

				while (qr.hasMoreElements()) {
				    try {
					pOutput = (ProjectOutput) qr.nextElement();
					pOutput.setCompletion(100);

					pOutput = (ProjectOutput) PersistenceHelper.manager.save(pOutput);
					E3PSTask task = (E3PSTask) pOutput.getTask();

					ProjectTaskHelper.manager.updateCompletionFromOutput(task);
				    } catch (Exception e) {
					Kogger.error(getClass(), e);
				    }
				}
			    }

			} else if (eventObj instanceof KETTryPress) {
			    KETTryPress pressMold = (KETTryPress) eventObj;
			    QueryResult qr = PersistenceHelper.manager.navigate(pressMold, "output", KETTryPressOutputLink.class);
			    ProjectOutput pOutput = null;

			    if (qr != null) {

				while (qr.hasMoreElements()) {
				    try {
					pOutput = (ProjectOutput) qr.nextElement();
					pOutput.setCompletion(100);

					pOutput = (ProjectOutput) PersistenceHelper.manager.save(pOutput);
					E3PSTask task = (E3PSTask) pOutput.getTask();

					ProjectTaskHelper.manager.updateCompletionFromOutput(task);
				    } catch (Exception e) {
					Kogger.error(getClass(), e);
				    }
				}
			    }
			}
		    } else if (eventObj instanceof GateAssessResult) {
			// GateAssessResult 게이트 결과 승인시 Task완료처리한다
			GateAssessResult gateAssRslt = (GateAssessResult) eventObj;

			try {
			    E3PSTask gateTask = ProjectTaskCompHelper.service.getTask(gateAssRslt);
			    if (gateTask != null) {
				// 실제작업시간 업데이트
				// gateTask.setExecWorkTime(0);

				// 완료100%설정
				gateTask.setTaskCompletion(100);

				// 오늘날짜를 실제종료일에 저장
				// ExtendScheduleData schedule = (ExtendScheduleData) gateTask.getTaskSchedule().getObject();
				// Timestamp ts = DateUtil.getCurrentTimestamp();
				// schedule.setExecEndDate(ts);
				// schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

				// 프로젝트 저장 등 완료처리
				// gateTask = (E3PSTask) ProjectTaskHelper.updateCompletion(gateTask);

				// task 저장
				gateTask = (E3PSTask) TaskHelper.manager.modifyTask(gateTask);
				// TODO 이전 이력 정보를 삭제 한다.
				QueryResult rs = PersistenceHelper.manager.navigate(gateTask, GateAssRsltTaskLink.ASSESS_ROLE,
				        GateAssRsltTaskLink.class, false);
				while (rs.hasMoreElements()) {
				    GateAssRsltTaskLink assRsltTaskLink = (GateAssRsltTaskLink) rs.nextElement();
				    if (gateTask != assRsltTaskLink.getTask()) {
					PersistenceHelper.manager.delete(assRsltTaskLink);
				    }
				}
			    }
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
		    }
		    // else if (obj instanceof ReviewProject) {
		    // ReviewProject project = (ReviewProject) obj;
		    // State reassignState = State.toState("PROGRESS");
		    // LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_REVIEW_PMS_LC",
		    // WTContainerHelper.service.getExchangeRef());
		    // project = (ReviewProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    // Kogger.info(getClass(), "[WorkflowEventListener] ReviewProject reassign KET_REVIEW_PMS_LC");
		    // project = (ReviewProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    // Kogger.info(getClass(), "[WorkflowEventListener] ReviewProject setLifeCycleState PROGRESS");
		    // }
		    // else if (obj instanceof ProductProject) {
		    // ProductProject project = (ProductProject) obj;
		    // State reassignState = State.toState("PROGRESS");
		    // LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_PRODUCT_PMS_LC",
		    // WTContainerHelper.service.getExchangeRef());
		    // project = (ProductProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    // Kogger.info(getClass(), "[WorkflowEventListener] ProductProject reassign KET_PRODUCT_PMS_LC");
		    // project = (ProductProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    // Kogger.info(getClass(), "[WorkflowEventListener] ProductProject setLifeCycleState PROGRESS");
		    // }
		    // else if (obj instanceof MoldProject) {
		    // MoldProject project = (MoldProject) obj;
		    // State reassignState = State.toState("PROGRESS");
		    // LifeCycleTemplateReference lctRef = LifeCycleHelper.service.getLifeCycleTemplateReference("KET_MOLD_PMS_LC",
		    // WTContainerHelper.service.getExchangeRef());
		    // project = (MoldProject) LifeCycleHelper.service.reassign(project, lctRef, project.getContainerReference());
		    // Kogger.info(getClass(), "[WorkflowEventListener] MoldProject reassign KET_MOLD_PMS_LC");
		    // project = (MoldProject) LifeCycleHelper.service.setLifeCycleState(project, reassignState, true);
		    // Kogger.info(getClass(), "[WorkflowEventListener] MoldProject setLifeCycleState PROGRESS");
		    // }
		} else if ((tState.equals(EXCUTEACTIVITY)) || (tState.equals(ACTIVITYCOMPLETED))) // ??? ?????ECA Workitem ???
		{
		    if ((eventObj instanceof KETMoldChangeOrder) || (eventObj instanceof KETProdChangeOrder)) {
			KETWfmHelper.service.createWorkItem(eventObj);
		    }
		}
		// Modified by MJOH, 2011-03-29
		// ?????? ??????????.. ??????????? ?????? ??? ??????????WorkItem???????
		// ???????????????????????? To-Do??ECO ????????????? ?????
		// else if( tState.equals(REWORK) )
		// {
		// if( eventObj instanceof KETMoldChangeOrder )
		// {
		// KETMoldChangeOrder moldCO = (KETMoldChangeOrder)eventObj;
		// QueryResult qr = WorkflowSearchHelper.manager.getCompletedTodoList(moldCO);
		//
		// while( qr.hasMoreElements() )
		// {
		// Object[] itemObj = (Object[])qr.nextElement();
		// WorkItem item = (WorkItem)itemObj[0];
		// WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
		// activity.deleteActivity();
		// }
		//
		// Object[] eca = EcmUtil.getChangeActivities(moldCO);
		//
		// if( eca != null )
		// {
		// for( int cnt = 0; cnt < eca.length; cnt++ )
		// {
		// KETMoldChangeActivity moldCA = (KETMoldChangeActivity)eca[cnt];
		// qr = WorkflowSearchHelper.manager.getCompletedTodoList(moldCA);
		//
		// while( qr.hasMoreElements() )
		// {
		// Object[] itemObj = (Object[])qr.nextElement();
		// WorkItem item = (WorkItem)itemObj[0];
		// WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
		// activity.deleteActivity();
		// }
		// }
		// }
		// }
		// else if( eventObj instanceof KETProdChangeOrder )
		// {
		// KETProdChangeOrder prodCO = (KETProdChangeOrder)eventObj;
		// QueryResult qr = WorkflowSearchHelper.manager.getCompletedTodoList(prodCO);
		//
		// while( qr.hasMoreElements() )
		// {
		// Object[] itemObj = (Object[])qr.nextElement();
		// WorkItem item = (WorkItem)itemObj[0];
		// WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
		// activity.deleteActivity();
		// }
		//
		// Object[] eca = EcmUtil.getChangeActivities(prodCO);
		//
		// if( eca != null )
		// {
		// for( int cnt = 0; cnt < eca.length; cnt++ )
		// {
		// KETProdChangeActivity prodCA = (KETProdChangeActivity)eca[cnt];
		// qr = WorkflowSearchHelper.manager.getCompletedTodoList(prodCA);
		//
		// while( qr.hasMoreElements() )
		// {
		// Object[] itemObj = (Object[])qr.nextElement();
		// WorkItem item = (WorkItem)itemObj[0];
		// WfAssignedActivity activity = (WfAssignedActivity)item.getSource().getObject();
		// activity.deleteActivity();
		// }
		// }
		// }
		// }
		// }
		else if (tState.equals(REJECTED)) {
		    /*
	             * 2010/12/27 ews??? ??? ??? ??? ??? if((eventObj instanceof KETEarlyWarningPlan)||(eventObj instanceof
	             * KETEarlyWarningResult) ||(eventObj instanceof KETEarlyWarningEndReq)) { KETEarlyWarningStep step =
	             * WorkflowSearchHelper.manager.getEWStep(eventObj); LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)step,
	             * EWRCANCELED); }
	             */

		    if (eventObj instanceof KETMoldChangeOrder) {
			KETMoldChangeOrder moldCO = (KETMoldChangeOrder) eventObj;
			QueryResult qr = WorkflowSearchHelper.manager.getCompletedTodoList(moldCO);

			while (qr.hasMoreElements()) {
			    Object[] itemObj = (Object[]) qr.nextElement();
			    WorkItem item = (WorkItem) itemObj[0];
			    WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
			    activity.deleteActivity();
			}

			Object[] eca = EcmUtil.getChangeActivities(moldCO);

			if (eca != null) {
			    for (int cnt = 0; cnt < eca.length; cnt++) {
				KETMoldChangeActivity moldCA = (KETMoldChangeActivity) eca[cnt];
				qr = WorkflowSearchHelper.manager.getCompletedTodoList(moldCA);

				while (qr.hasMoreElements()) {
				    Object[] itemObj = (Object[]) qr.nextElement();
				    WorkItem item = (WorkItem) itemObj[0];
				    WfAssignedActivity activity = (WfAssignedActivity) item.getSource().getObject();
				    activity.deleteActivity();
				}

				// 반려후 재상신 프로세스를 탈 경우 ECN이 생성되지 않는 문제로 반려시 모든 ECA의 WorkItem을 삭제처리 한다.
				if (WorkflowSearchHelper.manager.IsRuninningTodo(moldCA)) {
				    WorkflowSearchHelper.manager.deleteWorkItem(moldCA);
				}

			    }
			}
		    } else if (eventObj instanceof KETProdChangeOrder) {
			KETProdChangeOrder prodCO = (KETProdChangeOrder) eventObj;
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

			    }
			}
		    } else if (eventObj instanceof KETEarlyWarningPlan) {
			KETEarlyWarningPlan ewPlan = (KETEarlyWarningPlan) eventObj;
			KETEarlyWarning ew = WorkflowSearchHelper.manager.getEW(ewPlan);
			KETWfmHelper.service.createWorkItem(ew);
		    } else if (eventObj instanceof KETEarlyWarningResult) {
			KETEarlyWarningResult ewResult = (KETEarlyWarningResult) eventObj;
			KETEarlyWarning ew = WorkflowSearchHelper.manager.getEW(ewResult);
			QueryResult qrLink = PersistenceHelper.manager.navigate(ew.getMaster(), "plan", KETEarlyWarningPlanLink.class,
			        false);
			WTDocumentMaster master = null;

			while (qrLink.hasMoreElements()) {
			    KETEarlyWarningPlanLink planLink = (KETEarlyWarningPlanLink) qrLink.nextElement();
			    master = planLink.getPlan();
			}

			if (master != null) {
			    KETEarlyWarningPlan ewPlan = (KETEarlyWarningPlan) ObjectUtil.getLatestObject(master);
			    KETWfmHelper.service.createWorkItem(ewPlan);
			}
		    } else if (eventObj instanceof KETEarlyWarningEndReq) {
			KETEarlyWarningEndReq ewEndReq = (KETEarlyWarningEndReq) eventObj;
			KETEarlyWarning ew = WorkflowSearchHelper.manager.getEW(ewEndReq);
			QueryResult qrLink = PersistenceHelper.manager.navigate(ew.getMaster(), "result", KETEarlyWarningResultLink.class,
			        false);

			WTDocumentMaster master = null;

			while (qrLink.hasMoreElements()) {
			    KETEarlyWarningResultLink resultLink = (KETEarlyWarningResultLink) qrLink.nextElement();
			    master = resultLink.getResult();
			}

			if (master != null) {
			    KETEarlyWarningResult ewResult = (KETEarlyWarningResult) ObjectUtil.getLatestObject(master);
			    KETWfmHelper.service.createWorkItem(ewResult);
			}
		    }
		} else if (tState.equals(UNDERREVIEW)) {
		    if (eventObj instanceof KETWfmMultiApprovalRequest) {
			KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) eventObj;

			try {
			    multiReq.setRequestDate(DateUtil.getCurrentTimestamp());
			    PersistenceHelper.manager.modify(multiReq);
			    PersistenceHelper.manager.refresh(multiReq);
			} catch (WTPropertyVetoException e) {
			    Kogger.error(getClass(), e);
			}
		    }
		} else if (tState.equals(PROGRESS)) {
		    // PMS 일정변경 승인 시 체크 인
		    if (eventObj instanceof E3PSProject) {
			E3PSProject project = (E3PSProject) eventObj;
			if (project.isCheckOut()) {
			    try {
				HistoryHelper.checkIn(project);
			    } catch (Exception e) {
				Kogger.error(getClass(), e);
			    }
			}
			try {
			    // 이전 히스토리가 있으면 일정변경 승인에 의한 진행 상태이므로
			    if (HistoryHelper.getHistory(project).size() > 1) {
				Kogger.info(getClass(), "일정변경 메일 발송");
				ProjectHelper.manager.sendMailPlanChange(project);
			    } else {
				Kogger.info(getClass(), "프로젝트 최초 등록 메일 발송");
				ProjectHelper.manager.sendMailStartProject(project);
			    }
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
		    }

		}
	    } else if (eventTypeStr.equals("UPDATE"))// ???? ?? ?? ?
	    {

		Kogger.info(getClass(), "====================UPDATE=============================");
		// int i = 0;
		if ((eventObj instanceof WorkItem) && mailEnable) {
		    WorkItem item = (WorkItem) eventObj;
		    String fromUserID = "";
		    String toUserID = "";
		    String delegatedUserID = "";
		    String contents = "";
		    WTPrincipalReference pRef = null;

		    // if( (item.getStatus().equals(WfAssignmentState.POTENTIAL)) && (item.getPersistInfo().getUpdateCount() == 2) )
		    if (item.getStatus().equals(WfAssignmentState.COMPLETED)) {
			Kogger.info(getClass(), "### WorkItem status is COMPLETED!!");
			Object pbo = item.getPrimaryBusinessObject().getObject();
			if (pbo instanceof KETWfmMultiApprovalRequest) {
			    KETWfmMultiApprovalRequest multi = (KETWfmMultiApprovalRequest) pbo;
			    QueryResult qr = PersistenceHelper.manager.navigate(multi, "doc", KETWfmMultiReqDocLink.class, false);
			    if (qr.hasMoreElements()) {
				KETWfmMultiReqDocLink ll = (KETWfmMultiReqDocLink) qr.nextElement();
				String taskOid = ll.getTaskoid();
				Kogger.info(getClass(), "### taskOId = " + taskOid);

				E3PSTask task = (E3PSTask) CommonUtil.getObject(taskOid);
				Kogger.info(getClass(), "### task = " + task);
				QueryResult qq = null;
				if (task != null) {
				    qq = TaskDependencyHelper.manager.getDependTaskList1(task);
				}

				WTUser masterSource = null;
				WTUser masterTarget = null;
				if (qq != null) {
				    if (qq.hasMoreElements()) {
					TaskDependencyLink tdl = (TaskDependencyLink) qq.nextElement();
					E3PSTaskData ttdSource = null;
					E3PSTaskData ttdTarget = null;
					try {
					    ttdSource = new E3PSTaskData((E3PSTask) tdl.getDependSource());
					    ttdTarget = new E3PSTaskData((E3PSTask) tdl.getDependTarget());
					} catch (Exception e) {
					    Kogger.error(getClass(), e);
					}
					masterSource = ttdSource.getTaskWTUser();
					masterTarget = ttdTarget.getTaskWTUser();
					fromUserID = masterTarget.getFullName();

					Ownership ownership = Ownership.newOwnership(masterSource);

					pRef = ownership.getOwner();

					try {

					    Hashtable contentHash = MailUtil.getMailContent("approval", item, delegatedUserID, task);

					    /*
			                     * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
			                     */
					    String templateName = CommonUtil.getMailTemplateName(delegatedUserID, "TaskNoticeMail");

					    // contents =
					    // MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"TaskNoticeMail.html");
					    contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
					    // ////////////////////////////////////////////////////////////////////////////////////////////////

					    Hashtable hash = MailUtil.prepareMailInfoHash(pRef, contents, fromUserID);

					    if (hash != null) {
						MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
					    }
					} catch (Exception e) {
					    Kogger.error(getClass(), e);
					}
				    }
				}
			    }
			}
		    }

		    if ((item.getStatus().equals(WfAssignmentState.POTENTIAL))) {

			Kogger.info(getClass(), "### WorkItem state is POTENTIAL!!");
			try {
			    fromUserID = (String) ClassifyPBOUtil.extractPBOInfo(item.getPrimaryBusinessObject().getObject()).get(
				    "creatorID");
			    Kogger.info(getClass(), "### fromUserID = " + fromUserID);
			    Object pbo = item.getPrimaryBusinessObject().getObject();
			    Kogger.info(getClass(), "### pbo = " + pbo);

			    if (pbo instanceof E3PSProject) {
				People pp = new People();
				String appUser = null;
				KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
				Kogger.info(getClass(), "### appMaster = " + appMaster);
				if (appMaster != null) {
				    pp = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
				}

				if (pp != null) {
				    appUser = pp.getId();
				}

				fromUserID = appUser;
				Kogger.info(getClass(), "### fromUserID = " + fromUserID);
			    }

			    WTPrincipalReference rejUser = WorkflowSearchHelper.manager.getApprovalRejectUser((Persistable) pbo);
			    Kogger.info(getClass(), "### rejUser = " + rejUser);

			    if (rejUser != null) {
				String appUser = "";
				People pp = new People();
				KETWfmApprovalMaster appMaster = null;
				appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
				if (appMaster != null) {
				    pp = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
				}
				// }
				fromUserID = rejUser.getName();
				toUserID = pp.getId();
				pRef = appMaster.getOwner();
			    } else if (rejUser == null) {
				pRef = item.getOwnership().getOwner();
				toUserID = pRef.getName();
			    }

			    if (fromUserID == null) {
				fromUserID = (String) ClassifyPBOUtil.extractPBOInfo(item.getPrimaryBusinessObject().getObject()).get(
				        "creatorID");
			    }

			    WFMSearchUtilDao searchDao = new WFMSearchUtilDao();
			    delegatedUserID = StringUtil.trim(searchDao.getDelegator(toUserID));
			    Kogger.info(getClass(), "##### ??? #####");

			    /*
		             * ????????? WorkItem????? Update ??? ??????????? ???????? ??? ??? ?? ????? ?????WorkItem
		             * (keyEvent.getEventTarget() )?????????????????toUserID)??? ????????(Calendar.MINUTE)????? ???????? Update
		             * ?????? ???????? ??????????? ??????? check_vec : static ??? ?????????????? ?????????? check_sameMail : ???
		             * ?????? ??? mail_send_history : ?????? ?????check_vec???????? ??? HashMap??? Data Logic Start Edited By
		             * ?????2012.04.04
		             */
			    Calendar cal = Calendar.getInstance();
			    String check_sameMail = "N";

			    Kogger.info(getClass(), "@@@@@@@@@@@@ eventTarget @@@@@@@" + keyEvent.getEventTarget());
			    Kogger.info(getClass(), "@@@@@@@@@@@@ toUser @@@@@@@" + toUserID);
			    Kogger.info(getClass(), "@@@@@@@@@@@@ time @@@@@@@" + cal.get(Calendar.MINUTE));

			    for (int i = 0; i < check_vec.size(); i++) {
				HashMap mail_sent_history = (HashMap) check_vec.get(i);

				if (mail_sent_history.get("eventTarget") == keyEvent.getEventTarget()
				        && mail_sent_history.get("toUser").equals(toUserID)
				        && mail_sent_history.get("time").equals(cal.get(Calendar.MINUTE))) {
				    Kogger.info(getClass(),
					    "@@@@@@@@@@@@ eventTarget in hasmap @@@@@@@" + mail_sent_history.get("eventTarget"));
				    Kogger.info(getClass(), "@@@@@@@@@@@@ toUser in hasmap @@@@@@@" + mail_sent_history.get("toUser"));
				    Kogger.info(getClass(), "@@@@@@@@@@@@ time in hashmap @@@@@@@" + mail_sent_history.get("time"));
				    check_sameMail = "Y";
				    check_vec.removeAllElements();
				    break;
				}
			    }
			    if (check_vec.size() > 30) {
				check_vec.removeAllElements();
			    }

			    HashMap mail_send_history = new HashMap();
			    mail_send_history.put("eventTarget", keyEvent.getEventTarget());
			    mail_send_history.put("toUser", toUserID);
			    mail_send_history.put("time", cal.get(Calendar.MINUTE));

			    check_vec.add(mail_send_history);

			    /* Logic End */

			    System.out
				    .println("::::::::::::::::::::::::::::: BOTTOM MAIL LOGIC START ::::::::::::::::::::::::::::::::::::::::::::::::");

			    WTUser fromUser = KETObjectUtil.getUser((String) ClassifyPBOUtil.extractPBOInfo(
				    item.getPrimaryBusinessObject().getObject()).get("creatorID"));
			    People checkP = PeopleHelper.manager.getPeople(fromUser.getName());
			    People checkToUser = PeopleHelper.manager.getPeople(toUserID);
			    if (!checkP.isIsDisable()) {
				// Kogger.info(getClass(), "?????? ??? ???...");
				if (delegatedUserID.length() > 0 && item.getOrigOwner() != null) {
				    Hashtable contentHash = MailUtil.getMailContent("approval", item, delegatedUserID);

				    /*
		                     * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
		                     */
				    String templateName = CommonUtil.getMailTemplateName(delegatedUserID, "ApprovalNoticeMail");

				    // contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"ApprovalNoticeMail.html");
				    contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
				    // ///////////////////////////////////////////////////////////////////////////////////////////////////

				    Hashtable hash = MailUtil.prepareMailInfoHash(pRef, contents, fromUserID);

				    if (hash != null && check_sameMail.equals("N")) {
					MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
				    }
				} else if (delegatedUserID.length() == 0) {
				    if (!checkToUser.isIsDisable()) { // toUserID ????????????? ??? x 2013.03.12 shkim add

					Hashtable contentHash = MailUtil.getMailContent("approval", item, toUserID);

					/*
			                 * [PLM System 1????? ?????? : ??? ???????? ?????? : 2013. 7. 21 ?????: ?????
			                 */
					String templateName = CommonUtil.getMailTemplateName(toUserID, "ApprovalNoticeMail");

					// contents =
					// MailHtmlContentTemplate.getInstance().htmlContent(contentHash,"ApprovalNoticeMail.html");
					contents = MailHtmlContentTemplate.getInstance().htmlContent(contentHash, templateName);
					// ///////////////////////////////////////////////////////////////////////////////////////////////////

					Hashtable hash = MailUtil.prepareMailInfoHash(pRef, contents, fromUserID);

					if (hash != null && check_sameMail.equals("N")) {

					    MailUtil.sendMail2(hash, (String) contentHash.get("mailTitle"));
					}
				    }
				}
			    }
			    System.out
				    .println("::::::::::::::::::::::::::::: BOTTOM MAIL LOGIC END ::::::::::::::::::::::::::::::::::::::::::::::::");
			} catch (Exception e) {
			    Kogger.error(getClass(), e);
			}
		    }
		}
	    }
	}
    }

    /**
     * 결재이력 순서 재정렬
     * 
     * @param histories
     * @throws Exception
     * @메소드명 : reNumberingApprovalHistory
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 31.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    private void reNumberingApprovalHistory(Vector<KETWfmApprovalHistory> histories) throws Exception {
	Kogger.info(getClass(), "결재이력 재정렬");
	Collections.sort(histories, ComparatorUtil.APPROVALLINESORT);
	int seq = 1;
	for (KETWfmApprovalHistory history : histories) {
	    history.setSeqNum(seq++);
	    history = (KETWfmApprovalHistory) PersistenceHelper.manager.save(history);
	    Kogger.info(getClass(), history.getSeqNum() + "." + history.getActivityName() + "/" + history.getOwner().getFullName() + "/"
		    + DateUtil.getDateString(history.getCompletedDate(), "a") + "/" + history.getDecision());
	}
    }
}
