package ext.ket.wfm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETTodoDelegateHistory extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.wfm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETTodoDelegateHistory.class.getName();

   /**
    * 사유
    *
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String REASON = "reason";
   static int REASON_UPPER_LIMIT = -1;
   java.lang.String reason;
   /**
    * 사유
    *
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public java.lang.String getReason() {
      return reason;
   }
   /**
    * 사유
    *
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setReason(java.lang.String reason) throws wt.util.WTPropertyVetoException {
      reasonValidate(reason);
      this.reason = reason;
   }
   void reasonValidate(java.lang.String reason) throws wt.util.WTPropertyVetoException {
      if (REASON_UPPER_LIMIT < 1) {
         try { REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REASON_UPPER_LIMIT = 4000; }
      }
      if (reason != null && !wt.fc.PersistenceHelper.checkStoredLength(reason.toString(), REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reason"), java.lang.String.valueOf(java.lang.Math.min(REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reason", this.reason, reason));
   }

   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String WORKITEM = "workitem";
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String WORKITEM_REFERENCE = "workitemReference";
   wt.fc.ObjectReference workitemReference;
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public wt.workflow.work.WorkItem getWorkitem() {
      return (workitemReference != null) ? (wt.workflow.work.WorkItem) workitemReference.getObject() : null;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public wt.fc.ObjectReference getWorkitemReference() {
      return workitemReference;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setWorkitem(wt.workflow.work.WorkItem the_workitem) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setWorkitemReference(the_workitem == null ? null : wt.fc.ObjectReference.newObjectReference((wt.workflow.work.WorkItem) the_workitem));
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setWorkitemReference(wt.fc.ObjectReference the_workitemReference) throws wt.util.WTPropertyVetoException {
      workitemReferenceValidate(the_workitemReference);
      workitemReference = (wt.fc.ObjectReference) the_workitemReference;
   }
   void workitemReferenceValidate(wt.fc.ObjectReference the_workitemReference) throws wt.util.WTPropertyVetoException {
      if (the_workitemReference == null || the_workitemReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workitemReference") },
               new java.beans.PropertyChangeEvent(this, "workitemReference", this.workitemReference, workitemReference));
      if (the_workitemReference != null && the_workitemReference.getReferencedClass() != null &&
            !wt.workflow.work.WorkItem.class.isAssignableFrom(the_workitemReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workitemReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "workitemReference", this.workitemReference, workitemReference));
   }

   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String PBO = "pbo";
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String PBO_REFERENCE = "pboReference";
   wt.fc.ObjectReference pboReference;
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public wt.fc.Persistable getPbo() {
      return (pboReference != null) ? (wt.fc.Persistable) pboReference.getObject() : null;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public wt.fc.ObjectReference getPboReference() {
      return pboReference;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setPbo(wt.fc.Persistable the_pbo) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPboReference(the_pbo == null ? null : wt.fc.ObjectReference.newObjectReference((wt.fc.Persistable) the_pbo));
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
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

   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String FROM_USER = "fromUser";
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String FROM_USER_REFERENCE = "fromUserReference";
   wt.fc.ObjectReference fromUserReference;
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public e3ps.groupware.company.People getFromUser() {
      return (fromUserReference != null) ? (e3ps.groupware.company.People) fromUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public wt.fc.ObjectReference getFromUserReference() {
      return fromUserReference;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setFromUser(e3ps.groupware.company.People the_fromUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFromUserReference(the_fromUser == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.People) the_fromUser));
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setFromUserReference(wt.fc.ObjectReference the_fromUserReference) throws wt.util.WTPropertyVetoException {
      fromUserReferenceValidate(the_fromUserReference);
      fromUserReference = (wt.fc.ObjectReference) the_fromUserReference;
   }
   void fromUserReferenceValidate(wt.fc.ObjectReference the_fromUserReference) throws wt.util.WTPropertyVetoException {
      if (the_fromUserReference == null || the_fromUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "fromUserReference") },
               new java.beans.PropertyChangeEvent(this, "fromUserReference", this.fromUserReference, fromUserReference));
      if (the_fromUserReference != null && the_fromUserReference.getReferencedClass() != null &&
            !e3ps.groupware.company.People.class.isAssignableFrom(the_fromUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "fromUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "fromUserReference", this.fromUserReference, fromUserReference));
   }

   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String TO_USER = "toUser";
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public static final java.lang.String TO_USER_REFERENCE = "toUserReference";
   wt.fc.ObjectReference toUserReference;
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public e3ps.groupware.company.People getToUser() {
      return (toUserReference != null) ? (e3ps.groupware.company.People) toUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public wt.fc.ObjectReference getToUserReference() {
      return toUserReference;
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setToUser(e3ps.groupware.company.People the_toUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setToUserReference(the_toUser == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.People) the_toUser));
   }
   /**
    * @see ext.ket.wfm.entity.KETTodoDelegateHistory
    */
   public void setToUserReference(wt.fc.ObjectReference the_toUserReference) throws wt.util.WTPropertyVetoException {
      toUserReferenceValidate(the_toUserReference);
      toUserReference = (wt.fc.ObjectReference) the_toUserReference;
   }
   void toUserReferenceValidate(wt.fc.ObjectReference the_toUserReference) throws wt.util.WTPropertyVetoException {
      if (the_toUserReference == null || the_toUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "toUserReference") },
               new java.beans.PropertyChangeEvent(this, "toUserReference", this.toUserReference, toUserReference));
      if (the_toUserReference != null && the_toUserReference.getReferencedClass() != null &&
            !e3ps.groupware.company.People.class.isAssignableFrom(the_toUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "toUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "toUserReference", this.toUserReference, toUserReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7673881437513154455L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( fromUserReference );
      output.writeObject( pboReference );
      output.writeObject( reason );
      output.writeObject( toUserReference );
      output.writeObject( workitemReference );
   }

   protected void super_writeExternal_KETTodoDelegateHistory(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.wfm.entity.KETTodoDelegateHistory) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETTodoDelegateHistory(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "fromUserReference", fromUserReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "pboReference", pboReference, wt.fc.ObjectReference.class, true );
      output.setString( "reason", reason );
      output.writeObject( "toUserReference", toUserReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "workitemReference", workitemReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      fromUserReference = (wt.fc.ObjectReference) input.readObject( "fromUserReference", fromUserReference, wt.fc.ObjectReference.class, true );
      pboReference = (wt.fc.ObjectReference) input.readObject( "pboReference", pboReference, wt.fc.ObjectReference.class, true );
      reason = input.getString( "reason" );
      toUserReference = (wt.fc.ObjectReference) input.readObject( "toUserReference", toUserReference, wt.fc.ObjectReference.class, true );
      workitemReference = (wt.fc.ObjectReference) input.readObject( "workitemReference", workitemReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion7673881437513154455L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      fromUserReference = (wt.fc.ObjectReference) input.readObject();
      pboReference = (wt.fc.ObjectReference) input.readObject();
      reason = (java.lang.String) input.readObject();
      toUserReference = (wt.fc.ObjectReference) input.readObject();
      workitemReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( KETTodoDelegateHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7673881437513154455L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETTodoDelegateHistory( _KETTodoDelegateHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
