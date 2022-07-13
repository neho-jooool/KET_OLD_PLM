package ext.ket.edm.cad2bom.service.internal;

import java.util.ArrayList;
import java.util.List;

import ext.ket.shared.log.Kogger;

public enum EPMNumberProdType {
    
    CK(PartNumberProdType.CK), // NEW
    CP(PartNumberProdType.CP), // NEW
    
    EM41(PartNumberProdType.H41),
    EM42(PartNumberProdType.H42),
    EM43(PartNumberProdType.H43),
    EM44(PartNumberProdType.R44),
    EM45(PartNumberProdType.H45),
    EM46(PartNumberProdType.H46),
    EM47(PartNumberProdType.H47),
    EM48(PartNumberProdType.H48),
    EM49(PartNumberProdType.H49),
    
    JB50(PartNumberProdType.R50),
    JB51(PartNumberProdType.H51),
    JB52(PartNumberProdType.H52),
    JB53(PartNumberProdType.H53),
    JB54(PartNumberProdType.H54),
    JB55(PartNumberProdType.H55),
    JB56(PartNumberProdType.H56),
    JB57(PartNumberProdType.H57),
    JB58(PartNumberProdType.H58),
    JB59(PartNumberProdType.H59),
    
    K5(PartNumberProdType.HK5), // NEW
    KR(PartNumberProdType.HKR), // KR 에서 변경됨
    
    K3(PartNumberProdType.K3), // NEW
    K2(PartNumberProdType.K2), // NEW
    K1(PartNumberProdType.K1), // NEW
    
    KA(PartNumberProdType.HKA), // NEW
    KD4(PartNumberProdType.KD4),
    KD(PartNumberProdType.HKD), // NEW
    KH(PartNumberProdType.HKH),
    KI(PartNumberProdType.HKI), // NEW
    KP(PartNumberProdType.HKP),
    KS(PartNumberProdType.HKS),
    KW(PartNumberProdType.HKW),
    K(PartNumberProdType.HK, PartNumberProdType.K), // 1개 짜리
    
    MG60(PartNumberProdType.H60),
    MG61(PartNumberProdType.H61),
    MG62(PartNumberProdType.H62),
    MG63(PartNumberProdType.H63),
    MG64(PartNumberProdType.H64),
    MG65(PartNumberProdType.H65),
    MG66(PartNumberProdType.H66),
    MG67(PartNumberProdType.H67),
    MG68(PartNumberProdType.R68, PartNumberProdType._68), // MG68은 68로 결정, PartNumberProdType.R68
    
    PG88(PartNumberProdType.H88),
    PG89(PartNumberProdType.H89),
    
    R11(PartNumberProdType.R11), // New
    R19(PartNumberProdType.R19), // New
    R20(PartNumberProdType.R20), // New
    
    R40(PartNumberProdType.R40),
    R41(PartNumberProdType.R41),
    R42(PartNumberProdType.R42),
    R44(PartNumberProdType.R44),
    
    R50(PartNumberProdType.R50),
    R60(PartNumberProdType.R60),
    
    RK3(PartNumberProdType.RK3), // New
    
    ST71(PartNumberProdType.H71),
    ST72(PartNumberProdType.H72),
    ST73(PartNumberProdType.H73),
    ST74(PartNumberProdType.H74),
    ST75(PartNumberProdType.H75),
    ST76(PartNumberProdType.H76),
    ST77(PartNumberProdType.H77),
    ST78(PartNumberProdType.H78),
    ST79(PartNumberProdType.H79),
    
    WH31(PartNumberProdType.H31), // Wire Harness라서 상품은 무관할 듯, PartNumberProdType._31,
    WH32(PartNumberProdType.H32),
    WH33(PartNumberProdType.H33),
    WH(PartNumberProdType._31)
    ;
    
    private PartNumberProdType partNumberType;
    private PartNumberProdType partNumberType2;
    
    private EPMNumberProdType(PartNumberProdType partNumberType){
	this.partNumberType = partNumberType;
    }
    
    private EPMNumberProdType(PartNumberProdType partNumberType, PartNumberProdType partNumberType2){
	this.partNumberType = partNumberType;
	this.partNumberType2 = partNumberType2;
    }
    
    public PartNumberProdType getPartNumberType() {
        return partNumberType;
    }

    public PartNumberProdType getPartNumberType2() {
        return partNumberType2;
    }
    
    public static EPMNumberProdType getMatchEpm(String epmNo) throws Exception{
	if(epmNo == null) return null;
	    
	int length = 4;
	String tempEpmNo = null;
	for( int k=length; k>=1; k--){
	    if (epmNo.length() >= k) {
		tempEpmNo = epmNo.substring(0, k);
		Kogger.debug(EPMNumberProdType.class, " tempEpmNo # " + tempEpmNo);
		List<EPMNumberProdType> list = getCharEnum(k);
		for (EPMNumberProdType epmNumberType : list) {
		    if (epmNumberType.toString().replace("_", "").equals(tempEpmNo)) {
		    	Kogger.debug(EPMNumberProdType.class, "epmNumberType equal! # " + tempEpmNo);
			return epmNumberType;
		    }
		}

	    }
	}
	
	return null;
    }
    
    private static List<EPMNumberProdType> list1 =  null;
    private static List<EPMNumberProdType> list2 =  null;
    private static List<EPMNumberProdType> list3 =  null;
    private static List<EPMNumberProdType> list4 =  null;
    static{
	try {
	    
	    list1 = new ArrayList<EPMNumberProdType>();
	    list2 = new ArrayList<EPMNumberProdType>();
	    list3 = new ArrayList<EPMNumberProdType>();
	    list4 = new ArrayList<EPMNumberProdType>();
		
	    EPMNumberProdType[] array = EPMNumberProdType.values();
	    for (EPMNumberProdType epmNumberType : array) {
		String code = epmNumberType.toString();
		code = code.replace("_", "");
		if (code.length() == 1) {
		    list1.add(epmNumberType);
		} else if (code.length() == 2) {
		    list2.add(epmNumberType);
		} else if (code.length() == 3) {
		    list3.add(epmNumberType);
		} else {
		    list4.add(epmNumberType);
		}
	    }
		
        } catch (Exception e) {
	    Kogger.error(EPMNumberProdType.class, e);
        }
    }
    
    private static List<EPMNumberProdType> getCharEnum(int length) throws Exception {
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
