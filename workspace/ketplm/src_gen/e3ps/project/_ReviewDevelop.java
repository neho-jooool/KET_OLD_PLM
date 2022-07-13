package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ReviewDevelop implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ReviewDevelop.class.getName();

   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String PAST_DESC = "pastDesc";
   static int PAST_DESC_UPPER_LIMIT = -1;
   java.lang.String pastDesc;
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public java.lang.String getPastDesc() {
      return pastDesc;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setPastDesc(java.lang.String pastDesc) throws wt.util.WTPropertyVetoException {
      pastDescValidate(pastDesc);
      this.pastDesc = pastDesc;
   }
   void pastDescValidate(java.lang.String pastDesc) throws wt.util.WTPropertyVetoException {
      if (PAST_DESC_UPPER_LIMIT < 1) {
         try { PAST_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pastDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAST_DESC_UPPER_LIMIT = 6000; }
      }
      if (pastDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(pastDesc.toString(), PAST_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pastDesc"), java.lang.String.valueOf(java.lang.Math.min(PAST_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pastDesc", this.pastDesc, pastDesc));
   }

   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String IMPROVE_DESC = "improveDesc";
   static int IMPROVE_DESC_UPPER_LIMIT = -1;
   java.lang.String improveDesc;
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public java.lang.String getImproveDesc() {
      return improveDesc;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setImproveDesc(java.lang.String improveDesc) throws wt.util.WTPropertyVetoException {
      improveDescValidate(improveDesc);
      this.improveDesc = improveDesc;
   }
   void improveDescValidate(java.lang.String improveDesc) throws wt.util.WTPropertyVetoException {
      if (IMPROVE_DESC_UPPER_LIMIT < 1) {
         try { IMPROVE_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("improveDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IMPROVE_DESC_UPPER_LIMIT = 6000; }
      }
      if (improveDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(improveDesc.toString(), IMPROVE_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "improveDesc"), java.lang.String.valueOf(java.lang.Math.min(IMPROVE_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "improveDesc", this.improveDesc, improveDesc));
   }

   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String REVIEW_DESC = "reviewDesc";
   static int REVIEW_DESC_UPPER_LIMIT = -1;
   java.lang.String reviewDesc;
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public java.lang.String getReviewDesc() {
      return reviewDesc;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setReviewDesc(java.lang.String reviewDesc) throws wt.util.WTPropertyVetoException {
      reviewDescValidate(reviewDesc);
      this.reviewDesc = reviewDesc;
   }
   void reviewDescValidate(java.lang.String reviewDesc) throws wt.util.WTPropertyVetoException {
      if (REVIEW_DESC_UPPER_LIMIT < 1) {
         try { REVIEW_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reviewDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REVIEW_DESC_UPPER_LIMIT = 6000; }
      }
      if (reviewDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(reviewDesc.toString(), REVIEW_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reviewDesc"), java.lang.String.valueOf(java.lang.Math.min(REVIEW_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reviewDesc", this.reviewDesc, reviewDesc));
   }

   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String ATTR1 = "attr1";
   static int ATTR1_UPPER_LIMIT = -1;
   java.lang.String attr1;
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public java.lang.String getAttr1() {
      return attr1;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setAttr1(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      attr1Validate(attr1);
      this.attr1 = attr1;
   }
   void attr1Validate(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      if (ATTR1_UPPER_LIMIT < 1) {
         try { ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR1_UPPER_LIMIT = 2000; }
      }
      if (attr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr1.toString(), ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr1"), java.lang.String.valueOf(java.lang.Math.min(ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr1", this.attr1, attr1));
   }

   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String PAST_TYPE = "pastType";
   e3ps.project.PastType pastType;
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public e3ps.project.PastType getPastType() {
      return pastType;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setPastType(e3ps.project.PastType pastType) throws wt.util.WTPropertyVetoException {
      pastTypeValidate(pastType);
      this.pastType = pastType;
   }
   void pastTypeValidate(e3ps.project.PastType pastType) throws wt.util.WTPropertyVetoException {
      if (pastType == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pastType") },
               new java.beans.PropertyChangeEvent(this, "pastType", this.pastType, pastType));
   }

   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String DEVELOPTYPE = "developtype";
   e3ps.project.DevelopType developtype;
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public e3ps.project.DevelopType getDeveloptype() {
      return developtype;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setDeveloptype(e3ps.project.DevelopType developtype) throws wt.util.WTPropertyVetoException {
      developtypeValidate(developtype);
      this.developtype = developtype;
   }
   void developtypeValidate(e3ps.project.DevelopType developtype) throws wt.util.WTPropertyVetoException {
      if (developtype == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "developtype") },
               new java.beans.PropertyChangeEvent(this, "developtype", this.developtype, developtype));
   }

   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public e3ps.project.ReviewProject getProject() {
      return (projectReference != null) ? (e3ps.project.ReviewProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setProject(e3ps.project.ReviewProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ReviewProject) the_project));
   }
   /**
    * @see e3ps.project.ReviewDevelop
    */
   public void setProjectReference(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      projectReferenceValidate(the_projectReference);
      projectReference = (wt.fc.ObjectReference) the_projectReference;
   }
   void projectReferenceValidate(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      if (the_projectReference != null && the_projectReference.getReferencedClass() != null &&
            !e3ps.project.ReviewProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   wt.org.WTPrincipalReference owner;
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public wt.org.WTPrincipalReference getOwner() {
      return owner;
   }
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      ownerValidate(owner);
      this.owner = owner;
   }
   void ownerValidate(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      if (owner == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "owner") },
               new java.beans.PropertyChangeEvent(this, "owner", this.owner, owner));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1239443868264035787L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( attr1 );
      output.writeObject( (developtype == null ? null : developtype.getStringValue()) );
      output.writeObject( improveDesc );
      output.writeObject( owner );
      output.writeObject( pastDesc );
      output.writeObject( (pastType == null ? null : pastType.getStringValue()) );
      output.writeObject( projectReference );
      output.writeObject( reviewDesc );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ReviewDevelop) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "attr1", attr1 );
      output.setString( "developtype", (developtype == null ? null : developtype.toString()) );
      output.setString( "improveDesc", improveDesc );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "pastDesc", pastDesc );
      output.setString( "pastType", (pastType == null ? null : pastType.toString()) );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "reviewDesc", reviewDesc );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      attr1 = input.getString( "attr1" );
      java.lang.String developtype_string_value = (java.lang.String) input.getString("developtype");
      if ( developtype_string_value != null ) { 
         developtype = (e3ps.project.DevelopType) wt.introspection.ClassInfo.getConstrainedEnum( getClass(), "developtype", developtype_string_value );
         if ( developtype == null )  // hard-coded type
            developtype = e3ps.project.DevelopType.toDevelopType( developtype_string_value );
      }
      improveDesc = input.getString( "improveDesc" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      pastDesc = input.getString( "pastDesc" );
      java.lang.String pastType_string_value = (java.lang.String) input.getString("pastType");
      if ( pastType_string_value != null ) { 
         pastType = (e3ps.project.PastType) wt.introspection.ClassInfo.getConstrainedEnum( getClass(), "pastType", pastType_string_value );
         if ( pastType == null )  // hard-coded type
            pastType = e3ps.project.PastType.toPastType( pastType_string_value );
      }
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      reviewDesc = input.getString( "reviewDesc" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion1239443868264035787L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      attr1 = (java.lang.String) input.readObject();
      java.lang.String developtype_string_value = (java.lang.String) input.readObject();
      try { 
         developtype = (e3ps.project.DevelopType) wt.fc.EnumeratedTypeUtil.toEnumeratedType( developtype_string_value );
      } catch( wt.util.WTInvalidParameterException e ) {
         // Old Format
         developtype = e3ps.project.DevelopType.toDevelopType( developtype_string_value );
      }
      improveDesc = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      pastDesc = (java.lang.String) input.readObject();
      java.lang.String pastType_string_value = (java.lang.String) input.readObject();
      try { 
         pastType = (e3ps.project.PastType) wt.fc.EnumeratedTypeUtil.toEnumeratedType( pastType_string_value );
      } catch( wt.util.WTInvalidParameterException e ) {
         // Old Format
         pastType = e3ps.project.PastType.toPastType( pastType_string_value );
      }
      projectReference = (wt.fc.ObjectReference) input.readObject();
      reviewDesc = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( ReviewDevelop thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1239443868264035787L( input, readSerialVersionUID, superDone );
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
