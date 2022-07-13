package e3ps.edm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;

import wt.clients.iba.container.NewValueCreator;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.ReferenceFactory;
import wt.iba.definition.AbstractAttributeDefinition;
import wt.iba.definition.BooleanDefinition;
import wt.iba.definition.FloatDefinition;
import wt.iba.definition.IntegerDefinition;
import wt.iba.definition.RatioDefinition;
import wt.iba.definition.ReferenceDefinition;
import wt.iba.definition.StringDefinition;
import wt.iba.definition.StringDefinitionReference;
import wt.iba.definition.TimestampDefinition;
import wt.iba.definition.URLDefinition;
import wt.iba.definition.UnitDefinition;
import wt.iba.definition.litedefinition.AttributeDefDefaultView;
import wt.iba.value.AbstractValue;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.IBAHolder;
import wt.iba.value.IBAHolderReference;
import wt.iba.value.StringValue;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.litevalue.BooleanValueDefaultView;
import wt.iba.value.litevalue.FloatValueDefaultView;
import wt.iba.value.litevalue.IntegerValueDefaultView;
import wt.iba.value.litevalue.RatioValueDefaultView;
import wt.iba.value.litevalue.ReferenceValueDefaultView;
import wt.iba.value.litevalue.StringValueDefaultView;
import wt.iba.value.litevalue.TimestampValueDefaultView;
import wt.iba.value.litevalue.URLValueDefaultView;
import wt.iba.value.litevalue.UnitValueDefaultView;
import wt.iba.value.service.IBAValueHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

public class EDMAttributeUtil {

    public static IBAHolder updateIBAHolder(IBAHolder ibaHolder) throws WTException {
	try {

	    ibaHolder = (IBAHolder) PersistenceHelper.manager.refresh((Persistable) ibaHolder);
	    ibaHolder = IBAValueHelper.service.updateIBAHolder(ibaHolder, null, null, null);
	    // epm = (EPMDocument)PersistenceServerHelper.manager.restore(epm);
	} catch (Exception e) {
	    throw new WTException(e.getLocalizedMessage());
	}

	return refreshAttr(ibaHolder);
    }

