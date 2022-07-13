package ext.ket.dms.entity;

import wt.org.WTUser;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.dms.util.KETDocumentUtil;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dto.BaseDTO;

public class KETSGDocumentDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private String oid;
	private String docNo; // 문서번호
	private String docName; // 문서명
	private String moduleCode; // 모듈 CODE
	private String moduleDisplay; // 모듈 Display
	private String refView; // 관련화면
	private String description; // 양식설명
	private int version; // 버전
	private boolean lastest; // 최신
	private long branchId;

	private String creatorName; // 등록자명
	private String creatorOid; // 등록자 OID
	private String createDate; // 등록일
	private String modifyDate; // 수정일

	private ContentDTO primaryDTOFile;

	public KETSGDocumentDTO() {

	}

	public KETSGDocumentDTO(KETSGDocument sgDoc) throws Exception {

		this.oid = CommonUtil.getOIDString(sgDoc);
		this.docNo = sgDoc.getDocNo(); // 문서번호
		this.docName = sgDoc.getDocName(); // 문서명
		this.moduleCode = sgDoc.getModuleCode(); // 모듈 CODE
		this.moduleDisplay = KETDocumentUtil.moduleMap.get(this.moduleCode); // 모듈 Display
		this.refView = sgDoc.getRefView(); // 관련화면
		this.description = sgDoc.getDescription(); // 양식설명
		this.version = sgDoc.getVersion(); // 버전
		this.lastest = sgDoc.isLastest(); // 최신
		this.branchId = sgDoc.getBranchId();
		this.creatorName = sgDoc.getCreatorFullName(); // 등록자명
		this.creatorOid = CommonUtil.getOIDString((WTUser) sgDoc.getCreator().getPrincipal()); // 등록자 OID
		this.createDate = DateUtil.getDateString(sgDoc.getCreateTimestamp(), "d"); // 등록일
		this.modifyDate = DateUtil.getDateString(sgDoc.getModifyTimestamp(), "d"); // 수정일
		this.primaryDTOFile = KETContentHelper.manager.getPrimaryContent(sgDoc);
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
	 * @return the docNo
	 */
	public String getDocNo() {
		return docNo;
	}

	/**
	 * @param docNo
	 *            the docNo to set
	 */
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	/**
	 * @return the docName
	 */
	public String getDocName() {
		return docName;
	}

	/**
	 * @param docName
	 *            the docName to set
	 */
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}

	/**
	 * @param moduleCode
	 *            the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	/**
	 * @return the moduleDisplay
	 */
	public String getModuleDisplay() {
		return moduleDisplay;
	}

	/**
	 * @param moduleDisplay
	 *            the moduleDisplay to set
	 */
	public void setModuleDisplay(String moduleDisplay) {
		this.moduleDisplay = moduleDisplay;
	}

	/**
	 * @return the refView
	 */
	public String getRefView() {
		return refView;
	}

	/**
	 * @param refView
	 *            the refView to set
	 */
	public void setRefView(String refView) {
		this.refView = refView;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the lastest
	 */
	public boolean isLastest() {
		return lastest;
	}

	/**
	 * @param lastest
	 *            the lastest to set
	 */
	public void setLastest(boolean lastest) {
		this.lastest = lastest;
	}

	/**
	 * @return the branchId
	 */
	public long getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            the branchId to set
	 */
	public void setBranchId(long branchId) {
		this.branchId = branchId;
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
	 * @return the modifyDate
	 */
	public String getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate
	 *            the modifyDate to set
	 */
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public ContentDTO getPrimaryDTOFile() {
		return primaryDTOFile;
	}

	public void setPrimaryDTOFile(ContentDTO primaryDTOFile) {
		this.primaryDTOFile = primaryDTOFile;
	}

}
