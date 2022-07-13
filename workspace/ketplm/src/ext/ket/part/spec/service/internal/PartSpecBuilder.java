package ext.ket.part.spec.service.internal;

import e3ps.common.util.CommonUtil;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.dto.PartAttributeDTO;
import ext.ket.part.util.PartSpecEnum;

public class PartSpecBuilder {

    public KETPartAttribute buildDto2Pers(PartAttributeDTO paramObject) throws Exception {

	KETPartAttribute partAttr = KETPartAttribute.newKETPartAttribute();

	partAttr.setAttrCode(paramObject.getAttrCode());
	partAttr.setAttrInputType(paramObject.getAttrInputType());
	partAttr.setAttrCodeType(paramObject.getAttrCodeType());
	partAttr.setAttrMultiSelect(paramObject.getAttrMultiSelect());
	partAttr.setAttrName(paramObject.getAttrName());
	partAttr.setAttrOotbName(paramObject.getAttrOotbName());
	partAttr.setAttrDesc(paramObject.getAttrDesc());
	partAttr.setColumnName(paramObject.getColumnName());
	partAttr.setErpDesc(paramObject.getErpDesc());
	partAttr.setReceiveErp(paramObject.getReceiveErp());
	partAttr.setSendErp(paramObject.getSendErp());
	partAttr.setUseNumbering(paramObject.getUseNumbering());

	return partAttr;
    }

    public PartAttributeDTO buildPers2Dto(KETPartAttribute partAttr, PartAttributeDTO paramObject) throws Exception {

	paramObject.setAttrCode(partAttr.getAttrCode());
	PartSpecEnum partSpecEnum = PartSpecEnum.toPartSpecEnumByAttrCode(partAttr.getAttrCode());
	
	paramObject.setAttrInputType(partSpecEnum.getAttrInputType());
	paramObject.setAttrCodeType(partSpecEnum.getAttrCodeType());
	paramObject.setAttrMultiSelect(partSpecEnum.getAttrMultiSelect());
	paramObject.setAttrName(partSpecEnum.getAttrName());
	paramObject.setAttrOotbName(partSpecEnum.getAttrName());
	paramObject.setAttrDesc("");
	paramObject.setColumnName(partSpecEnum.getColumnName());
	paramObject.setErpDesc(partSpecEnum.getErpDesc());
	paramObject.setReceiveErp(partSpecEnum.getSendReceiveErp());
	paramObject.setUseNumbering(partSpecEnum.getUseNumbering());
	
	paramObject.setPartAttrOid(CommonUtil.getOIDString(partAttr));

	return paramObject;
    }
    
    public KETPartAttribute buildPers2Pers(KETPartAttribute partAttr, KETPartAttribute changeAttr) throws Exception {
	
	changeAttr.setAttrCode(partAttr.getAttrCode());
	changeAttr.setAttrInputType(partAttr.getAttrInputType());
	changeAttr.setAttrCodeType(partAttr.getAttrCodeType());
	changeAttr.setAttrMultiSelect(partAttr.isAttrMultiSelect());
	changeAttr.setAttrName(partAttr.getAttrName());
	changeAttr.setAttrOotbName(partAttr.getAttrOotbName());
	changeAttr.setAttrDesc(partAttr.getAttrDesc());
	changeAttr.setColumnName(partAttr.getColumnName());
	changeAttr.setErpDesc(partAttr.getErpDesc());
	changeAttr.setReceiveErp(partAttr.isReceiveErp());
	changeAttr.setSendErp(partAttr.isSendErp());
	changeAttr.setUseNumbering(partAttr.isUseNumbering());
	
	return changeAttr;
    }

    public static class Builder {

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

	public Builder() {

	}

	public KETPartAttribute build() throws Exception {

	    KETPartAttribute partAttr = KETPartAttribute.newKETPartAttribute();

	    partAttr.setAttrCode(attrCode);
	    partAttr.setAttrInputType(attrInputType);
	    partAttr.setAttrCodeType(attrCodeType);
	    partAttr.setAttrMultiSelect(attrMultiSelect);
	    partAttr.setAttrName(attrName);
	    partAttr.setAttrOotbName(attrOotbName);
	    partAttr.setAttrDesc(attrDesc);
	    partAttr.setColumnName(columnName);
	    partAttr.setErpDesc(erpDesc);
	    partAttr.setReceiveErp(receiveErp);
	    partAttr.setSendErp(sendErp);
	    partAttr.setUseNumbering(useNumbering);

	    return partAttr;
	}

	public Builder setAttrCode(String attrCode) {
	    this.attrCode = attrCode;
	    return this;
	}
	
	public Builder setAttrCodeType(String attrCodeType) {
	    this.attrCodeType = attrCodeType;
	    return this;
	}

	public Builder setAttrName(String attrName) {
	    this.attrName = attrName;
	    return this;
	}

	public Builder setAttrOotbName(String attrOotbName) {
	    this.attrOotbName = attrOotbName;
	    return this;
	}

	public Builder setColumnName(String columnName) {
	    this.columnName = columnName;
	    return this;
	}

	public Builder setAttrInputType(String attrInputType) {
	    this.attrInputType = attrInputType;
	    return this;
	}

	public Builder setAttrDesc(String attrDesc) {
	    this.attrDesc = attrDesc;
	    return this;
	}

	public Builder setSendErp(boolean sendErp) {
	    this.sendErp = sendErp;
	    return this;
	}

	public Builder setReceiveErp(boolean receiveErp) {
	    this.receiveErp = receiveErp;
	    return this;
	}

	public Builder setErpDesc(String erpDesc) {
	    this.erpDesc = erpDesc;
	    return this;
	}

	public Builder setUseNumbering(boolean useNumbering) {
	    this.useNumbering = useNumbering;
	    return this;
	}
	
	public Builder setAttrMultiSelect(boolean attrMultiSelect) {
	    this.attrMultiSelect = attrMultiSelect;
	    return this;
	}
	
    }

}
