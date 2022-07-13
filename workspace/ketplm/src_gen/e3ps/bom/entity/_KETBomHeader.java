package e3ps.bom.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETBomHeader extends e3ps.bom.entity.KETBomEcoHeader implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.bom.entity.entityResource";
   static final java.lang.String CLASSNAME = KETBomHeader.class.getName();

   /**
    * @see e3ps.bom.entity.KETBomHeader
    */
   public static final java.lang.String NEW_BOMCODE = "newBOMCode";
   static int NEW_BOMCODE_UPPER_LIMIT = -1;
   java.lang.String newBOMCode;
   /**
    * @see e3ps.bom.entity.KETBomHeader
    */
   public java.lang.String getNewBOMCode() {
      return newBOMCode;
   }
   /**
    * @see e3ps.bom.entity.KETBomHeader
    */
   public void setNewBOMCode(java.lang.String newBOMCode) throws wt.util.WTPropertyVetoException {
      newBOMCodeValidate(newBOMCode);
      this.newBOMCode = newBOMCode;
   }
   void newBOMCodeValidate(java.lang.String newBOMCode) throws wt.util.WTPropertyVetoException {
      if (NEW_BOMCODE_UPPER_LIMIT < 1) {
         try { NEW_BOMCODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("newBOMCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NEW_BOMCODE_UPPER_LIMIT = 200; }
      }
      if (newBOMCode != null && !wt.fc.PersistenceHelper.checkStoredLength(newBOMCode.toString(), NEW_BOMCODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "newBOMCode"), java.lang.String.valueOf(java.lang.Math.min(NEW_BOMCODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "newBOMCode", this.newBOMCode, newBOMCode));
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

   public static final long EXTERNALIZATION_VERSION_UID = -7978126796226772823L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( newBOMCode );
   }

   protected void super_writeExternal_KETBomHeader(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.bom.entity.KETBomHeader) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETBomHeader(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "newBOMCode", newBOMCode );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      newBOMCode = input.getString( "newBOMCode" );
   }

   boolean readVersion_7978126796226772823L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      newBOMCode = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETBomHeader thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7978126796226772823L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETBomHeader( _KETBomHeader thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
