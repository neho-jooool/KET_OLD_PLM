package e3ps.access;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AccessDeptLink extends e3ps.access.AccessAuthLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.access.accessResource";
   static final java.lang.String CLASSNAME = AccessDeptLink.class.getName();

   /**
    * @see e3ps.access.AccessDeptLink
    */
   public static final java.lang.String ACCESS_ROLE = "access";
   /**
    * @see e3ps.access.AccessDeptLink
    */
   public e3ps.access.AccessAuthority getAccess() {
      return (e3ps.access.AccessAuthority) getRoleAObject();
   }
   /**
    * @see e3ps.access.AccessDeptLink
    */
   public void setAccess(e3ps.access.AccessAuthority the_access) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_access);
   }

   /**
    * @see e3ps.access.AccessDeptLink
    */
   public static final java.lang.String DEPT_ROLE = "dept";
   /**
    * @see e3ps.access.AccessDeptLink
    */
   public e3ps.groupware.company.Department getDept() {
      return (e3ps.groupware.company.Department) getRoleBObject();
   }
   /**
    * @see e3ps.access.AccessDeptLink
    */
   public void setDept(e3ps.groupware.company.Department the_dept) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_dept);
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

   public static final long EXTERNALIZATION_VERSION_UID = -127492238611558224L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

   }

   protected void super_writeExternal_AccessDeptLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.access.AccessDeptLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_AccessDeptLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

   }

   boolean readVersion_127492238611558224L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      return true;
   }

   protected boolean readVersion( AccessDeptLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_127492238611558224L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_AccessDeptLink( _AccessDeptLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
