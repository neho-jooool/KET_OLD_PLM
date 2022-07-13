package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MoldProject extends e3ps.project.E3PSProject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = MoldProject.class.getName();

   /**
    * 제품중량
    *
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String PRODUCT_WEIGHT = "productWeight";
   static int PRODUCT_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String productWeight;
   /**
    * 제품중량
    *
    * @see e3ps.project.MoldProject
    */
   public java.lang.String getProductWeight() {
      return productWeight;
   }
   /**
    * 제품중량
    *
    * @see e3ps.project.MoldProject
    */
   public void setProductWeight(java.lang.String productWeight) throws wt.util.WTPropertyVetoException {
      productWeightValidate(productWeight);
      this.productWeight = productWeight;
   }
   void productWeightValidate(java.lang.String productWeight) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_WEIGHT_UPPER_LIMIT < 1) {
         try { PRODUCT_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (productWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(productWeight.toString(), PRODUCT_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productWeight"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productWeight", this.productWeight, productWeight));
   }

   /**
    * Scrap중량
    *
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String SCRAP_WEIGHT = "scrapWeight";
   static int SCRAP_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String scrapWeight;
   /**
    * Scrap중량
    *
    * @see e3ps.project.MoldProject
    */
   public java.lang.String getScrapWeight() {
      return scrapWeight;
   }
   /**
    * Scrap중량
    *
    * @see e3ps.project.MoldProject
    */
   public void setScrapWeight(java.lang.String scrapWeight) throws wt.util.WTPropertyVetoException {
      scrapWeightValidate(scrapWeight);
      this.scrapWeight = scrapWeight;
   }
   void scrapWeightValidate(java.lang.String scrapWeight) throws wt.util.WTPropertyVetoException {
      if (SCRAP_WEIGHT_UPPER_LIMIT < 1) {
         try { SCRAP_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (scrapWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapWeight.toString(), SCRAP_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapWeight"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapWeight", this.scrapWeight, scrapWeight));
   }

   /**
    * 특수사양
    *
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String SPECIAL_SPEC = "specialSpec";
   static int SPECIAL_SPEC_UPPER_LIMIT = -1;
   java.lang.String specialSpec;
   /**
    * 특수사양
    *
    * @see e3ps.project.MoldProject
    */
   public java.lang.String getSpecialSpec() {
      return specialSpec;
   }
   /**
    * 특수사양
    *
    * @see e3ps.project.MoldProject
    */
   public void setSpecialSpec(java.lang.String specialSpec) throws wt.util.WTPropertyVetoException {
      specialSpecValidate(specialSpec);
      this.specialSpec = specialSpec;
   }
   void specialSpecValidate(java.lang.String specialSpec) throws wt.util.WTPropertyVetoException {
      if (SPECIAL_SPEC_UPPER_LIMIT < 1) {
         try { SPECIAL_SPEC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("specialSpec").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPECIAL_SPEC_UPPER_LIMIT = 4000; }
      }
      if (specialSpec != null && !wt.fc.PersistenceHelper.checkStoredLength(specialSpec.toString(), SPECIAL_SPEC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "specialSpec"), java.lang.String.valueOf(java.lang.Math.min(SPECIAL_SPEC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "specialSpec", this.specialSpec, specialSpec));
   }

   /**
    * 비고
    *
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String REMARK = "remark";
   static int REMARK_UPPER_LIMIT = -1;
   java.lang.String remark;
   /**
    * 비고
    *
    * @see e3ps.project.MoldProject
    */
   public java.lang.String getRemark() {
      return remark;
   }
   /**
    * 비고
    *
    * @see e3ps.project.MoldProject
    */
   public void setRemark(java.lang.String remark) throws wt.util.WTPropertyVetoException {
      remarkValidate(remark);
      this.remark = remark;
   }
   void remarkValidate(java.lang.String remark) throws wt.util.WTPropertyVetoException {
      if (REMARK_UPPER_LIMIT < 1) {
         try { REMARK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("remark").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REMARK_UPPER_LIMIT = 200; }
      }
      if (remark != null && !wt.fc.PersistenceHelper.checkStoredLength(remark.toString(), REMARK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "remark"), java.lang.String.valueOf(java.lang.Math.min(REMARK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "remark", this.remark, remark));
   }

   /**
    * 제작처
    *
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String OUT_SOURCING = "outSourcing";
   static int OUT_SOURCING_UPPER_LIMIT = -1;
   java.lang.String outSourcing;
   /**
    * 제작처
    *
    * @see e3ps.project.MoldProject
    */
   public java.lang.String getOutSourcing() {
      return outSourcing;
   }
   /**
    * 제작처
    *
    * @see e3ps.project.MoldProject
    */
   public void setOutSourcing(java.lang.String outSourcing) throws wt.util.WTPropertyVetoException {
      outSourcingValidate(outSourcing);
      this.outSourcing = outSourcing;
   }
   void outSourcingValidate(java.lang.String outSourcing) throws wt.util.WTPropertyVetoException {
      if (OUT_SOURCING_UPPER_LIMIT < 1) {
         try { OUT_SOURCING_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outSourcing").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_SOURCING_UPPER_LIMIT = 200; }
      }
      if (outSourcing != null && !wt.fc.PersistenceHelper.checkStoredLength(outSourcing.toString(), OUT_SOURCING_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outSourcing"), java.lang.String.valueOf(java.lang.Math.min(OUT_SOURCING_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outSourcing", this.outSourcing, outSourcing));
   }

   /**
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String SHRINKAGE = "shrinkage";
   static int SHRINKAGE_UPPER_LIMIT = -1;
   java.lang.String shrinkage;
   /**
    * @see e3ps.project.MoldProject
    */
   public java.lang.String getShrinkage() {
      return shrinkage;
   }
   /**
    * @see e3ps.project.MoldProject
    */
   public void setShrinkage(java.lang.String shrinkage) throws wt.util.WTPropertyVetoException {
      shrinkageValidate(shrinkage);
      this.shrinkage = shrinkage;
   }
   void shrinkageValidate(java.lang.String shrinkage) throws wt.util.WTPropertyVetoException {
      if (SHRINKAGE_UPPER_LIMIT < 1) {
         try { SHRINKAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shrinkage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHRINKAGE_UPPER_LIMIT = 200; }
      }
      if (shrinkage != null && !wt.fc.PersistenceHelper.checkStoredLength(shrinkage.toString(), SHRINKAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shrinkage"), java.lang.String.valueOf(java.lang.Math.min(SHRINKAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shrinkage", this.shrinkage, shrinkage));
   }

   /**
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String MOLD_INFO = "moldInfo";
   /**
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String MOLD_INFO_REFERENCE = "moldInfoReference";
   wt.fc.ObjectReference moldInfoReference;
   /**
    * @see e3ps.project.MoldProject
    */
   public e3ps.project.MoldItemInfo getMoldInfo() {
      return (moldInfoReference != null) ? (e3ps.project.MoldItemInfo) moldInfoReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldProject
    */
   public wt.fc.ObjectReference getMoldInfoReference() {
      return moldInfoReference;
   }
   /**
    * @see e3ps.project.MoldProject
    */
   public void setMoldInfo(e3ps.project.MoldItemInfo the_moldInfo) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldInfoReference(the_moldInfo == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.MoldItemInfo) the_moldInfo));
   }
   /**
    * @see e3ps.project.MoldProject
    */
   public void setMoldInfoReference(wt.fc.ObjectReference the_moldInfoReference) throws wt.util.WTPropertyVetoException {
      moldInfoReferenceValidate(the_moldInfoReference);
      moldInfoReference = (wt.fc.ObjectReference) the_moldInfoReference;
   }
   void moldInfoReferenceValidate(wt.fc.ObjectReference the_moldInfoReference) throws wt.util.WTPropertyVetoException {
      if (the_moldInfoReference == null || the_moldInfoReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldInfoReference") },
               new java.beans.PropertyChangeEvent(this, "moldInfoReference", this.moldInfoReference, moldInfoReference));
      if (the_moldInfoReference != null && the_moldInfoReference.getReferencedClass() != null &&
            !e3ps.project.MoldItemInfo.class.isAssignableFrom(the_moldInfoReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldInfoReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldInfoReference", this.moldInfoReference, moldInfoReference));
   }

   /**
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String MOLD_MACHINE = "moldMachine";
   /**
    * @see e3ps.project.MoldProject
    */
   public static final java.lang.String MOLD_MACHINE_REFERENCE = "moldMachineReference";
   wt.fc.ObjectReference moldMachineReference;
   /**
    * @see e3ps.project.MoldProject
    */
   public e3ps.project.machine.MoldMachine getMoldMachine() {
      return (moldMachineReference != null) ? (e3ps.project.machine.MoldMachine) moldMachineReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldProject
    */
   public wt.fc.ObjectReference getMoldMachineReference() {
      return moldMachineReference;
   }
   /**
    * @see e3ps.project.MoldProject
    */
   public void setMoldMachine(e3ps.project.machine.MoldMachine the_moldMachine) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldMachineReference(the_moldMachine == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.machine.MoldMachine) the_moldMachine));
   }
   /**
    * @see e3ps.project.MoldProject
    */
   public void setMoldMachineReference(wt.fc.ObjectReference the_moldMachineReference) throws wt.util.WTPropertyVetoException {
      moldMachineReferenceValidate(the_moldMachineReference);
      moldMachineReference = (wt.fc.ObjectReference) the_moldMachineReference;
   }
   void moldMachineReferenceValidate(wt.fc.ObjectReference the_moldMachineReference) throws wt.util.WTPropertyVetoException {
      if (the_moldMachineReference != null && the_moldMachineReference.getReferencedClass() != null &&
            !e3ps.project.machine.MoldMachine.class.isAssignableFrom(the_moldMachineReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMachineReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldMachineReference", this.moldMachineReference, moldMachineReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 2862513024486994607L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( moldInfoReference );
      output.writeObject( moldMachineReference );
      output.writeObject( outSourcing );
      output.writeObject( productWeight );
      output.writeObject( remark );
      output.writeObject( scrapWeight );
      output.writeObject( shrinkage );
      output.writeObject( specialSpec );
   }

   protected void super_writeExternal_MoldProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.MoldProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_MoldProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "moldInfoReference", moldInfoReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "moldMachineReference", moldMachineReference, wt.fc.ObjectReference.class, true );
      output.setString( "outSourcing", outSourcing );
      output.setString( "productWeight", productWeight );
      output.setString( "remark", remark );
      output.setString( "scrapWeight", scrapWeight );
      output.setString( "shrinkage", shrinkage );
      output.setString( "specialSpec", specialSpec );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      moldInfoReference = (wt.fc.ObjectReference) input.readObject( "moldInfoReference", moldInfoReference, wt.fc.ObjectReference.class, true );
      moldMachineReference = (wt.fc.ObjectReference) input.readObject( "moldMachineReference", moldMachineReference, wt.fc.ObjectReference.class, true );
      outSourcing = input.getString( "outSourcing" );
      productWeight = input.getString( "productWeight" );
      remark = input.getString( "remark" );
      scrapWeight = input.getString( "scrapWeight" );
      shrinkage = input.getString( "shrinkage" );
      specialSpec = input.getString( "specialSpec" );
   }

   boolean readVersion2862513024486994607L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      moldInfoReference = (wt.fc.ObjectReference) input.readObject();
      moldMachineReference = (wt.fc.ObjectReference) input.readObject();
      outSourcing = (java.lang.String) input.readObject();
      productWeight = (java.lang.String) input.readObject();
      remark = (java.lang.String) input.readObject();
      scrapWeight = (java.lang.String) input.readObject();
      shrinkage = (java.lang.String) input.readObject();
      specialSpec = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( MoldProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2862513024486994607L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_MoldProject( _MoldProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
