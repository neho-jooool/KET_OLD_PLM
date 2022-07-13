package e3ps.sap;

import ext.ket.shared.log.Kogger;


public enum ErpPartDieHalbEnum {

  ///////////////////////////////////////////////////////////////////////////////////////////
  // 매핑자재 - 반제품의 매핑자재
  ///////////////////////////////////////////////////////////////////////////////////////////
  
  //금형번호 WTPARTMASTER wtpartnumber
  //PLM → ERP  제품일 경우 대표금형, 금형일 경우 금형번호
  //DDCNO [IT_TYPE7I]  금형번호
  //CHAR(8) [ZDDCT] 금형번호
  //ex) 
  DieNumber("dieNumber", "DDCNO", "금형번호", "", false, 8),

  //부품번호 WTPARTMASTER wtpartnumber
  //PLM → ERP  제품일 경우 부품번호, 금형일 경우 링크 부품번호
  //MATNR [IT_TYPE7I]  자재 번호
  //CHAR(18) [MATNR] 매핑자재
  //ex) 
  HalbNuumber("halbNumber", "MATNR", "자재 번호", "", false, 18);

    private String attrCode;
    private String erpField;
    private String erpDesc;
    private String defaultValue;
    private boolean essential;
    private int dataLength;
    
    private ErpPartDieHalbEnum(String attrCode, String erpField, String erpDesc, String defaultValue, boolean essential, int dataLength){
	
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
	ErpPartDieHalbEnum[] arry = ErpPartDieHalbEnum.values();
	for (ErpPartDieHalbEnum item : arry) {
	    Kogger.debug(ErpPartDieHalbEnum.class, "private String " + item.getAttrCode() + ";");
	}
    }

    

}
