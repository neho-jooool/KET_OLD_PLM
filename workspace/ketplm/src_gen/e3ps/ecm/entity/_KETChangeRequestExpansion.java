package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETChangeRequestExpansion extends wt.enterprise.Managed implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETChangeRequestExpansion.class.getName();

   /**
    * NumberCode?? ???(?????)???CODETYPE : 'MOLDCHANGE'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String MOLD_CHANGE_CODE = "moldChangeCode";
   static int MOLD_CHANGE_CODE_UPPER_LIMIT = -1;
   java.lang.String moldChangeCode;
   /**
    * NumberCode?? ???(?????)???CODETYPE : 'MOLDCHANGE'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.String getMoldChangeCode() {
      return moldChangeCode;
   }
   /**
    * NumberCode?? ???(?????)???CODETYPE : 'MOLDCHANGE'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setMoldChangeCode(java.lang.String moldChangeCode) throws wt.util.WTPropertyVetoException {
      moldChangeCodeValidate(moldChangeCode);
      this.moldChangeCode = moldChangeCode;
   }
   void moldChangeCodeValidate(java.lang.String moldChangeCode) throws wt.util.WTPropertyVetoException {
      if (MOLD_CHANGE_CODE_UPPER_LIMIT < 1) {
         try { MOLD_CHANGE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldChangeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_CHANGE_CODE_UPPER_LIMIT = 200; }
      }
      if (moldChangeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(moldChangeCode.toString(), MOLD_CHANGE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldChangeCode"), java.lang.String.valueOf(java.lang.Math.min(MOLD_CHANGE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldChangeCode", this.moldChangeCode, moldChangeCode));
   }

   /**
    * NumberCode?? ???(????)???CODETYPE : 'COSTCHANGE'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String COST_CHANGE_CODE = "costChangeCode";
   static int COST_CHANGE_CODE_UPPER_LIMIT = -1;
   java.lang.String costChangeCode;
   /**
    * NumberCode?? ???(????)???CODETYPE : 'COSTCHANGE'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.String getCostChangeCode() {
      return costChangeCode;
   }
   /**
    * NumberCode?? ???(????)???CODETYPE : 'COSTCHANGE'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setCostChangeCode(java.lang.String costChangeCode) throws wt.util.WTPropertyVetoException {
      costChangeCodeValidate(costChangeCode);
      this.costChangeCode = costChangeCode;
   }
   void costChangeCodeValidate(java.lang.String costChangeCode) throws wt.util.WTPropertyVetoException {
      if (COST_CHANGE_CODE_UPPER_LIMIT < 1) {
         try { COST_CHANGE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costChangeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_CHANGE_CODE_UPPER_LIMIT = 200; }
      }
      if (costChangeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(costChangeCode.toString(), COST_CHANGE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costChangeCode"), java.lang.String.valueOf(java.lang.Math.min(COST_CHANGE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costChangeCode", this.costChangeCode, costChangeCode));
   }

   /**
    * NumberCode?? ???(????)???CODETYPE : 'EMERGENCYPOSITION'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String EMERGENCY_POSITION_CODE = "emergencyPositionCode";
   static int EMERGENCY_POSITION_CODE_UPPER_LIMIT = -1;
   java.lang.String emergencyPositionCode;
   /**
    * NumberCode?? ???(????)???CODETYPE : 'EMERGENCYPOSITION'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.String getEmergencyPositionCode() {
      return emergencyPositionCode;
   }
   /**
    * NumberCode?? ???(????)???CODETYPE : 'EMERGENCYPOSITION'
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setEmergencyPositionCode(java.lang.String emergencyPositionCode) throws wt.util.WTPropertyVetoException {
      emergencyPositionCodeValidate(emergencyPositionCode);
      this.emergencyPositionCode = emergencyPositionCode;
   }
   void emergencyPositionCodeValidate(java.lang.String emergencyPositionCode) throws wt.util.WTPropertyVetoException {
      if (EMERGENCY_POSITION_CODE_UPPER_LIMIT < 1) {
         try { EMERGENCY_POSITION_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("emergencyPositionCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EMERGENCY_POSITION_CODE_UPPER_LIMIT = 200; }
      }
      if (emergencyPositionCode != null && !wt.fc.PersistenceHelper.checkStoredLength(emergencyPositionCode.toString(), EMERGENCY_POSITION_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "emergencyPositionCode"), java.lang.String.valueOf(java.lang.Math.min(EMERGENCY_POSITION_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "emergencyPositionCode", this.emergencyPositionCode, emergencyPositionCode));
   }

   /**
    * OEMProjectType?? ???(????)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String CAR_TYPE_CODE = "carTypeCode";
   static int CAR_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String carTypeCode;
   /**
    * OEMProjectType?? ???(????)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.String getCarTypeCode() {
      return carTypeCode;
   }
   /**
    * OEMProjectType?? ???(????)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setCarTypeCode(java.lang.String carTypeCode) throws wt.util.WTPropertyVetoException {
      carTypeCodeValidate(carTypeCode);
      this.carTypeCode = carTypeCode;
   }
   void carTypeCodeValidate(java.lang.String carTypeCode) throws wt.util.WTPropertyVetoException {
      if (CAR_TYPE_CODE_UPPER_LIMIT < 1) {
         try { CAR_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("carTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAR_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (carTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(carTypeCode.toString(), CAR_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carTypeCode"), java.lang.String.valueOf(java.lang.Math.min(CAR_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "carTypeCode", this.carTypeCode, carTypeCode));
   }

   /**
    * ??????????(?????? ??? ??????)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String REVIEW_REQUEST_DATE = "reviewRequestDate";
   java.sql.Timestamp reviewRequestDate;
   /**
    * ??????????(?????? ??? ??????)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.sql.Timestamp getReviewRequestDate() {
      return reviewRequestDate;
   }
   /**
    * ??????????(?????? ??? ??????)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setReviewRequestDate(java.sql.Timestamp reviewRequestDate) throws wt.util.WTPropertyVetoException {
      reviewRequestDateValidate(reviewRequestDate);
      this.reviewRequestDate = reviewRequestDate;
   }
   void reviewRequestDateValidate(java.sql.Timestamp reviewRequestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ???μ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String SUBJECT_CODE = "subjectCode";
   static int SUBJECT_CODE_UPPER_LIMIT = -1;
   java.lang.String subjectCode;
   /**
    * ???μ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.String getSubjectCode() {
      return subjectCode;
   }
   /**
    * ???μ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setSubjectCode(java.lang.String subjectCode) throws wt.util.WTPropertyVetoException {
      subjectCodeValidate(subjectCode);
      this.subjectCode = subjectCode;
   }
   void subjectCodeValidate(java.lang.String subjectCode) throws wt.util.WTPropertyVetoException {
      if (SUBJECT_CODE_UPPER_LIMIT < 1) {
         try { SUBJECT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subjectCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUBJECT_CODE_UPPER_LIMIT = 200; }
      }
      if (subjectCode != null && !wt.fc.PersistenceHelper.checkStoredLength(subjectCode.toString(), SUBJECT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subjectCode"), java.lang.String.valueOf(java.lang.Math.min(SUBJECT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subjectCode", this.subjectCode, subjectCode));
   }

   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String CHARGE_NAME = "chargeName";
   static int CHARGE_NAME_UPPER_LIMIT = -1;
   java.lang.String chargeName;
   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.String getChargeName() {
      return chargeName;
   }
   /**
    * ????? ID(WTUser?? Name)
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setChargeName(java.lang.String chargeName) throws wt.util.WTPropertyVetoException {
      chargeNameValidate(chargeName);
      this.chargeName = chargeName;
   }
   void chargeNameValidate(java.lang.String chargeName) throws wt.util.WTPropertyVetoException {
      if (CHARGE_NAME_UPPER_LIMIT < 1) {
         try { CHARGE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("chargeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHARGE_NAME_UPPER_LIMIT = 200; }
      }
      if (chargeName != null && !wt.fc.PersistenceHelper.checkStoredLength(chargeName.toString(), CHARGE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chargeName"), java.lang.String.valueOf(java.lang.Math.min(CHARGE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "chargeName", this.chargeName, chargeName));
   }

   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String WEB_EDITOR1 = "webEditor1";
   java.lang.Object webEditor1;
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.Object getWebEditor1() {
      return webEditor1;
   }
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setWebEditor1(java.lang.Object webEditor1) throws wt.util.WTPropertyVetoException {
      webEditor1Validate(webEditor1);
      this.webEditor1 = webEditor1;
   }
   void webEditor1Validate(java.lang.Object webEditor1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String WEB_EDITOR_TEXT1 = "webEditorText1";
   java.lang.Object webEditorText1;
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public java.lang.Object getWebEditorText1() {
      return webEditorText1;
   }
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setWebEditorText1(java.lang.Object webEditorText1) throws wt.util.WTPropertyVetoException {
      webEditorText1Validate(webEditorText1);
      this.webEditorText1 = webEditorText1;
   }
   void webEditorText1Validate(java.lang.Object webEditorText1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String ECR = "ecr";
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String ECR_REFERENCE = "ecrReference";
   wt.fc.ObjectReference ecrReference;
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.change2.WTChangeRequest2 getEcr() {
      return (ecrReference != null) ? (wt.change2.WTChangeRequest2) ecrReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.fc.ObjectReference getEcrReference() {
      return ecrReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setEcr(wt.change2.WTChangeRequest2 the_ecr) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setEcrReference(the_ecr == null ? null : wt.fc.ObjectReference.newObjectReference((wt.change2.WTChangeRequest2) the_ecr));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setEcrReference(wt.fc.ObjectReference the_ecrReference) throws wt.util.WTPropertyVetoException {
      ecrReferenceValidate(the_ecrReference);
      ecrReference = (wt.fc.ObjectReference) the_ecrReference;
   }
   void ecrReferenceValidate(wt.fc.ObjectReference the_ecrReference) throws wt.util.WTPropertyVetoException {
      if (the_ecrReference != null && the_ecrReference.getReferencedClass() != null &&
            !wt.change2.WTChangeRequest2.class.isAssignableFrom(the_ecrReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecrReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "ecrReference", this.ecrReference, ecrReference));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String MOLD_CHANGE = "moldChange";
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String MOLD_CHANGE_REFERENCE = "moldChangeReference";
   wt.fc.ObjectReference moldChangeReference;
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public e3ps.common.code.NumberCode getMoldChange() {
      return (moldChangeReference != null) ? (e3ps.common.code.NumberCode) moldChangeReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.fc.ObjectReference getMoldChangeReference() {
      return moldChangeReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setMoldChange(e3ps.common.code.NumberCode the_moldChange) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldChangeReference(the_moldChange == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_moldChange));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setMoldChangeReference(wt.fc.ObjectReference the_moldChangeReference) throws wt.util.WTPropertyVetoException {
      moldChangeReferenceValidate(the_moldChangeReference);
      moldChangeReference = (wt.fc.ObjectReference) the_moldChangeReference;
   }
   void moldChangeReferenceValidate(wt.fc.ObjectReference the_moldChangeReference) throws wt.util.WTPropertyVetoException {
      if (the_moldChangeReference != null && the_moldChangeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_moldChangeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldChangeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldChangeReference", this.moldChangeReference, moldChangeReference));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String EMERGENCY_POSITION = "emergencyPosition";
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String EMERGENCY_POSITION_REFERENCE = "emergencyPositionReference";
   wt.fc.ObjectReference emergencyPositionReference;
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public e3ps.common.code.NumberCode getEmergencyPosition() {
      return (emergencyPositionReference != null) ? (e3ps.common.code.NumberCode) emergencyPositionReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.fc.ObjectReference getEmergencyPositionReference() {
      return emergencyPositionReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setEmergencyPosition(e3ps.common.code.NumberCode the_emergencyPosition) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setEmergencyPositionReference(the_emergencyPosition == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_emergencyPosition));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setEmergencyPositionReference(wt.fc.ObjectReference the_emergencyPositionReference) throws wt.util.WTPropertyVetoException {
      emergencyPositionReferenceValidate(the_emergencyPositionReference);
      emergencyPositionReference = (wt.fc.ObjectReference) the_emergencyPositionReference;
   }
   void emergencyPositionReferenceValidate(wt.fc.ObjectReference the_emergencyPositionReference) throws wt.util.WTPropertyVetoException {
      if (the_emergencyPositionReference != null && the_emergencyPositionReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_emergencyPositionReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "emergencyPositionReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "emergencyPositionReference", this.emergencyPositionReference, emergencyPositionReference));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String COST_CHANGE = "costChange";
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String COST_CHANGE_REFERENCE = "costChangeReference";
   wt.fc.ObjectReference costChangeReference;
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public e3ps.common.code.NumberCode getCostChange() {
      return (costChangeReference != null) ? (e3ps.common.code.NumberCode) costChangeReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.fc.ObjectReference getCostChangeReference() {
      return costChangeReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setCostChange(e3ps.common.code.NumberCode the_costChange) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostChangeReference(the_costChange == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_costChange));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setCostChangeReference(wt.fc.ObjectReference the_costChangeReference) throws wt.util.WTPropertyVetoException {
      costChangeReferenceValidate(the_costChangeReference);
      costChangeReference = (wt.fc.ObjectReference) the_costChangeReference;
   }
   void costChangeReferenceValidate(wt.fc.ObjectReference the_costChangeReference) throws wt.util.WTPropertyVetoException {
      if (the_costChangeReference != null && the_costChangeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_costChangeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costChangeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "costChangeReference", this.costChangeReference, costChangeReference));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String CAR_TYPE = "carType";
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String CAR_TYPE_REFERENCE = "carTypeReference";
   wt.fc.ObjectReference carTypeReference;
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public e3ps.project.outputtype.OEMProjectType getCarType() {
      return (carTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) carTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.fc.ObjectReference getCarTypeReference() {
      return carTypeReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setCarType(e3ps.project.outputtype.OEMProjectType the_carType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCarTypeReference(the_carType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_carType));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setCarTypeReference(wt.fc.ObjectReference the_carTypeReference) throws wt.util.WTPropertyVetoException {
      carTypeReferenceValidate(the_carTypeReference);
      carTypeReference = (wt.fc.ObjectReference) the_carTypeReference;
   }
   void carTypeReferenceValidate(wt.fc.ObjectReference the_carTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_carTypeReference != null && the_carTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_carTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "carTypeReference", this.carTypeReference, carTypeReference));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String SUBJECT = "subject";
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String SUBJECT_REFERENCE = "subjectReference";
   wt.fc.ObjectReference subjectReference;
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public e3ps.groupware.company.Department getSubject() {
      return (subjectReference != null) ? (e3ps.groupware.company.Department) subjectReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.fc.ObjectReference getSubjectReference() {
      return subjectReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setSubject(e3ps.groupware.company.Department the_subject) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setSubjectReference(the_subject == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_subject));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setSubjectReference(wt.fc.ObjectReference the_subjectReference) throws wt.util.WTPropertyVetoException {
      subjectReferenceValidate(the_subjectReference);
      subjectReference = (wt.fc.ObjectReference) the_subjectReference;
   }
   void subjectReferenceValidate(wt.fc.ObjectReference the_subjectReference) throws wt.util.WTPropertyVetoException {
      if (the_subjectReference != null && the_subjectReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_subjectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subjectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "subjectReference", this.subjectReference, subjectReference));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String CHARGE = "charge";
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public static final java.lang.String CHARGE_REFERENCE = "chargeReference";
   wt.fc.ObjectReference chargeReference;
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.org.WTUser getCharge() {
      return (chargeReference != null) ? (wt.org.WTUser) chargeReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public wt.fc.ObjectReference getChargeReference() {
      return chargeReference;
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setCharge(wt.org.WTUser the_charge) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setChargeReference(the_charge == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_charge));
   }
   /**
    * @see e3ps.ecm.entity.KETChangeRequestExpansion
    */
   public void setChargeReference(wt.fc.ObjectReference the_chargeReference) throws wt.util.WTPropertyVetoException {
      chargeReferenceValidate(the_chargeReference);
      chargeReference = (wt.fc.ObjectReference) the_chargeReference;
   }
   void chargeReferenceValidate(wt.fc.ObjectReference the_chargeReference) throws wt.util.WTPropertyVetoException {
      if (the_chargeReference != null && the_chargeReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_chargeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chargeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "chargeReference", this.chargeReference, chargeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 4306514362018777669L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( carTypeCode );
      output.writeObject( carTypeReference );
      output.writeObject( chargeName );
      output.writeObject( chargeReference );
      output.writeObject( containerReference );
      output.writeObject( costChangeCode );
      output.writeObject( costChangeReference );
      output.writeObject( ecrReference );
      output.writeObject( emergencyPositionCode );
      output.writeObject( emergencyPositionReference );
      output.writeObject( moldChangeCode );
      output.writeObject( moldChangeReference );
      output.writeObject( reviewRequestDate );
      output.writeObject( subjectCode );
      output.writeObject( subjectReference );
      output.writeObject( webEditor );
      output.writeObject( webEditor1 );
      output.writeObject( webEditorText );
      output.writeObject( webEditorText1 );
   }

   protected void super_writeExternal_KETChangeRequestExpansion(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETChangeRequestExpansion) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETChangeRequestExpansion(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "carTypeCode", carTypeCode );
      output.writeObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "chargeName", chargeName );
      output.writeObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "costChangeCode", costChangeCode );
      output.writeObject( "costChangeReference", costChangeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "ecrReference", ecrReference, wt.fc.ObjectReference.class, true );
      output.setString( "emergencyPositionCode", emergencyPositionCode );
      output.writeObject( "emergencyPositionReference", emergencyPositionReference, wt.fc.ObjectReference.class, true );
      output.setString( "moldChangeCode", moldChangeCode );
      output.writeObject( "moldChangeReference", moldChangeReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "reviewRequestDate", reviewRequestDate );
      output.setString( "subjectCode", subjectCode );
      output.writeObject( "subjectReference", subjectReference, wt.fc.ObjectReference.class, true );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditor1", webEditor1 );
      output.setObject( "webEditorText", webEditorText );
      output.setObject( "webEditorText1", webEditorText1 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      carTypeCode = input.getString( "carTypeCode" );
      carTypeReference = (wt.fc.ObjectReference) input.readObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      chargeName = input.getString( "chargeName" );
      chargeReference = (wt.fc.ObjectReference) input.readObject( "chargeReference", chargeReference, wt.fc.ObjectReference.class, true );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      costChangeCode = input.getString( "costChangeCode" );
      costChangeReference = (wt.fc.ObjectReference) input.readObject( "costChangeReference", costChangeReference, wt.fc.ObjectReference.class, true );
      ecrReference = (wt.fc.ObjectReference) input.readObject( "ecrReference", ecrReference, wt.fc.ObjectReference.class, true );
      emergencyPositionCode = input.getString( "emergencyPositionCode" );
      emergencyPositionReference = (wt.fc.ObjectReference) input.readObject( "emergencyPositionReference", emergencyPositionReference, wt.fc.ObjectReference.class, true );
      moldChangeCode = input.getString( "moldChangeCode" );
      moldChangeReference = (wt.fc.ObjectReference) input.readObject( "moldChangeReference", moldChangeReference, wt.fc.ObjectReference.class, true );
      reviewRequestDate = input.getTimestamp( "reviewRequestDate" );
      subjectCode = input.getString( "subjectCode" );
      subjectReference = (wt.fc.ObjectReference) input.readObject( "subjectReference", subjectReference, wt.fc.ObjectReference.class, true );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditor1 = (java.lang.Object) input.getObject( "webEditor1" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
      webEditorText1 = (java.lang.Object) input.getObject( "webEditorText1" );
   }

   boolean readVersion4306514362018777669L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      carTypeCode = (java.lang.String) input.readObject();
      carTypeReference = (wt.fc.ObjectReference) input.readObject();
      chargeName = (java.lang.String) input.readObject();
      chargeReference = (wt.fc.ObjectReference) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      costChangeCode = (java.lang.String) input.readObject();
      costChangeReference = (wt.fc.ObjectReference) input.readObject();
      ecrReference = (wt.fc.ObjectReference) input.readObject();
      emergencyPositionCode = (java.lang.String) input.readObject();
      emergencyPositionReference = (wt.fc.ObjectReference) input.readObject();
      moldChangeCode = (java.lang.String) input.readObject();
      moldChangeReference = (wt.fc.ObjectReference) input.readObject();
      reviewRequestDate = (java.sql.Timestamp) input.readObject();
      subjectCode = (java.lang.String) input.readObject();
      subjectReference = (wt.fc.ObjectReference) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditor1 = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();
      webEditorText1 = (java.lang.Object) input.readObject();
      return true;
   }

   protected boolean readVersion( KETChangeRequestExpansion thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4306514362018777669L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETChangeRequestExpansion( _KETChangeRequestExpansion thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
