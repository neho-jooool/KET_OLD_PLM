package e3ps.project.outputtype;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _OEMProjectType implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.outputtype.outputtypeResource";
   static final java.lang.String CLASSNAME = OEMProjectType.class.getName();

   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
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
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String CODE = "code";
   static int CODE_UPPER_LIMIT = -1;
   java.lang.String code;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public java.lang.String getCode() {
      return code;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
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
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
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
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String IS_DISABLED = "isDisabled";
   boolean isDisabled = false;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public boolean isIsDisabled() {
      return isDisabled;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setIsDisabled(boolean isDisabled) throws wt.util.WTPropertyVetoException {
      isDisabledValidate(isDisabled);
      this.isDisabled = isDisabled;
   }
   void isDisabledValidate(boolean isDisabled) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String PATH = "path";
   static int PATH_UPPER_LIMIT = -1;
   java.lang.String path;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public java.lang.String getPath() {
      return path;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setPath(java.lang.String path) throws wt.util.WTPropertyVetoException {
      pathValidate(path);
      this.path = path;
   }
   void pathValidate(java.lang.String path) throws wt.util.WTPropertyVetoException {
      if (PATH_UPPER_LIMIT < 1) {
         try { PATH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("path").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PATH_UPPER_LIMIT = 200; }
      }
      if (path != null && !wt.fc.PersistenceHelper.checkStoredLength(path.toString(), PATH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "path"), java.lang.String.valueOf(java.lang.Math.min(PATH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "path", this.path, path));
   }

   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String O_LEVEL = "oLevel";
   int oLevel;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public int getOLevel() {
      return oLevel;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setOLevel(int oLevel) throws wt.util.WTPropertyVetoException {
      oLevelValidate(oLevel);
      this.oLevel = oLevel;
   }
   void oLevelValidate(int oLevel) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String IS_SAVE_PLAN = "isSavePlan";
   boolean isSavePlan;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public boolean isIsSavePlan() {
      return isSavePlan;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setIsSavePlan(boolean isSavePlan) throws wt.util.WTPropertyVetoException {
      isSavePlanValidate(isSavePlan);
      this.isSavePlan = isSavePlan;
   }
   void isSavePlanValidate(boolean isSavePlan) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 친환경여부
    *
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String IS_ECO_CAR = "isEcoCar";
   java.lang.Boolean isEcoCar = false;
   /**
    * 친환경여부
    *
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public java.lang.Boolean getIsEcoCar() {
      return isEcoCar;
   }
   /**
    * 친환경여부
    *
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setIsEcoCar(java.lang.Boolean isEcoCar) throws wt.util.WTPropertyVetoException {
      isEcoCarValidate(isEcoCar);
      this.isEcoCar = isEcoCar;
   }
   void isEcoCarValidate(java.lang.Boolean isEcoCar) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Running Change 여부
    *
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String IS_RUNNING_CHANGE = "isRunningChange";
   java.lang.Boolean isRunningChange = false;
   /**
    * Running Change 여부
    *
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public java.lang.Boolean getIsRunningChange() {
      return isRunningChange;
   }
   /**
    * Running Change 여부
    *
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setIsRunningChange(java.lang.Boolean isRunningChange) throws wt.util.WTPropertyVetoException {
      isRunningChangeValidate(isRunningChange);
      this.isRunningChange = isRunningChange;
   }
   void isRunningChangeValidate(java.lang.Boolean isRunningChange) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String OEM_TYPE = "oemType";
   e3ps.project.outputtype.OEMType oemType;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public e3ps.project.outputtype.OEMType getOemType() {
      return oemType;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setOemType(e3ps.project.outputtype.OEMType oemType) throws wt.util.WTPropertyVetoException {
      oemTypeValidate(oemType);
      this.oemType = oemType;
   }
   void oemTypeValidate(e3ps.project.outputtype.OEMType oemType) throws wt.util.WTPropertyVetoException {
      if (oemType == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oemType") },
               new java.beans.PropertyChangeEvent(this, "oemType", this.oemType, oemType));
   }

   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String MAKER = "maker";
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public static final java.lang.String MAKER_REFERENCE = "makerReference";
   wt.fc.ObjectReference makerReference;
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public e3ps.common.code.NumberCode getMaker() {
      return (makerReference != null) ? (e3ps.common.code.NumberCode) makerReference.getObject() : null;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public wt.fc.ObjectReference getMakerReference() {
      return makerReference;
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setMaker(e3ps.common.code.NumberCode the_maker) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMakerReference(the_maker == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_maker));
   }
   /**
    * @see e3ps.project.outputtype.OEMProjectType
    */
   public void setMakerReference(wt.fc.ObjectReference the_makerReference) throws wt.util.WTPropertyVetoException {
      makerReferenceValidate(the_makerReference);
      makerReference = (wt.fc.ObjectReference) the_makerReference;
   }
   void makerReferenceValidate(wt.fc.ObjectReference the_makerReference) throws wt.util.WTPropertyVetoException {
      if (the_makerReference == null || the_makerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "makerReference") },
               new java.beans.PropertyChangeEvent(this, "makerReference", this.makerReference, makerReference));
      if (the_makerReference != null && the_makerReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_makerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "makerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "makerReference", this.makerReference, makerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 314464170818708800L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( code );
      output.writeObject( description );
      output.writeBoolean( isDisabled );
      output.writeObject( isEcoCar );
      output.writeObject( isRunningChange );
      output.writeBoolean( isSavePlan );
      output.writeObject( makerReference );
      output.writeObject( name );
      output.writeInt( oLevel );
      output.writeObject( (oemType == null ? null : oemType.getStringValue()) );
      output.writeObject( parentReference );
      output.writeObject( path );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.outputtype.OEMProjectType) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "code", code );
      output.setString( "description", description );
      output.setBoolean( "isDisabled", isDisabled );
      output.setBooleanObject( "isEcoCar", isEcoCar );
      output.setBooleanObject( "isRunningChange", isRunningChange );
      output.setBoolean( "isSavePlan", isSavePlan );
      output.writeObject( "makerReference", makerReference, wt.fc.ObjectReference.class, true );
      output.setString( "name", name );
      output.setInt( "oLevel", oLevel );
      output.setString( "oemType", (oemType == null ? null : oemType.toString()) );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setString( "path", path );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      code = input.getString( "code" );
      description = input.getString( "description" );
      isDisabled = input.getBoolean( "isDisabled" );
      isEcoCar = input.getBooleanObject( "isEcoCar" );
      isRunningChange = input.getBooleanObject( "isRunningChange" );
      isSavePlan = input.getBoolean( "isSavePlan" );
      makerReference = (wt.fc.ObjectReference) input.readObject( "makerReference", makerReference, wt.fc.ObjectReference.class, true );
      name = input.getString( "name" );
      oLevel = input.getInt( "oLevel" );
      java.lang.String oemType_string_value = (java.lang.String) input.getString("oemType");
      if ( oemType_string_value != null ) { 
         oemType = (e3ps.project.outputtype.OEMType) wt.introspection.ClassInfo.getConstrainedEnum( getClass(), "oemType", oemType_string_value );
         if ( oemType == null )  // hard-coded type
            oemType = e3ps.project.outputtype.OEMType.toOEMType( oemType_string_value );
      }
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      path = input.getString( "path" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion314464170818708800L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      code = (java.lang.String) input.readObject();
      description = (java.lang.String) input.readObject();
      isDisabled = input.readBoolean();
      isEcoCar = (java.lang.Boolean) input.readObject();
      isRunningChange = (java.lang.Boolean) input.readObject();
      isSavePlan = input.readBoolean();
      makerReference = (wt.fc.ObjectReference) input.readObject();
      name = (java.lang.String) input.readObject();
      oLevel = input.readInt();
      java.lang.String oemType_string_value = (java.lang.String) input.readObject();
      try { 
         oemType = (e3ps.project.outputtype.OEMType) wt.fc.EnumeratedTypeUtil.toEnumeratedType( oemType_string_value );
      } catch( wt.util.WTInvalidParameterException e ) {
         // Old Format
         oemType = e3ps.project.outputtype.OEMType.toOEMType( oemType_string_value );
      }
      parentReference = (wt.fc.ObjectReference) input.readObject();
      path = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( OEMProjectType thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion314464170818708800L( input, readSerialVersionUID, superDone );
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
