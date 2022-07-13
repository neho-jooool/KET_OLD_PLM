package ext.ket.shared.code.entity;

import java.util.Locale;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeType;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : NumberCodeDTO
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class NumberCodeDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -4275478821324182172L;

    private NumberCodeType codetype = null;
    private NumberCode numbercode = null;
    private NumberCode parentCode = null;
    private String valueKO = "";
    private String valueEN = "";
    private String valueZH = "";
    private String valueJA = "";
    private String code = "";
    private String pcode = "";
    private String sorting = "";
    private String vendorCode = "";
    private String loid = "";
    private String parentOid = "";
    private String parentLoid = "";
    private String description = "";
    // added by tklee
    private String abbr = "";

    public NumberCodeDTO() {

    }

    public NumberCodeDTO(NumberCode _numbercode) {
	super();
	this.numbercode = _numbercode;
	this.parentCode = _numbercode.getParent();
	this.code = StringUtil.checkNull(_numbercode.getCode());
	this.codetype = _numbercode.getCodeType();
	this.sorting = StringUtil.checkNull(_numbercode.getSorting());
	if (_numbercode != null) {
	    setOid(CommonUtil.getOIDString(_numbercode));
	    this.loid = String.valueOf(CommonUtil.getOIDLongValue(_numbercode));
	}
	if (parentCode != null) {
	    this.parentOid = CommonUtil.getOIDString(parentCode);
	    this.parentLoid = String.valueOf(CommonUtil.getOIDLongValue(parentCode));
	    this.pcode = parentCode.getCode();
	}
	this.vendorCode = StringUtil.checkNull(_numbercode.getVenderCode());
	this.description = StringUtil.checkNull(_numbercode.getDescription());
    }

    public NumberCodeDTO(Object[] obj) {
	super();
	this.code = StringUtil.checkNull(String.valueOf(obj[1]));
    }

    public String getDisplay() {
	return getDisplay(Locale.KOREAN);
    }

    public String getDisplay(Locale locale) {

	String value = "";
	if (Locale.KOREAN.equals(locale))
	    value = getValueKO();
	else if (Locale.ENGLISH.equals(locale))
	    value = getValueEN();
	else if (Locale.SIMPLIFIED_CHINESE.equals(locale))
	    value = getValueZH();
	else if (Locale.JAPANESE.equals(locale))
	    value = getValueJA();
	else
	    value = getValueKO();
	return value;
    }

    public NumberCode getNumbercode() {
	return numbercode;
    }

    public void setNumbercode(NumberCode numbercode) {
	this.numbercode = numbercode;
    }

    public String getValueKO() {
	return valueKO;
    }

    public void setValueKO(String valueKO) {
	this.valueKO = valueKO;
    }

    public String getValueEN() {
	return valueEN;
    }

    public void setValueEN(String valueEN) {
	this.valueEN = valueEN;
    }

    public String getValueZH() {
	return valueZH;
    }

    public void setValueZH(String valueZH) {
	this.valueZH = valueZH;
    }

    public String getValueJA() {
	return valueJA;
    }

    public void setValueJA(String valueJA) {
	this.valueJA = valueJA;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    public NumberCodeType getCodetype() {
	return codetype;
    }

    public void setCodetype(NumberCodeType codetype) {
	this.codetype = codetype;
    }

    public NumberCode getParentCode() {
	return parentCode;
    }

    public void setParentCode(NumberCode parentCode) {
	this.parentCode = parentCode;
    }

    public String getSorting() {
	return sorting;
    }

    public void setSorting(String sorting) {
	this.sorting = sorting;
    }

    public String getPcode() {
	return pcode;
    }

    public void setPcode(String pcode) {
	this.pcode = pcode;
    }

    public String getVendorCode() {
	return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
	this.vendorCode = vendorCode;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public void setParentOid(String parentOid) {
	this.parentOid = parentOid;
    }

    public String getParentOid() {
	return parentOid;
    }

    public String getLoid() {
	return loid;
    }

    public void setLoid(String loid) {
	this.loid = loid;
    }

    public String getParentLoid() {
	return parentLoid;
    }

    public void setParentLoid(String parentLoid) {
	this.parentLoid = parentLoid;
    }

    public String getAbbr() {
	return abbr;
    }

    public void setAbbr(String abbr) {
	this.abbr = abbr;
    }

}
