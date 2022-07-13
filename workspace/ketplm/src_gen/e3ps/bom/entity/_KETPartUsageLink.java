package e3ps.bom.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartUsageLink extends wt.vc.struct.IteratedUsageLink implements wt.type.Typed, wt.configuration.TraceableLink, wt.fc.archive.Archiveable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.bom.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartUsageLink.class.getName();

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String PARENT_ITEM_CODE = "parentItemCode";
   static int PARENT_ITEM_CODE_UPPER_LIMIT = -1;
   java.lang.String parentItemCode;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getParentItemCode() {
      return parentItemCode;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setParentItemCode(java.lang.String parentItemCode) throws wt.util.WTPropertyVetoException {
      parentItemCodeValidate(parentItemCode);
      this.parentItemCode = parentItemCode;
   }
   void parentItemCodeValidate(java.lang.String parentItemCode) throws wt.util.WTPropertyVetoException {
      if (parentItemCode == null || parentItemCode.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentItemCode") },
               new java.beans.PropertyChangeEvent(this, "parentItemCode", this.parentItemCode, parentItemCode));
      if (PARENT_ITEM_CODE_UPPER_LIMIT < 1) {
         try { PARENT_ITEM_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("parentItemCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PARENT_ITEM_CODE_UPPER_LIMIT = 200; }
      }
      if (parentItemCode != null && !wt.fc.PersistenceHelper.checkStoredLength(parentItemCode.toString(), PARENT_ITEM_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentItemCode"), java.lang.String.valueOf(java.lang.Math.min(PARENT_ITEM_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "parentItemCode", this.parentItemCode, parentItemCode));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String CHILD_ITEM_CODE = "childItemCode";
   static int CHILD_ITEM_CODE_UPPER_LIMIT = -1;
   java.lang.String childItemCode;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getChildItemCode() {
      return childItemCode;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setChildItemCode(java.lang.String childItemCode) throws wt.util.WTPropertyVetoException {
      childItemCodeValidate(childItemCode);
      this.childItemCode = childItemCode;
   }
   void childItemCodeValidate(java.lang.String childItemCode) throws wt.util.WTPropertyVetoException {
      if (childItemCode == null || childItemCode.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "childItemCode") },
               new java.beans.PropertyChangeEvent(this, "childItemCode", this.childItemCode, childItemCode));
      if (CHILD_ITEM_CODE_UPPER_LIMIT < 1) {
         try { CHILD_ITEM_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("childItemCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHILD_ITEM_CODE_UPPER_LIMIT = 200; }
      }
      if (childItemCode != null && !wt.fc.PersistenceHelper.checkStoredLength(childItemCode.toString(), CHILD_ITEM_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "childItemCode"), java.lang.String.valueOf(java.lang.Math.min(CHILD_ITEM_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "childItemCode", this.childItemCode, childItemCode));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String CHILD_ITEM_VERSION = "childItemVersion";
   static int CHILD_ITEM_VERSION_UPPER_LIMIT = -1;
   java.lang.String childItemVersion;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getChildItemVersion() {
      return childItemVersion;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setChildItemVersion(java.lang.String childItemVersion) throws wt.util.WTPropertyVetoException {
      childItemVersionValidate(childItemVersion);
      this.childItemVersion = childItemVersion;
   }
   void childItemVersionValidate(java.lang.String childItemVersion) throws wt.util.WTPropertyVetoException {
      if (childItemVersion == null || childItemVersion.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "childItemVersion") },
               new java.beans.PropertyChangeEvent(this, "childItemVersion", this.childItemVersion, childItemVersion));
      if (CHILD_ITEM_VERSION_UPPER_LIMIT < 1) {
         try { CHILD_ITEM_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("childItemVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHILD_ITEM_VERSION_UPPER_LIMIT = 200; }
      }
      if (childItemVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(childItemVersion.toString(), CHILD_ITEM_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "childItemVersion"), java.lang.String.valueOf(java.lang.Math.min(CHILD_ITEM_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "childItemVersion", this.childItemVersion, childItemVersion));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String BOM_LEVEL = "bomLevel";
   static int BOM_LEVEL_UPPER_LIMIT = -1;
   java.lang.String bomLevel;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getBomLevel() {
      return bomLevel;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setBomLevel(java.lang.String bomLevel) throws wt.util.WTPropertyVetoException {
      bomLevelValidate(bomLevel);
      this.bomLevel = bomLevel;
   }
   void bomLevelValidate(java.lang.String bomLevel) throws wt.util.WTPropertyVetoException {
      if (BOM_LEVEL_UPPER_LIMIT < 1) {
         try { BOM_LEVEL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bomLevel").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOM_LEVEL_UPPER_LIMIT = 200; }
      }
      if (bomLevel != null && !wt.fc.PersistenceHelper.checkStoredLength(bomLevel.toString(), BOM_LEVEL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bomLevel"), java.lang.String.valueOf(java.lang.Math.min(BOM_LEVEL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bomLevel", this.bomLevel, bomLevel));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String SEQUENCE_NUMBER = "sequenceNumber";
   static int SEQUENCE_NUMBER_UPPER_LIMIT = -1;
   java.lang.String sequenceNumber;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getSequenceNumber() {
      return sequenceNumber;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setSequenceNumber(java.lang.String sequenceNumber) throws wt.util.WTPropertyVetoException {
      sequenceNumberValidate(sequenceNumber);
      this.sequenceNumber = sequenceNumber;
   }
   void sequenceNumberValidate(java.lang.String sequenceNumber) throws wt.util.WTPropertyVetoException {
      if (SEQUENCE_NUMBER_UPPER_LIMIT < 1) {
         try { SEQUENCE_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sequenceNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SEQUENCE_NUMBER_UPPER_LIMIT = 200; }
      }
      if (sequenceNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(sequenceNumber.toString(), SEQUENCE_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sequenceNumber"), java.lang.String.valueOf(java.lang.Math.min(SEQUENCE_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sequenceNumber", this.sequenceNumber, sequenceNumber));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ITEM_SEQ = "itemSeq";
   int itemSeq;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public int getItemSeq() {
      return itemSeq;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setItemSeq(int itemSeq) throws wt.util.WTPropertyVetoException {
      itemSeqValidate(itemSeq);
      this.itemSeq = itemSeq;
   }
   void itemSeqValidate(int itemSeq) throws wt.util.WTPropertyVetoException {
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String QUANTITY = "quantity";
   static int QUANTITY_UPPER_LIMIT = -1;
   java.lang.String quantity;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getQuantity() {
      return quantity;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setQuantity(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
      quantityValidate(quantity);
      this.quantity = quantity;
   }
   void quantityValidate(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
      if (quantity == null || quantity.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantity") },
               new java.beans.PropertyChangeEvent(this, "quantity", this.quantity, quantity));
      if (QUANTITY_UPPER_LIMIT < 1) {
         try { QUANTITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_UPPER_LIMIT = 200; }
      }
      if (quantity != null && !wt.fc.PersistenceHelper.checkStoredLength(quantity.toString(), QUANTITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantity"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantity", this.quantity, quantity));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String BOX_QUANTITY = "boxQuantity";
   static int BOX_QUANTITY_UPPER_LIMIT = -1;
   java.lang.String boxQuantity;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getBoxQuantity() {
      return boxQuantity;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setBoxQuantity(java.lang.String boxQuantity) throws wt.util.WTPropertyVetoException {
      boxQuantityValidate(boxQuantity);
      this.boxQuantity = boxQuantity;
   }
   void boxQuantityValidate(java.lang.String boxQuantity) throws wt.util.WTPropertyVetoException {
      if (BOX_QUANTITY_UPPER_LIMIT < 1) {
         try { BOX_QUANTITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("boxQuantity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOX_QUANTITY_UPPER_LIMIT = 200; }
      }
      if (boxQuantity != null && !wt.fc.PersistenceHelper.checkStoredLength(boxQuantity.toString(), BOX_QUANTITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "boxQuantity"), java.lang.String.valueOf(java.lang.Math.min(BOX_QUANTITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "boxQuantity", this.boxQuantity, boxQuantity));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String UNIT = "unit";
   static int UNIT_UPPER_LIMIT = -1;
   java.lang.String unit;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getUnit() {
      return unit;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
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
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String MATERIAL = "material";
   static int MATERIAL_UPPER_LIMIT = -1;
   java.lang.String material;
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getMaterial() {
      return material;
   }
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
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
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String HARDNESS_FROM = "hardnessFrom";
   static int HARDNESS_FROM_UPPER_LIMIT = -1;
   java.lang.String hardnessFrom;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getHardnessFrom() {
      return hardnessFrom;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setHardnessFrom(java.lang.String hardnessFrom) throws wt.util.WTPropertyVetoException {
      hardnessFromValidate(hardnessFrom);
      this.hardnessFrom = hardnessFrom;
   }
   void hardnessFromValidate(java.lang.String hardnessFrom) throws wt.util.WTPropertyVetoException {
      if (HARDNESS_FROM_UPPER_LIMIT < 1) {
         try { HARDNESS_FROM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("hardnessFrom").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HARDNESS_FROM_UPPER_LIMIT = 200; }
      }
      if (hardnessFrom != null && !wt.fc.PersistenceHelper.checkStoredLength(hardnessFrom.toString(), HARDNESS_FROM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "hardnessFrom"), java.lang.String.valueOf(java.lang.Math.min(HARDNESS_FROM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "hardnessFrom", this.hardnessFrom, hardnessFrom));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String HARDNESS_TO = "hardnessTo";
   static int HARDNESS_TO_UPPER_LIMIT = -1;
   java.lang.String hardnessTo;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getHardnessTo() {
      return hardnessTo;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setHardnessTo(java.lang.String hardnessTo) throws wt.util.WTPropertyVetoException {
      hardnessToValidate(hardnessTo);
      this.hardnessTo = hardnessTo;
   }
   void hardnessToValidate(java.lang.String hardnessTo) throws wt.util.WTPropertyVetoException {
      if (HARDNESS_TO_UPPER_LIMIT < 1) {
         try { HARDNESS_TO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("hardnessTo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HARDNESS_TO_UPPER_LIMIT = 200; }
      }
      if (hardnessTo != null && !wt.fc.PersistenceHelper.checkStoredLength(hardnessTo.toString(), HARDNESS_TO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "hardnessTo"), java.lang.String.valueOf(java.lang.Math.min(HARDNESS_TO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "hardnessTo", this.hardnessTo, hardnessTo));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ORG_CODE = "orgCode";
   static int ORG_CODE_UPPER_LIMIT = -1;
   java.lang.String orgCode;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getOrgCode() {
      return orgCode;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setOrgCode(java.lang.String orgCode) throws wt.util.WTPropertyVetoException {
      orgCodeValidate(orgCode);
      this.orgCode = orgCode;
   }
   void orgCodeValidate(java.lang.String orgCode) throws wt.util.WTPropertyVetoException {
      if (orgCode == null || orgCode.trim().length() == 0)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "orgCode") },
               new java.beans.PropertyChangeEvent(this, "orgCode", this.orgCode, orgCode));
      if (ORG_CODE_UPPER_LIMIT < 1) {
         try { ORG_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("orgCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ORG_CODE_UPPER_LIMIT = 200; }
      }
      if (orgCode != null && !wt.fc.PersistenceHelper.checkStoredLength(orgCode.toString(), ORG_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "orgCode"), java.lang.String.valueOf(java.lang.Math.min(ORG_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "orgCode", this.orgCode, orgCode));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String MFG_FLAG = "mfgFlag";
   static int MFG_FLAG_UPPER_LIMIT = -1;
   java.lang.String mfgFlag;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getMfgFlag() {
      return mfgFlag;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setMfgFlag(java.lang.String mfgFlag) throws wt.util.WTPropertyVetoException {
      mfgFlagValidate(mfgFlag);
      this.mfgFlag = mfgFlag;
   }
   void mfgFlagValidate(java.lang.String mfgFlag) throws wt.util.WTPropertyVetoException {
      if (MFG_FLAG_UPPER_LIMIT < 1) {
         try { MFG_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mfgFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFG_FLAG_UPPER_LIMIT = 200; }
      }
      if (mfgFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(mfgFlag.toString(), MFG_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mfgFlag"), java.lang.String.valueOf(java.lang.Math.min(MFG_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mfgFlag", this.mfgFlag, mfgFlag));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String START_DATE = "startDate";
   static int START_DATE_UPPER_LIMIT = -1;
   java.lang.String startDate;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getStartDate() {
      return startDate;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setStartDate(java.lang.String startDate) throws wt.util.WTPropertyVetoException {
      startDateValidate(startDate);
      this.startDate = startDate;
   }
   void startDateValidate(java.lang.String startDate) throws wt.util.WTPropertyVetoException {
      if (START_DATE_UPPER_LIMIT < 1) {
         try { START_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("startDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { START_DATE_UPPER_LIMIT = 200; }
      }
      if (startDate != null && !wt.fc.PersistenceHelper.checkStoredLength(startDate.toString(), START_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "startDate"), java.lang.String.valueOf(java.lang.Math.min(START_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "startDate", this.startDate, startDate));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String END_DATE = "endDate";
   static int END_DATE_UPPER_LIMIT = -1;
   java.lang.String endDate;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getEndDate() {
      return endDate;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setEndDate(java.lang.String endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.lang.String endDate) throws wt.util.WTPropertyVetoException {
      if (END_DATE_UPPER_LIMIT < 1) {
         try { END_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("endDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { END_DATE_UPPER_LIMIT = 200; }
      }
      if (endDate != null && !wt.fc.PersistenceHelper.checkStoredLength(endDate.toString(), END_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "endDate"), java.lang.String.valueOf(java.lang.Math.min(END_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "endDate", this.endDate, endDate));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute1(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      attribute1Validate(attribute1);
      this.attribute1 = attribute1;
   }
   void attribute1Validate(java.lang.String attribute1) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE1_UPPER_LIMIT < 1) {
         try { ATTRIBUTE1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE1_UPPER_LIMIT = 2000; }
      }
      if (attribute1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute1.toString(), ATTRIBUTE1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute1"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute1", this.attribute1, attribute1));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute2(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      attribute2Validate(attribute2);
      this.attribute2 = attribute2;
   }
   void attribute2Validate(java.lang.String attribute2) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE2_UPPER_LIMIT < 1) {
         try { ATTRIBUTE2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE2_UPPER_LIMIT = 2000; }
      }
      if (attribute2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute2.toString(), ATTRIBUTE2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute2"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute2", this.attribute2, attribute2));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute3(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      attribute3Validate(attribute3);
      this.attribute3 = attribute3;
   }
   void attribute3Validate(java.lang.String attribute3) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE3_UPPER_LIMIT < 1) {
         try { ATTRIBUTE3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE3_UPPER_LIMIT = 2000; }
      }
      if (attribute3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute3.toString(), ATTRIBUTE3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute3"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute3", this.attribute3, attribute3));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute4(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      attribute4Validate(attribute4);
      this.attribute4 = attribute4;
   }
   void attribute4Validate(java.lang.String attribute4) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE4_UPPER_LIMIT < 1) {
         try { ATTRIBUTE4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE4_UPPER_LIMIT = 2000; }
      }
      if (attribute4 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute4.toString(), ATTRIBUTE4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute4"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute4", this.attribute4, attribute4));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute5(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      attribute5Validate(attribute5);
      this.attribute5 = attribute5;
   }
   void attribute5Validate(java.lang.String attribute5) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE5_UPPER_LIMIT < 1) {
         try { ATTRIBUTE5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE5_UPPER_LIMIT = 2000; }
      }
      if (attribute5 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute5.toString(), ATTRIBUTE5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute5"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute5", this.attribute5, attribute5));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute6(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      attribute6Validate(attribute6);
      this.attribute6 = attribute6;
   }
   void attribute6Validate(java.lang.String attribute6) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE6_UPPER_LIMIT < 1) {
         try { ATTRIBUTE6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE6_UPPER_LIMIT = 2000; }
      }
      if (attribute6 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute6.toString(), ATTRIBUTE6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute6"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute6", this.attribute6, attribute6));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute7(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      attribute7Validate(attribute7);
      this.attribute7 = attribute7;
   }
   void attribute7Validate(java.lang.String attribute7) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE7_UPPER_LIMIT < 1) {
         try { ATTRIBUTE7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE7_UPPER_LIMIT = 2000; }
      }
      if (attribute7 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute7.toString(), ATTRIBUTE7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute7"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute7", this.attribute7, attribute7));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute8(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      attribute8Validate(attribute8);
      this.attribute8 = attribute8;
   }
   void attribute8Validate(java.lang.String attribute8) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE8_UPPER_LIMIT < 1) {
         try { ATTRIBUTE8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE8_UPPER_LIMIT = 2000; }
      }
      if (attribute8 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute8.toString(), ATTRIBUTE8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute8"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute8", this.attribute8, attribute8));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute9(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      attribute9Validate(attribute9);
      this.attribute9 = attribute9;
   }
   void attribute9Validate(java.lang.String attribute9) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE9_UPPER_LIMIT < 1) {
         try { ATTRIBUTE9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE9_UPPER_LIMIT = 2000; }
      }
      if (attribute9 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute9.toString(), ATTRIBUTE9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute9"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute9", this.attribute9, attribute9));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setAttribute10(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      attribute10Validate(attribute10);
      this.attribute10 = attribute10;
   }
   void attribute10Validate(java.lang.String attribute10) throws wt.util.WTPropertyVetoException {
      if (ATTRIBUTE10_UPPER_LIMIT < 1) {
         try { ATTRIBUTE10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attribute10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTRIBUTE10_UPPER_LIMIT = 2000; }
      }
      if (attribute10 != null && !wt.fc.PersistenceHelper.checkStoredLength(attribute10.toString(), ATTRIBUTE10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attribute10"), java.lang.String.valueOf(java.lang.Math.min(ATTRIBUTE10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attribute10", this.attribute10, attribute10));
   }

   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String BOM_VERSION = "bomVersion";
   static int BOM_VERSION_UPPER_LIMIT = -1;
   java.lang.String bomVersion;
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getBomVersion() {
      return bomVersion;
   }
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setBomVersion(java.lang.String bomVersion) throws wt.util.WTPropertyVetoException {
      bomVersionValidate(bomVersion);
      this.bomVersion = bomVersion;
   }
   void bomVersionValidate(java.lang.String bomVersion) throws wt.util.WTPropertyVetoException {
      if (BOM_VERSION_UPPER_LIMIT < 1) {
         try { BOM_VERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bomVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOM_VERSION_UPPER_LIMIT = 200; }
      }
      if (bomVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(bomVersion.toString(), BOM_VERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bomVersion"), java.lang.String.valueOf(java.lang.Math.min(BOM_VERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bomVersion", this.bomVersion, bomVersion));
   }

   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String VERSION_ITEM_CODE = "versionItemCode";
   static int VERSION_ITEM_CODE_UPPER_LIMIT = -1;
   java.lang.String versionItemCode;
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getVersionItemCode() {
      return versionItemCode;
   }
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setVersionItemCode(java.lang.String versionItemCode) throws wt.util.WTPropertyVetoException {
      versionItemCodeValidate(versionItemCode);
      this.versionItemCode = versionItemCode;
   }
   void versionItemCodeValidate(java.lang.String versionItemCode) throws wt.util.WTPropertyVetoException {
      if (VERSION_ITEM_CODE_UPPER_LIMIT < 1) {
         try { VERSION_ITEM_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("versionItemCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { VERSION_ITEM_CODE_UPPER_LIMIT = 200; }
      }
      if (versionItemCode != null && !wt.fc.PersistenceHelper.checkStoredLength(versionItemCode.toString(), VERSION_ITEM_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "versionItemCode"), java.lang.String.valueOf(java.lang.Math.min(VERSION_ITEM_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "versionItemCode", this.versionItemCode, versionItemCode));
   }

   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String DESIGN_DATE = "designDate";
   static int DESIGN_DATE_UPPER_LIMIT = -1;
   java.lang.String designDate;
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getDesignDate() {
      return designDate;
   }
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setDesignDate(java.lang.String designDate) throws wt.util.WTPropertyVetoException {
      designDateValidate(designDate);
      this.designDate = designDate;
   }
   void designDateValidate(java.lang.String designDate) throws wt.util.WTPropertyVetoException {
      if (DESIGN_DATE_UPPER_LIMIT < 1) {
         try { DESIGN_DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("designDate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESIGN_DATE_UPPER_LIMIT = 200; }
      }
      if (designDate != null && !wt.fc.PersistenceHelper.checkStoredLength(designDate.toString(), DESIGN_DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "designDate"), java.lang.String.valueOf(java.lang.Math.min(DESIGN_DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "designDate", this.designDate, designDate));
   }

   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String BOM_TYPE = "bomType";
   static int BOM_TYPE_UPPER_LIMIT = -1;
   java.lang.String bomType;
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getBomType() {
      return bomType;
   }
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setBomType(java.lang.String bomType) throws wt.util.WTPropertyVetoException {
      bomTypeValidate(bomType);
      this.bomType = bomType;
   }
   void bomTypeValidate(java.lang.String bomType) throws wt.util.WTPropertyVetoException {
      if (BOM_TYPE_UPPER_LIMIT < 1) {
         try { BOM_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bomType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOM_TYPE_UPPER_LIMIT = 200; }
      }
      if (bomType != null && !wt.fc.PersistenceHelper.checkStoredLength(bomType.toString(), BOM_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bomType"), java.lang.String.valueOf(java.lang.Math.min(BOM_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bomType", this.bomType, bomType));
   }

   /**
    * ??? ICT
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String ICT = "ict";
   static int ICT_UPPER_LIMIT = -1;
   java.lang.String ict;
   /**
    * ??? ICT
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getIct() {
      return ict;
   }
   /**
    * ??? ICT
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setIct(java.lang.String ict) throws wt.util.WTPropertyVetoException {
      ictValidate(ict);
      this.ict = ict;
   }
   void ictValidate(java.lang.String ict) throws wt.util.WTPropertyVetoException {
      if (ICT_UPPER_LIMIT < 1) {
         try { ICT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ict").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ICT_UPPER_LIMIT = 4; }
      }
      if (ict != null && !wt.fc.PersistenceHelper.checkStoredLength(ict.toString(), ICT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ict"), java.lang.String.valueOf(java.lang.Math.min(ICT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ict", this.ict, ict));
   }

   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String REF_TOP = "refTop";
   static int REF_TOP_UPPER_LIMIT = -1;
   java.lang.String refTop;
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getRefTop() {
      return refTop;
   }
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setRefTop(java.lang.String refTop) throws wt.util.WTPropertyVetoException {
      refTopValidate(refTop);
      this.refTop = refTop;
   }
   void refTopValidate(java.lang.String refTop) throws wt.util.WTPropertyVetoException {
      if (REF_TOP_UPPER_LIMIT < 1) {
         try { REF_TOP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("refTop").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REF_TOP_UPPER_LIMIT = 200; }
      }
      if (refTop != null && !wt.fc.PersistenceHelper.checkStoredLength(refTop.toString(), REF_TOP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "refTop"), java.lang.String.valueOf(java.lang.Math.min(REF_TOP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "refTop", this.refTop, refTop));
   }

   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public static final java.lang.String REF_BOTTOM = "refBottom";
   static int REF_BOTTOM_UPPER_LIMIT = -1;
   java.lang.String refBottom;
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public java.lang.String getRefBottom() {
      return refBottom;
   }
   /**
    * @see e3ps.bom.entity.KETPartUsageLink
    */
   public void setRefBottom(java.lang.String refBottom) throws wt.util.WTPropertyVetoException {
      refBottomValidate(refBottom);
      this.refBottom = refBottom;
   }
   void refBottomValidate(java.lang.String refBottom) throws wt.util.WTPropertyVetoException {
      if (REF_BOTTOM_UPPER_LIMIT < 1) {
         try { REF_BOTTOM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("refBottom").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REF_BOTTOM_UPPER_LIMIT = 200; }
      }
      if (refBottom != null && !wt.fc.PersistenceHelper.checkStoredLength(refBottom.toString(), REF_BOTTOM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "refBottom"), java.lang.String.valueOf(java.lang.Math.min(REF_BOTTOM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "refBottom", this.refBottom, refBottom));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    * @see wt.vc.struct.IteratedUsageLink
    */
   public wt.part.WTPart getUsedBy() {
      return (wt.part.WTPart) getRoleAObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    * @see wt.vc.struct.IteratedUsageLink
    */
   public void setUsedBy(wt.part.WTPart the_usedBy) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_usedBy);
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    * @see wt.vc.struct.IteratedUsageLink
    */
   public wt.part.WTPartMaster getUses() {
      return (wt.part.WTPartMaster) getRoleBObject();
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETPartUsageLink
    * @see wt.vc.struct.IteratedUsageLink
    */
   public void setUses(wt.part.WTPartMaster the_uses) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_uses);
   }

   wt.type.TypeDefinitionReference typeDefinitionReference;
   /**
    * @see wt.type.Typed
    */
   public wt.type.TypeDefinitionReference getTypeDefinitionReference() {
      return typeDefinitionReference;
   }
   /**
    * @see wt.type.Typed
    */
   public void setTypeDefinitionReference(wt.type.TypeDefinitionReference typeDefinitionReference) throws wt.util.WTPropertyVetoException {
      typeDefinitionReferenceValidate(typeDefinitionReference);
      this.typeDefinitionReference = typeDefinitionReference;
   }
   void typeDefinitionReferenceValidate(wt.type.TypeDefinitionReference typeDefinitionReference) throws wt.util.WTPropertyVetoException {
      if (typeDefinitionReference == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "typeDefinitionReference") },
               new java.beans.PropertyChangeEvent(this, "typeDefinitionReference", this.typeDefinitionReference, typeDefinitionReference));
   }

   wt.iba.value.AttributeContainer theAttributeContainer;
   /**
    * @see wt.iba.value.IBAHolder
    */
   public wt.iba.value.AttributeContainer getAttributeContainer() {
      return theAttributeContainer;
   }
   /**
    * @see wt.iba.value.IBAHolder
    */
   public void setAttributeContainer(wt.iba.value.AttributeContainer theAttributeContainer) {
      this.theAttributeContainer = theAttributeContainer;
   }

   static int TRACE_CODE_UPPER_LIMIT = -1;
   wt.configuration.TraceCode traceCode;
   /**
    * <b>Supported API: </b>true
    *
    * @see wt.configuration.TraceableLink
    */
   public wt.configuration.TraceCode getTraceCode() {
      return traceCode;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see wt.configuration.TraceableLink
    */
   public void setTraceCode(wt.configuration.TraceCode traceCode) {
      this.traceCode = traceCode;
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

   public static final long EXTERNALIZATION_VERSION_UID = 5254889542939852365L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( attribute1 );
      output.writeObject( attribute10 );
      output.writeObject( attribute2 );
      output.writeObject( attribute3 );
      output.writeObject( attribute4 );
      output.writeObject( attribute5 );
      output.writeObject( attribute6 );
      output.writeObject( attribute7 );
      output.writeObject( attribute8 );
      output.writeObject( attribute9 );
      output.writeObject( bomLevel );
      output.writeObject( bomType );
      output.writeObject( bomVersion );
      output.writeObject( boxQuantity );
      output.writeObject( childItemCode );
      output.writeObject( childItemVersion );
      output.writeObject( designDate );
      output.writeObject( endDate );
      output.writeObject( hardnessFrom );
      output.writeObject( hardnessTo );
      output.writeObject( ict );
      output.writeInt( itemSeq );
      output.writeObject( material );
      output.writeObject( mfgFlag );
      output.writeObject( orgCode );
      output.writeObject( parentItemCode );
      output.writeObject( quantity );
      output.writeObject( refBottom );
      output.writeObject( refTop );
      output.writeObject( sequenceNumber );
      output.writeObject( startDate );
      output.writeObject( (traceCode == null ? null : traceCode.getStringValue()) );
      output.writeObject( typeDefinitionReference );
      output.writeObject( unit );
      output.writeObject( versionItemCode );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( theAttributeContainer );
      }

   }

   protected void super_writeExternal_KETPartUsageLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.bom.entity.KETPartUsageLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETPartUsageLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "attribute1", attribute1 );
      output.setString( "attribute10", attribute10 );
      output.setString( "attribute2", attribute2 );
      output.setString( "attribute3", attribute3 );
      output.setString( "attribute4", attribute4 );
      output.setString( "attribute5", attribute5 );
      output.setString( "attribute6", attribute6 );
      output.setString( "attribute7", attribute7 );
      output.setString( "attribute8", attribute8 );
      output.setString( "attribute9", attribute9 );
      output.setString( "bomLevel", bomLevel );
      output.setString( "bomType", bomType );
      output.setString( "bomVersion", bomVersion );
      output.setString( "boxQuantity", boxQuantity );
      output.setString( "childItemCode", childItemCode );
      output.setString( "childItemVersion", childItemVersion );
      output.setString( "designDate", designDate );
      output.setString( "endDate", endDate );
      output.setString( "hardnessFrom", hardnessFrom );
      output.setString( "hardnessTo", hardnessTo );
      output.setString( "ict", ict );
      output.setInt( "itemSeq", itemSeq );
      output.setString( "material", material );
      output.setString( "mfgFlag", mfgFlag );
      output.setString( "orgCode", orgCode );
      output.setString( "parentItemCode", parentItemCode );
      output.setString( "quantity", quantity );
      output.setString( "refBottom", refBottom );
      output.setString( "refTop", refTop );
      output.setString( "sequenceNumber", sequenceNumber );
      output.setString( "startDate", startDate );
      output.setString( "traceCode", (traceCode == null ? null : traceCode.toString()) );
      output.writeObject( "typeDefinitionReference", typeDefinitionReference, wt.type.TypeDefinitionReference.class, true );
      output.setString( "unit", unit );
      output.setString( "versionItemCode", versionItemCode );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      attribute1 = input.getString( "attribute1" );
      attribute10 = input.getString( "attribute10" );
      attribute2 = input.getString( "attribute2" );
      attribute3 = input.getString( "attribute3" );
      attribute4 = input.getString( "attribute4" );
      attribute5 = input.getString( "attribute5" );
      attribute6 = input.getString( "attribute6" );
      attribute7 = input.getString( "attribute7" );
      attribute8 = input.getString( "attribute8" );
      attribute9 = input.getString( "attribute9" );
      bomLevel = input.getString( "bomLevel" );
      bomType = input.getString( "bomType" );
      bomVersion = input.getString( "bomVersion" );
      boxQuantity = input.getString( "boxQuantity" );
      childItemCode = input.getString( "childItemCode" );
      childItemVersion = input.getString( "childItemVersion" );
      designDate = input.getString( "designDate" );
      endDate = input.getString( "endDate" );
      hardnessFrom = input.getString( "hardnessFrom" );
      hardnessTo = input.getString( "hardnessTo" );
      ict = input.getString( "ict" );
      itemSeq = input.getInt( "itemSeq" );
      material = input.getString( "material" );
      mfgFlag = input.getString( "mfgFlag" );
      orgCode = input.getString( "orgCode" );
      parentItemCode = input.getString( "parentItemCode" );
      quantity = input.getString( "quantity" );
      refBottom = input.getString( "refBottom" );
      refTop = input.getString( "refTop" );
      sequenceNumber = input.getString( "sequenceNumber" );
      startDate = input.getString( "startDate" );
      java.lang.String traceCode_string_value = (java.lang.String) input.getString("traceCode");
      if ( traceCode_string_value != null ) { 
         traceCode = (wt.configuration.TraceCode) wt.introspection.ClassInfo.getConstrainedEnum( getClass(), "traceCode", traceCode_string_value );
         if ( traceCode == null )  // hard-coded type
            traceCode = wt.configuration.TraceCode.toTraceCode( traceCode_string_value );
      }
      typeDefinitionReference = (wt.type.TypeDefinitionReference) input.readObject( "typeDefinitionReference", typeDefinitionReference, wt.type.TypeDefinitionReference.class, true );
      unit = input.getString( "unit" );
      versionItemCode = input.getString( "versionItemCode" );
   }

   boolean readVersion5254889542939852365L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      attribute1 = (java.lang.String) input.readObject();
      attribute10 = (java.lang.String) input.readObject();
      attribute2 = (java.lang.String) input.readObject();
      attribute3 = (java.lang.String) input.readObject();
      attribute4 = (java.lang.String) input.readObject();
      attribute5 = (java.lang.String) input.readObject();
      attribute6 = (java.lang.String) input.readObject();
      attribute7 = (java.lang.String) input.readObject();
      attribute8 = (java.lang.String) input.readObject();
      attribute9 = (java.lang.String) input.readObject();
      bomLevel = (java.lang.String) input.readObject();
      bomType = (java.lang.String) input.readObject();
      bomVersion = (java.lang.String) input.readObject();
      boxQuantity = (java.lang.String) input.readObject();
      childItemCode = (java.lang.String) input.readObject();
      childItemVersion = (java.lang.String) input.readObject();
      designDate = (java.lang.String) input.readObject();
      endDate = (java.lang.String) input.readObject();
      hardnessFrom = (java.lang.String) input.readObject();
      hardnessTo = (java.lang.String) input.readObject();
      ict = (java.lang.String) input.readObject();
      itemSeq = input.readInt();
      material = (java.lang.String) input.readObject();
      mfgFlag = (java.lang.String) input.readObject();
      orgCode = (java.lang.String) input.readObject();
      parentItemCode = (java.lang.String) input.readObject();
      quantity = (java.lang.String) input.readObject();
      refBottom = (java.lang.String) input.readObject();
      refTop = (java.lang.String) input.readObject();
      sequenceNumber = (java.lang.String) input.readObject();
      startDate = (java.lang.String) input.readObject();
      java.lang.String traceCode_string_value = (java.lang.String) input.readObject();
      try { 
         traceCode = (wt.configuration.TraceCode) wt.fc.EnumeratedTypeUtil.toEnumeratedType( traceCode_string_value );
      } catch( wt.util.WTInvalidParameterException e ) {
         // Old Format
         traceCode = wt.configuration.TraceCode.toTraceCode( traceCode_string_value );
      }
      typeDefinitionReference = (wt.type.TypeDefinitionReference) input.readObject();
      unit = (java.lang.String) input.readObject();
      versionItemCode = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            theAttributeContainer = (wt.iba.value.AttributeContainer) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETPartUsageLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion5254889542939852365L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETPartUsageLink( _KETPartUsageLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
