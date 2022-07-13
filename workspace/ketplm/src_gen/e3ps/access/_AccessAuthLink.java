package e3ps.access;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AccessAuthLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.access.accessResource";
   static final java.lang.String CLASSNAME = AccessAuthLink.class.getName();

   /**
    * @see e3ps.access.AccessAuthLink
    */
   public static final java.lang.String IS_FULL = "isFull";
   boolean isFull = false;
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public boolean isIsFull() {
      return isFull;
   }
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public void setIsFull(boolean isFull) throws wt.util.WTPropertyVetoException {
      isFullValidate(isFull);
      this.isFull = isFull;
   }
   void isFullValidate(boolean isFull) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.access.AccessAuthLink
    */
   public static final java.lang.String IS_WRITE = "isWrite";
   boolean isWrite = false;
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public boolean isIsWrite() {
      return isWrite;
   }
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public void setIsWrite(boolean isWrite) throws wt.util.WTPropertyVetoException {
      isWriteValidate(isWrite);
      this.isWrite = isWrite;
   }
   void isWriteValidate(boolean isWrite) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.access.AccessAuthLink
    */
   public static final java.lang.String IS_MODIFY = "isModify";
   boolean isModify = false;
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public boolean isIsModify() {
      return isModify;
   }
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public void setIsModify(boolean isModify) throws wt.util.WTPropertyVetoException {
      isModifyValidate(isModify);
      this.isModify = isModify;
   }
   void isModifyValidate(boolean isModify) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.access.AccessAuthLink
    */
   public static final java.lang.String IS_DELETE = "isDelete";
   boolean isDelete = false;
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public boolean isIsDelete() {
      return isDelete;
   }
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public void setIsDelete(boolean isDelete) throws wt.util.WTPropertyVetoException {
      isDeleteValidate(isDelete);
      this.isDelete = isDelete;
   }
   void isDeleteValidate(boolean isDelete) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.access.AccessAuthLink
    */
   public static final java.lang.String IS_READ = "isRead";
   boolean isRead = false;
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public boolean isIsRead() {
      return isRead;
   }
   /**
    * @see e3ps.access.AccessAuthLink
    */
   public void setIsRead(boolean isRead) throws wt.util.WTPropertyVetoException {
      isReadValidate(isRead);
      this.isRead = isRead;
   }
   void isReadValidate(boolean isRead) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = 1906083872537547856L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeBoolean( isDelete );
      output.writeBoolean( isFull );
      output.writeBoolean( isModify );
      output.writeBoolean( isRead );
      output.writeBoolean( isWrite );
   }

   protected void super_writeExternal_AccessAuthLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.access.AccessAuthLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_AccessAuthLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setBoolean( "isDelete", isDelete );
      output.setBoolean( "isFull", isFull );
      output.setBoolean( "isModify", isModify );
      output.setBoolean( "isRead", isRead );
      output.setBoolean( "isWrite", isWrite );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      isDelete = input.getBoolean( "isDelete" );
      isFull = input.getBoolean( "isFull" );
      isModify = input.getBoolean( "isModify" );
      isRead = input.getBoolean( "isRead" );
      isWrite = input.getBoolean( "isWrite" );
   }

   boolean readVersion1906083872537547856L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      isDelete = input.readBoolean();
      isFull = input.readBoolean();
      isModify = input.readBoolean();
      isRead = input.readBoolean();
      isWrite = input.readBoolean();
      return true;
   }

   protected boolean readVersion( AccessAuthLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1906083872537547856L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_AccessAuthLink( _AccessAuthLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
