package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProductInfo implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProductInfo.class.getName();

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String P_NUM = "pNum";
   static int P_NUM_UPPER_LIMIT = -1;
   java.lang.String pNum;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getPNum() {
      return pNum;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setPNum(java.lang.String pNum) throws wt.util.WTPropertyVetoException {
      pNumValidate(pNum);
      this.pNum = pNum;
   }
   void pNumValidate(java.lang.String pNum) throws wt.util.WTPropertyVetoException {
      if (P_NUM_UPPER_LIMIT < 1) {
         try { P_NUM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pNum").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_NUM_UPPER_LIMIT = 200; }
      }
      if (pNum != null && !wt.fc.PersistenceHelper.checkStoredLength(pNum.toString(), P_NUM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pNum"), java.lang.String.valueOf(java.lang.Math.min(P_NUM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pNum", this.pNum, pNum));
   }

   /**
    * 품명
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String P_NAME = "pName";
   static int P_NAME_UPPER_LIMIT = -1;
   java.lang.String pName;
   /**
    * 품명
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getPName() {
      return pName;
   }
   /**
    * 품명
    *
    * @see e3ps.project.ProductInfo
    */
   public void setPName(java.lang.String pName) throws wt.util.WTPropertyVetoException {
      pNameValidate(pName);
      this.pName = pName;
   }
   void pNameValidate(java.lang.String pName) throws wt.util.WTPropertyVetoException {
      if (P_NAME_UPPER_LIMIT < 1) {
         try { P_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_NAME_UPPER_LIMIT = 200; }
      }
      if (pName != null && !wt.fc.PersistenceHelper.checkStoredLength(pName.toString(), P_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pName"), java.lang.String.valueOf(java.lang.Math.min(P_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pName", this.pName, pName));
   }

   /**
    * 적용부위
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String AREAS = "areas";
   static int AREAS_UPPER_LIMIT = -1;
   java.lang.String areas;
   /**
    * 적용부위
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getAreas() {
      return areas;
   }
   /**
    * 적용부위
    *
    * @see e3ps.project.ProductInfo
    */
   public void setAreas(java.lang.String areas) throws wt.util.WTPropertyVetoException {
      areasValidate(areas);
      this.areas = areas;
   }
   void areasValidate(java.lang.String areas) throws wt.util.WTPropertyVetoException {
      if (AREAS_UPPER_LIMIT < 1) {
         try { AREAS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("areas").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AREAS_UPPER_LIMIT = 200; }
      }
      if (areas != null && !wt.fc.PersistenceHelper.checkStoredLength(areas.toString(), AREAS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "areas"), java.lang.String.valueOf(java.lang.Math.min(AREAS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "areas", this.areas, areas));
   }

   /**
    * usage
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String USAGE = "usage";
   static int USAGE_UPPER_LIMIT = -1;
   java.lang.String usage;
   /**
    * usage
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getUsage() {
      return usage;
   }
   /**
    * usage
    *
    * @see e3ps.project.ProductInfo
    */
   public void setUsage(java.lang.String usage) throws wt.util.WTPropertyVetoException {
      usageValidate(usage);
      this.usage = usage;
   }
   void usageValidate(java.lang.String usage) throws wt.util.WTPropertyVetoException {
      if (USAGE_UPPER_LIMIT < 1) {
         try { USAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("usage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { USAGE_UPPER_LIMIT = 200; }
      }
      if (usage != null && !wt.fc.PersistenceHelper.checkStoredLength(usage.toString(), USAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "usage"), java.lang.String.valueOf(java.lang.Math.min(USAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "usage", this.usage, usage));
   }

   /**
    * 판매가
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String PRICE = "price";
   static int PRICE_UPPER_LIMIT = -1;
   java.lang.String price;
   /**
    * 판매가
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getPrice() {
      return price;
   }
   /**
    * 판매가
    *
    * @see e3ps.project.ProductInfo
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
    * 원가≪≫
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String COST = "cost";
   static int COST_UPPER_LIMIT = -1;
   java.lang.String cost;
   /**
    * 원가≪≫
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getCost() {
      return cost;
   }
   /**
    * 원가≪≫
    *
    * @see e3ps.project.ProductInfo
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
    * 수익률
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String RATE = "rate";
   static int RATE_UPPER_LIMIT = -1;
   java.lang.String rate;
   /**
    * 수익률
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getRate() {
      return rate;
   }
   /**
    * 수익률
    *
    * @see e3ps.project.ProductInfo
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
    * 고객예상가
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String ESTIMATED = "estimated";
   static int ESTIMATED_UPPER_LIMIT = -1;
   java.lang.String estimated;
   /**
    * 고객예상가
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getEstimated() {
      return estimated;
   }
   /**
    * 고객예상가
    *
    * @see e3ps.project.ProductInfo
    */
   public void setEstimated(java.lang.String estimated) throws wt.util.WTPropertyVetoException {
      estimatedValidate(estimated);
      this.estimated = estimated;
   }
   void estimatedValidate(java.lang.String estimated) throws wt.util.WTPropertyVetoException {
      if (ESTIMATED_UPPER_LIMIT < 1) {
         try { ESTIMATED_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("estimated").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ESTIMATED_UPPER_LIMIT = 200; }
      }
      if (estimated != null && !wt.fc.PersistenceHelper.checkStoredLength(estimated.toString(), ESTIMATED_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "estimated"), java.lang.String.valueOf(java.lang.Math.min(ESTIMATED_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "estimated", this.estimated, estimated));
   }

   /**
    * 목표 수익률
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String TARGET_RATE = "targetRate";
   static int TARGET_RATE_UPPER_LIMIT = -1;
   java.lang.String targetRate;
   /**
    * 목표 수익률
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getTargetRate() {
      return targetRate;
   }
   /**
    * 목표 수익률
    *
    * @see e3ps.project.ProductInfo
    */
   public void setTargetRate(java.lang.String targetRate) throws wt.util.WTPropertyVetoException {
      targetRateValidate(targetRate);
      this.targetRate = targetRate;
   }
   void targetRateValidate(java.lang.String targetRate) throws wt.util.WTPropertyVetoException {
      if (TARGET_RATE_UPPER_LIMIT < 1) {
         try { TARGET_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_RATE_UPPER_LIMIT = 200; }
      }
      if (targetRate != null && !wt.fc.PersistenceHelper.checkStoredLength(targetRate.toString(), TARGET_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetRate"), java.lang.String.valueOf(java.lang.Math.min(TARGET_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetRate", this.targetRate, targetRate));
   }

   /**
    * 목표투자비
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String INVESTMENTS = "investments";
   static int INVESTMENTS_UPPER_LIMIT = -1;
   java.lang.String investments;
   /**
    * 목표투자비
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getInvestments() {
      return investments;
   }
   /**
    * 목표투자비
    *
    * @see e3ps.project.ProductInfo
    */
   public void setInvestments(java.lang.String investments) throws wt.util.WTPropertyVetoException {
      investmentsValidate(investments);
      this.investments = investments;
   }
   void investmentsValidate(java.lang.String investments) throws wt.util.WTPropertyVetoException {
      if (INVESTMENTS_UPPER_LIMIT < 1) {
         try { INVESTMENTS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investments").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVESTMENTS_UPPER_LIMIT = 200; }
      }
      if (investments != null && !wt.fc.PersistenceHelper.checkStoredLength(investments.toString(), INVESTMENTS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investments"), java.lang.String.valueOf(java.lang.Math.min(INVESTMENTS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investments", this.investments, investments));
   }

   /**
    * 1년차 예상수량
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR1 = "year1";
   static int YEAR1_UPPER_LIMIT = -1;
   java.lang.String year1;
   /**
    * 1년차 예상수량
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear1() {
      return year1;
   }
   /**
    * 1년차 예상수량
    *
    * @see e3ps.project.ProductInfo
    */
   public void setYear1(java.lang.String year1) throws wt.util.WTPropertyVetoException {
      year1Validate(year1);
      this.year1 = year1;
   }
   void year1Validate(java.lang.String year1) throws wt.util.WTPropertyVetoException {
      if (YEAR1_UPPER_LIMIT < 1) {
         try { YEAR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR1_UPPER_LIMIT = 200; }
      }
      if (year1 != null && !wt.fc.PersistenceHelper.checkStoredLength(year1.toString(), YEAR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year1"), java.lang.String.valueOf(java.lang.Math.min(YEAR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year1", this.year1, year1));
   }

   /**
    * 2년차
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR2 = "year2";
   static int YEAR2_UPPER_LIMIT = -1;
   java.lang.String year2;
   /**
    * 2년차
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear2() {
      return year2;
   }
   /**
    * 2년차
    *
    * @see e3ps.project.ProductInfo
    */
   public void setYear2(java.lang.String year2) throws wt.util.WTPropertyVetoException {
      year2Validate(year2);
      this.year2 = year2;
   }
   void year2Validate(java.lang.String year2) throws wt.util.WTPropertyVetoException {
      if (YEAR2_UPPER_LIMIT < 1) {
         try { YEAR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR2_UPPER_LIMIT = 200; }
      }
      if (year2 != null && !wt.fc.PersistenceHelper.checkStoredLength(year2.toString(), YEAR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year2"), java.lang.String.valueOf(java.lang.Math.min(YEAR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year2", this.year2, year2));
   }

   /**
    * 3년차
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR3 = "year3";
   static int YEAR3_UPPER_LIMIT = -1;
   java.lang.String year3;
   /**
    * 3년차
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear3() {
      return year3;
   }
   /**
    * 3년차
    *
    * @see e3ps.project.ProductInfo
    */
   public void setYear3(java.lang.String year3) throws wt.util.WTPropertyVetoException {
      year3Validate(year3);
      this.year3 = year3;
   }
   void year3Validate(java.lang.String year3) throws wt.util.WTPropertyVetoException {
      if (YEAR3_UPPER_LIMIT < 1) {
         try { YEAR3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR3_UPPER_LIMIT = 200; }
      }
      if (year3 != null && !wt.fc.PersistenceHelper.checkStoredLength(year3.toString(), YEAR3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year3"), java.lang.String.valueOf(java.lang.Math.min(YEAR3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year3", this.year3, year3));
   }

   /**
    * 4년차
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR4 = "year4";
   static int YEAR4_UPPER_LIMIT = -1;
   java.lang.String year4;
   /**
    * 4년차
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear4() {
      return year4;
   }
   /**
    * 4년차
    *
    * @see e3ps.project.ProductInfo
    */
   public void setYear4(java.lang.String year4) throws wt.util.WTPropertyVetoException {
      year4Validate(year4);
      this.year4 = year4;
   }
   void year4Validate(java.lang.String year4) throws wt.util.WTPropertyVetoException {
      if (YEAR4_UPPER_LIMIT < 1) {
         try { YEAR4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR4_UPPER_LIMIT = 200; }
      }
      if (year4 != null && !wt.fc.PersistenceHelper.checkStoredLength(year4.toString(), YEAR4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year4"), java.lang.String.valueOf(java.lang.Math.min(YEAR4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year4", this.year4, year4));
   }

   /**
    * 5년차
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR5 = "year5";
   static int YEAR5_UPPER_LIMIT = -1;
   java.lang.String year5;
   /**
    * 5년차
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear5() {
      return year5;
   }
   /**
    * 5년차
    *
    * @see e3ps.project.ProductInfo
    */
   public void setYear5(java.lang.String year5) throws wt.util.WTPropertyVetoException {
      year5Validate(year5);
      this.year5 = year5;
   }
   void year5Validate(java.lang.String year5) throws wt.util.WTPropertyVetoException {
      if (YEAR5_UPPER_LIMIT < 1) {
         try { YEAR5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR5_UPPER_LIMIT = 200; }
      }
      if (year5 != null && !wt.fc.PersistenceHelper.checkStoredLength(year5.toString(), YEAR5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year5"), java.lang.String.valueOf(java.lang.Math.min(YEAR5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year5", this.year5, year5));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR6 = "year6";
   static int YEAR6_UPPER_LIMIT = -1;
   java.lang.String year6;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear6() {
      return year6;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setYear6(java.lang.String year6) throws wt.util.WTPropertyVetoException {
      year6Validate(year6);
      this.year6 = year6;
   }
   void year6Validate(java.lang.String year6) throws wt.util.WTPropertyVetoException {
      if (YEAR6_UPPER_LIMIT < 1) {
         try { YEAR6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR6_UPPER_LIMIT = 200; }
      }
      if (year6 != null && !wt.fc.PersistenceHelper.checkStoredLength(year6.toString(), YEAR6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year6"), java.lang.String.valueOf(java.lang.Math.min(YEAR6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year6", this.year6, year6));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR7 = "year7";
   static int YEAR7_UPPER_LIMIT = -1;
   java.lang.String year7;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear7() {
      return year7;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setYear7(java.lang.String year7) throws wt.util.WTPropertyVetoException {
      year7Validate(year7);
      this.year7 = year7;
   }
   void year7Validate(java.lang.String year7) throws wt.util.WTPropertyVetoException {
      if (YEAR7_UPPER_LIMIT < 1) {
         try { YEAR7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR7_UPPER_LIMIT = 200; }
      }
      if (year7 != null && !wt.fc.PersistenceHelper.checkStoredLength(year7.toString(), YEAR7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year7"), java.lang.String.valueOf(java.lang.Math.min(YEAR7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year7", this.year7, year7));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR8 = "year8";
   static int YEAR8_UPPER_LIMIT = -1;
   java.lang.String year8;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear8() {
      return year8;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setYear8(java.lang.String year8) throws wt.util.WTPropertyVetoException {
      year8Validate(year8);
      this.year8 = year8;
   }
   void year8Validate(java.lang.String year8) throws wt.util.WTPropertyVetoException {
      if (YEAR8_UPPER_LIMIT < 1) {
         try { YEAR8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR8_UPPER_LIMIT = 200; }
      }
      if (year8 != null && !wt.fc.PersistenceHelper.checkStoredLength(year8.toString(), YEAR8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year8"), java.lang.String.valueOf(java.lang.Math.min(YEAR8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year8", this.year8, year8));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR9 = "year9";
   static int YEAR9_UPPER_LIMIT = -1;
   java.lang.String year9;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear9() {
      return year9;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setYear9(java.lang.String year9) throws wt.util.WTPropertyVetoException {
      year9Validate(year9);
      this.year9 = year9;
   }
   void year9Validate(java.lang.String year9) throws wt.util.WTPropertyVetoException {
      if (YEAR9_UPPER_LIMIT < 1) {
         try { YEAR9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR9_UPPER_LIMIT = 200; }
      }
      if (year9 != null && !wt.fc.PersistenceHelper.checkStoredLength(year9.toString(), YEAR9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year9"), java.lang.String.valueOf(java.lang.Math.min(YEAR9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year9", this.year9, year9));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String YEAR10 = "year10";
   static int YEAR10_UPPER_LIMIT = -1;
   java.lang.String year10;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getYear10() {
      return year10;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setYear10(java.lang.String year10) throws wt.util.WTPropertyVetoException {
      year10Validate(year10);
      this.year10 = year10;
   }
   void year10Validate(java.lang.String year10) throws wt.util.WTPropertyVetoException {
      if (YEAR10_UPPER_LIMIT < 1) {
         try { YEAR10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR10_UPPER_LIMIT = 200; }
      }
      if (year10 != null && !wt.fc.PersistenceHelper.checkStoredLength(year10.toString(), YEAR10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year10"), java.lang.String.valueOf(java.lang.Math.min(YEAR10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year10", this.year10, year10));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String SEQ_NUM = "seqNum";
   static int SEQ_NUM_UPPER_LIMIT = -1;
   java.lang.String seqNum;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getSeqNum() {
      return seqNum;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setSeqNum(java.lang.String seqNum) throws wt.util.WTPropertyVetoException {
      seqNumValidate(seqNum);
      this.seqNum = seqNum;
   }
   void seqNumValidate(java.lang.String seqNum) throws wt.util.WTPropertyVetoException {
      if (SEQ_NUM_UPPER_LIMIT < 1) {
         try { SEQ_NUM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("seqNum").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SEQ_NUM_UPPER_LIMIT = 200; }
      }
      if (seqNum != null && !wt.fc.PersistenceHelper.checkStoredLength(seqNum.toString(), SEQ_NUM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "seqNum"), java.lang.String.valueOf(java.lang.Math.min(SEQ_NUM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "seqNum", this.seqNum, seqNum));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String REVIEW_PROJECT_NO = "reviewProjectNo";
   static int REVIEW_PROJECT_NO_UPPER_LIMIT = -1;
   java.lang.String reviewProjectNo;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getReviewProjectNo() {
      return reviewProjectNo;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setReviewProjectNo(java.lang.String reviewProjectNo) throws wt.util.WTPropertyVetoException {
      reviewProjectNoValidate(reviewProjectNo);
      this.reviewProjectNo = reviewProjectNo;
   }
   void reviewProjectNoValidate(java.lang.String reviewProjectNo) throws wt.util.WTPropertyVetoException {
      if (REVIEW_PROJECT_NO_UPPER_LIMIT < 1) {
         try { REVIEW_PROJECT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewProjectNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_PROJECT_NO_UPPER_LIMIT = 200; }
      }
      if (reviewProjectNo != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewProjectNo.toString(), REVIEW_PROJECT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewProjectNo"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_PROJECT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewProjectNo", this.reviewProjectNo, reviewProjectNo));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String REVIEW_SEQ_NO = "reviewSeqNo";
   static int REVIEW_SEQ_NO_UPPER_LIMIT = -1;
   java.lang.String reviewSeqNo;
   /**
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getReviewSeqNo() {
      return reviewSeqNo;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setReviewSeqNo(java.lang.String reviewSeqNo) throws wt.util.WTPropertyVetoException {
      reviewSeqNoValidate(reviewSeqNo);
      this.reviewSeqNo = reviewSeqNo;
   }
   void reviewSeqNoValidate(java.lang.String reviewSeqNo) throws wt.util.WTPropertyVetoException {
      if (REVIEW_SEQ_NO_UPPER_LIMIT < 1) {
         try { REVIEW_SEQ_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewSeqNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_SEQ_NO_UPPER_LIMIT = 200; }
      }
      if (reviewSeqNo != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewSeqNo.toString(), REVIEW_SEQ_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewSeqNo"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_SEQ_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewSeqNo", this.reviewSeqNo, reviewSeqNo));
   }

   /**
    * 조립처(사내/외주) 구분
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String ASSEMBLY_PLACE_TYPE = "assemblyPlaceType";
   static int ASSEMBLY_PLACE_TYPE_UPPER_LIMIT = -1;
   java.lang.String assemblyPlaceType;
   /**
    * 조립처(사내/외주) 구분
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getAssemblyPlaceType() {
      return assemblyPlaceType;
   }
   /**
    * 조립처(사내/외주) 구분
    *
    * @see e3ps.project.ProductInfo
    */
   public void setAssemblyPlaceType(java.lang.String assemblyPlaceType) throws wt.util.WTPropertyVetoException {
      assemblyPlaceTypeValidate(assemblyPlaceType);
      this.assemblyPlaceType = assemblyPlaceType;
   }
   void assemblyPlaceTypeValidate(java.lang.String assemblyPlaceType) throws wt.util.WTPropertyVetoException {
      if (ASSEMBLY_PLACE_TYPE_UPPER_LIMIT < 1) {
         try { ASSEMBLY_PLACE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assemblyPlaceType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSEMBLY_PLACE_TYPE_UPPER_LIMIT = 200; }
      }
      if (assemblyPlaceType != null && !wt.fc.PersistenceHelper.checkStoredLength(assemblyPlaceType.toString(), ASSEMBLY_PLACE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assemblyPlaceType"), java.lang.String.valueOf(java.lang.Math.min(ASSEMBLY_PLACE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assemblyPlaceType", this.assemblyPlaceType, assemblyPlaceType));
   }

   /**
    * 조립처 파트너NO
    *
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String ASSEMBLY_PARTNER_NO = "assemblyPartnerNo";
   static int ASSEMBLY_PARTNER_NO_UPPER_LIMIT = -1;
   java.lang.String assemblyPartnerNo;
   /**
    * 조립처 파트너NO
    *
    * @see e3ps.project.ProductInfo
    */
   public java.lang.String getAssemblyPartnerNo() {
      return assemblyPartnerNo;
   }
   /**
    * 조립처 파트너NO
    *
    * @see e3ps.project.ProductInfo
    */
   public void setAssemblyPartnerNo(java.lang.String assemblyPartnerNo) throws wt.util.WTPropertyVetoException {
      assemblyPartnerNoValidate(assemblyPartnerNo);
      this.assemblyPartnerNo = assemblyPartnerNo;
   }
   void assemblyPartnerNoValidate(java.lang.String assemblyPartnerNo) throws wt.util.WTPropertyVetoException {
      if (ASSEMBLY_PARTNER_NO_UPPER_LIMIT < 1) {
         try { ASSEMBLY_PARTNER_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assemblyPartnerNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSEMBLY_PARTNER_NO_UPPER_LIMIT = 200; }
      }
      if (assemblyPartnerNo != null && !wt.fc.PersistenceHelper.checkStoredLength(assemblyPartnerNo.toString(), ASSEMBLY_PARTNER_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assemblyPartnerNo"), java.lang.String.valueOf(java.lang.Math.min(ASSEMBLY_PARTNER_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assemblyPartnerNo", this.assemblyPartnerNo, assemblyPartnerNo));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.ProductInfo
    */
   public e3ps.project.E3PSProject getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setProject(e3ps.project.E3PSProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProject) the_project));
   }
   /**
    * @see e3ps.project.ProductInfo
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
            !e3ps.project.E3PSProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String ASSEMBLED_TYPE = "assembledType";
   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String ASSEMBLED_TYPE_REFERENCE = "assembledTypeReference";
   wt.fc.ObjectReference assembledTypeReference;
   /**
    * @see e3ps.project.ProductInfo
    */
   public e3ps.common.code.NumberCode getAssembledType() {
      return (assembledTypeReference != null) ? (e3ps.common.code.NumberCode) assembledTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public wt.fc.ObjectReference getAssembledTypeReference() {
      return assembledTypeReference;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setAssembledType(e3ps.common.code.NumberCode the_assembledType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAssembledTypeReference(the_assembledType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_assembledType));
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setAssembledTypeReference(wt.fc.ObjectReference the_assembledTypeReference) throws wt.util.WTPropertyVetoException {
      assembledTypeReferenceValidate(the_assembledTypeReference);
      assembledTypeReference = (wt.fc.ObjectReference) the_assembledTypeReference;
   }
   void assembledTypeReferenceValidate(wt.fc.ObjectReference the_assembledTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_assembledTypeReference != null && the_assembledTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_assembledTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assembledTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "assembledTypeReference", this.assembledTypeReference, assembledTypeReference));
   }

   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String ASSEMBLY_PLACE = "assemblyPlace";
   /**
    * @see e3ps.project.ProductInfo
    */
   public static final java.lang.String ASSEMBLY_PLACE_REFERENCE = "assemblyPlaceReference";
   wt.fc.ObjectReference assemblyPlaceReference;
   /**
    * @see e3ps.project.ProductInfo
    */
   public e3ps.common.code.NumberCode getAssemblyPlace() {
      return (assemblyPlaceReference != null) ? (e3ps.common.code.NumberCode) assemblyPlaceReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public wt.fc.ObjectReference getAssemblyPlaceReference() {
      return assemblyPlaceReference;
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setAssemblyPlace(e3ps.common.code.NumberCode the_assemblyPlace) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAssemblyPlaceReference(the_assemblyPlace == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_assemblyPlace));
   }
   /**
    * @see e3ps.project.ProductInfo
    */
   public void setAssemblyPlaceReference(wt.fc.ObjectReference the_assemblyPlaceReference) throws wt.util.WTPropertyVetoException {
      assemblyPlaceReferenceValidate(the_assemblyPlaceReference);
      assemblyPlaceReference = (wt.fc.ObjectReference) the_assemblyPlaceReference;
   }
   void assemblyPlaceReferenceValidate(wt.fc.ObjectReference the_assemblyPlaceReference) throws wt.util.WTPropertyVetoException {
      if (the_assemblyPlaceReference != null && the_assemblyPlaceReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_assemblyPlaceReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assemblyPlaceReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "assemblyPlaceReference", this.assemblyPlaceReference, assemblyPlaceReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -6255366863691718578L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( areas );
      output.writeObject( assembledTypeReference );
      output.writeObject( assemblyPartnerNo );
      output.writeObject( assemblyPlaceReference );
      output.writeObject( assemblyPlaceType );
      output.writeObject( cost );
      output.writeObject( estimated );
      output.writeObject( investments );
      output.writeObject( pName );
      output.writeObject( pNum );
      output.writeObject( price );
      output.writeObject( projectReference );
      output.writeObject( rate );
      output.writeObject( reviewProjectNo );
      output.writeObject( reviewSeqNo );
      output.writeObject( seqNum );
      output.writeObject( targetRate );
      output.writeObject( thePersistInfo );
      output.writeObject( usage );
      output.writeObject( year1 );
      output.writeObject( year10 );
      output.writeObject( year2 );
      output.writeObject( year3 );
      output.writeObject( year4 );
      output.writeObject( year5 );
      output.writeObject( year6 );
      output.writeObject( year7 );
      output.writeObject( year8 );
      output.writeObject( year9 );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProductInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "areas", areas );
      output.writeObject( "assembledTypeReference", assembledTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "assemblyPartnerNo", assemblyPartnerNo );
      output.writeObject( "assemblyPlaceReference", assemblyPlaceReference, wt.fc.ObjectReference.class, true );
      output.setString( "assemblyPlaceType", assemblyPlaceType );
      output.setString( "cost", cost );
      output.setString( "estimated", estimated );
      output.setString( "investments", investments );
      output.setString( "pName", pName );
      output.setString( "pNum", pNum );
      output.setString( "price", price );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "rate", rate );
      output.setString( "reviewProjectNo", reviewProjectNo );
      output.setString( "reviewSeqNo", reviewSeqNo );
      output.setString( "seqNum", seqNum );
      output.setString( "targetRate", targetRate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "usage", usage );
      output.setString( "year1", year1 );
      output.setString( "year10", year10 );
      output.setString( "year2", year2 );
      output.setString( "year3", year3 );
      output.setString( "year4", year4 );
      output.setString( "year5", year5 );
      output.setString( "year6", year6 );
      output.setString( "year7", year7 );
      output.setString( "year8", year8 );
      output.setString( "year9", year9 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      areas = input.getString( "areas" );
      assembledTypeReference = (wt.fc.ObjectReference) input.readObject( "assembledTypeReference", assembledTypeReference, wt.fc.ObjectReference.class, true );
      assemblyPartnerNo = input.getString( "assemblyPartnerNo" );
      assemblyPlaceReference = (wt.fc.ObjectReference) input.readObject( "assemblyPlaceReference", assemblyPlaceReference, wt.fc.ObjectReference.class, true );
      assemblyPlaceType = input.getString( "assemblyPlaceType" );
      cost = input.getString( "cost" );
      estimated = input.getString( "estimated" );
      investments = input.getString( "investments" );
      pName = input.getString( "pName" );
      pNum = input.getString( "pNum" );
      price = input.getString( "price" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      rate = input.getString( "rate" );
      reviewProjectNo = input.getString( "reviewProjectNo" );
      reviewSeqNo = input.getString( "reviewSeqNo" );
      seqNum = input.getString( "seqNum" );
      targetRate = input.getString( "targetRate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      usage = input.getString( "usage" );
      year1 = input.getString( "year1" );
      year10 = input.getString( "year10" );
      year2 = input.getString( "year2" );
      year3 = input.getString( "year3" );
      year4 = input.getString( "year4" );
      year5 = input.getString( "year5" );
      year6 = input.getString( "year6" );
      year7 = input.getString( "year7" );
      year8 = input.getString( "year8" );
      year9 = input.getString( "year9" );
   }

   boolean readVersion_6255366863691718578L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      areas = (java.lang.String) input.readObject();
      assembledTypeReference = (wt.fc.ObjectReference) input.readObject();
      assemblyPartnerNo = (java.lang.String) input.readObject();
      assemblyPlaceReference = (wt.fc.ObjectReference) input.readObject();
      assemblyPlaceType = (java.lang.String) input.readObject();
      cost = (java.lang.String) input.readObject();
      estimated = (java.lang.String) input.readObject();
      investments = (java.lang.String) input.readObject();
      pName = (java.lang.String) input.readObject();
      pNum = (java.lang.String) input.readObject();
      price = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      rate = (java.lang.String) input.readObject();
      reviewProjectNo = (java.lang.String) input.readObject();
      reviewSeqNo = (java.lang.String) input.readObject();
      seqNum = (java.lang.String) input.readObject();
      targetRate = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      usage = (java.lang.String) input.readObject();
      year1 = (java.lang.String) input.readObject();
      year10 = (java.lang.String) input.readObject();
      year2 = (java.lang.String) input.readObject();
      year3 = (java.lang.String) input.readObject();
      year4 = (java.lang.String) input.readObject();
      year5 = (java.lang.String) input.readObject();
      year6 = (java.lang.String) input.readObject();
      year7 = (java.lang.String) input.readObject();
      year8 = (java.lang.String) input.readObject();
      year9 = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ProductInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6255366863691718578L( input, readSerialVersionUID, superDone );
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
