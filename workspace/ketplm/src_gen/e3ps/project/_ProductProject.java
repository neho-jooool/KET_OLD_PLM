package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProductProject extends e3ps.project.E3PSProject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProductProject.class.getName();

   /**
    * 본부구분(자동차/전자)
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String TEAM_TYPE = "teamType";
   static int TEAM_TYPE_UPPER_LIMIT = -1;
   java.lang.String teamType;
   /**
    * 본부구분(자동차/전자)
    *
    * @see e3ps.project.ProductProject
    */
   public java.lang.String getTeamType() {
      return teamType;
   }
   /**
    * 본부구분(자동차/전자)
    *
    * @see e3ps.project.ProductProject
    */
   public void setTeamType(java.lang.String teamType) throws wt.util.WTPropertyVetoException {
      teamTypeValidate(teamType);
      this.teamType = teamType;
   }
   void teamTypeValidate(java.lang.String teamType) throws wt.util.WTPropertyVetoException {
      if (TEAM_TYPE_UPPER_LIMIT < 1) {
         try { TEAM_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("teamType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TEAM_TYPE_UPPER_LIMIT = 200; }
      }
      if (teamType != null && !wt.fc.PersistenceHelper.checkStoredLength(teamType.toString(), TEAM_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "teamType"), java.lang.String.valueOf(java.lang.Math.min(TEAM_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "teamType", this.teamType, teamType));
   }

   /**
    * 조립 Ass'y No
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 조립 Ass'y No
    *
    * @see e3ps.project.ProductProject
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 조립 Ass'y No
    *
    * @see e3ps.project.ProductProject
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
    * 적용차종
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String MODEL = "model";
   static int MODEL_UPPER_LIMIT = -1;
   java.lang.String model;
   /**
    * 적용차종
    *
    * @see e3ps.project.ProductProject
    */
   public java.lang.String getModel() {
      return model;
   }
   /**
    * 적용차종
    *
    * @see e3ps.project.ProductProject
    */
   public void setModel(java.lang.String model) throws wt.util.WTPropertyVetoException {
      modelValidate(model);
      this.model = model;
   }
   void modelValidate(java.lang.String model) throws wt.util.WTPropertyVetoException {
      if (MODEL_UPPER_LIMIT < 1) {
         try { MODEL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("model").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODEL_UPPER_LIMIT = 200; }
      }
      if (model != null && !wt.fc.PersistenceHelper.checkStoredLength(model.toString(), MODEL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "model"), java.lang.String.valueOf(java.lang.Math.min(MODEL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "model", this.model, model));
   }

   /**
    * 원가제출일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String COSTS_DATE = "costsDate";
   java.sql.Timestamp costsDate;
   /**
    * 원가제출일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getCostsDate() {
      return costsDate;
   }
   /**
    * 원가제출일
    *
    * @see e3ps.project.ProductProject
    */
   public void setCostsDate(java.sql.Timestamp costsDate) throws wt.util.WTPropertyVetoException {
      costsDateValidate(costsDate);
      this.costsDate = costsDate;
   }
   void costsDateValidate(java.sql.Timestamp costsDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String ASSEMBLY = "assembly";
   static int ASSEMBLY_UPPER_LIMIT = -1;
   java.lang.String assembly;
   /**
    * @see e3ps.project.ProductProject
    */
   public java.lang.String getAssembly() {
      return assembly;
   }
   /**
    * @see e3ps.project.ProductProject
    */
   public void setAssembly(java.lang.String assembly) throws wt.util.WTPropertyVetoException {
      assemblyValidate(assembly);
      this.assembly = assembly;
   }
   void assemblyValidate(java.lang.String assembly) throws wt.util.WTPropertyVetoException {
      if (ASSEMBLY_UPPER_LIMIT < 1) {
         try { ASSEMBLY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assembly").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSEMBLY_UPPER_LIMIT = 200; }
      }
      if (assembly != null && !wt.fc.PersistenceHelper.checkStoredLength(assembly.toString(), ASSEMBLY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assembly"), java.lang.String.valueOf(java.lang.Math.min(ASSEMBLY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assembly", this.assembly, assembly));
   }

   /**
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PARTNER_NO = "partnerNo";
   static int PARTNER_NO_UPPER_LIMIT = -1;
   java.lang.String partnerNo;
   /**
    * @see e3ps.project.ProductProject
    */
   public java.lang.String getPartnerNo() {
      return partnerNo;
   }
   /**
    * @see e3ps.project.ProductProject
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
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String IS_PM = "isPM";
   boolean isPM;
   /**
    * @see e3ps.project.ProductProject
    */
   public boolean isIsPM() {
      return isPM;
   }
   /**
    * @see e3ps.project.ProductProject
    */
   public void setIsPM(boolean isPM) throws wt.util.WTPropertyVetoException {
      isPMValidate(isPM);
      this.isPM = isPM;
   }
   void isPMValidate(boolean isPM) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String IT_DIVISION = "itDivision";
   static int IT_DIVISION_UPPER_LIMIT = -1;
   java.lang.String itDivision;
   /**
    * @see e3ps.project.ProductProject
    */
   public java.lang.String getItDivision() {
      return itDivision;
   }
   /**
    * @see e3ps.project.ProductProject
    */
   public void setItDivision(java.lang.String itDivision) throws wt.util.WTPropertyVetoException {
      itDivisionValidate(itDivision);
      this.itDivision = itDivision;
   }
   void itDivisionValidate(java.lang.String itDivision) throws wt.util.WTPropertyVetoException {
      if (IT_DIVISION_UPPER_LIMIT < 1) {
         try { IT_DIVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("itDivision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IT_DIVISION_UPPER_LIMIT = 200; }
      }
      if (itDivision != null && !wt.fc.PersistenceHelper.checkStoredLength(itDivision.toString(), IT_DIVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "itDivision"), java.lang.String.valueOf(java.lang.Math.min(IT_DIVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "itDivision", this.itDivision, itDivision));
   }

   /**
    * 개발시작회의(DR1)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE1 = "planEndDate1";
   java.sql.Timestamp planEndDate1;
   /**
    * 개발시작회의(DR1)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate1() {
      return planEndDate1;
   }
   /**
    * 개발시작회의(DR1)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate1(java.sql.Timestamp planEndDate1) throws wt.util.WTPropertyVetoException {
      planEndDate1Validate(planEndDate1);
      this.planEndDate1 = planEndDate1;
   }
   void planEndDate1Validate(java.sql.Timestamp planEndDate1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 개발시작회의(DR1)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE1 = "execEndDate1";
   java.sql.Timestamp execEndDate1;
   /**
    * 개발시작회의(DR1)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate1() {
      return execEndDate1;
   }
   /**
    * 개발시작회의(DR1)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate1(java.sql.Timestamp execEndDate1) throws wt.util.WTPropertyVetoException {
      execEndDate1Validate(execEndDate1);
      this.execEndDate1 = execEndDate1;
   }
   void execEndDate1Validate(java.sql.Timestamp execEndDate1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 투자품의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE2 = "planEndDate2";
   java.sql.Timestamp planEndDate2;
   /**
    * 투자품의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate2() {
      return planEndDate2;
   }
   /**
    * 투자품의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate2(java.sql.Timestamp planEndDate2) throws wt.util.WTPropertyVetoException {
      planEndDate2Validate(planEndDate2);
      this.planEndDate2 = planEndDate2;
   }
   void planEndDate2Validate(java.sql.Timestamp planEndDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 투자품의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE2 = "execEndDate2";
   java.sql.Timestamp execEndDate2;
   /**
    * 투자품의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate2() {
      return execEndDate2;
   }
   /**
    * 투자품의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate2(java.sql.Timestamp execEndDate2) throws wt.util.WTPropertyVetoException {
      execEndDate2Validate(execEndDate2);
      this.execEndDate2 = execEndDate2;
   }
   void execEndDate2Validate(java.sql.Timestamp execEndDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Design Review(DR2)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE3 = "planEndDate3";
   java.sql.Timestamp planEndDate3;
   /**
    * Design Review(DR2)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate3() {
      return planEndDate3;
   }
   /**
    * Design Review(DR2)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate3(java.sql.Timestamp planEndDate3) throws wt.util.WTPropertyVetoException {
      planEndDate3Validate(planEndDate3);
      this.planEndDate3 = planEndDate3;
   }
   void planEndDate3Validate(java.sql.Timestamp planEndDate3) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Design Review(DR2)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE3 = "execEndDate3";
   java.sql.Timestamp execEndDate3;
   /**
    * Design Review(DR2)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate3() {
      return execEndDate3;
   }
   /**
    * Design Review(DR2)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate3(java.sql.Timestamp execEndDate3) throws wt.util.WTPropertyVetoException {
      execEndDate3Validate(execEndDate3);
      this.execEndDate3 = execEndDate3;
   }
   void execEndDate3Validate(java.sql.Timestamp execEndDate3) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 도면출도_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE4 = "planEndDate4";
   java.sql.Timestamp planEndDate4;
   /**
    * 도면출도_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate4() {
      return planEndDate4;
   }
   /**
    * 도면출도_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate4(java.sql.Timestamp planEndDate4) throws wt.util.WTPropertyVetoException {
      planEndDate4Validate(planEndDate4);
      this.planEndDate4 = planEndDate4;
   }
   void planEndDate4Validate(java.sql.Timestamp planEndDate4) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 도면출도_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE4 = "execEndDate4";
   java.sql.Timestamp execEndDate4;
   /**
    * 도면출도_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate4() {
      return execEndDate4;
   }
   /**
    * 도면출도_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate4(java.sql.Timestamp execEndDate4) throws wt.util.WTPropertyVetoException {
      execEndDate4Validate(execEndDate4);
      this.execEndDate4 = execEndDate4;
   }
   void execEndDate4Validate(java.sql.Timestamp execEndDate4) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 금형제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE5 = "planEndDate5";
   java.sql.Timestamp planEndDate5;
   /**
    * 금형제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate5() {
      return planEndDate5;
   }
   /**
    * 금형제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate5(java.sql.Timestamp planEndDate5) throws wt.util.WTPropertyVetoException {
      planEndDate5Validate(planEndDate5);
      this.planEndDate5 = planEndDate5;
   }
   void planEndDate5Validate(java.sql.Timestamp planEndDate5) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 금형제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE5 = "execEndDate5";
   java.sql.Timestamp execEndDate5;
   /**
    * 금형제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate5() {
      return execEndDate5;
   }
   /**
    * 금형제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate5(java.sql.Timestamp execEndDate5) throws wt.util.WTPropertyVetoException {
      execEndDate5Validate(execEndDate5);
      this.execEndDate5 = execEndDate5;
   }
   void execEndDate5Validate(java.sql.Timestamp execEndDate5) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 설비제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE6 = "planEndDate6";
   java.sql.Timestamp planEndDate6;
   /**
    * 설비제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate6() {
      return planEndDate6;
   }
   /**
    * 설비제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate6(java.sql.Timestamp planEndDate6) throws wt.util.WTPropertyVetoException {
      planEndDate6Validate(planEndDate6);
      this.planEndDate6 = planEndDate6;
   }
   void planEndDate6Validate(java.sql.Timestamp planEndDate6) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 설비제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE6 = "execEndDate6";
   java.sql.Timestamp execEndDate6;
   /**
    * 설비제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate6() {
      return execEndDate6;
   }
   /**
    * 설비제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate6(java.sql.Timestamp execEndDate6) throws wt.util.WTPropertyVetoException {
      execEndDate6Validate(execEndDate6);
      this.execEndDate6 = execEndDate6;
   }
   void execEndDate6Validate(java.sql.Timestamp execEndDate6) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품제작(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE7 = "planEndDate7";
   java.sql.Timestamp planEndDate7;
   /**
    * 초도품제작(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate7() {
      return planEndDate7;
   }
   /**
    * 초도품제작(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate7(java.sql.Timestamp planEndDate7) throws wt.util.WTPropertyVetoException {
      planEndDate7Validate(planEndDate7);
      this.planEndDate7 = planEndDate7;
   }
   void planEndDate7Validate(java.sql.Timestamp planEndDate7) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품제작(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE7 = "execEndDate7";
   java.sql.Timestamp execEndDate7;
   /**
    * 초도품제작(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate7() {
      return execEndDate7;
   }
   /**
    * 초도품제작(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate7(java.sql.Timestamp execEndDate7) throws wt.util.WTPropertyVetoException {
      execEndDate7Validate(execEndDate7);
      this.execEndDate7 = execEndDate7;
   }
   void execEndDate7Validate(java.sql.Timestamp execEndDate7) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품평가(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE8 = "planEndDate8";
   java.sql.Timestamp planEndDate8;
   /**
    * 초도품평가(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate8() {
      return planEndDate8;
   }
   /**
    * 초도품평가(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate8(java.sql.Timestamp planEndDate8) throws wt.util.WTPropertyVetoException {
      planEndDate8Validate(planEndDate8);
      this.planEndDate8 = planEndDate8;
   }
   void planEndDate8Validate(java.sql.Timestamp planEndDate8) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품평가(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE8 = "execEndDate8";
   java.sql.Timestamp execEndDate8;
   /**
    * 초도품평가(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate8() {
      return execEndDate8;
   }
   /**
    * 초도품평가(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate8(java.sql.Timestamp execEndDate8) throws wt.util.WTPropertyVetoException {
      execEndDate8Validate(execEndDate8);
      this.execEndDate8 = execEndDate8;
   }
   void execEndDate8Validate(java.sql.Timestamp execEndDate8) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품납품(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE9 = "planEndDate9";
   java.sql.Timestamp planEndDate9;
   /**
    * 초도품납품(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate9() {
      return planEndDate9;
   }
   /**
    * 초도품납품(proto)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate9(java.sql.Timestamp planEndDate9) throws wt.util.WTPropertyVetoException {
      planEndDate9Validate(planEndDate9);
      this.planEndDate9 = planEndDate9;
   }
   void planEndDate9Validate(java.sql.Timestamp planEndDate9) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품납품(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE9 = "execEndDate9";
   java.sql.Timestamp execEndDate9;
   /**
    * 초도품납품(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate9() {
      return execEndDate9;
   }
   /**
    * 초도품납품(proto)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate9(java.sql.Timestamp execEndDate9) throws wt.util.WTPropertyVetoException {
      execEndDate9Validate(execEndDate9);
      this.execEndDate9 = execEndDate9;
   }
   void execEndDate9Validate(java.sql.Timestamp execEndDate9) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PROTO샘플제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE10 = "planEndDate10";
   java.sql.Timestamp planEndDate10;
   /**
    * PROTO샘플제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate10() {
      return planEndDate10;
   }
   /**
    * PROTO샘플제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate10(java.sql.Timestamp planEndDate10) throws wt.util.WTPropertyVetoException {
      planEndDate10Validate(planEndDate10);
      this.planEndDate10 = planEndDate10;
   }
   void planEndDate10Validate(java.sql.Timestamp planEndDate10) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PROTO샘플제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE10 = "execEndDate10";
   java.sql.Timestamp execEndDate10;
   /**
    * PROTO샘플제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate10() {
      return execEndDate10;
   }
   /**
    * PROTO샘플제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate10(java.sql.Timestamp execEndDate10) throws wt.util.WTPropertyVetoException {
      execEndDate10Validate(execEndDate10);
      this.execEndDate10 = execEndDate10;
   }
   void execEndDate10Validate(java.sql.Timestamp execEndDate10) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PROTO샘플납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE11 = "planEndDate11";
   java.sql.Timestamp planEndDate11;
   /**
    * PROTO샘플납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate11() {
      return planEndDate11;
   }
   /**
    * PROTO샘플납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate11(java.sql.Timestamp planEndDate11) throws wt.util.WTPropertyVetoException {
      planEndDate11Validate(planEndDate11);
      this.planEndDate11 = planEndDate11;
   }
   void planEndDate11Validate(java.sql.Timestamp planEndDate11) throws wt.util.WTPropertyVetoException {
   }

   /**
    * PROTO샘플납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE11 = "execEndDate11";
   java.sql.Timestamp execEndDate11;
   /**
    * PROTO샘플납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate11() {
      return execEndDate11;
   }
   /**
    * PROTO샘플납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate11(java.sql.Timestamp execEndDate11) throws wt.util.WTPropertyVetoException {
      execEndDate11Validate(execEndDate11);
      this.execEndDate11 = execEndDate11;
   }
   void execEndDate11Validate(java.sql.Timestamp execEndDate11) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성평가(DV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE12 = "planEndDate12";
   java.sql.Timestamp planEndDate12;
   /**
    * 신뢰성평가(DV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate12() {
      return planEndDate12;
   }
   /**
    * 신뢰성평가(DV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate12(java.sql.Timestamp planEndDate12) throws wt.util.WTPropertyVetoException {
      planEndDate12Validate(planEndDate12);
      this.planEndDate12 = planEndDate12;
   }
   void planEndDate12Validate(java.sql.Timestamp planEndDate12) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성평가(DV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE12 = "execEndDate12";
   java.sql.Timestamp execEndDate12;
   /**
    * 신뢰성평가(DV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate12() {
      return execEndDate12;
   }
   /**
    * 신뢰성평가(DV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate12(java.sql.Timestamp execEndDate12) throws wt.util.WTPropertyVetoException {
      execEndDate12Validate(execEndDate12);
      this.execEndDate12 = execEndDate12;
   }
   void execEndDate12Validate(java.sql.Timestamp execEndDate12) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE1_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE13 = "planEndDate13";
   java.sql.Timestamp planEndDate13;
   /**
    * GATE1_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate13() {
      return planEndDate13;
   }
   /**
    * GATE1_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate13(java.sql.Timestamp planEndDate13) throws wt.util.WTPropertyVetoException {
      planEndDate13Validate(planEndDate13);
      this.planEndDate13 = planEndDate13;
   }
   void planEndDate13Validate(java.sql.Timestamp planEndDate13) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE1_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE13 = "execEndDate13";
   java.sql.Timestamp execEndDate13;
   /**
    * GATE1_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate13() {
      return execEndDate13;
   }
   /**
    * GATE1_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate13(java.sql.Timestamp execEndDate13) throws wt.util.WTPropertyVetoException {
      execEndDate13Validate(execEndDate13);
      this.execEndDate13 = execEndDate13;
   }
   void execEndDate13Validate(java.sql.Timestamp execEndDate13) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE2_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE14 = "planEndDate14";
   java.sql.Timestamp planEndDate14;
   /**
    * GATE2_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate14() {
      return planEndDate14;
   }
   /**
    * GATE2_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate14(java.sql.Timestamp planEndDate14) throws wt.util.WTPropertyVetoException {
      planEndDate14Validate(planEndDate14);
      this.planEndDate14 = planEndDate14;
   }
   void planEndDate14Validate(java.sql.Timestamp planEndDate14) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE2_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE14 = "execEndDate14";
   java.sql.Timestamp execEndDate14;
   /**
    * GATE2_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate14() {
      return execEndDate14;
   }
   /**
    * GATE2_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate14(java.sql.Timestamp execEndDate14) throws wt.util.WTPropertyVetoException {
      execEndDate14Validate(execEndDate14);
      this.execEndDate14 = execEndDate14;
   }
   void execEndDate14Validate(java.sql.Timestamp execEndDate14) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산계획(DR3)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE15 = "planEndDate15";
   java.sql.Timestamp planEndDate15;
   /**
    * 양산계획(DR3)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate15() {
      return planEndDate15;
   }
   /**
    * 양산계획(DR3)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate15(java.sql.Timestamp planEndDate15) throws wt.util.WTPropertyVetoException {
      planEndDate15Validate(planEndDate15);
      this.planEndDate15 = planEndDate15;
   }
   void planEndDate15Validate(java.sql.Timestamp planEndDate15) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산계획(DR3)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE15 = "execEndDate15";
   java.sql.Timestamp execEndDate15;
   /**
    * 양산계획(DR3)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate15() {
      return execEndDate15;
   }
   /**
    * 양산계획(DR3)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate15(java.sql.Timestamp execEndDate15) throws wt.util.WTPropertyVetoException {
      execEndDate15Validate(execEndDate15);
      this.execEndDate15 = execEndDate15;
   }
   void execEndDate15Validate(java.sql.Timestamp execEndDate15) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 금형제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE16 = "planEndDate16";
   java.sql.Timestamp planEndDate16;
   /**
    * 금형제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate16() {
      return planEndDate16;
   }
   /**
    * 금형제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate16(java.sql.Timestamp planEndDate16) throws wt.util.WTPropertyVetoException {
      planEndDate16Validate(planEndDate16);
      this.planEndDate16 = planEndDate16;
   }
   void planEndDate16Validate(java.sql.Timestamp planEndDate16) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 금형제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE16 = "execEndDate16";
   java.sql.Timestamp execEndDate16;
   /**
    * 금형제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate16() {
      return execEndDate16;
   }
   /**
    * 금형제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate16(java.sql.Timestamp execEndDate16) throws wt.util.WTPropertyVetoException {
      execEndDate16Validate(execEndDate16);
      this.execEndDate16 = execEndDate16;
   }
   void execEndDate16Validate(java.sql.Timestamp execEndDate16) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 설비제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE17 = "planEndDate17";
   java.sql.Timestamp planEndDate17;
   /**
    * 설비제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate17() {
      return planEndDate17;
   }
   /**
    * 설비제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate17(java.sql.Timestamp planEndDate17) throws wt.util.WTPropertyVetoException {
      planEndDate17Validate(planEndDate17);
      this.planEndDate17 = planEndDate17;
   }
   void planEndDate17Validate(java.sql.Timestamp planEndDate17) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 설비제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE17 = "execEndDate17";
   java.sql.Timestamp execEndDate17;
   /**
    * 설비제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate17() {
      return execEndDate17;
   }
   /**
    * 설비제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate17(java.sql.Timestamp execEndDate17) throws wt.util.WTPropertyVetoException {
      execEndDate17Validate(execEndDate17);
      this.execEndDate17 = execEndDate17;
   }
   void execEndDate17Validate(java.sql.Timestamp execEndDate17) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE18 = "planEndDate18";
   java.sql.Timestamp planEndDate18;
   /**
    * 초도품제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate18() {
      return planEndDate18;
   }
   /**
    * 초도품제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate18(java.sql.Timestamp planEndDate18) throws wt.util.WTPropertyVetoException {
      planEndDate18Validate(planEndDate18);
      this.planEndDate18 = planEndDate18;
   }
   void planEndDate18Validate(java.sql.Timestamp planEndDate18) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE18 = "execEndDate18";
   java.sql.Timestamp execEndDate18;
   /**
    * 초도품제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate18() {
      return execEndDate18;
   }
   /**
    * 초도품제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate18(java.sql.Timestamp execEndDate18) throws wt.util.WTPropertyVetoException {
      execEndDate18Validate(execEndDate18);
      this.execEndDate18 = execEndDate18;
   }
   void execEndDate18Validate(java.sql.Timestamp execEndDate18) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품평가_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE19 = "planEndDate19";
   java.sql.Timestamp planEndDate19;
   /**
    * 초도품평가_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate19() {
      return planEndDate19;
   }
   /**
    * 초도품평가_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate19(java.sql.Timestamp planEndDate19) throws wt.util.WTPropertyVetoException {
      planEndDate19Validate(planEndDate19);
      this.planEndDate19 = planEndDate19;
   }
   void planEndDate19Validate(java.sql.Timestamp planEndDate19) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품평가_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE19 = "execEndDate19";
   java.sql.Timestamp execEndDate19;
   /**
    * 초도품평가_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate19() {
      return execEndDate19;
   }
   /**
    * 초도품평가_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate19(java.sql.Timestamp execEndDate19) throws wt.util.WTPropertyVetoException {
      execEndDate19Validate(execEndDate19);
      this.execEndDate19 = execEndDate19;
   }
   void execEndDate19Validate(java.sql.Timestamp execEndDate19) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE45 = "planEndDate45";
   java.sql.Timestamp planEndDate45;
   /**
    * 초도품납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate45() {
      return planEndDate45;
   }
   /**
    * 초도품납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate45(java.sql.Timestamp planEndDate45) throws wt.util.WTPropertyVetoException {
      planEndDate45Validate(planEndDate45);
      this.planEndDate45 = planEndDate45;
   }
   void planEndDate45Validate(java.sql.Timestamp planEndDate45) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도품납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE45 = "execEndDate45";
   java.sql.Timestamp execEndDate45;
   /**
    * 초도품납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate45() {
      return execEndDate45;
   }
   /**
    * 초도품납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate45(java.sql.Timestamp execEndDate45) throws wt.util.WTPropertyVetoException {
      execEndDate45Validate(execEndDate45);
      this.execEndDate45 = execEndDate45;
   }
   void execEndDate45Validate(java.sql.Timestamp execEndDate45) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 제품유효성평가(DR4)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE20 = "planEndDate20";
   java.sql.Timestamp planEndDate20;
   /**
    * 제품유효성평가(DR4)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate20() {
      return planEndDate20;
   }
   /**
    * 제품유효성평가(DR4)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate20(java.sql.Timestamp planEndDate20) throws wt.util.WTPropertyVetoException {
      planEndDate20Validate(planEndDate20);
      this.planEndDate20 = planEndDate20;
   }
   void planEndDate20Validate(java.sql.Timestamp planEndDate20) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 제품유효성평가(DR4)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE20 = "execEndDate20";
   java.sql.Timestamp execEndDate20;
   /**
    * 제품유효성평가(DR4)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate20() {
      return execEndDate20;
   }
   /**
    * 제품유효성평가(DR4)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate20(java.sql.Timestamp execEndDate20) throws wt.util.WTPropertyVetoException {
      execEndDate20Validate(execEndDate20);
      this.execEndDate20 = execEndDate20;
   }
   void execEndDate20Validate(java.sql.Timestamp execEndDate20) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성평가(DV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE21 = "planEndDate21";
   java.sql.Timestamp planEndDate21;
   /**
    * 신뢰성평가(DV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate21() {
      return planEndDate21;
   }
   /**
    * 신뢰성평가(DV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate21(java.sql.Timestamp planEndDate21) throws wt.util.WTPropertyVetoException {
      planEndDate21Validate(planEndDate21);
      this.planEndDate21 = planEndDate21;
   }
   void planEndDate21Validate(java.sql.Timestamp planEndDate21) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성평가(DV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE21 = "execEndDate21";
   java.sql.Timestamp execEndDate21;
   /**
    * 신뢰성평가(DV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate21() {
      return execEndDate21;
   }
   /**
    * 신뢰성평가(DV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate21(java.sql.Timestamp execEndDate21) throws wt.util.WTPropertyVetoException {
      execEndDate21Validate(execEndDate21);
      this.execEndDate21 = execEndDate21;
   }
   void execEndDate21Validate(java.sql.Timestamp execEndDate21) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 준비_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE22 = "planEndDate22";
   java.sql.Timestamp planEndDate22;
   /**
    * All-Tool 준비_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate22() {
      return planEndDate22;
   }
   /**
    * All-Tool 준비_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate22(java.sql.Timestamp planEndDate22) throws wt.util.WTPropertyVetoException {
      planEndDate22Validate(planEndDate22);
      this.planEndDate22 = planEndDate22;
   }
   void planEndDate22Validate(java.sql.Timestamp planEndDate22) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 준비_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE22 = "execEndDate22";
   java.sql.Timestamp execEndDate22;
   /**
    * All-Tool 준비_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate22() {
      return execEndDate22;
   }
   /**
    * All-Tool 준비_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate22(java.sql.Timestamp execEndDate22) throws wt.util.WTPropertyVetoException {
      execEndDate22Validate(execEndDate22);
      this.execEndDate22 = execEndDate22;
   }
   void execEndDate22Validate(java.sql.Timestamp execEndDate22) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 준비회의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE23 = "planEndDate23";
   java.sql.Timestamp planEndDate23;
   /**
    * All-Tool 준비회의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate23() {
      return planEndDate23;
   }
   /**
    * All-Tool 준비회의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate23(java.sql.Timestamp planEndDate23) throws wt.util.WTPropertyVetoException {
      planEndDate23Validate(planEndDate23);
      this.planEndDate23 = planEndDate23;
   }
   void planEndDate23Validate(java.sql.Timestamp planEndDate23) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 준비회의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE23 = "execEndDate23";
   java.sql.Timestamp execEndDate23;
   /**
    * All-Tool 준비회의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate23() {
      return execEndDate23;
   }
   /**
    * All-Tool 준비회의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate23(java.sql.Timestamp execEndDate23) throws wt.util.WTPropertyVetoException {
      execEndDate23Validate(execEndDate23);
      this.execEndDate23 = execEndDate23;
   }
   void execEndDate23Validate(java.sql.Timestamp execEndDate23) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 검증_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE24 = "planEndDate24";
   java.sql.Timestamp planEndDate24;
   /**
    * All-Tool 검증_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate24() {
      return planEndDate24;
   }
   /**
    * All-Tool 검증_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate24(java.sql.Timestamp planEndDate24) throws wt.util.WTPropertyVetoException {
      planEndDate24Validate(planEndDate24);
      this.planEndDate24 = planEndDate24;
   }
   void planEndDate24Validate(java.sql.Timestamp planEndDate24) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 검증_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE24 = "execEndDate24";
   java.sql.Timestamp execEndDate24;
   /**
    * All-Tool 검증_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate24() {
      return execEndDate24;
   }
   /**
    * All-Tool 검증_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate24(java.sql.Timestamp execEndDate24) throws wt.util.WTPropertyVetoException {
      execEndDate24Validate(execEndDate24);
      this.execEndDate24 = execEndDate24;
   }
   void execEndDate24Validate(java.sql.Timestamp execEndDate24) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 점검_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE25 = "planEndDate25";
   java.sql.Timestamp planEndDate25;
   /**
    * All-Tool 점검_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate25() {
      return planEndDate25;
   }
   /**
    * All-Tool 점검_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate25(java.sql.Timestamp planEndDate25) throws wt.util.WTPropertyVetoException {
      planEndDate25Validate(planEndDate25);
      this.planEndDate25 = planEndDate25;
   }
   void planEndDate25Validate(java.sql.Timestamp planEndDate25) throws wt.util.WTPropertyVetoException {
   }

   /**
    * All-Tool 점검_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE25 = "execEndDate25";
   java.sql.Timestamp execEndDate25;
   /**
    * All-Tool 점검_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate25() {
      return execEndDate25;
   }
   /**
    * All-Tool 점검_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate25(java.sql.Timestamp execEndDate25) throws wt.util.WTPropertyVetoException {
      execEndDate25Validate(execEndDate25);
      this.execEndDate25 = execEndDate25;
   }
   void execEndDate25Validate(java.sql.Timestamp execEndDate25) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE3_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE26 = "planEndDate26";
   java.sql.Timestamp planEndDate26;
   /**
    * GATE3_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate26() {
      return planEndDate26;
   }
   /**
    * GATE3_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate26(java.sql.Timestamp planEndDate26) throws wt.util.WTPropertyVetoException {
      planEndDate26Validate(planEndDate26);
      this.planEndDate26 = planEndDate26;
   }
   void planEndDate26Validate(java.sql.Timestamp planEndDate26) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE3_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE26 = "execEndDate26";
   java.sql.Timestamp execEndDate26;
   /**
    * GATE3_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate26() {
      return execEndDate26;
   }
   /**
    * GATE3_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate26(java.sql.Timestamp execEndDate26) throws wt.util.WTPropertyVetoException {
      execEndDate26Validate(execEndDate26);
      this.execEndDate26 = execEndDate26;
   }
   void execEndDate26Validate(java.sql.Timestamp execEndDate26) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P1납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE27 = "planEndDate27";
   java.sql.Timestamp planEndDate27;
   /**
    * P1납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate27() {
      return planEndDate27;
   }
   /**
    * P1납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate27(java.sql.Timestamp planEndDate27) throws wt.util.WTPropertyVetoException {
      planEndDate27Validate(planEndDate27);
      this.planEndDate27 = planEndDate27;
   }
   void planEndDate27Validate(java.sql.Timestamp planEndDate27) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P1납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE27 = "execEndDate27";
   java.sql.Timestamp execEndDate27;
   /**
    * P1납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate27() {
      return execEndDate27;
   }
   /**
    * P1납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate27(java.sql.Timestamp execEndDate27) throws wt.util.WTPropertyVetoException {
      execEndDate27Validate(execEndDate27);
      this.execEndDate27 = execEndDate27;
   }
   void execEndDate27Validate(java.sql.Timestamp execEndDate27) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성평가(PV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE28 = "planEndDate28";
   java.sql.Timestamp planEndDate28;
   /**
    * 신뢰성평가(PV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate28() {
      return planEndDate28;
   }
   /**
    * 신뢰성평가(PV)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate28(java.sql.Timestamp planEndDate28) throws wt.util.WTPropertyVetoException {
      planEndDate28Validate(planEndDate28);
      this.planEndDate28 = planEndDate28;
   }
   void planEndDate28Validate(java.sql.Timestamp planEndDate28) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 신뢰성평가(PV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE28 = "execEndDate28";
   java.sql.Timestamp execEndDate28;
   /**
    * 신뢰성평가(PV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate28() {
      return execEndDate28;
   }
   /**
    * 신뢰성평가(PV)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate28(java.sql.Timestamp execEndDate28) throws wt.util.WTPropertyVetoException {
      execEndDate28Validate(execEndDate28);
      this.execEndDate28 = execEndDate28;
   }
   void execEndDate28Validate(java.sql.Timestamp execEndDate28) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Full-Tool 준비회의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE29 = "planEndDate29";
   java.sql.Timestamp planEndDate29;
   /**
    * Full-Tool 준비회의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate29() {
      return planEndDate29;
   }
   /**
    * Full-Tool 준비회의_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate29(java.sql.Timestamp planEndDate29) throws wt.util.WTPropertyVetoException {
      planEndDate29Validate(planEndDate29);
      this.planEndDate29 = planEndDate29;
   }
   void planEndDate29Validate(java.sql.Timestamp planEndDate29) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Full-Tool 준비회의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE29 = "execEndDate29";
   java.sql.Timestamp execEndDate29;
   /**
    * Full-Tool 준비회의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate29() {
      return execEndDate29;
   }
   /**
    * Full-Tool 준비회의_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate29(java.sql.Timestamp execEndDate29) throws wt.util.WTPropertyVetoException {
      execEndDate29Validate(execEndDate29);
      this.execEndDate29 = execEndDate29;
   }
   void execEndDate29Validate(java.sql.Timestamp execEndDate29) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Full-Tool 검증_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE30 = "planEndDate30";
   java.sql.Timestamp planEndDate30;
   /**
    * Full-Tool 검증_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate30() {
      return planEndDate30;
   }
   /**
    * Full-Tool 검증_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate30(java.sql.Timestamp planEndDate30) throws wt.util.WTPropertyVetoException {
      planEndDate30Validate(planEndDate30);
      this.planEndDate30 = planEndDate30;
   }
   void planEndDate30Validate(java.sql.Timestamp planEndDate30) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Full-Tool 검증_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE30 = "execEndDate30";
   java.sql.Timestamp execEndDate30;
   /**
    * Full-Tool 검증_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate30() {
      return execEndDate30;
   }
   /**
    * Full-Tool 검증_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate30(java.sql.Timestamp execEndDate30) throws wt.util.WTPropertyVetoException {
      execEndDate30Validate(execEndDate30);
      this.execEndDate30 = execEndDate30;
   }
   void execEndDate30Validate(java.sql.Timestamp execEndDate30) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산유효성평가(DR5)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE31 = "planEndDate31";
   java.sql.Timestamp planEndDate31;
   /**
    * 양산유효성평가(DR5)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate31() {
      return planEndDate31;
   }
   /**
    * 양산유효성평가(DR5)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate31(java.sql.Timestamp planEndDate31) throws wt.util.WTPropertyVetoException {
      planEndDate31Validate(planEndDate31);
      this.planEndDate31 = planEndDate31;
   }
   void planEndDate31Validate(java.sql.Timestamp planEndDate31) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산유효성평가(DR5)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE31 = "execEndDate31";
   java.sql.Timestamp execEndDate31;
   /**
    * 양산유효성평가(DR5)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate31() {
      return execEndDate31;
   }
   /**
    * 양산유효성평가(DR5)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate31(java.sql.Timestamp execEndDate31) throws wt.util.WTPropertyVetoException {
      execEndDate31Validate(execEndDate31);
      this.execEndDate31 = execEndDate31;
   }
   void execEndDate31Validate(java.sql.Timestamp execEndDate31) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 제품합격_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE32 = "planEndDate32";
   java.sql.Timestamp planEndDate32;
   /**
    * 제품합격_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate32() {
      return planEndDate32;
   }
   /**
    * 제품합격_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate32(java.sql.Timestamp planEndDate32) throws wt.util.WTPropertyVetoException {
      planEndDate32Validate(planEndDate32);
      this.planEndDate32 = planEndDate32;
   }
   void planEndDate32Validate(java.sql.Timestamp planEndDate32) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 제품합격_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE32 = "execEndDate32";
   java.sql.Timestamp execEndDate32;
   /**
    * 제품합격_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate32() {
      return execEndDate32;
   }
   /**
    * 제품합격_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate32(java.sql.Timestamp execEndDate32) throws wt.util.WTPropertyVetoException {
      execEndDate32Validate(execEndDate32);
      this.execEndDate32 = execEndDate32;
   }
   void execEndDate32Validate(java.sql.Timestamp execEndDate32) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 협력사 ISIR승인_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE33 = "planEndDate33";
   java.sql.Timestamp planEndDate33;
   /**
    * 협력사 ISIR승인_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate33() {
      return planEndDate33;
   }
   /**
    * 협력사 ISIR승인_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate33(java.sql.Timestamp planEndDate33) throws wt.util.WTPropertyVetoException {
      planEndDate33Validate(planEndDate33);
      this.planEndDate33 = planEndDate33;
   }
   void planEndDate33Validate(java.sql.Timestamp planEndDate33) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 협력사 ISIR승인_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE33 = "execEndDate33";
   java.sql.Timestamp execEndDate33;
   /**
    * 협력사 ISIR승인_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate33() {
      return execEndDate33;
   }
   /**
    * 협력사 ISIR승인_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate33(java.sql.Timestamp execEndDate33) throws wt.util.WTPropertyVetoException {
      execEndDate33Validate(execEndDate33);
      this.execEndDate33 = execEndDate33;
   }
   void execEndDate33Validate(java.sql.Timestamp execEndDate33) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Full-Tool 점검_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE34 = "planEndDate34";
   java.sql.Timestamp planEndDate34;
   /**
    * Full-Tool 점검_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate34() {
      return planEndDate34;
   }
   /**
    * Full-Tool 점검_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate34(java.sql.Timestamp planEndDate34) throws wt.util.WTPropertyVetoException {
      planEndDate34Validate(planEndDate34);
      this.planEndDate34 = planEndDate34;
   }
   void planEndDate34Validate(java.sql.Timestamp planEndDate34) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Full-Tool 점검_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE34 = "execEndDate34";
   java.sql.Timestamp execEndDate34;
   /**
    * Full-Tool 점검_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate34() {
      return execEndDate34;
   }
   /**
    * Full-Tool 점검_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate34(java.sql.Timestamp execEndDate34) throws wt.util.WTPropertyVetoException {
      execEndDate34Validate(execEndDate34);
      this.execEndDate34 = execEndDate34;
   }
   void execEndDate34Validate(java.sql.Timestamp execEndDate34) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE4_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE35 = "planEndDate35";
   java.sql.Timestamp planEndDate35;
   /**
    * GATE4_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate35() {
      return planEndDate35;
   }
   /**
    * GATE4_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate35(java.sql.Timestamp planEndDate35) throws wt.util.WTPropertyVetoException {
      planEndDate35Validate(planEndDate35);
      this.planEndDate35 = planEndDate35;
   }
   void planEndDate35Validate(java.sql.Timestamp planEndDate35) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE4_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE35 = "execEndDate35";
   java.sql.Timestamp execEndDate35;
   /**
    * GATE4_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate35() {
      return execEndDate35;
   }
   /**
    * GATE4_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate35(java.sql.Timestamp execEndDate35) throws wt.util.WTPropertyVetoException {
      execEndDate35Validate(execEndDate35);
      this.execEndDate35 = execEndDate35;
   }
   void execEndDate35Validate(java.sql.Timestamp execEndDate35) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P2납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE36 = "planEndDate36";
   java.sql.Timestamp planEndDate36;
   /**
    * P2납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate36() {
      return planEndDate36;
   }
   /**
    * P2납품_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate36(java.sql.Timestamp planEndDate36) throws wt.util.WTPropertyVetoException {
      planEndDate36Validate(planEndDate36);
      this.planEndDate36 = planEndDate36;
   }
   void planEndDate36Validate(java.sql.Timestamp planEndDate36) throws wt.util.WTPropertyVetoException {
   }

   /**
    * P2납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE36 = "execEndDate36";
   java.sql.Timestamp execEndDate36;
   /**
    * P2납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate36() {
      return execEndDate36;
   }
   /**
    * P2납품_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate36(java.sql.Timestamp execEndDate36) throws wt.util.WTPropertyVetoException {
      execEndDate36Validate(execEndDate36);
      this.execEndDate36 = execEndDate36;
   }
   void execEndDate36Validate(java.sql.Timestamp execEndDate36) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ISIR/PPAP제출_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE37 = "planEndDate37";
   java.sql.Timestamp planEndDate37;
   /**
    * ISIR/PPAP제출_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate37() {
      return planEndDate37;
   }
   /**
    * ISIR/PPAP제출_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate37(java.sql.Timestamp planEndDate37) throws wt.util.WTPropertyVetoException {
      planEndDate37Validate(planEndDate37);
      this.planEndDate37 = planEndDate37;
   }
   void planEndDate37Validate(java.sql.Timestamp planEndDate37) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ISIR/PPAP제출_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE37 = "execEndDate37";
   java.sql.Timestamp execEndDate37;
   /**
    * ISIR/PPAP제출_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate37() {
      return execEndDate37;
   }
   /**
    * ISIR/PPAP제출_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate37(java.sql.Timestamp execEndDate37) throws wt.util.WTPropertyVetoException {
      execEndDate37Validate(execEndDate37);
      this.execEndDate37 = execEndDate37;
   }
   void execEndDate37Validate(java.sql.Timestamp execEndDate37) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산이관(금형)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE38 = "planEndDate38";
   java.sql.Timestamp planEndDate38;
   /**
    * 양산이관(금형)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate38() {
      return planEndDate38;
   }
   /**
    * 양산이관(금형)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate38(java.sql.Timestamp planEndDate38) throws wt.util.WTPropertyVetoException {
      planEndDate38Validate(planEndDate38);
      this.planEndDate38 = planEndDate38;
   }
   void planEndDate38Validate(java.sql.Timestamp planEndDate38) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산이관(금형)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE38 = "execEndDate38";
   java.sql.Timestamp execEndDate38;
   /**
    * 양산이관(금형)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate38() {
      return execEndDate38;
   }
   /**
    * 양산이관(금형)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate38(java.sql.Timestamp execEndDate38) throws wt.util.WTPropertyVetoException {
      execEndDate38Validate(execEndDate38);
      this.execEndDate38 = execEndDate38;
   }
   void execEndDate38Validate(java.sql.Timestamp execEndDate38) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산이관(설비)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE39 = "planEndDate39";
   java.sql.Timestamp planEndDate39;
   /**
    * 양산이관(설비)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate39() {
      return planEndDate39;
   }
   /**
    * 양산이관(설비)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate39(java.sql.Timestamp planEndDate39) throws wt.util.WTPropertyVetoException {
      planEndDate39Validate(planEndDate39);
      this.planEndDate39 = planEndDate39;
   }
   void planEndDate39Validate(java.sql.Timestamp planEndDate39) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산이관(설비)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE39 = "execEndDate39";
   java.sql.Timestamp execEndDate39;
   /**
    * 양산이관(설비)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate39() {
      return execEndDate39;
   }
   /**
    * 양산이관(설비)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate39(java.sql.Timestamp execEndDate39) throws wt.util.WTPropertyVetoException {
      execEndDate39Validate(execEndDate39);
      this.execEndDate39 = execEndDate39;
   }
   void execEndDate39Validate(java.sql.Timestamp execEndDate39) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산이관_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE40 = "planEndDate40";
   java.sql.Timestamp planEndDate40;
   /**
    * 양산이관_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate40() {
      return planEndDate40;
   }
   /**
    * 양산이관_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate40(java.sql.Timestamp planEndDate40) throws wt.util.WTPropertyVetoException {
      planEndDate40Validate(planEndDate40);
      this.planEndDate40 = planEndDate40;
   }
   void planEndDate40Validate(java.sql.Timestamp planEndDate40) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산이관_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE40 = "execEndDate40";
   java.sql.Timestamp execEndDate40;
   /**
    * 양산이관_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate40() {
      return execEndDate40;
   }
   /**
    * 양산이관_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate40(java.sql.Timestamp execEndDate40) throws wt.util.WTPropertyVetoException {
      execEndDate40Validate(execEndDate40);
      this.execEndDate40 = execEndDate40;
   }
   void execEndDate40Validate(java.sql.Timestamp execEndDate40) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도양산_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE41 = "planEndDate41";
   java.sql.Timestamp planEndDate41;
   /**
    * 초도양산_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate41() {
      return planEndDate41;
   }
   /**
    * 초도양산_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate41(java.sql.Timestamp planEndDate41) throws wt.util.WTPropertyVetoException {
      planEndDate41Validate(planEndDate41);
      this.planEndDate41 = planEndDate41;
   }
   void planEndDate41Validate(java.sql.Timestamp planEndDate41) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 초도양산_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE41 = "execEndDate41";
   java.sql.Timestamp execEndDate41;
   /**
    * 초도양산_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate41() {
      return execEndDate41;
   }
   /**
    * 초도양산_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate41(java.sql.Timestamp execEndDate41) throws wt.util.WTPropertyVetoException {
      execEndDate41Validate(execEndDate41);
      this.execEndDate41 = execEndDate41;
   }
   void execEndDate41Validate(java.sql.Timestamp execEndDate41) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산최종평가(DR6)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE42 = "planEndDate42";
   java.sql.Timestamp planEndDate42;
   /**
    * 양산최종평가(DR6)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate42() {
      return planEndDate42;
   }
   /**
    * 양산최종평가(DR6)_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate42(java.sql.Timestamp planEndDate42) throws wt.util.WTPropertyVetoException {
      planEndDate42Validate(planEndDate42);
      this.planEndDate42 = planEndDate42;
   }
   void planEndDate42Validate(java.sql.Timestamp planEndDate42) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 양산최종평가(DR6)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE42 = "execEndDate42";
   java.sql.Timestamp execEndDate42;
   /**
    * 양산최종평가(DR6)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate42() {
      return execEndDate42;
   }
   /**
    * 양산최종평가(DR6)_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate42(java.sql.Timestamp execEndDate42) throws wt.util.WTPropertyVetoException {
      execEndDate42Validate(execEndDate42);
      this.execEndDate42 = execEndDate42;
   }
   void execEndDate42Validate(java.sql.Timestamp execEndDate42) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE5_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE43 = "planEndDate43";
   java.sql.Timestamp planEndDate43;
   /**
    * GATE5_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate43() {
      return planEndDate43;
   }
   /**
    * GATE5_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate43(java.sql.Timestamp planEndDate43) throws wt.util.WTPropertyVetoException {
      planEndDate43Validate(planEndDate43);
      this.planEndDate43 = planEndDate43;
   }
   void planEndDate43Validate(java.sql.Timestamp planEndDate43) throws wt.util.WTPropertyVetoException {
   }

   /**
    * GATE5_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE43 = "execEndDate43";
   java.sql.Timestamp execEndDate43;
   /**
    * GATE5_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate43() {
      return execEndDate43;
   }
   /**
    * GATE5_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate43(java.sql.Timestamp execEndDate43) throws wt.util.WTPropertyVetoException {
      execEndDate43Validate(execEndDate43);
      this.execEndDate43 = execEndDate43;
   }
   void execEndDate43Validate(java.sql.Timestamp execEndDate43) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트완료_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE44 = "planEndDate44";
   java.sql.Timestamp planEndDate44;
   /**
    * 프로젝트완료_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate44() {
      return planEndDate44;
   }
   /**
    * 프로젝트완료_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate44(java.sql.Timestamp planEndDate44) throws wt.util.WTPropertyVetoException {
      planEndDate44Validate(planEndDate44);
      this.planEndDate44 = planEndDate44;
   }
   void planEndDate44Validate(java.sql.Timestamp planEndDate44) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트완료_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE44 = "execEndDate44";
   java.sql.Timestamp execEndDate44;
   /**
    * 프로젝트완료_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate44() {
      return execEndDate44;
   }
   /**
    * 프로젝트완료_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate44(java.sql.Timestamp execEndDate44) throws wt.util.WTPropertyVetoException {
      execEndDate44Validate(execEndDate44);
      this.execEndDate44 = execEndDate44;
   }
   void execEndDate44Validate(java.sql.Timestamp execEndDate44) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Tool제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String PLAN_END_DATE46 = "planEndDate46";
   java.sql.Timestamp planEndDate46;
   /**
    * Tool제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getPlanEndDate46() {
      return planEndDate46;
   }
   /**
    * Tool제작_계획완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setPlanEndDate46(java.sql.Timestamp planEndDate46) throws wt.util.WTPropertyVetoException {
      planEndDate46Validate(planEndDate46);
      this.planEndDate46 = planEndDate46;
   }
   void planEndDate46Validate(java.sql.Timestamp planEndDate46) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Tool제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public static final java.lang.String EXEC_END_DATE46 = "execEndDate46";
   java.sql.Timestamp execEndDate46;
   /**
    * Tool제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public java.sql.Timestamp getExecEndDate46() {
      return execEndDate46;
   }
   /**
    * Tool제작_실제완료일
    *
    * @see e3ps.project.ProductProject
    */
   public void setExecEndDate46(java.sql.Timestamp execEndDate46) throws wt.util.WTPropertyVetoException {
      execEndDate46Validate(execEndDate46);
      this.execEndDate46 = execEndDate46;
   }
   void execEndDate46Validate(java.sql.Timestamp execEndDate46) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 4513440677052901644L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( assembly );
      output.writeObject( costsDate );
      output.writeObject( execEndDate1 );
      output.writeObject( execEndDate10 );
      output.writeObject( execEndDate11 );
      output.writeObject( execEndDate12 );
      output.writeObject( execEndDate13 );
      output.writeObject( execEndDate14 );
      output.writeObject( execEndDate15 );
      output.writeObject( execEndDate16 );
      output.writeObject( execEndDate17 );
      output.writeObject( execEndDate18 );
      output.writeObject( execEndDate19 );
      output.writeObject( execEndDate2 );
      output.writeObject( execEndDate20 );
      output.writeObject( execEndDate21 );
      output.writeObject( execEndDate22 );
      output.writeObject( execEndDate23 );
      output.writeObject( execEndDate24 );
      output.writeObject( execEndDate25 );
      output.writeObject( execEndDate26 );
      output.writeObject( execEndDate27 );
      output.writeObject( execEndDate28 );
      output.writeObject( execEndDate29 );
      output.writeObject( execEndDate3 );
      output.writeObject( execEndDate30 );
      output.writeObject( execEndDate31 );
      output.writeObject( execEndDate32 );
      output.writeObject( execEndDate33 );
      output.writeObject( execEndDate34 );
      output.writeObject( execEndDate35 );
      output.writeObject( execEndDate36 );
      output.writeObject( execEndDate37 );
      output.writeObject( execEndDate38 );
      output.writeObject( execEndDate39 );
      output.writeObject( execEndDate4 );
      output.writeObject( execEndDate40 );
      output.writeObject( execEndDate41 );
      output.writeObject( execEndDate42 );
      output.writeObject( execEndDate43 );
      output.writeObject( execEndDate44 );
      output.writeObject( execEndDate45 );
      output.writeObject( execEndDate46 );
      output.writeObject( execEndDate5 );
      output.writeObject( execEndDate6 );
      output.writeObject( execEndDate7 );
      output.writeObject( execEndDate8 );
      output.writeObject( execEndDate9 );
      output.writeBoolean( isPM );
      output.writeObject( itDivision );
      output.writeObject( model );
      output.writeObject( partNo );
      output.writeObject( partnerNo );
      output.writeObject( planEndDate1 );
      output.writeObject( planEndDate10 );
      output.writeObject( planEndDate11 );
      output.writeObject( planEndDate12 );
      output.writeObject( planEndDate13 );
      output.writeObject( planEndDate14 );
      output.writeObject( planEndDate15 );
      output.writeObject( planEndDate16 );
      output.writeObject( planEndDate17 );
      output.writeObject( planEndDate18 );
      output.writeObject( planEndDate19 );
      output.writeObject( planEndDate2 );
      output.writeObject( planEndDate20 );
      output.writeObject( planEndDate21 );
      output.writeObject( planEndDate22 );
      output.writeObject( planEndDate23 );
      output.writeObject( planEndDate24 );
      output.writeObject( planEndDate25 );
      output.writeObject( planEndDate26 );
      output.writeObject( planEndDate27 );
      output.writeObject( planEndDate28 );
      output.writeObject( planEndDate29 );
      output.writeObject( planEndDate3 );
      output.writeObject( planEndDate30 );
      output.writeObject( planEndDate31 );
      output.writeObject( planEndDate32 );
      output.writeObject( planEndDate33 );
      output.writeObject( planEndDate34 );
      output.writeObject( planEndDate35 );
      output.writeObject( planEndDate36 );
      output.writeObject( planEndDate37 );
      output.writeObject( planEndDate38 );
      output.writeObject( planEndDate39 );
      output.writeObject( planEndDate4 );
      output.writeObject( planEndDate40 );
      output.writeObject( planEndDate41 );
      output.writeObject( planEndDate42 );
      output.writeObject( planEndDate43 );
      output.writeObject( planEndDate44 );
      output.writeObject( planEndDate45 );
      output.writeObject( planEndDate46 );
      output.writeObject( planEndDate5 );
      output.writeObject( planEndDate6 );
      output.writeObject( planEndDate7 );
      output.writeObject( planEndDate8 );
      output.writeObject( planEndDate9 );
      output.writeObject( teamType );
   }

   protected void super_writeExternal_ProductProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProductProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ProductProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "assembly", assembly );
      output.setTimestamp( "costsDate", costsDate );
      output.setTimestamp( "execEndDate1", execEndDate1 );
      output.setTimestamp( "execEndDate10", execEndDate10 );
      output.setTimestamp( "execEndDate11", execEndDate11 );
      output.setTimestamp( "execEndDate12", execEndDate12 );
      output.setTimestamp( "execEndDate13", execEndDate13 );
      output.setTimestamp( "execEndDate14", execEndDate14 );
      output.setTimestamp( "execEndDate15", execEndDate15 );
      output.setTimestamp( "execEndDate16", execEndDate16 );
      output.setTimestamp( "execEndDate17", execEndDate17 );
      output.setTimestamp( "execEndDate18", execEndDate18 );
      output.setTimestamp( "execEndDate19", execEndDate19 );
      output.setTimestamp( "execEndDate2", execEndDate2 );
      output.setTimestamp( "execEndDate20", execEndDate20 );
      output.setTimestamp( "execEndDate21", execEndDate21 );
      output.setTimestamp( "execEndDate22", execEndDate22 );
      output.setTimestamp( "execEndDate23", execEndDate23 );
      output.setTimestamp( "execEndDate24", execEndDate24 );
      output.setTimestamp( "execEndDate25", execEndDate25 );
      output.setTimestamp( "execEndDate26", execEndDate26 );
      output.setTimestamp( "execEndDate27", execEndDate27 );
      output.setTimestamp( "execEndDate28", execEndDate28 );
      output.setTimestamp( "execEndDate29", execEndDate29 );
      output.setTimestamp( "execEndDate3", execEndDate3 );
      output.setTimestamp( "execEndDate30", execEndDate30 );
      output.setTimestamp( "execEndDate31", execEndDate31 );
      output.setTimestamp( "execEndDate32", execEndDate32 );
      output.setTimestamp( "execEndDate33", execEndDate33 );
      output.setTimestamp( "execEndDate34", execEndDate34 );
      output.setTimestamp( "execEndDate35", execEndDate35 );
      output.setTimestamp( "execEndDate36", execEndDate36 );
      output.setTimestamp( "execEndDate37", execEndDate37 );
      output.setTimestamp( "execEndDate38", execEndDate38 );
      output.setTimestamp( "execEndDate39", execEndDate39 );
      output.setTimestamp( "execEndDate4", execEndDate4 );
      output.setTimestamp( "execEndDate40", execEndDate40 );
      output.setTimestamp( "execEndDate41", execEndDate41 );
      output.setTimestamp( "execEndDate42", execEndDate42 );
      output.setTimestamp( "execEndDate43", execEndDate43 );
      output.setTimestamp( "execEndDate44", execEndDate44 );
      output.setTimestamp( "execEndDate45", execEndDate45 );
      output.setTimestamp( "execEndDate46", execEndDate46 );
      output.setTimestamp( "execEndDate5", execEndDate5 );
      output.setTimestamp( "execEndDate6", execEndDate6 );
      output.setTimestamp( "execEndDate7", execEndDate7 );
      output.setTimestamp( "execEndDate8", execEndDate8 );
      output.setTimestamp( "execEndDate9", execEndDate9 );
      output.setBoolean( "isPM", isPM );
      output.setString( "itDivision", itDivision );
      output.setString( "model", model );
      output.setString( "partNo", partNo );
      output.setString( "partnerNo", partnerNo );
      output.setTimestamp( "planEndDate1", planEndDate1 );
      output.setTimestamp( "planEndDate10", planEndDate10 );
      output.setTimestamp( "planEndDate11", planEndDate11 );
      output.setTimestamp( "planEndDate12", planEndDate12 );
      output.setTimestamp( "planEndDate13", planEndDate13 );
      output.setTimestamp( "planEndDate14", planEndDate14 );
      output.setTimestamp( "planEndDate15", planEndDate15 );
      output.setTimestamp( "planEndDate16", planEndDate16 );
      output.setTimestamp( "planEndDate17", planEndDate17 );
      output.setTimestamp( "planEndDate18", planEndDate18 );
      output.setTimestamp( "planEndDate19", planEndDate19 );
      output.setTimestamp( "planEndDate2", planEndDate2 );
      output.setTimestamp( "planEndDate20", planEndDate20 );
      output.setTimestamp( "planEndDate21", planEndDate21 );
      output.setTimestamp( "planEndDate22", planEndDate22 );
      output.setTimestamp( "planEndDate23", planEndDate23 );
      output.setTimestamp( "planEndDate24", planEndDate24 );
      output.setTimestamp( "planEndDate25", planEndDate25 );
      output.setTimestamp( "planEndDate26", planEndDate26 );
      output.setTimestamp( "planEndDate27", planEndDate27 );
      output.setTimestamp( "planEndDate28", planEndDate28 );
      output.setTimestamp( "planEndDate29", planEndDate29 );
      output.setTimestamp( "planEndDate3", planEndDate3 );
      output.setTimestamp( "planEndDate30", planEndDate30 );
      output.setTimestamp( "planEndDate31", planEndDate31 );
      output.setTimestamp( "planEndDate32", planEndDate32 );
      output.setTimestamp( "planEndDate33", planEndDate33 );
      output.setTimestamp( "planEndDate34", planEndDate34 );
      output.setTimestamp( "planEndDate35", planEndDate35 );
      output.setTimestamp( "planEndDate36", planEndDate36 );
      output.setTimestamp( "planEndDate37", planEndDate37 );
      output.setTimestamp( "planEndDate38", planEndDate38 );
      output.setTimestamp( "planEndDate39", planEndDate39 );
      output.setTimestamp( "planEndDate4", planEndDate4 );
      output.setTimestamp( "planEndDate40", planEndDate40 );
      output.setTimestamp( "planEndDate41", planEndDate41 );
      output.setTimestamp( "planEndDate42", planEndDate42 );
      output.setTimestamp( "planEndDate43", planEndDate43 );
      output.setTimestamp( "planEndDate44", planEndDate44 );
      output.setTimestamp( "planEndDate45", planEndDate45 );
      output.setTimestamp( "planEndDate46", planEndDate46 );
      output.setTimestamp( "planEndDate5", planEndDate5 );
      output.setTimestamp( "planEndDate6", planEndDate6 );
      output.setTimestamp( "planEndDate7", planEndDate7 );
      output.setTimestamp( "planEndDate8", planEndDate8 );
      output.setTimestamp( "planEndDate9", planEndDate9 );
      output.setString( "teamType", teamType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      assembly = input.getString( "assembly" );
      costsDate = input.getTimestamp( "costsDate" );
      execEndDate1 = input.getTimestamp( "execEndDate1" );
      execEndDate10 = input.getTimestamp( "execEndDate10" );
      execEndDate11 = input.getTimestamp( "execEndDate11" );
      execEndDate12 = input.getTimestamp( "execEndDate12" );
      execEndDate13 = input.getTimestamp( "execEndDate13" );
      execEndDate14 = input.getTimestamp( "execEndDate14" );
      execEndDate15 = input.getTimestamp( "execEndDate15" );
      execEndDate16 = input.getTimestamp( "execEndDate16" );
      execEndDate17 = input.getTimestamp( "execEndDate17" );
      execEndDate18 = input.getTimestamp( "execEndDate18" );
      execEndDate19 = input.getTimestamp( "execEndDate19" );
      execEndDate2 = input.getTimestamp( "execEndDate2" );
      execEndDate20 = input.getTimestamp( "execEndDate20" );
      execEndDate21 = input.getTimestamp( "execEndDate21" );
      execEndDate22 = input.getTimestamp( "execEndDate22" );
      execEndDate23 = input.getTimestamp( "execEndDate23" );
      execEndDate24 = input.getTimestamp( "execEndDate24" );
      execEndDate25 = input.getTimestamp( "execEndDate25" );
      execEndDate26 = input.getTimestamp( "execEndDate26" );
      execEndDate27 = input.getTimestamp( "execEndDate27" );
      execEndDate28 = input.getTimestamp( "execEndDate28" );
      execEndDate29 = input.getTimestamp( "execEndDate29" );
      execEndDate3 = input.getTimestamp( "execEndDate3" );
      execEndDate30 = input.getTimestamp( "execEndDate30" );
      execEndDate31 = input.getTimestamp( "execEndDate31" );
      execEndDate32 = input.getTimestamp( "execEndDate32" );
      execEndDate33 = input.getTimestamp( "execEndDate33" );
      execEndDate34 = input.getTimestamp( "execEndDate34" );
      execEndDate35 = input.getTimestamp( "execEndDate35" );
      execEndDate36 = input.getTimestamp( "execEndDate36" );
      execEndDate37 = input.getTimestamp( "execEndDate37" );
      execEndDate38 = input.getTimestamp( "execEndDate38" );
      execEndDate39 = input.getTimestamp( "execEndDate39" );
      execEndDate4 = input.getTimestamp( "execEndDate4" );
      execEndDate40 = input.getTimestamp( "execEndDate40" );
      execEndDate41 = input.getTimestamp( "execEndDate41" );
      execEndDate42 = input.getTimestamp( "execEndDate42" );
      execEndDate43 = input.getTimestamp( "execEndDate43" );
      execEndDate44 = input.getTimestamp( "execEndDate44" );
      execEndDate45 = input.getTimestamp( "execEndDate45" );
      execEndDate46 = input.getTimestamp( "execEndDate46" );
      execEndDate5 = input.getTimestamp( "execEndDate5" );
      execEndDate6 = input.getTimestamp( "execEndDate6" );
      execEndDate7 = input.getTimestamp( "execEndDate7" );
      execEndDate8 = input.getTimestamp( "execEndDate8" );
      execEndDate9 = input.getTimestamp( "execEndDate9" );
      isPM = input.getBoolean( "isPM" );
      itDivision = input.getString( "itDivision" );
      model = input.getString( "model" );
      partNo = input.getString( "partNo" );
      partnerNo = input.getString( "partnerNo" );
      planEndDate1 = input.getTimestamp( "planEndDate1" );
      planEndDate10 = input.getTimestamp( "planEndDate10" );
      planEndDate11 = input.getTimestamp( "planEndDate11" );
      planEndDate12 = input.getTimestamp( "planEndDate12" );
      planEndDate13 = input.getTimestamp( "planEndDate13" );
      planEndDate14 = input.getTimestamp( "planEndDate14" );
      planEndDate15 = input.getTimestamp( "planEndDate15" );
      planEndDate16 = input.getTimestamp( "planEndDate16" );
      planEndDate17 = input.getTimestamp( "planEndDate17" );
      planEndDate18 = input.getTimestamp( "planEndDate18" );
      planEndDate19 = input.getTimestamp( "planEndDate19" );
      planEndDate2 = input.getTimestamp( "planEndDate2" );
      planEndDate20 = input.getTimestamp( "planEndDate20" );
      planEndDate21 = input.getTimestamp( "planEndDate21" );
      planEndDate22 = input.getTimestamp( "planEndDate22" );
      planEndDate23 = input.getTimestamp( "planEndDate23" );
      planEndDate24 = input.getTimestamp( "planEndDate24" );
      planEndDate25 = input.getTimestamp( "planEndDate25" );
      planEndDate26 = input.getTimestamp( "planEndDate26" );
      planEndDate27 = input.getTimestamp( "planEndDate27" );
      planEndDate28 = input.getTimestamp( "planEndDate28" );
      planEndDate29 = input.getTimestamp( "planEndDate29" );
      planEndDate3 = input.getTimestamp( "planEndDate3" );
      planEndDate30 = input.getTimestamp( "planEndDate30" );
      planEndDate31 = input.getTimestamp( "planEndDate31" );
      planEndDate32 = input.getTimestamp( "planEndDate32" );
      planEndDate33 = input.getTimestamp( "planEndDate33" );
      planEndDate34 = input.getTimestamp( "planEndDate34" );
      planEndDate35 = input.getTimestamp( "planEndDate35" );
      planEndDate36 = input.getTimestamp( "planEndDate36" );
      planEndDate37 = input.getTimestamp( "planEndDate37" );
      planEndDate38 = input.getTimestamp( "planEndDate38" );
      planEndDate39 = input.getTimestamp( "planEndDate39" );
      planEndDate4 = input.getTimestamp( "planEndDate4" );
      planEndDate40 = input.getTimestamp( "planEndDate40" );
      planEndDate41 = input.getTimestamp( "planEndDate41" );
      planEndDate42 = input.getTimestamp( "planEndDate42" );
      planEndDate43 = input.getTimestamp( "planEndDate43" );
      planEndDate44 = input.getTimestamp( "planEndDate44" );
      planEndDate45 = input.getTimestamp( "planEndDate45" );
      planEndDate46 = input.getTimestamp( "planEndDate46" );
      planEndDate5 = input.getTimestamp( "planEndDate5" );
      planEndDate6 = input.getTimestamp( "planEndDate6" );
      planEndDate7 = input.getTimestamp( "planEndDate7" );
      planEndDate8 = input.getTimestamp( "planEndDate8" );
      planEndDate9 = input.getTimestamp( "planEndDate9" );
      teamType = input.getString( "teamType" );
   }

   boolean readVersion4513440677052901644L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      assembly = (java.lang.String) input.readObject();
      costsDate = (java.sql.Timestamp) input.readObject();
      execEndDate1 = (java.sql.Timestamp) input.readObject();
      execEndDate10 = (java.sql.Timestamp) input.readObject();
      execEndDate11 = (java.sql.Timestamp) input.readObject();
      execEndDate12 = (java.sql.Timestamp) input.readObject();
      execEndDate13 = (java.sql.Timestamp) input.readObject();
      execEndDate14 = (java.sql.Timestamp) input.readObject();
      execEndDate15 = (java.sql.Timestamp) input.readObject();
      execEndDate16 = (java.sql.Timestamp) input.readObject();
      execEndDate17 = (java.sql.Timestamp) input.readObject();
      execEndDate18 = (java.sql.Timestamp) input.readObject();
      execEndDate19 = (java.sql.Timestamp) input.readObject();
      execEndDate2 = (java.sql.Timestamp) input.readObject();
      execEndDate20 = (java.sql.Timestamp) input.readObject();
      execEndDate21 = (java.sql.Timestamp) input.readObject();
      execEndDate22 = (java.sql.Timestamp) input.readObject();
      execEndDate23 = (java.sql.Timestamp) input.readObject();
      execEndDate24 = (java.sql.Timestamp) input.readObject();
      execEndDate25 = (java.sql.Timestamp) input.readObject();
      execEndDate26 = (java.sql.Timestamp) input.readObject();
      execEndDate27 = (java.sql.Timestamp) input.readObject();
      execEndDate28 = (java.sql.Timestamp) input.readObject();
      execEndDate29 = (java.sql.Timestamp) input.readObject();
      execEndDate3 = (java.sql.Timestamp) input.readObject();
      execEndDate30 = (java.sql.Timestamp) input.readObject();
      execEndDate31 = (java.sql.Timestamp) input.readObject();
      execEndDate32 = (java.sql.Timestamp) input.readObject();
      execEndDate33 = (java.sql.Timestamp) input.readObject();
      execEndDate34 = (java.sql.Timestamp) input.readObject();
      execEndDate35 = (java.sql.Timestamp) input.readObject();
      execEndDate36 = (java.sql.Timestamp) input.readObject();
      execEndDate37 = (java.sql.Timestamp) input.readObject();
      execEndDate38 = (java.sql.Timestamp) input.readObject();
      execEndDate39 = (java.sql.Timestamp) input.readObject();
      execEndDate4 = (java.sql.Timestamp) input.readObject();
      execEndDate40 = (java.sql.Timestamp) input.readObject();
      execEndDate41 = (java.sql.Timestamp) input.readObject();
      execEndDate42 = (java.sql.Timestamp) input.readObject();
      execEndDate43 = (java.sql.Timestamp) input.readObject();
      execEndDate44 = (java.sql.Timestamp) input.readObject();
      execEndDate45 = (java.sql.Timestamp) input.readObject();
      execEndDate46 = (java.sql.Timestamp) input.readObject();
      execEndDate5 = (java.sql.Timestamp) input.readObject();
      execEndDate6 = (java.sql.Timestamp) input.readObject();
      execEndDate7 = (java.sql.Timestamp) input.readObject();
      execEndDate8 = (java.sql.Timestamp) input.readObject();
      execEndDate9 = (java.sql.Timestamp) input.readObject();
      isPM = input.readBoolean();
      itDivision = (java.lang.String) input.readObject();
      model = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      partnerNo = (java.lang.String) input.readObject();
      planEndDate1 = (java.sql.Timestamp) input.readObject();
      planEndDate10 = (java.sql.Timestamp) input.readObject();
      planEndDate11 = (java.sql.Timestamp) input.readObject();
      planEndDate12 = (java.sql.Timestamp) input.readObject();
      planEndDate13 = (java.sql.Timestamp) input.readObject();
      planEndDate14 = (java.sql.Timestamp) input.readObject();
      planEndDate15 = (java.sql.Timestamp) input.readObject();
      planEndDate16 = (java.sql.Timestamp) input.readObject();
      planEndDate17 = (java.sql.Timestamp) input.readObject();
      planEndDate18 = (java.sql.Timestamp) input.readObject();
      planEndDate19 = (java.sql.Timestamp) input.readObject();
      planEndDate2 = (java.sql.Timestamp) input.readObject();
      planEndDate20 = (java.sql.Timestamp) input.readObject();
      planEndDate21 = (java.sql.Timestamp) input.readObject();
      planEndDate22 = (java.sql.Timestamp) input.readObject();
      planEndDate23 = (java.sql.Timestamp) input.readObject();
      planEndDate24 = (java.sql.Timestamp) input.readObject();
      planEndDate25 = (java.sql.Timestamp) input.readObject();
      planEndDate26 = (java.sql.Timestamp) input.readObject();
      planEndDate27 = (java.sql.Timestamp) input.readObject();
      planEndDate28 = (java.sql.Timestamp) input.readObject();
      planEndDate29 = (java.sql.Timestamp) input.readObject();
      planEndDate3 = (java.sql.Timestamp) input.readObject();
      planEndDate30 = (java.sql.Timestamp) input.readObject();
      planEndDate31 = (java.sql.Timestamp) input.readObject();
      planEndDate32 = (java.sql.Timestamp) input.readObject();
      planEndDate33 = (java.sql.Timestamp) input.readObject();
      planEndDate34 = (java.sql.Timestamp) input.readObject();
      planEndDate35 = (java.sql.Timestamp) input.readObject();
      planEndDate36 = (java.sql.Timestamp) input.readObject();
      planEndDate37 = (java.sql.Timestamp) input.readObject();
      planEndDate38 = (java.sql.Timestamp) input.readObject();
      planEndDate39 = (java.sql.Timestamp) input.readObject();
      planEndDate4 = (java.sql.Timestamp) input.readObject();
      planEndDate40 = (java.sql.Timestamp) input.readObject();
      planEndDate41 = (java.sql.Timestamp) input.readObject();
      planEndDate42 = (java.sql.Timestamp) input.readObject();
      planEndDate43 = (java.sql.Timestamp) input.readObject();
      planEndDate44 = (java.sql.Timestamp) input.readObject();
      planEndDate45 = (java.sql.Timestamp) input.readObject();
      planEndDate46 = (java.sql.Timestamp) input.readObject();
      planEndDate5 = (java.sql.Timestamp) input.readObject();
      planEndDate6 = (java.sql.Timestamp) input.readObject();
      planEndDate7 = (java.sql.Timestamp) input.readObject();
      planEndDate8 = (java.sql.Timestamp) input.readObject();
      planEndDate9 = (java.sql.Timestamp) input.readObject();
      teamType = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ProductProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4513440677052901644L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ProductProject( _ProductProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
