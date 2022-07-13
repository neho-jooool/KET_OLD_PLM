package ext.ket.part.sap;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.Table;

import e3ps.common.util.StringUtil;
import e3ps.sap.ErpPartDieEnum;
import e3ps.sap.ErpPartDieHalbEnum;
import e3ps.sap.ErpPartMoldEnum;
import e3ps.sap.ErpPartProdEnum;
import e3ps.sap.RFCConnect;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.shared.log.Kogger;

public class ErpPartHandler {

    public boolean notUsePart(String partNumber) throws Exception {

	boolean success = false;

	if (partNumber == null || partNumber.length() == 0) {
	    return success;
	}

	Client client = null;
	IRepository repository = null;

	try {

	    // JCO 연결
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);

	    String functionName = "Z_ST_DELETE_MATERIAL";

	    IFunctionTemplate tmpl = repository.getFunctionTemplate(functionName);
	    Function function = tmpl.getFunction();

	    function.getImportParameterList().getField("I_MATNR").setValue(partNumber); // 자재번호
	    client.execute(function);

	    String eReturn = (String) function.getExportParameterList().getValue("E_RETURN");
	    String eMsg = (String) function.getExportParameterList().getValue("E_MSG");

	    Kogger.debug(getClass(), "E_RETURN<<<<<< " + eReturn);
	    Kogger.debug(getClass(), "E_MSG<<<<<< " + eMsg);

	    if (eReturn != null && "S".equals((eReturn.toUpperCase()))) {
		Kogger.debug(getClass(), "######## SUCCESS part Not Use ");
		success = true;
	    } else {
		Kogger.debug(getClass(), "######## FAIL part Not Use ");
		success = false;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;

	} finally {
	    if (client != null) {
		client.disconnect();
	    }
	    repository = null;
	}

	return success;

    }

    public boolean existErpPart(String partNumber) throws Exception {

	boolean success = false;
	
	//partNumber = null;

	if (partNumber == null || partNumber.length() == 0) {
	    return success;
	}

	Client client = null;
	IRepository repository = null;

	try {

	    // 1. Connection 맺기
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);

	    // 2. Function 가져오기
	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_CHECK_MATNR");
	    Function function = tmpl.getFunction();

	    function.getImportParameterList().getField("I_MATNR").setValue(partNumber); // 자재번호
	    client.execute(function);

	    String strSuccess = (String) function.getExportParameterList().getValue("E_RETURN");
	    int resultCnt = function.getExportParameterList().getInt("E_CNT");
	    String strMsg = (String) function.getExportParameterList().getValue("E_MSG");
	    String strDeleted = (String) function.getExportParameterList().getValue("E_LVORM"); // 'X' 면 삭제됨

	    Kogger.debug(getClass(), "E_RETURN <<<<<<< " + strSuccess);
	    Kogger.debug(getClass(), "E_CNT <<<<<<< " + resultCnt);
	    Kogger.debug(getClass(), "E_MSG <<<<<<< " + strMsg);
	    Kogger.debug(getClass(), "E_LVORM <<<<<<< " + strDeleted);

	    if (strSuccess != null && "S".equals((strSuccess.toUpperCase()))) {
		Kogger.debug(getClass(), "######## SUCCESS part exist ");
		success = true;
	    } else {
		Kogger.debug(getClass(), "######## FAIL part exist ");
		success = false;
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    success = false;
	    throw e;

	} finally {

	    if (client != null) {
		client.disconnect();
	    }
	    repository = null;
	}

	return success;

    }

