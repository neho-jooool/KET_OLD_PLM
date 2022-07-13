package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMeetingMinutes extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMeetingMinutes.class.getName();

   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String MEETING_NAME = "meetingName";
   static int MEETING_NAME_UPPER_LIMIT = -1;
   java.lang.String meetingName;
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.String getMeetingName() {
      return meetingName;
   }
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setMeetingName(java.lang.String meetingName) throws wt.util.WTPropertyVetoException {
      meetingNameValidate(meetingName);
      this.meetingName = meetingName;
   }
   void meetingNameValidate(java.lang.String meetingName) throws wt.util.WTPropertyVetoException {
      if (MEETING_NAME_UPPER_LIMIT < 1) {
         try { MEETING_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("meetingName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEETING_NAME_UPPER_LIMIT = 200; }
      }
      if (meetingName != null && !wt.fc.PersistenceHelper.checkStoredLength(meetingName.toString(), MEETING_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingName"), java.lang.String.valueOf(java.lang.Math.min(MEETING_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "meetingName", this.meetingName, meetingName));
   }

   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String MEETING_DATE = "meetingDate";
   java.sql.Timestamp meetingDate;
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.sql.Timestamp getMeetingDate() {
      return meetingDate;
   }
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setMeetingDate(java.sql.Timestamp meetingDate) throws wt.util.WTPropertyVetoException {
      meetingDateValidate(meetingDate);
      this.meetingDate = meetingDate;
   }
   void meetingDateValidate(java.sql.Timestamp meetingDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ??? ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String ATTENDANCE = "attendance";
   static int ATTENDANCE_UPPER_LIMIT = -1;
   java.lang.String attendance;
   /**
    * ??? ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.String getAttendance() {
      return attendance;
   }
   /**
    * ??? ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setAttendance(java.lang.String attendance) throws wt.util.WTPropertyVetoException {
      attendanceValidate(attendance);
      this.attendance = attendance;
   }
   void attendanceValidate(java.lang.String attendance) throws wt.util.WTPropertyVetoException {
      if (ATTENDANCE_UPPER_LIMIT < 1) {
         try { ATTENDANCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attendance").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTENDANCE_UPPER_LIMIT = 200; }
      }
      if (attendance != null && !wt.fc.PersistenceHelper.checkStoredLength(attendance.toString(), ATTENDANCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attendance"), java.lang.String.valueOf(java.lang.Math.min(ATTENDANCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attendance", this.attendance, attendance));
   }

   /**
    * ???μ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String SUBJECT_CODE = "subjectCode";
   static int SUBJECT_CODE_UPPER_LIMIT = -1;
   java.lang.String subjectCode;
   /**
    * ???μ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.String getSubjectCode() {
      return subjectCode;
   }
   /**
    * ???μ????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setSubjectCode(java.lang.String subjectCode) throws wt.util.WTPropertyVetoException {
      subjectCodeValidate(subjectCode);
      this.subjectCode = subjectCode;
   }
   void subjectCodeValidate(java.lang.String subjectCode) throws wt.util.WTPropertyVetoException {
      if (SUBJECT_CODE_UPPER_LIMIT < 1) {
         try { SUBJECT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subjectCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUBJECT_CODE_UPPER_LIMIT = 200; }
      }
      if (subjectCode != null && !wt.fc.PersistenceHelper.checkStoredLength(subjectCode.toString(), SUBJECT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subjectCode"), java.lang.String.valueOf(java.lang.Math.min(SUBJECT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subjectCode", this.subjectCode, subjectCode));
   }

   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String CHARGE_NAME = "chargeName";
   static int CHARGE_NAME_UPPER_LIMIT = -1;
   java.lang.String chargeName;
   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.String getChargeName() {
      return chargeName;
   }
   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setChargeName(java.lang.String chargeName) throws wt.util.WTPropertyVetoException {
      chargeNameValidate(chargeName);
      this.chargeName = chargeName;
   }
   void chargeNameValidate(java.lang.String chargeName) throws wt.util.WTPropertyVetoException {
      if (CHARGE_NAME_UPPER_LIMIT < 1) {
         try { CHARGE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("chargeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHARGE_NAME_UPPER_LIMIT = 200; }
      }
      if (chargeName != null && !wt.fc.PersistenceHelper.checkStoredLength(chargeName.toString(), CHARGE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chargeName"), java.lang.String.valueOf(java.lang.Math.min(CHARGE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "chargeName", this.chargeName, chargeName));
   }

   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ???ð?
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String MEETING_TIME = "meetingTime";
   static int MEETING_TIME_UPPER_LIMIT = -1;
   java.lang.String meetingTime;
   /**
    * ???ð?
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.String getMeetingTime() {
      return meetingTime;
   }
   /**
    * ???ð?
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setMeetingTime(java.lang.String meetingTime) throws wt.util.WTPropertyVetoException {
      meetingTimeValidate(meetingTime);
      this.meetingTime = meetingTime;
   }
   void meetingTimeValidate(java.lang.String meetingTime) throws wt.util.WTPropertyVetoException {
      if (MEETING_TIME_UPPER_LIMIT < 1) {
         try { MEETING_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("meetingTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEETING_TIME_UPPER_LIMIT = 200; }
      }
      if (meetingTime != null && !wt.fc.PersistenceHelper.checkStoredLength(meetingTime.toString(), MEETING_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingTime"), java.lang.String.valueOf(java.lang.Math.min(MEETING_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "meetingTime", this.meetingTime, meetingTime));
   }

   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String MEETING_LOCATION = "meetingLocation";
   static int MEETING_LOCATION_UPPER_LIMIT = -1;
   java.lang.String meetingLocation;
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public java.lang.String getMeetingLocation() {
      return meetingLocation;
   }
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setMeetingLocation(java.lang.String meetingLocation) throws wt.util.WTPropertyVetoException {
      meetingLocationValidate(meetingLocation);
      this.meetingLocation = meetingLocation;
   }
   void meetingLocationValidate(java.lang.String meetingLocation) throws wt.util.WTPropertyVetoException {
      if (MEETING_LOCATION_UPPER_LIMIT < 1) {
         try { MEETING_LOCATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("meetingLocation").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEETING_LOCATION_UPPER_LIMIT = 200; }
      }
      if (meetingLocation != null && !wt.fc.PersistenceHelper.checkStoredLength(meetingLocation.toString(), MEETING_LOCATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingLocation"), java.lang.String.valueOf(java.lang.Math.min(MEETING_LOCATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "meetingLocation", this.meetingLocation, meetingLocation));
   }

   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String CHARGE = "charge";
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String CHARGE_REFERENCE = "chargeReference";
   wt.fc.ObjectReference chargeReference;
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public wt.org.WTUser getCharge() {
      return (chargeReference != null) ? (wt.org.WTUser) chargeReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public wt.fc.ObjectReference getChargeReference() {
      return chargeReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setCharge(wt.org.WTUser the_charge) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setChargeReference(the_charge == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_charge));
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setChargeReference(wt.fc.ObjectReference the_chargeReference) throws wt.util.WTPropertyVetoException {
      chargeReferenceValidate(the_chargeReference);
      chargeReference = (wt.fc.ObjectReference) the_chargeReference;
   }
   void chargeReferenceValidate(wt.fc.ObjectReference the_chargeReference) throws wt.util.WTPropertyVetoException {
      if (the_chargeReference != null && the_chargeReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_chargeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chargeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "chargeReference", this.chargeReference, chargeReference));
   }

   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String SUBJECT = "subject";
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public static final java.lang.String SUBJECT_REFERENCE = "subjectReference";
   wt.fc.ObjectReference subjectReference;
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public e3ps.groupware.company.Department getSubject() {
      return (subjectReference != null) ? (e3ps.groupware.company.Department) subjectReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public wt.fc.ObjectReference getSubjectReference() {
      return subjectReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setSubject(e3ps.groupware.company.Department the_subject) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setSubjectReference(the_subject == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_subject));
   }
   /**
    * @see e3ps.ecm.entity.KETMeetingMinutes
    */
   public void setSubjectReference(wt.fc.ObjectReference the_subjectReference) throws wt.util.WTPropertyVetoException {
      subjectReferenceValidate(the_subjectReference);
      subjectReference = (wt.fc.ObjectReference) the_subjectReference;
   }
   void subjectReferenceValidate(wt.fc.ObjectReference the_subjectReference) throws wt.util.WTPropertyVetoException {
      if (the_subjectReference != null && the_subjectReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_subjectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subjectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "subjectReference", this.subjectReference, subjectReference));
   }

   java.util.Vector contentVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getContentVector() {
      return contentVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setContentVector(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
      contentVectorValidate(contentVector);
      this.contentVector = contentVector;
   }
   void contentVectorValidate(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
   }

   boolean hasContents;
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public boolean isHasContents() {
      return hasContents;
   }
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public void setHasContents(boolean hasContents) throws wt.util.WTPropertyVetoException {
      hasContentsValidate(hasContents);
      this.hasContents = hasContents;
   }
   void hasContentsValidate(boolean hasContents) throws wt.util.WTPropertyVetoException {
   }

   wt.content.HttpContentOperation operation;
   /**
    * @see wt.content.ContentHolder
    */
   public wt.content.HttpContentOperation getOperation() {
      return operation;
   }
   /**
    * @see wt.content.ContentHolder
    */
   public void setOperation(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
      operationValidate(operation);
      this.operation = operation;
   }
   void operationValidate(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
   }

   java.util.Vector httpVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getHttpVector() {
      return httpVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setHttpVector(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
      httpVectorValidate(httpVector);
      this.httpVector = httpVector;
   }
   void httpVectorValidate(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -8656597231916374465L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( attendance );
      output.writeObject( chargeName );
      output.writeObject( chargeReference );
      output.writeObject( meetingDate );
      output.writeObject( meetingLocation );
      output.writeObject( meetingName );
      output.writeObject( meetingTime );
      output.writeObject( subjectCode );
      output.writeObject( subjectReference );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETMeetingMinutes(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMeetingMinutes) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMeetingMinutes(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "attendance", attendance );
      output.setString( "chargeName", chargeName );
      output.writeObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "meetingDate", meetingDate );
      output.setString( "meetingLocation", meetingLocation );
      output.setString( "meetingName", meetingName );
      output.setString( "meetingTime", meetingTime );
      output.setString( "subjectCode", subjectCode );
      output.writeObject( "subjectReference", subjectReference, wt.fc.ObjectReference.class, true );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      attendance = input.getString( "attendance" );
      chargeName = input.getString( "chargeName" );
      chargeReference = (wt.fc.ObjectReference) input.readObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      meetingDate = input.getTimestamp( "meetingDate" );
      meetingLocation = input.getString( "meetingLocation" );
      meetingName = input.getString( "meetingName" );
      meetingTime = input.getString( "meetingTime" );
      subjectCode = input.getString( "subjectCode" );
      subjectReference = (wt.fc.ObjectReference) input.readObject( "subjectReference", subjectReference, wt.fc.ObjectReference.class, true );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion_8656597231916374465L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      attendance = (java.lang.String) input.readObject();
      chargeName = (java.lang.String) input.readObject();
      chargeReference = (wt.fc.ObjectReference) input.readObject();
      meetingDate = (java.sql.Timestamp) input.readObject();
      meetingLocation = (java.lang.String) input.readObject();
      meetingName = (java.lang.String) input.readObject();
      meetingTime = (java.lang.String) input.readObject();
      subjectCode = (java.lang.String) input.readObject();
      subjectReference = (wt.fc.ObjectReference) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETMeetingMinutes thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8656597231916374465L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMeetingMinutes( _KETMeetingMinutes thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
