package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDocumentPartLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDocumentPartLink.class.getName();

   /**
    * partNo
    *
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * partNo
    *
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * partNo
    *
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public void setPartNo(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      partNoValidate(partNo);
      this.partNo = partNo;
   }
   void partNoValidate(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      if (PART_NO_UPPER_LIMIT < 1) {
         try { PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NO_UPPER_LIMIT = 200; }
      }
      if (partNo != null && !wt.fc.PersistenceHelper.checkStoredLength(partNo.toString(), PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNo"), java.lang.String.valueOf(java.lang.Math.min(PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNo", this.partNo, partNo));
   }

   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public static final java.lang.String PART_MASTER = "partMaster";
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public static final java.lang.String PART_MASTER_REFERENCE = "partMasterReference";
   wt.fc.ObjectReference partMasterReference;
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public wt.part.WTPartMaster getPartMaster() {
      return (partMasterReference != null) ? (wt.part.WTPartMaster) partMasterReference.getObject() : null;
   }
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public wt.fc.ObjectReference getPartMasterReference() {
      return partMasterReference;
   }
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public void setPartMaster(wt.part.WTPartMaster the_partMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartMasterReference(the_partMaster == null ? null : wt.fc.ObjectReference.newObjectReference((wt.part.WTPartMaster) the_partMaster));
   }
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public void setPartMasterReference(wt.fc.ObjectReference the_partMasterReference) throws wt.util.WTPropertyVetoException {
      partMasterReferenceValidate(the_partMasterReference);
      partMasterReference = (wt.fc.ObjectReference) the_partMasterReference;
   }
   void partMasterReferenceValidate(wt.fc.ObjectReference the_partMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_partMasterReference == null || the_partMasterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partMasterReference") },
               new java.beans.PropertyChangeEvent(this, "partMasterReference", this.partMasterReference, partMasterReference));
      if (the_partMasterReference != null && the_partMasterReference.getReferencedClass() != null &&
            !wt.part.WTPartMaster.class.isAssignableFrom(the_partMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "partMasterReference", this.partMasterReference, partMasterReference));
   }

   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public static final java.lang.String DOCUMENT_ROLE = "document";
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public e3ps.dms.entity.KETProjectDocument getDocument() {
      return (e3ps.dms.entity.KETProjectDocument) getRoleAObject();
   }
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public void setDocument(e3ps.dms.entity.KETProjectDocument the_document) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_document);
   }

   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public static final java.lang.String PART_ROLE = "part";
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public wt.part.WTPart getPart() {
      return (wt.part.WTPart) getRoleBObject();
   }
   /**
    * @see e3ps.dms.entity.KETDocumentPartLink
    */
   public void setPart(wt.part.WTPart the_part) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_part);
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

   public static final long EXTERNALIZATION_VERSION_UID = -4920931890414232993L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( partMasterReference );
      output.writeObject( partNo );
   }

   protected void super_writeExternal_KETDocumentPartLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETDocumentPartLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDocumentPartLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "partMasterReference", partMasterReference, wt.fc.ObjectReference.class, true );
      output.setString( "partNo", partNo );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      partMasterReference = (wt.fc.ObjectReference) input.readObject( "partMasterReference", partMasterReference, wt.fc.ObjectReference.class, true );
      partNo = input.getString( "partNo" );
   }

   boolean readVersion_4920931890414232993L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      partMasterReference = (wt.fc.ObjectReference) input.readObject();
      partNo = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDocumentPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4920931890414232993L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETDocumentPartLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDocumentPartLink( _KETDocumentPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
