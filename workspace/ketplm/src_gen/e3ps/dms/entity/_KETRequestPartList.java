package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETRequestPartList implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETRequestPartList.class.getName();

   /**
    * 제품명
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * 제품명
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * 제품명
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setPartName(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      partNameValidate(partName);
      this.partName = partName;
   }
   void partNameValidate(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      if (PART_NAME_UPPER_LIMIT < 1) {
         try { PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NAME_UPPER_LIMIT = 200; }
      }
      if (partName != null && !wt.fc.PersistenceHelper.checkStoredLength(partName.toString(), PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partName"), java.lang.String.valueOf(java.lang.Math.min(PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partName", this.partName, partName));
   }

   /**
    * 적용부위
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String APPLIED_REGION = "appliedRegion";
   static int APPLIED_REGION_UPPER_LIMIT = -1;
   java.lang.String appliedRegion;
   /**
    * 적용부위
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAppliedRegion() {
      return appliedRegion;
   }
   /**
    * 적용부위
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setAppliedRegion(java.lang.String appliedRegion) throws wt.util.WTPropertyVetoException {
      appliedRegionValidate(appliedRegion);
      this.appliedRegion = appliedRegion;
   }
   void appliedRegionValidate(java.lang.String appliedRegion) throws wt.util.WTPropertyVetoException {
      if (APPLIED_REGION_UPPER_LIMIT < 1) {
         try { APPLIED_REGION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("appliedRegion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPLIED_REGION_UPPER_LIMIT = 200; }
      }
      if (appliedRegion != null && !wt.fc.PersistenceHelper.checkStoredLength(appliedRegion.toString(), APPLIED_REGION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "appliedRegion"), java.lang.String.valueOf(java.lang.Math.min(APPLIED_REGION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "appliedRegion", this.appliedRegion, appliedRegion));
   }

   /**
    * 현적용품
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String CURRENT_APPLYED_PART = "currentApplyedPart";
   static int CURRENT_APPLYED_PART_UPPER_LIMIT = -1;
   java.lang.String currentApplyedPart;
   /**
    * 현적용품
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getCurrentApplyedPart() {
      return currentApplyedPart;
   }
   /**
    * 현적용품
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setCurrentApplyedPart(java.lang.String currentApplyedPart) throws wt.util.WTPropertyVetoException {
      currentApplyedPartValidate(currentApplyedPart);
      this.currentApplyedPart = currentApplyedPart;
   }
   void currentApplyedPartValidate(java.lang.String currentApplyedPart) throws wt.util.WTPropertyVetoException {
      if (CURRENT_APPLYED_PART_UPPER_LIMIT < 1) {
         try { CURRENT_APPLYED_PART_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("currentApplyedPart").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CURRENT_APPLYED_PART_UPPER_LIMIT = 200; }
      }
      if (currentApplyedPart != null && !wt.fc.PersistenceHelper.checkStoredLength(currentApplyedPart.toString(), CURRENT_APPLYED_PART_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "currentApplyedPart"), java.lang.String.valueOf(java.lang.Math.min(CURRENT_APPLYED_PART_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "currentApplyedPart", this.currentApplyedPart, currentApplyedPart));
   }

   /**
    * 포장사양코드
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String PACK_TYPE_CODE = "packTypeCode";
   static int PACK_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String packTypeCode;
   /**
    * 포장사양코드
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getPackTypeCode() {
      return packTypeCode;
   }
   /**
    * 포장사양코드
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setPackTypeCode(java.lang.String packTypeCode) throws wt.util.WTPropertyVetoException {
      packTypeCodeValidate(packTypeCode);
      this.packTypeCode = packTypeCode;
   }
   void packTypeCodeValidate(java.lang.String packTypeCode) throws wt.util.WTPropertyVetoException {
      if (PACK_TYPE_CODE_UPPER_LIMIT < 1) {
         try { PACK_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (packTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(packTypeCode.toString(), PACK_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packTypeCode"), java.lang.String.valueOf(java.lang.Math.min(PACK_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packTypeCode", this.packTypeCode, packTypeCode));
   }

   /**
    * 납입처
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String SUMMIT_DESTINATION = "summitDestination";
   static int SUMMIT_DESTINATION_UPPER_LIMIT = -1;
   java.lang.String summitDestination;
   /**
    * 납입처
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getSummitDestination() {
      return summitDestination;
   }
   /**
    * 납입처
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setSummitDestination(java.lang.String summitDestination) throws wt.util.WTPropertyVetoException {
      summitDestinationValidate(summitDestination);
      this.summitDestination = summitDestination;
   }
   void summitDestinationValidate(java.lang.String summitDestination) throws wt.util.WTPropertyVetoException {
      if (SUMMIT_DESTINATION_UPPER_LIMIT < 1) {
         try { SUMMIT_DESTINATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("summitDestination").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUMMIT_DESTINATION_UPPER_LIMIT = 200; }
      }
      if (summitDestination != null && !wt.fc.PersistenceHelper.checkStoredLength(summitDestination.toString(), SUMMIT_DESTINATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "summitDestination"), java.lang.String.valueOf(java.lang.Math.min(SUMMIT_DESTINATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "summitDestination", this.summitDestination, summitDestination));
   }

   /**
    * 고객사인정예상가
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String BUYER_ACCEPT_PRICE = "buyerAcceptPrice";
   static int BUYER_ACCEPT_PRICE_UPPER_LIMIT = -1;
   java.lang.String buyerAcceptPrice;
   /**
    * 고객사인정예상가
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getBuyerAcceptPrice() {
      return buyerAcceptPrice;
   }
   /**
    * 고객사인정예상가
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setBuyerAcceptPrice(java.lang.String buyerAcceptPrice) throws wt.util.WTPropertyVetoException {
      buyerAcceptPriceValidate(buyerAcceptPrice);
      this.buyerAcceptPrice = buyerAcceptPrice;
   }
   void buyerAcceptPriceValidate(java.lang.String buyerAcceptPrice) throws wt.util.WTPropertyVetoException {
      if (BUYER_ACCEPT_PRICE_UPPER_LIMIT < 1) {
         try { BUYER_ACCEPT_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("buyerAcceptPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BUYER_ACCEPT_PRICE_UPPER_LIMIT = 200; }
      }
      if (buyerAcceptPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(buyerAcceptPrice.toString(), BUYER_ACCEPT_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "buyerAcceptPrice"), java.lang.String.valueOf(java.lang.Math.min(BUYER_ACCEPT_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "buyerAcceptPrice", this.buyerAcceptPrice, buyerAcceptPrice));
   }

   /**
    * 판매목표가
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String KET_TARGET_PRICE = "ketTargetPrice";
   java.lang.Double ketTargetPrice;
   /**
    * 판매목표가
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.Double getKetTargetPrice() {
      return ketTargetPrice;
   }
   /**
    * 판매목표가
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setKetTargetPrice(java.lang.Double ketTargetPrice) throws wt.util.WTPropertyVetoException {
      ketTargetPriceValidate(ketTargetPrice);
      this.ketTargetPrice = ketTargetPrice;
   }
   void ketTargetPriceValidate(java.lang.Double ketTargetPrice) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 목표수익율-초도
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String TARGET_EARNING_RATE = "targetEarningRate";
   java.lang.Double targetEarningRate;
   /**
    * 목표수익율-초도
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.Double getTargetEarningRate() {
      return targetEarningRate;
   }
   /**
    * 목표수익율-초도
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setTargetEarningRate(java.lang.Double targetEarningRate) throws wt.util.WTPropertyVetoException {
      targetEarningRateValidate(targetEarningRate);
      this.targetEarningRate = targetEarningRate;
   }
   void targetEarningRateValidate(java.lang.Double targetEarningRate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 목표수익율-평균
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String TARGET_AVERAGE_RATE = "targetAverageRate";
   java.lang.Double targetAverageRate;
   /**
    * 목표수익율-평균
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.Double getTargetAverageRate() {
      return targetAverageRate;
   }
   /**
    * 목표수익율-평균
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setTargetAverageRate(java.lang.Double targetAverageRate) throws wt.util.WTPropertyVetoException {
      targetAverageRateValidate(targetAverageRate);
      this.targetAverageRate = targetAverageRate;
   }
   void targetAverageRateValidate(java.lang.Double targetAverageRate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 기간수익률
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String TARGET_TERM_RATE = "targetTermRate";
   java.lang.Double targetTermRate;
   /**
    * 기간수익률
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.Double getTargetTermRate() {
      return targetTermRate;
   }
   /**
    * 기간수익률
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setTargetTermRate(java.lang.Double targetTermRate) throws wt.util.WTPropertyVetoException {
      targetTermRateValidate(targetTermRate);
      this.targetTermRate = targetTermRate;
   }
   void targetTermRateValidate(java.lang.Double targetTermRate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 목표투자비
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String TARGET_INVESTMENT_COST = "targetInvestmentCost";
   java.lang.Double targetInvestmentCost;
   /**
    * 목표투자비
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.Double getTargetInvestmentCost() {
      return targetInvestmentCost;
   }
   /**
    * 목표투자비
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setTargetInvestmentCost(java.lang.Double targetInvestmentCost) throws wt.util.WTPropertyVetoException {
      targetInvestmentCostValidate(targetInvestmentCost);
      this.targetInvestmentCost = targetInvestmentCost;
   }
   void targetInvestmentCostValidate(java.lang.Double targetInvestmentCost) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 기타차종여부
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String IS_ETC_CAR_TYPE = "isEtcCarType";
   static int IS_ETC_CAR_TYPE_UPPER_LIMIT = -1;
   java.lang.String isEtcCarType;
   /**
    * 기타차종여부
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getIsEtcCarType() {
      return isEtcCarType;
   }
   /**
    * 기타차종여부
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public void setIsEtcCarType(java.lang.String isEtcCarType) throws wt.util.WTPropertyVetoException {
      isEtcCarTypeValidate(isEtcCarType);
      this.isEtcCarType = isEtcCarType;
   }
   void isEtcCarTypeValidate(java.lang.String isEtcCarType) throws wt.util.WTPropertyVetoException {
      if (IS_ETC_CAR_TYPE_UPPER_LIMIT < 1) {
         try { IS_ETC_CAR_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("isEtcCarType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IS_ETC_CAR_TYPE_UPPER_LIMIT = 200; }
      }
      if (isEtcCarType != null && !wt.fc.PersistenceHelper.checkStoredLength(isEtcCarType.toString(), IS_ETC_CAR_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "isEtcCarType"), java.lang.String.valueOf(java.lang.Math.min(IS_ETC_CAR_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "isEtcCarType", this.isEtcCarType, isEtcCarType));
   }

   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETRequestPartList
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
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETRequestPartList
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETRequestPartList
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

   public static final long EXTERNALIZATION_VERSION_UID = 2812537858907955623L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( appliedRegion );
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
      output.writeObject( buyerAcceptPrice );
      output.writeObject( currentApplyedPart );
      output.writeObject( isEtcCarType );
      output.writeObject( ketTargetPrice );
      output.writeObject( packTypeCode );
      output.writeObject( partName );
      output.writeObject( summitDestination );
      output.writeObject( targetAverageRate );
      output.writeObject( targetEarningRate );
      output.writeObject( targetInvestmentCost );
      output.writeObject( targetTermRate );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETRequestPartList) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "appliedRegion", appliedRegion );
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
      output.setString( "buyerAcceptPrice", buyerAcceptPrice );
      output.setString( "currentApplyedPart", currentApplyedPart );
      output.setString( "isEtcCarType", isEtcCarType );
      output.setDoubleObject( "ketTargetPrice", ketTargetPrice );
      output.setString( "packTypeCode", packTypeCode );
      output.setString( "partName", partName );
      output.setString( "summitDestination", summitDestination );
      output.setDoubleObject( "targetAverageRate", targetAverageRate );
      output.setDoubleObject( "targetEarningRate", targetEarningRate );
      output.setDoubleObject( "targetInvestmentCost", targetInvestmentCost );
      output.setDoubleObject( "targetTermRate", targetTermRate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      appliedRegion = input.getString( "appliedRegion" );
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
      buyerAcceptPrice = input.getString( "buyerAcceptPrice" );
      currentApplyedPart = input.getString( "currentApplyedPart" );
      isEtcCarType = input.getString( "isEtcCarType" );
      ketTargetPrice = input.getDoubleObject( "ketTargetPrice" );
      packTypeCode = input.getString( "packTypeCode" );
      partName = input.getString( "partName" );
      summitDestination = input.getString( "summitDestination" );
      targetAverageRate = input.getDoubleObject( "targetAverageRate" );
      targetEarningRate = input.getDoubleObject( "targetEarningRate" );
      targetInvestmentCost = input.getDoubleObject( "targetInvestmentCost" );
      targetTermRate = input.getDoubleObject( "targetTermRate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion2812537858907955623L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      appliedRegion = (java.lang.String) input.readObject();
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
      buyerAcceptPrice = (java.lang.String) input.readObject();
      currentApplyedPart = (java.lang.String) input.readObject();
      isEtcCarType = (java.lang.String) input.readObject();
      ketTargetPrice = (java.lang.Double) input.readObject();
      packTypeCode = (java.lang.String) input.readObject();
      partName = (java.lang.String) input.readObject();
      summitDestination = (java.lang.String) input.readObject();
      targetAverageRate = (java.lang.Double) input.readObject();
      targetEarningRate = (java.lang.Double) input.readObject();
      targetInvestmentCost = (java.lang.Double) input.readObject();
      targetTermRate = (java.lang.Double) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETRequestPartList thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2812537858907955623L( input, readSerialVersionUID, superDone );
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
