/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package ext.ket.cost.entity;

import wt.fc.InvalidAttributeException;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;

/**
 * 
 * <p>
 * Use the <code>newCostPartTypeCodeMaster</code> static factory method(s), not the <code>CostPartTypeCodeMaster</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(interfaces = { OwnPersistable.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "productLossRate", type = String.class, javaDoc = "생산Loss율"),
        @GeneratedProperty(name = "efficientRate", type = String.class, javaDoc = "생산효율"),
        @GeneratedProperty(name = "addManHourRate", type = String.class, javaDoc = "추가공수효율"),
        @GeneratedProperty(name = "laborCostRate", type = String.class, javaDoc = "표준임율"),
        @GeneratedProperty(name = "laborCostClimbRate", type = String.class, javaDoc = "표준임율상승율"),
        @GeneratedProperty(name = "laborCostYear", type = String.class, javaDoc = "표준임율년도"),
        @GeneratedProperty(name = "unitManage", type = String.class, javaDoc = "관리대수"),
        @GeneratedProperty(name = "indirectCostRate", type = String.class, javaDoc = "간접경비율"),
        @GeneratedProperty(name = "moldMaintenance", type = String.class, javaDoc = "금형유지비"),
        @GeneratedProperty(name = "tabaryu", type = String.class, javaDoc = "타발유"),
        @GeneratedProperty(name = "elecCost", type = String.class, javaDoc = "표준전력비"),
        @GeneratedProperty(name = "elecCostClimbRate", type = String.class, javaDoc = "표준전력비상승율"),
        @GeneratedProperty(name = "elecCostYear", type = String.class, javaDoc = "표준전력비년도"),
        @GeneratedProperty(name = "machineDpcCost", type = String.class, javaDoc = "기계감가(사출,프레스)"),
        @GeneratedProperty(name = "assyLossRate", type = String.class, javaDoc = "조립Loss율"),
        @GeneratedProperty(name = "mtrManageRate", type = String.class, javaDoc = "재료관리비율"),
        @GeneratedProperty(name = "coManageRate", type = String.class, javaDoc = "일반관리비율"),
        @GeneratedProperty(name = "rnd", type = String.class, javaDoc = "R&D"),
        @GeneratedProperty(name = "inDirectCost", type = String.class, javaDoc = "간접경비비용"),
        @GeneratedProperty(name = "coManageCost", type = String.class, javaDoc = "일반관리비용"),
        @GeneratedProperty(name = "moldFromVal", type = String.class, initialValue = "\"0\"", javaDoc = "금형유지비설정값From"),
        @GeneratedProperty(name = "moldFromSign", type = String.class, javaDoc = "금형유지비부등호From"),
        @GeneratedProperty(name = "moldToVal", type = String.class, initialValue = "\"0\"", javaDoc = "금형유지비설정값To"),
        @GeneratedProperty(name = "moldToSign", type = String.class, javaDoc = "금형유지비부등호To"),
        @GeneratedProperty(name = "tonFromVal", type = String.class, initialValue = "\"0\"", javaDoc = "설비Ton설정값From"),
        @GeneratedProperty(name = "tonFromSign", type = String.class, javaDoc = "설비Ton부등호From"),
        @GeneratedProperty(name = "tonToVal", type = String.class, initialValue = "\"0\"", javaDoc = "설비Ton설정값To"),
        @GeneratedProperty(name = "tonToSign", type = String.class, javaDoc = "설비Ton부등호To"),
        @GeneratedProperty(name = "quantityMaxFromVal", type = String.class, initialValue = "\"0\"", javaDoc = "부품별 max 물량 설정값From"),
        @GeneratedProperty(name = "quantityMaxFromSign", type = String.class, javaDoc = "부품별 max 물량 부등호From"),
        @GeneratedProperty(name = "quantityMaxToVal", type = String.class, initialValue = "\"0\"", javaDoc = "부품별 max 물량 설정값To"),
        @GeneratedProperty(name = "quantityMaxToSign", type = String.class, javaDoc = "부품별 max 물량 부등호To"),
        @GeneratedProperty(name = "sortLocation", type = Integer.class),
        @GeneratedProperty(name = "buyBackIndirectCostRate", type = String.class, javaDoc = "BuyBack간접경비율(본사)", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectLabourRate", type = String.class, javaDoc = "간접인건비비율(노무비)", initialValue = "\"0\""),
        @GeneratedProperty(name = "indirectRndRate", type = String.class, javaDoc = "간접인건비비율(R&D)", initialValue = "\"0\""), }, foreignKeys = {
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "costpartType", type = ext.ket.cost.entity.CostPartType.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostPartTypeCodeMaster", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "manufactureCode", type = e3ps.common.code.NumberCode.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostPartTypeCodeMaster", cardinality = Cardinality.ONE)) })
public class CostPartTypeCodeMaster extends _CostPartTypeCodeMaster {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return CostPartTypeCodeMaster
     * @exception wt.util.WTException
     **/
    public static CostPartTypeCodeMaster newCostPartTypeCodeMaster() throws WTException {

	CostPartTypeCodeMaster instance = new CostPartTypeCodeMaster();
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

    /**
     * Gets the value of the attribute: IDENTITY. Supplies the identity of the object for business purposes. The identity is composed of
     * name, number or possibly other attributes. The identity does not include the type of the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object) to return a localizable equivalent of getIdentity(). To return a
     *             localizable value which includes the object type, use IdentityFactory.getDisplayIdentity(object). Other alternatives are
     *             ((WTObject)obj).getDisplayIdentifier() and ((WTObject)obj).getDisplayIdentity().
     * 
     * @return String
     **/
    public String getIdentity() {

	return null;
    }

    /**
     * Gets the value of the attribute: TYPE. Identifies the type of the object for business purposes. This is typically the class name of
     * the object but may be derived from some other attribute of the object.
     * 
     * 
     * <BR>
     * <BR>
     * <B>Supported API: </B>false
     * 
     * @deprecated Replaced by IdentityFactory.getDispayType(object) to return a localizable equivalent of getType(). Another alternative is
     *             ((WTObject)obj).getDisplayType().
     * 
     * @return String
     **/
    public String getType() {

	return null;
    }

    @Override
    public void checkAttributes() throws InvalidAttributeException {

    }

}
