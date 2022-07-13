/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.Tree;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 * 감가비 정보(투자비)
 * <p>
 * Use the <code>newCostInvestInfo</code> static factory method(s), not
 * the <code>CostInvestInfo</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={Tree.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="investType", type=String.class,
      javaDoc="투자비구분(금형 : 설비 : 기타)"),
   @GeneratedProperty(name="costType", type=String.class,
      javaDoc="투자비 신규/양산 구분"),
   @GeneratedProperty(name="reduceCode", type=String.class,
      javaDoc="감가비 코드"),
   @GeneratedProperty(name="investCost", type=String.class,
      javaDoc="투자비(금형,설비,기타)"),
   @GeneratedProperty(name="investPay", type=String.class,
      javaDoc="지급액(금형,설비,기타)"),
   @GeneratedProperty(name="investReduceCost", type=String.class,
      javaDoc="감가액(금형,설비,기타)"),
   @GeneratedProperty(name="capaRate", type=String.class,
      javaDoc="생산Capa (금형,설비,기타)"),
   @GeneratedProperty(name="investUnit", type=String.class,
      javaDoc="벌수"),
   @GeneratedProperty(name="salesQty", type=String.class,
      javaDoc="판매수량(금형,설비,기타)"),
   @GeneratedProperty(name="massQty", type=String.class,
      javaDoc="양산수량(금형,설비,기타)"),
   @GeneratedProperty(name="addQty", type=String.class,
      javaDoc="추가수량(금형,설비,기타)"),
   @GeneratedProperty(name="salesReduceQty", type=String.class,
      javaDoc="판매감가수량(금형,설비,기타)"),
   @GeneratedProperty(name="normalReduceQty", type=String.class,
      javaDoc="일반감가수량(금형,설비,기타)"),
   @GeneratedProperty(name="eachReduceCost", type=String.class,
      javaDoc="개별감가비(금형,설비,기타)"),
   @GeneratedProperty(name="investNote", type=String.class,
      javaDoc="감가비 비고"),
   @GeneratedProperty(name="itemName", type=String.class,
      javaDoc="항목(기타 투자비에서만 사용)"),
   @GeneratedProperty(name="nFactory", type=String.class,
      javaDoc="신규제작처"),
   @GeneratedProperty(name="mFactory", type=String.class,
      javaDoc="양산제작처"),
   @GeneratedProperty(name="etcInvestOid", type=String.class,
      javaDoc="기타투자비OID"),
   @GeneratedProperty(name="workUseHour",  type=String.class, initialValue="\"20\"",
      javaDoc="조업도_시간"),
   @GeneratedProperty(name="workUseDay",  type=String.class, initialValue="\"260\"",
      javaDoc="조업도_일"),
   @GeneratedProperty(name="workUseYear",  type=String.class, initialValue="\"6\"",
      javaDoc="조업도_적용년수"),
   @GeneratedProperty(name="machineDpcCostExpr"         ,type=String.class, javaDoc="기계감가" ,initialValue="\"0\""),
   @GeneratedProperty(name="facReduceCtSpm", type=String.class, javaDoc="범용감가CT(SPM)", initialValue="\"0\""),
   @GeneratedProperty(name="facReduceOutputExpr", type=String.class, javaDoc="범용감가생산량", initialValue="\"0\""),
   @GeneratedProperty(name="machineReduceCost"          ,type=String.class, javaDoc="기계감가(표준)" ,initialValue="\"0\""),
   })
public class CostInvestInfo extends _CostInvestInfo {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostInvestInfo
    * @exception wt.util.WTException
    **/
   public static CostInvestInfo newCostInvestInfo()
            throws WTException {

      CostInvestInfo instance = new CostInvestInfo();
      instance.initialize();
      return instance;
   }

   /**
    * Supports initialization, following construction of an instance.  Invoked
    * by "new" factory having the same signature.
    *
    * @exception wt.util.WTException
    **/
   protected void initialize()
            throws WTException {

   }

   /**
    * Gets the value of the attribute: IDENTITY.
    * Supplies the identity of the object for business purposes.  The identity
    * is composed of name, number or possibly other attributes.  The identity
    * does not include the type of the object.
    *
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated Replaced by IdentityFactory.getDispayIdentifier(object)
    * to return a localizable equivalent of getIdentity().  To return a
    * localizable value which includes the object type, use IdentityFactory.getDisplayIdentity(object).
    * Other alternatives are ((WTObject)obj).getDisplayIdentifier() and
    * ((WTObject)obj).getDisplayIdentity().
    *
    * @return    String
    **/
   public String getIdentity() {

      return null;
   }

   /**
    * Gets the value of the attribute: TYPE.
    * Identifies the type of the object for business purposes.  This is
    * typically the class name of the object but may be derived from some
    * other attribute of the object.
    *
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated Replaced by IdentityFactory.getDispayType(object) to return
    * a localizable equivalent of getType().  Another alternative is ((WTObject)obj).getDisplayType().
    *
    * @return    String
    **/
   public String getType() {

      return null;
   }

   @Override
   public void checkAttributes()
            throws InvalidAttributeException {

   }

}
