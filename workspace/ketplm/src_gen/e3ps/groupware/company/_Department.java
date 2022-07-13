package e3ps.groupware.company;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _Department implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.company.companyResource";
   static final java.lang.String CLASSNAME = Department.class.getName();

   /**
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see e3ps.groupware.company.Department
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see e3ps.groupware.company.Department
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
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String EN_NAME = "enName";
   static int EN_NAME_UPPER_LIMIT = -1;
   java.lang.String enName;
   /**
    * @see e3ps.groupware.company.Department
    */
   public java.lang.String getEnName() {
      return enName;
   }
   /**
    * @see e3ps.groupware.company.Department
    */
   public void setEnName(java.lang.String enName) throws wt.util.WTPropertyVetoException {
      enNameValidate(enName);
      this.enName = enName;
   }
   void enNameValidate(java.lang.String enName) throws wt.util.WTPropertyVetoException {
      if (EN_NAME_UPPER_LIMIT < 1) {
         try { EN_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("enName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EN_NAME_UPPER_LIMIT = 200; }
      }
      if (enName != null && !wt.fc.PersistenceHelper.checkStoredLength(enName.toString(), EN_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "enName"), java.lang.String.valueOf(java.lang.Math.min(EN_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "enName", this.enName, enName));
   }

   /**
    * 부서 코드
    *
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String CODE = "code";
   static int CODE_UPPER_LIMIT = -1;
   java.lang.String code;
   /**
    * 부서 코드
    *
    * @see e3ps.groupware.company.Department
    */
   public java.lang.String getCode() {
      return code;
   }
   /**
    * 부서 코드
    *
    * @see e3ps.groupware.company.Department
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
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String CENTER_CODE = "centerCode";
   static int CENTER_CODE_UPPER_LIMIT = -1;
   java.lang.String centerCode;
   /**
    * @see e3ps.groupware.company.Department
    */
   public java.lang.String getCenterCode() {
      return centerCode;
   }
   /**
    * @see e3ps.groupware.company.Department
    */
   public void setCenterCode(java.lang.String centerCode) throws wt.util.WTPropertyVetoException {
      centerCodeValidate(centerCode);
      this.centerCode = centerCode;
   }
   void centerCodeValidate(java.lang.String centerCode) throws wt.util.WTPropertyVetoException {
      if (CENTER_CODE_UPPER_LIMIT < 1) {
         try { CENTER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("centerCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CENTER_CODE_UPPER_LIMIT = 200; }
      }
      if (centerCode != null && !wt.fc.PersistenceHelper.checkStoredLength(centerCode.toString(), CENTER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "centerCode"), java.lang.String.valueOf(java.lang.Math.min(CENTER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "centerCode", this.centerCode, centerCode));
   }

   /**
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String SORT = "sort";
   int sort = 0;
   /**
    * @see e3ps.groupware.company.Department
    */
   public int getSort() {
      return sort;
   }
   /**
    * @see e3ps.groupware.company.Department
    */
   public void setSort(int sort) throws wt.util.WTPropertyVetoException {
      sortValidate(sort);
      this.sort = sort;
   }
   void sortValidate(int sort) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 유효시작일≪≫
    *
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String ABAILABLE_START = "abailableStart";
   java.sql.Timestamp abailableStart;
   /**
    * 유효시작일≪≫
    *
    * @see e3ps.groupware.company.Department
    */
   public java.sql.Timestamp getAbailableStart() {
      return abailableStart;
   }
   /**
    * 유효시작일≪≫
    *
    * @see e3ps.groupware.company.Department
    */
   public void setAbailableStart(java.sql.Timestamp abailableStart) throws wt.util.WTPropertyVetoException {
      abailableStartValidate(abailableStart);
      this.abailableStart = abailableStart;
   }
   void abailableStartValidate(java.sql.Timestamp abailableStart) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 유효종료일
    *
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String ABAILABLE_END = "abailableEnd";
   java.sql.Timestamp abailableEnd;
   /**
    * 유효종료일
    *
    * @see e3ps.groupware.company.Department
    */
   public java.sql.Timestamp getAbailableEnd() {
      return abailableEnd;
   }
   /**
    * 유효종료일
    *
    * @see e3ps.groupware.company.Department
    */
   public void setAbailableEnd(java.sql.Timestamp abailableEnd) throws wt.util.WTPropertyVetoException {
      abailableEndValidate(abailableEnd);
      this.abailableEnd = abailableEnd;
   }
   void abailableEndValidate(java.sql.Timestamp abailableEnd) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String CC_CODE = "ccCode";
   static int CC_CODE_UPPER_LIMIT = -1;
   java.lang.String ccCode;
   /**
    * @see e3ps.groupware.company.Department
    */
   public java.lang.String getCcCode() {
      return ccCode;
   }
   /**
    * @see e3ps.groupware.company.Department
    */
   public void setCcCode(java.lang.String ccCode) throws wt.util.WTPropertyVetoException {
      ccCodeValidate(ccCode);
      this.ccCode = ccCode;
   }
   void ccCodeValidate(java.lang.String ccCode) throws wt.util.WTPropertyVetoException {
      if (CC_CODE_UPPER_LIMIT < 1) {
         try { CC_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ccCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CC_CODE_UPPER_LIMIT = 200; }
      }
      if (ccCode != null && !wt.fc.PersistenceHelper.checkStoredLength(ccCode.toString(), CC_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ccCode"), java.lang.String.valueOf(java.lang.Math.min(CC_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ccCode", this.ccCode, ccCode));
   }

   /**
    * @see e3ps.groupware.company.Department
    */
   public static final java.lang.String ENABLED = "enabled";
   boolean enabled;
   /**
    * @see e3ps.groupware.company.Department
    */
   public boolean isEnabled() {
      return enabled;
   }
   /**
    * @see e3ps.groupware.company.Department
    */
   public void setEnabled(boolean enabled) throws wt.util.WTPropertyVetoException {
      enabledValidate(enabled);
      this.enabled = enabled;
   }
   void enabledValidate(boolean enabled) throws wt.util.WTPropertyVetoException {
   }

   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent() {
      return (parentReference != null) ? (e3ps.common.impl.Tree) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.impl.Tree) the_parent));
   }
   /**
    * @see e3ps.common.impl.Tree
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
            !e3ps.common.impl.Tree.class.isAssignableFrom(the_parentReference.getReferencedClass()))
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

   public static final long EXTERNALIZATION_VERSION_UID = 6155688806609203193L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( abailableEnd );
      output.writeObject( abailableStart );
      output.writeObject( ccCode );
      output.writeObject( centerCode );
      output.writeObject( code );
      output.writeObject( enName );
      output.writeBoolean( enabled );
      output.writeObject( name );
      output.writeObject( parentReference );
      output.writeInt( sort );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.company.Department) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "abailableEnd", abailableEnd );
      output.setTimestamp( "abailableStart", abailableStart );
      output.setString( "ccCode", ccCode );
      output.setString( "centerCode", centerCode );
      output.setString( "code", code );
      output.setString( "enName", enName );
      output.setBoolean( "enabled", enabled );
      output.setString( "name", name );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setInt( "sort", sort );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      abailableEnd = input.getTimestamp( "abailableEnd" );
      abailableStart = input.getTimestamp( "abailableStart" );
      ccCode = input.getString( "ccCode" );
      centerCode = input.getString( "centerCode" );
      code = input.getString( "code" );
      enName = input.getString( "enName" );
      enabled = input.getBoolean( "enabled" );
      name = input.getString( "name" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      sort = input.getInt( "sort" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion6155688806609203193L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      abailableEnd = (java.sql.Timestamp) input.readObject();
      abailableStart = (java.sql.Timestamp) input.readObject();
      ccCode = (java.lang.String) input.readObject();
      centerCode = (java.lang.String) input.readObject();
      code = (java.lang.String) input.readObject();
      enName = (java.lang.String) input.readObject();
      enabled = input.readBoolean();
      name = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      sort = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( Department thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion6155688806609203193L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == -6953212342971754756L )
         return ((Department) this).readVersion_6953212342971754756L( input, readSerialVersionUID, superDone );
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
