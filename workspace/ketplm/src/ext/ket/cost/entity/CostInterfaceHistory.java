/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import java.sql.Timestamp;

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
 * 개발원가 ERP 인터페이스 이력 (제품기준)
 * <p>
 * Use the <code>newCostInterfaceHistory</code> static factory method(s), not the <code>CostInterfaceHistory</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(interfaces = { OwnPersistable.class }, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "pjtNo", type = String.class, javaDoc = "프로젝트번호"),
        @GeneratedProperty(name = "pjtType", type = String.class, javaDoc = "제품,검토"),
        @GeneratedProperty(name = "drStep", type = String.class, javaDoc = "DR단계"),
        @GeneratedProperty(name = "realPartNo", type = String.class, javaDoc = "실제품번"),
        @GeneratedProperty(name = "partNo", type = String.class, javaDoc = "가품번"),
        @GeneratedProperty(name = "version", type = String.class, javaDoc = "version + subVersion"),
        @GeneratedProperty(name = "materialCost", type = String.class, javaDoc = "재료비", initialValue = "\"0\""),
        @GeneratedProperty(name = "laborCost", type = String.class, javaDoc = "직접인건비", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectLaborCost", type = String.class, javaDoc = "간접인건비", initialValue = "\"0\""),
        @GeneratedProperty(name = "facReducePrice", type = String.class, javaDoc = "설비감가비", initialValue = "\"0\""),
        @GeneratedProperty(name = "directCost", type = String.class, javaDoc = "직접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "inDirectCost", type = String.class, javaDoc = "간접경비", initialValue = "\"0\""),
        @GeneratedProperty(name = "moldReducePrice", type = String.class, initialValue = "\"0\"", javaDoc = "금형감가비"),
        @GeneratedProperty(name = "outUnitCost", type = String.class, initialValue = "\"0\"", javaDoc = "외주가공비"),
        @GeneratedProperty(name = "etcCost", type = String.class, initialValue = "\"0\"", javaDoc = "기타원가"),
        @GeneratedProperty(name = "salesManageCost", type = String.class, initialValue = "\"0\"", javaDoc = "판관비"),
        @GeneratedProperty(name = "scrapSalesCost", type = String.class, javaDoc = "스크랩매출", initialValue = "\"0\""),
        @GeneratedProperty(name = "salesTargetCostTotal", type = String.class, javaDoc = "판매목표가"),
        @GeneratedProperty(name = "productCostTotal", type = String.class, javaDoc = "총원가(컨버팅)"),
        @GeneratedProperty(name = "orgProductCostTotal", type = String.class, javaDoc = "총원가(PLM)"),
        @GeneratedProperty(name = "costCalcDate", type = String.class, javaDoc = "원가계산일"),
        @GeneratedProperty(name = "sopSyear", type = String.class, javaDoc = "SOP시작년도"),
        @GeneratedProperty(name = "sopEyear", type = String.class, javaDoc = "SOP종료년도"),
        @GeneratedProperty(name = "salesQty1", type = String.class, javaDoc = "월평균판매수량1"),
        @GeneratedProperty(name = "salesQty2", type = String.class, javaDoc = "월평균판매수량2"),
        @GeneratedProperty(name = "salesQty3", type = String.class, javaDoc = "월평균판매수량3"),
        @GeneratedProperty(name = "salesQty4", type = String.class, javaDoc = "월평균판매수량4"),
        @GeneratedProperty(name = "salesQty5", type = String.class, javaDoc = "월평균판매수량5"),
        @GeneratedProperty(name = "salesQty6", type = String.class, javaDoc = "월평균판매수량6"),
        @GeneratedProperty(name = "salesQty7", type = String.class, javaDoc = "월평균판매수량7"),
        @GeneratedProperty(name = "salesQty8", type = String.class, javaDoc = "월평균판매수량8"),
        @GeneratedProperty(name = "salesQty9", type = String.class, javaDoc = "월평균판매수량9"),
        @GeneratedProperty(name = "salesQty10", type = String.class, javaDoc = "월평균판매수량10"),
        @GeneratedProperty(name = "salesQty11", type = String.class, javaDoc = "월평균판매수량11"),
        @GeneratedProperty(name = "salesQty12", type = String.class, javaDoc = "월평균판매수량12"),
        @GeneratedProperty(name = "salesQty13", type = String.class, javaDoc = "월평균판매수량13"),
        @GeneratedProperty(name = "salesQty14", type = String.class, javaDoc = "월평균판매수량14"),
        @GeneratedProperty(name = "salesQty15", type = String.class, javaDoc = "월평균판매수량15"),
        @GeneratedProperty(name = "salesQty16", type = String.class, javaDoc = "월평균판매수량16"),
        @GeneratedProperty(name = "salesQty17", type = String.class, javaDoc = "월평균판매수량17"),
        @GeneratedProperty(name = "salesQty18", type = String.class, javaDoc = "월평균판매수량18"),
        @GeneratedProperty(name = "salesQty19", type = String.class, javaDoc = "월평균판매수량19"),
        @GeneratedProperty(name = "salesQty20", type = String.class, javaDoc = "월평균판매수량20"),
        @GeneratedProperty(name = "lastest", type = boolean.class, initialValue = "false"),
        @GeneratedProperty(name = "bomTreeCheck", type = boolean.class, initialValue = "false", javaDoc = "자부품 생성여부"),
        @GeneratedProperty(name = "delFlag", type = String.class, javaDoc = "ERP삭제플래그"),
        @GeneratedProperty(name = "transferDate", type = Timestamp.class, javaDoc = "ERP전송일"),
        @GeneratedProperty(name = "transferFlag", type = String.class, javaDoc = "ERP전송여부"),
        @GeneratedProperty(name = "transferMsg", type = String.class, javaDoc = "ERP전송로그", constraints = @PropertyConstraints(upperLimit = 4000)) }, foreignKeys = {
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "task", type = e3ps.project.E3PSTask.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "costPart", type = ext.ket.cost.entity.CostPart.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "project", type = e3ps.project.E3PSProjectMaster.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceHistory", cardinality = Cardinality.ONE)),
        @GeneratedForeignKey(myRoleIsRoleA = false, foreignKeyRole = @ForeignKeyRole(name = "partList", type = ext.ket.cost.entity.PartList.class, constraints = @PropertyConstraints(required = true)), myRole = @MyRole(name = "theCostInterfaceHistory", cardinality = Cardinality.ONE)) })
public class CostInterfaceHistory extends _CostInterfaceHistory {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return CostInterfaceHistory
     * @exception wt.util.WTException
     **/
    public static CostInterfaceHistory newCostInterfaceHistory() throws WTException {

	CostInterfaceHistory instance = new CostInterfaceHistory();
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
