package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETEcnWeightHistory implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETEcnWeightHistory.class.getName();

   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public static final java.lang.String SP_TOTAL_WEIGHT = "spTotalWeight";
   static int SP_TOTAL_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String spTotalWeight;
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public java.lang.String getSpTotalWeight() {
      return spTotalWeight;
   }
   /**
    * ?????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public void setSpTotalWeight(java.lang.String spTotalWeight) throws wt.util.WTPropertyVetoException {
      spTotalWeightValidate(spTotalWeight);
      this.spTotalWeight = spTotalWeight;
   }
   void spTotalWeightValidate(java.lang.String spTotalWeight) throws wt.util.WTPropertyVetoException {
      if (SP_TOTAL_WEIGHT_UPPER_LIMIT < 1) {
         try { SP_TOTAL_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("spTotalWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SP_TOTAL_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (spTotalWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(spTotalWeight.toString(), SP_TOTAL_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "spTotalWeight"), java.lang.String.valueOf(java.lang.Math.min(SP_TOTAL_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "spTotalWeight", this.spTotalWeight, spTotalWeight));
   }

   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public static final java.lang.String SP_NET_WEIGHT = "spNetWeight";
   static int SP_NET_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String spNetWeight;
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public java.lang.String getSpNetWeight() {
      return spNetWeight;
   }
   /**
    * ??????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public void setSpNetWeight(java.lang.String spNetWeight) throws wt.util.WTPropertyVetoException {
      spNetWeightValidate(spNetWeight);
      this.spNetWeight = spNetWeight;
   }
   void spNetWeightValidate(java.lang.String spNetWeight) throws wt.util.WTPropertyVetoException {
      if (SP_NET_WEIGHT_UPPER_LIMIT < 1) {
         try { SP_NET_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("spNetWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SP_NET_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (spNetWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(spNetWeight.toString(), SP_NET_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "spNetWeight"), java.lang.String.valueOf(java.lang.Math.min(SP_NET_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "spNetWeight", this.spNetWeight, spNetWeight));
   }

   /**
    * ????????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public static final java.lang.String SP_SCRAB_WEIGHT = "spScrabWeight";
   static int SP_SCRAB_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String spScrabWeight;
   /**
    * ????????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public java.lang.String getSpScrabWeight() {
      return spScrabWeight;
   }
   /**
    * ????????
    *
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public void setSpScrabWeight(java.lang.String spScrabWeight) throws wt.util.WTPropertyVetoException {
      spScrabWeightValidate(spScrabWeight);
      this.spScrabWeight = spScrabWeight;
   }
   void spScrabWeightValidate(java.lang.String spScrabWeight) throws wt.util.WTPropertyVetoException {
      if (SP_SCRAB_WEIGHT_UPPER_LIMIT < 1) {
         try { SP_SCRAB_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("spScrabWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SP_SCRAB_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (spScrabWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(spScrabWeight.toString(), SP_SCRAB_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "spScrabWeight"), java.lang.String.valueOf(java.lang.Math.min(SP_SCRAB_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "spScrabWeight", this.spScrabWeight, spScrabWeight));
   }

   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public static final java.lang.String ECO = "eco";
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public static final java.lang.String ECO_REFERENCE = "ecoReference";
   wt.fc.ObjectReference ecoReference;
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public wt.change2.WTChangeOrder2 getEco() {
      return (ecoReference != null) ? (wt.change2.WTChangeOrder2) ecoReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public wt.fc.ObjectReference getEcoReference() {
      return ecoReference;
   }
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public void setEco(wt.change2.WTChangeOrder2 the_eco) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setEcoReference(the_eco == null ? null : wt.fc.ObjectReference.newObjectReference((wt.change2.WTChangeOrder2) the_eco));
   }
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public void setEcoReference(wt.fc.ObjectReference the_ecoReference) throws wt.util.WTPropertyVetoException {
      ecoReferenceValidate(the_ecoReference);
      ecoReference = (wt.fc.ObjectReference) the_ecoReference;
   }
   void ecoReferenceValidate(wt.fc.ObjectReference the_ecoReference) throws wt.util.WTPropertyVetoException {
      if (the_ecoReference != null && the_ecoReference.getReferencedClass() != null &&
            !wt.change2.WTChangeOrder2.class.isAssignableFrom(the_ecoReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "ecoReference", this.ecoReference, ecoReference));
   }

   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public static final java.lang.String PART = "part";
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public static final java.lang.String PART_REFERENCE = "partReference";
   wt.fc.ObjectReference partReference;
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public wt.part.WTPart getPart() {
      return (partReference != null) ? (wt.part.WTPart) partReference.getObject() : null;
   }
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public wt.fc.ObjectReference getPartReference() {
      return partReference;
   }
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public void setPart(wt.part.WTPart the_part) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartReference(the_part == null ? null : wt.fc.ObjectReference.newObjectReference((wt.part.WTPart) the_part));
   }
   /**
    * @see e3ps.ecm.entity.KETEcnWeightHistory
    */
   public void setPartReference(wt.fc.ObjectReference the_partReference) throws wt.util.WTPropertyVetoException {
      partReferenceValidate(the_partReference);
      partReference = (wt.fc.ObjectReference) the_partReference;
   }
   void partReferenceValidate(wt.fc.ObjectReference the_partReference) throws wt.util.WTPropertyVetoException {
      if (the_partReference != null && the_partReference.getReferencedClass() != null &&
            !wt.part.WTPart.class.isAssignableFrom(the_partReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "partReference", this.partReference, partReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -3351850416725065494L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( ecoReference );
      output.writeObject( partReference );
      output.writeObject( spNetWeight );
      output.writeObject( spScrabWeight );
      output.writeObject( spTotalWeight );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETEcnWeightHistory) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "ecoReference", ecoReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      output.setString( "spNetWeight", spNetWeight );
      output.setString( "spScrabWeight", spScrabWeight );
      output.setString( "spTotalWeight", spTotalWeight );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      ecoReference = (wt.fc.ObjectReference) input.readObject( "ecoReference", ecoReference, wt.fc.ObjectReference.class, true );
      partReference = (wt.fc.ObjectReference) input.readObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      spNetWeight = input.getString( "spNetWeight" );
      spScrabWeight = input.getString( "spScrabWeight" );
      spTotalWeight = input.getString( "spTotalWeight" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_3351850416725065494L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      ecoReference = (wt.fc.ObjectReference) input.readObject();
      partReference = (wt.fc.ObjectReference) input.readObject();
      spNetWeight = (java.lang.String) input.readObject();
      spScrabWeight = (java.lang.String) input.readObject();
      spTotalWeight = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETEcnWeightHistory thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3351850416725065494L( input, readSerialVersionUID, superDone );
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
