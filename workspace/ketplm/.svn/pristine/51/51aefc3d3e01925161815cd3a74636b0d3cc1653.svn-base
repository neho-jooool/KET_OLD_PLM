package ext.ket.part.classify.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.cost.util.StringUtil;
import ext.ket.part.classify.oxm.ClazXmlRowObject;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartClassificationDTO;

/**
 * 데이터 구성및 변환 관련 역할
 * 
 * @클래스명 : KETPartClazBuilder
 * @작성자 : yjlee
 * @작성일 : 2014. 7. 30.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartClazBuilder {

    public PartClazBuilder() {

    }

    // PartClassificationDTO >> KETPartClassification
    public KETPartClassification buildDto2Pers(PartClassificationDTO paramObject) throws Exception {

	KETPartClassification partClaz = KETPartClassification.newKETPartClassification();

	partClaz.setClassCode(paramObject.getClassCode());
	partClaz.setClassKrName(paramObject.getClassKrName());
	partClaz.setClassEnName(paramObject.getClassEnName());
	partClaz.setClassZhName(paramObject.getClassZhName());
	partClaz.setNumberingCode(paramObject.getNumberingCode());
	partClaz.setNumberingCharics(paramObject.getNumberingCharics());
	partClaz.setNumberingMinNo(paramObject.getNumberingMinNo());
	partClaz.setSortNo(paramObject.getSortNo());
	partClaz.setUseClaz(paramObject.getUseClaz());
	partClaz.setErpProdCode(paramObject.getErpProdCode());
	partClaz.setErpClassNo(paramObject.getErpClassNo());
	partClaz.setClassPartType(paramObject.getClassPartType());
	partClaz.setEpmCode(paramObject.getEpmCode());
	partClaz.setErpProdGroupCode(paramObject.getErpProdGroupCode());
	partClaz.setPartClassificType(paramObject.getPartClassificType());
	partClaz.setCatalogueCode(paramObject.getCatalogueCode());
	partClaz.setDivisionFlag(paramObject.getDivisionFlag());

	return partClaz;
    }

    // KETPartClassification >> PartClassificationDTO
    public void buildPers2Dto(PartClassificationDTO paramObject, KETPartClassification partClassification) {

	paramObject.setOid(CommonUtil.getOIDString(partClassification));

	paramObject.setClassCode(partClassification.getClassCode());
	paramObject.setClassKrName(partClassification.getClassKrName());
	paramObject.setClassEnName(partClassification.getClassEnName());
	paramObject.setClassZhName(partClassification.getClassZhName());
	paramObject.setNumberingCode(partClassification.getNumberingCode());
	paramObject.setNumberingCharics(partClassification.getNumberingCharics());
	paramObject.setNumberingMinNo(partClassification.getNumberingMinNo());
	paramObject.setSortNo(partClassification.getSortNo());
	paramObject.setUseClaz(partClassification.isUseClaz());
	paramObject.setErpProdCode(partClassification.getErpProdCode());
	paramObject.setErpClassNo(partClassification.getErpClassNo());
	paramObject.setClassPartType(partClassification.getClassPartType());
	paramObject.setEpmCode(partClassification.getEpmCode());
	paramObject.setErpProdGroupCode(partClassification.getErpProdGroupCode());
	paramObject.setPartClassificType(partClassification.getPartClassificType());
	paramObject.setCatalogueCode(partClassification.getCatalogueCode());
	paramObject.setDivisionFlag(partClassification.getDivisionFlag());
	paramObject.setEcoCar(partClassification.getIsEcoCar());
	paramObject.setCreateStamp(DateUtil.getDateString(partClassification.getPersistInfo().getCreateStamp(), "date"));
	paramObject.setModifyStamp(DateUtil.getDateString(partClassification.getPersistInfo().getModifyStamp(), "date"));
    }

    // ResultSet >> PartClassificationDTO
    public PartClassificationDTO buildResultSet2Dto(ResultSet rs) throws SQLException {

	PartClassificationDTO dto = new PartClassificationDTO();

	dto.setOid(rs.getString("OID"));
	dto.setClazOid(rs.getString("OID"));
	dto.setParentOid(rs.getString("PARENTOID"));
	dto.setHasPart(rs.getString("HASPART"));
	dto.setTreeFullPath(rs.getString("TREEFULLPATH"));
	dto.setLeaf("1".equals(rs.getString("leaf")));

	dto.setLev(rs.getString("LEV")); // 미적용

	dto.setClassCode(rs.getString("CLASSCODE"));
	dto.setClassKrName(rs.getString("CLASSKRNAME"));
	dto.setClassEnName(rs.getString("CLASSENNAME"));
	dto.setClassZhName(rs.getString("CLASSZHNAME"));

	dto.setSortNo(Integer.parseInt(rs.getString("SORTNO")));
	dto.setUseClaz(rs.getBoolean("USECLAZ")); //

	dto.setNumberingCode(rs.getString("NUMBERINGCODE"));
	dto.setNumberingCharics(rs.getString("NUMBERINGCHARICS"));
	dto.setNumberingMinNo(rs.getString("NUMBERINGMINNO"));

	dto.setEpmCode(rs.getString("EPMCODE"));

	dto.setErpProdCode(rs.getString("ERPPRODCODE"));
	dto.setClassPartType(rs.getString("CLASSPARTTYPE"));
	dto.setErpProdGroupCode(rs.getString("ERPPRODGROUPCODE"));
	dto.setErpClassNo(rs.getString("ERPCLASSNO"));

	dto.setPartClassificType(rs.getString("PARTCLASSIFICTYPE"));
	dto.setCatalogueCode(rs.getString("catalogueCode"));
	dto.setDivisionFlag(rs.getString("divisionFlag"));

	dto.setPartAttributeName(rs.getString("partAttributeName"));

	dto.setPartNamingOid(rs.getString("partNamingOid"));
	dto.setPartNamingName(rs.getString("partNamingName"));

	dto.setCreateStamp(rs.getString("CREATESTAMPA2"));
	dto.setModifyStamp(rs.getString("MODIFYSTAMPA2"));
	dto.setEcoCar(rs.getBoolean("isEcoCar"));
	// row.setClazXmlRowObjectList(dto.getClassKrName());

	return dto;
    }

    // PartClassificationDTO >> ClazXmlRowObject
    public ClazXmlRowObject getDto2Xml(PartClassificationDTO dto) {

	ClazXmlRowObject row = new ClazXmlRowObject();

	row.setClazOid(dto.getClazOid());
	row.setParentOid(StringUtil.checkNull(dto.getParentOid()));
	row.setLev(dto.getLev());
	row.setHasPart(dto.getHasPart());
	if ("0".equals(dto.getHasPart())) {
	    if ("1".equals(dto.getLev())) {
		row.setCanDelete("0");
	    } else {
		if(dto.getLeaf()){
		    row.setCanDelete("1");
		}else{
		    row.setCanDelete("0");
		}
	    }
	} else {
	    row.setCanDelete("0");
	}
	// row.setDeleted("0");
	row.setTreeFullPath(StringUtil.checkNull(dto.getTreeFullPath()));

	row.setClassCode(StringUtil.checkNull(dto.getClassCode()));
	row.setClassKrName(dto.getClassKrName());
	row.setClassEnName(StringUtil.checkNull(dto.getClassEnName()));
	row.setClassZhName(StringUtil.checkNull(dto.getClassZhName()));

	row.setSortNo(String.valueOf(dto.getSortNo()));
	row.setUseClaz(dto.getUseClaz() ? "1" : "0");
	row.setEcoCar(dto.getEcoCar() ? "1" : "0");
	row.setNumberingCode(StringUtil.checkNull(dto.getNumberingCode()));
	row.setNumberingCharics(StringUtil.checkNull(dto.getNumberingCharics()));
	row.setNumberingMinNo(StringUtil.checkNull(dto.getNumberingMinNo()));

	row.setEpmCode(StringUtil.checkNull(dto.getEpmCode()));

	row.setErpProdCode(StringUtil.checkNull(dto.getErpProdCode()));
	row.setClassPartType(StringUtil.checkNull(dto.getClassPartType()));
	row.setErpProdGroupCode(StringUtil.checkNull(dto.getErpProdGroupCode()));
	row.setErpClassNo(StringUtil.checkNull(dto.getErpClassNo()));

	row.setPartClassificType(StringUtil.checkNull(dto.getPartClassificType()));
	row.setCatalogueCode(StringUtil.checkNull(dto.getCatalogueCode()));
	row.setDivisionFlag(StringUtil.checkNull(dto.getDivisionFlag()));

	row.setPartAttributeName(StringUtil.checkNull(dto.getPartAttributeName()));
	
	if(dto.getLeaf()){
	    row.setPartAttributeNameButton("/plm/portal/images/img_default/button/but2_list.gif");
	    row.setPartAttributeNameOnDblClickSideButton("javascript:openUpdatePartAttrPopup( Grid, Row, Col, Event,'" + dto.getClazOid() + "');");
	}	

	row.setPartNamingName(StringUtil.checkNull(dto.getPartNamingOid()));

	row.setCreateStamp(StringUtil.checkNull(dto.getCreateStamp()));
	row.setModifyStamp(StringUtil.checkNull(dto.getModifyStamp()));

	return row;
    }

    // ClazXmlRowObject >> KETPartClassification
    public void buildXml2Pers(ClazXmlRowObject row, KETPartClassification partClassification) throws Exception {

	// TODO TKLEE 테스트후 나중에 원복 바람
	//if(partClassification.getPersistInfo() == null)
	    partClassification.setClassCode(row.getClassCode());
	
	partClassification.setClassKrName(row.getClassKrName());
	partClassification.setClassEnName(row.getClassEnName());
	partClassification.setClassZhName(row.getClassZhName());

	partClassification.setSortNo(Integer.parseInt(row.getSortNo()));
	partClassification.setUseClaz("1".equals(row.getUseClaz()));

	partClassification.setNumberingCode(row.getNumberingCode());
	partClassification.setNumberingCharics(row.getNumberingCharics());
	partClassification.setNumberingMinNo(row.getNumberingMinNo());

	partClassification.setEpmCode(row.getEpmCode());

	partClassification.setErpProdCode(row.getErpProdCode());
	partClassification.setClassPartType(row.getClassPartType());
	partClassification.setErpProdGroupCode(row.getErpProdGroupCode());
	partClassification.setErpClassNo(row.getErpClassNo());

	partClassification.setPartClassificType(row.getPartClassificType());
	partClassification.setCatalogueCode(row.getCatalogueCode());
	partClassification.setDivisionFlag(row.getDivisionFlag());
	partClassification.setIsEcoCar("1".equals(row.getEcoCar()));
    }

    public static class Builder {

	private String classCode;
	private String classKrName;
	private String classEnName;
	private String classZhName;

	private String numberingCode;
	private String numberingCharics;
	private String numberingMinNo;
	
	private int sortNo;

	private boolean useClaz;

	private String erpProdCode;
	private String classPartType;
	private String epmCode;
	private String erpProdGroupCode;
	private String erpClassNo;
	
	private String partClassificType;
	private String catalogueCode;
	private String divisionFlag;

	private String parentOid;

	public Builder() {

	}

	public Builder(String classKrName) {
	    this.classKrName = classKrName;
	}

	public Builder(String classKrName, KETPartClassification parent) {
	    this.classKrName = classKrName;
	    if (parent != null)
		this.parentOid = CommonUtil.getOIDString(parent);
	}

	public Builder(String classKrName, KETPartClassification parent, boolean useClaz) {
	    this.classKrName = classKrName;
	    if (parent != null)
		this.parentOid = CommonUtil.getOIDString(parent);
	    this.useClaz = useClaz;
	}

	public Builder(String classKrName, KETPartClassification parent, boolean useClaz, String partClassificType) {
	    this.classKrName = classKrName;
	    if (parent != null)
		this.parentOid = CommonUtil.getOIDString(parent);
	    this.useClaz = useClaz;
	    this.partClassificType = partClassificType;
	}

	public Builder(boolean useClaz) {
	    this.useClaz = useClaz;
	}

	// build Property >> KETPartClassification
	public KETPartClassification build() throws Exception {

	    KETPartClassification partClaz = KETPartClassification.newKETPartClassification();

	    partClaz.setClassKrName(classCode);
	    partClaz.setClassKrName(classKrName);
	    partClaz.setClassEnName(classEnName);
	    partClaz.setClassZhName(classZhName);
	    partClaz.setNumberingCode(numberingCode);
	    partClaz.setNumberingCharics(numberingCharics);
	    partClaz.setNumberingMinNo(numberingMinNo);
	    partClaz.setSortNo(sortNo);
	    partClaz.setUseClaz(useClaz);
	    partClaz.setErpProdCode(erpProdCode);
	    partClaz.setClassPartType(classPartType);
	    partClaz.setEpmCode(epmCode);
	    partClaz.setErpProdGroupCode(erpProdGroupCode);
	    partClaz.setErpClassNo(erpClassNo);
	    partClaz.setPartClassificType(partClassificType);
	    partClaz.setCatalogueCode(catalogueCode);
	    partClaz.setDivisionFlag(divisionFlag);

	    return partClaz;
	}

	public Builder setClassCode(String classCode) {
	    this.classCode = classCode;
	    return this;
	}
	
	public Builder setClassKrName(String classKrName) {
	    this.classKrName = classKrName;
	    return this;
	}

	public Builder setClassEnName(String classEnName) {
	    this.classEnName = classEnName;
	    return this;
	}

	public Builder setClassZhName(String classZhName) {
	    this.classZhName = classZhName;
	    return this;
	}

	public Builder setNumberingCode(String numberingCode) {
	    this.numberingCode = numberingCode;
	    return this;
	}
	
	public Builder setNumberingCharics(String numberingCharics) {
	    this.numberingCharics = numberingCharics;
	    return this;
	}
	
	public Builder setNumberingMinNo(String numberingMinNo) {
	    this.numberingMinNo = numberingMinNo;
	    return this;
	}

	public Builder setSortNo(int sortNo) {
	    this.sortNo = sortNo;
	    return this;
	}

	public Builder setUseClaz(boolean useClaz) {
	    this.useClaz = useClaz;
	    return this;
	}

	public Builder setErpProdCode(String erpProdCode) {
	    this.erpProdCode = erpProdCode;
	    return this;
	}
	
	public Builder setErpClassNo(String erpClassNo) {
	    this.erpClassNo = erpClassNo;
	    return this;
	}

	public Builder setClassPartType(String classPartType) {
	    this.classPartType = classPartType;
	    return this;
	}

	public Builder setEpmCode(String epmCode) {
	    this.epmCode = epmCode;
	    return this;
	}

	public Builder setErpProdGroupCode(String erpProdGroupCode) {
	    this.erpProdGroupCode = erpProdGroupCode;
	    return this;
	}

	public Builder setPartClassificType(String partClassificType) {
	    this.partClassificType = partClassificType;
	    return this;
	}

	public Builder setParentOid(String parentOid) {
	    this.parentOid = parentOid;
	    return this;
	}

	public Builder setCatalogueCode(String catalogueCode) {
	    this.catalogueCode = catalogueCode;
	    return this;
	}
	
	public Builder setDivisionFlag(String divisionFlag) {
	    this.divisionFlag = divisionFlag;
	    return this;
	}

    }

}
