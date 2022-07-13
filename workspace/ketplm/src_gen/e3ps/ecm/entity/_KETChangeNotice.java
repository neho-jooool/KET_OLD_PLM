package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETChangeNotice extends wt.enterprise.Managed implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETChangeNotice.class.getName();

   /**
    * ECN Number : ??) ECN-2014-001
    *
    * @see e3ps.ecm.entity.KETChangeNotice
    */
   public static final java.lang.String ECN_NUMBER = "ecnNumber";
   static int ECN_NUMBER_UPPER_LIMIT = -1;
   java.lang.String ecnNumber;
   /**
    * ECN Number : ??) ECN-2014-001
    *
    * @see e3ps.ecm.entity.KETChangeNotice
    */
   public java.lang.String getEcnNumber() {
      return ecnNumber;
   }
   /**
    * ECN Number : ??) ECN-2014-001
    *
    * @see e3ps.ecm.entity.KETChangeNotice
    */
   public void setEcnNumber(java.lang.String ecnNumber) throws wt.util.WTPropertyVetoException {
      ecnNumberValidate(ecnNumber);
      this.ecnNumber = ecnNumber;
   }
   void ecnNumberValidate(java.lang.String ecnNumber) throws wt.util.WTPropertyVetoException {
      if (ECN_NUMBER_UPPER_LIMIT < 1) {
         try { ECN_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecnNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECN_NUMBER_UPPER_LIMIT = 200; }
      }
      if (ecnNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(ecnNumber.toString(), ECN_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecnNumber"), java.lang.String.valueOf(java.lang.Math.min(ECN_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecnNumber", this.ecnNumber, ecnNumber));
   }

   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETChangeNotice
    */
   public static final java.lang.String COMPLETE_DATE = "completeDate";
   java.sql.Timestamp completeDate;
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETChangeNotice
    */
   public java.sql.Timestamp getCompleteDate() {
      return completeDate;
   }
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETChangeNotice
    */
   public void setCompleteDate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
      completeDateValidate(completeDate);
      this.completeDate = completeDate;
   }
   void completeDateValidate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -51766074782997792L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( completeDate );
      output.writeObject( containerReference );
      output.writeObject( ecnNumber );
   }

   protected void super_writeExternal_KETChangeNotice(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETChangeNotice) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETChangeNotice(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setTimestamp( "completeDate", completeDate );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "ecnNumber", ecnNumber );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      completeDate = input.getTimestamp( "completeDate" );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      ecnNumber = input.getString( "ecnNumber" );
   }

   boolean readVersion_51766074782997792L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      completeDate = (java.sql.Timestamp) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      ecnNumber = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETChangeNotice thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_51766074782997792L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETChangeNotice( _KETChangeNotice thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
