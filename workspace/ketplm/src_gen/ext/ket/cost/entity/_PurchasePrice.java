package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _PurchasePrice implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = PurchasePrice.class.getName();

   /**
    * 품번
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 품번
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 품번
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setPartNo(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      partNoValidate(partNo);
      this.partNo = partNo;
   }
   void partNoValidate(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      if (PART_NO_UPPER_LIMIT < 1) {
         try { PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NO_UPPER_LIMIT = 200; }
      }
      if (partNo != null && !wt.fc.PersistenceHelper.checkStoredLength(partNo.toString(), PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNo"), java.lang.String.valueOf(java.lang.Math.min(PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNo", this.partNo, partNo));
   }

   /**
    * 통화
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String CURRENCY = "currency";
   static int CURRENCY_UPPER_LIMIT = -1;
   java.lang.String currency;
   /**
    * 통화
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getCurrency() {
      return currency;
   }
   /**
    * 통화
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setCurrency(java.lang.String currency) throws wt.util.WTPropertyVetoException {
      currencyValidate(currency);
      this.currency = currency;
   }
   void currencyValidate(java.lang.String currency) throws wt.util.WTPropertyVetoException {
      if (CURRENCY_UPPER_LIMIT < 1) {
         try { CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("currency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CURRENCY_UPPER_LIMIT = 200; }
      }
      if (currency != null && !wt.fc.PersistenceHelper.checkStoredLength(currency.toString(), CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "currency"), java.lang.String.valueOf(java.lang.Math.min(CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "currency", this.currency, currency));
   }

   /**
    * 단위
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String UNIT = "unit";
   static int UNIT_UPPER_LIMIT = -1;
   java.lang.String unit;
   /**
    * 단위
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getUnit() {
      return unit;
   }
   /**
    * 단위
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setUnit(java.lang.String unit) throws wt.util.WTPropertyVetoException {
      unitValidate(unit);
      this.unit = unit;
   }
   void unitValidate(java.lang.String unit) throws wt.util.WTPropertyVetoException {
      if (UNIT_UPPER_LIMIT < 1) {
         try { UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("unit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { UNIT_UPPER_LIMIT = 200; }
      }
      if (unit != null && !wt.fc.PersistenceHelper.checkStoredLength(unit.toString(), UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "unit"), java.lang.String.valueOf(java.lang.Math.min(UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "unit", this.unit, unit));
   }

   /**
    * 단가
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String PRICE = "price";
   static int PRICE_UPPER_LIMIT = -1;
   java.lang.String price;
   /**
    * 단가
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getPrice() {
      return price;
   }
   /**
    * 단가
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setPrice(java.lang.String price) throws wt.util.WTPropertyVetoException {
      priceValidate(price);
      this.price = price;
   }
   void priceValidate(java.lang.String price) throws wt.util.WTPropertyVetoException {
      if (PRICE_UPPER_LIMIT < 1) {
         try { PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("price").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRICE_UPPER_LIMIT = 200; }
      }
      if (price != null && !wt.fc.PersistenceHelper.checkStoredLength(price.toString(), PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "price"), java.lang.String.valueOf(java.lang.Math.min(PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "price", this.price, price));
   }

   /**
    * 구매조직
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String PURCHASE_ORG = "purchaseOrg";
   static int PURCHASE_ORG_UPPER_LIMIT = -1;
   java.lang.String purchaseOrg;
   /**
    * 구매조직
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getPurchaseOrg() {
      return purchaseOrg;
   }
   /**
    * 구매조직
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setPurchaseOrg(java.lang.String purchaseOrg) throws wt.util.WTPropertyVetoException {
      purchaseOrgValidate(purchaseOrg);
      this.purchaseOrg = purchaseOrg;
   }
   void purchaseOrgValidate(java.lang.String purchaseOrg) throws wt.util.WTPropertyVetoException {
      if (PURCHASE_ORG_UPPER_LIMIT < 1) {
         try { PURCHASE_ORG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("purchaseOrg").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PURCHASE_ORG_UPPER_LIMIT = 200; }
      }
      if (purchaseOrg != null && !wt.fc.PersistenceHelper.checkStoredLength(purchaseOrg.toString(), PURCHASE_ORG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "purchaseOrg"), java.lang.String.valueOf(java.lang.Math.min(PURCHASE_ORG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "purchaseOrg", this.purchaseOrg, purchaseOrg));
   }

   /**
    * 공급업체
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String SUPPLIER_NAME = "supplierName";
   static int SUPPLIER_NAME_UPPER_LIMIT = -1;
   java.lang.String supplierName;
   /**
    * 공급업체
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getSupplierName() {
      return supplierName;
   }
   /**
    * 공급업체
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setSupplierName(java.lang.String supplierName) throws wt.util.WTPropertyVetoException {
      supplierNameValidate(supplierName);
      this.supplierName = supplierName;
   }
   void supplierNameValidate(java.lang.String supplierName) throws wt.util.WTPropertyVetoException {
      if (SUPPLIER_NAME_UPPER_LIMIT < 1) {
         try { SUPPLIER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("supplierName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUPPLIER_NAME_UPPER_LIMIT = 200; }
      }
      if (supplierName != null && !wt.fc.PersistenceHelper.checkStoredLength(supplierName.toString(), SUPPLIER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "supplierName"), java.lang.String.valueOf(java.lang.Math.min(SUPPLIER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "supplierName", this.supplierName, supplierName));
   }

   /**
    * 공급업체코드
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String SUPPLIER_CODE = "supplierCode";
   static int SUPPLIER_CODE_UPPER_LIMIT = -1;
   java.lang.String supplierCode;
   /**
    * 공급업체코드
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getSupplierCode() {
      return supplierCode;
   }
   /**
    * 공급업체코드
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setSupplierCode(java.lang.String supplierCode) throws wt.util.WTPropertyVetoException {
      supplierCodeValidate(supplierCode);
      this.supplierCode = supplierCode;
   }
   void supplierCodeValidate(java.lang.String supplierCode) throws wt.util.WTPropertyVetoException {
      if (SUPPLIER_CODE_UPPER_LIMIT < 1) {
         try { SUPPLIER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("supplierCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUPPLIER_CODE_UPPER_LIMIT = 200; }
      }
      if (supplierCode != null && !wt.fc.PersistenceHelper.checkStoredLength(supplierCode.toString(), SUPPLIER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "supplierCode"), java.lang.String.valueOf(java.lang.Math.min(SUPPLIER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "supplierCode", this.supplierCode, supplierCode));
   }

   /**
    * 유효종료일
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String VALID_DATE = "validDate";
   java.sql.Timestamp validDate;
   /**
    * 유효종료일
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.sql.Timestamp getValidDate() {
      return validDate;
   }
   /**
    * 유효종료일
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setValidDate(java.sql.Timestamp validDate) throws wt.util.WTPropertyVetoException {
      validDateValidate(validDate);
      this.validDate = validDate;
   }
   void validDateValidate(java.sql.Timestamp validDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 구매오더증빙일
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String PURCHASE_ORDER_DATE = "purchaseOrderDate";
   java.sql.Timestamp purchaseOrderDate;
   /**
    * 구매오더증빙일
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.sql.Timestamp getPurchaseOrderDate() {
      return purchaseOrderDate;
   }
   /**
    * 구매오더증빙일
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setPurchaseOrderDate(java.sql.Timestamp purchaseOrderDate) throws wt.util.WTPropertyVetoException {
      purchaseOrderDateValidate(purchaseOrderDate);
      this.purchaseOrderDate = purchaseOrderDate;
   }
   void purchaseOrderDateValidate(java.sql.Timestamp purchaseOrderDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * per
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public static final java.lang.String PER = "per";
   static int PER_UPPER_LIMIT = -1;
   java.lang.String per;
   /**
    * per
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public java.lang.String getPer() {
      return per;
   }
   /**
    * per
    *
    * @see ext.ket.cost.entity.PurchasePrice
    */
   public void setPer(java.lang.String per) throws wt.util.WTPropertyVetoException {
      perValidate(per);
      this.per = per;
   }
   void perValidate(java.lang.String per) throws wt.util.WTPropertyVetoException {
      if (PER_UPPER_LIMIT < 1) {
         try { PER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("per").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PER_UPPER_LIMIT = 200; }
      }
      if (per != null && !wt.fc.PersistenceHelper.checkStoredLength(per.toString(), PER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "per"), java.lang.String.valueOf(java.lang.Math.min(PER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "per", this.per, per));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1602841402972804864L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( currency );
      output.writeObject( owner );
      output.writeObject( partNo );
      output.writeObject( per );
      output.writeObject( price );
      output.writeObject( purchaseOrderDate );
      output.writeObject( purchaseOrg );
      output.writeObject( supplierCode );
      output.writeObject( supplierName );
      output.writeObject( thePersistInfo );
      output.writeObject( unit );
      output.writeObject( validDate );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.PurchasePrice) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "currency", currency );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "partNo", partNo );
      output.setString( "per", per );
      output.setString( "price", price );
      output.setTimestamp( "purchaseOrderDate", purchaseOrderDate );
      output.setString( "purchaseOrg", purchaseOrg );
      output.setString( "supplierCode", supplierCode );
      output.setString( "supplierName", supplierName );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "unit", unit );
      output.setTimestamp( "validDate", validDate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      currency = input.getString( "currency" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partNo = input.getString( "partNo" );
      per = input.getString( "per" );
      price = input.getString( "price" );
      purchaseOrderDate = input.getTimestamp( "purchaseOrderDate" );
      purchaseOrg = input.getString( "purchaseOrg" );
      supplierCode = input.getString( "supplierCode" );
      supplierName = input.getString( "supplierName" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      unit = input.getString( "unit" );
      validDate = input.getTimestamp( "validDate" );
   }

   boolean readVersion1602841402972804864L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      currency = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partNo = (java.lang.String) input.readObject();
      per = (java.lang.String) input.readObject();
      price = (java.lang.String) input.readObject();
      purchaseOrderDate = (java.sql.Timestamp) input.readObject();
      purchaseOrg = (java.lang.String) input.readObject();
      supplierCode = (java.lang.String) input.readObject();
      supplierName = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      unit = (java.lang.String) input.readObject();
      validDate = (java.sql.Timestamp) input.readObject();
      return true;
   }

   protected boolean readVersion( PurchasePrice thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1602841402972804864L( input, readSerialVersionUID, superDone );
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
