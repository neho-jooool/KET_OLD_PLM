package ext.ket.project.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETCostRate extends wt.enterprise.Managed implements wt.inf.container.WTContained, wt.content.FormatContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = KETCostRate.class.getName();

   /**
    * 검토완료일
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String REVIEW_COMPLETE_DATE = "reviewCompleteDate";
   java.sql.Timestamp reviewCompleteDate;
   /**
    * 검토완료일
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.sql.Timestamp getReviewCompleteDate() {
      return reviewCompleteDate;
   }
   /**
    * 검토완료일
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setReviewCompleteDate(java.sql.Timestamp reviewCompleteDate) throws wt.util.WTPropertyVetoException {
      reviewCompleteDateValidate(reviewCompleteDate);
      this.reviewCompleteDate = reviewCompleteDate;
   }
   void reviewCompleteDateValidate(java.sql.Timestamp reviewCompleteDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 검토단계
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String REVIEW_STEP = "reviewStep";
   static int REVIEW_STEP_UPPER_LIMIT = -1;
   java.lang.String reviewStep;
   /**
    * 검토단계
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getReviewStep() {
      return reviewStep;
   }
   /**
    * 검토단계
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setReviewStep(java.lang.String reviewStep) throws wt.util.WTPropertyVetoException {
      reviewStepValidate(reviewStep);
      this.reviewStep = reviewStep;
   }
   void reviewStepValidate(java.lang.String reviewStep) throws wt.util.WTPropertyVetoException {
      if (REVIEW_STEP_UPPER_LIMIT < 1) {
         try { REVIEW_STEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewStep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_STEP_UPPER_LIMIT = 200; }
      }
      if (reviewStep != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewStep.toString(), REVIEW_STEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewStep"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_STEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewStep", this.reviewStep, reviewStep));
   }

   /**
    * Part Name
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * Part Name
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * Part Name
    *
    * @see ext.ket.project.cost.entity.KETCostRate
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
    * 판매목표가(원)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String SALES_TARGET_COST = "salesTargetCost";
   static int SALES_TARGET_COST_UPPER_LIMIT = -1;
   java.lang.String salesTargetCost;
   /**
    * 판매목표가(원)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getSalesTargetCost() {
      return salesTargetCost;
   }
   /**
    * 판매목표가(원)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setSalesTargetCost(java.lang.String salesTargetCost) throws wt.util.WTPropertyVetoException {
      salesTargetCostValidate(salesTargetCost);
      this.salesTargetCost = salesTargetCost;
   }
   void salesTargetCostValidate(java.lang.String salesTargetCost) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_COST_UPPER_LIMIT < 1) {
         try { SALES_TARGET_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTargetCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_COST_UPPER_LIMIT = 200; }
      }
      if (salesTargetCost != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTargetCost.toString(), SALES_TARGET_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTargetCost"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTargetCost", this.salesTargetCost, salesTargetCost));
   }

   /**
    * 구분
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String CLASSIFICATION = "classification";
   static int CLASSIFICATION_UPPER_LIMIT = -1;
   java.lang.String classification;
   /**
    * 구분
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getClassification() {
      return classification;
   }
   /**
    * 구분
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setClassification(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      classificationValidate(classification);
      this.classification = classification;
   }
   void classificationValidate(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      if (CLASSIFICATION_UPPER_LIMIT < 1) {
         try { CLASSIFICATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classification").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASSIFICATION_UPPER_LIMIT = 200; }
      }
      if (classification != null && !wt.fc.PersistenceHelper.checkStoredLength(classification.toString(), CLASSIFICATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classification"), java.lang.String.valueOf(java.lang.Math.min(CLASSIFICATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classification", this.classification, classification));
   }

   /**
    * 총원가(원)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String TOTAL_COST = "totalCost";
   static int TOTAL_COST_UPPER_LIMIT = -1;
   java.lang.String totalCost;
   /**
    * 총원가(원)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getTotalCost() {
      return totalCost;
   }
   /**
    * 총원가(원)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setTotalCost(java.lang.String totalCost) throws wt.util.WTPropertyVetoException {
      totalCostValidate(totalCost);
      this.totalCost = totalCost;
   }
   void totalCostValidate(java.lang.String totalCost) throws wt.util.WTPropertyVetoException {
      if (TOTAL_COST_UPPER_LIMIT < 1) {
         try { TOTAL_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("totalCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOTAL_COST_UPPER_LIMIT = 200; }
      }
      if (totalCost != null && !wt.fc.PersistenceHelper.checkStoredLength(totalCost.toString(), TOTAL_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "totalCost"), java.lang.String.valueOf(java.lang.Math.min(TOTAL_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "totalCost", this.totalCost, totalCost));
   }

   /**
    * 수익율(%)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String PROFIT_RATE = "profitRate";
   static int PROFIT_RATE_UPPER_LIMIT = -1;
   java.lang.String profitRate;
   /**
    * 수익율(%)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getProfitRate() {
      return profitRate;
   }
   /**
    * 수익율(%)
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setProfitRate(java.lang.String profitRate) throws wt.util.WTPropertyVetoException {
      profitRateValidate(profitRate);
      this.profitRate = profitRate;
   }
   void profitRateValidate(java.lang.String profitRate) throws wt.util.WTPropertyVetoException {
      if (PROFIT_RATE_UPPER_LIMIT < 1) {
         try { PROFIT_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("profitRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROFIT_RATE_UPPER_LIMIT = 200; }
      }
      if (profitRate != null && !wt.fc.PersistenceHelper.checkStoredLength(profitRate.toString(), PROFIT_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "profitRate"), java.lang.String.valueOf(java.lang.Math.min(PROFIT_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "profitRate", this.profitRate, profitRate));
   }

   /**
    * 주요 Issue 사항
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String MAIN_ISSUE = "mainIssue";
   static int MAIN_ISSUE_UPPER_LIMIT = -1;
   java.lang.String mainIssue;
   /**
    * 주요 Issue 사항
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getMainIssue() {
      return mainIssue;
   }
   /**
    * 주요 Issue 사항
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setMainIssue(java.lang.String mainIssue) throws wt.util.WTPropertyVetoException {
      mainIssueValidate(mainIssue);
      this.mainIssue = mainIssue;
   }
   void mainIssueValidate(java.lang.String mainIssue) throws wt.util.WTPropertyVetoException {
      if (MAIN_ISSUE_UPPER_LIMIT < 1) {
         try { MAIN_ISSUE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mainIssue").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAIN_ISSUE_UPPER_LIMIT = 200; }
      }
      if (mainIssue != null && !wt.fc.PersistenceHelper.checkStoredLength(mainIssue.toString(), MAIN_ISSUE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mainIssue"), java.lang.String.valueOf(java.lang.Math.min(MAIN_ISSUE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mainIssue", this.mainIssue, mainIssue));
   }

   /**
    * 차수
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public static final java.lang.String REVISION = "revision";
   static int REVISION_UPPER_LIMIT = -1;
   java.lang.String revision;
   /**
    * 차수
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public java.lang.String getRevision() {
      return revision;
   }
   /**
    * 차수
    *
    * @see ext.ket.project.cost.entity.KETCostRate
    */
   public void setRevision(java.lang.String revision) throws wt.util.WTPropertyVetoException {
      revisionValidate(revision);
      this.revision = revision;
   }
   void revisionValidate(java.lang.String revision) throws wt.util.WTPropertyVetoException {
      if (REVISION_UPPER_LIMIT < 1) {
         try { REVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("revision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVISION_UPPER_LIMIT = 200; }
      }
      if (revision != null && !wt.fc.PersistenceHelper.checkStoredLength(revision.toString(), REVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "revision"), java.lang.String.valueOf(java.lang.Math.min(REVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "revision", this.revision, revision));
   }

   /**
    * Derived from {@link wt.inf.container.WTContainerRef#getName()}
    *
    * @see wt.inf.container.WTContained
    */
   public java.lang.String getContainerName() {
      try { return (java.lang.String) ((wt.inf.container.WTContainerRef) getContainerReference()).getName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   wt.inf.container.WTContainerRef containerReference;
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainer getContainer() {
      return (containerReference != null) ? (wt.inf.container.WTContainer) containerReference.getObject() : null;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainerRef getContainerReference() {
      return containerReference;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainer(wt.inf.container.WTContainer the_container) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setContainerReference(the_container == null ? null : wt.inf.container.WTContainerRef.newWTContainerRef((wt.inf.container.WTContainer) the_container));
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainerReference(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      containerReferenceValidate(the_containerReference);
      containerReference = (wt.inf.container.WTContainerRef) the_containerReference;
   }
   void containerReferenceValidate(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      if (the_containerReference == null || the_containerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference") },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
      if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
            !wt.inf.container.WTContainer.class.isAssignableFrom(the_containerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference"), "WTContainerRef" },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
   }

   wt.content.ContentItem primary;
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public wt.content.ContentItem getPrimary() {
      return primary;
   }
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public void setPrimary(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
      primaryValidate(primary);
      this.primary = primary;
   }
   void primaryValidate(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
   }

   wt.content.DataFormatReference format;
   /**
    * @see wt.content.FormatContentHolder
    */
   public wt.content.DataFormatReference getFormat() {
      return format;
   }
   /**
    * @see wt.content.FormatContentHolder
    */
   public void setFormat(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      formatValidate(format);
      this.format = format;
   }
   void formatValidate(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      if (format == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "format") },
               new java.beans.PropertyChangeEvent(this, "format", this.format, format));
   }

   /**
    * Derived from {@link wt.content.DataFormatReference#getFormatName()}
    *
    * @see wt.content.FormatContentHolder
    */
   public java.lang.String getFormatName() {
      try { return (java.lang.String) ((wt.content.DataFormatReference) getFormat()).getFormatName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   java.util.Vector contentVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getContentVector() {
      return contentVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setContentVector(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
      contentVectorValidate(contentVector);
      this.contentVector = contentVector;
   }
   void contentVectorValidate(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
   }

   boolean hasContents;
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public boolean isHasContents() {
      return hasContents;
   }
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public void setHasContents(boolean hasContents) throws wt.util.WTPropertyVetoException {
      hasContentsValidate(hasContents);
      this.hasContents = hasContents;
   }
   void hasContentsValidate(boolean hasContents) throws wt.util.WTPropertyVetoException {
   }

   wt.content.HttpContentOperation operation;
   /**
    * @see wt.content.ContentHolder
    */
   public wt.content.HttpContentOperation getOperation() {
      return operation;
   }
   /**
    * @see wt.content.ContentHolder
    */
   public void setOperation(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
      operationValidate(operation);
      this.operation = operation;
   }
   void operationValidate(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
   }

   java.util.Vector httpVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getHttpVector() {
      return httpVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setHttpVector(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
      httpVectorValidate(httpVector);
      this.httpVector = httpVector;
   }
   void httpVectorValidate(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -8589687643719214332L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( classification );
      output.writeObject( containerReference );
      output.writeObject( format );
      output.writeObject( mainIssue );
      output.writeObject( partName );
      output.writeObject( profitRate );
      output.writeObject( reviewCompleteDate );
      output.writeObject( reviewStep );
      output.writeObject( revision );
      output.writeObject( salesTargetCost );
      output.writeObject( totalCost );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
         output.writeObject( primary );
      }

   }

   protected void super_writeExternal_KETCostRate(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.cost.entity.KETCostRate) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETCostRate(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "classification", classification );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setString( "mainIssue", mainIssue );
      output.setString( "partName", partName );
      output.setString( "profitRate", profitRate );
      output.setTimestamp( "reviewCompleteDate", reviewCompleteDate );
      output.setString( "reviewStep", reviewStep );
      output.setString( "revision", revision );
      output.setString( "salesTargetCost", salesTargetCost );
      output.setString( "totalCost", totalCost );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      classification = input.getString( "classification" );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      mainIssue = input.getString( "mainIssue" );
      partName = input.getString( "partName" );
      profitRate = input.getString( "profitRate" );
      reviewCompleteDate = input.getTimestamp( "reviewCompleteDate" );
      reviewStep = input.getString( "reviewStep" );
      revision = input.getString( "revision" );
      salesTargetCost = input.getString( "salesTargetCost" );
      totalCost = input.getString( "totalCost" );
   }

   boolean readVersion_8589687643719214332L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      classification = (java.lang.String) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      format = (wt.content.DataFormatReference) input.readObject();
      mainIssue = (java.lang.String) input.readObject();
      partName = (java.lang.String) input.readObject();
      profitRate = (java.lang.String) input.readObject();
      reviewCompleteDate = (java.sql.Timestamp) input.readObject();
      reviewStep = (java.lang.String) input.readObject();
      revision = (java.lang.String) input.readObject();
      salesTargetCost = (java.lang.String) input.readObject();
      totalCost = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETCostRate thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8589687643719214332L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETCostRate( _KETCostRate thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
