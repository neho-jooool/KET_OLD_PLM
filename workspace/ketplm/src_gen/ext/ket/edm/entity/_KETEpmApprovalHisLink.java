package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETEpmApprovalHisLink extends wt.vc.ObjectToVersionLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETEpmApprovalHisLink.class.getName();

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHisLink
    */
   public static final java.lang.String APPROVAL_HIS_ROLE = "approvalHis";
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHisLink
    */
   public ext.ket.edm.entity.KETEpmApprovalHis getApprovalHis() {
      return (ext.ket.edm.entity.KETEpmApprovalHis) getRoleAObject();
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHisLink
    */
   public void setApprovalHis(ext.ket.edm.entity.KETEpmApprovalHis the_approvalHis) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_approvalHis);
   }

   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHisLink
    */
   public static final java.lang.String EPM_DOC_ROLE = "epmDoc";
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHisLink
    */
   public wt.epm.EPMDocument getEpmDoc() {
      return (wt.epm.EPMDocument) getRoleBObject();
   }
   /**
    * @see ext.ket.edm.entity.KETEpmApprovalHisLink
    */
   public void setEpmDoc(wt.epm.EPMDocument the_epmDoc) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_epmDoc);
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

   public static final long EXTERNALIZATION_VERSION_UID = -3060132777145846922L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

   }

   protected void super_writeExternal_KETEpmApprovalHisLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETEpmApprovalHisLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETEpmApprovalHisLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

   }

   boolean readVersion_3060132777145846922L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      return true;
   }

   protected boolean readVersion( KETEpmApprovalHisLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3060132777145846922L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETEpmApprovalHisLink( _KETEpmApprovalHisLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
