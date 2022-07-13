package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostPartType implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostPartType.class.getName();

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setName(java.lang.String name) throws wt.util.WTPropertyVetoException {
      nameValidate(name);
      this.name = name;
   }
   void nameValidate(java.lang.String name) throws wt.util.WTPropertyVetoException {
      if (NAME_UPPER_LIMIT < 1) {
         try { NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("name").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAME_UPPER_LIMIT = 200; }
      }
      if (name != null && !wt.fc.PersistenceHelper.checkStoredLength(name.toString(), NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "name"), java.lang.String.valueOf(java.lang.Math.min(NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "name", this.name, name));
   }

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String CODE = "code";
   static int CODE_UPPER_LIMIT = -1;
   java.lang.String code;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getCode() {
      return code;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setCode(java.lang.String code) throws wt.util.WTPropertyVetoException {
      codeValidate(code);
      this.code = code;
   }
   void codeValidate(java.lang.String code) throws wt.util.WTPropertyVetoException {
      if (CODE_UPPER_LIMIT < 1) {
         try { CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("code").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CODE_UPPER_LIMIT = 200; }
      }
      if (code != null && !wt.fc.PersistenceHelper.checkStoredLength(code.toString(), CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "code"), java.lang.String.valueOf(java.lang.Math.min(CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "code", this.code, code));
   }

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String LVL = "lvl";
   static int LVL_UPPER_LIMIT = -1;
   java.lang.String lvl;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getLvl() {
      return lvl;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setLvl(java.lang.String lvl) throws wt.util.WTPropertyVetoException {
      lvlValidate(lvl);
      this.lvl = lvl;
   }
   void lvlValidate(java.lang.String lvl) throws wt.util.WTPropertyVetoException {
      if (LVL_UPPER_LIMIT < 1) {
         try { LVL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lvl").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LVL_UPPER_LIMIT = 200; }
      }
      if (lvl != null && !wt.fc.PersistenceHelper.checkStoredLength(lvl.toString(), LVL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lvl"), java.lang.String.valueOf(java.lang.Math.min(LVL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lvl", this.lvl, lvl));
   }

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String LVL_OPTION = "lvlOption";
   static int LVL_OPTION_UPPER_LIMIT = -1;
   java.lang.String lvlOption;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getLvlOption() {
      return lvlOption;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setLvlOption(java.lang.String lvlOption) throws wt.util.WTPropertyVetoException {
      lvlOptionValidate(lvlOption);
      this.lvlOption = lvlOption;
   }
   void lvlOptionValidate(java.lang.String lvlOption) throws wt.util.WTPropertyVetoException {
      if (LVL_OPTION_UPPER_LIMIT < 1) {
         try { LVL_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lvlOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LVL_OPTION_UPPER_LIMIT = 200; }
      }
      if (lvlOption != null && !wt.fc.PersistenceHelper.checkStoredLength(lvlOption.toString(), LVL_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lvlOption"), java.lang.String.valueOf(java.lang.Math.min(LVL_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lvlOption", this.lvlOption, lvlOption));
   }

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String PARENT_CFG = "parentCfg";
   static int PARENT_CFG_UPPER_LIMIT = -1;
   java.lang.String parentCfg;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getParentCfg() {
      return parentCfg;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setParentCfg(java.lang.String parentCfg) throws wt.util.WTPropertyVetoException {
      parentCfgValidate(parentCfg);
      this.parentCfg = parentCfg;
   }
   void parentCfgValidate(java.lang.String parentCfg) throws wt.util.WTPropertyVetoException {
      if (PARENT_CFG_UPPER_LIMIT < 1) {
         try { PARENT_CFG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("parentCfg").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PARENT_CFG_UPPER_LIMIT = 200; }
      }
      if (parentCfg != null && !wt.fc.PersistenceHelper.checkStoredLength(parentCfg.toString(), PARENT_CFG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentCfg"), java.lang.String.valueOf(java.lang.Math.min(PARENT_CFG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "parentCfg", this.parentCfg, parentCfg));
   }

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String CHILD_CFG = "childCfg";
   static int CHILD_CFG_UPPER_LIMIT = -1;
   java.lang.String childCfg;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getChildCfg() {
      return childCfg;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setChildCfg(java.lang.String childCfg) throws wt.util.WTPropertyVetoException {
      childCfgValidate(childCfg);
      this.childCfg = childCfg;
   }
   void childCfgValidate(java.lang.String childCfg) throws wt.util.WTPropertyVetoException {
      if (CHILD_CFG_UPPER_LIMIT < 1) {
         try { CHILD_CFG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("childCfg").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHILD_CFG_UPPER_LIMIT = 200; }
      }
      if (childCfg != null && !wt.fc.PersistenceHelper.checkStoredLength(childCfg.toString(), CHILD_CFG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "childCfg"), java.lang.String.valueOf(java.lang.Math.min(CHILD_CFG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "childCfg", this.childCfg, childCfg));
   }

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String TASK_CODE = "taskCode";
   static int TASK_CODE_UPPER_LIMIT = -1;
   java.lang.String taskCode;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getTaskCode() {
      return taskCode;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
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
    * 조업도(시간)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String WORK_HOUR = "workHour";
   static int WORK_HOUR_UPPER_LIMIT = -1;
   java.lang.String workHour;
   /**
    * 조업도(시간)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getWorkHour() {
      return workHour;
   }
   /**
    * 조업도(시간)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setWorkHour(java.lang.String workHour) throws wt.util.WTPropertyVetoException {
      workHourValidate(workHour);
      this.workHour = workHour;
   }
   void workHourValidate(java.lang.String workHour) throws wt.util.WTPropertyVetoException {
      if (WORK_HOUR_UPPER_LIMIT < 1) {
         try { WORK_HOUR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workHour").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_HOUR_UPPER_LIMIT = 200; }
      }
      if (workHour != null && !wt.fc.PersistenceHelper.checkStoredLength(workHour.toString(), WORK_HOUR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workHour"), java.lang.String.valueOf(java.lang.Math.min(WORK_HOUR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workHour", this.workHour, workHour));
   }

   /**
    * 조업도(일)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String WORK_DAY = "workDay";
   static int WORK_DAY_UPPER_LIMIT = -1;
   java.lang.String workDay;
   /**
    * 조업도(일)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getWorkDay() {
      return workDay;
   }
   /**
    * 조업도(일)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setWorkDay(java.lang.String workDay) throws wt.util.WTPropertyVetoException {
      workDayValidate(workDay);
      this.workDay = workDay;
   }
   void workDayValidate(java.lang.String workDay) throws wt.util.WTPropertyVetoException {
      if (WORK_DAY_UPPER_LIMIT < 1) {
         try { WORK_DAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workDay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_DAY_UPPER_LIMIT = 200; }
      }
      if (workDay != null && !wt.fc.PersistenceHelper.checkStoredLength(workDay.toString(), WORK_DAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workDay"), java.lang.String.valueOf(java.lang.Math.min(WORK_DAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workDay", this.workDay, workDay));
   }

   /**
    * 년조업도(월)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String WORK_YEAR = "workYear";
   static int WORK_YEAR_UPPER_LIMIT = -1;
   java.lang.String workYear;
   /**
    * 년조업도(월)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getWorkYear() {
      return workYear;
   }
   /**
    * 년조업도(월)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setWorkYear(java.lang.String workYear) throws wt.util.WTPropertyVetoException {
      workYearValidate(workYear);
      this.workYear = workYear;
   }
   void workYearValidate(java.lang.String workYear) throws wt.util.WTPropertyVetoException {
      if (WORK_YEAR_UPPER_LIMIT < 1) {
         try { WORK_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_YEAR_UPPER_LIMIT = 200; }
      }
      if (workYear != null && !wt.fc.PersistenceHelper.checkStoredLength(workYear.toString(), WORK_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workYear"), java.lang.String.valueOf(java.lang.Math.min(WORK_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workYear", this.workYear, workYear));
   }

   /**
    * 적용년수(capa)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String CAPA_YEAR = "capaYear";
   static int CAPA_YEAR_UPPER_LIMIT = -1;
   java.lang.String capaYear;
   /**
    * 적용년수(capa)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getCapaYear() {
      return capaYear;
   }
   /**
    * 적용년수(capa)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setCapaYear(java.lang.String capaYear) throws wt.util.WTPropertyVetoException {
      capaYearValidate(capaYear);
      this.capaYear = capaYear;
   }
   void capaYearValidate(java.lang.String capaYear) throws wt.util.WTPropertyVetoException {
      if (CAPA_YEAR_UPPER_LIMIT < 1) {
         try { CAPA_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("capaYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAPA_YEAR_UPPER_LIMIT = 200; }
      }
      if (capaYear != null && !wt.fc.PersistenceHelper.checkStoredLength(capaYear.toString(), CAPA_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "capaYear"), java.lang.String.valueOf(java.lang.Math.min(CAPA_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "capaYear", this.capaYear, capaYear));
   }

   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String MFT_FACTORY1 = "mftFactory1";
   static int MFT_FACTORY1_UPPER_LIMIT = -1;
   java.lang.String mftFactory1;
   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getMftFactory1() {
      return mftFactory1;
   }
   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setMftFactory1(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      mftFactory1Validate(mftFactory1);
      this.mftFactory1 = mftFactory1;
   }
   void mftFactory1Validate(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY1_UPPER_LIMIT < 1) {
         try { MFT_FACTORY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY1_UPPER_LIMIT = 200; }
      }
      if (mftFactory1 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory1.toString(), MFT_FACTORY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory1"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory1", this.mftFactory1, mftFactory1));
   }

   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String MFT_FACTORY2 = "mftFactory2";
   static int MFT_FACTORY2_UPPER_LIMIT = -1;
   java.lang.String mftFactory2;
   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getMftFactory2() {
      return mftFactory2;
   }
   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setMftFactory2(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      mftFactory2Validate(mftFactory2);
      this.mftFactory2 = mftFactory2;
   }
   void mftFactory2Validate(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY2_UPPER_LIMIT < 1) {
         try { MFT_FACTORY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY2_UPPER_LIMIT = 200; }
      }
      if (mftFactory2 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory2.toString(), MFT_FACTORY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory2"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory2", this.mftFactory2, mftFactory2));
   }

   /**
    * capa 설정(감가비)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String CAPA_CFG = "capaCfg";
   boolean capaCfg = false;
   /**
    * capa 설정(감가비)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public boolean isCapaCfg() {
      return capaCfg;
   }
   /**
    * capa 설정(감가비)
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setCapaCfg(boolean capaCfg) throws wt.util.WTPropertyVetoException {
      capaCfgValidate(capaCfg);
      this.capaCfg = capaCfg;
   }
   void capaCfgValidate(boolean capaCfg) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 원가비율분석용 코드
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String COST_RATIO_CODE = "costRatioCode";
   static int COST_RATIO_CODE_UPPER_LIMIT = -1;
   java.lang.String costRatioCode;
   /**
    * 원가비율분석용 코드
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.String getCostRatioCode() {
      return costRatioCode;
   }
   /**
    * 원가비율분석용 코드
    *
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setCostRatioCode(java.lang.String costRatioCode) throws wt.util.WTPropertyVetoException {
      costRatioCodeValidate(costRatioCode);
      this.costRatioCode = costRatioCode;
   }
   void costRatioCodeValidate(java.lang.String costRatioCode) throws wt.util.WTPropertyVetoException {
      if (COST_RATIO_CODE_UPPER_LIMIT < 1) {
         try { COST_RATIO_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costRatioCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_RATIO_CODE_UPPER_LIMIT = 200; }
      }
      if (costRatioCode != null && !wt.fc.PersistenceHelper.checkStoredLength(costRatioCode.toString(), COST_RATIO_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costRatioCode"), java.lang.String.valueOf(java.lang.Math.min(COST_RATIO_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costRatioCode", this.costRatioCode, costRatioCode));
   }

   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public static final java.lang.String SORT_LOCATION = "sortLocation";
   java.lang.Integer sortLocation;
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public java.lang.Integer getSortLocation() {
      return sortLocation;
   }
   /**
    * @see ext.ket.cost.entity.CostPartType
    */
   public void setSortLocation(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
      sortLocationValidate(sortLocation);
      this.sortLocation = sortLocation;
   }
   void sortLocationValidate(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -8968449486058728829L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeBoolean( capaCfg );
      output.writeObject( capaYear );
      output.writeObject( childCfg );
      output.writeObject( code );
      output.writeObject( costRatioCode );
      output.writeObject( lvl );
      output.writeObject( lvlOption );
      output.writeObject( mftFactory1 );
      output.writeObject( mftFactory2 );
      output.writeObject( name );
      output.writeObject( parentCfg );
      output.writeObject( parentReference );
      output.writeObject( sortLocation );
      output.writeObject( taskCode );
      output.writeObject( thePersistInfo );
      output.writeObject( workDay );
      output.writeObject( workHour );
      output.writeObject( workYear );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostPartType) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setBoolean( "capaCfg", capaCfg );
      output.setString( "capaYear", capaYear );
      output.setString( "childCfg", childCfg );
      output.setString( "code", code );
      output.setString( "costRatioCode", costRatioCode );
      output.setString( "lvl", lvl );
      output.setString( "lvlOption", lvlOption );
      output.setString( "mftFactory1", mftFactory1 );
      output.setString( "mftFactory2", mftFactory2 );
      output.setString( "name", name );
      output.setString( "parentCfg", parentCfg );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setIntObject( "sortLocation", sortLocation );
      output.setString( "taskCode", taskCode );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "workDay", workDay );
      output.setString( "workHour", workHour );
      output.setString( "workYear", workYear );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      capaCfg = input.getBoolean( "capaCfg" );
      capaYear = input.getString( "capaYear" );
      childCfg = input.getString( "childCfg" );
      code = input.getString( "code" );
      costRatioCode = input.getString( "costRatioCode" );
      lvl = input.getString( "lvl" );
      lvlOption = input.getString( "lvlOption" );
      mftFactory1 = input.getString( "mftFactory1" );
      mftFactory2 = input.getString( "mftFactory2" );
      name = input.getString( "name" );
      parentCfg = input.getString( "parentCfg" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      sortLocation = input.getIntObject( "sortLocation" );
      taskCode = input.getString( "taskCode" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      workDay = input.getString( "workDay" );
      workHour = input.getString( "workHour" );
      workYear = input.getString( "workYear" );
   }

   boolean readVersion_8968449486058728829L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      capaCfg = input.readBoolean();
      capaYear = (java.lang.String) input.readObject();
      childCfg = (java.lang.String) input.readObject();
      code = (java.lang.String) input.readObject();
      costRatioCode = (java.lang.String) input.readObject();
      lvl = (java.lang.String) input.readObject();
      lvlOption = (java.lang.String) input.readObject();
      mftFactory1 = (java.lang.String) input.readObject();
      mftFactory2 = (java.lang.String) input.readObject();
      name = (java.lang.String) input.readObject();
      parentCfg = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      sortLocation = (java.lang.Integer) input.readObject();
      taskCode = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      workDay = (java.lang.String) input.readObject();
      workHour = (java.lang.String) input.readObject();
      workYear = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostPartType thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8968449486058728829L( input, readSerialVersionUID, superDone );
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
