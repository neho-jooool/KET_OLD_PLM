// Generated ProjectManagerWTUserLink%4B134DB800A3: ? 09/16/10 18:28:45
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

import e3ps.project.ProjectManager;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.fc.ObjectToObjectLink;
import wt.org.WTUser;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

//##begin user.imports preserve=yes
//##end user.imports

//##begin ProjectManagerWTUserLink%4B134DB800A3.doc preserve=no
/**
 *
 * <p>
 * Use the <code>newProjectManagerWTUserLink</code> static factory method(s),
 * not the <code>ProjectManagerWTUserLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/
//##end ProjectManagerWTUserLink%4B134DB800A3.doc

public class ProjectManagerWTUserLink extends ObjectToObjectLink implements Externalizable {


   // --- Attribute Section ---


   private static final String RESOURCE = "e3ps.project.projectResource";
   private static final String CLASSNAME = ProjectManagerWTUserLink.class.getName();

   //##begin WTUSER_ROLE%WTUSER_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end WTUSER_ROLE%WTUSER_ROLE.doc
   public static final String WTUSER_ROLE = "wtuser";


   //##begin MASTER_ROLE%MASTER_ROLE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end MASTER_ROLE%MASTER_ROLE.doc
   public static final String MASTER_ROLE = "master";


   //##begin PW_DESC%PW_DESC.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PW_DESC%PW_DESC.doc
   public static final String PW_DESC = "pwDesc";

   private static int PW_DESC_UPPER_LIMIT = -1;
   private String pwDesc;

   //##begin PW_MSG%PW_MSG.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PW_MSG%PW_MSG.doc
   public static final String PW_MSG = "pwMsg";

   private static int PW_MSG_UPPER_LIMIT = -1;
   private String pwMsg;

   //##begin NAME%NAME.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end NAME%NAME.doc
   public static final String NAME = "name";

   private static int NAME_UPPER_LIMIT = -1;
   private String name;

   //##begin VALIDITY_DATE%VALIDITY_DATE.doc preserve=no
   /**
    * Label for the attribute; ???
    **/
   //##end VALIDITY_DATE%VALIDITY_DATE.doc
   public static final String VALIDITY_DATE = "validityDate";

   private Timestamp validityDate;
   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = -2623576536122629924L;

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

      output.writeObject( name );
      output.writeObject( pwDesc );
      output.writeObject( pwMsg );
      output.writeObject( validityDate );
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

      long readSerialVersionUID = input.readLong();                // consume UID

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID ) {  // if current version UID
         super.readExternal( input );                               // handle super class

         name = (String)input.readObject();
         pwDesc = (String)input.readObject();
         pwMsg = (String)input.readObject();
         validityDate = (Timestamp)input.readObject();
      }
      else
         throw new java.io.InvalidClassException( CLASSNAME, "Local class not compatible:"
                           + " stream classdesc externalizationVersionUID=" + readSerialVersionUID 
                           + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID );
      //##end readExternal%readExternal.body
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

      output.setString( "pwDesc", pwDesc );
      output.setString( "pwMsg", pwMsg );
      output.setString( "name", name );
      output.setTimestamp( "validityDate", validityDate );
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

      pwDesc = input.getString( "pwDesc" );
      pwMsg = input.getString( "pwMsg" );
      name = input.getString( "name" );
      validityDate = input.getTimestamp( "validityDate" );
   }

   //##begin getWtuser%4B134CA20276g.doc preserve=no
   /**
    * Gets the object for the association that plays role: WTUSER_ROLE.
    *
    * @return    WTUser
    **/
   //##end getWtuser%4B134CA20276g.doc

   public WTUser getWtuser() {
      //##begin getWtuser%4B134CA20276g.body preserve=no

      return (WTUser)getRoleAObject();
      //##end getWtuser%4B134CA20276g.body
   }

   //##begin setWtuser%4B134CA20276s.doc preserve=no
   /**
    * Sets the object for the association that plays role: WTUSER_ROLE.
    *
    * @param     wtuser
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setWtuser%4B134CA20276s.doc

   public void setWtuser( WTUser wtuser )
            throws WTPropertyVetoException {
      //##begin setWtuser%4B134CA20276s.body preserve=no

      setRoleAObject( (WTUser) wtuser );
      //##end setWtuser%4B134CA20276s.body
   }

   //##begin getMaster%4B134CA2030Eg.doc preserve=no
   /**
    * Gets the object for the association that plays role: MASTER_ROLE.
    *
    * @return    ProjectManager
    **/
   //##end getMaster%4B134CA2030Eg.doc

   public ProjectManager getMaster() {
      //##begin getMaster%4B134CA2030Eg.body preserve=no

      return (ProjectManager)getRoleBObject();
      //##end getMaster%4B134CA2030Eg.body
   }

   //##begin setMaster%4B134CA2030Es.doc preserve=no
   /**
    * Sets the object for the association that plays role: MASTER_ROLE.
    *
    * @param     master
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setMaster%4B134CA2030Es.doc

   public void setMaster( ProjectManager master )
            throws WTPropertyVetoException {
      //##begin setMaster%4B134CA2030Es.body preserve=no

      setRoleBObject( (ProjectManager) master );
      //##end setMaster%4B134CA2030Es.body
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

   //##begin getPwDesc%4B134E0A01ABg.doc preserve=no
   /**
    * Gets the value of the attribute: PW_DESC.
    *
    * @return    String
    **/
   //##end getPwDesc%4B134E0A01ABg.doc

   public String getPwDesc() {
      //##begin getPwDesc%4B134E0A01ABg.body preserve=no

      return pwDesc;
      //##end getPwDesc%4B134E0A01ABg.body
   }

   //##begin setPwDesc%4B134E0A01ABs.doc preserve=no
   /**
    * Sets the value of the attribute: PW_DESC.
    *
    * @param     a_PwDesc
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setPwDesc%4B134E0A01ABs.doc

   public void setPwDesc( String a_PwDesc )
            throws WTPropertyVetoException {
      //##begin setPwDesc%4B134E0A01ABs.body preserve=no

      pwDescValidate( a_PwDesc );   // throws exception if not valid
      pwDesc = a_PwDesc;
      //##end setPwDesc%4B134E0A01ABs.body
   }

   //##begin pwDescValidate%4B134E0A01AB.doc preserve=no
   /**
    * @param     a_PwDesc
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end pwDescValidate%4B134E0A01AB.doc

   private void pwDescValidate( String a_PwDesc )
            throws WTPropertyVetoException {
      if ( PW_DESC_UPPER_LIMIT < 1 ) {
         try { PW_DESC_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "pwDesc" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { PW_DESC_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_PwDesc != null && !wt.fc.PersistenceHelper.checkStoredLength( a_PwDesc, PW_DESC_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "pwDesc" ), String.valueOf( Math.min ( PW_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "pwDesc", pwDesc, a_PwDesc ) );
      }
   }

   //##begin getPwMsg%4B134E510290g.doc preserve=no
   /**
    * Gets the value of the attribute: PW_MSG.
    *
    * @return    String
    **/
   //##end getPwMsg%4B134E510290g.doc

   public String getPwMsg() {
      //##begin getPwMsg%4B134E510290g.body preserve=no

      return pwMsg;
      //##end getPwMsg%4B134E510290g.body
   }

   //##begin setPwMsg%4B134E510290s.doc preserve=no
   /**
    * Sets the value of the attribute: PW_MSG.
    *
    * @param     a_PwMsg
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setPwMsg%4B134E510290s.doc

   public void setPwMsg( String a_PwMsg )
            throws WTPropertyVetoException {
      //##begin setPwMsg%4B134E510290s.body preserve=no

      pwMsgValidate( a_PwMsg );   // throws exception if not valid
      pwMsg = a_PwMsg;
      //##end setPwMsg%4B134E510290s.body
   }

   //##begin pwMsgValidate%4B134E510290.doc preserve=no
   /**
    * @param     a_PwMsg
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end pwMsgValidate%4B134E510290.doc

   private void pwMsgValidate( String a_PwMsg )
            throws WTPropertyVetoException {
      if ( PW_MSG_UPPER_LIMIT < 1 ) {
         try { PW_MSG_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "pwMsg" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { PW_MSG_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_PwMsg != null && !wt.fc.PersistenceHelper.checkStoredLength( a_PwMsg, PW_MSG_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "pwMsg" ), String.valueOf( Math.min ( PW_MSG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "pwMsg", pwMsg, a_PwMsg ) );
      }
   }

   //##begin getName%4B134E6C0126g.doc preserve=no
   /**
    * Gets the value of the attribute: NAME.
    *
    * @return    String
    **/
   //##end getName%4B134E6C0126g.doc

   public String getName() {
      //##begin getName%4B134E6C0126g.body preserve=no

      return name;
      //##end getName%4B134E6C0126g.body
   }

   //##begin setName%4B134E6C0126s.doc preserve=no
   /**
    * Sets the value of the attribute: NAME.
    *
    * @param     a_Name
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setName%4B134E6C0126s.doc

   public void setName( String a_Name )
            throws WTPropertyVetoException {
      //##begin setName%4B134E6C0126s.body preserve=no

      nameValidate( a_Name );   // throws exception if not valid
      name = a_Name;
      //##end setName%4B134E6C0126s.body
   }

   //##begin nameValidate%4B134E6C0126.doc preserve=no
   /**
    * @param     a_Name
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end nameValidate%4B134E6C0126.doc

   private void nameValidate( String a_Name )
            throws WTPropertyVetoException {
      if ( NAME_UPPER_LIMIT < 1 ) {
         try { NAME_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "name" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { NAME_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_Name != null && !wt.fc.PersistenceHelper.checkStoredLength( a_Name, NAME_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "name" ), String.valueOf( Math.min ( NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "name", name, a_Name ) );
      }
   }

   //##begin getValidityDate%4B134ECB0377g.doc preserve=no
   /**
    * Gets the value of the attribute: VALIDITY_DATE.
    * ???
    *
    * @return    Timestamp
    **/
   //##end getValidityDate%4B134ECB0377g.doc

   public Timestamp getValidityDate() {
      //##begin getValidityDate%4B134ECB0377g.body preserve=no

      return validityDate;
      //##end getValidityDate%4B134ECB0377g.body
   }

   //##begin setValidityDate%4B134ECB0377s.doc preserve=no
   /**
    * Sets the value of the attribute: VALIDITY_DATE.
    * ???
    *
    * @param     a_ValidityDate
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setValidityDate%4B134ECB0377s.doc

   public void setValidityDate( Timestamp a_ValidityDate )
            throws WTPropertyVetoException {
      //##begin setValidityDate%4B134ECB0377s.body preserve=no

      validityDate = a_ValidityDate;
      //##end setValidityDate%4B134ECB0377s.body
   }

   //##begin newProjectManagerWTUserLink%newProjectManagerWTUserLinkf.doc preserve=no
   /**
    * Default factory for the class.
    *
    * @param     wtuser
    * @param     master
    * @return    ProjectManagerWTUserLink
    * @exception wt.util.WTException
    **/
   //##end newProjectManagerWTUserLink%newProjectManagerWTUserLinkf.doc

   public static ProjectManagerWTUserLink newProjectManagerWTUserLink( WTUser wtuser, ProjectManager master )
            throws WTException {
      //##begin newProjectManagerWTUserLink%newProjectManagerWTUserLinkf.body preserve=no

      ProjectManagerWTUserLink instance = new ProjectManagerWTUserLink();
      instance.initialize( wtuser, master );
      return instance;
      //##end newProjectManagerWTUserLink%newProjectManagerWTUserLinkf.body
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
