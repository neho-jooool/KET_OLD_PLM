package ext.ket.shared.suggest.entity;

import ext.ket.shared.dto.BaseDTO;

/**
 * @클래스명 : KETSuggestDTO
 * @작성자 : Administrator
 * @작성일 : 2014. 7. 1.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
/**
 * 
 * @클래스명 : KETSuggestDTO
 * @작성자 : admin
 * @작성일 : 2021. 5. 13.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class KETSuggestDTO extends BaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = -3041577795706748385L;

    public static final String SUGGEST_USER = "USER";
    public static final String SUGGEST_USER_ALL = "USER_ALL";
    public static final String SUGGEST_DEPARTMENT = "DEPARTMENT";
    public static final String SUGGEST_DIENO = "DIENO";
    public static final String SUGGEST_REVIEWPROJNO = "REVIEWPROJNO";
    public static final String SUGGEST_PRODUCTPROJNO = "PRODUCTPROJNO";
    public static final String SUGGEST_WORKPROJNO = "WORKPROJNO";
    public static final String SUGGEST_MOLDPROJNO = "MOLDPROJNO";
    public static final String SUGGEST_CARTYPE = "CARTYPE";
    public static final String SUGGEST_PROJECTDOCTYPE = "PROJECTDOCTYPE";
    public static final String SUGGEST_TECHDOCTYPE = "TECHDOCTYPE";
    public static final String SUGGEST_CUSTOMER = "CUSTOMER";
    public static final String SUGGEST_PARTNO = "PARTNO";
    public static final String SUGGEST_PARTCLAZ = "PARTCLAZ";
    public static final String SUGGEST_PARTCLAZ_2LVL = "PART2LVLCLAZ";
    public static final String SUGGEST_EPMNO = "EPMNO";
    public static final String SUGGEST_PRODUCTTYPE = "PRODUCTTYPE";
    public static final String SUGGEST_PRODUCTPARTNO = "PRODUCTPARTNO";
    public static final String SUGGEST_ECONO = "ECONO";
    public static final String SUGGEST_ECNNO = "ECNNO";
    public static final String SUGGEST_ECRNO = "ECRNO";
    public static final String SUGGEST_CUSTOMEREVENT = "CUSTOMEREVENT";
    public static final String SUGGEST_USERDEPT = "USERDEPT";
    public static final String SUGGEST_DEPTUSER = "DEPTUSER";
    public static final String SUGGEST_SPSERIES = "SPSERIES";
    public static final String SUGGEST_KETTAG = "KETTAG";

    private String suggestType;
    private String inputParam;
    private String resultStr;
    private String resultHidStr;
    private String wtPartColumn;
    private String wtPartColumnValue;
    private String wtPartNoLike;

    /**
     * @return the suggestType
     */
    public String getSuggestType() {
	return suggestType;
    }

    /**
     * @param suggestType
     *            the suggestType to set
     */
    public void setSuggestType(String suggestType) {
	this.suggestType = suggestType;
    }

    /**
     * @return the inputParam
     */
    public String getInputParam() {
	return inputParam;
    }

    /**
     * @param inputParam
     *            the inputParam to set
     */
    public void setInputParam(String inputParam) {
	this.inputParam = inputParam;
    }

    /**
     * @return the resultStr
     */
    public String getResultStr() {
	return resultStr;
    }

    /**
     * @param resultStr
     *            the resultStr to set
     */
    public void setResultStr(String resultStr) {
	this.resultStr = resultStr;
    }

    /**
     * @return the resultHidStr
     */
    public String getResultHidStr() {
	return resultHidStr;
    }

    /**
     * @param resultHidStr
     *            the resultHidStr to set
     */
    public void setResultHidStr(String resultHidStr) {
	this.resultHidStr = resultHidStr;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public String getWtPartColumn() {
	return wtPartColumn;
    }

    public void setWtPartColumn(String wtPartColumn) {
	this.wtPartColumn = wtPartColumn;
    }

    public String getWtPartNoLike() {
	return wtPartNoLike;
    }

    public void setWtPartNoLike(String wtPartNoLike) {
	this.wtPartNoLike = wtPartNoLike;
    }

    public String getWtPartColumnValue() {
	return wtPartColumnValue;
    }

    public void setWtPartColumnValue(String wtPartColumnValue) {
	this.wtPartColumnValue = wtPartColumnValue;
    }

}
