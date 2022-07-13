package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MoldItemInfo implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = MoldItemInfo.class.getName();

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setPartNo(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      partNoValidate(partNo);
      this.partNo = partNo;
   }
   void partNoValidate(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      if (PART_NO_UPPER_LIMIT < 1) {
         try { PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NO_UPPER_LIMIT = 200; }
      }
      if (partNo != null && !wt.fc.PersistenceHelper.checkStoredLength(partNo.toString(), PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNo"), java.lang.String.valueOf(java.lang.Math.min(PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNo", this.partNo, partNo));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String DIE_NO = "dieNo";
   static int DIE_NO_UPPER_LIMIT = -1;
   java.lang.String dieNo;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getDieNo() {
      return dieNo;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setDieNo(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      dieNoValidate(dieNo);
      this.dieNo = dieNo;
   }
   void dieNoValidate(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      if (DIE_NO_UPPER_LIMIT < 1) {
         try { DIE_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dieNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIE_NO_UPPER_LIMIT = 200; }
      }
      if (dieNo != null && !wt.fc.PersistenceHelper.checkStoredLength(dieNo.toString(), DIE_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dieNo"), java.lang.String.valueOf(java.lang.Math.min(DIE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dieNo", this.dieNo, dieNo));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setPartName(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      partNameValidate(partName);
      this.partName = partName;
   }
   void partNameValidate(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      if (PART_NAME_UPPER_LIMIT < 1) {
         try { PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NAME_UPPER_LIMIT = 200; }
      }
      if (partName != null && !wt.fc.PersistenceHelper.checkStoredLength(partName.toString(), PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partName"), java.lang.String.valueOf(java.lang.Math.min(PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partName", this.partName, partName));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String ITEM_TYPE = "itemType";
   static int ITEM_TYPE_UPPER_LIMIT = -1;
   java.lang.String itemType;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getItemType() {
      return itemType;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setItemType(java.lang.String itemType) throws wt.util.WTPropertyVetoException {
      itemTypeValidate(itemType);
      this.itemType = itemType;
   }
   void itemTypeValidate(java.lang.String itemType) throws wt.util.WTPropertyVetoException {
      if (ITEM_TYPE_UPPER_LIMIT < 1) {
         try { ITEM_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("itemType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ITEM_TYPE_UPPER_LIMIT = 200; }
      }
      if (itemType != null && !wt.fc.PersistenceHelper.checkStoredLength(itemType.toString(), ITEM_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "itemType"), java.lang.String.valueOf(java.lang.Math.min(ITEM_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "itemType", this.itemType, itemType));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String C_VPITCH = "cVPitch";
   static int C_VPITCH_UPPER_LIMIT = -1;
   java.lang.String cVPitch;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getCVPitch() {
      return cVPitch;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setCVPitch(java.lang.String cVPitch) throws wt.util.WTPropertyVetoException {
      cVPitchValidate(cVPitch);
      this.cVPitch = cVPitch;
   }
   void cVPitchValidate(java.lang.String cVPitch) throws wt.util.WTPropertyVetoException {
      if (C_VPITCH_UPPER_LIMIT < 1) {
         try { C_VPITCH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cVPitch").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C_VPITCH_UPPER_LIMIT = 200; }
      }
      if (cVPitch != null && !wt.fc.PersistenceHelper.checkStoredLength(cVPitch.toString(), C_VPITCH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cVPitch"), java.lang.String.valueOf(java.lang.Math.min(C_VPITCH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cVPitch", this.cVPitch, cVPitch));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String C_TSPM = "cTSPM";
   static int C_TSPM_UPPER_LIMIT = -1;
   java.lang.String cTSPM;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getCTSPM() {
      return cTSPM;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setCTSPM(java.lang.String cTSPM) throws wt.util.WTPropertyVetoException {
      cTSPMValidate(cTSPM);
      this.cTSPM = cTSPM;
   }
   void cTSPMValidate(java.lang.String cTSPM) throws wt.util.WTPropertyVetoException {
      if (C_TSPM_UPPER_LIMIT < 1) {
         try { C_TSPM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cTSPM").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C_TSPM_UPPER_LIMIT = 200; }
      }
      if (cTSPM != null && !wt.fc.PersistenceHelper.checkStoredLength(cTSPM.toString(), C_TSPM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cTSPM"), java.lang.String.valueOf(java.lang.Math.min(C_TSPM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cTSPM", this.cTSPM, cTSPM));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MAKING = "making";
   static int MAKING_UPPER_LIMIT = -1;
   java.lang.String making;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getMaking() {
      return making;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMaking(java.lang.String making) throws wt.util.WTPropertyVetoException {
      makingValidate(making);
      this.making = making;
   }
   void makingValidate(java.lang.String making) throws wt.util.WTPropertyVetoException {
      if (MAKING_UPPER_LIMIT < 1) {
         try { MAKING_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("making").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAKING_UPPER_LIMIT = 200; }
      }
      if (making != null && !wt.fc.PersistenceHelper.checkStoredLength(making.toString(), MAKING_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "making"), java.lang.String.valueOf(java.lang.Math.min(MAKING_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "making", this.making, making));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PRODUCTION_PLACE = "productionPlace";
   static int PRODUCTION_PLACE_UPPER_LIMIT = -1;
   java.lang.String productionPlace;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getProductionPlace() {
      return productionPlace;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setProductionPlace(java.lang.String productionPlace) throws wt.util.WTPropertyVetoException {
      productionPlaceValidate(productionPlace);
      this.productionPlace = productionPlace;
   }
   void productionPlaceValidate(java.lang.String productionPlace) throws wt.util.WTPropertyVetoException {
      if (PRODUCTION_PLACE_UPPER_LIMIT < 1) {
         try { PRODUCTION_PLACE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productionPlace").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCTION_PLACE_UPPER_LIMIT = 200; }
      }
      if (productionPlace != null && !wt.fc.PersistenceHelper.checkStoredLength(productionPlace.toString(), PRODUCTION_PLACE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productionPlace"), java.lang.String.valueOf(java.lang.Math.min(PRODUCTION_PLACE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productionPlace", this.productionPlace, productionPlace));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String THICKNESS = "thickness";
   static int THICKNESS_UPPER_LIMIT = -1;
   java.lang.String thickness;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getThickness() {
      return thickness;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setThickness(java.lang.String thickness) throws wt.util.WTPropertyVetoException {
      thicknessValidate(thickness);
      this.thickness = thickness;
   }
   void thicknessValidate(java.lang.String thickness) throws wt.util.WTPropertyVetoException {
      if (THICKNESS_UPPER_LIMIT < 1) {
         try { THICKNESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("thickness").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { THICKNESS_UPPER_LIMIT = 200; }
      }
      if (thickness != null && !wt.fc.PersistenceHelper.checkStoredLength(thickness.toString(), THICKNESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "thickness"), java.lang.String.valueOf(java.lang.Math.min(THICKNESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "thickness", this.thickness, thickness));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String WIDTH = "width";
   static int WIDTH_UPPER_LIMIT = -1;
   java.lang.String width;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getWidth() {
      return width;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setWidth(java.lang.String width) throws wt.util.WTPropertyVetoException {
      widthValidate(width);
      this.width = width;
   }
   void widthValidate(java.lang.String width) throws wt.util.WTPropertyVetoException {
      if (WIDTH_UPPER_LIMIT < 1) {
         try { WIDTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("width").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WIDTH_UPPER_LIMIT = 200; }
      }
      if (width != null && !wt.fc.PersistenceHelper.checkStoredLength(width.toString(), WIDTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "width"), java.lang.String.valueOf(java.lang.Math.min(WIDTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "width", this.width, width));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PARTNER_NO = "partnerNo";
   static int PARTNER_NO_UPPER_LIMIT = -1;
   java.lang.String partnerNo;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getPartnerNo() {
      return partnerNo;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setPartnerNo(java.lang.String partnerNo) throws wt.util.WTPropertyVetoException {
      partnerNoValidate(partnerNo);
      this.partnerNo = partnerNo;
   }
   void partnerNoValidate(java.lang.String partnerNo) throws wt.util.WTPropertyVetoException {
      if (PARTNER_NO_UPPER_LIMIT < 1) {
         try { PARTNER_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partnerNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PARTNER_NO_UPPER_LIMIT = 200; }
      }
      if (partnerNo != null && !wt.fc.PersistenceHelper.checkStoredLength(partnerNo.toString(), PARTNER_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partnerNo"), java.lang.String.valueOf(java.lang.Math.min(PARTNER_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partnerNo", this.partnerNo, partnerNo));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String SHRINKAGE = "shrinkage";
   static int SHRINKAGE_UPPER_LIMIT = -1;
   java.lang.String shrinkage;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getShrinkage() {
      return shrinkage;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setShrinkage(java.lang.String shrinkage) throws wt.util.WTPropertyVetoException {
      shrinkageValidate(shrinkage);
      this.shrinkage = shrinkage;
   }
   void shrinkageValidate(java.lang.String shrinkage) throws wt.util.WTPropertyVetoException {
      if (SHRINKAGE_UPPER_LIMIT < 1) {
         try { SHRINKAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shrinkage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHRINKAGE_UPPER_LIMIT = 200; }
      }
      if (shrinkage != null && !wt.fc.PersistenceHelper.checkStoredLength(shrinkage.toString(), SHRINKAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shrinkage"), java.lang.String.valueOf(java.lang.Math.min(SHRINKAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shrinkage", this.shrinkage, shrinkage));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String ETC = "etc";
   static int ETC_UPPER_LIMIT = -1;
   java.lang.String etc;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getEtc() {
      return etc;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setEtc(java.lang.String etc) throws wt.util.WTPropertyVetoException {
      etcValidate(etc);
      this.etc = etc;
   }
   void etcValidate(java.lang.String etc) throws wt.util.WTPropertyVetoException {
      if (ETC_UPPER_LIMIT < 1) {
         try { ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_UPPER_LIMIT = 200; }
      }
      if (etc != null && !wt.fc.PersistenceHelper.checkStoredLength(etc.toString(), ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etc"), java.lang.String.valueOf(java.lang.Math.min(ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etc", this.etc, etc));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String ETC1 = "etc1";
   static int ETC1_UPPER_LIMIT = -1;
   java.lang.String etc1;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getEtc1() {
      return etc1;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setEtc1(java.lang.String etc1) throws wt.util.WTPropertyVetoException {
      etc1Validate(etc1);
      this.etc1 = etc1;
   }
   void etc1Validate(java.lang.String etc1) throws wt.util.WTPropertyVetoException {
      if (ETC1_UPPER_LIMIT < 1) {
         try { ETC1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etc1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC1_UPPER_LIMIT = 200; }
      }
      if (etc1 != null && !wt.fc.PersistenceHelper.checkStoredLength(etc1.toString(), ETC1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etc1"), java.lang.String.valueOf(java.lang.Math.min(ETC1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etc1", this.etc1, etc1));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MAKING_PLACE_PARTNER_NO = "makingPlacePartnerNo";
   static int MAKING_PLACE_PARTNER_NO_UPPER_LIMIT = -1;
   java.lang.String makingPlacePartnerNo;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public java.lang.String getMakingPlacePartnerNo() {
      return makingPlacePartnerNo;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMakingPlacePartnerNo(java.lang.String makingPlacePartnerNo) throws wt.util.WTPropertyVetoException {
      makingPlacePartnerNoValidate(makingPlacePartnerNo);
      this.makingPlacePartnerNo = makingPlacePartnerNo;
   }
   void makingPlacePartnerNoValidate(java.lang.String makingPlacePartnerNo) throws wt.util.WTPropertyVetoException {
      if (MAKING_PLACE_PARTNER_NO_UPPER_LIMIT < 1) {
         try { MAKING_PLACE_PARTNER_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("makingPlacePartnerNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAKING_PLACE_PARTNER_NO_UPPER_LIMIT = 200; }
      }
      if (makingPlacePartnerNo != null && !wt.fc.PersistenceHelper.checkStoredLength(makingPlacePartnerNo.toString(), MAKING_PLACE_PARTNER_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "makingPlacePartnerNo"), java.lang.String.valueOf(java.lang.Math.min(MAKING_PLACE_PARTNER_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "makingPlacePartnerNo", this.makingPlacePartnerNo, makingPlacePartnerNo));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.project.ProductProject getProject() {
      return (projectReference != null) ? (e3ps.project.ProductProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setProject(e3ps.project.ProductProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProductProject) the_project));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setProjectReference(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      projectReferenceValidate(the_projectReference);
      projectReference = (wt.fc.ObjectReference) the_projectReference;
   }
   void projectReferenceValidate(wt.fc.ObjectReference the_projectReference) throws wt.util.WTPropertyVetoException {
      if (the_projectReference == null || the_projectReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference") },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
      if (the_projectReference != null && the_projectReference.getReferencedClass() != null &&
            !e3ps.project.ProductProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PRODUCT_TYPE = "productType";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PRODUCT_TYPE_REFERENCE = "productTypeReference";
   wt.fc.ObjectReference productTypeReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.common.code.NumberCode getProductType() {
      return (productTypeReference != null) ? (e3ps.common.code.NumberCode) productTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getProductTypeReference() {
      return productTypeReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setProductType(e3ps.common.code.NumberCode the_productType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProductTypeReference(the_productType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_productType));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setProductTypeReference(wt.fc.ObjectReference the_productTypeReference) throws wt.util.WTPropertyVetoException {
      productTypeReferenceValidate(the_productTypeReference);
      productTypeReference = (wt.fc.ObjectReference) the_productTypeReference;
   }
   void productTypeReferenceValidate(wt.fc.ObjectReference the_productTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_productTypeReference != null && the_productTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_productTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "productTypeReference", this.productTypeReference, productTypeReference));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MOLD_TYPE = "moldType";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MOLD_TYPE_REFERENCE = "moldTypeReference";
   wt.fc.ObjectReference moldTypeReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.common.code.NumberCode getMoldType() {
      return (moldTypeReference != null) ? (e3ps.common.code.NumberCode) moldTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getMoldTypeReference() {
      return moldTypeReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMoldType(e3ps.common.code.NumberCode the_moldType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldTypeReference(the_moldType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_moldType));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMoldTypeReference(wt.fc.ObjectReference the_moldTypeReference) throws wt.util.WTPropertyVetoException {
      moldTypeReferenceValidate(the_moldTypeReference);
      moldTypeReference = (wt.fc.ObjectReference) the_moldTypeReference;
   }
   void moldTypeReferenceValidate(wt.fc.ObjectReference the_moldTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_moldTypeReference != null && the_moldTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_moldTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldTypeReference", this.moldTypeReference, moldTypeReference));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PURCHASE_PLACE = "purchasePlace";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PURCHASE_PLACE_REFERENCE = "purchasePlaceReference";
   wt.fc.ObjectReference purchasePlaceReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.common.code.NumberCode getPurchasePlace() {
      return (purchasePlaceReference != null) ? (e3ps.common.code.NumberCode) purchasePlaceReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getPurchasePlaceReference() {
      return purchasePlaceReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setPurchasePlace(e3ps.common.code.NumberCode the_purchasePlace) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPurchasePlaceReference(the_purchasePlace == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_purchasePlace));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setPurchasePlaceReference(wt.fc.ObjectReference the_purchasePlaceReference) throws wt.util.WTPropertyVetoException {
      purchasePlaceReferenceValidate(the_purchasePlaceReference);
      purchasePlaceReference = (wt.fc.ObjectReference) the_purchasePlaceReference;
   }
   void purchasePlaceReferenceValidate(wt.fc.ObjectReference the_purchasePlaceReference) throws wt.util.WTPropertyVetoException {
      if (the_purchasePlaceReference != null && the_purchasePlaceReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_purchasePlaceReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "purchasePlaceReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "purchasePlaceReference", this.purchasePlaceReference, purchasePlaceReference));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PROPERTY = "property";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String PROPERTY_REFERENCE = "propertyReference";
   wt.fc.ObjectReference propertyReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.common.code.NumberCode getProperty() {
      return (propertyReference != null) ? (e3ps.common.code.NumberCode) propertyReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getPropertyReference() {
      return propertyReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setProperty(e3ps.common.code.NumberCode the_property) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPropertyReference(the_property == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_property));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setPropertyReference(wt.fc.ObjectReference the_propertyReference) throws wt.util.WTPropertyVetoException {
      propertyReferenceValidate(the_propertyReference);
      propertyReference = (wt.fc.ObjectReference) the_propertyReference;
   }
   void propertyReferenceValidate(wt.fc.ObjectReference the_propertyReference) throws wt.util.WTPropertyVetoException {
      if (the_propertyReference != null && the_propertyReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_propertyReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "propertyReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "propertyReference", this.propertyReference, propertyReference));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MATERIAL = "material";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MATERIAL_REFERENCE = "materialReference";
   wt.fc.ObjectReference materialReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.project.material.MoldMaterial getMaterial() {
      return (materialReference != null) ? (e3ps.project.material.MoldMaterial) materialReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getMaterialReference() {
      return materialReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMaterial(e3ps.project.material.MoldMaterial the_material) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMaterialReference(the_material == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.material.MoldMaterial) the_material));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMaterialReference(wt.fc.ObjectReference the_materialReference) throws wt.util.WTPropertyVetoException {
      materialReferenceValidate(the_materialReference);
      materialReference = (wt.fc.ObjectReference) the_materialReference;
   }
   void materialReferenceValidate(wt.fc.ObjectReference the_materialReference) throws wt.util.WTPropertyVetoException {
      if (the_materialReference != null && the_materialReference.getReferencedClass() != null &&
            !e3ps.project.material.MoldMaterial.class.isAssignableFrom(the_materialReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "materialReference", this.materialReference, materialReference));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String COST_INFO = "costInfo";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String COST_INFO_REFERENCE = "costInfoReference";
   wt.fc.ObjectReference costInfoReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.project.CostInfo getCostInfo() {
      return (costInfoReference != null) ? (e3ps.project.CostInfo) costInfoReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getCostInfoReference() {
      return costInfoReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setCostInfo(e3ps.project.CostInfo the_costInfo) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCostInfoReference(the_costInfo == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.CostInfo) the_costInfo));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setCostInfoReference(wt.fc.ObjectReference the_costInfoReference) throws wt.util.WTPropertyVetoException {
      costInfoReferenceValidate(the_costInfoReference);
      costInfoReference = (wt.fc.ObjectReference) the_costInfoReference;
   }
   void costInfoReferenceValidate(wt.fc.ObjectReference the_costInfoReference) throws wt.util.WTPropertyVetoException {
      if (the_costInfoReference == null || the_costInfoReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costInfoReference") },
               new java.beans.PropertyChangeEvent(this, "costInfoReference", this.costInfoReference, costInfoReference));
      if (the_costInfoReference != null && the_costInfoReference.getReferencedClass() != null &&
            !e3ps.project.CostInfo.class.isAssignableFrom(the_costInfoReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costInfoReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "costInfoReference", this.costInfoReference, costInfoReference));
   }

   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MAKING_PLACE = "makingPlace";
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public static final java.lang.String MAKING_PLACE_REFERENCE = "makingPlaceReference";
   wt.fc.ObjectReference makingPlaceReference;
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public e3ps.common.code.NumberCode getMakingPlace() {
      return (makingPlaceReference != null) ? (e3ps.common.code.NumberCode) makingPlaceReference.getObject() : null;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public wt.fc.ObjectReference getMakingPlaceReference() {
      return makingPlaceReference;
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMakingPlace(e3ps.common.code.NumberCode the_makingPlace) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMakingPlaceReference(the_makingPlace == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_makingPlace));
   }
   /**
    * @see e3ps.project.MoldItemInfo
    */
   public void setMakingPlaceReference(wt.fc.ObjectReference the_makingPlaceReference) throws wt.util.WTPropertyVetoException {
      makingPlaceReferenceValidate(the_makingPlaceReference);
      makingPlaceReference = (wt.fc.ObjectReference) the_makingPlaceReference;
   }
   void makingPlaceReferenceValidate(wt.fc.ObjectReference the_makingPlaceReference) throws wt.util.WTPropertyVetoException {
      if (the_makingPlaceReference != null && the_makingPlaceReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_makingPlaceReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "makingPlaceReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "makingPlaceReference", this.makingPlaceReference, makingPlaceReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -956624276649557294L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( cTSPM );
      output.writeObject( cVPitch );
      output.writeObject( costInfoReference );
      output.writeObject( dieNo );
      output.writeObject( etc );
      output.writeObject( etc1 );
      output.writeObject( itemType );
      output.writeObject( making );
      output.writeObject( makingPlacePartnerNo );
      output.writeObject( makingPlaceReference );
      output.writeObject( materialReference );
      output.writeObject( moldTypeReference );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( partnerNo );
      output.writeObject( productTypeReference );
      output.writeObject( productionPlace );
      output.writeObject( projectReference );
      output.writeObject( propertyReference );
      output.writeObject( purchasePlaceReference );
      output.writeObject( shrinkage );
      output.writeObject( thePersistInfo );
      output.writeObject( thickness );
      output.writeObject( width );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.MoldItemInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "cTSPM", cTSPM );
      output.setString( "cVPitch", cVPitch );
      output.writeObject( "costInfoReference", costInfoReference, wt.fc.ObjectReference.class, true );
      output.setString( "dieNo", dieNo );
      output.setString( "etc", etc );
      output.setString( "etc1", etc1 );
      output.setString( "itemType", itemType );
      output.setString( "making", making );
      output.setString( "makingPlacePartnerNo", makingPlacePartnerNo );
      output.writeObject( "makingPlaceReference", makingPlaceReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "moldTypeReference", moldTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "partnerNo", partnerNo );
      output.writeObject( "productTypeReference", productTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "productionPlace", productionPlace );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "propertyReference", propertyReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "purchasePlaceReference", purchasePlaceReference, wt.fc.ObjectReference.class, true );
      output.setString( "shrinkage", shrinkage );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "thickness", thickness );
      output.setString( "width", width );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      cTSPM = input.getString( "cTSPM" );
      cVPitch = input.getString( "cVPitch" );
      costInfoReference = (wt.fc.ObjectReference) input.readObject( "costInfoReference", costInfoReference, wt.fc.ObjectReference.class, true );
      dieNo = input.getString( "dieNo" );
      etc = input.getString( "etc" );
      etc1 = input.getString( "etc1" );
      itemType = input.getString( "itemType" );
      making = input.getString( "making" );
      makingPlacePartnerNo = input.getString( "makingPlacePartnerNo" );
      makingPlaceReference = (wt.fc.ObjectReference) input.readObject( "makingPlaceReference", makingPlaceReference, wt.fc.ObjectReference.class, true );
      materialReference = (wt.fc.ObjectReference) input.readObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      moldTypeReference = (wt.fc.ObjectReference) input.readObject( "moldTypeReference", moldTypeReference, wt.fc.ObjectReference.class, true );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      partnerNo = input.getString( "partnerNo" );
      productTypeReference = (wt.fc.ObjectReference) input.readObject( "productTypeReference", productTypeReference, wt.fc.ObjectReference.class, true );
      productionPlace = input.getString( "productionPlace" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      propertyReference = (wt.fc.ObjectReference) input.readObject( "propertyReference", propertyReference, wt.fc.ObjectReference.class, true );
      purchasePlaceReference = (wt.fc.ObjectReference) input.readObject( "purchasePlaceReference", purchasePlaceReference, wt.fc.ObjectReference.class, true );
      shrinkage = input.getString( "shrinkage" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      thickness = input.getString( "thickness" );
      width = input.getString( "width" );
   }

   boolean readVersion_956624276649557294L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      cTSPM = (java.lang.String) input.readObject();
      cVPitch = (java.lang.String) input.readObject();
      costInfoReference = (wt.fc.ObjectReference) input.readObject();
      dieNo = (java.lang.String) input.readObject();
      etc = (java.lang.String) input.readObject();
      etc1 = (java.lang.String) input.readObject();
      itemType = (java.lang.String) input.readObject();
      making = (java.lang.String) input.readObject();
      makingPlacePartnerNo = (java.lang.String) input.readObject();
      makingPlaceReference = (wt.fc.ObjectReference) input.readObject();
      materialReference = (wt.fc.ObjectReference) input.readObject();
      moldTypeReference = (wt.fc.ObjectReference) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      partnerNo = (java.lang.String) input.readObject();
      productTypeReference = (wt.fc.ObjectReference) input.readObject();
      productionPlace = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      propertyReference = (wt.fc.ObjectReference) input.readObject();
      purchasePlaceReference = (wt.fc.ObjectReference) input.readObject();
      shrinkage = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      thickness = (java.lang.String) input.readObject();
      width = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( MoldItemInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_956624276649557294L( input, readSerialVersionUID, superDone );
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
