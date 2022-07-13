package e3ps.edm.util;

import java.lang.reflect.Method;
import java.util.Locale;

import wt.fc.EnumeratedType;
import wt.util.WTException;
import wt.util.WTStringUtilities;
import e3ps.edm.CADAppType;
import e3ps.edm.CADCategory;
import e3ps.edm.CADManageType;
import e3ps.edm.DevStage;
import ext.ket.shared.log.Kogger;

public class EDMEnumeratedTypeUtil {

	
	public static String getCADCategory(String displayName, Locale locale)
	{
		
		CADCategory catArr[] = CADCategory.getCADCategorySet();
		for(int i = 0; i < catArr.length; i++) {
    		if(displayName.equals(catArr[i].getDisplay(locale))) {
    			return catArr[i].toString();
    		}
    	}
		
		return "";		
	}
	
	public static String getCADManageType(String displayName, Locale locale)
	{
		
		CADManageType cmts[] = CADManageType.getCADManageTypeSet();
		for(int i = 0; i < cmts.length; i++) {
    		if(displayName.equals(cmts[i].getDisplay(locale))) {
    			return cmts[i].toString();
    		}
    	}
		
		return "";		
	}
	
	public static String getDevStage(String displayName, Locale locale)
	{
		
		DevStage stages[] = DevStage.getDevStageSet();
		for(int i = 0; i < stages.length; i++) {
    		if(displayName.equals(stages[i].getDisplay(locale))) {
    			return stages[i].toString();
    		}
    	}
		
		return "";		
	}
	
	
	public static String getCADAppType(String displayName, Locale locale)
	{
		
		CADAppType cats[] = CADAppType.getCADAppTypeSet();
		for(int i = 0; i < cats.length; i++) {
    		if(displayName.equals(cats[i].getDisplay(locale))) {
    			return cats[i].toString();
    		}
    	}
		
		return "";		
	}
	
	public static EnumeratedType[] getEnumeratedTypeSet(String name) throws WTException
	{
		
		try {
			Class localClass = loadClass(name);
			if(localClass == null) {
				return null;
			}
			
			Method localMethod = localClass.getMethod("get" + WTStringUtilities.tail(name, '.') + "Set", null);
			
		    return (EnumeratedType[])(EnumeratedType[])localMethod.invoke(null, null);
		}
		catch (ExceptionInInitializerError e) {
			Kogger.error(EDMAttributes.class, e.getException());
		}
		catch (Exception e) {
			Kogger.error(EDMAttributes.class, e);
		}
		return null;
	}
	
	public static Class loadClass(String name) throws WTException
	{
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			
		}
		return null;
	}
}
