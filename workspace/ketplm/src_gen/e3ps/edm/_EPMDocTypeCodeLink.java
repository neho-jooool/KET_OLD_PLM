package e3ps.edm;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _EPMDocTypeCodeLink extends wt.vc.ObjectToVersionLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.edm.edmResource";
   static final java.lang.String CLASSNAME = EPMDocTypeCodeLink.class.getName();

   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public static final java.lang.String CODE_TYPE = "codeType";
   static int CODE_TYPE_UPPER_LIMIT = -1;
   java.lang.String codeType;
   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public java.lang.String getCodeType() {
      return codeType;
   }
   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public void setCodeType(java.lang.String codeType) throws wt.util.WTPropertyVetoException {
      codeTypeValidate(codeType);
      this.codeType = codeType;
   }
   void codeTypeValidate(java.lang.String codeType) throws wt.util.WTPropertyVetoException {
      if (CODE_TYPE_UPPER_LIMIT < 1) {
         try { CODE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("codeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CODE_TYPE_UPPER_LIMIT = 200; }
      }
      if (codeType != null && !wt.fc.PersistenceHelper.checkStoredLength(codeType.toString(), CODE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "codeType"), java.lang.String.valueOf(java.lang.Math.min(CODE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "codeType", this.codeType, codeType));
   }

   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public static final java.lang.String TYPE_CODE_ROLE = "typeCode";
   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public e3ps.common.code.NumberCode getTypeCode() {
      return (e3ps.common.code.NumberCode) getRoleAObject();
   }
   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public void setTypeCode(e3ps.common.code.NumberCode the_typeCode) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_typeCode);
   }

   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public static final java.lang.String TYPE_DOC_ROLE = "typeDoc";
   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public wt.epm.EPMDocument getTypeDoc() {
      return (wt.epm.EPMDocument) getRoleBObject();
   }
   /**
    * @see e3ps.edm.EPMDocTypeCodeLink
    */
   public void setTypeDoc(wt.epm.EPMDocument the_typeDoc) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_typeDoc);
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

   public static final long EXTERNALIZATION_VERSION_UID = -8857389320261278973L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( codeType );
   }

   protected void super_writeExternal_EPMDocTypeCodeLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.edm.EPMDocTypeCodeLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_EPMDocTypeCodeLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "codeType", codeType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      codeType = input.getString( "codeType" );
   }

   boolean readVersion_8857389320261278973L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      codeType = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( EPMDocTypeCodeLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_8857389320261278973L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_EPMDocTypeCodeLink( _EPMDocTypeCodeLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
