package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _GateCheckSheet extends wt.fc.WTObject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = GateCheckSheet.class.getName();

   /**
    * Check Sheet Name
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String CHECK_SHEET_NAME = "checkSheetName";
   static int CHECK_SHEET_NAME_UPPER_LIMIT = -1;
   java.lang.String checkSheetName;
   /**
    * Check Sheet Name
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public java.lang.String getCheckSheetName() {
      return checkSheetName;
   }
   /**
    * Check Sheet Name
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setCheckSheetName(java.lang.String checkSheetName) throws wt.util.WTPropertyVetoException {
      checkSheetNameValidate(checkSheetName);
      this.checkSheetName = checkSheetName;
   }
   void checkSheetNameValidate(java.lang.String checkSheetName) throws wt.util.WTPropertyVetoException {
      if (CHECK_SHEET_NAME_UPPER_LIMIT < 1) {
         try { CHECK_SHEET_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkSheetName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_SHEET_NAME_UPPER_LIMIT = 200; }
      }
      if (checkSheetName != null && !wt.fc.PersistenceHelper.checkStoredLength(checkSheetName.toString(), CHECK_SHEET_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkSheetName"), java.lang.String.valueOf(java.lang.Math.min(CHECK_SHEET_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkSheetName", this.checkSheetName, checkSheetName));
   }

   /**
    * 달성기준
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String ACHIEVE_BASE = "achieveBase";
   static int ACHIEVE_BASE_UPPER_LIMIT = -1;
   java.lang.String achieveBase;
   /**
    * 달성기준
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public java.lang.String getAchieveBase() {
      return achieveBase;
   }
   /**
    * 달성기준
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setAchieveBase(java.lang.String achieveBase) throws wt.util.WTPropertyVetoException {
      achieveBaseValidate(achieveBase);
      this.achieveBase = achieveBase;
   }
   void achieveBaseValidate(java.lang.String achieveBase) throws wt.util.WTPropertyVetoException {
      if (ACHIEVE_BASE_UPPER_LIMIT < 1) {
         try { ACHIEVE_BASE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("achieveBase").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACHIEVE_BASE_UPPER_LIMIT = 200; }
      }
      if (achieveBase != null && !wt.fc.PersistenceHelper.checkStoredLength(achieveBase.toString(), ACHIEVE_BASE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "achieveBase"), java.lang.String.valueOf(java.lang.Math.min(ACHIEVE_BASE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "achieveBase", this.achieveBase, achieveBase));
   }

   /**
    * 단위
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String UNIT = "unit";
   static int UNIT_UPPER_LIMIT = -1;
   java.lang.String unit;
   /**
    * 단위
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public java.lang.String getUnit() {
      return unit;
   }
   /**
    * 단위
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setUnit(java.lang.String unit) throws wt.util.WTPropertyVetoException {
      unitValidate(unit);
      this.unit = unit;
   }
   void unitValidate(java.lang.String unit) throws wt.util.WTPropertyVetoException {
      if (UNIT_UPPER_LIMIT < 1) {
         try { UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("unit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { UNIT_UPPER_LIMIT = 200; }
      }
      if (unit != null && !wt.fc.PersistenceHelper.checkStoredLength(unit.toString(), UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "unit"), java.lang.String.valueOf(java.lang.Math.min(UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "unit", this.unit, unit));
   }

   /**
    * 기준
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String CRITERIA = "criteria";
   static int CRITERIA_UPPER_LIMIT = -1;
   java.lang.String criteria;
   /**
    * 기준
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public java.lang.String getCriteria() {
      return criteria;
   }
   /**
    * 기준
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setCriteria(java.lang.String criteria) throws wt.util.WTPropertyVetoException {
      criteriaValidate(criteria);
      this.criteria = criteria;
   }
   void criteriaValidate(java.lang.String criteria) throws wt.util.WTPropertyVetoException {
      if (CRITERIA_UPPER_LIMIT < 1) {
         try { CRITERIA_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("criteria").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CRITERIA_UPPER_LIMIT = 200; }
      }
      if (criteria != null && !wt.fc.PersistenceHelper.checkStoredLength(criteria.toString(), CRITERIA_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "criteria"), java.lang.String.valueOf(java.lang.Math.min(CRITERIA_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "criteria", this.criteria, criteria));
   }

   /**
    * 정렬순서
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String ORDER_NO = "orderNo";
   int orderNo;
   /**
    * 정렬순서
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public int getOrderNo() {
      return orderNo;
   }
   /**
    * 정렬순서
    *
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setOrderNo(int orderNo) throws wt.util.WTPropertyVetoException {
      orderNoValidate(orderNo);
      this.orderNo = orderNo;
   }
   void orderNoValidate(int orderNo) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String ATTR = "attr";
   ext.ket.project.gate.entity.GateAttribute attr;
   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public ext.ket.project.gate.entity.GateAttribute getAttr() {
      return attr;
   }
   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setAttr(ext.ket.project.gate.entity.GateAttribute attr) throws wt.util.WTPropertyVetoException {
      attrValidate(attr);
      this.attr = attr;
   }
   void attrValidate(ext.ket.project.gate.entity.GateAttribute attr) throws wt.util.WTPropertyVetoException {
      if (attr == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr") },
               new java.beans.PropertyChangeEvent(this, "attr", this.attr, attr));
   }

   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String TARGET_TYPE = "targetType";
   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public static final java.lang.String TARGET_TYPE_REFERENCE = "targetTypeReference";
   wt.fc.ObjectReference targetTypeReference;
   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public e3ps.common.code.NumberCode getTargetType() {
      return (targetTypeReference != null) ? (e3ps.common.code.NumberCode) targetTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public wt.fc.ObjectReference getTargetTypeReference() {
      return targetTypeReference;
   }
   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setTargetType(e3ps.common.code.NumberCode the_targetType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTargetTypeReference(the_targetType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_targetType));
   }
   /**
    * @see ext.ket.project.gate.entity.GateCheckSheet
    */
   public void setTargetTypeReference(wt.fc.ObjectReference the_targetTypeReference) throws wt.util.WTPropertyVetoException {
      targetTypeReferenceValidate(the_targetTypeReference);
      targetTypeReference = (wt.fc.ObjectReference) the_targetTypeReference;
   }
   void targetTypeReferenceValidate(wt.fc.ObjectReference the_targetTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_targetTypeReference == null || the_targetTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetTypeReference") },
               new java.beans.PropertyChangeEvent(this, "targetTypeReference", this.targetTypeReference, targetTypeReference));
      if (the_targetTypeReference != null && the_targetTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_targetTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "targetTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "targetTypeReference", this.targetTypeReference, targetTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 5852256403742625449L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( achieveBase );
      output.writeObject( attr );
      output.writeObject( checkSheetName );
      output.writeObject( criteria );
      output.writeInt( orderNo );
      output.writeObject( targetTypeReference );
      output.writeObject( unit );
   }

   protected void super_writeExternal_GateCheckSheet(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.GateCheckSheet) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_GateCheckSheet(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "achieveBase", achieveBase );
      output.writeObject( "attr", attr, ext.ket.project.gate.entity.GateAttribute.class, true );
      output.setString( "checkSheetName", checkSheetName );
      output.setString( "criteria", criteria );
      output.setInt( "orderNo", orderNo );
      output.writeObject( "targetTypeReference", targetTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "unit", unit );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      achieveBase = input.getString( "achieveBase" );
      attr = (ext.ket.project.gate.entity.GateAttribute) input.readObject( "attr", attr, ext.ket.project.gate.entity.GateAttribute.class, true );
      checkSheetName = input.getString( "checkSheetName" );
      criteria = input.getString( "criteria" );
      orderNo = input.getInt( "orderNo" );
      targetTypeReference = (wt.fc.ObjectReference) input.readObject( "targetTypeReference", targetTypeReference, wt.fc.ObjectReference.class, true );
      unit = input.getString( "unit" );
   }

   boolean readVersion5852256403742625449L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      achieveBase = (java.lang.String) input.readObject();
      attr = (ext.ket.project.gate.entity.GateAttribute) input.readObject();
      checkSheetName = (java.lang.String) input.readObject();
      criteria = (java.lang.String) input.readObject();
      orderNo = input.readInt();
      targetTypeReference = (wt.fc.ObjectReference) input.readObject();
      unit = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( GateCheckSheet thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5852256403742625449L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_GateCheckSheet( _GateCheckSheet thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
