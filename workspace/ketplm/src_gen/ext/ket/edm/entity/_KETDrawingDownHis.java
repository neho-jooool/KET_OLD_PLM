package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDrawingDownHis implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDrawingDownHis.class.getName();

   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String DOWNLOAD_FILE_NAME = "downloadFileName";
   static int DOWNLOAD_FILE_NAME_UPPER_LIMIT = -1;
   java.lang.String downloadFileName;
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public java.lang.String getDownloadFileName() {
      return downloadFileName;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setDownloadFileName(java.lang.String downloadFileName) throws wt.util.WTPropertyVetoException {
      downloadFileNameValidate(downloadFileName);
      this.downloadFileName = downloadFileName;
   }
   void downloadFileNameValidate(java.lang.String downloadFileName) throws wt.util.WTPropertyVetoException {
      if (DOWNLOAD_FILE_NAME_UPPER_LIMIT < 1) {
         try { DOWNLOAD_FILE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("downloadFileName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOWNLOAD_FILE_NAME_UPPER_LIMIT = 200; }
      }
      if (downloadFileName != null && !wt.fc.PersistenceHelper.checkStoredLength(downloadFileName.toString(), DOWNLOAD_FILE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "downloadFileName"), java.lang.String.valueOf(java.lang.Math.min(DOWNLOAD_FILE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "downloadFileName", this.downloadFileName, downloadFileName));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String DOWNLOAD_REASON = "downloadReason";
   static int DOWNLOAD_REASON_UPPER_LIMIT = -1;
   java.lang.String downloadReason;
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public java.lang.String getDownloadReason() {
      return downloadReason;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setDownloadReason(java.lang.String downloadReason) throws wt.util.WTPropertyVetoException {
      downloadReasonValidate(downloadReason);
      this.downloadReason = downloadReason;
   }
   void downloadReasonValidate(java.lang.String downloadReason) throws wt.util.WTPropertyVetoException {
      if (DOWNLOAD_REASON_UPPER_LIMIT < 1) {
         try { DOWNLOAD_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("downloadReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOWNLOAD_REASON_UPPER_LIMIT = 4000; }
      }
      if (downloadReason != null && !wt.fc.PersistenceHelper.checkStoredLength(downloadReason.toString(), DOWNLOAD_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "downloadReason"), java.lang.String.valueOf(java.lang.Math.min(DOWNLOAD_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "downloadReason", this.downloadReason, downloadReason));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String DOWNLOAD_DATE = "downloadDate";
   java.sql.Timestamp downloadDate;
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public java.sql.Timestamp getDownloadDate() {
      return downloadDate;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setDownloadDate(java.sql.Timestamp downloadDate) throws wt.util.WTPropertyVetoException {
      downloadDateValidate(downloadDate);
      this.downloadDate = downloadDate;
   }
   void downloadDateValidate(java.sql.Timestamp downloadDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String DIST_SUBCONTRACTOR = "distSubcontractor";
   static int DIST_SUBCONTRACTOR_UPPER_LIMIT = -1;
   java.lang.String distSubcontractor;
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public java.lang.String getDistSubcontractor() {
      return distSubcontractor;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setDistSubcontractor(java.lang.String distSubcontractor) throws wt.util.WTPropertyVetoException {
      distSubcontractorValidate(distSubcontractor);
      this.distSubcontractor = distSubcontractor;
   }
   void distSubcontractorValidate(java.lang.String distSubcontractor) throws wt.util.WTPropertyVetoException {
      if (DIST_SUBCONTRACTOR_UPPER_LIMIT < 1) {
         try { DIST_SUBCONTRACTOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("distSubcontractor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIST_SUBCONTRACTOR_UPPER_LIMIT = 200; }
      }
      if (distSubcontractor != null && !wt.fc.PersistenceHelper.checkStoredLength(distSubcontractor.toString(), DIST_SUBCONTRACTOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distSubcontractor"), java.lang.String.valueOf(java.lang.Math.min(DIST_SUBCONTRACTOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "distSubcontractor", this.distSubcontractor, distSubcontractor));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String DIST_REQ = "distReq";
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String DIST_REQ_REFERENCE = "distReqReference";
   wt.fc.ObjectReference distReqReference;
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public ext.ket.edm.entity.KETDrawingDistRequest getDistReq() {
      return (distReqReference != null) ? (ext.ket.edm.entity.KETDrawingDistRequest) distReqReference.getObject() : null;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public wt.fc.ObjectReference getDistReqReference() {
      return distReqReference;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setDistReq(ext.ket.edm.entity.KETDrawingDistRequest the_distReq) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDistReqReference(the_distReq == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.edm.entity.KETDrawingDistRequest) the_distReq));
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setDistReqReference(wt.fc.ObjectReference the_distReqReference) throws wt.util.WTPropertyVetoException {
      distReqReferenceValidate(the_distReqReference);
      distReqReference = (wt.fc.ObjectReference) the_distReqReference;
   }
   void distReqReferenceValidate(wt.fc.ObjectReference the_distReqReference) throws wt.util.WTPropertyVetoException {
      if (the_distReqReference == null || the_distReqReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distReqReference") },
               new java.beans.PropertyChangeEvent(this, "distReqReference", this.distReqReference, distReqReference));
      if (the_distReqReference != null && the_distReqReference.getReferencedClass() != null &&
            !ext.ket.edm.entity.KETDrawingDistRequest.class.isAssignableFrom(the_distReqReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distReqReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "distReqReference", this.distReqReference, distReqReference));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String USER = "user";
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDownHis
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference == null || the_userReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference") },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -2039371351147024908L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( distReqReference );
      output.writeObject( distSubcontractor );
      output.writeObject( downloadDate );
      output.writeObject( downloadFileName );
      output.writeObject( downloadReason );
      output.writeObject( thePersistInfo );
      output.writeObject( userReference );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETDrawingDownHis) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "distReqReference", distReqReference, wt.fc.ObjectReference.class, true );
      output.setString( "distSubcontractor", distSubcontractor );
      output.setTimestamp( "downloadDate", downloadDate );
      output.setString( "downloadFileName", downloadFileName );
      output.setString( "downloadReason", downloadReason );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      distReqReference = (wt.fc.ObjectReference) input.readObject( "distReqReference", distReqReference, wt.fc.ObjectReference.class, true );
      distSubcontractor = input.getString( "distSubcontractor" );
      downloadDate = input.getTimestamp( "downloadDate" );
      downloadFileName = input.getString( "downloadFileName" );
      downloadReason = input.getString( "downloadReason" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion_2039371351147024908L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      distReqReference = (wt.fc.ObjectReference) input.readObject();
      distSubcontractor = (java.lang.String) input.readObject();
      downloadDate = (java.sql.Timestamp) input.readObject();
      downloadFileName = (java.lang.String) input.readObject();
      downloadReason = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDrawingDownHis thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2039371351147024908L( input, readSerialVersionUID, superDone );
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
