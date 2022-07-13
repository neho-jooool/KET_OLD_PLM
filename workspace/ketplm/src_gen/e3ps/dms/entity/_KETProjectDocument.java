package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProjectDocument extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProjectDocument.class.getName();

   /**
    * 문서코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String DOCUMENT_NO = "documentNo";
   static int DOCUMENT_NO_UPPER_LIMIT = -1;
   java.lang.String documentNo;
   /**
    * 문서코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getDocumentNo() {
      return documentNo;
   }
   /**
    * 문서코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setDocumentNo(java.lang.String documentNo) throws wt.util.WTPropertyVetoException {
      documentNoValidate(documentNo);
      this.documentNo = documentNo;
   }
   void documentNoValidate(java.lang.String documentNo) throws wt.util.WTPropertyVetoException {
      if (DOCUMENT_NO_UPPER_LIMIT < 1) {
         try { DOCUMENT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("documentNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOCUMENT_NO_UPPER_LIMIT = 200; }
      }
      if (documentNo != null && !wt.fc.PersistenceHelper.checkStoredLength(documentNo.toString(), DOCUMENT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "documentNo"), java.lang.String.valueOf(java.lang.Math.min(DOCUMENT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "documentNo", this.documentNo, documentNo));
   }

   /**
    * 문서 설명
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String DOCUMENT_DESCRIPTION = "documentDescription";
   static int DOCUMENT_DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String documentDescription;
   /**
    * 문서 설명
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getDocumentDescription() {
      return documentDescription;
   }
   /**
    * 문서 설명
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setDocumentDescription(java.lang.String documentDescription) throws wt.util.WTPropertyVetoException {
      documentDescriptionValidate(documentDescription);
      this.documentDescription = documentDescription;
   }
   void documentDescriptionValidate(java.lang.String documentDescription) throws wt.util.WTPropertyVetoException {
      if (DOCUMENT_DESCRIPTION_UPPER_LIMIT < 1) {
         try { DOCUMENT_DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("documentDescription").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOCUMENT_DESCRIPTION_UPPER_LIMIT = 4000; }
      }
      if (documentDescription != null && !wt.fc.PersistenceHelper.checkStoredLength(documentDescription.toString(), DOCUMENT_DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "documentDescription"), java.lang.String.valueOf(java.lang.Math.min(DOCUMENT_DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "documentDescription", this.documentDescription, documentDescription));
   }

   /**
    * 사업부코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String DIVISION_CODE = "divisionCode";
   static int DIVISION_CODE_UPPER_LIMIT = -1;
   java.lang.String divisionCode;
   /**
    * 사업부코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getDivisionCode() {
      return divisionCode;
   }
   /**
    * 사업부코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setDivisionCode(java.lang.String divisionCode) throws wt.util.WTPropertyVetoException {
      divisionCodeValidate(divisionCode);
      this.divisionCode = divisionCode;
   }
   void divisionCodeValidate(java.lang.String divisionCode) throws wt.util.WTPropertyVetoException {
      if (DIVISION_CODE_UPPER_LIMIT < 1) {
         try { DIVISION_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("divisionCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIVISION_CODE_UPPER_LIMIT = 200; }
      }
      if (divisionCode != null && !wt.fc.PersistenceHelper.checkStoredLength(divisionCode.toString(), DIVISION_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "divisionCode"), java.lang.String.valueOf(java.lang.Math.min(DIVISION_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "divisionCode", this.divisionCode, divisionCode));
   }

   /**
    * 등록부서명
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * 등록부서명
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * 등록부서명
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setDeptName(java.lang.String deptName) throws wt.util.WTPropertyVetoException {
      deptNameValidate(deptName);
      this.deptName = deptName;
   }
   void deptNameValidate(java.lang.String deptName) throws wt.util.WTPropertyVetoException {
      if (DEPT_NAME_UPPER_LIMIT < 1) {
         try { DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (deptName != null && !wt.fc.PersistenceHelper.checkStoredLength(deptName.toString(), DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptName"), java.lang.String.valueOf(java.lang.Math.min(DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptName", this.deptName, deptName));
   }

   /**
    * 최초등록시점
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String FIRST_REGISTRATION_STAGE = "firstRegistrationStage";
   int firstRegistrationStage;
   /**
    * 최초등록시점
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public int getFirstRegistrationStage() {
      return firstRegistrationStage;
   }
   /**
    * 최초등록시점
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setFirstRegistrationStage(int firstRegistrationStage) throws wt.util.WTPropertyVetoException {
      firstRegistrationStageValidate(firstRegistrationStage);
      this.firstRegistrationStage = firstRegistrationStage;
   }
   void firstRegistrationStageValidate(int firstRegistrationStage) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 고객제출자료여부
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String IS_BUYER_SUMMIT = "isBuyerSummit";
   static int IS_BUYER_SUMMIT_UPPER_LIMIT = -1;
   java.lang.String isBuyerSummit;
   /**
    * 고객제출자료여부
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getIsBuyerSummit() {
      return isBuyerSummit;
   }
   /**
    * 고객제출자료여부
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setIsBuyerSummit(java.lang.String isBuyerSummit) throws wt.util.WTPropertyVetoException {
      isBuyerSummitValidate(isBuyerSummit);
      this.isBuyerSummit = isBuyerSummit;
   }
   void isBuyerSummitValidate(java.lang.String isBuyerSummit) throws wt.util.WTPropertyVetoException {
      if (IS_BUYER_SUMMIT_UPPER_LIMIT < 1) {
         try { IS_BUYER_SUMMIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("isBuyerSummit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IS_BUYER_SUMMIT_UPPER_LIMIT = 200; }
      }
      if (isBuyerSummit != null && !wt.fc.PersistenceHelper.checkStoredLength(isBuyerSummit.toString(), IS_BUYER_SUMMIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "isBuyerSummit"), java.lang.String.valueOf(java.lang.Math.min(IS_BUYER_SUMMIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "isBuyerSummit", this.isBuyerSummit, isBuyerSummit));
   }

   /**
    * 고객사코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String BUYER_CODE = "buyerCode";
   static int BUYER_CODE_UPPER_LIMIT = -1;
   java.lang.String buyerCode;
   /**
    * 고객사코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getBuyerCode() {
      return buyerCode;
   }
   /**
    * 고객사코드
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setBuyerCode(java.lang.String buyerCode) throws wt.util.WTPropertyVetoException {
      buyerCodeValidate(buyerCode);
      this.buyerCode = buyerCode;
   }
   void buyerCodeValidate(java.lang.String buyerCode) throws wt.util.WTPropertyVetoException {
      if (BUYER_CODE_UPPER_LIMIT < 1) {
         try { BUYER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("buyerCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BUYER_CODE_UPPER_LIMIT = 4000; }
      }
      if (buyerCode != null && !wt.fc.PersistenceHelper.checkStoredLength(buyerCode.toString(), BUYER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "buyerCode"), java.lang.String.valueOf(java.lang.Math.min(BUYER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "buyerCode", this.buyerCode, buyerCode));
   }

   /**
    * DR평가점수
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String D_RCHECK_POINT = "dRCheckPoint";
   int dRCheckPoint;
   /**
    * DR평가점수
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public int getDRCheckPoint() {
      return dRCheckPoint;
   }
   /**
    * DR평가점수
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setDRCheckPoint(int dRCheckPoint) throws wt.util.WTPropertyVetoException {
      dRCheckPointValidate(dRCheckPoint);
      this.dRCheckPoint = dRCheckPoint;
   }
   void dRCheckPointValidate(int dRCheckPoint) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 승인결과
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String APPROVAL_RESULT = "approvalResult";
   static int APPROVAL_RESULT_UPPER_LIMIT = -1;
   java.lang.String approvalResult;
   /**
    * 승인결과
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getApprovalResult() {
      return approvalResult;
   }
   /**
    * 승인결과
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setApprovalResult(java.lang.String approvalResult) throws wt.util.WTPropertyVetoException {
      approvalResultValidate(approvalResult);
      this.approvalResult = approvalResult;
   }
   void approvalResultValidate(java.lang.String approvalResult) throws wt.util.WTPropertyVetoException {
      if (APPROVAL_RESULT_UPPER_LIMIT < 1) {
         try { APPROVAL_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approvalResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVAL_RESULT_UPPER_LIMIT = 200; }
      }
      if (approvalResult != null && !wt.fc.PersistenceHelper.checkStoredLength(approvalResult.toString(), APPROVAL_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approvalResult"), java.lang.String.valueOf(java.lang.Math.min(APPROVAL_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approvalResult", this.approvalResult, approvalResult));
   }

   /**
    * 최종승인의견
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String LAST_APPROVAL_COMMENT = "lastApprovalComment";
   static int LAST_APPROVAL_COMMENT_UPPER_LIMIT = -1;
   java.lang.String lastApprovalComment;
   /**
    * 최종승인의견
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getLastApprovalComment() {
      return lastApprovalComment;
   }
   /**
    * 최종승인의견
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setLastApprovalComment(java.lang.String lastApprovalComment) throws wt.util.WTPropertyVetoException {
      lastApprovalCommentValidate(lastApprovalComment);
      this.lastApprovalComment = lastApprovalComment;
   }
   void lastApprovalCommentValidate(java.lang.String lastApprovalComment) throws wt.util.WTPropertyVetoException {
      if (LAST_APPROVAL_COMMENT_UPPER_LIMIT < 1) {
         try { LAST_APPROVAL_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastApprovalComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_APPROVAL_COMMENT_UPPER_LIMIT = 4000; }
      }
      if (lastApprovalComment != null && !wt.fc.PersistenceHelper.checkStoredLength(lastApprovalComment.toString(), LAST_APPROVAL_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastApprovalComment"), java.lang.String.valueOf(java.lang.Math.min(LAST_APPROVAL_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastApprovalComment", this.lastApprovalComment, lastApprovalComment));
   }

   /**
    * 보안등급
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String SECURITY = "security";
   static int SECURITY_UPPER_LIMIT = -1;
   java.lang.String security;
   /**
    * 보안등급
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getSecurity() {
      return security;
   }
   /**
    * 보안등급
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setSecurity(java.lang.String security) throws wt.util.WTPropertyVetoException {
      securityValidate(security);
      this.security = security;
   }
   void securityValidate(java.lang.String security) throws wt.util.WTPropertyVetoException {
      if (SECURITY_UPPER_LIMIT < 1) {
         try { SECURITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("security").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SECURITY_UPPER_LIMIT = 200; }
      }
      if (security != null && !wt.fc.PersistenceHelper.checkStoredLength(security.toString(), SECURITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "security"), java.lang.String.valueOf(java.lang.Math.min(SECURITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "security", this.security, security));
   }

   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute1(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      attribute1Validate(attribute1);
      this.attribute1 = attribute1;
   }
   void attribute1Validate(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE1_UPPER_LIMIT < 1) {
         try { ATTRIBUTE1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE1_UPPER_LIMIT = 200; }
      }
      if (attribute1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute1.toString(), ATTRIBUTE1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute1"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute1", this.attribute1, attribute1));
   }

   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute2(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      attribute2Validate(attribute2);
      this.attribute2 = attribute2;
   }
   void attribute2Validate(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE2_UPPER_LIMIT < 1) {
         try { ATTRIBUTE2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE2_UPPER_LIMIT = 200; }
      }
      if (attribute2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute2.toString(), ATTRIBUTE2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute2"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute2", this.attribute2, attribute2));
   }

   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute3(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      attribute3Validate(attribute3);
      this.attribute3 = attribute3;
   }
   void attribute3Validate(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE3_UPPER_LIMIT < 1) {
         try { ATTRIBUTE3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE3_UPPER_LIMIT = 200; }
      }
      if (attribute3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute3.toString(), ATTRIBUTE3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute3"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute3", this.attribute3, attribute3));
   }

   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute4(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      attribute4Validate(attribute4);
      this.attribute4 = attribute4;
   }
   void attribute4Validate(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE4_UPPER_LIMIT < 1) {
         try { ATTRIBUTE4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE4_UPPER_LIMIT = 200; }
      }
      if (attribute4 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute4.toString(), ATTRIBUTE4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute4"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute4", this.attribute4, attribute4));
   }

   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute5(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      attribute5Validate(attribute5);
      this.attribute5 = attribute5;
   }
   void attribute5Validate(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE5_UPPER_LIMIT < 1) {
         try { ATTRIBUTE5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE5_UPPER_LIMIT = 200; }
      }
      if (attribute5 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute5.toString(), ATTRIBUTE5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute5"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute5", this.attribute5, attribute5));
   }

   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute6(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      attribute6Validate(attribute6);
      this.attribute6 = attribute6;
   }
   void attribute6Validate(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE6_UPPER_LIMIT < 1) {
         try { ATTRIBUTE6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE6_UPPER_LIMIT = 200; }
      }
      if (attribute6 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute6.toString(), ATTRIBUTE6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute6"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute6", this.attribute6, attribute6));
   }

   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute7(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      attribute7Validate(attribute7);
      this.attribute7 = attribute7;
   }
   void attribute7Validate(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE7_UPPER_LIMIT < 1) {
         try { ATTRIBUTE7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE7_UPPER_LIMIT = 200; }
      }
      if (attribute7 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute7.toString(), ATTRIBUTE7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute7"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute7", this.attribute7, attribute7));
   }

   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute8(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      attribute8Validate(attribute8);
      this.attribute8 = attribute8;
   }
   void attribute8Validate(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE8_UPPER_LIMIT < 1) {
         try { ATTRIBUTE8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE8_UPPER_LIMIT = 200; }
      }
      if (attribute8 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute8.toString(), ATTRIBUTE8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute8"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute8", this.attribute8, attribute8));
   }

   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute9(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      attribute9Validate(attribute9);
      this.attribute9 = attribute9;
   }
   void attribute9Validate(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE9_UPPER_LIMIT < 1) {
         try { ATTRIBUTE9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE9_UPPER_LIMIT = 200; }
      }
      if (attribute9 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute9.toString(), ATTRIBUTE9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute9"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute9", this.attribute9, attribute9));
   }

   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setAttribute10(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      attribute10Validate(attribute10);
      this.attribute10 = attribute10;
   }
   void attribute10Validate(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE10_UPPER_LIMIT < 1) {
         try { ATTRIBUTE10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE10_UPPER_LIMIT = 200; }
      }
      if (attribute10 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute10.toString(), ATTRIBUTE10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute10"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute10", this.attribute10, attribute10));
   }

   /**
    * 원가의견
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String COST_COMMENT = "costComment";
   static int COST_COMMENT_UPPER_LIMIT = -1;
   java.lang.String costComment;
   /**
    * 원가의견
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.String getCostComment() {
      return costComment;
   }
   /**
    * 원가의견
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setCostComment(java.lang.String costComment) throws wt.util.WTPropertyVetoException {
      costCommentValidate(costComment);
      this.costComment = costComment;
   }
   void costCommentValidate(java.lang.String costComment) throws wt.util.WTPropertyVetoException {
      if (COST_COMMENT_UPPER_LIMIT < 1) {
         try { COST_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_COMMENT_UPPER_LIMIT = 4000; }
      }
      if (costComment != null && !wt.fc.PersistenceHelper.checkStoredLength(costComment.toString(), COST_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costComment"), java.lang.String.valueOf(java.lang.Math.min(COST_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costComment", this.costComment, costComment));
   }

   /**
    * Web Editor
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * Web Editor
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * Web Editor
    *
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see e3ps.dms.entity.KETProjectDocument
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 2546461055830931362L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( approvalResult );
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
      output.writeObject( buyerCode );
      output.writeObject( costComment );
      output.writeInt( dRCheckPoint );
      output.writeObject( deptName );
      output.writeObject( divisionCode );
      output.writeObject( documentDescription );
      output.writeObject( documentNo );
      output.writeInt( firstRegistrationStage );
      output.writeObject( isBuyerSummit );
      output.writeObject( lastApprovalComment );
      output.writeObject( security );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );
   }

   protected void super_writeExternal_KETProjectDocument(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETProjectDocument) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProjectDocument(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "approvalResult", approvalResult );
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
      output.setString( "buyerCode", buyerCode );
      output.setString( "costComment", costComment );
      output.setInt( "dRCheckPoint", dRCheckPoint );
      output.setString( "deptName", deptName );
      output.setString( "divisionCode", divisionCode );
      output.setString( "documentDescription", documentDescription );
      output.setString( "documentNo", documentNo );
      output.setInt( "firstRegistrationStage", firstRegistrationStage );
      output.setString( "isBuyerSummit", isBuyerSummit );
      output.setString( "lastApprovalComment", lastApprovalComment );
      output.setString( "security", security );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      approvalResult = input.getString( "approvalResult" );
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
      buyerCode = input.getString( "buyerCode" );
      costComment = input.getString( "costComment" );
      dRCheckPoint = input.getInt( "dRCheckPoint" );
      deptName = input.getString( "deptName" );
      divisionCode = input.getString( "divisionCode" );
      documentDescription = input.getString( "documentDescription" );
      documentNo = input.getString( "documentNo" );
      firstRegistrationStage = input.getInt( "firstRegistrationStage" );
      isBuyerSummit = input.getString( "isBuyerSummit" );
      lastApprovalComment = input.getString( "lastApprovalComment" );
      security = input.getString( "security" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion2546461055830931362L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      approvalResult = (java.lang.String) input.readObject();
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
      buyerCode = (java.lang.String) input.readObject();
      costComment = (java.lang.String) input.readObject();
      dRCheckPoint = input.readInt();
      deptName = (java.lang.String) input.readObject();
      divisionCode = (java.lang.String) input.readObject();
      documentDescription = (java.lang.String) input.readObject();
      documentNo = (java.lang.String) input.readObject();
      firstRegistrationStage = input.readInt();
      isBuyerSummit = (java.lang.String) input.readObject();
      lastApprovalComment = (java.lang.String) input.readObject();
      security = (java.lang.String) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProjectDocument thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2546461055830931362L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProjectDocument( _KETProjectDocument thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
