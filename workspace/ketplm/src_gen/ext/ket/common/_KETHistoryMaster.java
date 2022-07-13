package ext.ket.common;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETHistoryMaster implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.common.commonResource";
   static final java.lang.String CLASSNAME = KETHistoryMaster.class.getName();

   /**
    * 이력유형
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String HISTORY_TYPE = "historyType";
   static int HISTORY_TYPE_UPPER_LIMIT = -1;
   java.lang.String historyType;
   /**
    * 이력유형
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getHistoryType() {
      return historyType;
   }
   /**
    * 이력유형
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setHistoryType(java.lang.String historyType) throws wt.util.WTPropertyVetoException {
      historyTypeValidate(historyType);
      this.historyType = historyType;
   }
   void historyTypeValidate(java.lang.String historyType) throws wt.util.WTPropertyVetoException {
      if (HISTORY_TYPE_UPPER_LIMIT < 1) {
         try { HISTORY_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("historyType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HISTORY_TYPE_UPPER_LIMIT = 200; }
      }
      if (historyType != null && !wt.fc.PersistenceHelper.checkStoredLength(historyType.toString(), HISTORY_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "historyType"), java.lang.String.valueOf(java.lang.Math.min(HISTORY_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "historyType", this.historyType, historyType));
   }

   /**
    * 대상자아이디
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String TARGET_ID = "targetId";
   static int TARGET_ID_UPPER_LIMIT = -1;
   java.lang.String targetId;
   /**
    * 대상자아이디
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getTargetId() {
      return targetId;
   }
   /**
    * 대상자아이디
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setTargetId(java.lang.String targetId) throws wt.util.WTPropertyVetoException {
      targetIdValidate(targetId);
      this.targetId = targetId;
   }
   void targetIdValidate(java.lang.String targetId) throws wt.util.WTPropertyVetoException {
      if (TARGET_ID_UPPER_LIMIT < 1) {
         try { TARGET_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_ID_UPPER_LIMIT = 200; }
      }
      if (targetId != null && !wt.fc.PersistenceHelper.checkStoredLength(targetId.toString(), TARGET_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetId"), java.lang.String.valueOf(java.lang.Math.min(TARGET_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetId", this.targetId, targetId));
   }

   /**
    * 대상자이름
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String TARGET_NAME = "targetName";
   static int TARGET_NAME_UPPER_LIMIT = -1;
   java.lang.String targetName;
   /**
    * 대상자이름
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getTargetName() {
      return targetName;
   }
   /**
    * 대상자이름
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setTargetName(java.lang.String targetName) throws wt.util.WTPropertyVetoException {
      targetNameValidate(targetName);
      this.targetName = targetName;
   }
   void targetNameValidate(java.lang.String targetName) throws wt.util.WTPropertyVetoException {
      if (TARGET_NAME_UPPER_LIMIT < 1) {
         try { TARGET_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_NAME_UPPER_LIMIT = 200; }
      }
      if (targetName != null && !wt.fc.PersistenceHelper.checkStoredLength(targetName.toString(), TARGET_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetName"), java.lang.String.valueOf(java.lang.Math.min(TARGET_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetName", this.targetName, targetName));
   }

   /**
    * 대상
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String TARGET_CONTENT = "targetContent";
   static int TARGET_CONTENT_UPPER_LIMIT = -1;
   java.lang.String targetContent;
   /**
    * 대상
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getTargetContent() {
      return targetContent;
   }
   /**
    * 대상
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setTargetContent(java.lang.String targetContent) throws wt.util.WTPropertyVetoException {
      targetContentValidate(targetContent);
      this.targetContent = targetContent;
   }
   void targetContentValidate(java.lang.String targetContent) throws wt.util.WTPropertyVetoException {
      if (TARGET_CONTENT_UPPER_LIMIT < 1) {
         try { TARGET_CONTENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetContent").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_CONTENT_UPPER_LIMIT = 4000; }
      }
      if (targetContent != null && !wt.fc.PersistenceHelper.checkStoredLength(targetContent.toString(), TARGET_CONTENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetContent"), java.lang.String.valueOf(java.lang.Math.min(TARGET_CONTENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetContent", this.targetContent, targetContent));
   }

   /**
    * 소속부서코드
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String DEPTCODE = "deptcode";
   static int DEPTCODE_UPPER_LIMIT = -1;
   java.lang.String deptcode;
   /**
    * 소속부서코드
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getDeptcode() {
      return deptcode;
   }
   /**
    * 소속부서코드
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setDeptcode(java.lang.String deptcode) throws wt.util.WTPropertyVetoException {
      deptcodeValidate(deptcode);
      this.deptcode = deptcode;
   }
   void deptcodeValidate(java.lang.String deptcode) throws wt.util.WTPropertyVetoException {
      if (DEPTCODE_UPPER_LIMIT < 1) {
         try { DEPTCODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptcode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPTCODE_UPPER_LIMIT = 200; }
      }
      if (deptcode != null && !wt.fc.PersistenceHelper.checkStoredLength(deptcode.toString(), DEPTCODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptcode"), java.lang.String.valueOf(java.lang.Math.min(DEPTCODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptcode", this.deptcode, deptcode));
   }

   /**
    * 소속부서명
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * 소속부서명
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * 소속부서명
    *
    * @see ext.ket.common.KETHistoryMaster
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
    * 소속부서팀장아이디
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String DEPT_MANAGER_ID = "deptManagerId";
   static int DEPT_MANAGER_ID_UPPER_LIMIT = -1;
   java.lang.String deptManagerId;
   /**
    * 소속부서팀장아이디
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getDeptManagerId() {
      return deptManagerId;
   }
   /**
    * 소속부서팀장아이디
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setDeptManagerId(java.lang.String deptManagerId) throws wt.util.WTPropertyVetoException {
      deptManagerIdValidate(deptManagerId);
      this.deptManagerId = deptManagerId;
   }
   void deptManagerIdValidate(java.lang.String deptManagerId) throws wt.util.WTPropertyVetoException {
      if (DEPT_MANAGER_ID_UPPER_LIMIT < 1) {
         try { DEPT_MANAGER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptManagerId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_MANAGER_ID_UPPER_LIMIT = 200; }
      }
      if (deptManagerId != null && !wt.fc.PersistenceHelper.checkStoredLength(deptManagerId.toString(), DEPT_MANAGER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptManagerId"), java.lang.String.valueOf(java.lang.Math.min(DEPT_MANAGER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptManagerId", this.deptManagerId, deptManagerId));
   }

   /**
    * 소속부서팀장이름
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String DEPT_MANAGER_NAME = "deptManagerName";
   static int DEPT_MANAGER_NAME_UPPER_LIMIT = -1;
   java.lang.String deptManagerName;
   /**
    * 소속부서팀장이름
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.lang.String getDeptManagerName() {
      return deptManagerName;
   }
   /**
    * 소속부서팀장이름
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setDeptManagerName(java.lang.String deptManagerName) throws wt.util.WTPropertyVetoException {
      deptManagerNameValidate(deptManagerName);
      this.deptManagerName = deptManagerName;
   }
   void deptManagerNameValidate(java.lang.String deptManagerName) throws wt.util.WTPropertyVetoException {
      if (DEPT_MANAGER_NAME_UPPER_LIMIT < 1) {
         try { DEPT_MANAGER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptManagerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_MANAGER_NAME_UPPER_LIMIT = 200; }
      }
      if (deptManagerName != null && !wt.fc.PersistenceHelper.checkStoredLength(deptManagerName.toString(), DEPT_MANAGER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptManagerName"), java.lang.String.valueOf(java.lang.Math.min(DEPT_MANAGER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptManagerName", this.deptManagerName, deptManagerName));
   }

   /**
    * 이력일
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public static final java.lang.String HISTORY_DATE = "historyDate";
   java.sql.Timestamp historyDate;
   /**
    * 이력일
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public java.sql.Timestamp getHistoryDate() {
      return historyDate;
   }
   /**
    * 이력일
    *
    * @see ext.ket.common.KETHistoryMaster
    */
   public void setHistoryDate(java.sql.Timestamp historyDate) throws wt.util.WTPropertyVetoException {
      historyDateValidate(historyDate);
      this.historyDate = historyDate;
   }
   void historyDateValidate(java.sql.Timestamp historyDate) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 6140286565131371616L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( deptManagerId );
      output.writeObject( deptManagerName );
      output.writeObject( deptName );
      output.writeObject( deptcode );
      output.writeObject( historyDate );
      output.writeObject( historyType );
      output.writeObject( targetContent );
      output.writeObject( targetId );
      output.writeObject( targetName );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.common.KETHistoryMaster) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "deptManagerId", deptManagerId );
      output.setString( "deptManagerName", deptManagerName );
      output.setString( "deptName", deptName );
      output.setString( "deptcode", deptcode );
      output.setTimestamp( "historyDate", historyDate );
      output.setString( "historyType", historyType );
      output.setString( "targetContent", targetContent );
      output.setString( "targetId", targetId );
      output.setString( "targetName", targetName );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      deptManagerId = input.getString( "deptManagerId" );
      deptManagerName = input.getString( "deptManagerName" );
      deptName = input.getString( "deptName" );
      deptcode = input.getString( "deptcode" );
      historyDate = input.getTimestamp( "historyDate" );
      historyType = input.getString( "historyType" );
      targetContent = input.getString( "targetContent" );
      targetId = input.getString( "targetId" );
      targetName = input.getString( "targetName" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion6140286565131371616L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      deptManagerId = (java.lang.String) input.readObject();
      deptManagerName = (java.lang.String) input.readObject();
      deptName = (java.lang.String) input.readObject();
      deptcode = (java.lang.String) input.readObject();
      historyDate = (java.sql.Timestamp) input.readObject();
      historyType = (java.lang.String) input.readObject();
      targetContent = (java.lang.String) input.readObject();
      targetId = (java.lang.String) input.readObject();
      targetName = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETHistoryMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6140286565131371616L( input, readSerialVersionUID, superDone );
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
