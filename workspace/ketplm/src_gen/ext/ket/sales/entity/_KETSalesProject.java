package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesProject extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesProject.class.getName();

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String PROJECT_NO = "projectNo";
   static int PROJECT_NO_UPPER_LIMIT = -1;
   java.lang.String projectNo;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getProjectNo() {
      return projectNo;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setProjectNo(java.lang.String projectNo) throws wt.util.WTPropertyVetoException {
      projectNoValidate(projectNo);
      this.projectNo = projectNo;
   }
   void projectNoValidate(java.lang.String projectNo) throws wt.util.WTPropertyVetoException {
      if (PROJECT_NO_UPPER_LIMIT < 1) {
         try { PROJECT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_NO_UPPER_LIMIT = 200; }
      }
      if (projectNo != null && !wt.fc.PersistenceHelper.checkStoredLength(projectNo.toString(), PROJECT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectNo"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectNo", this.projectNo, projectNo));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String PROJECT_NAME = "projectName";
   static int PROJECT_NAME_UPPER_LIMIT = -1;
   java.lang.String projectName;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getProjectName() {
      return projectName;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setProjectName(java.lang.String projectName) throws wt.util.WTPropertyVetoException {
      projectNameValidate(projectName);
      this.projectName = projectName;
   }
   void projectNameValidate(java.lang.String projectName) throws wt.util.WTPropertyVetoException {
      if (PROJECT_NAME_UPPER_LIMIT < 1) {
         try { PROJECT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_NAME_UPPER_LIMIT = 200; }
      }
      if (projectName != null && !wt.fc.PersistenceHelper.checkStoredLength(projectName.toString(), PROJECT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectName"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectName", this.projectName, projectName));
   }

   /**
    * 적용부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String APPLY_AREA = "applyArea";
   static int APPLY_AREA_UPPER_LIMIT = -1;
   java.lang.String applyArea;
   /**
    * 적용부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getApplyArea() {
      return applyArea;
   }
   /**
    * 적용부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setApplyArea(java.lang.String applyArea) throws wt.util.WTPropertyVetoException {
      applyAreaValidate(applyArea);
      this.applyArea = applyArea;
   }
   void applyAreaValidate(java.lang.String applyArea) throws wt.util.WTPropertyVetoException {
      if (APPLY_AREA_UPPER_LIMIT < 1) {
         try { APPLY_AREA_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("applyArea").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPLY_AREA_UPPER_LIMIT = 200; }
      }
      if (applyArea != null && !wt.fc.PersistenceHelper.checkStoredLength(applyArea.toString(), APPLY_AREA_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "applyArea"), java.lang.String.valueOf(java.lang.Math.min(APPLY_AREA_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "applyArea", this.applyArea, applyArea));
   }

   /**
    * SOP
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String SOP = "sop";
   java.sql.Timestamp sop;
   /**
    * SOP
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.sql.Timestamp getSop() {
      return sop;
   }
   /**
    * SOP
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setSop(java.sql.Timestamp sop) throws wt.util.WTPropertyVetoException {
      sopValidate(sop);
      this.sop = sop;
   }
   void sopValidate(java.sql.Timestamp sop) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 투자비
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String INVEST_COST = "investCost";
   static int INVEST_COST_UPPER_LIMIT = -1;
   java.lang.String investCost;
   /**
    * 투자비
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getInvestCost() {
      return investCost;
   }
   /**
    * 투자비
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setInvestCost(java.lang.String investCost) throws wt.util.WTPropertyVetoException {
      investCostValidate(investCost);
      this.investCost = investCost;
   }
   void investCostValidate(java.lang.String investCost) throws wt.util.WTPropertyVetoException {
      if (INVEST_COST_UPPER_LIMIT < 1) {
         try { INVEST_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_COST_UPPER_LIMIT = 200; }
      }
      if (investCost != null && !wt.fc.PersistenceHelper.checkStoredLength(investCost.toString(), INVEST_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investCost"), java.lang.String.valueOf(java.lang.Math.min(INVEST_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investCost", this.investCost, investCost));
   }

   /**
    * 기획총대수
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String PLAN_TOTAL = "planTotal";
   static int PLAN_TOTAL_UPPER_LIMIT = -1;
   java.lang.String planTotal;
   /**
    * 기획총대수
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getPlanTotal() {
      return planTotal;
   }
   /**
    * 기획총대수
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setPlanTotal(java.lang.String planTotal) throws wt.util.WTPropertyVetoException {
      planTotalValidate(planTotal);
      this.planTotal = planTotal;
   }
   void planTotalValidate(java.lang.String planTotal) throws wt.util.WTPropertyVetoException {
      if (PLAN_TOTAL_UPPER_LIMIT < 1) {
         try { PLAN_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_TOTAL_UPPER_LIMIT = 200; }
      }
      if (planTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(planTotal.toString(), PLAN_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planTotal"), java.lang.String.valueOf(java.lang.Math.min(PLAN_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planTotal", this.planTotal, planTotal));
   }

   /**
    * 기획 년대수
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String PLAN_YEAR = "planYear";
   static int PLAN_YEAR_UPPER_LIMIT = -1;
   java.lang.String planYear;
   /**
    * 기획 년대수
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getPlanYear() {
      return planYear;
   }
   /**
    * 기획 년대수
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setPlanYear(java.lang.String planYear) throws wt.util.WTPropertyVetoException {
      planYearValidate(planYear);
      this.planYear = planYear;
   }
   void planYearValidate(java.lang.String planYear) throws wt.util.WTPropertyVetoException {
      if (PLAN_YEAR_UPPER_LIMIT < 1) {
         try { PLAN_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_YEAR_UPPER_LIMIT = 200; }
      }
      if (planYear != null && !wt.fc.PersistenceHelper.checkStoredLength(planYear.toString(), PLAN_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planYear"), java.lang.String.valueOf(java.lang.Math.min(PLAN_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planYear", this.planYear, planYear));
   }

   /**
    * 예상총매출
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String EXPECT_SALES_TOTAL = "expectSalesTotal";
   static int EXPECT_SALES_TOTAL_UPPER_LIMIT = -1;
   java.lang.String expectSalesTotal;
   /**
    * 예상총매출
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getExpectSalesTotal() {
      return expectSalesTotal;
   }
   /**
    * 예상총매출
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setExpectSalesTotal(java.lang.String expectSalesTotal) throws wt.util.WTPropertyVetoException {
      expectSalesTotalValidate(expectSalesTotal);
      this.expectSalesTotal = expectSalesTotal;
   }
   void expectSalesTotalValidate(java.lang.String expectSalesTotal) throws wt.util.WTPropertyVetoException {
      if (EXPECT_SALES_TOTAL_UPPER_LIMIT < 1) {
         try { EXPECT_SALES_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expectSalesTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPECT_SALES_TOTAL_UPPER_LIMIT = 200; }
      }
      if (expectSalesTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(expectSalesTotal.toString(), EXPECT_SALES_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expectSalesTotal"), java.lang.String.valueOf(java.lang.Math.min(EXPECT_SALES_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expectSalesTotal", this.expectSalesTotal, expectSalesTotal));
   }

   /**
    * 예상년매출
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String EXPECT_SALES_YEAR = "expectSalesYear";
   static int EXPECT_SALES_YEAR_UPPER_LIMIT = -1;
   java.lang.String expectSalesYear;
   /**
    * 예상년매출
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getExpectSalesYear() {
      return expectSalesYear;
   }
   /**
    * 예상년매출
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setExpectSalesYear(java.lang.String expectSalesYear) throws wt.util.WTPropertyVetoException {
      expectSalesYearValidate(expectSalesYear);
      this.expectSalesYear = expectSalesYear;
   }
   void expectSalesYearValidate(java.lang.String expectSalesYear) throws wt.util.WTPropertyVetoException {
      if (EXPECT_SALES_YEAR_UPPER_LIMIT < 1) {
         try { EXPECT_SALES_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expectSalesYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPECT_SALES_YEAR_UPPER_LIMIT = 200; }
      }
      if (expectSalesYear != null && !wt.fc.PersistenceHelper.checkStoredLength(expectSalesYear.toString(), EXPECT_SALES_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expectSalesYear"), java.lang.String.valueOf(java.lang.Math.min(EXPECT_SALES_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expectSalesYear", this.expectSalesYear, expectSalesYear));
   }

   /**
    * 프로젝트 결과
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String PROJECT_RESULT = "projectResult";
   static int PROJECT_RESULT_UPPER_LIMIT = -1;
   java.lang.String projectResult;
   /**
    * 프로젝트 결과
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getProjectResult() {
      return projectResult;
   }
   /**
    * 프로젝트 결과
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setProjectResult(java.lang.String projectResult) throws wt.util.WTPropertyVetoException {
      projectResultValidate(projectResult);
      this.projectResult = projectResult;
   }
   void projectResultValidate(java.lang.String projectResult) throws wt.util.WTPropertyVetoException {
      if (PROJECT_RESULT_UPPER_LIMIT < 1) {
         try { PROJECT_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_RESULT_UPPER_LIMIT = 4000; }
      }
      if (projectResult != null && !wt.fc.PersistenceHelper.checkStoredLength(projectResult.toString(), PROJECT_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectResult"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectResult", this.projectResult, projectResult));
   }

   /**
    * 실패사유
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String FAIL_REASON = "failReason";
   static int FAIL_REASON_UPPER_LIMIT = -1;
   java.lang.String failReason;
   /**
    * 실패사유
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getFailReason() {
      return failReason;
   }
   /**
    * 실패사유
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setFailReason(java.lang.String failReason) throws wt.util.WTPropertyVetoException {
      failReasonValidate(failReason);
      this.failReason = failReason;
   }
   void failReasonValidate(java.lang.String failReason) throws wt.util.WTPropertyVetoException {
      if (FAIL_REASON_UPPER_LIMIT < 1) {
         try { FAIL_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("failReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAIL_REASON_UPPER_LIMIT = 4000; }
      }
      if (failReason != null && !wt.fc.PersistenceHelper.checkStoredLength(failReason.toString(), FAIL_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "failReason"), java.lang.String.valueOf(java.lang.Math.min(FAIL_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "failReason", this.failReason, failReason));
   }

   /**
    * 계획시작일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String START_PLAN_DATE = "startPlanDate";
   java.sql.Timestamp startPlanDate;
   /**
    * 계획시작일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.sql.Timestamp getStartPlanDate() {
      return startPlanDate;
   }
   /**
    * 계획시작일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setStartPlanDate(java.sql.Timestamp startPlanDate) throws wt.util.WTPropertyVetoException {
      startPlanDateValidate(startPlanDate);
      this.startPlanDate = startPlanDate;
   }
   void startPlanDateValidate(java.sql.Timestamp startPlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 계획종료일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String END_PLAN_DATE = "endPlanDate";
   java.sql.Timestamp endPlanDate;
   /**
    * 계획종료일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.sql.Timestamp getEndPlanDate() {
      return endPlanDate;
   }
   /**
    * 계획종료일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setEndPlanDate(java.sql.Timestamp endPlanDate) throws wt.util.WTPropertyVetoException {
      endPlanDateValidate(endPlanDate);
      this.endPlanDate = endPlanDate;
   }
   void endPlanDateValidate(java.sql.Timestamp endPlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 실적 시작일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String START_RESULT_DATE = "startResultDate";
   java.sql.Timestamp startResultDate;
   /**
    * 실적 시작일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.sql.Timestamp getStartResultDate() {
      return startResultDate;
   }
   /**
    * 실적 시작일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setStartResultDate(java.sql.Timestamp startResultDate) throws wt.util.WTPropertyVetoException {
      startResultDateValidate(startResultDate);
      this.startResultDate = startResultDate;
   }
   void startResultDateValidate(java.sql.Timestamp startResultDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 실적 종료일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String END_RESULT_DATE = "endResultDate";
   java.sql.Timestamp endResultDate;
   /**
    * 실적 종료일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.sql.Timestamp getEndResultDate() {
      return endResultDate;
   }
   /**
    * 실적 종료일
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setEndResultDate(java.sql.Timestamp endResultDate) throws wt.util.WTPropertyVetoException {
      endResultDateValidate(endResultDate);
      this.endResultDate = endResultDate;
   }
   void endResultDateValidate(java.sql.Timestamp endResultDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * CS회의여부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String CS_YN = "csYn";
   static int CS_YN_UPPER_LIMIT = -1;
   java.lang.String csYn;
   /**
    * CS회의여부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getCsYn() {
      return csYn;
   }
   /**
    * CS회의여부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setCsYn(java.lang.String csYn) throws wt.util.WTPropertyVetoException {
      csYnValidate(csYn);
      this.csYn = csYn;
   }
   void csYnValidate(java.lang.String csYn) throws wt.util.WTPropertyVetoException {
      if (CS_YN_UPPER_LIMIT < 1) {
         try { CS_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("csYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CS_YN_UPPER_LIMIT = 200; }
      }
      if (csYn != null && !wt.fc.PersistenceHelper.checkStoredLength(csYn.toString(), CS_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "csYn"), java.lang.String.valueOf(java.lang.Math.min(CS_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "csYn", this.csYn, csYn));
   }

   /**
    * 비고
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String BIGO = "bigo";
   static int BIGO_UPPER_LIMIT = -1;
   java.lang.String bigo;
   /**
    * 비고
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getBigo() {
      return bigo;
   }
   /**
    * 비고
    *
    * @see ext.ket.sales.entity.KETSalesProject
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
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 영업목표
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * 영업목표
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * 영업목표
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String APPROVE_DATE = "approveDate";
   java.sql.Timestamp approveDate;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.sql.Timestamp getApproveDate() {
      return approveDate;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setApproveDate(java.sql.Timestamp approveDate) throws wt.util.WTPropertyVetoException {
      approveDateValidate(approveDate);
      this.approveDate = approveDate;
   }
   void approveDateValidate(java.sql.Timestamp approveDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 센터부서코드
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String CENTER_CODE = "centerCode";
   static int CENTER_CODE_UPPER_LIMIT = -1;
   java.lang.String centerCode;
   /**
    * 센터부서코드
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getCenterCode() {
      return centerCode;
   }
   /**
    * 센터부서코드
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setCenterCode(java.lang.String centerCode) throws wt.util.WTPropertyVetoException {
      centerCodeValidate(centerCode);
      this.centerCode = centerCode;
   }
   void centerCodeValidate(java.lang.String centerCode) throws wt.util.WTPropertyVetoException {
      if (CENTER_CODE_UPPER_LIMIT < 1) {
         try { CENTER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("centerCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CENTER_CODE_UPPER_LIMIT = 200; }
      }
      if (centerCode != null && !wt.fc.PersistenceHelper.checkStoredLength(centerCode.toString(), CENTER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "centerCode"), java.lang.String.valueOf(java.lang.Math.min(CENTER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "centerCode", this.centerCode, centerCode));
   }

   /**
    * 워크샵 여부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String WORK_SHOP_YN = "workShopYN";
   static int WORK_SHOP_YN_UPPER_LIMIT = -1;
   java.lang.String workShopYN;
   /**
    * 워크샵 여부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public java.lang.String getWorkShopYN() {
      return workShopYN;
   }
   /**
    * 워크샵 여부
    *
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setWorkShopYN(java.lang.String workShopYN) throws wt.util.WTPropertyVetoException {
      workShopYNValidate(workShopYN);
      this.workShopYN = workShopYN;
   }
   void workShopYNValidate(java.lang.String workShopYN) throws wt.util.WTPropertyVetoException {
      if (WORK_SHOP_YN_UPPER_LIMIT < 1) {
         try { WORK_SHOP_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workShopYN").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_SHOP_YN_UPPER_LIMIT = 200; }
      }
      if (workShopYN != null && !wt.fc.PersistenceHelper.checkStoredLength(workShopYN.toString(), WORK_SHOP_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workShopYN"), java.lang.String.valueOf(java.lang.Math.min(WORK_SHOP_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workShopYN", this.workShopYN, workShopYN));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String DEV_TYPE = "devType";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String DEV_TYPE_REFERENCE = "devTypeReference";
   wt.fc.ObjectReference devTypeReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getDevType() {
      return (devTypeReference != null) ? (e3ps.common.code.NumberCode) devTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getDevTypeReference() {
      return devTypeReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setDevType(e3ps.common.code.NumberCode the_devType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevTypeReference(the_devType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_devType));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setDevTypeReference(wt.fc.ObjectReference the_devTypeReference) throws wt.util.WTPropertyVetoException {
      devTypeReferenceValidate(the_devTypeReference);
      devTypeReference = (wt.fc.ObjectReference) the_devTypeReference;
   }
   void devTypeReferenceValidate(wt.fc.ObjectReference the_devTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_devTypeReference != null && the_devTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_devTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "devTypeReference", this.devTypeReference, devTypeReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String NATION_TYPE = "nationType";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String NATION_TYPE_REFERENCE = "nationTypeReference";
   wt.fc.ObjectReference nationTypeReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getNationType() {
      return (nationTypeReference != null) ? (e3ps.common.code.NumberCode) nationTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getNationTypeReference() {
      return nationTypeReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setNationType(e3ps.common.code.NumberCode the_nationType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setNationTypeReference(the_nationType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_nationType));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setNationTypeReference(wt.fc.ObjectReference the_nationTypeReference) throws wt.util.WTPropertyVetoException {
      nationTypeReferenceValidate(the_nationTypeReference);
      nationTypeReference = (wt.fc.ObjectReference) the_nationTypeReference;
   }
   void nationTypeReferenceValidate(wt.fc.ObjectReference the_nationTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_nationTypeReference != null && the_nationTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_nationTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "nationTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "nationTypeReference", this.nationTypeReference, nationTypeReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String OBTAIN_COMPANY_TYPE = "obtainCompanyType";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String OBTAIN_COMPANY_TYPE_REFERENCE = "obtainCompanyTypeReference";
   wt.fc.ObjectReference obtainCompanyTypeReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getObtainCompanyType() {
      return (obtainCompanyTypeReference != null) ? (e3ps.common.code.NumberCode) obtainCompanyTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getObtainCompanyTypeReference() {
      return obtainCompanyTypeReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setObtainCompanyType(e3ps.common.code.NumberCode the_obtainCompanyType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setObtainCompanyTypeReference(the_obtainCompanyType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_obtainCompanyType));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setObtainCompanyTypeReference(wt.fc.ObjectReference the_obtainCompanyTypeReference) throws wt.util.WTPropertyVetoException {
      obtainCompanyTypeReferenceValidate(the_obtainCompanyTypeReference);
      obtainCompanyTypeReference = (wt.fc.ObjectReference) the_obtainCompanyTypeReference;
   }
   void obtainCompanyTypeReferenceValidate(wt.fc.ObjectReference the_obtainCompanyTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_obtainCompanyTypeReference != null && the_obtainCompanyTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_obtainCompanyTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "obtainCompanyTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "obtainCompanyTypeReference", this.obtainCompanyTypeReference, obtainCompanyTypeReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String FAIL_TYPE = "failType";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String FAIL_TYPE_REFERENCE = "failTypeReference";
   wt.fc.ObjectReference failTypeReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getFailType() {
      return (failTypeReference != null) ? (e3ps.common.code.NumberCode) failTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getFailTypeReference() {
      return failTypeReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setFailType(e3ps.common.code.NumberCode the_failType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFailTypeReference(the_failType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_failType));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setFailTypeReference(wt.fc.ObjectReference the_failTypeReference) throws wt.util.WTPropertyVetoException {
      failTypeReferenceValidate(the_failTypeReference);
      failTypeReference = (wt.fc.ObjectReference) the_failTypeReference;
   }
   void failTypeReferenceValidate(wt.fc.ObjectReference the_failTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_failTypeReference != null && the_failTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_failTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "failTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "failTypeReference", this.failTypeReference, failTypeReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String PROEJCT_STATE = "proejctState";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String PROEJCT_STATE_REFERENCE = "proejctStateReference";
   wt.fc.ObjectReference proejctStateReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getProejctState() {
      return (proejctStateReference != null) ? (e3ps.common.code.NumberCode) proejctStateReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getProejctStateReference() {
      return proejctStateReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setProejctState(e3ps.common.code.NumberCode the_proejctState) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProejctStateReference(the_proejctState == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_proejctState));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setProejctStateReference(wt.fc.ObjectReference the_proejctStateReference) throws wt.util.WTPropertyVetoException {
      proejctStateReferenceValidate(the_proejctStateReference);
      proejctStateReference = (wt.fc.ObjectReference) the_proejctStateReference;
   }
   void proejctStateReferenceValidate(wt.fc.ObjectReference the_proejctStateReference) throws wt.util.WTPropertyVetoException {
      if (the_proejctStateReference == null || the_proejctStateReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "proejctStateReference") },
               new java.beans.PropertyChangeEvent(this, "proejctStateReference", this.proejctStateReference, proejctStateReference));
      if (the_proejctStateReference != null && the_proejctStateReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_proejctStateReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "proejctStateReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "proejctStateReference", this.proejctStateReference, proejctStateReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String RANK_TYPE = "rankType";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String RANK_TYPE_REFERENCE = "rankTypeReference";
   wt.fc.ObjectReference rankTypeReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getRankType() {
      return (rankTypeReference != null) ? (e3ps.common.code.NumberCode) rankTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getRankTypeReference() {
      return rankTypeReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setRankType(e3ps.common.code.NumberCode the_rankType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setRankTypeReference(the_rankType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_rankType));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setRankTypeReference(wt.fc.ObjectReference the_rankTypeReference) throws wt.util.WTPropertyVetoException {
      rankTypeReferenceValidate(the_rankTypeReference);
      rankTypeReference = (wt.fc.ObjectReference) the_rankTypeReference;
   }
   void rankTypeReferenceValidate(wt.fc.ObjectReference the_rankTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_rankTypeReference == null || the_rankTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rankTypeReference") },
               new java.beans.PropertyChangeEvent(this, "rankTypeReference", this.rankTypeReference, rankTypeReference));
      if (the_rankTypeReference != null && the_rankTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_rankTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rankTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "rankTypeReference", this.rankTypeReference, rankTypeReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String OEM_TYPE = "oemType";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String OEM_TYPE_REFERENCE = "oemTypeReference";
   wt.fc.ObjectReference oemTypeReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.project.outputtype.OEMProjectType getOemType() {
      return (oemTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) oemTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getOemTypeReference() {
      return oemTypeReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setOemType(e3ps.project.outputtype.OEMProjectType the_oemType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOemTypeReference(the_oemType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_oemType));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setOemTypeReference(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      oemTypeReferenceValidate(the_oemTypeReference);
      oemTypeReference = (wt.fc.ObjectReference) the_oemTypeReference;
   }
   void oemTypeReferenceValidate(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_oemTypeReference == null || the_oemTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oemTypeReference") },
               new java.beans.PropertyChangeEvent(this, "oemTypeReference", this.oemTypeReference, oemTypeReference));
      if (the_oemTypeReference != null && the_oemTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_oemTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oemTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "oemTypeReference", this.oemTypeReference, oemTypeReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String COMPETITOR = "competitor";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String COMPETITOR_REFERENCE = "competitorReference";
   wt.fc.ObjectReference competitorReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getCompetitor() {
      return (competitorReference != null) ? (e3ps.common.code.NumberCode) competitorReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getCompetitorReference() {
      return competitorReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setCompetitor(e3ps.common.code.NumberCode the_competitor) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCompetitorReference(the_competitor == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_competitor));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setCompetitorReference(wt.fc.ObjectReference the_competitorReference) throws wt.util.WTPropertyVetoException {
      competitorReferenceValidate(the_competitorReference);
      competitorReference = (wt.fc.ObjectReference) the_competitorReference;
   }
   void competitorReferenceValidate(wt.fc.ObjectReference the_competitorReference) throws wt.util.WTPropertyVetoException {
      if (the_competitorReference != null && the_competitorReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_competitorReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "competitorReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "competitorReference", this.competitorReference, competitorReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String SALES_STATE = "salesState";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String SALES_STATE_REFERENCE = "salesStateReference";
   wt.fc.ObjectReference salesStateReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getSalesState() {
      return (salesStateReference != null) ? (e3ps.common.code.NumberCode) salesStateReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getSalesStateReference() {
      return salesStateReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setSalesState(e3ps.common.code.NumberCode the_salesState) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setSalesStateReference(the_salesState == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_salesState));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setSalesStateReference(wt.fc.ObjectReference the_salesStateReference) throws wt.util.WTPropertyVetoException {
      salesStateReferenceValidate(the_salesStateReference);
      salesStateReference = (wt.fc.ObjectReference) the_salesStateReference;
   }
   void salesStateReferenceValidate(wt.fc.ObjectReference the_salesStateReference) throws wt.util.WTPropertyVetoException {
      if (the_salesStateReference != null && the_salesStateReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_salesStateReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesStateReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "salesStateReference", this.salesStateReference, salesStateReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String AFTER_SALES_STATE = "afterSalesState";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String AFTER_SALES_STATE_REFERENCE = "afterSalesStateReference";
   wt.fc.ObjectReference afterSalesStateReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getAfterSalesState() {
      return (afterSalesStateReference != null) ? (e3ps.common.code.NumberCode) afterSalesStateReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getAfterSalesStateReference() {
      return afterSalesStateReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setAfterSalesState(e3ps.common.code.NumberCode the_afterSalesState) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAfterSalesStateReference(the_afterSalesState == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_afterSalesState));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setAfterSalesStateReference(wt.fc.ObjectReference the_afterSalesStateReference) throws wt.util.WTPropertyVetoException {
      afterSalesStateReferenceValidate(the_afterSalesStateReference);
      afterSalesStateReference = (wt.fc.ObjectReference) the_afterSalesStateReference;
   }
   void afterSalesStateReferenceValidate(wt.fc.ObjectReference the_afterSalesStateReference) throws wt.util.WTPropertyVetoException {
      if (the_afterSalesStateReference != null && the_afterSalesStateReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_afterSalesStateReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "afterSalesStateReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "afterSalesStateReference", this.afterSalesStateReference, afterSalesStateReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String DEV_REQUEST = "devRequest";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String DEV_REQUEST_REFERENCE = "devRequestReference";
   wt.fc.ObjectReference devRequestReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.dms.entity.KETDevelopmentRequest getDevRequest() {
      return (devRequestReference != null) ? (e3ps.dms.entity.KETDevelopmentRequest) devRequestReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getDevRequestReference() {
      return devRequestReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setDevRequest(e3ps.dms.entity.KETDevelopmentRequest the_devRequest) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevRequestReference(the_devRequest == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.dms.entity.KETDevelopmentRequest) the_devRequest));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setDevRequestReference(wt.fc.ObjectReference the_devRequestReference) throws wt.util.WTPropertyVetoException {
      devRequestReferenceValidate(the_devRequestReference);
      devRequestReference = (wt.fc.ObjectReference) the_devRequestReference;
   }
   void devRequestReferenceValidate(wt.fc.ObjectReference the_devRequestReference) throws wt.util.WTPropertyVetoException {
      if (the_devRequestReference != null && the_devRequestReference.getReferencedClass() != null &&
            !e3ps.dms.entity.KETDevelopmentRequest.class.isAssignableFrom(the_devRequestReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devRequestReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "devRequestReference", this.devRequestReference, devRequestReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String SALES_TEAM = "salesTeam";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String SALES_TEAM_REFERENCE = "salesTeamReference";
   wt.fc.ObjectReference salesTeamReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.groupware.company.Department getSalesTeam() {
      return (salesTeamReference != null) ? (e3ps.groupware.company.Department) salesTeamReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getSalesTeamReference() {
      return salesTeamReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setSalesTeam(e3ps.groupware.company.Department the_salesTeam) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setSalesTeamReference(the_salesTeam == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_salesTeam));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setSalesTeamReference(wt.fc.ObjectReference the_salesTeamReference) throws wt.util.WTPropertyVetoException {
      salesTeamReferenceValidate(the_salesTeamReference);
      salesTeamReference = (wt.fc.ObjectReference) the_salesTeamReference;
   }
   void salesTeamReferenceValidate(wt.fc.ObjectReference the_salesTeamReference) throws wt.util.WTPropertyVetoException {
      if (the_salesTeamReference == null || the_salesTeamReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTeamReference") },
               new java.beans.PropertyChangeEvent(this, "salesTeamReference", this.salesTeamReference, salesTeamReference));
      if (the_salesTeamReference != null && the_salesTeamReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_salesTeamReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTeamReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "salesTeamReference", this.salesTeamReference, salesTeamReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String MAIN_CATEGORY_TYPE = "mainCategoryType";
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public static final java.lang.String MAIN_CATEGORY_TYPE_REFERENCE = "mainCategoryTypeReference";
   wt.fc.ObjectReference mainCategoryTypeReference;
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public e3ps.common.code.NumberCode getMainCategoryType() {
      return (mainCategoryTypeReference != null) ? (e3ps.common.code.NumberCode) mainCategoryTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public wt.fc.ObjectReference getMainCategoryTypeReference() {
      return mainCategoryTypeReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setMainCategoryType(e3ps.common.code.NumberCode the_mainCategoryType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMainCategoryTypeReference(the_mainCategoryType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_mainCategoryType));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesProject
    */
   public void setMainCategoryTypeReference(wt.fc.ObjectReference the_mainCategoryTypeReference) throws wt.util.WTPropertyVetoException {
      mainCategoryTypeReferenceValidate(the_mainCategoryTypeReference);
      mainCategoryTypeReference = (wt.fc.ObjectReference) the_mainCategoryTypeReference;
   }
   void mainCategoryTypeReferenceValidate(wt.fc.ObjectReference the_mainCategoryTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_mainCategoryTypeReference != null && the_mainCategoryTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_mainCategoryTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mainCategoryTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "mainCategoryTypeReference", this.mainCategoryTypeReference, mainCategoryTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 804100777479412488L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( afterSalesStateReference );
      output.writeObject( applyArea );
      output.writeObject( approveDate );
      output.writeObject( bigo );
      output.writeObject( centerCode );
      output.writeObject( competitorReference );
      output.writeObject( csYn );
      output.writeObject( devRequestReference );
      output.writeObject( devTypeReference );
      output.writeObject( endPlanDate );
      output.writeObject( endResultDate );
      output.writeObject( expectSalesTotal );
      output.writeObject( expectSalesYear );
      output.writeObject( failReason );
      output.writeObject( failTypeReference );
      output.writeObject( investCost );
      output.writeObject( mainCategoryTypeReference );
      output.writeObject( nationTypeReference );
      output.writeObject( obtainCompanyTypeReference );
      output.writeObject( oemTypeReference );
      output.writeObject( planTotal );
      output.writeObject( planYear );
      output.writeObject( proejctStateReference );
      output.writeObject( projectName );
      output.writeObject( projectNo );
      output.writeObject( projectResult );
      output.writeObject( rankTypeReference );
      output.writeObject( salesStateReference );
      output.writeObject( salesTeamReference );
      output.writeObject( sop );
      output.writeObject( startPlanDate );
      output.writeObject( startResultDate );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );
      output.writeObject( workShopYN );
   }

   protected void super_writeExternal_KETSalesProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSalesProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "afterSalesStateReference", afterSalesStateReference, wt.fc.ObjectReference.class, true );
      output.setString( "applyArea", applyArea );
      output.setTimestamp( "approveDate", approveDate );
      output.setString( "bigo", bigo );
      output.setString( "centerCode", centerCode );
      output.writeObject( "competitorReference", competitorReference, wt.fc.ObjectReference.class, true );
      output.setString( "csYn", csYn );
      output.writeObject( "devRequestReference", devRequestReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "devTypeReference", devTypeReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "endPlanDate", endPlanDate );
      output.setTimestamp( "endResultDate", endResultDate );
      output.setString( "expectSalesTotal", expectSalesTotal );
      output.setString( "expectSalesYear", expectSalesYear );
      output.setString( "failReason", failReason );
      output.writeObject( "failTypeReference", failTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "investCost", investCost );
      output.writeObject( "mainCategoryTypeReference", mainCategoryTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "nationTypeReference", nationTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "obtainCompanyTypeReference", obtainCompanyTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "planTotal", planTotal );
      output.setString( "planYear", planYear );
      output.writeObject( "proejctStateReference", proejctStateReference, wt.fc.ObjectReference.class, true );
      output.setString( "projectName", projectName );
      output.setString( "projectNo", projectNo );
      output.setString( "projectResult", projectResult );
      output.writeObject( "rankTypeReference", rankTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "salesStateReference", salesStateReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "salesTeamReference", salesTeamReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "sop", sop );
      output.setTimestamp( "startPlanDate", startPlanDate );
      output.setTimestamp( "startResultDate", startResultDate );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
      output.setString( "workShopYN", workShopYN );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      afterSalesStateReference = (wt.fc.ObjectReference) input.readObject( "afterSalesStateReference", afterSalesStateReference, wt.fc.ObjectReference.class, true );
      applyArea = input.getString( "applyArea" );
      approveDate = input.getTimestamp( "approveDate" );
      bigo = input.getString( "bigo" );
      centerCode = input.getString( "centerCode" );
      competitorReference = (wt.fc.ObjectReference) input.readObject( "competitorReference", competitorReference, wt.fc.ObjectReference.class, true );
      csYn = input.getString( "csYn" );
      devRequestReference = (wt.fc.ObjectReference) input.readObject( "devRequestReference", devRequestReference, wt.fc.ObjectReference.class, true );
      devTypeReference = (wt.fc.ObjectReference) input.readObject( "devTypeReference", devTypeReference, wt.fc.ObjectReference.class, true );
      endPlanDate = input.getTimestamp( "endPlanDate" );
      endResultDate = input.getTimestamp( "endResultDate" );
      expectSalesTotal = input.getString( "expectSalesTotal" );
      expectSalesYear = input.getString( "expectSalesYear" );
      failReason = input.getString( "failReason" );
      failTypeReference = (wt.fc.ObjectReference) input.readObject( "failTypeReference", failTypeReference, wt.fc.ObjectReference.class, true );
      investCost = input.getString( "investCost" );
      mainCategoryTypeReference = (wt.fc.ObjectReference) input.readObject( "mainCategoryTypeReference", mainCategoryTypeReference, wt.fc.ObjectReference.class, true );
      nationTypeReference = (wt.fc.ObjectReference) input.readObject( "nationTypeReference", nationTypeReference, wt.fc.ObjectReference.class, true );
      obtainCompanyTypeReference = (wt.fc.ObjectReference) input.readObject( "obtainCompanyTypeReference", obtainCompanyTypeReference, wt.fc.ObjectReference.class, true );
      oemTypeReference = (wt.fc.ObjectReference) input.readObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      planTotal = input.getString( "planTotal" );
      planYear = input.getString( "planYear" );
      proejctStateReference = (wt.fc.ObjectReference) input.readObject( "proejctStateReference", proejctStateReference, wt.fc.ObjectReference.class, true );
      projectName = input.getString( "projectName" );
      projectNo = input.getString( "projectNo" );
      projectResult = input.getString( "projectResult" );
      rankTypeReference = (wt.fc.ObjectReference) input.readObject( "rankTypeReference", rankTypeReference, wt.fc.ObjectReference.class, true );
      salesStateReference = (wt.fc.ObjectReference) input.readObject( "salesStateReference", salesStateReference, wt.fc.ObjectReference.class, true );
      salesTeamReference = (wt.fc.ObjectReference) input.readObject( "salesTeamReference", salesTeamReference, wt.fc.ObjectReference.class, true );
      sop = input.getTimestamp( "sop" );
      startPlanDate = input.getTimestamp( "startPlanDate" );
      startResultDate = input.getTimestamp( "startResultDate" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
      workShopYN = input.getString( "workShopYN" );
   }

   boolean readVersion804100777479412488L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      afterSalesStateReference = (wt.fc.ObjectReference) input.readObject();
      applyArea = (java.lang.String) input.readObject();
      approveDate = (java.sql.Timestamp) input.readObject();
      bigo = (java.lang.String) input.readObject();
      centerCode = (java.lang.String) input.readObject();
      competitorReference = (wt.fc.ObjectReference) input.readObject();
      csYn = (java.lang.String) input.readObject();
      devRequestReference = (wt.fc.ObjectReference) input.readObject();
      devTypeReference = (wt.fc.ObjectReference) input.readObject();
      endPlanDate = (java.sql.Timestamp) input.readObject();
      endResultDate = (java.sql.Timestamp) input.readObject();
      expectSalesTotal = (java.lang.String) input.readObject();
      expectSalesYear = (java.lang.String) input.readObject();
      failReason = (java.lang.String) input.readObject();
      failTypeReference = (wt.fc.ObjectReference) input.readObject();
      investCost = (java.lang.String) input.readObject();
      mainCategoryTypeReference = (wt.fc.ObjectReference) input.readObject();
      nationTypeReference = (wt.fc.ObjectReference) input.readObject();
      obtainCompanyTypeReference = (wt.fc.ObjectReference) input.readObject();
      oemTypeReference = (wt.fc.ObjectReference) input.readObject();
      planTotal = (java.lang.String) input.readObject();
      planYear = (java.lang.String) input.readObject();
      proejctStateReference = (wt.fc.ObjectReference) input.readObject();
      projectName = (java.lang.String) input.readObject();
      projectNo = (java.lang.String) input.readObject();
      projectResult = (java.lang.String) input.readObject();
      rankTypeReference = (wt.fc.ObjectReference) input.readObject();
      salesStateReference = (wt.fc.ObjectReference) input.readObject();
      salesTeamReference = (wt.fc.ObjectReference) input.readObject();
      sop = (java.sql.Timestamp) input.readObject();
      startPlanDate = (java.sql.Timestamp) input.readObject();
      startResultDate = (java.sql.Timestamp) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();
      workShopYN = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETSalesProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion804100777479412488L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSalesProject( _KETSalesProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
