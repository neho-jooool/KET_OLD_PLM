package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProdECOPartLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProdECOPartLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public static final java.lang.String EXPECT_COST = "expectCost";
   static int EXPECT_COST_UPPER_LIMIT = -1;
   java.lang.String expectCost;
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public java.lang.String getExpectCost() {
      return expectCost;
   }
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
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
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public static final java.lang.String SECURE_BUDGET_YN = "secureBudgetYn";
   static int SECURE_BUDGET_YN_UPPER_LIMIT = -1;
   java.lang.String secureBudgetYn;
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public java.lang.String getSecureBudgetYn() {
      return secureBudgetYn;
   }
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
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
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public static final java.lang.String RELATED_DIE_NO = "relatedDieNo";
   static int RELATED_DIE_NO_UPPER_LIMIT = -1;
   java.lang.String relatedDieNo;
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public java.lang.String getRelatedDieNo() {
      return relatedDieNo;
   }
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
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
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public static final java.lang.String PART_ROLE = "part";
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public wt.part.WTPart getPart() {
      return (wt.part.WTPart) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public void setPart(wt.part.WTPart the_part) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_part);
   }

   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public static final java.lang.String PROD_ECOROLE = "prodECO";
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public e3ps.ecm.entity.KETProdChangeOrder getProdECO() {
      return (e3ps.ecm.entity.KETProdChangeOrder) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETProdECOPartLink
    */
   public void setProdECO(e3ps.ecm.entity.KETProdChangeOrder the_prodECO) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_prodECO);
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

   public static final long EXTERNALIZATION_VERSION_UID = -5343244382062106964L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( expectCost );
      output.writeObject( relatedDieNo );
      output.writeObject( secureBudgetYn );
   }

   protected void super_writeExternal_KETProdECOPartLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETProdECOPartLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProdECOPartLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "expectCost", expectCost );
      output.setString( "relatedDieNo", relatedDieNo );
      output.setString( "secureBudgetYn", secureBudgetYn );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      expectCost = input.getString( "expectCost" );
      relatedDieNo = input.getString( "relatedDieNo" );
      secureBudgetYn = input.getString( "secureBudgetYn" );
   }

   boolean readVersion_5343244382062106964L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      expectCost = (java.lang.String) input.readObject();
      relatedDieNo = (java.lang.String) input.readObject();
      secureBudgetYn = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProdECOPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_5343244382062106964L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProdECOPartLink( _KETProdECOPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
