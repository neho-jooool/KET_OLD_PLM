package ext.ket.issue.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETIssueMeetingList extends wt.enterprise.Managed implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.issue.entity.entityResource";
   static final java.lang.String CLASSNAME = KETIssueMeetingList.class.getName();

   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public static final java.lang.String SORT_NO = "sortNo";
   java.lang.Integer sortNo;
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public java.lang.Integer getSortNo() {
      return sortNo;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public void setSortNo(java.lang.Integer sortNo) throws wt.util.WTPropertyVetoException {
      sortNoValidate(sortNo);
      this.sortNo = sortNo;
   }
   void sortNoValidate(java.lang.Integer sortNo) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public static final java.lang.String IS_FILE = "isFile";
   boolean isFile = false;
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public boolean isIsFile() {
      return isFile;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public void setIsFile(boolean isFile) throws wt.util.WTPropertyVetoException {
      isFileValidate(isFile);
      this.isFile = isFile;
   }
   void isFileValidate(boolean isFile) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public static final java.lang.String MEETING_HEADER = "meetingHeader";
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public static final java.lang.String MEETING_HEADER_REFERENCE = "meetingHeaderReference";
   wt.fc.ObjectReference meetingHeaderReference;
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public ext.ket.issue.entity.KETIssueMeetingHeader getMeetingHeader() {
      return (meetingHeaderReference != null) ? (ext.ket.issue.entity.KETIssueMeetingHeader) meetingHeaderReference.getObject() : null;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public wt.fc.ObjectReference getMeetingHeaderReference() {
      return meetingHeaderReference;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public void setMeetingHeader(ext.ket.issue.entity.KETIssueMeetingHeader the_meetingHeader) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMeetingHeaderReference(the_meetingHeader == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.issue.entity.KETIssueMeetingHeader) the_meetingHeader));
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public void setMeetingHeaderReference(wt.fc.ObjectReference the_meetingHeaderReference) throws wt.util.WTPropertyVetoException {
      meetingHeaderReferenceValidate(the_meetingHeaderReference);
      meetingHeaderReference = (wt.fc.ObjectReference) the_meetingHeaderReference;
   }
   void meetingHeaderReferenceValidate(wt.fc.ObjectReference the_meetingHeaderReference) throws wt.util.WTPropertyVetoException {
      if (the_meetingHeaderReference == null || the_meetingHeaderReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingHeaderReference") },
               new java.beans.PropertyChangeEvent(this, "meetingHeaderReference", this.meetingHeaderReference, meetingHeaderReference));
      if (the_meetingHeaderReference != null && the_meetingHeaderReference.getReferencedClass() != null &&
            !ext.ket.issue.entity.KETIssueMeetingHeader.class.isAssignableFrom(the_meetingHeaderReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "meetingHeaderReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "meetingHeaderReference", this.meetingHeaderReference, meetingHeaderReference));
   }

   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public static final java.lang.String ISSUEMASTER = "issuemaster";
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public static final java.lang.String ISSUEMASTER_REFERENCE = "issuemasterReference";
   wt.fc.ObjectReference issuemasterReference;
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public ext.ket.issue.entity.KETIssueMaster getIssuemaster() {
      return (issuemasterReference != null) ? (ext.ket.issue.entity.KETIssueMaster) issuemasterReference.getObject() : null;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public wt.fc.ObjectReference getIssuemasterReference() {
      return issuemasterReference;
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public void setIssuemaster(ext.ket.issue.entity.KETIssueMaster the_issuemaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setIssuemasterReference(the_issuemaster == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.issue.entity.KETIssueMaster) the_issuemaster));
   }
   /**
    * @see ext.ket.issue.entity.KETIssueMeetingList
    */
   public void setIssuemasterReference(wt.fc.ObjectReference the_issuemasterReference) throws wt.util.WTPropertyVetoException {
      issuemasterReferenceValidate(the_issuemasterReference);
      issuemasterReference = (wt.fc.ObjectReference) the_issuemasterReference;
   }
   void issuemasterReferenceValidate(wt.fc.ObjectReference the_issuemasterReference) throws wt.util.WTPropertyVetoException {
      if (the_issuemasterReference != null && the_issuemasterReference.getReferencedClass() != null &&
            !ext.ket.issue.entity.KETIssueMaster.class.isAssignableFrom(the_issuemasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "issuemasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "issuemasterReference", this.issuemasterReference, issuemasterReference));
   }

   java.util.Vector contentVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getContentVector() {
      return contentVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setContentVector(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
      contentVectorValidate(contentVector);
      this.contentVector = contentVector;
   }
   void contentVectorValidate(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
   }

   boolean hasContents;
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public boolean isHasContents() {
      return hasContents;
   }
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public void setHasContents(boolean hasContents) throws wt.util.WTPropertyVetoException {
      hasContentsValidate(hasContents);
      this.hasContents = hasContents;
   }
   void hasContentsValidate(boolean hasContents) throws wt.util.WTPropertyVetoException {
   }

   wt.content.HttpContentOperation operation;
   /**
    * @see wt.content.ContentHolder
    */
   public wt.content.HttpContentOperation getOperation() {
      return operation;
   }
   /**
    * @see wt.content.ContentHolder
    */
   public void setOperation(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
      operationValidate(operation);
      this.operation = operation;
   }
   void operationValidate(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
   }

   java.util.Vector httpVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getHttpVector() {
      return httpVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setHttpVector(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
      httpVectorValidate(httpVector);
      this.httpVector = httpVector;
   }
   void httpVectorValidate(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -4200892149886966164L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeBoolean( isFile );
      output.writeObject( issuemasterReference );
      output.writeObject( meetingHeaderReference );
      output.writeObject( sortNo );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_KETIssueMeetingList(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.issue.entity.KETIssueMeetingList) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETIssueMeetingList(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setBoolean( "isFile", isFile );
      output.writeObject( "issuemasterReference", issuemasterReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "meetingHeaderReference", meetingHeaderReference, wt.fc.ObjectReference.class, true );
      output.setIntObject( "sortNo", sortNo );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      isFile = input.getBoolean( "isFile" );
      issuemasterReference = (wt.fc.ObjectReference) input.readObject( "issuemasterReference", issuemasterReference, wt.fc.ObjectReference.class, true );
      meetingHeaderReference = (wt.fc.ObjectReference) input.readObject( "meetingHeaderReference", meetingHeaderReference, wt.fc.ObjectReference.class, true );
      sortNo = input.getIntObject( "sortNo" );
   }

   boolean readVersion_4200892149886966164L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      isFile = input.readBoolean();
      issuemasterReference = (wt.fc.ObjectReference) input.readObject();
      meetingHeaderReference = (wt.fc.ObjectReference) input.readObject();
      sortNo = (java.lang.Integer) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETIssueMeetingList thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4200892149886966164L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETIssueMeetingList( _KETIssueMeetingList thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
