package ext.ket.invest.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETInvestMaster extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.invest.entity.entityResource";
   static final java.lang.String CLASSNAME = KETInvestMaster.class.getName();

   /**
    * 품의번호
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String REQ_NO = "reqNo";
   static int REQ_NO_UPPER_LIMIT = -1;
   java.lang.String reqNo;
   /**
    * 품의번호
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getReqNo() {
      return reqNo;
   }
   /**
    * 품의번호
    *
    * @see ext.ket.invest.entity.KETInvestMaster
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
    * 개발제품명
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String REQ_NAME = "reqName";
   static int REQ_NAME_UPPER_LIMIT = -1;
   java.lang.String reqName;
   /**
    * 개발제품명
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getReqName() {
      return reqName;
   }
   /**
    * 개발제품명
    *
    * @see ext.ket.invest.entity.KETInvestMaster
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
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String DEPT_CODE = "deptCode";
   static int DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String deptCode;
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getDeptCode() {
      return deptCode;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
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
    * 고객투자비 합계
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String INVEST_EXPENSE = "investExpense";
   static int INVEST_EXPENSE_UPPER_LIMIT = -1;
   java.lang.String investExpense = "0";
   /**
    * 고객투자비 합계
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getInvestExpense() {
      return investExpense;
   }
   /**
    * 고객투자비 합계
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setInvestExpense(java.lang.String investExpense) throws wt.util.WTPropertyVetoException {
      investExpenseValidate(investExpense);
      this.investExpense = investExpense;
   }
   void investExpenseValidate(java.lang.String investExpense) throws wt.util.WTPropertyVetoException {
      if (INVEST_EXPENSE_UPPER_LIMIT < 1) {
         try { INVEST_EXPENSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investExpense").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_EXPENSE_UPPER_LIMIT = 200; }
      }
      if (investExpense != null && !wt.fc.PersistenceHelper.checkStoredLength(investExpense.toString(), INVEST_EXPENSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investExpense"), java.lang.String.valueOf(java.lang.Math.min(INVEST_EXPENSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investExpense", this.investExpense, investExpense));
   }

   /**
    * 고객투자비(금형/설비)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String INVEST_EXPENSE_1 = "investExpense_1";
   static int INVEST_EXPENSE_1_UPPER_LIMIT = -1;
   java.lang.String investExpense_1 = "0";
   /**
    * 고객투자비(금형/설비)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getInvestExpense_1() {
      return investExpense_1;
   }
   /**
    * 고객투자비(금형/설비)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setInvestExpense_1(java.lang.String investExpense_1) throws wt.util.WTPropertyVetoException {
      investExpense_1Validate(investExpense_1);
      this.investExpense_1 = investExpense_1;
   }
   void investExpense_1Validate(java.lang.String investExpense_1) throws wt.util.WTPropertyVetoException {
      if (INVEST_EXPENSE_1_UPPER_LIMIT < 1) {
         try { INVEST_EXPENSE_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investExpense_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_EXPENSE_1_UPPER_LIMIT = 200; }
      }
      if (investExpense_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(investExpense_1.toString(), INVEST_EXPENSE_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investExpense_1"), java.lang.String.valueOf(java.lang.Math.min(INVEST_EXPENSE_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investExpense_1", this.investExpense_1, investExpense_1));
   }

   /**
    * 고객투자비(QDM/기타)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String INVEST_EXPENSE_2 = "investExpense_2";
   static int INVEST_EXPENSE_2_UPPER_LIMIT = -1;
   java.lang.String investExpense_2 = "0";
   /**
    * 고객투자비(QDM/기타)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getInvestExpense_2() {
      return investExpense_2;
   }
   /**
    * 고객투자비(QDM/기타)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setInvestExpense_2(java.lang.String investExpense_2) throws wt.util.WTPropertyVetoException {
      investExpense_2Validate(investExpense_2);
      this.investExpense_2 = investExpense_2;
   }
   void investExpense_2Validate(java.lang.String investExpense_2) throws wt.util.WTPropertyVetoException {
      if (INVEST_EXPENSE_2_UPPER_LIMIT < 1) {
         try { INVEST_EXPENSE_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investExpense_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_EXPENSE_2_UPPER_LIMIT = 200; }
      }
      if (investExpense_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(investExpense_2.toString(), INVEST_EXPENSE_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investExpense_2"), java.lang.String.valueOf(java.lang.Math.min(INVEST_EXPENSE_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investExpense_2", this.investExpense_2, investExpense_2));
   }

   /**
    * 회수비용 합계
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String COLLECT_EXPENSE = "collectExpense";
   static int COLLECT_EXPENSE_UPPER_LIMIT = -1;
   java.lang.String collectExpense = "0";
   /**
    * 회수비용 합계
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getCollectExpense() {
      return collectExpense;
   }
   /**
    * 회수비용 합계
    *
    * @see ext.ket.invest.entity.KETInvestMaster
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
    * 회수비용 (금형/설비)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String COLLECT_EXPENSE_1 = "collectExpense_1";
   static int COLLECT_EXPENSE_1_UPPER_LIMIT = -1;
   java.lang.String collectExpense_1 = "0";
   /**
    * 회수비용 (금형/설비)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getCollectExpense_1() {
      return collectExpense_1;
   }
   /**
    * 회수비용 (금형/설비)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setCollectExpense_1(java.lang.String collectExpense_1) throws wt.util.WTPropertyVetoException {
      collectExpense_1Validate(collectExpense_1);
      this.collectExpense_1 = collectExpense_1;
   }
   void collectExpense_1Validate(java.lang.String collectExpense_1) throws wt.util.WTPropertyVetoException {
      if (COLLECT_EXPENSE_1_UPPER_LIMIT < 1) {
         try { COLLECT_EXPENSE_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("collectExpense_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLLECT_EXPENSE_1_UPPER_LIMIT = 200; }
      }
      if (collectExpense_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(collectExpense_1.toString(), COLLECT_EXPENSE_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collectExpense_1"), java.lang.String.valueOf(java.lang.Math.min(COLLECT_EXPENSE_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "collectExpense_1", this.collectExpense_1, collectExpense_1));
   }

   /**
    * 회수비용 (QDM/기타)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String COLLECT_EXPENSE_2 = "collectExpense_2";
   static int COLLECT_EXPENSE_2_UPPER_LIMIT = -1;
   java.lang.String collectExpense_2 = "0";
   /**
    * 회수비용 (QDM/기타)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getCollectExpense_2() {
      return collectExpense_2;
   }
   /**
    * 회수비용 (QDM/기타)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setCollectExpense_2(java.lang.String collectExpense_2) throws wt.util.WTPropertyVetoException {
      collectExpense_2Validate(collectExpense_2);
      this.collectExpense_2 = collectExpense_2;
   }
   void collectExpense_2Validate(java.lang.String collectExpense_2) throws wt.util.WTPropertyVetoException {
      if (COLLECT_EXPENSE_2_UPPER_LIMIT < 1) {
         try { COLLECT_EXPENSE_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("collectExpense_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLLECT_EXPENSE_2_UPPER_LIMIT = 200; }
      }
      if (collectExpense_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(collectExpense_2.toString(), COLLECT_EXPENSE_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collectExpense_2"), java.lang.String.valueOf(java.lang.Math.min(COLLECT_EXPENSE_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "collectExpense_2", this.collectExpense_2, collectExpense_2));
   }

   /**
    * 회수 예정일자
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * 회수 예정일자
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * 회수 예정일자
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 회수 완료일
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String COMPLETE_DATE = "completeDate";
   java.sql.Timestamp completeDate;
   /**
    * 회수 완료일
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.sql.Timestamp getCompleteDate() {
      return completeDate;
   }
   /**
    * 회수 완료일
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setCompleteDate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
      completeDateValidate(completeDate);
      this.completeDate = completeDate;
   }
   void completeDateValidate(java.sql.Timestamp completeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 상태
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String INVEST_STATE = "investState";
   static int INVEST_STATE_UPPER_LIMIT = -1;
   java.lang.String investState;
   /**
    * 상태
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getInvestState() {
      return investState;
   }
   /**
    * 상태
    *
    * @see ext.ket.invest.entity.KETInvestMaster
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
    * 개발발의자(사용안함)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String FIRST_USER_NAME = "firstUserName";
   static int FIRST_USER_NAME_UPPER_LIMIT = -1;
   java.lang.String firstUserName;
   /**
    * 개발발의자(사용안함)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getFirstUserName() {
      return firstUserName;
   }
   /**
    * 개발발의자(사용안함)
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setFirstUserName(java.lang.String firstUserName) throws wt.util.WTPropertyVetoException {
      firstUserNameValidate(firstUserName);
      this.firstUserName = firstUserName;
   }
   void firstUserNameValidate(java.lang.String firstUserName) throws wt.util.WTPropertyVetoException {
      if (FIRST_USER_NAME_UPPER_LIMIT < 1) {
         try { FIRST_USER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("firstUserName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FIRST_USER_NAME_UPPER_LIMIT = 200; }
      }
      if (firstUserName != null && !wt.fc.PersistenceHelper.checkStoredLength(firstUserName.toString(), FIRST_USER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "firstUserName"), java.lang.String.valueOf(java.lang.Math.min(FIRST_USER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "firstUserName", this.firstUserName, firstUserName));
   }

   /**
    * 영업부서
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String FIRST_DEPT_CODE = "firstDeptCode";
   static int FIRST_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String firstDeptCode;
   /**
    * 영업부서
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getFirstDeptCode() {
      return firstDeptCode;
   }
   /**
    * 영업부서
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setFirstDeptCode(java.lang.String firstDeptCode) throws wt.util.WTPropertyVetoException {
      firstDeptCodeValidate(firstDeptCode);
      this.firstDeptCode = firstDeptCode;
   }
   void firstDeptCodeValidate(java.lang.String firstDeptCode) throws wt.util.WTPropertyVetoException {
      if (FIRST_DEPT_CODE_UPPER_LIMIT < 1) {
         try { FIRST_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("firstDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FIRST_DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (firstDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(firstDeptCode.toString(), FIRST_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "firstDeptCode"), java.lang.String.valueOf(java.lang.Math.min(FIRST_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "firstDeptCode", this.firstDeptCode, firstDeptCode));
   }

   /**
    * 비고사항
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String BIGO = "bigo";
   static int BIGO_UPPER_LIMIT = -1;
   java.lang.String bigo;
   /**
    * 비고사항
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getBigo() {
      return bigo;
   }
   /**
    * 비고사항
    *
    * @see ext.ket.invest.entity.KETInvestMaster
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
    * 연계프로젝트
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String PJT_NOS = "pjtNos";
   static int PJT_NOS_UPPER_LIMIT = -1;
   java.lang.String pjtNos;
   /**
    * 연계프로젝트
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getPjtNos() {
      return pjtNos;
   }
   /**
    * 연계프로젝트
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setPjtNos(java.lang.String pjtNos) throws wt.util.WTPropertyVetoException {
      pjtNosValidate(pjtNos);
      this.pjtNos = pjtNos;
   }
   void pjtNosValidate(java.lang.String pjtNos) throws wt.util.WTPropertyVetoException {
      if (PJT_NOS_UPPER_LIMIT < 1) {
         try { PJT_NOS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtNos").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_NOS_UPPER_LIMIT = 4000; }
      }
      if (pjtNos != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtNos.toString(), PJT_NOS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtNos"), java.lang.String.valueOf(java.lang.Math.min(PJT_NOS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtNos", this.pjtNos, pjtNos));
   }

   /**
    * 회수예정일자 변경 횟수
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String DATE_CHANGE_COUNT = "dateChangeCount";
   java.lang.Integer dateChangeCount = 0;
   /**
    * 회수예정일자 변경 횟수
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.Integer getDateChangeCount() {
      return dateChangeCount;
   }
   /**
    * 회수예정일자 변경 횟수
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setDateChangeCount(java.lang.Integer dateChangeCount) throws wt.util.WTPropertyVetoException {
      dateChangeCountValidate(dateChangeCount);
      this.dateChangeCount = dateChangeCount;
   }
   void dateChangeCountValidate(java.lang.Integer dateChangeCount) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 유형
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String INVEST_TYPE_CODE = "investTypeCode";
   static int INVEST_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String investTypeCode;
   /**
    * 유형
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public java.lang.String getInvestTypeCode() {
      return investTypeCode;
   }
   /**
    * 유형
    *
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setInvestTypeCode(java.lang.String investTypeCode) throws wt.util.WTPropertyVetoException {
      investTypeCodeValidate(investTypeCode);
      this.investTypeCode = investTypeCode;
   }
   void investTypeCodeValidate(java.lang.String investTypeCode) throws wt.util.WTPropertyVetoException {
      if (INVEST_TYPE_CODE_UPPER_LIMIT < 1) {
         try { INVEST_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (investTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(investTypeCode.toString(), INVEST_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investTypeCode"), java.lang.String.valueOf(java.lang.Math.min(INVEST_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investTypeCode", this.investTypeCode, investTypeCode));
   }

   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String MANAGER = "manager";
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String MANAGER_REFERENCE = "managerReference";
   wt.fc.ObjectReference managerReference;
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public wt.org.WTUser getManager() {
      return (managerReference != null) ? (wt.org.WTUser) managerReference.getObject() : null;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public wt.fc.ObjectReference getManagerReference() {
      return managerReference;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setManager(wt.org.WTUser the_manager) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setManagerReference(the_manager == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_manager));
   }
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
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
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String DEV_REQUEST = "devRequest";
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public static final java.lang.String DEV_REQUEST_REFERENCE = "devRequestReference";
   wt.fc.ObjectReference devRequestReference;
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public e3ps.dms.entity.KETDevelopmentRequest getDevRequest() {
      return (devRequestReference != null) ? (e3ps.dms.entity.KETDevelopmentRequest) devRequestReference.getObject() : null;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public wt.fc.ObjectReference getDevRequestReference() {
      return devRequestReference;
   }
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
    */
   public void setDevRequest(e3ps.dms.entity.KETDevelopmentRequest the_devRequest) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevRequestReference(the_devRequest == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.dms.entity.KETDevelopmentRequest) the_devRequest));
   }
   /**
    * @see ext.ket.invest.entity.KETInvestMaster
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

   public static final long EXTERNALIZATION_VERSION_UID = -3045145974022452018L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( bigo );
      output.writeObject( collectExpense );
      output.writeObject( collectExpense_1 );
      output.writeObject( collectExpense_2 );
      output.writeObject( completeDate );
      output.writeObject( dateChangeCount );
      output.writeObject( deptCode );
      output.writeObject( devRequestReference );
      output.writeObject( firstDeptCode );
      output.writeObject( firstUserName );
      output.writeObject( investExpense );
      output.writeObject( investExpense_1 );
      output.writeObject( investExpense_2 );
      output.writeObject( investState );
      output.writeObject( investTypeCode );
      output.writeObject( managerReference );
      output.writeObject( pjtNos );
      output.writeObject( reqName );
      output.writeObject( reqNo );
      output.writeObject( requestDate );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETInvestMaster(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.invest.entity.KETInvestMaster) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETInvestMaster(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "bigo", bigo );
      output.setString( "collectExpense", collectExpense );
      output.setString( "collectExpense_1", collectExpense_1 );
      output.setString( "collectExpense_2", collectExpense_2 );
      output.setTimestamp( "completeDate", completeDate );
      output.setIntObject( "dateChangeCount", dateChangeCount );
      output.setString( "deptCode", deptCode );
      output.writeObject( "devRequestReference", devRequestReference, wt.fc.ObjectReference.class, true );
      output.setString( "firstDeptCode", firstDeptCode );
      output.setString( "firstUserName", firstUserName );
      output.setString( "investExpense", investExpense );
      output.setString( "investExpense_1", investExpense_1 );
      output.setString( "investExpense_2", investExpense_2 );
      output.setString( "investState", investState );
      output.setString( "investTypeCode", investTypeCode );
      output.writeObject( "managerReference", managerReference, wt.fc.ObjectReference.class, true );
      output.setString( "pjtNos", pjtNos );
      output.setString( "reqName", reqName );
      output.setString( "reqNo", reqNo );
      output.setTimestamp( "requestDate", requestDate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      bigo = input.getString( "bigo" );
      collectExpense = input.getString( "collectExpense" );
      collectExpense_1 = input.getString( "collectExpense_1" );
      collectExpense_2 = input.getString( "collectExpense_2" );
      completeDate = input.getTimestamp( "completeDate" );
      dateChangeCount = input.getIntObject( "dateChangeCount" );
      deptCode = input.getString( "deptCode" );
      devRequestReference = (wt.fc.ObjectReference) input.readObject( "devRequestReference", devRequestReference, wt.fc.ObjectReference.class, true );
      firstDeptCode = input.getString( "firstDeptCode" );
      firstUserName = input.getString( "firstUserName" );
      investExpense = input.getString( "investExpense" );
      investExpense_1 = input.getString( "investExpense_1" );
      investExpense_2 = input.getString( "investExpense_2" );
      investState = input.getString( "investState" );
      investTypeCode = input.getString( "investTypeCode" );
      managerReference = (wt.fc.ObjectReference) input.readObject( "managerReference", managerReference, wt.fc.ObjectReference.class, true );
      pjtNos = input.getString( "pjtNos" );
      reqName = input.getString( "reqName" );
      reqNo = input.getString( "reqNo" );
      requestDate = input.getTimestamp( "requestDate" );
   }

   boolean readVersion_3045145974022452018L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      bigo = (java.lang.String) input.readObject();
      collectExpense = (java.lang.String) input.readObject();
      collectExpense_1 = (java.lang.String) input.readObject();
      collectExpense_2 = (java.lang.String) input.readObject();
      completeDate = (java.sql.Timestamp) input.readObject();
      dateChangeCount = (java.lang.Integer) input.readObject();
      deptCode = (java.lang.String) input.readObject();
      devRequestReference = (wt.fc.ObjectReference) input.readObject();
      firstDeptCode = (java.lang.String) input.readObject();
      firstUserName = (java.lang.String) input.readObject();
      investExpense = (java.lang.String) input.readObject();
      investExpense_1 = (java.lang.String) input.readObject();
      investExpense_2 = (java.lang.String) input.readObject();
      investState = (java.lang.String) input.readObject();
      investTypeCode = (java.lang.String) input.readObject();
      managerReference = (wt.fc.ObjectReference) input.readObject();
      pjtNos = (java.lang.String) input.readObject();
      reqName = (java.lang.String) input.readObject();
      reqNo = (java.lang.String) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETInvestMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3045145974022452018L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETInvestMaster( _KETInvestMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
