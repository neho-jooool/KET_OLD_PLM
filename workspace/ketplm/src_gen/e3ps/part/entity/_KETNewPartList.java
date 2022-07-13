package e3ps.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETNewPartList extends wt.enterprise.Simple implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETNewPartList.class.getName();

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String PART_NUMBER = "partNumber";
   static int PART_NUMBER_UPPER_LIMIT = -1;
   java.lang.String partNumber;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.String getPartNumber() {
      return partNumber;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setPartNumber(java.lang.String partNumber) throws wt.util.WTPropertyVetoException {
      partNumberValidate(partNumber);
      this.partNumber = partNumber;
   }
   void partNumberValidate(java.lang.String partNumber) throws wt.util.WTPropertyVetoException {
      if (partNumber == null || partNumber.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNumber") },
               new java.beans.PropertyChangeEvent(this, "partNumber", this.partNumber, partNumber));
      if (PART_NUMBER_UPPER_LIMIT < 1) {
         try { PART_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NUMBER_UPPER_LIMIT = 200; }
      }
      if (partNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(partNumber.toString(), PART_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNumber"), java.lang.String.valueOf(java.lang.Math.min(PART_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNumber", this.partNumber, partNumber));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
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
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String RAW_MATERIAL = "rawMaterial";
   static int RAW_MATERIAL_UPPER_LIMIT = -1;
   java.lang.String rawMaterial;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.String getRawMaterial() {
      return rawMaterial;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setRawMaterial(java.lang.String rawMaterial) throws wt.util.WTPropertyVetoException {
      rawMaterialValidate(rawMaterial);
      this.rawMaterial = rawMaterial;
   }
   void rawMaterialValidate(java.lang.String rawMaterial) throws wt.util.WTPropertyVetoException {
      if (RAW_MATERIAL_UPPER_LIMIT < 1) {
         try { RAW_MATERIAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rawMaterial").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RAW_MATERIAL_UPPER_LIMIT = 200; }
      }
      if (rawMaterial != null && !wt.fc.PersistenceHelper.checkStoredLength(rawMaterial.toString(), RAW_MATERIAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rawMaterial"), java.lang.String.valueOf(java.lang.Math.min(RAW_MATERIAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rawMaterial", this.rawMaterial, rawMaterial));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String CUSTOMER = "customer";
   static int CUSTOMER_UPPER_LIMIT = -1;
   java.lang.String customer;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.String getCustomer() {
      return customer;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setCustomer(java.lang.String customer) throws wt.util.WTPropertyVetoException {
      customerValidate(customer);
      this.customer = customer;
   }
   void customerValidate(java.lang.String customer) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_UPPER_LIMIT < 1) {
         try { CUSTOMER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customer").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_UPPER_LIMIT = 200; }
      }
      if (customer != null && !wt.fc.PersistenceHelper.checkStoredLength(customer.toString(), CUSTOMER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customer"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customer", this.customer, customer));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String REGISTER = "register";
   static int REGISTER_UPPER_LIMIT = -1;
   java.lang.String register;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.String getRegister() {
      return register;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setRegister(java.lang.String register) throws wt.util.WTPropertyVetoException {
      registerValidate(register);
      this.register = register;
   }
   void registerValidate(java.lang.String register) throws wt.util.WTPropertyVetoException {
      if (REGISTER_UPPER_LIMIT < 1) {
         try { REGISTER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("register").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REGISTER_UPPER_LIMIT = 200; }
      }
      if (register != null && !wt.fc.PersistenceHelper.checkStoredLength(register.toString(), REGISTER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "register"), java.lang.String.valueOf(java.lang.Math.min(REGISTER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "register", this.register, register));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String REG_DATE = "regDate";
   java.sql.Date regDate;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.sql.Date getRegDate() {
      return regDate;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setRegDate(java.sql.Date regDate) throws wt.util.WTPropertyVetoException {
      regDateValidate(regDate);
      this.regDate = regDate;
   }
   void regDateValidate(java.sql.Date regDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String MODIFIER = "modifier";
   static int MODIFIER_UPPER_LIMIT = -1;
   java.lang.String modifier;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.String getModifier() {
      return modifier;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setModifier(java.lang.String modifier) throws wt.util.WTPropertyVetoException {
      modifierValidate(modifier);
      this.modifier = modifier;
   }
   void modifierValidate(java.lang.String modifier) throws wt.util.WTPropertyVetoException {
      if (MODIFIER_UPPER_LIMIT < 1) {
         try { MODIFIER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modifier").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODIFIER_UPPER_LIMIT = 200; }
      }
      if (modifier != null && !wt.fc.PersistenceHelper.checkStoredLength(modifier.toString(), MODIFIER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifier"), java.lang.String.valueOf(java.lang.Math.min(MODIFIER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modifier", this.modifier, modifier));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String MOD_DATE = "modDate";
   java.sql.Date modDate;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.sql.Date getModDate() {
      return modDate;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setModDate(java.sql.Date modDate) throws wt.util.WTPropertyVetoException {
      modDateValidate(modDate);
      this.modDate = modDate;
   }
   void modDateValidate(java.sql.Date modDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setDescription(java.lang.String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(java.lang.String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 200; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), java.lang.String.valueOf(java.lang.Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String DEL = "del";
   java.lang.Boolean del;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public java.lang.Boolean getDel() {
      return del;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setDel(java.lang.Boolean del) throws wt.util.WTPropertyVetoException {
      delValidate(del);
      this.del = del;
   }
   void delValidate(java.lang.Boolean del) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String NEWPARTTYPE = "newparttype";
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String NEWPARTTYPE_REFERENCE = "newparttypeReference";
   wt.fc.ObjectReference newparttypeReference;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public e3ps.common.code.NumberCode getNewparttype() {
      return (newparttypeReference != null) ? (e3ps.common.code.NumberCode) newparttypeReference.getObject() : null;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public wt.fc.ObjectReference getNewparttypeReference() {
      return newparttypeReference;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setNewparttype(e3ps.common.code.NumberCode the_newparttype) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setNewparttypeReference(the_newparttype == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_newparttype));
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setNewparttypeReference(wt.fc.ObjectReference the_newparttypeReference) throws wt.util.WTPropertyVetoException {
      newparttypeReferenceValidate(the_newparttypeReference);
      newparttypeReference = (wt.fc.ObjectReference) the_newparttypeReference;
   }
   void newparttypeReferenceValidate(wt.fc.ObjectReference the_newparttypeReference) throws wt.util.WTPropertyVetoException {
      if (the_newparttypeReference != null && the_newparttypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_newparttypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "newparttypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "newparttypeReference", this.newparttypeReference, newparttypeReference));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String NEWPRODUCT = "newproduct";
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String NEWPRODUCT_REFERENCE = "newproductReference";
   wt.fc.ObjectReference newproductReference;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public e3ps.common.code.NumberCode getNewproduct() {
      return (newproductReference != null) ? (e3ps.common.code.NumberCode) newproductReference.getObject() : null;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public wt.fc.ObjectReference getNewproductReference() {
      return newproductReference;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setNewproduct(e3ps.common.code.NumberCode the_newproduct) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setNewproductReference(the_newproduct == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_newproduct));
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setNewproductReference(wt.fc.ObjectReference the_newproductReference) throws wt.util.WTPropertyVetoException {
      newproductReferenceValidate(the_newproductReference);
      newproductReference = (wt.fc.ObjectReference) the_newproductReference;
   }
   void newproductReferenceValidate(wt.fc.ObjectReference the_newproductReference) throws wt.util.WTPropertyVetoException {
      if (the_newproductReference != null && the_newproductReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_newproductReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "newproductReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "newproductReference", this.newproductReference, newproductReference));
   }

   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String NEWDIE = "newdie";
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public static final java.lang.String NEWDIE_REFERENCE = "newdieReference";
   wt.fc.ObjectReference newdieReference;
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public e3ps.common.code.NumberCode getNewdie() {
      return (newdieReference != null) ? (e3ps.common.code.NumberCode) newdieReference.getObject() : null;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public wt.fc.ObjectReference getNewdieReference() {
      return newdieReference;
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setNewdie(e3ps.common.code.NumberCode the_newdie) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setNewdieReference(the_newdie == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_newdie));
   }
   /**
    * @see e3ps.part.entity.KETNewPartList
    */
   public void setNewdieReference(wt.fc.ObjectReference the_newdieReference) throws wt.util.WTPropertyVetoException {
      newdieReferenceValidate(the_newdieReference);
      newdieReference = (wt.fc.ObjectReference) the_newdieReference;
   }
   void newdieReferenceValidate(wt.fc.ObjectReference the_newdieReference) throws wt.util.WTPropertyVetoException {
      if (the_newdieReference != null && the_newdieReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_newdieReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "newdieReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "newdieReference", this.newdieReference, newdieReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -6335331211729209198L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( customer );
      output.writeObject( del );
      output.writeObject( description );
      output.writeObject( modDate );
      output.writeObject( modifier );
      output.writeObject( newdieReference );
      output.writeObject( newparttypeReference );
      output.writeObject( newproductReference );
      output.writeObject( partName );
      output.writeObject( partNumber );
      output.writeObject( rawMaterial );
      output.writeObject( regDate );
      output.writeObject( register );
   }

   protected void super_writeExternal_KETNewPartList(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.part.entity.KETNewPartList) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETNewPartList(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "customer", customer );
      output.setBooleanObject( "del", del );
      output.setString( "description", description );
      output.setDate( "modDate", modDate );
      output.setString( "modifier", modifier );
      output.writeObject( "newdieReference", newdieReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "newparttypeReference", newparttypeReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "newproductReference", newproductReference, wt.fc.ObjectReference.class, true );
      output.setString( "partName", partName );
      output.setString( "partNumber", partNumber );
      output.setString( "rawMaterial", rawMaterial );
      output.setDate( "regDate", regDate );
      output.setString( "register", register );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      customer = input.getString( "customer" );
      del = input.getBooleanObject( "del" );
      description = input.getString( "description" );
      modDate = input.getDate( "modDate" );
      modifier = input.getString( "modifier" );
      newdieReference = (wt.fc.ObjectReference) input.readObject( "newdieReference", newdieReference, wt.fc.ObjectReference.class, true );
      newparttypeReference = (wt.fc.ObjectReference) input.readObject( "newparttypeReference", newparttypeReference, wt.fc.ObjectReference.class, true );
      newproductReference = (wt.fc.ObjectReference) input.readObject( "newproductReference", newproductReference, wt.fc.ObjectReference.class, true );
      partName = input.getString( "partName" );
      partNumber = input.getString( "partNumber" );
      rawMaterial = input.getString( "rawMaterial" );
      regDate = input.getDate( "regDate" );
      register = input.getString( "register" );
   }

   boolean readVersion_6335331211729209198L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      customer = (java.lang.String) input.readObject();
      del = (java.lang.Boolean) input.readObject();
      description = (java.lang.String) input.readObject();
      modDate = (java.sql.Date) input.readObject();
      modifier = (java.lang.String) input.readObject();
      newdieReference = (wt.fc.ObjectReference) input.readObject();
      newparttypeReference = (wt.fc.ObjectReference) input.readObject();
      newproductReference = (wt.fc.ObjectReference) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNumber = (java.lang.String) input.readObject();
      rawMaterial = (java.lang.String) input.readObject();
      regDate = (java.sql.Date) input.readObject();
      register = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETNewPartList thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6335331211729209198L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETNewPartList( _KETNewPartList thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
