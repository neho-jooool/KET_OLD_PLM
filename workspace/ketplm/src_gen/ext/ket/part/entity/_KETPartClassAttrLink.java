package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartClassAttrLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartClassAttrLink.class.getName();

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String ESSENTIAL = "essential";
   boolean essential = false;
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public boolean isEssential() {
      return essential;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setEssential(boolean essential) throws wt.util.WTPropertyVetoException {
      essentialValidate(essential);
      this.essential = essential;
   }
   void essentialValidate(boolean essential) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String CHECKED = "checked";
   boolean checked = false;
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public boolean isChecked() {
      return checked;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setChecked(boolean checked) throws wt.util.WTPropertyVetoException {
      checkedValidate(checked);
      this.checked = checked;
   }
   void checkedValidate(boolean checked) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String INDEX_ROW = "indexRow";
   int indexRow;
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public int getIndexRow() {
      return indexRow;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setIndexRow(int indexRow) throws wt.util.WTPropertyVetoException {
      indexRowValidate(indexRow);
      this.indexRow = indexRow;
   }
   void indexRowValidate(int indexRow) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String INDEX_COL = "indexCol";
   int indexCol;
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public int getIndexCol() {
      return indexCol;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setIndexCol(int indexCol) throws wt.util.WTPropertyVetoException {
      indexColValidate(indexCol);
      this.indexCol = indexCol;
   }
   void indexColValidate(int indexCol) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String HP_YN = "hpYn";
   boolean hpYn = false;
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public boolean isHpYn() {
      return hpYn;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setHpYn(boolean hpYn) throws wt.util.WTPropertyVetoException {
      hpYnValidate(hpYn);
      this.hpYn = hpYn;
   }
   void hpYnValidate(boolean hpYn) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String INDEX_SORT = "indexSort";
   int indexSort;
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public int getIndexSort() {
      return indexSort;
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setIndexSort(int indexSort) throws wt.util.WTPropertyVetoException {
      indexSortValidate(indexSort);
      this.indexSort = indexSort;
   }
   void indexSortValidate(int indexSort) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String ATTR_ROLE = "attr";
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public ext.ket.part.entity.KETPartAttribute getAttr() {
      return (ext.ket.part.entity.KETPartAttribute) getRoleAObject();
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setAttr(ext.ket.part.entity.KETPartAttribute the_attr) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_attr);
   }

   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public static final java.lang.String CLASSIFIC_ROLE = "classific";
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public ext.ket.part.entity.KETPartClassification getClassific() {
      return (ext.ket.part.entity.KETPartClassification) getRoleBObject();
   }
   /**
    * @see ext.ket.part.entity.KETPartClassAttrLink
    */
   public void setClassific(ext.ket.part.entity.KETPartClassification the_classific) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_classific);
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

   public static final long EXTERNALIZATION_VERSION_UID = 5394419925887489991L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeBoolean( checked );
      output.writeBoolean( essential );
      output.writeBoolean( hpYn );
      output.writeInt( indexCol );
      output.writeInt( indexRow );
      output.writeInt( indexSort );
   }

   protected void super_writeExternal_KETPartClassAttrLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.KETPartClassAttrLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETPartClassAttrLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setBoolean( "checked", checked );
      output.setBoolean( "essential", essential );
      output.setBoolean( "hpYn", hpYn );
      output.setInt( "indexCol", indexCol );
      output.setInt( "indexRow", indexRow );
      output.setInt( "indexSort", indexSort );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      checked = input.getBoolean( "checked" );
      essential = input.getBoolean( "essential" );
      hpYn = input.getBoolean( "hpYn" );
      indexCol = input.getInt( "indexCol" );
      indexRow = input.getInt( "indexRow" );
      indexSort = input.getInt( "indexSort" );
   }

   boolean readVersion5394419925887489991L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      checked = input.readBoolean();
      essential = input.readBoolean();
      hpYn = input.readBoolean();
      indexCol = input.readInt();
      indexRow = input.readInt();
      indexSort = input.readInt();
      return true;
   }

   protected boolean readVersion( KETPartClassAttrLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5394419925887489991L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETPartClassAttrLink( _KETPartClassAttrLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
