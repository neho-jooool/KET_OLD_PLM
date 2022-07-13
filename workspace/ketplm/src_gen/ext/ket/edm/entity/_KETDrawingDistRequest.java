package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDrawingDistRequest extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDrawingDistRequest.class.getName();

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String DIST_TYPE = "distType";
   static int DIST_TYPE_UPPER_LIMIT = -1;
   java.lang.String distType;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getDistType() {
      return distType;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setDistType(java.lang.String distType) throws wt.util.WTPropertyVetoException {
      distTypeValidate(distType);
      this.distType = distType;
   }
   void distTypeValidate(java.lang.String distType) throws wt.util.WTPropertyVetoException {
      if (DIST_TYPE_UPPER_LIMIT < 1) {
         try { DIST_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("distType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIST_TYPE_UPPER_LIMIT = 200; }
      }
      if (distType != null && !wt.fc.PersistenceHelper.checkStoredLength(distType.toString(), DIST_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distType"), java.lang.String.valueOf(java.lang.Math.min(DIST_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "distType", this.distType, distType));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String DIST_REASON = "distReason";
   static int DIST_REASON_UPPER_LIMIT = -1;
   java.lang.String distReason;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getDistReason() {
      return distReason;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setDistReason(java.lang.String distReason) throws wt.util.WTPropertyVetoException {
      distReasonValidate(distReason);
      this.distReason = distReason;
   }
   void distReasonValidate(java.lang.String distReason) throws wt.util.WTPropertyVetoException {
      if (DIST_REASON_UPPER_LIMIT < 1) {
         try { DIST_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("distReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIST_REASON_UPPER_LIMIT = 4000; }
      }
      if (distReason != null && !wt.fc.PersistenceHelper.checkStoredLength(distReason.toString(), DIST_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distReason"), java.lang.String.valueOf(java.lang.Math.min(DIST_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "distReason", this.distReason, distReason));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String DOWNLOAD_EXPIRE_DATE = "downloadExpireDate";
   java.sql.Timestamp downloadExpireDate;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.sql.Timestamp getDownloadExpireDate() {
      return downloadExpireDate;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setDownloadExpireDate(java.sql.Timestamp downloadExpireDate) throws wt.util.WTPropertyVetoException {
      downloadExpireDateValidate(downloadExpireDate);
      this.downloadExpireDate = downloadExpireDate;
   }
   void downloadExpireDateValidate(java.sql.Timestamp downloadExpireDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String DIST_SUBCONTRACTOR = "distSubcontractor";
   static int DIST_SUBCONTRACTOR_UPPER_LIMIT = -1;
   java.lang.String distSubcontractor;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getDistSubcontractor() {
      return distSubcontractor;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setDistSubcontractor(java.lang.String distSubcontractor) throws wt.util.WTPropertyVetoException {
      distSubcontractorValidate(distSubcontractor);
      this.distSubcontractor = distSubcontractor;
   }
   void distSubcontractorValidate(java.lang.String distSubcontractor) throws wt.util.WTPropertyVetoException {
      if (DIST_SUBCONTRACTOR_UPPER_LIMIT < 1) {
         try { DIST_SUBCONTRACTOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("distSubcontractor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIST_SUBCONTRACTOR_UPPER_LIMIT = 4000; }
      }
      if (distSubcontractor != null && !wt.fc.PersistenceHelper.checkStoredLength(distSubcontractor.toString(), DIST_SUBCONTRACTOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distSubcontractor"), java.lang.String.valueOf(java.lang.Math.min(DIST_SUBCONTRACTOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "distSubcontractor", this.distSubcontractor, distSubcontractor));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String WRITE_DEPT_EN_NAME = "writeDeptEnName";
   static int WRITE_DEPT_EN_NAME_UPPER_LIMIT = -1;
   java.lang.String writeDeptEnName;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getWriteDeptEnName() {
      return writeDeptEnName;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setWriteDeptEnName(java.lang.String writeDeptEnName) throws wt.util.WTPropertyVetoException {
      writeDeptEnNameValidate(writeDeptEnName);
      this.writeDeptEnName = writeDeptEnName;
   }
   void writeDeptEnNameValidate(java.lang.String writeDeptEnName) throws wt.util.WTPropertyVetoException {
      if (WRITE_DEPT_EN_NAME_UPPER_LIMIT < 1) {
         try { WRITE_DEPT_EN_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("writeDeptEnName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WRITE_DEPT_EN_NAME_UPPER_LIMIT = 200; }
      }
      if (writeDeptEnName != null && !wt.fc.PersistenceHelper.checkStoredLength(writeDeptEnName.toString(), WRITE_DEPT_EN_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "writeDeptEnName"), java.lang.String.valueOf(java.lang.Math.min(WRITE_DEPT_EN_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "writeDeptEnName", this.writeDeptEnName, writeDeptEnName));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String WRITE_DEPT_KR_NAME = "writeDeptKrName";
   static int WRITE_DEPT_KR_NAME_UPPER_LIMIT = -1;
   java.lang.String writeDeptKrName;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getWriteDeptKrName() {
      return writeDeptKrName;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setWriteDeptKrName(java.lang.String writeDeptKrName) throws wt.util.WTPropertyVetoException {
      writeDeptKrNameValidate(writeDeptKrName);
      this.writeDeptKrName = writeDeptKrName;
   }
   void writeDeptKrNameValidate(java.lang.String writeDeptKrName) throws wt.util.WTPropertyVetoException {
      if (WRITE_DEPT_KR_NAME_UPPER_LIMIT < 1) {
         try { WRITE_DEPT_KR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("writeDeptKrName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WRITE_DEPT_KR_NAME_UPPER_LIMIT = 200; }
      }
      if (writeDeptKrName != null && !wt.fc.PersistenceHelper.checkStoredLength(writeDeptKrName.toString(), WRITE_DEPT_KR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "writeDeptKrName"), java.lang.String.valueOf(java.lang.Math.min(WRITE_DEPT_KR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "writeDeptKrName", this.writeDeptKrName, writeDeptKrName));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String WRITE_DEPT_CODE = "writeDeptCode";
   static int WRITE_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String writeDeptCode;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getWriteDeptCode() {
      return writeDeptCode;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setWriteDeptCode(java.lang.String writeDeptCode) throws wt.util.WTPropertyVetoException {
      writeDeptCodeValidate(writeDeptCode);
      this.writeDeptCode = writeDeptCode;
   }
   void writeDeptCodeValidate(java.lang.String writeDeptCode) throws wt.util.WTPropertyVetoException {
      if (WRITE_DEPT_CODE_UPPER_LIMIT < 1) {
         try { WRITE_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("writeDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WRITE_DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (writeDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(writeDeptCode.toString(), WRITE_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "writeDeptCode"), java.lang.String.valueOf(java.lang.Math.min(WRITE_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "writeDeptCode", this.writeDeptCode, writeDeptCode));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String DIST_DEPT_NAME = "distDeptName";
   static int DIST_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String distDeptName;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getDistDeptName() {
      return distDeptName;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setDistDeptName(java.lang.String distDeptName) throws wt.util.WTPropertyVetoException {
      distDeptNameValidate(distDeptName);
      this.distDeptName = distDeptName;
   }
   void distDeptNameValidate(java.lang.String distDeptName) throws wt.util.WTPropertyVetoException {
      if (DIST_DEPT_NAME_UPPER_LIMIT < 1) {
         try { DIST_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("distDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIST_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (distDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(distDeptName.toString(), DIST_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distDeptName"), java.lang.String.valueOf(java.lang.Math.min(DIST_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "distDeptName", this.distDeptName, distDeptName));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String BACKGROUND_YN = "backgroundYn";
   static int BACKGROUND_YN_UPPER_LIMIT = -1;
   java.lang.String backgroundYn;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getBackgroundYn() {
      return backgroundYn;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setBackgroundYn(java.lang.String backgroundYn) throws wt.util.WTPropertyVetoException {
      backgroundYnValidate(backgroundYn);
      this.backgroundYn = backgroundYn;
   }
   void backgroundYnValidate(java.lang.String backgroundYn) throws wt.util.WTPropertyVetoException {
      if (BACKGROUND_YN_UPPER_LIMIT < 1) {
         try { BACKGROUND_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("backgroundYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BACKGROUND_YN_UPPER_LIMIT = 200; }
      }
      if (backgroundYn != null && !wt.fc.PersistenceHelper.checkStoredLength(backgroundYn.toString(), BACKGROUND_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "backgroundYn"), java.lang.String.valueOf(java.lang.Math.min(BACKGROUND_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "backgroundYn", this.backgroundYn, backgroundYn));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String HP_SEND_STATUS = "hpSendStatus";
   static int HP_SEND_STATUS_UPPER_LIMIT = -1;
   java.lang.String hpSendStatus;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getHpSendStatus() {
      return hpSendStatus;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setHpSendStatus(java.lang.String hpSendStatus) throws wt.util.WTPropertyVetoException {
      hpSendStatusValidate(hpSendStatus);
      this.hpSendStatus = hpSendStatus;
   }
   void hpSendStatusValidate(java.lang.String hpSendStatus) throws wt.util.WTPropertyVetoException {
      if (HP_SEND_STATUS_UPPER_LIMIT < 1) {
         try { HP_SEND_STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("hpSendStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HP_SEND_STATUS_UPPER_LIMIT = 200; }
      }
      if (hpSendStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(hpSendStatus.toString(), HP_SEND_STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "hpSendStatus"), java.lang.String.valueOf(java.lang.Math.min(HP_SEND_STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "hpSendStatus", this.hpSendStatus, hpSendStatus));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String HP_SEND_DATE = "hpSendDate";
   java.sql.Timestamp hpSendDate;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.sql.Timestamp getHpSendDate() {
      return hpSendDate;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setHpSendDate(java.sql.Timestamp hpSendDate) throws wt.util.WTPropertyVetoException {
      hpSendDateValidate(hpSendDate);
      this.hpSendDate = hpSendDate;
   }
   void hpSendDateValidate(java.sql.Timestamp hpSendDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String REF_PART = "refPart";
   static int REF_PART_UPPER_LIMIT = -1;
   java.lang.String refPart;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getRefPart() {
      return refPart;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setRefPart(java.lang.String refPart) throws wt.util.WTPropertyVetoException {
      refPartValidate(refPart);
      this.refPart = refPart;
   }
   void refPartValidate(java.lang.String refPart) throws wt.util.WTPropertyVetoException {
      if (REF_PART_UPPER_LIMIT < 1) {
         try { REF_PART_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("refPart").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REF_PART_UPPER_LIMIT = 200; }
      }
      if (refPart != null && !wt.fc.PersistenceHelper.checkStoredLength(refPart.toString(), REF_PART_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "refPart"), java.lang.String.valueOf(java.lang.Math.min(REF_PART_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "refPart", this.refPart, refPart));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public static final java.lang.String ETC_YN = "etcYn";
   static int ETC_YN_UPPER_LIMIT = -1;
   java.lang.String etcYn;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public java.lang.String getEtcYn() {
      return etcYn;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistRequest
    */
   public void setEtcYn(java.lang.String etcYn) throws wt.util.WTPropertyVetoException {
      etcYnValidate(etcYn);
      this.etcYn = etcYn;
   }
   void etcYnValidate(java.lang.String etcYn) throws wt.util.WTPropertyVetoException {
      if (ETC_YN_UPPER_LIMIT < 1) {
         try { ETC_YN_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcYn").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_YN_UPPER_LIMIT = 200; }
      }
      if (etcYn != null && !wt.fc.PersistenceHelper.checkStoredLength(etcYn.toString(), ETC_YN_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcYn"), java.lang.String.valueOf(java.lang.Math.min(ETC_YN_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcYn", this.etcYn, etcYn));
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

   public static final long EXTERNALIZATION_VERSION_UID = 5375152102523259642L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( backgroundYn );
      output.writeObject( distDeptName );
      output.writeObject( distReason );
      output.writeObject( distSubcontractor );
      output.writeObject( distType );
      output.writeObject( downloadExpireDate );
      output.writeObject( etcYn );
      output.writeObject( hpSendDate );
      output.writeObject( hpSendStatus );
      output.writeObject( refPart );
      output.writeObject( writeDeptCode );
      output.writeObject( writeDeptEnName );
      output.writeObject( writeDeptKrName );
   }

   protected void super_writeExternal_KETDrawingDistRequest(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETDrawingDistRequest) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDrawingDistRequest(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "backgroundYn", backgroundYn );
      output.setString( "distDeptName", distDeptName );
      output.setString( "distReason", distReason );
      output.setString( "distSubcontractor", distSubcontractor );
      output.setString( "distType", distType );
      output.setTimestamp( "downloadExpireDate", downloadExpireDate );
      output.setString( "etcYn", etcYn );
      output.setTimestamp( "hpSendDate", hpSendDate );
      output.setString( "hpSendStatus", hpSendStatus );
      output.setString( "refPart", refPart );
      output.setString( "writeDeptCode", writeDeptCode );
      output.setString( "writeDeptEnName", writeDeptEnName );
      output.setString( "writeDeptKrName", writeDeptKrName );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      backgroundYn = input.getString( "backgroundYn" );
      distDeptName = input.getString( "distDeptName" );
      distReason = input.getString( "distReason" );
      distSubcontractor = input.getString( "distSubcontractor" );
      distType = input.getString( "distType" );
      downloadExpireDate = input.getTimestamp( "downloadExpireDate" );
      etcYn = input.getString( "etcYn" );
      hpSendDate = input.getTimestamp( "hpSendDate" );
      hpSendStatus = input.getString( "hpSendStatus" );
      refPart = input.getString( "refPart" );
      writeDeptCode = input.getString( "writeDeptCode" );
      writeDeptEnName = input.getString( "writeDeptEnName" );
      writeDeptKrName = input.getString( "writeDeptKrName" );
   }

   boolean readVersion5375152102523259642L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      backgroundYn = (java.lang.String) input.readObject();
      distDeptName = (java.lang.String) input.readObject();
      distReason = (java.lang.String) input.readObject();
      distSubcontractor = (java.lang.String) input.readObject();
      distType = (java.lang.String) input.readObject();
      downloadExpireDate = (java.sql.Timestamp) input.readObject();
      etcYn = (java.lang.String) input.readObject();
      hpSendDate = (java.sql.Timestamp) input.readObject();
      hpSendStatus = (java.lang.String) input.readObject();
      refPart = (java.lang.String) input.readObject();
      writeDeptCode = (java.lang.String) input.readObject();
      writeDeptEnName = (java.lang.String) input.readObject();
      writeDeptKrName = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDrawingDistRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5375152102523259642L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDrawingDistRequest( _KETDrawingDistRequest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
