package e3ps.groupware.board;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ManualBoard extends e3ps.groupware.board.HelpBoard implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.board.boardResource";
   static final java.lang.String CLASSNAME = ManualBoard.class.getName();

   /**
    * @see e3ps.groupware.board.ManualBoard
    */
   public static final java.lang.String CLASSIFICATION = "classification";
   static int CLASSIFICATION_UPPER_LIMIT = -1;
   java.lang.String classification;
   /**
    * @see e3ps.groupware.board.ManualBoard
    */
   public java.lang.String getClassification() {
      return classification;
   }
   /**
    * @see e3ps.groupware.board.ManualBoard
    */
   public void setClassification(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      classificationValidate(classification);
      this.classification = classification;
   }
   void classificationValidate(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      if (CLASSIFICATION_UPPER_LIMIT < 1) {
         try { CLASSIFICATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classification").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASSIFICATION_UPPER_LIMIT = 10; }
      }
      if (classification != null && !wt.fc.PersistenceHelper.checkStoredLength(classification.toString(), CLASSIFICATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classification"), java.lang.String.valueOf(java.lang.Math.min(CLASSIFICATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classification", this.classification, classification));
   }

   /**
    * @see e3ps.groupware.board.ManualBoard
    */
   public static final java.lang.String URL = "url";
   static int URL_UPPER_LIMIT = -1;
   java.lang.String url;
   /**
    * @see e3ps.groupware.board.ManualBoard
    */
   public java.lang.String getUrl() {
      return url;
   }
   /**
    * @see e3ps.groupware.board.ManualBoard
    */
   public void setUrl(java.lang.String url) throws wt.util.WTPropertyVetoException {
      urlValidate(url);
      this.url = url;
   }
   void urlValidate(java.lang.String url) throws wt.util.WTPropertyVetoException {
      if (URL_UPPER_LIMIT < 1) {
         try { URL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("url").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { URL_UPPER_LIMIT = 1000; }
      }
      if (url != null && !wt.fc.PersistenceHelper.checkStoredLength(url.toString(), URL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "url"), java.lang.String.valueOf(java.lang.Math.min(URL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "url", this.url, url));
   }

   /**
    * ?з?
    *
    * @see e3ps.groupware.board.ManualBoard
    */
   public static final java.lang.String CLASSIFICATION2 = "classification2";
   static int CLASSIFICATION2_UPPER_LIMIT = -1;
   java.lang.String classification2;
   /**
    * ?з?
    *
    * @see e3ps.groupware.board.ManualBoard
    */
   public java.lang.String getClassification2() {
      return classification2;
   }
   /**
    * ?з?
    *
    * @see e3ps.groupware.board.ManualBoard
    */
   public void setClassification2(java.lang.String classification2) throws wt.util.WTPropertyVetoException {
      classification2Validate(classification2);
      this.classification2 = classification2;
   }
   void classification2Validate(java.lang.String classification2) throws wt.util.WTPropertyVetoException {
      if (CLASSIFICATION2_UPPER_LIMIT < 1) {
         try { CLASSIFICATION2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classification2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASSIFICATION2_UPPER_LIMIT = 200; }
      }
      if (classification2 != null && !wt.fc.PersistenceHelper.checkStoredLength(classification2.toString(), CLASSIFICATION2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classification2"), java.lang.String.valueOf(java.lang.Math.min(CLASSIFICATION2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classification2", this.classification2, classification2));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1518606881789570657L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( classification );
      output.writeObject( classification2 );
      output.writeObject( url );
   }

   protected void super_writeExternal_ManualBoard(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.board.ManualBoard) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ManualBoard(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "classification", classification );
      output.setString( "classification2", classification2 );
      output.setString( "url", url );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      classification = input.getString( "classification" );
      classification2 = input.getString( "classification2" );
      url = input.getString( "url" );
   }

   boolean readVersion1518606881789570657L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      classification = (java.lang.String) input.readObject();
      classification2 = (java.lang.String) input.readObject();
      url = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ManualBoard thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1518606881789570657L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ManualBoard( _ManualBoard thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
