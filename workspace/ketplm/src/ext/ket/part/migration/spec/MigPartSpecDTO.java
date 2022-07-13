package ext.ket.part.migration.spec;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MigPartSpecDTO {

    private String attrCode;
    private String attrName;
    private boolean sendErp;
    private String columnName;
    private String attrInputType;
    private boolean multiSelect;
    private String numberCodeTypeCode;
    private String numberCodeTypeName;
    private boolean numberType;
    private String enumASIS;
    private boolean numberingRel;
    private boolean notNull;
    private String description;
    private String comment;
    
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

    public boolean isSendErp() {
        return sendErp;
    }

    public void setSendErp(boolean sendErp) {
        this.sendErp = sendErp;
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

    public boolean isMultiSelect() {
        return multiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public String getNumberCodeTypeCode() {
        return numberCodeTypeCode;
    }

    public void setNumberCodeTypeCode(String numberCodeTypeCode) {
        this.numberCodeTypeCode = numberCodeTypeCode;
    }

    public String getNumberCodeTypeName() {
        return numberCodeTypeName;
    }

    public void setNumberCodeTypeName(String numberCodeTypeName) {
        this.numberCodeTypeName = numberCodeTypeName;
    }

    public String getEnumASIS() {
        return enumASIS;
    }

    public void setEnumASIS(String enumASIS) {
        this.enumASIS = enumASIS;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isNumberingRel() {
        return numberingRel;
    }

    public void setNumberingRel(boolean numberingRel) {
        this.numberingRel = numberingRel;
    }

    public boolean isNumberType() {
        return numberType;
    }

    public void setNumberType(boolean numberType) {
        this.numberType = numberType;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
