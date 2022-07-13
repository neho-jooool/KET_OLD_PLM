package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostPacking implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostPacking.class.getName();

   /**
    * 포장재단가
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKING_PRICE = "packingPrice";
   static int PACKING_PRICE_UPPER_LIMIT = -1;
   java.lang.String packingPrice;
   /**
    * 포장재단가
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getPackingPrice() {
      return packingPrice;
   }
   /**
    * 포장재단가
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackingPrice(java.lang.String packingPrice) throws wt.util.WTPropertyVetoException {
      packingPriceValidate(packingPrice);
      this.packingPrice = packingPrice;
   }
   void packingPriceValidate(java.lang.String packingPrice) throws wt.util.WTPropertyVetoException {
      if (PACKING_PRICE_UPPER_LIMIT < 1) {
         try { PACKING_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packingPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACKING_PRICE_UPPER_LIMIT = 200; }
      }
      if (packingPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(packingPrice.toString(), PACKING_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packingPrice"), java.lang.String.valueOf(java.lang.Math.min(PACKING_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packingPrice", this.packingPrice, packingPrice));
   }

   /**
    * 포장재수량
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACK_QUANTITY = "packQuantity";
   static int PACK_QUANTITY_UPPER_LIMIT = -1;
   java.lang.String packQuantity;
   /**
    * 포장재수량
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getPackQuantity() {
      return packQuantity;
   }
   /**
    * 포장재수량
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackQuantity(java.lang.String packQuantity) throws wt.util.WTPropertyVetoException {
      packQuantityValidate(packQuantity);
      this.packQuantity = packQuantity;
   }
   void packQuantityValidate(java.lang.String packQuantity) throws wt.util.WTPropertyVetoException {
      if (PACK_QUANTITY_UPPER_LIMIT < 1) {
         try { PACK_QUANTITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packQuantity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_QUANTITY_UPPER_LIMIT = 200; }
      }
      if (packQuantity != null && !wt.fc.PersistenceHelper.checkStoredLength(packQuantity.toString(), PACK_QUANTITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packQuantity"), java.lang.String.valueOf(java.lang.Math.min(PACK_QUANTITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packQuantity", this.packQuantity, packQuantity));
   }

   /**
    * 포장재단위(화폐단위)
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String CURRENCY_CODE = "currencyCode";
   static int CURRENCY_CODE_UPPER_LIMIT = -1;
   java.lang.String currencyCode;
   /**
    * 포장재단위(화폐단위)
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getCurrencyCode() {
      return currencyCode;
   }
   /**
    * 포장재단위(화폐단위)
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setCurrencyCode(java.lang.String currencyCode) throws wt.util.WTPropertyVetoException {
      currencyCodeValidate(currencyCode);
      this.currencyCode = currencyCode;
   }
   void currencyCodeValidate(java.lang.String currencyCode) throws wt.util.WTPropertyVetoException {
      if (CURRENCY_CODE_UPPER_LIMIT < 1) {
         try { CURRENCY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("currencyCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CURRENCY_CODE_UPPER_LIMIT = 200; }
      }
      if (currencyCode != null && !wt.fc.PersistenceHelper.checkStoredLength(currencyCode.toString(), CURRENCY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "currencyCode"), java.lang.String.valueOf(java.lang.Math.min(CURRENCY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "currencyCode", this.currencyCode, currencyCode));
   }

   /**
    * 단위
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKUNIT = "packunit";
   static int PACKUNIT_UPPER_LIMIT = -1;
   java.lang.String packunit;
   /**
    * 단위
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getPackunit() {
      return packunit;
   }
   /**
    * 단위
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackunit(java.lang.String packunit) throws wt.util.WTPropertyVetoException {
      packunitValidate(packunit);
      this.packunit = packunit;
   }
   void packunitValidate(java.lang.String packunit) throws wt.util.WTPropertyVetoException {
      if (PACKUNIT_UPPER_LIMIT < 1) {
         try { PACKUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packunit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACKUNIT_UPPER_LIMIT = 200; }
      }
      if (packunit != null && !wt.fc.PersistenceHelper.checkStoredLength(packunit.toString(), PACKUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packunit"), java.lang.String.valueOf(java.lang.Math.min(PACKUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packunit", this.packunit, packunit));
   }

   /**
    * 포장재합계
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKQUANTITY_SUM = "packquantitySum";
   static int PACKQUANTITY_SUM_UPPER_LIMIT = -1;
   java.lang.String packquantitySum;
   /**
    * 포장재합계
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getPackquantitySum() {
      return packquantitySum;
   }
   /**
    * 포장재합계
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackquantitySum(java.lang.String packquantitySum) throws wt.util.WTPropertyVetoException {
      packquantitySumValidate(packquantitySum);
      this.packquantitySum = packquantitySum;
   }
   void packquantitySumValidate(java.lang.String packquantitySum) throws wt.util.WTPropertyVetoException {
      if (PACKQUANTITY_SUM_UPPER_LIMIT < 1) {
         try { PACKQUANTITY_SUM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packquantitySum").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACKQUANTITY_SUM_UPPER_LIMIT = 200; }
      }
      if (packquantitySum != null && !wt.fc.PersistenceHelper.checkStoredLength(packquantitySum.toString(), PACKQUANTITY_SUM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packquantitySum"), java.lang.String.valueOf(java.lang.Math.min(PACKQUANTITY_SUM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packquantitySum", this.packquantitySum, packquantitySum));
   }

   /**
    * 회수여부(회수 : 미회수)
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String COLLECT_OPTION = "collectOption";
   static int COLLECT_OPTION_UPPER_LIMIT = -1;
   java.lang.String collectOption;
   /**
    * 회수여부(회수 : 미회수)
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getCollectOption() {
      return collectOption;
   }
   /**
    * 회수여부(회수 : 미회수)
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setCollectOption(java.lang.String collectOption) throws wt.util.WTPropertyVetoException {
      collectOptionValidate(collectOption);
      this.collectOption = collectOption;
   }
   void collectOptionValidate(java.lang.String collectOption) throws wt.util.WTPropertyVetoException {
      if (COLLECT_OPTION_UPPER_LIMIT < 1) {
         try { COLLECT_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("collectOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLLECT_OPTION_UPPER_LIMIT = 200; }
      }
      if (collectOption != null && !wt.fc.PersistenceHelper.checkStoredLength(collectOption.toString(), COLLECT_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collectOption"), java.lang.String.valueOf(java.lang.Math.min(COLLECT_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "collectOption", this.collectOption, collectOption));
   }

   /**
    * 보유개월
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String HAVE_MONTH = "haveMonth";
   static int HAVE_MONTH_UPPER_LIMIT = -1;
   java.lang.String haveMonth;
   /**
    * 보유개월
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getHaveMonth() {
      return haveMonth;
   }
   /**
    * 보유개월
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setHaveMonth(java.lang.String haveMonth) throws wt.util.WTPropertyVetoException {
      haveMonthValidate(haveMonth);
      this.haveMonth = haveMonth;
   }
   void haveMonthValidate(java.lang.String haveMonth) throws wt.util.WTPropertyVetoException {
      if (HAVE_MONTH_UPPER_LIMIT < 1) {
         try { HAVE_MONTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("haveMonth").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HAVE_MONTH_UPPER_LIMIT = 200; }
      }
      if (haveMonth != null && !wt.fc.PersistenceHelper.checkStoredLength(haveMonth.toString(), HAVE_MONTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "haveMonth"), java.lang.String.valueOf(java.lang.Math.min(HAVE_MONTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "haveMonth", this.haveMonth, haveMonth));
   }

   /**
    * 회수율
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String COLLECT_RATE = "collectRate";
   static int COLLECT_RATE_UPPER_LIMIT = -1;
   java.lang.String collectRate;
   /**
    * 회수율
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getCollectRate() {
      return collectRate;
   }
   /**
    * 회수율
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setCollectRate(java.lang.String collectRate) throws wt.util.WTPropertyVetoException {
      collectRateValidate(collectRate);
      this.collectRate = collectRate;
   }
   void collectRateValidate(java.lang.String collectRate) throws wt.util.WTPropertyVetoException {
      if (COLLECT_RATE_UPPER_LIMIT < 1) {
         try { COLLECT_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("collectRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLLECT_RATE_UPPER_LIMIT = 200; }
      }
      if (collectRate != null && !wt.fc.PersistenceHelper.checkStoredLength(collectRate.toString(), COLLECT_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collectRate"), java.lang.String.valueOf(java.lang.Math.min(COLLECT_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "collectRate", this.collectRate, collectRate));
   }

   /**
    * 보유수량
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String HAVE_UNIT = "haveUnit";
   static int HAVE_UNIT_UPPER_LIMIT = -1;
   java.lang.String haveUnit;
   /**
    * 보유수량
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getHaveUnit() {
      return haveUnit;
   }
   /**
    * 보유수량
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setHaveUnit(java.lang.String haveUnit) throws wt.util.WTPropertyVetoException {
      haveUnitValidate(haveUnit);
      this.haveUnit = haveUnit;
   }
   void haveUnitValidate(java.lang.String haveUnit) throws wt.util.WTPropertyVetoException {
      if (HAVE_UNIT_UPPER_LIMIT < 1) {
         try { HAVE_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("haveUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HAVE_UNIT_UPPER_LIMIT = 200; }
      }
      if (haveUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(haveUnit.toString(), HAVE_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "haveUnit"), java.lang.String.valueOf(java.lang.Math.min(HAVE_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "haveUnit", this.haveUnit, haveUnit));
   }

   /**
    * 개별포장비
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACK_UNIT_PRICE = "packUnitPrice";
   static int PACK_UNIT_PRICE_UPPER_LIMIT = -1;
   java.lang.String packUnitPrice;
   /**
    * 개별포장비
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getPackUnitPrice() {
      return packUnitPrice;
   }
   /**
    * 개별포장비
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackUnitPrice(java.lang.String packUnitPrice) throws wt.util.WTPropertyVetoException {
      packUnitPriceValidate(packUnitPrice);
      this.packUnitPrice = packUnitPrice;
   }
   void packUnitPriceValidate(java.lang.String packUnitPrice) throws wt.util.WTPropertyVetoException {
      if (PACK_UNIT_PRICE_UPPER_LIMIT < 1) {
         try { PACK_UNIT_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packUnitPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_UNIT_PRICE_UPPER_LIMIT = 200; }
      }
      if (packUnitPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(packUnitPrice.toString(), PACK_UNIT_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packUnitPrice"), java.lang.String.valueOf(java.lang.Math.min(PACK_UNIT_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packUnitPrice", this.packUnitPrice, packUnitPrice));
   }

   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKING_NOTE = "packingNote";
   static int PACKING_NOTE_UPPER_LIMIT = -1;
   java.lang.String packingNote;
   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.String getPackingNote() {
      return packingNote;
   }
   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackingNote(java.lang.String packingNote) throws wt.util.WTPropertyVetoException {
      packingNoteValidate(packingNote);
      this.packingNote = packingNote;
   }
   void packingNoteValidate(java.lang.String packingNote) throws wt.util.WTPropertyVetoException {
      if (PACKING_NOTE_UPPER_LIMIT < 1) {
         try { PACKING_NOTE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packingNote").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACKING_NOTE_UPPER_LIMIT = 200; }
      }
      if (packingNote != null && !wt.fc.PersistenceHelper.checkStoredLength(packingNote.toString(), PACKING_NOTE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packingNote"), java.lang.String.valueOf(java.lang.Math.min(PACKING_NOTE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packingNote", this.packingNote, packingNote));
   }

   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String VERSION = "version";
   java.lang.Integer version = 0;
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public java.lang.Integer getVersion() {
      return version;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setVersion(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKING_CODE = "packingCode";
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKING_CODE_REFERENCE = "packingCodeReference";
   wt.fc.ObjectReference packingCodeReference;
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public e3ps.common.code.NumberCode getPackingCode() {
      return (packingCodeReference != null) ? (e3ps.common.code.NumberCode) packingCodeReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public wt.fc.ObjectReference getPackingCodeReference() {
      return packingCodeReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackingCode(e3ps.common.code.NumberCode the_packingCode) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPackingCodeReference(the_packingCode == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_packingCode));
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackingCodeReference(wt.fc.ObjectReference the_packingCodeReference) throws wt.util.WTPropertyVetoException {
      packingCodeReferenceValidate(the_packingCodeReference);
      packingCodeReference = (wt.fc.ObjectReference) the_packingCodeReference;
   }
   void packingCodeReferenceValidate(wt.fc.ObjectReference the_packingCodeReference) throws wt.util.WTPropertyVetoException {
      if (the_packingCodeReference == null || the_packingCodeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packingCodeReference") },
               new java.beans.PropertyChangeEvent(this, "packingCodeReference", this.packingCodeReference, packingCodeReference));
      if (the_packingCodeReference != null && the_packingCodeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_packingCodeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packingCodeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "packingCodeReference", this.packingCodeReference, packingCodeReference));
   }

   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKING_MASTER = "packingMaster";
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PACKING_MASTER_REFERENCE = "packingMasterReference";
   wt.fc.ObjectReference packingMasterReference;
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public ext.ket.cost.entity.ProductMaster getPackingMaster() {
      return (packingMasterReference != null) ? (ext.ket.cost.entity.ProductMaster) packingMasterReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public wt.fc.ObjectReference getPackingMasterReference() {
      return packingMasterReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackingMaster(ext.ket.cost.entity.ProductMaster the_packingMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPackingMasterReference(the_packingMaster == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.ProductMaster) the_packingMaster));
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPackingMasterReference(wt.fc.ObjectReference the_packingMasterReference) throws wt.util.WTPropertyVetoException {
      packingMasterReferenceValidate(the_packingMasterReference);
      packingMasterReference = (wt.fc.ObjectReference) the_packingMasterReference;
   }
   void packingMasterReferenceValidate(wt.fc.ObjectReference the_packingMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_packingMasterReference != null && the_packingMasterReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.ProductMaster.class.isAssignableFrom(the_packingMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packingMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "packingMasterReference", this.packingMasterReference, packingMasterReference));
   }

   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String CURRENCY_NUMBER_CODE = "currencyNumberCode";
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String CURRENCY_NUMBER_CODE_REFERENCE = "currencyNumberCodeReference";
   wt.fc.ObjectReference currencyNumberCodeReference;
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public e3ps.common.code.NumberCode getCurrencyNumberCode() {
      return (currencyNumberCodeReference != null) ? (e3ps.common.code.NumberCode) currencyNumberCodeReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public wt.fc.ObjectReference getCurrencyNumberCodeReference() {
      return currencyNumberCodeReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setCurrencyNumberCode(e3ps.common.code.NumberCode the_currencyNumberCode) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCurrencyNumberCodeReference(the_currencyNumberCode == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_currencyNumberCode));
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setCurrencyNumberCodeReference(wt.fc.ObjectReference the_currencyNumberCodeReference) throws wt.util.WTPropertyVetoException {
      currencyNumberCodeReferenceValidate(the_currencyNumberCodeReference);
      currencyNumberCodeReference = (wt.fc.ObjectReference) the_currencyNumberCodeReference;
   }
   void currencyNumberCodeReferenceValidate(wt.fc.ObjectReference the_currencyNumberCodeReference) throws wt.util.WTPropertyVetoException {
      if (the_currencyNumberCodeReference == null || the_currencyNumberCodeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "currencyNumberCodeReference") },
               new java.beans.PropertyChangeEvent(this, "currencyNumberCodeReference", this.currencyNumberCodeReference, currencyNumberCodeReference));
      if (the_currencyNumberCodeReference != null && the_currencyNumberCodeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_currencyNumberCodeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "currencyNumberCodeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "currencyNumberCodeReference", this.currencyNumberCodeReference, currencyNumberCodeReference));
   }

   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String COST_PART = "costPart";
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String COST_PART_REFERENCE = "costPartReference";
   wt.fc.ObjectReference costPartReference;
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public ext.ket.cost.entity.CostPart getCostPart() {
      return (costPartReference != null) ? (ext.ket.cost.entity.CostPart) costPartReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public wt.fc.ObjectReference getCostPartReference() {
      return costPartReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setCostPart(ext.ket.cost.entity.CostPart the_costPart) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostPartReference(the_costPart == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostPart) the_costPart));
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setCostPartReference(wt.fc.ObjectReference the_costPartReference) throws wt.util.WTPropertyVetoException {
      costPartReferenceValidate(the_costPartReference);
      costPartReference = (wt.fc.ObjectReference) the_costPartReference;
   }
   void costPartReferenceValidate(wt.fc.ObjectReference the_costPartReference) throws wt.util.WTPropertyVetoException {
      if (the_costPartReference != null && the_costPartReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.CostPart.class.isAssignableFrom(the_costPartReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costPartReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "costPartReference", this.costPartReference, costPartReference));
   }

   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PART_LIST = "partList";
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public static final java.lang.String PART_LIST_REFERENCE = "partListReference";
   wt.fc.ObjectReference partListReference;
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public ext.ket.cost.entity.PartList getPartList() {
      return (partListReference != null) ? (ext.ket.cost.entity.PartList) partListReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public wt.fc.ObjectReference getPartListReference() {
      return partListReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPartList(ext.ket.cost.entity.PartList the_partList) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartListReference(the_partList == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.PartList) the_partList));
   }
   /**
    * @see ext.ket.cost.entity.CostPacking
    */
   public void setPartListReference(wt.fc.ObjectReference the_partListReference) throws wt.util.WTPropertyVetoException {
      partListReferenceValidate(the_partListReference);
      partListReference = (wt.fc.ObjectReference) the_partListReference;
   }
   void partListReferenceValidate(wt.fc.ObjectReference the_partListReference) throws wt.util.WTPropertyVetoException {
      if (the_partListReference != null && the_partListReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.PartList.class.isAssignableFrom(the_partListReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partListReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "partListReference", this.partListReference, partListReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 6534739441566529647L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( collectOption );
      output.writeObject( collectRate );
      output.writeObject( costPartReference );
      output.writeObject( currencyCode );
      output.writeObject( currencyNumberCodeReference );
      output.writeObject( haveMonth );
      output.writeObject( haveUnit );
      output.writeObject( owner );
      output.writeObject( packQuantity );
      output.writeObject( packUnitPrice );
      output.writeObject( packingCodeReference );
      output.writeObject( packingMasterReference );
      output.writeObject( packingNote );
      output.writeObject( packingPrice );
      output.writeObject( packquantitySum );
      output.writeObject( packunit );
      output.writeObject( partListReference );
      output.writeObject( thePersistInfo );
      output.writeObject( version );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostPacking) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "collectOption", collectOption );
      output.setString( "collectRate", collectRate );
      output.writeObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      output.setString( "currencyCode", currencyCode );
      output.writeObject( "currencyNumberCodeReference", currencyNumberCodeReference, wt.fc.ObjectReference.class, true );
      output.setString( "haveMonth", haveMonth );
      output.setString( "haveUnit", haveUnit );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "packQuantity", packQuantity );
      output.setString( "packUnitPrice", packUnitPrice );
      output.writeObject( "packingCodeReference", packingCodeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "packingMasterReference", packingMasterReference, wt.fc.ObjectReference.class, true );
      output.setString( "packingNote", packingNote );
      output.setString( "packingPrice", packingPrice );
      output.setString( "packquantitySum", packquantitySum );
      output.setString( "packunit", packunit );
      output.writeObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setIntObject( "version", version );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      collectOption = input.getString( "collectOption" );
      collectRate = input.getString( "collectRate" );
      costPartReference = (wt.fc.ObjectReference) input.readObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      currencyCode = input.getString( "currencyCode" );
      currencyNumberCodeReference = (wt.fc.ObjectReference) input.readObject( "currencyNumberCodeReference", currencyNumberCodeReference, wt.fc.ObjectReference.class, true );
      haveMonth = input.getString( "haveMonth" );
      haveUnit = input.getString( "haveUnit" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      packQuantity = input.getString( "packQuantity" );
      packUnitPrice = input.getString( "packUnitPrice" );
      packingCodeReference = (wt.fc.ObjectReference) input.readObject( "packingCodeReference", packingCodeReference, wt.fc.ObjectReference.class, true );
      packingMasterReference = (wt.fc.ObjectReference) input.readObject( "packingMasterReference", packingMasterReference, wt.fc.ObjectReference.class, true );
      packingNote = input.getString( "packingNote" );
      packingPrice = input.getString( "packingPrice" );
      packquantitySum = input.getString( "packquantitySum" );
      packunit = input.getString( "packunit" );
      partListReference = (wt.fc.ObjectReference) input.readObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      version = input.getIntObject( "version" );
   }

   boolean readVersion6534739441566529647L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      collectOption = (java.lang.String) input.readObject();
      collectRate = (java.lang.String) input.readObject();
      costPartReference = (wt.fc.ObjectReference) input.readObject();
      currencyCode = (java.lang.String) input.readObject();
      currencyNumberCodeReference = (wt.fc.ObjectReference) input.readObject();
      haveMonth = (java.lang.String) input.readObject();
      haveUnit = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      packQuantity = (java.lang.String) input.readObject();
      packUnitPrice = (java.lang.String) input.readObject();
      packingCodeReference = (wt.fc.ObjectReference) input.readObject();
      packingMasterReference = (wt.fc.ObjectReference) input.readObject();
      packingNote = (java.lang.String) input.readObject();
      packingPrice = (java.lang.String) input.readObject();
      packquantitySum = (java.lang.String) input.readObject();
      packunit = (java.lang.String) input.readObject();
      partListReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      version = (java.lang.Integer) input.readObject();
      return true;
   }

   protected boolean readVersion( CostPacking thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6534739441566529647L( input, readSerialVersionUID, superDone );
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
