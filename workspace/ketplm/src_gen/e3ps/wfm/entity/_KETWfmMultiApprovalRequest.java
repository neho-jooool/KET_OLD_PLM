package e3ps.wfm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETWfmMultiApprovalRequest extends wt.enterprise.CabinetManaged implements e3ps.common.impl.OwnPersistable, wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.wfm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETWfmMultiApprovalRequest.class.getName();

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String REQ_NUMBER = "reqNumber";
   static int REQ_NUMBER_UPPER_LIMIT = -1;
   java.lang.String reqNumber;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getReqNumber() {
      return reqNumber;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setReqNumber(java.lang.String reqNumber) throws wt.util.WTPropertyVetoException {
      reqNumberValidate(reqNumber);
      this.reqNumber = reqNumber;
   }
   void reqNumberValidate(java.lang.String reqNumber) throws wt.util.WTPropertyVetoException {
      if (reqNumber == null || reqNumber.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reqNumber") },
               new java.beans.PropertyChangeEvent(this, "reqNumber", this.reqNumber, reqNumber));
      if (REQ_NUMBER_UPPER_LIMIT < 1) {
         try { REQ_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reqNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQ_NUMBER_UPPER_LIMIT = 30; }
      }
      if (reqNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(reqNumber.toString(), REQ_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reqNumber"), java.lang.String.valueOf(java.lang.Math.min(REQ_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reqNumber", this.reqNumber, reqNumber));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String REQ_NAME = "reqName";
   static int REQ_NAME_UPPER_LIMIT = -1;
   java.lang.String reqName;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getReqName() {
      return reqName;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setReqName(java.lang.String reqName) throws wt.util.WTPropertyVetoException {
      reqNameValidate(reqName);
      this.reqName = reqName;
   }
   void reqNameValidate(java.lang.String reqName) throws wt.util.WTPropertyVetoException {
      if (reqName == null || reqName.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reqName") },
               new java.beans.PropertyChangeEvent(this, "reqName", this.reqName, reqName));
      if (REQ_NAME_UPPER_LIMIT < 1) {
         try { REQ_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reqName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REQ_NAME_UPPER_LIMIT = 200; }
      }
      if (reqName != null && !wt.fc.PersistenceHelper.checkStoredLength(reqName.toString(), REQ_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reqName"), java.lang.String.valueOf(java.lang.Math.min(REQ_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reqName", this.reqName, reqName));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setDescription(java.lang.String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(java.lang.String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 2000; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), java.lang.String.valueOf(java.lang.Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute2(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      attribute2Validate(attribute2);
      this.attribute2 = attribute2;
   }
   void attribute2Validate(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE2_UPPER_LIMIT < 1) {
         try { ATTRIBUTE2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE2_UPPER_LIMIT = 2000; }
      }
      if (attribute2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute2.toString(), ATTRIBUTE2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute2"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute2", this.attribute2, attribute2));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute3(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      attribute3Validate(attribute3);
      this.attribute3 = attribute3;
   }
   void attribute3Validate(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE3_UPPER_LIMIT < 1) {
         try { ATTRIBUTE3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE3_UPPER_LIMIT = 2000; }
      }
      if (attribute3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute3.toString(), ATTRIBUTE3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute3"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute3", this.attribute3, attribute3));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute4(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      attribute4Validate(attribute4);
      this.attribute4 = attribute4;
   }
   void attribute4Validate(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE4_UPPER_LIMIT < 1) {
         try { ATTRIBUTE4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE4_UPPER_LIMIT = 2000; }
      }
      if (attribute4 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute4.toString(), ATTRIBUTE4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute4"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute4", this.attribute4, attribute4));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute5(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      attribute5Validate(attribute5);
      this.attribute5 = attribute5;
   }
   void attribute5Validate(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE5_UPPER_LIMIT < 1) {
         try { ATTRIBUTE5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE5_UPPER_LIMIT = 2000; }
      }
      if (attribute5 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute5.toString(), ATTRIBUTE5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute5"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute5", this.attribute5, attribute5));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute6(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      attribute6Validate(attribute6);
      this.attribute6 = attribute6;
   }
   void attribute6Validate(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE6_UPPER_LIMIT < 1) {
         try { ATTRIBUTE6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE6_UPPER_LIMIT = 2000; }
      }
      if (attribute6 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute6.toString(), ATTRIBUTE6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute6"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute6", this.attribute6, attribute6));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute7(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      attribute7Validate(attribute7);
      this.attribute7 = attribute7;
   }
   void attribute7Validate(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE7_UPPER_LIMIT < 1) {
         try { ATTRIBUTE7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE7_UPPER_LIMIT = 2000; }
      }
      if (attribute7 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute7.toString(), ATTRIBUTE7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute7"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute7", this.attribute7, attribute7));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute8(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      attribute8Validate(attribute8);
      this.attribute8 = attribute8;
   }
   void attribute8Validate(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE8_UPPER_LIMIT < 1) {
         try { ATTRIBUTE8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE8_UPPER_LIMIT = 2000; }
      }
      if (attribute8 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute8.toString(), ATTRIBUTE8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute8"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute8", this.attribute8, attribute8));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute9(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      attribute9Validate(attribute9);
      this.attribute9 = attribute9;
   }
   void attribute9Validate(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE9_UPPER_LIMIT < 1) {
         try { ATTRIBUTE9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE9_UPPER_LIMIT = 2000; }
      }
      if (attribute9 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute9.toString(), ATTRIBUTE9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute9"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute9", this.attribute9, attribute9));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiApprovalRequest
    */
   public void setAttribute10(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      attribute10Validate(attribute10);
      this.attribute10 = attribute10;
   }
   void attribute10Validate(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE10_UPPER_LIMIT < 1) {
         try { ATTRIBUTE10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE10_UPPER_LIMIT = 2000; }
      }
      if (attribute10 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute10.toString(), ATTRIBUTE10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute10"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute10", this.attribute10, attribute10));
   }

   wt.org.WTPrincipalReference owner;
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public wt.org.WTPrincipalReference getOwner() {
      return owner;
   }
   /**
    * @see e3ps.common.impl.OwnPersistable
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

   public static final long EXTERNALIZATION_VERSION_UID = -882457716586424213L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( attribute10 );
      output.writeObject( attribute2 );
      output.writeObject( attribute3 );
      output.writeObject( attribute4 );
      output.writeObject( attribute5 );
      output.writeObject( attribute6 );
      output.writeObject( attribute7 );
      output.writeObject( attribute8 );
      output.writeObject( attribute9 );
      output.writeObject( containerReference );
      output.writeObject( description );
      output.writeObject( owner );
      output.writeObject( reqName );
      output.writeObject( reqNumber );
      output.writeObject( requestDate );
   }

   protected void super_writeExternal_KETWfmMultiApprovalRequest(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.wfm.entity.KETWfmMultiApprovalRequest) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETWfmMultiApprovalRequest(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "attribute10", attribute10 );
      output.setString( "attribute2", attribute2 );
      output.setString( "attribute3", attribute3 );
      output.setString( "attribute4", attribute4 );
      output.setString( "attribute5", attribute5 );
      output.setString( "attribute6", attribute6 );
      output.setString( "attribute7", attribute7 );
      output.setString( "attribute8", attribute8 );
      output.setString( "attribute9", attribute9 );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "description", description );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "reqName", reqName );
      output.setString( "reqNumber", reqNumber );
      output.setTimestamp( "requestDate", requestDate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      attribute10 = input.getString( "attribute10" );
      attribute2 = input.getString( "attribute2" );
      attribute3 = input.getString( "attribute3" );
      attribute4 = input.getString( "attribute4" );
      attribute5 = input.getString( "attribute5" );
      attribute6 = input.getString( "attribute6" );
      attribute7 = input.getString( "attribute7" );
      attribute8 = input.getString( "attribute8" );
      attribute9 = input.getString( "attribute9" );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      description = input.getString( "description" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      reqName = input.getString( "reqName" );
      reqNumber = input.getString( "reqNumber" );
      requestDate = input.getTimestamp( "requestDate" );
   }

   boolean readVersion_882457716586424213L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      attribute10 = (java.lang.String) input.readObject();
      attribute2 = (java.lang.String) input.readObject();
      attribute3 = (java.lang.String) input.readObject();
      attribute4 = (java.lang.String) input.readObject();
      attribute5 = (java.lang.String) input.readObject();
      attribute6 = (java.lang.String) input.readObject();
      attribute7 = (java.lang.String) input.readObject();
      attribute8 = (java.lang.String) input.readObject();
      attribute9 = (java.lang.String) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      description = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      reqName = (java.lang.String) input.readObject();
      reqNumber = (java.lang.String) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();
      return true;
   }

   protected boolean readVersion( KETWfmMultiApprovalRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_882457716586424213L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETWfmMultiApprovalRequest( _KETWfmMultiApprovalRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
