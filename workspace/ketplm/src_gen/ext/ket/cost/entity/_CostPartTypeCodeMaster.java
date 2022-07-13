package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostPartTypeCodeMaster implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostPartTypeCodeMaster.class.getName();

   /**
    * 생산Loss율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String PRODUCT_LOSS_RATE = "productLossRate";
   static int PRODUCT_LOSS_RATE_UPPER_LIMIT = -1;
   java.lang.String productLossRate;
   /**
    * 생산Loss율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getProductLossRate() {
      return productLossRate;
   }
   /**
    * 생산Loss율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setProductLossRate(java.lang.String productLossRate) throws wt.util.WTPropertyVetoException {
      productLossRateValidate(productLossRate);
      this.productLossRate = productLossRate;
   }
   void productLossRateValidate(java.lang.String productLossRate) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_LOSS_RATE_UPPER_LIMIT < 1) {
         try { PRODUCT_LOSS_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productLossRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_LOSS_RATE_UPPER_LIMIT = 200; }
      }
      if (productLossRate != null && !wt.fc.PersistenceHelper.checkStoredLength(productLossRate.toString(), PRODUCT_LOSS_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productLossRate"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_LOSS_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productLossRate", this.productLossRate, productLossRate));
   }

   /**
    * 생산효율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String EFFICIENT_RATE = "efficientRate";
   static int EFFICIENT_RATE_UPPER_LIMIT = -1;
   java.lang.String efficientRate;
   /**
    * 생산효율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getEfficientRate() {
      return efficientRate;
   }
   /**
    * 생산효율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setEfficientRate(java.lang.String efficientRate) throws wt.util.WTPropertyVetoException {
      efficientRateValidate(efficientRate);
      this.efficientRate = efficientRate;
   }
   void efficientRateValidate(java.lang.String efficientRate) throws wt.util.WTPropertyVetoException {
      if (EFFICIENT_RATE_UPPER_LIMIT < 1) {
         try { EFFICIENT_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("efficientRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EFFICIENT_RATE_UPPER_LIMIT = 200; }
      }
      if (efficientRate != null && !wt.fc.PersistenceHelper.checkStoredLength(efficientRate.toString(), EFFICIENT_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "efficientRate"), java.lang.String.valueOf(java.lang.Math.min(EFFICIENT_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "efficientRate", this.efficientRate, efficientRate));
   }

   /**
    * 추가공수효율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String ADD_MAN_HOUR_RATE = "addManHourRate";
   static int ADD_MAN_HOUR_RATE_UPPER_LIMIT = -1;
   java.lang.String addManHourRate;
   /**
    * 추가공수효율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getAddManHourRate() {
      return addManHourRate;
   }
   /**
    * 추가공수효율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setAddManHourRate(java.lang.String addManHourRate) throws wt.util.WTPropertyVetoException {
      addManHourRateValidate(addManHourRate);
      this.addManHourRate = addManHourRate;
   }
   void addManHourRateValidate(java.lang.String addManHourRate) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_RATE_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_RATE_UPPER_LIMIT = 200; }
      }
      if (addManHourRate != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourRate.toString(), ADD_MAN_HOUR_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourRate"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourRate", this.addManHourRate, addManHourRate));
   }

   /**
    * 표준임율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String LABOR_COST_RATE = "laborCostRate";
   static int LABOR_COST_RATE_UPPER_LIMIT = -1;
   java.lang.String laborCostRate;
   /**
    * 표준임율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getLaborCostRate() {
      return laborCostRate;
   }
   /**
    * 표준임율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setLaborCostRate(java.lang.String laborCostRate) throws wt.util.WTPropertyVetoException {
      laborCostRateValidate(laborCostRate);
      this.laborCostRate = laborCostRate;
   }
   void laborCostRateValidate(java.lang.String laborCostRate) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_RATE_UPPER_LIMIT < 1) {
         try { LABOR_COST_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_RATE_UPPER_LIMIT = 200; }
      }
      if (laborCostRate != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostRate.toString(), LABOR_COST_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostRate"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostRate", this.laborCostRate, laborCostRate));
   }

   /**
    * 표준임율상승율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String LABOR_COST_CLIMB_RATE = "laborCostClimbRate";
   static int LABOR_COST_CLIMB_RATE_UPPER_LIMIT = -1;
   java.lang.String laborCostClimbRate;
   /**
    * 표준임율상승율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getLaborCostClimbRate() {
      return laborCostClimbRate;
   }
   /**
    * 표준임율상승율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setLaborCostClimbRate(java.lang.String laborCostClimbRate) throws wt.util.WTPropertyVetoException {
      laborCostClimbRateValidate(laborCostClimbRate);
      this.laborCostClimbRate = laborCostClimbRate;
   }
   void laborCostClimbRateValidate(java.lang.String laborCostClimbRate) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_CLIMB_RATE_UPPER_LIMIT < 1) {
         try { LABOR_COST_CLIMB_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostClimbRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_CLIMB_RATE_UPPER_LIMIT = 200; }
      }
      if (laborCostClimbRate != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostClimbRate.toString(), LABOR_COST_CLIMB_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostClimbRate"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_CLIMB_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostClimbRate", this.laborCostClimbRate, laborCostClimbRate));
   }

   /**
    * 표준임율년도
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String LABOR_COST_YEAR = "laborCostYear";
   static int LABOR_COST_YEAR_UPPER_LIMIT = -1;
   java.lang.String laborCostYear;
   /**
    * 표준임율년도
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getLaborCostYear() {
      return laborCostYear;
   }
   /**
    * 표준임율년도
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setLaborCostYear(java.lang.String laborCostYear) throws wt.util.WTPropertyVetoException {
      laborCostYearValidate(laborCostYear);
      this.laborCostYear = laborCostYear;
   }
   void laborCostYearValidate(java.lang.String laborCostYear) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_YEAR_UPPER_LIMIT < 1) {
         try { LABOR_COST_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_YEAR_UPPER_LIMIT = 200; }
      }
      if (laborCostYear != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostYear.toString(), LABOR_COST_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostYear"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostYear", this.laborCostYear, laborCostYear));
   }

   /**
    * 관리대수
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String UNIT_MANAGE = "unitManage";
   static int UNIT_MANAGE_UPPER_LIMIT = -1;
   java.lang.String unitManage;
   /**
    * 관리대수
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getUnitManage() {
      return unitManage;
   }
   /**
    * 관리대수
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setUnitManage(java.lang.String unitManage) throws wt.util.WTPropertyVetoException {
      unitManageValidate(unitManage);
      this.unitManage = unitManage;
   }
   void unitManageValidate(java.lang.String unitManage) throws wt.util.WTPropertyVetoException {
      if (UNIT_MANAGE_UPPER_LIMIT < 1) {
         try { UNIT_MANAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("unitManage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { UNIT_MANAGE_UPPER_LIMIT = 200; }
      }
      if (unitManage != null && !wt.fc.PersistenceHelper.checkStoredLength(unitManage.toString(), UNIT_MANAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "unitManage"), java.lang.String.valueOf(java.lang.Math.min(UNIT_MANAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "unitManage", this.unitManage, unitManage));
   }

   /**
    * 간접경비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String INDIRECT_COST_RATE = "indirectCostRate";
   static int INDIRECT_COST_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectCostRate;
   /**
    * 간접경비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getIndirectCostRate() {
      return indirectCostRate;
   }
   /**
    * 간접경비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setIndirectCostRate(java.lang.String indirectCostRate) throws wt.util.WTPropertyVetoException {
      indirectCostRateValidate(indirectCostRate);
      this.indirectCostRate = indirectCostRate;
   }
   void indirectCostRateValidate(java.lang.String indirectCostRate) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_COST_RATE_UPPER_LIMIT < 1) {
         try { INDIRECT_COST_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectCostRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_COST_RATE_UPPER_LIMIT = 200; }
      }
      if (indirectCostRate != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectCostRate.toString(), INDIRECT_COST_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectCostRate"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_COST_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectCostRate", this.indirectCostRate, indirectCostRate));
   }

   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MOLD_MAINTENANCE = "moldMaintenance";
   static int MOLD_MAINTENANCE_UPPER_LIMIT = -1;
   java.lang.String moldMaintenance;
   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getMoldMaintenance() {
      return moldMaintenance;
   }
   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setMoldMaintenance(java.lang.String moldMaintenance) throws wt.util.WTPropertyVetoException {
      moldMaintenanceValidate(moldMaintenance);
      this.moldMaintenance = moldMaintenance;
   }
   void moldMaintenanceValidate(java.lang.String moldMaintenance) throws wt.util.WTPropertyVetoException {
      if (MOLD_MAINTENANCE_UPPER_LIMIT < 1) {
         try { MOLD_MAINTENANCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMaintenance").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MAINTENANCE_UPPER_LIMIT = 200; }
      }
      if (moldMaintenance != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMaintenance.toString(), MOLD_MAINTENANCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMaintenance"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MAINTENANCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMaintenance", this.moldMaintenance, moldMaintenance));
   }

   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String TABARYU = "tabaryu";
   static int TABARYU_UPPER_LIMIT = -1;
   java.lang.String tabaryu;
   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getTabaryu() {
      return tabaryu;
   }
   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setTabaryu(java.lang.String tabaryu) throws wt.util.WTPropertyVetoException {
      tabaryuValidate(tabaryu);
      this.tabaryu = tabaryu;
   }
   void tabaryuValidate(java.lang.String tabaryu) throws wt.util.WTPropertyVetoException {
      if (TABARYU_UPPER_LIMIT < 1) {
         try { TABARYU_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tabaryu").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TABARYU_UPPER_LIMIT = 200; }
      }
      if (tabaryu != null && !wt.fc.PersistenceHelper.checkStoredLength(tabaryu.toString(), TABARYU_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tabaryu"), java.lang.String.valueOf(java.lang.Math.min(TABARYU_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tabaryu", this.tabaryu, tabaryu));
   }

   /**
    * 표준전력비
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String ELEC_COST = "elecCost";
   static int ELEC_COST_UPPER_LIMIT = -1;
   java.lang.String elecCost;
   /**
    * 표준전력비
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getElecCost() {
      return elecCost;
   }
   /**
    * 표준전력비
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setElecCost(java.lang.String elecCost) throws wt.util.WTPropertyVetoException {
      elecCostValidate(elecCost);
      this.elecCost = elecCost;
   }
   void elecCostValidate(java.lang.String elecCost) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_UPPER_LIMIT < 1) {
         try { ELEC_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_UPPER_LIMIT = 200; }
      }
      if (elecCost != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCost.toString(), ELEC_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCost"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCost", this.elecCost, elecCost));
   }

   /**
    * 표준전력비상승율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String ELEC_COST_CLIMB_RATE = "elecCostClimbRate";
   static int ELEC_COST_CLIMB_RATE_UPPER_LIMIT = -1;
   java.lang.String elecCostClimbRate;
   /**
    * 표준전력비상승율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getElecCostClimbRate() {
      return elecCostClimbRate;
   }
   /**
    * 표준전력비상승율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setElecCostClimbRate(java.lang.String elecCostClimbRate) throws wt.util.WTPropertyVetoException {
      elecCostClimbRateValidate(elecCostClimbRate);
      this.elecCostClimbRate = elecCostClimbRate;
   }
   void elecCostClimbRateValidate(java.lang.String elecCostClimbRate) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_CLIMB_RATE_UPPER_LIMIT < 1) {
         try { ELEC_COST_CLIMB_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCostClimbRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_CLIMB_RATE_UPPER_LIMIT = 200; }
      }
      if (elecCostClimbRate != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCostClimbRate.toString(), ELEC_COST_CLIMB_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCostClimbRate"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_CLIMB_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCostClimbRate", this.elecCostClimbRate, elecCostClimbRate));
   }

   /**
    * 표준전력비년도
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String ELEC_COST_YEAR = "elecCostYear";
   static int ELEC_COST_YEAR_UPPER_LIMIT = -1;
   java.lang.String elecCostYear;
   /**
    * 표준전력비년도
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getElecCostYear() {
      return elecCostYear;
   }
   /**
    * 표준전력비년도
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setElecCostYear(java.lang.String elecCostYear) throws wt.util.WTPropertyVetoException {
      elecCostYearValidate(elecCostYear);
      this.elecCostYear = elecCostYear;
   }
   void elecCostYearValidate(java.lang.String elecCostYear) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_YEAR_UPPER_LIMIT < 1) {
         try { ELEC_COST_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCostYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_YEAR_UPPER_LIMIT = 200; }
      }
      if (elecCostYear != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCostYear.toString(), ELEC_COST_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCostYear"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCostYear", this.elecCostYear, elecCostYear));
   }

   /**
    * 기계감가(사출,프레스)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MACHINE_DPC_COST = "machineDpcCost";
   static int MACHINE_DPC_COST_UPPER_LIMIT = -1;
   java.lang.String machineDpcCost;
   /**
    * 기계감가(사출,프레스)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getMachineDpcCost() {
      return machineDpcCost;
   }
   /**
    * 기계감가(사출,프레스)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setMachineDpcCost(java.lang.String machineDpcCost) throws wt.util.WTPropertyVetoException {
      machineDpcCostValidate(machineDpcCost);
      this.machineDpcCost = machineDpcCost;
   }
   void machineDpcCostValidate(java.lang.String machineDpcCost) throws wt.util.WTPropertyVetoException {
      if (MACHINE_DPC_COST_UPPER_LIMIT < 1) {
         try { MACHINE_DPC_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineDpcCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_DPC_COST_UPPER_LIMIT = 200; }
      }
      if (machineDpcCost != null && !wt.fc.PersistenceHelper.checkStoredLength(machineDpcCost.toString(), MACHINE_DPC_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineDpcCost"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_DPC_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineDpcCost", this.machineDpcCost, machineDpcCost));
   }

   /**
    * 조립Loss율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String ASSY_LOSS_RATE = "assyLossRate";
   static int ASSY_LOSS_RATE_UPPER_LIMIT = -1;
   java.lang.String assyLossRate;
   /**
    * 조립Loss율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getAssyLossRate() {
      return assyLossRate;
   }
   /**
    * 조립Loss율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setAssyLossRate(java.lang.String assyLossRate) throws wt.util.WTPropertyVetoException {
      assyLossRateValidate(assyLossRate);
      this.assyLossRate = assyLossRate;
   }
   void assyLossRateValidate(java.lang.String assyLossRate) throws wt.util.WTPropertyVetoException {
      if (ASSY_LOSS_RATE_UPPER_LIMIT < 1) {
         try { ASSY_LOSS_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assyLossRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSY_LOSS_RATE_UPPER_LIMIT = 200; }
      }
      if (assyLossRate != null && !wt.fc.PersistenceHelper.checkStoredLength(assyLossRate.toString(), ASSY_LOSS_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assyLossRate"), java.lang.String.valueOf(java.lang.Math.min(ASSY_LOSS_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assyLossRate", this.assyLossRate, assyLossRate));
   }

   /**
    * 재료관리비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MTR_MANAGE_RATE = "mtrManageRate";
   static int MTR_MANAGE_RATE_UPPER_LIMIT = -1;
   java.lang.String mtrManageRate;
   /**
    * 재료관리비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getMtrManageRate() {
      return mtrManageRate;
   }
   /**
    * 재료관리비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setMtrManageRate(java.lang.String mtrManageRate) throws wt.util.WTPropertyVetoException {
      mtrManageRateValidate(mtrManageRate);
      this.mtrManageRate = mtrManageRate;
   }
   void mtrManageRateValidate(java.lang.String mtrManageRate) throws wt.util.WTPropertyVetoException {
      if (MTR_MANAGE_RATE_UPPER_LIMIT < 1) {
         try { MTR_MANAGE_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrManageRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_MANAGE_RATE_UPPER_LIMIT = 200; }
      }
      if (mtrManageRate != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrManageRate.toString(), MTR_MANAGE_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrManageRate"), java.lang.String.valueOf(java.lang.Math.min(MTR_MANAGE_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrManageRate", this.mtrManageRate, mtrManageRate));
   }

   /**
    * 일반관리비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String CO_MANAGE_RATE = "coManageRate";
   static int CO_MANAGE_RATE_UPPER_LIMIT = -1;
   java.lang.String coManageRate;
   /**
    * 일반관리비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getCoManageRate() {
      return coManageRate;
   }
   /**
    * 일반관리비율
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setCoManageRate(java.lang.String coManageRate) throws wt.util.WTPropertyVetoException {
      coManageRateValidate(coManageRate);
      this.coManageRate = coManageRate;
   }
   void coManageRateValidate(java.lang.String coManageRate) throws wt.util.WTPropertyVetoException {
      if (CO_MANAGE_RATE_UPPER_LIMIT < 1) {
         try { CO_MANAGE_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coManageRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CO_MANAGE_RATE_UPPER_LIMIT = 200; }
      }
      if (coManageRate != null && !wt.fc.PersistenceHelper.checkStoredLength(coManageRate.toString(), CO_MANAGE_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coManageRate"), java.lang.String.valueOf(java.lang.Math.min(CO_MANAGE_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coManageRate", this.coManageRate, coManageRate));
   }

   /**
    * R&D
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String RND = "rnd";
   static int RND_UPPER_LIMIT = -1;
   java.lang.String rnd;
   /**
    * R&D
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getRnd() {
      return rnd;
   }
   /**
    * R&D
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setRnd(java.lang.String rnd) throws wt.util.WTPropertyVetoException {
      rndValidate(rnd);
      this.rnd = rnd;
   }
   void rndValidate(java.lang.String rnd) throws wt.util.WTPropertyVetoException {
      if (RND_UPPER_LIMIT < 1) {
         try { RND_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rnd").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RND_UPPER_LIMIT = 200; }
      }
      if (rnd != null && !wt.fc.PersistenceHelper.checkStoredLength(rnd.toString(), RND_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rnd"), java.lang.String.valueOf(java.lang.Math.min(RND_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rnd", this.rnd, rnd));
   }

   /**
    * 간접경비비용
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String IN_DIRECT_COST = "inDirectCost";
   static int IN_DIRECT_COST_UPPER_LIMIT = -1;
   java.lang.String inDirectCost;
   /**
    * 간접경비비용
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getInDirectCost() {
      return inDirectCost;
   }
   /**
    * 간접경비비용
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setInDirectCost(java.lang.String inDirectCost) throws wt.util.WTPropertyVetoException {
      inDirectCostValidate(inDirectCost);
      this.inDirectCost = inDirectCost;
   }
   void inDirectCostValidate(java.lang.String inDirectCost) throws wt.util.WTPropertyVetoException {
      if (IN_DIRECT_COST_UPPER_LIMIT < 1) {
         try { IN_DIRECT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inDirectCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_DIRECT_COST_UPPER_LIMIT = 200; }
      }
      if (inDirectCost != null && !wt.fc.PersistenceHelper.checkStoredLength(inDirectCost.toString(), IN_DIRECT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inDirectCost"), java.lang.String.valueOf(java.lang.Math.min(IN_DIRECT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inDirectCost", this.inDirectCost, inDirectCost));
   }

   /**
    * 일반관리비용
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String CO_MANAGE_COST = "coManageCost";
   static int CO_MANAGE_COST_UPPER_LIMIT = -1;
   java.lang.String coManageCost;
   /**
    * 일반관리비용
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getCoManageCost() {
      return coManageCost;
   }
   /**
    * 일반관리비용
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setCoManageCost(java.lang.String coManageCost) throws wt.util.WTPropertyVetoException {
      coManageCostValidate(coManageCost);
      this.coManageCost = coManageCost;
   }
   void coManageCostValidate(java.lang.String coManageCost) throws wt.util.WTPropertyVetoException {
      if (CO_MANAGE_COST_UPPER_LIMIT < 1) {
         try { CO_MANAGE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coManageCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CO_MANAGE_COST_UPPER_LIMIT = 200; }
      }
      if (coManageCost != null && !wt.fc.PersistenceHelper.checkStoredLength(coManageCost.toString(), CO_MANAGE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coManageCost"), java.lang.String.valueOf(java.lang.Math.min(CO_MANAGE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coManageCost", this.coManageCost, coManageCost));
   }

   /**
    * 금형유지비설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MOLD_FROM_VAL = "moldFromVal";
   static int MOLD_FROM_VAL_UPPER_LIMIT = -1;
   java.lang.String moldFromVal = "0";
   /**
    * 금형유지비설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getMoldFromVal() {
      return moldFromVal;
   }
   /**
    * 금형유지비설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setMoldFromVal(java.lang.String moldFromVal) throws wt.util.WTPropertyVetoException {
      moldFromValValidate(moldFromVal);
      this.moldFromVal = moldFromVal;
   }
   void moldFromValValidate(java.lang.String moldFromVal) throws wt.util.WTPropertyVetoException {
      if (MOLD_FROM_VAL_UPPER_LIMIT < 1) {
         try { MOLD_FROM_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldFromVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_FROM_VAL_UPPER_LIMIT = 200; }
      }
      if (moldFromVal != null && !wt.fc.PersistenceHelper.checkStoredLength(moldFromVal.toString(), MOLD_FROM_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldFromVal"), java.lang.String.valueOf(java.lang.Math.min(MOLD_FROM_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldFromVal", this.moldFromVal, moldFromVal));
   }

   /**
    * 금형유지비부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MOLD_FROM_SIGN = "moldFromSign";
   static int MOLD_FROM_SIGN_UPPER_LIMIT = -1;
   java.lang.String moldFromSign;
   /**
    * 금형유지비부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getMoldFromSign() {
      return moldFromSign;
   }
   /**
    * 금형유지비부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setMoldFromSign(java.lang.String moldFromSign) throws wt.util.WTPropertyVetoException {
      moldFromSignValidate(moldFromSign);
      this.moldFromSign = moldFromSign;
   }
   void moldFromSignValidate(java.lang.String moldFromSign) throws wt.util.WTPropertyVetoException {
      if (MOLD_FROM_SIGN_UPPER_LIMIT < 1) {
         try { MOLD_FROM_SIGN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldFromSign").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_FROM_SIGN_UPPER_LIMIT = 200; }
      }
      if (moldFromSign != null && !wt.fc.PersistenceHelper.checkStoredLength(moldFromSign.toString(), MOLD_FROM_SIGN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldFromSign"), java.lang.String.valueOf(java.lang.Math.min(MOLD_FROM_SIGN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldFromSign", this.moldFromSign, moldFromSign));
   }

   /**
    * 금형유지비설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MOLD_TO_VAL = "moldToVal";
   static int MOLD_TO_VAL_UPPER_LIMIT = -1;
   java.lang.String moldToVal = "0";
   /**
    * 금형유지비설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getMoldToVal() {
      return moldToVal;
   }
   /**
    * 금형유지비설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setMoldToVal(java.lang.String moldToVal) throws wt.util.WTPropertyVetoException {
      moldToValValidate(moldToVal);
      this.moldToVal = moldToVal;
   }
   void moldToValValidate(java.lang.String moldToVal) throws wt.util.WTPropertyVetoException {
      if (MOLD_TO_VAL_UPPER_LIMIT < 1) {
         try { MOLD_TO_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldToVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_TO_VAL_UPPER_LIMIT = 200; }
      }
      if (moldToVal != null && !wt.fc.PersistenceHelper.checkStoredLength(moldToVal.toString(), MOLD_TO_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldToVal"), java.lang.String.valueOf(java.lang.Math.min(MOLD_TO_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldToVal", this.moldToVal, moldToVal));
   }

   /**
    * 금형유지비부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MOLD_TO_SIGN = "moldToSign";
   static int MOLD_TO_SIGN_UPPER_LIMIT = -1;
   java.lang.String moldToSign;
   /**
    * 금형유지비부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getMoldToSign() {
      return moldToSign;
   }
   /**
    * 금형유지비부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setMoldToSign(java.lang.String moldToSign) throws wt.util.WTPropertyVetoException {
      moldToSignValidate(moldToSign);
      this.moldToSign = moldToSign;
   }
   void moldToSignValidate(java.lang.String moldToSign) throws wt.util.WTPropertyVetoException {
      if (MOLD_TO_SIGN_UPPER_LIMIT < 1) {
         try { MOLD_TO_SIGN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldToSign").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_TO_SIGN_UPPER_LIMIT = 200; }
      }
      if (moldToSign != null && !wt.fc.PersistenceHelper.checkStoredLength(moldToSign.toString(), MOLD_TO_SIGN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldToSign"), java.lang.String.valueOf(java.lang.Math.min(MOLD_TO_SIGN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldToSign", this.moldToSign, moldToSign));
   }

   /**
    * 설비Ton설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String TON_FROM_VAL = "tonFromVal";
   static int TON_FROM_VAL_UPPER_LIMIT = -1;
   java.lang.String tonFromVal = "0";
   /**
    * 설비Ton설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getTonFromVal() {
      return tonFromVal;
   }
   /**
    * 설비Ton설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setTonFromVal(java.lang.String tonFromVal) throws wt.util.WTPropertyVetoException {
      tonFromValValidate(tonFromVal);
      this.tonFromVal = tonFromVal;
   }
   void tonFromValValidate(java.lang.String tonFromVal) throws wt.util.WTPropertyVetoException {
      if (TON_FROM_VAL_UPPER_LIMIT < 1) {
         try { TON_FROM_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tonFromVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TON_FROM_VAL_UPPER_LIMIT = 200; }
      }
      if (tonFromVal != null && !wt.fc.PersistenceHelper.checkStoredLength(tonFromVal.toString(), TON_FROM_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tonFromVal"), java.lang.String.valueOf(java.lang.Math.min(TON_FROM_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tonFromVal", this.tonFromVal, tonFromVal));
   }

   /**
    * 설비Ton부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String TON_FROM_SIGN = "tonFromSign";
   static int TON_FROM_SIGN_UPPER_LIMIT = -1;
   java.lang.String tonFromSign;
   /**
    * 설비Ton부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getTonFromSign() {
      return tonFromSign;
   }
   /**
    * 설비Ton부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setTonFromSign(java.lang.String tonFromSign) throws wt.util.WTPropertyVetoException {
      tonFromSignValidate(tonFromSign);
      this.tonFromSign = tonFromSign;
   }
   void tonFromSignValidate(java.lang.String tonFromSign) throws wt.util.WTPropertyVetoException {
      if (TON_FROM_SIGN_UPPER_LIMIT < 1) {
         try { TON_FROM_SIGN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tonFromSign").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TON_FROM_SIGN_UPPER_LIMIT = 200; }
      }
      if (tonFromSign != null && !wt.fc.PersistenceHelper.checkStoredLength(tonFromSign.toString(), TON_FROM_SIGN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tonFromSign"), java.lang.String.valueOf(java.lang.Math.min(TON_FROM_SIGN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tonFromSign", this.tonFromSign, tonFromSign));
   }

   /**
    * 설비Ton설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String TON_TO_VAL = "tonToVal";
   static int TON_TO_VAL_UPPER_LIMIT = -1;
   java.lang.String tonToVal = "0";
   /**
    * 설비Ton설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getTonToVal() {
      return tonToVal;
   }
   /**
    * 설비Ton설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setTonToVal(java.lang.String tonToVal) throws wt.util.WTPropertyVetoException {
      tonToValValidate(tonToVal);
      this.tonToVal = tonToVal;
   }
   void tonToValValidate(java.lang.String tonToVal) throws wt.util.WTPropertyVetoException {
      if (TON_TO_VAL_UPPER_LIMIT < 1) {
         try { TON_TO_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tonToVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TON_TO_VAL_UPPER_LIMIT = 200; }
      }
      if (tonToVal != null && !wt.fc.PersistenceHelper.checkStoredLength(tonToVal.toString(), TON_TO_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tonToVal"), java.lang.String.valueOf(java.lang.Math.min(TON_TO_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tonToVal", this.tonToVal, tonToVal));
   }

   /**
    * 설비Ton부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String TON_TO_SIGN = "tonToSign";
   static int TON_TO_SIGN_UPPER_LIMIT = -1;
   java.lang.String tonToSign;
   /**
    * 설비Ton부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getTonToSign() {
      return tonToSign;
   }
   /**
    * 설비Ton부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setTonToSign(java.lang.String tonToSign) throws wt.util.WTPropertyVetoException {
      tonToSignValidate(tonToSign);
      this.tonToSign = tonToSign;
   }
   void tonToSignValidate(java.lang.String tonToSign) throws wt.util.WTPropertyVetoException {
      if (TON_TO_SIGN_UPPER_LIMIT < 1) {
         try { TON_TO_SIGN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tonToSign").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TON_TO_SIGN_UPPER_LIMIT = 200; }
      }
      if (tonToSign != null && !wt.fc.PersistenceHelper.checkStoredLength(tonToSign.toString(), TON_TO_SIGN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tonToSign"), java.lang.String.valueOf(java.lang.Math.min(TON_TO_SIGN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tonToSign", this.tonToSign, tonToSign));
   }

   /**
    * 부품별 max 물량 설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String QUANTITY_MAX_FROM_VAL = "quantityMaxFromVal";
   static int QUANTITY_MAX_FROM_VAL_UPPER_LIMIT = -1;
   java.lang.String quantityMaxFromVal = "0";
   /**
    * 부품별 max 물량 설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getQuantityMaxFromVal() {
      return quantityMaxFromVal;
   }
   /**
    * 부품별 max 물량 설정값From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setQuantityMaxFromVal(java.lang.String quantityMaxFromVal) throws wt.util.WTPropertyVetoException {
      quantityMaxFromValValidate(quantityMaxFromVal);
      this.quantityMaxFromVal = quantityMaxFromVal;
   }
   void quantityMaxFromValValidate(java.lang.String quantityMaxFromVal) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_MAX_FROM_VAL_UPPER_LIMIT < 1) {
         try { QUANTITY_MAX_FROM_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantityMaxFromVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_MAX_FROM_VAL_UPPER_LIMIT = 200; }
      }
      if (quantityMaxFromVal != null && !wt.fc.PersistenceHelper.checkStoredLength(quantityMaxFromVal.toString(), QUANTITY_MAX_FROM_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityMaxFromVal"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_MAX_FROM_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantityMaxFromVal", this.quantityMaxFromVal, quantityMaxFromVal));
   }

   /**
    * 부품별 max 물량 부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String QUANTITY_MAX_FROM_SIGN = "quantityMaxFromSign";
   static int QUANTITY_MAX_FROM_SIGN_UPPER_LIMIT = -1;
   java.lang.String quantityMaxFromSign;
   /**
    * 부품별 max 물량 부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getQuantityMaxFromSign() {
      return quantityMaxFromSign;
   }
   /**
    * 부품별 max 물량 부등호From
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setQuantityMaxFromSign(java.lang.String quantityMaxFromSign) throws wt.util.WTPropertyVetoException {
      quantityMaxFromSignValidate(quantityMaxFromSign);
      this.quantityMaxFromSign = quantityMaxFromSign;
   }
   void quantityMaxFromSignValidate(java.lang.String quantityMaxFromSign) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_MAX_FROM_SIGN_UPPER_LIMIT < 1) {
         try { QUANTITY_MAX_FROM_SIGN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantityMaxFromSign").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_MAX_FROM_SIGN_UPPER_LIMIT = 200; }
      }
      if (quantityMaxFromSign != null && !wt.fc.PersistenceHelper.checkStoredLength(quantityMaxFromSign.toString(), QUANTITY_MAX_FROM_SIGN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityMaxFromSign"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_MAX_FROM_SIGN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantityMaxFromSign", this.quantityMaxFromSign, quantityMaxFromSign));
   }

   /**
    * 부품별 max 물량 설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String QUANTITY_MAX_TO_VAL = "quantityMaxToVal";
   static int QUANTITY_MAX_TO_VAL_UPPER_LIMIT = -1;
   java.lang.String quantityMaxToVal = "0";
   /**
    * 부품별 max 물량 설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getQuantityMaxToVal() {
      return quantityMaxToVal;
   }
   /**
    * 부품별 max 물량 설정값To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setQuantityMaxToVal(java.lang.String quantityMaxToVal) throws wt.util.WTPropertyVetoException {
      quantityMaxToValValidate(quantityMaxToVal);
      this.quantityMaxToVal = quantityMaxToVal;
   }
   void quantityMaxToValValidate(java.lang.String quantityMaxToVal) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_MAX_TO_VAL_UPPER_LIMIT < 1) {
         try { QUANTITY_MAX_TO_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantityMaxToVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_MAX_TO_VAL_UPPER_LIMIT = 200; }
      }
      if (quantityMaxToVal != null && !wt.fc.PersistenceHelper.checkStoredLength(quantityMaxToVal.toString(), QUANTITY_MAX_TO_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityMaxToVal"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_MAX_TO_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantityMaxToVal", this.quantityMaxToVal, quantityMaxToVal));
   }

   /**
    * 부품별 max 물량 부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String QUANTITY_MAX_TO_SIGN = "quantityMaxToSign";
   static int QUANTITY_MAX_TO_SIGN_UPPER_LIMIT = -1;
   java.lang.String quantityMaxToSign;
   /**
    * 부품별 max 물량 부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getQuantityMaxToSign() {
      return quantityMaxToSign;
   }
   /**
    * 부품별 max 물량 부등호To
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setQuantityMaxToSign(java.lang.String quantityMaxToSign) throws wt.util.WTPropertyVetoException {
      quantityMaxToSignValidate(quantityMaxToSign);
      this.quantityMaxToSign = quantityMaxToSign;
   }
   void quantityMaxToSignValidate(java.lang.String quantityMaxToSign) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_MAX_TO_SIGN_UPPER_LIMIT < 1) {
         try { QUANTITY_MAX_TO_SIGN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantityMaxToSign").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_MAX_TO_SIGN_UPPER_LIMIT = 200; }
      }
      if (quantityMaxToSign != null && !wt.fc.PersistenceHelper.checkStoredLength(quantityMaxToSign.toString(), QUANTITY_MAX_TO_SIGN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityMaxToSign"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_MAX_TO_SIGN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantityMaxToSign", this.quantityMaxToSign, quantityMaxToSign));
   }

   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String SORT_LOCATION = "sortLocation";
   java.lang.Integer sortLocation;
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.Integer getSortLocation() {
      return sortLocation;
   }
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setSortLocation(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
      sortLocationValidate(sortLocation);
      this.sortLocation = sortLocation;
   }
   void sortLocationValidate(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
   }

   /**
    * BuyBack간접경비율(본사)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String BUY_BACK_INDIRECT_COST_RATE = "buyBackIndirectCostRate";
   static int BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT = -1;
   java.lang.String buyBackIndirectCostRate = "0";
   /**
    * BuyBack간접경비율(본사)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getBuyBackIndirectCostRate() {
      return buyBackIndirectCostRate;
   }
   /**
    * BuyBack간접경비율(본사)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setBuyBackIndirectCostRate(java.lang.String buyBackIndirectCostRate) throws wt.util.WTPropertyVetoException {
      buyBackIndirectCostRateValidate(buyBackIndirectCostRate);
      this.buyBackIndirectCostRate = buyBackIndirectCostRate;
   }
   void buyBackIndirectCostRateValidate(java.lang.String buyBackIndirectCostRate) throws wt.util.WTPropertyVetoException {
      if (BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT < 1) {
         try { BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("buyBackIndirectCostRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT = 200; }
      }
      if (buyBackIndirectCostRate != null && !wt.fc.PersistenceHelper.checkStoredLength(buyBackIndirectCostRate.toString(), BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "buyBackIndirectCostRate"), java.lang.String.valueOf(java.lang.Math.min(BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "buyBackIndirectCostRate", this.buyBackIndirectCostRate, buyBackIndirectCostRate));
   }

   /**
    * 간접인건비비율(노무비)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String INDIRECT_LABOUR_RATE = "indirectLabourRate";
   static int INDIRECT_LABOUR_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectLabourRate = "0";
   /**
    * 간접인건비비율(노무비)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getIndirectLabourRate() {
      return indirectLabourRate;
   }
   /**
    * 간접인건비비율(노무비)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setIndirectLabourRate(java.lang.String indirectLabourRate) throws wt.util.WTPropertyVetoException {
      indirectLabourRateValidate(indirectLabourRate);
      this.indirectLabourRate = indirectLabourRate;
   }
   void indirectLabourRateValidate(java.lang.String indirectLabourRate) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_LABOUR_RATE_UPPER_LIMIT < 1) {
         try { INDIRECT_LABOUR_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectLabourRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_LABOUR_RATE_UPPER_LIMIT = 200; }
      }
      if (indirectLabourRate != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectLabourRate.toString(), INDIRECT_LABOUR_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectLabourRate"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_LABOUR_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectLabourRate", this.indirectLabourRate, indirectLabourRate));
   }

   /**
    * 간접인건비비율(R&D)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String INDIRECT_RND_RATE = "indirectRndRate";
   static int INDIRECT_RND_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectRndRate = "0";
   /**
    * 간접인건비비율(R&D)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public java.lang.String getIndirectRndRate() {
      return indirectRndRate;
   }
   /**
    * 간접인건비비율(R&D)
    *
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setIndirectRndRate(java.lang.String indirectRndRate) throws wt.util.WTPropertyVetoException {
      indirectRndRateValidate(indirectRndRate);
      this.indirectRndRate = indirectRndRate;
   }
   void indirectRndRateValidate(java.lang.String indirectRndRate) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_RND_RATE_UPPER_LIMIT < 1) {
         try { INDIRECT_RND_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectRndRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_RND_RATE_UPPER_LIMIT = 200; }
      }
      if (indirectRndRate != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectRndRate.toString(), INDIRECT_RND_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectRndRate"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_RND_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectRndRate", this.indirectRndRate, indirectRndRate));
   }

   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String COSTPART_TYPE = "costpartType";
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String COSTPART_TYPE_REFERENCE = "costpartTypeReference";
   wt.fc.ObjectReference costpartTypeReference;
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public ext.ket.cost.entity.CostPartType getCostpartType() {
      return (costpartTypeReference != null) ? (ext.ket.cost.entity.CostPartType) costpartTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public wt.fc.ObjectReference getCostpartTypeReference() {
      return costpartTypeReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setCostpartType(ext.ket.cost.entity.CostPartType the_costpartType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostpartTypeReference(the_costpartType == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostPartType) the_costpartType));
   }
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setCostpartTypeReference(wt.fc.ObjectReference the_costpartTypeReference) throws wt.util.WTPropertyVetoException {
      costpartTypeReferenceValidate(the_costpartTypeReference);
      costpartTypeReference = (wt.fc.ObjectReference) the_costpartTypeReference;
   }
   void costpartTypeReferenceValidate(wt.fc.ObjectReference the_costpartTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_costpartTypeReference == null || the_costpartTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costpartTypeReference") },
               new java.beans.PropertyChangeEvent(this, "costpartTypeReference", this.costpartTypeReference, costpartTypeReference));
      if (the_costpartTypeReference != null && the_costpartTypeReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.CostPartType.class.isAssignableFrom(the_costpartTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costpartTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "costpartTypeReference", this.costpartTypeReference, costpartTypeReference));
   }

   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MANUFACTURE_CODE = "manufactureCode";
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public static final java.lang.String MANUFACTURE_CODE_REFERENCE = "manufactureCodeReference";
   wt.fc.ObjectReference manufactureCodeReference;
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public e3ps.common.code.NumberCode getManufactureCode() {
      return (manufactureCodeReference != null) ? (e3ps.common.code.NumberCode) manufactureCodeReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public wt.fc.ObjectReference getManufactureCodeReference() {
      return manufactureCodeReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setManufactureCode(e3ps.common.code.NumberCode the_manufactureCode) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setManufactureCodeReference(the_manufactureCode == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_manufactureCode));
   }
   /**
    * @see ext.ket.cost.entity.CostPartTypeCodeMaster
    */
   public void setManufactureCodeReference(wt.fc.ObjectReference the_manufactureCodeReference) throws wt.util.WTPropertyVetoException {
      manufactureCodeReferenceValidate(the_manufactureCodeReference);
      manufactureCodeReference = (wt.fc.ObjectReference) the_manufactureCodeReference;
   }
   void manufactureCodeReferenceValidate(wt.fc.ObjectReference the_manufactureCodeReference) throws wt.util.WTPropertyVetoException {
      if (the_manufactureCodeReference == null || the_manufactureCodeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manufactureCodeReference") },
               new java.beans.PropertyChangeEvent(this, "manufactureCodeReference", this.manufactureCodeReference, manufactureCodeReference));
      if (the_manufactureCodeReference != null && the_manufactureCodeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_manufactureCodeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manufactureCodeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "manufactureCodeReference", this.manufactureCodeReference, manufactureCodeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -4333708198654443716L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( addManHourRate );
      output.writeObject( assyLossRate );
      output.writeObject( buyBackIndirectCostRate );
      output.writeObject( coManageCost );
      output.writeObject( coManageRate );
      output.writeObject( costpartTypeReference );
      output.writeObject( efficientRate );
      output.writeObject( elecCost );
      output.writeObject( elecCostClimbRate );
      output.writeObject( elecCostYear );
      output.writeObject( inDirectCost );
      output.writeObject( indirectCostRate );
      output.writeObject( indirectLabourRate );
      output.writeObject( indirectRndRate );
      output.writeObject( laborCostClimbRate );
      output.writeObject( laborCostRate );
      output.writeObject( laborCostYear );
      output.writeObject( machineDpcCost );
      output.writeObject( manufactureCodeReference );
      output.writeObject( moldFromSign );
      output.writeObject( moldFromVal );
      output.writeObject( moldMaintenance );
      output.writeObject( moldToSign );
      output.writeObject( moldToVal );
      output.writeObject( mtrManageRate );
      output.writeObject( owner );
      output.writeObject( productLossRate );
      output.writeObject( quantityMaxFromSign );
      output.writeObject( quantityMaxFromVal );
      output.writeObject( quantityMaxToSign );
      output.writeObject( quantityMaxToVal );
      output.writeObject( rnd );
      output.writeObject( sortLocation );
      output.writeObject( tabaryu );
      output.writeObject( thePersistInfo );
      output.writeObject( tonFromSign );
      output.writeObject( tonFromVal );
      output.writeObject( tonToSign );
      output.writeObject( tonToVal );
      output.writeObject( unitManage );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostPartTypeCodeMaster) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "addManHourRate", addManHourRate );
      output.setString( "assyLossRate", assyLossRate );
      output.setString( "buyBackIndirectCostRate", buyBackIndirectCostRate );
      output.setString( "coManageCost", coManageCost );
      output.setString( "coManageRate", coManageRate );
      output.writeObject( "costpartTypeReference", costpartTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "efficientRate", efficientRate );
      output.setString( "elecCost", elecCost );
      output.setString( "elecCostClimbRate", elecCostClimbRate );
      output.setString( "elecCostYear", elecCostYear );
      output.setString( "inDirectCost", inDirectCost );
      output.setString( "indirectCostRate", indirectCostRate );
      output.setString( "indirectLabourRate", indirectLabourRate );
      output.setString( "indirectRndRate", indirectRndRate );
      output.setString( "laborCostClimbRate", laborCostClimbRate );
      output.setString( "laborCostRate", laborCostRate );
      output.setString( "laborCostYear", laborCostYear );
      output.setString( "machineDpcCost", machineDpcCost );
      output.writeObject( "manufactureCodeReference", manufactureCodeReference, wt.fc.ObjectReference.class, true );
      output.setString( "moldFromSign", moldFromSign );
      output.setString( "moldFromVal", moldFromVal );
      output.setString( "moldMaintenance", moldMaintenance );
      output.setString( "moldToSign", moldToSign );
      output.setString( "moldToVal", moldToVal );
      output.setString( "mtrManageRate", mtrManageRate );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "productLossRate", productLossRate );
      output.setString( "quantityMaxFromSign", quantityMaxFromSign );
      output.setString( "quantityMaxFromVal", quantityMaxFromVal );
      output.setString( "quantityMaxToSign", quantityMaxToSign );
      output.setString( "quantityMaxToVal", quantityMaxToVal );
      output.setString( "rnd", rnd );
      output.setIntObject( "sortLocation", sortLocation );
      output.setString( "tabaryu", tabaryu );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "tonFromSign", tonFromSign );
      output.setString( "tonFromVal", tonFromVal );
      output.setString( "tonToSign", tonToSign );
      output.setString( "tonToVal", tonToVal );
      output.setString( "unitManage", unitManage );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      addManHourRate = input.getString( "addManHourRate" );
      assyLossRate = input.getString( "assyLossRate" );
      buyBackIndirectCostRate = input.getString( "buyBackIndirectCostRate" );
      coManageCost = input.getString( "coManageCost" );
      coManageRate = input.getString( "coManageRate" );
      costpartTypeReference = (wt.fc.ObjectReference) input.readObject( "costpartTypeReference", costpartTypeReference, wt.fc.ObjectReference.class, true );
      efficientRate = input.getString( "efficientRate" );
      elecCost = input.getString( "elecCost" );
      elecCostClimbRate = input.getString( "elecCostClimbRate" );
      elecCostYear = input.getString( "elecCostYear" );
      inDirectCost = input.getString( "inDirectCost" );
      indirectCostRate = input.getString( "indirectCostRate" );
      indirectLabourRate = input.getString( "indirectLabourRate" );
      indirectRndRate = input.getString( "indirectRndRate" );
      laborCostClimbRate = input.getString( "laborCostClimbRate" );
      laborCostRate = input.getString( "laborCostRate" );
      laborCostYear = input.getString( "laborCostYear" );
      machineDpcCost = input.getString( "machineDpcCost" );
      manufactureCodeReference = (wt.fc.ObjectReference) input.readObject( "manufactureCodeReference", manufactureCodeReference, wt.fc.ObjectReference.class, true );
      moldFromSign = input.getString( "moldFromSign" );
      moldFromVal = input.getString( "moldFromVal" );
      moldMaintenance = input.getString( "moldMaintenance" );
      moldToSign = input.getString( "moldToSign" );
      moldToVal = input.getString( "moldToVal" );
      mtrManageRate = input.getString( "mtrManageRate" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      productLossRate = input.getString( "productLossRate" );
      quantityMaxFromSign = input.getString( "quantityMaxFromSign" );
      quantityMaxFromVal = input.getString( "quantityMaxFromVal" );
      quantityMaxToSign = input.getString( "quantityMaxToSign" );
      quantityMaxToVal = input.getString( "quantityMaxToVal" );
      rnd = input.getString( "rnd" );
      sortLocation = input.getIntObject( "sortLocation" );
      tabaryu = input.getString( "tabaryu" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      tonFromSign = input.getString( "tonFromSign" );
      tonFromVal = input.getString( "tonFromVal" );
      tonToSign = input.getString( "tonToSign" );
      tonToVal = input.getString( "tonToVal" );
      unitManage = input.getString( "unitManage" );
   }

   boolean readVersion_4333708198654443716L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      addManHourRate = (java.lang.String) input.readObject();
      assyLossRate = (java.lang.String) input.readObject();
      buyBackIndirectCostRate = (java.lang.String) input.readObject();
      coManageCost = (java.lang.String) input.readObject();
      coManageRate = (java.lang.String) input.readObject();
      costpartTypeReference = (wt.fc.ObjectReference) input.readObject();
      efficientRate = (java.lang.String) input.readObject();
      elecCost = (java.lang.String) input.readObject();
      elecCostClimbRate = (java.lang.String) input.readObject();
      elecCostYear = (java.lang.String) input.readObject();
      inDirectCost = (java.lang.String) input.readObject();
      indirectCostRate = (java.lang.String) input.readObject();
      indirectLabourRate = (java.lang.String) input.readObject();
      indirectRndRate = (java.lang.String) input.readObject();
      laborCostClimbRate = (java.lang.String) input.readObject();
      laborCostRate = (java.lang.String) input.readObject();
      laborCostYear = (java.lang.String) input.readObject();
      machineDpcCost = (java.lang.String) input.readObject();
      manufactureCodeReference = (wt.fc.ObjectReference) input.readObject();
      moldFromSign = (java.lang.String) input.readObject();
      moldFromVal = (java.lang.String) input.readObject();
      moldMaintenance = (java.lang.String) input.readObject();
      moldToSign = (java.lang.String) input.readObject();
      moldToVal = (java.lang.String) input.readObject();
      mtrManageRate = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      productLossRate = (java.lang.String) input.readObject();
      quantityMaxFromSign = (java.lang.String) input.readObject();
      quantityMaxFromVal = (java.lang.String) input.readObject();
      quantityMaxToSign = (java.lang.String) input.readObject();
      quantityMaxToVal = (java.lang.String) input.readObject();
      rnd = (java.lang.String) input.readObject();
      sortLocation = (java.lang.Integer) input.readObject();
      tabaryu = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      tonFromSign = (java.lang.String) input.readObject();
      tonFromVal = (java.lang.String) input.readObject();
      tonToSign = (java.lang.String) input.readObject();
      tonToVal = (java.lang.String) input.readObject();
      unitManage = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostPartTypeCodeMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4333708198654443716L( input, readSerialVersionUID, superDone );
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
