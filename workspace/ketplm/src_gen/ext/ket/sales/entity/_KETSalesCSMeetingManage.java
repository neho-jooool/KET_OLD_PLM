package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesCSMeetingManage extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesCSMeetingManage.class.getName();

   /**
    * 차수
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String DEGREE = "degree";
   static int DEGREE_UPPER_LIMIT = -1;
   java.lang.String degree;
   /**
    * 차수
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public java.lang.String getDegree() {
      return degree;
   }
   /**
    * 차수
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setDegree(java.lang.String degree) throws wt.util.WTPropertyVetoException {
      degreeValidate(degree);
      this.degree = degree;
   }
   void degreeValidate(java.lang.String degree) throws wt.util.WTPropertyVetoException {
      if (DEGREE_UPPER_LIMIT < 1) {
         try { DEGREE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("degree").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEGREE_UPPER_LIMIT = 200; }
      }
      if (degree != null && !wt.fc.PersistenceHelper.checkStoredLength(degree.toString(), DEGREE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "degree"), java.lang.String.valueOf(java.lang.Math.min(DEGREE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "degree", this.degree, degree));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String YEAR = "year";
   static int YEAR_UPPER_LIMIT = -1;
   java.lang.String year;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public java.lang.String getYear() {
      return year;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
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
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String MONTH = "month";
   static int MONTH_UPPER_LIMIT = -1;
   java.lang.String month;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public java.lang.String getMonth() {
      return month;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
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
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String CS_CLOSE = "csClose";
   static int CS_CLOSE_UPPER_LIMIT = -1;
   java.lang.String csClose;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public java.lang.String getCsClose() {
      return csClose;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setCsClose(java.lang.String csClose) throws wt.util.WTPropertyVetoException {
      csCloseValidate(csClose);
      this.csClose = csClose;
   }
   void csCloseValidate(java.lang.String csClose) throws wt.util.WTPropertyVetoException {
      if (CS_CLOSE_UPPER_LIMIT < 1) {
         try { CS_CLOSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("csClose").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CS_CLOSE_UPPER_LIMIT = 200; }
      }
      if (csClose != null && !wt.fc.PersistenceHelper.checkStoredLength(csClose.toString(), CS_CLOSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "csClose"), java.lang.String.valueOf(java.lang.Math.min(CS_CLOSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "csClose", this.csClose, csClose));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String CS_START_DATE = "csStartDate";
   java.sql.Timestamp csStartDate;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public java.sql.Timestamp getCsStartDate() {
      return csStartDate;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setCsStartDate(java.sql.Timestamp csStartDate) throws wt.util.WTPropertyVetoException {
      csStartDateValidate(csStartDate);
      this.csStartDate = csStartDate;
   }
   void csStartDateValidate(java.sql.Timestamp csStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String CS_END_DATE = "csEndDate";
   java.sql.Timestamp csEndDate;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public java.sql.Timestamp getCsEndDate() {
      return csEndDate;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setCsEndDate(java.sql.Timestamp csEndDate) throws wt.util.WTPropertyVetoException {
      csEndDateValidate(csEndDate);
      this.csEndDate = csEndDate;
   }
   void csEndDateValidate(java.sql.Timestamp csEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 차기회의시작일자(예정)
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String NEXT_STARTDATE = "nextStartdate";
   java.sql.Timestamp nextStartdate;
   /**
    * 차기회의시작일자(예정)
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public java.sql.Timestamp getNextStartdate() {
      return nextStartdate;
   }
   /**
    * 차기회의시작일자(예정)
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setNextStartdate(java.sql.Timestamp nextStartdate) throws wt.util.WTPropertyVetoException {
      nextStartdateValidate(nextStartdate);
      this.nextStartdate = nextStartdate;
   }
   void nextStartdateValidate(java.sql.Timestamp nextStartdate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String CREATE_USER = "createUser";
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String CREATE_USER_REFERENCE = "createUserReference";
   wt.fc.ObjectReference createUserReference;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public wt.org.WTUser getCreateUser() {
      return (createUserReference != null) ? (wt.org.WTUser) createUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public wt.fc.ObjectReference getCreateUserReference() {
      return createUserReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setCreateUser(wt.org.WTUser the_createUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCreateUserReference(the_createUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_createUser));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setCreateUserReference(wt.fc.ObjectReference the_createUserReference) throws wt.util.WTPropertyVetoException {
      createUserReferenceValidate(the_createUserReference);
      createUserReference = (wt.fc.ObjectReference) the_createUserReference;
   }
   void createUserReferenceValidate(wt.fc.ObjectReference the_createUserReference) throws wt.util.WTPropertyVetoException {
      if (the_createUserReference == null || the_createUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "createUserReference") },
               new java.beans.PropertyChangeEvent(this, "createUserReference", this.createUserReference, createUserReference));
      if (the_createUserReference != null && the_createUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_createUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "createUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "createUserReference", this.createUserReference, createUserReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String MODIFY_USER = "modifyUser";
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public static final java.lang.String MODIFY_USER_REFERENCE = "modifyUserReference";
   wt.fc.ObjectReference modifyUserReference;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public wt.org.WTUser getModifyUser() {
      return (modifyUserReference != null) ? (wt.org.WTUser) modifyUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public wt.fc.ObjectReference getModifyUserReference() {
      return modifyUserReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setModifyUser(wt.org.WTUser the_modifyUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setModifyUserReference(the_modifyUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_modifyUser));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManage
    */
   public void setModifyUserReference(wt.fc.ObjectReference the_modifyUserReference) throws wt.util.WTPropertyVetoException {
      modifyUserReferenceValidate(the_modifyUserReference);
      modifyUserReference = (wt.fc.ObjectReference) the_modifyUserReference;
   }
   void modifyUserReferenceValidate(wt.fc.ObjectReference the_modifyUserReference) throws wt.util.WTPropertyVetoException {
      if (the_modifyUserReference == null || the_modifyUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyUserReference") },
               new java.beans.PropertyChangeEvent(this, "modifyUserReference", this.modifyUserReference, modifyUserReference));
      if (the_modifyUserReference != null && the_modifyUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_modifyUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "modifyUserReference", this.modifyUserReference, modifyUserReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -4847795540428593326L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( createUserReference );
      output.writeObject( csClose );
      output.writeObject( csEndDate );
      output.writeObject( csStartDate );
      output.writeObject( degree );
      output.writeObject( modifyUserReference );
      output.writeObject( month );
      output.writeObject( nextStartdate );
      output.writeObject( year );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETSalesCSMeetingManage(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesCSMeetingManage) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSalesCSMeetingManage(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "createUserReference", createUserReference, wt.fc.ObjectReference.class, true );
      output.setString( "csClose", csClose );
      output.setTimestamp( "csEndDate", csEndDate );
      output.setTimestamp( "csStartDate", csStartDate );
      output.setString( "degree", degree );
      output.writeObject( "modifyUserReference", modifyUserReference, wt.fc.ObjectReference.class, true );
      output.setString( "month", month );
      output.setTimestamp( "nextStartdate", nextStartdate );
      output.setString( "year", year );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      createUserReference = (wt.fc.ObjectReference) input.readObject( "createUserReference", createUserReference, wt.fc.ObjectReference.class, true );
      csClose = input.getString( "csClose" );
      csEndDate = input.getTimestamp( "csEndDate" );
      csStartDate = input.getTimestamp( "csStartDate" );
      degree = input.getString( "degree" );
      modifyUserReference = (wt.fc.ObjectReference) input.readObject( "modifyUserReference", modifyUserReference, wt.fc.ObjectReference.class, true );
      month = input.getString( "month" );
      nextStartdate = input.getTimestamp( "nextStartdate" );
      year = input.getString( "year" );
   }

   boolean readVersion_4847795540428593326L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      createUserReference = (wt.fc.ObjectReference) input.readObject();
      csClose = (java.lang.String) input.readObject();
      csEndDate = (java.sql.Timestamp) input.readObject();
      csStartDate = (java.sql.Timestamp) input.readObject();
      degree = (java.lang.String) input.readObject();
      modifyUserReference = (wt.fc.ObjectReference) input.readObject();
      month = (java.lang.String) input.readObject();
      nextStartdate = (java.sql.Timestamp) input.readObject();
      year = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETSalesCSMeetingManage thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4847795540428593326L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSalesCSMeetingManage( _KETSalesCSMeetingManage thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
