package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ModelReferenceLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = ModelReferenceLink.class.getName();

   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public static final java.lang.String RELATED_LINK_OID = "relatedLinkOid";
   static int RELATED_LINK_OID_UPPER_LIMIT = -1;
   java.lang.String relatedLinkOid;
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public java.lang.String getRelatedLinkOid() {
      return relatedLinkOid;
   }
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public void setRelatedLinkOid(java.lang.String relatedLinkOid) throws wt.util.WTPropertyVetoException {
      relatedLinkOidValidate(relatedLinkOid);
      this.relatedLinkOid = relatedLinkOid;
   }
   void relatedLinkOidValidate(java.lang.String relatedLinkOid) throws wt.util.WTPropertyVetoException {
      if (RELATED_LINK_OID_UPPER_LIMIT < 1) {
         try { RELATED_LINK_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("relatedLinkOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RELATED_LINK_OID_UPPER_LIMIT = 200; }
      }
      if (relatedLinkOid != null && !wt.fc.PersistenceHelper.checkStoredLength(relatedLinkOid.toString(), RELATED_LINK_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "relatedLinkOid"), java.lang.String.valueOf(java.lang.Math.min(RELATED_LINK_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "relatedLinkOid", this.relatedLinkOid, relatedLinkOid));
   }

   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public static final java.lang.String REQUIRED = "required";
   int required = 1;
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public int getRequired() {
      return required;
   }
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public void setRequired(int required) throws wt.util.WTPropertyVetoException {
      requiredValidate(required);
      this.required = required;
   }
   void requiredValidate(int required) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public static final java.lang.String MODEL_MASTER_ROLE = "modelMaster";
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public wt.epm.EPMDocumentMaster getModelMaster() {
      return (wt.epm.EPMDocumentMaster) getRoleAObject();
   }
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public void setModelMaster(wt.epm.EPMDocumentMaster the_modelMaster) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_modelMaster);
   }

   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public static final java.lang.String DRAWING_MASTER_ROLE = "drawingMaster";
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public wt.epm.EPMDocumentMaster getDrawingMaster() {
      return (wt.epm.EPMDocumentMaster) getRoleBObject();
   }
   /**
    * @see e3ps.edm.ModelReferenceLink
    */
   public void setDrawingMaster(wt.epm.EPMDocumentMaster the_drawingMaster) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_drawingMaster);
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

   public static final long EXTERNALIZATION_VERSION_UID = -540091148334393400L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( relatedLinkOid );
      output.writeInt( required );
   }

   protected void super_writeExternal_ModelReferenceLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.edm.ModelReferenceLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ModelReferenceLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "relatedLinkOid", relatedLinkOid );
      output.setInt( "required", required );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      relatedLinkOid = input.getString( "relatedLinkOid" );
      required = input.getInt( "required" );
   }

   boolean readVersion_540091148334393400L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      relatedLinkOid = (java.lang.String) input.readObject();
      required = input.readInt();
      return true;
   }

   protected boolean readVersion( ModelReferenceLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_540091148334393400L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ModelReferenceLink( _ModelReferenceLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
