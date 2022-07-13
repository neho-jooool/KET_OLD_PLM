package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _PlanTaskWork implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = PlanTaskWork.class.getName();

   /**
    * @see e3ps.project.PlanTaskWork
    */
   public static final java.lang.String PLAN_WORK_START = "planWorkStart";
   java.sql.Timestamp planWorkStart;
   /**
    * @see e3ps.project.PlanTaskWork
    */
   public java.sql.Timestamp getPlanWorkStart() {
      return planWorkStart;
   }
   /**
    * @see e3ps.project.PlanTaskWork
    */
   public void setPlanWorkStart(java.sql.Timestamp planWorkStart) throws wt.util.WTPropertyVetoException {
      planWorkStartValidate(planWorkStart);
      this.planWorkStart = planWorkStart;
   }
   void planWorkStartValidate(java.sql.Timestamp planWorkStart) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.PlanTaskWork
    */
   public static final java.lang.String PLAN_WORK_END = "planWorkEnd";
   java.sql.Timestamp planWorkEnd;
   /**
    * @see e3ps.project.PlanTaskWork
    */
   public java.sql.Timestamp getPlanWorkEnd() {
      return planWorkEnd;
   }
   /**
    * @see e3ps.project.PlanTaskWork
    */
   public void setPlanWorkEnd(java.sql.Timestamp planWorkEnd) throws wt.util.WTPropertyVetoException {
      planWorkEndValidate(planWorkEnd);
      this.planWorkEnd = planWorkEnd;
   }
   void planWorkEndValidate(java.sql.Timestamp planWorkEnd) throws wt.util.WTPropertyVetoException {
   }

   wt.fc.PersistInfo thePersistInfo = new wt.fc.PersistInfo();
   /**
    * @see wt.fc.Persistable
    */
   public wt.fc.PersistInfo getPersistInfo() {
      return thePersistInfo;
   }
   /**
    * @see wt.fc.Persistable
    */
   public void setPersistInfo(wt.fc.PersistInfo thePersistInfo) {
      this.thePersistInfo = thePersistInfo;
   }

   public java.lang.String toString() {
      return getConceptualClassname();
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

   public boolean equals(java.lang.Object obj) {
      return wt.fc.PersistenceHelper.equals(this, obj);
   }

   public int hashCode() {
      return wt.fc.PersistenceHelper.hashCode(this);
   }

   public static final long EXTERNALIZATION_VERSION_UID = 5069434426358482481L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( planWorkEnd );
      output.writeObject( planWorkStart );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.PlanTaskWork) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setTimestamp( "planWorkEnd", planWorkEnd );
      output.setTimestamp( "planWorkStart", planWorkStart );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      planWorkEnd = input.getTimestamp( "planWorkEnd" );
      planWorkStart = input.getTimestamp( "planWorkStart" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion5069434426358482481L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      planWorkEnd = (java.sql.Timestamp) input.readObject();
      planWorkStart = (java.sql.Timestamp) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( PlanTaskWork thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5069434426358482481L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
