package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesTask implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesTask.class.getName();

   /**
    * Subject
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String SUBJECT = "subject";
   static int SUBJECT_UPPER_LIMIT = -1;
   java.lang.String subject;
   /**
    * Subject
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.String getSubject() {
      return subject;
   }
   /**
    * Subject
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setSubject(java.lang.String subject) throws wt.util.WTPropertyVetoException {
      subjectValidate(subject);
      this.subject = subject;
   }
   void subjectValidate(java.lang.String subject) throws wt.util.WTPropertyVetoException {
      if (SUBJECT_UPPER_LIMIT < 1) {
         try { SUBJECT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subject").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUBJECT_UPPER_LIMIT = 4000; }
      }
      if (subject != null && !wt.fc.PersistenceHelper.checkStoredLength(subject.toString(), SUBJECT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subject"), java.lang.String.valueOf(java.lang.Math.min(SUBJECT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subject", this.subject, subject));
   }

   /**
    * 영업추진계획
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String SALES_PLAN = "salesPlan";
   static int SALES_PLAN_UPPER_LIMIT = -1;
   java.lang.String salesPlan;
   /**
    * 영업추진계획
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.String getSalesPlan() {
      return salesPlan;
   }
   /**
    * 영업추진계획
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setSalesPlan(java.lang.String salesPlan) throws wt.util.WTPropertyVetoException {
      salesPlanValidate(salesPlan);
      this.salesPlan = salesPlan;
   }
   void salesPlanValidate(java.lang.String salesPlan) throws wt.util.WTPropertyVetoException {
      if (SALES_PLAN_UPPER_LIMIT < 1) {
         try { SALES_PLAN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesPlan").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_PLAN_UPPER_LIMIT = 4000; }
      }
      if (salesPlan != null && !wt.fc.PersistenceHelper.checkStoredLength(salesPlan.toString(), SALES_PLAN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesPlan"), java.lang.String.valueOf(java.lang.Math.min(SALES_PLAN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesPlan", this.salesPlan, salesPlan));
   }

   /**
    * 계획일자
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * 계획일자
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * 계획일자
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 실적일자
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String RESULT_DATE = "resultDate";
   java.sql.Timestamp resultDate;
   /**
    * 실적일자
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.sql.Timestamp getResultDate() {
      return resultDate;
   }
   /**
    * 실적일자
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setResultDate(java.sql.Timestamp resultDate) throws wt.util.WTPropertyVetoException {
      resultDateValidate(resultDate);
      this.resultDate = resultDate;
   }
   void resultDateValidate(java.sql.Timestamp resultDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 진행상황(G,Y,K)
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PLAN_CHECK = "planCheck";
   static int PLAN_CHECK_UPPER_LIMIT = -1;
   java.lang.String planCheck;
   /**
    * 진행상황(G,Y,K)
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.String getPlanCheck() {
      return planCheck;
   }
   /**
    * 진행상황(G,Y,K)
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setPlanCheck(java.lang.String planCheck) throws wt.util.WTPropertyVetoException {
      planCheckValidate(planCheck);
      this.planCheck = planCheck;
   }
   void planCheckValidate(java.lang.String planCheck) throws wt.util.WTPropertyVetoException {
      if (PLAN_CHECK_UPPER_LIMIT < 1) {
         try { PLAN_CHECK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planCheck").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_CHECK_UPPER_LIMIT = 200; }
      }
      if (planCheck != null && !wt.fc.PersistenceHelper.checkStoredLength(planCheck.toString(), PLAN_CHECK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planCheck"), java.lang.String.valueOf(java.lang.Math.min(PLAN_CHECK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planCheck", this.planCheck, planCheck));
   }

   /**
    * 진행현황
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PROPELWEB_EDITOR = "propelwebEditor";
   java.lang.Object propelwebEditor;
   /**
    * 진행현황
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.Object getPropelwebEditor() {
      return propelwebEditor;
   }
   /**
    * 진행현황
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setPropelwebEditor(java.lang.Object propelwebEditor) throws wt.util.WTPropertyVetoException {
      propelwebEditorValidate(propelwebEditor);
      this.propelwebEditor = propelwebEditor;
   }
   void propelwebEditorValidate(java.lang.Object propelwebEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PROPELWEB_EDITOR_TEXT = "propelwebEditorText";
   java.lang.Object propelwebEditorText;
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.Object getPropelwebEditorText() {
      return propelwebEditorText;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setPropelwebEditorText(java.lang.Object propelwebEditorText) throws wt.util.WTPropertyVetoException {
      propelwebEditorTextValidate(propelwebEditorText);
      this.propelwebEditorText = propelwebEditorText;
   }
   void propelwebEditorTextValidate(java.lang.Object propelwebEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 문제점
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PROBLEMWEB_EDITOR = "problemwebEditor";
   java.lang.Object problemwebEditor;
   /**
    * 문제점
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.Object getProblemwebEditor() {
      return problemwebEditor;
   }
   /**
    * 문제점
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setProblemwebEditor(java.lang.Object problemwebEditor) throws wt.util.WTPropertyVetoException {
      problemwebEditorValidate(problemwebEditor);
      this.problemwebEditor = problemwebEditor;
   }
   void problemwebEditorValidate(java.lang.Object problemwebEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 문제점
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PROBLEMWEB_EDITOR_TEXT = "problemwebEditorText";
   java.lang.Object problemwebEditorText;
   /**
    * 문제점
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.Object getProblemwebEditorText() {
      return problemwebEditorText;
   }
   /**
    * 문제점
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setProblemwebEditorText(java.lang.Object problemwebEditorText) throws wt.util.WTPropertyVetoException {
      problemwebEditorTextValidate(problemwebEditorText);
      this.problemwebEditorText = problemwebEditorText;
   }
   void problemwebEditorTextValidate(java.lang.Object problemwebEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 해결방안
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PLANWEB_EDITOR = "planwebEditor";
   java.lang.Object planwebEditor;
   /**
    * 해결방안
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.Object getPlanwebEditor() {
      return planwebEditor;
   }
   /**
    * 해결방안
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setPlanwebEditor(java.lang.Object planwebEditor) throws wt.util.WTPropertyVetoException {
      planwebEditorValidate(planwebEditor);
      this.planwebEditor = planwebEditor;
   }
   void planwebEditorValidate(java.lang.Object planwebEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String PLANWEB_EDITOR_TEXT = "planwebEditorText";
   java.lang.Object planwebEditorText;
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.Object getPlanwebEditorText() {
      return planwebEditorText;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setPlanwebEditorText(java.lang.Object planwebEditorText) throws wt.util.WTPropertyVetoException {
      planwebEditorTextValidate(planwebEditorText);
      this.planwebEditorText = planwebEditorText;
   }
   void planwebEditorTextValidate(java.lang.Object planwebEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * stepNo(sort)
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String STEP_NO = "stepNo";
   static int STEP_NO_UPPER_LIMIT = -1;
   java.lang.String stepNo;
   /**
    * stepNo(sort)
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.String getStepNo() {
      return stepNo;
   }
   /**
    * stepNo(sort)
    *
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setStepNo(java.lang.String stepNo) throws wt.util.WTPropertyVetoException {
      stepNoValidate(stepNo);
      this.stepNo = stepNo;
   }
   void stepNoValidate(java.lang.String stepNo) throws wt.util.WTPropertyVetoException {
      if (STEP_NO_UPPER_LIMIT < 1) {
         try { STEP_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("stepNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STEP_NO_UPPER_LIMIT = 200; }
      }
      if (stepNo != null && !wt.fc.PersistenceHelper.checkStoredLength(stepNo.toString(), STEP_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "stepNo"), java.lang.String.valueOf(java.lang.Math.min(STEP_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "stepNo", this.stepNo, stepNo));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String TASK_TYPE = "taskType";
   static int TASK_TYPE_UPPER_LIMIT = -1;
   java.lang.String taskType;
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.String getTaskType() {
      return taskType;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setTaskType(java.lang.String taskType) throws wt.util.WTPropertyVetoException {
      taskTypeValidate(taskType);
      this.taskType = taskType;
   }
   void taskTypeValidate(java.lang.String taskType) throws wt.util.WTPropertyVetoException {
      if (TASK_TYPE_UPPER_LIMIT < 1) {
         try { TASK_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_TYPE_UPPER_LIMIT = 200; }
      }
      if (taskType != null && !wt.fc.PersistenceHelper.checkStoredLength(taskType.toString(), TASK_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskType"), java.lang.String.valueOf(java.lang.Math.min(TASK_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskType", this.taskType, taskType));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String SALES_SUBJECT_CODE = "salesSubjectCode";
   static int SALES_SUBJECT_CODE_UPPER_LIMIT = -1;
   java.lang.String salesSubjectCode;
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public java.lang.String getSalesSubjectCode() {
      return salesSubjectCode;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setSalesSubjectCode(java.lang.String salesSubjectCode) throws wt.util.WTPropertyVetoException {
      salesSubjectCodeValidate(salesSubjectCode);
      this.salesSubjectCode = salesSubjectCode;
   }
   void salesSubjectCodeValidate(java.lang.String salesSubjectCode) throws wt.util.WTPropertyVetoException {
      if (SALES_SUBJECT_CODE_UPPER_LIMIT < 1) {
         try { SALES_SUBJECT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesSubjectCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_SUBJECT_CODE_UPPER_LIMIT = 200; }
      }
      if (salesSubjectCode != null && !wt.fc.PersistenceHelper.checkStoredLength(salesSubjectCode.toString(), SALES_SUBJECT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesSubjectCode"), java.lang.String.valueOf(java.lang.Math.min(SALES_SUBJECT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesSubjectCode", this.salesSubjectCode, salesSubjectCode));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String COLLABO_TEAM = "collaboTeam";
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String COLLABO_TEAM_REFERENCE = "collaboTeamReference";
   wt.fc.ObjectReference collaboTeamReference;
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public e3ps.groupware.company.Department getCollaboTeam() {
      return (collaboTeamReference != null) ? (e3ps.groupware.company.Department) collaboTeamReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public wt.fc.ObjectReference getCollaboTeamReference() {
      return collaboTeamReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setCollaboTeam(e3ps.groupware.company.Department the_collaboTeam) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCollaboTeamReference(the_collaboTeam == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_collaboTeam));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setCollaboTeamReference(wt.fc.ObjectReference the_collaboTeamReference) throws wt.util.WTPropertyVetoException {
      collaboTeamReferenceValidate(the_collaboTeamReference);
      collaboTeamReference = (wt.fc.ObjectReference) the_collaboTeamReference;
   }
   void collaboTeamReferenceValidate(wt.fc.ObjectReference the_collaboTeamReference) throws wt.util.WTPropertyVetoException {
      if (the_collaboTeamReference == null || the_collaboTeamReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collaboTeamReference") },
               new java.beans.PropertyChangeEvent(this, "collaboTeamReference", this.collaboTeamReference, collaboTeamReference));
      if (the_collaboTeamReference != null && the_collaboTeamReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_collaboTeamReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collaboTeamReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "collaboTeamReference", this.collaboTeamReference, collaboTeamReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String COLLABO_MANAGER = "collaboManager";
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public static final java.lang.String COLLABO_MANAGER_REFERENCE = "collaboManagerReference";
   wt.fc.ObjectReference collaboManagerReference;
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public wt.org.WTUser getCollaboManager() {
      return (collaboManagerReference != null) ? (wt.org.WTUser) collaboManagerReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public wt.fc.ObjectReference getCollaboManagerReference() {
      return collaboManagerReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setCollaboManager(wt.org.WTUser the_collaboManager) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCollaboManagerReference(the_collaboManager == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_collaboManager));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesTask
    */
   public void setCollaboManagerReference(wt.fc.ObjectReference the_collaboManagerReference) throws wt.util.WTPropertyVetoException {
      collaboManagerReferenceValidate(the_collaboManagerReference);
      collaboManagerReference = (wt.fc.ObjectReference) the_collaboManagerReference;
   }
   void collaboManagerReferenceValidate(wt.fc.ObjectReference the_collaboManagerReference) throws wt.util.WTPropertyVetoException {
      if (the_collaboManagerReference == null || the_collaboManagerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collaboManagerReference") },
               new java.beans.PropertyChangeEvent(this, "collaboManagerReference", this.collaboManagerReference, collaboManagerReference));
      if (the_collaboManagerReference != null && the_collaboManagerReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_collaboManagerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collaboManagerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "collaboManagerReference", this.collaboManagerReference, collaboManagerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 8276328507244014640L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( collaboManagerReference );
      output.writeObject( collaboTeamReference );
      output.writeObject( planCheck );
      output.writeObject( planDate );
      output.writeObject( planwebEditor );
      output.writeObject( planwebEditorText );
      output.writeObject( problemwebEditor );
      output.writeObject( problemwebEditorText );
      output.writeObject( propelwebEditor );
      output.writeObject( propelwebEditorText );
      output.writeObject( resultDate );
      output.writeObject( salesPlan );
      output.writeObject( salesSubjectCode );
      output.writeObject( stepNo );
      output.writeObject( subject );
      output.writeObject( taskType );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesTask) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "collaboManagerReference", collaboManagerReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "collaboTeamReference", collaboTeamReference, wt.fc.ObjectReference.class, true );
      output.setString( "planCheck", planCheck );
      output.setTimestamp( "planDate", planDate );
      output.setObject( "planwebEditor", planwebEditor );
      output.setObject( "planwebEditorText", planwebEditorText );
      output.setObject( "problemwebEditor", problemwebEditor );
      output.setObject( "problemwebEditorText", problemwebEditorText );
      output.setObject( "propelwebEditor", propelwebEditor );
      output.setObject( "propelwebEditorText", propelwebEditorText );
      output.setTimestamp( "resultDate", resultDate );
      output.setString( "salesPlan", salesPlan );
      output.setString( "salesSubjectCode", salesSubjectCode );
      output.setString( "stepNo", stepNo );
      output.setString( "subject", subject );
      output.setString( "taskType", taskType );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      collaboManagerReference = (wt.fc.ObjectReference) input.readObject( "collaboManagerReference", collaboManagerReference, wt.fc.ObjectReference.class, true );
      collaboTeamReference = (wt.fc.ObjectReference) input.readObject( "collaboTeamReference", collaboTeamReference, wt.fc.ObjectReference.class, true );
      planCheck = input.getString( "planCheck" );
      planDate = input.getTimestamp( "planDate" );
      planwebEditor = (java.lang.Object) input.getObject( "planwebEditor" );
      planwebEditorText = (java.lang.Object) input.getObject( "planwebEditorText" );
      problemwebEditor = (java.lang.Object) input.getObject( "problemwebEditor" );
      problemwebEditorText = (java.lang.Object) input.getObject( "problemwebEditorText" );
      propelwebEditor = (java.lang.Object) input.getObject( "propelwebEditor" );
      propelwebEditorText = (java.lang.Object) input.getObject( "propelwebEditorText" );
      resultDate = input.getTimestamp( "resultDate" );
      salesPlan = input.getString( "salesPlan" );
      salesSubjectCode = input.getString( "salesSubjectCode" );
      stepNo = input.getString( "stepNo" );
      subject = input.getString( "subject" );
      taskType = input.getString( "taskType" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion8276328507244014640L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      collaboManagerReference = (wt.fc.ObjectReference) input.readObject();
      collaboTeamReference = (wt.fc.ObjectReference) input.readObject();
      planCheck = (java.lang.String) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      planwebEditor = (java.lang.Object) input.readObject();
      planwebEditorText = (java.lang.Object) input.readObject();
      problemwebEditor = (java.lang.Object) input.readObject();
      problemwebEditorText = (java.lang.Object) input.readObject();
      propelwebEditor = (java.lang.Object) input.readObject();
      propelwebEditorText = (java.lang.Object) input.readObject();
      resultDate = (java.sql.Timestamp) input.readObject();
      salesPlan = (java.lang.String) input.readObject();
      salesSubjectCode = (java.lang.String) input.readObject();
      stepNo = (java.lang.String) input.readObject();
      subject = (java.lang.String) input.readObject();
      taskType = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETSalesTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8276328507244014640L( input, readSerialVersionUID, superDone );
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
