package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _E3PSProjectMasterEPMDocLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = E3PSProjectMasterEPMDocLink.class.getName();

   /**
    * @see e3ps.project.E3PSProjectMasterEPMDocLink
    */
   public static final java.lang.String MASTER_ROLE = "master";
   /**
    * @see e3ps.project.E3PSProjectMasterEPMDocLink
    */
   public e3ps.project.E3PSProjectMaster getMaster() {
      return (e3ps.project.E3PSProjectMaster) getRoleAObject();
   }
   /**
    * @see e3ps.project.E3PSProjectMasterEPMDocLink
    */
   public void setMaster(e3ps.project.E3PSProjectMaster the_master) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_master);
   }

   /**
    * @see e3ps.project.E3PSProjectMasterEPMDocLink
    */
   public static final java.lang.String EPM_ROLE = "epm";
   /**
    * @see e3ps.project.E3PSProjectMasterEPMDocLink
    */
   public wt.epm.EPMDocument getEpm() {
      return (wt.epm.EPMDocument) getRoleBObject();
   }
   /**
    * @see e3ps.project.E3PSProjectMasterEPMDocLink
    */
   public void setEpm(wt.epm.EPMDocument the_epm) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_epm);
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

   protected void super_writeExternal_E3PSProjectMasterEPMDocLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.E3PSProjectMasterEPMDocLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_E3PSProjectMasterEPMDocLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( E3PSProjectMasterEPMDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((E3PSProjectMasterEPMDocLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_E3PSProjectMasterEPMDocLink( _E3PSProjectMasterEPMDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
