package e3ps.project.cancel;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _cancelHistoryLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.cancel.cancelResource";
   static final java.lang.String CLASSNAME = cancelHistoryLink.class.getName();

   /**
    * @see e3ps.project.cancel.cancelHistoryLink
    */
   public static final java.lang.String COST_HISTORY_ROLE = "costHistory";
   /**
    * @see e3ps.project.cancel.cancelHistoryLink
    */
   public e3ps.project.cancel.CancelCostHistory getCostHistory() {
      return (e3ps.project.cancel.CancelCostHistory) getRoleAObject();
   }
   /**
    * @see e3ps.project.cancel.cancelHistoryLink
    */
   public void setCostHistory(e3ps.project.cancel.CancelCostHistory the_costHistory) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_costHistory);
   }

   /**
    * @see e3ps.project.cancel.cancelHistoryLink
    */
   public static final java.lang.String CANCEL_ROLE = "cancel";
   /**
    * @see e3ps.project.cancel.cancelHistoryLink
    */
   public e3ps.project.cancel.CancelProject getCancel() {
      return (e3ps.project.cancel.CancelProject) getRoleBObject();
   }
   /**
    * @see e3ps.project.cancel.cancelHistoryLink
    */
   public void setCancel(e3ps.project.cancel.CancelProject the_cancel) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_cancel);
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

   protected void super_writeExternal_cancelHistoryLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.cancel.cancelHistoryLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_cancelHistoryLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( cancelHistoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 2538346186404157511L )
         return ((cancelHistoryLink) this).readVersion2538346186404157511L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_cancelHistoryLink( _cancelHistoryLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
