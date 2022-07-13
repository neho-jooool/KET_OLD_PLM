package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesTaskHistoryLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesTaskHistoryLink.class.getName();

   /**
    * @see ext.ket.sales.entity.KETSalesTaskHistoryLink
    */
   public static final java.lang.String ORG_TASK_ROLE = "orgTask";
   /**
    * @see ext.ket.sales.entity.KETSalesTaskHistoryLink
    */
   public ext.ket.sales.entity.KETSalesTask getOrgTask() {
      return (ext.ket.sales.entity.KETSalesTask) getRoleAObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTaskHistoryLink
    */
   public void setOrgTask(ext.ket.sales.entity.KETSalesTask the_orgTask) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_orgTask);
   }

   /**
    * @see ext.ket.sales.entity.KETSalesTaskHistoryLink
    */
   public static final java.lang.String TASKHISTORY_ROLE = "Taskhistory";
   /**
    * @see ext.ket.sales.entity.KETSalesTaskHistoryLink
    */
   public ext.ket.sales.entity.KETSalesTaskHistory getTaskhistory() {
      return (ext.ket.sales.entity.KETSalesTaskHistory) getRoleBObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTaskHistoryLink
    */
   public void setTaskhistory(ext.ket.sales.entity.KETSalesTaskHistory the_Taskhistory) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_Taskhistory);
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

   protected void super_writeExternal_KETSalesTaskHistoryLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesTaskHistoryLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSalesTaskHistoryLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( KETSalesTaskHistoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSalesTaskHistoryLink( _KETSalesTaskHistoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
