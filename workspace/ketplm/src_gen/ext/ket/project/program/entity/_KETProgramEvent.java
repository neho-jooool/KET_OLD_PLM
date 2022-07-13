package ext.ket.project.program.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProgramEvent extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.program.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProgramEvent.class.getName();

   /**
    * 고객 이벤트 명
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public static final java.lang.String CUSTOMER_EVENT_NAME = "customerEventName";
   static int CUSTOMER_EVENT_NAME_UPPER_LIMIT = -1;
   java.lang.String customerEventName;
   /**
    * 고객 이벤트 명
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public java.lang.String getCustomerEventName() {
      return customerEventName;
   }
   /**
    * 고객 이벤트 명
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public void setCustomerEventName(java.lang.String customerEventName) throws wt.util.WTPropertyVetoException {
      customerEventNameValidate(customerEventName);
      this.customerEventName = customerEventName;
   }
   void customerEventNameValidate(java.lang.String customerEventName) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_EVENT_NAME_UPPER_LIMIT < 1) {
         try { CUSTOMER_EVENT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customerEventName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_EVENT_NAME_UPPER_LIMIT = 200; }
      }
      if (customerEventName != null && !wt.fc.PersistenceHelper.checkStoredLength(customerEventName.toString(), CUSTOMER_EVENT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerEventName"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_EVENT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customerEventName", this.customerEventName, customerEventName));
   }

   /**
    * 고객이벤트 일정
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public static final java.lang.String CUSTOMER_EVENT_DATE = "customerEventDate";
   java.sql.Timestamp customerEventDate;
   /**
    * 고객이벤트 일정
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public java.sql.Timestamp getCustomerEventDate() {
      return customerEventDate;
   }
   /**
    * 고객이벤트 일정
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public void setCustomerEventDate(java.sql.Timestamp customerEventDate) throws wt.util.WTPropertyVetoException {
      customerEventDateValidate(customerEventDate);
      this.customerEventDate = customerEventDate;
   }
   void customerEventDateValidate(java.sql.Timestamp customerEventDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 이벤트 순서
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public static final java.lang.String EVENT_ORDER = "eventOrder";
   int eventOrder = 0;
   /**
    * 이벤트 순서
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public int getEventOrder() {
      return eventOrder;
   }
   /**
    * 이벤트 순서
    *
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public void setEventOrder(int eventOrder) throws wt.util.WTPropertyVetoException {
      eventOrderValidate(eventOrder);
      this.eventOrder = eventOrder;
   }
   void eventOrderValidate(int eventOrder) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public static final java.lang.String OEM_PLAN = "oemPlan";
   /**
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public static final java.lang.String OEM_PLAN_REFERENCE = "oemPlanReference";
   wt.fc.ObjectReference oemPlanReference;
   /**
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public e3ps.project.outputtype.OEMPlan getOemPlan() {
      return (oemPlanReference != null) ? (e3ps.project.outputtype.OEMPlan) oemPlanReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public wt.fc.ObjectReference getOemPlanReference() {
      return oemPlanReference;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public void setOemPlan(e3ps.project.outputtype.OEMPlan the_oemPlan) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOemPlanReference(the_oemPlan == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMPlan) the_oemPlan));
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramEvent
    */
   public void setOemPlanReference(wt.fc.ObjectReference the_oemPlanReference) throws wt.util.WTPropertyVetoException {
      oemPlanReferenceValidate(the_oemPlanReference);
      oemPlanReference = (wt.fc.ObjectReference) the_oemPlanReference;
   }
   void oemPlanReferenceValidate(wt.fc.ObjectReference the_oemPlanReference) throws wt.util.WTPropertyVetoException {
      if (the_oemPlanReference != null && the_oemPlanReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMPlan.class.isAssignableFrom(the_oemPlanReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oemPlanReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "oemPlanReference", this.oemPlanReference, oemPlanReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -2413464104303582367L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( customerEventDate );
      output.writeObject( customerEventName );
      output.writeInt( eventOrder );
      output.writeObject( oemPlanReference );
   }

   protected void super_writeExternal_KETProgramEvent(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.program.entity.KETProgramEvent) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProgramEvent(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setTimestamp( "customerEventDate", customerEventDate );
      output.setString( "customerEventName", customerEventName );
      output.setInt( "eventOrder", eventOrder );
      output.writeObject( "oemPlanReference", oemPlanReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      customerEventDate = input.getTimestamp( "customerEventDate" );
      customerEventName = input.getString( "customerEventName" );
      eventOrder = input.getInt( "eventOrder" );
      oemPlanReference = (wt.fc.ObjectReference) input.readObject( "oemPlanReference", oemPlanReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion_2413464104303582367L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      customerEventDate = (java.sql.Timestamp) input.readObject();
      customerEventName = (java.lang.String) input.readObject();
      eventOrder = input.readInt();
      oemPlanReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProgramEvent thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2413464104303582367L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProgramEvent( _KETProgramEvent thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
