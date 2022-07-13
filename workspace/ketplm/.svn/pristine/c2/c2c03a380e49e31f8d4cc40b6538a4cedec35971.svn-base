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
import java.util.Vector;

import wt.fc.InvalidAttributeException;
import wt.org.WTPrincipalReference;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;

/**
 * 금형부품관리
 * <p>
 * Use the <code>newMoldPartManager</code> static factory method(s), not
 * the <code>MoldPartManager</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="dieNo", type=String.class,
      columnProperties=@ColumnProperties(index=true)),
   @GeneratedProperty(name="ecoNo", type=String.class),
   @GeneratedProperty(name="levelType", type=String.class),
   @GeneratedProperty(name="createType", type=String.class),
   @GeneratedProperty(name="moldDate", type=Timestamp.class),
   @GeneratedProperty(name="planDate", type=Timestamp.class),
   @GeneratedProperty(name="levelMold", type=String.class),
   @GeneratedProperty(name="moldState", type=String.class),
   @GeneratedProperty(name="sortNo", type=String.class),
   @GeneratedProperty(name="attr1", type=String.class),
   @GeneratedProperty(name="attr2", type=String.class),
   @GeneratedProperty(name="attr3", type=String.class),
   @GeneratedProperty(name="endDate", type=Timestamp.class),
   @GeneratedProperty(name="cha", type=int.class),
   @GeneratedProperty(name="subCha", type=int.class),
   @GeneratedProperty(name="partUser", type=WTPrincipalReference.class,
      constraints=@PropertyConstraints(required=true),
      columnProperties=@ColumnProperties(columnName="B"))
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="project", type=e3ps.project.MoldProject.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theMoldPartManager", cardinality=Cardinality.ONE))
   })
public class MoldPartManager extends _MoldPartManager {


   private transient Vector subParts;
   static final long serialVersionUID = 1;




   /**
    * Gets the object for the association that plays role: SUB_PARTS.
    *
    * @return    Vector
    **/
   public Vector getSubParts() {

      return subParts;
   }

   /**
    * Sets the object for the association that plays role: SUB_PARTS.
    *
    * @param     a_SubParts
    * @exception wt.util.WTPropertyVetoException
    **/
   public void setSubParts( Vector a_SubParts )
            throws WTPropertyVetoException {

      subPartsValidate( a_SubParts );   // throws exception if not valid
      subParts = a_SubParts;
   }

   /**
    * @param     a_SubParts
    * @exception wt.util.WTPropertyVetoException
    **/
   private void subPartsValidate( Vector a_SubParts )
            throws WTPropertyVetoException {
      if ( a_SubParts == null ) {                                  // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "subParts" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "subParts", subParts, a_SubParts ) );
      }
   }

   /**
    * Default factory for the class.
    *
    * @return    MoldPartManager
    * @exception wt.util.WTException
    **/
   public static MoldPartManager newMoldPartManager()
            throws WTException {

      MoldPartManager instance = new MoldPartManager();
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
