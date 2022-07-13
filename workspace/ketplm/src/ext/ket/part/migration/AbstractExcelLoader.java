package ext.ket.part.migration;

import java.io.Serializable;

public class AbstractExcelLoader implements Serializable{
    
    protected boolean isSame(String strValue, String... strCompareTarget) {
	
	for( String str : strCompareTarget ){
	    if(str.equals(strValue)){
		return true;
	    }
	}
	
	return false;
    }

    protected String getCameStr(String str){
	
	if(str == null) return null;
	
	String tempStr = str;
	
	if(tempStr.length() <= 1){
	    return tempStr.toUpperCase();
	} else {
	    return tempStr.substring(0,1).toUpperCase() + tempStr.substring(1);
	}
    }
    
    protected String getNameDT(String str){
	
	if(str == null) return null;
	
	String tempStr = str;
	
	if(str.indexOf("[") == -1) return str;
	
	return tempStr.substring(0, str.indexOf("[")).trim();
    }
    
    protected String getNextStr(String str){
	
	if(str == null) return str;
	
	String tempStr = str;
	tempStr = str.replaceAll("\r\n", ", ");
	tempStr = str.replaceAll("\n", ", ");
	
	return tempStr;
    }
    
    protected String getStringType(String str){
	
	if(str == null) return "\"\"";
	return "\""+ str +"\"";
    }
    
    protected String getBooleanType(boolean flag){
	
	return flag?"true":"false";
    }
    
}
