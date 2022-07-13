package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostQuantity implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostQuantity.class.getName();

   /**
    * 예상수량
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String QUANTITY = "quantity";
   static int QUANTITY_UPPER_LIMIT = -1;
   java.lang.String quantity;
   /**
    * 예상수량
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getQuantity() {
      return quantity;
   }
   /**
    * 예상수량
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setQuantity(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
      quantityValidate(quantity);
      this.quantity = quantity;
   }
   void quantityValidate(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_UPPER_LIMIT < 1) {
         try { QUANTITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_UPPER_LIMIT = 200; }
      }
      if (quantity != null && !wt.fc.PersistenceHelper.checkStoredLength(quantity.toString(), QUANTITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantity"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantity", this.quantity, quantity));
   }

   /**
    * 년차
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String YEAR = "year";
   static int YEAR_UPPER_LIMIT = -1;
   java.lang.String year;
   /**
    * 년차
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getYear() {
      return year;
   }
   /**
    * 년차
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setYear(java.lang.String year) throws wt.util.WTPropertyVetoException {
      yearValidate(year);
      this.year = year;
   }
   void yearValidate(java.lang.String year) throws wt.util.WTPropertyVetoException {
      if (YEAR_UPPER_LIMIT < 1) {
         try { YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR_UPPER_LIMIT = 200; }
      }
      if (year != null && !wt.fc.PersistenceHelper.checkStoredLength(year.toString(), YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year"), java.lang.String.valueOf(java.lang.Math.min(YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year", this.year, year));
   }

   /**
    * 버전
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String VERSION = "version";
   java.lang.Integer version = 0;
   /**
    * 버전
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.Integer getVersion() {
      return version;
   }
   /**
    * 버전
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setVersion(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String SALES_TARGET_COST_EXPR = "salesTargetCostExpr";
   static int SALES_TARGET_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String salesTargetCostExpr = "0";
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getSalesTargetCostExpr() {
      return salesTargetCostExpr;
   }
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setSalesTargetCostExpr(java.lang.String salesTargetCostExpr) throws wt.util.WTPropertyVetoException {
      salesTargetCostExprValidate(salesTargetCostExpr);
      this.salesTargetCostExpr = salesTargetCostExpr;
   }
   void salesTargetCostExprValidate(java.lang.String salesTargetCostExpr) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_COST_EXPR_UPPER_LIMIT < 1) {
         try { SALES_TARGET_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTargetCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (salesTargetCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTargetCostExpr.toString(), SALES_TARGET_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTargetCostExpr"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTargetCostExpr", this.salesTargetCostExpr, salesTargetCostExpr));
   }

   /**
    * 완제품입고
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String PAY_PLACE = "payPlace";
   static int PAY_PLACE_UPPER_LIMIT = -1;
   java.lang.String payPlace;
   /**
    * 완제품입고
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getPayPlace() {
      return payPlace;
   }
   /**
    * 완제품입고
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setPayPlace(java.lang.String payPlace) throws wt.util.WTPropertyVetoException {
      payPlaceValidate(payPlace);
      this.payPlace = payPlace;
   }
   void payPlaceValidate(java.lang.String payPlace) throws wt.util.WTPropertyVetoException {
      if (PAY_PLACE_UPPER_LIMIT < 1) {
         try { PAY_PLACE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payPlace").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_PLACE_UPPER_LIMIT = 200; }
      }
      if (payPlace != null && !wt.fc.PersistenceHelper.checkStoredLength(payPlace.toString(), PAY_PLACE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payPlace"), java.lang.String.valueOf(java.lang.Math.min(PAY_PLACE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payPlace", this.payPlace, payPlace));
   }

   /**
    * sop
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String SOP_YEAR = "sopYear";
   static int SOP_YEAR_UPPER_LIMIT = -1;
   java.lang.String sopYear;
   /**
    * sop
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getSopYear() {
      return sopYear;
   }
   /**
    * sop
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setSopYear(java.lang.String sopYear) throws wt.util.WTPropertyVetoException {
      sopYearValidate(sopYear);
      this.sopYear = sopYear;
   }
   void sopYearValidate(java.lang.String sopYear) throws wt.util.WTPropertyVetoException {
      if (SOP_YEAR_UPPER_LIMIT < 1) {
         try { SOP_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sopYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SOP_YEAR_UPPER_LIMIT = 200; }
      }
      if (sopYear != null && !wt.fc.PersistenceHelper.checkStoredLength(sopYear.toString(), SOP_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sopYear"), java.lang.String.valueOf(java.lang.Math.min(SOP_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sopYear", this.sopYear, sopYear));
   }

   /**
    * 입고처
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String DELIVER_NAME = "deliverName";
   static int DELIVER_NAME_UPPER_LIMIT = -1;
   java.lang.String deliverName;
   /**
    * 입고처
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getDeliverName() {
      return deliverName;
   }
   /**
    * 입고처
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setDeliverName(java.lang.String deliverName) throws wt.util.WTPropertyVetoException {
      deliverNameValidate(deliverName);
      this.deliverName = deliverName;
   }
   void deliverNameValidate(java.lang.String deliverName) throws wt.util.WTPropertyVetoException {
      if (DELIVER_NAME_UPPER_LIMIT < 1) {
         try { DELIVER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deliverName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DELIVER_NAME_UPPER_LIMIT = 200; }
      }
      if (deliverName != null && !wt.fc.PersistenceHelper.checkStoredLength(deliverName.toString(), DELIVER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deliverName"), java.lang.String.valueOf(java.lang.Math.min(DELIVER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deliverName", this.deliverName, deliverName));
   }

   /**
    * CR적용율
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String DISPOSABLE_CR = "disposableCr";
   static int DISPOSABLE_CR_UPPER_LIMIT = -1;
   java.lang.String disposableCr = "0";
   /**
    * CR적용율
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getDisposableCr() {
      return disposableCr;
   }
   /**
    * CR적용율
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setDisposableCr(java.lang.String disposableCr) throws wt.util.WTPropertyVetoException {
      disposableCrValidate(disposableCr);
      this.disposableCr = disposableCr;
   }
   void disposableCrValidate(java.lang.String disposableCr) throws wt.util.WTPropertyVetoException {
      if (DISPOSABLE_CR_UPPER_LIMIT < 1) {
         try { DISPOSABLE_CR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("disposableCr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DISPOSABLE_CR_UPPER_LIMIT = 200; }
      }
      if (disposableCr != null && !wt.fc.PersistenceHelper.checkStoredLength(disposableCr.toString(), DISPOSABLE_CR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "disposableCr"), java.lang.String.valueOf(java.lang.Math.min(DISPOSABLE_CR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "disposableCr", this.disposableCr, disposableCr));
   }

   /**
    * CR적용년수
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String DISPOSABLE_APPLY_YEAR = "disposableApplyYear";
   static int DISPOSABLE_APPLY_YEAR_UPPER_LIMIT = -1;
   java.lang.String disposableApplyYear = "0";
   /**
    * CR적용년수
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getDisposableApplyYear() {
      return disposableApplyYear;
   }
   /**
    * CR적용년수
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setDisposableApplyYear(java.lang.String disposableApplyYear) throws wt.util.WTPropertyVetoException {
      disposableApplyYearValidate(disposableApplyYear);
      this.disposableApplyYear = disposableApplyYear;
   }
   void disposableApplyYearValidate(java.lang.String disposableApplyYear) throws wt.util.WTPropertyVetoException {
      if (DISPOSABLE_APPLY_YEAR_UPPER_LIMIT < 1) {
         try { DISPOSABLE_APPLY_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("disposableApplyYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DISPOSABLE_APPLY_YEAR_UPPER_LIMIT = 200; }
      }
      if (disposableApplyYear != null && !wt.fc.PersistenceHelper.checkStoredLength(disposableApplyYear.toString(), DISPOSABLE_APPLY_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "disposableApplyYear"), java.lang.String.valueOf(java.lang.Math.min(DISPOSABLE_APPLY_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "disposableApplyYear", this.disposableApplyYear, disposableApplyYear));
   }

   /**
    * 최종안 여부
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String LASTEST = "lastest";
   static int LASTEST_UPPER_LIMIT = -1;
   java.lang.String lastest = "0";
   /**
    * 최종안 여부
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getLastest() {
      return lastest;
   }
   /**
    * 최종안 여부
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setLastest(java.lang.String lastest) throws wt.util.WTPropertyVetoException {
      lastestValidate(lastest);
      this.lastest = lastest;
   }
   void lastestValidate(java.lang.String lastest) throws wt.util.WTPropertyVetoException {
      if (LASTEST_UPPER_LIMIT < 1) {
         try { LASTEST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastest").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LASTEST_UPPER_LIMIT = 200; }
      }
      if (lastest != null && !wt.fc.PersistenceHelper.checkStoredLength(lastest.toString(), LASTEST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastest"), java.lang.String.valueOf(java.lang.Math.min(LASTEST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastest", this.lastest, lastest));
   }

   /**
    * 판가구분
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String SALES_TARGET_GB = "salesTargetGb";
   static int SALES_TARGET_GB_UPPER_LIMIT = -1;
   java.lang.String salesTargetGb;
   /**
    * 판가구분
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public java.lang.String getSalesTargetGb() {
      return salesTargetGb;
   }
   /**
    * 판가구분
    *
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setSalesTargetGb(java.lang.String salesTargetGb) throws wt.util.WTPropertyVetoException {
      salesTargetGbValidate(salesTargetGb);
      this.salesTargetGb = salesTargetGb;
   }
   void salesTargetGbValidate(java.lang.String salesTargetGb) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_GB_UPPER_LIMIT < 1) {
         try { SALES_TARGET_GB_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTargetGb").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_GB_UPPER_LIMIT = 200; }
      }
      if (salesTargetGb != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTargetGb.toString(), SALES_TARGET_GB_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTargetGb"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_GB_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTargetGb", this.salesTargetGb, salesTargetGb));
   }

   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String QUANTITY_MASTER = "quantityMaster";
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String QUANTITY_MASTER_REFERENCE = "quantityMasterReference";
   wt.fc.ObjectReference quantityMasterReference;
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public ext.ket.cost.entity.ProductMaster getQuantityMaster() {
      return (quantityMasterReference != null) ? (ext.ket.cost.entity.ProductMaster) quantityMasterReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public wt.fc.ObjectReference getQuantityMasterReference() {
      return quantityMasterReference;
   }
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setQuantityMaster(ext.ket.cost.entity.ProductMaster the_quantityMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setQuantityMasterReference(the_quantityMaster == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.ProductMaster) the_quantityMaster));
   }
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setQuantityMasterReference(wt.fc.ObjectReference the_quantityMasterReference) throws wt.util.WTPropertyVetoException {
      quantityMasterReferenceValidate(the_quantityMasterReference);
      quantityMasterReference = (wt.fc.ObjectReference) the_quantityMasterReference;
   }
   void quantityMasterReferenceValidate(wt.fc.ObjectReference the_quantityMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_quantityMasterReference != null && the_quantityMasterReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.ProductMaster.class.isAssignableFrom(the_quantityMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "quantityMasterReference", this.quantityMasterReference, quantityMasterReference));
   }

   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String COST_PART = "costPart";
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public static final java.lang.String COST_PART_REFERENCE = "costPartReference";
   wt.fc.ObjectReference costPartReference;
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public ext.ket.cost.entity.CostPart getCostPart() {
      return (costPartReference != null) ? (ext.ket.cost.entity.CostPart) costPartReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public wt.fc.ObjectReference getCostPartReference() {
      return costPartReference;
   }
   /**
    * @see ext.ket.cost.entity.CostQuantity
    */
   public void setCostPart(ext.ket.cost.entity.CostPart the_costPart) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostPartReference(the_costPart == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostPart) the_costPart));
   }
   /**
    * @see ext.ket.cost.entity.CostQuantity
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

   public static final long EXTERNALIZATION_VERSION_UID = -1643843059358003718L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( costPartReference );
      output.writeObject( deliverName );
      output.writeObject( disposableApplyYear );
      output.writeObject( disposableCr );
      output.writeObject( lastest );
      output.writeObject( owner );
      output.writeObject( payPlace );
      output.writeObject( quantity );
      output.writeObject( quantityMasterReference );
      output.writeObject( salesTargetCostExpr );
      output.writeObject( salesTargetGb );
      output.writeObject( sopYear );
      output.writeObject( thePersistInfo );
      output.writeObject( version );
      output.writeObject( year );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostQuantity) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      output.setString( "deliverName", deliverName );
      output.setString( "disposableApplyYear", disposableApplyYear );
      output.setString( "disposableCr", disposableCr );
      output.setString( "lastest", lastest );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "payPlace", payPlace );
      output.setString( "quantity", quantity );
      output.writeObject( "quantityMasterReference", quantityMasterReference, wt.fc.ObjectReference.class, true );
      output.setString( "salesTargetCostExpr", salesTargetCostExpr );
      output.setString( "salesTargetGb", salesTargetGb );
      output.setString( "sopYear", sopYear );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setIntObject( "version", version );
      output.setString( "year", year );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      costPartReference = (wt.fc.ObjectReference) input.readObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      deliverName = input.getString( "deliverName" );
      disposableApplyYear = input.getString( "disposableApplyYear" );
      disposableCr = input.getString( "disposableCr" );
      lastest = input.getString( "lastest" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      payPlace = input.getString( "payPlace" );
      quantity = input.getString( "quantity" );
      quantityMasterReference = (wt.fc.ObjectReference) input.readObject( "quantityMasterReference", quantityMasterReference, wt.fc.ObjectReference.class, true );
      salesTargetCostExpr = input.getString( "salesTargetCostExpr" );
      salesTargetGb = input.getString( "salesTargetGb" );
      sopYear = input.getString( "sopYear" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      version = input.getIntObject( "version" );
      year = input.getString( "year" );
   }

   boolean readVersion_1643843059358003718L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      costPartReference = (wt.fc.ObjectReference) input.readObject();
      deliverName = (java.lang.String) input.readObject();
      disposableApplyYear = (java.lang.String) input.readObject();
      disposableCr = (java.lang.String) input.readObject();
      lastest = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      payPlace = (java.lang.String) input.readObject();
      quantity = (java.lang.String) input.readObject();
      quantityMasterReference = (wt.fc.ObjectReference) input.readObject();
      salesTargetCostExpr = (java.lang.String) input.readObject();
      salesTargetGb = (java.lang.String) input.readObject();
      sopYear = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      version = (java.lang.Integer) input.readObject();
      year = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostQuantity thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1643843059358003718L( input, readSerialVersionUID, superDone );
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
