package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _PointDivisionTheE3PSTask extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = PointDivisionTheE3PSTask.class.getName();

   /**
    * @see e3ps.project.PointDivisionTheE3PSTask
    */
   public static final java.lang.String POINT_DIVISION_ROLE = "pointDivision";
   /**
    * @see e3ps.project.PointDivisionTheE3PSTask
    */
   public e3ps.common.code.NumberCode getPointDivision() {
      return (e3ps.common.code.NumberCode) getRoleAObject();
   }
   /**
    * @see e3ps.project.PointDivisionTheE3PSTask
    */
   public void setPointDivision(e3ps.common.code.NumberCode the_pointDivision) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_pointDivision);
   }

   /**
    * @see e3ps.project.PointDivisionTheE3PSTask
    */
   public static final java.lang.String E3_PSTASK_ROLE = "theE3PSTask";
   /**
    * @see e3ps.project.PointDivisionTheE3PSTask
    */
   public e3ps.project.E3PSTask getE3PSTask() {
      return (e3ps.project.E3PSTask) getRoleBObject();
   }
   /**
    * @see e3ps.project.PointDivisionTheE3PSTask
    */
   public void setE3PSTask(e3ps.project.E3PSTask the_theE3PSTask) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theE3PSTask);
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

   protected void super_writeExternal_PointDivisionTheE3PSTask(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.PointDivisionTheE3PSTask) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_PointDivisionTheE3PSTask(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( PointDivisionTheE3PSTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_PointDivisionTheE3PSTask( _PointDivisionTheE3PSTask thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
