// Generated ProjectManager%4AFC079602D2: ? 09/16/10 16:52:01
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
import e3ps.project.outputtype.OEMProjectType;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import java.sql.Timestamp;
import wt.enterprise.Managed;
import wt.fc.ObjectReference;
import wt.fc.Persistable;
import wt.inf.container.WTContained;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.org.WTPrincipalReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

//##begin user.imports preserve=yes
import wt.fc.InvalidAttributeException;  // Preserved unmodeled dependency
import wt.fc.PersistInfo;  // Preserved unmodeled dependency
import wt.introspection.ClassInfo;  // Preserved unmodeled dependency
import wt.introspection.WTIntrospectionException;  // Preserved unmodeled dependency
import wt.introspection.WTIntrospector;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
import wt.fc.ObjectReference;  // Preserved unmodeled dependency
//##end user.imports

//##begin ProjectManager%4AFC079602D2.doc preserve=no
/**
 *
 * <p>
 * Use the <code>newProjectManager</code> static factory method(s), not
 * the <code>ProjectManager</code> constructor, to construct instances of
 * this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/
//##end ProjectManager%4AFC079602D2.doc

public class ProjectManager extends Managed implements Persistable, WTContained, Externalizable {


   // --- Attribute Section ---


   private static final String RESOURCE = "e3ps.project.projectResource";
   private static final String CLASSNAME = ProjectManager.class.getName();

   //##begin NAME%NAME.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end NAME%NAME.doc
   public static final String NAME = "name";

   private static int NAME_UPPER_LIMIT = -1;
   private String name;

   //##begin SOP_DATE%SOP_DATE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end SOP_DATE%SOP_DATE.doc
   public static final String SOP_DATE = "sopDate";

   private Timestamp sopDate;

   //##begin SOP_START_DATE%SOP_START_DATE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end SOP_START_DATE%SOP_START_DATE.doc
   public static final String SOP_START_DATE = "sopStartDate";

   private Timestamp sopStartDate;

   //##begin ORDER_NO%ORDER_NO.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end ORDER_NO%ORDER_NO.doc
   public static final String ORDER_NO = "orderNo";

   private static int ORDER_NO_UPPER_LIMIT = -1;
   private String orderNo;

   //##begin CAR_INFO%CAR_INFO.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end CAR_INFO%CAR_INFO.doc
   public static final String CAR_INFO = "carInfo";

   private static int CAR_INFO_UPPER_LIMIT = -1;
   private String carInfo;

   //##begin PM_ETC1%PM_ETC1.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PM_ETC1%PM_ETC1.doc
   public static final String PM_ETC1 = "pmEtc1";

   private static int PM_ETC1_UPPER_LIMIT = -1;
   private String pmEtc1;

   //##begin PM_ETC2%PM_ETC2.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PM_ETC2%PM_ETC2.doc
   public static final String PM_ETC2 = "pmEtc2";

   private static int PM_ETC2_UPPER_LIMIT = -1;
   private String pmEtc2;

   //##begin TEMPLATE_OID%TEMPLATE_OID.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end TEMPLATE_OID%TEMPLATE_OID.doc
   public static final String TEMPLATE_OID = "templateOid";

   private static int TEMPLATE_OID_UPPER_LIMIT = -1;
   private String templateOid;

   //##begin PM%PM.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PM%PM.doc
   public static final String PM = "pm";

   private WTPrincipalReference pm;

   //##begin SALE%SALE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end SALE%SALE.doc
   public static final String SALE = "sale";

   private WTPrincipalReference sale;

   //##begin MODEL%MODEL.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end MODEL%MODEL.doc
   public static final String MODEL = "model";


   //##begin MODEL_REFERENCE%MODEL_REFERENCE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end MODEL_REFERENCE%MODEL_REFERENCE.doc
   public static final String MODEL_REFERENCE = "modelReference";

   private ObjectReference modelReference;

   //##begin OEM_TYPE%OEM_TYPE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end OEM_TYPE%OEM_TYPE.doc
   public static final String OEM_TYPE = "oemType";


   //##begin OEM_TYPE_REFERENCE%OEM_TYPE_REFERENCE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end OEM_TYPE_REFERENCE%OEM_TYPE_REFERENCE.doc
   public static final String OEM_TYPE_REFERENCE = "oemTypeReference";

   private ObjectReference oemTypeReference;

   //##begin PRODUCT%PRODUCT.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PRODUCT%PRODUCT.doc
   public static final String PRODUCT = "product";


   //##begin PRODUCT_REFERENCE%PRODUCT_REFERENCE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end PRODUCT_REFERENCE%PRODUCT_REFERENCE.doc
   public static final String PRODUCT_REFERENCE = "productReference";

   private ObjectReference productReference;

   //##begin CUSTOMER%CUSTOMER.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end CUSTOMER%CUSTOMER.doc
   public static final String CUSTOMER = "customer";


   //##begin CUSTOMER_REFERENCE%CUSTOMER_REFERENCE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end CUSTOMER_REFERENCE%CUSTOMER_REFERENCE.doc
   public static final String CUSTOMER_REFERENCE = "customerReference";

   private ObjectReference customerReference;

   //##begin OEMTYPECODE%OEMTYPECODE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end OEMTYPECODE%OEMTYPECODE.doc
   public static final String OEMTYPECODE = "oemtypecode";


   //##begin OEMTYPECODE_REFERENCE%OEMTYPECODE_REFERENCE.doc preserve=no
   /**
    * Label for the attribute.
    **/
   //##end OEMTYPECODE_REFERENCE%OEMTYPECODE_REFERENCE.doc
   public static final String OEMTYPECODE_REFERENCE = "oemtypecodeReference";

   private ObjectReference oemtypecodeReference;

   //##begin CONTAINER%CONTAINER.doc preserve=no
   /**
    * Label for the attribute.
    *
    * <BR><BR><B>Supported API: </B>false
    **/
   //##end CONTAINER%CONTAINER.doc
   public static final String CONTAINER = "container";

   private WTContainerRef containerReference;
   static final long serialVersionUID = 1;
   public static final long EXTERNALIZATION_VERSION_UID = -8971782688773673055L;

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

      output.writeObject( carInfo );
      output.writeObject( containerReference );
      output.writeObject( customerReference );
      output.writeObject( modelReference );
      output.writeObject( name );
      output.writeObject( oemTypeReference );
      output.writeObject( oemtypecodeReference );
      output.writeObject( orderNo );
      output.writeObject( pm );
      output.writeObject( pmEtc1 );
      output.writeObject( pmEtc2 );
      output.writeObject( productReference );
      output.writeObject( sale );
      output.writeObject( sopDate );
      output.writeObject( sopStartDate );
      output.writeObject( templateOid );
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

         carInfo = (String)input.readObject();
         containerReference = (WTContainerRef)input.readObject();
         customerReference = (ObjectReference)input.readObject();
         modelReference = (ObjectReference)input.readObject();
         name = (String)input.readObject();
         oemTypeReference = (ObjectReference)input.readObject();
         oemtypecodeReference = (ObjectReference)input.readObject();
         orderNo = (String)input.readObject();
         pm = (WTPrincipalReference)input.readObject();
         pmEtc1 = (String)input.readObject();
         pmEtc2 = (String)input.readObject();
         productReference = (ObjectReference)input.readObject();
         sale = (WTPrincipalReference)input.readObject();
         sopDate = (Timestamp)input.readObject();
         sopStartDate = (Timestamp)input.readObject();
         templateOid = (String)input.readObject();
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

      output.setString( "name", name );
      output.setTimestamp( "sopDate", sopDate );
      output.setTimestamp( "sopStartDate", sopStartDate );
      output.setString( "orderNo", orderNo );
      output.setString( "carInfo", carInfo );
      output.setString( "pmEtc1", pmEtc1 );
      output.setString( "pmEtc2", pmEtc2 );
      output.setString( "templateOid", templateOid );
      output.writeObject( "pm", pm, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "sale", sale, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "modelReference", modelReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "productReference", productReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "customerReference", customerReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "oemtypecodeReference", oemtypecodeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
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

      name = input.getString( "name" );
      sopDate = input.getTimestamp( "sopDate" );
      sopStartDate = input.getTimestamp( "sopStartDate" );
      orderNo = input.getString( "orderNo" );
      carInfo = input.getString( "carInfo" );
      pmEtc1 = input.getString( "pmEtc1" );
      pmEtc2 = input.getString( "pmEtc2" );
      templateOid = input.getString( "templateOid" );
      pm = (wt.org.WTPrincipalReference)input.readObject( "pm", pm, wt.org.WTPrincipalReference.class, true );
      sale = (wt.org.WTPrincipalReference)input.readObject( "sale", sale, wt.org.WTPrincipalReference.class, true );
      modelReference = (wt.fc.ObjectReference)input.readObject( "modelReference", modelReference, wt.fc.ObjectReference.class, true );
      oemTypeReference = (wt.fc.ObjectReference)input.readObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      productReference = (wt.fc.ObjectReference)input.readObject( "productReference", productReference, wt.fc.ObjectReference.class, true );
      customerReference = (wt.fc.ObjectReference)input.readObject( "customerReference", customerReference, wt.fc.ObjectReference.class, true );
      oemtypecodeReference = (wt.fc.ObjectReference)input.readObject( "oemtypecodeReference", oemtypecodeReference, wt.fc.ObjectReference.class, true );
      containerReference = (wt.inf.container.WTContainerRef)input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
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

   //##begin getName%4AFC07BD0116g.doc preserve=no
   /**
    * Gets the value of the attribute: NAME.
    *
    * @return    String
    **/
   //##end getName%4AFC07BD0116g.doc

   public String getName() {
      //##begin getName%4AFC07BD0116g.body preserve=no

      return name;
      //##end getName%4AFC07BD0116g.body
   }

   //##begin setName%4AFC07BD0116s.doc preserve=no
   /**
    * Sets the value of the attribute: NAME.
    *
    * @param     a_Name
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setName%4AFC07BD0116s.doc

   public void setName( String a_Name )
            throws WTPropertyVetoException {
      //##begin setName%4AFC07BD0116s.body preserve=no

      nameValidate( a_Name );   // throws exception if not valid
      name = a_Name;
      //##end setName%4AFC07BD0116s.body
   }

   //##begin nameValidate%4AFC07BD0116.doc preserve=no
   /**
    * @param     a_Name
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end nameValidate%4AFC07BD0116.doc

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

   //##begin getSopDate%4AFC07CB0276g.doc preserve=no
   /**
    * Gets the value of the attribute: SOP_DATE.
    *
    * @return    Timestamp
    **/
   //##end getSopDate%4AFC07CB0276g.doc

   public Timestamp getSopDate() {
      //##begin getSopDate%4AFC07CB0276g.body preserve=no

      return sopDate;
      //##end getSopDate%4AFC07CB0276g.body
   }

   //##begin setSopDate%4AFC07CB0276s.doc preserve=no
   /**
    * Sets the value of the attribute: SOP_DATE.
    *
    * @param     a_SopDate
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setSopDate%4AFC07CB0276s.doc

   public void setSopDate( Timestamp a_SopDate )
            throws WTPropertyVetoException {
      //##begin setSopDate%4AFC07CB0276s.body preserve=no

      sopDate = a_SopDate;
      //##end setSopDate%4AFC07CB0276s.body
   }

   //##begin getSopStartDate%4AFC084000AAg.doc preserve=no
   /**
    * Gets the value of the attribute: SOP_START_DATE.
    *
    * @return    Timestamp
    **/
   //##end getSopStartDate%4AFC084000AAg.doc

   public Timestamp getSopStartDate() {
      //##begin getSopStartDate%4AFC084000AAg.body preserve=no

      return sopStartDate;
      //##end getSopStartDate%4AFC084000AAg.body
   }

   //##begin setSopStartDate%4AFC084000AAs.doc preserve=no
   /**
    * Sets the value of the attribute: SOP_START_DATE.
    *
    * @param     a_SopStartDate
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setSopStartDate%4AFC084000AAs.doc

   public void setSopStartDate( Timestamp a_SopStartDate )
            throws WTPropertyVetoException {
      //##begin setSopStartDate%4AFC084000AAs.body preserve=no

      sopStartDate = a_SopStartDate;
      //##end setSopStartDate%4AFC084000AAs.body
   }

   //##begin getOrderNo%4AFC0B5C008Cg.doc preserve=no
   /**
    * Gets the value of the attribute: ORDER_NO.
    *
    * @return    String
    **/
   //##end getOrderNo%4AFC0B5C008Cg.doc

   public String getOrderNo() {
      //##begin getOrderNo%4AFC0B5C008Cg.body preserve=no

      return orderNo;
      //##end getOrderNo%4AFC0B5C008Cg.body
   }

   //##begin setOrderNo%4AFC0B5C008Cs.doc preserve=no
   /**
    * Sets the value of the attribute: ORDER_NO.
    *
    * @param     a_OrderNo
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setOrderNo%4AFC0B5C008Cs.doc

   public void setOrderNo( String a_OrderNo )
            throws WTPropertyVetoException {
      //##begin setOrderNo%4AFC0B5C008Cs.body preserve=no

      orderNoValidate( a_OrderNo );   // throws exception if not valid
      orderNo = a_OrderNo;
      //##end setOrderNo%4AFC0B5C008Cs.body
   }

   //##begin orderNoValidate%4AFC0B5C008C.doc preserve=no
   /**
    * @param     a_OrderNo
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end orderNoValidate%4AFC0B5C008C.doc

   private void orderNoValidate( String a_OrderNo )
            throws WTPropertyVetoException {
      if ( ORDER_NO_UPPER_LIMIT < 1 ) {
         try { ORDER_NO_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "orderNo" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { ORDER_NO_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_OrderNo != null && !wt.fc.PersistenceHelper.checkStoredLength( a_OrderNo, ORDER_NO_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "orderNo" ), String.valueOf( Math.min ( ORDER_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "orderNo", orderNo, a_OrderNo ) );
      }
   }

   //##begin getCarInfo%4AFC0B850375g.doc preserve=no
   /**
    * Gets the value of the attribute: CAR_INFO.
    *
    * @return    String
    **/
   //##end getCarInfo%4AFC0B850375g.doc

   public String getCarInfo() {
      //##begin getCarInfo%4AFC0B850375g.body preserve=no

      return carInfo;
      //##end getCarInfo%4AFC0B850375g.body
   }

   //##begin setCarInfo%4AFC0B850375s.doc preserve=no
   /**
    * Sets the value of the attribute: CAR_INFO.
    *
    * @param     a_CarInfo
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setCarInfo%4AFC0B850375s.doc

   public void setCarInfo( String a_CarInfo )
            throws WTPropertyVetoException {
      //##begin setCarInfo%4AFC0B850375s.body preserve=no

      carInfoValidate( a_CarInfo );   // throws exception if not valid
      carInfo = a_CarInfo;
      //##end setCarInfo%4AFC0B850375s.body
   }

   //##begin carInfoValidate%4AFC0B850375.doc preserve=no
   /**
    * @param     a_CarInfo
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end carInfoValidate%4AFC0B850375.doc

   private void carInfoValidate( String a_CarInfo )
            throws WTPropertyVetoException {
      if ( CAR_INFO_UPPER_LIMIT < 1 ) {
         try { CAR_INFO_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "carInfo" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { CAR_INFO_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_CarInfo != null && !wt.fc.PersistenceHelper.checkStoredLength( a_CarInfo, CAR_INFO_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "carInfo" ), String.valueOf( Math.min ( CAR_INFO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "carInfo", carInfo, a_CarInfo ) );
      }
   }

   //##begin getPmEtc1%4AFC0CB10223g.doc preserve=no
   /**
    * Gets the value of the attribute: PM_ETC1.
    *
    * @return    String
    **/
   //##end getPmEtc1%4AFC0CB10223g.doc

   public String getPmEtc1() {
      //##begin getPmEtc1%4AFC0CB10223g.body preserve=no

      return pmEtc1;
      //##end getPmEtc1%4AFC0CB10223g.body
   }

   //##begin setPmEtc1%4AFC0CB10223s.doc preserve=no
   /**
    * Sets the value of the attribute: PM_ETC1.
    *
    * @param     a_PmEtc1
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setPmEtc1%4AFC0CB10223s.doc

   public void setPmEtc1( String a_PmEtc1 )
            throws WTPropertyVetoException {
      //##begin setPmEtc1%4AFC0CB10223s.body preserve=no

      pmEtc1Validate( a_PmEtc1 );   // throws exception if not valid
      pmEtc1 = a_PmEtc1;
      //##end setPmEtc1%4AFC0CB10223s.body
   }

   //##begin pmEtc1Validate%4AFC0CB10223.doc preserve=no
   /**
    * @param     a_PmEtc1
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end pmEtc1Validate%4AFC0CB10223.doc

   private void pmEtc1Validate( String a_PmEtc1 )
            throws WTPropertyVetoException {
      if ( PM_ETC1_UPPER_LIMIT < 1 ) {
         try { PM_ETC1_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "pmEtc1" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { PM_ETC1_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_PmEtc1 != null && !wt.fc.PersistenceHelper.checkStoredLength( a_PmEtc1, PM_ETC1_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "pmEtc1" ), String.valueOf( Math.min ( PM_ETC1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "pmEtc1", pmEtc1, a_PmEtc1 ) );
      }
   }

   //##begin getPmEtc2%4AFC0CCD0279g.doc preserve=no
   /**
    * Gets the value of the attribute: PM_ETC2.
    *
    * @return    String
    **/
   //##end getPmEtc2%4AFC0CCD0279g.doc

   public String getPmEtc2() {
      //##begin getPmEtc2%4AFC0CCD0279g.body preserve=no

      return pmEtc2;
      //##end getPmEtc2%4AFC0CCD0279g.body
   }

   //##begin setPmEtc2%4AFC0CCD0279s.doc preserve=no
   /**
    * Sets the value of the attribute: PM_ETC2.
    *
    * @param     a_PmEtc2
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setPmEtc2%4AFC0CCD0279s.doc

   public void setPmEtc2( String a_PmEtc2 )
            throws WTPropertyVetoException {
      //##begin setPmEtc2%4AFC0CCD0279s.body preserve=no

      pmEtc2Validate( a_PmEtc2 );   // throws exception if not valid
      pmEtc2 = a_PmEtc2;
      //##end setPmEtc2%4AFC0CCD0279s.body
   }

   //##begin pmEtc2Validate%4AFC0CCD0279.doc preserve=no
   /**
    * @param     a_PmEtc2
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end pmEtc2Validate%4AFC0CCD0279.doc

   private void pmEtc2Validate( String a_PmEtc2 )
            throws WTPropertyVetoException {
      if ( PM_ETC2_UPPER_LIMIT < 1 ) {
         try { PM_ETC2_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "pmEtc2" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { PM_ETC2_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_PmEtc2 != null && !wt.fc.PersistenceHelper.checkStoredLength( a_PmEtc2, PM_ETC2_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "pmEtc2" ), String.valueOf( Math.min ( PM_ETC2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "pmEtc2", pmEtc2, a_PmEtc2 ) );
      }
   }

   //##begin getTemplateOid%4AFC157D01FFg.doc preserve=no
   /**
    * Gets the value of the attribute: TEMPLATE_OID.
    *
    * @return    String
    **/
   //##end getTemplateOid%4AFC157D01FFg.doc

   public String getTemplateOid() {
      //##begin getTemplateOid%4AFC157D01FFg.body preserve=no

      return templateOid;
      //##end getTemplateOid%4AFC157D01FFg.body
   }

   //##begin setTemplateOid%4AFC157D01FFs.doc preserve=no
   /**
    * Sets the value of the attribute: TEMPLATE_OID.
    *
    * @param     a_TemplateOid
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setTemplateOid%4AFC157D01FFs.doc

   public void setTemplateOid( String a_TemplateOid )
            throws WTPropertyVetoException {
      //##begin setTemplateOid%4AFC157D01FFs.body preserve=no

      templateOidValidate( a_TemplateOid );   // throws exception if not valid
      templateOid = a_TemplateOid;
      //##end setTemplateOid%4AFC157D01FFs.body
   }

   //##begin templateOidValidate%4AFC157D01FF.doc preserve=no
   /**
    * @param     a_TemplateOid
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end templateOidValidate%4AFC157D01FF.doc

   private void templateOidValidate( String a_TemplateOid )
            throws WTPropertyVetoException {
      if ( TEMPLATE_OID_UPPER_LIMIT < 1 ) {
         try { TEMPLATE_OID_UPPER_LIMIT = ((Integer)wt.introspection.WTIntrospector.getClassInfo( CLASSNAME ).getPropertyDescriptor( "templateOid" ).getValue( wt.introspection.WTIntrospector.UPPER_LIMIT )).intValue(); }
         catch (wt.introspection.WTIntrospectionException e) { TEMPLATE_OID_UPPER_LIMIT = 200; } // resort to modeled value
      }
      if ( a_TemplateOid != null && !wt.fc.PersistenceHelper.checkStoredLength( a_TemplateOid, TEMPLATE_OID_UPPER_LIMIT , true ) ) {   // upper limit check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "templateOid" ), String.valueOf( Math.min ( TEMPLATE_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT, args,
               new java.beans.PropertyChangeEvent( this, "templateOid", templateOid, a_TemplateOid ) );
      }
   }

   //##begin getPm%4AFC0D860369g.doc preserve=no
   /**
    * Gets the object for the association that plays role: PM.
    *
    * @return    WTPrincipalReference
    **/
   //##end getPm%4AFC0D860369g.doc

   public WTPrincipalReference getPm() {
      //##begin getPm%4AFC0D860369g.body preserve=no

      return pm;
      //##end getPm%4AFC0D860369g.body
   }

   //##begin setPm%4AFC0D860369s.doc preserve=no
   /**
    * Sets the object for the association that plays role: PM.
    *
    * @param     a_Pm
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setPm%4AFC0D860369s.doc

   public void setPm( WTPrincipalReference a_Pm )
            throws WTPropertyVetoException {
      //##begin setPm%4AFC0D860369s.body preserve=no

      pmValidate( a_Pm );   // throws exception if not valid
      pm = a_Pm;
      //##end setPm%4AFC0D860369s.body
   }

   //##begin pmValidate%4AFC0D860369.doc preserve=no
   /**
    * @param     a_Pm
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end pmValidate%4AFC0D860369.doc

   private void pmValidate( WTPrincipalReference a_Pm )
            throws WTPropertyVetoException {
      if ( a_Pm == null ) {                                  // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "pm" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "pm", pm, a_Pm ) );
      }
      if ( a_Pm != null && !( a_Pm instanceof WTPrincipalReference ) ) {  // type check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "pm" ), "WTPrincipalReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "pm", pm, a_Pm ) );
      }
   }

   //##begin getSale%4AFC0DE2034Cg.doc preserve=no
   /**
    * Gets the object for the association that plays role: SALE.
    *
    * @return    WTPrincipalReference
    **/
   //##end getSale%4AFC0DE2034Cg.doc

   public WTPrincipalReference getSale() {
      //##begin getSale%4AFC0DE2034Cg.body preserve=no

      return sale;
      //##end getSale%4AFC0DE2034Cg.body
   }

   //##begin setSale%4AFC0DE2034Cs.doc preserve=no
   /**
    * Sets the object for the association that plays role: SALE.
    *
    * @param     a_Sale
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setSale%4AFC0DE2034Cs.doc

   public void setSale( WTPrincipalReference a_Sale )
            throws WTPropertyVetoException {
      //##begin setSale%4AFC0DE2034Cs.body preserve=no

      saleValidate( a_Sale );   // throws exception if not valid
      sale = a_Sale;
      //##end setSale%4AFC0DE2034Cs.body
   }

   //##begin saleValidate%4AFC0DE2034C.doc preserve=no
   /**
    * @param     a_Sale
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end saleValidate%4AFC0DE2034C.doc

   private void saleValidate( WTPrincipalReference a_Sale )
            throws WTPropertyVetoException {
      if ( a_Sale == null ) {                                  // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "sale" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "sale", sale, a_Sale ) );
      }
      if ( a_Sale != null && !( a_Sale instanceof WTPrincipalReference ) ) {  // type check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "sale" ), "WTPrincipalReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "sale", sale, a_Sale ) );
      }
   }

   //##begin getModel%4AFC0EAF0348d.doc preserve=no
   /**
    * Gets the object for the association that plays role: MODEL.
    *
    * @return    NumberCode
    **/
   //##end getModel%4AFC0EAF0348d.doc

   public NumberCode getModel() {
      //##begin getModel%4AFC0EAF0348d.body preserve=no

      if ( modelReference == null )
         return null;

      return (NumberCode)modelReference.getObject();
      //##end getModel%4AFC0EAF0348d.body
   }

   //##begin setModel%4AFC0EAF0348p.doc preserve=no
   /**
    * Sets the object for the association that plays role: MODEL.
    *
    * @param     a_Model
    * @exception wt.util.WTPropertyVetoException
    * @exception wt.util.WTException
    **/
   //##end setModel%4AFC0EAF0348p.doc

   public void setModel( NumberCode a_Model )
            throws WTPropertyVetoException, WTException {
      //##begin setModel%4AFC0EAF0348p.body preserve=no

      setModelReference( a_Model == null ? null : ObjectReference.newObjectReference( a_Model ) );
      //##end setModel%4AFC0EAF0348p.body
   }

   //##begin getModelReference%4AFC0EAF0348g.doc preserve=no
   /**
    * Gets the value of the attribute: MODEL_REFERENCE.
    *
    * @return    ObjectReference
    **/
   //##end getModelReference%4AFC0EAF0348g.doc

   public ObjectReference getModelReference() {
      //##begin getModelReference%4AFC0EAF0348g.body preserve=no

      return modelReference;
      //##end getModelReference%4AFC0EAF0348g.body
   }

   //##begin setModelReference%4AFC0EAF0348s.doc preserve=no
   /**
    * Sets the value of the attribute: MODEL_REFERENCE.
    *
    * @param     a_ModelReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setModelReference%4AFC0EAF0348s.doc

   public void setModelReference( ObjectReference a_ModelReference )
            throws WTPropertyVetoException {
      //##begin setModelReference%4AFC0EAF0348s.body preserve=no

      modelReferenceValidate( a_ModelReference );   // throws exception if not valid
      modelReference = a_ModelReference;
      //##end setModelReference%4AFC0EAF0348s.body
   }

   //##begin modelReferenceValidate%4AFC0EAF0348.doc preserve=no
   /**
    * @param     a_ModelReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end modelReferenceValidate%4AFC0EAF0348.doc

   private void modelReferenceValidate( ObjectReference a_ModelReference )
            throws WTPropertyVetoException {
      if ( a_ModelReference == null || a_ModelReference.getReferencedClass() == null ) { // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "modelReference" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "modelReference", modelReference, a_ModelReference ) );
      }
      if ( a_ModelReference != null && a_ModelReference.getReferencedClass() != null &&  // type check
               !( e3ps.common.code.NumberCode.class.isAssignableFrom( a_ModelReference.getReferencedClass() ) ) ) {
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "modelReference" ), "ObjectReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "modelReference", modelReference, a_ModelReference ) );
      }
   }

   //##begin getOemType%4AFFB4EB0119d.doc preserve=no
   /**
    * Gets the object for the association that plays role: OEM_TYPE.
    *
    * @return    OEMProjectType
    **/
   //##end getOemType%4AFFB4EB0119d.doc

   public OEMProjectType getOemType() {
      //##begin getOemType%4AFFB4EB0119d.body preserve=no

      if ( oemTypeReference == null )
         return null;

      return (OEMProjectType)oemTypeReference.getObject();
      //##end getOemType%4AFFB4EB0119d.body
   }

   //##begin setOemType%4AFFB4EB0119p.doc preserve=no
   /**
    * Sets the object for the association that plays role: OEM_TYPE.
    *
    * @param     a_OemType
    * @exception wt.util.WTPropertyVetoException
    * @exception wt.util.WTException
    **/
   //##end setOemType%4AFFB4EB0119p.doc

   public void setOemType( OEMProjectType a_OemType )
            throws WTPropertyVetoException, WTException {
      //##begin setOemType%4AFFB4EB0119p.body preserve=no

      setOemTypeReference( a_OemType == null ? null : ObjectReference.newObjectReference( a_OemType ) );
      //##end setOemType%4AFFB4EB0119p.body
   }

   //##begin getOemTypeReference%4AFFB4EB0119g.doc preserve=no
   /**
    * Gets the value of the attribute: OEM_TYPE_REFERENCE.
    *
    * @return    ObjectReference
    **/
   //##end getOemTypeReference%4AFFB4EB0119g.doc

   public ObjectReference getOemTypeReference() {
      //##begin getOemTypeReference%4AFFB4EB0119g.body preserve=no

      return oemTypeReference;
      //##end getOemTypeReference%4AFFB4EB0119g.body
   }

   //##begin setOemTypeReference%4AFFB4EB0119s.doc preserve=no
   /**
    * Sets the value of the attribute: OEM_TYPE_REFERENCE.
    *
    * @param     a_OemTypeReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setOemTypeReference%4AFFB4EB0119s.doc

   public void setOemTypeReference( ObjectReference a_OemTypeReference )
            throws WTPropertyVetoException {
      //##begin setOemTypeReference%4AFFB4EB0119s.body preserve=no

      oemTypeReferenceValidate( a_OemTypeReference );   // throws exception if not valid
      oemTypeReference = a_OemTypeReference;
      //##end setOemTypeReference%4AFFB4EB0119s.body
   }

   //##begin oemTypeReferenceValidate%4AFFB4EB0119.doc preserve=no
   /**
    * @param     a_OemTypeReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end oemTypeReferenceValidate%4AFFB4EB0119.doc

   private void oemTypeReferenceValidate( ObjectReference a_OemTypeReference )
            throws WTPropertyVetoException {
      if ( a_OemTypeReference != null && a_OemTypeReference.getReferencedClass() != null &&  // type check
               !( e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom( a_OemTypeReference.getReferencedClass() ) ) ) {
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "oemTypeReference" ), "ObjectReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "oemTypeReference", oemTypeReference, a_OemTypeReference ) );
      }
   }

   //##begin getProduct%4B15D03C009Ad.doc preserve=no
   /**
    * Gets the object for the association that plays role: PRODUCT.
    *
    * @return    NumberCode
    **/
   //##end getProduct%4B15D03C009Ad.doc

   public NumberCode getProduct() {
      //##begin getProduct%4B15D03C009Ad.body preserve=no

      if ( productReference == null )
         return null;

      return (NumberCode)productReference.getObject();
      //##end getProduct%4B15D03C009Ad.body
   }

   //##begin setProduct%4B15D03C009Ap.doc preserve=no
   /**
    * Sets the object for the association that plays role: PRODUCT.
    *
    * @param     a_Product
    * @exception wt.util.WTPropertyVetoException
    * @exception wt.util.WTException
    **/
   //##end setProduct%4B15D03C009Ap.doc

   public void setProduct( NumberCode a_Product )
            throws WTPropertyVetoException, WTException {
      //##begin setProduct%4B15D03C009Ap.body preserve=no

      setProductReference( a_Product == null ? null : ObjectReference.newObjectReference( a_Product ) );
      //##end setProduct%4B15D03C009Ap.body
   }

   //##begin getProductReference%4B15D03C009Ag.doc preserve=no
   /**
    * Gets the value of the attribute: PRODUCT_REFERENCE.
    *
    * @return    ObjectReference
    **/
   //##end getProductReference%4B15D03C009Ag.doc

   public ObjectReference getProductReference() {
      //##begin getProductReference%4B15D03C009Ag.body preserve=no

      return productReference;
      //##end getProductReference%4B15D03C009Ag.body
   }

   //##begin setProductReference%4B15D03C009As.doc preserve=no
   /**
    * Sets the value of the attribute: PRODUCT_REFERENCE.
    *
    * @param     a_ProductReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setProductReference%4B15D03C009As.doc

   public void setProductReference( ObjectReference a_ProductReference )
            throws WTPropertyVetoException {
      //##begin setProductReference%4B15D03C009As.body preserve=no

      productReferenceValidate( a_ProductReference );   // throws exception if not valid
      productReference = a_ProductReference;
      //##end setProductReference%4B15D03C009As.body
   }

   //##begin productReferenceValidate%4B15D03C009A.doc preserve=no
   /**
    * @param     a_ProductReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end productReferenceValidate%4B15D03C009A.doc

   private void productReferenceValidate( ObjectReference a_ProductReference )
            throws WTPropertyVetoException {
      if ( a_ProductReference == null || a_ProductReference.getReferencedClass() == null ) { // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "productReference" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "productReference", productReference, a_ProductReference ) );
      }
      if ( a_ProductReference != null && a_ProductReference.getReferencedClass() != null &&  // type check
               !( e3ps.common.code.NumberCode.class.isAssignableFrom( a_ProductReference.getReferencedClass() ) ) ) {
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "productReference" ), "ObjectReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "productReference", productReference, a_ProductReference ) );
      }
   }

   //##begin getCustomer%4B15D25F011Ed.doc preserve=no
   /**
    * Gets the object for the association that plays role: CUSTOMER.
    *
    * @return    NumberCode
    **/
   //##end getCustomer%4B15D25F011Ed.doc

   public NumberCode getCustomer() {
      //##begin getCustomer%4B15D25F011Ed.body preserve=no

      if ( customerReference == null )
         return null;

      return (NumberCode)customerReference.getObject();
      //##end getCustomer%4B15D25F011Ed.body
   }

   //##begin setCustomer%4B15D25F011Ep.doc preserve=no
   /**
    * Sets the object for the association that plays role: CUSTOMER.
    *
    * @param     a_Customer
    * @exception wt.util.WTPropertyVetoException
    * @exception wt.util.WTException
    **/
   //##end setCustomer%4B15D25F011Ep.doc

   public void setCustomer( NumberCode a_Customer )
            throws WTPropertyVetoException, WTException {
      //##begin setCustomer%4B15D25F011Ep.body preserve=no

      setCustomerReference( a_Customer == null ? null : ObjectReference.newObjectReference( a_Customer ) );
      //##end setCustomer%4B15D25F011Ep.body
   }

   //##begin getCustomerReference%4B15D25F011Eg.doc preserve=no
   /**
    * Gets the value of the attribute: CUSTOMER_REFERENCE.
    *
    * @return    ObjectReference
    **/
   //##end getCustomerReference%4B15D25F011Eg.doc

   public ObjectReference getCustomerReference() {
      //##begin getCustomerReference%4B15D25F011Eg.body preserve=no

      return customerReference;
      //##end getCustomerReference%4B15D25F011Eg.body
   }

   //##begin setCustomerReference%4B15D25F011Es.doc preserve=no
   /**
    * Sets the value of the attribute: CUSTOMER_REFERENCE.
    *
    * @param     a_CustomerReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setCustomerReference%4B15D25F011Es.doc

   public void setCustomerReference( ObjectReference a_CustomerReference )
            throws WTPropertyVetoException {
      //##begin setCustomerReference%4B15D25F011Es.body preserve=no

      customerReferenceValidate( a_CustomerReference );   // throws exception if not valid
      customerReference = a_CustomerReference;
      //##end setCustomerReference%4B15D25F011Es.body
   }

   //##begin customerReferenceValidate%4B15D25F011E.doc preserve=no
   /**
    * @param     a_CustomerReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end customerReferenceValidate%4B15D25F011E.doc

   private void customerReferenceValidate( ObjectReference a_CustomerReference )
            throws WTPropertyVetoException {
      if ( a_CustomerReference == null || a_CustomerReference.getReferencedClass() == null ) { // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "customerReference" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "customerReference", customerReference, a_CustomerReference ) );
      }
      if ( a_CustomerReference != null && a_CustomerReference.getReferencedClass() != null &&  // type check
               !( e3ps.common.code.NumberCode.class.isAssignableFrom( a_CustomerReference.getReferencedClass() ) ) ) {
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "customerReference" ), "ObjectReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "customerReference", customerReference, a_CustomerReference ) );
      }
   }

   //##begin getOemtypecode%4B1CA4220277d.doc preserve=no
   /**
    * Gets the object for the association that plays role: OEMTYPECODE.
    *
    * @return    NumberCode
    **/
   //##end getOemtypecode%4B1CA4220277d.doc

   public NumberCode getOemtypecode() {
      //##begin getOemtypecode%4B1CA4220277d.body preserve=no

      if ( oemtypecodeReference == null )
         return null;

      return (NumberCode)oemtypecodeReference.getObject();
      //##end getOemtypecode%4B1CA4220277d.body
   }

   //##begin setOemtypecode%4B1CA4220277p.doc preserve=no
   /**
    * Sets the object for the association that plays role: OEMTYPECODE.
    *
    * @param     a_Oemtypecode
    * @exception wt.util.WTPropertyVetoException
    * @exception wt.util.WTException
    **/
   //##end setOemtypecode%4B1CA4220277p.doc

   public void setOemtypecode( NumberCode a_Oemtypecode )
            throws WTPropertyVetoException, WTException {
      //##begin setOemtypecode%4B1CA4220277p.body preserve=no

      setOemtypecodeReference( a_Oemtypecode == null ? null : ObjectReference.newObjectReference( a_Oemtypecode ) );
      //##end setOemtypecode%4B1CA4220277p.body
   }

   //##begin getOemtypecodeReference%4B1CA4220277g.doc preserve=no
   /**
    * Gets the value of the attribute: OEMTYPECODE_REFERENCE.
    *
    * @return    ObjectReference
    **/
   //##end getOemtypecodeReference%4B1CA4220277g.doc

   public ObjectReference getOemtypecodeReference() {
      //##begin getOemtypecodeReference%4B1CA4220277g.body preserve=no

      return oemtypecodeReference;
      //##end getOemtypecodeReference%4B1CA4220277g.body
   }

   //##begin setOemtypecodeReference%4B1CA4220277s.doc preserve=no
   /**
    * Sets the value of the attribute: OEMTYPECODE_REFERENCE.
    *
    * @param     a_OemtypecodeReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setOemtypecodeReference%4B1CA4220277s.doc

   public void setOemtypecodeReference( ObjectReference a_OemtypecodeReference )
            throws WTPropertyVetoException {
      //##begin setOemtypecodeReference%4B1CA4220277s.body preserve=no

      oemtypecodeReferenceValidate( a_OemtypecodeReference );   // throws exception if not valid
      oemtypecodeReference = a_OemtypecodeReference;
      //##end setOemtypecodeReference%4B1CA4220277s.body
   }

   //##begin oemtypecodeReferenceValidate%4B1CA4220277.doc preserve=no
   /**
    * @param     a_OemtypecodeReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end oemtypecodeReferenceValidate%4B1CA4220277.doc

   private void oemtypecodeReferenceValidate( ObjectReference a_OemtypecodeReference )
            throws WTPropertyVetoException {
      if ( a_OemtypecodeReference != null && a_OemtypecodeReference.getReferencedClass() != null &&  // type check
               !( e3ps.common.code.NumberCode.class.isAssignableFrom( a_OemtypecodeReference.getReferencedClass() ) ) ) {
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "oemtypecodeReference" ), "ObjectReference" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "oemtypecodeReference", oemtypecodeReference, a_OemtypecodeReference ) );
      }
   }

   //##begin newProjectManager%newProjectManagerf.doc preserve=no
   /**
    * Default factory for the class.
    *
    * @return    ProjectManager
    * @exception wt.util.WTException
    **/
   //##end newProjectManager%newProjectManagerf.doc

   public static ProjectManager newProjectManager()
            throws WTException {
      //##begin newProjectManager%newProjectManagerf.body preserve=no

      ProjectManager instance = new ProjectManager();
      instance.initialize();
      return instance;
      //##end newProjectManager%newProjectManagerf.body
   }

   //##begin getContainerName%3E43E949022Eg.doc preserve=no
   /**
    * Gets the value of the attribute: CONTAINER_NAME.
    * The name of the <code>WTContainer</code> this object is assigned to.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @return    String
    **/
   //##end getContainerName%3E43E949022Eg.doc

   public String getContainerName() {
      //##begin getContainerName%3E43E949022Eg.body preserve=no

      try { return getContainerReference().getName(); }
      catch (NullPointerException npe) { return null; }
      //##end getContainerName%3E43E949022Eg.body
   }

   //##begin getContainer%3D6FA60B0115d.doc preserve=no
   /**
    * Gets the object for the association that plays role: CONTAINER.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @return    WTContainer
    **/
   //##end getContainer%3D6FA60B0115d.doc

   public WTContainer getContainer() {
      //##begin getContainer%3D6FA60B0115d.body preserve=no

      if ( containerReference == null )
         return null;

      return (WTContainer)containerReference.getObject();
      //##end getContainer%3D6FA60B0115d.body
   }

   //##begin setContainer%3D6FA60B0115p.doc preserve=no
   /**
    * Sets the object for the association that plays role: CONTAINER.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     a_Container
    * @exception wt.util.WTPropertyVetoException
    * @exception wt.util.WTException
    **/
   //##end setContainer%3D6FA60B0115p.doc

   public void setContainer( WTContainer a_Container )
            throws WTPropertyVetoException, WTException {
      //##begin setContainer%3D6FA60B0115p.body preserve=no

      setContainerReference( a_Container == null ? null : WTContainerRef.newWTContainerRef( a_Container ) );
      //##end setContainer%3D6FA60B0115p.body
   }

   //##begin getContainerReference%3D6FA60B0115g.doc preserve=no
   /**
    * Gets the value of the attribute: CONTAINER_REFERENCE.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @return    WTContainerRef
    **/
   //##end getContainerReference%3D6FA60B0115g.doc

   public WTContainerRef getContainerReference() {
      //##begin getContainerReference%3D6FA60B0115g.body preserve=no

      return containerReference;
      //##end getContainerReference%3D6FA60B0115g.body
   }

   //##begin setContainerReference%3D6FA60B0115s.doc preserve=no
   /**
    * Sets the value of the attribute: CONTAINER_REFERENCE.
    *
    * <BR><BR><B>Supported API: </B>false
    *
    * @param     a_ContainerReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end setContainerReference%3D6FA60B0115s.doc

   public void setContainerReference( WTContainerRef a_ContainerReference )
            throws WTPropertyVetoException {
      //##begin setContainerReference%3D6FA60B0115s.body preserve=no

      containerReferenceValidate( a_ContainerReference );   // throws exception if not valid
      containerReference = a_ContainerReference;
      //##end setContainerReference%3D6FA60B0115s.body
   }

   //##begin containerReferenceValidate%3D6FA60B0115.doc preserve=no
   /**
    * @param     a_ContainerReference
    * @exception wt.util.WTPropertyVetoException
    **/
   //##end containerReferenceValidate%3D6FA60B0115.doc

   private void containerReferenceValidate( WTContainerRef a_ContainerReference )
            throws WTPropertyVetoException {
      if ( a_ContainerReference == null || a_ContainerReference.getReferencedClass() == null ) { // required attribute check
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "containerReference" ) };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE, args,
               new java.beans.PropertyChangeEvent( this, "containerReference", containerReference, a_ContainerReference ) );
      }
      if ( a_ContainerReference != null && a_ContainerReference.getReferencedClass() != null &&  // type check
               !( wt.inf.container.WTContainer.class.isAssignableFrom( a_ContainerReference.getReferencedClass() ) ) ) {
         Object[] args = { new wt.introspection.PropertyDisplayName( CLASSNAME, "containerReference" ), "WTContainerRef" };
         throw new WTPropertyVetoException( "wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE, args,
               new java.beans.PropertyChangeEvent( this, "containerReference", containerReference, a_ContainerReference ) );
      }
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
