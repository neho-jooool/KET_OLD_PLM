package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CreateUserTheKETSalesCSMeetingManage extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = CreateUserTheKETSalesCSMeetingManage.class.getName();

   /**
    * @see ext.ket.sales.entity.CreateUserTheKETSalesCSMeetingManage
    */
   public static final java.lang.String CREATE_USER_ROLE = "createUser";
   /**
    * @see ext.ket.sales.entity.CreateUserTheKETSalesCSMeetingManage
    */
   public wt.org.WTUser getCreateUser() {
      return (wt.org.WTUser) getRoleAObject();
   }
   /**
    * @see ext.ket.sales.entity.CreateUserTheKETSalesCSMeetingManage
    */
   public void setCreateUser(wt.org.WTUser the_createUser) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_createUser);
   }

   /**
    * @see ext.ket.sales.entity.CreateUserTheKETSalesCSMeetingManage
    */
   public static final java.lang.String KETSALES_CSMEETING_MANAGE_ROLE = "theKETSalesCSMeetingManage";
   /**
    * @see ext.ket.sales.entity.CreateUserTheKETSalesCSMeetingManage
    */
   public ext.ket.sales.entity.KETSalesCSMeetingManage getKETSalesCSMeetingManage() {
      return (ext.ket.sales.entity.KETSalesCSMeetingManage) getRoleBObject();
   }
   /**
    * @see ext.ket.sales.entity.CreateUserTheKETSalesCSMeetingManage
    */
   public void setKETSalesCSMeetingManage(ext.ket.sales.entity.KETSalesCSMeetingManage the_theKETSalesCSMeetingManage) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theKETSalesCSMeetingManage);
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

   public static final long EXTERNALIZATION_VERSION_UID = 2454203546147727912L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

   }

   protected void super_writeExternal_CreateUserTheKETSalesCSMeetingManage(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.CreateUserTheKETSalesCSMeetingManage) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_CreateUserTheKETSalesCSMeetingManage(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

   }

   boolean readVersion2454203546147727912L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      return true;
   }

   protected boolean readVersion( CreateUserTheKETSalesCSMeetingManage thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_CreateUserTheKETSalesCSMeetingManage( _CreateUserTheKETSalesCSMeetingManage thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
