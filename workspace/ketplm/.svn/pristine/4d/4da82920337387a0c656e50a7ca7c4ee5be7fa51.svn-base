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

import wt.fc.ObjectToObjectLink;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

import ext.ket.dqm.entity.KETDqmHeader;

/**
 * 
 * <p>
 * Use the <code>newAssessResultDqmHeaderLink</code> static factory method(s), not the <code>AssessResultDqmHeaderLink</code> constructor,
 * to construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "dqmCheck", type = String.class, javaDoc = "Check"),
        @GeneratedProperty(name = "dqmCompDate", type = String.class, javaDoc = "완료일"),
        @GeneratedProperty(name = "dqmState", type = String.class, javaDoc = "상태"),
        @GeneratedProperty(name="rev", type=int.class)}, roleA = @GeneratedRole(name = "dqm", type = KETDqmHeader.class), roleB = @GeneratedRole(name = "assess", type = GateAssessResult.class, cardinality = Cardinality.ZERO_TO_ONE), tableProperties = @TableProperties(tableName = "KETAssessResultDqmHeaderLink"))
public class AssessResultDqmHeaderLink extends _AssessResultDqmHeaderLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param dqm
     * @param assess
     * @return AssessResultDqmHeaderLink
     * @exception wt.util.WTException
     **/
    public static AssessResultDqmHeaderLink newAssessResultDqmHeaderLink(KETDqmHeader dqm, GateAssessResult assess) throws WTException {

	AssessResultDqmHeaderLink instance = new AssessResultDqmHeaderLink();
	instance.initialize(dqm, assess);
	return instance;
    }

}
