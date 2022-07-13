package ext.ket.sales.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETSalesCSMeetingManageLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.sales.entity.entityResource";
   static final java.lang.String CLASSNAME = KETSalesCSMeetingManageLink.class.getName();

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String SORT_NO = "sortNo";
   static int SORT_NO_UPPER_LIMIT = -1;
   java.lang.String sortNo;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public java.lang.String getSortNo() {
      return sortNo;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setSortNo(java.lang.String sortNo) throws wt.util.WTPropertyVetoException {
      sortNoValidate(sortNo);
      this.sortNo = sortNo;
   }
   void sortNoValidate(java.lang.String sortNo) throws wt.util.WTPropertyVetoException {
      if (SORT_NO_UPPER_LIMIT < 1) {
         try { SORT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sortNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SORT_NO_UPPER_LIMIT = 200; }
      }
      if (sortNo != null && !wt.fc.PersistenceHelper.checkStoredLength(sortNo.toString(), SORT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sortNo"), java.lang.String.valueOf(java.lang.Math.min(SORT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sortNo", this.sortNo, sortNo));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String CS_STATE = "csState";
   static int CS_STATE_UPPER_LIMIT = -1;
   java.lang.String csState;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public java.lang.String getCsState() {
      return csState;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setCsState(java.lang.String csState) throws wt.util.WTPropertyVetoException {
      csStateValidate(csState);
      this.csState = csState;
   }
   void csStateValidate(java.lang.String csState) throws wt.util.WTPropertyVetoException {
      if (CS_STATE_UPPER_LIMIT < 1) {
         try { CS_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("csState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CS_STATE_UPPER_LIMIT = 200; }
      }
      if (csState != null && !wt.fc.PersistenceHelper.checkStoredLength(csState.toString(), CS_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "csState"), java.lang.String.valueOf(java.lang.Math.min(CS_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "csState", this.csState, csState));
   }

   /**
    * 부서별 sortNo
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String DEPT_SORT_NO = "deptSortNo";
   static int DEPT_SORT_NO_UPPER_LIMIT = -1;
   java.lang.String deptSortNo;
   /**
    * 부서별 sortNo
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public java.lang.String getDeptSortNo() {
      return deptSortNo;
   }
   /**
    * 부서별 sortNo
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setDeptSortNo(java.lang.String deptSortNo) throws wt.util.WTPropertyVetoException {
      deptSortNoValidate(deptSortNo);
      this.deptSortNo = deptSortNo;
   }
   void deptSortNoValidate(java.lang.String deptSortNo) throws wt.util.WTPropertyVetoException {
      if (DEPT_SORT_NO_UPPER_LIMIT < 1) {
         try { DEPT_SORT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deptSortNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEPT_SORT_NO_UPPER_LIMIT = 200; }
      }
      if (deptSortNo != null && !wt.fc.PersistenceHelper.checkStoredLength(deptSortNo.toString(), DEPT_SORT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deptSortNo"), java.lang.String.valueOf(java.lang.Math.min(DEPT_SORT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deptSortNo", this.deptSortNo, deptSortNo));
   }

   /**
    * applicationData
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String CS_FILE = "csFile";
   static int CS_FILE_UPPER_LIMIT = -1;
   java.lang.String csFile;
   /**
    * applicationData
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public java.lang.String getCsFile() {
      return csFile;
   }
   /**
    * applicationData
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setCsFile(java.lang.String csFile) throws wt.util.WTPropertyVetoException {
      csFileValidate(csFile);
      this.csFile = csFile;
   }
   void csFileValidate(java.lang.String csFile) throws wt.util.WTPropertyVetoException {
      if (CS_FILE_UPPER_LIMIT < 1) {
         try { CS_FILE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("csFile").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CS_FILE_UPPER_LIMIT = 200; }
      }
      if (csFile != null && !wt.fc.PersistenceHelper.checkStoredLength(csFile.toString(), CS_FILE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "csFile"), java.lang.String.valueOf(java.lang.Math.min(CS_FILE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "csFile", this.csFile, csFile));
   }

   /**
    * front / back
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String FILE_SORT_OPTION = "FileSortOption";
   static int FILE_SORT_OPTION_UPPER_LIMIT = -1;
   java.lang.String FileSortOption;
   /**
    * front / back
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public java.lang.String getFileSortOption() {
      return FileSortOption;
   }
   /**
    * front / back
    *
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setFileSortOption(java.lang.String FileSortOption) throws wt.util.WTPropertyVetoException {
      FileSortOptionValidate(FileSortOption);
      this.FileSortOption = FileSortOption;
   }
   void FileSortOptionValidate(java.lang.String FileSortOption) throws wt.util.WTPropertyVetoException {
      if (FILE_SORT_OPTION_UPPER_LIMIT < 1) {
         try { FILE_SORT_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("FileSortOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FILE_SORT_OPTION_UPPER_LIMIT = 200; }
      }
      if (FileSortOption != null && !wt.fc.PersistenceHelper.checkStoredLength(FileSortOption.toString(), FILE_SORT_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "FileSortOption"), java.lang.String.valueOf(java.lang.Math.min(FILE_SORT_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "FileSortOption", this.FileSortOption, FileSortOption));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String CS_MEETING_APPROVE = "csMeetingApprove";
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String CS_MEETING_APPROVE_REFERENCE = "csMeetingApproveReference";
   wt.fc.ObjectReference csMeetingApproveReference;
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public ext.ket.sales.entity.KETSalesCSMeetingApproval getCsMeetingApprove() {
      return (csMeetingApproveReference != null) ? (ext.ket.sales.entity.KETSalesCSMeetingApproval) csMeetingApproveReference.getObject() : null;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public wt.fc.ObjectReference getCsMeetingApproveReference() {
      return csMeetingApproveReference;
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setCsMeetingApprove(ext.ket.sales.entity.KETSalesCSMeetingApproval the_csMeetingApprove) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCsMeetingApproveReference(the_csMeetingApprove == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.sales.entity.KETSalesCSMeetingApproval) the_csMeetingApprove));
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setCsMeetingApproveReference(wt.fc.ObjectReference the_csMeetingApproveReference) throws wt.util.WTPropertyVetoException {
      csMeetingApproveReferenceValidate(the_csMeetingApproveReference);
      csMeetingApproveReference = (wt.fc.ObjectReference) the_csMeetingApproveReference;
   }
   void csMeetingApproveReferenceValidate(wt.fc.ObjectReference the_csMeetingApproveReference) throws wt.util.WTPropertyVetoException {
      if (the_csMeetingApproveReference != null && the_csMeetingApproveReference.getReferencedClass() != null &&
            !ext.ket.sales.entity.KETSalesCSMeetingApproval.class.isAssignableFrom(the_csMeetingApproveReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "csMeetingApproveReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "csMeetingApproveReference", this.csMeetingApproveReference, csMeetingApproveReference));
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String CS_PROJECT_ROLE = "csProject";
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public ext.ket.sales.entity.KETSalesProject getCsProject() {
      return (ext.ket.sales.entity.KETSalesProject) getRoleAObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setCsProject(ext.ket.sales.entity.KETSalesProject the_csProject) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_csProject);
   }

   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public static final java.lang.String CS_DEGREE_ROLE = "csDegree";
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public ext.ket.sales.entity.KETSalesCSMeetingManage getCsDegree() {
      return (ext.ket.sales.entity.KETSalesCSMeetingManage) getRoleBObject();
   }
   /**
    * @see ext.ket.sales.entity.KETSalesCSMeetingManageLink
    */
   public void setCsDegree(ext.ket.sales.entity.KETSalesCSMeetingManage the_csDegree) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_csDegree);
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

   public static final long EXTERNALIZATION_VERSION_UID = -699529582719497826L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( FileSortOption );
      output.writeObject( csFile );
      output.writeObject( csMeetingApproveReference );
      output.writeObject( csState );
      output.writeObject( deptSortNo );
      output.writeObject( sortNo );
   }

   protected void super_writeExternal_KETSalesCSMeetingManageLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.sales.entity.KETSalesCSMeetingManageLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETSalesCSMeetingManageLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "FileSortOption", FileSortOption );
      output.setString( "csFile", csFile );
      output.writeObject( "csMeetingApproveReference", csMeetingApproveReference, wt.fc.ObjectReference.class, true );
      output.setString( "csState", csState );
      output.setString( "deptSortNo", deptSortNo );
      output.setString( "sortNo", sortNo );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      FileSortOption = input.getString( "FileSortOption" );
      csFile = input.getString( "csFile" );
      csMeetingApproveReference = (wt.fc.ObjectReference) input.readObject( "csMeetingApproveReference", csMeetingApproveReference, wt.fc.ObjectReference.class, true );
      csState = input.getString( "csState" );
      deptSortNo = input.getString( "deptSortNo" );
      sortNo = input.getString( "sortNo" );
   }

   boolean readVersion_699529582719497826L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      FileSortOption = (java.lang.String) input.readObject();
      csFile = (java.lang.String) input.readObject();
      csMeetingApproveReference = (wt.fc.ObjectReference) input.readObject();
      csState = (java.lang.String) input.readObject();
      deptSortNo = (java.lang.String) input.readObject();
      sortNo = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETSalesCSMeetingManageLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_699529582719497826L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETSalesCSMeetingManageLink( _KETSalesCSMeetingManageLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
