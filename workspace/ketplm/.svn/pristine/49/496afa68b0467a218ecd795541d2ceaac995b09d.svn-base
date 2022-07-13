package ext.ket.project.gate.entity;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.shared.dto.BaseDTO;

public class TemplateAssessSheetDTO extends BaseDTO {
    private String assessOid = "";
    private String division = "";
    private String devType = "";
    private String devDiv = "";
    private String prodCategory = "";
    private String sheetName = "";
    private String sheetDesc = "";
    private String active = "";
    private String startDate = "";
    private String endDate = "";
    private String delOids = "";
    private String partName = "";
    private String partOid = "";
    private String partMultiNames = "";
    private String partMultiOids = "";

    private String createStamp;
    private String modifyStamp;
    private String creator;
    private String isSearch;

    public TemplateAssessSheetDTO() {
    }

    public TemplateAssessSheetDTO(TemplateAssessSheet ass) {
	this.setOid(CommonUtil.getOIDString(ass));
	this.assessOid = "" + ass.getPersistInfo().getObjectIdentifier().getId();
	this.division = "" + ass.getDivision().getPersistInfo().getObjectIdentifier().getId();
	this.devType = "" + ass.getDevType().getPersistInfo().getObjectIdentifier().getId();
	if (ass.getDevDiv() != null) {
	    this.devDiv = "" + ass.getDevDiv().getPersistInfo().getObjectIdentifier().getId();
	}
	// this.prodCategory = "" + ass.getProdCategory().getPersistInfo().getObjectIdentifier().getId();
	String partOidStr = ass.getPartOid();
	if (!StringUtil.isEmpty(partOidStr)) {
	    // WTPartMaster partMaster = (WTPartMaster) CommonUtil.getObject("wt.part.WTPartMaster:" + partOidStr);
	    KETPartClassification partMaster = null;
	    if (partOidStr != null && partOidStr.indexOf("KETPartClassification") > 0) {
		partMaster = (KETPartClassification) CommonUtil.getObject(partOidStr);
	    }

	    // String className = partMaster.getPersistInfo().getObjectIdentifier().getClassname();
	    // try {
	    //
	    // ReferenceFactory rf = new ReferenceFactory();
	    // WTPart part = (WTPart) rf.getReference("VR:wt.part.WTPart:" + partOidStr).getObject();
	    // } catch (Exception e) {
	    // Kogger.error(getClass(), e);
	    // }
	    this.partOid = partOidStr;
	    if (partMaster != null) {
		this.partName = partMaster.getClassKrName();
	    }
	}

	// this.division = "" + ass.getDivision().getCode();
	// this.devType = "" + ass.getDevType().getCode();
	// this.devDiv = "" + ass.getDevDiv().getCode();
	// this.prodCategory = "" + ass.getProdCategory().getCode();
	this.sheetName = ass.getSheetName();
	this.sheetDesc = ass.getSheetDesc();
	this.active = "" + ass.getActive();
	this.createStamp = DateUtil.getDateString(ass.getPersistInfo().getCreateStamp(), "date");
	this.modifyStamp = DateUtil.getDateString(ass.getPersistInfo().getModifyStamp(), "date");
	this.creator = ass.getOwner().getFullName();
    }

    public String getPartMultiNames() {
	return partMultiNames;
    }

    public void setPartMultiNames(String partMultiNames) {
	this.partMultiNames = partMultiNames;
    }

    public String getPartMultiOids() {
	return partMultiOids;
    }

    public void setPartMultiOids(String partMultiOids) {
	this.partMultiOids = partMultiOids;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getPartOid() {
	return partOid;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public String getIsSearch() {
	return isSearch;
    }

    public void setIsSearch(String isSearch) {
	this.isSearch = isSearch;
    }

    public String getCreator() {
	return creator;
    }

    public void setCreator(String creator) {
	this.creator = creator;
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

    public String getAssessOid() {
	return assessOid;
    }

    public void setAssessOid(String assessOid) {
	this.assessOid = assessOid;
    }

    public String getCreateStamp() {
	return createStamp;
    }

    public void setCreateStamp(String createStamp) {
	this.createStamp = createStamp;
    }

    public String getModifyStamp() {
	return modifyStamp;
    }

    public void setModifyStamp(String modifyStamp) {
	this.modifyStamp = modifyStamp;
    }

    public String getDivision() {
	return division;
    }

    public void setDivision(String division) {
	this.division = division;
    }

    public String getDevType() {
	return devType;
    }

    public void setDevType(String devType) {
	this.devType = devType;
    }

    public String getDevDiv() {
	return devDiv;
    }

    public void setDevDiv(String devDiv) {
	this.devDiv = devDiv;
    }

    public String getProdCategory() {
	return prodCategory;
    }

    public void setProdCategory(String prodCategory) {
	this.prodCategory = prodCategory;
    }

    public String getSheetName() {
	return sheetName;
    }

    public void setSheetName(String sheetName) {
	this.sheetName = sheetName;
    }

    public String getSheetDesc() {
	return sheetDesc;
    }

    public void setSheetDesc(String sheetDesc) {
	this.sheetDesc = sheetDesc;
    }

    public String getDelOids() {
	return delOids;
    }

    public void setDelOids(String delOids) {
	this.delOids = delOids;
    }

    public String getActive() {
	return active;
    }

    public void setActive(String active) {
	this.active = active;
    }

}
