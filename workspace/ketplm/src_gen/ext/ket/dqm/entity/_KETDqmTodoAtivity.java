package ext.ket.dqm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDqmTodoAtivity extends wt.enterprise.Managed implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dqm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDqmTodoAtivity.class.getName();

   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String TASK_NAME = "taskName";
   static int TASK_NAME_UPPER_LIMIT = -1;
   java.lang.String taskName;
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public java.lang.String getTaskName() {
      return taskName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setTaskName(java.lang.String taskName) throws wt.util.WTPropertyVetoException {
      taskNameValidate(taskName);
      this.taskName = taskName;
   }
   void taskNameValidate(java.lang.String taskName) throws wt.util.WTPropertyVetoException {
      if (TASK_NAME_UPPER_LIMIT < 1) {
         try { TASK_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_NAME_UPPER_LIMIT = 200; }
      }
      if (taskName != null && !wt.fc.PersistenceHelper.checkStoredLength(taskName.toString(), TASK_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskName"), java.lang.String.valueOf(java.lang.Math.min(TASK_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskName", this.taskName, taskName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String TASK_CODE = "taskCode";
   static int TASK_CODE_UPPER_LIMIT = -1;
   java.lang.String taskCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public java.lang.String getTaskCode() {
      return taskCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setTaskCode(java.lang.String taskCode) throws wt.util.WTPropertyVetoException {
      taskCodeValidate(taskCode);
      this.taskCode = taskCode;
   }
   void taskCodeValidate(java.lang.String taskCode) throws wt.util.WTPropertyVetoException {
      if (TASK_CODE_UPPER_LIMIT < 1) {
         try { TASK_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_CODE_UPPER_LIMIT = 200; }
      }
      if (taskCode != null && !wt.fc.PersistenceHelper.checkStoredLength(taskCode.toString(), TASK_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskCode"), java.lang.String.valueOf(java.lang.Math.min(TASK_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskCode", this.taskCode, taskCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String ACTION = "action";
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String ACTION_REFERENCE = "actionReference";
   wt.fc.ObjectReference actionReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public ext.ket.dqm.entity.KETDqmAction getAction() {
      return (actionReference != null) ? (ext.ket.dqm.entity.KETDqmAction) actionReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public wt.fc.ObjectReference getActionReference() {
      return actionReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setAction(ext.ket.dqm.entity.KETDqmAction the_action) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setActionReference(the_action == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.dqm.entity.KETDqmAction) the_action));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setActionReference(wt.fc.ObjectReference the_actionReference) throws wt.util.WTPropertyVetoException {
      actionReferenceValidate(the_actionReference);
      actionReference = (wt.fc.ObjectReference) the_actionReference;
   }
   void actionReferenceValidate(wt.fc.ObjectReference the_actionReference) throws wt.util.WTPropertyVetoException {
      if (the_actionReference != null && the_actionReference.getReferencedClass() != null &&
            !ext.ket.dqm.entity.KETDqmAction.class.isAssignableFrom(the_actionReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "actionReference", this.actionReference, actionReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String CLOSE = "close";
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String CLOSE_REFERENCE = "closeReference";
   wt.fc.ObjectReference closeReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public ext.ket.dqm.entity.KETDqmClose getClose() {
      return (closeReference != null) ? (ext.ket.dqm.entity.KETDqmClose) closeReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public wt.fc.ObjectReference getCloseReference() {
      return closeReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setClose(ext.ket.dqm.entity.KETDqmClose the_close) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCloseReference(the_close == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.dqm.entity.KETDqmClose) the_close));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setCloseReference(wt.fc.ObjectReference the_closeReference) throws wt.util.WTPropertyVetoException {
      closeReferenceValidate(the_closeReference);
      closeReference = (wt.fc.ObjectReference) the_closeReference;
   }
   void closeReferenceValidate(wt.fc.ObjectReference the_closeReference) throws wt.util.WTPropertyVetoException {
      if (the_closeReference != null && the_closeReference.getReferencedClass() != null &&
            !ext.ket.dqm.entity.KETDqmClose.class.isAssignableFrom(the_closeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "closeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "closeReference", this.closeReference, closeReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String HEADER = "header";
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public static final java.lang.String HEADER_REFERENCE = "headerReference";
   wt.fc.ObjectReference headerReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public ext.ket.dqm.entity.KETDqmHeader getHeader() {
      return (headerReference != null) ? (ext.ket.dqm.entity.KETDqmHeader) headerReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public wt.fc.ObjectReference getHeaderReference() {
      return headerReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setHeader(ext.ket.dqm.entity.KETDqmHeader the_header) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setHeaderReference(the_header == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.dqm.entity.KETDqmHeader) the_header));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmTodoAtivity
    */
   public void setHeaderReference(wt.fc.ObjectReference the_headerReference) throws wt.util.WTPropertyVetoException {
      headerReferenceValidate(the_headerReference);
      headerReference = (wt.fc.ObjectReference) the_headerReference;
   }
   void headerReferenceValidate(wt.fc.ObjectReference the_headerReference) throws wt.util.WTPropertyVetoException {
      if (the_headerReference != null && the_headerReference.getReferencedClass() != null &&
            !ext.ket.dqm.entity.KETDqmHeader.class.isAssignableFrom(the_headerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "headerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "headerReference", this.headerReference, headerReference));
   }

   /**
    * Derived from {@link wt.inf.container.WTContainerRef#getName()}
    *
    * @see wt.inf.container.WTContained
    */
   public java.lang.String getContainerName() {
      try { return (java.lang.String) ((wt.inf.container.WTContainerRef) getContainerReference()).getName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   wt.inf.container.WTContainerRef containerReference;
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainer getContainer() {
      return (containerReference != null) ? (wt.inf.container.WTContainer) containerReference.getObject() : null;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainerRef getContainerReference() {
      return containerReference;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainer(wt.inf.container.WTContainer the_container) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setContainerReference(the_container == null ? null : wt.inf.container.WTContainerRef.newWTContainerRef((wt.inf.container.WTContainer) the_container));
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainerReference(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      containerReferenceValidate(the_containerReference);
      containerReference = (wt.inf.container.WTContainerRef) the_containerReference;
   }
   void containerReferenceValidate(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      if (the_containerReference == null || the_containerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference") },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
      if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
            !wt.inf.container.WTContainer.class.isAssignableFrom(the_containerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference"), "WTContainerRef" },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 9100682077075080543L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( actionReference );
      output.writeObject( closeReference );
      output.writeObject( containerReference );
      output.writeObject( headerReference );
      output.writeObject( taskCode );
      output.writeObject( taskName );
   }

   protected void super_writeExternal_KETDqmTodoAtivity(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dqm.entity.KETDqmTodoAtivity) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDqmTodoAtivity(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "actionReference", actionReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "closeReference", closeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.writeObject( "headerReference", headerReference, wt.fc.ObjectReference.class, true );
      output.setString( "taskCode", taskCode );
      output.setString( "taskName", taskName );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      actionReference = (wt.fc.ObjectReference) input.readObject( "actionReference", actionReference, wt.fc.ObjectReference.class, true );
      closeReference = (wt.fc.ObjectReference) input.readObject( "closeReference", closeReference, wt.fc.ObjectReference.class, true );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      headerReference = (wt.fc.ObjectReference) input.readObject( "headerReference", headerReference, wt.fc.ObjectReference.class, true );
      taskCode = input.getString( "taskCode" );
      taskName = input.getString( "taskName" );
   }

   boolean readVersion9100682077075080543L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      actionReference = (wt.fc.ObjectReference) input.readObject();
      closeReference = (wt.fc.ObjectReference) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      headerReference = (wt.fc.ObjectReference) input.readObject();
      taskCode = (java.lang.String) input.readObject();
      taskName = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDqmTodoAtivity thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion9100682077075080543L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDqmTodoAtivity( _KETDqmTodoAtivity thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
