package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesProjectLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesProjectLink.class.getName();

   /**
    * @see ext.ket.sales.entity.KETSalesProjectLink
    */
   public static final java.lang.String SALES_PROJECT_ROLE = "salesProject";
   /**
    * @see ext.ket.sales.entity.KETSalesProjectLink
    */
   public ext.ket.sales.entity.KETSalesProject getSalesProject() {
      return (ext.ket.sales.entity.KETSalesProject) getRoleAObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProjectLink
    */
   public void setSalesProject(ext.ket.sales.entity.KETSalesProject the_salesProject) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_salesProject);
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProjectLink
    */
   public static final java.lang.String PJT_MASTER_ROLE = "pjtMaster";
   /**
    * @see ext.ket.sales.entity.KETSalesProjectLink
    */
   public e3ps.project.E3PSProjectMaster getPjtMaster() {
      return (e3ps.project.E3PSProjectMaster) getRoleBObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProjectLink
    */
   public void setPjtMaster(e3ps.project.E3PSProjectMaster the_pjtMaster) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_pjtMaster);
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

   protected void super_writeExternal_KETSalesProjectLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesProjectLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSalesProjectLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( KETSalesProjectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSalesProjectLink( _KETSalesProjectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
