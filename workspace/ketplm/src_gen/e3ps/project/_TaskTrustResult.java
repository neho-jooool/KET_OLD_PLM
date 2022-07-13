package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TaskTrustResult extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = TaskTrustResult.class.getName();

   /**
    * 신뢰성평가차수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public static final java.lang.String REV = "rev";
   int rev = 1;
   /**
    * 신뢰성평가차수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public int getRev() {
      return rev;
   }
   /**
    * 신뢰성평가차수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public void setRev(int rev) throws wt.util.WTPropertyVetoException {
      revValidate(rev);
      this.rev = rev;
   }
   void revValidate(int rev) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 평가일
    *
    * @see e3ps.project.TaskTrustResult
    */
   public static final java.lang.String ESTIMATE_DATE = "estimateDate";
   static int ESTIMATE_DATE_UPPER_LIMIT = -1;
   java.lang.String estimateDate;
   /**
    * 평가일
    *
    * @see e3ps.project.TaskTrustResult
    */
   public java.lang.String getEstimateDate() {
      return estimateDate;
   }
   /**
    * 평가일
    *
    * @see e3ps.project.TaskTrustResult
    */
   public void setEstimateDate(java.lang.String estimateDate) throws wt.util.WTPropertyVetoException {
      estimateDateValidate(estimateDate);
      this.estimateDate = estimateDate;
   }
   void estimateDateValidate(java.lang.String estimateDate) throws wt.util.WTPropertyVetoException {
      if (ESTIMATE_DATE_UPPER_LIMIT < 1) {
         try { ESTIMATE_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("estimateDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ESTIMATE_DATE_UPPER_LIMIT = 200; }
      }
      if (estimateDate != null && !wt.fc.PersistenceHelper.checkStoredLength(estimateDate.toString(), ESTIMATE_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "estimateDate"), java.lang.String.valueOf(java.lang.Math.min(ESTIMATE_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "estimateDate", this.estimateDate, estimateDate));
   }

   /**
    * OK 갯수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public static final java.lang.String OK_CNT = "okCnt";
   int okCnt = 0;
   /**
    * OK 갯수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public int getOkCnt() {
      return okCnt;
   }
   /**
    * OK 갯수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public void setOkCnt(int okCnt) throws wt.util.WTPropertyVetoException {
      okCntValidate(okCnt);
      this.okCnt = okCnt;
   }
   void okCntValidate(int okCnt) throws wt.util.WTPropertyVetoException {
   }

   /**
    * NG 갯수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public static final java.lang.String NG_CNT = "ngCnt";
   int ngCnt = 0;
   /**
    * NG 갯수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public int getNgCnt() {
      return ngCnt;
   }
   /**
    * NG 갯수
    *
    * @see e3ps.project.TaskTrustResult
    */
   public void setNgCnt(int ngCnt) throws wt.util.WTPropertyVetoException {
      ngCntValidate(ngCnt);
      this.ngCnt = ngCnt;
   }
   void ngCntValidate(int ngCnt) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 사유 및 항목
    *
    * @see e3ps.project.TaskTrustResult
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * 사유 및 항목
    *
    * @see e3ps.project.TaskTrustResult
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * 사유 및 항목
    *
    * @see e3ps.project.TaskTrustResult
    */
   public void setDescription(java.lang.String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(java.lang.String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 4000; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), java.lang.String.valueOf(java.lang.Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
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

   public static final long EXTERNALIZATION_VERSION_UID = -4587068625017260754L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( description );
      output.writeObject( estimateDate );
      output.writeInt( ngCnt );
      output.writeInt( okCnt );
      output.writeInt( rev );
   }

   protected void super_writeExternal_TaskTrustResult(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.TaskTrustResult) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_TaskTrustResult(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "description", description );
      output.setString( "estimateDate", estimateDate );
      output.setInt( "ngCnt", ngCnt );
      output.setInt( "okCnt", okCnt );
      output.setInt( "rev", rev );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      description = input.getString( "description" );
      estimateDate = input.getString( "estimateDate" );
      ngCnt = input.getInt( "ngCnt" );
      okCnt = input.getInt( "okCnt" );
      rev = input.getInt( "rev" );
   }

   boolean readVersion_4587068625017260754L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      description = (java.lang.String) input.readObject();
      estimateDate = (java.lang.String) input.readObject();
      ngCnt = input.readInt();
      okCnt = input.readInt();
      rev = input.readInt();
      return true;
   }

   protected boolean readVersion( TaskTrustResult thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4587068625017260754L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_TaskTrustResult( _TaskTrustResult thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
