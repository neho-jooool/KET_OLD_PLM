/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.cost.entity;

import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 * 매출액 및 영업이익
 * <p>
 * Use the <code>newCostAnalysis</code> static factory method(s), not the
 * <code>CostAnalysis</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="year", type=Integer.class,
      javaDoc="년차"),
   @GeneratedProperty(name="appointSales", type=String.class, initialValue="\"0\"",
      javaDoc="지정품판가"),
   @GeneratedProperty(name="appointTotal", type=String.class, initialValue="\"0\"",
      javaDoc="지정품합계"),
   @GeneratedProperty(name="cr", type=String.class, initialValue="\"0\"",
      javaDoc="CR(%)"),
   @GeneratedProperty(name="applyYear", type=String.class, initialValue="\"0\"", 
      javaDoc="적용년수"),
   @GeneratedProperty(name="correctPeriod", type=String.class, initialValue="\"0\"",
      javaDoc="회수기간"),
   @GeneratedProperty(name="salesTargetCostTotal", type=String.class,
      javaDoc="판매목표가"),
   @GeneratedProperty(name="productCostTotal", type=String.class,
      javaDoc="총원가"),
   @GeneratedProperty(name="quantity", type=String.class,
      javaDoc="판매량(천개)"),
   @GeneratedProperty(name="totalSales", type=String.class,
      javaDoc="매출액(백만원)"),
   @GeneratedProperty(name="profitCost", type=String.class,
      javaDoc="영업이익(백만원)"),
   @GeneratedProperty(name="cashInFlow", type=String.class,
      javaDoc="현금유입액(백만원)"),
   @GeneratedProperty(name="profitRateTotal", type=String.class,
      javaDoc="영업이익율")
   },
   foreignKeys={
   @GeneratedForeignKey(name="costAnalisysLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="costPart", type=ext.ket.cost.entity.CostPart.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="costAnalysis"))
   })
public class CostAnalysis extends _CostAnalysis {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostAnalysis
    * @exception wt.util.WTException
    **/
   public static CostAnalysis newCostAnalysis()
            throws WTException {

      CostAnalysis instance = new CostAnalysis();
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
