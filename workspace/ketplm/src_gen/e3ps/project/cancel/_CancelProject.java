package e3ps.project.cancel;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CancelProject implements wt.content.ContentHolder, e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.cancel.cancelResource";
   static final java.lang.String CLASSNAME = CancelProject.class.getName();

   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String CANCEL_HISTORY = "cancelHistory";
   static int CANCEL_HISTORY_UPPER_LIMIT = -1;
   java.lang.String cancelHistory;
   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public java.lang.String getCancelHistory() {
      return cancelHistory;
   }
   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public void setCancelHistory(java.lang.String cancelHistory) throws wt.util.WTPropertyVetoException {
      cancelHistoryValidate(cancelHistory);
      this.cancelHistory = cancelHistory;
   }
   void cancelHistoryValidate(java.lang.String cancelHistory) throws wt.util.WTPropertyVetoException {
      if (CANCEL_HISTORY_UPPER_LIMIT < 1) {
         try { CANCEL_HISTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cancelHistory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CANCEL_HISTORY_UPPER_LIMIT = 200; }
      }
      if (cancelHistory != null && !wt.fc.PersistenceHelper.checkStoredLength(cancelHistory.toString(), CANCEL_HISTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cancelHistory"), java.lang.String.valueOf(java.lang.Math.min(CANCEL_HISTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cancelHistory", this.cancelHistory, cancelHistory));
   }

   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String CANCEL_TYPE = "cancelType";
   static int CANCEL_TYPE_UPPER_LIMIT = -1;
   java.lang.String cancelType;
   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public java.lang.String getCancelType() {
      return cancelType;
   }
   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public void setCancelType(java.lang.String cancelType) throws wt.util.WTPropertyVetoException {
      cancelTypeValidate(cancelType);
      this.cancelType = cancelType;
   }
   void cancelTypeValidate(java.lang.String cancelType) throws wt.util.WTPropertyVetoException {
      if (CANCEL_TYPE_UPPER_LIMIT < 1) {
         try { CANCEL_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cancelType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CANCEL_TYPE_UPPER_LIMIT = 200; }
      }
      if (cancelType != null && !wt.fc.PersistenceHelper.checkStoredLength(cancelType.toString(), CANCEL_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cancelType"), java.lang.String.valueOf(java.lang.Math.min(CANCEL_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cancelType", this.cancelType, cancelType));
   }

   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String CANCEL_DATE = "cancelDate";
   java.sql.Timestamp cancelDate;
   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public java.sql.Timestamp getCancelDate() {
      return cancelDate;
   }
   /**
    * @see e3ps.project.cancel.CancelProject
    */
   public void setCancelDate(java.sql.Timestamp cancelDate) throws wt.util.WTPropertyVetoException {
      cancelDateValidate(cancelDate);
      this.cancelDate = cancelDate;
   }
   void cancelDateValidate(java.sql.Timestamp cancelDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Web Editor
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * Web Editor
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * Web Editor
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Web Editor Text
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * Web Editor Text
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * Web Editor Text
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 변경사유구분
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String CANCEL_REASON_TYPE = "cancelReasonType";
   static int CANCEL_REASON_TYPE_UPPER_LIMIT = -1;
   java.lang.String cancelReasonType;
   /**
    * 변경사유구분
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public java.lang.String getCancelReasonType() {
      return cancelReasonType;
   }
   /**
    * 변경사유구분
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public void setCancelReasonType(java.lang.String cancelReasonType) throws wt.util.WTPropertyVetoException {
      cancelReasonTypeValidate(cancelReasonType);
      this.cancelReasonType = cancelReasonType;
   }
   void cancelReasonTypeValidate(java.lang.String cancelReasonType) throws wt.util.WTPropertyVetoException {
      if (CANCEL_REASON_TYPE_UPPER_LIMIT < 1) {
         try { CANCEL_REASON_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cancelReasonType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CANCEL_REASON_TYPE_UPPER_LIMIT = 200; }
      }
      if (cancelReasonType != null && !wt.fc.PersistenceHelper.checkStoredLength(cancelReasonType.toString(), CANCEL_REASON_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cancelReasonType"), java.lang.String.valueOf(java.lang.Math.min(CANCEL_REASON_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cancelReasonType", this.cancelReasonType, cancelReasonType));
   }

   /**
    * 목표회수비용 sum
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String TARGET_COST = "targetCost";
   static int TARGET_COST_UPPER_LIMIT = -1;
   java.lang.String targetCost;
   /**
    * 목표회수비용 sum
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public java.lang.String getTargetCost() {
      return targetCost;
   }
   /**
    * 목표회수비용 sum
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public void setTargetCost(java.lang.String targetCost) throws wt.util.WTPropertyVetoException {
      targetCostValidate(targetCost);
      this.targetCost = targetCost;
   }
   void targetCostValidate(java.lang.String targetCost) throws wt.util.WTPropertyVetoException {
      if (TARGET_COST_UPPER_LIMIT < 1) {
         try { TARGET_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_COST_UPPER_LIMIT = 200; }
      }
      if (targetCost != null && !wt.fc.PersistenceHelper.checkStoredLength(targetCost.toString(), TARGET_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetCost"), java.lang.String.valueOf(java.lang.Math.min(TARGET_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetCost", this.targetCost, targetCost));
   }

   /**
    * 실제 회수비용 sum
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public static final java.lang.String REAL_COST = "realCost";
   static int REAL_COST_UPPER_LIMIT = -1;
   java.lang.String realCost;
   /**
    * 실제 회수비용 sum
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public java.lang.String getRealCost() {
      return realCost;
   }
   /**
    * 실제 회수비용 sum
    *
    * @see e3ps.project.cancel.CancelProject
    */
   public void setRealCost(java.lang.String realCost) throws wt.util.WTPropertyVetoException {
      realCostValidate(realCost);
      this.realCost = realCost;
   }
   void realCostValidate(java.lang.String realCost) throws wt.util.WTPropertyVetoException {
      if (REAL_COST_UPPER_LIMIT < 1) {
         try { REAL_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("realCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REAL_COST_UPPER_LIMIT = 200; }
      }
      if (realCost != null && !wt.fc.PersistenceHelper.checkStoredLength(realCost.toString(), REAL_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "realCost"), java.lang.String.valueOf(java.lang.Math.min(REAL_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "realCost", this.realCost, realCost));
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

   wt.org.WTPrincipalReference owner;
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public wt.org.WTPrincipalReference getOwner() {
      return owner;
   }
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      ownerValidate(owner);
      this.owner = owner;
   }
   void ownerValidate(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      if (owner == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "owner") },
               new java.beans.PropertyChangeEvent(this, "owner", this.owner, owner));
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

   public static final long EXTERNALIZATION_VERSION_UID = 2880637727574651735L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( cancelDate );
      output.writeObject( cancelHistory );
      output.writeObject( cancelReasonType );
      output.writeObject( cancelType );
      output.writeObject( owner );
      output.writeObject( realCost );
      output.writeObject( targetCost );
      output.writeObject( thePersistInfo );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.cancel.CancelProject) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "cancelDate", cancelDate );
      output.setString( "cancelHistory", cancelHistory );
      output.setString( "cancelReasonType", cancelReasonType );
      output.setString( "cancelType", cancelType );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "realCost", realCost );
      output.setString( "targetCost", targetCost );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      cancelDate = input.getTimestamp( "cancelDate" );
      cancelHistory = input.getString( "cancelHistory" );
      cancelReasonType = input.getString( "cancelReasonType" );
      cancelType = input.getString( "cancelType" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      realCost = input.getString( "realCost" );
      targetCost = input.getString( "targetCost" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion2880637727574651735L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      cancelDate = (java.sql.Timestamp) input.readObject();
      cancelHistory = (java.lang.String) input.readObject();
      cancelReasonType = (java.lang.String) input.readObject();
      cancelType = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      realCost = (java.lang.String) input.readObject();
      targetCost = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( CancelProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2880637727574651735L( input, readSerialVersionUID, superDone );
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
