package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TaskMemberLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = TaskMemberLink.class.getName();

   /**
    * 0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member
    *
    * @see e3ps.project.TaskMemberLink
    */
   public static final java.lang.String TASK_MEMBER_TYPE = "taskMemberType";
   int taskMemberType;
   /**
    * 0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member
    *
    * @see e3ps.project.TaskMemberLink
    */
   public int getTaskMemberType() {
      return taskMemberType;
   }
   /**
    * 0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member
    *
    * @see e3ps.project.TaskMemberLink
    */
   public void setTaskMemberType(int taskMemberType) throws wt.util.WTPropertyVetoException {
      taskMemberTypeValidate(taskMemberType);
      this.taskMemberType = taskMemberType;
   }
   void taskMemberTypeValidate(int taskMemberType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Role Name
    *
    * @see e3ps.project.TaskMemberLink
    */
   public static final java.lang.String TASK_ROLE_NAME = "taskRoleName";
   static int TASK_ROLE_NAME_UPPER_LIMIT = -1;
   java.lang.String taskRoleName;
   /**
    * Role Name
    *
    * @see e3ps.project.TaskMemberLink
    */
   public java.lang.String getTaskRoleName() {
      return taskRoleName;
   }
   /**
    * Role Name
    *
    * @see e3ps.project.TaskMemberLink
    */
   public void setTaskRoleName(java.lang.String taskRoleName) throws wt.util.WTPropertyVetoException {
      taskRoleNameValidate(taskRoleName);
      this.taskRoleName = taskRoleName;
   }
   void taskRoleNameValidate(java.lang.String taskRoleName) throws wt.util.WTPropertyVetoException {
      if (TASK_ROLE_NAME_UPPER_LIMIT < 1) {
         try { TASK_ROLE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskRoleName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_ROLE_NAME_UPPER_LIMIT = 200; }
      }
      if (taskRoleName != null && !wt.fc.PersistenceHelper.checkStoredLength(taskRoleName.toString(), TASK_ROLE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskRoleName"), java.lang.String.valueOf(java.lang.Math.min(TASK_ROLE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskRoleName", this.taskRoleName, taskRoleName));
   }

   /**
    * TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TaskMemberLink
    */
   public static final java.lang.String TASK_HISTORY = "taskHistory";
   int taskHistory;
   /**
    * TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TaskMemberLink
    */
   public int getTaskHistory() {
      return taskHistory;
   }
   /**
    * TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TaskMemberLink
    */
   public void setTaskHistory(int taskHistory) throws wt.util.WTPropertyVetoException {
      taskHistoryValidate(taskHistory);
      this.taskHistory = taskHistory;
   }
   void taskHistoryValidate(int taskHistory) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.TaskMemberLink
    */
   public static final java.lang.String ACTOR = "actor";
   wt.org.WTPrincipalReference actor;
   /**
    * @see e3ps.project.TaskMemberLink
    */
   public wt.org.WTPrincipalReference getActor() {
      return actor;
   }
   /**
    * @see e3ps.project.TaskMemberLink
    */
   public void setActor(wt.org.WTPrincipalReference actor) throws wt.util.WTPropertyVetoException {
      actorValidate(actor);
      this.actor = actor;
   }
   void actorValidate(wt.org.WTPrincipalReference actor) throws wt.util.WTPropertyVetoException {
      if (actor == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actor") },
               new java.beans.PropertyChangeEvent(this, "actor", this.actor, actor));
   }

   /**
    * @see e3ps.project.TaskMemberLink
    */
   public static final java.lang.String TASK_ROLE = "task";
   /**
    * @see e3ps.project.TaskMemberLink
    */
   public e3ps.project.TemplateTask getTask() {
      return (e3ps.project.TemplateTask) getRoleAObject();
   }
   /**
    * @see e3ps.project.TaskMemberLink
    */
   public void setTask(e3ps.project.TemplateTask the_task) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_task);
   }

   /**
    * @see e3ps.project.TaskMemberLink
    */
   public static final java.lang.String MEMBER_ROLE = "member";
   /**
    * @see e3ps.project.TaskMemberLink
    */
   public e3ps.project.ProjectMemberLink getMember() {
      return (e3ps.project.ProjectMemberLink) getRoleBObject();
   }
   /**
    * @see e3ps.project.TaskMemberLink
    */
   public void setMember(e3ps.project.ProjectMemberLink the_member) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_member);
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

   public static final long EXTERNALIZATION_VERSION_UID = -3835163342759266379L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( actor );
      output.writeInt( taskHistory );
      output.writeInt( taskMemberType );
      output.writeObject( taskRoleName );
   }

   protected void super_writeExternal_TaskMemberLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.TaskMemberLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_TaskMemberLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "actor", actor, wt.org.WTPrincipalReference.class, true );
      output.setInt( "taskHistory", taskHistory );
      output.setInt( "taskMemberType", taskMemberType );
      output.setString( "taskRoleName", taskRoleName );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      actor = (wt.org.WTPrincipalReference) input.readObject( "actor", actor, wt.org.WTPrincipalReference.class, true );
      taskHistory = input.getInt( "taskHistory" );
      taskMemberType = input.getInt( "taskMemberType" );
      taskRoleName = input.getString( "taskRoleName" );
   }

   boolean readVersion_3835163342759266379L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      actor = (wt.org.WTPrincipalReference) input.readObject();
      taskHistory = input.readInt();
      taskMemberType = input.readInt();
      taskRoleName = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( TaskMemberLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3835163342759266379L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_TaskMemberLink( _TaskMemberLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
