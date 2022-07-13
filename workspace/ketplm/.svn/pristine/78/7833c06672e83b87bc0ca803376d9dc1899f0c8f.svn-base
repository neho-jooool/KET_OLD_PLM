package ext.ket.part.code.internal;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.StringUtil;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;

/**
 * S + 재질/구매품 구분 2자리 + 일련번호 4자리
 * 
 * @클래스명 : PartScrabNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 27.
 * @설명 : 
 * @수정이력 - 수정일,수정자,수정내용  					   
 *
 */
public class PartScrabNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto)throws Exception {
	String prefix3No = StringUtil.checkNull(dto.getNumberingCode()); // "S" 포함됨
	String minNo = dto.getNumberingMinNo();
	if(StringUtils.isNotEmpty(minNo) && !"-".equals(minNo)){
	    minNo = minNo.substring(prefix3No.length());
	}
	
	if(!prefix3No.startsWith("S"))
	    prefix3No = "S" + prefix3No;
	
	String gubun1No = "-";

	String serial4No = null;
	
	if(USE_MAX){
	    serial4No = getMaxSerial(prefix3No, "", 4, gubun1No, null, true);
	    if (StringUtils.isEmpty(serial4No))
		serial4No = SERIAL_4;
	    else
		serial4No = String.valueOf(Integer.parseInt(serial4No) + 1);
	}else{
	    serial4No = getMinSerial(prefix3No, "", 4, gubun1No, null, true, minNo);
	}
	
	if(isMaxOrOver(serial4No, 4))throw new Exception("## 채번 Serial 한도 초과되었습니다.");
	
	//String partNo = prefix3No + getSerailNumberFormat(serial4No, 4);
	String partNo = erpCheckNumber ( prefix3No, getSerailNumberFormat(serial4No, 4), "");

	return partNo;
    }

}
