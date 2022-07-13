package e3ps.project.machine;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MachineMakerTheMoldMachine extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.machine.machineResource";
   static final java.lang.String CLASSNAME = MachineMakerTheMoldMachine.class.getName();

   /**
    * @see e3ps.project.machine.MachineMakerTheMoldMachine
    */
   public static final java.lang.String MACHINE_MAKER_ROLE = "machineMaker";
   /**
    * @see e3ps.project.machine.MachineMakerTheMoldMachine
    */
   public e3ps.common.code.NumberCode getMachineMaker() {
      return (e3ps.common.code.NumberCode) getRoleAObject();
   }
   /**
    * @see e3ps.project.machine.MachineMakerTheMoldMachine
    */
   public void setMachineMaker(e3ps.common.code.NumberCode the_machineMaker) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_machineMaker);
   }

   /**
    * @see e3ps.project.machine.MachineMakerTheMoldMachine
    */
   public static final java.lang.String MOLD_MACHINE_ROLE = "theMoldMachine";
   /**
    * @see e3ps.project.machine.MachineMakerTheMoldMachine
    */
   public e3ps.project.machine.MoldMachine getMoldMachine() {
      return (e3ps.project.machine.MoldMachine) getRoleBObject();
   }
   /**
    * @see e3ps.project.machine.MachineMakerTheMoldMachine
    */
   public void setMoldMachine(e3ps.project.machine.MoldMachine the_theMoldMachine) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theMoldMachine);
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

   protected void super_writeExternal_MachineMakerTheMoldMachine(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.machine.MachineMakerTheMoldMachine) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_MachineMakerTheMoldMachine(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( MachineMakerTheMoldMachine thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_MachineMakerTheMoldMachine( _MachineMakerTheMoldMachine thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
