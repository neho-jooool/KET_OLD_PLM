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

package e3ps.project.material;

import e3ps.common.code.NumberCode;

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
 *
 * <p>
 * Use the <code>newMoldMaterial</code> static factory method(s), not the
 * <code>MoldMaterial</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="material", type=String.class),
   @GeneratedProperty(name="grade", type=String.class),
   @GeneratedProperty(name="spec_no", type=String.class),
   @GeneratedProperty(name="temper", type=String.class),
   @GeneratedProperty(name="s_gravity", type=String.class),
   @GeneratedProperty(name="m_elasticity", type=String.class),
   @GeneratedProperty(name="e_conductivity", type=String.class),
   @GeneratedProperty(name="hardness", type=String.class),
   @GeneratedProperty(name="strength", type=String.class),
   @GeneratedProperty(name="elong", type=String.class),
   @GeneratedProperty(name="formability", type=String.class),
   @GeneratedProperty(name="residual_stress", type=String.class),
   @GeneratedProperty(name="t_melt", type=String.class),
   @GeneratedProperty(name="t_soft", type=String.class),
   @GeneratedProperty(name="t_conductivity", type=String.class),
   @GeneratedProperty(name="r_y", type=String.class),
   @GeneratedProperty(name="r_m", type=String.class),
   @GeneratedProperty(name="r_d", type=String.class),
   @GeneratedProperty(name="melt_point", type=String.class),
   @GeneratedProperty(name="m_index", type=String.class),
   @GeneratedProperty(name="m_shrinkage", type=String.class),
   @GeneratedProperty(name="absorb", type=String.class),
   @GeneratedProperty(name="t_strength", type=String.class),
   @GeneratedProperty(name="f_stringth", type=String.class),
   @GeneratedProperty(name="f_modulus", type=String.class),
   @GeneratedProperty(name="i_strength", type=String.class),
   @GeneratedProperty(name="h_dis_temp_18", type=String.class),
   @GeneratedProperty(name="h_dis_temp_4", type=String.class),
   @GeneratedProperty(name="flammability", type=String.class),
   @GeneratedProperty(name="s_flow", type=String.class),
   @GeneratedProperty(name="memo", type=String.class),
   @GeneratedProperty(name="price", type=String.class),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="materialMaker", type=e3ps.common.code.NumberCode.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theMoldMaterial", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="materialType", type=e3ps.common.code.NumberCode.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theMoldMaterial", cardinality=Cardinality.ONE))
   })
public class MoldMaterial extends _MoldMaterial {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    MoldMaterial
    * @exception wt.util.WTException
    **/
   public static MoldMaterial newMoldMaterial()
            throws WTException {

      MoldMaterial instance = new MoldMaterial();
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
