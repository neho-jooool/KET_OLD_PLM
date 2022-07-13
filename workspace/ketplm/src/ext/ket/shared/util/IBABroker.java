package ext.ket.shared.util;

import java.sql.Timestamp;
import java.util.Vector;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.iba.value.AbstractValue;
import wt.iba.value.BooleanValue;
import wt.iba.value.DefaultAttributeContainer;
import wt.iba.value.FloatValue;
import wt.iba.value.IBAHolder;
import wt.iba.value.IBAHolderReference;
import wt.iba.value.IntegerValue;
import wt.iba.value.RatioValue;
import wt.iba.value.StringValue;
import wt.iba.value.TimestampValue;
import wt.iba.value.URLValue;
import wt.iba.value.UnitValue;
import wt.iba.value.litevalue.AbstractValueView;
import wt.iba.value.litevalue.BooleanValueDefaultView;
import wt.iba.value.litevalue.FloatValueDefaultView;
import wt.iba.value.litevalue.IntegerValueDefaultView;
import wt.iba.value.litevalue.RatioValueDefaultView;
import wt.iba.value.litevalue.StringValueDefaultView;
import wt.iba.value.litevalue.TimestampValueDefaultView;
import wt.iba.value.litevalue.URLValueDefaultView;
import wt.iba.value.litevalue.UnitValueDefaultView;
import wt.iba.value.service.IBAValueHelper;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import wt.query.SearchCondition;

import com.ptc.core.meta.type.mgmt.server.impl.TypeSingleAttrConstraint;

import e3ps.common.util.StringUtil;

/**
 * @(#) IBABroker.java
 *      Copyright (c) Digitek. All rights reserverd
 * @version 1.00
 * @since jdk1.6.0_18
 * @createdate 2010. 11. 23.
 * @author Seong Jin, Han. narrsass@naver.com
 * @desc
 */
public class IBABroker {

    public Class   ibaDefinitionClass;
    public String  ibaName;
    public String  ibaValue;
    public String  ibaValue1;
    public String  ibaValue2;
    public boolean isValuePair;

    public IBABroker(Class ibaDefinitionClass, String ibaName, String ibaValue) {
	this.ibaDefinitionClass = ibaDefinitionClass;
	this.ibaName = ibaName;
	this.ibaValue = ibaValue;
	this.isValuePair = false;
    }

    public IBABroker(Class ibaDefinitionClass, String ibaName, String ibaValue1, String ibaValue2) {
	this.ibaDefinitionClass = ibaDefinitionClass;
	this.ibaName = ibaName;
	this.ibaValue1 = "".equals(ibaValue1) ? null : ibaValue1;
	this.ibaValue2 = "".equals(ibaValue2) ? null : ibaValue2;
	this.isValuePair = true;
    }

    /**
     * @param obj
     * @param ibaname
     * @param ibavalue
     * @throws Exception
     */
    public static void setIBAValueStr(Object obj, String ibaname, String ibavalue) throws Exception {
	IBAHolder ibaholder = (IBAHolder) obj;
	ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, null, null);
	DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaholder.getAttributeContainer();
	AbstractValueView abstractValuesView[] = defaultattributecontainer.getAttributeValues();

