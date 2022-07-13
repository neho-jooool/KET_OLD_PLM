package ext.ket.common.dashboard.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDashBoardMailSetting implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.common.dashboard.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDashBoardMailSetting.class.getName();

   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
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
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public static final java.lang.String ID = "id";
   static int ID_UPPER_LIMIT = -1;
   java.lang.String id;
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public java.lang.String getId() {
      return id;
   }
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public void setId(java.lang.String id) throws wt.util.WTPropertyVetoException {
      idValidate(id);
      this.id = id;
   }
   void idValidate(java.lang.String id) throws wt.util.WTPropertyVetoException {
      if (ID_UPPER_LIMIT < 1) {
         try { ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("id").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ID_UPPER_LIMIT = 200; }
      }
      if (id != null && !wt.fc.PersistenceHelper.checkStoredLength(id.toString(), ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "id"), java.lang.String.valueOf(java.lang.Math.min(ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "id", this.id, id));
   }

   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public static final java.lang.String MAN = "man";
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public static final java.lang.String MAN_REFERENCE = "manReference";
   wt.fc.ObjectReference manReference;
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public e3ps.groupware.company.People getMan() {
      return (manReference != null) ? (e3ps.groupware.company.People) manReference.getObject() : null;
   }
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public wt.fc.ObjectReference getManReference() {
      return manReference;
   }
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public void setMan(e3ps.groupware.company.People the_man) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setManReference(the_man == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.People) the_man));
   }
   /**
    * @see ext.ket.common.dashboard.entity.KETDashBoardMailSetting
    */
   public void setManReference(wt.fc.ObjectReference the_manReference) throws wt.util.WTPropertyVetoException {
      manReferenceValidate(the_manReference);
      manReference = (wt.fc.ObjectReference) the_manReference;
   }
   void manReferenceValidate(wt.fc.ObjectReference the_manReference) throws wt.util.WTPropertyVetoException {
      if (the_manReference != null && the_manReference.getReferencedClass() != null &&
            !e3ps.groupware.company.People.class.isAssignableFrom(the_manReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "manReference", this.manReference, manReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -6532512579724450387L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( id );
      output.writeObject( manReference );
      output.writeObject( name );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.common.dashboard.entity.KETDashBoardMailSetting) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "id", id );
      output.writeObject( "manReference", manReference, wt.fc.ObjectReference.class, true );
      output.setString( "name", name );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      id = input.getString( "id" );
      manReference = (wt.fc.ObjectReference) input.readObject( "manReference", manReference, wt.fc.ObjectReference.class, true );
      name = input.getString( "name" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_6532512579724450387L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      id = (java.lang.String) input.readObject();
      manReference = (wt.fc.ObjectReference) input.readObject();
      name = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDashBoardMailSetting thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6532512579724450387L( input, readSerialVersionUID, superDone );
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
