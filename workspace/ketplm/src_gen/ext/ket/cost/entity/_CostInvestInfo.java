package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostInvestInfo implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostInvestInfo.class.getName();

   /**
    * 투자비구분(금형 : 설비 : 기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String INVEST_TYPE = "investType";
   static int INVEST_TYPE_UPPER_LIMIT = -1;
   java.lang.String investType;
   /**
    * 투자비구분(금형 : 설비 : 기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getInvestType() {
      return investType;
   }
   /**
    * 투자비구분(금형 : 설비 : 기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setInvestType(java.lang.String investType) throws wt.util.WTPropertyVetoException {
      investTypeValidate(investType);
      this.investType = investType;
   }
   void investTypeValidate(java.lang.String investType) throws wt.util.WTPropertyVetoException {
      if (INVEST_TYPE_UPPER_LIMIT < 1) {
         try { INVEST_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_TYPE_UPPER_LIMIT = 200; }
      }
      if (investType != null && !wt.fc.PersistenceHelper.checkStoredLength(investType.toString(), INVEST_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investType"), java.lang.String.valueOf(java.lang.Math.min(INVEST_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investType", this.investType, investType));
   }

   /**
    * 투자비 신규/양산 구분
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String COST_TYPE = "costType";
   static int COST_TYPE_UPPER_LIMIT = -1;
   java.lang.String costType;
   /**
    * 투자비 신규/양산 구분
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getCostType() {
      return costType;
   }
   /**
    * 투자비 신규/양산 구분
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setCostType(java.lang.String costType) throws wt.util.WTPropertyVetoException {
      costTypeValidate(costType);
      this.costType = costType;
   }
   void costTypeValidate(java.lang.String costType) throws wt.util.WTPropertyVetoException {
      if (COST_TYPE_UPPER_LIMIT < 1) {
         try { COST_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_TYPE_UPPER_LIMIT = 200; }
      }
      if (costType != null && !wt.fc.PersistenceHelper.checkStoredLength(costType.toString(), COST_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costType"), java.lang.String.valueOf(java.lang.Math.min(COST_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costType", this.costType, costType));
   }

   /**
    * 감가비 코드
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String REDUCE_CODE = "reduceCode";
   static int REDUCE_CODE_UPPER_LIMIT = -1;
   java.lang.String reduceCode;
   /**
    * 감가비 코드
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getReduceCode() {
      return reduceCode;
   }
   /**
    * 감가비 코드
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setReduceCode(java.lang.String reduceCode) throws wt.util.WTPropertyVetoException {
      reduceCodeValidate(reduceCode);
      this.reduceCode = reduceCode;
   }
   void reduceCodeValidate(java.lang.String reduceCode) throws wt.util.WTPropertyVetoException {
      if (REDUCE_CODE_UPPER_LIMIT < 1) {
         try { REDUCE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reduceCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REDUCE_CODE_UPPER_LIMIT = 200; }
      }
      if (reduceCode != null && !wt.fc.PersistenceHelper.checkStoredLength(reduceCode.toString(), REDUCE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reduceCode"), java.lang.String.valueOf(java.lang.Math.min(REDUCE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reduceCode", this.reduceCode, reduceCode));
   }

   /**
    * 투자비(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String INVEST_COST = "investCost";
   static int INVEST_COST_UPPER_LIMIT = -1;
   java.lang.String investCost;
   /**
    * 투자비(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getInvestCost() {
      return investCost;
   }
   /**
    * 투자비(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setInvestCost(java.lang.String investCost) throws wt.util.WTPropertyVetoException {
      investCostValidate(investCost);
      this.investCost = investCost;
   }
   void investCostValidate(java.lang.String investCost) throws wt.util.WTPropertyVetoException {
      if (INVEST_COST_UPPER_LIMIT < 1) {
         try { INVEST_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_COST_UPPER_LIMIT = 200; }
      }
      if (investCost != null && !wt.fc.PersistenceHelper.checkStoredLength(investCost.toString(), INVEST_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investCost"), java.lang.String.valueOf(java.lang.Math.min(INVEST_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investCost", this.investCost, investCost));
   }

   /**
    * 지급액(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String INVEST_PAY = "investPay";
   static int INVEST_PAY_UPPER_LIMIT = -1;
   java.lang.String investPay;
   /**
    * 지급액(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getInvestPay() {
      return investPay;
   }
   /**
    * 지급액(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setInvestPay(java.lang.String investPay) throws wt.util.WTPropertyVetoException {
      investPayValidate(investPay);
      this.investPay = investPay;
   }
   void investPayValidate(java.lang.String investPay) throws wt.util.WTPropertyVetoException {
      if (INVEST_PAY_UPPER_LIMIT < 1) {
         try { INVEST_PAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investPay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_PAY_UPPER_LIMIT = 200; }
      }
      if (investPay != null && !wt.fc.PersistenceHelper.checkStoredLength(investPay.toString(), INVEST_PAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investPay"), java.lang.String.valueOf(java.lang.Math.min(INVEST_PAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investPay", this.investPay, investPay));
   }

   /**
    * 감가액(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String INVEST_REDUCE_COST = "investReduceCost";
   static int INVEST_REDUCE_COST_UPPER_LIMIT = -1;
   java.lang.String investReduceCost;
   /**
    * 감가액(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getInvestReduceCost() {
      return investReduceCost;
   }
   /**
    * 감가액(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setInvestReduceCost(java.lang.String investReduceCost) throws wt.util.WTPropertyVetoException {
      investReduceCostValidate(investReduceCost);
      this.investReduceCost = investReduceCost;
   }
   void investReduceCostValidate(java.lang.String investReduceCost) throws wt.util.WTPropertyVetoException {
      if (INVEST_REDUCE_COST_UPPER_LIMIT < 1) {
         try { INVEST_REDUCE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investReduceCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_REDUCE_COST_UPPER_LIMIT = 200; }
      }
      if (investReduceCost != null && !wt.fc.PersistenceHelper.checkStoredLength(investReduceCost.toString(), INVEST_REDUCE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investReduceCost"), java.lang.String.valueOf(java.lang.Math.min(INVEST_REDUCE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investReduceCost", this.investReduceCost, investReduceCost));
   }

   /**
    * 생산Capa (금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String CAPA_RATE = "capaRate";
   static int CAPA_RATE_UPPER_LIMIT = -1;
   java.lang.String capaRate;
   /**
    * 생산Capa (금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getCapaRate() {
      return capaRate;
   }
   /**
    * 생산Capa (금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setCapaRate(java.lang.String capaRate) throws wt.util.WTPropertyVetoException {
      capaRateValidate(capaRate);
      this.capaRate = capaRate;
   }
   void capaRateValidate(java.lang.String capaRate) throws wt.util.WTPropertyVetoException {
      if (CAPA_RATE_UPPER_LIMIT < 1) {
         try { CAPA_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("capaRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAPA_RATE_UPPER_LIMIT = 200; }
      }
      if (capaRate != null && !wt.fc.PersistenceHelper.checkStoredLength(capaRate.toString(), CAPA_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "capaRate"), java.lang.String.valueOf(java.lang.Math.min(CAPA_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "capaRate", this.capaRate, capaRate));
   }

   /**
    * 벌수
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String INVEST_UNIT = "investUnit";
   static int INVEST_UNIT_UPPER_LIMIT = -1;
   java.lang.String investUnit;
   /**
    * 벌수
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getInvestUnit() {
      return investUnit;
   }
   /**
    * 벌수
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setInvestUnit(java.lang.String investUnit) throws wt.util.WTPropertyVetoException {
      investUnitValidate(investUnit);
      this.investUnit = investUnit;
   }
   void investUnitValidate(java.lang.String investUnit) throws wt.util.WTPropertyVetoException {
      if (INVEST_UNIT_UPPER_LIMIT < 1) {
         try { INVEST_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_UNIT_UPPER_LIMIT = 200; }
      }
      if (investUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(investUnit.toString(), INVEST_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investUnit"), java.lang.String.valueOf(java.lang.Math.min(INVEST_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investUnit", this.investUnit, investUnit));
   }

   /**
    * 판매수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String SALES_QTY = "salesQty";
   static int SALES_QTY_UPPER_LIMIT = -1;
   java.lang.String salesQty;
   /**
    * 판매수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getSalesQty() {
      return salesQty;
   }
   /**
    * 판매수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setSalesQty(java.lang.String salesQty) throws wt.util.WTPropertyVetoException {
      salesQtyValidate(salesQty);
      this.salesQty = salesQty;
   }
   void salesQtyValidate(java.lang.String salesQty) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY_UPPER_LIMIT < 1) {
         try { SALES_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY_UPPER_LIMIT = 200; }
      }
      if (salesQty != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty.toString(), SALES_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty", this.salesQty, salesQty));
   }

   /**
    * 양산수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String MASS_QTY = "massQty";
   static int MASS_QTY_UPPER_LIMIT = -1;
   java.lang.String massQty;
   /**
    * 양산수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getMassQty() {
      return massQty;
   }
   /**
    * 양산수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setMassQty(java.lang.String massQty) throws wt.util.WTPropertyVetoException {
      massQtyValidate(massQty);
      this.massQty = massQty;
   }
   void massQtyValidate(java.lang.String massQty) throws wt.util.WTPropertyVetoException {
      if (MASS_QTY_UPPER_LIMIT < 1) {
         try { MASS_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("massQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MASS_QTY_UPPER_LIMIT = 200; }
      }
      if (massQty != null && !wt.fc.PersistenceHelper.checkStoredLength(massQty.toString(), MASS_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "massQty"), java.lang.String.valueOf(java.lang.Math.min(MASS_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "massQty", this.massQty, massQty));
   }

   /**
    * 추가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String ADD_QTY = "addQty";
   static int ADD_QTY_UPPER_LIMIT = -1;
   java.lang.String addQty;
   /**
    * 추가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getAddQty() {
      return addQty;
   }
   /**
    * 추가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setAddQty(java.lang.String addQty) throws wt.util.WTPropertyVetoException {
      addQtyValidate(addQty);
      this.addQty = addQty;
   }
   void addQtyValidate(java.lang.String addQty) throws wt.util.WTPropertyVetoException {
      if (ADD_QTY_UPPER_LIMIT < 1) {
         try { ADD_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_QTY_UPPER_LIMIT = 200; }
      }
      if (addQty != null && !wt.fc.PersistenceHelper.checkStoredLength(addQty.toString(), ADD_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addQty"), java.lang.String.valueOf(java.lang.Math.min(ADD_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addQty", this.addQty, addQty));
   }

   /**
    * 판매감가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String SALES_REDUCE_QTY = "salesReduceQty";
   static int SALES_REDUCE_QTY_UPPER_LIMIT = -1;
   java.lang.String salesReduceQty;
   /**
    * 판매감가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getSalesReduceQty() {
      return salesReduceQty;
   }
   /**
    * 판매감가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setSalesReduceQty(java.lang.String salesReduceQty) throws wt.util.WTPropertyVetoException {
      salesReduceQtyValidate(salesReduceQty);
      this.salesReduceQty = salesReduceQty;
   }
   void salesReduceQtyValidate(java.lang.String salesReduceQty) throws wt.util.WTPropertyVetoException {
      if (SALES_REDUCE_QTY_UPPER_LIMIT < 1) {
         try { SALES_REDUCE_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesReduceQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_REDUCE_QTY_UPPER_LIMIT = 200; }
      }
      if (salesReduceQty != null && !wt.fc.PersistenceHelper.checkStoredLength(salesReduceQty.toString(), SALES_REDUCE_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesReduceQty"), java.lang.String.valueOf(java.lang.Math.min(SALES_REDUCE_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesReduceQty", this.salesReduceQty, salesReduceQty));
   }

   /**
    * 일반감가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String NORMAL_REDUCE_QTY = "normalReduceQty";
   static int NORMAL_REDUCE_QTY_UPPER_LIMIT = -1;
   java.lang.String normalReduceQty;
   /**
    * 일반감가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getNormalReduceQty() {
      return normalReduceQty;
   }
   /**
    * 일반감가수량(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setNormalReduceQty(java.lang.String normalReduceQty) throws wt.util.WTPropertyVetoException {
      normalReduceQtyValidate(normalReduceQty);
      this.normalReduceQty = normalReduceQty;
   }
   void normalReduceQtyValidate(java.lang.String normalReduceQty) throws wt.util.WTPropertyVetoException {
      if (NORMAL_REDUCE_QTY_UPPER_LIMIT < 1) {
         try { NORMAL_REDUCE_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("normalReduceQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NORMAL_REDUCE_QTY_UPPER_LIMIT = 200; }
      }
      if (normalReduceQty != null && !wt.fc.PersistenceHelper.checkStoredLength(normalReduceQty.toString(), NORMAL_REDUCE_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "normalReduceQty"), java.lang.String.valueOf(java.lang.Math.min(NORMAL_REDUCE_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "normalReduceQty", this.normalReduceQty, normalReduceQty));
   }

   /**
    * 개별감가비(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String EACH_REDUCE_COST = "eachReduceCost";
   static int EACH_REDUCE_COST_UPPER_LIMIT = -1;
   java.lang.String eachReduceCost;
   /**
    * 개별감가비(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getEachReduceCost() {
      return eachReduceCost;
   }
   /**
    * 개별감가비(금형,설비,기타)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setEachReduceCost(java.lang.String eachReduceCost) throws wt.util.WTPropertyVetoException {
      eachReduceCostValidate(eachReduceCost);
      this.eachReduceCost = eachReduceCost;
   }
   void eachReduceCostValidate(java.lang.String eachReduceCost) throws wt.util.WTPropertyVetoException {
      if (EACH_REDUCE_COST_UPPER_LIMIT < 1) {
         try { EACH_REDUCE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("eachReduceCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EACH_REDUCE_COST_UPPER_LIMIT = 200; }
      }
      if (eachReduceCost != null && !wt.fc.PersistenceHelper.checkStoredLength(eachReduceCost.toString(), EACH_REDUCE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "eachReduceCost"), java.lang.String.valueOf(java.lang.Math.min(EACH_REDUCE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "eachReduceCost", this.eachReduceCost, eachReduceCost));
   }

   /**
    * 감가비 비고
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String INVEST_NOTE = "investNote";
   static int INVEST_NOTE_UPPER_LIMIT = -1;
   java.lang.String investNote;
   /**
    * 감가비 비고
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getInvestNote() {
      return investNote;
   }
   /**
    * 감가비 비고
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setInvestNote(java.lang.String investNote) throws wt.util.WTPropertyVetoException {
      investNoteValidate(investNote);
      this.investNote = investNote;
   }
   void investNoteValidate(java.lang.String investNote) throws wt.util.WTPropertyVetoException {
      if (INVEST_NOTE_UPPER_LIMIT < 1) {
         try { INVEST_NOTE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investNote").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_NOTE_UPPER_LIMIT = 200; }
      }
      if (investNote != null && !wt.fc.PersistenceHelper.checkStoredLength(investNote.toString(), INVEST_NOTE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investNote"), java.lang.String.valueOf(java.lang.Math.min(INVEST_NOTE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investNote", this.investNote, investNote));
   }

   /**
    * 항목(기타 투자비에서만 사용)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String ITEM_NAME = "itemName";
   static int ITEM_NAME_UPPER_LIMIT = -1;
   java.lang.String itemName;
   /**
    * 항목(기타 투자비에서만 사용)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getItemName() {
      return itemName;
   }
   /**
    * 항목(기타 투자비에서만 사용)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setItemName(java.lang.String itemName) throws wt.util.WTPropertyVetoException {
      itemNameValidate(itemName);
      this.itemName = itemName;
   }
   void itemNameValidate(java.lang.String itemName) throws wt.util.WTPropertyVetoException {
      if (ITEM_NAME_UPPER_LIMIT < 1) {
         try { ITEM_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("itemName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ITEM_NAME_UPPER_LIMIT = 200; }
      }
      if (itemName != null && !wt.fc.PersistenceHelper.checkStoredLength(itemName.toString(), ITEM_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "itemName"), java.lang.String.valueOf(java.lang.Math.min(ITEM_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "itemName", this.itemName, itemName));
   }

   /**
    * 신규제작처
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String N_FACTORY = "nFactory";
   static int N_FACTORY_UPPER_LIMIT = -1;
   java.lang.String nFactory;
   /**
    * 신규제작처
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getNFactory() {
      return nFactory;
   }
   /**
    * 신규제작처
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setNFactory(java.lang.String nFactory) throws wt.util.WTPropertyVetoException {
      nFactoryValidate(nFactory);
      this.nFactory = nFactory;
   }
   void nFactoryValidate(java.lang.String nFactory) throws wt.util.WTPropertyVetoException {
      if (N_FACTORY_UPPER_LIMIT < 1) {
         try { N_FACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("nFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { N_FACTORY_UPPER_LIMIT = 200; }
      }
      if (nFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(nFactory.toString(), N_FACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "nFactory"), java.lang.String.valueOf(java.lang.Math.min(N_FACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "nFactory", this.nFactory, nFactory));
   }

   /**
    * 양산제작처
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String M_FACTORY = "mFactory";
   static int M_FACTORY_UPPER_LIMIT = -1;
   java.lang.String mFactory;
   /**
    * 양산제작처
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getMFactory() {
      return mFactory;
   }
   /**
    * 양산제작처
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setMFactory(java.lang.String mFactory) throws wt.util.WTPropertyVetoException {
      mFactoryValidate(mFactory);
      this.mFactory = mFactory;
   }
   void mFactoryValidate(java.lang.String mFactory) throws wt.util.WTPropertyVetoException {
      if (M_FACTORY_UPPER_LIMIT < 1) {
         try { M_FACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { M_FACTORY_UPPER_LIMIT = 200; }
      }
      if (mFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(mFactory.toString(), M_FACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mFactory"), java.lang.String.valueOf(java.lang.Math.min(M_FACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mFactory", this.mFactory, mFactory));
   }

   /**
    * 기타투자비OID
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String ETC_INVEST_OID = "etcInvestOid";
   static int ETC_INVEST_OID_UPPER_LIMIT = -1;
   java.lang.String etcInvestOid;
   /**
    * 기타투자비OID
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getEtcInvestOid() {
      return etcInvestOid;
   }
   /**
    * 기타투자비OID
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setEtcInvestOid(java.lang.String etcInvestOid) throws wt.util.WTPropertyVetoException {
      etcInvestOidValidate(etcInvestOid);
      this.etcInvestOid = etcInvestOid;
   }
   void etcInvestOidValidate(java.lang.String etcInvestOid) throws wt.util.WTPropertyVetoException {
      if (ETC_INVEST_OID_UPPER_LIMIT < 1) {
         try { ETC_INVEST_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcInvestOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_INVEST_OID_UPPER_LIMIT = 200; }
      }
      if (etcInvestOid != null && !wt.fc.PersistenceHelper.checkStoredLength(etcInvestOid.toString(), ETC_INVEST_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcInvestOid"), java.lang.String.valueOf(java.lang.Math.min(ETC_INVEST_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcInvestOid", this.etcInvestOid, etcInvestOid));
   }

   /**
    * 조업도_시간
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String WORK_USE_HOUR = "workUseHour";
   static int WORK_USE_HOUR_UPPER_LIMIT = -1;
   java.lang.String workUseHour = "20";
   /**
    * 조업도_시간
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getWorkUseHour() {
      return workUseHour;
   }
   /**
    * 조업도_시간
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setWorkUseHour(java.lang.String workUseHour) throws wt.util.WTPropertyVetoException {
      workUseHourValidate(workUseHour);
      this.workUseHour = workUseHour;
   }
   void workUseHourValidate(java.lang.String workUseHour) throws wt.util.WTPropertyVetoException {
      if (WORK_USE_HOUR_UPPER_LIMIT < 1) {
         try { WORK_USE_HOUR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workUseHour").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_USE_HOUR_UPPER_LIMIT = 200; }
      }
      if (workUseHour != null && !wt.fc.PersistenceHelper.checkStoredLength(workUseHour.toString(), WORK_USE_HOUR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workUseHour"), java.lang.String.valueOf(java.lang.Math.min(WORK_USE_HOUR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workUseHour", this.workUseHour, workUseHour));
   }

   /**
    * 조업도_일
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String WORK_USE_DAY = "workUseDay";
   static int WORK_USE_DAY_UPPER_LIMIT = -1;
   java.lang.String workUseDay = "260";
   /**
    * 조업도_일
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getWorkUseDay() {
      return workUseDay;
   }
   /**
    * 조업도_일
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setWorkUseDay(java.lang.String workUseDay) throws wt.util.WTPropertyVetoException {
      workUseDayValidate(workUseDay);
      this.workUseDay = workUseDay;
   }
   void workUseDayValidate(java.lang.String workUseDay) throws wt.util.WTPropertyVetoException {
      if (WORK_USE_DAY_UPPER_LIMIT < 1) {
         try { WORK_USE_DAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workUseDay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_USE_DAY_UPPER_LIMIT = 200; }
      }
      if (workUseDay != null && !wt.fc.PersistenceHelper.checkStoredLength(workUseDay.toString(), WORK_USE_DAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workUseDay"), java.lang.String.valueOf(java.lang.Math.min(WORK_USE_DAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workUseDay", this.workUseDay, workUseDay));
   }

   /**
    * 조업도_적용년수
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String WORK_USE_YEAR = "workUseYear";
   static int WORK_USE_YEAR_UPPER_LIMIT = -1;
   java.lang.String workUseYear = "6";
   /**
    * 조업도_적용년수
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getWorkUseYear() {
      return workUseYear;
   }
   /**
    * 조업도_적용년수
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setWorkUseYear(java.lang.String workUseYear) throws wt.util.WTPropertyVetoException {
      workUseYearValidate(workUseYear);
      this.workUseYear = workUseYear;
   }
   void workUseYearValidate(java.lang.String workUseYear) throws wt.util.WTPropertyVetoException {
      if (WORK_USE_YEAR_UPPER_LIMIT < 1) {
         try { WORK_USE_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workUseYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_USE_YEAR_UPPER_LIMIT = 200; }
      }
      if (workUseYear != null && !wt.fc.PersistenceHelper.checkStoredLength(workUseYear.toString(), WORK_USE_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workUseYear"), java.lang.String.valueOf(java.lang.Math.min(WORK_USE_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workUseYear", this.workUseYear, workUseYear));
   }

   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String MACHINE_DPC_COST_EXPR = "machineDpcCostExpr";
   static int MACHINE_DPC_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String machineDpcCostExpr = "0";
   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getMachineDpcCostExpr() {
      return machineDpcCostExpr;
   }
   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setMachineDpcCostExpr(java.lang.String machineDpcCostExpr) throws wt.util.WTPropertyVetoException {
      machineDpcCostExprValidate(machineDpcCostExpr);
      this.machineDpcCostExpr = machineDpcCostExpr;
   }
   void machineDpcCostExprValidate(java.lang.String machineDpcCostExpr) throws wt.util.WTPropertyVetoException {
      if (MACHINE_DPC_COST_EXPR_UPPER_LIMIT < 1) {
         try { MACHINE_DPC_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineDpcCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_DPC_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (machineDpcCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(machineDpcCostExpr.toString(), MACHINE_DPC_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineDpcCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_DPC_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineDpcCostExpr", this.machineDpcCostExpr, machineDpcCostExpr));
   }

   /**
    * 범용감가CT(SPM)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String FAC_REDUCE_CT_SPM = "facReduceCtSpm";
   static int FAC_REDUCE_CT_SPM_UPPER_LIMIT = -1;
   java.lang.String facReduceCtSpm = "0";
   /**
    * 범용감가CT(SPM)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getFacReduceCtSpm() {
      return facReduceCtSpm;
   }
   /**
    * 범용감가CT(SPM)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setFacReduceCtSpm(java.lang.String facReduceCtSpm) throws wt.util.WTPropertyVetoException {
      facReduceCtSpmValidate(facReduceCtSpm);
      this.facReduceCtSpm = facReduceCtSpm;
   }
   void facReduceCtSpmValidate(java.lang.String facReduceCtSpm) throws wt.util.WTPropertyVetoException {
      if (FAC_REDUCE_CT_SPM_UPPER_LIMIT < 1) {
         try { FAC_REDUCE_CT_SPM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facReduceCtSpm").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_REDUCE_CT_SPM_UPPER_LIMIT = 200; }
      }
      if (facReduceCtSpm != null && !wt.fc.PersistenceHelper.checkStoredLength(facReduceCtSpm.toString(), FAC_REDUCE_CT_SPM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facReduceCtSpm"), java.lang.String.valueOf(java.lang.Math.min(FAC_REDUCE_CT_SPM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facReduceCtSpm", this.facReduceCtSpm, facReduceCtSpm));
   }

   /**
    * 범용감가생산량
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String FAC_REDUCE_OUTPUT_EXPR = "facReduceOutputExpr";
   static int FAC_REDUCE_OUTPUT_EXPR_UPPER_LIMIT = -1;
   java.lang.String facReduceOutputExpr = "0";
   /**
    * 범용감가생산량
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getFacReduceOutputExpr() {
      return facReduceOutputExpr;
   }
   /**
    * 범용감가생산량
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setFacReduceOutputExpr(java.lang.String facReduceOutputExpr) throws wt.util.WTPropertyVetoException {
      facReduceOutputExprValidate(facReduceOutputExpr);
      this.facReduceOutputExpr = facReduceOutputExpr;
   }
   void facReduceOutputExprValidate(java.lang.String facReduceOutputExpr) throws wt.util.WTPropertyVetoException {
      if (FAC_REDUCE_OUTPUT_EXPR_UPPER_LIMIT < 1) {
         try { FAC_REDUCE_OUTPUT_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facReduceOutputExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_REDUCE_OUTPUT_EXPR_UPPER_LIMIT = 200; }
      }
      if (facReduceOutputExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(facReduceOutputExpr.toString(), FAC_REDUCE_OUTPUT_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facReduceOutputExpr"), java.lang.String.valueOf(java.lang.Math.min(FAC_REDUCE_OUTPUT_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facReduceOutputExpr", this.facReduceOutputExpr, facReduceOutputExpr));
   }

   /**
    * 기계감가(표준)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public static final java.lang.String MACHINE_REDUCE_COST = "machineReduceCost";
   static int MACHINE_REDUCE_COST_UPPER_LIMIT = -1;
   java.lang.String machineReduceCost = "0";
   /**
    * 기계감가(표준)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public java.lang.String getMachineReduceCost() {
      return machineReduceCost;
   }
   /**
    * 기계감가(표준)
    *
    * @see ext.ket.cost.entity.CostInvestInfo
    */
   public void setMachineReduceCost(java.lang.String machineReduceCost) throws wt.util.WTPropertyVetoException {
      machineReduceCostValidate(machineReduceCost);
      this.machineReduceCost = machineReduceCost;
   }
   void machineReduceCostValidate(java.lang.String machineReduceCost) throws wt.util.WTPropertyVetoException {
      if (MACHINE_REDUCE_COST_UPPER_LIMIT < 1) {
         try { MACHINE_REDUCE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineReduceCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_REDUCE_COST_UPPER_LIMIT = 200; }
      }
      if (machineReduceCost != null && !wt.fc.PersistenceHelper.checkStoredLength(machineReduceCost.toString(), MACHINE_REDUCE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineReduceCost"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_REDUCE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineReduceCost", this.machineReduceCost, machineReduceCost));
   }

   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent() {
      return (parentReference != null) ? (e3ps.common.impl.Tree) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.impl.Tree) the_parent));
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParentReference(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      parentReferenceValidate(the_parentReference);
      parentReference = (wt.fc.ObjectReference) the_parentReference;
   }
   void parentReferenceValidate(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      if (the_parentReference == null || the_parentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference") },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
      if (the_parentReference != null && the_parentReference.getReferencedClass() != null &&
            !e3ps.common.impl.Tree.class.isAssignableFrom(the_parentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -2082042311177574200L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( addQty );
      output.writeObject( capaRate );
      output.writeObject( costType );
      output.writeObject( eachReduceCost );
      output.writeObject( etcInvestOid );
      output.writeObject( facReduceCtSpm );
      output.writeObject( facReduceOutputExpr );
      output.writeObject( investCost );
      output.writeObject( investNote );
      output.writeObject( investPay );
      output.writeObject( investReduceCost );
      output.writeObject( investType );
      output.writeObject( investUnit );
      output.writeObject( itemName );
      output.writeObject( mFactory );
      output.writeObject( machineDpcCostExpr );
      output.writeObject( machineReduceCost );
      output.writeObject( massQty );
      output.writeObject( nFactory );
      output.writeObject( normalReduceQty );
      output.writeObject( parentReference );
      output.writeObject( reduceCode );
      output.writeObject( salesQty );
      output.writeObject( salesReduceQty );
      output.writeObject( thePersistInfo );
      output.writeObject( workUseDay );
      output.writeObject( workUseHour );
      output.writeObject( workUseYear );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostInvestInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "addQty", addQty );
      output.setString( "capaRate", capaRate );
      output.setString( "costType", costType );
      output.setString( "eachReduceCost", eachReduceCost );
      output.setString( "etcInvestOid", etcInvestOid );
      output.setString( "facReduceCtSpm", facReduceCtSpm );
      output.setString( "facReduceOutputExpr", facReduceOutputExpr );
      output.setString( "investCost", investCost );
      output.setString( "investNote", investNote );
      output.setString( "investPay", investPay );
      output.setString( "investReduceCost", investReduceCost );
      output.setString( "investType", investType );
      output.setString( "investUnit", investUnit );
      output.setString( "itemName", itemName );
      output.setString( "mFactory", mFactory );
      output.setString( "machineDpcCostExpr", machineDpcCostExpr );
      output.setString( "machineReduceCost", machineReduceCost );
      output.setString( "massQty", massQty );
      output.setString( "nFactory", nFactory );
      output.setString( "normalReduceQty", normalReduceQty );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setString( "reduceCode", reduceCode );
      output.setString( "salesQty", salesQty );
      output.setString( "salesReduceQty", salesReduceQty );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "workUseDay", workUseDay );
      output.setString( "workUseHour", workUseHour );
      output.setString( "workUseYear", workUseYear );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      addQty = input.getString( "addQty" );
      capaRate = input.getString( "capaRate" );
      costType = input.getString( "costType" );
      eachReduceCost = input.getString( "eachReduceCost" );
      etcInvestOid = input.getString( "etcInvestOid" );
      facReduceCtSpm = input.getString( "facReduceCtSpm" );
      facReduceOutputExpr = input.getString( "facReduceOutputExpr" );
      investCost = input.getString( "investCost" );
      investNote = input.getString( "investNote" );
      investPay = input.getString( "investPay" );
      investReduceCost = input.getString( "investReduceCost" );
      investType = input.getString( "investType" );
      investUnit = input.getString( "investUnit" );
      itemName = input.getString( "itemName" );
      mFactory = input.getString( "mFactory" );
      machineDpcCostExpr = input.getString( "machineDpcCostExpr" );
      machineReduceCost = input.getString( "machineReduceCost" );
      massQty = input.getString( "massQty" );
      nFactory = input.getString( "nFactory" );
      normalReduceQty = input.getString( "normalReduceQty" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      reduceCode = input.getString( "reduceCode" );
      salesQty = input.getString( "salesQty" );
      salesReduceQty = input.getString( "salesReduceQty" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      workUseDay = input.getString( "workUseDay" );
      workUseHour = input.getString( "workUseHour" );
      workUseYear = input.getString( "workUseYear" );
   }

   boolean readVersion_2082042311177574200L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      addQty = (java.lang.String) input.readObject();
      capaRate = (java.lang.String) input.readObject();
      costType = (java.lang.String) input.readObject();
      eachReduceCost = (java.lang.String) input.readObject();
      etcInvestOid = (java.lang.String) input.readObject();
      facReduceCtSpm = (java.lang.String) input.readObject();
      facReduceOutputExpr = (java.lang.String) input.readObject();
      investCost = (java.lang.String) input.readObject();
      investNote = (java.lang.String) input.readObject();
      investPay = (java.lang.String) input.readObject();
      investReduceCost = (java.lang.String) input.readObject();
      investType = (java.lang.String) input.readObject();
      investUnit = (java.lang.String) input.readObject();
      itemName = (java.lang.String) input.readObject();
      mFactory = (java.lang.String) input.readObject();
      machineDpcCostExpr = (java.lang.String) input.readObject();
      machineReduceCost = (java.lang.String) input.readObject();
      massQty = (java.lang.String) input.readObject();
      nFactory = (java.lang.String) input.readObject();
      normalReduceQty = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      reduceCode = (java.lang.String) input.readObject();
      salesQty = (java.lang.String) input.readObject();
      salesReduceQty = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      workUseDay = (java.lang.String) input.readObject();
      workUseHour = (java.lang.String) input.readObject();
      workUseYear = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostInvestInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2082042311177574200L( input, readSerialVersionUID, superDone );
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
