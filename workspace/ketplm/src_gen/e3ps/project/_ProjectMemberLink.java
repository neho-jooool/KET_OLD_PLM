package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectMemberLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectMemberLink.class.getName();

   /**
    * 0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member≪≫4: Only View
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public static final java.lang.String PJT_MEMBER_TYPE = "pjtMemberType";
   int pjtMemberType;
   /**
    * 0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member≪≫4: Only View
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public int getPjtMemberType() {
      return pjtMemberType;
   }
   /**
    * 0: PM≪≫1: PL≪≫2: Role Member≪≫3: Etc Member≪≫4: Only View
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public void setPjtMemberType(int pjtMemberType) throws wt.util.WTPropertyVetoException {
      pjtMemberTypeValidate(pjtMemberType);
      this.pjtMemberType = pjtMemberType;
   }
   void pjtMemberTypeValidate(int pjtMemberType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Role Name≪≫≪≫기구(Team_Machine)≪≫영업(Team_Sale)≪≫제어(Team_Control)≪≫연구(Team_RND)≪≫QC(Team_QC)≪≫SW(Team_SW)≪≫구매(Team_Purchase)≪≫CS(Team_CS)≪≫조립(Team_Make)
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public static final java.lang.String PJT_ROLE = "pjtRole";
   static int PJT_ROLE_UPPER_LIMIT = -1;
   java.lang.String pjtRole;
   /**
    * Role Name≪≫≪≫기구(Team_Machine)≪≫영업(Team_Sale)≪≫제어(Team_Control)≪≫연구(Team_RND)≪≫QC(Team_QC)≪≫SW(Team_SW)≪≫구매(Team_Purchase)≪≫CS(Team_CS)≪≫조립(Team_Make)
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public java.lang.String getPjtRole() {
      return pjtRole;
   }
   /**
    * Role Name≪≫≪≫기구(Team_Machine)≪≫영업(Team_Sale)≪≫제어(Team_Control)≪≫연구(Team_RND)≪≫QC(Team_QC)≪≫SW(Team_SW)≪≫구매(Team_Purchase)≪≫CS(Team_CS)≪≫조립(Team_Make)
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public void setPjtRole(java.lang.String pjtRole) throws wt.util.WTPropertyVetoException {
      pjtRoleValidate(pjtRole);
      this.pjtRole = pjtRole;
   }
   void pjtRoleValidate(java.lang.String pjtRole) throws wt.util.WTPropertyVetoException {
      if (PJT_ROLE_UPPER_LIMIT < 1) {
         try { PJT_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_ROLE_UPPER_LIMIT = 200; }
      }
      if (pjtRole != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtRole.toString(), PJT_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtRole"), java.lang.String.valueOf(java.lang.Math.min(PJT_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtRole", this.pjtRole, pjtRole));
   }

   /**
    * 프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public static final java.lang.String PJT_HISTORY = "pjtHistory";
   int pjtHistory;
   /**
    * 프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public int getPjtHistory() {
      return pjtHistory;
   }
   /**
    * 프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.ProjectMemberLink
    */
   public void setPjtHistory(int pjtHistory) throws wt.util.WTPropertyVetoException {
      pjtHistoryValidate(pjtHistory);
      this.pjtHistory = pjtHistory;
   }
   void pjtHistoryValidate(int pjtHistory) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProjectMemberLink
    */
   public static final java.lang.String PROJECT_ROLE = "project";
   /**
    * @see e3ps.project.ProjectMemberLink
    */
   public e3ps.project.TemplateProject getProject() {
      return (e3ps.project.TemplateProject) getRoleAObject();
   }
   /**
    * @see e3ps.project.ProjectMemberLink
    */
   public void setProject(e3ps.project.TemplateProject the_project) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_project);
   }

   /**
    * @see e3ps.project.ProjectMemberLink
    */
   public static final java.lang.String MEMBER_ROLE = "member";
   /**
    * @see e3ps.project.ProjectMemberLink
    */
   public wt.org.WTUser getMember() {
      return (wt.org.WTUser) getRoleBObject();
   }
   /**
    * @see e3ps.project.ProjectMemberLink
    */
   public void setMember(wt.org.WTUser the_member) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_member);
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

   public static final long EXTERNALIZATION_VERSION_UID = 4743950565467288666L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeInt( pjtHistory );
      output.writeInt( pjtMemberType );
      output.writeObject( pjtRole );
   }

   protected void super_writeExternal_ProjectMemberLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectMemberLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ProjectMemberLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setInt( "pjtHistory", pjtHistory );
      output.setInt( "pjtMemberType", pjtMemberType );
      output.setString( "pjtRole", pjtRole );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      pjtHistory = input.getInt( "pjtHistory" );
      pjtMemberType = input.getInt( "pjtMemberType" );
      pjtRole = input.getString( "pjtRole" );
   }

   boolean readVersion4743950565467288666L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      pjtHistory = input.readInt();
      pjtMemberType = input.readInt();
      pjtRole = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ProjectMemberLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4743950565467288666L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ProjectMemberLink( _ProjectMemberLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
