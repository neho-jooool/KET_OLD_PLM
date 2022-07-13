package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDocumentCategoryLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDocumentCategoryLink.class.getName();

   /**
    * @see e3ps.dms.entity.KETDocumentCategoryLink
    */
   public static final java.lang.String DOCUMENT_CATEGORY_ROLE = "documentCategory";
   /**
    * @see e3ps.dms.entity.KETDocumentCategoryLink
    */
   public e3ps.dms.entity.KETDocumentCategory getDocumentCategory() {
      return (e3ps.dms.entity.KETDocumentCategory) getRoleAObject();
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategoryLink
    */
   public void setDocumentCategory(e3ps.dms.entity.KETDocumentCategory the_documentCategory) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_documentCategory);
   }

   /**
    * @see e3ps.dms.entity.KETDocumentCategoryLink
    */
   public static final java.lang.String DOCUMENT_ROLE = "document";
   /**
    * @see e3ps.dms.entity.KETDocumentCategoryLink
    */
   public e3ps.dms.entity.KETProjectDocument getDocument() {
      return (e3ps.dms.entity.KETProjectDocument) getRoleBObject();
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategoryLink
    */
   public void setDocument(e3ps.dms.entity.KETProjectDocument the_document) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_document);
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

   public static final long EXTERNALIZATION_VERSION_UID = 2538346186404157511L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

   }

   protected void super_writeExternal_KETDocumentCategoryLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETDocumentCategoryLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDocumentCategoryLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

   }

   boolean readVersion2538346186404157511L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      return true;
   }

   protected boolean readVersion( KETDocumentCategoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETDocumentCategoryLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDocumentCategoryLink( _KETDocumentCategoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
