package e3ps.wfm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETWfmApprovalLine implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.wfm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETWfmApprovalLine.class.getName();

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public static final java.lang.String APPROVAL_TYPE = "approvalType";
   static int APPROVAL_TYPE_UPPER_LIMIT = -1;
   java.lang.String approvalType;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public java.lang.String getApprovalType() {
      return approvalType;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public void setApprovalType(java.lang.String approvalType) throws wt.util.WTPropertyVetoException {
      approvalTypeValidate(approvalType);
      this.approvalType = approvalType;
   }
   void approvalTypeValidate(java.lang.String approvalType) throws wt.util.WTPropertyVetoException {
      if (APPROVAL_TYPE_UPPER_LIMIT < 1) {
         try { APPROVAL_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approvalType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVAL_TYPE_UPPER_LIMIT = 30; }
      }
      if (approvalType != null && !wt.fc.PersistenceHelper.checkStoredLength(approvalType.toString(), APPROVAL_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approvalType"), java.lang.String.valueOf(java.lang.Math.min(APPROVAL_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approvalType", this.approvalType, approvalType));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public static final java.lang.String APPROVAL_ORDER = "approvalOrder";
   int approvalOrder = 0;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public int getApprovalOrder() {
      return approvalOrder;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public void setApprovalOrder(int approvalOrder) throws wt.util.WTPropertyVetoException {
      approvalOrderValidate(approvalOrder);
      this.approvalOrder = approvalOrder;
   }
   void approvalOrderValidate(int approvalOrder) throws wt.util.WTPropertyVetoException {
      if (approvalOrder > 2000)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approvalOrder"), "2000" },
               new java.beans.PropertyChangeEvent(this, "approvalOrder", this.approvalOrder, approvalOrder));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public static final java.lang.String APPROVER_ID = "approverID";
   static int APPROVER_ID_UPPER_LIMIT = -1;
   java.lang.String approverID;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public java.lang.String getApproverID() {
      return approverID;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public void setApproverID(java.lang.String approverID) throws wt.util.WTPropertyVetoException {
      approverIDValidate(approverID);
      this.approverID = approverID;
   }
   void approverIDValidate(java.lang.String approverID) throws wt.util.WTPropertyVetoException {
      if (APPROVER_ID_UPPER_LIMIT < 1) {
         try { APPROVER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approverID").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVER_ID_UPPER_LIMIT = 30; }
      }
      if (approverID != null && !wt.fc.PersistenceHelper.checkStoredLength(approverID.toString(), APPROVER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approverID"), java.lang.String.valueOf(java.lang.Math.min(APPROVER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approverID", this.approverID, approverID));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public static final java.lang.String APP_MASTER = "appMaster";
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public static final java.lang.String APP_MASTER_REFERENCE = "appMasterReference";
   wt.fc.ObjectReference appMasterReference;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public e3ps.wfm.entity.KETWfmApprovalMaster getAppMaster() {
      return (appMasterReference != null) ? (e3ps.wfm.entity.KETWfmApprovalMaster) appMasterReference.getObject() : null;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public wt.fc.ObjectReference getAppMasterReference() {
      return appMasterReference;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public void setAppMaster(e3ps.wfm.entity.KETWfmApprovalMaster the_appMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAppMasterReference(the_appMaster == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.wfm.entity.KETWfmApprovalMaster) the_appMaster));
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalLine
    */
   public void setAppMasterReference(wt.fc.ObjectReference the_appMasterReference) throws wt.util.WTPropertyVetoException {
      appMasterReferenceValidate(the_appMasterReference);
      appMasterReference = (wt.fc.ObjectReference) the_appMasterReference;
   }
   void appMasterReferenceValidate(wt.fc.ObjectReference the_appMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_appMasterReference == null || the_appMasterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "appMasterReference") },
               new java.beans.PropertyChangeEvent(this, "appMasterReference", this.appMasterReference, appMasterReference));
      if (the_appMasterReference != null && the_appMasterReference.getReferencedClass() != null &&
            !e3ps.wfm.entity.KETWfmApprovalMaster.class.isAssignableFrom(the_appMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "appMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "appMasterReference", this.appMasterReference, appMasterReference));
   }

   wt.fc.PersistInfo thePersistInfo = new wt.fc.PersistInfo();
   /**
    * @see wt.fc.Persistable
    */
   public wt.fc.PersistInfo getPersistInfo() {
      return thePersistInfo;
   }
   /**
    * @see wt.fc.Persistable
    */
   public void setPersistInfo(wt.fc.PersistInfo thePersistInfo) {
      this.thePersistInfo = thePersistInfo;
   }

   public java.lang.String toString() {
      return getConceptualClassname();
   }

   public java.lang.String getConceptualClassname() {
      return CLASSNAME;
   }

   public wt.introspection.ClassInfo getClassInfo() throws wt.introspection.WTIntrospectionException {
      return wt.introspection.WTIntrospector.getClassInfo(getConceptualClassname());
   }

   public java.lang.String getType() {
      try { return getClassInfo().getDisplayName(); }
      catch (wt.introspection.WTIntrospectionException wte) { return wt.util.WTStringUtilities.tail(getConceptualClassname(), '.'); }
   }

   public boolean equals(java.lang.Object obj) {
      return wt.fc.PersistenceHelper.equals(this, obj);
   }

   public int hashCode() {
      return wt.fc.PersistenceHelper.hashCode(this);
   }

   public static final long EXTERNALIZATION_VERSION_UID = 7317875098711176787L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( appMasterReference );
      output.writeInt( approvalOrder );
      output.writeObject( approvalType );
      output.writeObject( approverID );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.wfm.entity.KETWfmApprovalLine) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "appMasterReference", appMasterReference, wt.fc.ObjectReference.class, true );
      output.setInt( "approvalOrder", approvalOrder );
      output.setString( "approvalType", approvalType );
      output.setString( "approverID", approverID );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      appMasterReference = (wt.fc.ObjectReference) input.readObject( "appMasterReference", appMasterReference, wt.fc.ObjectReference.class, true );
      approvalOrder = input.getInt( "approvalOrder" );
      approvalType = input.getString( "approvalType" );
      approverID = input.getString( "approverID" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion7317875098711176787L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      appMasterReference = (wt.fc.ObjectReference) input.readObject();
      approvalOrder = input.readInt();
      approvalType = (java.lang.String) input.readObject();
      approverID = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETWfmApprovalLine thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7317875098711176787L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
