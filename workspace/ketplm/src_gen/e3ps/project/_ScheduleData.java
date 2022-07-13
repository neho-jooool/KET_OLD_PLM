package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ScheduleData implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ScheduleData.class.getName();

   /**
    * 총 기간
    *
    * @see e3ps.project.ScheduleData
    */
   public static final java.lang.String DURATION = "duration";
   int duration;
   /**
    * 총 기간
    *
    * @see e3ps.project.ScheduleData
    */
   public int getDuration() {
      return duration;
   }
   /**
    * 총 기간
    *
    * @see e3ps.project.ScheduleData
    */
   public void setDuration(int duration) throws wt.util.WTPropertyVetoException {
      durationValidate(duration);
      this.duration = duration;
   }
   void durationValidate(int duration) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 일정 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ScheduleData
    */
   public static final java.lang.String SCHEDULE_HISTORY = "scheduleHistory";
   int scheduleHistory;
   /**
    * 일정 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ScheduleData
    */
   public int getScheduleHistory() {
      return scheduleHistory;
   }
   /**
    * 일정 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ScheduleData
    */
   public void setScheduleHistory(int scheduleHistory) throws wt.util.WTPropertyVetoException {
      scheduleHistoryValidate(scheduleHistory);
      this.scheduleHistory = scheduleHistory;
   }
   void scheduleHistoryValidate(int scheduleHistory) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ScheduleData
    */
   public static final java.lang.String PLAN_START_DATE = "planStartDate";
   java.sql.Timestamp planStartDate;
   /**
    * @see e3ps.project.ScheduleData
    */
   public java.sql.Timestamp getPlanStartDate() {
      return planStartDate;
   }
   /**
    * @see e3ps.project.ScheduleData
    */
   public void setPlanStartDate(java.sql.Timestamp planStartDate) throws wt.util.WTPropertyVetoException {
      planStartDateValidate(planStartDate);
      this.planStartDate = planStartDate;
   }
   void planStartDateValidate(java.sql.Timestamp planStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 계획 종료일자
    *
    * @see e3ps.project.ScheduleData
    */
   public static final java.lang.String PLAN_END_DATE = "planEndDate";
   java.sql.Timestamp planEndDate;
   /**
    * 계획 종료일자
    *
    * @see e3ps.project.ScheduleData
    */
   public java.sql.Timestamp getPlanEndDate() {
      return planEndDate;
   }
   /**
    * 계획 종료일자
    *
    * @see e3ps.project.ScheduleData
    */
   public void setPlanEndDate(java.sql.Timestamp planEndDate) throws wt.util.WTPropertyVetoException {
      planEndDateValidate(planEndDate);
      this.planEndDate = planEndDate;
   }
   void planEndDateValidate(java.sql.Timestamp planEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 표준공수
    *
    * @see e3ps.project.ScheduleData
    */
   public static final java.lang.String STD_WORK = "stdWork";
   int stdWork;
   /**
    * 표준공수
    *
    * @see e3ps.project.ScheduleData
    */
   public int getStdWork() {
      return stdWork;
   }
   /**
    * 표준공수
    *
    * @see e3ps.project.ScheduleData
    */
   public void setStdWork(int stdWork) throws wt.util.WTPropertyVetoException {
      stdWorkValidate(stdWork);
      this.stdWork = stdWork;
   }
   void stdWorkValidate(int stdWork) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 7013024526932978174L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeInt( duration );
      output.writeObject( planEndDate );
      output.writeObject( planStartDate );
      output.writeInt( scheduleHistory );
      output.writeInt( stdWork );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ScheduleData) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setInt( "duration", duration );
      output.setTimestamp( "planEndDate", planEndDate );
      output.setTimestamp( "planStartDate", planStartDate );
      output.setInt( "scheduleHistory", scheduleHistory );
      output.setInt( "stdWork", stdWork );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      duration = input.getInt( "duration" );
      planEndDate = input.getTimestamp( "planEndDate" );
      planStartDate = input.getTimestamp( "planStartDate" );
      scheduleHistory = input.getInt( "scheduleHistory" );
      stdWork = input.getInt( "stdWork" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion7013024526932978174L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      duration = input.readInt();
      planEndDate = (java.sql.Timestamp) input.readObject();
      planStartDate = (java.sql.Timestamp) input.readObject();
      scheduleHistory = input.readInt();
      stdWork = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( ScheduleData thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7013024526932978174L( input, readSerialVersionUID, superDone );
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
