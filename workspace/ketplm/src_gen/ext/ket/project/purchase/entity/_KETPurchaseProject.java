package ext.ket.project.purchase.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPurchaseProject extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.purchase.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPurchaseProject.class.getName();

   /**
    * 품번
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 품번
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 품번
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
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
    * 품명
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * 품명
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * 품명
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
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
    * 협력사(제조사)
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String OUT_SOURCING = "outSourcing";
   static int OUT_SOURCING_UPPER_LIMIT = -1;
   java.lang.String outSourcing;
   /**
    * 협력사(제조사)
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getOutSourcing() {
      return outSourcing;
   }
   /**
    * 협력사(제조사)
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setOutSourcing(java.lang.String outSourcing) throws wt.util.WTPropertyVetoException {
      outSourcingValidate(outSourcing);
      this.outSourcing = outSourcing;
   }
   void outSourcingValidate(java.lang.String outSourcing) throws wt.util.WTPropertyVetoException {
      if (OUT_SOURCING_UPPER_LIMIT < 1) {
         try { OUT_SOURCING_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outSourcing").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_SOURCING_UPPER_LIMIT = 200; }
      }
      if (outSourcing != null && !wt.fc.PersistenceHelper.checkStoredLength(outSourcing.toString(), OUT_SOURCING_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outSourcing"), java.lang.String.valueOf(java.lang.Math.min(OUT_SOURCING_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outSourcing", this.outSourcing, outSourcing));
   }

   /**
    * 기존,신규
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String OUT_SOURCING_GUBUN = "outSourcingGubun";
   static int OUT_SOURCING_GUBUN_UPPER_LIMIT = -1;
   java.lang.String outSourcingGubun;
   /**
    * 기존,신규
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getOutSourcingGubun() {
      return outSourcingGubun;
   }
   /**
    * 기존,신규
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setOutSourcingGubun(java.lang.String outSourcingGubun) throws wt.util.WTPropertyVetoException {
      outSourcingGubunValidate(outSourcingGubun);
      this.outSourcingGubun = outSourcingGubun;
   }
   void outSourcingGubunValidate(java.lang.String outSourcingGubun) throws wt.util.WTPropertyVetoException {
      if (OUT_SOURCING_GUBUN_UPPER_LIMIT < 1) {
         try { OUT_SOURCING_GUBUN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outSourcingGubun").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_SOURCING_GUBUN_UPPER_LIMIT = 200; }
      }
      if (outSourcingGubun != null && !wt.fc.PersistenceHelper.checkStoredLength(outSourcingGubun.toString(), OUT_SOURCING_GUBUN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outSourcingGubun"), java.lang.String.valueOf(java.lang.Math.min(OUT_SOURCING_GUBUN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outSourcingGubun", this.outSourcingGubun, outSourcingGubun));
   }

   /**
    * 담당자이름
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MANAGER_NAME = "managerName";
   static int MANAGER_NAME_UPPER_LIMIT = -1;
   java.lang.String managerName;
   /**
    * 담당자이름
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getManagerName() {
      return managerName;
   }
   /**
    * 담당자이름
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setManagerName(java.lang.String managerName) throws wt.util.WTPropertyVetoException {
      managerNameValidate(managerName);
      this.managerName = managerName;
   }
   void managerNameValidate(java.lang.String managerName) throws wt.util.WTPropertyVetoException {
      if (MANAGER_NAME_UPPER_LIMIT < 1) {
         try { MANAGER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("managerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGER_NAME_UPPER_LIMIT = 200; }
      }
      if (managerName != null && !wt.fc.PersistenceHelper.checkStoredLength(managerName.toString(), MANAGER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerName"), java.lang.String.valueOf(java.lang.Math.min(MANAGER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "managerName", this.managerName, managerName));
   }

   /**
    * 담당자아이디
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MANAGER_CODE = "managerCode";
   static int MANAGER_CODE_UPPER_LIMIT = -1;
   java.lang.String managerCode;
   /**
    * 담당자아이디
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getManagerCode() {
      return managerCode;
   }
   /**
    * 담당자아이디
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setManagerCode(java.lang.String managerCode) throws wt.util.WTPropertyVetoException {
      managerCodeValidate(managerCode);
      this.managerCode = managerCode;
   }
   void managerCodeValidate(java.lang.String managerCode) throws wt.util.WTPropertyVetoException {
      if (MANAGER_CODE_UPPER_LIMIT < 1) {
         try { MANAGER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("managerCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGER_CODE_UPPER_LIMIT = 200; }
      }
      if (managerCode != null && !wt.fc.PersistenceHelper.checkStoredLength(managerCode.toString(), MANAGER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerCode"), java.lang.String.valueOf(java.lang.Math.min(MANAGER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "managerCode", this.managerCode, managerCode));
   }

   /**
    * 담당자부서
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MANAGER_DEPT_NAME = "managerDeptName";
   static int MANAGER_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String managerDeptName;
   /**
    * 담당자부서
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getManagerDeptName() {
      return managerDeptName;
   }
   /**
    * 담당자부서
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setManagerDeptName(java.lang.String managerDeptName) throws wt.util.WTPropertyVetoException {
      managerDeptNameValidate(managerDeptName);
      this.managerDeptName = managerDeptName;
   }
   void managerDeptNameValidate(java.lang.String managerDeptName) throws wt.util.WTPropertyVetoException {
      if (MANAGER_DEPT_NAME_UPPER_LIMIT < 1) {
         try { MANAGER_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("managerDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGER_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (managerDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(managerDeptName.toString(), MANAGER_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerDeptName"), java.lang.String.valueOf(java.lang.Math.min(MANAGER_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "managerDeptName", this.managerDeptName, managerDeptName));
   }

   /**
    * 담당자부서코드
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MANAGER_DEPT_CODE = "managerDeptCode";
   static int MANAGER_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String managerDeptCode;
   /**
    * 담당자부서코드
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getManagerDeptCode() {
      return managerDeptCode;
   }
   /**
    * 담당자부서코드
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setManagerDeptCode(java.lang.String managerDeptCode) throws wt.util.WTPropertyVetoException {
      managerDeptCodeValidate(managerDeptCode);
      this.managerDeptCode = managerDeptCode;
   }
   void managerDeptCodeValidate(java.lang.String managerDeptCode) throws wt.util.WTPropertyVetoException {
      if (MANAGER_DEPT_CODE_UPPER_LIMIT < 1) {
         try { MANAGER_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("managerDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGER_DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (managerDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(managerDeptCode.toString(), MANAGER_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerDeptCode"), java.lang.String.valueOf(java.lang.Math.min(MANAGER_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "managerDeptCode", this.managerDeptCode, managerDeptCode));
   }

   /**
    * 프로젝트번호
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * 프로젝트번호
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * 프로젝트번호
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
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
    * 개발발주계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String DEV_ORDER_START_DATE = "devOrderStartDate";
   java.sql.Timestamp devOrderStartDate;
   /**
    * 개발발주계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getDevOrderStartDate() {
      return devOrderStartDate;
   }
   /**
    * 개발발주계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setDevOrderStartDate(java.sql.Timestamp devOrderStartDate) throws wt.util.WTPropertyVetoException {
      devOrderStartDateValidate(devOrderStartDate);
      this.devOrderStartDate = devOrderStartDate;
   }
   void devOrderStartDateValidate(java.sql.Timestamp devOrderStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 개발발주종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String DEV_ORDER_END_DATE = "devOrderEndDate";
   java.sql.Timestamp devOrderEndDate;
   /**
    * 개발발주종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getDevOrderEndDate() {
      return devOrderEndDate;
   }
   /**
    * 개발발주종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setDevOrderEndDate(java.sql.Timestamp devOrderEndDate) throws wt.util.WTPropertyVetoException {
      devOrderEndDateValidate(devOrderEndDate);
      this.devOrderEndDate = devOrderEndDate;
   }
   void devOrderEndDateValidate(java.sql.Timestamp devOrderEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 수입검사기준서계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String IMSP_START_DATE = "imspStartDate";
   java.sql.Timestamp imspStartDate;
   /**
    * 수입검사기준서계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getImspStartDate() {
      return imspStartDate;
   }
   /**
    * 수입검사기준서계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setImspStartDate(java.sql.Timestamp imspStartDate) throws wt.util.WTPropertyVetoException {
      imspStartDateValidate(imspStartDate);
      this.imspStartDate = imspStartDate;
   }
   void imspStartDateValidate(java.sql.Timestamp imspStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 수입검사기준서종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String IMSP_END_DATE = "imspEndDate";
   java.sql.Timestamp imspEndDate;
   /**
    * 수입검사기준서종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getImspEndDate() {
      return imspEndDate;
   }
   /**
    * 수입검사기준서종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setImspEndDate(java.sql.Timestamp imspEndDate) throws wt.util.WTPropertyVetoException {
      imspEndDateValidate(imspEndDate);
      this.imspEndDate = imspEndDate;
   }
   void imspEndDateValidate(java.sql.Timestamp imspEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 검사협정서접수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String EXA_AGREE_START_DATE = "exaAgreeStartDate";
   java.sql.Timestamp exaAgreeStartDate;
   /**
    * 검사협정서접수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getExaAgreeStartDate() {
      return exaAgreeStartDate;
   }
   /**
    * 검사협정서접수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setExaAgreeStartDate(java.sql.Timestamp exaAgreeStartDate) throws wt.util.WTPropertyVetoException {
      exaAgreeStartDateValidate(exaAgreeStartDate);
      this.exaAgreeStartDate = exaAgreeStartDate;
   }
   void exaAgreeStartDateValidate(java.sql.Timestamp exaAgreeStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 검사협정서접수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String EXA_AGREE_END_DATE = "exaAgreeEndDate";
   java.sql.Timestamp exaAgreeEndDate;
   /**
    * 검사협정서접수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getExaAgreeEndDate() {
      return exaAgreeEndDate;
   }
   /**
    * 검사협정서접수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setExaAgreeEndDate(java.sql.Timestamp exaAgreeEndDate) throws wt.util.WTPropertyVetoException {
      exaAgreeEndDateValidate(exaAgreeEndDate);
      this.exaAgreeEndDate = exaAgreeEndDate;
   }
   void exaAgreeEndDateValidate(java.sql.Timestamp exaAgreeEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 금형제작계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MOLDMK_START_DATE = "moldmkStartDate";
   java.sql.Timestamp moldmkStartDate;
   /**
    * 금형제작계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getMoldmkStartDate() {
      return moldmkStartDate;
   }
   /**
    * 금형제작계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setMoldmkStartDate(java.sql.Timestamp moldmkStartDate) throws wt.util.WTPropertyVetoException {
      moldmkStartDateValidate(moldmkStartDate);
      this.moldmkStartDate = moldmkStartDate;
   }
   void moldmkStartDateValidate(java.sql.Timestamp moldmkStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 금형제작종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MOLDMK_END_DATE = "moldmkEndDate";
   java.sql.Timestamp moldmkEndDate;
   /**
    * 금형제작종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getMoldmkEndDate() {
      return moldmkEndDate;
   }
   /**
    * 금형제작종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setMoldmkEndDate(java.sql.Timestamp moldmkEndDate) throws wt.util.WTPropertyVetoException {
      moldmkEndDateValidate(moldmkEndDate);
      this.moldmkEndDate = moldmkEndDate;
   }
   void moldmkEndDateValidate(java.sql.Timestamp moldmkEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Try계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MOLD_TRY_START_DATE = "moldTryStartDate";
   java.sql.Timestamp moldTryStartDate;
   /**
    * Try계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getMoldTryStartDate() {
      return moldTryStartDate;
   }
   /**
    * Try계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setMoldTryStartDate(java.sql.Timestamp moldTryStartDate) throws wt.util.WTPropertyVetoException {
      moldTryStartDateValidate(moldTryStartDate);
      this.moldTryStartDate = moldTryStartDate;
   }
   void moldTryStartDateValidate(java.sql.Timestamp moldTryStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Try종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MOLD_TRY_END_DATE = "moldTryEndDate";
   java.sql.Timestamp moldTryEndDate;
   /**
    * Try종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getMoldTryEndDate() {
      return moldTryEndDate;
   }
   /**
    * Try종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setMoldTryEndDate(java.sql.Timestamp moldTryEndDate) throws wt.util.WTPropertyVetoException {
      moldTryEndDateValidate(moldTryEndDate);
      this.moldTryEndDate = moldTryEndDate;
   }
   void moldTryEndDateValidate(java.sql.Timestamp moldTryEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 중요치수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String IMSIZE_START_DATE = "imsizeStartDate";
   java.sql.Timestamp imsizeStartDate;
   /**
    * 중요치수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getImsizeStartDate() {
      return imsizeStartDate;
   }
   /**
    * 중요치수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setImsizeStartDate(java.sql.Timestamp imsizeStartDate) throws wt.util.WTPropertyVetoException {
      imsizeStartDateValidate(imsizeStartDate);
      this.imsizeStartDate = imsizeStartDate;
   }
   void imsizeStartDateValidate(java.sql.Timestamp imsizeStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 중요치수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String IMSIZE_END_DATE = "imsizeEndDate";
   java.sql.Timestamp imsizeEndDate;
   /**
    * 중요치수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getImsizeEndDate() {
      return imsizeEndDate;
   }
   /**
    * 중요치수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setImsizeEndDate(java.sql.Timestamp imsizeEndDate) throws wt.util.WTPropertyVetoException {
      imsizeEndDateValidate(imsizeEndDate);
      this.imsizeEndDate = imsizeEndDate;
   }
   void imsizeEndDateValidate(java.sql.Timestamp imsizeEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String TRUST_START_DATE = "trustStartDate";
   java.sql.Timestamp trustStartDate;
   /**
    * 신뢰성계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getTrustStartDate() {
      return trustStartDate;
   }
   /**
    * 신뢰성계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setTrustStartDate(java.sql.Timestamp trustStartDate) throws wt.util.WTPropertyVetoException {
      trustStartDateValidate(trustStartDate);
      this.trustStartDate = trustStartDate;
   }
   void trustStartDateValidate(java.sql.Timestamp trustStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String TRUST_END_DATE = "trustEndDate";
   java.sql.Timestamp trustEndDate;
   /**
    * 신뢰성종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getTrustEndDate() {
      return trustEndDate;
   }
   /**
    * 신뢰성종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setTrustEndDate(java.sql.Timestamp trustEndDate) throws wt.util.WTPropertyVetoException {
      trustEndDateValidate(trustEndDate);
      this.trustEndDate = trustEndDate;
   }
   void trustEndDateValidate(java.sql.Timestamp trustEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P1입고계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String P1_START_DATE = "p1StartDate";
   java.sql.Timestamp p1StartDate;
   /**
    * P1입고계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getP1StartDate() {
      return p1StartDate;
   }
   /**
    * P1입고계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setP1StartDate(java.sql.Timestamp p1StartDate) throws wt.util.WTPropertyVetoException {
      p1StartDateValidate(p1StartDate);
      this.p1StartDate = p1StartDate;
   }
   void p1StartDateValidate(java.sql.Timestamp p1StartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P1입고종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String P1_END_DATE = "p1EndDate";
   java.sql.Timestamp p1EndDate;
   /**
    * P1입고종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getP1EndDate() {
      return p1EndDate;
   }
   /**
    * P1입고종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setP1EndDate(java.sql.Timestamp p1EndDate) throws wt.util.WTPropertyVetoException {
      p1EndDateValidate(p1EndDate);
      this.p1EndDate = p1EndDate;
   }
   void p1EndDateValidate(java.sql.Timestamp p1EndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 전치수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String ALL_SIZE_START_DATE = "allSizeStartDate";
   java.sql.Timestamp allSizeStartDate;
   /**
    * 전치수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getAllSizeStartDate() {
      return allSizeStartDate;
   }
   /**
    * 전치수계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setAllSizeStartDate(java.sql.Timestamp allSizeStartDate) throws wt.util.WTPropertyVetoException {
      allSizeStartDateValidate(allSizeStartDate);
      this.allSizeStartDate = allSizeStartDate;
   }
   void allSizeStartDateValidate(java.sql.Timestamp allSizeStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 전치수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String ALL_SIZE_END_DATE = "allSizeEndDate";
   java.sql.Timestamp allSizeEndDate;
   /**
    * 전치수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getAllSizeEndDate() {
      return allSizeEndDate;
   }
   /**
    * 전치수종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setAllSizeEndDate(java.sql.Timestamp allSizeEndDate) throws wt.util.WTPropertyVetoException {
      allSizeEndDateValidate(allSizeEndDate);
      this.allSizeEndDate = allSizeEndDate;
   }
   void allSizeEndDateValidate(java.sql.Timestamp allSizeEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PPAP서류제출계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PPAP_START_DATE1 = "ppapStartDate1";
   java.sql.Timestamp ppapStartDate1;
   /**
    * PPAP서류제출계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getPpapStartDate1() {
      return ppapStartDate1;
   }
   /**
    * PPAP서류제출계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPpapStartDate1(java.sql.Timestamp ppapStartDate1) throws wt.util.WTPropertyVetoException {
      ppapStartDate1Validate(ppapStartDate1);
      this.ppapStartDate1 = ppapStartDate1;
   }
   void ppapStartDate1Validate(java.sql.Timestamp ppapStartDate1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PPAP서류제출종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PPAP_END_DATE1 = "ppapEndDate1";
   java.sql.Timestamp ppapEndDate1;
   /**
    * PPAP서류제출종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getPpapEndDate1() {
      return ppapEndDate1;
   }
   /**
    * PPAP서류제출종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPpapEndDate1(java.sql.Timestamp ppapEndDate1) throws wt.util.WTPropertyVetoException {
      ppapEndDate1Validate(ppapEndDate1);
      this.ppapEndDate1 = ppapEndDate1;
   }
   void ppapEndDate1Validate(java.sql.Timestamp ppapEndDate1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PPAP공정감사계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PPAP_START_DATE2 = "ppapStartDate2";
   java.sql.Timestamp ppapStartDate2;
   /**
    * PPAP공정감사계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getPpapStartDate2() {
      return ppapStartDate2;
   }
   /**
    * PPAP공정감사계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPpapStartDate2(java.sql.Timestamp ppapStartDate2) throws wt.util.WTPropertyVetoException {
      ppapStartDate2Validate(ppapStartDate2);
      this.ppapStartDate2 = ppapStartDate2;
   }
   void ppapStartDate2Validate(java.sql.Timestamp ppapStartDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PPAP공정감사종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PPAP_END_DATE2 = "ppapEndDate2";
   java.sql.Timestamp ppapEndDate2;
   /**
    * PPAP공정감사종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getPpapEndDate2() {
      return ppapEndDate2;
   }
   /**
    * PPAP공정감사종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPpapEndDate2(java.sql.Timestamp ppapEndDate2) throws wt.util.WTPropertyVetoException {
      ppapEndDate2Validate(ppapEndDate2);
      this.ppapEndDate2 = ppapEndDate2;
   }
   void ppapEndDate2Validate(java.sql.Timestamp ppapEndDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PPAP승인계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PPAP_START_DATE3 = "ppapStartDate3";
   java.sql.Timestamp ppapStartDate3;
   /**
    * PPAP승인계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getPpapStartDate3() {
      return ppapStartDate3;
   }
   /**
    * PPAP승인계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPpapStartDate3(java.sql.Timestamp ppapStartDate3) throws wt.util.WTPropertyVetoException {
      ppapStartDate3Validate(ppapStartDate3);
      this.ppapStartDate3 = ppapStartDate3;
   }
   void ppapStartDate3Validate(java.sql.Timestamp ppapStartDate3) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PPAP승인종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PPAP_END_DATE3 = "ppapEndDate3";
   java.sql.Timestamp ppapEndDate3;
   /**
    * PPAP승인종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getPpapEndDate3() {
      return ppapEndDate3;
   }
   /**
    * PPAP승인종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPpapEndDate3(java.sql.Timestamp ppapEndDate3) throws wt.util.WTPropertyVetoException {
      ppapEndDate3Validate(ppapEndDate3);
      this.ppapEndDate3 = ppapEndDate3;
   }
   void ppapEndDate3Validate(java.sql.Timestamp ppapEndDate3) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P2계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String P2_START_DATE = "p2StartDate";
   java.sql.Timestamp p2StartDate;
   /**
    * P2계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getP2StartDate() {
      return p2StartDate;
   }
   /**
    * P2계획일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setP2StartDate(java.sql.Timestamp p2StartDate) throws wt.util.WTPropertyVetoException {
      p2StartDateValidate(p2StartDate);
      this.p2StartDate = p2StartDate;
   }
   void p2StartDateValidate(java.sql.Timestamp p2StartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P2종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String P2_END_DATE = "p2EndDate";
   java.sql.Timestamp p2EndDate;
   /**
    * P2종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getP2EndDate() {
      return p2EndDate;
   }
   /**
    * P2종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setP2EndDate(java.sql.Timestamp p2EndDate) throws wt.util.WTPropertyVetoException {
      p2EndDateValidate(p2EndDate);
      this.p2EndDate = p2EndDate;
   }
   void p2EndDateValidate(java.sql.Timestamp p2EndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트실제시작일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PROJECT_EXEC_START_DATE = "projectExecStartDate";
   java.sql.Timestamp projectExecStartDate;
   /**
    * 프로젝트실제시작일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getProjectExecStartDate() {
      return projectExecStartDate;
   }
   /**
    * 프로젝트실제시작일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setProjectExecStartDate(java.sql.Timestamp projectExecStartDate) throws wt.util.WTPropertyVetoException {
      projectExecStartDateValidate(projectExecStartDate);
      this.projectExecStartDate = projectExecStartDate;
   }
   void projectExecStartDateValidate(java.sql.Timestamp projectExecStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트실제종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PROJECT_EXEC_END_DATE = "projectExecEndDate";
   java.sql.Timestamp projectExecEndDate;
   /**
    * 프로젝트실제종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getProjectExecEndDate() {
      return projectExecEndDate;
   }
   /**
    * 프로젝트실제종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setProjectExecEndDate(java.sql.Timestamp projectExecEndDate) throws wt.util.WTPropertyVetoException {
      projectExecEndDateValidate(projectExecEndDate);
      this.projectExecEndDate = projectExecEndDate;
   }
   void projectExecEndDateValidate(java.sql.Timestamp projectExecEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트계획시작일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PROJECT_PLAN_START_DATE = "projectPlanStartDate";
   java.sql.Timestamp projectPlanStartDate;
   /**
    * 프로젝트계획시작일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getProjectPlanStartDate() {
      return projectPlanStartDate;
   }
   /**
    * 프로젝트계획시작일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setProjectPlanStartDate(java.sql.Timestamp projectPlanStartDate) throws wt.util.WTPropertyVetoException {
      projectPlanStartDateValidate(projectPlanStartDate);
      this.projectPlanStartDate = projectPlanStartDate;
   }
   void projectPlanStartDateValidate(java.sql.Timestamp projectPlanStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트계획종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PROJECT_PLAN_END_DATE = "projectPlanEndDate";
   java.sql.Timestamp projectPlanEndDate;
   /**
    * 프로젝트계획종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.sql.Timestamp getProjectPlanEndDate() {
      return projectPlanEndDate;
   }
   /**
    * 프로젝트계획종료일
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setProjectPlanEndDate(java.sql.Timestamp projectPlanEndDate) throws wt.util.WTPropertyVetoException {
      projectPlanEndDateValidate(projectPlanEndDate);
      this.projectPlanEndDate = projectPlanEndDate;
   }
   void projectPlanEndDateValidate(java.sql.Timestamp projectPlanEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 상태
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PJT_STATE = "pjtState";
   static int PJT_STATE_UPPER_LIMIT = -1;
   java.lang.String pjtState;
   /**
    * 상태
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getPjtState() {
      return pjtState;
   }
   /**
    * 상태
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPjtState(java.lang.String pjtState) throws wt.util.WTPropertyVetoException {
      pjtStateValidate(pjtState);
      this.pjtState = pjtState;
   }
   void pjtStateValidate(java.lang.String pjtState) throws wt.util.WTPropertyVetoException {
      if (PJT_STATE_UPPER_LIMIT < 1) {
         try { PJT_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_STATE_UPPER_LIMIT = 200; }
      }
      if (pjtState != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtState.toString(), PJT_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtState"), java.lang.String.valueOf(java.lang.Math.min(PJT_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtState", this.pjtState, pjtState));
   }

   /**
    * 비고
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String BIGO = "bigo";
   static int BIGO_UPPER_LIMIT = -1;
   java.lang.String bigo;
   /**
    * 비고
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public java.lang.String getBigo() {
      return bigo;
   }
   /**
    * 비고
    *
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setBigo(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      bigoValidate(bigo);
      this.bigo = bigo;
   }
   void bigoValidate(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      if (BIGO_UPPER_LIMIT < 1) {
         try { BIGO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bigo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BIGO_UPPER_LIMIT = 4000; }
      }
      if (bigo != null && !wt.fc.PersistenceHelper.checkStoredLength(bigo.toString(), BIGO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bigo"), java.lang.String.valueOf(java.lang.Math.min(BIGO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bigo", this.bigo, bigo));
   }

   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String DEPARTMENT = "department";
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String DEPARTMENT_REFERENCE = "departmentReference";
   wt.fc.ObjectReference departmentReference;
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public e3ps.groupware.company.Department getDepartment() {
      return (departmentReference != null) ? (e3ps.groupware.company.Department) departmentReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.fc.ObjectReference getDepartmentReference() {
      return departmentReference;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setDepartment(e3ps.groupware.company.Department the_department) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDepartmentReference(the_department == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_department));
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setDepartmentReference(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      departmentReferenceValidate(the_departmentReference);
      departmentReference = (wt.fc.ObjectReference) the_departmentReference;
   }
   void departmentReferenceValidate(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      if (the_departmentReference == null || the_departmentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "departmentReference") },
               new java.beans.PropertyChangeEvent(this, "departmentReference", this.departmentReference, departmentReference));
      if (the_departmentReference != null && the_departmentReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_departmentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "departmentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "departmentReference", this.departmentReference, departmentReference));
   }

   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PM = "pm";
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PM_REFERENCE = "pmReference";
   wt.fc.ObjectReference pmReference;
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.org.WTUser getPm() {
      return (pmReference != null) ? (wt.org.WTUser) pmReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.fc.ObjectReference getPmReference() {
      return pmReference;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPm(wt.org.WTUser the_pm) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPmReference(the_pm == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_pm));
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setPmReference(wt.fc.ObjectReference the_pmReference) throws wt.util.WTPropertyVetoException {
      pmReferenceValidate(the_pmReference);
      pmReference = (wt.fc.ObjectReference) the_pmReference;
   }
   void pmReferenceValidate(wt.fc.ObjectReference the_pmReference) throws wt.util.WTPropertyVetoException {
      if (the_pmReference == null || the_pmReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pmReference") },
               new java.beans.PropertyChangeEvent(this, "pmReference", this.pmReference, pmReference));
      if (the_pmReference != null && the_pmReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_pmReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pmReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "pmReference", this.pmReference, pmReference));
   }

   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String CREATE_USER = "createUser";
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String CREATE_USER_REFERENCE = "createUserReference";
   wt.fc.ObjectReference createUserReference;
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.org.WTUser getCreateUser() {
      return (createUserReference != null) ? (wt.org.WTUser) createUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.fc.ObjectReference getCreateUserReference() {
      return createUserReference;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setCreateUser(wt.org.WTUser the_createUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCreateUserReference(the_createUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_createUser));
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setCreateUserReference(wt.fc.ObjectReference the_createUserReference) throws wt.util.WTPropertyVetoException {
      createUserReferenceValidate(the_createUserReference);
      createUserReference = (wt.fc.ObjectReference) the_createUserReference;
   }
   void createUserReferenceValidate(wt.fc.ObjectReference the_createUserReference) throws wt.util.WTPropertyVetoException {
      if (the_createUserReference == null || the_createUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "createUserReference") },
               new java.beans.PropertyChangeEvent(this, "createUserReference", this.createUserReference, createUserReference));
      if (the_createUserReference != null && the_createUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_createUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "createUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "createUserReference", this.createUserReference, createUserReference));
   }

   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MODIFY_USER = "modifyUser";
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String MODIFY_USER_REFERENCE = "modifyUserReference";
   wt.fc.ObjectReference modifyUserReference;
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.org.WTUser getModifyUser() {
      return (modifyUserReference != null) ? (wt.org.WTUser) modifyUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.fc.ObjectReference getModifyUserReference() {
      return modifyUserReference;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setModifyUser(wt.org.WTUser the_modifyUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setModifyUserReference(the_modifyUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_modifyUser));
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setModifyUserReference(wt.fc.ObjectReference the_modifyUserReference) throws wt.util.WTPropertyVetoException {
      modifyUserReferenceValidate(the_modifyUserReference);
      modifyUserReference = (wt.fc.ObjectReference) the_modifyUserReference;
   }
   void modifyUserReferenceValidate(wt.fc.ObjectReference the_modifyUserReference) throws wt.util.WTPropertyVetoException {
      if (the_modifyUserReference == null || the_modifyUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyUserReference") },
               new java.beans.PropertyChangeEvent(this, "modifyUserReference", this.modifyUserReference, modifyUserReference));
      if (the_modifyUserReference != null && the_modifyUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_modifyUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "modifyUserReference", this.modifyUserReference, modifyUserReference));
   }

   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public e3ps.project.E3PSProjectMaster getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProjectMaster) projectReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
    */
   public void setProject(e3ps.project.E3PSProjectMaster the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProjectMaster) the_project));
   }
   /**
    * @see ext.ket.project.purchase.entity.KETPurchaseProject
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

   java.util.Vector contentVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getContentVector() {
      return contentVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setContentVector(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
      contentVectorValidate(contentVector);
      this.contentVector = contentVector;
   }
   void contentVectorValidate(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
   }

   boolean hasContents;
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public boolean isHasContents() {
      return hasContents;
   }
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public void setHasContents(boolean hasContents) throws wt.util.WTPropertyVetoException {
      hasContentsValidate(hasContents);
      this.hasContents = hasContents;
   }
   void hasContentsValidate(boolean hasContents) throws wt.util.WTPropertyVetoException {
   }

   wt.content.HttpContentOperation operation;
   /**
    * @see wt.content.ContentHolder
    */
   public wt.content.HttpContentOperation getOperation() {
      return operation;
   }
   /**
    * @see wt.content.ContentHolder
    */
   public void setOperation(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
      operationValidate(operation);
      this.operation = operation;
   }
   void operationValidate(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
   }

   java.util.Vector httpVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getHttpVector() {
      return httpVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setHttpVector(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
      httpVectorValidate(httpVector);
      this.httpVector = httpVector;
   }
   void httpVectorValidate(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 1598559779054763962L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( allSizeEndDate );
      output.writeObject( allSizeStartDate );
      output.writeObject( bigo );
      output.writeObject( createUserReference );
      output.writeObject( departmentReference );
      output.writeObject( devOrderEndDate );
      output.writeObject( devOrderStartDate );
      output.writeObject( exaAgreeEndDate );
      output.writeObject( exaAgreeStartDate );
      output.writeObject( imsizeEndDate );
      output.writeObject( imsizeStartDate );
      output.writeObject( imspEndDate );
      output.writeObject( imspStartDate );
      output.writeObject( managerCode );
      output.writeObject( managerDeptCode );
      output.writeObject( managerDeptName );
      output.writeObject( managerName );
      output.writeObject( modifyUserReference );
      output.writeObject( moldTryEndDate );
      output.writeObject( moldTryStartDate );
      output.writeObject( moldmkEndDate );
      output.writeObject( moldmkStartDate );
      output.writeObject( outSourcing );
      output.writeObject( outSourcingGubun );
      output.writeObject( p1EndDate );
      output.writeObject( p1StartDate );
      output.writeObject( p2EndDate );
      output.writeObject( p2StartDate );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( pjtNo );
      output.writeObject( pjtState );
      output.writeObject( pmReference );
      output.writeObject( ppapEndDate1 );
      output.writeObject( ppapEndDate2 );
      output.writeObject( ppapEndDate3 );
      output.writeObject( ppapStartDate1 );
      output.writeObject( ppapStartDate2 );
      output.writeObject( ppapStartDate3 );
      output.writeObject( projectExecEndDate );
      output.writeObject( projectExecStartDate );
      output.writeObject( projectPlanEndDate );
      output.writeObject( projectPlanStartDate );
      output.writeObject( projectReference );
      output.writeObject( trustEndDate );
      output.writeObject( trustStartDate );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETPurchaseProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.purchase.entity.KETPurchaseProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETPurchaseProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setTimestamp( "allSizeEndDate", allSizeEndDate );
      output.setTimestamp( "allSizeStartDate", allSizeStartDate );
      output.setString( "bigo", bigo );
      output.writeObject( "createUserReference", createUserReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "devOrderEndDate", devOrderEndDate );
      output.setTimestamp( "devOrderStartDate", devOrderStartDate );
      output.setTimestamp( "exaAgreeEndDate", exaAgreeEndDate );
      output.setTimestamp( "exaAgreeStartDate", exaAgreeStartDate );
      output.setTimestamp( "imsizeEndDate", imsizeEndDate );
      output.setTimestamp( "imsizeStartDate", imsizeStartDate );
      output.setTimestamp( "imspEndDate", imspEndDate );
      output.setTimestamp( "imspStartDate", imspStartDate );
      output.setString( "managerCode", managerCode );
      output.setString( "managerDeptCode", managerDeptCode );
      output.setString( "managerDeptName", managerDeptName );
      output.setString( "managerName", managerName );
      output.writeObject( "modifyUserReference", modifyUserReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "moldTryEndDate", moldTryEndDate );
      output.setTimestamp( "moldTryStartDate", moldTryStartDate );
      output.setTimestamp( "moldmkEndDate", moldmkEndDate );
      output.setTimestamp( "moldmkStartDate", moldmkStartDate );
      output.setString( "outSourcing", outSourcing );
      output.setString( "outSourcingGubun", outSourcingGubun );
      output.setTimestamp( "p1EndDate", p1EndDate );
      output.setTimestamp( "p1StartDate", p1StartDate );
      output.setTimestamp( "p2EndDate", p2EndDate );
      output.setTimestamp( "p2StartDate", p2StartDate );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "pjtNo", pjtNo );
      output.setString( "pjtState", pjtState );
      output.writeObject( "pmReference", pmReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "ppapEndDate1", ppapEndDate1 );
      output.setTimestamp( "ppapEndDate2", ppapEndDate2 );
      output.setTimestamp( "ppapEndDate3", ppapEndDate3 );
      output.setTimestamp( "ppapStartDate1", ppapStartDate1 );
      output.setTimestamp( "ppapStartDate2", ppapStartDate2 );
      output.setTimestamp( "ppapStartDate3", ppapStartDate3 );
      output.setTimestamp( "projectExecEndDate", projectExecEndDate );
      output.setTimestamp( "projectExecStartDate", projectExecStartDate );
      output.setTimestamp( "projectPlanEndDate", projectPlanEndDate );
      output.setTimestamp( "projectPlanStartDate", projectPlanStartDate );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "trustEndDate", trustEndDate );
      output.setTimestamp( "trustStartDate", trustStartDate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      allSizeEndDate = input.getTimestamp( "allSizeEndDate" );
      allSizeStartDate = input.getTimestamp( "allSizeStartDate" );
      bigo = input.getString( "bigo" );
      createUserReference = (wt.fc.ObjectReference) input.readObject( "createUserReference", createUserReference, wt.fc.ObjectReference.class, true );
      departmentReference = (wt.fc.ObjectReference) input.readObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      devOrderEndDate = input.getTimestamp( "devOrderEndDate" );
      devOrderStartDate = input.getTimestamp( "devOrderStartDate" );
      exaAgreeEndDate = input.getTimestamp( "exaAgreeEndDate" );
      exaAgreeStartDate = input.getTimestamp( "exaAgreeStartDate" );
      imsizeEndDate = input.getTimestamp( "imsizeEndDate" );
      imsizeStartDate = input.getTimestamp( "imsizeStartDate" );
      imspEndDate = input.getTimestamp( "imspEndDate" );
      imspStartDate = input.getTimestamp( "imspStartDate" );
      managerCode = input.getString( "managerCode" );
      managerDeptCode = input.getString( "managerDeptCode" );
      managerDeptName = input.getString( "managerDeptName" );
      managerName = input.getString( "managerName" );
      modifyUserReference = (wt.fc.ObjectReference) input.readObject( "modifyUserReference", modifyUserReference, wt.fc.ObjectReference.class, true );
      moldTryEndDate = input.getTimestamp( "moldTryEndDate" );
      moldTryStartDate = input.getTimestamp( "moldTryStartDate" );
      moldmkEndDate = input.getTimestamp( "moldmkEndDate" );
      moldmkStartDate = input.getTimestamp( "moldmkStartDate" );
      outSourcing = input.getString( "outSourcing" );
      outSourcingGubun = input.getString( "outSourcingGubun" );
      p1EndDate = input.getTimestamp( "p1EndDate" );
      p1StartDate = input.getTimestamp( "p1StartDate" );
      p2EndDate = input.getTimestamp( "p2EndDate" );
      p2StartDate = input.getTimestamp( "p2StartDate" );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      pjtNo = input.getString( "pjtNo" );
      pjtState = input.getString( "pjtState" );
      pmReference = (wt.fc.ObjectReference) input.readObject( "pmReference", pmReference, wt.fc.ObjectReference.class, true );
      ppapEndDate1 = input.getTimestamp( "ppapEndDate1" );
      ppapEndDate2 = input.getTimestamp( "ppapEndDate2" );
      ppapEndDate3 = input.getTimestamp( "ppapEndDate3" );
      ppapStartDate1 = input.getTimestamp( "ppapStartDate1" );
      ppapStartDate2 = input.getTimestamp( "ppapStartDate2" );
      ppapStartDate3 = input.getTimestamp( "ppapStartDate3" );
      projectExecEndDate = input.getTimestamp( "projectExecEndDate" );
      projectExecStartDate = input.getTimestamp( "projectExecStartDate" );
      projectPlanEndDate = input.getTimestamp( "projectPlanEndDate" );
      projectPlanStartDate = input.getTimestamp( "projectPlanStartDate" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      trustEndDate = input.getTimestamp( "trustEndDate" );
      trustStartDate = input.getTimestamp( "trustStartDate" );
   }

   boolean readVersion1598559779054763962L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      allSizeEndDate = (java.sql.Timestamp) input.readObject();
      allSizeStartDate = (java.sql.Timestamp) input.readObject();
      bigo = (java.lang.String) input.readObject();
      createUserReference = (wt.fc.ObjectReference) input.readObject();
      departmentReference = (wt.fc.ObjectReference) input.readObject();
      devOrderEndDate = (java.sql.Timestamp) input.readObject();
      devOrderStartDate = (java.sql.Timestamp) input.readObject();
      exaAgreeEndDate = (java.sql.Timestamp) input.readObject();
      exaAgreeStartDate = (java.sql.Timestamp) input.readObject();
      imsizeEndDate = (java.sql.Timestamp) input.readObject();
      imsizeStartDate = (java.sql.Timestamp) input.readObject();
      imspEndDate = (java.sql.Timestamp) input.readObject();
      imspStartDate = (java.sql.Timestamp) input.readObject();
      managerCode = (java.lang.String) input.readObject();
      managerDeptCode = (java.lang.String) input.readObject();
      managerDeptName = (java.lang.String) input.readObject();
      managerName = (java.lang.String) input.readObject();
      modifyUserReference = (wt.fc.ObjectReference) input.readObject();
      moldTryEndDate = (java.sql.Timestamp) input.readObject();
      moldTryStartDate = (java.sql.Timestamp) input.readObject();
      moldmkEndDate = (java.sql.Timestamp) input.readObject();
      moldmkStartDate = (java.sql.Timestamp) input.readObject();
      outSourcing = (java.lang.String) input.readObject();
      outSourcingGubun = (java.lang.String) input.readObject();
      p1EndDate = (java.sql.Timestamp) input.readObject();
      p1StartDate = (java.sql.Timestamp) input.readObject();
      p2EndDate = (java.sql.Timestamp) input.readObject();
      p2StartDate = (java.sql.Timestamp) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      pjtState = (java.lang.String) input.readObject();
      pmReference = (wt.fc.ObjectReference) input.readObject();
      ppapEndDate1 = (java.sql.Timestamp) input.readObject();
      ppapEndDate2 = (java.sql.Timestamp) input.readObject();
      ppapEndDate3 = (java.sql.Timestamp) input.readObject();
      ppapStartDate1 = (java.sql.Timestamp) input.readObject();
      ppapStartDate2 = (java.sql.Timestamp) input.readObject();
      ppapStartDate3 = (java.sql.Timestamp) input.readObject();
      projectExecEndDate = (java.sql.Timestamp) input.readObject();
      projectExecStartDate = (java.sql.Timestamp) input.readObject();
      projectPlanEndDate = (java.sql.Timestamp) input.readObject();
      projectPlanStartDate = (java.sql.Timestamp) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      trustEndDate = (java.sql.Timestamp) input.readObject();
      trustStartDate = (java.sql.Timestamp) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETPurchaseProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1598559779054763962L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETPurchaseProject( _KETPurchaseProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
