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
import e3ps.project.CostInfo;
import e3ps.project.ProductProject;
import e3ps.project.material.MoldMaterial;
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

/**
 * 금형 Item정보
 * <p>
 * Use the <code>newMoldItemInfo</code> static factory method(s), not the
 * <code>MoldItemInfo</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="partNo", type=String.class),
   @GeneratedProperty(name="dieNo", type=String.class),
   @GeneratedProperty(name="partName", type=String.class),
   @GeneratedProperty(name="itemType", type=String.class),
   @GeneratedProperty(name="cVPitch", type=String.class),
   @GeneratedProperty(name="cTSPM", type=String.class),
   @GeneratedProperty(name="making", type=String.class),
   @GeneratedProperty(name="productionPlace", type=String.class),
   @GeneratedProperty(name="thickness", type=String.class),
   @GeneratedProperty(name="width", type=String.class),
   @GeneratedProperty(name="partnerNo", type=String.class),
   @GeneratedProperty(name="shrinkage", type=String.class),
   @GeneratedProperty(name="etc", type=String.class),
   @GeneratedProperty(name="etc1", type=String.class),
   @GeneratedProperty(name="makingPlacePartnerNo", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(name="ProductProjectMoldInfoLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="project", type=e3ps.project.ProductProject.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="moldInfo", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="MoldItemProductTypeLInk", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="productType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="moldItem", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="MoldItemTypeLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldType", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="moldItem", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="purchasePlace", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="moldItem", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="MoldItemPropertyLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="property", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="moldItem", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="MoldItemMaterialLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="material", type=e3ps.project.material.MoldMaterial.class),
      myRole=@MyRole(name="moldItem", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="CostInfoMoldItemLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="costInfo", type=e3ps.project.CostInfo.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="moldItem", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="MoldItemMakingPlaceLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="makingPlace", type=e3ps.common.code.NumberCode.class),
      myRole=@MyRole(name="modelItem", cardinality=Cardinality.ONE))
   })
public class MoldItemInfo extends _MoldItemInfo {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    MoldItemInfo
    * @exception wt.util.WTException
    **/
   public static MoldItemInfo newMoldItemInfo()
            throws WTException {

      MoldItemInfo instance = new MoldItemInfo();
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
