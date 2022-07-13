package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostFormula implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostFormula.class.getName();

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String FORMULA = "formula";
   static int FORMULA_UPPER_LIMIT = -1;
   java.lang.String formula;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getFormula() {
      return formula;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setFormula(java.lang.String formula) throws wt.util.WTPropertyVetoException {
      formulaValidate(formula);
      this.formula = formula;
   }
   void formulaValidate(java.lang.String formula) throws wt.util.WTPropertyVetoException {
      if (FORMULA_UPPER_LIMIT < 1) {
         try { FORMULA_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("formula").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FORMULA_UPPER_LIMIT = 8000; }
      }
      if (formula != null && !wt.fc.PersistenceHelper.checkStoredLength(formula.toString(), FORMULA_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "formula"), java.lang.String.valueOf(java.lang.Math.min(FORMULA_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "formula", this.formula, formula));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String FORMULA_KEYS = "formulaKeys";
   static int FORMULA_KEYS_UPPER_LIMIT = -1;
   java.lang.String formulaKeys;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getFormulaKeys() {
      return formulaKeys;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setFormulaKeys(java.lang.String formulaKeys) throws wt.util.WTPropertyVetoException {
      formulaKeysValidate(formulaKeys);
      this.formulaKeys = formulaKeys;
   }
   void formulaKeysValidate(java.lang.String formulaKeys) throws wt.util.WTPropertyVetoException {
      if (FORMULA_KEYS_UPPER_LIMIT < 1) {
         try { FORMULA_KEYS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("formulaKeys").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FORMULA_KEYS_UPPER_LIMIT = 8000; }
      }
      if (formulaKeys != null && !wt.fc.PersistenceHelper.checkStoredLength(formulaKeys.toString(), FORMULA_KEYS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "formulaKeys"), java.lang.String.valueOf(java.lang.Math.min(FORMULA_KEYS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "formulaKeys", this.formulaKeys, formulaKeys));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String CALCULATION_STD = "calculationStd";
   static int CALCULATION_STD_UPPER_LIMIT = -1;
   java.lang.String calculationStd;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getCalculationStd() {
      return calculationStd;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setCalculationStd(java.lang.String calculationStd) throws wt.util.WTPropertyVetoException {
      calculationStdValidate(calculationStd);
      this.calculationStd = calculationStd;
   }
   void calculationStdValidate(java.lang.String calculationStd) throws wt.util.WTPropertyVetoException {
      if (CALCULATION_STD_UPPER_LIMIT < 1) {
         try { CALCULATION_STD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calculationStd").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALCULATION_STD_UPPER_LIMIT = 200; }
      }
      if (calculationStd != null && !wt.fc.PersistenceHelper.checkStoredLength(calculationStd.toString(), CALCULATION_STD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calculationStd"), java.lang.String.valueOf(java.lang.Math.min(CALCULATION_STD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calculationStd", this.calculationStd, calculationStd));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String SORT_LOCATION = "sortLocation";
   java.lang.Integer sortLocation;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.Integer getSortLocation() {
      return sortLocation;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setSortLocation(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
      sortLocationValidate(sortLocation);
      this.sortLocation = sortLocation;
   }
   void sortLocationValidate(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String DEPT_TYPE = "deptType";
   static int DEPT_TYPE_UPPER_LIMIT = -1;
   java.lang.String deptType;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getDeptType() {
      return deptType;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setDeptType(java.lang.String deptType) throws wt.util.WTPropertyVetoException {
      deptTypeValidate(deptType);
      this.deptType = deptType;
   }
   void deptTypeValidate(java.lang.String deptType) throws wt.util.WTPropertyVetoException {
      if (DEPT_TYPE_UPPER_LIMIT < 1) {
         try { DEPT_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_TYPE_UPPER_LIMIT = 200; }
      }
      if (deptType != null && !wt.fc.PersistenceHelper.checkStoredLength(deptType.toString(), DEPT_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptType"), java.lang.String.valueOf(java.lang.Math.min(DEPT_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptType", this.deptType, deptType));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String PART_TYPE = "partType";
   static int PART_TYPE_UPPER_LIMIT = -1;
   java.lang.String partType;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getPartType() {
      return partType;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setPartType(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      partTypeValidate(partType);
      this.partType = partType;
   }
   void partTypeValidate(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      if (PART_TYPE_UPPER_LIMIT < 1) {
         try { PART_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_TYPE_UPPER_LIMIT = 8000; }
      }
      if (partType != null && !wt.fc.PersistenceHelper.checkStoredLength(partType.toString(), PART_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partType"), java.lang.String.valueOf(java.lang.Math.min(PART_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partType", this.partType, partType));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String MFT_FACTORY1 = "mftFactory1";
   static int MFT_FACTORY1_UPPER_LIMIT = -1;
   java.lang.String mftFactory1;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getMftFactory1() {
      return mftFactory1;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setMftFactory1(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      mftFactory1Validate(mftFactory1);
      this.mftFactory1 = mftFactory1;
   }
   void mftFactory1Validate(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY1_UPPER_LIMIT < 1) {
         try { MFT_FACTORY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY1_UPPER_LIMIT = 8000; }
      }
      if (mftFactory1 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory1.toString(), MFT_FACTORY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory1"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory1", this.mftFactory1, mftFactory1));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String MFT_FACTORY2 = "mftFactory2";
   java.lang.Object mftFactory2;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.Object getMftFactory2() {
      return mftFactory2;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setMftFactory2(java.lang.Object mftFactory2) throws wt.util.WTPropertyVetoException {
      mftFactory2Validate(mftFactory2);
      this.mftFactory2 = mftFactory2;
   }
   void mftFactory2Validate(java.lang.Object mftFactory2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String MFT_FACTORY2_OLD = "mftFactory2_old";
   static int MFT_FACTORY2_OLD_UPPER_LIMIT = -1;
   java.lang.String mftFactory2_old;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getMftFactory2_old() {
      return mftFactory2_old;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setMftFactory2_old(java.lang.String mftFactory2_old) throws wt.util.WTPropertyVetoException {
      mftFactory2_oldValidate(mftFactory2_old);
      this.mftFactory2_old = mftFactory2_old;
   }
   void mftFactory2_oldValidate(java.lang.String mftFactory2_old) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY2_OLD_UPPER_LIMIT < 1) {
         try { MFT_FACTORY2_OLD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory2_old").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY2_OLD_UPPER_LIMIT = 8000; }
      }
      if (mftFactory2_old != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory2_old.toString(), MFT_FACTORY2_OLD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory2_old"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY2_OLD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory2_old", this.mftFactory2_old, mftFactory2_old));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String MAPPING_CODE = "mappingCode";
   static int MAPPING_CODE_UPPER_LIMIT = -1;
   java.lang.String mappingCode;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getMappingCode() {
      return mappingCode;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setMappingCode(java.lang.String mappingCode) throws wt.util.WTPropertyVetoException {
      mappingCodeValidate(mappingCode);
      this.mappingCode = mappingCode;
   }
   void mappingCodeValidate(java.lang.String mappingCode) throws wt.util.WTPropertyVetoException {
      if (MAPPING_CODE_UPPER_LIMIT < 1) {
         try { MAPPING_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mappingCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAPPING_CODE_UPPER_LIMIT = 200; }
      }
      if (mappingCode != null && !wt.fc.PersistenceHelper.checkStoredLength(mappingCode.toString(), MAPPING_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mappingCode"), java.lang.String.valueOf(java.lang.Math.min(MAPPING_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mappingCode", this.mappingCode, mappingCode));
   }

   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see ext.ket.cost.entity.CostFormula
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
    * 수식 버전
    *
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String FORMULA_VERSION = "formulaVersion";
   java.lang.Integer formulaVersion = 0;
   /**
    * 수식 버전
    *
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.Integer getFormulaVersion() {
      return formulaVersion;
   }
   /**
    * 수식 버전
    *
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setFormulaVersion(java.lang.Integer formulaVersion) throws wt.util.WTPropertyVetoException {
      formulaVersionValidate(formulaVersion);
      this.formulaVersion = formulaVersion;
   }
   void formulaVersionValidate(java.lang.Integer formulaVersion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 수식배포상태
    *
    * @see ext.ket.cost.entity.CostFormula
    */
   public static final java.lang.String STATUS = "status";
   static int STATUS_UPPER_LIMIT = -1;
   java.lang.String status = "INWORK";
   /**
    * 수식배포상태
    *
    * @see ext.ket.cost.entity.CostFormula
    */
   public java.lang.String getStatus() {
      return status;
   }
   /**
    * 수식배포상태
    *
    * @see ext.ket.cost.entity.CostFormula
    */
   public void setStatus(java.lang.String status) throws wt.util.WTPropertyVetoException {
      statusValidate(status);
      this.status = status;
   }
   void statusValidate(java.lang.String status) throws wt.util.WTPropertyVetoException {
      if (STATUS_UPPER_LIMIT < 1) {
         try { STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("status").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STATUS_UPPER_LIMIT = 200; }
      }
      if (status != null && !wt.fc.PersistenceHelper.checkStoredLength(status.toString(), STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "status"), java.lang.String.valueOf(java.lang.Math.min(STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "status", this.status, status));
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

   public static final long EXTERNALIZATION_VERSION_UID = -8962570919158378120L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( calculationStd );
      output.writeObject( deptType );
      output.writeObject( formula );
      output.writeObject( formulaKeys );
      output.writeObject( formulaVersion );
      output.writeObject( mappingCode );
      output.writeObject( mftFactory1 );
      output.writeObject( mftFactory2 );
      output.writeObject( mftFactory2_old );
      output.writeObject( name );
      output.writeObject( parentReference );
      output.writeObject( partType );
      output.writeObject( sortLocation );
      output.writeObject( status );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostFormula) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "calculationStd", calculationStd );
      output.setString( "deptType", deptType );
      output.setString( "formula", formula );
      output.setString( "formulaKeys", formulaKeys );
      output.setIntObject( "formulaVersion", formulaVersion );
      output.setString( "mappingCode", mappingCode );
      output.setString( "mftFactory1", mftFactory1 );
      output.setObject( "mftFactory2", mftFactory2 );
      output.setString( "mftFactory2_old", mftFactory2_old );
      output.setString( "name", name );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setString( "partType", partType );
      output.setIntObject( "sortLocation", sortLocation );
      output.setString( "status", status );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      calculationStd = input.getString( "calculationStd" );
      deptType = input.getString( "deptType" );
      formula = input.getString( "formula" );
      formulaKeys = input.getString( "formulaKeys" );
      formulaVersion = input.getIntObject( "formulaVersion" );
      mappingCode = input.getString( "mappingCode" );
      mftFactory1 = input.getString( "mftFactory1" );
      mftFactory2 = (java.lang.Object) input.getObject( "mftFactory2" );
      mftFactory2_old = input.getString( "mftFactory2_old" );
      name = input.getString( "name" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      partType = input.getString( "partType" );
      sortLocation = input.getIntObject( "sortLocation" );
      status = input.getString( "status" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_8962570919158378120L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      calculationStd = (java.lang.String) input.readObject();
      deptType = (java.lang.String) input.readObject();
      formula = (java.lang.String) input.readObject();
      formulaKeys = (java.lang.String) input.readObject();
      formulaVersion = (java.lang.Integer) input.readObject();
      mappingCode = (java.lang.String) input.readObject();
      mftFactory1 = (java.lang.String) input.readObject();
      mftFactory2 = (java.lang.Object) input.readObject();
      mftFactory2_old = (java.lang.String) input.readObject();
      name = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      partType = (java.lang.String) input.readObject();
      sortLocation = (java.lang.Integer) input.readObject();
      status = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CostFormula thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8962570919158378120L( input, readSerialVersionUID, superDone );
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
