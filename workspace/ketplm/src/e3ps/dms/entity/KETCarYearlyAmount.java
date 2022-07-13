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

package e3ps.dms.entity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Double;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.InvalidAttributeException;
import wt.fc.PersistInfo;
import wt.fc.Persistable;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETCarYearlyAmount</code> static factory method(s),
 * not the <code>KETCarYearlyAmount</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="carTypeCode", type=String.class,
      javaDoc="차종코드"),
   @GeneratedProperty(name="carType", type=String.class,
      javaDoc="차종명"),
   @GeneratedProperty(name="yearAmount1", type=Double.class,
      javaDoc="1년차기획수량"),
   @GeneratedProperty(name="yearAmount2", type=Double.class,
      javaDoc="2년차기획수량"),
   @GeneratedProperty(name="yearAmount3", type=Double.class,
      javaDoc="3년차기획수량"),
   @GeneratedProperty(name="yearAmount4", type=Double.class,
      javaDoc="4년차기획수량"),
   @GeneratedProperty(name="yearAmount5", type=Double.class,
      javaDoc="5년차기획수량"),
   @GeneratedProperty(name="yearAmount6", type=Double.class,
      javaDoc="6년차기획수량"),
   @GeneratedProperty(name="yearAmount7", type=Double.class,
      javaDoc="7년차기획수량"),
   @GeneratedProperty(name="yearAmount8", type=Double.class,
      javaDoc="8년차기획수량"),
   @GeneratedProperty(name="yearAmount9", type=Double.class,
      javaDoc="9년차기획수량"),
   @GeneratedProperty(name="yearAmount10", type=Double.class,
      javaDoc="10년차기획수량"),
   @GeneratedProperty(name="applyedUsage", type=Double.class,
      javaDoc="적용수량"),
   @GeneratedProperty(name="optionRate", type=Double.class,
      javaDoc="옵션률"),
   @GeneratedProperty(name="attribute1", type=String.class,
      javaDoc="기타1"),
   @GeneratedProperty(name="attribute2", type=String.class,
      javaDoc="기타2"),
   @GeneratedProperty(name="attribute3", type=String.class,
      javaDoc="기타3"),
   @GeneratedProperty(name="attribute4", type=String.class,
      javaDoc="기타4"),
   @GeneratedProperty(name="attribute5", type=String.class,
      javaDoc="기타5"),
   @GeneratedProperty(name="attribute6", type=String.class,
      javaDoc="기타6"),
   @GeneratedProperty(name="attribute7", type=String.class,
      javaDoc="기타7"),
   @GeneratedProperty(name="attribute8", type=String.class,
      javaDoc="기타8"),
   @GeneratedProperty(name="attribute9", type=String.class,
      javaDoc="기타9"),
   @GeneratedProperty(name="attribute10", type=String.class,
      javaDoc="기타10")
   })
public class KETCarYearlyAmount extends _KETCarYearlyAmount {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETCarYearlyAmount
    * @exception wt.util.WTException
    **/
   public static KETCarYearlyAmount newKETCarYearlyAmount()
            throws WTException {

      KETCarYearlyAmount instance = new KETCarYearlyAmount();
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
