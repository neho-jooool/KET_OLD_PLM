// Generated ProjectDependenceLink%4C88384F013C: ? 09/28/10 08:32:42
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

import e3ps.project.E3PSProject;
import e3ps.project.outputtype.OEMProjectType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

//##begin user.imports preserve=yes
import wt.fc.ForeignKeyLink;  // Preserved unmodeled dependency
//##end user.imports

//##begin version.uid preserve=yes
/* version                                    uid
   X-12-M050                                = 2454203546147727912L
*/
//##end version.uid

//##begin ProjectDependenceLink%4C88384F013C.doc preserve=no
/**
 *
 * <p>
 * Use the <code>newProjectDependenceLink</code> static factory method(s),
 * not the <code>ProjectDependenceLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/
//##end ProjectDependenceLink%4C88384F013C.doc

public class ProjectDependenceLink extends ObjectToObjectLink implements Externalizable {


   // --- Attribute Section ---


   private static final String RESOURCE = "e3ps.project.projectResource";
   private static final String CLASSNAME = ProjectDependenceLink.class.getName();

   //##begin DEPENDENCE_ROLE%DEPENDENCE_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end DEPENDENCE_ROLE%DEPENDENCE_ROLE.doc
   public static final String DEPENDENCE_ROLE = "dependence";


   //##begin PROJECT_ROLE%PROJECT_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PROJECT_ROLE%PROJECT_ROLE.doc
   public static final String PROJECT_ROLE = "project";

   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = 2538346186404157511L;
   protected static final long OLD_FORMAT_VERSION_UID = -8857738583588960794L;

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

   protected boolean readVersion( ProjectDependenceLink thisObject, ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone )
            throws IOException, ClassNotFoundException {
      //##begin readVersion%readVersion.body preserve=no

      boolean success = true;

      if ( readSerialVersionUID == 2538346186404157511L )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
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

   //##begin getDependence%4C88384F03DAg.doc preserve=no
   /**
    * Gets the object for the association that plays role: DEPENDENCE_ROLE.
    *
    * @return    OEMProjectType
    **/
   //##end getDependence%4C88384F03DAg.doc

   public OEMProjectType getDependence() {
      //##begin getDependence%4C88384F03DAg.body preserve=no

      return (OEMProjectType)getRoleAObject();
      //##end getDependence%4C88384F03DAg.body
   }

   //##begin setDependence%4C88384F03DAs.doc preserve=no
   /**
    * Sets the object for the association that plays role: DEPENDENCE_ROLE.
    *
    * @param     dependence
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setDependence%4C88384F03DAs.doc

   public void setDependence( OEMProjectType dependence )
            throws WTPropertyVetoException {
      //##begin setDependence%4C88384F03DAs.body preserve=no

      setRoleAObject( (OEMProjectType) dependence );
      //##end setDependence%4C88384F03DAs.body
   }

   //##begin getProject%4C88384F03E4g.doc preserve=no
   /**
    * Gets the object for the association that plays role: PROJECT_ROLE.
    *
    * @return    E3PSProject
    **/
   //##end getProject%4C88384F03E4g.doc

   public E3PSProject getProject() {
      //##begin getProject%4C88384F03E4g.body preserve=no

      return (E3PSProject)getRoleBObject();
      //##end getProject%4C88384F03E4g.body
   }

   //##begin setProject%4C88384F03E4s.doc preserve=no
   /**
    * Sets the object for the association that plays role: PROJECT_ROLE.
    *
    * @param     project
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setProject%4C88384F03E4s.doc

   public void setProject( E3PSProject project )
            throws WTPropertyVetoException {
      //##begin setProject%4C88384F03E4s.body preserve=no

      setRoleBObject( (E3PSProject) project );
      //##end setProject%4C88384F03E4s.body
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

   //##begin newProjectDependenceLink%newProjectDependenceLinkf.doc preserve=no
   /**
    * Default factory for the class.
    *
    * @param     dependence
    * @param     project
    * @return    ProjectDependenceLink
    * @exception wt.util.WTException
    **/
   //##end newProjectDependenceLink%newProjectDependenceLinkf.doc

   public static ProjectDependenceLink newProjectDependenceLink( OEMProjectType dependence, E3PSProject project )
            throws WTException {
      //##begin newProjectDependenceLink%newProjectDependenceLinkf.body preserve=no

      ProjectDependenceLink instance = new ProjectDependenceLink();
      instance.initialize( dependence, project );
      return instance;
      //##end newProjectDependenceLink%newProjectDependenceLinkf.body
   }

   //##begin readVersion2538346186404157511L%readVersion2538346186404157511L.doc preserve=no
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
   //##end readVersion2538346186404157511L%readVersion2538346186404157511L.doc

   private boolean readVersion2538346186404157511L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {
      //##begin readVersion2538346186404157511L%readVersion2538346186404157511L.body preserve=maybe

      if ( !superDone )                                             // if not doing backward compatibility
         super.readExternal( input );                               // handle super class


      return true;
      //##end readVersion2538346186404157511L%readVersion2538346186404157511L.body
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
