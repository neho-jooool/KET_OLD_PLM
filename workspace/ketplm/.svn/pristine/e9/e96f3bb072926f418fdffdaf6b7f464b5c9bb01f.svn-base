package ext.ket.issue.entity;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import wt.fc.Persistable;
import wt.org.WTUser;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.issue.util.IssueUtil;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.dto.BaseDTO;

public class KETIssueMasterDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String oid;
    private String reqNo; // 요청No
    private String reqName; // 요청명
    private String deptCode; // 등록자 부서코드
    private String deptCodeName; // 등록자 부서
    private String reqCode; // 요청구분코드
    private String reqCodeName; // 요청구분
    private String detailCode; // 상세구분코드
    private String detailCodeName; // 상세구분
    private String rank; // 등급코드
    private String rankName; // 등급
    private String requestDate; // 완료 요청일자
    private String completeDate; // 완료일
    private String lastCustomer; // 자동차사코드
    private String lastCustomerName; // 자동차사
    private String subContractor; // 고객사코드
    private String subContractorName; // 고객사
    private String mainSubject; // 요청사항
    private String mainSubjectHtml; // 요청사항
    private String issueState; // 상태코드
    private String issueStateName; // 상태
    private int version; // 버전

    private String creatorName; // 등록자명
    private String creatorOid; // 등록자 OID
    private String createDate; // 등록일

    private String pboOid; // pbo
    private String pboNo; // pboNo
    private String pboName; // pboName
    private String managerOid; // 주 담당자 OID
    private String managerName; // 주 담당자명
    private String mDeptName; // 주 담당자부서
    private String oemOid; // 대표차종
    private String oemName; // 대표차종

    // 검색조건
    private String requestStartDate;
    private String requestEndDate;
    private String sopStartDate;
    private String sopEndDate;
    private String completeStartDate;
    private String completeEndDate;
    private String tmUserOid; // 세부 담당자
    private String issueTaskState; // 세부항목 상태
    private String taskDeptCode; // 세부 담당부서

    private String tmUserNames;
    private String pboState;
    private String issueStateCnt; // 세부 진행상태 정보
    private String delayState;
    private int delayFlag;
    private String partNos;
    private String partCount;
    private String divisionCode;
    private String meetingTarget;

    public KETIssueMasterDTO() {

    }

    public KETIssueMasterDTO(KETIssueMaster im) throws Exception {

	this.oid = CommonUtil.getOIDString(im);
	this.reqNo = im.getReqNo();
	this.reqName = im.getReqName();

	this.reqNo = im.getReqNo(); // 요청No
	this.reqName = im.getReqName(); // 요청명
	this.deptCode = im.getDeptCode(); // 등록자 부서코드
	this.reqCode = im.getReqCode(); // 요청구분코드
	this.detailCode = im.getDetailCode(); // 상세구분코드
	this.rank = im.getRank(); // 등급코드
	this.requestDate = DateUtil.getDateString(im.getRequestDate(), "d"); // 완료 요청일자
	this.completeDate = DateUtil.getDateString(im.getCompleteDate(), "d"); // 완료일
	this.lastCustomer = im.getLastCustomer(); // 자동차사코드
	this.subContractor = im.getSubContractor(); // 고객사코드
	this.mainSubject = StringUtil.checkNull(im.getMainSubject()); // 요청사항
	this.mainSubjectHtml = this.mainSubject.replaceAll("\r\n", "<br/>"); // 요청사항
	this.issueState = im.getIssueState(); // 상태코드
	this.version = im.getVersion(); // 버전
	this.creatorName = im.getCreatorFullName(); // 등록자명
	this.creatorOid = CommonUtil.getOIDString((WTUser) im.getCreator().getPrincipal()); // 등록자 OID
	this.createDate = DateUtil.getDateString(im.getCreateTimestamp(), "d"); // 등록일
	this.partNos = im.getPartNos();
	this.partCount = Integer.toString(im.getPartCount());

	Department dept = DepartmentHelper.manager.getDepartment(this.deptCode);
	NumberCode reqCode = CodeHelper.manager.getNumberCode("ISSUEREQ", this.reqCode);
	NumberCode detailCode = CodeHelper.manager.getNumberCode("ISSUEREQ", this.detailCode);
	NumberCode rank = CodeHelper.manager.getNumberCode("ISSUERANK", this.rank);
	NumberCode lastCustomer = null;
	if (StringUtils.isNotEmpty(this.lastCustomer)) {
	    lastCustomer = CodeHelper.manager.getNumberCode("CUSTOMEREVENT", this.lastCustomer);
	}
	NumberCode issueState = CodeHelper.manager.getNumberCode("ISSUESTATE", this.issueState);

	if (dept != null)
	    this.deptCodeName = dept.getName(); // 등록자 부서
	if (reqCode != null)
	    this.reqCodeName = reqCode.getName(); // 요청구분
	if (detailCode != null)
	    this.detailCodeName = detailCode.getName(); // 상세구분
	if (rank != null)
	    this.rankName = rank.getName(); // 등급
	if (lastCustomer != null)
	    this.lastCustomerName = lastCustomer.getName(); // 자동차사
	if (issueState != null)
	    this.issueStateName = issueState.getName(); // 상태

	String[] subContractors = StringUtils.isNotEmpty(this.subContractor) ? this.subContractor.split(",") : null;

	String subContractorsName = "";
	if (subContractors != null) {
	    for (String subContractorStr : subContractors) {
		NumberCode subContractor = NumberCodeHelper.manager.getNumberCode("SUBCONTRACTOR", subContractorStr);
		if (subContractor != null)
		    subContractorsName += subContractor.getName() + ",";
	    }
	}

	subContractorsName = StringUtils.removeEnd(subContractorsName, ",");
	this.subContractorName = subContractorsName; // 고객사

	KETGeneralissueLink link = IssueUtil.manager.getPBOLink(im);

	if (link != null) {
	    Persistable pbo = link.getPbo();
	    this.pboNo = link.getPboNo();
	    this.pboName = link.getPboName();
	    this.pboOid = CommonUtil.getOIDString(link.getPbo());

	    if (pbo instanceof KETSalesProject) {
		NumberCode salesState = ((KETSalesProject) pbo).getSalesState();
		if (salesState != null)
		    this.pboState = salesState.getName();
	    }
	}

	this.managerName = im.getManager().getFullName();
	this.managerOid = CommonUtil.getOIDString(im.getManager());
	dept = DepartmentHelper.manager.getDepartment(im.getManager());
	if (dept != null)
	    this.mDeptName = dept.getName();

	if (im.getOemType() != null) {
	    this.oemName = im.getOemType().getName();
	    this.oemOid = CommonUtil.getOIDString(im.getOemType());
	}

	if (!IssueUtil.COMPLETE.equals(this.issueState)) {

	    this.delayState = "<img src='/plm/extcore/image/ico_green.png' />";

	    if (IssueUtil.INPROGRESS.equals(this.issueState)) {
		this.delayFlag = IssueUtil.FLAGINPROGRESS;
	    }

	    Timestamp requestDate = im.getRequestDate();
	    if (requestDate != null) {

		long request = requestDate.getTime() + 86400000;
		long current = DateUtil.getCurrentTimestamp().getTime();
		long flag = request;

		if ("IR001".equals(this.rank)) {
		    flag -= (86400000 * 1); // 1일 milisecond
		} else {
		    flag -= (86400000 * 3); // 3일 milisecond
		}

		if (request < current) {
		    this.delayFlag = IssueUtil.FLAGDELAY;
		    this.delayState = "<img src='/plm/extcore/image/ico_red.png' />";
		} else if (flag < current) {
		    this.delayFlag = IssueUtil.FLAGWARNING;
		    this.delayState = "<img src='/plm/extcore/image/ico_yellow.png' />";
		}
	    }

	} else {
	    this.delayState = "";
	    this.delayFlag = IssueUtil.FLAGCOMPLETE;
	}
    }

    /**
     * @return the oid
     */
    public String getOid() {
	return oid;
    }

    /**
     * @param oid
     *            the oid to set
     */
    public void setOid(String oid) {
	this.oid = oid;
    }

    /**
     * @return the reqNo
     */
    public String getReqNo() {
	return reqNo;
    }

    /**
     * @param reqNo
     *            the reqNo to set
     */
    public void setReqNo(String reqNo) {
	this.reqNo = reqNo;
    }

    /**
     * @return the reqName
     */
    public String getReqName() {
	return reqName;
    }

    /**
     * @param reqName
     *            the reqName to set
     */
    public void setReqName(String reqName) {
	this.reqName = reqName;
    }

    /**
     * @return the deptCode
     */
    public String getDeptCode() {
	return deptCode;
    }

    /**
     * @param deptCode
     *            the deptCode to set
     */
    public void setDeptCode(String deptCode) {
	this.deptCode = deptCode;
    }

    /**
     * @return the deptCodeName
     */
    public String getDeptCodeName() {
	return deptCodeName;
    }

    /**
     * @param deptCodeName
     *            the deptCodeName to set
     */
    public void setDeptCodeName(String deptCodeName) {
	this.deptCodeName = deptCodeName;
    }

    /**
     * @return the reqCode
     */
    public String getReqCode() {
	return reqCode;
    }

    /**
     * @param reqCode
     *            the reqCode to set
     */
    public void setReqCode(String reqCode) {
	this.reqCode = reqCode;
    }

    /**
     * @return the reqCodeName
     */
    public String getReqCodeName() {
	return reqCodeName;
    }

    /**
     * @param reqCodeName
     *            the reqCodeName to set
     */
    public void setReqCodeName(String reqCodeName) {
	this.reqCodeName = reqCodeName;
    }

    /**
     * @return the detailCode
     */
    public String getDetailCode() {
	return detailCode;
    }

    /**
     * @param detailCode
     *            the detailCode to set
     */
    public void setDetailCode(String detailCode) {
	this.detailCode = detailCode;
    }

    /**
     * @return the detailCodeName
     */
    public String getDetailCodeName() {
	return detailCodeName;
    }

    /**
     * @param detailCodeName
     *            the detailCodeName to set
     */
    public void setDetailCodeName(String detailCodeName) {
	this.detailCodeName = detailCodeName;
    }

    /**
     * @return the rank
     */
    public String getRank() {
	return rank;
    }

    /**
     * @param rank
     *            the rank to set
     */
    public void setRank(String rank) {
	this.rank = rank;
    }

    /**
     * @return the rankName
     */
    public String getRankName() {
	return rankName;
    }

    /**
     * @param rankName
     *            the rankName to set
     */
    public void setRankName(String rankName) {
	this.rankName = rankName;
    }

    /**
     * @return the requestDate
     */
    public String getRequestDate() {
	return requestDate;
    }

    /**
     * @param requestDate
     *            the requestDate to set
     */
    public void setRequestDate(String requestDate) {
	this.requestDate = requestDate;
    }

    /**
     * @return the completeDate
     */
    public String getCompleteDate() {
	return completeDate;
    }

    /**
     * @param completeDate
     *            the completeDate to set
     */
    public void setCompleteDate(String completeDate) {
	this.completeDate = completeDate;
    }

    /**
     * @return the lastCustomer
     */
    public String getLastCustomer() {
	return lastCustomer;
    }

    /**
     * @param lastCustomer
     *            the lastCustomer to set
     */
    public void setLastCustomer(String lastCustomer) {
	this.lastCustomer = lastCustomer;
    }

    /**
     * @return the lastCustomerName
     */
    public String getLastCustomerName() {
	return lastCustomerName;
    }

    /**
     * @param lastCustomerName
     *            the lastCustomerName to set
     */
    public void setLastCustomerName(String lastCustomerName) {
	this.lastCustomerName = lastCustomerName;
    }

    /**
     * @return the subContractor
     */
    public String getSubContractor() {
	return subContractor;
    }

    /**
     * @param subContractor
     *            the subContractor to set
     */
    public void setSubContractor(String subContractor) {
	this.subContractor = subContractor;
    }

    /**
     * @return the subContractorName
     */
    public String getSubContractorName() {
	return subContractorName;
    }

    /**
     * @param subContractorName
     *            the subContractorName to set
     */
    public void setSubContractorName(String subContractorName) {
	this.subContractorName = subContractorName;
    }

    /**
     * @return the mainSubject
     */
    public String getMainSubject() {
	return mainSubject;
    }

    /**
     * @param mainSubject
     *            the mainSubject to set
     */
    public void setMainSubject(String mainSubject) {
	this.mainSubject = mainSubject;
    }

    /**
     * @return the mainSubjectHtml
     */
    public String getMainSubjectHtml() {
	return mainSubjectHtml;
    }

    /**
     * @param mainSubjectHtml
     *            the mainSubjectHtml to set
     */
    public void setMainSubjectHtml(String mainSubjectHtml) {
	this.mainSubjectHtml = mainSubjectHtml;
    }

    /**
     * @return the issueState
     */
    public String getIssueState() {
	return issueState;
    }

    /**
     * @param issueState
     *            the issueState to set
     */
    public void setIssueState(String issueState) {
	this.issueState = issueState;
    }

    /**
     * @return the issueStateName
     */
    public String getIssueStateName() {
	return issueStateName;
    }

    /**
     * @param issueStateName
     *            the issueStateName to set
     */
    public void setIssueStateName(String issueStateName) {
	this.issueStateName = issueStateName;
    }

    /**
     * @return the version
     */
    public int getVersion() {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(int version) {
	this.version = version;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
	return creatorName;
    }

    /**
     * @param creatorName
     *            the creatorName to set
     */
    public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
    }

    /**
     * @return the creatorOid
     */
    public String getCreatorOid() {
	return creatorOid;
    }

    /**
     * @param creatorOid
     *            the creatorOid to set
     */
    public void setCreatorOid(String creatorOid) {
	this.creatorOid = creatorOid;
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
     * @return the pboNo
     */
    public String getPboNo() {
	return pboNo;
    }

    /**
     * @param pboNo
     *            the pboNo to set
     */
    public void setPboNo(String pboNo) {
	this.pboNo = pboNo;
    }

    /**
     * @return the pboName
     */
    public String getPboName() {
	return pboName;
    }

    /**
     * @param pboName
     *            the pboName to set
     */
    public void setPboName(String pboName) {
	this.pboName = pboName;
    }

    /**
     * @return the managerOid
     */
    public String getManagerOid() {
	return managerOid;
    }

    /**
     * @param managerOid
     *            the managerOid to set
     */
    public void setManagerOid(String managerOid) {
	this.managerOid = managerOid;
    }

    /**
     * @return the managerName
     */
    public String getManagerName() {
	return managerName;
    }

    /**
     * @param managerName
     *            the managerName to set
     */
    public void setManagerName(String managerName) {
	this.managerName = managerName;
    }

    /**
     * @return the mDeptName
     */
    public String getmDeptName() {
	return mDeptName;
    }

    /**
     * @param mDeptName
     *            the mDeptName to set
     */
    public void setmDeptName(String mDeptName) {
	this.mDeptName = mDeptName;
    }

    /**
     * @return the oemOid
     */
    public String getOemOid() {
	return oemOid;
    }

    /**
     * @param oemOid
     *            the oemOid to set
     */
    public void setOemOid(String oemOid) {
	this.oemOid = oemOid;
    }

    /**
     * @return the oemName
     */
    public String getOemName() {
	return oemName;
    }

    /**
     * @param oemName
     *            the oemName to set
     */
    public void setOemName(String oemName) {
	this.oemName = oemName;
    }

    /**
     * @return the requestStartDate
     */
    public String getRequestStartDate() {
	return requestStartDate;
    }

    /**
     * @param requestStartDate
     *            the requestStartDate to set
     */
    public void setRequestStartDate(String requestStartDate) {
	this.requestStartDate = requestStartDate;
    }

    /**
     * @return the requestEndDate
     */
    public String getRequestEndDate() {
	return requestEndDate;
    }

    /**
     * @param requestEndDate
     *            the requestEndDate to set
     */
    public void setRequestEndDate(String requestEndDate) {
	this.requestEndDate = requestEndDate;
    }

    /**
     * @return the sopStartDate
     */
    public String getSopStartDate() {
	return sopStartDate;
    }

    /**
     * @param sopStartDate
     *            the sopStartDate to set
     */
    public void setSopStartDate(String sopStartDate) {
	this.sopStartDate = sopStartDate;
    }

    /**
     * @return the sopEndDate
     */
    public String getSopEndDate() {
	return sopEndDate;
    }

    /**
     * @param sopEndDate
     *            the sopEndDate to set
     */
    public void setSopEndDate(String sopEndDate) {
	this.sopEndDate = sopEndDate;
    }

    /**
     * @return the tmUserOid
     */
    public String getTmUserOid() {
	return tmUserOid;
    }

    /**
     * @param tmUserOid
     *            the tmUserOid to set
     */
    public void setTmUserOid(String tmUserOid) {
	this.tmUserOid = tmUserOid;
    }

    /**
     * @return the tmUserNames
     */
    public String getTmUserNames() {
	return tmUserNames;
    }

    /**
     * @param tmUserNames
     *            the tmUserNames to set
     */
    public void setTmUserNames(String tmUserNames) {
	this.tmUserNames = tmUserNames;
    }

    /**
     * @return the pboState
     */
    public String getPboState() {
	return pboState;
    }

    /**
     * @param pboState
     *            the pboState to set
     */
    public void setPboState(String pboState) {
	this.pboState = pboState;
    }

    /**
     * @return the issueStateCnt
     */
    public String getIssueStateCnt() {
	return issueStateCnt;
    }

    /**
     * @param issueStateCnt
     *            the issueStateCnt to set
     */
    public void setIssueStateCnt(String issueStateCnt) {
	this.issueStateCnt = issueStateCnt;
    }

    /**
     * @return the delayState
     */
    public String getDelayState() {
	return delayState;
    }

    /**
     * @param delayState
     *            the delayState to set
     */
    public void setDelayState(String delayState) {
	this.delayState = delayState;
    }

    /**
     * @return the delayFlag
     */
    public int getDelayFlag() {
	return delayFlag;
    }

    /**
     * @param delayFlag
     *            the delayFlag to set
     */
    public void setDelayFlag(int delayFlag) {
	this.delayFlag = delayFlag;
    }

    /**
     * @return the issueTaskState
     */
    public String getIssueTaskState() {
	return issueTaskState;
    }

    /**
     * @param issueTaskState
     *            the issueTaskState to set
     */
    public void setIssueTaskState(String issueTaskState) {
	this.issueTaskState = issueTaskState;
    }

    /**
     * @return the taskDeptCode
     */
    public String getTaskDeptCode() {
	return taskDeptCode;
    }

    /**
     * @param taskDeptCode
     *            the taskDeptCode to set
     */
    public void setTaskDeptCode(String taskDeptCode) {
	this.taskDeptCode = taskDeptCode;
    }

    public String getPartNos() {
	return partNos;
    }

    public void setPartNos(String partNos) {
	this.partNos = partNos;
    }

    public String getPartCount() {
	return partCount;
    }

    public void setPartCount(String partCount) {
	this.partCount = partCount;
    }

    public String getDivisionCode() {
	return divisionCode;
    }

    public void setDivisionCode(String divisionCode) {
	this.divisionCode = divisionCode;
    }

    public String getCompleteStartDate() {
	return completeStartDate;
    }

    public void setCompleteStartDate(String completeStartDate) {
	this.completeStartDate = completeStartDate;
    }

    public String getCompleteEndDate() {
	return completeEndDate;
    }

    public void setCompleteEndDate(String completeEndDate) {
	this.completeEndDate = completeEndDate;
    }

    public String getMeetingTarget() {
	return meetingTarget;
    }

    public void setMeetingTarget(String meetingTarget) {
	this.meetingTarget = meetingTarget;
    }
}
