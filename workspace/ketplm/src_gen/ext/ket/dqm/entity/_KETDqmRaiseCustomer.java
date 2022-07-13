package ext.ket.dqm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDqmRaiseCustomer implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dqm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDqmRaiseCustomer.class.getName();

   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public void setName(java.lang.String name) throws wt.util.WTPropertyVetoException {
      nameValidate(name);
      this.name = name;
   }
   void nameValidate(java.lang.String name) throws wt.util.WTPropertyVetoException {
      if (NAME_UPPER_LIMIT < 1) {
         try { NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("name").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAME_UPPER_LIMIT = 200; }
      }
      if (name != null && !wt.fc.PersistenceHelper.checkStoredLength(name.toString(), NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "name"), java.lang.String.valueOf(java.lang.Math.min(NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "name", this.name, name));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public static final java.lang.String CODE = "code";
   static int CODE_UPPER_LIMIT = -1;
   java.lang.String code;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public java.lang.String getCode() {
      return code;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public void setCode(java.lang.String code) throws wt.util.WTPropertyVetoException {
      codeValidate(code);
      this.code = code;
   }
   void codeValidate(java.lang.String code) throws wt.util.WTPropertyVetoException {
      if (CODE_UPPER_LIMIT < 1) {
         try { CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("code").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CODE_UPPER_LIMIT = 200; }
      }
      if (code != null && !wt.fc.PersistenceHelper.checkStoredLength(code.toString(), CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "code"), java.lang.String.valueOf(java.lang.Math.min(CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "code", this.code, code));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public static final java.lang.String OID = "oid";
   static int OID_UPPER_LIMIT = -1;
   java.lang.String oid;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public java.lang.String getOid() {
      return oid;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaiseCustomer
    */
   public void setOid(java.lang.String oid) throws wt.util.WTPropertyVetoException {
      oidValidate(oid);
      this.oid = oid;
   }
   void oidValidate(java.lang.String oid) throws wt.util.WTPropertyVetoException {
      if (OID_UPPER_LIMIT < 1) {
         try { OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("oid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OID_UPPER_LIMIT = 200; }
      }
      if (oid != null && !wt.fc.PersistenceHelper.checkStoredLength(oid.toString(), OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oid"), java.lang.String.valueOf(java.lang.Math.min(OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "oid", this.oid, oid));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1308874878623478767L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( code );
      output.writeObject( name );
      output.writeObject( oid );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dqm.entity.KETDqmRaiseCustomer) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "code", code );
      output.setString( "name", name );
      output.setString( "oid", oid );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      code = input.getString( "code" );
      name = input.getString( "name" );
      oid = input.getString( "oid" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion1308874878623478767L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      code = (java.lang.String) input.readObject();
      name = (java.lang.String) input.readObject();
      oid = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDqmRaiseCustomer thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1308874878623478767L( input, readSerialVersionUID, superDone );
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
