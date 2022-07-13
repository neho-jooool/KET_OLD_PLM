package ext.ket.shared.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;

public abstract class BaseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String oid;
    private int rowNum;
    private int perPage;
    private int page;
    private int formPage;
    private int offSet;
    private int limit;
    private int totalCount;
    private String command;
    private String sortName;
    private String loginUserName;
    private String loginUserId;
    private MultipartFile primaryFile;
    private List<MultipartFile> secondaryFiles;
    private String[] secondaryFileOids;
    private String[] fileDescriptions;

    private MultipartFile iFileImg;

    private MultipartFile iFile2D;

    private MultipartFile iFile3D;

    private MultipartFile iFileStep;

    private MultipartFile iFileIgs;

    public MultipartFile getiFileImg() {
	return iFileImg;
    }

    public void setiFileImg(MultipartFile iFileImg) {
	this.iFileImg = iFileImg;
    }

    public MultipartFile getiFile2D() {
	return iFile2D;
    }

    public void setiFile2D(MultipartFile iFile2D) {
	this.iFile2D = iFile2D;
    }

    public MultipartFile getiFile3D() {
	return iFile3D;
    }

    public void setiFile3D(MultipartFile iFile3D) {
	this.iFile3D = iFile3D;
    }

    public MultipartFile getiFileStep() {
	return iFileStep;
    }

    public void setiFileStep(MultipartFile iFileStep) {
	this.iFileStep = iFileStep;
    }

    public MultipartFile getiFileIgs() {
	return iFileIgs;
    }

    public void setiFileIgs(MultipartFile iFileIgs) {
	this.iFileIgs = iFileIgs;
    }

    /**
     * EJS grid row color 속성
     */
    @JsonProperty("NoColor")
    private String noColor = "2";
    /**
     * EJS grid 삭제 속성 비활성화
     */
    @JsonProperty("CanDelete")
    private String canDelete = "0";
    /**
     * EJS grid에 selection radio 활성화 비활성화 옵션
     */
    @JsonProperty("CanSelect")
    private String canSelect = "1";

    public int getRowNum() {
	return rowNum;
    }

    public void setRowNum(int rowNum) {
	this.rowNum = rowNum;
    }

    public int getTotalCount() {
	return totalCount;
    }

    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }

    public MultipartFile getPrimaryFile() {
	return primaryFile;
    }

    public void setPrimaryFile(MultipartFile primaryFile) {
	this.primaryFile = primaryFile;
    }

    public List<MultipartFile> getSecondaryFiles() {
	return secondaryFiles;
    }

    public void setSecondaryFiles(List<MultipartFile> secondaryFiles) {
	this.secondaryFiles = secondaryFiles;
    }

    public int getOffSet() {
	return offSet;
    }

    public void setOffSet(int offSet) {
	this.offSet = offSet;
    }

    public int getLimit() {
	return limit;
    }

    public void setLimit(int limit) {
	this.limit = limit;
    }

    public String getLoginUserName() throws Exception {
	this.loginUserName = CommonUtil.getUsernameFromSession();
	return this.loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
	this.loginUserName = loginUserName;
    }

    public String getLoginUserId() throws Exception {
	this.loginUserId = CommonUtil.getUserIDFromSession();
	return this.loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
	this.loginUserId = loginUserId;
    }

    public String getSortName() {
	if (!StringUtil.isTrimToEmpty(sortName)) {
	    if (sortName.equals("createStamp") || sortName.equals("modifyStamp")) {
		return "thePersistInfo." + sortName;
	    } else if (sortName.equals("-createStamp") || sortName.equals("-modifyStamp")) {
		return "-thePersistInfo." + sortName.substring(1);
	    }
	}
	return sortName;
    }

    public void setSortName(String sortName) {
	this.sortName = sortName;
    }

    public int getPerPage() {
	return perPage;
    }

    public void setPerPage(int perPage) {
	this.perPage = perPage;
    }

    public int getPage() {
	return page;
    }

    public void setPage(int page) {
	this.page = page;
    }

    public int getFormPage() {
	return formPage;
    }

    public void setFormPage(int formPage) {
	this.formPage = formPage;
    }

    public String getOid() {
	return oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public String getNoColor() {
	return noColor;
    }

    public void setNoColor(String noColor) {
	this.noColor = noColor;
    }

    public String getCanDelete() {
	return canDelete;
    }

    public void setCanDelete(String canDelete) {
	this.canDelete = canDelete;
    }

    /**
     * @return the secondaryFileOids
     */
    public String[] getSecondaryFileOids() {
	return secondaryFileOids;
    }

    /**
     * @param secondaryFileOids
     *            the secondaryFileOids to set
     */
    public void setSecondaryFileOids(String[] secondaryFileOids) {
	this.secondaryFileOids = secondaryFileOids;
    }

    /**
     * @return the canSelect
     */
    public String getCanSelect() {
	return canSelect;
    }

    /**
     * @param canSelect
     *            the canSelect to set
     */
    public void setCanSelect(String canSelect) {
	this.canSelect = canSelect;
    }

    /**
     * @return the command
     */
    public String getCommand() {
	return command;
    }

    /**
     * @param command
     *            the command to set
     */
    public void setCommand(String command) {
	this.command = command;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    protected String getHtmlPrefix() {
	return "<font color='" + PropertiesUtil.getSearchGridLinkColor() + "' style='cursor:hand'>";
    }

    protected String getHtmlPostfix() {
	return "</font>";
    }
    
    public String[] getFileDescriptions() {
        return fileDescriptions;
    }

    public void setFileDescriptions(String[] fileDescriptions) {
        this.fileDescriptions = fileDescriptions;
    }
}
