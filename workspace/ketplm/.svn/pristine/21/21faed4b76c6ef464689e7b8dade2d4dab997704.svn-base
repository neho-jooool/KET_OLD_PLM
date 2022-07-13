package e3ps.sap;

import ext.ket.shared.log.Kogger;

public enum ErpProdGroupCodeEnum {
    
     //제품군 재재정비로 인해 변경됨.. 2019.08.30 경영기획 강가람 대리 요청
    
    TML("K11","10"),
    HSG("K12","10"),
    PCB("K13","10"),
    ASSY("K14","10"),
    HighVoltageConnector("K15","10"),
    AutomotiveIT("K21","10"),
    MultiFuse("K28","10"),
    HighVoltageFuse("K29","10"),
    BFT("K31","10"),
    PRA("K32","23"),
    Sensor("K33","20"),
    GNSS("K34","20"),
    MultimediaUnit("K35","20"),
    ETC_Senser_ItModule("K36","20"),
    ICB_Sensing("K37","23"),
    JunctionBox("K38","23"),
    ElectricModule_Parts("K39","23"),
    WH("K41","15"),
    WHECO("K42","15"),
    SealHAWA("K51","10"),
    ETC("K91","10");
    
    /*//제품군 재정비로 인해 변경됨.. 2019.06.24 경영기획 강가람 대리/김재중 차장 요청
    
    TML("K11","10"),
    HSG("K12","10"),
    PCB("K13","10"),
    ASSY("K14","10"),
    HighVoltageConnector("K15","10"),
    AutomotiveIT("K21","10"),
    FUSE("K31","10"),
    EEModule("K32","23"),
    Sensor("K33","10"),
    ITModule("K34","20"),
    WH("K41","15"),
    WHECO("K42","15"),
    SealHAWA("K51","10"),
    Optical광("K61","22"),
    ETC("K91","10");*/

    /*Connectors("010", "10"), 
    Fuses("015", "10"),
    Cable_Assemblies("030", "15"), 
    IT_Modules("025", "20"),
    Electronic_Modules("020", "23"),
    Raw_Material("095", "50"), 
    LED_LF("080", "S1"), 
    ETC("099", "10");*/

    // ERP 영업뷰의 제품군, 기본뷰의 제품군 매핑
    
    /* 제품군1(신)	         		| 제품군(구)
     -----------------------------------------------------------------		
	                        		| 1	제품군 01_활용안함
     -----------------------------------------------------------------                        			
     010 Connectors				| 10	커넥터	
     -----------------------------------------------------------------
     015 Fuses		        		| 10	커넥터
     -----------------------------------------------------------------	
                                                | 14	콤비스위치	                        
     -----------------------------------------------------------------
     030 Cable Assemblies			| 15	WIRING ASSY	
     -----------------------------------------------------------------
     025 IT Modules				| 20	무선통신	
     -----------------------------------------------------------------
                                                | 22	광통신	                                
     -----------------------------------------------------------------
     020 Electrical & Electronic Modules	| 23	전장모듈	
     -----------------------------------------------------------------
     095 Raw Material				| 50	원자재	
     -----------------------------------------------------------------
     080 LED L/F				| S1	LEAD FRAME	
     -----------------------------------------------------------------
     099 ETC					| 10	커넥터	        */

    
    private String newCode;
    private String oldCode;

    private ErpProdGroupCodeEnum(String newCode, String oldCode) {

	this.newCode = newCode;
	this.oldCode = oldCode;
    }

    public String getNewCode() {
	return newCode;
    }

    public void setNewCode(String newCode) {
	this.newCode = newCode;
    }

    public String getOldCode() {
	return oldCode;
    }

    public void setOldCode(String oldCode) {
	this.oldCode = oldCode;
    }

    public static void main(String[] args) {
	ErpProdGroupCodeEnum[] arry = ErpProdGroupCodeEnum.values();
	for (ErpProdGroupCodeEnum item : arry) {
	    Kogger.debug(ErpProdGroupCodeEnum.class, "private String " + item.getNewCode() + ";");
	}
    }

}
