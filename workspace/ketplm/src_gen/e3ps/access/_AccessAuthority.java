package e3ps.access;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AccessAuthority implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.access.accessResource";
   static final java.lang.String CLASSNAME = AccessAuthority.class.getName();

   /**
    * @see e3ps.access.AccessAuthority
    */
   public static final java.lang.String ACCESS_NAME = "accessName";
   static int ACCESS_NAME_UPPER_LIMIT = -1;
   java.lang.String accessName;
   /**
    * @see e3ps.access.AccessAuthority
    */
   public java.lang.String getAccessName() {
      return accessName;
   }
   /**
    * @see e3ps.access.AccessAuthority
    */
   public void setAccessName(java.lang.String accessName) throws wt.util.WTPropertyVetoException {
      accessNameValidate(accessName);
      this.accessName = accessName;
   }
   void accessNameValidate(java.lang.String accessName) throws wt.util.WTPropertyVetoException {
      if (ACCESS_NAME_UPPER_LIMIT < 1) {
         try { ACCESS_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("accessName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACCESS_NAME_UPPER_LIMIT = 200; }
      }
      if (accessName != null && !wt.fc.PersistenceHelper.checkStoredLength(accessName.toString(), ACCESS_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "accessName"), java.lang.String.valueOf(java.lang.Math.min(ACCESS_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "accessName", this.accessName, accessName));
   }

   /**
    * @see e3ps.access.AccessAuthority
    */
   public static final java.lang.String OBJECT_ID = "objectID";
   long objectID;
   /**
    * @see e3ps.access.AccessAuthority
    */
   public long getObjectID() {
      return objectID;
   }
   /**
    * @see e3ps.access.AccessAuthority
    */
   public void setObjectID(long objectID) throws wt.util.WTPropertyVetoException {
      objectIDValidate(objectID);
      this.objectID = objectID;
   }
   void objectIDValidate(long objectID) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.access.AccessAuthority
    */
   public static final java.lang.String OBJ_CLASS_NAME = "objClassName";
   static int OBJ_CLASS_NAME_UPPER_LIMIT = -1;
   java.lang.String objClassName;
   /**
    * @see e3ps.access.AccessAuthority
    */
   public java.lang.String getObjClassName() {
      return objClassName;
   }
   /**
    * @see e3ps.access.AccessAuthority
    */
   public void setObjClassName(java.lang.String objClassName) throws wt.util.WTPropertyVetoException {
      objClassNameValidate(objClassName);
      this.objClassName = objClassName;
   }
   void objClassNameValidate(java.lang.String objClassName) throws wt.util.WTPropertyVetoException {
      if (OBJ_CLASS_NAME_UPPER_LIMIT < 1) {
         try { OBJ_CLASS_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("objClassName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OBJ_CLASS_NAME_UPPER_LIMIT = 200; }
      }
      if (objClassName != null && !wt.fc.PersistenceHelper.checkStoredLength(objClassName.toString(), OBJ_CLASS_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "objClassName"), java.lang.String.valueOf(java.lang.Math.min(OBJ_CLASS_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "objClassName", this.objClassName, objClassName));
   }

   /**
    * @see e3ps.access.AccessAuthority
    */
   public static final java.lang.String IS_VR = "isVR";
   boolean isVR;
   /**
    * @see e3ps.access.AccessAuthority
    */
   public boolean isIsVR() {
      return isVR;
   }
   /**
    * @see e3ps.access.AccessAuthority
    */
   public void setIsVR(boolean isVR) throws wt.util.WTPropertyVetoException {
      isVRValidate(isVR);
      this.isVR = isVR;
   }
   void isVRValidate(boolean isVR) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -5456114327508210663L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( accessName );
      output.writeBoolean( isVR );
      output.writeObject( objClassName );
      output.writeLong( objectID );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.access.AccessAuthority) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "accessName", accessName );
      output.setBoolean( "isVR", isVR );
      output.setString( "objClassName", objClassName );
      output.setLong( "objectID", objectID );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      accessName = input.getString( "accessName" );
      isVR = input.getBoolean( "isVR" );
      objClassName = input.getString( "objClassName" );
      objectID = input.getLong( "objectID" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_5456114327508210663L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      accessName = (java.lang.String) input.readObject();
      isVR = input.readBoolean();
      objClassName = (java.lang.String) input.readObject();
      objectID = input.readLong();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( AccessAuthority thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_5456114327508210663L( input, readSerialVersionUID, superDone );
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
