package e3ps.sap;

import ext.ket.shared.log.Kogger;

public enum ErpPartProdEnum {

    // 부품번호 WTPARTMASTER wtpartnumber
    // PLM → ERP
    // MATNR [IT_TYPE0] O 자재 번호
    // CHAR(18) [MATNR] 제품ECO 승인시(제품유형 1~6)
    // ex) 615598
    WtpartNumber("wtpartNumber", "MATNR", "자재 번호", "", true, 18),

    // 부품명 WTPART spPartNameHis
    // PLM → ERP
    // MAKTX [IT_TYPE0] O 자재내역
    // CHAR(40) [MAKTX]
    // ex) 0407 26F HOUSING TEST
    SpPartNameHis("spPartNameHis", "MAKTX", "자재내역", "", true, 40),

    // 부품유형 WTPART spPartType
    // PLM → ERP LOV 변환 필요(AsIs Lov때문)
    // MTART [IT_TYPE0] O 자재 유형
    // CHAR(4) [MTART] FERT: 01.완제품(KET), HALB: 02.반제품(KET), HAWA: 03.상품(KET), ROH: 04.원자재(KET), PACK: 05.포장재(KET), SCRP: 06.스크랩(KET)
    // ex) FERT
    SpPartType("spPartType", "MTART", "자재 유형", "", true, 4),

    // 사업부 KETPartClassification divisionFlag
    // PLM → ERP 부품분류 사업부 자동차: 1, 전자: 2, 케이이티솔루션 3
    // BUSCD [IT_TYPE0] 사업부구분
    // CHAR(1) [] 1.자동차, 2 전가, 3케이티솔루션 시리즈 입력시 필수
    // ex)
    DivisionFlag("divisionFlag", "BUSCD", "사업부구분", "", false, 1),

    // ERP 제품코드 ERP 반제품코드 KETPartClassification erpProdCode
    // PLM → ERP 부품분류의 자재그룹 코드 부품유형에 따라 구분 전송
    // MATKL [IT_TYPE0] O 자재 그룹
    // CHAR(9) [MATKL]
    // ex) 106010
    ErpProdHalbCode("erpProdHalbCode", "MATKL", "자재 그룹", "", true, 9),

    // 기본단위 WTPARTMASTER defaultUnit
    // PLM → ERP
    // MEINS [IT_TYPE0] O 기본 단위
    // CHAR(3) [MEINS]
    // ex) EA
    DefaultUnit("defaultUnit", "MEINS", "기본 단위", "", true, 3),

    // 제품군 KETPartClassification erpProdGroupCode
    // PLM → ERP 부품분류의 제품군 코드
    // SPART [IT_TYPE0] 제품군
    // CHAR(2) [SPART] 자재유형 FERT,HAWA 는 필수
    // ex) 10
    ErpProdGroupCode("erpProdGroupCode", "SPART", "제품군", "", false, 2),

    // 총중량 WTPART spTotalWeight
    // PLM → ERP
    // BRGEW [IT_TYPE0] 총중량
    // CHAR(20) []
    // ex) 10.846
    SpTotalWeight("spTotalWeight", "BRGEW", "총중량", "", false, 20),

    // 순중량 WTPART spNetWeight
    // PLM → ERP
    // NTGEW [IT_TYPE0] 순 중량
    // CHAR(20) []
    // ex) 9.036
    SpNetWeight("spNetWeight", "NTGEW", "순 중량", "", false, 20),

    // 스크랩중량 WTPART spScrabWeight
    // PLM → ERP
    // SCGEW [IT_TYPE0] 스크립중량
    // CHAR(20) []
    // ex)
    SpScrabWeight("spScrabWeight", "SCGEW", "스크립중량", "", false, 20),

    // 중량단위 WTPART spWeightUnit
    // PLM → ERP [G] Default 전송
    // GEWEI [IT_TYPE0] 중량 단위
    // CHAR(3) [GEWEI]
    // ex) G
    SpWeightUnit("spWeightUnit", "GEWEI", "중량 단위", "G", false, 3),

    // 제품 Size WTPART spProdSize
    // PLM → ERP
    // GROES [IT_TYPE0] 크기/치수
    // CHAR(32) [GROES]
    // ex)
    SpProdSize("spProdSize", "GROES", "크기/치수", "", false, 32),

    // 스크랩 코드 WTPART spScrabCode
    // PLM → ERP 스크랩 품번
    // ZSCRP [IT_TYPE0] 스크랩 코드
    // CHAR(18) [ZSCRP] 비철의 경우 필수 임.
    // ex)
    SpScrabCode("spScrabCode", "ZSCRP", "스크랩 코드", "", false, 18),

    // 시리즈 WTPART spSeries
    // PLM → ERP LOV 코드
    // MVGR1 [IT_TYPE0] Series No
    // CHAR(3) [MVGR1] 시리즈No 입력을위한 플랜트, 판매조직,유통경로 등 추가 로 입력받을수 있음. 시리즈과 같이있는 항목(용도,필수 용량은 기본 값900 기타로 넣기로함)
    // ex) 653
    SpSeries("spSeries", "MVGR1", "Series No", "", false, 3),

