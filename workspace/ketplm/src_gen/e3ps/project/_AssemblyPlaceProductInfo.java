package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AssemblyPlaceProductInfo extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = AssemblyPlaceProductInfo.class.getName();

   /**
    * @see e3ps.project.AssemblyPlaceProductInfo
    */
   public static final java.lang.String ASSEMBLY_PLACE_ROLE = "assemblyPlace";
   /**
    * @see e3ps.project.AssemblyPlaceProductInfo
    */
   public e3ps.common.code.NumberCode getAssemblyPlace() {
      return (e3ps.common.code.NumberCode) getRoleAObject();
   }
   /**
    * @see e3ps.project.AssemblyPlaceProductInfo
    */
   public void setAssemblyPlace(e3ps.common.code.NumberCode the_assemblyPlace) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_assemblyPlace);
   }

   /**
    * @see e3ps.project.AssemblyPlaceProductInfo
    */
   public static final java.lang.String PRODUCT_INFO_ROLE = "productInfo";
   /**
    * @see e3ps.project.AssemblyPlaceProductInfo
    */
   public e3ps.project.ProductInfo getProductInfo() {
      return (e3ps.project.ProductInfo) getRoleBObject();
   }
   /**
    * @see e3ps.project.AssemblyPlaceProductInfo
    */
   public void setProductInfo(e3ps.project.ProductInfo the_productInfo) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_productInfo);
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

   protected void super_writeExternal_AssemblyPlaceProductInfo(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.AssemblyPlaceProductInfo) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_AssemblyPlaceProductInfo(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( AssemblyPlaceProductInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_AssemblyPlaceProductInfo( _AssemblyPlaceProductInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
