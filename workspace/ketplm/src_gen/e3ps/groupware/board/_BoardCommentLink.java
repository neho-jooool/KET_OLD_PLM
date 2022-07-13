package e3ps.groupware.board;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _BoardCommentLink extends wt.fc.ForeignKeyLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.board.boardResource";
   static final java.lang.String CLASSNAME = BoardCommentLink.class.getName();

   /**
    * @see e3ps.groupware.board.BoardCommentLink
    */
   public static final java.lang.String COMMENT_ROLE = "comment";
   /**
    * @see e3ps.groupware.board.BoardCommentLink
    */
   public e3ps.groupware.board.BoardComment getComment() {
      return (e3ps.groupware.board.BoardComment) getRoleAObject();
   }
   /**
    * @see e3ps.groupware.board.BoardCommentLink
    */
   public void setComment(e3ps.groupware.board.BoardComment the_comment) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_comment);
   }

   /**
    * @see e3ps.groupware.board.BoardCommentLink
    */
   public static final java.lang.String BOARD_ROLE = "board";
   /**
    * @see e3ps.groupware.board.BoardCommentLink
    */
   public e3ps.groupware.board.Board getBoard() {
      return (e3ps.groupware.board.Board) getRoleBObject();
   }
   /**
    * @see e3ps.groupware.board.BoardCommentLink
    */
   public void setBoard(e3ps.groupware.board.Board the_board) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_board);
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

   protected void super_writeExternal_BoardCommentLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.board.BoardCommentLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_BoardCommentLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
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

   protected boolean readVersion( BoardCommentLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2454203546147727912L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_BoardCommentLink( _BoardCommentLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
