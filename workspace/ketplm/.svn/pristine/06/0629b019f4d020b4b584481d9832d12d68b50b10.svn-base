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

package e3ps.project.trySchdule;

import e3ps.common.code.NumberCode;
import e3ps.common.impl.OwnPersistable;
import e3ps.project.E3PSProjectMaster;
import e3ps.project.material.MoldMaterial;
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
import wt.fc.Persistable;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.org.WTPrincipalReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;
import java.lang.Boolean;  // Preserved unmodeled dependency
import java.lang.Boolean;  // Preserved unmodeled dependency
import e3ps.project.MoldProject;  // Preserved unmodeled dependency

/**
 *
 * <p>
 * Use the <code>newTrySchdule</code> static factory method(s), not the
 * <code>TrySchdule</code> constructor, to construct instances of this class.
 *  Instances must be constructed using the static factory(s), in order
 * to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="tryType", type=String.class,
      javaDoc="Try단계"),
   @GeneratedProperty(name="state", type=String.class,
      javaDoc="상태"),
   @GeneratedProperty(name="des", type=String.class,
      javaDoc="수정내용",
      constraints=@PropertyConstraints(upperLimit=2000)),
   @GeneratedProperty(name="ton", type=String.class,
      javaDoc="설비(TON)"),
   @GeneratedProperty(name="quantity", type=String.class,
      javaDoc="수량"),
   @GeneratedProperty(name="planDate", type=Timestamp.class,
      javaDoc="계획"),
   @GeneratedProperty(name="completed", type=boolean.class),
   @GeneratedProperty(name="tryPlan", type=boolean.class),
   @GeneratedProperty(name="tryPlace", type=String.class,
      javaDoc="Try장소"),
   @GeneratedProperty(name="width", type=String.class,
      javaDoc="원재료(비철) 폭"),
   @GeneratedProperty(name="thickness", type=String.class,
      javaDoc="원재료(비철) 두께"),
   @GeneratedProperty(name="requester", type=WTPrincipalReference.class,
      constraints=@PropertyConstraints(required=true),
      columnProperties=@ColumnProperties(columnName="D")),
   @GeneratedProperty(name="creator", type=WTPrincipalReference.class,
      constraints=@PropertyConstraints(required=true),
      columnProperties=@ColumnProperties(columnName="E"))
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="material", type=e3ps.project.material.MoldMaterial.class),
      myRole=@MyRole(name="theTrySchdule", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="property", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="theTrySchdule", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldMaster", type=e3ps.project.E3PSProjectMaster.class),
      myRole=@MyRole(name="theTrySchdule", cardinality=Cardinality.ONE))
   })
public class TrySchdule extends _TrySchdule {


   static final long serialVersionUID = 1;




   /**
    * @return    TrySchdule
    * @exception wt.util.WTException
    **/
   public static TrySchdule newTrySchdule()
            throws WTException {

      TrySchdule instance = new TrySchdule();
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
