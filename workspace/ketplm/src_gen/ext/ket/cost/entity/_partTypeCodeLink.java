package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _partTypeCodeLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = partTypeCodeLink.class.getName();

   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public static final java.lang.String CODE_TYPE = "codeType";
   static int CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String codeType;
   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public java.lang.String getCodeType() {
      return codeType;
   }
   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public void setCodeType(java.lang.String codeType) throws wt.util.WTPropertyVetoException {
      codeTypeValidate(codeType);
      this.codeType = codeType;
   }
   void codeTypeValidate(java.lang.String codeType) throws wt.util.WTPropertyVetoException {
      if (CODE_TYPE_UPPER_LIMIT < 1) {
         try { CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("codeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (codeType != null && !wt.fc.PersistenceHelper.checkStoredLength(codeType.toString(), CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "codeType"), java.lang.String.valueOf(java.lang.Math.min(CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "codeType", this.codeType, codeType));
   }

   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public static final java.lang.String CODE_MASTER_ROLE = "codeMaster";
   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public ext.ket.cost.entity.CostPartTypeCodeMaster getCodeMaster() {
      return (ext.ket.cost.entity.CostPartTypeCodeMaster) getRoleAObject();
   }
   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public void setCodeMaster(ext.ket.cost.entity.CostPartTypeCodeMaster the_codeMaster) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_codeMaster);
   }

   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public static final java.lang.String MANUFACTURE_CODE2_ROLE = "manufactureCode2";
   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public e3ps.common.code.NumberCode getManufactureCode2() {
      return (e3ps.common.code.NumberCode) getRoleBObject();
   }
   /**
    * @see ext.ket.cost.entity.partTypeCodeLink
    */
   public void setManufactureCode2(e3ps.common.code.NumberCode the_manufactureCode2) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_manufactureCode2);
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

   public static final long EXTERNALIZATION_VERSION_UID = 6229027120261070773L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( codeType );
   }

   protected void super_writeExternal_partTypeCodeLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.partTypeCodeLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_partTypeCodeLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "codeType", codeType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      codeType = input.getString( "codeType" );
   }

   boolean readVersion6229027120261070773L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      codeType = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( partTypeCodeLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6229027120261070773L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_partTypeCodeLink( _partTypeCodeLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
