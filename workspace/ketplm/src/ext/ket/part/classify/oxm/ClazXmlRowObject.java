package ext.ket.part.classify.oxm;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "I")
public class ClazXmlRowObject {

    // /////////////////////////////////
    // added
    // /////////////////////////////////

    @XmlAttribute(name = "clazOid")
    private String clazOid;

    @XmlAttribute(name = "parentOid")
    private String parentOid;

    @XmlAttribute(name = "hasPart")
    private String hasPart;

    @XmlAttribute(name = "treeFullPath")
    private String treeFullPath;

    // level은 최상위를 처리 : 최상위1레벨
    @XmlAttribute(name = "lev")
    private String lev;

    @XmlAttribute(name = "partAttributeName")
    private String partAttributeName;

    @XmlAttribute(name = "partNamingName")
    private String partNamingName;

    // 삭제 할 수 있는 아이콘 표시 : 기본 1
    @XmlAttribute(name = "CanDelete")
    private String canDelete = "1";

    // // 삭제 할 수 있는 아이콘 표시 : 기본 0
    // @XmlAttribute(name = "Deleted")
    // private String deleted = "0";

    // 삭제 구분 : 1, 0
    @XmlAttribute(name = "deleteFlag")
    private String deleteFlag;

    // HtmlPrefix="<b><font color='#000000'>" TaskNameHtmlPostfix="</font></b>"");

    // "/plm/portal/images/img_default/button/but2_list.gif"
    // 속성 연결 버튼
    @XmlAttribute(name = "partAttributeNameButton")
    private String partAttributeNameButton;

    // "javascript:openUpdateProjectPopup('" + taskOid + "');"
    // 속성 연결 버튼 이벤트
    @XmlAttribute(name = "partAttributeNameOnDblClickSideButton")
    private String partAttributeNameOnDblClickSideButton;

    // /////////////////////////////////
    // default
    // /////////////////////////////////

    @XmlAttribute(name = "divisionFlag")
    private String divisionFlag;

    @XmlAttribute(name = "classCode")
    private String classCode;

    @XmlAttribute(name = "classKrName")
    private String classKrName;

    @XmlAttribute(name = "classEnName")
    private String classEnName;

    @XmlAttribute(name = "classZhName")
    private String classZhName;

    @XmlAttribute(name = "numberingCode")
    private String numberingCode;
    
    @XmlAttribute(name = "numberingCharics")
    private String numberingCharics;
    
    @XmlAttribute(name = "numberingMinNo")
    private String numberingMinNo;

    @XmlAttribute(name = "sortNo")
    private String sortNo;

    @XmlAttribute(name = "useClaz")
    private String useClaz;

    @XmlAttribute(name = "erpProdCode")
    private String erpProdCode;

    @XmlAttribute(name = "classPartType")
    private String classPartType;

    @XmlAttribute(name = "epmCode")
    private String epmCode;

    @XmlAttribute(name = "erpProdGroupCode")
    private String erpProdGroupCode;
    
    @XmlAttribute(name = "erpClassNo")
    private String erpClassNo;

    @XmlAttribute(name = "partClassificType")
    private String partClassificType;

    @XmlAttribute(name = "catalogueCode")
    private String catalogueCode;

    @XmlAttribute(name = "createStamp")
    private String createStamp;

    @XmlAttribute(name = "modifyStamp")
    private String modifyStamp;
    
    @XmlAttribute(name = "ecoCar")
    private String ecoCar;

    @XmlElement(name = "I", type = ClazXmlRowObject.class)
    private List<ClazXmlRowObject> clazXmlRowObjectList = new ArrayList<ClazXmlRowObject>();

    public void addClazXmlRowObject(ClazXmlRowObject clazXmlRowObject) {
	this.clazXmlRowObjectList.add(clazXmlRowObject);
    }

    public List<ClazXmlRowObject> getClazXmlRowObjectList() {
	return clazXmlRowObjectList;
    }

    public void setClazXmlRowObjectList(List<ClazXmlRowObject> clazXmlRowObjectList) {
	this.clazXmlRowObjectList = clazXmlRowObjectList;
    }

    public String getPartAttributeName() {
	return partAttributeName;
    }

    public void setPartAttributeName(String partAttributeName) {
	this.partAttributeName = partAttributeName;
    }

    public String getPartNamingName() {
	return partNamingName;
    }

    public void setPartNamingName(String partNamingName) {
	this.partNamingName = partNamingName;
    }

    public String getClazOid() {
	return clazOid;
    }

    public void setClazOid(String clazOid) {
	this.clazOid = clazOid;
    }

    public String getParentOid() {
	return parentOid;
    }

    public void setParentOid(String parentOid) {
	this.parentOid = parentOid;
    }

    public String getHasPart() {
	return hasPart;
    }

    public void setHasPart(String hasPart) {
	this.hasPart = hasPart;
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

    public String getNumberingCharics() {
        return numberingCharics;
    }

    public void setNumberingCharics(String numberingCharics) {
        this.numberingCharics = numberingCharics;
    }

    public String getTreeFullPath() {
	return treeFullPath;
    }

    public void setTreeFullPath(String treeFullPath) {
	this.treeFullPath = treeFullPath;
    }

    public String getSortNo() {
	return sortNo;
    }

    public void setSortNo(String sortNo) {
	this.sortNo = sortNo;
    }

    public String getUseClaz() {
	return useClaz;
    }

    public void setUseClaz(String useClaz) {
	this.useClaz = useClaz;
    }

    public String getErpProdCode() {
	return erpProdCode;
    }

    public void setErpProdCode(String erpProdCode) {
	this.erpProdCode = erpProdCode;
    }

    public String getClassPartType() {
        return classPartType;
    }

    public void setClassPartType(String classPartType) {
        this.classPartType = classPartType;
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

    public String getCanDelete() {
	return canDelete;
    }

    public void setCanDelete(String canDelete) {
	this.canDelete = canDelete;
    }

    public String getLev() {
	return lev;
    }

    public void setLev(String lev) {
	this.lev = lev;
    }

    public String getDeleteFlag() {
	return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
	this.deleteFlag = deleteFlag;
    }

    public String getPartAttributeNameButton() {
	return partAttributeNameButton;
    }

    public void setPartAttributeNameButton(String partAttributeNameButton) {
	this.partAttributeNameButton = partAttributeNameButton;
    }

    public String getPartAttributeNameOnDblClickSideButton() {
	return partAttributeNameOnDblClickSideButton;
    }

    public void setPartAttributeNameOnDblClickSideButton(String partAttributeNameOnDblClickSideButton) {
	this.partAttributeNameOnDblClickSideButton = partAttributeNameOnDblClickSideButton;
    }

    public String getCatalogueCode() {
	return catalogueCode;
    }

    public void setCatalogueCode(String catalogueCode) {
	this.catalogueCode = catalogueCode;
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
    
    public String getNumberingMinNo() {
        return numberingMinNo;
    }

    public void setNumberingMinNo(String numberingMinNo) {
        this.numberingMinNo = numberingMinNo;
    }

    public String getEcoCar() {
        return ecoCar;
    }

    public void setEcoCar(String ecoCar) {
        this.ecoCar = ecoCar;
    }

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
