package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _PerformType implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = PerformType.class.getName();

   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String IS_BOM = "isBom";
   boolean isBom = false;
   /**
    * @see e3ps.project.PerformType
    */
   public boolean isIsBom() {
      return isBom;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setIsBom(boolean isBom) throws wt.util.WTPropertyVetoException {
      isBomValidate(isBom);
      this.isBom = isBom;
   }
   void isBomValidate(boolean isBom) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String IS_PACKAGE = "isPackage";
   boolean isPackage = false;
   /**
    * @see e3ps.project.PerformType
    */
   public boolean isIsPackage() {
      return isPackage;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setIsPackage(boolean isPackage) throws wt.util.WTPropertyVetoException {
      isPackageValidate(isPackage);
      this.isPackage = isPackage;
   }
   void isPackageValidate(boolean isPackage) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String IS_MASS = "isMass";
   boolean isMass = false;
   /**
    * @see e3ps.project.PerformType
    */
   public boolean isIsMass() {
      return isMass;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setIsMass(boolean isMass) throws wt.util.WTPropertyVetoException {
      isMassValidate(isMass);
      this.isMass = isMass;
   }
   void isMassValidate(boolean isMass) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String IS_APPRAISAL = "isAppraisal";
   boolean isAppraisal = false;
   /**
    * @see e3ps.project.PerformType
    */
   public boolean isIsAppraisal() {
      return isAppraisal;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setIsAppraisal(boolean isAppraisal) throws wt.util.WTPropertyVetoException {
      isAppraisalValidate(isAppraisal);
      this.isAppraisal = isAppraisal;
   }
   void isAppraisalValidate(boolean isAppraisal) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String IS_PRECEDENCE = "isPrecedence";
   boolean isPrecedence = false;
   /**
    * @see e3ps.project.PerformType
    */
   public boolean isIsPrecedence() {
      return isPrecedence;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setIsPrecedence(boolean isPrecedence) throws wt.util.WTPropertyVetoException {
      isPrecedenceValidate(isPrecedence);
      this.isPrecedence = isPrecedence;
   }
   void isPrecedenceValidate(boolean isPrecedence) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String IS_ETC = "isEtc";
   boolean isEtc = false;
   /**
    * @see e3ps.project.PerformType
    */
   public boolean isIsEtc() {
      return isEtc;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setIsEtc(boolean isEtc) throws wt.util.WTPropertyVetoException {
      isEtcValidate(isEtc);
      this.isEtc = isEtc;
   }
   void isEtcValidate(boolean isEtc) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String PERFORM = "perform";
   /**
    * @see e3ps.project.PerformType
    */
   public static final java.lang.String PERFORM_REFERENCE = "performReference";
   wt.fc.ObjectReference performReference;
   /**
    * @see e3ps.project.PerformType
    */
   public e3ps.project.Performance getPerform() {
      return (performReference != null) ? (e3ps.project.Performance) performReference.getObject() : null;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public wt.fc.ObjectReference getPerformReference() {
      return performReference;
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setPerform(e3ps.project.Performance the_perform) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPerformReference(the_perform == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.Performance) the_perform));
   }
   /**
    * @see e3ps.project.PerformType
    */
   public void setPerformReference(wt.fc.ObjectReference the_performReference) throws wt.util.WTPropertyVetoException {
      performReferenceValidate(the_performReference);
      performReference = (wt.fc.ObjectReference) the_performReference;
   }
   void performReferenceValidate(wt.fc.ObjectReference the_performReference) throws wt.util.WTPropertyVetoException {
      if (the_performReference != null && the_performReference.getReferencedClass() != null &&
            !e3ps.project.Performance.class.isAssignableFrom(the_performReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "performReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "performReference", this.performReference, performReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -8834183021067470642L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeBoolean( isAppraisal );
      output.writeBoolean( isBom );
      output.writeBoolean( isEtc );
      output.writeBoolean( isMass );
      output.writeBoolean( isPackage );
      output.writeBoolean( isPrecedence );
      output.writeObject( performReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.PerformType) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setBoolean( "isAppraisal", isAppraisal );
      output.setBoolean( "isBom", isBom );
      output.setBoolean( "isEtc", isEtc );
      output.setBoolean( "isMass", isMass );
      output.setBoolean( "isPackage", isPackage );
      output.setBoolean( "isPrecedence", isPrecedence );
      output.writeObject( "performReference", performReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      isAppraisal = input.getBoolean( "isAppraisal" );
      isBom = input.getBoolean( "isBom" );
      isEtc = input.getBoolean( "isEtc" );
      isMass = input.getBoolean( "isMass" );
      isPackage = input.getBoolean( "isPackage" );
      isPrecedence = input.getBoolean( "isPrecedence" );
      performReference = (wt.fc.ObjectReference) input.readObject( "performReference", performReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_8834183021067470642L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      isAppraisal = input.readBoolean();
      isBom = input.readBoolean();
      isEtc = input.readBoolean();
      isMass = input.readBoolean();
      isPackage = input.readBoolean();
      isPrecedence = input.readBoolean();
      performReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( PerformType thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8834183021067470642L( input, readSerialVersionUID, superDone );
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
