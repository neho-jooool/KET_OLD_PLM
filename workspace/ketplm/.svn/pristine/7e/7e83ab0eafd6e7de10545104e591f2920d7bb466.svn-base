package ext.ket.part.spec.util;

public enum PartTypeEnum {

    /**
    // erp
    01.완제품(KET)
    02.반제품(KET)
    03.상품(KET)
    04.원자재(KET)
    05.포장재(KET)
    06.스크랩(KET)
    07.금형(KET)
    08.금형부품(KET)
     */
    FERT("제품", "F", "FERT"), 
    HALB("반제품", "H", "HALB"), 
    ROH("원자재", "R", "ROH"), 
    PACK("포장재", "K", "PACK"), 
    SCRP("스크랩", "S", "SCRP"), 
    DIENO("금형", "D", "CAST"), 
    MOLD("금형부품", "M", "DIEM"),
    HAWA("상품", "W", "HAWA"), 
    // TKLEE ERP I/F로 넘어온 부품중 사용 안하는 것[8가지 자재유형외의 것]은 모두 'O'로
    OTHER("기타", "O", ""); 
    
    private String desc;
    private String plmCode;
    private String erpCode;
    
    private PartTypeEnum(String desc, String plmCode, String erpCode){
	this.desc = desc;
	this.plmCode = plmCode;
	this.erpCode = erpCode;
    }
    	
    public static PartTypeEnum toPartTypeEnum(String plmCode){
	if(plmCode == null) return null;
	
	PartTypeEnum ret = null;
	
	PartTypeEnum[] arry = PartTypeEnum.values();
	for( PartTypeEnum item : arry){
	    if(plmCode.equalsIgnoreCase(item.getPlmCode())){
		ret = item;
		break;
	    }
	}
	
	return ret;
    }
    
    public static PartTypeEnum toPartTypeEnumByErpCode(String erpCode){
	if(erpCode == null) return null;
	
	PartTypeEnum ret = null;
	
	PartTypeEnum[] arry = PartTypeEnum.values();
	for( PartTypeEnum item : arry){
	    if(erpCode.equalsIgnoreCase(item.getErpCode())){
		ret = item;
		break;
	    }
	}
	
	return ret;
    }

    public String getDesc() {
        return desc;
    }

    public String getPlmCode() {
        return plmCode;
    }

    public String getErpCode() {
        return erpCode;
    }
    
}
