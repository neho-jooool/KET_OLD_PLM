package ext.ket.wfm.entity;

import wt.doc.WTDocument;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.dms.entity.KETDocumentCategory;
import e3ps.dms.entity.KETDocumentCategoryLink;
import e3ps.dms.entity.KETProjectDocument;
import e3ps.dms.entity.KETTechnicalCategoryLink;
import e3ps.dms.entity.KETTechnicalDocument;
import e3ps.wfm.entity.KETWfmApprovalMaster;
import e3ps.wfm.util.WorkflowSearchHelper;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : MyDocumentDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 16.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
/**
 * @클래스명 : MyDocumentDTO
 * @작성자 : KKW
 * @작성일 : 2014. 8. 29.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class MyDocumentDTO extends BaseDTO {

    private static final long serialVersionUID = -2241980790108936272L;

    private String documentNo;
    private String documentCategory;
    private String documentCategoryTxt;
    private String documentName;
    private String state;
    private String createDateFrom;
    private String createDateTo;
    private String createDate;
    private String creator;
    private String pjtType;
    private String pjtNo;
    private String version;
    private String partNo;
    private String primaryConentIconUrl;
    private String primaryConentDownUrl;
    private String buyerSummit;
    private String hasApprovalMaster;
    private String security;
    private String totalFlag;
    private String designTechFlag;
    private String appDataOid;
    private String fileDoownloadUrl;
    private String attribute1;
    private boolean onlyResult = false;
    private int totalSize;

    public MyDocumentDTO() {

    }

    public MyDocumentDTO(WTDocument document) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	String oid = CommonUtil.getOIDString(document);
	String documentNo = "";
	String documentCategory = "";
	String security = "";
	String designTechFlag = "";
	String appDataOid = "";
	String fileDoownloadUrl = "";
	String attribute1 = ""; // 품질표준문서번호
	if (document instanceof KETProjectDocument) {
	    documentNo = ((KETProjectDocument) document).getDocumentNo();
	    attribute1 = ((KETProjectDocument) document).getAttribute1();
	    security = ((KETProjectDocument) document).getSecurity();
	    QueryResult qr = PersistenceHelper.manager.navigate(document, KETDocumentCategoryLink.DOCUMENT_CATEGORY_ROLE,
		    KETDocumentCategoryLink.class, true);
	    if (qr.hasMoreElements()) {
		KETDocumentCategory category = (KETDocumentCategory) qr.nextElement();
		documentCategory = category.getCategoryName();
	    }
	} else if (document instanceof KETTechnicalDocument) {
	    documentNo = ((KETTechnicalDocument) document).getNumber();
	    attribute1 = ((KETTechnicalDocument) document).getAttribute1();
	    security = ((KETTechnicalDocument) document).getSecurity();
	    designTechFlag = ((KETTechnicalDocument) document).getAttribute1();
	    QueryResult qr = PersistenceHelper.manager.navigate(document, KETTechnicalCategoryLink.DOCUMENT_CATEGORY_ROLE,
		    KETTechnicalCategoryLink.class, true);
	    if (qr.hasMoreElements()) {
		KETDocumentCategory category = (KETDocumentCategory) qr.nextElement();
		documentCategory = category.getCategoryName();
	    }
	}
	designTechFlag = "".equals(designTechFlag) ? "N" : designTechFlag;
	String documentName = document.getTitle();

	String state = document.getLifeCycleState().getDisplay(messageService.getLocale());
	String version = document.getVersionIdentifier().getValue();
	String creator = (document.getIterationInfo().getCreator() == null) ? "" : document.getIterationInfo().getCreator().getFullName();
	String createDate = DateUtil.getDateString(document.getCreateTimestamp(), "d");
	ContentDTO contentDTO = KETContentHelper.manager.getPrimaryContent(document);
	appDataOid = (contentDTO == null) ? "" : contentDTO.getContentoid();
	String primaryConentIconUrl = (contentDTO == null) ? "" : contentDTO.getIconURLStr();
	String primaryConentDownUrl = (contentDTO == null) ? "" : contentDTO.getDownloadURL();
	String buyerSummit = "";
	if (document instanceof KETProjectDocument) {
	    buyerSummit = CodeHelper.manager.getCodeValue("BUYERSUMMIT",
		    StringUtil.checkReplaceStr(((KETProjectDocument) document).getIsBuyerSummit(), "2"));
	}
	KETWfmApprovalMaster approvalMaster = WorkflowSearchHelper.manager.getMaster(document);
	if (!"".equals(primaryConentDownUrl)) {
	    fileDoownloadUrl = "/plm/servlet/AttachmentsDownloadDirectionServlet?oid=OR:" + contentDTO.getHolderoid() + "&cioids="
		    + appDataOid + "&role=PRIMARY";
	}
	if ("Y".equals(designTechFlag)) {
	    primaryConentDownUrl = "<a href=javascript:fileOpenAjax('" + oid + "'" + ",'" + appDataOid + "')>" + primaryConentIconUrl
		    + "</a>";
	} else {
	    primaryConentDownUrl = "<a href=\"" + fileDoownloadUrl + "\" target=\"download\">" + primaryConentIconUrl + "</a>";
	}

	setSecurity(security);
	setOid(oid);
	setDocumentNo(documentNo);
	setDocumentCategory(documentCategory);
	setDocumentName(documentName);
	setState(state);
	setVersion(version);
	setCreator(creator);
	setCreateDate(createDate);
	setPrimaryConentIconUrl(primaryConentIconUrl);
	setPrimaryConentDownUrl(primaryConentDownUrl);
	setBuyerSummit(buyerSummit);
	setHasApprovalMaster(String.valueOf(approvalMaster != null));
	setDesignTechFlag(designTechFlag);
	setAppDataOid(appDataOid);
	setFileDoownloadUrl(fileDoownloadUrl);
	setAttribute1(attribute1);

    }

    public String getAttribute1() {
	return attribute1;
    }

    public void setAttribute1(String attribute1) {
	this.attribute1 = attribute1;
    }

    public String getSecurity() {
	return security;
    }

    public void setSecurity(String security) {
	this.security = security;
    }

    public String getDocumentNoHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    public String getDocumentNoHtmlPostfix() {
	return "</font>";
    }

    public String getTotalFlag() {
	return totalFlag;
    }

    public void setTotalFlag(String totalFlag) {
	this.totalFlag = totalFlag;
    }

    /**
     * @return the documentNo
     */
    public String getDocumentNo() {
	return documentNo;
    }

    /**
     * @param documentNo
     *            the documentNo to set
     */
    public void setDocumentNo(String documentNo) {
	this.documentNo = documentNo;
    }

    /**
     * @return the documentCategory
     */
    public String getDocumentCategory() {
	return documentCategory;
    }

    /**
     * @param documentCategory
     *            the documentCategory to set
     */
    public void setDocumentCategory(String documentCategory) {
	this.documentCategory = documentCategory;
    }

    /**
     * @return the documentCategoryTxt
     */
    public String getDocumentCategoryTxt() {
	return documentCategoryTxt;
    }

    /**
     * @param documentCategoryTxt
     *            the documentCategoryTxt to set
     */
    public void setDocumentCategoryTxt(String documentCategoryTxt) {
	this.documentCategoryTxt = documentCategoryTxt;
    }

    /**
     * @return the documentName
     */
    public String getDocumentName() {
	return documentName;
    }

    /**
     * @param documentName
     *            the documentName to set
     */
    public void setDocumentName(String documentName) {
	this.documentName = documentName;
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
     * @return the createDateFrom
     */
    public String getCreateDateFrom() {
	return createDateFrom;
    }

    /**
     * @param createDateFrom
     *            the createDateFrom to set
     */
    public void setCreateDateFrom(String createDateFrom) {
	this.createDateFrom = createDateFrom;
    }

    /**
     * @return the createDateTo
     */
    public String getCreateDateTo() {
	return createDateTo;
    }

    /**
     * @param createDateTo
     *            the createDateTo to set
     */
    public void setCreateDateTo(String createDateTo) {
	this.createDateTo = createDateTo;
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
     * @return the pjtType
     */
    public String getPjtType() {
	return pjtType;
    }

    /**
     * @param pjtType
     *            the pjtType to set
     */
    public void setPjtType(String pjtType) {
	this.pjtType = pjtType;
    }

    /**
     * @return the pjtNo
     */
    public String getPjtNo() {
	return pjtNo;
    }

    /**
     * @param pjtNo
     *            the pjtNo to set
     */
    public void setPjtNo(String pjtNo) {
	this.pjtNo = pjtNo;
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
     * @return the primaryConentIconUrl
     */
    public String getPrimaryConentIconUrl() {
	return primaryConentIconUrl;
    }

    /**
     * @param primaryConentIconUrl
     *            the primaryConentIconUrl to set
     */
    public void setPrimaryConentIconUrl(String primaryConentIconUrl) {
	this.primaryConentIconUrl = primaryConentIconUrl;
    }

    /**
     * @return the primaryConentDownUrl
     */
    public String getPrimaryConentDownUrl() {
	return primaryConentDownUrl;
    }

    /**
     * @param primaryConentDownUrl
     *            the primaryConentDownUrl to set
     */
    public void setPrimaryConentDownUrl(String primaryConentDownUrl) {
	this.primaryConentDownUrl = primaryConentDownUrl;
    }

    /**
     * @return the buyerSummit
     */
    public String getBuyerSummit() {
	return buyerSummit;
    }

    /**
     * @param buyerSummit
     *            the buyerSummit to set
     */
    public void setBuyerSummit(String buyerSummit) {
	this.buyerSummit = buyerSummit;
    }

    /**
     * @return the hasApprovalMaster
     */
    public String getHasApprovalMaster() {
	return hasApprovalMaster;
    }

    /**
     * @param hasApprovalMaster
     *            the hasApprovalMaster to set
     */
    public void setHasApprovalMaster(String hasApprovalMaster) {
	this.hasApprovalMaster = hasApprovalMaster;
    }

    public String getAppDataOid() {
	return appDataOid;
    }

    public void setAppDataOid(String appDataOid) {
	this.appDataOid = appDataOid;
    }

    public String getDesignTechFlag() {
	return designTechFlag;
    }

    public void setDesignTechFlag(String designTechFlag) {
	this.designTechFlag = designTechFlag;
    }

    public String getFileDoownloadUrl() {
	return fileDoownloadUrl;
    }

    public void setFileDoownloadUrl(String fileDoownloadUrl) {
	this.fileDoownloadUrl = fileDoownloadUrl;
    }

    public boolean isOnlyResult() {
        return onlyResult;
    }

    public void setOnlyResult(boolean onlyResult) {
        this.onlyResult = onlyResult;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
