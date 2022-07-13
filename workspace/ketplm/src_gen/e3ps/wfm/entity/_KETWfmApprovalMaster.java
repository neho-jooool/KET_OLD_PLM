package e3ps.wfm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETWfmApprovalMaster implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.wfm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETWfmApprovalMaster.class.getName();

   /**
    * 합의유형
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public static final java.lang.String AGREEMENT_TYPE = "agreementType";
   static int AGREEMENT_TYPE_UPPER_LIMIT = -1;
   java.lang.String agreementType;
   /**
    * 합의유형
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public java.lang.String getAgreementType() {
      return agreementType;
   }
   /**
    * 합의유형
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public void setAgreementType(java.lang.String agreementType) throws wt.util.WTPropertyVetoException {
      agreementTypeValidate(agreementType);
      this.agreementType = agreementType;
   }
   void agreementTypeValidate(java.lang.String agreementType) throws wt.util.WTPropertyVetoException {
      if (AGREEMENT_TYPE_UPPER_LIMIT < 1) {
         try { AGREEMENT_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("agreementType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AGREEMENT_TYPE_UPPER_LIMIT = 30; }
      }
      if (agreementType != null && !wt.fc.PersistenceHelper.checkStoredLength(agreementType.toString(), AGREEMENT_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "agreementType"), java.lang.String.valueOf(java.lang.Math.min(AGREEMENT_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "agreementType", this.agreementType, agreementType));
   }

   /**
    * 결재요청의견
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public static final java.lang.String COMMENTS = "comments";
   static int COMMENTS_UPPER_LIMIT = -1;
   java.lang.String comments;
   /**
    * 결재요청의견
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public java.lang.String getComments() {
      return comments;
   }
   /**
    * 결재요청의견
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
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
    * 임시저장 = 0≪≫결재시작 = 1
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public static final java.lang.String START_FLAG = "startFlag";
   boolean startFlag = false;
   /**
    * 임시저장 = 0≪≫결재시작 = 1
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public boolean isStartFlag() {
      return startFlag;
   }
   /**
    * 임시저장 = 0≪≫결재시작 = 1
    *
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public void setStartFlag(boolean startFlag) throws wt.util.WTPropertyVetoException {
      startFlagValidate(startFlag);
      this.startFlag = startFlag;
   }
   void startFlagValidate(boolean startFlag) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public static final java.lang.String BUSINESSOBJECT_REF = "businessobjectRef";
   wt.fc.PersistentReference businessobjectRef;
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public wt.fc.PersistentReference getBusinessobjectRef() {
      return businessobjectRef;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public void setBusinessobjectRef(wt.fc.PersistentReference businessobjectRef) throws wt.util.WTPropertyVetoException {
      businessobjectRefValidate(businessobjectRef);
      this.businessobjectRef = businessobjectRef;
   }
   void businessobjectRefValidate(wt.fc.PersistentReference businessobjectRef) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public static final java.lang.String COMPLETED_DATE = "completedDate";
   java.sql.Timestamp completedDate;
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public java.sql.Timestamp getCompletedDate() {
      return completedDate;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public void setCompletedDate(java.sql.Timestamp completedDate) throws wt.util.WTPropertyVetoException {
      completedDateValidate(completedDate);
      this.completedDate = completedDate;
   }
   void completedDateValidate(java.sql.Timestamp completedDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public static final java.lang.String PBO = "pbo";
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public static final java.lang.String PBO_REFERENCE = "pboReference";
   wt.fc.ObjectReference pboReference;
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public wt.fc.Persistable getPbo() {
      return (pboReference != null) ? (wt.fc.Persistable) pboReference.getObject() : null;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public wt.fc.ObjectReference getPboReference() {
      return pboReference;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public void setPbo(wt.fc.Persistable the_pbo) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPboReference(the_pbo == null ? null : wt.fc.ObjectReference.newObjectReference((wt.fc.Persistable) the_pbo));
   }
   /**
    * @see e3ps.wfm.entity.KETWfmApprovalMaster
    */
   public void setPboReference(wt.fc.ObjectReference the_pboReference) throws wt.util.WTPropertyVetoException {
      pboReferenceValidate(the_pboReference);
      pboReference = (wt.fc.ObjectReference) the_pboReference;
   }
   void pboReferenceValidate(wt.fc.ObjectReference the_pboReference) throws wt.util.WTPropertyVetoException {
      if (the_pboReference == null || the_pboReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pboReference") },
               new java.beans.PropertyChangeEvent(this, "pboReference", this.pboReference, pboReference));
      if (the_pboReference != null && the_pboReference.getReferencedClass() != null &&
            !wt.fc.Persistable.class.isAssignableFrom(the_pboReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pboReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "pboReference", this.pboReference, pboReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 5675688361247368669L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( agreementType );
      output.writeObject( businessobjectRef );
      output.writeObject( comments );
      output.writeObject( completedDate );
      output.writeObject( owner );
      output.writeObject( pboReference );
      output.writeBoolean( startFlag );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.wfm.entity.KETWfmApprovalMaster) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "agreementType", agreementType );
      output.writeObject( "businessobjectRef", businessobjectRef, wt.fc.PersistentReference.class, true );
      output.setString( "comments", comments );
      output.setTimestamp( "completedDate", completedDate );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "pboReference", pboReference, wt.fc.ObjectReference.class, true );
      output.setBoolean( "startFlag", startFlag );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      agreementType = input.getString( "agreementType" );
      businessobjectRef = (wt.fc.PersistentReference) input.readObject( "businessobjectRef", businessobjectRef, wt.fc.PersistentReference.class, true );
      comments = input.getString( "comments" );
      completedDate = input.getTimestamp( "completedDate" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      pboReference = (wt.fc.ObjectReference) input.readObject( "pboReference", pboReference, wt.fc.ObjectReference.class, true );
      startFlag = input.getBoolean( "startFlag" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion5675688361247368669L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      agreementType = (java.lang.String) input.readObject();
      businessobjectRef = (wt.fc.PersistentReference) input.readObject();
      comments = (java.lang.String) input.readObject();
      completedDate = (java.sql.Timestamp) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      pboReference = (wt.fc.ObjectReference) input.readObject();
      startFlag = input.readBoolean();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETWfmApprovalMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5675688361247368669L( input, readSerialVersionUID, superDone );
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
