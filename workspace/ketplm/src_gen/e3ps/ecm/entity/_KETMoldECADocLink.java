package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldECADocLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldECADocLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String CHANGE_TYPE = "changeType";
   static int CHANGE_TYPE_UPPER_LIMIT = -1;
   java.lang.String changeType;
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public java.lang.String getChangeType() {
      return changeType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
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
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String TARGET_PART = "targetPart";
   static int TARGET_PART_UPPER_LIMIT = -1;
   java.lang.String targetPart;
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public java.lang.String getTargetPart() {
      return targetPart;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public void setTargetPart(java.lang.String targetPart) throws wt.util.WTPropertyVetoException {
      targetPartValidate(targetPart);
      this.targetPart = targetPart;
   }
   void targetPartValidate(java.lang.String targetPart) throws wt.util.WTPropertyVetoException {
      if (TARGET_PART_UPPER_LIMIT < 1) {
         try { TARGET_PART_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetPart").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_PART_UPPER_LIMIT = 200; }
      }
      if (targetPart != null && !wt.fc.PersistenceHelper.checkStoredLength(targetPart.toString(), TARGET_PART_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetPart"), java.lang.String.valueOf(java.lang.Math.min(TARGET_PART_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetPart", this.targetPart, targetPart));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String ACTIVITY_COMMENT = "activityComment";
   static int ACTIVITY_COMMENT_UPPER_LIMIT = -1;
   java.lang.String activityComment;
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public java.lang.String getActivityComment() {
      return activityComment;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
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
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String PRE_VERSION = "preVersion";
   static int PRE_VERSION_UPPER_LIMIT = -1;
   java.lang.String preVersion;
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public java.lang.String getPreVersion() {
      return preVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
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
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String AFTER_VERSION = "afterVersion";
   static int AFTER_VERSION_UPPER_LIMIT = -1;
   java.lang.String afterVersion;
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public java.lang.String getAfterVersion() {
      return afterVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
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
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String DOC_ROLE = "doc";
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public e3ps.dms.entity.KETProjectDocument getDoc() {
      return (e3ps.dms.entity.KETProjectDocument) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public void setDoc(e3ps.dms.entity.KETProjectDocument the_doc) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_doc);
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public static final java.lang.String MOLD_ECAROLE = "moldECA";
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
    */
   public e3ps.ecm.entity.KETMoldChangeActivity getMoldECA() {
      return (e3ps.ecm.entity.KETMoldChangeActivity) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECADocLink
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

   public static final long EXTERNALIZATION_VERSION_UID = 7900860518073643120L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( activityComment );
      output.writeObject( afterVersion );
      output.writeObject( changeType );
      output.writeObject( planDate );
      output.writeObject( preVersion );
      output.writeObject( targetPart );
   }

   protected void super_writeExternal_KETMoldECADocLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldECADocLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldECADocLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "activityComment", activityComment );
      output.setString( "afterVersion", afterVersion );
      output.setString( "changeType", changeType );
      output.setTimestamp( "planDate", planDate );
      output.setString( "preVersion", preVersion );
      output.setString( "targetPart", targetPart );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      activityComment = input.getString( "activityComment" );
      afterVersion = input.getString( "afterVersion" );
      changeType = input.getString( "changeType" );
      planDate = input.getTimestamp( "planDate" );
      preVersion = input.getString( "preVersion" );
      targetPart = input.getString( "targetPart" );
   }

   boolean readVersion7900860518073643120L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      activityComment = (java.lang.String) input.readObject();
      afterVersion = (java.lang.String) input.readObject();
      changeType = (java.lang.String) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      preVersion = (java.lang.String) input.readObject();
      targetPart = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETMoldECADocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7900860518073643120L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldECADocLink( _KETMoldECADocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
