package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDrawingDistFileType implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDrawingDistFileType.class.getName();

   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public static final java.lang.String DIST_FILE_TYPE = "distFileType";
   static int DIST_FILE_TYPE_UPPER_LIMIT = -1;
   java.lang.String distFileType;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public java.lang.String getDistFileType() {
      return distFileType;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public void setDistFileType(java.lang.String distFileType) throws wt.util.WTPropertyVetoException {
      distFileTypeValidate(distFileType);
      this.distFileType = distFileType;
   }
   void distFileTypeValidate(java.lang.String distFileType) throws wt.util.WTPropertyVetoException {
      if (DIST_FILE_TYPE_UPPER_LIMIT < 1) {
         try { DIST_FILE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("distFileType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIST_FILE_TYPE_UPPER_LIMIT = 200; }
      }
      if (distFileType != null && !wt.fc.PersistenceHelper.checkStoredLength(distFileType.toString(), DIST_FILE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distFileType"), java.lang.String.valueOf(java.lang.Math.min(DIST_FILE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "distFileType", this.distFileType, distFileType));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public static final java.lang.String DIST_REQ = "distReq";
   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public static final java.lang.String DIST_REQ_REFERENCE = "distReqReference";
   wt.fc.ObjectReference distReqReference;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public ext.ket.edm.entity.KETDrawingDistRequest getDistReq() {
      return (distReqReference != null) ? (ext.ket.edm.entity.KETDrawingDistRequest) distReqReference.getObject() : null;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public wt.fc.ObjectReference getDistReqReference() {
      return distReqReference;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public void setDistReq(ext.ket.edm.entity.KETDrawingDistRequest the_distReq) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDistReqReference(the_distReq == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.edm.entity.KETDrawingDistRequest) the_distReq));
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistFileType
    */
   public void setDistReqReference(wt.fc.ObjectReference the_distReqReference) throws wt.util.WTPropertyVetoException {
      distReqReferenceValidate(the_distReqReference);
      distReqReference = (wt.fc.ObjectReference) the_distReqReference;
   }
   void distReqReferenceValidate(wt.fc.ObjectReference the_distReqReference) throws wt.util.WTPropertyVetoException {
      if (the_distReqReference == null || the_distReqReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distReqReference") },
               new java.beans.PropertyChangeEvent(this, "distReqReference", this.distReqReference, distReqReference));
      if (the_distReqReference != null && the_distReqReference.getReferencedClass() != null &&
            !ext.ket.edm.entity.KETDrawingDistRequest.class.isAssignableFrom(the_distReqReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distReqReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "distReqReference", this.distReqReference, distReqReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -6571877081686207567L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( distFileType );
      output.writeObject( distReqReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETDrawingDistFileType) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "distFileType", distFileType );
      output.writeObject( "distReqReference", distReqReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      distFileType = input.getString( "distFileType" );
      distReqReference = (wt.fc.ObjectReference) input.readObject( "distReqReference", distReqReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_6571877081686207567L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      distFileType = (java.lang.String) input.readObject();
      distReqReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDrawingDistFileType thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6571877081686207567L( input, readSerialVersionUID, superDone );
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
