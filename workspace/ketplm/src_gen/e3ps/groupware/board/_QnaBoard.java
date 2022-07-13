package e3ps.groupware.board;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _QnaBoard extends e3ps.groupware.board.HelpBoard implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.board.boardResource";
   static final java.lang.String CLASSNAME = QnaBoard.class.getName();

   /**
    * @see e3ps.groupware.board.QnaBoard
    */
   public static final java.lang.String CLASSIFICATION = "classification";
   static int CLASSIFICATION_UPPER_LIMIT = -1;
   java.lang.String classification;
   /**
    * @see e3ps.groupware.board.QnaBoard
    */
   public java.lang.String getClassification() {
      return classification;
   }
   /**
    * @see e3ps.groupware.board.QnaBoard
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
    * @see e3ps.groupware.board.QnaBoard
    */
   public static final java.lang.String STATE = "state";
   static int STATE_UPPER_LIMIT = -1;
   java.lang.String state;
   /**
    * @see e3ps.groupware.board.QnaBoard
    */
   public java.lang.String getState() {
      return state;
   }
   /**
    * @see e3ps.groupware.board.QnaBoard
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

   public static final long EXTERNALIZATION_VERSION_UID = -5546872457487983718L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( classification );
      output.writeObject( state );
   }

   protected void super_writeExternal_QnaBoard(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.board.QnaBoard) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_QnaBoard(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "classification", classification );
      output.setString( "state", state );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      classification = input.getString( "classification" );
      state = input.getString( "state" );
   }

   boolean readVersion_5546872457487983718L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      classification = (java.lang.String) input.readObject();
      state = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( QnaBoard thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_5546872457487983718L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_QnaBoard( _QnaBoard thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
