package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartClassification implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartClassification.class.getName();

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String CLASS_CODE = "classCode";
   static int CLASS_CODE_UPPER_LIMIT = -1;
   java.lang.String classCode;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getClassCode() {
      return classCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setClassCode(java.lang.String classCode) throws wt.util.WTPropertyVetoException {
      classCodeValidate(classCode);
      this.classCode = classCode;
   }
   void classCodeValidate(java.lang.String classCode) throws wt.util.WTPropertyVetoException {
      if (CLASS_CODE_UPPER_LIMIT < 1) {
         try { CLASS_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASS_CODE_UPPER_LIMIT = 200; }
      }
      if (classCode != null && !wt.fc.PersistenceHelper.checkStoredLength(classCode.toString(), CLASS_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classCode"), java.lang.String.valueOf(java.lang.Math.min(CLASS_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classCode", this.classCode, classCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String CLASS_KR_NAME = "classKrName";
   static int CLASS_KR_NAME_UPPER_LIMIT = -1;
   java.lang.String classKrName;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getClassKrName() {
      return classKrName;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setClassKrName(java.lang.String classKrName) throws wt.util.WTPropertyVetoException {
      classKrNameValidate(classKrName);
      this.classKrName = classKrName;
   }
   void classKrNameValidate(java.lang.String classKrName) throws wt.util.WTPropertyVetoException {
      if (CLASS_KR_NAME_UPPER_LIMIT < 1) {
         try { CLASS_KR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classKrName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASS_KR_NAME_UPPER_LIMIT = 200; }
      }
      if (classKrName != null && !wt.fc.PersistenceHelper.checkStoredLength(classKrName.toString(), CLASS_KR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classKrName"), java.lang.String.valueOf(java.lang.Math.min(CLASS_KR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classKrName", this.classKrName, classKrName));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String CLASS_EN_NAME = "classEnName";
   static int CLASS_EN_NAME_UPPER_LIMIT = -1;
   java.lang.String classEnName;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getClassEnName() {
      return classEnName;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setClassEnName(java.lang.String classEnName) throws wt.util.WTPropertyVetoException {
      classEnNameValidate(classEnName);
      this.classEnName = classEnName;
   }
   void classEnNameValidate(java.lang.String classEnName) throws wt.util.WTPropertyVetoException {
      if (CLASS_EN_NAME_UPPER_LIMIT < 1) {
         try { CLASS_EN_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classEnName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASS_EN_NAME_UPPER_LIMIT = 200; }
      }
      if (classEnName != null && !wt.fc.PersistenceHelper.checkStoredLength(classEnName.toString(), CLASS_EN_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classEnName"), java.lang.String.valueOf(java.lang.Math.min(CLASS_EN_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classEnName", this.classEnName, classEnName));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String CLASS_ZH_NAME = "classZhName";
   static int CLASS_ZH_NAME_UPPER_LIMIT = -1;
   java.lang.String classZhName;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getClassZhName() {
      return classZhName;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setClassZhName(java.lang.String classZhName) throws wt.util.WTPropertyVetoException {
      classZhNameValidate(classZhName);
      this.classZhName = classZhName;
   }
   void classZhNameValidate(java.lang.String classZhName) throws wt.util.WTPropertyVetoException {
      if (CLASS_ZH_NAME_UPPER_LIMIT < 1) {
         try { CLASS_ZH_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classZhName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASS_ZH_NAME_UPPER_LIMIT = 200; }
      }
      if (classZhName != null && !wt.fc.PersistenceHelper.checkStoredLength(classZhName.toString(), CLASS_ZH_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classZhName"), java.lang.String.valueOf(java.lang.Math.min(CLASS_ZH_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classZhName", this.classZhName, classZhName));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String NUMBERING_CODE = "numberingCode";
   static int NUMBERING_CODE_UPPER_LIMIT = -1;
   java.lang.String numberingCode;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getNumberingCode() {
      return numberingCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setNumberingCode(java.lang.String numberingCode) throws wt.util.WTPropertyVetoException {
      numberingCodeValidate(numberingCode);
      this.numberingCode = numberingCode;
   }
   void numberingCodeValidate(java.lang.String numberingCode) throws wt.util.WTPropertyVetoException {
      if (NUMBERING_CODE_UPPER_LIMIT < 1) {
         try { NUMBERING_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("numberingCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NUMBERING_CODE_UPPER_LIMIT = 200; }
      }
      if (numberingCode != null && !wt.fc.PersistenceHelper.checkStoredLength(numberingCode.toString(), NUMBERING_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "numberingCode"), java.lang.String.valueOf(java.lang.Math.min(NUMBERING_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "numberingCode", this.numberingCode, numberingCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String NUMBERING_CHARICS = "numberingCharics";
   static int NUMBERING_CHARICS_UPPER_LIMIT = -1;
   java.lang.String numberingCharics;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getNumberingCharics() {
      return numberingCharics;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setNumberingCharics(java.lang.String numberingCharics) throws wt.util.WTPropertyVetoException {
      numberingCharicsValidate(numberingCharics);
      this.numberingCharics = numberingCharics;
   }
   void numberingCharicsValidate(java.lang.String numberingCharics) throws wt.util.WTPropertyVetoException {
      if (NUMBERING_CHARICS_UPPER_LIMIT < 1) {
         try { NUMBERING_CHARICS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("numberingCharics").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NUMBERING_CHARICS_UPPER_LIMIT = 200; }
      }
      if (numberingCharics != null && !wt.fc.PersistenceHelper.checkStoredLength(numberingCharics.toString(), NUMBERING_CHARICS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "numberingCharics"), java.lang.String.valueOf(java.lang.Math.min(NUMBERING_CHARICS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "numberingCharics", this.numberingCharics, numberingCharics));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String NUMBERING_MIN_NO = "numberingMinNo";
   static int NUMBERING_MIN_NO_UPPER_LIMIT = -1;
   java.lang.String numberingMinNo;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getNumberingMinNo() {
      return numberingMinNo;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setNumberingMinNo(java.lang.String numberingMinNo) throws wt.util.WTPropertyVetoException {
      numberingMinNoValidate(numberingMinNo);
      this.numberingMinNo = numberingMinNo;
   }
   void numberingMinNoValidate(java.lang.String numberingMinNo) throws wt.util.WTPropertyVetoException {
      if (NUMBERING_MIN_NO_UPPER_LIMIT < 1) {
         try { NUMBERING_MIN_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("numberingMinNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NUMBERING_MIN_NO_UPPER_LIMIT = 200; }
      }
      if (numberingMinNo != null && !wt.fc.PersistenceHelper.checkStoredLength(numberingMinNo.toString(), NUMBERING_MIN_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "numberingMinNo"), java.lang.String.valueOf(java.lang.Math.min(NUMBERING_MIN_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "numberingMinNo", this.numberingMinNo, numberingMinNo));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String SORT_NO = "sortNo";
   int sortNo;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public int getSortNo() {
      return sortNo;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setSortNo(int sortNo) throws wt.util.WTPropertyVetoException {
      sortNoValidate(sortNo);
      this.sortNo = sortNo;
   }
   void sortNoValidate(int sortNo) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String USE_CLAZ = "useClaz";
   boolean useClaz = true;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public boolean isUseClaz() {
      return useClaz;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setUseClaz(boolean useClaz) throws wt.util.WTPropertyVetoException {
      useClazValidate(useClaz);
      this.useClaz = useClaz;
   }
   void useClazValidate(boolean useClaz) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String ERP_PROD_CODE = "erpProdCode";
   static int ERP_PROD_CODE_UPPER_LIMIT = -1;
   java.lang.String erpProdCode;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getErpProdCode() {
      return erpProdCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setErpProdCode(java.lang.String erpProdCode) throws wt.util.WTPropertyVetoException {
      erpProdCodeValidate(erpProdCode);
      this.erpProdCode = erpProdCode;
   }
   void erpProdCodeValidate(java.lang.String erpProdCode) throws wt.util.WTPropertyVetoException {
      if (ERP_PROD_CODE_UPPER_LIMIT < 1) {
         try { ERP_PROD_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("erpProdCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ERP_PROD_CODE_UPPER_LIMIT = 200; }
      }
      if (erpProdCode != null && !wt.fc.PersistenceHelper.checkStoredLength(erpProdCode.toString(), ERP_PROD_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "erpProdCode"), java.lang.String.valueOf(java.lang.Math.min(ERP_PROD_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "erpProdCode", this.erpProdCode, erpProdCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String CLASS_PART_TYPE = "classPartType";
   static int CLASS_PART_TYPE_UPPER_LIMIT = -1;
   java.lang.String classPartType;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getClassPartType() {
      return classPartType;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setClassPartType(java.lang.String classPartType) throws wt.util.WTPropertyVetoException {
      classPartTypeValidate(classPartType);
      this.classPartType = classPartType;
   }
   void classPartTypeValidate(java.lang.String classPartType) throws wt.util.WTPropertyVetoException {
      if (CLASS_PART_TYPE_UPPER_LIMIT < 1) {
         try { CLASS_PART_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classPartType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASS_PART_TYPE_UPPER_LIMIT = 200; }
      }
      if (classPartType != null && !wt.fc.PersistenceHelper.checkStoredLength(classPartType.toString(), CLASS_PART_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classPartType"), java.lang.String.valueOf(java.lang.Math.min(CLASS_PART_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classPartType", this.classPartType, classPartType));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String ERP_CLASS_NO = "erpClassNo";
   static int ERP_CLASS_NO_UPPER_LIMIT = -1;
   java.lang.String erpClassNo;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getErpClassNo() {
      return erpClassNo;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setErpClassNo(java.lang.String erpClassNo) throws wt.util.WTPropertyVetoException {
      erpClassNoValidate(erpClassNo);
      this.erpClassNo = erpClassNo;
   }
   void erpClassNoValidate(java.lang.String erpClassNo) throws wt.util.WTPropertyVetoException {
      if (ERP_CLASS_NO_UPPER_LIMIT < 1) {
         try { ERP_CLASS_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("erpClassNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ERP_CLASS_NO_UPPER_LIMIT = 200; }
      }
      if (erpClassNo != null && !wt.fc.PersistenceHelper.checkStoredLength(erpClassNo.toString(), ERP_CLASS_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "erpClassNo"), java.lang.String.valueOf(java.lang.Math.min(ERP_CLASS_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "erpClassNo", this.erpClassNo, erpClassNo));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String EPM_CODE = "epmCode";
   static int EPM_CODE_UPPER_LIMIT = -1;
   java.lang.String epmCode;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getEpmCode() {
      return epmCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setEpmCode(java.lang.String epmCode) throws wt.util.WTPropertyVetoException {
      epmCodeValidate(epmCode);
      this.epmCode = epmCode;
   }
   void epmCodeValidate(java.lang.String epmCode) throws wt.util.WTPropertyVetoException {
      if (EPM_CODE_UPPER_LIMIT < 1) {
         try { EPM_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("epmCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EPM_CODE_UPPER_LIMIT = 200; }
      }
      if (epmCode != null && !wt.fc.PersistenceHelper.checkStoredLength(epmCode.toString(), EPM_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "epmCode"), java.lang.String.valueOf(java.lang.Math.min(EPM_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "epmCode", this.epmCode, epmCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String ERP_PROD_GROUP_CODE = "erpProdGroupCode";
   static int ERP_PROD_GROUP_CODE_UPPER_LIMIT = -1;
   java.lang.String erpProdGroupCode;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getErpProdGroupCode() {
      return erpProdGroupCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setErpProdGroupCode(java.lang.String erpProdGroupCode) throws wt.util.WTPropertyVetoException {
      erpProdGroupCodeValidate(erpProdGroupCode);
      this.erpProdGroupCode = erpProdGroupCode;
   }
   void erpProdGroupCodeValidate(java.lang.String erpProdGroupCode) throws wt.util.WTPropertyVetoException {
      if (ERP_PROD_GROUP_CODE_UPPER_LIMIT < 1) {
         try { ERP_PROD_GROUP_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("erpProdGroupCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ERP_PROD_GROUP_CODE_UPPER_LIMIT = 200; }
      }
      if (erpProdGroupCode != null && !wt.fc.PersistenceHelper.checkStoredLength(erpProdGroupCode.toString(), ERP_PROD_GROUP_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "erpProdGroupCode"), java.lang.String.valueOf(java.lang.Math.min(ERP_PROD_GROUP_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "erpProdGroupCode", this.erpProdGroupCode, erpProdGroupCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String PART_CLASSIFIC_TYPE = "partClassificType";
   static int PART_CLASSIFIC_TYPE_UPPER_LIMIT = -1;
   java.lang.String partClassificType;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getPartClassificType() {
      return partClassificType;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setPartClassificType(java.lang.String partClassificType) throws wt.util.WTPropertyVetoException {
      partClassificTypeValidate(partClassificType);
      this.partClassificType = partClassificType;
   }
   void partClassificTypeValidate(java.lang.String partClassificType) throws wt.util.WTPropertyVetoException {
      if (PART_CLASSIFIC_TYPE_UPPER_LIMIT < 1) {
         try { PART_CLASSIFIC_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partClassificType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_CLASSIFIC_TYPE_UPPER_LIMIT = 200; }
      }
      if (partClassificType != null && !wt.fc.PersistenceHelper.checkStoredLength(partClassificType.toString(), PART_CLASSIFIC_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partClassificType"), java.lang.String.valueOf(java.lang.Math.min(PART_CLASSIFIC_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partClassificType", this.partClassificType, partClassificType));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String CATALOGUE_CODE = "catalogueCode";
   static int CATALOGUE_CODE_UPPER_LIMIT = -1;
   java.lang.String catalogueCode;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getCatalogueCode() {
      return catalogueCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setCatalogueCode(java.lang.String catalogueCode) throws wt.util.WTPropertyVetoException {
      catalogueCodeValidate(catalogueCode);
      this.catalogueCode = catalogueCode;
   }
   void catalogueCodeValidate(java.lang.String catalogueCode) throws wt.util.WTPropertyVetoException {
      if (CATALOGUE_CODE_UPPER_LIMIT < 1) {
         try { CATALOGUE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("catalogueCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CATALOGUE_CODE_UPPER_LIMIT = 200; }
      }
      if (catalogueCode != null && !wt.fc.PersistenceHelper.checkStoredLength(catalogueCode.toString(), CATALOGUE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "catalogueCode"), java.lang.String.valueOf(java.lang.Math.min(CATALOGUE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "catalogueCode", this.catalogueCode, catalogueCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String DIVISION_FLAG = "divisionFlag";
   static int DIVISION_FLAG_UPPER_LIMIT = -1;
   java.lang.String divisionFlag;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.String getDivisionFlag() {
      return divisionFlag;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setDivisionFlag(java.lang.String divisionFlag) throws wt.util.WTPropertyVetoException {
      divisionFlagValidate(divisionFlag);
      this.divisionFlag = divisionFlag;
   }
   void divisionFlagValidate(java.lang.String divisionFlag) throws wt.util.WTPropertyVetoException {
      if (DIVISION_FLAG_UPPER_LIMIT < 1) {
         try { DIVISION_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("divisionFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIVISION_FLAG_UPPER_LIMIT = 200; }
      }
      if (divisionFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(divisionFlag.toString(), DIVISION_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "divisionFlag"), java.lang.String.valueOf(java.lang.Math.min(DIVISION_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "divisionFlag", this.divisionFlag, divisionFlag));
   }

   /**
    * 친환경여부
    *
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String IS_ECO_CAR = "isEcoCar";
   java.lang.Boolean isEcoCar = false;
   /**
    * 친환경여부
    *
    * @see ext.ket.part.entity.KETPartClassification
    */
   public java.lang.Boolean getIsEcoCar() {
      return isEcoCar;
   }
   /**
    * 친환경여부
    *
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setIsEcoCar(java.lang.Boolean isEcoCar) throws wt.util.WTPropertyVetoException {
      isEcoCarValidate(isEcoCar);
      this.isEcoCar = isEcoCar;
   }
   void isEcoCarValidate(java.lang.Boolean isEcoCar) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String PARENT = "parent";
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public static final java.lang.String PARENT_REFERENCE = "parentReference";
   wt.fc.ObjectReference parentReference;
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public ext.ket.part.entity.KETPartClassification getParent() {
      return (parentReference != null) ? (ext.ket.part.entity.KETPartClassification) parentReference.getObject() : null;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
    */
   public void setParent(ext.ket.part.entity.KETPartClassification the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.part.entity.KETPartClassification) the_parent));
   }
   /**
    * @see ext.ket.part.entity.KETPartClassification
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
            !ext.ket.part.entity.KETPartClassification.class.isAssignableFrom(the_parentReference.getReferencedClass()))
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

   public static final long EXTERNALIZATION_VERSION_UID = 7654007035389580797L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( catalogueCode );
      output.writeObject( classCode );
      output.writeObject( classEnName );
      output.writeObject( classKrName );
      output.writeObject( classPartType );
      output.writeObject( classZhName );
      output.writeObject( divisionFlag );
      output.writeObject( epmCode );
      output.writeObject( erpClassNo );
      output.writeObject( erpProdCode );
      output.writeObject( erpProdGroupCode );
      output.writeObject( isEcoCar );
      output.writeObject( numberingCharics );
      output.writeObject( numberingCode );
      output.writeObject( numberingMinNo );
      output.writeObject( parentReference );
      output.writeObject( partClassificType );
      output.writeInt( sortNo );
      output.writeObject( thePersistInfo );
      output.writeBoolean( useClaz );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.KETPartClassification) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "catalogueCode", catalogueCode );
      output.setString( "classCode", classCode );
      output.setString( "classEnName", classEnName );
      output.setString( "classKrName", classKrName );
      output.setString( "classPartType", classPartType );
      output.setString( "classZhName", classZhName );
      output.setString( "divisionFlag", divisionFlag );
      output.setString( "epmCode", epmCode );
      output.setString( "erpClassNo", erpClassNo );
      output.setString( "erpProdCode", erpProdCode );
      output.setString( "erpProdGroupCode", erpProdGroupCode );
      output.setBooleanObject( "isEcoCar", isEcoCar );
      output.setString( "numberingCharics", numberingCharics );
      output.setString( "numberingCode", numberingCode );
      output.setString( "numberingMinNo", numberingMinNo );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setString( "partClassificType", partClassificType );
      output.setInt( "sortNo", sortNo );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setBoolean( "useClaz", useClaz );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      catalogueCode = input.getString( "catalogueCode" );
      classCode = input.getString( "classCode" );
      classEnName = input.getString( "classEnName" );
      classKrName = input.getString( "classKrName" );
      classPartType = input.getString( "classPartType" );
      classZhName = input.getString( "classZhName" );
      divisionFlag = input.getString( "divisionFlag" );
      epmCode = input.getString( "epmCode" );
      erpClassNo = input.getString( "erpClassNo" );
      erpProdCode = input.getString( "erpProdCode" );
      erpProdGroupCode = input.getString( "erpProdGroupCode" );
      isEcoCar = input.getBooleanObject( "isEcoCar" );
      numberingCharics = input.getString( "numberingCharics" );
      numberingCode = input.getString( "numberingCode" );
      numberingMinNo = input.getString( "numberingMinNo" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      partClassificType = input.getString( "partClassificType" );
      sortNo = input.getInt( "sortNo" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      useClaz = input.getBoolean( "useClaz" );
   }

   boolean readVersion7654007035389580797L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      catalogueCode = (java.lang.String) input.readObject();
      classCode = (java.lang.String) input.readObject();
      classEnName = (java.lang.String) input.readObject();
      classKrName = (java.lang.String) input.readObject();
      classPartType = (java.lang.String) input.readObject();
      classZhName = (java.lang.String) input.readObject();
      divisionFlag = (java.lang.String) input.readObject();
      epmCode = (java.lang.String) input.readObject();
      erpClassNo = (java.lang.String) input.readObject();
      erpProdCode = (java.lang.String) input.readObject();
      erpProdGroupCode = (java.lang.String) input.readObject();
      isEcoCar = (java.lang.Boolean) input.readObject();
      numberingCharics = (java.lang.String) input.readObject();
      numberingCode = (java.lang.String) input.readObject();
      numberingMinNo = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      partClassificType = (java.lang.String) input.readObject();
      sortNo = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      useClaz = input.readBoolean();
      return true;
   }

   protected boolean readVersion( KETPartClassification thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7654007035389580797L( input, readSerialVersionUID, superDone );
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
