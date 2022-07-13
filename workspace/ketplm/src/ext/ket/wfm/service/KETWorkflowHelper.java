package ext.ket.wfm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.codec.net.URLCodec;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.ecm.entity.KETCompetentDepartment;
import e3ps.ecm.entity.KETMeetingMinutes;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETMoldChangeRequest;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdChangeRequest;
import e3ps.ews.entity.KETEarlyWarning;
import e3ps.ews.entity.KETEarlyWarningEnd;
import e3ps.ews.entity.KETEarlyWarningEndReq;
import e3ps.ews.entity.KETEarlyWarningPlan;
import e3ps.ews.entity.KETEarlyWarningResult;
import e3ps.project.E3PSProject;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.util.WFMProperties;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.entity.KETAnalysisRequestInfo;
import ext.ket.arm.entity.KETAnalysisRequestMaster;
import ext.ket.arm.service.KETAnalysisInfoHelper;
import ext.ket.cost.entity.CostReport;
import ext.ket.dqm.entity.KETDqmAction;
import ext.ket.dqm.entity.KETDqmRaise;
import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.project.gate.entity.AssessSheet;
import ext.ket.project.gate.entity.GateAssessResult;
import ext.ket.project.trycondition.entity.KETTryMold;
import ext.ket.project.trycondition.entity.KETTryPress;
import ext.ket.sales.entity.KETSalesCSMeetingApproval;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.sample.entity.KETSamplePart;
import ext.ket.sample.entity.KETSamplePartLink;
import ext.ket.sample.entity.KETSampleRequest;
import ext.ket.shared.util.ComparatorUtil;
import ext.ket.wfm.entity.KETWfmApprovalHistoryDTO;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPart;
import wt.services.ServiceFactory;
import wt.workflow.work.WorkItem;

