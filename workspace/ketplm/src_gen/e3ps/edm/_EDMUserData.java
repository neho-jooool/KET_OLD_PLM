package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _EDMUserData implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = EDMUserData.class.getName();

   /**
    * 최초 작성자 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String INIT_CREATOR_NO = "initCreatorNo";
   static int INIT_CREATOR_NO_UPPER_LIMIT = -1;
   java.lang.String initCreatorNo;
   /**
    * 최초 작성자 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getInitCreatorNo() {
      return initCreatorNo;
   }
   /**
    * 최초 작성자 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setInitCreatorNo(java.lang.String initCreatorNo) throws wt.util.WTPropertyVetoException {
      initCreatorNoValidate(initCreatorNo);
      this.initCreatorNo = initCreatorNo;
   }
   void initCreatorNoValidate(java.lang.String initCreatorNo) throws wt.util.WTPropertyVetoException {
      if (INIT_CREATOR_NO_UPPER_LIMIT < 1) {
         try { INIT_CREATOR_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("initCreatorNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INIT_CREATOR_NO_UPPER_LIMIT = 200; }
      }
      if (initCreatorNo != null && !wt.fc.PersistenceHelper.checkStoredLength(initCreatorNo.toString(), INIT_CREATOR_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "initCreatorNo"), java.lang.String.valueOf(java.lang.Math.min(INIT_CREATOR_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "initCreatorNo", this.initCreatorNo, initCreatorNo));
   }

   /**
    * 최초 작성자 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String INIT_CREATOR_ID = "initCreatorId";
   static int INIT_CREATOR_ID_UPPER_LIMIT = -1;
   java.lang.String initCreatorId;
   /**
    * 최초 작성자 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getInitCreatorId() {
      return initCreatorId;
   }
   /**
    * 최초 작성자 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setInitCreatorId(java.lang.String initCreatorId) throws wt.util.WTPropertyVetoException {
      initCreatorIdValidate(initCreatorId);
      this.initCreatorId = initCreatorId;
   }
   void initCreatorIdValidate(java.lang.String initCreatorId) throws wt.util.WTPropertyVetoException {
      if (INIT_CREATOR_ID_UPPER_LIMIT < 1) {
         try { INIT_CREATOR_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("initCreatorId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INIT_CREATOR_ID_UPPER_LIMIT = 200; }
      }
      if (initCreatorId != null && !wt.fc.PersistenceHelper.checkStoredLength(initCreatorId.toString(), INIT_CREATOR_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "initCreatorId"), java.lang.String.valueOf(java.lang.Math.min(INIT_CREATOR_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "initCreatorId", this.initCreatorId, initCreatorId));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String INIT_CREATOR_NAME = "initCreatorName";
   static int INIT_CREATOR_NAME_UPPER_LIMIT = -1;
   java.lang.String initCreatorName;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getInitCreatorName() {
      return initCreatorName;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setInitCreatorName(java.lang.String initCreatorName) throws wt.util.WTPropertyVetoException {
      initCreatorNameValidate(initCreatorName);
      this.initCreatorName = initCreatorName;
   }
   void initCreatorNameValidate(java.lang.String initCreatorName) throws wt.util.WTPropertyVetoException {
      if (INIT_CREATOR_NAME_UPPER_LIMIT < 1) {
         try { INIT_CREATOR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("initCreatorName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INIT_CREATOR_NAME_UPPER_LIMIT = 200; }
      }
      if (initCreatorName != null && !wt.fc.PersistenceHelper.checkStoredLength(initCreatorName.toString(), INIT_CREATOR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "initCreatorName"), java.lang.String.valueOf(java.lang.Math.min(INIT_CREATOR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "initCreatorName", this.initCreatorName, initCreatorName));
   }

   /**
    * 최초 작성자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String INIT_DEPT_NAME = "initDeptName";
   static int INIT_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String initDeptName;
   /**
    * 최초 작성자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getInitDeptName() {
      return initDeptName;
   }
   /**
    * 최초 작성자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setInitDeptName(java.lang.String initDeptName) throws wt.util.WTPropertyVetoException {
      initDeptNameValidate(initDeptName);
      this.initDeptName = initDeptName;
   }
   void initDeptNameValidate(java.lang.String initDeptName) throws wt.util.WTPropertyVetoException {
      if (INIT_DEPT_NAME_UPPER_LIMIT < 1) {
         try { INIT_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("initDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INIT_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (initDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(initDeptName.toString(), INIT_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "initDeptName"), java.lang.String.valueOf(java.lang.Math.min(INIT_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "initDeptName", this.initDeptName, initDeptName));
   }

   /**
    * 최초 작성자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String INIT_DEPT_EN_NAME = "initDeptEnName";
   static int INIT_DEPT_EN_NAME_UPPER_LIMIT = -1;
   java.lang.String initDeptEnName;
   /**
    * 최초 작성자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getInitDeptEnName() {
      return initDeptEnName;
   }
   /**
    * 최초 작성자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setInitDeptEnName(java.lang.String initDeptEnName) throws wt.util.WTPropertyVetoException {
      initDeptEnNameValidate(initDeptEnName);
      this.initDeptEnName = initDeptEnName;
   }
   void initDeptEnNameValidate(java.lang.String initDeptEnName) throws wt.util.WTPropertyVetoException {
      if (INIT_DEPT_EN_NAME_UPPER_LIMIT < 1) {
         try { INIT_DEPT_EN_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("initDeptEnName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INIT_DEPT_EN_NAME_UPPER_LIMIT = 200; }
      }
      if (initDeptEnName != null && !wt.fc.PersistenceHelper.checkStoredLength(initDeptEnName.toString(), INIT_DEPT_EN_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "initDeptEnName"), java.lang.String.valueOf(java.lang.Math.min(INIT_DEPT_EN_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "initDeptEnName", this.initDeptEnName, initDeptEnName));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String INIT_CREATE_TIME = "initCreateTime";
   java.sql.Timestamp initCreateTime;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.sql.Timestamp getInitCreateTime() {
      return initCreateTime;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setInitCreateTime(java.sql.Timestamp initCreateTime) throws wt.util.WTPropertyVetoException {
      initCreateTimeValidate(initCreateTime);
      this.initCreateTime = initCreateTime;
   }
   void initCreateTimeValidate(java.sql.Timestamp initCreateTime) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 버전별 작성자 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String CREATOR_NO = "creatorNo";
   static int CREATOR_NO_UPPER_LIMIT = -1;
   java.lang.String creatorNo;
   /**
    * 버전별 작성자 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getCreatorNo() {
      return creatorNo;
   }
   /**
    * 버전별 작성자 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setCreatorNo(java.lang.String creatorNo) throws wt.util.WTPropertyVetoException {
      creatorNoValidate(creatorNo);
      this.creatorNo = creatorNo;
   }
   void creatorNoValidate(java.lang.String creatorNo) throws wt.util.WTPropertyVetoException {
      if (CREATOR_NO_UPPER_LIMIT < 1) {
         try { CREATOR_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_NO_UPPER_LIMIT = 200; }
      }
      if (creatorNo != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorNo.toString(), CREATOR_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorNo"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorNo", this.creatorNo, creatorNo));
   }

   /**
    * 버전별 작성자 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String CREATOR_ID = "creatorId";
   static int CREATOR_ID_UPPER_LIMIT = -1;
   java.lang.String creatorId;
   /**
    * 버전별 작성자 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getCreatorId() {
      return creatorId;
   }
   /**
    * 버전별 작성자 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setCreatorId(java.lang.String creatorId) throws wt.util.WTPropertyVetoException {
      creatorIdValidate(creatorId);
      this.creatorId = creatorId;
   }
   void creatorIdValidate(java.lang.String creatorId) throws wt.util.WTPropertyVetoException {
      if (CREATOR_ID_UPPER_LIMIT < 1) {
         try { CREATOR_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_ID_UPPER_LIMIT = 200; }
      }
      if (creatorId != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorId.toString(), CREATOR_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorId"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorId", this.creatorId, creatorId));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String CREATOR_NAME = "creatorName";
   static int CREATOR_NAME_UPPER_LIMIT = -1;
   java.lang.String creatorName;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getCreatorName() {
      return creatorName;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setCreatorName(java.lang.String creatorName) throws wt.util.WTPropertyVetoException {
      creatorNameValidate(creatorName);
      this.creatorName = creatorName;
   }
   void creatorNameValidate(java.lang.String creatorName) throws wt.util.WTPropertyVetoException {
      if (CREATOR_NAME_UPPER_LIMIT < 1) {
         try { CREATOR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_NAME_UPPER_LIMIT = 200; }
      }
      if (creatorName != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorName.toString(), CREATOR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorName"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorName", this.creatorName, creatorName));
   }

   /**
    * 버전별 작성자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String CREATOR_DEPT_NAME = "creatorDeptName";
   static int CREATOR_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String creatorDeptName;
   /**
    * 버전별 작성자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getCreatorDeptName() {
      return creatorDeptName;
   }
   /**
    * 버전별 작성자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setCreatorDeptName(java.lang.String creatorDeptName) throws wt.util.WTPropertyVetoException {
      creatorDeptNameValidate(creatorDeptName);
      this.creatorDeptName = creatorDeptName;
   }
   void creatorDeptNameValidate(java.lang.String creatorDeptName) throws wt.util.WTPropertyVetoException {
      if (CREATOR_DEPT_NAME_UPPER_LIMIT < 1) {
         try { CREATOR_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (creatorDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorDeptName.toString(), CREATOR_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorDeptName"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorDeptName", this.creatorDeptName, creatorDeptName));
   }

   /**
    * 버전별 작성자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String CREATOR_DEPT_EN_NAME = "creatorDeptEnName";
   static int CREATOR_DEPT_EN_NAME_UPPER_LIMIT = -1;
   java.lang.String creatorDeptEnName;
   /**
    * 버전별 작성자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getCreatorDeptEnName() {
      return creatorDeptEnName;
   }
   /**
    * 버전별 작성자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setCreatorDeptEnName(java.lang.String creatorDeptEnName) throws wt.util.WTPropertyVetoException {
      creatorDeptEnNameValidate(creatorDeptEnName);
      this.creatorDeptEnName = creatorDeptEnName;
   }
   void creatorDeptEnNameValidate(java.lang.String creatorDeptEnName) throws wt.util.WTPropertyVetoException {
      if (CREATOR_DEPT_EN_NAME_UPPER_LIMIT < 1) {
         try { CREATOR_DEPT_EN_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorDeptEnName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_DEPT_EN_NAME_UPPER_LIMIT = 200; }
      }
      if (creatorDeptEnName != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorDeptEnName.toString(), CREATOR_DEPT_EN_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorDeptEnName"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_DEPT_EN_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorDeptEnName", this.creatorDeptEnName, creatorDeptEnName));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String CREATE_TIME = "createTime";
   java.sql.Timestamp createTime;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.sql.Timestamp getCreateTime() {
      return createTime;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setCreateTime(java.sql.Timestamp createTime) throws wt.util.WTPropertyVetoException {
      createTimeValidate(createTime);
      this.createTime = createTime;
   }
   void createTimeValidate(java.sql.Timestamp createTime) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 버전별 수정자의 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String MODIFIER_NO = "modifierNo";
   static int MODIFIER_NO_UPPER_LIMIT = -1;
   java.lang.String modifierNo;
   /**
    * 버전별 수정자의 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getModifierNo() {
      return modifierNo;
   }
   /**
    * 버전별 수정자의 사번
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setModifierNo(java.lang.String modifierNo) throws wt.util.WTPropertyVetoException {
      modifierNoValidate(modifierNo);
      this.modifierNo = modifierNo;
   }
   void modifierNoValidate(java.lang.String modifierNo) throws wt.util.WTPropertyVetoException {
      if (MODIFIER_NO_UPPER_LIMIT < 1) {
         try { MODIFIER_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifierNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFIER_NO_UPPER_LIMIT = 200; }
      }
      if (modifierNo != null && !wt.fc.PersistenceHelper.checkStoredLength(modifierNo.toString(), MODIFIER_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifierNo"), java.lang.String.valueOf(java.lang.Math.min(MODIFIER_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifierNo", this.modifierNo, modifierNo));
   }

   /**
    * 버전별 수정자의 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String MODIFIER_ID = "modifierId";
   static int MODIFIER_ID_UPPER_LIMIT = -1;
   java.lang.String modifierId;
   /**
    * 버전별 수정자의 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getModifierId() {
      return modifierId;
   }
   /**
    * 버전별 수정자의 ID
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setModifierId(java.lang.String modifierId) throws wt.util.WTPropertyVetoException {
      modifierIdValidate(modifierId);
      this.modifierId = modifierId;
   }
   void modifierIdValidate(java.lang.String modifierId) throws wt.util.WTPropertyVetoException {
      if (MODIFIER_ID_UPPER_LIMIT < 1) {
         try { MODIFIER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifierId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFIER_ID_UPPER_LIMIT = 200; }
      }
      if (modifierId != null && !wt.fc.PersistenceHelper.checkStoredLength(modifierId.toString(), MODIFIER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifierId"), java.lang.String.valueOf(java.lang.Math.min(MODIFIER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifierId", this.modifierId, modifierId));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String MODIFIER_NAME = "modifierName";
   static int MODIFIER_NAME_UPPER_LIMIT = -1;
   java.lang.String modifierName;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getModifierName() {
      return modifierName;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setModifierName(java.lang.String modifierName) throws wt.util.WTPropertyVetoException {
      modifierNameValidate(modifierName);
      this.modifierName = modifierName;
   }
   void modifierNameValidate(java.lang.String modifierName) throws wt.util.WTPropertyVetoException {
      if (MODIFIER_NAME_UPPER_LIMIT < 1) {
         try { MODIFIER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifierName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFIER_NAME_UPPER_LIMIT = 200; }
      }
      if (modifierName != null && !wt.fc.PersistenceHelper.checkStoredLength(modifierName.toString(), MODIFIER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifierName"), java.lang.String.valueOf(java.lang.Math.min(MODIFIER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifierName", this.modifierName, modifierName));
   }

   /**
    * 버전별 수정자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String MODIFIER_DEPT_NAME = "modifierDeptName";
   static int MODIFIER_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String modifierDeptName;
   /**
    * 버전별 수정자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getModifierDeptName() {
      return modifierDeptName;
   }
   /**
    * 버전별 수정자의 소속 부서(작성 부서)
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setModifierDeptName(java.lang.String modifierDeptName) throws wt.util.WTPropertyVetoException {
      modifierDeptNameValidate(modifierDeptName);
      this.modifierDeptName = modifierDeptName;
   }
   void modifierDeptNameValidate(java.lang.String modifierDeptName) throws wt.util.WTPropertyVetoException {
      if (MODIFIER_DEPT_NAME_UPPER_LIMIT < 1) {
         try { MODIFIER_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifierDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFIER_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (modifierDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(modifierDeptName.toString(), MODIFIER_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifierDeptName"), java.lang.String.valueOf(java.lang.Math.min(MODIFIER_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifierDeptName", this.modifierDeptName, modifierDeptName));
   }

   /**
    * 버전별 수정자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String MODIFIER_DEPT_EN_NAME = "modifierDeptEnName";
   static int MODIFIER_DEPT_EN_NAME_UPPER_LIMIT = -1;
   java.lang.String modifierDeptEnName;
   /**
    * 버전별 수정자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getModifierDeptEnName() {
      return modifierDeptEnName;
   }
   /**
    * 버전별 수정자의 소속 부서(작성 부서 영문명)
    *
    * @see e3ps.edm.EDMUserData
    */
   public void setModifierDeptEnName(java.lang.String modifierDeptEnName) throws wt.util.WTPropertyVetoException {
      modifierDeptEnNameValidate(modifierDeptEnName);
      this.modifierDeptEnName = modifierDeptEnName;
   }
   void modifierDeptEnNameValidate(java.lang.String modifierDeptEnName) throws wt.util.WTPropertyVetoException {
      if (MODIFIER_DEPT_EN_NAME_UPPER_LIMIT < 1) {
         try { MODIFIER_DEPT_EN_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifierDeptEnName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFIER_DEPT_EN_NAME_UPPER_LIMIT = 200; }
      }
      if (modifierDeptEnName != null && !wt.fc.PersistenceHelper.checkStoredLength(modifierDeptEnName.toString(), MODIFIER_DEPT_EN_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifierDeptEnName"), java.lang.String.valueOf(java.lang.Math.min(MODIFIER_DEPT_EN_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifierDeptEnName", this.modifierDeptEnName, modifierDeptEnName));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String MODIFY_TIME = "modifyTime";
   java.sql.Timestamp modifyTime;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.sql.Timestamp getModifyTime() {
      return modifyTime;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setModifyTime(java.sql.Timestamp modifyTime) throws wt.util.WTPropertyVetoException {
      modifyTimeValidate(modifyTime);
      this.modifyTime = modifyTime;
   }
   void modifyTimeValidate(java.sql.Timestamp modifyTime) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String OBJ_NUMBER = "objNumber";
   static int OBJ_NUMBER_UPPER_LIMIT = -1;
   java.lang.String objNumber;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getObjNumber() {
      return objNumber;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setObjNumber(java.lang.String objNumber) throws wt.util.WTPropertyVetoException {
      objNumberValidate(objNumber);
      this.objNumber = objNumber;
   }
   void objNumberValidate(java.lang.String objNumber) throws wt.util.WTPropertyVetoException {
      if (OBJ_NUMBER_UPPER_LIMIT < 1) {
         try { OBJ_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("objNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OBJ_NUMBER_UPPER_LIMIT = 200; }
      }
      if (objNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(objNumber.toString(), OBJ_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "objNumber"), java.lang.String.valueOf(java.lang.Math.min(OBJ_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "objNumber", this.objNumber, objNumber));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String OBJ_VERSION = "objVersion";
   static int OBJ_VERSION_UPPER_LIMIT = -1;
   java.lang.String objVersion;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getObjVersion() {
      return objVersion;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setObjVersion(java.lang.String objVersion) throws wt.util.WTPropertyVetoException {
      objVersionValidate(objVersion);
      this.objVersion = objVersion;
   }
   void objVersionValidate(java.lang.String objVersion) throws wt.util.WTPropertyVetoException {
      if (OBJ_VERSION_UPPER_LIMIT < 1) {
         try { OBJ_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("objVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OBJ_VERSION_UPPER_LIMIT = 200; }
      }
      if (objVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(objVersion.toString(), OBJ_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "objVersion"), java.lang.String.valueOf(java.lang.Math.min(OBJ_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "objVersion", this.objVersion, objVersion));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String OBJ_CLASSNAME = "objClassname";
   static int OBJ_CLASSNAME_UPPER_LIMIT = -1;
   java.lang.String objClassname;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public java.lang.String getObjClassname() {
      return objClassname;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setObjClassname(java.lang.String objClassname) throws wt.util.WTPropertyVetoException {
      objClassnameValidate(objClassname);
      this.objClassname = objClassname;
   }
   void objClassnameValidate(java.lang.String objClassname) throws wt.util.WTPropertyVetoException {
      if (OBJ_CLASSNAME_UPPER_LIMIT < 1) {
         try { OBJ_CLASSNAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("objClassname").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OBJ_CLASSNAME_UPPER_LIMIT = 200; }
      }
      if (objClassname != null && !wt.fc.PersistenceHelper.checkStoredLength(objClassname.toString(), OBJ_CLASSNAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "objClassname"), java.lang.String.valueOf(java.lang.Math.min(OBJ_CLASSNAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "objClassname", this.objClassname, objClassname));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String OBJ_BRANCH_ID = "objBranchId";
   long objBranchId;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public long getObjBranchId() {
      return objBranchId;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setObjBranchId(long objBranchId) throws wt.util.WTPropertyVetoException {
      objBranchIdValidate(objBranchId);
      this.objBranchId = objBranchId;
   }
   void objBranchIdValidate(long objBranchId) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String PRE_VERSION_USER_DATA = "preVersionUserData";
   wt.fc.ObjectReference preVersionUserData;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public wt.fc.ObjectReference getPreVersionUserData() {
      return preVersionUserData;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setPreVersionUserData(wt.fc.ObjectReference preVersionUserData) throws wt.util.WTPropertyVetoException {
      preVersionUserDataValidate(preVersionUserData);
      this.preVersionUserData = preVersionUserData;
   }
   void preVersionUserDataValidate(wt.fc.ObjectReference preVersionUserData) throws wt.util.WTPropertyVetoException {
      if (preVersionUserData == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "preVersionUserData") },
               new java.beans.PropertyChangeEvent(this, "preVersionUserData", this.preVersionUserData, preVersionUserData));
   }

   /**
    * @see e3ps.edm.EDMUserData
    */
   public static final java.lang.String OBJ_DATA = "objData";
   wt.fc.ObjectReference objData;
   /**
    * @see e3ps.edm.EDMUserData
    */
   public wt.fc.ObjectReference getObjData() {
      return objData;
   }
   /**
    * @see e3ps.edm.EDMUserData
    */
   public void setObjData(wt.fc.ObjectReference objData) throws wt.util.WTPropertyVetoException {
      objDataValidate(objData);
      this.objData = objData;
   }
   void objDataValidate(wt.fc.ObjectReference objData) throws wt.util.WTPropertyVetoException {
      if (objData == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "objData") },
               new java.beans.PropertyChangeEvent(this, "objData", this.objData, objData));
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

   public static final long EXTERNALIZATION_VERSION_UID = -3468464260259267952L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( createTime );
      output.writeObject( creatorDeptEnName );
      output.writeObject( creatorDeptName );
      output.writeObject( creatorId );
      output.writeObject( creatorName );
      output.writeObject( creatorNo );
      output.writeObject( initCreateTime );
      output.writeObject( initCreatorId );
      output.writeObject( initCreatorName );
      output.writeObject( initCreatorNo );
      output.writeObject( initDeptEnName );
      output.writeObject( initDeptName );
      output.writeObject( modifierDeptEnName );
      output.writeObject( modifierDeptName );
      output.writeObject( modifierId );
      output.writeObject( modifierName );
      output.writeObject( modifierNo );
      output.writeObject( modifyTime );
      output.writeLong( objBranchId );
      output.writeObject( objClassname );
      output.writeObject( objData );
      output.writeObject( objNumber );
      output.writeObject( objVersion );
      output.writeObject( preVersionUserData );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.edm.EDMUserData) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "createTime", createTime );
      output.setString( "creatorDeptEnName", creatorDeptEnName );
      output.setString( "creatorDeptName", creatorDeptName );
      output.setString( "creatorId", creatorId );
      output.setString( "creatorName", creatorName );
      output.setString( "creatorNo", creatorNo );
      output.setTimestamp( "initCreateTime", initCreateTime );
      output.setString( "initCreatorId", initCreatorId );
      output.setString( "initCreatorName", initCreatorName );
      output.setString( "initCreatorNo", initCreatorNo );
      output.setString( "initDeptEnName", initDeptEnName );
      output.setString( "initDeptName", initDeptName );
      output.setString( "modifierDeptEnName", modifierDeptEnName );
      output.setString( "modifierDeptName", modifierDeptName );
      output.setString( "modifierId", modifierId );
      output.setString( "modifierName", modifierName );
      output.setString( "modifierNo", modifierNo );
      output.setTimestamp( "modifyTime", modifyTime );
      output.setLong( "objBranchId", objBranchId );
      output.setString( "objClassname", objClassname );
      output.writeObject( "objData", objData, wt.fc.ObjectReference.class, true );
      output.setString( "objNumber", objNumber );
      output.setString( "objVersion", objVersion );
      output.writeObject( "preVersionUserData", preVersionUserData, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      createTime = input.getTimestamp( "createTime" );
      creatorDeptEnName = input.getString( "creatorDeptEnName" );
      creatorDeptName = input.getString( "creatorDeptName" );
      creatorId = input.getString( "creatorId" );
      creatorName = input.getString( "creatorName" );
      creatorNo = input.getString( "creatorNo" );
      initCreateTime = input.getTimestamp( "initCreateTime" );
      initCreatorId = input.getString( "initCreatorId" );
      initCreatorName = input.getString( "initCreatorName" );
      initCreatorNo = input.getString( "initCreatorNo" );
      initDeptEnName = input.getString( "initDeptEnName" );
      initDeptName = input.getString( "initDeptName" );
      modifierDeptEnName = input.getString( "modifierDeptEnName" );
      modifierDeptName = input.getString( "modifierDeptName" );
      modifierId = input.getString( "modifierId" );
      modifierName = input.getString( "modifierName" );
      modifierNo = input.getString( "modifierNo" );
      modifyTime = input.getTimestamp( "modifyTime" );
      objBranchId = input.getLong( "objBranchId" );
      objClassname = input.getString( "objClassname" );
      objData = (wt.fc.ObjectReference) input.readObject( "objData", objData, wt.fc.ObjectReference.class, true );
      objNumber = input.getString( "objNumber" );
      objVersion = input.getString( "objVersion" );
      preVersionUserData = (wt.fc.ObjectReference) input.readObject( "preVersionUserData", preVersionUserData, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_3468464260259267952L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      createTime = (java.sql.Timestamp) input.readObject();
      creatorDeptEnName = (java.lang.String) input.readObject();
      creatorDeptName = (java.lang.String) input.readObject();
      creatorId = (java.lang.String) input.readObject();
      creatorName = (java.lang.String) input.readObject();
      creatorNo = (java.lang.String) input.readObject();
      initCreateTime = (java.sql.Timestamp) input.readObject();
      initCreatorId = (java.lang.String) input.readObject();
      initCreatorName = (java.lang.String) input.readObject();
      initCreatorNo = (java.lang.String) input.readObject();
      initDeptEnName = (java.lang.String) input.readObject();
      initDeptName = (java.lang.String) input.readObject();
      modifierDeptEnName = (java.lang.String) input.readObject();
      modifierDeptName = (java.lang.String) input.readObject();
      modifierId = (java.lang.String) input.readObject();
      modifierName = (java.lang.String) input.readObject();
      modifierNo = (java.lang.String) input.readObject();
      modifyTime = (java.sql.Timestamp) input.readObject();
      objBranchId = input.readLong();
      objClassname = (java.lang.String) input.readObject();
      objData = (wt.fc.ObjectReference) input.readObject();
      objNumber = (java.lang.String) input.readObject();
      objVersion = (java.lang.String) input.readObject();
      preVersionUserData = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( EDMUserData thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3468464260259267952L( input, readSerialVersionUID, superDone );
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
