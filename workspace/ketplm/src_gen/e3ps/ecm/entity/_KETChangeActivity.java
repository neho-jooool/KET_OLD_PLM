package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETChangeActivity implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETChangeActivity.class.getName();

   /**
    * NumberCode?? ???(ECA ???)
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String ACTIVITY_CODE = "activityCode";
   static int ACTIVITY_CODE_UPPER_LIMIT = -1;
   java.lang.String activityCode;
   /**
    * NumberCode?? ???(ECA ???)
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public java.lang.String getActivityCode() {
      return activityCode;
   }
   /**
    * NumberCode?? ???(ECA ???)
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setActivityCode(java.lang.String activityCode) throws wt.util.WTPropertyVetoException {
      activityCodeValidate(activityCode);
      this.activityCode = activityCode;
   }
   void activityCodeValidate(java.lang.String activityCode) throws wt.util.WTPropertyVetoException {
      if (ACTIVITY_CODE_UPPER_LIMIT < 1) {
         try { ACTIVITY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activityCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVITY_CODE_UPPER_LIMIT = 200; }
      }
      if (activityCode != null && !wt.fc.PersistenceHelper.checkStoredLength(activityCode.toString(), ACTIVITY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityCode"), java.lang.String.valueOf(java.lang.Math.min(ACTIVITY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activityCode", this.activityCode, activityCode));
   }

   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String CHARGE_NAME = "chargeName";
   static int CHARGE_NAME_UPPER_LIMIT = -1;
   java.lang.String chargeName;
   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public java.lang.String getChargeName() {
      return chargeName;
   }
   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setChargeName(java.lang.String chargeName) throws wt.util.WTPropertyVetoException {
      chargeNameValidate(chargeName);
      this.chargeName = chargeName;
   }
   void chargeNameValidate(java.lang.String chargeName) throws wt.util.WTPropertyVetoException {
      if (CHARGE_NAME_UPPER_LIMIT < 1) {
         try { CHARGE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("chargeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHARGE_NAME_UPPER_LIMIT = 200; }
      }
      if (chargeName != null && !wt.fc.PersistenceHelper.checkStoredLength(chargeName.toString(), CHARGE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chargeName"), java.lang.String.valueOf(java.lang.Math.min(CHARGE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "chargeName", this.chargeName, chargeName));
   }

   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String COMPLETE_REQUEST_DATE = "completeRequestDate";
   java.sql.Timestamp completeRequestDate;
   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public java.sql.Timestamp getCompleteRequestDate() {
      return completeRequestDate;
   }
   /**
    * ???????
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setCompleteRequestDate(java.sql.Timestamp completeRequestDate) throws wt.util.WTPropertyVetoException {
      completeRequestDateValidate(completeRequestDate);
      this.completeRequestDate = completeRequestDate;
   }
   void completeRequestDateValidate(java.sql.Timestamp completeRequestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ????? ????? Activity Type : 'DOC', 'ACT'
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String CUSTOM_TYPE = "customType";
   static int CUSTOM_TYPE_UPPER_LIMIT = -1;
   java.lang.String customType;
   /**
    * ????? ????? Activity Type : 'DOC', 'ACT'
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public java.lang.String getCustomType() {
      return customType;
   }
   /**
    * ????? ????? Activity Type : 'DOC', 'ACT'
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setCustomType(java.lang.String customType) throws wt.util.WTPropertyVetoException {
      customTypeValidate(customType);
      this.customType = customType;
   }
   void customTypeValidate(java.lang.String customType) throws wt.util.WTPropertyVetoException {
      if (CUSTOM_TYPE_UPPER_LIMIT < 1) {
         try { CUSTOM_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOM_TYPE_UPPER_LIMIT = 200; }
      }
      if (customType != null && !wt.fc.PersistenceHelper.checkStoredLength(customType.toString(), CUSTOM_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customType"), java.lang.String.valueOf(java.lang.Math.min(CUSTOM_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customType", this.customType, customType));
   }

   /**
    * ????? ????? Activity Code
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String CUSTOM_CODE = "customCode";
   static int CUSTOM_CODE_UPPER_LIMIT = -1;
   java.lang.String customCode;
   /**
    * ????? ????? Activity Code
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public java.lang.String getCustomCode() {
      return customCode;
   }
   /**
    * ????? ????? Activity Code
    *
    * @see e3ps.ecm.entity.KETChangeActivity
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
    * ????? ????? Activity Name
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String CUSTOM_NAME = "customName";
   static int CUSTOM_NAME_UPPER_LIMIT = -1;
   java.lang.String customName;
   /**
    * ????? ????? Activity Name
    *
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public java.lang.String getCustomName() {
      return customName;
   }
   /**
    * ????? ????? Activity Name
    *
    * @see e3ps.ecm.entity.KETChangeActivity
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
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String CHARGE = "charge";
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String CHARGE_REFERENCE = "chargeReference";
   wt.fc.ObjectReference chargeReference;
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public wt.org.WTUser getCharge() {
      return (chargeReference != null) ? (wt.org.WTUser) chargeReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public wt.fc.ObjectReference getChargeReference() {
      return chargeReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setCharge(wt.org.WTUser the_charge) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setChargeReference(the_charge == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_charge));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setChargeReference(wt.fc.ObjectReference the_chargeReference) throws wt.util.WTPropertyVetoException {
      chargeReferenceValidate(the_chargeReference);
      chargeReference = (wt.fc.ObjectReference) the_chargeReference;
   }
   void chargeReferenceValidate(wt.fc.ObjectReference the_chargeReference) throws wt.util.WTPropertyVetoException {
      if (the_chargeReference != null && the_chargeReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_chargeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chargeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "chargeReference", this.chargeReference, chargeReference));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String ACTIVITY = "activity";
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public static final java.lang.String ACTIVITY_REFERENCE = "activityReference";
   wt.fc.ObjectReference activityReference;
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public e3ps.common.code.NumberCode getActivity() {
      return (activityReference != null) ? (e3ps.common.code.NumberCode) activityReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public wt.fc.ObjectReference getActivityReference() {
      return activityReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setActivity(e3ps.common.code.NumberCode the_activity) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setActivityReference(the_activity == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_activity));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivity
    */
   public void setActivityReference(wt.fc.ObjectReference the_activityReference) throws wt.util.WTPropertyVetoException {
      activityReferenceValidate(the_activityReference);
      activityReference = (wt.fc.ObjectReference) the_activityReference;
   }
   void activityReferenceValidate(wt.fc.ObjectReference the_activityReference) throws wt.util.WTPropertyVetoException {
      if (the_activityReference != null && the_activityReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_activityReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "activityReference", this.activityReference, activityReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1268319697062817226L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( activityCode );
      output.writeObject( activityReference );
      output.writeObject( chargeName );
      output.writeObject( chargeReference );
      output.writeObject( completeRequestDate );
      output.writeObject( containerReference );
      output.writeObject( customCode );
      output.writeObject( customName );
      output.writeObject( customType );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETChangeActivity) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "activityCode", activityCode );
      output.writeObject( "activityReference", activityReference, wt.fc.ObjectReference.class, true );
      output.setString( "chargeName", chargeName );
      output.writeObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "completeRequestDate", completeRequestDate );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "customCode", customCode );
      output.setString( "customName", customName );
      output.setString( "customType", customType );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      activityCode = input.getString( "activityCode" );
      activityReference = (wt.fc.ObjectReference) input.readObject( "activityReference", activityReference, wt.fc.ObjectReference.class, true );
      chargeName = input.getString( "chargeName" );
      chargeReference = (wt.fc.ObjectReference) input.readObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      completeRequestDate = input.getTimestamp( "completeRequestDate" );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      customCode = input.getString( "customCode" );
      customName = input.getString( "customName" );
      customType = input.getString( "customType" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion1268319697062817226L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      activityCode = (java.lang.String) input.readObject();
      activityReference = (wt.fc.ObjectReference) input.readObject();
      chargeName = (java.lang.String) input.readObject();
      chargeReference = (wt.fc.ObjectReference) input.readObject();
      completeRequestDate = (java.sql.Timestamp) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      customCode = (java.lang.String) input.readObject();
      customName = (java.lang.String) input.readObject();
      customType = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETChangeActivity thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1268319697062817226L( input, readSerialVersionUID, superDone );
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
