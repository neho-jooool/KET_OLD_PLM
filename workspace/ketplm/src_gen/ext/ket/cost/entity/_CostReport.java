package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostReport extends wt.enterprise.Managed implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostReport.class.getName();

   /**
    * 품번
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 품번
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 품번
    *
    * @see ext.ket.cost.entity.CostReport
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
    * DR단계
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String STEP = "step";
   static int STEP_UPPER_LIMIT = -1;
   java.lang.String step;
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getStep() {
      return step;
   }
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public void setStep(java.lang.String step) throws wt.util.WTPropertyVetoException {
      stepValidate(step);
      this.step = step;
   }
   void stepValidate(java.lang.String step) throws wt.util.WTPropertyVetoException {
      if (STEP_UPPER_LIMIT < 1) {
         try { STEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("step").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STEP_UPPER_LIMIT = 200; }
      }
      if (step != null && !wt.fc.PersistenceHelper.checkStoredLength(step.toString(), STEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "step"), java.lang.String.valueOf(java.lang.Math.min(STEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "step", this.step, step));
   }

   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public void setPjtNo(java.lang.String pjtNo) throws wt.util.WTPropertyVetoException {
      pjtNoValidate(pjtNo);
      this.pjtNo = pjtNo;
   }
   void pjtNoValidate(java.lang.String pjtNo) throws wt.util.WTPropertyVetoException {
      if (PJT_NO_UPPER_LIMIT < 1) {
         try { PJT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_NO_UPPER_LIMIT = 200; }
      }
      if (pjtNo != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtNo.toString(), PJT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtNo"), java.lang.String.valueOf(java.lang.Math.min(PJT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtNo", this.pjtNo, pjtNo));
   }

   /**
    * 물류흐름
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String LOGISTICS_FLOW = "logisticsFlow";
   static int LOGISTICS_FLOW_UPPER_LIMIT = -1;
   java.lang.String logisticsFlow;
   /**
    * 물류흐름
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getLogisticsFlow() {
      return logisticsFlow;
   }
   /**
    * 물류흐름
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public void setLogisticsFlow(java.lang.String logisticsFlow) throws wt.util.WTPropertyVetoException {
      logisticsFlowValidate(logisticsFlow);
      this.logisticsFlow = logisticsFlow;
   }
   void logisticsFlowValidate(java.lang.String logisticsFlow) throws wt.util.WTPropertyVetoException {
      if (LOGISTICS_FLOW_UPPER_LIMIT < 1) {
         try { LOGISTICS_FLOW_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("logisticsFlow").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LOGISTICS_FLOW_UPPER_LIMIT = 200; }
      }
      if (logisticsFlow != null && !wt.fc.PersistenceHelper.checkStoredLength(logisticsFlow.toString(), LOGISTICS_FLOW_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "logisticsFlow"), java.lang.String.valueOf(java.lang.Math.min(LOGISTICS_FLOW_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "logisticsFlow", this.logisticsFlow, logisticsFlow));
   }

   /**
    * 검토목적
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String REVIEW_PURPOSE = "reviewPurpose";
   static int REVIEW_PURPOSE_UPPER_LIMIT = -1;
   java.lang.String reviewPurpose;
   /**
    * 검토목적
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getReviewPurpose() {
      return reviewPurpose;
   }
   /**
    * 검토목적
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public void setReviewPurpose(java.lang.String reviewPurpose) throws wt.util.WTPropertyVetoException {
      reviewPurposeValidate(reviewPurpose);
      this.reviewPurpose = reviewPurpose;
   }
   void reviewPurposeValidate(java.lang.String reviewPurpose) throws wt.util.WTPropertyVetoException {
      if (REVIEW_PURPOSE_UPPER_LIMIT < 1) {
         try { REVIEW_PURPOSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewPurpose").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_PURPOSE_UPPER_LIMIT = 200; }
      }
      if (reviewPurpose != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewPurpose.toString(), REVIEW_PURPOSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewPurpose"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_PURPOSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewPurpose", this.reviewPurpose, reviewPurpose));
   }

   /**
    * 포장사양
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String PACK_SPEC = "packSpec";
   static int PACK_SPEC_UPPER_LIMIT = -1;
   java.lang.String packSpec;
   /**
    * 포장사양
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getPackSpec() {
      return packSpec;
   }
   /**
    * 포장사양
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public void setPackSpec(java.lang.String packSpec) throws wt.util.WTPropertyVetoException {
      packSpecValidate(packSpec);
      this.packSpec = packSpec;
   }
   void packSpecValidate(java.lang.String packSpec) throws wt.util.WTPropertyVetoException {
      if (PACK_SPEC_UPPER_LIMIT < 1) {
         try { PACK_SPEC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packSpec").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_SPEC_UPPER_LIMIT = 200; }
      }
      if (packSpec != null && !wt.fc.PersistenceHelper.checkStoredLength(packSpec.toString(), PACK_SPEC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packSpec"), java.lang.String.valueOf(java.lang.Math.min(PACK_SPEC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packSpec", this.packSpec, packSpec));
   }

   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String VERSION = "version";
   static int VERSION_UPPER_LIMIT = -1;
   java.lang.String version = "0";
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getVersion() {
      return version;
   }
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public void setVersion(java.lang.String version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.String version) throws wt.util.WTPropertyVetoException {
      if (VERSION_UPPER_LIMIT < 1) {
         try { VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("version").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VERSION_UPPER_LIMIT = 200; }
      }
      if (version != null && !wt.fc.PersistenceHelper.checkStoredLength(version.toString(), VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "version"), java.lang.String.valueOf(java.lang.Math.min(VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "version", this.version, version));
   }

   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String BIGO = "bigo";
   static int BIGO_UPPER_LIMIT = -1;
   java.lang.String bigo;
   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getBigo() {
      return bigo;
   }
   /**
    * 비고
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public void setBigo(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      bigoValidate(bigo);
      this.bigo = bigo;
   }
   void bigoValidate(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      if (BIGO_UPPER_LIMIT < 1) {
         try { BIGO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bigo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BIGO_UPPER_LIMIT = 200; }
      }
      if (bigo != null && !wt.fc.PersistenceHelper.checkStoredLength(bigo.toString(), BIGO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bigo"), java.lang.String.valueOf(java.lang.Math.min(BIGO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bigo", this.bigo, bigo));
   }

   /**
    * 배포단계
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String RELEASE_STEP = "releaseStep";
   static int RELEASE_STEP_UPPER_LIMIT = -1;
   java.lang.String releaseStep;
   /**
    * 배포단계
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public java.lang.String getReleaseStep() {
      return releaseStep;
   }
   /**
    * 배포단계
    *
    * @see ext.ket.cost.entity.CostReport
    */
   public void setReleaseStep(java.lang.String releaseStep) throws wt.util.WTPropertyVetoException {
      releaseStepValidate(releaseStep);
      this.releaseStep = releaseStep;
   }
   void releaseStepValidate(java.lang.String releaseStep) throws wt.util.WTPropertyVetoException {
      if (RELEASE_STEP_UPPER_LIMIT < 1) {
         try { RELEASE_STEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("releaseStep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RELEASE_STEP_UPPER_LIMIT = 200; }
      }
      if (releaseStep != null && !wt.fc.PersistenceHelper.checkStoredLength(releaseStep.toString(), RELEASE_STEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "releaseStep"), java.lang.String.valueOf(java.lang.Math.min(RELEASE_STEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "releaseStep", this.releaseStep, releaseStep));
   }

   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public e3ps.project.E3PSProjectMaster getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProjectMaster) projectReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public void setProject(e3ps.project.E3PSProjectMaster the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProjectMaster) the_project));
   }
   /**
    * @see ext.ket.cost.entity.CostReport
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
            !e3ps.project.E3PSProjectMaster.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String TASK = "task";
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public static final java.lang.String TASK_REFERENCE = "taskReference";
   wt.fc.ObjectReference taskReference;
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public e3ps.project.E3PSTask getTask() {
      return (taskReference != null) ? (e3ps.project.E3PSTask) taskReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public wt.fc.ObjectReference getTaskReference() {
      return taskReference;
   }
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public void setTask(e3ps.project.E3PSTask the_task) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTaskReference(the_task == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSTask) the_task));
   }
   /**
    * @see ext.ket.cost.entity.CostReport
    */
   public void setTaskReference(wt.fc.ObjectReference the_taskReference) throws wt.util.WTPropertyVetoException {
      taskReferenceValidate(the_taskReference);
      taskReference = (wt.fc.ObjectReference) the_taskReference;
   }
   void taskReferenceValidate(wt.fc.ObjectReference the_taskReference) throws wt.util.WTPropertyVetoException {
      if (the_taskReference == null || the_taskReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskReference") },
               new java.beans.PropertyChangeEvent(this, "taskReference", this.taskReference, taskReference));
      if (the_taskReference != null && the_taskReference.getReferencedClass() != null &&
            !e3ps.project.E3PSTask.class.isAssignableFrom(the_taskReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "taskReference", this.taskReference, taskReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7490583126186450710L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( bigo );
      output.writeObject( logisticsFlow );
      output.writeObject( packSpec );
      output.writeObject( partNo );
      output.writeObject( pjtNo );
      output.writeObject( projectReference );
      output.writeObject( releaseStep );
      output.writeObject( reviewPurpose );
      output.writeObject( step );
      output.writeObject( taskReference );
      output.writeObject( version );
   }

   protected void super_writeExternal_CostReport(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostReport) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_CostReport(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "bigo", bigo );
      output.setString( "logisticsFlow", logisticsFlow );
      output.setString( "packSpec", packSpec );
      output.setString( "partNo", partNo );
      output.setString( "pjtNo", pjtNo );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "releaseStep", releaseStep );
      output.setString( "reviewPurpose", reviewPurpose );
      output.setString( "step", step );
      output.writeObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      output.setString( "version", version );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      bigo = input.getString( "bigo" );
      logisticsFlow = input.getString( "logisticsFlow" );
      packSpec = input.getString( "packSpec" );
      partNo = input.getString( "partNo" );
      pjtNo = input.getString( "pjtNo" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      releaseStep = input.getString( "releaseStep" );
      reviewPurpose = input.getString( "reviewPurpose" );
      step = input.getString( "step" );
      taskReference = (wt.fc.ObjectReference) input.readObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      version = input.getString( "version" );
   }

   boolean readVersion7490583126186450710L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      bigo = (java.lang.String) input.readObject();
      logisticsFlow = (java.lang.String) input.readObject();
      packSpec = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      releaseStep = (java.lang.String) input.readObject();
      reviewPurpose = (java.lang.String) input.readObject();
      step = (java.lang.String) input.readObject();
      taskReference = (wt.fc.ObjectReference) input.readObject();
      version = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostReport thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7490583126186450710L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_CostReport( _CostReport thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
