package e3ps.project.trySchdule;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TryChange implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.trySchdule.trySchduleResource";
   static final java.lang.String CLASSNAME = TryChange.class.getName();

   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public static final java.lang.String CHANGE_DATE = "changeDate";
   java.sql.Timestamp changeDate;
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public java.sql.Timestamp getChangeDate() {
      return changeDate;
   }
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public void setChangeDate(java.sql.Timestamp changeDate) throws wt.util.WTPropertyVetoException {
      changeDateValidate(changeDate);
      this.changeDate = changeDate;
   }
   void changeDateValidate(java.sql.Timestamp changeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public static final java.lang.String CHANGE_DETIL = "changeDetil";
   static int CHANGE_DETIL_UPPER_LIMIT = -1;
   java.lang.String changeDetil;
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public java.lang.String getChangeDetil() {
      return changeDetil;
   }
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public void setChangeDetil(java.lang.String changeDetil) throws wt.util.WTPropertyVetoException {
      changeDetilValidate(changeDetil);
      this.changeDetil = changeDetil;
   }
   void changeDetilValidate(java.lang.String changeDetil) throws wt.util.WTPropertyVetoException {
      if (CHANGE_DETIL_UPPER_LIMIT < 1) {
         try { CHANGE_DETIL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeDetil").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_DETIL_UPPER_LIMIT = 3000; }
      }
      if (changeDetil != null && !wt.fc.PersistenceHelper.checkStoredLength(changeDetil.toString(), CHANGE_DETIL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeDetil"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_DETIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeDetil", this.changeDetil, changeDetil));
   }

   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public static final java.lang.String CHANGE_ATTR1 = "changeAttr1";
   static int CHANGE_ATTR1_UPPER_LIMIT = -1;
   java.lang.String changeAttr1;
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public java.lang.String getChangeAttr1() {
      return changeAttr1;
   }
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public void setChangeAttr1(java.lang.String changeAttr1) throws wt.util.WTPropertyVetoException {
      changeAttr1Validate(changeAttr1);
      this.changeAttr1 = changeAttr1;
   }
   void changeAttr1Validate(java.lang.String changeAttr1) throws wt.util.WTPropertyVetoException {
      if (CHANGE_ATTR1_UPPER_LIMIT < 1) {
         try { CHANGE_ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeAttr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_ATTR1_UPPER_LIMIT = 200; }
      }
      if (changeAttr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(changeAttr1.toString(), CHANGE_ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeAttr1"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeAttr1", this.changeAttr1, changeAttr1));
   }

   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public static final java.lang.String CHANGE_ATTR2 = "changeAttr2";
   static int CHANGE_ATTR2_UPPER_LIMIT = -1;
   java.lang.String changeAttr2;
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public java.lang.String getChangeAttr2() {
      return changeAttr2;
   }
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public void setChangeAttr2(java.lang.String changeAttr2) throws wt.util.WTPropertyVetoException {
      changeAttr2Validate(changeAttr2);
      this.changeAttr2 = changeAttr2;
   }
   void changeAttr2Validate(java.lang.String changeAttr2) throws wt.util.WTPropertyVetoException {
      if (CHANGE_ATTR2_UPPER_LIMIT < 1) {
         try { CHANGE_ATTR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeAttr2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_ATTR2_UPPER_LIMIT = 200; }
      }
      if (changeAttr2 != null && !wt.fc.PersistenceHelper.checkStoredLength(changeAttr2.toString(), CHANGE_ATTR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeAttr2"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_ATTR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeAttr2", this.changeAttr2, changeAttr2));
   }

   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public static final java.lang.String TRY_SCHDULE = "trySchdule";
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public static final java.lang.String TRY_SCHDULE_REFERENCE = "trySchduleReference";
   wt.fc.ObjectReference trySchduleReference;
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public e3ps.project.trySchdule.TrySchdule getTrySchdule() {
      return (trySchduleReference != null) ? (e3ps.project.trySchdule.TrySchdule) trySchduleReference.getObject() : null;
   }
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public wt.fc.ObjectReference getTrySchduleReference() {
      return trySchduleReference;
   }
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public void setTrySchdule(e3ps.project.trySchdule.TrySchdule the_trySchdule) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTrySchduleReference(the_trySchdule == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.trySchdule.TrySchdule) the_trySchdule));
   }
   /**
    * @see e3ps.project.trySchdule.TryChange
    */
   public void setTrySchduleReference(wt.fc.ObjectReference the_trySchduleReference) throws wt.util.WTPropertyVetoException {
      trySchduleReferenceValidate(the_trySchduleReference);
      trySchduleReference = (wt.fc.ObjectReference) the_trySchduleReference;
   }
   void trySchduleReferenceValidate(wt.fc.ObjectReference the_trySchduleReference) throws wt.util.WTPropertyVetoException {
      if (the_trySchduleReference == null || the_trySchduleReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "trySchduleReference") },
               new java.beans.PropertyChangeEvent(this, "trySchduleReference", this.trySchduleReference, trySchduleReference));
      if (the_trySchduleReference != null && the_trySchduleReference.getReferencedClass() != null &&
            !e3ps.project.trySchdule.TrySchdule.class.isAssignableFrom(the_trySchduleReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "trySchduleReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "trySchduleReference", this.trySchduleReference, trySchduleReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -1860043474881621702L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( changeAttr1 );
      output.writeObject( changeAttr2 );
      output.writeObject( changeDate );
      output.writeObject( changeDetil );
      output.writeObject( owner );
      output.writeObject( thePersistInfo );
      output.writeObject( trySchduleReference );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.trySchdule.TryChange) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "changeAttr1", changeAttr1 );
      output.setString( "changeAttr2", changeAttr2 );
      output.setTimestamp( "changeDate", changeDate );
      output.setString( "changeDetil", changeDetil );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.writeObject( "trySchduleReference", trySchduleReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      changeAttr1 = input.getString( "changeAttr1" );
      changeAttr2 = input.getString( "changeAttr2" );
      changeDate = input.getTimestamp( "changeDate" );
      changeDetil = input.getString( "changeDetil" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      trySchduleReference = (wt.fc.ObjectReference) input.readObject( "trySchduleReference", trySchduleReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion_1860043474881621702L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      changeAttr1 = (java.lang.String) input.readObject();
      changeAttr2 = (java.lang.String) input.readObject();
      changeDate = (java.sql.Timestamp) input.readObject();
      changeDetil = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      trySchduleReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( TryChange thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1860043474881621702L( input, readSerialVersionUID, superDone );
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
