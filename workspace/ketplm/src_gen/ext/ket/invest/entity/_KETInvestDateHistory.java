package ext.ket.invest.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETInvestDateHistory implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.invest.entity.entityResource";
   static final java.lang.String CLASSNAME = KETInvestDateHistory.class.getName();

   /**
    * 완료요청일자
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public static final java.lang.String ORG_REQUEST_DATE = "orgRequestDate";
   java.sql.Timestamp orgRequestDate;
   /**
    * 완료요청일자
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public java.sql.Timestamp getOrgRequestDate() {
      return orgRequestDate;
   }
   /**
    * 완료요청일자
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public void setOrgRequestDate(java.sql.Timestamp orgRequestDate) throws wt.util.WTPropertyVetoException {
      orgRequestDateValidate(orgRequestDate);
      this.orgRequestDate = orgRequestDate;
   }
   void orgRequestDateValidate(java.sql.Timestamp orgRequestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 완료일자
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public static final java.lang.String CHANGE_REQUEST_DATE = "changeRequestDate";
   java.sql.Timestamp changeRequestDate;
   /**
    * 완료일자
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public java.sql.Timestamp getChangeRequestDate() {
      return changeRequestDate;
   }
   /**
    * 완료일자
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public void setChangeRequestDate(java.sql.Timestamp changeRequestDate) throws wt.util.WTPropertyVetoException {
      changeRequestDateValidate(changeRequestDate);
      this.changeRequestDate = changeRequestDate;
   }
   void changeRequestDateValidate(java.sql.Timestamp changeRequestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 변경사유
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public static final java.lang.String CHANGE_HISTORY = "changeHistory";
   static int CHANGE_HISTORY_UPPER_LIMIT = -1;
   java.lang.String changeHistory;
   /**
    * 변경사유
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public java.lang.String getChangeHistory() {
      return changeHistory;
   }
   /**
    * 변경사유
    *
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public void setChangeHistory(java.lang.String changeHistory) throws wt.util.WTPropertyVetoException {
      changeHistoryValidate(changeHistory);
      this.changeHistory = changeHistory;
   }
   void changeHistoryValidate(java.lang.String changeHistory) throws wt.util.WTPropertyVetoException {
      if (CHANGE_HISTORY_UPPER_LIMIT < 1) {
         try { CHANGE_HISTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeHistory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_HISTORY_UPPER_LIMIT = 4000; }
      }
      if (changeHistory != null && !wt.fc.PersistenceHelper.checkStoredLength(changeHistory.toString(), CHANGE_HISTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeHistory"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_HISTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeHistory", this.changeHistory, changeHistory));
   }

   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public static final java.lang.String WORKER = "worker";
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public static final java.lang.String WORKER_REFERENCE = "workerReference";
   wt.fc.ObjectReference workerReference;
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public wt.org.WTUser getWorker() {
      return (workerReference != null) ? (wt.org.WTUser) workerReference.getObject() : null;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public wt.fc.ObjectReference getWorkerReference() {
      return workerReference;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public void setWorker(wt.org.WTUser the_worker) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setWorkerReference(the_worker == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_worker));
   }
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public void setWorkerReference(wt.fc.ObjectReference the_workerReference) throws wt.util.WTPropertyVetoException {
      workerReferenceValidate(the_workerReference);
      workerReference = (wt.fc.ObjectReference) the_workerReference;
   }
   void workerReferenceValidate(wt.fc.ObjectReference the_workerReference) throws wt.util.WTPropertyVetoException {
      if (the_workerReference == null || the_workerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workerReference") },
               new java.beans.PropertyChangeEvent(this, "workerReference", this.workerReference, workerReference));
      if (the_workerReference != null && the_workerReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_workerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "workerReference", this.workerReference, workerReference));
   }

   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public static final java.lang.String INVEST_MASTER = "investMaster";
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public static final java.lang.String INVEST_MASTER_REFERENCE = "investMasterReference";
   wt.fc.ObjectReference investMasterReference;
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public ext.ket.invest.entity.KETInvestMaster getInvestMaster() {
      return (investMasterReference != null) ? (ext.ket.invest.entity.KETInvestMaster) investMasterReference.getObject() : null;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public wt.fc.ObjectReference getInvestMasterReference() {
      return investMasterReference;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public void setInvestMaster(ext.ket.invest.entity.KETInvestMaster the_investMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setInvestMasterReference(the_investMaster == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.invest.entity.KETInvestMaster) the_investMaster));
   }
   /**
    * @see ext.ket.invest.entity.KETInvestDateHistory
    */
   public void setInvestMasterReference(wt.fc.ObjectReference the_investMasterReference) throws wt.util.WTPropertyVetoException {
      investMasterReferenceValidate(the_investMasterReference);
      investMasterReference = (wt.fc.ObjectReference) the_investMasterReference;
   }
   void investMasterReferenceValidate(wt.fc.ObjectReference the_investMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_investMasterReference == null || the_investMasterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investMasterReference") },
               new java.beans.PropertyChangeEvent(this, "investMasterReference", this.investMasterReference, investMasterReference));
      if (the_investMasterReference != null && the_investMasterReference.getReferencedClass() != null &&
            !ext.ket.invest.entity.KETInvestMaster.class.isAssignableFrom(the_investMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "investMasterReference", this.investMasterReference, investMasterReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 9123376198589164513L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( changeHistory );
      output.writeObject( changeRequestDate );
      output.writeObject( investMasterReference );
      output.writeObject( orgRequestDate );
      output.writeObject( owner );
      output.writeObject( thePersistInfo );
      output.writeObject( workerReference );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.invest.entity.KETInvestDateHistory) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "changeHistory", changeHistory );
      output.setTimestamp( "changeRequestDate", changeRequestDate );
      output.writeObject( "investMasterReference", investMasterReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "orgRequestDate", orgRequestDate );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.writeObject( "workerReference", workerReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      changeHistory = input.getString( "changeHistory" );
      changeRequestDate = input.getTimestamp( "changeRequestDate" );
      investMasterReference = (wt.fc.ObjectReference) input.readObject( "investMasterReference", investMasterReference, wt.fc.ObjectReference.class, true );
      orgRequestDate = input.getTimestamp( "orgRequestDate" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      workerReference = (wt.fc.ObjectReference) input.readObject( "workerReference", workerReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion9123376198589164513L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      changeHistory = (java.lang.String) input.readObject();
      changeRequestDate = (java.sql.Timestamp) input.readObject();
      investMasterReference = (wt.fc.ObjectReference) input.readObject();
      orgRequestDate = (java.sql.Timestamp) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      workerReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( KETInvestDateHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion9123376198589164513L( input, readSerialVersionUID, superDone );
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
