package ext.ket.project.trycondition.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETTryCondition extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.trycondition.entity.entityResource";
   static final java.lang.String CLASSNAME = KETTryCondition.class.getName();

   /**
    * Try NO
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String TRY_NO = "tryNo";
   static int TRY_NO_UPPER_LIMIT = -1;
   java.lang.String tryNo;
   /**
    * Try NO
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getTryNo() {
      return tryNo;
   }
   /**
    * Try NO
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setTryNo(java.lang.String tryNo) throws wt.util.WTPropertyVetoException {
      tryNoValidate(tryNo);
      this.tryNo = tryNo;
   }
   void tryNoValidate(java.lang.String tryNo) throws wt.util.WTPropertyVetoException {
      if (TRY_NO_UPPER_LIMIT < 1) {
         try { TRY_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tryNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRY_NO_UPPER_LIMIT = 200; }
      }
      if (tryNo != null && !wt.fc.PersistenceHelper.checkStoredLength(tryNo.toString(), TRY_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tryNo"), java.lang.String.valueOf(java.lang.Math.min(TRY_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tryNo", this.tryNo, tryNo));
   }

   /**
    * subVer
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String SUB_VER = "subVer";
   int subVer = 0;
   /**
    * subVer
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public int getSubVer() {
      return subVer;
   }
   /**
    * subVer
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setSubVer(int subVer) throws wt.util.WTPropertyVetoException {
      subVerValidate(subVer);
      this.subVer = subVer;
   }
   void subVerValidate(int subVer) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Try일자
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String TRY_DATE = "tryDate";
   java.sql.Timestamp tryDate;
   /**
    * Try일자
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.sql.Timestamp getTryDate() {
      return tryDate;
   }
   /**
    * Try일자
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setTryDate(java.sql.Timestamp tryDate) throws wt.util.WTPropertyVetoException {
      tryDateValidate(tryDate);
      this.tryDate = tryDate;
   }
   void tryDateValidate(java.sql.Timestamp tryDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * try 장소
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String TRY_PLACE = "tryPlace";
   static int TRY_PLACE_UPPER_LIMIT = -1;
   java.lang.String tryPlace;
   /**
    * try 장소
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getTryPlace() {
      return tryPlace;
   }
   /**
    * try 장소
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setTryPlace(java.lang.String tryPlace) throws wt.util.WTPropertyVetoException {
      tryPlaceValidate(tryPlace);
      this.tryPlace = tryPlace;
   }
   void tryPlaceValidate(java.lang.String tryPlace) throws wt.util.WTPropertyVetoException {
      if (TRY_PLACE_UPPER_LIMIT < 1) {
         try { TRY_PLACE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tryPlace").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRY_PLACE_UPPER_LIMIT = 200; }
      }
      if (tryPlace != null && !wt.fc.PersistenceHelper.checkStoredLength(tryPlace.toString(), TRY_PLACE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tryPlace"), java.lang.String.valueOf(java.lang.Math.min(TRY_PLACE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tryPlace", this.tryPlace, tryPlace));
   }

   /**
    * dieNo
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String DIE_NO = "dieNo";
   static int DIE_NO_UPPER_LIMIT = -1;
   java.lang.String dieNo;
   /**
    * dieNo
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getDieNo() {
      return dieNo;
   }
   /**
    * dieNo
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setDieNo(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      dieNoValidate(dieNo);
      this.dieNo = dieNo;
   }
   void dieNoValidate(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      if (DIE_NO_UPPER_LIMIT < 1) {
         try { DIE_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dieNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIE_NO_UPPER_LIMIT = 200; }
      }
      if (dieNo != null && !wt.fc.PersistenceHelper.checkStoredLength(dieNo.toString(), DIE_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dieNo"), java.lang.String.valueOf(java.lang.Math.min(DIE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dieNo", this.dieNo, dieNo));
   }

   /**
    * Part No
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * Part No
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * Part No
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
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
    * Part Name
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * Part Name
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * Part Name
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
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
    * 금형 난이도
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String MOLD_RANK = "moldRank";
   static int MOLD_RANK_UPPER_LIMIT = -1;
   java.lang.String moldRank;
   /**
    * 금형 난이도
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getMoldRank() {
      return moldRank;
   }
   /**
    * 금형 난이도
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setMoldRank(java.lang.String moldRank) throws wt.util.WTPropertyVetoException {
      moldRankValidate(moldRank);
      this.moldRank = moldRank;
   }
   void moldRankValidate(java.lang.String moldRank) throws wt.util.WTPropertyVetoException {
      if (MOLD_RANK_UPPER_LIMIT < 1) {
         try { MOLD_RANK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldRank").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_RANK_UPPER_LIMIT = 200; }
      }
      if (moldRank != null && !wt.fc.PersistenceHelper.checkStoredLength(moldRank.toString(), MOLD_RANK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldRank"), java.lang.String.valueOf(java.lang.Math.min(MOLD_RANK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldRank", this.moldRank, moldRank));
   }

   /**
    * 참석자
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String ATTENDANT = "attendant";
   static int ATTENDANT_UPPER_LIMIT = -1;
   java.lang.String attendant;
   /**
    * 참석자
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getAttendant() {
      return attendant;
   }
   /**
    * 참석자
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setAttendant(java.lang.String attendant) throws wt.util.WTPropertyVetoException {
      attendantValidate(attendant);
      this.attendant = attendant;
   }
   void attendantValidate(java.lang.String attendant) throws wt.util.WTPropertyVetoException {
      if (ATTENDANT_UPPER_LIMIT < 1) {
         try { ATTENDANT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attendant").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTENDANT_UPPER_LIMIT = 200; }
      }
      if (attendant != null && !wt.fc.PersistenceHelper.checkStoredLength(attendant.toString(), ATTENDANT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attendant"), java.lang.String.valueOf(java.lang.Math.min(ATTENDANT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attendant", this.attendant, attendant));
   }

   /**
    * 디버깅사유
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String DEBUG_REASON = "debugReason";
   static int DEBUG_REASON_UPPER_LIMIT = -1;
   java.lang.String debugReason;
   /**
    * 디버깅사유
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getDebugReason() {
      return debugReason;
   }
   /**
    * 디버깅사유
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setDebugReason(java.lang.String debugReason) throws wt.util.WTPropertyVetoException {
      debugReasonValidate(debugReason);
      this.debugReason = debugReason;
   }
   void debugReasonValidate(java.lang.String debugReason) throws wt.util.WTPropertyVetoException {
      if (DEBUG_REASON_UPPER_LIMIT < 1) {
         try { DEBUG_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("debugReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEBUG_REASON_UPPER_LIMIT = 200; }
      }
      if (debugReason != null && !wt.fc.PersistenceHelper.checkStoredLength(debugReason.toString(), DEBUG_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "debugReason"), java.lang.String.valueOf(java.lang.Math.min(DEBUG_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "debugReason", this.debugReason, debugReason));
   }

   /**
    * 특이사항
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String DETAIL = "detail";
   static int DETAIL_UPPER_LIMIT = -1;
   java.lang.String detail;
   /**
    * 특이사항
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getDetail() {
      return detail;
   }
   /**
    * 특이사항
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setDetail(java.lang.String detail) throws wt.util.WTPropertyVetoException {
      detailValidate(detail);
      this.detail = detail;
   }
   void detailValidate(java.lang.String detail) throws wt.util.WTPropertyVetoException {
      if (DETAIL_UPPER_LIMIT < 1) {
         try { DETAIL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("detail").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DETAIL_UPPER_LIMIT = 200; }
      }
      if (detail != null && !wt.fc.PersistenceHelper.checkStoredLength(detail.toString(), DETAIL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "detail"), java.lang.String.valueOf(java.lang.Math.min(DETAIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "detail", this.detail, detail));
   }

   /**
    * 제품설계(제품도출도  Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String PRODUCT_DESIGN_ROLE = "productDesignRole";
   static int PRODUCT_DESIGN_ROLE_UPPER_LIMIT = -1;
   java.lang.String productDesignRole;
   /**
    * 제품설계(제품도출도  Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getProductDesignRole() {
      return productDesignRole;
   }
   /**
    * 제품설계(제품도출도  Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setProductDesignRole(java.lang.String productDesignRole) throws wt.util.WTPropertyVetoException {
      productDesignRoleValidate(productDesignRole);
      this.productDesignRole = productDesignRole;
   }
   void productDesignRoleValidate(java.lang.String productDesignRole) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_DESIGN_ROLE_UPPER_LIMIT < 1) {
         try { PRODUCT_DESIGN_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productDesignRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_DESIGN_ROLE_UPPER_LIMIT = 200; }
      }
      if (productDesignRole != null && !wt.fc.PersistenceHelper.checkStoredLength(productDesignRole.toString(), PRODUCT_DESIGN_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productDesignRole"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_DESIGN_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productDesignRole", this.productDesignRole, productDesignRole));
   }

   /**
    * 금형제작(금형 프로젝트 정보의 제작구분/제작처 표시)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String MOLD_MAKE = "moldMake";
   static int MOLD_MAKE_UPPER_LIMIT = -1;
   java.lang.String moldMake;
   /**
    * 금형제작(금형 프로젝트 정보의 제작구분/제작처 표시)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getMoldMake() {
      return moldMake;
   }
   /**
    * 금형제작(금형 프로젝트 정보의 제작구분/제작처 표시)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setMoldMake(java.lang.String moldMake) throws wt.util.WTPropertyVetoException {
      moldMakeValidate(moldMake);
      this.moldMake = moldMake;
   }
   void moldMakeValidate(java.lang.String moldMake) throws wt.util.WTPropertyVetoException {
      if (MOLD_MAKE_UPPER_LIMIT < 1) {
         try { MOLD_MAKE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMake").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MAKE_UPPER_LIMIT = 200; }
      }
      if (moldMake != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMake.toString(), MOLD_MAKE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMake"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MAKE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMake", this.moldMake, moldMake));
   }

   /**
    * 금형설계(금형설계 Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String MOLD_DESIGN_ROLE = "moldDesignRole";
   static int MOLD_DESIGN_ROLE_UPPER_LIMIT = -1;
   java.lang.String moldDesignRole;
   /**
    * 금형설계(금형설계 Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getMoldDesignRole() {
      return moldDesignRole;
   }
   /**
    * 금형설계(금형설계 Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setMoldDesignRole(java.lang.String moldDesignRole) throws wt.util.WTPropertyVetoException {
      moldDesignRoleValidate(moldDesignRole);
      this.moldDesignRole = moldDesignRole;
   }
   void moldDesignRoleValidate(java.lang.String moldDesignRole) throws wt.util.WTPropertyVetoException {
      if (MOLD_DESIGN_ROLE_UPPER_LIMIT < 1) {
         try { MOLD_DESIGN_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldDesignRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_DESIGN_ROLE_UPPER_LIMIT = 200; }
      }
      if (moldDesignRole != null && !wt.fc.PersistenceHelper.checkStoredLength(moldDesignRole.toString(), MOLD_DESIGN_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldDesignRole"), java.lang.String.valueOf(java.lang.Math.min(MOLD_DESIGN_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldDesignRole", this.moldDesignRole, moldDesignRole));
   }

   /**
    * 금형Try(금형Try Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String MOLD_TRY_ROLE = "moldTryRole";
   static int MOLD_TRY_ROLE_UPPER_LIMIT = -1;
   java.lang.String moldTryRole;
   /**
    * 금형Try(금형Try Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public java.lang.String getMoldTryRole() {
      return moldTryRole;
   }
   /**
    * 금형Try(금형Try Task 책임자)
    *
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setMoldTryRole(java.lang.String moldTryRole) throws wt.util.WTPropertyVetoException {
      moldTryRoleValidate(moldTryRole);
      this.moldTryRole = moldTryRole;
   }
   void moldTryRoleValidate(java.lang.String moldTryRole) throws wt.util.WTPropertyVetoException {
      if (MOLD_TRY_ROLE_UPPER_LIMIT < 1) {
         try { MOLD_TRY_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldTryRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_TRY_ROLE_UPPER_LIMIT = 200; }
      }
      if (moldTryRole != null && !wt.fc.PersistenceHelper.checkStoredLength(moldTryRole.toString(), MOLD_TRY_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldTryRole"), java.lang.String.valueOf(java.lang.Math.min(MOLD_TRY_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldTryRole", this.moldTryRole, moldTryRole));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String MOLD_PROJECT = "moldProject";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public static final java.lang.String MOLD_PROJECT_REFERENCE = "moldProjectReference";
   wt.fc.ObjectReference moldProjectReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public e3ps.project.MoldProject getMoldProject() {
      return (moldProjectReference != null) ? (e3ps.project.MoldProject) moldProjectReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public wt.fc.ObjectReference getMoldProjectReference() {
      return moldProjectReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setMoldProject(e3ps.project.MoldProject the_moldProject) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldProjectReference(the_moldProject == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.MoldProject) the_moldProject));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryCondition
    */
   public void setMoldProjectReference(wt.fc.ObjectReference the_moldProjectReference) throws wt.util.WTPropertyVetoException {
      moldProjectReferenceValidate(the_moldProjectReference);
      moldProjectReference = (wt.fc.ObjectReference) the_moldProjectReference;
   }
   void moldProjectReferenceValidate(wt.fc.ObjectReference the_moldProjectReference) throws wt.util.WTPropertyVetoException {
      if (the_moldProjectReference == null || the_moldProjectReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldProjectReference") },
               new java.beans.PropertyChangeEvent(this, "moldProjectReference", this.moldProjectReference, moldProjectReference));
      if (the_moldProjectReference != null && the_moldProjectReference.getReferencedClass() != null &&
            !e3ps.project.MoldProject.class.isAssignableFrom(the_moldProjectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldProjectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldProjectReference", this.moldProjectReference, moldProjectReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -294532020129681216L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( attendant );
      output.writeObject( debugReason );
      output.writeObject( detail );
      output.writeObject( dieNo );
      output.writeObject( moldDesignRole );
      output.writeObject( moldMake );
      output.writeObject( moldProjectReference );
      output.writeObject( moldRank );
      output.writeObject( moldTryRole );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( productDesignRole );
      output.writeInt( subVer );
      output.writeObject( tryDate );
      output.writeObject( tryNo );
      output.writeObject( tryPlace );
   }

   protected void super_writeExternal_KETTryCondition(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.trycondition.entity.KETTryCondition) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETTryCondition(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "attendant", attendant );
      output.setString( "debugReason", debugReason );
      output.setString( "detail", detail );
      output.setString( "dieNo", dieNo );
      output.setString( "moldDesignRole", moldDesignRole );
      output.setString( "moldMake", moldMake );
      output.writeObject( "moldProjectReference", moldProjectReference, wt.fc.ObjectReference.class, true );
      output.setString( "moldRank", moldRank );
      output.setString( "moldTryRole", moldTryRole );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "productDesignRole", productDesignRole );
      output.setInt( "subVer", subVer );
      output.setTimestamp( "tryDate", tryDate );
      output.setString( "tryNo", tryNo );
      output.setString( "tryPlace", tryPlace );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      attendant = input.getString( "attendant" );
      debugReason = input.getString( "debugReason" );
      detail = input.getString( "detail" );
      dieNo = input.getString( "dieNo" );
      moldDesignRole = input.getString( "moldDesignRole" );
      moldMake = input.getString( "moldMake" );
      moldProjectReference = (wt.fc.ObjectReference) input.readObject( "moldProjectReference", moldProjectReference, wt.fc.ObjectReference.class, true );
      moldRank = input.getString( "moldRank" );
      moldTryRole = input.getString( "moldTryRole" );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      productDesignRole = input.getString( "productDesignRole" );
      subVer = input.getInt( "subVer" );
      tryDate = input.getTimestamp( "tryDate" );
      tryNo = input.getString( "tryNo" );
      tryPlace = input.getString( "tryPlace" );
   }

   boolean readVersion_294532020129681216L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      attendant = (java.lang.String) input.readObject();
      debugReason = (java.lang.String) input.readObject();
      detail = (java.lang.String) input.readObject();
      dieNo = (java.lang.String) input.readObject();
      moldDesignRole = (java.lang.String) input.readObject();
      moldMake = (java.lang.String) input.readObject();
      moldProjectReference = (wt.fc.ObjectReference) input.readObject();
      moldRank = (java.lang.String) input.readObject();
      moldTryRole = (java.lang.String) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      productDesignRole = (java.lang.String) input.readObject();
      subVer = input.readInt();
      tryDate = (java.sql.Timestamp) input.readObject();
      tryNo = (java.lang.String) input.readObject();
      tryPlace = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETTryCondition thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_294532020129681216L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETTryCondition( _KETTryCondition thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
