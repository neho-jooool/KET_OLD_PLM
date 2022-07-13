package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectMasterCodeSubCodeLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectMasterCodeSubCodeLink.class.getName();

   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public static final java.lang.String PROJECT_ID = "projectId";
   static int PROJECT_ID_UPPER_LIMIT = -1;
   java.lang.String projectId;
   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public java.lang.String getProjectId() {
      return projectId;
   }
   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public void setProjectId(java.lang.String projectId) throws wt.util.WTPropertyVetoException {
      projectIdValidate(projectId);
      this.projectId = projectId;
   }
   void projectIdValidate(java.lang.String projectId) throws wt.util.WTPropertyVetoException {
      if (PROJECT_ID_UPPER_LIMIT < 1) {
         try { PROJECT_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_ID_UPPER_LIMIT = 200; }
      }
      if (projectId != null && !wt.fc.PersistenceHelper.checkStoredLength(projectId.toString(), PROJECT_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectId"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectId", this.projectId, projectId));
   }

   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public static final java.lang.String SUB_CODE_ROLE = "subCode";
   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public e3ps.common.code.NumberCode getSubCode() {
      return (e3ps.common.code.NumberCode) getRoleAObject();
   }
   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public void setSubCode(e3ps.common.code.NumberCode the_subCode) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_subCode);
   }

   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public static final java.lang.String MASTER_CODE_ROLE = "masterCode";
   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public e3ps.common.code.NumberCode getMasterCode() {
      return (e3ps.common.code.NumberCode) getRoleBObject();
   }
   /**
    * @see e3ps.project.ProjectMasterCodeSubCodeLink
    */
   public void setMasterCode(e3ps.common.code.NumberCode the_masterCode) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_masterCode);
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

   public static final long EXTERNALIZATION_VERSION_UID = 2205893274743543173L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( projectId );
   }

   protected void super_writeExternal_ProjectMasterCodeSubCodeLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectMasterCodeSubCodeLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ProjectMasterCodeSubCodeLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "projectId", projectId );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      projectId = input.getString( "projectId" );
   }

   boolean readVersion2205893274743543173L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      projectId = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ProjectMasterCodeSubCodeLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2205893274743543173L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ProjectMasterCodeSubCodeLink( _ProjectMasterCodeSubCodeLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
