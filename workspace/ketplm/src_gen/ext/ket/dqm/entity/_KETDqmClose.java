package ext.ket.dqm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDqmClose implements wt.content.FormatContentHolder, wt.access.AccessControlled, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dqm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDqmClose.class.getName();

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String REVIEW_RSLT_NAME = "reviewRsltName";
   static int REVIEW_RSLT_NAME_UPPER_LIMIT = -1;
   java.lang.String reviewRsltName;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.String getReviewRsltName() {
      return reviewRsltName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setReviewRsltName(java.lang.String reviewRsltName) throws wt.util.WTPropertyVetoException {
      reviewRsltNameValidate(reviewRsltName);
      this.reviewRsltName = reviewRsltName;
   }
   void reviewRsltNameValidate(java.lang.String reviewRsltName) throws wt.util.WTPropertyVetoException {
      if (REVIEW_RSLT_NAME_UPPER_LIMIT < 1) {
         try { REVIEW_RSLT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewRsltName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_RSLT_NAME_UPPER_LIMIT = 200; }
      }
      if (reviewRsltName != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewRsltName.toString(), REVIEW_RSLT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewRsltName"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_RSLT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewRsltName", this.reviewRsltName, reviewRsltName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String REVIEW_RSLT_CODE = "reviewRsltCode";
   static int REVIEW_RSLT_CODE_UPPER_LIMIT = -1;
   java.lang.String reviewRsltCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.String getReviewRsltCode() {
      return reviewRsltCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setReviewRsltCode(java.lang.String reviewRsltCode) throws wt.util.WTPropertyVetoException {
      reviewRsltCodeValidate(reviewRsltCode);
      this.reviewRsltCode = reviewRsltCode;
   }
   void reviewRsltCodeValidate(java.lang.String reviewRsltCode) throws wt.util.WTPropertyVetoException {
      if (REVIEW_RSLT_CODE_UPPER_LIMIT < 1) {
         try { REVIEW_RSLT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewRsltCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_RSLT_CODE_UPPER_LIMIT = 200; }
      }
      if (reviewRsltCode != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewRsltCode.toString(), REVIEW_RSLT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewRsltCode"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_RSLT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewRsltCode", this.reviewRsltCode, reviewRsltCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String APPLY_EXPECT_DATE = "applyExpectDate";
   java.sql.Timestamp applyExpectDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.sql.Timestamp getApplyExpectDate() {
      return applyExpectDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setApplyExpectDate(java.sql.Timestamp applyExpectDate) throws wt.util.WTPropertyVetoException {
      applyExpectDateValidate(applyExpectDate);
      this.applyExpectDate = applyExpectDate;
   }
   void applyExpectDateValidate(java.sql.Timestamp applyExpectDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String REVIEW_DATE = "reviewDate";
   java.sql.Timestamp reviewDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.sql.Timestamp getReviewDate() {
      return reviewDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setReviewDate(java.sql.Timestamp reviewDate) throws wt.util.WTPropertyVetoException {
      reviewDateValidate(reviewDate);
      this.reviewDate = reviewDate;
   }
   void reviewDateValidate(java.sql.Timestamp reviewDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String REVIEW_CHECK_CODE = "reviewCheckCode";
   static int REVIEW_CHECK_CODE_UPPER_LIMIT = -1;
   java.lang.String reviewCheckCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.String getReviewCheckCode() {
      return reviewCheckCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setReviewCheckCode(java.lang.String reviewCheckCode) throws wt.util.WTPropertyVetoException {
      reviewCheckCodeValidate(reviewCheckCode);
      this.reviewCheckCode = reviewCheckCode;
   }
   void reviewCheckCodeValidate(java.lang.String reviewCheckCode) throws wt.util.WTPropertyVetoException {
      if (REVIEW_CHECK_CODE_UPPER_LIMIT < 1) {
         try { REVIEW_CHECK_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewCheckCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_CHECK_CODE_UPPER_LIMIT = 200; }
      }
      if (reviewCheckCode != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewCheckCode.toString(), REVIEW_CHECK_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewCheckCode"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_CHECK_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewCheckCode", this.reviewCheckCode, reviewCheckCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String REVIEW_CHECK_NAME = "reviewCheckName";
   static int REVIEW_CHECK_NAME_UPPER_LIMIT = -1;
   java.lang.String reviewCheckName;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.String getReviewCheckName() {
      return reviewCheckName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setReviewCheckName(java.lang.String reviewCheckName) throws wt.util.WTPropertyVetoException {
      reviewCheckNameValidate(reviewCheckName);
      this.reviewCheckName = reviewCheckName;
   }
   void reviewCheckNameValidate(java.lang.String reviewCheckName) throws wt.util.WTPropertyVetoException {
      if (REVIEW_CHECK_NAME_UPPER_LIMIT < 1) {
         try { REVIEW_CHECK_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewCheckName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_CHECK_NAME_UPPER_LIMIT = 200; }
      }
      if (reviewCheckName != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewCheckName.toString(), REVIEW_CHECK_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewCheckName"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_CHECK_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewCheckName", this.reviewCheckName, reviewCheckName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String PROBLEM_REFLECT_YN = "problemReflectYn";
   static int PROBLEM_REFLECT_YN_UPPER_LIMIT = -1;
   java.lang.String problemReflectYn;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.String getProblemReflectYn() {
      return problemReflectYn;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setProblemReflectYn(java.lang.String problemReflectYn) throws wt.util.WTPropertyVetoException {
      problemReflectYnValidate(problemReflectYn);
      this.problemReflectYn = problemReflectYn;
   }
   void problemReflectYnValidate(java.lang.String problemReflectYn) throws wt.util.WTPropertyVetoException {
      if (PROBLEM_REFLECT_YN_UPPER_LIMIT < 1) {
         try { PROBLEM_REFLECT_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemReflectYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEM_REFLECT_YN_UPPER_LIMIT = 200; }
      }
      if (problemReflectYn != null && !wt.fc.PersistenceHelper.checkStoredLength(problemReflectYn.toString(), PROBLEM_REFLECT_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemReflectYn"), java.lang.String.valueOf(java.lang.Math.min(PROBLEM_REFLECT_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemReflectYn", this.problemReflectYn, problemReflectYn));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String VALIDATION_DATE = "validationDate";
   java.sql.Timestamp validationDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.sql.Timestamp getValidationDate() {
      return validationDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setValidationDate(java.sql.Timestamp validationDate) throws wt.util.WTPropertyVetoException {
      validationDateValidate(validationDate);
      this.validationDate = validationDate;
   }
   void validationDateValidate(java.sql.Timestamp validationDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String VALIDATION_CHECK = "validationCheck";
   static int VALIDATION_CHECK_UPPER_LIMIT = -1;
   java.lang.String validationCheck;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public java.lang.String getValidationCheck() {
      return validationCheck;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setValidationCheck(java.lang.String validationCheck) throws wt.util.WTPropertyVetoException {
      validationCheckValidate(validationCheck);
      this.validationCheck = validationCheck;
   }
   void validationCheckValidate(java.lang.String validationCheck) throws wt.util.WTPropertyVetoException {
      if (VALIDATION_CHECK_UPPER_LIMIT < 1) {
         try { VALIDATION_CHECK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("validationCheck").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VALIDATION_CHECK_UPPER_LIMIT = 200; }
      }
      if (validationCheck != null && !wt.fc.PersistenceHelper.checkStoredLength(validationCheck.toString(), VALIDATION_CHECK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "validationCheck"), java.lang.String.valueOf(java.lang.Math.min(VALIDATION_CHECK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "validationCheck", this.validationCheck, validationCheck));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String USER = "user";
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmClose
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
   }

   wt.content.ContentItem primary;
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public wt.content.ContentItem getPrimary() {
      return primary;
   }
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public void setPrimary(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
      primaryValidate(primary);
      this.primary = primary;
   }
   void primaryValidate(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
   }

   wt.content.DataFormatReference format;
   /**
    * @see wt.content.FormatContentHolder
    */
   public wt.content.DataFormatReference getFormat() {
      return format;
   }
   /**
    * @see wt.content.FormatContentHolder
    */
   public void setFormat(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      formatValidate(format);
      this.format = format;
   }
   void formatValidate(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      if (format == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "format") },
               new java.beans.PropertyChangeEvent(this, "format", this.format, format));
   }

   /**
    * Derived from {@link wt.content.DataFormatReference#getFormatName()}
    *
    * @see wt.content.FormatContentHolder
    */
   public java.lang.String getFormatName() {
      try { return (java.lang.String) ((wt.content.DataFormatReference) getFormat()).getFormatName(); }
      catch (java.lang.NullPointerException npe) { return null; }
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

   public static final long EXTERNALIZATION_VERSION_UID = -4502559868710280623L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( applyExpectDate );
      output.writeObject( format );
      output.writeObject( problemReflectYn );
      output.writeObject( reviewCheckCode );
      output.writeObject( reviewCheckName );
      output.writeObject( reviewDate );
      output.writeObject( reviewRsltCode );
      output.writeObject( reviewRsltName );
      output.writeObject( thePersistInfo );
      output.writeObject( userReference );
      output.writeObject( validationCheck );
      output.writeObject( validationDate );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
         output.writeObject( primary );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dqm.entity.KETDqmClose) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "applyExpectDate", applyExpectDate );
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setString( "problemReflectYn", problemReflectYn );
      output.setString( "reviewCheckCode", reviewCheckCode );
      output.setString( "reviewCheckName", reviewCheckName );
      output.setTimestamp( "reviewDate", reviewDate );
      output.setString( "reviewRsltCode", reviewRsltCode );
      output.setString( "reviewRsltName", reviewRsltName );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      output.setString( "validationCheck", validationCheck );
      output.setTimestamp( "validationDate", validationDate );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      applyExpectDate = input.getTimestamp( "applyExpectDate" );
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      problemReflectYn = input.getString( "problemReflectYn" );
      reviewCheckCode = input.getString( "reviewCheckCode" );
      reviewCheckName = input.getString( "reviewCheckName" );
      reviewDate = input.getTimestamp( "reviewDate" );
      reviewRsltCode = input.getString( "reviewRsltCode" );
      reviewRsltName = input.getString( "reviewRsltName" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      validationCheck = input.getString( "validationCheck" );
      validationDate = input.getTimestamp( "validationDate" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion_4502559868710280623L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      applyExpectDate = (java.sql.Timestamp) input.readObject();
      format = (wt.content.DataFormatReference) input.readObject();
      problemReflectYn = (java.lang.String) input.readObject();
      reviewCheckCode = (java.lang.String) input.readObject();
      reviewCheckName = (java.lang.String) input.readObject();
      reviewDate = (java.sql.Timestamp) input.readObject();
      reviewRsltCode = (java.lang.String) input.readObject();
      reviewRsltName = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      validationCheck = (java.lang.String) input.readObject();
      validationDate = (java.sql.Timestamp) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETDqmClose thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4502559868710280623L( input, readSerialVersionUID, superDone );
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
