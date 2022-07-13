package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesPartClassLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesPartClassLink.class.getName();

   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String MAIN_YN = "mainYn";
   static int MAIN_YN_UPPER_LIMIT = -1;
   java.lang.String mainYn;
   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public java.lang.String getMainYn() {
      return mainYn;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setMainYn(java.lang.String mainYn) throws wt.util.WTPropertyVetoException {
      mainYnValidate(mainYn);
      this.mainYn = mainYn;
   }
   void mainYnValidate(java.lang.String mainYn) throws wt.util.WTPropertyVetoException {
      if (MAIN_YN_UPPER_LIMIT < 1) {
         try { MAIN_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mainYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAIN_YN_UPPER_LIMIT = 200; }
      }
      if (mainYn != null && !wt.fc.PersistenceHelper.checkStoredLength(mainYn.toString(), MAIN_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mainYn"), java.lang.String.valueOf(java.lang.Math.min(MAIN_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mainYn", this.mainYn, mainYn));
   }

   /**
    * ?????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String INVEST_COST = "investCost";
   static int INVEST_COST_UPPER_LIMIT = -1;
   java.lang.String investCost;
   /**
    * ?????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public java.lang.String getInvestCost() {
      return investCost;
   }
   /**
    * ?????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setInvestCost(java.lang.String investCost) throws wt.util.WTPropertyVetoException {
      investCostValidate(investCost);
      this.investCost = investCost;
   }
   void investCostValidate(java.lang.String investCost) throws wt.util.WTPropertyVetoException {
      if (INVEST_COST_UPPER_LIMIT < 1) {
         try { INVEST_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("investCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVEST_COST_UPPER_LIMIT = 200; }
      }
      if (investCost != null && !wt.fc.PersistenceHelper.checkStoredLength(investCost.toString(), INVEST_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "investCost"), java.lang.String.valueOf(java.lang.Math.min(INVEST_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "investCost", this.investCost, investCost));
   }

   /**
    * ???????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String PLAN_TOTAL = "planTotal";
   static int PLAN_TOTAL_UPPER_LIMIT = -1;
   java.lang.String planTotal;
   /**
    * ???????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public java.lang.String getPlanTotal() {
      return planTotal;
   }
   /**
    * ???????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setPlanTotal(java.lang.String planTotal) throws wt.util.WTPropertyVetoException {
      planTotalValidate(planTotal);
      this.planTotal = planTotal;
   }
   void planTotalValidate(java.lang.String planTotal) throws wt.util.WTPropertyVetoException {
      if (PLAN_TOTAL_UPPER_LIMIT < 1) {
         try { PLAN_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_TOTAL_UPPER_LIMIT = 200; }
      }
      if (planTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(planTotal.toString(), PLAN_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planTotal"), java.lang.String.valueOf(java.lang.Math.min(PLAN_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planTotal", this.planTotal, planTotal));
   }

   /**
    * ???????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String PLAN_YEAR = "planYear";
   static int PLAN_YEAR_UPPER_LIMIT = -1;
   java.lang.String planYear;
   /**
    * ???????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public java.lang.String getPlanYear() {
      return planYear;
   }
   /**
    * ???????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setPlanYear(java.lang.String planYear) throws wt.util.WTPropertyVetoException {
      planYearValidate(planYear);
      this.planYear = planYear;
   }
   void planYearValidate(java.lang.String planYear) throws wt.util.WTPropertyVetoException {
      if (PLAN_YEAR_UPPER_LIMIT < 1) {
         try { PLAN_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("planYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLAN_YEAR_UPPER_LIMIT = 200; }
      }
      if (planYear != null && !wt.fc.PersistenceHelper.checkStoredLength(planYear.toString(), PLAN_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "planYear"), java.lang.String.valueOf(java.lang.Math.min(PLAN_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "planYear", this.planYear, planYear));
   }

   /**
    * ?????????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String EXPECT_SALES_TOTAL = "expectSalesTotal";
   static int EXPECT_SALES_TOTAL_UPPER_LIMIT = -1;
   java.lang.String expectSalesTotal;
   /**
    * ?????????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public java.lang.String getExpectSalesTotal() {
      return expectSalesTotal;
   }
   /**
    * ?????????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setExpectSalesTotal(java.lang.String expectSalesTotal) throws wt.util.WTPropertyVetoException {
      expectSalesTotalValidate(expectSalesTotal);
      this.expectSalesTotal = expectSalesTotal;
   }
   void expectSalesTotalValidate(java.lang.String expectSalesTotal) throws wt.util.WTPropertyVetoException {
      if (EXPECT_SALES_TOTAL_UPPER_LIMIT < 1) {
         try { EXPECT_SALES_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expectSalesTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPECT_SALES_TOTAL_UPPER_LIMIT = 200; }
      }
      if (expectSalesTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(expectSalesTotal.toString(), EXPECT_SALES_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expectSalesTotal"), java.lang.String.valueOf(java.lang.Math.min(EXPECT_SALES_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expectSalesTotal", this.expectSalesTotal, expectSalesTotal));
   }

   /**
    * ????????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String EXPECT_SALES_YEAR = "expectSalesYear";
   static int EXPECT_SALES_YEAR_UPPER_LIMIT = -1;
   java.lang.String expectSalesYear;
   /**
    * ????????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public java.lang.String getExpectSalesYear() {
      return expectSalesYear;
   }
   /**
    * ????????
    *
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setExpectSalesYear(java.lang.String expectSalesYear) throws wt.util.WTPropertyVetoException {
      expectSalesYearValidate(expectSalesYear);
      this.expectSalesYear = expectSalesYear;
   }
   void expectSalesYearValidate(java.lang.String expectSalesYear) throws wt.util.WTPropertyVetoException {
      if (EXPECT_SALES_YEAR_UPPER_LIMIT < 1) {
         try { EXPECT_SALES_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expectSalesYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPECT_SALES_YEAR_UPPER_LIMIT = 200; }
      }
      if (expectSalesYear != null && !wt.fc.PersistenceHelper.checkStoredLength(expectSalesYear.toString(), EXPECT_SALES_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expectSalesYear"), java.lang.String.valueOf(java.lang.Math.min(EXPECT_SALES_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expectSalesYear", this.expectSalesYear, expectSalesYear));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String PROJECT_CLASS_ROLE = "projectClass";
   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public ext.ket.sales.entity.KETSalesProject getProjectClass() {
      return (ext.ket.sales.entity.KETSalesProject) getRoleAObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setProjectClass(ext.ket.sales.entity.KETSalesProject the_projectClass) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_projectClass);
   }

   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public static final java.lang.String PART_CLASS_ROLE = "partClass";
   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public ext.ket.part.entity.KETPartClassification getPartClass() {
      return (ext.ket.part.entity.KETPartClassification) getRoleBObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesPartClassLink
    */
   public void setPartClass(ext.ket.part.entity.KETPartClassification the_partClass) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_partClass);
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

   public static final long EXTERNALIZATION_VERSION_UID = 1409780212486578089L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( expectSalesTotal );
      output.writeObject( expectSalesYear );
      output.writeObject( investCost );
      output.writeObject( mainYn );
      output.writeObject( planTotal );
      output.writeObject( planYear );
   }

   protected void super_writeExternal_KETSalesPartClassLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesPartClassLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSalesPartClassLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "expectSalesTotal", expectSalesTotal );
      output.setString( "expectSalesYear", expectSalesYear );
      output.setString( "investCost", investCost );
      output.setString( "mainYn", mainYn );
      output.setString( "planTotal", planTotal );
      output.setString( "planYear", planYear );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      expectSalesTotal = input.getString( "expectSalesTotal" );
      expectSalesYear = input.getString( "expectSalesYear" );
      investCost = input.getString( "investCost" );
      mainYn = input.getString( "mainYn" );
      planTotal = input.getString( "planTotal" );
      planYear = input.getString( "planYear" );
   }

   boolean readVersion1409780212486578089L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      expectSalesTotal = (java.lang.String) input.readObject();
      expectSalesYear = (java.lang.String) input.readObject();
      investCost = (java.lang.String) input.readObject();
      mainYn = (java.lang.String) input.readObject();
      planTotal = (java.lang.String) input.readObject();
      planYear = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETSalesPartClassLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1409780212486578089L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSalesPartClassLink( _KETSalesPartClassLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
