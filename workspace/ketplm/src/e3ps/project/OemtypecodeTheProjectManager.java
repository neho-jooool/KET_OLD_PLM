// Generated OemtypecodeTheProjectManager%4B1CA421030F: ? 04/28/10 13:53:06
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
import e3ps.project.ProjectManager;
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
   X-12-M020                                = 2454203546147727912L
*/
//##end version.uid

//##begin OemtypecodeTheProjectManager%4B1CA421030F.doc preserve=no
/**
 *
 * <p>
 * Use the <code>newOemtypecodeTheProjectManager</code> static factory method(s),
 * not the <code>OemtypecodeTheProjectManager</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/
//##end OemtypecodeTheProjectManager%4B1CA421030F.doc

public class OemtypecodeTheProjectManager extends ForeignKeyLink implements Externalizable {


   // --- Attribute Section ---


   private static final String RESOURCE = "e3ps.project.projectResource";
   private static final String CLASSNAME = OemtypecodeTheProjectManager.class.getName();

   //##begin OEMTYPECODE_ROLE%OEMTYPECODE_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end OEMTYPECODE_ROLE%OEMTYPECODE_ROLE.doc
   public static final String OEMTYPECODE_ROLE = "oemtypecode";


   //##begin PROJECT_MANAGER_ROLE%PROJECT_MANAGER_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PROJECT_MANAGER_ROLE%PROJECT_MANAGER_ROLE.doc
   public static final String PROJECT_MANAGER_ROLE = "theProjectManager";

   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = 2454203546147727912L;
   protected static final long OLD_FORMAT_VERSION_UID = -7404770214684364714L;

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

   protected boolean readVersion( OemtypecodeTheProjectManager thisObject, ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone )
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

   //##begin getOemtypecode%4B1CA4220277g.doc preserve=no
   /**
    * Gets the object for the association that plays role: OEMTYPECODE_ROLE.
    *
    * @return    NumberCode
    **/
   //##end getOemtypecode%4B1CA4220277g.doc

   public NumberCode getOemtypecode() {
      //##begin getOemtypecode%4B1CA4220277g.body preserve=no

      return (NumberCode)getRoleAObject();
      //##end getOemtypecode%4B1CA4220277g.body
   }

   //##begin setOemtypecode%4B1CA4220277s.doc preserve=no
   /**
    * Sets the object for the association that plays role: OEMTYPECODE_ROLE.
    *
    * @param     oemtypecode
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setOemtypecode%4B1CA4220277s.doc

   public void setOemtypecode( NumberCode oemtypecode )
            throws WTPropertyVetoException {
      //##begin setOemtypecode%4B1CA4220277s.body preserve=no

      setRoleAObject( (NumberCode) oemtypecode );
      //##end setOemtypecode%4B1CA4220277s.body
   }

   //##begin getProjectManager%4B1CA422033Cg.doc preserve=no
   /**
    * Gets the object for the association that plays role: PROJECT_MANAGER_ROLE.
    *
    * @return    ProjectManager
    **/
   //##end getProjectManager%4B1CA422033Cg.doc

   public ProjectManager getProjectManager() {
      //##begin getProjectManager%4B1CA422033Cg.body preserve=no

      return (ProjectManager)getRoleBObject();
      //##end getProjectManager%4B1CA422033Cg.body
   }

   //##begin setProjectManager%4B1CA422033Cs.doc preserve=no
   /**
    * Sets the object for the association that plays role: PROJECT_MANAGER_ROLE.
    *
    * @param     theProjectManager
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setProjectManager%4B1CA422033Cs.doc

   public void setProjectManager( ProjectManager theProjectManager )
            throws WTPropertyVetoException {
      //##begin setProjectManager%4B1CA422033Cs.body preserve=no

      setRoleBObject( (ProjectManager) theProjectManager );
      //##end setProjectManager%4B1CA422033Cs.body
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

   //##begin newOemtypecodeTheProjectManager%newOemtypecodeTheProjectManagerf.doc preserve=no
   /**
    * Default factory for the class.
    *
    * @param     oemtypecode
    * @param     theProjectManager
    * @return    OemtypecodeTheProjectManager
    * @exception wt.util.WTException
    **/
   //##end newOemtypecodeTheProjectManager%newOemtypecodeTheProjectManagerf.doc

   public static OemtypecodeTheProjectManager newOemtypecodeTheProjectManager( NumberCode oemtypecode, ProjectManager theProjectManager )
            throws WTException {
      //##begin newOemtypecodeTheProjectManager%newOemtypecodeTheProjectManagerf.body preserve=no

      OemtypecodeTheProjectManager instance = new OemtypecodeTheProjectManager();
      instance.initialize( oemtypecode, theProjectManager );
      return instance;
      //##end newOemtypecodeTheProjectManager%newOemtypecodeTheProjectManagerf.body
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
