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

package ext.ket.project.trycondition.entity;

import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;

/**
 * Try 조건표 Press
 * <p>
 * Use the <code>newKETTryPress</code> static factory method(s), not the <code>KETTryPress</code> constructor, to construct instances of this class. Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = KETTryCondition.class, properties = { @GeneratedProperty(name = "thickness", type = String.class, javaDoc = "두께/폭"),
        @GeneratedProperty(name = "plating", type = String.class, javaDoc = "도금"), @GeneratedProperty(name = "moldStrucEtc", type = String.class, javaDoc = "금형구조(기타)"),
        @GeneratedProperty(name = "productMethodEtc", type = String.class, javaDoc = "제품방식(기타)"), @GeneratedProperty(name = "moldSizeWidth", type = String.class, javaDoc = "금형Size가로"),
        @GeneratedProperty(name = "moldSizeLength", type = String.class, javaDoc = "금형Size(길이)"), @GeneratedProperty(name = "moldSizeHeight", type = String.class, javaDoc = "금형Size(높이)"),
        @GeneratedProperty(name = "moldWeightTop", type = String.class, javaDoc = "금형중량(상형)"), @GeneratedProperty(name = "moldWeightLower", type = String.class, javaDoc = "금형중량(하형)"),
        @GeneratedProperty(name = "dieHeight", type = String.class, javaDoc = "Die Height"), @GeneratedProperty(name = "pitch", type = String.class, javaDoc = "Pitch"),
        @GeneratedProperty(name = "punchingSpeed", type = String.class, javaDoc = "타발속도"), @GeneratedProperty(name = "punchingCount", type = String.class, javaDoc = "타발수량"),
        @GeneratedProperty(name = "productOutputCol", type = String.class, javaDoc = "제품 취출수(열)"), @GeneratedProperty(name = "productOutputPitch", type = String.class, javaDoc = "제품취출수(피치)"),
        @GeneratedProperty(name = "saftySensor", type = String.class, javaDoc = "안전센서(중복선택)"), @GeneratedProperty(name = "press", type = String.class, javaDoc = "Press"),
        @GeneratedProperty(name = "tone", type = String.class, javaDoc = "톤수"), @GeneratedProperty(name = "stroke", type = String.class, javaDoc = "설비정보(Stroke)"),
        @GeneratedProperty(name = "spm", type = String.class, javaDoc = "설비정보(SPM)"), @GeneratedProperty(name = "bolsterWidth", type = String.class, javaDoc = "Bolster정보(가로)"),
        @GeneratedProperty(name = "bolsterHeight", type = String.class, javaDoc = "Bolster정보(세로)"),
        @GeneratedProperty(name = "feederMachine", type = String.class, javaDoc = "피더기(중복선택)", constraints = @PropertyConstraints(upperLimit = 2000)),
        @GeneratedProperty(name = "feederMachineEtc", type = String.class, javaDoc = "피더기(기타)"), @GeneratedProperty(name = "testResult", type = String.class, javaDoc = "검사결과") }, foreignKeys = {
        @GeneratedForeignKey(name = "MoldStrucTryPressLink", foreignKeyRole = @ForeignKeyRole(name = "moldStruc", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "tryHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ProductMethodLink", foreignKeyRole = @ForeignKeyRole(name = "productMethod", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryPress", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "ScrapProcessLink", foreignKeyRole = @ForeignKeyRole(name = "scrapProcess", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryPress", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "MoldMaterialLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "material", type = e3ps.project.material.MoldMaterial.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "tryPress", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "PunchingOilLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "punchingOil", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryPress", cardinality = Cardinality.ONE)) })
public class KETTryPress extends _KETTryPress {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETTryPress
     * @exception wt.util.WTException
     **/
    public static KETTryPress newKETTryPress() throws WTException {

	KETTryPress instance = new KETTryPress();
	instance.initialize();
	return instance;
    }

}
