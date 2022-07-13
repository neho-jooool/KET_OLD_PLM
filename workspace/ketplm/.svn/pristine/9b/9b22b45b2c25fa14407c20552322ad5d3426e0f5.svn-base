package e3ps.sap;

import ext.ket.shared.log.Kogger;

public enum ErpPartMoldEnum {
  
  //부품(금형)번호 WTPARTMASTER wtpartnumber
  //PLM → ERP  금형부품 품번
  //IDNRK [IT_TYPE8] O BOM 구성부품
  //CHAR(18) [IDNRK] 금형ECO (금형부품 자재유형 8)
  //ex) 10625A00-001
  WtpartNumber("wtpartNumber", "IDNRK", "BOM 구성부품", "", true, 18),
  
  //금형번호 WTPARTMASTER wtpartnumber
  //PLM → ERP  금형SET 품번
  //MATNR [IT_TYPE8] O 자재 번호
  //CHAR(18) [MATNR] Die No
  //ex) 10339900
  DieNumber("dieNumber", "MATNR", "자재 번호", "", true, 18),

  //부품명 WTPART spPartNameHis
  //PLM → ERP  금형부품 품명
  //MAKTX [IT_TYPE8] O 자재내역
  //CHAR(40) [MAKTX] 금형부품의 내역
  //ex) DIE SET(UPPER)
  SpPartNameHis("spPartNameHis", "MAKTX", "자재내역", "", true, 40),

  //기본단위 WTPARTMASTER defaultUnit
  //PLM → ERP  
  //MEINS [IT_TYPE8] O 기본 단위
  //CHAR(3) [MEINS] 
  //ex) EA
  DefaultUnit("defaultUnit", "MEINS", "기본 단위", "", true, 3),

  //부품유형 WTPART spPartType
  //PLM → ERP  DIEM 고정
  //MTART [IT_TYPE8] O 자재 유형
  //CHAR(4) [MTART] DIEM 08.금형부품(KET)
  //ex) DIEM
  SpPartType("spPartType", "MTART", "자재 유형", "", true, 4),

  //ERP 제품코드 ERP 반제품코드 KETPartClassification erpProdCode
  //PLM → ERP  부품분류의 자재그룹 코드 부품유형에 따라 구분 전송
  //MATKL [IT_TYPE8] O 자재 그룹
  //CHAR(9) [MATKL] 
  //ex) 502000
  ErpProdHalbCode("erpProdHalbCode", "MATKL", "자재 그룹", "", true, 9),

  //재질(금형) WTPART spMaterDie
  //PLM → ERP  
  //ATWRT [IT_TYPE8]  재질
  //CHAR(30) [ZATWRT] S01 ~ S33
  //ex) S04
  SpMaterDie("spMaterDie", "ATWRT", "재질", "", false, 30);

    private String attrCode;
    private String erpField;
    private String erpDesc;
    private String defaultValue;
    private boolean essential;
    private int dataLength;
    
    private ErpPartMoldEnum(String attrCode, String erpField, String erpDesc, String defaultValue, boolean essential, int dataLength){
	
	this.attrCode = attrCode;
	this.erpField = erpField;
	this.erpDesc = erpDesc;
	this.defaultValue = defaultValue;
	this.essential = essential;
	this.dataLength = dataLength;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public String getErpField() {
        return erpField;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isEssential() {
        return essential;
    }

    public int getDataLength() {
        return dataLength;
    }

    public static void main(String[] args){
	ErpPartMoldEnum[] arry = ErpPartMoldEnum.values();
	for (ErpPartMoldEnum item : arry) {
	    Kogger.debug(ErpPartMoldEnum.class, "private String " + item.getAttrCode() + ";");
	}
    }


}
