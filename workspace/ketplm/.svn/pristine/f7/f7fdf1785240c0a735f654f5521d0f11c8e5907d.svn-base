package ext.ket.part.code.internal;

import org.apache.commons.lang.StringUtils;

import e3ps.cost.util.StringUtil;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;

/**
 * T + 일련번호 6자리
 * 
 * @클래스명 : PartHawaNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartHawaNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {

	String prefixNo = dto.getNumberingCode(); // "T" 포함 않됨.

	// if ("E".equals(dto.getDivisionFlag())) {
	// throw new Exception("전자는 상품 등록이 금지되어 있습니다.");
	// }

	// // 전자 K로 시작하는 상품 등록 금지
	// if(prefix1No!= null || prefix1No.startsWith("K")){
	// throw new Exception("전자의 K로 시작하는 상품은 등록이 금지되어 있습니다.");
	// }

	// if(prefixNo== null || (!prefixNo.startsWith("R") && !prefixNo.startsWith("68") ) )
	// prefixNo = "T" + StringUtil.checkNull(prefixNo);

	String gubun1No = "-";

	String checkFlag = dto.getClassPartType();
	if (checkFlag == null) {
	    if (("68".equals(prefixNo))) {
		checkFlag = "68";
	    } else if ("R68".equals(prefixNo)) {
		checkFlag = "R68";
	    } else if ("50".equals(prefixNo)) {
		checkFlag = "50";
	    } else if ("R50".equals(prefixNo)) {
		checkFlag = "R50";
	    }
	} else {
	    if (("68".equals(prefixNo))) {
		checkFlag = checkFlag + "|68";
	    } else if ("R68".equals(prefixNo)) {
		checkFlag = checkFlag + "|R68";
	    } else if ("50".equals(prefixNo)) {
		checkFlag = checkFlag + "|50";
	    } else if ("R50".equals(prefixNo)) {
		checkFlag = checkFlag + "|R50";
	    }
	}

	String endGubun1Plus3No = getSpecialCharicters(dto, baseDto, gubun1No);

	if ("68".equals(prefixNo)) { // || "68".equals(prefixNo || "R68".equals(prefixNo)

	    String serial4No = null;
	    int FOUR = 4;

	    if (USE_MAX) {
		serial4No = getMaxSerial(prefixNo, "", FOUR, gubun1No, checkFlag, true);
		if (StringUtils.isEmpty(serial4No))
		    serial4No = SERIAL_4;
		else
		    serial4No = String.valueOf(Integer.parseInt(serial4No) + 1);
	    } else {
		String minNo = dto.getNumberingMinNo();
		if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
		    minNo = minNo.substring(minNo.length() - FOUR);
		}
		serial4No = getMinSerial(prefixNo, "", FOUR, gubun1No, checkFlag, true, minNo);
	    }

	    if (isMaxOrOver(serial4No, FOUR))
		throw new Exception(" 채번 Serial 한도 초과되었습니다.");

	    String partNo = erpCheckNumber(prefixNo, getSerailNumberFormat(serial4No, FOUR), endGubun1Plus3No);

	    return partNo;

	} else if (prefixNo.length() == 1) {

	    String serial5No = null;
	    int FIVE = 5;

	    if (USE_MAX) {
		serial5No = getMaxSerial(prefixNo, "", FIVE, gubun1No, checkFlag, true);
		if (StringUtils.isEmpty(serial5No))
		    serial5No = SERIAL_5;
		else
		    serial5No = String.valueOf(Integer.parseInt(serial5No) + 1);
	    } else {
		String minNo = dto.getNumberingMinNo();
		if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
		    minNo = minNo.substring(minNo.length() - FIVE);
		}
		serial5No = getMinSerial(prefixNo, "", FIVE, gubun1No, checkFlag, true, minNo);
	    }

	    if (isMaxOrOver(serial5No, FIVE))
		throw new Exception(" 채번 Serial 한도 초과되었습니다.");

	    // String partNo = prefix1No + getSerailNumberFormat(serial5No, FIVE);
	    String partNo = erpCheckNumber(prefixNo, getSerailNumberFormat(serial5No, FIVE), endGubun1Plus3No);

	    return partNo;

	} else if (prefixNo.length() == 2) {

	    String serial4No = null;
	    int FOUR = 4;

	    if (USE_MAX) {
		serial4No = getMaxSerial(prefixNo, "", FOUR, gubun1No, checkFlag, true);
		if (StringUtils.isEmpty(serial4No))
		    serial4No = SERIAL_4;
		else
		    serial4No = String.valueOf(Integer.parseInt(serial4No) + 1);
	    } else {
		String minNo = dto.getNumberingMinNo();
		if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
		    minNo = minNo.substring(minNo.length() - FOUR);
		}
		serial4No = getMinSerial(prefixNo, "", FOUR, gubun1No, checkFlag, true, minNo);
	    }

	    if (isMaxOrOver(serial4No, FOUR))
		throw new Exception(" 채번 Serial 한도 초과되었습니다.");

	    String partNo = erpCheckNumber(prefixNo, getSerailNumberFormat(serial4No, FOUR), endGubun1Plus3No);

	    return partNo;

	} else if (prefixNo.length() == 3) {

	    String serial3No = null;
	    int THREE = 3;

	    if (USE_MAX) {
		serial3No = getMaxSerial(prefixNo, "", THREE, gubun1No, checkFlag, true);
		if (StringUtils.isEmpty(serial3No))
		    serial3No = SERIAL_3;
		else
		    serial3No = String.valueOf(Integer.parseInt(serial3No) + 1);
	    } else {
		String minNo = dto.getNumberingMinNo();
		if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
		    minNo = minNo.substring(minNo.length() - THREE);
		}
		serial3No = getMinSerial(prefixNo, "", THREE, gubun1No, checkFlag, true, minNo);
	    }

	    if (isMaxOrOver(serial3No, THREE))
		throw new Exception(" 채번 Serial 한도 초과되었습니다.");

	    String partNo = erpCheckNumber(prefixNo, getSerailNumberFormat(serial3No, THREE), endGubun1Plus3No);

	    return partNo;

	} else {
	    throw new Exception("상품 채번 코드는 3자리가 될 수 없습니다.");
	}

    }

    public String getSpecialCharicters(PartGenDTO dto, PartBaseDTO baseDto, String gubun1No) throws Exception {

	String endGubun1Plus3No = "";

	String numberingCharics = dto.getNumberingCharics();

	if (numberingCharics == null || numberingCharics.equals("")) {
	} else if ("C".equals(numberingCharics)) { // 컬러

	    String color = baseDto.getSpColor();

	    String colorCode2No = getColorCodeAuto(color);

	    if (StringUtils.isEmpty(colorCode2No) && "E".equals(dto.getDivisionFlag())) {
		colorCode2No = getColorCodeElec(baseDto.getSpColorElec());
	    }

	    if (StringUtils.isEmpty(colorCode2No) || "0".equals(colorCode2No)/* natural color */) {
		endGubun1Plus3No = "";
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

	return endGubun1Plus3No;
    }

}
