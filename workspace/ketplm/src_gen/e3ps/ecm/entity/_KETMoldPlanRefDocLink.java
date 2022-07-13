package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldPlanRefDocLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldPlanRefDocLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldPlanRefDocLink
    */
   public static final java.lang.String MOLD_PLAN_ROLE = "moldPlan";
   /**
    * @see e3ps.ecm.entity.KETMoldPlanRefDocLink
    */
   public e3ps.ecm.entity.KETMoldChangePlan getMoldPlan() {
      return (e3ps.ecm.entity.KETMoldChangePlan) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldPlanRefDocLink
    */
   public void setMoldPlan(e3ps.ecm.entity.KETMoldChangePlan the_moldPlan) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_moldPlan);
   }

   /**
    * @see e3ps.ecm.entity.KETMoldPlanRefDocLink
    */
   public static final java.lang.String REF_DOC_ROLE = "refDoc";
   /**
    * @see e3ps.ecm.entity.KETMoldPlanRefDocLink
    */
   public e3ps.dms.entity.KETProjectDocument getRefDoc() {
      return (e3ps.dms.entity.KETProjectDocument) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldPlanRefDocLink
    */
   public void setRefDoc(e3ps.dms.entity.KETProjectDocument the_refDoc) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_refDoc);
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

   protected void super_writeExternal_KETMoldPlanRefDocLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldPlanRefDocLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldPlanRefDocLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( KETMoldPlanRefDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((KETMoldPlanRefDocLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldPlanRefDocLink( _KETMoldPlanRefDocLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
