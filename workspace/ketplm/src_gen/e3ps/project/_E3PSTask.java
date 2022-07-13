package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _E3PSTask extends e3ps.project.TemplateTask implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = E3PSTask.class.getName();

   /**
    * TASK 진행율
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String TASK_COMPLETION = "taskCompletion";
   int taskCompletion;
   /**
    * TASK 진행율
    *
    * @see e3ps.project.E3PSTask
    */
   public int getTaskCompletion() {
      return taskCompletion;
   }
   /**
    * TASK 진행율
    *
    * @see e3ps.project.E3PSTask
    */
   public void setTaskCompletion(int taskCompletion) throws wt.util.WTPropertyVetoException {
      taskCompletionValidate(taskCompletion);
      this.taskCompletion = taskCompletion;
   }
   void taskCompletionValidate(int taskCompletion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * TASK 상태≪≫≪≫0: 진행중≪≫1: 중지(Hold)≪≫2: 취소(Revocation)≪≫3: 재시작(Restart)≪≫4: 지연(Delay)
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String TASK_STATE = "taskState";
   int taskState;
   /**
    * TASK 상태≪≫≪≫0: 진행중≪≫1: 중지(Hold)≪≫2: 취소(Revocation)≪≫3: 재시작(Restart)≪≫4: 지연(Delay)
    *
    * @see e3ps.project.E3PSTask
    */
   public int getTaskState() {
      return taskState;
   }
   /**
    * TASK 상태≪≫≪≫0: 진행중≪≫1: 중지(Hold)≪≫2: 취소(Revocation)≪≫3: 재시작(Restart)≪≫4: 지연(Delay)
    *
    * @see e3ps.project.E3PSTask
    */
   public void setTaskState(int taskState) throws wt.util.WTPropertyVetoException {
      taskStateValidate(taskState);
      this.taskState = taskState;
   }
   void taskStateValidate(int taskState) throws wt.util.WTPropertyVetoException {
   }

   /**
    * DR Check 점수
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String DR_RESULT = "drResult";
   static int DR_RESULT_UPPER_LIMIT = -1;
   java.lang.String drResult;
   /**
    * DR Check 점수
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getDrResult() {
      return drResult;
   }
   /**
    * DR Check 점수
    *
    * @see e3ps.project.E3PSTask
    */
   public void setDrResult(java.lang.String drResult) throws wt.util.WTPropertyVetoException {
      drResultValidate(drResult);
      this.drResult = drResult;
   }
   void drResultValidate(java.lang.String drResult) throws wt.util.WTPropertyVetoException {
      if (DR_RESULT_UPPER_LIMIT < 1) {
         try { DR_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("drResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DR_RESULT_UPPER_LIMIT = 200; }
      }
      if (drResult != null && !wt.fc.PersistenceHelper.checkStoredLength(drResult.toString(), DR_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "drResult"), java.lang.String.valueOf(java.lang.Math.min(DR_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "drResult", this.drResult, drResult));
   }

   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String DEBUG_SUB = "debug_sub";
   boolean debug_sub;
   /**
    * @see e3ps.project.E3PSTask
    */
   public boolean isDebug_sub() {
      return debug_sub;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setDebug_sub(boolean debug_sub) throws wt.util.WTPropertyVetoException {
      debug_subValidate(debug_sub);
      this.debug_sub = debug_sub;
   }
   void debug_subValidate(boolean debug_sub) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String DEBUG_N = "debug_n";
   boolean debug_n;
   /**
    * @see e3ps.project.E3PSTask
    */
   public boolean isDebug_n() {
      return debug_n;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setDebug_n(boolean debug_n) throws wt.util.WTPropertyVetoException {
      debug_nValidate(debug_n);
      this.debug_n = debug_n;
   }
   void debug_nValidate(boolean debug_n) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String NCHA = "ncha";
   int ncha;
   /**
    * @see e3ps.project.E3PSTask
    */
   public int getNcha() {
      return ncha;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setNcha(int ncha) throws wt.util.WTPropertyVetoException {
      nchaValidate(ncha);
      this.ncha = ncha;
   }
   void nchaValidate(int ncha) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 사유
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String REASON = "reason";
   static int REASON_UPPER_LIMIT = -1;
   java.lang.String reason;
   /**
    * 사유
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getReason() {
      return reason;
   }
   /**
    * 사유
    *
    * @see e3ps.project.E3PSTask
    */
   public void setReason(java.lang.String reason) throws wt.util.WTPropertyVetoException {
      reasonValidate(reason);
      this.reason = reason;
   }
   void reasonValidate(java.lang.String reason) throws wt.util.WTPropertyVetoException {
      if (REASON_UPPER_LIMIT < 1) {
         try { REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REASON_UPPER_LIMIT = 200; }
      }
      if (reason != null && !wt.fc.PersistenceHelper.checkStoredLength(reason.toString(), REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reason"), java.lang.String.valueOf(java.lang.Math.min(REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reason", this.reason, reason));
   }

   /**
    * 특이사항
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String SPECIAL = "special";
   static int SPECIAL_UPPER_LIMIT = -1;
   java.lang.String special;
   /**
    * 특이사항
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getSpecial() {
      return special;
   }
   /**
    * 특이사항
    *
    * @see e3ps.project.E3PSTask
    */
   public void setSpecial(java.lang.String special) throws wt.util.WTPropertyVetoException {
      specialValidate(special);
      this.special = special;
   }
   void specialValidate(java.lang.String special) throws wt.util.WTPropertyVetoException {
      if (SPECIAL_UPPER_LIMIT < 1) {
         try { SPECIAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("special").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPECIAL_UPPER_LIMIT = 2000; }
      }
      if (special != null && !wt.fc.PersistenceHelper.checkStoredLength(special.toString(), SPECIAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "special"), java.lang.String.valueOf(java.lang.Math.min(SPECIAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "special", this.special, special));
   }

   /**
    * 설비(TON)
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String TON = "ton";
   static int TON_UPPER_LIMIT = -1;
   java.lang.String ton;
   /**
    * 설비(TON)
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getTon() {
      return ton;
   }
   /**
    * 설비(TON)
    *
    * @see e3ps.project.E3PSTask
    */
   public void setTon(java.lang.String ton) throws wt.util.WTPropertyVetoException {
      tonValidate(ton);
      this.ton = ton;
   }
   void tonValidate(java.lang.String ton) throws wt.util.WTPropertyVetoException {
      if (TON_UPPER_LIMIT < 1) {
         try { TON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ton").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TON_UPPER_LIMIT = 200; }
      }
      if (ton != null && !wt.fc.PersistenceHelper.checkStoredLength(ton.toString(), TON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ton"), java.lang.String.valueOf(java.lang.Math.min(TON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ton", this.ton, ton));
   }

   /**
    * 측정결과
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String CHECK_RESULT = "checkResult";
   static int CHECK_RESULT_UPPER_LIMIT = -1;
   java.lang.String checkResult;
   /**
    * 측정결과
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCheckResult() {
      return checkResult;
   }
   /**
    * 측정결과
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCheckResult(java.lang.String checkResult) throws wt.util.WTPropertyVetoException {
      checkResultValidate(checkResult);
      this.checkResult = checkResult;
   }
   void checkResultValidate(java.lang.String checkResult) throws wt.util.WTPropertyVetoException {
      if (CHECK_RESULT_UPPER_LIMIT < 1) {
         try { CHECK_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_RESULT_UPPER_LIMIT = 200; }
      }
      if (checkResult != null && !wt.fc.PersistenceHelper.checkStoredLength(checkResult.toString(), CHECK_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkResult"), java.lang.String.valueOf(java.lang.Math.min(CHECK_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkResult", this.checkResult, checkResult));
   }

   /**
    * 수량
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String QUANTITY = "quantity";
   static int QUANTITY_UPPER_LIMIT = -1;
   java.lang.String quantity;
   /**
    * 수량
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getQuantity() {
      return quantity;
   }
   /**
    * 수량
    *
    * @see e3ps.project.E3PSTask
    */
   public void setQuantity(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
      quantityValidate(quantity);
      this.quantity = quantity;
   }
   void quantityValidate(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_UPPER_LIMIT < 1) {
         try { QUANTITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_UPPER_LIMIT = 200; }
      }
      if (quantity != null && !wt.fc.PersistenceHelper.checkStoredLength(quantity.toString(), QUANTITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantity"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantity", this.quantity, quantity));
   }

   /**
    * Try장소
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String TRY_PLACE = "tryPlace";
   static int TRY_PLACE_UPPER_LIMIT = -1;
   java.lang.String tryPlace;
   /**
    * Try장소
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getTryPlace() {
      return tryPlace;
   }
   /**
    * Try장소
    *
    * @see e3ps.project.E3PSTask
    */
   public void setTryPlace(java.lang.String tryPlace) throws wt.util.WTPropertyVetoException {
      tryPlaceValidate(tryPlace);
      this.tryPlace = tryPlace;
   }
   void tryPlaceValidate(java.lang.String tryPlace) throws wt.util.WTPropertyVetoException {
      if (TRY_PLACE_UPPER_LIMIT < 1) {
         try { TRY_PLACE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tryPlace").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRY_PLACE_UPPER_LIMIT = 200; }
      }
      if (tryPlace != null && !wt.fc.PersistenceHelper.checkStoredLength(tryPlace.toString(), TRY_PLACE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tryPlace"), java.lang.String.valueOf(java.lang.Math.min(TRY_PLACE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tryPlace", this.tryPlace, tryPlace));
   }

   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String TRY_DES = "tryDes";
   static int TRY_DES_UPPER_LIMIT = -1;
   java.lang.String tryDes;
   /**
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getTryDes() {
      return tryDes;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setTryDes(java.lang.String tryDes) throws wt.util.WTPropertyVetoException {
      tryDesValidate(tryDes);
      this.tryDes = tryDes;
   }
   void tryDesValidate(java.lang.String tryDes) throws wt.util.WTPropertyVetoException {
      if (TRY_DES_UPPER_LIMIT < 1) {
         try { TRY_DES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tryDes").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRY_DES_UPPER_LIMIT = 2000; }
      }
      if (tryDes != null && !wt.fc.PersistenceHelper.checkStoredLength(tryDes.toString(), TRY_DES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tryDes"), java.lang.String.valueOf(java.lang.Math.min(TRY_DES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tryDes", this.tryDes, tryDes));
   }

   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String TRY_PLAN = "tryPlan";
   boolean tryPlan;
   /**
    * @see e3ps.project.E3PSTask
    */
   public boolean isTryPlan() {
      return tryPlan;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setTryPlan(boolean tryPlan) throws wt.util.WTPropertyVetoException {
      tryPlanValidate(tryPlan);
      this.tryPlan = tryPlan;
   }
   void tryPlanValidate(boolean tryPlan) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 100% 입력 사유
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String COMP_REASON = "compReason";
   static int COMP_REASON_UPPER_LIMIT = -1;
   java.lang.String compReason;
   /**
    * 100% 입력 사유
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCompReason() {
      return compReason;
   }
   /**
    * 100% 입력 사유
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCompReason(java.lang.String compReason) throws wt.util.WTPropertyVetoException {
      compReasonValidate(compReason);
      this.compReason = compReason;
   }
   void compReasonValidate(java.lang.String compReason) throws wt.util.WTPropertyVetoException {
      if (COMP_REASON_UPPER_LIMIT < 1) {
         try { COMP_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("compReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMP_REASON_UPPER_LIMIT = 4000; }
      }
      if (compReason != null && !wt.fc.PersistenceHelper.checkStoredLength(compReason.toString(), COMP_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "compReason"), java.lang.String.valueOf(java.lang.Math.min(COMP_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "compReason", this.compReason, compReason));
   }

   /**
    * 불합격Point
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String NON_PASS_POINT = "nonPassPoint";
   static int NON_PASS_POINT_UPPER_LIMIT = -1;
   java.lang.String nonPassPoint;
   /**
    * 불합격Point
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getNonPassPoint() {
      return nonPassPoint;
   }
   /**
    * 불합격Point
    *
    * @see e3ps.project.E3PSTask
    */
   public void setNonPassPoint(java.lang.String nonPassPoint) throws wt.util.WTPropertyVetoException {
      nonPassPointValidate(nonPassPoint);
      this.nonPassPoint = nonPassPoint;
   }
   void nonPassPointValidate(java.lang.String nonPassPoint) throws wt.util.WTPropertyVetoException {
      if (NON_PASS_POINT_UPPER_LIMIT < 1) {
         try { NON_PASS_POINT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("nonPassPoint").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NON_PASS_POINT_UPPER_LIMIT = 200; }
      }
      if (nonPassPoint != null && !wt.fc.PersistenceHelper.checkStoredLength(nonPassPoint.toString(), NON_PASS_POINT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "nonPassPoint"), java.lang.String.valueOf(java.lang.Math.min(NON_PASS_POINT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "nonPassPoint", this.nonPassPoint, nonPassPoint));
   }

   /**
    * 측정Point
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String CHECK_DESC_POINT = "checkDescPoint";
   static int CHECK_DESC_POINT_UPPER_LIMIT = -1;
   java.lang.String checkDescPoint;
   /**
    * 측정Point
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCheckDescPoint() {
      return checkDescPoint;
   }
   /**
    * 측정Point
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCheckDescPoint(java.lang.String checkDescPoint) throws wt.util.WTPropertyVetoException {
      checkDescPointValidate(checkDescPoint);
      this.checkDescPoint = checkDescPoint;
   }
   void checkDescPointValidate(java.lang.String checkDescPoint) throws wt.util.WTPropertyVetoException {
      if (CHECK_DESC_POINT_UPPER_LIMIT < 1) {
         try { CHECK_DESC_POINT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkDescPoint").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_DESC_POINT_UPPER_LIMIT = 200; }
      }
      if (checkDescPoint != null && !wt.fc.PersistenceHelper.checkStoredLength(checkDescPoint.toString(), CHECK_DESC_POINT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkDescPoint"), java.lang.String.valueOf(java.lang.Math.min(CHECK_DESC_POINT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkDescPoint", this.checkDescPoint, checkDescPoint));
   }

   /**
    * 측정 비고
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String CHECK_ETC = "checkEtc";
   static int CHECK_ETC_UPPER_LIMIT = -1;
   java.lang.String checkEtc;
   /**
    * 측정 비고
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCheckEtc() {
      return checkEtc;
   }
   /**
    * 측정 비고
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCheckEtc(java.lang.String checkEtc) throws wt.util.WTPropertyVetoException {
      checkEtcValidate(checkEtc);
      this.checkEtc = checkEtc;
   }
   void checkEtcValidate(java.lang.String checkEtc) throws wt.util.WTPropertyVetoException {
      if (CHECK_ETC_UPPER_LIMIT < 1) {
         try { CHECK_ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkEtc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_ETC_UPPER_LIMIT = 3000; }
      }
      if (checkEtc != null && !wt.fc.PersistenceHelper.checkStoredLength(checkEtc.toString(), CHECK_ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkEtc"), java.lang.String.valueOf(java.lang.Math.min(CHECK_ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkEtc", this.checkEtc, checkEtc));
   }

   /**
    * leaf 여부
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String LEAF = "leaf";
   boolean leaf = false;
   /**
    * leaf 여부
    *
    * @see e3ps.project.E3PSTask
    */
   public boolean isLeaf() {
      return leaf;
   }
   /**
    * leaf 여부
    *
    * @see e3ps.project.E3PSTask
    */
   public void setLeaf(boolean leaf) throws wt.util.WTPropertyVetoException {
      leafValidate(leaf);
      this.leaf = leaf;
   }
   void leafValidate(boolean leaf) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 중요 측정Point
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String CHECK_DESC_POINT_I = "checkDescPoint_i";
   static int CHECK_DESC_POINT_I_UPPER_LIMIT = -1;
   java.lang.String checkDescPoint_i;
   /**
    * 중요 측정Point
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCheckDescPoint_i() {
      return checkDescPoint_i;
   }
   /**
    * 중요 측정Point
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCheckDescPoint_i(java.lang.String checkDescPoint_i) throws wt.util.WTPropertyVetoException {
      checkDescPoint_iValidate(checkDescPoint_i);
      this.checkDescPoint_i = checkDescPoint_i;
   }
   void checkDescPoint_iValidate(java.lang.String checkDescPoint_i) throws wt.util.WTPropertyVetoException {
      if (CHECK_DESC_POINT_I_UPPER_LIMIT < 1) {
         try { CHECK_DESC_POINT_I_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkDescPoint_i").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_DESC_POINT_I_UPPER_LIMIT = 200; }
      }
      if (checkDescPoint_i != null && !wt.fc.PersistenceHelper.checkStoredLength(checkDescPoint_i.toString(), CHECK_DESC_POINT_I_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkDescPoint_i"), java.lang.String.valueOf(java.lang.Math.min(CHECK_DESC_POINT_I_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkDescPoint_i", this.checkDescPoint_i, checkDescPoint_i));
   }

   /**
    * 중요 불합격Point
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String NON_PASS_POINT_I = "nonPassPoint_i";
   static int NON_PASS_POINT_I_UPPER_LIMIT = -1;
   java.lang.String nonPassPoint_i;
   /**
    * 중요 불합격Point
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getNonPassPoint_i() {
      return nonPassPoint_i;
   }
   /**
    * 중요 불합격Point
    *
    * @see e3ps.project.E3PSTask
    */
   public void setNonPassPoint_i(java.lang.String nonPassPoint_i) throws wt.util.WTPropertyVetoException {
      nonPassPoint_iValidate(nonPassPoint_i);
      this.nonPassPoint_i = nonPassPoint_i;
   }
   void nonPassPoint_iValidate(java.lang.String nonPassPoint_i) throws wt.util.WTPropertyVetoException {
      if (NON_PASS_POINT_I_UPPER_LIMIT < 1) {
         try { NON_PASS_POINT_I_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("nonPassPoint_i").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NON_PASS_POINT_I_UPPER_LIMIT = 200; }
      }
      if (nonPassPoint_i != null && !wt.fc.PersistenceHelper.checkStoredLength(nonPassPoint_i.toString(), NON_PASS_POINT_I_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "nonPassPoint_i"), java.lang.String.valueOf(java.lang.Math.min(NON_PASS_POINT_I_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "nonPassPoint_i", this.nonPassPoint_i, nonPassPoint_i));
   }

   /**
    * 중요 측정결과
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String CHECK_RESULT_I = "checkResult_i";
   static int CHECK_RESULT_I_UPPER_LIMIT = -1;
   java.lang.String checkResult_i;
   /**
    * 중요 측정결과
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCheckResult_i() {
      return checkResult_i;
   }
   /**
    * 중요 측정결과
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCheckResult_i(java.lang.String checkResult_i) throws wt.util.WTPropertyVetoException {
      checkResult_iValidate(checkResult_i);
      this.checkResult_i = checkResult_i;
   }
   void checkResult_iValidate(java.lang.String checkResult_i) throws wt.util.WTPropertyVetoException {
      if (CHECK_RESULT_I_UPPER_LIMIT < 1) {
         try { CHECK_RESULT_I_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkResult_i").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_RESULT_I_UPPER_LIMIT = 200; }
      }
      if (checkResult_i != null && !wt.fc.PersistenceHelper.checkStoredLength(checkResult_i.toString(), CHECK_RESULT_I_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkResult_i"), java.lang.String.valueOf(java.lang.Math.min(CHECK_RESULT_I_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkResult_i", this.checkResult_i, checkResult_i));
   }

   /**
    * 중요 비고
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String CHECK_ETC_I = "checkEtc_i";
   static int CHECK_ETC_I_UPPER_LIMIT = -1;
   java.lang.String checkEtc_i;
   /**
    * 중요 비고
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCheckEtc_i() {
      return checkEtc_i;
   }
   /**
    * 중요 비고
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCheckEtc_i(java.lang.String checkEtc_i) throws wt.util.WTPropertyVetoException {
      checkEtc_iValidate(checkEtc_i);
      this.checkEtc_i = checkEtc_i;
   }
   void checkEtc_iValidate(java.lang.String checkEtc_i) throws wt.util.WTPropertyVetoException {
      if (CHECK_ETC_I_UPPER_LIMIT < 1) {
         try { CHECK_ETC_I_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkEtc_i").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_ETC_I_UPPER_LIMIT = 200; }
      }
      if (checkEtc_i != null && !wt.fc.PersistenceHelper.checkStoredLength(checkEtc_i.toString(), CHECK_ETC_I_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkEtc_i"), java.lang.String.valueOf(java.lang.Math.min(CHECK_ETC_I_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkEtc_i", this.checkEtc_i, checkEtc_i));
   }

   /**
    * 전체 Point
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String ALL_POINT = "allPoint";
   static int ALL_POINT_UPPER_LIMIT = -1;
   java.lang.String allPoint;
   /**
    * 전체 Point
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getAllPoint() {
      return allPoint;
   }
   /**
    * 전체 Point
    *
    * @see e3ps.project.E3PSTask
    */
   public void setAllPoint(java.lang.String allPoint) throws wt.util.WTPropertyVetoException {
      allPointValidate(allPoint);
      this.allPoint = allPoint;
   }
   void allPointValidate(java.lang.String allPoint) throws wt.util.WTPropertyVetoException {
      if (ALL_POINT_UPPER_LIMIT < 1) {
         try { ALL_POINT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("allPoint").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ALL_POINT_UPPER_LIMIT = 200; }
      }
      if (allPoint != null && !wt.fc.PersistenceHelper.checkStoredLength(allPoint.toString(), ALL_POINT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "allPoint"), java.lang.String.valueOf(java.lang.Math.min(ALL_POINT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "allPoint", this.allPoint, allPoint));
   }

   /**
    * 중요 전체Point
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String ALL_POINT_I = "allPoint_i";
   static int ALL_POINT_I_UPPER_LIMIT = -1;
   java.lang.String allPoint_i;
   /**
    * 중요 전체Point
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getAllPoint_i() {
      return allPoint_i;
   }
   /**
    * 중요 전체Point
    *
    * @see e3ps.project.E3PSTask
    */
   public void setAllPoint_i(java.lang.String allPoint_i) throws wt.util.WTPropertyVetoException {
      allPoint_iValidate(allPoint_i);
      this.allPoint_i = allPoint_i;
   }
   void allPoint_iValidate(java.lang.String allPoint_i) throws wt.util.WTPropertyVetoException {
      if (ALL_POINT_I_UPPER_LIMIT < 1) {
         try { ALL_POINT_I_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("allPoint_i").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ALL_POINT_I_UPPER_LIMIT = 200; }
      }
      if (allPoint_i != null && !wt.fc.PersistenceHelper.checkStoredLength(allPoint_i.toString(), ALL_POINT_I_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "allPoint_i"), java.lang.String.valueOf(java.lang.Math.min(ALL_POINT_I_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "allPoint_i", this.allPoint_i, allPoint_i));
   }

   /**
    * costPart버전
    *
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String COST_VERSION = "costVersion";
   static int COST_VERSION_UPPER_LIMIT = -1;
   java.lang.String costVersion = "0";
   /**
    * costPart버전
    *
    * @see e3ps.project.E3PSTask
    */
   public java.lang.String getCostVersion() {
      return costVersion;
   }
   /**
    * costPart버전
    *
    * @see e3ps.project.E3PSTask
    */
   public void setCostVersion(java.lang.String costVersion) throws wt.util.WTPropertyVetoException {
      costVersionValidate(costVersion);
      this.costVersion = costVersion;
   }
   void costVersionValidate(java.lang.String costVersion) throws wt.util.WTPropertyVetoException {
      if (COST_VERSION_UPPER_LIMIT < 1) {
         try { COST_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_VERSION_UPPER_LIMIT = 200; }
      }
      if (costVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(costVersion.toString(), COST_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costVersion"), java.lang.String.valueOf(java.lang.Math.min(COST_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costVersion", this.costVersion, costVersion));
   }

   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String COST_REQUEST = "costRequest";
   boolean costRequest = false;
   /**
    * @see e3ps.project.E3PSTask
    */
   public boolean isCostRequest() {
      return costRequest;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setCostRequest(boolean costRequest) throws wt.util.WTPropertyVetoException {
      costRequestValidate(costRequest);
      this.costRequest = costRequest;
   }
   void costRequestValidate(boolean costRequest) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String POINT_DIVISION = "pointDivision";
   /**
    * @see e3ps.project.E3PSTask
    */
   public static final java.lang.String POINT_DIVISION_REFERENCE = "pointDivisionReference";
   wt.fc.ObjectReference pointDivisionReference;
   /**
    * @see e3ps.project.E3PSTask
    */
   public e3ps.common.code.NumberCode getPointDivision() {
      return (pointDivisionReference != null) ? (e3ps.common.code.NumberCode) pointDivisionReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public wt.fc.ObjectReference getPointDivisionReference() {
      return pointDivisionReference;
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setPointDivision(e3ps.common.code.NumberCode the_pointDivision) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPointDivisionReference(the_pointDivision == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_pointDivision));
   }
   /**
    * @see e3ps.project.E3PSTask
    */
   public void setPointDivisionReference(wt.fc.ObjectReference the_pointDivisionReference) throws wt.util.WTPropertyVetoException {
      pointDivisionReferenceValidate(the_pointDivisionReference);
      pointDivisionReference = (wt.fc.ObjectReference) the_pointDivisionReference;
   }
   void pointDivisionReferenceValidate(wt.fc.ObjectReference the_pointDivisionReference) throws wt.util.WTPropertyVetoException {
      if (the_pointDivisionReference != null && the_pointDivisionReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_pointDivisionReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pointDivisionReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "pointDivisionReference", this.pointDivisionReference, pointDivisionReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 2957279310210549083L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( allPoint );
      output.writeObject( allPoint_i );
      output.writeObject( checkDescPoint );
      output.writeObject( checkDescPoint_i );
      output.writeObject( checkEtc );
      output.writeObject( checkEtc_i );
      output.writeObject( checkResult );
      output.writeObject( checkResult_i );
      output.writeObject( compReason );
      output.writeBoolean( costRequest );
      output.writeObject( costVersion );
      output.writeBoolean( debug_n );
      output.writeBoolean( debug_sub );
      output.writeObject( drResult );
      output.writeBoolean( leaf );
      output.writeInt( ncha );
      output.writeObject( nonPassPoint );
      output.writeObject( nonPassPoint_i );
      output.writeObject( pointDivisionReference );
      output.writeObject( quantity );
      output.writeObject( reason );
      output.writeObject( special );
      output.writeInt( taskCompletion );
      output.writeInt( taskState );
      output.writeObject( ton );
      output.writeObject( tryDes );
      output.writeObject( tryPlace );
      output.writeBoolean( tryPlan );
   }

   protected void super_writeExternal_E3PSTask(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.E3PSTask) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_E3PSTask(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "allPoint", allPoint );
      output.setString( "allPoint_i", allPoint_i );
      output.setString( "checkDescPoint", checkDescPoint );
      output.setString( "checkDescPoint_i", checkDescPoint_i );
      output.setString( "checkEtc", checkEtc );
      output.setString( "checkEtc_i", checkEtc_i );
      output.setString( "checkResult", checkResult );
      output.setString( "checkResult_i", checkResult_i );
      output.setString( "compReason", compReason );
      output.setBoolean( "costRequest", costRequest );
      output.setString( "costVersion", costVersion );
      output.setBoolean( "debug_n", debug_n );
      output.setBoolean( "debug_sub", debug_sub );
      output.setString( "drResult", drResult );
      output.setBoolean( "leaf", leaf );
      output.setInt( "ncha", ncha );
      output.setString( "nonPassPoint", nonPassPoint );
      output.setString( "nonPassPoint_i", nonPassPoint_i );
      output.writeObject( "pointDivisionReference", pointDivisionReference, wt.fc.ObjectReference.class, true );
      output.setString( "quantity", quantity );
      output.setString( "reason", reason );
      output.setString( "special", special );
      output.setInt( "taskCompletion", taskCompletion );
      output.setInt( "taskState", taskState );
      output.setString( "ton", ton );
      output.setString( "tryDes", tryDes );
      output.setString( "tryPlace", tryPlace );
      output.setBoolean( "tryPlan", tryPlan );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      allPoint = input.getString( "allPoint" );
      allPoint_i = input.getString( "allPoint_i" );
      checkDescPoint = input.getString( "checkDescPoint" );
      checkDescPoint_i = input.getString( "checkDescPoint_i" );
      checkEtc = input.getString( "checkEtc" );
      checkEtc_i = input.getString( "checkEtc_i" );
      checkResult = input.getString( "checkResult" );
      checkResult_i = input.getString( "checkResult_i" );
      compReason = input.getString( "compReason" );
      costRequest = input.getBoolean( "costRequest" );
      costVersion = input.getString( "costVersion" );
      debug_n = input.getBoolean( "debug_n" );
      debug_sub = input.getBoolean( "debug_sub" );
      drResult = input.getString( "drResult" );
      leaf = input.getBoolean( "leaf" );
      ncha = input.getInt( "ncha" );
      nonPassPoint = input.getString( "nonPassPoint" );
      nonPassPoint_i = input.getString( "nonPassPoint_i" );
      pointDivisionReference = (wt.fc.ObjectReference) input.readObject( "pointDivisionReference", pointDivisionReference, wt.fc.ObjectReference.class, true );
      quantity = input.getString( "quantity" );
      reason = input.getString( "reason" );
      special = input.getString( "special" );
      taskCompletion = input.getInt( "taskCompletion" );
      taskState = input.getInt( "taskState" );
      ton = input.getString( "ton" );
      tryDes = input.getString( "tryDes" );
      tryPlace = input.getString( "tryPlace" );
      tryPlan = input.getBoolean( "tryPlan" );
   }

   boolean readVersion2957279310210549083L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      allPoint = (java.lang.String) input.readObject();
      allPoint_i = (java.lang.String) input.readObject();
      checkDescPoint = (java.lang.String) input.readObject();
      checkDescPoint_i = (java.lang.String) input.readObject();
      checkEtc = (java.lang.String) input.readObject();
      checkEtc_i = (java.lang.String) input.readObject();
      checkResult = (java.lang.String) input.readObject();
      checkResult_i = (java.lang.String) input.readObject();
      compReason = (java.lang.String) input.readObject();
      costRequest = input.readBoolean();
      costVersion = (java.lang.String) input.readObject();
      debug_n = input.readBoolean();
      debug_sub = input.readBoolean();
      drResult = (java.lang.String) input.readObject();
      leaf = input.readBoolean();
      ncha = input.readInt();
      nonPassPoint = (java.lang.String) input.readObject();
      nonPassPoint_i = (java.lang.String) input.readObject();
      pointDivisionReference = (wt.fc.ObjectReference) input.readObject();
      quantity = (java.lang.String) input.readObject();
      reason = (java.lang.String) input.readObject();
      special = (java.lang.String) input.readObject();
      taskCompletion = input.readInt();
      taskState = input.readInt();
      ton = (java.lang.String) input.readObject();
      tryDes = (java.lang.String) input.readObject();
      tryPlace = (java.lang.String) input.readObject();
      tryPlan = input.readBoolean();
      return true;
   }

   protected boolean readVersion( E3PSTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2957279310210549083L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_E3PSTask( _E3PSTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
