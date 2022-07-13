package e3ps.groupware.board;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ImproveBoard extends e3ps.groupware.board.HelpBoard implements e3ps.common.impl.Tree, e3ps.common.impl.OwnPersistable, wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.board.boardResource";
   static final java.lang.String CLASSNAME = ImproveBoard.class.getName();

   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public static final java.lang.String CLASSIFICATION = "classification";
   static int CLASSIFICATION_UPPER_LIMIT = -1;
   java.lang.String classification;
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public java.lang.String getClassification() {
      return classification;
   }
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public void setClassification(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      classificationValidate(classification);
      this.classification = classification;
   }
   void classificationValidate(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      if (CLASSIFICATION_UPPER_LIMIT < 1) {
         try { CLASSIFICATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classification").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASSIFICATION_UPPER_LIMIT = 10; }
      }
      if (classification != null && !wt.fc.PersistenceHelper.checkStoredLength(classification.toString(), CLASSIFICATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classification"), java.lang.String.valueOf(java.lang.Math.min(CLASSIFICATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classification", this.classification, classification));
   }

   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public static final java.lang.String STATE = "state";
   static int STATE_UPPER_LIMIT = -1;
   java.lang.String state;
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public java.lang.String getState() {
      return state;
   }
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public void setState(java.lang.String state) throws wt.util.WTPropertyVetoException {
      stateValidate(state);
      this.state = state;
   }
   void stateValidate(java.lang.String state) throws wt.util.WTPropertyVetoException {
      if (STATE_UPPER_LIMIT < 1) {
         try { STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("state").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STATE_UPPER_LIMIT = 10; }
      }
      if (state != null && !wt.fc.PersistenceHelper.checkStoredLength(state.toString(), STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "state"), java.lang.String.valueOf(java.lang.Math.min(STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "state", this.state, state));
   }

   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public static final java.lang.String DIVISION = "division";
   static int DIVISION_UPPER_LIMIT = -1;
   java.lang.String division;
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public java.lang.String getDivision() {
      return division;
   }
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public void setDivision(java.lang.String division) throws wt.util.WTPropertyVetoException {
      divisionValidate(division);
      this.division = division;
   }
   void divisionValidate(java.lang.String division) throws wt.util.WTPropertyVetoException {
      if (DIVISION_UPPER_LIMIT < 1) {
         try { DIVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("division").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIVISION_UPPER_LIMIT = 200; }
      }
      if (division != null && !wt.fc.PersistenceHelper.checkStoredLength(division.toString(), DIVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "division"), java.lang.String.valueOf(java.lang.Math.min(DIVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "division", this.division, division));
   }

   /**
    * ??????
    *
    * @see e3ps.groupware.board.ImproveBoard
    */
   public static final java.lang.String HANDLE_RESULT = "handleResult";
   static int HANDLE_RESULT_UPPER_LIMIT = -1;
   java.lang.String handleResult;
   /**
    * ??????
    *
    * @see e3ps.groupware.board.ImproveBoard
    */
   public java.lang.String getHandleResult() {
      return handleResult;
   }
   /**
    * ??????
    *
    * @see e3ps.groupware.board.ImproveBoard
    */
   public void setHandleResult(java.lang.String handleResult) throws wt.util.WTPropertyVetoException {
      handleResultValidate(handleResult);
      this.handleResult = handleResult;
   }
   void handleResultValidate(java.lang.String handleResult) throws wt.util.WTPropertyVetoException {
      if (HANDLE_RESULT_UPPER_LIMIT < 1) {
         try { HANDLE_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("handleResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HANDLE_RESULT_UPPER_LIMIT = 4000; }
      }
      if (handleResult != null && !wt.fc.PersistenceHelper.checkStoredLength(handleResult.toString(), HANDLE_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "handleResult"), java.lang.String.valueOf(java.lang.Math.min(HANDLE_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "handleResult", this.handleResult, handleResult));
   }

   /**
    * ????
    *
    * @see e3ps.groupware.board.ImproveBoard
    */
   public static final java.lang.String CAUSE = "cause";
   static int CAUSE_UPPER_LIMIT = -1;
   java.lang.String cause;
   /**
    * ????
    *
    * @see e3ps.groupware.board.ImproveBoard
    */
   public java.lang.String getCause() {
      return cause;
   }
   /**
    * ????
    *
    * @see e3ps.groupware.board.ImproveBoard
    */
   public void setCause(java.lang.String cause) throws wt.util.WTPropertyVetoException {
      causeValidate(cause);
      this.cause = cause;
   }
   void causeValidate(java.lang.String cause) throws wt.util.WTPropertyVetoException {
      if (CAUSE_UPPER_LIMIT < 1) {
         try { CAUSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cause").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAUSE_UPPER_LIMIT = 4000; }
      }
      if (cause != null && !wt.fc.PersistenceHelper.checkStoredLength(cause.toString(), CAUSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cause"), java.lang.String.valueOf(java.lang.Math.min(CAUSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cause", this.cause, cause));
   }

   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public static final java.lang.String USER = "user";
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see e3ps.groupware.board.ImproveBoard
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1905446712020029362L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( cause );
      output.writeObject( classification );
      output.writeObject( division );
      output.writeObject( handleResult );
      output.writeObject( state );
      output.writeObject( userReference );
   }

   protected void super_writeExternal_ImproveBoard(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.board.ImproveBoard) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ImproveBoard(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "cause", cause );
      output.setString( "classification", classification );
      output.setString( "division", division );
      output.setString( "handleResult", handleResult );
      output.setString( "state", state );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      cause = input.getString( "cause" );
      classification = input.getString( "classification" );
      division = input.getString( "division" );
      handleResult = input.getString( "handleResult" );
      state = input.getString( "state" );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion1905446712020029362L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      cause = (java.lang.String) input.readObject();
      classification = (java.lang.String) input.readObject();
      division = (java.lang.String) input.readObject();
      handleResult = (java.lang.String) input.readObject();
      state = (java.lang.String) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( ImproveBoard thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1905446712020029362L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ImproveBoard( _ImproveBoard thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
