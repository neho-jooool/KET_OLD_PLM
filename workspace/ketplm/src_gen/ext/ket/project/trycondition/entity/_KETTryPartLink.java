package ext.ket.project.trycondition.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETTryPartLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.trycondition.entity.entityResource";
   static final java.lang.String CLASSNAME = KETTryPartLink.class.getName();

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
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
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
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
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String TOTAL_WEIGHT = "totalWeight";
   static int TOTAL_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String totalWeight;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public java.lang.String getTotalWeight() {
      return totalWeight;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public void setTotalWeight(java.lang.String totalWeight) throws wt.util.WTPropertyVetoException {
      totalWeightValidate(totalWeight);
      this.totalWeight = totalWeight;
   }
   void totalWeightValidate(java.lang.String totalWeight) throws wt.util.WTPropertyVetoException {
      if (TOTAL_WEIGHT_UPPER_LIMIT < 1) {
         try { TOTAL_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("totalWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOTAL_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (totalWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(totalWeight.toString(), TOTAL_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "totalWeight"), java.lang.String.valueOf(java.lang.Math.min(TOTAL_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "totalWeight", this.totalWeight, totalWeight));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String SCRAB_WEIGHT = "scrabWeight";
   static int SCRAB_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String scrabWeight;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public java.lang.String getScrabWeight() {
      return scrabWeight;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public void setScrabWeight(java.lang.String scrabWeight) throws wt.util.WTPropertyVetoException {
      scrabWeightValidate(scrabWeight);
      this.scrabWeight = scrabWeight;
   }
   void scrabWeightValidate(java.lang.String scrabWeight) throws wt.util.WTPropertyVetoException {
      if (SCRAB_WEIGHT_UPPER_LIMIT < 1) {
         try { SCRAB_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrabWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAB_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (scrabWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(scrabWeight.toString(), SCRAB_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrabWeight"), java.lang.String.valueOf(java.lang.Math.min(SCRAB_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrabWeight", this.scrabWeight, scrabWeight));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String SCRAB_CARRIER_WEIGHT = "scrabCarrierWeight";
   static int SCRAB_CARRIER_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String scrabCarrierWeight;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public java.lang.String getScrabCarrierWeight() {
      return scrabCarrierWeight;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public void setScrabCarrierWeight(java.lang.String scrabCarrierWeight) throws wt.util.WTPropertyVetoException {
      scrabCarrierWeightValidate(scrabCarrierWeight);
      this.scrabCarrierWeight = scrabCarrierWeight;
   }
   void scrabCarrierWeightValidate(java.lang.String scrabCarrierWeight) throws wt.util.WTPropertyVetoException {
      if (SCRAB_CARRIER_WEIGHT_UPPER_LIMIT < 1) {
         try { SCRAB_CARRIER_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrabCarrierWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAB_CARRIER_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (scrabCarrierWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(scrabCarrierWeight.toString(), SCRAB_CARRIER_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrabCarrierWeight"), java.lang.String.valueOf(java.lang.Math.min(SCRAB_CARRIER_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrabCarrierWeight", this.scrabCarrierWeight, scrabCarrierWeight));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String NET_WEIGHT = "netWeight";
   static int NET_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String netWeight;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public java.lang.String getNetWeight() {
      return netWeight;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public void setNetWeight(java.lang.String netWeight) throws wt.util.WTPropertyVetoException {
      netWeightValidate(netWeight);
      this.netWeight = netWeight;
   }
   void netWeightValidate(java.lang.String netWeight) throws wt.util.WTPropertyVetoException {
      if (NET_WEIGHT_UPPER_LIMIT < 1) {
         try { NET_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("netWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NET_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (netWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(netWeight.toString(), NET_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "netWeight"), java.lang.String.valueOf(java.lang.Math.min(NET_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "netWeight", this.netWeight, netWeight));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String TRY_MASTER_ROLE = "tryMaster";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public ext.ket.project.trycondition.entity.KETTryCondition getTryMaster() {
      return (ext.ket.project.trycondition.entity.KETTryCondition) getRoleAObject();
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public void setTryMaster(ext.ket.project.trycondition.entity.KETTryCondition the_tryMaster) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_tryMaster);
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public static final java.lang.String PART_MASTER_ROLE = "partMaster";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public wt.part.WTPartMaster getPartMaster() {
      return (wt.part.WTPartMaster) getRoleBObject();
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPartLink
    */
   public void setPartMaster(wt.part.WTPartMaster the_partMaster) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_partMaster);
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

   public static final long EXTERNALIZATION_VERSION_UID = -2658239628131859855L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( netWeight );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( scrabCarrierWeight );
      output.writeObject( scrabWeight );
      output.writeObject( totalWeight );
   }

   protected void super_writeExternal_KETTryPartLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.trycondition.entity.KETTryPartLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETTryPartLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "netWeight", netWeight );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "scrabCarrierWeight", scrabCarrierWeight );
      output.setString( "scrabWeight", scrabWeight );
      output.setString( "totalWeight", totalWeight );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      netWeight = input.getString( "netWeight" );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      scrabCarrierWeight = input.getString( "scrabCarrierWeight" );
      scrabWeight = input.getString( "scrabWeight" );
      totalWeight = input.getString( "totalWeight" );
   }

   boolean readVersion_2658239628131859855L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      netWeight = (java.lang.String) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      scrabCarrierWeight = (java.lang.String) input.readObject();
      scrabWeight = (java.lang.String) input.readObject();
      totalWeight = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETTryPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2658239628131859855L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETTryPartLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETTryPartLink( _KETTryPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
