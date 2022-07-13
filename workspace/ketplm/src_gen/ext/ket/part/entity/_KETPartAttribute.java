package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartAttribute implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartAttribute.class.getName();

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_CODE = "attrCode";
   static int ATTR_CODE_UPPER_LIMIT = -1;
   java.lang.String attrCode;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getAttrCode() {
      return attrCode;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrCode(java.lang.String attrCode) throws wt.util.WTPropertyVetoException {
      attrCodeValidate(attrCode);
      this.attrCode = attrCode;
   }
   void attrCodeValidate(java.lang.String attrCode) throws wt.util.WTPropertyVetoException {
      if (ATTR_CODE_UPPER_LIMIT < 1) {
         try { ATTR_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_CODE_UPPER_LIMIT = 200; }
      }
      if (attrCode != null && !wt.fc.PersistenceHelper.checkStoredLength(attrCode.toString(), ATTR_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrCode"), java.lang.String.valueOf(java.lang.Math.min(ATTR_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrCode", this.attrCode, attrCode));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_NAME = "attrName";
   static int ATTR_NAME_UPPER_LIMIT = -1;
   java.lang.String attrName;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getAttrName() {
      return attrName;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrName(java.lang.String attrName) throws wt.util.WTPropertyVetoException {
      attrNameValidate(attrName);
      this.attrName = attrName;
   }
   void attrNameValidate(java.lang.String attrName) throws wt.util.WTPropertyVetoException {
      if (ATTR_NAME_UPPER_LIMIT < 1) {
         try { ATTR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_NAME_UPPER_LIMIT = 200; }
      }
      if (attrName != null && !wt.fc.PersistenceHelper.checkStoredLength(attrName.toString(), ATTR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrName"), java.lang.String.valueOf(java.lang.Math.min(ATTR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrName", this.attrName, attrName));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_ENAME = "attrEName";
   static int ATTR_ENAME_UPPER_LIMIT = -1;
   java.lang.String attrEName;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getAttrEName() {
      return attrEName;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrEName(java.lang.String attrEName) throws wt.util.WTPropertyVetoException {
      attrENameValidate(attrEName);
      this.attrEName = attrEName;
   }
   void attrENameValidate(java.lang.String attrEName) throws wt.util.WTPropertyVetoException {
      if (ATTR_ENAME_UPPER_LIMIT < 1) {
         try { ATTR_ENAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrEName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_ENAME_UPPER_LIMIT = 200; }
      }
      if (attrEName != null && !wt.fc.PersistenceHelper.checkStoredLength(attrEName.toString(), ATTR_ENAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrEName"), java.lang.String.valueOf(java.lang.Math.min(ATTR_ENAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrEName", this.attrEName, attrEName));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_OOTB_NAME = "attrOotbName";
   static int ATTR_OOTB_NAME_UPPER_LIMIT = -1;
   java.lang.String attrOotbName;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getAttrOotbName() {
      return attrOotbName;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrOotbName(java.lang.String attrOotbName) throws wt.util.WTPropertyVetoException {
      attrOotbNameValidate(attrOotbName);
      this.attrOotbName = attrOotbName;
   }
   void attrOotbNameValidate(java.lang.String attrOotbName) throws wt.util.WTPropertyVetoException {
      if (ATTR_OOTB_NAME_UPPER_LIMIT < 1) {
         try { ATTR_OOTB_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrOotbName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_OOTB_NAME_UPPER_LIMIT = 200; }
      }
      if (attrOotbName != null && !wt.fc.PersistenceHelper.checkStoredLength(attrOotbName.toString(), ATTR_OOTB_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrOotbName"), java.lang.String.valueOf(java.lang.Math.min(ATTR_OOTB_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrOotbName", this.attrOotbName, attrOotbName));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String COLUMN_NAME = "columnName";
   static int COLUMN_NAME_UPPER_LIMIT = -1;
   java.lang.String columnName;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getColumnName() {
      return columnName;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setColumnName(java.lang.String columnName) throws wt.util.WTPropertyVetoException {
      columnNameValidate(columnName);
      this.columnName = columnName;
   }
   void columnNameValidate(java.lang.String columnName) throws wt.util.WTPropertyVetoException {
      if (COLUMN_NAME_UPPER_LIMIT < 1) {
         try { COLUMN_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("columnName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLUMN_NAME_UPPER_LIMIT = 200; }
      }
      if (columnName != null && !wt.fc.PersistenceHelper.checkStoredLength(columnName.toString(), COLUMN_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "columnName"), java.lang.String.valueOf(java.lang.Math.min(COLUMN_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "columnName", this.columnName, columnName));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_INPUT_TYPE = "attrInputType";
   static int ATTR_INPUT_TYPE_UPPER_LIMIT = -1;
   java.lang.String attrInputType;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getAttrInputType() {
      return attrInputType;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrInputType(java.lang.String attrInputType) throws wt.util.WTPropertyVetoException {
      attrInputTypeValidate(attrInputType);
      this.attrInputType = attrInputType;
   }
   void attrInputTypeValidate(java.lang.String attrInputType) throws wt.util.WTPropertyVetoException {
      if (ATTR_INPUT_TYPE_UPPER_LIMIT < 1) {
         try { ATTR_INPUT_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrInputType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_INPUT_TYPE_UPPER_LIMIT = 200; }
      }
      if (attrInputType != null && !wt.fc.PersistenceHelper.checkStoredLength(attrInputType.toString(), ATTR_INPUT_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrInputType"), java.lang.String.valueOf(java.lang.Math.min(ATTR_INPUT_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrInputType", this.attrInputType, attrInputType));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_CODE_TYPE = "attrCodeType";
   static int ATTR_CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String attrCodeType;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getAttrCodeType() {
      return attrCodeType;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrCodeType(java.lang.String attrCodeType) throws wt.util.WTPropertyVetoException {
      attrCodeTypeValidate(attrCodeType);
      this.attrCodeType = attrCodeType;
   }
   void attrCodeTypeValidate(java.lang.String attrCodeType) throws wt.util.WTPropertyVetoException {
      if (ATTR_CODE_TYPE_UPPER_LIMIT < 1) {
         try { ATTR_CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrCodeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (attrCodeType != null && !wt.fc.PersistenceHelper.checkStoredLength(attrCodeType.toString(), ATTR_CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrCodeType"), java.lang.String.valueOf(java.lang.Math.min(ATTR_CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrCodeType", this.attrCodeType, attrCodeType));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_MULTI_SELECT = "attrMultiSelect";
   boolean attrMultiSelect = false;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public boolean isAttrMultiSelect() {
      return attrMultiSelect;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrMultiSelect(boolean attrMultiSelect) throws wt.util.WTPropertyVetoException {
      attrMultiSelectValidate(attrMultiSelect);
      this.attrMultiSelect = attrMultiSelect;
   }
   void attrMultiSelectValidate(boolean attrMultiSelect) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ATTR_DESC = "attrDesc";
   static int ATTR_DESC_UPPER_LIMIT = -1;
   java.lang.String attrDesc;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getAttrDesc() {
      return attrDesc;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setAttrDesc(java.lang.String attrDesc) throws wt.util.WTPropertyVetoException {
      attrDescValidate(attrDesc);
      this.attrDesc = attrDesc;
   }
   void attrDescValidate(java.lang.String attrDesc) throws wt.util.WTPropertyVetoException {
      if (ATTR_DESC_UPPER_LIMIT < 1) {
         try { ATTR_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_DESC_UPPER_LIMIT = 2000; }
      }
      if (attrDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(attrDesc.toString(), ATTR_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrDesc"), java.lang.String.valueOf(java.lang.Math.min(ATTR_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrDesc", this.attrDesc, attrDesc));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String SEND_ERP = "sendErp";
   boolean sendErp = false;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public boolean isSendErp() {
      return sendErp;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setSendErp(boolean sendErp) throws wt.util.WTPropertyVetoException {
      sendErpValidate(sendErp);
      this.sendErp = sendErp;
   }
   void sendErpValidate(boolean sendErp) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String RECEIVE_ERP = "receiveErp";
   boolean receiveErp = false;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public boolean isReceiveErp() {
      return receiveErp;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setReceiveErp(boolean receiveErp) throws wt.util.WTPropertyVetoException {
      receiveErpValidate(receiveErp);
      this.receiveErp = receiveErp;
   }
   void receiveErpValidate(boolean receiveErp) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String ERP_DESC = "erpDesc";
   static int ERP_DESC_UPPER_LIMIT = -1;
   java.lang.String erpDesc;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public java.lang.String getErpDesc() {
      return erpDesc;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setErpDesc(java.lang.String erpDesc) throws wt.util.WTPropertyVetoException {
      erpDescValidate(erpDesc);
      this.erpDesc = erpDesc;
   }
   void erpDescValidate(java.lang.String erpDesc) throws wt.util.WTPropertyVetoException {
      if (ERP_DESC_UPPER_LIMIT < 1) {
         try { ERP_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("erpDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ERP_DESC_UPPER_LIMIT = 2000; }
      }
      if (erpDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(erpDesc.toString(), ERP_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "erpDesc"), java.lang.String.valueOf(java.lang.Math.min(ERP_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "erpDesc", this.erpDesc, erpDesc));
   }

   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public static final java.lang.String USE_NUMBERING = "useNumbering";
   boolean useNumbering = false;
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public boolean isUseNumbering() {
      return useNumbering;
   }
   /**
    * @see ext.ket.part.entity.KETPartAttribute
    */
   public void setUseNumbering(boolean useNumbering) throws wt.util.WTPropertyVetoException {
      useNumberingValidate(useNumbering);
      this.useNumbering = useNumbering;
   }
   void useNumberingValidate(boolean useNumbering) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -6042378152934812435L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( attrCode );
      output.writeObject( attrCodeType );
      output.writeObject( attrDesc );
      output.writeObject( attrEName );
      output.writeObject( attrInputType );
      output.writeBoolean( attrMultiSelect );
      output.writeObject( attrName );
      output.writeObject( attrOotbName );
      output.writeObject( columnName );
      output.writeObject( erpDesc );
      output.writeBoolean( receiveErp );
      output.writeBoolean( sendErp );
      output.writeObject( thePersistInfo );
      output.writeBoolean( useNumbering );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.KETPartAttribute) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "attrCode", attrCode );
      output.setString( "attrCodeType", attrCodeType );
      output.setString( "attrDesc", attrDesc );
      output.setString( "attrEName", attrEName );
      output.setString( "attrInputType", attrInputType );
      output.setBoolean( "attrMultiSelect", attrMultiSelect );
      output.setString( "attrName", attrName );
      output.setString( "attrOotbName", attrOotbName );
      output.setString( "columnName", columnName );
      output.setString( "erpDesc", erpDesc );
      output.setBoolean( "receiveErp", receiveErp );
      output.setBoolean( "sendErp", sendErp );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setBoolean( "useNumbering", useNumbering );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      attrCode = input.getString( "attrCode" );
      attrCodeType = input.getString( "attrCodeType" );
      attrDesc = input.getString( "attrDesc" );
      attrEName = input.getString( "attrEName" );
      attrInputType = input.getString( "attrInputType" );
      attrMultiSelect = input.getBoolean( "attrMultiSelect" );
      attrName = input.getString( "attrName" );
      attrOotbName = input.getString( "attrOotbName" );
      columnName = input.getString( "columnName" );
      erpDesc = input.getString( "erpDesc" );
      receiveErp = input.getBoolean( "receiveErp" );
      sendErp = input.getBoolean( "sendErp" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      useNumbering = input.getBoolean( "useNumbering" );
   }

   boolean readVersion_6042378152934812435L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      attrCode = (java.lang.String) input.readObject();
      attrCodeType = (java.lang.String) input.readObject();
      attrDesc = (java.lang.String) input.readObject();
      attrEName = (java.lang.String) input.readObject();
      attrInputType = (java.lang.String) input.readObject();
      attrMultiSelect = input.readBoolean();
      attrName = (java.lang.String) input.readObject();
      attrOotbName = (java.lang.String) input.readObject();
      columnName = (java.lang.String) input.readObject();
      erpDesc = (java.lang.String) input.readObject();
      receiveErp = input.readBoolean();
      sendErp = input.readBoolean();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      useNumbering = input.readBoolean();
      return true;
   }

   protected boolean readVersion( KETPartAttribute thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6042378152934812435L( input, readSerialVersionUID, superDone );
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
