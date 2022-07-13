package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETChangeActivityExpansion extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETChangeActivityExpansion.class.getName();

   /**
    * NumberCode?? ???(???), CodeType : SUBCONTRACTOR
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public static final java.lang.String SUB_CONTRACTOR_CODE = "subContractorCode";
   static int SUB_CONTRACTOR_CODE_UPPER_LIMIT = -1;
   java.lang.String subContractorCode;
   /**
    * NumberCode?? ???(???), CodeType : SUBCONTRACTOR
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public java.lang.String getSubContractorCode() {
      return subContractorCode;
   }
   /**
    * NumberCode?? ???(???), CodeType : SUBCONTRACTOR
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public void setSubContractorCode(java.lang.String subContractorCode) throws wt.util.WTPropertyVetoException {
      subContractorCodeValidate(subContractorCode);
      this.subContractorCode = subContractorCode;
   }
   void subContractorCodeValidate(java.lang.String subContractorCode) throws wt.util.WTPropertyVetoException {
      if (SUB_CONTRACTOR_CODE_UPPER_LIMIT < 1) {
         try { SUB_CONTRACTOR_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subContractorCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_CONTRACTOR_CODE_UPPER_LIMIT = 200; }
      }
      if (subContractorCode != null && !wt.fc.PersistenceHelper.checkStoredLength(subContractorCode.toString(), SUB_CONTRACTOR_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subContractorCode"), java.lang.String.valueOf(java.lang.Math.min(SUB_CONTRACTOR_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subContractorCode", this.subContractorCode, subContractorCode));
   }

   /**
    * ????????
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public static final java.lang.String SUB_CONTRACTOR_APPROVAL_DATE = "subContractorApprovalDate";
   java.sql.Timestamp subContractorApprovalDate;
   /**
    * ????????
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public java.sql.Timestamp getSubContractorApprovalDate() {
      return subContractorApprovalDate;
   }
   /**
    * ????????
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public void setSubContractorApprovalDate(java.sql.Timestamp subContractorApprovalDate) throws wt.util.WTPropertyVetoException {
      subContractorApprovalDateValidate(subContractorApprovalDate);
      this.subContractorApprovalDate = subContractorApprovalDate;
   }
   void subContractorApprovalDateValidate(java.sql.Timestamp subContractorApprovalDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * ERP ????????? ???? Flag
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public static final java.lang.String ERP_FLAG = "erpFlag";
   static int ERP_FLAG_UPPER_LIMIT = -1;
   java.lang.String erpFlag;
   /**
    * ERP ????????? ???? Flag
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public java.lang.String getErpFlag() {
      return erpFlag;
   }
   /**
    * ERP ????????? ???? Flag
    *
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public void setErpFlag(java.lang.String erpFlag) throws wt.util.WTPropertyVetoException {
      erpFlagValidate(erpFlag);
      this.erpFlag = erpFlag;
   }
   void erpFlagValidate(java.lang.String erpFlag) throws wt.util.WTPropertyVetoException {
      if (ERP_FLAG_UPPER_LIMIT < 1) {
         try { ERP_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("erpFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ERP_FLAG_UPPER_LIMIT = 200; }
      }
      if (erpFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(erpFlag.toString(), ERP_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "erpFlag"), java.lang.String.valueOf(java.lang.Math.min(ERP_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "erpFlag", this.erpFlag, erpFlag));
   }

   /**
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public static final java.lang.String SOME_ROLE = "some";
   /**
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public wt.fc.Persistable getSome() {
      return (wt.fc.Persistable) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public void setSome(wt.fc.Persistable the_some) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_some);
   }

   /**
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public static final java.lang.String ECA_ROLE = "eca";
   /**
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public wt.enterprise.Managed getEca() {
      return (wt.enterprise.Managed) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETChangeActivityExpansion
    */
   public void setEca(wt.enterprise.Managed the_eca) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_eca);
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

   public static final long EXTERNALIZATION_VERSION_UID = -4889013460978931449L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( erpFlag );
      output.writeObject( subContractorApprovalDate );
      output.writeObject( subContractorCode );
   }

   protected void super_writeExternal_KETChangeActivityExpansion(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETChangeActivityExpansion) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETChangeActivityExpansion(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "erpFlag", erpFlag );
      output.setTimestamp( "subContractorApprovalDate", subContractorApprovalDate );
      output.setString( "subContractorCode", subContractorCode );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      erpFlag = input.getString( "erpFlag" );
      subContractorApprovalDate = input.getTimestamp( "subContractorApprovalDate" );
      subContractorCode = input.getString( "subContractorCode" );
   }

   boolean readVersion_4889013460978931449L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      erpFlag = (java.lang.String) input.readObject();
      subContractorApprovalDate = (java.sql.Timestamp) input.readObject();
      subContractorCode = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETChangeActivityExpansion thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4889013460978931449L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETChangeActivityExpansion( _KETChangeActivityExpansion thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
