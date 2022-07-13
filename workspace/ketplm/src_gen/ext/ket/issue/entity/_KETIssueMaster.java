package ext.ket.issue.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETIssueMaster extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.issue.entity.entityResource";
   static final java.lang.String CLASSNAME = KETIssueMaster.class.getName();

   /**
    * 요청No
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String REQ_NO = "reqNo";
   static int REQ_NO_UPPER_LIMIT = -1;
   java.lang.String reqNo;
   /**
    * 요청No
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getReqNo() {
      return reqNo;
   }
   /**
    * 요청No
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setReqNo(java.lang.String reqNo) throws wt.util.WTPropertyVetoException {
      reqNoValidate(reqNo);
      this.reqNo = reqNo;
   }
   void reqNoValidate(java.lang.String reqNo) throws wt.util.WTPropertyVetoException {
      if (REQ_NO_UPPER_LIMIT < 1) {
         try { REQ_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reqNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQ_NO_UPPER_LIMIT = 200; }
      }
      if (reqNo != null && !wt.fc.PersistenceHelper.checkStoredLength(reqNo.toString(), REQ_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reqNo"), java.lang.String.valueOf(java.lang.Math.min(REQ_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reqNo", this.reqNo, reqNo));
   }

   /**
    * 요청명
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String REQ_NAME = "reqName";
   static int REQ_NAME_UPPER_LIMIT = -1;
   java.lang.String reqName;
   /**
    * 요청명
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getReqName() {
      return reqName;
   }
   /**
    * 요청명
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setReqName(java.lang.String reqName) throws wt.util.WTPropertyVetoException {
      reqNameValidate(reqName);
      this.reqName = reqName;
   }
   void reqNameValidate(java.lang.String reqName) throws wt.util.WTPropertyVetoException {
      if (REQ_NAME_UPPER_LIMIT < 1) {
         try { REQ_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reqName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQ_NAME_UPPER_LIMIT = 200; }
      }
      if (reqName != null && !wt.fc.PersistenceHelper.checkStoredLength(reqName.toString(), REQ_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reqName"), java.lang.String.valueOf(java.lang.Math.min(REQ_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reqName", this.reqName, reqName));
   }

   /**
    * 요청구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String REQ_CODE = "reqCode";
   static int REQ_CODE_UPPER_LIMIT = -1;
   java.lang.String reqCode;
   /**
    * 요청구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getReqCode() {
      return reqCode;
   }
   /**
    * 요청구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setReqCode(java.lang.String reqCode) throws wt.util.WTPropertyVetoException {
      reqCodeValidate(reqCode);
      this.reqCode = reqCode;
   }
   void reqCodeValidate(java.lang.String reqCode) throws wt.util.WTPropertyVetoException {
      if (REQ_CODE_UPPER_LIMIT < 1) {
         try { REQ_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reqCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQ_CODE_UPPER_LIMIT = 200; }
      }
      if (reqCode != null && !wt.fc.PersistenceHelper.checkStoredLength(reqCode.toString(), REQ_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reqCode"), java.lang.String.valueOf(java.lang.Math.min(REQ_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reqCode", this.reqCode, reqCode));
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String DEPT_CODE = "deptCode";
   static int DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String deptCode;
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getDeptCode() {
      return deptCode;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
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
    * 상세구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String DETAIL_CODE = "detailCode";
   static int DETAIL_CODE_UPPER_LIMIT = -1;
   java.lang.String detailCode;
   /**
    * 상세구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getDetailCode() {
      return detailCode;
   }
   /**
    * 상세구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setDetailCode(java.lang.String detailCode) throws wt.util.WTPropertyVetoException {
      detailCodeValidate(detailCode);
      this.detailCode = detailCode;
   }
   void detailCodeValidate(java.lang.String detailCode) throws wt.util.WTPropertyVetoException {
      if (DETAIL_CODE_UPPER_LIMIT < 1) {
         try { DETAIL_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("detailCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DETAIL_CODE_UPPER_LIMIT = 200; }
      }
      if (detailCode != null && !wt.fc.PersistenceHelper.checkStoredLength(detailCode.toString(), DETAIL_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "detailCode"), java.lang.String.valueOf(java.lang.Math.min(DETAIL_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "detailCode", this.detailCode, detailCode));
   }

   /**
    * 등급
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String RANK = "rank";
   static int RANK_UPPER_LIMIT = -1;
   java.lang.String rank;
   /**
    * 등급
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getRank() {
      return rank;
   }
   /**
    * 등급
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setRank(java.lang.String rank) throws wt.util.WTPropertyVetoException {
      rankValidate(rank);
      this.rank = rank;
   }
   void rankValidate(java.lang.String rank) throws wt.util.WTPropertyVetoException {
      if (RANK_UPPER_LIMIT < 1) {
         try { RANK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rank").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RANK_UPPER_LIMIT = 200; }
      }
      if (rank != null && !wt.fc.PersistenceHelper.checkStoredLength(rank.toString(), RANK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rank"), java.lang.String.valueOf(java.lang.Math.min(RANK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rank", this.rank, rank));
   }

   /**
    * 완료 요청일자
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * 완료 요청일자
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * 완료 요청일자
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 완료일
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String COMPLETE_DATE = "completeDate";
   java.sql.Timestamp completeDate;
   /**
    * 완료일
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.sql.Timestamp getCompleteDate() {
      return completeDate;
   }
   /**
    * 완료일
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setCompleteDate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
      completeDateValidate(completeDate);
      this.completeDate = completeDate;
   }
   void completeDateValidate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 최종고객
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String LAST_CUSTOMER = "lastCustomer";
   static int LAST_CUSTOMER_UPPER_LIMIT = -1;
   java.lang.String lastCustomer;
   /**
    * 최종고객
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getLastCustomer() {
      return lastCustomer;
   }
   /**
    * 최종고객
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setLastCustomer(java.lang.String lastCustomer) throws wt.util.WTPropertyVetoException {
      lastCustomerValidate(lastCustomer);
      this.lastCustomer = lastCustomer;
   }
   void lastCustomerValidate(java.lang.String lastCustomer) throws wt.util.WTPropertyVetoException {
      if (LAST_CUSTOMER_UPPER_LIMIT < 1) {
         try { LAST_CUSTOMER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lastCustomer").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LAST_CUSTOMER_UPPER_LIMIT = 200; }
      }
      if (lastCustomer != null && !wt.fc.PersistenceHelper.checkStoredLength(lastCustomer.toString(), LAST_CUSTOMER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastCustomer"), java.lang.String.valueOf(java.lang.Math.min(LAST_CUSTOMER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lastCustomer", this.lastCustomer, lastCustomer));
   }

   /**
    * 접점고객
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String SUB_CONTRACTOR = "subContractor";
   static int SUB_CONTRACTOR_UPPER_LIMIT = -1;
   java.lang.String subContractor;
   /**
    * 접점고객
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getSubContractor() {
      return subContractor;
   }
   /**
    * 접점고객
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setSubContractor(java.lang.String subContractor) throws wt.util.WTPropertyVetoException {
      subContractorValidate(subContractor);
      this.subContractor = subContractor;
   }
   void subContractorValidate(java.lang.String subContractor) throws wt.util.WTPropertyVetoException {
      if (SUB_CONTRACTOR_UPPER_LIMIT < 1) {
         try { SUB_CONTRACTOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subContractor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_CONTRACTOR_UPPER_LIMIT = 200; }
      }
      if (subContractor != null && !wt.fc.PersistenceHelper.checkStoredLength(subContractor.toString(), SUB_CONTRACTOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subContractor"), java.lang.String.valueOf(java.lang.Math.min(SUB_CONTRACTOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subContractor", this.subContractor, subContractor));
   }

   /**
    * 요청사항
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String MAIN_SUBJECT = "mainSubject";
   static int MAIN_SUBJECT_UPPER_LIMIT = -1;
   java.lang.String mainSubject;
   /**
    * 요청사항
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getMainSubject() {
      return mainSubject;
   }
   /**
    * 요청사항
    *
    * @see ext.ket.issue.entity.KETIssueMaster
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
    * 상태
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String ISSUE_STATE = "issueState";
   static int ISSUE_STATE_UPPER_LIMIT = -1;
   java.lang.String issueState;
   /**
    * 상태
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getIssueState() {
      return issueState;
   }
   /**
    * 상태
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setIssueState(java.lang.String issueState) throws wt.util.WTPropertyVetoException {
      issueStateValidate(issueState);
      this.issueState = issueState;
   }
   void issueStateValidate(java.lang.String issueState) throws wt.util.WTPropertyVetoException {
      if (ISSUE_STATE_UPPER_LIMIT < 1) {
         try { ISSUE_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("issueState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ISSUE_STATE_UPPER_LIMIT = 200; }
      }
      if (issueState != null && !wt.fc.PersistenceHelper.checkStoredLength(issueState.toString(), ISSUE_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "issueState"), java.lang.String.valueOf(java.lang.Math.min(ISSUE_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "issueState", this.issueState, issueState));
   }

   /**
    * 사업부구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String DIVISION_CODE = "divisionCode";
   static int DIVISION_CODE_UPPER_LIMIT = -1;
   java.lang.String divisionCode;
   /**
    * 사업부구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getDivisionCode() {
      return divisionCode;
   }
   /**
    * 사업부구분
    *
    * @see ext.ket.issue.entity.KETIssueMaster
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
    * 회의대상
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String MEETING_TARGET = "meetingTarget";
   static int MEETING_TARGET_UPPER_LIMIT = -1;
   java.lang.String meetingTarget;
   /**
    * 회의대상
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getMeetingTarget() {
      return meetingTarget;
   }
   /**
    * 회의대상
    *
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setMeetingTarget(java.lang.String meetingTarget) throws wt.util.WTPropertyVetoException {
      meetingTargetValidate(meetingTarget);
      this.meetingTarget = meetingTarget;
   }
   void meetingTargetValidate(java.lang.String meetingTarget) throws wt.util.WTPropertyVetoException {
      if (MEETING_TARGET_UPPER_LIMIT < 1) {
         try { MEETING_TARGET_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("meetingTarget").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEETING_TARGET_UPPER_LIMIT = 200; }
      }
      if (meetingTarget != null && !wt.fc.PersistenceHelper.checkStoredLength(meetingTarget.toString(), MEETING_TARGET_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingTarget"), java.lang.String.valueOf(java.lang.Math.min(MEETING_TARGET_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "meetingTarget", this.meetingTarget, meetingTarget));
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String VERSION = "version";
   java.lang.Integer version = 0;
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.Integer getVersion() {
      return version;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setVersion(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String PART_COUNT = "partCount";
   java.lang.Integer partCount = 0;
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.Integer getPartCount() {
      return partCount;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setPartCount(java.lang.Integer partCount) throws wt.util.WTPropertyVetoException {
      partCountValidate(partCount);
      this.partCount = partCount;
   }
   void partCountValidate(java.lang.Integer partCount) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String PART_NOS = "partNos";
   static int PART_NOS_UPPER_LIMIT = -1;
   java.lang.String partNos;
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public java.lang.String getPartNos() {
      return partNos;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setPartNos(java.lang.String partNos) throws wt.util.WTPropertyVetoException {
      partNosValidate(partNos);
      this.partNos = partNos;
   }
   void partNosValidate(java.lang.String partNos) throws wt.util.WTPropertyVetoException {
      if (PART_NOS_UPPER_LIMIT < 1) {
         try { PART_NOS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNos").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NOS_UPPER_LIMIT = 8000; }
      }
      if (partNos != null && !wt.fc.PersistenceHelper.checkStoredLength(partNos.toString(), PART_NOS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNos"), java.lang.String.valueOf(java.lang.Math.min(PART_NOS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNos", this.partNos, partNos));
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String MANAGER = "manager";
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String MANAGER_REFERENCE = "managerReference";
   wt.fc.ObjectReference managerReference;
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public wt.org.WTUser getManager() {
      return (managerReference != null) ? (wt.org.WTUser) managerReference.getObject() : null;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public wt.fc.ObjectReference getManagerReference() {
      return managerReference;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setManager(wt.org.WTUser the_manager) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setManagerReference(the_manager == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_manager));
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setManagerReference(wt.fc.ObjectReference the_managerReference) throws wt.util.WTPropertyVetoException {
      managerReferenceValidate(the_managerReference);
      managerReference = (wt.fc.ObjectReference) the_managerReference;
   }
   void managerReferenceValidate(wt.fc.ObjectReference the_managerReference) throws wt.util.WTPropertyVetoException {
      if (the_managerReference == null || the_managerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerReference") },
               new java.beans.PropertyChangeEvent(this, "managerReference", this.managerReference, managerReference));
      if (the_managerReference != null && the_managerReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_managerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "managerReference", this.managerReference, managerReference));
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String OEM_TYPE = "oemType";
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public static final java.lang.String OEM_TYPE_REFERENCE = "oemTypeReference";
   wt.fc.ObjectReference oemTypeReference;
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public e3ps.project.outputtype.OEMProjectType getOemType() {
      return (oemTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) oemTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public wt.fc.ObjectReference getOemTypeReference() {
      return oemTypeReference;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setOemType(e3ps.project.outputtype.OEMProjectType the_oemType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOemTypeReference(the_oemType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_oemType));
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMaster
    */
   public void setOemTypeReference(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      oemTypeReferenceValidate(the_oemTypeReference);
      oemTypeReference = (wt.fc.ObjectReference) the_oemTypeReference;
   }
   void oemTypeReferenceValidate(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_oemTypeReference != null && the_oemTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_oemTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oemTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "oemTypeReference", this.oemTypeReference, oemTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 6709852256354309553L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( completeDate );
      output.writeObject( deptCode );
      output.writeObject( detailCode );
      output.writeObject( divisionCode );
      output.writeObject( issueState );
      output.writeObject( lastCustomer );
      output.writeObject( mainSubject );
      output.writeObject( managerReference );
      output.writeObject( meetingTarget );
      output.writeObject( oemTypeReference );
      output.writeObject( partCount );
      output.writeObject( partNos );
      output.writeObject( rank );
      output.writeObject( reqCode );
      output.writeObject( reqName );
      output.writeObject( reqNo );
      output.writeObject( requestDate );
      output.writeObject( subContractor );
      output.writeObject( version );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETIssueMaster(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.issue.entity.KETIssueMaster) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETIssueMaster(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setTimestamp( "completeDate", completeDate );
      output.setString( "deptCode", deptCode );
      output.setString( "detailCode", detailCode );
      output.setString( "divisionCode", divisionCode );
      output.setString( "issueState", issueState );
      output.setString( "lastCustomer", lastCustomer );
      output.setString( "mainSubject", mainSubject );
      output.writeObject( "managerReference", managerReference, wt.fc.ObjectReference.class, true );
      output.setString( "meetingTarget", meetingTarget );
      output.writeObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      output.setIntObject( "partCount", partCount );
      output.setString( "partNos", partNos );
      output.setString( "rank", rank );
      output.setString( "reqCode", reqCode );
      output.setString( "reqName", reqName );
      output.setString( "reqNo", reqNo );
      output.setTimestamp( "requestDate", requestDate );
      output.setString( "subContractor", subContractor );
      output.setIntObject( "version", version );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      completeDate = input.getTimestamp( "completeDate" );
      deptCode = input.getString( "deptCode" );
      detailCode = input.getString( "detailCode" );
      divisionCode = input.getString( "divisionCode" );
      issueState = input.getString( "issueState" );
      lastCustomer = input.getString( "lastCustomer" );
      mainSubject = input.getString( "mainSubject" );
      managerReference = (wt.fc.ObjectReference) input.readObject( "managerReference", managerReference, wt.fc.ObjectReference.class, true );
      meetingTarget = input.getString( "meetingTarget" );
      oemTypeReference = (wt.fc.ObjectReference) input.readObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      partCount = input.getIntObject( "partCount" );
      partNos = input.getString( "partNos" );
      rank = input.getString( "rank" );
      reqCode = input.getString( "reqCode" );
      reqName = input.getString( "reqName" );
      reqNo = input.getString( "reqNo" );
      requestDate = input.getTimestamp( "requestDate" );
      subContractor = input.getString( "subContractor" );
      version = input.getIntObject( "version" );
   }

   boolean readVersion6709852256354309553L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      completeDate = (java.sql.Timestamp) input.readObject();
      deptCode = (java.lang.String) input.readObject();
      detailCode = (java.lang.String) input.readObject();
      divisionCode = (java.lang.String) input.readObject();
      issueState = (java.lang.String) input.readObject();
      lastCustomer = (java.lang.String) input.readObject();
      mainSubject = (java.lang.String) input.readObject();
      managerReference = (wt.fc.ObjectReference) input.readObject();
      meetingTarget = (java.lang.String) input.readObject();
      oemTypeReference = (wt.fc.ObjectReference) input.readObject();
      partCount = (java.lang.Integer) input.readObject();
      partNos = (java.lang.String) input.readObject();
      rank = (java.lang.String) input.readObject();
      reqCode = (java.lang.String) input.readObject();
      reqName = (java.lang.String) input.readObject();
      reqNo = (java.lang.String) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();
      subContractor = (java.lang.String) input.readObject();
      version = (java.lang.Integer) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETIssueMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6709852256354309553L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETIssueMaster( _KETIssueMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
