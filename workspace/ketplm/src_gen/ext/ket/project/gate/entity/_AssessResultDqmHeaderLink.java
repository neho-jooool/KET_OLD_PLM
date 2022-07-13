package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AssessResultDqmHeaderLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = AssessResultDqmHeaderLink.class.getName();

   /**
    * Check
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public static final java.lang.String DQM_CHECK = "dqmCheck";
   static int DQM_CHECK_UPPER_LIMIT = -1;
   java.lang.String dqmCheck;
   /**
    * Check
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public java.lang.String getDqmCheck() {
      return dqmCheck;
   }
   /**
    * Check
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public void setDqmCheck(java.lang.String dqmCheck) throws wt.util.WTPropertyVetoException {
      dqmCheckValidate(dqmCheck);
      this.dqmCheck = dqmCheck;
   }
   void dqmCheckValidate(java.lang.String dqmCheck) throws wt.util.WTPropertyVetoException {
      if (DQM_CHECK_UPPER_LIMIT < 1) {
         try { DQM_CHECK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dqmCheck").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DQM_CHECK_UPPER_LIMIT = 200; }
      }
      if (dqmCheck != null && !wt.fc.PersistenceHelper.checkStoredLength(dqmCheck.toString(), DQM_CHECK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dqmCheck"), java.lang.String.valueOf(java.lang.Math.min(DQM_CHECK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dqmCheck", this.dqmCheck, dqmCheck));
   }

   /**
    * 완료일
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public static final java.lang.String DQM_COMP_DATE = "dqmCompDate";
   static int DQM_COMP_DATE_UPPER_LIMIT = -1;
   java.lang.String dqmCompDate;
   /**
    * 완료일
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public java.lang.String getDqmCompDate() {
      return dqmCompDate;
   }
   /**
    * 완료일
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public void setDqmCompDate(java.lang.String dqmCompDate) throws wt.util.WTPropertyVetoException {
      dqmCompDateValidate(dqmCompDate);
      this.dqmCompDate = dqmCompDate;
   }
   void dqmCompDateValidate(java.lang.String dqmCompDate) throws wt.util.WTPropertyVetoException {
      if (DQM_COMP_DATE_UPPER_LIMIT < 1) {
         try { DQM_COMP_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dqmCompDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DQM_COMP_DATE_UPPER_LIMIT = 200; }
      }
      if (dqmCompDate != null && !wt.fc.PersistenceHelper.checkStoredLength(dqmCompDate.toString(), DQM_COMP_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dqmCompDate"), java.lang.String.valueOf(java.lang.Math.min(DQM_COMP_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dqmCompDate", this.dqmCompDate, dqmCompDate));
   }

   /**
    * 상태
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public static final java.lang.String DQM_STATE = "dqmState";
   static int DQM_STATE_UPPER_LIMIT = -1;
   java.lang.String dqmState;
   /**
    * 상태
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public java.lang.String getDqmState() {
      return dqmState;
   }
   /**
    * 상태
    *
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public void setDqmState(java.lang.String dqmState) throws wt.util.WTPropertyVetoException {
      dqmStateValidate(dqmState);
      this.dqmState = dqmState;
   }
   void dqmStateValidate(java.lang.String dqmState) throws wt.util.WTPropertyVetoException {
      if (DQM_STATE_UPPER_LIMIT < 1) {
         try { DQM_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dqmState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DQM_STATE_UPPER_LIMIT = 200; }
      }
      if (dqmState != null && !wt.fc.PersistenceHelper.checkStoredLength(dqmState.toString(), DQM_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dqmState"), java.lang.String.valueOf(java.lang.Math.min(DQM_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dqmState", this.dqmState, dqmState));
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public static final java.lang.String REV = "rev";
   int rev;
   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public int getRev() {
      return rev;
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public void setRev(int rev) throws wt.util.WTPropertyVetoException {
      revValidate(rev);
      this.rev = rev;
   }
   void revValidate(int rev) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public static final java.lang.String DQM_ROLE = "dqm";
   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public ext.ket.dqm.entity.KETDqmHeader getDqm() {
      return (ext.ket.dqm.entity.KETDqmHeader) getRoleAObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public void setDqm(ext.ket.dqm.entity.KETDqmHeader the_dqm) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_dqm);
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public static final java.lang.String ASSESS_ROLE = "assess";
   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public ext.ket.project.gate.entity.GateAssessResult getAssess() {
      return (ext.ket.project.gate.entity.GateAssessResult) getRoleBObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultDqmHeaderLink
    */
   public void setAssess(ext.ket.project.gate.entity.GateAssessResult the_assess) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_assess);
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

   public static final long EXTERNALIZATION_VERSION_UID = 947321825706312080L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( dqmCheck );
      output.writeObject( dqmCompDate );
      output.writeObject( dqmState );
      output.writeInt( rev );
   }

   protected void super_writeExternal_AssessResultDqmHeaderLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.AssessResultDqmHeaderLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_AssessResultDqmHeaderLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "dqmCheck", dqmCheck );
      output.setString( "dqmCompDate", dqmCompDate );
      output.setString( "dqmState", dqmState );
      output.setInt( "rev", rev );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      dqmCheck = input.getString( "dqmCheck" );
      dqmCompDate = input.getString( "dqmCompDate" );
      dqmState = input.getString( "dqmState" );
      rev = input.getInt( "rev" );
   }

   boolean readVersion947321825706312080L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      dqmCheck = (java.lang.String) input.readObject();
      dqmCompDate = (java.lang.String) input.readObject();
      dqmState = (java.lang.String) input.readObject();
      rev = input.readInt();
      return true;
   }

   protected boolean readVersion( AssessResultDqmHeaderLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion947321825706312080L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_AssessResultDqmHeaderLink( _AssessResultDqmHeaderLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
