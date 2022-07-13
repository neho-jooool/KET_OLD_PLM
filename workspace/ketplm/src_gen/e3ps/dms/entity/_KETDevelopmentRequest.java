package e3ps.dms.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDevelopmentRequest extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.dms.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDevelopmentRequest.class.getName();

   /**
    * 프로젝트 식별자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String PROJECT_OID = "projectOID";
   static int PROJECT_OID_UPPER_LIMIT = -1;
   java.lang.String projectOID;
   /**
    * 프로젝트 식별자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getProjectOID() {
      return projectOID;
   }
   /**
    * 프로젝트 식별자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setProjectOID(java.lang.String projectOID) throws wt.util.WTPropertyVetoException {
      projectOIDValidate(projectOID);
      this.projectOID = projectOID;
   }
   void projectOIDValidate(java.lang.String projectOID) throws wt.util.WTPropertyVetoException {
      if (PROJECT_OID_UPPER_LIMIT < 1) {
         try { PROJECT_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectOID").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_OID_UPPER_LIMIT = 4000; }
      }
      if (projectOID != null && !wt.fc.PersistenceHelper.checkStoredLength(projectOID.toString(), PROJECT_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectOID"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectOID", this.projectOID, projectOID));
   }

   /**
    * 사업부코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DIVISION_CODE = "divisionCode";
   static int DIVISION_CODE_UPPER_LIMIT = -1;
   java.lang.String divisionCode;
   /**
    * 사업부코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDivisionCode() {
      return divisionCode;
   }
   /**
    * 사업부코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * 등록부서명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * 등록부서명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 개발단계
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DEVELOPMENT_STEP = "developmentStep";
   static int DEVELOPMENT_STEP_UPPER_LIMIT = -1;
   java.lang.String developmentStep;
   /**
    * 개발단계
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDevelopmentStep() {
      return developmentStep;
   }
   /**
    * 개발단계
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setDevelopmentStep(java.lang.String developmentStep) throws wt.util.WTPropertyVetoException {
      developmentStepValidate(developmentStep);
      this.developmentStep = developmentStep;
   }
   void developmentStepValidate(java.lang.String developmentStep) throws wt.util.WTPropertyVetoException {
      if (DEVELOPMENT_STEP_UPPER_LIMIT < 1) {
         try { DEVELOPMENT_STEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("developmentStep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEVELOPMENT_STEP_UPPER_LIMIT = 200; }
      }
      if (developmentStep != null && !wt.fc.PersistenceHelper.checkStoredLength(developmentStep.toString(), DEVELOPMENT_STEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "developmentStep"), java.lang.String.valueOf(java.lang.Math.min(DEVELOPMENT_STEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "developmentStep", this.developmentStep, developmentStep));
   }

   /**
    * 개발검토의뢰처
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String REQUEST_BUYER = "requestBuyer";
   static int REQUEST_BUYER_UPPER_LIMIT = -1;
   java.lang.String requestBuyer;
   /**
    * 개발검토의뢰처
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getRequestBuyer() {
      return requestBuyer;
   }
   /**
    * 개발검토의뢰처
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setRequestBuyer(java.lang.String requestBuyer) throws wt.util.WTPropertyVetoException {
      requestBuyerValidate(requestBuyer);
      this.requestBuyer = requestBuyer;
   }
   void requestBuyerValidate(java.lang.String requestBuyer) throws wt.util.WTPropertyVetoException {
      if (REQUEST_BUYER_UPPER_LIMIT < 1) {
         try { REQUEST_BUYER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestBuyer").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUEST_BUYER_UPPER_LIMIT = 200; }
      }
      if (requestBuyer != null && !wt.fc.PersistenceHelper.checkStoredLength(requestBuyer.toString(), REQUEST_BUYER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestBuyer"), java.lang.String.valueOf(java.lang.Math.min(REQUEST_BUYER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestBuyer", this.requestBuyer, requestBuyer));
   }

   /**
    * 개발검토의뢰처담당자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String REQUEST_BUYER_MANAGER = "requestBuyerManager";
   static int REQUEST_BUYER_MANAGER_UPPER_LIMIT = -1;
   java.lang.String requestBuyerManager;
   /**
    * 개발검토의뢰처담당자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getRequestBuyerManager() {
      return requestBuyerManager;
   }
   /**
    * 개발검토의뢰처담당자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setRequestBuyerManager(java.lang.String requestBuyerManager) throws wt.util.WTPropertyVetoException {
      requestBuyerManagerValidate(requestBuyerManager);
      this.requestBuyerManager = requestBuyerManager;
   }
   void requestBuyerManagerValidate(java.lang.String requestBuyerManager) throws wt.util.WTPropertyVetoException {
      if (REQUEST_BUYER_MANAGER_UPPER_LIMIT < 1) {
         try { REQUEST_BUYER_MANAGER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestBuyerManager").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUEST_BUYER_MANAGER_UPPER_LIMIT = 200; }
      }
      if (requestBuyerManager != null && !wt.fc.PersistenceHelper.checkStoredLength(requestBuyerManager.toString(), REQUEST_BUYER_MANAGER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestBuyerManager"), java.lang.String.valueOf(java.lang.Math.min(REQUEST_BUYER_MANAGER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestBuyerManager", this.requestBuyerManager, requestBuyerManager));
   }

   /**
    * 의뢰접수자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String RECEPTION = "reception";
   static int RECEPTION_UPPER_LIMIT = -1;
   java.lang.String reception;
   /**
    * 의뢰접수자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getReception() {
      return reception;
   }
   /**
    * 의뢰접수자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setReception(java.lang.String reception) throws wt.util.WTPropertyVetoException {
      receptionValidate(reception);
      this.reception = reception;
   }
   void receptionValidate(java.lang.String reception) throws wt.util.WTPropertyVetoException {
      if (RECEPTION_UPPER_LIMIT < 1) {
         try { RECEPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reception").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RECEPTION_UPPER_LIMIT = 200; }
      }
      if (reception != null && !wt.fc.PersistenceHelper.checkStoredLength(reception.toString(), RECEPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reception"), java.lang.String.valueOf(java.lang.Math.min(RECEPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reception", this.reception, reception));
   }

   /**
    * 최종고객사코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String LAST_USING_BUYER = "lastUsingBuyer";
   static int LAST_USING_BUYER_UPPER_LIMIT = -1;
   java.lang.String lastUsingBuyer;
   /**
    * 최종고객사코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getLastUsingBuyer() {
      return lastUsingBuyer;
   }
   /**
    * 최종고객사코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setLastUsingBuyer(java.lang.String lastUsingBuyer) throws wt.util.WTPropertyVetoException {
      lastUsingBuyerValidate(lastUsingBuyer);
      this.lastUsingBuyer = lastUsingBuyer;
   }
   void lastUsingBuyerValidate(java.lang.String lastUsingBuyer) throws wt.util.WTPropertyVetoException {
      if (LAST_USING_BUYER_UPPER_LIMIT < 1) {
         try { LAST_USING_BUYER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastUsingBuyer").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_USING_BUYER_UPPER_LIMIT = 200; }
      }
      if (lastUsingBuyer != null && !wt.fc.PersistenceHelper.checkStoredLength(lastUsingBuyer.toString(), LAST_USING_BUYER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastUsingBuyer"), java.lang.String.valueOf(java.lang.Math.min(LAST_USING_BUYER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastUsingBuyer", this.lastUsingBuyer, lastUsingBuyer));
   }

   /**
    * 최종고객사담당자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String LAST_USING_BUYER_MANAGER = "lastUsingBuyerManager";
   static int LAST_USING_BUYER_MANAGER_UPPER_LIMIT = -1;
   java.lang.String lastUsingBuyerManager;
   /**
    * 최종고객사담당자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getLastUsingBuyerManager() {
      return lastUsingBuyerManager;
   }
   /**
    * 최종고객사담당자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setLastUsingBuyerManager(java.lang.String lastUsingBuyerManager) throws wt.util.WTPropertyVetoException {
      lastUsingBuyerManagerValidate(lastUsingBuyerManager);
      this.lastUsingBuyerManager = lastUsingBuyerManager;
   }
   void lastUsingBuyerManagerValidate(java.lang.String lastUsingBuyerManager) throws wt.util.WTPropertyVetoException {
      if (LAST_USING_BUYER_MANAGER_UPPER_LIMIT < 1) {
         try { LAST_USING_BUYER_MANAGER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastUsingBuyerManager").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_USING_BUYER_MANAGER_UPPER_LIMIT = 200; }
      }
      if (lastUsingBuyerManager != null && !wt.fc.PersistenceHelper.checkStoredLength(lastUsingBuyerManager.toString(), LAST_USING_BUYER_MANAGER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastUsingBuyerManager"), java.lang.String.valueOf(java.lang.Math.min(LAST_USING_BUYER_MANAGER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastUsingBuyerManager", this.lastUsingBuyerManager, lastUsingBuyerManager));
   }

   /**
    * 대표차종
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String REP_CAR_TYPE = "repCarType";
   static int REP_CAR_TYPE_UPPER_LIMIT = -1;
   java.lang.String repCarType;
   /**
    * 대표차종
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getRepCarType() {
      return repCarType;
   }
   /**
    * 대표차종
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setRepCarType(java.lang.String repCarType) throws wt.util.WTPropertyVetoException {
      repCarTypeValidate(repCarType);
      this.repCarType = repCarType;
   }
   void repCarTypeValidate(java.lang.String repCarType) throws wt.util.WTPropertyVetoException {
      if (REP_CAR_TYPE_UPPER_LIMIT < 1) {
         try { REP_CAR_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("repCarType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REP_CAR_TYPE_UPPER_LIMIT = 200; }
      }
      if (repCarType != null && !wt.fc.PersistenceHelper.checkStoredLength(repCarType.toString(), REP_CAR_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "repCarType"), java.lang.String.valueOf(java.lang.Math.min(REP_CAR_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "repCarType", this.repCarType, repCarType));
   }

   /**
    * DR요구일자여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String IS_DRREQUEST = "isDRRequest";
   static int IS_DRREQUEST_UPPER_LIMIT = -1;
   java.lang.String isDRRequest;
   /**
    * DR요구일자여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getIsDRRequest() {
      return isDRRequest;
   }
   /**
    * DR요구일자여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setIsDRRequest(java.lang.String isDRRequest) throws wt.util.WTPropertyVetoException {
      isDRRequestValidate(isDRRequest);
      this.isDRRequest = isDRRequest;
   }
   void isDRRequestValidate(java.lang.String isDRRequest) throws wt.util.WTPropertyVetoException {
      if (IS_DRREQUEST_UPPER_LIMIT < 1) {
         try { IS_DRREQUEST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("isDRRequest").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IS_DRREQUEST_UPPER_LIMIT = 200; }
      }
      if (isDRRequest != null && !wt.fc.PersistenceHelper.checkStoredLength(isDRRequest.toString(), IS_DRREQUEST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "isDRRequest"), java.lang.String.valueOf(java.lang.Math.min(IS_DRREQUEST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "isDRRequest", this.isDRRequest, isDRRequest));
   }

   /**
    * DR요구일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DR_REQUEST_DATE = "drRequestDate";
   java.sql.Timestamp drRequestDate;
   /**
    * DR요구일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getDrRequestDate() {
      return drRequestDate;
   }
   /**
    * DR요구일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setDrRequestDate(java.sql.Timestamp drRequestDate) throws wt.util.WTPropertyVetoException {
      drRequestDateValidate(drRequestDate);
      this.drRequestDate = drRequestDate;
   }
   void drRequestDateValidate(java.sql.Timestamp drRequestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 제안서제출여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String IS_PROPOSAL_SUBMIT = "isProposalSubmit";
   static int IS_PROPOSAL_SUBMIT_UPPER_LIMIT = -1;
   java.lang.String isProposalSubmit;
   /**
    * 제안서제출여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getIsProposalSubmit() {
      return isProposalSubmit;
   }
   /**
    * 제안서제출여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setIsProposalSubmit(java.lang.String isProposalSubmit) throws wt.util.WTPropertyVetoException {
      isProposalSubmitValidate(isProposalSubmit);
      this.isProposalSubmit = isProposalSubmit;
   }
   void isProposalSubmitValidate(java.lang.String isProposalSubmit) throws wt.util.WTPropertyVetoException {
      if (IS_PROPOSAL_SUBMIT_UPPER_LIMIT < 1) {
         try { IS_PROPOSAL_SUBMIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("isProposalSubmit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IS_PROPOSAL_SUBMIT_UPPER_LIMIT = 200; }
      }
      if (isProposalSubmit != null && !wt.fc.PersistenceHelper.checkStoredLength(isProposalSubmit.toString(), IS_PROPOSAL_SUBMIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "isProposalSubmit"), java.lang.String.valueOf(java.lang.Math.min(IS_PROPOSAL_SUBMIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "isProposalSubmit", this.isProposalSubmit, isProposalSubmit));
   }

   /**
    * 제안서제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String PROPOSAL_SUBMIT_DATE = "proposalSubmitDate";
   java.sql.Timestamp proposalSubmitDate;
   /**
    * 제안서제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getProposalSubmitDate() {
      return proposalSubmitDate;
   }
   /**
    * 제안서제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setProposalSubmitDate(java.sql.Timestamp proposalSubmitDate) throws wt.util.WTPropertyVetoException {
      proposalSubmitDateValidate(proposalSubmitDate);
      this.proposalSubmitDate = proposalSubmitDate;
   }
   void proposalSubmitDateValidate(java.sql.Timestamp proposalSubmitDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 개발원가제출여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String IS_COST_SUBMIT = "isCostSubmit";
   static int IS_COST_SUBMIT_UPPER_LIMIT = -1;
   java.lang.String isCostSubmit;
   /**
    * 개발원가제출여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getIsCostSubmit() {
      return isCostSubmit;
   }
   /**
    * 개발원가제출여부
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setIsCostSubmit(java.lang.String isCostSubmit) throws wt.util.WTPropertyVetoException {
      isCostSubmitValidate(isCostSubmit);
      this.isCostSubmit = isCostSubmit;
   }
   void isCostSubmitValidate(java.lang.String isCostSubmit) throws wt.util.WTPropertyVetoException {
      if (IS_COST_SUBMIT_UPPER_LIMIT < 1) {
         try { IS_COST_SUBMIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("isCostSubmit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IS_COST_SUBMIT_UPPER_LIMIT = 200; }
      }
      if (isCostSubmit != null && !wt.fc.PersistenceHelper.checkStoredLength(isCostSubmit.toString(), IS_COST_SUBMIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "isCostSubmit"), java.lang.String.valueOf(java.lang.Math.min(IS_COST_SUBMIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "isCostSubmit", this.isCostSubmit, isCostSubmit));
   }

   /**
    * 개발원가제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String COST_SUBMIT_DATE = "costSubmitDate";
   java.sql.Timestamp costSubmitDate;
   /**
    * 개발원가제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getCostSubmitDate() {
      return costSubmitDate;
   }
   /**
    * 개발원가제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setCostSubmitDate(java.sql.Timestamp costSubmitDate) throws wt.util.WTPropertyVetoException {
      costSubmitDateValidate(costSubmitDate);
      this.costSubmitDate = costSubmitDate;
   }
   void costSubmitDateValidate(java.sql.Timestamp costSubmitDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 개발검토제품명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DEV_PRODUCT_NAME = "devProductName";
   static int DEV_PRODUCT_NAME_UPPER_LIMIT = -1;
   java.lang.String devProductName;
   /**
    * 개발검토제품명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDevProductName() {
      return devProductName;
   }
   /**
    * 개발검토제품명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setDevProductName(java.lang.String devProductName) throws wt.util.WTPropertyVetoException {
      devProductNameValidate(devProductName);
      this.devProductName = devProductName;
   }
   void devProductNameValidate(java.lang.String devProductName) throws wt.util.WTPropertyVetoException {
      if (DEV_PRODUCT_NAME_UPPER_LIMIT < 1) {
         try { DEV_PRODUCT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devProductName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_PRODUCT_NAME_UPPER_LIMIT = 200; }
      }
      if (devProductName != null && !wt.fc.PersistenceHelper.checkStoredLength(devProductName.toString(), DEV_PRODUCT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devProductName"), java.lang.String.valueOf(java.lang.Math.min(DEV_PRODUCT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devProductName", this.devProductName, devProductName));
   }

   /**
    * 개발검토유형
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DEV_REVIEW_TYPE_CODE = "devReviewTypeCode";
   static int DEV_REVIEW_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String devReviewTypeCode;
   /**
    * 개발검토유형
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDevReviewTypeCode() {
      return devReviewTypeCode;
   }
   /**
    * 개발검토유형
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setDevReviewTypeCode(java.lang.String devReviewTypeCode) throws wt.util.WTPropertyVetoException {
      devReviewTypeCodeValidate(devReviewTypeCode);
      this.devReviewTypeCode = devReviewTypeCode;
   }
   void devReviewTypeCodeValidate(java.lang.String devReviewTypeCode) throws wt.util.WTPropertyVetoException {
      if (DEV_REVIEW_TYPE_CODE_UPPER_LIMIT < 1) {
         try { DEV_REVIEW_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devReviewTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_REVIEW_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (devReviewTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(devReviewTypeCode.toString(), DEV_REVIEW_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devReviewTypeCode"), java.lang.String.valueOf(java.lang.Math.min(DEV_REVIEW_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devReviewTypeCode", this.devReviewTypeCode, devReviewTypeCode));
   }

   /**
    * 기타개발검토유형
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DEV_REVIEW_TYPE_ETC = "devReviewTypeEtc";
   static int DEV_REVIEW_TYPE_ETC_UPPER_LIMIT = -1;
   java.lang.String devReviewTypeEtc;
   /**
    * 기타개발검토유형
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDevReviewTypeEtc() {
      return devReviewTypeEtc;
   }
   /**
    * 기타개발검토유형
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setDevReviewTypeEtc(java.lang.String devReviewTypeEtc) throws wt.util.WTPropertyVetoException {
      devReviewTypeEtcValidate(devReviewTypeEtc);
      this.devReviewTypeEtc = devReviewTypeEtc;
   }
   void devReviewTypeEtcValidate(java.lang.String devReviewTypeEtc) throws wt.util.WTPropertyVetoException {
      if (DEV_REVIEW_TYPE_ETC_UPPER_LIMIT < 1) {
         try { DEV_REVIEW_TYPE_ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devReviewTypeEtc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_REVIEW_TYPE_ETC_UPPER_LIMIT = 200; }
      }
      if (devReviewTypeEtc != null && !wt.fc.PersistenceHelper.checkStoredLength(devReviewTypeEtc.toString(), DEV_REVIEW_TYPE_ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devReviewTypeEtc"), java.lang.String.valueOf(java.lang.Math.min(DEV_REVIEW_TYPE_ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devReviewTypeEtc", this.devReviewTypeEtc, devReviewTypeEtc));
   }

   /**
    * 개발검토상세내용
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DEV_REVIEW_DETAIL_COMMENT = "devReviewDetailComment";
   static int DEV_REVIEW_DETAIL_COMMENT_UPPER_LIMIT = -1;
   java.lang.String devReviewDetailComment;
   /**
    * 개발검토상세내용
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDevReviewDetailComment() {
      return devReviewDetailComment;
   }
   /**
    * 개발검토상세내용
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setDevReviewDetailComment(java.lang.String devReviewDetailComment) throws wt.util.WTPropertyVetoException {
      devReviewDetailCommentValidate(devReviewDetailComment);
      this.devReviewDetailComment = devReviewDetailComment;
   }
   void devReviewDetailCommentValidate(java.lang.String devReviewDetailComment) throws wt.util.WTPropertyVetoException {
      if (DEV_REVIEW_DETAIL_COMMENT_UPPER_LIMIT < 1) {
         try { DEV_REVIEW_DETAIL_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devReviewDetailComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_REVIEW_DETAIL_COMMENT_UPPER_LIMIT = 4000; }
      }
      if (devReviewDetailComment != null && !wt.fc.PersistenceHelper.checkStoredLength(devReviewDetailComment.toString(), DEV_REVIEW_DETAIL_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devReviewDetailComment"), java.lang.String.valueOf(java.lang.Math.min(DEV_REVIEW_DETAIL_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devReviewDetailComment", this.devReviewDetailComment, devReviewDetailComment));
   }

   /**
    * 제품판매최초년도
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String PRODUCT_SALE_FIRST_YEAR = "productSaleFirstYear";
   static int PRODUCT_SALE_FIRST_YEAR_UPPER_LIMIT = -1;
   java.lang.String productSaleFirstYear;
   /**
    * 제품판매최초년도
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getProductSaleFirstYear() {
      return productSaleFirstYear;
   }
   /**
    * 제품판매최초년도
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setProductSaleFirstYear(java.lang.String productSaleFirstYear) throws wt.util.WTPropertyVetoException {
      productSaleFirstYearValidate(productSaleFirstYear);
      this.productSaleFirstYear = productSaleFirstYear;
   }
   void productSaleFirstYearValidate(java.lang.String productSaleFirstYear) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_SALE_FIRST_YEAR_UPPER_LIMIT < 1) {
         try { PRODUCT_SALE_FIRST_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productSaleFirstYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_SALE_FIRST_YEAR_UPPER_LIMIT = 200; }
      }
      if (productSaleFirstYear != null && !wt.fc.PersistenceHelper.checkStoredLength(productSaleFirstYear.toString(), PRODUCT_SALE_FIRST_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productSaleFirstYear"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_SALE_FIRST_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productSaleFirstYear", this.productSaleFirstYear, productSaleFirstYear));
   }

   /**
    * 초도품제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String INITIAL_SAMPLE_SUMMIT_DATE = "initialSampleSummitDate";
   java.sql.Timestamp initialSampleSummitDate;
   /**
    * 초도품제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getInitialSampleSummitDate() {
      return initialSampleSummitDate;
   }
   /**
    * 초도품제출일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setInitialSampleSummitDate(java.sql.Timestamp initialSampleSummitDate) throws wt.util.WTPropertyVetoException {
      initialSampleSummitDateValidate(initialSampleSummitDate);
      this.initialSampleSummitDate = initialSampleSummitDate;
   }
   void initialSampleSummitDateValidate(java.sql.Timestamp initialSampleSummitDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ESIR일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String E_SIRDATE = "eSIRDate";
   java.sql.Timestamp eSIRDate;
   /**
    * ESIR일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getESIRDate() {
      return eSIRDate;
   }
   /**
    * ESIR일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setESIRDate(java.sql.Timestamp eSIRDate) throws wt.util.WTPropertyVetoException {
      eSIRDateValidate(eSIRDate);
      this.eSIRDate = eSIRDate;
   }
   void eSIRDateValidate(java.sql.Timestamp eSIRDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ISIR일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String I_SIRDATE = "iSIRDate";
   java.sql.Timestamp iSIRDate;
   /**
    * ISIR일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getISIRDate() {
      return iSIRDate;
   }
   /**
    * ISIR일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setISIRDate(java.sql.Timestamp iSIRDate) throws wt.util.WTPropertyVetoException {
      iSIRDateValidate(iSIRDate);
      this.iSIRDate = iSIRDate;
   }
   void iSIRDateValidate(java.sql.Timestamp iSIRDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * KET양산일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String KET_MASS_PRODUCTION_DATE = "ketMassProductionDate";
   java.sql.Timestamp ketMassProductionDate;
   /**
    * KET양산일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getKetMassProductionDate() {
      return ketMassProductionDate;
   }
   /**
    * KET양산일자
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setKetMassProductionDate(java.sql.Timestamp ketMassProductionDate) throws wt.util.WTPropertyVetoException {
      ketMassProductionDateValidate(ketMassProductionDate);
      this.ketMassProductionDate = ketMassProductionDate;
   }
   void ketMassProductionDateValidate(java.sql.Timestamp ketMassProductionDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 제품군코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String PRODUCT_CATEGORY_CODE = "productCategoryCode";
   static int PRODUCT_CATEGORY_CODE_UPPER_LIMIT = -1;
   java.lang.String productCategoryCode;
   /**
    * 제품군코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getProductCategoryCode() {
      return productCategoryCode;
   }
   /**
    * 제품군코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setProductCategoryCode(java.lang.String productCategoryCode) throws wt.util.WTPropertyVetoException {
      productCategoryCodeValidate(productCategoryCode);
      this.productCategoryCode = productCategoryCode;
   }
   void productCategoryCodeValidate(java.lang.String productCategoryCode) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_CATEGORY_CODE_UPPER_LIMIT < 1) {
         try { PRODUCT_CATEGORY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productCategoryCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_CATEGORY_CODE_UPPER_LIMIT = 4000; }
      }
      if (productCategoryCode != null && !wt.fc.PersistenceHelper.checkStoredLength(productCategoryCode.toString(), PRODUCT_CATEGORY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productCategoryCode"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_CATEGORY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productCategoryCode", this.productCategoryCode, productCategoryCode));
   }

   /**
    * 제품 Type
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String PRODUCT_TYPE_CODE = "productTypeCode";
   static int PRODUCT_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String productTypeCode;
   /**
    * 제품 Type
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getProductTypeCode() {
      return productTypeCode;
   }
   /**
    * 제품 Type
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setProductTypeCode(java.lang.String productTypeCode) throws wt.util.WTPropertyVetoException {
      productTypeCodeValidate(productTypeCode);
      this.productTypeCode = productTypeCode;
   }
   void productTypeCodeValidate(java.lang.String productTypeCode) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_TYPE_CODE_UPPER_LIMIT < 1) {
         try { PRODUCT_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (productTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(productTypeCode.toString(), PRODUCT_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productTypeCode"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productTypeCode", this.productTypeCode, productTypeCode));
   }

   /**
    * 기타
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ETC_SPECIFICATION = "etcSpecification";
   static int ETC_SPECIFICATION_UPPER_LIMIT = -1;
   java.lang.String etcSpecification;
   /**
    * 기타
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getEtcSpecification() {
      return etcSpecification;
   }
   /**
    * 기타
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setEtcSpecification(java.lang.String etcSpecification) throws wt.util.WTPropertyVetoException {
      etcSpecificationValidate(etcSpecification);
      this.etcSpecification = etcSpecification;
   }
   void etcSpecificationValidate(java.lang.String etcSpecification) throws wt.util.WTPropertyVetoException {
      if (ETC_SPECIFICATION_UPPER_LIMIT < 1) {
         try { ETC_SPECIFICATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcSpecification").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_SPECIFICATION_UPPER_LIMIT = 200; }
      }
      if (etcSpecification != null && !wt.fc.PersistenceHelper.checkStoredLength(etcSpecification.toString(), ETC_SPECIFICATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcSpecification"), java.lang.String.valueOf(java.lang.Math.min(ETC_SPECIFICATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcSpecification", this.etcSpecification, etcSpecification));
   }

   /**
    * TabSize
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String TAB_SIZE = "tabSize";
   static int TAB_SIZE_UPPER_LIMIT = -1;
   java.lang.String tabSize;
   /**
    * TabSize
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getTabSize() {
      return tabSize;
   }
   /**
    * TabSize
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setTabSize(java.lang.String tabSize) throws wt.util.WTPropertyVetoException {
      tabSizeValidate(tabSize);
      this.tabSize = tabSize;
   }
   void tabSizeValidate(java.lang.String tabSize) throws wt.util.WTPropertyVetoException {
      if (TAB_SIZE_UPPER_LIMIT < 1) {
         try { TAB_SIZE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tabSize").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TAB_SIZE_UPPER_LIMIT = 200; }
      }
      if (tabSize != null && !wt.fc.PersistenceHelper.checkStoredLength(tabSize.toString(), TAB_SIZE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tabSize"), java.lang.String.valueOf(java.lang.Math.min(TAB_SIZE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tabSize", this.tabSize, tabSize));
   }

   /**
    * 원재료부자재
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String MATERIAL_SUB_MATERIAL = "materialSubMaterial";
   static int MATERIAL_SUB_MATERIAL_UPPER_LIMIT = -1;
   java.lang.String materialSubMaterial;
   /**
    * 원재료부자재
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getMaterialSubMaterial() {
      return materialSubMaterial;
   }
   /**
    * 원재료부자재
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setMaterialSubMaterial(java.lang.String materialSubMaterial) throws wt.util.WTPropertyVetoException {
      materialSubMaterialValidate(materialSubMaterial);
      this.materialSubMaterial = materialSubMaterial;
   }
   void materialSubMaterialValidate(java.lang.String materialSubMaterial) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_SUB_MATERIAL_UPPER_LIMIT < 1) {
         try { MATERIAL_SUB_MATERIAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("materialSubMaterial").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_SUB_MATERIAL_UPPER_LIMIT = 200; }
      }
      if (materialSubMaterial != null && !wt.fc.PersistenceHelper.checkStoredLength(materialSubMaterial.toString(), MATERIAL_SUB_MATERIAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialSubMaterial"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_SUB_MATERIAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "materialSubMaterial", this.materialSubMaterial, materialSubMaterial));
   }

   /**
    * 표면처리코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String SURFACE_TREATMENT_CODE = "surfaceTreatmentCode";
   static int SURFACE_TREATMENT_CODE_UPPER_LIMIT = -1;
   java.lang.String surfaceTreatmentCode;
   /**
    * 표면처리코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getSurfaceTreatmentCode() {
      return surfaceTreatmentCode;
   }
   /**
    * 표면처리코드
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setSurfaceTreatmentCode(java.lang.String surfaceTreatmentCode) throws wt.util.WTPropertyVetoException {
      surfaceTreatmentCodeValidate(surfaceTreatmentCode);
      this.surfaceTreatmentCode = surfaceTreatmentCode;
   }
   void surfaceTreatmentCodeValidate(java.lang.String surfaceTreatmentCode) throws wt.util.WTPropertyVetoException {
      if (SURFACE_TREATMENT_CODE_UPPER_LIMIT < 1) {
         try { SURFACE_TREATMENT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("surfaceTreatmentCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SURFACE_TREATMENT_CODE_UPPER_LIMIT = 200; }
      }
      if (surfaceTreatmentCode != null && !wt.fc.PersistenceHelper.checkStoredLength(surfaceTreatmentCode.toString(), SURFACE_TREATMENT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "surfaceTreatmentCode"), java.lang.String.valueOf(java.lang.Math.min(SURFACE_TREATMENT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "surfaceTreatmentCode", this.surfaceTreatmentCode, surfaceTreatmentCode));
   }

   /**
    * 적용전선
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String APPLYED_WIRE = "applyedWire";
   static int APPLYED_WIRE_UPPER_LIMIT = -1;
   java.lang.String applyedWire;
   /**
    * 적용전선
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getApplyedWire() {
      return applyedWire;
   }
   /**
    * 적용전선
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setApplyedWire(java.lang.String applyedWire) throws wt.util.WTPropertyVetoException {
      applyedWireValidate(applyedWire);
      this.applyedWire = applyedWire;
   }
   void applyedWireValidate(java.lang.String applyedWire) throws wt.util.WTPropertyVetoException {
      if (APPLYED_WIRE_UPPER_LIMIT < 1) {
         try { APPLYED_WIRE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("applyedWire").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPLYED_WIRE_UPPER_LIMIT = 200; }
      }
      if (applyedWire != null && !wt.fc.PersistenceHelper.checkStoredLength(applyedWire.toString(), APPLYED_WIRE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "applyedWire"), java.lang.String.valueOf(java.lang.Math.min(APPLYED_WIRE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "applyedWire", this.applyedWire, applyedWire));
   }

   /**
    * 주요기능
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String PRIMARY_FUNCTION = "primaryFunction";
   static int PRIMARY_FUNCTION_UPPER_LIMIT = -1;
   java.lang.String primaryFunction;
   /**
    * 주요기능
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getPrimaryFunction() {
      return primaryFunction;
   }
   /**
    * 주요기능
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setPrimaryFunction(java.lang.String primaryFunction) throws wt.util.WTPropertyVetoException {
      primaryFunctionValidate(primaryFunction);
      this.primaryFunction = primaryFunction;
   }
   void primaryFunctionValidate(java.lang.String primaryFunction) throws wt.util.WTPropertyVetoException {
      if (PRIMARY_FUNCTION_UPPER_LIMIT < 1) {
         try { PRIMARY_FUNCTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("primaryFunction").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRIMARY_FUNCTION_UPPER_LIMIT = 4000; }
      }
      if (primaryFunction != null && !wt.fc.PersistenceHelper.checkStoredLength(primaryFunction.toString(), PRIMARY_FUNCTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "primaryFunction"), java.lang.String.valueOf(java.lang.Math.min(PRIMARY_FUNCTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "primaryFunction", this.primaryFunction, primaryFunction));
   }

   /**
    * 향후전망
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String OUTLOOK = "outlook";
   static int OUTLOOK_UPPER_LIMIT = -1;
   java.lang.String outlook;
   /**
    * 향후전망
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getOutlook() {
      return outlook;
   }
   /**
    * 향후전망
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setOutlook(java.lang.String outlook) throws wt.util.WTPropertyVetoException {
      outlookValidate(outlook);
      this.outlook = outlook;
   }
   void outlookValidate(java.lang.String outlook) throws wt.util.WTPropertyVetoException {
      if (OUTLOOK_UPPER_LIMIT < 1) {
         try { OUTLOOK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outlook").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTLOOK_UPPER_LIMIT = 4000; }
      }
      if (outlook != null && !wt.fc.PersistenceHelper.checkStoredLength(outlook.toString(), OUTLOOK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outlook"), java.lang.String.valueOf(java.lang.Math.min(OUTLOOK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outlook", this.outlook, outlook));
   }

   /**
    * 금형비감가기준판매
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String MOLD_DEPRECIATION_TYPE_SALE = "moldDepreciationTypeSale";
   static int MOLD_DEPRECIATION_TYPE_SALE_UPPER_LIMIT = -1;
   java.lang.String moldDepreciationTypeSale;
   /**
    * 금형비감가기준판매
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getMoldDepreciationTypeSale() {
      return moldDepreciationTypeSale;
   }
   /**
    * 금형비감가기준판매
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setMoldDepreciationTypeSale(java.lang.String moldDepreciationTypeSale) throws wt.util.WTPropertyVetoException {
      moldDepreciationTypeSaleValidate(moldDepreciationTypeSale);
      this.moldDepreciationTypeSale = moldDepreciationTypeSale;
   }
   void moldDepreciationTypeSaleValidate(java.lang.String moldDepreciationTypeSale) throws wt.util.WTPropertyVetoException {
      if (MOLD_DEPRECIATION_TYPE_SALE_UPPER_LIMIT < 1) {
         try { MOLD_DEPRECIATION_TYPE_SALE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldDepreciationTypeSale").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_DEPRECIATION_TYPE_SALE_UPPER_LIMIT = 200; }
      }
      if (moldDepreciationTypeSale != null && !wt.fc.PersistenceHelper.checkStoredLength(moldDepreciationTypeSale.toString(), MOLD_DEPRECIATION_TYPE_SALE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldDepreciationTypeSale"), java.lang.String.valueOf(java.lang.Math.min(MOLD_DEPRECIATION_TYPE_SALE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldDepreciationTypeSale", this.moldDepreciationTypeSale, moldDepreciationTypeSale));
   }

   /**
    * 금형비감가기준일반
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String MOLD_DEPRECIATION_TYPE_GENERAL = "moldDepreciationTypeGeneral";
   static int MOLD_DEPRECIATION_TYPE_GENERAL_UPPER_LIMIT = -1;
   java.lang.String moldDepreciationTypeGeneral;
   /**
    * 금형비감가기준일반
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getMoldDepreciationTypeGeneral() {
      return moldDepreciationTypeGeneral;
   }
   /**
    * 금형비감가기준일반
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setMoldDepreciationTypeGeneral(java.lang.String moldDepreciationTypeGeneral) throws wt.util.WTPropertyVetoException {
      moldDepreciationTypeGeneralValidate(moldDepreciationTypeGeneral);
      this.moldDepreciationTypeGeneral = moldDepreciationTypeGeneral;
   }
   void moldDepreciationTypeGeneralValidate(java.lang.String moldDepreciationTypeGeneral) throws wt.util.WTPropertyVetoException {
      if (MOLD_DEPRECIATION_TYPE_GENERAL_UPPER_LIMIT < 1) {
         try { MOLD_DEPRECIATION_TYPE_GENERAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldDepreciationTypeGeneral").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_DEPRECIATION_TYPE_GENERAL_UPPER_LIMIT = 200; }
      }
      if (moldDepreciationTypeGeneral != null && !wt.fc.PersistenceHelper.checkStoredLength(moldDepreciationTypeGeneral.toString(), MOLD_DEPRECIATION_TYPE_GENERAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldDepreciationTypeGeneral"), java.lang.String.valueOf(java.lang.Math.min(MOLD_DEPRECIATION_TYPE_GENERAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldDepreciationTypeGeneral", this.moldDepreciationTypeGeneral, moldDepreciationTypeGeneral));
   }

   /**
    * 금형비감가기준지급
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String MOLD_DEPRECIATION_TYPE_PAYMENT = "moldDepreciationTypePayment";
   static int MOLD_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT = -1;
   java.lang.String moldDepreciationTypePayment;
   /**
    * 금형비감가기준지급
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getMoldDepreciationTypePayment() {
      return moldDepreciationTypePayment;
   }
   /**
    * 금형비감가기준지급
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setMoldDepreciationTypePayment(java.lang.String moldDepreciationTypePayment) throws wt.util.WTPropertyVetoException {
      moldDepreciationTypePaymentValidate(moldDepreciationTypePayment);
      this.moldDepreciationTypePayment = moldDepreciationTypePayment;
   }
   void moldDepreciationTypePaymentValidate(java.lang.String moldDepreciationTypePayment) throws wt.util.WTPropertyVetoException {
      if (MOLD_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT < 1) {
         try { MOLD_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldDepreciationTypePayment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT = 200; }
      }
      if (moldDepreciationTypePayment != null && !wt.fc.PersistenceHelper.checkStoredLength(moldDepreciationTypePayment.toString(), MOLD_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldDepreciationTypePayment"), java.lang.String.valueOf(java.lang.Math.min(MOLD_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldDepreciationTypePayment", this.moldDepreciationTypePayment, moldDepreciationTypePayment));
   }

   /**
    * 금형비감가기준감가기간
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String MOLD_DEPRECIATION_TYPE_PERIOD = "moldDepreciationTypePeriod";
   static int MOLD_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT = -1;
   java.lang.String moldDepreciationTypePeriod;
   /**
    * 금형비감가기준감가기간
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getMoldDepreciationTypePeriod() {
      return moldDepreciationTypePeriod;
   }
   /**
    * 금형비감가기준감가기간
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setMoldDepreciationTypePeriod(java.lang.String moldDepreciationTypePeriod) throws wt.util.WTPropertyVetoException {
      moldDepreciationTypePeriodValidate(moldDepreciationTypePeriod);
      this.moldDepreciationTypePeriod = moldDepreciationTypePeriod;
   }
   void moldDepreciationTypePeriodValidate(java.lang.String moldDepreciationTypePeriod) throws wt.util.WTPropertyVetoException {
      if (MOLD_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT < 1) {
         try { MOLD_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldDepreciationTypePeriod").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT = 200; }
      }
      if (moldDepreciationTypePeriod != null && !wt.fc.PersistenceHelper.checkStoredLength(moldDepreciationTypePeriod.toString(), MOLD_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldDepreciationTypePeriod"), java.lang.String.valueOf(java.lang.Math.min(MOLD_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldDepreciationTypePeriod", this.moldDepreciationTypePeriod, moldDepreciationTypePeriod));
   }

   /**
    * 금형비감가기준기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String MOLD_DEPRECIATION_TYPE_ETC_INFO = "moldDepreciationTypeEtcInfo";
   static int MOLD_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT = -1;
   java.lang.String moldDepreciationTypeEtcInfo;
   /**
    * 금형비감가기준기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getMoldDepreciationTypeEtcInfo() {
      return moldDepreciationTypeEtcInfo;
   }
   /**
    * 금형비감가기준기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setMoldDepreciationTypeEtcInfo(java.lang.String moldDepreciationTypeEtcInfo) throws wt.util.WTPropertyVetoException {
      moldDepreciationTypeEtcInfoValidate(moldDepreciationTypeEtcInfo);
      this.moldDepreciationTypeEtcInfo = moldDepreciationTypeEtcInfo;
   }
   void moldDepreciationTypeEtcInfoValidate(java.lang.String moldDepreciationTypeEtcInfo) throws wt.util.WTPropertyVetoException {
      if (MOLD_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT < 1) {
         try { MOLD_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldDepreciationTypeEtcInfo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT = 200; }
      }
      if (moldDepreciationTypeEtcInfo != null && !wt.fc.PersistenceHelper.checkStoredLength(moldDepreciationTypeEtcInfo.toString(), MOLD_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldDepreciationTypeEtcInfo"), java.lang.String.valueOf(java.lang.Math.min(MOLD_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldDepreciationTypeEtcInfo", this.moldDepreciationTypeEtcInfo, moldDepreciationTypeEtcInfo));
   }

   /**
    * 설비감가기준판매
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String EQUIP_DEPRECIATION_TYPE_SALE = "equipDepreciationTypeSale";
   static int EQUIP_DEPRECIATION_TYPE_SALE_UPPER_LIMIT = -1;
   java.lang.String equipDepreciationTypeSale;
   /**
    * 설비감가기준판매
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getEquipDepreciationTypeSale() {
      return equipDepreciationTypeSale;
   }
   /**
    * 설비감가기준판매
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setEquipDepreciationTypeSale(java.lang.String equipDepreciationTypeSale) throws wt.util.WTPropertyVetoException {
      equipDepreciationTypeSaleValidate(equipDepreciationTypeSale);
      this.equipDepreciationTypeSale = equipDepreciationTypeSale;
   }
   void equipDepreciationTypeSaleValidate(java.lang.String equipDepreciationTypeSale) throws wt.util.WTPropertyVetoException {
      if (EQUIP_DEPRECIATION_TYPE_SALE_UPPER_LIMIT < 1) {
         try { EQUIP_DEPRECIATION_TYPE_SALE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("equipDepreciationTypeSale").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EQUIP_DEPRECIATION_TYPE_SALE_UPPER_LIMIT = 200; }
      }
      if (equipDepreciationTypeSale != null && !wt.fc.PersistenceHelper.checkStoredLength(equipDepreciationTypeSale.toString(), EQUIP_DEPRECIATION_TYPE_SALE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "equipDepreciationTypeSale"), java.lang.String.valueOf(java.lang.Math.min(EQUIP_DEPRECIATION_TYPE_SALE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "equipDepreciationTypeSale", this.equipDepreciationTypeSale, equipDepreciationTypeSale));
   }

   /**
    * 설비감가기준지급
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String EQUIP_DEPRECIATION_TYPE_PAYMENT = "equipDepreciationTypePayment";
   static int EQUIP_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT = -1;
   java.lang.String equipDepreciationTypePayment;
   /**
    * 설비감가기준지급
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getEquipDepreciationTypePayment() {
      return equipDepreciationTypePayment;
   }
   /**
    * 설비감가기준지급
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setEquipDepreciationTypePayment(java.lang.String equipDepreciationTypePayment) throws wt.util.WTPropertyVetoException {
      equipDepreciationTypePaymentValidate(equipDepreciationTypePayment);
      this.equipDepreciationTypePayment = equipDepreciationTypePayment;
   }
   void equipDepreciationTypePaymentValidate(java.lang.String equipDepreciationTypePayment) throws wt.util.WTPropertyVetoException {
      if (EQUIP_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT < 1) {
         try { EQUIP_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("equipDepreciationTypePayment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EQUIP_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT = 200; }
      }
      if (equipDepreciationTypePayment != null && !wt.fc.PersistenceHelper.checkStoredLength(equipDepreciationTypePayment.toString(), EQUIP_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "equipDepreciationTypePayment"), java.lang.String.valueOf(java.lang.Math.min(EQUIP_DEPRECIATION_TYPE_PAYMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "equipDepreciationTypePayment", this.equipDepreciationTypePayment, equipDepreciationTypePayment));
   }

   /**
    * 설비감가기준감가기간
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String EQUIP_DEPRECIATION_TYPE_PERIOD = "equipDepreciationTypePeriod";
   static int EQUIP_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT = -1;
   java.lang.String equipDepreciationTypePeriod;
   /**
    * 설비감가기준감가기간
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getEquipDepreciationTypePeriod() {
      return equipDepreciationTypePeriod;
   }
   /**
    * 설비감가기준감가기간
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setEquipDepreciationTypePeriod(java.lang.String equipDepreciationTypePeriod) throws wt.util.WTPropertyVetoException {
      equipDepreciationTypePeriodValidate(equipDepreciationTypePeriod);
      this.equipDepreciationTypePeriod = equipDepreciationTypePeriod;
   }
   void equipDepreciationTypePeriodValidate(java.lang.String equipDepreciationTypePeriod) throws wt.util.WTPropertyVetoException {
      if (EQUIP_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT < 1) {
         try { EQUIP_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("equipDepreciationTypePeriod").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EQUIP_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT = 200; }
      }
      if (equipDepreciationTypePeriod != null && !wt.fc.PersistenceHelper.checkStoredLength(equipDepreciationTypePeriod.toString(), EQUIP_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "equipDepreciationTypePeriod"), java.lang.String.valueOf(java.lang.Math.min(EQUIP_DEPRECIATION_TYPE_PERIOD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "equipDepreciationTypePeriod", this.equipDepreciationTypePeriod, equipDepreciationTypePeriod));
   }

   /**
    * 설비감가기준기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String EQUIP_DEPRECIATION_TYPE_ETC_INFO = "equipDepreciationTypeEtcInfo";
   static int EQUIP_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT = -1;
   java.lang.String equipDepreciationTypeEtcInfo;
   /**
    * 설비감가기준기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getEquipDepreciationTypeEtcInfo() {
      return equipDepreciationTypeEtcInfo;
   }
   /**
    * 설비감가기준기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setEquipDepreciationTypeEtcInfo(java.lang.String equipDepreciationTypeEtcInfo) throws wt.util.WTPropertyVetoException {
      equipDepreciationTypeEtcInfoValidate(equipDepreciationTypeEtcInfo);
      this.equipDepreciationTypeEtcInfo = equipDepreciationTypeEtcInfo;
   }
   void equipDepreciationTypeEtcInfoValidate(java.lang.String equipDepreciationTypeEtcInfo) throws wt.util.WTPropertyVetoException {
      if (EQUIP_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT < 1) {
         try { EQUIP_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("equipDepreciationTypeEtcInfo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EQUIP_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT = 200; }
      }
      if (equipDepreciationTypeEtcInfo != null && !wt.fc.PersistenceHelper.checkStoredLength(equipDepreciationTypeEtcInfo.toString(), EQUIP_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "equipDepreciationTypeEtcInfo"), java.lang.String.valueOf(java.lang.Math.min(EQUIP_DEPRECIATION_TYPE_ETC_INFO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "equipDepreciationTypeEtcInfo", this.equipDepreciationTypeEtcInfo, equipDepreciationTypeEtcInfo));
   }

   /**
    * Device사양
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String DEVICE_SPECIFICATION = "deviceSpecification";
   static int DEVICE_SPECIFICATION_UPPER_LIMIT = -1;
   java.lang.String deviceSpecification;
   /**
    * Device사양
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getDeviceSpecification() {
      return deviceSpecification;
   }
   /**
    * Device사양
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setDeviceSpecification(java.lang.String deviceSpecification) throws wt.util.WTPropertyVetoException {
      deviceSpecificationValidate(deviceSpecification);
      this.deviceSpecification = deviceSpecification;
   }
   void deviceSpecificationValidate(java.lang.String deviceSpecification) throws wt.util.WTPropertyVetoException {
      if (DEVICE_SPECIFICATION_UPPER_LIMIT < 1) {
         try { DEVICE_SPECIFICATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deviceSpecification").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEVICE_SPECIFICATION_UPPER_LIMIT = 200; }
      }
      if (deviceSpecification != null && !wt.fc.PersistenceHelper.checkStoredLength(deviceSpecification.toString(), DEVICE_SPECIFICATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deviceSpecification"), java.lang.String.valueOf(java.lang.Math.min(DEVICE_SPECIFICATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deviceSpecification", this.deviceSpecification, deviceSpecification));
   }

   /**
    * 환경규제항목
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ENVIRONMENTAL_REGULATION_ITEM = "environmentalRegulationItem";
   static int ENVIRONMENTAL_REGULATION_ITEM_UPPER_LIMIT = -1;
   java.lang.String environmentalRegulationItem;
   /**
    * 환경규제항목
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getEnvironmentalRegulationItem() {
      return environmentalRegulationItem;
   }
   /**
    * 환경규제항목
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setEnvironmentalRegulationItem(java.lang.String environmentalRegulationItem) throws wt.util.WTPropertyVetoException {
      environmentalRegulationItemValidate(environmentalRegulationItem);
      this.environmentalRegulationItem = environmentalRegulationItem;
   }
   void environmentalRegulationItemValidate(java.lang.String environmentalRegulationItem) throws wt.util.WTPropertyVetoException {
      if (ENVIRONMENTAL_REGULATION_ITEM_UPPER_LIMIT < 1) {
         try { ENVIRONMENTAL_REGULATION_ITEM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("environmentalRegulationItem").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ENVIRONMENTAL_REGULATION_ITEM_UPPER_LIMIT = 200; }
      }
      if (environmentalRegulationItem != null && !wt.fc.PersistenceHelper.checkStoredLength(environmentalRegulationItem.toString(), ENVIRONMENTAL_REGULATION_ITEM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "environmentalRegulationItem"), java.lang.String.valueOf(java.lang.Math.min(ENVIRONMENTAL_REGULATION_ITEM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "environmentalRegulationItem", this.environmentalRegulationItem, environmentalRegulationItem));
   }

   /**
    * 고객요청기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String BUYER_ETC_REQUIREMENT = "buyerEtcRequirement";
   static int BUYER_ETC_REQUIREMENT_UPPER_LIMIT = -1;
   java.lang.String buyerEtcRequirement;
   /**
    * 고객요청기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getBuyerEtcRequirement() {
      return buyerEtcRequirement;
   }
   /**
    * 고객요청기타사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setBuyerEtcRequirement(java.lang.String buyerEtcRequirement) throws wt.util.WTPropertyVetoException {
      buyerEtcRequirementValidate(buyerEtcRequirement);
      this.buyerEtcRequirement = buyerEtcRequirement;
   }
   void buyerEtcRequirementValidate(java.lang.String buyerEtcRequirement) throws wt.util.WTPropertyVetoException {
      if (BUYER_ETC_REQUIREMENT_UPPER_LIMIT < 1) {
         try { BUYER_ETC_REQUIREMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("buyerEtcRequirement").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BUYER_ETC_REQUIREMENT_UPPER_LIMIT = 200; }
      }
      if (buyerEtcRequirement != null && !wt.fc.PersistenceHelper.checkStoredLength(buyerEtcRequirement.toString(), BUYER_ETC_REQUIREMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "buyerEtcRequirement"), java.lang.String.valueOf(java.lang.Math.min(BUYER_ETC_REQUIREMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "buyerEtcRequirement", this.buyerEtcRequirement, buyerEtcRequirement));
   }

   /**
    * 영업추가요구사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String SALES_ADDITIONAL_REQUIREMENT = "salesAdditionalRequirement";
   static int SALES_ADDITIONAL_REQUIREMENT_UPPER_LIMIT = -1;
   java.lang.String salesAdditionalRequirement;
   /**
    * 영업추가요구사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getSalesAdditionalRequirement() {
      return salesAdditionalRequirement;
   }
   /**
    * 영업추가요구사항
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setSalesAdditionalRequirement(java.lang.String salesAdditionalRequirement) throws wt.util.WTPropertyVetoException {
      salesAdditionalRequirementValidate(salesAdditionalRequirement);
      this.salesAdditionalRequirement = salesAdditionalRequirement;
   }
   void salesAdditionalRequirementValidate(java.lang.String salesAdditionalRequirement) throws wt.util.WTPropertyVetoException {
      if (SALES_ADDITIONAL_REQUIREMENT_UPPER_LIMIT < 1) {
         try { SALES_ADDITIONAL_REQUIREMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesAdditionalRequirement").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_ADDITIONAL_REQUIREMENT_UPPER_LIMIT = 4000; }
      }
      if (salesAdditionalRequirement != null && !wt.fc.PersistenceHelper.checkStoredLength(salesAdditionalRequirement.toString(), SALES_ADDITIONAL_REQUIREMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesAdditionalRequirement"), java.lang.String.valueOf(java.lang.Math.min(SALES_ADDITIONAL_REQUIREMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesAdditionalRequirement", this.salesAdditionalRequirement, salesAdditionalRequirement));
   }

   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * 기타1
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * 기타2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타3
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * 기타3
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타4
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * 기타4
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타5
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * 기타5
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타6
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * 기타6
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타7
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * 기타7
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타8
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * 기타8
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타9
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * 기타9
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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
    * 기타10
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * 기타10
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
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

   /**
    * 기타11
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE11 = "attribute11";
   static int ATTRIBUTE11_UPPER_LIMIT = -1;
   java.lang.String attribute11;
   /**
    * 기타11
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute11() {
      return attribute11;
   }
   /**
    * 기타11
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setAttribute11(java.lang.String attribute11) throws wt.util.WTPropertyVetoException {
      attribute11Validate(attribute11);
      this.attribute11 = attribute11;
   }
   void attribute11Validate(java.lang.String attribute11) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE11_UPPER_LIMIT < 1) {
         try { ATTRIBUTE11_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute11").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE11_UPPER_LIMIT = 4000; }
      }
      if (attribute11 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute11.toString(), ATTRIBUTE11_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute11"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE11_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute11", this.attribute11, attribute11));
   }

   /**
    * 기타12
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String ATTRIBUTE12 = "attribute12";
   static int ATTRIBUTE12_UPPER_LIMIT = -1;
   java.lang.String attribute12;
   /**
    * 기타12
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getAttribute12() {
      return attribute12;
   }
   /**
    * 기타12
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setAttribute12(java.lang.String attribute12) throws wt.util.WTPropertyVetoException {
      attribute12Validate(attribute12);
      this.attribute12 = attribute12;
   }
   void attribute12Validate(java.lang.String attribute12) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE12_UPPER_LIMIT < 1) {
         try { ATTRIBUTE12_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute12").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE12_UPPER_LIMIT = 4000; }
      }
      if (attribute12 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute12.toString(), ATTRIBUTE12_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute12"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE12_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute12", this.attribute12, attribute12));
   }

   /**
    * 초도품제출일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String INITIAL_SAMPLE_SUMMIT_DATE2 = "initialSampleSummitDate2";
   java.sql.Timestamp initialSampleSummitDate2;
   /**
    * 초도품제출일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getInitialSampleSummitDate2() {
      return initialSampleSummitDate2;
   }
   /**
    * 초도품제출일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setInitialSampleSummitDate2(java.sql.Timestamp initialSampleSummitDate2) throws wt.util.WTPropertyVetoException {
      initialSampleSummitDate2Validate(initialSampleSummitDate2);
      this.initialSampleSummitDate2 = initialSampleSummitDate2;
   }
   void initialSampleSummitDate2Validate(java.sql.Timestamp initialSampleSummitDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * esir일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String E_SIRDATE2 = "eSIRDate2";
   java.sql.Timestamp eSIRDate2;
   /**
    * esir일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getESIRDate2() {
      return eSIRDate2;
   }
   /**
    * esir일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setESIRDate2(java.sql.Timestamp eSIRDate2) throws wt.util.WTPropertyVetoException {
      eSIRDate2Validate(eSIRDate2);
      this.eSIRDate2 = eSIRDate2;
   }
   void eSIRDate2Validate(java.sql.Timestamp eSIRDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * isir일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String I_SIRDATE2 = "iSIRDate2";
   java.sql.Timestamp iSIRDate2;
   /**
    * isir일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getISIRDate2() {
      return iSIRDate2;
   }
   /**
    * isir일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setISIRDate2(java.sql.Timestamp iSIRDate2) throws wt.util.WTPropertyVetoException {
      iSIRDate2Validate(iSIRDate2);
      this.iSIRDate2 = iSIRDate2;
   }
   void iSIRDate2Validate(java.sql.Timestamp iSIRDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ket양산일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String KET_MASS_PRODUCTION_DATE2 = "ketMassProductionDate2";
   java.sql.Timestamp ketMassProductionDate2;
   /**
    * ket양산일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getKetMassProductionDate2() {
      return ketMassProductionDate2;
   }
   /**
    * ket양산일정2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setKetMassProductionDate2(java.sql.Timestamp ketMassProductionDate2) throws wt.util.WTPropertyVetoException {
      ketMassProductionDate2Validate(ketMassProductionDate2);
      this.ketMassProductionDate2 = ketMassProductionDate2;
   }
   void ketMassProductionDate2Validate(java.sql.Timestamp ketMassProductionDate2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 고객sop2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String PRODUCT_SALE_FIRST_YEAR2 = "productSaleFirstYear2";
   java.sql.Timestamp productSaleFirstYear2;
   /**
    * 고객sop2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.sql.Timestamp getProductSaleFirstYear2() {
      return productSaleFirstYear2;
   }
   /**
    * 고객sop2
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setProductSaleFirstYear2(java.sql.Timestamp productSaleFirstYear2) throws wt.util.WTPropertyVetoException {
      productSaleFirstYear2Validate(productSaleFirstYear2);
      this.productSaleFirstYear2 = productSaleFirstYear2;
   }
   void productSaleFirstYear2Validate(java.sql.Timestamp productSaleFirstYear2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 진행일정명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public static final java.lang.String SCHEDULE_NAME = "scheduleName";
   static int SCHEDULE_NAME_UPPER_LIMIT = -1;
   java.lang.String scheduleName;
   /**
    * 진행일정명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public java.lang.String getScheduleName() {
      return scheduleName;
   }
   /**
    * 진행일정명
    *
    * @see e3ps.dms.entity.KETDevelopmentRequest
    */
   public void setScheduleName(java.lang.String scheduleName) throws wt.util.WTPropertyVetoException {
      scheduleNameValidate(scheduleName);
      this.scheduleName = scheduleName;
   }
   void scheduleNameValidate(java.lang.String scheduleName) throws wt.util.WTPropertyVetoException {
      if (SCHEDULE_NAME_UPPER_LIMIT < 1) {
         try { SCHEDULE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scheduleName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCHEDULE_NAME_UPPER_LIMIT = 200; }
      }
      if (scheduleName != null && !wt.fc.PersistenceHelper.checkStoredLength(scheduleName.toString(), SCHEDULE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scheduleName"), java.lang.String.valueOf(java.lang.Math.min(SCHEDULE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scheduleName", this.scheduleName, scheduleName));
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

   public static final long EXTERNALIZATION_VERSION_UID = -8136249337044195730L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( applyedWire );
      output.writeObject( attribute1 );
      output.writeObject( attribute10 );
      output.writeObject( attribute11 );
      output.writeObject( attribute12 );
      output.writeObject( attribute2 );
      output.writeObject( attribute3 );
      output.writeObject( attribute4 );
      output.writeObject( attribute5 );
      output.writeObject( attribute6 );
      output.writeObject( attribute7 );
      output.writeObject( attribute8 );
      output.writeObject( attribute9 );
      output.writeObject( buyerEtcRequirement );
      output.writeObject( costSubmitDate );
      output.writeObject( deptName );
      output.writeObject( devProductName );
      output.writeObject( devReviewDetailComment );
      output.writeObject( devReviewTypeCode );
      output.writeObject( devReviewTypeEtc );
      output.writeObject( developmentStep );
      output.writeObject( deviceSpecification );
      output.writeObject( divisionCode );
      output.writeObject( drRequestDate );
      output.writeObject( eSIRDate );
      output.writeObject( eSIRDate2 );
      output.writeObject( environmentalRegulationItem );
      output.writeObject( equipDepreciationTypeEtcInfo );
      output.writeObject( equipDepreciationTypePayment );
      output.writeObject( equipDepreciationTypePeriod );
      output.writeObject( equipDepreciationTypeSale );
      output.writeObject( etcSpecification );
      output.writeObject( iSIRDate );
      output.writeObject( iSIRDate2 );
      output.writeObject( initialSampleSummitDate );
      output.writeObject( initialSampleSummitDate2 );
      output.writeObject( isCostSubmit );
      output.writeObject( isDRRequest );
      output.writeObject( isProposalSubmit );
      output.writeObject( ketMassProductionDate );
      output.writeObject( ketMassProductionDate2 );
      output.writeObject( lastUsingBuyer );
      output.writeObject( lastUsingBuyerManager );
      output.writeObject( materialSubMaterial );
      output.writeObject( moldDepreciationTypeEtcInfo );
      output.writeObject( moldDepreciationTypeGeneral );
      output.writeObject( moldDepreciationTypePayment );
      output.writeObject( moldDepreciationTypePeriod );
      output.writeObject( moldDepreciationTypeSale );
      output.writeObject( outlook );
      output.writeObject( primaryFunction );
      output.writeObject( productCategoryCode );
      output.writeObject( productSaleFirstYear );
      output.writeObject( productSaleFirstYear2 );
      output.writeObject( productTypeCode );
      output.writeObject( projectOID );
      output.writeObject( proposalSubmitDate );
      output.writeObject( reception );
      output.writeObject( repCarType );
      output.writeObject( requestBuyer );
      output.writeObject( requestBuyerManager );
      output.writeObject( salesAdditionalRequirement );
      output.writeObject( scheduleName );
      output.writeObject( surfaceTreatmentCode );
      output.writeObject( tabSize );
   }

   protected void super_writeExternal_KETDevelopmentRequest(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.dms.entity.KETDevelopmentRequest) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDevelopmentRequest(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "applyedWire", applyedWire );
      output.setString( "attribute1", attribute1 );
      output.setString( "attribute10", attribute10 );
      output.setString( "attribute11", attribute11 );
      output.setString( "attribute12", attribute12 );
      output.setString( "attribute2", attribute2 );
      output.setString( "attribute3", attribute3 );
      output.setString( "attribute4", attribute4 );
      output.setString( "attribute5", attribute5 );
      output.setString( "attribute6", attribute6 );
      output.setString( "attribute7", attribute7 );
      output.setString( "attribute8", attribute8 );
      output.setString( "attribute9", attribute9 );
      output.setString( "buyerEtcRequirement", buyerEtcRequirement );
      output.setTimestamp( "costSubmitDate", costSubmitDate );
      output.setString( "deptName", deptName );
      output.setString( "devProductName", devProductName );
      output.setString( "devReviewDetailComment", devReviewDetailComment );
      output.setString( "devReviewTypeCode", devReviewTypeCode );
      output.setString( "devReviewTypeEtc", devReviewTypeEtc );
      output.setString( "developmentStep", developmentStep );
      output.setString( "deviceSpecification", deviceSpecification );
      output.setString( "divisionCode", divisionCode );
      output.setTimestamp( "drRequestDate", drRequestDate );
      output.setTimestamp( "eSIRDate", eSIRDate );
      output.setTimestamp( "eSIRDate2", eSIRDate2 );
      output.setString( "environmentalRegulationItem", environmentalRegulationItem );
      output.setString( "equipDepreciationTypeEtcInfo", equipDepreciationTypeEtcInfo );
      output.setString( "equipDepreciationTypePayment", equipDepreciationTypePayment );
      output.setString( "equipDepreciationTypePeriod", equipDepreciationTypePeriod );
      output.setString( "equipDepreciationTypeSale", equipDepreciationTypeSale );
      output.setString( "etcSpecification", etcSpecification );
      output.setTimestamp( "iSIRDate", iSIRDate );
      output.setTimestamp( "iSIRDate2", iSIRDate2 );
      output.setTimestamp( "initialSampleSummitDate", initialSampleSummitDate );
      output.setTimestamp( "initialSampleSummitDate2", initialSampleSummitDate2 );
      output.setString( "isCostSubmit", isCostSubmit );
      output.setString( "isDRRequest", isDRRequest );
      output.setString( "isProposalSubmit", isProposalSubmit );
      output.setTimestamp( "ketMassProductionDate", ketMassProductionDate );
      output.setTimestamp( "ketMassProductionDate2", ketMassProductionDate2 );
      output.setString( "lastUsingBuyer", lastUsingBuyer );
      output.setString( "lastUsingBuyerManager", lastUsingBuyerManager );
      output.setString( "materialSubMaterial", materialSubMaterial );
      output.setString( "moldDepreciationTypeEtcInfo", moldDepreciationTypeEtcInfo );
      output.setString( "moldDepreciationTypeGeneral", moldDepreciationTypeGeneral );
      output.setString( "moldDepreciationTypePayment", moldDepreciationTypePayment );
      output.setString( "moldDepreciationTypePeriod", moldDepreciationTypePeriod );
      output.setString( "moldDepreciationTypeSale", moldDepreciationTypeSale );
      output.setString( "outlook", outlook );
      output.setString( "primaryFunction", primaryFunction );
      output.setString( "productCategoryCode", productCategoryCode );
      output.setString( "productSaleFirstYear", productSaleFirstYear );
      output.setTimestamp( "productSaleFirstYear2", productSaleFirstYear2 );
      output.setString( "productTypeCode", productTypeCode );
      output.setString( "projectOID", projectOID );
      output.setTimestamp( "proposalSubmitDate", proposalSubmitDate );
      output.setString( "reception", reception );
      output.setString( "repCarType", repCarType );
      output.setString( "requestBuyer", requestBuyer );
      output.setString( "requestBuyerManager", requestBuyerManager );
      output.setString( "salesAdditionalRequirement", salesAdditionalRequirement );
      output.setString( "scheduleName", scheduleName );
      output.setString( "surfaceTreatmentCode", surfaceTreatmentCode );
      output.setString( "tabSize", tabSize );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      applyedWire = input.getString( "applyedWire" );
      attribute1 = input.getString( "attribute1" );
      attribute10 = input.getString( "attribute10" );
      attribute11 = input.getString( "attribute11" );
      attribute12 = input.getString( "attribute12" );
      attribute2 = input.getString( "attribute2" );
      attribute3 = input.getString( "attribute3" );
      attribute4 = input.getString( "attribute4" );
      attribute5 = input.getString( "attribute5" );
      attribute6 = input.getString( "attribute6" );
      attribute7 = input.getString( "attribute7" );
      attribute8 = input.getString( "attribute8" );
      attribute9 = input.getString( "attribute9" );
      buyerEtcRequirement = input.getString( "buyerEtcRequirement" );
      costSubmitDate = input.getTimestamp( "costSubmitDate" );
      deptName = input.getString( "deptName" );
      devProductName = input.getString( "devProductName" );
      devReviewDetailComment = input.getString( "devReviewDetailComment" );
      devReviewTypeCode = input.getString( "devReviewTypeCode" );
      devReviewTypeEtc = input.getString( "devReviewTypeEtc" );
      developmentStep = input.getString( "developmentStep" );
      deviceSpecification = input.getString( "deviceSpecification" );
      divisionCode = input.getString( "divisionCode" );
      drRequestDate = input.getTimestamp( "drRequestDate" );
      eSIRDate = input.getTimestamp( "eSIRDate" );
      eSIRDate2 = input.getTimestamp( "eSIRDate2" );
      environmentalRegulationItem = input.getString( "environmentalRegulationItem" );
      equipDepreciationTypeEtcInfo = input.getString( "equipDepreciationTypeEtcInfo" );
      equipDepreciationTypePayment = input.getString( "equipDepreciationTypePayment" );
      equipDepreciationTypePeriod = input.getString( "equipDepreciationTypePeriod" );
      equipDepreciationTypeSale = input.getString( "equipDepreciationTypeSale" );
      etcSpecification = input.getString( "etcSpecification" );
      iSIRDate = input.getTimestamp( "iSIRDate" );
      iSIRDate2 = input.getTimestamp( "iSIRDate2" );
      initialSampleSummitDate = input.getTimestamp( "initialSampleSummitDate" );
      initialSampleSummitDate2 = input.getTimestamp( "initialSampleSummitDate2" );
      isCostSubmit = input.getString( "isCostSubmit" );
      isDRRequest = input.getString( "isDRRequest" );
      isProposalSubmit = input.getString( "isProposalSubmit" );
      ketMassProductionDate = input.getTimestamp( "ketMassProductionDate" );
      ketMassProductionDate2 = input.getTimestamp( "ketMassProductionDate2" );
      lastUsingBuyer = input.getString( "lastUsingBuyer" );
      lastUsingBuyerManager = input.getString( "lastUsingBuyerManager" );
      materialSubMaterial = input.getString( "materialSubMaterial" );
      moldDepreciationTypeEtcInfo = input.getString( "moldDepreciationTypeEtcInfo" );
      moldDepreciationTypeGeneral = input.getString( "moldDepreciationTypeGeneral" );
      moldDepreciationTypePayment = input.getString( "moldDepreciationTypePayment" );
      moldDepreciationTypePeriod = input.getString( "moldDepreciationTypePeriod" );
      moldDepreciationTypeSale = input.getString( "moldDepreciationTypeSale" );
      outlook = input.getString( "outlook" );
      primaryFunction = input.getString( "primaryFunction" );
      productCategoryCode = input.getString( "productCategoryCode" );
      productSaleFirstYear = input.getString( "productSaleFirstYear" );
      productSaleFirstYear2 = input.getTimestamp( "productSaleFirstYear2" );
      productTypeCode = input.getString( "productTypeCode" );
      projectOID = input.getString( "projectOID" );
      proposalSubmitDate = input.getTimestamp( "proposalSubmitDate" );
      reception = input.getString( "reception" );
      repCarType = input.getString( "repCarType" );
      requestBuyer = input.getString( "requestBuyer" );
      requestBuyerManager = input.getString( "requestBuyerManager" );
      salesAdditionalRequirement = input.getString( "salesAdditionalRequirement" );
      scheduleName = input.getString( "scheduleName" );
      surfaceTreatmentCode = input.getString( "surfaceTreatmentCode" );
      tabSize = input.getString( "tabSize" );
   }

   boolean readVersion_8136249337044195730L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      applyedWire = (java.lang.String) input.readObject();
      attribute1 = (java.lang.String) input.readObject();
      attribute10 = (java.lang.String) input.readObject();
      attribute11 = (java.lang.String) input.readObject();
      attribute12 = (java.lang.String) input.readObject();
      attribute2 = (java.lang.String) input.readObject();
      attribute3 = (java.lang.String) input.readObject();
      attribute4 = (java.lang.String) input.readObject();
      attribute5 = (java.lang.String) input.readObject();
      attribute6 = (java.lang.String) input.readObject();
      attribute7 = (java.lang.String) input.readObject();
      attribute8 = (java.lang.String) input.readObject();
      attribute9 = (java.lang.String) input.readObject();
      buyerEtcRequirement = (java.lang.String) input.readObject();
      costSubmitDate = (java.sql.Timestamp) input.readObject();
      deptName = (java.lang.String) input.readObject();
      devProductName = (java.lang.String) input.readObject();
      devReviewDetailComment = (java.lang.String) input.readObject();
      devReviewTypeCode = (java.lang.String) input.readObject();
      devReviewTypeEtc = (java.lang.String) input.readObject();
      developmentStep = (java.lang.String) input.readObject();
      deviceSpecification = (java.lang.String) input.readObject();
      divisionCode = (java.lang.String) input.readObject();
      drRequestDate = (java.sql.Timestamp) input.readObject();
      eSIRDate = (java.sql.Timestamp) input.readObject();
      eSIRDate2 = (java.sql.Timestamp) input.readObject();
      environmentalRegulationItem = (java.lang.String) input.readObject();
      equipDepreciationTypeEtcInfo = (java.lang.String) input.readObject();
      equipDepreciationTypePayment = (java.lang.String) input.readObject();
      equipDepreciationTypePeriod = (java.lang.String) input.readObject();
      equipDepreciationTypeSale = (java.lang.String) input.readObject();
      etcSpecification = (java.lang.String) input.readObject();
      iSIRDate = (java.sql.Timestamp) input.readObject();
      iSIRDate2 = (java.sql.Timestamp) input.readObject();
      initialSampleSummitDate = (java.sql.Timestamp) input.readObject();
      initialSampleSummitDate2 = (java.sql.Timestamp) input.readObject();
      isCostSubmit = (java.lang.String) input.readObject();
      isDRRequest = (java.lang.String) input.readObject();
      isProposalSubmit = (java.lang.String) input.readObject();
      ketMassProductionDate = (java.sql.Timestamp) input.readObject();
      ketMassProductionDate2 = (java.sql.Timestamp) input.readObject();
      lastUsingBuyer = (java.lang.String) input.readObject();
      lastUsingBuyerManager = (java.lang.String) input.readObject();
      materialSubMaterial = (java.lang.String) input.readObject();
      moldDepreciationTypeEtcInfo = (java.lang.String) input.readObject();
      moldDepreciationTypeGeneral = (java.lang.String) input.readObject();
      moldDepreciationTypePayment = (java.lang.String) input.readObject();
      moldDepreciationTypePeriod = (java.lang.String) input.readObject();
      moldDepreciationTypeSale = (java.lang.String) input.readObject();
      outlook = (java.lang.String) input.readObject();
      primaryFunction = (java.lang.String) input.readObject();
      productCategoryCode = (java.lang.String) input.readObject();
      productSaleFirstYear = (java.lang.String) input.readObject();
      productSaleFirstYear2 = (java.sql.Timestamp) input.readObject();
      productTypeCode = (java.lang.String) input.readObject();
      projectOID = (java.lang.String) input.readObject();
      proposalSubmitDate = (java.sql.Timestamp) input.readObject();
      reception = (java.lang.String) input.readObject();
      repCarType = (java.lang.String) input.readObject();
      requestBuyer = (java.lang.String) input.readObject();
      requestBuyerManager = (java.lang.String) input.readObject();
      salesAdditionalRequirement = (java.lang.String) input.readObject();
      scheduleName = (java.lang.String) input.readObject();
      surfaceTreatmentCode = (java.lang.String) input.readObject();
      tabSize = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDevelopmentRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8136249337044195730L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDevelopmentRequest( _KETDevelopmentRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
