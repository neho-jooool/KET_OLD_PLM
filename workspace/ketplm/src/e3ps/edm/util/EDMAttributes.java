package e3ps.edm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import wt.fc.EnumeratedType;
import wt.iba.definition.litedefinition.AbstractAttributeDefinizerNodeView;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.definition.litedefinition.AttributeDefNodeView;
import wt.iba.definition.litedefinition.AttributeOrgNodeView;
import wt.iba.definition.service.IBADefinitionHelper;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.litevalue.ReferenceValueDefaultView;
import wt.iba.value.service.IBAValueHelper;
import wt.util.WTContext;
import wt.util.WTException;
import e3ps.edm.CADAppType;
import e3ps.edm.CADCategory;
import e3ps.edm.CADManageType;
import e3ps.edm.DevStage;
import e3ps.edm.PartReferenceType;
import e3ps.edm.beans.EDMHelper;
import ext.ket.shared.log.Kogger;

public class EDMAttributes {
	
	private static EDMAttributes INSTANCE = null;
	private static HashMap ATTRIBUTES = null;
	
	private static Locale DEFAULT_LOCALE = Locale.KOREAN;
	
	private static String ORG_NAME = "EPMAttributes";
	
	private EDMAttributes()
    {
        this.initialize();
    }
	
    private void initialize()
    {
        
    	if (EDMAttributes.ATTRIBUTES == null)
        {
    		EDMAttributes.ATTRIBUTES = new HashMap();
            try {
            	AttributeOrgNodeView orgNodeViewArr[] = null;
            	
            	if( (EDMAttributes.ORG_NAME == null) || (EDMAttributes.ORG_NAME.trim().length() == 0) ) {
            		orgNodeViewArr = IBADefinitionHelper.service.getAttributeOrganizerRoots();
            	} else {
            		orgNodeViewArr = IBADefinitionHelper.service.getAttributeOrganizerRoots(ORG_NAME);
            	}
            	
            	for (int i = orgNodeViewArr.length - 1; i > -1; i--) {
            		AttributeOrgNodeView orgNodeView = orgNodeViewArr[i];
                    String nodeName = orgNodeView.getName();
                    
                    AbstractAttributeDefinizerNodeView definizerNodeViewArr[] = IBADefinitionHelper.service.getAttributeChildren(orgNodeView);
                    // softtype attributes
                    for (int j = 0; j < definizerNodeViewArr.length; j++)
                    {
                    	AttributeDefDefaultView defDefaultView = null;
                    	AbstractValueView valueView = null;
                    	if (definizerNodeViewArr[j] instanceof AttributeDefNodeView) {
                    		defDefaultView = IBADefinitionHelper.service.getAttributeDefDefaultView((AttributeDefNodeView) definizerNodeViewArr[j]);
                    		
                    		EDMAttributes.ATTRIBUTES.put(defDefaultView.getName(), defDefaultView);
                    	}
                    }                   
                }                
            }
            catch (Exception e)
            {
                Kogger.error(getClass(), this.getClass().getName() + ":initialize() EPMAttributes - Can't initialize : "
                        + e.getMessage());
            }
        }
        
    }
    