    public PartBaseDTO migrateErpPart(String partNumber) throws Exception {

	PartBaseDTO dto = null;
	
	if (partNumber == null || partNumber.length() == 0) {
	    return dto;
	}

	Client client = null;
	IRepository repository = null;

	try {

	    // 1. Connection 맺기
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);

	    // 2. Function 가져오기
	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_MATERIAL_LIST");
	    Function function = tmpl.getFunction();

	    // 3. 조회 조건 입력 (미입력 시 전체 정보 리턴)
	    //function.getImportParameterList().setValue("ROH", "I_MTART");
	    function.getImportParameterList().setValue(partNumber.trim(), "I_MATNR");
	    //function.getImportParameterList().setValue("2011.01.01", "I_ERSDA_FR");
	    //function.getImportParameterList().setValue("2014.10.18", "I_ERSDA_TO");

	    // 4. 조회 결과 저장 할 I/F 테이블 지정
	    Table tablesP = function.getTableParameterList().getTable("IT_TYPE0");
	    Table tablesD = function.getTableParameterList().getTable("IT_TYPE7");
	    Table tablesDH = function.getTableParameterList().getTable("IT_TYPE7I");
	    Table tablesM = function.getTableParameterList().getTable("IT_TYPE8");

	    client.execute(function);

	    String strSuccess = (String) function.getExportParameterList().getValue("E_RETURN");
	    int resultCnt = function.getExportParameterList().getInt("E_CNT");
	    String strMsg = (String) function.getExportParameterList().getValue("E_MSG");

	    Kogger.debug(getClass(), "E_RETURN <<<<<<< " + strSuccess);
	    Kogger.debug(getClass(), "E_CNT <<<<<<< " + resultCnt);
	    Kogger.debug(getClass(), "E_MSG <<<<<<< " + strMsg);

	    if (tablesP.getNumRows() > 0) { // 제품
		dto = setProd(tablesP);
	    } else if (tablesD.getNumRows() > 0) { // Die No
		dto = setDie(tablesD, tablesDH);
	    } else if (tablesM.getNumRows() > 0) { // Mold No
		dto = setMold(tablesM);
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;

	} finally {

	    if (client != null) {
		client.disconnect();
	    }
	    repository = null;
	}
	
	return dto;

    }

    private String checkErpValue(String erpValue) {

	return StringUtil.checkNull(erpValue);
    }

