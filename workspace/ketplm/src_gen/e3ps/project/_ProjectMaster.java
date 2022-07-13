package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProjectMaster implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProjectMaster.class.getName();

   /**
    * 프로젝트 NO
    *
    * @see e3ps.project.ProjectMaster
    */
   public static final java.lang.String PJT_NO = "pjtNo";
   static int PJT_NO_UPPER_LIMIT = -1;
   java.lang.String pjtNo;
   /**
    * 프로젝트 NO
    *
    * @see e3ps.project.ProjectMaster
    */
   public java.lang.String getPjtNo() {
      return pjtNo;
   }
   /**
    * 프로젝트 NO
    *
    * @see e3ps.project.ProjectMaster
    */
   public void setPjtNo(java.lang.String pjtNo) throws wt.util.WTPropertyVetoException {
      pjtNoValidate(pjtNo);
      this.pjtNo = pjtNo;
   }
   void pjtNoValidate(java.lang.String pjtNo) throws wt.util.WTPropertyVetoException {
      if (PJT_NO_UPPER_LIMIT < 1) {
         try { PJT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_NO_UPPER_LIMIT = 200; }
      }
      if (pjtNo != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtNo.toString(), PJT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtNo"), java.lang.String.valueOf(java.lang.Math.min(PJT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtNo", this.pjtNo, pjtNo));
   }

   /**
    * 프로젝트 명
    *
    * @see e3ps.project.ProjectMaster
    */
   public static final java.lang.String PJT_NAME = "pjtName";
   static int PJT_NAME_UPPER_LIMIT = -1;
   java.lang.String pjtName;
   /**
    * 프로젝트 명
    *
    * @see e3ps.project.ProjectMaster
    */
   public java.lang.String getPjtName() {
      return pjtName;
   }
   /**
    * 프로젝트 명
    *
    * @see e3ps.project.ProjectMaster
    */
   public void setPjtName(java.lang.String pjtName) throws wt.util.WTPropertyVetoException {
      pjtNameValidate(pjtName);
      this.pjtName = pjtName;
   }
   void pjtNameValidate(java.lang.String pjtName) throws wt.util.WTPropertyVetoException {
      if (PJT_NAME_UPPER_LIMIT < 1) {
         try { PJT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_NAME_UPPER_LIMIT = 200; }
      }
      if (pjtName != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtName.toString(), PJT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtName"), java.lang.String.valueOf(java.lang.Math.min(PJT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtName", this.pjtName, pjtName));
   }

   /**
    * @see e3ps.project.ProjectMaster
    */
   public static final java.lang.String PJT_TYPE_NAME = "pjtTypeName";
   static int PJT_TYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String pjtTypeName;
   /**
    * @see e3ps.project.ProjectMaster
    */
   public java.lang.String getPjtTypeName() {
      return pjtTypeName;
   }
   /**
    * @see e3ps.project.ProjectMaster
    */
   public void setPjtTypeName(java.lang.String pjtTypeName) throws wt.util.WTPropertyVetoException {
      pjtTypeNameValidate(pjtTypeName);
      this.pjtTypeName = pjtTypeName;
   }
   void pjtTypeNameValidate(java.lang.String pjtTypeName) throws wt.util.WTPropertyVetoException {
      if (PJT_TYPE_NAME_UPPER_LIMIT < 1) {
         try { PJT_TYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtTypeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_TYPE_NAME_UPPER_LIMIT = 200; }
      }
      if (pjtTypeName != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtTypeName.toString(), PJT_TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtTypeName"), java.lang.String.valueOf(java.lang.Math.min(PJT_TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtTypeName", this.pjtTypeName, pjtTypeName));
   }

   /**
    * @see e3ps.project.ProjectMaster
    */
   public static final java.lang.String LINK_PROJECT_NO = "linkProjectNo";
   static int LINK_PROJECT_NO_UPPER_LIMIT = -1;
   java.lang.String linkProjectNo;
   /**
    * @see e3ps.project.ProjectMaster
    */
   public java.lang.String getLinkProjectNo() {
      return linkProjectNo;
   }
   /**
    * @see e3ps.project.ProjectMaster
    */
   public void setLinkProjectNo(java.lang.String linkProjectNo) throws wt.util.WTPropertyVetoException {
      linkProjectNoValidate(linkProjectNo);
      this.linkProjectNo = linkProjectNo;
   }
   void linkProjectNoValidate(java.lang.String linkProjectNo) throws wt.util.WTPropertyVetoException {
      if (LINK_PROJECT_NO_UPPER_LIMIT < 1) {
         try { LINK_PROJECT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("linkProjectNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LINK_PROJECT_NO_UPPER_LIMIT = 4000; }
      }
      if (linkProjectNo != null && !wt.fc.PersistenceHelper.checkStoredLength(linkProjectNo.toString(), LINK_PROJECT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "linkProjectNo"), java.lang.String.valueOf(java.lang.Math.min(LINK_PROJECT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "linkProjectNo", this.linkProjectNo, linkProjectNo));
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

   public static final long EXTERNALIZATION_VERSION_UID = 8982101192491763100L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( linkProjectNo );
      output.writeObject( pjtName );
      output.writeObject( pjtNo );
      output.writeObject( pjtTypeName );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProjectMaster) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "linkProjectNo", linkProjectNo );
      output.setString( "pjtName", pjtName );
      output.setString( "pjtNo", pjtNo );
      output.setString( "pjtTypeName", pjtTypeName );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      linkProjectNo = input.getString( "linkProjectNo" );
      pjtName = input.getString( "pjtName" );
      pjtNo = input.getString( "pjtNo" );
      pjtTypeName = input.getString( "pjtTypeName" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion8982101192491763100L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      linkProjectNo = (java.lang.String) input.readObject();
      pjtName = (java.lang.String) input.readObject();
      pjtNo = (java.lang.String) input.readObject();
      pjtTypeName = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( ProjectMaster thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8982101192491763100L( input, readSerialVersionUID, superDone );
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
