/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */

package ext.ket.project.gate.entity;

import wt.fc.WTObject;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newGateCheckSheet</code> static factory method(s), not the <code>GateCheckSheet</code> constructor, to construct instances
 * of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTObject.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "checkSheetName", type = String.class, javaDoc = "Check Sheet Name"),
        @GeneratedProperty(name = "achieveBase", type = String.class, javaDoc = "달성기준"),
        @GeneratedProperty(name = "unit", type = String.class, javaDoc = "단위"),
        @GeneratedProperty(name = "criteria", type = String.class, javaDoc = "기준"),
        @GeneratedProperty(name = "orderNo", type = int.class, javaDoc = "정렬순서"),
        @GeneratedProperty(name = "attr", type = GateAttribute.class, constraints = @PropertyConstraints(required = true), columnProperties = @ColumnProperties(columnName = "B")) }, foreignKeys = { @GeneratedForeignKey(name = "GateCheckTargetTypeLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "targetType", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "check", cardinality = Cardinality.ONE)) }, tableProperties = @TableProperties(tableName = "KETGateCheckSheet"))
public class GateCheckSheet extends _GateCheckSheet {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return GateCheckSheet
     * @exception wt.util.WTException
     **/
    public static GateCheckSheet newGateCheckSheet() throws WTException {

	GateCheckSheet instance = new GateCheckSheet();
	instance.initialize();
	return instance;
    }

}
