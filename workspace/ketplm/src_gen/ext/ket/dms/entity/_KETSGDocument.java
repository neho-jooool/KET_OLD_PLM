package ext.ket.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSGDocument extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSGDocument.class.getName();

   /**
    * 문서번호
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String DOC_NO = "docNo";
   static int DOC_NO_UPPER_LIMIT = -1;
   java.lang.String docNo;
   /**
    * 문서번호
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public java.lang.String getDocNo() {
      return docNo;
   }
   /**
    * 문서번호
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setDocNo(java.lang.String docNo) throws wt.util.WTPropertyVetoException {
      docNoValidate(docNo);
      this.docNo = docNo;
   }
   void docNoValidate(java.lang.String docNo) throws wt.util.WTPropertyVetoException {
      if (DOC_NO_UPPER_LIMIT < 1) {
         try { DOC_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_NO_UPPER_LIMIT = 200; }
      }
      if (docNo != null && !wt.fc.PersistenceHelper.checkStoredLength(docNo.toString(), DOC_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docNo"), java.lang.String.valueOf(java.lang.Math.min(DOC_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docNo", this.docNo, docNo));
   }

   /**
    * 문서명
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String DOC_NAME = "docName";
   static int DOC_NAME_UPPER_LIMIT = -1;
   java.lang.String docName;
   /**
    * 문서명
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public java.lang.String getDocName() {
      return docName;
   }
   /**
    * 문서명
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setDocName(java.lang.String docName) throws wt.util.WTPropertyVetoException {
      docNameValidate(docName);
      this.docName = docName;
   }
   void docNameValidate(java.lang.String docName) throws wt.util.WTPropertyVetoException {
      if (DOC_NAME_UPPER_LIMIT < 1) {
         try { DOC_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_NAME_UPPER_LIMIT = 200; }
      }
      if (docName != null && !wt.fc.PersistenceHelper.checkStoredLength(docName.toString(), DOC_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docName"), java.lang.String.valueOf(java.lang.Math.min(DOC_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docName", this.docName, docName));
   }

   /**
    * 모듈 CODE
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String MODULE_CODE = "moduleCode";
   static int MODULE_CODE_UPPER_LIMIT = -1;
   java.lang.String moduleCode;
   /**
    * 모듈 CODE
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public java.lang.String getModuleCode() {
      return moduleCode;
   }
   /**
    * 모듈 CODE
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setModuleCode(java.lang.String moduleCode) throws wt.util.WTPropertyVetoException {
      moduleCodeValidate(moduleCode);
      this.moduleCode = moduleCode;
   }
   void moduleCodeValidate(java.lang.String moduleCode) throws wt.util.WTPropertyVetoException {
      if (MODULE_CODE_UPPER_LIMIT < 1) {
         try { MODULE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moduleCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODULE_CODE_UPPER_LIMIT = 200; }
      }
      if (moduleCode != null && !wt.fc.PersistenceHelper.checkStoredLength(moduleCode.toString(), MODULE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moduleCode"), java.lang.String.valueOf(java.lang.Math.min(MODULE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moduleCode", this.moduleCode, moduleCode));
   }

   /**
    * 관련화면
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String REF_VIEW = "refView";
   static int REF_VIEW_UPPER_LIMIT = -1;
   java.lang.String refView;
   /**
    * 관련화면
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public java.lang.String getRefView() {
      return refView;
   }
   /**
    * 관련화면
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setRefView(java.lang.String refView) throws wt.util.WTPropertyVetoException {
      refViewValidate(refView);
      this.refView = refView;
   }
   void refViewValidate(java.lang.String refView) throws wt.util.WTPropertyVetoException {
      if (REF_VIEW_UPPER_LIMIT < 1) {
         try { REF_VIEW_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("refView").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REF_VIEW_UPPER_LIMIT = 200; }
      }
      if (refView != null && !wt.fc.PersistenceHelper.checkStoredLength(refView.toString(), REF_VIEW_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "refView"), java.lang.String.valueOf(java.lang.Math.min(REF_VIEW_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "refView", this.refView, refView));
   }

   /**
    * 양식설명
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * 양식설명
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * 양식설명
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setDescription(java.lang.String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(java.lang.String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 4000; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), java.lang.String.valueOf(java.lang.Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
   }

   /**
    * 버전
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String VERSION = "version";
   java.lang.Integer version = 0;
   /**
    * 버전
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public java.lang.Integer getVersion() {
      return version;
   }
   /**
    * 버전
    *
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setVersion(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String LASTEST = "lastest";
   boolean lastest = false;
   /**
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public boolean isLastest() {
      return lastest;
   }
   /**
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setLastest(boolean lastest) throws wt.util.WTPropertyVetoException {
      lastestValidate(lastest);
      this.lastest = lastest;
   }
   void lastestValidate(boolean lastest) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public static final java.lang.String BRANCH_ID = "branchId";
   long branchId = 0;
   /**
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public long getBranchId() {
      return branchId;
   }
   /**
    * @see ext.ket.dms.entity.KETSGDocument
    */
   public void setBranchId(long branchId) throws wt.util.WTPropertyVetoException {
      branchIdValidate(branchId);
      this.branchId = branchId;
   }
   void branchIdValidate(long branchId) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 7629923261978979814L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeLong( branchId );
      output.writeObject( description );
      output.writeObject( docName );
      output.writeObject( docNo );
      output.writeBoolean( lastest );
      output.writeObject( moduleCode );
      output.writeObject( refView );
      output.writeObject( version );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETSGDocument(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dms.entity.KETSGDocument) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSGDocument(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setLong( "branchId", branchId );
      output.setString( "description", description );
      output.setString( "docName", docName );
      output.setString( "docNo", docNo );
      output.setBoolean( "lastest", lastest );
      output.setString( "moduleCode", moduleCode );
      output.setString( "refView", refView );
      output.setIntObject( "version", version );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      branchId = input.getLong( "branchId" );
      description = input.getString( "description" );
      docName = input.getString( "docName" );
      docNo = input.getString( "docNo" );
      lastest = input.getBoolean( "lastest" );
      moduleCode = input.getString( "moduleCode" );
      refView = input.getString( "refView" );
      version = input.getIntObject( "version" );
   }

   boolean readVersion7629923261978979814L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      branchId = input.readLong();
      description = (java.lang.String) input.readObject();
      docName = (java.lang.String) input.readObject();
      docNo = (java.lang.String) input.readObject();
      lastest = input.readBoolean();
      moduleCode = (java.lang.String) input.readObject();
      refView = (java.lang.String) input.readObject();
      version = (java.lang.Integer) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETSGDocument thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7629923261978979814L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSGDocument( _KETSGDocument thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