    private PartBaseDTO setProd(Table tables) {

	tables.setRow(0);

	String partNumber = checkErpValue(tables.getString(ErpPartProdEnum.WtpartNumber.getErpField()));
	Kogger.debug(getClass(), partNumber);

	PartBaseDTO dto = new PartBaseDTO();

	Kogger.debug(getClass(), "####### part Prod :" + partNumber);

	// 품번
	dto.setPartNumber(partNumber);

	// 품명
	String partName = checkErpValue(tables.getString(ErpPartProdEnum.SpPartNameHis.getErpField()));
	Kogger.debug(getClass(), "partName:" + partName);
	dto.setPartName(partName);

	// 부품유형 : FERT: 01.완제품(KET), HALB: 02.반제품(KET), HAWA: 03.상품(KET), ROH: 04.원자재(KET), PACK: 05.포장재(KET), SCRP: 06.스크랩(KET)
	String partType = checkErpValue(tables.getString(ErpPartProdEnum.SpPartType.getErpField()));
	Kogger.debug(getClass(), "partType:" + partType);
	PartTypeEnum partTypeEnum = PartTypeEnum.toPartTypeEnumByErpCode(partType);
	dto.setSpPartType(partTypeEnum.getPlmCode());

	// 사업부 : 1.자동차, 2 전자, 3케이티솔루션 시리즈 입력시 필수
	String divisionFlag = checkErpValue(tables.getString(ErpPartProdEnum.DivisionFlag.getErpField()));
	Kogger.debug(getClass(), "divisionFlag:" + divisionFlag);
//	if("1".equals(divisionFlag)){
//	    divisionFlag = "C";
//	}else if("2".equals(divisionFlag)){
//	    divisionFlag = "E";
//	}else if("3".equals(divisionFlag)){
//	    divisionFlag = "S";
//	}

	// ERP 제품코드 : 부품유형에 따라 구분 전송
	String erpProdHalbCode = checkErpValue(tables.getString(ErpPartProdEnum.ErpProdHalbCode.getErpField()));
	Kogger.debug(getClass(), "erpProdHalbCode:" + erpProdHalbCode);

	// 기본단위 - EA
	String defaultUnit = checkErpValue(tables.getString(ErpPartProdEnum.DefaultUnit.getErpField()));
	Kogger.debug(getClass(), "defaultUnit:" + defaultUnit);
	
	if (defaultUnit != null && !defaultUnit.startsWith("KET_")) defaultUnit = "KET_" + defaultUnit;
	if ("KET_%".equals(defaultUnit)) defaultUnit = "KET_PERCENT";
	
	dto.setPartDefaultUnit(defaultUnit);

	// 제품군 : 부품분류의 제품군 코드 : 자재유형 FERT,HAWA 는 필수
	String erpProdGroupCode = checkErpValue(tables.getString(ErpPartProdEnum.ErpProdGroupCode.getErpField()));
	Kogger.debug(getClass(), "erpProdGroupCode:" + erpProdGroupCode);

	// 총중량
	String totalWeight = checkErpValue(tables.getString(ErpPartProdEnum.SpTotalWeight.getErpField()));
	Kogger.debug(getClass(), "totalWeight:" + totalWeight);
	dto.setSpTotalWeight(totalWeight);

	// 순중량
	String netWeight = checkErpValue(tables.getString(ErpPartProdEnum.SpNetWeight.getErpField()));
	Kogger.debug(getClass(), "netWeight:" + netWeight);
	dto.setSpNetWeight(netWeight);

	// 스크랩중량
	String scrabWeight = checkErpValue(tables.getString(ErpPartProdEnum.SpScrabWeight.getErpField()));
	Kogger.debug(getClass(), "scrabWeight:" + scrabWeight);
	dto.setSpScrabWeight(scrabWeight);

	// 중량단위- Default 'G' 으로 전송
	String weightUnit = checkErpValue(tables.getString(ErpPartProdEnum.SpWeightUnit.getErpField()));
	Kogger.debug(getClass(), "weightUnit:" + weightUnit);

	// 제품 Size
	String prodSize = checkErpValue(tables.getString(ErpPartProdEnum.SpProdSize.getErpField()));
	Kogger.debug(getClass(), "prodSize:" + prodSize);
	dto.setSpProdSize(prodSize);

	// 스크랩 코드
	String scrabCode = checkErpValue(tables.getString(ErpPartProdEnum.SpScrabCode.getErpField()));
	Kogger.debug(getClass(), "scrabCode:" + scrabCode);
	dto.setSpScrabCode(scrabCode);

	// 시리즈
	String series = checkErpValue(tables.getString(ErpPartProdEnum.SpSeries.getErpField()));
	Kogger.debug(getClass(), "series:" + series);
	dto.setSpSeries(series);

	// 방수여부 : 100: 방수 , 200: 비방수
	String waterProof = checkErpValue(tables.getString(ErpPartProdEnum.SpWaterProof.getErpField()));
	Kogger.debug(getClass(), "waterProof:" + waterProof);
	if ("100".equals(waterProof)) {
	    waterProof = "SEALED010";
	}
	else if ("200".equals(waterProof)) {
	    waterProof = "SEALED020";
	}
	else {
	    waterProof = "SEALED030";
	}
	dto.setSpWaterProof(waterProof);

	// Class No : 부품분류의 Class No ex) RD_01
	String erpClassNo = checkErpValue(tables.getString(ErpPartProdEnum.ErpClassNo.getErpField()));
	Kogger.debug(getClass(), "erpClassNo:" + erpClassNo);

	// 작성일 : createstampa2 YYYY.MM.DD(형식으로)
	String createstampa2 = checkErpValue(tables.getString(ErpPartProdEnum.Createstampa2.getErpField()));
	Kogger.debug(getClass(), "createstampa2:" + createstampa2);

	// 재질(비철, 수지) 재질(금형) : 비철, 금형에 따라 별도 LOV
	// RD_01 일경우는 TEXT 입력 ZSS 비철 자재의 경우 SELECT ex) PBT(GP-1000S)
	String materialInfo = checkErpValue(tables.getString(ErpPartProdEnum.SpMaterialInfo.getErpField()));
	// 비철은 ERP I/F 않하는 것으로 결론남.
	// SpMaterialInfo
	// SpMaterNotFe
	Kogger.debug(getClass(), "spMaterialInfo:" + materialInfo);
	dto.setSpMaterialInfo(materialInfo);
	// UI에서 처리
//	if(partNumber.startsWith("R20")){
//	    Kogger.debug(getClass(), "spMaterialInfo:" + materialInfo);
//	    
//	}else if(partNumber.startsWith("R1")){
//	    Kogger.debug(getClass(), "spMaterNotFe:" + materialInfo);
//	    dto.setSpMaterNotFe(materialInfo);
//	}else{
//	    Kogger.debug(getClass(), "spMaterPurch:" + materialInfo);
//	    dto.setSpMaterPurch(materialInfo);
//	}

	// 색상 : 색상 (제약 확인 필요) 색상(color)	spColor 색상(구매품) spColorPurch 색상(전자사업부) spColorElec : UI에서 처리
	// BLACK, BLUE, BROWN, GRAY, D/GRAY, L/GRAY, GREEN, L/GREEN, IVORY, JADE MAGENTA, NATURAL, ORANGE, RED YELLOW, 기타
	// TODO TKLEE 추가 로직 필요한지 확인 필요
	String color = checkErpValue(tables.getString(ErpPartProdEnum.SpColor.getErpField()));
	Kogger.debug(getClass(), "color:" + color);
	dto.setSpColor(color);

	// 도금
	String plating = checkErpValue(tables.getString(ErpPartProdEnum.SpPlating.getErpField()));
	Kogger.debug(getClass(), "plating" + plating);
	dto.setSpPlant(plating);

	// 대표금형
	String representM = checkErpValue(tables.getString(ErpPartProdEnum.SpRepresentM.getErpField()));
	Kogger.debug(getClass(), "representM" + representM);
	dto.setSpRepresentM(representM);
	

	// YAZAKI여부 : 값이 없는경우 Default NO / YES, NO 'RD_01' 경우만 필수
	String isYazaki = checkErpValue(tables.getString(ErpPartProdEnum.SpIsYazaki.getErpField()));
	if (!"YES".equals(isYazaki)) isYazaki = "NO";
	Kogger.debug(getClass(), "isYazaki" + isYazaki);
	dto.setSpIsYazaki(isYazaki);

	// YAZAKI제번
	String yazakiNo = checkErpValue(tables.getString(ErpPartProdEnum.SpYazakiNo.getErpField()));
	Kogger.debug(getClass(), "yazakiNo" + yazakiNo);
	dto.setSpYazakiNo(yazakiNo);

	// 개발담당자 : ECO의 설계변경부품/도면 탭의 담당자
	String workerName = checkErpValue(tables.getString(ErpPartProdEnum.WorkerId.getErpField()));
	Kogger.debug(getClass(), "workerName" + workerName);
	
	return dto;

    }
    
