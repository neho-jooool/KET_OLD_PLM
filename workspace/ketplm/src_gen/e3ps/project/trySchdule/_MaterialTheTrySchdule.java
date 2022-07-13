package e3ps.project.trySchdule;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MaterialTheTrySchdule extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.trySchdule.trySchduleResource";
   static final java.lang.String CLASSNAME = MaterialTheTrySchdule.class.getName();

   /**
    * @see e3ps.project.trySchdule.MaterialTheTrySchdule
    */
   public static final java.lang.String MATERIAL_ROLE = "material";
   /**
    * @see e3ps.project.trySchdule.MaterialTheTrySchdule
    */
   public e3ps.project.material.MoldMaterial getMaterial() {
      return (e3ps.project.material.MoldMaterial) getRoleAObject();
   }
   /**
    * @see e3ps.project.trySchdule.MaterialTheTrySchdule
    */
   public void setMaterial(e3ps.project.material.MoldMaterial the_material) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_material);
   }

   /**
    * @see e3ps.project.trySchdule.MaterialTheTrySchdule
    */
   public static final java.lang.String TRY_SCHDULE_ROLE = "theTrySchdule";
   /**
    * @see e3ps.project.trySchdule.MaterialTheTrySchdule
    */
   public e3ps.project.trySchdule.TrySchdule getTrySchdule() {
      return (e3ps.project.trySchdule.TrySchdule) getRoleBObject();
   }
   /**
    * @see e3ps.project.trySchdule.MaterialTheTrySchdule
    */
   public void setTrySchdule(e3ps.project.trySchdule.TrySchdule the_theTrySchdule) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theTrySchdule);
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

   protected void super_writeExternal_MaterialTheTrySchdule(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.trySchdule.MaterialTheTrySchdule) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_MaterialTheTrySchdule(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( MaterialTheTrySchdule thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_MaterialTheTrySchdule( _MaterialTheTrySchdule thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
