package ext.ket.part.code.internal;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import e3ps.cost.util.StringUtil;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.spec.util.PartTypeEnum;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.code.service.CodeHelper;

/**
 * 제품군 2자리 + 일련번호 4자리 + 구분자 1자리 + 색상,도금사양 2자리 [색상이 NEUTRAL일 경우 3,4 코드 삭제]
 * 
 * @클래스명 : PartFertNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartEliecItNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {

	String numberingCode = dto.getNumberingCode();

	// KR - FAKRA
	if (numberingCode != null && (numberingCode.startsWith("KR"))) {
	    return getFertHalbFakraNo(dto, baseDto);
	}
	// K7 : HAWA
	else if (numberingCode != null && (numberingCode.startsWith("K7"))) {
	    return getFertHawaK7No(dto, baseDto);
	}
	// K5 : 제품
	else if (numberingCode != null && (numberingCode.startsWith("K5"))) {
	    return getFertHalbK5No(dto, baseDto);
	}
	// K1, K2, K3... 반제품 생성
	else if (numberingCode != null && ( (numberingCode.startsWith("K1")) 
		|| (numberingCode.startsWith("K2")) || (numberingCode.startsWith("K3")) )) {
	    return getHalbK123No(dto, baseDto);
	}
	// K : Hawa인 경우 : 고객품번
	else if (numberingCode != null && (numberingCode.startsWith("K"))) {
	    return getFertHawaKNo(dto, baseDto);
	}else{
	    throw new Exception("채번할 수 없는 분류체계 넘버코드가 넘어 왔습니다.");
	}
    }
    
    // FAKARA인 경우 - HKR
    private String getFertHalbFakraNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	String prefix3No = dto.getNumberingCode(); // KR
	String minNo = "-"; // dto.getNumberingMinNo();
//	if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)){
//	    minNo = minNo.substring(prefix3No.length());
//	}
	
	PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(baseDto.getSpPartType());
	if (ptype == PartTypeEnum.HALB && !prefix3No.startsWith("H")) {
	    prefix3No = "H" + prefix3No;
	}

	String gubun1No = "-";

	String prodCode1No = baseDto.getSpInterface(); // 인터페이스 // spInterface // B to W / W to W / 방수 W to W 제품군 구분 유의미 코드 
	String specCode1No = baseDto.getSpPinNShape(); // 핀수 & 형상 // spPinNShape // PIN 수 / 사양별 구분 유의미 코드 
	String subAssyProdCode1No = baseDto.getSpSUBSuppliy(); // SUB공급형태 // spSUBSuppliy // sub assy, 부품군 > 부품군 구별 유의미 코드 - 고객사 판매 단위 구분
	
	if(StringUtils.isEmpty(prodCode1No) || StringUtils.isEmpty(specCode1No) || StringUtils.isEmpty(subAssyProdCode1No) ){
	    throw new Exception("채번에 사용할 부품 속성 값이 비어 있습니다.");
	}
	
	prodCode1No = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpInterface.getAttrCodeType() , prodCode1No, Locale.KOREA );
	specCode1No = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpPinNShape.getAttrCodeType() , specCode1No, Locale.KOREA );
	subAssyProdCode1No = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpSUBSuppliy.getAttrCodeType() , subAssyProdCode1No, Locale.KOREA );
	
	if(StringUtils.isEmpty(prodCode1No) || StringUtils.isEmpty(specCode1No) || StringUtils.isEmpty(subAssyProdCode1No) ){
	    throw new Exception("채번에 사용할 부품 코드체계의 채번[Description] 값이 비어 있습니다.");
	}

	String serial2No = null;
	
	if(USE_MAX){
	    serial2No = getMaxSerial(prefix3No + prodCode1No + specCode1No + subAssyProdCode1No, "", 2, gubun1No, dto.getClassPartType(), true);
	    if (StringUtils.isEmpty(serial2No))
		serial2No = SERIAL_2;
	    else
		serial2No = String.valueOf(Integer.parseInt(serial2No) + 1);
	}else{
	    serial2No = getMinSerial(prefix3No + prodCode1No + specCode1No + subAssyProdCode1No, "", 2, gubun1No, dto.getClassPartType(), true, minNo);
	}
	
	if (isMaxOrOver(serial2No, 2))
	    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

	String end3No = "";

	// 1자리 : 실장방식& 적용전선 : spMountWayApE
	String addSpecGroupCode1No = baseDto.getSpMountWayApE(); // SD0123
	if(StringUtils.isNotEmpty(addSpecGroupCode1No)){
	    addSpecGroupCode1No = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpMountWayApE.getAttrCodeType(), addSpecGroupCode1No, Locale.KOREA);
	}
	// 1자리 : 코딩&색상 & spCodingNColor
	String prodCodingSpecGroupCode1No = baseDto.getSpCodingNColor();
	if(StringUtils.isNotEmpty(prodCodingSpecGroupCode1No)){
	    prodCodingSpecGroupCode1No = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpCodingNColor.getAttrCodeType() , prodCodingSpecGroupCode1No, Locale.KOREA );
	}
	
	if (StringUtils.isNotEmpty(addSpecGroupCode1No) || StringUtils.isNotEmpty(prodCodingSpecGroupCode1No)) {
	    end3No = gubun1No + StringUtil.checkNull(addSpecGroupCode1No) + StringUtil.checkNull(prodCodingSpecGroupCode1No);
	}

	//String partNo = prefix3No + prodCode1No + specCode1No + subAssyProdCode1No + getSerailNumberFormat(serial2No, 2) + end3No;
	String partNo = erpCheckNumber ( prefix3No + prodCode1No + specCode1No + subAssyProdCode1No, getSerailNumberFormat(serial2No, 2), end3No);
	
	return partNo;
    }
    
    // HAWA - K7
    private String getFertHawaK7No(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	
	String prefix2No = dto.getNumberingCode();
	String minNo = dto.getNumberingMinNo();
	if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)){
	    minNo = minNo.substring(prefix2No.length());
	}
	
	String gubun1No = "-";
	String end0No = "";
	String serial5No = null;
	
	if(USE_MAX){
	    serial5No = getMaxSerial(prefix2No, "", 5, gubun1No, dto.getClassPartType(), true);
	    if (StringUtils.isEmpty(serial5No))
		serial5No = SERIAL_5;
	    else
		serial5No = String.valueOf(Integer.parseInt(serial5No) + 1);
	}else{
	    serial5No = getMinSerial(prefix2No, "", 5, gubun1No, dto.getClassPartType(), true, minNo);
	}

	if (isMaxOrOver(serial5No, 5))
	    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

	// String partNo = prefix3No + getSerailNumberFormat(serial5No, 5) + endGubun1Plus3No;
	String partNo = erpCheckNumber(prefix2No, getSerailNumberFormat(serial5No, 5), end0No);

	return partNo;

    }
    
    // HALB - K5
    private String getFertHalbK5No(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	
	String prefix3No = dto.getNumberingCode();
	String minNo = dto.getNumberingMinNo();
	if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)){
	    minNo = minNo.substring(prefix3No.length());
	}
	
	PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(baseDto.getSpPartType());
	if (ptype == PartTypeEnum.HALB && !prefix3No.startsWith("H")) {
	    prefix3No = "H" + prefix3No;
	}
	
	String gubun1No = "-";
	
	String endGubun1Plus3No = "";
	
	String numberingCharics = dto.getNumberingCharics();
	
	if ("S".equals(numberingCharics)) { // 핀수 SpNoOfPole : 19 – 19PIN
	    
	    String noOfPole = baseDto.getSpNoOfPole();
	    String noOfPoleCode = getNoOfPoleCode(noOfPole);
	    
	    String pinNShapeCode2No = "";
	    if (StringUtils.isEmpty(noOfPoleCode)) {
	    } else {
		pinNShapeCode2No = noOfPoleCode;
	    }

	    if (StringUtils.isEmpty(pinNShapeCode2No)) {
		endGubun1Plus3No = pinNShapeCode2No;
	    } else {
		endGubun1Plus3No = gubun1No + pinNShapeCode2No;
	    }
	}
	
	String serial5No = null;
	if(USE_MAX){
	    serial5No = getMaxSerial(prefix3No, "", 5, gubun1No, dto.getClassPartType(), true);
	    if (StringUtils.isEmpty(serial5No))
		serial5No = SERIAL_5;
	    else
		serial5No = String.valueOf(Integer.parseInt(serial5No) + 1);
	}else{
	    serial5No = getMinSerial(prefix3No, "", 5, gubun1No, dto.getClassPartType(), true, minNo);
	}
	
	if (isMaxOrOver(serial5No, 5))
	    throw new Exception("## 채번 Serial 한도 초과되었습니다.");
	
	//String partNo = prefix3No + getSerailNumberFormat(serial5No, 5) + endGubun1Plus3No;
	String partNo = erpCheckNumber ( prefix3No, getSerailNumberFormat(serial5No, 5), endGubun1Plus3No);
	
	return partNo;
    }

    // 부품 - K1, K2, K3,,,
    private String getHalbK123No(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	
	String prefix2No = dto.getNumberingCode();
	String minNo = dto.getNumberingMinNo();
	if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)){
	    minNo = minNo.substring(prefix2No.length());
	}
	
	// K1, K2, K3는 PartTypeEnum을 붙이지 않음.
//	PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(baseDto.getSpPartType());
//	if (ptype == PartTypeEnum.HALB && !prefix2No.startsWith("H")) {
//	    prefix2No = "H" + prefix2No;
//	}
	
	String gubun1No = "-";

	String endGubun1Plus3No = "";
	String platingAt1No = "";
	
	// K1으로 시작
	if (prefix2No.startsWith("K1")) {
	    
	    // 컬러 spColorElec - CODE
	    String color = baseDto.getSpColorElec();
	    String colorCode2No = getColorCodeElec(color);

	    if (StringUtils.isEmpty(colorCode2No)) {
		endGubun1Plus3No = StringUtil.checkNull(colorCode2No);
	    } else {
		endGubun1Plus3No = gubun1No + colorCode2No;
	    }

	} else if (prefix2No.startsWith("K2")) { // K2로 시작하는 경우 동일 재채번 : StandardBaseService에 코딩
	    
	    // 도금
	    String plating = baseDto.getSpPlating();
	    String platingCode = getPlatingCodeElec(plating);

	    if (StringUtils.isEmpty(platingCode)) {
		endGubun1Plus3No = StringUtil.checkNull(platingCode);
	    } else {
		endGubun1Plus3No = gubun1No + platingCode;
	    }
	    
	} else if (prefix2No.startsWith("K3")) {
	    // nothing to do
	}

	String serial5No = null;
	
	if(USE_MAX){
	    serial5No = getMaxSerial(prefix2No, "", 5, gubun1No, dto.getClassPartType(), true);
	    if (StringUtils.isEmpty(serial5No))
		serial5No = SERIAL_5;
	    else
		serial5No = String.valueOf(Integer.parseInt(serial5No) + 1);

	}else{
	    serial5No = getMinSerial(prefix2No, "", 5, gubun1No, dto.getClassPartType(), true, minNo);
	}

	if (isMaxOrOver(serial5No, 5))
	    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

	//String partNo = prefix2No + getSerailNumberFormat(serial5No, 5) + platingAt1No + endGubun1Plus3No;
	String partNo = erpCheckNumber ( prefix2No, getSerailNumberFormat(serial5No, 5), platingAt1No + endGubun1Plus3No);

	return partNo;
    }
    
    // HAWA - K
    private String getFertHawaKNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	
	String prefix1No = dto.getNumberingCode();

	// 고객사 품번 :  SpCustomPartNo
	String spCustomPartNo = baseDto.getSpCustomPartNo();
	
	if(StringUtils.isNotEmpty(spCustomPartNo)){
	    
	    String partNo = prefix1No + spCustomPartNo;

	    if(existPlmNumber(partNo)){
		throw new Exception("채번에 사용할 품번 '" + partNo + "'이 PLM 시스템에 존재합니다.");
	    }
	    
	    if(existErpNumber(partNo)){
		throw new Exception("채번에 사용할 품번 '" + partNo + "'이 ERP에 존재합니다.");
	    }
		
	    return partNo;

	}else {
	    
	    throw new Exception("채번에 사용할 고객사 품번이 empty 입니다.");
	    
    	}

	
    }
    
}
