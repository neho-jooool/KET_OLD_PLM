package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _E3PSProject extends e3ps.project.TemplateProject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = E3PSProject.class.getName();

   /**
    * 프로젝트 진행율
    *
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String PJT_COMPLETION = "pjtCompletion";
   int pjtCompletion;
   /**
    * 프로젝트 진행율
    *
    * @see e3ps.project.E3PSProject
    */
   public int getPjtCompletion() {
      return pjtCompletion;
   }
   /**
    * 프로젝트 진행율
    *
    * @see e3ps.project.E3PSProject
    */
   public void setPjtCompletion(int pjtCompletion) throws wt.util.WTPropertyVetoException {
      pjtCompletionValidate(pjtCompletion);
      this.pjtCompletion = pjtCompletion;
   }
   void pjtCompletionValidate(int pjtCompletion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트 상태≪≫≪≫0: 진행중≪≫1: 중지(Hold)≪≫2: 취소(Revocation)≪≫3: 재시작(Restart)≪≫4: 지연(Delay)
    *
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String PJT_STATE = "pjtState";
   int pjtState;
   /**
    * 프로젝트 상태≪≫≪≫0: 진행중≪≫1: 중지(Hold)≪≫2: 취소(Revocation)≪≫3: 재시작(Restart)≪≫4: 지연(Delay)
    *
    * @see e3ps.project.E3PSProject
    */
   public int getPjtState() {
      return pjtState;
   }
   /**
    * 프로젝트 상태≪≫≪≫0: 진행중≪≫1: 중지(Hold)≪≫2: 취소(Revocation)≪≫3: 재시작(Restart)≪≫4: 지연(Delay)
    *
    * @see e3ps.project.E3PSProject
    */
   public void setPjtState(int pjtState) throws wt.util.WTPropertyVetoException {
      pjtStateValidate(pjtState);
      this.pjtState = pjtState;
   }
   void pjtStateValidate(int pjtState) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String TEMPLATE_CODE = "templateCode";
   static int TEMPLATE_CODE_UPPER_LIMIT = -1;
   java.lang.String templateCode;
   /**
    * @see e3ps.project.E3PSProject
    */
   public java.lang.String getTemplateCode() {
      return templateCode;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setTemplateCode(java.lang.String templateCode) throws wt.util.WTPropertyVetoException {
      templateCodeValidate(templateCode);
      this.templateCode = templateCode;
   }
   void templateCodeValidate(java.lang.String templateCode) throws wt.util.WTPropertyVetoException {
      if (TEMPLATE_CODE_UPPER_LIMIT < 1) {
         try { TEMPLATE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("templateCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TEMPLATE_CODE_UPPER_LIMIT = 200; }
      }
      if (templateCode != null && !wt.fc.PersistenceHelper.checkStoredLength(templateCode.toString(), TEMPLATE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "templateCode"), java.lang.String.valueOf(java.lang.Math.min(TEMPLATE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "templateCode", this.templateCode, templateCode));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String PREFER_COMP = "preferComp";
   static int PREFER_COMP_UPPER_LIMIT = -1;
   java.lang.String preferComp;
   /**
    * @see e3ps.project.E3PSProject
    */
   public java.lang.String getPreferComp() {
      return preferComp;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setPreferComp(java.lang.String preferComp) throws wt.util.WTPropertyVetoException {
      preferCompValidate(preferComp);
      this.preferComp = preferComp;
   }
   void preferCompValidate(java.lang.String preferComp) throws wt.util.WTPropertyVetoException {
      if (PREFER_COMP_UPPER_LIMIT < 1) {
         try { PREFER_COMP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("preferComp").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PREFER_COMP_UPPER_LIMIT = 200; }
      }
      if (preferComp != null && !wt.fc.PersistenceHelper.checkStoredLength(preferComp.toString(), PREFER_COMP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "preferComp"), java.lang.String.valueOf(java.lang.Math.min(PREFER_COMP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "preferComp", this.preferComp, preferComp));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEVELOPED_TYPE = "developedType";
   static int DEVELOPED_TYPE_UPPER_LIMIT = -1;
   java.lang.String developedType;
   /**
    * @see e3ps.project.E3PSProject
    */
   public java.lang.String getDevelopedType() {
      return developedType;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevelopedType(java.lang.String developedType) throws wt.util.WTPropertyVetoException {
      developedTypeValidate(developedType);
      this.developedType = developedType;
   }
   void developedTypeValidate(java.lang.String developedType) throws wt.util.WTPropertyVetoException {
      if (DEVELOPED_TYPE_UPPER_LIMIT < 1) {
         try { DEVELOPED_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("developedType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEVELOPED_TYPE_UPPER_LIMIT = 4000; }
      }
      if (developedType != null && !wt.fc.PersistenceHelper.checkStoredLength(developedType.toString(), DEVELOPED_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "developedType"), java.lang.String.valueOf(java.lang.Math.min(DEVELOPED_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "developedType", this.developedType, developedType));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEV_REQUEST_NO = "devRequestNo";
   static int DEV_REQUEST_NO_UPPER_LIMIT = -1;
   java.lang.String devRequestNo;
   /**
    * @see e3ps.project.E3PSProject
    */
   public java.lang.String getDevRequestNo() {
      return devRequestNo;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevRequestNo(java.lang.String devRequestNo) throws wt.util.WTPropertyVetoException {
      devRequestNoValidate(devRequestNo);
      this.devRequestNo = devRequestNo;
   }
   void devRequestNoValidate(java.lang.String devRequestNo) throws wt.util.WTPropertyVetoException {
      if (DEV_REQUEST_NO_UPPER_LIMIT < 1) {
         try { DEV_REQUEST_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devRequestNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_REQUEST_NO_UPPER_LIMIT = 200; }
      }
      if (devRequestNo != null && !wt.fc.PersistenceHelper.checkStoredLength(devRequestNo.toString(), DEV_REQUEST_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devRequestNo"), java.lang.String.valueOf(java.lang.Math.min(DEV_REQUEST_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devRequestNo", this.devRequestNo, devRequestNo));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String REVIEW_PJT_NO = "reviewPjtNo";
   static int REVIEW_PJT_NO_UPPER_LIMIT = -1;
   java.lang.String reviewPjtNo;
   /**
    * @see e3ps.project.E3PSProject
    */
   public java.lang.String getReviewPjtNo() {
      return reviewPjtNo;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setReviewPjtNo(java.lang.String reviewPjtNo) throws wt.util.WTPropertyVetoException {
      reviewPjtNoValidate(reviewPjtNo);
      this.reviewPjtNo = reviewPjtNo;
   }
   void reviewPjtNoValidate(java.lang.String reviewPjtNo) throws wt.util.WTPropertyVetoException {
      if (REVIEW_PJT_NO_UPPER_LIMIT < 1) {
         try { REVIEW_PJT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewPjtNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_PJT_NO_UPPER_LIMIT = 200; }
      }
      if (reviewPjtNo != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewPjtNo.toString(), REVIEW_PJT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewPjtNo"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_PJT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewPjtNo", this.reviewPjtNo, reviewPjtNo));
   }

   /**
    * 생산처구분
    *
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String MANUFAC_PLACE = "manufacPlace";
   static int MANUFAC_PLACE_UPPER_LIMIT = -1;
   java.lang.String manufacPlace;
   /**
    * 생산처구분
    *
    * @see e3ps.project.E3PSProject
    */
   public java.lang.String getManufacPlace() {
      return manufacPlace;
   }
   /**
    * 생산처구분
    *
    * @see e3ps.project.E3PSProject
    */
   public void setManufacPlace(java.lang.String manufacPlace) throws wt.util.WTPropertyVetoException {
      manufacPlaceValidate(manufacPlace);
      this.manufacPlace = manufacPlace;
   }
   void manufacPlaceValidate(java.lang.String manufacPlace) throws wt.util.WTPropertyVetoException {
      if (MANUFAC_PLACE_UPPER_LIMIT < 1) {
         try { MANUFAC_PLACE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("manufacPlace").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANUFAC_PLACE_UPPER_LIMIT = 200; }
      }
      if (manufacPlace != null && !wt.fc.PersistenceHelper.checkStoredLength(manufacPlace.toString(), MANUFAC_PLACE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manufacPlace"), java.lang.String.valueOf(java.lang.Math.min(MANUFAC_PLACE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "manufacPlace", this.manufacPlace, manufacPlace));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String PRODUCT_TYPE = "productType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String PRODUCT_TYPE_REFERENCE = "productTypeReference";
   wt.fc.ObjectReference productTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getProductType() {
      return (productTypeReference != null) ? (e3ps.common.code.NumberCode) productTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getProductTypeReference() {
      return productTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setProductType(e3ps.common.code.NumberCode the_productType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProductTypeReference(the_productType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_productType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setProductTypeReference(wt.fc.ObjectReference the_productTypeReference) throws wt.util.WTPropertyVetoException {
      productTypeReferenceValidate(the_productTypeReference);
      productTypeReference = (wt.fc.ObjectReference) the_productTypeReference;
   }
   void productTypeReferenceValidate(wt.fc.ObjectReference the_productTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_productTypeReference != null && the_productTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_productTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "productTypeReference", this.productTypeReference, productTypeReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String RANK = "rank";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String RANK_REFERENCE = "rankReference";
   wt.fc.ObjectReference rankReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getRank() {
      return (rankReference != null) ? (e3ps.common.code.NumberCode) rankReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getRankReference() {
      return rankReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setRank(e3ps.common.code.NumberCode the_rank) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setRankReference(the_rank == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_rank));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setRankReference(wt.fc.ObjectReference the_rankReference) throws wt.util.WTPropertyVetoException {
      rankReferenceValidate(the_rankReference);
      rankReference = (wt.fc.ObjectReference) the_rankReference;
   }
   void rankReferenceValidate(wt.fc.ObjectReference the_rankReference) throws wt.util.WTPropertyVetoException {
      if (the_rankReference != null && the_rankReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_rankReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rankReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "rankReference", this.rankReference, rankReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String ASSEMBLED_TYPE = "assembledType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String ASSEMBLED_TYPE_REFERENCE = "assembledTypeReference";
   wt.fc.ObjectReference assembledTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getAssembledType() {
      return (assembledTypeReference != null) ? (e3ps.common.code.NumberCode) assembledTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getAssembledTypeReference() {
      return assembledTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setAssembledType(e3ps.common.code.NumberCode the_assembledType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAssembledTypeReference(the_assembledType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_assembledType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setAssembledTypeReference(wt.fc.ObjectReference the_assembledTypeReference) throws wt.util.WTPropertyVetoException {
      assembledTypeReferenceValidate(the_assembledTypeReference);
      assembledTypeReference = (wt.fc.ObjectReference) the_assembledTypeReference;
   }
   void assembledTypeReferenceValidate(wt.fc.ObjectReference the_assembledTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_assembledTypeReference != null && the_assembledTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_assembledTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assembledTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "assembledTypeReference", this.assembledTypeReference, assembledTypeReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEVELOPENT_TYPE = "developentType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEVELOPENT_TYPE_REFERENCE = "developentTypeReference";
   wt.fc.ObjectReference developentTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getDevelopentType() {
      return (developentTypeReference != null) ? (e3ps.common.code.NumberCode) developentTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getDevelopentTypeReference() {
      return developentTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevelopentType(e3ps.common.code.NumberCode the_developentType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevelopentTypeReference(the_developentType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_developentType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevelopentTypeReference(wt.fc.ObjectReference the_developentTypeReference) throws wt.util.WTPropertyVetoException {
      developentTypeReferenceValidate(the_developentTypeReference);
      developentTypeReference = (wt.fc.ObjectReference) the_developentTypeReference;
   }
   void developentTypeReferenceValidate(wt.fc.ObjectReference the_developentTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_developentTypeReference != null && the_developentTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_developentTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "developentTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "developentTypeReference", this.developentTypeReference, developentTypeReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String ASSEMBLY_PLACE = "assemblyPlace";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String ASSEMBLY_PLACE_REFERENCE = "assemblyPlaceReference";
   wt.fc.ObjectReference assemblyPlaceReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getAssemblyPlace() {
      return (assemblyPlaceReference != null) ? (e3ps.common.code.NumberCode) assemblyPlaceReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getAssemblyPlaceReference() {
      return assemblyPlaceReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setAssemblyPlace(e3ps.common.code.NumberCode the_assemblyPlace) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAssemblyPlaceReference(the_assemblyPlace == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_assemblyPlace));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setAssemblyPlaceReference(wt.fc.ObjectReference the_assemblyPlaceReference) throws wt.util.WTPropertyVetoException {
      assemblyPlaceReferenceValidate(the_assemblyPlaceReference);
      assemblyPlaceReference = (wt.fc.ObjectReference) the_assemblyPlaceReference;
   }
   void assemblyPlaceReferenceValidate(wt.fc.ObjectReference the_assemblyPlaceReference) throws wt.util.WTPropertyVetoException {
      if (the_assemblyPlaceReference != null && the_assemblyPlaceReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_assemblyPlaceReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assemblyPlaceReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "assemblyPlaceReference", this.assemblyPlaceReference, assemblyPlaceReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String PROCESS = "process";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String PROCESS_REFERENCE = "processReference";
   wt.fc.ObjectReference processReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getProcess() {
      return (processReference != null) ? (e3ps.common.code.NumberCode) processReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getProcessReference() {
      return processReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setProcess(e3ps.common.code.NumberCode the_process) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProcessReference(the_process == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_process));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setProcessReference(wt.fc.ObjectReference the_processReference) throws wt.util.WTPropertyVetoException {
      processReferenceValidate(the_processReference);
      processReference = (wt.fc.ObjectReference) the_processReference;
   }
   void processReferenceValidate(wt.fc.ObjectReference the_processReference) throws wt.util.WTPropertyVetoException {
      if (the_processReference != null && the_processReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_processReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "processReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "processReference", this.processReference, processReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DESIGN_TYPE = "designType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DESIGN_TYPE_REFERENCE = "designTypeReference";
   wt.fc.ObjectReference designTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getDesignType() {
      return (designTypeReference != null) ? (e3ps.common.code.NumberCode) designTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getDesignTypeReference() {
      return designTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDesignType(e3ps.common.code.NumberCode the_designType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDesignTypeReference(the_designType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_designType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDesignTypeReference(wt.fc.ObjectReference the_designTypeReference) throws wt.util.WTPropertyVetoException {
      designTypeReferenceValidate(the_designTypeReference);
      designTypeReference = (wt.fc.ObjectReference) the_designTypeReference;
   }
   void designTypeReferenceValidate(wt.fc.ObjectReference the_designTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_designTypeReference != null && the_designTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_designTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "designTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "designTypeReference", this.designTypeReference, designTypeReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEV_REQUEST = "devRequest";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEV_REQUEST_REFERENCE = "devRequestReference";
   wt.fc.ObjectReference devRequestReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.dms.entity.KETDevelopmentRequest getDevRequest() {
      return (devRequestReference != null) ? (e3ps.dms.entity.KETDevelopmentRequest) devRequestReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getDevRequestReference() {
      return devRequestReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevRequest(e3ps.dms.entity.KETDevelopmentRequest the_devRequest) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevRequestReference(the_devRequest == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.dms.entity.KETDevelopmentRequest) the_devRequest));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevRequestReference(wt.fc.ObjectReference the_devRequestReference) throws wt.util.WTPropertyVetoException {
      devRequestReferenceValidate(the_devRequestReference);
      devRequestReference = (wt.fc.ObjectReference) the_devRequestReference;
   }
   void devRequestReferenceValidate(wt.fc.ObjectReference the_devRequestReference) throws wt.util.WTPropertyVetoException {
      if (the_devRequestReference != null && the_devRequestReference.getReferencedClass() != null &&
            !e3ps.dms.entity.KETDevelopmentRequest.class.isAssignableFrom(the_devRequestReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devRequestReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "devRequestReference", this.devRequestReference, devRequestReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String BUSINESS = "business";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String BUSINESS_REFERENCE = "businessReference";
   wt.fc.ObjectReference businessReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.org.WTUser getBusiness() {
      return (businessReference != null) ? (wt.org.WTUser) businessReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getBusinessReference() {
      return businessReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setBusiness(wt.org.WTUser the_business) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setBusinessReference(the_business == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_business));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setBusinessReference(wt.fc.ObjectReference the_businessReference) throws wt.util.WTPropertyVetoException {
      businessReferenceValidate(the_businessReference);
      businessReference = (wt.fc.ObjectReference) the_businessReference;
   }
   void businessReferenceValidate(wt.fc.ObjectReference the_businessReference) throws wt.util.WTPropertyVetoException {
      if (the_businessReference != null && the_businessReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_businessReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "businessReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "businessReference", this.businessReference, businessReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String TEMPLATE = "template";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String TEMPLATE_REFERENCE = "templateReference";
   wt.fc.ObjectReference templateReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.project.TemplateProject getTemplate() {
      return (templateReference != null) ? (e3ps.project.TemplateProject) templateReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getTemplateReference() {
      return templateReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setTemplate(e3ps.project.TemplateProject the_template) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTemplateReference(the_template == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.TemplateProject) the_template));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setTemplateReference(wt.fc.ObjectReference the_templateReference) throws wt.util.WTPropertyVetoException {
      templateReferenceValidate(the_templateReference);
      templateReference = (wt.fc.ObjectReference) the_templateReference;
   }
   void templateReferenceValidate(wt.fc.ObjectReference the_templateReference) throws wt.util.WTPropertyVetoException {
      if (the_templateReference == null || the_templateReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "templateReference") },
               new java.beans.PropertyChangeEvent(this, "templateReference", this.templateReference, templateReference));
      if (the_templateReference != null && the_templateReference.getReferencedClass() != null &&
            !e3ps.project.TemplateProject.class.isAssignableFrom(the_templateReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "templateReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "templateReference", this.templateReference, templateReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEVELOP_PATTERN = "developPattern";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String DEVELOP_PATTERN_REFERENCE = "developPatternReference";
   wt.fc.ObjectReference developPatternReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getDevelopPattern() {
      return (developPatternReference != null) ? (e3ps.common.code.NumberCode) developPatternReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getDevelopPatternReference() {
      return developPatternReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevelopPattern(e3ps.common.code.NumberCode the_developPattern) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevelopPatternReference(the_developPattern == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_developPattern));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setDevelopPatternReference(wt.fc.ObjectReference the_developPatternReference) throws wt.util.WTPropertyVetoException {
      developPatternReferenceValidate(the_developPatternReference);
      developPatternReference = (wt.fc.ObjectReference) the_developPatternReference;
   }
   void developPatternReferenceValidate(wt.fc.ObjectReference the_developPatternReference) throws wt.util.WTPropertyVetoException {
      if (the_developPatternReference != null && the_developPatternReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_developPatternReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "developPatternReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "developPatternReference", this.developPatternReference, developPatternReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String MAIN_GOAL_TYPE = "mainGoalType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String MAIN_GOAL_TYPE_REFERENCE = "mainGoalTypeReference";
   wt.fc.ObjectReference mainGoalTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getMainGoalType() {
      return (mainGoalTypeReference != null) ? (e3ps.common.code.NumberCode) mainGoalTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getMainGoalTypeReference() {
      return mainGoalTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setMainGoalType(e3ps.common.code.NumberCode the_mainGoalType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMainGoalTypeReference(the_mainGoalType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_mainGoalType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setMainGoalTypeReference(wt.fc.ObjectReference the_mainGoalTypeReference) throws wt.util.WTPropertyVetoException {
      mainGoalTypeReferenceValidate(the_mainGoalTypeReference);
      mainGoalTypeReference = (wt.fc.ObjectReference) the_mainGoalTypeReference;
   }
   void mainGoalTypeReferenceValidate(wt.fc.ObjectReference the_mainGoalTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_mainGoalTypeReference != null && the_mainGoalTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_mainGoalTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mainGoalTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "mainGoalTypeReference", this.mainGoalTypeReference, mainGoalTypeReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String SUB_GOAL_TYPE = "subGoalType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String SUB_GOAL_TYPE_REFERENCE = "subGoalTypeReference";
   wt.fc.ObjectReference subGoalTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getSubGoalType() {
      return (subGoalTypeReference != null) ? (e3ps.common.code.NumberCode) subGoalTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getSubGoalTypeReference() {
      return subGoalTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setSubGoalType(e3ps.common.code.NumberCode the_subGoalType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setSubGoalTypeReference(the_subGoalType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_subGoalType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setSubGoalTypeReference(wt.fc.ObjectReference the_subGoalTypeReference) throws wt.util.WTPropertyVetoException {
      subGoalTypeReferenceValidate(the_subGoalTypeReference);
      subGoalTypeReference = (wt.fc.ObjectReference) the_subGoalTypeReference;
   }
   void subGoalTypeReferenceValidate(wt.fc.ObjectReference the_subGoalTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_subGoalTypeReference != null && the_subGoalTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_subGoalTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subGoalTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "subGoalTypeReference", this.subGoalTypeReference, subGoalTypeReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String MANAGE_PRODUCT_TYPE = "manageProductType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String MANAGE_PRODUCT_TYPE_REFERENCE = "manageProductTypeReference";
   wt.fc.ObjectReference manageProductTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getManageProductType() {
      return (manageProductTypeReference != null) ? (e3ps.common.code.NumberCode) manageProductTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getManageProductTypeReference() {
      return manageProductTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setManageProductType(e3ps.common.code.NumberCode the_manageProductType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setManageProductTypeReference(the_manageProductType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_manageProductType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setManageProductTypeReference(wt.fc.ObjectReference the_manageProductTypeReference) throws wt.util.WTPropertyVetoException {
      manageProductTypeReferenceValidate(the_manageProductTypeReference);
      manageProductTypeReference = (wt.fc.ObjectReference) the_manageProductTypeReference;
   }
   void manageProductTypeReferenceValidate(wt.fc.ObjectReference the_manageProductTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_manageProductTypeReference != null && the_manageProductTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_manageProductTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manageProductTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "manageProductTypeReference", this.manageProductTypeReference, manageProductTypeReference));
   }

   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String OBTAIN_TYPE = "obtainType";
   /**
    * @see e3ps.project.E3PSProject
    */
   public static final java.lang.String OBTAIN_TYPE_REFERENCE = "obtainTypeReference";
   wt.fc.ObjectReference obtainTypeReference;
   /**
    * @see e3ps.project.E3PSProject
    */
   public e3ps.common.code.NumberCode getObtainType() {
      return (obtainTypeReference != null) ? (e3ps.common.code.NumberCode) obtainTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public wt.fc.ObjectReference getObtainTypeReference() {
      return obtainTypeReference;
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setObtainType(e3ps.common.code.NumberCode the_obtainType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setObtainTypeReference(the_obtainType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_obtainType));
   }
   /**
    * @see e3ps.project.E3PSProject
    */
   public void setObtainTypeReference(wt.fc.ObjectReference the_obtainTypeReference) throws wt.util.WTPropertyVetoException {
      obtainTypeReferenceValidate(the_obtainTypeReference);
      obtainTypeReference = (wt.fc.ObjectReference) the_obtainTypeReference;
   }
   void obtainTypeReferenceValidate(wt.fc.ObjectReference the_obtainTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_obtainTypeReference != null && the_obtainTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_obtainTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "obtainTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "obtainTypeReference", this.obtainTypeReference, obtainTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 6435009893100365663L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( assembledTypeReference );
      output.writeObject( assemblyPlaceReference );
      output.writeObject( businessReference );
      output.writeObject( designTypeReference );
      output.writeObject( devRequestNo );
      output.writeObject( devRequestReference );
      output.writeObject( developPatternReference );
      output.writeObject( developedType );
      output.writeObject( developentTypeReference );
      output.writeObject( mainGoalTypeReference );
      output.writeObject( manageProductTypeReference );
      output.writeObject( manufacPlace );
      output.writeObject( obtainTypeReference );
      output.writeInt( pjtCompletion );
      output.writeInt( pjtState );
      output.writeObject( preferComp );
      output.writeObject( processReference );
      output.writeObject( productTypeReference );
      output.writeObject( rankReference );
      output.writeObject( reviewPjtNo );
      output.writeObject( subGoalTypeReference );
      output.writeObject( templateCode );
      output.writeObject( templateReference );
   }

   protected void super_writeExternal_E3PSProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.E3PSProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_E3PSProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "assembledTypeReference", assembledTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "assemblyPlaceReference", assemblyPlaceReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "businessReference", businessReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "designTypeReference", designTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "devRequestNo", devRequestNo );
      output.writeObject( "devRequestReference", devRequestReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "developPatternReference", developPatternReference, wt.fc.ObjectReference.class, true );
      output.setString( "developedType", developedType );
      output.writeObject( "developentTypeReference", developentTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "mainGoalTypeReference", mainGoalTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "manageProductTypeReference", manageProductTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "manufacPlace", manufacPlace );
      output.writeObject( "obtainTypeReference", obtainTypeReference, wt.fc.ObjectReference.class, true );
      output.setInt( "pjtCompletion", pjtCompletion );
      output.setInt( "pjtState", pjtState );
      output.setString( "preferComp", preferComp );
      output.writeObject( "processReference", processReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "productTypeReference", productTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "rankReference", rankReference, wt.fc.ObjectReference.class, true );
      output.setString( "reviewPjtNo", reviewPjtNo );
      output.writeObject( "subGoalTypeReference", subGoalTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "templateCode", templateCode );
      output.writeObject( "templateReference", templateReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      assembledTypeReference = (wt.fc.ObjectReference) input.readObject( "assembledTypeReference", assembledTypeReference, wt.fc.ObjectReference.class, true );
      assemblyPlaceReference = (wt.fc.ObjectReference) input.readObject( "assemblyPlaceReference", assemblyPlaceReference, wt.fc.ObjectReference.class, true );
      businessReference = (wt.fc.ObjectReference) input.readObject( "businessReference", businessReference, wt.fc.ObjectReference.class, true );
      designTypeReference = (wt.fc.ObjectReference) input.readObject( "designTypeReference", designTypeReference, wt.fc.ObjectReference.class, true );
      devRequestNo = input.getString( "devRequestNo" );
      devRequestReference = (wt.fc.ObjectReference) input.readObject( "devRequestReference", devRequestReference, wt.fc.ObjectReference.class, true );
      developPatternReference = (wt.fc.ObjectReference) input.readObject( "developPatternReference", developPatternReference, wt.fc.ObjectReference.class, true );
      developedType = input.getString( "developedType" );
      developentTypeReference = (wt.fc.ObjectReference) input.readObject( "developentTypeReference", developentTypeReference, wt.fc.ObjectReference.class, true );
      mainGoalTypeReference = (wt.fc.ObjectReference) input.readObject( "mainGoalTypeReference", mainGoalTypeReference, wt.fc.ObjectReference.class, true );
      manageProductTypeReference = (wt.fc.ObjectReference) input.readObject( "manageProductTypeReference", manageProductTypeReference, wt.fc.ObjectReference.class, true );
      manufacPlace = input.getString( "manufacPlace" );
      obtainTypeReference = (wt.fc.ObjectReference) input.readObject( "obtainTypeReference", obtainTypeReference, wt.fc.ObjectReference.class, true );
      pjtCompletion = input.getInt( "pjtCompletion" );
      pjtState = input.getInt( "pjtState" );
      preferComp = input.getString( "preferComp" );
      processReference = (wt.fc.ObjectReference) input.readObject( "processReference", processReference, wt.fc.ObjectReference.class, true );
      productTypeReference = (wt.fc.ObjectReference) input.readObject( "productTypeReference", productTypeReference, wt.fc.ObjectReference.class, true );
      rankReference = (wt.fc.ObjectReference) input.readObject( "rankReference", rankReference, wt.fc.ObjectReference.class, true );
      reviewPjtNo = input.getString( "reviewPjtNo" );
      subGoalTypeReference = (wt.fc.ObjectReference) input.readObject( "subGoalTypeReference", subGoalTypeReference, wt.fc.ObjectReference.class, true );
      templateCode = input.getString( "templateCode" );
      templateReference = (wt.fc.ObjectReference) input.readObject( "templateReference", templateReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion6435009893100365663L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      assembledTypeReference = (wt.fc.ObjectReference) input.readObject();
      assemblyPlaceReference = (wt.fc.ObjectReference) input.readObject();
      businessReference = (wt.fc.ObjectReference) input.readObject();
      designTypeReference = (wt.fc.ObjectReference) input.readObject();
      devRequestNo = (java.lang.String) input.readObject();
      devRequestReference = (wt.fc.ObjectReference) input.readObject();
      developPatternReference = (wt.fc.ObjectReference) input.readObject();
      developedType = (java.lang.String) input.readObject();
      developentTypeReference = (wt.fc.ObjectReference) input.readObject();
      mainGoalTypeReference = (wt.fc.ObjectReference) input.readObject();
      manageProductTypeReference = (wt.fc.ObjectReference) input.readObject();
      manufacPlace = (java.lang.String) input.readObject();
      obtainTypeReference = (wt.fc.ObjectReference) input.readObject();
      pjtCompletion = input.readInt();
      pjtState = input.readInt();
      preferComp = (java.lang.String) input.readObject();
      processReference = (wt.fc.ObjectReference) input.readObject();
      productTypeReference = (wt.fc.ObjectReference) input.readObject();
      rankReference = (wt.fc.ObjectReference) input.readObject();
      reviewPjtNo = (java.lang.String) input.readObject();
      subGoalTypeReference = (wt.fc.ObjectReference) input.readObject();
      templateCode = (java.lang.String) input.readObject();
      templateReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( E3PSProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6435009893100365663L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_E3PSProject( _E3PSProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
