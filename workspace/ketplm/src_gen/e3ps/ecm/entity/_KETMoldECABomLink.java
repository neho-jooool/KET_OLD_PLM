package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldECABomLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldECABomLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String ACTIVITY_COMMENT = "activityComment";
   static int ACTIVITY_COMMENT_UPPER_LIMIT = -1;
   java.lang.String activityComment;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getActivityComment() {
      return activityComment;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
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
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String PRE_VERSION = "preVersion";
   static int PRE_VERSION_UPPER_LIMIT = -1;
   java.lang.String preVersion;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getPreVersion() {
      return preVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
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
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String AFTER_VERSION = "afterVersion";
   static int AFTER_VERSION_UPPER_LIMIT = -1;
   java.lang.String afterVersion;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getAfterVersion() {
      return afterVersion;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
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
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String CHANGE_YN = "changeYn";
   static int CHANGE_YN_UPPER_LIMIT = -1;
   java.lang.String changeYn;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getChangeYn() {
      return changeYn;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
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
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String BEFORE_PART_OID = "beforePartOid";
   static int BEFORE_PART_OID_UPPER_LIMIT = -1;
   java.lang.String beforePartOid;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getBeforePartOid() {
      return beforePartOid;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public void setBeforePartOid(java.lang.String beforePartOid) throws wt.util.WTPropertyVetoException {
      beforePartOidValidate(beforePartOid);
      this.beforePartOid = beforePartOid;
   }
   void beforePartOidValidate(java.lang.String beforePartOid) throws wt.util.WTPropertyVetoException {
      if (BEFORE_PART_OID_UPPER_LIMIT < 1) {
         try { BEFORE_PART_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("beforePartOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BEFORE_PART_OID_UPPER_LIMIT = 200; }
      }
      if (beforePartOid != null && !wt.fc.PersistenceHelper.checkStoredLength(beforePartOid.toString(), BEFORE_PART_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "beforePartOid"), java.lang.String.valueOf(java.lang.Math.min(BEFORE_PART_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "beforePartOid", this.beforePartOid, beforePartOid));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String EXPECT_COST = "expectCost";
   static int EXPECT_COST_UPPER_LIMIT = -1;
   java.lang.String expectCost;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getExpectCost() {
      return expectCost;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public void setExpectCost(java.lang.String expectCost) throws wt.util.WTPropertyVetoException {
      expectCostValidate(expectCost);
      this.expectCost = expectCost;
   }
   void expectCostValidate(java.lang.String expectCost) throws wt.util.WTPropertyVetoException {
      if (EXPECT_COST_UPPER_LIMIT < 1) {
         try { EXPECT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expectCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPECT_COST_UPPER_LIMIT = 200; }
      }
      if (expectCost != null && !wt.fc.PersistenceHelper.checkStoredLength(expectCost.toString(), EXPECT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expectCost"), java.lang.String.valueOf(java.lang.Math.min(EXPECT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expectCost", this.expectCost, expectCost));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String SECURE_BUDGET_YN = "secureBudgetYn";
   static int SECURE_BUDGET_YN_UPPER_LIMIT = -1;
   java.lang.String secureBudgetYn;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getSecureBudgetYn() {
      return secureBudgetYn;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public void setSecureBudgetYn(java.lang.String secureBudgetYn) throws wt.util.WTPropertyVetoException {
      secureBudgetYnValidate(secureBudgetYn);
      this.secureBudgetYn = secureBudgetYn;
   }
   void secureBudgetYnValidate(java.lang.String secureBudgetYn) throws wt.util.WTPropertyVetoException {
      if (SECURE_BUDGET_YN_UPPER_LIMIT < 1) {
         try { SECURE_BUDGET_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("secureBudgetYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SECURE_BUDGET_YN_UPPER_LIMIT = 200; }
      }
      if (secureBudgetYn != null && !wt.fc.PersistenceHelper.checkStoredLength(secureBudgetYn.toString(), SECURE_BUDGET_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "secureBudgetYn"), java.lang.String.valueOf(java.lang.Math.min(SECURE_BUDGET_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "secureBudgetYn", this.secureBudgetYn, secureBudgetYn));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String RELATED_DIE_NO = "relatedDieNo";
   static int RELATED_DIE_NO_UPPER_LIMIT = -1;
   java.lang.String relatedDieNo;
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public java.lang.String getRelatedDieNo() {
      return relatedDieNo;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public void setRelatedDieNo(java.lang.String relatedDieNo) throws wt.util.WTPropertyVetoException {
      relatedDieNoValidate(relatedDieNo);
      this.relatedDieNo = relatedDieNo;
   }
   void relatedDieNoValidate(java.lang.String relatedDieNo) throws wt.util.WTPropertyVetoException {
      if (RELATED_DIE_NO_UPPER_LIMIT < 1) {
         try { RELATED_DIE_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("relatedDieNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RELATED_DIE_NO_UPPER_LIMIT = 200; }
      }
      if (relatedDieNo != null && !wt.fc.PersistenceHelper.checkStoredLength(relatedDieNo.toString(), RELATED_DIE_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "relatedDieNo"), java.lang.String.valueOf(java.lang.Math.min(RELATED_DIE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "relatedDieNo", this.relatedDieNo, relatedDieNo));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String BOM_ROLE = "bom";
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public e3ps.bom.entity.KETBomEcoHeader getBom() {
      return (e3ps.bom.entity.KETBomEcoHeader) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public void setBom(e3ps.bom.entity.KETBomEcoHeader the_bom) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_bom);
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public static final java.lang.String MOLD_ECAROLE = "moldECA";
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
    */
   public e3ps.ecm.entity.KETMoldChangeActivity getMoldECA() {
      return (e3ps.ecm.entity.KETMoldChangeActivity) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECABomLink
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

   public static final long EXTERNALIZATION_VERSION_UID = 7788848198306164456L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( activityComment );
      output.writeObject( afterVersion );
      output.writeObject( beforePartOid );
      output.writeObject( changeYn );
      output.writeObject( expectCost );
      output.writeObject( planDate );
      output.writeObject( preVersion );
      output.writeObject( relatedDieNo );
      output.writeObject( secureBudgetYn );
   }

   protected void super_writeExternal_KETMoldECABomLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldECABomLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldECABomLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "activityComment", activityComment );
      output.setString( "afterVersion", afterVersion );
      output.setString( "beforePartOid", beforePartOid );
      output.setString( "changeYn", changeYn );
      output.setString( "expectCost", expectCost );
      output.setTimestamp( "planDate", planDate );
      output.setString( "preVersion", preVersion );
      output.setString( "relatedDieNo", relatedDieNo );
      output.setString( "secureBudgetYn", secureBudgetYn );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      activityComment = input.getString( "activityComment" );
      afterVersion = input.getString( "afterVersion" );
      beforePartOid = input.getString( "beforePartOid" );
      changeYn = input.getString( "changeYn" );
      expectCost = input.getString( "expectCost" );
      planDate = input.getTimestamp( "planDate" );
      preVersion = input.getString( "preVersion" );
      relatedDieNo = input.getString( "relatedDieNo" );
      secureBudgetYn = input.getString( "secureBudgetYn" );
   }

   boolean readVersion7788848198306164456L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      activityComment = (java.lang.String) input.readObject();
      afterVersion = (java.lang.String) input.readObject();
      beforePartOid = (java.lang.String) input.readObject();
      changeYn = (java.lang.String) input.readObject();
      expectCost = (java.lang.String) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      preVersion = (java.lang.String) input.readObject();
      relatedDieNo = (java.lang.String) input.readObject();
      secureBudgetYn = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETMoldECABomLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7788848198306164456L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldECABomLink( _KETMoldECABomLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
