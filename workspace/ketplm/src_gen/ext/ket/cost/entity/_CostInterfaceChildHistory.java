package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostInterfaceChildHistory implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostInterfaceChildHistory.class.getName();

   /**
    * 프로젝트번호
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * 프로젝트번호
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * 프로젝트번호
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PJT_TYPE = "pjtType";
   static int PJT_TYPE_UPPER_LIMIT = -1;
   java.lang.String pjtType;
   /**
    * 제품,검토
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPjtType() {
      return pjtType;
   }
   /**
    * 제품,검토
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 가품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 가품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 가품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 완제품품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String ROOT_PART_NO = "rootPartNo";
   static int ROOT_PART_NO_UPPER_LIMIT = -1;
   java.lang.String rootPartNo;
   /**
    * 완제품품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getRootPartNo() {
      return rootPartNo;
   }
   /**
    * 완제품품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setRootPartNo(java.lang.String rootPartNo) throws wt.util.WTPropertyVetoException {
      rootPartNoValidate(rootPartNo);
      this.rootPartNo = rootPartNo;
   }
   void rootPartNoValidate(java.lang.String rootPartNo) throws wt.util.WTPropertyVetoException {
      if (ROOT_PART_NO_UPPER_LIMIT < 1) {
         try { ROOT_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rootPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ROOT_PART_NO_UPPER_LIMIT = 200; }
      }
      if (rootPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(rootPartNo.toString(), ROOT_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rootPartNo"), java.lang.String.valueOf(java.lang.Math.min(ROOT_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rootPartNo", this.rootPartNo, rootPartNo));
   }

   /**
    * 공정품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String REAL_PART_NO = "realPartNo";
   static int REAL_PART_NO_UPPER_LIMIT = -1;
   java.lang.String realPartNo;
   /**
    * 공정품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getRealPartNo() {
      return realPartNo;
   }
   /**
    * 공정품번
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * DR단계
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DR_STEP = "drStep";
   static int DR_STEP_UPPER_LIMIT = -1;
   java.lang.String drStep;
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDrStep() {
      return drStep;
   }
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 제품명
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * 제품명
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * 제품명
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * U/S
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String US = "us";
   static int US_UPPER_LIMIT = -1;
   java.lang.String us;
   /**
    * U/S
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getUs() {
      return us;
   }
   /**
    * U/S
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setUs(java.lang.String us) throws wt.util.WTPropertyVetoException {
      usValidate(us);
      this.us = us;
   }
   void usValidate(java.lang.String us) throws wt.util.WTPropertyVetoException {
      if (US_UPPER_LIMIT < 1) {
         try { US_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("us").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { US_UPPER_LIMIT = 200; }
      }
      if (us != null && !wt.fc.PersistenceHelper.checkStoredLength(us.toString(), US_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "us"), java.lang.String.valueOf(java.lang.Math.min(US_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "us", this.us, us));
   }

   /**
    * version + subVersion
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String VERSION = "version";
   static int VERSION_UPPER_LIMIT = -1;
   java.lang.String version;
   /**
    * version + subVersion
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getVersion() {
      return version;
   }
   /**
    * version + subVersion
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 제품분류
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PART_TYPE = "partType";
   static int PART_TYPE_UPPER_LIMIT = -1;
   java.lang.String partType;
   /**
    * 제품분류
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPartType() {
      return partType;
   }
   /**
    * 제품분류
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPartType(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      partTypeValidate(partType);
      this.partType = partType;
   }
   void partTypeValidate(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      if (PART_TYPE_UPPER_LIMIT < 1) {
         try { PART_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_TYPE_UPPER_LIMIT = 200; }
      }
      if (partType != null && !wt.fc.PersistenceHelper.checkStoredLength(partType.toString(), PART_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partType"), java.lang.String.valueOf(java.lang.Math.min(PART_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partType", this.partType, partType));
   }

   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MFT_FACTORY1 = "mftFactory1";
   static int MFT_FACTORY1_UPPER_LIMIT = -1;
   java.lang.String mftFactory1;
   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMftFactory1() {
      return mftFactory1;
   }
   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMftFactory1(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      mftFactory1Validate(mftFactory1);
      this.mftFactory1 = mftFactory1;
   }
   void mftFactory1Validate(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY1_UPPER_LIMIT < 1) {
         try { MFT_FACTORY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY1_UPPER_LIMIT = 200; }
      }
      if (mftFactory1 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory1.toString(), MFT_FACTORY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory1"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory1", this.mftFactory1, mftFactory1));
   }

   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MFT_FACTORY2 = "mftFactory2";
   static int MFT_FACTORY2_UPPER_LIMIT = -1;
   java.lang.String mftFactory2;
   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMftFactory2() {
      return mftFactory2;
   }
   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMftFactory2(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      mftFactory2Validate(mftFactory2);
      this.mftFactory2 = mftFactory2;
   }
   void mftFactory2Validate(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY2_UPPER_LIMIT < 1) {
         try { MFT_FACTORY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY2_UPPER_LIMIT = 200; }
      }
      if (mftFactory2 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory2.toString(), MFT_FACTORY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory2"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory2", this.mftFactory2, mftFactory2));
   }

   /**
    * 생산정보 - 업체명
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String COMPANY = "company";
   static int COMPANY_UPPER_LIMIT = -1;
   java.lang.String company;
   /**
    * 생산정보 - 업체명
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCompany() {
      return company;
   }
   /**
    * 생산정보 - 업체명
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCompany(java.lang.String company) throws wt.util.WTPropertyVetoException {
      companyValidate(company);
      this.company = company;
   }
   void companyValidate(java.lang.String company) throws wt.util.WTPropertyVetoException {
      if (COMPANY_UPPER_LIMIT < 1) {
         try { COMPANY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("company").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMPANY_UPPER_LIMIT = 200; }
      }
      if (company != null && !wt.fc.PersistenceHelper.checkStoredLength(company.toString(), COMPANY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "company"), java.lang.String.valueOf(java.lang.Math.min(COMPANY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "company", this.company, company));
   }

   /**
    * 생산정보 - 적용설비(Ton)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String FACILITIES = "facilities";
   static int FACILITIES_UPPER_LIMIT = -1;
   java.lang.String facilities = "0";
   /**
    * 생산정보 - 적용설비(Ton)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getFacilities() {
      return facilities;
   }
   /**
    * 생산정보 - 적용설비(Ton)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setFacilities(java.lang.String facilities) throws wt.util.WTPropertyVetoException {
      facilitiesValidate(facilities);
      this.facilities = facilities;
   }
   void facilitiesValidate(java.lang.String facilities) throws wt.util.WTPropertyVetoException {
      if (FACILITIES_UPPER_LIMIT < 1) {
         try { FACILITIES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facilities").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FACILITIES_UPPER_LIMIT = 200; }
      }
      if (facilities != null && !wt.fc.PersistenceHelper.checkStoredLength(facilities.toString(), FACILITIES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facilities"), java.lang.String.valueOf(java.lang.Math.min(FACILITIES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facilities", this.facilities, facilities));
   }

   /**
    * 생산Loss
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PRODUCT_LOSS_EXPR = "productLossExpr";
   static int PRODUCT_LOSS_EXPR_UPPER_LIMIT = -1;
   java.lang.String productLossExpr = "0";
   /**
    * 생산Loss
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getProductLossExpr() {
      return productLossExpr;
   }
   /**
    * 생산Loss
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setProductLossExpr(java.lang.String productLossExpr) throws wt.util.WTPropertyVetoException {
      productLossExprValidate(productLossExpr);
      this.productLossExpr = productLossExpr;
   }
   void productLossExprValidate(java.lang.String productLossExpr) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_LOSS_EXPR_UPPER_LIMIT < 1) {
         try { PRODUCT_LOSS_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productLossExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_LOSS_EXPR_UPPER_LIMIT = 200; }
      }
      if (productLossExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(productLossExpr.toString(), PRODUCT_LOSS_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productLossExpr"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_LOSS_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productLossExpr", this.productLossExpr, productLossExpr));
   }

   /**
    * 조립Loss
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String ASSY_LOSS_EXPR = "assyLossExpr";
   static int ASSY_LOSS_EXPR_UPPER_LIMIT = -1;
   java.lang.String assyLossExpr = "0";
   /**
    * 조립Loss
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getAssyLossExpr() {
      return assyLossExpr;
   }
   /**
    * 조립Loss
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setAssyLossExpr(java.lang.String assyLossExpr) throws wt.util.WTPropertyVetoException {
      assyLossExprValidate(assyLossExpr);
      this.assyLossExpr = assyLossExpr;
   }
   void assyLossExprValidate(java.lang.String assyLossExpr) throws wt.util.WTPropertyVetoException {
      if (ASSY_LOSS_EXPR_UPPER_LIMIT < 1) {
         try { ASSY_LOSS_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assyLossExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSY_LOSS_EXPR_UPPER_LIMIT = 200; }
      }
      if (assyLossExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(assyLossExpr.toString(), ASSY_LOSS_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assyLossExpr"), java.lang.String.valueOf(java.lang.Math.min(ASSY_LOSS_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assyLossExpr", this.assyLossExpr, assyLossExpr));
   }

   /**
    * SOP년도
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String SOP_YEAR = "sopYear";
   static int SOP_YEAR_UPPER_LIMIT = -1;
   java.lang.String sopYear;
   /**
    * SOP년도
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getSopYear() {
      return sopYear;
   }
   /**
    * SOP년도
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setSopYear(java.lang.String sopYear) throws wt.util.WTPropertyVetoException {
      sopYearValidate(sopYear);
      this.sopYear = sopYear;
   }
   void sopYearValidate(java.lang.String sopYear) throws wt.util.WTPropertyVetoException {
      if (SOP_YEAR_UPPER_LIMIT < 1) {
         try { SOP_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sopYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SOP_YEAR_UPPER_LIMIT = 200; }
      }
      if (sopYear != null && !wt.fc.PersistenceHelper.checkStoredLength(sopYear.toString(), SOP_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sopYear"), java.lang.String.valueOf(java.lang.Math.min(SOP_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sopYear", this.sopYear, sopYear));
   }

   /**
    * 통화
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String WAERS = "waers";
   static int WAERS_UPPER_LIMIT = -1;
   java.lang.String waers = "KRW";
   /**
    * 통화
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getWaers() {
      return waers;
   }
   /**
    * 통화
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setWaers(java.lang.String waers) throws wt.util.WTPropertyVetoException {
      waersValidate(waers);
      this.waers = waers;
   }
   void waersValidate(java.lang.String waers) throws wt.util.WTPropertyVetoException {
      if (WAERS_UPPER_LIMIT < 1) {
         try { WAERS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("waers").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WAERS_UPPER_LIMIT = 200; }
      }
      if (waers != null && !wt.fc.PersistenceHelper.checkStoredLength(waers.toString(), WAERS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "waers"), java.lang.String.valueOf(java.lang.Math.min(WAERS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "waers", this.waers, waers));
   }

   /**
    * 총원가(컨버팅)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PRODUCT_COST_TOTAL = "productCostTotal";
   static int PRODUCT_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String productCostTotal;
   /**
    * 총원가(컨버팅)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getProductCostTotal() {
      return productCostTotal;
   }
   /**
    * 총원가(컨버팅)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String ORG_PRODUCT_COST_TOTAL = "orgProductCostTotal";
   static int ORG_PRODUCT_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String orgProductCostTotal;
   /**
    * 총원가(PLM)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getOrgProductCostTotal() {
      return orgProductCostTotal;
   }
   /**
    * 총원가(PLM)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 재료비(수식)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MATERIAL_COST_EXPR = "materialCostExpr";
   static int MATERIAL_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String materialCostExpr = "0";
   /**
    * 재료비(수식)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMaterialCostExpr() {
      return materialCostExpr;
   }
   /**
    * 재료비(수식)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMaterialCostExpr(java.lang.String materialCostExpr) throws wt.util.WTPropertyVetoException {
      materialCostExprValidate(materialCostExpr);
      this.materialCostExpr = materialCostExpr;
   }
   void materialCostExprValidate(java.lang.String materialCostExpr) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_COST_EXPR_UPPER_LIMIT < 1) {
         try { MATERIAL_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("materialCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (materialCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(materialCostExpr.toString(), MATERIAL_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "materialCostExpr", this.materialCostExpr, materialCostExpr));
   }

   /**
    * 가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PROCESSING_COST = "processingCost";
   static int PROCESSING_COST_UPPER_LIMIT = -1;
   java.lang.String processingCost = "0";
   /**
    * 가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getProcessingCost() {
      return processingCost;
   }
   /**
    * 가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setProcessingCost(java.lang.String processingCost) throws wt.util.WTPropertyVetoException {
      processingCostValidate(processingCost);
      this.processingCost = processingCost;
   }
   void processingCostValidate(java.lang.String processingCost) throws wt.util.WTPropertyVetoException {
      if (PROCESSING_COST_UPPER_LIMIT < 1) {
         try { PROCESSING_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("processingCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROCESSING_COST_UPPER_LIMIT = 200; }
      }
      if (processingCost != null && !wt.fc.PersistenceHelper.checkStoredLength(processingCost.toString(), PROCESSING_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "processingCost"), java.lang.String.valueOf(java.lang.Math.min(PROCESSING_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "processingCost", this.processingCost, processingCost));
   }

   /**
    * 판관비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String SALES_MANAGE_COST = "salesManageCost";
   static int SALES_MANAGE_COST_UPPER_LIMIT = -1;
   java.lang.String salesManageCost = "0";
   /**
    * 판관비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getSalesManageCost() {
      return salesManageCost;
   }
   /**
    * 판관비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 개별포장비 합계(옵션)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PACK_UNIT_PRICE_OPTION = "packUnitPriceOption";
   static int PACK_UNIT_PRICE_OPTION_UPPER_LIMIT = -1;
   java.lang.String packUnitPriceOption = "0";
   /**
    * 개별포장비 합계(옵션)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPackUnitPriceOption() {
      return packUnitPriceOption;
   }
   /**
    * 개별포장비 합계(옵션)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPackUnitPriceOption(java.lang.String packUnitPriceOption) throws wt.util.WTPropertyVetoException {
      packUnitPriceOptionValidate(packUnitPriceOption);
      this.packUnitPriceOption = packUnitPriceOption;
   }
   void packUnitPriceOptionValidate(java.lang.String packUnitPriceOption) throws wt.util.WTPropertyVetoException {
      if (PACK_UNIT_PRICE_OPTION_UPPER_LIMIT < 1) {
         try { PACK_UNIT_PRICE_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packUnitPriceOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_UNIT_PRICE_OPTION_UPPER_LIMIT = 200; }
      }
      if (packUnitPriceOption != null && !wt.fc.PersistenceHelper.checkStoredLength(packUnitPriceOption.toString(), PACK_UNIT_PRICE_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packUnitPriceOption"), java.lang.String.valueOf(java.lang.Math.min(PACK_UNIT_PRICE_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packUnitPriceOption", this.packUnitPriceOption, packUnitPriceOption));
   }

   /**
    * 원재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String RAW_MATERIAL_COST_EXPR = "rawMaterialCostExpr";
   static int RAW_MATERIAL_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String rawMaterialCostExpr = "0";
   /**
    * 원재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getRawMaterialCostExpr() {
      return rawMaterialCostExpr;
   }
   /**
    * 원재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setRawMaterialCostExpr(java.lang.String rawMaterialCostExpr) throws wt.util.WTPropertyVetoException {
      rawMaterialCostExprValidate(rawMaterialCostExpr);
      this.rawMaterialCostExpr = rawMaterialCostExpr;
   }
   void rawMaterialCostExprValidate(java.lang.String rawMaterialCostExpr) throws wt.util.WTPropertyVetoException {
      if (RAW_MATERIAL_COST_EXPR_UPPER_LIMIT < 1) {
         try { RAW_MATERIAL_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rawMaterialCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RAW_MATERIAL_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (rawMaterialCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(rawMaterialCostExpr.toString(), RAW_MATERIAL_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rawMaterialCostExpr"), java.lang.String.valueOf(java.lang.Math.min(RAW_MATERIAL_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rawMaterialCostExpr", this.rawMaterialCostExpr, rawMaterialCostExpr));
   }

   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MATERIAL_COST = "materialCost";
   static int MATERIAL_COST_UPPER_LIMIT = -1;
   java.lang.String materialCost = "0";
   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMaterialCost() {
      return materialCost;
   }
   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 간접인건비비율R&D
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String INDIRECT_RND_RATE = "indirectRndRate";
   static int INDIRECT_RND_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectRndRate = "0";
   /**
    * 간접인건비비율R&D
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getIndirectRndRate() {
      return indirectRndRate;
   }
   /**
    * 간접인건비비율R&D
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 간접인건비비율노무비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String INDIRECT_LABOUR_RATE = "indirectLabourRate";
   static int INDIRECT_LABOUR_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectLabourRate = "0";
   /**
    * 간접인건비비율노무비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getIndirectLabourRate() {
      return indirectLabourRate;
   }
   /**
    * 간접인건비비율노무비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * R&D비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String RND_EXPR = "rndExpr";
   static int RND_EXPR_UPPER_LIMIT = -1;
   java.lang.String rndExpr = "0";
   /**
    * R&D비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getRndExpr() {
      return rndExpr;
   }
   /**
    * R&D비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setRndExpr(java.lang.String rndExpr) throws wt.util.WTPropertyVetoException {
      rndExprValidate(rndExpr);
      this.rndExpr = rndExpr;
   }
   void rndExprValidate(java.lang.String rndExpr) throws wt.util.WTPropertyVetoException {
      if (RND_EXPR_UPPER_LIMIT < 1) {
         try { RND_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rndExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RND_EXPR_UPPER_LIMIT = 200; }
      }
      if (rndExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(rndExpr.toString(), RND_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rndExpr"), java.lang.String.valueOf(java.lang.Math.min(RND_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rndExpr", this.rndExpr, rndExpr));
   }

   /**
    * 노무비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String LABOR_COST_EXPR = "laborCostExpr";
   static int LABOR_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String laborCostExpr = "0";
   /**
    * 노무비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getLaborCostExpr() {
      return laborCostExpr;
   }
   /**
    * 노무비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setLaborCostExpr(java.lang.String laborCostExpr) throws wt.util.WTPropertyVetoException {
      laborCostExprValidate(laborCostExpr);
      this.laborCostExpr = laborCostExpr;
   }
   void laborCostExprValidate(java.lang.String laborCostExpr) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_EXPR_UPPER_LIMIT < 1) {
         try { LABOR_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (laborCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostExpr.toString(), LABOR_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostExpr"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostExpr", this.laborCostExpr, laborCostExpr));
   }

   /**
    * 간접인건비R&D
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String INDIRECT_LABOUR_RND_COST = "indirectLabourRndCost";
   static int INDIRECT_LABOUR_RND_COST_UPPER_LIMIT = -1;
   java.lang.String indirectLabourRndCost = "0";
   /**
    * 간접인건비R&D
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getIndirectLabourRndCost() {
      return indirectLabourRndCost;
   }
   /**
    * 간접인건비R&D
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setIndirectLabourRndCost(java.lang.String indirectLabourRndCost) throws wt.util.WTPropertyVetoException {
      indirectLabourRndCostValidate(indirectLabourRndCost);
      this.indirectLabourRndCost = indirectLabourRndCost;
   }
   void indirectLabourRndCostValidate(java.lang.String indirectLabourRndCost) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_LABOUR_RND_COST_UPPER_LIMIT < 1) {
         try { INDIRECT_LABOUR_RND_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectLabourRndCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_LABOUR_RND_COST_UPPER_LIMIT = 200; }
      }
      if (indirectLabourRndCost != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectLabourRndCost.toString(), INDIRECT_LABOUR_RND_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectLabourRndCost"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_LABOUR_RND_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectLabourRndCost", this.indirectLabourRndCost, indirectLabourRndCost));
   }

   /**
    * 직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String LABOR_COST = "laborCost";
   static int LABOR_COST_UPPER_LIMIT = -1;
   java.lang.String laborCost = "0";
   /**
    * 직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getLaborCost() {
      return laborCost;
   }
   /**
    * 직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String IN_DIRECT_LABOR_COST = "inDirectLaborCost";
   static int IN_DIRECT_LABOR_COST_UPPER_LIMIT = -1;
   java.lang.String inDirectLaborCost = "0";
   /**
    * 간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getInDirectLaborCost() {
      return inDirectLaborCost;
   }
   /**
    * 간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 설비감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String FAC_REDUCE_COST = "facReduceCost";
   static int FAC_REDUCE_COST_UPPER_LIMIT = -1;
   java.lang.String facReduceCost = "0";
   /**
    * 설비감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getFacReduceCost() {
      return facReduceCost;
   }
   /**
    * 설비감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setFacReduceCost(java.lang.String facReduceCost) throws wt.util.WTPropertyVetoException {
      facReduceCostValidate(facReduceCost);
      this.facReduceCost = facReduceCost;
   }
   void facReduceCostValidate(java.lang.String facReduceCost) throws wt.util.WTPropertyVetoException {
      if (FAC_REDUCE_COST_UPPER_LIMIT < 1) {
         try { FAC_REDUCE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facReduceCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_REDUCE_COST_UPPER_LIMIT = 200; }
      }
      if (facReduceCost != null && !wt.fc.PersistenceHelper.checkStoredLength(facReduceCost.toString(), FAC_REDUCE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facReduceCost"), java.lang.String.valueOf(java.lang.Math.min(FAC_REDUCE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facReduceCost", this.facReduceCost, facReduceCost));
   }

   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MACHINE_DPC_COST_EXPR = "machineDpcCostExpr";
   static int MACHINE_DPC_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String machineDpcCostExpr = "0";
   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMachineDpcCostExpr() {
      return machineDpcCostExpr;
   }
   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMachineDpcCostExpr(java.lang.String machineDpcCostExpr) throws wt.util.WTPropertyVetoException {
      machineDpcCostExprValidate(machineDpcCostExpr);
      this.machineDpcCostExpr = machineDpcCostExpr;
   }
   void machineDpcCostExprValidate(java.lang.String machineDpcCostExpr) throws wt.util.WTPropertyVetoException {
      if (MACHINE_DPC_COST_EXPR_UPPER_LIMIT < 1) {
         try { MACHINE_DPC_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineDpcCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_DPC_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (machineDpcCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(machineDpcCostExpr.toString(), MACHINE_DPC_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineDpcCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_DPC_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineDpcCostExpr", this.machineDpcCostExpr, machineDpcCostExpr));
   }

   /**
    * 설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String FAC_REDUCE_PRICE = "facReducePrice";
   static int FAC_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String facReducePrice = "0";
   /**
    * 설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getFacReducePrice() {
      return facReducePrice;
   }
   /**
    * 설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 전력비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String ELEC_COST_EXPR = "elecCostExpr";
   static int ELEC_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String elecCostExpr = "0";
   /**
    * 전력비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getElecCostExpr() {
      return elecCostExpr;
   }
   /**
    * 전력비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setElecCostExpr(java.lang.String elecCostExpr) throws wt.util.WTPropertyVetoException {
      elecCostExprValidate(elecCostExpr);
      this.elecCostExpr = elecCostExpr;
   }
   void elecCostExprValidate(java.lang.String elecCostExpr) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_EXPR_UPPER_LIMIT < 1) {
         try { ELEC_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (elecCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCostExpr.toString(), ELEC_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCostExpr"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCostExpr", this.elecCostExpr, elecCostExpr));
   }

   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TABARYU_EXPR = "tabaryuExpr";
   static int TABARYU_EXPR_UPPER_LIMIT = -1;
   java.lang.String tabaryuExpr = "0";
   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getTabaryuExpr() {
      return tabaryuExpr;
   }
   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setTabaryuExpr(java.lang.String tabaryuExpr) throws wt.util.WTPropertyVetoException {
      tabaryuExprValidate(tabaryuExpr);
      this.tabaryuExpr = tabaryuExpr;
   }
   void tabaryuExprValidate(java.lang.String tabaryuExpr) throws wt.util.WTPropertyVetoException {
      if (TABARYU_EXPR_UPPER_LIMIT < 1) {
         try { TABARYU_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tabaryuExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TABARYU_EXPR_UPPER_LIMIT = 200; }
      }
      if (tabaryuExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(tabaryuExpr.toString(), TABARYU_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tabaryuExpr"), java.lang.String.valueOf(java.lang.Math.min(TABARYU_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tabaryuExpr", this.tabaryuExpr, tabaryuExpr));
   }

   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MOLD_MAINTENANCE = "moldMaintenance";
   static int MOLD_MAINTENANCE_UPPER_LIMIT = -1;
   java.lang.String moldMaintenance = "0";
   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMoldMaintenance() {
      return moldMaintenance;
   }
   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 기타감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String ETC_REDUCE_PRICE = "etcReducePrice";
   static int ETC_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String etcReducePrice = "0";
   /**
    * 기타감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getEtcReducePrice() {
      return etcReducePrice;
   }
   /**
    * 기타감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setEtcReducePrice(java.lang.String etcReducePrice) throws wt.util.WTPropertyVetoException {
      etcReducePriceValidate(etcReducePrice);
      this.etcReducePrice = etcReducePrice;
   }
   void etcReducePriceValidate(java.lang.String etcReducePrice) throws wt.util.WTPropertyVetoException {
      if (ETC_REDUCE_PRICE_UPPER_LIMIT < 1) {
         try { ETC_REDUCE_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcReducePrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_REDUCE_PRICE_UPPER_LIMIT = 200; }
      }
      if (etcReducePrice != null && !wt.fc.PersistenceHelper.checkStoredLength(etcReducePrice.toString(), ETC_REDUCE_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcReducePrice"), java.lang.String.valueOf(java.lang.Math.min(ETC_REDUCE_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcReducePrice", this.etcReducePrice, etcReducePrice));
   }

   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DIRECT_COST = "directCost";
   static int DIRECT_COST_UPPER_LIMIT = -1;
   java.lang.String directCost = "0";
   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDirectCost() {
      return directCost;
   }
   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 개별포장비합계
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PACK_UNIT_PRICE_SUM = "packUnitPriceSum";
   static int PACK_UNIT_PRICE_SUM_UPPER_LIMIT = -1;
   java.lang.String packUnitPriceSum = "0";
   /**
    * 개별포장비합계
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPackUnitPriceSum() {
      return packUnitPriceSum;
   }
   /**
    * 개별포장비합계
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPackUnitPriceSum(java.lang.String packUnitPriceSum) throws wt.util.WTPropertyVetoException {
      packUnitPriceSumValidate(packUnitPriceSum);
      this.packUnitPriceSum = packUnitPriceSum;
   }
   void packUnitPriceSumValidate(java.lang.String packUnitPriceSum) throws wt.util.WTPropertyVetoException {
      if (PACK_UNIT_PRICE_SUM_UPPER_LIMIT < 1) {
         try { PACK_UNIT_PRICE_SUM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packUnitPriceSum").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_UNIT_PRICE_SUM_UPPER_LIMIT = 200; }
      }
      if (packUnitPriceSum != null && !wt.fc.PersistenceHelper.checkStoredLength(packUnitPriceSum.toString(), PACK_UNIT_PRICE_SUM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packUnitPriceSum"), java.lang.String.valueOf(java.lang.Math.min(PACK_UNIT_PRICE_SUM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packUnitPriceSum", this.packUnitPriceSum, packUnitPriceSum));
   }

   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String IN_DIRECT_COST_EXPR = "inDirectCostExpr";
   static int IN_DIRECT_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String inDirectCostExpr = "0";
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getInDirectCostExpr() {
      return inDirectCostExpr;
   }
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setInDirectCostExpr(java.lang.String inDirectCostExpr) throws wt.util.WTPropertyVetoException {
      inDirectCostExprValidate(inDirectCostExpr);
      this.inDirectCostExpr = inDirectCostExpr;
   }
   void inDirectCostExprValidate(java.lang.String inDirectCostExpr) throws wt.util.WTPropertyVetoException {
      if (IN_DIRECT_COST_EXPR_UPPER_LIMIT < 1) {
         try { IN_DIRECT_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inDirectCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_DIRECT_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (inDirectCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(inDirectCostExpr.toString(), IN_DIRECT_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inDirectCostExpr"), java.lang.String.valueOf(java.lang.Math.min(IN_DIRECT_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inDirectCostExpr", this.inDirectCostExpr, inDirectCostExpr));
   }

   /**
    * 간접경비2
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String IN_DIRECT_COST2_EXPR = "inDirectCost2Expr";
   static int IN_DIRECT_COST2_EXPR_UPPER_LIMIT = -1;
   java.lang.String inDirectCost2Expr = "0";
   /**
    * 간접경비2
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getInDirectCost2Expr() {
      return inDirectCost2Expr;
   }
   /**
    * 간접경비2
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setInDirectCost2Expr(java.lang.String inDirectCost2Expr) throws wt.util.WTPropertyVetoException {
      inDirectCost2ExprValidate(inDirectCost2Expr);
      this.inDirectCost2Expr = inDirectCost2Expr;
   }
   void inDirectCost2ExprValidate(java.lang.String inDirectCost2Expr) throws wt.util.WTPropertyVetoException {
      if (IN_DIRECT_COST2_EXPR_UPPER_LIMIT < 1) {
         try { IN_DIRECT_COST2_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inDirectCost2Expr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_DIRECT_COST2_EXPR_UPPER_LIMIT = 200; }
      }
      if (inDirectCost2Expr != null && !wt.fc.PersistenceHelper.checkStoredLength(inDirectCost2Expr.toString(), IN_DIRECT_COST2_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inDirectCost2Expr"), java.lang.String.valueOf(java.lang.Math.min(IN_DIRECT_COST2_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inDirectCost2Expr", this.inDirectCost2Expr, inDirectCost2Expr));
   }

   /**
    * 원재료물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MANAGE_MTR_LOGIS_EXPR = "manageMtrLogisExpr";
   static int MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT = -1;
   java.lang.String manageMtrLogisExpr = "0";
   /**
    * 원재료물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getManageMtrLogisExpr() {
      return manageMtrLogisExpr;
   }
   /**
    * 원재료물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setManageMtrLogisExpr(java.lang.String manageMtrLogisExpr) throws wt.util.WTPropertyVetoException {
      manageMtrLogisExprValidate(manageMtrLogisExpr);
      this.manageMtrLogisExpr = manageMtrLogisExpr;
   }
   void manageMtrLogisExprValidate(java.lang.String manageMtrLogisExpr) throws wt.util.WTPropertyVetoException {
      if (MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT < 1) {
         try { MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("manageMtrLogisExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT = 200; }
      }
      if (manageMtrLogisExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(manageMtrLogisExpr.toString(), MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manageMtrLogisExpr"), java.lang.String.valueOf(java.lang.Math.min(MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "manageMtrLogisExpr", this.manageMtrLogisExpr, manageMtrLogisExpr));
   }

   /**
    * 원재료관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MANAGE_MTRDUTIE_EXPR = "manageMtrdutieExpr";
   static int MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT = -1;
   java.lang.String manageMtrdutieExpr = "0";
   /**
    * 원재료관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getManageMtrdutieExpr() {
      return manageMtrdutieExpr;
   }
   /**
    * 원재료관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setManageMtrdutieExpr(java.lang.String manageMtrdutieExpr) throws wt.util.WTPropertyVetoException {
      manageMtrdutieExprValidate(manageMtrdutieExpr);
      this.manageMtrdutieExpr = manageMtrdutieExpr;
   }
   void manageMtrdutieExprValidate(java.lang.String manageMtrdutieExpr) throws wt.util.WTPropertyVetoException {
      if (MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT < 1) {
         try { MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("manageMtrdutieExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT = 200; }
      }
      if (manageMtrdutieExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(manageMtrdutieExpr.toString(), MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manageMtrdutieExpr"), java.lang.String.valueOf(java.lang.Math.min(MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "manageMtrdutieExpr", this.manageMtrdutieExpr, manageMtrdutieExpr));
   }

   /**
    * 공정물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_LT_COST_EXPR = "mtrLtCostExpr";
   static int MTR_LT_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String mtrLtCostExpr = "0";
   /**
    * 공정물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrLtCostExpr() {
      return mtrLtCostExpr;
   }
   /**
    * 공정물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrLtCostExpr(java.lang.String mtrLtCostExpr) throws wt.util.WTPropertyVetoException {
      mtrLtCostExprValidate(mtrLtCostExpr);
      this.mtrLtCostExpr = mtrLtCostExpr;
   }
   void mtrLtCostExprValidate(java.lang.String mtrLtCostExpr) throws wt.util.WTPropertyVetoException {
      if (MTR_LT_COST_EXPR_UPPER_LIMIT < 1) {
         try { MTR_LT_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrLtCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_LT_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (mtrLtCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrLtCostExpr.toString(), MTR_LT_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrLtCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MTR_LT_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrLtCostExpr", this.mtrLtCostExpr, mtrLtCostExpr));
   }

   /**
    * 공정관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_LT_RATE_EXPR = "mtrLtRateExpr";
   static int MTR_LT_RATE_EXPR_UPPER_LIMIT = -1;
   java.lang.String mtrLtRateExpr = "0";
   /**
    * 공정관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrLtRateExpr() {
      return mtrLtRateExpr;
   }
   /**
    * 공정관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrLtRateExpr(java.lang.String mtrLtRateExpr) throws wt.util.WTPropertyVetoException {
      mtrLtRateExprValidate(mtrLtRateExpr);
      this.mtrLtRateExpr = mtrLtRateExpr;
   }
   void mtrLtRateExprValidate(java.lang.String mtrLtRateExpr) throws wt.util.WTPropertyVetoException {
      if (MTR_LT_RATE_EXPR_UPPER_LIMIT < 1) {
         try { MTR_LT_RATE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrLtRateExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_LT_RATE_EXPR_UPPER_LIMIT = 200; }
      }
      if (mtrLtRateExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrLtRateExpr.toString(), MTR_LT_RATE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrLtRateExpr"), java.lang.String.valueOf(java.lang.Math.min(MTR_LT_RATE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrLtRateExpr", this.mtrLtRateExpr, mtrLtRateExpr));
   }

   /**
    * 납입물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PAY_COST_LT_EXPR = "payCostLtExpr";
   static int PAY_COST_LT_EXPR_UPPER_LIMIT = -1;
   java.lang.String payCostLtExpr = "0";
   /**
    * 납입물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPayCostLtExpr() {
      return payCostLtExpr;
   }
   /**
    * 납입물류비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPayCostLtExpr(java.lang.String payCostLtExpr) throws wt.util.WTPropertyVetoException {
      payCostLtExprValidate(payCostLtExpr);
      this.payCostLtExpr = payCostLtExpr;
   }
   void payCostLtExprValidate(java.lang.String payCostLtExpr) throws wt.util.WTPropertyVetoException {
      if (PAY_COST_LT_EXPR_UPPER_LIMIT < 1) {
         try { PAY_COST_LT_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payCostLtExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_COST_LT_EXPR_UPPER_LIMIT = 200; }
      }
      if (payCostLtExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(payCostLtExpr.toString(), PAY_COST_LT_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payCostLtExpr"), java.lang.String.valueOf(java.lang.Math.min(PAY_COST_LT_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payCostLtExpr", this.payCostLtExpr, payCostLtExpr));
   }

   /**
    * 납입관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PAY_RATE_LT_EXPR = "payRateLtExpr";
   static int PAY_RATE_LT_EXPR_UPPER_LIMIT = -1;
   java.lang.String payRateLtExpr = "0";
   /**
    * 납입관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPayRateLtExpr() {
      return payRateLtExpr;
   }
   /**
    * 납입관세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPayRateLtExpr(java.lang.String payRateLtExpr) throws wt.util.WTPropertyVetoException {
      payRateLtExprValidate(payRateLtExpr);
      this.payRateLtExpr = payRateLtExpr;
   }
   void payRateLtExprValidate(java.lang.String payRateLtExpr) throws wt.util.WTPropertyVetoException {
      if (PAY_RATE_LT_EXPR_UPPER_LIMIT < 1) {
         try { PAY_RATE_LT_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payRateLtExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_RATE_LT_EXPR_UPPER_LIMIT = 200; }
      }
      if (payRateLtExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(payRateLtExpr.toString(), PAY_RATE_LT_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payRateLtExpr"), java.lang.String.valueOf(java.lang.Math.min(PAY_RATE_LT_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payRateLtExpr", this.payRateLtExpr, payRateLtExpr));
   }

   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String INDIRECT_COST_RND = "indirectCostRnd";
   static int INDIRECT_COST_RND_UPPER_LIMIT = -1;
   java.lang.String indirectCostRnd = "0";
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getIndirectCostRnd() {
      return indirectCostRnd;
   }
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setIndirectCostRnd(java.lang.String indirectCostRnd) throws wt.util.WTPropertyVetoException {
      indirectCostRndValidate(indirectCostRnd);
      this.indirectCostRnd = indirectCostRnd;
   }
   void indirectCostRndValidate(java.lang.String indirectCostRnd) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_COST_RND_UPPER_LIMIT < 1) {
         try { INDIRECT_COST_RND_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectCostRnd").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_COST_RND_UPPER_LIMIT = 200; }
      }
      if (indirectCostRnd != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectCostRnd.toString(), INDIRECT_COST_RND_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectCostRnd"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_COST_RND_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectCostRnd", this.indirectCostRnd, indirectCostRnd));
   }

   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String IN_DIRECT_COST = "inDirectCost";
   static int IN_DIRECT_COST_UPPER_LIMIT = -1;
   java.lang.String inDirectCost = "0";
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getInDirectCost() {
      return inDirectCost;
   }
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MOLD_REDUCE_PRICE = "moldReducePrice";
   static int MOLD_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String moldReducePrice = "0";
   /**
    * 금형감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMoldReducePrice() {
      return moldReducePrice;
   }
   /**
    * 금형감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 외주단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String OUT_UNIT_COST_VAL = "outUnitCostVal";
   static int OUT_UNIT_COST_VAL_UPPER_LIMIT = -1;
   java.lang.String outUnitCostVal = "0";
   /**
    * 외주단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getOutUnitCostVal() {
      return outUnitCostVal;
   }
   /**
    * 외주단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setOutUnitCostVal(java.lang.String outUnitCostVal) throws wt.util.WTPropertyVetoException {
      outUnitCostValValidate(outUnitCostVal);
      this.outUnitCostVal = outUnitCostVal;
   }
   void outUnitCostValValidate(java.lang.String outUnitCostVal) throws wt.util.WTPropertyVetoException {
      if (OUT_UNIT_COST_VAL_UPPER_LIMIT < 1) {
         try { OUT_UNIT_COST_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outUnitCostVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_UNIT_COST_VAL_UPPER_LIMIT = 200; }
      }
      if (outUnitCostVal != null && !wt.fc.PersistenceHelper.checkStoredLength(outUnitCostVal.toString(), OUT_UNIT_COST_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outUnitCostVal"), java.lang.String.valueOf(java.lang.Math.min(OUT_UNIT_COST_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outUnitCostVal", this.outUnitCostVal, outUnitCostVal));
   }

   /**
    * 후도금단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String AP_UNIT_COST_VAL = "apUnitCostVal";
   static int AP_UNIT_COST_VAL_UPPER_LIMIT = -1;
   java.lang.String apUnitCostVal = "0";
   /**
    * 후도금단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getApUnitCostVal() {
      return apUnitCostVal;
   }
   /**
    * 후도금단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setApUnitCostVal(java.lang.String apUnitCostVal) throws wt.util.WTPropertyVetoException {
      apUnitCostValValidate(apUnitCostVal);
      this.apUnitCostVal = apUnitCostVal;
   }
   void apUnitCostValValidate(java.lang.String apUnitCostVal) throws wt.util.WTPropertyVetoException {
      if (AP_UNIT_COST_VAL_UPPER_LIMIT < 1) {
         try { AP_UNIT_COST_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apUnitCostVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_UNIT_COST_VAL_UPPER_LIMIT = 200; }
      }
      if (apUnitCostVal != null && !wt.fc.PersistenceHelper.checkStoredLength(apUnitCostVal.toString(), AP_UNIT_COST_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apUnitCostVal"), java.lang.String.valueOf(java.lang.Math.min(AP_UNIT_COST_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apUnitCostVal", this.apUnitCostVal, apUnitCostVal));
   }

   /**
    * 후도금비(옵션)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String AP_UNIT_COST_OPTION = "apUnitCostOption";
   static int AP_UNIT_COST_OPTION_UPPER_LIMIT = -1;
   java.lang.String apUnitCostOption = "0";
   /**
    * 후도금비(옵션)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getApUnitCostOption() {
      return apUnitCostOption;
   }
   /**
    * 후도금비(옵션)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setApUnitCostOption(java.lang.String apUnitCostOption) throws wt.util.WTPropertyVetoException {
      apUnitCostOptionValidate(apUnitCostOption);
      this.apUnitCostOption = apUnitCostOption;
   }
   void apUnitCostOptionValidate(java.lang.String apUnitCostOption) throws wt.util.WTPropertyVetoException {
      if (AP_UNIT_COST_OPTION_UPPER_LIMIT < 1) {
         try { AP_UNIT_COST_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apUnitCostOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_UNIT_COST_OPTION_UPPER_LIMIT = 200; }
      }
      if (apUnitCostOption != null && !wt.fc.PersistenceHelper.checkStoredLength(apUnitCostOption.toString(), AP_UNIT_COST_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apUnitCostOption"), java.lang.String.valueOf(java.lang.Math.min(AP_UNIT_COST_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apUnitCostOption", this.apUnitCostOption, apUnitCostOption));
   }

   /**
    * 법인간마진비용
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CORPORATE_MARGIN_COST_EXPR = "corporateMarginCostExpr";
   static int CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String corporateMarginCostExpr = "0";
   /**
    * 법인간마진비용
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCorporateMarginCostExpr() {
      return corporateMarginCostExpr;
   }
   /**
    * 법인간마진비용
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCorporateMarginCostExpr(java.lang.String corporateMarginCostExpr) throws wt.util.WTPropertyVetoException {
      corporateMarginCostExprValidate(corporateMarginCostExpr);
      this.corporateMarginCostExpr = corporateMarginCostExpr;
   }
   void corporateMarginCostExprValidate(java.lang.String corporateMarginCostExpr) throws wt.util.WTPropertyVetoException {
      if (CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT < 1) {
         try { CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("corporateMarginCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (corporateMarginCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(corporateMarginCostExpr.toString(), CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "corporateMarginCostExpr"), java.lang.String.valueOf(java.lang.Math.min(CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "corporateMarginCostExpr", this.corporateMarginCostExpr, corporateMarginCostExpr));
   }

   /**
    * 외주가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String OUT_UNIT_COST = "outUnitCost";
   static int OUT_UNIT_COST_UPPER_LIMIT = -1;
   java.lang.String outUnitCost = "0";
   /**
    * 외주가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getOutUnitCost() {
      return outUnitCost;
   }
   /**
    * 외주가공비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String ETC_COST = "etcCost";
   static int ETC_COST_UPPER_LIMIT = -1;
   java.lang.String etcCost = "0";
   /**
    * 기타원가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getEtcCost() {
      return etcCost;
   }
   /**
    * 기타원가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 일반관리비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CO_MANAGE_EXPR = "coManageExpr";
   static int CO_MANAGE_EXPR_UPPER_LIMIT = -1;
   java.lang.String coManageExpr = "0";
   /**
    * 일반관리비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCoManageExpr() {
      return coManageExpr;
   }
   /**
    * 일반관리비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCoManageExpr(java.lang.String coManageExpr) throws wt.util.WTPropertyVetoException {
      coManageExprValidate(coManageExpr);
      this.coManageExpr = coManageExpr;
   }
   void coManageExprValidate(java.lang.String coManageExpr) throws wt.util.WTPropertyVetoException {
      if (CO_MANAGE_EXPR_UPPER_LIMIT < 1) {
         try { CO_MANAGE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coManageExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CO_MANAGE_EXPR_UPPER_LIMIT = 200; }
      }
      if (coManageExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(coManageExpr.toString(), CO_MANAGE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coManageExpr"), java.lang.String.valueOf(java.lang.Math.min(CO_MANAGE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coManageExpr", this.coManageExpr, coManageExpr));
   }

   /**
    * 불량비율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DEFECT_COST_EXPR = "defectCostExpr";
   static int DEFECT_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String defectCostExpr = "0";
   /**
    * 불량비율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDefectCostExpr() {
      return defectCostExpr;
   }
   /**
    * 불량비율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDefectCostExpr(java.lang.String defectCostExpr) throws wt.util.WTPropertyVetoException {
      defectCostExprValidate(defectCostExpr);
      this.defectCostExpr = defectCostExpr;
   }
   void defectCostExprValidate(java.lang.String defectCostExpr) throws wt.util.WTPropertyVetoException {
      if (DEFECT_COST_EXPR_UPPER_LIMIT < 1) {
         try { DEFECT_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (defectCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(defectCostExpr.toString(), DEFECT_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectCostExpr"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectCostExpr", this.defectCostExpr, defectCostExpr));
   }

   /**
    * 스크랩매출
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String SCRAP_SALES_COST = "scrapSalesCost";
   static int SCRAP_SALES_COST_UPPER_LIMIT = -1;
   java.lang.String scrapSalesCost = "0";
   /**
    * 스크랩매출
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getScrapSalesCost() {
      return scrapSalesCost;
   }
   /**
    * 스크랩매출
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 가격차이-직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DF_DIRECT_LABOR_COST = "dfDirectLaborCost";
   static int DF_DIRECT_LABOR_COST_UPPER_LIMIT = -1;
   java.lang.String dfDirectLaborCost = "0";
   /**
    * 가격차이-직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDfDirectLaborCost() {
      return dfDirectLaborCost;
   }
   /**
    * 가격차이-직접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDfDirectLaborCost(java.lang.String dfDirectLaborCost) throws wt.util.WTPropertyVetoException {
      dfDirectLaborCostValidate(dfDirectLaborCost);
      this.dfDirectLaborCost = dfDirectLaborCost;
   }
   void dfDirectLaborCostValidate(java.lang.String dfDirectLaborCost) throws wt.util.WTPropertyVetoException {
      if (DF_DIRECT_LABOR_COST_UPPER_LIMIT < 1) {
         try { DF_DIRECT_LABOR_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dfDirectLaborCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DF_DIRECT_LABOR_COST_UPPER_LIMIT = 200; }
      }
      if (dfDirectLaborCost != null && !wt.fc.PersistenceHelper.checkStoredLength(dfDirectLaborCost.toString(), DF_DIRECT_LABOR_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dfDirectLaborCost"), java.lang.String.valueOf(java.lang.Math.min(DF_DIRECT_LABOR_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dfDirectLaborCost", this.dfDirectLaborCost, dfDirectLaborCost));
   }

   /**
    * 가격차이-간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DF_IN_DIRECT_LABOR_COST = "dfInDirectLaborCost";
   static int DF_IN_DIRECT_LABOR_COST_UPPER_LIMIT = -1;
   java.lang.String dfInDirectLaborCost = "0";
   /**
    * 가격차이-간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDfInDirectLaborCost() {
      return dfInDirectLaborCost;
   }
   /**
    * 가격차이-간접인건비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDfInDirectLaborCost(java.lang.String dfInDirectLaborCost) throws wt.util.WTPropertyVetoException {
      dfInDirectLaborCostValidate(dfInDirectLaborCost);
      this.dfInDirectLaborCost = dfInDirectLaborCost;
   }
   void dfInDirectLaborCostValidate(java.lang.String dfInDirectLaborCost) throws wt.util.WTPropertyVetoException {
      if (DF_IN_DIRECT_LABOR_COST_UPPER_LIMIT < 1) {
         try { DF_IN_DIRECT_LABOR_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dfInDirectLaborCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DF_IN_DIRECT_LABOR_COST_UPPER_LIMIT = 200; }
      }
      if (dfInDirectLaborCost != null && !wt.fc.PersistenceHelper.checkStoredLength(dfInDirectLaborCost.toString(), DF_IN_DIRECT_LABOR_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dfInDirectLaborCost"), java.lang.String.valueOf(java.lang.Math.min(DF_IN_DIRECT_LABOR_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dfInDirectLaborCost", this.dfInDirectLaborCost, dfInDirectLaborCost));
   }

   /**
    * 가격차이-설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DF_MACHINE_DEP = "dfMachineDep";
   static int DF_MACHINE_DEP_UPPER_LIMIT = -1;
   java.lang.String dfMachineDep = "0";
   /**
    * 가격차이-설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDfMachineDep() {
      return dfMachineDep;
   }
   /**
    * 가격차이-설비감가비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDfMachineDep(java.lang.String dfMachineDep) throws wt.util.WTPropertyVetoException {
      dfMachineDepValidate(dfMachineDep);
      this.dfMachineDep = dfMachineDep;
   }
   void dfMachineDepValidate(java.lang.String dfMachineDep) throws wt.util.WTPropertyVetoException {
      if (DF_MACHINE_DEP_UPPER_LIMIT < 1) {
         try { DF_MACHINE_DEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dfMachineDep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DF_MACHINE_DEP_UPPER_LIMIT = 200; }
      }
      if (dfMachineDep != null && !wt.fc.PersistenceHelper.checkStoredLength(dfMachineDep.toString(), DF_MACHINE_DEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dfMachineDep"), java.lang.String.valueOf(java.lang.Math.min(DF_MACHINE_DEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dfMachineDep", this.dfMachineDep, dfMachineDep));
   }

   /**
    * 가격차이-직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DF_DIRECT_EXPENSES = "dfDirectExpenses";
   static int DF_DIRECT_EXPENSES_UPPER_LIMIT = -1;
   java.lang.String dfDirectExpenses = "0";
   /**
    * 가격차이-직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDfDirectExpenses() {
      return dfDirectExpenses;
   }
   /**
    * 가격차이-직접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDfDirectExpenses(java.lang.String dfDirectExpenses) throws wt.util.WTPropertyVetoException {
      dfDirectExpensesValidate(dfDirectExpenses);
      this.dfDirectExpenses = dfDirectExpenses;
   }
   void dfDirectExpensesValidate(java.lang.String dfDirectExpenses) throws wt.util.WTPropertyVetoException {
      if (DF_DIRECT_EXPENSES_UPPER_LIMIT < 1) {
         try { DF_DIRECT_EXPENSES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dfDirectExpenses").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DF_DIRECT_EXPENSES_UPPER_LIMIT = 200; }
      }
      if (dfDirectExpenses != null && !wt.fc.PersistenceHelper.checkStoredLength(dfDirectExpenses.toString(), DF_DIRECT_EXPENSES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dfDirectExpenses"), java.lang.String.valueOf(java.lang.Math.min(DF_DIRECT_EXPENSES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dfDirectExpenses", this.dfDirectExpenses, dfDirectExpenses));
   }

   /**
    * 가격차이-간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DF_IN_DIRECT_EXPENSES = "dfInDirectExpenses";
   static int DF_IN_DIRECT_EXPENSES_UPPER_LIMIT = -1;
   java.lang.String dfInDirectExpenses = "0";
   /**
    * 가격차이-간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDfInDirectExpenses() {
      return dfInDirectExpenses;
   }
   /**
    * 가격차이-간접경비
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDfInDirectExpenses(java.lang.String dfInDirectExpenses) throws wt.util.WTPropertyVetoException {
      dfInDirectExpensesValidate(dfInDirectExpenses);
      this.dfInDirectExpenses = dfInDirectExpenses;
   }
   void dfInDirectExpensesValidate(java.lang.String dfInDirectExpenses) throws wt.util.WTPropertyVetoException {
      if (DF_IN_DIRECT_EXPENSES_UPPER_LIMIT < 1) {
         try { DF_IN_DIRECT_EXPENSES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dfInDirectExpenses").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DF_IN_DIRECT_EXPENSES_UPPER_LIMIT = 200; }
      }
      if (dfInDirectExpenses != null && !wt.fc.PersistenceHelper.checkStoredLength(dfInDirectExpenses.toString(), DF_IN_DIRECT_EXPENSES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dfInDirectExpenses"), java.lang.String.valueOf(java.lang.Math.min(DF_IN_DIRECT_EXPENSES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dfInDirectExpenses", this.dfInDirectExpenses, dfInDirectExpenses));
   }

   /**
    * 가격차이-금형감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DF_MOLD_DEP = "dfMoldDep";
   static int DF_MOLD_DEP_UPPER_LIMIT = -1;
   java.lang.String dfMoldDep = "0";
   /**
    * 가격차이-금형감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDfMoldDep() {
      return dfMoldDep;
   }
   /**
    * 가격차이-금형감가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDfMoldDep(java.lang.String dfMoldDep) throws wt.util.WTPropertyVetoException {
      dfMoldDepValidate(dfMoldDep);
      this.dfMoldDep = dfMoldDep;
   }
   void dfMoldDepValidate(java.lang.String dfMoldDep) throws wt.util.WTPropertyVetoException {
      if (DF_MOLD_DEP_UPPER_LIMIT < 1) {
         try { DF_MOLD_DEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dfMoldDep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DF_MOLD_DEP_UPPER_LIMIT = 200; }
      }
      if (dfMoldDep != null && !wt.fc.PersistenceHelper.checkStoredLength(dfMoldDep.toString(), DF_MOLD_DEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dfMoldDep"), java.lang.String.valueOf(java.lang.Math.min(DF_MOLD_DEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dfMoldDep", this.dfMoldDep, dfMoldDep));
   }

   /**
    * 수량차이-시간당생산량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String OUTPUT_EXPR = "outputExpr";
   static int OUTPUT_EXPR_UPPER_LIMIT = -1;
   java.lang.String outputExpr = "0";
   /**
    * 수량차이-시간당생산량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getOutputExpr() {
      return outputExpr;
   }
   /**
    * 수량차이-시간당생산량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setOutputExpr(java.lang.String outputExpr) throws wt.util.WTPropertyVetoException {
      outputExprValidate(outputExpr);
      this.outputExpr = outputExpr;
   }
   void outputExprValidate(java.lang.String outputExpr) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_EXPR_UPPER_LIMIT < 1) {
         try { OUTPUT_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_EXPR_UPPER_LIMIT = 200; }
      }
      if (outputExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(outputExpr.toString(), OUTPUT_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputExpr"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputExpr", this.outputExpr, outputExpr));
   }

   /**
    * 수량차이-CT
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String DF_CT = "dfCt";
   static int DF_CT_UPPER_LIMIT = -1;
   java.lang.String dfCt = "0";
   /**
    * 수량차이-CT
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getDfCt() {
      return dfCt;
   }
   /**
    * 수량차이-CT
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setDfCt(java.lang.String dfCt) throws wt.util.WTPropertyVetoException {
      dfCtValidate(dfCt);
      this.dfCt = dfCt;
   }
   void dfCtValidate(java.lang.String dfCt) throws wt.util.WTPropertyVetoException {
      if (DF_CT_UPPER_LIMIT < 1) {
         try { DF_CT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dfCt").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DF_CT_UPPER_LIMIT = 200; }
      }
      if (dfCt != null && !wt.fc.PersistenceHelper.checkStoredLength(dfCt.toString(), DF_CT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dfCt"), java.lang.String.valueOf(java.lang.Math.min(DF_CT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dfCt", this.dfCt, dfCt));
   }

   /**
    * 수량차이-CT(금형)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CT_SPMMOLD = "ctSPMMold";
   static int CT_SPMMOLD_UPPER_LIMIT = -1;
   java.lang.String ctSPMMold = "0";
   /**
    * 수량차이-CT(금형)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCtSPMMold() {
      return ctSPMMold;
   }
   /**
    * 수량차이-CT(금형)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCtSPMMold(java.lang.String ctSPMMold) throws wt.util.WTPropertyVetoException {
      ctSPMMoldValidate(ctSPMMold);
      this.ctSPMMold = ctSPMMold;
   }
   void ctSPMMoldValidate(java.lang.String ctSPMMold) throws wt.util.WTPropertyVetoException {
      if (CT_SPMMOLD_UPPER_LIMIT < 1) {
         try { CT_SPMMOLD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ctSPMMold").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CT_SPMMOLD_UPPER_LIMIT = 200; }
      }
      if (ctSPMMold != null && !wt.fc.PersistenceHelper.checkStoredLength(ctSPMMold.toString(), CT_SPMMOLD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ctSPMMold"), java.lang.String.valueOf(java.lang.Math.min(CT_SPMMOLD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ctSPMMold", this.ctSPMMold, ctSPMMold));
   }

   /**
    * 수량차이-CT(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CT_SPMASSEMBLE = "ctSPMAssemble";
   static int CT_SPMASSEMBLE_UPPER_LIMIT = -1;
   java.lang.String ctSPMAssemble = "0";
   /**
    * 수량차이-CT(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCtSPMAssemble() {
      return ctSPMAssemble;
   }
   /**
    * 수량차이-CT(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCtSPMAssemble(java.lang.String ctSPMAssemble) throws wt.util.WTPropertyVetoException {
      ctSPMAssembleValidate(ctSPMAssemble);
      this.ctSPMAssemble = ctSPMAssemble;
   }
   void ctSPMAssembleValidate(java.lang.String ctSPMAssemble) throws wt.util.WTPropertyVetoException {
      if (CT_SPMASSEMBLE_UPPER_LIMIT < 1) {
         try { CT_SPMASSEMBLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ctSPMAssemble").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CT_SPMASSEMBLE_UPPER_LIMIT = 200; }
      }
      if (ctSPMAssemble != null && !wt.fc.PersistenceHelper.checkStoredLength(ctSPMAssemble.toString(), CT_SPMASSEMBLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ctSPMAssemble"), java.lang.String.valueOf(java.lang.Math.min(CT_SPMASSEMBLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ctSPMAssemble", this.ctSPMAssemble, ctSPMAssemble));
   }

   /**
    * 수량차이-CV
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CV = "cv";
   static int CV_UPPER_LIMIT = -1;
   java.lang.String cv = "0";
   /**
    * 수량차이-CV
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCv() {
      return cv;
   }
   /**
    * 수량차이-CV
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCv(java.lang.String cv) throws wt.util.WTPropertyVetoException {
      cvValidate(cv);
      this.cv = cv;
   }
   void cvValidate(java.lang.String cv) throws wt.util.WTPropertyVetoException {
      if (CV_UPPER_LIMIT < 1) {
         try { CV_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cv").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CV_UPPER_LIMIT = 200; }
      }
      if (cv != null && !wt.fc.PersistenceHelper.checkStoredLength(cv.toString(), CV_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cv"), java.lang.String.valueOf(java.lang.Math.min(CV_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cv", this.cv, cv));
   }

   /**
    * 수량차이-CV(금형)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CV_MOLD = "cvMold";
   static int CV_MOLD_UPPER_LIMIT = -1;
   java.lang.String cvMold = "0";
   /**
    * 수량차이-CV(금형)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCvMold() {
      return cvMold;
   }
   /**
    * 수량차이-CV(금형)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCvMold(java.lang.String cvMold) throws wt.util.WTPropertyVetoException {
      cvMoldValidate(cvMold);
      this.cvMold = cvMold;
   }
   void cvMoldValidate(java.lang.String cvMold) throws wt.util.WTPropertyVetoException {
      if (CV_MOLD_UPPER_LIMIT < 1) {
         try { CV_MOLD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cvMold").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CV_MOLD_UPPER_LIMIT = 200; }
      }
      if (cvMold != null && !wt.fc.PersistenceHelper.checkStoredLength(cvMold.toString(), CV_MOLD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cvMold"), java.lang.String.valueOf(java.lang.Math.min(CV_MOLD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cvMold", this.cvMold, cvMold));
   }

   /**
    * 수량차이-CV(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CV_ASSEMBLE = "cvAssemble";
   static int CV_ASSEMBLE_UPPER_LIMIT = -1;
   java.lang.String cvAssemble = "0";
   /**
    * 수량차이-CV(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getCvAssemble() {
      return cvAssemble;
   }
   /**
    * 수량차이-CV(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCvAssemble(java.lang.String cvAssemble) throws wt.util.WTPropertyVetoException {
      cvAssembleValidate(cvAssemble);
      this.cvAssemble = cvAssemble;
   }
   void cvAssembleValidate(java.lang.String cvAssemble) throws wt.util.WTPropertyVetoException {
      if (CV_ASSEMBLE_UPPER_LIMIT < 1) {
         try { CV_ASSEMBLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cvAssemble").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CV_ASSEMBLE_UPPER_LIMIT = 200; }
      }
      if (cvAssemble != null && !wt.fc.PersistenceHelper.checkStoredLength(cvAssemble.toString(), CV_ASSEMBLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cvAssemble"), java.lang.String.valueOf(java.lang.Math.min(CV_ASSEMBLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cvAssemble", this.cvAssemble, cvAssemble));
   }

   /**
    * 수량차이-생산효율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String EFFICIENT_RATE = "efficientRate";
   static int EFFICIENT_RATE_UPPER_LIMIT = -1;
   java.lang.String efficientRate = "0";
   /**
    * 수량차이-생산효율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getEfficientRate() {
      return efficientRate;
   }
   /**
    * 수량차이-생산효율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 수량차이-인시당생산량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PERSON_QLH = "personQLH";
   static int PERSON_QLH_UPPER_LIMIT = -1;
   java.lang.String personQLH = "0";
   /**
    * 수량차이-인시당생산량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPersonQLH() {
      return personQLH;
   }
   /**
    * 수량차이-인시당생산량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPersonQLH(java.lang.String personQLH) throws wt.util.WTPropertyVetoException {
      personQLHValidate(personQLH);
      this.personQLH = personQLH;
   }
   void personQLHValidate(java.lang.String personQLH) throws wt.util.WTPropertyVetoException {
      if (PERSON_QLH_UPPER_LIMIT < 1) {
         try { PERSON_QLH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("personQLH").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PERSON_QLH_UPPER_LIMIT = 200; }
      }
      if (personQLH != null && !wt.fc.PersistenceHelper.checkStoredLength(personQLH.toString(), PERSON_QLH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "personQLH"), java.lang.String.valueOf(java.lang.Math.min(PERSON_QLH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "personQLH", this.personQLH, personQLH));
   }

   /**
    * 수량차이-투입인원(계산)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String CONVERT_WORKER = "convertWorker";
   static int CONVERT_WORKER_UPPER_LIMIT = -1;
   java.lang.String convertWorker = "0";
   /**
    * 수량차이-투입인원(계산)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getConvertWorker() {
      return convertWorker;
   }
   /**
    * 수량차이-투입인원(계산)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setConvertWorker(java.lang.String convertWorker) throws wt.util.WTPropertyVetoException {
      convertWorkerValidate(convertWorker);
      this.convertWorker = convertWorker;
   }
   void convertWorkerValidate(java.lang.String convertWorker) throws wt.util.WTPropertyVetoException {
      if (CONVERT_WORKER_UPPER_LIMIT < 1) {
         try { CONVERT_WORKER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("convertWorker").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CONVERT_WORKER_UPPER_LIMIT = 200; }
      }
      if (convertWorker != null && !wt.fc.PersistenceHelper.checkStoredLength(convertWorker.toString(), CONVERT_WORKER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "convertWorker"), java.lang.String.valueOf(java.lang.Math.min(CONVERT_WORKER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "convertWorker", this.convertWorker, convertWorker));
   }

   /**
    * 수량차이-관리대수
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String UNIT_MANAGE = "unitManage";
   static int UNIT_MANAGE_UPPER_LIMIT = -1;
   java.lang.String unitManage = "0";
   /**
    * 수량차이-관리대수
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getUnitManage() {
      return unitManage;
   }
   /**
    * 수량차이-관리대수
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 수량차이-투입인원(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String WORKER = "worker";
   static int WORKER_UPPER_LIMIT = -1;
   java.lang.String worker = "0";
   /**
    * 수량차이-투입인원(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getWorker() {
      return worker;
   }
   /**
    * 수량차이-투입인원(설비)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setWorker(java.lang.String worker) throws wt.util.WTPropertyVetoException {
      workerValidate(worker);
      this.worker = worker;
   }
   void workerValidate(java.lang.String worker) throws wt.util.WTPropertyVetoException {
      if (WORKER_UPPER_LIMIT < 1) {
         try { WORKER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("worker").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORKER_UPPER_LIMIT = 200; }
      }
      if (worker != null && !wt.fc.PersistenceHelper.checkStoredLength(worker.toString(), WORKER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "worker"), java.lang.String.valueOf(java.lang.Math.min(WORKER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "worker", this.worker, worker));
   }

   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String SORT_LOCATION = "sortLocation";
   java.lang.Integer sortLocation;
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.Integer getSortLocation() {
      return sortLocation;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setSortLocation(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
      sortLocationValidate(sortLocation);
      this.sortLocation = sortLocation;
   }
   void sortLocationValidate(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 재료비 가공 -1레벨 상위 partNo
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PARENT_PART_NO = "parentPartNo";
   static int PARENT_PART_NO_UPPER_LIMIT = -1;
   java.lang.String parentPartNo;
   /**
    * 재료비 가공 -1레벨 상위 partNo
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getParentPartNo() {
      return parentPartNo;
   }
   /**
    * 재료비 가공 -1레벨 상위 partNo
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setParentPartNo(java.lang.String parentPartNo) throws wt.util.WTPropertyVetoException {
      parentPartNoValidate(parentPartNo);
      this.parentPartNo = parentPartNo;
   }
   void parentPartNoValidate(java.lang.String parentPartNo) throws wt.util.WTPropertyVetoException {
      if (PARENT_PART_NO_UPPER_LIMIT < 1) {
         try { PARENT_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("parentPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PARENT_PART_NO_UPPER_LIMIT = 200; }
      }
      if (parentPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(parentPartNo.toString(), PARENT_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentPartNo"), java.lang.String.valueOf(java.lang.Math.min(PARENT_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "parentPartNo", this.parentPartNo, parentPartNo));
   }

   /**
    * 재료비 가공 -1레벨 상위 partNo의 u/s
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PARENT_PART_US = "parentPartUs";
   static int PARENT_PART_US_UPPER_LIMIT = -1;
   java.lang.String parentPartUs;
   /**
    * 재료비 가공 -1레벨 상위 partNo의 u/s
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getParentPartUs() {
      return parentPartUs;
   }
   /**
    * 재료비 가공 -1레벨 상위 partNo의 u/s
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setParentPartUs(java.lang.String parentPartUs) throws wt.util.WTPropertyVetoException {
      parentPartUsValidate(parentPartUs);
      this.parentPartUs = parentPartUs;
   }
   void parentPartUsValidate(java.lang.String parentPartUs) throws wt.util.WTPropertyVetoException {
      if (PARENT_PART_US_UPPER_LIMIT < 1) {
         try { PARENT_PART_US_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("parentPartUs").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PARENT_PART_US_UPPER_LIMIT = 200; }
      }
      if (parentPartUs != null && !wt.fc.PersistenceHelper.checkStoredLength(parentPartUs.toString(), PARENT_PART_US_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentPartUs"), java.lang.String.valueOf(java.lang.Math.min(PARENT_PART_US_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "parentPartUs", this.parentPartUs, parentPartUs));
   }

   /**
    * 재료비 가공 -bom level
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String BOM_LEVEL = "bomLevel";
   static int BOM_LEVEL_UPPER_LIMIT = -1;
   java.lang.String bomLevel;
   /**
    * 재료비 가공 -bom level
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getBomLevel() {
      return bomLevel;
   }
   /**
    * 재료비 가공 -bom level
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setBomLevel(java.lang.String bomLevel) throws wt.util.WTPropertyVetoException {
      bomLevelValidate(bomLevel);
      this.bomLevel = bomLevel;
   }
   void bomLevelValidate(java.lang.String bomLevel) throws wt.util.WTPropertyVetoException {
      if (BOM_LEVEL_UPPER_LIMIT < 1) {
         try { BOM_LEVEL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bomLevel").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOM_LEVEL_UPPER_LIMIT = 200; }
      }
      if (bomLevel != null && !wt.fc.PersistenceHelper.checkStoredLength(bomLevel.toString(), BOM_LEVEL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bomLevel"), java.lang.String.valueOf(java.lang.Math.min(BOM_LEVEL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bomLevel", this.bomLevel, bomLevel));
   }

   /**
    * 재료비 가공 - 부품번호(이관시 기준이 되는 품번)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TARGET_PART_NO = "targetPartNo";
   static int TARGET_PART_NO_UPPER_LIMIT = -1;
   java.lang.String targetPartNo;
   /**
    * 재료비 가공 - 부품번호(이관시 기준이 되는 품번)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getTargetPartNo() {
      return targetPartNo;
   }
   /**
    * 재료비 가공 - 부품번호(이관시 기준이 되는 품번)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setTargetPartNo(java.lang.String targetPartNo) throws wt.util.WTPropertyVetoException {
      targetPartNoValidate(targetPartNo);
      this.targetPartNo = targetPartNo;
   }
   void targetPartNoValidate(java.lang.String targetPartNo) throws wt.util.WTPropertyVetoException {
      if (TARGET_PART_NO_UPPER_LIMIT < 1) {
         try { TARGET_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_PART_NO_UPPER_LIMIT = 200; }
      }
      if (targetPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(targetPartNo.toString(), TARGET_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetPartNo"), java.lang.String.valueOf(java.lang.Math.min(TARGET_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetPartNo", this.targetPartNo, targetPartNo));
   }

   /**
    * 재료비 가공 - 부품명(이관시 기준이 되는 품명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TARGET_PART_NAME = "targetPartName";
   static int TARGET_PART_NAME_UPPER_LIMIT = -1;
   java.lang.String targetPartName;
   /**
    * 재료비 가공 - 부품명(이관시 기준이 되는 품명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getTargetPartName() {
      return targetPartName;
   }
   /**
    * 재료비 가공 - 부품명(이관시 기준이 되는 품명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setTargetPartName(java.lang.String targetPartName) throws wt.util.WTPropertyVetoException {
      targetPartNameValidate(targetPartName);
      this.targetPartName = targetPartName;
   }
   void targetPartNameValidate(java.lang.String targetPartName) throws wt.util.WTPropertyVetoException {
      if (TARGET_PART_NAME_UPPER_LIMIT < 1) {
         try { TARGET_PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetPartName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_PART_NAME_UPPER_LIMIT = 200; }
      }
      if (targetPartName != null && !wt.fc.PersistenceHelper.checkStoredLength(targetPartName.toString(), TARGET_PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetPartName"), java.lang.String.valueOf(java.lang.Math.min(TARGET_PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetPartName", this.targetPartName, targetPartName));
   }

   /**
    * 재료비 가공 - 수지제번 (원재료번호)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String SUJI_PART_NO = "sujiPartNo";
   static int SUJI_PART_NO_UPPER_LIMIT = -1;
   java.lang.String sujiPartNo;
   /**
    * 재료비 가공 - 수지제번 (원재료번호)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getSujiPartNo() {
      return sujiPartNo;
   }
   /**
    * 재료비 가공 - 수지제번 (원재료번호)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setSujiPartNo(java.lang.String sujiPartNo) throws wt.util.WTPropertyVetoException {
      sujiPartNoValidate(sujiPartNo);
      this.sujiPartNo = sujiPartNo;
   }
   void sujiPartNoValidate(java.lang.String sujiPartNo) throws wt.util.WTPropertyVetoException {
      if (SUJI_PART_NO_UPPER_LIMIT < 1) {
         try { SUJI_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_PART_NO_UPPER_LIMIT = 200; }
      }
      if (sujiPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiPartNo.toString(), SUJI_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiPartNo"), java.lang.String.valueOf(java.lang.Math.min(SUJI_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiPartNo", this.sujiPartNo, sujiPartNo));
   }

   /**
    * 재료비 가공 - 수지명칭 (원재료명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String SUJI_PART_NAME = "sujiPartName";
   static int SUJI_PART_NAME_UPPER_LIMIT = -1;
   java.lang.String sujiPartName;
   /**
    * 재료비 가공 - 수지명칭 (원재료명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getSujiPartName() {
      return sujiPartName;
   }
   /**
    * 재료비 가공 - 수지명칭 (원재료명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setSujiPartName(java.lang.String sujiPartName) throws wt.util.WTPropertyVetoException {
      sujiPartNameValidate(sujiPartName);
      this.sujiPartName = sujiPartName;
   }
   void sujiPartNameValidate(java.lang.String sujiPartName) throws wt.util.WTPropertyVetoException {
      if (SUJI_PART_NAME_UPPER_LIMIT < 1) {
         try { SUJI_PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiPartName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_PART_NAME_UPPER_LIMIT = 200; }
      }
      if (sujiPartName != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiPartName.toString(), SUJI_PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiPartName"), java.lang.String.valueOf(java.lang.Math.min(SUJI_PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiPartName", this.sujiPartName, sujiPartName));
   }

   /**
    * 재료비 가공 - 비철제번 (원재료번호)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String METAL_PART_NO = "metalPartNo";
   static int METAL_PART_NO_UPPER_LIMIT = -1;
   java.lang.String metalPartNo;
   /**
    * 재료비 가공 - 비철제번 (원재료번호)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMetalPartNo() {
      return metalPartNo;
   }
   /**
    * 재료비 가공 - 비철제번 (원재료번호)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMetalPartNo(java.lang.String metalPartNo) throws wt.util.WTPropertyVetoException {
      metalPartNoValidate(metalPartNo);
      this.metalPartNo = metalPartNo;
   }
   void metalPartNoValidate(java.lang.String metalPartNo) throws wt.util.WTPropertyVetoException {
      if (METAL_PART_NO_UPPER_LIMIT < 1) {
         try { METAL_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_PART_NO_UPPER_LIMIT = 200; }
      }
      if (metalPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(metalPartNo.toString(), METAL_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalPartNo"), java.lang.String.valueOf(java.lang.Math.min(METAL_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalPartNo", this.metalPartNo, metalPartNo));
   }

   /**
    * 재료비 가공 - 비철명칭 (원재료명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String METAL_PART_NAME = "metalPartName";
   static int METAL_PART_NAME_UPPER_LIMIT = -1;
   java.lang.String metalPartName;
   /**
    * 재료비 가공 - 비철명칭 (원재료명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMetalPartName() {
      return metalPartName;
   }
   /**
    * 재료비 가공 - 비철명칭 (원재료명)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMetalPartName(java.lang.String metalPartName) throws wt.util.WTPropertyVetoException {
      metalPartNameValidate(metalPartName);
      this.metalPartName = metalPartName;
   }
   void metalPartNameValidate(java.lang.String metalPartName) throws wt.util.WTPropertyVetoException {
      if (METAL_PART_NAME_UPPER_LIMIT < 1) {
         try { METAL_PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalPartName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_PART_NAME_UPPER_LIMIT = 200; }
      }
      if (metalPartName != null && !wt.fc.PersistenceHelper.checkStoredLength(metalPartName.toString(), METAL_PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalPartName"), java.lang.String.valueOf(java.lang.Math.min(METAL_PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalPartName", this.metalPartName, metalPartName));
   }

   /**
    * 최종 업체명(수지나 비철일 경우 maker, 그게 아니면 company)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String LAST_COMPANY = "lastCompany";
   static int LAST_COMPANY_UPPER_LIMIT = -1;
   java.lang.String lastCompany;
   /**
    * 최종 업체명(수지나 비철일 경우 maker, 그게 아니면 company)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getLastCompany() {
      return lastCompany;
   }
   /**
    * 최종 업체명(수지나 비철일 경우 maker, 그게 아니면 company)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setLastCompany(java.lang.String lastCompany) throws wt.util.WTPropertyVetoException {
      lastCompanyValidate(lastCompany);
      this.lastCompany = lastCompany;
   }
   void lastCompanyValidate(java.lang.String lastCompany) throws wt.util.WTPropertyVetoException {
      if (LAST_COMPANY_UPPER_LIMIT < 1) {
         try { LAST_COMPANY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastCompany").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_COMPANY_UPPER_LIMIT = 200; }
      }
      if (lastCompany != null && !wt.fc.PersistenceHelper.checkStoredLength(lastCompany.toString(), LAST_COMPANY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastCompany"), java.lang.String.valueOf(java.lang.Math.min(LAST_COMPANY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastCompany", this.lastCompany, lastCompany));
   }

   /**
    * 원재료 업체명 (수지,비철) 아직 원가프로그램에 없는 컬럼
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MATERIAL_COMPANY = "materialCompany";
   static int MATERIAL_COMPANY_UPPER_LIMIT = -1;
   java.lang.String materialCompany;
   /**
    * 원재료 업체명 (수지,비철) 아직 원가프로그램에 없는 컬럼
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMaterialCompany() {
      return materialCompany;
   }
   /**
    * 원재료 업체명 (수지,비철) 아직 원가프로그램에 없는 컬럼
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMaterialCompany(java.lang.String materialCompany) throws wt.util.WTPropertyVetoException {
      materialCompanyValidate(materialCompany);
      this.materialCompany = materialCompany;
   }
   void materialCompanyValidate(java.lang.String materialCompany) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_COMPANY_UPPER_LIMIT < 1) {
         try { MATERIAL_COMPANY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("materialCompany").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_COMPANY_UPPER_LIMIT = 200; }
      }
      if (materialCompany != null && !wt.fc.PersistenceHelper.checkStoredLength(materialCompany.toString(), MATERIAL_COMPANY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialCompany"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_COMPANY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "materialCompany", this.materialCompany, materialCompany));
   }

   /**
    * 단가 - (구매품 or 수지 or 비철)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String LAST_UNIT_COST = "lastUnitCost";
   static int LAST_UNIT_COST_UPPER_LIMIT = -1;
   java.lang.String lastUnitCost;
   /**
    * 단가 - (구매품 or 수지 or 비철)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getLastUnitCost() {
      return lastUnitCost;
   }
   /**
    * 단가 - (구매품 or 수지 or 비철)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setLastUnitCost(java.lang.String lastUnitCost) throws wt.util.WTPropertyVetoException {
      lastUnitCostValidate(lastUnitCost);
      this.lastUnitCost = lastUnitCost;
   }
   void lastUnitCostValidate(java.lang.String lastUnitCost) throws wt.util.WTPropertyVetoException {
      if (LAST_UNIT_COST_UPPER_LIMIT < 1) {
         try { LAST_UNIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastUnitCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_UNIT_COST_UPPER_LIMIT = 200; }
      }
      if (lastUnitCost != null && !wt.fc.PersistenceHelper.checkStoredLength(lastUnitCost.toString(), LAST_UNIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastUnitCost"), java.lang.String.valueOf(java.lang.Math.min(LAST_UNIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastUnitCost", this.lastUnitCost, lastUnitCost));
   }

   /**
    * 단위 - (구매품 or 수지 or 비철) EA, G
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String LAST_UNIT = "lastUnit";
   static int LAST_UNIT_UPPER_LIMIT = -1;
   java.lang.String lastUnit;
   /**
    * 단위 - (구매품 or 수지 or 비철) EA, G
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getLastUnit() {
      return lastUnit;
   }
   /**
    * 단위 - (구매품 or 수지 or 비철) EA, G
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setLastUnit(java.lang.String lastUnit) throws wt.util.WTPropertyVetoException {
      lastUnitValidate(lastUnit);
      this.lastUnit = lastUnit;
   }
   void lastUnitValidate(java.lang.String lastUnit) throws wt.util.WTPropertyVetoException {
      if (LAST_UNIT_UPPER_LIMIT < 1) {
         try { LAST_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_UNIT_UPPER_LIMIT = 200; }
      }
      if (lastUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(lastUnit.toString(), LAST_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastUnit"), java.lang.String.valueOf(java.lang.Math.min(LAST_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastUnit", this.lastUnit, lastUnit));
   }

   /**
    * 통화 - (구매품 or 수지 or 비철)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String LAST_MONETARY_UNIT = "lastMonetaryUnit";
   static int LAST_MONETARY_UNIT_UPPER_LIMIT = -1;
   java.lang.String lastMonetaryUnit;
   /**
    * 통화 - (구매품 or 수지 or 비철)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getLastMonetaryUnit() {
      return lastMonetaryUnit;
   }
   /**
    * 통화 - (구매품 or 수지 or 비철)
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setLastMonetaryUnit(java.lang.String lastMonetaryUnit) throws wt.util.WTPropertyVetoException {
      lastMonetaryUnitValidate(lastMonetaryUnit);
      this.lastMonetaryUnit = lastMonetaryUnit;
   }
   void lastMonetaryUnitValidate(java.lang.String lastMonetaryUnit) throws wt.util.WTPropertyVetoException {
      if (LAST_MONETARY_UNIT_UPPER_LIMIT < 1) {
         try { LAST_MONETARY_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastMonetaryUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_MONETARY_UNIT_UPPER_LIMIT = 200; }
      }
      if (lastMonetaryUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(lastMonetaryUnit.toString(), LAST_MONETARY_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastMonetaryUnit"), java.lang.String.valueOf(java.lang.Math.min(LAST_MONETARY_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastMonetaryUnit", this.lastMonetaryUnit, lastMonetaryUnit));
   }

   /**
    * 구매단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String P_UNIT_COST = "pUnitCost";
   static int P_UNIT_COST_UPPER_LIMIT = -1;
   java.lang.String pUnitCost;
   /**
    * 구매단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPUnitCost() {
      return pUnitCost;
   }
   /**
    * 구매단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPUnitCost(java.lang.String pUnitCost) throws wt.util.WTPropertyVetoException {
      pUnitCostValidate(pUnitCost);
      this.pUnitCost = pUnitCost;
   }
   void pUnitCostValidate(java.lang.String pUnitCost) throws wt.util.WTPropertyVetoException {
      if (P_UNIT_COST_UPPER_LIMIT < 1) {
         try { P_UNIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pUnitCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_UNIT_COST_UPPER_LIMIT = 200; }
      }
      if (pUnitCost != null && !wt.fc.PersistenceHelper.checkStoredLength(pUnitCost.toString(), P_UNIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pUnitCost"), java.lang.String.valueOf(java.lang.Math.min(P_UNIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pUnitCost", this.pUnitCost, pUnitCost));
   }

   /**
    * 구매단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String P_MONETARY_UNIT = "pMonetaryUnit";
   static int P_MONETARY_UNIT_UPPER_LIMIT = -1;
   java.lang.String pMonetaryUnit;
   /**
    * 구매단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPMonetaryUnit() {
      return pMonetaryUnit;
   }
   /**
    * 구매단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPMonetaryUnit(java.lang.String pMonetaryUnit) throws wt.util.WTPropertyVetoException {
      pMonetaryUnitValidate(pMonetaryUnit);
      this.pMonetaryUnit = pMonetaryUnit;
   }
   void pMonetaryUnitValidate(java.lang.String pMonetaryUnit) throws wt.util.WTPropertyVetoException {
      if (P_MONETARY_UNIT_UPPER_LIMIT < 1) {
         try { P_MONETARY_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pMonetaryUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_MONETARY_UNIT_UPPER_LIMIT = 200; }
      }
      if (pMonetaryUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(pMonetaryUnit.toString(), P_MONETARY_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pMonetaryUnit"), java.lang.String.valueOf(java.lang.Math.min(P_MONETARY_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pMonetaryUnit", this.pMonetaryUnit, pMonetaryUnit));
   }

   /**
    * 기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String P_UNIT = "pUnit";
   static int P_UNIT_UPPER_LIMIT = -1;
   java.lang.String pUnit = "EA";
   /**
    * 기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPUnit() {
      return pUnit;
   }
   /**
    * 기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPUnit(java.lang.String pUnit) throws wt.util.WTPropertyVetoException {
      pUnitValidate(pUnit);
      this.pUnit = pUnit;
   }
   void pUnitValidate(java.lang.String pUnit) throws wt.util.WTPropertyVetoException {
      if (P_UNIT_UPPER_LIMIT < 1) {
         try { P_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_UNIT_UPPER_LIMIT = 200; }
      }
      if (pUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(pUnit.toString(), P_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pUnit"), java.lang.String.valueOf(java.lang.Math.min(P_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pUnit", this.pUnit, pUnit));
   }

   /**
    * 수지 원재료 단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_SUJI_PRICE = "mtrSujiPrice";
   static int MTR_SUJI_PRICE_UPPER_LIMIT = -1;
   java.lang.String mtrSujiPrice;
   /**
    * 수지 원재료 단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrSujiPrice() {
      return mtrSujiPrice;
   }
   /**
    * 수지 원재료 단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrSujiPrice(java.lang.String mtrSujiPrice) throws wt.util.WTPropertyVetoException {
      mtrSujiPriceValidate(mtrSujiPrice);
      this.mtrSujiPrice = mtrSujiPrice;
   }
   void mtrSujiPriceValidate(java.lang.String mtrSujiPrice) throws wt.util.WTPropertyVetoException {
      if (MTR_SUJI_PRICE_UPPER_LIMIT < 1) {
         try { MTR_SUJI_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrSujiPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_SUJI_PRICE_UPPER_LIMIT = 200; }
      }
      if (mtrSujiPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrSujiPrice.toString(), MTR_SUJI_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrSujiPrice"), java.lang.String.valueOf(java.lang.Math.min(MTR_SUJI_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrSujiPrice", this.mtrSujiPrice, mtrSujiPrice));
   }

   /**
    * 수지화폐단위 - KRW로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_SUJI_MONETARY_UNIT = "mtrSujiMonetaryUnit";
   static int MTR_SUJI_MONETARY_UNIT_UPPER_LIMIT = -1;
   java.lang.String mtrSujiMonetaryUnit = "KRW";
   /**
    * 수지화폐단위 - KRW로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrSujiMonetaryUnit() {
      return mtrSujiMonetaryUnit;
   }
   /**
    * 수지화폐단위 - KRW로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrSujiMonetaryUnit(java.lang.String mtrSujiMonetaryUnit) throws wt.util.WTPropertyVetoException {
      mtrSujiMonetaryUnitValidate(mtrSujiMonetaryUnit);
      this.mtrSujiMonetaryUnit = mtrSujiMonetaryUnit;
   }
   void mtrSujiMonetaryUnitValidate(java.lang.String mtrSujiMonetaryUnit) throws wt.util.WTPropertyVetoException {
      if (MTR_SUJI_MONETARY_UNIT_UPPER_LIMIT < 1) {
         try { MTR_SUJI_MONETARY_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrSujiMonetaryUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_SUJI_MONETARY_UNIT_UPPER_LIMIT = 200; }
      }
      if (mtrSujiMonetaryUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrSujiMonetaryUnit.toString(), MTR_SUJI_MONETARY_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrSujiMonetaryUnit"), java.lang.String.valueOf(java.lang.Math.min(MTR_SUJI_MONETARY_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrSujiMonetaryUnit", this.mtrSujiMonetaryUnit, mtrSujiMonetaryUnit));
   }

   /**
    * 수지기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_SUJI_UNIT = "mtrSujiUnit";
   static int MTR_SUJI_UNIT_UPPER_LIMIT = -1;
   java.lang.String mtrSujiUnit = "EA";
   /**
    * 수지기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrSujiUnit() {
      return mtrSujiUnit;
   }
   /**
    * 수지기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrSujiUnit(java.lang.String mtrSujiUnit) throws wt.util.WTPropertyVetoException {
      mtrSujiUnitValidate(mtrSujiUnit);
      this.mtrSujiUnit = mtrSujiUnit;
   }
   void mtrSujiUnitValidate(java.lang.String mtrSujiUnit) throws wt.util.WTPropertyVetoException {
      if (MTR_SUJI_UNIT_UPPER_LIMIT < 1) {
         try { MTR_SUJI_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrSujiUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_SUJI_UNIT_UPPER_LIMIT = 200; }
      }
      if (mtrSujiUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrSujiUnit.toString(), MTR_SUJI_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrSujiUnit"), java.lang.String.valueOf(java.lang.Math.min(MTR_SUJI_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrSujiUnit", this.mtrSujiUnit, mtrSujiUnit));
   }

   /**
    * 비철 원재료 단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_METAL_PRICE = "mtrMetalPrice";
   static int MTR_METAL_PRICE_UPPER_LIMIT = -1;
   java.lang.String mtrMetalPrice;
   /**
    * 비철 원재료 단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrMetalPrice() {
      return mtrMetalPrice;
   }
   /**
    * 비철 원재료 단가
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrMetalPrice(java.lang.String mtrMetalPrice) throws wt.util.WTPropertyVetoException {
      mtrMetalPriceValidate(mtrMetalPrice);
      this.mtrMetalPrice = mtrMetalPrice;
   }
   void mtrMetalPriceValidate(java.lang.String mtrMetalPrice) throws wt.util.WTPropertyVetoException {
      if (MTR_METAL_PRICE_UPPER_LIMIT < 1) {
         try { MTR_METAL_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrMetalPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_METAL_PRICE_UPPER_LIMIT = 200; }
      }
      if (mtrMetalPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrMetalPrice.toString(), MTR_METAL_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrMetalPrice"), java.lang.String.valueOf(java.lang.Math.min(MTR_METAL_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrMetalPrice", this.mtrMetalPrice, mtrMetalPrice));
   }

   /**
    * 비철화폐단위 - KRW로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_METAL_MONETARY_UNIT = "mtrMetalMonetaryUnit";
   static int MTR_METAL_MONETARY_UNIT_UPPER_LIMIT = -1;
   java.lang.String mtrMetalMonetaryUnit = "KRW";
   /**
    * 비철화폐단위 - KRW로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrMetalMonetaryUnit() {
      return mtrMetalMonetaryUnit;
   }
   /**
    * 비철화폐단위 - KRW로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrMetalMonetaryUnit(java.lang.String mtrMetalMonetaryUnit) throws wt.util.WTPropertyVetoException {
      mtrMetalMonetaryUnitValidate(mtrMetalMonetaryUnit);
      this.mtrMetalMonetaryUnit = mtrMetalMonetaryUnit;
   }
   void mtrMetalMonetaryUnitValidate(java.lang.String mtrMetalMonetaryUnit) throws wt.util.WTPropertyVetoException {
      if (MTR_METAL_MONETARY_UNIT_UPPER_LIMIT < 1) {
         try { MTR_METAL_MONETARY_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrMetalMonetaryUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_METAL_MONETARY_UNIT_UPPER_LIMIT = 200; }
      }
      if (mtrMetalMonetaryUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrMetalMonetaryUnit.toString(), MTR_METAL_MONETARY_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrMetalMonetaryUnit"), java.lang.String.valueOf(java.lang.Math.min(MTR_METAL_MONETARY_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrMetalMonetaryUnit", this.mtrMetalMonetaryUnit, mtrMetalMonetaryUnit));
   }

   /**
    * 비철기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String MTR_METAL_UNIT = "mtrMetalUnit";
   static int MTR_METAL_UNIT_UPPER_LIMIT = -1;
   java.lang.String mtrMetalUnit = "EA";
   /**
    * 비철기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMtrMetalUnit() {
      return mtrMetalUnit;
   }
   /**
    * 비철기준단위 - EA 로 하드코딩
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMtrMetalUnit(java.lang.String mtrMetalUnit) throws wt.util.WTPropertyVetoException {
      mtrMetalUnitValidate(mtrMetalUnit);
      this.mtrMetalUnit = mtrMetalUnit;
   }
   void mtrMetalUnitValidate(java.lang.String mtrMetalUnit) throws wt.util.WTPropertyVetoException {
      if (MTR_METAL_UNIT_UPPER_LIMIT < 1) {
         try { MTR_METAL_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrMetalUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_METAL_UNIT_UPPER_LIMIT = 200; }
      }
      if (mtrMetalUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrMetalUnit.toString(), MTR_METAL_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrMetalUnit"), java.lang.String.valueOf(java.lang.Math.min(MTR_METAL_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrMetalUnit", this.mtrMetalUnit, mtrMetalUnit));
   }

   /**
    * LME시세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String METAL_LME_STD = "metalLmeStd";
   static int METAL_LME_STD_UPPER_LIMIT = -1;
   java.lang.String metalLmeStd;
   /**
    * LME시세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getMetalLmeStd() {
      return metalLmeStd;
   }
   /**
    * LME시세
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setMetalLmeStd(java.lang.String metalLmeStd) throws wt.util.WTPropertyVetoException {
      metalLmeStdValidate(metalLmeStd);
      this.metalLmeStd = metalLmeStd;
   }
   void metalLmeStdValidate(java.lang.String metalLmeStd) throws wt.util.WTPropertyVetoException {
      if (METAL_LME_STD_UPPER_LIMIT < 1) {
         try { METAL_LME_STD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalLmeStd").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_LME_STD_UPPER_LIMIT = 200; }
      }
      if (metalLmeStd != null && !wt.fc.PersistenceHelper.checkStoredLength(metalLmeStd.toString(), METAL_LME_STD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalLmeStd"), java.lang.String.valueOf(java.lang.Math.min(METAL_LME_STD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalLmeStd", this.metalLmeStd, metalLmeStd));
   }

   /**
    * 구매 환율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String P_MONETARY_UNIT_CURRENCY = "pMonetaryUnitCurrency";
   static int P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String pMonetaryUnitCurrency;
   /**
    * 구매 환율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getPMonetaryUnitCurrency() {
      return pMonetaryUnitCurrency;
   }
   /**
    * 구매 환율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPMonetaryUnitCurrency(java.lang.String pMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      pMonetaryUnitCurrencyValidate(pMonetaryUnitCurrency);
      this.pMonetaryUnitCurrency = pMonetaryUnitCurrency;
   }
   void pMonetaryUnitCurrencyValidate(java.lang.String pMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pMonetaryUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (pMonetaryUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(pMonetaryUnitCurrency.toString(), P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pMonetaryUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pMonetaryUnitCurrency", this.pMonetaryUnitCurrency, pMonetaryUnitCurrency));
   }

   /**
    * 수량차이-투입
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String QA_DIFF_INPUT = "qaDiffInput";
   static int QA_DIFF_INPUT_UPPER_LIMIT = -1;
   java.lang.String qaDiffInput;
   /**
    * 수량차이-투입
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getQaDiffInput() {
      return qaDiffInput;
   }
   /**
    * 수량차이-투입
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setQaDiffInput(java.lang.String qaDiffInput) throws wt.util.WTPropertyVetoException {
      qaDiffInputValidate(qaDiffInput);
      this.qaDiffInput = qaDiffInput;
   }
   void qaDiffInputValidate(java.lang.String qaDiffInput) throws wt.util.WTPropertyVetoException {
      if (QA_DIFF_INPUT_UPPER_LIMIT < 1) {
         try { QA_DIFF_INPUT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("qaDiffInput").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QA_DIFF_INPUT_UPPER_LIMIT = 200; }
      }
      if (qaDiffInput != null && !wt.fc.PersistenceHelper.checkStoredLength(qaDiffInput.toString(), QA_DIFF_INPUT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "qaDiffInput"), java.lang.String.valueOf(java.lang.Math.min(QA_DIFF_INPUT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "qaDiffInput", this.qaDiffInput, qaDiffInput));
   }

   /**
    * 수량차이-단위
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String QA_DIFF_UNIT = "qaDiffUnit";
   static int QA_DIFF_UNIT_UPPER_LIMIT = -1;
   java.lang.String qaDiffUnit;
   /**
    * 수량차이-단위
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getQaDiffUnit() {
      return qaDiffUnit;
   }
   /**
    * 수량차이-단위
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setQaDiffUnit(java.lang.String qaDiffUnit) throws wt.util.WTPropertyVetoException {
      qaDiffUnitValidate(qaDiffUnit);
      this.qaDiffUnit = qaDiffUnit;
   }
   void qaDiffUnitValidate(java.lang.String qaDiffUnit) throws wt.util.WTPropertyVetoException {
      if (QA_DIFF_UNIT_UPPER_LIMIT < 1) {
         try { QA_DIFF_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("qaDiffUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QA_DIFF_UNIT_UPPER_LIMIT = 200; }
      }
      if (qaDiffUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(qaDiffUnit.toString(), QA_DIFF_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "qaDiffUnit"), java.lang.String.valueOf(java.lang.Math.min(QA_DIFF_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "qaDiffUnit", this.qaDiffUnit, qaDiffUnit));
   }

   /**
    * 생산Loss율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PRODUCT_LOSS_RATE = "productLossRate";
   static int PRODUCT_LOSS_RATE_UPPER_LIMIT = -1;
   java.lang.String productLossRate;
   /**
    * 생산Loss율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getProductLossRate() {
      return productLossRate;
   }
   /**
    * 생산Loss율
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * 제품중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PRODUCT_WEIGHT = "productWeight";
   static int PRODUCT_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String productWeight;
   /**
    * 제품중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getProductWeight() {
      return productWeight;
   }
   /**
    * 제품중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setProductWeight(java.lang.String productWeight) throws wt.util.WTPropertyVetoException {
      productWeightValidate(productWeight);
      this.productWeight = productWeight;
   }
   void productWeightValidate(java.lang.String productWeight) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_WEIGHT_UPPER_LIMIT < 1) {
         try { PRODUCT_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (productWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(productWeight.toString(), PRODUCT_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productWeight"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productWeight", this.productWeight, productWeight));
   }

   /**
    * 스크랩중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String SCRAP_WEIGHT = "scrapWeight";
   static int SCRAP_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String scrapWeight;
   /**
    * 스크랩중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getScrapWeight() {
      return scrapWeight;
   }
   /**
    * 스크랩중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setScrapWeight(java.lang.String scrapWeight) throws wt.util.WTPropertyVetoException {
      scrapWeightValidate(scrapWeight);
      this.scrapWeight = scrapWeight;
   }
   void scrapWeightValidate(java.lang.String scrapWeight) throws wt.util.WTPropertyVetoException {
      if (SCRAP_WEIGHT_UPPER_LIMIT < 1) {
         try { SCRAP_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (scrapWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapWeight.toString(), SCRAP_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapWeight"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapWeight", this.scrapWeight, scrapWeight));
   }

   /**
    * 총중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TOTAL_WEIGHT = "totalWeight";
   static int TOTAL_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String totalWeight;
   /**
    * 총중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getTotalWeight() {
      return totalWeight;
   }
   /**
    * 총중량
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setTotalWeight(java.lang.String totalWeight) throws wt.util.WTPropertyVetoException {
      totalWeightValidate(totalWeight);
      this.totalWeight = totalWeight;
   }
   void totalWeightValidate(java.lang.String totalWeight) throws wt.util.WTPropertyVetoException {
      if (TOTAL_WEIGHT_UPPER_LIMIT < 1) {
         try { TOTAL_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("totalWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOTAL_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (totalWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(totalWeight.toString(), TOTAL_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "totalWeight"), java.lang.String.valueOf(java.lang.Math.min(TOTAL_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "totalWeight", this.totalWeight, totalWeight));
   }

   /**
    * ERP전송일
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TRANSFER_DATE = "transferDate";
   java.sql.Timestamp transferDate;
   /**
    * ERP전송일
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.sql.Timestamp getTransferDate() {
      return transferDate;
   }
   /**
    * ERP전송일
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TRANSFER_FLAG = "transferFlag";
   static int TRANSFER_FLAG_UPPER_LIMIT = -1;
   java.lang.String transferFlag;
   /**
    * ERP전송여부
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getTransferFlag() {
      return transferFlag;
   }
   /**
    * ERP전송여부
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TRANSFER_MSG = "transferMsg";
   static int TRANSFER_MSG_UPPER_LIMIT = -1;
   java.lang.String transferMsg;
   /**
    * ERP전송로그
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public java.lang.String getTransferMsg() {
      return transferMsg;
   }
   /**
    * ERP전송로그
    *
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PARENT_HISTORY = "parentHistory";
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PARENT_HISTORY_REFERENCE = "parentHistoryReference";
   wt.fc.ObjectReference parentHistoryReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public ext.ket.cost.entity.CostInterfaceHistory getParentHistory() {
      return (parentHistoryReference != null) ? (ext.ket.cost.entity.CostInterfaceHistory) parentHistoryReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public wt.fc.ObjectReference getParentHistoryReference() {
      return parentHistoryReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setParentHistory(ext.ket.cost.entity.CostInterfaceHistory the_parentHistory) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentHistoryReference(the_parentHistory == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostInterfaceHistory) the_parentHistory));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setParentHistoryReference(wt.fc.ObjectReference the_parentHistoryReference) throws wt.util.WTPropertyVetoException {
      parentHistoryReferenceValidate(the_parentHistoryReference);
      parentHistoryReference = (wt.fc.ObjectReference) the_parentHistoryReference;
   }
   void parentHistoryReferenceValidate(wt.fc.ObjectReference the_parentHistoryReference) throws wt.util.WTPropertyVetoException {
      if (the_parentHistoryReference == null || the_parentHistoryReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentHistoryReference") },
               new java.beans.PropertyChangeEvent(this, "parentHistoryReference", this.parentHistoryReference, parentHistoryReference));
      if (the_parentHistoryReference != null && the_parentHistoryReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.CostInterfaceHistory.class.isAssignableFrom(the_parentHistoryReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentHistoryReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "parentHistoryReference", this.parentHistoryReference, parentHistoryReference));
   }

   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TASK = "task";
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String TASK_REFERENCE = "taskReference";
   wt.fc.ObjectReference taskReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public e3ps.project.E3PSTask getTask() {
      return (taskReference != null) ? (e3ps.project.E3PSTask) taskReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public wt.fc.ObjectReference getTaskReference() {
      return taskReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setTask(e3ps.project.E3PSTask the_task) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTaskReference(the_task == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSTask) the_task));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String COST_PART = "costPart";
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String COST_PART_REFERENCE = "costPartReference";
   wt.fc.ObjectReference costPartReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public ext.ket.cost.entity.CostPart getCostPart() {
      return (costPartReference != null) ? (ext.ket.cost.entity.CostPart) costPartReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public wt.fc.ObjectReference getCostPartReference() {
      return costPartReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setCostPart(ext.ket.cost.entity.CostPart the_costPart) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostPartReference(the_costPart == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostPart) the_costPart));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public e3ps.project.E3PSProjectMaster getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProjectMaster) projectReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setProject(e3ps.project.E3PSProjectMaster the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProjectMaster) the_project));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PART_LIST = "partList";
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public static final java.lang.String PART_LIST_REFERENCE = "partListReference";
   wt.fc.ObjectReference partListReference;
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public ext.ket.cost.entity.PartList getPartList() {
      return (partListReference != null) ? (ext.ket.cost.entity.PartList) partListReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public wt.fc.ObjectReference getPartListReference() {
      return partListReference;
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
    */
   public void setPartList(ext.ket.cost.entity.PartList the_partList) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartListReference(the_partList == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.PartList) the_partList));
   }
   /**
    * @see ext.ket.cost.entity.CostInterfaceChildHistory
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

   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent() {
      return (parentReference != null) ? (e3ps.common.impl.Tree) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.impl.Tree) the_parent));
   }
   /**
    * @see e3ps.common.impl.Tree
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
            !e3ps.common.impl.Tree.class.isAssignableFrom(the_parentReference.getReferencedClass()))
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

   public static final long EXTERNALIZATION_VERSION_UID = -8746303150760254721L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( apUnitCostOption );
      output.writeObject( apUnitCostVal );
      output.writeObject( assyLossExpr );
      output.writeObject( bomLevel );
      output.writeObject( coManageExpr );
      output.writeObject( company );
      output.writeObject( convertWorker );
      output.writeObject( corporateMarginCostExpr );
      output.writeObject( costPartReference );
      output.writeObject( ctSPMAssemble );
      output.writeObject( ctSPMMold );
      output.writeObject( cv );
      output.writeObject( cvAssemble );
      output.writeObject( cvMold );
      output.writeObject( defectCostExpr );
      output.writeObject( dfCt );
      output.writeObject( dfDirectExpenses );
      output.writeObject( dfDirectLaborCost );
      output.writeObject( dfInDirectExpenses );
      output.writeObject( dfInDirectLaborCost );
      output.writeObject( dfMachineDep );
      output.writeObject( dfMoldDep );
      output.writeObject( directCost );
      output.writeObject( drStep );
      output.writeObject( efficientRate );
      output.writeObject( elecCostExpr );
      output.writeObject( etcCost );
      output.writeObject( etcReducePrice );
      output.writeObject( facReduceCost );
      output.writeObject( facReducePrice );
      output.writeObject( facilities );
      output.writeObject( inDirectCost );
      output.writeObject( inDirectCost2Expr );
      output.writeObject( inDirectCostExpr );
      output.writeObject( inDirectLaborCost );
      output.writeObject( indirectCostRnd );
      output.writeObject( indirectLabourRate );
      output.writeObject( indirectLabourRndCost );
      output.writeObject( indirectRndRate );
      output.writeObject( laborCost );
      output.writeObject( laborCostExpr );
      output.writeObject( lastCompany );
      output.writeObject( lastMonetaryUnit );
      output.writeObject( lastUnit );
      output.writeObject( lastUnitCost );
      output.writeObject( machineDpcCostExpr );
      output.writeObject( manageMtrLogisExpr );
      output.writeObject( manageMtrdutieExpr );
      output.writeObject( materialCompany );
      output.writeObject( materialCost );
      output.writeObject( materialCostExpr );
      output.writeObject( metalLmeStd );
      output.writeObject( metalPartName );
      output.writeObject( metalPartNo );
      output.writeObject( mftFactory1 );
      output.writeObject( mftFactory2 );
      output.writeObject( moldMaintenance );
      output.writeObject( moldReducePrice );
      output.writeObject( mtrLtCostExpr );
      output.writeObject( mtrLtRateExpr );
      output.writeObject( mtrMetalMonetaryUnit );
      output.writeObject( mtrMetalPrice );
      output.writeObject( mtrMetalUnit );
      output.writeObject( mtrSujiMonetaryUnit );
      output.writeObject( mtrSujiPrice );
      output.writeObject( mtrSujiUnit );
      output.writeObject( orgProductCostTotal );
      output.writeObject( outUnitCost );
      output.writeObject( outUnitCostVal );
      output.writeObject( outputExpr );
      output.writeObject( pMonetaryUnit );
      output.writeObject( pMonetaryUnitCurrency );
      output.writeObject( pUnit );
      output.writeObject( pUnitCost );
      output.writeObject( packUnitPriceOption );
      output.writeObject( packUnitPriceSum );
      output.writeObject( parentHistoryReference );
      output.writeObject( parentPartNo );
      output.writeObject( parentPartUs );
      output.writeObject( parentReference );
      output.writeObject( partListReference );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( partType );
      output.writeObject( payCostLtExpr );
      output.writeObject( payRateLtExpr );
      output.writeObject( personQLH );
      output.writeObject( pjtNo );
      output.writeObject( pjtType );
      output.writeObject( processingCost );
      output.writeObject( productCostTotal );
      output.writeObject( productLossExpr );
      output.writeObject( productLossRate );
      output.writeObject( productWeight );
      output.writeObject( projectReference );
      output.writeObject( qaDiffInput );
      output.writeObject( qaDiffUnit );
      output.writeObject( rawMaterialCostExpr );
      output.writeObject( realPartNo );
      output.writeObject( rndExpr );
      output.writeObject( rootPartNo );
      output.writeObject( salesManageCost );
      output.writeObject( scrapSalesCost );
      output.writeObject( scrapWeight );
      output.writeObject( sopYear );
      output.writeObject( sortLocation );
      output.writeObject( sujiPartName );
      output.writeObject( sujiPartNo );
      output.writeObject( tabaryuExpr );
      output.writeObject( targetPartName );
      output.writeObject( targetPartNo );
      output.writeObject( taskReference );
      output.writeObject( thePersistInfo );
      output.writeObject( totalWeight );
      output.writeObject( transferDate );
      output.writeObject( transferFlag );
      output.writeObject( transferMsg );
      output.writeObject( unitManage );
      output.writeObject( us );
      output.writeObject( version );
      output.writeObject( waers );
      output.writeObject( worker );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostInterfaceChildHistory) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "apUnitCostOption", apUnitCostOption );
      output.setString( "apUnitCostVal", apUnitCostVal );
      output.setString( "assyLossExpr", assyLossExpr );
      output.setString( "bomLevel", bomLevel );
      output.setString( "coManageExpr", coManageExpr );
      output.setString( "company", company );
      output.setString( "convertWorker", convertWorker );
      output.setString( "corporateMarginCostExpr", corporateMarginCostExpr );
      output.writeObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      output.setString( "ctSPMAssemble", ctSPMAssemble );
      output.setString( "ctSPMMold", ctSPMMold );
      output.setString( "cv", cv );
      output.setString( "cvAssemble", cvAssemble );
      output.setString( "cvMold", cvMold );
      output.setString( "defectCostExpr", defectCostExpr );
      output.setString( "dfCt", dfCt );
      output.setString( "dfDirectExpenses", dfDirectExpenses );
      output.setString( "dfDirectLaborCost", dfDirectLaborCost );
      output.setString( "dfInDirectExpenses", dfInDirectExpenses );
      output.setString( "dfInDirectLaborCost", dfInDirectLaborCost );
      output.setString( "dfMachineDep", dfMachineDep );
      output.setString( "dfMoldDep", dfMoldDep );
      output.setString( "directCost", directCost );
      output.setString( "drStep", drStep );
      output.setString( "efficientRate", efficientRate );
      output.setString( "elecCostExpr", elecCostExpr );
      output.setString( "etcCost", etcCost );
      output.setString( "etcReducePrice", etcReducePrice );
      output.setString( "facReduceCost", facReduceCost );
      output.setString( "facReducePrice", facReducePrice );
      output.setString( "facilities", facilities );
      output.setString( "inDirectCost", inDirectCost );
      output.setString( "inDirectCost2Expr", inDirectCost2Expr );
      output.setString( "inDirectCostExpr", inDirectCostExpr );
      output.setString( "inDirectLaborCost", inDirectLaborCost );
      output.setString( "indirectCostRnd", indirectCostRnd );
      output.setString( "indirectLabourRate", indirectLabourRate );
      output.setString( "indirectLabourRndCost", indirectLabourRndCost );
      output.setString( "indirectRndRate", indirectRndRate );
      output.setString( "laborCost", laborCost );
      output.setString( "laborCostExpr", laborCostExpr );
      output.setString( "lastCompany", lastCompany );
      output.setString( "lastMonetaryUnit", lastMonetaryUnit );
      output.setString( "lastUnit", lastUnit );
      output.setString( "lastUnitCost", lastUnitCost );
      output.setString( "machineDpcCostExpr", machineDpcCostExpr );
      output.setString( "manageMtrLogisExpr", manageMtrLogisExpr );
      output.setString( "manageMtrdutieExpr", manageMtrdutieExpr );
      output.setString( "materialCompany", materialCompany );
      output.setString( "materialCost", materialCost );
      output.setString( "materialCostExpr", materialCostExpr );
      output.setString( "metalLmeStd", metalLmeStd );
      output.setString( "metalPartName", metalPartName );
      output.setString( "metalPartNo", metalPartNo );
      output.setString( "mftFactory1", mftFactory1 );
      output.setString( "mftFactory2", mftFactory2 );
      output.setString( "moldMaintenance", moldMaintenance );
      output.setString( "moldReducePrice", moldReducePrice );
      output.setString( "mtrLtCostExpr", mtrLtCostExpr );
      output.setString( "mtrLtRateExpr", mtrLtRateExpr );
      output.setString( "mtrMetalMonetaryUnit", mtrMetalMonetaryUnit );
      output.setString( "mtrMetalPrice", mtrMetalPrice );
      output.setString( "mtrMetalUnit", mtrMetalUnit );
      output.setString( "mtrSujiMonetaryUnit", mtrSujiMonetaryUnit );
      output.setString( "mtrSujiPrice", mtrSujiPrice );
      output.setString( "mtrSujiUnit", mtrSujiUnit );
      output.setString( "orgProductCostTotal", orgProductCostTotal );
      output.setString( "outUnitCost", outUnitCost );
      output.setString( "outUnitCostVal", outUnitCostVal );
      output.setString( "outputExpr", outputExpr );
      output.setString( "pMonetaryUnit", pMonetaryUnit );
      output.setString( "pMonetaryUnitCurrency", pMonetaryUnitCurrency );
      output.setString( "pUnit", pUnit );
      output.setString( "pUnitCost", pUnitCost );
      output.setString( "packUnitPriceOption", packUnitPriceOption );
      output.setString( "packUnitPriceSum", packUnitPriceSum );
      output.writeObject( "parentHistoryReference", parentHistoryReference, wt.fc.ObjectReference.class, true );
      output.setString( "parentPartNo", parentPartNo );
      output.setString( "parentPartUs", parentPartUs );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "partType", partType );
      output.setString( "payCostLtExpr", payCostLtExpr );
      output.setString( "payRateLtExpr", payRateLtExpr );
      output.setString( "personQLH", personQLH );
      output.setString( "pjtNo", pjtNo );
      output.setString( "pjtType", pjtType );
      output.setString( "processingCost", processingCost );
      output.setString( "productCostTotal", productCostTotal );
      output.setString( "productLossExpr", productLossExpr );
      output.setString( "productLossRate", productLossRate );
      output.setString( "productWeight", productWeight );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "qaDiffInput", qaDiffInput );
      output.setString( "qaDiffUnit", qaDiffUnit );
      output.setString( "rawMaterialCostExpr", rawMaterialCostExpr );
      output.setString( "realPartNo", realPartNo );
      output.setString( "rndExpr", rndExpr );
      output.setString( "rootPartNo", rootPartNo );
      output.setString( "salesManageCost", salesManageCost );
      output.setString( "scrapSalesCost", scrapSalesCost );
      output.setString( "scrapWeight", scrapWeight );
      output.setString( "sopYear", sopYear );
      output.setIntObject( "sortLocation", sortLocation );
      output.setString( "sujiPartName", sujiPartName );
      output.setString( "sujiPartNo", sujiPartNo );
      output.setString( "tabaryuExpr", tabaryuExpr );
      output.setString( "targetPartName", targetPartName );
      output.setString( "targetPartNo", targetPartNo );
      output.writeObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "totalWeight", totalWeight );
      output.setTimestamp( "transferDate", transferDate );
      output.setString( "transferFlag", transferFlag );
      output.setString( "transferMsg", transferMsg );
      output.setString( "unitManage", unitManage );
      output.setString( "us", us );
      output.setString( "version", version );
      output.setString( "waers", waers );
      output.setString( "worker", worker );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      apUnitCostOption = input.getString( "apUnitCostOption" );
      apUnitCostVal = input.getString( "apUnitCostVal" );
      assyLossExpr = input.getString( "assyLossExpr" );
      bomLevel = input.getString( "bomLevel" );
      coManageExpr = input.getString( "coManageExpr" );
      company = input.getString( "company" );
      convertWorker = input.getString( "convertWorker" );
      corporateMarginCostExpr = input.getString( "corporateMarginCostExpr" );
      costPartReference = (wt.fc.ObjectReference) input.readObject( "costPartReference", costPartReference, wt.fc.ObjectReference.class, true );
      ctSPMAssemble = input.getString( "ctSPMAssemble" );
      ctSPMMold = input.getString( "ctSPMMold" );
      cv = input.getString( "cv" );
      cvAssemble = input.getString( "cvAssemble" );
      cvMold = input.getString( "cvMold" );
      defectCostExpr = input.getString( "defectCostExpr" );
      dfCt = input.getString( "dfCt" );
      dfDirectExpenses = input.getString( "dfDirectExpenses" );
      dfDirectLaborCost = input.getString( "dfDirectLaborCost" );
      dfInDirectExpenses = input.getString( "dfInDirectExpenses" );
      dfInDirectLaborCost = input.getString( "dfInDirectLaborCost" );
      dfMachineDep = input.getString( "dfMachineDep" );
      dfMoldDep = input.getString( "dfMoldDep" );
      directCost = input.getString( "directCost" );
      drStep = input.getString( "drStep" );
      efficientRate = input.getString( "efficientRate" );
      elecCostExpr = input.getString( "elecCostExpr" );
      etcCost = input.getString( "etcCost" );
      etcReducePrice = input.getString( "etcReducePrice" );
      facReduceCost = input.getString( "facReduceCost" );
      facReducePrice = input.getString( "facReducePrice" );
      facilities = input.getString( "facilities" );
      inDirectCost = input.getString( "inDirectCost" );
      inDirectCost2Expr = input.getString( "inDirectCost2Expr" );
      inDirectCostExpr = input.getString( "inDirectCostExpr" );
      inDirectLaborCost = input.getString( "inDirectLaborCost" );
      indirectCostRnd = input.getString( "indirectCostRnd" );
      indirectLabourRate = input.getString( "indirectLabourRate" );
      indirectLabourRndCost = input.getString( "indirectLabourRndCost" );
      indirectRndRate = input.getString( "indirectRndRate" );
      laborCost = input.getString( "laborCost" );
      laborCostExpr = input.getString( "laborCostExpr" );
      lastCompany = input.getString( "lastCompany" );
      lastMonetaryUnit = input.getString( "lastMonetaryUnit" );
      lastUnit = input.getString( "lastUnit" );
      lastUnitCost = input.getString( "lastUnitCost" );
      machineDpcCostExpr = input.getString( "machineDpcCostExpr" );
      manageMtrLogisExpr = input.getString( "manageMtrLogisExpr" );
      manageMtrdutieExpr = input.getString( "manageMtrdutieExpr" );
      materialCompany = input.getString( "materialCompany" );
      materialCost = input.getString( "materialCost" );
      materialCostExpr = input.getString( "materialCostExpr" );
      metalLmeStd = input.getString( "metalLmeStd" );
      metalPartName = input.getString( "metalPartName" );
      metalPartNo = input.getString( "metalPartNo" );
      mftFactory1 = input.getString( "mftFactory1" );
      mftFactory2 = input.getString( "mftFactory2" );
      moldMaintenance = input.getString( "moldMaintenance" );
      moldReducePrice = input.getString( "moldReducePrice" );
      mtrLtCostExpr = input.getString( "mtrLtCostExpr" );
      mtrLtRateExpr = input.getString( "mtrLtRateExpr" );
      mtrMetalMonetaryUnit = input.getString( "mtrMetalMonetaryUnit" );
      mtrMetalPrice = input.getString( "mtrMetalPrice" );
      mtrMetalUnit = input.getString( "mtrMetalUnit" );
      mtrSujiMonetaryUnit = input.getString( "mtrSujiMonetaryUnit" );
      mtrSujiPrice = input.getString( "mtrSujiPrice" );
      mtrSujiUnit = input.getString( "mtrSujiUnit" );
      orgProductCostTotal = input.getString( "orgProductCostTotal" );
      outUnitCost = input.getString( "outUnitCost" );
      outUnitCostVal = input.getString( "outUnitCostVal" );
      outputExpr = input.getString( "outputExpr" );
      pMonetaryUnit = input.getString( "pMonetaryUnit" );
      pMonetaryUnitCurrency = input.getString( "pMonetaryUnitCurrency" );
      pUnit = input.getString( "pUnit" );
      pUnitCost = input.getString( "pUnitCost" );
      packUnitPriceOption = input.getString( "packUnitPriceOption" );
      packUnitPriceSum = input.getString( "packUnitPriceSum" );
      parentHistoryReference = (wt.fc.ObjectReference) input.readObject( "parentHistoryReference", parentHistoryReference, wt.fc.ObjectReference.class, true );
      parentPartNo = input.getString( "parentPartNo" );
      parentPartUs = input.getString( "parentPartUs" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      partListReference = (wt.fc.ObjectReference) input.readObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      partType = input.getString( "partType" );
      payCostLtExpr = input.getString( "payCostLtExpr" );
      payRateLtExpr = input.getString( "payRateLtExpr" );
      personQLH = input.getString( "personQLH" );
      pjtNo = input.getString( "pjtNo" );
      pjtType = input.getString( "pjtType" );
      processingCost = input.getString( "processingCost" );
      productCostTotal = input.getString( "productCostTotal" );
      productLossExpr = input.getString( "productLossExpr" );
      productLossRate = input.getString( "productLossRate" );
      productWeight = input.getString( "productWeight" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      qaDiffInput = input.getString( "qaDiffInput" );
      qaDiffUnit = input.getString( "qaDiffUnit" );
      rawMaterialCostExpr = input.getString( "rawMaterialCostExpr" );
      realPartNo = input.getString( "realPartNo" );
      rndExpr = input.getString( "rndExpr" );
      rootPartNo = input.getString( "rootPartNo" );
      salesManageCost = input.getString( "salesManageCost" );
      scrapSalesCost = input.getString( "scrapSalesCost" );
      scrapWeight = input.getString( "scrapWeight" );
      sopYear = input.getString( "sopYear" );
      sortLocation = input.getIntObject( "sortLocation" );
      sujiPartName = input.getString( "sujiPartName" );
      sujiPartNo = input.getString( "sujiPartNo" );
      tabaryuExpr = input.getString( "tabaryuExpr" );
      targetPartName = input.getString( "targetPartName" );
      targetPartNo = input.getString( "targetPartNo" );
      taskReference = (wt.fc.ObjectReference) input.readObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      totalWeight = input.getString( "totalWeight" );
      transferDate = input.getTimestamp( "transferDate" );
      transferFlag = input.getString( "transferFlag" );
      transferMsg = input.getString( "transferMsg" );
      unitManage = input.getString( "unitManage" );
      us = input.getString( "us" );
      version = input.getString( "version" );
      waers = input.getString( "waers" );
      worker = input.getString( "worker" );
   }

   boolean readVersion_8746303150760254721L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      apUnitCostOption = (java.lang.String) input.readObject();
      apUnitCostVal = (java.lang.String) input.readObject();
      assyLossExpr = (java.lang.String) input.readObject();
      bomLevel = (java.lang.String) input.readObject();
      coManageExpr = (java.lang.String) input.readObject();
      company = (java.lang.String) input.readObject();
      convertWorker = (java.lang.String) input.readObject();
      corporateMarginCostExpr = (java.lang.String) input.readObject();
      costPartReference = (wt.fc.ObjectReference) input.readObject();
      ctSPMAssemble = (java.lang.String) input.readObject();
      ctSPMMold = (java.lang.String) input.readObject();
      cv = (java.lang.String) input.readObject();
      cvAssemble = (java.lang.String) input.readObject();
      cvMold = (java.lang.String) input.readObject();
      defectCostExpr = (java.lang.String) input.readObject();
      dfCt = (java.lang.String) input.readObject();
      dfDirectExpenses = (java.lang.String) input.readObject();
      dfDirectLaborCost = (java.lang.String) input.readObject();
      dfInDirectExpenses = (java.lang.String) input.readObject();
      dfInDirectLaborCost = (java.lang.String) input.readObject();
      dfMachineDep = (java.lang.String) input.readObject();
      dfMoldDep = (java.lang.String) input.readObject();
      directCost = (java.lang.String) input.readObject();
      drStep = (java.lang.String) input.readObject();
      efficientRate = (java.lang.String) input.readObject();
      elecCostExpr = (java.lang.String) input.readObject();
      etcCost = (java.lang.String) input.readObject();
      etcReducePrice = (java.lang.String) input.readObject();
      facReduceCost = (java.lang.String) input.readObject();
      facReducePrice = (java.lang.String) input.readObject();
      facilities = (java.lang.String) input.readObject();
      inDirectCost = (java.lang.String) input.readObject();
      inDirectCost2Expr = (java.lang.String) input.readObject();
      inDirectCostExpr = (java.lang.String) input.readObject();
      inDirectLaborCost = (java.lang.String) input.readObject();
      indirectCostRnd = (java.lang.String) input.readObject();
      indirectLabourRate = (java.lang.String) input.readObject();
      indirectLabourRndCost = (java.lang.String) input.readObject();
      indirectRndRate = (java.lang.String) input.readObject();
      laborCost = (java.lang.String) input.readObject();
      laborCostExpr = (java.lang.String) input.readObject();
      lastCompany = (java.lang.String) input.readObject();
      lastMonetaryUnit = (java.lang.String) input.readObject();
      lastUnit = (java.lang.String) input.readObject();
      lastUnitCost = (java.lang.String) input.readObject();
      machineDpcCostExpr = (java.lang.String) input.readObject();
      manageMtrLogisExpr = (java.lang.String) input.readObject();
      manageMtrdutieExpr = (java.lang.String) input.readObject();
      materialCompany = (java.lang.String) input.readObject();
      materialCost = (java.lang.String) input.readObject();
      materialCostExpr = (java.lang.String) input.readObject();
      metalLmeStd = (java.lang.String) input.readObject();
      metalPartName = (java.lang.String) input.readObject();
      metalPartNo = (java.lang.String) input.readObject();
      mftFactory1 = (java.lang.String) input.readObject();
      mftFactory2 = (java.lang.String) input.readObject();
      moldMaintenance = (java.lang.String) input.readObject();
      moldReducePrice = (java.lang.String) input.readObject();
      mtrLtCostExpr = (java.lang.String) input.readObject();
      mtrLtRateExpr = (java.lang.String) input.readObject();
      mtrMetalMonetaryUnit = (java.lang.String) input.readObject();
      mtrMetalPrice = (java.lang.String) input.readObject();
      mtrMetalUnit = (java.lang.String) input.readObject();
      mtrSujiMonetaryUnit = (java.lang.String) input.readObject();
      mtrSujiPrice = (java.lang.String) input.readObject();
      mtrSujiUnit = (java.lang.String) input.readObject();
      orgProductCostTotal = (java.lang.String) input.readObject();
      outUnitCost = (java.lang.String) input.readObject();
      outUnitCostVal = (java.lang.String) input.readObject();
      outputExpr = (java.lang.String) input.readObject();
      pMonetaryUnit = (java.lang.String) input.readObject();
      pMonetaryUnitCurrency = (java.lang.String) input.readObject();
      pUnit = (java.lang.String) input.readObject();
      pUnitCost = (java.lang.String) input.readObject();
      packUnitPriceOption = (java.lang.String) input.readObject();
      packUnitPriceSum = (java.lang.String) input.readObject();
      parentHistoryReference = (wt.fc.ObjectReference) input.readObject();
      parentPartNo = (java.lang.String) input.readObject();
      parentPartUs = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      partListReference = (wt.fc.ObjectReference) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      partType = (java.lang.String) input.readObject();
      payCostLtExpr = (java.lang.String) input.readObject();
      payRateLtExpr = (java.lang.String) input.readObject();
      personQLH = (java.lang.String) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      pjtType = (java.lang.String) input.readObject();
      processingCost = (java.lang.String) input.readObject();
      productCostTotal = (java.lang.String) input.readObject();
      productLossExpr = (java.lang.String) input.readObject();
      productLossRate = (java.lang.String) input.readObject();
      productWeight = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      qaDiffInput = (java.lang.String) input.readObject();
      qaDiffUnit = (java.lang.String) input.readObject();
      rawMaterialCostExpr = (java.lang.String) input.readObject();
      realPartNo = (java.lang.String) input.readObject();
      rndExpr = (java.lang.String) input.readObject();
      rootPartNo = (java.lang.String) input.readObject();
      salesManageCost = (java.lang.String) input.readObject();
      scrapSalesCost = (java.lang.String) input.readObject();
      scrapWeight = (java.lang.String) input.readObject();
      sopYear = (java.lang.String) input.readObject();
      sortLocation = (java.lang.Integer) input.readObject();
      sujiPartName = (java.lang.String) input.readObject();
      sujiPartNo = (java.lang.String) input.readObject();
      tabaryuExpr = (java.lang.String) input.readObject();
      targetPartName = (java.lang.String) input.readObject();
      targetPartNo = (java.lang.String) input.readObject();
      taskReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      totalWeight = (java.lang.String) input.readObject();
      transferDate = (java.sql.Timestamp) input.readObject();
      transferFlag = (java.lang.String) input.readObject();
      transferMsg = (java.lang.String) input.readObject();
      unitManage = (java.lang.String) input.readObject();
      us = (java.lang.String) input.readObject();
      version = (java.lang.String) input.readObject();
      waers = (java.lang.String) input.readObject();
      worker = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostInterfaceChildHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8746303150760254721L( input, readSerialVersionUID, superDone );
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
