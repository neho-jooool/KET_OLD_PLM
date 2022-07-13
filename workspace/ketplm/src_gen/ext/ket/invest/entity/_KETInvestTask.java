package ext.ket.invest.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETInvestTask extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.invest.entity.entityResource";
   static final java.lang.String CLASSNAME = KETInvestTask.class.getName();

   /**
    * Task구분(영업,관련부서)
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String TASK_CODE = "taskCode";
   static int TASK_CODE_UPPER_LIMIT = -1;
   java.lang.String taskCode;
   /**
    * Task구분(영업,관련부서)
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.String getTaskCode() {
      return taskCode;
   }
   /**
    * Task구분(영업,관련부서)
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setTaskCode(java.lang.String taskCode) throws wt.util.WTPropertyVetoException {
      taskCodeValidate(taskCode);
      this.taskCode = taskCode;
   }
   void taskCodeValidate(java.lang.String taskCode) throws wt.util.WTPropertyVetoException {
      if (TASK_CODE_UPPER_LIMIT < 1) {
         try { TASK_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_CODE_UPPER_LIMIT = 200; }
      }
      if (taskCode != null && !wt.fc.PersistenceHelper.checkStoredLength(taskCode.toString(), TASK_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskCode"), java.lang.String.valueOf(java.lang.Math.min(TASK_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskCode", this.taskCode, taskCode));
   }

   /**
    * 요청항목
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String SUBJECT = "subject";
   static int SUBJECT_UPPER_LIMIT = -1;
   java.lang.String subject;
   /**
    * 요청항목
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.String getSubject() {
      return subject;
   }
   /**
    * 요청항목
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setSubject(java.lang.String subject) throws wt.util.WTPropertyVetoException {
      subjectValidate(subject);
      this.subject = subject;
   }
   void subjectValidate(java.lang.String subject) throws wt.util.WTPropertyVetoException {
      if (SUBJECT_UPPER_LIMIT < 1) {
         try { SUBJECT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subject").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUBJECT_UPPER_LIMIT = 200; }
      }
      if (subject != null && !wt.fc.PersistenceHelper.checkStoredLength(subject.toString(), SUBJECT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subject"), java.lang.String.valueOf(java.lang.Math.min(SUBJECT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subject", this.subject, subject));
   }

   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String DEPT_CODE = "deptCode";
   static int DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String deptCode;
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.String getDeptCode() {
      return deptCode;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setDeptCode(java.lang.String deptCode) throws wt.util.WTPropertyVetoException {
      deptCodeValidate(deptCode);
      this.deptCode = deptCode;
   }
   void deptCodeValidate(java.lang.String deptCode) throws wt.util.WTPropertyVetoException {
      if (DEPT_CODE_UPPER_LIMIT < 1) {
         try { DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (deptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(deptCode.toString(), DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptCode"), java.lang.String.valueOf(java.lang.Math.min(DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptCode", this.deptCode, deptCode));
   }

   /**
    * 완료요청일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * 완료요청일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * 완료요청일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 완료일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String COMPLETE_DATE = "completeDate";
   java.sql.Timestamp completeDate;
   /**
    * 완료일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.sql.Timestamp getCompleteDate() {
      return completeDate;
   }
   /**
    * 완료일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setCompleteDate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
      completeDateValidate(completeDate);
      this.completeDate = completeDate;
   }
   void completeDateValidate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 상태
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String INVEST_STATE = "investState";
   static int INVEST_STATE_UPPER_LIMIT = -1;
   java.lang.String investState;
   /**
    * 상태
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.String getInvestState() {
      return investState;
   }
   /**
    * 상태
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setInvestState(java.lang.String investState) throws wt.util.WTPropertyVetoException {
      investStateValidate(investState);
      this.investState = investState;
   }
   void investStateValidate(java.lang.String investState) throws wt.util.WTPropertyVetoException {
      if (INVEST_STATE_UPPER_LIMIT < 1) {
         try { INVEST_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_STATE_UPPER_LIMIT = 200; }
      }
      if (investState != null && !wt.fc.PersistenceHelper.checkStoredLength(investState.toString(), INVEST_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investState"), java.lang.String.valueOf(java.lang.Math.min(INVEST_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investState", this.investState, investState));
   }

   /**
    * 진행요청일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String PROGRESS_DATE = "progressDate";
   java.sql.Timestamp progressDate;
   /**
    * 진행요청일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.sql.Timestamp getProgressDate() {
      return progressDate;
   }
   /**
    * 진행요청일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setProgressDate(java.sql.Timestamp progressDate) throws wt.util.WTPropertyVetoException {
      progressDateValidate(progressDate);
      this.progressDate = progressDate;
   }
   void progressDateValidate(java.sql.Timestamp progressDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 회수비구분
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String COLLECT_CODE = "collectCode";
   static int COLLECT_CODE_UPPER_LIMIT = -1;
   java.lang.String collectCode;
   /**
    * 회수비구분
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.String getCollectCode() {
      return collectCode;
   }
   /**
    * 회수비구분
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setCollectCode(java.lang.String collectCode) throws wt.util.WTPropertyVetoException {
      collectCodeValidate(collectCode);
      this.collectCode = collectCode;
   }
   void collectCodeValidate(java.lang.String collectCode) throws wt.util.WTPropertyVetoException {
      if (COLLECT_CODE_UPPER_LIMIT < 1) {
         try { COLLECT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("collectCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLLECT_CODE_UPPER_LIMIT = 200; }
      }
      if (collectCode != null && !wt.fc.PersistenceHelper.checkStoredLength(collectCode.toString(), COLLECT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collectCode"), java.lang.String.valueOf(java.lang.Math.min(COLLECT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "collectCode", this.collectCode, collectCode));
   }

   /**
    * 회수비용
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String COLLECT_EXPENSE = "collectExpense";
   static int COLLECT_EXPENSE_UPPER_LIMIT = -1;
   java.lang.String collectExpense = "0";
   /**
    * 회수비용
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.String getCollectExpense() {
      return collectExpense;
   }
   /**
    * 회수비용
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setCollectExpense(java.lang.String collectExpense) throws wt.util.WTPropertyVetoException {
      collectExpenseValidate(collectExpense);
      this.collectExpense = collectExpense;
   }
   void collectExpenseValidate(java.lang.String collectExpense) throws wt.util.WTPropertyVetoException {
      if (COLLECT_EXPENSE_UPPER_LIMIT < 1) {
         try { COLLECT_EXPENSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("collectExpense").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLLECT_EXPENSE_UPPER_LIMIT = 200; }
      }
      if (collectExpense != null && !wt.fc.PersistenceHelper.checkStoredLength(collectExpense.toString(), COLLECT_EXPENSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collectExpense"), java.lang.String.valueOf(java.lang.Math.min(COLLECT_EXPENSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "collectExpense", this.collectExpense, collectExpense));
   }

   /**
    * 회수일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String COLLECT_DATE = "collectDate";
   java.sql.Timestamp collectDate;
   /**
    * 회수일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.sql.Timestamp getCollectDate() {
      return collectDate;
   }
   /**
    * 회수일자
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setCollectDate(java.sql.Timestamp collectDate) throws wt.util.WTPropertyVetoException {
      collectDateValidate(collectDate);
      this.collectDate = collectDate;
   }
   void collectDateValidate(java.sql.Timestamp collectDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 진행상세
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String PROGRESS_SUBJECT = "progressSubject";
   static int PROGRESS_SUBJECT_UPPER_LIMIT = -1;
   java.lang.String progressSubject;
   /**
    * 진행상세
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.String getProgressSubject() {
      return progressSubject;
   }
   /**
    * 진행상세
    *
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setProgressSubject(java.lang.String progressSubject) throws wt.util.WTPropertyVetoException {
      progressSubjectValidate(progressSubject);
      this.progressSubject = progressSubject;
   }
   void progressSubjectValidate(java.lang.String progressSubject) throws wt.util.WTPropertyVetoException {
      if (PROGRESS_SUBJECT_UPPER_LIMIT < 1) {
         try { PROGRESS_SUBJECT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("progressSubject").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROGRESS_SUBJECT_UPPER_LIMIT = 4000; }
      }
      if (progressSubject != null && !wt.fc.PersistenceHelper.checkStoredLength(progressSubject.toString(), PROGRESS_SUBJECT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "progressSubject"), java.lang.String.valueOf(java.lang.Math.min(PROGRESS_SUBJECT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "progressSubject", this.progressSubject, progressSubject));
   }

   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String SORT = "sort";
   java.lang.Integer sort = 0;
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public java.lang.Integer getSort() {
      return sort;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setSort(java.lang.Integer sort) throws wt.util.WTPropertyVetoException {
      sortValidate(sort);
      this.sort = sort;
   }
   void sortValidate(java.lang.Integer sort) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String WORKER = "worker";
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String WORKER_REFERENCE = "workerReference";
   wt.fc.ObjectReference workerReference;
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public wt.org.WTUser getWorker() {
      return (workerReference != null) ? (wt.org.WTUser) workerReference.getObject() : null;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public wt.fc.ObjectReference getWorkerReference() {
      return workerReference;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setWorker(wt.org.WTUser the_worker) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setWorkerReference(the_worker == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_worker));
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setWorkerReference(wt.fc.ObjectReference the_workerReference) throws wt.util.WTPropertyVetoException {
      workerReferenceValidate(the_workerReference);
      workerReference = (wt.fc.ObjectReference) the_workerReference;
   }
   void workerReferenceValidate(wt.fc.ObjectReference the_workerReference) throws wt.util.WTPropertyVetoException {
      if (the_workerReference == null || the_workerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workerReference") },
               new java.beans.PropertyChangeEvent(this, "workerReference", this.workerReference, workerReference));
      if (the_workerReference != null && the_workerReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_workerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "workerReference", this.workerReference, workerReference));
   }

   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String INVEST_MASTER = "investMaster";
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public static final java.lang.String INVEST_MASTER_REFERENCE = "investMasterReference";
   wt.fc.ObjectReference investMasterReference;
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public ext.ket.invest.entity.KETInvestMaster getInvestMaster() {
      return (investMasterReference != null) ? (ext.ket.invest.entity.KETInvestMaster) investMasterReference.getObject() : null;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public wt.fc.ObjectReference getInvestMasterReference() {
      return investMasterReference;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setInvestMaster(ext.ket.invest.entity.KETInvestMaster the_investMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setInvestMasterReference(the_investMaster == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.invest.entity.KETInvestMaster) the_investMaster));
   }
   /**
    * @see ext.ket.invest.entity.KETInvestTask
    */
   public void setInvestMasterReference(wt.fc.ObjectReference the_investMasterReference) throws wt.util.WTPropertyVetoException {
      investMasterReferenceValidate(the_investMasterReference);
      investMasterReference = (wt.fc.ObjectReference) the_investMasterReference;
   }
   void investMasterReferenceValidate(wt.fc.ObjectReference the_investMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_investMasterReference == null || the_investMasterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investMasterReference") },
               new java.beans.PropertyChangeEvent(this, "investMasterReference", this.investMasterReference, investMasterReference));
      if (the_investMasterReference != null && the_investMasterReference.getReferencedClass() != null &&
            !ext.ket.invest.entity.KETInvestMaster.class.isAssignableFrom(the_investMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "investMasterReference", this.investMasterReference, investMasterReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -7705510430945121037L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( collectCode );
      output.writeObject( collectDate );
      output.writeObject( collectExpense );
      output.writeObject( completeDate );
      output.writeObject( deptCode );
      output.writeObject( investMasterReference );
      output.writeObject( investState );
      output.writeObject( progressDate );
      output.writeObject( progressSubject );
      output.writeObject( requestDate );
      output.writeObject( sort );
      output.writeObject( subject );
      output.writeObject( taskCode );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );
      output.writeObject( workerReference );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETInvestTask(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.invest.entity.KETInvestTask) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETInvestTask(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "collectCode", collectCode );
      output.setTimestamp( "collectDate", collectDate );
      output.setString( "collectExpense", collectExpense );
      output.setTimestamp( "completeDate", completeDate );
      output.setString( "deptCode", deptCode );
      output.writeObject( "investMasterReference", investMasterReference, wt.fc.ObjectReference.class, true );
      output.setString( "investState", investState );
      output.setTimestamp( "progressDate", progressDate );
      output.setString( "progressSubject", progressSubject );
      output.setTimestamp( "requestDate", requestDate );
      output.setIntObject( "sort", sort );
      output.setString( "subject", subject );
      output.setString( "taskCode", taskCode );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
      output.writeObject( "workerReference", workerReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      collectCode = input.getString( "collectCode" );
      collectDate = input.getTimestamp( "collectDate" );
      collectExpense = input.getString( "collectExpense" );
      completeDate = input.getTimestamp( "completeDate" );
      deptCode = input.getString( "deptCode" );
      investMasterReference = (wt.fc.ObjectReference) input.readObject( "investMasterReference", investMasterReference, wt.fc.ObjectReference.class, true );
      investState = input.getString( "investState" );
      progressDate = input.getTimestamp( "progressDate" );
      progressSubject = input.getString( "progressSubject" );
      requestDate = input.getTimestamp( "requestDate" );
      sort = input.getIntObject( "sort" );
      subject = input.getString( "subject" );
      taskCode = input.getString( "taskCode" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
      workerReference = (wt.fc.ObjectReference) input.readObject( "workerReference", workerReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion_7705510430945121037L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      collectCode = (java.lang.String) input.readObject();
      collectDate = (java.sql.Timestamp) input.readObject();
      collectExpense = (java.lang.String) input.readObject();
      completeDate = (java.sql.Timestamp) input.readObject();
      deptCode = (java.lang.String) input.readObject();
      investMasterReference = (wt.fc.ObjectReference) input.readObject();
      investState = (java.lang.String) input.readObject();
      progressDate = (java.sql.Timestamp) input.readObject();
      progressSubject = (java.lang.String) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();
      sort = (java.lang.Integer) input.readObject();
      subject = (java.lang.String) input.readObject();
      taskCode = (java.lang.String) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();
      workerReference = (wt.fc.ObjectReference) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETInvestTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7705510430945121037L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETInvestTask( _KETInvestTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
