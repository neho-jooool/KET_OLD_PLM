package ext.ket.invest.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.org.WTUser;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDevelopmentRequest;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.invest.util.InvestUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.dto.BaseDTO;

public class KETInvestMasterDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String oid;
    private String reqNo; // 요청No
    private String reqName; // 요청명
    private String deptCode; // 등록자 부서코드
    private String deptCodeName; // 등록자 부서
    private String requestDate; // 완료 요청일자
    private String completeDate; // 완료일

    private String subContractor; // 고객사코드
    private String subContractorName; // 고객사
    private String bigo; // 요청사항
    private String bigoHtml; // 요청사항
    private String investState; // 상태코드
    private String investStateName; // 상태

    private String creatorName; // 등록자명
    private String creatorOid; // 등록자 OID
    private String createDate; // 등록일

    private String managerOid; // 주 담당자 OID
    private String managerName; // 주 담당자명
    private String mDeptName; // 주 담당자부서

    // 검색조건
    private String requestStartDate;
    private String requestEndDate;
    private String sopStartDate;
    private String sopEndDate;
    private String tmUserOid; // 세부 담당자
    private String investTaskState; // 세부항목 상태
    private String taskDeptCode; // 세부 담당부서

    private String tmUserNames;
    private String investStateCnt; // 세부 진행상태 정보
    private String delayState;
    private String delayFlag;
    private String drNumber;
    private String drKeyOid;
    private String drName;

    private String investExpense; // 투자비 합계
    private String collectExpense; // 회수비 합계
    private String investExpense_1; // 투자비(금형/설비)
    private String investExpense_2; // 투자비 (QDM/기타)
    private String collectExpense_1; // 회수비(금형/설비)
    private String collectExpense_2; // 회수비(금형/설비)
    private String taskDeptCodeNames;
    private int dateChangeCount;
    private String dateChangeComment;

    private String state;
    private String pjtNo;
    private String investTypeCode;
    private String investTypeCodeName;

    private List<Map<String, Object>> productProjectList;
    private List<Map<String, Object>> dateHistoryList;

    public KETInvestMasterDTO() {

    }

    public KETInvestMasterDTO(KETInvestMaster im) throws Exception {

	this.oid = CommonUtil.getOIDString(im);
	this.reqNo = im.getReqNo();
	this.reqName = im.getReqName();

	this.reqNo = im.getReqNo(); // 요청No
	this.reqName = im.getReqName(); // 요청명
	this.deptCode = im.getDeptCode(); // 등록자 부서코드
	this.requestDate = DateUtil.getDateString(im.getRequestDate(), "d"); // 완료 요청일자
	this.completeDate = DateUtil.getDateString(im.getCompleteDate(), "d"); // 완료일

	this.bigo = StringUtil.checkNull(im.getBigo()); // 요청사항
	this.bigoHtml = this.bigo.replaceAll("\r\n", "<br/>"); // 요청사항
	this.investState = im.getInvestState(); // 상태코드

	this.creatorName = im.getCreatorFullName(); // 등록자명
	this.creatorOid = CommonUtil.getOIDString((WTUser) im.getCreator().getPrincipal()); // 등록자 OID
	this.createDate = DateUtil.getDateString(im.getCreateTimestamp(), "d"); // 등록일
	this.investExpense = im.getInvestExpense();
	this.collectExpense = im.getCollectExpense();
	this.investExpense_1 = im.getInvestExpense_1();
	this.investExpense_2 = im.getInvestExpense_2();
	this.collectExpense_1 = im.getCollectExpense_1();
	this.collectExpense_2 = im.getCollectExpense_2();
	this.investTypeCode = im.getInvestTypeCode();
	if (im.getDateChangeCount() != null) {
	    this.dateChangeCount = im.getDateChangeCount();
	} else {
	    this.dateChangeCount = 0;
	}

	this.dateChangeComment = " (변경 " + this.dateChangeCount + "회)";

	Department dept = DepartmentHelper.manager.getDepartment(this.deptCode);

	NumberCode investState = CodeHelper.manager.getNumberCode("INVESTSTATE", this.investState);

	KETDevelopmentRequest dr = im.getDevRequest();

	if (dr != null) {
	    this.drKeyOid = CommonUtil.getOIDString(dr);
	    this.drNumber = dr.getNumber();
	    this.drName = dr.getDevProductName();
	    String[] subContractors = dr.getRequestBuyer().split(",");
	    String subContractorsName = "";
	    for (String subContractorStr : subContractors) {
		NumberCode subContractor = NumberCodeHelper.manager.getNumberCode("SUBCONTRACTOR", subContractorStr);
		if (subContractor != null) {
		    subContractorsName += subContractor.getName() + ",";
		    subContractorsName = StringUtils.removeEnd(subContractorsName, ",");
		    this.subContractorName = subContractorsName; // 고객사
		}
	    }
	    // this.setProductProjectList(InvestUtil.manager.getProjectListByDrNumber(this.drNumber));
	}
	this.pjtNo = im.getPjtNos();
	String pjtNos = im.getPjtNos();

	if (StringUtils.isNotEmpty(pjtNos)) {
	    String pjtList[] = pjtNos.split(",");
	    Map<String, Object> reqMap = new HashMap<String, Object>();
	    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	    if (pjtList != null) {
		for (int i = 0; i < pjtList.length; i++) {
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("pjtNo", pjtList[i]);
		    dataList.add(map);
		}
		reqMap.put("data", dataList);
		this.setProductProjectList(InvestUtil.manager.getProjectInfoByPjtNo(reqMap));
	    }
	}

	if (this.investTypeCode != null) {
	    NumberCode investCode = CodeHelper.manager.getNumberCode("INVESTTYPE", this.investTypeCode);
	    if (investCode != null)
		this.investTypeCodeName = investCode.getName(); // 유형
	}

	if (dept != null)
	    this.deptCodeName = dept.getName(); // 등록자 부서

	if (investState != null)
	    this.investStateName = investState.getName(); // 상태

	this.managerName = im.getManager().getFullName();
	this.managerOid = CommonUtil.getOIDString(im.getManager());
	dept = DepartmentHelper.manager.getDepartment(im.getManager());
	if (dept != null)
	    this.mDeptName = dept.getName();

	this.delayState = "●";
	if (im.getRequestDate() != null) {
	    this.delayFlag = InvestUtil.manager.getDelayFlag(im);
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
     * @return the bigo
     */
    public String getbigo() {
	return bigo;
    }

    /**
     * @param bigo
     *            the bigo to set
     */
    public void setbigo(String bigo) {
	this.bigo = bigo;
    }

    /**
     * @return the bigoHtml
     */
    public String getbigoHtml() {
	return bigoHtml;
    }

    /**
     * @param bigoHtml
     *            the bigoHtml to set
     */
    public void setbigoHtml(String bigoHtml) {
	this.bigoHtml = bigoHtml;
    }

    /**
     * @return the investState
     */
    public String getInvestState() {
	return investState;
    }

    /**
     * @param investState
     *            the investState to set
     */
    public void setInvestState(String investState) {
	this.investState = investState;
    }

    /**
     * @return the investStateName
     */
    public String getInvestStateName() {
	return investStateName;
    }

    /**
     * @param investStateName
     *            the investStateName to set
     */
    public void setInvestStateName(String investStateName) {
	this.investStateName = investStateName;
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
     * @return the investStateCnt
     */
    public String getInvestStateCnt() {
	return investStateCnt;
    }

    /**
     * @param investStateCnt
     *            the investStateCnt to set
     */
    public void setInvestStateCnt(String investStateCnt) {
	this.investStateCnt = investStateCnt;
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
    public String getDelayFlag() {
	return delayFlag;
    }

    /**
     * @param delayFlag
     *            the delayFlag to set
     */
    public void setDelayFlag(String delayFlag) {
	this.delayFlag = delayFlag;
    }

    /**
     * @return the investTaskState
     */
    public String getInvestTaskState() {
	return investTaskState;
    }

    /**
     * @param investTaskState
     *            the investTaskState to set
     */
    public void setInvestTaskState(String investTaskState) {
	this.investTaskState = investTaskState;
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

    /**
     * @return the drKeyOid
     */
    public String getDrKeyOid() {
	return drKeyOid;
    }

    /**
     * @param drKeyOid
     *            the drKeyOid to set
     */
    public void setDrKeyOid(String drKeyOid) {
	this.drKeyOid = drKeyOid;
    }

    /**
     * @return the drNumber
     */
    public String getDrNumber() {
	return drNumber;
    }

    /**
     * @param drNumber
     *            the drNumber to set
     */
    public void setDrNumber(String drNumber) {
	this.drNumber = drNumber;
    }

    /**
     * @return the drName
     */
    public String getDrName() {
	return drName;
    }

    /**
     * @param drName
     *            the drName to set
     */
    public void setDrName(String drName) {
	this.drName = drName;
    }

    /**
     * @return the investExpense
     */
    public String getInvestExpense() {
	return investExpense;
    }

    /**
     * @param investExpense
     *            the investExpense to set
     */
    public void setInvestExpense(String investExpense) {
	this.investExpense = investExpense;
    }

    /**
     * @return the collectExpense
     */
    public String getCollectExpense() {
	return collectExpense;
    }

    /**
     * @param collectExpense
     *            the collectExpense to set
     */
    public void setCollectExpense(String collectExpense) {
	this.collectExpense = collectExpense;
    }

    /**
     * @return the productProjectList
     */
    public List<Map<String, Object>> getProductProjectList() {
	return productProjectList;
    }

    /**
     * @param productProjectList
     *            the productProjectList to set
     */
    public void setProductProjectList(List<Map<String, Object>> productProjectList) {
	this.productProjectList = productProjectList;
    }

    /**
     * @return the taskDeptCodeNames
     */
    public String getTaskDeptCodeNames() {
	return taskDeptCodeNames;
    }

    /**
     * @param taskDeptCodeNames
     *            the taskDeptCodeNames to set
     */
    public void setTaskDeptCodeNames(String taskDeptCodeNames) {
	this.taskDeptCodeNames = taskDeptCodeNames;
    }

    public String getDelayStateHtmlPrefix() {
	String font = "";
	font = "<font color='" + delayFlag + "'>";
	return font;

    }

    public String getDelayStateHtmlPostfix() {
	String font = "";
	font = "</font>";
	return font;
    }

    /**
     * @return the investExpense_1
     */
    public String getInvestExpense_1() {
	return investExpense_1;
    }

    /**
     * @param investExpense_1
     *            the investExpense_1 to set
     */
    public void setInvestExpense_1(String investExpense_1) {
	this.investExpense_1 = investExpense_1;
    }

    public String getInvestExpense_2() {
	return investExpense_2;
    }

    public void setInvestExpense_2(String investExpense_2) {
	this.investExpense_2 = investExpense_2;
    }

    public String getCollectExpense_1() {
	return collectExpense_1;
    }

    public void setCollectExpense_1(String collectExpense_1) {
	this.collectExpense_1 = collectExpense_1;
    }

    public String getCollectExpense_2() {
	return collectExpense_2;
    }

    public void setCollectExpense_2(String collectExpense_2) {
	this.collectExpense_2 = collectExpense_2;
    }

    public List<Map<String, Object>> getDateHistoryList() {
	return dateHistoryList;
    }

    public void setDateHistoryList(List<Map<String, Object>> dateHistoryList) {
	this.dateHistoryList = dateHistoryList;
    }

    /**
     * @return the dateChangeCount
     */
    public int getDateChangeCount() {
	return dateChangeCount;
    }

    /**
     * @param dateChangeCount
     *            the dateChangeCount to set
     */
    public void setDateChangeCount(int dateChangeCount) {
	this.dateChangeCount = dateChangeCount;
    }

    /**
     * @return the dateChangeComment
     */
    public String getDateChangeComment() {
	return dateChangeComment;
    }

    /**
     * @param dateChangeComment
     *            the dateChangeComment to set
     */
    public void setDateChangeComment(String dateChangeComment) {
	this.dateChangeComment = dateChangeComment;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getPjtNo() {
	return pjtNo;
    }

    public void setPjtNo(String pjtNo) {
	this.pjtNo = pjtNo;
    }

    public String getInvestTypeCode() {
	return investTypeCode;
    }

    public void setInvestTypeCode(String investTypeCode) {
	this.investTypeCode = investTypeCode;
    }

    public String getInvestTypeCodeName() {
	return investTypeCodeName;
    }

    public void setInvestTypeCodeName(String investTypeCodeName) {
	this.investTypeCodeName = investTypeCodeName;
    }

}
