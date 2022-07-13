// Generated OutputGWDocument%4AEA4E66007A: ? 09/16/10 18:28:45
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

import e3ps.project.ProjectOutput;
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

//##begin user.imports preserve=yes
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
//##end user.imports

//##begin OutputGWDocument%4AEA4E66007A.doc preserve=no
/**
 *
 * <p>
 * Use the <code>newOutputGWDocument</code> static factory method(s), not
 * the <code>OutputGWDocument</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/
//##end OutputGWDocument%4AEA4E66007A.doc

public class OutputGWDocument implements Persistable, Externalizable {


   // --- Attribute Section ---


   private static final String RESOURCE = "e3ps.project.projectResource";
   private static final String CLASSNAME = OutputGWDocument.class.getName();

   //##begin GW_DOC_TITLE%GW_DOC_TITLE.doc preserve=no
   /**
    * Label for the attribute; GW ?? ??
    **/
   //##end GW_DOC_TITLE%GW_DOC_TITLE.doc
   public static final String GW_DOC_TITLE = "gwDocTitle";

   private static int GW_DOC_TITLE_UPPER_LIMIT = -1;
   private String gwDocTitle;

   //##begin GW_DOC_NO%GW_DOC_NO.doc preserve=no
   /**
    * Label for the attribute; GW ?? ??
    **/
   //##end GW_DOC_NO%GW_DOC_NO.doc
   public static final String GW_DOC_NO = "gwDocNo";

   private static int GW_DOC_NO_UPPER_LIMIT = -1;
   private String gwDocNo;

   //##begin GW_DOC_CREATOR%GW_DOC_CREATOR.doc preserve=no
   /**
    * Label for the attribute; GW ?? ???
    **/
   //##end GW_DOC_CREATOR%GW_DOC_CREATOR.doc
   public static final String GW_DOC_CREATOR = "gwDocCreator";

   private static int GW_DOC_CREATOR_UPPER_LIMIT = -1;
   private String gwDocCreator;

   //##begin GW_DOC_CREATE_DATE%GW_DOC_CREATE_DATE.doc preserve=no
   /**
    * Label for the attribute; GW ?? ????
    **/
   //##end GW_DOC_CREATE_DATE%GW_DOC_CREATE_DATE.doc
   public static final String GW_DOC_CREATE_DATE = "gwDocCreateDate";

   private static int GW_DOC_CREATE_DATE_UPPER_LIMIT = -1;
   private String gwDocCreateDate;

   //##begin GW_DOC_PATH%GW_DOC_PATH.doc preserve=no
   /**
    * Label for the attribute; GW ?? ?? URL
    **/
   //##end GW_DOC_PATH%GW_DOC_PATH.doc
   public static final String GW_DOC_PATH = "gwDocPath";

   private static int GW_DOC_PATH_UPPER_LIMIT = -1;
   private String gwDocPath;

   //##begin GW_DOC_ID%GW_DOC_ID.doc preserve=no
   /**
    * Label for the attribute; GW ?? Unique ID
    **/
   //##end GW_DOC_ID%GW_DOC_ID.doc
   public static final String GW_DOC_ID = "gwDocId";

   private static int GW_DOC_ID_UPPER_LIMIT = -1;
   private String gwDocId;

   //##begin PROJECT_OUTPUT%PROJECT_OUTPUT.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PROJECT_OUTPUT%PROJECT_OUTPUT.doc
   public static final String PROJECT_OUTPUT = "projectOutput";


   //##begin PROJECT_OUTPUT_REFERENCE%PROJECT_OUTPUT_REFERENCE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PROJECT_OUTPUT_REFERENCE%PROJECT_OUTPUT_REFERENCE.doc
   public static final String PROJECT_OUTPUT_REFERENCE = "projectOutputReference";

   private ObjectReference projectOutputReference;
   private PersistInfo thePersistInfo = new PersistInfo();
   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = -4410563055593160748L;

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

      output.writeObject( gwDocCreateDate );
      output.writeObject( gwDocCreator );
      output.writeObject( gwDocId );
      output.writeObject( gwDocNo );
      output.writeObject( gwDocPath );
      output.writeObject( gwDocTitle );
      output.writeObject( projectOutputReference );
      output.writeObject( thePersistInfo );
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
         gwDocCreateDate = (String)input.readObject();
         gwDocCreator = (String)input.readObject();
         gwDocId = (String)input.readObject();
         gwDocNo = (String)input.readObject();
         gwDocPath = (String)input.readObject();
         gwDocTitle = (String)input.readObject();
         projectOutputReference = (ObjectReference)input.readObject();
         thePersistInfo = (PersistInfo)input.readObject();
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
      output.setString( "gwDocTitle", gwDocTitle );
      output.setString( "gwDocNo", gwDocNo );
      output.setString( "gwDocCreator", gwDocCreator );
      output.setString( "gwDocCreateDate", gwDocCreateDate );
      output.setString( "gwDocPath", gwDocPath );
      output.setString( "gwDocId", gwDocId );
      output.writeObject( "projectOutputReference", projectOutputReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
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
      gwDocTitle = input.getString( "gwDocTitle" );
      gwDocNo = input.getString( "gwDocNo" );
      gwDocCreator = input.getString( "gwDocCreator" );
      gwDocCreateDate = input.getString( "gwDocCreateDate" );
      gwDocPath = input.getString( "gwDocPath" );
      gwDocId = input.getString( "gwDocId" );
      projectOutputReference = (wt.fc.ObjectReference)input.readObject( "projectOutputReference", projectOutputReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo)input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
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

   //##begin toString%toString.doc preserve=no
   /**
    * Returns the conceptual (modeled) name for the class.
    *
    * @return    String
    **/
   //##end toString%toString.doc

   public String toString() {
      //##begin toString%toString.body preserve=no

      return getConceptualClassname();
      //##end toString%toString.body
   }

   //##begin getClassInfo%getClassInfog.doc preserve=no
   /**
    * Returns the ClassInfo object for this class.
    *
    * @return    ClassInfo
    * @exception wt.introspection.WTIntrospectionException
    **/
   //##end getClassInfo%getClassInfog.doc

   public ClassInfo getClassInfo()
            throws WTIntrospectionException {
      //##begin getClassInfo%getClassInfog.body preserve=no

      return WTIntrospector.getClassInfo( getConceptualClassname() );
      //##end getClassInfo%getClassInfog.body
   }

   //##begin getGwDocTitle%4AEA4F9A0387g.doc preserve=no
   /**
    * Gets the value of the attribute: GW_DOC_TITLE.
    * GW ?? ??
    *
    * @return    String
    **/
   //##end getGwDocTitle%4AEA4F9A0387g.doc

   public String getGwDocTitle() {
      //##begin getGwDocTitle%4AEA4F9A0387g.body preserve=no

      return gwDocTitle;
      //##end getGwDocTitle%4AEA4F9A0387g.body
   }

   //##begin setGwDocTitle%4AEA4F9A0387s.doc preserve=no
   /**
    * Sets the value of the attribute: GW_DOC_TITLE.
    * GW ?? ??
    *
    * @param     a_GwDocTitle
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setGwDocTitle%4AEA4F9A0387s.doc

   public void setGwDocTitle( String a_GwDocTitle )
            throws WTPropertyVetoException {
      //##begin setGwDocTitle%4AEA4F9A0387s.body preserve=no

      gwDocTitleValidate( a_GwDocTitle );   // throws exception if not valid
      gwDocTitle = a_GwDocTitle;
      //##end setGwDocTitle%4AEA4F9A0387s.body
   }

   //##begin gwDocTitleValidate%4AEA4F9A0387.doc preserve=no
   /**
    * @param     a_GwDocTitle
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end gwDocTitleValidate%4AEA4F9A0387.doc

   private void gwDocTitleValidate( String a_GwDocTitle )
            throws WTPropertyVetoException {
      if ( GW_DOC_TITLE_UPPER_LIMIT < 1 ) {
         try { GW_DOC_TITLE_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "gwDocTitle" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { GW_DOC_TITLE_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_GwDocTitle != null && !wt.fc.PersistenceHelper.checkStoredLength( a_GwDocTitle, GW_DOC_TITLE_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "gwDocTitle" ), String.valueOf( Math.min ( GW_DOC_TITLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "gwDocTitle", gwDocTitle, a_GwDocTitle ) );
      }
   }

   //##begin getGwDocNo%4AEA4FA100AFg.doc preserve=no
   /**
    * Gets the value of the attribute: GW_DOC_NO.
    * GW ?? ??
    *
    * @return    String
    **/
   //##end getGwDocNo%4AEA4FA100AFg.doc

   public String getGwDocNo() {
      //##begin getGwDocNo%4AEA4FA100AFg.body preserve=no

      return gwDocNo;
      //##end getGwDocNo%4AEA4FA100AFg.body
   }

   //##begin setGwDocNo%4AEA4FA100AFs.doc preserve=no
   /**
    * Sets the value of the attribute: GW_DOC_NO.
    * GW ?? ??
    *
    * @param     a_GwDocNo
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setGwDocNo%4AEA4FA100AFs.doc

   public void setGwDocNo( String a_GwDocNo )
            throws WTPropertyVetoException {
      //##begin setGwDocNo%4AEA4FA100AFs.body preserve=no

      gwDocNoValidate( a_GwDocNo );   // throws exception if not valid
      gwDocNo = a_GwDocNo;
      //##end setGwDocNo%4AEA4FA100AFs.body
   }

   //##begin gwDocNoValidate%4AEA4FA100AF.doc preserve=no
   /**
    * @param     a_GwDocNo
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end gwDocNoValidate%4AEA4FA100AF.doc

   private void gwDocNoValidate( String a_GwDocNo )
            throws WTPropertyVetoException {
      if ( GW_DOC_NO_UPPER_LIMIT < 1 ) {
         try { GW_DOC_NO_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "gwDocNo" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { GW_DOC_NO_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_GwDocNo != null && !wt.fc.PersistenceHelper.checkStoredLength( a_GwDocNo, GW_DOC_NO_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "gwDocNo" ), String.valueOf( Math.min ( GW_DOC_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "gwDocNo", gwDocNo, a_GwDocNo ) );
      }
   }

   //##begin getGwDocCreator%4AEA51D60233g.doc preserve=no
   /**
    * Gets the value of the attribute: GW_DOC_CREATOR.
    * GW ?? ???
    *
    * @return    String
    **/
   //##end getGwDocCreator%4AEA51D60233g.doc

   public String getGwDocCreator() {
      //##begin getGwDocCreator%4AEA51D60233g.body preserve=no

      return gwDocCreator;
      //##end getGwDocCreator%4AEA51D60233g.body
   }

   //##begin setGwDocCreator%4AEA51D60233s.doc preserve=no
   /**
    * Sets the value of the attribute: GW_DOC_CREATOR.
    * GW ?? ???
    *
    * @param     a_GwDocCreator
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setGwDocCreator%4AEA51D60233s.doc

   public void setGwDocCreator( String a_GwDocCreator )
            throws WTPropertyVetoException {
      //##begin setGwDocCreator%4AEA51D60233s.body preserve=no

      gwDocCreatorValidate( a_GwDocCreator );   // throws exception if not valid
      gwDocCreator = a_GwDocCreator;
      //##end setGwDocCreator%4AEA51D60233s.body
   }

   //##begin gwDocCreatorValidate%4AEA51D60233.doc preserve=no
   /**
    * @param     a_GwDocCreator
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end gwDocCreatorValidate%4AEA51D60233.doc

   private void gwDocCreatorValidate( String a_GwDocCreator )
            throws WTPropertyVetoException {
      if ( GW_DOC_CREATOR_UPPER_LIMIT < 1 ) {
         try { GW_DOC_CREATOR_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "gwDocCreator" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { GW_DOC_CREATOR_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_GwDocCreator != null && !wt.fc.PersistenceHelper.checkStoredLength( a_GwDocCreator, GW_DOC_CREATOR_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "gwDocCreator" ), String.valueOf( Math.min ( GW_DOC_CREATOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "gwDocCreator", gwDocCreator, a_GwDocCreator ) );
      }
   }

   //##begin getGwDocCreateDate%4AEA51DD03A2g.doc preserve=no
   /**
    * Gets the value of the attribute: GW_DOC_CREATE_DATE.
    * GW ?? ????
    *
    * @return    String
    **/
   //##end getGwDocCreateDate%4AEA51DD03A2g.doc

   public String getGwDocCreateDate() {
      //##begin getGwDocCreateDate%4AEA51DD03A2g.body preserve=no

      return gwDocCreateDate;
      //##end getGwDocCreateDate%4AEA51DD03A2g.body
   }

   //##begin setGwDocCreateDate%4AEA51DD03A2s.doc preserve=no
   /**
    * Sets the value of the attribute: GW_DOC_CREATE_DATE.
    * GW ?? ????
    *
    * @param     a_GwDocCreateDate
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setGwDocCreateDate%4AEA51DD03A2s.doc

   public void setGwDocCreateDate( String a_GwDocCreateDate )
            throws WTPropertyVetoException {
      //##begin setGwDocCreateDate%4AEA51DD03A2s.body preserve=no

      gwDocCreateDateValidate( a_GwDocCreateDate );   // throws exception if not valid
      gwDocCreateDate = a_GwDocCreateDate;
      //##end setGwDocCreateDate%4AEA51DD03A2s.body
   }

   //##begin gwDocCreateDateValidate%4AEA51DD03A2.doc preserve=no
   /**
    * @param     a_GwDocCreateDate
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end gwDocCreateDateValidate%4AEA51DD03A2.doc

   private void gwDocCreateDateValidate( String a_GwDocCreateDate )
            throws WTPropertyVetoException {
      if ( GW_DOC_CREATE_DATE_UPPER_LIMIT < 1 ) {
         try { GW_DOC_CREATE_DATE_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "gwDocCreateDate" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { GW_DOC_CREATE_DATE_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_GwDocCreateDate != null && !wt.fc.PersistenceHelper.checkStoredLength( a_GwDocCreateDate, GW_DOC_CREATE_DATE_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "gwDocCreateDate" ), String.valueOf( Math.min ( GW_DOC_CREATE_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "gwDocCreateDate", gwDocCreateDate, a_GwDocCreateDate ) );
      }
   }

   //##begin getGwDocPath%4AEA4FBF02EDg.doc preserve=no
   /**
    * Gets the value of the attribute: GW_DOC_PATH.
    * GW ?? ?? URL
    *
    * @return    String
    **/
   //##end getGwDocPath%4AEA4FBF02EDg.doc

   public String getGwDocPath() {
      //##begin getGwDocPath%4AEA4FBF02EDg.body preserve=no

      return gwDocPath;
      //##end getGwDocPath%4AEA4FBF02EDg.body
   }

   //##begin setGwDocPath%4AEA4FBF02EDs.doc preserve=no
   /**
    * Sets the value of the attribute: GW_DOC_PATH.
    * GW ?? ?? URL
    *
    * @param     a_GwDocPath
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setGwDocPath%4AEA4FBF02EDs.doc

   public void setGwDocPath( String a_GwDocPath )
            throws WTPropertyVetoException {
      //##begin setGwDocPath%4AEA4FBF02EDs.body preserve=no

      gwDocPathValidate( a_GwDocPath );   // throws exception if not valid
      gwDocPath = a_GwDocPath;
      //##end setGwDocPath%4AEA4FBF02EDs.body
   }

   //##begin gwDocPathValidate%4AEA4FBF02ED.doc preserve=no
   /**
    * @param     a_GwDocPath
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end gwDocPathValidate%4AEA4FBF02ED.doc

   private void gwDocPathValidate( String a_GwDocPath )
            throws WTPropertyVetoException {
      if ( GW_DOC_PATH_UPPER_LIMIT < 1 ) {
         try { GW_DOC_PATH_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "gwDocPath" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { GW_DOC_PATH_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_GwDocPath != null && !wt.fc.PersistenceHelper.checkStoredLength( a_GwDocPath, GW_DOC_PATH_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "gwDocPath" ), String.valueOf( Math.min ( GW_DOC_PATH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "gwDocPath", gwDocPath, a_GwDocPath ) );
      }
   }

   //##begin getGwDocId%4AEA4FC700DDg.doc preserve=no
   /**
    * Gets the value of the attribute: GW_DOC_ID.
    * GW ?? Unique ID
    *
    * @return    String
    **/
   //##end getGwDocId%4AEA4FC700DDg.doc

   public String getGwDocId() {
      //##begin getGwDocId%4AEA4FC700DDg.body preserve=no

      return gwDocId;
      //##end getGwDocId%4AEA4FC700DDg.body
   }

   //##begin setGwDocId%4AEA4FC700DDs.doc preserve=no
   /**
    * Sets the value of the attribute: GW_DOC_ID.
    * GW ?? Unique ID
    *
    * @param     a_GwDocId
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setGwDocId%4AEA4FC700DDs.doc

   public void setGwDocId( String a_GwDocId )
            throws WTPropertyVetoException {
      //##begin setGwDocId%4AEA4FC700DDs.body preserve=no

      gwDocIdValidate( a_GwDocId );   // throws exception if not valid
      gwDocId = a_GwDocId;
      //##end setGwDocId%4AEA4FC700DDs.body
   }

   //##begin gwDocIdValidate%4AEA4FC700DD.doc preserve=no
   /**
    * @param     a_GwDocId
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end gwDocIdValidate%4AEA4FC700DD.doc

   private void gwDocIdValidate( String a_GwDocId )
            throws WTPropertyVetoException {
      if ( GW_DOC_ID_UPPER_LIMIT < 1 ) {
         try { GW_DOC_ID_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "gwDocId" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { GW_DOC_ID_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_GwDocId != null && !wt.fc.PersistenceHelper.checkStoredLength( a_GwDocId, GW_DOC_ID_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "gwDocId" ), String.valueOf( Math.min ( GW_DOC_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "gwDocId", gwDocId, a_GwDocId ) );
      }
   }

   //##begin getProjectOutput%4AEA52B2026Dd.doc preserve=no
   /**
    * Gets the object for the association that plays role: PROJECT_OUTPUT.
    *
    * @return    ProjectOutput
    **/
   //##end getProjectOutput%4AEA52B2026Dd.doc

   public ProjectOutput getProjectOutput() {
      //##begin getProjectOutput%4AEA52B2026Dd.body preserve=no

      if ( projectOutputReference == null )
         return null;

      return (ProjectOutput)projectOutputReference.getObject();
      //##end getProjectOutput%4AEA52B2026Dd.body
   }

   //##begin setProjectOutput%4AEA52B2026Dp.doc preserve=no
   /**
    * Sets the object for the association that plays role: PROJECT_OUTPUT.
    *
    * @param     a_ProjectOutput
    * @exception wt.util.WTPropertyVetoException
    * @exception wt.util.WTException
    **/
   //##end setProjectOutput%4AEA52B2026Dp.doc

   public void setProjectOutput( ProjectOutput a_ProjectOutput )
            throws WTPropertyVetoException, WTException {
      //##begin setProjectOutput%4AEA52B2026Dp.body preserve=no

      setProjectOutputReference( a_ProjectOutput == null ? null : ObjectReference.newObjectReference( a_ProjectOutput ) );
      //##end setProjectOutput%4AEA52B2026Dp.body
   }

   //##begin getProjectOutputReference%4AEA52B2026Dg.doc preserve=no
   /**
    * Gets the value of the attribute: PROJECT_OUTPUT_REFERENCE.
    *
    * @return    ObjectReference
    **/
   //##end getProjectOutputReference%4AEA52B2026Dg.doc

   public ObjectReference getProjectOutputReference() {
      //##begin getProjectOutputReference%4AEA52B2026Dg.body preserve=no

      return projectOutputReference;
      //##end getProjectOutputReference%4AEA52B2026Dg.body
   }

   //##begin setProjectOutputReference%4AEA52B2026Ds.doc preserve=no
   /**
    * Sets the value of the attribute: PROJECT_OUTPUT_REFERENCE.
    *
    * @param     a_ProjectOutputReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setProjectOutputReference%4AEA52B2026Ds.doc

   public void setProjectOutputReference( ObjectReference a_ProjectOutputReference )
            throws WTPropertyVetoException {
      //##begin setProjectOutputReference%4AEA52B2026Ds.body preserve=no

      projectOutputReferenceValidate( a_ProjectOutputReference );   // throws exception if not valid
      projectOutputReference = a_ProjectOutputReference;
      //##end setProjectOutputReference%4AEA52B2026Ds.body
   }

   //##begin projectOutputReferenceValidate%4AEA52B2026D.doc preserve=no
   /**
    * @param     a_ProjectOutputReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end projectOutputReferenceValidate%4AEA52B2026D.doc

   private void projectOutputReferenceValidate( ObjectReference a_ProjectOutputReference )
            throws WTPropertyVetoException {
      if ( a_ProjectOutputReference == null || a_ProjectOutputReference.getReferencedClass() == null ) { // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "projectOutputReference" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "projectOutputReference", projectOutputReference, a_ProjectOutputReference ) );
      }
      if ( a_ProjectOutputReference != null && a_ProjectOutputReference.getReferencedClass() != null &&  // type check
               !( e3ps.project.ProjectOutput.class.isAssignableFrom( a_ProjectOutputReference.getReferencedClass() ) ) ) {
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "projectOutputReference" ), "ObjectReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "projectOutputReference", projectOutputReference, a_ProjectOutputReference ) );
      }
   }

   //##begin newOutputGWDocument%newOutputGWDocumentf.doc preserve=no
   /**
    * Default factory for the class.
    *
    * @return    OutputGWDocument
    * @exception wt.util.WTException
    **/
   //##end newOutputGWDocument%newOutputGWDocumentf.doc

   public static OutputGWDocument newOutputGWDocument()
            throws WTException {
      //##begin newOutputGWDocument%newOutputGWDocumentf.body preserve=no

      OutputGWDocument instance = new OutputGWDocument();
      instance.initialize();
      return instance;
      //##end newOutputGWDocument%newOutputGWDocumentf.body
   }

   //##begin initialize%initialize.doc preserve=no
   /**
    * Supports initialization, following construction of an instance.  Invoked
    * by "new" factory having the same signature.
    *
    * @exception wt.util.WTException
    **/
   //##end initialize%initialize.doc

   protected void initialize()
            throws WTException {
      //##begin initialize%initialize.body preserve=yes

      //##end initialize%initialize.body
   }

   //##begin getIdentity%357885260399g.doc preserve=no
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
   //##end getIdentity%357885260399g.doc

   public String getIdentity() {
      //##begin getIdentity%357885260399g.body preserve=yes

      return null;
      //##end getIdentity%357885260399g.body
   }

   //##begin getType%357885340109g.doc preserve=no
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
   //##end getType%357885340109g.doc

   public String getType() {
      //##begin getType%357885340109g.body preserve=yes

      return null;
      //##end getType%357885340109g.body
   }

   //##begin checkAttributes%3458CE2E02EE.doc preserve=no
   /**
    * Validate the values of this Persistable object's attributes.
    *
    * @exception wt.fc.InvalidAttributeException
    **/
   //##end checkAttributes%3458CE2E02EE.doc

   public void checkAttributes()
            throws InvalidAttributeException {
      //##begin checkAttributes%3458CE2E02EE.body preserve=yes

      //##end checkAttributes%3458CE2E02EE.body
   }

   //##begin getPersistInfo%3432AAAF00EAg.doc preserve=no
   /**
    * Gets the object for the association that plays role: PERSIST_INFO.
    *
    * @return    PersistInfo
    **/
   //##end getPersistInfo%3432AAAF00EAg.doc

   public PersistInfo getPersistInfo() {
      //##begin getPersistInfo%3432AAAF00EAg.body preserve=no

      return thePersistInfo;
      //##end getPersistInfo%3432AAAF00EAg.body
   }

   //##begin setPersistInfo%3432AAAF00EAs.doc preserve=no
   /**
    * Sets the object for the association that plays role: PERSIST_INFO.
    *
    * @param     a_PersistInfo
    **/
   //##end setPersistInfo%3432AAAF00EAs.doc

   public void setPersistInfo( PersistInfo a_PersistInfo ) {
      //##begin setPersistInfo%3432AAAF00EAs.body preserve=no

      thePersistInfo = a_PersistInfo;
      //##end setPersistInfo%3432AAAF00EAs.body
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
