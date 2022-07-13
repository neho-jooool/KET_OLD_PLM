package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ExtendScheduleData extends e3ps.project.ScheduleData implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ExtendScheduleData.class.getName();

   /**
    * 실제 시작일자
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public static final java.lang.String EXEC_START_DATE = "execStartDate";
   java.sql.Timestamp execStartDate;
   /**
    * 실제 시작일자
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public java.sql.Timestamp getExecStartDate() {
      return execStartDate;
   }
   /**
    * 실제 시작일자
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public void setExecStartDate(java.sql.Timestamp execStartDate) throws wt.util.WTPropertyVetoException {
      execStartDateValidate(execStartDate);
      this.execStartDate = execStartDate;
   }
   void execStartDateValidate(java.sql.Timestamp execStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 실제 종료일자
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public static final java.lang.String EXEC_END_DATE = "execEndDate";
   java.sql.Timestamp execEndDate;
   /**
    * 실제 종료일자
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public java.sql.Timestamp getExecEndDate() {
      return execEndDate;
   }
   /**
    * 실제 종료일자
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public void setExecEndDate(java.sql.Timestamp execEndDate) throws wt.util.WTPropertyVetoException {
      execEndDateValidate(execEndDate);
      this.execEndDate = execEndDate;
   }
   void execEndDateValidate(java.sql.Timestamp execEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 실 공수
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public static final java.lang.String EXEC_WORK = "execWork";
   int execWork;
   /**
    * 실 공수
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public int getExecWork() {
      return execWork;
   }
   /**
    * 실 공수
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public void setExecWork(int execWork) throws wt.util.WTPropertyVetoException {
      execWorkValidate(execWork);
      this.execWork = execWork;
   }
   void execWorkValidate(int execWork) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 계획공수
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public static final java.lang.String PLAN_WORK = "planWork";
   int planWork;
   /**
    * 계획공수
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public int getPlanWork() {
      return planWork;
   }
   /**
    * 계획공수
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public void setPlanWork(int planWork) throws wt.util.WTPropertyVetoException {
      planWorkValidate(planWork);
      this.planWork = planWork;
   }
   void planWorkValidate(int planWork) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 계획공수(hr)
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public static final java.lang.String PLAN_MAN_HOUR = "planManHour";
   float planManHour;
   /**
    * 계획공수(hr)
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public float getPlanManHour() {
      return planManHour;
   }
   /**
    * 계획공수(hr)
    *
    * @see e3ps.project.ExtendScheduleData
    */
   public void setPlanManHour(float planManHour) throws wt.util.WTPropertyVetoException {
      planManHourValidate(planManHour);
      this.planManHour = planManHour;
   }
   void planManHourValidate(float planManHour) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -6769692424304471813L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( execEndDate );
      output.writeObject( execStartDate );
      output.writeInt( execWork );
      output.writeFloat( planManHour );
      output.writeInt( planWork );
   }

   protected void super_writeExternal_ExtendScheduleData(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ExtendScheduleData) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ExtendScheduleData(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setTimestamp( "execEndDate", execEndDate );
      output.setTimestamp( "execStartDate", execStartDate );
      output.setInt( "execWork", execWork );
      output.setFloat( "planManHour", planManHour );
      output.setInt( "planWork", planWork );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      execEndDate = input.getTimestamp( "execEndDate" );
      execStartDate = input.getTimestamp( "execStartDate" );
      execWork = input.getInt( "execWork" );
      planManHour = input.getFloat( "planManHour" );
      planWork = input.getInt( "planWork" );
   }

   boolean readVersion_6769692424304471813L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      execEndDate = (java.sql.Timestamp) input.readObject();
      execStartDate = (java.sql.Timestamp) input.readObject();
      execWork = input.readInt();
      planManHour = input.readFloat();
      planWork = input.readInt();
      return true;
   }

   protected boolean readVersion( ExtendScheduleData thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6769692424304471813L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ExtendScheduleData( _ExtendScheduleData thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
