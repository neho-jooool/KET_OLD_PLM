package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _PartToEPMLink extends wt.vc.VersionToVersionLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = PartToEPMLink.class.getName();

   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public static final java.lang.String REQUIRED = "required";
   int required = 1;
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public int getRequired() {
      return required;
   }
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public void setRequired(int required) throws wt.util.WTPropertyVetoException {
      requiredValidate(required);
      this.required = required;
   }
   void requiredValidate(int required) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public static final java.lang.String REFERENCE_TYPE = "referenceType";
   static int REFERENCE_TYPE_UPPER_LIMIT = -1;
   java.lang.String referenceType;
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public java.lang.String getReferenceType() {
      return referenceType;
   }
   /**
    * @see e3ps.edm.PartToEPMLink
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
    * @see e3ps.edm.PartToEPMLink
    */
   public static final java.lang.String ECAD = "ecad";
   boolean ecad = false;
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public boolean isEcad() {
      return ecad;
   }
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public void setEcad(boolean ecad) throws wt.util.WTPropertyVetoException {
      ecadValidate(ecad);
      this.ecad = ecad;
   }
   void ecadValidate(boolean ecad) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public static final java.lang.String EPM_ROLE = "epm";
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public wt.epm.EPMDocument getEpm() {
      return (wt.epm.EPMDocument) getRoleAObject();
   }
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public void setEpm(wt.epm.EPMDocument the_epm) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_epm);
   }

   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public static final java.lang.String PART_ROLE = "part";
   /**
    * @see e3ps.edm.PartToEPMLink
    */
   public wt.part.WTPart getPart() {
      return (wt.part.WTPart) getRoleBObject();
   }
   /**
    * @see e3ps.edm.PartToEPMLink
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

   public static final long EXTERNALIZATION_VERSION_UID = -1887108095436943365L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeBoolean( ecad );
      output.writeObject( referenceType );
      output.writeInt( required );
   }

   protected void super_writeExternal_PartToEPMLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.edm.PartToEPMLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_PartToEPMLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   boolean readVersion_1887108095436943365L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      ecad = input.readBoolean();
      referenceType = (java.lang.String) input.readObject();
      required = input.readInt();
      return true;
   }

   protected boolean readVersion( PartToEPMLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1887108095436943365L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_PartToEPMLink( _PartToEPMLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
