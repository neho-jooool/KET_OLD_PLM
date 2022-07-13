package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProductMaster implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = ProductMaster.class.getName();

   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public static final java.lang.String MASTER_OID = "masterOid";
   static int MASTER_OID_UPPER_LIMIT = -1;
   java.lang.String masterOid;
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public java.lang.String getMasterOid() {
      return masterOid;
   }
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public void setMasterOid(java.lang.String masterOid) throws wt.util.WTPropertyVetoException {
      masterOidValidate(masterOid);
      this.masterOid = masterOid;
   }
   void masterOidValidate(java.lang.String masterOid) throws wt.util.WTPropertyVetoException {
      if (MASTER_OID_UPPER_LIMIT < 1) {
         try { MASTER_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("masterOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MASTER_OID_UPPER_LIMIT = 200; }
      }
      if (masterOid != null && !wt.fc.PersistenceHelper.checkStoredLength(masterOid.toString(), MASTER_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterOid"), java.lang.String.valueOf(java.lang.Math.min(MASTER_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "masterOid", this.masterOid, masterOid));
   }

   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public static final java.lang.String PART_LIST = "partList";
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public static final java.lang.String PART_LIST_REFERENCE = "partListReference";
   wt.fc.ObjectReference partListReference;
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public ext.ket.cost.entity.PartList getPartList() {
      return (partListReference != null) ? (ext.ket.cost.entity.PartList) partListReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public wt.fc.ObjectReference getPartListReference() {
      return partListReference;
   }
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public void setPartList(ext.ket.cost.entity.PartList the_partList) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartListReference(the_partList == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.PartList) the_partList));
   }
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public void setPartListReference(wt.fc.ObjectReference the_partListReference) throws wt.util.WTPropertyVetoException {
      partListReferenceValidate(the_partListReference);
      partListReference = (wt.fc.ObjectReference) the_partListReference;
   }
   void partListReferenceValidate(wt.fc.ObjectReference the_partListReference) throws wt.util.WTPropertyVetoException {
      if (the_partListReference == null || the_partListReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partListReference") },
               new java.beans.PropertyChangeEvent(this, "partListReference", this.partListReference, partListReference));
      if (the_partListReference != null && the_partListReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.PartList.class.isAssignableFrom(the_partListReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partListReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "partListReference", this.partListReference, partListReference));
   }

   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public static final java.lang.String DELIVER_CODE = "deliverCode";
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public static final java.lang.String DELIVER_CODE_REFERENCE = "deliverCodeReference";
   wt.fc.ObjectReference deliverCodeReference;
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public e3ps.common.code.NumberCode getDeliverCode() {
      return (deliverCodeReference != null) ? (e3ps.common.code.NumberCode) deliverCodeReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public wt.fc.ObjectReference getDeliverCodeReference() {
      return deliverCodeReference;
   }
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public void setDeliverCode(e3ps.common.code.NumberCode the_deliverCode) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDeliverCodeReference(the_deliverCode == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_deliverCode));
   }
   /**
    * @see ext.ket.cost.entity.ProductMaster
    */
   public void setDeliverCodeReference(wt.fc.ObjectReference the_deliverCodeReference) throws wt.util.WTPropertyVetoException {
      deliverCodeReferenceValidate(the_deliverCodeReference);
      deliverCodeReference = (wt.fc.ObjectReference) the_deliverCodeReference;
   }
   void deliverCodeReferenceValidate(wt.fc.ObjectReference the_deliverCodeReference) throws wt.util.WTPropertyVetoException {
      if (the_deliverCodeReference != null && the_deliverCodeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_deliverCodeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deliverCodeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "deliverCodeReference", this.deliverCodeReference, deliverCodeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1243998840423145551L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( deliverCodeReference );
      output.writeObject( masterOid );
      output.writeObject( owner );
      output.writeObject( partListReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.ProductMaster) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "deliverCodeReference", deliverCodeReference, wt.fc.ObjectReference.class, true );
      output.setString( "masterOid", masterOid );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      deliverCodeReference = (wt.fc.ObjectReference) input.readObject( "deliverCodeReference", deliverCodeReference, wt.fc.ObjectReference.class, true );
      masterOid = input.getString( "masterOid" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partListReference = (wt.fc.ObjectReference) input.readObject( "partListReference", partListReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion1243998840423145551L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      deliverCodeReference = (wt.fc.ObjectReference) input.readObject();
      masterOid = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partListReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( ProductMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1243998840423145551L( input, readSerialVersionUID, superDone );
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
