package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMeetingCall implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMeetingCall.class.getName();

   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_MEETING_NAME = "callMeetingName";
   static int CALL_MEETING_NAME_UPPER_LIMIT = -1;
   java.lang.String callMeetingName;
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.lang.String getCallMeetingName() {
      return callMeetingName;
   }
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallMeetingName(java.lang.String callMeetingName) throws wt.util.WTPropertyVetoException {
      callMeetingNameValidate(callMeetingName);
      this.callMeetingName = callMeetingName;
   }
   void callMeetingNameValidate(java.lang.String callMeetingName) throws wt.util.WTPropertyVetoException {
      if (CALL_MEETING_NAME_UPPER_LIMIT < 1) {
         try { CALL_MEETING_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("callMeetingName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALL_MEETING_NAME_UPPER_LIMIT = 200; }
      }
      if (callMeetingName != null && !wt.fc.PersistenceHelper.checkStoredLength(callMeetingName.toString(), CALL_MEETING_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callMeetingName"), java.lang.String.valueOf(java.lang.Math.min(CALL_MEETING_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "callMeetingName", this.callMeetingName, callMeetingName));
   }

   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_MEETING_DATE = "callMeetingDate";
   java.sql.Timestamp callMeetingDate;
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.sql.Timestamp getCallMeetingDate() {
      return callMeetingDate;
   }
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallMeetingDate(java.sql.Timestamp callMeetingDate) throws wt.util.WTPropertyVetoException {
      callMeetingDateValidate(callMeetingDate);
      this.callMeetingDate = callMeetingDate;
   }
   void callMeetingDateValidate(java.sql.Timestamp callMeetingDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ???? ???μ? ???
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_DEPARTMENT_CODE = "callDepartmentCode";
   static int CALL_DEPARTMENT_CODE_UPPER_LIMIT = -1;
   java.lang.String callDepartmentCode;
   /**
    * ???? ???μ? ???
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.lang.String getCallDepartmentCode() {
      return callDepartmentCode;
   }
   /**
    * ???? ???μ? ???
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallDepartmentCode(java.lang.String callDepartmentCode) throws wt.util.WTPropertyVetoException {
      callDepartmentCodeValidate(callDepartmentCode);
      this.callDepartmentCode = callDepartmentCode;
   }
   void callDepartmentCodeValidate(java.lang.String callDepartmentCode) throws wt.util.WTPropertyVetoException {
      if (CALL_DEPARTMENT_CODE_UPPER_LIMIT < 1) {
         try { CALL_DEPARTMENT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("callDepartmentCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALL_DEPARTMENT_CODE_UPPER_LIMIT = 200; }
      }
      if (callDepartmentCode != null && !wt.fc.PersistenceHelper.checkStoredLength(callDepartmentCode.toString(), CALL_DEPARTMENT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callDepartmentCode"), java.lang.String.valueOf(java.lang.Math.min(CALL_DEPARTMENT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "callDepartmentCode", this.callDepartmentCode, callDepartmentCode));
   }

   /**
    * ???? ????????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_ATTENDANCE_NAME = "callAttendanceName";
   static int CALL_ATTENDANCE_NAME_UPPER_LIMIT = -1;
   java.lang.String callAttendanceName;
   /**
    * ???? ????????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.lang.String getCallAttendanceName() {
      return callAttendanceName;
   }
   /**
    * ???? ????????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallAttendanceName(java.lang.String callAttendanceName) throws wt.util.WTPropertyVetoException {
      callAttendanceNameValidate(callAttendanceName);
      this.callAttendanceName = callAttendanceName;
   }
   void callAttendanceNameValidate(java.lang.String callAttendanceName) throws wt.util.WTPropertyVetoException {
      if (CALL_ATTENDANCE_NAME_UPPER_LIMIT < 1) {
         try { CALL_ATTENDANCE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("callAttendanceName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALL_ATTENDANCE_NAME_UPPER_LIMIT = 200; }
      }
      if (callAttendanceName != null && !wt.fc.PersistenceHelper.checkStoredLength(callAttendanceName.toString(), CALL_ATTENDANCE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callAttendanceName"), java.lang.String.valueOf(java.lang.Math.min(CALL_ATTENDANCE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "callAttendanceName", this.callAttendanceName, callAttendanceName));
   }

   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_SEND_DATE = "callSendDate";
   java.sql.Timestamp callSendDate;
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.sql.Timestamp getCallSendDate() {
      return callSendDate;
   }
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallSendDate(java.sql.Timestamp callSendDate) throws wt.util.WTPropertyVetoException {
      callSendDateValidate(callSendDate);
      this.callSendDate = callSendDate;
   }
   void callSendDateValidate(java.sql.Timestamp callSendDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_NUMBER = "callNumber";
   static int CALL_NUMBER_UPPER_LIMIT = -1;
   java.lang.String callNumber;
   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.lang.String getCallNumber() {
      return callNumber;
   }
   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallNumber(java.lang.String callNumber) throws wt.util.WTPropertyVetoException {
      callNumberValidate(callNumber);
      this.callNumber = callNumber;
   }
   void callNumberValidate(java.lang.String callNumber) throws wt.util.WTPropertyVetoException {
      if (CALL_NUMBER_UPPER_LIMIT < 1) {
         try { CALL_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("callNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALL_NUMBER_UPPER_LIMIT = 200; }
      }
      if (callNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(callNumber.toString(), CALL_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callNumber"), java.lang.String.valueOf(java.lang.Math.min(CALL_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "callNumber", this.callNumber, callNumber));
   }

   /**
    * ???ð?
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_MEETING_TIME = "callMeetingTime";
   static int CALL_MEETING_TIME_UPPER_LIMIT = -1;
   java.lang.String callMeetingTime;
   /**
    * ???ð?
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.lang.String getCallMeetingTime() {
      return callMeetingTime;
   }
   /**
    * ???ð?
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallMeetingTime(java.lang.String callMeetingTime) throws wt.util.WTPropertyVetoException {
      callMeetingTimeValidate(callMeetingTime);
      this.callMeetingTime = callMeetingTime;
   }
   void callMeetingTimeValidate(java.lang.String callMeetingTime) throws wt.util.WTPropertyVetoException {
      if (CALL_MEETING_TIME_UPPER_LIMIT < 1) {
         try { CALL_MEETING_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("callMeetingTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALL_MEETING_TIME_UPPER_LIMIT = 200; }
      }
      if (callMeetingTime != null && !wt.fc.PersistenceHelper.checkStoredLength(callMeetingTime.toString(), CALL_MEETING_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callMeetingTime"), java.lang.String.valueOf(java.lang.Math.min(CALL_MEETING_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "callMeetingTime", this.callMeetingTime, callMeetingTime));
   }

   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_MEETING_LOCATION = "callMeetingLocation";
   static int CALL_MEETING_LOCATION_UPPER_LIMIT = -1;
   java.lang.String callMeetingLocation;
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public java.lang.String getCallMeetingLocation() {
      return callMeetingLocation;
   }
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallMeetingLocation(java.lang.String callMeetingLocation) throws wt.util.WTPropertyVetoException {
      callMeetingLocationValidate(callMeetingLocation);
      this.callMeetingLocation = callMeetingLocation;
   }
   void callMeetingLocationValidate(java.lang.String callMeetingLocation) throws wt.util.WTPropertyVetoException {
      if (CALL_MEETING_LOCATION_UPPER_LIMIT < 1) {
         try { CALL_MEETING_LOCATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("callMeetingLocation").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALL_MEETING_LOCATION_UPPER_LIMIT = 200; }
      }
      if (callMeetingLocation != null && !wt.fc.PersistenceHelper.checkStoredLength(callMeetingLocation.toString(), CALL_MEETING_LOCATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callMeetingLocation"), java.lang.String.valueOf(java.lang.Math.min(CALL_MEETING_LOCATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "callMeetingLocation", this.callMeetingLocation, callMeetingLocation));
   }

   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_DEPARTMENT = "callDepartment";
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_DEPARTMENT_REFERENCE = "callDepartmentReference";
   wt.fc.ObjectReference callDepartmentReference;
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public e3ps.groupware.company.Department getCallDepartment() {
      return (callDepartmentReference != null) ? (e3ps.groupware.company.Department) callDepartmentReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public wt.fc.ObjectReference getCallDepartmentReference() {
      return callDepartmentReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallDepartment(e3ps.groupware.company.Department the_callDepartment) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCallDepartmentReference(the_callDepartment == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_callDepartment));
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallDepartmentReference(wt.fc.ObjectReference the_callDepartmentReference) throws wt.util.WTPropertyVetoException {
      callDepartmentReferenceValidate(the_callDepartmentReference);
      callDepartmentReference = (wt.fc.ObjectReference) the_callDepartmentReference;
   }
   void callDepartmentReferenceValidate(wt.fc.ObjectReference the_callDepartmentReference) throws wt.util.WTPropertyVetoException {
      if (the_callDepartmentReference != null && the_callDepartmentReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_callDepartmentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callDepartmentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "callDepartmentReference", this.callDepartmentReference, callDepartmentReference));
   }

   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_ATTENDANCE = "callAttendance";
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public static final java.lang.String CALL_ATTENDANCE_REFERENCE = "callAttendanceReference";
   wt.fc.ObjectReference callAttendanceReference;
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public wt.org.WTUser getCallAttendance() {
      return (callAttendanceReference != null) ? (wt.org.WTUser) callAttendanceReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public wt.fc.ObjectReference getCallAttendanceReference() {
      return callAttendanceReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallAttendance(wt.org.WTUser the_callAttendance) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCallAttendanceReference(the_callAttendance == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_callAttendance));
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingCall
    */
   public void setCallAttendanceReference(wt.fc.ObjectReference the_callAttendanceReference) throws wt.util.WTPropertyVetoException {
      callAttendanceReferenceValidate(the_callAttendanceReference);
      callAttendanceReference = (wt.fc.ObjectReference) the_callAttendanceReference;
   }
   void callAttendanceReferenceValidate(wt.fc.ObjectReference the_callAttendanceReference) throws wt.util.WTPropertyVetoException {
      if (the_callAttendanceReference != null && the_callAttendanceReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_callAttendanceReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "callAttendanceReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "callAttendanceReference", this.callAttendanceReference, callAttendanceReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -4708117955163265718L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( callAttendanceName );
      output.writeObject( callAttendanceReference );
      output.writeObject( callDepartmentCode );
      output.writeObject( callDepartmentReference );
      output.writeObject( callMeetingDate );
      output.writeObject( callMeetingLocation );
      output.writeObject( callMeetingName );
      output.writeObject( callMeetingTime );
      output.writeObject( callNumber );
      output.writeObject( callSendDate );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMeetingCall) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "callAttendanceName", callAttendanceName );
      output.writeObject( "callAttendanceReference", callAttendanceReference, wt.fc.ObjectReference.class, true );
      output.setString( "callDepartmentCode", callDepartmentCode );
      output.writeObject( "callDepartmentReference", callDepartmentReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "callMeetingDate", callMeetingDate );
      output.setString( "callMeetingLocation", callMeetingLocation );
      output.setString( "callMeetingName", callMeetingName );
      output.setString( "callMeetingTime", callMeetingTime );
      output.setString( "callNumber", callNumber );
      output.setTimestamp( "callSendDate", callSendDate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      callAttendanceName = input.getString( "callAttendanceName" );
      callAttendanceReference = (wt.fc.ObjectReference) input.readObject( "callAttendanceReference", callAttendanceReference, wt.fc.ObjectReference.class, true );
      callDepartmentCode = input.getString( "callDepartmentCode" );
      callDepartmentReference = (wt.fc.ObjectReference) input.readObject( "callDepartmentReference", callDepartmentReference, wt.fc.ObjectReference.class, true );
      callMeetingDate = input.getTimestamp( "callMeetingDate" );
      callMeetingLocation = input.getString( "callMeetingLocation" );
      callMeetingName = input.getString( "callMeetingName" );
      callMeetingTime = input.getString( "callMeetingTime" );
      callNumber = input.getString( "callNumber" );
      callSendDate = input.getTimestamp( "callSendDate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_4708117955163265718L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      callAttendanceName = (java.lang.String) input.readObject();
      callAttendanceReference = (wt.fc.ObjectReference) input.readObject();
      callDepartmentCode = (java.lang.String) input.readObject();
      callDepartmentReference = (wt.fc.ObjectReference) input.readObject();
      callMeetingDate = (java.sql.Timestamp) input.readObject();
      callMeetingLocation = (java.lang.String) input.readObject();
      callMeetingName = (java.lang.String) input.readObject();
      callMeetingTime = (java.lang.String) input.readObject();
      callNumber = (java.lang.String) input.readObject();
      callSendDate = (java.sql.Timestamp) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETMeetingCall thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4708117955163265718L( input, readSerialVersionUID, superDone );
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