	for (int k = 0; k < abstractValuesView.length; k++) {
	    // Logger.user.println("abstractValuesView["+k+"].getDefinition().getName()---"+abstractValuesView[k].getDefinition().getName());

	    AbstractValue abstractvalue = (AbstractValue) PersistenceHelper.manager.refresh(abstractValuesView[k].getObjectID());

	    if (abstractValuesView[k].getDefinition().getName().equals(ibaname)) {
		if (abstractValuesView[k] instanceof StringValueDefaultView) {
		    // Logger.user.println("StringValue");
		    ((StringValue) abstractvalue).setValue(ibavalue);
		    ((StringValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		} else if (abstractValuesView[k] instanceof FloatValueDefaultView) {
		    // Logger.user.println("FloatValue");
		    ((FloatValue) abstractvalue).setValue(Double.parseDouble(ibavalue));
		    ((FloatValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		} else if (abstractValuesView[k] instanceof IntegerValueDefaultView) {
		    // Logger.user.println("IntegerValue");
		    ((IntegerValue) abstractvalue).setValue(Long.parseLong(ibavalue));
		    ((IntegerValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		} else if (abstractValuesView[k] instanceof BooleanValueDefaultView) {
		    // Logger.user.println("BooleanValue");
		    // ((BooleanValue)abstractvalue).setValue(Boolean.getBoolean(ibavalue));
		    ((BooleanValue) abstractvalue).setValue("true".equals(ibavalue) ? Boolean.TRUE : Boolean.FALSE);
		    ((BooleanValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		} else if (abstractValuesView[k] instanceof TimestampValueDefaultView) {
		    // Logger.user.println("TimestampValue");
		    ((TimestampValue) abstractvalue).setValue(Timestamp.valueOf(ibavalue));
		    ((TimestampValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		} else if (abstractValuesView[k] instanceof UnitValueDefaultView) {
		    // Logger.user.println("UnitValue");
		    ((UnitValue) abstractvalue).setValue(Double.parseDouble(ibavalue));
		    ((UnitValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		} else if (abstractValuesView[k] instanceof RatioValueDefaultView) {
		    // Logger.user.println("RatioValue");
		    ((RatioValue) abstractvalue).setValue(Double.parseDouble(ibavalue));
		    ((RatioValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		} else if (abstractValuesView[k] instanceof URLValueDefaultView) {
		    // Logger.user.println("URLValue");
		    ((URLValue) abstractvalue).setValue(ibavalue);
		    ((URLValue) abstractvalue).setIBAHolderReference(IBAHolderReference.newIBAHolderReference(ibaholder));
		}
		PersistenceHelper.manager.save(abstractvalue);
		break;
	    }
	}
    }

    /**
     * @param obj
     * @param ibaname
     * @return
     * @throws Exception
     */
    public static Vector getConstraints(Object obj, String ibaname) throws Exception {
	Vector constVec = new Vector();
	String constString = "";
	TypeSingleAttrConstraint constraintObj = null;
	Class objClass = wt.type.Typed.class;
	Class constClass = TypeSingleAttrConstraint.class;
	Class definitClass = wt.iba.definition.StringDefinition.class;
	QuerySpec query = new QuerySpec();
	int objIdx = query.appendClassList(objClass, false);
	int constIdx = query.appendClassList(constClass, true);
	int definitIdx = query.appendClassList(definitClass, false);
	query.appendWhere(new SearchCondition(objClass, "typeDefinitionReference.key.id", constClass, "typeDefinitionReference.key.id"), new int[] { objIdx, constIdx });
	query.appendAnd();
	query.appendWhere(new SearchCondition(constClass, "attributeDefinition.key.id", definitClass, "thePersistInfo.theObjectIdentifier.id"), new int[] { constIdx, definitIdx });
	query.appendAnd();
	long objId = PersistenceHelper.getObjectIdentifier((Persistable) obj).getId();
	query.appendWhere(new SearchCondition(objClass, "thePersistInfo.theObjectIdentifier.id", SearchCondition.EQUAL, objId), new int[] { objIdx });
	query.appendAnd();
	query.appendWhere(new SearchCondition(definitClass, "name", SearchCondition.EQUAL, ibaname), new int[] { definitIdx });
	QueryResult qResult = PersistenceHelper.manager.find((StatementSpec) query);
	if (qResult.hasMoreElements()) {
	    Object[] tempObj = (Object[]) qResult.nextElement();
	    constraintObj = (TypeSingleAttrConstraint) tempObj[0];
	    constString = constraintObj.getEnforcementRuleData().toString();
	    constString = StringUtil.replaceString(constString, "[", "");
	    constString = StringUtil.replaceString(constString, "]", "");
	    constVec = StringUtil.getSplitAllStringVector(constString, ",");
	} else {
	    constVec = null;
	}
	return constVec;
    }

    /**
     * @param obj
     * @return
     * @throws Exception
     */
    public static Vector getIBASet(Object obj) throws Exception {
	Vector ibaSetVec = null;
	IBAHolder ibaholder = (IBAHolder) obj;
	ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, null, null);
	DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaholder.getAttributeContainer();
	AbstractValueView abstractValuesView[] = defaultattributecontainer.getAttributeValues();
	if (abstractValuesView != null && abstractValuesView.length > 0) {
	    int maxLength = abstractValuesView.length;
	    String ibaName[] = new String[maxLength];
	    String ibaValue[] = new String[maxLength];
	    for (int k = 0; k < maxLength; k++) {
		// Logger.user.println(" "+abstractValuesView[k].getDefinition().getName()+","+getAttributeValue(abstractValuesView[k]));
		ibaName[k] = abstractValuesView[k].getDefinition().getName();
		ibaValue[k] = getAttributeValue(abstractValuesView[k]);
	    }
	    // Sorting
	    String tempIbaName = "";
	    String tempIbaValue = "";
	    for (int i = 0; i < maxLength; i++) {
		for (int j = i; j < maxLength; j++) {
		    if (ibaName[i].compareTo(ibaName[j]) > 0) {
			tempIbaName = ibaName[j];
			tempIbaValue = ibaValue[j];
			ibaName[j] = ibaName[i];
			ibaValue[j] = ibaValue[i];
			ibaName[i] = tempIbaName;
			ibaValue[i] = tempIbaValue;
		    }
		}
	    }
	    // Setting at Vector
	    ibaSetVec = new Vector();
	    for (int idx = 0; idx < maxLength; idx++) {
		ibaSetVec.add(new String[] { ibaName[idx], ibaValue[idx] });
	    }
	}
	return ibaSetVec;
    }

    /**
     * @param obj
     * @param ibaname
     * @return
     * @throws Exception
     */
    public static String getIBAValueStr(Object obj, String ibaname) throws Exception {
	AbstractValueView abstractValuesView[] = getAbstractValues(obj);

	for (int k = 0; k < abstractValuesView.length; k++) {
	    if (abstractValuesView[k].getDefinition().getName().equals(ibaname)) {
		// if(abstractValuesView[k] instanceof StringValueDefaultView) {
		return (getAttributeValue(abstractValuesView[k]));
		// }
	    }
	}

	// We have not found the value so we return nothing
	// Logger.user.println("Failed to find specific IBA Value");
	return null;
    }

    /**
     * �ش� Object�� �����ִ� IBA�� Array�� �������� �޼ҵ�.
     * 
     * @param obj
     *            IBA�� �����ִ� Object
     * @return IBA�� Array
     */
    private static AbstractValueView[] getAbstractValues(Object obj) throws Exception {
	IBAHolder ibaholder = (IBAHolder) obj;
	ibaholder = IBAValueHelper.service.refreshAttributeContainer(ibaholder, null, null, null);
	DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer) ibaholder.getAttributeContainer();

	if (defaultattributecontainer != null) {
	    return (defaultattributecontainer.getAttributeValues());
	}
	return (null);
    }

    /**
     * @param abstractvalueview
     * @return
     * @throws Exception
     */
    public static String getAttributeValue(AbstractValueView abstractvalueview) throws Exception {
	String s = new String();
	if (abstractvalueview instanceof StringValueDefaultView)
	    s = ((StringValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof FloatValueDefaultView)
	    s = ((FloatValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof IntegerValueDefaultView)
	    s = ((IntegerValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof BooleanValueDefaultView)
	    s = ((BooleanValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof TimestampValueDefaultView)
	    s = ((TimestampValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof UnitValueDefaultView)
	    s = ((UnitValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof RatioValueDefaultView)
	    s = ((RatioValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof URLValueDefaultView)
	    s = ((URLValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	else if (abstractvalueview instanceof UnitValueDefaultView)
	    s = ((UnitValueDefaultView) abstractvalueview).getLocalizedDisplayString();
	return s;
    }

}
