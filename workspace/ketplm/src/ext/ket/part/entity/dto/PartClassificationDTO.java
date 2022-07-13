package ext.ket.part.entity.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ext.ket.part.classify.service.internal.PartClazBuilder;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.shared.dto.BaseDTO;

final public class PartClassificationDTO extends BaseDTO {

    // ///////////////////////
    // 추가
    // ///////////////////////

    private String lev;
    private String parentOid;
    private String clazOid;

    private String partAttributeName;
    private String partNamingOid;
    private String partNamingName;
    private String treeFullPath;
    private boolean leaf;
    private String hasPart;

    // //////////////////////
    // 기본
    // //////////////////////

    private String classCode;
    
    private String classKrName;
    private String classEnName;
    private String classZhName;

    private String numberingCode;
    private String numberingCharics;
    private String numberingMinNo;
    private int sortNo;
    private String catalogueCode;

    private boolean useClaz;

    private String erpProdCode;
    private String classPartType;
    private String epmCode;
    private String erpProdGroupCode;
    private String erpClassNo;
    private String partClassificType;
    private String divisionFlag;

    private String createStamp;
    private String modifyStamp;
    private boolean ecoCar;

    public boolean getEcoCar() {
	return ecoCar;
    }

    public void setEcoCar(boolean ecoCar) {
        this.ecoCar = ecoCar;
    }

    private List<PartClassificationDTO> childList = new ArrayList<PartClassificationDTO>();

    public PartClassificationDTO() {
    }

    public PartClassificationDTO(KETPartClassification partClassification) {
	new PartClazBuilder().buildPers2Dto(this, partClassification);
    }

    public String getPartNamingName() {
	return partNamingName;
    }

    public void setPartNamingName(String partNamingName) {
	this.partNamingName = partNamingName;
    }

    public String getPartAttributeName() {
	return partAttributeName;
    }

    public void setPartAttributeName(String partAttributeName) {
	this.partAttributeName = partAttributeName;
    }

    public String getPartNamingOid() {
	return partNamingOid;
    }

    public void setPartNamingOid(String partNamingOid) {
	this.partNamingOid = partNamingOid;
    }

    public String getTreeFullPath() {
	return treeFullPath;
    }

    public void setTreeFullPath(String treeFullPath) {
	this.treeFullPath = treeFullPath;
    }

    public String getParentOid() {
	return parentOid;
    }

    public void setParentOid(String parentOid) {
	this.parentOid = parentOid;
    }

    public String getClassKrName() {
	return classKrName;
    }

    public void setClassKrName(String classKrName) {
	this.classKrName = classKrName;
    }

    public String getClassEnName() {
	return classEnName;
    }

    public void setClassEnName(String classEnName) {
	this.classEnName = classEnName;
    }

    public String getClassZhName() {
	return classZhName;
    }

    public void setClassZhName(String classZhName) {
	this.classZhName = classZhName;
    }

    public String getNumberingCode() {
	return numberingCode;
    }

    public void setNumberingCode(String numberingCode) {
	this.numberingCode = numberingCode;
    }

    public int getSortNo() {
	return sortNo;
    }

    public void setSortNo(int sortNo) {
	this.sortNo = sortNo;
    }

    public boolean getUseClaz() {
	return useClaz;
    }

    public void setUseClaz(boolean useClaz) {
	this.useClaz = useClaz;
    }

    public String getErpProdCode() {
	return erpProdCode;
    }

    public void setErpProdCode(String erpProdCode) {
	this.erpProdCode = erpProdCode;
    }

    public String getEpmCode() {
	return epmCode;
    }

    public void setEpmCode(String epmCode) {
	this.epmCode = epmCode;
    }

    public String getErpProdGroupCode() {
	return erpProdGroupCode;
    }

    public void setErpProdGroupCode(String erpProdGroupCode) {
	this.erpProdGroupCode = erpProdGroupCode;
    }

    public String getPartClassificType() {
	return partClassificType;
    }

    public void setPartClassificType(String partClassificType) {
	this.partClassificType = partClassificType;
    }

    public List<PartClassificationDTO> getChildList() {
	return childList;
    }

    public void setChildList(List<PartClassificationDTO> childList) {
	this.childList = childList;
    }

    public String getClazOid() {
	return clazOid;
    }

    public void setClazOid(String clazOid) {
	this.clazOid = clazOid;
    }

    public String getLev() {
	return lev;
    }

    public void setLev(String lev) {
	this.lev = lev;
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

    public String getHasPart() {
	return hasPart;
    }

    public void setHasPart(String hasPart) {
	this.hasPart = hasPart;
    }

    public String getCatalogueCode() {
	return catalogueCode;
    }

    public void setCatalogueCode(String catalogueCode) {
	this.catalogueCode = catalogueCode;
    }

    public boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }
    
    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDivisionFlag() {
        return divisionFlag;
    }

    public void setDivisionFlag(String divisionFlag) {
        this.divisionFlag = divisionFlag;
    }

    public String getErpClassNo() {
        return erpClassNo;
    }

    public void setErpClassNo(String erpClassNo) {
        this.erpClassNo = erpClassNo;
    }

    public String getClassPartType() {
        return classPartType;
    }

    public void setClassPartType(String classPartType) {
        this.classPartType = classPartType;
    }
    
    public String getNumberingCharics() {
        return numberingCharics;
    }

    public void setNumberingCharics(String numberingCharics) {
        this.numberingCharics = numberingCharics;
    }

    public String getNumberingMinNo() {
        return numberingMinNo;
    }

    public void setNumberingMinNo(String numberingMinNo) {
        this.numberingMinNo = numberingMinNo;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
