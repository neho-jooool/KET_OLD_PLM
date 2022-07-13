package ext.ket.issue.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETIssueMeetingHeader implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.issue.entity.entityResource";
   static final java.lang.String CLASSNAME = KETIssueMeetingHeader.class.getName();

   /**
    * 차수
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public static final java.lang.String CHASU = "chasu";
   java.lang.Integer chasu;
   /**
    * 차수
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public java.lang.Integer getChasu() {
      return chasu;
   }
   /**
    * 차수
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public void setChasu(java.lang.Integer chasu) throws wt.util.WTPropertyVetoException {
      chasuValidate(chasu);
      this.chasu = chasu;
   }
   void chasuValidate(java.lang.Integer chasu) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 년도
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public static final java.lang.String YEAR = "year";
   static int YEAR_UPPER_LIMIT = -1;
   java.lang.String year;
   /**
    * 년도
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public java.lang.String getYear() {
      return year;
   }
   /**
    * 년도
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public void setYear(java.lang.String year) throws wt.util.WTPropertyVetoException {
      yearValidate(year);
      this.year = year;
   }
   void yearValidate(java.lang.String year) throws wt.util.WTPropertyVetoException {
      if (YEAR_UPPER_LIMIT < 1) {
         try { YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR_UPPER_LIMIT = 200; }
      }
      if (year != null && !wt.fc.PersistenceHelper.checkStoredLength(year.toString(), YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year"), java.lang.String.valueOf(java.lang.Math.min(YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year", this.year, year));
   }

   /**
    * 월
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public static final java.lang.String MONTH = "month";
   static int MONTH_UPPER_LIMIT = -1;
   java.lang.String month;
   /**
    * 월
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public java.lang.String getMonth() {
      return month;
   }
   /**
    * 월
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public void setMonth(java.lang.String month) throws wt.util.WTPropertyVetoException {
      monthValidate(month);
      this.month = month;
   }
   void monthValidate(java.lang.String month) throws wt.util.WTPropertyVetoException {
      if (MONTH_UPPER_LIMIT < 1) {
         try { MONTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("month").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MONTH_UPPER_LIMIT = 200; }
      }
      if (month != null && !wt.fc.PersistenceHelper.checkStoredLength(month.toString(), MONTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "month"), java.lang.String.valueOf(java.lang.Math.min(MONTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "month", this.month, month));
   }

   /**
    * 마감여부
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public static final java.lang.String IS_CLOSE = "isClose";
   boolean isClose = false;
   /**
    * 마감여부
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public boolean isIsClose() {
      return isClose;
   }
   /**
    * 마감여부
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public void setIsClose(boolean isClose) throws wt.util.WTPropertyVetoException {
      isCloseValidate(isClose);
      this.isClose = isClose;
   }
   void isCloseValidate(boolean isClose) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 회의시작일
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public static final java.lang.String START_DATE = "startDate";
   java.sql.Timestamp startDate;
   /**
    * 회의시작일
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public java.sql.Timestamp getStartDate() {
      return startDate;
   }
   /**
    * 회의시작일
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public void setStartDate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
      startDateValidate(startDate);
      this.startDate = startDate;
   }
   void startDateValidate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 회의종료일
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public static final java.lang.String END_DATE = "endDate";
   java.sql.Timestamp endDate;
   /**
    * 회의종료일
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public java.sql.Timestamp getEndDate() {
      return endDate;
   }
   /**
    * 회의종료일
    *
    * @see ext.ket.issue.entity.KETIssueMeetingHeader
    */
   public void setEndDate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
   }

   wt.org.WTPrincipalReference owner;
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public wt.org.WTPrincipalReference getOwner() {
      return owner;
   }
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      ownerValidate(owner);
      this.owner = owner;
   }
   void ownerValidate(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      if (owner == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "owner") },
               new java.beans.PropertyChangeEvent(this, "owner", this.owner, owner));
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

   public static final long EXTERNALIZATION_VERSION_UID = -6340750047633875402L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( chasu );
      output.writeObject( endDate );
      output.writeBoolean( isClose );
      output.writeObject( month );
      output.writeObject( owner );
      output.writeObject( startDate );
      output.writeObject( thePersistInfo );
      output.writeObject( year );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.issue.entity.KETIssueMeetingHeader) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setIntObject( "chasu", chasu );
      output.setTimestamp( "endDate", endDate );
      output.setBoolean( "isClose", isClose );
      output.setString( "month", month );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setTimestamp( "startDate", startDate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "year", year );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      chasu = input.getIntObject( "chasu" );
      endDate = input.getTimestamp( "endDate" );
      isClose = input.getBoolean( "isClose" );
      month = input.getString( "month" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      startDate = input.getTimestamp( "startDate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      year = input.getString( "year" );
   }

   boolean readVersion_6340750047633875402L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      chasu = (java.lang.Integer) input.readObject();
      endDate = (java.sql.Timestamp) input.readObject();
      isClose = input.readBoolean();
      month = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      startDate = (java.sql.Timestamp) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      year = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETIssueMeetingHeader thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6340750047633875402L( input, readSerialVersionUID, superDone );
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
