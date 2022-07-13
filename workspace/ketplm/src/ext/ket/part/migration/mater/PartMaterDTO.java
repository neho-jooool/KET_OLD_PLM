package ext.ket.part.migration.mater;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PartMaterDTO implements Serializable {

    private String partNo;
    private String partName;
    private String partRevision;
    private String partAppState;
    private String partType;
    private String partMaterGroup;
    private String partOwner;
    private String partSujiGubun;
    private String partMaterMaker;
    private String partMaterType;
    private String partMaterGrade;
    private String partMaterCharacteristics;

    public String getPartNo() {
	return partNo;
    }

    public void setPartNo(String partNo) {
	this.partNo = partNo;
    }

    public String getPartName() {
	return partName;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public String getPartRevision() {
	return partRevision;
    }

    public void setPartRevision(String partRevision) {
	this.partRevision = partRevision;
    }

    public String getPartAppState() {
	return partAppState;
    }

    public void setPartAppState(String partAppState) {
	this.partAppState = partAppState;
    }

    public String getPartType() {
	return partType;
    }

    public void setPartType(String partType) {
	this.partType = partType;
    }

    public String getPartMaterGroup() {
	return partMaterGroup;
    }

    public void setPartMaterGroup(String partMaterGroup) {
	this.partMaterGroup = partMaterGroup;
    }

    public String getPartOwner() {
	return partOwner;
    }

    public void setPartOwner(String partOwner) {
	this.partOwner = partOwner;
    }

    public String getPartSujiGubun() {
	return partSujiGubun;
    }

    public void setPartSujiGubun(String partSujiGubun) {
	this.partSujiGubun = partSujiGubun;
    }

    public String getPartMaterMaker() {
	return partMaterMaker;
    }

    public void setPartMaterMaker(String partMaterMaker) {
	this.partMaterMaker = partMaterMaker;
    }

    public String getPartMaterType() {
	return partMaterType;
    }

    public void setPartMaterType(String partMaterType) {
	this.partMaterType = partMaterType;
    }

    public String getPartMaterGrade() {
	return partMaterGrade;
    }

    public void setPartMaterGrade(String partMaterGrade) {
	this.partMaterGrade = partMaterGrade;
    }

    public String getPartMaterCharacteristics() {
	return partMaterCharacteristics;
    }

    public void setPartMaterCharacteristics(String partMaterCharacteristics) {
	this.partMaterCharacteristics = partMaterCharacteristics;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
