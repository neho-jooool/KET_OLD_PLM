/*
 * PBO의 정보를 추출하기 위한 페이지
 * @team - 결재 시 Role을 지정하기 위한 team
 * @title - pbo의 제목 또는 이름
 * @type - pbo 유형 (ex. 도면, BOM, 일괄결재, ECO 등...)
 * @state - pbo 상태
 */

package e3ps.wfm.util;

import java.util.Hashtable;

import wt.access.NotAuthorizedException;
import wt.doc.WTDocument;
import wt.doc.WTDocumentMaster;
import wt.epm.EPMDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.team.TeamException;
import wt.team.TeamHelper;
import wt.team.TeamManaged;
import wt.util.WTException;
import e3ps.bom.entity.KETBomHeader;
import e3ps.common.message.KETMessageService;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.web.ParamUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.entity.KETChangeRequestExpansion;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEnd;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningPlanLink;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.Performance;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import e3ps.project.WorkProject;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.arm.service.KETAnalysisInfoHelper;
import ext.ket.cost.entity.CostReport;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmHeader;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.dqm.entity.KETDqmTodoAtivity;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.gate.entity.ProjectAssSheetLink;
import ext.ket.project.task.service.ProjectTaskCompHelper;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.sample.entity.KETSamplePartLink;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.shared.log.Kogger;

