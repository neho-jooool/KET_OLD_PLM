package ext.ket.dms.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import wt.fc.ObjectNoLongerExistsException;
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
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.content.entity.ContentDTO;
import ext.ket.shared.content.service.KETContentHelper;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;
import ext.ket.wfm.entity.MyDocumentDTO;

/**
 * @클래스명 : ProjectDocumentDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class ProjectDocumentDTO extends BaseDTO {

	private static final long serialVersionUID = -5486458955942508972L;

	private String documentNo;
	private String projectDocType;
	private String documentName;
	private String state;
	private String version;
	private String creator;
	private String createDate;
	private String primaryConentIconUrl;
	private String primaryConentDownUrl;
	private String buyerSummit;

	private String divisionCode;
	private String createDateFrom;
	private String createDateTo;
	private String subcontractor;
	private String pjtType;
	private String pjtNo;
	private String pjtName;
	private String partNo;
	private String documentQuality;
	private String totalFlag;
	private String dateRevision;

	private String attribute1;
	private String deptName;

	public ProjectDocumentDTO() {
	}

	public ProjectDocumentDTO(KETProjectDocument document) {

		try {
			KETMessageService messageService = KETMessageService.getMessageService();
			String oid = CommonUtil.getOIDString(document);
			String documentNo = document.getDocumentNo();
			String attribute1 = document.getAttribute1();
			String projectDocType = "";
			QueryResult qr = PersistenceHelper.manager.navigate(document, KETDocumentCategoryLink.DOCUMENT_CATEGORY_ROLE, KETDocumentCategoryLink.class, true);
			if (qr.hasMoreElements()) {
				KETDocumentCategory category = (KETDocumentCategory) qr.nextElement();
				projectDocType = category.getCategoryName();
			}
			String documentName = document.getTitle();
			String state = document.getLifeCycleState().getDisplay(messageService.getLocale());
			String version = document.getVersionIdentifier().getValue();
			String creator = (document.getIterationInfo().getCreator() == null) ? "" : document.getIterationInfo().getCreator().getFullName();
			String createDate = DateUtil.getDateString(document.getCreateTimestamp(), "d");
			ContentDTO contentDTO = KETContentHelper.manager.getPrimaryContent(document);
			String primaryConentIconUrl = "";
			String primaryConentDownUrl = "";
			if (contentDTO != null) {
				primaryConentIconUrl = contentDTO.getIconURLStr();
				primaryConentDownUrl = contentDTO.getDownURLStr();
			}
			String buyerSummit = CodeHelper.manager.getCodeValue("BUYERSUMMIT", StringUtil.checkReplaceStr(document.getIsBuyerSummit(), "2"));
			String deptName = document.getDeptName();
			
			setOid(oid);
			setDocumentNo(documentNo);
			setAttribute1(attribute1);
			setProjectDocType(projectDocType);
			setDocumentName(documentName);
			setState(state);
			setVersion(version);
			setCreator(creator);
			setCreateDate(createDate);
			setPrimaryConentIconUrl(primaryConentIconUrl);
			setPrimaryConentDownUrl(primaryConentDownUrl);
			setBuyerSummit(buyerSummit);
			setDeptName(deptName);

			if ("Y".equals(document.getAttribute8())) {
				int daily = Integer.parseInt(document.getAttribute10()) * 30;

				Timestamp pubStamp = DateUtil.convertDate2(document.getAttribute9());
				Calendar pubCal = Calendar.getInstance();
				pubCal.setTime(pubStamp);
				pubCal.add(Calendar.DATE, daily);
				Date pubDate = pubCal.getTime();

				Timestamp currentStamp = DateUtil.getCurrentTimestamp();
				Date currDate = new Date(currentStamp.getTime());

				int dr = DateUtil.getDuration(pubDate, currDate);

				String dateRevision = "";

				if (pubDate.getTime() > currDate.getTime()) {
					dateRevision = dr + "일전";
				} else {
					dateRevision = dr + "일 지연";
				}
				setDateRevision(dateRevision);

			}
		} catch (Exception e) {
			if (e instanceof ObjectNoLongerExistsException)
				Kogger.debug(getClass(), "ObjectNoLongerExistsException");
			Kogger.error(getClass(), e);
		}
	}

	public ProjectDocumentDTO(MyDocumentDTO documentDTO) {
		setProjectDocType(documentDTO.getDocumentCategory());
		setDocumentNo(documentDTO.getDocumentNo());
		setAttribute1(documentDTO.getAttribute1());
		setDocumentName(documentDTO.getDocumentName());
		setState(documentDTO.getState());
		setCreateDateFrom(documentDTO.getCreateDateFrom());
		setCreateDateTo(documentDTO.getCreateDateTo());
		setPjtType(documentDTO.getPjtType());
		setPjtNo(documentDTO.getPjtNo());
		setVersion(documentDTO.getVersion());
		setPartNo(documentDTO.getPartNo());
		setCreator(documentDTO.getCreator());
		setTotalFlag(documentDTO.getTotalFlag());
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
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
	 * @return the projectDocType
	 */
	public String getProjectDocType() {
		return projectDocType;
	}

	/**
	 * @param projectDocType
	 *            the projectDocType to set
	 */
	public void setProjectDocType(String projectDocType) {
		this.projectDocType = projectDocType;
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
	 * @return the divisionCode
	 */
	public String getDivisionCode() {
		return divisionCode;
	}

	/**
	 * @param divisionCode
	 *            the divisionCode to set
	 */
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
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
	 * @return the subcontractor
	 */
	public String getSubcontractor() {
		return subcontractor;
	}

	/**
	 * @param subcontractor
	 *            the subcontractor to set
	 */
	public void setSubcontractor(String subcontractor) {
		this.subcontractor = subcontractor;
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
	 * @return the pjtName
	 */
	public String getPjtName() {
		return pjtName;
	}

	/**
	 * @param pjtName
	 *            the pjtName to set
	 */
	public void setPjtName(String pjtName) {
		this.pjtName = pjtName;
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
	 * @return the documentQuality
	 */
	public String getDocumentQuality() {
		return documentQuality;
	}

	/**
	 * @param documentQuality
	 *            the documentQuality to set
	 */
	public void setDocumentQuality(String documentQuality) {
		this.documentQuality = documentQuality;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDateRevision() {
		return dateRevision;
	}

	public void setDateRevision(String dateRevision) {
		this.dateRevision = dateRevision;
	}

	public String getDeptName() {
	    return deptName;
	}

	public void setDeptName(String deptName) {
	    this.deptName = deptName;
	}
	
	

}
