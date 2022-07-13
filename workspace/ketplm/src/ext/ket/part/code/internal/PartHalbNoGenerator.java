package ext.ket.part.code.internal;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import e3ps.cost.util.StringUtil;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.code.service.CodeHelper;

/**
 * H + 제품군 2자리 + 일련번호 4자리 + 도금전후 구분 (A) + 구분자 1자리 + 색상,도금사양 2자리 [색상이 NEUTRAL일 경우 3,4 코드 삭제]
 * 
 * @클래스명 : PartHalbNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartHalbNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	if ("C".equals(dto.getDivisionFlag())) { // 자동차
	    return generatePartNoByC(dto, baseDto);
	} else if ("E".equals(dto.getDivisionFlag())) { // 전자
	    return generatePartNoByE(dto, baseDto);
	} else {
	    throw new Exception("#사업부 오류 입니다.");
	}
    }

    // 1(H) + 2(채번코드) + 4(일련번호) + 1(A 도금전후) + 1(-) + 2(색상-6 Connector류/도금-7 Terminal류)
    public String generatePartNoByC(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	String prefix3No = dto.getNumberingCode(); // "H" 넣어 보내줌
	String minNo = dto.getNumberingMinNo();
	if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
	    minNo = minNo.substring(prefix3No.length());
	}

	if (!prefix3No.startsWith("H") && "F".equals(dto.getClassPartType()))
	    prefix3No = "H" + prefix3No;

	String gubun1No = "-";

	String platingAt1No = "";
	String endGubun1Plus3No = "";

	String numberingCharics = dto.getNumberingCharics();

	if (numberingCharics == null || numberingCharics.equals("")) {
	} else if ("C".equals(numberingCharics)) { // 컬러

	    String color = baseDto.getSpColor();
	    String colorCode2No = getColorCodeAuto(color);

	    if (StringUtils.isEmpty(colorCode2No) || "0".equals(colorCode2No)/* natural color */) {
		endGubun1Plus3No = ""; // StringUtil.checkNull(colorCode2No);
	    } else {
		endGubun1Plus3No = gubun1No + colorCode2No;
	    }

	} else if ("P".equals(numberingCharics)) { // 도금

	    String plating = baseDto.getSpPlating();
	    String platingCode = getPlatingCodeAuto(plating);

	    if (StringUtils.isEmpty(platingCode)) {
		endGubun1Plus3No = StringUtil.checkNull(platingCode);
	    } else {
		endGubun1Plus3No = gubun1No + platingCode;
	    }
	} else if ("S".equals(numberingCharics)) { // 핀수

	    String noOfPole = baseDto.getSpNoOfPole();
	    String noOfPoleCode = getNoOfPoleCode(noOfPole);

	    if (StringUtils.isEmpty(noOfPoleCode)) {
		endGubun1Plus3No = StringUtil.checkNull(noOfPoleCode);
	    } else {
		endGubun1Plus3No = gubun1No + noOfPoleCode;
	    }
	} else if ("D".equals(numberingCharics)) { // 개발단계

	    String devLevelCode = baseDto.getSpPartDevLevel();

	    if (StringUtils.isNotEmpty(devLevelCode) && "PC001".equals(devLevelCode)) {// 개발단계 Proto 일때만
		endGubun1Plus3No = gubun1No + "T";
	    }
	} else if ("R".equals(numberingCharics)) { // 고전압릴레이 신규채번으로 인한 추가 -> ratedVoltage + ratedCurrent + mountingType

	    endGubun1Plus3No = baseDto.getRatedVoltage() + baseDto.getRatedCurrent() + baseDto.getMountingType();

	}

	String checkFlag = dto.getClassPartType();
	if (checkFlag == null) {
	    if ("R68".equals(prefix3No)) {
		checkFlag = "R68";
	    }
	} else {
	    if ("R68".equals(prefix3No)) {
		checkFlag = checkFlag + "|R68";
	    }
	}

	String serial4No = null;

	if (USE_MAX) {
	    serial4No = getMaxSerial(prefix3No, "", 4, gubun1No, checkFlag, true);
	    if (StringUtils.isEmpty(serial4No))
		serial4No = SERIAL_4;
	    else
		serial4No = String.valueOf(Integer.parseInt(serial4No) + 1);
	} else {
	    serial4No = getMinSerial(prefix3No, "", 4, gubun1No, checkFlag, true, minNo);
	}

	if (isMaxOrOver(serial4No, 4))
	    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

	// String partNo = prefix3No + getSerailNumberFormat(serial4No, 4) + platingAt1No + endGubun1Plus3No;
	String partNo = erpCheckNumber(prefix3No, getSerailNumberFormat(serial4No, 4), platingAt1No + endGubun1Plus3No);

	return partNo;
    }

    // 2(채번코드[제품군+제품Type])+4(일련번호) +1(-)+2(색상) // 6-Housing
    // 2(채번코드[제품군+제품Type])+4(일련번호)+ 1(A 도금전후) +1(-)+2(도금)+2(바렐사양) // 7Terminal
    public String generatePartNoByE(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {

	String prefix3No = dto.getNumberingCode(); // "H" 넣어 보내줌
	String minNo = dto.getNumberingMinNo();
	if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
	    minNo = minNo.substring(prefix3No.length());
	}

	if (!prefix3No.startsWith("H") && "F".equals(dto.getClassPartType()))
	    prefix3No = "H" + prefix3No;

	String gubun1No = "-";

	String platingAt1No = "";
	String endGubun1Plus3No = "";

	String numberingCharics = dto.getNumberingCharics();

	if (numberingCharics == null || numberingCharics.equals("")) {
	} else if ("C".equals(numberingCharics)) { // 컬러

	    String color = baseDto.getSpColorElec();

	    if (StringUtils.isEmpty(color)) {

		color = baseDto.getSpColor();
		String colorCode2No = getColorCodeAuto(color);

		if (StringUtils.isEmpty(colorCode2No)) {
		    endGubun1Plus3No = StringUtil.checkNull(colorCode2No);
		} else {
		    endGubun1Plus3No = gubun1No + colorCode2No;
		}

	    } else {

		String colorCode2No = getColorCodeElec(color);

		if (StringUtils.isEmpty(colorCode2No)) {
		    endGubun1Plus3No = StringUtil.checkNull(colorCode2No);
		} else {
		    endGubun1Plus3No = gubun1No + colorCode2No;
		}
	    }

	} else if ("P".equals(numberingCharics)) { // 도금

	    String plating = baseDto.getSpPlating();
	    String platingCode = getPlatingCodeElec(plating);

	    if (StringUtils.isEmpty(platingCode)) {
		endGubun1Plus3No = StringUtil.checkNull(platingCode);
	    } else {
		endGubun1Plus3No = gubun1No + platingCode;
	    }

	    // 전자 터미널 K30으로 시작되면 BarrelSize를 - 뒤쪽 채번 코드에 붙여 줄것
	    String classCode = dto.getClassCode();
	    if (classCode != null && classCode.startsWith("K30")) {
		String barrelSize = baseDto.getSpTermBarrelSz();
		if (StringUtils.isNotEmpty(barrelSize)) {
		    String addedBarrelCode = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpTermBarrelSz.getAttrCodeType(),
			    barrelSize, Locale.KOREA);
		    if (StringUtils.isNotEmpty(addedBarrelCode)) {
			if (StringUtils.isEmpty(platingCode)) {
			    endGubun1Plus3No = gubun1No + addedBarrelCode;
			} else {
			    endGubun1Plus3No = gubun1No + platingCode + addedBarrelCode;
			}
		    }
		}
	    }
	} else if ("S".equals(numberingCharics)) { // 핀수

	    String noOfPole = baseDto.getSpNoOfPole();
	    String noOfPoleCode = getNoOfPoleCode(noOfPole);

	    if (StringUtils.isEmpty(noOfPoleCode)) {
		endGubun1Plus3No = StringUtil.checkNull(noOfPoleCode);
	    } else {
		endGubun1Plus3No = gubun1No + noOfPoleCode;
	    }
	} else if ("CS".equals(numberingCharics)) { // 컬러+핀수 ⇒ 전자사업부 채번특성 추가에 따른 소스코드 추가. 이 경우 컬러가 NATURAL 이면 0 + 핀수 2016.07.22 황정태 수정 박주미
		                                    // 요청

	    String color = baseDto.getSpColorElec();

	    if (StringUtils.isEmpty(color)) {
		color = baseDto.getSpColor();
		endGubun1Plus3No = getColorCodeAuto(color);
	    } else {
		endGubun1Plus3No = getColorCodeElec(color);
	    }

	    if ("NATURAL".equals(color.toUpperCase())) {
		endGubun1Plus3No = "0";
	    }

	    String noOfPole = baseDto.getSpNoOfPole();
	    String noOfPoleCode = getNoOfPoleCode(noOfPole);

	    endGubun1Plus3No = gubun1No + endGubun1Plus3No + noOfPoleCode;

	}

	String checkFlag = dto.getClassPartType();
	if (checkFlag == null) {
	    if ("R68".equals(prefix3No)) {
		checkFlag = "R68";
	    }
	} else {
	    if ("R68".equals(prefix3No)) {
		checkFlag = checkFlag + "|R68";
	    }
	}

	String serial4No = null;

	if (USE_MAX) {
	    serial4No = getMaxSerial(prefix3No, "", 4, gubun1No, checkFlag, true);
	    if (StringUtils.isEmpty(serial4No))
		serial4No = SERIAL_4;
	    else
		serial4No = String.valueOf(Integer.parseInt(serial4No) + 1);
	} else {
	    serial4No = getMinSerial(prefix3No, "", 4, gubun1No, checkFlag, true, minNo);
	}

	if (isMaxOrOver(serial4No, 4))
	    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

	// String partNo = prefix3No + getSerailNumberFormat(serial4No, 4) + platingAt1No + endGubun1Plus3No;
	String partNo = erpCheckNumber(prefix3No, getSerailNumberFormat(serial4No, 4), platingAt1No + endGubun1Plus3No);

	return partNo;
    }

}
