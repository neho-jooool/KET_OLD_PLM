package ext.ket.part.util;

import ext.ket.shared.log.Kogger;

/**
 * 부품속성 정보 관리
 * 
 * @클래스명 : PartSpecEnum
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 28.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public enum PartSpecEnum {

    // ///////////////////////////////////////////////
    //
    // 기존의 부품 속성 정보 ( 마이그레이션 대상 ) : 기존 속성 변경 partType >> specPartType 형태로 "sp" prefix를 넣어줌
    //
    // ///////////////////////////////////////////////

    // 멀티체크 - Tg, 특성, 표면처리, 외형가공, Solder Resistor Corlor, WireRange_AWG(전자)
    // 재질(금형부품), 재질(비철), 기타-TEXT => 1개, Search()
    // 칼럼 : PTC_STR_1TypeInfoWTPart, query attribute : typeInfoWTPart.ptc_str_1

    // Project No -
    // Reference
    // 프로젝트 NO
    SpProjectNo("spProjectNo", "Project No", "", "", "REFERENCE", "", false, false, false, "", false, ""),
    // EO No [관리] -
    SpEoNo("spEoNo", "EO No", "PTC_STR_150TYPEINFOWTPART", "typeInfoWTPart.ptc_str_150", "TEXT", "", false, false, false, "", false, ""),
    // 부품명 이력 [관리] -
    // OOTB 외 별도 속성
    SpPartNameHis("spPartNameHis", "부품명 이력", "PTC_STR_1TYPEINFOWTPART", "typeInfoWTPart.ptc_str_1", "TEXT", "", false, false, false, "",
	    true, ""),
    // 부품 버전 [관리] -
    // OOTB 외 별도 속성
    SpPartRevision("spPartRevision", "부품 버전", "PTC_STR_2TYPEINFOWTPART", "typeInfoWTPart.ptc_str_2", "TEXT", "", false, false, false, "",
	    true, ""),
    // 부품유형[기존 변경] - partType[P, M, D]
    // P, M, D [제품, 금형, DIE NO]+@
    SpPartType("spPartType", "부품유형", "PTC_STR_3TYPEINFOWTPART", "typeInfoWTPart.ptc_str_3", "SELECT", "SPECPARTTYPE", false, false, false,
	    "", true, ""),
    // 개발단계 - PC003:개발;PC004:양산
    SpPartDevLevel("spPartDevLevel", "개발단계", "PTC_STR_4TYPEINFOWTPART", "typeInfoWTPart.ptc_str_4", "SELECT", "", false, false, false, "",
	    false, ""),
    // 부품삭제구분[기존 유지] - IsDeleted[Y,N]
    // Y, N
    SpIsDeleted("spIsDeleted", "부품삭제구분", "PTC_STR_5TYPEINFOWTPART", "typeInfoWTPart.ptc_str_5", "비대상", "", false, false, false, "", true,
	    ""),
    // 신규부품구분[기존 유지] - BOMFlag[OLD,NEW]
    // OLD, NEW
    SpBOMFlag("spBOMFlag", "신규부품구분", "PTC_STR_6TYPEINFOWTPART", "typeInfoWTPart.ptc_str_6", "비대상", "", false, false, false, "", false, ""),
    // 중량단위[기존 유지] - WeightUnit[KG,G]
    // KG, G
    SpWeightUnit("spWeightUnit", "중량단위", "PTC_STR_7TYPEINFOWTPART", "typeInfoWTPart.ptc_str_7", "SELECT", "", false, false, false, "",
	    false, ""),
    // 부품중량 - PartWeight
    SpNetWeight("spNetWeight", "부품중량", "PTC_STR_8TYPEINFOWTPART", "typeInfoWTPart.ptc_str_8", "TEXT", "", false, false, false, "", true, ""),
    // 총중량 -
    // - 프레스 상품의 경우 스크랩 포함 , 중량으로 제품자체의 중량 기입 가능 여부 확인 필요, - 중량은 필수 관리 필요
    SpTotalWeight("spTotalWeight", "총중량", "PTC_STR_9TYPEINFOWTPART", "typeInfoWTPart.ptc_str_9", "TEXT", "", false, false, false, "", true,
	    ""),
    // 스크랩중량 -
    SpScrabWeight("spScrabWeight", "스크랩중량", "PTC_STR_10TYPEINFOWTPART", "typeInfoWTPart.ptc_str_10", "TEXT", "", false, false, false, "",
	    true, ""),
    // YAZAKI여부 - IsYazaki[YES,NO]
    // YES, NO
    SpIsYazaki("spIsYazaki", "YAZAKI여부", "PTC_STR_11TYPEINFOWTPART", "typeInfoWTPart.ptc_str_11", "SELECT", "SPECISYAZAKI", false, false,
	    false, "", true, ""),
    // YAZAKI제번 - YazakiNo
    SpYazakiNo("spYazakiNo", "YAZAKI제번", "PTC_STR_12TYPEINFOWTPART", "typeInfoWTPart.ptc_str_12", "TEXT", "", false, false, false, "",
	    true, ""),
    // 생산처구분 -
    SpManufAt("spManufAt", "생산처구분", "PTC_STR_25TYPEINFOWTPART", "typeInfoWTPart.ptc_str_25", "SELECT", "SPECMSELFCONTRACTFLAG", false,
	    false, false, "", false, ""),
    // 생산처 -
    // 사내/외주에 따라 다른 LOV - SELECT PRODUCTIONDEPT|KETPARTNER
    SpManufacPlace("spManufacPlace", "생산처", "PTC_STR_13TYPEINFOWTPART", "typeInfoWTPart.ptc_str_13", "TEXT", "", false, false, false, "",
	    false, ""),
    // 고객 Rev -
    SpCustomRev("spCustomRev", "고객 Rev", "PTC_STR_14TYPEINFOWTPART", "typeInfoWTPart.ptc_str_14", "TEXT", "", false, false, false, "",
	    false, ""),
    // 스크랩 코드 -
    SpScrabCode("spScrabCode", "스크랩 코드", "PTC_STR_15TYPEINFOWTPART", "typeInfoWTPart.ptc_str_15", "TEXT", "", false, false, false, "",
	    true, ""),
    // 방수여부 [ERP 방수여부] -
    // 방수, 비방수, 방수/비방수
    SpWaterProof("spWaterProof", "방수여부", "PTC_STR_16TYPEINFOWTPART", "typeInfoWTPart.ptc_str_16", "SELECT", "SEALED", false, false, false,
	    "", true, ""),
    // 재질마킹 -
    // Yes, NO
    // - 금형코드(몰드)가 연계되는 아이템은 재질마킹 여부 확인 필요
    SpMaterialMark("spMaterialMark", "재질마킹", "PTC_STR_17TYPEINFOWTPART", "typeInfoWTPart.ptc_str_17", "SELECT", "SPECMATERIALMARKING",
	    false, false, false, "", false, ""),
    // 색상(color) -
    // LOV
    SpColor("spColor", "색상(color)", "PTC_STR_18TYPEINFOWTPART", "typeInfoWTPart.ptc_str_18", "SELECT", "SPECCOLOR", false, false, true, "",
	    true, ""),
    // 색상(구매품) -
    SpColorPurch("spColorPurch", "색상(구매품)", "PTC_STR_19TYPEINFOWTPART", "typeInfoWTPart.ptc_str_19", "SELECT", "SPCOLORPURCH", false,
	    false, false, "", false, ""),
    // 색상(color)_IT 부품 -
    SpColorElec("spColorElec", "색상(color)_IT 부품", "PTC_STR_20TYPEINFOWTPART", "typeInfoWTPart.ptc_str_20", "SELECT", "SPCOLORELEC", false,
	    false, true, "", true, ""),
    // 시리즈[Series No] -
    // 025, 060, 090, 250, 375…..
    // ERP에 속성 추가 검토 -경영정보, ERP 속성과 Mapping 추진 필요
    SpSeries("spSeries", "시리즈", "PTC_STR_21TYPEINFOWTPART", "typeInfoWTPart.ptc_str_21", "SELECT", "SPECSERIES", false, false, true, "",
	    true, ""),
    // 시리즈(LAMP) -
    SpSeriesLAMP("spSeriesLAMP", "시리즈(LAMP)", "PTC_STR_22TYPEINFOWTPART", "typeInfoWTPart.ptc_str_22", "SELECT", "SPSERIESLAMP", false,
	    false, false, "", false, ""),
    // 시리즈(Bulb) -
    SpSeriesBulb("spSeriesBulb", "시리즈(Bulb)", "PTC_STR_151TYPEINFOWTPART", "typeInfoWTPart.ptc_str_151", "SELECT", "SPSERIESBULB", false,
	    false, false, "", false, ""),
    // 제품 Size(L*W*H) [erp 크기/치수] -
    // 가로 * 세로 * 높이
    SpProdSize("spProdSize", "제품 Size(L*W*H)", "PTC_STR_23TYPEINFOWTPART", "typeInfoWTPart.ptc_str_23", "TEXT", "", false, false, false,
	    "", true, ""),
    // 친환경 -
    SpEnvFriend("spEnvFriend", "친환경", "PTC_STR_24TYPEINFOWTPART", "typeInfoWTPart.ptc_str_24", "SELECT", "SPENVFRIEND", false, false,
	    false, "", false, ""),
    // Type(일반커넥터) -
    SpTGenConn("spTGenConn", "Type(일반커넥터)", "PTC_STR_26TYPEINFOWTPART", "typeInfoWTPart.ptc_str_26", "SELECT", "SPTGENCONN", false, false,
	    false, "", false, ""),
    // Type(PCB 커넥터) -
    SpTPCBConn("spTPCBConn", "Type(PCB 커넥터)", "PTC_STR_27TYPEINFOWTPART", "typeInfoWTPart.ptc_str_27", "SELECT", "SPTPCBCONN", false,
	    false, false, "", false, ""),
    // Type(LAMP) -
    SpTLAMPConn("spTLAMPConn", "Type(LAMP)", "PTC_STR_28TYPEINFOWTPART", "typeInfoWTPart.ptc_str_28", "SELECT", "SPTLAMPCONN", false,
	    false, false, "", false, ""),
    // Type(Bulb) -
    SpTBulbConn("spTBulbConn", "Type(Bulb)", "PTC_STR_152TYPEINFOWTPART", "typeInfoWTPart.ptc_str_152", "SELECT", "SPTBULBCONN", false,
	    false, false, "", false, ""),
    // Type(고전압) -
    SpTHiVolt("spTHiVolt", "Type(고전압)", "PTC_STR_29TYPEINFOWTPART", "typeInfoWTPart.ptc_str_29", "SELECT", "SPTHIVOLT", false, false,
	    false, "", false, ""),
    // Type(충전기) -
    SpTCharger("spTCharger", "Type(충전기)", "PTC_STR_30TYPEINFOWTPART", "typeInfoWTPart.ptc_str_30", "SELECT", "SPECTYPECHARGER", false,
	    false, false, "", false, ""),
    // Type2(충전기) -
    SpT2Charger("spT2Charger", "Type2(충전기)", "PTC_STR_153TYPEINFOWTPART", "typeInfoWTPart.ptc_str_153", "SELECT", "SPT2CHARGER", false,
	    false, false, "", false, ""),
    // Type1(고전압퓨즈) -
    SpT1HiVoltFuz("spT1HiVoltFuz", "Type1(고전압퓨즈)", "PTC_STR_154TYPEINFOWTPART", "typeInfoWTPart.ptc_str_154", "SELECT", "SPT1HIVOLTFUZ",
	    false, false, false, "", false, ""),
    // Type2(고전압퓨즈) -
    SpTHiVoltFuse("spTHiVoltFuse", "Type2(고전압퓨즈)", "PTC_STR_31TYPEINFOWTPART", "typeInfoWTPart.ptc_str_31", "SELECT",
	    "SPECTYPEHIGHVOLTAGEFUSE", false, false, false, "", false, ""),
    // Type(J/B) -
    SpTJnB("spTJnB", "Type(J/B)", "PTC_STR_32TYPEINFOWTPART", "typeInfoWTPart.ptc_str_32", "SELECT", "SPTJNB", false, false, false, "",
	    false, ""),
    // Type(PRA) -
    SpTPRA("spTPRA", "Type(PRA)", "PTC_STR_33TYPEINFOWTPART", "typeInfoWTPart.ptc_str_33", "SELECT", "SPTPRA", false, false, false, "",
	    false, ""),
    // Type(GNSS 모듈) -
    SpTGNSS("spTGNSS", "Type(GNSS 모듈)", "PTC_STR_34TYPEINFOWTPART", "typeInfoWTPart.ptc_str_34", "SELECT", "SPTGNSS", false, false, false,
	    "", false, ""),
    // Type(통신모듈) -
    SpTComModule("spTComModule", "Type(통신모듈)", "PTC_STR_35TYPEINFOWTPART", "typeInfoWTPart.ptc_str_35", "SELECT", "SPTCOMMODULE", false,
	    false, false, "", false, ""),
    // Type(멀티미디어 유닛) -
    SpTMultiMedUnit("spTMultiMedUnit", "Type(멀티미디어 유닛)", "PTC_STR_36TYPEINFOWTPART", "typeInfoWTPart.ptc_str_36", "SELECT",
	    "SPTMULTIMEDUNIT", false, false, false, "", false, ""),
    // Lance Type -
    SpLanceType("spLanceType", "Lance Type", "PTC_STR_37TYPEINFOWTPART", "typeInfoWTPart.ptc_str_37", "SELECT", "SPLANCETYPE", false,
	    false, false, "", false, ""),
    // 극수 -
    // HOUSING HOLE 수
    SpNoOfPole("spNoOfPole", "극수", "PTC_STR_38TYPEINFOWTPART", "typeInfoWTPart.ptc_str_38", "TEXT", "", false, true, true, "", false, ""),
    // 매칭터미널 -
    // Reference
    // 조립 터미널 NO
    SpMTerminal("spMTerminal", "매칭터미널", "PTC_STR_39TYPEINFOWTPART", "typeInfoWTPart.ptc_str_39", "TEXT", "", false, false, false, "",
	    false, ""),
    // 매칭커넥터 -
    // Reference
    // 상대편 커넥터 NO (Mail <-> Femail)
    SpMConnector("spMConnector", "매칭커넥터", "PTC_STR_40TYPEINFOWTPART", "typeInfoWTPart.ptc_str_40", "TEXT", "", false, false, false, "",
	    false, ""),
    // 매칭전구 -
    // Reference
    SpMatchBulb("spMatchBulb", "매칭전구", "PTC_STR_41TYPEINFOWTPART", "typeInfoWTPart.ptc_str_41", "TEXT", "", false, false, false, "", false,
	    ""),
    // 매칭Clip -
    // Reference
    // Clip NO
    SpMClip("spMClip", "매칭Clip", "PTC_STR_42TYPEINFOWTPART", "typeInfoWTPart.ptc_str_42", "TEXT", "", false, false, false, "", false, ""),
    // 매칭R/H -
    // Reference
    // R/H NO
    SpMRH("spMRH", "매칭R/H", "PTC_STR_43TYPEINFOWTPART", "typeInfoWTPart.ptc_str_43", "TEXT", "", false, false, false, "", false, ""),
    // 매칭 Cover -
    // Reference
    SpMCover("spMCover", "매칭 Cover", "PTC_STR_155TYPEINFOWTPART", "typeInfoWTPart.ptc_str_155", "TEXT", "", false, false, false, "", false,
	    ""),
    // Locking Type -
    // Single Lock, Double Lock (CPA 적용 여부)
    SpLockingType("spLockingType", "Locking Type", "PTC_STR_44TYPEINFOWTPART", "typeInfoWTPart.ptc_str_44", "SELECT", "SPECLOCKINGTYPE",
	    false, false, false, "", false, ""),
    // TPA 적용 -
    SpLockTTML("spLockTTML", "TPA 적용", "PTC_STR_45TYPEINFOWTPART", "typeInfoWTPart.ptc_str_45", "SELECT", "SPECLOCKTTML", false, false,
	    false, "", false, ""),
    // CPA 적용 -
    SpLockTConn("spLockTConn", "CPA 적용", "PTC_STR_46TYPEINFOWTPART", "typeInfoWTPart.ptc_str_46", "SELECT", "SPECLOCKTCONN", false, false,
	    false, "", false, ""),
    // 매칭 Wire Seal -
    // Reference
    // 매칭 Wire Seal NO
    SpMWireSeal("spMWireSeal", "매칭 Wire Seal", "PTC_STR_47TYPEINFOWTPART", "typeInfoWTPart.ptc_str_47", "TEXT", "", false, false, false,
	    "", false, ""),
    // 대표금형 [ERP 대표금형1] -
    // Reference
    // 금형 번호
    SpRepresentM("spRepresentM", "대표금형", "PTC_STR_48TYPEINFOWTPART", "typeInfoWTPart.ptc_str_48", "TEXT", "", false, false, false, "",
	    true, ""),
    // 원재료 Maker -
    SpMaterMaker("spMaterMaker", "원재료 Maker", "PTC_STR_147TYPEINFOWTPART", "typeInfoWTPart.ptc_str_147", "SELECT", "MATERIALMAKER", false,
	    false, false, "", false, ""),
    // 원재료 Type -
    SpMaterType("spMaterType", "원재료 Type", "PTC_STR_148TYPEINFOWTPART", "typeInfoWTPart.ptc_str_148", "SELECT", "MATERIALTYPE", false,
	    false, false, "", false, ""),
    // 재질(수지) -
    // Grade SELECT MOLDMATERIAL
    SpMaterialInfo("spMaterialInfo", "재질(수지)", "PTC_STR_49TYPEINFOWTPART", "typeInfoWTPart.ptc_str_49", "SELECT", "", false, false, false,
	    "", true, ""),
    // 재질(비철) -
    // Grade SELECT SPECMATERIALNONFERROUS
    SpMaterNotFe("spMaterNotFe", "재질(비철)", "PTC_STR_50TYPEINFOWTPART", "typeInfoWTPart.ptc_str_50", "SELECT", "", false, false, false, "",
	    true, ""),
    // 재질(금형) -
    SpMaterDie("spMaterDie", "재질(금형)", "PTC_STR_51TYPEINFOWTPART", "typeInfoWTPart.ptc_str_51", "SELECT", "SPECMATERIALMOLD", false, false,
	    false, "", true, ""),
    // 재질(구매품) -
    SpMaterPurch("spMaterPurch", "재질(구매품)", "PTC_STR_52TYPEINFOWTPART", "typeInfoWTPart.ptc_str_52", "TEXT", "", false, false, false, "",
	    false, ""),
    // 도금 -
    // Brass,Brass/Tin, Tin/Ni, Pre-Tin, Ag/Ni, Ni, Cr…
    SpPlating("spPlating", "도금", "PTC_STR_53TYPEINFOWTPART", "typeInfoWTPart.ptc_str_53", "SELECT", "SPECPLATING", false, false, true, "",
	    true, ""),
    // 도금(구매품) -
    SpPlatingPurch("spPlatingPurch", "도금(구매품)", "PTC_STR_54TYPEINFOWTPART", "typeInfoWTPart.ptc_str_54", "SELECT", "SPPLATINGPURCH", false,
	    false, false, "", false, ""),
    // 고객사 품번 -
    SpCustomPartNo("spCustomPartNo", "고객사 품번", "PTC_STR_55TYPEINFOWTPART", "typeInfoWTPart.ptc_str_55", "TEXT", "", false, false, true, "",
	    false, ""),
    // 허용전류 -
    SpPermitVolt("spPermitVolt", "허용전류", "PTC_STR_56TYPEINFOWTPART", "typeInfoWTPart.ptc_str_56", "TEXT", "", false, false, false, "",
	    false, ""),
    // Wire Range -
    SpWireRangeAWG("spWireRangeAWG", "Wire Range", "PTC_STR_57TYPEINFOWTPART", "typeInfoWTPart.ptc_str_57", "TEXT", "", false, false,
	    false, "", false, ""),
    // WireRange_AWG(전자) -
    SpWireRAWGElec("spWireRAWGElec", "WireRange_AWG(전자)", "PTC_STR_58TYPEINFOWTPART", "typeInfoWTPart.ptc_str_58", "SELECT",
	    "SPWIRERAWGELEC", true, false, false, "", false, ""),
    // Insulation Range -
    SpWireRangeMm("spWireRangeMm", "Insulation Range", "PTC_STR_59TYPEINFOWTPART", "typeInfoWTPart.ptc_str_59", "TEXT", "", false, false,
	    false, "", false, ""),
    // TabThickness -
    SpTabThickness("spTabThickness", "TabThickness", "PTC_STR_60TYPEINFOWTPART", "typeInfoWTPart.ptc_str_60", "TEXT", "", false, false,
	    false, "", false, ""),
    // Material_Thick -
    SpMaterThick("spMaterThick", "Material_Thick", "PTC_STR_61TYPEINFOWTPART", "typeInfoWTPart.ptc_str_61", "TEXT", "", false, false,
	    false, "", false, ""),
    // BracketSize_D -
    // CLIP 해당
    SpBracketSizeD("spBracketSizeD", "BracketSize_D", "PTC_STR_62TYPEINFOWTPART", "typeInfoWTPart.ptc_str_62", "TEXT", "", false, false,
	    false, "", false, ""),
    // BracketSize_H -
    // CLIP 해당
    SpBracketSizeH("spBracketSizeH", "BracketSize_H", "PTC_STR_63TYPEINFOWTPART", "typeInfoWTPart.ptc_str_63", "TEXT", "", false, false,
	    false, "", false, ""),
    // BracketSize_T -
    // CLIP 해당
    SpBracketSizeT("spBracketSizeT", "BracketSize_T", "PTC_STR_64TYPEINFOWTPART", "typeInfoWTPart.ptc_str_64", "TEXT", "", false, false,
	    false, "", false, ""),
    // RELAY 용량 -
    SpRelayCapa("spRelayCapa", "RELAY 용량", "PTC_STR_65TYPEINFOWTPART", "typeInfoWTPart.ptc_str_65", "SELECT", "SPECRELAYCAPACITY", false,
	    false, false, "", false, ""),
    // 저항값 -
    SpEResistVal("spEResistVal", "저항값", "PTC_STR_66TYPEINFOWTPART", "typeInfoWTPart.ptc_str_66", "SELECT", "SPECELECTRICRESISTANCEVALUE",
	    false, false, false, "", false, ""),
    // 전류센서용량 -
    SpVoltSensCapa("spVoltSensCapa", "전류센서용량", "PTC_STR_67TYPEINFOWTPART", "typeInfoWTPart.ptc_str_67", "SELECT",
	    "SPECVOLTAGESENSECAPACITY", false, false, false, "", false, ""),
    // 제조사 품번 -
    SpManufPartNo("spManufPartNo", "제조사 품번", "PTC_STR_68TYPEINFOWTPART", "typeInfoWTPart.ptc_str_68", "TEXT", "", false, false, false, "",
	    false, ""),
    // Value -
    SpValueValue("spValueValue", "Value", "PTC_STR_69TYPEINFOWTPART", "typeInfoWTPart.ptc_str_69", "TEXT", "", false, false, false, "",
	    false, ""),
    // Tolerance -
    SpTolerance("spTolerance", "Tolerance", "PTC_STR_70TYPEINFOWTPART", "typeInfoWTPart.ptc_str_70", "SELECT", "SPECTOLERANCE", false,
	    false, false, "", false, ""),
    // 제조사 -
    // TYCO/KUM/MOLEX...
    SpManufacturer("spManufacturer", "제조사", "PTC_STR_71TYPEINFOWTPART", "typeInfoWTPart.ptc_str_71", "TEXT", "", false, false, false, "",
	    false, ""),
    // Rated voltage -
    SpRatedVoltage("spRatedVoltage", "Rated voltage", "PTC_STR_72TYPEINFOWTPART", "typeInfoWTPart.ptc_str_72", "TEXT", "", false, false,
	    false, "", false, ""),
    // Watt -
    SpWatt("spWatt", "Watt", "PTC_STR_73TYPEINFOWTPART", "typeInfoWTPart.ptc_str_73", "TEXT", "", false, false, false, "", false, ""),
    // Operating Temperature(TA) -
    SpOperTemp("spOperTemp", "Operating Temperature(TA)", "PTC_STR_74TYPEINFOWTPART", "typeInfoWTPart.ptc_str_74", "TEXT", "", false,
	    false, false, "", false, ""),
    // Storage Temperature(Tstg) -
    SpStoreTemp("spStoreTemp", "Storage Temperature(Tstg)", "PTC_STR_156TYPEINFOWTPART", "typeInfoWTPart.ptc_str_156", "TEXT", "", false,
	    false, false, "", false, ""),
    // Package Type -
    SpPackageType("spPackageType", "Package Type", "PTC_STR_75TYPEINFOWTPART", "typeInfoWTPart.ptc_str_75", "SELECT", "SPECPACKAGETYPE",
	    false, false, false, "", false, ""),
    // MSL -
    SpMSL("spMSL", "MSL", "PTC_STR_76TYPEINFOWTPART", "typeInfoWTPart.ptc_str_76", "SELECT", "SPECMSL", false, false, false, "", false, ""),
    // ESD(HBM) -
    SpESD("spESD", "ESD(HBM)", "PTC_STR_77TYPEINFOWTPART", "typeInfoWTPart.ptc_str_77", "SELECT", "SPECESD", false, false, false, "",
	    false, ""),
    // AEC-Q -
    SpAECQ("spAECQ", "AEC-Q", "PTC_STR_78TYPEINFOWTPART", "typeInfoWTPart.ptc_str_78", "SELECT", "SPAECQ", false, false, false, "", false,
	    ""),
    // 특성1 -
    // 제품의 특성에 대해 추가로 기입할 수 있는 것이 필요함
    SpCharact1("spCharact1", "특성1", "PTC_STR_79TYPEINFOWTPART", "typeInfoWTPart.ptc_str_79", "TEXT", "", false, false, false, "", false, ""),
    // 특성2 -
    // 제품의 특성에 대해 추가로 기입할 수 있는 것이 필요함
    SpCharact2("spCharact2", "특성2", "PTC_STR_80TYPEINFOWTPART", "typeInfoWTPart.ptc_str_80", "TEXT", "", false, false, false, "", false, ""),
    // Hole Size(Ø) -
    SpHoleSize("spHoleSize", "Hole Size(Ø)", "PTC_STR_81TYPEINFOWTPART", "typeInfoWTPart.ptc_str_81", "TEXT", "", false, false, false, "",
	    false, ""),
    // Wire Range(SQ) -
    SpWireRangeSQ("spWireRangeSQ", "Wire Range(SQ)", "PTC_STR_82TYPEINFOWTPART", "typeInfoWTPart.ptc_str_82", "SELECT", "SPECWIRERANGE",
	    false, false, false, "", false, ""),
    // WATT(W) -
    SpWATTW("spWATTW", "WATT(W)", "PTC_STR_83TYPEINFOWTPART", "typeInfoWTPart.ptc_str_83", "SELECT", "SPECWATTW", false, false, false, "",
	    false, ""),
    // Size(mm) -
    SpSizemm("spSizemm", "Size(mm)", "PTC_STR_84TYPEINFOWTPART", "typeInfoWTPart.ptc_str_84", "TEXT", "", false, false, false, "", false,
	    ""),
    // 허용온도(℃) -
    SpPermitTempC("spPermitTempC", "허용온도(℃)", "PTC_STR_85TYPEINFOWTPART", "typeInfoWTPart.ptc_str_85", "TEXT", "", false, false, false, "",
	    false, ""),
    // 레진 여부 -
    // Yes, NO
    SpIsResin("spIsResin", "레진 여부", "PTC_STR_86TYPEINFOWTPART", "typeInfoWTPart.ptc_str_86", "SELECT", "SPECRESIN", false, false, false,
	    "", false, ""),
    // Thickness -
    SpThickness("spThickness", "Thickness", "PTC_STR_87TYPEINFOWTPART", "typeInfoWTPart.ptc_str_87", "SELECT", "SPECTHICKNESS", false,
	    false, false, "", false, ""),
    // Hole 형상 -
    // Slot/정홀
    SpHoleShape("spHoleShape", "Hole 형상", "PTC_STR_88TYPEINFOWTPART", "typeInfoWTPart.ptc_str_88", "SELECT", "SPECHOLESHAPE", false, false,
	    false, "", false, ""),
    // 용량(Ω) -
    SpCapaEResist("spCapaEResist", "용량(Ω)", "PTC_STR_89TYPEINFOWTPART", "typeInfoWTPart.ptc_str_89", "TEXT", "", false, false, false, "",
	    false, ""),
    // 피복재질 -
    // AVSS/AEXF/AVSSXF....
    SpClothesMater("spClothesMater", "피복재질", "PTC_STR_90TYPEINFOWTPART", "typeInfoWTPart.ptc_str_90", "SELECT", "SPECCLOTHESMATERIAL",
	    false, false, false, "", false, ""),
    // 쉴드여부 -
    // Yes, NO
    SpIsShield("spIsShield", "쉴드여부", "PTC_STR_91TYPEINFOWTPART", "typeInfoWTPart.ptc_str_91", "SELECT", "SPECISSHIELD", false, false,
	    false, "", false, ""),
    // 커넥터 승인 -
    // Yes, NO
    SpESIRApproval("spESIRApproval", "커넥터 승인", "PTC_STR_92TYPEINFOWTPART", "typeInfoWTPart.ptc_str_92", "SELECT", "SPECESIRAPPROVAL",
	    false, false, false, "", false, ""),
    // ISIR 승인 -
    // Yes, NO
    // 승인번호 관리 가능한지 QA 확인 필요
    SpISIRApproval("spISIRApproval", "ISIR 승인", "PTC_STR_93TYPEINFOWTPART", "typeInfoWTPart.ptc_str_93", "SELECT", "SPECISIRAPPROVAL",
	    false, false, false, "", false, ""),
    // 커넥터 승인EO -
    SpConAppNoESIR("spConAppNoESIR", "커넥터 승인EO", "PTC_STR_94TYPEINFOWTPART", "typeInfoWTPart.ptc_str_94", "TEXT", "", false, false, false,
	    "", false, ""),
    // Layer -
    SpLayer("spLayer", "Layer", "PTC_STR_95TYPEINFOWTPART", "typeInfoWTPart.ptc_str_95", "SELECT", "SPECLAYER", false, false, false, "",
	    false, ""),
    // 원판 품번 -
    SpOrgDskPartNo("spOrgDskPartNo", "원판 품번", "PTC_STR_96TYPEINFOWTPART", "typeInfoWTPart.ptc_str_96", "TEXT", "", false, false, false, "",
	    false, ""),
    // PCB 재질 -
    SpPCBMaterial("spPCBMaterial", "PCB 재질", "PTC_STR_97TYPEINFOWTPART", "typeInfoWTPart.ptc_str_97", "SELECT", "SPECPCBMATERIAL", false,
	    false, false, "", false, ""),
    // Tg -
    // 130~140℃,141~169℃,170℃이상,기타
    // Multi Select
    SpTg("spTg", "Tg", "PTC_STR_98TYPEINFOWTPART", "typeInfoWTPart.ptc_str_98", "SELECT", "SPECTG", true, false, false, "", false, ""),
    // 특성 -
    // Lead-Free,Anti-CAF,Halogen Free,CTI
    // Multi Select
    SpCharact("spCharact", "특성", "PTC_STR_99TYPEINFOWTPART", "typeInfoWTPart.ptc_str_99", "SELECT", "SPECCHARACTERISTICS", true, false,
	    false, "", false, ""),
    // 표면처리 -
    // H.S.A.L, O.S.P, E.N.I.G, TIN 도금, 기타
    // Multi Select
    SpSurfaceTreat("spSurfaceTreat", "표면처리", "PTC_STR_100TYPEINFOWTPART", "typeInfoWTPart.ptc_str_100", "SELECT", "SPECSURFACETREATMENT",
	    true, false, false, "", false, ""),
    // 외형가공 -
    // ROUTING, V-CUT, PRESS, LASER, 기타
    // Multi Select
    SpAppearanProc("spAppearanProc", "외형가공", "PTC_STR_101TYPEINFOWTPART", "typeInfoWTPart.ptc_str_101", "SELECT", "SPECAPPEARANCEPROCESS",
	    true, false, false, "", false, ""),
    // Solder Resistor Color -
    // Green, Black, White, 기타
    // Multi Select
    SpSoldResColor("spSoldResColor", "Solder Resistor Color", "PTC_STR_102TYPEINFOWTPART", "typeInfoWTPart.ptc_str_102", "SELECT",
	    "SPECSOLDERRESISTORCOLOR", true, false, false, "", false, ""),
    // 장착위치 -
    SpInstallPos("spInstallPos", "장착위치", "PTC_STR_103TYPEINFOWTPART", "typeInfoWTPart.ptc_str_103", "SELECT", "SPECINSTALLPOSITION", false,
	    false, false, "", false, ""),
    // S/W Version -
    SpSwVersion("spSwVersion", "S/W Version", "PTC_STR_104TYPEINFOWTPART", "typeInfoWTPart.ptc_str_104", "TEXT", "", false, false, false,
	    "", false, ""),
    // 단위 -
    SpUnitUnit("spUnitUnit", "단위", "PTC_STR_105TYPEINFOWTPART", "typeInfoWTPart.ptc_str_105", "SELECT", "SPECUNITUNIT", false, false,
	    false, "", false, ""),
    // RES 단위 -
    SpResUnit("spResUnit", "RES 단위", "PTC_STR_106TYPEINFOWTPART", "typeInfoWTPart.ptc_str_106", "SELECT", "SPECRESUNIT", false, false,
	    false, "", false, ""),
    // IND 단위 -
    SpINDUnit("spINDUnit", "IND 단위", "PTC_STR_107TYPEINFOWTPART", "typeInfoWTPart.ptc_str_107", "SELECT", "SPECINDUNIT", false, false,
	    false, "", false, ""),
    // CAP 단위 -
    SpCapUnit("spCapUnit", "CAP 단위", "PTC_STR_108TYPEINFOWTPART", "typeInfoWTPart.ptc_str_108", "SELECT", "SPECCAPUNIT", false, false,
	    false, "", false, ""),
    // RLC Package type -
    SpRLCPackType("spRLCPackType", "RLC Package type", "PTC_STR_109TYPEINFOWTPART", "typeInfoWTPart.ptc_str_109", "SELECT",
	    "SPECRLCPACKAGETYPE", false, false, false, "", false, ""),
    // RLC Tolerance -
    SpRLCTolerance("spRLCTolerance", "RLC Tolerance", "PTC_STR_110TYPEINFOWTPART", "typeInfoWTPart.ptc_str_110", "SELECT",
	    "SPECRLCTOLERANCE", false, false, false, "", false, ""),
    // 제전방지 -
    SpFestPrevent("spFestPrevent", "제전방지", "PTC_STR_111TYPEINFOWTPART", "typeInfoWTPart.ptc_str_111", "SELECT", "SPFESTPREVENT", false,
	    false, false, "", false, ""),
    // 커넥터 결합 방식 -
    SpConnCombDir("spConnCombDir", "커넥터 결합 방식", "PTC_STR_112TYPEINFOWTPART", "typeInfoWTPart.ptc_str_112", "SELECT", "SPCONNCOMBDIR",
	    false, false, false, "", false, ""),
    // Certified -
    // 표준 규격 인증 ????
    SpCertified("spCertified", "Certified", "PTC_STR_113TYPEINFOWTPART", "typeInfoWTPart.ptc_str_113", "SELECT", "SPECCERTIFIED", false,
	    false, false, "", false, ""),
    // 포장사양 -
    SpPackageSpec("spPackageSpec", "포장사양", "PTC_STR_114TYPEINFOWTPART", "typeInfoWTPart.ptc_str_114", "SELECT", "SPPACKAGESPEC", false,
	    false, false, "", false, ""),
    // tap size -
    SpTabSize("spTabSize", "Tab Width(inch)", "PTC_STR_115TYPEINFOWTPART", "typeInfoWTPart.ptc_str_115", "TEXT", "", false, false, false,
	    "", false, ""),
    // Seal Type -
    SpSealType("spSealType", "Seal Type", "PTC_STR_116TYPEINFOWTPART", "typeInfoWTPart.ptc_str_116", "SELECT", "SPECSEALTYPE", false,
	    false, false, "", false, ""),
    // Stud Size -
    SpStudSize("spStudSize", "Stud Size", "PTC_STR_117TYPEINFOWTPART", "typeInfoWTPart.ptc_str_117", "TEXT", "", false, false, false, "",
	    false, ""),
    // Pitch -
    SpPitch("spPitch", "Pitch", "PTC_STR_118TYPEINFOWTPART", "typeInfoWTPart.ptc_str_118", "TEXT", "", false, false, false, "", false, ""),
    // 납땜 방식 -
    SpSoldering("spSoldering", "납땜 방식", "PTC_STR_119TYPEINFOWTPART", "typeInfoWTPart.ptc_str_119", "SELECT", "SPECSOLDERING", false, false,
	    false, "", false, ""),
    // 조립 공법 -
    SpConstrucMeth("spConstrucMeth", "조립 공법", "PTC_STR_120TYPEINFOWTPART", "typeInfoWTPart.ptc_str_120", "SELECT", "SPECCONSTRUCMETH",
	    false, false, false, "", false, ""),
    // 적용온도 (사용온도) -
    SpOptimumTemp("spOptimumTemp", "적용온도 (사용온도)", "PTC_STR_121TYPEINFOWTPART", "typeInfoWTPart.ptc_str_121", "TEXT", "", false, false,
	    false, "", false, ""),
    // 접촉저항 -
    SpContactRes("spContactRes", "접촉저항", "PTC_STR_122TYPEINFOWTPART", "typeInfoWTPart.ptc_str_122", "TEXT", "", false, false, false, "",
	    false, ""),
    // 절연저항 -
    SpInsulatRes("spInsulatRes", "절연저항", "PTC_STR_123TYPEINFOWTPART", "typeInfoWTPart.ptc_str_123", "TEXT", "", false, false, false, "",
	    false, ""),
    // conn't 높이 (height) -
    SpConntHeight("spConntHeight", "conn't 높이 (height)", "PTC_STR_124TYPEINFOWTPART", "typeInfoWTPart.ptc_str_124", "TEXT", "", false,
	    false, false, "", false, ""),
    // Cable 연결 방식 -
    SpCableConMeth("spCableConMeth", "Cable 연결 방식", "PTC_STR_125TYPEINFOWTPART", "typeInfoWTPart.ptc_str_125", "SELECT",
	    "SPECCABLECONMETH", false, false, false, "", false, ""),
    // 터미널 TYPE -
    SpTerminalType("spTerminalType", "터미널 TYPE", "PTC_STR_126TYPEINFOWTPART", "typeInfoWTPart.ptc_str_126", "SELECT", "SPECTERMINALTYPE",
	    false, false, false, "", false, ""),
    // 터미널 BARREL-SIZE -
    SpTermBarrelSz("spTermBarrelSz", "터미널 BARREL-SIZE", "PTC_STR_127TYPEINFOWTPART", "typeInfoWTPart.ptc_str_127", "SELECT",
	    "SPECTERMBARRELSZ", false, false, false, "", false, ""),
    // 터미널 압착 방식 -
    SpTermPrezType("spTermPrezType", "터미널 압착 방식", "PTC_STR_128TYPEINFOWTPART", "typeInfoWTPart.ptc_str_128", "SELECT", "SPECTERMPREZTYPE",
	    false, false, false, "", false, ""),
    // GWIT 재질여부 -
    SpGWITMaterAt("spGWITMaterAt", "GWIT 재질여부", "PTC_STR_129TYPEINFOWTPART", "typeInfoWTPart.ptc_str_129", "SELECT", "SPECGWITMATERAT",
	    false, false, false, "", false, ""),
    // 인터페이스 -
    SpInterface("spInterface", "인터페이스", "PTC_STR_130TYPEINFOWTPART", "typeInfoWTPart.ptc_str_130", "SELECT", "SPECINTERFACE", false, false,
	    true, "", false, ""),
    // 핀수&형상 -
    SpPinNShape("spPinNShape", "핀수&형상", "PTC_STR_131TYPEINFOWTPART", "typeInfoWTPart.ptc_str_131", "SELECT", "SPECPINNSHAPE", false, false,
	    true, "", false, ""),
    // SUB 공급형태 -
    SpSUBSuppliy("spSUBSuppliy", "SUB 공급형태", "PTC_STR_132TYPEINFOWTPART", "typeInfoWTPart.ptc_str_132", "SELECT", "SPECSUBSUPPLIY", false,
	    false, true, "", false, ""),
    // 코딩&색상 -
    SpCodingNColor("spCodingNColor", "코딩&색상", "PTC_STR_133TYPEINFOWTPART", "typeInfoWTPart.ptc_str_133", "SELECT", "SPECCODINGNCOLOR",
	    false, false, true, "", false, ""),
    // 플랜트[erp DieNo] -
    SpPlant("spPlant", "플랜트", "PTC_STR_134TYPEINFOWTPART", "typeInfoWTPart.ptc_str_134", "SELECT", "SPECPLANT", false, false, false, "",
	    true, ""),
    // 구매담당자 [ERP 구매그룹] -
    SpPuchaseGroup("spPuchaseGroup", "구매담당자", "PTC_STR_135TYPEINFOWTPART", "typeInfoWTPart.ptc_str_135", "SELECT", "SPECPURCHASEGROUP",
	    false, false, false, "", true, ""),
    // 개발담당자 [ERP 개발담당자명] -
    SpDevManNm("spDevManNm", "개발담당자", "PTC_STR_136TYPEINFOWTPART", "typeInfoWTPart.ptc_str_136", "TEXT", "", false, false, false, "", true,
	    ""),
    // 금형담당자 [ERP 설계담당자명] -
    SpDesignManNm("spDesignManNm", "금형담당자", "PTC_STR_137TYPEINFOWTPART", "typeInfoWTPart.ptc_str_137", "TEXT", "", false, false, false, "",
	    true, ""),
    // 제작담당자 [ERP 제작담당자명] -
    SpManufacManNm("spManufacManNm", "제작담당자", "PTC_STR_138TYPEINFOWTPART", "typeInfoWTPart.ptc_str_138", "TEXT", "", false, false, false,
	    "", true, ""),
    // 사급구분 [ERP 사급구분] -
    SpMContractSAt("spMContractSAt", "사급구분", "PTC_STR_139TYPEINFOWTPART", "typeInfoWTPart.ptc_str_139", "SELECT", "SPECMSELFCONTRACTFLAG",
	    false, false, false, "", true, ""),
    // 금형구분 [ERP 금형구분] -
    SpMMoldAt("spMMoldAt", "금형구분", "PTC_STR_140TYPEINFOWTPART", "typeInfoWTPart.ptc_str_140", "SELECT", "SPECMMOLDFLAG", false, false,
	    false, "", true, ""),
    // 제작구분 [ERP 제작구분] -
    SpMMakingAt("spMMakingAt", "제작구분", "PTC_STR_141TYPEINFOWTPART", "typeInfoWTPart.ptc_str_141", "SELECT", "SPECMMAKINGFLAG", false,
	    false, false, "", true, ""),
    // 생산구분 [ERP 생산구분] -
    SpMProdAt("spMProdAt", "생산구분", "PTC_STR_142TYPEINFOWTPART", "typeInfoWTPart.ptc_str_142", "SELECT", "SPECMPRODFLAG", false, false,
	    false, "", true, ""),
    // 금형제작처 [ERP 제작담당자명] - SELECT PRODUCTIONDEPT|KETPARTNER
    SpDieWhere("spDieWhere", "금형제작처", "PTC_STR_143TYPEINFOWTPART", "typeInfoWTPart.ptc_str_143", "TEXT", "", false, false, false, "", true,
	    ""),
    // 목표 C/T (SPM) [ERP 표준 SPM(C/T)] -
    SpObjectiveCT("spObjectiveCT", "목표 C/T (SPM)", "PTC_STR_144TYPEINFOWTPART", "typeInfoWTPart.ptc_str_144", "TEXT", "", false, false,
	    false, "", true, ""),
    // Cavity [ERP 표준 Cavity] -
    SpCavityStd("spCavityStd", "Cavity", "PTC_STR_145TYPEINFOWTPART", "typeInfoWTPart.ptc_str_145", "TEXT", "", false, false, false, "",
	    true, ""),
    // 실장방식&적용전선 -
    SpMountWayApE("spMountWayApE", "실장방식&적용전선", "PTC_STR_146TYPEINFOWTPART", "typeInfoWTPart.ptc_str_146", "SELECT", "SPMOUNTWAYAPE",
	    false, false, true, "", false, ""),
    // Thickness(W/H) -
    SpThickWH("spThickWH", "Thickness(W/H)", "PTC_STR_149TYPEINFOWTPART", "typeInfoWTPart.ptc_str_149", "TEXT", "", false, false, false,
	    "", false, ""),
    // 기타 -
    SpPropEtc("spPropEtc", "기타", "PTC_STR_157TYPEINFOWTPART", "typeInfoWTPart.ptc_str_157", "TEXT", "", false, false, false, "", false, ""),
    // 15.11.25 added
    // 고전압 센싱 범위 -
    SpHighVoltageSensingLimit("spHighVoltageSensingLimit", "고전압 센싱 범위", "PTC_STR_158TYPEINFOWTPART", "typeInfoWTPart.ptc_str_158", "TEXT",
	    "", false, false, false, "", false, ""),
    // 동작 전압 -
    SpMovVoltage("spMovVoltage", "동작 전압", "PTC_STR_159TYPEINFOWTPART", "typeInfoWTPart.ptc_str_159", "TEXT", "", false, false, false, "",
	    false, ""),
    // 톨러런스(센싱) -
    SpToleranceSenSing("spToleranceSensing", "톨러런스(센싱)", "PTC_STR_160TYPEINFOWTPART", "typeInfoWTPart.ptc_str_160", "TEXT", "", false,
	    false, false, "", false, ""),
    // ESIR 승인 -
    SpEsirApprove("spEsirApprove", "ESIR 승인", "PTC_STR_161TYPEINFOWTPART", "typeInfoWTPart.ptc_str_161", "TEXT", "", false, false, false,
	    "", false, ""),
    // Main Chipset -
    SpMainChipset("spMainChipset", "Main Chipset", "PTC_STR_162TYPEINFOWTPART", "typeInfoWTPart.ptc_str_162", "TEXT", "", false, false,
	    false, "", false, ""),
    // Frequency -
    SpFrequency("spFrequency", "Frequency", "PTC_STR_163TYPEINFOWTPART", "typeInfoWTPart.ptc_str_163", "TEXT", "", false, false, false, "",
	    false, ""),
    // DC Power -
    SpDcpower("spDcpower", "DC Power", "PTC_STR_164TYPEINFOWTPART", "typeInfoWTPart.ptc_str_164", "TEXT", "", false, false, false, "",
	    false, ""),
    // Interface -
    SpInterfaceIt("spInterfaceIt", "Interface", "PTC_STR_165TYPEINFOWTPART", "typeInfoWTPart.ptc_str_165", "TEXT", "", false, false, false,
	    "", false, ""),
    // Features -
    SpFeatures("spFeatures", "Features", "PTC_STR_166TYPEINFOWTPART", "typeInfoWTPart.ptc_str_166", "TEXT", "", false, false, false, "",
	    false, ""),
    // 개발스펙 -
    SpDevSpec("spDevSpec", "개발스펙", "PTC_STR_167TYPEINFOWTPART", "typeInfoWTPart.ptc_str_167", "TEXT", "", false, false, false, "", false,
	    ""),
    // 난연등급 - select
    SpFlameLevel("spFlameLevel", "난연등급", "PTC_STR_168TYPEINFOWTPART", "typeInfoWTPart.ptc_str_168", "SELECT", "SPECMFLAMELEVEL", false,
	    false, false, "", false, ""),
    // 정격전압 -
    SpERatedVoltage("spERatedVoltage", "정격전압", "PTC_STR_169TYPEINFOWTPART", "typeInfoWTPart.ptc_str_169", "TEXT", "", false, false, false,
	    "", false, ""),
    // 품질인증번호 -
    SpQCNo("spQCNo", "품질인증번호", "PTC_STR_170TYPEINFOWTPART", "typeInfoWTPart.ptc_str_170", "TEXT", "", false, false, false, "", false, ""),
    // Actuator 위치구분 - select
    SpActuatorLctn("spActuatorLctn", "Actuator (위치구분)", "PTC_STR_171TYPEINFOWTPART", "typeInfoWTPart.ptc_str_171", "SELECT",
	    "SPECMACLCTNFLAG", false, false, false, "", false, ""),
    // 최대허용주파수 -
    SpMaxFrequency("spMaxFrequency", "최대허용주파수", "PTC_STR_172TYPEINFOWTPART", "typeInfoWTPart.ptc_str_172", "TEXT", "", false, false, false,
	    "", false, ""),
    // 임피던스 -
    SpImpedance("spImpedance", "임피던스", "PTC_STR_173TYPEINFOWTPART", "typeInfoWTPart.ptc_str_173", "TEXT", "", false, false, false, "",
	    false, ""),
    // 홈페이지 등록 -
    HomepageIF("homepageIF", "홈페이지 등록", "PTC_STR_174TYPEINFOWTPART", "typeInfoWTPart.ptc_str_174", "SELECT", "SPECISYAZAKI", false, false,
	    false, "", false, ""),
    // 홈페이지 2D 등록 -
    Homepage2DIF("homepage2DIF", "홈페이지 (2D) 등록", "PTC_STR_175TYPEINFOWTPART", "typeInfoWTPart.ptc_str_175", "SELECT", "SPECISYAZAKI",
	    false, false, false, "", false, ""),
    // 홈페이지 3D 등록 -
    Hompage3DIF("hompage3DIF", "홈페이지 (3D) 등록", "PTC_STR_176TYPEINFOWTPART", "typeInfoWTPart.ptc_str_176", "SELECT", "SPECISYAZAKI", false,
	    false, false, "", false, ""),
    // 이미지 등록 -
    HompageImgIF("hompageImgIF", "이미지 등록", "PTC_STR_177TYPEINFOWTPART", "typeInfoWTPart.ptc_str_177", "TEXT", "", false, false, false, "",
	    false, ""),
    // 표준커넥터 -
    spStandardConnect("spStandardConnect", "용도", "PTC_STR_178TYPEINFOWTPART", "typeInfoWTPart.ptc_str_178", "SELECT", "SPSTANDARDCONNECT",
	    false, false, false, "", false, ""),
    // 커넥터스타일 -
    spConnectorStyle("spConnectorStyle", "커넥터스타일", "PTC_STR_179TYPEINFOWTPART", "typeInfoWTPart.ptc_str_179", "SELECT", "SPCONNECTORSTYLE",
	    false, false, false, "", false, ""),
    // 입고처 -
    WhPlant("whPlant", "입고처", "PTC_STR_180TYPEINFOWTPART", "typeInfoWTPart.ptc_str_180", "SELECT", "COSTWAREHOUSING", false, false, false,
	    "", false, ""),

    RatedVoltage("ratedVoltage", "Rated Voltage(고전압릴레이)", "PTC_STR_181TYPEINFOWTPART", "typeInfoWTPart.ptc_str_181", "SELECT",
	    "RATEDVOLTAGE", false, false, true, "", false, ""),

    RatedCurrent("ratedCurrent", "Rated Current", "PTC_STR_182TYPEINFOWTPART", "typeInfoWTPart.ptc_str_182", "SELECT", "RATEDCURRENT",
	    false, false, true, "", false, ""),

    MountingType("mountingType", "Mounting Type", "PTC_STR_183TYPEINFOWTPART", "typeInfoWTPart.ptc_str_183", "SELECT", "MOUNTINGTYPE",
	    false, false, true, "", false, ""), ;

    private String attrCode;
    private String attrName;
    private String columnName; // DB column 명
    private String querySpecCode; // querySpec 용 String
    private String attrInputType; // textBox, selectBox
    private String attrCodeType; // select Box NumberCode
    private boolean attrMultiSelect; // multi select 여부
    private boolean numberType; // 숫자만 입력
    private boolean useNumbering; // 채번에 사용
    private String attrUnit; // 기본 단위
    private boolean sendReceiveErp; // Erp 전송여부
    private String erpDesc;

    private PartSpecEnum(String attrCode, String attrName, String columnName, String querySpecCode, String attrInputType,
	    String attrCodeType, boolean attrMultiSelect, boolean numberType, boolean useNumbering, String attrUnit,
	    boolean sendReceiveErp, String erpDesc) {
	this.attrCode = attrCode;
	this.attrName = attrName;
	this.columnName = columnName;
	this.querySpecCode = querySpecCode;
	this.attrInputType = attrInputType;
	this.attrCodeType = attrCodeType;
	this.attrMultiSelect = attrMultiSelect;
	this.numberType = numberType;
	this.useNumbering = useNumbering;
	this.attrUnit = attrUnit;
	this.sendReceiveErp = sendReceiveErp;
	this.erpDesc = erpDesc;
    }

    public static PartSpecEnum toPartSpecEnumByAttrCode(String attrCode) {
	if (attrCode == null)
	    return null;

	PartSpecEnum ret = null;

	PartSpecEnum[] arry = PartSpecEnum.values();
	for (PartSpecEnum item : arry) {
	    if (attrCode.equalsIgnoreCase(item.getAttrCode())) {
		ret = item;
		break;
	    }
	}

	return ret;
    }

    public static void main(String[] args) {

	PartSpecEnum[] list = PartSpecEnum.values();

	// for (PartSpecEnum item : list) {
	// if (item.getSendReceiveErp()) {
	// Kogger.debug(getClass(), "ERP:" + item.getAttrName());
	// }
	// }

	int count = 1;

	for (PartSpecEnum item : list) {
	    if (item.getAttrInputType().indexOf("SELECT") != -1) {
		// Kogger.debug(getClass(), "SELECT:" + count + ":" + item.getAttrCodeType() + ":" + item.getAttrName());
		Kogger.debug(PartSpecEnum.class, item.getAttrCodeType());
		count++;
	    }
	}

	// for (PartSpecEnum item : list) {
	// if (item.getAttrMultiSelect()) {
	// Kogger.debug(getClass(), "Milti SELECT:" + item.getAttrCode() + " > " + item.getAttrName());
	// }
	// }
    }

    public String getAttrCode() {
	return attrCode;
    }

    public String getAttrName() {
	return attrName;
    }

    public String getColumnName() {
	return columnName;
    }

    public String getAttrInputType() {
	return attrInputType;
    }

    public boolean getUseNumbering() {
	return useNumbering;
    }

    public String getAttrUnit() {
	return attrUnit;
    }

    public boolean getSendReceiveErp() {
	return sendReceiveErp;
    }

    public boolean getNumberType() {
	return numberType;
    }

    public String getErpDesc() {
	return erpDesc;
    }

    public String getQuerySpecCode() {
	return querySpecCode;
    }

    public String getAttrCodeType() {
	return attrCodeType;
    }

    public boolean getAttrMultiSelect() {
	return attrMultiSelect;
    }

}
