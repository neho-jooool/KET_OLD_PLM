package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostCurrencyInfo implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostCurrencyInfo.class.getName();

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String VALUE1 = "value1";
   static int VALUE1_UPPER_LIMIT = -1;
   java.lang.String value1;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.String getValue1() {
      return value1;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setValue1(java.lang.String value1) throws wt.util.WTPropertyVetoException {
      value1Validate(value1);
      this.value1 = value1;
   }
   void value1Validate(java.lang.String value1) throws wt.util.WTPropertyVetoException {
      if (VALUE1_UPPER_LIMIT < 1) {
         try { VALUE1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("value1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VALUE1_UPPER_LIMIT = 200; }
      }
      if (value1 != null && !wt.fc.PersistenceHelper.checkStoredLength(value1.toString(), VALUE1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "value1"), java.lang.String.valueOf(java.lang.Math.min(VALUE1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "value1", this.value1, value1));
   }

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String VALUE2 = "value2";
   static int VALUE2_UPPER_LIMIT = -1;
   java.lang.String value2;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.String getValue2() {
      return value2;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setValue2(java.lang.String value2) throws wt.util.WTPropertyVetoException {
      value2Validate(value2);
      this.value2 = value2;
   }
   void value2Validate(java.lang.String value2) throws wt.util.WTPropertyVetoException {
      if (VALUE2_UPPER_LIMIT < 1) {
         try { VALUE2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("value2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VALUE2_UPPER_LIMIT = 200; }
      }
      if (value2 != null && !wt.fc.PersistenceHelper.checkStoredLength(value2.toString(), VALUE2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "value2"), java.lang.String.valueOf(java.lang.Math.min(VALUE2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "value2", this.value2, value2));
   }

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String ITEM_TYPE = "itemType";
   static int ITEM_TYPE_UPPER_LIMIT = -1;
   java.lang.String itemType;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.String getItemType() {
      return itemType;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setItemType(java.lang.String itemType) throws wt.util.WTPropertyVetoException {
      itemTypeValidate(itemType);
      this.itemType = itemType;
   }
   void itemTypeValidate(java.lang.String itemType) throws wt.util.WTPropertyVetoException {
      if (ITEM_TYPE_UPPER_LIMIT < 1) {
         try { ITEM_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("itemType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ITEM_TYPE_UPPER_LIMIT = 200; }
      }
      if (itemType != null && !wt.fc.PersistenceHelper.checkStoredLength(itemType.toString(), ITEM_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "itemType"), java.lang.String.valueOf(java.lang.Math.min(ITEM_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "itemType", this.itemType, itemType));
   }

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String SORT = "sort";
   java.lang.Integer sort = 0;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.Integer getSort() {
      return sort;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setSort(java.lang.Integer sort) throws wt.util.WTPropertyVetoException {
      sortValidate(sort);
      this.sort = sort;
   }
   void sortValidate(java.lang.Integer sort) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
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
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String VERSION = "version";
   java.lang.Integer version = 0;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.Integer getVersion() {
      return version;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setVersion(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String SUB_VERSION = "subVersion";
   java.lang.Integer subVersion = 0;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.Integer getSubVersion() {
      return subVersion;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setSubVersion(java.lang.Integer subVersion) throws wt.util.WTPropertyVetoException {
      subVersionValidate(subVersion);
      this.subVersion = subVersion;
   }
   void subVersionValidate(java.lang.Integer subVersion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setPjtNo(java.lang.String pjtNo) throws wt.util.WTPropertyVetoException {
      pjtNoValidate(pjtNo);
      this.pjtNo = pjtNo;
   }
   void pjtNoValidate(java.lang.String pjtNo) throws wt.util.WTPropertyVetoException {
      if (PJT_NO_UPPER_LIMIT < 1) {
         try { PJT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_NO_UPPER_LIMIT = 200; }
      }
      if (pjtNo != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtNo.toString(), PJT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtNo"), java.lang.String.valueOf(java.lang.Math.min(PJT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtNo", this.pjtNo, pjtNo));
   }

   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String MASTER = "master";
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public static final java.lang.String MASTER_REFERENCE = "masterReference";
   wt.fc.ObjectReference masterReference;
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public ext.ket.cost.entity.ProductMaster getMaster() {
      return (masterReference != null) ? (ext.ket.cost.entity.ProductMaster) masterReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public wt.fc.ObjectReference getMasterReference() {
      return masterReference;
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setMaster(ext.ket.cost.entity.ProductMaster the_master) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMasterReference(the_master == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.ProductMaster) the_master));
   }
   /**
    * @see ext.ket.cost.entity.CostCurrencyInfo
    */
   public void setMasterReference(wt.fc.ObjectReference the_masterReference) throws wt.util.WTPropertyVetoException {
      masterReferenceValidate(the_masterReference);
      masterReference = (wt.fc.ObjectReference) the_masterReference;
   }
   void masterReferenceValidate(wt.fc.ObjectReference the_masterReference) throws wt.util.WTPropertyVetoException {
      if (the_masterReference == null || the_masterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterReference") },
               new java.beans.PropertyChangeEvent(this, "masterReference", this.masterReference, masterReference));
      if (the_masterReference != null && the_masterReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.ProductMaster.class.isAssignableFrom(the_masterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "masterReference", this.masterReference, masterReference));
   }

   wt.org.WTPrincipalReference owner;
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public wt.org.WTPrincipalReference getOwner() {
      return owner;
   }
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      ownerValidate(owner);
      this.owner = owner;
   }
   void ownerValidate(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      if (owner == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "owner") },
               new java.beans.PropertyChangeEvent(this, "owner", this.owner, owner));
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

   public static final long EXTERNALIZATION_VERSION_UID = 6848153069981317372L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( itemType );
      output.writeObject( masterReference );
      output.writeObject( name );
      output.writeObject( owner );
      output.writeObject( pjtNo );
      output.writeObject( sort );
      output.writeObject( subVersion );
      output.writeObject( thePersistInfo );
      output.writeObject( value1 );
      output.writeObject( value2 );
      output.writeObject( version );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostCurrencyInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "itemType", itemType );
      output.writeObject( "masterReference", masterReference, wt.fc.ObjectReference.class, true );
      output.setString( "name", name );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "pjtNo", pjtNo );
      output.setIntObject( "sort", sort );
      output.setIntObject( "subVersion", subVersion );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "value1", value1 );
      output.setString( "value2", value2 );
      output.setIntObject( "version", version );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      itemType = input.getString( "itemType" );
      masterReference = (wt.fc.ObjectReference) input.readObject( "masterReference", masterReference, wt.fc.ObjectReference.class, true );
      name = input.getString( "name" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      pjtNo = input.getString( "pjtNo" );
      sort = input.getIntObject( "sort" );
      subVersion = input.getIntObject( "subVersion" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      value1 = input.getString( "value1" );
      value2 = input.getString( "value2" );
      version = input.getIntObject( "version" );
   }

   boolean readVersion6848153069981317372L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      itemType = (java.lang.String) input.readObject();
      masterReference = (wt.fc.ObjectReference) input.readObject();
      name = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      sort = (java.lang.Integer) input.readObject();
      subVersion = (java.lang.Integer) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      value1 = (java.lang.String) input.readObject();
      value2 = (java.lang.String) input.readObject();
      version = (java.lang.Integer) input.readObject();
      return true;
   }

   protected boolean readVersion( CostCurrencyInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6848153069981317372L( input, readSerialVersionUID, superDone );
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
