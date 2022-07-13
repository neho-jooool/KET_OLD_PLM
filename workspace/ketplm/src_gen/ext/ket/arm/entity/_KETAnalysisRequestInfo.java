package ext.ket.arm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETAnalysisRequestInfo extends wt.enterprise.Managed implements wt.content.FormatContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.arm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETAnalysisRequestInfo.class.getName();

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   static int WEB_EDITOR_UPPER_LIMIT = -1;
   java.lang.String webEditor;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public java.lang.String getWebEditor() {
      return webEditor;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public void setWebEditor(java.lang.String webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.String webEditor) throws wt.util.WTPropertyVetoException {
      if (WEB_EDITOR_UPPER_LIMIT < 1) {
         try { WEB_EDITOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("webEditor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WEB_EDITOR_UPPER_LIMIT = 200; }
      }
      if (webEditor != null && !wt.fc.PersistenceHelper.checkStoredLength(webEditor.toString(), WEB_EDITOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "webEditor"), java.lang.String.valueOf(java.lang.Math.min(WEB_EDITOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "webEditor", this.webEditor, webEditor));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   static int WEB_EDITOR_TEXT_UPPER_LIMIT = -1;
   java.lang.String webEditorText;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public java.lang.String getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public void setWebEditorText(java.lang.String webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.String webEditorText) throws wt.util.WTPropertyVetoException {
      if (WEB_EDITOR_TEXT_UPPER_LIMIT < 1) {
         try { WEB_EDITOR_TEXT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("webEditorText").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WEB_EDITOR_TEXT_UPPER_LIMIT = 200; }
      }
      if (webEditorText != null && !wt.fc.PersistenceHelper.checkStoredLength(webEditorText.toString(), WEB_EDITOR_TEXT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "webEditorText"), java.lang.String.valueOf(java.lang.Math.min(WEB_EDITOR_TEXT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "webEditorText", this.webEditorText, webEditorText));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public static final java.lang.String DESIGN_SPEC = "designSpec";
   static int DESIGN_SPEC_UPPER_LIMIT = -1;
   java.lang.String designSpec;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public java.lang.String getDesignSpec() {
      return designSpec;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public void setDesignSpec(java.lang.String designSpec) throws wt.util.WTPropertyVetoException {
      designSpecValidate(designSpec);
      this.designSpec = designSpec;
   }
   void designSpecValidate(java.lang.String designSpec) throws wt.util.WTPropertyVetoException {
      if (DESIGN_SPEC_UPPER_LIMIT < 1) {
         try { DESIGN_SPEC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("designSpec").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESIGN_SPEC_UPPER_LIMIT = 200; }
      }
      if (designSpec != null && !wt.fc.PersistenceHelper.checkStoredLength(designSpec.toString(), DESIGN_SPEC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "designSpec"), java.lang.String.valueOf(java.lang.Math.min(DESIGN_SPEC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "designSpec", this.designSpec, designSpec));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public static final java.lang.String REQUESTED_TERM = "requestedTerm";
   static int REQUESTED_TERM_UPPER_LIMIT = -1;
   java.lang.String requestedTerm;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public java.lang.String getRequestedTerm() {
      return requestedTerm;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public void setRequestedTerm(java.lang.String requestedTerm) throws wt.util.WTPropertyVetoException {
      requestedTermValidate(requestedTerm);
      this.requestedTerm = requestedTerm;
   }
   void requestedTermValidate(java.lang.String requestedTerm) throws wt.util.WTPropertyVetoException {
      if (REQUESTED_TERM_UPPER_LIMIT < 1) {
         try { REQUESTED_TERM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestedTerm").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUESTED_TERM_UPPER_LIMIT = 200; }
      }
      if (requestedTerm != null && !wt.fc.PersistenceHelper.checkStoredLength(requestedTerm.toString(), REQUESTED_TERM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestedTerm"), java.lang.String.valueOf(java.lang.Math.min(REQUESTED_TERM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestedTerm", this.requestedTerm, requestedTerm));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public static final java.lang.String ETC = "etc";
   static int ETC_UPPER_LIMIT = -1;
   java.lang.String etc;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public java.lang.String getEtc() {
      return etc;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestInfo
    */
   public void setEtc(java.lang.String etc) throws wt.util.WTPropertyVetoException {
      etcValidate(etc);
      this.etc = etc;
   }
   void etcValidate(java.lang.String etc) throws wt.util.WTPropertyVetoException {
      if (ETC_UPPER_LIMIT < 1) {
         try { ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_UPPER_LIMIT = 200; }
      }
      if (etc != null && !wt.fc.PersistenceHelper.checkStoredLength(etc.toString(), ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etc"), java.lang.String.valueOf(java.lang.Math.min(ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etc", this.etc, etc));
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

   public static final long EXTERNALIZATION_VERSION_UID = -2906080722702639750L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( designSpec );
      output.writeObject( etc );
      output.writeObject( format );
      output.writeObject( requestedTerm );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
         output.writeObject( primary );
      }

   }

   protected void super_writeExternal_KETAnalysisRequestInfo(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.arm.entity.KETAnalysisRequestInfo) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETAnalysisRequestInfo(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "designSpec", designSpec );
      output.setString( "etc", etc );
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setString( "requestedTerm", requestedTerm );
      output.setString( "webEditor", webEditor );
      output.setString( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      designSpec = input.getString( "designSpec" );
      etc = input.getString( "etc" );
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      requestedTerm = input.getString( "requestedTerm" );
      webEditor = input.getString( "webEditor" );
      webEditorText = input.getString( "webEditorText" );
   }

   boolean readVersion_2906080722702639750L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      designSpec = (java.lang.String) input.readObject();
      etc = (java.lang.String) input.readObject();
      format = (wt.content.DataFormatReference) input.readObject();
      requestedTerm = (java.lang.String) input.readObject();
      webEditor = (java.lang.String) input.readObject();
      webEditorText = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETAnalysisRequestInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2906080722702639750L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETAnalysisRequestInfo( _KETAnalysisRequestInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
