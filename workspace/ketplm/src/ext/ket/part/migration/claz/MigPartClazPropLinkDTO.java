package ext.ket.part.migration.claz;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MigPartClazPropLinkDTO {
    
    private String clazOid;

    private String attrCode;
    private String attrName;
    private String keyCode;
    private String esse;
    private String check;
    private String row;
    private String col;

    public String getClazOid() {
        return clazOid;
    }

    public void setClazOid(String clazOid) {
        this.clazOid = clazOid;
    }

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

    public String getKeyCode() {
	return keyCode;
    }

    public void setKeyCode(String keyCode) {
	this.keyCode = keyCode;
    }

    public String getEsse() {
	return esse;
    }

    public void setEsse(String esse) {
	this.esse = esse;
    }

    public String getCheck() {
	return check;
    }

    public void setCheck(String check) {
	this.check = check;
    }

    public String getRow() {
	return row;
    }

    public void setRow(String row) {
	this.row = row;
    }

    public String getCol() {
	return col;
    }

    public void setCol(String col) {
	this.col = col;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
