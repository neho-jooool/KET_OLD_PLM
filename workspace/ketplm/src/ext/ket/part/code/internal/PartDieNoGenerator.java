package ext.ket.part.code.internal;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;

/**
 * 프레스/사출구분 1자리 + 제작지 구분 1자리 + 일련번호 3자리 + 추가금형 1자리 + Modify 금형구분 1자리
 * 
 * @클래스명 : PartDieNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartDieNoGenerator extends AbsCodeGenerator {

    // 2(채번코드, 1자리 넘어올 경우에는 NumberCode 추가 ) + 3(일련번호) + 1(추가금형) + 2(Modify금형)
    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {

	String prefix2No = dto.getNumberingCode();

	if (prefix2No == null)
	    throw new Exception("##  분류체계 채번 No 값이 없습니다.");

	String gubun1No = "-";

	// TKLEE Die No 로직 필요
	// SPECMPRODFLAG 1 SET
	// SPECMPRODFLAG 2 추가
	// SPECMPRODFLAG 3 Modify
	String suffixFlag = baseDto.getSpMProdAt();
	String partNo = "";
	if ("1".equals(suffixFlag)) { // SET

	    if (prefix2No.length() == 1) {

		// 추가 금형
		String endFirst1No = "0";
		// Modify 금형 구분
		String endSecond2No = "00";

		String prefix1No = prefix2No;

		String serial4No = null;
		
		if(USE_MAX){
		    serial4No = getMaxSerial(prefix1No, "", 4, gubun1No, 8, null, true);
		    if (StringUtils.isEmpty(serial4No))
			serial4No = SERIAL_4;
		    else
			serial4No = String.valueOf(Integer.parseInt(serial4No) + 1);
		}else{
		    String minNo = dto.getNumberingMinNo();
		    if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
			minNo = minNo.substring(prefix2No.length());
			minNo = minNo.substring(0, 4);
		    }
		    serial4No = getMinSerial(prefix1No, "", 4, gubun1No, 8, null, true, minNo);
		}

		if (isMaxOrOver(serial4No, 4))
		    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

		// partNo = prefix1No + getSerailNumberFormat(serial4No, 4) + endFirst1No + endSecond2No;
		partNo = erpCheckNumber(prefix1No, getSerailNumberFormat(serial4No, 4), endFirst1No + endSecond2No);

	    } else if (prefix2No.length() == 2) {

		// 추가 금형
		String endFirst1No = "0";
		// Modify 금형 구분
		String endSecond2No = "00";

		String serial3No = null;
		
		if(USE_MAX){
		    serial3No = getMaxSerial(prefix2No /* prefix1No + prefixSecond1No */, "", 3, gubun1No, 8, null, true);
		    if (StringUtils.isEmpty(serial3No))
			    serial3No = SERIAL_3;
		    else
			serial3No = String.valueOf(Integer.parseInt(serial3No) + 1);
		}else{
		    String minNo = dto.getNumberingMinNo();
		    if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
			minNo = minNo.substring(prefix2No.length());
			minNo = minNo.substring(0, 3);
		    }
		    serial3No = getMinSerial(prefix2No /* prefix1No + prefixSecond1No */, "", 3, gubun1No, 8, null, true, minNo);
		}

		if (isMaxOrOver(serial3No, 3))
		    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

		// partNo = prefix2No /*prefix1No + prefixSecond1No*/ + getSerailNumberFormat(serial3No, 3) + endFirst1No + endSecond2No;
		partNo = erpCheckNumber(prefix2No /* prefix1No + prefixSecond1No */, getSerailNumberFormat(serial3No, 3), endFirst1No + endSecond2No);
	    }

	} else if ("2".equals(suffixFlag)) { // 추가일경우 6번째 alphabet을 올려주고 00을 붙여줌

	    if (StringUtils.isEmpty(baseDto.getSpRepresentM()))
		throw new Exception("##  대표금형(Die No) 값이  없습니다.");

	    String repMoldNo = baseDto.getSpRepresentM();
	    String prefix5No = repMoldNo.substring(0, 5);
	    String endSecond2No = "00";

	    String sixthMaxNo = null;
	    //if(USE_MAX){
	    	sixthMaxNo = getMaxSerial(prefix5No, endSecond2No, 1, gubun1No, 8, null, false);
	    //}else{
//	    	sixthMaxNo = getMinSerial(prefix5No, endSecond2No, 1, gubun1No, 8, null, false);
	    //}

	    // 추가 금형
	    String endFirst1No = null;
	    boolean isInt = false;
	    try {
		int k = Integer.parseInt(sixthMaxNo);
		isInt = true;
	    } catch (Exception e) {
	    }

	    if (isInt) {
		// if("9".equals(sixthMaxNo)){
		endFirst1No = "A";
		// }else{
		// endFirst1No = (String.valueOf(Integer.parseInt(sixthMaxNo) + 1));
		// }
	    } else {
		if ("Z".equalsIgnoreCase(sixthMaxNo))
		    throw new Exception("## 채번할 코드가 고갈되었습니다.");

		endFirst1No = getNextAlphabet(sixthMaxNo);
	    }
	    
	    partNo = getFillPreviousPartNo(prefix5No,endFirst1No,endSecond2No);//앞번호부터 채우기..
	    
	    //partNo = prefix5No + endFirst1No + endSecond2No;
	    while (existErpNumber(partNo)) {

		boolean isIntOk = false;
		try {
		    int m = Integer.parseInt(endFirst1No);
		    isIntOk = true;
		} catch (Exception e) {
		}

		if (isIntOk) {
		    // if ("9".equals(endFirst1No)) {
		    endFirst1No = "A";
		    // } else {
		    // endFirst1No = (String.valueOf(Integer.parseInt(endFirst1No) + 1));
		    // }
		} else {
		    if ("Z".equalsIgnoreCase(endFirst1No))
			throw new Exception("## 채번할 코드가 고갈되었습니다.");

		    endFirst1No = getNextAlphabet(endFirst1No);
		}

		partNo = prefix5No + endFirst1No + endSecond2No;
	    }

	} else if ("3".equals(suffixFlag)) { // Modify일 경우 뒤 2자리의 차수를 오려줌

	    if (StringUtils.isEmpty(baseDto.getSpRepresentM()))
		throw new Exception("##  대표금형(Die No) 값이  없습니다.");

	    String repMoldNo = baseDto.getSpRepresentM();
	    String prefix6No = repMoldNo.substring(0, 6);

	    String serial2No = null;
	    
//	    if(USE_MAX){
		serial2No = getMaxSerial(prefix6No, "", 2, gubun1No, 8, null, true);
		if (StringUtils.isEmpty(serial2No)) {
		    serial2No = SERIAL_2;
		} else {
		    serial2No = String.valueOf(Integer.parseInt(serial2No) + 1);
		}
//	    }else{
//		serial2No = getMinSerial(prefix6No, "", 2, gubun1No, 8, null, true);
//	    }

	    if (isMaxOrOver(serial2No, 2))
		throw new Exception("## 채번할 코드가 고갈되었습니다.");

	    String endSecond2No = StringUtils.isEmpty(serial2No) ? SERIAL_2 : getSerailNumberFormat(serial2No, 2);

	    // partNo = prefix6No + endSecond2No;
	    partNo = erpCheckNumber(prefix6No, endSecond2No, "");

	} else {

	    throw new Exception("생산구분 - SpMProdAt이 정의되지 않았습니다.");
	}

	return partNo;
    }

}
