package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _Factory3TheCostLogistics extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = Factory3TheCostLogistics.class.getName();

   /**
    * @see ext.ket.cost.entity.Factory3TheCostLogistics
    */
   public static final java.lang.String FACTORY3_ROLE = "factory3";
   /**
    * @see ext.ket.cost.entity.Factory3TheCostLogistics
    */
   public e3ps.common.code.NumberCode getFactory3() {
      return (e3ps.common.code.NumberCode) getRoleAObject();
   }
   /**
    * @see ext.ket.cost.entity.Factory3TheCostLogistics
    */
   public void setFactory3(e3ps.common.code.NumberCode the_factory3) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_factory3);
   }

   /**
    * @see ext.ket.cost.entity.Factory3TheCostLogistics
    */
   public static final java.lang.String COST_LOGISTICS_ROLE = "theCostLogistics";
   /**
    * @see ext.ket.cost.entity.Factory3TheCostLogistics
    */
   public ext.ket.cost.entity.CostLogistics getCostLogistics() {
      return (ext.ket.cost.entity.CostLogistics) getRoleBObject();
   }
   /**
    * @see ext.ket.cost.entity.Factory3TheCostLogistics
    */
   public void setCostLogistics(ext.ket.cost.entity.CostLogistics the_theCostLogistics) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theCostLogistics);
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

   public static final long EXTERNALIZATION_VERSION_UID = 2454203546147727912L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

   }

   protected void super_writeExternal_Factory3TheCostLogistics(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.Factory3TheCostLogistics) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_Factory3TheCostLogistics(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

   }

   boolean readVersion2454203546147727912L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      return true;
   }

   protected boolean readVersion( Factory3TheCostLogistics thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_Factory3TheCostLogistics( _Factory3TheCostLogistics thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
