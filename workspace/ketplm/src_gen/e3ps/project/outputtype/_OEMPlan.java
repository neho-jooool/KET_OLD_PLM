package e3ps.project.outputtype;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _OEMPlan implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.outputtype.outputtypeResource";
   static final java.lang.String CLASSNAME = OEMPlan.class.getName();

   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public static final java.lang.String OEM_END_DATE = "oemEndDate";
   java.sql.Timestamp oemEndDate;
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public java.sql.Timestamp getOemEndDate() {
      return oemEndDate;
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public void setOemEndDate(java.sql.Timestamp oemEndDate) throws wt.util.WTPropertyVetoException {
      oemEndDateValidate(oemEndDate);
      this.oemEndDate = oemEndDate;
   }
   void oemEndDateValidate(java.sql.Timestamp oemEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public static final java.lang.String CUSTOMER = "customer";
   static int CUSTOMER_UPPER_LIMIT = -1;
   java.lang.String customer;
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public java.lang.String getCustomer() {
      return customer;
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public void setCustomer(java.lang.String customer) throws wt.util.WTPropertyVetoException {
      customerValidate(customer);
      this.customer = customer;
   }
   void customerValidate(java.lang.String customer) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_UPPER_LIMIT < 1) {
         try { CUSTOMER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customer").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_UPPER_LIMIT = 200; }
      }
      if (customer != null && !wt.fc.PersistenceHelper.checkStoredLength(customer.toString(), CUSTOMER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customer"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customer", this.customer, customer));
   }

   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public static final java.lang.String VIEW_TYPE = "viewType";
   int viewType;
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public int getViewType() {
      return viewType;
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public void setViewType(int viewType) throws wt.util.WTPropertyVetoException {
      viewTypeValidate(viewType);
      this.viewType = viewType;
   }
   void viewTypeValidate(int viewType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public static final java.lang.String OEM_TYPE = "oemType";
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public static final java.lang.String OEM_TYPE_REFERENCE = "oemTypeReference";
   wt.fc.ObjectReference oemTypeReference;
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public e3ps.project.outputtype.OEMProjectType getOemType() {
      return (oemTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) oemTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public wt.fc.ObjectReference getOemTypeReference() {
      return oemTypeReference;
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public void setOemType(e3ps.project.outputtype.OEMProjectType the_oemType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOemTypeReference(the_oemType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_oemType));
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public void setOemTypeReference(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      oemTypeReferenceValidate(the_oemTypeReference);
      oemTypeReference = (wt.fc.ObjectReference) the_oemTypeReference;
   }
   void oemTypeReferenceValidate(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_oemTypeReference != null && the_oemTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_oemTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oemTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "oemTypeReference", this.oemTypeReference, oemTypeReference));
   }

   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public static final java.lang.String MODEL_PLAN = "modelPlan";
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public static final java.lang.String MODEL_PLAN_REFERENCE = "modelPlanReference";
   wt.fc.ObjectReference modelPlanReference;
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public e3ps.project.outputtype.ModelPlan getModelPlan() {
      return (modelPlanReference != null) ? (e3ps.project.outputtype.ModelPlan) modelPlanReference.getObject() : null;
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public wt.fc.ObjectReference getModelPlanReference() {
      return modelPlanReference;
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public void setModelPlan(e3ps.project.outputtype.ModelPlan the_modelPlan) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setModelPlanReference(the_modelPlan == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.ModelPlan) the_modelPlan));
   }
   /**
    * @see e3ps.project.outputtype.OEMPlan
    */
   public void setModelPlanReference(wt.fc.ObjectReference the_modelPlanReference) throws wt.util.WTPropertyVetoException {
      modelPlanReferenceValidate(the_modelPlanReference);
      modelPlanReference = (wt.fc.ObjectReference) the_modelPlanReference;
   }
   void modelPlanReferenceValidate(wt.fc.ObjectReference the_modelPlanReference) throws wt.util.WTPropertyVetoException {
      if (the_modelPlanReference != null && the_modelPlanReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.ModelPlan.class.isAssignableFrom(the_modelPlanReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modelPlanReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "modelPlanReference", this.modelPlanReference, modelPlanReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 5877419621543200764L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( customer );
      output.writeObject( modelPlanReference );
      output.writeObject( oemEndDate );
      output.writeObject( oemTypeReference );
      output.writeObject( thePersistInfo );
      output.writeInt( viewType );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.outputtype.OEMPlan) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "customer", customer );
      output.writeObject( "modelPlanReference", modelPlanReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "oemEndDate", oemEndDate );
      output.writeObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setInt( "viewType", viewType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      customer = input.getString( "customer" );
      modelPlanReference = (wt.fc.ObjectReference) input.readObject( "modelPlanReference", modelPlanReference, wt.fc.ObjectReference.class, true );
      oemEndDate = input.getTimestamp( "oemEndDate" );
      oemTypeReference = (wt.fc.ObjectReference) input.readObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      viewType = input.getInt( "viewType" );
   }

   boolean readVersion5877419621543200764L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      customer = (java.lang.String) input.readObject();
      modelPlanReference = (wt.fc.ObjectReference) input.readObject();
      oemEndDate = (java.sql.Timestamp) input.readObject();
      oemTypeReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      viewType = input.readInt();
      return true;
   }

   protected boolean readVersion( OEMPlan thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5877419621543200764L( input, readSerialVersionUID, superDone );
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
