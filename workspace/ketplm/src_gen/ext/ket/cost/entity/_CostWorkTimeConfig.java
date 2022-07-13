package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostWorkTimeConfig implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostWorkTimeConfig.class.getName();

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String PART_TYPE_OID = "partTypeOid";
   static int PART_TYPE_OID_UPPER_LIMIT = -1;
   java.lang.String partTypeOid;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.String getPartTypeOid() {
      return partTypeOid;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setPartTypeOid(java.lang.String partTypeOid) throws wt.util.WTPropertyVetoException {
      partTypeOidValidate(partTypeOid);
      this.partTypeOid = partTypeOid;
   }
   void partTypeOidValidate(java.lang.String partTypeOid) throws wt.util.WTPropertyVetoException {
      if (PART_TYPE_OID_UPPER_LIMIT < 1) {
         try { PART_TYPE_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partTypeOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_TYPE_OID_UPPER_LIMIT = 200; }
      }
      if (partTypeOid != null && !wt.fc.PersistenceHelper.checkStoredLength(partTypeOid.toString(), PART_TYPE_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partTypeOid"), java.lang.String.valueOf(java.lang.Math.min(PART_TYPE_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partTypeOid", this.partTypeOid, partTypeOid));
   }

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String MFT_FACTORY1 = "mftFactory1";
   static int MFT_FACTORY1_UPPER_LIMIT = -1;
   java.lang.String mftFactory1;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.String getMftFactory1() {
      return mftFactory1;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setMftFactory1(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      mftFactory1Validate(mftFactory1);
      this.mftFactory1 = mftFactory1;
   }
   void mftFactory1Validate(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY1_UPPER_LIMIT < 1) {
         try { MFT_FACTORY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY1_UPPER_LIMIT = 200; }
      }
      if (mftFactory1 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory1.toString(), MFT_FACTORY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory1"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory1", this.mftFactory1, mftFactory1));
   }

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String MFT_FACTORY2 = "mftFactory2";
   static int MFT_FACTORY2_UPPER_LIMIT = -1;
   java.lang.String mftFactory2;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.String getMftFactory2() {
      return mftFactory2;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setMftFactory2(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      mftFactory2Validate(mftFactory2);
      this.mftFactory2 = mftFactory2;
   }
   void mftFactory2Validate(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY2_UPPER_LIMIT < 1) {
         try { MFT_FACTORY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY2_UPPER_LIMIT = 200; }
      }
      if (mftFactory2 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory2.toString(), MFT_FACTORY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory2"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory2", this.mftFactory2, mftFactory2));
   }

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String WORK_HOUR = "workHour";
   static int WORK_HOUR_UPPER_LIMIT = -1;
   java.lang.String workHour;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.String getWorkHour() {
      return workHour;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setWorkHour(java.lang.String workHour) throws wt.util.WTPropertyVetoException {
      workHourValidate(workHour);
      this.workHour = workHour;
   }
   void workHourValidate(java.lang.String workHour) throws wt.util.WTPropertyVetoException {
      if (WORK_HOUR_UPPER_LIMIT < 1) {
         try { WORK_HOUR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workHour").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_HOUR_UPPER_LIMIT = 200; }
      }
      if (workHour != null && !wt.fc.PersistenceHelper.checkStoredLength(workHour.toString(), WORK_HOUR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workHour"), java.lang.String.valueOf(java.lang.Math.min(WORK_HOUR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workHour", this.workHour, workHour));
   }

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String WORK_DAY = "workDay";
   static int WORK_DAY_UPPER_LIMIT = -1;
   java.lang.String workDay;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.String getWorkDay() {
      return workDay;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setWorkDay(java.lang.String workDay) throws wt.util.WTPropertyVetoException {
      workDayValidate(workDay);
      this.workDay = workDay;
   }
   void workDayValidate(java.lang.String workDay) throws wt.util.WTPropertyVetoException {
      if (WORK_DAY_UPPER_LIMIT < 1) {
         try { WORK_DAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workDay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_DAY_UPPER_LIMIT = 200; }
      }
      if (workDay != null && !wt.fc.PersistenceHelper.checkStoredLength(workDay.toString(), WORK_DAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workDay"), java.lang.String.valueOf(java.lang.Math.min(WORK_DAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workDay", this.workDay, workDay));
   }

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String WORK_YEAR = "workYear";
   static int WORK_YEAR_UPPER_LIMIT = -1;
   java.lang.String workYear;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.String getWorkYear() {
      return workYear;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setWorkYear(java.lang.String workYear) throws wt.util.WTPropertyVetoException {
      workYearValidate(workYear);
      this.workYear = workYear;
   }
   void workYearValidate(java.lang.String workYear) throws wt.util.WTPropertyVetoException {
      if (WORK_YEAR_UPPER_LIMIT < 1) {
         try { WORK_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_YEAR_UPPER_LIMIT = 200; }
      }
      if (workYear != null && !wt.fc.PersistenceHelper.checkStoredLength(workYear.toString(), WORK_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workYear"), java.lang.String.valueOf(java.lang.Math.min(WORK_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workYear", this.workYear, workYear));
   }

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String CAPA_YEAR = "capaYear";
   static int CAPA_YEAR_UPPER_LIMIT = -1;
   java.lang.String capaYear;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.String getCapaYear() {
      return capaYear;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setCapaYear(java.lang.String capaYear) throws wt.util.WTPropertyVetoException {
      capaYearValidate(capaYear);
      this.capaYear = capaYear;
   }
   void capaYearValidate(java.lang.String capaYear) throws wt.util.WTPropertyVetoException {
      if (CAPA_YEAR_UPPER_LIMIT < 1) {
         try { CAPA_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("capaYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAPA_YEAR_UPPER_LIMIT = 200; }
      }
      if (capaYear != null && !wt.fc.PersistenceHelper.checkStoredLength(capaYear.toString(), CAPA_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "capaYear"), java.lang.String.valueOf(java.lang.Math.min(CAPA_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "capaYear", this.capaYear, capaYear));
   }

   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public static final java.lang.String SORT_LOCATION = "sortLocation";
   java.lang.Integer sortLocation;
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public java.lang.Integer getSortLocation() {
      return sortLocation;
   }
   /**
    * @see ext.ket.cost.entity.CostWorkTimeConfig
    */
   public void setSortLocation(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
      sortLocationValidate(sortLocation);
      this.sortLocation = sortLocation;
   }
   void sortLocationValidate(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -8361996325923256112L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( capaYear );
      output.writeObject( mftFactory1 );
      output.writeObject( mftFactory2 );
      output.writeObject( owner );
      output.writeObject( partTypeOid );
      output.writeObject( sortLocation );
      output.writeObject( thePersistInfo );
      output.writeObject( workDay );
      output.writeObject( workHour );
      output.writeObject( workYear );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostWorkTimeConfig) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "capaYear", capaYear );
      output.setString( "mftFactory1", mftFactory1 );
      output.setString( "mftFactory2", mftFactory2 );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "partTypeOid", partTypeOid );
      output.setIntObject( "sortLocation", sortLocation );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "workDay", workDay );
      output.setString( "workHour", workHour );
      output.setString( "workYear", workYear );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      capaYear = input.getString( "capaYear" );
      mftFactory1 = input.getString( "mftFactory1" );
      mftFactory2 = input.getString( "mftFactory2" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partTypeOid = input.getString( "partTypeOid" );
      sortLocation = input.getIntObject( "sortLocation" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      workDay = input.getString( "workDay" );
      workHour = input.getString( "workHour" );
      workYear = input.getString( "workYear" );
   }

   boolean readVersion_8361996325923256112L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      capaYear = (java.lang.String) input.readObject();
      mftFactory1 = (java.lang.String) input.readObject();
      mftFactory2 = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partTypeOid = (java.lang.String) input.readObject();
      sortLocation = (java.lang.Integer) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      workDay = (java.lang.String) input.readObject();
      workHour = (java.lang.String) input.readObject();
      workYear = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostWorkTimeConfig thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8361996325923256112L( input, readSerialVersionUID, superDone );
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
