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

package e3ps.ecm.entity;

import e3ps.common.code.NumberCode;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newKETChangeActivity</code> static factory method(s), not
 * the <code>KETChangeActivity</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={WTContained.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="activityCode", type=String.class,
      javaDoc="NumberCode의 코드(ECA 타입)"),
   @GeneratedProperty(name="chargeName", type=String.class,
      javaDoc="담당자 ID(WTUser의 Name)"),
   @GeneratedProperty(name="completeRequestDate", type=Timestamp.class,
      javaDoc="완료요청일"),
   @GeneratedProperty(name="customType", type=String.class,
      javaDoc="사용자 정의시 Activity Type : 'DOC', 'ACT'"),
   @GeneratedProperty(name="customCode", type=String.class,
      javaDoc="사용자 정의시 Activity Code"),
   @GeneratedProperty(name="customName", type=String.class,
      javaDoc="사용자 정의시 Activity Name")
   },
   foreignKeys={
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="charge", type=wt.org.WTUser.class),
      myRole=@MyRole(name="theKETChangeActivity", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(
      foreignKeyRole=@ForeignKeyRole(name="activity", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theKETChangeActivity", cardinality=Cardinality.ONE))
   })
public class KETChangeActivity extends _KETChangeActivity {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETChangeActivity
    * @exception wt.util.WTException
    **/
   public static KETChangeActivity newKETChangeActivity()
            throws WTException {

      KETChangeActivity instance = new KETChangeActivity();
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
