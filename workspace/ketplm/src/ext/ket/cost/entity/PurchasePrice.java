/* bcwti�����Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.�����This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.�����ecwti
 */

package ext.ket.cost.entity;

import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newPurchasePrice</code> static factory method(s), not the
 * <code>PurchasePrice</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="partNo", type=String.class,
      javaDoc="품번"),
   @GeneratedProperty(name="currency", type=String.class,
      javaDoc="통화"),
   @GeneratedProperty(name="unit", type=String.class,
      javaDoc="단위"),
   @GeneratedProperty(name="price", type=String.class,
      javaDoc="단가"),
   @GeneratedProperty(name="purchaseOrg", type=String.class,
      javaDoc="구매조직"),
   @GeneratedProperty(name="supplierName", type=String.class,
      javaDoc="공급업체"),
   @GeneratedProperty(name="supplierCode", type=String.class,
      javaDoc="공급업체코드"),
   @GeneratedProperty(name="validDate", type=Timestamp.class,
      javaDoc="유효종료일"),
   @GeneratedProperty(name="purchaseOrderDate", type=Timestamp.class,
      javaDoc="구매오더증빙일"),
   @GeneratedProperty(name="per", type=String.class,
      javaDoc="per")
   })
public class PurchasePrice extends _PurchasePrice {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    PurchasePrice
    * @exception wt.util.WTException
    **/
   public static PurchasePrice newPurchasePrice()
            throws WTException {

      PurchasePrice instance = new PurchasePrice();
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
