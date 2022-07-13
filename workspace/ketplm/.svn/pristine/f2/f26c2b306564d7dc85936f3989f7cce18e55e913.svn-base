package ext.ket.invest.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import wt.content.ContentHolder;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import e3ps.common.code.NumberCode;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dto.BaseDTO;

@SuppressWarnings("unused")
public class KETInvestTaskDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private String oid;
	private String subject; // 진행 요청사항
	private String deptCode; // 등록자 부서코드
	private String deptCodeName; // 등록자 부서
	private String requestDate; // 완료 요청일자
	private String completeDate; // 완료일
	private String webEditor; // 진행사항
	private String webEditorText; // 진행사항 Text
	private String webEditorTextHtml; // 진행사항 Text
	private String investState; // 상태코드
	private String investStateName; // 상태
	private String workerName; // 담당자명
	private String workerOid; // 담당자 OID
	private String workerDeptCode; // 담당자 부서
	private String workerDeptName; // 담당자 부서
	private String memberOids; // 참조자 OID
	private String memberNames; // 참조자명

	private String creatorName; // 등록자명
	private String creatorOid; // 등록자 OID
	private String createDate; // 등록일
	
	private String collectCode;
	private String collectCodeName;
	private String collectExpense;
	private String collectDate;
	private String progressSubject;

	private int sort;


	private String completeDateState; // 완료일자(상태)

	private KETInvestMasterDTO investMaster;

	private String fileCnt;
	private List<ContentDTO> attachFiles = new ArrayList<ContentDTO>();

	private String investMasterReqNo; // 요청서 No
	private String investMasterReqName; // 요청명
	private String investMasterinvestStateCnt; // 진행상태
	private String investMasterLastCustomerName; // 자동차사
	private String investMasterSubContractorName; // 고객사
	private String investMasterCreateDate; // 등록일자
	private String investMasterRequestDate; // 완료 요청일자
	private String investMasterCompleteDate; // 완료일자
	private String investMasterDelayState; // 신호등
	private String investMasterManagerName; // 주 담당자

	public KETInvestTaskDTO() {

	}

	public KETInvestTaskDTO(KETInvestTask it) throws Exception {

		this.oid = CommonUtil.getOIDString(it);
		this.subject = it.getSubject(); // 요청항목
		this.requestDate = DateUtil.getDateString(it.getRequestDate(), "d"); // 완료 요청일자
		this.completeDate = DateUtil.getDateString(it.getCompleteDate(), "d"); // 완료일
		this.completeDateState = this.completeDate;
		this.webEditor = StringUtil.checkNull(String.valueOf(it.getWebEditor()));
		this.webEditorText = StringUtil.checkNull(String.valueOf(it.getWebEditorText()));
		this.webEditorTextHtml = this.webEditorText.replaceAll("\r\n", "<br/>");
		
		this.collectCode = it.getCollectCode();
		this.collectDate = DateUtil.getDateString(it.getCollectDate(), "d");
		this.collectExpense = it.getCollectExpense();
		this.progressSubject = it.getProgressSubject();

		if (it.getWorker() != null)
			this.workerName = it.getWorker().getFullName(); // 담당자명
		if (it.getWorker() != null)
			this.workerOid = CommonUtil.getOIDString(it.getWorker()); // 담당자 OID

		// 참조자 OID, 참조자명
		QueryResult qr = PersistenceHelper.manager.navigate(it, KETInvestMemberLink.MEMBER_ROLE, KETInvestMemberLink.class, true);

		while (qr.hasMoreElements()) {
			WTUser member = (WTUser) qr.nextElement();

			if (StringUtil.checkString(this.memberOids)) {
				this.memberOids += "," + CommonUtil.getOIDString(member);
				this.memberNames += "," + member.getFullName();
			} else {
				this.memberOids = CommonUtil.getOIDString(member);
				this.memberNames = member.getFullName();
			}
		}

		this.investState = it.getInvestState(); // 상태코드
		this.creatorName = it.getCreatorFullName();
		this.creatorOid = CommonUtil.getOIDString((WTUser) it.getCreator().getPrincipal());
		this.createDate = DateUtil.getDateString(it.getCreateTimestamp(), "d"); // 등록일
		this.investMaster = new KETInvestMasterDTO(it.getInvestMaster());
		this.deptCode = investMaster.getDeptCode();
		this.workerDeptCode = it.getDeptCode();
		this.sort = it.getSort();


		Department dept = DepartmentHelper.manager.getDepartment(this.deptCode);
		if (dept != null)
			this.deptCodeName = dept.getName(); // 등록자 부서

		dept = DepartmentHelper.manager.getDepartment(this.workerDeptCode);
		if (dept != null)
			this.workerDeptName = dept.getName(); // 담당자 부서

		NumberCode investState = CodeHelper.manager.getNumberCode("INVESTSTATE", this.investState);
		if (investState != null)
			this.investStateName = investState.getName(); // 상태

		if (!StringUtil.checkString(this.completeDate)) {

			this.completeDateState = this.investStateName;

			Timestamp requestDate = it.getRequestDate();

			if (requestDate != null) {

				long request = requestDate.getTime() + 86400000;
				long current = DateUtil.getCurrentTimestamp().getTime();

				if (request < current) {
					this.investStateName = "<span style='color:#FF0000;font-weight:bold;'>지연</span>";
					this.completeDateState = "<span style='color:#FF0000;font-weight:bold;'>지연</span>";
				}
			}
		}
		if(this.collectCode != null){
		NumberCode collectCode = CodeHelper.manager.getNumberCode("INVESTCOLLECT", this.collectCode);
		if (collectCode != null)
			this.collectCodeName = collectCode.getName(); // 회수비 구분
		}
		this.attachFiles = KETContentHelper.manager.getSecondaryContents((ContentHolder) it);

		if (attachFiles.size() == 0)
			this.fileCnt = "-";
		else
			this.fileCnt = String.valueOf(attachFiles.size());
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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
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
	 * @return the webEditor
	 */
	public String getWebEditor() {
		return webEditor;
	}

	/**
	 * @param webEditor
	 *            the webEditor to set
	 */
	public void setWebEditor(String webEditor) {
		this.webEditor = webEditor;
	}

	/**
	 * @return the webEditorText
	 */
	public String getWebEditorText() {
		return webEditorText;
	}

	/**
	 * @return the webEditorTextHtml
	 */
	public String getWebEditorTextHtml() {
		return webEditorTextHtml;
	}

	/**
	 * @param webEditorTextHtml
	 *            the webEditorTextHtml to set
	 */
	public void setWebEditorTextHtml(String webEditorTextHtml) {
		this.webEditorTextHtml = webEditorTextHtml;
	}

	/**
	 * @param webEditorText
	 *            the webEditorText to set
	 */
	public void setWebEditorText(String webEditorText) {
		this.webEditorText = webEditorText;
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
	 * @return the workerName
	 */
	public String getWorkerName() {
		return workerName;
	}

	/**
	 * @param workerName
	 *            the workerName to set
	 */
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	/**
	 * @return the workerOid
	 */
	public String getWorkerOid() {
		return workerOid;
	}

	/**
	 * @param workerOid
	 *            the workerOid to set
	 */
	public void setWorkerOid(String workerOid) {
		this.workerOid = workerOid;
	}

	/**
	 * @return the memberOids
	 */
	public String getMemberOids() {
		return memberOids;
	}

	/**
	 * @param memberOids
	 *            the memberOids to set
	 */
	public void setMemberOids(String memberOids) {
		this.memberOids = memberOids;
	}

	/**
	 * @return the memberNames
	 */
	public String getMemberNames() {
		return memberNames;
	}

	/**
	 * @param memberNames
	 *            the memberNames to set
	 */
	public void setMemberNames(String memberNames) {
		this.memberNames = memberNames;
	}

	/**
	 * @return the workerDeptCode
	 */
	public String getWorkerDeptCode() {
		return workerDeptCode;
	}

	/**
	 * @param workerDeptCode
	 *            the workerDeptCode to set
	 */
	public void setWorkerDeptCode(String workerDeptCode) {
		this.workerDeptCode = workerDeptCode;
	}

	/**
	 * @return the workerDeptName
	 */
	public String getWorkerDeptName() {
		return workerDeptName;
	}

	/**
	 * @param workerDeptName
	 *            the workerDeptName to set
	 */
	public void setWorkerDeptName(String workerDeptName) {
		this.workerDeptName = workerDeptName;
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
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * @return the investMaster
	 */
	public KETInvestMasterDTO getinvestMaster() {
		return investMaster;
	}

	/**
	 * @param investMaster
	 *            the investMaster to set
	 */
	public void setInvestMaster(KETInvestMasterDTO investMaster) {
		this.investMaster = investMaster;
	}

	/**
	 * @return the completeDateState
	 */
	public String getCompleteDateState() {
		return completeDateState;
	}

	/**
	 * @param completeDateState
	 *            the completeDateState to set
	 */
	public void setCompleteDateState(String completeDateState) {
		this.completeDateState = completeDateState;
	}

	/**
	 * @return the fileCnt
	 */
	public String getFileCnt() {
		return fileCnt;
	}

	/**
	 * @param fileCnt
	 *            the fileCnt to set
	 */
	public void setFileCnt(String fileCnt) {
		this.fileCnt = fileCnt;
	}

	/**
	 * @return the investMasterReqNo
	 */
	public String getInvestMasterReqNo() {
		return investMaster.getReqNo();
	}

	/**
	 * @return the investMasterReqName
	 */
	public String getInvestMasterReqName() {
		return investMaster.getReqName();
	}

	/**
	 * @return the investMasterSubContractorName
	 */
	public String getInvestMasterSubContractorName() {
		return investMaster.getSubContractorName();
	}

	/**
	 * @return the investMasterCreateDate
	 */
	public String getInvestMasterCreateDate() {
		return investMaster.getCreateDate();
	}

	/**
	 * @return the investMasterRequestDate
	 */
	public String getInvestMasterRequestDate() {
		return investMaster.getRequestDate();
	}

	/**
	 * @return the investMasterCompleteDate
	 */
	public String getInvestMasterCompleteDate() {
		return investMaster.getCompleteDate();
	}

	/**
	 * @return the investMasterDelayState
	 */
	public String getInvestMasterDelayState() {
		return investMaster.getDelayState();
	}

	/**
	 * @return the investMasterManagerName
	 */
	public String getInvestMasterManagerName() {
		return investMaster.getManagerName();
	}

	/**
	 * @return the attachFiles
	 */
	public List<ContentDTO> getAttachFiles() {
		return attachFiles;
	}

	/**
	 * @param attachFiles
	 *            the attachFiles to set
	 */
	public void setAttachFiles(List<ContentDTO> attachFiles) {
		this.attachFiles = attachFiles;
	}
	
	
	public String getCollectCode() {
	    return collectCode;
	}

	public void setCollectCode(String collectCode) {
	    this.collectCode = collectCode;
	}

	public String getCollectExpense() {
	    return collectExpense;
	}

	public void setCollectExpense(String collectExpense) {
	    this.collectExpense = collectExpense;
	}

	public String getCollectDate() {
	    return collectDate;
	}

	public void setCollectDate(String collectDate) {
	    this.collectDate = collectDate;
	}

	public String getProgressSubject() {
	    return progressSubject;
	}

	public void setProgressSubject(String progressSubject) {
	    this.progressSubject = progressSubject;
	}

	public String getCollectCodeName() {
	    return collectCodeName;
	}

	public void setCollectCodeName(String collectCodeName) {
	    this.collectCodeName = collectCodeName;
	}
	
	

}
