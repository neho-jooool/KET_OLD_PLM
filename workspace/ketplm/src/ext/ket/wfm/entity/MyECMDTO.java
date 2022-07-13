package ext.ket.wfm.entity;

import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : MyECMDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 9. 30.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class MyECMDTO extends BaseDTO {

    private static final long serialVersionUID = 8706536735488687593L;
    private String	    ecmType;
    private String	    ecmNo;
    private String	    title;
    private String	    projectNo;
    private String	    projectOid;
    private String	    ecmReason;
    private String	    budget;
    private String	    creatorName;
    private String	    createDate;
    private String	    state;

    private String	    partNo;
    private String	    partOid;
    private String	    partName;
    private String	    searchPjtType;
    private String	    searchEcmNo;
    private String	    searchTitle;
    private String	    prodMoldCls;
    private String	    devYn;
    private String	    createStartDate;
    private String	    createEndDate;
    private String	    sancStateFlag;

    /**
     * @return the ecmType
     */
    public String getEcmType() {
	return ecmType;
    }

    /**
     * @param ecmType
     *            the ecmType to set
     */
    public void setEcmType(String ecmType) {
	this.ecmType = ecmType;
    }

    /**
     * @return the ecmNo
     */
    public String getEcmNo() {
	return ecmNo;
    }

    /**
     * @param ecmNo
     *            the ecmNo to set
     */
    public void setEcmNo(String ecmNo) {
	this.ecmNo = ecmNo;
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
     * @return the projectNo
     */
    public String getProjectNo() {
	return projectNo;
    }

    /**
     * @param projectNo
     *            the projectNo to set
     */
    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    /**
     * @return the projectOid
     */
    public String getProjectOid() {
	return projectOid;
    }

    /**
     * @param projectOid
     *            the projectOid to set
     */
    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    /**
     * @return the ecmReason
     */
    public String getEcmReason() {
	return ecmReason;
    }

    /**
     * @param ecmReason
     *            the ecmReason to set
     */
    public void setEcmReason(String ecmReason) {
	this.ecmReason = ecmReason;
    }

    /**
     * @return the budget
     */
    public String getBudget() {
	return budget;
    }

    /**
     * @param budget
     *            the budget to set
     */
    public void setBudget(String budget) {
	this.budget = budget;
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
     * @return the state
     */
    public String getState() {
	return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
	this.state = state;
    }

    /**
     * @return the partNo
     */
    public String getPartNo() {
	return partNo;
    }

    /**
     * @param partNo
     *            the partNo to set
     */
    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    /**
     * @return the partOid
     */
    public String getPartOid() {
	return partOid;
    }

    /**
     * @param partOid
     *            the partOid to set
     */
    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    /**
     * @return the partName
     */
    public String getPartName() {
	return partName;
    }

    /**
     * @param partName
     *            the partName to set
     */
    public void setPartName(String partName) {
	this.partName = partName;
    }

    /**
     * @return the searchPjtType
     */
    public String getSearchPjtType() {
	return searchPjtType;
    }

    /**
     * @param searchPjtType
     *            the searchPjtType to set
     */
    public void setSearchPjtType(String searchPjtType) {
	this.searchPjtType = searchPjtType;
    }

    /**
     * @return the searchEcmNo
     */
    public String getSearchEcmNo() {
	return searchEcmNo;
    }

    /**
     * @param searchEcmNo
     *            the searchEcmNo to set
     */
    public void setSearchEcmNo(String searchEcmNo) {
	this.searchEcmNo = searchEcmNo;
    }

    /**
     * @return the searchTitle
     */
    public String getSearchTitle() {
	return searchTitle;
    }

    /**
     * @param searchTitle
     *            the searchTitle to set
     */
    public void setSearchTitle(String searchTitle) {
	this.searchTitle = searchTitle;
    }

    /**
     * @return the prodMoldCls
     */
    public String getProdMoldCls() {
	return prodMoldCls;
    }

    /**
     * @param prodMoldCls
     *            the prodMoldCls to set
     */
    public void setProdMoldCls(String prodMoldCls) {
	this.prodMoldCls = prodMoldCls;
    }

    /**
     * @return the devYn
     */
    public String getDevYn() {
	return devYn;
    }

    /**
     * @param devYn
     *            the devYn to set
     */
    public void setDevYn(String devYn) {
	this.devYn = devYn;
    }

    /**
     * @return the createStartDate
     */
    public String getCreateStartDate() {
	return createStartDate;
    }

    /**
     * @param createStartDate
     *            the createStartDate to set
     */
    public void setCreateStartDate(String createStartDate) {
	this.createStartDate = createStartDate;
    }

    /**
     * @return the createEndDate
     */
    public String getCreateEndDate() {
	return createEndDate;
    }

    /**
     * @param createEndDate
     *            the createEndDate to set
     */
    public void setCreateEndDate(String createEndDate) {
	this.createEndDate = createEndDate;
    }

    /**
     * @return the sancStateFlag
     */
    public String getSancStateFlag() {
	return sancStateFlag;
    }

    /**
     * @param sancStateFlag
     *            the sancStateFlag to set
     */
    public void setSancStateFlag(String sancStateFlag) {
	this.sancStateFlag = sancStateFlag;
    }
}
