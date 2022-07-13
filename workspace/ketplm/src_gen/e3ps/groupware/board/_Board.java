package e3ps.groupware.board;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _Board implements wt.content.ContentHolder, e3ps.common.impl.Tree, e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.board.boardResource";
   static final java.lang.String CLASSNAME = Board.class.getName();

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String TITLE = "title";
   static int TITLE_UPPER_LIMIT = -1;
   java.lang.String title;
   /**
    * @see e3ps.groupware.board.Board
    */
   public java.lang.String getTitle() {
      return title;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setTitle(java.lang.String title) throws wt.util.WTPropertyVetoException {
      titleValidate(title);
      this.title = title;
   }
   void titleValidate(java.lang.String title) throws wt.util.WTPropertyVetoException {
      if (TITLE_UPPER_LIMIT < 1) {
         try { TITLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("title").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TITLE_UPPER_LIMIT = 2000; }
      }
      if (title != null && !wt.fc.PersistenceHelper.checkStoredLength(title.toString(), TITLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "title"), java.lang.String.valueOf(java.lang.Math.min(TITLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "title", this.title, title));
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String CONTENTS = "contents";
   java.lang.Object contents;
   /**
    * @see e3ps.groupware.board.Board
    */
   public java.lang.Object getContents() {
      return contents;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setContents(java.lang.Object contents) throws wt.util.WTPropertyVetoException {
      contentsValidate(contents);
      this.contents = contents;
   }
   void contentsValidate(java.lang.Object contents) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String READ_COUNT = "readCount";
   int readCount = 0;
   /**
    * @see e3ps.groupware.board.Board
    */
   public int getReadCount() {
      return readCount;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setReadCount(int readCount) throws wt.util.WTPropertyVetoException {
      readCountValidate(readCount);
      this.readCount = readCount;
   }
   void readCountValidate(int readCount) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String DEPTH = "depth";
   int depth;
   /**
    * @see e3ps.groupware.board.Board
    */
   public int getDepth() {
      return depth;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setDepth(int depth) throws wt.util.WTPropertyVetoException {
      depthValidate(depth);
      this.depth = depth;
   }
   void depthValidate(int depth) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String STATE = "state";
   int state;
   /**
    * @see e3ps.groupware.board.Board
    */
   public int getState() {
      return state;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setState(int state) throws wt.util.WTPropertyVetoException {
      stateValidate(state);
      this.state = state;
   }
   void stateValidate(int state) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String DOC_NUMBER = "docNumber";
   int docNumber;
   /**
    * @see e3ps.groupware.board.Board
    */
   public int getDocNumber() {
      return docNumber;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setDocNumber(int docNumber) throws wt.util.WTPropertyVetoException {
      docNumberValidate(docNumber);
      this.docNumber = docNumber;
   }
   void docNumberValidate(int docNumber) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 0=false, 1=true
    *
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String PREFERRED = "preferred";
   int preferred = 0;
   /**
    * 0=false, 1=true
    *
    * @see e3ps.groupware.board.Board
    */
   public int getPreferred() {
      return preferred;
   }
   /**
    * 0=false, 1=true
    *
    * @see e3ps.groupware.board.Board
    */
   public void setPreferred(int preferred) throws wt.util.WTPropertyVetoException {
      preferredValidate(preferred);
      this.preferred = preferred;
   }
   void preferredValidate(int preferred) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see e3ps.groupware.board.Board
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see e3ps.groupware.board.Board
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String CATEGORY = "category";
   /**
    * @see e3ps.groupware.board.Board
    */
   public static final java.lang.String CATEGORY_REFERENCE = "categoryReference";
   wt.fc.ObjectReference categoryReference;
   /**
    * @see e3ps.groupware.board.Board
    */
   public e3ps.groupware.board.BoardCategory getCategory() {
      return (categoryReference != null) ? (e3ps.groupware.board.BoardCategory) categoryReference.getObject() : null;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public wt.fc.ObjectReference getCategoryReference() {
      return categoryReference;
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setCategory(e3ps.groupware.board.BoardCategory the_category) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCategoryReference(the_category == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.board.BoardCategory) the_category));
   }
   /**
    * @see e3ps.groupware.board.Board
    */
   public void setCategoryReference(wt.fc.ObjectReference the_categoryReference) throws wt.util.WTPropertyVetoException {
      categoryReferenceValidate(the_categoryReference);
      categoryReference = (wt.fc.ObjectReference) the_categoryReference;
   }
   void categoryReferenceValidate(wt.fc.ObjectReference the_categoryReference) throws wt.util.WTPropertyVetoException {
      if (the_categoryReference == null || the_categoryReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "categoryReference") },
               new java.beans.PropertyChangeEvent(this, "categoryReference", this.categoryReference, categoryReference));
      if (the_categoryReference != null && the_categoryReference.getReferencedClass() != null &&
            !e3ps.groupware.board.BoardCategory.class.isAssignableFrom(the_categoryReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "categoryReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "categoryReference", this.categoryReference, categoryReference));
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

   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent() {
      return (parentReference != null) ? (e3ps.common.impl.Tree) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.impl.Tree) the_parent));
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParentReference(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      parentReferenceValidate(the_parentReference);
      parentReference = (wt.fc.ObjectReference) the_parentReference;
   }
   void parentReferenceValidate(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      if (the_parentReference == null || the_parentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference") },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
      if (the_parentReference != null && the_parentReference.getReferencedClass() != null &&
            !e3ps.common.impl.Tree.class.isAssignableFrom(the_parentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 472499785313402617L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( categoryReference );
      output.writeObject( contents );
      output.writeInt( depth );
      output.writeInt( docNumber );
      output.writeObject( owner );
      output.writeObject( parentReference );
      output.writeInt( preferred );
      output.writeInt( readCount );
      output.writeInt( state );
      output.writeObject( thePersistInfo );
      output.writeObject( title );
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
      readVersion( (e3ps.groupware.board.Board) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "categoryReference", categoryReference, wt.fc.ObjectReference.class, true );
      output.setObject( "contents", contents );
      output.setInt( "depth", depth );
      output.setInt( "docNumber", docNumber );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setInt( "preferred", preferred );
      output.setInt( "readCount", readCount );
      output.setInt( "state", state );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "title", title );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      categoryReference = (wt.fc.ObjectReference) input.readObject( "categoryReference", categoryReference, wt.fc.ObjectReference.class, true );
      contents = (java.lang.Object) input.getObject( "contents" );
      depth = input.getInt( "depth" );
      docNumber = input.getInt( "docNumber" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      preferred = input.getInt( "preferred" );
      readCount = input.getInt( "readCount" );
      state = input.getInt( "state" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      title = input.getString( "title" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion472499785313402617L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      categoryReference = (wt.fc.ObjectReference) input.readObject();
      contents = (java.lang.Object) input.readObject();
      depth = input.readInt();
      docNumber = input.readInt();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      preferred = input.readInt();
      readCount = input.readInt();
      state = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      title = (java.lang.String) input.readObject();
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

   protected boolean readVersion( Board thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion472499785313402617L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 472499785313402617L )
         return ((Board) this).readVersion472499785313402617L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 3694896520867992815L )
         return ((Board) this).readVersion3694896520867992815L( input, readSerialVersionUID, superDone );
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
