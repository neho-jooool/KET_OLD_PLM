package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TemplateTask implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = TemplateTask.class.getName();

   /**
    * TASK NO
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_NO = "taskNo";
   static int TASK_NO_UPPER_LIMIT = -1;
   java.lang.String taskNo;
   /**
    * TASK NO
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskNo() {
      return taskNo;
   }
   /**
    * TASK NO
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskNo(java.lang.String taskNo) throws wt.util.WTPropertyVetoException {
      taskNoValidate(taskNo);
      this.taskNo = taskNo;
   }
   void taskNoValidate(java.lang.String taskNo) throws wt.util.WTPropertyVetoException {
      if (TASK_NO_UPPER_LIMIT < 1) {
         try { TASK_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_NO_UPPER_LIMIT = 200; }
      }
      if (taskNo != null && !wt.fc.PersistenceHelper.checkStoredLength(taskNo.toString(), TASK_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskNo"), java.lang.String.valueOf(java.lang.Math.min(TASK_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskNo", this.taskNo, taskNo));
   }

   /**
    * TASK 명
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_NAME = "taskName";
   static int TASK_NAME_UPPER_LIMIT = -1;
   java.lang.String taskName;
   /**
    * TASK 명
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskName() {
      return taskName;
   }
   /**
    * TASK 명
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskName(java.lang.String taskName) throws wt.util.WTPropertyVetoException {
      taskNameValidate(taskName);
      this.taskName = taskName;
   }
   void taskNameValidate(java.lang.String taskName) throws wt.util.WTPropertyVetoException {
      if (TASK_NAME_UPPER_LIMIT < 1) {
         try { TASK_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_NAME_UPPER_LIMIT = 200; }
      }
      if (taskName != null && !wt.fc.PersistenceHelper.checkStoredLength(taskName.toString(), TASK_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskName"), java.lang.String.valueOf(java.lang.Math.min(TASK_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskName", this.taskName, taskName));
   }

   /**
    * TASK 명 (영문)
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_NAME_EN = "taskNameEn";
   static int TASK_NAME_EN_UPPER_LIMIT = -1;
   java.lang.String taskNameEn;
   /**
    * TASK 명 (영문)
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskNameEn() {
      return taskNameEn;
   }
   /**
    * TASK 명 (영문)
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskNameEn(java.lang.String taskNameEn) throws wt.util.WTPropertyVetoException {
      taskNameEnValidate(taskNameEn);
      this.taskNameEn = taskNameEn;
   }
   void taskNameEnValidate(java.lang.String taskNameEn) throws wt.util.WTPropertyVetoException {
      if (TASK_NAME_EN_UPPER_LIMIT < 1) {
         try { TASK_NAME_EN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskNameEn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_NAME_EN_UPPER_LIMIT = 4000; }
      }
      if (taskNameEn != null && !wt.fc.PersistenceHelper.checkStoredLength(taskNameEn.toString(), TASK_NAME_EN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskNameEn"), java.lang.String.valueOf(java.lang.Math.min(TASK_NAME_EN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskNameEn", this.taskNameEn, taskNameEn));
   }

   /**
    * TASK 순서
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_SEQ = "taskSeq";
   int taskSeq;
   /**
    * TASK 순서
    *
    * @see e3ps.project.TemplateTask
    */
   public int getTaskSeq() {
      return taskSeq;
   }
   /**
    * TASK 순서
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskSeq(int taskSeq) throws wt.util.WTPropertyVetoException {
      taskSeqValidate(taskSeq);
      this.taskSeq = taskSeq;
   }
   void taskSeqValidate(int taskSeq) throws wt.util.WTPropertyVetoException {
   }

   /**
    * TASK 설명
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_DESC = "taskDesc";
   static int TASK_DESC_UPPER_LIMIT = -1;
   java.lang.String taskDesc;
   /**
    * TASK 설명
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskDesc() {
      return taskDesc;
   }
   /**
    * TASK 설명
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskDesc(java.lang.String taskDesc) throws wt.util.WTPropertyVetoException {
      taskDescValidate(taskDesc);
      this.taskDesc = taskDesc;
   }
   void taskDescValidate(java.lang.String taskDesc) throws wt.util.WTPropertyVetoException {
      if (TASK_DESC_UPPER_LIMIT < 1) {
         try { TASK_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_DESC_UPPER_LIMIT = 5000; }
      }
      if (taskDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(taskDesc.toString(), TASK_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskDesc"), java.lang.String.valueOf(java.lang.Math.min(TASK_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskDesc", this.taskDesc, taskDesc));
   }

   /**
    * TASK 일정
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_SCHEDULE = "taskSchedule";
   wt.fc.ObjectReference taskSchedule;
   /**
    * TASK 일정
    *
    * @see e3ps.project.TemplateTask
    */
   public wt.fc.ObjectReference getTaskSchedule() {
      return taskSchedule;
   }
   /**
    * TASK 일정
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskSchedule(wt.fc.ObjectReference taskSchedule) throws wt.util.WTPropertyVetoException {
      taskScheduleValidate(taskSchedule);
      this.taskSchedule = taskSchedule;
   }
   void taskScheduleValidate(wt.fc.ObjectReference taskSchedule) throws wt.util.WTPropertyVetoException {
   }

   /**
    * TASK Type (=pjtType 동일)≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_TYPE = "taskType";
   static int TASK_TYPE_UPPER_LIMIT = -1;
   java.lang.String taskType;
   /**
    * TASK Type (=pjtType 동일)≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskType() {
      return taskType;
   }
   /**
    * TASK Type (=pjtType 동일)≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발
    *
    * @see e3ps.project.TemplateTask
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
    * TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_HISTORY = "taskHistory";
   int taskHistory;
   /**
    * TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TemplateTask
    */
   public int getTaskHistory() {
      return taskHistory;
   }
   /**
    * TASK 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskHistory(int taskHistory) throws wt.util.WTPropertyVetoException {
      taskHistoryValidate(taskHistory);
      this.taskHistory = taskHistory;
   }
   void taskHistoryValidate(int taskHistory) throws wt.util.WTPropertyVetoException {
   }

   /**
    * TASK Sequence값 (대?r8자리)
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_CODE = "taskCode";
   static int TASK_CODE_UPPER_LIMIT = -1;
   java.lang.String taskCode;
   /**
    * TASK Sequence값 (대?r8자리)
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskCode() {
      return taskCode;
   }
   /**
    * TASK Sequence값 (대?r8자리)
    *
    * @see e3ps.project.TemplateTask
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
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_KEY = "taskKey";
   static int TASK_KEY_UPPER_LIMIT = -1;
   java.lang.String taskKey;
   /**
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskKey() {
      return taskKey;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setTaskKey(java.lang.String taskKey) throws wt.util.WTPropertyVetoException {
      taskKeyValidate(taskKey);
      this.taskKey = taskKey;
   }
   void taskKeyValidate(java.lang.String taskKey) throws wt.util.WTPropertyVetoException {
      if (TASK_KEY_UPPER_LIMIT < 1) {
         try { TASK_KEY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskKey").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_KEY_UPPER_LIMIT = 200; }
      }
      if (taskKey != null && !wt.fc.PersistenceHelper.checkStoredLength(taskKey.toString(), TASK_KEY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskKey"), java.lang.String.valueOf(java.lang.Math.min(TASK_KEY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskKey", this.taskKey, taskKey));
   }

   /**
    * 담당자 Role
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String PERSON_ROLE = "personRole";
   static int PERSON_ROLE_UPPER_LIMIT = -1;
   java.lang.String personRole;
   /**
    * 담당자 Role
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getPersonRole() {
      return personRole;
   }
   /**
    * 담당자 Role
    *
    * @see e3ps.project.TemplateTask
    */
   public void setPersonRole(java.lang.String personRole) throws wt.util.WTPropertyVetoException {
      personRoleValidate(personRole);
      this.personRole = personRole;
   }
   void personRoleValidate(java.lang.String personRole) throws wt.util.WTPropertyVetoException {
      if (PERSON_ROLE_UPPER_LIMIT < 1) {
         try { PERSON_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("personRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PERSON_ROLE_UPPER_LIMIT = 200; }
      }
      if (personRole != null && !wt.fc.PersistenceHelper.checkStoredLength(personRole.toString(), PERSON_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "personRole"), java.lang.String.valueOf(java.lang.Math.min(PERSON_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "personRole", this.personRole, personRole));
   }

   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String MILE_STONE = "mileStone";
   boolean mileStone;
   /**
    * @see e3ps.project.TemplateTask
    */
   public boolean isMileStone() {
      return mileStone;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setMileStone(boolean mileStone) throws wt.util.WTPropertyVetoException {
      mileStoneValidate(mileStone);
      this.mileStone = mileStone;
   }
   void mileStoneValidate(boolean mileStone) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 일정 종속 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String SUB = "sub";
   boolean sub;
   /**
    * 일정 종속 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public boolean isSub() {
      return sub;
   }
   /**
    * 일정 종속 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public void setSub(boolean sub) throws wt.util.WTPropertyVetoException {
      subValidate(sub);
      this.sub = sub;
   }
   void subValidate(boolean sub) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 종속시 제품 프로젝트 테스크명
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String SUPER_TASK_NAME = "superTaskName";
   static int SUPER_TASK_NAME_UPPER_LIMIT = -1;
   java.lang.String superTaskName;
   /**
    * 종속시 제품 프로젝트 테스크명
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getSuperTaskName() {
      return superTaskName;
   }
   /**
    * 종속시 제품 프로젝트 테스크명
    *
    * @see e3ps.project.TemplateTask
    */
   public void setSuperTaskName(java.lang.String superTaskName) throws wt.util.WTPropertyVetoException {
      superTaskNameValidate(superTaskName);
      this.superTaskName = superTaskName;
   }
   void superTaskNameValidate(java.lang.String superTaskName) throws wt.util.WTPropertyVetoException {
      if (SUPER_TASK_NAME_UPPER_LIMIT < 1) {
         try { SUPER_TASK_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("superTaskName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUPER_TASK_NAME_UPPER_LIMIT = 200; }
      }
      if (superTaskName != null && !wt.fc.PersistenceHelper.checkStoredLength(superTaskName.toString(), SUPER_TASK_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "superTaskName"), java.lang.String.valueOf(java.lang.Math.min(SUPER_TASK_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "superTaskName", this.superTaskName, superTaskName));
   }

   /**
    * 옵션/필수
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String OPTION_TYPE = "optionType";
   boolean optionType;
   /**
    * 옵션/필수
    *
    * @see e3ps.project.TemplateTask
    */
   public boolean isOptionType() {
      return optionType;
   }
   /**
    * 옵션/필수
    *
    * @see e3ps.project.TemplateTask
    */
   public void setOptionType(boolean optionType) throws wt.util.WTPropertyVetoException {
      optionTypeValidate(optionType);
      this.optionType = optionType;
   }
   void optionTypeValidate(boolean optionType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 금형 Task에서 제품 Task 여부≪≫제품 Task에서 제품 Task 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String OUT_TASK = "outTask";
   boolean outTask;
   /**
    * 금형 Task에서 제품 Task 여부≪≫제품 Task에서 제품 Task 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public boolean isOutTask() {
      return outTask;
   }
   /**
    * 금형 Task에서 제품 Task 여부≪≫제품 Task에서 제품 Task 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public void setOutTask(boolean outTask) throws wt.util.WTPropertyVetoException {
      outTaskValidate(outTask);
      this.outTask = outTask;
   }
   void outTaskValidate(boolean outTask) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String DR_VALUE = "drValue";
   static int DR_VALUE_UPPER_LIMIT = -1;
   java.lang.String drValue;
   /**
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getDrValue() {
      return drValue;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setDrValue(java.lang.String drValue) throws wt.util.WTPropertyVetoException {
      drValueValidate(drValue);
      this.drValue = drValue;
   }
   void drValueValidate(java.lang.String drValue) throws wt.util.WTPropertyVetoException {
      if (DR_VALUE_UPPER_LIMIT < 1) {
         try { DR_VALUE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("drValue").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DR_VALUE_UPPER_LIMIT = 200; }
      }
      if (drValue != null && !wt.fc.PersistenceHelper.checkStoredLength(drValue.toString(), DR_VALUE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "drValue"), java.lang.String.valueOf(java.lang.Math.min(DR_VALUE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "drValue", this.drValue, drValue));
   }

   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String DR_VALUE_CONDITION = "drValueCondition";
   static int DR_VALUE_CONDITION_UPPER_LIMIT = -1;
   java.lang.String drValueCondition;
   /**
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getDrValueCondition() {
      return drValueCondition;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setDrValueCondition(java.lang.String drValueCondition) throws wt.util.WTPropertyVetoException {
      drValueConditionValidate(drValueCondition);
      this.drValueCondition = drValueCondition;
   }
   void drValueConditionValidate(java.lang.String drValueCondition) throws wt.util.WTPropertyVetoException {
      if (DR_VALUE_CONDITION_UPPER_LIMIT < 1) {
         try { DR_VALUE_CONDITION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("drValueCondition").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DR_VALUE_CONDITION_UPPER_LIMIT = 200; }
      }
      if (drValueCondition != null && !wt.fc.PersistenceHelper.checkStoredLength(drValueCondition.toString(), DR_VALUE_CONDITION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "drValueCondition"), java.lang.String.valueOf(java.lang.Math.min(DR_VALUE_CONDITION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "drValueCondition", this.drValueCondition, drValueCondition));
   }

   /**
    * 주요일정 식별 코드
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String MAIN_SCHEDULE_CODE = "mainScheduleCode";
   static int MAIN_SCHEDULE_CODE_UPPER_LIMIT = -1;
   java.lang.String mainScheduleCode;
   /**
    * 주요일정 식별 코드
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getMainScheduleCode() {
      return mainScheduleCode;
   }
   /**
    * 주요일정 식별 코드
    *
    * @see e3ps.project.TemplateTask
    */
   public void setMainScheduleCode(java.lang.String mainScheduleCode) throws wt.util.WTPropertyVetoException {
      mainScheduleCodeValidate(mainScheduleCode);
      this.mainScheduleCode = mainScheduleCode;
   }
   void mainScheduleCodeValidate(java.lang.String mainScheduleCode) throws wt.util.WTPropertyVetoException {
      if (MAIN_SCHEDULE_CODE_UPPER_LIMIT < 1) {
         try { MAIN_SCHEDULE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mainScheduleCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAIN_SCHEDULE_CODE_UPPER_LIMIT = 200; }
      }
      if (mainScheduleCode != null && !wt.fc.PersistenceHelper.checkStoredLength(mainScheduleCode.toString(), MAIN_SCHEDULE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mainScheduleCode"), java.lang.String.valueOf(java.lang.Math.min(MAIN_SCHEDULE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mainScheduleCode", this.mainScheduleCode, mainScheduleCode));
   }

   /**
    * 타스크 일정연계 코드
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String SCHEDULE_TYPE = "scheduleType";
   static int SCHEDULE_TYPE_UPPER_LIMIT = -1;
   java.lang.String scheduleType;
   /**
    * 타스크 일정연계 코드
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getScheduleType() {
      return scheduleType;
   }
   /**
    * 타스크 일정연계 코드
    *
    * @see e3ps.project.TemplateTask
    */
   public void setScheduleType(java.lang.String scheduleType) throws wt.util.WTPropertyVetoException {
      scheduleTypeValidate(scheduleType);
      this.scheduleType = scheduleType;
   }
   void scheduleTypeValidate(java.lang.String scheduleType) throws wt.util.WTPropertyVetoException {
      if (SCHEDULE_TYPE_UPPER_LIMIT < 1) {
         try { SCHEDULE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scheduleType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCHEDULE_TYPE_UPPER_LIMIT = 200; }
      }
      if (scheduleType != null && !wt.fc.PersistenceHelper.checkStoredLength(scheduleType.toString(), SCHEDULE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scheduleType"), java.lang.String.valueOf(java.lang.Math.min(SCHEDULE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scheduleType", this.scheduleType, scheduleType));
   }

   /**
    * 중점관리 Task 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String PRIORITY_CONTROL = "priorityControl";
   static int PRIORITY_CONTROL_UPPER_LIMIT = -1;
   java.lang.String priorityControl;
   /**
    * 중점관리 Task 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getPriorityControl() {
      return priorityControl;
   }
   /**
    * 중점관리 Task 여부
    *
    * @see e3ps.project.TemplateTask
    */
   public void setPriorityControl(java.lang.String priorityControl) throws wt.util.WTPropertyVetoException {
      priorityControlValidate(priorityControl);
      this.priorityControl = priorityControl;
   }
   void priorityControlValidate(java.lang.String priorityControl) throws wt.util.WTPropertyVetoException {
      if (PRIORITY_CONTROL_UPPER_LIMIT < 1) {
         try { PRIORITY_CONTROL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("priorityControl").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRIORITY_CONTROL_UPPER_LIMIT = 200; }
      }
      if (priorityControl != null && !wt.fc.PersistenceHelper.checkStoredLength(priorityControl.toString(), PRIORITY_CONTROL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "priorityControl"), java.lang.String.valueOf(java.lang.Math.min(PRIORITY_CONTROL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "priorityControl", this.priorityControl, priorityControl));
   }

   /**
    * 계획작업시간
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String PLAN_WORK_TIME = "planWorkTime";
   float planWorkTime;
   /**
    * 계획작업시간
    *
    * @see e3ps.project.TemplateTask
    */
   public float getPlanWorkTime() {
      return planWorkTime;
   }
   /**
    * 계획작업시간
    *
    * @see e3ps.project.TemplateTask
    */
   public void setPlanWorkTime(float planWorkTime) throws wt.util.WTPropertyVetoException {
      planWorkTimeValidate(planWorkTime);
      this.planWorkTime = planWorkTime;
   }
   void planWorkTimeValidate(float planWorkTime) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 실제작업시간
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String EXEC_WORK_TIME = "execWorkTime";
   float execWorkTime;
   /**
    * 실제작업시간
    *
    * @see e3ps.project.TemplateTask
    */
   public float getExecWorkTime() {
      return execWorkTime;
   }
   /**
    * 실제작업시간
    *
    * @see e3ps.project.TemplateTask
    */
   public void setExecWorkTime(float execWorkTime) throws wt.util.WTPropertyVetoException {
      execWorkTimeValidate(execWorkTime);
      this.execWorkTime = execWorkTime;
   }
   void execWorkTimeValidate(float execWorkTime) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 지연사유
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String DELAY_REASON = "delayReason";
   static int DELAY_REASON_UPPER_LIMIT = -1;
   java.lang.String delayReason;
   /**
    * 지연사유
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getDelayReason() {
      return delayReason;
   }
   /**
    * 지연사유
    *
    * @see e3ps.project.TemplateTask
    */
   public void setDelayReason(java.lang.String delayReason) throws wt.util.WTPropertyVetoException {
      delayReasonValidate(delayReason);
      this.delayReason = delayReason;
   }
   void delayReasonValidate(java.lang.String delayReason) throws wt.util.WTPropertyVetoException {
      if (DELAY_REASON_UPPER_LIMIT < 1) {
         try { DELAY_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("delayReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DELAY_REASON_UPPER_LIMIT = 200; }
      }
      if (delayReason != null && !wt.fc.PersistenceHelper.checkStoredLength(delayReason.toString(), DELAY_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "delayReason"), java.lang.String.valueOf(java.lang.Math.min(DELAY_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "delayReason", this.delayReason, delayReason));
   }

   /**
    * 지연사유유형
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String DELAY_TYPE = "delayType";
   static int DELAY_TYPE_UPPER_LIMIT = -1;
   java.lang.String delayType;
   /**
    * 지연사유유형
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getDelayType() {
      return delayType;
   }
   /**
    * 지연사유유형
    *
    * @see e3ps.project.TemplateTask
    */
   public void setDelayType(java.lang.String delayType) throws wt.util.WTPropertyVetoException {
      delayTypeValidate(delayType);
      this.delayType = delayType;
   }
   void delayTypeValidate(java.lang.String delayType) throws wt.util.WTPropertyVetoException {
      if (DELAY_TYPE_UPPER_LIMIT < 1) {
         try { DELAY_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("delayType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DELAY_TYPE_UPPER_LIMIT = 200; }
      }
      if (delayType != null && !wt.fc.PersistenceHelper.checkStoredLength(delayType.toString(), DELAY_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "delayType"), java.lang.String.valueOf(java.lang.Math.min(DELAY_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "delayType", this.delayType, delayType));
   }

   /**
    * 구분-신규
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String NEW_TYPE = "newType";
   int newType;
   /**
    * 구분-신규
    *
    * @see e3ps.project.TemplateTask
    */
   public int getNewType() {
      return newType;
   }
   /**
    * 구분-신규
    *
    * @see e3ps.project.TemplateTask
    */
   public void setNewType(int newType) throws wt.util.WTPropertyVetoException {
      newTypeValidate(newType);
      this.newType = newType;
   }
   void newTypeValidate(int newType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 구분-Modify
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String MODIFY_TYPE = "modifyType";
   int modifyType;
   /**
    * 구분-Modify
    *
    * @see e3ps.project.TemplateTask
    */
   public int getModifyType() {
      return modifyType;
   }
   /**
    * 구분-Modify
    *
    * @see e3ps.project.TemplateTask
    */
   public void setModifyType(int modifyType) throws wt.util.WTPropertyVetoException {
      modifyTypeValidate(modifyType);
      this.modifyType = modifyType;
   }
   void modifyTypeValidate(int modifyType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-공통
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String COMMON = "common";
   int common;
   /**
    * Category-공통
    *
    * @see e3ps.project.TemplateTask
    */
   public int getCommon() {
      return common;
   }
   /**
    * Category-공통
    *
    * @see e3ps.project.TemplateTask
    */
   public void setCommon(int common) throws wt.util.WTPropertyVetoException {
      commonValidate(common);
      this.common = common;
   }
   void commonValidate(int common) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-기구
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String MDRAW = "mdraw";
   int mdraw;
   /**
    * Category-기구
    *
    * @see e3ps.project.TemplateTask
    */
   public int getMdraw() {
      return mdraw;
   }
   /**
    * Category-기구
    *
    * @see e3ps.project.TemplateTask
    */
   public void setMdraw(int mdraw) throws wt.util.WTPropertyVetoException {
      mdrawValidate(mdraw);
      this.mdraw = mdraw;
   }
   void mdrawValidate(int mdraw) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-HW
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String HW = "hw";
   int hw;
   /**
    * Category-HW
    *
    * @see e3ps.project.TemplateTask
    */
   public int getHw() {
      return hw;
   }
   /**
    * Category-HW
    *
    * @see e3ps.project.TemplateTask
    */
   public void setHw(int hw) throws wt.util.WTPropertyVetoException {
      hwValidate(hw);
      this.hw = hw;
   }
   void hwValidate(int hw) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-SW
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String SW = "sw";
   int sw;
   /**
    * Category-SW
    *
    * @see e3ps.project.TemplateTask
    */
   public int getSw() {
      return sw;
   }
   /**
    * Category-SW
    *
    * @see e3ps.project.TemplateTask
    */
   public void setSw(int sw) throws wt.util.WTPropertyVetoException {
      swValidate(sw);
      this.sw = sw;
   }
   void swValidate(int sw) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-M
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String M = "m";
   int m;
   /**
    * Category-M
    *
    * @see e3ps.project.TemplateTask
    */
   public int getM() {
      return m;
   }
   /**
    * Category-M
    *
    * @see e3ps.project.TemplateTask
    */
   public void setM(int m) throws wt.util.WTPropertyVetoException {
      mValidate(m);
      this.m = m;
   }
   void mValidate(int m) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-P
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String P = "p";
   int p;
   /**
    * Category-P
    *
    * @see e3ps.project.TemplateTask
    */
   public int getP() {
      return p;
   }
   /**
    * Category-P
    *
    * @see e3ps.project.TemplateTask
    */
   public void setP(int p) throws wt.util.WTPropertyVetoException {
      pValidate(p);
      this.p = p;
   }
   void pValidate(int p) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-구매
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String BUY = "buy";
   int buy;
   /**
    * Category-구매
    *
    * @see e3ps.project.TemplateTask
    */
   public int getBuy() {
      return buy;
   }
   /**
    * Category-구매
    *
    * @see e3ps.project.TemplateTask
    */
   public void setBuy(int buy) throws wt.util.WTPropertyVetoException {
      buyValidate(buy);
      this.buy = buy;
   }
   void buyValidate(int buy) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Category-설비
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String SYSTEM = "system";
   int system;
   /**
    * Category-설비
    *
    * @see e3ps.project.TemplateTask
    */
   public int getSystem() {
      return system;
   }
   /**
    * Category-설비
    *
    * @see e3ps.project.TemplateTask
    */
   public void setSystem(int system) throws wt.util.WTPropertyVetoException {
      systemValidate(system);
      this.system = system;
   }
   void systemValidate(int system) throws wt.util.WTPropertyVetoException {
   }

   /**
    * taskType유형
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_TYPE_CATEGORY = "taskTypeCategory";
   static int TASK_TYPE_CATEGORY_UPPER_LIMIT = -1;
   java.lang.String taskTypeCategory;
   /**
    * taskType유형
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getTaskTypeCategory() {
      return taskTypeCategory;
   }
   /**
    * taskType유형
    *
    * @see e3ps.project.TemplateTask
    */
   public void setTaskTypeCategory(java.lang.String taskTypeCategory) throws wt.util.WTPropertyVetoException {
      taskTypeCategoryValidate(taskTypeCategory);
      this.taskTypeCategory = taskTypeCategory;
   }
   void taskTypeCategoryValidate(java.lang.String taskTypeCategory) throws wt.util.WTPropertyVetoException {
      if (TASK_TYPE_CATEGORY_UPPER_LIMIT < 1) {
         try { TASK_TYPE_CATEGORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("taskTypeCategory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TASK_TYPE_CATEGORY_UPPER_LIMIT = 200; }
      }
      if (taskTypeCategory != null && !wt.fc.PersistenceHelper.checkStoredLength(taskTypeCategory.toString(), TASK_TYPE_CATEGORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskTypeCategory"), java.lang.String.valueOf(java.lang.Math.min(TASK_TYPE_CATEGORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "taskTypeCategory", this.taskTypeCategory, taskTypeCategory));
   }

   /**
    * 계획시작일
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String PLAN_START_DATE = "planStartDate";
   static int PLAN_START_DATE_UPPER_LIMIT = -1;
   java.lang.String planStartDate;
   /**
    * 계획시작일
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getPlanStartDate() {
      return planStartDate;
   }
   /**
    * 계획시작일
    *
    * @see e3ps.project.TemplateTask
    */
   public void setPlanStartDate(java.lang.String planStartDate) throws wt.util.WTPropertyVetoException {
      planStartDateValidate(planStartDate);
      this.planStartDate = planStartDate;
   }
   void planStartDateValidate(java.lang.String planStartDate) throws wt.util.WTPropertyVetoException {
      if (PLAN_START_DATE_UPPER_LIMIT < 1) {
         try { PLAN_START_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planStartDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_START_DATE_UPPER_LIMIT = 200; }
      }
      if (planStartDate != null && !wt.fc.PersistenceHelper.checkStoredLength(planStartDate.toString(), PLAN_START_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planStartDate"), java.lang.String.valueOf(java.lang.Math.min(PLAN_START_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planStartDate", this.planStartDate, planStartDate));
   }

   /**
    * 계획완료일
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String PLAN_END_DATE = "planEndDate";
   static int PLAN_END_DATE_UPPER_LIMIT = -1;
   java.lang.String planEndDate;
   /**
    * 계획완료일
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getPlanEndDate() {
      return planEndDate;
   }
   /**
    * 계획완료일
    *
    * @see e3ps.project.TemplateTask
    */
   public void setPlanEndDate(java.lang.String planEndDate) throws wt.util.WTPropertyVetoException {
      planEndDateValidate(planEndDate);
      this.planEndDate = planEndDate;
   }
   void planEndDateValidate(java.lang.String planEndDate) throws wt.util.WTPropertyVetoException {
      if (PLAN_END_DATE_UPPER_LIMIT < 1) {
         try { PLAN_END_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planEndDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_END_DATE_UPPER_LIMIT = 200; }
      }
      if (planEndDate != null && !wt.fc.PersistenceHelper.checkStoredLength(planEndDate.toString(), PLAN_END_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planEndDate"), java.lang.String.valueOf(java.lang.Math.min(PLAN_END_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planEndDate", this.planEndDate, planEndDate));
   }

   /**
    * 과거TaskOid
    *
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String OLD_TASK_OID = "oldTaskOid";
   static int OLD_TASK_OID_UPPER_LIMIT = -1;
   java.lang.String oldTaskOid;
   /**
    * 과거TaskOid
    *
    * @see e3ps.project.TemplateTask
    */
   public java.lang.String getOldTaskOid() {
      return oldTaskOid;
   }
   /**
    * 과거TaskOid
    *
    * @see e3ps.project.TemplateTask
    */
   public void setOldTaskOid(java.lang.String oldTaskOid) throws wt.util.WTPropertyVetoException {
      oldTaskOidValidate(oldTaskOid);
      this.oldTaskOid = oldTaskOid;
   }
   void oldTaskOidValidate(java.lang.String oldTaskOid) throws wt.util.WTPropertyVetoException {
      if (OLD_TASK_OID_UPPER_LIMIT < 1) {
         try { OLD_TASK_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("oldTaskOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OLD_TASK_OID_UPPER_LIMIT = 200; }
      }
      if (oldTaskOid != null && !wt.fc.PersistenceHelper.checkStoredLength(oldTaskOid.toString(), OLD_TASK_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oldTaskOid"), java.lang.String.valueOf(java.lang.Math.min(OLD_TASK_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "oldTaskOid", this.oldTaskOid, oldTaskOid));
   }

   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.TemplateTask
    */
   public e3ps.project.TemplateProject getProject() {
      return (projectReference != null) ? (e3ps.project.TemplateProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setProject(e3ps.project.TemplateProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.TemplateProject) the_project));
   }
   /**
    * @see e3ps.project.TemplateTask
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
            !e3ps.project.TemplateProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String DEPARTMENT = "department";
   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String DEPARTMENT_REFERENCE = "departmentReference";
   wt.fc.ObjectReference departmentReference;
   /**
    * @see e3ps.project.TemplateTask
    */
   public e3ps.groupware.company.Department getDepartment() {
      return (departmentReference != null) ? (e3ps.groupware.company.Department) departmentReference.getObject() : null;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public wt.fc.ObjectReference getDepartmentReference() {
      return departmentReference;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setDepartment(e3ps.groupware.company.Department the_department) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDepartmentReference(the_department == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_department));
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setDepartmentReference(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      departmentReferenceValidate(the_departmentReference);
      departmentReference = (wt.fc.ObjectReference) the_departmentReference;
   }
   void departmentReferenceValidate(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      if (the_departmentReference != null && the_departmentReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_departmentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "departmentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "departmentReference", this.departmentReference, departmentReference));
   }

   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_WORK = "taskWork";
   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String TASK_WORK_REFERENCE = "taskWorkReference";
   wt.fc.ObjectReference taskWorkReference;
   /**
    * @see e3ps.project.TemplateTask
    */
   public e3ps.project.PlanTaskWork getTaskWork() {
      return (taskWorkReference != null) ? (e3ps.project.PlanTaskWork) taskWorkReference.getObject() : null;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public wt.fc.ObjectReference getTaskWorkReference() {
      return taskWorkReference;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setTaskWork(e3ps.project.PlanTaskWork the_taskWork) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTaskWorkReference(the_taskWork == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.PlanTaskWork) the_taskWork));
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setTaskWorkReference(wt.fc.ObjectReference the_taskWorkReference) throws wt.util.WTPropertyVetoException {
      taskWorkReferenceValidate(the_taskWorkReference);
      taskWorkReference = (wt.fc.ObjectReference) the_taskWorkReference;
   }
   void taskWorkReferenceValidate(wt.fc.ObjectReference the_taskWorkReference) throws wt.util.WTPropertyVetoException {
      if (the_taskWorkReference != null && the_taskWorkReference.getReferencedClass() != null &&
            !e3ps.project.PlanTaskWork.class.isAssignableFrom(the_taskWorkReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskWorkReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "taskWorkReference", this.taskWorkReference, taskWorkReference));
   }

   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String OEM_TYPE = "oemType";
   /**
    * @see e3ps.project.TemplateTask
    */
   public static final java.lang.String OEM_TYPE_REFERENCE = "oemTypeReference";
   wt.fc.ObjectReference oemTypeReference;
   /**
    * @see e3ps.project.TemplateTask
    */
   public e3ps.project.outputtype.OEMProjectType getOemType() {
      return (oemTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) oemTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public wt.fc.ObjectReference getOemTypeReference() {
      return oemTypeReference;
   }
   /**
    * @see e3ps.project.TemplateTask
    */
   public void setOemType(e3ps.project.outputtype.OEMProjectType the_oemType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOemTypeReference(the_oemType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_oemType));
   }
   /**
    * @see e3ps.project.TemplateTask
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

   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent() {
      return (parentReference != null) ? (e3ps.common.impl.Tree) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.impl.Tree) the_parent));
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParentReference(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      parentReferenceValidate(the_parentReference);
      parentReference = (wt.fc.ObjectReference) the_parentReference;
   }
   void parentReferenceValidate(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      if (the_parentReference == null || the_parentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference") },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
      if (the_parentReference != null && the_parentReference.getReferencedClass() != null &&
            !e3ps.common.impl.Tree.class.isAssignableFrom(the_parentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 4872175898223208035L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeInt( buy );
      output.writeInt( common );
      output.writeObject( delayReason );
      output.writeObject( delayType );
      output.writeObject( departmentReference );
      output.writeObject( drValue );
      output.writeObject( drValueCondition );
      output.writeFloat( execWorkTime );
      output.writeInt( hw );
      output.writeInt( m );
      output.writeObject( mainScheduleCode );
      output.writeInt( mdraw );
      output.writeBoolean( mileStone );
      output.writeInt( modifyType );
      output.writeInt( newType );
      output.writeObject( oemTypeReference );
      output.writeObject( oldTaskOid );
      output.writeBoolean( optionType );
      output.writeBoolean( outTask );
      output.writeInt( p );
      output.writeObject( parentReference );
      output.writeObject( personRole );
      output.writeObject( planEndDate );
      output.writeObject( planStartDate );
      output.writeFloat( planWorkTime );
      output.writeObject( priorityControl );
      output.writeObject( projectReference );
      output.writeObject( scheduleType );
      output.writeBoolean( sub );
      output.writeObject( superTaskName );
      output.writeInt( sw );
      output.writeInt( system );
      output.writeObject( taskCode );
      output.writeObject( taskDesc );
      output.writeInt( taskHistory );
      output.writeObject( taskKey );
      output.writeObject( taskName );
      output.writeObject( taskNameEn );
      output.writeObject( taskNo );
      output.writeObject( taskSchedule );
      output.writeInt( taskSeq );
      output.writeObject( taskType );
      output.writeObject( taskTypeCategory );
      output.writeObject( taskWorkReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.TemplateTask) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setInt( "buy", buy );
      output.setInt( "common", common );
      output.setString( "delayReason", delayReason );
      output.setString( "delayType", delayType );
      output.writeObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      output.setString( "drValue", drValue );
      output.setString( "drValueCondition", drValueCondition );
      output.setFloat( "execWorkTime", execWorkTime );
      output.setInt( "hw", hw );
      output.setInt( "m", m );
      output.setString( "mainScheduleCode", mainScheduleCode );
      output.setInt( "mdraw", mdraw );
      output.setBoolean( "mileStone", mileStone );
      output.setInt( "modifyType", modifyType );
      output.setInt( "newType", newType );
      output.writeObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "oldTaskOid", oldTaskOid );
      output.setBoolean( "optionType", optionType );
      output.setBoolean( "outTask", outTask );
      output.setInt( "p", p );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setString( "personRole", personRole );
      output.setString( "planEndDate", planEndDate );
      output.setString( "planStartDate", planStartDate );
      output.setFloat( "planWorkTime", planWorkTime );
      output.setString( "priorityControl", priorityControl );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "scheduleType", scheduleType );
      output.setBoolean( "sub", sub );
      output.setString( "superTaskName", superTaskName );
      output.setInt( "sw", sw );
      output.setInt( "system", system );
      output.setString( "taskCode", taskCode );
      output.setString( "taskDesc", taskDesc );
      output.setInt( "taskHistory", taskHistory );
      output.setString( "taskKey", taskKey );
      output.setString( "taskName", taskName );
      output.setString( "taskNameEn", taskNameEn );
      output.setString( "taskNo", taskNo );
      output.writeObject( "taskSchedule", taskSchedule, wt.fc.ObjectReference.class, true );
      output.setInt( "taskSeq", taskSeq );
      output.setString( "taskType", taskType );
      output.setString( "taskTypeCategory", taskTypeCategory );
      output.writeObject( "taskWorkReference", taskWorkReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      buy = input.getInt( "buy" );
      common = input.getInt( "common" );
      delayReason = input.getString( "delayReason" );
      delayType = input.getString( "delayType" );
      departmentReference = (wt.fc.ObjectReference) input.readObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      drValue = input.getString( "drValue" );
      drValueCondition = input.getString( "drValueCondition" );
      execWorkTime = input.getFloat( "execWorkTime" );
      hw = input.getInt( "hw" );
      m = input.getInt( "m" );
      mainScheduleCode = input.getString( "mainScheduleCode" );
      mdraw = input.getInt( "mdraw" );
      mileStone = input.getBoolean( "mileStone" );
      modifyType = input.getInt( "modifyType" );
      newType = input.getInt( "newType" );
      oemTypeReference = (wt.fc.ObjectReference) input.readObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      oldTaskOid = input.getString( "oldTaskOid" );
      optionType = input.getBoolean( "optionType" );
      outTask = input.getBoolean( "outTask" );
      p = input.getInt( "p" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      personRole = input.getString( "personRole" );
      planEndDate = input.getString( "planEndDate" );
      planStartDate = input.getString( "planStartDate" );
      planWorkTime = input.getFloat( "planWorkTime" );
      priorityControl = input.getString( "priorityControl" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      scheduleType = input.getString( "scheduleType" );
      sub = input.getBoolean( "sub" );
      superTaskName = input.getString( "superTaskName" );
      sw = input.getInt( "sw" );
      system = input.getInt( "system" );
      taskCode = input.getString( "taskCode" );
      taskDesc = input.getString( "taskDesc" );
      taskHistory = input.getInt( "taskHistory" );
      taskKey = input.getString( "taskKey" );
      taskName = input.getString( "taskName" );
      taskNameEn = input.getString( "taskNameEn" );
      taskNo = input.getString( "taskNo" );
      taskSchedule = (wt.fc.ObjectReference) input.readObject( "taskSchedule", taskSchedule, wt.fc.ObjectReference.class, true );
      taskSeq = input.getInt( "taskSeq" );
      taskType = input.getString( "taskType" );
      taskTypeCategory = input.getString( "taskTypeCategory" );
      taskWorkReference = (wt.fc.ObjectReference) input.readObject( "taskWorkReference", taskWorkReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion4872175898223208035L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      buy = input.readInt();
      common = input.readInt();
      delayReason = (java.lang.String) input.readObject();
      delayType = (java.lang.String) input.readObject();
      departmentReference = (wt.fc.ObjectReference) input.readObject();
      drValue = (java.lang.String) input.readObject();
      drValueCondition = (java.lang.String) input.readObject();
      execWorkTime = input.readFloat();
      hw = input.readInt();
      m = input.readInt();
      mainScheduleCode = (java.lang.String) input.readObject();
      mdraw = input.readInt();
      mileStone = input.readBoolean();
      modifyType = input.readInt();
      newType = input.readInt();
      oemTypeReference = (wt.fc.ObjectReference) input.readObject();
      oldTaskOid = (java.lang.String) input.readObject();
      optionType = input.readBoolean();
      outTask = input.readBoolean();
      p = input.readInt();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      personRole = (java.lang.String) input.readObject();
      planEndDate = (java.lang.String) input.readObject();
      planStartDate = (java.lang.String) input.readObject();
      planWorkTime = input.readFloat();
      priorityControl = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      scheduleType = (java.lang.String) input.readObject();
      sub = input.readBoolean();
      superTaskName = (java.lang.String) input.readObject();
      sw = input.readInt();
      system = input.readInt();
      taskCode = (java.lang.String) input.readObject();
      taskDesc = (java.lang.String) input.readObject();
      taskHistory = input.readInt();
      taskKey = (java.lang.String) input.readObject();
      taskName = (java.lang.String) input.readObject();
      taskNameEn = (java.lang.String) input.readObject();
      taskNo = (java.lang.String) input.readObject();
      taskSchedule = (wt.fc.ObjectReference) input.readObject();
      taskSeq = input.readInt();
      taskType = (java.lang.String) input.readObject();
      taskTypeCategory = (java.lang.String) input.readObject();
      taskWorkReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( TemplateTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4872175898223208035L( input, readSerialVersionUID, superDone );
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
