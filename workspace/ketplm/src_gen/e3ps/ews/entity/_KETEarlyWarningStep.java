package e3ps.ews.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETEarlyWarningStep extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ews.entity.entityResource";
   static final java.lang.String CLASSNAME = KETEarlyWarningStep.class.getName();

   /**
    * 초기유동관리 진행단계
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public static final java.lang.String STATUS = "status";
   static int STATUS_UPPER_LIMIT = -1;
   java.lang.String status;
   /**
    * 초기유동관리 진행단계
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public java.lang.String getStatus() {
      return status;
   }
   /**
    * 초기유동관리 진행단계
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public void setStatus(java.lang.String status) throws wt.util.WTPropertyVetoException {
      statusValidate(status);
      this.status = status;
   }
   void statusValidate(java.lang.String status) throws wt.util.WTPropertyVetoException {
      if (STATUS_UPPER_LIMIT < 1) {
         try { STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("status").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STATUS_UPPER_LIMIT = 200; }
      }
      if (status != null && !wt.fc.PersistenceHelper.checkStoredLength(status.toString(), STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "status"), java.lang.String.valueOf(java.lang.Math.min(STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "status", this.status, status));
   }

   /**
    * 수행담당자
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public static final java.lang.String FST_CHARGE = "fstCharge";
   static int FST_CHARGE_UPPER_LIMIT = -1;
   java.lang.String fstCharge;
   /**
    * 수행담당자
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public java.lang.String getFstCharge() {
      return fstCharge;
   }
   /**
    * 수행담당자
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public void setFstCharge(java.lang.String fstCharge) throws wt.util.WTPropertyVetoException {
      fstChargeValidate(fstCharge);
      this.fstCharge = fstCharge;
   }
   void fstChargeValidate(java.lang.String fstCharge) throws wt.util.WTPropertyVetoException {
      if (FST_CHARGE_UPPER_LIMIT < 1) {
         try { FST_CHARGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("fstCharge").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FST_CHARGE_UPPER_LIMIT = 200; }
      }
      if (fstCharge != null && !wt.fc.PersistenceHelper.checkStoredLength(fstCharge.toString(), FST_CHARGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "fstCharge"), java.lang.String.valueOf(java.lang.Math.min(FST_CHARGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "fstCharge", this.fstCharge, fstCharge));
   }

   /**
    * 활동완료일
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public static final java.lang.String END_DATE = "endDate";
   java.sql.Timestamp endDate;
   /**
    * 활동완료일
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public java.sql.Timestamp getEndDate() {
      return endDate;
   }
   /**
    * 활동완료일
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public void setEndDate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 중단일자
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public static final java.lang.String STOPDATE = "stopdate";
   java.sql.Timestamp stopdate;
   /**
    * 중단일자
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public java.sql.Timestamp getStopdate() {
      return stopdate;
   }
   /**
    * 중단일자
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public void setStopdate(java.sql.Timestamp stopdate) throws wt.util.WTPropertyVetoException {
      stopdateValidate(stopdate);
      this.stopdate = stopdate;
   }
   void stopdateValidate(java.sql.Timestamp stopdate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 중단이유
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public static final java.lang.String REASON = "reason";
   static int REASON_UPPER_LIMIT = -1;
   java.lang.String reason;
   /**
    * 중단이유
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public java.lang.String getReason() {
      return reason;
   }
   /**
    * 중단이유
    *
    * @see e3ps.ews.entity.KETEarlyWarningStep
    */
   public void setReason(java.lang.String reason) throws wt.util.WTPropertyVetoException {
      reasonValidate(reason);
      this.reason = reason;
   }
   void reasonValidate(java.lang.String reason) throws wt.util.WTPropertyVetoException {
      if (REASON_UPPER_LIMIT < 1) {
         try { REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REASON_UPPER_LIMIT = 4000; }
      }
      if (reason != null && !wt.fc.PersistenceHelper.checkStoredLength(reason.toString(), REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reason"), java.lang.String.valueOf(java.lang.Math.min(REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reason", this.reason, reason));
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

   public static final long EXTERNALIZATION_VERSION_UID = -8733752562146068259L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( endDate );
      output.writeObject( fstCharge );
      output.writeObject( reason );
      output.writeObject( status );
      output.writeObject( stopdate );
   }

   protected void super_writeExternal_KETEarlyWarningStep(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ews.entity.KETEarlyWarningStep) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETEarlyWarningStep(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setTimestamp( "endDate", endDate );
      output.setString( "fstCharge", fstCharge );
      output.setString( "reason", reason );
      output.setString( "status", status );
      output.setTimestamp( "stopdate", stopdate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      endDate = input.getTimestamp( "endDate" );
      fstCharge = input.getString( "fstCharge" );
      reason = input.getString( "reason" );
      status = input.getString( "status" );
      stopdate = input.getTimestamp( "stopdate" );
   }

   boolean readVersion_8733752562146068259L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      endDate = (java.sql.Timestamp) input.readObject();
      fstCharge = (java.lang.String) input.readObject();
      reason = (java.lang.String) input.readObject();
      status = (java.lang.String) input.readObject();
      stopdate = (java.sql.Timestamp) input.readObject();
      return true;
   }

   protected boolean readVersion( KETEarlyWarningStep thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8733752562146068259L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETEarlyWarningStep( _KETEarlyWarningStep thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
