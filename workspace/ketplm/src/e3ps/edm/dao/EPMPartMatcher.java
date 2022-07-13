package e3ps.edm.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ext.ket.edm.cad2bom.service.internal.EPMNumberProdType;
import ext.ket.edm.cad2bom.service.internal.PartNumberProdType;

final public class EPMPartMatcher {
    
   private final static String[] prefixArry = new String[]{"", "AP_", "CU_"};
   private final static String[] suffixArry = new String[]{"", "_3D", "_2D", "_PRT", "_DWG", "_PLS", "_SCH", "_PCB"};
	
    public List<String> getPartMatchEpmNo(String[] partNumberArray) throws Exception {

	List<String> relatedEPM = new ArrayList<String>();

	for (String element : partNumberArray) {

	    if (StringUtils.isNotEmpty(element)) {

		element = element.trim();
		relatedEPM.addAll(getRelatedExpecetedNumber(element));
	    }
	}

	return relatedEPM;
    }

    private List<String> getRelatedExpecetedNumber(String element) throws Exception{
	
	List<String> relatedEPM = new ArrayList<String>();
	PartNumberProdType epmType = PartNumberProdType.getMatchPart(element);
	
	if (epmType == null){
	    
	    checkNos(relatedEPM, element);
	    
	}else{
	    
	    EPMNumberProdType epmType1 = epmType.getEpmNumberType();
	    String newCode1 = null;
	    String epmCode = epmType.toString().replace("_", "");
	    
	    if (epmType1 != null) {
		
		String partCode = epmType1.toString().replace("_", "");
		newCode1 = element.substring(epmCode.length());
		newCode1 = partCode + newCode1;
		
		checkNos(relatedEPM, newCode1);
	    }
	}

	return relatedEPM;
    }
    
    private void checkNos(List<String> relatedEPM, String newCode1){
	
	for( String prefix : prefixArry ){
	    String item = prefix + newCode1;
	    for( String suffix : prefixArry ){
		String itemWithSuffix = item + suffix;
		relatedEPM.add(itemWithSuffix);
	    }
	}
    }
    
    public static void main(String[] args) throws Exception{
	
	EPMPartMatcher matcher = new EPMPartMatcher();
	//matcher.getRelatedExpecetedNumber("H32053");
    }
    
}
