package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectDependencyLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectDependencyLink.class.getName();

   /**
    * 양산/개발 프로젝트
    *
    * @see e3ps.project.ProjectDependencyLink
    */
   public static final java.lang.String DEPEND_SOURCE_ROLE = "dependSource";
   /**
    * 양산/개발 프로젝트
    *
    * @see e3ps.project.ProjectDependencyLink
    */
   public e3ps.project.TemplateProject getDependSource() {
      return (e3ps.project.TemplateProject) getRoleAObject();
   }
   /**
    * 양산/개발 프로젝트
    *
    * @see e3ps.project.ProjectDependencyLink
    */
   public void setDependSource(e3ps.project.TemplateProject the_dependSource) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_dependSource);
   }

   /**
    * 사전개발계획
    *
    * @see e3ps.project.ProjectDependencyLink
    */
   public static final java.lang.String DEPEND_TARGET_ROLE = "dependTarget";
   /**
    * 사전개발계획
    *
    * @see e3ps.project.ProjectDependencyLink
    */
   public e3ps.project.TemplateProject getDependTarget() {
      return (e3ps.project.TemplateProject) getRoleBObject();
   }
   /**
    * 사전개발계획
    *
    * @see e3ps.project.ProjectDependencyLink
    */
   public void setDependTarget(e3ps.project.TemplateProject the_dependTarget) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 2538346186404157511L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

   }

   protected void super_writeExternal_ProjectDependencyLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectDependencyLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ProjectDependencyLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( ProjectDependencyLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((ProjectDependencyLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ProjectDependencyLink( _ProjectDependencyLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
