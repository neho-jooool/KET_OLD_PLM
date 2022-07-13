package ext.ket.material.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartMaterialLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.material.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartMaterialLink.class.getName();

   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public void setPartNo(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      partNoValidate(partNo);
      this.partNo = partNo;
   }
   void partNoValidate(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      if (PART_NO_UPPER_LIMIT < 1) {
         try { PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NO_UPPER_LIMIT = 200; }
      }
      if (partNo != null && !wt.fc.PersistenceHelper.checkStoredLength(partNo.toString(), PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNo"), java.lang.String.valueOf(java.lang.Math.min(PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNo", this.partNo, partNo));
   }

   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public void setPartName(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      partNameValidate(partName);
      this.partName = partName;
   }
   void partNameValidate(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      if (PART_NAME_UPPER_LIMIT < 1) {
         try { PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NAME_UPPER_LIMIT = 200; }
      }
      if (partName != null && !wt.fc.PersistenceHelper.checkStoredLength(partName.toString(), PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partName"), java.lang.String.valueOf(java.lang.Math.min(PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partName", this.partName, partName));
   }

   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public static final java.lang.String PART_TYPE = "partType";
   static int PART_TYPE_UPPER_LIMIT = -1;
   java.lang.String partType;
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public java.lang.String getPartType() {
      return partType;
   }
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public void setPartType(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      partTypeValidate(partType);
      this.partType = partType;
   }
   void partTypeValidate(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      if (PART_TYPE_UPPER_LIMIT < 1) {
         try { PART_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_TYPE_UPPER_LIMIT = 200; }
      }
      if (partType != null && !wt.fc.PersistenceHelper.checkStoredLength(partType.toString(), PART_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partType"), java.lang.String.valueOf(java.lang.Math.min(PART_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partType", this.partType, partType));
   }

   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public static final java.lang.String PRICE = "price";
   static int PRICE_UPPER_LIMIT = -1;
   java.lang.String price;
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public java.lang.String getPrice() {
      return price;
   }
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public void setPrice(java.lang.String price) throws wt.util.WTPropertyVetoException {
      priceValidate(price);
      this.price = price;
   }
   void priceValidate(java.lang.String price) throws wt.util.WTPropertyVetoException {
      if (PRICE_UPPER_LIMIT < 1) {
         try { PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("price").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRICE_UPPER_LIMIT = 200; }
      }
      if (price != null && !wt.fc.PersistenceHelper.checkStoredLength(price.toString(), PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "price"), java.lang.String.valueOf(java.lang.Math.min(PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "price", this.price, price));
   }

   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public static final java.lang.String MATERIAL_ROLE = "material";
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public e3ps.project.material.MoldMaterial getMaterial() {
      return (e3ps.project.material.MoldMaterial) getRoleAObject();
   }
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public void setMaterial(e3ps.project.material.MoldMaterial the_material) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_material);
   }

   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public static final java.lang.String PART_ROLE = "part";
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public wt.part.WTPartMaster getPart() {
      return (wt.part.WTPartMaster) getRoleBObject();
   }
   /**
    * @see ext.ket.material.entity.KETPartMaterialLink
    */
   public void setPart(wt.part.WTPartMaster the_part) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_part);
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

   public static final long EXTERNALIZATION_VERSION_UID = -2666812628307683241L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( partType );
      output.writeObject( price );
   }

   protected void super_writeExternal_KETPartMaterialLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.material.entity.KETPartMaterialLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETPartMaterialLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "partType", partType );
      output.setString( "price", price );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      partType = input.getString( "partType" );
      price = input.getString( "price" );
   }

   boolean readVersion_2666812628307683241L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      partType = (java.lang.String) input.readObject();
      price = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETPartMaterialLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2666812628307683241L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETPartMaterialLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETPartMaterialLink( _KETPartMaterialLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
