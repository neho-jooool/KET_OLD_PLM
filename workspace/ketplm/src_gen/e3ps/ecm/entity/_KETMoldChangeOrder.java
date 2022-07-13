package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldChangeOrder extends wt.change2.WTChangeOrder2 implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldChangeOrder.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ECO_ID = "ecoId";
   static int ECO_ID_UPPER_LIMIT = -1;
   java.lang.String ecoId;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getEcoId() {
      return ecoId;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ECO_NAME = "ecoName";
   static int ECO_NAME_UPPER_LIMIT = -1;
   java.lang.String ecoName;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getEcoName() {
      return ecoName;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String DEV_YN = "devYn";
   static int DEV_YN_UPPER_LIMIT = -1;
   java.lang.String devYn;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getDevYn() {
      return devYn;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String DIVISION_FLAG = "divisionFlag";
   static int DIVISION_FLAG_UPPER_LIMIT = -1;
   java.lang.String divisionFlag;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getDivisionFlag() {
      return divisionFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String PROJECT_OID = "projectOid";
   static int PROJECT_OID_UPPER_LIMIT = -1;
   java.lang.String projectOid;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getProjectOid() {
      return projectOid;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String VENDOR_FLAG = "vendorFlag";
   static int VENDOR_FLAG_UPPER_LIMIT = -1;
   java.lang.String vendorFlag;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getVendorFlag() {
      return vendorFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public void setVendorFlag(java.lang.String vendorFlag) throws wt.util.WTPropertyVetoException {
      vendorFlagValidate(vendorFlag);
      this.vendorFlag = vendorFlag;
   }
   void vendorFlagValidate(java.lang.String vendorFlag) throws wt.util.WTPropertyVetoException {
      if (VENDOR_FLAG_UPPER_LIMIT < 1) {
         try { VENDOR_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("vendorFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VENDOR_FLAG_UPPER_LIMIT = 200; }
      }
      if (vendorFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(vendorFlag.toString(), VENDOR_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "vendorFlag"), java.lang.String.valueOf(java.lang.Math.min(VENDOR_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "vendorFlag", this.vendorFlag, vendorFlag));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String PROD_VENDOR = "prodVendor";
   static int PROD_VENDOR_UPPER_LIMIT = -1;
   java.lang.String prodVendor;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getProdVendor() {
      return prodVendor;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public void setProdVendor(java.lang.String prodVendor) throws wt.util.WTPropertyVetoException {
      prodVendorValidate(prodVendor);
      this.prodVendor = prodVendor;
   }
   void prodVendorValidate(java.lang.String prodVendor) throws wt.util.WTPropertyVetoException {
      if (PROD_VENDOR_UPPER_LIMIT < 1) {
         try { PROD_VENDOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("prodVendor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROD_VENDOR_UPPER_LIMIT = 200; }
      }
      if (prodVendor != null && !wt.fc.PersistenceHelper.checkStoredLength(prodVendor.toString(), PROD_VENDOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodVendor"), java.lang.String.valueOf(java.lang.Math.min(PROD_VENDOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "prodVendor", this.prodVendor, prodVendor));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String CHANGE_REASON = "changeReason";
   static int CHANGE_REASON_UPPER_LIMIT = -1;
   java.lang.String changeReason;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getChangeReason() {
      return changeReason;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String OTHER_REASON_DESC = "otherReasonDesc";
   static int OTHER_REASON_DESC_UPPER_LIMIT = -1;
   java.lang.String otherReasonDesc;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getOtherReasonDesc() {
      return otherReasonDesc;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public void setOtherReasonDesc(java.lang.String otherReasonDesc) throws wt.util.WTPropertyVetoException {
      otherReasonDescValidate(otherReasonDesc);
      this.otherReasonDesc = otherReasonDesc;
   }
   void otherReasonDescValidate(java.lang.String otherReasonDesc) throws wt.util.WTPropertyVetoException {
      if (OTHER_REASON_DESC_UPPER_LIMIT < 1) {
         try { OTHER_REASON_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("otherReasonDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OTHER_REASON_DESC_UPPER_LIMIT = 200; }
      }
      if (otherReasonDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(otherReasonDesc.toString(), OTHER_REASON_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "otherReasonDesc"), java.lang.String.valueOf(java.lang.Math.min(OTHER_REASON_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "otherReasonDesc", this.otherReasonDesc, otherReasonDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String INCREASE_PROD_TYPE = "increaseProdType";
   static int INCREASE_PROD_TYPE_UPPER_LIMIT = -1;
   java.lang.String increaseProdType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getIncreaseProdType() {
      return increaseProdType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public void setIncreaseProdType(java.lang.String increaseProdType) throws wt.util.WTPropertyVetoException {
      increaseProdTypeValidate(increaseProdType);
      this.increaseProdType = increaseProdType;
   }
   void increaseProdTypeValidate(java.lang.String increaseProdType) throws wt.util.WTPropertyVetoException {
      if (INCREASE_PROD_TYPE_UPPER_LIMIT < 1) {
         try { INCREASE_PROD_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("increaseProdType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INCREASE_PROD_TYPE_UPPER_LIMIT = 200; }
      }
      if (increaseProdType != null && !wt.fc.PersistenceHelper.checkStoredLength(increaseProdType.toString(), INCREASE_PROD_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "increaseProdType"), java.lang.String.valueOf(java.lang.Math.min(INCREASE_PROD_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "increaseProdType", this.increaseProdType, increaseProdType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String OTHER_INCREASE_PROD_TYPE = "otherIncreaseProdType";
   static int OTHER_INCREASE_PROD_TYPE_UPPER_LIMIT = -1;
   java.lang.String otherIncreaseProdType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getOtherIncreaseProdType() {
      return otherIncreaseProdType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public void setOtherIncreaseProdType(java.lang.String otherIncreaseProdType) throws wt.util.WTPropertyVetoException {
      otherIncreaseProdTypeValidate(otherIncreaseProdType);
      this.otherIncreaseProdType = otherIncreaseProdType;
   }
   void otherIncreaseProdTypeValidate(java.lang.String otherIncreaseProdType) throws wt.util.WTPropertyVetoException {
      if (OTHER_INCREASE_PROD_TYPE_UPPER_LIMIT < 1) {
         try { OTHER_INCREASE_PROD_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("otherIncreaseProdType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OTHER_INCREASE_PROD_TYPE_UPPER_LIMIT = 200; }
      }
      if (otherIncreaseProdType != null && !wt.fc.PersistenceHelper.checkStoredLength(otherIncreaseProdType.toString(), OTHER_INCREASE_PROD_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "otherIncreaseProdType"), java.lang.String.valueOf(java.lang.Math.min(OTHER_INCREASE_PROD_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "otherIncreaseProdType", this.otherIncreaseProdType, otherIncreaseProdType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ECO_WORKER_ID = "ecoWorkerId";
   static int ECO_WORKER_ID_UPPER_LIMIT = -1;
   java.lang.String ecoWorkerId;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getEcoWorkerId() {
      return ecoWorkerId;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public void setEcoWorkerId(java.lang.String ecoWorkerId) throws wt.util.WTPropertyVetoException {
      ecoWorkerIdValidate(ecoWorkerId);
      this.ecoWorkerId = ecoWorkerId;
   }
   void ecoWorkerIdValidate(java.lang.String ecoWorkerId) throws wt.util.WTPropertyVetoException {
      if (ECO_WORKER_ID_UPPER_LIMIT < 1) {
         try { ECO_WORKER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoWorkerId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_WORKER_ID_UPPER_LIMIT = 200; }
      }
      if (ecoWorkerId != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoWorkerId.toString(), ECO_WORKER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoWorkerId"), java.lang.String.valueOf(java.lang.Math.min(ECO_WORKER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoWorkerId", this.ecoWorkerId, ecoWorkerId));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * ????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * ????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String WEB_EDITOR1 = "webEditor1";
   java.lang.Object webEditor1;
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.Object getWebEditor1() {
      return webEditor1;
   }
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public static final java.lang.String WEB_EDITOR_TEXT1 = "webEditorText1";
   java.lang.Object webEditorText1;
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
    */
   public java.lang.Object getWebEditorText1() {
      return webEditorText1;
   }
   /**
    * ?????? ?? ??????
    *
    * @see e3ps.ecm.entity.KETMoldChangeOrder
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

   public static final long EXTERNALIZATION_VERSION_UID = 7223027112408820172L;

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
      output.writeObject( changeReason );
      output.writeObject( deptName );
      output.writeObject( devYn );
      output.writeObject( divisionFlag );
      output.writeObject( ecoId );
      output.writeObject( ecoName );
      output.writeObject( ecoWorkerId );
      output.writeObject( increaseProdType );
      output.writeObject( otherIncreaseProdType );
      output.writeObject( otherReasonDesc );
      output.writeObject( prodVendor );
      output.writeObject( projectOid );
      output.writeObject( vendorFlag );
      output.writeObject( webEditor );
      output.writeObject( webEditor1 );
      output.writeObject( webEditorText );
      output.writeObject( webEditorText1 );
   }

   protected void super_writeExternal_KETMoldChangeOrder(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldChangeOrder) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldChangeOrder(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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
      output.setString( "changeReason", changeReason );
      output.setString( "deptName", deptName );
      output.setString( "devYn", devYn );
      output.setString( "divisionFlag", divisionFlag );
      output.setString( "ecoId", ecoId );
      output.setString( "ecoName", ecoName );
      output.setString( "ecoWorkerId", ecoWorkerId );
      output.setString( "increaseProdType", increaseProdType );
      output.setString( "otherIncreaseProdType", otherIncreaseProdType );
      output.setString( "otherReasonDesc", otherReasonDesc );
      output.setString( "prodVendor", prodVendor );
      output.setString( "projectOid", projectOid );
      output.setString( "vendorFlag", vendorFlag );
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
      changeReason = input.getString( "changeReason" );
      deptName = input.getString( "deptName" );
      devYn = input.getString( "devYn" );
      divisionFlag = input.getString( "divisionFlag" );
      ecoId = input.getString( "ecoId" );
      ecoName = input.getString( "ecoName" );
      ecoWorkerId = input.getString( "ecoWorkerId" );
      increaseProdType = input.getString( "increaseProdType" );
      otherIncreaseProdType = input.getString( "otherIncreaseProdType" );
      otherReasonDesc = input.getString( "otherReasonDesc" );
      prodVendor = input.getString( "prodVendor" );
      projectOid = input.getString( "projectOid" );
      vendorFlag = input.getString( "vendorFlag" );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditor1 = (java.lang.Object) input.getObject( "webEditor1" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
      webEditorText1 = (java.lang.Object) input.getObject( "webEditorText1" );
   }

   boolean readVersion7223027112408820172L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
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
      changeReason = (java.lang.String) input.readObject();
      deptName = (java.lang.String) input.readObject();
      devYn = (java.lang.String) input.readObject();
      divisionFlag = (java.lang.String) input.readObject();
      ecoId = (java.lang.String) input.readObject();
      ecoName = (java.lang.String) input.readObject();
      ecoWorkerId = (java.lang.String) input.readObject();
      increaseProdType = (java.lang.String) input.readObject();
      otherIncreaseProdType = (java.lang.String) input.readObject();
      otherReasonDesc = (java.lang.String) input.readObject();
      prodVendor = (java.lang.String) input.readObject();
      projectOid = (java.lang.String) input.readObject();
      vendorFlag = (java.lang.String) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditor1 = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();
      webEditorText1 = (java.lang.Object) input.readObject();
      return true;
   }

   protected boolean readVersion( KETMoldChangeOrder thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7223027112408820172L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldChangeOrder( _KETMoldChangeOrder thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