    private PartBaseDTO setMold(Table tables) {

	tables.setRow(0);

	String partNumber = checkErpValue(tables.getString(ErpPartMoldEnum.WtpartNumber.getErpField()));
	Kogger.debug(getClass(), partNumber);

	PartBaseDTO dto = new PartBaseDTO();

	Kogger.debug(getClass(), "####### part Die :" + partNumber);

	// 품번
	dto.setPartNumber(partNumber);

	// 금형번호 / CHAR(18)
	String dieNumber = checkErpValue(tables.getString(ErpPartMoldEnum.DieNumber.getErpField()));
	Kogger.debug(getClass(), "dieNumber" + dieNumber);
	//dto.setDieNumber(dieNumber);
	
	// 품명 // CHAR(40)
	String partNameHis = checkErpValue(tables.getString(ErpPartMoldEnum.SpPartNameHis.getErpField()));
	Kogger.debug(getClass(), "partNameHis" + partNameHis);
	dto.setPartName(partNameHis);
	
	// 기본단위 - EA
	String defaultUnit = checkErpValue(tables.getString(ErpPartMoldEnum.DefaultUnit.getErpField()));
	Kogger.debug(getClass(), "defaultUnit:" + defaultUnit);
	
	if (defaultUnit != null && !defaultUnit.startsWith("KET_")) defaultUnit = "KET_" + defaultUnit;
	if ("KET_%".equals(defaultUnit)) defaultUnit = "KET_PERCENT";
	dto.setPartDefaultUnit(defaultUnit);
	
	// 부품유형 : FERT: 01.완제품(KET), HALB: 02.반제품(KET), HAWA: 03.상품(KET), ROH: 04.원자재(KET), PACK: 05.포장재(KET), SCRP: 06.스크랩(KET)
	String partType = checkErpValue(tables.getString(ErpPartMoldEnum.SpPartType.getErpField()));
	Kogger.debug(getClass(), "partType:" + partType);
	PartTypeEnum partTypeEnum = PartTypeEnum.toPartTypeEnumByErpCode(partType);
	dto.setSpPartType(partTypeEnum.getPlmCode());
	
	// ERP 제품코드 : 부품유형에 따라 구분 전송
	String erpProdHalbCode = checkErpValue(tables.getString(ErpPartMoldEnum.ErpProdHalbCode.getErpField()));
	Kogger.debug(getClass(), "erpProdHalbCode" + erpProdHalbCode);
	
	// 재질(금형) : ex) S04
	String spMaterDie = checkErpValue(tables.getString(ErpPartMoldEnum.SpMaterDie.getErpField()));
	Kogger.debug(getClass(), "spMaterDie" + spMaterDie);
	dto.setSpMaterDie(spMaterDie);

	return dto;
    }

