package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _DrawingToModelReferenceLink extends wt.vc.VersionToVersionLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = DrawingToModelReferenceLink.class.getName();

   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public static final java.lang.String RELATED_LINK_OID = "relatedLinkOid";
   static int RELATED_LINK_OID_UPPER_LIMIT = -1;
   java.lang.String relatedLinkOid;
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public java.lang.String getRelatedLinkOid() {
      return relatedLinkOid;
   }
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
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
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public static final java.lang.String REQUIRED = "required";
   int required = 1;
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public int getRequired() {
      return required;
   }
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public void setRequired(int required) throws wt.util.WTPropertyVetoException {
      requiredValidate(required);
      this.required = required;
   }
   void requiredValidate(int required) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public static final java.lang.String MODEL_ROLE = "model";
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public wt.epm.EPMDocument getModel() {
      return (wt.epm.EPMDocument) getRoleAObject();
   }
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public void setModel(wt.epm.EPMDocument the_model) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_model);
   }

   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public static final java.lang.String DRAWING_ROLE = "drawing";
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public wt.epm.EPMDocument getDrawing() {
      return (wt.epm.EPMDocument) getRoleBObject();
   }
   /**
    * @see e3ps.edm.DrawingToModelReferenceLink
    */
   public void setDrawing(wt.epm.EPMDocument the_drawing) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_drawing);
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

   public static final long EXTERNALIZATION_VERSION_UID = 5388957369125871222L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( relatedLinkOid );
      output.writeInt( required );
   }

   protected void super_writeExternal_DrawingToModelReferenceLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.edm.DrawingToModelReferenceLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_DrawingToModelReferenceLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   boolean readVersion5388957369125871222L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      relatedLinkOid = (java.lang.String) input.readObject();
      required = input.readInt();
      return true;
   }

   protected boolean readVersion( DrawingToModelReferenceLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5388957369125871222L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_DrawingToModelReferenceLink( _DrawingToModelReferenceLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
