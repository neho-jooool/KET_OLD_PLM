package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectTheCostInterfaceChildHistory extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = ProjectTheCostInterfaceChildHistory.class.getName();

   /**
    * @see ext.ket.cost.entity.ProjectTheCostInterfaceChildHistory
    */
   public static final java.lang.String PROJECT_ROLE = "project";
   /**
    * @see ext.ket.cost.entity.ProjectTheCostInterfaceChildHistory
    */
   public e3ps.project.E3PSProjectMaster getProject() {
      return (e3ps.project.E3PSProjectMaster) getRoleAObject();
   }
   /**
    * @see ext.ket.cost.entity.ProjectTheCostInterfaceChildHistory
    */
   public void setProject(e3ps.project.E3PSProjectMaster the_project) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_project);
   }

   /**
    * @see ext.ket.cost.entity.ProjectTheCostInterfaceChildHistory
    */
   public static final java.lang.String COST_INTERFACE_CHILD_HISTORY_ROLE = "theCostInterfaceChildHistory";
   /**
    * @see ext.ket.cost.entity.ProjectTheCostInterfaceChildHistory
    */
   public ext.ket.cost.entity.CostInterfaceChildHistory getCostInterfaceChildHistory() {
      return (ext.ket.cost.entity.CostInterfaceChildHistory) getRoleBObject();
   }
   /**
    * @see ext.ket.cost.entity.ProjectTheCostInterfaceChildHistory
    */
   public void setCostInterfaceChildHistory(ext.ket.cost.entity.CostInterfaceChildHistory the_theCostInterfaceChildHistory) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theCostInterfaceChildHistory);
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

   protected void super_writeExternal_ProjectTheCostInterfaceChildHistory(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.ProjectTheCostInterfaceChildHistory) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ProjectTheCostInterfaceChildHistory(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( ProjectTheCostInterfaceChildHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ProjectTheCostInterfaceChildHistory( _ProjectTheCostInterfaceChildHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
