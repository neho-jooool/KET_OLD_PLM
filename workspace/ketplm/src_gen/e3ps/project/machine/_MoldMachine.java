package e3ps.project.machine;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MoldMachine implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.machine.machineResource";
   static final java.lang.String CLASSNAME = MoldMachine.class.getName();

   /**
    * Model
    *
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String MODEL = "model";
   static int MODEL_UPPER_LIMIT = -1;
   java.lang.String model;
   /**
    * Model
    *
    * @see e3ps.project.machine.MoldMachine
    */
   public java.lang.String getModel() {
      return model;
   }
   /**
    * Model
    *
    * @see e3ps.project.machine.MoldMachine
    */
   public void setModel(java.lang.String model) throws wt.util.WTPropertyVetoException {
      modelValidate(model);
      this.model = model;
   }
   void modelValidate(java.lang.String model) throws wt.util.WTPropertyVetoException {
      if (MODEL_UPPER_LIMIT < 1) {
         try { MODEL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("model").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODEL_UPPER_LIMIT = 200; }
      }
      if (model != null && !wt.fc.PersistenceHelper.checkStoredLength(model.toString(), MODEL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "model"), java.lang.String.valueOf(java.lang.Math.min(MODEL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "model", this.model, model));
   }

   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String MOLD_TYPE = "moldType";
   static int MOLD_TYPE_UPPER_LIMIT = -1;
   java.lang.String moldType;
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public java.lang.String getMoldType() {
      return moldType;
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public void setMoldType(java.lang.String moldType) throws wt.util.WTPropertyVetoException {
      moldTypeValidate(moldType);
      this.moldType = moldType;
   }
   void moldTypeValidate(java.lang.String moldType) throws wt.util.WTPropertyVetoException {
      if (MOLD_TYPE_UPPER_LIMIT < 1) {
         try { MOLD_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_TYPE_UPPER_LIMIT = 200; }
      }
      if (moldType != null && !wt.fc.PersistenceHelper.checkStoredLength(moldType.toString(), MOLD_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldType"), java.lang.String.valueOf(java.lang.Math.min(MOLD_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldType", this.moldType, moldType));
   }

   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String MACHINE_TYPE = "machineType";
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String MACHINE_TYPE_REFERENCE = "machineTypeReference";
   wt.fc.ObjectReference machineTypeReference;
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public e3ps.common.code.NumberCode getMachineType() {
      return (machineTypeReference != null) ? (e3ps.common.code.NumberCode) machineTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public wt.fc.ObjectReference getMachineTypeReference() {
      return machineTypeReference;
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public void setMachineType(e3ps.common.code.NumberCode the_machineType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMachineTypeReference(the_machineType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_machineType));
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public void setMachineTypeReference(wt.fc.ObjectReference the_machineTypeReference) throws wt.util.WTPropertyVetoException {
      machineTypeReferenceValidate(the_machineTypeReference);
      machineTypeReference = (wt.fc.ObjectReference) the_machineTypeReference;
   }
   void machineTypeReferenceValidate(wt.fc.ObjectReference the_machineTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_machineTypeReference == null || the_machineTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineTypeReference") },
               new java.beans.PropertyChangeEvent(this, "machineTypeReference", this.machineTypeReference, machineTypeReference));
      if (the_machineTypeReference != null && the_machineTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_machineTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "machineTypeReference", this.machineTypeReference, machineTypeReference));
   }

   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String TON = "ton";
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String TON_REFERENCE = "tonReference";
   wt.fc.ObjectReference tonReference;
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public e3ps.common.code.NumberCode getTon() {
      return (tonReference != null) ? (e3ps.common.code.NumberCode) tonReference.getObject() : null;
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public wt.fc.ObjectReference getTonReference() {
      return tonReference;
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public void setTon(e3ps.common.code.NumberCode the_ton) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTonReference(the_ton == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_ton));
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public void setTonReference(wt.fc.ObjectReference the_tonReference) throws wt.util.WTPropertyVetoException {
      tonReferenceValidate(the_tonReference);
      tonReference = (wt.fc.ObjectReference) the_tonReference;
   }
   void tonReferenceValidate(wt.fc.ObjectReference the_tonReference) throws wt.util.WTPropertyVetoException {
      if (the_tonReference == null || the_tonReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tonReference") },
               new java.beans.PropertyChangeEvent(this, "tonReference", this.tonReference, tonReference));
      if (the_tonReference != null && the_tonReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_tonReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tonReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "tonReference", this.tonReference, tonReference));
   }

   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String MACHINE_MAKER = "machineMaker";
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public static final java.lang.String MACHINE_MAKER_REFERENCE = "machineMakerReference";
   wt.fc.ObjectReference machineMakerReference;
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public e3ps.common.code.NumberCode getMachineMaker() {
      return (machineMakerReference != null) ? (e3ps.common.code.NumberCode) machineMakerReference.getObject() : null;
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public wt.fc.ObjectReference getMachineMakerReference() {
      return machineMakerReference;
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public void setMachineMaker(e3ps.common.code.NumberCode the_machineMaker) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMachineMakerReference(the_machineMaker == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_machineMaker));
   }
   /**
    * @see e3ps.project.machine.MoldMachine
    */
   public void setMachineMakerReference(wt.fc.ObjectReference the_machineMakerReference) throws wt.util.WTPropertyVetoException {
      machineMakerReferenceValidate(the_machineMakerReference);
      machineMakerReference = (wt.fc.ObjectReference) the_machineMakerReference;
   }
   void machineMakerReferenceValidate(wt.fc.ObjectReference the_machineMakerReference) throws wt.util.WTPropertyVetoException {
      if (the_machineMakerReference == null || the_machineMakerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineMakerReference") },
               new java.beans.PropertyChangeEvent(this, "machineMakerReference", this.machineMakerReference, machineMakerReference));
      if (the_machineMakerReference != null && the_machineMakerReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_machineMakerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineMakerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "machineMakerReference", this.machineMakerReference, machineMakerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 680968695107831730L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( machineMakerReference );
      output.writeObject( machineTypeReference );
      output.writeObject( model );
      output.writeObject( moldType );
      output.writeObject( thePersistInfo );
      output.writeObject( tonReference );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.machine.MoldMachine) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "machineMakerReference", machineMakerReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "machineTypeReference", machineTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "model", model );
      output.setString( "moldType", moldType );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.writeObject( "tonReference", tonReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      machineMakerReference = (wt.fc.ObjectReference) input.readObject( "machineMakerReference", machineMakerReference, wt.fc.ObjectReference.class, true );
      machineTypeReference = (wt.fc.ObjectReference) input.readObject( "machineTypeReference", machineTypeReference, wt.fc.ObjectReference.class, true );
      model = input.getString( "model" );
      moldType = input.getString( "moldType" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      tonReference = (wt.fc.ObjectReference) input.readObject( "tonReference", tonReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion680968695107831730L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      machineMakerReference = (wt.fc.ObjectReference) input.readObject();
      machineTypeReference = (wt.fc.ObjectReference) input.readObject();
      model = (java.lang.String) input.readObject();
      moldType = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      tonReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( MoldMachine thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion680968695107831730L( input, readSerialVersionUID, superDone );
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
