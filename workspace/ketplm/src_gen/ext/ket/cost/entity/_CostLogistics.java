package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostLogistics implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostLogistics.class.getName();

   /**
    * 구분(원재료:M,부품:P)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String DATATYPE = "datatype";
   static int DATATYPE_UPPER_LIMIT = -1;
   java.lang.String datatype;
   /**
    * 구분(원재료:M,부품:P)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getDatatype() {
      return datatype;
   }
   /**
    * 구분(원재료:M,부품:P)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setDatatype(java.lang.String datatype) throws wt.util.WTPropertyVetoException {
      datatypeValidate(datatype);
      this.datatype = datatype;
   }
   void datatypeValidate(java.lang.String datatype) throws wt.util.WTPropertyVetoException {
      if (DATATYPE_UPPER_LIMIT < 1) {
         try { DATATYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("datatype").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DATATYPE_UPPER_LIMIT = 200; }
      }
      if (datatype != null && !wt.fc.PersistenceHelper.checkStoredLength(datatype.toString(), DATATYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "datatype"), java.lang.String.valueOf(java.lang.Math.min(DATATYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "datatype", this.datatype, datatype));
   }

   /**
    * 관세율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String RATE = "rate";
   static int RATE_UPPER_LIMIT = -1;
   java.lang.String rate;
   /**
    * 관세율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getRate() {
      return rate;
   }
   /**
    * 관세율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setRate(java.lang.String rate) throws wt.util.WTPropertyVetoException {
      rateValidate(rate);
      this.rate = rate;
   }
   void rateValidate(java.lang.String rate) throws wt.util.WTPropertyVetoException {
      if (RATE_UPPER_LIMIT < 1) {
         try { RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RATE_UPPER_LIMIT = 200; }
      }
      if (rate != null && !wt.fc.PersistenceHelper.checkStoredLength(rate.toString(), RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rate"), java.lang.String.valueOf(java.lang.Math.min(RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rate", this.rate, rate));
   }

   /**
    * 물류비용
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String COST = "cost";
   static int COST_UPPER_LIMIT = -1;
   java.lang.String cost;
   /**
    * 물류비용
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getCost() {
      return cost;
   }
   /**
    * 물류비용
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setCost(java.lang.String cost) throws wt.util.WTPropertyVetoException {
      costValidate(cost);
      this.cost = cost;
   }
   void costValidate(java.lang.String cost) throws wt.util.WTPropertyVetoException {
      if (COST_UPPER_LIMIT < 1) {
         try { COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_UPPER_LIMIT = 200; }
      }
      if (cost != null && !wt.fc.PersistenceHelper.checkStoredLength(cost.toString(), COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cost"), java.lang.String.valueOf(java.lang.Math.min(COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cost", this.cost, cost));
   }

   /**
    * 내륙운송비(평택)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String IN_LAND_COST = "inLandCost";
   static int IN_LAND_COST_UPPER_LIMIT = -1;
   java.lang.String inLandCost;
   /**
    * 내륙운송비(평택)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getInLandCost() {
      return inLandCost;
   }
   /**
    * 내륙운송비(평택)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setInLandCost(java.lang.String inLandCost) throws wt.util.WTPropertyVetoException {
      inLandCostValidate(inLandCost);
      this.inLandCost = inLandCost;
   }
   void inLandCostValidate(java.lang.String inLandCost) throws wt.util.WTPropertyVetoException {
      if (IN_LAND_COST_UPPER_LIMIT < 1) {
         try { IN_LAND_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inLandCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_LAND_COST_UPPER_LIMIT = 200; }
      }
      if (inLandCost != null && !wt.fc.PersistenceHelper.checkStoredLength(inLandCost.toString(), IN_LAND_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inLandCost"), java.lang.String.valueOf(java.lang.Math.min(IN_LAND_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inLandCost", this.inLandCost, inLandCost));
   }

   /**
    * 항구부대비용(평택)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String IN_HARBOR_COST = "inHarborCost";
   static int IN_HARBOR_COST_UPPER_LIMIT = -1;
   java.lang.String inHarborCost;
   /**
    * 항구부대비용(평택)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getInHarborCost() {
      return inHarborCost;
   }
   /**
    * 항구부대비용(평택)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setInHarborCost(java.lang.String inHarborCost) throws wt.util.WTPropertyVetoException {
      inHarborCostValidate(inHarborCost);
      this.inHarborCost = inHarborCost;
   }
   void inHarborCostValidate(java.lang.String inHarborCost) throws wt.util.WTPropertyVetoException {
      if (IN_HARBOR_COST_UPPER_LIMIT < 1) {
         try { IN_HARBOR_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inHarborCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_HARBOR_COST_UPPER_LIMIT = 200; }
      }
      if (inHarborCost != null && !wt.fc.PersistenceHelper.checkStoredLength(inHarborCost.toString(), IN_HARBOR_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inHarborCost"), java.lang.String.valueOf(java.lang.Math.min(IN_HARBOR_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inHarborCost", this.inHarborCost, inHarborCost));
   }

   /**
    * 해상운송비
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String SEA_COST = "seaCost";
   static int SEA_COST_UPPER_LIMIT = -1;
   java.lang.String seaCost;
   /**
    * 해상운송비
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getSeaCost() {
      return seaCost;
   }
   /**
    * 해상운송비
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setSeaCost(java.lang.String seaCost) throws wt.util.WTPropertyVetoException {
      seaCostValidate(seaCost);
      this.seaCost = seaCost;
   }
   void seaCostValidate(java.lang.String seaCost) throws wt.util.WTPropertyVetoException {
      if (SEA_COST_UPPER_LIMIT < 1) {
         try { SEA_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("seaCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SEA_COST_UPPER_LIMIT = 200; }
      }
      if (seaCost != null && !wt.fc.PersistenceHelper.checkStoredLength(seaCost.toString(), SEA_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "seaCost"), java.lang.String.valueOf(java.lang.Math.min(SEA_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "seaCost", this.seaCost, seaCost));
   }

   /**
    * 항구부대비용(위해)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String OUT_HARBOR_COST = "outHarborCost";
   static int OUT_HARBOR_COST_UPPER_LIMIT = -1;
   java.lang.String outHarborCost;
   /**
    * 항구부대비용(위해)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getOutHarborCost() {
      return outHarborCost;
   }
   /**
    * 항구부대비용(위해)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setOutHarborCost(java.lang.String outHarborCost) throws wt.util.WTPropertyVetoException {
      outHarborCostValidate(outHarborCost);
      this.outHarborCost = outHarborCost;
   }
   void outHarborCostValidate(java.lang.String outHarborCost) throws wt.util.WTPropertyVetoException {
      if (OUT_HARBOR_COST_UPPER_LIMIT < 1) {
         try { OUT_HARBOR_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outHarborCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_HARBOR_COST_UPPER_LIMIT = 200; }
      }
      if (outHarborCost != null && !wt.fc.PersistenceHelper.checkStoredLength(outHarborCost.toString(), OUT_HARBOR_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outHarborCost"), java.lang.String.valueOf(java.lang.Math.min(OUT_HARBOR_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outHarborCost", this.outHarborCost, outHarborCost));
   }

   /**
    * 내륙운송비(중국)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String OUT_LAND_COST = "outLandCost";
   static int OUT_LAND_COST_UPPER_LIMIT = -1;
   java.lang.String outLandCost;
   /**
    * 내륙운송비(중국)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getOutLandCost() {
      return outLandCost;
   }
   /**
    * 내륙운송비(중국)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setOutLandCost(java.lang.String outLandCost) throws wt.util.WTPropertyVetoException {
      outLandCostValidate(outLandCost);
      this.outLandCost = outLandCost;
   }
   void outLandCostValidate(java.lang.String outLandCost) throws wt.util.WTPropertyVetoException {
      if (OUT_LAND_COST_UPPER_LIMIT < 1) {
         try { OUT_LAND_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outLandCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_LAND_COST_UPPER_LIMIT = 200; }
      }
      if (outLandCost != null && !wt.fc.PersistenceHelper.checkStoredLength(outLandCost.toString(), OUT_LAND_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outLandCost"), java.lang.String.valueOf(java.lang.Math.min(OUT_LAND_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outLandCost", this.outLandCost, outLandCost));
   }

   /**
    * 환율코드(해상)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String SEA_CURRENCY = "seaCurrency";
   static int SEA_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String seaCurrency;
   /**
    * 환율코드(해상)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getSeaCurrency() {
      return seaCurrency;
   }
   /**
    * 환율코드(해상)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setSeaCurrency(java.lang.String seaCurrency) throws wt.util.WTPropertyVetoException {
      seaCurrencyValidate(seaCurrency);
      this.seaCurrency = seaCurrency;
   }
   void seaCurrencyValidate(java.lang.String seaCurrency) throws wt.util.WTPropertyVetoException {
      if (SEA_CURRENCY_UPPER_LIMIT < 1) {
         try { SEA_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("seaCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SEA_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (seaCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(seaCurrency.toString(), SEA_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "seaCurrency"), java.lang.String.valueOf(java.lang.Math.min(SEA_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "seaCurrency", this.seaCurrency, seaCurrency));
   }

   /**
    * 환율코드(항구)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String HARBOR_CURRENCY = "harborCurrency";
   static int HARBOR_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String harborCurrency;
   /**
    * 환율코드(항구)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getHarborCurrency() {
      return harborCurrency;
   }
   /**
    * 환율코드(항구)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setHarborCurrency(java.lang.String harborCurrency) throws wt.util.WTPropertyVetoException {
      harborCurrencyValidate(harborCurrency);
      this.harborCurrency = harborCurrency;
   }
   void harborCurrencyValidate(java.lang.String harborCurrency) throws wt.util.WTPropertyVetoException {
      if (HARBOR_CURRENCY_UPPER_LIMIT < 1) {
         try { HARBOR_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("harborCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HARBOR_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (harborCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(harborCurrency.toString(), HARBOR_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "harborCurrency"), java.lang.String.valueOf(java.lang.Math.min(HARBOR_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "harborCurrency", this.harborCurrency, harborCurrency));
   }

   /**
    * 환율코드(내륙)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String LAND_CURRENCY = "landCurrency";
   static int LAND_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String landCurrency;
   /**
    * 환율코드(내륙)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getLandCurrency() {
      return landCurrency;
   }
   /**
    * 환율코드(내륙)
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setLandCurrency(java.lang.String landCurrency) throws wt.util.WTPropertyVetoException {
      landCurrencyValidate(landCurrency);
      this.landCurrency = landCurrency;
   }
   void landCurrencyValidate(java.lang.String landCurrency) throws wt.util.WTPropertyVetoException {
      if (LAND_CURRENCY_UPPER_LIMIT < 1) {
         try { LAND_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("landCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAND_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (landCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(landCurrency.toString(), LAND_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "landCurrency"), java.lang.String.valueOf(java.lang.Math.min(LAND_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "landCurrency", this.landCurrency, landCurrency));
   }

   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String NOTE = "note";
   static int NOTE_UPPER_LIMIT = -1;
   java.lang.String note;
   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getNote() {
      return note;
   }
   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setNote(java.lang.String note) throws wt.util.WTPropertyVetoException {
      noteValidate(note);
      this.note = note;
   }
   void noteValidate(java.lang.String note) throws wt.util.WTPropertyVetoException {
      if (NOTE_UPPER_LIMIT < 1) {
         try { NOTE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("note").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NOTE_UPPER_LIMIT = 200; }
      }
      if (note != null && !wt.fc.PersistenceHelper.checkStoredLength(note.toString(), NOTE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "note"), java.lang.String.valueOf(java.lang.Math.min(NOTE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "note", this.note, note));
   }

   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String SORTING_ORDER1 = "sortingOrder1";
   java.lang.Integer sortingOrder1;
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.Integer getSortingOrder1() {
      return sortingOrder1;
   }
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setSortingOrder1(java.lang.Integer sortingOrder1) throws wt.util.WTPropertyVetoException {
      sortingOrder1Validate(sortingOrder1);
      this.sortingOrder1 = sortingOrder1;
   }
   void sortingOrder1Validate(java.lang.Integer sortingOrder1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String SORTING_ORDER2 = "sortingOrder2";
   java.lang.Integer sortingOrder2;
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.Integer getSortingOrder2() {
      return sortingOrder2;
   }
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setSortingOrder2(java.lang.Integer sortingOrder2) throws wt.util.WTPropertyVetoException {
      sortingOrder2Validate(sortingOrder2);
      this.sortingOrder2 = sortingOrder2;
   }
   void sortingOrder2Validate(java.lang.Integer sortingOrder2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String SORTING_ORDER3 = "sortingOrder3";
   java.lang.Integer sortingOrder3;
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.Integer getSortingOrder3() {
      return sortingOrder3;
   }
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setSortingOrder3(java.lang.Integer sortingOrder3) throws wt.util.WTPropertyVetoException {
      sortingOrder3Validate(sortingOrder3);
      this.sortingOrder3 = sortingOrder3;
   }
   void sortingOrder3Validate(java.lang.Integer sortingOrder3) throws wt.util.WTPropertyVetoException {
   }

   /**
    * pallet/Container
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String P_CONTAINER = "p_container";
   static int P_CONTAINER_UPPER_LIMIT = -1;
   java.lang.String p_container;
   /**
    * pallet/Container
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getP_container() {
      return p_container;
   }
   /**
    * pallet/Container
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setP_container(java.lang.String p_container) throws wt.util.WTPropertyVetoException {
      p_containerValidate(p_container);
      this.p_container = p_container;
   }
   void p_containerValidate(java.lang.String p_container) throws wt.util.WTPropertyVetoException {
      if (P_CONTAINER_UPPER_LIMIT < 1) {
         try { P_CONTAINER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("p_container").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_CONTAINER_UPPER_LIMIT = 200; }
      }
      if (p_container != null && !wt.fc.PersistenceHelper.checkStoredLength(p_container.toString(), P_CONTAINER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "p_container"), java.lang.String.valueOf(java.lang.Math.min(P_CONTAINER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "p_container", this.p_container, p_container));
   }

   /**
    * 출하내륙운송비환율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String IN_LAND_CURRENCY = "inLandCurrency";
   static int IN_LAND_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String inLandCurrency;
   /**
    * 출하내륙운송비환율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getInLandCurrency() {
      return inLandCurrency;
   }
   /**
    * 출하내륙운송비환율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setInLandCurrency(java.lang.String inLandCurrency) throws wt.util.WTPropertyVetoException {
      inLandCurrencyValidate(inLandCurrency);
      this.inLandCurrency = inLandCurrency;
   }
   void inLandCurrencyValidate(java.lang.String inLandCurrency) throws wt.util.WTPropertyVetoException {
      if (IN_LAND_CURRENCY_UPPER_LIMIT < 1) {
         try { IN_LAND_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inLandCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_LAND_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (inLandCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(inLandCurrency.toString(), IN_LAND_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inLandCurrency"), java.lang.String.valueOf(java.lang.Math.min(IN_LAND_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inLandCurrency", this.inLandCurrency, inLandCurrency));
   }

   /**
    * 출하항구부대비용환율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String IN_HARBOR_CURRENCY = "inHarborCurrency";
   static int IN_HARBOR_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String inHarborCurrency;
   /**
    * 출하항구부대비용환율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getInHarborCurrency() {
      return inHarborCurrency;
   }
   /**
    * 출하항구부대비용환율
    *
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setInHarborCurrency(java.lang.String inHarborCurrency) throws wt.util.WTPropertyVetoException {
      inHarborCurrencyValidate(inHarborCurrency);
      this.inHarborCurrency = inHarborCurrency;
   }
   void inHarborCurrencyValidate(java.lang.String inHarborCurrency) throws wt.util.WTPropertyVetoException {
      if (IN_HARBOR_CURRENCY_UPPER_LIMIT < 1) {
         try { IN_HARBOR_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inHarborCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_HARBOR_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (inHarborCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(inHarborCurrency.toString(), IN_HARBOR_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inHarborCurrency"), java.lang.String.valueOf(java.lang.Math.min(IN_HARBOR_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inHarborCurrency", this.inHarborCurrency, inHarborCurrency));
   }

   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String MFT1 = "mft1";
   static int MFT1_UPPER_LIMIT = -1;
   java.lang.String mft1;
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getMft1() {
      return mft1;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setMft1(java.lang.String mft1) throws wt.util.WTPropertyVetoException {
      mft1Validate(mft1);
      this.mft1 = mft1;
   }
   void mft1Validate(java.lang.String mft1) throws wt.util.WTPropertyVetoException {
      if (MFT1_UPPER_LIMIT < 1) {
         try { MFT1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mft1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT1_UPPER_LIMIT = 200; }
      }
      if (mft1 != null && !wt.fc.PersistenceHelper.checkStoredLength(mft1.toString(), MFT1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mft1"), java.lang.String.valueOf(java.lang.Math.min(MFT1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mft1", this.mft1, mft1));
   }

   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String MFT2 = "mft2";
   static int MFT2_UPPER_LIMIT = -1;
   java.lang.String mft2;
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getMft2() {
      return mft2;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setMft2(java.lang.String mft2) throws wt.util.WTPropertyVetoException {
      mft2Validate(mft2);
      this.mft2 = mft2;
   }
   void mft2Validate(java.lang.String mft2) throws wt.util.WTPropertyVetoException {
      if (MFT2_UPPER_LIMIT < 1) {
         try { MFT2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mft2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT2_UPPER_LIMIT = 200; }
      }
      if (mft2 != null && !wt.fc.PersistenceHelper.checkStoredLength(mft2.toString(), MFT2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mft2"), java.lang.String.valueOf(java.lang.Math.min(MFT2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mft2", this.mft2, mft2));
   }

   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String MFT3 = "mft3";
   static int MFT3_UPPER_LIMIT = -1;
   java.lang.String mft3;
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getMft3() {
      return mft3;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setMft3(java.lang.String mft3) throws wt.util.WTPropertyVetoException {
      mft3Validate(mft3);
      this.mft3 = mft3;
   }
   void mft3Validate(java.lang.String mft3) throws wt.util.WTPropertyVetoException {
      if (MFT3_UPPER_LIMIT < 1) {
         try { MFT3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mft3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT3_UPPER_LIMIT = 200; }
      }
      if (mft3 != null && !wt.fc.PersistenceHelper.checkStoredLength(mft3.toString(), MFT3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mft3"), java.lang.String.valueOf(java.lang.Math.min(MFT3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mft3", this.mft3, mft3));
   }

   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String MFT4 = "mft4";
   static int MFT4_UPPER_LIMIT = -1;
   java.lang.String mft4;
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public java.lang.String getMft4() {
      return mft4;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setMft4(java.lang.String mft4) throws wt.util.WTPropertyVetoException {
      mft4Validate(mft4);
      this.mft4 = mft4;
   }
   void mft4Validate(java.lang.String mft4) throws wt.util.WTPropertyVetoException {
      if (MFT4_UPPER_LIMIT < 1) {
         try { MFT4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mft4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT4_UPPER_LIMIT = 200; }
      }
      if (mft4 != null && !wt.fc.PersistenceHelper.checkStoredLength(mft4.toString(), MFT4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mft4"), java.lang.String.valueOf(java.lang.Math.min(MFT4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mft4", this.mft4, mft4));
   }

   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String FACTORY1 = "factory1";
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String FACTORY1_REFERENCE = "factory1Reference";
   wt.fc.ObjectReference factory1Reference;
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public e3ps.common.code.NumberCode getFactory1() {
      return (factory1Reference != null) ? (e3ps.common.code.NumberCode) factory1Reference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public wt.fc.ObjectReference getFactory1Reference() {
      return factory1Reference;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setFactory1(e3ps.common.code.NumberCode the_factory1) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFactory1Reference(the_factory1 == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_factory1));
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setFactory1Reference(wt.fc.ObjectReference the_factory1Reference) throws wt.util.WTPropertyVetoException {
      factory1ReferenceValidate(the_factory1Reference);
      factory1Reference = (wt.fc.ObjectReference) the_factory1Reference;
   }
   void factory1ReferenceValidate(wt.fc.ObjectReference the_factory1Reference) throws wt.util.WTPropertyVetoException {
      if (the_factory1Reference != null && the_factory1Reference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_factory1Reference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "factory1Reference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "factory1Reference", this.factory1Reference, factory1Reference));
   }

   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String FACTORY2 = "factory2";
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String FACTORY2_REFERENCE = "factory2Reference";
   wt.fc.ObjectReference factory2Reference;
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public e3ps.common.code.NumberCode getFactory2() {
      return (factory2Reference != null) ? (e3ps.common.code.NumberCode) factory2Reference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public wt.fc.ObjectReference getFactory2Reference() {
      return factory2Reference;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setFactory2(e3ps.common.code.NumberCode the_factory2) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFactory2Reference(the_factory2 == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_factory2));
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setFactory2Reference(wt.fc.ObjectReference the_factory2Reference) throws wt.util.WTPropertyVetoException {
      factory2ReferenceValidate(the_factory2Reference);
      factory2Reference = (wt.fc.ObjectReference) the_factory2Reference;
   }
   void factory2ReferenceValidate(wt.fc.ObjectReference the_factory2Reference) throws wt.util.WTPropertyVetoException {
      if (the_factory2Reference != null && the_factory2Reference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_factory2Reference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "factory2Reference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "factory2Reference", this.factory2Reference, factory2Reference));
   }

   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String FACTORY3 = "factory3";
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public static final java.lang.String FACTORY3_REFERENCE = "factory3Reference";
   wt.fc.ObjectReference factory3Reference;
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public e3ps.common.code.NumberCode getFactory3() {
      return (factory3Reference != null) ? (e3ps.common.code.NumberCode) factory3Reference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public wt.fc.ObjectReference getFactory3Reference() {
      return factory3Reference;
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setFactory3(e3ps.common.code.NumberCode the_factory3) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFactory3Reference(the_factory3 == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_factory3));
   }
   /**
    * @see ext.ket.cost.entity.CostLogistics
    */
   public void setFactory3Reference(wt.fc.ObjectReference the_factory3Reference) throws wt.util.WTPropertyVetoException {
      factory3ReferenceValidate(the_factory3Reference);
      factory3Reference = (wt.fc.ObjectReference) the_factory3Reference;
   }
   void factory3ReferenceValidate(wt.fc.ObjectReference the_factory3Reference) throws wt.util.WTPropertyVetoException {
      if (the_factory3Reference != null && the_factory3Reference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_factory3Reference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "factory3Reference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "factory3Reference", this.factory3Reference, factory3Reference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -7692295468070758307L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( cost );
      output.writeObject( datatype );
      output.writeObject( factory1Reference );
      output.writeObject( factory2Reference );
      output.writeObject( factory3Reference );
      output.writeObject( harborCurrency );
      output.writeObject( inHarborCost );
      output.writeObject( inHarborCurrency );
      output.writeObject( inLandCost );
      output.writeObject( inLandCurrency );
      output.writeObject( landCurrency );
      output.writeObject( mft1 );
      output.writeObject( mft2 );
      output.writeObject( mft3 );
      output.writeObject( mft4 );
      output.writeObject( note );
      output.writeObject( outHarborCost );
      output.writeObject( outLandCost );
      output.writeObject( owner );
      output.writeObject( p_container );
      output.writeObject( rate );
      output.writeObject( seaCost );
      output.writeObject( seaCurrency );
      output.writeObject( sortingOrder1 );
      output.writeObject( sortingOrder2 );
      output.writeObject( sortingOrder3 );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostLogistics) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "cost", cost );
      output.setString( "datatype", datatype );
      output.writeObject( "factory1Reference", factory1Reference, wt.fc.ObjectReference.class, true );
      output.writeObject( "factory2Reference", factory2Reference, wt.fc.ObjectReference.class, true );
      output.writeObject( "factory3Reference", factory3Reference, wt.fc.ObjectReference.class, true );
      output.setString( "harborCurrency", harborCurrency );
      output.setString( "inHarborCost", inHarborCost );
      output.setString( "inHarborCurrency", inHarborCurrency );
      output.setString( "inLandCost", inLandCost );
      output.setString( "inLandCurrency", inLandCurrency );
      output.setString( "landCurrency", landCurrency );
      output.setString( "mft1", mft1 );
      output.setString( "mft2", mft2 );
      output.setString( "mft3", mft3 );
      output.setString( "mft4", mft4 );
      output.setString( "note", note );
      output.setString( "outHarborCost", outHarborCost );
      output.setString( "outLandCost", outLandCost );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "p_container", p_container );
      output.setString( "rate", rate );
      output.setString( "seaCost", seaCost );
      output.setString( "seaCurrency", seaCurrency );
      output.setIntObject( "sortingOrder1", sortingOrder1 );
      output.setIntObject( "sortingOrder2", sortingOrder2 );
      output.setIntObject( "sortingOrder3", sortingOrder3 );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      cost = input.getString( "cost" );
      datatype = input.getString( "datatype" );
      factory1Reference = (wt.fc.ObjectReference) input.readObject( "factory1Reference", factory1Reference, wt.fc.ObjectReference.class, true );
      factory2Reference = (wt.fc.ObjectReference) input.readObject( "factory2Reference", factory2Reference, wt.fc.ObjectReference.class, true );
      factory3Reference = (wt.fc.ObjectReference) input.readObject( "factory3Reference", factory3Reference, wt.fc.ObjectReference.class, true );
      harborCurrency = input.getString( "harborCurrency" );
      inHarborCost = input.getString( "inHarborCost" );
      inHarborCurrency = input.getString( "inHarborCurrency" );
      inLandCost = input.getString( "inLandCost" );
      inLandCurrency = input.getString( "inLandCurrency" );
      landCurrency = input.getString( "landCurrency" );
      mft1 = input.getString( "mft1" );
      mft2 = input.getString( "mft2" );
      mft3 = input.getString( "mft3" );
      mft4 = input.getString( "mft4" );
      note = input.getString( "note" );
      outHarborCost = input.getString( "outHarborCost" );
      outLandCost = input.getString( "outLandCost" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      p_container = input.getString( "p_container" );
      rate = input.getString( "rate" );
      seaCost = input.getString( "seaCost" );
      seaCurrency = input.getString( "seaCurrency" );
      sortingOrder1 = input.getIntObject( "sortingOrder1" );
      sortingOrder2 = input.getIntObject( "sortingOrder2" );
      sortingOrder3 = input.getIntObject( "sortingOrder3" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_7692295468070758307L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      cost = (java.lang.String) input.readObject();
      datatype = (java.lang.String) input.readObject();
      factory1Reference = (wt.fc.ObjectReference) input.readObject();
      factory2Reference = (wt.fc.ObjectReference) input.readObject();
      factory3Reference = (wt.fc.ObjectReference) input.readObject();
      harborCurrency = (java.lang.String) input.readObject();
      inHarborCost = (java.lang.String) input.readObject();
      inHarborCurrency = (java.lang.String) input.readObject();
      inLandCost = (java.lang.String) input.readObject();
      inLandCurrency = (java.lang.String) input.readObject();
      landCurrency = (java.lang.String) input.readObject();
      mft1 = (java.lang.String) input.readObject();
      mft2 = (java.lang.String) input.readObject();
      mft3 = (java.lang.String) input.readObject();
      mft4 = (java.lang.String) input.readObject();
      note = (java.lang.String) input.readObject();
      outHarborCost = (java.lang.String) input.readObject();
      outLandCost = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      p_container = (java.lang.String) input.readObject();
      rate = (java.lang.String) input.readObject();
      seaCost = (java.lang.String) input.readObject();
      seaCurrency = (java.lang.String) input.readObject();
      sortingOrder1 = (java.lang.Integer) input.readObject();
      sortingOrder2 = (java.lang.Integer) input.readObject();
      sortingOrder3 = (java.lang.Integer) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CostLogistics thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7692295468070758307L( input, readSerialVersionUID, superDone );
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
