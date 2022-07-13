package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _GateAssessResult extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = GateAssessResult.class.getName();

   /**
    * 최종평가
    *
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public static final java.lang.String FINAL_RESULT = "finalResult";
   static int FINAL_RESULT_UPPER_LIMIT = -1;
   java.lang.String finalResult;
   /**
    * 최종평가
    *
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public java.lang.String getFinalResult() {
      return finalResult;
   }
   /**
    * 최종평가
    *
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public void setFinalResult(java.lang.String finalResult) throws wt.util.WTPropertyVetoException {
      finalResultValidate(finalResult);
      this.finalResult = finalResult;
   }
   void finalResultValidate(java.lang.String finalResult) throws wt.util.WTPropertyVetoException {
      if (FINAL_RESULT_UPPER_LIMIT < 1) {
         try { FINAL_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("finalResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FINAL_RESULT_UPPER_LIMIT = 200; }
      }
      if (finalResult != null && !wt.fc.PersistenceHelper.checkStoredLength(finalResult.toString(), FINAL_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "finalResult"), java.lang.String.valueOf(java.lang.Math.min(FINAL_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "finalResult", this.finalResult, finalResult));
   }

   /**
    * 합격근거
    *
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public static final java.lang.String PASS_COMMENT = "passComment";
   static int PASS_COMMENT_UPPER_LIMIT = -1;
   java.lang.String passComment;
   /**
    * 합격근거
    *
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public java.lang.String getPassComment() {
      return passComment;
   }
   /**
    * 합격근거
    *
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public void setPassComment(java.lang.String passComment) throws wt.util.WTPropertyVetoException {
      passCommentValidate(passComment);
      this.passComment = passComment;
   }
   void passCommentValidate(java.lang.String passComment) throws wt.util.WTPropertyVetoException {
      if (PASS_COMMENT_UPPER_LIMIT < 1) {
         try { PASS_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("passComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PASS_COMMENT_UPPER_LIMIT = 4000; }
      }
      if (passComment != null && !wt.fc.PersistenceHelper.checkStoredLength(passComment.toString(), PASS_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "passComment"), java.lang.String.valueOf(java.lang.Math.min(PASS_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "passComment", this.passComment, passComment));
   }

   /**
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public e3ps.project.E3PSProject getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProject) projectReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public void setProject(e3ps.project.E3PSProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProject) the_project));
   }
   /**
    * @see ext.ket.project.gate.entity.GateAssessResult
    */
   public void setProjectReference(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      projectReferenceValidate(the_projectReference);
      projectReference = (wt.fc.ObjectReference) the_projectReference;
   }
   void projectReferenceValidate(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      if (the_projectReference != null && the_projectReference.getReferencedClass() != null &&
            !e3ps.project.E3PSProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 6060282487944414858L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( finalResult );
      output.writeObject( passComment );
      output.writeObject( projectReference );
   }

   protected void super_writeExternal_GateAssessResult(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.GateAssessResult) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_GateAssessResult(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "finalResult", finalResult );
      output.setString( "passComment", passComment );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      finalResult = input.getString( "finalResult" );
      passComment = input.getString( "passComment" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion6060282487944414858L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      finalResult = (java.lang.String) input.readObject();
      passComment = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( GateAssessResult thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6060282487944414858L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_GateAssessResult( _GateAssessResult thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
