package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectDeptRole implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectDeptRole.class.getName();

   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public static final java.lang.String DEPT_ROLE = "deptRole";
   static int DEPT_ROLE_UPPER_LIMIT = -1;
   java.lang.String deptRole;
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public java.lang.String getDeptRole() {
      return deptRole;
   }
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public void setDeptRole(java.lang.String deptRole) throws wt.util.WTPropertyVetoException {
      deptRoleValidate(deptRole);
      this.deptRole = deptRole;
   }
   void deptRoleValidate(java.lang.String deptRole) throws wt.util.WTPropertyVetoException {
      if (DEPT_ROLE_UPPER_LIMIT < 1) {
         try { DEPT_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_ROLE_UPPER_LIMIT = 200; }
      }
      if (deptRole != null && !wt.fc.PersistenceHelper.checkStoredLength(deptRole.toString(), DEPT_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptRole"), java.lang.String.valueOf(java.lang.Math.min(DEPT_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptRole", this.deptRole, deptRole));
   }

   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public e3ps.project.TemplateProject getProject() {
      return (projectReference != null) ? (e3ps.project.TemplateProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public void setProject(e3ps.project.TemplateProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.TemplateProject) the_project));
   }
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public void setProjectReference(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      projectReferenceValidate(the_projectReference);
      projectReference = (wt.fc.ObjectReference) the_projectReference;
   }
   void projectReferenceValidate(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      if (the_projectReference == null || the_projectReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference") },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
      if (the_projectReference != null && the_projectReference.getReferencedClass() != null &&
            !e3ps.project.TemplateProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public static final java.lang.String DEPARTMENT = "department";
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public static final java.lang.String DEPARTMENT_REFERENCE = "departmentReference";
   wt.fc.ObjectReference departmentReference;
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public e3ps.groupware.company.Department getDepartment() {
      return (departmentReference != null) ? (e3ps.groupware.company.Department) departmentReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public wt.fc.ObjectReference getDepartmentReference() {
      return departmentReference;
   }
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public void setDepartment(e3ps.groupware.company.Department the_department) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDepartmentReference(the_department == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_department));
   }
   /**
    * @see e3ps.project.ProjectDeptRole
    */
   public void setDepartmentReference(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      departmentReferenceValidate(the_departmentReference);
      departmentReference = (wt.fc.ObjectReference) the_departmentReference;
   }
   void departmentReferenceValidate(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      if (the_departmentReference == null || the_departmentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "departmentReference") },
               new java.beans.PropertyChangeEvent(this, "departmentReference", this.departmentReference, departmentReference));
      if (the_departmentReference != null && the_departmentReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_departmentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "departmentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "departmentReference", this.departmentReference, departmentReference));
   }

   wt.fc.PersistInfo thePersistInfo = new wt.fc.PersistInfo();
   /**
    * @see wt.fc.Persistable
    */
   public wt.fc.PersistInfo getPersistInfo() {
      return thePersistInfo;
   }
   /**
    * @see wt.fc.Persistable
    */
   public void setPersistInfo(wt.fc.PersistInfo thePersistInfo) {
      this.thePersistInfo = thePersistInfo;
   }

   public java.lang.String toString() {
      return getConceptualClassname();
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

   public boolean equals(java.lang.Object obj) {
      return wt.fc.PersistenceHelper.equals(this, obj);
   }

   public int hashCode() {
      return wt.fc.PersistenceHelper.hashCode(this);
   }

   public static final long EXTERNALIZATION_VERSION_UID = 8507009830005144196L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( departmentReference );
      output.writeObject( deptRole );
      output.writeObject( projectReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectDeptRole) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      output.setString( "deptRole", deptRole );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      departmentReference = (wt.fc.ObjectReference) input.readObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      deptRole = input.getString( "deptRole" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion8507009830005144196L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      departmentReference = (wt.fc.ObjectReference) input.readObject();
      deptRole = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( ProjectDeptRole thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8507009830005144196L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
