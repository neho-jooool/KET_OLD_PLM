package ext.ket.part.entity.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ext.ket.part.util.PartSpecEnum;

final public class PartAttributeDTO implements Serializable {

    //==========추가된 속성============
    private boolean selectedPartAttr = false; // 기본 false
    
    private PartSpecEnum partSpecEnum;
    
    //===========기본속성=============
    private String partAttrOid;

    private String attrCode;
    private String attrName;
    private String attrOotbName;
    private String columnName;
    private String attrInputType;
    private String attrCodeType;
    private boolean attrMultiSelect;
    private String attrDesc;
    private boolean sendErp;
    private boolean receiveErp;
    private String erpDesc;
    private boolean useNumbering;

    public String getAttrCode() {
	return attrCode;
    }

    public void setAttrCode(String attrCode) {
	this.attrCode = attrCode;
    }

    public String getAttrName() {
	return attrName;
    }

    public void setAttrName(String attrName) {
	this.attrName = attrName;
    }

    public String getAttrOotbName() {
	return attrOotbName;
    }

    public void setAttrOotbName(String attrOotbName) {
	this.attrOotbName = attrOotbName;
    }

    public String getColumnName() {
	return columnName;
    }

    public void setColumnName(String columnName) {
	this.columnName = columnName;
    }

    public String getAttrInputType() {
	return attrInputType;
    }

    public void setAttrInputType(String attrInputType) {
	this.attrInputType = attrInputType;
    }

    public String getAttrDesc() {
	return attrDesc;
    }

    public void setAttrDesc(String attrDesc) {
	this.attrDesc = attrDesc;
    }

    public boolean getSendErp() {
	return sendErp;
    }

    public void setSendErp(boolean sendErp) {
	this.sendErp = sendErp;
    }

    public boolean getReceiveErp() {
	return receiveErp;
    }

    public void setReceiveErp(boolean receiveErp) {
	this.receiveErp = receiveErp;
    }

    public String getErpDesc() {
	return erpDesc;
    }

    public void setErpDesc(String erpDesc) {
	this.erpDesc = erpDesc;
    }

    public boolean getUseNumbering() {
	return useNumbering;
    }

    public void setUseNumbering(boolean useNumbering) {
	this.useNumbering = useNumbering;
    }

    public String getPartAttrOid() {
	return partAttrOid;
    }

    public void setPartAttrOid(String partAttrOid) {
	this.partAttrOid = partAttrOid;
    }

    public boolean getSelectedPartAttr() {
        return selectedPartAttr;
    }

    public void setSelectedPartAttr(boolean selectedPartAttr) {
        this.selectedPartAttr = selectedPartAttr;
    }
    
    public PartSpecEnum getPartSpecEnum() {
        return PartSpecEnum.toPartSpecEnumByAttrCode(this.attrCode);
    }

    public void setPartSpecEnum(PartSpecEnum partSpecEnum) {
        this.partSpecEnum = partSpecEnum;
    }
    
    public String getAttrCodeType() {
        return attrCodeType;
    }

    public void setAttrCodeType(String attrCodeType) {
        this.attrCodeType = attrCodeType;
    }
    
    public boolean getAttrMultiSelect() {
        return attrMultiSelect;
    }

    public void setAttrMultiSelect(boolean attrMultiSelect) {
        this.attrMultiSelect = attrMultiSelect;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
