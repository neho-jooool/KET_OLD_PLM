package e3ps.groupware.board;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _NotifyWTUserLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.board.boardResource";
   static final java.lang.String CLASSNAME = NotifyWTUserLink.class.getName();

   /**
    * @see e3ps.groupware.board.NotifyWTUserLink
    */
   public static final java.lang.String USER_ROLE = "user";
   /**
    * @see e3ps.groupware.board.NotifyWTUserLink
    */
   public wt.org.WTUser getUser() {
      return (wt.org.WTUser) getRoleAObject();
   }
   /**
    * @see e3ps.groupware.board.NotifyWTUserLink
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_user);
   }

   /**
    * @see e3ps.groupware.board.NotifyWTUserLink
    */
   public static final java.lang.String NOTIFY_ROLE = "notify";
   /**
    * @see e3ps.groupware.board.NotifyWTUserLink
    */
   public e3ps.groupware.board.Notify getNotify() {
      return (e3ps.groupware.board.Notify) getRoleBObject();
   }
   /**
    * @see e3ps.groupware.board.NotifyWTUserLink
    */
   public void setNotify(e3ps.groupware.board.Notify the_notify) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_notify);
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

   protected void super_writeExternal_NotifyWTUserLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.board.NotifyWTUserLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_NotifyWTUserLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( NotifyWTUserLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((NotifyWTUserLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_NotifyWTUserLink( _NotifyWTUserLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
