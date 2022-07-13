package ext.ket.edm.service.internal;

import wt.epm.EPMDocument;
import ext.ket.shared.log.Kogger;

public class EpmEcoValidator {
    // ECO Validation : 개정 버튼 누르면, 해당 도면이 체크인되어있지 않으면 Validation Message 전달
    public String validCheckoutInfoEpm(EPMDocument epmDoc){
	String retStr = "";
	String epmState = null;
	try {

	    if (epmDoc == null) {
		return retStr;
	    }else{
		epmState = epmDoc.getCheckoutInfo().getState().toString();
	    }
	    if(!"c/i".equals(epmState)){
		retStr = epmDoc.getNumber() + "\n 체크아웃 되어있는 도면은 개정 할 수 없습니다.";
	    }
	    
	} catch (Exception e){
	    Kogger.error(getClass(), e);
	}
	
	return retStr;
    }
}