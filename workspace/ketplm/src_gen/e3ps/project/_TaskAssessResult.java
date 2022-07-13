package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TaskAssessResult extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = TaskAssessResult.class.getName();

   /**
    * @see e3ps.project.TaskAssessResult
    */
   public static final java.lang.String REV = "rev";
   int rev = 1;
   /**
    * @see e3ps.project.TaskAssessResult
    */
   public int getRev() {
      return rev;
   }
   /**
    * @see e3ps.project.TaskAssessResult
    */
   public void setRev(int rev) throws wt.util.WTPropertyVetoException {
      revValidate(rev);
      this.rev = rev;
   }
   void revValidate(int rev) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 평가관리 목표값
    *
    * @see e3ps.project.TaskAssessResult
    */
   public static final java.lang.String TARGET_SCORE = "targetScore";
   static int TARGET_SCORE_UPPER_LIMIT = -1;
   java.lang.String targetScore;
   /**
    * 평가관리 목표값
    *
    * @see e3ps.project.TaskAssessResult
    */
   public java.lang.String getTargetScore() {
      return targetScore;
   }
   /**
    * 평가관리 목표값
    *
    * @see e3ps.project.TaskAssessResult
    */
   public void setTargetScore(java.lang.String targetScore) throws wt.util.WTPropertyVetoException {
      targetScoreValidate(targetScore);
      this.targetScore = targetScore;
   }
   void targetScoreValidate(java.lang.String targetScore) throws wt.util.WTPropertyVetoException {
      if (TARGET_SCORE_UPPER_LIMIT < 1) {
         try { TARGET_SCORE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetScore").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_SCORE_UPPER_LIMIT = 200; }
      }
      if (targetScore != null && !wt.fc.PersistenceHelper.checkStoredLength(targetScore.toString(), TARGET_SCORE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetScore"), java.lang.String.valueOf(java.lang.Math.min(TARGET_SCORE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetScore", this.targetScore, targetScore));
   }

   /**
    * 평가관리 실적값
    *
    * @see e3ps.project.TaskAssessResult
    */
   public static final java.lang.String RESULT_SCORE = "resultScore";
   static int RESULT_SCORE_UPPER_LIMIT = -1;
   java.lang.String resultScore;
   /**
    * 평가관리 실적값
    *
    * @see e3ps.project.TaskAssessResult
    */
   public java.lang.String getResultScore() {
      return resultScore;
   }
   /**
    * 평가관리 실적값
    *
    * @see e3ps.project.TaskAssessResult
    */
   public void setResultScore(java.lang.String resultScore) throws wt.util.WTPropertyVetoException {
      resultScoreValidate(resultScore);
      this.resultScore = resultScore;
   }
   void resultScoreValidate(java.lang.String resultScore) throws wt.util.WTPropertyVetoException {
      if (RESULT_SCORE_UPPER_LIMIT < 1) {
         try { RESULT_SCORE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("resultScore").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_SCORE_UPPER_LIMIT = 200; }
      }
      if (resultScore != null && !wt.fc.PersistenceHelper.checkStoredLength(resultScore.toString(), RESULT_SCORE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "resultScore"), java.lang.String.valueOf(java.lang.Math.min(RESULT_SCORE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "resultScore", this.resultScore, resultScore));
   }

   /**
    * 평가관리 합불여부 OK/NG/CDT)
    *
    * @see e3ps.project.TaskAssessResult
    */
   public static final java.lang.String RESULT = "result";
   static int RESULT_UPPER_LIMIT = -1;
   java.lang.String result;
   /**
    * 평가관리 합불여부 OK/NG/CDT)
    *
    * @see e3ps.project.TaskAssessResult
    */
   public java.lang.String getResult() {
      return result;
   }
   /**
    * 평가관리 합불여부 OK/NG/CDT)
    *
    * @see e3ps.project.TaskAssessResult
    */
   public void setResult(java.lang.String result) throws wt.util.WTPropertyVetoException {
      resultValidate(result);
      this.result = result;
   }
   void resultValidate(java.lang.String result) throws wt.util.WTPropertyVetoException {
      if (RESULT_UPPER_LIMIT < 1) {
         try { RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("result").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_UPPER_LIMIT = 200; }
      }
      if (result != null && !wt.fc.PersistenceHelper.checkStoredLength(result.toString(), RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "result"), java.lang.String.valueOf(java.lang.Math.min(RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "result", this.result, result));
   }

   /**
    * 사유 및 설명
    *
    * @see e3ps.project.TaskAssessResult
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * 사유 및 설명
    *
    * @see e3ps.project.TaskAssessResult
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * 사유 및 설명
    *
    * @see e3ps.project.TaskAssessResult
    */
   public void setDescription(java.lang.String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(java.lang.String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 200; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), java.lang.String.valueOf(java.lang.Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1910338393395010798L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( description );
      output.writeObject( result );
      output.writeObject( resultScore );
      output.writeInt( rev );
      output.writeObject( targetScore );
   }

   protected void super_writeExternal_TaskAssessResult(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.TaskAssessResult) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_TaskAssessResult(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "description", description );
      output.setString( "result", result );
      output.setString( "resultScore", resultScore );
      output.setInt( "rev", rev );
      output.setString( "targetScore", targetScore );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      description = input.getString( "description" );
      result = input.getString( "result" );
      resultScore = input.getString( "resultScore" );
      rev = input.getInt( "rev" );
      targetScore = input.getString( "targetScore" );
   }

   boolean readVersion1910338393395010798L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      description = (java.lang.String) input.readObject();
      result = (java.lang.String) input.readObject();
      resultScore = (java.lang.String) input.readObject();
      rev = input.readInt();
      targetScore = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( TaskAssessResult thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1910338393395010798L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_TaskAssessResult( _TaskAssessResult thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
