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
 * Try 조건표 Mold
 * <p>
 * Use the <code>newKETTryMold</code> static factory method(s), not the <code>KETTryMold</code> constructor, to construct instances of this
 * class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = KETTryCondition.class, properties = {
        @GeneratedProperty(name = "grade", type = String.class, javaDoc = "종류/Grade"),
        @GeneratedProperty(name = "color", type = String.class, javaDoc = "color"),
        @GeneratedProperty(name = "dryTemp", type = String.class, javaDoc = "건조온도"),
        @GeneratedProperty(name = "dryTime", type = String.class, javaDoc = "건조시간"),
        @GeneratedProperty(name = "moistureRate", type = String.class, javaDoc = "수분율"),
        @GeneratedProperty(name = "moldStrucEtc", type = String.class, javaDoc = "금형구조(기타)"),
        @GeneratedProperty(name = "moldBaseSizeWidth", type = String.class, javaDoc = "MoldBaseSize(가로)"),
        @GeneratedProperty(name = "moldBaseSizeLength", type = String.class, javaDoc = "MoldBaseSize(세로)"),
        @GeneratedProperty(name = "moldBaseSizeHeight", type = String.class, javaDoc = "MoldBaseSize(높이)"),
        @GeneratedProperty(name = "weight", type = String.class, javaDoc = "중량"),
        @GeneratedProperty(name = "cavityCount", type = String.class, javaDoc = "cavity수"),
        @GeneratedProperty(name = "mountThicknessEtc", type = String.class, javaDoc = "취부판두께(기타)"),
        @GeneratedProperty(name = "machineSpec", type = String.class, javaDoc = "기계사양"),
        @GeneratedProperty(name = "screwDiameter", type = String.class, javaDoc = "Screw직경"),
        @GeneratedProperty(name = "nozzleType", type = String.class, javaDoc = "노즐타입"),
        @GeneratedProperty(name = "oilTemp", type = String.class, javaDoc = "유온"),
        @GeneratedProperty(name = "tone", type = String.class, javaDoc = "톤수"),
        @GeneratedProperty(name = "tiebarInterval", type = String.class, javaDoc = "타이바 간격"),
        @GeneratedProperty(name = "cylinderTempNH", type = String.class, javaDoc = "실린더온도(NH)"),
        @GeneratedProperty(name = "cylinderTempN1", type = String.class, javaDoc = "실린더온도(N1)"),
        @GeneratedProperty(name = "cylinderTempN2", type = String.class, javaDoc = "실린더온도(N2)"),
        @GeneratedProperty(name = "cylinderTempN3", type = String.class, javaDoc = "실린더온도(N3)"),
        @GeneratedProperty(name = "cylinderTempN4", type = String.class, javaDoc = "실린더온도(N4)"),
        @GeneratedProperty(name = "coolWaterTempHigh", type = String.class, javaDoc = "냉각수온도(상)"),
        @GeneratedProperty(name = "coolWaterTempLow", type = String.class, javaDoc = "냉각수온도(하)"),
        @GeneratedProperty(name = "injectPress1", type = String.class, javaDoc = "사출압력(1단)"),
        @GeneratedProperty(name = "injectPress2", type = String.class, javaDoc = "사출압력(2단)"),
        @GeneratedProperty(name = "injectPress3", type = String.class, javaDoc = "사출압력(3단)"),
        @GeneratedProperty(name = "injectPress4", type = String.class, javaDoc = "사출압력(4단)"),
        @GeneratedProperty(name = "injectPress5", type = String.class, javaDoc = "사출압력(5단)"),
        @GeneratedProperty(name = "injectSpeed1", type = String.class, javaDoc = "사출속도(1단)"),
        @GeneratedProperty(name = "injectSpeed2", type = String.class, javaDoc = "사출속도(2단)"),
        @GeneratedProperty(name = "injectSpeed3", type = String.class, javaDoc = "사출속도(3단)"),
        @GeneratedProperty(name = "injectSpeed4", type = String.class, javaDoc = "사출속도4단)"),
        @GeneratedProperty(name = "injectSpeed5", type = String.class, javaDoc = "사출속도(5단)"),
        @GeneratedProperty(name = "shortTransition1", type = String.class, javaDoc = "다단거리전환(1단)"),
        @GeneratedProperty(name = "shortTransition2", type = String.class, javaDoc = "다단거리전환(2단)"),
        @GeneratedProperty(name = "shortTransition3", type = String.class, javaDoc = "다단거리전환(3단)"),
        @GeneratedProperty(name = "shortTransition4", type = String.class, javaDoc = "다단거리전환(4단)"),
        @GeneratedProperty(name = "shortTransition5", type = String.class, javaDoc = "다단거리전환(5단)"),
        @GeneratedProperty(name = "holdPress1", type = String.class, javaDoc = "보압(1단)"),
        @GeneratedProperty(name = "holdPress2", type = String.class, javaDoc = "보압(2단)"),
        @GeneratedProperty(name = "holdPress3", type = String.class, javaDoc = "보압(3단)"),
        @GeneratedProperty(name = "holdPressSpeed1", type = String.class, javaDoc = "보압속도(1단)"),
        @GeneratedProperty(name = "holdPressSpeed2", type = String.class, javaDoc = "보압속도(2단)"),
        @GeneratedProperty(name = "holdPressSpeed3", type = String.class, javaDoc = "보압속도(3단)"),
        @GeneratedProperty(name = "moldOpenSpeed1", type = String.class, javaDoc = "헝개속도(1단)"),
        @GeneratedProperty(name = "moldOpenSpeed2", type = String.class, javaDoc = "헝개속도(2단)"),
        @GeneratedProperty(name = "moldOpenSpeed3", type = String.class, javaDoc = "헝개속도(3단)"),
        @GeneratedProperty(name = "moldOpenDist1", type = String.class, javaDoc = "헝개거리(1단)"),
        @GeneratedProperty(name = "moldOpenDist2", type = String.class, javaDoc = "헝개거리(2단)"),
        @GeneratedProperty(name = "moldOpenDist3", type = String.class, javaDoc = "헝개거리(3단)"),
        @GeneratedProperty(name = "stroke", type = String.class, javaDoc = "stroke"),
        @GeneratedProperty(name = "speed", type = String.class, javaDoc = "속도"),
        @GeneratedProperty(name = "pressures", type = String.class, javaDoc = "압력"),
        @GeneratedProperty(name = "pressCount", type = String.class, javaDoc = "횟수"),
        @GeneratedProperty(name = "delayTime", type = String.class, javaDoc = "전진지연시간"),
        @GeneratedProperty(name = "cycleTime", type = String.class, javaDoc = "Cycle time"),
        @GeneratedProperty(name = "pressTime", type = String.class, javaDoc = "사출시간"),
        @GeneratedProperty(name = "coolingTime", type = String.class, javaDoc = "냉각시간"),
        @GeneratedProperty(name = "holdPressTime", type = String.class, javaDoc = "보압시간"),
        @GeneratedProperty(name = "moldCloseTime", type = String.class, javaDoc = "형폐시간"),
        @GeneratedProperty(name = "shotWeight", type = String.class, javaDoc = "Shot 중량"),
        @GeneratedProperty(name = "sprue", type = String.class, javaDoc = "Spure"),
        @GeneratedProperty(name = "cvWeight", type = String.class, javaDoc = "C/V 중량"),
        @GeneratedProperty(name = "fixedSideTempInput", type = String.class, javaDoc = "고정측 입력값"),
        @GeneratedProperty(name = "fixedSideTemp", type = String.class, javaDoc = "고정측 실제값"),
        @GeneratedProperty(name = "movingSideTempInput", type = String.class, javaDoc = "이동측 입력값"),
        @GeneratedProperty(name = "movingSideTemp", type = String.class, javaDoc = "이동측 실제값"),
        @GeneratedProperty(name = "backPress", type = String.class, javaDoc = "배압"),
        @GeneratedProperty(name = "mensuration", type = String.class, javaDoc = "계량"),
        @GeneratedProperty(name = "lowPressShape", type = String.class, javaDoc = "저압형체"),
        @GeneratedProperty(name = "holdPressTurnPoint", type = String.class, javaDoc = "보압전환점"),
        @GeneratedProperty(name = "mensurationTime", type = String.class, javaDoc = "계량시간"),
        @GeneratedProperty(name = "highPressShapeSection", type = String.class, javaDoc = "고압형체구간"),
        @GeneratedProperty(name = "cushionAmount", type = String.class, javaDoc = "쿠션량"),
        @GeneratedProperty(name = "spillResistentCfg", type = String.class, javaDoc = "흘림방지설정"),
        @GeneratedProperty(name = "shotCount", type = String.class, javaDoc = "Shot 수"),
        @GeneratedProperty(name = "mensurationDist", type = String.class, javaDoc = "계량거리"),
        @GeneratedProperty(name = "spillResistantSpeed", type = String.class, javaDoc = "흘림방지속도"),
        @GeneratedProperty(name = "maxPress", type = String.class, javaDoc = "최대충진압"),
        @GeneratedProperty(name = "insertWeight", type = String.class, javaDoc = "Insert중량"),
        @GeneratedProperty(name = "cavityBigo", type = String.class) }, foreignKeys = {
        @GeneratedForeignKey(name = "MaterialLink", foreignKeyRole = @ForeignKeyRole(name = "material", type = e3ps.project.material.MoldMaterial.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "GateLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "gateType", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "MountingThicknessLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "mountThickness", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "TempSensorLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "temperatureSensor", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true), javaDoc = "온도센서"), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "InjectPressUnitLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "injectPressUnit", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "HoldPressUnitLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "packingPressUnit", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "LowPressShapeUnitLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "lowPressShapeUnit", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "BackPressTryMoldLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "backPressUnit", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(name = "MoldStrucTryMoldLink", myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "moldStruc", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theKETTryMold", cardinality = Cardinality.ONE)) })
public class KETTryMold extends _KETTryMold {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETTryMold
     * @exception wt.util.WTException
     **/
    public static KETTryMold newKETTryMold() throws WTException {

	KETTryMold instance = new KETTryMold();
	instance.initialize();
	return instance;
    }

}
