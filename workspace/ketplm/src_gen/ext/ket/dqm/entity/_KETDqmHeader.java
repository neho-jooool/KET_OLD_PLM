package ext.ket.dqm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDqmHeader implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dqm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDqmHeader.class.getName();

   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String PROBLEM = "problem";
   static int PROBLEM_UPPER_LIMIT = -1;
   java.lang.String problem;
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public java.lang.String getProblem() {
      return problem;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setProblem(java.lang.String problem) throws wt.util.WTPropertyVetoException {
      problemValidate(problem);
      this.problem = problem;
   }
   void problemValidate(java.lang.String problem) throws wt.util.WTPropertyVetoException {
      if (PROBLEM_UPPER_LIMIT < 1) {
         try { PROBLEM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problem").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEM_UPPER_LIMIT = 200; }
      }
      if (problem != null && !wt.fc.PersistenceHelper.checkStoredLength(problem.toString(), PROBLEM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problem"), java.lang.String.valueOf(java.lang.Math.min(PROBLEM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problem", this.problem, problem));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String PROBLEM_NO = "problemNo";
   static int PROBLEM_NO_UPPER_LIMIT = -1;
   java.lang.String problemNo;
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public java.lang.String getProblemNo() {
      return problemNo;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setProblemNo(java.lang.String problemNo) throws wt.util.WTPropertyVetoException {
      problemNoValidate(problemNo);
      this.problemNo = problemNo;
   }
   void problemNoValidate(java.lang.String problemNo) throws wt.util.WTPropertyVetoException {
      if (PROBLEM_NO_UPPER_LIMIT < 1) {
         try { PROBLEM_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEM_NO_UPPER_LIMIT = 200; }
      }
      if (problemNo != null && !wt.fc.PersistenceHelper.checkStoredLength(problemNo.toString(), PROBLEM_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemNo"), java.lang.String.valueOf(java.lang.Math.min(PROBLEM_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemNo", this.problemNo, problemNo));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String DQM_STATE_NAME = "dqmStateName";
   static int DQM_STATE_NAME_UPPER_LIMIT = -1;
   java.lang.String dqmStateName;
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public java.lang.String getDqmStateName() {
      return dqmStateName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setDqmStateName(java.lang.String dqmStateName) throws wt.util.WTPropertyVetoException {
      dqmStateNameValidate(dqmStateName);
      this.dqmStateName = dqmStateName;
   }
   void dqmStateNameValidate(java.lang.String dqmStateName) throws wt.util.WTPropertyVetoException {
      if (DQM_STATE_NAME_UPPER_LIMIT < 1) {
         try { DQM_STATE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dqmStateName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DQM_STATE_NAME_UPPER_LIMIT = 200; }
      }
      if (dqmStateName != null && !wt.fc.PersistenceHelper.checkStoredLength(dqmStateName.toString(), DQM_STATE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dqmStateName"), java.lang.String.valueOf(java.lang.Math.min(DQM_STATE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dqmStateName", this.dqmStateName, dqmStateName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String DQM_STATE_CODE = "dqmStateCode";
   static int DQM_STATE_CODE_UPPER_LIMIT = -1;
   java.lang.String dqmStateCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public java.lang.String getDqmStateCode() {
      return dqmStateCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setDqmStateCode(java.lang.String dqmStateCode) throws wt.util.WTPropertyVetoException {
      dqmStateCodeValidate(dqmStateCode);
      this.dqmStateCode = dqmStateCode;
   }
   void dqmStateCodeValidate(java.lang.String dqmStateCode) throws wt.util.WTPropertyVetoException {
      if (DQM_STATE_CODE_UPPER_LIMIT < 1) {
         try { DQM_STATE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dqmStateCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DQM_STATE_CODE_UPPER_LIMIT = 200; }
      }
      if (dqmStateCode != null && !wt.fc.PersistenceHelper.checkStoredLength(dqmStateCode.toString(), DQM_STATE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dqmStateCode"), java.lang.String.valueOf(java.lang.Math.min(DQM_STATE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dqmStateCode", this.dqmStateCode, dqmStateCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String CLOSE = "close";
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String CLOSE_REFERENCE = "closeReference";
   wt.fc.ObjectReference closeReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public ext.ket.dqm.entity.KETDqmClose getClose() {
      return (closeReference != null) ? (ext.ket.dqm.entity.KETDqmClose) closeReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public wt.fc.ObjectReference getCloseReference() {
      return closeReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setClose(ext.ket.dqm.entity.KETDqmClose the_close) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCloseReference(the_close == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.dqm.entity.KETDqmClose) the_close));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setCloseReference(wt.fc.ObjectReference the_closeReference) throws wt.util.WTPropertyVetoException {
      closeReferenceValidate(the_closeReference);
      closeReference = (wt.fc.ObjectReference) the_closeReference;
   }
   void closeReferenceValidate(wt.fc.ObjectReference the_closeReference) throws wt.util.WTPropertyVetoException {
      if (the_closeReference != null && the_closeReference.getReferencedClass() != null &&
            !ext.ket.dqm.entity.KETDqmClose.class.isAssignableFrom(the_closeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "closeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "closeReference", this.closeReference, closeReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String ACTION = "action";
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String ACTION_REFERENCE = "actionReference";
   wt.fc.ObjectReference actionReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public ext.ket.dqm.entity.KETDqmAction getAction() {
      return (actionReference != null) ? (ext.ket.dqm.entity.KETDqmAction) actionReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public wt.fc.ObjectReference getActionReference() {
      return actionReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setAction(ext.ket.dqm.entity.KETDqmAction the_action) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setActionReference(the_action == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.dqm.entity.KETDqmAction) the_action));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setActionReference(wt.fc.ObjectReference the_actionReference) throws wt.util.WTPropertyVetoException {
      actionReferenceValidate(the_actionReference);
      actionReference = (wt.fc.ObjectReference) the_actionReference;
   }
   void actionReferenceValidate(wt.fc.ObjectReference the_actionReference) throws wt.util.WTPropertyVetoException {
      if (the_actionReference != null && the_actionReference.getReferencedClass() != null &&
            !ext.ket.dqm.entity.KETDqmAction.class.isAssignableFrom(the_actionReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "actionReference", this.actionReference, actionReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String RAISE = "raise";
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public static final java.lang.String RAISE_REFERENCE = "raiseReference";
   wt.fc.ObjectReference raiseReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public ext.ket.dqm.entity.KETDqmRaise getRaise() {
      return (raiseReference != null) ? (ext.ket.dqm.entity.KETDqmRaise) raiseReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public wt.fc.ObjectReference getRaiseReference() {
      return raiseReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setRaise(ext.ket.dqm.entity.KETDqmRaise the_raise) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setRaiseReference(the_raise == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.dqm.entity.KETDqmRaise) the_raise));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmHeader
    */
   public void setRaiseReference(wt.fc.ObjectReference the_raiseReference) throws wt.util.WTPropertyVetoException {
      raiseReferenceValidate(the_raiseReference);
      raiseReference = (wt.fc.ObjectReference) the_raiseReference;
   }
   void raiseReferenceValidate(wt.fc.ObjectReference the_raiseReference) throws wt.util.WTPropertyVetoException {
      if (the_raiseReference != null && the_raiseReference.getReferencedClass() != null &&
            !ext.ket.dqm.entity.KETDqmRaise.class.isAssignableFrom(the_raiseReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "raiseReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "raiseReference", this.raiseReference, raiseReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 9210186714777068761L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( actionReference );
      output.writeObject( closeReference );
      output.writeObject( dqmStateCode );
      output.writeObject( dqmStateName );
      output.writeObject( problem );
      output.writeObject( problemNo );
      output.writeObject( raiseReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dqm.entity.KETDqmHeader) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "actionReference", actionReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "closeReference", closeReference, wt.fc.ObjectReference.class, true );
      output.setString( "dqmStateCode", dqmStateCode );
      output.setString( "dqmStateName", dqmStateName );
      output.setString( "problem", problem );
      output.setString( "problemNo", problemNo );
      output.writeObject( "raiseReference", raiseReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      actionReference = (wt.fc.ObjectReference) input.readObject( "actionReference", actionReference, wt.fc.ObjectReference.class, true );
      closeReference = (wt.fc.ObjectReference) input.readObject( "closeReference", closeReference, wt.fc.ObjectReference.class, true );
      dqmStateCode = input.getString( "dqmStateCode" );
      dqmStateName = input.getString( "dqmStateName" );
      problem = input.getString( "problem" );
      problemNo = input.getString( "problemNo" );
      raiseReference = (wt.fc.ObjectReference) input.readObject( "raiseReference", raiseReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion9210186714777068761L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      actionReference = (wt.fc.ObjectReference) input.readObject();
      closeReference = (wt.fc.ObjectReference) input.readObject();
      dqmStateCode = (java.lang.String) input.readObject();
      dqmStateName = (java.lang.String) input.readObject();
      problem = (java.lang.String) input.readObject();
      problemNo = (java.lang.String) input.readObject();
      raiseReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDqmHeader thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion9210186714777068761L( input, readSerialVersionUID, superDone );
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
