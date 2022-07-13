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

package ext.ket.project.program.entity;

import java.sql.Timestamp;

import wt.fc.WTObject;
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
 * Use the <code>newKETProgramEvent</code> static factory method(s), not the <code>KETProgramEvent</code> constructor, to construct instances of this class. Instances must be constructed using the
 * static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTObject.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "customerEventName", type = String.class, javaDoc = "고객 이벤트 명"), @GeneratedProperty(name = "customerEventDate", type = Timestamp.class, javaDoc = "고객이벤트 일정"),
        @GeneratedProperty(name = "eventOrder", type = int.class, initialValue = "0", javaDoc = "이벤트 순서") }, foreignKeys = { @GeneratedForeignKey(name = "KETOemPlanLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "oemPlan", type = e3ps.project.outputtype.OEMPlan.class), myRole = @MyRole(name = "event", cardinality = Cardinality.ZERO_TO_ONE)) })
public class KETProgramEvent extends _KETProgramEvent {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETProgramEvent
     * @exception wt.util.WTException
     **/
    public static KETProgramEvent newKETProgramEvent() throws WTException {

	KETProgramEvent instance = new KETProgramEvent();
	instance.initialize();
	return instance;
    }

}
