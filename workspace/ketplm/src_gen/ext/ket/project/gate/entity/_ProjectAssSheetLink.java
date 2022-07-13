package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectAssSheetLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = ProjectAssSheetLink.class.getName();

   /**
    * @see ext.ket.project.gate.entity.ProjectAssSheetLink
    */
   public static final java.lang.String ASSESS_ROLE = "assess";
   /**
    * @see ext.ket.project.gate.entity.ProjectAssSheetLink
    */
   public ext.ket.project.gate.entity.AssessSheet getAssess() {
      return (ext.ket.project.gate.entity.AssessSheet) getRoleAObject();
   }
   /**
    * @see ext.ket.project.gate.entity.ProjectAssSheetLink
    */
   public void setAssess(ext.ket.project.gate.entity.AssessSheet the_assess) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_assess);
   }

   /**
    * @see ext.ket.project.gate.entity.ProjectAssSheetLink
    */
   public static final java.lang.String PROJECT_ROLE = "project";
   /**
    * @see ext.ket.project.gate.entity.ProjectAssSheetLink
    */
   public e3ps.project.E3PSProject getProject() {
      return (e3ps.project.E3PSProject) getRoleBObject();
   }
   /**
    * @see ext.ket.project.gate.entity.ProjectAssSheetLink
    */
   public void setProject(e3ps.project.E3PSProject the_project) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_project);
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

   protected void super_writeExternal_ProjectAssSheetLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.ProjectAssSheetLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ProjectAssSheetLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( ProjectAssSheetLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((ProjectAssSheetLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ProjectAssSheetLink( _ProjectAssSheetLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
