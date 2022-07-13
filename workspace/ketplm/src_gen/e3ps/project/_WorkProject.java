package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _WorkProject extends e3ps.project.E3PSProject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = WorkProject.class.getName();

   /**
    * @see e3ps.project.WorkProject
    */
   public static final java.lang.String DEV_DEPT = "devDept";
   /**
    * @see e3ps.project.WorkProject
    */
   public static final java.lang.String DEV_DEPT_REFERENCE = "devDeptReference";
   wt.fc.ObjectReference devDeptReference;
   /**
    * @see e3ps.project.WorkProject
    */
   public e3ps.groupware.company.Department getDevDept() {
      return (devDeptReference != null) ? (e3ps.groupware.company.Department) devDeptReference.getObject() : null;
   }
   /**
    * @see e3ps.project.WorkProject
    */
   public wt.fc.ObjectReference getDevDeptReference() {
      return devDeptReference;
   }
   /**
    * @see e3ps.project.WorkProject
    */
   public void setDevDept(e3ps.groupware.company.Department the_devDept) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevDeptReference(the_devDept == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_devDept));
   }
   /**
    * @see e3ps.project.WorkProject
    */
   public void setDevDeptReference(wt.fc.ObjectReference the_devDeptReference) throws wt.util.WTPropertyVetoException {
      devDeptReferenceValidate(the_devDeptReference);
      devDeptReference = (wt.fc.ObjectReference) the_devDeptReference;
   }
   void devDeptReferenceValidate(wt.fc.ObjectReference the_devDeptReference) throws wt.util.WTPropertyVetoException {
      if (the_devDeptReference == null || the_devDeptReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devDeptReference") },
               new java.beans.PropertyChangeEvent(this, "devDeptReference", this.devDeptReference, devDeptReference));
      if (the_devDeptReference != null && the_devDeptReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_devDeptReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devDeptReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "devDeptReference", this.devDeptReference, devDeptReference));
   }

   /**
    * @see e3ps.project.WorkProject
    */
   public static final java.lang.String WORK_TYPE = "workType";
   /**
    * @see e3ps.project.WorkProject
    */
   public static final java.lang.String WORK_TYPE_REFERENCE = "workTypeReference";
   wt.fc.ObjectReference workTypeReference;
   /**
    * @see e3ps.project.WorkProject
    */
   public e3ps.common.code.NumberCode getWorkType() {
      return (workTypeReference != null) ? (e3ps.common.code.NumberCode) workTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.WorkProject
    */
   public wt.fc.ObjectReference getWorkTypeReference() {
      return workTypeReference;
   }
   /**
    * @see e3ps.project.WorkProject
    */
   public void setWorkType(e3ps.common.code.NumberCode the_workType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setWorkTypeReference(the_workType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_workType));
   }
   /**
    * @see e3ps.project.WorkProject
    */
   public void setWorkTypeReference(wt.fc.ObjectReference the_workTypeReference) throws wt.util.WTPropertyVetoException {
      workTypeReferenceValidate(the_workTypeReference);
      workTypeReference = (wt.fc.ObjectReference) the_workTypeReference;
   }
   void workTypeReferenceValidate(wt.fc.ObjectReference the_workTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_workTypeReference != null && the_workTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_workTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "workTypeReference", this.workTypeReference, workTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 5476097985433930657L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( devDeptReference );
      output.writeObject( workTypeReference );
   }

   protected void super_writeExternal_WorkProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.WorkProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_WorkProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "devDeptReference", devDeptReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "workTypeReference", workTypeReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      devDeptReference = (wt.fc.ObjectReference) input.readObject( "devDeptReference", devDeptReference, wt.fc.ObjectReference.class, true );
      workTypeReference = (wt.fc.ObjectReference) input.readObject( "workTypeReference", workTypeReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion5476097985433930657L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      devDeptReference = (wt.fc.ObjectReference) input.readObject();
      workTypeReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( WorkProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5476097985433930657L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_WorkProject( _WorkProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
