package ext.ket.part.entity.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

final public class PartClassAttrLinkDTO implements Serializable {

    private PartAttributeDTO partAttributeDTO = new PartAttributeDTO();
    private PartClassificationDTO partClassificationDTO = new PartClassificationDTO();

    private boolean essential; // default false;
    private boolean checked; // default false;
    private int indexRow = 0;
    private int indexCol = 0;

    private boolean hpYn; // default false;
    private int indexSort = 0;

    private String partClazOid;
    private String partAttrOid;
    private String partClazAttrLinkOid;

    // value - part 할당된 실제 속성
    private String partStandardValue;

    public PartAttributeDTO getPartAttributeDTO() {
	return partAttributeDTO;
    }

    public void setPartAttributeDTO(PartAttributeDTO partAttributeDTO) {
	this.partAttributeDTO = partAttributeDTO;
    }

    public boolean getEssential() {
	return essential;
    }

    public void setEssential(boolean essential) {
	this.essential = essential;
    }

    public boolean getChecked() {
	return checked;
    }

    public void setChecked(boolean checked) {
	this.checked = checked;
    }

    public boolean getHpYn() {
	return hpYn;
    }

    public void setHpYn(boolean hpYn) {
	this.hpYn = hpYn;
    }

    public int getIndexRow() {
	return indexRow;
    }

    public void setIndexRow(int indexRow) {
	this.indexRow = indexRow;
    }

    public int getIndexCol() {
	return indexCol;
    }

    public void setIndexCol(int indexCol) {
	this.indexCol = indexCol;
    }

    public int getIndexSort() {
	return indexSort;
    }

    public void setIndexSort(int indexSort) {
	this.indexSort = indexSort;
    }

    public String getPartClazOid() {
	return partClazOid;
    }

    public void setPartClazOid(String partClazOid) {
	this.partClazOid = partClazOid;
    }

    public String getPartAttrOid() {
	return partAttrOid;
    }

    public void setPartAttrOid(String partAttrOid) {
	this.partAttrOid = partAttrOid;
    }

    public String getPartClazAttrLinkOid() {
	return partClazAttrLinkOid;
    }

    public void setPartClazAttrLinkOid(String partClazAttrLinkOid) {
	this.partClazAttrLinkOid = partClazAttrLinkOid;
    }

    public PartClassificationDTO getPartClassificationDTO() {
	return partClassificationDTO;
    }

    public void setPartClassificationDTO(PartClassificationDTO partClassificationDTO) {
	this.partClassificationDTO = partClassificationDTO;
    }

    public String getPartStandardValue() {
	return partStandardValue;
    }

    public void setPartStandardValue(String partStandardValue) {
	this.partStandardValue = partStandardValue;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
