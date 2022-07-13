// Generated TheMoldProjectTheMoldItemInfo%4C875CC50284: ? 09/09/10 11:10:59
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

import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ForeignKeyLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

//##begin user.imports preserve=yes
//##end user.imports

//##begin version.uid preserve=yes
/* version                                    uid
   X-12-M040                                = 2454203546147727912L
*/
//##end version.uid

//##begin TheMoldProjectTheMoldItemInfo%4C875CC50284.doc preserve=no
/**
 *
 * <p>
 * Use the <code>newTheMoldProjectTheMoldItemInfo</code> static factory
 * method(s), not the <code>TheMoldProjectTheMoldItemInfo</code> constructor,
 * to construct instances of this class.  Instances must be constructed
 * using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/
//##end TheMoldProjectTheMoldItemInfo%4C875CC50284.doc

public class TheMoldProjectTheMoldItemInfo extends ForeignKeyLink implements Externalizable {


   // --- Attribute Section ---


   private static final String RESOURCE = "e3ps.project.projectResource";
   private static final String CLASSNAME = TheMoldProjectTheMoldItemInfo.class.getName();

   //##begin MOLD_PROJECT_ROLE%MOLD_PROJECT_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end MOLD_PROJECT_ROLE%MOLD_PROJECT_ROLE.doc
   public static final String MOLD_PROJECT_ROLE = "theMoldProject";


   //##begin MOLD_ITEM_INFO_ROLE%MOLD_ITEM_INFO_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end MOLD_ITEM_INFO_ROLE%MOLD_ITEM_INFO_ROLE.doc
   public static final String MOLD_ITEM_INFO_ROLE = "theMoldItemInfo";

   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = 2454203546147727912L;
   protected static final long OLD_FORMAT_VERSION_UID = 9014418128237953846L;

   // WARNING: Fields placed in this section will not be generated into externalization methods.
   //##begin user.attributes preserve=yes
   //##end user.attributes

   //##begin static.initialization preserve=yes
   //##end static.initialization


   // --- Operation Section ---

   //##begin writeExternal%writeExternal.doc preserve=no
   /**
    * Writes the non-transient fields of this class to an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     output
    * @exception java.io.IOException
    **/
   //##end writeExternal%writeExternal.doc

   public void writeExternal( ObjectOutput output )
            throws IOException {
      //##begin writeExternal%writeExternal.body preserve=no

      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      //##end writeExternal%writeExternal.body
   }

   //##begin readExternal%readExternal.doc preserve=no
   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     input
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   //##end readExternal%readExternal.doc

   public void readExternal( ObjectInput input )
            throws IOException, ClassNotFoundException {
      //##begin readExternal%readExternal.body preserve=no

      long readSerialVersionUID = input.readLong();                  // consume UID
      readVersion( this, input, readSerialVersionUID, false, false );  // read fields
      //##end readExternal%readExternal.body
   }

   //##begin readVersion%readVersion.doc preserve=no
   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     thisObject
    * @param     input
    * @param     readSerialVersionUID
    * @param     passThrough
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   //##end readVersion%readVersion.doc

   protected boolean readVersion( TheMoldProjectTheMoldItemInfo thisObject, ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone )
            throws IOException, ClassNotFoundException {
      //##begin readVersion%readVersion.body preserve=no

      boolean success = true;

      if ( readSerialVersionUID == 2454203546147727912L )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
      //##end readVersion%readVersion.body
   }

   //##begin readOldVersion%readOldVersion.doc preserve=no
   /**
    * Reads the non-transient fields of this class from an external source,
    * which is not the current version.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     passThrough
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   //##end readOldVersion%readOldVersion.doc

   private boolean readOldVersion( ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone )
            throws IOException, ClassNotFoundException {
      //##begin readOldVersion%readOldVersion.body preserve=no

      boolean success = true;

      if ( readSerialVersionUID == OLD_FORMAT_VERSION_UID ) {          // handle previous version
      }
      else if ( !superDone ) {
         success = super.readVersion( this, input, readSerialVersionUID, false, false );  // reformatted stream?
         if ( success && !passThrough &&                                                   // forced pass through to skip me
            readSerialVersionUID != super.EXTERNALIZATION_VERSION_UID )                    // I have been inserted into hierarchy
            readVersion( this, input, input.readLong(), false, true );                     // try mine again
      }
      else
         throw new java.io.InvalidClassException( CLASSNAME, "Local class not compatible:"
                           + " stream classdesc externalizationVersionUID=" + readSerialVersionUID
                           + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID );

      return success;
      //##end readOldVersion%readOldVersion.body
   }

   //##begin writeExternal%writeExternal.doc preserve=no
   /**
    * Used by Persistent Data Service to obtain the values of the persistent
    * attributes of this class, so they can be written to a persistent store.
    * <p>(Not intended for general use.)
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     output
    * @exception java.sql.SQLException
    * @exception wt.pom.DatastoreException
    **/
   //##end writeExternal%writeExternal.doc

   public void writeExternal( PersistentStoreIfc output )
            throws SQLException, DatastoreException {
      super.writeExternal( output );

   }

   //##begin readExternal%readExternal.doc preserve=no
   /**
    * Used by Persistent Data Service to populate the persistent attributes
    * of this class from a persistent store. <p>(Not intended for general
    * use.)
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     input
    * @exception java.sql.SQLException
    * @exception wt.pom.DatastoreException
    **/
   //##end readExternal%readExternal.doc

   public void readExternal( PersistentRetrieveIfc input )
            throws SQLException, DatastoreException {
      super.readExternal( input );

   }

   //##begin getMoldProject%4C875CC600CCg.doc preserve=no
   /**
    * Gets the object for the association that plays role: MOLD_PROJECT_ROLE.
    *
    * @return    MoldProject
    **/
   //##end getMoldProject%4C875CC600CCg.doc

   public MoldProject getMoldProject() {
      //##begin getMoldProject%4C875CC600CCg.body preserve=no

      return (MoldProject)getRoleAObject();
      //##end getMoldProject%4C875CC600CCg.body
   }

   //##begin setMoldProject%4C875CC600CCs.doc preserve=no
   /**
    * Sets the object for the association that plays role: MOLD_PROJECT_ROLE.
    *
    * @param     theMoldProject
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setMoldProject%4C875CC600CCs.doc

   public void setMoldProject( MoldProject theMoldProject )
            throws WTPropertyVetoException {
      //##begin setMoldProject%4C875CC600CCs.body preserve=no

      setRoleAObject( (MoldProject) theMoldProject );
      //##end setMoldProject%4C875CC600CCs.body
   }

   //##begin getMoldItemInfo%4C875CC600CEg.doc preserve=no
   /**
    * Gets the object for the association that plays role: MOLD_ITEM_INFO_ROLE.
    *
    * @return    MoldItemInfo
    **/
   //##end getMoldItemInfo%4C875CC600CEg.doc

   public MoldItemInfo getMoldItemInfo() {
      //##begin getMoldItemInfo%4C875CC600CEg.body preserve=no

      return (MoldItemInfo)getRoleBObject();
      //##end getMoldItemInfo%4C875CC600CEg.body
   }

   //##begin setMoldItemInfo%4C875CC600CEs.doc preserve=no
   /**
    * Sets the object for the association that plays role: MOLD_ITEM_INFO_ROLE.
    *
    * @param     theMoldItemInfo
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setMoldItemInfo%4C875CC600CEs.doc

   public void setMoldItemInfo( MoldItemInfo theMoldItemInfo )
            throws WTPropertyVetoException {
      //##begin setMoldItemInfo%4C875CC600CEs.body preserve=no

      setRoleBObject( (MoldItemInfo) theMoldItemInfo );
      //##end setMoldItemInfo%4C875CC600CEs.body
   }

   //##begin getConceptualClassname%getConceptualClassnameg.doc preserve=no
   /**
    * Returns the conceptual (modeled) name for the class.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @deprecated
    *
    * @return    String
    **/
   //##end getConceptualClassname%getConceptualClassnameg.doc

   public String getConceptualClassname() {
      //##begin getConceptualClassname%getConceptualClassnameg.body preserve=no

      return CLASSNAME;
      //##end getConceptualClassname%getConceptualClassnameg.body
   }

   //##begin newTheMoldProjectTheMoldItemInfo%newTheMoldProjectTheMoldItemInfof.doc preserve=no
   /**
    * Default factory for the class.
    *
    * @param     theMoldProject
    * @param     theMoldItemInfo
    * @return    TheMoldProjectTheMoldItemInfo
    * @exception wt.util.WTException
    **/
   //##end newTheMoldProjectTheMoldItemInfo%newTheMoldProjectTheMoldItemInfof.doc

   public static TheMoldProjectTheMoldItemInfo newTheMoldProjectTheMoldItemInfo( MoldProject theMoldProject, MoldItemInfo theMoldItemInfo )
            throws WTException {
      //##begin newTheMoldProjectTheMoldItemInfo%newTheMoldProjectTheMoldItemInfof.body preserve=no

      TheMoldProjectTheMoldItemInfo instance = new TheMoldProjectTheMoldItemInfo();
      instance.initialize( theMoldProject, theMoldItemInfo );
      return instance;
      //##end newTheMoldProjectTheMoldItemInfo%newTheMoldProjectTheMoldItemInfof.body
   }

   //##begin readVersion2454203546147727912L%readVersion2454203546147727912L.doc preserve=no
   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   //##end readVersion2454203546147727912L%readVersion2454203546147727912L.doc

   private boolean readVersion2454203546147727912L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {
      //##begin readVersion2454203546147727912L%readVersion2454203546147727912L.body preserve=maybe

      if ( !superDone )                                             // if not doing backward compatibility
         super.readExternal( input );                               // handle super class


      return true;
      //##end readVersion2454203546147727912L%readVersion2454203546147727912L.body
   }

   //##begin equals%equals.doc preserve=no
   /**
    * Indicates whether the given object is equal to this object from a
    * persistence perspective, by comparing the two objects <code>ObjectIdentifier</code>s.
    * Changed or stale copies are still considered equal by this method.
    * Delegates to {@link wt.fc.PersistenceHelper#equals(Persistable,Object)}.
    * <p>
    * <b>Warning:</b> Certain core Windchill operations may depend upon
    * <code>equals</code> being <code>ObjectIdentifier</code>-based. Changes
    * to the default implementation should be done with care, if at all.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     obj
    * @return    boolean
    **/
   //##end equals%equals.doc

   public boolean equals( Object obj ) {
      //##begin equals%equals.body preserve=no

      // WARNING: Do not change
      return wt.fc.PersistenceHelper.equals(this,obj);
      //##end equals%equals.body
   }

   //##begin hashCode%hashCode.doc preserve=no
   /**
    * Returns a hash code for this object based upon its <code>ObjectIdentifier</code>.
    * Delegates to {@link wt.fc.PersistenceHelper#hashCode(Persistable)}.
    * <p>
    * <b>Warning:</b> Certain core Windchill operations may depend upon
    * <code>hashCode</code> being <code>ObjectIdentifier-based</code>. Changes
    * to the default implementation should be done with care, if at all.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @return    int
    **/
   //##end hashCode%hashCode.doc

   public int hashCode() {
      //##begin hashCode%hashCode.body preserve=no

      // WARNING: Do not change
      return wt.fc.PersistenceHelper.hashCode(this);
      //##end hashCode%hashCode.body
   }

   //##begin user.operations preserve=yes
   //##end user.operations
}