    public static IBAHolder refreshAttr(IBAHolder ibaHolder) throws WTException {
	try {
	    ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, null, null);
	} catch (RemoteException e) {
	    throw new WTException(e.getLocalizedMessage());
	}
	return ibaHolder;
    }

    public static IBAHolder setAttributeValues(IBAHolder ibaHolder, HashMap map) throws WTException, RemoteException {
	// Kogger.debug(getClass(), "=========    IBA begin  ====================================");
	// Kogger.debug(getClass(), "========= " + ibaHolder.getClass().getName());

	EDMAttributes edmAttributes = EDMAttributes.getInstance();

	Hashtable ibavalue = new Hashtable();
	HashMap ibaDefs = EDMAttributes.getInstance().getEPMAttributes();

	Iterator ibaDefitr = ibaDefs.keySet().iterator();

	while (ibaDefitr.hasNext()) {
	    String defName = (String) ibaDefitr.next();
	    AttributeDefDefaultView defview = (AttributeDefDefaultView) ibaDefs.get(defName);

	    Object value = edmAttributes.convertValue(defName, map.get(defName), Locale.KOREAN);
	    if ((value != null)) {
		ibavalue.put(defview, value);
	    }
	}

	DefaultAttributeContainer container = updateAttribute(ibaHolder, ibavalue);
	if (container != null) {
	    ibaHolder.setAttributeContainer(container);
	}
	// Kogger.debug(getClass(), "=========    IBA end   ====================================");
	return ibaHolder;
    }

    public static DefaultAttributeContainer updateAttribute(IBAHolder ibaHolder, Hashtable hash) throws WTException {
	DefaultAttributeContainer container = null;
	try {
	    IBAHolder iba = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, null, null);
	    container = (DefaultAttributeContainer) iba.getAttributeContainer();
	    if (container == null) {
		container = new DefaultAttributeContainer();
	    }

	    HashMap dMap = new HashMap();
	    HashMap vMap = new HashMap();

	    Enumeration enums = hash.keys();
	    AttributeDefDefaultView aview = null;
	    Object value = null;
	    while (enums.hasMoreElements()) {
		aview = (AttributeDefDefaultView) enums.nextElement();

		dMap.put(aview.getObjectID().getStringValue(), aview);
		vMap.put(aview.getObjectID().getStringValue(), hash.get(aview));
	    }

	    AttributeDefDefaultView definitions[] = container.getAttributeDefinitions();
	    for (int i = 0; i < definitions.length; i++) {
		AttributeDefDefaultView definition = definitions[i];
		String objectID = definition.getObjectID().getStringValue();

		if (!dMap.containsKey(objectID) || (vMap.get(objectID) == null)) {
		    container.deleteAttributeValues(definition);
		    continue;
		} else {
		    AbstractValueView abstractvalueview[] = container.getAttributeValues(definition);

		    if (abstractvalueview[0] instanceof FloatValueDefaultView) {
			FloatValueDefaultView view = (FloatValueDefaultView) abstractvalueview[0];
			view.setValue(Double.parseDouble((String) (String) vMap.get(objectID)));
			container.updateAttributeValue(view);

		    } else if (abstractvalueview[0] instanceof RatioValueDefaultView) {
			RatioValueDefaultView view = (RatioValueDefaultView) abstractvalueview[0];
			view.setValue(Double.parseDouble((String) (String) vMap.get(objectID)));
			container.updateAttributeValue(view);

		    } else if (abstractvalueview[0] instanceof TimestampValueDefaultView) {
			TimestampValueDefaultView view = (TimestampValueDefaultView) abstractvalueview[0];
			view.setValue((Timestamp) vMap.get(objectID));
			container.updateAttributeValue(view);

		    } else if (abstractvalueview[0] instanceof URLValueDefaultView) {
			URLValueDefaultView view = (URLValueDefaultView) abstractvalueview[0];
			view.setValue((String) vMap.get(objectID));
			container.updateAttributeValue(view);

		    } else if (abstractvalueview[0] instanceof ReferenceValueDefaultView) {
			ReferenceValueDefaultView view = (ReferenceValueDefaultView) abstractvalueview[0];
			// ...

		    } else if (abstractvalueview[0] instanceof IntegerValueDefaultView) {
			IntegerValueDefaultView view = (IntegerValueDefaultView) abstractvalueview[0];
			view.setValue(Long.parseLong((String) vMap.get(objectID)));
			container.updateAttributeValue(view);

		    } else if (abstractvalueview[0] instanceof StringValueDefaultView) {
			StringValueDefaultView view = (StringValueDefaultView) abstractvalueview[0];
			view.setValue((String) vMap.get(objectID));
			container.updateAttributeValue(view);

		    } else if (abstractvalueview[0] instanceof BooleanValueDefaultView) {
			BooleanValueDefaultView view = (BooleanValueDefaultView) abstractvalueview[0];
			view.setValue(Boolean.parseBoolean((String) vMap.get(objectID)));
			container.updateAttributeValue(view);

		    } else if (abstractvalueview[0] instanceof UnitValueDefaultView) {
			UnitValueDefaultView view = (UnitValueDefaultView) abstractvalueview[0];
			view.setValue(Double.parseDouble((String) (String) vMap.get(objectID)));
			container.updateAttributeValue(view);
		    }

		    dMap.remove(objectID);
		}

	    }

	    AbstractValueView valueView = null;

	    Iterator itr = dMap.keySet().iterator();
	    while (itr.hasNext()) {
		String objectID = (String) itr.next();

		AttributeDefDefaultView defView = (AttributeDefDefaultView) dMap.get(objectID);
		Object value0 = vMap.get(objectID);

		if (value0 == null) {
		    continue;
		}

		valueView = (AbstractValueView) NewValueCreator.createNewValueObject(defView);

		if (valueView instanceof FloatValueDefaultView) {
		    FloatValueDefaultView view = (FloatValueDefaultView) valueView;
		    view.setValue(Double.parseDouble((String) (String) vMap.get(objectID)));
		    container.addAttributeValue(view);

		} else if (valueView instanceof RatioValueDefaultView) {
		    RatioValueDefaultView view = (RatioValueDefaultView) valueView;
		    view.setValue(Double.parseDouble((String) (String) vMap.get(objectID)));
		    container.addAttributeValue(view);

		} else if (valueView instanceof TimestampValueDefaultView) {
		    TimestampValueDefaultView view = (TimestampValueDefaultView) valueView;
		    view.setValue((Timestamp) vMap.get(objectID));
		    container.addAttributeValue(view);

		} else if (valueView instanceof URLValueDefaultView) {
		    URLValueDefaultView view = (URLValueDefaultView) valueView;
		    view.setValue((String) vMap.get(objectID));
		    container.addAttributeValue(view);

		} else if (valueView instanceof ReferenceValueDefaultView) {
		    ReferenceValueDefaultView view = (ReferenceValueDefaultView) valueView;
		    // ...

		} else if (valueView instanceof IntegerValueDefaultView) {
		    IntegerValueDefaultView view = (IntegerValueDefaultView) valueView;
		    view.setValue(Long.parseLong((String) vMap.get(objectID)));
		    container.addAttributeValue(view);

		} else if (valueView instanceof StringValueDefaultView) {
		    StringValueDefaultView view = (StringValueDefaultView) valueView;
		    view.setValue((String) vMap.get(objectID));
		    container.addAttributeValue(view);

		} else if (valueView instanceof BooleanValueDefaultView) {
		    BooleanValueDefaultView view = (BooleanValueDefaultView) valueView;
		    view.setValue(Boolean.parseBoolean((String) vMap.get(objectID)));
		    container.addAttributeValue(view);

		} else if (valueView instanceof UnitValueDefaultView) {
		    UnitValueDefaultView view = (UnitValueDefaultView) valueView;
		    view.setValue(Double.parseDouble((String) (String) vMap.get(objectID)));
		    container.addAttributeValue(view);
		}
	    }
	} catch (WTException e) {
	    container = null;
	    throw new WTException(e.getLocalizedMessage());

	} catch (RemoteException e) {
	    container = null;
	    throw new WTException(e.getLocalizedMessage());
	} catch (WTPropertyVetoException e) {
	    container = null;
	    throw new WTException(e.getLocalizedMessage());
	}

	return container;
    }

    public static void copyAttributes(IBAHolder source, IBAHolder target) throws WTException, WTPropertyVetoException {

	ArrayList list = new ArrayList();
	list.add(target);
	copyAttributes(source, list);
    }

    public static void copyAttributes(IBAHolder source, ArrayList targets) throws WTException, WTPropertyVetoException {

	if ((source == null) || (targets == null) || (targets.size() == 0)) {
	    return;
	}

	EDMProperties edmProperties = EDMProperties.getInstance();
	String[] defNames = edmProperties.getAttributeDefSync();
	if (defNames.length == 0) {
	    return;
	}

	HashMap dam = new HashMap();
	for (int i = 0; i < defNames.length; i++) {
	    dam.put(defNames[i], "");
	}

	IBAHolder sourceHolder = null;
	try {
	    sourceHolder = IBAValueHelper.service.refreshAttributeContainer(source, null, null, null);
	} catch (RemoteException e) {
	    throw new WTException(e.getLocalizedMessage());
	}

	DefaultAttributeContainer sourceContainer = (DefaultAttributeContainer) sourceHolder.getAttributeContainer();
	if (sourceContainer == null) {
	    return;
	}

	// AttributeDefDefaultView[] defDefaultViews = sourceContainer.getAttributeDefinitions();
	AbstractValueView[] valueViews = sourceContainer.getAttributeValues();

	for (int i = 0; i < valueViews.length; i++) {
	    AbstractValueView valueView = valueViews[i];
	    if (dam.containsKey(valueView.getDefinition().getName())) {
		String value = "";
		if (valueView instanceof StringValueDefaultView) {
		    StringValueDefaultView stringView = (StringValueDefaultView) valueView;
		    value = stringView.getValue();
		}
		dam.put(valueView.getDefinition().getName(), value);
	    }
	}

	setAttributes(targets, dam, false);
    }

    public static void setAttributes(ArrayList ibaHolders, HashMap attrMap, boolean isOverride) throws WTException, WTPropertyVetoException {

	if ((ibaHolders == null) || (ibaHolders.size() == 0)) {
	    return;
	}

	if (attrMap == null) {
	    attrMap = new HashMap();
	}
	/*
	 * Kogger.debug(getClass(), "!!!!!!!!!! KEY-Value !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	 * HashMap hm0 = (HashMap)attrMap.clone();
	 * Iterator itr0 = hm0.keySet().iterator();
	 * while(itr0.hasNext()) {
	 * String key = (String)itr0.next();
	 * String value = (String)hm0.get(key);
	 * Kogger.debug(getClass(), key + "-" + value);
	 * }
	 * Kogger.debug(getClass(), "!!!!!!!!!!! Target IBAHolder !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	 * for(int i = 0 ; i< ibaHolders.size(); i++) {
	 * Kogger.debug(getClass(), PersistenceHelper.getObjectIdentifier((Persistable)(IBAHolder)ibaHolders.get(i)).getStringValue());
	 * }
	 */

	ReferenceFactory rf = new ReferenceFactory();

	EDMAttributes edmAttributes = EDMAttributes.getInstance();

	IBAHolder ibaHolder = null;
	for (int k = 0; k < ibaHolders.size(); k++) {
	    ibaHolder = (IBAHolder) ibaHolders.get(k);

	    HashMap attrMap0 = (HashMap) attrMap.clone();

	    try {
		ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, null, null);
	    } catch (RemoteException e) {
		throw new WTException(e.getLocalizedMessage());
	    }

	    DefaultAttributeContainer container = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
	    if (container == null) {
		return;
	    }

	    AbstractValueView[] targetValueViews = container.getAttributeValues();

	    for (int i = 0; i < targetValueViews.length; i++) {
		AbstractValueView valueView = targetValueViews[i];

		if (!attrMap0.containsKey(valueView.getDefinition().getName())) {
		    if (isOverride) {
			AbstractValue v = (AbstractValue) (new ReferenceFactory()).getReference(valueView.getObjectID().toString()).getObject();
			PersistenceHelper.manager.delete(v);
		    }
		    continue;
		}

		String sourceValue = (String) attrMap0.get(valueView.getDefinition().getName());
		if (sourceValue.length() == 0) {
		    AbstractValue v = (AbstractValue) (new ReferenceFactory()).getReference(valueView.getObjectID().toString()).getObject();
		    PersistenceHelper.manager.delete(v);

		} else {
		    if (valueView instanceof StringValueDefaultView) {
			StringValueDefaultView stringView = (StringValueDefaultView) valueView;
			String value = stringView.getValue();

			if (!sourceValue.equals(value)) {
			    StringValue sv = (StringValue) (new ReferenceFactory()).getReference(stringView.getObjectID().toString()).getObject();
			    sv.setValue(sourceValue);
			    PersistenceHelper.manager.modify(sv);
			}
		    }
		}

		attrMap0.remove(valueView.getDefinition().getName());
	    }

	    Iterator iterator = attrMap0.keySet().iterator();
	    while (iterator.hasNext()) {
		String defName = (String) iterator.next();
		String value = (String) attrMap0.get(defName);
		if (value.length() == 0) {
		    continue;
		}

		AttributeDefDefaultView defDefaultView = (AttributeDefDefaultView) edmAttributes.getEPMAttribute(defName);
		createAttributeValue(ibaHolder, (StringDefinition) rf.getReference(defDefaultView.getObjectID().toString()).getObject(), value);
	    }

	    try {
		ibaHolder = IBAValueHelper.service.refreshAttributeContainer(ibaHolder, null, null, null);
	    } catch (RemoteException e) {
		throw new WTException(e.getLocalizedMessage());
	    }
	    /*
	     * Kogger.debug(getClass(), "********* After ...***********************");
	     * DefaultAttributeContainer after0 = (DefaultAttributeContainer) ibaHolder.getAttributeContainer();
	     * AbstractValueView[] afterView0 = after0.getAttributeValues();
	     * for(int i = 0; i < afterView0.length; i++) {
	     * System.out.print(afterView0[i].getDefinition().getName());
	     * if(afterView0[i] instanceof StringValueDefaultView) {
	     * StringValueDefaultView stringView = (StringValueDefaultView)afterView0[i];
	     * String value = stringView.getValue();
	     * Kogger.debug(getClass(), "-" + value);
	     * }
	     * }
	     */
	}
    }

    public static void createAttributeValue(IBAHolder ibaHolder, AbstractAttributeDefinition definition, Object value) throws WTException, WTPropertyVetoException {

	AbstractValue av = null;
	if (definition instanceof FloatDefinition) {
	} else if (definition instanceof RatioDefinition) {
	} else if (definition instanceof TimestampDefinition) {
	} else if (definition instanceof URLDefinition) {
	} else if (definition instanceof ReferenceDefinition) {
	} else if (definition instanceof IntegerDefinition) {
	} else if (definition instanceof StringDefinition) {
	    av = new StringValue();
	    ((StringValue) av).setValue((String) value);
	    ((StringValue) av).setDefinitionReference(StringDefinitionReference.newStringDefinitionReference((StringDefinition) definition));
	    ((StringValue) av).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaHolder));
	} else if (definition instanceof BooleanDefinition) {
	} else if (definition instanceof UnitDefinition) {
	}

	if (av != null) {
	    av = (AbstractValue) PersistenceHelper.manager.save(av);
	}
	return;
    }

    public static void deleteAttributeValue(AbstractValueView view) throws WTException {
	ReferenceFactory rf = new ReferenceFactory();
	AbstractValue av = (AbstractValue) rf.getReference(view.getObjectID().getStringValue()).getObject();
	PersistenceHelper.manager.delete(av);
    }

    public static Object getConvertedValue(String paramString, Class paramClass) {
	Object obj = null;
	if (paramClass.getName() == "java.sql.Timestamp") {
	    obj = new Timestamp(Long.parseLong(paramString));
	} else if (paramClass.getName() == "long") {
	    obj = new Integer(paramString);
	} else if (paramClass.getName() == "double") {
	    obj = new Double(paramString);
	} else if (paramClass.getName() == "boolean") {
	    if (paramString.equals("1")) {
		obj = new Boolean("true");
	    } else {
		obj = new Boolean("false");
	    }
	} else {
	    obj = paramString;
	}

	return obj;
    }

    public static void setValue(AbstractValueView view, Object obj) throws WTException {

	try {
	    Class[] arrayOfClass = { view.getClass().getDeclaredField("value").getType() };
	    Method localMethod = view.getClass().getDeclaredMethod("setValue", arrayOfClass);

	    Object[] arrayOfObject = { obj };
	    localMethod.invoke(view, arrayOfObject);
	} catch (InvocationTargetException localInvocationTargetException) {
	    throw new WTException(localInvocationTargetException.getTargetException());
	} catch (Exception localException) {
	    throw new WTException(localException);
	}
    }
}
