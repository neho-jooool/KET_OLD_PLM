package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETCarYearlyAmount implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETCarYearlyAmount.class.getName();

   /**
    * 차종코드
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String CAR_TYPE_CODE = "carTypeCode";
   static int CAR_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String carTypeCode;
   /**
    * 차종코드
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getCarTypeCode() {
      return carTypeCode;
   }
   /**
    * 차종코드
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setCarTypeCode(java.lang.String carTypeCode) throws wt.util.WTPropertyVetoException {
      carTypeCodeValidate(carTypeCode);
      this.carTypeCode = carTypeCode;
   }
   void carTypeCodeValidate(java.lang.String carTypeCode) throws wt.util.WTPropertyVetoException {
      if (CAR_TYPE_CODE_UPPER_LIMIT < 1) {
         try { CAR_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("carTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAR_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (carTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(carTypeCode.toString(), CAR_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carTypeCode"), java.lang.String.valueOf(java.lang.Math.min(CAR_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "carTypeCode", this.carTypeCode, carTypeCode));
   }

   /**
    * 차종명
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String CAR_TYPE = "carType";
   static int CAR_TYPE_UPPER_LIMIT = -1;
   java.lang.String carType;
   /**
    * 차종명
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getCarType() {
      return carType;
   }
   /**
    * 차종명
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setCarType(java.lang.String carType) throws wt.util.WTPropertyVetoException {
      carTypeValidate(carType);
      this.carType = carType;
   }
   void carTypeValidate(java.lang.String carType) throws wt.util.WTPropertyVetoException {
      if (CAR_TYPE_UPPER_LIMIT < 1) {
         try { CAR_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("carType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAR_TYPE_UPPER_LIMIT = 200; }
      }
      if (carType != null && !wt.fc.PersistenceHelper.checkStoredLength(carType.toString(), CAR_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carType"), java.lang.String.valueOf(java.lang.Math.min(CAR_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "carType", this.carType, carType));
   }

   /**
    * 1년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT1 = "yearAmount1";
   java.lang.Double yearAmount1;
   /**
    * 1년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount1() {
      return yearAmount1;
   }
   /**
    * 1년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount1(java.lang.Double yearAmount1) throws wt.util.WTPropertyVetoException {
      yearAmount1Validate(yearAmount1);
      this.yearAmount1 = yearAmount1;
   }
   void yearAmount1Validate(java.lang.Double yearAmount1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 2년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT2 = "yearAmount2";
   java.lang.Double yearAmount2;
   /**
    * 2년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount2() {
      return yearAmount2;
   }
   /**
    * 2년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount2(java.lang.Double yearAmount2) throws wt.util.WTPropertyVetoException {
      yearAmount2Validate(yearAmount2);
      this.yearAmount2 = yearAmount2;
   }
   void yearAmount2Validate(java.lang.Double yearAmount2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 3년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT3 = "yearAmount3";
   java.lang.Double yearAmount3;
   /**
    * 3년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount3() {
      return yearAmount3;
   }
   /**
    * 3년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount3(java.lang.Double yearAmount3) throws wt.util.WTPropertyVetoException {
      yearAmount3Validate(yearAmount3);
      this.yearAmount3 = yearAmount3;
   }
   void yearAmount3Validate(java.lang.Double yearAmount3) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 4년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT4 = "yearAmount4";
   java.lang.Double yearAmount4;
   /**
    * 4년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount4() {
      return yearAmount4;
   }
   /**
    * 4년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount4(java.lang.Double yearAmount4) throws wt.util.WTPropertyVetoException {
      yearAmount4Validate(yearAmount4);
      this.yearAmount4 = yearAmount4;
   }
   void yearAmount4Validate(java.lang.Double yearAmount4) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 5년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT5 = "yearAmount5";
   java.lang.Double yearAmount5;
   /**
    * 5년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount5() {
      return yearAmount5;
   }
   /**
    * 5년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount5(java.lang.Double yearAmount5) throws wt.util.WTPropertyVetoException {
      yearAmount5Validate(yearAmount5);
      this.yearAmount5 = yearAmount5;
   }
   void yearAmount5Validate(java.lang.Double yearAmount5) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 6년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT6 = "yearAmount6";
   java.lang.Double yearAmount6;
   /**
    * 6년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount6() {
      return yearAmount6;
   }
   /**
    * 6년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount6(java.lang.Double yearAmount6) throws wt.util.WTPropertyVetoException {
      yearAmount6Validate(yearAmount6);
      this.yearAmount6 = yearAmount6;
   }
   void yearAmount6Validate(java.lang.Double yearAmount6) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 7년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT7 = "yearAmount7";
   java.lang.Double yearAmount7;
   /**
    * 7년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount7() {
      return yearAmount7;
   }
   /**
    * 7년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount7(java.lang.Double yearAmount7) throws wt.util.WTPropertyVetoException {
      yearAmount7Validate(yearAmount7);
      this.yearAmount7 = yearAmount7;
   }
   void yearAmount7Validate(java.lang.Double yearAmount7) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 8년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT8 = "yearAmount8";
   java.lang.Double yearAmount8;
   /**
    * 8년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount8() {
      return yearAmount8;
   }
   /**
    * 8년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount8(java.lang.Double yearAmount8) throws wt.util.WTPropertyVetoException {
      yearAmount8Validate(yearAmount8);
      this.yearAmount8 = yearAmount8;
   }
   void yearAmount8Validate(java.lang.Double yearAmount8) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 9년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT9 = "yearAmount9";
   java.lang.Double yearAmount9;
   /**
    * 9년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount9() {
      return yearAmount9;
   }
   /**
    * 9년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount9(java.lang.Double yearAmount9) throws wt.util.WTPropertyVetoException {
      yearAmount9Validate(yearAmount9);
      this.yearAmount9 = yearAmount9;
   }
   void yearAmount9Validate(java.lang.Double yearAmount9) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 10년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String YEAR_AMOUNT10 = "yearAmount10";
   java.lang.Double yearAmount10;
   /**
    * 10년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getYearAmount10() {
      return yearAmount10;
   }
   /**
    * 10년차기획수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setYearAmount10(java.lang.Double yearAmount10) throws wt.util.WTPropertyVetoException {
      yearAmount10Validate(yearAmount10);
      this.yearAmount10 = yearAmount10;
   }
   void yearAmount10Validate(java.lang.Double yearAmount10) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 적용수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String APPLYED_USAGE = "applyedUsage";
   java.lang.Double applyedUsage;
   /**
    * 적용수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getApplyedUsage() {
      return applyedUsage;
   }
   /**
    * 적용수량
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setApplyedUsage(java.lang.Double applyedUsage) throws wt.util.WTPropertyVetoException {
      applyedUsageValidate(applyedUsage);
      this.applyedUsage = applyedUsage;
   }
   void applyedUsageValidate(java.lang.Double applyedUsage) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 옵션률
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String OPTION_RATE = "optionRate";
   java.lang.Double optionRate;
   /**
    * 옵션률
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.Double getOptionRate() {
      return optionRate;
   }
   /**
    * 옵션률
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setOptionRate(java.lang.Double optionRate) throws wt.util.WTPropertyVetoException {
      optionRateValidate(optionRate);
      this.optionRate = optionRate;
   }
   void optionRateValidate(java.lang.Double optionRate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute1(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      attribute1Validate(attribute1);
      this.attribute1 = attribute1;
   }
   void attribute1Validate(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE1_UPPER_LIMIT < 1) {
         try { ATTRIBUTE1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE1_UPPER_LIMIT = 200; }
      }
      if (attribute1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute1.toString(), ATTRIBUTE1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute1"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute1", this.attribute1, attribute1));
   }

   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute2(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      attribute2Validate(attribute2);
      this.attribute2 = attribute2;
   }
   void attribute2Validate(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE2_UPPER_LIMIT < 1) {
         try { ATTRIBUTE2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE2_UPPER_LIMIT = 200; }
      }
      if (attribute2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute2.toString(), ATTRIBUTE2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute2"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute2", this.attribute2, attribute2));
   }

   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute3(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      attribute3Validate(attribute3);
      this.attribute3 = attribute3;
   }
   void attribute3Validate(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE3_UPPER_LIMIT < 1) {
         try { ATTRIBUTE3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE3_UPPER_LIMIT = 200; }
      }
      if (attribute3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute3.toString(), ATTRIBUTE3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute3"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute3", this.attribute3, attribute3));
   }

   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute4(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      attribute4Validate(attribute4);
      this.attribute4 = attribute4;
   }
   void attribute4Validate(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE4_UPPER_LIMIT < 1) {
         try { ATTRIBUTE4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE4_UPPER_LIMIT = 200; }
      }
      if (attribute4 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute4.toString(), ATTRIBUTE4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute4"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute4", this.attribute4, attribute4));
   }

   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute5(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      attribute5Validate(attribute5);
      this.attribute5 = attribute5;
   }
   void attribute5Validate(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE5_UPPER_LIMIT < 1) {
         try { ATTRIBUTE5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE5_UPPER_LIMIT = 200; }
      }
      if (attribute5 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute5.toString(), ATTRIBUTE5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute5"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute5", this.attribute5, attribute5));
   }

   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute6(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      attribute6Validate(attribute6);
      this.attribute6 = attribute6;
   }
   void attribute6Validate(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE6_UPPER_LIMIT < 1) {
         try { ATTRIBUTE6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE6_UPPER_LIMIT = 200; }
      }
      if (attribute6 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute6.toString(), ATTRIBUTE6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute6"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute6", this.attribute6, attribute6));
   }

   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute7(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      attribute7Validate(attribute7);
      this.attribute7 = attribute7;
   }
   void attribute7Validate(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE7_UPPER_LIMIT < 1) {
         try { ATTRIBUTE7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE7_UPPER_LIMIT = 200; }
      }
      if (attribute7 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute7.toString(), ATTRIBUTE7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute7"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute7", this.attribute7, attribute7));
   }

   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute8(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      attribute8Validate(attribute8);
      this.attribute8 = attribute8;
   }
   void attribute8Validate(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE8_UPPER_LIMIT < 1) {
         try { ATTRIBUTE8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE8_UPPER_LIMIT = 200; }
      }
      if (attribute8 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute8.toString(), ATTRIBUTE8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute8"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute8", this.attribute8, attribute8));
   }

   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute9(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      attribute9Validate(attribute9);
      this.attribute9 = attribute9;
   }
   void attribute9Validate(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE9_UPPER_LIMIT < 1) {
         try { ATTRIBUTE9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE9_UPPER_LIMIT = 200; }
      }
      if (attribute9 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute9.toString(), ATTRIBUTE9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute9"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute9", this.attribute9, attribute9));
   }

   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETCarYearlyAmount
    */
   public void setAttribute10(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      attribute10Validate(attribute10);
      this.attribute10 = attribute10;
   }
   void attribute10Validate(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE10_UPPER_LIMIT < 1) {
         try { ATTRIBUTE10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE10_UPPER_LIMIT = 200; }
      }
      if (attribute10 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute10.toString(), ATTRIBUTE10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute10"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute10", this.attribute10, attribute10));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7711206531489081861L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( applyedUsage );
      output.writeObject( attribute1 );
      output.writeObject( attribute10 );
      output.writeObject( attribute2 );
      output.writeObject( attribute3 );
      output.writeObject( attribute4 );
      output.writeObject( attribute5 );
      output.writeObject( attribute6 );
      output.writeObject( attribute7 );
      output.writeObject( attribute8 );
      output.writeObject( attribute9 );
      output.writeObject( carType );
      output.writeObject( carTypeCode );
      output.writeObject( optionRate );
      output.writeObject( thePersistInfo );
      output.writeObject( yearAmount1 );
      output.writeObject( yearAmount10 );
      output.writeObject( yearAmount2 );
      output.writeObject( yearAmount3 );
      output.writeObject( yearAmount4 );
      output.writeObject( yearAmount5 );
      output.writeObject( yearAmount6 );
      output.writeObject( yearAmount7 );
      output.writeObject( yearAmount8 );
      output.writeObject( yearAmount9 );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETCarYearlyAmount) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setDoubleObject( "applyedUsage", applyedUsage );
      output.setString( "attribute1", attribute1 );
      output.setString( "attribute10", attribute10 );
      output.setString( "attribute2", attribute2 );
      output.setString( "attribute3", attribute3 );
      output.setString( "attribute4", attribute4 );
      output.setString( "attribute5", attribute5 );
      output.setString( "attribute6", attribute6 );
      output.setString( "attribute7", attribute7 );
      output.setString( "attribute8", attribute8 );
      output.setString( "attribute9", attribute9 );
      output.setString( "carType", carType );
      output.setString( "carTypeCode", carTypeCode );
      output.setDoubleObject( "optionRate", optionRate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setDoubleObject( "yearAmount1", yearAmount1 );
      output.setDoubleObject( "yearAmount10", yearAmount10 );
      output.setDoubleObject( "yearAmount2", yearAmount2 );
      output.setDoubleObject( "yearAmount3", yearAmount3 );
      output.setDoubleObject( "yearAmount4", yearAmount4 );
      output.setDoubleObject( "yearAmount5", yearAmount5 );
      output.setDoubleObject( "yearAmount6", yearAmount6 );
      output.setDoubleObject( "yearAmount7", yearAmount7 );
      output.setDoubleObject( "yearAmount8", yearAmount8 );
      output.setDoubleObject( "yearAmount9", yearAmount9 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      applyedUsage = input.getDoubleObject( "applyedUsage" );
      attribute1 = input.getString( "attribute1" );
      attribute10 = input.getString( "attribute10" );
      attribute2 = input.getString( "attribute2" );
      attribute3 = input.getString( "attribute3" );
      attribute4 = input.getString( "attribute4" );
      attribute5 = input.getString( "attribute5" );
      attribute6 = input.getString( "attribute6" );
      attribute7 = input.getString( "attribute7" );
      attribute8 = input.getString( "attribute8" );
      attribute9 = input.getString( "attribute9" );
      carType = input.getString( "carType" );
      carTypeCode = input.getString( "carTypeCode" );
      optionRate = input.getDoubleObject( "optionRate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      yearAmount1 = input.getDoubleObject( "yearAmount1" );
      yearAmount10 = input.getDoubleObject( "yearAmount10" );
      yearAmount2 = input.getDoubleObject( "yearAmount2" );
      yearAmount3 = input.getDoubleObject( "yearAmount3" );
      yearAmount4 = input.getDoubleObject( "yearAmount4" );
      yearAmount5 = input.getDoubleObject( "yearAmount5" );
      yearAmount6 = input.getDoubleObject( "yearAmount6" );
      yearAmount7 = input.getDoubleObject( "yearAmount7" );
      yearAmount8 = input.getDoubleObject( "yearAmount8" );
      yearAmount9 = input.getDoubleObject( "yearAmount9" );
   }

   boolean readVersion7711206531489081861L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      applyedUsage = (java.lang.Double) input.readObject();
      attribute1 = (java.lang.String) input.readObject();
      attribute10 = (java.lang.String) input.readObject();
      attribute2 = (java.lang.String) input.readObject();
      attribute3 = (java.lang.String) input.readObject();
      attribute4 = (java.lang.String) input.readObject();
      attribute5 = (java.lang.String) input.readObject();
      attribute6 = (java.lang.String) input.readObject();
      attribute7 = (java.lang.String) input.readObject();
      attribute8 = (java.lang.String) input.readObject();
      attribute9 = (java.lang.String) input.readObject();
      carType = (java.lang.String) input.readObject();
      carTypeCode = (java.lang.String) input.readObject();
      optionRate = (java.lang.Double) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      yearAmount1 = (java.lang.Double) input.readObject();
      yearAmount10 = (java.lang.Double) input.readObject();
      yearAmount2 = (java.lang.Double) input.readObject();
      yearAmount3 = (java.lang.Double) input.readObject();
      yearAmount4 = (java.lang.Double) input.readObject();
      yearAmount5 = (java.lang.Double) input.readObject();
      yearAmount6 = (java.lang.Double) input.readObject();
      yearAmount7 = (java.lang.Double) input.readObject();
      yearAmount8 = (java.lang.Double) input.readObject();
      yearAmount9 = (java.lang.Double) input.readObject();
      return true;
   }

   protected boolean readVersion( KETCarYearlyAmount thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7711206531489081861L( input, readSerialVersionUID, superDone );
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
