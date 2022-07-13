package ext.ket.sample.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSampleRequest extends wt.enterprise.Managed implements wt.content.FormatContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sample.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSampleRequest.class.getName();

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String REQUEST_NO = "requestNo";
   static int REQUEST_NO_UPPER_LIMIT = -1;
   java.lang.String requestNo;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getRequestNo() {
      return requestNo;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setRequestNo(java.lang.String requestNo) throws wt.util.WTPropertyVetoException {
      requestNoValidate(requestNo);
      this.requestNo = requestNo;
   }
   void requestNoValidate(java.lang.String requestNo) throws wt.util.WTPropertyVetoException {
      if (REQUEST_NO_UPPER_LIMIT < 1) {
         try { REQUEST_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUEST_NO_UPPER_LIMIT = 200; }
      }
      if (requestNo != null && !wt.fc.PersistenceHelper.checkStoredLength(requestNo.toString(), REQUEST_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestNo"), java.lang.String.valueOf(java.lang.Math.min(REQUEST_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestNo", this.requestNo, requestNo));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String REQUEST_TITLE = "requestTitle";
   static int REQUEST_TITLE_UPPER_LIMIT = -1;
   java.lang.String requestTitle;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getRequestTitle() {
      return requestTitle;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setRequestTitle(java.lang.String requestTitle) throws wt.util.WTPropertyVetoException {
      requestTitleValidate(requestTitle);
      this.requestTitle = requestTitle;
   }
   void requestTitleValidate(java.lang.String requestTitle) throws wt.util.WTPropertyVetoException {
      if (REQUEST_TITLE_UPPER_LIMIT < 1) {
         try { REQUEST_TITLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestTitle").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUEST_TITLE_UPPER_LIMIT = 200; }
      }
      if (requestTitle != null && !wt.fc.PersistenceHelper.checkStoredLength(requestTitle.toString(), REQUEST_TITLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestTitle"), java.lang.String.valueOf(java.lang.Math.min(REQUEST_TITLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestTitle", this.requestTitle, requestTitle));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String CUSTOMER_CODE = "customerCode";
   static int CUSTOMER_CODE_UPPER_LIMIT = -1;
   java.lang.String customerCode;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getCustomerCode() {
      return customerCode;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setCustomerCode(java.lang.String customerCode) throws wt.util.WTPropertyVetoException {
      customerCodeValidate(customerCode);
      this.customerCode = customerCode;
   }
   void customerCodeValidate(java.lang.String customerCode) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_CODE_UPPER_LIMIT < 1) {
         try { CUSTOMER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customerCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_CODE_UPPER_LIMIT = 4000; }
      }
      if (customerCode != null && !wt.fc.PersistenceHelper.checkStoredLength(customerCode.toString(), CUSTOMER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerCode"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customerCode", this.customerCode, customerCode));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String CUSTOMER_NAME = "customerName";
   static int CUSTOMER_NAME_UPPER_LIMIT = -1;
   java.lang.String customerName;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getCustomerName() {
      return customerName;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setCustomerName(java.lang.String customerName) throws wt.util.WTPropertyVetoException {
      customerNameValidate(customerName);
      this.customerName = customerName;
   }
   void customerNameValidate(java.lang.String customerName) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_NAME_UPPER_LIMIT < 1) {
         try { CUSTOMER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_NAME_UPPER_LIMIT = 4000; }
      }
      if (customerName != null && !wt.fc.PersistenceHelper.checkStoredLength(customerName.toString(), CUSTOMER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerName"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customerName", this.customerName, customerName));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String CUSTOMER_CONTRACTOR = "customerContractor";
   static int CUSTOMER_CONTRACTOR_UPPER_LIMIT = -1;
   java.lang.String customerContractor;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getCustomerContractor() {
      return customerContractor;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setCustomerContractor(java.lang.String customerContractor) throws wt.util.WTPropertyVetoException {
      customerContractorValidate(customerContractor);
      this.customerContractor = customerContractor;
   }
   void customerContractorValidate(java.lang.String customerContractor) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_CONTRACTOR_UPPER_LIMIT < 1) {
         try { CUSTOMER_CONTRACTOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customerContractor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_CONTRACTOR_UPPER_LIMIT = 200; }
      }
      if (customerContractor != null && !wt.fc.PersistenceHelper.checkStoredLength(customerContractor.toString(), CUSTOMER_CONTRACTOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerContractor"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_CONTRACTOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customerContractor", this.customerContractor, customerContractor));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String CAR_TYPE_CODE = "carTypeCode";
   static int CAR_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String carTypeCode;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getCarTypeCode() {
      return carTypeCode;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
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
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String CAR_TYPE_NAME = "carTypeName";
   static int CAR_TYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String carTypeName;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getCarTypeName() {
      return carTypeName;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setCarTypeName(java.lang.String carTypeName) throws wt.util.WTPropertyVetoException {
      carTypeNameValidate(carTypeName);
      this.carTypeName = carTypeName;
   }
   void carTypeNameValidate(java.lang.String carTypeName) throws wt.util.WTPropertyVetoException {
      if (CAR_TYPE_NAME_UPPER_LIMIT < 1) {
         try { CAR_TYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("carTypeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAR_TYPE_NAME_UPPER_LIMIT = 200; }
      }
      if (carTypeName != null && !wt.fc.PersistenceHelper.checkStoredLength(carTypeName.toString(), CAR_TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carTypeName"), java.lang.String.valueOf(java.lang.Math.min(CAR_TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "carTypeName", this.carTypeName, carTypeName));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String DEVELOPE_STAGE_NAME = "developeStageName";
   static int DEVELOPE_STAGE_NAME_UPPER_LIMIT = -1;
   java.lang.String developeStageName;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getDevelopeStageName() {
      return developeStageName;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setDevelopeStageName(java.lang.String developeStageName) throws wt.util.WTPropertyVetoException {
      developeStageNameValidate(developeStageName);
      this.developeStageName = developeStageName;
   }
   void developeStageNameValidate(java.lang.String developeStageName) throws wt.util.WTPropertyVetoException {
      if (DEVELOPE_STAGE_NAME_UPPER_LIMIT < 1) {
         try { DEVELOPE_STAGE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("developeStageName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEVELOPE_STAGE_NAME_UPPER_LIMIT = 200; }
      }
      if (developeStageName != null && !wt.fc.PersistenceHelper.checkStoredLength(developeStageName.toString(), DEVELOPE_STAGE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "developeStageName"), java.lang.String.valueOf(java.lang.Math.min(DEVELOPE_STAGE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "developeStageName", this.developeStageName, developeStageName));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String DEVELOPE_STAGE_CODE = "developeStageCode";
   static int DEVELOPE_STAGE_CODE_UPPER_LIMIT = -1;
   java.lang.String developeStageCode;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getDevelopeStageCode() {
      return developeStageCode;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setDevelopeStageCode(java.lang.String developeStageCode) throws wt.util.WTPropertyVetoException {
      developeStageCodeValidate(developeStageCode);
      this.developeStageCode = developeStageCode;
   }
   void developeStageCodeValidate(java.lang.String developeStageCode) throws wt.util.WTPropertyVetoException {
      if (DEVELOPE_STAGE_CODE_UPPER_LIMIT < 1) {
         try { DEVELOPE_STAGE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("developeStageCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEVELOPE_STAGE_CODE_UPPER_LIMIT = 200; }
      }
      if (developeStageCode != null && !wt.fc.PersistenceHelper.checkStoredLength(developeStageCode.toString(), DEVELOPE_STAGE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "developeStageCode"), java.lang.String.valueOf(java.lang.Math.min(DEVELOPE_STAGE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "developeStageCode", this.developeStageCode, developeStageCode));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String SAMPLE_REQUEST_STATE_CODE = "sampleRequestStateCode";
   static int SAMPLE_REQUEST_STATE_CODE_UPPER_LIMIT = -1;
   java.lang.String sampleRequestStateCode;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getSampleRequestStateCode() {
      return sampleRequestStateCode;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setSampleRequestStateCode(java.lang.String sampleRequestStateCode) throws wt.util.WTPropertyVetoException {
      sampleRequestStateCodeValidate(sampleRequestStateCode);
      this.sampleRequestStateCode = sampleRequestStateCode;
   }
   void sampleRequestStateCodeValidate(java.lang.String sampleRequestStateCode) throws wt.util.WTPropertyVetoException {
      if (SAMPLE_REQUEST_STATE_CODE_UPPER_LIMIT < 1) {
         try { SAMPLE_REQUEST_STATE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sampleRequestStateCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SAMPLE_REQUEST_STATE_CODE_UPPER_LIMIT = 200; }
      }
      if (sampleRequestStateCode != null && !wt.fc.PersistenceHelper.checkStoredLength(sampleRequestStateCode.toString(), SAMPLE_REQUEST_STATE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sampleRequestStateCode"), java.lang.String.valueOf(java.lang.Math.min(SAMPLE_REQUEST_STATE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sampleRequestStateCode", this.sampleRequestStateCode, sampleRequestStateCode));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String SAMPLE_REQUEST_STATE_NAME = "sampleRequestStateName";
   static int SAMPLE_REQUEST_STATE_NAME_UPPER_LIMIT = -1;
   java.lang.String sampleRequestStateName;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getSampleRequestStateName() {
      return sampleRequestStateName;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setSampleRequestStateName(java.lang.String sampleRequestStateName) throws wt.util.WTPropertyVetoException {
      sampleRequestStateNameValidate(sampleRequestStateName);
      this.sampleRequestStateName = sampleRequestStateName;
   }
   void sampleRequestStateNameValidate(java.lang.String sampleRequestStateName) throws wt.util.WTPropertyVetoException {
      if (SAMPLE_REQUEST_STATE_NAME_UPPER_LIMIT < 1) {
         try { SAMPLE_REQUEST_STATE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sampleRequestStateName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SAMPLE_REQUEST_STATE_NAME_UPPER_LIMIT = 200; }
      }
      if (sampleRequestStateName != null && !wt.fc.PersistenceHelper.checkStoredLength(sampleRequestStateName.toString(), SAMPLE_REQUEST_STATE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sampleRequestStateName"), java.lang.String.valueOf(java.lang.Math.min(SAMPLE_REQUEST_STATE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sampleRequestStateName", this.sampleRequestStateName, sampleRequestStateName));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String REQUEST_DEPT_NAME = "requestDeptName";
   static int REQUEST_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String requestDeptName;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getRequestDeptName() {
      return requestDeptName;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setRequestDeptName(java.lang.String requestDeptName) throws wt.util.WTPropertyVetoException {
      requestDeptNameValidate(requestDeptName);
      this.requestDeptName = requestDeptName;
   }
   void requestDeptNameValidate(java.lang.String requestDeptName) throws wt.util.WTPropertyVetoException {
      if (REQUEST_DEPT_NAME_UPPER_LIMIT < 1) {
         try { REQUEST_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUEST_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (requestDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(requestDeptName.toString(), REQUEST_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestDeptName"), java.lang.String.valueOf(java.lang.Math.min(REQUEST_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestDeptName", this.requestDeptName, requestDeptName));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String REQUEST_DEPT_CODE = "requestDeptCode";
   static int REQUEST_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String requestDeptCode;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getRequestDeptCode() {
      return requestDeptCode;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setRequestDeptCode(java.lang.String requestDeptCode) throws wt.util.WTPropertyVetoException {
      requestDeptCodeValidate(requestDeptCode);
      this.requestDeptCode = requestDeptCode;
   }
   void requestDeptCodeValidate(java.lang.String requestDeptCode) throws wt.util.WTPropertyVetoException {
      if (REQUEST_DEPT_CODE_UPPER_LIMIT < 1) {
         try { REQUEST_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("requestDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQUEST_DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (requestDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(requestDeptCode.toString(), REQUEST_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requestDeptCode"), java.lang.String.valueOf(java.lang.Math.min(REQUEST_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "requestDeptCode", this.requestDeptCode, requestDeptCode));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String PURPOSE = "purpose";
   static int PURPOSE_UPPER_LIMIT = -1;
   java.lang.String purpose;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.String getPurpose() {
      return purpose;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setPurpose(java.lang.String purpose) throws wt.util.WTPropertyVetoException {
      purposeValidate(purpose);
      this.purpose = purpose;
   }
   void purposeValidate(java.lang.String purpose) throws wt.util.WTPropertyVetoException {
      if (PURPOSE_UPPER_LIMIT < 1) {
         try { PURPOSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("purpose").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PURPOSE_UPPER_LIMIT = 200; }
      }
      if (purpose != null && !wt.fc.PersistenceHelper.checkStoredLength(purpose.toString(), PURPOSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "purpose"), java.lang.String.valueOf(java.lang.Math.min(PURPOSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "purpose", this.purpose, purpose));
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String USER = "user";
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see ext.ket.sample.entity.KETSampleRequest
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7744443085650407754L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( carTypeCode );
      output.writeObject( carTypeName );
      output.writeObject( customerCode );
      output.writeObject( customerContractor );
      output.writeObject( customerName );
      output.writeObject( developeStageCode );
      output.writeObject( developeStageName );
      output.writeObject( format );
      output.writeObject( purpose );
      output.writeObject( requestDate );
      output.writeObject( requestDeptCode );
      output.writeObject( requestDeptName );
      output.writeObject( requestNo );
      output.writeObject( requestTitle );
      output.writeObject( sampleRequestStateCode );
      output.writeObject( sampleRequestStateName );
      output.writeObject( userReference );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
         output.writeObject( primary );
      }

   }

   protected void super_writeExternal_KETSampleRequest(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sample.entity.KETSampleRequest) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSampleRequest(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "carTypeCode", carTypeCode );
      output.setString( "carTypeName", carTypeName );
      output.setString( "customerCode", customerCode );
      output.setString( "customerContractor", customerContractor );
      output.setString( "customerName", customerName );
      output.setString( "developeStageCode", developeStageCode );
      output.setString( "developeStageName", developeStageName );
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setString( "purpose", purpose );
      output.setTimestamp( "requestDate", requestDate );
      output.setString( "requestDeptCode", requestDeptCode );
      output.setString( "requestDeptName", requestDeptName );
      output.setString( "requestNo", requestNo );
      output.setString( "requestTitle", requestTitle );
      output.setString( "sampleRequestStateCode", sampleRequestStateCode );
      output.setString( "sampleRequestStateName", sampleRequestStateName );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      carTypeCode = input.getString( "carTypeCode" );
      carTypeName = input.getString( "carTypeName" );
      customerCode = input.getString( "customerCode" );
      customerContractor = input.getString( "customerContractor" );
      customerName = input.getString( "customerName" );
      developeStageCode = input.getString( "developeStageCode" );
      developeStageName = input.getString( "developeStageName" );
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      purpose = input.getString( "purpose" );
      requestDate = input.getTimestamp( "requestDate" );
      requestDeptCode = input.getString( "requestDeptCode" );
      requestDeptName = input.getString( "requestDeptName" );
      requestNo = input.getString( "requestNo" );
      requestTitle = input.getString( "requestTitle" );
      sampleRequestStateCode = input.getString( "sampleRequestStateCode" );
      sampleRequestStateName = input.getString( "sampleRequestStateName" );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion7744443085650407754L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      carTypeCode = (java.lang.String) input.readObject();
      carTypeName = (java.lang.String) input.readObject();
      customerCode = (java.lang.String) input.readObject();
      customerContractor = (java.lang.String) input.readObject();
      customerName = (java.lang.String) input.readObject();
      developeStageCode = (java.lang.String) input.readObject();
      developeStageName = (java.lang.String) input.readObject();
      format = (wt.content.DataFormatReference) input.readObject();
      purpose = (java.lang.String) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();
      requestDeptCode = (java.lang.String) input.readObject();
      requestDeptName = (java.lang.String) input.readObject();
      requestNo = (java.lang.String) input.readObject();
      requestTitle = (java.lang.String) input.readObject();
      sampleRequestStateCode = (java.lang.String) input.readObject();
      sampleRequestStateName = (java.lang.String) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETSampleRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7744443085650407754L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSampleRequest( _KETSampleRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
