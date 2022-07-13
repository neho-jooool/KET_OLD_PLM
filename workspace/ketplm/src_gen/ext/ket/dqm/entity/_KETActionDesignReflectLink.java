package ext.ket.dqm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETActionDesignReflectLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dqm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETActionDesignReflectLink.class.getName();

   /**
    * @see ext.ket.dqm.entity.KETActionDesignReflectLink
    */
   public static final java.lang.String DQM_ACTION_ROLE = "dqmAction";
   /**
    * @see ext.ket.dqm.entity.KETActionDesignReflectLink
    */
   public ext.ket.dqm.entity.KETDqmAction getDqmAction() {
      return (ext.ket.dqm.entity.KETDqmAction) getRoleAObject();
   }
   /**
    * @see ext.ket.dqm.entity.KETActionDesignReflectLink
    */
   public void setDqmAction(ext.ket.dqm.entity.KETDqmAction the_dqmAction) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_dqmAction);
   }

   /**
    * @see ext.ket.dqm.entity.KETActionDesignReflectLink
    */
   public static final java.lang.String DQM_ACTION_DESIGN_REFLECT_ROLE = "dqmActionDesignReflect";
   /**
    * @see ext.ket.dqm.entity.KETActionDesignReflectLink
    */
   public ext.ket.dqm.entity.KETDqmActionDesignReflect getDqmActionDesignReflect() {
      return (ext.ket.dqm.entity.KETDqmActionDesignReflect) getRoleBObject();
   }
   /**
    * @see ext.ket.dqm.entity.KETActionDesignReflectLink
    */
   public void setDqmActionDesignReflect(ext.ket.dqm.entity.KETDqmActionDesignReflect the_dqmActionDesignReflect) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_dqmActionDesignReflect);
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

   protected void super_writeExternal_KETActionDesignReflectLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dqm.entity.KETActionDesignReflectLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETActionDesignReflectLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( KETActionDesignReflectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETActionDesignReflectLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETActionDesignReflectLink( _KETActionDesignReflectLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
