package ext.ket.part.code.internal;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;

/**
 * P + 일련번호 6자리
 * 
 * @클래스명 : PartPackageNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 27.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartPackageNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {

	String prefix2No = dto.getNumberingCode(); // "P" 포함
	String minNo = dto.getNumberingMinNo();
	if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)){
	    minNo = minNo.substring(prefix2No.length());
	}
	
//	if (prefix2No == null || prefix2No.length() != 2)
//	    throw new Exception("포장재는 2자리 채번코드여야 합니다.");

	// 전자 포장재 체크 로직
//	if ("P3".equals(prefix1No)) {
//	    return generatePartNoElec(dto, baseDto);
//	}

	String gubun1No = "-";

	String serial5No = null;
	 if(USE_MAX){
	     serial5No = getMaxSerial(prefix2No, "", 4, gubun1No, 7, null, true);
	    if (StringUtils.isEmpty(serial5No))
		serial5No = SERIAL_4;
	    else
		serial5No = String.valueOf(Integer.parseInt(serial5No) + 1);
	 }else{
	     serial5No = getMinSerial(prefix2No, "", 4, gubun1No, 7, null, true, minNo);
	 }
	 
	if (isMaxOrOver(serial5No, 4))
	    throw new Exception("## 채번 Serial 한도 초과되었습니다.");

	//String partNo = prefix2No + getSerailNumberFormat(serial5No, 5);
	String partNo = erpCheckNumber ( prefix2No, getSerailNumberFormat(serial5No, 4), "");
	
	return partNo;
    }

//    // 전자 포장재 체크 로직
//    public String generatePartNoElec(PartGenDTO dto, PartBaseDTO baseDto)throws Exception {
//	
//	String prefix2No = dto.getNumberingCode(); //"P" 포함
//	
//	if(prefix2No== null || !prefix2No.startsWith("P"))
//	    prefix2No = "P" + StringUtil.checkNull(prefix2No);
//	
//	String gubun1No = "-";
//
//	String serial5No = getMaxSerial(prefix2No, "", 5, gubun1No);
//	if (StringUtils.isEmpty(serial5No))
//	    serial5No = SERIAL_5;
//	else
//	    serial5No = String.valueOf(Integer.parseInt(serial5No) + 1);
//	
//	if(isMaxOrOver(serial5No, 5))throw new Exception("## 채번 Serial 한도 초과되었습니다.");
//	
//	String partNo = prefix2No + getSerailNumberFormat(serial5No, 5);
//
//	return partNo;
//    }
}
