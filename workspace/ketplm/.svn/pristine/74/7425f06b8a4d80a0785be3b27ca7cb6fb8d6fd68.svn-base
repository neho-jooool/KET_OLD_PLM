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

import wt.enterprise.Managed;
import wt.inf.container.WTContained;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 
 * <p>
 * Use the <code>newKETSamplePart</code> static factory method(s), not the <code>KETSamplePart</code> constructor, to construct instances of
 * this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = Managed.class, interfaces = { WTContained.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "requestCount", type = int.class), @GeneratedProperty(name = "dispensationCount", type = int.class),
        @GeneratedProperty(name = "receptionDate", type = Timestamp.class),
        @GeneratedProperty(name = "dispensationExpectDate", type = Timestamp.class),
        @GeneratedProperty(name = "samplePartStateCode", type = String.class), @GeneratedProperty(name = "pjtno", type = String.class),
        @GeneratedProperty(name = "pjtoid", type = String.class), @GeneratedProperty(name = "samplePartStateName", type = String.class),
        @GeneratedProperty(name = "dispensationDate", type = Timestamp.class) }, foreignKeys = {
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "part", type = wt.part.WTPart.class), myRole = @MyRole(name = "smplpart", cardinality = Cardinality.ZERO_TO_ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "user", type = wt.org.WTUser.class), myRole = @MyRole(name = "smplpm", cardinality = Cardinality.ZERO_TO_ONE)) })
public class KETSamplePart extends _KETSamplePart {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETSamplePart
     * @exception wt.util.WTException
     **/
    public static KETSamplePart newKETSamplePart() throws WTException {

	KETSamplePart instance = new KETSamplePart();
	instance.initialize();
	return instance;
    }

}
