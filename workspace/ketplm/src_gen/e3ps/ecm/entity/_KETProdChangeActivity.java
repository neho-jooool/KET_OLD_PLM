package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProdChangeActivity extends wt.enterprise.Managed implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProdChangeActivity.class.getName();

   /**
    * OOTB?????? Lifecycle.state ?? ???? ?? ??? state
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String ACTIVITY_STATUS = "activityStatus";
   static int ACTIVITY_STATUS_UPPER_LIMIT = -1;
   java.lang.String activityStatus;
   /**
    * OOTB?????? Lifecycle.state ?? ???? ?? ??? state
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.lang.String getActivityStatus() {
      return activityStatus;
   }
   /**
    * OOTB?????? Lifecycle.state ?? ???? ?? ??? state
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setActivityStatus(java.lang.String activityStatus) throws wt.util.WTPropertyVetoException {
      activityStatusValidate(activityStatus);
      this.activityStatus = activityStatus;
   }
   void activityStatusValidate(java.lang.String activityStatus) throws wt.util.WTPropertyVetoException {
      if (ACTIVITY_STATUS_UPPER_LIMIT < 1) {
         try { ACTIVITY_STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activityStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVITY_STATUS_UPPER_LIMIT = 200; }
      }
      if (activityStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(activityStatus.toString(), ACTIVITY_STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityStatus"), java.lang.String.valueOf(java.lang.Math.min(ACTIVITY_STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activityStatus", this.activityStatus, activityStatus));
   }

   /**
    * 1:????, 2:BOM, 3:????, 4:???
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String ACTIVITY_TYPE = "activityType";
   static int ACTIVITY_TYPE_UPPER_LIMIT = -1;
   java.lang.String activityType;
   /**
    * 1:????, 2:BOM, 3:????, 4:???
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.lang.String getActivityType() {
      return activityType;
   }
   /**
    * 1:????, 2:BOM, 3:????, 4:???
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setActivityType(java.lang.String activityType) throws wt.util.WTPropertyVetoException {
      activityTypeValidate(activityType);
      this.activityType = activityType;
   }
   void activityTypeValidate(java.lang.String activityType) throws wt.util.WTPropertyVetoException {
      if (ACTIVITY_TYPE_UPPER_LIMIT < 1) {
         try { ACTIVITY_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activityType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVITY_TYPE_UPPER_LIMIT = 200; }
      }
      if (activityType != null && !wt.fc.PersistenceHelper.checkStoredLength(activityType.toString(), ACTIVITY_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityType"), java.lang.String.valueOf(java.lang.Math.min(ACTIVITY_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activityType", this.activityType, activityType));
   }

   /**
    * ?????, 
???? ??½? ????? ???
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String CUSTOM_CODE = "customCode";
   static int CUSTOM_CODE_UPPER_LIMIT = -1;
   java.lang.String customCode;
   /**
    * ?????, 
???? ??½? ????? ???
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.lang.String getCustomCode() {
      return customCode;
   }
   /**
    * ?????, 
???? ??½? ????? ???
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setCustomCode(java.lang.String customCode) throws wt.util.WTPropertyVetoException {
      customCodeValidate(customCode);
      this.customCode = customCode;
   }
   void customCodeValidate(java.lang.String customCode) throws wt.util.WTPropertyVetoException {
      if (CUSTOM_CODE_UPPER_LIMIT < 1) {
         try { CUSTOM_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOM_CODE_UPPER_LIMIT = 200; }
      }
      if (customCode != null && !wt.fc.PersistenceHelper.checkStoredLength(customCode.toString(), CUSTOM_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customCode"), java.lang.String.valueOf(java.lang.Math.min(CUSTOM_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customCode", this.customCode, customCode));
   }

   /**
    * ???? ??? ?????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String CUSTOM_NAME = "customName";
   static int CUSTOM_NAME_UPPER_LIMIT = -1;
   java.lang.String customName;
   /**
    * ???? ??? ?????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.lang.String getCustomName() {
      return customName;
   }
   /**
    * ???? ??? ?????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setCustomName(java.lang.String customName) throws wt.util.WTPropertyVetoException {
      customNameValidate(customName);
      this.customName = customName;
   }
   void customNameValidate(java.lang.String customName) throws wt.util.WTPropertyVetoException {
      if (CUSTOM_NAME_UPPER_LIMIT < 1) {
         try { CUSTOM_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOM_NAME_UPPER_LIMIT = 200; }
      }
      if (customName != null && !wt.fc.PersistenceHelper.checkStoredLength(customName.toString(), CUSTOM_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customName"), java.lang.String.valueOf(java.lang.Math.min(CUSTOM_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customName", this.customName, customName));
   }

   /**
    * ????? WTUser.ida2a2
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String WORKER_ID = "workerId";
   static int WORKER_ID_UPPER_LIMIT = -1;
   java.lang.String workerId;
   /**
    * ????? WTUser.ida2a2
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.lang.String getWorkerId() {
      return workerId;
   }
   /**
    * ????? WTUser.ida2a2
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setWorkerId(java.lang.String workerId) throws wt.util.WTPropertyVetoException {
      workerIdValidate(workerId);
      this.workerId = workerId;
   }
   void workerIdValidate(java.lang.String workerId) throws wt.util.WTPropertyVetoException {
      if (WORKER_ID_UPPER_LIMIT < 1) {
         try { WORKER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workerId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORKER_ID_UPPER_LIMIT = 200; }
      }
      if (workerId != null && !wt.fc.PersistenceHelper.checkStoredLength(workerId.toString(), WORKER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workerId"), java.lang.String.valueOf(java.lang.Math.min(WORKER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workerId", this.workerId, workerId));
   }

   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String COMPLETE_DATE = "completeDate";
   java.sql.Timestamp completeDate;
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.sql.Timestamp getCompleteDate() {
      return completeDate;
   }
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setCompleteDate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
      completeDateValidate(completeDate);
      this.completeDate = completeDate;
   }
   void completeDateValidate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String COMPLETE_REQUEST_DATE = "completeRequestDate";
   java.sql.Timestamp completeRequestDate;
   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.sql.Timestamp getCompleteRequestDate() {
      return completeRequestDate;
   }
   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setCompleteRequestDate(java.sql.Timestamp completeRequestDate) throws wt.util.WTPropertyVetoException {
      completeRequestDateValidate(completeRequestDate);
      this.completeRequestDate = completeRequestDate;
   }
   void completeRequestDateValidate(java.sql.Timestamp completeRequestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ?????????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String RECEIVE_CONFIRMED_DATE = "receiveConfirmedDate";
   java.sql.Timestamp receiveConfirmedDate;
   /**
    * ?????????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.sql.Timestamp getReceiveConfirmedDate() {
      return receiveConfirmedDate;
   }
   /**
    * ?????????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setReceiveConfirmedDate(java.sql.Timestamp receiveConfirmedDate) throws wt.util.WTPropertyVetoException {
      receiveConfirmedDateValidate(receiveConfirmedDate);
      this.receiveConfirmedDate = receiveConfirmedDate;
   }
   void receiveConfirmedDateValidate(java.sql.Timestamp receiveConfirmedDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String ACTIVITY_TYPE_DESC = "activityTypeDesc";
   static int ACTIVITY_TYPE_DESC_UPPER_LIMIT = -1;
   java.lang.String activityTypeDesc;
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.lang.String getActivityTypeDesc() {
      return activityTypeDesc;
   }
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setActivityTypeDesc(java.lang.String activityTypeDesc) throws wt.util.WTPropertyVetoException {
      activityTypeDescValidate(activityTypeDesc);
      this.activityTypeDesc = activityTypeDesc;
   }
   void activityTypeDescValidate(java.lang.String activityTypeDesc) throws wt.util.WTPropertyVetoException {
      if (ACTIVITY_TYPE_DESC_UPPER_LIMIT < 1) {
         try { ACTIVITY_TYPE_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activityTypeDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVITY_TYPE_DESC_UPPER_LIMIT = 200; }
      }
      if (activityTypeDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(activityTypeDesc.toString(), ACTIVITY_TYPE_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityTypeDesc"), java.lang.String.valueOf(java.lang.Math.min(ACTIVITY_TYPE_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activityTypeDesc", this.activityTypeDesc, activityTypeDesc));
   }

   /**
    * ????(ToDo???? ???? ????)
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String ACTIVITY_BACK_DESC = "activityBackDesc";
   static int ACTIVITY_BACK_DESC_UPPER_LIMIT = -1;
   java.lang.String activityBackDesc;
   /**
    * ????(ToDo???? ???? ????)
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public java.lang.String getActivityBackDesc() {
      return activityBackDesc;
   }
   /**
    * ????(ToDo???? ???? ????)
    *
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setActivityBackDesc(java.lang.String activityBackDesc) throws wt.util.WTPropertyVetoException {
      activityBackDescValidate(activityBackDesc);
      this.activityBackDesc = activityBackDesc;
   }
   void activityBackDescValidate(java.lang.String activityBackDesc) throws wt.util.WTPropertyVetoException {
      if (ACTIVITY_BACK_DESC_UPPER_LIMIT < 1) {
         try { ACTIVITY_BACK_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activityBackDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVITY_BACK_DESC_UPPER_LIMIT = 200; }
      }
      if (activityBackDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(activityBackDesc.toString(), ACTIVITY_BACK_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityBackDesc"), java.lang.String.valueOf(java.lang.Math.min(ACTIVITY_BACK_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activityBackDesc", this.activityBackDesc, activityBackDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String PROD_ECO = "prodECO";
   /**
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public static final java.lang.String PROD_ECOREFERENCE = "prodECOReference";
   wt.fc.ObjectReference prodECOReference;
   /**
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public e3ps.ecm.entity.KETProdChangeOrder getProdECO() {
      return (prodECOReference != null) ? (e3ps.ecm.entity.KETProdChangeOrder) prodECOReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public wt.fc.ObjectReference getProdECOReference() {
      return prodECOReference;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setProdECO(e3ps.ecm.entity.KETProdChangeOrder the_prodECO) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProdECOReference(the_prodECO == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.ecm.entity.KETProdChangeOrder) the_prodECO));
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeActivity
    */
   public void setProdECOReference(wt.fc.ObjectReference the_prodECOReference) throws wt.util.WTPropertyVetoException {
      prodECOReferenceValidate(the_prodECOReference);
      prodECOReference = (wt.fc.ObjectReference) the_prodECOReference;
   }
   void prodECOReferenceValidate(wt.fc.ObjectReference the_prodECOReference) throws wt.util.WTPropertyVetoException {
      if (the_prodECOReference == null || the_prodECOReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodECOReference") },
               new java.beans.PropertyChangeEvent(this, "prodECOReference", this.prodECOReference, prodECOReference));
      if (the_prodECOReference != null && the_prodECOReference.getReferencedClass() != null &&
            !e3ps.ecm.entity.KETProdChangeOrder.class.isAssignableFrom(the_prodECOReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodECOReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "prodECOReference", this.prodECOReference, prodECOReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1609055731235914829L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( activityBackDesc );
      output.writeObject( activityStatus );
      output.writeObject( activityType );
      output.writeObject( activityTypeDesc );
      output.writeObject( completeDate );
      output.writeObject( completeRequestDate );
      output.writeObject( containerReference );
      output.writeObject( customCode );
      output.writeObject( customName );
      output.writeObject( prodECOReference );
      output.writeObject( receiveConfirmedDate );
      output.writeObject( workerId );
   }

   protected void super_writeExternal_KETProdChangeActivity(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETProdChangeActivity) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProdChangeActivity(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "activityBackDesc", activityBackDesc );
      output.setString( "activityStatus", activityStatus );
      output.setString( "activityType", activityType );
      output.setString( "activityTypeDesc", activityTypeDesc );
      output.setTimestamp( "completeDate", completeDate );
      output.setTimestamp( "completeRequestDate", completeRequestDate );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "customCode", customCode );
      output.setString( "customName", customName );
      output.writeObject( "prodECOReference", prodECOReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "receiveConfirmedDate", receiveConfirmedDate );
      output.setString( "workerId", workerId );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      activityBackDesc = input.getString( "activityBackDesc" );
      activityStatus = input.getString( "activityStatus" );
      activityType = input.getString( "activityType" );
      activityTypeDesc = input.getString( "activityTypeDesc" );
      completeDate = input.getTimestamp( "completeDate" );
      completeRequestDate = input.getTimestamp( "completeRequestDate" );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      customCode = input.getString( "customCode" );
      customName = input.getString( "customName" );
      prodECOReference = (wt.fc.ObjectReference) input.readObject( "prodECOReference", prodECOReference, wt.fc.ObjectReference.class, true );
      receiveConfirmedDate = input.getTimestamp( "receiveConfirmedDate" );
      workerId = input.getString( "workerId" );
   }

   boolean readVersion1609055731235914829L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      activityBackDesc = (java.lang.String) input.readObject();
      activityStatus = (java.lang.String) input.readObject();
      activityType = (java.lang.String) input.readObject();
      activityTypeDesc = (java.lang.String) input.readObject();
      completeDate = (java.sql.Timestamp) input.readObject();
      completeRequestDate = (java.sql.Timestamp) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      customCode = (java.lang.String) input.readObject();
      customName = (java.lang.String) input.readObject();
      prodECOReference = (wt.fc.ObjectReference) input.readObject();
      receiveConfirmedDate = (java.sql.Timestamp) input.readObject();
      workerId = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProdChangeActivity thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1609055731235914829L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProdChangeActivity( _KETProdChangeActivity thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
