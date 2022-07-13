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

package ext.ket.sample.entity;

import java.sql.Timestamp;

import wt.content.FormatContentHolder;
import wt.enterprise.Managed;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 
 * <p>
 * Use the <code>newKETSampleRequest</code> static factory method(s), not the <code>KETSampleRequest</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = Managed.class, interfaces = { FormatContentHolder.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "requestNo", type = String.class), @GeneratedProperty(name = "requestDate", type = Timestamp.class),
        @GeneratedProperty(name = "requestTitle", type = String.class),
        @GeneratedProperty(name = "customerCode", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "customerName", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "customerContractor", type = String.class),
        @GeneratedProperty(name = "carTypeCode", type = String.class), @GeneratedProperty(name = "carTypeName", type = String.class),
        @GeneratedProperty(name = "developeStageName", type = String.class),
        @GeneratedProperty(name = "developeStageCode", type = String.class),
        @GeneratedProperty(name = "sampleRequestStateCode", type = String.class),
        @GeneratedProperty(name = "sampleRequestStateName", type = String.class),
        @GeneratedProperty(name = "requestDeptName", type = String.class),
        @GeneratedProperty(name = "requestDeptCode", type = String.class), @GeneratedProperty(name = "purpose", type = String.class),
        @GeneratedProperty(name = "webEditor", type = Object.class, columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)),
        @GeneratedProperty(name = "webEditorText", type = Object.class, columnProperties = @ColumnProperties(columnType = ColumnType.BLOB)) }, foreignKeys = { @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "user", type = wt.org.WTUser.class), myRole = @MyRole(name = "contractor", cardinality = Cardinality.ZERO_TO_ONE)) })
public class KETSampleRequest extends _KETSampleRequest {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETSampleRequest
     * @exception wt.util.WTException
     **/
    public static KETSampleRequest newKETSampleRequest() throws WTException {

	KETSampleRequest instance = new KETSampleRequest();
	instance.initialize();
	return instance;
    }

}
