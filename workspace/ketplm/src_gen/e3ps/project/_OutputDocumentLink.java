package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _OutputDocumentLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = OutputDocumentLink.class.getName();

   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public static final java.lang.String BRANCH_IDENTIFIER = "branchIdentifier";
   long branchIdentifier;
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public long getBranchIdentifier() {
      return branchIdentifier;
   }
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public void setBranchIdentifier(long branchIdentifier) throws wt.util.WTPropertyVetoException {
      branchIdentifierValidate(branchIdentifier);
      this.branchIdentifier = branchIdentifier;
   }
   void branchIdentifierValidate(long branchIdentifier) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public static final java.lang.String DOC_CLASS_NAME = "docClassName";
   static int DOC_CLASS_NAME_UPPER_LIMIT = -1;
   java.lang.String docClassName;
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public java.lang.String getDocClassName() {
      return docClassName;
   }
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public void setDocClassName(java.lang.String docClassName) throws wt.util.WTPropertyVetoException {
      docClassNameValidate(docClassName);
      this.docClassName = docClassName;
   }
   void docClassNameValidate(java.lang.String docClassName) throws wt.util.WTPropertyVetoException {
      if (DOC_CLASS_NAME_UPPER_LIMIT < 1) {
         try { DOC_CLASS_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docClassName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_CLASS_NAME_UPPER_LIMIT = 200; }
      }
      if (docClassName != null && !wt.fc.PersistenceHelper.checkStoredLength(docClassName.toString(), DOC_CLASS_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docClassName"), java.lang.String.valueOf(java.lang.Math.min(DOC_CLASS_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docClassName", this.docClassName, docClassName));
   }

   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public static final java.lang.String OUTPUT_ROLE = "output";
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public e3ps.project.ProjectOutput getOutput() {
      return (e3ps.project.ProjectOutput) getRoleAObject();
   }
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public void setOutput(e3ps.project.ProjectOutput the_output) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_output);
   }

   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public static final java.lang.String OUTPUT_OBJECT_ROLE = "outputObject";
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public wt.enterprise.Master getOutputObject() {
      return (wt.enterprise.Master) getRoleBObject();
   }
   /**
    * @see e3ps.project.OutputDocumentLink
    */
   public void setOutputObject(wt.enterprise.Master the_outputObject) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_outputObject);
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

   public static final long EXTERNALIZATION_VERSION_UID = -8058574409767419023L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeLong( branchIdentifier );
      output.writeObject( docClassName );
   }

   protected void super_writeExternal_OutputDocumentLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.OutputDocumentLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_OutputDocumentLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setLong( "branchIdentifier", branchIdentifier );
      output.setString( "docClassName", docClassName );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      branchIdentifier = input.getLong( "branchIdentifier" );
      docClassName = input.getString( "docClassName" );
   }

   boolean readVersion_8058574409767419023L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      branchIdentifier = input.readLong();
      docClassName = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( OutputDocumentLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8058574409767419023L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_OutputDocumentLink( _OutputDocumentLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
