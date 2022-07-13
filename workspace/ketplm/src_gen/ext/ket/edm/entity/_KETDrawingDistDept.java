package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDrawingDistDept implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDrawingDistDept.class.getName();

   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public static final java.lang.String DEPT_CODE = "deptCode";
   static int DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String deptCode;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public java.lang.String getDeptCode() {
      return deptCode;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public void setDeptCode(java.lang.String deptCode) throws wt.util.WTPropertyVetoException {
      deptCodeValidate(deptCode);
      this.deptCode = deptCode;
   }
   void deptCodeValidate(java.lang.String deptCode) throws wt.util.WTPropertyVetoException {
      if (DEPT_CODE_UPPER_LIMIT < 1) {
         try { DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (deptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(deptCode.toString(), DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptCode"), java.lang.String.valueOf(java.lang.Math.min(DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptCode", this.deptCode, deptCode));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public void setDeptName(java.lang.String deptName) throws wt.util.WTPropertyVetoException {
      deptNameValidate(deptName);
      this.deptName = deptName;
   }
   void deptNameValidate(java.lang.String deptName) throws wt.util.WTPropertyVetoException {
      if (DEPT_NAME_UPPER_LIMIT < 1) {
         try { DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (deptName != null && !wt.fc.PersistenceHelper.checkStoredLength(deptName.toString(), DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptName"), java.lang.String.valueOf(java.lang.Math.min(DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptName", this.deptName, deptName));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public static final java.lang.String DIST_REQ = "distReq";
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public static final java.lang.String DIST_REQ_REFERENCE = "distReqReference";
   wt.fc.ObjectReference distReqReference;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public ext.ket.edm.entity.KETDrawingDistRequest getDistReq() {
      return (distReqReference != null) ? (ext.ket.edm.entity.KETDrawingDistRequest) distReqReference.getObject() : null;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public wt.fc.ObjectReference getDistReqReference() {
      return distReqReference;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
    */
   public void setDistReq(ext.ket.edm.entity.KETDrawingDistRequest the_distReq) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDistReqReference(the_distReq == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.edm.entity.KETDrawingDistRequest) the_distReq));
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistDept
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

   public static final long EXTERNALIZATION_VERSION_UID = 7187508567596728538L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( deptCode );
      output.writeObject( deptName );
      output.writeObject( distReqReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETDrawingDistDept) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "deptCode", deptCode );
      output.setString( "deptName", deptName );
      output.writeObject( "distReqReference", distReqReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      deptCode = input.getString( "deptCode" );
      deptName = input.getString( "deptName" );
      distReqReference = (wt.fc.ObjectReference) input.readObject( "distReqReference", distReqReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion7187508567596728538L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      deptCode = (java.lang.String) input.readObject();
      deptName = (java.lang.String) input.readObject();
      distReqReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDrawingDistDept thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7187508567596728538L( input, readSerialVersionUID, superDone );
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