    private PartBaseDTO setDie(Table tables, Table mapTables) {

	tables.setRow(0);

	String partNumber = checkErpValue(tables.getString(ErpPartDieEnum.WtpartNumber.getErpField()));
	Kogger.debug(getClass(), partNumber);

	PartBaseDTO dto = new PartBaseDTO();

	Kogger.debug(getClass(), "####### part Die :" + partNumber);

	// 품번
	dto.setPartNumber(partNumber);
	
	// 품명 // CHAR(40)
	//String partNameHis = checkErpValue(tables.getString(ErpPartMoldEnum.SpPartNameHis.getErpField()));
	//Kogger.debug(getClass(), "partNameHis" + partNameHis);
	//dto.setPartName(partNameHis);
	dto.setPartName(partNumber);
	
	// 기본단위 - EA
	String defaultUnit = "KET_EA";//checkErpValue(tables.getString(ErpPartMoldEnum.DefaultUnit.getErpField()));
	Kogger.debug(getClass(), "defaultUnit:" + defaultUnit);
	dto.setPartDefaultUnit(defaultUnit);
	
	// 부품유형
	dto.setSpPartType("D");

	// 사급구분 : 1 (사내), 2 (외주) :
	String spMContractSAt = checkErpValue(tables.getString(ErpPartDieEnum.SpMContractSAt.getErpField()));
	Kogger.debug(getClass(), "spMContractSAt:" + spMContractSAt);
	dto.setSpMContractSAt(spMContractSAt);
	
	// 금형구분 : 1 (프레스), 2 (사출), 3 (기타)
	String spMMoldAt = checkErpValue(tables.getString(ErpPartDieEnum.SpMMoldAt.getErpField()));
	Kogger.debug(getClass(), "spMMoldAt:" + spMMoldAt);
	dto.setSpMMoldAt(spMMoldAt); 
		
	// 제작구분 : 1 (자체), 2 (OEM), 3 (대여), 4 (Yazaki), 5 (타사금형), 9 (기타)
	String spMMakingAt = checkErpValue(tables.getString(ErpPartDieEnum.SpMMakingAt.getErpField()));
	Kogger.debug(getClass(), "spMMakingAt:" + spMMakingAt);
	dto.setSpMMakingAt(spMMakingAt); 
	
	// 생산구분 : [ZGBN4] 1 (SET), 2 (추가), 3 (Modify)
	String spMProdAt = checkErpValue(tables.getString(ErpPartDieEnum.SpMProdAt.getErpField()));
	Kogger.debug(getClass(), "spMProdAt:" + spMProdAt);
	dto.setSpMProdAt(spMProdAt); 
	
	// 구매담당자 : 구매 그룹
	String spPuchaseGroup = checkErpValue(tables.getString(ErpPartDieEnum.SpPuchaseGroup.getErpField()));
	Kogger.debug(getClass(), "spPuchaseGroup:" + spPuchaseGroup);
	dto.setSpPuchaseGroup(spPuchaseGroup);
	
	// 플랜트
	String spPlant = checkErpValue(tables.getString(ErpPartDieEnum.SpPlant.getErpField()));
	Kogger.debug(getClass(), "spPlant:" + spPlant);
	dto.setSpPlant(spPlant);
	
	// 개발담당자 : 개발담당자명
	String spDevManNm = checkErpValue(tables.getString(ErpPartDieEnum.SpDevManNm.getErpField()));
	Kogger.debug(getClass(), "spDevManNm:" + spDevManNm);
	dto.setSpDevManNm(spDevManNm);
	
	// 금형담당자 : 설계담당자명
	String spDesignManNm = checkErpValue(tables.getString(ErpPartDieEnum.SpDesignManNm.getErpField()));
	Kogger.debug(getClass(), "spDesignManNm:" + spDesignManNm);
	dto.setSpDesignManNm(spDesignManNm);
	
	// 제작담당자 : 금형제작처 - 사급구분이 사내일 경우 제작담당자, 사급구분이 외주일 경우 금형제작처
	String spManufacManNm = checkErpValue(tables.getString(ErpPartDieEnum.SpManufacManNm.getErpField()));
	Kogger.debug(getClass(), "spManufacManNm:" + spManufacManNm);
	if ("1".equals(spMContractSAt)) { // 사내
	    dto.setSpManufacManNm(spManufacManNm);
	}
	else if ("2".equals(spMContractSAt)) { // 외주
	    dto.setSpDieWhere(spManufacManNm);
	}
	
	// 목표 C/T (SPM)
	String spObjectiveCT = checkErpValue(tables.getString(ErpPartDieEnum.SpObjectiveCT.getErpField()));
	Kogger.debug(getClass(), "spObjectiveCT:" + spObjectiveCT);
	dto.setSpObjectiveCT(spObjectiveCT);
	
	// Cavity
	String spCavityStd = checkErpValue(tables.getString(ErpPartDieEnum.SpCavityStd.getErpField()));
	Kogger.debug(getClass(), "spCavityStd:" + spCavityStd);
	dto.setSpCavityStd(spCavityStd);
	
	// /////////////////////////////////////////////////////////////////////////////////////////
	// 매핑자재 - 반제품의 매핑자재
	// /////////////////////////////////////////////////////////////////////////////////////////

	if(mapTables != null){
	    
	    String partProdMigNumber = null;
	    for( int j = 0; j < mapTables.getNumRows(); j++ ) {
        	mapTables.setRow(j);
        	String dieNumber = checkErpValue(mapTables.getString(ErpPartDieHalbEnum.DieNumber.getErpField()));
        	Kogger.debug(getClass(), "dieNumber:" + dieNumber);
        //	dto.setDieNumber(dieNumber);
        	// 부품번호 : 제품일 경우 부품번호, 금형일 경우 링크 부품번호 / CHAR(18)
        	String halbNumber = checkErpValue(mapTables.getString(ErpPartDieHalbEnum.HalbNuumber.getErpField()));
        	Kogger.debug(getClass(), "halbNumber:" + halbNumber);
        //	dto.setHalbNumber(halbNumber);
        	if(partProdMigNumber == null){
        	    partProdMigNumber = halbNumber;
        	}else{
        	    partProdMigNumber = partProdMigNumber + "," + halbNumber;
        	}
	    }
	    
	    dto.setPartProdMigNumber(partProdMigNumber);
	}
	
	return dto;

    }
    
    public static void main(String[] args) throws Exception {

	ErpPartHandler erpPartHandler = new ErpPartHandler();
	erpPartHandler.migrateErpPart("R103585");
	
//	ErpPartHandler erpPartHandler = new ErpPartHandler();
//	boolean exist = erpPartHandler.existErpPart("R103585");
//	Kogger.debug(getClass(), exist);

//	 ErpPartHandler erpPartHandler = new ErpPartHandler();
//	 boolean notUsePart = erpPartHandler.notUsePart("R103585");
//	 Kogger.debug(getClass(), notUsePart);

    }

}
