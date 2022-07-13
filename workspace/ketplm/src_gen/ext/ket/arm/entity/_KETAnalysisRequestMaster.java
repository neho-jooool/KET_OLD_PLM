package ext.ket.arm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETAnalysisRequestMaster extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.arm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETAnalysisRequestMaster.class.getName();

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String ANALYSIS_REQUEST_NO = "analysisRequestNo";
   static int ANALYSIS_REQUEST_NO_UPPER_LIMIT = -1;
   java.lang.String analysisRequestNo;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getAnalysisRequestNo() {
      return analysisRequestNo;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setAnalysisRequestNo(java.lang.String analysisRequestNo) throws wt.util.WTPropertyVetoException {
      analysisRequestNoValidate(analysisRequestNo);
      this.analysisRequestNo = analysisRequestNo;
   }
   void analysisRequestNoValidate(java.lang.String analysisRequestNo) throws wt.util.WTPropertyVetoException {
      if (ANALYSIS_REQUEST_NO_UPPER_LIMIT < 1) {
         try { ANALYSIS_REQUEST_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("analysisRequestNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ANALYSIS_REQUEST_NO_UPPER_LIMIT = 200; }
      }
      if (analysisRequestNo != null && !wt.fc.PersistenceHelper.checkStoredLength(analysisRequestNo.toString(), ANALYSIS_REQUEST_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "analysisRequestNo"), java.lang.String.valueOf(java.lang.Math.min(ANALYSIS_REQUEST_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "analysisRequestNo", this.analysisRequestNo, analysisRequestNo));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String PROJECT_NO = "projectNo";
   static int PROJECT_NO_UPPER_LIMIT = -1;
   java.lang.String projectNo;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getProjectNo() {
      return projectNo;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
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
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String ANALYSIS_TITLE = "analysisTitle";
   static int ANALYSIS_TITLE_UPPER_LIMIT = -1;
   java.lang.String analysisTitle;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getAnalysisTitle() {
      return analysisTitle;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setAnalysisTitle(java.lang.String analysisTitle) throws wt.util.WTPropertyVetoException {
      analysisTitleValidate(analysisTitle);
      this.analysisTitle = analysisTitle;
   }
   void analysisTitleValidate(java.lang.String analysisTitle) throws wt.util.WTPropertyVetoException {
      if (ANALYSIS_TITLE_UPPER_LIMIT < 1) {
         try { ANALYSIS_TITLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("analysisTitle").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ANALYSIS_TITLE_UPPER_LIMIT = 200; }
      }
      if (analysisTitle != null && !wt.fc.PersistenceHelper.checkStoredLength(analysisTitle.toString(), ANALYSIS_TITLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "analysisTitle"), java.lang.String.valueOf(java.lang.Math.min(ANALYSIS_TITLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "analysisTitle", this.analysisTitle, analysisTitle));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String CUSTOMER_CHARGE_NAME = "customerChargeName";
   static int CUSTOMER_CHARGE_NAME_UPPER_LIMIT = -1;
   java.lang.String customerChargeName;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getCustomerChargeName() {
      return customerChargeName;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setCustomerChargeName(java.lang.String customerChargeName) throws wt.util.WTPropertyVetoException {
      customerChargeNameValidate(customerChargeName);
      this.customerChargeName = customerChargeName;
   }
   void customerChargeNameValidate(java.lang.String customerChargeName) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_CHARGE_NAME_UPPER_LIMIT < 1) {
         try { CUSTOMER_CHARGE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customerChargeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_CHARGE_NAME_UPPER_LIMIT = 200; }
      }
      if (customerChargeName != null && !wt.fc.PersistenceHelper.checkStoredLength(customerChargeName.toString(), CUSTOMER_CHARGE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerChargeName"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_CHARGE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customerChargeName", this.customerChargeName, customerChargeName));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
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
    * ???????????
    *
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String START_DATE = "startDate";
   java.sql.Timestamp startDate;
   /**
    * ???????????
    *
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.sql.Timestamp getStartDate() {
      return startDate;
   }
   /**
    * ???????????
    *
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setStartDate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
      startDateValidate(startDate);
      this.startDate = startDate;
   }
   void startDateValidate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ??????????
    *
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String END_DATE = "endDate";
   java.sql.Timestamp endDate;
   /**
    * ??????????
    *
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.sql.Timestamp getEndDate() {
      return endDate;
   }
   /**
    * ??????????
    *
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setEndDate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String ETC_COMMENT = "etcComment";
   static int ETC_COMMENT_UPPER_LIMIT = -1;
   java.lang.String etcComment;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getEtcComment() {
      return etcComment;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setEtcComment(java.lang.String etcComment) throws wt.util.WTPropertyVetoException {
      etcCommentValidate(etcComment);
      this.etcComment = etcComment;
   }
   void etcCommentValidate(java.lang.String etcComment) throws wt.util.WTPropertyVetoException {
      if (ETC_COMMENT_UPPER_LIMIT < 1) {
         try { ETC_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_COMMENT_UPPER_LIMIT = 200; }
      }
      if (etcComment != null && !wt.fc.PersistenceHelper.checkStoredLength(etcComment.toString(), ETC_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcComment"), java.lang.String.valueOf(java.lang.Math.min(ETC_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcComment", this.etcComment, etcComment));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String ANALYSIS_OBJECT_COMMENT = "analysisObjectComment";
   static int ANALYSIS_OBJECT_COMMENT_UPPER_LIMIT = -1;
   java.lang.String analysisObjectComment;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getAnalysisObjectComment() {
      return analysisObjectComment;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setAnalysisObjectComment(java.lang.String analysisObjectComment) throws wt.util.WTPropertyVetoException {
      analysisObjectCommentValidate(analysisObjectComment);
      this.analysisObjectComment = analysisObjectComment;
   }
   void analysisObjectCommentValidate(java.lang.String analysisObjectComment) throws wt.util.WTPropertyVetoException {
      if (ANALYSIS_OBJECT_COMMENT_UPPER_LIMIT < 1) {
         try { ANALYSIS_OBJECT_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("analysisObjectComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ANALYSIS_OBJECT_COMMENT_UPPER_LIMIT = 200; }
      }
      if (analysisObjectComment != null && !wt.fc.PersistenceHelper.checkStoredLength(analysisObjectComment.toString(), ANALYSIS_OBJECT_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "analysisObjectComment"), java.lang.String.valueOf(java.lang.Math.min(ANALYSIS_OBJECT_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "analysisObjectComment", this.analysisObjectComment, analysisObjectComment));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String ANALYSIS_TARGET_COMMENT = "analysisTargetComment";
   static int ANALYSIS_TARGET_COMMENT_UPPER_LIMIT = -1;
   java.lang.String analysisTargetComment;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public java.lang.String getAnalysisTargetComment() {
      return analysisTargetComment;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setAnalysisTargetComment(java.lang.String analysisTargetComment) throws wt.util.WTPropertyVetoException {
      analysisTargetCommentValidate(analysisTargetComment);
      this.analysisTargetComment = analysisTargetComment;
   }
   void analysisTargetCommentValidate(java.lang.String analysisTargetComment) throws wt.util.WTPropertyVetoException {
      if (ANALYSIS_TARGET_COMMENT_UPPER_LIMIT < 1) {
         try { ANALYSIS_TARGET_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("analysisTargetComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ANALYSIS_TARGET_COMMENT_UPPER_LIMIT = 200; }
      }
      if (analysisTargetComment != null && !wt.fc.PersistenceHelper.checkStoredLength(analysisTargetComment.toString(), ANALYSIS_TARGET_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "analysisTargetComment"), java.lang.String.valueOf(java.lang.Math.min(ANALYSIS_TARGET_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "analysisTargetComment", this.analysisTargetComment, analysisTargetComment));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CLIENT_USER = "ketClientUser";
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CLIENT_USER_REFERENCE = "ketClientUserReference";
   wt.fc.ObjectReference ketClientUserReference;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.org.WTUser getKetClientUser() {
      return (ketClientUserReference != null) ? (wt.org.WTUser) ketClientUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.fc.ObjectReference getKetClientUserReference() {
      return ketClientUserReference;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetClientUser(wt.org.WTUser the_ketClientUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setKetClientUserReference(the_ketClientUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_ketClientUser));
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetClientUserReference(wt.fc.ObjectReference the_ketClientUserReference) throws wt.util.WTPropertyVetoException {
      ketClientUserReferenceValidate(the_ketClientUserReference);
      ketClientUserReference = (wt.fc.ObjectReference) the_ketClientUserReference;
   }
   void ketClientUserReferenceValidate(wt.fc.ObjectReference the_ketClientUserReference) throws wt.util.WTPropertyVetoException {
      if (the_ketClientUserReference == null || the_ketClientUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketClientUserReference") },
               new java.beans.PropertyChangeEvent(this, "ketClientUserReference", this.ketClientUserReference, ketClientUserReference));
      if (the_ketClientUserReference != null && the_ketClientUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_ketClientUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketClientUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "ketClientUserReference", this.ketClientUserReference, ketClientUserReference));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CLIENT_DEPARTMENT = "ketClientDepartment";
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CLIENT_DEPARTMENT_REFERENCE = "ketClientDepartmentReference";
   wt.fc.ObjectReference ketClientDepartmentReference;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public e3ps.groupware.company.Department getKetClientDepartment() {
      return (ketClientDepartmentReference != null) ? (e3ps.groupware.company.Department) ketClientDepartmentReference.getObject() : null;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.fc.ObjectReference getKetClientDepartmentReference() {
      return ketClientDepartmentReference;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetClientDepartment(e3ps.groupware.company.Department the_ketClientDepartment) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setKetClientDepartmentReference(the_ketClientDepartment == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_ketClientDepartment));
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetClientDepartmentReference(wt.fc.ObjectReference the_ketClientDepartmentReference) throws wt.util.WTPropertyVetoException {
      ketClientDepartmentReferenceValidate(the_ketClientDepartmentReference);
      ketClientDepartmentReference = (wt.fc.ObjectReference) the_ketClientDepartmentReference;
   }
   void ketClientDepartmentReferenceValidate(wt.fc.ObjectReference the_ketClientDepartmentReference) throws wt.util.WTPropertyVetoException {
      if (the_ketClientDepartmentReference == null || the_ketClientDepartmentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketClientDepartmentReference") },
               new java.beans.PropertyChangeEvent(this, "ketClientDepartmentReference", this.ketClientDepartmentReference, ketClientDepartmentReference));
      if (the_ketClientDepartmentReference != null && the_ketClientDepartmentReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_ketClientDepartmentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketClientDepartmentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "ketClientDepartmentReference", this.ketClientDepartmentReference, ketClientDepartmentReference));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CHARGE_USER = "ketChargeUser";
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CHARGE_USER_REFERENCE = "ketChargeUserReference";
   wt.fc.ObjectReference ketChargeUserReference;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.org.WTUser getKetChargeUser() {
      return (ketChargeUserReference != null) ? (wt.org.WTUser) ketChargeUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.fc.ObjectReference getKetChargeUserReference() {
      return ketChargeUserReference;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetChargeUser(wt.org.WTUser the_ketChargeUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setKetChargeUserReference(the_ketChargeUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_ketChargeUser));
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetChargeUserReference(wt.fc.ObjectReference the_ketChargeUserReference) throws wt.util.WTPropertyVetoException {
      ketChargeUserReferenceValidate(the_ketChargeUserReference);
      ketChargeUserReference = (wt.fc.ObjectReference) the_ketChargeUserReference;
   }
   void ketChargeUserReferenceValidate(wt.fc.ObjectReference the_ketChargeUserReference) throws wt.util.WTPropertyVetoException {
      if (the_ketChargeUserReference == null || the_ketChargeUserReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketChargeUserReference") },
               new java.beans.PropertyChangeEvent(this, "ketChargeUserReference", this.ketChargeUserReference, ketChargeUserReference));
      if (the_ketChargeUserReference != null && the_ketChargeUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_ketChargeUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketChargeUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "ketChargeUserReference", this.ketChargeUserReference, ketChargeUserReference));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CHARGE_DEPARTMENT = "ketChargeDepartment";
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String KET_CHARGE_DEPARTMENT_REFERENCE = "ketChargeDepartmentReference";
   wt.fc.ObjectReference ketChargeDepartmentReference;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public e3ps.groupware.company.Department getKetChargeDepartment() {
      return (ketChargeDepartmentReference != null) ? (e3ps.groupware.company.Department) ketChargeDepartmentReference.getObject() : null;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.fc.ObjectReference getKetChargeDepartmentReference() {
      return ketChargeDepartmentReference;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetChargeDepartment(e3ps.groupware.company.Department the_ketChargeDepartment) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setKetChargeDepartmentReference(the_ketChargeDepartment == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_ketChargeDepartment));
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setKetChargeDepartmentReference(wt.fc.ObjectReference the_ketChargeDepartmentReference) throws wt.util.WTPropertyVetoException {
      ketChargeDepartmentReferenceValidate(the_ketChargeDepartmentReference);
      ketChargeDepartmentReference = (wt.fc.ObjectReference) the_ketChargeDepartmentReference;
   }
   void ketChargeDepartmentReferenceValidate(wt.fc.ObjectReference the_ketChargeDepartmentReference) throws wt.util.WTPropertyVetoException {
      if (the_ketChargeDepartmentReference == null || the_ketChargeDepartmentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketChargeDepartmentReference") },
               new java.beans.PropertyChangeEvent(this, "ketChargeDepartmentReference", this.ketChargeDepartmentReference, ketChargeDepartmentReference));
      if (the_ketChargeDepartmentReference != null && the_ketChargeDepartmentReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_ketChargeDepartmentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketChargeDepartmentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "ketChargeDepartmentReference", this.ketChargeDepartmentReference, ketChargeDepartmentReference));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String CAR_TYPE = "carType";
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String CAR_TYPE_REFERENCE = "carTypeReference";
   wt.fc.ObjectReference carTypeReference;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public e3ps.project.outputtype.OEMProjectType getCarType() {
      return (carTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) carTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.fc.ObjectReference getCarTypeReference() {
      return carTypeReference;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setCarType(e3ps.project.outputtype.OEMProjectType the_carType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCarTypeReference(the_carType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_carType));
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setCarTypeReference(wt.fc.ObjectReference the_carTypeReference) throws wt.util.WTPropertyVetoException {
      carTypeReferenceValidate(the_carTypeReference);
      carTypeReference = (wt.fc.ObjectReference) the_carTypeReference;
   }
   void carTypeReferenceValidate(wt.fc.ObjectReference the_carTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_carTypeReference != null && the_carTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_carTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "carTypeReference", this.carTypeReference, carTypeReference));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String PROCESS = "process";
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String PROCESS_REFERENCE = "processReference";
   wt.fc.ObjectReference processReference;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public e3ps.common.code.NumberCode getProcess() {
      return (processReference != null) ? (e3ps.common.code.NumberCode) processReference.getObject() : null;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.fc.ObjectReference getProcessReference() {
      return processReference;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setProcess(e3ps.common.code.NumberCode the_process) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProcessReference(the_process == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_process));
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setProcessReference(wt.fc.ObjectReference the_processReference) throws wt.util.WTPropertyVetoException {
      processReferenceValidate(the_processReference);
      processReference = (wt.fc.ObjectReference) the_processReference;
   }
   void processReferenceValidate(wt.fc.ObjectReference the_processReference) throws wt.util.WTPropertyVetoException {
      if (the_processReference != null && the_processReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_processReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "processReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "processReference", this.processReference, processReference));
   }

   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String USAGE = "usage";
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public static final java.lang.String USAGE_REFERENCE = "usageReference";
   wt.fc.ObjectReference usageReference;
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public e3ps.common.code.NumberCode getUsage() {
      return (usageReference != null) ? (e3ps.common.code.NumberCode) usageReference.getObject() : null;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public wt.fc.ObjectReference getUsageReference() {
      return usageReference;
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setUsage(e3ps.common.code.NumberCode the_usage) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUsageReference(the_usage == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_usage));
   }
   /**
    * @see ext.ket.arm.entity.KETAnalysisRequestMaster
    */
   public void setUsageReference(wt.fc.ObjectReference the_usageReference) throws wt.util.WTPropertyVetoException {
      usageReferenceValidate(the_usageReference);
      usageReference = (wt.fc.ObjectReference) the_usageReference;
   }
   void usageReferenceValidate(wt.fc.ObjectReference the_usageReference) throws wt.util.WTPropertyVetoException {
      if (the_usageReference != null && the_usageReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_usageReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "usageReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "usageReference", this.usageReference, usageReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -7030948374011428911L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( analysisObjectComment );
      output.writeObject( analysisRequestNo );
      output.writeObject( analysisTargetComment );
      output.writeObject( analysisTitle );
      output.writeObject( carTypeReference );
      output.writeObject( customerChargeName );
      output.writeObject( endDate );
      output.writeObject( etcComment );
      output.writeObject( ketChargeDepartmentReference );
      output.writeObject( ketChargeUserReference );
      output.writeObject( ketClientDepartmentReference );
      output.writeObject( ketClientUserReference );
      output.writeObject( partNo );
      output.writeObject( processReference );
      output.writeObject( projectNo );
      output.writeObject( startDate );
      output.writeObject( usageReference );
   }

   protected void super_writeExternal_KETAnalysisRequestMaster(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.arm.entity.KETAnalysisRequestMaster) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETAnalysisRequestMaster(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "analysisObjectComment", analysisObjectComment );
      output.setString( "analysisRequestNo", analysisRequestNo );
      output.setString( "analysisTargetComment", analysisTargetComment );
      output.setString( "analysisTitle", analysisTitle );
      output.writeObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "customerChargeName", customerChargeName );
      output.setTimestamp( "endDate", endDate );
      output.setString( "etcComment", etcComment );
      output.writeObject( "ketChargeDepartmentReference", ketChargeDepartmentReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "ketChargeUserReference", ketChargeUserReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "ketClientDepartmentReference", ketClientDepartmentReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "ketClientUserReference", ketClientUserReference, wt.fc.ObjectReference.class, true );
      output.setString( "partNo", partNo );
      output.writeObject( "processReference", processReference, wt.fc.ObjectReference.class, true );
      output.setString( "projectNo", projectNo );
      output.setTimestamp( "startDate", startDate );
      output.writeObject( "usageReference", usageReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      analysisObjectComment = input.getString( "analysisObjectComment" );
      analysisRequestNo = input.getString( "analysisRequestNo" );
      analysisTargetComment = input.getString( "analysisTargetComment" );
      analysisTitle = input.getString( "analysisTitle" );
      carTypeReference = (wt.fc.ObjectReference) input.readObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      customerChargeName = input.getString( "customerChargeName" );
      endDate = input.getTimestamp( "endDate" );
      etcComment = input.getString( "etcComment" );
      ketChargeDepartmentReference = (wt.fc.ObjectReference) input.readObject( "ketChargeDepartmentReference", ketChargeDepartmentReference, wt.fc.ObjectReference.class, true );
      ketChargeUserReference = (wt.fc.ObjectReference) input.readObject( "ketChargeUserReference", ketChargeUserReference, wt.fc.ObjectReference.class, true );
      ketClientDepartmentReference = (wt.fc.ObjectReference) input.readObject( "ketClientDepartmentReference", ketClientDepartmentReference, wt.fc.ObjectReference.class, true );
      ketClientUserReference = (wt.fc.ObjectReference) input.readObject( "ketClientUserReference", ketClientUserReference, wt.fc.ObjectReference.class, true );
      partNo = input.getString( "partNo" );
      processReference = (wt.fc.ObjectReference) input.readObject( "processReference", processReference, wt.fc.ObjectReference.class, true );
      projectNo = input.getString( "projectNo" );
      startDate = input.getTimestamp( "startDate" );
      usageReference = (wt.fc.ObjectReference) input.readObject( "usageReference", usageReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion_7030948374011428911L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      analysisObjectComment = (java.lang.String) input.readObject();
      analysisRequestNo = (java.lang.String) input.readObject();
      analysisTargetComment = (java.lang.String) input.readObject();
      analysisTitle = (java.lang.String) input.readObject();
      carTypeReference = (wt.fc.ObjectReference) input.readObject();
      customerChargeName = (java.lang.String) input.readObject();
      endDate = (java.sql.Timestamp) input.readObject();
      etcComment = (java.lang.String) input.readObject();
      ketChargeDepartmentReference = (wt.fc.ObjectReference) input.readObject();
      ketChargeUserReference = (wt.fc.ObjectReference) input.readObject();
      ketClientDepartmentReference = (wt.fc.ObjectReference) input.readObject();
      ketClientUserReference = (wt.fc.ObjectReference) input.readObject();
      partNo = (java.lang.String) input.readObject();
      processReference = (wt.fc.ObjectReference) input.readObject();
      projectNo = (java.lang.String) input.readObject();
      startDate = (java.sql.Timestamp) input.readObject();
      usageReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( KETAnalysisRequestMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7030948374011428911L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETAnalysisRequestMaster( _KETAnalysisRequestMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
