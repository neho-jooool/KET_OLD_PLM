package e3ps.project.elecSop;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CustomerSopHistory implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.elecSop.elecSopResource";
   static final java.lang.String CLASSNAME = CustomerSopHistory.class.getName();

   /**
    * 버전
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public static final java.lang.String REV = "rev";
   static int REV_UPPER_LIMIT = -1;
   java.lang.String rev;
   /**
    * 버전
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public java.lang.String getRev() {
      return rev;
   }
   /**
    * 버전
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public void setRev(java.lang.String rev) throws wt.util.WTPropertyVetoException {
      revValidate(rev);
      this.rev = rev;
   }
   void revValidate(java.lang.String rev) throws wt.util.WTPropertyVetoException {
      if (REV_UPPER_LIMIT < 1) {
         try { REV_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rev").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REV_UPPER_LIMIT = 200; }
      }
      if (rev != null && !wt.fc.PersistenceHelper.checkStoredLength(rev.toString(), REV_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rev"), java.lang.String.valueOf(java.lang.Math.min(REV_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rev", this.rev, rev));
   }

   /**
    * (목표)고객sop
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public static final java.lang.String SOP_DATE = "sopDate";
   java.sql.Timestamp sopDate;
   /**
    * (목표)고객sop
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public java.sql.Timestamp getSopDate() {
      return sopDate;
   }
   /**
    * (목표)고객sop
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public void setSopDate(java.sql.Timestamp sopDate) throws wt.util.WTPropertyVetoException {
      sopDateValidate(sopDate);
      this.sopDate = sopDate;
   }
   void sopDateValidate(java.sql.Timestamp sopDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 변경사유
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public static final java.lang.String CHANGE_REASON = "changeReason";
   static int CHANGE_REASON_UPPER_LIMIT = -1;
   java.lang.String changeReason;
   /**
    * 변경사유
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public java.lang.String getChangeReason() {
      return changeReason;
   }
   /**
    * 변경사유
    *
    * @see e3ps.project.elecSop.CustomerSopHistory
    */
   public void setChangeReason(java.lang.String changeReason) throws wt.util.WTPropertyVetoException {
      changeReasonValidate(changeReason);
      this.changeReason = changeReason;
   }
   void changeReasonValidate(java.lang.String changeReason) throws wt.util.WTPropertyVetoException {
      if (CHANGE_REASON_UPPER_LIMIT < 1) {
         try { CHANGE_REASON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeReason").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_REASON_UPPER_LIMIT = 4000; }
      }
      if (changeReason != null && !wt.fc.PersistenceHelper.checkStoredLength(changeReason.toString(), CHANGE_REASON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeReason"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_REASON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeReason", this.changeReason, changeReason));
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

   public static final long EXTERNALIZATION_VERSION_UID = 228701968247064908L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( changeReason );
      output.writeObject( rev );
      output.writeObject( sopDate );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.elecSop.CustomerSopHistory) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "changeReason", changeReason );
      output.setString( "rev", rev );
      output.setTimestamp( "sopDate", sopDate );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      changeReason = input.getString( "changeReason" );
      rev = input.getString( "rev" );
      sopDate = input.getTimestamp( "sopDate" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion228701968247064908L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      changeReason = (java.lang.String) input.readObject();
      rev = (java.lang.String) input.readObject();
      sopDate = (java.sql.Timestamp) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CustomerSopHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion228701968247064908L( input, readSerialVersionUID, superDone );
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
