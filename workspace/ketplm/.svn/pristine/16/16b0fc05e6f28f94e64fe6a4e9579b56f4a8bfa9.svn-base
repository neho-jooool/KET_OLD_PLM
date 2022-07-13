package ext.ket.arm.entity;

import java.util.Vector;

import wt.fc.WTObject;
import wt.session.SessionHelper;
import e3ps.common.service.KETCommonHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.ParamUtil;
import e3ps.project.StandardE3PSProjectService;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.arm.service.KETAnalysisInfoHelper;
import ext.ket.arm.service.KETAnalysisRequestHelper;
import ext.ket.shared.dto.BaseDTO;

/**
 * KETAnalysisRequest DTO 객체
 * 
 * @클래스명 : KETAnalysisRequestDTO
 * @작성자 : hooni
 * @작성일 : 2014. 10. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class KETAnalysisRequestDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String analysisRequestOid; // 해석의뢰OID
    private String analysisRequestNo; // 해석의뢰번호
    private String projectNo; // project No
    private String projectTitle;
    private String projectOid;
    private String analysisTitle; // 해석의뢰 제목
    private String customerChargeName; // 고객담당자
    private String carType; // 차종
    private String carTypeName; // 차종명 _ 상세화면 용
    private String process; // 개발구분
    private String processName; // 개발구분명 _ 상세화면 용
    private String ketClientUser; // 의뢰자
    private String ketClientUserName; // 의뢰자명 _ 상세화면 용
    private String ketChargeUser; // 담당자
    private String ketChargeUserName; // 담당자 _ 상세화면 용
    private String ketClientDepartment; // 의뢰부서
    private String ketClientDepartmentName; // 의뢰부서 _ 상세화면 용
    private String ketChargeDepartment; // 담당부서
    private String ketChargeDepartmentName; // 담당부서 _ 상세화면 용
    private String customerDepartment; // 고객처
    private String customerDepartmentName; // 고객처명
    private String analysisUse; // 해석용도
    private String analysisUseName; // 해석용도 _ 상세화면 용
    private String analysisDivision; // 해석구분
    private String analysisComment; // 해석의견
    private String analysisDivisionName; // 해석구분명
    private String partNo; // 의뢰부품
    private String analysisObjectComment; // 해석의뢰목적
    private String analysisTargetComment; // 설계목표&설계기준
    private String state; // 결재상태
    private String stateName; // 결재상태명
    private String startDate; // 시작(의뢰)일
    private String endDate; // 완료일
    private boolean createUserFlag; // 작성자여부확인
    private String approvalUserName; // 결재승인자
    private boolean infoStartFlag = false; // 의뢰접수여부확인
    private boolean infoProgressFlag = false;
    private String createStartDate;
    private String createEndDate;
    private String endStartDate;
    private String endEndDate;
    private String className;// 제품구분
    private String popup;
    private String opreationDivision;

    public KETAnalysisRequestDTO() {
    }

    public KETAnalysisRequestDTO(KETAnalysisRequestMaster analysis) throws Exception {
	super.setOid(CommonUtil.getOIDString(analysis));
	this.setAnalysisRequestOid(CommonUtil.getOIDString(analysis)); // 해석의뢰 OID
	this.setAnalysisRequestNo(analysis.getAnalysisRequestNo()); // 해석의뢰 번호
	this.setProjectNo(analysis.getProjectNo()); // project No
	this.setProjectOid(KETAnalysisRequestHelper.service.getProjectOidLink(CommonUtil.getOIDString(analysis)));
	this.setAnalysisTitle(analysis.getAnalysisTitle()); // 해석의뢰 제목
	this.setCustomerChargeName(analysis.getCustomerChargeName()); // 고객담당자
	this.setCarType(CommonUtil.getOIDString(analysis.getCarType())); // 차종
	this.setCarTypeName(analysis.getCarType() != null ? analysis.getCarType().getName() : ""); // 차종명 _ 상세화면 용
	this.setProcess(CommonUtil.getOIDString(analysis.getProcess())); // 개발구분
	this.setProcessName(analysis.getProcess() != null ? analysis.getProcess().getName() : ""); // 개발구분명 _ 상세화면 용
	this.setKetClientUser(CommonUtil.getOIDString(analysis.getKetClientUser())); // 의뢰자
	this.setKetClientUserName(analysis.getKetClientUser().getFullName()); // 의뢰자명 _ 상세화면 용
	this.setKetClientDepartment(CommonUtil.getOIDString(analysis.getKetClientDepartment())); // 의뢰부서
	this.setKetClientDepartmentName(analysis.getKetClientDepartment().getName()); // 의뢰부서명 _ 상세화면 용
	this.setAnalysisUse(CommonUtil.getOIDString(analysis.getUsage())); // 해석용도
	this.setAnalysisUseName(analysis.getUsage().getName()); // 해석용도명 _ 상세화면 용
	this.setPartNo(analysis.getPartNo()); // part No
	this.setAnalysisDivision(KETAnalysisRequestHelper.service.getAnalysisDivisionCodeLink(getAnalysisRequestOid()));
	this.setCustomerDepartment(KETAnalysisRequestHelper.service.getCustomerDeptsCodeLink(getAnalysisRequestOid()));
	this.setState(analysis.getLifeCycleState().toString()); // 결재상태
	this.setStateName(analysis.getLifeCycleState().getDisplay()); // 결재상태Name
	this.setEndDate(KETAnalysisInfoHelper.service.getAnalysisInfoEndDate(getAnalysisRequestOid())); // 완료일
	this.setAnalysisComment(analysis.getEtcComment()); // 해석의견
	this.setAnalysisObjectComment(analysis.getAnalysisObjectComment()); // 해석의뢰목적
	this.setAnalysisTargetComment(analysis.getAnalysisTargetComment()); // 설계목표&설계기준

	if (KETAnalysisRequestHelper.service.getAnalysisMasterClassName(getAnalysisRequestOid()) != null) {
	    this.setClassName(KETAnalysisRequestHelper.service.getAnalysisMasterClassName(getAnalysisRequestOid()));// 제품구분
	}
	// if (analysis.getKetClientUser().getFullName().equals(SessionHelper.manager.getPrincipalReference().getFullName())) {
	// this.setCreateUserFlag(true);
	// } else {
	// this.setCreateUserFlag(false);
	// }

	WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(CommonUtil.getOIDString(analysis)));
	// System.out.println("by hooni ::: DTO1 ::: targetObj" + targetObj);
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	Object temp = new Object();
	Vector vec = null;
	if (master != null) {
	    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);
	    if (vec != null) {
		String activityName = "&nbsp;";
		for (int i = 0; i < vec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토")) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "").equals("승인")) {
			    if (history.getCompletedDate() != null) {
				this.setStartDate(DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd"));
			    }
			}
		    } else if (activityName.equals("의뢰접수")) {
			if ("승인".equals(ParamUtil.checkStrParameter(history.getDecision(), ""))) {
			    if (!"".equals(KETAnalysisInfoHelper.service.getAnalysisInfoOid(getAnalysisRequestOid()))) {
				this.setApprovalUserName(ParamUtil.checkStrParameter(history.getOwner().getFullName(), ""));
				this.setStateName(KETAnalysisInfoHelper.service.getAnalysisInfoStateName(getAnalysisRequestOid()));
				this.setInfoStartFlag(true);
				this.setInfoProgressFlag(true);
			    } else {
				if (SessionHelper.manager.getPrincipalReference().getFullName()
				        .equals(ParamUtil.checkStrParameter(history.getOwner().getFullName(), ""))) {
				    this.setInfoStartFlag(true);
				    this.setInfoProgressFlag(false);
				} else {
				    this.setInfoStartFlag(false);
				    this.setInfoProgressFlag(false);
				}
				this.setApprovalUserName(ParamUtil.checkStrParameter(history.getOwner().getFullName(), ""));
				this.setStateName("의뢰접수");
			    }
			}
		    }
		}
	    }
	}
    }

    public KETAnalysisRequestDTO KETAnalysisRequestDTO(KETAnalysisRequestDTO analysisDTO, KETAnalysisRequestMaster analysis)
	    throws Exception {
	analysisDTO.setAnalysisRequestOid(CommonUtil.getOIDString(analysis)); // 해석의뢰 OID
	analysisDTO.setAnalysisRequestNo(analysis.getAnalysisRequestNo()); // 해석의뢰 번호
	analysisDTO.setProjectNo(analysis.getProjectNo()); // project No
	analysisDTO.setAnalysisTitle(analysis.getAnalysisTitle()); // 해석의뢰 제목
	analysisDTO.setCustomerChargeName(analysis.getCustomerChargeName()); // 고객담당자
	analysisDTO.setCarType(CommonUtil.getOIDString(analysis.getCarType())); // 차종
	analysisDTO.setCarTypeName(analysis.getCarType() != null ? analysis.getCarType().getName() : ""); // 차종명 _ 상세화면 용
	analysisDTO.setProcess(CommonUtil.getOIDString(analysis.getProcess())); // 개발구분
	analysisDTO.setProcessName(analysis.getProcess() != null ? analysis.getProcess().getName() : ""); // 개발구분명 _ 상세화면 용
	analysisDTO.setKetClientUser(CommonUtil.getOIDString(analysis.getKetClientUser())); // 의뢰자
	analysisDTO.setKetClientUserName(analysis.getKetClientUser().getFullName()); // 의뢰자명 _ 상세화면 용
	analysisDTO.setKetClientDepartment(CommonUtil.getOIDString(analysis.getKetClientDepartment())); // 의뢰부서
	analysisDTO.setKetClientDepartmentName(analysis.getKetClientDepartment().getName()); // 의뢰부서명 _ 상세화면 용
	analysisDTO.setState(analysis.getLifeCycleState().toString()); // 결재상태
	analysisDTO.setStateName(analysis.getLifeCycleState().getDisplay()); // 결재상태Name
	analysisDTO.setAnalysisUse(CommonUtil.getOIDString(analysis.getUsage())); // 해석용도
	analysisDTO.setAnalysisUseName(analysis.getUsage().getName()); // 해석용도명 _ 상세화면 용
	analysisDTO.setAnalysisComment(analysis.getEtcComment());// 해석의견
	analysisDTO.setAnalysisObjectComment(analysis.getAnalysisObjectComment()); // 해석의뢰목적
	analysisDTO.setAnalysisTargetComment(analysis.getAnalysisTargetComment()); // 설계목표&설계기준
	analysisDTO.setPartNo(analysis.getPartNo()); // part No
	analysisDTO.setAnalysisDivision(KETAnalysisRequestHelper.service.getAnalysisDivisionCodeLink(getAnalysisRequestOid()));
	analysisDTO.setCustomerDepartment(KETAnalysisRequestHelper.service.getCustomerDeptsCodeLink(getAnalysisRequestOid()));
	analysisDTO.setEndDate(KETAnalysisInfoHelper.service.getAnalysisInfoEndDate(getAnalysisRequestOid())); // 완료일
	analysisDTO.setClassName(KETAnalysisRequestHelper.service.getAnalysisMasterClassName(getAnalysisRequestOid()));// 제품구분

	if (analysis.getKetClientUser().getFullName().equals(SessionHelper.manager.getPrincipalReference().getFullName())) {
	    analysisDTO.setCreateUserFlag(true);
	} else {
	    analysisDTO.setCreateUserFlag(false);
	}

	WTObject targetObj = KETCommonHelper.service.getPBO((WTObject) CommonUtil.getObject(analysisDTO.getOid()));
	// System.out.println("by hooni ::: DTO2 ::: targetObj" + targetObj);
	KETWfmApprovalMaster master = (KETWfmApprovalMaster) WorkflowSearchHelper.manager.getMaster(targetObj);
	Object temp = new Object();
	Vector vec = null;
	if (master != null) {

	    vec = WorkflowSearchHelper.manager.getApprovalHistory(master);

	    if (vec != null) {
		String activityName = "";

		for (int i = 0; i < vec.size(); i++) {
		    KETWfmApprovalHistory history = (KETWfmApprovalHistory) vec.get(i);
		    activityName = StringUtil.checkNull(history.getActivityName());
		    if (activityName.equals("검토")) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "").equals("승인")) {
			    if (history.getCompletedDate() != null) {
				analysisDTO.setStartDate(DateUtil.getTimeFormat(history.getCompletedDate(), "yyyy-MM-dd"));
			    }
			}
		    } else if (activityName.equals("의뢰접수")) {
			if (ParamUtil.checkStrParameter(history.getDecision(), "").equals("승인")) {
			    analysisDTO.setApprovalUserName(ParamUtil.checkStrParameter(history.getOwner().getFullName(), "&nbsp;"));
			    analysisDTO.setStateName("의뢰접수");
			}
		    }

		}
	    }
	}
	return analysisDTO;
    }

    public String getAnalysisRequestOid() {
	return analysisRequestOid;
    }

    public void setAnalysisRequestOid(String analysisRequestOid) {
	this.analysisRequestOid = analysisRequestOid;
    }

    public String getAnalysisRequestNoHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getAnalysisRequestNoHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public String getAnalysisTitleHtmlPrefix() {
	return super.getHtmlPrefix();
    }

    public String getAnalysisTitleHtmlPostfix() {
	return super.getHtmlPostfix();
    }

    public String getAnalysisRequestNo() {
	return analysisRequestNo;
    }

    public void setAnalysisRequestNo(String analysisRequestNo) {
	this.analysisRequestNo = analysisRequestNo;
    }

    public String getProjectNo() {
	return projectNo;
    }

    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    public String getAnalysisTitle() {
	return analysisTitle;
    }

    public void setAnalysisTitle(String analysisTitle) {
	this.analysisTitle = analysisTitle;
    }

    public String getCarType() {
	return carType;
    }

    public void setCarType(String carType) {
	this.carType = carType;
    }

    public String getCustomerChargeName() {
	return customerChargeName;
    }

    public void setCustomerChargeName(String customerChargeName) {
	this.customerChargeName = customerChargeName;
    }

    public String getPartNo() {
	return partNo;
    }

    public String getCustomerDepartment() {
	return customerDepartment;
    }

    public void setCustomerDepartment(String customerDepartment) {
	this.customerDepartment = customerDepartment;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getKetClientUser() {
	return ketClientUser;
    }

    public void setKetClientUser(String ketClientUser) {
	this.ketClientUser = ketClientUser;
    }

    public String getKetChargeUser() {
	return ketChargeUser;
    }

    public void setKetChargeUser(String ketChargeUser) {
	this.ketChargeUser = ketChargeUser;
    }

    public String getKetClientDepartment() {
	return ketClientDepartment;
    }

    public void setKetClientDepartment(String ketClientDepartment) {
	this.ketClientDepartment = ketClientDepartment;
    }

    public String getKetChargeDepartment() {
	return ketChargeDepartment;
    }

    public void setKetChargeDepartment(String ketChargeDepartment) {
	this.ketChargeDepartment = ketChargeDepartment;
    }

    public String getProcess() {
	return process;
    }

    public void setProcess(String process) {
	this.process = process;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getStartDate() {
	return startDate;
    }

    public void setStartDate(String startDate) {
	this.startDate = startDate;
    }

    public String getEndDate() {
	return endDate;
    }

    public void setEndDate(String endDate) {
	this.endDate = endDate;
    }

    public String getAnalysisUse() {
	return analysisUse;
    }

    public void setAnalysisUse(String analysisUse) {
	this.analysisUse = analysisUse;
    }

    public String getAnalysisDivision() {
	return analysisDivision;
    }

    public void setAnalysisDivision(String analysisDivision) {
	this.analysisDivision = analysisDivision;
    }

    public String getProjectTitle() throws Exception {
	StandardE3PSProjectService projectService = new StandardE3PSProjectService();
	return getProjectNo() != null ? projectService.getProjectNameByProjectNo(getProjectNo()) : "";
    }

    public void setProjectTitle(String projectTitle) {
	this.projectTitle = projectTitle;
    }

    public String getCustomerDepartmentName() throws Exception {
	return KETAnalysisRequestHelper.service.getCustomerDeptsNameLink(getAnalysisRequestOid());
    }

    public void setCustomerDepartmentName(String customerDepartmentName) {
	this.customerDepartmentName = customerDepartmentName;
    }

    public String getAnalysisDivisionName() throws Exception {
	return KETAnalysisRequestHelper.service.getAnalysisDivisionNameLink(getAnalysisRequestOid());
    }

    public void setAnalysisDivisionName(String analysisDivisionName) {
	this.analysisDivisionName = analysisDivisionName;
    }

    public String getCarTypeName() {
	return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
	this.carTypeName = carTypeName;
    }

    public String getProcessName() {
	return processName;
    }

    public void setProcessName(String processName) {
	this.processName = processName;
    }

    public String getKetClientUserName() {
	return ketClientUserName;
    }

    public void setKetClientUserName(String ketClientUserName) {
	this.ketClientUserName = ketClientUserName;
    }

    public String getKetChargeUserName() {
	return ketChargeUserName;
    }

    public void setKetChargeUserName(String ketChargeUserName) {
	this.ketChargeUserName = ketChargeUserName;
    }

    public String getKetClientDepartmentName() {
	return ketClientDepartmentName;
    }

    public void setKetClientDepartmentName(String ketClientDepartmentName) {
	this.ketClientDepartmentName = ketClientDepartmentName;
    }

    public String getKetChargeDepartmentName() {
	return ketChargeDepartmentName;
    }

    public void setKetChargeDepartmentName(String ketChargeDepartmentName) {
	this.ketChargeDepartmentName = ketChargeDepartmentName;
    }

    public String getAnalysisUseName() {
	return analysisUseName;
    }

    public void setAnalysisUseName(String analysisUseName) {
	this.analysisUseName = analysisUseName;
    }

    public String getStateName() {
	return stateName;
    }

    public void setStateName(String stateName) {
	this.stateName = stateName;
    }

    public boolean isCreateUserFlag() {
	return createUserFlag;
    }

    public void setCreateUserFlag(boolean createUserFlag) {
	this.createUserFlag = createUserFlag;
    }

    public String getApprovalUserName() {
	return approvalUserName;
    }

    public void setApprovalUserName(String approvalUserName) {
	this.approvalUserName = approvalUserName;
    }

    public String getStateNameHtmlPrefix() throws Exception {
	if (SessionHelper.manager.getPrincipalReference().getFullName().equals(getApprovalUserName())) {
	    if ("".equals(KETAnalysisInfoHelper.service.getAnalysisInfoOid(getAnalysisRequestOid()))) {
		return super.getHtmlPrefix();
	    } else {
		return "";
	    }
	} else {
	    return "";
	}
    }

    public String getStateNameHtmlPostfix() throws Exception {
	if (SessionHelper.manager.getPrincipalReference().getFullName().equals(getApprovalUserName())) {
	    if ("".equals(KETAnalysisInfoHelper.service.getAnalysisInfoOid(getAnalysisRequestOid()))) {
		return super.getHtmlPostfix();
	    } else {
		return "";
	    }
	} else {
	    return "";
	}

    }

    public boolean isInfoStartFlag() {
	return infoStartFlag;
    }

    public void setInfoStartFlag(boolean infoStartFlag) {
	this.infoStartFlag = infoStartFlag;
    }

    public boolean isInfoProgressFlag() {
	return infoProgressFlag;
    }

    public void setInfoProgressFlag(boolean infoProgressFlag) {
	this.infoProgressFlag = infoProgressFlag;
    }

    public String getCreateStartDate() {
	return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
	this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
	return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
	this.createEndDate = createEndDate;
    }

    public String getEndStartDate() {
	return endStartDate;
    }

    public void setEndStartDate(String endStartDate) {
	this.endStartDate = endStartDate;
    }

    public String getEndEndDate() {
	return endEndDate;
    }

    public void setEndEndDate(String endEndDate) {
	this.endEndDate = endEndDate;
    }

    public String getAnalysisComment() {
	return analysisComment;
    }

    public void setAnalysisComment(String analysisComment) {
	this.analysisComment = analysisComment;
    }

    public String getAnalysisObjectComment() {
	return analysisObjectComment;
    }

    public void setAnalysisObjectComment(String analysisObjectComment) {
	this.analysisObjectComment = analysisObjectComment;
    }

    public String getAnalysisTargetComment() {
	return analysisTargetComment;
    }

    public void setAnalysisTargetComment(String analysisTargetComment) {
	this.analysisTargetComment = analysisTargetComment;
    }

    public String getClassName() {
	return className;
    }

    public void setClassName(String className) {
	this.className = className;
    }

    public String getProjectOid() {
	return projectOid;
    }

    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    public String getPopup() {
	return popup;
    }

    public void setPopup(String popup) {
	this.popup = popup;
    }

    public String getOpreationDivision() {
	return opreationDivision;
    }

    public void setOpreationDivision(String opreationDivision) {
	this.opreationDivision = opreationDivision;
    }
}
