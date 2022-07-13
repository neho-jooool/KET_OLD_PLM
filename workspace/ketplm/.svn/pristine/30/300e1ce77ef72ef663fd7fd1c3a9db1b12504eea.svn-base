package ext.ket.part.code.internal;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.part.WTPartMaster;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.code.NumberCodeUtil;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.code.PartNoGenerable;
import ext.ket.part.code.SerialGenerator;
import ext.ket.part.entity.dto.PartBaseDTO;
import ext.ket.part.sap.ErpPartHandler;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.code.service.CodeHelper;

public abstract class AbsCodeGenerator implements PartNoGenerable {
    
    // Max 값을 가져와서 +1로 처리할 것인지?
    // Min - 최하위부터 빈 값들 찾아서 올 것인지?
    protected final static boolean USE_MAX = false;
    
    protected final static String SERIAL_2 = "00";
    protected final static String SERIAL_3 = "000";
    protected final static String SERIAL_4 = "0000";
    protected final static String SERIAL_5 = "00000";
    protected final static String SERIAL_6 = "000000";
    private ErpPartHandler erpPartHandler = new ErpPartHandler();
    private SerialGenerator serialGenerator = null;

    public abstract String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception;

    protected String getMaxSerial(String prefix, String suffix, int serialLenth, String escapeStr, String classPartType, boolean serialOnlyNumber) throws Exception {

	return NumberCodeUtil.getPartMaxSerial(prefix, suffix, serialLenth, escapeStr, -1, classPartType, serialOnlyNumber);
    }
    
    protected String getMinSerial(String prefix, String suffix, int serialLength, String escapeStr, String classPartType, boolean serialOnlyNumber, String minNo) throws Exception {
	serialGenerator = new SerialGenerator(prefix, suffix, serialLength, escapeStr, -1, classPartType, serialOnlyNumber, minNo);
	serialGenerator.searchSerial();
	return serialGenerator.getNextSerial();
    }
    
    /**
     * get DB Max Value
     * 
     * @param prefix
     * @param suffix
     * @param serialLenth
     * @param escapeStr
     * @return
     * @메소드명 : getMaxSerial
     * @작성자 : yjlee
     * @작성일 : 2014. 9. 1.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    protected String getMaxSerial(String prefix, String suffix, int serialLenth, String escapeStr, int rightWTpartNoLength, String classPartType
	    , boolean serialOnlyNumber) throws Exception {

	return NumberCodeUtil.getPartMaxSerial(prefix, suffix, serialLenth, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber);
    }
    
    protected String getMinSerial(String prefix, String suffix, int serialLength, String escapeStr, int rightWTpartNoLength, String classPartType
	    , boolean serialOnlyNumber, String minNo) throws Exception {
	
	serialGenerator = new SerialGenerator(prefix, suffix, serialLength, escapeStr, rightWTpartNoLength, classPartType, serialOnlyNumber, minNo);
	serialGenerator.searchSerial();
	return serialGenerator.getNextSerial();
    }

    /**
     * 123 => 0123
     * 
     * @param no
     * @param length
     * @return
     * @throws Exception
     * @메소드명 : getSerailNumberFormat
     * @작성자 : yjlee
     * @작성일 : 2014. 9. 1.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    protected String getSerailNumberFormat(String no, int length) throws Exception {

	return NumberCodeUtil.getSerailNumberFormat(no, length);
    }
    
    /**
     * 9999번과 같은 시리얼을 ERP에서는 사용하고 있으니 주의 바람 
     */
    protected boolean isMaxOrOver(String no, int length) throws Exception{
	
	if(no.length() > length){
	    return true;
	}else{
	    return false;
	}
    }

    // 컬러 자동차
    protected String getColorCodeAuto(String color) throws Exception {
	String colorCode2No = "";
	// - Neutral이면 뒤3개 안나오게 : 전자 , 자동차 동일
	if (StringUtils.isNotEmpty(color) && !"NATURAL".equals(color.toUpperCase())) {
	    colorCode2No = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpColor.getAttrCodeType() , color, Locale.KOREA );
	}

