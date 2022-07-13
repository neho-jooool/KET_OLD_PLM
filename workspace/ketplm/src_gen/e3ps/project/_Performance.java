package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _Performance extends wt.enterprise.Managed implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = Performance.class.getName();

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String PLAN_QUALITY = "planQuality";
   static int PLAN_QUALITY_UPPER_LIMIT = -1;
   java.lang.String planQuality;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getPlanQuality() {
      return planQuality;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setPlanQuality(java.lang.String planQuality) throws wt.util.WTPropertyVetoException {
      planQualityValidate(planQuality);
      this.planQuality = planQuality;
   }
   void planQualityValidate(java.lang.String planQuality) throws wt.util.WTPropertyVetoException {
      if (PLAN_QUALITY_UPPER_LIMIT < 1) {
         try { PLAN_QUALITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planQuality").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_QUALITY_UPPER_LIMIT = 200; }
      }
      if (planQuality != null && !wt.fc.PersistenceHelper.checkStoredLength(planQuality.toString(), PLAN_QUALITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planQuality"), java.lang.String.valueOf(java.lang.Math.min(PLAN_QUALITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planQuality", this.planQuality, planQuality));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String PLAN_COST = "planCost";
   static int PLAN_COST_UPPER_LIMIT = -1;
   java.lang.String planCost;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getPlanCost() {
      return planCost;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setPlanCost(java.lang.String planCost) throws wt.util.WTPropertyVetoException {
      planCostValidate(planCost);
      this.planCost = planCost;
   }
   void planCostValidate(java.lang.String planCost) throws wt.util.WTPropertyVetoException {
      if (PLAN_COST_UPPER_LIMIT < 1) {
         try { PLAN_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_COST_UPPER_LIMIT = 200; }
      }
      if (planCost != null && !wt.fc.PersistenceHelper.checkStoredLength(planCost.toString(), PLAN_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planCost"), java.lang.String.valueOf(java.lang.Math.min(PLAN_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planCost", this.planCost, planCost));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String PLAN_DELIVERY1 = "planDelivery1";
   static int PLAN_DELIVERY1_UPPER_LIMIT = -1;
   java.lang.String planDelivery1;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getPlanDelivery1() {
      return planDelivery1;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setPlanDelivery1(java.lang.String planDelivery1) throws wt.util.WTPropertyVetoException {
      planDelivery1Validate(planDelivery1);
      this.planDelivery1 = planDelivery1;
   }
   void planDelivery1Validate(java.lang.String planDelivery1) throws wt.util.WTPropertyVetoException {
      if (PLAN_DELIVERY1_UPPER_LIMIT < 1) {
         try { PLAN_DELIVERY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planDelivery1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_DELIVERY1_UPPER_LIMIT = 200; }
      }
      if (planDelivery1 != null && !wt.fc.PersistenceHelper.checkStoredLength(planDelivery1.toString(), PLAN_DELIVERY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planDelivery1"), java.lang.String.valueOf(java.lang.Math.min(PLAN_DELIVERY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planDelivery1", this.planDelivery1, planDelivery1));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String PLAN_DELIVERY2 = "planDelivery2";
   static int PLAN_DELIVERY2_UPPER_LIMIT = -1;
   java.lang.String planDelivery2;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getPlanDelivery2() {
      return planDelivery2;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setPlanDelivery2(java.lang.String planDelivery2) throws wt.util.WTPropertyVetoException {
      planDelivery2Validate(planDelivery2);
      this.planDelivery2 = planDelivery2;
   }
   void planDelivery2Validate(java.lang.String planDelivery2) throws wt.util.WTPropertyVetoException {
      if (PLAN_DELIVERY2_UPPER_LIMIT < 1) {
         try { PLAN_DELIVERY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planDelivery2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_DELIVERY2_UPPER_LIMIT = 200; }
      }
      if (planDelivery2 != null && !wt.fc.PersistenceHelper.checkStoredLength(planDelivery2.toString(), PLAN_DELIVERY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planDelivery2"), java.lang.String.valueOf(java.lang.Math.min(PLAN_DELIVERY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planDelivery2", this.planDelivery2, planDelivery2));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String PLAN_DELIVERY3 = "planDelivery3";
   static int PLAN_DELIVERY3_UPPER_LIMIT = -1;
   java.lang.String planDelivery3;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getPlanDelivery3() {
      return planDelivery3;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setPlanDelivery3(java.lang.String planDelivery3) throws wt.util.WTPropertyVetoException {
      planDelivery3Validate(planDelivery3);
      this.planDelivery3 = planDelivery3;
   }
   void planDelivery3Validate(java.lang.String planDelivery3) throws wt.util.WTPropertyVetoException {
      if (PLAN_DELIVERY3_UPPER_LIMIT < 1) {
         try { PLAN_DELIVERY3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planDelivery3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_DELIVERY3_UPPER_LIMIT = 200; }
      }
      if (planDelivery3 != null && !wt.fc.PersistenceHelper.checkStoredLength(planDelivery3.toString(), PLAN_DELIVERY3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planDelivery3"), java.lang.String.valueOf(java.lang.Math.min(PLAN_DELIVERY3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planDelivery3", this.planDelivery3, planDelivery3));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String PLAN_TECHNICAL = "planTechnical";
   static int PLAN_TECHNICAL_UPPER_LIMIT = -1;
   java.lang.String planTechnical;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getPlanTechnical() {
      return planTechnical;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setPlanTechnical(java.lang.String planTechnical) throws wt.util.WTPropertyVetoException {
      planTechnicalValidate(planTechnical);
      this.planTechnical = planTechnical;
   }
   void planTechnicalValidate(java.lang.String planTechnical) throws wt.util.WTPropertyVetoException {
      if (PLAN_TECHNICAL_UPPER_LIMIT < 1) {
         try { PLAN_TECHNICAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planTechnical").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_TECHNICAL_UPPER_LIMIT = 200; }
      }
      if (planTechnical != null && !wt.fc.PersistenceHelper.checkStoredLength(planTechnical.toString(), PLAN_TECHNICAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planTechnical"), java.lang.String.valueOf(java.lang.Math.min(PLAN_TECHNICAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planTechnical", this.planTechnical, planTechnical));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RESULT_QUALITY = "resultQuality";
   static int RESULT_QUALITY_UPPER_LIMIT = -1;
   java.lang.String resultQuality;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getResultQuality() {
      return resultQuality;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setResultQuality(java.lang.String resultQuality) throws wt.util.WTPropertyVetoException {
      resultQualityValidate(resultQuality);
      this.resultQuality = resultQuality;
   }
   void resultQualityValidate(java.lang.String resultQuality) throws wt.util.WTPropertyVetoException {
      if (RESULT_QUALITY_UPPER_LIMIT < 1) {
         try { RESULT_QUALITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("resultQuality").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_QUALITY_UPPER_LIMIT = 200; }
      }
      if (resultQuality != null && !wt.fc.PersistenceHelper.checkStoredLength(resultQuality.toString(), RESULT_QUALITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "resultQuality"), java.lang.String.valueOf(java.lang.Math.min(RESULT_QUALITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "resultQuality", this.resultQuality, resultQuality));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RESULT_COST = "resultCost";
   static int RESULT_COST_UPPER_LIMIT = -1;
   java.lang.String resultCost;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getResultCost() {
      return resultCost;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setResultCost(java.lang.String resultCost) throws wt.util.WTPropertyVetoException {
      resultCostValidate(resultCost);
      this.resultCost = resultCost;
   }
   void resultCostValidate(java.lang.String resultCost) throws wt.util.WTPropertyVetoException {
      if (RESULT_COST_UPPER_LIMIT < 1) {
         try { RESULT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("resultCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_COST_UPPER_LIMIT = 200; }
      }
      if (resultCost != null && !wt.fc.PersistenceHelper.checkStoredLength(resultCost.toString(), RESULT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "resultCost"), java.lang.String.valueOf(java.lang.Math.min(RESULT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "resultCost", this.resultCost, resultCost));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RESULT_DELIVERY1 = "resultDelivery1";
   static int RESULT_DELIVERY1_UPPER_LIMIT = -1;
   java.lang.String resultDelivery1;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getResultDelivery1() {
      return resultDelivery1;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setResultDelivery1(java.lang.String resultDelivery1) throws wt.util.WTPropertyVetoException {
      resultDelivery1Validate(resultDelivery1);
      this.resultDelivery1 = resultDelivery1;
   }
   void resultDelivery1Validate(java.lang.String resultDelivery1) throws wt.util.WTPropertyVetoException {
      if (RESULT_DELIVERY1_UPPER_LIMIT < 1) {
         try { RESULT_DELIVERY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("resultDelivery1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_DELIVERY1_UPPER_LIMIT = 200; }
      }
      if (resultDelivery1 != null && !wt.fc.PersistenceHelper.checkStoredLength(resultDelivery1.toString(), RESULT_DELIVERY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "resultDelivery1"), java.lang.String.valueOf(java.lang.Math.min(RESULT_DELIVERY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "resultDelivery1", this.resultDelivery1, resultDelivery1));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RESULT_DELIVERY2 = "resultDelivery2";
   static int RESULT_DELIVERY2_UPPER_LIMIT = -1;
   java.lang.String resultDelivery2;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getResultDelivery2() {
      return resultDelivery2;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setResultDelivery2(java.lang.String resultDelivery2) throws wt.util.WTPropertyVetoException {
      resultDelivery2Validate(resultDelivery2);
      this.resultDelivery2 = resultDelivery2;
   }
   void resultDelivery2Validate(java.lang.String resultDelivery2) throws wt.util.WTPropertyVetoException {
      if (RESULT_DELIVERY2_UPPER_LIMIT < 1) {
         try { RESULT_DELIVERY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("resultDelivery2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_DELIVERY2_UPPER_LIMIT = 200; }
      }
      if (resultDelivery2 != null && !wt.fc.PersistenceHelper.checkStoredLength(resultDelivery2.toString(), RESULT_DELIVERY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "resultDelivery2"), java.lang.String.valueOf(java.lang.Math.min(RESULT_DELIVERY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "resultDelivery2", this.resultDelivery2, resultDelivery2));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RESULT_DELIVERY3 = "resultDelivery3";
   static int RESULT_DELIVERY3_UPPER_LIMIT = -1;
   java.lang.String resultDelivery3;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getResultDelivery3() {
      return resultDelivery3;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setResultDelivery3(java.lang.String resultDelivery3) throws wt.util.WTPropertyVetoException {
      resultDelivery3Validate(resultDelivery3);
      this.resultDelivery3 = resultDelivery3;
   }
   void resultDelivery3Validate(java.lang.String resultDelivery3) throws wt.util.WTPropertyVetoException {
      if (RESULT_DELIVERY3_UPPER_LIMIT < 1) {
         try { RESULT_DELIVERY3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("resultDelivery3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_DELIVERY3_UPPER_LIMIT = 200; }
      }
      if (resultDelivery3 != null && !wt.fc.PersistenceHelper.checkStoredLength(resultDelivery3.toString(), RESULT_DELIVERY3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "resultDelivery3"), java.lang.String.valueOf(java.lang.Math.min(RESULT_DELIVERY3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "resultDelivery3", this.resultDelivery3, resultDelivery3));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RESULT_TECHNICAL = "resultTechnical";
   static int RESULT_TECHNICAL_UPPER_LIMIT = -1;
   java.lang.String resultTechnical;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getResultTechnical() {
      return resultTechnical;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setResultTechnical(java.lang.String resultTechnical) throws wt.util.WTPropertyVetoException {
      resultTechnicalValidate(resultTechnical);
      this.resultTechnical = resultTechnical;
   }
   void resultTechnicalValidate(java.lang.String resultTechnical) throws wt.util.WTPropertyVetoException {
      if (RESULT_TECHNICAL_UPPER_LIMIT < 1) {
         try { RESULT_TECHNICAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("resultTechnical").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_TECHNICAL_UPPER_LIMIT = 200; }
      }
      if (resultTechnical != null && !wt.fc.PersistenceHelper.checkStoredLength(resultTechnical.toString(), RESULT_TECHNICAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "resultTechnical"), java.lang.String.valueOf(java.lang.Math.min(RESULT_TECHNICAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "resultTechnical", this.resultTechnical, resultTechnical));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String SCORE_QUALITY = "scoreQuality";
   static int SCORE_QUALITY_UPPER_LIMIT = -1;
   java.lang.String scoreQuality;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getScoreQuality() {
      return scoreQuality;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setScoreQuality(java.lang.String scoreQuality) throws wt.util.WTPropertyVetoException {
      scoreQualityValidate(scoreQuality);
      this.scoreQuality = scoreQuality;
   }
   void scoreQualityValidate(java.lang.String scoreQuality) throws wt.util.WTPropertyVetoException {
      if (SCORE_QUALITY_UPPER_LIMIT < 1) {
         try { SCORE_QUALITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scoreQuality").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCORE_QUALITY_UPPER_LIMIT = 200; }
      }
      if (scoreQuality != null && !wt.fc.PersistenceHelper.checkStoredLength(scoreQuality.toString(), SCORE_QUALITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scoreQuality"), java.lang.String.valueOf(java.lang.Math.min(SCORE_QUALITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scoreQuality", this.scoreQuality, scoreQuality));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String SCORE_COST = "scoreCost";
   static int SCORE_COST_UPPER_LIMIT = -1;
   java.lang.String scoreCost;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getScoreCost() {
      return scoreCost;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setScoreCost(java.lang.String scoreCost) throws wt.util.WTPropertyVetoException {
      scoreCostValidate(scoreCost);
      this.scoreCost = scoreCost;
   }
   void scoreCostValidate(java.lang.String scoreCost) throws wt.util.WTPropertyVetoException {
      if (SCORE_COST_UPPER_LIMIT < 1) {
         try { SCORE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scoreCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCORE_COST_UPPER_LIMIT = 200; }
      }
      if (scoreCost != null && !wt.fc.PersistenceHelper.checkStoredLength(scoreCost.toString(), SCORE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scoreCost"), java.lang.String.valueOf(java.lang.Math.min(SCORE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scoreCost", this.scoreCost, scoreCost));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String SCORE_DELIVERY1 = "scoreDelivery1";
   static int SCORE_DELIVERY1_UPPER_LIMIT = -1;
   java.lang.String scoreDelivery1;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getScoreDelivery1() {
      return scoreDelivery1;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setScoreDelivery1(java.lang.String scoreDelivery1) throws wt.util.WTPropertyVetoException {
      scoreDelivery1Validate(scoreDelivery1);
      this.scoreDelivery1 = scoreDelivery1;
   }
   void scoreDelivery1Validate(java.lang.String scoreDelivery1) throws wt.util.WTPropertyVetoException {
      if (SCORE_DELIVERY1_UPPER_LIMIT < 1) {
         try { SCORE_DELIVERY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scoreDelivery1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCORE_DELIVERY1_UPPER_LIMIT = 200; }
      }
      if (scoreDelivery1 != null && !wt.fc.PersistenceHelper.checkStoredLength(scoreDelivery1.toString(), SCORE_DELIVERY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scoreDelivery1"), java.lang.String.valueOf(java.lang.Math.min(SCORE_DELIVERY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scoreDelivery1", this.scoreDelivery1, scoreDelivery1));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String SCORE_DELIVERY2 = "scoreDelivery2";
   static int SCORE_DELIVERY2_UPPER_LIMIT = -1;
   java.lang.String scoreDelivery2;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getScoreDelivery2() {
      return scoreDelivery2;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setScoreDelivery2(java.lang.String scoreDelivery2) throws wt.util.WTPropertyVetoException {
      scoreDelivery2Validate(scoreDelivery2);
      this.scoreDelivery2 = scoreDelivery2;
   }
   void scoreDelivery2Validate(java.lang.String scoreDelivery2) throws wt.util.WTPropertyVetoException {
      if (SCORE_DELIVERY2_UPPER_LIMIT < 1) {
         try { SCORE_DELIVERY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scoreDelivery2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCORE_DELIVERY2_UPPER_LIMIT = 200; }
      }
      if (scoreDelivery2 != null && !wt.fc.PersistenceHelper.checkStoredLength(scoreDelivery2.toString(), SCORE_DELIVERY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scoreDelivery2"), java.lang.String.valueOf(java.lang.Math.min(SCORE_DELIVERY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scoreDelivery2", this.scoreDelivery2, scoreDelivery2));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String SCORE_DELIVERY3 = "scoreDelivery3";
   static int SCORE_DELIVERY3_UPPER_LIMIT = -1;
   java.lang.String scoreDelivery3;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getScoreDelivery3() {
      return scoreDelivery3;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setScoreDelivery3(java.lang.String scoreDelivery3) throws wt.util.WTPropertyVetoException {
      scoreDelivery3Validate(scoreDelivery3);
      this.scoreDelivery3 = scoreDelivery3;
   }
   void scoreDelivery3Validate(java.lang.String scoreDelivery3) throws wt.util.WTPropertyVetoException {
      if (SCORE_DELIVERY3_UPPER_LIMIT < 1) {
         try { SCORE_DELIVERY3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scoreDelivery3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCORE_DELIVERY3_UPPER_LIMIT = 200; }
      }
      if (scoreDelivery3 != null && !wt.fc.PersistenceHelper.checkStoredLength(scoreDelivery3.toString(), SCORE_DELIVERY3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scoreDelivery3"), java.lang.String.valueOf(java.lang.Math.min(SCORE_DELIVERY3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scoreDelivery3", this.scoreDelivery3, scoreDelivery3));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String SCORE_TECHNICAL = "scoreTechnical";
   static int SCORE_TECHNICAL_UPPER_LIMIT = -1;
   java.lang.String scoreTechnical;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getScoreTechnical() {
      return scoreTechnical;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setScoreTechnical(java.lang.String scoreTechnical) throws wt.util.WTPropertyVetoException {
      scoreTechnicalValidate(scoreTechnical);
      this.scoreTechnical = scoreTechnical;
   }
   void scoreTechnicalValidate(java.lang.String scoreTechnical) throws wt.util.WTPropertyVetoException {
      if (SCORE_TECHNICAL_UPPER_LIMIT < 1) {
         try { SCORE_TECHNICAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scoreTechnical").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCORE_TECHNICAL_UPPER_LIMIT = 200; }
      }
      if (scoreTechnical != null && !wt.fc.PersistenceHelper.checkStoredLength(scoreTechnical.toString(), SCORE_TECHNICAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scoreTechnical"), java.lang.String.valueOf(java.lang.Math.min(SCORE_TECHNICAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scoreTechnical", this.scoreTechnical, scoreTechnical));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String EVALUATE_QUALITY = "evaluateQuality";
   static int EVALUATE_QUALITY_UPPER_LIMIT = -1;
   java.lang.String evaluateQuality;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getEvaluateQuality() {
      return evaluateQuality;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setEvaluateQuality(java.lang.String evaluateQuality) throws wt.util.WTPropertyVetoException {
      evaluateQualityValidate(evaluateQuality);
      this.evaluateQuality = evaluateQuality;
   }
   void evaluateQualityValidate(java.lang.String evaluateQuality) throws wt.util.WTPropertyVetoException {
      if (EVALUATE_QUALITY_UPPER_LIMIT < 1) {
         try { EVALUATE_QUALITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("evaluateQuality").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EVALUATE_QUALITY_UPPER_LIMIT = 200; }
      }
      if (evaluateQuality != null && !wt.fc.PersistenceHelper.checkStoredLength(evaluateQuality.toString(), EVALUATE_QUALITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "evaluateQuality"), java.lang.String.valueOf(java.lang.Math.min(EVALUATE_QUALITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "evaluateQuality", this.evaluateQuality, evaluateQuality));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String EVALUATE_COST = "evaluateCost";
   static int EVALUATE_COST_UPPER_LIMIT = -1;
   java.lang.String evaluateCost;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getEvaluateCost() {
      return evaluateCost;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setEvaluateCost(java.lang.String evaluateCost) throws wt.util.WTPropertyVetoException {
      evaluateCostValidate(evaluateCost);
      this.evaluateCost = evaluateCost;
   }
   void evaluateCostValidate(java.lang.String evaluateCost) throws wt.util.WTPropertyVetoException {
      if (EVALUATE_COST_UPPER_LIMIT < 1) {
         try { EVALUATE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("evaluateCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EVALUATE_COST_UPPER_LIMIT = 200; }
      }
      if (evaluateCost != null && !wt.fc.PersistenceHelper.checkStoredLength(evaluateCost.toString(), EVALUATE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "evaluateCost"), java.lang.String.valueOf(java.lang.Math.min(EVALUATE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "evaluateCost", this.evaluateCost, evaluateCost));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String EVALUATE_DELIVERY1 = "evaluateDelivery1";
   static int EVALUATE_DELIVERY1_UPPER_LIMIT = -1;
   java.lang.String evaluateDelivery1;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getEvaluateDelivery1() {
      return evaluateDelivery1;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setEvaluateDelivery1(java.lang.String evaluateDelivery1) throws wt.util.WTPropertyVetoException {
      evaluateDelivery1Validate(evaluateDelivery1);
      this.evaluateDelivery1 = evaluateDelivery1;
   }
   void evaluateDelivery1Validate(java.lang.String evaluateDelivery1) throws wt.util.WTPropertyVetoException {
      if (EVALUATE_DELIVERY1_UPPER_LIMIT < 1) {
         try { EVALUATE_DELIVERY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("evaluateDelivery1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EVALUATE_DELIVERY1_UPPER_LIMIT = 200; }
      }
      if (evaluateDelivery1 != null && !wt.fc.PersistenceHelper.checkStoredLength(evaluateDelivery1.toString(), EVALUATE_DELIVERY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "evaluateDelivery1"), java.lang.String.valueOf(java.lang.Math.min(EVALUATE_DELIVERY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "evaluateDelivery1", this.evaluateDelivery1, evaluateDelivery1));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String EVALUATE_DELIVERY2 = "evaluateDelivery2";
   static int EVALUATE_DELIVERY2_UPPER_LIMIT = -1;
   java.lang.String evaluateDelivery2;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getEvaluateDelivery2() {
      return evaluateDelivery2;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setEvaluateDelivery2(java.lang.String evaluateDelivery2) throws wt.util.WTPropertyVetoException {
      evaluateDelivery2Validate(evaluateDelivery2);
      this.evaluateDelivery2 = evaluateDelivery2;
   }
   void evaluateDelivery2Validate(java.lang.String evaluateDelivery2) throws wt.util.WTPropertyVetoException {
      if (EVALUATE_DELIVERY2_UPPER_LIMIT < 1) {
         try { EVALUATE_DELIVERY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("evaluateDelivery2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EVALUATE_DELIVERY2_UPPER_LIMIT = 200; }
      }
      if (evaluateDelivery2 != null && !wt.fc.PersistenceHelper.checkStoredLength(evaluateDelivery2.toString(), EVALUATE_DELIVERY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "evaluateDelivery2"), java.lang.String.valueOf(java.lang.Math.min(EVALUATE_DELIVERY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "evaluateDelivery2", this.evaluateDelivery2, evaluateDelivery2));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String EVALUATE_DELIVERY3 = "evaluateDelivery3";
   static int EVALUATE_DELIVERY3_UPPER_LIMIT = -1;
   java.lang.String evaluateDelivery3;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getEvaluateDelivery3() {
      return evaluateDelivery3;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setEvaluateDelivery3(java.lang.String evaluateDelivery3) throws wt.util.WTPropertyVetoException {
      evaluateDelivery3Validate(evaluateDelivery3);
      this.evaluateDelivery3 = evaluateDelivery3;
   }
   void evaluateDelivery3Validate(java.lang.String evaluateDelivery3) throws wt.util.WTPropertyVetoException {
      if (EVALUATE_DELIVERY3_UPPER_LIMIT < 1) {
         try { EVALUATE_DELIVERY3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("evaluateDelivery3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EVALUATE_DELIVERY3_UPPER_LIMIT = 200; }
      }
      if (evaluateDelivery3 != null && !wt.fc.PersistenceHelper.checkStoredLength(evaluateDelivery3.toString(), EVALUATE_DELIVERY3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "evaluateDelivery3"), java.lang.String.valueOf(java.lang.Math.min(EVALUATE_DELIVERY3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "evaluateDelivery3", this.evaluateDelivery3, evaluateDelivery3));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String EVALUATE_TECHNICAL = "evaluateTechnical";
   static int EVALUATE_TECHNICAL_UPPER_LIMIT = -1;
   java.lang.String evaluateTechnical;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getEvaluateTechnical() {
      return evaluateTechnical;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setEvaluateTechnical(java.lang.String evaluateTechnical) throws wt.util.WTPropertyVetoException {
      evaluateTechnicalValidate(evaluateTechnical);
      this.evaluateTechnical = evaluateTechnical;
   }
   void evaluateTechnicalValidate(java.lang.String evaluateTechnical) throws wt.util.WTPropertyVetoException {
      if (EVALUATE_TECHNICAL_UPPER_LIMIT < 1) {
         try { EVALUATE_TECHNICAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("evaluateTechnical").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EVALUATE_TECHNICAL_UPPER_LIMIT = 200; }
      }
      if (evaluateTechnical != null && !wt.fc.PersistenceHelper.checkStoredLength(evaluateTechnical.toString(), EVALUATE_TECHNICAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "evaluateTechnical"), java.lang.String.valueOf(java.lang.Math.min(EVALUATE_TECHNICAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "evaluateTechnical", this.evaluateTechnical, evaluateTechnical));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String DESC_MSG = "descMsg";
   static int DESC_MSG_UPPER_LIMIT = -1;
   java.lang.String descMsg;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getDescMsg() {
      return descMsg;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setDescMsg(java.lang.String descMsg) throws wt.util.WTPropertyVetoException {
      descMsgValidate(descMsg);
      this.descMsg = descMsg;
   }
   void descMsgValidate(java.lang.String descMsg) throws wt.util.WTPropertyVetoException {
      if (DESC_MSG_UPPER_LIMIT < 1) {
         try { DESC_MSG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("descMsg").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESC_MSG_UPPER_LIMIT = 6000; }
      }
      if (descMsg != null && !wt.fc.PersistenceHelper.checkStoredLength(descMsg.toString(), DESC_MSG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "descMsg"), java.lang.String.valueOf(java.lang.Math.min(DESC_MSG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "descMsg", this.descMsg, descMsg));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String KEY_NO = "keyNo";
   static int KEY_NO_UPPER_LIMIT = -1;
   java.lang.String keyNo;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getKeyNo() {
      return keyNo;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setKeyNo(java.lang.String keyNo) throws wt.util.WTPropertyVetoException {
      keyNoValidate(keyNo);
      this.keyNo = keyNo;
   }
   void keyNoValidate(java.lang.String keyNo) throws wt.util.WTPropertyVetoException {
      if (KEY_NO_UPPER_LIMIT < 1) {
         try { KEY_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("keyNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KEY_NO_UPPER_LIMIT = 200; }
      }
      if (keyNo != null && !wt.fc.PersistenceHelper.checkStoredLength(keyNo.toString(), KEY_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "keyNo"), java.lang.String.valueOf(java.lang.Math.min(KEY_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "keyNo", this.keyNo, keyNo));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RANK_ONE = "rankOne";
   static int RANK_ONE_UPPER_LIMIT = -1;
   java.lang.String rankOne;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getRankOne() {
      return rankOne;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setRankOne(java.lang.String rankOne) throws wt.util.WTPropertyVetoException {
      rankOneValidate(rankOne);
      this.rankOne = rankOne;
   }
   void rankOneValidate(java.lang.String rankOne) throws wt.util.WTPropertyVetoException {
      if (RANK_ONE_UPPER_LIMIT < 1) {
         try { RANK_ONE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rankOne").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RANK_ONE_UPPER_LIMIT = 200; }
      }
      if (rankOne != null && !wt.fc.PersistenceHelper.checkStoredLength(rankOne.toString(), RANK_ONE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rankOne"), java.lang.String.valueOf(java.lang.Math.min(RANK_ONE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rankOne", this.rankOne, rankOne));
   }

   /**
    * @see e3ps.project.Performance
    */
   public static final java.lang.String RANK_TWO = "rankTwo";
   static int RANK_TWO_UPPER_LIMIT = -1;
   java.lang.String rankTwo;
   /**
    * @see e3ps.project.Performance
    */
   public java.lang.String getRankTwo() {
      return rankTwo;
   }
   /**
    * @see e3ps.project.Performance
    */
   public void setRankTwo(java.lang.String rankTwo) throws wt.util.WTPropertyVetoException {
      rankTwoValidate(rankTwo);
      this.rankTwo = rankTwo;
   }
   void rankTwoValidate(java.lang.String rankTwo) throws wt.util.WTPropertyVetoException {
      if (RANK_TWO_UPPER_LIMIT < 1) {
         try { RANK_TWO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rankTwo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RANK_TWO_UPPER_LIMIT = 200; }
      }
      if (rankTwo != null && !wt.fc.PersistenceHelper.checkStoredLength(rankTwo.toString(), RANK_TWO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rankTwo"), java.lang.String.valueOf(java.lang.Math.min(RANK_TWO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rankTwo", this.rankTwo, rankTwo));
   }

   /**
    * Derived from {@link wt.inf.container.WTContainerRef#getName()}
    *
    * @see wt.inf.container.WTContained
    */
   public java.lang.String getContainerName() {
      try { return (java.lang.String) ((wt.inf.container.WTContainerRef) getContainerReference()).getName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   wt.inf.container.WTContainerRef containerReference;
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainer getContainer() {
      return (containerReference != null) ? (wt.inf.container.WTContainer) containerReference.getObject() : null;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainerRef getContainerReference() {
      return containerReference;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainer(wt.inf.container.WTContainer the_container) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setContainerReference(the_container == null ? null : wt.inf.container.WTContainerRef.newWTContainerRef((wt.inf.container.WTContainer) the_container));
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainerReference(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      containerReferenceValidate(the_containerReference);
      containerReference = (wt.inf.container.WTContainerRef) the_containerReference;
   }
   void containerReferenceValidate(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      if (the_containerReference == null || the_containerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference") },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
      if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
            !wt.inf.container.WTContainer.class.isAssignableFrom(the_containerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference"), "WTContainerRef" },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -5867864216913495888L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( containerReference );
      output.writeObject( descMsg );
      output.writeObject( evaluateCost );
      output.writeObject( evaluateDelivery1 );
      output.writeObject( evaluateDelivery2 );
      output.writeObject( evaluateDelivery3 );
      output.writeObject( evaluateQuality );
      output.writeObject( evaluateTechnical );
      output.writeObject( keyNo );
      output.writeObject( planCost );
      output.writeObject( planDelivery1 );
      output.writeObject( planDelivery2 );
      output.writeObject( planDelivery3 );
      output.writeObject( planQuality );
      output.writeObject( planTechnical );
      output.writeObject( rankOne );
      output.writeObject( rankTwo );
      output.writeObject( resultCost );
      output.writeObject( resultDelivery1 );
      output.writeObject( resultDelivery2 );
      output.writeObject( resultDelivery3 );
      output.writeObject( resultQuality );
      output.writeObject( resultTechnical );
      output.writeObject( scoreCost );
      output.writeObject( scoreDelivery1 );
      output.writeObject( scoreDelivery2 );
      output.writeObject( scoreDelivery3 );
      output.writeObject( scoreQuality );
      output.writeObject( scoreTechnical );
   }

   protected void super_writeExternal_Performance(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.Performance) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_Performance(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "descMsg", descMsg );
      output.setString( "evaluateCost", evaluateCost );
      output.setString( "evaluateDelivery1", evaluateDelivery1 );
      output.setString( "evaluateDelivery2", evaluateDelivery2 );
      output.setString( "evaluateDelivery3", evaluateDelivery3 );
      output.setString( "evaluateQuality", evaluateQuality );
      output.setString( "evaluateTechnical", evaluateTechnical );
      output.setString( "keyNo", keyNo );
      output.setString( "planCost", planCost );
      output.setString( "planDelivery1", planDelivery1 );
      output.setString( "planDelivery2", planDelivery2 );
      output.setString( "planDelivery3", planDelivery3 );
      output.setString( "planQuality", planQuality );
      output.setString( "planTechnical", planTechnical );
      output.setString( "rankOne", rankOne );
      output.setString( "rankTwo", rankTwo );
      output.setString( "resultCost", resultCost );
      output.setString( "resultDelivery1", resultDelivery1 );
      output.setString( "resultDelivery2", resultDelivery2 );
      output.setString( "resultDelivery3", resultDelivery3 );
      output.setString( "resultQuality", resultQuality );
      output.setString( "resultTechnical", resultTechnical );
      output.setString( "scoreCost", scoreCost );
      output.setString( "scoreDelivery1", scoreDelivery1 );
      output.setString( "scoreDelivery2", scoreDelivery2 );
      output.setString( "scoreDelivery3", scoreDelivery3 );
      output.setString( "scoreQuality", scoreQuality );
      output.setString( "scoreTechnical", scoreTechnical );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      descMsg = input.getString( "descMsg" );
      evaluateCost = input.getString( "evaluateCost" );
      evaluateDelivery1 = input.getString( "evaluateDelivery1" );
      evaluateDelivery2 = input.getString( "evaluateDelivery2" );
      evaluateDelivery3 = input.getString( "evaluateDelivery3" );
      evaluateQuality = input.getString( "evaluateQuality" );
      evaluateTechnical = input.getString( "evaluateTechnical" );
      keyNo = input.getString( "keyNo" );
      planCost = input.getString( "planCost" );
      planDelivery1 = input.getString( "planDelivery1" );
      planDelivery2 = input.getString( "planDelivery2" );
      planDelivery3 = input.getString( "planDelivery3" );
      planQuality = input.getString( "planQuality" );
      planTechnical = input.getString( "planTechnical" );
      rankOne = input.getString( "rankOne" );
      rankTwo = input.getString( "rankTwo" );
      resultCost = input.getString( "resultCost" );
      resultDelivery1 = input.getString( "resultDelivery1" );
      resultDelivery2 = input.getString( "resultDelivery2" );
      resultDelivery3 = input.getString( "resultDelivery3" );
      resultQuality = input.getString( "resultQuality" );
      resultTechnical = input.getString( "resultTechnical" );
      scoreCost = input.getString( "scoreCost" );
      scoreDelivery1 = input.getString( "scoreDelivery1" );
      scoreDelivery2 = input.getString( "scoreDelivery2" );
      scoreDelivery3 = input.getString( "scoreDelivery3" );
      scoreQuality = input.getString( "scoreQuality" );
      scoreTechnical = input.getString( "scoreTechnical" );
   }

   boolean readVersion_5867864216913495888L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      descMsg = (java.lang.String) input.readObject();
      evaluateCost = (java.lang.String) input.readObject();
      evaluateDelivery1 = (java.lang.String) input.readObject();
      evaluateDelivery2 = (java.lang.String) input.readObject();
      evaluateDelivery3 = (java.lang.String) input.readObject();
      evaluateQuality = (java.lang.String) input.readObject();
      evaluateTechnical = (java.lang.String) input.readObject();
      keyNo = (java.lang.String) input.readObject();
      planCost = (java.lang.String) input.readObject();
      planDelivery1 = (java.lang.String) input.readObject();
      planDelivery2 = (java.lang.String) input.readObject();
      planDelivery3 = (java.lang.String) input.readObject();
      planQuality = (java.lang.String) input.readObject();
      planTechnical = (java.lang.String) input.readObject();
      rankOne = (java.lang.String) input.readObject();
      rankTwo = (java.lang.String) input.readObject();
      resultCost = (java.lang.String) input.readObject();
      resultDelivery1 = (java.lang.String) input.readObject();
      resultDelivery2 = (java.lang.String) input.readObject();
      resultDelivery3 = (java.lang.String) input.readObject();
      resultQuality = (java.lang.String) input.readObject();
      resultTechnical = (java.lang.String) input.readObject();
      scoreCost = (java.lang.String) input.readObject();
      scoreDelivery1 = (java.lang.String) input.readObject();
      scoreDelivery2 = (java.lang.String) input.readObject();
      scoreDelivery3 = (java.lang.String) input.readObject();
      scoreQuality = (java.lang.String) input.readObject();
      scoreTechnical = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( Performance thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_5867864216913495888L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_Performance( _Performance thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
