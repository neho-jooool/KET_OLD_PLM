package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _EPMLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = EPMLink.class.getName();

   /**
    * @see e3ps.edm.EPMLink
    */
   public static final java.lang.String REQUIRED = "required";
   int required = 1;
   /**
    * @see e3ps.edm.EPMLink
    */
   public int getRequired() {
      return required;
   }
   /**
    * @see e3ps.edm.EPMLink
    */
   public void setRequired(int required) throws wt.util.WTPropertyVetoException {
      requiredValidate(required);
      this.required = required;
   }
   void requiredValidate(int required) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.EPMLink
    */
   public static final java.lang.String REFERENCE_TYPE = "referenceType";
   static int REFERENCE_TYPE_UPPER_LIMIT = -1;
   java.lang.String referenceType;
   /**
    * @see e3ps.edm.EPMLink
    */
   public java.lang.String getReferenceType() {
      return referenceType;
   }
   /**
    * @see e3ps.edm.EPMLink
    */
   public void setReferenceType(java.lang.String referenceType) throws wt.util.WTPropertyVetoException {
      referenceTypeValidate(referenceType);
      this.referenceType = referenceType;
   }
   void referenceTypeValidate(java.lang.String referenceType) throws wt.util.WTPropertyVetoException {
      if (REFERENCE_TYPE_UPPER_LIMIT < 1) {
         try { REFERENCE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("referenceType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REFERENCE_TYPE_UPPER_LIMIT = 200; }
      }
      if (referenceType != null && !wt.fc.PersistenceHelper.checkStoredLength(referenceType.toString(), REFERENCE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "referenceType"), java.lang.String.valueOf(java.lang.Math.min(REFERENCE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "referenceType", this.referenceType, referenceType));
   }

   /**
    * @see e3ps.edm.EPMLink
    */
   public static final java.lang.String ECAD = "ecad";
   boolean ecad = false;
   /**
    * @see e3ps.edm.EPMLink
    */
   public boolean isEcad() {
      return ecad;
   }
   /**
    * @see e3ps.edm.EPMLink
    */
   public void setEcad(boolean ecad) throws wt.util.WTPropertyVetoException {
      ecadValidate(ecad);
      this.ecad = ecad;
   }
   void ecadValidate(boolean ecad) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.EPMLink
    */
   public static final java.lang.String EPM_MASTER_ROLE = "epmMaster";
   /**
    * @see e3ps.edm.EPMLink
    */
   public wt.epm.EPMDocumentMaster getEpmMaster() {
      return (wt.epm.EPMDocumentMaster) getRoleAObject();
   }
   /**
    * @see e3ps.edm.EPMLink
    */
   public void setEpmMaster(wt.epm.EPMDocumentMaster the_epmMaster) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_epmMaster);
   }

   /**
    * @see e3ps.edm.EPMLink
    */
   public static final java.lang.String PART_MASTER_ROLE = "partMaster";
   /**
    * @see e3ps.edm.EPMLink
    */
   public wt.part.WTPartMaster getPartMaster() {
      return (wt.part.WTPartMaster) getRoleBObject();
   }
   /**
    * @see e3ps.edm.EPMLink
    */
   public void setPartMaster(wt.part.WTPartMaster the_partMaster) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_partMaster);
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

   public static final long EXTERNALIZATION_VERSION_UID = 3865612707841340339L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeBoolean( ecad );
      output.writeObject( referenceType );
      output.writeInt( required );
   }

   protected void super_writeExternal_EPMLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.edm.EPMLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_EPMLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setBoolean( "ecad", ecad );
      output.setString( "referenceType", referenceType );
      output.setInt( "required", required );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      ecad = input.getBoolean( "ecad" );
      referenceType = input.getString( "referenceType" );
      required = input.getInt( "required" );
   }

   boolean readVersion3865612707841340339L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      ecad = input.readBoolean();
      referenceType = (java.lang.String) input.readObject();
      required = input.readInt();
      return true;
   }

   protected boolean readVersion( EPMLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion3865612707841340339L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_EPMLink( _EPMLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
