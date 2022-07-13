package ext.ket.cost.entity.manage;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _BomEditorACLFromType implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.manage.manageResource";
   static final java.lang.String CLASSNAME = BomEditorACLFromType.class.getName();

   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public static final java.lang.String COLUMN_KEY = "columnKey";
   static int COLUMN_KEY_UPPER_LIMIT = -1;
   java.lang.String columnKey;
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public java.lang.String getColumnKey() {
      return columnKey;
   }
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public void setColumnKey(java.lang.String columnKey) throws wt.util.WTPropertyVetoException {
      columnKeyValidate(columnKey);
      this.columnKey = columnKey;
   }
   void columnKeyValidate(java.lang.String columnKey) throws wt.util.WTPropertyVetoException {
      if (COLUMN_KEY_UPPER_LIMIT < 1) {
         try { COLUMN_KEY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("columnKey").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLUMN_KEY_UPPER_LIMIT = 200; }
      }
      if (columnKey != null && !wt.fc.PersistenceHelper.checkStoredLength(columnKey.toString(), COLUMN_KEY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "columnKey"), java.lang.String.valueOf(java.lang.Math.min(COLUMN_KEY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "columnKey", this.columnKey, columnKey));
   }

   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public static final java.lang.String DISPLAY_NAME = "displayName";
   static int DISPLAY_NAME_UPPER_LIMIT = -1;
   java.lang.String displayName;
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public java.lang.String getDisplayName() {
      return displayName;
   }
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public void setDisplayName(java.lang.String displayName) throws wt.util.WTPropertyVetoException {
      displayNameValidate(displayName);
      this.displayName = displayName;
   }
   void displayNameValidate(java.lang.String displayName) throws wt.util.WTPropertyVetoException {
      if (DISPLAY_NAME_UPPER_LIMIT < 1) {
         try { DISPLAY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("displayName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DISPLAY_NAME_UPPER_LIMIT = 200; }
      }
      if (displayName != null && !wt.fc.PersistenceHelper.checkStoredLength(displayName.toString(), DISPLAY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "displayName"), java.lang.String.valueOf(java.lang.Math.min(DISPLAY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "displayName", this.displayName, displayName));
   }

   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public void setDescription(java.lang.String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(java.lang.String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 200; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), java.lang.String.valueOf(java.lang.Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
   }

   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public static final java.lang.String TYPE_ID = "typeId";
   static int TYPE_ID_UPPER_LIMIT = -1;
   java.lang.String typeId;
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public java.lang.String getTypeId() {
      return typeId;
   }
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public void setTypeId(java.lang.String typeId) throws wt.util.WTPropertyVetoException {
      typeIdValidate(typeId);
      this.typeId = typeId;
   }
   void typeIdValidate(java.lang.String typeId) throws wt.util.WTPropertyVetoException {
      if (TYPE_ID_UPPER_LIMIT < 1) {
         try { TYPE_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("typeId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TYPE_ID_UPPER_LIMIT = 200; }
      }
      if (typeId != null && !wt.fc.PersistenceHelper.checkStoredLength(typeId.toString(), TYPE_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeId"), java.lang.String.valueOf(java.lang.Math.min(TYPE_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "typeId", this.typeId, typeId));
   }

   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public static final java.lang.String NECESSARY = "necessary";
   boolean necessary;
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public boolean isNecessary() {
      return necessary;
   }
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public void setNecessary(boolean necessary) throws wt.util.WTPropertyVetoException {
      necessaryValidate(necessary);
      this.necessary = necessary;
   }
   void necessaryValidate(boolean necessary) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public static final java.lang.String DISABLED = "disabled";
   boolean disabled = false;
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public boolean isDisabled() {
      return disabled;
   }
   /**
    * @see ext.ket.cost.entity.manage.BomEditorACLFromType
    */
   public void setDisabled(boolean disabled) throws wt.util.WTPropertyVetoException {
      disabledValidate(disabled);
      this.disabled = disabled;
   }
   void disabledValidate(boolean disabled) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -5267888448682560236L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( columnKey );
      output.writeObject( description );
      output.writeBoolean( disabled );
      output.writeObject( displayName );
      output.writeBoolean( necessary );
      output.writeObject( thePersistInfo );
      output.writeObject( typeId );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.manage.BomEditorACLFromType) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "columnKey", columnKey );
      output.setString( "description", description );
      output.setBoolean( "disabled", disabled );
      output.setString( "displayName", displayName );
      output.setBoolean( "necessary", necessary );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "typeId", typeId );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      columnKey = input.getString( "columnKey" );
      description = input.getString( "description" );
      disabled = input.getBoolean( "disabled" );
      displayName = input.getString( "displayName" );
      necessary = input.getBoolean( "necessary" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      typeId = input.getString( "typeId" );
   }

   boolean readVersion_5267888448682560236L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      columnKey = (java.lang.String) input.readObject();
      description = (java.lang.String) input.readObject();
      disabled = input.readBoolean();
      displayName = (java.lang.String) input.readObject();
      necessary = input.readBoolean();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      typeId = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( BomEditorACLFromType thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_5267888448682560236L( input, readSerialVersionUID, superDone );
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
