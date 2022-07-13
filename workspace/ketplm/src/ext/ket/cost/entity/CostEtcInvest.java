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

import e3ps.common.impl.OwnPersistable;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 * 기타 투자비
 * <p>
 * Use the <code>newCostEtcInvest</code> static factory method(s), not the
 * <code>CostEtcInvest</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="itemName", type=String.class,
      javaDoc="항목"),
   @GeneratedProperty(name="etcNfactory", type=String.class,
      javaDoc="신규 제작처"),
   @GeneratedProperty(name="etcNcost", type=String.class,
      javaDoc="신규 투자비"),
   @GeneratedProperty(name="etcNunit_1", type=String.class,
      javaDoc="신규 투자비 화폐단위"),
   @GeneratedProperty(name="etcNpay", type=String.class,
      javaDoc="신규 지급액"),
   @GeneratedProperty(name="etcNunit_2", type=String.class,
      javaDoc="신규 지급액 화폐단위"),
   @GeneratedProperty(name="etcPfactory", type=String.class,
      javaDoc="양산 제작처"),
   @GeneratedProperty(name="etcPcost", type=String.class,
      javaDoc="양산 투자비"),
   @GeneratedProperty(name="etcPunit", type=String.class,
      javaDoc="양산 화폐단위"),
   @GeneratedProperty(name="etcTotalQty", type=String.class,
      javaDoc="양산수량 Total"),
   @GeneratedProperty(name="etcMaxQty", type=String.class,
      javaDoc="양산수량 MAX")
   })
public class CostEtcInvest extends _CostEtcInvest {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostEtcInvest
    * @exception wt.util.WTException
    **/
   public static CostEtcInvest newCostEtcInvest()
            throws WTException {

      CostEtcInvest instance = new CostEtcInvest();
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
