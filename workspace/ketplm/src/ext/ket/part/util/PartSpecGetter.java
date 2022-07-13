package ext.ket.part.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import wt.part.WTPart;
import wt.part.WTPartTypeInfo;
import wt.part.WTPartTypeInterface;
import e3ps.cost.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class PartSpecGetter {
    
    public static String getPartSpec(WTPart wtPart, PartSpecEnum partSpecEnum) {
	
	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface)wtPart;
	WTPartTypeInfo  wTPartTypeInfo= wtPartTypeInterface.getTypeInfoWTPart();
	
	String value = null;
        try {
	    value = BeanUtils.getProperty(wTPartTypeInfo, partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase());
        } catch (IllegalAccessException e) {
	    Kogger.error(PartSpecGetter.class, e);
        } catch (InvocationTargetException e) {
	    Kogger.error(PartSpecGetter.class, e);
        } catch (NoSuchMethodException e) {
	    Kogger.error(PartSpecGetter.class, e);
        } 
        
	Kogger.debug(PartSpecGetter.class, "#" + partSpecEnum.getAttrCode() + ":" +  partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase() + ":" 
	+ partSpecEnum.getAttrName() + ":" + StringUtil.checkNull(value) );
	
	return StringUtil.checkNull(value);
    }
    
    public static String getPartSpecWithType(WTPartTypeInfo wtPartTypeInfo, PartSpecEnum partSpecEnum) {
	
	String value = null;
        try {
	    value = BeanUtils.getProperty(wtPartTypeInfo, partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase());
        } catch (IllegalAccessException e) {
	    Kogger.error(PartSpecGetter.class, e);
        } catch (InvocationTargetException e) {
	    Kogger.error(PartSpecGetter.class, e);
        } catch (NoSuchMethodException e) {
	    Kogger.error(PartSpecGetter.class, e);
        }
        
	Kogger.debug(PartSpecGetter.class, "#" + partSpecEnum.getAttrCode() + ":" +  partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase() + ":" 
	+ partSpecEnum.getAttrName() + ":" + StringUtil.checkNull(value) );
	
	return StringUtil.checkNull(value);
    }
   
}
