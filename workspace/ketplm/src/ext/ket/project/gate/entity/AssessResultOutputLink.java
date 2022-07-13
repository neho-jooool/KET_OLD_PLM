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

import e3ps.project.ProjectOutput;

/**
 * 
 * <p>
 * Use the <code>newAssessResultOutputLink</code> static factory method(s), not the <code>AssessResultOutputLink</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "outputCheck", type = String.class, javaDoc = "체크여부"),
        @GeneratedProperty(name = "outputComment", type = String.class, javaDoc = "comment"),
        @GeneratedProperty(name = "outputName", type = String.class, javaDoc = "산출물명"),
        @GeneratedProperty(name = "outputCharge", type = String.class, javaDoc = "책임자"),
        @GeneratedProperty(name = "outputState", type = String.class, javaDoc = "상태"),
        @GeneratedProperty(name = "outputOid", type = String.class, javaDoc = "산출물객체"),
        @GeneratedProperty(name = "rev", type=int.class)}, roleA = @GeneratedRole(name = "assess", type = GateAssessResult.class, cardinality = Cardinality.ZERO_TO_ONE), roleB = @GeneratedRole(name = "output", type = ProjectOutput.class), tableProperties = @TableProperties(tableName = "KETAssessResultOutputLink"))
public class AssessResultOutputLink extends _AssessResultOutputLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param assess
     * @param output
     * @return AssessResultOutputLink
     * @exception wt.util.WTException
     **/
    public static AssessResultOutputLink newAssessResultOutputLink(GateAssessResult assess, ProjectOutput output) throws WTException {

	AssessResultOutputLink instance = new AssessResultOutputLink();
	instance.initialize(assess, output);
	return instance;
    }

}
