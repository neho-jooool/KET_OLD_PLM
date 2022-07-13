package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TemplateAssessSheet extends wt.fc.WTObject implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = TemplateAssessSheet.class.getName();

   /**
    * Check Sheet Name
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String SHEET_NAME = "sheetName";
   static int SHEET_NAME_UPPER_LIMIT = -1;
   java.lang.String sheetName;
   /**
    * Check Sheet Name
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public java.lang.String getSheetName() {
      return sheetName;
   }
   /**
    * Check Sheet Name
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setSheetName(java.lang.String sheetName) throws wt.util.WTPropertyVetoException {
      sheetNameValidate(sheetName);
      this.sheetName = sheetName;
   }
   void sheetNameValidate(java.lang.String sheetName) throws wt.util.WTPropertyVetoException {
      if (SHEET_NAME_UPPER_LIMIT < 1) {
         try { SHEET_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_NAME_UPPER_LIMIT = 200; }
      }
      if (sheetName != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetName.toString(), SHEET_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetName"), java.lang.String.valueOf(java.lang.Math.min(SHEET_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetName", this.sheetName, sheetName));
   }

   /**
    * 설명
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String SHEET_DESC = "sheetDesc";
   static int SHEET_DESC_UPPER_LIMIT = -1;
   java.lang.String sheetDesc;
   /**
    * 설명
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public java.lang.String getSheetDesc() {
      return sheetDesc;
   }
   /**
    * 설명
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setSheetDesc(java.lang.String sheetDesc) throws wt.util.WTPropertyVetoException {
      sheetDescValidate(sheetDesc);
      this.sheetDesc = sheetDesc;
   }
   void sheetDescValidate(java.lang.String sheetDesc) throws wt.util.WTPropertyVetoException {
      if (SHEET_DESC_UPPER_LIMIT < 1) {
         try { SHEET_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_DESC_UPPER_LIMIT = 200; }
      }
      if (sheetDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetDesc.toString(), SHEET_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetDesc"), java.lang.String.valueOf(java.lang.Math.min(SHEET_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetDesc", this.sheetDesc, sheetDesc));
   }

   /**
    * 부품Oid
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String PART_OID = "partOid";
   static int PART_OID_UPPER_LIMIT = -1;
   java.lang.String partOid;
   /**
    * 부품Oid
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public java.lang.String getPartOid() {
      return partOid;
   }
   /**
    * 부품Oid
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setPartOid(java.lang.String partOid) throws wt.util.WTPropertyVetoException {
      partOidValidate(partOid);
      this.partOid = partOid;
   }
   void partOidValidate(java.lang.String partOid) throws wt.util.WTPropertyVetoException {
      if (PART_OID_UPPER_LIMIT < 1) {
         try { PART_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_OID_UPPER_LIMIT = 200; }
      }
      if (partOid != null && !wt.fc.PersistenceHelper.checkStoredLength(partOid.toString(), PART_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partOid"), java.lang.String.valueOf(java.lang.Math.min(PART_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partOid", this.partOid, partOid));
   }

   /**
    * 활성여부
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String ACTIVE = "active";
   static int ACTIVE_UPPER_LIMIT = -1;
   java.lang.String active;
   /**
    * 활성여부
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public java.lang.String getActive() {
      return active;
   }
   /**
    * 활성여부
    *
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setActive(java.lang.String active) throws wt.util.WTPropertyVetoException {
      activeValidate(active);
      this.active = active;
   }
   void activeValidate(java.lang.String active) throws wt.util.WTPropertyVetoException {
      if (ACTIVE_UPPER_LIMIT < 1) {
         try { ACTIVE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("active").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVE_UPPER_LIMIT = 200; }
      }
      if (active != null && !wt.fc.PersistenceHelper.checkStoredLength(active.toString(), ACTIVE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "active"), java.lang.String.valueOf(java.lang.Math.min(ACTIVE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "active", this.active, active));
   }

   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String DEV_TYPE = "devType";
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String DEV_TYPE_REFERENCE = "devTypeReference";
   wt.fc.ObjectReference devTypeReference;
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public e3ps.common.code.NumberCode getDevType() {
      return (devTypeReference != null) ? (e3ps.common.code.NumberCode) devTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public wt.fc.ObjectReference getDevTypeReference() {
      return devTypeReference;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setDevType(e3ps.common.code.NumberCode the_devType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevTypeReference(the_devType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_devType));
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setDevTypeReference(wt.fc.ObjectReference the_devTypeReference) throws wt.util.WTPropertyVetoException {
      devTypeReferenceValidate(the_devTypeReference);
      devTypeReference = (wt.fc.ObjectReference) the_devTypeReference;
   }
   void devTypeReferenceValidate(wt.fc.ObjectReference the_devTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_devTypeReference == null || the_devTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devTypeReference") },
               new java.beans.PropertyChangeEvent(this, "devTypeReference", this.devTypeReference, devTypeReference));
      if (the_devTypeReference != null && the_devTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_devTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "devTypeReference", this.devTypeReference, devTypeReference));
   }

   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String DEV_DIV = "devDiv";
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String DEV_DIV_REFERENCE = "devDivReference";
   wt.fc.ObjectReference devDivReference;
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public e3ps.common.code.NumberCode getDevDiv() {
      return (devDivReference != null) ? (e3ps.common.code.NumberCode) devDivReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public wt.fc.ObjectReference getDevDivReference() {
      return devDivReference;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setDevDiv(e3ps.common.code.NumberCode the_devDiv) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDevDivReference(the_devDiv == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_devDiv));
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setDevDivReference(wt.fc.ObjectReference the_devDivReference) throws wt.util.WTPropertyVetoException {
      devDivReferenceValidate(the_devDivReference);
      devDivReference = (wt.fc.ObjectReference) the_devDivReference;
   }
   void devDivReferenceValidate(wt.fc.ObjectReference the_devDivReference) throws wt.util.WTPropertyVetoException {
      if (the_devDivReference == null || the_devDivReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devDivReference") },
               new java.beans.PropertyChangeEvent(this, "devDivReference", this.devDivReference, devDivReference));
      if (the_devDivReference != null && the_devDivReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_devDivReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devDivReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "devDivReference", this.devDivReference, devDivReference));
   }

   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String DIVISION = "division";
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String DIVISION_REFERENCE = "divisionReference";
   wt.fc.ObjectReference divisionReference;
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public e3ps.common.code.NumberCode getDivision() {
      return (divisionReference != null) ? (e3ps.common.code.NumberCode) divisionReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public wt.fc.ObjectReference getDivisionReference() {
      return divisionReference;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setDivision(e3ps.common.code.NumberCode the_division) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDivisionReference(the_division == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_division));
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setDivisionReference(wt.fc.ObjectReference the_divisionReference) throws wt.util.WTPropertyVetoException {
      divisionReferenceValidate(the_divisionReference);
      divisionReference = (wt.fc.ObjectReference) the_divisionReference;
   }
   void divisionReferenceValidate(wt.fc.ObjectReference the_divisionReference) throws wt.util.WTPropertyVetoException {
      if (the_divisionReference == null || the_divisionReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "divisionReference") },
               new java.beans.PropertyChangeEvent(this, "divisionReference", this.divisionReference, divisionReference));
      if (the_divisionReference != null && the_divisionReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_divisionReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "divisionReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "divisionReference", this.divisionReference, divisionReference));
   }

   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String PROD_CATEGORY = "prodCategory";
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public static final java.lang.String PROD_CATEGORY_REFERENCE = "prodCategoryReference";
   wt.fc.ObjectReference prodCategoryReference;
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public e3ps.common.code.NumberCode getProdCategory() {
      return (prodCategoryReference != null) ? (e3ps.common.code.NumberCode) prodCategoryReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public wt.fc.ObjectReference getProdCategoryReference() {
      return prodCategoryReference;
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setProdCategory(e3ps.common.code.NumberCode the_prodCategory) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProdCategoryReference(the_prodCategory == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_prodCategory));
   }
   /**
    * @see ext.ket.project.gate.entity.TemplateAssessSheet
    */
   public void setProdCategoryReference(wt.fc.ObjectReference the_prodCategoryReference) throws wt.util.WTPropertyVetoException {
      prodCategoryReferenceValidate(the_prodCategoryReference);
      prodCategoryReference = (wt.fc.ObjectReference) the_prodCategoryReference;
   }
   void prodCategoryReferenceValidate(wt.fc.ObjectReference the_prodCategoryReference) throws wt.util.WTPropertyVetoException {
      if (the_prodCategoryReference == null || the_prodCategoryReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodCategoryReference") },
               new java.beans.PropertyChangeEvent(this, "prodCategoryReference", this.prodCategoryReference, prodCategoryReference));
      if (the_prodCategoryReference != null && the_prodCategoryReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_prodCategoryReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prodCategoryReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "prodCategoryReference", this.prodCategoryReference, prodCategoryReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -7099414752090556915L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( active );
      output.writeObject( devDivReference );
      output.writeObject( devTypeReference );
      output.writeObject( divisionReference );
      output.writeObject( owner );
      output.writeObject( partOid );
      output.writeObject( prodCategoryReference );
      output.writeObject( sheetDesc );
      output.writeObject( sheetName );
   }

   protected void super_writeExternal_TemplateAssessSheet(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.TemplateAssessSheet) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_TemplateAssessSheet(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "active", active );
      output.writeObject( "devDivReference", devDivReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "devTypeReference", devTypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "divisionReference", divisionReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "partOid", partOid );
      output.writeObject( "prodCategoryReference", prodCategoryReference, wt.fc.ObjectReference.class, true );
      output.setString( "sheetDesc", sheetDesc );
      output.setString( "sheetName", sheetName );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      active = input.getString( "active" );
      devDivReference = (wt.fc.ObjectReference) input.readObject( "devDivReference", devDivReference, wt.fc.ObjectReference.class, true );
      devTypeReference = (wt.fc.ObjectReference) input.readObject( "devTypeReference", devTypeReference, wt.fc.ObjectReference.class, true );
      divisionReference = (wt.fc.ObjectReference) input.readObject( "divisionReference", divisionReference, wt.fc.ObjectReference.class, true );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partOid = input.getString( "partOid" );
      prodCategoryReference = (wt.fc.ObjectReference) input.readObject( "prodCategoryReference", prodCategoryReference, wt.fc.ObjectReference.class, true );
      sheetDesc = input.getString( "sheetDesc" );
      sheetName = input.getString( "sheetName" );
   }

   boolean readVersion_7099414752090556915L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      active = (java.lang.String) input.readObject();
      devDivReference = (wt.fc.ObjectReference) input.readObject();
      devTypeReference = (wt.fc.ObjectReference) input.readObject();
      divisionReference = (wt.fc.ObjectReference) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partOid = (java.lang.String) input.readObject();
      prodCategoryReference = (wt.fc.ObjectReference) input.readObject();
      sheetDesc = (java.lang.String) input.readObject();
      sheetName = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( TemplateAssessSheet thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7099414752090556915L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_TemplateAssessSheet( _TemplateAssessSheet thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
