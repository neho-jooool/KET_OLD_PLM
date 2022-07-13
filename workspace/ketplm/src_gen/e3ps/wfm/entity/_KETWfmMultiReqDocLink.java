package e3ps.wfm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETWfmMultiReqDocLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.wfm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETWfmMultiReqDocLink.class.getName();

   /**
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public static final java.lang.String TASKOID = "taskoid";
   static int TASKOID_UPPER_LIMIT = -1;
   java.lang.String taskoid;
   /**
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public java.lang.String getTaskoid() {
      return taskoid;
   }
   /**
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public void setTaskoid(java.lang.String taskoid) throws wt.util.WTPropertyVetoException {
      taskoidValidate(taskoid);
      this.taskoid = taskoid;
   }
   void taskoidValidate(java.lang.String taskoid) throws wt.util.WTPropertyVetoException {
      if (TASKOID_UPPER_LIMIT < 1) {
         try { TASKOID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskoid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASKOID_UPPER_LIMIT = 200; }
      }
      if (taskoid != null && !wt.fc.PersistenceHelper.checkStoredLength(taskoid.toString(), TASKOID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskoid"), java.lang.String.valueOf(java.lang.Math.min(TASKOID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskoid", this.taskoid, taskoid));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public static final java.lang.String REQUEST_ROLE = "request";
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public e3ps.wfm.entity.KETWfmMultiApprovalRequest getRequest() {
      return (e3ps.wfm.entity.KETWfmMultiApprovalRequest) getRoleAObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public void setRequest(e3ps.wfm.entity.KETWfmMultiApprovalRequest the_request) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_request);
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public static final java.lang.String DOC_ROLE = "doc";
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public wt.doc.WTDocument getDoc() {
      return (wt.doc.WTDocument) getRoleBObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqDocLink
    */
   public void setDoc(wt.doc.WTDocument the_doc) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_doc);
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

   public static final long EXTERNALIZATION_VERSION_UID = -6377518729255937836L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( taskoid );
   }

   protected void super_writeExternal_KETWfmMultiReqDocLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.wfm.entity.KETWfmMultiReqDocLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETWfmMultiReqDocLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "taskoid", taskoid );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      taskoid = input.getString( "taskoid" );
   }

   boolean readVersion_6377518729255937836L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      taskoid = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETWfmMultiReqDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6377518729255937836L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETWfmMultiReqDocLink( _KETWfmMultiReqDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
