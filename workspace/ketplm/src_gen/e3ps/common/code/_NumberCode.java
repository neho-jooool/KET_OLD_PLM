package e3ps.common.code;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _NumberCode implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.common.code.codeResource";
   static final java.lang.String CLASSNAME = NumberCode.class.getName();

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setName(java.lang.String name) throws wt.util.WTPropertyVetoException {
      nameValidate(name);
      this.name = name;
   }
   void nameValidate(java.lang.String name) throws wt.util.WTPropertyVetoException {
      if (NAME_UPPER_LIMIT < 1) {
         try { NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("name").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAME_UPPER_LIMIT = 200; }
      }
      if (name != null && !wt.fc.PersistenceHelper.checkStoredLength(name.toString(), NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "name"), java.lang.String.valueOf(java.lang.Math.min(NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "name", this.name, name));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String CODE = "code";
   static int CODE_UPPER_LIMIT = -1;
   java.lang.String code;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getCode() {
      return code;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setCode(java.lang.String code) throws wt.util.WTPropertyVetoException {
      codeValidate(code);
      this.code = code;
   }
   void codeValidate(java.lang.String code) throws wt.util.WTPropertyVetoException {
      if (CODE_UPPER_LIMIT < 1) {
         try { CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("code").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CODE_UPPER_LIMIT = 200; }
      }
      if (code != null && !wt.fc.PersistenceHelper.checkStoredLength(code.toString(), CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "code"), java.lang.String.valueOf(java.lang.Math.min(CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "code", this.code, code));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * @see e3ps.common.code.NumberCode
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
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String DISABLED = "disabled";
   boolean disabled;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public boolean isDisabled() {
      return disabled;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setDisabled(boolean disabled) throws wt.util.WTPropertyVetoException {
      disabledValidate(disabled);
      this.disabled = disabled;
   }
   void disabledValidate(boolean disabled) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String WC_TYPE = "wcType";
   static int WC_TYPE_UPPER_LIMIT = -1;
   java.lang.String wcType;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getWcType() {
      return wcType;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setWcType(java.lang.String wcType) throws wt.util.WTPropertyVetoException {
      wcTypeValidate(wcType);
      this.wcType = wcType;
   }
   void wcTypeValidate(java.lang.String wcType) throws wt.util.WTPropertyVetoException {
      if (WC_TYPE_UPPER_LIMIT < 1) {
         try { WC_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("wcType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WC_TYPE_UPPER_LIMIT = 200; }
      }
      if (wcType != null && !wt.fc.PersistenceHelper.checkStoredLength(wcType.toString(), WC_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "wcType"), java.lang.String.valueOf(java.lang.Math.min(WC_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "wcType", this.wcType, wcType));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String DS_CODE = "dsCode";
   static int DS_CODE_UPPER_LIMIT = -1;
   java.lang.String dsCode;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getDsCode() {
      return dsCode;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setDsCode(java.lang.String dsCode) throws wt.util.WTPropertyVetoException {
      dsCodeValidate(dsCode);
      this.dsCode = dsCode;
   }
   void dsCodeValidate(java.lang.String dsCode) throws wt.util.WTPropertyVetoException {
      if (DS_CODE_UPPER_LIMIT < 1) {
         try { DS_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dsCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DS_CODE_UPPER_LIMIT = 200; }
      }
      if (dsCode != null && !wt.fc.PersistenceHelper.checkStoredLength(dsCode.toString(), DS_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dsCode"), java.lang.String.valueOf(java.lang.Math.min(DS_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dsCode", this.dsCode, dsCode));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String COST_CODE = "costCode";
   static int COST_CODE_UPPER_LIMIT = -1;
   java.lang.String costCode;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getCostCode() {
      return costCode;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setCostCode(java.lang.String costCode) throws wt.util.WTPropertyVetoException {
      costCodeValidate(costCode);
      this.costCode = costCode;
   }
   void costCodeValidate(java.lang.String costCode) throws wt.util.WTPropertyVetoException {
      if (COST_CODE_UPPER_LIMIT < 1) {
         try { COST_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_CODE_UPPER_LIMIT = 200; }
      }
      if (costCode != null && !wt.fc.PersistenceHelper.checkStoredLength(costCode.toString(), COST_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costCode"), java.lang.String.valueOf(java.lang.Math.min(COST_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costCode", this.costCode, costCode));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String VENDER_CODE = "venderCode";
   static int VENDER_CODE_UPPER_LIMIT = -1;
   java.lang.String venderCode;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getVenderCode() {
      return venderCode;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setVenderCode(java.lang.String venderCode) throws wt.util.WTPropertyVetoException {
      venderCodeValidate(venderCode);
      this.venderCode = venderCode;
   }
   void venderCodeValidate(java.lang.String venderCode) throws wt.util.WTPropertyVetoException {
      if (VENDER_CODE_UPPER_LIMIT < 1) {
         try { VENDER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("venderCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VENDER_CODE_UPPER_LIMIT = 200; }
      }
      if (venderCode != null && !wt.fc.PersistenceHelper.checkStoredLength(venderCode.toString(), VENDER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "venderCode"), java.lang.String.valueOf(java.lang.Math.min(VENDER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "venderCode", this.venderCode, venderCode));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String SORTING = "sorting";
   static int SORTING_UPPER_LIMIT = -1;
   java.lang.String sorting;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public java.lang.String getSorting() {
      return sorting;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setSorting(java.lang.String sorting) throws wt.util.WTPropertyVetoException {
      sortingValidate(sorting);
      this.sorting = sorting;
   }
   void sortingValidate(java.lang.String sorting) throws wt.util.WTPropertyVetoException {
      if (SORTING_UPPER_LIMIT < 1) {
         try { SORTING_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sorting").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SORTING_UPPER_LIMIT = 200; }
      }
      if (sorting != null && !wt.fc.PersistenceHelper.checkStoredLength(sorting.toString(), SORTING_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sorting"), java.lang.String.valueOf(java.lang.Math.min(SORTING_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sorting", this.sorting, sorting));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String CODE_TYPE = "codeType";
   e3ps.common.code.NumberCodeType codeType;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public e3ps.common.code.NumberCodeType getCodeType() {
      return codeType;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setCodeType(e3ps.common.code.NumberCodeType codeType) throws wt.util.WTPropertyVetoException {
      codeTypeValidate(codeType);
      this.codeType = codeType;
   }
   void codeTypeValidate(e3ps.common.code.NumberCodeType codeType) throws wt.util.WTPropertyVetoException {
      if (!wt.fc.IdentityHelper.isChangeable(this))
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.CHANGE_RESTRICTION,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "codeType") },
               new java.beans.PropertyChangeEvent(this, "codeType", this.codeType, codeType));
      if (codeType == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "codeType") },
               new java.beans.PropertyChangeEvent(this, "codeType", this.codeType, codeType));
   }

   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String PARENT = "parent";
   /**
    * @see e3ps.common.code.NumberCode
    */
   public static final java.lang.String PARENT_REFERENCE = "parentReference";
   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.code.NumberCode
    */
   public e3ps.common.code.NumberCode getParent() {
      return (parentReference != null) ? (e3ps.common.code.NumberCode) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setParent(e3ps.common.code.NumberCode the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_parent));
   }
   /**
    * @see e3ps.common.code.NumberCode
    */
   public void setParentReference(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      parentReferenceValidate(the_parentReference);
      parentReference = (wt.fc.ObjectReference) the_parentReference;
   }
   void parentReferenceValidate(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      if (the_parentReference == null || the_parentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference") },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
      if (the_parentReference != null && the_parentReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_parentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7881427213251509781L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( code );
      output.writeObject( (codeType == null ? null : codeType.getStringValue()) );
      output.writeObject( costCode );
      output.writeObject( description );
      output.writeBoolean( disabled );
      output.writeObject( dsCode );
      output.writeObject( name );
      output.writeObject( parentReference );
      output.writeObject( sorting );
      output.writeObject( thePersistInfo );
      output.writeObject( venderCode );
      output.writeObject( wcType );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.common.code.NumberCode) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "code", code );
      output.setString( "codeType", (codeType == null ? null : codeType.toString()) );
      output.setString( "costCode", costCode );
      output.setString( "description", description );
      output.setBoolean( "disabled", disabled );
      output.setString( "dsCode", dsCode );
      output.setString( "name", name );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setString( "sorting", sorting );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "venderCode", venderCode );
      output.setString( "wcType", wcType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      code = input.getString( "code" );
      java.lang.String codeType_string_value = (java.lang.String) input.getString("codeType");
      if ( codeType_string_value != null ) { 
         codeType = (e3ps.common.code.NumberCodeType) wt.introspection.ClassInfo.getConstrainedEnum( getClass(), "codeType", codeType_string_value );
         if ( codeType == null )  // hard-coded type
            codeType = e3ps.common.code.NumberCodeType.toNumberCodeType( codeType_string_value );
      }
      costCode = input.getString( "costCode" );
      description = input.getString( "description" );
      disabled = input.getBoolean( "disabled" );
      dsCode = input.getString( "dsCode" );
      name = input.getString( "name" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      sorting = input.getString( "sorting" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      venderCode = input.getString( "venderCode" );
      wcType = input.getString( "wcType" );
   }

   boolean readVersion7881427213251509781L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      code = (java.lang.String) input.readObject();
      java.lang.String codeType_string_value = (java.lang.String) input.readObject();
      try { 
         codeType = (e3ps.common.code.NumberCodeType) wt.fc.EnumeratedTypeUtil.toEnumeratedType( codeType_string_value );
      } catch( wt.util.WTInvalidParameterException e ) {
         // Old Format
         codeType = e3ps.common.code.NumberCodeType.toNumberCodeType( codeType_string_value );
      }
      costCode = (java.lang.String) input.readObject();
      description = (java.lang.String) input.readObject();
      disabled = input.readBoolean();
      dsCode = (java.lang.String) input.readObject();
      name = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      sorting = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      venderCode = (java.lang.String) input.readObject();
      wcType = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( NumberCode thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7881427213251509781L( input, readSerialVersionUID, superDone );
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
