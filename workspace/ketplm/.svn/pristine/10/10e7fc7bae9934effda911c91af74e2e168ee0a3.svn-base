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
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 * 예상물량
 * <p>
 * Use the <code>newCostQuantity</code> static factory method(s), not the
 * <code>CostQuantity</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="quantity", type=String.class,
      javaDoc="예상수량"),
   @GeneratedProperty(name="year", type=String.class,
      javaDoc="년차"),
   @GeneratedProperty(name="version", type=Integer.class, initialValue="0",
      javaDoc="버전"),
   @GeneratedProperty(name="salesTargetCostExpr", type=String.class, initialValue="\"0\"",
      javaDoc="판매목표가"),
   @GeneratedProperty(name="payPlace", type=String.class,
      javaDoc="완제품입고"),
   @GeneratedProperty(name="sopYear", type=String.class,
      javaDoc="sop"),
   @GeneratedProperty(name="deliverName", type=String.class,
      javaDoc="입고처"),
   @GeneratedProperty(name="disposableCr", type=String.class, initialValue="\"0\"",
      javaDoc="CR적용율"),
   @GeneratedProperty(name="disposableApplyYear", type=String.class, initialValue="\"0\"",
      javaDoc="CR적용년수"),
   @GeneratedProperty(name="lastest", type=String.class, initialValue="\"0\"",
      javaDoc="최종안 여부"),
   @GeneratedProperty(name="salesTargetGb", type=String.class,
      javaDoc="판가구분")
   },
   foreignKeys={
   @GeneratedForeignKey(name="costQuantityLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="quantityMaster", type=ext.ket.cost.entity.ProductMaster.class),
      myRole=@MyRole(name="costQuantity")),
   @GeneratedForeignKey(name="costPartQtyLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="costPart", type=ext.ket.cost.entity.CostPart.class),
      myRole=@MyRole(name="costQuantity"))
   })
public class CostQuantity extends _CostQuantity {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostQuantity
    * @exception wt.util.WTException
    **/
   public static CostQuantity newCostQuantity()
            throws WTException {

      CostQuantity instance = new CostQuantity();
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
