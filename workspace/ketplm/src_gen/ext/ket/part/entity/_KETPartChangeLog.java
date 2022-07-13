package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartChangeLog implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartChangeLog.class.getName();

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String DEPT_KR_NAME = "deptKrName";
   static int DEPT_KR_NAME_UPPER_LIMIT = -1;
   java.lang.String deptKrName;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getDeptKrName() {
      return deptKrName;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setDeptKrName(java.lang.String deptKrName) throws wt.util.WTPropertyVetoException {
      deptKrNameValidate(deptKrName);
      this.deptKrName = deptKrName;
   }
   void deptKrNameValidate(java.lang.String deptKrName) throws wt.util.WTPropertyVetoException {
      if (DEPT_KR_NAME_UPPER_LIMIT < 1) {
         try { DEPT_KR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptKrName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_KR_NAME_UPPER_LIMIT = 200; }
      }
      if (deptKrName != null && !wt.fc.PersistenceHelper.checkStoredLength(deptKrName.toString(), DEPT_KR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptKrName"), java.lang.String.valueOf(java.lang.Math.min(DEPT_KR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptKrName", this.deptKrName, deptKrName));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String DEPT_CODE = "deptCode";
   static int DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String deptCode;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getDeptCode() {
      return deptCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
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
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String CHANGE_USER_ID = "changeUserId";
   static int CHANGE_USER_ID_UPPER_LIMIT = -1;
   java.lang.String changeUserId;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getChangeUserId() {
      return changeUserId;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setChangeUserId(java.lang.String changeUserId) throws wt.util.WTPropertyVetoException {
      changeUserIdValidate(changeUserId);
      this.changeUserId = changeUserId;
   }
   void changeUserIdValidate(java.lang.String changeUserId) throws wt.util.WTPropertyVetoException {
      if (CHANGE_USER_ID_UPPER_LIMIT < 1) {
         try { CHANGE_USER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeUserId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_USER_ID_UPPER_LIMIT = 200; }
      }
      if (changeUserId != null && !wt.fc.PersistenceHelper.checkStoredLength(changeUserId.toString(), CHANGE_USER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeUserId"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_USER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeUserId", this.changeUserId, changeUserId));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String CHANGE_USER_NAME = "changeUserName";
   static int CHANGE_USER_NAME_UPPER_LIMIT = -1;
   java.lang.String changeUserName;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getChangeUserName() {
      return changeUserName;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setChangeUserName(java.lang.String changeUserName) throws wt.util.WTPropertyVetoException {
      changeUserNameValidate(changeUserName);
      this.changeUserName = changeUserName;
   }
   void changeUserNameValidate(java.lang.String changeUserName) throws wt.util.WTPropertyVetoException {
      if (CHANGE_USER_NAME_UPPER_LIMIT < 1) {
         try { CHANGE_USER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeUserName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_USER_NAME_UPPER_LIMIT = 200; }
      }
      if (changeUserName != null && !wt.fc.PersistenceHelper.checkStoredLength(changeUserName.toString(), CHANGE_USER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeUserName"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_USER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeUserName", this.changeUserName, changeUserName));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_IDA2A2 = "partIda2a2";
   long partIda2a2;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public long getPartIda2a2() {
      return partIda2a2;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setPartIda2a2(long partIda2a2) throws wt.util.WTPropertyVetoException {
      partIda2a2Validate(partIda2a2);
      this.partIda2a2 = partIda2a2;
   }
   void partIda2a2Validate(long partIda2a2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_NUMBER = "partNumber";
   static int PART_NUMBER_UPPER_LIMIT = -1;
   java.lang.String partNumber;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getPartNumber() {
      return partNumber;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setPartNumber(java.lang.String partNumber) throws wt.util.WTPropertyVetoException {
      partNumberValidate(partNumber);
      this.partNumber = partNumber;
   }
   void partNumberValidate(java.lang.String partNumber) throws wt.util.WTPropertyVetoException {
      if (PART_NUMBER_UPPER_LIMIT < 1) {
         try { PART_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NUMBER_UPPER_LIMIT = 200; }
      }
      if (partNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(partNumber.toString(), PART_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNumber"), java.lang.String.valueOf(java.lang.Math.min(PART_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNumber", this.partNumber, partNumber));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
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
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_REVISION = "partRevision";
   static int PART_REVISION_UPPER_LIMIT = -1;
   java.lang.String partRevision;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getPartRevision() {
      return partRevision;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setPartRevision(java.lang.String partRevision) throws wt.util.WTPropertyVetoException {
      partRevisionValidate(partRevision);
      this.partRevision = partRevision;
   }
   void partRevisionValidate(java.lang.String partRevision) throws wt.util.WTPropertyVetoException {
      if (PART_REVISION_UPPER_LIMIT < 1) {
         try { PART_REVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partRevision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_REVISION_UPPER_LIMIT = 200; }
      }
      if (partRevision != null && !wt.fc.PersistenceHelper.checkStoredLength(partRevision.toString(), PART_REVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partRevision"), java.lang.String.valueOf(java.lang.Math.min(PART_REVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partRevision", this.partRevision, partRevision));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_ITERATION = "partIteration";
   static int PART_ITERATION_UPPER_LIMIT = -1;
   java.lang.String partIteration;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getPartIteration() {
      return partIteration;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setPartIteration(java.lang.String partIteration) throws wt.util.WTPropertyVetoException {
      partIterationValidate(partIteration);
      this.partIteration = partIteration;
   }
   void partIterationValidate(java.lang.String partIteration) throws wt.util.WTPropertyVetoException {
      if (PART_ITERATION_UPPER_LIMIT < 1) {
         try { PART_ITERATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partIteration").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_ITERATION_UPPER_LIMIT = 200; }
      }
      if (partIteration != null && !wt.fc.PersistenceHelper.checkStoredLength(partIteration.toString(), PART_ITERATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partIteration"), java.lang.String.valueOf(java.lang.Math.min(PART_ITERATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partIteration", this.partIteration, partIteration));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_PROPERTY_CODE = "partPropertyCode";
   static int PART_PROPERTY_CODE_UPPER_LIMIT = -1;
   java.lang.String partPropertyCode;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getPartPropertyCode() {
      return partPropertyCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setPartPropertyCode(java.lang.String partPropertyCode) throws wt.util.WTPropertyVetoException {
      partPropertyCodeValidate(partPropertyCode);
      this.partPropertyCode = partPropertyCode;
   }
   void partPropertyCodeValidate(java.lang.String partPropertyCode) throws wt.util.WTPropertyVetoException {
      if (PART_PROPERTY_CODE_UPPER_LIMIT < 1) {
         try { PART_PROPERTY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partPropertyCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_PROPERTY_CODE_UPPER_LIMIT = 200; }
      }
      if (partPropertyCode != null && !wt.fc.PersistenceHelper.checkStoredLength(partPropertyCode.toString(), PART_PROPERTY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partPropertyCode"), java.lang.String.valueOf(java.lang.Math.min(PART_PROPERTY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partPropertyCode", this.partPropertyCode, partPropertyCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_PROPERTY_NAME = "partPropertyName";
   static int PART_PROPERTY_NAME_UPPER_LIMIT = -1;
   java.lang.String partPropertyName;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getPartPropertyName() {
      return partPropertyName;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setPartPropertyName(java.lang.String partPropertyName) throws wt.util.WTPropertyVetoException {
      partPropertyNameValidate(partPropertyName);
      this.partPropertyName = partPropertyName;
   }
   void partPropertyNameValidate(java.lang.String partPropertyName) throws wt.util.WTPropertyVetoException {
      if (PART_PROPERTY_NAME_UPPER_LIMIT < 1) {
         try { PART_PROPERTY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partPropertyName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_PROPERTY_NAME_UPPER_LIMIT = 200; }
      }
      if (partPropertyName != null && !wt.fc.PersistenceHelper.checkStoredLength(partPropertyName.toString(), PART_PROPERTY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partPropertyName"), java.lang.String.valueOf(java.lang.Math.min(PART_PROPERTY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partPropertyName", this.partPropertyName, partPropertyName));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String PART_CHANGE_DATE = "partChangeDate";
   java.sql.Timestamp partChangeDate;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.sql.Timestamp getPartChangeDate() {
      return partChangeDate;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setPartChangeDate(java.sql.Timestamp partChangeDate) throws wt.util.WTPropertyVetoException {
      partChangeDateValidate(partChangeDate);
      this.partChangeDate = partChangeDate;
   }
   void partChangeDateValidate(java.sql.Timestamp partChangeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String BEFORE_VALUE = "beforeValue";
   static int BEFORE_VALUE_UPPER_LIMIT = -1;
   java.lang.String beforeValue;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getBeforeValue() {
      return beforeValue;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setBeforeValue(java.lang.String beforeValue) throws wt.util.WTPropertyVetoException {
      beforeValueValidate(beforeValue);
      this.beforeValue = beforeValue;
   }
   void beforeValueValidate(java.lang.String beforeValue) throws wt.util.WTPropertyVetoException {
      if (BEFORE_VALUE_UPPER_LIMIT < 1) {
         try { BEFORE_VALUE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("beforeValue").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BEFORE_VALUE_UPPER_LIMIT = 1500; }
      }
      if (beforeValue != null && !wt.fc.PersistenceHelper.checkStoredLength(beforeValue.toString(), BEFORE_VALUE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "beforeValue"), java.lang.String.valueOf(java.lang.Math.min(BEFORE_VALUE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "beforeValue", this.beforeValue, beforeValue));
   }

   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public static final java.lang.String AFTER_VALUE = "afterValue";
   static int AFTER_VALUE_UPPER_LIMIT = -1;
   java.lang.String afterValue;
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public java.lang.String getAfterValue() {
      return afterValue;
   }
   /**
    * @see ext.ket.part.entity.KETPartChangeLog
    */
   public void setAfterValue(java.lang.String afterValue) throws wt.util.WTPropertyVetoException {
      afterValueValidate(afterValue);
      this.afterValue = afterValue;
   }
   void afterValueValidate(java.lang.String afterValue) throws wt.util.WTPropertyVetoException {
      if (AFTER_VALUE_UPPER_LIMIT < 1) {
         try { AFTER_VALUE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("afterValue").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AFTER_VALUE_UPPER_LIMIT = 1500; }
      }
      if (afterValue != null && !wt.fc.PersistenceHelper.checkStoredLength(afterValue.toString(), AFTER_VALUE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "afterValue"), java.lang.String.valueOf(java.lang.Math.min(AFTER_VALUE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "afterValue", this.afterValue, afterValue));
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

   public static final long EXTERNALIZATION_VERSION_UID = 550938557780831601L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( afterValue );
      output.writeObject( beforeValue );
      output.writeObject( changeUserId );
      output.writeObject( changeUserName );
      output.writeObject( deptCode );
      output.writeObject( deptKrName );
      output.writeObject( partChangeDate );
      output.writeLong( partIda2a2 );
      output.writeObject( partIteration );
      output.writeObject( partName );
      output.writeObject( partNumber );
      output.writeObject( partPropertyCode );
      output.writeObject( partPropertyName );
      output.writeObject( partRevision );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.KETPartChangeLog) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "afterValue", afterValue );
      output.setString( "beforeValue", beforeValue );
      output.setString( "changeUserId", changeUserId );
      output.setString( "changeUserName", changeUserName );
      output.setString( "deptCode", deptCode );
      output.setString( "deptKrName", deptKrName );
      output.setTimestamp( "partChangeDate", partChangeDate );
      output.setLong( "partIda2a2", partIda2a2 );
      output.setString( "partIteration", partIteration );
      output.setString( "partName", partName );
      output.setString( "partNumber", partNumber );
      output.setString( "partPropertyCode", partPropertyCode );
      output.setString( "partPropertyName", partPropertyName );
      output.setString( "partRevision", partRevision );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      afterValue = input.getString( "afterValue" );
      beforeValue = input.getString( "beforeValue" );
      changeUserId = input.getString( "changeUserId" );
      changeUserName = input.getString( "changeUserName" );
      deptCode = input.getString( "deptCode" );
      deptKrName = input.getString( "deptKrName" );
      partChangeDate = input.getTimestamp( "partChangeDate" );
      partIda2a2 = input.getLong( "partIda2a2" );
      partIteration = input.getString( "partIteration" );
      partName = input.getString( "partName" );
      partNumber = input.getString( "partNumber" );
      partPropertyCode = input.getString( "partPropertyCode" );
      partPropertyName = input.getString( "partPropertyName" );
      partRevision = input.getString( "partRevision" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion550938557780831601L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      afterValue = (java.lang.String) input.readObject();
      beforeValue = (java.lang.String) input.readObject();
      changeUserId = (java.lang.String) input.readObject();
      changeUserName = (java.lang.String) input.readObject();
      deptCode = (java.lang.String) input.readObject();
      deptKrName = (java.lang.String) input.readObject();
      partChangeDate = (java.sql.Timestamp) input.readObject();
      partIda2a2 = input.readLong();
      partIteration = (java.lang.String) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNumber = (java.lang.String) input.readObject();
      partPropertyCode = (java.lang.String) input.readObject();
      partPropertyName = (java.lang.String) input.readObject();
      partRevision = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETPartChangeLog thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion550938557780831601L( input, readSerialVersionUID, superDone );
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
