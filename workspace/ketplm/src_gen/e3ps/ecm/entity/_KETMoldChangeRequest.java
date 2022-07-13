package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldChangeRequest extends wt.change2.WTChangeRequest2 implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldChangeRequest.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ECR_ID = "ecrId";
   static int ECR_ID_UPPER_LIMIT = -1;
   java.lang.String ecrId;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getEcrId() {
      return ecrId;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setEcrId(java.lang.String ecrId) throws wt.util.WTPropertyVetoException {
      ecrIdValidate(ecrId);
      this.ecrId = ecrId;
   }
   void ecrIdValidate(java.lang.String ecrId) throws wt.util.WTPropertyVetoException {
      if (ECR_ID_UPPER_LIMIT < 1) {
         try { ECR_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecrId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECR_ID_UPPER_LIMIT = 200; }
      }
      if (ecrId != null && !wt.fc.PersistenceHelper.checkStoredLength(ecrId.toString(), ECR_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecrId"), java.lang.String.valueOf(java.lang.Math.min(ECR_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecrId", this.ecrId, ecrId));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ECR_NAME = "ecrName";
   static int ECR_NAME_UPPER_LIMIT = -1;
   java.lang.String ecrName;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getEcrName() {
      return ecrName;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setEcrName(java.lang.String ecrName) throws wt.util.WTPropertyVetoException {
      ecrNameValidate(ecrName);
      this.ecrName = ecrName;
   }
   void ecrNameValidate(java.lang.String ecrName) throws wt.util.WTPropertyVetoException {
      if (ECR_NAME_UPPER_LIMIT < 1) {
         try { ECR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecrName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECR_NAME_UPPER_LIMIT = 200; }
      }
      if (ecrName != null && !wt.fc.PersistenceHelper.checkStoredLength(ecrName.toString(), ECR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecrName"), java.lang.String.valueOf(java.lang.Math.min(ECR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecrName", this.ecrName, ecrName));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String DEV_YN = "devYn";
   static int DEV_YN_UPPER_LIMIT = -1;
   java.lang.String devYn;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getDevYn() {
      return devYn;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String DIVISION_FLAG = "divisionFlag";
   static int DIVISION_FLAG_UPPER_LIMIT = -1;
   java.lang.String divisionFlag;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getDivisionFlag() {
      return divisionFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String PROJECT_OID = "projectOid";
   static int PROJECT_OID_UPPER_LIMIT = -1;
   java.lang.String projectOid;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getProjectOid() {
      return projectOid;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String REQUEST_TYPE = "requestType";
   static int REQUEST_TYPE_UPPER_LIMIT = -1;
   java.lang.String requestType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getRequestType() {
      return requestType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setRequestType(java.lang.String requestType) throws wt.util.WTPropertyVetoException {
      requestTypeValidate(requestType);
      this.requestType = requestType;
   }
   void requestTypeValidate(java.lang.String requestType) throws wt.util.WTPropertyVetoException {
      if (REQUEST_TYPE_UPPER_LIMIT < 1) {
         try { REQUEST_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUEST_TYPE_UPPER_LIMIT = 200; }
      }
      if (requestType != null && !wt.fc.PersistenceHelper.checkStoredLength(requestType.toString(), REQUEST_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestType"), java.lang.String.valueOf(java.lang.Math.min(REQUEST_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestType", this.requestType, requestType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String OTHER_REQ_TYPE = "otherReqType";
   static int OTHER_REQ_TYPE_UPPER_LIMIT = -1;
   java.lang.String otherReqType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getOtherReqType() {
      return otherReqType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setOtherReqType(java.lang.String otherReqType) throws wt.util.WTPropertyVetoException {
      otherReqTypeValidate(otherReqType);
      this.otherReqType = otherReqType;
   }
   void otherReqTypeValidate(java.lang.String otherReqType) throws wt.util.WTPropertyVetoException {
      if (OTHER_REQ_TYPE_UPPER_LIMIT < 1) {
         try { OTHER_REQ_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("otherReqType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OTHER_REQ_TYPE_UPPER_LIMIT = 200; }
      }
      if (otherReqType != null && !wt.fc.PersistenceHelper.checkStoredLength(otherReqType.toString(), OTHER_REQ_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "otherReqType"), java.lang.String.valueOf(java.lang.Math.min(OTHER_REQ_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "otherReqType", this.otherReqType, otherReqType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String CHANGE_TYPE = "changeType";
   static int CHANGE_TYPE_UPPER_LIMIT = -1;
   java.lang.String changeType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getChangeType() {
      return changeType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setChangeType(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      changeTypeValidate(changeType);
      this.changeType = changeType;
   }
   void changeTypeValidate(java.lang.String changeType) throws wt.util.WTPropertyVetoException {
      if (CHANGE_TYPE_UPPER_LIMIT < 1) {
         try { CHANGE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_TYPE_UPPER_LIMIT = 200; }
      }
      if (changeType != null && !wt.fc.PersistenceHelper.checkStoredLength(changeType.toString(), CHANGE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeType"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeType", this.changeType, changeType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String OTHER_CHANGE_DESC = "otherChangeDesc";
   static int OTHER_CHANGE_DESC_UPPER_LIMIT = -1;
   java.lang.String otherChangeDesc;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getOtherChangeDesc() {
      return otherChangeDesc;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setOtherChangeDesc(java.lang.String otherChangeDesc) throws wt.util.WTPropertyVetoException {
      otherChangeDescValidate(otherChangeDesc);
      this.otherChangeDesc = otherChangeDesc;
   }
   void otherChangeDescValidate(java.lang.String otherChangeDesc) throws wt.util.WTPropertyVetoException {
      if (OTHER_CHANGE_DESC_UPPER_LIMIT < 1) {
         try { OTHER_CHANGE_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("otherChangeDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OTHER_CHANGE_DESC_UPPER_LIMIT = 200; }
      }
      if (otherChangeDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(otherChangeDesc.toString(), OTHER_CHANGE_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "otherChangeDesc"), java.lang.String.valueOf(java.lang.Math.min(OTHER_CHANGE_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "otherChangeDesc", this.otherChangeDesc, otherChangeDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String COMPLETE_REQ_DATE = "completeReqDate";
   static int COMPLETE_REQ_DATE_UPPER_LIMIT = -1;
   java.lang.String completeReqDate;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getCompleteReqDate() {
      return completeReqDate;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setCompleteReqDate(java.lang.String completeReqDate) throws wt.util.WTPropertyVetoException {
      completeReqDateValidate(completeReqDate);
      this.completeReqDate = completeReqDate;
   }
   void completeReqDateValidate(java.lang.String completeReqDate) throws wt.util.WTPropertyVetoException {
      if (COMPLETE_REQ_DATE_UPPER_LIMIT < 1) {
         try { COMPLETE_REQ_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("completeReqDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMPLETE_REQ_DATE_UPPER_LIMIT = 200; }
      }
      if (completeReqDate != null && !wt.fc.PersistenceHelper.checkStoredLength(completeReqDate.toString(), COMPLETE_REQ_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "completeReqDate"), java.lang.String.valueOf(java.lang.Math.min(COMPLETE_REQ_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "completeReqDate", this.completeReqDate, completeReqDate));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String VENDOR_FLAG = "vendorFlag";
   static int VENDOR_FLAG_UPPER_LIMIT = -1;
   java.lang.String vendorFlag;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getVendorFlag() {
      return vendorFlag;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String PROD_VENDOR = "prodVendor";
   static int PROD_VENDOR_UPPER_LIMIT = -1;
   java.lang.String prodVendor;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getProdVendor() {
      return prodVendor;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String PROCESS_TYPE = "processType";
   static int PROCESS_TYPE_UPPER_LIMIT = -1;
   java.lang.String processType;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getProcessType() {
      return processType;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setProcessType(java.lang.String processType) throws wt.util.WTPropertyVetoException {
      processTypeValidate(processType);
      this.processType = processType;
   }
   void processTypeValidate(java.lang.String processType) throws wt.util.WTPropertyVetoException {
      if (PROCESS_TYPE_UPPER_LIMIT < 1) {
         try { PROCESS_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("processType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROCESS_TYPE_UPPER_LIMIT = 200; }
      }
      if (processType != null && !wt.fc.PersistenceHelper.checkStoredLength(processType.toString(), PROCESS_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "processType"), java.lang.String.valueOf(java.lang.Math.min(PROCESS_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "processType", this.processType, processType));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String OTHER_PROCESS_DESC = "otherProcessDesc";
   static int OTHER_PROCESS_DESC_UPPER_LIMIT = -1;
   java.lang.String otherProcessDesc;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getOtherProcessDesc() {
      return otherProcessDesc;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setOtherProcessDesc(java.lang.String otherProcessDesc) throws wt.util.WTPropertyVetoException {
      otherProcessDescValidate(otherProcessDesc);
      this.otherProcessDesc = otherProcessDesc;
   }
   void otherProcessDescValidate(java.lang.String otherProcessDesc) throws wt.util.WTPropertyVetoException {
      if (OTHER_PROCESS_DESC_UPPER_LIMIT < 1) {
         try { OTHER_PROCESS_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("otherProcessDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OTHER_PROCESS_DESC_UPPER_LIMIT = 200; }
      }
      if (otherProcessDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(otherProcessDesc.toString(), OTHER_PROCESS_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "otherProcessDesc"), java.lang.String.valueOf(java.lang.Math.min(OTHER_PROCESS_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "otherProcessDesc", this.otherProcessDesc, otherProcessDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ECR_DESC = "ecrDesc";
   static int ECR_DESC_UPPER_LIMIT = -1;
   java.lang.String ecrDesc;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getEcrDesc() {
      return ecrDesc;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setEcrDesc(java.lang.String ecrDesc) throws wt.util.WTPropertyVetoException {
      ecrDescValidate(ecrDesc);
      this.ecrDesc = ecrDesc;
   }
   void ecrDescValidate(java.lang.String ecrDesc) throws wt.util.WTPropertyVetoException {
      if (ECR_DESC_UPPER_LIMIT < 1) {
         try { ECR_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecrDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECR_DESC_UPPER_LIMIT = 200; }
      }
      if (ecrDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(ecrDesc.toString(), ECR_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecrDesc"), java.lang.String.valueOf(java.lang.Math.min(ECR_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecrDesc", this.ecrDesc, ecrDesc));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String EXPECT_EFFECT = "expectEffect";
   static int EXPECT_EFFECT_UPPER_LIMIT = -1;
   java.lang.String expectEffect;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getExpectEffect() {
      return expectEffect;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setExpectEffect(java.lang.String expectEffect) throws wt.util.WTPropertyVetoException {
      expectEffectValidate(expectEffect);
      this.expectEffect = expectEffect;
   }
   void expectEffectValidate(java.lang.String expectEffect) throws wt.util.WTPropertyVetoException {
      if (EXPECT_EFFECT_UPPER_LIMIT < 1) {
         try { EXPECT_EFFECT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expectEffect").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPECT_EFFECT_UPPER_LIMIT = 200; }
      }
      if (expectEffect != null && !wt.fc.PersistenceHelper.checkStoredLength(expectEffect.toString(), EXPECT_EFFECT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expectEffect"), java.lang.String.valueOf(java.lang.Math.min(EXPECT_EFFECT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expectEffect", this.expectEffect, expectEffect));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String DEPT_NAME = "deptName";
   static int DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String deptName;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getDeptName() {
      return deptName;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
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
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public static final java.lang.String ECR_STATE_STATE = "ecrStateState";
   wt.lifecycle.State ecrStateState;
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public wt.lifecycle.State getEcrStateState() {
      return ecrStateState;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldChangeRequest
    */
   public void setEcrStateState(wt.lifecycle.State ecrStateState) throws wt.util.WTPropertyVetoException {
      ecrStateStateValidate(ecrStateState);
      this.ecrStateState = ecrStateState;
   }
   void ecrStateStateValidate(wt.lifecycle.State ecrStateState) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 1863639708468053513L;

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
      output.writeObject( changeType );
      output.writeObject( completeReqDate );
      output.writeObject( deptName );
      output.writeObject( devYn );
      output.writeObject( divisionFlag );
      output.writeObject( ecrDesc );
      output.writeObject( ecrId );
      output.writeObject( ecrName );
      output.writeObject( (ecrStateState == null ? null : ecrStateState.getStringValue()) );
      output.writeObject( expectEffect );
      output.writeObject( otherChangeDesc );
      output.writeObject( otherProcessDesc );
      output.writeObject( otherReqType );
      output.writeObject( processType );
      output.writeObject( prodVendor );
      output.writeObject( projectOid );
      output.writeObject( requestType );
      output.writeObject( vendorFlag );
   }

   protected void super_writeExternal_KETMoldChangeRequest(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldChangeRequest) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldChangeRequest(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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
      output.setString( "changeType", changeType );
      output.setString( "completeReqDate", completeReqDate );
      output.setString( "deptName", deptName );
      output.setString( "devYn", devYn );
      output.setString( "divisionFlag", divisionFlag );
      output.setString( "ecrDesc", ecrDesc );
      output.setString( "ecrId", ecrId );
      output.setString( "ecrName", ecrName );
      output.setString( "ecrStateState", (ecrStateState == null ? null : ecrStateState.toString()) );
      output.setString( "expectEffect", expectEffect );
      output.setString( "otherChangeDesc", otherChangeDesc );
      output.setString( "otherProcessDesc", otherProcessDesc );
      output.setString( "otherReqType", otherReqType );
      output.setString( "processType", processType );
      output.setString( "prodVendor", prodVendor );
      output.setString( "projectOid", projectOid );
      output.setString( "requestType", requestType );
      output.setString( "vendorFlag", vendorFlag );
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
      changeType = input.getString( "changeType" );
      completeReqDate = input.getString( "completeReqDate" );
      deptName = input.getString( "deptName" );
      devYn = input.getString( "devYn" );
      divisionFlag = input.getString( "divisionFlag" );
      ecrDesc = input.getString( "ecrDesc" );
      ecrId = input.getString( "ecrId" );
      ecrName = input.getString( "ecrName" );
      java.lang.String ecrStateState_string_value = (java.lang.String) input.getString("ecrStateState");
      if ( ecrStateState_string_value != null ) { 
         ecrStateState = (wt.lifecycle.State) wt.introspection.ClassInfo.getConstrainedEnum( getClass(), "ecrStateState", ecrStateState_string_value );
         if ( ecrStateState == null )  // hard-coded type
            ecrStateState = wt.lifecycle.State.toState( ecrStateState_string_value );
      }
      expectEffect = input.getString( "expectEffect" );
      otherChangeDesc = input.getString( "otherChangeDesc" );
      otherProcessDesc = input.getString( "otherProcessDesc" );
      otherReqType = input.getString( "otherReqType" );
      processType = input.getString( "processType" );
      prodVendor = input.getString( "prodVendor" );
      projectOid = input.getString( "projectOid" );
      requestType = input.getString( "requestType" );
      vendorFlag = input.getString( "vendorFlag" );
   }

   boolean readVersion1863639708468053513L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
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
      changeType = (java.lang.String) input.readObject();
      completeReqDate = (java.lang.String) input.readObject();
      deptName = (java.lang.String) input.readObject();
      devYn = (java.lang.String) input.readObject();
      divisionFlag = (java.lang.String) input.readObject();
      ecrDesc = (java.lang.String) input.readObject();
      ecrId = (java.lang.String) input.readObject();
      ecrName = (java.lang.String) input.readObject();
      java.lang.String ecrStateState_string_value = (java.lang.String) input.readObject();
      try { 
         ecrStateState = (wt.lifecycle.State) wt.fc.EnumeratedTypeUtil.toEnumeratedType( ecrStateState_string_value );
      } catch( wt.util.WTInvalidParameterException e ) {
         // Old Format
         ecrStateState = wt.lifecycle.State.toState( ecrStateState_string_value );
      }
      expectEffect = (java.lang.String) input.readObject();
      otherChangeDesc = (java.lang.String) input.readObject();
      otherProcessDesc = (java.lang.String) input.readObject();
      otherReqType = (java.lang.String) input.readObject();
      processType = (java.lang.String) input.readObject();
      prodVendor = (java.lang.String) input.readObject();
      projectOid = (java.lang.String) input.readObject();
      requestType = (java.lang.String) input.readObject();
      vendorFlag = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETMoldChangeRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1863639708468053513L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldChangeRequest( _KETMoldChangeRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
