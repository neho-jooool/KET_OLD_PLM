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

import e3ps.common.impl.OwnPersistable;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;
import wt.content.ContentHolder;
import wt.content.HttpContentOperation;
import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
import wt.fc.PersistInfo;
import wt.introspection.ClassInfo;
import wt.introspection.WTIntrospectionException;
import wt.introspection.WTIntrospector;
import wt.org.WTPrincipalReference;
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
 * Use the <code>newKETMoldChangePlan</code> static factory method(s), not
 * the <code>KETMoldChangePlan</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class, ContentHolder.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="scheduleId", type=String.class),
   @GeneratedProperty(name="planType", type=String.class),
   @GeneratedProperty(name="dieNo", type=String.class),
   @GeneratedProperty(name="partNo", type=String.class),
   @GeneratedProperty(name="partName", type=String.class),
   @GeneratedProperty(name="planDesc", type=String.class),
   @GeneratedProperty(name="prodDwgPlanDate", type=Timestamp.class),
   @GeneratedProperty(name="prodDwgActualDate", type=Timestamp.class),
   @GeneratedProperty(name="moldChangePlanDate", type=Timestamp.class),
   @GeneratedProperty(name="moldChangeActualDate", type=Timestamp.class),
   @GeneratedProperty(name="workPlanDate", type=Timestamp.class),
   @GeneratedProperty(name="workActualDate", type=Timestamp.class),
   @GeneratedProperty(name="storePlanDate", type=Timestamp.class),
   @GeneratedProperty(name="storeActualDate", type=Timestamp.class),
   @GeneratedProperty(name="assemblePlanDate", type=Timestamp.class),
   @GeneratedProperty(name="assembleActualDate", type=Timestamp.class),
   @GeneratedProperty(name="tryPlanDate", type=Timestamp.class),
   @GeneratedProperty(name="tryActualDate", type=Timestamp.class),
   @GeneratedProperty(name="testPlanDate", type=Timestamp.class),
   @GeneratedProperty(name="testActualDate", type=Timestamp.class),
   @GeneratedProperty(name="approvePlanDate", type=Timestamp.class),
   @GeneratedProperty(name="approveActualDate", type=Timestamp.class),
   @GeneratedProperty(name="deptName", type=String.class),
   @GeneratedProperty(name="scheduleStatus", type=String.class),
   @GeneratedProperty(name="vendorFlag", type=String.class),
   @GeneratedProperty(name="vendorCode", type=String.class),
   @GeneratedProperty(name="prodDeptName", type=String.class),
   @GeneratedProperty(name="regBasis", type=String.class),
   @GeneratedProperty(name="basisDate", type=Timestamp.class),
   @GeneratedProperty(name="modifyDesc", type=String.class),
   @GeneratedProperty(name="measureType", type=String.class),
   @GeneratedProperty(name="failAction", type=String.class),
   @GeneratedProperty(name="result", type=String.class),
   @GeneratedProperty(name="measureDate", type=Timestamp.class),
   @GeneratedProperty(name="currentProcess", type=String.class),
   @GeneratedProperty(name="currentProcPlanDate", type=Timestamp.class),
   @GeneratedProperty(name="attribute1", type=String.class),
   @GeneratedProperty(name="attribute2", type=String.class),
   @GeneratedProperty(name="attribute3", type=String.class),
   @GeneratedProperty(name="attribute4", type=String.class),
   @GeneratedProperty(name="attribute5", type=String.class),
   @GeneratedProperty(name="attribute6", type=String.class),
   @GeneratedProperty(name="attribute7", type=String.class),
   @GeneratedProperty(name="attribute8", type=String.class),
   @GeneratedProperty(name="attribute9", type=String.class),
   @GeneratedProperty(name="attribute10", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(name="KETMoldECOPlanLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldECO", type=e3ps.ecm.entity.KETMoldChangeOrder.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="moldPlan")),
   @GeneratedForeignKey(name="KETProdECOPlanLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="prodECO", type=e3ps.ecm.entity.KETProdChangeOrder.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="moldPlan")),
   @GeneratedForeignKey(name="KETMoldPlanPEcoOwnerLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="prodEcoOwner", type=wt.org.WTUser.class),
      myRole=@MyRole(name="theKETMoldChangePlan", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETMoldPlanMEcoOwnerLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldEcoOwner", type=wt.org.WTUser.class),
      myRole=@MyRole(name="theKETMoldChangePlan", cardinality=Cardinality.ONE))
   })
public class KETMoldChangePlan extends _KETMoldChangePlan {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETMoldChangePlan
    * @exception wt.util.WTException
    **/
   public static KETMoldChangePlan newKETMoldChangePlan()
            throws WTException {

      KETMoldChangePlan instance = new KETMoldChangePlan();
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
