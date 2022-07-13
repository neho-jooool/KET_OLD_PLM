package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostAnalysis implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostAnalysis.class.getName();

   /**
    * 년차
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String YEAR = "year";
   java.lang.Integer year;
   /**
    * 년차
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.Integer getYear() {
      return year;
   }
   /**
    * 년차
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setYear(java.lang.Integer year) throws wt.util.WTPropertyVetoException {
      yearValidate(year);
      this.year = year;
   }
   void yearValidate(java.lang.Integer year) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 지정품판가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String APPOINT_SALES = "appointSales";
   static int APPOINT_SALES_UPPER_LIMIT = -1;
   java.lang.String appointSales = "0";
   /**
    * 지정품판가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getAppointSales() {
      return appointSales;
   }
   /**
    * 지정품판가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setAppointSales(java.lang.String appointSales) throws wt.util.WTPropertyVetoException {
      appointSalesValidate(appointSales);
      this.appointSales = appointSales;
   }
   void appointSalesValidate(java.lang.String appointSales) throws wt.util.WTPropertyVetoException {
      if (APPOINT_SALES_UPPER_LIMIT < 1) {
         try { APPOINT_SALES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("appointSales").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPOINT_SALES_UPPER_LIMIT = 200; }
      }
      if (appointSales != null && !wt.fc.PersistenceHelper.checkStoredLength(appointSales.toString(), APPOINT_SALES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "appointSales"), java.lang.String.valueOf(java.lang.Math.min(APPOINT_SALES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "appointSales", this.appointSales, appointSales));
   }

   /**
    * 지정품합계
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String APPOINT_TOTAL = "appointTotal";
   static int APPOINT_TOTAL_UPPER_LIMIT = -1;
   java.lang.String appointTotal = "0";
   /**
    * 지정품합계
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getAppointTotal() {
      return appointTotal;
   }
   /**
    * 지정품합계
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setAppointTotal(java.lang.String appointTotal) throws wt.util.WTPropertyVetoException {
      appointTotalValidate(appointTotal);
      this.appointTotal = appointTotal;
   }
   void appointTotalValidate(java.lang.String appointTotal) throws wt.util.WTPropertyVetoException {
      if (APPOINT_TOTAL_UPPER_LIMIT < 1) {
         try { APPOINT_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("appointTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPOINT_TOTAL_UPPER_LIMIT = 200; }
      }
      if (appointTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(appointTotal.toString(), APPOINT_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "appointTotal"), java.lang.String.valueOf(java.lang.Math.min(APPOINT_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "appointTotal", this.appointTotal, appointTotal));
   }

   /**
    * CR(%)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String CR = "cr";
   static int CR_UPPER_LIMIT = -1;
   java.lang.String cr = "0";
   /**
    * CR(%)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getCr() {
      return cr;
   }
   /**
    * CR(%)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setCr(java.lang.String cr) throws wt.util.WTPropertyVetoException {
      crValidate(cr);
      this.cr = cr;
   }
   void crValidate(java.lang.String cr) throws wt.util.WTPropertyVetoException {
      if (CR_UPPER_LIMIT < 1) {
         try { CR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CR_UPPER_LIMIT = 200; }
      }
      if (cr != null && !wt.fc.PersistenceHelper.checkStoredLength(cr.toString(), CR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cr"), java.lang.String.valueOf(java.lang.Math.min(CR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cr", this.cr, cr));
   }

   /**
    * 적용년수
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String APPLY_YEAR = "applyYear";
   static int APPLY_YEAR_UPPER_LIMIT = -1;
   java.lang.String applyYear = "0";
   /**
    * 적용년수
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getApplyYear() {
      return applyYear;
   }
   /**
    * 적용년수
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setApplyYear(java.lang.String applyYear) throws wt.util.WTPropertyVetoException {
      applyYearValidate(applyYear);
      this.applyYear = applyYear;
   }
   void applyYearValidate(java.lang.String applyYear) throws wt.util.WTPropertyVetoException {
      if (APPLY_YEAR_UPPER_LIMIT < 1) {
         try { APPLY_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("applyYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPLY_YEAR_UPPER_LIMIT = 200; }
      }
      if (applyYear != null && !wt.fc.PersistenceHelper.checkStoredLength(applyYear.toString(), APPLY_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "applyYear"), java.lang.String.valueOf(java.lang.Math.min(APPLY_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "applyYear", this.applyYear, applyYear));
   }

   /**
    * 회수기간
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String CORRECT_PERIOD = "correctPeriod";
   static int CORRECT_PERIOD_UPPER_LIMIT = -1;
   java.lang.String correctPeriod = "0";
   /**
    * 회수기간
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getCorrectPeriod() {
      return correctPeriod;
   }
   /**
    * 회수기간
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setCorrectPeriod(java.lang.String correctPeriod) throws wt.util.WTPropertyVetoException {
      correctPeriodValidate(correctPeriod);
      this.correctPeriod = correctPeriod;
   }
   void correctPeriodValidate(java.lang.String correctPeriod) throws wt.util.WTPropertyVetoException {
      if (CORRECT_PERIOD_UPPER_LIMIT < 1) {
         try { CORRECT_PERIOD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("correctPeriod").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CORRECT_PERIOD_UPPER_LIMIT = 200; }
      }
      if (correctPeriod != null && !wt.fc.PersistenceHelper.checkStoredLength(correctPeriod.toString(), CORRECT_PERIOD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "correctPeriod"), java.lang.String.valueOf(java.lang.Math.min(CORRECT_PERIOD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "correctPeriod", this.correctPeriod, correctPeriod));
   }

   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String SALES_TARGET_COST_TOTAL = "salesTargetCostTotal";
   static int SALES_TARGET_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String salesTargetCostTotal;
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getSalesTargetCostTotal() {
      return salesTargetCostTotal;
   }
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setSalesTargetCostTotal(java.lang.String salesTargetCostTotal) throws wt.util.WTPropertyVetoException {
      salesTargetCostTotalValidate(salesTargetCostTotal);
      this.salesTargetCostTotal = salesTargetCostTotal;
   }
   void salesTargetCostTotalValidate(java.lang.String salesTargetCostTotal) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_COST_TOTAL_UPPER_LIMIT < 1) {
         try { SALES_TARGET_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTargetCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (salesTargetCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTargetCostTotal.toString(), SALES_TARGET_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTargetCostTotal"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTargetCostTotal", this.salesTargetCostTotal, salesTargetCostTotal));
   }

   /**
    * 총원가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String PRODUCT_COST_TOTAL = "productCostTotal";
   static int PRODUCT_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String productCostTotal;
   /**
    * 총원가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getProductCostTotal() {
      return productCostTotal;
   }
   /**
    * 총원가
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setProductCostTotal(java.lang.String productCostTotal) throws wt.util.WTPropertyVetoException {
      productCostTotalValidate(productCostTotal);
      this.productCostTotal = productCostTotal;
   }
   void productCostTotalValidate(java.lang.String productCostTotal) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_COST_TOTAL_UPPER_LIMIT < 1) {
         try { PRODUCT_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (productCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(productCostTotal.toString(), PRODUCT_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productCostTotal"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productCostTotal", this.productCostTotal, productCostTotal));
   }

   /**
    * 판매량(천개)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String QUANTITY = "quantity";
   static int QUANTITY_UPPER_LIMIT = -1;
   java.lang.String quantity;
   /**
    * 판매량(천개)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getQuantity() {
      return quantity;
   }
   /**
    * 판매량(천개)
    *
    * @see ext.ket.cost.entity.CostAnalysis
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
    * 매출액(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String TOTAL_SALES = "totalSales";
   static int TOTAL_SALES_UPPER_LIMIT = -1;
   java.lang.String totalSales;
   /**
    * 매출액(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getTotalSales() {
      return totalSales;
   }
   /**
    * 매출액(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setTotalSales(java.lang.String totalSales) throws wt.util.WTPropertyVetoException {
      totalSalesValidate(totalSales);
      this.totalSales = totalSales;
   }
   void totalSalesValidate(java.lang.String totalSales) throws wt.util.WTPropertyVetoException {
      if (TOTAL_SALES_UPPER_LIMIT < 1) {
         try { TOTAL_SALES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("totalSales").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOTAL_SALES_UPPER_LIMIT = 200; }
      }
      if (totalSales != null && !wt.fc.PersistenceHelper.checkStoredLength(totalSales.toString(), TOTAL_SALES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "totalSales"), java.lang.String.valueOf(java.lang.Math.min(TOTAL_SALES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "totalSales", this.totalSales, totalSales));
   }

   /**
    * 영업이익(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String PROFIT_COST = "profitCost";
   static int PROFIT_COST_UPPER_LIMIT = -1;
   java.lang.String profitCost;
   /**
    * 영업이익(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getProfitCost() {
      return profitCost;
   }
   /**
    * 영업이익(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setProfitCost(java.lang.String profitCost) throws wt.util.WTPropertyVetoException {
      profitCostValidate(profitCost);
      this.profitCost = profitCost;
   }
   void profitCostValidate(java.lang.String profitCost) throws wt.util.WTPropertyVetoException {
      if (PROFIT_COST_UPPER_LIMIT < 1) {
         try { PROFIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("profitCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROFIT_COST_UPPER_LIMIT = 200; }
      }
      if (profitCost != null && !wt.fc.PersistenceHelper.checkStoredLength(profitCost.toString(), PROFIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "profitCost"), java.lang.String.valueOf(java.lang.Math.min(PROFIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "profitCost", this.profitCost, profitCost));
   }

   /**
    * 현금유입액(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String CASH_IN_FLOW = "cashInFlow";
   static int CASH_IN_FLOW_UPPER_LIMIT = -1;
   java.lang.String cashInFlow;
   /**
    * 현금유입액(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getCashInFlow() {
      return cashInFlow;
   }
   /**
    * 현금유입액(백만원)
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setCashInFlow(java.lang.String cashInFlow) throws wt.util.WTPropertyVetoException {
      cashInFlowValidate(cashInFlow);
      this.cashInFlow = cashInFlow;
   }
   void cashInFlowValidate(java.lang.String cashInFlow) throws wt.util.WTPropertyVetoException {
      if (CASH_IN_FLOW_UPPER_LIMIT < 1) {
         try { CASH_IN_FLOW_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cashInFlow").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CASH_IN_FLOW_UPPER_LIMIT = 200; }
      }
      if (cashInFlow != null && !wt.fc.PersistenceHelper.checkStoredLength(cashInFlow.toString(), CASH_IN_FLOW_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cashInFlow"), java.lang.String.valueOf(java.lang.Math.min(CASH_IN_FLOW_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cashInFlow", this.cashInFlow, cashInFlow));
   }

   /**
    * 영업이익율
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String PROFIT_RATE_TOTAL = "profitRateTotal";
   static int PROFIT_RATE_TOTAL_UPPER_LIMIT = -1;
   java.lang.String profitRateTotal;
   /**
    * 영업이익율
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public java.lang.String getProfitRateTotal() {
      return profitRateTotal;
   }
   /**
    * 영업이익율
    *
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setProfitRateTotal(java.lang.String profitRateTotal) throws wt.util.WTPropertyVetoException {
      profitRateTotalValidate(profitRateTotal);
      this.profitRateTotal = profitRateTotal;
   }
   void profitRateTotalValidate(java.lang.String profitRateTotal) throws wt.util.WTPropertyVetoException {
      if (PROFIT_RATE_TOTAL_UPPER_LIMIT < 1) {
         try { PROFIT_RATE_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("profitRateTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROFIT_RATE_TOTAL_UPPER_LIMIT = 200; }
      }
      if (profitRateTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(profitRateTotal.toString(), PROFIT_RATE_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "profitRateTotal"), java.lang.String.valueOf(java.lang.Math.min(PROFIT_RATE_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "profitRateTotal", this.profitRateTotal, profitRateTotal));
   }

   /**
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String COST_PART = "costPart";
   /**
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public static final java.lang.String COST_PART_REFERENCE = "costPartReference";
   wt.fc.ObjectReference costPartReference;
   /**
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public ext.ket.cost.entity.CostPart getCostPart() {
      return (costPartReference != null) ? (ext.ket.cost.entity.CostPart) costPartReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public wt.fc.ObjectReference getCostPartReference() {
      return costPartReference;
   }
   /**
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setCostPart(ext.ket.cost.entity.CostPart the_costPart) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostPartReference(the_costPart == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostPart) the_costPart));
   }
   /**
    * @see ext.ket.cost.entity.CostAnalysis
    */
   public void setCostPartReference(wt.fc.ObjectReference the_costPartReference) throws wt.util.WTPropertyVetoException {
      costPartReferenceValidate(the_costPartReference);
      costPartReference = (wt.fc.ObjectReference) the_costPartReference;
   }
   void costPartReferenceValidate(wt.fc.ObjectReference the_costPartReference) throws wt.util.WTPropertyVetoException {
      if (the_costPartReference == null || the_costPartReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costPartReference") },
               new java.beans.PropertyChangeEvent(this, "costPartReference", this.costPartReference, costPartReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1765547783399705518L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( applyYear );
      output.writeObject( appointSales );
      output.writeObject( appointTotal );
      output.writeObject( cashInFlow );
      output.writeObject( correctPeriod );
      output.writeObject( costPartReference );
      output.writeObject( cr );
      output.writeObject( owner );
      output.writeObject( productCostTotal );
      output.writeObject( profitCost );
      output.writeObject( profitRateTotal );
      output.writeObject( quantity );
      output.writeObject( salesTargetCostTotal );
      output.writeObject( thePersistInfo );
      output.writeObject( totalSales );
      output.writeObject( year );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostAnalysis) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "applyYear", applyYear );
      output.setString( "appointSales", appointSales );
      output.setString( "appointTotal", appointTotal );
      output.setString( "cashInFlow", cashInFlow );
      output.setString( "correctPeriod", correctPeriod );
      output.writeObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      output.setString( "cr", cr );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "productCostTotal", productCostTotal );
      output.setString( "profitCost", profitCost );
      output.setString( "profitRateTotal", profitRateTotal );
      output.setString( "quantity", quantity );
      output.setString( "salesTargetCostTotal", salesTargetCostTotal );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "totalSales", totalSales );
      output.setIntObject( "year", year );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      applyYear = input.getString( "applyYear" );
      appointSales = input.getString( "appointSales" );
      appointTotal = input.getString( "appointTotal" );
      cashInFlow = input.getString( "cashInFlow" );
      correctPeriod = input.getString( "correctPeriod" );
      costPartReference = (wt.fc.ObjectReference) input.readObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      cr = input.getString( "cr" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      productCostTotal = input.getString( "productCostTotal" );
      profitCost = input.getString( "profitCost" );
      profitRateTotal = input.getString( "profitRateTotal" );
      quantity = input.getString( "quantity" );
      salesTargetCostTotal = input.getString( "salesTargetCostTotal" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      totalSales = input.getString( "totalSales" );
      year = input.getIntObject( "year" );
   }

   boolean readVersion1765547783399705518L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      applyYear = (java.lang.String) input.readObject();
      appointSales = (java.lang.String) input.readObject();
      appointTotal = (java.lang.String) input.readObject();
      cashInFlow = (java.lang.String) input.readObject();
      correctPeriod = (java.lang.String) input.readObject();
      costPartReference = (wt.fc.ObjectReference) input.readObject();
      cr = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      productCostTotal = (java.lang.String) input.readObject();
      profitCost = (java.lang.String) input.readObject();
      profitRateTotal = (java.lang.String) input.readObject();
      quantity = (java.lang.String) input.readObject();
      salesTargetCostTotal = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      totalSales = (java.lang.String) input.readObject();
      year = (java.lang.Integer) input.readObject();
      return true;
   }

   protected boolean readVersion( CostAnalysis thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1765547783399705518L( input, readSerialVersionUID, superDone );
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
