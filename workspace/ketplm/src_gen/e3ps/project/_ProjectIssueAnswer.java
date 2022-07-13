package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectIssueAnswer implements wt.content.ContentHolder, e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectIssueAnswer.class.getName();

   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String QUESTION = "question";
   java.lang.Object question;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public java.lang.Object getQuestion() {
      return question;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public void setQuestion(java.lang.Object question) throws wt.util.WTPropertyVetoException {
      questionValidate(question);
      this.question = question;
   }
   void questionValidate(java.lang.Object question) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String CREATE_DATE = "createDate";
   java.sql.Timestamp createDate;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public java.sql.Timestamp getCreateDate() {
      return createDate;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public void setCreateDate(java.sql.Timestamp createDate) throws wt.util.WTPropertyVetoException {
      createDateValidate(createDate);
      this.createDate = createDate;
   }
   void createDateValidate(java.sql.Timestamp createDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String HISTORY_ATTR = "historyAttr";
   static int HISTORY_ATTR_UPPER_LIMIT = -1;
   java.lang.String historyAttr;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public java.lang.String getHistoryAttr() {
      return historyAttr;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public void setHistoryAttr(java.lang.String historyAttr) throws wt.util.WTPropertyVetoException {
      historyAttrValidate(historyAttr);
      this.historyAttr = historyAttr;
   }
   void historyAttrValidate(java.lang.String historyAttr) throws wt.util.WTPropertyVetoException {
      if (HISTORY_ATTR_UPPER_LIMIT < 1) {
         try { HISTORY_ATTR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("historyAttr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HISTORY_ATTR_UPPER_LIMIT = 200; }
      }
      if (historyAttr != null && !wt.fc.PersistenceHelper.checkStoredLength(historyAttr.toString(), HISTORY_ATTR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "historyAttr"), java.lang.String.valueOf(java.lang.Math.min(HISTORY_ATTR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "historyAttr", this.historyAttr, historyAttr));
   }

   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String ATTR1 = "attr1";
   static int ATTR1_UPPER_LIMIT = -1;
   java.lang.String attr1;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public java.lang.String getAttr1() {
      return attr1;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
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
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String ATTR2 = "attr2";
   static int ATTR2_UPPER_LIMIT = -1;
   java.lang.String attr2;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public java.lang.String getAttr2() {
      return attr2;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
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
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String IS_CHECK = "isCheck";
   boolean isCheck = false;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public boolean isIsCheck() {
      return isCheck;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public void setIsCheck(boolean isCheck) throws wt.util.WTPropertyVetoException {
      isCheckValidate(isCheck);
      this.isCheck = isCheck;
   }
   void isCheckValidate(boolean isCheck) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String DEPENDENCY_ANSWER = "dependencyAnswer";
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public static final java.lang.String DEPENDENCY_ANSWER_REFERENCE = "dependencyAnswerReference";
   wt.fc.ObjectReference dependencyAnswerReference;
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public e3ps.project.ProjectIssueAnswer getDependencyAnswer() {
      return (dependencyAnswerReference != null) ? (e3ps.project.ProjectIssueAnswer) dependencyAnswerReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public wt.fc.ObjectReference getDependencyAnswerReference() {
      return dependencyAnswerReference;
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public void setDependencyAnswer(e3ps.project.ProjectIssueAnswer the_dependencyAnswer) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDependencyAnswerReference(the_dependencyAnswer == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProjectIssueAnswer) the_dependencyAnswer));
   }
   /**
    * @see e3ps.project.ProjectIssueAnswer
    */
   public void setDependencyAnswerReference(wt.fc.ObjectReference the_dependencyAnswerReference) throws wt.util.WTPropertyVetoException {
      dependencyAnswerReferenceValidate(the_dependencyAnswerReference);
      dependencyAnswerReference = (wt.fc.ObjectReference) the_dependencyAnswerReference;
   }
   void dependencyAnswerReferenceValidate(wt.fc.ObjectReference the_dependencyAnswerReference) throws wt.util.WTPropertyVetoException {
      if (the_dependencyAnswerReference == null || the_dependencyAnswerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dependencyAnswerReference") },
               new java.beans.PropertyChangeEvent(this, "dependencyAnswerReference", this.dependencyAnswerReference, dependencyAnswerReference));
      if (the_dependencyAnswerReference != null && the_dependencyAnswerReference.getReferencedClass() != null &&
            !e3ps.project.ProjectIssueAnswer.class.isAssignableFrom(the_dependencyAnswerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dependencyAnswerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "dependencyAnswerReference", this.dependencyAnswerReference, dependencyAnswerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -1096442131857193244L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( attr1 );
      output.writeObject( attr2 );
      output.writeObject( createDate );
      output.writeObject( dependencyAnswerReference );
      output.writeObject( historyAttr );
      output.writeBoolean( isCheck );
      output.writeObject( owner );
      output.writeObject( planDate );
      output.writeObject( question );
      output.writeObject( thePersistInfo );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectIssueAnswer) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "attr1", attr1 );
      output.setString( "attr2", attr2 );
      output.setTimestamp( "createDate", createDate );
      output.writeObject( "dependencyAnswerReference", dependencyAnswerReference, wt.fc.ObjectReference.class, true );
      output.setString( "historyAttr", historyAttr );
      output.setBoolean( "isCheck", isCheck );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setTimestamp( "planDate", planDate );
      output.setObject( "question", question );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      attr1 = input.getString( "attr1" );
      attr2 = input.getString( "attr2" );
      createDate = input.getTimestamp( "createDate" );
      dependencyAnswerReference = (wt.fc.ObjectReference) input.readObject( "dependencyAnswerReference", dependencyAnswerReference, wt.fc.ObjectReference.class, true );
      historyAttr = input.getString( "historyAttr" );
      isCheck = input.getBoolean( "isCheck" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      planDate = input.getTimestamp( "planDate" );
      question = (java.lang.Object) input.getObject( "question" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_1096442131857193244L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      attr1 = (java.lang.String) input.readObject();
      attr2 = (java.lang.String) input.readObject();
      createDate = (java.sql.Timestamp) input.readObject();
      dependencyAnswerReference = (wt.fc.ObjectReference) input.readObject();
      historyAttr = (java.lang.String) input.readObject();
      isCheck = input.readBoolean();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      question = (java.lang.Object) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( ProjectIssueAnswer thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1096442131857193244L( input, readSerialVersionUID, superDone );
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
