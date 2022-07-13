package ext.ket.project.atft.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETATFTMainSheet extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.atft.entity.entityResource";
   static final java.lang.String CLASSNAME = KETATFTMainSheet.class.getName();

   /**
    * P1/P2 구분
    *
    * @see ext.ket.project.atft.entity.KETATFTMainSheet
    */
   public static final java.lang.String SHEET_DIVISION = "sheetDivision";
   static int SHEET_DIVISION_UPPER_LIMIT = -1;
   java.lang.String sheetDivision;
   /**
    * P1/P2 구분
    *
    * @see ext.ket.project.atft.entity.KETATFTMainSheet
    */
   public java.lang.String getSheetDivision() {
      return sheetDivision;
   }
   /**
    * P1/P2 구분
    *
    * @see ext.ket.project.atft.entity.KETATFTMainSheet
    */
   public void setSheetDivision(java.lang.String sheetDivision) throws wt.util.WTPropertyVetoException {
      sheetDivisionValidate(sheetDivision);
      this.sheetDivision = sheetDivision;
   }
   void sheetDivisionValidate(java.lang.String sheetDivision) throws wt.util.WTPropertyVetoException {
      if (SHEET_DIVISION_UPPER_LIMIT < 1) {
         try { SHEET_DIVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetDivision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_DIVISION_UPPER_LIMIT = 200; }
      }
      if (sheetDivision != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetDivision.toString(), SHEET_DIVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetDivision"), java.lang.String.valueOf(java.lang.Math.min(SHEET_DIVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetDivision", this.sheetDivision, sheetDivision));
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

   public static final long EXTERNALIZATION_VERSION_UID = -8993372930815503621L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( sheetDivision );
   }

   protected void super_writeExternal_KETATFTMainSheet(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.atft.entity.KETATFTMainSheet) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETATFTMainSheet(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "sheetDivision", sheetDivision );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      sheetDivision = input.getString( "sheetDivision" );
   }

   boolean readVersion_8993372930815503621L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      sheetDivision = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETATFTMainSheet thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8993372930815503621L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETATFTMainSheet( _KETATFTMainSheet thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
