package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostInfo implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = CostInfo.class.getName();

   /**
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String COST_TYPE = "costType";
   static int COST_TYPE_UPPER_LIMIT = -1;
   java.lang.String costType;
   /**
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getCostType() {
      return costType;
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public void setCostType(java.lang.String costType) throws wt.util.WTPropertyVetoException {
      costTypeValidate(costType);
      this.costType = costType;
   }
   void costTypeValidate(java.lang.String costType) throws wt.util.WTPropertyVetoException {
      if (COST_TYPE_UPPER_LIMIT < 1) {
         try { COST_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_TYPE_UPPER_LIMIT = 200; }
      }
      if (costType != null && !wt.fc.PersistenceHelper.checkStoredLength(costType.toString(), COST_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costType"), java.lang.String.valueOf(java.lang.Math.min(COST_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costType", this.costType, costType));
   }

   /**
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String DIE_NO = "dieNo";
   static int DIE_NO_UPPER_LIMIT = -1;
   java.lang.String dieNo;
   /**
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getDieNo() {
      return dieNo;
   }
   /**
    * @see e3ps.project.CostInfo
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
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String COST_NAME = "costName";
   static int COST_NAME_UPPER_LIMIT = -1;
   java.lang.String costName;
   /**
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getCostName() {
      return costName;
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public void setCostName(java.lang.String costName) throws wt.util.WTPropertyVetoException {
      costNameValidate(costName);
      this.costName = costName;
   }
   void costNameValidate(java.lang.String costName) throws wt.util.WTPropertyVetoException {
      if (COST_NAME_UPPER_LIMIT < 1) {
         try { COST_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_NAME_UPPER_LIMIT = 200; }
      }
      if (costName != null && !wt.fc.PersistenceHelper.checkStoredLength(costName.toString(), COST_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costName"), java.lang.String.valueOf(java.lang.Math.min(COST_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costName", this.costName, costName));
   }

   /**
    * 목표가
    *
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String TARGET_COST = "targetCost";
   static int TARGET_COST_UPPER_LIMIT = -1;
   java.lang.String targetCost;
   /**
    * 목표가
    *
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getTargetCost() {
      return targetCost;
   }
   /**
    * 목표가
    *
    * @see e3ps.project.CostInfo
    */
   public void setTargetCost(java.lang.String targetCost) throws wt.util.WTPropertyVetoException {
      targetCostValidate(targetCost);
      this.targetCost = targetCost;
   }
   void targetCostValidate(java.lang.String targetCost) throws wt.util.WTPropertyVetoException {
      if (TARGET_COST_UPPER_LIMIT < 1) {
         try { TARGET_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("targetCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TARGET_COST_UPPER_LIMIT = 200; }
      }
      if (targetCost != null && !wt.fc.PersistenceHelper.checkStoredLength(targetCost.toString(), TARGET_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetCost"), java.lang.String.valueOf(java.lang.Math.min(TARGET_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "targetCost", this.targetCost, targetCost));
   }

   /**
    * 투자오더
    *
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String ORDER_INVEST = "orderInvest";
   static int ORDER_INVEST_UPPER_LIMIT = -1;
   java.lang.String orderInvest;
   /**
    * 투자오더
    *
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getOrderInvest() {
      return orderInvest;
   }
   /**
    * 투자오더
    *
    * @see e3ps.project.CostInfo
    */
   public void setOrderInvest(java.lang.String orderInvest) throws wt.util.WTPropertyVetoException {
      orderInvestValidate(orderInvest);
      this.orderInvest = orderInvest;
   }
   void orderInvestValidate(java.lang.String orderInvest) throws wt.util.WTPropertyVetoException {
      if (ORDER_INVEST_UPPER_LIMIT < 1) {
         try { ORDER_INVEST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("orderInvest").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ORDER_INVEST_UPPER_LIMIT = 200; }
      }
      if (orderInvest != null && !wt.fc.PersistenceHelper.checkStoredLength(orderInvest.toString(), ORDER_INVEST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "orderInvest"), java.lang.String.valueOf(java.lang.Math.min(ORDER_INVEST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "orderInvest", this.orderInvest, orderInvest));
   }

   /**
    * 집행가
    *
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String EXECUTION_COST = "executionCost";
   static int EXECUTION_COST_UPPER_LIMIT = -1;
   java.lang.String executionCost;
   /**
    * 집행가
    *
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getExecutionCost() {
      return executionCost;
   }
   /**
    * 집행가
    *
    * @see e3ps.project.CostInfo
    */
   public void setExecutionCost(java.lang.String executionCost) throws wt.util.WTPropertyVetoException {
      executionCostValidate(executionCost);
      this.executionCost = executionCost;
   }
   void executionCostValidate(java.lang.String executionCost) throws wt.util.WTPropertyVetoException {
      if (EXECUTION_COST_UPPER_LIMIT < 1) {
         try { EXECUTION_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("executionCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXECUTION_COST_UPPER_LIMIT = 200; }
      }
      if (executionCost != null && !wt.fc.PersistenceHelper.checkStoredLength(executionCost.toString(), EXECUTION_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "executionCost"), java.lang.String.valueOf(java.lang.Math.min(EXECUTION_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "executionCost", this.executionCost, executionCost));
   }

   /**
    * 수정비
    *
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String EDIT_COST = "editCost";
   static int EDIT_COST_UPPER_LIMIT = -1;
   java.lang.String editCost;
   /**
    * 수정비
    *
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getEditCost() {
      return editCost;
   }
   /**
    * 수정비
    *
    * @see e3ps.project.CostInfo
    */
   public void setEditCost(java.lang.String editCost) throws wt.util.WTPropertyVetoException {
      editCostValidate(editCost);
      this.editCost = editCost;
   }
   void editCostValidate(java.lang.String editCost) throws wt.util.WTPropertyVetoException {
      if (EDIT_COST_UPPER_LIMIT < 1) {
         try { EDIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("editCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EDIT_COST_UPPER_LIMIT = 200; }
      }
      if (editCost != null && !wt.fc.PersistenceHelper.checkStoredLength(editCost.toString(), EDIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "editCost"), java.lang.String.valueOf(java.lang.Math.min(EDIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "editCost", this.editCost, editCost));
   }

   /**
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * @see e3ps.project.CostInfo
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
    * 확정가
    *
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String DECIDE_COST = "decideCost";
   static int DECIDE_COST_UPPER_LIMIT = -1;
   java.lang.String decideCost;
   /**
    * 확정가
    *
    * @see e3ps.project.CostInfo
    */
   public java.lang.String getDecideCost() {
      return decideCost;
   }
   /**
    * 확정가
    *
    * @see e3ps.project.CostInfo
    */
   public void setDecideCost(java.lang.String decideCost) throws wt.util.WTPropertyVetoException {
      decideCostValidate(decideCost);
      this.decideCost = decideCost;
   }
   void decideCostValidate(java.lang.String decideCost) throws wt.util.WTPropertyVetoException {
      if (DECIDE_COST_UPPER_LIMIT < 1) {
         try { DECIDE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("decideCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DECIDE_COST_UPPER_LIMIT = 200; }
      }
      if (decideCost != null && !wt.fc.PersistenceHelper.checkStoredLength(decideCost.toString(), DECIDE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "decideCost"), java.lang.String.valueOf(java.lang.Math.min(DECIDE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "decideCost", this.decideCost, decideCost));
   }

   /**
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.CostInfo
    */
   public e3ps.project.ProductProject getProject() {
      return (projectReference != null) ? (e3ps.project.ProductProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public void setProject(e3ps.project.ProductProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProductProject) the_project));
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public void setProjectReference(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      projectReferenceValidate(the_projectReference);
      projectReference = (wt.fc.ObjectReference) the_projectReference;
   }
   void projectReferenceValidate(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      if (the_projectReference == null || the_projectReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference") },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
      if (the_projectReference != null && the_projectReference.getReferencedClass() != null &&
            !e3ps.project.ProductProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String MOLD_TYPE = "moldType";
   /**
    * @see e3ps.project.CostInfo
    */
   public static final java.lang.String MOLD_TYPE_REFERENCE = "moldTypeReference";
   wt.fc.ObjectReference moldTypeReference;
   /**
    * @see e3ps.project.CostInfo
    */
   public e3ps.common.code.NumberCode getMoldType() {
      return (moldTypeReference != null) ? (e3ps.common.code.NumberCode) moldTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public wt.fc.ObjectReference getMoldTypeReference() {
      return moldTypeReference;
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public void setMoldType(e3ps.common.code.NumberCode the_moldType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldTypeReference(the_moldType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_moldType));
   }
   /**
    * @see e3ps.project.CostInfo
    */
   public void setMoldTypeReference(wt.fc.ObjectReference the_moldTypeReference) throws wt.util.WTPropertyVetoException {
      moldTypeReferenceValidate(the_moldTypeReference);
      moldTypeReference = (wt.fc.ObjectReference) the_moldTypeReference;
   }
   void moldTypeReferenceValidate(wt.fc.ObjectReference the_moldTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_moldTypeReference != null && the_moldTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_moldTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldTypeReference", this.moldTypeReference, moldTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 2516868113288486330L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( costName );
      output.writeObject( costType );
      output.writeObject( decideCost );
      output.writeObject( dieNo );
      output.writeObject( editCost );
      output.writeObject( executionCost );
      output.writeObject( moldTypeReference );
      output.writeObject( orderInvest );
      output.writeObject( partNo );
      output.writeObject( projectReference );
      output.writeObject( targetCost );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.CostInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "costName", costName );
      output.setString( "costType", costType );
      output.setString( "decideCost", decideCost );
      output.setString( "dieNo", dieNo );
      output.setString( "editCost", editCost );
      output.setString( "executionCost", executionCost );
      output.writeObject( "moldTypeReference", moldTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "orderInvest", orderInvest );
      output.setString( "partNo", partNo );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "targetCost", targetCost );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      costName = input.getString( "costName" );
      costType = input.getString( "costType" );
      decideCost = input.getString( "decideCost" );
      dieNo = input.getString( "dieNo" );
      editCost = input.getString( "editCost" );
      executionCost = input.getString( "executionCost" );
      moldTypeReference = (wt.fc.ObjectReference) input.readObject( "moldTypeReference", moldTypeReference, wt.fc.ObjectReference.class, true );
      orderInvest = input.getString( "orderInvest" );
      partNo = input.getString( "partNo" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      targetCost = input.getString( "targetCost" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion2516868113288486330L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      costName = (java.lang.String) input.readObject();
      costType = (java.lang.String) input.readObject();
      decideCost = (java.lang.String) input.readObject();
      dieNo = (java.lang.String) input.readObject();
      editCost = (java.lang.String) input.readObject();
      executionCost = (java.lang.String) input.readObject();
      moldTypeReference = (wt.fc.ObjectReference) input.readObject();
      orderInvest = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      targetCost = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CostInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2516868113288486330L( input, readSerialVersionUID, superDone );
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
