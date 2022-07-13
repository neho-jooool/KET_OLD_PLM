package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostReduceCode implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostReduceCode.class.getName();

   /**
    * 금형-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String MOLD_TARIFF_PRICE = "moldTariffPrice";
   static int MOLD_TARIFF_PRICE_UPPER_LIMIT = -1;
   java.lang.String moldTariffPrice;
   /**
    * 금형-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.String getMoldTariffPrice() {
      return moldTariffPrice;
   }
   /**
    * 금형-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setMoldTariffPrice(java.lang.String moldTariffPrice) throws wt.util.WTPropertyVetoException {
      moldTariffPriceValidate(moldTariffPrice);
      this.moldTariffPrice = moldTariffPrice;
   }
   void moldTariffPriceValidate(java.lang.String moldTariffPrice) throws wt.util.WTPropertyVetoException {
      if (MOLD_TARIFF_PRICE_UPPER_LIMIT < 1) {
         try { MOLD_TARIFF_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldTariffPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_TARIFF_PRICE_UPPER_LIMIT = 200; }
      }
      if (moldTariffPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(moldTariffPrice.toString(), MOLD_TARIFF_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldTariffPrice"), java.lang.String.valueOf(java.lang.Math.min(MOLD_TARIFF_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldTariffPrice", this.moldTariffPrice, moldTariffPrice));
   }

   /**
    * 설비-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String FAC_TARIFF_PRICE = "facTariffPrice";
   static int FAC_TARIFF_PRICE_UPPER_LIMIT = -1;
   java.lang.String facTariffPrice;
   /**
    * 설비-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.String getFacTariffPrice() {
      return facTariffPrice;
   }
   /**
    * 설비-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setFacTariffPrice(java.lang.String facTariffPrice) throws wt.util.WTPropertyVetoException {
      facTariffPriceValidate(facTariffPrice);
      this.facTariffPrice = facTariffPrice;
   }
   void facTariffPriceValidate(java.lang.String facTariffPrice) throws wt.util.WTPropertyVetoException {
      if (FAC_TARIFF_PRICE_UPPER_LIMIT < 1) {
         try { FAC_TARIFF_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facTariffPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_TARIFF_PRICE_UPPER_LIMIT = 200; }
      }
      if (facTariffPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(facTariffPrice.toString(), FAC_TARIFF_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facTariffPrice"), java.lang.String.valueOf(java.lang.Math.min(FAC_TARIFF_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facTariffPrice", this.facTariffPrice, facTariffPrice));
   }

   /**
    * 기타-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String ETC_TARIFF_PRICE = "etcTariffPrice";
   static int ETC_TARIFF_PRICE_UPPER_LIMIT = -1;
   java.lang.String etcTariffPrice;
   /**
    * 기타-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.String getEtcTariffPrice() {
      return etcTariffPrice;
   }
   /**
    * 기타-관세,물류비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setEtcTariffPrice(java.lang.String etcTariffPrice) throws wt.util.WTPropertyVetoException {
      etcTariffPriceValidate(etcTariffPrice);
      this.etcTariffPrice = etcTariffPrice;
   }
   void etcTariffPriceValidate(java.lang.String etcTariffPrice) throws wt.util.WTPropertyVetoException {
      if (ETC_TARIFF_PRICE_UPPER_LIMIT < 1) {
         try { ETC_TARIFF_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcTariffPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_TARIFF_PRICE_UPPER_LIMIT = 200; }
      }
      if (etcTariffPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(etcTariffPrice.toString(), ETC_TARIFF_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcTariffPrice"), java.lang.String.valueOf(java.lang.Math.min(ETC_TARIFF_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcTariffPrice", this.etcTariffPrice, etcTariffPrice));
   }

   /**
    * 금형관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String MOLD_MTN_EXPENCE = "moldMtnExpence";
   static int MOLD_MTN_EXPENCE_UPPER_LIMIT = -1;
   java.lang.String moldMtnExpence;
   /**
    * 금형관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.String getMoldMtnExpence() {
      return moldMtnExpence;
   }
   /**
    * 금형관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setMoldMtnExpence(java.lang.String moldMtnExpence) throws wt.util.WTPropertyVetoException {
      moldMtnExpenceValidate(moldMtnExpence);
      this.moldMtnExpence = moldMtnExpence;
   }
   void moldMtnExpenceValidate(java.lang.String moldMtnExpence) throws wt.util.WTPropertyVetoException {
      if (MOLD_MTN_EXPENCE_UPPER_LIMIT < 1) {
         try { MOLD_MTN_EXPENCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMtnExpence").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MTN_EXPENCE_UPPER_LIMIT = 200; }
      }
      if (moldMtnExpence != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMtnExpence.toString(), MOLD_MTN_EXPENCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMtnExpence"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MTN_EXPENCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMtnExpence", this.moldMtnExpence, moldMtnExpence));
   }

   /**
    * 설비관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String FAC_MTN_EXPENCE = "facMtnExpence";
   static int FAC_MTN_EXPENCE_UPPER_LIMIT = -1;
   java.lang.String facMtnExpence;
   /**
    * 설비관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.String getFacMtnExpence() {
      return facMtnExpence;
   }
   /**
    * 설비관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setFacMtnExpence(java.lang.String facMtnExpence) throws wt.util.WTPropertyVetoException {
      facMtnExpenceValidate(facMtnExpence);
      this.facMtnExpence = facMtnExpence;
   }
   void facMtnExpenceValidate(java.lang.String facMtnExpence) throws wt.util.WTPropertyVetoException {
      if (FAC_MTN_EXPENCE_UPPER_LIMIT < 1) {
         try { FAC_MTN_EXPENCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMtnExpence").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MTN_EXPENCE_UPPER_LIMIT = 200; }
      }
      if (facMtnExpence != null && !wt.fc.PersistenceHelper.checkStoredLength(facMtnExpence.toString(), FAC_MTN_EXPENCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMtnExpence"), java.lang.String.valueOf(java.lang.Math.min(FAC_MTN_EXPENCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMtnExpence", this.facMtnExpence, facMtnExpence));
   }

   /**
    * 기타관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String ETC_MTN_EXPENCE = "etcMtnExpence";
   static int ETC_MTN_EXPENCE_UPPER_LIMIT = -1;
   java.lang.String etcMtnExpence;
   /**
    * 기타관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.String getEtcMtnExpence() {
      return etcMtnExpence;
   }
   /**
    * 기타관리비
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setEtcMtnExpence(java.lang.String etcMtnExpence) throws wt.util.WTPropertyVetoException {
      etcMtnExpenceValidate(etcMtnExpence);
      this.etcMtnExpence = etcMtnExpence;
   }
   void etcMtnExpenceValidate(java.lang.String etcMtnExpence) throws wt.util.WTPropertyVetoException {
      if (ETC_MTN_EXPENCE_UPPER_LIMIT < 1) {
         try { ETC_MTN_EXPENCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcMtnExpence").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_MTN_EXPENCE_UPPER_LIMIT = 200; }
      }
      if (etcMtnExpence != null && !wt.fc.PersistenceHelper.checkStoredLength(etcMtnExpence.toString(), ETC_MTN_EXPENCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcMtnExpence"), java.lang.String.valueOf(java.lang.Math.min(ETC_MTN_EXPENCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcMtnExpence", this.etcMtnExpence, etcMtnExpence));
   }

   /**
    * 제작처 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String SORTING_ORDER1 = "sortingOrder1";
   java.lang.Integer sortingOrder1;
   /**
    * 제작처 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.Integer getSortingOrder1() {
      return sortingOrder1;
   }
   /**
    * 제작처 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setSortingOrder1(java.lang.Integer sortingOrder1) throws wt.util.WTPropertyVetoException {
      sortingOrder1Validate(sortingOrder1);
      this.sortingOrder1 = sortingOrder1;
   }
   void sortingOrder1Validate(java.lang.Integer sortingOrder1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String SORTING_ORDER2 = "sortingOrder2";
   java.lang.Integer sortingOrder2;
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public java.lang.Integer getSortingOrder2() {
      return sortingOrder2;
   }
   /**
    * 생산국 NumberCode의 Sort
    *
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setSortingOrder2(java.lang.Integer sortingOrder2) throws wt.util.WTPropertyVetoException {
      sortingOrder2Validate(sortingOrder2);
      this.sortingOrder2 = sortingOrder2;
   }
   void sortingOrder2Validate(java.lang.Integer sortingOrder2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String COST_MAKING = "costMaking";
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String COST_MAKING_REFERENCE = "costMakingReference";
   wt.fc.ObjectReference costMakingReference;
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public e3ps.common.code.NumberCode getCostMaking() {
      return (costMakingReference != null) ? (e3ps.common.code.NumberCode) costMakingReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public wt.fc.ObjectReference getCostMakingReference() {
      return costMakingReference;
   }
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setCostMaking(e3ps.common.code.NumberCode the_costMaking) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostMakingReference(the_costMaking == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_costMaking));
   }
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setCostMakingReference(wt.fc.ObjectReference the_costMakingReference) throws wt.util.WTPropertyVetoException {
      costMakingReferenceValidate(the_costMakingReference);
      costMakingReference = (wt.fc.ObjectReference) the_costMakingReference;
   }
   void costMakingReferenceValidate(wt.fc.ObjectReference the_costMakingReference) throws wt.util.WTPropertyVetoException {
      if (the_costMakingReference == null || the_costMakingReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costMakingReference") },
               new java.beans.PropertyChangeEvent(this, "costMakingReference", this.costMakingReference, costMakingReference));
      if (the_costMakingReference != null && the_costMakingReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_costMakingReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costMakingReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "costMakingReference", this.costMakingReference, costMakingReference));
   }

   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String FACTORY = "factory";
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public static final java.lang.String FACTORY_REFERENCE = "factoryReference";
   wt.fc.ObjectReference factoryReference;
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public e3ps.common.code.NumberCode getFactory() {
      return (factoryReference != null) ? (e3ps.common.code.NumberCode) factoryReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public wt.fc.ObjectReference getFactoryReference() {
      return factoryReference;
   }
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setFactory(e3ps.common.code.NumberCode the_factory) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFactoryReference(the_factory == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_factory));
   }
   /**
    * @see ext.ket.cost.entity.CostReduceCode
    */
   public void setFactoryReference(wt.fc.ObjectReference the_factoryReference) throws wt.util.WTPropertyVetoException {
      factoryReferenceValidate(the_factoryReference);
      factoryReference = (wt.fc.ObjectReference) the_factoryReference;
   }
   void factoryReferenceValidate(wt.fc.ObjectReference the_factoryReference) throws wt.util.WTPropertyVetoException {
      if (the_factoryReference == null || the_factoryReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "factoryReference") },
               new java.beans.PropertyChangeEvent(this, "factoryReference", this.factoryReference, factoryReference));
      if (the_factoryReference != null && the_factoryReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_factoryReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "factoryReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "factoryReference", this.factoryReference, factoryReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7186252785995055610L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( costMakingReference );
      output.writeObject( etcMtnExpence );
      output.writeObject( etcTariffPrice );
      output.writeObject( facMtnExpence );
      output.writeObject( facTariffPrice );
      output.writeObject( factoryReference );
      output.writeObject( moldMtnExpence );
      output.writeObject( moldTariffPrice );
      output.writeObject( owner );
      output.writeObject( sortingOrder1 );
      output.writeObject( sortingOrder2 );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostReduceCode) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "costMakingReference", costMakingReference, wt.fc.ObjectReference.class, true );
      output.setString( "etcMtnExpence", etcMtnExpence );
      output.setString( "etcTariffPrice", etcTariffPrice );
      output.setString( "facMtnExpence", facMtnExpence );
      output.setString( "facTariffPrice", facTariffPrice );
      output.writeObject( "factoryReference", factoryReference, wt.fc.ObjectReference.class, true );
      output.setString( "moldMtnExpence", moldMtnExpence );
      output.setString( "moldTariffPrice", moldTariffPrice );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setIntObject( "sortingOrder1", sortingOrder1 );
      output.setIntObject( "sortingOrder2", sortingOrder2 );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      costMakingReference = (wt.fc.ObjectReference) input.readObject( "costMakingReference", costMakingReference, wt.fc.ObjectReference.class, true );
      etcMtnExpence = input.getString( "etcMtnExpence" );
      etcTariffPrice = input.getString( "etcTariffPrice" );
      facMtnExpence = input.getString( "facMtnExpence" );
      facTariffPrice = input.getString( "facTariffPrice" );
      factoryReference = (wt.fc.ObjectReference) input.readObject( "factoryReference", factoryReference, wt.fc.ObjectReference.class, true );
      moldMtnExpence = input.getString( "moldMtnExpence" );
      moldTariffPrice = input.getString( "moldTariffPrice" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      sortingOrder1 = input.getIntObject( "sortingOrder1" );
      sortingOrder2 = input.getIntObject( "sortingOrder2" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion7186252785995055610L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      costMakingReference = (wt.fc.ObjectReference) input.readObject();
      etcMtnExpence = (java.lang.String) input.readObject();
      etcTariffPrice = (java.lang.String) input.readObject();
      facMtnExpence = (java.lang.String) input.readObject();
      facTariffPrice = (java.lang.String) input.readObject();
      factoryReference = (wt.fc.ObjectReference) input.readObject();
      moldMtnExpence = (java.lang.String) input.readObject();
      moldTariffPrice = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      sortingOrder1 = (java.lang.Integer) input.readObject();
      sortingOrder2 = (java.lang.Integer) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CostReduceCode thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7186252785995055610L( input, readSerialVersionUID, superDone );
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
