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

package e3ps.ews.entity;

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
import e3ps.ews.entity.KETEarlyWarning;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
import java.lang.Double;  // Preserved unmodeled dependency
import java.lang.Float;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newKETEarlyWarningTarget</code> static factory method(s),
 * not the <code>KETEarlyWarningTarget</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="productNo", type=String.class,
      javaDoc="제품 번호"),
   @GeneratedProperty(name="targetCusError", type=Double.class,
      javaDoc="고객불량 PPM의 목표값"),
   @GeneratedProperty(name="targetWorkError", type=Double.class,
      javaDoc="공정불량 PPM의 목표값"),
   @GeneratedProperty(name="targetFacility", type=Double.class,
      javaDoc="설비가동율의 목표값"),
   @GeneratedProperty(name="targetPerform", type=Double.class,
      javaDoc="성능가동율의 목표값"),
   @GeneratedProperty(name="targetGood", type=Double.class,
      javaDoc="양품율의 목표값"),
   @GeneratedProperty(name="attribute1", type=String.class,
      javaDoc="추가 속성 항목1",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute2", type=String.class,
      javaDoc="추가 속성 항목2",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute3", type=String.class,
      javaDoc="추가 속성 항목3",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute4", type=String.class,
      javaDoc="추가 속성 항목4",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute5", type=String.class,
      javaDoc="추가 속성 항목5",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute6", type=String.class,
      javaDoc="추가 속성 항목6",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute7", type=String.class,
      javaDoc="추가 속성 항목7",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute8", type=String.class,
      javaDoc="추가 속성 항목8",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute9", type=String.class,
      javaDoc="추가 속성 항목9",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="attribute10", type=String.class,
      javaDoc="추가 속성 항목10",
      constraints=@PropertyConstraints(upperLimit=4000))
   })
public class KETEarlyWarningTarget extends _KETEarlyWarningTarget {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETEarlyWarningTarget
    * @exception wt.util.WTException
    **/
   public static KETEarlyWarningTarget newKETEarlyWarningTarget()
            throws WTException {

      KETEarlyWarningTarget instance = new KETEarlyWarningTarget();
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