    // ERP
    // PLM 제품분류에 따른 ERP 제품군 코드 return
    // MVGR2 [IT_TYPE0] 제품군1
    // CHAR(3) [MVGR2] 010: Connector , 015: Fuse ...
    // ex)
    SpProductFamily("spProductFamily", "MVGR2", "제품군1", "", false, 3),

    // 친환경 WTPART SpEnvFriend
    // PLM → ERP LOV 코드
    // MVGR3 [IT_TYPE0] 친환경
    // CHAR(3) [MVGR3] 400: Yes , 410: No
    // ex)
    SpEnvFriend("spEnvFriend", "MVGR3", "친환경", "", false, 3),

    // 방수여부 WTPART spWaterProof
    // PLM → ERP LOV 코드
    // MVGR4 [IT_TYPE0] 방수구분
    // CHAR(3) [MVGR4] 100: 방수 , 200: 비방수
    // ex)
    SpWaterProof("spWaterProof", "MVGR4", "방수구분", "", false, 3),

    // 표준커넥터 WTPART spStandardConnect
    // PLM → ERP LOV 코드
    // MVGR4 [IT_TYPE0] 방수구분
    // CHAR(3) [MVGR5] 100: 방수 , 200: 비방수
    // ex)
    SpStandardConnect("spStandardConnect", "MVGR5", "용도", "", false, 3),

    // Class No KETPartClassification erpClassNo
    // PLM → ERP 부품분류의 Class No
    // CLASS [IT_TYPE0] 클래스 번호
    // CHAR(18) [KLASSE_D] CLASS번호
    // ex) RD_01
    ErpClassNo("erpClassNo", "CLASS", "클래스 번호", "", false, 18),

    // 작성일 createstampa2
    // PLM → ERP YYYY.MM.DD
    // RD_01_01 [IT_TYPE0] 개발일자
    // CHAR(30) [] YYYY.MM.DD(형식으로)
    // ex) 19970722
    Createstampa2("createstampa2", "RD_01_01", "개발일자", "", false, 30),

    // 재질(비철) 재질(금형) WTPART spMaterialInfo
    // PLM → ERP 비철, 금형에 따라 별도 LOV (수지는 Interface 대상 아님)
    // RD_01_02 [IT_TYPE0] 재질
    // CHAR(30) [] RD_01 일경우는 TEXT 입력 ZSS 비철 자재의 경우 SELECT
    // ex) PBT(GP-1000S)
    SpMaterialInfo("spMaterialInfo", "RD_01_02", "재질", "", false, 30),

    // 색상 WTPART spColor
    // PLM → ERP 색상 (제약 확인 필요)
    // RD_01_04 [IT_TYPE0] 색상
    // CHAR(30) [] BLACK, BLUE, BROWN, GRAY, D/GRAY, L/GRAY, GREEN, L/GREEN, IVORY, JADE MAGENTA, NATURAL, ORANGE, RED YELLOW, 기타
    // ex) MAGENTA
    SpColor("spColor", "RD_01_04", "색상", "", false, 30),

    // 도금 WTPART spPlating
    // PLM → ERP LOV 명
    // RD_01_05 [IT_TYPE0] 도금
    // CHAR(30) []
    // ex)
    SpPlating("spPlating", "RD_01_05", "도금", "", false, 30),

    // 대표금형 WTPART spRepresentM
    // PLM → ERP
    // RD_01_08 [IT_TYPE0] 대표금형1
    // CHAR(30) [] ZDDT002에도 동시 생성 필요
    // ex) 20950000
    SpRepresentM("spRepresentM", "RD_01_08", "대표금형1", "", false, 30),

    // YAZAKI여부 WTPART spIsYazaki
    // PLM → ERP 값이 없는경우 Default NO
    // RD_01_12 [IT_TYPE0] O YAZAKI 여부
    // CHAR(30) [] YES, NO 'RD_01' 경우만 필수
    // ex) NO
    SpIsYazaki("spIsYazaki", "RD_01_12", "YAZAKI 여부", "", true, 30),

    // YAZAKI제번 WTPART spYazakiNo
    // PLM → ERP
    // RD_01_13 [IT_TYPE0] YAZAKI 제번
    // CHAR(30) []
    // ex)
    SpYazakiNo("spYazakiNo", "RD_01_13", "YAZAKI 제번", "", false, 30),

    // 담당자 KETProdChangeActivity workerID
    // PLM → ERP ECO의 설계변경부품/도면 탭의 담당자
    // RD_01_15 [IT_TYPE0] 개발담당자
    // CHAR(30) []
    // ex)
    WorkerId("workerId", "RD_01_15", "YAZAKI 제번", "", false, 30);

    private String attrCode;
    private String erpField;
    private String erpDesc;
    private String defaultValue;
    private boolean essential;
    private int dataLength;

    private ErpPartProdEnum(String attrCode, String erpField, String erpDesc, String defaultValue, boolean essential, int dataLength) {

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

    public static void main(String[] args) {
	ErpPartProdEnum[] arry = ErpPartProdEnum.values();
	for (ErpPartProdEnum item : arry) {
	    Kogger.debug(ErpPartProdEnum.class, "private String " + item.getAttrCode() + ";");
	}
    }

}
