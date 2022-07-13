package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldPlanPEcoOwnerLink extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldPlanPEcoOwnerLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldPlanPEcoOwnerLink
    */
   public static final java.lang.String PROD_ECO_OWNER_ROLE = "prodEcoOwner";
   /**
    * @see e3ps.ecm.entity.KETMoldPlanPEcoOwnerLink
    */
   public wt.org.WTUser getProdEcoOwner() {
      return (wt.org.WTUser) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldPlanPEcoOwnerLink
    */
   public void setProdEcoOwner(wt.org.WTUser the_prodEcoOwner) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_prodEcoOwner);
   }

   /**
    * @see e3ps.ecm.entity.KETMoldPlanPEcoOwnerLink
    */
   public static final java.lang.String KETMOLD_CHANGE_PLAN_ROLE = "theKETMoldChangePlan";
   /**
    * @see e3ps.ecm.entity.KETMoldPlanPEcoOwnerLink
    */
   public e3ps.ecm.entity.KETMoldChangePlan getKETMoldChangePlan() {
      return (e3ps.ecm.entity.KETMoldChangePlan) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldPlanPEcoOwnerLink
    */
   public void setKETMoldChangePlan(e3ps.ecm.entity.KETMoldChangePlan the_theKETMoldChangePlan) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_theKETMoldChangePlan);
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

   protected void super_writeExternal_KETMoldPlanPEcoOwnerLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldPlanPEcoOwnerLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldPlanPEcoOwnerLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( KETMoldPlanPEcoOwnerLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldPlanPEcoOwnerLink( _KETMoldPlanPEcoOwnerLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
