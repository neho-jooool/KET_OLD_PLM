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
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newAssessResultGateCheckLink</code> static factory method(s), not the <code>AssessResultGateCheckLink</code> constructor,
 * to construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "sheetResult", type = int.class, javaDoc = "실적"),
        @GeneratedProperty(name = "sheetCheck", type = String.class, javaDoc = "Check"),
        @GeneratedProperty(name = "sheetCheck1", type = String.class, javaDoc = "sheetCheck1"),
        @GeneratedProperty(name = "sheetCheck2", type = String.class, javaDoc = "sheetCheck2"),
        @GeneratedProperty(name = "sheetCheck3", type = String.class, javaDoc = "sheetCheck3"),
        @GeneratedProperty(name = "sheetCheck4", type = String.class, javaDoc = "sheetCheck4"),
        @GeneratedProperty(name = "sheetCheck5", type = String.class, javaDoc = "sheetCheck5"),
        @GeneratedProperty(name = "sheetCheck6", type = String.class, javaDoc = "sheetCheck6"),
        @GeneratedProperty(name = "sheetCheck7", type = String.class, javaDoc = "sheetCheck7"),
        @GeneratedProperty(name = "sheetCheck8", type = String.class, javaDoc = "sheetCheck8"),
        @GeneratedProperty(name = "sheetCheck9", type = String.class, javaDoc = "sheetCheck9"),
        @GeneratedProperty(name = "sheetCheck10", type = String.class, javaDoc = "sheetCheck10"),
        @GeneratedProperty(name = "sheetCheck11", type = String.class, javaDoc = "sheetCheck11"),
        @GeneratedProperty(name = "sheetCheck12", type = String.class, javaDoc = "sheetCheck12"),
        @GeneratedProperty(name = "sheetCheck13", type = String.class, javaDoc = "sheetCheck13"),
        @GeneratedProperty(name = "sheetCheck14", type = String.class, javaDoc = "sheetCheck14"),
        @GeneratedProperty(name = "sheetCheck15", type = String.class, javaDoc = "sheetCheck15"),
        @GeneratedProperty(name = "sheetCheck16", type = String.class, javaDoc = "sheetCheck16"),
        @GeneratedProperty(name = "sheetCheck17", type = String.class, javaDoc = "sheetCheck17"),
        @GeneratedProperty(name = "sheetCheck18", type = String.class, javaDoc = "sheetCheck18"),
        @GeneratedProperty(name = "sheetCheck19", type = String.class, javaDoc = "sheetCheck11"),
        @GeneratedProperty(name = "sheetCheck20", type = String.class, javaDoc = "sheetCheck20"),
        @GeneratedProperty(name = "sheetResult1", type = String.class, javaDoc = "sheetResult1"),
        @GeneratedProperty(name = "sheetResult2", type = String.class, javaDoc = "sheetResult2"),
        @GeneratedProperty(name = "sheetResult3", type = String.class, javaDoc = "sheetResult3"),
        @GeneratedProperty(name = "sheetResult4", type = String.class, javaDoc = "sheetResult4"),
        @GeneratedProperty(name = "sheetResult5", type = String.class, javaDoc = "sheetResult5"),
        @GeneratedProperty(name = "sheetResult6", type = String.class, javaDoc = "sheetResult6"),
        @GeneratedProperty(name = "sheetResult7", type = String.class, javaDoc = "sheetResult7"),
        @GeneratedProperty(name = "sheetResult8", type = String.class, javaDoc = "sheetResult8"),
        @GeneratedProperty(name = "sheetResult9", type = String.class, javaDoc = "sheetResult9"),
        @GeneratedProperty(name = "sheetResult10", type = String.class, javaDoc = "sheetResult10"),
        @GeneratedProperty(name = "sheetResult11", type = String.class, javaDoc = "sheetResult11"),
        @GeneratedProperty(name = "sheetResult12", type = String.class, javaDoc = "sheetResult12"),
        @GeneratedProperty(name = "sheetResult13", type = String.class, javaDoc = "sheetResult13"),
        @GeneratedProperty(name = "sheetResult14", type = String.class, javaDoc = "sheetResult14"),
        @GeneratedProperty(name = "sheetResult15", type = String.class, javaDoc = "sheetResult15"),
        @GeneratedProperty(name = "sheetResult16", type = String.class, javaDoc = "sheetResult16"),
        @GeneratedProperty(name = "sheetResult17", type = String.class, javaDoc = "sheetResult17"),
        @GeneratedProperty(name = "sheetResult18", type = String.class, javaDoc = "sheetResult18"),
        @GeneratedProperty(name = "sheetResult19", type = String.class, javaDoc = "sheetResult11"),
        @GeneratedProperty(name = "sheetResult20", type = String.class, javaDoc = "sheetResult20"),
        @GeneratedProperty(name = "assessSheetOid", type = String.class, javaDoc = "assessSheetOid"),
        @GeneratedProperty(name = "managerDeptCode", type = String.class, javaDoc = "managerDeptCode", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "managerDeptName", type = String.class, javaDoc = "managerDeptName", constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "rev", type = int.class) }, roleA = @GeneratedRole(name = "check", type = GateCheckSheet.class), roleB = @GeneratedRole(name = "assess", type = GateAssessResult.class, cardinality = Cardinality.ZERO_TO_ONE), tableProperties = @TableProperties(tableName = "KETAssessResultGateCheckLink"))
public class AssessResultGateCheckLink extends _AssessResultGateCheckLink {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @param check
     * @param assess
     * @return AssessResultGateCheckLink
     * @exception wt.util.WTException
     **/
    public static AssessResultGateCheckLink newAssessResultGateCheckLink(GateCheckSheet check, GateAssessResult assess) throws WTException {

	AssessResultGateCheckLink instance = new AssessResultGateCheckLink();
	instance.initialize(check, assess);
	return instance;
    }

}
