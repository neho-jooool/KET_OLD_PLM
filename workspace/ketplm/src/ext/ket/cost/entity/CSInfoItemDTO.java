package ext.ket.cost.entity;

import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import ext.ket.cost.util.CostUtil;

/*********************************************************
 * @description
 * @author dhkim
 * @date 2018. 1. 4. 오전 9:45:12
 * @Pakage ext.ket.cost.entity
 * @class NMetalCSInfoItemDTO
 *********************************************************/
public class CSInfoItemDTO {

	private String itemType;
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String value5;
	private String mUnit;
	private String value1CodeName;
	private String value2CodeName;
	private String value3CodeName;
	private String value4CodeName;
	private String value5CodeName;
	private String mUnitCodeName;
	private NumberCode value1Code;
	private NumberCode value2Code;
	private NumberCode value3Code;
	private NumberCode value4Code;
	private NumberCode value5Code;
	private NumberCode mUnitCode;
	private CSInfoItem object;

	// private String value3CodeName;

	/**
	 * @param item
	 */
	public CSInfoItemDTO(CSInfoItem item) {

		this.itemType = item.getItemType();
		this.value1 = item.getValue1();
		this.value2 = item.getValue2();
		this.value3 = item.getValue3();
		this.value4 = item.getValue4();
		this.value5 = item.getValue5();
		this.mUnit = item.getMUnit();
		this.object = item;

		this.mUnitCode = NumberCodeHelper.manager.getNumberCode("MONETARYUNIT", this.mUnit);
		if (this.mUnitCode != null)
			this.mUnitCodeName = this.mUnitCode.getName();

		switch (this.itemType) {
		case CostUtil.NMETALCOST:

			// 시나리오, LME 기준, 금액, 화폐단위

			this.value1Code = NumberCodeHelper.manager.getNumberCode("SCENARIO", this.value1);
			if (this.value1Code != null)
				this.value1CodeName = this.value1Code.getName();

			break;
		case CostUtil.PLATINGCOST:

			// 두께, 금액, 화폐단위

			this.value1Code = NumberCodeHelper.manager.getNumberCode("RAWMATTHICKNESS", this.value1);
			if (this.value1Code != null)
				this.value1CodeName = this.value1Code.getName();

			break;
		case CostUtil.CUTTINGCOST:

			// 폭, 두께, 금액, 화폐단위

			this.value1Code = NumberCodeHelper.manager.getNumberCode("RAWMATWIDTH", this.value1);
			if (this.value1Code != null)
				this.value1CodeName = this.value1Code.getName();

			this.value2Code = NumberCodeHelper.manager.getNumberCode("RAWMATTHICKNESS", this.value2);
			if (this.value2Code != null)
				this.value2CodeName = this.value2Code.getName();

			break;
		case CostUtil.SCRAPRECYCLECOST:

			this.value1Code = NumberCodeHelper.manager.getNumberCode("PLATING", this.value1);
			if (this.value1Code != null)
				this.value1CodeName = this.value1Code.getName();

			this.value4Code = NumberCodeHelper.manager.getNumberCode("MFTFACTORY", this.value4);
			if (this.value4Code != null)
				this.value4CodeName = this.value4Code.getName();

			break;
		case CostUtil.PROFITCOST:

			this.value1Code = NumberCodeHelper.manager.getNumberCode("RESINSDIVISION", this.value1);
			if (this.value1Code != null)
				this.value1CodeName = this.value1Code.getName();

			this.value3Code = NumberCodeHelper.manager.getNumberCode("MFTFACTORY", this.value3);
			if (this.value3Code != null)
				this.value3CodeName = this.value3Code.getName();

			break;
		case CostUtil.EXRATECOST:

			this.value1Code = NumberCodeHelper.manager.getNumberCode("MONETARYUNIT", this.value1);
			if (this.value1Code != null)
				this.value1CodeName = this.value1Code.getName();

			break;

		case CostUtil.SCRAPSALESCOST:

			// 시나리오, 생산국, 선도금사양, 스크랩판매비, 화폐단위

			this.value5Code = NumberCodeHelper.manager.getNumberCode("SCENARIO", this.value5);
			if (this.value5Code != null)
				this.value5CodeName = this.value5Code.getName();

			this.value1Code = NumberCodeHelper.manager.getNumberCode("MFTFACTORY", this.value1);
			if (this.value1Code != null)
				this.value1CodeName = this.value1Code.getName();

			this.value2Code = NumberCodeHelper.manager.getNumberCode("PLATING", this.value2);
			if (this.value2Code != null)
				this.value2CodeName = this.value2Code.getName();

			break;
		default:
			break;
		}
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType
	 *            the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(CSInfoItem object) {
		this.object = object;
	}

	/**
	 * @return the value1
	 */
	public String getValue1() {
		return value1;
	}

	/**
	 * @param value1
	 *            the value1 to set
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

	/**
	 * @return the value2
	 */
	public String getValue2() {
		return value2;
	}

	/**
	 * @param value2
	 *            the value2 to set
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	/**
	 * @return the value3
	 */
	public String getValue3() {
		return value3;
	}

	/**
	 * @param value3
	 *            the value3 to set
	 */
	public void setValue3(String value3) {
		this.value3 = value3;
	}

	/**
	 * @return the value1CodeName
	 */
	public String getValue1CodeName() {
		return value1CodeName;
	}

	/**
	 * @param value1CodeName
	 *            the value1CodeName to set
	 */
	public void setValue1CodeName(String value1CodeName) {
		this.value1CodeName = value1CodeName;
	}

	/**
	 * @return the value2CodeName
	 */
	public String getValue2CodeName() {
		return value2CodeName;
	}

	/**
	 * @param value2CodeName
	 *            the value2CodeName to set
	 */
	public void setValue2CodeName(String value2CodeName) {
		this.value2CodeName = value2CodeName;
	}

	/**
	 * @return the object
	 */
	public CSInfoItem getObject() {
		return object;
	}

	/**
	 * @return the value1Code
	 */
	public NumberCode getValue1Code() {
		return value1Code;
	}

	/**
	 * @param value1Code
	 *            the value1Code to set
	 */
	public void setValue1Code(NumberCode value1Code) {
		this.value1Code = value1Code;
	}

	/**
	 * @return the value2Code
	 */
	public NumberCode getValue2Code() {
		return value2Code;
	}

	/**
	 * @param value2Code
	 *            the value2Code to set
	 */
	public void setValue2Code(NumberCode value2Code) {
		this.value2Code = value2Code;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue4CodeName() {
		return value4CodeName;
	}

	public void setValue4CodeName(String value4CodeName) {
		this.value4CodeName = value4CodeName;
	}

	public NumberCode getValue4Code() {
		return value4Code;
	}

	public void setValue4Code(NumberCode value4Code) {
		this.value4Code = value4Code;
	}

	public String getValue3CodeName() {
		return value3CodeName;
	}

	public void setValue3CodeName(String value3CodeName) {
		this.value3CodeName = value3CodeName;
	}

	public NumberCode getValue3Code() {
		return value3Code;
	}

	public void setValue3Code(NumberCode value3Code) {
		this.value3Code = value3Code;
	}

	/**
	 * @return the value5
	 */
	public String getValue5() {
		return value5;
	}

	/**
	 * @param value5
	 *            the value5 to set
	 */
	public void setValue5(String value5) {
		this.value5 = value5;
	}

	/**
	 * @return the value5CodeName
	 */
	public String getValue5CodeName() {
		return value5CodeName;
	}

	/**
	 * @param value5CodeName
	 *            the value5CodeName to set
	 */
	public void setValue5CodeName(String value5CodeName) {
		this.value5CodeName = value5CodeName;
	}

	/**
	 * @return the value5Code
	 */
	public NumberCode getValue5Code() {
		return value5Code;
	}

	/**
	 * @param value5Code
	 *            the value5Code to set
	 */
	public void setValue5Code(NumberCode value5Code) {
		this.value5Code = value5Code;
	}

	/**
	 * @return the mUnit
	 */
	public String getmUnit() {
		return mUnit;
	}

	/**
	 * @param mUnit
	 *            the mUnit to set
	 */
	public void setmUnit(String mUnit) {
		this.mUnit = mUnit;
	}

	/**
	 * @return the mUnitCodeName
	 */
	public String getmUnitCodeName() {
		return mUnitCodeName;
	}

	/**
	 * @param mUnitCodeName
	 *            the mUnitCodeName to set
	 */
	public void setmUnitCodeName(String mUnitCodeName) {
		this.mUnitCodeName = mUnitCodeName;
	}

	/**
	 * @return the mUnitCode
	 */
	public NumberCode getmUnitCode() {
		return mUnitCode;
	}

	/**
	 * @param mUnitCode
	 *            the mUnitCode to set
	 */
	public void setmUnitCode(NumberCode mUnitCode) {
		this.mUnitCode = mUnitCode;
	}

}