	return colorCode2No;
    }

    // 컬러 전자
    protected String getColorCodeElec(String color) throws Exception {
	String colorCode2No = "";
	// - Neutral이면 뒤3개 안나오게 : 전자 , 자동차 동일
	if (StringUtils.isNotEmpty(color) && !"NATURAL".equals(color.toUpperCase())) {
	    colorCode2No = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpColorElec.getAttrCodeType() , color, Locale.KOREA );
	}

	return colorCode2No;
    }

    // 도금 자동차
    protected String getPlatingCodeAuto(String plating) throws Exception {
	String platingCode = "";
	// Terminal 제품
	if (StringUtils.isNotEmpty(plating)) {
	    platingCode = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpPlating.getAttrCodeType() , plating, Locale.KOREA );
	}

	return platingCode;
    }

    // 도금 전자
    protected String getPlatingCodeElec(String plating) throws Exception {
	String platingCode = "";
	// Terminal 제품
	if (StringUtils.isNotEmpty(plating)) {
	    platingCode = CodeHelper.manager.getCodeDescription(PartSpecEnum.SpPlating.getAttrCodeType() , plating, Locale.KOREA );
	}

	return platingCode;
    }
    
    // 핀수
    protected String getNoOfPoleCode(String noOfPole) throws Exception {
	String noOfPoleCode = "";
	// Terminal 제품
	if (StringUtils.isEmpty(noOfPole)) {
	} else if (noOfPole.length() < 3) {
	    noOfPoleCode = getSerailNumberFormat(noOfPole, 2);
	} else if (noOfPole.length() >= 2) {
	}

	return noOfPoleCode;
    }
    
    protected String getNextAlphabet(String nowAlphabet) throws Exception {

	String retStr = null;

	if (nowAlphabet == null)
	    return null;

	int charInt = nowAlphabet.charAt(0);

	retStr = String.format("%c", charInt + 1);

	return retStr;
    }
    
    protected String getPreviousAlphabet(String nowAlphabet) throws Exception {

	String retStr = null;

	if (nowAlphabet == null)
	    return null;

	int charInt = nowAlphabet.charAt(0);

	retStr = String.format("%c", charInt - 1);

	return retStr;
    }
    
    protected String getFillPreviousPartNo(String prefix5No, String endFirst1No, String endSecond2No) throws Exception{
	
	String previousEndFirst1No = "A";
	String previousPartNo = prefix5No + previousEndFirst1No + endSecond2No;
	
	while (existPlmNumber(previousPartNo)) {
	    
	    previousEndFirst1No = this.getNextAlphabet(previousEndFirst1No);
	    previousPartNo = prefix5No + previousEndFirst1No + endSecond2No;
	    
	}
	
	return previousPartNo;
    }
    
    
    protected boolean existPlmNumber(String partNo) throws Exception {
	
	if(StringUtils.isEmpty(partNo)) return false;
	
	WTPartMaster mast = PartBaseHelper.service.getMaster(partNo);
	if(mast != null){
	    return true;
	}else{
	    return false;
	}
	
    }
    
    protected boolean existErpNumber(String partNo) throws Exception {
	
	boolean existErp = erpPartHandler.existErpPart(partNo);
	if(existErp){
	    return true;
	}else{
	    return false;
	}
	
    }
    
    protected String erpCheckNumber(String prefix, String serial, String suffix) throws Exception {
	
	boolean existErp = erpPartHandler.existErpPart(prefix + serial + suffix);
	if(existErp){
	    String partNo = erpCheckNumberContinue(prefix, serial, suffix, serial.length());
	    if("99".equals(serial)||"999".equals(serial)||"9999".equals(serial)||"99999".equals(serial)||"999999".equals(serial)||"9999999".equals(serial)||"99999999".equals(serial)){
		throw new Exception("부품 시리얼 번호 한도 초과입니다.");
	    }
	    return partNo;
	}else{
	    String partNo = prefix + serial + suffix;
	    if("99".equals(serial)||"999".equals(serial)||"9999".equals(serial)||"99999".equals(serial)||"999999".equals(serial)||"9999999".equals(serial)||"99999999".equals(serial)){
		throw new Exception("부품 시리얼 번호 한도 초과입니다.");
	    }
	    return partNo;
	}
	
    }
    
    protected String erpCheckNumberContinue(String prefix, String serial, String suffix, int serialLen) throws Exception {
	
	if(serialGenerator == null){
	
	    int serialInt = Integer.parseInt(serial) + 1;
	    String newSerial = String.valueOf(serialInt);
	    if (newSerial.length() > serialLen) {
		throw new Exception("일련번호 생성할 수 있는 최대 크기를 초과했습니다.");
	    }

	    newSerial = getSerailNumberFormat(newSerial, serialLen);
	    boolean existErp = erpPartHandler.existErpPart(prefix + newSerial + suffix);
	    if (existErp) {
		return erpCheckNumberContinue(prefix, newSerial, suffix, serialLen);
	    } else {
		return prefix + newSerial + suffix;
	    }
	    
	}else{
	    
	    String newSerial = serialGenerator.getNextSerial();
	    boolean existErp = erpPartHandler.existErpPart(prefix + newSerial + suffix);
	    if (existErp) {
		return erpCheckNumberContinue(prefix, newSerial, suffix, serialLen);
	    } else {
		return prefix + newSerial + suffix;
	    }
	}
    }
    
}
