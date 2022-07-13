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

package e3ps.wfm.entity;

import e3ps.common.impl.OwnPersistable;
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
import wt.fc.PersistentReference;
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

/**
 *
 * <p>
 * Use the <code>newKETWfmApprovalMaster</code> static factory method(s),
 * not the <code>KETWfmApprovalMaster</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * <BR><BR><B>Supported API: </B>true
 * <BR><BR><B>Extendable: </B>false
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="agreementType", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      javaDoc="합의유형",
      constraints=@PropertyConstraints(upperLimit=30)),
   @GeneratedProperty(name="comments", type=String.class, supportedAPI=SupportedAPI.PUBLIC,
      javaDoc="결재요청의견",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="startFlag", type=boolean.class, initialValue="false",
      javaDoc="임시저장 = 0≪≫결재시작 = 1"),
   @GeneratedProperty(name="businessobjectRef", type=PersistentReference.class),
   @GeneratedProperty(name="completedDate", type=Timestamp.class)
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETWfmMasterPboLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="pbo", type=wt.fc.Persistable.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="appMaster", cardinality=Cardinality.ZERO_TO_ONE))
   },
   tableProperties=@TableProperties(oracleTableSize=OracleTableSize.LARGE)
)
public class KETWfmApprovalMaster extends _KETWfmApprovalMaster {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETWfmApprovalMaster
    * @exception wt.util.WTException
    **/
   public static KETWfmApprovalMaster newKETWfmApprovalMaster()
            throws WTException {

      KETWfmApprovalMaster instance = new KETWfmApprovalMaster();
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
