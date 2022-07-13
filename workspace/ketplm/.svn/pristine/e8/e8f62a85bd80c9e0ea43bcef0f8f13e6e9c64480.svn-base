package ext.ket.wfm.entity;

import wt.content.FormatContentHolder;
import e3ps.common.content.ContentInfo;
import e3ps.common.content.ContentUtil;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.ProjectOutputData;
import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : ProjectOutputDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 7. 17.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class ProjectOutputDTO extends BaseDTO {

    private static final long serialVersionUID = 6321257567896151216L;

    private String	    pjtOid;
    private String	    pjtNo;
    private String	    pjtName;

    private String	    taskOid;
    private String	    taskName;

    private String	    outputOid;
    private String	    outputName;
    private String	    objType;
    private String	    location;
    private String	    creatorName;
    private String	    version;
    private String	    state;
    private String	    createDate;
    private String	    fileIconUrl;
    private String	    deleteIconUrl;
    private String	    templateLinkUrl;
    private String	    completion;

    public ProjectOutputDTO() {

    }

    public ProjectOutputDTO(ProjectOutput output) throws Exception {

	KETMessageService messageService = KETMessageService.getMessageService();
	ProjectOutputData data = new ProjectOutputData(output);

	setPjtOid(CommonUtil.getOIDString(data.project));
	setPjtNo(data.project.getPjtNo());
	setPjtName(data.project.getPjtName());

	setTaskOid(CommonUtil.getOIDString(data.task));
	setTaskName(data.task.getTaskName());

	setOutputOid(data.oid);
	setOutputName(data.name);
	setObjType(data.objType);
	setLocation(data.locationStr);
	setCreatorName((data.LastDocument == null) ? "&nbsp;" : data.LastDocument.getCreator().getFullName());
	setVersion((data.LastDocument == null) ? "&nbsp;" : data.LastDocument.getVersionIdentifier().getValue());
	setState((data.LastDocument == null) ? "&nbsp;" : data.LastDocument.getLifeCycleState().getDisplay(messageService.getLocale()));
	setCreateDate((data.LastDocument == null) ? "&nbsp;" : DateUtil.getDateString(data.LastDocument.getPersistInfo().getCreateStamp(), "d"));
	setCompletion(String.valueOf(output.getCompletion()));

	if (data.LastDocument != null) {
	    ContentInfo content = ContentUtil.getPrimaryContent((FormatContentHolder) data.LastDocument);
	    if (content != null)
		setFileIconUrl(content.getIconURLStr());
	    else
		setFileIconUrl("&nbsp;");
	} else {
	    setFileIconUrl("&nbsp;");
	}
	setDeleteIconUrl("<a href=\"JavaScript:deleteOutput('" + data.oid + "')\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width=\"13\" height=\"12\" border=\"0\"></p></a>");
	// TODO 템플릿 링크 URL 연결해야함.
	setTemplateLinkUrl("&nbsp;");

    }

    /**
     * @return the pjtOid
     */
    public String getPjtOid() {
	return pjtOid;
    }

    /**
     * @param pjtOid
     *            the pjtOid to set
     */
    public void setPjtOid(String pjtOid) {
	this.pjtOid = pjtOid;
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
     * @return the taskOid
     */
    public String getTaskOid() {
	return taskOid;
    }

    /**
     * @param taskOid
     *            the taskOid to set
     */
    public void setTaskOid(String taskOid) {
	this.taskOid = taskOid;
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
     * @return the outputOid
     */
    public String getOutputOid() {
	return outputOid;
    }

    /**
     * @param outputOid
     *            the outputOid to set
     */
    public void setOutputOid(String outputOid) {
	this.outputOid = outputOid;
    }

    /**
     * @return the outputName
     */
    public String getOutputName() {
	return outputName;
    }

    /**
     * @param outputName
     *            the outputName to set
     */
    public void setOutputName(String outputName) {
	this.outputName = outputName;
    }

    /**
     * @return the objType
     */
    public String getObjType() {
	return objType;
    }

    /**
     * @param objType
     *            the objType to set
     */
    public void setObjType(String objType) {
	this.objType = objType;
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
     * @return the fileIconUrl
     */
    public String getFileIconUrl() {
	return fileIconUrl;
    }

    /**
     * @param fileIconUrl
     *            the fileIconUrl to set
     */
    public void setFileIconUrl(String fileIconUrl) {
	this.fileIconUrl = fileIconUrl;
    }

    /**
     * @return the deleteIconUrl
     */
    public String getDeleteIconUrl() {
	return deleteIconUrl;
    }

    /**
     * @param deleteIconUrl
     *            the deleteIconUrl to set
     */
    public void setDeleteIconUrl(String deleteIconUrl) {
	this.deleteIconUrl = deleteIconUrl;
    }

    /**
     * @return the templateLinkUrl
     */
    public String getTemplateLinkUrl() {
	return templateLinkUrl;
    }

    /**
     * @param templateLinkUrl
     *            the templateLinkUrl to set
     */
    public void setTemplateLinkUrl(String templateLinkUrl) {
	this.templateLinkUrl = templateLinkUrl;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the completion
     */
    public String getCompletion() {
	return completion;
    }

    /**
     * @param completion
     *            the completion to set
     */
    public void setCompletion(String completion) {
	this.completion = completion;
    }

}
