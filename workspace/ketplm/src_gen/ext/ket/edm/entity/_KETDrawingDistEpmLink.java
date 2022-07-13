package ext.ket.edm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDrawingDistEpmLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.edm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDrawingDistEpmLink.class.getName();

   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public static final java.lang.String SHEET_TYPE = "sheetType";
   static int SHEET_TYPE_UPPER_LIMIT = -1;
   java.lang.String sheetType;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public java.lang.String getSheetType() {
      return sheetType;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public void setSheetType(java.lang.String sheetType) throws wt.util.WTPropertyVetoException {
      sheetTypeValidate(sheetType);
      this.sheetType = sheetType;
   }
   void sheetTypeValidate(java.lang.String sheetType) throws wt.util.WTPropertyVetoException {
      if (SHEET_TYPE_UPPER_LIMIT < 1) {
         try { SHEET_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_TYPE_UPPER_LIMIT = 200; }
      }
      if (sheetType != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetType.toString(), SHEET_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetType"), java.lang.String.valueOf(java.lang.Math.min(SHEET_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetType", this.sheetType, sheetType));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public static final java.lang.String REF_PART = "refPart";
   static int REF_PART_UPPER_LIMIT = -1;
   java.lang.String refPart;
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public java.lang.String getRefPart() {
      return refPart;
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public void setRefPart(java.lang.String refPart) throws wt.util.WTPropertyVetoException {
      refPartValidate(refPart);
      this.refPart = refPart;
   }
   void refPartValidate(java.lang.String refPart) throws wt.util.WTPropertyVetoException {
      if (REF_PART_UPPER_LIMIT < 1) {
         try { REF_PART_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("refPart").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REF_PART_UPPER_LIMIT = 200; }
      }
      if (refPart != null && !wt.fc.PersistenceHelper.checkStoredLength(refPart.toString(), REF_PART_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "refPart"), java.lang.String.valueOf(java.lang.Math.min(REF_PART_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "refPart", this.refPart, refPart));
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public static final java.lang.String DIST_EPM_ROLE = "distEpm";
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public wt.epm.EPMDocument getDistEpm() {
      return (wt.epm.EPMDocument) getRoleAObject();
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public void setDistEpm(wt.epm.EPMDocument the_distEpm) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_distEpm);
   }

   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public static final java.lang.String DIST_REQ_ROLE = "distReq";
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public ext.ket.edm.entity.KETDrawingDistRequest getDistReq() {
      return (ext.ket.edm.entity.KETDrawingDistRequest) getRoleBObject();
   }
   /**
    * @see ext.ket.edm.entity.KETDrawingDistEpmLink
    */
   public void setDistReq(ext.ket.edm.entity.KETDrawingDistRequest the_distReq) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_distReq);
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

   public static final long EXTERNALIZATION_VERSION_UID = -2105218429970094081L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( refPart );
      output.writeObject( sheetType );
   }

   protected void super_writeExternal_KETDrawingDistEpmLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.edm.entity.KETDrawingDistEpmLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDrawingDistEpmLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "refPart", refPart );
      output.setString( "sheetType", sheetType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      refPart = input.getString( "refPart" );
      sheetType = input.getString( "sheetType" );
   }

   boolean readVersion_2105218429970094081L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      refPart = (java.lang.String) input.readObject();
      sheetType = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETDrawingDistEpmLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2105218429970094081L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDrawingDistEpmLink( _KETDrawingDistEpmLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
