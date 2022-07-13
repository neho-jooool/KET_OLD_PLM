package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectIssue implements wt.content.ContentHolder, e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectIssue.class.getName();

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String SUBJECT = "subject";
   static int SUBJECT_UPPER_LIMIT = -1;
   java.lang.String subject;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getSubject() {
      return subject;
   }
   /**
    * @see e3ps.project.ProjectIssue
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
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String ANSWER = "answer";
   java.lang.Object answer;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.Object getAnswer() {
      return answer;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setAnswer(java.lang.Object answer) throws wt.util.WTPropertyVetoException {
      answerValidate(answer);
      this.answer = answer;
   }
   void answerValidate(java.lang.Object answer) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 완료예정일
    *
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String PLAN_DONE_DATE = "planDoneDate";
   java.sql.Timestamp planDoneDate;
   /**
    * 완료예정일
    *
    * @see e3ps.project.ProjectIssue
    */
   public java.sql.Timestamp getPlanDoneDate() {
      return planDoneDate;
   }
   /**
    * 완료예정일
    *
    * @see e3ps.project.ProjectIssue
    */
   public void setPlanDoneDate(java.sql.Timestamp planDoneDate) throws wt.util.WTPropertyVetoException {
      planDoneDateValidate(planDoneDate);
      this.planDoneDate = planDoneDate;
   }
   void planDoneDateValidate(java.sql.Timestamp planDoneDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 완료여부
    *
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String IS_DONE = "isDone";
   boolean isDone;
   /**
    * 완료여부
    *
    * @see e3ps.project.ProjectIssue
    */
   public boolean isIsDone() {
      return isDone;
   }
   /**
    * 완료여부
    *
    * @see e3ps.project.ProjectIssue
    */
   public void setIsDone(boolean isDone) throws wt.util.WTPropertyVetoException {
      isDoneValidate(isDone);
      this.isDone = isDone;
   }
   void isDoneValidate(boolean isDone) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String CREATE_DATE = "createDate";
   java.sql.Timestamp createDate;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.sql.Timestamp getCreateDate() {
      return createDate;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setCreateDate(java.sql.Timestamp createDate) throws wt.util.WTPropertyVetoException {
      createDateValidate(createDate);
      this.createDate = createDate;
   }
   void createDateValidate(java.sql.Timestamp createDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 긴급도
    *
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String URGENCY = "urgency";
   static int URGENCY_UPPER_LIMIT = -1;
   java.lang.String urgency;
   /**
    * 긴급도
    *
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getUrgency() {
      return urgency;
   }
   /**
    * 긴급도
    *
    * @see e3ps.project.ProjectIssue
    */
   public void setUrgency(java.lang.String urgency) throws wt.util.WTPropertyVetoException {
      urgencyValidate(urgency);
      this.urgency = urgency;
   }
   void urgencyValidate(java.lang.String urgency) throws wt.util.WTPropertyVetoException {
      if (URGENCY_UPPER_LIMIT < 1) {
         try { URGENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("urgency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { URGENCY_UPPER_LIMIT = 200; }
      }
      if (urgency != null && !wt.fc.PersistenceHelper.checkStoredLength(urgency.toString(), URGENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "urgency"), java.lang.String.valueOf(java.lang.Math.min(URGENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "urgency", this.urgency, urgency));
   }

   /**
    * 중요도
    *
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String IMPORTANCE = "importance";
   static int IMPORTANCE_UPPER_LIMIT = -1;
   java.lang.String importance;
   /**
    * 중요도
    *
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getImportance() {
      return importance;
   }
   /**
    * 중요도
    *
    * @see e3ps.project.ProjectIssue
    */
   public void setImportance(java.lang.String importance) throws wt.util.WTPropertyVetoException {
      importanceValidate(importance);
      this.importance = importance;
   }
   void importanceValidate(java.lang.String importance) throws wt.util.WTPropertyVetoException {
      if (IMPORTANCE_UPPER_LIMIT < 1) {
         try { IMPORTANCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("importance").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IMPORTANCE_UPPER_LIMIT = 200; }
      }
      if (importance != null && !wt.fc.PersistenceHelper.checkStoredLength(importance.toString(), IMPORTANCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "importance"), java.lang.String.valueOf(java.lang.Math.min(IMPORTANCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "importance", this.importance, importance));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String IS_ANSWER_DONE = "isAnswerDone";
   boolean isAnswerDone = false;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public boolean isIsAnswerDone() {
      return isAnswerDone;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setIsAnswerDone(boolean isAnswerDone) throws wt.util.WTPropertyVetoException {
      isAnswerDoneValidate(isAnswerDone);
      this.isAnswerDone = isAnswerDone;
   }
   void isAnswerDoneValidate(boolean isAnswerDone) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String ISSUE_ATTR1 = "issueAttr1";
   static int ISSUE_ATTR1_UPPER_LIMIT = -1;
   java.lang.String issueAttr1;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getIssueAttr1() {
      return issueAttr1;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setIssueAttr1(java.lang.String issueAttr1) throws wt.util.WTPropertyVetoException {
      issueAttr1Validate(issueAttr1);
      this.issueAttr1 = issueAttr1;
   }
   void issueAttr1Validate(java.lang.String issueAttr1) throws wt.util.WTPropertyVetoException {
      if (ISSUE_ATTR1_UPPER_LIMIT < 1) {
         try { ISSUE_ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("issueAttr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ISSUE_ATTR1_UPPER_LIMIT = 200; }
      }
      if (issueAttr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(issueAttr1.toString(), ISSUE_ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "issueAttr1"), java.lang.String.valueOf(java.lang.Math.min(ISSUE_ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "issueAttr1", this.issueAttr1, issueAttr1));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String ISSUE_ATTR2 = "issueAttr2";
   static int ISSUE_ATTR2_UPPER_LIMIT = -1;
   java.lang.String issueAttr2;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getIssueAttr2() {
      return issueAttr2;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setIssueAttr2(java.lang.String issueAttr2) throws wt.util.WTPropertyVetoException {
      issueAttr2Validate(issueAttr2);
      this.issueAttr2 = issueAttr2;
   }
   void issueAttr2Validate(java.lang.String issueAttr2) throws wt.util.WTPropertyVetoException {
      if (ISSUE_ATTR2_UPPER_LIMIT < 1) {
         try { ISSUE_ATTR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("issueAttr2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ISSUE_ATTR2_UPPER_LIMIT = 200; }
      }
      if (issueAttr2 != null && !wt.fc.PersistenceHelper.checkStoredLength(issueAttr2.toString(), ISSUE_ATTR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "issueAttr2"), java.lang.String.valueOf(java.lang.Math.min(ISSUE_ATTR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "issueAttr2", this.issueAttr2, issueAttr2));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String AEGIS_TRANS = "aegisTrans";
   static int AEGIS_TRANS_UPPER_LIMIT = -1;
   java.lang.String aegisTrans;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getAegisTrans() {
      return aegisTrans;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setAegisTrans(java.lang.String aegisTrans) throws wt.util.WTPropertyVetoException {
      aegisTransValidate(aegisTrans);
      this.aegisTrans = aegisTrans;
   }
   void aegisTransValidate(java.lang.String aegisTrans) throws wt.util.WTPropertyVetoException {
      if (AEGIS_TRANS_UPPER_LIMIT < 1) {
         try { AEGIS_TRANS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("aegisTrans").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AEGIS_TRANS_UPPER_LIMIT = 200; }
      }
      if (aegisTrans != null && !wt.fc.PersistenceHelper.checkStoredLength(aegisTrans.toString(), AEGIS_TRANS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "aegisTrans"), java.lang.String.valueOf(java.lang.Math.min(AEGIS_TRANS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "aegisTrans", this.aegisTrans, aegisTrans));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String AEGIS_STATUS = "aegisStatus";
   static int AEGIS_STATUS_UPPER_LIMIT = -1;
   java.lang.String aegisStatus;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getAegisStatus() {
      return aegisStatus;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setAegisStatus(java.lang.String aegisStatus) throws wt.util.WTPropertyVetoException {
      aegisStatusValidate(aegisStatus);
      this.aegisStatus = aegisStatus;
   }
   void aegisStatusValidate(java.lang.String aegisStatus) throws wt.util.WTPropertyVetoException {
      if (AEGIS_STATUS_UPPER_LIMIT < 1) {
         try { AEGIS_STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("aegisStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AEGIS_STATUS_UPPER_LIMIT = 200; }
      }
      if (aegisStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(aegisStatus.toString(), AEGIS_STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "aegisStatus"), java.lang.String.valueOf(java.lang.Math.min(AEGIS_STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "aegisStatus", this.aegisStatus, aegisStatus));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String AEGIS_COMMENT = "aegisComment";
   java.lang.Object aegisComment;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.Object getAegisComment() {
      return aegisComment;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setAegisComment(java.lang.Object aegisComment) throws wt.util.WTPropertyVetoException {
      aegisCommentValidate(aegisComment);
      this.aegisComment = aegisComment;
   }
   void aegisCommentValidate(java.lang.Object aegisComment) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String AEGIS_CUT_OFF_COMMENT = "aegisCutOffComment";
   static int AEGIS_CUT_OFF_COMMENT_UPPER_LIMIT = -1;
   java.lang.String aegisCutOffComment;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public java.lang.String getAegisCutOffComment() {
      return aegisCutOffComment;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setAegisCutOffComment(java.lang.String aegisCutOffComment) throws wt.util.WTPropertyVetoException {
      aegisCutOffCommentValidate(aegisCutOffComment);
      this.aegisCutOffComment = aegisCutOffComment;
   }
   void aegisCutOffCommentValidate(java.lang.String aegisCutOffComment) throws wt.util.WTPropertyVetoException {
      if (AEGIS_CUT_OFF_COMMENT_UPPER_LIMIT < 1) {
         try { AEGIS_CUT_OFF_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("aegisCutOffComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AEGIS_CUT_OFF_COMMENT_UPPER_LIMIT = 4000; }
      }
      if (aegisCutOffComment != null && !wt.fc.PersistenceHelper.checkStoredLength(aegisCutOffComment.toString(), AEGIS_CUT_OFF_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "aegisCutOffComment"), java.lang.String.valueOf(java.lang.Math.min(AEGIS_CUT_OFF_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "aegisCutOffComment", this.aegisCutOffComment, aegisCutOffComment));
   }

   /**
    * aegis이관일
    *
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String AEGIS_DATE = "aegisDate";
   java.sql.Timestamp aegisDate;
   /**
    * aegis이관일
    *
    * @see e3ps.project.ProjectIssue
    */
   public java.sql.Timestamp getAegisDate() {
      return aegisDate;
   }
   /**
    * aegis이관일
    *
    * @see e3ps.project.ProjectIssue
    */
   public void setAegisDate(java.sql.Timestamp aegisDate) throws wt.util.WTPropertyVetoException {
      aegisDateValidate(aegisDate);
      this.aegisDate = aegisDate;
   }
   void aegisDateValidate(java.sql.Timestamp aegisDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String ISSUE_TYPE = "issueType";
   e3ps.project.IssueType issueType;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public e3ps.project.IssueType getIssueType() {
      return issueType;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setIssueType(e3ps.project.IssueType issueType) throws wt.util.WTPropertyVetoException {
      issueTypeValidate(issueType);
      this.issueType = issueType;
   }
   void issueTypeValidate(e3ps.project.IssueType issueType) throws wt.util.WTPropertyVetoException {
      if (issueType == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "issueType") },
               new java.beans.PropertyChangeEvent(this, "issueType", this.issueType, issueType));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String MANAGER = "manager";
   wt.org.WTPrincipalReference manager;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public wt.org.WTPrincipalReference getManager() {
      return manager;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setManager(wt.org.WTPrincipalReference manager) throws wt.util.WTPropertyVetoException {
      managerValidate(manager);
      this.manager = manager;
   }
   void managerValidate(wt.org.WTPrincipalReference manager) throws wt.util.WTPropertyVetoException {
      if (manager == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manager") },
               new java.beans.PropertyChangeEvent(this, "manager", this.manager, manager));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public e3ps.project.E3PSProject getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setProject(e3ps.project.E3PSProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProject) the_project));
   }
   /**
    * @see e3ps.project.ProjectIssue
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
            !e3ps.project.E3PSProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String TASK = "task";
   /**
    * @see e3ps.project.ProjectIssue
    */
   public static final java.lang.String TASK_REFERENCE = "taskReference";
   wt.fc.ObjectReference taskReference;
   /**
    * @see e3ps.project.ProjectIssue
    */
   public e3ps.project.E3PSTask getTask() {
      return (taskReference != null) ? (e3ps.project.E3PSTask) taskReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public wt.fc.ObjectReference getTaskReference() {
      return taskReference;
   }
   /**
    * @see e3ps.project.ProjectIssue
    */
   public void setTask(e3ps.project.E3PSTask the_task) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTaskReference(the_task == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSTask) the_task));
   }
   /**
    * @see e3ps.project.ProjectIssue
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

   public static final long EXTERNALIZATION_VERSION_UID = -4172619985972941186L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( aegisComment );
      output.writeObject( aegisCutOffComment );
      output.writeObject( aegisDate );
      output.writeObject( aegisStatus );
      output.writeObject( aegisTrans );
      output.writeObject( answer );
      output.writeObject( createDate );
      output.writeObject( importance );
      output.writeBoolean( isAnswerDone );
      output.writeBoolean( isDone );
      output.writeObject( issueAttr1 );
      output.writeObject( issueAttr2 );
      output.writeObject( (issueType == null ? null : issueType.getStringValue()) );
      output.writeObject( manager );
      output.writeObject( owner );
      output.writeObject( planDoneDate );
      output.writeObject( projectReference );
      output.writeObject( subject );
      output.writeObject( taskReference );
      output.writeObject( thePersistInfo );
      output.writeObject( urgency );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectIssue) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setObject( "aegisComment", aegisComment );
      output.setString( "aegisCutOffComment", aegisCutOffComment );
      output.setTimestamp( "aegisDate", aegisDate );
      output.setString( "aegisStatus", aegisStatus );
      output.setString( "aegisTrans", aegisTrans );
      output.setObject( "answer", answer );
      output.setTimestamp( "createDate", createDate );
      output.setString( "importance", importance );
      output.setBoolean( "isAnswerDone", isAnswerDone );
      output.setBoolean( "isDone", isDone );
      output.setString( "issueAttr1", issueAttr1 );
      output.setString( "issueAttr2", issueAttr2 );
      output.setString( "issueType", (issueType == null ? null : issueType.toString()) );
      output.writeObject( "manager", manager, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setTimestamp( "planDoneDate", planDoneDate );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "subject", subject );
      output.writeObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "urgency", urgency );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      aegisComment = (java.lang.Object) input.getObject( "aegisComment" );
      aegisCutOffComment = input.getString( "aegisCutOffComment" );
      aegisDate = input.getTimestamp( "aegisDate" );
      aegisStatus = input.getString( "aegisStatus" );
      aegisTrans = input.getString( "aegisTrans" );
      answer = (java.lang.Object) input.getObject( "answer" );
      createDate = input.getTimestamp( "createDate" );
      importance = input.getString( "importance" );
      isAnswerDone = input.getBoolean( "isAnswerDone" );
      isDone = input.getBoolean( "isDone" );
      issueAttr1 = input.getString( "issueAttr1" );
      issueAttr2 = input.getString( "issueAttr2" );
      java.lang.String issueType_string_value = (java.lang.String) input.getString("issueType");
      if ( issueType_string_value != null ) { 
         issueType = (e3ps.project.IssueType) wt.introspection.ClassInfo.getConstrainedEnum( getClass(), "issueType", issueType_string_value );
         if ( issueType == null )  // hard-coded type
            issueType = e3ps.project.IssueType.toIssueType( issueType_string_value );
      }
      manager = (wt.org.WTPrincipalReference) input.readObject( "manager", manager, wt.org.WTPrincipalReference.class, true );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      planDoneDate = input.getTimestamp( "planDoneDate" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      subject = input.getString( "subject" );
      taskReference = (wt.fc.ObjectReference) input.readObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      urgency = input.getString( "urgency" );
   }

   boolean readVersion_4172619985972941186L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      aegisComment = (java.lang.Object) input.readObject();
      aegisCutOffComment = (java.lang.String) input.readObject();
      aegisDate = (java.sql.Timestamp) input.readObject();
      aegisStatus = (java.lang.String) input.readObject();
      aegisTrans = (java.lang.String) input.readObject();
      answer = (java.lang.Object) input.readObject();
      createDate = (java.sql.Timestamp) input.readObject();
      importance = (java.lang.String) input.readObject();
      isAnswerDone = input.readBoolean();
      isDone = input.readBoolean();
      issueAttr1 = (java.lang.String) input.readObject();
      issueAttr2 = (java.lang.String) input.readObject();
      java.lang.String issueType_string_value = (java.lang.String) input.readObject();
      try { 
         issueType = (e3ps.project.IssueType) wt.fc.EnumeratedTypeUtil.toEnumeratedType( issueType_string_value );
      } catch( wt.util.WTInvalidParameterException e ) {
         // Old Format
         issueType = e3ps.project.IssueType.toIssueType( issueType_string_value );
      }
      manager = (wt.org.WTPrincipalReference) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      planDoneDate = (java.sql.Timestamp) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      subject = (java.lang.String) input.readObject();
      taskReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      urgency = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( ProjectIssue thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4172619985972941186L( input, readSerialVersionUID, superDone );
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
