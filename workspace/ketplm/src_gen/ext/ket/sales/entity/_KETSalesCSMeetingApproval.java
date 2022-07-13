package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesCSMeetingApproval extends wt.enterprise.Managed implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesCSMeetingApproval.class.getName();

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingApproval
    */
   public static final java.lang.String MEETING_MANAGE_LINK_INFO = "meetingManageLinkInfo";
   static int MEETING_MANAGE_LINK_INFO_UPPER_LIMIT = -1;
   java.lang.String meetingManageLinkInfo;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingApproval
    */
   public java.lang.String getMeetingManageLinkInfo() {
      return meetingManageLinkInfo;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingApproval
    */
   public void setMeetingManageLinkInfo(java.lang.String meetingManageLinkInfo) throws wt.util.WTPropertyVetoException {
      meetingManageLinkInfoValidate(meetingManageLinkInfo);
      this.meetingManageLinkInfo = meetingManageLinkInfo;
   }
   void meetingManageLinkInfoValidate(java.lang.String meetingManageLinkInfo) throws wt.util.WTPropertyVetoException {
      if (MEETING_MANAGE_LINK_INFO_UPPER_LIMIT < 1) {
         try { MEETING_MANAGE_LINK_INFO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("meetingManageLinkInfo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEETING_MANAGE_LINK_INFO_UPPER_LIMIT = 200; }
      }
      if (meetingManageLinkInfo != null && !wt.fc.PersistenceHelper.checkStoredLength(meetingManageLinkInfo.toString(), MEETING_MANAGE_LINK_INFO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingManageLinkInfo"), java.lang.String.valueOf(java.lang.Math.min(MEETING_MANAGE_LINK_INFO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "meetingManageLinkInfo", this.meetingManageLinkInfo, meetingManageLinkInfo));
   }

   /**
    * ?μ?????
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingApproval
    */
   public static final java.lang.String DEPT_SORT = "deptSort";
   static int DEPT_SORT_UPPER_LIMIT = -1;
   java.lang.String deptSort;
   /**
    * ?μ?????
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingApproval
    */
   public java.lang.String getDeptSort() {
      return deptSort;
   }
   /**
    * ?μ?????
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingApproval
    */
   public void setDeptSort(java.lang.String deptSort) throws wt.util.WTPropertyVetoException {
      deptSortValidate(deptSort);
      this.deptSort = deptSort;
   }
   void deptSortValidate(java.lang.String deptSort) throws wt.util.WTPropertyVetoException {
      if (DEPT_SORT_UPPER_LIMIT < 1) {
         try { DEPT_SORT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptSort").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_SORT_UPPER_LIMIT = 200; }
      }
      if (deptSort != null && !wt.fc.PersistenceHelper.checkStoredLength(deptSort.toString(), DEPT_SORT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptSort"), java.lang.String.valueOf(java.lang.Math.min(DEPT_SORT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptSort", this.deptSort, deptSort));
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

   public static final long EXTERNALIZATION_VERSION_UID = 4173903257108336088L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( deptSort );
      output.writeObject( meetingManageLinkInfo );
   }

   protected void super_writeExternal_KETSalesCSMeetingApproval(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesCSMeetingApproval) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSalesCSMeetingApproval(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "deptSort", deptSort );
      output.setString( "meetingManageLinkInfo", meetingManageLinkInfo );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      deptSort = input.getString( "deptSort" );
      meetingManageLinkInfo = input.getString( "meetingManageLinkInfo" );
   }

   boolean readVersion4173903257108336088L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      deptSort = (java.lang.String) input.readObject();
      meetingManageLinkInfo = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETSalesCSMeetingApproval thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4173903257108336088L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSalesCSMeetingApproval( _KETSalesCSMeetingApproval thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
