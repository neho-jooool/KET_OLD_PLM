package ext.ket.project.atft.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETATFTSheetLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.atft.entity.entityResource";
   static final java.lang.String CLASSNAME = KETATFTSheetLink.class.getName();

   /**
    * 판정근거
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public static final java.lang.String BASIS = "basis";
   static int BASIS_UPPER_LIMIT = -1;
   java.lang.String basis;
   /**
    * 판정근거
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public java.lang.String getBasis() {
      return basis;
   }
   /**
    * 판정근거
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public void setBasis(java.lang.String basis) throws wt.util.WTPropertyVetoException {
      basisValidate(basis);
      this.basis = basis;
   }
   void basisValidate(java.lang.String basis) throws wt.util.WTPropertyVetoException {
      if (BASIS_UPPER_LIMIT < 1) {
         try { BASIS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("basis").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BASIS_UPPER_LIMIT = 4000; }
      }
      if (basis != null && !wt.fc.PersistenceHelper.checkStoredLength(basis.toString(), BASIS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "basis"), java.lang.String.valueOf(java.lang.Math.min(BASIS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "basis", this.basis, basis));
   }

   /**
    * 상태(OK or NG)
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public static final java.lang.String DESISION = "desision";
   static int DESISION_UPPER_LIMIT = -1;
   java.lang.String desision;
   /**
    * 상태(OK or NG)
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public java.lang.String getDesision() {
      return desision;
   }
   /**
    * 상태(OK or NG)
    *
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public void setDesision(java.lang.String desision) throws wt.util.WTPropertyVetoException {
      desisionValidate(desision);
      this.desision = desision;
   }
   void desisionValidate(java.lang.String desision) throws wt.util.WTPropertyVetoException {
      if (DESISION_UPPER_LIMIT < 1) {
         try { DESISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("desision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESISION_UPPER_LIMIT = 200; }
      }
      if (desision != null && !wt.fc.PersistenceHelper.checkStoredLength(desision.toString(), DESISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "desision"), java.lang.String.valueOf(java.lang.Math.min(DESISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "desision", this.desision, desision));
   }

   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public static final java.lang.String MAIN_SHEET_ROLE = "mainSheet";
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public ext.ket.project.atft.entity.KETATFTMainSheet getMainSheet() {
      return (ext.ket.project.atft.entity.KETATFTMainSheet) getRoleAObject();
   }
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public void setMainSheet(ext.ket.project.atft.entity.KETATFTMainSheet the_mainSheet) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_mainSheet);
   }

   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public static final java.lang.String SHEET_TEMPLATE_ROLE = "sheetTemplate";
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public ext.ket.project.atft.entity.KETATFTSheetTemplate getSheetTemplate() {
      return (ext.ket.project.atft.entity.KETATFTSheetTemplate) getRoleBObject();
   }
   /**
    * @see ext.ket.project.atft.entity.KETATFTSheetLink
    */
   public void setSheetTemplate(ext.ket.project.atft.entity.KETATFTSheetTemplate the_sheetTemplate) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_sheetTemplate);
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

   public static final long EXTERNALIZATION_VERSION_UID = -1755123413505574703L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( basis );
      output.writeObject( desision );
   }

   protected void super_writeExternal_KETATFTSheetLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.atft.entity.KETATFTSheetLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETATFTSheetLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "basis", basis );
      output.setString( "desision", desision );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      basis = input.getString( "basis" );
      desision = input.getString( "desision" );
   }

   boolean readVersion_1755123413505574703L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      basis = (java.lang.String) input.readObject();
      desision = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETATFTSheetLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1755123413505574703L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETATFTSheetLink( _KETATFTSheetLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
