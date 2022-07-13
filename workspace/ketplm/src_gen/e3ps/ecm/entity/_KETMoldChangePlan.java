package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldChangePlan implements e3ps.common.impl.OwnPersistable, wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldChangePlan.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String SCHEDULE_ID = "scheduleId";
   static int SCHEDULE_ID_UPPER_LIMIT = -1;
   java.lang.String scheduleId;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getScheduleId() {
      return scheduleId;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setScheduleId(java.lang.String scheduleId) throws wt.util.WTPropertyVetoException {
      scheduleIdValidate(scheduleId);
      this.scheduleId = scheduleId;
   }
   void scheduleIdValidate(java.lang.String scheduleId) throws wt.util.WTPropertyVetoException {
      if (SCHEDULE_ID_UPPER_LIMIT < 1) {
         try { SCHEDULE_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scheduleId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCHEDULE_ID_UPPER_LIMIT = 200; }
      }
      if (scheduleId != null && !wt.fc.PersistenceHelper.checkStoredLength(scheduleId.toString(), SCHEDULE_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scheduleId"), java.lang.String.valueOf(java.lang.Math.min(SCHEDULE_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scheduleId", this.scheduleId, scheduleId));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PLAN_TYPE = "planType";
   static int PLAN_TYPE_UPPER_LIMIT = -1;
   java.lang.String planType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getPlanType() {
      return planType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setPlanType(java.lang.String planType) throws wt.util.WTPropertyVetoException {
      planTypeValidate(planType);
      this.planType = planType;
   }
   void planTypeValidate(java.lang.String planType) throws wt.util.WTPropertyVetoException {
      if (PLAN_TYPE_UPPER_LIMIT < 1) {
         try { PLAN_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_TYPE_UPPER_LIMIT = 200; }
      }
      if (planType != null && !wt.fc.PersistenceHelper.checkStoredLength(planType.toString(), PLAN_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planType"), java.lang.String.valueOf(java.lang.Math.min(PLAN_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planType", this.planType, planType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String DIE_NO = "dieNo";
   static int DIE_NO_UPPER_LIMIT = -1;
   java.lang.String dieNo;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getDieNo() {
      return dieNo;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setDieNo(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      dieNoValidate(dieNo);
      this.dieNo = dieNo;
   }
   void dieNoValidate(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      if (DIE_NO_UPPER_LIMIT < 1) {
         try { DIE_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dieNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIE_NO_UPPER_LIMIT = 200; }
      }
      if (dieNo != null && !wt.fc.PersistenceHelper.checkStoredLength(dieNo.toString(), DIE_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dieNo"), java.lang.String.valueOf(java.lang.Math.min(DIE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dieNo", this.dieNo, dieNo));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setPartName(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      partNameValidate(partName);
      this.partName = partName;
   }
   void partNameValidate(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      if (PART_NAME_UPPER_LIMIT < 1) {
         try { PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NAME_UPPER_LIMIT = 200; }
      }
      if (partName != null && !wt.fc.PersistenceHelper.checkStoredLength(partName.toString(), PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partName"), java.lang.String.valueOf(java.lang.Math.min(PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partName", this.partName, partName));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PLAN_DESC = "planDesc";
   static int PLAN_DESC_UPPER_LIMIT = -1;
   java.lang.String planDesc;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getPlanDesc() {
      return planDesc;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setPlanDesc(java.lang.String planDesc) throws wt.util.WTPropertyVetoException {
      planDescValidate(planDesc);
      this.planDesc = planDesc;
   }
   void planDescValidate(java.lang.String planDesc) throws wt.util.WTPropertyVetoException {
      if (PLAN_DESC_UPPER_LIMIT < 1) {
         try { PLAN_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_DESC_UPPER_LIMIT = 200; }
      }
      if (planDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(planDesc.toString(), PLAN_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planDesc"), java.lang.String.valueOf(java.lang.Math.min(PLAN_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planDesc", this.planDesc, planDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PROD_DWG_PLAN_DATE = "prodDwgPlanDate";
   java.sql.Timestamp prodDwgPlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getProdDwgPlanDate() {
      return prodDwgPlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setProdDwgPlanDate(java.sql.Timestamp prodDwgPlanDate) throws wt.util.WTPropertyVetoException {
      prodDwgPlanDateValidate(prodDwgPlanDate);
      this.prodDwgPlanDate = prodDwgPlanDate;
   }
   void prodDwgPlanDateValidate(java.sql.Timestamp prodDwgPlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PROD_DWG_ACTUAL_DATE = "prodDwgActualDate";
   java.sql.Timestamp prodDwgActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getProdDwgActualDate() {
      return prodDwgActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setProdDwgActualDate(java.sql.Timestamp prodDwgActualDate) throws wt.util.WTPropertyVetoException {
      prodDwgActualDateValidate(prodDwgActualDate);
      this.prodDwgActualDate = prodDwgActualDate;
   }
   void prodDwgActualDateValidate(java.sql.Timestamp prodDwgActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MOLD_CHANGE_PLAN_DATE = "moldChangePlanDate";
   java.sql.Timestamp moldChangePlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getMoldChangePlanDate() {
      return moldChangePlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMoldChangePlanDate(java.sql.Timestamp moldChangePlanDate) throws wt.util.WTPropertyVetoException {
      moldChangePlanDateValidate(moldChangePlanDate);
      this.moldChangePlanDate = moldChangePlanDate;
   }
   void moldChangePlanDateValidate(java.sql.Timestamp moldChangePlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MOLD_CHANGE_ACTUAL_DATE = "moldChangeActualDate";
   java.sql.Timestamp moldChangeActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getMoldChangeActualDate() {
      return moldChangeActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMoldChangeActualDate(java.sql.Timestamp moldChangeActualDate) throws wt.util.WTPropertyVetoException {
      moldChangeActualDateValidate(moldChangeActualDate);
      this.moldChangeActualDate = moldChangeActualDate;
   }
   void moldChangeActualDateValidate(java.sql.Timestamp moldChangeActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String WORK_PLAN_DATE = "workPlanDate";
   java.sql.Timestamp workPlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getWorkPlanDate() {
      return workPlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setWorkPlanDate(java.sql.Timestamp workPlanDate) throws wt.util.WTPropertyVetoException {
      workPlanDateValidate(workPlanDate);
      this.workPlanDate = workPlanDate;
   }
   void workPlanDateValidate(java.sql.Timestamp workPlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String WORK_ACTUAL_DATE = "workActualDate";
   java.sql.Timestamp workActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getWorkActualDate() {
      return workActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setWorkActualDate(java.sql.Timestamp workActualDate) throws wt.util.WTPropertyVetoException {
      workActualDateValidate(workActualDate);
      this.workActualDate = workActualDate;
   }
   void workActualDateValidate(java.sql.Timestamp workActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String STORE_PLAN_DATE = "storePlanDate";
   java.sql.Timestamp storePlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getStorePlanDate() {
      return storePlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setStorePlanDate(java.sql.Timestamp storePlanDate) throws wt.util.WTPropertyVetoException {
      storePlanDateValidate(storePlanDate);
      this.storePlanDate = storePlanDate;
   }
   void storePlanDateValidate(java.sql.Timestamp storePlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String STORE_ACTUAL_DATE = "storeActualDate";
   java.sql.Timestamp storeActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getStoreActualDate() {
      return storeActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setStoreActualDate(java.sql.Timestamp storeActualDate) throws wt.util.WTPropertyVetoException {
      storeActualDateValidate(storeActualDate);
      this.storeActualDate = storeActualDate;
   }
   void storeActualDateValidate(java.sql.Timestamp storeActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ASSEMBLE_PLAN_DATE = "assemblePlanDate";
   java.sql.Timestamp assemblePlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getAssemblePlanDate() {
      return assemblePlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setAssemblePlanDate(java.sql.Timestamp assemblePlanDate) throws wt.util.WTPropertyVetoException {
      assemblePlanDateValidate(assemblePlanDate);
      this.assemblePlanDate = assemblePlanDate;
   }
   void assemblePlanDateValidate(java.sql.Timestamp assemblePlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ASSEMBLE_ACTUAL_DATE = "assembleActualDate";
   java.sql.Timestamp assembleActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getAssembleActualDate() {
      return assembleActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setAssembleActualDate(java.sql.Timestamp assembleActualDate) throws wt.util.WTPropertyVetoException {
      assembleActualDateValidate(assembleActualDate);
      this.assembleActualDate = assembleActualDate;
   }
   void assembleActualDateValidate(java.sql.Timestamp assembleActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String TRY_PLAN_DATE = "tryPlanDate";
   java.sql.Timestamp tryPlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getTryPlanDate() {
      return tryPlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setTryPlanDate(java.sql.Timestamp tryPlanDate) throws wt.util.WTPropertyVetoException {
      tryPlanDateValidate(tryPlanDate);
      this.tryPlanDate = tryPlanDate;
   }
   void tryPlanDateValidate(java.sql.Timestamp tryPlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String TRY_ACTUAL_DATE = "tryActualDate";
   java.sql.Timestamp tryActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getTryActualDate() {
      return tryActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setTryActualDate(java.sql.Timestamp tryActualDate) throws wt.util.WTPropertyVetoException {
      tryActualDateValidate(tryActualDate);
      this.tryActualDate = tryActualDate;
   }
   void tryActualDateValidate(java.sql.Timestamp tryActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String TEST_PLAN_DATE = "testPlanDate";
   java.sql.Timestamp testPlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getTestPlanDate() {
      return testPlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setTestPlanDate(java.sql.Timestamp testPlanDate) throws wt.util.WTPropertyVetoException {
      testPlanDateValidate(testPlanDate);
      this.testPlanDate = testPlanDate;
   }
   void testPlanDateValidate(java.sql.Timestamp testPlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String TEST_ACTUAL_DATE = "testActualDate";
   java.sql.Timestamp testActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getTestActualDate() {
      return testActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setTestActualDate(java.sql.Timestamp testActualDate) throws wt.util.WTPropertyVetoException {
      testActualDateValidate(testActualDate);
      this.testActualDate = testActualDate;
   }
   void testActualDateValidate(java.sql.Timestamp testActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String APPROVE_PLAN_DATE = "approvePlanDate";
   java.sql.Timestamp approvePlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getApprovePlanDate() {
      return approvePlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setApprovePlanDate(java.sql.Timestamp approvePlanDate) throws wt.util.WTPropertyVetoException {
      approvePlanDateValidate(approvePlanDate);
      this.approvePlanDate = approvePlanDate;
   }
   void approvePlanDateValidate(java.sql.Timestamp approvePlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String APPROVE_ACTUAL_DATE = "approveActualDate";
   java.sql.Timestamp approveActualDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getApproveActualDate() {
      return approveActualDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setApproveActualDate(java.sql.Timestamp approveActualDate) throws wt.util.WTPropertyVetoException {
      approveActualDateValidate(approveActualDate);
      this.approveActualDate = approveActualDate;
   }
   void approveActualDateValidate(java.sql.Timestamp approveActualDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String SCHEDULE_STATUS = "scheduleStatus";
   static int SCHEDULE_STATUS_UPPER_LIMIT = -1;
   java.lang.String scheduleStatus;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getScheduleStatus() {
      return scheduleStatus;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setScheduleStatus(java.lang.String scheduleStatus) throws wt.util.WTPropertyVetoException {
      scheduleStatusValidate(scheduleStatus);
      this.scheduleStatus = scheduleStatus;
   }
   void scheduleStatusValidate(java.lang.String scheduleStatus) throws wt.util.WTPropertyVetoException {
      if (SCHEDULE_STATUS_UPPER_LIMIT < 1) {
         try { SCHEDULE_STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scheduleStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCHEDULE_STATUS_UPPER_LIMIT = 200; }
      }
      if (scheduleStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(scheduleStatus.toString(), SCHEDULE_STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scheduleStatus"), java.lang.String.valueOf(java.lang.Math.min(SCHEDULE_STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scheduleStatus", this.scheduleStatus, scheduleStatus));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String VENDOR_FLAG = "vendorFlag";
   static int VENDOR_FLAG_UPPER_LIMIT = -1;
   java.lang.String vendorFlag;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getVendorFlag() {
      return vendorFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setVendorFlag(java.lang.String vendorFlag) throws wt.util.WTPropertyVetoException {
      vendorFlagValidate(vendorFlag);
      this.vendorFlag = vendorFlag;
   }
   void vendorFlagValidate(java.lang.String vendorFlag) throws wt.util.WTPropertyVetoException {
      if (VENDOR_FLAG_UPPER_LIMIT < 1) {
         try { VENDOR_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("vendorFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VENDOR_FLAG_UPPER_LIMIT = 200; }
      }
      if (vendorFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(vendorFlag.toString(), VENDOR_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "vendorFlag"), java.lang.String.valueOf(java.lang.Math.min(VENDOR_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "vendorFlag", this.vendorFlag, vendorFlag));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String VENDOR_CODE = "vendorCode";
   static int VENDOR_CODE_UPPER_LIMIT = -1;
   java.lang.String vendorCode;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getVendorCode() {
      return vendorCode;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setVendorCode(java.lang.String vendorCode) throws wt.util.WTPropertyVetoException {
      vendorCodeValidate(vendorCode);
      this.vendorCode = vendorCode;
   }
   void vendorCodeValidate(java.lang.String vendorCode) throws wt.util.WTPropertyVetoException {
      if (VENDOR_CODE_UPPER_LIMIT < 1) {
         try { VENDOR_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("vendorCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VENDOR_CODE_UPPER_LIMIT = 200; }
      }
      if (vendorCode != null && !wt.fc.PersistenceHelper.checkStoredLength(vendorCode.toString(), VENDOR_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "vendorCode"), java.lang.String.valueOf(java.lang.Math.min(VENDOR_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "vendorCode", this.vendorCode, vendorCode));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PROD_DEPT_NAME = "prodDeptName";
   static int PROD_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String prodDeptName;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getProdDeptName() {
      return prodDeptName;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setProdDeptName(java.lang.String prodDeptName) throws wt.util.WTPropertyVetoException {
      prodDeptNameValidate(prodDeptName);
      this.prodDeptName = prodDeptName;
   }
   void prodDeptNameValidate(java.lang.String prodDeptName) throws wt.util.WTPropertyVetoException {
      if (PROD_DEPT_NAME_UPPER_LIMIT < 1) {
         try { PROD_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("prodDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROD_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (prodDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(prodDeptName.toString(), PROD_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodDeptName"), java.lang.String.valueOf(java.lang.Math.min(PROD_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "prodDeptName", this.prodDeptName, prodDeptName));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String REG_BASIS = "regBasis";
   static int REG_BASIS_UPPER_LIMIT = -1;
   java.lang.String regBasis;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getRegBasis() {
      return regBasis;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setRegBasis(java.lang.String regBasis) throws wt.util.WTPropertyVetoException {
      regBasisValidate(regBasis);
      this.regBasis = regBasis;
   }
   void regBasisValidate(java.lang.String regBasis) throws wt.util.WTPropertyVetoException {
      if (REG_BASIS_UPPER_LIMIT < 1) {
         try { REG_BASIS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("regBasis").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REG_BASIS_UPPER_LIMIT = 200; }
      }
      if (regBasis != null && !wt.fc.PersistenceHelper.checkStoredLength(regBasis.toString(), REG_BASIS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "regBasis"), java.lang.String.valueOf(java.lang.Math.min(REG_BASIS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "regBasis", this.regBasis, regBasis));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String BASIS_DATE = "basisDate";
   java.sql.Timestamp basisDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getBasisDate() {
      return basisDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setBasisDate(java.sql.Timestamp basisDate) throws wt.util.WTPropertyVetoException {
      basisDateValidate(basisDate);
      this.basisDate = basisDate;
   }
   void basisDateValidate(java.sql.Timestamp basisDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MODIFY_DESC = "modifyDesc";
   static int MODIFY_DESC_UPPER_LIMIT = -1;
   java.lang.String modifyDesc;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getModifyDesc() {
      return modifyDesc;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setModifyDesc(java.lang.String modifyDesc) throws wt.util.WTPropertyVetoException {
      modifyDescValidate(modifyDesc);
      this.modifyDesc = modifyDesc;
   }
   void modifyDescValidate(java.lang.String modifyDesc) throws wt.util.WTPropertyVetoException {
      if (MODIFY_DESC_UPPER_LIMIT < 1) {
         try { MODIFY_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifyDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFY_DESC_UPPER_LIMIT = 200; }
      }
      if (modifyDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(modifyDesc.toString(), MODIFY_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifyDesc"), java.lang.String.valueOf(java.lang.Math.min(MODIFY_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifyDesc", this.modifyDesc, modifyDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MEASURE_TYPE = "measureType";
   static int MEASURE_TYPE_UPPER_LIMIT = -1;
   java.lang.String measureType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getMeasureType() {
      return measureType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMeasureType(java.lang.String measureType) throws wt.util.WTPropertyVetoException {
      measureTypeValidate(measureType);
      this.measureType = measureType;
   }
   void measureTypeValidate(java.lang.String measureType) throws wt.util.WTPropertyVetoException {
      if (MEASURE_TYPE_UPPER_LIMIT < 1) {
         try { MEASURE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("measureType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEASURE_TYPE_UPPER_LIMIT = 200; }
      }
      if (measureType != null && !wt.fc.PersistenceHelper.checkStoredLength(measureType.toString(), MEASURE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "measureType"), java.lang.String.valueOf(java.lang.Math.min(MEASURE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "measureType", this.measureType, measureType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String FAIL_ACTION = "failAction";
   static int FAIL_ACTION_UPPER_LIMIT = -1;
   java.lang.String failAction;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getFailAction() {
      return failAction;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setFailAction(java.lang.String failAction) throws wt.util.WTPropertyVetoException {
      failActionValidate(failAction);
      this.failAction = failAction;
   }
   void failActionValidate(java.lang.String failAction) throws wt.util.WTPropertyVetoException {
      if (FAIL_ACTION_UPPER_LIMIT < 1) {
         try { FAIL_ACTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("failAction").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAIL_ACTION_UPPER_LIMIT = 200; }
      }
      if (failAction != null && !wt.fc.PersistenceHelper.checkStoredLength(failAction.toString(), FAIL_ACTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "failAction"), java.lang.String.valueOf(java.lang.Math.min(FAIL_ACTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "failAction", this.failAction, failAction));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String RESULT = "result";
   static int RESULT_UPPER_LIMIT = -1;
   java.lang.String result;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getResult() {
      return result;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setResult(java.lang.String result) throws wt.util.WTPropertyVetoException {
      resultValidate(result);
      this.result = result;
   }
   void resultValidate(java.lang.String result) throws wt.util.WTPropertyVetoException {
      if (RESULT_UPPER_LIMIT < 1) {
         try { RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("result").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESULT_UPPER_LIMIT = 200; }
      }
      if (result != null && !wt.fc.PersistenceHelper.checkStoredLength(result.toString(), RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "result"), java.lang.String.valueOf(java.lang.Math.min(RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "result", this.result, result));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MEASURE_DATE = "measureDate";
   java.sql.Timestamp measureDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getMeasureDate() {
      return measureDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMeasureDate(java.sql.Timestamp measureDate) throws wt.util.WTPropertyVetoException {
      measureDateValidate(measureDate);
      this.measureDate = measureDate;
   }
   void measureDateValidate(java.sql.Timestamp measureDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String CURRENT_PROCESS = "currentProcess";
   static int CURRENT_PROCESS_UPPER_LIMIT = -1;
   java.lang.String currentProcess;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getCurrentProcess() {
      return currentProcess;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setCurrentProcess(java.lang.String currentProcess) throws wt.util.WTPropertyVetoException {
      currentProcessValidate(currentProcess);
      this.currentProcess = currentProcess;
   }
   void currentProcessValidate(java.lang.String currentProcess) throws wt.util.WTPropertyVetoException {
      if (CURRENT_PROCESS_UPPER_LIMIT < 1) {
         try { CURRENT_PROCESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("currentProcess").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CURRENT_PROCESS_UPPER_LIMIT = 200; }
      }
      if (currentProcess != null && !wt.fc.PersistenceHelper.checkStoredLength(currentProcess.toString(), CURRENT_PROCESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "currentProcess"), java.lang.String.valueOf(java.lang.Math.min(CURRENT_PROCESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "currentProcess", this.currentProcess, currentProcess));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String CURRENT_PROC_PLAN_DATE = "currentProcPlanDate";
   java.sql.Timestamp currentProcPlanDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.sql.Timestamp getCurrentProcPlanDate() {
      return currentProcPlanDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setCurrentProcPlanDate(java.sql.Timestamp currentProcPlanDate) throws wt.util.WTPropertyVetoException {
      currentProcPlanDateValidate(currentProcPlanDate);
      this.currentProcPlanDate = currentProcPlanDate;
   }
   void currentProcPlanDateValidate(java.sql.Timestamp currentProcPlanDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
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
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MOLD_ECO = "moldECO";
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MOLD_ECOREFERENCE = "moldECOReference";
   wt.fc.ObjectReference moldECOReference;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public e3ps.ecm.entity.KETMoldChangeOrder getMoldECO() {
      return (moldECOReference != null) ? (e3ps.ecm.entity.KETMoldChangeOrder) moldECOReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public wt.fc.ObjectReference getMoldECOReference() {
      return moldECOReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMoldECO(e3ps.ecm.entity.KETMoldChangeOrder the_moldECO) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldECOReference(the_moldECO == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.ecm.entity.KETMoldChangeOrder) the_moldECO));
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMoldECOReference(wt.fc.ObjectReference the_moldECOReference) throws wt.util.WTPropertyVetoException {
      moldECOReferenceValidate(the_moldECOReference);
      moldECOReference = (wt.fc.ObjectReference) the_moldECOReference;
   }
   void moldECOReferenceValidate(wt.fc.ObjectReference the_moldECOReference) throws wt.util.WTPropertyVetoException {
      if (the_moldECOReference == null || the_moldECOReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldECOReference") },
               new java.beans.PropertyChangeEvent(this, "moldECOReference", this.moldECOReference, moldECOReference));
      if (the_moldECOReference != null && the_moldECOReference.getReferencedClass() != null &&
            !e3ps.ecm.entity.KETMoldChangeOrder.class.isAssignableFrom(the_moldECOReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldECOReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldECOReference", this.moldECOReference, moldECOReference));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PROD_ECO = "prodECO";
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PROD_ECOREFERENCE = "prodECOReference";
   wt.fc.ObjectReference prodECOReference;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public e3ps.ecm.entity.KETProdChangeOrder getProdECO() {
      return (prodECOReference != null) ? (e3ps.ecm.entity.KETProdChangeOrder) prodECOReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public wt.fc.ObjectReference getProdECOReference() {
      return prodECOReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setProdECO(e3ps.ecm.entity.KETProdChangeOrder the_prodECO) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProdECOReference(the_prodECO == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.ecm.entity.KETProdChangeOrder) the_prodECO));
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setProdECOReference(wt.fc.ObjectReference the_prodECOReference) throws wt.util.WTPropertyVetoException {
      prodECOReferenceValidate(the_prodECOReference);
      prodECOReference = (wt.fc.ObjectReference) the_prodECOReference;
   }
   void prodECOReferenceValidate(wt.fc.ObjectReference the_prodECOReference) throws wt.util.WTPropertyVetoException {
      if (the_prodECOReference == null || the_prodECOReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodECOReference") },
               new java.beans.PropertyChangeEvent(this, "prodECOReference", this.prodECOReference, prodECOReference));
      if (the_prodECOReference != null && the_prodECOReference.getReferencedClass() != null &&
            !e3ps.ecm.entity.KETProdChangeOrder.class.isAssignableFrom(the_prodECOReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodECOReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "prodECOReference", this.prodECOReference, prodECOReference));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PROD_ECO_OWNER = "prodEcoOwner";
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String PROD_ECO_OWNER_REFERENCE = "prodEcoOwnerReference";
   wt.fc.ObjectReference prodEcoOwnerReference;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public wt.org.WTUser getProdEcoOwner() {
      return (prodEcoOwnerReference != null) ? (wt.org.WTUser) prodEcoOwnerReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public wt.fc.ObjectReference getProdEcoOwnerReference() {
      return prodEcoOwnerReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setProdEcoOwner(wt.org.WTUser the_prodEcoOwner) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProdEcoOwnerReference(the_prodEcoOwner == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_prodEcoOwner));
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setProdEcoOwnerReference(wt.fc.ObjectReference the_prodEcoOwnerReference) throws wt.util.WTPropertyVetoException {
      prodEcoOwnerReferenceValidate(the_prodEcoOwnerReference);
      prodEcoOwnerReference = (wt.fc.ObjectReference) the_prodEcoOwnerReference;
   }
   void prodEcoOwnerReferenceValidate(wt.fc.ObjectReference the_prodEcoOwnerReference) throws wt.util.WTPropertyVetoException {
      if (the_prodEcoOwnerReference != null && the_prodEcoOwnerReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_prodEcoOwnerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodEcoOwnerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "prodEcoOwnerReference", this.prodEcoOwnerReference, prodEcoOwnerReference));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MOLD_ECO_OWNER = "moldEcoOwner";
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public static final java.lang.String MOLD_ECO_OWNER_REFERENCE = "moldEcoOwnerReference";
   wt.fc.ObjectReference moldEcoOwnerReference;
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public wt.org.WTUser getMoldEcoOwner() {
      return (moldEcoOwnerReference != null) ? (wt.org.WTUser) moldEcoOwnerReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public wt.fc.ObjectReference getMoldEcoOwnerReference() {
      return moldEcoOwnerReference;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMoldEcoOwner(wt.org.WTUser the_moldEcoOwner) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldEcoOwnerReference(the_moldEcoOwner == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_moldEcoOwner));
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangePlan
    */
   public void setMoldEcoOwnerReference(wt.fc.ObjectReference the_moldEcoOwnerReference) throws wt.util.WTPropertyVetoException {
      moldEcoOwnerReferenceValidate(the_moldEcoOwnerReference);
      moldEcoOwnerReference = (wt.fc.ObjectReference) the_moldEcoOwnerReference;
   }
   void moldEcoOwnerReferenceValidate(wt.fc.ObjectReference the_moldEcoOwnerReference) throws wt.util.WTPropertyVetoException {
      if (the_moldEcoOwnerReference != null && the_moldEcoOwnerReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_moldEcoOwnerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldEcoOwnerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldEcoOwnerReference", this.moldEcoOwnerReference, moldEcoOwnerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -3128933511068965013L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( approveActualDate );
      output.writeObject( approvePlanDate );
      output.writeObject( assembleActualDate );
      output.writeObject( assemblePlanDate );
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
      output.writeObject( basisDate );
      output.writeObject( currentProcPlanDate );
      output.writeObject( currentProcess );
      output.writeObject( deptName );
      output.writeObject( dieNo );
      output.writeObject( failAction );
      output.writeObject( measureDate );
      output.writeObject( measureType );
      output.writeObject( modifyDesc );
      output.writeObject( moldChangeActualDate );
      output.writeObject( moldChangePlanDate );
      output.writeObject( moldECOReference );
      output.writeObject( moldEcoOwnerReference );
      output.writeObject( owner );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( planDesc );
      output.writeObject( planType );
      output.writeObject( prodDeptName );
      output.writeObject( prodDwgActualDate );
      output.writeObject( prodDwgPlanDate );
      output.writeObject( prodECOReference );
      output.writeObject( prodEcoOwnerReference );
      output.writeObject( regBasis );
      output.writeObject( result );
      output.writeObject( scheduleId );
      output.writeObject( scheduleStatus );
      output.writeObject( storeActualDate );
      output.writeObject( storePlanDate );
      output.writeObject( testActualDate );
      output.writeObject( testPlanDate );
      output.writeObject( thePersistInfo );
      output.writeObject( tryActualDate );
      output.writeObject( tryPlanDate );
      output.writeObject( vendorCode );
      output.writeObject( vendorFlag );
      output.writeObject( workActualDate );
      output.writeObject( workPlanDate );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldChangePlan) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "approveActualDate", approveActualDate );
      output.setTimestamp( "approvePlanDate", approvePlanDate );
      output.setTimestamp( "assembleActualDate", assembleActualDate );
      output.setTimestamp( "assemblePlanDate", assemblePlanDate );
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
      output.setTimestamp( "basisDate", basisDate );
      output.setTimestamp( "currentProcPlanDate", currentProcPlanDate );
      output.setString( "currentProcess", currentProcess );
      output.setString( "deptName", deptName );
      output.setString( "dieNo", dieNo );
      output.setString( "failAction", failAction );
      output.setTimestamp( "measureDate", measureDate );
      output.setString( "measureType", measureType );
      output.setString( "modifyDesc", modifyDesc );
      output.setTimestamp( "moldChangeActualDate", moldChangeActualDate );
      output.setTimestamp( "moldChangePlanDate", moldChangePlanDate );
      output.writeObject( "moldECOReference", moldECOReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "moldEcoOwnerReference", moldEcoOwnerReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "planDesc", planDesc );
      output.setString( "planType", planType );
      output.setString( "prodDeptName", prodDeptName );
      output.setTimestamp( "prodDwgActualDate", prodDwgActualDate );
      output.setTimestamp( "prodDwgPlanDate", prodDwgPlanDate );
      output.writeObject( "prodECOReference", prodECOReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "prodEcoOwnerReference", prodEcoOwnerReference, wt.fc.ObjectReference.class, true );
      output.setString( "regBasis", regBasis );
      output.setString( "result", result );
      output.setString( "scheduleId", scheduleId );
      output.setString( "scheduleStatus", scheduleStatus );
      output.setTimestamp( "storeActualDate", storeActualDate );
      output.setTimestamp( "storePlanDate", storePlanDate );
      output.setTimestamp( "testActualDate", testActualDate );
      output.setTimestamp( "testPlanDate", testPlanDate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setTimestamp( "tryActualDate", tryActualDate );
      output.setTimestamp( "tryPlanDate", tryPlanDate );
      output.setString( "vendorCode", vendorCode );
      output.setString( "vendorFlag", vendorFlag );
      output.setTimestamp( "workActualDate", workActualDate );
      output.setTimestamp( "workPlanDate", workPlanDate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      approveActualDate = input.getTimestamp( "approveActualDate" );
      approvePlanDate = input.getTimestamp( "approvePlanDate" );
      assembleActualDate = input.getTimestamp( "assembleActualDate" );
      assemblePlanDate = input.getTimestamp( "assemblePlanDate" );
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
      basisDate = input.getTimestamp( "basisDate" );
      currentProcPlanDate = input.getTimestamp( "currentProcPlanDate" );
      currentProcess = input.getString( "currentProcess" );
      deptName = input.getString( "deptName" );
      dieNo = input.getString( "dieNo" );
      failAction = input.getString( "failAction" );
      measureDate = input.getTimestamp( "measureDate" );
      measureType = input.getString( "measureType" );
      modifyDesc = input.getString( "modifyDesc" );
      moldChangeActualDate = input.getTimestamp( "moldChangeActualDate" );
      moldChangePlanDate = input.getTimestamp( "moldChangePlanDate" );
      moldECOReference = (wt.fc.ObjectReference) input.readObject( "moldECOReference", moldECOReference, wt.fc.ObjectReference.class, true );
      moldEcoOwnerReference = (wt.fc.ObjectReference) input.readObject( "moldEcoOwnerReference", moldEcoOwnerReference, wt.fc.ObjectReference.class, true );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      planDesc = input.getString( "planDesc" );
      planType = input.getString( "planType" );
      prodDeptName = input.getString( "prodDeptName" );
      prodDwgActualDate = input.getTimestamp( "prodDwgActualDate" );
      prodDwgPlanDate = input.getTimestamp( "prodDwgPlanDate" );
      prodECOReference = (wt.fc.ObjectReference) input.readObject( "prodECOReference", prodECOReference, wt.fc.ObjectReference.class, true );
      prodEcoOwnerReference = (wt.fc.ObjectReference) input.readObject( "prodEcoOwnerReference", prodEcoOwnerReference, wt.fc.ObjectReference.class, true );
      regBasis = input.getString( "regBasis" );
      result = input.getString( "result" );
      scheduleId = input.getString( "scheduleId" );
      scheduleStatus = input.getString( "scheduleStatus" );
      storeActualDate = input.getTimestamp( "storeActualDate" );
      storePlanDate = input.getTimestamp( "storePlanDate" );
      testActualDate = input.getTimestamp( "testActualDate" );
      testPlanDate = input.getTimestamp( "testPlanDate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      tryActualDate = input.getTimestamp( "tryActualDate" );
      tryPlanDate = input.getTimestamp( "tryPlanDate" );
      vendorCode = input.getString( "vendorCode" );
      vendorFlag = input.getString( "vendorFlag" );
      workActualDate = input.getTimestamp( "workActualDate" );
      workPlanDate = input.getTimestamp( "workPlanDate" );
   }

   boolean readVersion_3128933511068965013L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      approveActualDate = (java.sql.Timestamp) input.readObject();
      approvePlanDate = (java.sql.Timestamp) input.readObject();
      assembleActualDate = (java.sql.Timestamp) input.readObject();
      assemblePlanDate = (java.sql.Timestamp) input.readObject();
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
      basisDate = (java.sql.Timestamp) input.readObject();
      currentProcPlanDate = (java.sql.Timestamp) input.readObject();
      currentProcess = (java.lang.String) input.readObject();
      deptName = (java.lang.String) input.readObject();
      dieNo = (java.lang.String) input.readObject();
      failAction = (java.lang.String) input.readObject();
      measureDate = (java.sql.Timestamp) input.readObject();
      measureType = (java.lang.String) input.readObject();
      modifyDesc = (java.lang.String) input.readObject();
      moldChangeActualDate = (java.sql.Timestamp) input.readObject();
      moldChangePlanDate = (java.sql.Timestamp) input.readObject();
      moldECOReference = (wt.fc.ObjectReference) input.readObject();
      moldEcoOwnerReference = (wt.fc.ObjectReference) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      planDesc = (java.lang.String) input.readObject();
      planType = (java.lang.String) input.readObject();
      prodDeptName = (java.lang.String) input.readObject();
      prodDwgActualDate = (java.sql.Timestamp) input.readObject();
      prodDwgPlanDate = (java.sql.Timestamp) input.readObject();
      prodECOReference = (wt.fc.ObjectReference) input.readObject();
      prodEcoOwnerReference = (wt.fc.ObjectReference) input.readObject();
      regBasis = (java.lang.String) input.readObject();
      result = (java.lang.String) input.readObject();
      scheduleId = (java.lang.String) input.readObject();
      scheduleStatus = (java.lang.String) input.readObject();
      storeActualDate = (java.sql.Timestamp) input.readObject();
      storePlanDate = (java.sql.Timestamp) input.readObject();
      testActualDate = (java.sql.Timestamp) input.readObject();
      testPlanDate = (java.sql.Timestamp) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      tryActualDate = (java.sql.Timestamp) input.readObject();
      tryPlanDate = (java.sql.Timestamp) input.readObject();
      vendorCode = (java.lang.String) input.readObject();
      vendorFlag = (java.lang.String) input.readObject();
      workActualDate = (java.sql.Timestamp) input.readObject();
      workPlanDate = (java.sql.Timestamp) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETMoldChangePlan thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3128933511068965013L( input, readSerialVersionUID, superDone );
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
