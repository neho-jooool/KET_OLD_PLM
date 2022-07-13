package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETTechnicalCategoryLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETTechnicalCategoryLink.class.getName();

   /**
    * @see e3ps.dms.entity.KETTechnicalCategoryLink
    */
   public static final java.lang.String DOCUMENT_CATEGORY_ROLE = "documentCategory";
   /**
    * @see e3ps.dms.entity.KETTechnicalCategoryLink
    */
   public e3ps.dms.entity.KETDocumentCategory getDocumentCategory() {
      return (e3ps.dms.entity.KETDocumentCategory) getRoleAObject();
   }
   /**
    * @see e3ps.dms.entity.KETTechnicalCategoryLink
    */
   public void setDocumentCategory(e3ps.dms.entity.KETDocumentCategory the_documentCategory) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_documentCategory);
   }

   /**
    * @see e3ps.dms.entity.KETTechnicalCategoryLink
    */
   public static final java.lang.String TECHNICAL_ROLE = "technical";
   /**
    * @see e3ps.dms.entity.KETTechnicalCategoryLink
    */
   public e3ps.dms.entity.KETTechnicalDocument getTechnical() {
      return (e3ps.dms.entity.KETTechnicalDocument) getRoleBObject();
   }
   /**
    * @see e3ps.dms.entity.KETTechnicalCategoryLink
    */
   public void setTechnical(e3ps.dms.entity.KETTechnicalDocument the_technical) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_technical);
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

   protected void super_writeExternal_KETTechnicalCategoryLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETTechnicalCategoryLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETTechnicalCategoryLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( KETTechnicalCategoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETTechnicalCategoryLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETTechnicalCategoryLink( _KETTechnicalCategoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
