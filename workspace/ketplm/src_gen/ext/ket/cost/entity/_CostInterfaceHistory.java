package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostInterfaceHistory implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostInterfaceHistory.class.getName();

   /**
    * 프로젝트번호
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * 프로젝트번호
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * 프로젝트번호
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
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
    * 제품,검토
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PJT_TYPE = "pjtType";
   static int PJT_TYPE_UPPER_LIMIT = -1;
   java.lang.String pjtType;
   /**
    * 제품,검토
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getPjtType() {
      return pjtType;
   }
   /**
    * 제품,검토
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setPjtType(java.lang.String pjtType) throws wt.util.WTPropertyVetoException {
      pjtTypeValidate(pjtType);
      this.pjtType = pjtType;
   }
   void pjtTypeValidate(java.lang.String pjtType) throws wt.util.WTPropertyVetoException {
      if (PJT_TYPE_UPPER_LIMIT < 1) {
         try { PJT_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_TYPE_UPPER_LIMIT = 200; }
      }
      if (pjtType != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtType.toString(), PJT_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtType"), java.lang.String.valueOf(java.lang.Math.min(PJT_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtType", this.pjtType, pjtType));
   }

   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String DR_STEP = "drStep";
   static int DR_STEP_UPPER_LIMIT = -1;
   java.lang.String drStep;
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getDrStep() {
      return drStep;
   }
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setDrStep(java.lang.String drStep) throws wt.util.WTPropertyVetoException {
      drStepValidate(drStep);
      this.drStep = drStep;
   }
   void drStepValidate(java.lang.String drStep) throws wt.util.WTPropertyVetoException {
      if (DR_STEP_UPPER_LIMIT < 1) {
         try { DR_STEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("drStep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DR_STEP_UPPER_LIMIT = 200; }
      }
      if (drStep != null && !wt.fc.PersistenceHelper.checkStoredLength(drStep.toString(), DR_STEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "drStep"), java.lang.String.valueOf(java.lang.Math.min(DR_STEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "drStep", this.drStep, drStep));
   }

   /**
    * 실제품번
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String REAL_PART_NO = "realPartNo";
   static int REAL_PART_NO_UPPER_LIMIT = -1;
   java.lang.String realPartNo;
   /**
    * 실제품번
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getRealPartNo() {
      return realPartNo;
   }
   /**
    * 실제품번
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setRealPartNo(java.lang.String realPartNo) throws wt.util.WTPropertyVetoException {
      realPartNoValidate(realPartNo);
      this.realPartNo = realPartNo;
   }
   void realPartNoValidate(java.lang.String realPartNo) throws wt.util.WTPropertyVetoException {
      if (REAL_PART_NO_UPPER_LIMIT < 1) {
         try { REAL_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("realPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REAL_PART_NO_UPPER_LIMIT = 200; }
      }
      if (realPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(realPartNo.toString(), REAL_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "realPartNo"), java.lang.String.valueOf(java.lang.Math.min(REAL_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "realPartNo", this.realPartNo, realPartNo));
   }

   /**
    * 가품번
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 가품번
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 가품번
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
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
    * version + subVersion
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String VERSION = "version";
   static int VERSION_UPPER_LIMIT = -1;
   java.lang.String version;
   /**
    * version + subVersion
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getVersion() {
      return version;
   }
   /**
    * version + subVersion
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setVersion(java.lang.String version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.String version) throws wt.util.WTPropertyVetoException {
      if (VERSION_UPPER_LIMIT < 1) {
         try { VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("version").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VERSION_UPPER_LIMIT = 200; }
      }
      if (version != null && !wt.fc.PersistenceHelper.checkStoredLength(version.toString(), VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "version"), java.lang.String.valueOf(java.lang.Math.min(VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "version", this.version, version));
   }

   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String MATERIAL_COST = "materialCost";
   static int MATERIAL_COST_UPPER_LIMIT = -1;
   java.lang.String materialCost = "0";
   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getMaterialCost() {
      return materialCost;
   }
   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setMaterialCost(java.lang.String materialCost) throws wt.util.WTPropertyVetoException {
      materialCostValidate(materialCost);
      this.materialCost = materialCost;
   }
   void materialCostValidate(java.lang.String materialCost) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_COST_UPPER_LIMIT < 1) {
         try { MATERIAL_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("materialCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_COST_UPPER_LIMIT = 200; }
      }
      if (materialCost != null && !wt.fc.PersistenceHelper.checkStoredLength(materialCost.toString(), MATERIAL_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialCost"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "materialCost", this.materialCost, materialCost));
   }

   /**
    * 직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String LABOR_COST = "laborCost";
   static int LABOR_COST_UPPER_LIMIT = -1;
   java.lang.String laborCost = "0";
   /**
    * 직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getLaborCost() {
      return laborCost;
   }
   /**
    * 직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setLaborCost(java.lang.String laborCost) throws wt.util.WTPropertyVetoException {
      laborCostValidate(laborCost);
      this.laborCost = laborCost;
   }
   void laborCostValidate(java.lang.String laborCost) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_UPPER_LIMIT < 1) {
         try { LABOR_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_UPPER_LIMIT = 200; }
      }
      if (laborCost != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCost.toString(), LABOR_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCost"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCost", this.laborCost, laborCost));
   }

   /**
    * 간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String IN_DIRECT_LABOR_COST = "inDirectLaborCost";
   static int IN_DIRECT_LABOR_COST_UPPER_LIMIT = -1;
   java.lang.String inDirectLaborCost = "0";
   /**
    * 간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getInDirectLaborCost() {
      return inDirectLaborCost;
   }
   /**
    * 간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setInDirectLaborCost(java.lang.String inDirectLaborCost) throws wt.util.WTPropertyVetoException {
      inDirectLaborCostValidate(inDirectLaborCost);
      this.inDirectLaborCost = inDirectLaborCost;
   }
   void inDirectLaborCostValidate(java.lang.String inDirectLaborCost) throws wt.util.WTPropertyVetoException {
      if (IN_DIRECT_LABOR_COST_UPPER_LIMIT < 1) {
         try { IN_DIRECT_LABOR_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inDirectLaborCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_DIRECT_LABOR_COST_UPPER_LIMIT = 200; }
      }
      if (inDirectLaborCost != null && !wt.fc.PersistenceHelper.checkStoredLength(inDirectLaborCost.toString(), IN_DIRECT_LABOR_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inDirectLaborCost"), java.lang.String.valueOf(java.lang.Math.min(IN_DIRECT_LABOR_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inDirectLaborCost", this.inDirectLaborCost, inDirectLaborCost));
   }

   /**
    * 설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String FAC_REDUCE_PRICE = "facReducePrice";
   static int FAC_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String facReducePrice = "0";
   /**
    * 설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getFacReducePrice() {
      return facReducePrice;
   }
   /**
    * 설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setFacReducePrice(java.lang.String facReducePrice) throws wt.util.WTPropertyVetoException {
      facReducePriceValidate(facReducePrice);
      this.facReducePrice = facReducePrice;
   }
   void facReducePriceValidate(java.lang.String facReducePrice) throws wt.util.WTPropertyVetoException {
      if (FAC_REDUCE_PRICE_UPPER_LIMIT < 1) {
         try { FAC_REDUCE_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facReducePrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_REDUCE_PRICE_UPPER_LIMIT = 200; }
      }
      if (facReducePrice != null && !wt.fc.PersistenceHelper.checkStoredLength(facReducePrice.toString(), FAC_REDUCE_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facReducePrice"), java.lang.String.valueOf(java.lang.Math.min(FAC_REDUCE_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facReducePrice", this.facReducePrice, facReducePrice));
   }

   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String DIRECT_COST = "directCost";
   static int DIRECT_COST_UPPER_LIMIT = -1;
   java.lang.String directCost = "0";
   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getDirectCost() {
      return directCost;
   }
   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setDirectCost(java.lang.String directCost) throws wt.util.WTPropertyVetoException {
      directCostValidate(directCost);
      this.directCost = directCost;
   }
   void directCostValidate(java.lang.String directCost) throws wt.util.WTPropertyVetoException {
      if (DIRECT_COST_UPPER_LIMIT < 1) {
         try { DIRECT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("directCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIRECT_COST_UPPER_LIMIT = 200; }
      }
      if (directCost != null && !wt.fc.PersistenceHelper.checkStoredLength(directCost.toString(), DIRECT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "directCost"), java.lang.String.valueOf(java.lang.Math.min(DIRECT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "directCost", this.directCost, directCost));
   }

   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String IN_DIRECT_COST = "inDirectCost";
   static int IN_DIRECT_COST_UPPER_LIMIT = -1;
   java.lang.String inDirectCost = "0";
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getInDirectCost() {
      return inDirectCost;
   }
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
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
    * 금형감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String MOLD_REDUCE_PRICE = "moldReducePrice";
   static int MOLD_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String moldReducePrice = "0";
   /**
    * 금형감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getMoldReducePrice() {
      return moldReducePrice;
   }
   /**
    * 금형감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setMoldReducePrice(java.lang.String moldReducePrice) throws wt.util.WTPropertyVetoException {
      moldReducePriceValidate(moldReducePrice);
      this.moldReducePrice = moldReducePrice;
   }
   void moldReducePriceValidate(java.lang.String moldReducePrice) throws wt.util.WTPropertyVetoException {
      if (MOLD_REDUCE_PRICE_UPPER_LIMIT < 1) {
         try { MOLD_REDUCE_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldReducePrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_REDUCE_PRICE_UPPER_LIMIT = 200; }
      }
      if (moldReducePrice != null && !wt.fc.PersistenceHelper.checkStoredLength(moldReducePrice.toString(), MOLD_REDUCE_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldReducePrice"), java.lang.String.valueOf(java.lang.Math.min(MOLD_REDUCE_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldReducePrice", this.moldReducePrice, moldReducePrice));
   }

   /**
    * 외주가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String OUT_UNIT_COST = "outUnitCost";
   static int OUT_UNIT_COST_UPPER_LIMIT = -1;
   java.lang.String outUnitCost = "0";
   /**
    * 외주가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getOutUnitCost() {
      return outUnitCost;
   }
   /**
    * 외주가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setOutUnitCost(java.lang.String outUnitCost) throws wt.util.WTPropertyVetoException {
      outUnitCostValidate(outUnitCost);
      this.outUnitCost = outUnitCost;
   }
   void outUnitCostValidate(java.lang.String outUnitCost) throws wt.util.WTPropertyVetoException {
      if (OUT_UNIT_COST_UPPER_LIMIT < 1) {
         try { OUT_UNIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outUnitCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_UNIT_COST_UPPER_LIMIT = 200; }
      }
      if (outUnitCost != null && !wt.fc.PersistenceHelper.checkStoredLength(outUnitCost.toString(), OUT_UNIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outUnitCost"), java.lang.String.valueOf(java.lang.Math.min(OUT_UNIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outUnitCost", this.outUnitCost, outUnitCost));
   }

   /**
    * 기타원가
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String ETC_COST = "etcCost";
   static int ETC_COST_UPPER_LIMIT = -1;
   java.lang.String etcCost = "0";
   /**
    * 기타원가
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getEtcCost() {
      return etcCost;
   }
   /**
    * 기타원가
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setEtcCost(java.lang.String etcCost) throws wt.util.WTPropertyVetoException {
      etcCostValidate(etcCost);
      this.etcCost = etcCost;
   }
   void etcCostValidate(java.lang.String etcCost) throws wt.util.WTPropertyVetoException {
      if (ETC_COST_UPPER_LIMIT < 1) {
         try { ETC_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_COST_UPPER_LIMIT = 200; }
      }
      if (etcCost != null && !wt.fc.PersistenceHelper.checkStoredLength(etcCost.toString(), ETC_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcCost"), java.lang.String.valueOf(java.lang.Math.min(ETC_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcCost", this.etcCost, etcCost));
   }

   /**
    * 판관비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_MANAGE_COST = "salesManageCost";
   static int SALES_MANAGE_COST_UPPER_LIMIT = -1;
   java.lang.String salesManageCost = "0";
   /**
    * 판관비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesManageCost() {
      return salesManageCost;
   }
   /**
    * 판관비
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesManageCost(java.lang.String salesManageCost) throws wt.util.WTPropertyVetoException {
      salesManageCostValidate(salesManageCost);
      this.salesManageCost = salesManageCost;
   }
   void salesManageCostValidate(java.lang.String salesManageCost) throws wt.util.WTPropertyVetoException {
      if (SALES_MANAGE_COST_UPPER_LIMIT < 1) {
         try { SALES_MANAGE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesManageCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_MANAGE_COST_UPPER_LIMIT = 200; }
      }
      if (salesManageCost != null && !wt.fc.PersistenceHelper.checkStoredLength(salesManageCost.toString(), SALES_MANAGE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesManageCost"), java.lang.String.valueOf(java.lang.Math.min(SALES_MANAGE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesManageCost", this.salesManageCost, salesManageCost));
   }

   /**
    * 스크랩매출
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SCRAP_SALES_COST = "scrapSalesCost";
   static int SCRAP_SALES_COST_UPPER_LIMIT = -1;
   java.lang.String scrapSalesCost = "0";
   /**
    * 스크랩매출
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getScrapSalesCost() {
      return scrapSalesCost;
   }
   /**
    * 스크랩매출
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setScrapSalesCost(java.lang.String scrapSalesCost) throws wt.util.WTPropertyVetoException {
      scrapSalesCostValidate(scrapSalesCost);
      this.scrapSalesCost = scrapSalesCost;
   }
   void scrapSalesCostValidate(java.lang.String scrapSalesCost) throws wt.util.WTPropertyVetoException {
      if (SCRAP_SALES_COST_UPPER_LIMIT < 1) {
         try { SCRAP_SALES_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapSalesCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_SALES_COST_UPPER_LIMIT = 200; }
      }
      if (scrapSalesCost != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapSalesCost.toString(), SCRAP_SALES_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapSalesCost"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_SALES_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapSalesCost", this.scrapSalesCost, scrapSalesCost));
   }

   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_TARGET_COST_TOTAL = "salesTargetCostTotal";
   static int SALES_TARGET_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String salesTargetCostTotal;
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesTargetCostTotal() {
      return salesTargetCostTotal;
   }
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
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
    * 총원가(컨버팅)
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PRODUCT_COST_TOTAL = "productCostTotal";
   static int PRODUCT_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String productCostTotal;
   /**
    * 총원가(컨버팅)
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getProductCostTotal() {
      return productCostTotal;
   }
   /**
    * 총원가(컨버팅)
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
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
    * 총원가(PLM)
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String ORG_PRODUCT_COST_TOTAL = "orgProductCostTotal";
   static int ORG_PRODUCT_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String orgProductCostTotal;
   /**
    * 총원가(PLM)
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getOrgProductCostTotal() {
      return orgProductCostTotal;
   }
   /**
    * 총원가(PLM)
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setOrgProductCostTotal(java.lang.String orgProductCostTotal) throws wt.util.WTPropertyVetoException {
      orgProductCostTotalValidate(orgProductCostTotal);
      this.orgProductCostTotal = orgProductCostTotal;
   }
   void orgProductCostTotalValidate(java.lang.String orgProductCostTotal) throws wt.util.WTPropertyVetoException {
      if (ORG_PRODUCT_COST_TOTAL_UPPER_LIMIT < 1) {
         try { ORG_PRODUCT_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("orgProductCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ORG_PRODUCT_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (orgProductCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(orgProductCostTotal.toString(), ORG_PRODUCT_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "orgProductCostTotal"), java.lang.String.valueOf(java.lang.Math.min(ORG_PRODUCT_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "orgProductCostTotal", this.orgProductCostTotal, orgProductCostTotal));
   }

   /**
    * 원가계산일
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String COST_CALC_DATE = "costCalcDate";
   static int COST_CALC_DATE_UPPER_LIMIT = -1;
   java.lang.String costCalcDate;
   /**
    * 원가계산일
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getCostCalcDate() {
      return costCalcDate;
   }
   /**
    * 원가계산일
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setCostCalcDate(java.lang.String costCalcDate) throws wt.util.WTPropertyVetoException {
      costCalcDateValidate(costCalcDate);
      this.costCalcDate = costCalcDate;
   }
   void costCalcDateValidate(java.lang.String costCalcDate) throws wt.util.WTPropertyVetoException {
      if (COST_CALC_DATE_UPPER_LIMIT < 1) {
         try { COST_CALC_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costCalcDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_CALC_DATE_UPPER_LIMIT = 200; }
      }
      if (costCalcDate != null && !wt.fc.PersistenceHelper.checkStoredLength(costCalcDate.toString(), COST_CALC_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costCalcDate"), java.lang.String.valueOf(java.lang.Math.min(COST_CALC_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costCalcDate", this.costCalcDate, costCalcDate));
   }

   /**
    * SOP시작년도
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SOP_SYEAR = "sopSyear";
   static int SOP_SYEAR_UPPER_LIMIT = -1;
   java.lang.String sopSyear;
   /**
    * SOP시작년도
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSopSyear() {
      return sopSyear;
   }
   /**
    * SOP시작년도
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSopSyear(java.lang.String sopSyear) throws wt.util.WTPropertyVetoException {
      sopSyearValidate(sopSyear);
      this.sopSyear = sopSyear;
   }
   void sopSyearValidate(java.lang.String sopSyear) throws wt.util.WTPropertyVetoException {
      if (SOP_SYEAR_UPPER_LIMIT < 1) {
         try { SOP_SYEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sopSyear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SOP_SYEAR_UPPER_LIMIT = 200; }
      }
      if (sopSyear != null && !wt.fc.PersistenceHelper.checkStoredLength(sopSyear.toString(), SOP_SYEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sopSyear"), java.lang.String.valueOf(java.lang.Math.min(SOP_SYEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sopSyear", this.sopSyear, sopSyear));
   }

   /**
    * SOP종료년도
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SOP_EYEAR = "sopEyear";
   static int SOP_EYEAR_UPPER_LIMIT = -1;
   java.lang.String sopEyear;
   /**
    * SOP종료년도
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSopEyear() {
      return sopEyear;
   }
   /**
    * SOP종료년도
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSopEyear(java.lang.String sopEyear) throws wt.util.WTPropertyVetoException {
      sopEyearValidate(sopEyear);
      this.sopEyear = sopEyear;
   }
   void sopEyearValidate(java.lang.String sopEyear) throws wt.util.WTPropertyVetoException {
      if (SOP_EYEAR_UPPER_LIMIT < 1) {
         try { SOP_EYEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sopEyear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SOP_EYEAR_UPPER_LIMIT = 200; }
      }
      if (sopEyear != null && !wt.fc.PersistenceHelper.checkStoredLength(sopEyear.toString(), SOP_EYEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sopEyear"), java.lang.String.valueOf(java.lang.Math.min(SOP_EYEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sopEyear", this.sopEyear, sopEyear));
   }

   /**
    * 월평균판매수량1
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY1 = "salesQty1";
   static int SALES_QTY1_UPPER_LIMIT = -1;
   java.lang.String salesQty1;
   /**
    * 월평균판매수량1
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty1() {
      return salesQty1;
   }
   /**
    * 월평균판매수량1
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty1(java.lang.String salesQty1) throws wt.util.WTPropertyVetoException {
      salesQty1Validate(salesQty1);
      this.salesQty1 = salesQty1;
   }
   void salesQty1Validate(java.lang.String salesQty1) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY1_UPPER_LIMIT < 1) {
         try { SALES_QTY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY1_UPPER_LIMIT = 200; }
      }
      if (salesQty1 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty1.toString(), SALES_QTY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty1"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty1", this.salesQty1, salesQty1));
   }

   /**
    * 월평균판매수량2
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY2 = "salesQty2";
   static int SALES_QTY2_UPPER_LIMIT = -1;
   java.lang.String salesQty2;
   /**
    * 월평균판매수량2
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty2() {
      return salesQty2;
   }
   /**
    * 월평균판매수량2
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty2(java.lang.String salesQty2) throws wt.util.WTPropertyVetoException {
      salesQty2Validate(salesQty2);
      this.salesQty2 = salesQty2;
   }
   void salesQty2Validate(java.lang.String salesQty2) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY2_UPPER_LIMIT < 1) {
         try { SALES_QTY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY2_UPPER_LIMIT = 200; }
      }
      if (salesQty2 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty2.toString(), SALES_QTY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty2"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty2", this.salesQty2, salesQty2));
   }

   /**
    * 월평균판매수량3
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY3 = "salesQty3";
   static int SALES_QTY3_UPPER_LIMIT = -1;
   java.lang.String salesQty3;
   /**
    * 월평균판매수량3
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty3() {
      return salesQty3;
   }
   /**
    * 월평균판매수량3
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty3(java.lang.String salesQty3) throws wt.util.WTPropertyVetoException {
      salesQty3Validate(salesQty3);
      this.salesQty3 = salesQty3;
   }
   void salesQty3Validate(java.lang.String salesQty3) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY3_UPPER_LIMIT < 1) {
         try { SALES_QTY3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY3_UPPER_LIMIT = 200; }
      }
      if (salesQty3 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty3.toString(), SALES_QTY3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty3"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty3", this.salesQty3, salesQty3));
   }

   /**
    * 월평균판매수량4
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY4 = "salesQty4";
   static int SALES_QTY4_UPPER_LIMIT = -1;
   java.lang.String salesQty4;
   /**
    * 월평균판매수량4
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty4() {
      return salesQty4;
   }
   /**
    * 월평균판매수량4
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty4(java.lang.String salesQty4) throws wt.util.WTPropertyVetoException {
      salesQty4Validate(salesQty4);
      this.salesQty4 = salesQty4;
   }
   void salesQty4Validate(java.lang.String salesQty4) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY4_UPPER_LIMIT < 1) {
         try { SALES_QTY4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY4_UPPER_LIMIT = 200; }
      }
      if (salesQty4 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty4.toString(), SALES_QTY4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty4"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty4", this.salesQty4, salesQty4));
   }

   /**
    * 월평균판매수량5
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY5 = "salesQty5";
   static int SALES_QTY5_UPPER_LIMIT = -1;
   java.lang.String salesQty5;
   /**
    * 월평균판매수량5
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty5() {
      return salesQty5;
   }
   /**
    * 월평균판매수량5
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty5(java.lang.String salesQty5) throws wt.util.WTPropertyVetoException {
      salesQty5Validate(salesQty5);
      this.salesQty5 = salesQty5;
   }
   void salesQty5Validate(java.lang.String salesQty5) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY5_UPPER_LIMIT < 1) {
         try { SALES_QTY5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY5_UPPER_LIMIT = 200; }
      }
      if (salesQty5 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty5.toString(), SALES_QTY5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty5"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty5", this.salesQty5, salesQty5));
   }

   /**
    * 월평균판매수량6
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY6 = "salesQty6";
   static int SALES_QTY6_UPPER_LIMIT = -1;
   java.lang.String salesQty6;
   /**
    * 월평균판매수량6
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty6() {
      return salesQty6;
   }
   /**
    * 월평균판매수량6
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty6(java.lang.String salesQty6) throws wt.util.WTPropertyVetoException {
      salesQty6Validate(salesQty6);
      this.salesQty6 = salesQty6;
   }
   void salesQty6Validate(java.lang.String salesQty6) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY6_UPPER_LIMIT < 1) {
         try { SALES_QTY6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY6_UPPER_LIMIT = 200; }
      }
      if (salesQty6 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty6.toString(), SALES_QTY6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty6"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty6", this.salesQty6, salesQty6));
   }

   /**
    * 월평균판매수량7
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY7 = "salesQty7";
   static int SALES_QTY7_UPPER_LIMIT = -1;
   java.lang.String salesQty7;
   /**
    * 월평균판매수량7
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty7() {
      return salesQty7;
   }
   /**
    * 월평균판매수량7
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty7(java.lang.String salesQty7) throws wt.util.WTPropertyVetoException {
      salesQty7Validate(salesQty7);
      this.salesQty7 = salesQty7;
   }
   void salesQty7Validate(java.lang.String salesQty7) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY7_UPPER_LIMIT < 1) {
         try { SALES_QTY7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY7_UPPER_LIMIT = 200; }
      }
      if (salesQty7 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty7.toString(), SALES_QTY7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty7"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty7", this.salesQty7, salesQty7));
   }

   /**
    * 월평균판매수량8
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY8 = "salesQty8";
   static int SALES_QTY8_UPPER_LIMIT = -1;
   java.lang.String salesQty8;
   /**
    * 월평균판매수량8
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty8() {
      return salesQty8;
   }
   /**
    * 월평균판매수량8
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty8(java.lang.String salesQty8) throws wt.util.WTPropertyVetoException {
      salesQty8Validate(salesQty8);
      this.salesQty8 = salesQty8;
   }
   void salesQty8Validate(java.lang.String salesQty8) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY8_UPPER_LIMIT < 1) {
         try { SALES_QTY8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY8_UPPER_LIMIT = 200; }
      }
      if (salesQty8 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty8.toString(), SALES_QTY8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty8"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty8", this.salesQty8, salesQty8));
   }

   /**
    * 월평균판매수량9
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY9 = "salesQty9";
   static int SALES_QTY9_UPPER_LIMIT = -1;
   java.lang.String salesQty9;
   /**
    * 월평균판매수량9
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty9() {
      return salesQty9;
   }
   /**
    * 월평균판매수량9
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty9(java.lang.String salesQty9) throws wt.util.WTPropertyVetoException {
      salesQty9Validate(salesQty9);
      this.salesQty9 = salesQty9;
   }
   void salesQty9Validate(java.lang.String salesQty9) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY9_UPPER_LIMIT < 1) {
         try { SALES_QTY9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY9_UPPER_LIMIT = 200; }
      }
      if (salesQty9 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty9.toString(), SALES_QTY9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty9"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty9", this.salesQty9, salesQty9));
   }

   /**
    * 월평균판매수량10
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY10 = "salesQty10";
   static int SALES_QTY10_UPPER_LIMIT = -1;
   java.lang.String salesQty10;
   /**
    * 월평균판매수량10
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty10() {
      return salesQty10;
   }
   /**
    * 월평균판매수량10
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty10(java.lang.String salesQty10) throws wt.util.WTPropertyVetoException {
      salesQty10Validate(salesQty10);
      this.salesQty10 = salesQty10;
   }
   void salesQty10Validate(java.lang.String salesQty10) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY10_UPPER_LIMIT < 1) {
         try { SALES_QTY10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY10_UPPER_LIMIT = 200; }
      }
      if (salesQty10 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty10.toString(), SALES_QTY10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty10"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty10", this.salesQty10, salesQty10));
   }

   /**
    * 월평균판매수량11
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY11 = "salesQty11";
   static int SALES_QTY11_UPPER_LIMIT = -1;
   java.lang.String salesQty11;
   /**
    * 월평균판매수량11
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty11() {
      return salesQty11;
   }
   /**
    * 월평균판매수량11
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty11(java.lang.String salesQty11) throws wt.util.WTPropertyVetoException {
      salesQty11Validate(salesQty11);
      this.salesQty11 = salesQty11;
   }
   void salesQty11Validate(java.lang.String salesQty11) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY11_UPPER_LIMIT < 1) {
         try { SALES_QTY11_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty11").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY11_UPPER_LIMIT = 200; }
      }
      if (salesQty11 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty11.toString(), SALES_QTY11_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty11"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY11_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty11", this.salesQty11, salesQty11));
   }

   /**
    * 월평균판매수량12
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY12 = "salesQty12";
   static int SALES_QTY12_UPPER_LIMIT = -1;
   java.lang.String salesQty12;
   /**
    * 월평균판매수량12
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty12() {
      return salesQty12;
   }
   /**
    * 월평균판매수량12
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty12(java.lang.String salesQty12) throws wt.util.WTPropertyVetoException {
      salesQty12Validate(salesQty12);
      this.salesQty12 = salesQty12;
   }
   void salesQty12Validate(java.lang.String salesQty12) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY12_UPPER_LIMIT < 1) {
         try { SALES_QTY12_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty12").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY12_UPPER_LIMIT = 200; }
      }
      if (salesQty12 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty12.toString(), SALES_QTY12_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty12"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY12_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty12", this.salesQty12, salesQty12));
   }

   /**
    * 월평균판매수량13
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY13 = "salesQty13";
   static int SALES_QTY13_UPPER_LIMIT = -1;
   java.lang.String salesQty13;
   /**
    * 월평균판매수량13
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty13() {
      return salesQty13;
   }
   /**
    * 월평균판매수량13
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty13(java.lang.String salesQty13) throws wt.util.WTPropertyVetoException {
      salesQty13Validate(salesQty13);
      this.salesQty13 = salesQty13;
   }
   void salesQty13Validate(java.lang.String salesQty13) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY13_UPPER_LIMIT < 1) {
         try { SALES_QTY13_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty13").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY13_UPPER_LIMIT = 200; }
      }
      if (salesQty13 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty13.toString(), SALES_QTY13_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty13"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY13_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty13", this.salesQty13, salesQty13));
   }

   /**
    * 월평균판매수량14
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY14 = "salesQty14";
   static int SALES_QTY14_UPPER_LIMIT = -1;
   java.lang.String salesQty14;
   /**
    * 월평균판매수량14
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty14() {
      return salesQty14;
   }
   /**
    * 월평균판매수량14
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty14(java.lang.String salesQty14) throws wt.util.WTPropertyVetoException {
      salesQty14Validate(salesQty14);
      this.salesQty14 = salesQty14;
   }
   void salesQty14Validate(java.lang.String salesQty14) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY14_UPPER_LIMIT < 1) {
         try { SALES_QTY14_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty14").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY14_UPPER_LIMIT = 200; }
      }
      if (salesQty14 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty14.toString(), SALES_QTY14_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty14"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY14_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty14", this.salesQty14, salesQty14));
   }

   /**
    * 월평균판매수량15
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY15 = "salesQty15";
   static int SALES_QTY15_UPPER_LIMIT = -1;
   java.lang.String salesQty15;
   /**
    * 월평균판매수량15
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty15() {
      return salesQty15;
   }
   /**
    * 월평균판매수량15
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty15(java.lang.String salesQty15) throws wt.util.WTPropertyVetoException {
      salesQty15Validate(salesQty15);
      this.salesQty15 = salesQty15;
   }
   void salesQty15Validate(java.lang.String salesQty15) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY15_UPPER_LIMIT < 1) {
         try { SALES_QTY15_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty15").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY15_UPPER_LIMIT = 200; }
      }
      if (salesQty15 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty15.toString(), SALES_QTY15_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty15"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY15_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty15", this.salesQty15, salesQty15));
   }

   /**
    * 월평균판매수량16
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY16 = "salesQty16";
   static int SALES_QTY16_UPPER_LIMIT = -1;
   java.lang.String salesQty16;
   /**
    * 월평균판매수량16
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty16() {
      return salesQty16;
   }
   /**
    * 월평균판매수량16
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty16(java.lang.String salesQty16) throws wt.util.WTPropertyVetoException {
      salesQty16Validate(salesQty16);
      this.salesQty16 = salesQty16;
   }
   void salesQty16Validate(java.lang.String salesQty16) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY16_UPPER_LIMIT < 1) {
         try { SALES_QTY16_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty16").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY16_UPPER_LIMIT = 200; }
      }
      if (salesQty16 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty16.toString(), SALES_QTY16_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty16"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY16_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty16", this.salesQty16, salesQty16));
   }

   /**
    * 월평균판매수량17
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY17 = "salesQty17";
   static int SALES_QTY17_UPPER_LIMIT = -1;
   java.lang.String salesQty17;
   /**
    * 월평균판매수량17
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty17() {
      return salesQty17;
   }
   /**
    * 월평균판매수량17
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty17(java.lang.String salesQty17) throws wt.util.WTPropertyVetoException {
      salesQty17Validate(salesQty17);
      this.salesQty17 = salesQty17;
   }
   void salesQty17Validate(java.lang.String salesQty17) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY17_UPPER_LIMIT < 1) {
         try { SALES_QTY17_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty17").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY17_UPPER_LIMIT = 200; }
      }
      if (salesQty17 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty17.toString(), SALES_QTY17_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty17"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY17_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty17", this.salesQty17, salesQty17));
   }

   /**
    * 월평균판매수량18
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY18 = "salesQty18";
   static int SALES_QTY18_UPPER_LIMIT = -1;
   java.lang.String salesQty18;
   /**
    * 월평균판매수량18
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty18() {
      return salesQty18;
   }
   /**
    * 월평균판매수량18
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty18(java.lang.String salesQty18) throws wt.util.WTPropertyVetoException {
      salesQty18Validate(salesQty18);
      this.salesQty18 = salesQty18;
   }
   void salesQty18Validate(java.lang.String salesQty18) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY18_UPPER_LIMIT < 1) {
         try { SALES_QTY18_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty18").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY18_UPPER_LIMIT = 200; }
      }
      if (salesQty18 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty18.toString(), SALES_QTY18_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty18"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY18_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty18", this.salesQty18, salesQty18));
   }

   /**
    * 월평균판매수량19
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY19 = "salesQty19";
   static int SALES_QTY19_UPPER_LIMIT = -1;
   java.lang.String salesQty19;
   /**
    * 월평균판매수량19
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty19() {
      return salesQty19;
   }
   /**
    * 월평균판매수량19
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty19(java.lang.String salesQty19) throws wt.util.WTPropertyVetoException {
      salesQty19Validate(salesQty19);
      this.salesQty19 = salesQty19;
   }
   void salesQty19Validate(java.lang.String salesQty19) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY19_UPPER_LIMIT < 1) {
         try { SALES_QTY19_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty19").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY19_UPPER_LIMIT = 200; }
      }
      if (salesQty19 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty19.toString(), SALES_QTY19_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty19"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY19_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty19", this.salesQty19, salesQty19));
   }

   /**
    * 월평균판매수량20
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String SALES_QTY20 = "salesQty20";
   static int SALES_QTY20_UPPER_LIMIT = -1;
   java.lang.String salesQty20;
   /**
    * 월평균판매수량20
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getSalesQty20() {
      return salesQty20;
   }
   /**
    * 월평균판매수량20
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setSalesQty20(java.lang.String salesQty20) throws wt.util.WTPropertyVetoException {
      salesQty20Validate(salesQty20);
      this.salesQty20 = salesQty20;
   }
   void salesQty20Validate(java.lang.String salesQty20) throws wt.util.WTPropertyVetoException {
      if (SALES_QTY20_UPPER_LIMIT < 1) {
         try { SALES_QTY20_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesQty20").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_QTY20_UPPER_LIMIT = 200; }
      }
      if (salesQty20 != null && !wt.fc.PersistenceHelper.checkStoredLength(salesQty20.toString(), SALES_QTY20_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesQty20"), java.lang.String.valueOf(java.lang.Math.min(SALES_QTY20_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesQty20", this.salesQty20, salesQty20));
   }

   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String LASTEST = "lastest";
   boolean lastest = false;
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public boolean isLastest() {
      return lastest;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setLastest(boolean lastest) throws wt.util.WTPropertyVetoException {
      lastestValidate(lastest);
      this.lastest = lastest;
   }
   void lastestValidate(boolean lastest) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 자부품 생성여부
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String BOM_TREE_CHECK = "bomTreeCheck";
   boolean bomTreeCheck = false;
   /**
    * 자부품 생성여부
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public boolean isBomTreeCheck() {
      return bomTreeCheck;
   }
   /**
    * 자부품 생성여부
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setBomTreeCheck(boolean bomTreeCheck) throws wt.util.WTPropertyVetoException {
      bomTreeCheckValidate(bomTreeCheck);
      this.bomTreeCheck = bomTreeCheck;
   }
   void bomTreeCheckValidate(boolean bomTreeCheck) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ERP삭제플래그
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String DEL_FLAG = "delFlag";
   static int DEL_FLAG_UPPER_LIMIT = -1;
   java.lang.String delFlag;
   /**
    * ERP삭제플래그
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getDelFlag() {
      return delFlag;
   }
   /**
    * ERP삭제플래그
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setDelFlag(java.lang.String delFlag) throws wt.util.WTPropertyVetoException {
      delFlagValidate(delFlag);
      this.delFlag = delFlag;
   }
   void delFlagValidate(java.lang.String delFlag) throws wt.util.WTPropertyVetoException {
      if (DEL_FLAG_UPPER_LIMIT < 1) {
         try { DEL_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("delFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEL_FLAG_UPPER_LIMIT = 200; }
      }
      if (delFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(delFlag.toString(), DEL_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "delFlag"), java.lang.String.valueOf(java.lang.Math.min(DEL_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "delFlag", this.delFlag, delFlag));
   }

   /**
    * ERP전송일
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String TRANSFER_DATE = "transferDate";
   java.sql.Timestamp transferDate;
   /**
    * ERP전송일
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.sql.Timestamp getTransferDate() {
      return transferDate;
   }
   /**
    * ERP전송일
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setTransferDate(java.sql.Timestamp transferDate) throws wt.util.WTPropertyVetoException {
      transferDateValidate(transferDate);
      this.transferDate = transferDate;
   }
   void transferDateValidate(java.sql.Timestamp transferDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ERP전송여부
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String TRANSFER_FLAG = "transferFlag";
   static int TRANSFER_FLAG_UPPER_LIMIT = -1;
   java.lang.String transferFlag;
   /**
    * ERP전송여부
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getTransferFlag() {
      return transferFlag;
   }
   /**
    * ERP전송여부
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setTransferFlag(java.lang.String transferFlag) throws wt.util.WTPropertyVetoException {
      transferFlagValidate(transferFlag);
      this.transferFlag = transferFlag;
   }
   void transferFlagValidate(java.lang.String transferFlag) throws wt.util.WTPropertyVetoException {
      if (TRANSFER_FLAG_UPPER_LIMIT < 1) {
         try { TRANSFER_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("transferFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRANSFER_FLAG_UPPER_LIMIT = 200; }
      }
      if (transferFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(transferFlag.toString(), TRANSFER_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "transferFlag"), java.lang.String.valueOf(java.lang.Math.min(TRANSFER_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "transferFlag", this.transferFlag, transferFlag));
   }

   /**
    * ERP전송로그
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String TRANSFER_MSG = "transferMsg";
   static int TRANSFER_MSG_UPPER_LIMIT = -1;
   java.lang.String transferMsg;
   /**
    * ERP전송로그
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public java.lang.String getTransferMsg() {
      return transferMsg;
   }
   /**
    * ERP전송로그
    *
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setTransferMsg(java.lang.String transferMsg) throws wt.util.WTPropertyVetoException {
      transferMsgValidate(transferMsg);
      this.transferMsg = transferMsg;
   }
   void transferMsgValidate(java.lang.String transferMsg) throws wt.util.WTPropertyVetoException {
      if (TRANSFER_MSG_UPPER_LIMIT < 1) {
         try { TRANSFER_MSG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("transferMsg").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRANSFER_MSG_UPPER_LIMIT = 4000; }
      }
      if (transferMsg != null && !wt.fc.PersistenceHelper.checkStoredLength(transferMsg.toString(), TRANSFER_MSG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "transferMsg"), java.lang.String.valueOf(java.lang.Math.min(TRANSFER_MSG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "transferMsg", this.transferMsg, transferMsg));
   }

   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String TASK = "task";
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String TASK_REFERENCE = "taskReference";
   wt.fc.ObjectReference taskReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public e3ps.project.E3PSTask getTask() {
      return (taskReference != null) ? (e3ps.project.E3PSTask) taskReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public wt.fc.ObjectReference getTaskReference() {
      return taskReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setTask(e3ps.project.E3PSTask the_task) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTaskReference(the_task == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSTask) the_task));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setTaskReference(wt.fc.ObjectReference the_taskReference) throws wt.util.WTPropertyVetoException {
      taskReferenceValidate(the_taskReference);
      taskReference = (wt.fc.ObjectReference) the_taskReference;
   }
   void taskReferenceValidate(wt.fc.ObjectReference the_taskReference) throws wt.util.WTPropertyVetoException {
      if (the_taskReference == null || the_taskReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskReference") },
               new java.beans.PropertyChangeEvent(this, "taskReference", this.taskReference, taskReference));
      if (the_taskReference != null && the_taskReference.getReferencedClass() != null &&
            !e3ps.project.E3PSTask.class.isAssignableFrom(the_taskReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "taskReference", this.taskReference, taskReference));
   }

   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String COST_PART = "costPart";
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String COST_PART_REFERENCE = "costPartReference";
   wt.fc.ObjectReference costPartReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public ext.ket.cost.entity.CostPart getCostPart() {
      return (costPartReference != null) ? (ext.ket.cost.entity.CostPart) costPartReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public wt.fc.ObjectReference getCostPartReference() {
      return costPartReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setCostPart(ext.ket.cost.entity.CostPart the_costPart) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostPartReference(the_costPart == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostPart) the_costPart));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
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

   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public e3ps.project.E3PSProjectMaster getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProjectMaster) projectReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setProject(e3ps.project.E3PSProjectMaster the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProjectMaster) the_project));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setProjectReference(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      projectReferenceValidate(the_projectReference);
      projectReference = (wt.fc.ObjectReference) the_projectReference;
   }
   void projectReferenceValidate(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      if (the_projectReference == null || the_projectReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference") },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
      if (the_projectReference != null && the_projectReference.getReferencedClass() != null &&
            !e3ps.project.E3PSProjectMaster.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PART_LIST = "partList";
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public static final java.lang.String PART_LIST_REFERENCE = "partListReference";
   wt.fc.ObjectReference partListReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public ext.ket.cost.entity.PartList getPartList() {
      return (partListReference != null) ? (ext.ket.cost.entity.PartList) partListReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public wt.fc.ObjectReference getPartListReference() {
      return partListReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setPartList(ext.ket.cost.entity.PartList the_partList) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartListReference(the_partList == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.PartList) the_partList));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceHistory
    */
   public void setPartListReference(wt.fc.ObjectReference the_partListReference) throws wt.util.WTPropertyVetoException {
      partListReferenceValidate(the_partListReference);
      partListReference = (wt.fc.ObjectReference) the_partListReference;
   }
   void partListReferenceValidate(wt.fc.ObjectReference the_partListReference) throws wt.util.WTPropertyVetoException {
      if (the_partListReference == null || the_partListReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partListReference") },
               new java.beans.PropertyChangeEvent(this, "partListReference", this.partListReference, partListReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -9098457252059629669L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeBoolean( bomTreeCheck );
      output.writeObject( costCalcDate );
      output.writeObject( costPartReference );
      output.writeObject( delFlag );
      output.writeObject( directCost );
      output.writeObject( drStep );
      output.writeObject( etcCost );
      output.writeObject( facReducePrice );
      output.writeObject( inDirectCost );
      output.writeObject( inDirectLaborCost );
      output.writeObject( laborCost );
      output.writeBoolean( lastest );
      output.writeObject( materialCost );
      output.writeObject( moldReducePrice );
      output.writeObject( orgProductCostTotal );
      output.writeObject( outUnitCost );
      output.writeObject( owner );
      output.writeObject( partListReference );
      output.writeObject( partNo );
      output.writeObject( pjtNo );
      output.writeObject( pjtType );
      output.writeObject( productCostTotal );
      output.writeObject( projectReference );
      output.writeObject( realPartNo );
      output.writeObject( salesManageCost );
      output.writeObject( salesQty1 );
      output.writeObject( salesQty10 );
      output.writeObject( salesQty11 );
      output.writeObject( salesQty12 );
      output.writeObject( salesQty13 );
      output.writeObject( salesQty14 );
      output.writeObject( salesQty15 );
      output.writeObject( salesQty16 );
      output.writeObject( salesQty17 );
      output.writeObject( salesQty18 );
      output.writeObject( salesQty19 );
      output.writeObject( salesQty2 );
      output.writeObject( salesQty20 );
      output.writeObject( salesQty3 );
      output.writeObject( salesQty4 );
      output.writeObject( salesQty5 );
      output.writeObject( salesQty6 );
      output.writeObject( salesQty7 );
      output.writeObject( salesQty8 );
      output.writeObject( salesQty9 );
      output.writeObject( salesTargetCostTotal );
      output.writeObject( scrapSalesCost );
      output.writeObject( sopEyear );
      output.writeObject( sopSyear );
      output.writeObject( taskReference );
      output.writeObject( thePersistInfo );
      output.writeObject( transferDate );
      output.writeObject( transferFlag );
      output.writeObject( transferMsg );
      output.writeObject( version );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostInterfaceHistory) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setBoolean( "bomTreeCheck", bomTreeCheck );
      output.setString( "costCalcDate", costCalcDate );
      output.writeObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      output.setString( "delFlag", delFlag );
      output.setString( "directCost", directCost );
      output.setString( "drStep", drStep );
      output.setString( "etcCost", etcCost );
      output.setString( "facReducePrice", facReducePrice );
      output.setString( "inDirectCost", inDirectCost );
      output.setString( "inDirectLaborCost", inDirectLaborCost );
      output.setString( "laborCost", laborCost );
      output.setBoolean( "lastest", lastest );
      output.setString( "materialCost", materialCost );
      output.setString( "moldReducePrice", moldReducePrice );
      output.setString( "orgProductCostTotal", orgProductCostTotal );
      output.setString( "outUnitCost", outUnitCost );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      output.setString( "partNo", partNo );
      output.setString( "pjtNo", pjtNo );
      output.setString( "pjtType", pjtType );
      output.setString( "productCostTotal", productCostTotal );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "realPartNo", realPartNo );
      output.setString( "salesManageCost", salesManageCost );
      output.setString( "salesQty1", salesQty1 );
      output.setString( "salesQty10", salesQty10 );
      output.setString( "salesQty11", salesQty11 );
      output.setString( "salesQty12", salesQty12 );
      output.setString( "salesQty13", salesQty13 );
      output.setString( "salesQty14", salesQty14 );
      output.setString( "salesQty15", salesQty15 );
      output.setString( "salesQty16", salesQty16 );
      output.setString( "salesQty17", salesQty17 );
      output.setString( "salesQty18", salesQty18 );
      output.setString( "salesQty19", salesQty19 );
      output.setString( "salesQty2", salesQty2 );
      output.setString( "salesQty20", salesQty20 );
      output.setString( "salesQty3", salesQty3 );
      output.setString( "salesQty4", salesQty4 );
      output.setString( "salesQty5", salesQty5 );
      output.setString( "salesQty6", salesQty6 );
      output.setString( "salesQty7", salesQty7 );
      output.setString( "salesQty8", salesQty8 );
      output.setString( "salesQty9", salesQty9 );
      output.setString( "salesTargetCostTotal", salesTargetCostTotal );
      output.setString( "scrapSalesCost", scrapSalesCost );
      output.setString( "sopEyear", sopEyear );
      output.setString( "sopSyear", sopSyear );
      output.writeObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setTimestamp( "transferDate", transferDate );
      output.setString( "transferFlag", transferFlag );
      output.setString( "transferMsg", transferMsg );
      output.setString( "version", version );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      bomTreeCheck = input.getBoolean( "bomTreeCheck" );
      costCalcDate = input.getString( "costCalcDate" );
      costPartReference = (wt.fc.ObjectReference) input.readObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      delFlag = input.getString( "delFlag" );
      directCost = input.getString( "directCost" );
      drStep = input.getString( "drStep" );
      etcCost = input.getString( "etcCost" );
      facReducePrice = input.getString( "facReducePrice" );
      inDirectCost = input.getString( "inDirectCost" );
      inDirectLaborCost = input.getString( "inDirectLaborCost" );
      laborCost = input.getString( "laborCost" );
      lastest = input.getBoolean( "lastest" );
      materialCost = input.getString( "materialCost" );
      moldReducePrice = input.getString( "moldReducePrice" );
      orgProductCostTotal = input.getString( "orgProductCostTotal" );
      outUnitCost = input.getString( "outUnitCost" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partListReference = (wt.fc.ObjectReference) input.readObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      partNo = input.getString( "partNo" );
      pjtNo = input.getString( "pjtNo" );
      pjtType = input.getString( "pjtType" );
      productCostTotal = input.getString( "productCostTotal" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      realPartNo = input.getString( "realPartNo" );
      salesManageCost = input.getString( "salesManageCost" );
      salesQty1 = input.getString( "salesQty1" );
      salesQty10 = input.getString( "salesQty10" );
      salesQty11 = input.getString( "salesQty11" );
      salesQty12 = input.getString( "salesQty12" );
      salesQty13 = input.getString( "salesQty13" );
      salesQty14 = input.getString( "salesQty14" );
      salesQty15 = input.getString( "salesQty15" );
      salesQty16 = input.getString( "salesQty16" );
      salesQty17 = input.getString( "salesQty17" );
      salesQty18 = input.getString( "salesQty18" );
      salesQty19 = input.getString( "salesQty19" );
      salesQty2 = input.getString( "salesQty2" );
      salesQty20 = input.getString( "salesQty20" );
      salesQty3 = input.getString( "salesQty3" );
      salesQty4 = input.getString( "salesQty4" );
      salesQty5 = input.getString( "salesQty5" );
      salesQty6 = input.getString( "salesQty6" );
      salesQty7 = input.getString( "salesQty7" );
      salesQty8 = input.getString( "salesQty8" );
      salesQty9 = input.getString( "salesQty9" );
      salesTargetCostTotal = input.getString( "salesTargetCostTotal" );
      scrapSalesCost = input.getString( "scrapSalesCost" );
      sopEyear = input.getString( "sopEyear" );
      sopSyear = input.getString( "sopSyear" );
      taskReference = (wt.fc.ObjectReference) input.readObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      transferDate = input.getTimestamp( "transferDate" );
      transferFlag = input.getString( "transferFlag" );
      transferMsg = input.getString( "transferMsg" );
      version = input.getString( "version" );
   }

   boolean readVersion_9098457252059629669L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      bomTreeCheck = input.readBoolean();
      costCalcDate = (java.lang.String) input.readObject();
      costPartReference = (wt.fc.ObjectReference) input.readObject();
      delFlag = (java.lang.String) input.readObject();
      directCost = (java.lang.String) input.readObject();
      drStep = (java.lang.String) input.readObject();
      etcCost = (java.lang.String) input.readObject();
      facReducePrice = (java.lang.String) input.readObject();
      inDirectCost = (java.lang.String) input.readObject();
      inDirectLaborCost = (java.lang.String) input.readObject();
      laborCost = (java.lang.String) input.readObject();
      lastest = input.readBoolean();
      materialCost = (java.lang.String) input.readObject();
      moldReducePrice = (java.lang.String) input.readObject();
      orgProductCostTotal = (java.lang.String) input.readObject();
      outUnitCost = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partListReference = (wt.fc.ObjectReference) input.readObject();
      partNo = (java.lang.String) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      pjtType = (java.lang.String) input.readObject();
      productCostTotal = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      realPartNo = (java.lang.String) input.readObject();
      salesManageCost = (java.lang.String) input.readObject();
      salesQty1 = (java.lang.String) input.readObject();
      salesQty10 = (java.lang.String) input.readObject();
      salesQty11 = (java.lang.String) input.readObject();
      salesQty12 = (java.lang.String) input.readObject();
      salesQty13 = (java.lang.String) input.readObject();
      salesQty14 = (java.lang.String) input.readObject();
      salesQty15 = (java.lang.String) input.readObject();
      salesQty16 = (java.lang.String) input.readObject();
      salesQty17 = (java.lang.String) input.readObject();
      salesQty18 = (java.lang.String) input.readObject();
      salesQty19 = (java.lang.String) input.readObject();
      salesQty2 = (java.lang.String) input.readObject();
      salesQty20 = (java.lang.String) input.readObject();
      salesQty3 = (java.lang.String) input.readObject();
      salesQty4 = (java.lang.String) input.readObject();
      salesQty5 = (java.lang.String) input.readObject();
      salesQty6 = (java.lang.String) input.readObject();
      salesQty7 = (java.lang.String) input.readObject();
      salesQty8 = (java.lang.String) input.readObject();
      salesQty9 = (java.lang.String) input.readObject();
      salesTargetCostTotal = (java.lang.String) input.readObject();
      scrapSalesCost = (java.lang.String) input.readObject();
      sopEyear = (java.lang.String) input.readObject();
      sopSyear = (java.lang.String) input.readObject();
      taskReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      transferDate = (java.sql.Timestamp) input.readObject();
      transferFlag = (java.lang.String) input.readObject();
      transferMsg = (java.lang.String) input.readObject();
      version = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostInterfaceHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_9098457252059629669L( input, readSerialVersionUID, superDone );
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