    public synchronized static EDMAttributes getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new EDMAttributes();
        return INSTANCE;
    }
    
    public HashMap getEPMAttributes() {
    	return ATTRIBUTES;
    }
    
    public AttributeDefDefaultView getEPMAttribute(String name) {
    	return (AttributeDefDefaultView)ATTRIBUTES.get(name);
    }
    
    public Object convertValue(String defName, Object value, Locale locale) {
    	EnumeratedType et = null;
    	
    	if(value == null) {
    		return null;
    	}
    	
    	if( !(value instanceof String) ) {
    		return value;
    	}
    	
    	String str = (String)value;
    	
    	if(defName.equals(EDMHelper.IBA_DEV_STAGE)) {//if("DevStage".equals(defName)) {
    		et = DevStage.toDevStage(str);
    	} else if(defName.equals(EDMHelper.IBA_CAD_CATEGORY)) {//else if("CADCategory".equals(defName)) {
    		et = CADCategory.toCADCategory(str);
    	} else if(defName.equals(EDMHelper.IBA_CAD_APP_TYPE)) {//else if("CADAppType".equals(defName)) {
    		et = CADAppType.toCADAppType(str);
    	} else if(defName.equals(EDMHelper.IBA_CAD_MANAGE_TYPE)) {//else if("CADManageType".equals(defName)) {
    		et = CADManageType.toCADManageType(str);
    	} else if(defName.equals(EDMHelper.IBA_PART_REF_TYPE)) {//else if("PartReferenceType".equals(defName)) {
    		et = PartReferenceType.toPartReferenceType(str);
    	} else {
    		return (str.trim().length() == 0)? null:str;
    	}
    	
    	if(locale == null) {
			locale = DEFAULT_LOCALE;
		}
		return et.getDisplay(locale);
    }
    
    public static ArrayList getAttributes() {
    	return getAttributes(null);
    }
    
    
    public static ArrayList getAttributes(String orgName) {
    	ArrayList list = new ArrayList();
    	try {
    		AttributeOrgNodeView orgNodeViewArr[] = null;
        	
        	if( (orgName == null) || (orgName.trim().length() == 0) ) {
        		orgNodeViewArr = IBADefinitionHelper.service.getAttributeOrganizerRoots();
        	} else {
        		orgNodeViewArr = IBADefinitionHelper.service.getAttributeOrganizerRoots(orgName);
        	}
        	
	    	for (int i = orgNodeViewArr.length - 1; i > -1; i--) {
	    		AttributeOrgNodeView orgNodeView = orgNodeViewArr[i];
	            
	            AbstractAttributeDefinizerNodeView definizerNodeViewArr[] = IBADefinitionHelper.service.getAttributeChildren(orgNodeView);
	            for (int j = 0; j < definizerNodeViewArr.length; j++)
	            {
	            	AttributeDefDefaultView defDefaultView = null;
	            	if (definizerNodeViewArr[j] instanceof AttributeDefNodeView) {
	            		defDefaultView = IBADefinitionHelper.service.getAttributeDefDefaultView((AttributeDefNodeView) definizerNodeViewArr[j]);
	            		list.add(defDefaultView);
	            	}
	            }                   
	        }       
    	}
    	catch(Exception e) {
    		Kogger.error(EDMAttributes.class, e);
    	}
    	return list;
    }
    
    public static HashMap getAttributeValues(IBAHolder ibaHolder, Locale locale) {
    	HashMap values = new HashMap();
    	
    	//wt.iba.value.IBAValueUtility utility = new wt.iba.value.IBAValueUtility(ibaHolder, locale);
    	//utility.getAttributeValues();
    	
    	if (locale == null) { locale = WTContext.getContext().getLocale(); }
    	
    	DefaultAttributeContainer container = null;
    	try {
    		ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, locale, null);    	
    		container = (DefaultAttributeContainer)ibaHolder.getAttributeContainer(); // container > values> CADAppType:AutoCAD,IsDummyFile:N,DevStage:개발단계,CADCategory:제품도면,CADManageType:제품도면,Security:S1
    	}
    	catch(Exception e) {
    		Kogger.error(EDMAttributes.class, e);
    	}
    	
    	if(container == null) {
    		return null;
    	}
    	else {
    		AbstractValueView[] avv = container.getAttributeValues();
    		for (int i = 0; i < avv.length; i++) {
    			values.put(avv[i].getDefinition().getName(), avv[i]);
    		}
    	}
    	
    	return values;
    }
    
    public static String getAttributeValue(IBAHolder ibaHolder, String name, Locale locale)
    	throws WTException {
    	
    	HashMap values = getAttributeValues(ibaHolder, locale);    	
    	if(values.containsKey(name)) {
    		AbstractValueView avv = (AbstractValueView)values.get(name);
    		return getLocalizedIBAValueDisplayString(avv,locale);
    	}
    	return null;
    }
    
    
    public static DefaultAttributeContainer getAttributeContainer(IBAHolder ibaHolder, Locale locale) {
    	
    	if (locale == null) { locale = WTContext.getContext().getLocale(); }
    	
    	DefaultAttributeContainer container = null;
    	try {
    		ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, locale, null);    	
    		container = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
    	}
    	catch(Exception e) {
    		Kogger.error(EDMAttributes.class, e);
    	}
    	
    	return container;
    }
    
    
    public static HashMap getIBAValueViews(IBAHolder ibaHolder, Locale locale) {
    	HashMap values = new HashMap();
    	
    	//wt.iba.value.IBAValueUtility utility = new wt.iba.value.IBAValueUtility(ibaHolder, locale);
    	//utility.getAttributeValues();
    	
    	if (locale == null) locale = WTContext.getContext().getLocale();
    	
    	DefaultAttributeContainer ibaContainer = null;
    	try {
    		ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, locale, null);    	
    		ibaContainer = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
    	}
    	catch(Exception e) {
    		Kogger.error(EDMAttributes.class, e);
    	}
    	
    	if (ibaContainer != null) {
    		AbstractValueView[] avv = ibaContainer.getAttributeValues();
    		for (int i = 0; i < avv.length; i++) {
    			values.put(avv[i].getDefinition().getName(), avv[i]);
    		}
    		
    	}
    	
    	return values;
    }
    
    
    public static HashMap getIBAValues(IBAHolder ibaHolder, Locale locale) throws WTException {
    	HashMap values = new HashMap();
    	
    	//wt.iba.value.IBAValueUtility utility = new wt.iba.value.IBAValueUtility(ibaHolder, locale);
    	//utility.getAttributeValues();
    	
    	if (locale == null) locale = WTContext.getContext().getLocale();
    	
    	DefaultAttributeContainer ibaContainer = null;
    	try {
    		ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, locale, null);    	
    		ibaContainer = (DefaultAttributeContainer)ibaHolder.getAttributeContainer();
    	}
    	catch(Exception e) {
    		Kogger.error(EDMAttributes.class, e);
    	}
    	
    	if (ibaContainer != null) {
    		AbstractValueView[] avv = ibaContainer.getAttributeValues();
    		for (int i = 0; i < avv.length; i++) {
    			values.put(avv[i].getDefinition().getName(), getLocalizedIBAValueDisplayString(avv[i],locale));
    		}
    		
    	}
    	
    	return values;
    }
    
    public static String getLocalizedIBAValueDisplayString(AbstractValueView avv, Locale locale) throws WTException {
    	if(avv == null) {
    		return "";
    	}
    	
    	String str = new String();
    	if (avv instanceof ReferenceValueDefaultView) {
    		str = ((ReferenceValueDefaultView)avv).getLiteIBAReferenceable().getIBAReferenceableDisplayString();
    	} else {
    		str = avv.getLocalizedDisplayString(locale);
    	}
    	return str;
    }
    
}
