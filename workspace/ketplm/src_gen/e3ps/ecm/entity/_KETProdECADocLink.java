package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProdECADocLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProdECADocLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public static final java.lang.String DOC_TYPE = "docType";
   static int DOC_TYPE_UPPER_LIMIT = -1;
   java.lang.String docType;
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public java.lang.String getDocType() {
      return docType;
   }
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public void setDocType(java.lang.String docType) throws wt.util.WTPropertyVetoException {
      docTypeValidate(docType);
      this.docType = docType;
   }
   void docTypeValidate(java.lang.String docType) throws wt.util.WTPropertyVetoException {
      if (DOC_TYPE_UPPER_LIMIT < 1) {
         try { DOC_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_TYPE_UPPER_LIMIT = 200; }
      }
      if (docType != null && !wt.fc.PersistenceHelper.checkStoredLength(docType.toString(), DOC_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docType"), java.lang.String.valueOf(java.lang.Math.min(DOC_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docType", this.docType, docType));
   }

   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public static final java.lang.String PRE_VERSION = "preVersion";
   static int PRE_VERSION_UPPER_LIMIT = -1;
   java.lang.String preVersion;
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public java.lang.String getPreVersion() {
      return preVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public void setPreVersion(java.lang.String preVersion) throws wt.util.WTPropertyVetoException {
      preVersionValidate(preVersion);
      this.preVersion = preVersion;
   }
   void preVersionValidate(java.lang.String preVersion) throws wt.util.WTPropertyVetoException {
      if (PRE_VERSION_UPPER_LIMIT < 1) {
         try { PRE_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("preVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRE_VERSION_UPPER_LIMIT = 200; }
      }
      if (preVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(preVersion.toString(), PRE_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "preVersion"), java.lang.String.valueOf(java.lang.Math.min(PRE_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "preVersion", this.preVersion, preVersion));
   }

   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public static final java.lang.String AFTER_VERSION = "afterVersion";
   static int AFTER_VERSION_UPPER_LIMIT = -1;
   java.lang.String afterVersion;
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public java.lang.String getAfterVersion() {
      return afterVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public void setAfterVersion(java.lang.String afterVersion) throws wt.util.WTPropertyVetoException {
      afterVersionValidate(afterVersion);
      this.afterVersion = afterVersion;
   }
   void afterVersionValidate(java.lang.String afterVersion) throws wt.util.WTPropertyVetoException {
      if (AFTER_VERSION_UPPER_LIMIT < 1) {
         try { AFTER_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("afterVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AFTER_VERSION_UPPER_LIMIT = 200; }
      }
      if (afterVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(afterVersion.toString(), AFTER_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "afterVersion"), java.lang.String.valueOf(java.lang.Math.min(AFTER_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "afterVersion", this.afterVersion, afterVersion));
   }

   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public static final java.lang.String DOC_TYPE_DESC = "docTypeDesc";
   static int DOC_TYPE_DESC_UPPER_LIMIT = -1;
   java.lang.String docTypeDesc;
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public java.lang.String getDocTypeDesc() {
      return docTypeDesc;
   }
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public void setDocTypeDesc(java.lang.String docTypeDesc) throws wt.util.WTPropertyVetoException {
      docTypeDescValidate(docTypeDesc);
      this.docTypeDesc = docTypeDesc;
   }
   void docTypeDescValidate(java.lang.String docTypeDesc) throws wt.util.WTPropertyVetoException {
      if (DOC_TYPE_DESC_UPPER_LIMIT < 1) {
         try { DOC_TYPE_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docTypeDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_TYPE_DESC_UPPER_LIMIT = 200; }
      }
      if (docTypeDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(docTypeDesc.toString(), DOC_TYPE_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docTypeDesc"), java.lang.String.valueOf(java.lang.Math.min(DOC_TYPE_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docTypeDesc", this.docTypeDesc, docTypeDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public static final java.lang.String DOC_ROLE = "doc";
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public e3ps.dms.entity.KETProjectDocument getDoc() {
      return (e3ps.dms.entity.KETProjectDocument) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public void setDoc(e3ps.dms.entity.KETProjectDocument the_doc) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_doc);
   }

   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public static final java.lang.String PROD_ECAROLE = "prodECA";
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public e3ps.ecm.entity.KETProdChangeActivity getProdECA() {
      return (e3ps.ecm.entity.KETProdChangeActivity) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETProdECADocLink
    */
   public void setProdECA(e3ps.ecm.entity.KETProdChangeActivity the_prodECA) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_prodECA);
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

   public static final long EXTERNALIZATION_VERSION_UID = -7775137355757128119L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( afterVersion );
      output.writeObject( docType );
      output.writeObject( docTypeDesc );
      output.writeObject( preVersion );
   }

   protected void super_writeExternal_KETProdECADocLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETProdECADocLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProdECADocLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "afterVersion", afterVersion );
      output.setString( "docType", docType );
      output.setString( "docTypeDesc", docTypeDesc );
      output.setString( "preVersion", preVersion );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      afterVersion = input.getString( "afterVersion" );
      docType = input.getString( "docType" );
      docTypeDesc = input.getString( "docTypeDesc" );
      preVersion = input.getString( "preVersion" );
   }

   boolean readVersion_7775137355757128119L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      afterVersion = (java.lang.String) input.readObject();
      docType = (java.lang.String) input.readObject();
      docTypeDesc = (java.lang.String) input.readObject();
      preVersion = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProdECADocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7775137355757128119L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProdECADocLink( _KETProdECADocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
