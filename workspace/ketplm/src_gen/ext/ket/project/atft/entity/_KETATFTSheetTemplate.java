package ext.ket.project.atft.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETATFTSheetTemplate implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.atft.entity.entityResource";
   static final java.lang.String CLASSNAME = KETATFTSheetTemplate.class.getName();

   /**
    * 구분
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public static final java.lang.String CLASSIFICATION = "classification";
   static int CLASSIFICATION_UPPER_LIMIT = -1;
   java.lang.String classification;
   /**
    * 구분
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public java.lang.String getClassification() {
      return classification;
   }
   /**
    * 구분
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public void setClassification(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      classificationValidate(classification);
      this.classification = classification;
   }
   void classificationValidate(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      if (CLASSIFICATION_UPPER_LIMIT < 1) {
         try { CLASSIFICATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classification").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASSIFICATION_UPPER_LIMIT = 200; }
      }
      if (classification != null && !wt.fc.PersistenceHelper.checkStoredLength(classification.toString(), CLASSIFICATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classification"), java.lang.String.valueOf(java.lang.Math.min(CLASSIFICATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classification", this.classification, classification));
   }

   /**
    * 순서
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public static final java.lang.String SORT_NO = "sortNo";
   static int SORT_NO_UPPER_LIMIT = -1;
   java.lang.String sortNo;
   /**
    * 순서
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public java.lang.String getSortNo() {
      return sortNo;
   }
   /**
    * 순서
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public void setSortNo(java.lang.String sortNo) throws wt.util.WTPropertyVetoException {
      sortNoValidate(sortNo);
      this.sortNo = sortNo;
   }
   void sortNoValidate(java.lang.String sortNo) throws wt.util.WTPropertyVetoException {
      if (SORT_NO_UPPER_LIMIT < 1) {
         try { SORT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sortNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SORT_NO_UPPER_LIMIT = 200; }
      }
      if (sortNo != null && !wt.fc.PersistenceHelper.checkStoredLength(sortNo.toString(), SORT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sortNo"), java.lang.String.valueOf(java.lang.Math.min(SORT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sortNo", this.sortNo, sortNo));
   }

   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public static final java.lang.String ATFT_CODE = "atftCode";
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public static final java.lang.String ATFT_CODE_REFERENCE = "atftCodeReference";
   wt.fc.ObjectReference atftCodeReference;
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public e3ps.common.code.NumberCode getAtftCode() {
      return (atftCodeReference != null) ? (e3ps.common.code.NumberCode) atftCodeReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public wt.fc.ObjectReference getAtftCodeReference() {
      return atftCodeReference;
   }
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public void setAtftCode(e3ps.common.code.NumberCode the_atftCode) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setAtftCodeReference(the_atftCode == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_atftCode));
   }
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetTemplate
    */
   public void setAtftCodeReference(wt.fc.ObjectReference the_atftCodeReference) throws wt.util.WTPropertyVetoException {
      atftCodeReferenceValidate(the_atftCodeReference);
      atftCodeReference = (wt.fc.ObjectReference) the_atftCodeReference;
   }
   void atftCodeReferenceValidate(wt.fc.ObjectReference the_atftCodeReference) throws wt.util.WTPropertyVetoException {
      if (the_atftCodeReference == null || the_atftCodeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "atftCodeReference") },
               new java.beans.PropertyChangeEvent(this, "atftCodeReference", this.atftCodeReference, atftCodeReference));
      if (the_atftCodeReference != null && the_atftCodeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_atftCodeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "atftCodeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "atftCodeReference", this.atftCodeReference, atftCodeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -8127053795781393351L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( atftCodeReference );
      output.writeObject( classification );
      output.writeObject( sortNo );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.atft.entity.KETATFTSheetTemplate) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "atftCodeReference", atftCodeReference, wt.fc.ObjectReference.class, true );
      output.setString( "classification", classification );
      output.setString( "sortNo", sortNo );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      atftCodeReference = (wt.fc.ObjectReference) input.readObject( "atftCodeReference", atftCodeReference, wt.fc.ObjectReference.class, true );
      classification = input.getString( "classification" );
      sortNo = input.getString( "sortNo" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_8127053795781393351L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      atftCodeReference = (wt.fc.ObjectReference) input.readObject();
      classification = (java.lang.String) input.readObject();
      sortNo = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETATFTSheetTemplate thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8127053795781393351L( input, readSerialVersionUID, superDone );
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
