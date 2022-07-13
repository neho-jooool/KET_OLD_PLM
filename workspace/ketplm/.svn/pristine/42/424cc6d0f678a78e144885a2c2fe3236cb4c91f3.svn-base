package ext.ket.part.code.internal;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;

import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.shared.log.Kogger;

/**
 * 기타 유형 처리 - CP
 * 
 * @클래스명 : PartOtherNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 12. 2.
 * @설명 : 
 * @수정이력 - 수정일,수정자,수정내용  					   
 *
 */
public class PartOtherNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto)throws Exception {
	
	String prefix2No = dto.getNumberingCode(); // CP 
	
	String gubun1No = "-";
	
	String serial4No = null;
	String checkFlag = dto.getClassPartType();
	
	String yy = "";
	try {
	    
	    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy");
	    yy = sdFormat.format(Calendar.getInstance().getTime());
	    yy = yy.substring(2);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), "Exception : " + e.getMessage());
	}
	
//	if (USE_MAX) {
	    serial4No = getMaxSerial(prefix2No + yy, "", 4, gubun1No, checkFlag, true);
	    if (StringUtils.isEmpty(serial4No))
		serial4No = SERIAL_4;
	    else
		serial4No = String.valueOf(Integer.parseInt(serial4No) + 1);
//	} else {
//	    String minNo = dto.getNumberingMinNo();
//	    if (StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)) {
//		minNo = minNo.substring((prefix2No + yy).length());
//	    }else{
//		minNo = "0000";
//	    }
//	    serial4No = getMinSerial(prefix2No + yy, "", 4, gubun1No, checkFlag, true, minNo);
//	}

	if (isMaxOrOver(serial4No, 4))
	    throw new Exception(" 채번 Serial 한도 초과되었습니다.");

	String partNo = erpCheckNumber(prefix2No + yy, getSerailNumberFormat(serial4No, 4), "");

	return partNo;
    }
    
}
