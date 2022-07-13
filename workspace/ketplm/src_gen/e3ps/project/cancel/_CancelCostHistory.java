package e3ps.project.cancel;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CancelCostHistory implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.cancel.cancelResource";
   static final java.lang.String CLASSNAME = CancelCostHistory.class.getName();

   /**
    * 리비전
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public static final java.lang.String REV = "rev";
   static int REV_UPPER_LIMIT = -1;
   java.lang.String rev;
   /**
    * 리비전
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public java.lang.String getRev() {
      return rev;
   }
   /**
    * 리비전
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public void setRev(java.lang.String rev) throws wt.util.WTPropertyVetoException {
      revValidate(rev);
      this.rev = rev;
   }
   void revValidate(java.lang.String rev) throws wt.util.WTPropertyVetoException {
      if (REV_UPPER_LIMIT < 1) {
         try { REV_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rev").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REV_UPPER_LIMIT = 200; }
      }
      if (rev != null && !wt.fc.PersistenceHelper.checkStoredLength(rev.toString(), REV_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rev"), java.lang.String.valueOf(java.lang.Math.min(REV_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rev", this.rev, rev));
   }

   /**
    * 목표회수비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public static final java.lang.String TARGET_COLLECT_COST = "targetCollectCost";
   static int TARGET_COLLECT_COST_UPPER_LIMIT = -1;
   java.lang.String targetCollectCost;
   /**
    * 목표회수비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public java.lang.String getTargetCollectCost() {
      return targetCollectCost;
   }
   /**
    * 목표회수비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public void setTargetCollectCost(java.lang.String targetCollectCost) throws wt.util.WTPropertyVetoException {
      targetCollectCostValidate(targetCollectCost);
      this.targetCollectCost = targetCollectCost;
   }
   void targetCollectCostValidate(java.lang.String targetCollectCost) throws wt.util.WTPropertyVetoException {
      if (TARGET_COLLECT_COST_UPPER_LIMIT < 1) {
         try { TARGET_COLLECT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetCollectCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_COLLECT_COST_UPPER_LIMIT = 200; }
      }
      if (targetCollectCost != null && !wt.fc.PersistenceHelper.checkStoredLength(targetCollectCost.toString(), TARGET_COLLECT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetCollectCost"), java.lang.String.valueOf(java.lang.Math.min(TARGET_COLLECT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetCollectCost", this.targetCollectCost, targetCollectCost));
   }

   /**
    * 실제회수비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public static final java.lang.String REAL_COLLECT_COST = "realCollectCost";
   static int REAL_COLLECT_COST_UPPER_LIMIT = -1;
   java.lang.String realCollectCost;
   /**
    * 실제회수비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public java.lang.String getRealCollectCost() {
      return realCollectCost;
   }
   /**
    * 실제회수비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public void setRealCollectCost(java.lang.String realCollectCost) throws wt.util.WTPropertyVetoException {
      realCollectCostValidate(realCollectCost);
      this.realCollectCost = realCollectCost;
   }
   void realCollectCostValidate(java.lang.String realCollectCost) throws wt.util.WTPropertyVetoException {
      if (REAL_COLLECT_COST_UPPER_LIMIT < 1) {
         try { REAL_COLLECT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("realCollectCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REAL_COLLECT_COST_UPPER_LIMIT = 200; }
      }
      if (realCollectCost != null && !wt.fc.PersistenceHelper.checkStoredLength(realCollectCost.toString(), REAL_COLLECT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "realCollectCost"), java.lang.String.valueOf(java.lang.Math.min(REAL_COLLECT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "realCollectCost", this.realCollectCost, realCollectCost));
   }

   /**
    * 프로젝트 총비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public static final java.lang.String PROJECT_COST = "projectCost";
   static int PROJECT_COST_UPPER_LIMIT = -1;
   java.lang.String projectCost;
   /**
    * 프로젝트 총비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public java.lang.String getProjectCost() {
      return projectCost;
   }
   /**
    * 프로젝트 총비용
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public void setProjectCost(java.lang.String projectCost) throws wt.util.WTPropertyVetoException {
      projectCostValidate(projectCost);
      this.projectCost = projectCost;
   }
   void projectCostValidate(java.lang.String projectCost) throws wt.util.WTPropertyVetoException {
      if (PROJECT_COST_UPPER_LIMIT < 1) {
         try { PROJECT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_COST_UPPER_LIMIT = 200; }
      }
      if (projectCost != null && !wt.fc.PersistenceHelper.checkStoredLength(projectCost.toString(), PROJECT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectCost"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectCost", this.projectCost, projectCost));
   }

   /**
    * 예상손실비
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public static final java.lang.String EXPECTED_LOSS_COST = "expectedLossCost";
   static int EXPECTED_LOSS_COST_UPPER_LIMIT = -1;
   java.lang.String expectedLossCost;
   /**
    * 예상손실비
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public java.lang.String getExpectedLossCost() {
      return expectedLossCost;
   }
   /**
    * 예상손실비
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public void setExpectedLossCost(java.lang.String expectedLossCost) throws wt.util.WTPropertyVetoException {
      expectedLossCostValidate(expectedLossCost);
      this.expectedLossCost = expectedLossCost;
   }
   void expectedLossCostValidate(java.lang.String expectedLossCost) throws wt.util.WTPropertyVetoException {
      if (EXPECTED_LOSS_COST_UPPER_LIMIT < 1) {
         try { EXPECTED_LOSS_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expectedLossCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPECTED_LOSS_COST_UPPER_LIMIT = 200; }
      }
      if (expectedLossCost != null && !wt.fc.PersistenceHelper.checkStoredLength(expectedLossCost.toString(), EXPECTED_LOSS_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expectedLossCost"), java.lang.String.valueOf(java.lang.Math.min(EXPECTED_LOSS_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expectedLossCost", this.expectedLossCost, expectedLossCost));
   }

   /**
    * 최종손실비
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public static final java.lang.String END_LOST_COST = "endLostCost";
   static int END_LOST_COST_UPPER_LIMIT = -1;
   java.lang.String endLostCost;
   /**
    * 최종손실비
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public java.lang.String getEndLostCost() {
      return endLostCost;
   }
   /**
    * 최종손실비
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public void setEndLostCost(java.lang.String endLostCost) throws wt.util.WTPropertyVetoException {
      endLostCostValidate(endLostCost);
      this.endLostCost = endLostCost;
   }
   void endLostCostValidate(java.lang.String endLostCost) throws wt.util.WTPropertyVetoException {
      if (END_LOST_COST_UPPER_LIMIT < 1) {
         try { END_LOST_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("endLostCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { END_LOST_COST_UPPER_LIMIT = 200; }
      }
      if (endLostCost != null && !wt.fc.PersistenceHelper.checkStoredLength(endLostCost.toString(), END_LOST_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "endLostCost"), java.lang.String.valueOf(java.lang.Math.min(END_LOST_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "endLostCost", this.endLostCost, endLostCost));
   }

   /**
    * 회수완료여부
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public static final java.lang.String COLLECT_COMPLETE_YN = "collectCompleteYn";
   static int COLLECT_COMPLETE_YN_UPPER_LIMIT = -1;
   java.lang.String collectCompleteYn;
   /**
    * 회수완료여부
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public java.lang.String getCollectCompleteYn() {
      return collectCompleteYn;
   }
   /**
    * 회수완료여부
    *
    * @see e3ps.project.cancel.CancelCostHistory
    */
   public void setCollectCompleteYn(java.lang.String collectCompleteYn) throws wt.util.WTPropertyVetoException {
      collectCompleteYnValidate(collectCompleteYn);
      this.collectCompleteYn = collectCompleteYn;
   }
   void collectCompleteYnValidate(java.lang.String collectCompleteYn) throws wt.util.WTPropertyVetoException {
      if (COLLECT_COMPLETE_YN_UPPER_LIMIT < 1) {
         try { COLLECT_COMPLETE_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("collectCompleteYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLLECT_COMPLETE_YN_UPPER_LIMIT = 200; }
      }
      if (collectCompleteYn != null && !wt.fc.PersistenceHelper.checkStoredLength(collectCompleteYn.toString(), COLLECT_COMPLETE_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "collectCompleteYn"), java.lang.String.valueOf(java.lang.Math.min(COLLECT_COMPLETE_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "collectCompleteYn", this.collectCompleteYn, collectCompleteYn));
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

   public static final long EXTERNALIZATION_VERSION_UID = 4469537681292013720L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( collectCompleteYn );
      output.writeObject( endLostCost );
      output.writeObject( expectedLossCost );
      output.writeObject( projectCost );
      output.writeObject( realCollectCost );
      output.writeObject( rev );
      output.writeObject( targetCollectCost );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.cancel.CancelCostHistory) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "collectCompleteYn", collectCompleteYn );
      output.setString( "endLostCost", endLostCost );
      output.setString( "expectedLossCost", expectedLossCost );
      output.setString( "projectCost", projectCost );
      output.setString( "realCollectCost", realCollectCost );
      output.setString( "rev", rev );
      output.setString( "targetCollectCost", targetCollectCost );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      collectCompleteYn = input.getString( "collectCompleteYn" );
      endLostCost = input.getString( "endLostCost" );
      expectedLossCost = input.getString( "expectedLossCost" );
      projectCost = input.getString( "projectCost" );
      realCollectCost = input.getString( "realCollectCost" );
      rev = input.getString( "rev" );
      targetCollectCost = input.getString( "targetCollectCost" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion4469537681292013720L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      collectCompleteYn = (java.lang.String) input.readObject();
      endLostCost = (java.lang.String) input.readObject();
      expectedLossCost = (java.lang.String) input.readObject();
      projectCost = (java.lang.String) input.readObject();
      realCollectCost = (java.lang.String) input.readObject();
      rev = (java.lang.String) input.readObject();
      targetCollectCost = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CancelCostHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4469537681292013720L( input, readSerialVersionUID, superDone );
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
