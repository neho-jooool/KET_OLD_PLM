package e3ps.project.moldPart;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MoldSubPart implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.moldPart.moldPartResource";
   static final java.lang.String CLASSNAME = MoldSubPart.class.getName();

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String MOLD_NO = "moldNo";
   static int MOLD_NO_UPPER_LIMIT = -1;
   java.lang.String moldNo;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getMoldNo() {
      return moldNo;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setMoldNo(java.lang.String moldNo) throws wt.util.WTPropertyVetoException {
      moldNoValidate(moldNo);
      this.moldNo = moldNo;
   }
   void moldNoValidate(java.lang.String moldNo) throws wt.util.WTPropertyVetoException {
      if (MOLD_NO_UPPER_LIMIT < 1) {
         try { MOLD_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NO_UPPER_LIMIT = 200; }
      }
      if (moldNo != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNo.toString(), MOLD_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNo"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNo", this.moldNo, moldNo));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String MOLD_NAME = "moldName";
   static int MOLD_NAME_UPPER_LIMIT = -1;
   java.lang.String moldName;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getMoldName() {
      return moldName;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setMoldName(java.lang.String moldName) throws wt.util.WTPropertyVetoException {
      moldNameValidate(moldName);
      this.moldName = moldName;
   }
   void moldNameValidate(java.lang.String moldName) throws wt.util.WTPropertyVetoException {
      if (MOLD_NAME_UPPER_LIMIT < 1) {
         try { MOLD_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NAME_UPPER_LIMIT = 3000; }
      }
      if (moldName != null && !wt.fc.PersistenceHelper.checkStoredLength(moldName.toString(), MOLD_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldName"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldName", this.moldName, moldName));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String EA = "ea";
   static int EA_UPPER_LIMIT = -1;
   java.lang.String ea;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getEa() {
      return ea;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setEa(java.lang.String ea) throws wt.util.WTPropertyVetoException {
      eaValidate(ea);
      this.ea = ea;
   }
   void eaValidate(java.lang.String ea) throws wt.util.WTPropertyVetoException {
      if (EA_UPPER_LIMIT < 1) {
         try { EA_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ea").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EA_UPPER_LIMIT = 200; }
      }
      if (ea != null && !wt.fc.PersistenceHelper.checkStoredLength(ea.toString(), EA_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ea"), java.lang.String.valueOf(java.lang.Math.min(EA_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ea", this.ea, ea));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String MATERIAL = "material";
   static int MATERIAL_UPPER_LIMIT = -1;
   java.lang.String material;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getMaterial() {
      return material;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setMaterial(java.lang.String material) throws wt.util.WTPropertyVetoException {
      materialValidate(material);
      this.material = material;
   }
   void materialValidate(java.lang.String material) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_UPPER_LIMIT < 1) {
         try { MATERIAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("material").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_UPPER_LIMIT = 200; }
      }
      if (material != null && !wt.fc.PersistenceHelper.checkStoredLength(material.toString(), MATERIAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "material"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "material", this.material, material));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String PART_CLASS = "partClass";
   static int PART_CLASS_UPPER_LIMIT = -1;
   java.lang.String partClass;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getPartClass() {
      return partClass;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setPartClass(java.lang.String partClass) throws wt.util.WTPropertyVetoException {
      partClassValidate(partClass);
      this.partClass = partClass;
   }
   void partClassValidate(java.lang.String partClass) throws wt.util.WTPropertyVetoException {
      if (PART_CLASS_UPPER_LIMIT < 1) {
         try { PART_CLASS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partClass").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_CLASS_UPPER_LIMIT = 200; }
      }
      if (partClass != null && !wt.fc.PersistenceHelper.checkStoredLength(partClass.toString(), PART_CLASS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partClass"), java.lang.String.valueOf(java.lang.Math.min(PART_CLASS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partClass", this.partClass, partClass));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String PART_TYPE = "partType";
   static int PART_TYPE_UPPER_LIMIT = -1;
   java.lang.String partType;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getPartType() {
      return partType;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setPartType(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      partTypeValidate(partType);
      this.partType = partType;
   }
   void partTypeValidate(java.lang.String partType) throws wt.util.WTPropertyVetoException {
      if (PART_TYPE_UPPER_LIMIT < 1) {
         try { PART_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_TYPE_UPPER_LIMIT = 200; }
      }
      if (partType != null && !wt.fc.PersistenceHelper.checkStoredLength(partType.toString(), PART_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partType"), java.lang.String.valueOf(java.lang.Math.min(PART_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partType", this.partType, partType));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String END_DATE = "endDate";
   java.sql.Timestamp endDate;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.sql.Timestamp getEndDate() {
      return endDate;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setEndDate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String M_TYPE = "mType";
   static int M_TYPE_UPPER_LIMIT = -1;
   java.lang.String mType;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getMType() {
      return mType;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setMType(java.lang.String mType) throws wt.util.WTPropertyVetoException {
      mTypeValidate(mType);
      this.mType = mType;
   }
   void mTypeValidate(java.lang.String mType) throws wt.util.WTPropertyVetoException {
      if (M_TYPE_UPPER_LIMIT < 1) {
         try { M_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { M_TYPE_UPPER_LIMIT = 200; }
      }
      if (mType != null && !wt.fc.PersistenceHelper.checkStoredLength(mType.toString(), M_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mType"), java.lang.String.valueOf(java.lang.Math.min(M_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mType", this.mType, mType));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String ACTION_TYPE = "actionType";
   static int ACTION_TYPE_UPPER_LIMIT = -1;
   java.lang.String actionType;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getActionType() {
      return actionType;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setActionType(java.lang.String actionType) throws wt.util.WTPropertyVetoException {
      actionTypeValidate(actionType);
      this.actionType = actionType;
   }
   void actionTypeValidate(java.lang.String actionType) throws wt.util.WTPropertyVetoException {
      if (ACTION_TYPE_UPPER_LIMIT < 1) {
         try { ACTION_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("actionType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTION_TYPE_UPPER_LIMIT = 200; }
      }
      if (actionType != null && !wt.fc.PersistenceHelper.checkStoredLength(actionType.toString(), ACTION_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionType"), java.lang.String.valueOf(java.lang.Math.min(ACTION_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "actionType", this.actionType, actionType));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String TRANSFER_DATE = "transferDate";
   java.sql.Timestamp transferDate;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.sql.Timestamp getTransferDate() {
      return transferDate;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setTransferDate(java.sql.Timestamp transferDate) throws wt.util.WTPropertyVetoException {
      transferDateValidate(transferDate);
      this.transferDate = transferDate;
   }
   void transferDateValidate(java.sql.Timestamp transferDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String ETC_DESC = "etcDesc";
   static int ETC_DESC_UPPER_LIMIT = -1;
   java.lang.String etcDesc;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getEtcDesc() {
      return etcDesc;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setEtcDesc(java.lang.String etcDesc) throws wt.util.WTPropertyVetoException {
      etcDescValidate(etcDesc);
      this.etcDesc = etcDesc;
   }
   void etcDescValidate(java.lang.String etcDesc) throws wt.util.WTPropertyVetoException {
      if (ETC_DESC_UPPER_LIMIT < 1) {
         try { ETC_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_DESC_UPPER_LIMIT = 3000; }
      }
      if (etcDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(etcDesc.toString(), ETC_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcDesc"), java.lang.String.valueOf(java.lang.Math.min(ETC_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcDesc", this.etcDesc, etcDesc));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String SUB_STATE = "subState";
   static int SUB_STATE_UPPER_LIMIT = -1;
   java.lang.String subState;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getSubState() {
      return subState;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setSubState(java.lang.String subState) throws wt.util.WTPropertyVetoException {
      subStateValidate(subState);
      this.subState = subState;
   }
   void subStateValidate(java.lang.String subState) throws wt.util.WTPropertyVetoException {
      if (SUB_STATE_UPPER_LIMIT < 1) {
         try { SUB_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_STATE_UPPER_LIMIT = 200; }
      }
      if (subState != null && !wt.fc.PersistenceHelper.checkStoredLength(subState.toString(), SUB_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subState"), java.lang.String.valueOf(java.lang.Math.min(SUB_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subState", this.subState, subState));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String ATTR1 = "attr1";
   static int ATTR1_UPPER_LIMIT = -1;
   java.lang.String attr1;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getAttr1() {
      return attr1;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setAttr1(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      attr1Validate(attr1);
      this.attr1 = attr1;
   }
   void attr1Validate(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      if (ATTR1_UPPER_LIMIT < 1) {
         try { ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR1_UPPER_LIMIT = 200; }
      }
      if (attr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr1.toString(), ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr1"), java.lang.String.valueOf(java.lang.Math.min(ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr1", this.attr1, attr1));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String ATTR2 = "attr2";
   static int ATTR2_UPPER_LIMIT = -1;
   java.lang.String attr2;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getAttr2() {
      return attr2;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setAttr2(java.lang.String attr2) throws wt.util.WTPropertyVetoException {
      attr2Validate(attr2);
      this.attr2 = attr2;
   }
   void attr2Validate(java.lang.String attr2) throws wt.util.WTPropertyVetoException {
      if (ATTR2_UPPER_LIMIT < 1) {
         try { ATTR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR2_UPPER_LIMIT = 200; }
      }
      if (attr2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr2.toString(), ATTR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr2"), java.lang.String.valueOf(java.lang.Math.min(ATTR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr2", this.attr2, attr2));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String ATTR3 = "attr3";
   static int ATTR3_UPPER_LIMIT = -1;
   java.lang.String attr3;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public java.lang.String getAttr3() {
      return attr3;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setAttr3(java.lang.String attr3) throws wt.util.WTPropertyVetoException {
      attr3Validate(attr3);
      this.attr3 = attr3;
   }
   void attr3Validate(java.lang.String attr3) throws wt.util.WTPropertyVetoException {
      if (ATTR3_UPPER_LIMIT < 1) {
         try { ATTR3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR3_UPPER_LIMIT = 200; }
      }
      if (attr3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr3.toString(), ATTR3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr3"), java.lang.String.valueOf(java.lang.Math.min(ATTR3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr3", this.attr3, attr3));
   }

   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String MANAGER = "manager";
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public static final java.lang.String MANAGER_REFERENCE = "managerReference";
   wt.fc.ObjectReference managerReference;
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public e3ps.project.moldPart.MoldPartManager getManager() {
      return (managerReference != null) ? (e3ps.project.moldPart.MoldPartManager) managerReference.getObject() : null;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public wt.fc.ObjectReference getManagerReference() {
      return managerReference;
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setManager(e3ps.project.moldPart.MoldPartManager the_manager) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setManagerReference(the_manager == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.moldPart.MoldPartManager) the_manager));
   }
   /**
    * @see e3ps.project.moldPart.MoldSubPart
    */
   public void setManagerReference(wt.fc.ObjectReference the_managerReference) throws wt.util.WTPropertyVetoException {
      managerReferenceValidate(the_managerReference);
      managerReference = (wt.fc.ObjectReference) the_managerReference;
   }
   void managerReferenceValidate(wt.fc.ObjectReference the_managerReference) throws wt.util.WTPropertyVetoException {
      if (the_managerReference == null || the_managerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerReference") },
               new java.beans.PropertyChangeEvent(this, "managerReference", this.managerReference, managerReference));
      if (the_managerReference != null && the_managerReference.getReferencedClass() != null &&
            !e3ps.project.moldPart.MoldPartManager.class.isAssignableFrom(the_managerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "managerReference", this.managerReference, managerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1428747766967118407L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( actionType );
      output.writeObject( attr1 );
      output.writeObject( attr2 );
      output.writeObject( attr3 );
      output.writeObject( ea );
      output.writeObject( endDate );
      output.writeObject( etcDesc );
      output.writeObject( mType );
      output.writeObject( managerReference );
      output.writeObject( material );
      output.writeObject( moldName );
      output.writeObject( moldNo );
      output.writeObject( partClass );
      output.writeObject( partType );
      output.writeObject( subState );
      output.writeObject( thePersistInfo );
      output.writeObject( transferDate );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.moldPart.MoldSubPart) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "actionType", actionType );
      output.setString( "attr1", attr1 );
      output.setString( "attr2", attr2 );
      output.setString( "attr3", attr3 );
      output.setString( "ea", ea );
      output.setTimestamp( "endDate", endDate );
      output.setString( "etcDesc", etcDesc );
      output.setString( "mType", mType );
      output.writeObject( "managerReference", managerReference, wt.fc.ObjectReference.class, true );
      output.setString( "material", material );
      output.setString( "moldName", moldName );
      output.setString( "moldNo", moldNo );
      output.setString( "partClass", partClass );
      output.setString( "partType", partType );
      output.setString( "subState", subState );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setTimestamp( "transferDate", transferDate );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      actionType = input.getString( "actionType" );
      attr1 = input.getString( "attr1" );
      attr2 = input.getString( "attr2" );
      attr3 = input.getString( "attr3" );
      ea = input.getString( "ea" );
      endDate = input.getTimestamp( "endDate" );
      etcDesc = input.getString( "etcDesc" );
      mType = input.getString( "mType" );
      managerReference = (wt.fc.ObjectReference) input.readObject( "managerReference", managerReference, wt.fc.ObjectReference.class, true );
      material = input.getString( "material" );
      moldName = input.getString( "moldName" );
      moldNo = input.getString( "moldNo" );
      partClass = input.getString( "partClass" );
      partType = input.getString( "partType" );
      subState = input.getString( "subState" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      transferDate = input.getTimestamp( "transferDate" );
   }

   boolean readVersion1428747766967118407L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      actionType = (java.lang.String) input.readObject();
      attr1 = (java.lang.String) input.readObject();
      attr2 = (java.lang.String) input.readObject();
      attr3 = (java.lang.String) input.readObject();
      ea = (java.lang.String) input.readObject();
      endDate = (java.sql.Timestamp) input.readObject();
      etcDesc = (java.lang.String) input.readObject();
      mType = (java.lang.String) input.readObject();
      managerReference = (wt.fc.ObjectReference) input.readObject();
      material = (java.lang.String) input.readObject();
      moldName = (java.lang.String) input.readObject();
      moldNo = (java.lang.String) input.readObject();
      partClass = (java.lang.String) input.readObject();
      partType = (java.lang.String) input.readObject();
      subState = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      transferDate = (java.sql.Timestamp) input.readObject();
      return true;
   }

   protected boolean readVersion( MoldSubPart thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1428747766967118407L( input, readSerialVersionUID, superDone );
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
