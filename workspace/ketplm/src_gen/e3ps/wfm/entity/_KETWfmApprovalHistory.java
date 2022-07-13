package e3ps.wfm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETWfmApprovalHistory implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.wfm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETWfmApprovalHistory.class.getName();

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String COMPLETED_DATE = "completedDate";
   java.sql.Timestamp completedDate;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public java.sql.Timestamp getCompletedDate() {
      return completedDate;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setCompletedDate(java.sql.Timestamp completedDate) throws wt.util.WTPropertyVetoException {
      completedDateValidate(completedDate);
      this.completedDate = completedDate;
   }
   void completedDateValidate(java.sql.Timestamp completedDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String ACTIVITY_NAME = "activityName";
   static int ACTIVITY_NAME_UPPER_LIMIT = -1;
   java.lang.String activityName;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public java.lang.String getActivityName() {
      return activityName;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setActivityName(java.lang.String activityName) throws wt.util.WTPropertyVetoException {
      activityNameValidate(activityName);
      this.activityName = activityName;
   }
   void activityNameValidate(java.lang.String activityName) throws wt.util.WTPropertyVetoException {
      if (ACTIVITY_NAME_UPPER_LIMIT < 1) {
         try { ACTIVITY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activityName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVITY_NAME_UPPER_LIMIT = 200; }
      }
      if (activityName != null && !wt.fc.PersistenceHelper.checkStoredLength(activityName.toString(), ACTIVITY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityName"), java.lang.String.valueOf(java.lang.Math.min(ACTIVITY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activityName", this.activityName, activityName));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String DELEGATE = "delegate";
   static int DELEGATE_UPPER_LIMIT = -1;
   java.lang.String delegate;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public java.lang.String getDelegate() {
      return delegate;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setDelegate(java.lang.String delegate) throws wt.util.WTPropertyVetoException {
      delegateValidate(delegate);
      this.delegate = delegate;
   }
   void delegateValidate(java.lang.String delegate) throws wt.util.WTPropertyVetoException {
      if (DELEGATE_UPPER_LIMIT < 1) {
         try { DELEGATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("delegate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DELEGATE_UPPER_LIMIT = 200; }
      }
      if (delegate != null && !wt.fc.PersistenceHelper.checkStoredLength(delegate.toString(), DELEGATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "delegate"), java.lang.String.valueOf(java.lang.Math.min(DELEGATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "delegate", this.delegate, delegate));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String COMMENTS = "comments";
   static int COMMENTS_UPPER_LIMIT = -1;
   java.lang.String comments;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public java.lang.String getComments() {
      return comments;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setComments(java.lang.String comments) throws wt.util.WTPropertyVetoException {
      commentsValidate(comments);
      this.comments = comments;
   }
   void commentsValidate(java.lang.String comments) throws wt.util.WTPropertyVetoException {
      if (COMMENTS_UPPER_LIMIT < 1) {
         try { COMMENTS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("comments").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMMENTS_UPPER_LIMIT = 4000; }
      }
      if (comments != null && !wt.fc.PersistenceHelper.checkStoredLength(comments.toString(), COMMENTS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "comments"), java.lang.String.valueOf(java.lang.Math.min(COMMENTS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "comments", this.comments, comments));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String DECISION = "decision";
   static int DECISION_UPPER_LIMIT = -1;
   java.lang.String decision;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public java.lang.String getDecision() {
      return decision;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setDecision(java.lang.String decision) throws wt.util.WTPropertyVetoException {
      decisionValidate(decision);
      this.decision = decision;
   }
   void decisionValidate(java.lang.String decision) throws wt.util.WTPropertyVetoException {
      if (DECISION_UPPER_LIMIT < 1) {
         try { DECISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("decision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DECISION_UPPER_LIMIT = 30; }
      }
      if (decision != null && !wt.fc.PersistenceHelper.checkStoredLength(decision.toString(), DECISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "decision"), java.lang.String.valueOf(java.lang.Math.min(DECISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "decision", this.decision, decision));
   }

   /**
    * 결재순서
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String SEQ_NUM = "seqNum";
   int seqNum;
   /**
    * 결재순서
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public int getSeqNum() {
      return seqNum;
   }
   /**
    * 결재순서
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setSeqNum(int seqNum) throws wt.util.WTPropertyVetoException {
      seqNumValidate(seqNum);
      this.seqNum = seqNum;
   }
   void seqNumValidate(int seqNum) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 조건부승인
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String CONDITIONAL_ACCEPT = "conditionalAccept";
   boolean conditionalAccept = false;
   /**
    * 조건부승인
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public boolean isConditionalAccept() {
      return conditionalAccept;
   }
   /**
    * 조건부승인
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setConditionalAccept(boolean conditionalAccept) throws wt.util.WTPropertyVetoException {
      conditionalAcceptValidate(conditionalAccept);
      this.conditionalAccept = conditionalAccept;
   }
   void conditionalAcceptValidate(boolean conditionalAccept) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String LAST = "last";
   boolean last = false;
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public boolean isLast() {
      return last;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setLast(boolean last) throws wt.util.WTPropertyVetoException {
      lastValidate(last);
      this.last = last;
   }
   void lastValidate(boolean last) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 차수(Gate용)
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String DEGREE = "degree";
   int degree;
   /**
    * 차수(Gate용)
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public int getDegree() {
      return degree;
   }
   /**
    * 차수(Gate용)
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setDegree(int degree) throws wt.util.WTPropertyVetoException {
      degreeValidate(degree);
      this.degree = degree;
   }
   void degreeValidate(int degree) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 결재의견 점검
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String RISK_CHECK = "riskCheck";
   boolean riskCheck = false;
   /**
    * 결재의견 점검
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public boolean isRiskCheck() {
      return riskCheck;
   }
   /**
    * 결재의견 점검
    *
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setRiskCheck(boolean riskCheck) throws wt.util.WTPropertyVetoException {
      riskCheckValidate(riskCheck);
      this.riskCheck = riskCheck;
   }
   void riskCheckValidate(boolean riskCheck) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String APP_MASTER = "appMaster";
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public static final java.lang.String APP_MASTER_REFERENCE = "appMasterReference";
   wt.fc.ObjectReference appMasterReference;
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public e3ps.wfm.entity.KETWfmApprovalMaster getAppMaster() {
      return (appMasterReference != null) ? (e3ps.wfm.entity.KETWfmApprovalMaster) appMasterReference.getObject() : null;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public wt.fc.ObjectReference getAppMasterReference() {
      return appMasterReference;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setAppMaster(e3ps.wfm.entity.KETWfmApprovalMaster the_appMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAppMasterReference(the_appMaster == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.wfm.entity.KETWfmApprovalMaster) the_appMaster));
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalHistory
    */
   public void setAppMasterReference(wt.fc.ObjectReference the_appMasterReference) throws wt.util.WTPropertyVetoException {
      appMasterReferenceValidate(the_appMasterReference);
      appMasterReference = (wt.fc.ObjectReference) the_appMasterReference;
   }
   void appMasterReferenceValidate(wt.fc.ObjectReference the_appMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_appMasterReference == null || the_appMasterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "appMasterReference") },
               new java.beans.PropertyChangeEvent(this, "appMasterReference", this.appMasterReference, appMasterReference));
      if (the_appMasterReference != null && the_appMasterReference.getReferencedClass() != null &&
            !e3ps.wfm.entity.KETWfmApprovalMaster.class.isAssignableFrom(the_appMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "appMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "appMasterReference", this.appMasterReference, appMasterReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 4596036005550384835L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( activityName );
      output.writeObject( appMasterReference );
      output.writeObject( comments );
      output.writeObject( completedDate );
      output.writeBoolean( conditionalAccept );
      output.writeObject( decision );
      output.writeInt( degree );
      output.writeObject( delegate );
      output.writeBoolean( last );
      output.writeObject( owner );
      output.writeBoolean( riskCheck );
      output.writeInt( seqNum );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.wfm.entity.KETWfmApprovalHistory) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "activityName", activityName );
      output.writeObject( "appMasterReference", appMasterReference, wt.fc.ObjectReference.class, true );
      output.setString( "comments", comments );
      output.setTimestamp( "completedDate", completedDate );
      output.setBoolean( "conditionalAccept", conditionalAccept );
      output.setString( "decision", decision );
      output.setInt( "degree", degree );
      output.setString( "delegate", delegate );
      output.setBoolean( "last", last );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setBoolean( "riskCheck", riskCheck );
      output.setInt( "seqNum", seqNum );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      activityName = input.getString( "activityName" );
      appMasterReference = (wt.fc.ObjectReference) input.readObject( "appMasterReference", appMasterReference, wt.fc.ObjectReference.class, true );
      comments = input.getString( "comments" );
      completedDate = input.getTimestamp( "completedDate" );
      conditionalAccept = input.getBoolean( "conditionalAccept" );
      decision = input.getString( "decision" );
      degree = input.getInt( "degree" );
      delegate = input.getString( "delegate" );
      last = input.getBoolean( "last" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      riskCheck = input.getBoolean( "riskCheck" );
      seqNum = input.getInt( "seqNum" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion4596036005550384835L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      activityName = (java.lang.String) input.readObject();
      appMasterReference = (wt.fc.ObjectReference) input.readObject();
      comments = (java.lang.String) input.readObject();
      completedDate = (java.sql.Timestamp) input.readObject();
      conditionalAccept = input.readBoolean();
      decision = (java.lang.String) input.readObject();
      degree = input.readInt();
      delegate = (java.lang.String) input.readObject();
      last = input.readBoolean();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      riskCheck = input.readBoolean();
      seqNum = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETWfmApprovalHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4596036005550384835L( input, readSerialVersionUID, superDone );
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
