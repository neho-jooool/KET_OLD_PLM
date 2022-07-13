package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _EPMDocProjectLink extends wt.vc.ObjectToVersionLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = EPMDocProjectLink.class.getName();

   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public static final java.lang.String INIT_PJT_VERSION = "initPjtVersion";
   static int INIT_PJT_VERSION_UPPER_LIMIT = -1;
   java.lang.String initPjtVersion;
   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public java.lang.String getInitPjtVersion() {
      return initPjtVersion;
   }
   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public void setInitPjtVersion(java.lang.String initPjtVersion) throws wt.util.WTPropertyVetoException {
      initPjtVersionValidate(initPjtVersion);
      this.initPjtVersion = initPjtVersion;
   }
   void initPjtVersionValidate(java.lang.String initPjtVersion) throws wt.util.WTPropertyVetoException {
      if (INIT_PJT_VERSION_UPPER_LIMIT < 1) {
         try { INIT_PJT_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("initPjtVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INIT_PJT_VERSION_UPPER_LIMIT = 200; }
      }
      if (initPjtVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(initPjtVersion.toString(), INIT_PJT_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "initPjtVersion"), java.lang.String.valueOf(java.lang.Math.min(INIT_PJT_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "initPjtVersion", this.initPjtVersion, initPjtVersion));
   }

   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public static final java.lang.String PJT_MASTER_ROLE = "pjtMaster";
   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public e3ps.project.ProjectMaster getPjtMaster() {
      return (e3ps.project.ProjectMaster) getRoleAObject();
   }
   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public void setPjtMaster(e3ps.project.ProjectMaster the_pjtMaster) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_pjtMaster);
   }

   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public static final java.lang.String PJT_DOC_ROLE = "pjtDoc";
   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public wt.epm.EPMDocument getPjtDoc() {
      return (wt.epm.EPMDocument) getRoleBObject();
   }
   /**
    * @see e3ps.edm.EPMDocProjectLink
    */
   public void setPjtDoc(wt.epm.EPMDocument the_pjtDoc) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_pjtDoc);
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

   public static final long EXTERNALIZATION_VERSION_UID = -5873714174633335204L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( initPjtVersion );
   }

   protected void super_writeExternal_EPMDocProjectLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.edm.EPMDocProjectLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_EPMDocProjectLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "initPjtVersion", initPjtVersion );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      initPjtVersion = input.getString( "initPjtVersion" );
   }

   boolean readVersion_5873714174633335204L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      initPjtVersion = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( EPMDocProjectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_5873714174633335204L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_EPMDocProjectLink( _EPMDocProjectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
