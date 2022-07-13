package e3ps.ews.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETEarlyWarning extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ews.entity.entityResource";
   static final java.lang.String CLASSNAME = KETEarlyWarning.class.getName();

   /**
    * 프로젝트 번호
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * 프로젝트 번호
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * 프로젝트 번호
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * 사내품 또는 외주품 구분정보
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String IN_OUT = "inOut";
   static int IN_OUT_UPPER_LIMIT = -1;
   java.lang.String inOut;
   /**
    * 사내품 또는 외주품 구분정보
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getInOut() {
      return inOut;
   }
   /**
    * 사내품 또는 외주품 구분정보
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setInOut(java.lang.String inOut) throws wt.util.WTPropertyVetoException {
      inOutValidate(inOut);
      this.inOut = inOut;
   }
   void inOutValidate(java.lang.String inOut) throws wt.util.WTPropertyVetoException {
      if (IN_OUT_UPPER_LIMIT < 1) {
         try { IN_OUT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inOut").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_OUT_UPPER_LIMIT = 200; }
      }
      if (inOut != null && !wt.fc.PersistenceHelper.checkStoredLength(inOut.toString(), IN_OUT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inOut"), java.lang.String.valueOf(java.lang.Math.min(IN_OUT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inOut", this.inOut, inOut));
   }

   /**
    * 대상 제품을 생산하는 팀의 코드값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String PROTEAM_NO = "proteamNo";
   static int PROTEAM_NO_UPPER_LIMIT = -1;
   java.lang.String proteamNo;
   /**
    * 대상 제품을 생산하는 팀의 코드값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getProteamNo() {
      return proteamNo;
   }
   /**
    * 대상 제품을 생산하는 팀의 코드값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setProteamNo(java.lang.String proteamNo) throws wt.util.WTPropertyVetoException {
      proteamNoValidate(proteamNo);
      this.proteamNo = proteamNo;
   }
   void proteamNoValidate(java.lang.String proteamNo) throws wt.util.WTPropertyVetoException {
      if (PROTEAM_NO_UPPER_LIMIT < 1) {
         try { PROTEAM_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("proteamNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROTEAM_NO_UPPER_LIMIT = 200; }
      }
      if (proteamNo != null && !wt.fc.PersistenceHelper.checkStoredLength(proteamNo.toString(), PROTEAM_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "proteamNo"), java.lang.String.valueOf(java.lang.Math.min(PROTEAM_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "proteamNo", this.proteamNo, proteamNo));
   }

   /**
    * 대상 제품을 생산하는 협력사의 코드값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String PARTNER_NO = "partnerNo";
   static int PARTNER_NO_UPPER_LIMIT = -1;
   java.lang.String partnerNo;
   /**
    * 대상 제품을 생산하는 협력사의 코드값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getPartnerNo() {
      return partnerNo;
   }
   /**
    * 대상 제품을 생산하는 협력사의 코드값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setPartnerNo(java.lang.String partnerNo) throws wt.util.WTPropertyVetoException {
      partnerNoValidate(partnerNo);
      this.partnerNo = partnerNo;
   }
   void partnerNoValidate(java.lang.String partnerNo) throws wt.util.WTPropertyVetoException {
      if (PARTNER_NO_UPPER_LIMIT < 1) {
         try { PARTNER_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partnerNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PARTNER_NO_UPPER_LIMIT = 200; }
      }
      if (partnerNo != null && !wt.fc.PersistenceHelper.checkStoredLength(partnerNo.toString(), PARTNER_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partnerNo"), java.lang.String.valueOf(java.lang.Math.min(PARTNER_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partnerNo", this.partnerNo, partnerNo));
   }

   /**
    * 수행담당자(정) 아이디 값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String FST_CHARGE = "fstCharge";
   static int FST_CHARGE_UPPER_LIMIT = -1;
   java.lang.String fstCharge;
   /**
    * 수행담당자(정) 아이디 값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getFstCharge() {
      return fstCharge;
   }
   /**
    * 수행담당자(정) 아이디 값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setFstCharge(java.lang.String fstCharge) throws wt.util.WTPropertyVetoException {
      fstChargeValidate(fstCharge);
      this.fstCharge = fstCharge;
   }
   void fstChargeValidate(java.lang.String fstCharge) throws wt.util.WTPropertyVetoException {
      if (FST_CHARGE_UPPER_LIMIT < 1) {
         try { FST_CHARGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("fstCharge").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FST_CHARGE_UPPER_LIMIT = 200; }
      }
      if (fstCharge != null && !wt.fc.PersistenceHelper.checkStoredLength(fstCharge.toString(), FST_CHARGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "fstCharge"), java.lang.String.valueOf(java.lang.Math.min(FST_CHARGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "fstCharge", this.fstCharge, fstCharge));
   }

   /**
    * 수행담당자(부) 아이디 값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String SND_CHARGE = "sndCharge";
   static int SND_CHARGE_UPPER_LIMIT = -1;
   java.lang.String sndCharge;
   /**
    * 수행담당자(부) 아이디 값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getSndCharge() {
      return sndCharge;
   }
   /**
    * 수행담당자(부) 아이디 값
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setSndCharge(java.lang.String sndCharge) throws wt.util.WTPropertyVetoException {
      sndChargeValidate(sndCharge);
      this.sndCharge = sndCharge;
   }
   void sndChargeValidate(java.lang.String sndCharge) throws wt.util.WTPropertyVetoException {
      if (SND_CHARGE_UPPER_LIMIT < 1) {
         try { SND_CHARGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sndCharge").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SND_CHARGE_UPPER_LIMIT = 200; }
      }
      if (sndCharge != null && !wt.fc.PersistenceHelper.checkStoredLength(sndCharge.toString(), SND_CHARGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sndCharge"), java.lang.String.valueOf(java.lang.Math.min(SND_CHARGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sndCharge", this.sndCharge, sndCharge));
   }

   /**
    * 초기유동관리 활동개월수
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String WORKING_MM = "workingMM";
   int workingMM;
   /**
    * 초기유동관리 활동개월수
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public int getWorkingMM() {
      return workingMM;
   }
   /**
    * 초기유동관리 활동개월수
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setWorkingMM(int workingMM) throws wt.util.WTPropertyVetoException {
      workingMMValidate(workingMM);
      this.workingMM = workingMM;
   }
   void workingMMValidate(int workingMM) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초기유동관리 시작일
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String START_DATE = "startDate";
   java.sql.Timestamp startDate;
   /**
    * 초기유동관리 시작일
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.sql.Timestamp getStartDate() {
      return startDate;
   }
   /**
    * 초기유동관리 시작일
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setStartDate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
      startDateValidate(startDate);
      this.startDate = startDate;
   }
   void startDateValidate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초기유동관리 종료일
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String END_DATE = "endDate";
   java.sql.Timestamp endDate;
   /**
    * 초기유동관리 종료일
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.sql.Timestamp getEndDate() {
      return endDate;
   }
   /**
    * 초기유동관리 종료일
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setEndDate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초기유동관리 계획서 제출날짜
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * 초기유동관리 계획서 제출날짜
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * 초기유동관리 계획서 제출날짜
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 추가 속성 항목1
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * 추가 속성 항목1
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * 추가 속성 항목1
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * 추가 속성 항목2
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * 추가 속성 항목2
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * 추가 속성 항목3
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * 추가 속성 항목3
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * 추가 속성 항목4
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * 추가 속성 항목4
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * 추가 속성 항목5
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * 추가 속성 항목5
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * 추가 속성 항목6
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * 추가 속성 항목6
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * 추가 속성 항목7
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * 추가 속성 항목7
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * 추가 속성 항목8
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * 추가 속성 항목8
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * 추가 속성 항목9
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * 추가 속성 항목9
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * 추가 속성 항목10
    *
    * @see e3ps.ews.entity.KETEarlyWarning
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * 추가 속성 항목10
    *
    * @see e3ps.ews.entity.KETEarlyWarning
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

   public static final long EXTERNALIZATION_VERSION_UID = 2922192803286247192L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

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
      output.writeObject( endDate );
      output.writeObject( fstCharge );
      output.writeObject( inOut );
      output.writeObject( partnerNo );
      output.writeObject( pjtNo );
      output.writeObject( planDate );
      output.writeObject( proteamNo );
      output.writeObject( sndCharge );
      output.writeObject( startDate );
      output.writeInt( workingMM );
   }

   protected void super_writeExternal_KETEarlyWarning(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ews.entity.KETEarlyWarning) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETEarlyWarning(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

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
      output.setTimestamp( "endDate", endDate );
      output.setString( "fstCharge", fstCharge );
      output.setString( "inOut", inOut );
      output.setString( "partnerNo", partnerNo );
      output.setString( "pjtNo", pjtNo );
      output.setTimestamp( "planDate", planDate );
      output.setString( "proteamNo", proteamNo );
      output.setString( "sndCharge", sndCharge );
      output.setTimestamp( "startDate", startDate );
      output.setInt( "workingMM", workingMM );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

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
      endDate = input.getTimestamp( "endDate" );
      fstCharge = input.getString( "fstCharge" );
      inOut = input.getString( "inOut" );
      partnerNo = input.getString( "partnerNo" );
      pjtNo = input.getString( "pjtNo" );
      planDate = input.getTimestamp( "planDate" );
      proteamNo = input.getString( "proteamNo" );
      sndCharge = input.getString( "sndCharge" );
      startDate = input.getTimestamp( "startDate" );
      workingMM = input.getInt( "workingMM" );
   }

   boolean readVersion2922192803286247192L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

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
      endDate = (java.sql.Timestamp) input.readObject();
      fstCharge = (java.lang.String) input.readObject();
      inOut = (java.lang.String) input.readObject();
      partnerNo = (java.lang.String) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      proteamNo = (java.lang.String) input.readObject();
      sndCharge = (java.lang.String) input.readObject();
      startDate = (java.sql.Timestamp) input.readObject();
      workingMM = input.readInt();
      return true;
   }

   protected boolean readVersion( KETEarlyWarning thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2922192803286247192L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETEarlyWarning( _KETEarlyWarning thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
