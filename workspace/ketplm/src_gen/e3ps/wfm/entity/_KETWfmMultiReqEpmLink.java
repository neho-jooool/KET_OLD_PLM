package e3ps.wfm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETWfmMultiReqEpmLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.wfm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETWfmMultiReqEpmLink.class.getName();

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqEpmLink
    */
   public static final java.lang.String REQUEST_ROLE = "request";
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqEpmLink
    */
   public e3ps.wfm.entity.KETWfmMultiApprovalRequest getRequest() {
      return (e3ps.wfm.entity.KETWfmMultiApprovalRequest) getRoleAObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqEpmLink
    */
   public void setRequest(e3ps.wfm.entity.KETWfmMultiApprovalRequest the_request) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_request);
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqEpmLink
    */
   public static final java.lang.String EPM_DOC_ROLE = "epmDoc";
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqEpmLink
    */
   public wt.epm.EPMDocument getEpmDoc() {
      return (wt.epm.EPMDocument) getRoleBObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.wfm.entity.KETWfmMultiReqEpmLink
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

   public static final long EXTERNALIZATION_VERSION_UID = 2538346186404157511L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

   }

   protected void super_writeExternal_KETWfmMultiReqEpmLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.wfm.entity.KETWfmMultiReqEpmLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETWfmMultiReqEpmLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

   }

   boolean readVersion2538346186404157511L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      return true;
   }

   protected boolean readVersion( KETWfmMultiReqEpmLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETWfmMultiReqEpmLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETWfmMultiReqEpmLink( _KETWfmMultiReqEpmLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
