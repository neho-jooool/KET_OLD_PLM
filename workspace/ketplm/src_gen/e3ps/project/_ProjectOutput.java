package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectOutput implements e3ps.common.impl.OwnableTree, wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectOutput.class.getName();

   /**
    * 산출물 정의 이름
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_NAME = "outputName";
   static int OUTPUT_NAME_UPPER_LIMIT = -1;
   java.lang.String outputName;
   /**
    * 산출물 정의 이름
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOutputName() {
      return outputName;
   }
   /**
    * 산출물 정의 이름
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputName(java.lang.String outputName) throws wt.util.WTPropertyVetoException {
      outputNameValidate(outputName);
      this.outputName = outputName;
   }
   void outputNameValidate(java.lang.String outputName) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_NAME_UPPER_LIMIT < 1) {
         try { OUTPUT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_NAME_UPPER_LIMIT = 200; }
      }
      if (outputName != null && !wt.fc.PersistenceHelper.checkStoredLength(outputName.toString(), OUTPUT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputName"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputName", this.outputName, outputName));
   }

   /**
    * 산출물 정의 설명
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_DESC = "outputDesc";
   static int OUTPUT_DESC_UPPER_LIMIT = -1;
   java.lang.String outputDesc;
   /**
    * 산출물 정의 설명
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOutputDesc() {
      return outputDesc;
   }
   /**
    * 산출물 정의 설명
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputDesc(java.lang.String outputDesc) throws wt.util.WTPropertyVetoException {
      outputDescValidate(outputDesc);
      this.outputDesc = outputDesc;
   }
   void outputDescValidate(java.lang.String outputDesc) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_DESC_UPPER_LIMIT < 1) {
         try { OUTPUT_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_DESC_UPPER_LIMIT = 200; }
      }
      if (outputDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(outputDesc.toString(), OUTPUT_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputDesc"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputDesc", this.outputDesc, outputDesc));
   }

   /**
    * 산출물 정의 위치(문서분류체계)
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_LOCATION = "outputLocation";
   static int OUTPUT_LOCATION_UPPER_LIMIT = -1;
   java.lang.String outputLocation;
   /**
    * 산출물 정의 위치(문서분류체계)
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOutputLocation() {
      return outputLocation;
   }
   /**
    * 산출물 정의 위치(문서분류체계)
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputLocation(java.lang.String outputLocation) throws wt.util.WTPropertyVetoException {
      outputLocationValidate(outputLocation);
      this.outputLocation = outputLocation;
   }
   void outputLocationValidate(java.lang.String outputLocation) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_LOCATION_UPPER_LIMIT < 1) {
         try { OUTPUT_LOCATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputLocation").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_LOCATION_UPPER_LIMIT = 200; }
      }
      if (outputLocation != null && !wt.fc.PersistenceHelper.checkStoredLength(outputLocation.toString(), OUTPUT_LOCATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputLocation"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_LOCATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputLocation", this.outputLocation, outputLocation));
   }

   /**
    * Role
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_ROLE = "outputRole";
   static int OUTPUT_ROLE_UPPER_LIMIT = -1;
   java.lang.String outputRole;
   /**
    * Role
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOutputRole() {
      return outputRole;
   }
   /**
    * Role
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputRole(java.lang.String outputRole) throws wt.util.WTPropertyVetoException {
      outputRoleValidate(outputRole);
      this.outputRole = outputRole;
   }
   void outputRoleValidate(java.lang.String outputRole) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_ROLE_UPPER_LIMIT < 1) {
         try { OUTPUT_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_ROLE_UPPER_LIMIT = 200; }
      }
      if (outputRole != null && !wt.fc.PersistenceHelper.checkStoredLength(outputRole.toString(), OUTPUT_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputRole"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputRole", this.outputRole, outputRole));
   }

   /**
    * 산출물 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_HISTORY = "outputHistory";
   int outputHistory;
   /**
    * 산출물 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ProjectOutput
    */
   public int getOutputHistory() {
      return outputHistory;
   }
   /**
    * 산출물 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputHistory(int outputHistory) throws wt.util.WTPropertyVetoException {
      outputHistoryValidate(outputHistory);
      this.outputHistory = outputHistory;
   }
   void outputHistoryValidate(int outputHistory) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String COMPLETION = "completion";
   int completion;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public int getCompletion() {
      return completion;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setCompletion(int completion) throws wt.util.WTPropertyVetoException {
      completionValidate(completion);
      this.completion = completion;
   }
   void completionValidate(int completion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * TRUE: Primary ProjectOutput≪≫FALSE: Secondary ProjectOutput
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String IS_PRIMARY = "isPrimary";
   boolean isPrimary = false;
   /**
    * TRUE: Primary ProjectOutput≪≫FALSE: Secondary ProjectOutput
    *
    * @see e3ps.project.ProjectOutput
    */
   public boolean isIsPrimary() {
      return isPrimary;
   }
   /**
    * TRUE: Primary ProjectOutput≪≫FALSE: Secondary ProjectOutput
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setIsPrimary(boolean isPrimary) throws wt.util.WTPropertyVetoException {
      isPrimaryValidate(isPrimary);
      this.isPrimary = isPrimary;
   }
   void isPrimaryValidate(boolean isPrimary) throws wt.util.WTPropertyVetoException {
   }

   /**
    * DOC: Document≪≫DRW: EPMDocument
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OBJ_TYPE = "objType";
   static int OBJ_TYPE_UPPER_LIMIT = -1;
   java.lang.String objType;
   /**
    * DOC: Document≪≫DRW: EPMDocument
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getObjType() {
      return objType;
   }
   /**
    * DOC: Document≪≫DRW: EPMDocument
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setObjType(java.lang.String objType) throws wt.util.WTPropertyVetoException {
      objTypeValidate(objType);
      this.objType = objType;
   }
   void objTypeValidate(java.lang.String objType) throws wt.util.WTPropertyVetoException {
      if (OBJ_TYPE_UPPER_LIMIT < 1) {
         try { OBJ_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("objType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OBJ_TYPE_UPPER_LIMIT = 200; }
      }
      if (objType != null && !wt.fc.PersistenceHelper.checkStoredLength(objType.toString(), OBJ_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "objType"), java.lang.String.valueOf(java.lang.Math.min(OBJ_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "objType", this.objType, objType));
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_KEY_TYPE = "outputKeyType";
   static int OUTPUT_KEY_TYPE_UPPER_LIMIT = -1;
   java.lang.String outputKeyType;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOutputKeyType() {
      return outputKeyType;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputKeyType(java.lang.String outputKeyType) throws wt.util.WTPropertyVetoException {
      outputKeyTypeValidate(outputKeyType);
      this.outputKeyType = outputKeyType;
   }
   void outputKeyTypeValidate(java.lang.String outputKeyType) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_KEY_TYPE_UPPER_LIMIT < 1) {
         try { OUTPUT_KEY_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputKeyType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_KEY_TYPE_UPPER_LIMIT = 200; }
      }
      if (outputKeyType != null && !wt.fc.PersistenceHelper.checkStoredLength(outputKeyType.toString(), OUTPUT_KEY_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputKeyType"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_KEY_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputKeyType", this.outputKeyType, outputKeyType));
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String COMPLETE_REASON = "complete_reason";
   static int COMPLETE_REASON_UPPER_LIMIT = -1;
   java.lang.String complete_reason;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getComplete_reason() {
      return complete_reason;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setComplete_reason(java.lang.String complete_reason) throws wt.util.WTPropertyVetoException {
      complete_reasonValidate(complete_reason);
      this.complete_reason = complete_reason;
   }
   void complete_reasonValidate(java.lang.String complete_reason) throws wt.util.WTPropertyVetoException {
      if (COMPLETE_REASON_UPPER_LIMIT < 1) {
         try { COMPLETE_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("complete_reason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMPLETE_REASON_UPPER_LIMIT = 6000; }
      }
      if (complete_reason != null && !wt.fc.PersistenceHelper.checkStoredLength(complete_reason.toString(), COMPLETE_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "complete_reason"), java.lang.String.valueOf(java.lang.Math.min(COMPLETE_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "complete_reason", this.complete_reason, complete_reason));
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String ETC_COMPLETION = "etcCompletion";
   java.sql.Timestamp etcCompletion;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public java.sql.Timestamp getEtcCompletion() {
      return etcCompletion;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setEtcCompletion(java.sql.Timestamp etcCompletion) throws wt.util.WTPropertyVetoException {
      etcCompletionValidate(etcCompletion);
      this.etcCompletion = etcCompletion;
   }
   void etcCompletionValidate(java.sql.Timestamp etcCompletion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Gate대상
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String SUBJECT_TYPE = "subjectType";
   static int SUBJECT_TYPE_UPPER_LIMIT = -1;
   java.lang.String subjectType;
   /**
    * Gate대상
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getSubjectType() {
      return subjectType;
   }
   /**
    * Gate대상
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setSubjectType(java.lang.String subjectType) throws wt.util.WTPropertyVetoException {
      subjectTypeValidate(subjectType);
      this.subjectType = subjectType;
   }
   void subjectTypeValidate(java.lang.String subjectType) throws wt.util.WTPropertyVetoException {
      if (SUBJECT_TYPE_UPPER_LIMIT < 1) {
         try { SUBJECT_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subjectType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUBJECT_TYPE_UPPER_LIMIT = 200; }
      }
      if (subjectType != null && !wt.fc.PersistenceHelper.checkStoredLength(subjectType.toString(), SUBJECT_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subjectType"), java.lang.String.valueOf(java.lang.Math.min(SUBJECT_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subjectType", this.subjectType, subjectType));
   }

   /**
    * 추가점검Gate대상
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String GATE_CHECK_TYPE = "gateCheckType";
   static int GATE_CHECK_TYPE_UPPER_LIMIT = -1;
   java.lang.String gateCheckType;
   /**
    * 추가점검Gate대상
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getGateCheckType() {
      return gateCheckType;
   }
   /**
    * 추가점검Gate대상
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setGateCheckType(java.lang.String gateCheckType) throws wt.util.WTPropertyVetoException {
      gateCheckTypeValidate(gateCheckType);
      this.gateCheckType = gateCheckType;
   }
   void gateCheckTypeValidate(java.lang.String gateCheckType) throws wt.util.WTPropertyVetoException {
      if (GATE_CHECK_TYPE_UPPER_LIMIT < 1) {
         try { GATE_CHECK_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("gateCheckType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GATE_CHECK_TYPE_UPPER_LIMIT = 200; }
      }
      if (gateCheckType != null && !wt.fc.PersistenceHelper.checkStoredLength(gateCheckType.toString(), GATE_CHECK_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "gateCheckType"), java.lang.String.valueOf(java.lang.Math.min(GATE_CHECK_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "gateCheckType", this.gateCheckType, gateCheckType));
   }

   /**
    * 이전버전 Output Oid
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OLD_OUTPUT_OID = "oldOutputOid";
   static int OLD_OUTPUT_OID_UPPER_LIMIT = -1;
   java.lang.String oldOutputOid;
   /**
    * 이전버전 Output Oid
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOldOutputOid() {
      return oldOutputOid;
   }
   /**
    * 이전버전 Output Oid
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOldOutputOid(java.lang.String oldOutputOid) throws wt.util.WTPropertyVetoException {
      oldOutputOidValidate(oldOutputOid);
      this.oldOutputOid = oldOutputOid;
   }
   void oldOutputOidValidate(java.lang.String oldOutputOid) throws wt.util.WTPropertyVetoException {
      if (OLD_OUTPUT_OID_UPPER_LIMIT < 1) {
         try { OLD_OUTPUT_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("oldOutputOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OLD_OUTPUT_OID_UPPER_LIMIT = 200; }
      }
      if (oldOutputOid != null && !wt.fc.PersistenceHelper.checkStoredLength(oldOutputOid.toString(), OLD_OUTPUT_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oldOutputOid"), java.lang.String.valueOf(java.lang.Math.min(OLD_OUTPUT_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "oldOutputOid", this.oldOutputOid, oldOutputOid));
   }

   /**
    * 총원가(원) 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String TOTAL_COST = "totalCost";
   static int TOTAL_COST_UPPER_LIMIT = -1;
   java.lang.String totalCost;
   /**
    * 총원가(원) 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getTotalCost() {
      return totalCost;
   }
   /**
    * 총원가(원) 검토
    *
    * @see e3ps.project.ProjectOutput
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
    * 수익율 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String RATE = "rate";
   static int RATE_UPPER_LIMIT = -1;
   java.lang.String rate;
   /**
    * 수익율 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getRate() {
      return rate;
   }
   /**
    * 수익율 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setRate(java.lang.String rate) throws wt.util.WTPropertyVetoException {
      rateValidate(rate);
      this.rate = rate;
   }
   void rateValidate(java.lang.String rate) throws wt.util.WTPropertyVetoException {
      if (RATE_UPPER_LIMIT < 1) {
         try { RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RATE_UPPER_LIMIT = 200; }
      }
      if (rate != null && !wt.fc.PersistenceHelper.checkStoredLength(rate.toString(), RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rate"), java.lang.String.valueOf(java.lang.Math.min(RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rate", this.rate, rate));
   }

   /**
    * 비고
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String BIGO = "bigo";
   static int BIGO_UPPER_LIMIT = -1;
   java.lang.String bigo;
   /**
    * 비고
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getBigo() {
      return bigo;
   }
   /**
    * 비고
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setBigo(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      bigoValidate(bigo);
      this.bigo = bigo;
   }
   void bigoValidate(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      if (BIGO_UPPER_LIMIT < 1) {
         try { BIGO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bigo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BIGO_UPPER_LIMIT = 4000; }
      }
      if (bigo != null && !wt.fc.PersistenceHelper.checkStoredLength(bigo.toString(), BIGO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bigo"), java.lang.String.valueOf(java.lang.Math.min(BIGO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bigo", this.bigo, bigo));
   }

   /**
    * 총원가(원) 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String TOTAL_COST_FINAL = "totalCostFinal";
   static int TOTAL_COST_FINAL_UPPER_LIMIT = -1;
   java.lang.String totalCostFinal;
   /**
    * 총원가(원) 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getTotalCostFinal() {
      return totalCostFinal;
   }
   /**
    * 총원가(원) 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setTotalCostFinal(java.lang.String totalCostFinal) throws wt.util.WTPropertyVetoException {
      totalCostFinalValidate(totalCostFinal);
      this.totalCostFinal = totalCostFinal;
   }
   void totalCostFinalValidate(java.lang.String totalCostFinal) throws wt.util.WTPropertyVetoException {
      if (TOTAL_COST_FINAL_UPPER_LIMIT < 1) {
         try { TOTAL_COST_FINAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("totalCostFinal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOTAL_COST_FINAL_UPPER_LIMIT = 200; }
      }
      if (totalCostFinal != null && !wt.fc.PersistenceHelper.checkStoredLength(totalCostFinal.toString(), TOTAL_COST_FINAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "totalCostFinal"), java.lang.String.valueOf(java.lang.Math.min(TOTAL_COST_FINAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "totalCostFinal", this.totalCostFinal, totalCostFinal));
   }

   /**
    * 수익율 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String RATE_FINAL = "rateFinal";
   static int RATE_FINAL_UPPER_LIMIT = -1;
   java.lang.String rateFinal;
   /**
    * 수익율 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getRateFinal() {
      return rateFinal;
   }
   /**
    * 수익율 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setRateFinal(java.lang.String rateFinal) throws wt.util.WTPropertyVetoException {
      rateFinalValidate(rateFinal);
      this.rateFinal = rateFinal;
   }
   void rateFinalValidate(java.lang.String rateFinal) throws wt.util.WTPropertyVetoException {
      if (RATE_FINAL_UPPER_LIMIT < 1) {
         try { RATE_FINAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rateFinal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RATE_FINAL_UPPER_LIMIT = 200; }
      }
      if (rateFinal != null && !wt.fc.PersistenceHelper.checkStoredLength(rateFinal.toString(), RATE_FINAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rateFinal"), java.lang.String.valueOf(java.lang.Math.min(RATE_FINAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rateFinal", this.rateFinal, rateFinal));
   }

   /**
    * 판매목표가 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String SALES_TARGET = "salesTarget";
   static int SALES_TARGET_UPPER_LIMIT = -1;
   java.lang.String salesTarget;
   /**
    * 판매목표가 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getSalesTarget() {
      return salesTarget;
   }
   /**
    * 판매목표가 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setSalesTarget(java.lang.String salesTarget) throws wt.util.WTPropertyVetoException {
      salesTargetValidate(salesTarget);
      this.salesTarget = salesTarget;
   }
   void salesTargetValidate(java.lang.String salesTarget) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_UPPER_LIMIT < 1) {
         try { SALES_TARGET_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTarget").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_UPPER_LIMIT = 200; }
      }
      if (salesTarget != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTarget.toString(), SALES_TARGET_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTarget"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTarget", this.salesTarget, salesTarget));
   }

   /**
    * 연평균수량(EA) 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String YEAR_AVERAGE_QTY = "yearAverageQty";
   static int YEAR_AVERAGE_QTY_UPPER_LIMIT = -1;
   java.lang.String yearAverageQty;
   /**
    * 연평균수량(EA) 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getYearAverageQty() {
      return yearAverageQty;
   }
   /**
    * 연평균수량(EA) 검토
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setYearAverageQty(java.lang.String yearAverageQty) throws wt.util.WTPropertyVetoException {
      yearAverageQtyValidate(yearAverageQty);
      this.yearAverageQty = yearAverageQty;
   }
   void yearAverageQtyValidate(java.lang.String yearAverageQty) throws wt.util.WTPropertyVetoException {
      if (YEAR_AVERAGE_QTY_UPPER_LIMIT < 1) {
         try { YEAR_AVERAGE_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yearAverageQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR_AVERAGE_QTY_UPPER_LIMIT = 200; }
      }
      if (yearAverageQty != null && !wt.fc.PersistenceHelper.checkStoredLength(yearAverageQty.toString(), YEAR_AVERAGE_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yearAverageQty"), java.lang.String.valueOf(java.lang.Math.min(YEAR_AVERAGE_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yearAverageQty", this.yearAverageQty, yearAverageQty));
   }

   /**
    * 판매목표가 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String SALES_TARGET_FINAL = "salesTargetFinal";
   static int SALES_TARGET_FINAL_UPPER_LIMIT = -1;
   java.lang.String salesTargetFinal;
   /**
    * 판매목표가 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getSalesTargetFinal() {
      return salesTargetFinal;
   }
   /**
    * 판매목표가 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setSalesTargetFinal(java.lang.String salesTargetFinal) throws wt.util.WTPropertyVetoException {
      salesTargetFinalValidate(salesTargetFinal);
      this.salesTargetFinal = salesTargetFinal;
   }
   void salesTargetFinalValidate(java.lang.String salesTargetFinal) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_FINAL_UPPER_LIMIT < 1) {
         try { SALES_TARGET_FINAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTargetFinal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_FINAL_UPPER_LIMIT = 200; }
      }
      if (salesTargetFinal != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTargetFinal.toString(), SALES_TARGET_FINAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTargetFinal"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_FINAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTargetFinal", this.salesTargetFinal, salesTargetFinal));
   }

   /**
    * 연평균수량(EA) 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String YEAR_AVERAGE_QTY_FINAL = "yearAverageQtyFinal";
   static int YEAR_AVERAGE_QTY_FINAL_UPPER_LIMIT = -1;
   java.lang.String yearAverageQtyFinal;
   /**
    * 연평균수량(EA) 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getYearAverageQtyFinal() {
      return yearAverageQtyFinal;
   }
   /**
    * 연평균수량(EA) 최종
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setYearAverageQtyFinal(java.lang.String yearAverageQtyFinal) throws wt.util.WTPropertyVetoException {
      yearAverageQtyFinalValidate(yearAverageQtyFinal);
      this.yearAverageQtyFinal = yearAverageQtyFinal;
   }
   void yearAverageQtyFinalValidate(java.lang.String yearAverageQtyFinal) throws wt.util.WTPropertyVetoException {
      if (YEAR_AVERAGE_QTY_FINAL_UPPER_LIMIT < 1) {
         try { YEAR_AVERAGE_QTY_FINAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yearAverageQtyFinal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR_AVERAGE_QTY_FINAL_UPPER_LIMIT = 200; }
      }
      if (yearAverageQtyFinal != null && !wt.fc.PersistenceHelper.checkStoredLength(yearAverageQtyFinal.toString(), YEAR_AVERAGE_QTY_FINAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yearAverageQtyFinal"), java.lang.String.valueOf(java.lang.Math.min(YEAR_AVERAGE_QTY_FINAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yearAverageQtyFinal", this.yearAverageQtyFinal, yearAverageQtyFinal));
   }

   /**
    * 표준양식Oid
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_DOC_OID = "outputDocOid";
   static int OUTPUT_DOC_OID_UPPER_LIMIT = -1;
   java.lang.String outputDocOid;
   /**
    * 표준양식Oid
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOutputDocOid() {
      return outputDocOid;
   }
   /**
    * 표준양식Oid
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputDocOid(java.lang.String outputDocOid) throws wt.util.WTPropertyVetoException {
      outputDocOidValidate(outputDocOid);
      this.outputDocOid = outputDocOid;
   }
   void outputDocOidValidate(java.lang.String outputDocOid) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_DOC_OID_UPPER_LIMIT < 1) {
         try { OUTPUT_DOC_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputDocOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_DOC_OID_UPPER_LIMIT = 200; }
      }
      if (outputDocOid != null && !wt.fc.PersistenceHelper.checkStoredLength(outputDocOid.toString(), OUTPUT_DOC_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputDocOid"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_DOC_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputDocOid", this.outputDocOid, outputDocOid));
   }

   /**
    * 표준양식 이름
    *
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_DOC_NAME = "outputDocName";
   static int OUTPUT_DOC_NAME_UPPER_LIMIT = -1;
   java.lang.String outputDocName;
   /**
    * 표준양식 이름
    *
    * @see e3ps.project.ProjectOutput
    */
   public java.lang.String getOutputDocName() {
      return outputDocName;
   }
   /**
    * 표준양식 이름
    *
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputDocName(java.lang.String outputDocName) throws wt.util.WTPropertyVetoException {
      outputDocNameValidate(outputDocName);
      this.outputDocName = outputDocName;
   }
   void outputDocNameValidate(java.lang.String outputDocName) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_DOC_NAME_UPPER_LIMIT < 1) {
         try { OUTPUT_DOC_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputDocName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_DOC_NAME_UPPER_LIMIT = 200; }
      }
      if (outputDocName != null && !wt.fc.PersistenceHelper.checkStoredLength(outputDocName.toString(), OUTPUT_DOC_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputDocName"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_DOC_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputDocName", this.outputDocName, outputDocName));
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public e3ps.project.TemplateProject getProject() {
      return (projectReference != null) ? (e3ps.project.TemplateProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setProject(e3ps.project.TemplateProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.TemplateProject) the_project));
   }
   /**
    * @see e3ps.project.ProjectOutput
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
            !e3ps.project.TemplateProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String TASK = "task";
   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String TASK_REFERENCE = "taskReference";
   wt.fc.ObjectReference taskReference;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public e3ps.project.TemplateTask getTask() {
      return (taskReference != null) ? (e3ps.project.TemplateTask) taskReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public wt.fc.ObjectReference getTaskReference() {
      return taskReference;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setTask(e3ps.project.TemplateTask the_task) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTaskReference(the_task == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.TemplateTask) the_task));
   }
   /**
    * @see e3ps.project.ProjectOutput
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
            !e3ps.project.TemplateTask.class.isAssignableFrom(the_taskReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "taskReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "taskReference", this.taskReference, taskReference));
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_TYPE = "outputType";
   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String OUTPUT_TYPE_REFERENCE = "outputTypeReference";
   wt.fc.ObjectReference outputTypeReference;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public e3ps.project.outputtype.ProjectOutPutType getOutputType() {
      return (outputTypeReference != null) ? (e3ps.project.outputtype.ProjectOutPutType) outputTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public wt.fc.ObjectReference getOutputTypeReference() {
      return outputTypeReference;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputType(e3ps.project.outputtype.ProjectOutPutType the_outputType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOutputTypeReference(the_outputType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.ProjectOutPutType) the_outputType));
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setOutputTypeReference(wt.fc.ObjectReference the_outputTypeReference) throws wt.util.WTPropertyVetoException {
      outputTypeReferenceValidate(the_outputTypeReference);
      outputTypeReference = (wt.fc.ObjectReference) the_outputTypeReference;
   }
   void outputTypeReferenceValidate(wt.fc.ObjectReference the_outputTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_outputTypeReference != null && the_outputTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.ProjectOutPutType.class.isAssignableFrom(the_outputTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "outputTypeReference", this.outputTypeReference, outputTypeReference));
   }

   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String COST_DIVISION = "costDivision";
   /**
    * @see e3ps.project.ProjectOutput
    */
   public static final java.lang.String COST_DIVISION_REFERENCE = "costDivisionReference";
   wt.fc.ObjectReference costDivisionReference;
   /**
    * @see e3ps.project.ProjectOutput
    */
   public e3ps.common.code.NumberCode getCostDivision() {
      return (costDivisionReference != null) ? (e3ps.common.code.NumberCode) costDivisionReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public wt.fc.ObjectReference getCostDivisionReference() {
      return costDivisionReference;
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setCostDivision(e3ps.common.code.NumberCode the_costDivision) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostDivisionReference(the_costDivision == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_costDivision));
   }
   /**
    * @see e3ps.project.ProjectOutput
    */
   public void setCostDivisionReference(wt.fc.ObjectReference the_costDivisionReference) throws wt.util.WTPropertyVetoException {
      costDivisionReferenceValidate(the_costDivisionReference);
      costDivisionReference = (wt.fc.ObjectReference) the_costDivisionReference;
   }
   void costDivisionReferenceValidate(wt.fc.ObjectReference the_costDivisionReference) throws wt.util.WTPropertyVetoException {
      if (the_costDivisionReference != null && the_costDivisionReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_costDivisionReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costDivisionReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "costDivisionReference", this.costDivisionReference, costDivisionReference));
   }

   wt.org.WTPrincipalReference owner;
   /**
    * @see e3ps.common.impl.OwnableTree
    */
   public wt.org.WTPrincipalReference getOwner() {
      return owner;
   }
   /**
    * @see e3ps.common.impl.OwnableTree
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      ownerValidate(owner);
      this.owner = owner;
   }
   void ownerValidate(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      if (owner == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "owner") },
               new java.beans.PropertyChangeEvent(this, "owner", this.owner, owner));
   }

   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent() {
      return (parentReference != null) ? (e3ps.common.impl.Tree) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.impl.Tree) the_parent));
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParentReference(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      parentReferenceValidate(the_parentReference);
      parentReference = (wt.fc.ObjectReference) the_parentReference;
   }
   void parentReferenceValidate(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      if (the_parentReference == null || the_parentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference") },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
      if (the_parentReference != null && the_parentReference.getReferencedClass() != null &&
            !e3ps.common.impl.Tree.class.isAssignableFrom(the_parentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 314100245798333202L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( bigo );
      output.writeObject( complete_reason );
      output.writeInt( completion );
      output.writeObject( costDivisionReference );
      output.writeObject( etcCompletion );
      output.writeObject( gateCheckType );
      output.writeBoolean( isPrimary );
      output.writeObject( objType );
      output.writeObject( oldOutputOid );
      output.writeObject( outputDesc );
      output.writeObject( outputDocName );
      output.writeObject( outputDocOid );
      output.writeInt( outputHistory );
      output.writeObject( outputKeyType );
      output.writeObject( outputLocation );
      output.writeObject( outputName );
      output.writeObject( outputRole );
      output.writeObject( outputTypeReference );
      output.writeObject( owner );
      output.writeObject( parentReference );
      output.writeObject( projectReference );
      output.writeObject( rate );
      output.writeObject( rateFinal );
      output.writeObject( salesTarget );
      output.writeObject( salesTargetFinal );
      output.writeObject( subjectType );
      output.writeObject( taskReference );
      output.writeObject( thePersistInfo );
      output.writeObject( totalCost );
      output.writeObject( totalCostFinal );
      output.writeObject( yearAverageQty );
      output.writeObject( yearAverageQtyFinal );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectOutput) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "bigo", bigo );
      output.setString( "complete_reason", complete_reason );
      output.setInt( "completion", completion );
      output.writeObject( "costDivisionReference", costDivisionReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "etcCompletion", etcCompletion );
      output.setString( "gateCheckType", gateCheckType );
      output.setBoolean( "isPrimary", isPrimary );
      output.setString( "objType", objType );
      output.setString( "oldOutputOid", oldOutputOid );
      output.setString( "outputDesc", outputDesc );
      output.setString( "outputDocName", outputDocName );
      output.setString( "outputDocOid", outputDocOid );
      output.setInt( "outputHistory", outputHistory );
      output.setString( "outputKeyType", outputKeyType );
      output.setString( "outputLocation", outputLocation );
      output.setString( "outputName", outputName );
      output.setString( "outputRole", outputRole );
      output.writeObject( "outputTypeReference", outputTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "rate", rate );
      output.setString( "rateFinal", rateFinal );
      output.setString( "salesTarget", salesTarget );
      output.setString( "salesTargetFinal", salesTargetFinal );
      output.setString( "subjectType", subjectType );
      output.writeObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "totalCost", totalCost );
      output.setString( "totalCostFinal", totalCostFinal );
      output.setString( "yearAverageQty", yearAverageQty );
      output.setString( "yearAverageQtyFinal", yearAverageQtyFinal );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      bigo = input.getString( "bigo" );
      complete_reason = input.getString( "complete_reason" );
      completion = input.getInt( "completion" );
      costDivisionReference = (wt.fc.ObjectReference) input.readObject( "costDivisionReference", costDivisionReference, wt.fc.ObjectReference.class, true );
      etcCompletion = input.getTimestamp( "etcCompletion" );
      gateCheckType = input.getString( "gateCheckType" );
      isPrimary = input.getBoolean( "isPrimary" );
      objType = input.getString( "objType" );
      oldOutputOid = input.getString( "oldOutputOid" );
      outputDesc = input.getString( "outputDesc" );
      outputDocName = input.getString( "outputDocName" );
      outputDocOid = input.getString( "outputDocOid" );
      outputHistory = input.getInt( "outputHistory" );
      outputKeyType = input.getString( "outputKeyType" );
      outputLocation = input.getString( "outputLocation" );
      outputName = input.getString( "outputName" );
      outputRole = input.getString( "outputRole" );
      outputTypeReference = (wt.fc.ObjectReference) input.readObject( "outputTypeReference", outputTypeReference, wt.fc.ObjectReference.class, true );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      rate = input.getString( "rate" );
      rateFinal = input.getString( "rateFinal" );
      salesTarget = input.getString( "salesTarget" );
      salesTargetFinal = input.getString( "salesTargetFinal" );
      subjectType = input.getString( "subjectType" );
      taskReference = (wt.fc.ObjectReference) input.readObject( "taskReference", taskReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      totalCost = input.getString( "totalCost" );
      totalCostFinal = input.getString( "totalCostFinal" );
      yearAverageQty = input.getString( "yearAverageQty" );
      yearAverageQtyFinal = input.getString( "yearAverageQtyFinal" );
   }

   boolean readVersion314100245798333202L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      bigo = (java.lang.String) input.readObject();
      complete_reason = (java.lang.String) input.readObject();
      completion = input.readInt();
      costDivisionReference = (wt.fc.ObjectReference) input.readObject();
      etcCompletion = (java.sql.Timestamp) input.readObject();
      gateCheckType = (java.lang.String) input.readObject();
      isPrimary = input.readBoolean();
      objType = (java.lang.String) input.readObject();
      oldOutputOid = (java.lang.String) input.readObject();
      outputDesc = (java.lang.String) input.readObject();
      outputDocName = (java.lang.String) input.readObject();
      outputDocOid = (java.lang.String) input.readObject();
      outputHistory = input.readInt();
      outputKeyType = (java.lang.String) input.readObject();
      outputLocation = (java.lang.String) input.readObject();
      outputName = (java.lang.String) input.readObject();
      outputRole = (java.lang.String) input.readObject();
      outputTypeReference = (wt.fc.ObjectReference) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      rate = (java.lang.String) input.readObject();
      rateFinal = (java.lang.String) input.readObject();
      salesTarget = (java.lang.String) input.readObject();
      salesTargetFinal = (java.lang.String) input.readObject();
      subjectType = (java.lang.String) input.readObject();
      taskReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      totalCost = (java.lang.String) input.readObject();
      totalCostFinal = (java.lang.String) input.readObject();
      yearAverageQty = (java.lang.String) input.readObject();
      yearAverageQtyFinal = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( ProjectOutput thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion314100245798333202L( input, readSerialVersionUID, superDone );
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
