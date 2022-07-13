package ext.ket.issue.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETGeneralissueLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.issue.entity.entityResource";
   static final java.lang.String CLASSNAME = KETGeneralissueLink.class.getName();

   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public static final java.lang.String PBO_NO = "pboNo";
   static int PBO_NO_UPPER_LIMIT = -1;
   java.lang.String pboNo;
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public java.lang.String getPboNo() {
      return pboNo;
   }
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public void setPboNo(java.lang.String pboNo) throws wt.util.WTPropertyVetoException {
      pboNoValidate(pboNo);
      this.pboNo = pboNo;
   }
   void pboNoValidate(java.lang.String pboNo) throws wt.util.WTPropertyVetoException {
      if (PBO_NO_UPPER_LIMIT < 1) {
         try { PBO_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pboNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PBO_NO_UPPER_LIMIT = 200; }
      }
      if (pboNo != null && !wt.fc.PersistenceHelper.checkStoredLength(pboNo.toString(), PBO_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pboNo"), java.lang.String.valueOf(java.lang.Math.min(PBO_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pboNo", this.pboNo, pboNo));
   }

   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public static final java.lang.String PBO_NAME = "pboName";
   static int PBO_NAME_UPPER_LIMIT = -1;
   java.lang.String pboName;
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public java.lang.String getPboName() {
      return pboName;
   }
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public void setPboName(java.lang.String pboName) throws wt.util.WTPropertyVetoException {
      pboNameValidate(pboName);
      this.pboName = pboName;
   }
   void pboNameValidate(java.lang.String pboName) throws wt.util.WTPropertyVetoException {
      if (PBO_NAME_UPPER_LIMIT < 1) {
         try { PBO_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pboName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PBO_NAME_UPPER_LIMIT = 200; }
      }
      if (pboName != null && !wt.fc.PersistenceHelper.checkStoredLength(pboName.toString(), PBO_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pboName"), java.lang.String.valueOf(java.lang.Math.min(PBO_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pboName", this.pboName, pboName));
   }

   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public static final java.lang.String KETISSUE_MASTER_ROLE = "theKETIssueMaster";
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public ext.ket.issue.entity.KETIssueMaster getKETIssueMaster() {
      return (ext.ket.issue.entity.KETIssueMaster) getRoleAObject();
   }
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public void setKETIssueMaster(ext.ket.issue.entity.KETIssueMaster the_theKETIssueMaster) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_theKETIssueMaster);
   }

   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public static final java.lang.String PBO_ROLE = "pbo";
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public wt.fc.Persistable getPbo() {
      return (wt.fc.Persistable) getRoleBObject();
   }
   /**
    * @see ext.ket.issue.entity.KETGeneralissueLink
    */
   public void setPbo(wt.fc.Persistable the_pbo) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_pbo);
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

   public static final long EXTERNALIZATION_VERSION_UID = -8161165802063812022L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( pboName );
      output.writeObject( pboNo );
   }

   protected void super_writeExternal_KETGeneralissueLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.issue.entity.KETGeneralissueLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETGeneralissueLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "pboName", pboName );
      output.setString( "pboNo", pboNo );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      pboName = input.getString( "pboName" );
      pboNo = input.getString( "pboNo" );
   }

   boolean readVersion_8161165802063812022L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      pboName = (java.lang.String) input.readObject();
      pboNo = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETGeneralissueLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8161165802063812022L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETGeneralissueLink( _KETGeneralissueLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
