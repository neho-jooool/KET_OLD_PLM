package ext.ket.dqm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDqmAction extends wt.enterprise.Managed implements wt.content.FormatContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dqm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDqmAction.class.getName();

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String DUPLICATE_YN = "duplicateYn";
   static int DUPLICATE_YN_UPPER_LIMIT = -1;
   java.lang.String duplicateYn;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.String getDuplicateYn() {
      return duplicateYn;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setDuplicateYn(java.lang.String duplicateYn) throws wt.util.WTPropertyVetoException {
      duplicateYnValidate(duplicateYn);
      this.duplicateYn = duplicateYn;
   }
   void duplicateYnValidate(java.lang.String duplicateYn) throws wt.util.WTPropertyVetoException {
      if (DUPLICATE_YN_UPPER_LIMIT < 1) {
         try { DUPLICATE_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("duplicateYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DUPLICATE_YN_UPPER_LIMIT = 200; }
      }
      if (duplicateYn != null && !wt.fc.PersistenceHelper.checkStoredLength(duplicateYn.toString(), DUPLICATE_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "duplicateYn"), java.lang.String.valueOf(java.lang.Math.min(DUPLICATE_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "duplicateYn", this.duplicateYn, duplicateYn));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String DUPLICATE_REPORT_NAME = "duplicateReportName";
   static int DUPLICATE_REPORT_NAME_UPPER_LIMIT = -1;
   java.lang.String duplicateReportName;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.String getDuplicateReportName() {
      return duplicateReportName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setDuplicateReportName(java.lang.String duplicateReportName) throws wt.util.WTPropertyVetoException {
      duplicateReportNameValidate(duplicateReportName);
      this.duplicateReportName = duplicateReportName;
   }
   void duplicateReportNameValidate(java.lang.String duplicateReportName) throws wt.util.WTPropertyVetoException {
      if (DUPLICATE_REPORT_NAME_UPPER_LIMIT < 1) {
         try { DUPLICATE_REPORT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("duplicateReportName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DUPLICATE_REPORT_NAME_UPPER_LIMIT = 200; }
      }
      if (duplicateReportName != null && !wt.fc.PersistenceHelper.checkStoredLength(duplicateReportName.toString(), DUPLICATE_REPORT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "duplicateReportName"), java.lang.String.valueOf(java.lang.Math.min(DUPLICATE_REPORT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "duplicateReportName", this.duplicateReportName, duplicateReportName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String DUPLICATE_REPORT_CODE = "duplicateReportCode";
   static int DUPLICATE_REPORT_CODE_UPPER_LIMIT = -1;
   java.lang.String duplicateReportCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.String getDuplicateReportCode() {
      return duplicateReportCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setDuplicateReportCode(java.lang.String duplicateReportCode) throws wt.util.WTPropertyVetoException {
      duplicateReportCodeValidate(duplicateReportCode);
      this.duplicateReportCode = duplicateReportCode;
   }
   void duplicateReportCodeValidate(java.lang.String duplicateReportCode) throws wt.util.WTPropertyVetoException {
      if (DUPLICATE_REPORT_CODE_UPPER_LIMIT < 1) {
         try { DUPLICATE_REPORT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("duplicateReportCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DUPLICATE_REPORT_CODE_UPPER_LIMIT = 200; }
      }
      if (duplicateReportCode != null && !wt.fc.PersistenceHelper.checkStoredLength(duplicateReportCode.toString(), DUPLICATE_REPORT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "duplicateReportCode"), java.lang.String.valueOf(java.lang.Math.min(DUPLICATE_REPORT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "duplicateReportCode", this.duplicateReportCode, duplicateReportCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String CAUSE_WEB_EDITOR = "causeWebEditor";
   java.lang.Object causeWebEditor;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.Object getCauseWebEditor() {
      return causeWebEditor;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setCauseWebEditor(java.lang.Object causeWebEditor) throws wt.util.WTPropertyVetoException {
      causeWebEditorValidate(causeWebEditor);
      this.causeWebEditor = causeWebEditor;
   }
   void causeWebEditorValidate(java.lang.Object causeWebEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String CAUSE_WEB_EDITOR_TEXT = "causeWebEditorText";
   java.lang.Object causeWebEditorText;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.Object getCauseWebEditorText() {
      return causeWebEditorText;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setCauseWebEditorText(java.lang.Object causeWebEditorText) throws wt.util.WTPropertyVetoException {
      causeWebEditorTextValidate(causeWebEditorText);
      this.causeWebEditorText = causeWebEditorText;
   }
   void causeWebEditorTextValidate(java.lang.Object causeWebEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String IMPROVE_WEB_EDITOR = "improveWebEditor";
   java.lang.Object improveWebEditor;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.Object getImproveWebEditor() {
      return improveWebEditor;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setImproveWebEditor(java.lang.Object improveWebEditor) throws wt.util.WTPropertyVetoException {
      improveWebEditorValidate(improveWebEditor);
      this.improveWebEditor = improveWebEditor;
   }
   void improveWebEditorValidate(java.lang.Object improveWebEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String IMPROVE_WEB_EDITOR_TEXT = "improveWebEditorText";
   java.lang.Object improveWebEditorText;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.Object getImproveWebEditorText() {
      return improveWebEditorText;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setImproveWebEditorText(java.lang.Object improveWebEditorText) throws wt.util.WTPropertyVetoException {
      improveWebEditorTextValidate(improveWebEditorText);
      this.improveWebEditorText = improveWebEditorText;
   }
   void improveWebEditorTextValidate(java.lang.Object improveWebEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String MOLD_MODIFY_DATE = "moldModifyDate";
   java.sql.Timestamp moldModifyDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.sql.Timestamp getMoldModifyDate() {
      return moldModifyDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setMoldModifyDate(java.sql.Timestamp moldModifyDate) throws wt.util.WTPropertyVetoException {
      moldModifyDateValidate(moldModifyDate);
      this.moldModifyDate = moldModifyDate;
   }
   void moldModifyDateValidate(java.sql.Timestamp moldModifyDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String DRAWING_OUT_DATE = "drawingOutDate";
   java.sql.Timestamp drawingOutDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.sql.Timestamp getDrawingOutDate() {
      return drawingOutDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setDrawingOutDate(java.sql.Timestamp drawingOutDate) throws wt.util.WTPropertyVetoException {
      drawingOutDateValidate(drawingOutDate);
      this.drawingOutDate = drawingOutDate;
   }
   void drawingOutDateValidate(java.sql.Timestamp drawingOutDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String TO_DATE = "toDate";
   java.sql.Timestamp toDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.sql.Timestamp getToDate() {
      return toDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setToDate(java.sql.Timestamp toDate) throws wt.util.WTPropertyVetoException {
      toDateValidate(toDate);
      this.toDate = toDate;
   }
   void toDateValidate(java.sql.Timestamp toDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String TRUST_TEST_DATE = "trustTestDate";
   java.sql.Timestamp trustTestDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.sql.Timestamp getTrustTestDate() {
      return trustTestDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setTrustTestDate(java.sql.Timestamp trustTestDate) throws wt.util.WTPropertyVetoException {
      trustTestDateValidate(trustTestDate);
      this.trustTestDate = trustTestDate;
   }
   void trustTestDateValidate(java.sql.Timestamp trustTestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String CAUSE_CODE = "causeCode";
   static int CAUSE_CODE_UPPER_LIMIT = -1;
   java.lang.String causeCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.String getCauseCode() {
      return causeCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setCauseCode(java.lang.String causeCode) throws wt.util.WTPropertyVetoException {
      causeCodeValidate(causeCode);
      this.causeCode = causeCode;
   }
   void causeCodeValidate(java.lang.String causeCode) throws wt.util.WTPropertyVetoException {
      if (CAUSE_CODE_UPPER_LIMIT < 1) {
         try { CAUSE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("causeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAUSE_CODE_UPPER_LIMIT = 200; }
      }
      if (causeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(causeCode.toString(), CAUSE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "causeCode"), java.lang.String.valueOf(java.lang.Math.min(CAUSE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "causeCode", this.causeCode, causeCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String REVIEW_DATE = "reviewDate";
   java.sql.Timestamp reviewDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.sql.Timestamp getReviewDate() {
      return reviewDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setReviewDate(java.sql.Timestamp reviewDate) throws wt.util.WTPropertyVetoException {
      reviewDateValidate(reviewDate);
      this.reviewDate = reviewDate;
   }
   void reviewDateValidate(java.sql.Timestamp reviewDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String VALIDATION_DATE = "validationDate";
   java.sql.Timestamp validationDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.sql.Timestamp getValidationDate() {
      return validationDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setValidationDate(java.sql.Timestamp validationDate) throws wt.util.WTPropertyVetoException {
      validationDateValidate(validationDate);
      this.validationDate = validationDate;
   }
   void validationDateValidate(java.sql.Timestamp validationDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String EXEC_END_DATE = "execEndDate";
   java.sql.Timestamp execEndDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.sql.Timestamp getExecEndDate() {
      return execEndDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setExecEndDate(java.sql.Timestamp execEndDate) throws wt.util.WTPropertyVetoException {
      execEndDateValidate(execEndDate);
      this.execEndDate = execEndDate;
   }
   void execEndDateValidate(java.sql.Timestamp execEndDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String PROBLEM_REFLECT_YN = "problemReflectYn";
   static int PROBLEM_REFLECT_YN_UPPER_LIMIT = -1;
   java.lang.String problemReflectYn;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.String getProblemReflectYn() {
      return problemReflectYn;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setProblemReflectYn(java.lang.String problemReflectYn) throws wt.util.WTPropertyVetoException {
      problemReflectYnValidate(problemReflectYn);
      this.problemReflectYn = problemReflectYn;
   }
   void problemReflectYnValidate(java.lang.String problemReflectYn) throws wt.util.WTPropertyVetoException {
      if (PROBLEM_REFLECT_YN_UPPER_LIMIT < 1) {
         try { PROBLEM_REFLECT_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemReflectYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEM_REFLECT_YN_UPPER_LIMIT = 200; }
      }
      if (problemReflectYn != null && !wt.fc.PersistenceHelper.checkStoredLength(problemReflectYn.toString(), PROBLEM_REFLECT_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemReflectYn"), java.lang.String.valueOf(java.lang.Math.min(PROBLEM_REFLECT_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemReflectYn", this.problemReflectYn, problemReflectYn));
   }

   /**
    * 설계반영여부
    *
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String DESIGN_CHANGE_YN = "DesignChangeYn";
   static int DESIGN_CHANGE_YN_UPPER_LIMIT = -1;
   java.lang.String DesignChangeYn;
   /**
    * 설계반영여부
    *
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.String getDesignChangeYn() {
      return DesignChangeYn;
   }
   /**
    * 설계반영여부
    *
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setDesignChangeYn(java.lang.String DesignChangeYn) throws wt.util.WTPropertyVetoException {
      DesignChangeYnValidate(DesignChangeYn);
      this.DesignChangeYn = DesignChangeYn;
   }
   void DesignChangeYnValidate(java.lang.String DesignChangeYn) throws wt.util.WTPropertyVetoException {
      if (DESIGN_CHANGE_YN_UPPER_LIMIT < 1) {
         try { DESIGN_CHANGE_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("DesignChangeYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESIGN_CHANGE_YN_UPPER_LIMIT = 200; }
      }
      if (DesignChangeYn != null && !wt.fc.PersistenceHelper.checkStoredLength(DesignChangeYn.toString(), DESIGN_CHANGE_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "DesignChangeYn"), java.lang.String.valueOf(java.lang.Math.min(DESIGN_CHANGE_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "DesignChangeYn", this.DesignChangeYn, DesignChangeYn));
   }

   /**
    * 진행사항
    *
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String MAIN_SUBJECT = "mainSubject";
   static int MAIN_SUBJECT_UPPER_LIMIT = -1;
   java.lang.String mainSubject;
   /**
    * 진행사항
    *
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public java.lang.String getMainSubject() {
      return mainSubject;
   }
   /**
    * 진행사항
    *
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setMainSubject(java.lang.String mainSubject) throws wt.util.WTPropertyVetoException {
      mainSubjectValidate(mainSubject);
      this.mainSubject = mainSubject;
   }
   void mainSubjectValidate(java.lang.String mainSubject) throws wt.util.WTPropertyVetoException {
      if (MAIN_SUBJECT_UPPER_LIMIT < 1) {
         try { MAIN_SUBJECT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mainSubject").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAIN_SUBJECT_UPPER_LIMIT = 4000; }
      }
      if (mainSubject != null && !wt.fc.PersistenceHelper.checkStoredLength(mainSubject.toString(), MAIN_SUBJECT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mainSubject"), java.lang.String.valueOf(java.lang.Math.min(MAIN_SUBJECT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mainSubject", this.mainSubject, mainSubject));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String USER = "user";
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmAction
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
   }

   wt.content.ContentItem primary;
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public wt.content.ContentItem getPrimary() {
      return primary;
   }
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public void setPrimary(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
      primaryValidate(primary);
      this.primary = primary;
   }
   void primaryValidate(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
   }

   wt.content.DataFormatReference format;
   /**
    * @see wt.content.FormatContentHolder
    */
   public wt.content.DataFormatReference getFormat() {
      return format;
   }
   /**
    * @see wt.content.FormatContentHolder
    */
   public void setFormat(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      formatValidate(format);
      this.format = format;
   }
   void formatValidate(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      if (format == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "format") },
               new java.beans.PropertyChangeEvent(this, "format", this.format, format));
   }

   /**
    * Derived from {@link wt.content.DataFormatReference#getFormatName()}
    *
    * @see wt.content.FormatContentHolder
    */
   public java.lang.String getFormatName() {
      try { return (java.lang.String) ((wt.content.DataFormatReference) getFormat()).getFormatName(); }
      catch (java.lang.NullPointerException npe) { return null; }
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

   public static final long EXTERNALIZATION_VERSION_UID = -8175840602962450L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( DesignChangeYn );
      output.writeObject( causeCode );
      output.writeObject( causeWebEditor );
      output.writeObject( causeWebEditorText );
      output.writeObject( drawingOutDate );
      output.writeObject( duplicateReportCode );
      output.writeObject( duplicateReportName );
      output.writeObject( duplicateYn );
      output.writeObject( execEndDate );
      output.writeObject( format );
      output.writeObject( improveWebEditor );
      output.writeObject( improveWebEditorText );
      output.writeObject( mainSubject );
      output.writeObject( moldModifyDate );
      output.writeObject( problemReflectYn );
      output.writeObject( reviewDate );
      output.writeObject( toDate );
      output.writeObject( trustTestDate );
      output.writeObject( userReference );
      output.writeObject( validationDate );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
         output.writeObject( primary );
      }

   }

   protected void super_writeExternal_KETDqmAction(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dqm.entity.KETDqmAction) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDqmAction(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "DesignChangeYn", DesignChangeYn );
      output.setString( "causeCode", causeCode );
      output.setObject( "causeWebEditor", causeWebEditor );
      output.setObject( "causeWebEditorText", causeWebEditorText );
      output.setTimestamp( "drawingOutDate", drawingOutDate );
      output.setString( "duplicateReportCode", duplicateReportCode );
      output.setString( "duplicateReportName", duplicateReportName );
      output.setString( "duplicateYn", duplicateYn );
      output.setTimestamp( "execEndDate", execEndDate );
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setObject( "improveWebEditor", improveWebEditor );
      output.setObject( "improveWebEditorText", improveWebEditorText );
      output.setString( "mainSubject", mainSubject );
      output.setTimestamp( "moldModifyDate", moldModifyDate );
      output.setString( "problemReflectYn", problemReflectYn );
      output.setTimestamp( "reviewDate", reviewDate );
      output.setTimestamp( "toDate", toDate );
      output.setTimestamp( "trustTestDate", trustTestDate );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "validationDate", validationDate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      DesignChangeYn = input.getString( "DesignChangeYn" );
      causeCode = input.getString( "causeCode" );
      causeWebEditor = (java.lang.Object) input.getObject( "causeWebEditor" );
      causeWebEditorText = (java.lang.Object) input.getObject( "causeWebEditorText" );
      drawingOutDate = input.getTimestamp( "drawingOutDate" );
      duplicateReportCode = input.getString( "duplicateReportCode" );
      duplicateReportName = input.getString( "duplicateReportName" );
      duplicateYn = input.getString( "duplicateYn" );
      execEndDate = input.getTimestamp( "execEndDate" );
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      improveWebEditor = (java.lang.Object) input.getObject( "improveWebEditor" );
      improveWebEditorText = (java.lang.Object) input.getObject( "improveWebEditorText" );
      mainSubject = input.getString( "mainSubject" );
      moldModifyDate = input.getTimestamp( "moldModifyDate" );
      problemReflectYn = input.getString( "problemReflectYn" );
      reviewDate = input.getTimestamp( "reviewDate" );
      toDate = input.getTimestamp( "toDate" );
      trustTestDate = input.getTimestamp( "trustTestDate" );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      validationDate = input.getTimestamp( "validationDate" );
   }

   boolean readVersion_8175840602962450L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      DesignChangeYn = (java.lang.String) input.readObject();
      causeCode = (java.lang.String) input.readObject();
      causeWebEditor = (java.lang.Object) input.readObject();
      causeWebEditorText = (java.lang.Object) input.readObject();
      drawingOutDate = (java.sql.Timestamp) input.readObject();
      duplicateReportCode = (java.lang.String) input.readObject();
      duplicateReportName = (java.lang.String) input.readObject();
      duplicateYn = (java.lang.String) input.readObject();
      execEndDate = (java.sql.Timestamp) input.readObject();
      format = (wt.content.DataFormatReference) input.readObject();
      improveWebEditor = (java.lang.Object) input.readObject();
      improveWebEditorText = (java.lang.Object) input.readObject();
      mainSubject = (java.lang.String) input.readObject();
      moldModifyDate = (java.sql.Timestamp) input.readObject();
      problemReflectYn = (java.lang.String) input.readObject();
      reviewDate = (java.sql.Timestamp) input.readObject();
      toDate = (java.sql.Timestamp) input.readObject();
      trustTestDate = (java.sql.Timestamp) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      validationDate = (java.sql.Timestamp) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETDqmAction thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8175840602962450L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDqmAction( _KETDqmAction thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
