package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETEpmApprovalHis implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETEpmApprovalHis.class.getName();

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String APPROVAL_TYPE = "approvalType";
   static int APPROVAL_TYPE_UPPER_LIMIT = -1;
   java.lang.String approvalType;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.lang.String getApprovalType() {
      return approvalType;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setApprovalType(java.lang.String approvalType) throws wt.util.WTPropertyVetoException {
      approvalTypeValidate(approvalType);
      this.approvalType = approvalType;
   }
   void approvalTypeValidate(java.lang.String approvalType) throws wt.util.WTPropertyVetoException {
      if (APPROVAL_TYPE_UPPER_LIMIT < 1) {
         try { APPROVAL_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approvalType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVAL_TYPE_UPPER_LIMIT = 200; }
      }
      if (approvalType != null && !wt.fc.PersistenceHelper.checkStoredLength(approvalType.toString(), APPROVAL_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approvalType"), java.lang.String.valueOf(java.lang.Math.min(APPROVAL_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approvalType", this.approvalType, approvalType));
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String APPROVAL_DATE = "approvalDate";
   java.sql.Timestamp approvalDate;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.sql.Timestamp getApprovalDate() {
      return approvalDate;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setApprovalDate(java.sql.Timestamp approvalDate) throws wt.util.WTPropertyVetoException {
      approvalDateValidate(approvalDate);
      this.approvalDate = approvalDate;
   }
   void approvalDateValidate(java.sql.Timestamp approvalDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String APPROVER_ID = "approverId";
   static int APPROVER_ID_UPPER_LIMIT = -1;
   java.lang.String approverId;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.lang.String getApproverId() {
      return approverId;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setApproverId(java.lang.String approverId) throws wt.util.WTPropertyVetoException {
      approverIdValidate(approverId);
      this.approverId = approverId;
   }
   void approverIdValidate(java.lang.String approverId) throws wt.util.WTPropertyVetoException {
      if (APPROVER_ID_UPPER_LIMIT < 1) {
         try { APPROVER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approverId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVER_ID_UPPER_LIMIT = 200; }
      }
      if (approverId != null && !wt.fc.PersistenceHelper.checkStoredLength(approverId.toString(), APPROVER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approverId"), java.lang.String.valueOf(java.lang.Math.min(APPROVER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approverId", this.approverId, approverId));
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String APPROVER_NAME = "approverName";
   static int APPROVER_NAME_UPPER_LIMIT = -1;
   java.lang.String approverName;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.lang.String getApproverName() {
      return approverName;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setApproverName(java.lang.String approverName) throws wt.util.WTPropertyVetoException {
      approverNameValidate(approverName);
      this.approverName = approverName;
   }
   void approverNameValidate(java.lang.String approverName) throws wt.util.WTPropertyVetoException {
      if (APPROVER_NAME_UPPER_LIMIT < 1) {
         try { APPROVER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approverName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVER_NAME_UPPER_LIMIT = 200; }
      }
      if (approverName != null && !wt.fc.PersistenceHelper.checkStoredLength(approverName.toString(), APPROVER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approverName"), java.lang.String.valueOf(java.lang.Math.min(APPROVER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approverName", this.approverName, approverName));
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String APPROVER_DELEGATE = "approverDelegate";
   static int APPROVER_DELEGATE_UPPER_LIMIT = -1;
   java.lang.String approverDelegate;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.lang.String getApproverDelegate() {
      return approverDelegate;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setApproverDelegate(java.lang.String approverDelegate) throws wt.util.WTPropertyVetoException {
      approverDelegateValidate(approverDelegate);
      this.approverDelegate = approverDelegate;
   }
   void approverDelegateValidate(java.lang.String approverDelegate) throws wt.util.WTPropertyVetoException {
      if (APPROVER_DELEGATE_UPPER_LIMIT < 1) {
         try { APPROVER_DELEGATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approverDelegate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVER_DELEGATE_UPPER_LIMIT = 200; }
      }
      if (approverDelegate != null && !wt.fc.PersistenceHelper.checkStoredLength(approverDelegate.toString(), APPROVER_DELEGATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approverDelegate"), java.lang.String.valueOf(java.lang.Math.min(APPROVER_DELEGATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approverDelegate", this.approverDelegate, approverDelegate));
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String ECO_NO = "ecoNo";
   static int ECO_NO_UPPER_LIMIT = -1;
   java.lang.String ecoNo;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.lang.String getEcoNo() {
      return ecoNo;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setEcoNo(java.lang.String ecoNo) throws wt.util.WTPropertyVetoException {
      ecoNoValidate(ecoNo);
      this.ecoNo = ecoNo;
   }
   void ecoNoValidate(java.lang.String ecoNo) throws wt.util.WTPropertyVetoException {
      if (ECO_NO_UPPER_LIMIT < 1) {
         try { ECO_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_NO_UPPER_LIMIT = 200; }
      }
      if (ecoNo != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoNo.toString(), ECO_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoNo"), java.lang.String.valueOf(java.lang.Math.min(ECO_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoNo", this.ecoNo, ecoNo));
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String PBO_OID = "pboOid";
   static int PBO_OID_UPPER_LIMIT = -1;
   java.lang.String pboOid;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.lang.String getPboOid() {
      return pboOid;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setPboOid(java.lang.String pboOid) throws wt.util.WTPropertyVetoException {
      pboOidValidate(pboOid);
      this.pboOid = pboOid;
   }
   void pboOidValidate(java.lang.String pboOid) throws wt.util.WTPropertyVetoException {
      if (PBO_OID_UPPER_LIMIT < 1) {
         try { PBO_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pboOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PBO_OID_UPPER_LIMIT = 200; }
      }
      if (pboOid != null && !wt.fc.PersistenceHelper.checkStoredLength(pboOid.toString(), PBO_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pboOid"), java.lang.String.valueOf(java.lang.Math.min(PBO_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pboOid", this.pboOid, pboOid));
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public static final java.lang.String APPROVAL_MAST_OID = "approvalMastOid";
   static int APPROVAL_MAST_OID_UPPER_LIMIT = -1;
   java.lang.String approvalMastOid;
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public java.lang.String getApprovalMastOid() {
      return approvalMastOid;
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHis
    */
   public void setApprovalMastOid(java.lang.String approvalMastOid) throws wt.util.WTPropertyVetoException {
      approvalMastOidValidate(approvalMastOid);
      this.approvalMastOid = approvalMastOid;
   }
   void approvalMastOidValidate(java.lang.String approvalMastOid) throws wt.util.WTPropertyVetoException {
      if (APPROVAL_MAST_OID_UPPER_LIMIT < 1) {
         try { APPROVAL_MAST_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approvalMastOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVAL_MAST_OID_UPPER_LIMIT = 200; }
      }
      if (approvalMastOid != null && !wt.fc.PersistenceHelper.checkStoredLength(approvalMastOid.toString(), APPROVAL_MAST_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approvalMastOid"), java.lang.String.valueOf(java.lang.Math.min(APPROVAL_MAST_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approvalMastOid", this.approvalMastOid, approvalMastOid));
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

   public static final long EXTERNALIZATION_VERSION_UID = 139550946335960179L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( approvalDate );
      output.writeObject( approvalMastOid );
      output.writeObject( approvalType );
      output.writeObject( approverDelegate );
      output.writeObject( approverId );
      output.writeObject( approverName );
      output.writeObject( ecoNo );
      output.writeObject( pboOid );
      output.writeObject( requestDate );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETEpmApprovalHis) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "approvalDate", approvalDate );
      output.setString( "approvalMastOid", approvalMastOid );
      output.setString( "approvalType", approvalType );
      output.setString( "approverDelegate", approverDelegate );
      output.setString( "approverId", approverId );
      output.setString( "approverName", approverName );
      output.setString( "ecoNo", ecoNo );
      output.setString( "pboOid", pboOid );
      output.setTimestamp( "requestDate", requestDate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      approvalDate = input.getTimestamp( "approvalDate" );
      approvalMastOid = input.getString( "approvalMastOid" );
      approvalType = input.getString( "approvalType" );
      approverDelegate = input.getString( "approverDelegate" );
      approverId = input.getString( "approverId" );
      approverName = input.getString( "approverName" );
      ecoNo = input.getString( "ecoNo" );
      pboOid = input.getString( "pboOid" );
      requestDate = input.getTimestamp( "requestDate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion139550946335960179L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      approvalDate = (java.sql.Timestamp) input.readObject();
      approvalMastOid = (java.lang.String) input.readObject();
      approvalType = (java.lang.String) input.readObject();
      approverDelegate = (java.lang.String) input.readObject();
      approverId = (java.lang.String) input.readObject();
      approverName = (java.lang.String) input.readObject();
      ecoNo = (java.lang.String) input.readObject();
      pboOid = (java.lang.String) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETEpmApprovalHis thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion139550946335960179L( input, readSerialVersionUID, superDone );
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
