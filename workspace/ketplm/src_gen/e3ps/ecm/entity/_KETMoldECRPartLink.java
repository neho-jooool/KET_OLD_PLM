package e3ps.ecm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETMoldECRPartLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ecm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETMoldECRPartLink.class.getName();

   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public static final java.lang.String CHANGE_REQ_COMMENT = "changeReqComment";
   static int CHANGE_REQ_COMMENT_UPPER_LIMIT = -1;
   java.lang.String changeReqComment;
   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public java.lang.String getChangeReqComment() {
      return changeReqComment;
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public void setChangeReqComment(java.lang.String changeReqComment) throws wt.util.WTPropertyVetoException {
      changeReqCommentValidate(changeReqComment);
      this.changeReqComment = changeReqComment;
   }
   void changeReqCommentValidate(java.lang.String changeReqComment) throws wt.util.WTPropertyVetoException {
      if (CHANGE_REQ_COMMENT_UPPER_LIMIT < 1) {
         try { CHANGE_REQ_COMMENT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("changeReqComment").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHANGE_REQ_COMMENT_UPPER_LIMIT = 200; }
      }
      if (changeReqComment != null && !wt.fc.PersistenceHelper.checkStoredLength(changeReqComment.toString(), CHANGE_REQ_COMMENT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "changeReqComment"), java.lang.String.valueOf(java.lang.Math.min(CHANGE_REQ_COMMENT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "changeReqComment", this.changeReqComment, changeReqComment));
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public static final java.lang.String PART_ROLE = "part";
   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public wt.part.WTPart getPart() {
      return (wt.part.WTPart) getRoleAObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public void setPart(wt.part.WTPart the_part) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_part);
   }

   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public static final java.lang.String MOLD_ECRROLE = "moldECR";
   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public e3ps.ecm.entity.KETMoldChangeRequest getMoldECR() {
      return (e3ps.ecm.entity.KETMoldChangeRequest) getRoleBObject();
   }
   /**
    * @see e3ps.ecm.entity.KETMoldECRPartLink
    */
   public void setMoldECR(e3ps.ecm.entity.KETMoldChangeRequest the_moldECR) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_moldECR);
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

   public static final long EXTERNALIZATION_VERSION_UID = 2505496302304654635L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( changeReqComment );
   }

   protected void super_writeExternal_KETMoldECRPartLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ecm.entity.KETMoldECRPartLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETMoldECRPartLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "changeReqComment", changeReqComment );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      changeReqComment = input.getString( "changeReqComment" );
   }

   boolean readVersion2505496302304654635L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      changeReqComment = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETMoldECRPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2505496302304654635L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETMoldECRPartLink( _KETMoldECRPartLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
