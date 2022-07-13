package e3ps.sap;

import ext.ket.shared.log.Kogger;


public enum ErpPartDieEnum {

  //부품번호 WTPARTMASTER wtpartnumber
  //PLM → ERP  
  //DDCNO [IT_TYPE7] O 금형번호
  //CHAR(8) [ZDDCT] 금형ECO (금형DIE NO 생성 자재유형 7 )
  //ex) 10339900
  WtpartNumber("wtpartNumber", "DDCNO", "금형번호", "", true, 8),

  //사급구분 WTPART spMContractSAt
  //PLM → ERP  LOV 코드
  //GUBN1 [IT_TYPE7] O 사급구분
  //CHAR(1) [ZGBN1] 1 (사내), 2 (외주)
  //ex) 2
  SpMContractSAt("spMContractSAt", "GUBN1", "사급구분", "", true, 1),

  //금형구분 WTPART spMMoldAt
  //PLM → ERP  LOV 코드
  //GUBN2 [IT_TYPE7] O 금형구분
  //CHAR(1) [ZGBN2] 1 (프레스), 2 (사출), 3 (기타)
  //ex) 1
  SpMMoldAt("spMMoldAt", "GUBN2", "금형구분", "", true, 1),

  //제작구분 WTPART spMMakingAt
  //PLM → ERP  LOV 코드
  //GUBN3 [IT_TYPE7] O 제작구분
  //CHAR(1) [ZGBN3] 1 (자체), 2 (OEM), 3 (대여), 4 (Yazaki),  5 (타사금형), 9 (기타)
  //ex) 1
  SpMMakingAt("spMMakingAt", "GUBN3", "제작구분", "", true, 1),

  //생산구분 WTPART spMProdAt
  //PLM → ERP  LOV 코드
  //GUBN4 [IT_TYPE7] O 생산구분
  //CHAR(1) [ZGBN4] 1 (SET), 2 (추가), 3 (Modify)
  //ex) 1
  SpMProdAt("spMProdAt", "GUBN4", "생산구분", "", true, 1),

  //구매담당자 WTPART spPuchaseGroup
  //PLM → ERP  사용자 이름
  //EKGRP [IT_TYPE7] O 구매 그룹
  //CHAR(3) [EKGRP] PLM에 구매그룹 정보가 있나요? USER가 안다면 CHECK 만.. 필요하다면 펑션추가
  //ex) PH0
  SpPuchaseGroup("spPuchaseGroup", "EKGRP", "구매 그룹", "", true, 3),

  //플랜트 WTPART spPlant
  //PLM → ERP  LOV 코드
  //WERKS [IT_TYPE7] O 플랜트
  //CHAR(4) [WERKS_D] 
  //ex) 1000
  SpPlant("spPlant", "WERKS", "플랜트", "", true, 4),

  //개발담당자 WTPART spDevManNm
  //PLM → ERP  사용자 이름
  //DEVNM [IT_TYPE7]  개발담당자명
  //CHAR(10) [ZDENM] 
  //ex) 
  SpDevManNm("spDevManNm", "DEVNM", "개발담당자명", "", false, 10),

  //금형담당자 WTPART spDesignManNm
  //PLM → ERP  사용자 이름
  //PLNNM [IT_TYPE7]  설계담당자명
  //CHAR(10) [ZPLNM] 
  //ex) 송유선
  SpDesignManNm("spDesignManNm", "PLNNM", "설계담당자명", "", false, 10),

  //제작담당자 금형제작처 WTPART spManufacManNm
  //PLM → ERP  사급구분이 사내일 경우 제작담당자, 사급구분이 외주일 경우 금형제작처
  //WOKNM [IT_TYPE7]  제작담당자명
  //CHAR(10) [ZWKNM] 
  //ex) 김영근
  SpManufacManNm("spManufacManNm", "WOKNM", "제작담당자명", "", false, 10),

  //목표 C/T (SPM) WTPART spObjectiveCT
  //PLM → ERP  
  //SPMCT [IT_TYPE7]  표준 SPM(C/T)
  //CHAR(10) [ZSPCT] 
  //ex) 
  SpObjectiveCT("spObjectiveCT", "SPMCT", "표준 SPM(C/T)", "", false, 10),

  //Cavity WTPART spCavityStd
  //PLM → ERP  
  //SAVTY [IT_TYPE7]  표준 Cavity
  //NUMC(4) [ZSVTY_C] 
  //ex) 
  SpCavityStd("spCavityStd", "SAVTY", "표준 Cavity", "", false, 4),
  
  // 목표 C/T (SPM) WTPART spObjectiveCT
  //PLM → ERP  
  //DEV_SPMCT [IT_TYPE7]  개발 SPM(C/T)
  //CHAR(10) [ZSPCT] 
  //ex) 
  SpDEVObjectiveCT("spDEVObjectiveCT", "DEV_SPMCT", "개발 SPM(C/T)", "", false, 10),

  //Cavity WTPART spCavityStd
  //PLM → ERP  
  //DEV_SAVTY [IT_TYPE7]  개발 Cavity
  //NUMC(4) [ZSVTY_C] 
  //ex) 
  SpDEVCavityStd("spDEVCavityStd", "DEV_SAVTY", "개발 Cavity", "", false, 4);

    private String attrCode;
    private String erpField;
    private String erpDesc;
    private String defaultValue;
    private boolean essential;
    private int dataLength;
    
    private ErpPartDieEnum(String attrCode, String erpField, String erpDesc, String defaultValue, boolean essential, int dataLength){
	
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
	ErpPartDieEnum[] arry = ErpPartDieEnum.values();
	for (ErpPartDieEnum item : arry) {
	    Kogger.debug(ErpPartDieEnum.class, "private String " + item.getAttrCode() + ";");
	}
    }

    

}
