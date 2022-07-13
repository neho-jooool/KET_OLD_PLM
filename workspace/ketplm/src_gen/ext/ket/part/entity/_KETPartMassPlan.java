package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartMassPlan implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartMassPlan.class.getName();

   /**
    * 품번
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 품번
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 품번
    *
    * @see ext.ket.part.entity.KETPartMassPlan
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
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * 품명
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * 품명
    *
    * @see ext.ket.part.entity.KETPartMassPlan
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
    * 대표 프로젝트번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * 대표 프로젝트번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * 대표 프로젝트번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
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
    * 프로젝트명
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PJT_NAME = "pjtName";
   static int PJT_NAME_UPPER_LIMIT = -1;
   java.lang.String pjtName;
   /**
    * 프로젝트명
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getPjtName() {
      return pjtName;
   }
   /**
    * 프로젝트명
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setPjtName(java.lang.String pjtName) throws wt.util.WTPropertyVetoException {
      pjtNameValidate(pjtName);
      this.pjtName = pjtName;
   }
   void pjtNameValidate(java.lang.String pjtName) throws wt.util.WTPropertyVetoException {
      if (PJT_NAME_UPPER_LIMIT < 1) {
         try { PJT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_NAME_UPPER_LIMIT = 200; }
      }
      if (pjtName != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtName.toString(), PJT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtName"), java.lang.String.valueOf(java.lang.Math.min(PJT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtName", this.pjtName, pjtName));
   }

   /**
    * 연관프로젝트번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PJT_NOS = "pjtNos";
   static int PJT_NOS_UPPER_LIMIT = -1;
   java.lang.String pjtNos;
   /**
    * 연관프로젝트번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getPjtNos() {
      return pjtNos;
   }
   /**
    * 연관프로젝트번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setPjtNos(java.lang.String pjtNos) throws wt.util.WTPropertyVetoException {
      pjtNosValidate(pjtNos);
      this.pjtNos = pjtNos;
   }
   void pjtNosValidate(java.lang.String pjtNos) throws wt.util.WTPropertyVetoException {
      if (PJT_NOS_UPPER_LIMIT < 1) {
         try { PJT_NOS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtNos").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_NOS_UPPER_LIMIT = 200; }
      }
      if (pjtNos != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtNos.toString(), PJT_NOS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtNos"), java.lang.String.valueOf(java.lang.Math.min(PJT_NOS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtNos", this.pjtNos, pjtNos));
   }

   /**
    * Pilot,양산
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PROCESS_GB = "processGb";
   static int PROCESS_GB_UPPER_LIMIT = -1;
   java.lang.String processGb;
   /**
    * Pilot,양산
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getProcessGb() {
      return processGb;
   }
   /**
    * Pilot,양산
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setProcessGb(java.lang.String processGb) throws wt.util.WTPropertyVetoException {
      processGbValidate(processGb);
      this.processGb = processGb;
   }
   void processGbValidate(java.lang.String processGb) throws wt.util.WTPropertyVetoException {
      if (PROCESS_GB_UPPER_LIMIT < 1) {
         try { PROCESS_GB_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("processGb").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROCESS_GB_UPPER_LIMIT = 200; }
      }
      if (processGb != null && !wt.fc.PersistenceHelper.checkStoredLength(processGb.toString(), PROCESS_GB_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "processGb"), java.lang.String.valueOf(java.lang.Math.min(PROCESS_GB_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "processGb", this.processGb, processGb));
   }

   /**
    * ECO번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String ECO_NO = "ecoNo";
   static int ECO_NO_UPPER_LIMIT = -1;
   java.lang.String ecoNo;
   /**
    * ECO번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getEcoNo() {
      return ecoNo;
   }
   /**
    * ECO번호
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setEcoNo(java.lang.String ecoNo) throws wt.util.WTPropertyVetoException {
      ecoNoValidate(ecoNo);
      this.ecoNo = ecoNo;
   }
   void ecoNoValidate(java.lang.String ecoNo) throws wt.util.WTPropertyVetoException {
      if (ECO_NO_UPPER_LIMIT < 1) {
         try { ECO_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_NO_UPPER_LIMIT = 200; }
      }
      if (ecoNo != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoNo.toString(), ECO_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoNo"), java.lang.String.valueOf(java.lang.Math.min(ECO_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoNo", this.ecoNo, ecoNo));
   }

   /**
    * 양산시작일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String MAS_START_DATE = "masStartDate";
   java.sql.Timestamp masStartDate;
   /**
    * 양산시작일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.sql.Timestamp getMasStartDate() {
      return masStartDate;
   }
   /**
    * 양산시작일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setMasStartDate(java.sql.Timestamp masStartDate) throws wt.util.WTPropertyVetoException {
      masStartDateValidate(masStartDate);
      this.masStartDate = masStartDate;
   }
   void masStartDateValidate(java.sql.Timestamp masStartDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트종료일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PJT_END_DATE = "pjtEndDate";
   java.sql.Timestamp pjtEndDate;
   /**
    * 프로젝트종료일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.sql.Timestamp getPjtEndDate() {
      return pjtEndDate;
   }
   /**
    * 프로젝트종료일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setPjtEndDate(java.sql.Timestamp pjtEndDate) throws wt.util.WTPropertyVetoException {
      pjtEndDateValidate(pjtEndDate);
      this.pjtEndDate = pjtEndDate;
   }
   void pjtEndDateValidate(java.sql.Timestamp pjtEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * DR6종료일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String DR6_END_DATE = "dr6EndDate";
   java.sql.Timestamp dr6EndDate;
   /**
    * DR6종료일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.sql.Timestamp getDr6EndDate() {
      return dr6EndDate;
   }
   /**
    * DR6종료일
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setDr6EndDate(java.sql.Timestamp dr6EndDate) throws wt.util.WTPropertyVetoException {
      dr6EndDateValidate(dr6EndDate);
      this.dr6EndDate = dr6EndDate;
   }
   void dr6EndDateValidate(java.sql.Timestamp dr6EndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 최초ECO승인일(양산이관)
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String ECO_APPROVE_DATE = "ecoApproveDate";
   java.sql.Timestamp ecoApproveDate;
   /**
    * 최초ECO승인일(양산이관)
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.sql.Timestamp getEcoApproveDate() {
      return ecoApproveDate;
   }
   /**
    * 최초ECO승인일(양산이관)
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setEcoApproveDate(java.sql.Timestamp ecoApproveDate) throws wt.util.WTPropertyVetoException {
      ecoApproveDateValidate(ecoApproveDate);
      this.ecoApproveDate = ecoApproveDate;
   }
   void ecoApproveDateValidate(java.sql.Timestamp ecoApproveDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 수정자이름
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String MODIFY_NAME = "modifyName";
   static int MODIFY_NAME_UPPER_LIMIT = -1;
   java.lang.String modifyName;
   /**
    * 수정자이름
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getModifyName() {
      return modifyName;
   }
   /**
    * 수정자이름
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setModifyName(java.lang.String modifyName) throws wt.util.WTPropertyVetoException {
      modifyNameValidate(modifyName);
      this.modifyName = modifyName;
   }
   void modifyNameValidate(java.lang.String modifyName) throws wt.util.WTPropertyVetoException {
      if (MODIFY_NAME_UPPER_LIMIT < 1) {
         try { MODIFY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifyName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFY_NAME_UPPER_LIMIT = 200; }
      }
      if (modifyName != null && !wt.fc.PersistenceHelper.checkStoredLength(modifyName.toString(), MODIFY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyName"), java.lang.String.valueOf(java.lang.Math.min(MODIFY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifyName", this.modifyName, modifyName));
   }

   /**
    * 수정자아이디
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String MODIFY_CODE = "modifyCode";
   static int MODIFY_CODE_UPPER_LIMIT = -1;
   java.lang.String modifyCode;
   /**
    * 수정자아이디
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getModifyCode() {
      return modifyCode;
   }
   /**
    * 수정자아이디
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setModifyCode(java.lang.String modifyCode) throws wt.util.WTPropertyVetoException {
      modifyCodeValidate(modifyCode);
      this.modifyCode = modifyCode;
   }
   void modifyCodeValidate(java.lang.String modifyCode) throws wt.util.WTPropertyVetoException {
      if (MODIFY_CODE_UPPER_LIMIT < 1) {
         try { MODIFY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifyCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFY_CODE_UPPER_LIMIT = 200; }
      }
      if (modifyCode != null && !wt.fc.PersistenceHelper.checkStoredLength(modifyCode.toString(), MODIFY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyCode"), java.lang.String.valueOf(java.lang.Math.min(MODIFY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifyCode", this.modifyCode, modifyCode));
   }

   /**
    * 수정자부서
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String MODIFY_DEPT_NAME = "modifyDeptName";
   static int MODIFY_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String modifyDeptName;
   /**
    * 수정자부서
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getModifyDeptName() {
      return modifyDeptName;
   }
   /**
    * 수정자부서
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setModifyDeptName(java.lang.String modifyDeptName) throws wt.util.WTPropertyVetoException {
      modifyDeptNameValidate(modifyDeptName);
      this.modifyDeptName = modifyDeptName;
   }
   void modifyDeptNameValidate(java.lang.String modifyDeptName) throws wt.util.WTPropertyVetoException {
      if (MODIFY_DEPT_NAME_UPPER_LIMIT < 1) {
         try { MODIFY_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifyDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFY_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (modifyDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(modifyDeptName.toString(), MODIFY_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyDeptName"), java.lang.String.valueOf(java.lang.Math.min(MODIFY_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifyDeptName", this.modifyDeptName, modifyDeptName));
   }

   /**
    * 수정자부서코드
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String MODIFY_DEPT_CODE = "modifyDeptCode";
   static int MODIFY_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String modifyDeptCode;
   /**
    * 수정자부서코드
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getModifyDeptCode() {
      return modifyDeptCode;
   }
   /**
    * 수정자부서코드
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setModifyDeptCode(java.lang.String modifyDeptCode) throws wt.util.WTPropertyVetoException {
      modifyDeptCodeValidate(modifyDeptCode);
      this.modifyDeptCode = modifyDeptCode;
   }
   void modifyDeptCodeValidate(java.lang.String modifyDeptCode) throws wt.util.WTPropertyVetoException {
      if (MODIFY_DEPT_CODE_UPPER_LIMIT < 1) {
         try { MODIFY_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifyDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFY_DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (modifyDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(modifyDeptCode.toString(), MODIFY_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyDeptCode"), java.lang.String.valueOf(java.lang.Math.min(MODIFY_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifyDeptCode", this.modifyDeptCode, modifyDeptCode));
   }

   /**
    * 비고
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String BIGO = "bigo";
   static int BIGO_UPPER_LIMIT = -1;
   java.lang.String bigo;
   /**
    * 비고
    *
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public java.lang.String getBigo() {
      return bigo;
   }
   /**
    * 비고
    *
    * @see ext.ket.part.entity.KETPartMassPlan
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
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PART_MASTER = "partMaster";
   /**
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public static final java.lang.String PART_MASTER_REFERENCE = "partMasterReference";
   wt.fc.ObjectReference partMasterReference;
   /**
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public wt.part.WTPartMaster getPartMaster() {
      return (partMasterReference != null) ? (wt.part.WTPartMaster) partMasterReference.getObject() : null;
   }
   /**
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public wt.fc.ObjectReference getPartMasterReference() {
      return partMasterReference;
   }
   /**
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setPartMaster(wt.part.WTPartMaster the_partMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartMasterReference(the_partMaster == null ? null : wt.fc.ObjectReference.newObjectReference((wt.part.WTPartMaster) the_partMaster));
   }
   /**
    * @see ext.ket.part.entity.KETPartMassPlan
    */
   public void setPartMasterReference(wt.fc.ObjectReference the_partMasterReference) throws wt.util.WTPropertyVetoException {
      partMasterReferenceValidate(the_partMasterReference);
      partMasterReference = (wt.fc.ObjectReference) the_partMasterReference;
   }
   void partMasterReferenceValidate(wt.fc.ObjectReference the_partMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_partMasterReference == null || the_partMasterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partMasterReference") },
               new java.beans.PropertyChangeEvent(this, "partMasterReference", this.partMasterReference, partMasterReference));
      if (the_partMasterReference != null && the_partMasterReference.getReferencedClass() != null &&
            !wt.part.WTPartMaster.class.isAssignableFrom(the_partMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "partMasterReference", this.partMasterReference, partMasterReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -1507592629628630317L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( bigo );
      output.writeObject( dr6EndDate );
      output.writeObject( ecoApproveDate );
      output.writeObject( ecoNo );
      output.writeObject( masStartDate );
      output.writeObject( modifyCode );
      output.writeObject( modifyDeptCode );
      output.writeObject( modifyDeptName );
      output.writeObject( modifyName );
      output.writeObject( owner );
      output.writeObject( partMasterReference );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( pjtEndDate );
      output.writeObject( pjtName );
      output.writeObject( pjtNo );
      output.writeObject( pjtNos );
      output.writeObject( processGb );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.KETPartMassPlan) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "bigo", bigo );
      output.setTimestamp( "dr6EndDate", dr6EndDate );
      output.setTimestamp( "ecoApproveDate", ecoApproveDate );
      output.setString( "ecoNo", ecoNo );
      output.setTimestamp( "masStartDate", masStartDate );
      output.setString( "modifyCode", modifyCode );
      output.setString( "modifyDeptCode", modifyDeptCode );
      output.setString( "modifyDeptName", modifyDeptName );
      output.setString( "modifyName", modifyName );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "partMasterReference", partMasterReference, wt.fc.ObjectReference.class, true );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setTimestamp( "pjtEndDate", pjtEndDate );
      output.setString( "pjtName", pjtName );
      output.setString( "pjtNo", pjtNo );
      output.setString( "pjtNos", pjtNos );
      output.setString( "processGb", processGb );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      bigo = input.getString( "bigo" );
      dr6EndDate = input.getTimestamp( "dr6EndDate" );
      ecoApproveDate = input.getTimestamp( "ecoApproveDate" );
      ecoNo = input.getString( "ecoNo" );
      masStartDate = input.getTimestamp( "masStartDate" );
      modifyCode = input.getString( "modifyCode" );
      modifyDeptCode = input.getString( "modifyDeptCode" );
      modifyDeptName = input.getString( "modifyDeptName" );
      modifyName = input.getString( "modifyName" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partMasterReference = (wt.fc.ObjectReference) input.readObject( "partMasterReference", partMasterReference, wt.fc.ObjectReference.class, true );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      pjtEndDate = input.getTimestamp( "pjtEndDate" );
      pjtName = input.getString( "pjtName" );
      pjtNo = input.getString( "pjtNo" );
      pjtNos = input.getString( "pjtNos" );
      processGb = input.getString( "processGb" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_1507592629628630317L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      bigo = (java.lang.String) input.readObject();
      dr6EndDate = (java.sql.Timestamp) input.readObject();
      ecoApproveDate = (java.sql.Timestamp) input.readObject();
      ecoNo = (java.lang.String) input.readObject();
      masStartDate = (java.sql.Timestamp) input.readObject();
      modifyCode = (java.lang.String) input.readObject();
      modifyDeptCode = (java.lang.String) input.readObject();
      modifyDeptName = (java.lang.String) input.readObject();
      modifyName = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partMasterReference = (wt.fc.ObjectReference) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      pjtEndDate = (java.sql.Timestamp) input.readObject();
      pjtName = (java.lang.String) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      pjtNos = (java.lang.String) input.readObject();
      processGb = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETPartMassPlan thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1507592629628630317L( input, readSerialVersionUID, superDone );
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
