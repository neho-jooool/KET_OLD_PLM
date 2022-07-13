package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TaskDependencyLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = TaskDependencyLink.class.getName();

   /**
    * @see e3ps.project.TaskDependencyLink
    */
   public static final java.lang.String DELAY_DURATION = "delayDuration";
   int delayDuration;
   /**
    * @see e3ps.project.TaskDependencyLink
    */
   public int getDelayDuration() {
      return delayDuration;
   }
   /**
    * @see e3ps.project.TaskDependencyLink
    */
   public void setDelayDuration(int delayDuration) throws wt.util.WTPropertyVetoException {
      delayDurationValidate(delayDuration);
      this.delayDuration = delayDuration;
   }
   void delayDurationValidate(int delayDuration) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 모 TASK
    *
    * @see e3ps.project.TaskDependencyLink
    */
   public static final java.lang.String DEPEND_SOURCE_ROLE = "dependSource";
   /**
    * 모 TASK
    *
    * @see e3ps.project.TaskDependencyLink
    */
   public e3ps.project.TemplateTask getDependSource() {
      return (e3ps.project.TemplateTask) getRoleAObject();
   }
   /**
    * 모 TASK
    *
    * @see e3ps.project.TaskDependencyLink
    */
   public void setDependSource(e3ps.project.TemplateTask the_dependSource) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_dependSource);
   }

   /**
    * 자 TASK
    *
    * @see e3ps.project.TaskDependencyLink
    */
   public static final java.lang.String DEPEND_TARGET_ROLE = "dependTarget";
   /**
    * 자 TASK
    *
    * @see e3ps.project.TaskDependencyLink
    */
   public e3ps.project.TemplateTask getDependTarget() {
      return (e3ps.project.TemplateTask) getRoleBObject();
   }
   /**
    * 자 TASK
    *
    * @see e3ps.project.TaskDependencyLink
    */
   public void setDependTarget(e3ps.project.TemplateTask the_dependTarget) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_dependTarget);
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

   public static final long EXTERNALIZATION_VERSION_UID = 722948499120155994L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeInt( delayDuration );
   }

   protected void super_writeExternal_TaskDependencyLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.TaskDependencyLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_TaskDependencyLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setInt( "delayDuration", delayDuration );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      delayDuration = input.getInt( "delayDuration" );
   }

   boolean readVersion722948499120155994L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      delayDuration = input.readInt();
      return true;
   }

   protected boolean readVersion( TaskDependencyLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion722948499120155994L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_TaskDependencyLink( _TaskDependencyLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
