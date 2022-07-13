/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */

package ext.ket.sales.entity;

import e3ps.groupware.company.Department;
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
 * Use the <code>newKETSalesTask</code> static factory method(s), not the
 * <code>KETSalesTask</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="subject", type=String.class,
      javaDoc="Subject",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="salesPlan", type=String.class,
      javaDoc="영업추진계획",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="planDate", type=Timestamp.class,
      javaDoc="계획일자"),
   @GeneratedProperty(name="resultDate", type=Timestamp.class,
      javaDoc="실적일자"),
   @GeneratedProperty(name="planCheck", type=String.class,
      javaDoc="진행상황(G,Y,K)"),
   @GeneratedProperty(name="propelwebEditor", type=Object.class,
      javaDoc="진행현황",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="propelwebEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="problemwebEditor", type=Object.class,
      javaDoc="문제점",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="problemwebEditorText", type=Object.class,
      javaDoc="문제점",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="planwebEditor", type=Object.class,
      javaDoc="해결방안",
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="planwebEditorText", type=Object.class,
      columnProperties=@ColumnProperties(columnType=ColumnType.BLOB)),
   @GeneratedProperty(name="stepNo", type=String.class,
      javaDoc="stepNo(sort)"),
   @GeneratedProperty(name="taskType", type=String.class),
   @GeneratedProperty(name="salesSubjectCode", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="collaboTeam", type=e3ps.groupware.company.Department.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETSalesTask", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="collaboManager", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETSalesTask", cardinality=Cardinality.ONE))
   })
public class KETSalesTask extends _KETSalesTask {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    KETSalesTask
    * @exception wt.util.WTException
    **/
   public static KETSalesTask newKETSalesTask()
            throws WTException {

      KETSalesTask instance = new KETSalesTask();
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
