package ext.ket.project.gate.entity;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.dto.BaseDTO;

public class AssessSheetDTO extends BaseDTO {
    private String division = "";
    private String devType = "";
    private String devDiv = "";
    // private String prodCategory = "";
    private String sheetName = "";
    private String sheetDesc = "";
    private String isActive = "";
    private String delOids = "";
    private String createStamp;
    private String modifyStamp;
    private String reviseCause;
    private String pjtOid;
    private String partOid;
    private String partName;

    public AssessSheetDTO() {
    }

    public AssessSheetDTO(AssessSheet ass) {
	this.setOid(CommonUtil.getOIDString(ass));
	this.division = "" + ass.getDivision().getPersistInfo().getObjectIdentifier().getId();
	this.devType = "" + ass.getDevType().getPersistInfo().getObjectIdentifier().getId();
	this.devDiv = "" + ass.getDevDiv().getPersistInfo().getObjectIdentifier().getId();
	// this.prodCategory = "" + ass.getProdCategory().getPersistInfo().getObjectIdentifier().getId();
	this.sheetName = ass.getSheetName();
	this.sheetDesc = ass.getSheetDesc();
	String partOidStr = ass.getPartOid();
	if (!StringUtil.isEmpty(partOidStr)) {
	    // WTPartMaster partMaster = (WTPartMaster) CommonUtil.getObject("wt.part.WTPartMaster:" + partOidStr);
	    // KETPartClassification partMaster = (KETPartClassification) CommonUtil.getObject(partOidStr);
	    this.partOid = partOidStr;
	    // this.partName = partMaster.getClassKrName();
	    this.partName = "";
	}
	this.isActive = "" + ass.getActive();
	this.createStamp = DateUtil.getDateString(ass.getPersistInfo().getCreateStamp(), "date");
	this.modifyStamp = DateUtil.getDateString(ass.getPersistInfo().getModifyStamp(), "date");
    }

    public String getPartOid() {
	return partOid;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getPjtOid() {
	return pjtOid;
    }

    public void setPjtOid(String pjtOid) {
	this.pjtOid = pjtOid;
    }

    public String getReviseCause() {
	return reviseCause;
    }

    public void setReviseCause(String reviseCause) {
	this.reviseCause = reviseCause;
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

    public String getIsActive() {
	return isActive;
    }

    public void setIsActive(String isActive) {
	this.isActive = isActive;
    }

    public String getDelOids() {
	return delOids;
    }

    public void setDelOids(String delOids) {
	this.delOids = delOids;
    }

}
