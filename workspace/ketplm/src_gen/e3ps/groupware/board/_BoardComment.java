package e3ps.groupware.board;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _BoardComment implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.board.boardResource";
   static final java.lang.String CLASSNAME = BoardComment.class.getName();

   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public static final java.lang.String CONTENTS = "contents";
   java.lang.Object contents;
   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public java.lang.Object getContents() {
      return contents;
   }
   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public void setContents(java.lang.Object contents) throws wt.util.WTPropertyVetoException {
      contentsValidate(contents);
      this.contents = contents;
   }
   void contentsValidate(java.lang.Object contents) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public static final java.lang.String BOARD = "board";
   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public static final java.lang.String BOARD_REFERENCE = "boardReference";
   wt.fc.ObjectReference boardReference;
   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public e3ps.groupware.board.Board getBoard() {
      return (boardReference != null) ? (e3ps.groupware.board.Board) boardReference.getObject() : null;
   }
   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public wt.fc.ObjectReference getBoardReference() {
      return boardReference;
   }
   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public void setBoard(e3ps.groupware.board.Board the_board) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setBoardReference(the_board == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.board.Board) the_board));
   }
   /**
    * @see e3ps.groupware.board.BoardComment
    */
   public void setBoardReference(wt.fc.ObjectReference the_boardReference) throws wt.util.WTPropertyVetoException {
      boardReferenceValidate(the_boardReference);
      boardReference = (wt.fc.ObjectReference) the_boardReference;
   }
   void boardReferenceValidate(wt.fc.ObjectReference the_boardReference) throws wt.util.WTPropertyVetoException {
      if (the_boardReference == null || the_boardReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "boardReference") },
               new java.beans.PropertyChangeEvent(this, "boardReference", this.boardReference, boardReference));
      if (the_boardReference != null && the_boardReference.getReferencedClass() != null &&
            !e3ps.groupware.board.Board.class.isAssignableFrom(the_boardReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "boardReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "boardReference", this.boardReference, boardReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 3771976305647371332L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( boardReference );
      output.writeObject( contents );
      output.writeObject( owner );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.board.BoardComment) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "boardReference", boardReference, wt.fc.ObjectReference.class, true );
      output.setObject( "contents", contents );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      boardReference = (wt.fc.ObjectReference) input.readObject( "boardReference", boardReference, wt.fc.ObjectReference.class, true );
      contents = (java.lang.Object) input.getObject( "contents" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion3771976305647371332L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boardReference = (wt.fc.ObjectReference) input.readObject();
      contents = (java.lang.Object) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( BoardComment thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion3771976305647371332L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == 3771976305647371332L )
         return ((BoardComment) this).readVersion3771976305647371332L( input, readSerialVersionUID, superDone );
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
