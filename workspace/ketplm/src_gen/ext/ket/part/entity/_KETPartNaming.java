package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartNaming implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartNaming.class.getName();

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String NAMING_CODE = "namingCode";
   static int NAMING_CODE_UPPER_LIMIT = -1;
   java.lang.String namingCode;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public java.lang.String getNamingCode() {
      return namingCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setNamingCode(java.lang.String namingCode) throws wt.util.WTPropertyVetoException {
      namingCodeValidate(namingCode);
      this.namingCode = namingCode;
   }
   void namingCodeValidate(java.lang.String namingCode) throws wt.util.WTPropertyVetoException {
      if (NAMING_CODE_UPPER_LIMIT < 1) {
         try { NAMING_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("namingCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAMING_CODE_UPPER_LIMIT = 200; }
      }
      if (namingCode != null && !wt.fc.PersistenceHelper.checkStoredLength(namingCode.toString(), NAMING_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "namingCode"), java.lang.String.valueOf(java.lang.Math.min(NAMING_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "namingCode", this.namingCode, namingCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String NAMING_NAME = "namingName";
   static int NAMING_NAME_UPPER_LIMIT = -1;
   java.lang.String namingName;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public java.lang.String getNamingName() {
      return namingName;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setNamingName(java.lang.String namingName) throws wt.util.WTPropertyVetoException {
      namingNameValidate(namingName);
      this.namingName = namingName;
   }
   void namingNameValidate(java.lang.String namingName) throws wt.util.WTPropertyVetoException {
      if (NAMING_NAME_UPPER_LIMIT < 1) {
         try { NAMING_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("namingName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAMING_NAME_UPPER_LIMIT = 200; }
      }
      if (namingName != null && !wt.fc.PersistenceHelper.checkStoredLength(namingName.toString(), NAMING_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "namingName"), java.lang.String.valueOf(java.lang.Math.min(NAMING_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "namingName", this.namingName, namingName));
   }

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String LEV1_NUMBER_CODE_TYPE = "lev1NumberCodeType";
   static int LEV1_NUMBER_CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String lev1NumberCodeType;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public java.lang.String getLev1NumberCodeType() {
      return lev1NumberCodeType;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setLev1NumberCodeType(java.lang.String lev1NumberCodeType) throws wt.util.WTPropertyVetoException {
      lev1NumberCodeTypeValidate(lev1NumberCodeType);
      this.lev1NumberCodeType = lev1NumberCodeType;
   }
   void lev1NumberCodeTypeValidate(java.lang.String lev1NumberCodeType) throws wt.util.WTPropertyVetoException {
      if (LEV1_NUMBER_CODE_TYPE_UPPER_LIMIT < 1) {
         try { LEV1_NUMBER_CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lev1NumberCodeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LEV1_NUMBER_CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (lev1NumberCodeType != null && !wt.fc.PersistenceHelper.checkStoredLength(lev1NumberCodeType.toString(), LEV1_NUMBER_CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lev1NumberCodeType"), java.lang.String.valueOf(java.lang.Math.min(LEV1_NUMBER_CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lev1NumberCodeType", this.lev1NumberCodeType, lev1NumberCodeType));
   }

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String LEV2_NUMBER_CODE_TYPE = "lev2NumberCodeType";
   static int LEV2_NUMBER_CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String lev2NumberCodeType;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public java.lang.String getLev2NumberCodeType() {
      return lev2NumberCodeType;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setLev2NumberCodeType(java.lang.String lev2NumberCodeType) throws wt.util.WTPropertyVetoException {
      lev2NumberCodeTypeValidate(lev2NumberCodeType);
      this.lev2NumberCodeType = lev2NumberCodeType;
   }
   void lev2NumberCodeTypeValidate(java.lang.String lev2NumberCodeType) throws wt.util.WTPropertyVetoException {
      if (LEV2_NUMBER_CODE_TYPE_UPPER_LIMIT < 1) {
         try { LEV2_NUMBER_CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lev2NumberCodeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LEV2_NUMBER_CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (lev2NumberCodeType != null && !wt.fc.PersistenceHelper.checkStoredLength(lev2NumberCodeType.toString(), LEV2_NUMBER_CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lev2NumberCodeType"), java.lang.String.valueOf(java.lang.Math.min(LEV2_NUMBER_CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lev2NumberCodeType", this.lev2NumberCodeType, lev2NumberCodeType));
   }

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String LEV3_NUMBER_CODE_TYPE = "lev3NumberCodeType";
   static int LEV3_NUMBER_CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String lev3NumberCodeType;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public java.lang.String getLev3NumberCodeType() {
      return lev3NumberCodeType;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setLev3NumberCodeType(java.lang.String lev3NumberCodeType) throws wt.util.WTPropertyVetoException {
      lev3NumberCodeTypeValidate(lev3NumberCodeType);
      this.lev3NumberCodeType = lev3NumberCodeType;
   }
   void lev3NumberCodeTypeValidate(java.lang.String lev3NumberCodeType) throws wt.util.WTPropertyVetoException {
      if (LEV3_NUMBER_CODE_TYPE_UPPER_LIMIT < 1) {
         try { LEV3_NUMBER_CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lev3NumberCodeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LEV3_NUMBER_CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (lev3NumberCodeType != null && !wt.fc.PersistenceHelper.checkStoredLength(lev3NumberCodeType.toString(), LEV3_NUMBER_CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lev3NumberCodeType"), java.lang.String.valueOf(java.lang.Math.min(LEV3_NUMBER_CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lev3NumberCodeType", this.lev3NumberCodeType, lev3NumberCodeType));
   }

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String LEV4_NUMBER_CODE_TYPE = "lev4NumberCodeType";
   static int LEV4_NUMBER_CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String lev4NumberCodeType;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public java.lang.String getLev4NumberCodeType() {
      return lev4NumberCodeType;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setLev4NumberCodeType(java.lang.String lev4NumberCodeType) throws wt.util.WTPropertyVetoException {
      lev4NumberCodeTypeValidate(lev4NumberCodeType);
      this.lev4NumberCodeType = lev4NumberCodeType;
   }
   void lev4NumberCodeTypeValidate(java.lang.String lev4NumberCodeType) throws wt.util.WTPropertyVetoException {
      if (LEV4_NUMBER_CODE_TYPE_UPPER_LIMIT < 1) {
         try { LEV4_NUMBER_CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lev4NumberCodeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LEV4_NUMBER_CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (lev4NumberCodeType != null && !wt.fc.PersistenceHelper.checkStoredLength(lev4NumberCodeType.toString(), LEV4_NUMBER_CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lev4NumberCodeType"), java.lang.String.valueOf(java.lang.Math.min(LEV4_NUMBER_CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lev4NumberCodeType", this.lev4NumberCodeType, lev4NumberCodeType));
   }

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String LEV5_NUMBER_CODE_TYPE = "lev5NumberCodeType";
   static int LEV5_NUMBER_CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String lev5NumberCodeType;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public java.lang.String getLev5NumberCodeType() {
      return lev5NumberCodeType;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setLev5NumberCodeType(java.lang.String lev5NumberCodeType) throws wt.util.WTPropertyVetoException {
      lev5NumberCodeTypeValidate(lev5NumberCodeType);
      this.lev5NumberCodeType = lev5NumberCodeType;
   }
   void lev5NumberCodeTypeValidate(java.lang.String lev5NumberCodeType) throws wt.util.WTPropertyVetoException {
      if (LEV5_NUMBER_CODE_TYPE_UPPER_LIMIT < 1) {
         try { LEV5_NUMBER_CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lev5NumberCodeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LEV5_NUMBER_CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (lev5NumberCodeType != null && !wt.fc.PersistenceHelper.checkStoredLength(lev5NumberCodeType.toString(), LEV5_NUMBER_CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lev5NumberCodeType"), java.lang.String.valueOf(java.lang.Math.min(LEV5_NUMBER_CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lev5NumberCodeType", this.lev5NumberCodeType, lev5NumberCodeType));
   }

   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public static final java.lang.String USE_NAMING = "useNaming";
   boolean useNaming;
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public boolean isUseNaming() {
      return useNaming;
   }
   /**
    * @see ext.ket.part.entity.KETPartNaming
    */
   public void setUseNaming(boolean useNaming) throws wt.util.WTPropertyVetoException {
      useNamingValidate(useNaming);
      this.useNaming = useNaming;
   }
   void useNamingValidate(boolean useNaming) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -377492914447511792L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( lev1NumberCodeType );
      output.writeObject( lev2NumberCodeType );
      output.writeObject( lev3NumberCodeType );
      output.writeObject( lev4NumberCodeType );
      output.writeObject( lev5NumberCodeType );
      output.writeObject( namingCode );
      output.writeObject( namingName );
      output.writeObject( thePersistInfo );
      output.writeBoolean( useNaming );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.KETPartNaming) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "lev1NumberCodeType", lev1NumberCodeType );
      output.setString( "lev2NumberCodeType", lev2NumberCodeType );
      output.setString( "lev3NumberCodeType", lev3NumberCodeType );
      output.setString( "lev4NumberCodeType", lev4NumberCodeType );
      output.setString( "lev5NumberCodeType", lev5NumberCodeType );
      output.setString( "namingCode", namingCode );
      output.setString( "namingName", namingName );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setBoolean( "useNaming", useNaming );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      lev1NumberCodeType = input.getString( "lev1NumberCodeType" );
      lev2NumberCodeType = input.getString( "lev2NumberCodeType" );
      lev3NumberCodeType = input.getString( "lev3NumberCodeType" );
      lev4NumberCodeType = input.getString( "lev4NumberCodeType" );
      lev5NumberCodeType = input.getString( "lev5NumberCodeType" );
      namingCode = input.getString( "namingCode" );
      namingName = input.getString( "namingName" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      useNaming = input.getBoolean( "useNaming" );
   }

   boolean readVersion_377492914447511792L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      lev1NumberCodeType = (java.lang.String) input.readObject();
      lev2NumberCodeType = (java.lang.String) input.readObject();
      lev3NumberCodeType = (java.lang.String) input.readObject();
      lev4NumberCodeType = (java.lang.String) input.readObject();
      lev5NumberCodeType = (java.lang.String) input.readObject();
      namingCode = (java.lang.String) input.readObject();
      namingName = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      useNaming = input.readBoolean();
      return true;
   }

   protected boolean readVersion( KETPartNaming thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_377492914447511792L( input, readSerialVersionUID, superDone );
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
