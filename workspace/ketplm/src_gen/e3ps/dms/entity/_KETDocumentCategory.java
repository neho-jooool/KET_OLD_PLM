package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDocumentCategory implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDocumentCategory.class.getName();

   /**
    * 분류식별자
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String CATEGORY_CODE = "categoryCode";
   static int CATEGORY_CODE_UPPER_LIMIT = -1;
   java.lang.String categoryCode;
   /**
    * 분류식별자
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getCategoryCode() {
      return categoryCode;
   }
   /**
    * 분류식별자
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setCategoryCode(java.lang.String categoryCode) throws wt.util.WTPropertyVetoException {
      categoryCodeValidate(categoryCode);
      this.categoryCode = categoryCode;
   }
   void categoryCodeValidate(java.lang.String categoryCode) throws wt.util.WTPropertyVetoException {
      if (CATEGORY_CODE_UPPER_LIMIT < 1) {
         try { CATEGORY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("categoryCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CATEGORY_CODE_UPPER_LIMIT = 200; }
      }
      if (categoryCode != null && !wt.fc.PersistenceHelper.checkStoredLength(categoryCode.toString(), CATEGORY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "categoryCode"), java.lang.String.valueOf(java.lang.Math.min(CATEGORY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "categoryCode", this.categoryCode, categoryCode));
   }

   /**
    * 분류 이름
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String CATEGORY_NAME = "categoryName";
   static int CATEGORY_NAME_UPPER_LIMIT = -1;
   java.lang.String categoryName;
   /**
    * 분류 이름
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getCategoryName() {
      return categoryName;
   }
   /**
    * 분류 이름
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setCategoryName(java.lang.String categoryName) throws wt.util.WTPropertyVetoException {
      categoryNameValidate(categoryName);
      this.categoryName = categoryName;
   }
   void categoryNameValidate(java.lang.String categoryName) throws wt.util.WTPropertyVetoException {
      if (CATEGORY_NAME_UPPER_LIMIT < 1) {
         try { CATEGORY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("categoryName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CATEGORY_NAME_UPPER_LIMIT = 200; }
      }
      if (categoryName != null && !wt.fc.PersistenceHelper.checkStoredLength(categoryName.toString(), CATEGORY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "categoryName"), java.lang.String.valueOf(java.lang.Math.min(CATEGORY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "categoryName", this.categoryName, categoryName));
   }

   /**
    * 문서번호 채번 시 앞 2자리 구분자로 사용
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String DOC_TYPE_CODE = "docTypeCode";
   static int DOC_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String docTypeCode;
   /**
    * 문서번호 채번 시 앞 2자리 구분자로 사용
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getDocTypeCode() {
      return docTypeCode;
   }
   /**
    * 문서번호 채번 시 앞 2자리 구분자로 사용
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setDocTypeCode(java.lang.String docTypeCode) throws wt.util.WTPropertyVetoException {
      docTypeCodeValidate(docTypeCode);
      this.docTypeCode = docTypeCode;
   }
   void docTypeCodeValidate(java.lang.String docTypeCode) throws wt.util.WTPropertyVetoException {
      if (DOC_TYPE_CODE_UPPER_LIMIT < 1) {
         try { DOC_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("docTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOC_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (docTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(docTypeCode.toString(), DOC_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "docTypeCode"), java.lang.String.valueOf(java.lang.Math.min(DOC_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "docTypeCode", this.docTypeCode, docTypeCode));
   }

   /**
    * 분류체계 정렬순서
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String SORT_NO = "sortNo";
   int sortNo;
   /**
    * 분류체계 정렬순서
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public int getSortNo() {
      return sortNo;
   }
   /**
    * 분류체계 정렬순서
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setSortNo(int sortNo) throws wt.util.WTPropertyVetoException {
      sortNoValidate(sortNo);
      this.sortNo = sortNo;
   }
   void sortNoValidate(int sortNo) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 분류체계 레벨
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String CATEGORY_LEVEL = "categoryLevel";
   int categoryLevel;
   /**
    * 분류체계 레벨
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public int getCategoryLevel() {
      return categoryLevel;
   }
   /**
    * 분류체계 레벨
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setCategoryLevel(int categoryLevel) throws wt.util.WTPropertyVetoException {
      categoryLevelValidate(categoryLevel);
      this.categoryLevel = categoryLevel;
   }
   void categoryLevelValidate(int categoryLevel) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 상위분류 식별자
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String PARENT_CATEGORY_CODE = "parentCategoryCode";
   static int PARENT_CATEGORY_CODE_UPPER_LIMIT = -1;
   java.lang.String parentCategoryCode;
   /**
    * 상위분류 식별자
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getParentCategoryCode() {
      return parentCategoryCode;
   }
   /**
    * 상위분류 식별자
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setParentCategoryCode(java.lang.String parentCategoryCode) throws wt.util.WTPropertyVetoException {
      parentCategoryCodeValidate(parentCategoryCode);
      this.parentCategoryCode = parentCategoryCode;
   }
   void parentCategoryCodeValidate(java.lang.String parentCategoryCode) throws wt.util.WTPropertyVetoException {
      if (PARENT_CATEGORY_CODE_UPPER_LIMIT < 1) {
         try { PARENT_CATEGORY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("parentCategoryCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PARENT_CATEGORY_CODE_UPPER_LIMIT = 200; }
      }
      if (parentCategoryCode != null && !wt.fc.PersistenceHelper.checkStoredLength(parentCategoryCode.toString(), PARENT_CATEGORY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentCategoryCode"), java.lang.String.valueOf(java.lang.Math.min(PARENT_CATEGORY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "parentCategoryCode", this.parentCategoryCode, parentCategoryCode));
   }

   /**
    * 사용여부
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_USED = "isUsed";
   java.lang.Boolean isUsed;
   /**
    * 사용여부
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsUsed() {
      return isUsed;
   }
   /**
    * 사용여부
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsUsed(java.lang.Boolean isUsed) throws wt.util.WTPropertyVetoException {
      isUsedValidate(isUsed);
      this.isUsed = isUsed;
   }
   void isUsedValidate(java.lang.Boolean isUsed) throws wt.util.WTPropertyVetoException {
   }

   /**
    * APQP대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_APQP = "isAPQP";
   java.lang.Boolean isAPQP;
   /**
    * APQP대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsAPQP() {
      return isAPQP;
   }
   /**
    * APQP대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsAPQP(java.lang.Boolean isAPQP) throws wt.util.WTPropertyVetoException {
      isAPQPValidate(isAPQP);
      this.isAPQP = isAPQP;
   }
   void isAPQPValidate(java.lang.Boolean isAPQP) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PSP10대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_PSO10 = "isPSO10";
   java.lang.Boolean isPSO10;
   /**
    * PSP10대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsPSO10() {
      return isPSO10;
   }
   /**
    * PSP10대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsPSO10(java.lang.Boolean isPSO10) throws wt.util.WTPropertyVetoException {
      isPSO10Validate(isPSO10);
      this.isPSO10 = isPSO10;
   }
   void isPSO10Validate(java.lang.Boolean isPSO10) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ESIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_ESIR = "isESIR";
   java.lang.Boolean isESIR;
   /**
    * ESIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsESIR() {
      return isESIR;
   }
   /**
    * ESIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsESIR(java.lang.Boolean isESIR) throws wt.util.WTPropertyVetoException {
      isESIRValidate(isESIR);
      this.isESIR = isESIR;
   }
   void isESIRValidate(java.lang.Boolean isESIR) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ISIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_ISIRCAR = "isISIRCar";
   java.lang.Boolean isISIRCar;
   /**
    * ISIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsISIRCar() {
      return isISIRCar;
   }
   /**
    * ISIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsISIRCar(java.lang.Boolean isISIRCar) throws wt.util.WTPropertyVetoException {
      isISIRCarValidate(isISIRCar);
      this.isISIRCar = isISIRCar;
   }
   void isISIRCarValidate(java.lang.Boolean isISIRCar) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ISIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_ISIRELEC = "isISIRElec";
   java.lang.Boolean isISIRElec;
   /**
    * ISIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsISIRElec() {
      return isISIRElec;
   }
   /**
    * ISIR대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsISIRElec(java.lang.Boolean isISIRElec) throws wt.util.WTPropertyVetoException {
      isISIRElecValidate(isISIRElec);
      this.isISIRElec = isISIRElec;
   }
   void isISIRElecValidate(java.lang.Boolean isISIRElec) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ANPQP대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_ANPQP = "isANPQP";
   java.lang.Boolean isANPQP;
   /**
    * ANPQP대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsANPQP() {
      return isANPQP;
   }
   /**
    * ANPQP대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsANPQP(java.lang.Boolean isANPQP) throws wt.util.WTPropertyVetoException {
      isANPQPValidate(isANPQP);
      this.isANPQP = isANPQP;
   }
   void isANPQPValidate(java.lang.Boolean isANPQP) throws wt.util.WTPropertyVetoException {
   }

   /**
    * SYMC대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_SYMC = "isSYMC";
   java.lang.Boolean isSYMC;
   /**
    * SYMC대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsSYMC() {
      return isSYMC;
   }
   /**
    * SYMC대상여부 구분 값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsSYMC(java.lang.Boolean isSYMC) throws wt.util.WTPropertyVetoException {
      isSYMCValidate(isSYMC);
      this.isSYMC = isSYMC;
   }
   void isSYMCValidate(java.lang.Boolean isSYMC) throws wt.util.WTPropertyVetoException {
   }

   /**
    * DR CheckSheet 대상여부 구분값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String IS_DRCHECK_SHEET = "isDRCheckSheet";
   java.lang.Boolean isDRCheckSheet;
   /**
    * DR CheckSheet 대상여부 구분값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.Boolean getIsDRCheckSheet() {
      return isDRCheckSheet;
   }
   /**
    * DR CheckSheet 대상여부 구분값
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setIsDRCheckSheet(java.lang.Boolean isDRCheckSheet) throws wt.util.WTPropertyVetoException {
      isDRCheckSheetValidate(isDRCheckSheet);
      this.isDRCheckSheet = isDRCheckSheet;
   }
   void isDRCheckSheetValidate(java.lang.Boolean isDRCheckSheet) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 추가 속성 항목1
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * 추가 속성 항목1
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * 추가 속성 항목1
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목2
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * 추가 속성 항목2
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * 추가 속성 항목2
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목3
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * 추가 속성 항목3
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * 추가 속성 항목3
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목4
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * 추가 속성 항목4
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * 추가 속성 항목4
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목5
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * 추가 속성 항목5
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * 추가 속성 항목5
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목6
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * 추가 속성 항목6
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * 추가 속성 항목6
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목7
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * 추가 속성 항목7
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * 추가 속성 항목7
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목8
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * 추가 속성 항목8
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * 추가 속성 항목8
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목9
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * 추가 속성 항목9
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * 추가 속성 항목9
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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
    * 추가 속성 항목10
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * 추가 속성 항목10
    *
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * 추가 속성 항목10
    *
    * @see e3ps.dms.entity.KETDocumentCategory
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

   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String FOLDER = "folder";
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String FOLDER_REFERENCE = "folderReference";
   wt.fc.ObjectReference folderReference;
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public wt.folder.Folder getFolder() {
      return (folderReference != null) ? (wt.folder.Folder) folderReference.getObject() : null;
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public wt.fc.ObjectReference getFolderReference() {
      return folderReference;
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setFolder(wt.folder.Folder the_folder) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFolderReference(the_folder == null ? null : wt.fc.ObjectReference.newObjectReference((wt.folder.Folder) the_folder));
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setFolderReference(wt.fc.ObjectReference the_folderReference) throws wt.util.WTPropertyVetoException {
      folderReferenceValidate(the_folderReference);
      folderReference = (wt.fc.ObjectReference) the_folderReference;
   }
   void folderReferenceValidate(wt.fc.ObjectReference the_folderReference) throws wt.util.WTPropertyVetoException {
      if (the_folderReference == null || the_folderReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "folderReference") },
               new java.beans.PropertyChangeEvent(this, "folderReference", this.folderReference, folderReference));
      if (the_folderReference != null && the_folderReference.getReferencedClass() != null &&
            !wt.folder.Folder.class.isAssignableFrom(the_folderReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "folderReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "folderReference", this.folderReference, folderReference));
   }

   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String PARENT = "parent";
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public static final java.lang.String PARENT_REFERENCE = "parentReference";
   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public e3ps.dms.entity.KETDocumentCategory getParent() {
      return (parentReference != null) ? (e3ps.dms.entity.KETDocumentCategory) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
    */
   public void setParent(e3ps.dms.entity.KETDocumentCategory the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.dms.entity.KETDocumentCategory) the_parent));
   }
   /**
    * @see e3ps.dms.entity.KETDocumentCategory
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
            !e3ps.dms.entity.KETDocumentCategory.class.isAssignableFrom(the_parentReference.getReferencedClass()))
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

   public static final long EXTERNALIZATION_VERSION_UID = 8015185555473646229L;

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
      output.writeObject( categoryCode );
      output.writeInt( categoryLevel );
      output.writeObject( categoryName );
      output.writeObject( docTypeCode );
      output.writeObject( folderReference );
      output.writeObject( isANPQP );
      output.writeObject( isAPQP );
      output.writeObject( isDRCheckSheet );
      output.writeObject( isESIR );
      output.writeObject( isISIRCar );
      output.writeObject( isISIRElec );
      output.writeObject( isPSO10 );
      output.writeObject( isSYMC );
      output.writeObject( isUsed );
      output.writeObject( parentCategoryCode );
      output.writeObject( parentReference );
      output.writeInt( sortNo );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETDocumentCategory) this, input, readSerialVersionUID, false, false );
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
      output.setString( "categoryCode", categoryCode );
      output.setInt( "categoryLevel", categoryLevel );
      output.setString( "categoryName", categoryName );
      output.setString( "docTypeCode", docTypeCode );
      output.writeObject( "folderReference", folderReference, wt.fc.ObjectReference.class, true );
      output.setBooleanObject( "isANPQP", isANPQP );
      output.setBooleanObject( "isAPQP", isAPQP );
      output.setBooleanObject( "isDRCheckSheet", isDRCheckSheet );
      output.setBooleanObject( "isESIR", isESIR );
      output.setBooleanObject( "isISIRCar", isISIRCar );
      output.setBooleanObject( "isISIRElec", isISIRElec );
      output.setBooleanObject( "isPSO10", isPSO10 );
      output.setBooleanObject( "isSYMC", isSYMC );
      output.setBooleanObject( "isUsed", isUsed );
      output.setString( "parentCategoryCode", parentCategoryCode );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setInt( "sortNo", sortNo );
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
      categoryCode = input.getString( "categoryCode" );
      categoryLevel = input.getInt( "categoryLevel" );
      categoryName = input.getString( "categoryName" );
      docTypeCode = input.getString( "docTypeCode" );
      folderReference = (wt.fc.ObjectReference) input.readObject( "folderReference", folderReference, wt.fc.ObjectReference.class, true );
      isANPQP = input.getBooleanObject( "isANPQP" );
      isAPQP = input.getBooleanObject( "isAPQP" );
      isDRCheckSheet = input.getBooleanObject( "isDRCheckSheet" );
      isESIR = input.getBooleanObject( "isESIR" );
      isISIRCar = input.getBooleanObject( "isISIRCar" );
      isISIRElec = input.getBooleanObject( "isISIRElec" );
      isPSO10 = input.getBooleanObject( "isPSO10" );
      isSYMC = input.getBooleanObject( "isSYMC" );
      isUsed = input.getBooleanObject( "isUsed" );
      parentCategoryCode = input.getString( "parentCategoryCode" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      sortNo = input.getInt( "sortNo" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion8015185555473646229L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
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
      categoryCode = (java.lang.String) input.readObject();
      categoryLevel = input.readInt();
      categoryName = (java.lang.String) input.readObject();
      docTypeCode = (java.lang.String) input.readObject();
      folderReference = (wt.fc.ObjectReference) input.readObject();
      isANPQP = (java.lang.Boolean) input.readObject();
      isAPQP = (java.lang.Boolean) input.readObject();
      isDRCheckSheet = (java.lang.Boolean) input.readObject();
      isESIR = (java.lang.Boolean) input.readObject();
      isISIRCar = (java.lang.Boolean) input.readObject();
      isISIRElec = (java.lang.Boolean) input.readObject();
      isPSO10 = (java.lang.Boolean) input.readObject();
      isSYMC = (java.lang.Boolean) input.readObject();
      isUsed = (java.lang.Boolean) input.readObject();
      parentCategoryCode = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      sortNo = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDocumentCategory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8015185555473646229L( input, readSerialVersionUID, superDone );
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
