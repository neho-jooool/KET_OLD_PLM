package ext.ket.sample.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSamplePart extends wt.enterprise.Managed implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sample.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSamplePart.class.getName();

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String REQUEST_COUNT = "requestCount";
   int requestCount;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public int getRequestCount() {
      return requestCount;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setRequestCount(int requestCount) throws wt.util.WTPropertyVetoException {
      requestCountValidate(requestCount);
      this.requestCount = requestCount;
   }
   void requestCountValidate(int requestCount) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String DISPENSATION_COUNT = "dispensationCount";
   int dispensationCount;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public int getDispensationCount() {
      return dispensationCount;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setDispensationCount(int dispensationCount) throws wt.util.WTPropertyVetoException {
      dispensationCountValidate(dispensationCount);
      this.dispensationCount = dispensationCount;
   }
   void dispensationCountValidate(int dispensationCount) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String RECEPTION_DATE = "receptionDate";
   java.sql.Timestamp receptionDate;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public java.sql.Timestamp getReceptionDate() {
      return receptionDate;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setReceptionDate(java.sql.Timestamp receptionDate) throws wt.util.WTPropertyVetoException {
      receptionDateValidate(receptionDate);
      this.receptionDate = receptionDate;
   }
   void receptionDateValidate(java.sql.Timestamp receptionDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String DISPENSATION_EXPECT_DATE = "dispensationExpectDate";
   java.sql.Timestamp dispensationExpectDate;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public java.sql.Timestamp getDispensationExpectDate() {
      return dispensationExpectDate;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setDispensationExpectDate(java.sql.Timestamp dispensationExpectDate) throws wt.util.WTPropertyVetoException {
      dispensationExpectDateValidate(dispensationExpectDate);
      this.dispensationExpectDate = dispensationExpectDate;
   }
   void dispensationExpectDateValidate(java.sql.Timestamp dispensationExpectDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String SAMPLE_PART_STATE_CODE = "samplePartStateCode";
   static int SAMPLE_PART_STATE_CODE_UPPER_LIMIT = -1;
   java.lang.String samplePartStateCode;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public java.lang.String getSamplePartStateCode() {
      return samplePartStateCode;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setSamplePartStateCode(java.lang.String samplePartStateCode) throws wt.util.WTPropertyVetoException {
      samplePartStateCodeValidate(samplePartStateCode);
      this.samplePartStateCode = samplePartStateCode;
   }
   void samplePartStateCodeValidate(java.lang.String samplePartStateCode) throws wt.util.WTPropertyVetoException {
      if (SAMPLE_PART_STATE_CODE_UPPER_LIMIT < 1) {
         try { SAMPLE_PART_STATE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("samplePartStateCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SAMPLE_PART_STATE_CODE_UPPER_LIMIT = 200; }
      }
      if (samplePartStateCode != null && !wt.fc.PersistenceHelper.checkStoredLength(samplePartStateCode.toString(), SAMPLE_PART_STATE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "samplePartStateCode"), java.lang.String.valueOf(java.lang.Math.min(SAMPLE_PART_STATE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "samplePartStateCode", this.samplePartStateCode, samplePartStateCode));
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String PJTNO = "pjtno";
   static int PJTNO_UPPER_LIMIT = -1;
   java.lang.String pjtno;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public java.lang.String getPjtno() {
      return pjtno;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setPjtno(java.lang.String pjtno) throws wt.util.WTPropertyVetoException {
      pjtnoValidate(pjtno);
      this.pjtno = pjtno;
   }
   void pjtnoValidate(java.lang.String pjtno) throws wt.util.WTPropertyVetoException {
      if (PJTNO_UPPER_LIMIT < 1) {
         try { PJTNO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtno").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJTNO_UPPER_LIMIT = 200; }
      }
      if (pjtno != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtno.toString(), PJTNO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtno"), java.lang.String.valueOf(java.lang.Math.min(PJTNO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtno", this.pjtno, pjtno));
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String PJTOID = "pjtoid";
   static int PJTOID_UPPER_LIMIT = -1;
   java.lang.String pjtoid;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public java.lang.String getPjtoid() {
      return pjtoid;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setPjtoid(java.lang.String pjtoid) throws wt.util.WTPropertyVetoException {
      pjtoidValidate(pjtoid);
      this.pjtoid = pjtoid;
   }
   void pjtoidValidate(java.lang.String pjtoid) throws wt.util.WTPropertyVetoException {
      if (PJTOID_UPPER_LIMIT < 1) {
         try { PJTOID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtoid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJTOID_UPPER_LIMIT = 200; }
      }
      if (pjtoid != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtoid.toString(), PJTOID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtoid"), java.lang.String.valueOf(java.lang.Math.min(PJTOID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtoid", this.pjtoid, pjtoid));
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String SAMPLE_PART_STATE_NAME = "samplePartStateName";
   static int SAMPLE_PART_STATE_NAME_UPPER_LIMIT = -1;
   java.lang.String samplePartStateName;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public java.lang.String getSamplePartStateName() {
      return samplePartStateName;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setSamplePartStateName(java.lang.String samplePartStateName) throws wt.util.WTPropertyVetoException {
      samplePartStateNameValidate(samplePartStateName);
      this.samplePartStateName = samplePartStateName;
   }
   void samplePartStateNameValidate(java.lang.String samplePartStateName) throws wt.util.WTPropertyVetoException {
      if (SAMPLE_PART_STATE_NAME_UPPER_LIMIT < 1) {
         try { SAMPLE_PART_STATE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("samplePartStateName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SAMPLE_PART_STATE_NAME_UPPER_LIMIT = 200; }
      }
      if (samplePartStateName != null && !wt.fc.PersistenceHelper.checkStoredLength(samplePartStateName.toString(), SAMPLE_PART_STATE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "samplePartStateName"), java.lang.String.valueOf(java.lang.Math.min(SAMPLE_PART_STATE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "samplePartStateName", this.samplePartStateName, samplePartStateName));
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String DISPENSATION_DATE = "dispensationDate";
   java.sql.Timestamp dispensationDate;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public java.sql.Timestamp getDispensationDate() {
      return dispensationDate;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setDispensationDate(java.sql.Timestamp dispensationDate) throws wt.util.WTPropertyVetoException {
      dispensationDateValidate(dispensationDate);
      this.dispensationDate = dispensationDate;
   }
   void dispensationDateValidate(java.sql.Timestamp dispensationDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String PART = "part";
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String PART_REFERENCE = "partReference";
   wt.fc.ObjectReference partReference;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public wt.part.WTPart getPart() {
      return (partReference != null) ? (wt.part.WTPart) partReference.getObject() : null;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public wt.fc.ObjectReference getPartReference() {
      return partReference;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setPart(wt.part.WTPart the_part) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartReference(the_part == null ? null : wt.fc.ObjectReference.newObjectReference((wt.part.WTPart) the_part));
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setPartReference(wt.fc.ObjectReference the_partReference) throws wt.util.WTPropertyVetoException {
      partReferenceValidate(the_partReference);
      partReference = (wt.fc.ObjectReference) the_partReference;
   }
   void partReferenceValidate(wt.fc.ObjectReference the_partReference) throws wt.util.WTPropertyVetoException {
      if (the_partReference != null && the_partReference.getReferencedClass() != null &&
            !wt.part.WTPart.class.isAssignableFrom(the_partReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "partReference", this.partReference, partReference));
   }

   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String USER = "user";
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see ext.ket.sample.entity.KETSamplePart
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -8857412908319338052L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( containerReference );
      output.writeInt( dispensationCount );
      output.writeObject( dispensationDate );
      output.writeObject( dispensationExpectDate );
      output.writeObject( partReference );
      output.writeObject( pjtno );
      output.writeObject( pjtoid );
      output.writeObject( receptionDate );
      output.writeInt( requestCount );
      output.writeObject( samplePartStateCode );
      output.writeObject( samplePartStateName );
      output.writeObject( userReference );
   }

   protected void super_writeExternal_KETSamplePart(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sample.entity.KETSamplePart) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSamplePart(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setInt( "dispensationCount", dispensationCount );
      output.setTimestamp( "dispensationDate", dispensationDate );
      output.setTimestamp( "dispensationExpectDate", dispensationExpectDate );
      output.writeObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      output.setString( "pjtno", pjtno );
      output.setString( "pjtoid", pjtoid );
      output.setTimestamp( "receptionDate", receptionDate );
      output.setInt( "requestCount", requestCount );
      output.setString( "samplePartStateCode", samplePartStateCode );
      output.setString( "samplePartStateName", samplePartStateName );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      dispensationCount = input.getInt( "dispensationCount" );
      dispensationDate = input.getTimestamp( "dispensationDate" );
      dispensationExpectDate = input.getTimestamp( "dispensationExpectDate" );
      partReference = (wt.fc.ObjectReference) input.readObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      pjtno = input.getString( "pjtno" );
      pjtoid = input.getString( "pjtoid" );
      receptionDate = input.getTimestamp( "receptionDate" );
      requestCount = input.getInt( "requestCount" );
      samplePartStateCode = input.getString( "samplePartStateCode" );
      samplePartStateName = input.getString( "samplePartStateName" );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion_8857412908319338052L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      dispensationCount = input.readInt();
      dispensationDate = (java.sql.Timestamp) input.readObject();
      dispensationExpectDate = (java.sql.Timestamp) input.readObject();
      partReference = (wt.fc.ObjectReference) input.readObject();
      pjtno = (java.lang.String) input.readObject();
      pjtoid = (java.lang.String) input.readObject();
      receptionDate = (java.sql.Timestamp) input.readObject();
      requestCount = input.readInt();
      samplePartStateCode = (java.lang.String) input.readObject();
      samplePartStateName = (java.lang.String) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( KETSamplePart thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8857412908319338052L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSamplePart( _KETSamplePart thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
