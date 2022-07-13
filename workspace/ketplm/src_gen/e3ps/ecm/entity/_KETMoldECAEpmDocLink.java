package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldECAEpmDocLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldECAEpmDocLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String CHANGE_TYPE = "changeType";
   static int CHANGE_TYPE_UPPER_LIMIT = -1;
   java.lang.String changeType;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getChangeType() {
      return changeType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setChangeType(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      changeTypeValidate(changeType);
      this.changeType = changeType;
   }
   void changeTypeValidate(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      if (CHANGE_TYPE_UPPER_LIMIT < 1) {
         try { CHANGE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_TYPE_UPPER_LIMIT = 200; }
      }
      if (changeType != null && !wt.fc.PersistenceHelper.checkStoredLength(changeType.toString(), CHANGE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeType"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeType", this.changeType, changeType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String ACTIVITY_COMMENT = "activityComment";
   static int ACTIVITY_COMMENT_UPPER_LIMIT = -1;
   java.lang.String activityComment;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getActivityComment() {
      return activityComment;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setActivityComment(java.lang.String activityComment) throws wt.util.WTPropertyVetoException {
      activityCommentValidate(activityComment);
      this.activityComment = activityComment;
   }
   void activityCommentValidate(java.lang.String activityComment) throws wt.util.WTPropertyVetoException {
      if (ACTIVITY_COMMENT_UPPER_LIMIT < 1) {
         try { ACTIVITY_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activityComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVITY_COMMENT_UPPER_LIMIT = 200; }
      }
      if (activityComment != null && !wt.fc.PersistenceHelper.checkStoredLength(activityComment.toString(), ACTIVITY_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activityComment"), java.lang.String.valueOf(java.lang.Math.min(ACTIVITY_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activityComment", this.activityComment, activityComment));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String PRE_VERSION = "preVersion";
   static int PRE_VERSION_UPPER_LIMIT = -1;
   java.lang.String preVersion;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getPreVersion() {
      return preVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setPreVersion(java.lang.String preVersion) throws wt.util.WTPropertyVetoException {
      preVersionValidate(preVersion);
      this.preVersion = preVersion;
   }
   void preVersionValidate(java.lang.String preVersion) throws wt.util.WTPropertyVetoException {
      if (PRE_VERSION_UPPER_LIMIT < 1) {
         try { PRE_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("preVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRE_VERSION_UPPER_LIMIT = 200; }
      }
      if (preVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(preVersion.toString(), PRE_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "preVersion"), java.lang.String.valueOf(java.lang.Math.min(PRE_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "preVersion", this.preVersion, preVersion));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String AFTER_VERSION = "afterVersion";
   static int AFTER_VERSION_UPPER_LIMIT = -1;
   java.lang.String afterVersion;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getAfterVersion() {
      return afterVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setAfterVersion(java.lang.String afterVersion) throws wt.util.WTPropertyVetoException {
      afterVersionValidate(afterVersion);
      this.afterVersion = afterVersion;
   }
   void afterVersionValidate(java.lang.String afterVersion) throws wt.util.WTPropertyVetoException {
      if (AFTER_VERSION_UPPER_LIMIT < 1) {
         try { AFTER_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("afterVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AFTER_VERSION_UPPER_LIMIT = 200; }
      }
      if (afterVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(afterVersion.toString(), AFTER_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "afterVersion"), java.lang.String.valueOf(java.lang.Math.min(AFTER_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "afterVersion", this.afterVersion, afterVersion));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String DIE_NO = "dieNo";
   static int DIE_NO_UPPER_LIMIT = -1;
   java.lang.String dieNo;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getDieNo() {
      return dieNo;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
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
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String SCHEDULE_ID = "scheduleId";
   static int SCHEDULE_ID_UPPER_LIMIT = -1;
   java.lang.String scheduleId;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getScheduleId() {
      return scheduleId;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
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
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String CHANGE_YN = "changeYn";
   static int CHANGE_YN_UPPER_LIMIT = -1;
   java.lang.String changeYn;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getChangeYn() {
      return changeYn;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setChangeYn(java.lang.String changeYn) throws wt.util.WTPropertyVetoException {
      changeYnValidate(changeYn);
      this.changeYn = changeYn;
   }
   void changeYnValidate(java.lang.String changeYn) throws wt.util.WTPropertyVetoException {
      if (CHANGE_YN_UPPER_LIMIT < 1) {
         try { CHANGE_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_YN_UPPER_LIMIT = 200; }
      }
      if (changeYn != null && !wt.fc.PersistenceHelper.checkStoredLength(changeYn.toString(), CHANGE_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeYn"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeYn", this.changeYn, changeYn));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String EPM_DOC_TYPE = "epmDocType";
   static int EPM_DOC_TYPE_UPPER_LIMIT = -1;
   java.lang.String epmDocType;
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public java.lang.String getEpmDocType() {
      return epmDocType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setEpmDocType(java.lang.String epmDocType) throws wt.util.WTPropertyVetoException {
      epmDocTypeValidate(epmDocType);
      this.epmDocType = epmDocType;
   }
   void epmDocTypeValidate(java.lang.String epmDocType) throws wt.util.WTPropertyVetoException {
      if (EPM_DOC_TYPE_UPPER_LIMIT < 1) {
         try { EPM_DOC_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("epmDocType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EPM_DOC_TYPE_UPPER_LIMIT = 200; }
      }
      if (epmDocType != null && !wt.fc.PersistenceHelper.checkStoredLength(epmDocType.toString(), EPM_DOC_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "epmDocType"), java.lang.String.valueOf(java.lang.Math.min(EPM_DOC_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "epmDocType", this.epmDocType, epmDocType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String EPM_DOC_ROLE = "epmDoc";
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public wt.epm.EPMDocument getEpmDoc() {
      return (wt.epm.EPMDocument) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setEpmDoc(wt.epm.EPMDocument the_epmDoc) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_epmDoc);
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public static final java.lang.String MOLD_ECAROLE = "moldECA";
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public e3ps.ecm.entity.KETMoldChangeActivity getMoldECA() {
      return (e3ps.ecm.entity.KETMoldChangeActivity) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECAEpmDocLink
    */
   public void setMoldECA(e3ps.ecm.entity.KETMoldChangeActivity the_moldECA) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_moldECA);
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

   public static final long EXTERNALIZATION_VERSION_UID = -7570980208166268188L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( activityComment );
      output.writeObject( afterVersion );
      output.writeObject( changeType );
      output.writeObject( changeYn );
      output.writeObject( dieNo );
      output.writeObject( epmDocType );
      output.writeObject( planDate );
      output.writeObject( preVersion );
      output.writeObject( scheduleId );
   }

   protected void super_writeExternal_KETMoldECAEpmDocLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldECAEpmDocLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldECAEpmDocLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "activityComment", activityComment );
      output.setString( "afterVersion", afterVersion );
      output.setString( "changeType", changeType );
      output.setString( "changeYn", changeYn );
      output.setString( "dieNo", dieNo );
      output.setString( "epmDocType", epmDocType );
      output.setTimestamp( "planDate", planDate );
      output.setString( "preVersion", preVersion );
      output.setString( "scheduleId", scheduleId );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      activityComment = input.getString( "activityComment" );
      afterVersion = input.getString( "afterVersion" );
      changeType = input.getString( "changeType" );
      changeYn = input.getString( "changeYn" );
      dieNo = input.getString( "dieNo" );
      epmDocType = input.getString( "epmDocType" );
      planDate = input.getTimestamp( "planDate" );
      preVersion = input.getString( "preVersion" );
      scheduleId = input.getString( "scheduleId" );
   }

   boolean readVersion_7570980208166268188L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      activityComment = (java.lang.String) input.readObject();
      afterVersion = (java.lang.String) input.readObject();
      changeType = (java.lang.String) input.readObject();
      changeYn = (java.lang.String) input.readObject();
      dieNo = (java.lang.String) input.readObject();
      epmDocType = (java.lang.String) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      preVersion = (java.lang.String) input.readObject();
      scheduleId = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETMoldECAEpmDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7570980208166268188L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldECAEpmDocLink( _KETMoldECAEpmDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
