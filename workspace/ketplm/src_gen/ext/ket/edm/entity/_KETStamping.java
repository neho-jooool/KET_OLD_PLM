package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETStamping implements wt.content.FormatContentHolder, wt.access.AccessControlled, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETStamping.class.getName();

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String STAMP_STATUS = "stampStatus";
   static int STAMP_STATUS_UPPER_LIMIT = -1;
   java.lang.String stampStatus;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public java.lang.String getStampStatus() {
      return stampStatus;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setStampStatus(java.lang.String stampStatus) throws wt.util.WTPropertyVetoException {
      stampStatusValidate(stampStatus);
      this.stampStatus = stampStatus;
   }
   void stampStatusValidate(java.lang.String stampStatus) throws wt.util.WTPropertyVetoException {
      if (STAMP_STATUS_UPPER_LIMIT < 1) {
         try { STAMP_STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("stampStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STAMP_STATUS_UPPER_LIMIT = 200; }
      }
      if (stampStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(stampStatus.toString(), STAMP_STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "stampStatus"), java.lang.String.valueOf(java.lang.Math.min(STAMP_STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "stampStatus", this.stampStatus, stampStatus));
   }

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String ERROR_TYPE = "errorType";
   static int ERROR_TYPE_UPPER_LIMIT = -1;
   java.lang.String errorType;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public java.lang.String getErrorType() {
      return errorType;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setErrorType(java.lang.String errorType) throws wt.util.WTPropertyVetoException {
      errorTypeValidate(errorType);
      this.errorType = errorType;
   }
   void errorTypeValidate(java.lang.String errorType) throws wt.util.WTPropertyVetoException {
      if (ERROR_TYPE_UPPER_LIMIT < 1) {
         try { ERROR_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("errorType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ERROR_TYPE_UPPER_LIMIT = 200; }
      }
      if (errorType != null && !wt.fc.PersistenceHelper.checkStoredLength(errorType.toString(), ERROR_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "errorType"), java.lang.String.valueOf(java.lang.Math.min(ERROR_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "errorType", this.errorType, errorType));
   }

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String ERROR_LOG = "errorLog";
   static int ERROR_LOG_UPPER_LIMIT = -1;
   java.lang.String errorLog;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public java.lang.String getErrorLog() {
      return errorLog;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setErrorLog(java.lang.String errorLog) throws wt.util.WTPropertyVetoException {
      errorLogValidate(errorLog);
      this.errorLog = errorLog;
   }
   void errorLogValidate(java.lang.String errorLog) throws wt.util.WTPropertyVetoException {
      if (ERROR_LOG_UPPER_LIMIT < 1) {
         try { ERROR_LOG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("errorLog").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ERROR_LOG_UPPER_LIMIT = 200; }
      }
      if (errorLog != null && !wt.fc.PersistenceHelper.checkStoredLength(errorLog.toString(), ERROR_LOG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "errorLog"), java.lang.String.valueOf(java.lang.Math.min(ERROR_LOG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "errorLog", this.errorLog, errorLog));
   }

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String COMPLETED_DATE = "completedDate";
   java.sql.Timestamp completedDate;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public java.sql.Timestamp getCompletedDate() {
      return completedDate;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setCompletedDate(java.sql.Timestamp completedDate) throws wt.util.WTPropertyVetoException {
      completedDateValidate(completedDate);
      this.completedDate = completedDate;
   }
   void completedDateValidate(java.sql.Timestamp completedDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String ERROR_OCCUR_DATE = "errorOccurDate";
   java.sql.Timestamp errorOccurDate;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public java.sql.Timestamp getErrorOccurDate() {
      return errorOccurDate;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setErrorOccurDate(java.sql.Timestamp errorOccurDate) throws wt.util.WTPropertyVetoException {
      errorOccurDateValidate(errorOccurDate);
      this.errorOccurDate = errorOccurDate;
   }
   void errorOccurDateValidate(java.sql.Timestamp errorOccurDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String QUEUE_INPUT_DATE = "queueInputDate";
   java.sql.Timestamp queueInputDate;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public java.sql.Timestamp getQueueInputDate() {
      return queueInputDate;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setQueueInputDate(java.sql.Timestamp queueInputDate) throws wt.util.WTPropertyVetoException {
      queueInputDateValidate(queueInputDate);
      this.queueInputDate = queueInputDate;
   }
   void queueInputDateValidate(java.sql.Timestamp queueInputDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String EXECUTE_NO = "executeNo";
   int executeNo;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public int getExecuteNo() {
      return executeNo;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setExecuteNo(int executeNo) throws wt.util.WTPropertyVetoException {
      executeNoValidate(executeNo);
      this.executeNo = executeNo;
   }
   void executeNoValidate(int executeNo) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public static final java.lang.String STAMP_FILE_PATH = "stampFilePath";
   static int STAMP_FILE_PATH_UPPER_LIMIT = -1;
   java.lang.String stampFilePath;
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public java.lang.String getStampFilePath() {
      return stampFilePath;
   }
   /**
    * @see ext.ket.edm.entity.KETStamping
    */
   public void setStampFilePath(java.lang.String stampFilePath) throws wt.util.WTPropertyVetoException {
      stampFilePathValidate(stampFilePath);
      this.stampFilePath = stampFilePath;
   }
   void stampFilePathValidate(java.lang.String stampFilePath) throws wt.util.WTPropertyVetoException {
      if (STAMP_FILE_PATH_UPPER_LIMIT < 1) {
         try { STAMP_FILE_PATH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("stampFilePath").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STAMP_FILE_PATH_UPPER_LIMIT = 200; }
      }
      if (stampFilePath != null && !wt.fc.PersistenceHelper.checkStoredLength(stampFilePath.toString(), STAMP_FILE_PATH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "stampFilePath"), java.lang.String.valueOf(java.lang.Math.min(STAMP_FILE_PATH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "stampFilePath", this.stampFilePath, stampFilePath));
   }

   wt.content.ContentItem primary;
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public wt.content.ContentItem getPrimary() {
      return primary;
   }
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public void setPrimary(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
      primaryValidate(primary);
      this.primary = primary;
   }
   void primaryValidate(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
   }

   wt.content.DataFormatReference format;
   /**
    * @see wt.content.FormatContentHolder
    */
   public wt.content.DataFormatReference getFormat() {
      return format;
   }
   /**
    * @see wt.content.FormatContentHolder
    */
   public void setFormat(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      formatValidate(format);
      this.format = format;
   }
   void formatValidate(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      if (format == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "format") },
               new java.beans.PropertyChangeEvent(this, "format", this.format, format));
   }

   /**
    * Derived from {@link wt.content.DataFormatReference#getFormatName()}
    *
    * @see wt.content.FormatContentHolder
    */
   public java.lang.String getFormatName() {
      try { return (java.lang.String) ((wt.content.DataFormatReference) getFormat()).getFormatName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   java.util.Vector contentVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getContentVector() {
      return contentVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setContentVector(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
      contentVectorValidate(contentVector);
      this.contentVector = contentVector;
   }
   void contentVectorValidate(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
   }

   boolean hasContents;
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public boolean isHasContents() {
      return hasContents;
   }
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public void setHasContents(boolean hasContents) throws wt.util.WTPropertyVetoException {
      hasContentsValidate(hasContents);
      this.hasContents = hasContents;
   }
   void hasContentsValidate(boolean hasContents) throws wt.util.WTPropertyVetoException {
   }

   wt.content.HttpContentOperation operation;
   /**
    * @see wt.content.ContentHolder
    */
   public wt.content.HttpContentOperation getOperation() {
      return operation;
   }
   /**
    * @see wt.content.ContentHolder
    */
   public void setOperation(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
      operationValidate(operation);
      this.operation = operation;
   }
   void operationValidate(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
   }

   java.util.Vector httpVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getHttpVector() {
      return httpVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setHttpVector(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
      httpVectorValidate(httpVector);
      this.httpVector = httpVector;
   }
   void httpVectorValidate(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -6219262953081116548L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( completedDate );
      output.writeObject( errorLog );
      output.writeObject( errorOccurDate );
      output.writeObject( errorType );
      output.writeInt( executeNo );
      output.writeObject( format );
      output.writeObject( queueInputDate );
      output.writeObject( stampFilePath );
      output.writeObject( stampStatus );
      output.writeObject( thePersistInfo );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
         output.writeObject( primary );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETStamping) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "completedDate", completedDate );
      output.setString( "errorLog", errorLog );
      output.setTimestamp( "errorOccurDate", errorOccurDate );
      output.setString( "errorType", errorType );
      output.setInt( "executeNo", executeNo );
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setTimestamp( "queueInputDate", queueInputDate );
      output.setString( "stampFilePath", stampFilePath );
      output.setString( "stampStatus", stampStatus );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      completedDate = input.getTimestamp( "completedDate" );
      errorLog = input.getString( "errorLog" );
      errorOccurDate = input.getTimestamp( "errorOccurDate" );
      errorType = input.getString( "errorType" );
      executeNo = input.getInt( "executeNo" );
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      queueInputDate = input.getTimestamp( "queueInputDate" );
      stampFilePath = input.getString( "stampFilePath" );
      stampStatus = input.getString( "stampStatus" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_6219262953081116548L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      completedDate = (java.sql.Timestamp) input.readObject();
      errorLog = (java.lang.String) input.readObject();
      errorOccurDate = (java.sql.Timestamp) input.readObject();
      errorType = (java.lang.String) input.readObject();
      executeNo = input.readInt();
      format = (wt.content.DataFormatReference) input.readObject();
      queueInputDate = (java.sql.Timestamp) input.readObject();
      stampFilePath = (java.lang.String) input.readObject();
      stampStatus = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETStamping thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6219262953081116548L( input, readSerialVersionUID, superDone );
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
