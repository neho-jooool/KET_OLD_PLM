package ext.ket.project.program.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProgramProjectLink extends wt.vc.ObjectToVersionLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.program.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProgramProjectLink.class.getName();

   /**
    * 기준 프로그램
    *
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public static final java.lang.String IS_BASE = "isBase";
   boolean isBase = false;
   /**
    * 기준 프로그램
    *
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public boolean isIsBase() {
      return isBase;
   }
   /**
    * 기준 프로그램
    *
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public void setIsBase(boolean isBase) throws wt.util.WTPropertyVetoException {
      isBaseValidate(isBase);
      this.isBase = isBase;
   }
   void isBaseValidate(boolean isBase) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 일정 확인
    *
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public static final java.lang.String IS_CHECKED_EVENT = "isCheckedEvent";
   java.lang.Boolean isCheckedEvent = false;
   /**
    * 일정 확인
    *
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public java.lang.Boolean getIsCheckedEvent() {
      return isCheckedEvent;
   }
   /**
    * 일정 확인
    *
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public void setIsCheckedEvent(java.lang.Boolean isCheckedEvent) throws wt.util.WTPropertyVetoException {
      isCheckedEventValidate(isCheckedEvent);
      this.isCheckedEvent = isCheckedEvent;
   }
   void isCheckedEventValidate(java.lang.Boolean isCheckedEvent) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public static final java.lang.String PROJECT_ROLE = "project";
   /**
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public e3ps.project.ProductProject getProject() {
      return (e3ps.project.ProductProject) getRoleAObject();
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public void setProject(e3ps.project.ProductProject the_project) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_project);
   }

   /**
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public static final java.lang.String PROGRAM_ROLE = "program";
   /**
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public ext.ket.project.program.entity.KETProgramSchedule getProgram() {
      return (ext.ket.project.program.entity.KETProgramSchedule) getRoleBObject();
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramProjectLink
    */
   public void setProgram(ext.ket.project.program.entity.KETProgramSchedule the_program) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_program);
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

   public static final long EXTERNALIZATION_VERSION_UID = -100657797297483051L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeBoolean( isBase );
      output.writeObject( isCheckedEvent );
   }

   protected void super_writeExternal_KETProgramProjectLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.program.entity.KETProgramProjectLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProgramProjectLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setBoolean( "isBase", isBase );
      output.setBooleanObject( "isCheckedEvent", isCheckedEvent );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      isBase = input.getBoolean( "isBase" );
      isCheckedEvent = input.getBooleanObject( "isCheckedEvent" );
   }

   boolean readVersion_100657797297483051L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      isBase = input.readBoolean();
      isCheckedEvent = (java.lang.Boolean) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProgramProjectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_100657797297483051L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProgramProjectLink( _KETProgramProjectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
