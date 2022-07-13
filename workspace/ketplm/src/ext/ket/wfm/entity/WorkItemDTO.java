package ext.ket.wfm.entity;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import wt.doc.WTDocument;
import wt.fc.Persistable;
import wt.org.WTUser;
import wt.workflow.definer.WfProcessTemplate;
import wt.workflow.definer.WfTemplateObjectReference;
import wt.workflow.engine.WfProcess;
import wt.workflow.work.WfAssignedActivity;
import wt.workflow.work.WorkItem;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.ecm.entity.KETMoldChangeActivity;
import e3ps.ecm.entity.KETProdChangeActivity;
import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProjectHelper;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.entity.KETWfmMultiApprovalRequest;
import e3ps.wfm.util.ClassifyPBOUtil;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.cost.entity.CostReport;
import ext.ket.invest.entity.KETInvestMaster;
import ext.ket.invest.entity.KETInvestTask;
import ext.ket.invest.util.InvestUtil;
import ext.ket.issue.entity.KETGeneralissueLink;
import ext.ket.issue.entity.KETIssueMaster;
import ext.ket.issue.entity.KETIssueTask;
import ext.ket.issue.util.IssueUtil;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.wfm.service.KETWorkflowHelper;

/**
 * @클래스명 : WorkItemDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
/**
 * @클래스명 : WorkItemDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 8. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class WorkItemDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 2216759113715663390L;

    private String taskType;
    private String taskName;
    private String title;
    private String status;
    private String creator;
    private String createDate;
    private String arriveDate;
    private String viewTaskUrl;
    private String pboOid;
    private String masterOid;
    private String predate;
    private String postdate;
    private String version;
    private String cancelApproval;
    private boolean multiApproval = false;
    private boolean todo = false;
    private String receiptDate;
    private String delegateHistory;
    private Integer queryLimit;
    private String filterClass;
    private String pboViewUrl;
    private String searchType;
    private String targetCnt;
    private String costReportOid;
    KETMessageService messageService = KETMessageService.getMessageService();

    public WorkItemDTO() {
    }

    /**
     * @param im
     * @throws Exception
     */
    public WorkItemDTO(KETIssueMaster im) throws Exception {

	this.setOid(CommonUtil.getOIDString(im));
	this.taskType = "요구사항관리";

	WTUser manager = im.getManager();
	this.creator = manager.getFullName();
	this.createDate = DateUtil.getDateString(im.getCreateTimestamp(), "d");
	if (IssueUtil.INWORK.equals(im.getIssueState())) {
	    this.taskName = "요청서 작성";
	    this.arriveDate = DateUtil.getDateString(im.getCreateTimestamp(), "d");

	} else {

	    this.taskName = "요구사항 완료";

	    List<KETIssueTask> itList = IssueUtil.manager.getIssueTaskList(im);

	    long tempTime = 0;
	    for (KETIssueTask it : itList) {
		if (IssueUtil.REJECT.equals(it.getIssueState())) {
		    this.taskName = "반려 재요청";
		    this.arriveDate = DateUtil.getDateString(it.getModifyTimestamp(), "d");
		    break;
		}
		if (IssueUtil.COMPLETE.equals(it.getIssueState())) {

		    long compTime = it.getCompleteDate().getTime();

		    if (compTime > tempTime) {
			this.arriveDate = DateUtil.getDateString(it.getCompleteDate(), "d");
			tempTime = compTime;
		    }
		}
	    }

	}

	KETGeneralissueLink link = IssueUtil.manager.getPBOLink(im);

	this.title = im.getReqName();
	if (link != null) {
	    this.title += " [" + StringUtil.checkNull(link.getPboName()) + "]";
	    this.pboOid = CommonUtil.getOIDString(link.getPbo());
	}
    }

    /**
     * @param it
     * @throws Exception
     */
    public WorkItemDTO(KETIssueTask it) throws Exception {
	this.setOid(CommonUtil.getOIDString(it));
	this.taskType = "요구사항관리 ";

	KETIssueMaster im = it.getIssueMaster();
	WTUser manager = im.getManager();
	this.creator = manager.getFullName();

	this.taskName = "진행현황 등록";
	this.title = it.getSubject();
	this.arriveDate = DateUtil.getDateString(it.getProgressDate(), "d");

	KETGeneralissueLink link = IssueUtil.manager.getPBOLink(im);
	if (link != null) {
	    this.title += " [" + StringUtil.checkNull(link.getPboName()) + "]";
	    this.pboOid = CommonUtil.getOIDString(link.getPbo());
	}
    }

    /**
     * @param im
     * @throws Exception
     */
    public WorkItemDTO(KETInvestMaster im) throws Exception {

	this.setOid(CommonUtil.getOIDString(im));
	this.taskType = "고객투자비 회수관리";

	WTUser manager = im.getManager();
	this.creator = manager.getFullName();
	this.createDate = DateUtil.getDateString(im.getCreateTimestamp(), "d");
	if (InvestUtil.REGDIT.equals(im.getInvestState())) {
	    this.creator = im.getCreatorFullName();
	    this.taskName = "이관요청";
	    this.arriveDate = DateUtil.getDateString(im.getCreateTimestamp(), "d");

	} else if (InvestUtil.INWORK.equals(im.getInvestState())) {
	    this.creator = im.getCreatorFullName();
	    this.taskName = "접수요청";
	    this.arriveDate = DateUtil.getDateString(im.getCreateTimestamp(), "d");

	} else if (InvestUtil.INPROGRESS.equals(im.getInvestState())) {
	    this.creator = im.getCreatorFullName();
	    this.taskName = "자료취합 중";
	    this.arriveDate = DateUtil.getDateString(im.getCreateTimestamp(), "d");
	    List<KETInvestTask> list = InvestUtil.manager.getInvestTaskList(im, "BTYPE");
	    boolean isCompleted = true;
	    for (KETInvestTask task : list) {
		if (!InvestUtil.COMPLETE.equals(task.getInvestState())) {
		    isCompleted = false;
		    break;
		}
	    }
	    if (isCompleted) {
		this.taskName = "자료취합 완료";
	    }
	} else {

	    this.taskName = "투자비회수 진행";

	    List<KETInvestTask> itList = InvestUtil.manager.getInvestTaskList(im, "BTYPE");

	    long tempTime = 0;
	    for (KETInvestTask it : itList) {
		if (InvestUtil.COMPLETE.equals(it.getInvestState())) {

		    long compTime = it.getCompleteDate().getTime();

		    if (compTime > tempTime) {
			this.arriveDate = DateUtil.getDateString(it.getCompleteDate(), "d");
			tempTime = compTime;
		    }
		}
	    }
	}

	this.title = im.getReqName();
    }

    /**
     * @param it
     * @throws Exception
     */
    public WorkItemDTO(KETInvestTask it) throws Exception {
	this.setOid(CommonUtil.getOIDString(it));
	this.taskType = "고객투자비 회수관리";

	KETInvestMaster im = it.getInvestMaster();
	WTUser manager = im.getManager();
	this.creator = manager.getFullName();

	this.taskName = "증빙자료 회신";
	this.title = it.getSubject() + " [" + im.getReqName() + "]";
	this.arriveDate = DateUtil.getDateString(it.getProgressDate(), "d");

    }

    public WorkItemDTO(WorkItem workItem) throws Exception {

	String oid = CommonUtil.getOIDString(workItem);
	String retUrl = "#";
	Object pbo = null;
	try {
	    pbo = workItem.getPrimaryBusinessObject().getObject();
	} catch (Exception e) {
	    throw e;
	}
	if (pbo != null) {
	    Hashtable<String, Object> hashtable = ClassifyPBOUtil.extractPBOInfo(pbo);
	    String title = hashtable.get("title").toString();
	    String creator = hashtable.get("creator").toString();
	    String type = hashtable.get("type").toString();
	    String status = hashtable.get("state").toString();
	    status = status.replaceAll("&nbsp;", "");
	    WfAssignedActivity activity = (WfAssignedActivity) workItem.getSource().getObject();
	    KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
	    WTUser lastApprover = (appMaster == null) ? WorkflowSearchHelper.manager.getLastUser((Persistable) pbo)
		    : WorkflowSearchHelper.manager.getLastUser(appMaster);
	    String step;
	    if (pbo instanceof KETDevelopmentRequest) {
		KETDevelopmentRequest devReq = (KETDevelopmentRequest) pbo;
		step = devReq.getDevelopmentStep();
	    } else {
		step = "";
	    }
	    String taskName;
	    if (activity.getName().equals("수행담당자")) {
		taskName = hashtable.get("task").toString();
		retUrl = ClassifyPBOUtil.getTaskUrl(pbo);
	    } else {
		if ("R".equals(step) && "개발검토의뢰문서".equals(type)) {
		    taskName = "개발검토의뢰";
		} else if ("D".equals(step) && "개발검토의뢰문서".equals(type)) {
		    taskName = "개발의뢰" + activity.getName();
		} else {
		    taskName = activity.getName();
		}
	    }
	    if (lastApprover != null) {
		String ownerID = workItem.getOwnership().getOwner().getName();
		if (ownerID.equals(lastApprover.getName())) {
		    taskName = "승인";
		}
	    }
	    // 추가 된 부분 기안자랑 다른 부분 수정
	    People apUser = new People();
	    String appUser = "&nbsp";
	    if (appMaster != null) {
		apUser = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());
		if (apUser != null) {
		    appUser = apUser.getName();
		}
	    }

	    String viewTaskUrl;
	    if (taskName.equals("담당자지정")) {
		viewTaskUrl = "/plm/jsp/project/Wf_Assign.jsp?oid=" + workItem.toString();
		setTodo(true);
	    } else if (activity.getName().equals("수행담당자")) {
		viewTaskUrl = retUrl;
		setTodo(true);
	    } else {
		viewTaskUrl = "/plm/jsp/wfm/ReviewTask.jsp?oid=" + workItem.toString();
	    }

	    String receiptDate = "";

	    // 현재 사용되고 있지 않아 주석처리함 주석 풀면 시간 엄청 걸림 2010.02.07
	    // receiptDate = KETWorkspaceHelper.service.getReceiptDate((LifeCycleManaged) pbo);

	    String delegateHistory = "";
	    if (workItem.getOrigOwner().getObject() != null) {
		// QueryResult qr = PersistenceHelper.manager.navigate(workItem, KETDelegateHistoryWorkItemLink.DELEGATE_HOSTORY_ROLE,
		// KETDelegateHistoryWorkItemLink.class, true);
		// if (qr != null && qr.size() > 0) {
		delegateHistory = "<span class=\"b-small box-size\" onclick=\"javascript:viewDelegateHistoryPopup('" + oid
		        + "');\" style=\"cursor: hand;\">" + messageService.getString("e3ps.message.ket_message", "05145")/* 위임이력 */
		        + "</span>";
		// }
	    }

	    // 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	    try {
		WfProcess process = activity.getParentProcess();
		WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
		boolean isCanWF = false;
		if (template != null)
		    isCanWF = "KET_CAN_WF_NEW".equals(template.getName());
		if (isCanWF) {
		    type = type + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		}
	    } catch (Exception e) {
	    }

	    if (pbo instanceof ProductProject) {

		ProductProject project = (ProductProject) pbo;

		if (ProjectHelper.isStopProject(project)) {
		    type = type + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		}
	    }

	    String pbooid = pbo.toString();
	    setOid(oid);
	    setTaskType(type);
	    setTaskName(taskName);
	    setTitle(title);
	    setStatus(status);
	    setCreator(((appMaster != null) ? appUser : creator));
	    setArriveDate(DateUtil.getDateString(workItem.getPersistInfo().getCreateStamp(), "d"));
	    setViewTaskUrl(viewTaskUrl);
	    setPboOid(pbooid);
	    setMasterOid(CommonUtil.getOIDString(appMaster));
	    setReceiptDate(receiptDate);
	    setDelegateHistory(delegateHistory);

	    if (pbo instanceof CostReport) {
		CostReport report = (CostReport) pbo;
		String taskOid = CommonUtil.getOIDString(report.getTask());
		setCostReportOid(taskOid);

	    }

	}
    }

    public WorkItemDTO(HashMap<String, Object> map) throws Exception {

	Persistable pbo = (Persistable) map.get("pbo");
	String prOid = pbo.toString();
	String classStr = map.get("className").toString();
	String time = map.get("createTime").toString();
	String title = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pbo).get("title").toString());
	String state = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pbo).get("state").toString());
	state = state.replaceAll("&nbsp;", "");
	String type = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pbo).get("type").toString());
	String creator = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pbo).get("creator").toString());
	String createDate = StringUtil.checkNull(ClassifyPBOUtil.extractPBOInfo(pbo).get("ctime").toString());
	String reqDate;
	if (classStr.equals("wt.workflow.work.WorkItem")) {
	    reqDate = time.substring(0, 10);
	    setOid(prOid);
	} else {
	    reqDate = "";
	}

	if (pbo instanceof KETMoldChangeActivity) {
	    KETMoldChangeActivity moldCA = (KETMoldChangeActivity) pbo;
	    prOid = moldCA.getMoldECO().toString();
	} else if (pbo instanceof KETProdChangeActivity) {
	    KETProdChangeActivity prodCA = (KETProdChangeActivity) pbo;
	    prOid = prodCA.getProdECO().toString();
	}

	// 추가 된 부분 기안자랑 다른 부분 수정
	People apUser = new People();
	String appUser = "&nbsp";
	KETWfmApprovalMaster appMaster = WorkflowSearchHelper.manager.getMaster((Persistable) pbo);
	if (appMaster != null) {
	    apUser = PeopleHelper.manager.getPeople(appMaster.getOwner().getName());

	    if (apUser != null) {
		appUser = apUser.getName();
	    }
	}
	// 2013.1.14 shkim
	Vector<WTDocument> docVec = null;
	if (pbo instanceof KETWfmMultiApprovalRequest) {
	    KETWfmMultiApprovalRequest multiReq = (KETWfmMultiApprovalRequest) CommonUtil.getObject(prOid);
	    docVec = WorkflowSearchHelper.manager.getDocList(multiReq);
	    setMultiApproval(docVec != null && docVec.size() > 0);
	}
	String cancelApproval = "&nbsp;";
	if (KETWorkflowHelper.service.isCancelApproval(prOid)) {
	    cancelApproval = "<span class=\"b-small box-size\" onclick=\"javascript:WorkItem.doCancelApproval('" + prOid
		    + "');\" style=\"cursor: hand;\">" + messageService.getString("e3ps.message.ket_message", "05106")/* 상신취소 */+ "</span>";
	}

	// 결재유형명에 취소 결재인 경우 (취소) 문구 추가
	try {
	    WfProcess process = WorkflowSearchHelper.manager.getProcess(appMaster);
	    WfProcessTemplate template = (WfProcessTemplate) process.getTemplate().getObject();
	    boolean isCanWF = false;
	    if (template != null)
		isCanWF = "KET_CAN_WF".equals(template.getName());
	    if (isCanWF) {
		type = type + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
	    }
	} catch (Exception e) {
	}

	if (pbo instanceof ProductProject) {

	    ProductProject project = (ProductProject) pbo;

	    if (ProjectHelper.isStopProject(project)) {
		type = type + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
	    }
	}

	setTaskType(type);
	setTitle(title);
	setStatus(state);
	setPboOid(prOid);
	setCreator((appMaster != null) ? appUser : creator);
	setCreateDate(createDate);
	setArriveDate(reqDate);
	setCancelApproval(cancelApproval);
    }

    public WorkItemDTO(KETWfmApprovalMaster approvalMaster, boolean isCompleted) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	String title = "";
	String state = "";
	String type = "";
	String version = "";
	Persistable target = null;
	if (approvalMaster != null) {
	    try {
		target = approvalMaster.getBusinessobjectRef().getObject();
	    } catch (Exception e) {
		// PersistenceHelper.manager.delete(approvalMaster);
		setTaskType("");
		setTitle("결재 대상이 삭제되었습니다");
		setStatus("");
		setPboOid("");
		setCreator("");
		setCreateDate("");
		setArriveDate("");
		setCancelApproval("");
	    }
	    if (target != null) {
		Hashtable<String, Object> hashtable = ClassifyPBOUtil.extractPBOInfo(target);
		title = StringUtil.checkNull(hashtable.get("title").toString());
		state = StringUtil.checkNull(hashtable.get("state").toString());
		state = state.replaceAll("&nbsp;", "");
		type = StringUtil.checkNull(hashtable.get("type").toString());
		version = StringUtil.checkNull(hashtable.get("version").toString());
		String pbooid = CommonUtil.getOIDString(target);
		String cancelApproval = "&nbsp;";
		if (!isCompleted) {
		    if (KETWorkflowHelper.service.isCancelApproval(pbooid)) {
			cancelApproval = "<span class=\"b-small box-size\" onclick=\"javascript:WorkItem.doCancelApproval('" + pbooid
			        + "');\" style=\"cursor: hand;\">" + messageService.getString("e3ps.message.ket_message", "05106")/* 상신취소 */
			        + "</span>";
		    }
		}

		// 결재유형명에 취소 결재인 경우 (취소) 문구 추가
		WfProcess process = WorkflowSearchHelper.manager.getProcess(approvalMaster);

		try {
		    WfTemplateObjectReference template = (WfTemplateObjectReference) process.getTemplate();
		    boolean isCanWF = false;
		    if (template != null)
			isCanWF = "KET_CAN_WF".equals(template.getName());
		    if (isCanWF) {
			type = type + "(" + messageService.getString("e3ps.message.ket_message", "02887") + ")"; // 취소
		    }
		} catch (Exception e) {
		}

		if (approvalMaster.getPbo() instanceof ProductProject) {

		    ProductProject project = (ProductProject) approvalMaster.getPbo();

		    if (ProjectHelper.isStopProject(project)) {
			type = type + "(" + messageService.getString("e3ps.message.ket_message", "02695") + ")"; // 중지
		    }
		}

		setTaskType(type);
		setTitle(title);
		setStatus(state);
		setVersion(version);
		setPboOid(pbooid);
		setCreator(approvalMaster.getOwner().getFullName());
		setCreateDate(DateUtil.getDateString(target.getPersistInfo().getCreateStamp(), "d"));
		if (isCompleted)
		    setArriveDate(DateUtil.getDateString(approvalMaster.getCompletedDate(), "d"));
		else
		    setArriveDate(DateUtil.getDateString(approvalMaster.getPersistInfo().getCreateStamp(), "d"));
		setCancelApproval(cancelApproval);

		if (target instanceof CostReport) {

		    CostReport report = (CostReport) target;
		    String taskOid = CommonUtil.getOIDString(report.getTask());
		    setCostReportOid(taskOid);

		}
	    }
	}
    }

    /*
     * public String getTaskNameHtmlPrefix() { return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
     * } public String getTaskNameHtmlPostfix() { return "</font>"; }
     */
    public String getTitleHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getTitleHtmlPostfix() {
	return "</font>";
    }

    public String getStatusHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getStatusHtmlPostfix() {
	return "</font>";
    }

    /**
     * @return the cancelApproval
     */
    public String getCancelApproval() {
	return cancelApproval;
    }

    /**
     * @param cancelApproval
     *            the cancelApproval to set
     */
    public void setCancelApproval(String cancelApproval) {
	this.cancelApproval = cancelApproval;
    }

    /**
     * @return the taskType
     */
    public String getTaskType() {
	return taskType;
    }

    /**
     * @param taskType
     *            the taskType to set
     */
    public void setTaskType(String taskType) {
	this.taskType = taskType;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
	return taskName;
    }

    /**
     * @param taskName
     *            the taskName to set
     */
    public void setTaskName(String taskName) {
	this.taskName = taskName;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
	this.status = status;
    }

    /**
     * @return the creator
     */
    public String getCreator() {
	return creator;
    }

    /**
     * @param creator
     *            the creator to set
     */
    public void setCreator(String creator) {
	this.creator = creator;
    }

    /**
     * @return the arriveDate
     */
    public String getArriveDate() {
	return arriveDate;
    }

    /**
     * @param arriveDate
     *            the arriveDate to set
     */
    public void setArriveDate(String arriveDate) {
	this.arriveDate = arriveDate;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    /**
     * @return the viewTaskUrl
     */
    public String getViewTaskUrl() {
	return viewTaskUrl;
    }

    /**
     * @param viewTaskUrl
     *            the viewTaskUrl to set
     */
    public void setViewTaskUrl(String viewTaskUrl) {
	this.viewTaskUrl = viewTaskUrl;
    }

    /**
     * @return the pboOid
     */
    public String getPboOid() {
	return pboOid;
    }

    /**
     * @param pboOid
     *            the pboOid to set
     */
    public void setPboOid(String pboOid) {
	this.pboOid = pboOid;
    }

    /**
     * @return the predate
     */
    public String getPredate() {
	return predate;
    }

    /**
     * @param predate
     *            the predate to set
     */
    public void setPredate(String predate) {
	this.predate = predate;
    }

    /**
     * @return the postdate
     */
    public String getPostdate() {
	return postdate;
    }

    /**
     * @param postdate
     *            the postdate to set
     */
    public void setPostdate(String postdate) {
	this.postdate = postdate;
    }

    /**
     * @return the createDate
     */
    public String getCreateDate() {
	return createDate;
    }

    /**
     * @param createDate
     *            the createDate to set
     */
    public void setCreateDate(String createDate) {
	this.createDate = createDate;
    }

    /**
     * @return the multiApproval
     */
    public boolean isMultiApproval() {
	return multiApproval;
    }

    /**
     * @param multiApproval
     *            the multiApproval to set
     */
    public void setMultiApproval(boolean multiApproval) {
	this.multiApproval = multiApproval;
    }

    /**
     * @return the version
     */
    public String getVersion() {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
	this.version = version;
    }

    /**
     * @return the masterOid
     */
    public String getMasterOid() {
	return masterOid;
    }

    /**
     * @param masterOid
     *            the masterOid to set
     */
    public void setMasterOid(String masterOid) {
	this.masterOid = masterOid;
    }

    /**
     * @return the receiptDate
     */
    public String getReceiptDate() {
	return receiptDate;
    }

    /**
     * @param receiptDate
     *            the receiptDate to set
     */
    public void setReceiptDate(String receiptDate) {
	this.receiptDate = receiptDate;
    }

    /**
     * @return the delegateHistory
     */
    public String getDelegateHistory() {
	return delegateHistory;
    }

    /**
     * @param delegateHistory
     *            the delegateHistory to set
     */
    public void setDelegateHistory(String delegateHistory) {
	this.delegateHistory = delegateHistory;
    }

    /**
     * @return the queryLimit
     */
    public Integer getQueryLimit() {
	return queryLimit;
    }

    /**
     * @param queryLimit
     *            the queryLimit to set
     */
    public void setQueryLimit(Integer queryLimit) {
	this.queryLimit = queryLimit;
    }

    /**
     * @return the filterClass
     */
    public String getFilterClass() {
	return filterClass;
    }

    /**
     * @param filterClass
     *            the filterClass to set
     */
    public void setFilterClass(String filterClass) {
	this.filterClass = filterClass;
    }

    /**
     * @return the pboViewUrl
     */
    public String getPboViewUrl() {
	return pboViewUrl;
    }

    /**
     * @param pboViewUrl
     *            the pboViewUrl to set
     */
    public void setPboViewUrl(String pboViewUrl) {
	this.pboViewUrl = pboViewUrl;
    }

    /**
     * @return the searchType
     */
    public String getSearchType() {
	return searchType;
    }

    /**
     * @param searchType
     *            the searchType to set
     */
    public void setSearchType(String searchType) {
	this.searchType = searchType;
    }

    /**
     * @return the todo
     */
    public boolean isTodo() {
	return todo;
    }

    /**
     * @param todo
     *            the todo to set
     */
    public void setTodo(boolean todo) {
	this.todo = todo;
    }

    public String getTargetCnt() {
	return targetCnt;
    }

    public void setTargetCnt(String targetCnt) {
	this.targetCnt = targetCnt;
    }

    public String getCostReportOid() {
	return costReportOid;
    }

    public void setCostReportOid(String costReportOid) {
	this.costReportOid = costReportOid;
    }

}
