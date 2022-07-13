package e3ps.ews.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETEarlyWarningTarget implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ews.entity.entityResource";
   static final java.lang.String CLASSNAME = KETEarlyWarningTarget.class.getName();

   /**
    * 제품 번호
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String PRODUCT_NO = "productNo";
   static int PRODUCT_NO_UPPER_LIMIT = -1;
   java.lang.String productNo;
   /**
    * 제품 번호
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getProductNo() {
      return productNo;
   }
   /**
    * 제품 번호
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setProductNo(java.lang.String productNo) throws wt.util.WTPropertyVetoException {
      productNoValidate(productNo);
      this.productNo = productNo;
   }
   void productNoValidate(java.lang.String productNo) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_NO_UPPER_LIMIT < 1) {
         try { PRODUCT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_NO_UPPER_LIMIT = 200; }
      }
      if (productNo != null && !wt.fc.PersistenceHelper.checkStoredLength(productNo.toString(), PRODUCT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productNo"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productNo", this.productNo, productNo));
   }

   /**
    * 고객불량 PPM의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String TARGET_CUS_ERROR = "targetCusError";
   java.lang.Double targetCusError;
   /**
    * 고객불량 PPM의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.Double getTargetCusError() {
      return targetCusError;
   }
   /**
    * 고객불량 PPM의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setTargetCusError(java.lang.Double targetCusError) throws wt.util.WTPropertyVetoException {
      targetCusErrorValidate(targetCusError);
      this.targetCusError = targetCusError;
   }
   void targetCusErrorValidate(java.lang.Double targetCusError) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 공정불량 PPM의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String TARGET_WORK_ERROR = "targetWorkError";
   java.lang.Double targetWorkError;
   /**
    * 공정불량 PPM의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.Double getTargetWorkError() {
      return targetWorkError;
   }
   /**
    * 공정불량 PPM의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setTargetWorkError(java.lang.Double targetWorkError) throws wt.util.WTPropertyVetoException {
      targetWorkErrorValidate(targetWorkError);
      this.targetWorkError = targetWorkError;
   }
   void targetWorkErrorValidate(java.lang.Double targetWorkError) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 설비가동율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String TARGET_FACILITY = "targetFacility";
   java.lang.Double targetFacility;
   /**
    * 설비가동율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.Double getTargetFacility() {
      return targetFacility;
   }
   /**
    * 설비가동율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setTargetFacility(java.lang.Double targetFacility) throws wt.util.WTPropertyVetoException {
      targetFacilityValidate(targetFacility);
      this.targetFacility = targetFacility;
   }
   void targetFacilityValidate(java.lang.Double targetFacility) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 성능가동율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String TARGET_PERFORM = "targetPerform";
   java.lang.Double targetPerform;
   /**
    * 성능가동율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.Double getTargetPerform() {
      return targetPerform;
   }
   /**
    * 성능가동율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setTargetPerform(java.lang.Double targetPerform) throws wt.util.WTPropertyVetoException {
      targetPerformValidate(targetPerform);
      this.targetPerform = targetPerform;
   }
   void targetPerformValidate(java.lang.Double targetPerform) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양품율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String TARGET_GOOD = "targetGood";
   java.lang.Double targetGood;
   /**
    * 양품율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.Double getTargetGood() {
      return targetGood;
   }
   /**
    * 양품율의 목표값
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setTargetGood(java.lang.Double targetGood) throws wt.util.WTPropertyVetoException {
      targetGoodValidate(targetGood);
      this.targetGood = targetGood;
   }
   void targetGoodValidate(java.lang.Double targetGood) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 추가 속성 항목1
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * 추가 속성 항목1
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * 추가 속성 항목1
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute1(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      attribute1Validate(attribute1);
      this.attribute1 = attribute1;
   }
   void attribute1Validate(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE1_UPPER_LIMIT < 1) {
         try { ATTRIBUTE1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE1_UPPER_LIMIT = 4000; }
      }
      if (attribute1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute1.toString(), ATTRIBUTE1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute1"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute1", this.attribute1, attribute1));
   }

   /**
    * 추가 속성 항목2
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * 추가 속성 항목2
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * 추가 속성 항목2
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute2(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      attribute2Validate(attribute2);
      this.attribute2 = attribute2;
   }
   void attribute2Validate(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE2_UPPER_LIMIT < 1) {
         try { ATTRIBUTE2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE2_UPPER_LIMIT = 4000; }
      }
      if (attribute2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute2.toString(), ATTRIBUTE2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute2"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute2", this.attribute2, attribute2));
   }

   /**
    * 추가 속성 항목3
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * 추가 속성 항목3
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * 추가 속성 항목3
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute3(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      attribute3Validate(attribute3);
      this.attribute3 = attribute3;
   }
   void attribute3Validate(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE3_UPPER_LIMIT < 1) {
         try { ATTRIBUTE3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE3_UPPER_LIMIT = 4000; }
      }
      if (attribute3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute3.toString(), ATTRIBUTE3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute3"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute3", this.attribute3, attribute3));
   }

   /**
    * 추가 속성 항목4
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * 추가 속성 항목4
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * 추가 속성 항목4
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute4(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      attribute4Validate(attribute4);
      this.attribute4 = attribute4;
   }
   void attribute4Validate(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE4_UPPER_LIMIT < 1) {
         try { ATTRIBUTE4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE4_UPPER_LIMIT = 4000; }
      }
      if (attribute4 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute4.toString(), ATTRIBUTE4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute4"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute4", this.attribute4, attribute4));
   }

   /**
    * 추가 속성 항목5
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * 추가 속성 항목5
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * 추가 속성 항목5
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute5(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      attribute5Validate(attribute5);
      this.attribute5 = attribute5;
   }
   void attribute5Validate(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE5_UPPER_LIMIT < 1) {
         try { ATTRIBUTE5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE5_UPPER_LIMIT = 4000; }
      }
      if (attribute5 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute5.toString(), ATTRIBUTE5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute5"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute5", this.attribute5, attribute5));
   }

   /**
    * 추가 속성 항목6
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * 추가 속성 항목6
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * 추가 속성 항목6
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute6(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      attribute6Validate(attribute6);
      this.attribute6 = attribute6;
   }
   void attribute6Validate(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE6_UPPER_LIMIT < 1) {
         try { ATTRIBUTE6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE6_UPPER_LIMIT = 4000; }
      }
      if (attribute6 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute6.toString(), ATTRIBUTE6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute6"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute6", this.attribute6, attribute6));
   }

   /**
    * 추가 속성 항목7
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * 추가 속성 항목7
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * 추가 속성 항목7
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute7(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      attribute7Validate(attribute7);
      this.attribute7 = attribute7;
   }
   void attribute7Validate(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE7_UPPER_LIMIT < 1) {
         try { ATTRIBUTE7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE7_UPPER_LIMIT = 4000; }
      }
      if (attribute7 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute7.toString(), ATTRIBUTE7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute7"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute7", this.attribute7, attribute7));
   }

   /**
    * 추가 속성 항목8
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * 추가 속성 항목8
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * 추가 속성 항목8
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute8(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      attribute8Validate(attribute8);
      this.attribute8 = attribute8;
   }
   void attribute8Validate(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE8_UPPER_LIMIT < 1) {
         try { ATTRIBUTE8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE8_UPPER_LIMIT = 4000; }
      }
      if (attribute8 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute8.toString(), ATTRIBUTE8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute8"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute8", this.attribute8, attribute8));
   }

   /**
    * 추가 속성 항목9
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * 추가 속성 항목9
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * 추가 속성 항목9
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute9(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      attribute9Validate(attribute9);
      this.attribute9 = attribute9;
   }
   void attribute9Validate(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE9_UPPER_LIMIT < 1) {
         try { ATTRIBUTE9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE9_UPPER_LIMIT = 4000; }
      }
      if (attribute9 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute9.toString(), ATTRIBUTE9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute9"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute9", this.attribute9, attribute9));
   }

   /**
    * 추가 속성 항목10
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * 추가 속성 항목10
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * 추가 속성 항목10
    *
    * @see e3ps.ews.entity.KETEarlyWarningTarget
    */
   public void setAttribute10(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      attribute10Validate(attribute10);
      this.attribute10 = attribute10;
   }
   void attribute10Validate(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE10_UPPER_LIMIT < 1) {
         try { ATTRIBUTE10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE10_UPPER_LIMIT = 4000; }
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

   public static final long EXTERNALIZATION_VERSION_UID = -4713775711996348744L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

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
      output.writeObject( productNo );
      output.writeObject( targetCusError );
      output.writeObject( targetFacility );
      output.writeObject( targetGood );
      output.writeObject( targetPerform );
      output.writeObject( targetWorkError );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ews.entity.KETEarlyWarningTarget) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
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
      output.setString( "productNo", productNo );
      output.setDoubleObject( "targetCusError", targetCusError );
      output.setDoubleObject( "targetFacility", targetFacility );
      output.setDoubleObject( "targetGood", targetGood );
      output.setDoubleObject( "targetPerform", targetPerform );
      output.setDoubleObject( "targetWorkError", targetWorkError );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
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
      productNo = input.getString( "productNo" );
      targetCusError = input.getDoubleObject( "targetCusError" );
      targetFacility = input.getDoubleObject( "targetFacility" );
      targetGood = input.getDoubleObject( "targetGood" );
      targetPerform = input.getDoubleObject( "targetPerform" );
      targetWorkError = input.getDoubleObject( "targetWorkError" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_4713775711996348744L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
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
      productNo = (java.lang.String) input.readObject();
      targetCusError = (java.lang.Double) input.readObject();
      targetFacility = (java.lang.Double) input.readObject();
      targetGood = (java.lang.Double) input.readObject();
      targetPerform = (java.lang.Double) input.readObject();
      targetWorkError = (java.lang.Double) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETEarlyWarningTarget thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4713775711996348744L( input, readSerialVersionUID, superDone );
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
