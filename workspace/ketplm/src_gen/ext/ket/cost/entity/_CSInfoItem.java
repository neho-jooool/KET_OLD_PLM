package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CSInfoItem implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CSInfoItem.class.getName();

   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String VALUE1 = "value1";
   static int VALUE1_UPPER_LIMIT = -1;
   java.lang.String value1;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.String getValue1() {
      return value1;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
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
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String VALUE2 = "value2";
   static int VALUE2_UPPER_LIMIT = -1;
   java.lang.String value2;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.String getValue2() {
      return value2;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
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
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String VALUE3 = "value3";
   static int VALUE3_UPPER_LIMIT = -1;
   java.lang.String value3;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.String getValue3() {
      return value3;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public void setValue3(java.lang.String value3) throws wt.util.WTPropertyVetoException {
      value3Validate(value3);
      this.value3 = value3;
   }
   void value3Validate(java.lang.String value3) throws wt.util.WTPropertyVetoException {
      if (VALUE3_UPPER_LIMIT < 1) {
         try { VALUE3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("value3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VALUE3_UPPER_LIMIT = 200; }
      }
      if (value3 != null && !wt.fc.PersistenceHelper.checkStoredLength(value3.toString(), VALUE3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "value3"), java.lang.String.valueOf(java.lang.Math.min(VALUE3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "value3", this.value3, value3));
   }

   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String VALUE4 = "value4";
   static int VALUE4_UPPER_LIMIT = -1;
   java.lang.String value4;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.String getValue4() {
      return value4;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public void setValue4(java.lang.String value4) throws wt.util.WTPropertyVetoException {
      value4Validate(value4);
      this.value4 = value4;
   }
   void value4Validate(java.lang.String value4) throws wt.util.WTPropertyVetoException {
      if (VALUE4_UPPER_LIMIT < 1) {
         try { VALUE4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("value4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VALUE4_UPPER_LIMIT = 200; }
      }
      if (value4 != null && !wt.fc.PersistenceHelper.checkStoredLength(value4.toString(), VALUE4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "value4"), java.lang.String.valueOf(java.lang.Math.min(VALUE4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "value4", this.value4, value4));
   }

   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String VALUE5 = "value5";
   static int VALUE5_UPPER_LIMIT = -1;
   java.lang.String value5;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.String getValue5() {
      return value5;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public void setValue5(java.lang.String value5) throws wt.util.WTPropertyVetoException {
      value5Validate(value5);
      this.value5 = value5;
   }
   void value5Validate(java.lang.String value5) throws wt.util.WTPropertyVetoException {
      if (VALUE5_UPPER_LIMIT < 1) {
         try { VALUE5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("value5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VALUE5_UPPER_LIMIT = 200; }
      }
      if (value5 != null && !wt.fc.PersistenceHelper.checkStoredLength(value5.toString(), VALUE5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "value5"), java.lang.String.valueOf(java.lang.Math.min(VALUE5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "value5", this.value5, value5));
   }

   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String M_UNIT = "mUnit";
   static int M_UNIT_UPPER_LIMIT = -1;
   java.lang.String mUnit;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.String getMUnit() {
      return mUnit;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public void setMUnit(java.lang.String mUnit) throws wt.util.WTPropertyVetoException {
      mUnitValidate(mUnit);
      this.mUnit = mUnit;
   }
   void mUnitValidate(java.lang.String mUnit) throws wt.util.WTPropertyVetoException {
      if (M_UNIT_UPPER_LIMIT < 1) {
         try { M_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { M_UNIT_UPPER_LIMIT = 200; }
      }
      if (mUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(mUnit.toString(), M_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mUnit"), java.lang.String.valueOf(java.lang.Math.min(M_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mUnit", this.mUnit, mUnit));
   }

   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String ITEM_TYPE = "itemType";
   static int ITEM_TYPE_UPPER_LIMIT = -1;
   java.lang.String itemType;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.String getItemType() {
      return itemType;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
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
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String SORT = "sort";
   java.lang.Integer sort = 0;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public java.lang.Integer getSort() {
      return sort;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public void setSort(java.lang.Integer sort) throws wt.util.WTPropertyVetoException {
      sortValidate(sort);
      this.sort = sort;
   }
   void sortValidate(java.lang.Integer sort) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String CSINFO = "CSInfo";
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public static final java.lang.String CSINFO_REFERENCE = "CSInfoReference";
   wt.fc.ObjectReference CSInfoReference;
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public ext.ket.cost.entity.CSInfo getCSInfo() {
      return (CSInfoReference != null) ? (ext.ket.cost.entity.CSInfo) CSInfoReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public wt.fc.ObjectReference getCSInfoReference() {
      return CSInfoReference;
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public void setCSInfo(ext.ket.cost.entity.CSInfo the_CSInfo) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCSInfoReference(the_CSInfo == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CSInfo) the_CSInfo));
   }
   /**
    * @see ext.ket.cost.entity.CSInfoItem
    */
   public void setCSInfoReference(wt.fc.ObjectReference the_CSInfoReference) throws wt.util.WTPropertyVetoException {
      CSInfoReferenceValidate(the_CSInfoReference);
      CSInfoReference = (wt.fc.ObjectReference) the_CSInfoReference;
   }
   void CSInfoReferenceValidate(wt.fc.ObjectReference the_CSInfoReference) throws wt.util.WTPropertyVetoException {
      if (the_CSInfoReference == null || the_CSInfoReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "CSInfoReference") },
               new java.beans.PropertyChangeEvent(this, "CSInfoReference", this.CSInfoReference, CSInfoReference));
      if (the_CSInfoReference != null && the_CSInfoReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.CSInfo.class.isAssignableFrom(the_CSInfoReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "CSInfoReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "CSInfoReference", this.CSInfoReference, CSInfoReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -5908515275615033471L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( CSInfoReference );
      output.writeObject( itemType );
      output.writeObject( mUnit );
      output.writeObject( owner );
      output.writeObject( sort );
      output.writeObject( thePersistInfo );
      output.writeObject( value1 );
      output.writeObject( value2 );
      output.writeObject( value3 );
      output.writeObject( value4 );
      output.writeObject( value5 );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CSInfoItem) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "CSInfoReference", CSInfoReference, wt.fc.ObjectReference.class, true );
      output.setString( "itemType", itemType );
      output.setString( "mUnit", mUnit );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setIntObject( "sort", sort );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "value1", value1 );
      output.setString( "value2", value2 );
      output.setString( "value3", value3 );
      output.setString( "value4", value4 );
      output.setString( "value5", value5 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      CSInfoReference = (wt.fc.ObjectReference) input.readObject( "CSInfoReference", CSInfoReference, wt.fc.ObjectReference.class, true );
      itemType = input.getString( "itemType" );
      mUnit = input.getString( "mUnit" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      sort = input.getIntObject( "sort" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      value1 = input.getString( "value1" );
      value2 = input.getString( "value2" );
      value3 = input.getString( "value3" );
      value4 = input.getString( "value4" );
      value5 = input.getString( "value5" );
   }

   boolean readVersion_5908515275615033471L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      CSInfoReference = (wt.fc.ObjectReference) input.readObject();
      itemType = (java.lang.String) input.readObject();
      mUnit = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      sort = (java.lang.Integer) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      value1 = (java.lang.String) input.readObject();
      value2 = (java.lang.String) input.readObject();
      value3 = (java.lang.String) input.readObject();
      value4 = (java.lang.String) input.readObject();
      value5 = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CSInfoItem thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_5908515275615033471L( input, readSerialVersionUID, superDone );
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
