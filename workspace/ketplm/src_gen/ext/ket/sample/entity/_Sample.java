package ext.ket.sample.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _Sample implements wt.content.FormatContentHolder, wt.access.AccessControlled, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sample.entity.entityResource";
   static final java.lang.String CLASSNAME = Sample.class.getName();

   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public static final java.lang.String NUM = "num";
   int num = 0;
   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public int getNum() {
      return num;
   }
   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public void setNum(int num) throws wt.util.WTPropertyVetoException {
      numValidate(num);
      this.num = num;
   }
   void numValidate(int num) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public void setName(java.lang.String name) throws wt.util.WTPropertyVetoException {
      nameValidate(name);
      this.name = name;
   }
   void nameValidate(java.lang.String name) throws wt.util.WTPropertyVetoException {
      if (NAME_UPPER_LIMIT < 1) {
         try { NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("name").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAME_UPPER_LIMIT = 200; }
      }
      if (name != null && !wt.fc.PersistenceHelper.checkStoredLength(name.toString(), NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "name"), java.lang.String.valueOf(java.lang.Math.min(NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "name", this.name, name));
   }

   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public static final java.lang.String TITLE = "title";
   static int TITLE_UPPER_LIMIT = -1;
   java.lang.String title;
   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public java.lang.String getTitle() {
      return title;
   }
   /**
    * ??
    *
    * @see ext.ket.sample.entity.Sample
    */
   public void setTitle(java.lang.String title) throws wt.util.WTPropertyVetoException {
      titleValidate(title);
      this.title = title;
   }
   void titleValidate(java.lang.String title) throws wt.util.WTPropertyVetoException {
      if (TITLE_UPPER_LIMIT < 1) {
         try { TITLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("title").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TITLE_UPPER_LIMIT = 200; }
      }
      if (title != null && !wt.fc.PersistenceHelper.checkStoredLength(title.toString(), TITLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "title"), java.lang.String.valueOf(java.lang.Math.min(TITLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "title", this.title, title));
   }

   /**
    * ???? Data
    *
    * @see ext.ket.sample.entity.Sample
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * ???? Data
    *
    * @see ext.ket.sample.entity.Sample
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * ???? Data
    *
    * @see ext.ket.sample.entity.Sample
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ???? Text
    *
    * @see ext.ket.sample.entity.Sample
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * ???? Text
    *
    * @see ext.ket.sample.entity.Sample
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * ???? Text
    *
    * @see ext.ket.sample.entity.Sample
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -3680005926452556536L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( format );
      output.writeObject( name );
      output.writeInt( num );
      output.writeObject( thePersistInfo );
      output.writeObject( title );
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


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sample.entity.Sample) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setString( "name", name );
      output.setInt( "num", num );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "title", title );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      name = input.getString( "name" );
      num = input.getInt( "num" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      title = input.getString( "title" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion_3680005926452556536L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      format = (wt.content.DataFormatReference) input.readObject();
      name = (java.lang.String) input.readObject();
      num = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      title = (java.lang.String) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( Sample thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3680005926452556536L( input, readSerialVersionUID, superDone );
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
