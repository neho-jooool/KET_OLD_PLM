package ext.ket.part.code.internal;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.StringUtil;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;

/**
 * R + 제품군 2자리 + 재질/구매품구분 2자리 + 일련번호4
 * 
 * @클래스명 : PartRohNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartRohNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {
	String prefix3No = StringUtil.checkNull(dto.getNumberingCode()); // "R" 포함됨
	String minNo = dto.getNumberingMinNo();
	if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
	    minNo = minNo.substring(prefix3No.length());
	}

	if (!prefix3No.startsWith("R"))
	    prefix3No = "R" + prefix3No;

	String gubun1No = "-";

	String checkFlag = dto.getClassPartType();
	if (checkFlag == null) {
	    // if(("68".equals(prefix3No))){
	    // checkFlag = "68";
	    // }else
	    if ("R68".equals(prefix3No)) {
		checkFlag = "R68";
	    } else if ("R50".equals(prefix3No)) {
		checkFlag = "R50";
	    }
	} else {
	    // if(("68".equals(prefix3No))){
	    // checkFlag = checkFlag+"|68";
	    // }else
	    if ("R68".equals(prefix3No)) {
		checkFlag = checkFlag + "|R68";
	    } else if ("R50".equals(prefix3No)) {
		checkFlag = checkFlag + "|R50";
	    }
	}

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

	// String partNo = prefix3No + getSerailNumberFormat(serial4No, 4);
	String partNo = erpCheckNumber(prefix3No, getSerailNumberFormat(serial4No, 4), endGubun1Plus3No);

	return partNo;
    }

}
