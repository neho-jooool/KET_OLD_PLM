package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CSInfo implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CSInfo.class.getName();

   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public static final java.lang.String INFO_TYPE = "infoType";
   static int INFO_TYPE_UPPER_LIMIT = -1;
   java.lang.String infoType;
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public java.lang.String getInfoType() {
      return infoType;
   }
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public void setInfoType(java.lang.String infoType) throws wt.util.WTPropertyVetoException {
      infoTypeValidate(infoType);
      this.infoType = infoType;
   }
   void infoTypeValidate(java.lang.String infoType) throws wt.util.WTPropertyVetoException {
      if (INFO_TYPE_UPPER_LIMIT < 1) {
         try { INFO_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("infoType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INFO_TYPE_UPPER_LIMIT = 200; }
      }
      if (infoType != null && !wt.fc.PersistenceHelper.checkStoredLength(infoType.toString(), INFO_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "infoType"), java.lang.String.valueOf(java.lang.Math.min(INFO_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "infoType", this.infoType, infoType));
   }

   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public static final java.lang.String R_MAT_CODE = "rMatCode";
   static int R_MAT_CODE_UPPER_LIMIT = -1;
   java.lang.String rMatCode;
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public java.lang.String getRMatCode() {
      return rMatCode;
   }
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public void setRMatCode(java.lang.String rMatCode) throws wt.util.WTPropertyVetoException {
      rMatCodeValidate(rMatCode);
      this.rMatCode = rMatCode;
   }
   void rMatCodeValidate(java.lang.String rMatCode) throws wt.util.WTPropertyVetoException {
      if (R_MAT_CODE_UPPER_LIMIT < 1) {
         try { R_MAT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rMatCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_MAT_CODE_UPPER_LIMIT = 200; }
      }
      if (rMatCode != null && !wt.fc.PersistenceHelper.checkStoredLength(rMatCode.toString(), R_MAT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rMatCode"), java.lang.String.valueOf(java.lang.Math.min(R_MAT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rMatCode", this.rMatCode, rMatCode));
   }

   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public static final java.lang.String IMPORTANCE = "importance";
   static int IMPORTANCE_UPPER_LIMIT = -1;
   java.lang.String importance;
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public java.lang.String getImportance() {
      return importance;
   }
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public void setImportance(java.lang.String importance) throws wt.util.WTPropertyVetoException {
      importanceValidate(importance);
      this.importance = importance;
   }
   void importanceValidate(java.lang.String importance) throws wt.util.WTPropertyVetoException {
      if (IMPORTANCE_UPPER_LIMIT < 1) {
         try { IMPORTANCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("importance").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IMPORTANCE_UPPER_LIMIT = 200; }
      }
      if (importance != null && !wt.fc.PersistenceHelper.checkStoredLength(importance.toString(), IMPORTANCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "importance"), java.lang.String.valueOf(java.lang.Math.min(IMPORTANCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "importance", this.importance, importance));
   }

   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public static final java.lang.String SCRAP_INTERNAL = "scrapInternal";
   static int SCRAP_INTERNAL_UPPER_LIMIT = -1;
   java.lang.String scrapInternal;
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public java.lang.String getScrapInternal() {
      return scrapInternal;
   }
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public void setScrapInternal(java.lang.String scrapInternal) throws wt.util.WTPropertyVetoException {
      scrapInternalValidate(scrapInternal);
      this.scrapInternal = scrapInternal;
   }
   void scrapInternalValidate(java.lang.String scrapInternal) throws wt.util.WTPropertyVetoException {
      if (SCRAP_INTERNAL_UPPER_LIMIT < 1) {
         try { SCRAP_INTERNAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapInternal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_INTERNAL_UPPER_LIMIT = 200; }
      }
      if (scrapInternal != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapInternal.toString(), SCRAP_INTERNAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapInternal"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_INTERNAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapInternal", this.scrapInternal, scrapInternal));
   }

   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public static final java.lang.String SCRAP_CHINA = "scrapChina";
   static int SCRAP_CHINA_UPPER_LIMIT = -1;
   java.lang.String scrapChina;
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public java.lang.String getScrapChina() {
      return scrapChina;
   }
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public void setScrapChina(java.lang.String scrapChina) throws wt.util.WTPropertyVetoException {
      scrapChinaValidate(scrapChina);
      this.scrapChina = scrapChina;
   }
   void scrapChinaValidate(java.lang.String scrapChina) throws wt.util.WTPropertyVetoException {
      if (SCRAP_CHINA_UPPER_LIMIT < 1) {
         try { SCRAP_CHINA_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapChina").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_CHINA_UPPER_LIMIT = 200; }
      }
      if (scrapChina != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapChina.toString(), SCRAP_CHINA_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapChina"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_CHINA_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapChina", this.scrapChina, scrapChina));
   }

   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public static final java.lang.String VERSION = "version";
   java.lang.Integer version = 0;
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public java.lang.Integer getVersion() {
      return version;
   }
   /**
    * @see ext.ket.cost.entity.CSInfo
    */
   public void setVersion(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 8576788789884868833L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( importance );
      output.writeObject( infoType );
      output.writeObject( owner );
      output.writeObject( rMatCode );
      output.writeObject( scrapChina );
      output.writeObject( scrapInternal );
      output.writeObject( thePersistInfo );
      output.writeObject( version );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CSInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "importance", importance );
      output.setString( "infoType", infoType );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "rMatCode", rMatCode );
      output.setString( "scrapChina", scrapChina );
      output.setString( "scrapInternal", scrapInternal );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setIntObject( "version", version );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      importance = input.getString( "importance" );
      infoType = input.getString( "infoType" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      rMatCode = input.getString( "rMatCode" );
      scrapChina = input.getString( "scrapChina" );
      scrapInternal = input.getString( "scrapInternal" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      version = input.getIntObject( "version" );
   }

   boolean readVersion8576788789884868833L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      importance = (java.lang.String) input.readObject();
      infoType = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      rMatCode = (java.lang.String) input.readObject();
      scrapChina = (java.lang.String) input.readObject();
      scrapInternal = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      version = (java.lang.Integer) input.readObject();
      return true;
   }

   protected boolean readVersion( CSInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8576788789884868833L( input, readSerialVersionUID, superDone );
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
