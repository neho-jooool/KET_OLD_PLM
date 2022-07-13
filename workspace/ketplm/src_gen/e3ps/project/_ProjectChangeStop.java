package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectChangeStop implements wt.content.ContentHolder, e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectChangeStop.class.getName();

   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String CHANGE_DETIL = "changeDetil";
   static int CHANGE_DETIL_UPPER_LIMIT = -1;
   java.lang.String changeDetil;
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public java.lang.String getChangeDetil() {
      return changeDetil;
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public void setChangeDetil(java.lang.String changeDetil) throws wt.util.WTPropertyVetoException {
      changeDetilValidate(changeDetil);
      this.changeDetil = changeDetil;
   }
   void changeDetilValidate(java.lang.String changeDetil) throws wt.util.WTPropertyVetoException {
      if (CHANGE_DETIL_UPPER_LIMIT < 1) {
         try { CHANGE_DETIL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeDetil").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_DETIL_UPPER_LIMIT = 3000; }
      }
      if (changeDetil != null && !wt.fc.PersistenceHelper.checkStoredLength(changeDetil.toString(), CHANGE_DETIL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeDetil"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_DETIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeDetil", this.changeDetil, changeDetil));
   }

   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String CHANGE_DATE = "changeDate";
   java.sql.Timestamp changeDate;
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public java.sql.Timestamp getChangeDate() {
      return changeDate;
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public void setChangeDate(java.sql.Timestamp changeDate) throws wt.util.WTPropertyVetoException {
      changeDateValidate(changeDate);
      this.changeDate = changeDate;
   }
   void changeDateValidate(java.sql.Timestamp changeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String CHANGE_TYPE = "changeType";
   static int CHANGE_TYPE_UPPER_LIMIT = -1;
   java.lang.String changeType;
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public java.lang.String getChangeType() {
      return changeType;
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public void setChangeType(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      changeTypeValidate(changeType);
      this.changeType = changeType;
   }
   void changeTypeValidate(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      if (CHANGE_TYPE_UPPER_LIMIT < 1) {
         try { CHANGE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_TYPE_UPPER_LIMIT = 200; }
      }
      if (changeType != null && !wt.fc.PersistenceHelper.checkStoredLength(changeType.toString(), CHANGE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeType"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeType", this.changeType, changeType));
   }

   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String CHANGE_ATTR1 = "changeAttr1";
   static int CHANGE_ATTR1_UPPER_LIMIT = -1;
   java.lang.String changeAttr1;
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public java.lang.String getChangeAttr1() {
      return changeAttr1;
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public void setChangeAttr1(java.lang.String changeAttr1) throws wt.util.WTPropertyVetoException {
      changeAttr1Validate(changeAttr1);
      this.changeAttr1 = changeAttr1;
   }
   void changeAttr1Validate(java.lang.String changeAttr1) throws wt.util.WTPropertyVetoException {
      if (CHANGE_ATTR1_UPPER_LIMIT < 1) {
         try { CHANGE_ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeAttr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_ATTR1_UPPER_LIMIT = 200; }
      }
      if (changeAttr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(changeAttr1.toString(), CHANGE_ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeAttr1"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeAttr1", this.changeAttr1, changeAttr1));
   }

   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String CHANGE_ATTR2 = "changeAttr2";
   static int CHANGE_ATTR2_UPPER_LIMIT = -1;
   java.lang.String changeAttr2;
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public java.lang.String getChangeAttr2() {
      return changeAttr2;
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public void setChangeAttr2(java.lang.String changeAttr2) throws wt.util.WTPropertyVetoException {
      changeAttr2Validate(changeAttr2);
      this.changeAttr2 = changeAttr2;
   }
   void changeAttr2Validate(java.lang.String changeAttr2) throws wt.util.WTPropertyVetoException {
      if (CHANGE_ATTR2_UPPER_LIMIT < 1) {
         try { CHANGE_ATTR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeAttr2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_ATTR2_UPPER_LIMIT = 200; }
      }
      if (changeAttr2 != null && !wt.fc.PersistenceHelper.checkStoredLength(changeAttr2.toString(), CHANGE_ATTR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeAttr2"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_ATTR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeAttr2", this.changeAttr2, changeAttr2));
   }

   /**
    * Web Editor
    *
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * Web Editor
    *
    * @see e3ps.project.ProjectChangeStop
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * Web Editor
    *
    * @see e3ps.project.ProjectChangeStop
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
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * Web Editor Text
    *
    * @see e3ps.project.ProjectChangeStop
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * Web Editor Text
    *
    * @see e3ps.project.ProjectChangeStop
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
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String CHANGE_STOP_TYPE = "changeStopType";
   static int CHANGE_STOP_TYPE_UPPER_LIMIT = -1;
   java.lang.String changeStopType;
   /**
    * 변경사유구분
    *
    * @see e3ps.project.ProjectChangeStop
    */
   public java.lang.String getChangeStopType() {
      return changeStopType;
   }
   /**
    * 변경사유구분
    *
    * @see e3ps.project.ProjectChangeStop
    */
   public void setChangeStopType(java.lang.String changeStopType) throws wt.util.WTPropertyVetoException {
      changeStopTypeValidate(changeStopType);
      this.changeStopType = changeStopType;
   }
   void changeStopTypeValidate(java.lang.String changeStopType) throws wt.util.WTPropertyVetoException {
      if (CHANGE_STOP_TYPE_UPPER_LIMIT < 1) {
         try { CHANGE_STOP_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeStopType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_STOP_TYPE_UPPER_LIMIT = 200; }
      }
      if (changeStopType != null && !wt.fc.PersistenceHelper.checkStoredLength(changeStopType.toString(), CHANGE_STOP_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeStopType"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_STOP_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeStopType", this.changeStopType, changeStopType));
   }

   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String PCS_MASTER = "pcsMaster";
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public static final java.lang.String PCS_MASTER_REFERENCE = "pcsMasterReference";
   wt.fc.ObjectReference pcsMasterReference;
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public e3ps.project.ProjectMaster getPcsMaster() {
      return (pcsMasterReference != null) ? (e3ps.project.ProjectMaster) pcsMasterReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public wt.fc.ObjectReference getPcsMasterReference() {
      return pcsMasterReference;
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public void setPcsMaster(e3ps.project.ProjectMaster the_pcsMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPcsMasterReference(the_pcsMaster == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProjectMaster) the_pcsMaster));
   }
   /**
    * @see e3ps.project.ProjectChangeStop
    */
   public void setPcsMasterReference(wt.fc.ObjectReference the_pcsMasterReference) throws wt.util.WTPropertyVetoException {
      pcsMasterReferenceValidate(the_pcsMasterReference);
      pcsMasterReference = (wt.fc.ObjectReference) the_pcsMasterReference;
   }
   void pcsMasterReferenceValidate(wt.fc.ObjectReference the_pcsMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_pcsMasterReference == null || the_pcsMasterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pcsMasterReference") },
               new java.beans.PropertyChangeEvent(this, "pcsMasterReference", this.pcsMasterReference, pcsMasterReference));
      if (the_pcsMasterReference != null && the_pcsMasterReference.getReferencedClass() != null &&
            !e3ps.project.ProjectMaster.class.isAssignableFrom(the_pcsMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pcsMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "pcsMasterReference", this.pcsMasterReference, pcsMasterReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7596299170184597515L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( changeAttr1 );
      output.writeObject( changeAttr2 );
      output.writeObject( changeDate );
      output.writeObject( changeDetil );
      output.writeObject( changeStopType );
      output.writeObject( changeType );
      output.writeObject( owner );
      output.writeObject( pcsMasterReference );
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
      readVersion( (e3ps.project.ProjectChangeStop) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "changeAttr1", changeAttr1 );
      output.setString( "changeAttr2", changeAttr2 );
      output.setTimestamp( "changeDate", changeDate );
      output.setString( "changeDetil", changeDetil );
      output.setString( "changeStopType", changeStopType );
      output.setString( "changeType", changeType );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "pcsMasterReference", pcsMasterReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      changeAttr1 = input.getString( "changeAttr1" );
      changeAttr2 = input.getString( "changeAttr2" );
      changeDate = input.getTimestamp( "changeDate" );
      changeDetil = input.getString( "changeDetil" );
      changeStopType = input.getString( "changeStopType" );
      changeType = input.getString( "changeType" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      pcsMasterReference = (wt.fc.ObjectReference) input.readObject( "pcsMasterReference", pcsMasterReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion7596299170184597515L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      changeAttr1 = (java.lang.String) input.readObject();
      changeAttr2 = (java.lang.String) input.readObject();
      changeDate = (java.sql.Timestamp) input.readObject();
      changeDetil = (java.lang.String) input.readObject();
      changeStopType = (java.lang.String) input.readObject();
      changeType = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      pcsMasterReference = (wt.fc.ObjectReference) input.readObject();
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

   protected boolean readVersion( ProjectChangeStop thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7596299170184597515L( input, readSerialVersionUID, superDone );
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