/**
 * @클래스명 : WorkflowHelper
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class KETWorkflowHelper {

    public static final KETWorkflowService service = ServiceFactory.getService(KETWorkflowService.class);

    public static KETWorkflowHelper manager = new KETWorkflowHelper();

    /**
     * @param appMaster
     * @return
     * @throws Exception
     * @메소드명 : getRecipientHistory
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public ArrayList<KETWfmApprovalHistory> getRecipientHistory(KETWfmApprovalMaster appMaster) throws Exception {

	ArrayList<KETWfmApprovalHistory> recipientArr = null;
	HashMap<String, String> checkMap = new HashMap<String, String>();
	if (appMaster != null) {
	    recipientArr = new ArrayList<KETWfmApprovalHistory>();
	    @SuppressWarnings("unchecked")
	    Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(appMaster);
	    Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	    for (KETWfmApprovalHistory history : lineVec) {
		if (KETWfmApprovalHistoryDTO.RECEIVER.equals(history.getActivityName())) {
		    String userid = history.getOwner().getName();
		    if (!checkMap.containsKey(userid)) {
			if (!KETWfmApprovalHistoryDTO.DELEGATE.equals(history.getDecision())) {
			    checkMap.put(userid, userid);
			    recipientArr.add(history);
			}
		    }
		}
	    }
	}
	return recipientArr;
    }

    /**
     * @param appMaster
     * @return
     * @throws Exception
     * @메소드명 : getReferenceHistory
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public ArrayList<KETWfmApprovalHistory> getReferenceHistory(KETWfmApprovalMaster appMaster) throws Exception {

	ArrayList<KETWfmApprovalHistory> referenceArr = null;
	HashMap<String, String> checkMap = new HashMap<String, String>();
	if (appMaster != null) {
	    referenceArr = new ArrayList<KETWfmApprovalHistory>();
	    @SuppressWarnings("unchecked")
	    Vector<KETWfmApprovalHistory> lineVec = WorkflowSearchHelper.manager.getApprovalHistory(appMaster);
	    Collections.sort(lineVec, ComparatorUtil.APPROVALLINESORT);
	    for (KETWfmApprovalHistory history : lineVec) {
		if (KETWfmApprovalHistoryDTO.REFERENCE.equals(history.getActivityName())) {
		    String userid = history.getOwner().getName();
		    if (!checkMap.containsKey(userid)) {
			checkMap.put(userid, userid);
			referenceArr.add(history);
		    }
		}
	    }
	}
	return referenceArr;
    }

    /**
     * 결재대기함 > 결재수행화면 > 결재 대상 IFRAME URL
     * 
     * @param pbo
     * @return
     * @throws Exception
     * @메소드명 : getPboViewUrl
     * @작성자 : Jason, Han
     * @작성일 : 2014. 9. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public String getPboViewUrl(WorkItem item) throws Exception {

	Persistable pbo = item.getPrimaryBusinessObject().getObject();
	String pboViewUrl = "";
	if (pbo instanceof KETProjectDocument) {

	    pboViewUrl = "/plm/jsp/dms/ViewDocument.jsp?isIframe=true&isPop=Y&buttenView=F&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETTechnicalDocument) {

	    pboViewUrl = "/plm/jsp/dms/ViewTechDocument.jsp?isIframe=true&isPop=Y&buttenView=F&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETDevelopmentRequest) {

	    pboViewUrl = "/plm/jsp/dms/ViewDevRequest.jsp?isIframe=true&isPop=Y&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETMoldChangeOrder) {

	    pboViewUrl = "/plm/servlet/e3ps/MoldEcoServlet?isIframe=true&cmd=view&prePage=Search&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETMoldChangeRequest) {

	    String detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + CommonUtil.getOIDString(pbo);
	    URLCodec urlCodec = new URLCodec();
	    pboViewUrl = "/plm/jsp/ecm/reform/ViewEcrForm.jsp?isIframe=true&tabId=1&redirectURL=" + urlCodec.encode(detailInfoUrl);

	} else if (pbo instanceof KETProdChangeOrder) {

	    pboViewUrl = "/plm/servlet/e3ps/ProdEcoServlet?isIframe=true&prePage=S&cmd=View&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETProdChangeRequest) {

	    String detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?isIframe=true&prePage=Search&cmd=View&oid="
		    + CommonUtil.getOIDString(pbo);
	    URLCodec urlCodec = new URLCodec();
	    pboViewUrl = "/plm/jsp/ecm/reform/ViewEcrForm.jsp?isIframe=true&tabId=1&redirectURL=" + urlCodec.encode(detailInfoUrl);

	} else if (pbo instanceof KETCompetentDepartment) {

	    String ecrOid = "";
	    String detailInfoUrl = "";
	    KETCompetentDepartment competent = (e3ps.ecm.entity.KETCompetentDepartment) pbo;
	    QueryResult qr = PersistenceHelper.manager.navigate(competent, "ecr", e3ps.ecm.entity.KETEcrCompetentLink.class, false);
	    while (qr.hasMoreElements()) {
		e3ps.ecm.entity.KETEcrCompetentLink link = (e3ps.ecm.entity.KETEcrCompetentLink) qr.nextElement();
		pbo = link.getEcr();
		ecrOid = CommonUtil.getOIDString(pbo);
		if (pbo instanceof e3ps.ecm.entity.KETProdChangeRequest) {
		    detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?isIframe=true&prePage=Search&cmd=View&oid=" + ecrOid; //
		} else {
		    detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?cmd=view&oid=" + ecrOid;
		}

	    }
	    URLCodec urlCodec = new URLCodec();
	    pboViewUrl = "/plm/jsp/ecm/reform/ViewEcrForm.jsp?isIframe=true&tabId=2&redirectURL=" + urlCodec.encode(detailInfoUrl);

	} else if (pbo instanceof KETMeetingMinutes) {

	    String ecrOid = "";
	    String detailInfoUrl = "";
	    KETMeetingMinutes meeting = (e3ps.ecm.entity.KETMeetingMinutes) pbo;
	    QueryResult qr = PersistenceHelper.manager.navigate(meeting, "ecr", e3ps.ecm.entity.KETEcrMeetingLink.class, false);
	    while (qr.hasMoreElements()) {
		e3ps.ecm.entity.KETEcrMeetingLink link = (e3ps.ecm.entity.KETEcrMeetingLink) qr.nextElement();
		pbo = link.getEcr();
		ecrOid = CommonUtil.getOIDString(pbo);
		if (pbo instanceof e3ps.ecm.entity.KETProdChangeRequest) {
		    detailInfoUrl = "/plm/servlet/e3ps/ProdEcrServlet?isIframe=true&prePage=Search&cmd=View&oid=" + ecrOid; //
		} else {
		    detailInfoUrl = "/plm/servlet/e3ps/MoldEcrServlet?isIframe=true&cmd=view&oid=" + ecrOid;
		}
	    }
	    URLCodec urlCodec = new URLCodec();
	    pboViewUrl = "/plm/jsp/ecm/reform/ViewEcrForm.jsp?isIframe=true&tabId=3&redirectURL=" + urlCodec.encode(detailInfoUrl);

	} else if (pbo instanceof EPMDocument) {

	    pboViewUrl = "/plm/jsp/edm/ViewEPMDocument.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETWfmMultiApprovalRequest) {

	    pboViewUrl = "/plm/jsp/wfm/ViewMultiApproval.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETEarlyWarning) {

	    pboViewUrl = "/plm/jsp/ews/ViewEarlyWarning.jsp?isIframe=true&isPopup=Y&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETEarlyWarningPlan) {

	    pboViewUrl = "/plm/jsp/ews/ViewEarlyWarningPlan.jsp?isIframe=true&isPopup=Y&planOid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETEarlyWarningResult) {

	    pboViewUrl = "/plm/jsp/ews/ViewEarlyWarningResult.jsp?isIframe=true&isPopup=Y&resultOid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETEarlyWarningEndReq) {

	    // KETEarlyWarningEndReq ketEarlyWarningEndReq = (KETEarlyWarningEndReq) pbo;
	    // QueryResult isEnd = PersistenceHelper.navigate((WTDocumentMaster) ketEarlyWarningEndReq.getMaster(),
	    // KETEndReqLink.ROLE_BOBJECT_ROLE, KETEndReqLink.class, false);
	    // KETEndReqLink ketEndReqLink = null;
	    // Object endMaster = null;
	    // ObjectData endMasterData = null;
	    // String endOid = "";
	    // if (isEnd != null && isEnd.size() != 0) {
	    // while (isEnd.hasMoreElements()) {
	    // ketEndReqLink = (KETEndReqLink) isEnd.nextElement();
	    // endMaster = (Object) ObjectUtil.getLatestObject((WTDocumentMaster) ketEndReqLink.getRoleBObject());
	    // endMasterData = new ObjectData((WTDocument) endMaster);
	    // }
	    // endOid = endMasterData.getOid();
	    // }
	    pboViewUrl = "/plm/jsp/ews/ViewEarlyWarningEndReq.jsp?endReqOid=" + CommonUtil.getOIDString(pbo) + "&todo=&isPopup=";
	    // pboViewUrl = "http://plmapdev.ket.com/plm/jsp/ews/ViewEarlyWarningEndReq.jsp?endReqOid=" + CommonUtil.getOIDString(pbo) +
	    // "&endOid=" + endOid + "&todo=&isPopup=";

	} else if (pbo instanceof KETEarlyWarningEnd) {

	    e3ps.ews.entity.KETEarlyWarningEnd ketEarlyWarningEnd = (e3ps.ews.entity.KETEarlyWarningEnd) pbo;
	    QueryResult isEndReq = PersistenceHelper.navigate((wt.doc.WTDocumentMaster) ketEarlyWarningEnd.getMaster(),
		    e3ps.ews.entity.KETEndReqLink.ROLE_AOBJECT_ROLE, e3ps.ews.entity.KETEndReqLink.class, false);
	    e3ps.ews.entity.KETEndReqLink ketEndReqLink = null;
	    Object endReqMaster = null;
	    e3ps.common.obj.ObjectData endReqMasterData = null;

	    while (isEndReq.hasMoreElements()) {
		ketEndReqLink = (e3ps.ews.entity.KETEndReqLink) isEndReq.nextElement();
		endReqMaster = (Object) e3ps.common.obj.ObjectUtil
		        .getLatestObject((wt.doc.WTDocumentMaster) ketEndReqLink.getRoleAObject());
		endReqMasterData = new e3ps.common.obj.ObjectData((wt.doc.WTDocument) endReqMaster);
	    }

	    String endReqOid = endReqMasterData.getOid();

	    pboViewUrl = "/plm/jsp/ews/ViewEarlyWarningEnd.jsp?isIframe=true&isPopup=Y&endReqOid=" + endReqOid;
	} else if (pbo instanceof WTPart) {

	    pboViewUrl = "/plm/jsp/bom/ViewPart.jsp?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof E3PSProject) {

	    pboViewUrl = "/plm/ext/wfm/workflow/projectObjectPBOForm.do?workItemoid=" + CommonUtil.getOIDString(item);

	} else if (pbo instanceof KETDrawingDistRequest) {

	    pboViewUrl = "/plm/ext/edm/drawingDistRequestViewForm.do?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof AssessSheet) {

	    pboViewUrl = "/plm/ext/wfm/workflow/projectObjectPBOForm.do?workItemoid=" + CommonUtil.getOIDString(item);

	} else if (pbo instanceof GateAssessResult) {

	    pboViewUrl = "/plm/ext/wfm/workflow/projectObjectPBOForm.do?workItemoid=" + CommonUtil.getOIDString(item);

	} else if (pbo instanceof KETDqmRaise) {

	    pboViewUrl = "/plm/ext/dqm/dqmMainForm.do?type=raise&isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETDqmAction) {

	    pboViewUrl = "/plm/ext/dqm/dqmMainForm.do?type=action&isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETSampleRequest) {

	    pboViewUrl = "/plm/ext/sample/sampleRequstMainForm.do?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETSamplePart) {

	    KETSamplePart ketSamplePart = (KETSamplePart) pbo;
	    QueryResult qrLink = PersistenceHelper.manager.navigate(ketSamplePart, "request", KETSamplePartLink.class);
	    while (qrLink.hasMoreElements()) {
		KETSampleRequest ketSampleRequest = (KETSampleRequest) qrLink.nextElement();
		String oid = CommonUtil.getOIDString(ketSampleRequest);
		pboViewUrl = "/plm/ext/sample/sampleRequstMainForm.do?viewType=partTODO&isIframe=true&oid=" + oid;
		break;
	    }
	} else if (pbo instanceof KETTryMold) {

	    pboViewUrl = "/plm/ext/project/trycondition/viewTryMoldForm.do?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETTryPress) {

	    pboViewUrl = "/plm/ext/project/trycondition/viewTryPressForm.do?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);

	} else if (pbo instanceof KETAnalysisRequestMaster) {
	    pboViewUrl = "/plm/ext/arm/master/armMasterViewForm?isIframe=true&oid=" + CommonUtil.getOIDString(pbo);
	} else if (pbo instanceof KETAnalysisRequestInfo) {
	    pboViewUrl = "/plm/ext/arm/info/armInfoViewForm?isIframe=true&oid="
		    + KETAnalysisInfoHelper.service.getAnalysisMasterOid(CommonUtil.getOIDString(pbo));
	} else if (pbo instanceof KETSalesProject) {
	    pboViewUrl = "/plm/ext/sales/project/viewSalesProjectForm?oid=" + CommonUtil.getOIDString(pbo);
	} else if (pbo instanceof KETSalesCSMeetingApproval) {
	    pboViewUrl = "/plm/ext/sales/project/viewSalesCSForm?oid=" + CommonUtil.getOIDString(pbo);
	} else if (pbo instanceof CostReport) {
	    CostReport report = (CostReport) pbo;
	    pboViewUrl = "/plm/ext/cost/costReportPopup?taskOid=" + CommonUtil.getOIDString(report.getTask());
	} else if (pbo instanceof KETInvestMaster) {
	    KETInvestMaster im = (KETInvestMaster) pbo;
	    pboViewUrl = "/plm/ext/invest/viewInvestPopup?oid=" + CommonUtil.getOIDString(im);
	}

	return pboViewUrl;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    static Map sortByValue(Map map) {
	List list = new LinkedList(map.entrySet());
	Collections.sort(list, new Comparator() {
	    public int compare(Object o1, Object o2) {
		return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
	    }
	});
	Map result = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
	    Map.Entry entry = (Map.Entry) it.next();
	    result.put(entry.getKey(), entry.getValue());
	}
	return result;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getTypeList() throws Exception {

	Map<String, String> map = new HashMap<String, String>();
	WFMProperties properties = WFMProperties.getInstance();
	map.put("KETBomHeader", properties.getString("wfm.type.bom"));
	map.put("KETProjectDocument", properties.getString("wfm.type.docproj"));
	map.put("KETTechnicalDocument", properties.getString("wfm.type.techdoc"));
	map.put("KETDevelopmentRequest", properties.getString("wfm.type.devreqdoc"));
	// map.put("wfm.type.devreqdoc.R", properties.getString("wfm.type.devreqdoc.R"));
	// map.put("wfm.type.devreqdoc.D", properties.getString("wfm.type.devreqdoc.D"));
	map.put("KETMoldChangeOrder", properties.getString("wfm.type.moldco"));
	map.put("KETMoldChangeRequest", properties.getString("wfm.type.moldcr"));
	map.put("KETMoldChangeActivity", properties.getString("wfm.type.moldca"));
	map.put("KETProdChangeOrder", properties.getString("wfm.type.prodco"));
	map.put("KETProdChangeRequest", properties.getString("wfm.type.prodcr"));
	map.put("KETProdChangeActivity", properties.getString("wfm.type.prodca"));
	map.put("KETCompetentDepartment", properties.getString("wfm.type.ecr.competent"));
	map.put("KETMeetingMinutes", properties.getString("wfm.type.ecr.meeting"));
	map.put("EPMDocument", properties.getString("wfm.type.epmdoc"));
	map.put("KETWfmMultiApprovalRequest", properties.getString("wfm.type.multiepm"));
	map.put("KETEarlyWarning", properties.getString("wfm.type.ew"));
	map.put("KETEarlyWarningPlan", properties.getString("wfm.type.ewplan"));
	map.put("KETEarlyWarningResult", properties.getString("wfm.type.ewresult"));
	map.put("KETEarlyWarningEndReq", properties.getString("wfm.type.ewendreq"));
	map.put("KETEarlyWarningEnd", properties.getString("wfm.type.ewend"));
	map.put("WTPart", properties.getString("wfm.type.part"));
	map.put("ReviewProject", properties.getString("wfm.type.reviewproject"));
	map.put("ProductProject", properties.getString("wfm.type.productproject"));
	map.put("MoldProject", properties.getString("wfm.type.moldproject"));
	map.put("Performance", properties.getString("wfm.type.performance"));
	map.put("WTDocument", properties.getString("wfm.type.wtdoc"));
	map.put("KETDqmTodoAtivity", properties.getString("wfm.type.dqm"));
	map.put("KETDqmRaise", properties.getString("wfm.type.dqmraise"));
	map.put("KETDqmAction", properties.getString("wfm.type.dqmaction"));
	map.put("KETDrawingDistRequest", properties.getString("wfm.type.drawingdistrequset"));
	map.put("KETSampleRequest,KETSamplePart", properties.getString("wfm.type.samplerequest"));
	map.put("AssessSheet", properties.getString("wfm.type.assessRequest"));
	map.put("GateAssessResult", properties.getString("wfm.type.assRsltRequest"));
	map.put("KETTryMold,KETTryPress", properties.getString("wfm.type.tryCondition"));
	map.put("KETAnalysisRequestMaster", properties.getString("wfm.type.analysis"));
	map.put("KETAnalysisRequestInfo", properties.getString("wfm.type.analysisInfo"));
	map.put("CostReport", "개발원가보고서");
	
	return sortByValue(map);
    }

    /**
     * 설계변경활동 재작업 시 반려이력을 삭제하기 위해 확인하는 메소드
     * 
     * @param master
     * @return
     * @throws Exception
     * @메소드명 : hasRejectHistory
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 31.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public boolean hasRejectHistory(KETWfmApprovalMaster master) throws Exception {

	boolean flag = false;
	@SuppressWarnings("unchecked")
	Vector<KETWfmApprovalHistory> hisVec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	String reject_p = WFMProperties.getInstance().getString("wfm.reject");
	Collections.sort(hisVec, ComparatorUtil.APPROVALLINESORT);
	for (KETWfmApprovalHistory his : hisVec) {
	    if (StringUtil.checkNull(his.getDecision()).equals(reject_p))
		flag = true;
	}

	return flag;// && (master.getPbo() instanceof KETProdChangeOrder || master.getPbo() instanceof KETMoldChangeOrder);
    }
}