public class ClassifyPBOUtil {
    public static Hashtable<String, Object> extractPBOInfo(Object obj) throws NullPointerException, TeamException, NotAuthorizedException,
	    WTException {
	Hashtable<String, Object> pbo = new Hashtable<String, Object>();
	KETMessageService messageService = KETMessageService.getMessageService();
	Object team = null;
	String etc_p = WFMProperties.getInstance().getString("wfm.type.etc");
	try {
	    String title = "";
	    String state = "";
	    String creator = "";
	    String creatorID = "";
	    String ctime = "";
	    String type = "";
	    String taskName = "";
	    String step = "";
	    String version = "";

	    if (obj instanceof KETBomHeader) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETBomHeader Info");
		KETBomHeader bomHeader = (KETBomHeader) obj;
		String bom_p = WFMProperties.getInstance().getString("wfm.type.bom");
		team = bomHeader.getTeamId().getObject();
		title = bomHeader.getDescription();
		state = bomHeader.getState().getState().getDisplay(messageService.getLocale());
		creator = bomHeader.getCreatorFullName();
		creatorID = bomHeader.getCreatorName();
		String tempTime = DateUtil.getDateString(bomHeader.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = bom_p;
	    } else if (obj instanceof KETProjectDocument) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETProjectDocument Info");
		KETProjectDocument projDoc = (KETProjectDocument) obj;
		String docproj_p = WFMProperties.getInstance().getString("wfm.type.docproj");
		team = projDoc.getTeamId().getObject();
		title = projDoc.getTitle();
		state = projDoc.getState().getState().getDisplay(messageService.getLocale());
		creator = projDoc.getCreatorFullName();
		creatorID = projDoc.getCreatorName();
		String tempTime = DateUtil.getDateString(projDoc.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = docproj_p;
		version = projDoc.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETTechnicalDocument) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETTechnicalDocument Info");
		KETTechnicalDocument techDoc = (KETTechnicalDocument) obj;
		String techdoc_p = WFMProperties.getInstance().getString("wfm.type.techdoc");
		team = techDoc.getTeamId().getObject();
		title = techDoc.getTitle();
		state = techDoc.getState().getState().getDisplay(messageService.getLocale());
		creator = techDoc.getCreatorFullName();
		creatorID = techDoc.getCreatorName();
		String tempTime = DateUtil.getDateString(techDoc.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = techdoc_p;
		version = techDoc.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETDevelopmentRequest) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETDevelopmentRequest Info");
		KETDevelopmentRequest devReq = (KETDevelopmentRequest) obj;
		String devReq_p = "";
		if ("R".equals(devReq.getDevelopmentStep()))
		    devReq_p = WFMProperties.getInstance().getString("wfm.type.devreqdoc.R");
		else
		    devReq_p = WFMProperties.getInstance().getString("wfm.type.devreqdoc.D");
		team = devReq.getTeamId().getObject();
		step = devReq.getDevelopmentStep();
		title = devReq.getDevProductName();
		state = devReq.getState().getState().getDisplay(messageService.getLocale());
		creator = devReq.getCreatorFullName();
		creatorID = devReq.getCreatorName();
		String tempTime = DateUtil.getDateString(devReq.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = devReq_p;
		version = devReq.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETMoldChangeActivity) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETMoldChangeActivity Info");
		KETMoldChangeActivity moldEca = (KETMoldChangeActivity) obj;
		String moldca_p = WFMProperties.getInstance().getString("wfm.type.moldca");
		// Modified by MJOH, 2011-03-28
		team = moldEca.getMoldECO().getTeamId().getObject();
		// ///////////////////////////////////////////////////////////////////
		taskName = WFMProperties.getInstance().getString("wfm.task.moldca");

		if (moldEca.getActivityType().equals("1")) {
		    // title = "[도면]"+moldEca.getMoldECO().getEcoName();
		    title = moldEca.getMoldECO().getEcoName();
		    taskName = taskName + "(도면)";
		} else if (moldEca.getActivityType().equals("2")) {
		    // title = "[BOM]"+moldEca.getMoldECO().getEcoName();
		    title = moldEca.getMoldECO().getEcoName();
		    taskName = taskName + "(BOM)";
		} else if (moldEca.getActivityType().equals("3")) {
		    // title = "[문서]"+moldEca.getMoldECO().getEcoName();
		    title = moldEca.getMoldECO().getEcoName();
		    taskName = taskName + "(문서)";
		} else {
		    // title = "[문서]"+moldEca.getMoldECO().getEcoName();
		    title = moldEca.getMoldECO().getEcoName();
		    taskName = taskName + "(ECN)";
		}
		state = moldEca.getMoldECO().getState().getState().getDisplay(messageService.getLocale());
		creator = moldEca.getMoldECO().getCreatorFullName();
		creatorID = moldEca.getMoldECO().getCreatorName();
		String tempTime = DateUtil.getDateString(moldEca.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = moldca_p;
	    } else if (obj instanceof KETProdChangeActivity) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETProdChangeActivity Info");
		KETProdChangeActivity prodEca = (KETProdChangeActivity) obj;
		String prodca_p = WFMProperties.getInstance().getString("wfm.type.prodca");
		// Modified by MJOH, 2011-03-28
		team = prodEca.getProdECO().getTeamId().getObject();
		// ////////////////////////////////////////////////////////////////////////////////
		taskName = WFMProperties.getInstance().getString("wfm.task.prodca");

		if (prodEca.getActivityType().equals("1")) {
		    // title = "[도면]" + prodEca.getProdECO().getEcoName();
		    title = prodEca.getProdECO().getEcoName();
		    taskName = taskName + "(도면)";
		} else if (prodEca.getActivityType().equals("2")) {
		    // title = "[BOM]" + prodEca.getProdECO().getEcoName();
		    title = prodEca.getProdECO().getEcoName();
		    taskName = taskName + "(BOM)";
		} else if (prodEca.getActivityType().equals("3")) {
		    // title = "[문서]" + prodEca.getProdECO().getEcoName();
		    title = prodEca.getProdECO().getEcoName();
		    // taskName = taskName + "(문서)";
		    taskName = taskName + "(ECN)";
		} else {
		    // title = "[후속활동]" + prodEca.getProdECO().getEcoName();
		    title = prodEca.getProdECO().getEcoName();
		    // taskName = taskName + "(후속활동)";
		    taskName = taskName + "(ECN)";
		}
		state = prodEca.getProdECO().getState().getState().getDisplay(messageService.getLocale());
		creator = prodEca.getProdECO().getCreatorFullName();
		creatorID = prodEca.getProdECO().getCreatorName();
		String tempTime = DateUtil.getDateString(prodEca.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = prodca_p;
	    } else if (obj instanceof KETMoldChangeOrder) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETMoldChangeOrder Info");
		KETMoldChangeOrder moldCO = (KETMoldChangeOrder) obj;
		String moldco_p = WFMProperties.getInstance().getString("wfm.type.moldco");
		team = moldCO.getTeamId().getObject();
		title = moldCO.getEcoName();
		state = moldCO.getState().getState().getDisplay(messageService.getLocale());
		creator = moldCO.getCreatorFullName();
		creatorID = moldCO.getCreatorName();
		String tempTime = DateUtil.getDateString(moldCO.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = moldco_p;
		taskName = WFMProperties.getInstance().getString("wfm.task.moldco");
		version = moldCO.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETMoldChangeRequest) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETMoldChangeRequest Info");
		KETMoldChangeRequest moldCR = (KETMoldChangeRequest) obj;
		String moldcr_p = WFMProperties.getInstance().getString("wfm.type.moldcr");
		team = moldCR.getTeamId().getObject();
		title = moldCR.getEcrName();
		state = moldCR.getState().getState().getDisplay(messageService.getLocale());
		creator = moldCR.getCreatorFullName();
		creatorID = moldCR.getCreatorName();
		String tempTime = DateUtil.getDateString(moldCR.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = moldcr_p;
		version = moldCR.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETProdChangeOrder) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETProdChangeOrder Info");
		KETProdChangeOrder prodCO = (KETProdChangeOrder) obj;
		String prodco_p = WFMProperties.getInstance().getString("wfm.type.prodco");
		team = prodCO.getTeamId().getObject();
		title = prodCO.getEcoName();
		state = prodCO.getState().getState().getDisplay(messageService.getLocale());
		creator = prodCO.getCreatorFullName();
		creatorID = prodCO.getCreatorName();
		String tempTime = DateUtil.getDateString(prodCO.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = prodco_p;
		taskName = WFMProperties.getInstance().getString("wfm.task.prodco");
		version = prodCO.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETProdChangeRequest) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETProdChangeRequest Info");
		KETProdChangeRequest prodCR = (KETProdChangeRequest) obj;
		String prodcr_p = WFMProperties.getInstance().getString("wfm.type.prodcr");
		team = prodCR.getTeamId().getObject();
		title = prodCR.getEcrName();
		state = prodCR.getState().getState().getDisplay(messageService.getLocale());
		creator = prodCR.getCreatorFullName();
		creatorID = prodCR.getCreatorName();
		String tempTime = DateUtil.getDateString(prodCR.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = prodcr_p;
		version = prodCR.getVersionInfo().getIdentifier().getValue();
	    }
	    /*
	     * ECR - 담당자 Todo
	     */
	    else if (obj instanceof e3ps.ecm.entity.KETChangeRequestExpansion) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETCompetentDepartment Info");
		e3ps.ecm.entity.KETChangeRequestExpansion expansion = (e3ps.ecm.entity.KETChangeRequestExpansion) obj;

		wt.change2.WTChangeRequest2 ecr = expansion.getEcr();

		if (ecr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
		    e3ps.ecm.entity.KETProdChangeRequest ketProdChangeRequest = (e3ps.ecm.entity.KETProdChangeRequest) ecr;
		    title = ketProdChangeRequest.getEcrName();
		    taskName = WFMProperties.getInstance().getString("wfm.type.prodco");
		    state = ketProdChangeRequest.getEcrStateState().getDisplay(messageService.getLocale());

		} else if (ecr instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
		    e3ps.ecm.entity.KETMoldChangeRequest ketMoldChangeRequest = (e3ps.ecm.entity.KETMoldChangeRequest) ecr;
		    title = ketMoldChangeRequest.getEcrName();
		    taskName = WFMProperties.getInstance().getString("wfm.type.moldco");
		    state = ketMoldChangeRequest.getEcrStateState().getDisplay(messageService.getLocale());

		}

		String prodcr_p = WFMProperties.getInstance().getString("wfm.type.ecr.competent");
		team = expansion.getTeamId().getObject();
		// title = prodCR.getEcrName();
		creator = expansion.getCreatorFullName();
		creatorID = expansion.getCreatorName();
		String tempTime = DateUtil.getDateString(expansion.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = prodcr_p;

	    }
	    /*
	     * ECR - 주관부서
	     */
	    else if (obj instanceof e3ps.ecm.entity.KETCompetentDepartment) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETCompetentDepartment Info");
		e3ps.ecm.entity.KETCompetentDepartment competent = (e3ps.ecm.entity.KETCompetentDepartment) obj;

		wt.change2.WTChangeRequest2 prodCR = null;
		QueryResult qr = PersistenceHelper.manager.navigate(competent, "ecr", e3ps.ecm.entity.KETEcrCompetentLink.class, false);
		while (qr.hasMoreElements()) {
		    e3ps.ecm.entity.KETEcrCompetentLink link = (e3ps.ecm.entity.KETEcrCompetentLink) qr.nextElement();
		    prodCR = link.getEcr();

		    if (prodCR instanceof e3ps.ecm.entity.KETProdChangeRequest) {
			title = ((e3ps.ecm.entity.KETProdChangeRequest) prodCR).getEcrName();
		    } else if (prodCR instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
			title = ((e3ps.ecm.entity.KETMoldChangeRequest) prodCR).getEcrName();
		    }
		}

		String prodcr_p = WFMProperties.getInstance().getString("wfm.type.ecr.competent");
		team = competent.getTeamId().getObject();
		// title = prodCR.getEcrName();
		state = competent.getState().getState().getDisplay(messageService.getLocale());
		creator = competent.getCreatorFullName();
		creatorID = competent.getCreatorName();
		String tempTime = DateUtil.getDateString(competent.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = prodcr_p;
	    }
	    /*
	     * ECR - 회의록
	     */
	    else if (obj instanceof e3ps.ecm.entity.KETMeetingMinutes) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETMeetingMinutes Info");
		e3ps.ecm.entity.KETMeetingMinutes meeting = (e3ps.ecm.entity.KETMeetingMinutes) obj;

		wt.change2.WTChangeRequest2 prodCR = null;
		QueryResult qr = PersistenceHelper.manager.navigate(meeting, "ecr", e3ps.ecm.entity.KETEcrMeetingLink.class, false);
		while (qr.hasMoreElements()) {
		    e3ps.ecm.entity.KETEcrMeetingLink link = (e3ps.ecm.entity.KETEcrMeetingLink) qr.nextElement();
		    prodCR = link.getEcr();

		    if (prodCR instanceof e3ps.ecm.entity.KETProdChangeRequest) {
			title = ((e3ps.ecm.entity.KETProdChangeRequest) prodCR).getEcrName();
		    } else if (prodCR instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
			title = ((e3ps.ecm.entity.KETMoldChangeRequest) prodCR).getEcrName();
		    }
		}

		String prodcr_p = WFMProperties.getInstance().getString("wfm.type.ecr.meeting");
		team = meeting.getTeamId().getObject();
		// title = prodCR.getEcrName();
		state = meeting.getState().getState().getDisplay(messageService.getLocale());
		creator = meeting.getCreatorFullName();
		creatorID = meeting.getCreatorName();
		String tempTime = DateUtil.getDateString(meeting.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = prodcr_p;
	    } else if (obj instanceof EPMDocument) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get EPMDocument Info");
		EPMDocument epmdoc = (EPMDocument) obj;
		String epmdoc_p = WFMProperties.getInstance().getString("wfm.type.epmdoc");
		if (epmdoc.getTeamId() != null)
		    team = epmdoc.getTeamId().getObject();
		title = epmdoc.getName();
		state = epmdoc.getState().getState().getDisplay(messageService.getLocale());
		creator = epmdoc.getCreatorFullName();
		creatorID = epmdoc.getCreatorName();
		String tempTime = DateUtil.getDateString(epmdoc.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = epmdoc_p;
		version = epmdoc.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETWfmMultiApprovalRequest) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETWfmMultiApprovalRequest Info");
		KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) obj;
		String multiepm_p = WFMProperties.getInstance().getString("wfm.type.multiepm");
		if (multiReq.getAttribute2() != null && multiReq.getAttribute2().length() > 0) {
		    multiepm_p = "산출물 일괄결재";
		}

		team = multiReq.getTeamId().getObject();
		title = multiReq.getReqName();
		state = multiReq.getState().getState().getDisplay(messageService.getLocale());
		creator = multiReq.getCreatorFullName();
		creatorID = multiReq.getCreatorName();
		String tempTime = DateUtil.getDateString(multiReq.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = multiepm_p;
	    } else if (obj instanceof KETEarlyWarning) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETEarlyWarning Info");
		KETEarlyWarning ew = (KETEarlyWarning) obj;
		String ew_p = WFMProperties.getInstance().getString("wfm.type.ew");
		team = ew.getTeamId().getObject();
		title = ew.getName() + " - 초기유동관리지정";
		state = ew.getState().getState().getDisplay(messageService.getLocale());
		creator = ew.getCreatorFullName();
		creatorID = ew.getCreatorName();
		String tempTime = DateUtil.getDateString(ew.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = ew_p;
		taskName = WFMProperties.getInstance().getString("wfm.task.ewplan");
		version = ew.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETEarlyWarningPlan) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETEarlyWarningPlan Info");
		KETEarlyWarningPlan ewPlan = (KETEarlyWarningPlan) obj;
		String ewplan_p = WFMProperties.getInstance().getString("wfm.type.ewplan");
		team = ewPlan.getTeamId().getObject();
		title = ewPlan.getName() + " - 초기유동관리계획서";
		state = ewPlan.getState().getState().getDisplay(messageService.getLocale());
		creator = ewPlan.getCreatorFullName();
		creatorID = ewPlan.getCreatorName();
		String tempTime = DateUtil.getDateString(ewPlan.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = ewplan_p;
		taskName = WFMProperties.getInstance().getString("wfm.task.ewresult");
		version = ewPlan.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETEarlyWarningResult) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETEarlyWarningResult Info");
		KETEarlyWarningResult ewResult = (KETEarlyWarningResult) obj;
		String ewresult_p = WFMProperties.getInstance().getString("wfm.type.ewresult");
		team = ewResult.getTeamId().getObject();
		title = ewResult.getName();
		state = ewResult.getState().getState().getDisplay(messageService.getLocale());
		creator = ewResult.getCreatorFullName();
		creatorID = ewResult.getCreatorName();
		String tempTime = DateUtil.getDateString(ewResult.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = ewresult_p;
		taskName = WFMProperties.getInstance().getString("wfm.task.ewendreq");
		version = ewResult.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETEarlyWarningEndReq) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETEarlyWarningEndReq Info");
		KETEarlyWarningEndReq ewEndReq = (KETEarlyWarningEndReq) obj;
		String ewEndReq_p = WFMProperties.getInstance().getString("wfm.type.ewendreq");
		team = ewEndReq.getTeamId().getObject();
		title = ewEndReq.getName() + " - 초기유동관리해제신청";
		state = ewEndReq.getState().getState().getDisplay(messageService.getLocale());
		creator = ewEndReq.getCreatorFullName();
		creatorID = ewEndReq.getCreatorName();
		String tempTime = DateUtil.getDateString(ewEndReq.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = ewEndReq_p;
		taskName = WFMProperties.getInstance().getString("wfm.task.ewend");
		version = ewEndReq.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETEarlyWarningEnd) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETEarlyWarningEnd Info");
		KETEarlyWarningEnd ewEnd = (KETEarlyWarningEnd) obj;
		String ewEnd_p = WFMProperties.getInstance().getString("wfm.type.ewend");
		team = ewEnd.getTeamId().getObject();
		title = ewEnd.getName() + " - 초기유동관리해제판정";
		state = ewEnd.getState().getState().getDisplay(messageService.getLocale());
		creator = ewEnd.getCreatorFullName();
		creatorID = ewEnd.getCreatorName();
		String tempTime = DateUtil.getDateString(ewEnd.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = ewEnd_p;
		taskName = WFMProperties.getInstance().getString("wfm.task.ewplan");
		version = ewEnd.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof WTPart) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTPart Info");
		WTPart part = (WTPart) obj;
		String part_p = WFMProperties.getInstance().getString("wfm.type.part");
		if (part.getTeamId() != null)
		    team = part.getTeamId().getObject();
		title = part.getName();
		state = part.getState().getState().getDisplay(messageService.getLocale());
		creator = part.getCreatorFullName();
		creatorID = part.getCreatorName();
		String tempTime = DateUtil.getDateString(part.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = part_p;
		version = part.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof E3PSProject) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get E3PSProject Info");
		E3PSProject project = (E3PSProject) obj;
		String project_p = "";
		if (obj instanceof ReviewProject) {
		    // 검토 프로젝트
		    project_p = WFMProperties.getInstance().getString("wfm.type.reviewproject");
		} else if (obj instanceof ProductProject) {
		    // 제품 프로젝트
		    project_p = WFMProperties.getInstance().getString("wfm.type.productproject");
		} else if (obj instanceof WorkProject) {
		    // 업무 프로젝트
		    project_p = WFMProperties.getInstance().getString("wfm.type.workproject");
		} else if (obj instanceof MoldProject) {

		    MoldProject mProject = (MoldProject) project;
		    MoldItemInfo moldInfo = mProject.getMoldInfo();

		    if ("구매품".equals(moldInfo.getItemType())) {
			project_p = WFMProperties.getInstance().getString("wfm.type.purchaseproject");
		    } else {
			// 금형 프로젝트
			project_p = WFMProperties.getInstance().getString("wfm.type.moldproject");
		    }
		}
		team = project.getTeamId().getObject();
		if (obj instanceof MoldProject) {
		    MoldProject mp = (MoldProject) obj;
		    MoldItemInfo mi = mp.getMoldInfo();
		    title = project.getPjtNo();
		    if (mi != null) {
			if (mi.getPartName().length() > 0) {
			    title = title + "(" + mi.getPartName() + ")";
			}
		    }
		} else {
		    title = project.getPjtName();
		}
		state = project.getState().getState().getDisplay(messageService.getLocale());
		creator = project.getCreatorFullName();
		creatorID = project.getCreatorName();
		String tempTime = DateUtil.getDateString(project.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = project_p;
	    } else if (obj instanceof Performance) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get Performance Info");
		Performance per = (Performance) obj;
		String performance_p = WFMProperties.getInstance().getString("wfm.type.performance");
		team = per.getTeamId().getObject();
		title = "성과관리 [Project No :" + per.getKeyNo() + "]";
		state = per.getState().getState().getDisplay(messageService.getLocale());
		creator = per.getCreatorFullName();
		creatorID = per.getCreatorName();
		String tempTime = DateUtil.getDateString(per.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = performance_p;
	    } else if (obj instanceof KETDrawingDistRequest) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		KETDrawingDistRequest ketDrawingDistRequest = (KETDrawingDistRequest) obj;
		String drawingdistrequset_p = WFMProperties.getInstance().getString("wfm.type.drawingdistrequset");
		title = ketDrawingDistRequest.getTitle();
		state = ketDrawingDistRequest.getState().getState().getDisplay(messageService.getLocale());
		creator = ketDrawingDistRequest.getCreator().getFullName();
		creatorID = ketDrawingDistRequest.getCreatorName();
		team = ketDrawingDistRequest.getTeamId().getObject();
		String tempTime = DateUtil.getDateString(ketDrawingDistRequest.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = drawingdistrequset_p;
	    } else if (obj instanceof AssessSheet) {
		// 프로젝트 평가시트
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		AssessSheet assSheet = (AssessSheet) obj;

		E3PSProject proj = new E3PSProject();
		QueryResult qr = PersistenceHelper.manager.navigate(assSheet, "project", ProjectAssSheetLink.class);
		if (qr.hasMoreElements()) {
		    proj = (E3PSProject) qr.nextElement();
		}
		team = assSheet.getTeamId().getObject();

		String dqmraise_p = WFMProperties.getInstance().getString("wfm.type.assessRequest");
		title = proj.getPjtName() + " 목표승인 요청";
		state = assSheet.getState().getState().getDisplay(messageService.getLocale());
		creator = assSheet.getCreator().getFullName();
		creatorID = assSheet.getCreatorName();
		String tempTime = DateUtil.getDateString(assSheet.getPersistInfo().getCreateStamp(), "d");
		// version = assSheet.getVersionIdentifier().getValue();
		ctime = tempTime;
		type = dqmraise_p;
	    } else if (obj instanceof GateAssessResult) {
		// 프로젝트 평가시트 결과
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		GateAssessResult gateAssResult = (GateAssessResult) obj;

		E3PSTask task = ProjectTaskCompHelper.service.getTask(gateAssResult);

		String pjtName = task.getProject().getPjtName();

		team = gateAssResult.getTeamId().getObject();
		String dqmraise_p = WFMProperties.getInstance().getString("wfm.type.assRsltRequest");
		title = pjtName + " / " + gateAssResult.getName() + " 평가결과";
		state = gateAssResult.getState().getState().getDisplay(messageService.getLocale());
		creator = gateAssResult.getCreator().getFullName();
		creatorID = gateAssResult.getCreatorName();
		String tempTime = DateUtil.getDateString(gateAssResult.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = dqmraise_p;
	    }
	    // DQM
	    else if (obj instanceof KETDqmRaise) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		KETDqmRaise ketDqmRaise = (KETDqmRaise) obj;
		team = ketDqmRaise.getTeamId().getObject();
		String dqmraise_p = WFMProperties.getInstance().getString("wfm.type.dqmraise");
		QuerySpec query = new QuerySpec();
		SearchCondition sc = null;
		int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
		sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(ketDqmRaise));
		query.appendWhere(sc, new int[] { idxHeaer });
		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];
		    title = ketDqmHeader.getProblem();
		    // state = ketDqmHeader.getDqmStateName();
		}
		state = ketDqmRaise.getState().getState().getDisplay(messageService.getLocale());
		creator = ketDqmRaise.getCreator().getFullName();
		creatorID = ketDqmRaise.getCreatorName();
		String tempTime = DateUtil.getDateString(ketDqmRaise.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = dqmraise_p;
	    } else if (obj instanceof KETDqmAction) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		KETDqmAction ketDqmAction = (KETDqmAction) obj;
		team = ketDqmAction.getTeamId().getObject();
		String dqmraise_p = WFMProperties.getInstance().getString("wfm.type.dqmaction");
		QuerySpec query = new QuerySpec();
		SearchCondition sc = null;
		int idxHeaer = query.appendClassList(KETDqmHeader.class, true);
		sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
		        CommonUtil.getOIDLongValue(ketDqmAction));
		query.appendWhere(sc, new int[] { idxHeaer });
		QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
		while (qr.hasMoreElements()) {
		    Object[] tempObj = (Object[]) qr.nextElement();
		    KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];
		    title = ketDqmHeader.getProblem();
		    // state = ketDqmHeader.getDqmStateName();
		}
		state = ketDqmAction.getState().getState().getDisplay(messageService.getLocale());
		creator = ketDqmAction.getCreator().getFullName();
		creatorID = ketDqmAction.getCreatorName();
		String tempTime = DateUtil.getDateString(ketDqmAction.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = dqmraise_p;
	    } else if (obj instanceof KETDqmTodoAtivity) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) obj;

		KETDqmHeader ketDqmHeader = ketDqmTodoAtivity.getHeader();

		String dqm = WFMProperties.getInstance().getString("wfm.type.dqm");

		if (ketDqmTodoAtivity.getTaskCode().equals("REVIEW_USER_CHOISE")) {
		    taskName = ketDqmTodoAtivity.getTaskName();
		    title = ketDqmHeader.getProblem();
		} else if (ketDqmTodoAtivity.getTaskCode().equals("DQM_ACTION")) {
		    String stateCode = ketDqmTodoAtivity.getHeader().getDqmStateCode();
		    if ("ACTIONPROGRESS".equals(stateCode)) {
			taskName = "개선안진행";
		    } else {
			taskName = ketDqmTodoAtivity.getTaskName();
		    }

		    title = ketDqmHeader.getProblem();
		} else if (ketDqmTodoAtivity.getTaskCode().equals("DQM_CLOSE")) {
		    taskName = ketDqmTodoAtivity.getTaskName();
		    title = ketDqmHeader.getProblem();
		} else {
		    taskName = ketDqmTodoAtivity.getTaskName();
		    title = ketDqmHeader.getProblem();
		}

		state = ketDqmTodoAtivity.getState().getState().getDisplay(messageService.getLocale());
		creator = ketDqmTodoAtivity.getCreator().getFullName();
		creatorID = ketDqmTodoAtivity.getCreatorName();
		String tempTime = DateUtil.getDateString(ketDqmTodoAtivity.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = dqm;
	    } else if (obj instanceof KETSampleRequest) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		KETSampleRequest ketSampleRequest = (KETSampleRequest) obj;
		team = ketSampleRequest.getTeamId().getObject();
		String dqmraise_p = WFMProperties.getInstance().getString("wfm.type.samplerequest");
		title = ketSampleRequest.getRequestTitle();
		state = ketSampleRequest.getState().getState().getDisplay(messageService.getLocale());
		creator = ketSampleRequest.getCreator().getFullName();
		creatorID = ketSampleRequest.getCreatorName();
		String tempTime = DateUtil.getDateString(ketSampleRequest.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = dqmraise_p;
	    } else if (obj instanceof KETSamplePart) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		KETSamplePart ketSamplePart = (KETSamplePart) obj;
		team = ketSamplePart.getTeamId().getObject();
		String dqmraise_p = WFMProperties.getInstance().getString("wfm.type.samplerequest");

		QueryResult qrLink = PersistenceHelper.manager.navigate(ketSamplePart, "request", KETSamplePartLink.class);

		while (qrLink.hasMoreElements()) {
		    KETSampleRequest ketSampleRequest = (KETSampleRequest) qrLink.nextElement();
		    state = ketSampleRequest.getSampleRequestStateName();
		    if (ketSampleRequest.getSampleRequestStateCode().equals("DISPENSATION")) {
			title = ketSampleRequest.getRequestTitle() + " Sample " + state;// ketSampleRequest.getRequestTitle();
			taskName = "Sample " + state;
			creator = ketSamplePart.getUser().getFullName();
			creatorID = ketSamplePart.getUser().getName();
		    } else {
			title = ketSamplePart.getPart().getNumber() + " Sample " + state;// ketSampleRequest.getRequestTitle();
			taskName = "Sample " + state;
			creator = ketSamplePart.getCreator().getFullName();
			creatorID = ketSamplePart.getCreatorName();
		    }

		    String tempTime = DateUtil.getDateString(ketSamplePart.getPersistInfo().getCreateStamp(), "d");
		    ctime = tempTime;
		    type = dqmraise_p;
		    break;
		}
	    } else if (obj instanceof KETTryCondition) {
		KETTryCondition tryCondition = (KETTryCondition) obj;
		team = tryCondition.getTeamId().getObject();
		String tryConditionType = WFMProperties.getInstance().getString("wfm.type.tryCondition");
		state = tryCondition.getLifeCycleState().getDisplay(messageService.getLocale());
		title = tryCondition.getName();
		// taskName = "Sample " + state;
		creator = tryCondition.getCreator().getFullName();
		creatorID = tryCondition.getCreatorName();
		type = tryConditionType;
	    } else if (obj instanceof KETAnalysisRequestMaster) {
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get KETAnalysisRequestMaster Info");
		// 해석의뢰 team 생성
		KETAnalysisRequestMaster arm = (KETAnalysisRequestMaster) obj;
		String analysis_type = "";
		analysis_type = WFMProperties.getInstance().getString("wfm.type.analysis");
		team = arm.getTeamId().getObject();
		// step = arm.getDevelopmentStep();
		title = arm.getAnalysisTitle();
		state = arm.getState().getState().getDisplay(messageService.getLocale());
		creator = arm.getCreatorFullName();
		creatorID = arm.getCreatorName();
		String tempTime = DateUtil.getDateString(arm.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = analysis_type;
		version = arm.getVersionInfo().getIdentifier().getValue();
	    } else if (obj instanceof KETAnalysisRequestInfo) {
		Kogger.debug(ClassifyPBOUtil.class, ">>>>>>>>>> ClassifyPBOUtil.java :: get KETAnalysisRequestInfo");
		// 해석의뢰 team 생성
		KETAnalysisRequestInfo arm = (KETAnalysisRequestInfo) obj;
		String analysis_type = "";
		analysis_type = WFMProperties.getInstance().getString("wfm.type.analysisInfo");
		team = arm.getTeamId().getObject();
		title = KETAnalysisInfoHelper.service.getAnalysisInfoTitle(CommonUtil.getOIDLongValue(arm));
		// title = KETAnalysisRequestHelper.service.getAnalysisDivisionCodeLink(getAnalysisRequestOid());
		state = arm.getState().getState().getDisplay(messageService.getLocale());
		creator = arm.getCreatorFullName();
		creatorID = arm.getCreatorName();
		String tempTime = DateUtil.getDateString(arm.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = analysis_type;
	    } else if (obj instanceof KETSalesProject) {
		Kogger.debug(ClassifyPBOUtil.class, ">>>>>>>>>> ClassifyPBOUtil.java :: get KETSalesProject");
		// 영업프로젝트 team 생성
		KETSalesProject salesProject = (KETSalesProject) obj;
		String salesproject_type = "";
		salesproject_type = WFMProperties.getInstance().getString("wfm.type.salesproject");
		team = salesProject.getTeamId().getObject();
		title = salesProject.getProjectName();
		// title = KETAnalysisRequestHelper.service.getAnalysisDivisionCodeLink(getAnalysisRequestOid());
		state = salesProject.getState().getState().getDisplay(messageService.getLocale());
		creator = salesProject.getCreatorFullName();
		creatorID = salesProject.getCreatorName();
		String tempTime = DateUtil.getDateString(salesProject.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = salesproject_type;
	    } else if (obj instanceof KETSalesCSMeetingApproval) {
		Kogger.debug(ClassifyPBOUtil.class, ">>>>>>>>>> ClassifyPBOUtil.java :: get KETSalesCSMeetingApproval");
		// 영업프로젝트 team 생성
		KETSalesCSMeetingApproval approve = (KETSalesCSMeetingApproval) obj;

		String salesproject_type = "";

		salesproject_type = WFMProperties.getInstance().getString("wfm.type.salesproject");
		team = approve.getTeamId().getObject();
		title = "CS회의순서";
		// title = KETAnalysisRequestHelper.service.getAnalysisDivisionCodeLink(getAnalysisRequestOid());
		state = approve.getState().getState().getDisplay(messageService.getLocale());
		creator = approve.getCreatorFullName();
		creatorID = approve.getCreatorName();
		String tempTime = DateUtil.getDateString(approve.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = salesproject_type;
	    } else if (obj instanceof CostReport) {
		Kogger.debug(ClassifyPBOUtil.class, ">>>>>>>>>> ClassifyPBOUtil.java :: get CostReport");
		// 원가보고서 team 생성
		CostReport approve = (CostReport) obj;

		team = approve.getTeamId().getObject();
		String pjtName = approve.getTask().getProject().getPjtName();
		String ReleaseStep = approve.getReleaseStep();
		String drStep = approve.getStep();
		version = "REV." + approve.getVersion();

		title = "원가보고서 - " + pjtName + "(" + ReleaseStep + "/" + drStep + "/" + version + ")";
		// title = KETAnalysisRequestHelper.service.getAnalysisDivisionCodeLink(getAnalysisRequestOid());
		state = approve.getState().getState().getDisplay(messageService.getLocale());
		creator = approve.getCreatorFullName();
		creatorID = approve.getCreatorName();
		String tempTime = DateUtil.getDateString(approve.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = "개발원가";
	    } else if (obj instanceof KETInvestMaster) {
		Kogger.debug(ClassifyPBOUtil.class, ">>>>>>>>>> ClassifyPBOUtil.java :: get KETInvestMaster");
		// 고객 직접투자비 team 생성
		KETInvestMaster im = (KETInvestMaster) obj;

		team = im.getTeamId().getObject();
		title = "고객투자비 회수관리 / " + im.getReqName();

		state = im.getState().getState().getDisplay(messageService.getLocale());
		creator = im.getCreatorFullName();
		creatorID = im.getCreatorName();
		String tempTime = DateUtil.getDateString(im.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = "고객투자비";
	    } else if (obj instanceof WTDocument) {// 요것이 맨 아래에 정의되어야 합니다..
		// Kogger.debug(getClass(), ">>>>>>>>>> ClassifyPBOUtil.java :: get WTDocument Info");
		WTDocument doc = (WTDocument) obj;
		team = doc.getTeamId().getObject();
		String wtdoc_p = WFMProperties.getInstance().getString("wfm.type.wtdoc");
		title = doc.getName();
		state = doc.getState().getState().getDisplay(messageService.getLocale());
		creator = doc.getCreator().getFullName();
		creatorID = doc.getCreatorName();
		String tempTime = DateUtil.getDateString(doc.getPersistInfo().getCreateStamp(), "d");
		ctime = tempTime;
		type = wtdoc_p;
		if (doc.getTeamId() != null) {
		    pbo.put("team", doc.getTeamId().getObject());
		}
		version = doc.getVersionInfo().getIdentifier().getValue();
	    }

	    // 테스트용
	    if (team != null)
		pbo.put("team", team);

	    pbo.put("title", ParamUtil.checkStrParameter(title, etc_p));
	    pbo.put("step", ParamUtil.checkStrParameter(step, etc_p));
	    pbo.put("state", ParamUtil.checkStrParameter(state, etc_p));
	    pbo.put("creator", ParamUtil.checkStrParameter(creator, etc_p));
	    pbo.put("creatorID", ParamUtil.checkStrParameter(creatorID, ""));
	    pbo.put("ctime", ParamUtil.checkStrParameter(ctime, etc_p));
	    pbo.put("type", ParamUtil.checkStrParameter(type, etc_p));
	    pbo.put("task", ParamUtil.checkStrParameter(taskName, etc_p));
	    pbo.put("version", version);
	} catch (Exception e) {
	    if (team != null)
		pbo.put("team", TeamHelper.service.getTeam((TeamManaged) obj));
	    pbo.put("title", etc_p);
	    pbo.put("step", etc_p);
	    pbo.put("state", etc_p);
	    pbo.put("creator", etc_p);
	    pbo.put("creatorID", etc_p);
	    pbo.put("ctime", etc_p);
	    pbo.put("type", etc_p);
	    pbo.put("task", etc_p);

	    Kogger.error(ClassifyPBOUtil.class, e);

	}
	return pbo;
    }

    /**
     * @param target
     *            : 저장이나 개정된 객체
     * @return url : ToDo 작업을 위한 페이지 주소
     * @throws Exception
     */
    public static String getTaskUrl(Object target) throws Exception {
	String url = "ReviewTask.jsp?oid=" + target.toString();

	KETEarlyWarning ew = new KETEarlyWarning();

	if (target instanceof KETEarlyWarning) {
	    url = "/plm/jsp/ews/TodoEarlyWarningPlan.jsp?oid=" + target.toString();
	} else if (target instanceof KETEarlyWarningPlan) {
	    ew = WorkflowSearchHelper.manager.getEW(target);
	    url = "/plm/jsp/ews/CreateEarlyWarningResult.jsp?todo=Y&oid=" + ew.toString();
	} else if (target instanceof KETEarlyWarningResult) {
	    ew = WorkflowSearchHelper.manager.getEW(target);
	    url = "/plm/jsp/ews/TodoEarlyWarningEndReq.jsp?oid=" + ew.toString();
	} else if (target instanceof KETEarlyWarningEnd) {
	    ew = WorkflowSearchHelper.manager.getEW(target);
	    KETEarlyWarningPlan plan = new KETEarlyWarningPlan();
	    QueryResult qrLink = PersistenceHelper.manager.navigate(ew.getMaster(), "plan", KETEarlyWarningPlanLink.class, false);

	    while (qrLink.hasMoreElements()) {
		KETEarlyWarningPlanLink planLink = (KETEarlyWarningPlanLink) qrLink.nextElement();
		WTDocumentMaster master = planLink.getPlan();
		plan = (KETEarlyWarningPlan) ObjectUtil.getLatestObject(master);
	    }

	    url = "/plm/jsp/ews/ViewEarlyWarningPlan.jsp?todo=Y&planOid=" + plan.toString();
	} else if (target instanceof KETChangeRequestExpansion) {
	    e3ps.ecm.entity.KETChangeRequestExpansion expansion = (e3ps.ecm.entity.KETChangeRequestExpansion) target;

	    wt.change2.WTChangeRequest2 ecr = expansion.getEcr();
	    if (ecr instanceof e3ps.ecm.entity.KETProdChangeRequest) {
		String ecrOid = CommonUtil.getOIDString(ecr);
		url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=PopupView&oid=" + ecrOid + "&tabId=2";

	    } else if (ecr instanceof e3ps.ecm.entity.KETMoldChangeRequest) {
		String ecrOid = CommonUtil.getOIDString(ecr);
		url = "/plm/servlet/e3ps/ProdEcrServlet?cmd=PopupView&oid=" + ecrOid + "&tabId=2";
	    }

	} else if (target instanceof KETMoldChangeActivity) {
	    KETMoldChangeActivity moldEca = (KETMoldChangeActivity) target;
	    String ecoOid = CommonUtil.getOIDString(moldEca.getMoldECO());
	    url = "/plm/servlet/e3ps/MoldEcoChangeServlet?cmd=updateview&oid=" + ecoOid + "&ecaType=" + moldEca.getActivityType();
	} else if (target instanceof KETProdChangeActivity) {
	    KETProdChangeActivity prodEca = (KETProdChangeActivity) target;
	    String ecoOid = CommonUtil.getOIDString(prodEca.getProdECO());
	    url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=updateview&oid=" + ecoOid + "&ecaType=" + prodEca.getActivityType();
	} else if (target instanceof KETMoldChangeOrder) {
	    KETMoldChangeOrder moldCO = (KETMoldChangeOrder) target;
	    /*
	     * if (moldCO.getLifeCycleState().equals(State.toState("ACTIVITYCOMPLETED")) ||
	     * moldCO.getLifeCycleState().equals(State.toState("APPROVED")) || moldCO.getLifeCycleState().equals(State.toState("REWORK"))) {
	     */
	    String ecoOid = CommonUtil.getOIDString(moldCO);
	    url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=view&oid=" + ecoOid;
	    /*
	     * }
	     */
	} else if (target instanceof KETProdChangeOrder) {
	    KETProdChangeOrder prodCO = (KETProdChangeOrder) target;

	    /*
	     * if (prodCO.getLifeCycleState().equals(State.toState("ACTIVITYCOMPLETED")) ||
	     * prodCO.getLifeCycleState().equals(State.toState("APPROVED")) || prodCO.getLifeCycleState().equals(State.toState("REWORK"))) {
	     */
	    String ecoOid = CommonUtil.getOIDString(prodCO);
	    url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=view&oid=" + ecoOid;
	    /*
	     * }
	     */
	} else if (target instanceof KETDqmRaise) {
	    KETDqmRaise ketDqmRaise = (KETDqmRaise) target;

	    String headerOid = "";
	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

	    sc = new SearchCondition(KETDqmHeader.class, "raiseReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmRaise));
	    query.appendWhere(sc, new int[] { idxHeaer });

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];
		headerOid = CommonUtil.getOIDString(ketDqmHeader);
	    }

	    url = "/plm/ext/dqm/dqmMainForm.do?type=raise&oid=" + headerOid;
	} else if (target instanceof KETDqmAction) {
	    KETDqmAction ketDqmAction = (KETDqmAction) target;

	    String headerOid = "";
	    QuerySpec query = new QuerySpec();
	    SearchCondition sc = null;
	    int idxHeaer = query.appendClassList(KETDqmHeader.class, true);

	    sc = new SearchCondition(KETDqmHeader.class, "actionReference.key.id", SearchCondition.EQUAL,
		    CommonUtil.getOIDLongValue(ketDqmAction));
	    query.appendWhere(sc, new int[] { idxHeaer });

	    QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);

	    while (qr.hasMoreElements()) {
		Object[] tempObj = (Object[]) qr.nextElement();
		KETDqmHeader ketDqmHeader = (KETDqmHeader) tempObj[0];
		headerOid = CommonUtil.getOIDString(ketDqmHeader);
	    }

	    url = "/plm/ext/dqm/dqmMainForm.do?type=action&oid=" + headerOid;

	} else if (target instanceof KETSamplePart) {
	    KETSamplePart ketSamplePart = (KETSamplePart) target;

	    QueryResult qrLink = PersistenceHelper.manager.navigate(ketSamplePart, "request", KETSamplePartLink.class);

	    // QueryResult qrLink = PersistenceHelper.manager.navigate(ketSamplePart, "request", KETSamplePartLink.class, false);

	    while (qrLink.hasMoreElements()) {
		KETSampleRequest ketSampleRequest = (KETSampleRequest) qrLink.nextElement();
		String oid = CommonUtil.getOIDString(ketSampleRequest);
		url = "/plm/ext/sample/sampleRequstMainForm.do?viewType=partTODO&oid=" + oid;
	    }

	} else if (target instanceof KETDqmTodoAtivity) {
	    KETDqmTodoAtivity ketDqmTodoAtivity = (KETDqmTodoAtivity) target;

	    KETDqmHeader ketDqmHeader = ketDqmTodoAtivity.getHeader();

	    String oid = CommonUtil.getOIDString(ketDqmHeader);

	    if (ketDqmTodoAtivity.getTaskCode().equals("REVIEW_USER_CHOISE")) {
		url = "/plm/ext/dqm/dqmMainForm.do?type=REVIEW_USER_CHOISE&oid=" + oid;
	    } else if (ketDqmTodoAtivity.getTaskCode().equals("DQM_ACTION")) {
		url = "/plm/ext/dqm/dqmMainForm.do?type=DQM_ACTION&oid=" + oid;
	    } else if (ketDqmTodoAtivity.getTaskCode().equals("DQM_CLOSE")) {
		url = "/plm/ext/dqm/dqmMainForm.do?type=DQM_CLOSE&oid=" + oid;
	    } else {
		url = "/plm/ext/dqm/dqmMainForm.do?type=view&oid=" + oid;
	    }

	}

	return url;
    }
}
