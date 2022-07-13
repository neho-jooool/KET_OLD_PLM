package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETCompetentDepartment extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETCompetentDepartment.class.getName();

   /**
    * 회신기한(ECR의 검토요청기한)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String REPLY_DEADLINE = "replyDeadline";
   java.sql.Timestamp replyDeadline;
   /**
    * 회신기한(ECR의 검토요청기한)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public java.sql.Timestamp getReplyDeadline() {
      return replyDeadline;
   }
   /**
    * 회신기한(ECR의 검토요청기한)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setReplyDeadline(java.sql.Timestamp replyDeadline) throws wt.util.WTPropertyVetoException {
      replyDeadlineValidate(replyDeadline);
      this.replyDeadline = replyDeadline;
   }
   void replyDeadlineValidate(java.sql.Timestamp replyDeadline) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 주관부서코드
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String SUBJECT_CODE = "subjectCode";
   static int SUBJECT_CODE_UPPER_LIMIT = -1;
   java.lang.String subjectCode;
   /**
    * 주관부서코드
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public java.lang.String getSubjectCode() {
      return subjectCode;
   }
   /**
    * 주관부서코드
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
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
    * 담당자 ID(WTUser의 Name)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String CHARGE_NAME = "chargeName";
   static int CHARGE_NAME_UPPER_LIMIT = -1;
   java.lang.String chargeName;
   /**
    * 담당자 ID(WTUser의 Name)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public java.lang.String getChargeName() {
      return chargeName;
   }
   /**
    * 담당자 ID(WTUser의 Name)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
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
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * NumberCode의 코드(검토결과)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String REVIEW_CODE = "reviewCode";
   static int REVIEW_CODE_UPPER_LIMIT = -1;
   java.lang.String reviewCode;
   /**
    * NumberCode의 코드(검토결과)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public java.lang.String getReviewCode() {
      return reviewCode;
   }
   /**
    * NumberCode의 코드(검토결과)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setReviewCode(java.lang.String reviewCode) throws wt.util.WTPropertyVetoException {
      reviewCodeValidate(reviewCode);
      this.reviewCode = reviewCode;
   }
   void reviewCodeValidate(java.lang.String reviewCode) throws wt.util.WTPropertyVetoException {
      if (REVIEW_CODE_UPPER_LIMIT < 1) {
         try { REVIEW_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_CODE_UPPER_LIMIT = 200; }
      }
      if (reviewCode != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewCode.toString(), REVIEW_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewCode"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewCode", this.reviewCode, reviewCode));
   }

   /**
    * NumberCode의 코드(회의필요여부)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String MEETING_CODE = "meetingCode";
   static int MEETING_CODE_UPPER_LIMIT = -1;
   java.lang.String meetingCode;
   /**
    * NumberCode의 코드(회의필요여부)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public java.lang.String getMeetingCode() {
      return meetingCode;
   }
   /**
    * NumberCode의 코드(회의필요여부)
    *
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setMeetingCode(java.lang.String meetingCode) throws wt.util.WTPropertyVetoException {
      meetingCodeValidate(meetingCode);
      this.meetingCode = meetingCode;
   }
   void meetingCodeValidate(java.lang.String meetingCode) throws wt.util.WTPropertyVetoException {
      if (MEETING_CODE_UPPER_LIMIT < 1) {
         try { MEETING_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("meetingCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEETING_CODE_UPPER_LIMIT = 200; }
      }
      if (meetingCode != null && !wt.fc.PersistenceHelper.checkStoredLength(meetingCode.toString(), MEETING_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingCode"), java.lang.String.valueOf(java.lang.Math.min(MEETING_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "meetingCode", this.meetingCode, meetingCode));
   }

   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String CHARGE = "charge";
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String CHARGE_REFERENCE = "chargeReference";
   wt.fc.ObjectReference chargeReference;
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public wt.org.WTUser getCharge() {
      return (chargeReference != null) ? (wt.org.WTUser) chargeReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public wt.fc.ObjectReference getChargeReference() {
      return chargeReference;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setCharge(wt.org.WTUser the_charge) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setChargeReference(the_charge == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_charge));
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
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
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String SUBJECT = "subject";
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String SUBJECT_REFERENCE = "subjectReference";
   wt.fc.ObjectReference subjectReference;
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public e3ps.groupware.company.Department getSubject() {
      return (subjectReference != null) ? (e3ps.groupware.company.Department) subjectReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public wt.fc.ObjectReference getSubjectReference() {
      return subjectReference;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setSubject(e3ps.groupware.company.Department the_subject) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setSubjectReference(the_subject == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_subject));
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
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

   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String REVIEW = "review";
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public static final java.lang.String REVIEW_REFERENCE = "reviewReference";
   wt.fc.ObjectReference reviewReference;
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public e3ps.common.code.NumberCode getReview() {
      return (reviewReference != null) ? (e3ps.common.code.NumberCode) reviewReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public wt.fc.ObjectReference getReviewReference() {
      return reviewReference;
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setReview(e3ps.common.code.NumberCode the_review) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setReviewReference(the_review == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_review));
   }
   /**
    * @see e3ps.ecm.entity.KETCompetentDepartment
    */
   public void setReviewReference(wt.fc.ObjectReference the_reviewReference) throws wt.util.WTPropertyVetoException {
      reviewReferenceValidate(the_reviewReference);
      reviewReference = (wt.fc.ObjectReference) the_reviewReference;
   }
   void reviewReferenceValidate(wt.fc.ObjectReference the_reviewReference) throws wt.util.WTPropertyVetoException {
      if (the_reviewReference != null && the_reviewReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_reviewReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "reviewReference", this.reviewReference, reviewReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7097647814826054974L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( chargeName );
      output.writeObject( chargeReference );
      output.writeObject( meetingCode );
      output.writeObject( replyDeadline );
      output.writeObject( reviewCode );
      output.writeObject( reviewReference );
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

   protected void super_writeExternal_KETCompetentDepartment(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETCompetentDepartment) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETCompetentDepartment(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "chargeName", chargeName );
      output.writeObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      output.setString( "meetingCode", meetingCode );
      output.setTimestamp( "replyDeadline", replyDeadline );
      output.setString( "reviewCode", reviewCode );
      output.writeObject( "reviewReference", reviewReference, wt.fc.ObjectReference.class, true );
      output.setString( "subjectCode", subjectCode );
      output.writeObject( "subjectReference", subjectReference, wt.fc.ObjectReference.class, true );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      chargeName = input.getString( "chargeName" );
      chargeReference = (wt.fc.ObjectReference) input.readObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      meetingCode = input.getString( "meetingCode" );
      replyDeadline = input.getTimestamp( "replyDeadline" );
      reviewCode = input.getString( "reviewCode" );
      reviewReference = (wt.fc.ObjectReference) input.readObject( "reviewReference", reviewReference, wt.fc.ObjectReference.class, true );
      subjectCode = input.getString( "subjectCode" );
      subjectReference = (wt.fc.ObjectReference) input.readObject( "subjectReference", subjectReference, wt.fc.ObjectReference.class, true );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion7097647814826054974L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      chargeName = (java.lang.String) input.readObject();
      chargeReference = (wt.fc.ObjectReference) input.readObject();
      meetingCode = (java.lang.String) input.readObject();
      replyDeadline = (java.sql.Timestamp) input.readObject();
      reviewCode = (java.lang.String) input.readObject();
      reviewReference = (wt.fc.ObjectReference) input.readObject();
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

   protected boolean readVersion( KETCompetentDepartment thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7097647814826054974L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETCompetentDepartment( _KETCompetentDepartment thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
