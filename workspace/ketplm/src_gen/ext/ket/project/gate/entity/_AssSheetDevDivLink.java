package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AssSheetDevDivLink extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = AssSheetDevDivLink.class.getName();

   /**
    * @see ext.ket.project.gate.entity.AssSheetDevDivLink
    */
   public static final java.lang.String DEV_DIV_ROLE = "devDiv";
   /**
    * @see ext.ket.project.gate.entity.AssSheetDevDivLink
    */
   public e3ps.common.code.NumberCode getDevDiv() {
      return (e3ps.common.code.NumberCode) getRoleAObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssSheetDevDivLink
    */
   public void setDevDiv(e3ps.common.code.NumberCode the_devDiv) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_devDiv);
   }

   /**
    * @see ext.ket.project.gate.entity.AssSheetDevDivLink
    */
   public static final java.lang.String ASSESS_SHEET_ROLE = "theAssessSheet";
   /**
    * @see ext.ket.project.gate.entity.AssSheetDevDivLink
    */
   public ext.ket.project.gate.entity.AssessSheet getAssessSheet() {
      return (ext.ket.project.gate.entity.AssessSheet) getRoleBObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssSheetDevDivLink
    */
   public void setAssessSheet(ext.ket.project.gate.entity.AssessSheet the_theAssessSheet) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theAssessSheet);
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

   protected void super_writeExternal_AssSheetDevDivLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.AssSheetDevDivLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_AssSheetDevDivLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( AssSheetDevDivLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_AssSheetDevDivLink( _AssSheetDevDivLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
