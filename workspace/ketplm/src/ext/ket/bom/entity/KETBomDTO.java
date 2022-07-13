package ext.ket.bom.entity;

import wt.part.WTPart;
import e3ps.bom.entity.KETPartUsageLink;
import ext.ket.shared.dto.BaseDTO;

public class KETBomDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String parentItemCode;
    private String childItemCode;
    private String childItemVersion;
    private String bomLevel;
    private String sequenceNumber;

    private int itemSeq;
    private String quantity;
    private String boxQuantity;
    private String unit;
    private String material;
    private String hardnessFrom;
    private String hardnessTo;
    private String orgCode;
    private String mfgFlag;

    private String startDate;
    private String endDate;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;

    private String bomVersion;
    private String versionItemCode;
    private String designDate;
    private String bomType;
    private String ida2a2;

    private WTPart parent;
    private WTPart child;

    private String childPartName;
    private String parentPartName;

    private String parentOid;
    private String childMasterOid;

    private String checkoutUserId;

    private String ecoNo;

    public KETBomDTO(KETPartUsageLink link) {
	// TODO Auto-generated constructor stub
    }

    public String getParentItemCode() {
	return parentItemCode;
    }

    public void setParentItemCode(String parentItemCode) {
	this.parentItemCode = parentItemCode;
    }

    public String getChildItemCode() {
	return childItemCode;
    }

    public void setChildItemCode(String childItemCode) {
	this.childItemCode = childItemCode;
    }

    public String getChildItemVersion() {
	return childItemVersion;
    }

    public void setChildItemVersion(String childItemVersion) {
	this.childItemVersion = childItemVersion;
    }

    public String getBomLevel() {
	return bomLevel;
    }

    public void setBomLevel(String bomLevel) {
	this.bomLevel = bomLevel;
    }

    public String getSequenceNumber() {
	return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
	this.sequenceNumber = sequenceNumber;
    }

    public int getItemSeq() {
	return itemSeq;
    }

    public void setItemSeq(int itemSeq) {
	this.itemSeq = itemSeq;
    }

    public String getQuantity() {
	return quantity;
    }

    public void setQuantity(String quantity) {
	this.quantity = quantity;
    }

    public String getBoxQuantity() {
	return boxQuantity;
    }

    public void setBoxQuantity(String boxQuantity) {
	this.boxQuantity = boxQuantity;
    }

    public String getUnit() {
	return unit;
    }

    public void setUnit(String unit) {
	this.unit = unit;
    }

    public String getMaterial() {
	return material;
    }

    public void setMaterial(String material) {
	this.material = material;
    }

    public String getHardnessFrom() {
	return hardnessFrom;
    }

    public void setHardnessFrom(String hardnessFrom) {
	this.hardnessFrom = hardnessFrom;
    }

    public String getHardnessTo() {
	return hardnessTo;
    }

    public void setHardnessTo(String hardnessTo) {
	this.hardnessTo = hardnessTo;
    }

    public String getOrgCode() {
	return orgCode;
    }

    public void setOrgCode(String orgCode) {
	this.orgCode = orgCode;
    }

    public String getMfgFlag() {
	return mfgFlag;
    }

    public void setMfgFlag(String mfgFlag) {
	this.mfgFlag = mfgFlag;
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

    public String getAttribute1() {
	return attribute1;
    }

    public void setAttribute1(String attribute1) {
	this.attribute1 = attribute1;
    }

    public String getAttribute2() {
	return attribute2;
    }

    public void setAttribute2(String attribute2) {
	this.attribute2 = attribute2;
    }

    public String getAttribute3() {
	return attribute3;
    }

    public void setAttribute3(String attribute3) {
	this.attribute3 = attribute3;
    }

    public String getAttribute4() {
	return attribute4;
    }

    public void setAttribute4(String attribute4) {
	this.attribute4 = attribute4;
    }

    public String getAttribute5() {
	return attribute5;
    }

    public void setAttribute5(String attribute5) {
	this.attribute5 = attribute5;
    }

    public String getAttribute6() {
	return attribute6;
    }

    public void setAttribute6(String attribute6) {
	this.attribute6 = attribute6;
    }

    public String getAttribute7() {
	return attribute7;
    }

    public void setAttribute7(String attribute7) {
	this.attribute7 = attribute7;
    }

    public String getAttribute8() {
	return attribute8;
    }

    public void setAttribute8(String attribute8) {
	this.attribute8 = attribute8;
    }

    public String getAttribute9() {
	return attribute9;
    }

    public void setAttribute9(String attribute9) {
	this.attribute9 = attribute9;
    }

    public String getAttribute10() {
	return attribute10;
    }

    public void setAttribute10(String attribute10) {
	this.attribute10 = attribute10;
    }

    public String getBomVersion() {
	return bomVersion;
    }

    public void setBomVersion(String bomVersion) {
	this.bomVersion = bomVersion;
    }

    public String getVersionItemCode() {
	return versionItemCode;
    }

    public void setVersionItemCode(String versionItemCode) {
	this.versionItemCode = versionItemCode;
    }

    public String getDesignDate() {
	return designDate;
    }

    public void setDesignDate(String designDate) {
	this.designDate = designDate;
    }

    public String getBomType() {
	return bomType;
    }

    public void setBomType(String bomType) {
	this.bomType = bomType;
    }

    public String getIda2a2() {
	return ida2a2;
    }

    public void setIda2a2(String ida2a2) {
	this.ida2a2 = ida2a2;
    }

    public WTPart getParent() {
	return parent;
    }

    public void setParent(WTPart parent) {
	this.parent = parent;
    }

    public WTPart getChild() {
	return child;
    }

    public void setChild(WTPart child) {
	this.child = child;
    }

    public String getChildPartName() {
	return childPartName;
    }

    public void setChildPartName(String childPartName) {
	this.childPartName = childPartName;
    }

    public String getParentPartName() {
	return parentPartName;
    }

    public void setParentPartName(String parentPartName) {
	this.parentPartName = parentPartName;
    }

    public String getParentOid() {
	return parentOid;
    }

    public void setParentOid(String parentOid) {
	this.parentOid = parentOid;
    }

    public String getChildMasterOid() {
	return childMasterOid;
    }

    public void setChildMasterOid(String childMasterOid) {
	this.childMasterOid = childMasterOid;
    }

    public String getCheckoutUserId() {
	return checkoutUserId;
    }

    public void setCheckoutUserId(String checkoutUserId) {
	this.checkoutUserId = checkoutUserId;
    }

    public String getEcoNo() {
	return ecoNo;
    }

    public void setEcoNo(String ecoNo) {
	this.ecoNo = ecoNo;
    }

}
