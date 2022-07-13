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

package e3ps.project;

import e3ps.common.code.NumberCode;
import e3ps.project.ProductProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.InvalidAttributeException;
import wt.fc.ObjectReference;
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
import e3ps.project.MoldItemInfo;  // Preserved unmodeled dependency

/**
 * 비용관리
 * <p>
 * Use the <code>newCostInfo</code> static factory method(s), not the <code>CostInfo</code>
 * constructor, to construct instances of this class.  Instances must be
 * constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="costType", type=String.class),
   @GeneratedProperty(name="dieNo", type=String.class),
   @GeneratedProperty(name="costName", type=String.class),
   @GeneratedProperty(name="targetCost", type=String.class,
      javaDoc="목표가"),
   @GeneratedProperty(name="orderInvest", type=String.class,
      javaDoc="투자오더"),
   @GeneratedProperty(name="executionCost", type=String.class,
      javaDoc="집행가"),
   @GeneratedProperty(name="editCost", type=String.class,
      javaDoc="수정비"),
   @GeneratedProperty(name="partNo", type=String.class),
   @GeneratedProperty(name="decideCost", type=String.class,
      javaDoc="확정가")
   },
   foreignKeys={
   @GeneratedForeignKey(name="ProjectCostInfoLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="project", type=e3ps.project.ProductProject.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="costInfo", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="CostItemMoldTypeLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="costItem", cardinality=Cardinality.ONE))
   })
public class CostInfo extends _CostInfo {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    CostInfo
    * @exception wt.util.WTException
    **/
   public static CostInfo newCostInfo()
            throws WTException {

      CostInfo instance = new CostInfo();
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
