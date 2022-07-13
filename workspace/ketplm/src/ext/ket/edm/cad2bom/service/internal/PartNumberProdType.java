package ext.ket.edm.cad2bom.service.internal;

import java.util.ArrayList;
import java.util.List;

import ext.ket.shared.log.Kogger;

public enum PartNumberProdType {
    
    _31(EPMNumberProdType.WH31), // 생성자 2개 짜리
    _68(EPMNumberProdType.MG68), // 2자 짜리   
    
    CK(EPMNumberProdType.CK), // NEW
    CP(EPMNumberProdType.CP), // NEW
    
    H31(EPMNumberProdType.WH31),
    H32(EPMNumberProdType.WH32),
    H33(EPMNumberProdType.WH33),
    
    H41(EPMNumberProdType.EM41),
    H42(EPMNumberProdType.EM42),
    H43(EPMNumberProdType.EM43),

    H45(EPMNumberProdType.EM45),
    H46(EPMNumberProdType.EM46),
    H47(EPMNumberProdType.EM47),
    H48(EPMNumberProdType.EM48),
    H49(EPMNumberProdType.EM49),
    
    H51(EPMNumberProdType.JB51),
    H52(EPMNumberProdType.JB52),
    H53(EPMNumberProdType.JB53),
    H54(EPMNumberProdType.JB54),
    H55(EPMNumberProdType.JB55),
    H56(EPMNumberProdType.JB56),
    H57(EPMNumberProdType.JB57),
    H58(EPMNumberProdType.JB58),
    H59(EPMNumberProdType.JB59),
    H60(EPMNumberProdType.MG60),
    H61(EPMNumberProdType.MG61),
    H62(EPMNumberProdType.MG62),
    H63(EPMNumberProdType.MG63),
    H64(EPMNumberProdType.MG64),
    H65(EPMNumberProdType.MG65),
    H66(EPMNumberProdType.MG66),
    H67(EPMNumberProdType.MG67),    
    
    H71(EPMNumberProdType.ST71),
    H72(EPMNumberProdType.ST72),
    H73(EPMNumberProdType.ST73),
    H74(EPMNumberProdType.ST74),
    H75(EPMNumberProdType.ST75),
    H76(EPMNumberProdType.ST76),
    H77(EPMNumberProdType.ST77),
    H78(EPMNumberProdType.ST78),
    H79(EPMNumberProdType.ST79),
    
    H88(EPMNumberProdType.PG88),
    H89(EPMNumberProdType.PG89),    
    
    HK5(EPMNumberProdType.K5), // NEW
    HKR(EPMNumberProdType.KR), // NEW
    
    K3(EPMNumberProdType.K3), // NEW
    K2(EPMNumberProdType.K2), // NEW
    K1(EPMNumberProdType.K1), // NEW
    
    HKA(EPMNumberProdType.KA), // NEW
    HKD(EPMNumberProdType.KD), // NEW
    HKH(EPMNumberProdType.KH),
    HKI(EPMNumberProdType.KI), // NEW
    HKP(EPMNumberProdType.KP),
    HKS(EPMNumberProdType.KS),
    HKW(EPMNumberProdType.KW),
    HK(EPMNumberProdType.K), // 2자짜리
    
    KD4(EPMNumberProdType.KD4),
    KR(EPMNumberProdType.KR), // 2자짜리
    K(EPMNumberProdType.K), // 1자 짜리
    
    R11(EPMNumberProdType.R11), // New
    R19(EPMNumberProdType.R19), // New
    R20(EPMNumberProdType.R20), // New
    
    R40(EPMNumberProdType.R40),
    R41(EPMNumberProdType.R41),
    R42(EPMNumberProdType.R42),    
    R44(EPMNumberProdType.EM44),    
    
    R50(EPMNumberProdType.R50),    
    R60(EPMNumberProdType.R60),   
    
    R68(EPMNumberProdType.MG68),    
    
    RK3(EPMNumberProdType.RK3), // New
    
    ;
    
    private EPMNumberProdType epmNumberType;
    private EPMNumberProdType epmNumberType2;
    
    private PartNumberProdType(EPMNumberProdType epmNumberType){
	this.epmNumberType = epmNumberType;
    }
    
    private PartNumberProdType(EPMNumberProdType epmNumberType, EPMNumberProdType epmNumberType2){
	this.epmNumberType = epmNumberType;
	this.epmNumberType2 = epmNumberType2;
    }
    
    public EPMNumberProdType getEpmNumberType() {
        return epmNumberType;
    }
    
    public EPMNumberProdType getEpmNumberType2() {
        return epmNumberType2;
    }
    
    public static PartNumberProdType getMatchPart(String partNo) throws Exception{
	if(partNo == null) return null;
	    
	int length = 4;
	String tempPartNo = null;
	for( int k=length; k>=1; k--){
	    if (partNo.length() >= k) {
		tempPartNo = partNo.substring(0, k);
		List<PartNumberProdType> list = getCharEnum(k);
		for (PartNumberProdType partNumberType : list) {
		    if (partNumberType.toString().replace("_", "").equals(tempPartNo)) {
			return partNumberType;
		    }
		}

	    }
	}
	
	return null;
    }
    
    private static List<PartNumberProdType> list1 =  null;
    private static List<PartNumberProdType> list2 =  null;
    private static List<PartNumberProdType> list3 =  null;
    private static List<PartNumberProdType> list4 =  null;
    static{
	try {
	    
	    list1 = new ArrayList<PartNumberProdType>();
	    list2 = new ArrayList<PartNumberProdType>();
	    list3 = new ArrayList<PartNumberProdType>();
	    list4 = new ArrayList<PartNumberProdType>();
		
	    PartNumberProdType[] array = PartNumberProdType.values();
	    for (PartNumberProdType partNumberType : array) {
		String code = partNumberType.toString();
		code = code.replace("_", "");
		if (code.length() == 1) {
		    list1.add(partNumberType);
		} else if (code.length() == 2) {
		    list2.add(partNumberType);
		} else if (code.length() == 3) {
		    list3.add(partNumberType);
		} else {
		    list4.add(partNumberType);
		}
	    }
		
        } catch (Exception e) {
	    Kogger.error(EPMNumberProdType.class, e);
        }
    }
    
    private static List<PartNumberProdType> getCharEnum(int length) throws Exception {
	if (length == 1) {
	    return list1;
	} else if (length == 2) {
	    return list2;
	} else if (length == 3) {
	    return list3;
	} else {
	    return list4;
	}
    }
    
}
