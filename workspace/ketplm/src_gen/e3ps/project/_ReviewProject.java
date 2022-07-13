package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ReviewProject extends e3ps.project.E3PSProject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ReviewProject.class.getName();

   /**
    * 의뢰서 요청일
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * 의뢰서 요청일
    *
    * @see e3ps.project.ReviewProject
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * 의뢰서 요청일
    *
    * @see e3ps.project.ReviewProject
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 의뢰서 접수일
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String RECEIVE_DATE = "receiveDate";
   java.sql.Timestamp receiveDate;
   /**
    * 의뢰서 접수일
    *
    * @see e3ps.project.ReviewProject
    */
   public java.sql.Timestamp getReceiveDate() {
      return receiveDate;
   }
   /**
    * 의뢰서 접수일
    *
    * @see e3ps.project.ReviewProject
    */
   public void setReceiveDate(java.sql.Timestamp receiveDate) throws wt.util.WTPropertyVetoException {
      receiveDateValidate(receiveDate);
      this.receiveDate = receiveDate;
   }
   void receiveDateValidate(java.sql.Timestamp receiveDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 제안서 제출일
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String PROPOSAL_DATE = "proposalDate";
   java.sql.Timestamp proposalDate;
   /**
    * 제안서 제출일
    *
    * @see e3ps.project.ReviewProject
    */
   public java.sql.Timestamp getProposalDate() {
      return proposalDate;
   }
   /**
    * 제안서 제출일
    *
    * @see e3ps.project.ReviewProject
    */
   public void setProposalDate(java.sql.Timestamp proposalDate) throws wt.util.WTPropertyVetoException {
      proposalDateValidate(proposalDate);
      this.proposalDate = proposalDate;
   }
   void proposalDateValidate(java.sql.Timestamp proposalDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 견적서 제출일
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String ESTIMATE_DATE = "estimateDate";
   java.sql.Timestamp estimateDate;
   /**
    * 견적서 제출일
    *
    * @see e3ps.project.ReviewProject
    */
   public java.sql.Timestamp getEstimateDate() {
      return estimateDate;
   }
   /**
    * 견적서 제출일
    *
    * @see e3ps.project.ReviewProject
    */
   public void setEstimateDate(java.sql.Timestamp estimateDate) throws wt.util.WTPropertyVetoException {
      estimateDateValidate(estimateDate);
      this.estimateDate = estimateDate;
   }
   void estimateDateValidate(java.sql.Timestamp estimateDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * SOP 일정
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String SOP = "sop";
   java.sql.Timestamp sop;
   /**
    * SOP 일정
    *
    * @see e3ps.project.ReviewProject
    */
   public java.sql.Timestamp getSop() {
      return sop;
   }
   /**
    * SOP 일정
    *
    * @see e3ps.project.ReviewProject
    */
   public void setSop(java.sql.Timestamp sop) throws wt.util.WTPropertyVetoException {
      sopValidate(sop);
      this.sop = sop;
   }
   void sopValidate(java.sql.Timestamp sop) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 개발자
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String ATTR3 = "attr3";
   static int ATTR3_UPPER_LIMIT = -1;
   java.lang.String attr3;
   /**
    * 개발자
    *
    * @see e3ps.project.ReviewProject
    */
   public java.lang.String getAttr3() {
      return attr3;
   }
   /**
    * 개발자
    *
    * @see e3ps.project.ReviewProject
    */
   public void setAttr3(java.lang.String attr3) throws wt.util.WTPropertyVetoException {
      attr3Validate(attr3);
      this.attr3 = attr3;
   }
   void attr3Validate(java.lang.String attr3) throws wt.util.WTPropertyVetoException {
      if (ATTR3_UPPER_LIMIT < 1) {
         try { ATTR3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR3_UPPER_LIMIT = 200; }
      }
      if (attr3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr3.toString(), ATTR3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr3"), java.lang.String.valueOf(java.lang.Math.min(ATTR3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr3", this.attr3, attr3));
   }

   /**
    * 영업담당자
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String ATTR2 = "attr2";
   static int ATTR2_UPPER_LIMIT = -1;
   java.lang.String attr2;
   /**
    * 영업담당자
    *
    * @see e3ps.project.ReviewProject
    */
   public java.lang.String getAttr2() {
      return attr2;
   }
   /**
    * 영업담당자
    *
    * @see e3ps.project.ReviewProject
    */
   public void setAttr2(java.lang.String attr2) throws wt.util.WTPropertyVetoException {
      attr2Validate(attr2);
      this.attr2 = attr2;
   }
   void attr2Validate(java.lang.String attr2) throws wt.util.WTPropertyVetoException {
      if (ATTR2_UPPER_LIMIT < 1) {
         try { ATTR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR2_UPPER_LIMIT = 200; }
      }
      if (attr2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr2.toString(), ATTR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr2"), java.lang.String.valueOf(java.lang.Math.min(ATTR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr2", this.attr2, attr2));
   }

   /**
    * 기획담당자
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String ATTR1 = "attr1";
   static int ATTR1_UPPER_LIMIT = -1;
   java.lang.String attr1;
   /**
    * 기획담당자
    *
    * @see e3ps.project.ReviewProject
    */
   public java.lang.String getAttr1() {
      return attr1;
   }
   /**
    * 기획담당자
    *
    * @see e3ps.project.ReviewProject
    */
   public void setAttr1(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      attr1Validate(attr1);
      this.attr1 = attr1;
   }
   void attr1Validate(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      if (ATTR1_UPPER_LIMIT < 1) {
         try { ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR1_UPPER_LIMIT = 200; }
      }
      if (attr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr1.toString(), ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr1"), java.lang.String.valueOf(java.lang.Math.min(ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr1", this.attr1, attr1));
   }

   /**
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String ATTR4 = "attr4";
   static int ATTR4_UPPER_LIMIT = -1;
   java.lang.String attr4;
   /**
    * @see e3ps.project.ReviewProject
    */
   public java.lang.String getAttr4() {
      return attr4;
   }
   /**
    * @see e3ps.project.ReviewProject
    */
   public void setAttr4(java.lang.String attr4) throws wt.util.WTPropertyVetoException {
      attr4Validate(attr4);
      this.attr4 = attr4;
   }
   void attr4Validate(java.lang.String attr4) throws wt.util.WTPropertyVetoException {
      if (ATTR4_UPPER_LIMIT < 1) {
         try { ATTR4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR4_UPPER_LIMIT = 200; }
      }
      if (attr4 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr4.toString(), ATTR4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr4"), java.lang.String.valueOf(java.lang.Math.min(ATTR4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr4", this.attr4, attr4));
   }

   /**
    * 검토결과
    *
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String REVIEW_RESULT = "reviewResult";
   static int REVIEW_RESULT_UPPER_LIMIT = -1;
   java.lang.String reviewResult;
   /**
    * 검토결과
    *
    * @see e3ps.project.ReviewProject
    */
   public java.lang.String getReviewResult() {
      return reviewResult;
   }
   /**
    * 검토결과
    *
    * @see e3ps.project.ReviewProject
    */
   public void setReviewResult(java.lang.String reviewResult) throws wt.util.WTPropertyVetoException {
      reviewResultValidate(reviewResult);
      this.reviewResult = reviewResult;
   }
   void reviewResultValidate(java.lang.String reviewResult) throws wt.util.WTPropertyVetoException {
      if (REVIEW_RESULT_UPPER_LIMIT < 1) {
         try { REVIEW_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_RESULT_UPPER_LIMIT = 200; }
      }
      if (reviewResult != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewResult.toString(), REVIEW_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewResult"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewResult", this.reviewResult, reviewResult));
   }

   /**
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String DEV_DEPT = "devDept";
   /**
    * @see e3ps.project.ReviewProject
    */
   public static final java.lang.String DEV_DEPT_REFERENCE = "devDeptReference";
   wt.fc.ObjectReference devDeptReference;
   /**
    * @see e3ps.project.ReviewProject
    */
   public e3ps.groupware.company.Department getDevDept() {
      return (devDeptReference != null) ? (e3ps.groupware.company.Department) devDeptReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ReviewProject
    */
   public wt.fc.ObjectReference getDevDeptReference() {
      return devDeptReference;
   }
   /**
    * @see e3ps.project.ReviewProject
    */
   public void setDevDept(e3ps.groupware.company.Department the_devDept) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevDeptReference(the_devDept == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_devDept));
   }
   /**
    * @see e3ps.project.ReviewProject
    */
   public void setDevDeptReference(wt.fc.ObjectReference the_devDeptReference) throws wt.util.WTPropertyVetoException {
      devDeptReferenceValidate(the_devDeptReference);
      devDeptReference = (wt.fc.ObjectReference) the_devDeptReference;
   }
   void devDeptReferenceValidate(wt.fc.ObjectReference the_devDeptReference) throws wt.util.WTPropertyVetoException {
      if (the_devDeptReference == null || the_devDeptReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devDeptReference") },
               new java.beans.PropertyChangeEvent(this, "devDeptReference", this.devDeptReference, devDeptReference));
      if (the_devDeptReference != null && the_devDeptReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_devDeptReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devDeptReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "devDeptReference", this.devDeptReference, devDeptReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 6074573276119269724L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( attr1 );
      output.writeObject( attr2 );
      output.writeObject( attr3 );
      output.writeObject( attr4 );
      output.writeObject( devDeptReference );
      output.writeObject( estimateDate );
      output.writeObject( proposalDate );
      output.writeObject( receiveDate );
      output.writeObject( requestDate );
      output.writeObject( reviewResult );
      output.writeObject( sop );
   }

   protected void super_writeExternal_ReviewProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ReviewProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ReviewProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "attr1", attr1 );
      output.setString( "attr2", attr2 );
      output.setString( "attr3", attr3 );
      output.setString( "attr4", attr4 );
      output.writeObject( "devDeptReference", devDeptReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "estimateDate", estimateDate );
      output.setTimestamp( "proposalDate", proposalDate );
      output.setTimestamp( "receiveDate", receiveDate );
      output.setTimestamp( "requestDate", requestDate );
      output.setString( "reviewResult", reviewResult );
      output.setTimestamp( "sop", sop );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      attr1 = input.getString( "attr1" );
      attr2 = input.getString( "attr2" );
      attr3 = input.getString( "attr3" );
      attr4 = input.getString( "attr4" );
      devDeptReference = (wt.fc.ObjectReference) input.readObject( "devDeptReference", devDeptReference, wt.fc.ObjectReference.class, true );
      estimateDate = input.getTimestamp( "estimateDate" );
      proposalDate = input.getTimestamp( "proposalDate" );
      receiveDate = input.getTimestamp( "receiveDate" );
      requestDate = input.getTimestamp( "requestDate" );
      reviewResult = input.getString( "reviewResult" );
      sop = input.getTimestamp( "sop" );
   }

   boolean readVersion6074573276119269724L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      attr1 = (java.lang.String) input.readObject();
      attr2 = (java.lang.String) input.readObject();
      attr3 = (java.lang.String) input.readObject();
      attr4 = (java.lang.String) input.readObject();
      devDeptReference = (wt.fc.ObjectReference) input.readObject();
      estimateDate = (java.sql.Timestamp) input.readObject();
      proposalDate = (java.sql.Timestamp) input.readObject();
      receiveDate = (java.sql.Timestamp) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();
      reviewResult = (java.lang.String) input.readObject();
      sop = (java.sql.Timestamp) input.readObject();
      return true;
   }

   protected boolean readVersion( ReviewProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6074573276119269724L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ReviewProject( _ReviewProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
