package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProductHistoryChage implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProductHistoryChage.class.getName();

   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public static final java.lang.String CHAGE_DETIL = "chageDetil";
   static int CHAGE_DETIL_UPPER_LIMIT = -1;
   java.lang.String chageDetil;
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public java.lang.String getChageDetil() {
      return chageDetil;
   }
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public void setChageDetil(java.lang.String chageDetil) throws wt.util.WTPropertyVetoException {
      chageDetilValidate(chageDetil);
      this.chageDetil = chageDetil;
   }
   void chageDetilValidate(java.lang.String chageDetil) throws wt.util.WTPropertyVetoException {
      if (CHAGE_DETIL_UPPER_LIMIT < 1) {
         try { CHAGE_DETIL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("chageDetil").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHAGE_DETIL_UPPER_LIMIT = 3000; }
      }
      if (chageDetil != null && !wt.fc.PersistenceHelper.checkStoredLength(chageDetil.toString(), CHAGE_DETIL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chageDetil"), java.lang.String.valueOf(java.lang.Math.min(CHAGE_DETIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "chageDetil", this.chageDetil, chageDetil));
   }

   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public static final java.lang.String CHAGE_DATE = "chageDate";
   java.sql.Timestamp chageDate;
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public java.sql.Timestamp getChageDate() {
      return chageDate;
   }
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public void setChageDate(java.sql.Timestamp chageDate) throws wt.util.WTPropertyVetoException {
      chageDateValidate(chageDate);
      this.chageDate = chageDate;
   }
   void chageDateValidate(java.sql.Timestamp chageDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public static final java.lang.String CHAGE_ATTR1 = "chageAttr1";
   static int CHAGE_ATTR1_UPPER_LIMIT = -1;
   java.lang.String chageAttr1;
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public java.lang.String getChageAttr1() {
      return chageAttr1;
   }
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public void setChageAttr1(java.lang.String chageAttr1) throws wt.util.WTPropertyVetoException {
      chageAttr1Validate(chageAttr1);
      this.chageAttr1 = chageAttr1;
   }
   void chageAttr1Validate(java.lang.String chageAttr1) throws wt.util.WTPropertyVetoException {
      if (CHAGE_ATTR1_UPPER_LIMIT < 1) {
         try { CHAGE_ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("chageAttr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHAGE_ATTR1_UPPER_LIMIT = 200; }
      }
      if (chageAttr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(chageAttr1.toString(), CHAGE_ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chageAttr1"), java.lang.String.valueOf(java.lang.Math.min(CHAGE_ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "chageAttr1", this.chageAttr1, chageAttr1));
   }

   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public static final java.lang.String CHAGE_ATTR2 = "chageAttr2";
   static int CHAGE_ATTR2_UPPER_LIMIT = -1;
   java.lang.String chageAttr2;
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public java.lang.String getChageAttr2() {
      return chageAttr2;
   }
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public void setChageAttr2(java.lang.String chageAttr2) throws wt.util.WTPropertyVetoException {
      chageAttr2Validate(chageAttr2);
      this.chageAttr2 = chageAttr2;
   }
   void chageAttr2Validate(java.lang.String chageAttr2) throws wt.util.WTPropertyVetoException {
      if (CHAGE_ATTR2_UPPER_LIMIT < 1) {
         try { CHAGE_ATTR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("chageAttr2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHAGE_ATTR2_UPPER_LIMIT = 200; }
      }
      if (chageAttr2 != null && !wt.fc.PersistenceHelper.checkStoredLength(chageAttr2.toString(), CHAGE_ATTR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chageAttr2"), java.lang.String.valueOf(java.lang.Math.min(CHAGE_ATTR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "chageAttr2", this.chageAttr2, chageAttr2));
   }

   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public static final java.lang.String MASTER_CHANGE = "masterChange";
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public static final java.lang.String MASTER_CHANGE_REFERENCE = "masterChangeReference";
   wt.fc.ObjectReference masterChangeReference;
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public e3ps.project.ProjectMaster getMasterChange() {
      return (masterChangeReference != null) ? (e3ps.project.ProjectMaster) masterChangeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public wt.fc.ObjectReference getMasterChangeReference() {
      return masterChangeReference;
   }
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public void setMasterChange(e3ps.project.ProjectMaster the_masterChange) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMasterChangeReference(the_masterChange == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProjectMaster) the_masterChange));
   }
   /**
    * @see e3ps.project.ProductHistoryChage
    */
   public void setMasterChangeReference(wt.fc.ObjectReference the_masterChangeReference) throws wt.util.WTPropertyVetoException {
      masterChangeReferenceValidate(the_masterChangeReference);
      masterChangeReference = (wt.fc.ObjectReference) the_masterChangeReference;
   }
   void masterChangeReferenceValidate(wt.fc.ObjectReference the_masterChangeReference) throws wt.util.WTPropertyVetoException {
      if (the_masterChangeReference == null || the_masterChangeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterChangeReference") },
               new java.beans.PropertyChangeEvent(this, "masterChangeReference", this.masterChangeReference, masterChangeReference));
      if (the_masterChangeReference != null && the_masterChangeReference.getReferencedClass() != null &&
            !e3ps.project.ProjectMaster.class.isAssignableFrom(the_masterChangeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterChangeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "masterChangeReference", this.masterChangeReference, masterChangeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -439383117094427393L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( chageAttr1 );
      output.writeObject( chageAttr2 );
      output.writeObject( chageDate );
      output.writeObject( chageDetil );
      output.writeObject( masterChangeReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProductHistoryChage) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "chageAttr1", chageAttr1 );
      output.setString( "chageAttr2", chageAttr2 );
      output.setTimestamp( "chageDate", chageDate );
      output.setString( "chageDetil", chageDetil );
      output.writeObject( "masterChangeReference", masterChangeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      chageAttr1 = input.getString( "chageAttr1" );
      chageAttr2 = input.getString( "chageAttr2" );
      chageDate = input.getTimestamp( "chageDate" );
      chageDetil = input.getString( "chageDetil" );
      masterChangeReference = (wt.fc.ObjectReference) input.readObject( "masterChangeReference", masterChangeReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_439383117094427393L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      chageAttr1 = (java.lang.String) input.readObject();
      chageAttr2 = (java.lang.String) input.readObject();
      chageDate = (java.sql.Timestamp) input.readObject();
      chageDetil = (java.lang.String) input.readObject();
      masterChangeReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( ProductHistoryChage thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_439383117094427393L( input, readSerialVersionUID, superDone );
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
