package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProdChangeOrder extends wt.change2.WTChangeOrder2 implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProdChangeOrder.class.getName();

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ECO_ID = "ecoId";
   static int ECO_ID_UPPER_LIMIT = -1;
   java.lang.String ecoId;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getEcoId() {
      return ecoId;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setEcoId(java.lang.String ecoId) throws wt.util.WTPropertyVetoException {
      ecoIdValidate(ecoId);
      this.ecoId = ecoId;
   }
   void ecoIdValidate(java.lang.String ecoId) throws wt.util.WTPropertyVetoException {
      if (ECO_ID_UPPER_LIMIT < 1) {
         try { ECO_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_ID_UPPER_LIMIT = 200; }
      }
      if (ecoId != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoId.toString(), ECO_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoId"), java.lang.String.valueOf(java.lang.Math.min(ECO_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoId", this.ecoId, ecoId));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ECO_NAME = "ecoName";
   static int ECO_NAME_UPPER_LIMIT = -1;
   java.lang.String ecoName;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getEcoName() {
      return ecoName;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setEcoName(java.lang.String ecoName) throws wt.util.WTPropertyVetoException {
      ecoNameValidate(ecoName);
      this.ecoName = ecoName;
   }
   void ecoNameValidate(java.lang.String ecoName) throws wt.util.WTPropertyVetoException {
      if (ECO_NAME_UPPER_LIMIT < 1) {
         try { ECO_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_NAME_UPPER_LIMIT = 200; }
      }
      if (ecoName != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoName.toString(), ECO_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoName"), java.lang.String.valueOf(java.lang.Math.min(ECO_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoName", this.ecoName, ecoName));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DEV_YN = "devYn";
   static int DEV_YN_UPPER_LIMIT = -1;
   java.lang.String devYn;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDevYn() {
      return devYn;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDevYn(java.lang.String devYn) throws wt.util.WTPropertyVetoException {
      devYnValidate(devYn);
      this.devYn = devYn;
   }
   void devYnValidate(java.lang.String devYn) throws wt.util.WTPropertyVetoException {
      if (DEV_YN_UPPER_LIMIT < 1) {
         try { DEV_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_YN_UPPER_LIMIT = 200; }
      }
      if (devYn != null && !wt.fc.PersistenceHelper.checkStoredLength(devYn.toString(), DEV_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devYn"), java.lang.String.valueOf(java.lang.Math.min(DEV_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devYn", this.devYn, devYn));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DIVISION_FLAG = "divisionFlag";
   static int DIVISION_FLAG_UPPER_LIMIT = -1;
   java.lang.String divisionFlag;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDivisionFlag() {
      return divisionFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDivisionFlag(java.lang.String divisionFlag) throws wt.util.WTPropertyVetoException {
      divisionFlagValidate(divisionFlag);
      this.divisionFlag = divisionFlag;
   }
   void divisionFlagValidate(java.lang.String divisionFlag) throws wt.util.WTPropertyVetoException {
      if (DIVISION_FLAG_UPPER_LIMIT < 1) {
         try { DIVISION_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("divisionFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIVISION_FLAG_UPPER_LIMIT = 200; }
      }
      if (divisionFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(divisionFlag.toString(), DIVISION_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "divisionFlag"), java.lang.String.valueOf(java.lang.Math.min(DIVISION_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "divisionFlag", this.divisionFlag, divisionFlag));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String PROJECT_OID = "projectOid";
   static int PROJECT_OID_UPPER_LIMIT = -1;
   java.lang.String projectOid;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getProjectOid() {
      return projectOid;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setProjectOid(java.lang.String projectOid) throws wt.util.WTPropertyVetoException {
      projectOidValidate(projectOid);
      this.projectOid = projectOid;
   }
   void projectOidValidate(java.lang.String projectOid) throws wt.util.WTPropertyVetoException {
      if (PROJECT_OID_UPPER_LIMIT < 1) {
         try { PROJECT_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_OID_UPPER_LIMIT = 200; }
      }
      if (projectOid != null && !wt.fc.PersistenceHelper.checkStoredLength(projectOid.toString(), PROJECT_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectOid"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectOid", this.projectOid, projectOid));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String CHANGE_REASON = "changeReason";
   static int CHANGE_REASON_UPPER_LIMIT = -1;
   java.lang.String changeReason;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getChangeReason() {
      return changeReason;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setChangeReason(java.lang.String changeReason) throws wt.util.WTPropertyVetoException {
      changeReasonValidate(changeReason);
      this.changeReason = changeReason;
   }
   void changeReasonValidate(java.lang.String changeReason) throws wt.util.WTPropertyVetoException {
      if (CHANGE_REASON_UPPER_LIMIT < 1) {
         try { CHANGE_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_REASON_UPPER_LIMIT = 200; }
      }
      if (changeReason != null && !wt.fc.PersistenceHelper.checkStoredLength(changeReason.toString(), CHANGE_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeReason"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeReason", this.changeReason, changeReason));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String OTHER_CHANGE_REASON = "otherChangeReason";
   static int OTHER_CHANGE_REASON_UPPER_LIMIT = -1;
   java.lang.String otherChangeReason;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getOtherChangeReason() {
      return otherChangeReason;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setOtherChangeReason(java.lang.String otherChangeReason) throws wt.util.WTPropertyVetoException {
      otherChangeReasonValidate(otherChangeReason);
      this.otherChangeReason = otherChangeReason;
   }
   void otherChangeReasonValidate(java.lang.String otherChangeReason) throws wt.util.WTPropertyVetoException {
      if (OTHER_CHANGE_REASON_UPPER_LIMIT < 1) {
         try { OTHER_CHANGE_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("otherChangeReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OTHER_CHANGE_REASON_UPPER_LIMIT = 200; }
      }
      if (otherChangeReason != null && !wt.fc.PersistenceHelper.checkStoredLength(otherChangeReason.toString(), OTHER_CHANGE_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "otherChangeReason"), java.lang.String.valueOf(java.lang.Math.min(OTHER_CHANGE_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "otherChangeReason", this.otherChangeReason, otherChangeReason));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String CUSTORMER_FLAG = "custormerFlag";
   static int CUSTORMER_FLAG_UPPER_LIMIT = -1;
   java.lang.String custormerFlag;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getCustormerFlag() {
      return custormerFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setCustormerFlag(java.lang.String custormerFlag) throws wt.util.WTPropertyVetoException {
      custormerFlagValidate(custormerFlag);
      this.custormerFlag = custormerFlag;
   }
   void custormerFlagValidate(java.lang.String custormerFlag) throws wt.util.WTPropertyVetoException {
      if (CUSTORMER_FLAG_UPPER_LIMIT < 1) {
         try { CUSTORMER_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("custormerFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTORMER_FLAG_UPPER_LIMIT = 200; }
      }
      if (custormerFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(custormerFlag.toString(), CUSTORMER_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "custormerFlag"), java.lang.String.valueOf(java.lang.Math.min(CUSTORMER_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "custormerFlag", this.custormerFlag, custormerFlag));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String OTHER_CUST_FLAG_DESC = "otherCustFlagDesc";
   static int OTHER_CUST_FLAG_DESC_UPPER_LIMIT = -1;
   java.lang.String otherCustFlagDesc;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getOtherCustFlagDesc() {
      return otherCustFlagDesc;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setOtherCustFlagDesc(java.lang.String otherCustFlagDesc) throws wt.util.WTPropertyVetoException {
      otherCustFlagDescValidate(otherCustFlagDesc);
      this.otherCustFlagDesc = otherCustFlagDesc;
   }
   void otherCustFlagDescValidate(java.lang.String otherCustFlagDesc) throws wt.util.WTPropertyVetoException {
      if (OTHER_CUST_FLAG_DESC_UPPER_LIMIT < 1) {
         try { OTHER_CUST_FLAG_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("otherCustFlagDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OTHER_CUST_FLAG_DESC_UPPER_LIMIT = 200; }
      }
      if (otherCustFlagDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(otherCustFlagDesc.toString(), OTHER_CUST_FLAG_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "otherCustFlagDesc"), java.lang.String.valueOf(java.lang.Math.min(OTHER_CUST_FLAG_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "otherCustFlagDesc", this.otherCustFlagDesc, otherCustFlagDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String CHANGE_FLAG = "changeFlag";
   static int CHANGE_FLAG_UPPER_LIMIT = -1;
   java.lang.String changeFlag;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getChangeFlag() {
      return changeFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setChangeFlag(java.lang.String changeFlag) throws wt.util.WTPropertyVetoException {
      changeFlagValidate(changeFlag);
      this.changeFlag = changeFlag;
   }
   void changeFlagValidate(java.lang.String changeFlag) throws wt.util.WTPropertyVetoException {
      if (CHANGE_FLAG_UPPER_LIMIT < 1) {
         try { CHANGE_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_FLAG_UPPER_LIMIT = 200; }
      }
      if (changeFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(changeFlag.toString(), CHANGE_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeFlag"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeFlag", this.changeFlag, changeFlag));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String EFFECTIVE_DATE_FLAG = "effectiveDateFlag";
   static int EFFECTIVE_DATE_FLAG_UPPER_LIMIT = -1;
   java.lang.String effectiveDateFlag;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getEffectiveDateFlag() {
      return effectiveDateFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setEffectiveDateFlag(java.lang.String effectiveDateFlag) throws wt.util.WTPropertyVetoException {
      effectiveDateFlagValidate(effectiveDateFlag);
      this.effectiveDateFlag = effectiveDateFlag;
   }
   void effectiveDateFlagValidate(java.lang.String effectiveDateFlag) throws wt.util.WTPropertyVetoException {
      if (EFFECTIVE_DATE_FLAG_UPPER_LIMIT < 1) {
         try { EFFECTIVE_DATE_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("effectiveDateFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EFFECTIVE_DATE_FLAG_UPPER_LIMIT = 200; }
      }
      if (effectiveDateFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(effectiveDateFlag.toString(), EFFECTIVE_DATE_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "effectiveDateFlag"), java.lang.String.valueOf(java.lang.Math.min(EFFECTIVE_DATE_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "effectiveDateFlag", this.effectiveDateFlag, effectiveDateFlag));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String INVENTORY_CLEAR = "inventoryClear";
   static int INVENTORY_CLEAR_UPPER_LIMIT = -1;
   java.lang.String inventoryClear;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getInventoryClear() {
      return inventoryClear;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setInventoryClear(java.lang.String inventoryClear) throws wt.util.WTPropertyVetoException {
      inventoryClearValidate(inventoryClear);
      this.inventoryClear = inventoryClear;
   }
   void inventoryClearValidate(java.lang.String inventoryClear) throws wt.util.WTPropertyVetoException {
      if (INVENTORY_CLEAR_UPPER_LIMIT < 1) {
         try { INVENTORY_CLEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inventoryClear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INVENTORY_CLEAR_UPPER_LIMIT = 200; }
      }
      if (inventoryClear != null && !wt.fc.PersistenceHelper.checkStoredLength(inventoryClear.toString(), INVENTORY_CLEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inventoryClear"), java.lang.String.valueOf(java.lang.Math.min(INVENTORY_CLEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inventoryClear", this.inventoryClear, inventoryClear));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String CU_DRAWING_CHANGE_YN = "cuDrawingChangeYn";
   static int CU_DRAWING_CHANGE_YN_UPPER_LIMIT = -1;
   java.lang.String cuDrawingChangeYn;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getCuDrawingChangeYn() {
      return cuDrawingChangeYn;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setCuDrawingChangeYn(java.lang.String cuDrawingChangeYn) throws wt.util.WTPropertyVetoException {
      cuDrawingChangeYnValidate(cuDrawingChangeYn);
      this.cuDrawingChangeYn = cuDrawingChangeYn;
   }
   void cuDrawingChangeYnValidate(java.lang.String cuDrawingChangeYn) throws wt.util.WTPropertyVetoException {
      if (CU_DRAWING_CHANGE_YN_UPPER_LIMIT < 1) {
         try { CU_DRAWING_CHANGE_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cuDrawingChangeYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CU_DRAWING_CHANGE_YN_UPPER_LIMIT = 200; }
      }
      if (cuDrawingChangeYn != null && !wt.fc.PersistenceHelper.checkStoredLength(cuDrawingChangeYn.toString(), CU_DRAWING_CHANGE_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cuDrawingChangeYn"), java.lang.String.valueOf(java.lang.Math.min(CU_DRAWING_CHANGE_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cuDrawingChangeYn", this.cuDrawingChangeYn, cuDrawingChangeYn));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDeptName(java.lang.String deptName) throws wt.util.WTPropertyVetoException {
      deptNameValidate(deptName);
      this.deptName = deptName;
   }
   void deptNameValidate(java.lang.String deptName) throws wt.util.WTPropertyVetoException {
      if (DEPT_NAME_UPPER_LIMIT < 1) {
         try { DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (deptName != null && !wt.fc.PersistenceHelper.checkStoredLength(deptName.toString(), DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptName"), java.lang.String.valueOf(java.lang.Math.min(DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptName", this.deptName, deptName));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DOMESTIC_YN = "domesticYn";
   static int DOMESTIC_YN_UPPER_LIMIT = -1;
   java.lang.String domesticYn;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDomesticYn() {
      return domesticYn;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDomesticYn(java.lang.String domesticYn) throws wt.util.WTPropertyVetoException {
      domesticYnValidate(domesticYn);
      this.domesticYn = domesticYn;
   }
   void domesticYnValidate(java.lang.String domesticYn) throws wt.util.WTPropertyVetoException {
      if (DOMESTIC_YN_UPPER_LIMIT < 1) {
         try { DOMESTIC_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("domesticYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DOMESTIC_YN_UPPER_LIMIT = 200; }
      }
      if (domesticYn != null && !wt.fc.PersistenceHelper.checkStoredLength(domesticYn.toString(), DOMESTIC_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "domesticYn"), java.lang.String.valueOf(java.lang.Math.min(DOMESTIC_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "domesticYn", this.domesticYn, domesticYn));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String CAR_MAKER = "carMaker";
   static int CAR_MAKER_UPPER_LIMIT = -1;
   java.lang.String carMaker;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getCarMaker() {
      return carMaker;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setCarMaker(java.lang.String carMaker) throws wt.util.WTPropertyVetoException {
      carMakerValidate(carMaker);
      this.carMaker = carMaker;
   }
   void carMakerValidate(java.lang.String carMaker) throws wt.util.WTPropertyVetoException {
      if (CAR_MAKER_UPPER_LIMIT < 1) {
         try { CAR_MAKER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("carMaker").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAR_MAKER_UPPER_LIMIT = 200; }
      }
      if (carMaker != null && !wt.fc.PersistenceHelper.checkStoredLength(carMaker.toString(), CAR_MAKER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carMaker"), java.lang.String.valueOf(java.lang.Math.min(CAR_MAKER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "carMaker", this.carMaker, carMaker));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String CAR_CATEGORY = "carCategory";
   static int CAR_CATEGORY_UPPER_LIMIT = -1;
   java.lang.String carCategory;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getCarCategory() {
      return carCategory;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setCarCategory(java.lang.String carCategory) throws wt.util.WTPropertyVetoException {
      carCategoryValidate(carCategory);
      this.carCategory = carCategory;
   }
   void carCategoryValidate(java.lang.String carCategory) throws wt.util.WTPropertyVetoException {
      if (CAR_CATEGORY_UPPER_LIMIT < 1) {
         try { CAR_CATEGORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("carCategory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAR_CATEGORY_UPPER_LIMIT = 200; }
      }
      if (carCategory != null && !wt.fc.PersistenceHelper.checkStoredLength(carCategory.toString(), CAR_CATEGORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carCategory"), java.lang.String.valueOf(java.lang.Math.min(CAR_CATEGORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "carCategory", this.carCategory, carCategory));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute1(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      attribute1Validate(attribute1);
      this.attribute1 = attribute1;
   }
   void attribute1Validate(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE1_UPPER_LIMIT < 1) {
         try { ATTRIBUTE1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE1_UPPER_LIMIT = 200; }
      }
      if (attribute1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute1.toString(), ATTRIBUTE1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute1"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute1", this.attribute1, attribute1));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute2(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      attribute2Validate(attribute2);
      this.attribute2 = attribute2;
   }
   void attribute2Validate(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE2_UPPER_LIMIT < 1) {
         try { ATTRIBUTE2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE2_UPPER_LIMIT = 200; }
      }
      if (attribute2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute2.toString(), ATTRIBUTE2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute2"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute2", this.attribute2, attribute2));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute3(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      attribute3Validate(attribute3);
      this.attribute3 = attribute3;
   }
   void attribute3Validate(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE3_UPPER_LIMIT < 1) {
         try { ATTRIBUTE3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE3_UPPER_LIMIT = 200; }
      }
      if (attribute3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute3.toString(), ATTRIBUTE3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute3"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute3", this.attribute3, attribute3));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute4(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      attribute4Validate(attribute4);
      this.attribute4 = attribute4;
   }
   void attribute4Validate(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE4_UPPER_LIMIT < 1) {
         try { ATTRIBUTE4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE4_UPPER_LIMIT = 200; }
      }
      if (attribute4 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute4.toString(), ATTRIBUTE4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute4"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute4", this.attribute4, attribute4));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute5(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      attribute5Validate(attribute5);
      this.attribute5 = attribute5;
   }
   void attribute5Validate(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE5_UPPER_LIMIT < 1) {
         try { ATTRIBUTE5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE5_UPPER_LIMIT = 200; }
      }
      if (attribute5 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute5.toString(), ATTRIBUTE5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute5"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute5", this.attribute5, attribute5));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute6(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      attribute6Validate(attribute6);
      this.attribute6 = attribute6;
   }
   void attribute6Validate(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE6_UPPER_LIMIT < 1) {
         try { ATTRIBUTE6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE6_UPPER_LIMIT = 200; }
      }
      if (attribute6 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute6.toString(), ATTRIBUTE6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute6"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute6", this.attribute6, attribute6));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute7(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      attribute7Validate(attribute7);
      this.attribute7 = attribute7;
   }
   void attribute7Validate(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE7_UPPER_LIMIT < 1) {
         try { ATTRIBUTE7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE7_UPPER_LIMIT = 200; }
      }
      if (attribute7 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute7.toString(), ATTRIBUTE7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute7"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute7", this.attribute7, attribute7));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute8(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      attribute8Validate(attribute8);
      this.attribute8 = attribute8;
   }
   void attribute8Validate(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE8_UPPER_LIMIT < 1) {
         try { ATTRIBUTE8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE8_UPPER_LIMIT = 200; }
      }
      if (attribute8 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute8.toString(), ATTRIBUTE8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute8"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute8", this.attribute8, attribute8));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute9(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      attribute9Validate(attribute9);
      this.attribute9 = attribute9;
   }
   void attribute9Validate(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE9_UPPER_LIMIT < 1) {
         try { ATTRIBUTE9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE9_UPPER_LIMIT = 200; }
      }
      if (attribute9 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute9.toString(), ATTRIBUTE9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute9"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute9", this.attribute9, attribute9));
   }

   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setAttribute10(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      attribute10Validate(attribute10);
      this.attribute10 = attribute10;
   }
   void attribute10Validate(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE10_UPPER_LIMIT < 1) {
         try { ATTRIBUTE10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE10_UPPER_LIMIT = 200; }
      }
      if (attribute10 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute10.toString(), ATTRIBUTE10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute10"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute10", this.attribute10, attribute10));
   }

   /**
    * 설계변경 상세사유
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String CHANGE_TYPE = "changeType";
   static int CHANGE_TYPE_UPPER_LIMIT = -1;
   java.lang.String changeType;
   /**
    * 설계변경 상세사유
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getChangeType() {
      return changeType;
   }
   /**
    * 설계변경 상세사유
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setChangeType(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      changeTypeValidate(changeType);
      this.changeType = changeType;
   }
   void changeTypeValidate(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      if (CHANGE_TYPE_UPPER_LIMIT < 1) {
         try { CHANGE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_TYPE_UPPER_LIMIT = 1024; }
      }
      if (changeType != null && !wt.fc.PersistenceHelper.checkStoredLength(changeType.toString(), CHANGE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeType"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeType", this.changeType, changeType));
   }

   /**
    * 검토결과
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String REVIEW_RESULT = "reviewResult";
   static int REVIEW_RESULT_UPPER_LIMIT = -1;
   java.lang.String reviewResult;
   /**
    * 검토결과
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getReviewResult() {
      return reviewResult;
   }
   /**
    * 검토결과
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setReviewResult(java.lang.String reviewResult) throws wt.util.WTPropertyVetoException {
      reviewResultValidate(reviewResult);
      this.reviewResult = reviewResult;
   }
   void reviewResultValidate(java.lang.String reviewResult) throws wt.util.WTPropertyVetoException {
      if (REVIEW_RESULT_UPPER_LIMIT < 1) {
         try { REVIEW_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_RESULT_UPPER_LIMIT = 200; }
      }
      if (reviewResult != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewResult.toString(), REVIEW_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewResult"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewResult", this.reviewResult, reviewResult));
   }

   /**
    * 설계가이드 반영
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DESIGN_GUIDE_YN = "designGuideYn";
   static int DESIGN_GUIDE_YN_UPPER_LIMIT = -1;
   java.lang.String designGuideYn;
   /**
    * 설계가이드 반영
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDesignGuideYn() {
      return designGuideYn;
   }
   /**
    * 설계가이드 반영
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDesignGuideYn(java.lang.String designGuideYn) throws wt.util.WTPropertyVetoException {
      designGuideYnValidate(designGuideYn);
      this.designGuideYn = designGuideYn;
   }
   void designGuideYnValidate(java.lang.String designGuideYn) throws wt.util.WTPropertyVetoException {
      if (DESIGN_GUIDE_YN_UPPER_LIMIT < 1) {
         try { DESIGN_GUIDE_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("designGuideYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESIGN_GUIDE_YN_UPPER_LIMIT = 200; }
      }
      if (designGuideYn != null && !wt.fc.PersistenceHelper.checkStoredLength(designGuideYn.toString(), DESIGN_GUIDE_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "designGuideYn"), java.lang.String.valueOf(java.lang.Math.min(DESIGN_GUIDE_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "designGuideYn", this.designGuideYn, designGuideYn));
   }

   /**
    * 설계검증체크시트 반영
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DESIGN_CHECKSHEET_YN = "designChecksheetYn";
   static int DESIGN_CHECKSHEET_YN_UPPER_LIMIT = -1;
   java.lang.String designChecksheetYn;
   /**
    * 설계검증체크시트 반영
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDesignChecksheetYn() {
      return designChecksheetYn;
   }
   /**
    * 설계검증체크시트 반영
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDesignChecksheetYn(java.lang.String designChecksheetYn) throws wt.util.WTPropertyVetoException {
      designChecksheetYnValidate(designChecksheetYn);
      this.designChecksheetYn = designChecksheetYn;
   }
   void designChecksheetYnValidate(java.lang.String designChecksheetYn) throws wt.util.WTPropertyVetoException {
      if (DESIGN_CHECKSHEET_YN_UPPER_LIMIT < 1) {
         try { DESIGN_CHECKSHEET_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("designChecksheetYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESIGN_CHECKSHEET_YN_UPPER_LIMIT = 200; }
      }
      if (designChecksheetYn != null && !wt.fc.PersistenceHelper.checkStoredLength(designChecksheetYn.toString(), DESIGN_CHECKSHEET_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "designChecksheetYn"), java.lang.String.valueOf(java.lang.Math.min(DESIGN_CHECKSHEET_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "designChecksheetYn", this.designChecksheetYn, designChecksheetYn));
   }

   /**
    * 불량구분코드
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DEFECT_DIV_CODE = "defectDivCode";
   static int DEFECT_DIV_CODE_UPPER_LIMIT = -1;
   java.lang.String defectDivCode;
   /**
    * 불량구분코드
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDefectDivCode() {
      return defectDivCode;
   }
   /**
    * 불량구분코드
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDefectDivCode(java.lang.String defectDivCode) throws wt.util.WTPropertyVetoException {
      defectDivCodeValidate(defectDivCode);
      this.defectDivCode = defectDivCode;
   }
   void defectDivCodeValidate(java.lang.String defectDivCode) throws wt.util.WTPropertyVetoException {
      if (DEFECT_DIV_CODE_UPPER_LIMIT < 1) {
         try { DEFECT_DIV_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectDivCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_DIV_CODE_UPPER_LIMIT = 200; }
      }
      if (defectDivCode != null && !wt.fc.PersistenceHelper.checkStoredLength(defectDivCode.toString(), DEFECT_DIV_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectDivCode"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_DIV_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectDivCode", this.defectDivCode, defectDivCode));
   }

   /**
    * 불량구분
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DEFECT_DIV_NAME = "defectDivName";
   static int DEFECT_DIV_NAME_UPPER_LIMIT = -1;
   java.lang.String defectDivName;
   /**
    * 불량구분
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDefectDivName() {
      return defectDivName;
   }
   /**
    * 불량구분
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDefectDivName(java.lang.String defectDivName) throws wt.util.WTPropertyVetoException {
      defectDivNameValidate(defectDivName);
      this.defectDivName = defectDivName;
   }
   void defectDivNameValidate(java.lang.String defectDivName) throws wt.util.WTPropertyVetoException {
      if (DEFECT_DIV_NAME_UPPER_LIMIT < 1) {
         try { DEFECT_DIV_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectDivName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_DIV_NAME_UPPER_LIMIT = 200; }
      }
      if (defectDivName != null && !wt.fc.PersistenceHelper.checkStoredLength(defectDivName.toString(), DEFECT_DIV_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectDivName"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_DIV_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectDivName", this.defectDivName, defectDivName));
   }

   /**
    * 불량유형코드
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DEFECT_TYPE_CODE = "defectTypeCode";
   static int DEFECT_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String defectTypeCode;
   /**
    * 불량유형코드
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDefectTypeCode() {
      return defectTypeCode;
   }
   /**
    * 불량유형코드
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDefectTypeCode(java.lang.String defectTypeCode) throws wt.util.WTPropertyVetoException {
      defectTypeCodeValidate(defectTypeCode);
      this.defectTypeCode = defectTypeCode;
   }
   void defectTypeCodeValidate(java.lang.String defectTypeCode) throws wt.util.WTPropertyVetoException {
      if (DEFECT_TYPE_CODE_UPPER_LIMIT < 1) {
         try { DEFECT_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (defectTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(defectTypeCode.toString(), DEFECT_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectTypeCode"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectTypeCode", this.defectTypeCode, defectTypeCode));
   }

   /**
    * 불량유형
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String DEFECT_TYPE_NAME = "defectTypeName";
   static int DEFECT_TYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String defectTypeName;
   /**
    * 불량유형
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getDefectTypeName() {
      return defectTypeName;
   }
   /**
    * 불량유형
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setDefectTypeName(java.lang.String defectTypeName) throws wt.util.WTPropertyVetoException {
      defectTypeNameValidate(defectTypeName);
      this.defectTypeName = defectTypeName;
   }
   void defectTypeNameValidate(java.lang.String defectTypeName) throws wt.util.WTPropertyVetoException {
      if (DEFECT_TYPE_NAME_UPPER_LIMIT < 1) {
         try { DEFECT_TYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectTypeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_TYPE_NAME_UPPER_LIMIT = 200; }
      }
      if (defectTypeName != null && !wt.fc.PersistenceHelper.checkStoredLength(defectTypeName.toString(), DEFECT_TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectTypeName"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectTypeName", this.defectTypeName, defectTypeName));
   }

   /**
    * 원가변동
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String COST_CHANGE_CODE = "costChangeCode";
   static int COST_CHANGE_CODE_UPPER_LIMIT = -1;
   java.lang.String costChangeCode;
   /**
    * 원가변동
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getCostChangeCode() {
      return costChangeCode;
   }
   /**
    * 원가변동
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
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
    * 원가증감비율
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String COST_VARIATION_RATE = "costVariationRate";
   static int COST_VARIATION_RATE_UPPER_LIMIT = -1;
   java.lang.String costVariationRate;
   /**
    * 원가증감비율
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getCostVariationRate() {
      return costVariationRate;
   }
   /**
    * 원가증감비율
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setCostVariationRate(java.lang.String costVariationRate) throws wt.util.WTPropertyVetoException {
      costVariationRateValidate(costVariationRate);
      this.costVariationRate = costVariationRate;
   }
   void costVariationRateValidate(java.lang.String costVariationRate) throws wt.util.WTPropertyVetoException {
      if (COST_VARIATION_RATE_UPPER_LIMIT < 1) {
         try { COST_VARIATION_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costVariationRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_VARIATION_RATE_UPPER_LIMIT = 200; }
      }
      if (costVariationRate != null && !wt.fc.PersistenceHelper.checkStoredLength(costVariationRate.toString(), COST_VARIATION_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costVariationRate"), java.lang.String.valueOf(java.lang.Math.min(COST_VARIATION_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costVariationRate", this.costVariationRate, costVariationRate));
   }

   /**
    * 중요포인트 반영/변경
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String POINT_YN = "pointYn";
   static int POINT_YN_UPPER_LIMIT = -1;
   java.lang.String pointYn;
   /**
    * 중요포인트 반영/변경
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getPointYn() {
      return pointYn;
   }
   /**
    * 중요포인트 반영/변경
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setPointYn(java.lang.String pointYn) throws wt.util.WTPropertyVetoException {
      pointYnValidate(pointYn);
      this.pointYn = pointYn;
   }
   void pointYnValidate(java.lang.String pointYn) throws wt.util.WTPropertyVetoException {
      if (POINT_YN_UPPER_LIMIT < 1) {
         try { POINT_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pointYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { POINT_YN_UPPER_LIMIT = 200; }
      }
      if (pointYn != null && !wt.fc.PersistenceHelper.checkStoredLength(pointYn.toString(), POINT_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pointYn"), java.lang.String.valueOf(java.lang.Math.min(POINT_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pointYn", this.pointYn, pointYn));
   }

   /**
    * 사양변경 식별표기
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String SPEC_CHANGE_YN = "specChangeYn";
   static int SPEC_CHANGE_YN_UPPER_LIMIT = -1;
   java.lang.String specChangeYn;
   /**
    * 사양변경 식별표기
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getSpecChangeYn() {
      return specChangeYn;
   }
   /**
    * 사양변경 식별표기
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setSpecChangeYn(java.lang.String specChangeYn) throws wt.util.WTPropertyVetoException {
      specChangeYnValidate(specChangeYn);
      this.specChangeYn = specChangeYn;
   }
   void specChangeYnValidate(java.lang.String specChangeYn) throws wt.util.WTPropertyVetoException {
      if (SPEC_CHANGE_YN_UPPER_LIMIT < 1) {
         try { SPEC_CHANGE_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("specChangeYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPEC_CHANGE_YN_UPPER_LIMIT = 200; }
      }
      if (specChangeYn != null && !wt.fc.PersistenceHelper.checkStoredLength(specChangeYn.toString(), SPEC_CHANGE_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "specChangeYn"), java.lang.String.valueOf(java.lang.Math.min(SPEC_CHANGE_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "specChangeYn", this.specChangeYn, specChangeYn));
   }

   /**
    * ECO적용시점
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String ECO_APPLY_POINT = "ecoApplyPoint";
   static int ECO_APPLY_POINT_UPPER_LIMIT = -1;
   java.lang.String ecoApplyPoint;
   /**
    * ECO적용시점
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.String getEcoApplyPoint() {
      return ecoApplyPoint;
   }
   /**
    * ECO적용시점
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setEcoApplyPoint(java.lang.String ecoApplyPoint) throws wt.util.WTPropertyVetoException {
      ecoApplyPointValidate(ecoApplyPoint);
      this.ecoApplyPoint = ecoApplyPoint;
   }
   void ecoApplyPointValidate(java.lang.String ecoApplyPoint) throws wt.util.WTPropertyVetoException {
      if (ECO_APPLY_POINT_UPPER_LIMIT < 1) {
         try { ECO_APPLY_POINT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoApplyPoint").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_APPLY_POINT_UPPER_LIMIT = 200; }
      }
      if (ecoApplyPoint != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoApplyPoint.toString(), ECO_APPLY_POINT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoApplyPoint"), java.lang.String.valueOf(java.lang.Math.min(ECO_APPLY_POINT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoApplyPoint", this.ecoApplyPoint, ecoApplyPoint));
   }

   /**
    * 현상
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * 현상
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * 현상
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 현상
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * 현상
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * 현상
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 문제점 및 요구사항
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String WEB_EDITOR1 = "webEditor1";
   java.lang.Object webEditor1;
   /**
    * 문제점 및 요구사항
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.Object getWebEditor1() {
      return webEditor1;
   }
   /**
    * 문제점 및 요구사항
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setWebEditor1(java.lang.Object webEditor1) throws wt.util.WTPropertyVetoException {
      webEditor1Validate(webEditor1);
      this.webEditor1 = webEditor1;
   }
   void webEditor1Validate(java.lang.Object webEditor1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 문제점 및 요구사항
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public static final java.lang.String WEB_EDITOR_TEXT1 = "webEditorText1";
   java.lang.Object webEditorText1;
   /**
    * 문제점 및 요구사항
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public java.lang.Object getWebEditorText1() {
      return webEditorText1;
   }
   /**
    * 문제점 및 요구사항
    *
    * @see e3ps.ecm.entity.KETProdChangeOrder
    */
   public void setWebEditorText1(java.lang.Object webEditorText1) throws wt.util.WTPropertyVetoException {
      webEditorText1Validate(webEditorText1);
      this.webEditorText1 = webEditorText1;
   }
   void webEditorText1Validate(java.lang.Object webEditorText1) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -695932674235824695L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( attribute1 );
      output.writeObject( attribute10 );
      output.writeObject( attribute2 );
      output.writeObject( attribute3 );
      output.writeObject( attribute4 );
      output.writeObject( attribute5 );
      output.writeObject( attribute6 );
      output.writeObject( attribute7 );
      output.writeObject( attribute8 );
      output.writeObject( attribute9 );
      output.writeObject( carCategory );
      output.writeObject( carMaker );
      output.writeObject( changeFlag );
      output.writeObject( changeReason );
      output.writeObject( changeType );
      output.writeObject( costChangeCode );
      output.writeObject( costVariationRate );
      output.writeObject( cuDrawingChangeYn );
      output.writeObject( custormerFlag );
      output.writeObject( defectDivCode );
      output.writeObject( defectDivName );
      output.writeObject( defectTypeCode );
      output.writeObject( defectTypeName );
      output.writeObject( deptName );
      output.writeObject( designChecksheetYn );
      output.writeObject( designGuideYn );
      output.writeObject( devYn );
      output.writeObject( divisionFlag );
      output.writeObject( domesticYn );
      output.writeObject( ecoApplyPoint );
      output.writeObject( ecoId );
      output.writeObject( ecoName );
      output.writeObject( effectiveDateFlag );
      output.writeObject( inventoryClear );
      output.writeObject( otherChangeReason );
      output.writeObject( otherCustFlagDesc );
      output.writeObject( pointYn );
      output.writeObject( projectOid );
      output.writeObject( reviewResult );
      output.writeObject( specChangeYn );
      output.writeObject( webEditor );
      output.writeObject( webEditor1 );
      output.writeObject( webEditorText );
      output.writeObject( webEditorText1 );
   }

   protected void super_writeExternal_KETProdChangeOrder(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETProdChangeOrder) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProdChangeOrder(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "attribute1", attribute1 );
      output.setString( "attribute10", attribute10 );
      output.setString( "attribute2", attribute2 );
      output.setString( "attribute3", attribute3 );
      output.setString( "attribute4", attribute4 );
      output.setString( "attribute5", attribute5 );
      output.setString( "attribute6", attribute6 );
      output.setString( "attribute7", attribute7 );
      output.setString( "attribute8", attribute8 );
      output.setString( "attribute9", attribute9 );
      output.setString( "carCategory", carCategory );
      output.setString( "carMaker", carMaker );
      output.setString( "changeFlag", changeFlag );
      output.setString( "changeReason", changeReason );
      output.setString( "changeType", changeType );
      output.setString( "costChangeCode", costChangeCode );
      output.setString( "costVariationRate", costVariationRate );
      output.setString( "cuDrawingChangeYn", cuDrawingChangeYn );
      output.setString( "custormerFlag", custormerFlag );
      output.setString( "defectDivCode", defectDivCode );
      output.setString( "defectDivName", defectDivName );
      output.setString( "defectTypeCode", defectTypeCode );
      output.setString( "defectTypeName", defectTypeName );
      output.setString( "deptName", deptName );
      output.setString( "designChecksheetYn", designChecksheetYn );
      output.setString( "designGuideYn", designGuideYn );
      output.setString( "devYn", devYn );
      output.setString( "divisionFlag", divisionFlag );
      output.setString( "domesticYn", domesticYn );
      output.setString( "ecoApplyPoint", ecoApplyPoint );
      output.setString( "ecoId", ecoId );
      output.setString( "ecoName", ecoName );
      output.setString( "effectiveDateFlag", effectiveDateFlag );
      output.setString( "inventoryClear", inventoryClear );
      output.setString( "otherChangeReason", otherChangeReason );
      output.setString( "otherCustFlagDesc", otherCustFlagDesc );
      output.setString( "pointYn", pointYn );
      output.setString( "projectOid", projectOid );
      output.setString( "reviewResult", reviewResult );
      output.setString( "specChangeYn", specChangeYn );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditor1", webEditor1 );
      output.setObject( "webEditorText", webEditorText );
      output.setObject( "webEditorText1", webEditorText1 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      attribute1 = input.getString( "attribute1" );
      attribute10 = input.getString( "attribute10" );
      attribute2 = input.getString( "attribute2" );
      attribute3 = input.getString( "attribute3" );
      attribute4 = input.getString( "attribute4" );
      attribute5 = input.getString( "attribute5" );
      attribute6 = input.getString( "attribute6" );
      attribute7 = input.getString( "attribute7" );
      attribute8 = input.getString( "attribute8" );
      attribute9 = input.getString( "attribute9" );
      carCategory = input.getString( "carCategory" );
      carMaker = input.getString( "carMaker" );
      changeFlag = input.getString( "changeFlag" );
      changeReason = input.getString( "changeReason" );
      changeType = input.getString( "changeType" );
      costChangeCode = input.getString( "costChangeCode" );
      costVariationRate = input.getString( "costVariationRate" );
      cuDrawingChangeYn = input.getString( "cuDrawingChangeYn" );
      custormerFlag = input.getString( "custormerFlag" );
      defectDivCode = input.getString( "defectDivCode" );
      defectDivName = input.getString( "defectDivName" );
      defectTypeCode = input.getString( "defectTypeCode" );
      defectTypeName = input.getString( "defectTypeName" );
      deptName = input.getString( "deptName" );
      designChecksheetYn = input.getString( "designChecksheetYn" );
      designGuideYn = input.getString( "designGuideYn" );
      devYn = input.getString( "devYn" );
      divisionFlag = input.getString( "divisionFlag" );
      domesticYn = input.getString( "domesticYn" );
      ecoApplyPoint = input.getString( "ecoApplyPoint" );
      ecoId = input.getString( "ecoId" );
      ecoName = input.getString( "ecoName" );
      effectiveDateFlag = input.getString( "effectiveDateFlag" );
      inventoryClear = input.getString( "inventoryClear" );
      otherChangeReason = input.getString( "otherChangeReason" );
      otherCustFlagDesc = input.getString( "otherCustFlagDesc" );
      pointYn = input.getString( "pointYn" );
      projectOid = input.getString( "projectOid" );
      reviewResult = input.getString( "reviewResult" );
      specChangeYn = input.getString( "specChangeYn" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditor1 = (java.lang.Object) input.getObject( "webEditor1" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
      webEditorText1 = (java.lang.Object) input.getObject( "webEditorText1" );
   }

   boolean readVersion_695932674235824695L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      attribute1 = (java.lang.String) input.readObject();
      attribute10 = (java.lang.String) input.readObject();
      attribute2 = (java.lang.String) input.readObject();
      attribute3 = (java.lang.String) input.readObject();
      attribute4 = (java.lang.String) input.readObject();
      attribute5 = (java.lang.String) input.readObject();
      attribute6 = (java.lang.String) input.readObject();
      attribute7 = (java.lang.String) input.readObject();
      attribute8 = (java.lang.String) input.readObject();
      attribute9 = (java.lang.String) input.readObject();
      carCategory = (java.lang.String) input.readObject();
      carMaker = (java.lang.String) input.readObject();
      changeFlag = (java.lang.String) input.readObject();
      changeReason = (java.lang.String) input.readObject();
      changeType = (java.lang.String) input.readObject();
      costChangeCode = (java.lang.String) input.readObject();
      costVariationRate = (java.lang.String) input.readObject();
      cuDrawingChangeYn = (java.lang.String) input.readObject();
      custormerFlag = (java.lang.String) input.readObject();
      defectDivCode = (java.lang.String) input.readObject();
      defectDivName = (java.lang.String) input.readObject();
      defectTypeCode = (java.lang.String) input.readObject();
      defectTypeName = (java.lang.String) input.readObject();
      deptName = (java.lang.String) input.readObject();
      designChecksheetYn = (java.lang.String) input.readObject();
      designGuideYn = (java.lang.String) input.readObject();
      devYn = (java.lang.String) input.readObject();
      divisionFlag = (java.lang.String) input.readObject();
      domesticYn = (java.lang.String) input.readObject();
      ecoApplyPoint = (java.lang.String) input.readObject();
      ecoId = (java.lang.String) input.readObject();
      ecoName = (java.lang.String) input.readObject();
      effectiveDateFlag = (java.lang.String) input.readObject();
      inventoryClear = (java.lang.String) input.readObject();
      otherChangeReason = (java.lang.String) input.readObject();
      otherCustFlagDesc = (java.lang.String) input.readObject();
      pointYn = (java.lang.String) input.readObject();
      projectOid = (java.lang.String) input.readObject();
      reviewResult = (java.lang.String) input.readObject();
      specChangeYn = (java.lang.String) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditor1 = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();
      webEditorText1 = (java.lang.Object) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProdChangeOrder thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_695932674235824695L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProdChangeOrder( _KETProdChangeOrder thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
