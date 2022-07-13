package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AssessResultOutputLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = AssessResultOutputLink.class.getName();

   /**
    * 체크여부
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String OUTPUT_CHECK = "outputCheck";
   static int OUTPUT_CHECK_UPPER_LIMIT = -1;
   java.lang.String outputCheck;
   /**
    * 체크여부
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public java.lang.String getOutputCheck() {
      return outputCheck;
   }
   /**
    * 체크여부
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setOutputCheck(java.lang.String outputCheck) throws wt.util.WTPropertyVetoException {
      outputCheckValidate(outputCheck);
      this.outputCheck = outputCheck;
   }
   void outputCheckValidate(java.lang.String outputCheck) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_CHECK_UPPER_LIMIT < 1) {
         try { OUTPUT_CHECK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputCheck").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_CHECK_UPPER_LIMIT = 200; }
      }
      if (outputCheck != null && !wt.fc.PersistenceHelper.checkStoredLength(outputCheck.toString(), OUTPUT_CHECK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputCheck"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_CHECK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputCheck", this.outputCheck, outputCheck));
   }

   /**
    * comment
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String OUTPUT_COMMENT = "outputComment";
   static int OUTPUT_COMMENT_UPPER_LIMIT = -1;
   java.lang.String outputComment;
   /**
    * comment
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public java.lang.String getOutputComment() {
      return outputComment;
   }
   /**
    * comment
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setOutputComment(java.lang.String outputComment) throws wt.util.WTPropertyVetoException {
      outputCommentValidate(outputComment);
      this.outputComment = outputComment;
   }
   void outputCommentValidate(java.lang.String outputComment) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_COMMENT_UPPER_LIMIT < 1) {
         try { OUTPUT_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_COMMENT_UPPER_LIMIT = 200; }
      }
      if (outputComment != null && !wt.fc.PersistenceHelper.checkStoredLength(outputComment.toString(), OUTPUT_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputComment"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputComment", this.outputComment, outputComment));
   }

   /**
    * 산출물명
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String OUTPUT_NAME = "outputName";
   static int OUTPUT_NAME_UPPER_LIMIT = -1;
   java.lang.String outputName;
   /**
    * 산출물명
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public java.lang.String getOutputName() {
      return outputName;
   }
   /**
    * 산출물명
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setOutputName(java.lang.String outputName) throws wt.util.WTPropertyVetoException {
      outputNameValidate(outputName);
      this.outputName = outputName;
   }
   void outputNameValidate(java.lang.String outputName) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_NAME_UPPER_LIMIT < 1) {
         try { OUTPUT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_NAME_UPPER_LIMIT = 200; }
      }
      if (outputName != null && !wt.fc.PersistenceHelper.checkStoredLength(outputName.toString(), OUTPUT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputName"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputName", this.outputName, outputName));
   }

   /**
    * 책임자
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String OUTPUT_CHARGE = "outputCharge";
   static int OUTPUT_CHARGE_UPPER_LIMIT = -1;
   java.lang.String outputCharge;
   /**
    * 책임자
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public java.lang.String getOutputCharge() {
      return outputCharge;
   }
   /**
    * 책임자
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setOutputCharge(java.lang.String outputCharge) throws wt.util.WTPropertyVetoException {
      outputChargeValidate(outputCharge);
      this.outputCharge = outputCharge;
   }
   void outputChargeValidate(java.lang.String outputCharge) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_CHARGE_UPPER_LIMIT < 1) {
         try { OUTPUT_CHARGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputCharge").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_CHARGE_UPPER_LIMIT = 200; }
      }
      if (outputCharge != null && !wt.fc.PersistenceHelper.checkStoredLength(outputCharge.toString(), OUTPUT_CHARGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputCharge"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_CHARGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputCharge", this.outputCharge, outputCharge));
   }

   /**
    * 상태
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String OUTPUT_STATE = "outputState";
   static int OUTPUT_STATE_UPPER_LIMIT = -1;
   java.lang.String outputState;
   /**
    * 상태
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public java.lang.String getOutputState() {
      return outputState;
   }
   /**
    * 상태
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setOutputState(java.lang.String outputState) throws wt.util.WTPropertyVetoException {
      outputStateValidate(outputState);
      this.outputState = outputState;
   }
   void outputStateValidate(java.lang.String outputState) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_STATE_UPPER_LIMIT < 1) {
         try { OUTPUT_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_STATE_UPPER_LIMIT = 200; }
      }
      if (outputState != null && !wt.fc.PersistenceHelper.checkStoredLength(outputState.toString(), OUTPUT_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputState"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputState", this.outputState, outputState));
   }

   /**
    * 산출물객체
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String OUTPUT_OID = "outputOid";
   static int OUTPUT_OID_UPPER_LIMIT = -1;
   java.lang.String outputOid;
   /**
    * 산출물객체
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public java.lang.String getOutputOid() {
      return outputOid;
   }
   /**
    * 산출물객체
    *
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setOutputOid(java.lang.String outputOid) throws wt.util.WTPropertyVetoException {
      outputOidValidate(outputOid);
      this.outputOid = outputOid;
   }
   void outputOidValidate(java.lang.String outputOid) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_OID_UPPER_LIMIT < 1) {
         try { OUTPUT_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_OID_UPPER_LIMIT = 200; }
      }
      if (outputOid != null && !wt.fc.PersistenceHelper.checkStoredLength(outputOid.toString(), OUTPUT_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputOid"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputOid", this.outputOid, outputOid));
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String REV = "rev";
   int rev;
   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public int getRev() {
      return rev;
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setRev(int rev) throws wt.util.WTPropertyVetoException {
      revValidate(rev);
      this.rev = rev;
   }
   void revValidate(int rev) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String ASSESS_ROLE = "assess";
   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public ext.ket.project.gate.entity.GateAssessResult getAssess() {
      return (ext.ket.project.gate.entity.GateAssessResult) getRoleAObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setAssess(ext.ket.project.gate.entity.GateAssessResult the_assess) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_assess);
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public static final java.lang.String OUTPUT_ROLE = "output";
   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public e3ps.project.ProjectOutput getOutput() {
      return (e3ps.project.ProjectOutput) getRoleBObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultOutputLink
    */
   public void setOutput(e3ps.project.ProjectOutput the_output) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_output);
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

   public static final long EXTERNALIZATION_VERSION_UID = -620693784612214525L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( outputCharge );
      output.writeObject( outputCheck );
      output.writeObject( outputComment );
      output.writeObject( outputName );
      output.writeObject( outputOid );
      output.writeObject( outputState );
      output.writeInt( rev );
   }

   protected void super_writeExternal_AssessResultOutputLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.AssessResultOutputLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_AssessResultOutputLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "outputCharge", outputCharge );
      output.setString( "outputCheck", outputCheck );
      output.setString( "outputComment", outputComment );
      output.setString( "outputName", outputName );
      output.setString( "outputOid", outputOid );
      output.setString( "outputState", outputState );
      output.setInt( "rev", rev );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      outputCharge = input.getString( "outputCharge" );
      outputCheck = input.getString( "outputCheck" );
      outputComment = input.getString( "outputComment" );
      outputName = input.getString( "outputName" );
      outputOid = input.getString( "outputOid" );
      outputState = input.getString( "outputState" );
      rev = input.getInt( "rev" );
   }

   boolean readVersion_620693784612214525L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      outputCharge = (java.lang.String) input.readObject();
      outputCheck = (java.lang.String) input.readObject();
      outputComment = (java.lang.String) input.readObject();
      outputName = (java.lang.String) input.readObject();
      outputOid = (java.lang.String) input.readObject();
      outputState = (java.lang.String) input.readObject();
      rev = input.readInt();
      return true;
   }

   protected boolean readVersion( AssessResultOutputLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_620693784612214525L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_AssessResultOutputLink( _AssessResultOutputLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
