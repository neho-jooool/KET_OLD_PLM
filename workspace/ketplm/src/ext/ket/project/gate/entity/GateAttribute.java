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

import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsObjectMappable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 
 * <p>
 * Use the <code>newGateAttribute</code> static factory method(s), not the <code>GateAttribute</code> constructor, to construct instances of
 * this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsObjectMappable(serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "select1", type = boolean.class, initialValue = "false", javaDoc = "선택여부1"),
        @GeneratedProperty(name = "select2", type = boolean.class, initialValue = "false", javaDoc = "선택여부2"),
        @GeneratedProperty(name = "select3", type = boolean.class, initialValue = "false", javaDoc = "선택여부3"),
        @GeneratedProperty(name = "select4", type = boolean.class, initialValue = "false", javaDoc = "선택여부4"),
        @GeneratedProperty(name = "select5", type = boolean.class, initialValue = "false", javaDoc = "선택여부5"),
        @GeneratedProperty(name = "select6", type = boolean.class, initialValue = "false", javaDoc = "선택여부6"),
        @GeneratedProperty(name = "select7", type = boolean.class, initialValue = "false", javaDoc = "선택여부7"),
        @GeneratedProperty(name = "select8", type = boolean.class, initialValue = "false", javaDoc = "선택여부8"),
        @GeneratedProperty(name = "select9", type = boolean.class, initialValue = "false", javaDoc = "선택여부9"),
        @GeneratedProperty(name = "select10", type = boolean.class, initialValue = "false", javaDoc = "선택여부10"),
        @GeneratedProperty(name = "select11", type = boolean.class, initialValue = "false", javaDoc = "선택여부11"),
        @GeneratedProperty(name = "select12", type = boolean.class, initialValue = "false", javaDoc = "선택여부12"),
        @GeneratedProperty(name = "select13", type = boolean.class, initialValue = "false", javaDoc = "선택여부13"),
        @GeneratedProperty(name = "select14", type = boolean.class, initialValue = "false", javaDoc = "선택여부14"),
        @GeneratedProperty(name = "select15", type = boolean.class, initialValue = "false", javaDoc = "선택여부15"),
        @GeneratedProperty(name = "select16", type = boolean.class, initialValue = "false", javaDoc = "선택여부16"),
        @GeneratedProperty(name = "select17", type = boolean.class, initialValue = "false", javaDoc = "선택여부17"),
        @GeneratedProperty(name = "select18", type = boolean.class, initialValue = "false", javaDoc = "선택여부18"),
        @GeneratedProperty(name = "select19", type = boolean.class, initialValue = "false", javaDoc = "선택여부19"),
        @GeneratedProperty(name = "select20", type = boolean.class, initialValue = "false", javaDoc = "선택여부20"),
        @GeneratedProperty(name = "target1", type = String.class, javaDoc = "목표1"),
        @GeneratedProperty(name = "target2", type = String.class, javaDoc = "목표2"),
        @GeneratedProperty(name = "target3", type = String.class, javaDoc = "목표3"),
        @GeneratedProperty(name = "target4", type = String.class, javaDoc = "목표4"),
        @GeneratedProperty(name = "target5", type = String.class, javaDoc = "목표5"),
        @GeneratedProperty(name = "target6", type = String.class, javaDoc = "목표6"),
        @GeneratedProperty(name = "target7", type = String.class, javaDoc = "목표7"),
        @GeneratedProperty(name = "target8", type = String.class, javaDoc = "목표8"),
        @GeneratedProperty(name = "target9", type = String.class, javaDoc = "목표9"),
        @GeneratedProperty(name = "target10", type = String.class, javaDoc = "목표10"),
        @GeneratedProperty(name = "target11", type = String.class, javaDoc = "목표11"),
        @GeneratedProperty(name = "target12", type = String.class, javaDoc = "목표12"),
        @GeneratedProperty(name = "target13", type = String.class, javaDoc = "목표13"),
        @GeneratedProperty(name = "target14", type = String.class, javaDoc = "목표14"),
        @GeneratedProperty(name = "target15", type = String.class, javaDoc = "목표15"),
        @GeneratedProperty(name = "target16", type = String.class, javaDoc = "목표16"),
        @GeneratedProperty(name = "target17", type = String.class, javaDoc = "목표17"),
        @GeneratedProperty(name = "target18", type = String.class, javaDoc = "목표18"),
        @GeneratedProperty(name = "target19", type = String.class, javaDoc = "목표19"),
        @GeneratedProperty(name = "target20", type = String.class, javaDoc = "목표20"),
        @GeneratedProperty(name = "result1", type = String.class, javaDoc = "실적1"),
        @GeneratedProperty(name = "result2", type = String.class, javaDoc = "실적2"),
        @GeneratedProperty(name = "result3", type = String.class, javaDoc = "실적3"),
        @GeneratedProperty(name = "result4", type = String.class, javaDoc = "실적4"),
        @GeneratedProperty(name = "result5", type = String.class, javaDoc = "실적5"),
        @GeneratedProperty(name = "result6", type = String.class, javaDoc = "실적6"),
        @GeneratedProperty(name = "result7", type = String.class, javaDoc = "실적7"),
        @GeneratedProperty(name = "result8", type = String.class, javaDoc = "실적8"),
        @GeneratedProperty(name = "result9", type = String.class, javaDoc = "실적9"),
        @GeneratedProperty(name = "result10", type = String.class, javaDoc = "실적10"),
        @GeneratedProperty(name = "result11", type = String.class, javaDoc = "실적11"),
        @GeneratedProperty(name = "result12", type = String.class, javaDoc = "실적12"),
        @GeneratedProperty(name = "result13", type = String.class, javaDoc = "실적13"),
        @GeneratedProperty(name = "result14", type = String.class, javaDoc = "실적14"),
        @GeneratedProperty(name = "result15", type = String.class, javaDoc = "실적15"),
        @GeneratedProperty(name = "result16", type = String.class, javaDoc = "실적16"),
        @GeneratedProperty(name = "result17", type = String.class, javaDoc = "실적17"),
        @GeneratedProperty(name = "result18", type = String.class, javaDoc = "실적18"),
        @GeneratedProperty(name = "result19", type = String.class, javaDoc = "실적19"),
        @GeneratedProperty(name = "result20", type = String.class, javaDoc = "실적20"),
        @GeneratedProperty(name = "gateName1", type = String.class, javaDoc = "gateName1"),
        @GeneratedProperty(name = "gateName2", type = String.class, javaDoc = "gateName2"),
        @GeneratedProperty(name = "gateName3", type = String.class, javaDoc = "gateName3"),
        @GeneratedProperty(name = "gateName4", type = String.class, javaDoc = "gateName4"),
        @GeneratedProperty(name = "gateName5", type = String.class, javaDoc = "gateName5"),
        @GeneratedProperty(name = "gateName6", type = String.class, javaDoc = "gateName6"),
        @GeneratedProperty(name = "gateName7", type = String.class, javaDoc = "gateName7"),
        @GeneratedProperty(name = "gateName8", type = String.class, javaDoc = "gateName8"),
        @GeneratedProperty(name = "gateName9", type = String.class, javaDoc = "gateName9"),
        @GeneratedProperty(name = "gateName10", type = String.class, javaDoc = "gateName10"),
        @GeneratedProperty(name = "gateName11", type = String.class, javaDoc = "gateName11"),
        @GeneratedProperty(name = "gateName12", type = String.class, javaDoc = "gateName12"),
        @GeneratedProperty(name = "gateName13", type = String.class, javaDoc = "gateName13"),
        @GeneratedProperty(name = "gateName14", type = String.class, javaDoc = "gateName14"),
        @GeneratedProperty(name = "gateName15", type = String.class, javaDoc = "gateName15"),
        @GeneratedProperty(name = "gateName16", type = String.class, javaDoc = "gateName16"),
        @GeneratedProperty(name = "gateName17", type = String.class, javaDoc = "gateName17"),
        @GeneratedProperty(name = "gateName18", type = String.class, javaDoc = "gateName18"),
        @GeneratedProperty(name = "gateName19", type = String.class, javaDoc = "gateName19"),
        @GeneratedProperty(name = "gateName20", type = String.class, javaDoc = "gateName20") }
// , tableProperties=@TableProperties(tableName="KETGateAttribute")
)
public class GateAttribute extends _GateAttribute {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return GateAttribute
     * @exception wt.util.WTException
     **/
    public static GateAttribute newGateAttribute() throws WTException {

	GateAttribute instance = new GateAttribute();
	instance.initialize();
	return instance;
    }

    /**
     * Supports initialization, following construction of an instance. Invoked by "new" factory having the same signature.
     * 
     * @exception wt.util.WTException
     **/
    protected void initialize() throws WTException {

    }

}
