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

package e3ps.project.moldPart;

import java.sql.Timestamp;

import wt.fc.InvalidAttributeException;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 부품목록
 * <p>
 * Use the <code>newMoldSubPart</code> static factory method(s), not the
 * <code>MoldSubPart</code> constructor, to construct instances of this
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
   @GeneratedProperty(name="moldNo", type=String.class),
   @GeneratedProperty(name="moldName", type=String.class,
      constraints=@PropertyConstraints(upperLimit=3000)),
   @GeneratedProperty(name="ea", type=String.class),
   @GeneratedProperty(name="material", type=String.class),
   @GeneratedProperty(name="partClass", type=String.class),
   @GeneratedProperty(name="partType", type=String.class),
   @GeneratedProperty(name="endDate", type=Timestamp.class),
   @GeneratedProperty(name="mType", type=String.class),
   @GeneratedProperty(name="actionType", type=String.class),
   @GeneratedProperty(name="transferDate", type=Timestamp.class),
   @GeneratedProperty(name="etcDesc", type=String.class,
      constraints=@PropertyConstraints(upperLimit=3000)),
   @GeneratedProperty(name="subState", type=String.class),
   @GeneratedProperty(name="attr1", type=String.class),
   @GeneratedProperty(name="attr2", type=String.class),
   @GeneratedProperty(name="attr3", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="manager", type=e3ps.project.moldPart.MoldPartManager.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theMoldSubPart", cardinality=Cardinality.ONE))
   })
public class MoldSubPart extends _MoldSubPart {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    MoldSubPart
    * @exception wt.util.WTException
    **/
   public static MoldSubPart newMoldSubPart()
            throws WTException {

      MoldSubPart instance = new MoldSubPart();
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
