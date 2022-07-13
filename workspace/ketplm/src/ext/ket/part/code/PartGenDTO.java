package ext.ket.part.code;

public class PartGenDTO {
    
    // 채번 코드 - Prefix - FERT
    private String numberingCode;
    private String numberingCharics;
    private String numberingMinNo;
    private String classCode;
    private String divisionFlag;
    private String classPartType;
    
    public String getNumberingCode() {
        return numberingCode;
    }
    public void setNumberingCode(String numberingCode) {
        this.numberingCode = numberingCode;
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
    
}
