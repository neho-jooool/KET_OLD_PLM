package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostPart implements e3ps.common.impl.Tree, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostPart.class.getName();

   /**
    * 버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String VERSION = "version";
   java.lang.Integer version = 0;
   /**
    * 버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.Integer getVersion() {
      return version;
   }
   /**
    * 버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setVersion(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
      versionValidate(version);
      this.version = version;
   }
   void versionValidate(java.lang.Integer version) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 개발/검토
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DEV_TYPE = "devType";
   static int DEV_TYPE_UPPER_LIMIT = -1;
   java.lang.String devType = "0";
   /**
    * 개발/검토
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDevType() {
      return devType;
   }
   /**
    * 개발/검토
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDevType(java.lang.String devType) throws wt.util.WTPropertyVetoException {
      devTypeValidate(devType);
      this.devType = devType;
   }
   void devTypeValidate(java.lang.String devType) throws wt.util.WTPropertyVetoException {
      if (DEV_TYPE_UPPER_LIMIT < 1) {
         try { DEV_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_TYPE_UPPER_LIMIT = 200; }
      }
      if (devType != null && !wt.fc.PersistenceHelper.checkStoredLength(devType.toString(), DEV_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devType"), java.lang.String.valueOf(java.lang.Math.min(DEV_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devType", this.devType, devType));
   }

   /**
    * 제품구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PART_TYPE = "partType";
   static int PART_TYPE_UPPER_LIMIT = -1;
   java.lang.String partType;
   /**
    * 제품구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPartType() {
      return partType;
   }
   /**
    * 제품구분
    *
    * @see ext.ket.cost.entity.CostPart
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
    * 레벨
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String BOM_LEVEL = "bomLevel";
   static int BOM_LEVEL_UPPER_LIMIT = -1;
   java.lang.String bomLevel;
   /**
    * 레벨
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getBomLevel() {
      return bomLevel;
   }
   /**
    * 레벨
    *
    * @see ext.ket.cost.entity.CostPart
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
    * 제품 SIZE W
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SIZE_W = "sizeW";
   static int SIZE_W_UPPER_LIMIT = -1;
   java.lang.String sizeW = "0";
   /**
    * 제품 SIZE W
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSizeW() {
      return sizeW;
   }
   /**
    * 제품 SIZE W
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSizeW(java.lang.String sizeW) throws wt.util.WTPropertyVetoException {
      sizeWValidate(sizeW);
      this.sizeW = sizeW;
   }
   void sizeWValidate(java.lang.String sizeW) throws wt.util.WTPropertyVetoException {
      if (SIZE_W_UPPER_LIMIT < 1) {
         try { SIZE_W_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sizeW").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SIZE_W_UPPER_LIMIT = 200; }
      }
      if (sizeW != null && !wt.fc.PersistenceHelper.checkStoredLength(sizeW.toString(), SIZE_W_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sizeW"), java.lang.String.valueOf(java.lang.Math.min(SIZE_W_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sizeW", this.sizeW, sizeW));
   }

   /**
    * 제품 SIZE L
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SIZE_L = "sizeL";
   static int SIZE_L_UPPER_LIMIT = -1;
   java.lang.String sizeL = "0";
   /**
    * 제품 SIZE L
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSizeL() {
      return sizeL;
   }
   /**
    * 제품 SIZE L
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSizeL(java.lang.String sizeL) throws wt.util.WTPropertyVetoException {
      sizeLValidate(sizeL);
      this.sizeL = sizeL;
   }
   void sizeLValidate(java.lang.String sizeL) throws wt.util.WTPropertyVetoException {
      if (SIZE_L_UPPER_LIMIT < 1) {
         try { SIZE_L_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sizeL").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SIZE_L_UPPER_LIMIT = 200; }
      }
      if (sizeL != null && !wt.fc.PersistenceHelper.checkStoredLength(sizeL.toString(), SIZE_L_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sizeL"), java.lang.String.valueOf(java.lang.Math.min(SIZE_L_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sizeL", this.sizeL, sizeL));
   }

   /**
    * 제품 SIZE H
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SIZE_H = "sizeH";
   static int SIZE_H_UPPER_LIMIT = -1;
   java.lang.String sizeH = "0";
   /**
    * 제품 SIZE H
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSizeH() {
      return sizeH;
   }
   /**
    * 제품 SIZE H
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSizeH(java.lang.String sizeH) throws wt.util.WTPropertyVetoException {
      sizeHValidate(sizeH);
      this.sizeH = sizeH;
   }
   void sizeHValidate(java.lang.String sizeH) throws wt.util.WTPropertyVetoException {
      if (SIZE_H_UPPER_LIMIT < 1) {
         try { SIZE_H_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sizeH").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SIZE_H_UPPER_LIMIT = 200; }
      }
      if (sizeH != null && !wt.fc.PersistenceHelper.checkStoredLength(sizeH.toString(), SIZE_H_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sizeH"), java.lang.String.valueOf(java.lang.Math.min(SIZE_H_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sizeH", this.sizeH, sizeH));
   }

   /**
    * 임시번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 임시번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 임시번호
    *
    * @see ext.ket.cost.entity.CostPart
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
    * 품명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * 품명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * 품명
    *
    * @see ext.ket.cost.entity.CostPart
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
    * U/S
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String US = "us";
   static int US_UPPER_LIMIT = -1;
   java.lang.String us;
   /**
    * U/S
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getUs() {
      return us;
   }
   /**
    * U/S
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setUs(java.lang.String us) throws wt.util.WTPropertyVetoException {
      usValidate(us);
      this.us = us;
   }
   void usValidate(java.lang.String us) throws wt.util.WTPropertyVetoException {
      if (US_UPPER_LIMIT < 1) {
         try { US_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("us").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { US_UPPER_LIMIT = 200; }
      }
      if (us != null && !wt.fc.PersistenceHelper.checkStoredLength(us.toString(), US_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "us"), java.lang.String.valueOf(java.lang.Math.min(US_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "us", this.us, us));
   }

   /**
    * 생산정보 - C/V - 금형
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CV_MOLD = "cvMold";
   static int CV_MOLD_UPPER_LIMIT = -1;
   java.lang.String cvMold = "0";
   /**
    * 생산정보 - C/V - 금형
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCvMold() {
      return cvMold;
   }
   /**
    * 생산정보 - C/V - 금형
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCvMold(java.lang.String cvMold) throws wt.util.WTPropertyVetoException {
      cvMoldValidate(cvMold);
      this.cvMold = cvMold;
   }
   void cvMoldValidate(java.lang.String cvMold) throws wt.util.WTPropertyVetoException {
      if (CV_MOLD_UPPER_LIMIT < 1) {
         try { CV_MOLD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cvMold").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CV_MOLD_UPPER_LIMIT = 200; }
      }
      if (cvMold != null && !wt.fc.PersistenceHelper.checkStoredLength(cvMold.toString(), CV_MOLD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cvMold"), java.lang.String.valueOf(java.lang.Math.min(CV_MOLD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cvMold", this.cvMold, cvMold));
   }

   /**
    * 생산정보 - C/V - 조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CV_ASSEMBLE = "cvAssemble";
   static int CV_ASSEMBLE_UPPER_LIMIT = -1;
   java.lang.String cvAssemble = "0";
   /**
    * 생산정보 - C/V - 조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCvAssemble() {
      return cvAssemble;
   }
   /**
    * 생산정보 - C/V - 조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCvAssemble(java.lang.String cvAssemble) throws wt.util.WTPropertyVetoException {
      cvAssembleValidate(cvAssemble);
      this.cvAssemble = cvAssemble;
   }
   void cvAssembleValidate(java.lang.String cvAssemble) throws wt.util.WTPropertyVetoException {
      if (CV_ASSEMBLE_UPPER_LIMIT < 1) {
         try { CV_ASSEMBLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cvAssemble").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CV_ASSEMBLE_UPPER_LIMIT = 200; }
      }
      if (cvAssemble != null && !wt.fc.PersistenceHelper.checkStoredLength(cvAssemble.toString(), CV_ASSEMBLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cvAssemble"), java.lang.String.valueOf(java.lang.Math.min(CV_ASSEMBLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cvAssemble", this.cvAssemble, cvAssemble));
   }

   /**
    * 생산정보 - C/T&SPM - 금형
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CT_SPMMOLD = "ctSPMMold";
   static int CT_SPMMOLD_UPPER_LIMIT = -1;
   java.lang.String ctSPMMold = "0";
   /**
    * 생산정보 - C/T&SPM - 금형
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCtSPMMold() {
      return ctSPMMold;
   }
   /**
    * 생산정보 - C/T&SPM - 금형
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCtSPMMold(java.lang.String ctSPMMold) throws wt.util.WTPropertyVetoException {
      ctSPMMoldValidate(ctSPMMold);
      this.ctSPMMold = ctSPMMold;
   }
   void ctSPMMoldValidate(java.lang.String ctSPMMold) throws wt.util.WTPropertyVetoException {
      if (CT_SPMMOLD_UPPER_LIMIT < 1) {
         try { CT_SPMMOLD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ctSPMMold").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CT_SPMMOLD_UPPER_LIMIT = 200; }
      }
      if (ctSPMMold != null && !wt.fc.PersistenceHelper.checkStoredLength(ctSPMMold.toString(), CT_SPMMOLD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ctSPMMold"), java.lang.String.valueOf(java.lang.Math.min(CT_SPMMOLD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ctSPMMold", this.ctSPMMold, ctSPMMold));
   }

   /**
    * 생산정보 - C/T&SPM - 조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CT_SPMASSEMBLE = "ctSPMAssemble";
   static int CT_SPMASSEMBLE_UPPER_LIMIT = -1;
   java.lang.String ctSPMAssemble = "0";
   /**
    * 생산정보 - C/T&SPM - 조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCtSPMAssemble() {
      return ctSPMAssemble;
   }
   /**
    * 생산정보 - C/T&SPM - 조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCtSPMAssemble(java.lang.String ctSPMAssemble) throws wt.util.WTPropertyVetoException {
      ctSPMAssembleValidate(ctSPMAssemble);
      this.ctSPMAssemble = ctSPMAssemble;
   }
   void ctSPMAssembleValidate(java.lang.String ctSPMAssemble) throws wt.util.WTPropertyVetoException {
      if (CT_SPMASSEMBLE_UPPER_LIMIT < 1) {
         try { CT_SPMASSEMBLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ctSPMAssemble").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CT_SPMASSEMBLE_UPPER_LIMIT = 200; }
      }
      if (ctSPMAssemble != null && !wt.fc.PersistenceHelper.checkStoredLength(ctSPMAssemble.toString(), CT_SPMASSEMBLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ctSPMAssemble"), java.lang.String.valueOf(java.lang.Math.min(CT_SPMASSEMBLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ctSPMAssemble", this.ctSPMAssemble, ctSPMAssemble));
   }

   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MFT_FACTORY1 = "mftFactory1";
   static int MFT_FACTORY1_UPPER_LIMIT = -1;
   java.lang.String mftFactory1;
   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMftFactory1() {
      return mftFactory1;
   }
   /**
    * 생산정보 - 생산국(입고처)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMftFactory1(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      mftFactory1Validate(mftFactory1);
      this.mftFactory1 = mftFactory1;
   }
   void mftFactory1Validate(java.lang.String mftFactory1) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY1_UPPER_LIMIT < 1) {
         try { MFT_FACTORY1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY1_UPPER_LIMIT = 200; }
      }
      if (mftFactory1 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory1.toString(), MFT_FACTORY1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory1"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory1", this.mftFactory1, mftFactory1));
   }

   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MFT_FACTORY2 = "mftFactory2";
   static int MFT_FACTORY2_UPPER_LIMIT = -1;
   java.lang.String mftFactory2;
   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMftFactory2() {
      return mftFactory2;
   }
   /**
    * 생산지(유/무상)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMftFactory2(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      mftFactory2Validate(mftFactory2);
      this.mftFactory2 = mftFactory2;
   }
   void mftFactory2Validate(java.lang.String mftFactory2) throws wt.util.WTPropertyVetoException {
      if (MFT_FACTORY2_UPPER_LIMIT < 1) {
         try { MFT_FACTORY2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mftFactory2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFT_FACTORY2_UPPER_LIMIT = 200; }
      }
      if (mftFactory2 != null && !wt.fc.PersistenceHelper.checkStoredLength(mftFactory2.toString(), MFT_FACTORY2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mftFactory2"), java.lang.String.valueOf(java.lang.Math.min(MFT_FACTORY2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mftFactory2", this.mftFactory2, mftFactory2));
   }

   /**
    * 생산정보 - 업체명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String COMPANY = "company";
   static int COMPANY_UPPER_LIMIT = -1;
   java.lang.String company;
   /**
    * 생산정보 - 업체명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCompany() {
      return company;
   }
   /**
    * 생산정보 - 업체명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCompany(java.lang.String company) throws wt.util.WTPropertyVetoException {
      companyValidate(company);
      this.company = company;
   }
   void companyValidate(java.lang.String company) throws wt.util.WTPropertyVetoException {
      if (COMPANY_UPPER_LIMIT < 1) {
         try { COMPANY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("company").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMPANY_UPPER_LIMIT = 200; }
      }
      if (company != null && !wt.fc.PersistenceHelper.checkStoredLength(company.toString(), COMPANY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "company"), java.lang.String.valueOf(java.lang.Math.min(COMPANY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "company", this.company, company));
   }

   /**
    * 생산정보 - 적용설비(Ton)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FACILITIES = "facilities";
   static int FACILITIES_UPPER_LIMIT = -1;
   java.lang.String facilities = "0";
   /**
    * 생산정보 - 적용설비(Ton)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacilities() {
      return facilities;
   }
   /**
    * 생산정보 - 적용설비(Ton)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacilities(java.lang.String facilities) throws wt.util.WTPropertyVetoException {
      facilitiesValidate(facilities);
      this.facilities = facilities;
   }
   void facilitiesValidate(java.lang.String facilities) throws wt.util.WTPropertyVetoException {
      if (FACILITIES_UPPER_LIMIT < 1) {
         try { FACILITIES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facilities").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FACILITIES_UPPER_LIMIT = 200; }
      }
      if (facilities != null && !wt.fc.PersistenceHelper.checkStoredLength(facilities.toString(), FACILITIES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facilities"), java.lang.String.valueOf(java.lang.Math.min(FACILITIES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facilities", this.facilities, facilities));
   }

   /**
    * 생산정보 - 작업인원(명)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String WORKER = "worker";
   static int WORKER_UPPER_LIMIT = -1;
   java.lang.String worker = "0";
   /**
    * 생산정보 - 작업인원(명)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getWorker() {
      return worker;
   }
   /**
    * 생산정보 - 작업인원(명)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setWorker(java.lang.String worker) throws wt.util.WTPropertyVetoException {
      workerValidate(worker);
      this.worker = worker;
   }
   void workerValidate(java.lang.String worker) throws wt.util.WTPropertyVetoException {
      if (WORKER_UPPER_LIMIT < 1) {
         try { WORKER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("worker").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORKER_UPPER_LIMIT = 200; }
      }
      if (worker != null && !wt.fc.PersistenceHelper.checkStoredLength(worker.toString(), WORKER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "worker"), java.lang.String.valueOf(java.lang.Math.min(WORKER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "worker", this.worker, worker));
   }

   /**
    * 구매/외주 - 선적조건
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String TOS = "tos";
   static int TOS_UPPER_LIMIT = -1;
   java.lang.String tos;
   /**
    * 구매/외주 - 선적조건
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getTos() {
      return tos;
   }
   /**
    * 구매/외주 - 선적조건
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setTos(java.lang.String tos) throws wt.util.WTPropertyVetoException {
      tosValidate(tos);
      this.tos = tos;
   }
   void tosValidate(java.lang.String tos) throws wt.util.WTPropertyVetoException {
      if (TOS_UPPER_LIMIT < 1) {
         try { TOS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tos").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOS_UPPER_LIMIT = 200; }
      }
      if (tos != null && !wt.fc.PersistenceHelper.checkStoredLength(tos.toString(), TOS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tos"), java.lang.String.valueOf(java.lang.Math.min(TOS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tos", this.tos, tos));
   }

   /**
    * 구매/외주 - 관세(%)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DUTY = "duty";
   static int DUTY_UPPER_LIMIT = -1;
   java.lang.String duty = "0";
   /**
    * 구매/외주 - 관세(%)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDuty() {
      return duty;
   }
   /**
    * 구매/외주 - 관세(%)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDuty(java.lang.String duty) throws wt.util.WTPropertyVetoException {
      dutyValidate(duty);
      this.duty = duty;
   }
   void dutyValidate(java.lang.String duty) throws wt.util.WTPropertyVetoException {
      if (DUTY_UPPER_LIMIT < 1) {
         try { DUTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("duty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DUTY_UPPER_LIMIT = 200; }
      }
      if (duty != null && !wt.fc.PersistenceHelper.checkStoredLength(duty.toString(), DUTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "duty"), java.lang.String.valueOf(java.lang.Math.min(DUTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "duty", this.duty, duty));
   }

   /**
    * 구매/외주 - 물류비(%)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DISTRIBUTION_COST = "distributionCost";
   static int DISTRIBUTION_COST_UPPER_LIMIT = -1;
   java.lang.String distributionCost = "0";
   /**
    * 구매/외주 - 물류비(%)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDistributionCost() {
      return distributionCost;
   }
   /**
    * 구매/외주 - 물류비(%)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDistributionCost(java.lang.String distributionCost) throws wt.util.WTPropertyVetoException {
      distributionCostValidate(distributionCost);
      this.distributionCost = distributionCost;
   }
   void distributionCostValidate(java.lang.String distributionCost) throws wt.util.WTPropertyVetoException {
      if (DISTRIBUTION_COST_UPPER_LIMIT < 1) {
         try { DISTRIBUTION_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("distributionCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DISTRIBUTION_COST_UPPER_LIMIT = 200; }
      }
      if (distributionCost != null && !wt.fc.PersistenceHelper.checkStoredLength(distributionCost.toString(), DISTRIBUTION_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "distributionCost"), java.lang.String.valueOf(java.lang.Math.min(DISTRIBUTION_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "distributionCost", this.distributionCost, distributionCost));
   }

   /**
    * 구매/외주 - 구매단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String P_UNIT_COST = "pUnitCost";
   static int P_UNIT_COST_UPPER_LIMIT = -1;
   java.lang.String pUnitCost = "0";
   /**
    * 구매/외주 - 구매단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPUnitCost() {
      return pUnitCost;
   }
   /**
    * 구매/외주 - 구매단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPUnitCost(java.lang.String pUnitCost) throws wt.util.WTPropertyVetoException {
      pUnitCostValidate(pUnitCost);
      this.pUnitCost = pUnitCost;
   }
   void pUnitCostValidate(java.lang.String pUnitCost) throws wt.util.WTPropertyVetoException {
      if (P_UNIT_COST_UPPER_LIMIT < 1) {
         try { P_UNIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pUnitCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_UNIT_COST_UPPER_LIMIT = 200; }
      }
      if (pUnitCost != null && !wt.fc.PersistenceHelper.checkStoredLength(pUnitCost.toString(), P_UNIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pUnitCost"), java.lang.String.valueOf(java.lang.Math.min(P_UNIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pUnitCost", this.pUnitCost, pUnitCost));
   }

   /**
    * 구매/외주 - 구매단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String P_MONETARY_UNIT = "pMonetaryUnit";
   static int P_MONETARY_UNIT_UPPER_LIMIT = -1;
   java.lang.String pMonetaryUnit;
   /**
    * 구매/외주 - 구매단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPMonetaryUnit() {
      return pMonetaryUnit;
   }
   /**
    * 구매/외주 - 구매단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPMonetaryUnit(java.lang.String pMonetaryUnit) throws wt.util.WTPropertyVetoException {
      pMonetaryUnitValidate(pMonetaryUnit);
      this.pMonetaryUnit = pMonetaryUnit;
   }
   void pMonetaryUnitValidate(java.lang.String pMonetaryUnit) throws wt.util.WTPropertyVetoException {
      if (P_MONETARY_UNIT_UPPER_LIMIT < 1) {
         try { P_MONETARY_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pMonetaryUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_MONETARY_UNIT_UPPER_LIMIT = 200; }
      }
      if (pMonetaryUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(pMonetaryUnit.toString(), P_MONETARY_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pMonetaryUnit"), java.lang.String.valueOf(java.lang.Math.min(P_MONETARY_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pMonetaryUnit", this.pMonetaryUnit, pMonetaryUnit));
   }

   /**
    * 구매/외주 - 후도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String AP_UNIT_COST = "apUnitCost";
   static int AP_UNIT_COST_UPPER_LIMIT = -1;
   java.lang.String apUnitCost = "0";
   /**
    * 구매/외주 - 후도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getApUnitCost() {
      return apUnitCost;
   }
   /**
    * 구매/외주 - 후도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setApUnitCost(java.lang.String apUnitCost) throws wt.util.WTPropertyVetoException {
      apUnitCostValidate(apUnitCost);
      this.apUnitCost = apUnitCost;
   }
   void apUnitCostValidate(java.lang.String apUnitCost) throws wt.util.WTPropertyVetoException {
      if (AP_UNIT_COST_UPPER_LIMIT < 1) {
         try { AP_UNIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apUnitCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_UNIT_COST_UPPER_LIMIT = 200; }
      }
      if (apUnitCost != null && !wt.fc.PersistenceHelper.checkStoredLength(apUnitCost.toString(), AP_UNIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apUnitCost"), java.lang.String.valueOf(java.lang.Math.min(AP_UNIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apUnitCost", this.apUnitCost, apUnitCost));
   }

   /**
    * 구매/외주 - 후도금단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String AP_MONETARY_UNIT = "apMonetaryUnit";
   static int AP_MONETARY_UNIT_UPPER_LIMIT = -1;
   java.lang.String apMonetaryUnit;
   /**
    * 구매/외주 - 후도금단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getApMonetaryUnit() {
      return apMonetaryUnit;
   }
   /**
    * 구매/외주 - 후도금단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setApMonetaryUnit(java.lang.String apMonetaryUnit) throws wt.util.WTPropertyVetoException {
      apMonetaryUnitValidate(apMonetaryUnit);
      this.apMonetaryUnit = apMonetaryUnit;
   }
   void apMonetaryUnitValidate(java.lang.String apMonetaryUnit) throws wt.util.WTPropertyVetoException {
      if (AP_MONETARY_UNIT_UPPER_LIMIT < 1) {
         try { AP_MONETARY_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apMonetaryUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_MONETARY_UNIT_UPPER_LIMIT = 200; }
      }
      if (apMonetaryUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(apMonetaryUnit.toString(), AP_MONETARY_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apMonetaryUnit"), java.lang.String.valueOf(java.lang.Math.min(AP_MONETARY_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apMonetaryUnit", this.apMonetaryUnit, apMonetaryUnit));
   }

   /**
    * 구매/외주 - 후도금 사양
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String AP_PLATING_SPEC = "apPlatingSpec";
   static int AP_PLATING_SPEC_UPPER_LIMIT = -1;
   java.lang.String apPlatingSpec;
   /**
    * 구매/외주 - 후도금 사양
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getApPlatingSpec() {
      return apPlatingSpec;
   }
   /**
    * 구매/외주 - 후도금 사양
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setApPlatingSpec(java.lang.String apPlatingSpec) throws wt.util.WTPropertyVetoException {
      apPlatingSpecValidate(apPlatingSpec);
      this.apPlatingSpec = apPlatingSpec;
   }
   void apPlatingSpecValidate(java.lang.String apPlatingSpec) throws wt.util.WTPropertyVetoException {
      if (AP_PLATING_SPEC_UPPER_LIMIT < 1) {
         try { AP_PLATING_SPEC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apPlatingSpec").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_PLATING_SPEC_UPPER_LIMIT = 200; }
      }
      if (apPlatingSpec != null && !wt.fc.PersistenceHelper.checkStoredLength(apPlatingSpec.toString(), AP_PLATING_SPEC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apPlatingSpec"), java.lang.String.valueOf(java.lang.Math.min(AP_PLATING_SPEC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apPlatingSpec", this.apPlatingSpec, apPlatingSpec));
   }

   /**
    * 구매/외주 - 후도금단가(환율적용)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String AP_UNIT_COST_VAL = "apUnitCostVal";
   static int AP_UNIT_COST_VAL_UPPER_LIMIT = -1;
   java.lang.String apUnitCostVal = "0";
   /**
    * 구매/외주 - 후도금단가(환율적용)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getApUnitCostVal() {
      return apUnitCostVal;
   }
   /**
    * 구매/외주 - 후도금단가(환율적용)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setApUnitCostVal(java.lang.String apUnitCostVal) throws wt.util.WTPropertyVetoException {
      apUnitCostValValidate(apUnitCostVal);
      this.apUnitCostVal = apUnitCostVal;
   }
   void apUnitCostValValidate(java.lang.String apUnitCostVal) throws wt.util.WTPropertyVetoException {
      if (AP_UNIT_COST_VAL_UPPER_LIMIT < 1) {
         try { AP_UNIT_COST_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apUnitCostVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_UNIT_COST_VAL_UPPER_LIMIT = 200; }
      }
      if (apUnitCostVal != null && !wt.fc.PersistenceHelper.checkStoredLength(apUnitCostVal.toString(), AP_UNIT_COST_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apUnitCostVal"), java.lang.String.valueOf(java.lang.Math.min(AP_UNIT_COST_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apUnitCostVal", this.apUnitCostVal, apUnitCostVal));
   }

   /**
    * 구매/외주 - 외주단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String OUT_UNIT_COST = "outUnitCost";
   static int OUT_UNIT_COST_UPPER_LIMIT = -1;
   java.lang.String outUnitCost = "0";
   /**
    * 구매/외주 - 외주단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getOutUnitCost() {
      return outUnitCost;
   }
   /**
    * 구매/외주 - 외주단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setOutUnitCost(java.lang.String outUnitCost) throws wt.util.WTPropertyVetoException {
      outUnitCostValidate(outUnitCost);
      this.outUnitCost = outUnitCost;
   }
   void outUnitCostValidate(java.lang.String outUnitCost) throws wt.util.WTPropertyVetoException {
      if (OUT_UNIT_COST_UPPER_LIMIT < 1) {
         try { OUT_UNIT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outUnitCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_UNIT_COST_UPPER_LIMIT = 200; }
      }
      if (outUnitCost != null && !wt.fc.PersistenceHelper.checkStoredLength(outUnitCost.toString(), OUT_UNIT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outUnitCost"), java.lang.String.valueOf(java.lang.Math.min(OUT_UNIT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outUnitCost", this.outUnitCost, outUnitCost));
   }

   /**
    * 구매/외주 - 외주단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String OUT_MONETARY_UNIT = "outMonetaryUnit";
   static int OUT_MONETARY_UNIT_UPPER_LIMIT = -1;
   java.lang.String outMonetaryUnit;
   /**
    * 구매/외주 - 외주단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getOutMonetaryUnit() {
      return outMonetaryUnit;
   }
   /**
    * 구매/외주 - 외주단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setOutMonetaryUnit(java.lang.String outMonetaryUnit) throws wt.util.WTPropertyVetoException {
      outMonetaryUnitValidate(outMonetaryUnit);
      this.outMonetaryUnit = outMonetaryUnit;
   }
   void outMonetaryUnitValidate(java.lang.String outMonetaryUnit) throws wt.util.WTPropertyVetoException {
      if (OUT_MONETARY_UNIT_UPPER_LIMIT < 1) {
         try { OUT_MONETARY_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outMonetaryUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_MONETARY_UNIT_UPPER_LIMIT = 200; }
      }
      if (outMonetaryUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(outMonetaryUnit.toString(), OUT_MONETARY_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outMonetaryUnit"), java.lang.String.valueOf(java.lang.Math.min(OUT_MONETARY_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outMonetaryUnit", this.outMonetaryUnit, outMonetaryUnit));
   }

   /**
    * 구매/외주 - 외주단가(환율적용)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String OUT_UNIT_COST_VAL = "outUnitCostVal";
   static int OUT_UNIT_COST_VAL_UPPER_LIMIT = -1;
   java.lang.String outUnitCostVal = "0";
   /**
    * 구매/외주 - 외주단가(환율적용)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getOutUnitCostVal() {
      return outUnitCostVal;
   }
   /**
    * 구매/외주 - 외주단가(환율적용)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setOutUnitCostVal(java.lang.String outUnitCostVal) throws wt.util.WTPropertyVetoException {
      outUnitCostValValidate(outUnitCostVal);
      this.outUnitCostVal = outUnitCostVal;
   }
   void outUnitCostValValidate(java.lang.String outUnitCostVal) throws wt.util.WTPropertyVetoException {
      if (OUT_UNIT_COST_VAL_UPPER_LIMIT < 1) {
         try { OUT_UNIT_COST_VAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outUnitCostVal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_UNIT_COST_VAL_UPPER_LIMIT = 200; }
      }
      if (outUnitCostVal != null && !wt.fc.PersistenceHelper.checkStoredLength(outUnitCostVal.toString(), OUT_UNIT_COST_VAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outUnitCostVal"), java.lang.String.valueOf(java.lang.Math.min(OUT_UNIT_COST_VAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outUnitCostVal", this.outUnitCostVal, outUnitCostVal));
   }

   /**
    * 제품중량 - 제품중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRODUCT_WEIGHT = "productWeight";
   static int PRODUCT_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String productWeight = "0";
   /**
    * 제품중량 - 제품중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getProductWeight() {
      return productWeight;
   }
   /**
    * 제품중량 - 제품중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProductWeight(java.lang.String productWeight) throws wt.util.WTPropertyVetoException {
      productWeightValidate(productWeight);
      this.productWeight = productWeight;
   }
   void productWeightValidate(java.lang.String productWeight) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_WEIGHT_UPPER_LIMIT < 1) {
         try { PRODUCT_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (productWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(productWeight.toString(), PRODUCT_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productWeight"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productWeight", this.productWeight, productWeight));
   }

   /**
    * 제품중량 - 스크랩중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SCRAP_WEIGHT = "scrapWeight";
   static int SCRAP_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String scrapWeight = "0";
   /**
    * 제품중량 - 스크랩중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getScrapWeight() {
      return scrapWeight;
   }
   /**
    * 제품중량 - 스크랩중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setScrapWeight(java.lang.String scrapWeight) throws wt.util.WTPropertyVetoException {
      scrapWeightValidate(scrapWeight);
      this.scrapWeight = scrapWeight;
   }
   void scrapWeightValidate(java.lang.String scrapWeight) throws wt.util.WTPropertyVetoException {
      if (SCRAP_WEIGHT_UPPER_LIMIT < 1) {
         try { SCRAP_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (scrapWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapWeight.toString(), SCRAP_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapWeight"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapWeight", this.scrapWeight, scrapWeight));
   }

   /**
    * 제품중량 - 총중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String TOTAL_WEIGHT = "totalWeight";
   static int TOTAL_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String totalWeight = "0";
   /**
    * 제품중량 - 총중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getTotalWeight() {
      return totalWeight;
   }
   /**
    * 제품중량 - 총중량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setTotalWeight(java.lang.String totalWeight) throws wt.util.WTPropertyVetoException {
      totalWeightValidate(totalWeight);
      this.totalWeight = totalWeight;
   }
   void totalWeightValidate(java.lang.String totalWeight) throws wt.util.WTPropertyVetoException {
      if (TOTAL_WEIGHT_UPPER_LIMIT < 1) {
         try { TOTAL_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("totalWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOTAL_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (totalWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(totalWeight.toString(), TOTAL_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "totalWeight"), java.lang.String.valueOf(java.lang.Math.min(TOTAL_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "totalWeight", this.totalWeight, totalWeight));
   }

   /**
    * 금형 투자비 - 투자오더
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_ORDER = "moldOrder";
   static int MOLD_ORDER_UPPER_LIMIT = -1;
   java.lang.String moldOrder;
   /**
    * 금형 투자비 - 투자오더
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldOrder() {
      return moldOrder;
   }
   /**
    * 금형 투자비 - 투자오더
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldOrder(java.lang.String moldOrder) throws wt.util.WTPropertyVetoException {
      moldOrderValidate(moldOrder);
      this.moldOrder = moldOrder;
   }
   void moldOrderValidate(java.lang.String moldOrder) throws wt.util.WTPropertyVetoException {
      if (MOLD_ORDER_UPPER_LIMIT < 1) {
         try { MOLD_ORDER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldOrder").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_ORDER_UPPER_LIMIT = 200; }
      }
      if (moldOrder != null && !wt.fc.PersistenceHelper.checkStoredLength(moldOrder.toString(), MOLD_ORDER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldOrder"), java.lang.String.valueOf(java.lang.Math.min(MOLD_ORDER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldOrder", this.moldOrder, moldOrder));
   }

   /**
    * 금형 투자비 - Die No
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_DIE_NO = "moldDieNo";
   static int MOLD_DIE_NO_UPPER_LIMIT = -1;
   java.lang.String moldDieNo;
   /**
    * 금형 투자비 - Die No
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldDieNo() {
      return moldDieNo;
   }
   /**
    * 금형 투자비 - Die No
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldDieNo(java.lang.String moldDieNo) throws wt.util.WTPropertyVetoException {
      moldDieNoValidate(moldDieNo);
      this.moldDieNo = moldDieNo;
   }
   void moldDieNoValidate(java.lang.String moldDieNo) throws wt.util.WTPropertyVetoException {
      if (MOLD_DIE_NO_UPPER_LIMIT < 1) {
         try { MOLD_DIE_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldDieNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_DIE_NO_UPPER_LIMIT = 200; }
      }
      if (moldDieNo != null && !wt.fc.PersistenceHelper.checkStoredLength(moldDieNo.toString(), MOLD_DIE_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldDieNo"), java.lang.String.valueOf(java.lang.Math.min(MOLD_DIE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldDieNo", this.moldDieNo, moldDieNo));
   }

   /**
    * 금형 투자비 - 금형구조
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_STRUCTURE = "moldStructure";
   static int MOLD_STRUCTURE_UPPER_LIMIT = -1;
   java.lang.String moldStructure;
   /**
    * 금형 투자비 - 금형구조
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldStructure() {
      return moldStructure;
   }
   /**
    * 금형 투자비 - 금형구조
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldStructure(java.lang.String moldStructure) throws wt.util.WTPropertyVetoException {
      moldStructureValidate(moldStructure);
      this.moldStructure = moldStructure;
   }
   void moldStructureValidate(java.lang.String moldStructure) throws wt.util.WTPropertyVetoException {
      if (MOLD_STRUCTURE_UPPER_LIMIT < 1) {
         try { MOLD_STRUCTURE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldStructure").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_STRUCTURE_UPPER_LIMIT = 200; }
      }
      if (moldStructure != null && !wt.fc.PersistenceHelper.checkStoredLength(moldStructure.toString(), MOLD_STRUCTURE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldStructure"), java.lang.String.valueOf(java.lang.Math.min(MOLD_STRUCTURE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldStructure", this.moldStructure, moldStructure));
   }

   /**
    * 금형 투자비 - 제작구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MFT_DIVISION = "moldMftDivision";
   static int MOLD_MFT_DIVISION_UPPER_LIMIT = -1;
   java.lang.String moldMftDivision;
   /**
    * 금형 투자비 - 제작구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMftDivision() {
      return moldMftDivision;
   }
   /**
    * 금형 투자비 - 제작구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMftDivision(java.lang.String moldMftDivision) throws wt.util.WTPropertyVetoException {
      moldMftDivisionValidate(moldMftDivision);
      this.moldMftDivision = moldMftDivision;
   }
   void moldMftDivisionValidate(java.lang.String moldMftDivision) throws wt.util.WTPropertyVetoException {
      if (MOLD_MFT_DIVISION_UPPER_LIMIT < 1) {
         try { MOLD_MFT_DIVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMftDivision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MFT_DIVISION_UPPER_LIMIT = 200; }
      }
      if (moldMftDivision != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMftDivision.toString(), MOLD_MFT_DIVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMftDivision"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MFT_DIVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMftDivision", this.moldMftDivision, moldMftDivision));
   }

   /**
    * 금형 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_NFACTORY = "moldNFactory";
   static int MOLD_NFACTORY_UPPER_LIMIT = -1;
   java.lang.String moldNFactory;
   /**
    * 금형 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldNFactory() {
      return moldNFactory;
   }
   /**
    * 금형 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldNFactory(java.lang.String moldNFactory) throws wt.util.WTPropertyVetoException {
      moldNFactoryValidate(moldNFactory);
      this.moldNFactory = moldNFactory;
   }
   void moldNFactoryValidate(java.lang.String moldNFactory) throws wt.util.WTPropertyVetoException {
      if (MOLD_NFACTORY_UPPER_LIMIT < 1) {
         try { MOLD_NFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NFACTORY_UPPER_LIMIT = 200; }
      }
      if (moldNFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNFactory.toString(), MOLD_NFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNFactory"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNFactory", this.moldNFactory, moldNFactory));
   }

   /**
    * 금형 투자비 - 신규 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_NIC = "moldNIC";
   static int MOLD_NIC_UPPER_LIMIT = -1;
   java.lang.String moldNIC = "0";
   /**
    * 금형 투자비 - 신규 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldNIC() {
      return moldNIC;
   }
   /**
    * 금형 투자비 - 신규 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldNIC(java.lang.String moldNIC) throws wt.util.WTPropertyVetoException {
      moldNICValidate(moldNIC);
      this.moldNIC = moldNIC;
   }
   void moldNICValidate(java.lang.String moldNIC) throws wt.util.WTPropertyVetoException {
      if (MOLD_NIC_UPPER_LIMIT < 1) {
         try { MOLD_NIC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNIC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NIC_UPPER_LIMIT = 200; }
      }
      if (moldNIC != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNIC.toString(), MOLD_NIC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNIC"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NIC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNIC", this.moldNIC, moldNIC));
   }

   /**
    * 금형 투자비 - 신규 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_NICMUNIT = "moldNICMUnit";
   static int MOLD_NICMUNIT_UPPER_LIMIT = -1;
   java.lang.String moldNICMUnit;
   /**
    * 금형 투자비 - 신규 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldNICMUnit() {
      return moldNICMUnit;
   }
   /**
    * 금형 투자비 - 신규 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldNICMUnit(java.lang.String moldNICMUnit) throws wt.util.WTPropertyVetoException {
      moldNICMUnitValidate(moldNICMUnit);
      this.moldNICMUnit = moldNICMUnit;
   }
   void moldNICMUnitValidate(java.lang.String moldNICMUnit) throws wt.util.WTPropertyVetoException {
      if (MOLD_NICMUNIT_UPPER_LIMIT < 1) {
         try { MOLD_NICMUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNICMUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NICMUNIT_UPPER_LIMIT = 200; }
      }
      if (moldNICMUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNICMUnit.toString(), MOLD_NICMUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNICMUnit"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NICMUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNICMUnit", this.moldNICMUnit, moldNICMUnit));
   }

   /**
    * 금형 투자비 - 신규 - 지급액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_NPAY = "moldNPay";
   static int MOLD_NPAY_UPPER_LIMIT = -1;
   java.lang.String moldNPay = "0";
   /**
    * 금형 투자비 - 신규 - 지급액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldNPay() {
      return moldNPay;
   }
   /**
    * 금형 투자비 - 신규 - 지급액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldNPay(java.lang.String moldNPay) throws wt.util.WTPropertyVetoException {
      moldNPayValidate(moldNPay);
      this.moldNPay = moldNPay;
   }
   void moldNPayValidate(java.lang.String moldNPay) throws wt.util.WTPropertyVetoException {
      if (MOLD_NPAY_UPPER_LIMIT < 1) {
         try { MOLD_NPAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNPay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NPAY_UPPER_LIMIT = 200; }
      }
      if (moldNPay != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNPay.toString(), MOLD_NPAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNPay"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NPAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNPay", this.moldNPay, moldNPay));
   }

   /**
    * 금형 투자비 - 신규 - 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_NPAY_MUNIT = "moldNPayMUnit";
   static int MOLD_NPAY_MUNIT_UPPER_LIMIT = -1;
   java.lang.String moldNPayMUnit;
   /**
    * 금형 투자비 - 신규 - 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldNPayMUnit() {
      return moldNPayMUnit;
   }
   /**
    * 금형 투자비 - 신규 - 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldNPayMUnit(java.lang.String moldNPayMUnit) throws wt.util.WTPropertyVetoException {
      moldNPayMUnitValidate(moldNPayMUnit);
      this.moldNPayMUnit = moldNPayMUnit;
   }
   void moldNPayMUnitValidate(java.lang.String moldNPayMUnit) throws wt.util.WTPropertyVetoException {
      if (MOLD_NPAY_MUNIT_UPPER_LIMIT < 1) {
         try { MOLD_NPAY_MUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNPayMUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NPAY_MUNIT_UPPER_LIMIT = 200; }
      }
      if (moldNPayMUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNPayMUnit.toString(), MOLD_NPAY_MUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNPayMUnit"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NPAY_MUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNPayMUnit", this.moldNPayMUnit, moldNPayMUnit));
   }

   /**
    * 금형 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MPFACTORY = "moldMPFactory";
   static int MOLD_MPFACTORY_UPPER_LIMIT = -1;
   java.lang.String moldMPFactory;
   /**
    * 금형 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMPFactory() {
      return moldMPFactory;
   }
   /**
    * 금형 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMPFactory(java.lang.String moldMPFactory) throws wt.util.WTPropertyVetoException {
      moldMPFactoryValidate(moldMPFactory);
      this.moldMPFactory = moldMPFactory;
   }
   void moldMPFactoryValidate(java.lang.String moldMPFactory) throws wt.util.WTPropertyVetoException {
      if (MOLD_MPFACTORY_UPPER_LIMIT < 1) {
         try { MOLD_MPFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMPFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MPFACTORY_UPPER_LIMIT = 200; }
      }
      if (moldMPFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMPFactory.toString(), MOLD_MPFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMPFactory"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MPFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMPFactory", this.moldMPFactory, moldMPFactory));
   }

   /**
    * 금형 투자비 - 양산 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MPIC = "moldMPIC";
   static int MOLD_MPIC_UPPER_LIMIT = -1;
   java.lang.String moldMPIC = "0";
   /**
    * 금형 투자비 - 양산 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMPIC() {
      return moldMPIC;
   }
   /**
    * 금형 투자비 - 양산 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMPIC(java.lang.String moldMPIC) throws wt.util.WTPropertyVetoException {
      moldMPICValidate(moldMPIC);
      this.moldMPIC = moldMPIC;
   }
   void moldMPICValidate(java.lang.String moldMPIC) throws wt.util.WTPropertyVetoException {
      if (MOLD_MPIC_UPPER_LIMIT < 1) {
         try { MOLD_MPIC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMPIC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MPIC_UPPER_LIMIT = 200; }
      }
      if (moldMPIC != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMPIC.toString(), MOLD_MPIC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMPIC"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MPIC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMPIC", this.moldMPIC, moldMPIC));
   }

   /**
    * 금형 투자비 - 양산 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MPICMUNIT = "moldMPICMUnit";
   static int MOLD_MPICMUNIT_UPPER_LIMIT = -1;
   java.lang.String moldMPICMUnit;
   /**
    * 금형 투자비 - 양산 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMPICMUnit() {
      return moldMPICMUnit;
   }
   /**
    * 금형 투자비 - 양산 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMPICMUnit(java.lang.String moldMPICMUnit) throws wt.util.WTPropertyVetoException {
      moldMPICMUnitValidate(moldMPICMUnit);
      this.moldMPICMUnit = moldMPICMUnit;
   }
   void moldMPICMUnitValidate(java.lang.String moldMPICMUnit) throws wt.util.WTPropertyVetoException {
      if (MOLD_MPICMUNIT_UPPER_LIMIT < 1) {
         try { MOLD_MPICMUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMPICMUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MPICMUNIT_UPPER_LIMIT = 200; }
      }
      if (moldMPICMUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMPICMUnit.toString(), MOLD_MPICMUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMPICMUnit"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MPICMUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMPICMUnit", this.moldMPICMUnit, moldMPICMUnit));
   }

   /**
    * 금형 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MPQTOTAL = "moldMPQTotal";
   static int MOLD_MPQTOTAL_UPPER_LIMIT = -1;
   java.lang.String moldMPQTotal = "0";
   /**
    * 금형 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMPQTotal() {
      return moldMPQTotal;
   }
   /**
    * 금형 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMPQTotal(java.lang.String moldMPQTotal) throws wt.util.WTPropertyVetoException {
      moldMPQTotalValidate(moldMPQTotal);
      this.moldMPQTotal = moldMPQTotal;
   }
   void moldMPQTotalValidate(java.lang.String moldMPQTotal) throws wt.util.WTPropertyVetoException {
      if (MOLD_MPQTOTAL_UPPER_LIMIT < 1) {
         try { MOLD_MPQTOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMPQTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MPQTOTAL_UPPER_LIMIT = 200; }
      }
      if (moldMPQTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMPQTotal.toString(), MOLD_MPQTOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMPQTotal"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MPQTOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMPQTotal", this.moldMPQTotal, moldMPQTotal));
   }

   /**
    * 금형 투자비 - 양산수량 - MAX
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MPQMAX = "moldMPQMax";
   static int MOLD_MPQMAX_UPPER_LIMIT = -1;
   java.lang.String moldMPQMax = "0";
   /**
    * 금형 투자비 - 양산수량 - MAX
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMPQMax() {
      return moldMPQMax;
   }
   /**
    * 금형 투자비 - 양산수량 - MAX
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMPQMax(java.lang.String moldMPQMax) throws wt.util.WTPropertyVetoException {
      moldMPQMaxValidate(moldMPQMax);
      this.moldMPQMax = moldMPQMax;
   }
   void moldMPQMaxValidate(java.lang.String moldMPQMax) throws wt.util.WTPropertyVetoException {
      if (MOLD_MPQMAX_UPPER_LIMIT < 1) {
         try { MOLD_MPQMAX_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMPQMax").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MPQMAX_UPPER_LIMIT = 200; }
      }
      if (moldMPQMax != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMPQMax.toString(), MOLD_MPQMAX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMPQMax"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MPQMAX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMPQMax", this.moldMPQMax, moldMPQMax));
   }

   /**
    * 설비 투자비 - 투자오더
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_ORDER = "facOrder";
   static int FAC_ORDER_UPPER_LIMIT = -1;
   java.lang.String facOrder;
   /**
    * 설비 투자비 - 투자오더
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacOrder() {
      return facOrder;
   }
   /**
    * 설비 투자비 - 투자오더
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacOrder(java.lang.String facOrder) throws wt.util.WTPropertyVetoException {
      facOrderValidate(facOrder);
      this.facOrder = facOrder;
   }
   void facOrderValidate(java.lang.String facOrder) throws wt.util.WTPropertyVetoException {
      if (FAC_ORDER_UPPER_LIMIT < 1) {
         try { FAC_ORDER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facOrder").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_ORDER_UPPER_LIMIT = 200; }
      }
      if (facOrder != null && !wt.fc.PersistenceHelper.checkStoredLength(facOrder.toString(), FAC_ORDER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facOrder"), java.lang.String.valueOf(java.lang.Math.min(FAC_ORDER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facOrder", this.facOrder, facOrder));
   }

   /**
    * 설비 투자비 - 제작구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_MFT_DIVISION = "facMftDivision";
   static int FAC_MFT_DIVISION_UPPER_LIMIT = -1;
   java.lang.String facMftDivision;
   /**
    * 설비 투자비 - 제작구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacMftDivision() {
      return facMftDivision;
   }
   /**
    * 설비 투자비 - 제작구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacMftDivision(java.lang.String facMftDivision) throws wt.util.WTPropertyVetoException {
      facMftDivisionValidate(facMftDivision);
      this.facMftDivision = facMftDivision;
   }
   void facMftDivisionValidate(java.lang.String facMftDivision) throws wt.util.WTPropertyVetoException {
      if (FAC_MFT_DIVISION_UPPER_LIMIT < 1) {
         try { FAC_MFT_DIVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMftDivision").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MFT_DIVISION_UPPER_LIMIT = 200; }
      }
      if (facMftDivision != null && !wt.fc.PersistenceHelper.checkStoredLength(facMftDivision.toString(), FAC_MFT_DIVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMftDivision"), java.lang.String.valueOf(java.lang.Math.min(FAC_MFT_DIVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMftDivision", this.facMftDivision, facMftDivision));
   }

   /**
    * 설비 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_NFACTORY = "facNFactory";
   static int FAC_NFACTORY_UPPER_LIMIT = -1;
   java.lang.String facNFactory;
   /**
    * 설비 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacNFactory() {
      return facNFactory;
   }
   /**
    * 설비 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacNFactory(java.lang.String facNFactory) throws wt.util.WTPropertyVetoException {
      facNFactoryValidate(facNFactory);
      this.facNFactory = facNFactory;
   }
   void facNFactoryValidate(java.lang.String facNFactory) throws wt.util.WTPropertyVetoException {
      if (FAC_NFACTORY_UPPER_LIMIT < 1) {
         try { FAC_NFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facNFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_NFACTORY_UPPER_LIMIT = 200; }
      }
      if (facNFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(facNFactory.toString(), FAC_NFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facNFactory"), java.lang.String.valueOf(java.lang.Math.min(FAC_NFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facNFactory", this.facNFactory, facNFactory));
   }

   /**
    * 설비 투자비 - 신규 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_NIC = "facNIC";
   static int FAC_NIC_UPPER_LIMIT = -1;
   java.lang.String facNIC = "0";
   /**
    * 설비 투자비 - 신규 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacNIC() {
      return facNIC;
   }
   /**
    * 설비 투자비 - 신규 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacNIC(java.lang.String facNIC) throws wt.util.WTPropertyVetoException {
      facNICValidate(facNIC);
      this.facNIC = facNIC;
   }
   void facNICValidate(java.lang.String facNIC) throws wt.util.WTPropertyVetoException {
      if (FAC_NIC_UPPER_LIMIT < 1) {
         try { FAC_NIC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facNIC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_NIC_UPPER_LIMIT = 200; }
      }
      if (facNIC != null && !wt.fc.PersistenceHelper.checkStoredLength(facNIC.toString(), FAC_NIC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facNIC"), java.lang.String.valueOf(java.lang.Math.min(FAC_NIC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facNIC", this.facNIC, facNIC));
   }

   /**
    * 설비 투자비 - 신규 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_NICMUNIT = "facNICMUnit";
   static int FAC_NICMUNIT_UPPER_LIMIT = -1;
   java.lang.String facNICMUnit;
   /**
    * 설비 투자비 - 신규 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacNICMUnit() {
      return facNICMUnit;
   }
   /**
    * 설비 투자비 - 신규 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacNICMUnit(java.lang.String facNICMUnit) throws wt.util.WTPropertyVetoException {
      facNICMUnitValidate(facNICMUnit);
      this.facNICMUnit = facNICMUnit;
   }
   void facNICMUnitValidate(java.lang.String facNICMUnit) throws wt.util.WTPropertyVetoException {
      if (FAC_NICMUNIT_UPPER_LIMIT < 1) {
         try { FAC_NICMUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facNICMUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_NICMUNIT_UPPER_LIMIT = 200; }
      }
      if (facNICMUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(facNICMUnit.toString(), FAC_NICMUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facNICMUnit"), java.lang.String.valueOf(java.lang.Math.min(FAC_NICMUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facNICMUnit", this.facNICMUnit, facNICMUnit));
   }

   /**
    * 설비 투자비 - 신규 - 지급액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_NPAY = "facNPay";
   static int FAC_NPAY_UPPER_LIMIT = -1;
   java.lang.String facNPay = "0";
   /**
    * 설비 투자비 - 신규 - 지급액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacNPay() {
      return facNPay;
   }
   /**
    * 설비 투자비 - 신규 - 지급액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacNPay(java.lang.String facNPay) throws wt.util.WTPropertyVetoException {
      facNPayValidate(facNPay);
      this.facNPay = facNPay;
   }
   void facNPayValidate(java.lang.String facNPay) throws wt.util.WTPropertyVetoException {
      if (FAC_NPAY_UPPER_LIMIT < 1) {
         try { FAC_NPAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facNPay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_NPAY_UPPER_LIMIT = 200; }
      }
      if (facNPay != null && !wt.fc.PersistenceHelper.checkStoredLength(facNPay.toString(), FAC_NPAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facNPay"), java.lang.String.valueOf(java.lang.Math.min(FAC_NPAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facNPay", this.facNPay, facNPay));
   }

   /**
    * 설비 투자비 - 신규 - 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_NPAY_MUNIT = "facNPayMUnit";
   static int FAC_NPAY_MUNIT_UPPER_LIMIT = -1;
   java.lang.String facNPayMUnit;
   /**
    * 설비 투자비 - 신규 - 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacNPayMUnit() {
      return facNPayMUnit;
   }
   /**
    * 설비 투자비 - 신규 - 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacNPayMUnit(java.lang.String facNPayMUnit) throws wt.util.WTPropertyVetoException {
      facNPayMUnitValidate(facNPayMUnit);
      this.facNPayMUnit = facNPayMUnit;
   }
   void facNPayMUnitValidate(java.lang.String facNPayMUnit) throws wt.util.WTPropertyVetoException {
      if (FAC_NPAY_MUNIT_UPPER_LIMIT < 1) {
         try { FAC_NPAY_MUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facNPayMUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_NPAY_MUNIT_UPPER_LIMIT = 200; }
      }
      if (facNPayMUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(facNPayMUnit.toString(), FAC_NPAY_MUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facNPayMUnit"), java.lang.String.valueOf(java.lang.Math.min(FAC_NPAY_MUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facNPayMUnit", this.facNPayMUnit, facNPayMUnit));
   }

   /**
    * 설비 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_MPFACTORY = "facMPFactory";
   static int FAC_MPFACTORY_UPPER_LIMIT = -1;
   java.lang.String facMPFactory;
   /**
    * 설비 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacMPFactory() {
      return facMPFactory;
   }
   /**
    * 설비 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacMPFactory(java.lang.String facMPFactory) throws wt.util.WTPropertyVetoException {
      facMPFactoryValidate(facMPFactory);
      this.facMPFactory = facMPFactory;
   }
   void facMPFactoryValidate(java.lang.String facMPFactory) throws wt.util.WTPropertyVetoException {
      if (FAC_MPFACTORY_UPPER_LIMIT < 1) {
         try { FAC_MPFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMPFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MPFACTORY_UPPER_LIMIT = 200; }
      }
      if (facMPFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(facMPFactory.toString(), FAC_MPFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMPFactory"), java.lang.String.valueOf(java.lang.Math.min(FAC_MPFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMPFactory", this.facMPFactory, facMPFactory));
   }

   /**
    * 설비 투자비 - 양산 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_MPIC = "facMPIC";
   static int FAC_MPIC_UPPER_LIMIT = -1;
   java.lang.String facMPIC = "0";
   /**
    * 설비 투자비 - 양산 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacMPIC() {
      return facMPIC;
   }
   /**
    * 설비 투자비 - 양산 - 투자비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacMPIC(java.lang.String facMPIC) throws wt.util.WTPropertyVetoException {
      facMPICValidate(facMPIC);
      this.facMPIC = facMPIC;
   }
   void facMPICValidate(java.lang.String facMPIC) throws wt.util.WTPropertyVetoException {
      if (FAC_MPIC_UPPER_LIMIT < 1) {
         try { FAC_MPIC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMPIC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MPIC_UPPER_LIMIT = 200; }
      }
      if (facMPIC != null && !wt.fc.PersistenceHelper.checkStoredLength(facMPIC.toString(), FAC_MPIC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMPIC"), java.lang.String.valueOf(java.lang.Math.min(FAC_MPIC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMPIC", this.facMPIC, facMPIC));
   }

   /**
    * 설비 투자비 - 양산 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_MPICMUNIT = "facMPICMUnit";
   static int FAC_MPICMUNIT_UPPER_LIMIT = -1;
   java.lang.String facMPICMUnit;
   /**
    * 설비 투자비 - 양산 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacMPICMUnit() {
      return facMPICMUnit;
   }
   /**
    * 설비 투자비 - 양산 - 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacMPICMUnit(java.lang.String facMPICMUnit) throws wt.util.WTPropertyVetoException {
      facMPICMUnitValidate(facMPICMUnit);
      this.facMPICMUnit = facMPICMUnit;
   }
   void facMPICMUnitValidate(java.lang.String facMPICMUnit) throws wt.util.WTPropertyVetoException {
      if (FAC_MPICMUNIT_UPPER_LIMIT < 1) {
         try { FAC_MPICMUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMPICMUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MPICMUNIT_UPPER_LIMIT = 200; }
      }
      if (facMPICMUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(facMPICMUnit.toString(), FAC_MPICMUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMPICMUnit"), java.lang.String.valueOf(java.lang.Math.min(FAC_MPICMUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMPICMUnit", this.facMPICMUnit, facMPICMUnit));
   }

   /**
    * 설비 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_MPQTOTAL = "facMPQTotal";
   static int FAC_MPQTOTAL_UPPER_LIMIT = -1;
   java.lang.String facMPQTotal = "0";
   /**
    * 설비 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacMPQTotal() {
      return facMPQTotal;
   }
   /**
    * 설비 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacMPQTotal(java.lang.String facMPQTotal) throws wt.util.WTPropertyVetoException {
      facMPQTotalValidate(facMPQTotal);
      this.facMPQTotal = facMPQTotal;
   }
   void facMPQTotalValidate(java.lang.String facMPQTotal) throws wt.util.WTPropertyVetoException {
      if (FAC_MPQTOTAL_UPPER_LIMIT < 1) {
         try { FAC_MPQTOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMPQTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MPQTOTAL_UPPER_LIMIT = 200; }
      }
      if (facMPQTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(facMPQTotal.toString(), FAC_MPQTOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMPQTotal"), java.lang.String.valueOf(java.lang.Math.min(FAC_MPQTOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMPQTotal", this.facMPQTotal, facMPQTotal));
   }

   /**
    * 설비 투자비 - 양산수량 - Max
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_MPQMAX = "facMPQMax";
   static int FAC_MPQMAX_UPPER_LIMIT = -1;
   java.lang.String facMPQMax = "0";
   /**
    * 설비 투자비 - 양산수량 - Max
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacMPQMax() {
      return facMPQMax;
   }
   /**
    * 설비 투자비 - 양산수량 - Max
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacMPQMax(java.lang.String facMPQMax) throws wt.util.WTPropertyVetoException {
      facMPQMaxValidate(facMPQMax);
      this.facMPQMax = facMPQMax;
   }
   void facMPQMaxValidate(java.lang.String facMPQMax) throws wt.util.WTPropertyVetoException {
      if (FAC_MPQMAX_UPPER_LIMIT < 1) {
         try { FAC_MPQMAX_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMPQMax").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MPQMAX_UPPER_LIMIT = 200; }
      }
      if (facMPQMax != null && !wt.fc.PersistenceHelper.checkStoredLength(facMPQMax.toString(), FAC_MPQMAX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMPQMax"), java.lang.String.valueOf(java.lang.Math.min(FAC_MPQMAX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMPQMax", this.facMPQMax, facMPQMax));
   }

   /**
    * 기타 투자비 - 신규 - 투자비(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_NIC = "etcNIC";
   static int ETC_NIC_UPPER_LIMIT = -1;
   java.lang.String etcNIC = "0";
   /**
    * 기타 투자비 - 신규 - 투자비(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcNIC() {
      return etcNIC;
   }
   /**
    * 기타 투자비 - 신규 - 투자비(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcNIC(java.lang.String etcNIC) throws wt.util.WTPropertyVetoException {
      etcNICValidate(etcNIC);
      this.etcNIC = etcNIC;
   }
   void etcNICValidate(java.lang.String etcNIC) throws wt.util.WTPropertyVetoException {
      if (ETC_NIC_UPPER_LIMIT < 1) {
         try { ETC_NIC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNIC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NIC_UPPER_LIMIT = 200; }
      }
      if (etcNIC != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNIC.toString(), ETC_NIC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNIC"), java.lang.String.valueOf(java.lang.Math.min(ETC_NIC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNIC", this.etcNIC, etcNIC));
   }

   /**
    * 기타 투자비 - 신규 - 지급액(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_NPAY = "etcNPay";
   static int ETC_NPAY_UPPER_LIMIT = -1;
   java.lang.String etcNPay = "0";
   /**
    * 기타 투자비 - 신규 - 지급액(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcNPay() {
      return etcNPay;
   }
   /**
    * 기타 투자비 - 신규 - 지급액(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcNPay(java.lang.String etcNPay) throws wt.util.WTPropertyVetoException {
      etcNPayValidate(etcNPay);
      this.etcNPay = etcNPay;
   }
   void etcNPayValidate(java.lang.String etcNPay) throws wt.util.WTPropertyVetoException {
      if (ETC_NPAY_UPPER_LIMIT < 1) {
         try { ETC_NPAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNPay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NPAY_UPPER_LIMIT = 200; }
      }
      if (etcNPay != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNPay.toString(), ETC_NPAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNPay"), java.lang.String.valueOf(java.lang.Math.min(ETC_NPAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNPay", this.etcNPay, etcNPay));
   }

   /**
    * 기타 투자비 - 양산 - 투자비(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_MPIC = "etcMPIC";
   static int ETC_MPIC_UPPER_LIMIT = -1;
   java.lang.String etcMPIC = "0";
   /**
    * 기타 투자비 - 양산 - 투자비(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcMPIC() {
      return etcMPIC;
   }
   /**
    * 기타 투자비 - 양산 - 투자비(합계)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcMPIC(java.lang.String etcMPIC) throws wt.util.WTPropertyVetoException {
      etcMPICValidate(etcMPIC);
      this.etcMPIC = etcMPIC;
   }
   void etcMPICValidate(java.lang.String etcMPIC) throws wt.util.WTPropertyVetoException {
      if (ETC_MPIC_UPPER_LIMIT < 1) {
         try { ETC_MPIC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcMPIC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_MPIC_UPPER_LIMIT = 200; }
      }
      if (etcMPIC != null && !wt.fc.PersistenceHelper.checkStoredLength(etcMPIC.toString(), ETC_MPIC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcMPIC"), java.lang.String.valueOf(java.lang.Math.min(ETC_MPIC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcMPIC", this.etcMPIC, etcMPIC));
   }

   /**
    * 기타 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_MPQTOTAL = "etcMPQTotal";
   static int ETC_MPQTOTAL_UPPER_LIMIT = -1;
   java.lang.String etcMPQTotal = "0";
   /**
    * 기타 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcMPQTotal() {
      return etcMPQTotal;
   }
   /**
    * 기타 투자비 - 양산수량 - Total
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcMPQTotal(java.lang.String etcMPQTotal) throws wt.util.WTPropertyVetoException {
      etcMPQTotalValidate(etcMPQTotal);
      this.etcMPQTotal = etcMPQTotal;
   }
   void etcMPQTotalValidate(java.lang.String etcMPQTotal) throws wt.util.WTPropertyVetoException {
      if (ETC_MPQTOTAL_UPPER_LIMIT < 1) {
         try { ETC_MPQTOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcMPQTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_MPQTOTAL_UPPER_LIMIT = 200; }
      }
      if (etcMPQTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(etcMPQTotal.toString(), ETC_MPQTOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcMPQTotal"), java.lang.String.valueOf(java.lang.Math.min(ETC_MPQTOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcMPQTotal", this.etcMPQTotal, etcMPQTotal));
   }

   /**
    * 기타 투자비 - 양산수량 - Max
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_MPQMAX = "etcMPQMax";
   static int ETC_MPQMAX_UPPER_LIMIT = -1;
   java.lang.String etcMPQMax = "0";
   /**
    * 기타 투자비 - 양산수량 - Max
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcMPQMax() {
      return etcMPQMax;
   }
   /**
    * 기타 투자비 - 양산수량 - Max
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcMPQMax(java.lang.String etcMPQMax) throws wt.util.WTPropertyVetoException {
      etcMPQMaxValidate(etcMPQMax);
      this.etcMPQMax = etcMPQMax;
   }
   void etcMPQMaxValidate(java.lang.String etcMPQMax) throws wt.util.WTPropertyVetoException {
      if (ETC_MPQMAX_UPPER_LIMIT < 1) {
         try { ETC_MPQMAX_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcMPQMax").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_MPQMAX_UPPER_LIMIT = 200; }
      }
      if (etcMPQMax != null && !wt.fc.PersistenceHelper.checkStoredLength(etcMPQMax.toString(), ETC_MPQMAX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcMPQMax"), java.lang.String.valueOf(java.lang.Math.min(ETC_MPQMAX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcMPQMax", this.etcMPQMax, etcMPQMax));
   }

   /**
    * 기타 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_MPFACTORY = "etcMPFactory";
   static int ETC_MPFACTORY_UPPER_LIMIT = -1;
   java.lang.String etcMPFactory;
   /**
    * 기타 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcMPFactory() {
      return etcMPFactory;
   }
   /**
    * 기타 투자비 - 양산 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcMPFactory(java.lang.String etcMPFactory) throws wt.util.WTPropertyVetoException {
      etcMPFactoryValidate(etcMPFactory);
      this.etcMPFactory = etcMPFactory;
   }
   void etcMPFactoryValidate(java.lang.String etcMPFactory) throws wt.util.WTPropertyVetoException {
      if (ETC_MPFACTORY_UPPER_LIMIT < 1) {
         try { ETC_MPFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcMPFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_MPFACTORY_UPPER_LIMIT = 200; }
      }
      if (etcMPFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(etcMPFactory.toString(), ETC_MPFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcMPFactory"), java.lang.String.valueOf(java.lang.Math.min(ETC_MPFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcMPFactory", this.etcMPFactory, etcMPFactory));
   }

   /**
    * 기타 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_NPFACTORY = "etcNPFactory";
   static int ETC_NPFACTORY_UPPER_LIMIT = -1;
   java.lang.String etcNPFactory;
   /**
    * 기타 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcNPFactory() {
      return etcNPFactory;
   }
   /**
    * 기타 투자비 - 신규 - 제작처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcNPFactory(java.lang.String etcNPFactory) throws wt.util.WTPropertyVetoException {
      etcNPFactoryValidate(etcNPFactory);
      this.etcNPFactory = etcNPFactory;
   }
   void etcNPFactoryValidate(java.lang.String etcNPFactory) throws wt.util.WTPropertyVetoException {
      if (ETC_NPFACTORY_UPPER_LIMIT < 1) {
         try { ETC_NPFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNPFactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NPFACTORY_UPPER_LIMIT = 200; }
      }
      if (etcNPFactory != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNPFactory.toString(), ETC_NPFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNPFactory"), java.lang.String.valueOf(java.lang.Math.min(ETC_NPFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNPFactory", this.etcNPFactory, etcNPFactory));
   }

   /**
    * 항목
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ITEM_NAME = "itemName";
   static int ITEM_NAME_UPPER_LIMIT = -1;
   java.lang.String itemName;
   /**
    * 항목
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getItemName() {
      return itemName;
   }
   /**
    * 항목
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setItemName(java.lang.String itemName) throws wt.util.WTPropertyVetoException {
      itemNameValidate(itemName);
      this.itemName = itemName;
   }
   void itemNameValidate(java.lang.String itemName) throws wt.util.WTPropertyVetoException {
      if (ITEM_NAME_UPPER_LIMIT < 1) {
         try { ITEM_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("itemName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ITEM_NAME_UPPER_LIMIT = 200; }
      }
      if (itemName != null && !wt.fc.PersistenceHelper.checkStoredLength(itemName.toString(), ITEM_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "itemName"), java.lang.String.valueOf(java.lang.Math.min(ITEM_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "itemName", this.itemName, itemName));
   }

   /**
    * 원재료정보 - 비철원재료 - 원재료명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String R_MAT_NMETAL_NAME = "rMatNMetalName";
   static int R_MAT_NMETAL_NAME_UPPER_LIMIT = -1;
   java.lang.String rMatNMetalName;
   /**
    * 원재료정보 - 비철원재료 - 원재료명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRMatNMetalName() {
      return rMatNMetalName;
   }
   /**
    * 원재료정보 - 비철원재료 - 원재료명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRMatNMetalName(java.lang.String rMatNMetalName) throws wt.util.WTPropertyVetoException {
      rMatNMetalNameValidate(rMatNMetalName);
      this.rMatNMetalName = rMatNMetalName;
   }
   void rMatNMetalNameValidate(java.lang.String rMatNMetalName) throws wt.util.WTPropertyVetoException {
      if (R_MAT_NMETAL_NAME_UPPER_LIMIT < 1) {
         try { R_MAT_NMETAL_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rMatNMetalName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_MAT_NMETAL_NAME_UPPER_LIMIT = 200; }
      }
      if (rMatNMetalName != null && !wt.fc.PersistenceHelper.checkStoredLength(rMatNMetalName.toString(), R_MAT_NMETAL_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rMatNMetalName"), java.lang.String.valueOf(java.lang.Math.min(R_MAT_NMETAL_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rMatNMetalName", this.rMatNMetalName, rMatNMetalName));
   }

   /**
    * 원재료정보 - 비철원재료 - 선도금사양
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRE_PLATING_SPEC = "prePlatingSpec";
   static int PRE_PLATING_SPEC_UPPER_LIMIT = -1;
   java.lang.String prePlatingSpec;
   /**
    * 원재료정보 - 비철원재료 - 선도금사양
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPrePlatingSpec() {
      return prePlatingSpec;
   }
   /**
    * 원재료정보 - 비철원재료 - 선도금사양
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPrePlatingSpec(java.lang.String prePlatingSpec) throws wt.util.WTPropertyVetoException {
      prePlatingSpecValidate(prePlatingSpec);
      this.prePlatingSpec = prePlatingSpec;
   }
   void prePlatingSpecValidate(java.lang.String prePlatingSpec) throws wt.util.WTPropertyVetoException {
      if (PRE_PLATING_SPEC_UPPER_LIMIT < 1) {
         try { PRE_PLATING_SPEC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("prePlatingSpec").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRE_PLATING_SPEC_UPPER_LIMIT = 200; }
      }
      if (prePlatingSpec != null && !wt.fc.PersistenceHelper.checkStoredLength(prePlatingSpec.toString(), PRE_PLATING_SPEC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prePlatingSpec"), java.lang.String.valueOf(java.lang.Math.min(PRE_PLATING_SPEC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "prePlatingSpec", this.prePlatingSpec, prePlatingSpec));
   }

   /**
    * 구매/도금/외주 - 선도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRE_PLATING_COST = "prePlatingCost";
   static int PRE_PLATING_COST_UPPER_LIMIT = -1;
   java.lang.String prePlatingCost = "0";
   /**
    * 구매/도금/외주 - 선도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPrePlatingCost() {
      return prePlatingCost;
   }
   /**
    * 구매/도금/외주 - 선도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPrePlatingCost(java.lang.String prePlatingCost) throws wt.util.WTPropertyVetoException {
      prePlatingCostValidate(prePlatingCost);
      this.prePlatingCost = prePlatingCost;
   }
   void prePlatingCostValidate(java.lang.String prePlatingCost) throws wt.util.WTPropertyVetoException {
      if (PRE_PLATING_COST_UPPER_LIMIT < 1) {
         try { PRE_PLATING_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("prePlatingCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRE_PLATING_COST_UPPER_LIMIT = 200; }
      }
      if (prePlatingCost != null && !wt.fc.PersistenceHelper.checkStoredLength(prePlatingCost.toString(), PRE_PLATING_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prePlatingCost"), java.lang.String.valueOf(java.lang.Math.min(PRE_PLATING_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "prePlatingCost", this.prePlatingCost, prePlatingCost));
   }

   /**
    * 구매/도금/외주 - 선도금단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRE_PLATING_UNIT = "prePlatingUnit";
   static int PRE_PLATING_UNIT_UPPER_LIMIT = -1;
   java.lang.String prePlatingUnit;
   /**
    * 구매/도금/외주 - 선도금단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPrePlatingUnit() {
      return prePlatingUnit;
   }
   /**
    * 구매/도금/외주 - 선도금단가 화폐단위
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPrePlatingUnit(java.lang.String prePlatingUnit) throws wt.util.WTPropertyVetoException {
      prePlatingUnitValidate(prePlatingUnit);
      this.prePlatingUnit = prePlatingUnit;
   }
   void prePlatingUnitValidate(java.lang.String prePlatingUnit) throws wt.util.WTPropertyVetoException {
      if (PRE_PLATING_UNIT_UPPER_LIMIT < 1) {
         try { PRE_PLATING_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("prePlatingUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRE_PLATING_UNIT_UPPER_LIMIT = 200; }
      }
      if (prePlatingUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(prePlatingUnit.toString(), PRE_PLATING_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prePlatingUnit"), java.lang.String.valueOf(java.lang.Math.min(PRE_PLATING_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "prePlatingUnit", this.prePlatingUnit, prePlatingUnit));
   }

   /**
    * 원재료정보 - 비철원재료 - 두께
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String R_MAT_NMETAL_T = "rMatNMetalT";
   static int R_MAT_NMETAL_T_UPPER_LIMIT = -1;
   java.lang.String rMatNMetalT = "0";
   /**
    * 원재료정보 - 비철원재료 - 두께
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRMatNMetalT() {
      return rMatNMetalT;
   }
   /**
    * 원재료정보 - 비철원재료 - 두께
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRMatNMetalT(java.lang.String rMatNMetalT) throws wt.util.WTPropertyVetoException {
      rMatNMetalTValidate(rMatNMetalT);
      this.rMatNMetalT = rMatNMetalT;
   }
   void rMatNMetalTValidate(java.lang.String rMatNMetalT) throws wt.util.WTPropertyVetoException {
      if (R_MAT_NMETAL_T_UPPER_LIMIT < 1) {
         try { R_MAT_NMETAL_T_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rMatNMetalT").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_MAT_NMETAL_T_UPPER_LIMIT = 200; }
      }
      if (rMatNMetalT != null && !wt.fc.PersistenceHelper.checkStoredLength(rMatNMetalT.toString(), R_MAT_NMETAL_T_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rMatNMetalT"), java.lang.String.valueOf(java.lang.Math.min(R_MAT_NMETAL_T_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rMatNMetalT", this.rMatNMetalT, rMatNMetalT));
   }

   /**
    * 원재료정보 - 비철원재료 - 폭
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String R_MAT_NMETAL_W = "rMatNMetalW";
   static int R_MAT_NMETAL_W_UPPER_LIMIT = -1;
   java.lang.String rMatNMetalW = "0";
   /**
    * 원재료정보 - 비철원재료 - 폭
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRMatNMetalW() {
      return rMatNMetalW;
   }
   /**
    * 원재료정보 - 비철원재료 - 폭
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRMatNMetalW(java.lang.String rMatNMetalW) throws wt.util.WTPropertyVetoException {
      rMatNMetalWValidate(rMatNMetalW);
      this.rMatNMetalW = rMatNMetalW;
   }
   void rMatNMetalWValidate(java.lang.String rMatNMetalW) throws wt.util.WTPropertyVetoException {
      if (R_MAT_NMETAL_W_UPPER_LIMIT < 1) {
         try { R_MAT_NMETAL_W_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rMatNMetalW").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_MAT_NMETAL_W_UPPER_LIMIT = 200; }
      }
      if (rMatNMetalW != null && !wt.fc.PersistenceHelper.checkStoredLength(rMatNMetalW.toString(), R_MAT_NMETAL_W_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rMatNMetalW"), java.lang.String.valueOf(java.lang.Math.min(R_MAT_NMETAL_W_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rMatNMetalW", this.rMatNMetalW, rMatNMetalW));
   }

   /**
    * 원재료정보 - 비철원재료 - 피치
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String R_MAT_NMETAL_P = "rMatNMetalP";
   static int R_MAT_NMETAL_P_UPPER_LIMIT = -1;
   java.lang.String rMatNMetalP = "0";
   /**
    * 원재료정보 - 비철원재료 - 피치
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRMatNMetalP() {
      return rMatNMetalP;
   }
   /**
    * 원재료정보 - 비철원재료 - 피치
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRMatNMetalP(java.lang.String rMatNMetalP) throws wt.util.WTPropertyVetoException {
      rMatNMetalPValidate(rMatNMetalP);
      this.rMatNMetalP = rMatNMetalP;
   }
   void rMatNMetalPValidate(java.lang.String rMatNMetalP) throws wt.util.WTPropertyVetoException {
      if (R_MAT_NMETAL_P_UPPER_LIMIT < 1) {
         try { R_MAT_NMETAL_P_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rMatNMetalP").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_MAT_NMETAL_P_UPPER_LIMIT = 200; }
      }
      if (rMatNMetalP != null && !wt.fc.PersistenceHelper.checkStoredLength(rMatNMetalP.toString(), R_MAT_NMETAL_P_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rMatNMetalP"), java.lang.String.valueOf(java.lang.Math.min(R_MAT_NMETAL_P_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rMatNMetalP", this.rMatNMetalP, rMatNMetalP));
   }

   /**
    * 입고처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String COST_DELIVER = "costDeliver";
   static int COST_DELIVER_UPPER_LIMIT = -1;
   java.lang.String costDeliver;
   /**
    * 입고처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCostDeliver() {
      return costDeliver;
   }
   /**
    * 입고처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCostDeliver(java.lang.String costDeliver) throws wt.util.WTPropertyVetoException {
      costDeliverValidate(costDeliver);
      this.costDeliver = costDeliver;
   }
   void costDeliverValidate(java.lang.String costDeliver) throws wt.util.WTPropertyVetoException {
      if (COST_DELIVER_UPPER_LIMIT < 1) {
         try { COST_DELIVER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("costDeliver").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COST_DELIVER_UPPER_LIMIT = 200; }
      }
      if (costDeliver != null && !wt.fc.PersistenceHelper.checkStoredLength(costDeliver.toString(), COST_DELIVER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "costDeliver"), java.lang.String.valueOf(java.lang.Math.min(COST_DELIVER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "costDeliver", this.costDeliver, costDeliver));
   }

   /**
    * 금형감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_REDUCE_PRICE = "moldReducePrice";
   static int MOLD_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String moldReducePrice = "0";
   /**
    * 금형감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldReducePrice() {
      return moldReducePrice;
   }
   /**
    * 금형감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldReducePrice(java.lang.String moldReducePrice) throws wt.util.WTPropertyVetoException {
      moldReducePriceValidate(moldReducePrice);
      this.moldReducePrice = moldReducePrice;
   }
   void moldReducePriceValidate(java.lang.String moldReducePrice) throws wt.util.WTPropertyVetoException {
      if (MOLD_REDUCE_PRICE_UPPER_LIMIT < 1) {
         try { MOLD_REDUCE_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldReducePrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_REDUCE_PRICE_UPPER_LIMIT = 200; }
      }
      if (moldReducePrice != null && !wt.fc.PersistenceHelper.checkStoredLength(moldReducePrice.toString(), MOLD_REDUCE_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldReducePrice"), java.lang.String.valueOf(java.lang.Math.min(MOLD_REDUCE_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldReducePrice", this.moldReducePrice, moldReducePrice));
   }

   /**
    * 설비감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_REDUCE_PRICE = "facReducePrice";
   static int FAC_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String facReducePrice = "0";
   /**
    * 설비감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacReducePrice() {
      return facReducePrice;
   }
   /**
    * 설비감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacReducePrice(java.lang.String facReducePrice) throws wt.util.WTPropertyVetoException {
      facReducePriceValidate(facReducePrice);
      this.facReducePrice = facReducePrice;
   }
   void facReducePriceValidate(java.lang.String facReducePrice) throws wt.util.WTPropertyVetoException {
      if (FAC_REDUCE_PRICE_UPPER_LIMIT < 1) {
         try { FAC_REDUCE_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facReducePrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_REDUCE_PRICE_UPPER_LIMIT = 200; }
      }
      if (facReducePrice != null && !wt.fc.PersistenceHelper.checkStoredLength(facReducePrice.toString(), FAC_REDUCE_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facReducePrice"), java.lang.String.valueOf(java.lang.Math.min(FAC_REDUCE_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facReducePrice", this.facReducePrice, facReducePrice));
   }

   /**
    * 기타감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETC_REDUCE_PRICE = "etcReducePrice";
   static int ETC_REDUCE_PRICE_UPPER_LIMIT = -1;
   java.lang.String etcReducePrice = "0";
   /**
    * 기타감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcReducePrice() {
      return etcReducePrice;
   }
   /**
    * 기타감가비합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcReducePrice(java.lang.String etcReducePrice) throws wt.util.WTPropertyVetoException {
      etcReducePriceValidate(etcReducePrice);
      this.etcReducePrice = etcReducePrice;
   }
   void etcReducePriceValidate(java.lang.String etcReducePrice) throws wt.util.WTPropertyVetoException {
      if (ETC_REDUCE_PRICE_UPPER_LIMIT < 1) {
         try { ETC_REDUCE_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcReducePrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_REDUCE_PRICE_UPPER_LIMIT = 200; }
      }
      if (etcReducePrice != null && !wt.fc.PersistenceHelper.checkStoredLength(etcReducePrice.toString(), ETC_REDUCE_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcReducePrice"), java.lang.String.valueOf(java.lang.Math.min(ETC_REDUCE_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcReducePrice", this.etcReducePrice, etcReducePrice));
   }

   /**
    * 조업도(시간)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String WORK_HOUR = "workHour";
   static int WORK_HOUR_UPPER_LIMIT = -1;
   java.lang.String workHour;
   /**
    * 조업도(시간)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getWorkHour() {
      return workHour;
   }
   /**
    * 조업도(시간)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setWorkHour(java.lang.String workHour) throws wt.util.WTPropertyVetoException {
      workHourValidate(workHour);
      this.workHour = workHour;
   }
   void workHourValidate(java.lang.String workHour) throws wt.util.WTPropertyVetoException {
      if (WORK_HOUR_UPPER_LIMIT < 1) {
         try { WORK_HOUR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workHour").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_HOUR_UPPER_LIMIT = 200; }
      }
      if (workHour != null && !wt.fc.PersistenceHelper.checkStoredLength(workHour.toString(), WORK_HOUR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workHour"), java.lang.String.valueOf(java.lang.Math.min(WORK_HOUR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workHour", this.workHour, workHour));
   }

   /**
    * 조업도(일)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String WORK_DAY = "workDay";
   static int WORK_DAY_UPPER_LIMIT = -1;
   java.lang.String workDay;
   /**
    * 조업도(일)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getWorkDay() {
      return workDay;
   }
   /**
    * 조업도(일)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setWorkDay(java.lang.String workDay) throws wt.util.WTPropertyVetoException {
      workDayValidate(workDay);
      this.workDay = workDay;
   }
   void workDayValidate(java.lang.String workDay) throws wt.util.WTPropertyVetoException {
      if (WORK_DAY_UPPER_LIMIT < 1) {
         try { WORK_DAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workDay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_DAY_UPPER_LIMIT = 200; }
      }
      if (workDay != null && !wt.fc.PersistenceHelper.checkStoredLength(workDay.toString(), WORK_DAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workDay"), java.lang.String.valueOf(java.lang.Math.min(WORK_DAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workDay", this.workDay, workDay));
   }

   /**
    * 년조업도(월)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String WORK_YEAR = "workYear";
   static int WORK_YEAR_UPPER_LIMIT = -1;
   java.lang.String workYear;
   /**
    * 년조업도(월)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getWorkYear() {
      return workYear;
   }
   /**
    * 년조업도(월)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setWorkYear(java.lang.String workYear) throws wt.util.WTPropertyVetoException {
      workYearValidate(workYear);
      this.workYear = workYear;
   }
   void workYearValidate(java.lang.String workYear) throws wt.util.WTPropertyVetoException {
      if (WORK_YEAR_UPPER_LIMIT < 1) {
         try { WORK_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("workYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WORK_YEAR_UPPER_LIMIT = 200; }
      }
      if (workYear != null && !wt.fc.PersistenceHelper.checkStoredLength(workYear.toString(), WORK_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "workYear"), java.lang.String.valueOf(java.lang.Math.min(WORK_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "workYear", this.workYear, workYear));
   }

   /**
    * 년 생산량(EA)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String EA_OUTPUT = "eaOutput";
   static int EA_OUTPUT_UPPER_LIMIT = -1;
   java.lang.String eaOutput = "0";
   /**
    * 년 생산량(EA)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEaOutput() {
      return eaOutput;
   }
   /**
    * 년 생산량(EA)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEaOutput(java.lang.String eaOutput) throws wt.util.WTPropertyVetoException {
      eaOutputValidate(eaOutput);
      this.eaOutput = eaOutput;
   }
   void eaOutputValidate(java.lang.String eaOutput) throws wt.util.WTPropertyVetoException {
      if (EA_OUTPUT_UPPER_LIMIT < 1) {
         try { EA_OUTPUT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("eaOutput").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EA_OUTPUT_UPPER_LIMIT = 200; }
      }
      if (eaOutput != null && !wt.fc.PersistenceHelper.checkStoredLength(eaOutput.toString(), EA_OUTPUT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "eaOutput"), java.lang.String.valueOf(java.lang.Math.min(EA_OUTPUT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "eaOutput", this.eaOutput, eaOutput));
   }

   /**
    * Capa 분석결과
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CAPA = "capa";
   static int CAPA_UPPER_LIMIT = -1;
   java.lang.String capa = "0";
   /**
    * Capa 분석결과
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCapa() {
      return capa;
   }
   /**
    * Capa 분석결과
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCapa(java.lang.String capa) throws wt.util.WTPropertyVetoException {
      capaValidate(capa);
      this.capa = capa;
   }
   void capaValidate(java.lang.String capa) throws wt.util.WTPropertyVetoException {
      if (CAPA_UPPER_LIMIT < 1) {
         try { CAPA_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("capa").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAPA_UPPER_LIMIT = 200; }
      }
      if (capa != null && !wt.fc.PersistenceHelper.checkStoredLength(capa.toString(), CAPA_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "capa"), java.lang.String.valueOf(java.lang.Math.min(CAPA_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "capa", this.capa, capa));
   }

   /**
    * Capa 비고
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CAPA_NOTE = "capaNote";
   static int CAPA_NOTE_UPPER_LIMIT = -1;
   java.lang.String capaNote;
   /**
    * Capa 비고
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCapaNote() {
      return capaNote;
   }
   /**
    * Capa 비고
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCapaNote(java.lang.String capaNote) throws wt.util.WTPropertyVetoException {
      capaNoteValidate(capaNote);
      this.capaNote = capaNote;
   }
   void capaNoteValidate(java.lang.String capaNote) throws wt.util.WTPropertyVetoException {
      if (CAPA_NOTE_UPPER_LIMIT < 1) {
         try { CAPA_NOTE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("capaNote").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAPA_NOTE_UPPER_LIMIT = 200; }
      }
      if (capaNote != null && !wt.fc.PersistenceHelper.checkStoredLength(capaNote.toString(), CAPA_NOTE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "capaNote"), java.lang.String.valueOf(java.lang.Math.min(CAPA_NOTE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "capaNote", this.capaNote, capaNote));
   }

   /**
    * 수지 원재료 단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_SUJI_PRICE = "mtrSujiPrice";
   static int MTR_SUJI_PRICE_UPPER_LIMIT = -1;
   java.lang.String mtrSujiPrice = "0";
   /**
    * 수지 원재료 단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrSujiPrice() {
      return mtrSujiPrice;
   }
   /**
    * 수지 원재료 단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrSujiPrice(java.lang.String mtrSujiPrice) throws wt.util.WTPropertyVetoException {
      mtrSujiPriceValidate(mtrSujiPrice);
      this.mtrSujiPrice = mtrSujiPrice;
   }
   void mtrSujiPriceValidate(java.lang.String mtrSujiPrice) throws wt.util.WTPropertyVetoException {
      if (MTR_SUJI_PRICE_UPPER_LIMIT < 1) {
         try { MTR_SUJI_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrSujiPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_SUJI_PRICE_UPPER_LIMIT = 200; }
      }
      if (mtrSujiPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrSujiPrice.toString(), MTR_SUJI_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrSujiPrice"), java.lang.String.valueOf(java.lang.Math.min(MTR_SUJI_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrSujiPrice", this.mtrSujiPrice, mtrSujiPrice));
   }

   /**
    * 비철 원재료 단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_METAL_PRICE = "mtrMetalPrice";
   static int MTR_METAL_PRICE_UPPER_LIMIT = -1;
   java.lang.String mtrMetalPrice = "0";
   /**
    * 비철 원재료 단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrMetalPrice() {
      return mtrMetalPrice;
   }
   /**
    * 비철 원재료 단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrMetalPrice(java.lang.String mtrMetalPrice) throws wt.util.WTPropertyVetoException {
      mtrMetalPriceValidate(mtrMetalPrice);
      this.mtrMetalPrice = mtrMetalPrice;
   }
   void mtrMetalPriceValidate(java.lang.String mtrMetalPrice) throws wt.util.WTPropertyVetoException {
      if (MTR_METAL_PRICE_UPPER_LIMIT < 1) {
         try { MTR_METAL_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrMetalPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_METAL_PRICE_UPPER_LIMIT = 200; }
      }
      if (mtrMetalPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrMetalPrice.toString(), MTR_METAL_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrMetalPrice"), java.lang.String.valueOf(java.lang.Math.min(MTR_METAL_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrMetalPrice", this.mtrMetalPrice, mtrMetalPrice));
   }

   /**
    * 원재료 관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_LT_RATE = "mtrLtRate";
   static int MTR_LT_RATE_UPPER_LIMIT = -1;
   java.lang.String mtrLtRate = "0";
   /**
    * 원재료 관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrLtRate() {
      return mtrLtRate;
   }
   /**
    * 원재료 관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrLtRate(java.lang.String mtrLtRate) throws wt.util.WTPropertyVetoException {
      mtrLtRateValidate(mtrLtRate);
      this.mtrLtRate = mtrLtRate;
   }
   void mtrLtRateValidate(java.lang.String mtrLtRate) throws wt.util.WTPropertyVetoException {
      if (MTR_LT_RATE_UPPER_LIMIT < 1) {
         try { MTR_LT_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrLtRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_LT_RATE_UPPER_LIMIT = 200; }
      }
      if (mtrLtRate != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrLtRate.toString(), MTR_LT_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrLtRate"), java.lang.String.valueOf(java.lang.Math.min(MTR_LT_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrLtRate", this.mtrLtRate, mtrLtRate));
   }

   /**
    * 원재료 물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_LT_COST = "mtrLtCost";
   static int MTR_LT_COST_UPPER_LIMIT = -1;
   java.lang.String mtrLtCost = "0";
   /**
    * 원재료 물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrLtCost() {
      return mtrLtCost;
   }
   /**
    * 원재료 물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrLtCost(java.lang.String mtrLtCost) throws wt.util.WTPropertyVetoException {
      mtrLtCostValidate(mtrLtCost);
      this.mtrLtCost = mtrLtCost;
   }
   void mtrLtCostValidate(java.lang.String mtrLtCost) throws wt.util.WTPropertyVetoException {
      if (MTR_LT_COST_UPPER_LIMIT < 1) {
         try { MTR_LT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrLtCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_LT_COST_UPPER_LIMIT = 200; }
      }
      if (mtrLtCost != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrLtCost.toString(), MTR_LT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrLtCost"), java.lang.String.valueOf(java.lang.Math.min(MTR_LT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrLtCost", this.mtrLtCost, mtrLtCost));
   }

   /**
    * 부품 관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PART_LT_RATE = "partLtRate";
   static int PART_LT_RATE_UPPER_LIMIT = -1;
   java.lang.String partLtRate = "0";
   /**
    * 부품 관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPartLtRate() {
      return partLtRate;
   }
   /**
    * 부품 관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPartLtRate(java.lang.String partLtRate) throws wt.util.WTPropertyVetoException {
      partLtRateValidate(partLtRate);
      this.partLtRate = partLtRate;
   }
   void partLtRateValidate(java.lang.String partLtRate) throws wt.util.WTPropertyVetoException {
      if (PART_LT_RATE_UPPER_LIMIT < 1) {
         try { PART_LT_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partLtRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_LT_RATE_UPPER_LIMIT = 200; }
      }
      if (partLtRate != null && !wt.fc.PersistenceHelper.checkStoredLength(partLtRate.toString(), PART_LT_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partLtRate"), java.lang.String.valueOf(java.lang.Math.min(PART_LT_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partLtRate", this.partLtRate, partLtRate));
   }

   /**
    * 부품 물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PART_LT_COST = "partLtCost";
   static int PART_LT_COST_UPPER_LIMIT = -1;
   java.lang.String partLtCost = "0";
   /**
    * 부품 물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPartLtCost() {
      return partLtCost;
   }
   /**
    * 부품 물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPartLtCost(java.lang.String partLtCost) throws wt.util.WTPropertyVetoException {
      partLtCostValidate(partLtCost);
      this.partLtCost = partLtCost;
   }
   void partLtCostValidate(java.lang.String partLtCost) throws wt.util.WTPropertyVetoException {
      if (PART_LT_COST_UPPER_LIMIT < 1) {
         try { PART_LT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partLtCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_LT_COST_UPPER_LIMIT = 200; }
      }
      if (partLtCost != null && !wt.fc.PersistenceHelper.checkStoredLength(partLtCost.toString(), PART_LT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partLtCost"), java.lang.String.valueOf(java.lang.Math.min(PART_LT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partLtCost", this.partLtCost, partLtCost));
   }

   /**
    * 생산 Loss 율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRODUCT_LOSS_RATE = "productLossRate";
   static int PRODUCT_LOSS_RATE_UPPER_LIMIT = -1;
   java.lang.String productLossRate = "0";
   /**
    * 생산 Loss 율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getProductLossRate() {
      return productLossRate;
   }
   /**
    * 생산 Loss 율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProductLossRate(java.lang.String productLossRate) throws wt.util.WTPropertyVetoException {
      productLossRateValidate(productLossRate);
      this.productLossRate = productLossRate;
   }
   void productLossRateValidate(java.lang.String productLossRate) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_LOSS_RATE_UPPER_LIMIT < 1) {
         try { PRODUCT_LOSS_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productLossRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_LOSS_RATE_UPPER_LIMIT = 200; }
      }
      if (productLossRate != null && !wt.fc.PersistenceHelper.checkStoredLength(productLossRate.toString(), PRODUCT_LOSS_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productLossRate"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_LOSS_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productLossRate", this.productLossRate, productLossRate));
   }

   /**
    * 조립Loss
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ASSY_LOSS_EXPR = "assyLossExpr";
   static int ASSY_LOSS_EXPR_UPPER_LIMIT = -1;
   java.lang.String assyLossExpr = "0";
   /**
    * 조립Loss
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAssyLossExpr() {
      return assyLossExpr;
   }
   /**
    * 조립Loss
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAssyLossExpr(java.lang.String assyLossExpr) throws wt.util.WTPropertyVetoException {
      assyLossExprValidate(assyLossExpr);
      this.assyLossExpr = assyLossExpr;
   }
   void assyLossExprValidate(java.lang.String assyLossExpr) throws wt.util.WTPropertyVetoException {
      if (ASSY_LOSS_EXPR_UPPER_LIMIT < 1) {
         try { ASSY_LOSS_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assyLossExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSY_LOSS_EXPR_UPPER_LIMIT = 200; }
      }
      if (assyLossExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(assyLossExpr.toString(), ASSY_LOSS_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assyLossExpr"), java.lang.String.valueOf(java.lang.Math.min(ASSY_LOSS_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assyLossExpr", this.assyLossExpr, assyLossExpr));
   }

   /**
    * 조립Loss율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ASSY_LOSS_RATE = "assyLossRate";
   static int ASSY_LOSS_RATE_UPPER_LIMIT = -1;
   java.lang.String assyLossRate = "0";
   /**
    * 조립Loss율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAssyLossRate() {
      return assyLossRate;
   }
   /**
    * 조립Loss율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAssyLossRate(java.lang.String assyLossRate) throws wt.util.WTPropertyVetoException {
      assyLossRateValidate(assyLossRate);
      this.assyLossRate = assyLossRate;
   }
   void assyLossRateValidate(java.lang.String assyLossRate) throws wt.util.WTPropertyVetoException {
      if (ASSY_LOSS_RATE_UPPER_LIMIT < 1) {
         try { ASSY_LOSS_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assyLossRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSY_LOSS_RATE_UPPER_LIMIT = 200; }
      }
      if (assyLossRate != null && !wt.fc.PersistenceHelper.checkStoredLength(assyLossRate.toString(), ASSY_LOSS_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assyLossRate"), java.lang.String.valueOf(java.lang.Math.min(ASSY_LOSS_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assyLossRate", this.assyLossRate, assyLossRate));
   }

   /**
    * 일반관리비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CO_MANAGE_COST = "coManageCost";
   static int CO_MANAGE_COST_UPPER_LIMIT = -1;
   java.lang.String coManageCost = "0";
   /**
    * 일반관리비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCoManageCost() {
      return coManageCost;
   }
   /**
    * 일반관리비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCoManageCost(java.lang.String coManageCost) throws wt.util.WTPropertyVetoException {
      coManageCostValidate(coManageCost);
      this.coManageCost = coManageCost;
   }
   void coManageCostValidate(java.lang.String coManageCost) throws wt.util.WTPropertyVetoException {
      if (CO_MANAGE_COST_UPPER_LIMIT < 1) {
         try { CO_MANAGE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coManageCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CO_MANAGE_COST_UPPER_LIMIT = 200; }
      }
      if (coManageCost != null && !wt.fc.PersistenceHelper.checkStoredLength(coManageCost.toString(), CO_MANAGE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coManageCost"), java.lang.String.valueOf(java.lang.Math.min(CO_MANAGE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coManageCost", this.coManageCost, coManageCost));
   }

   /**
    * 일반관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CO_MANAGE_EXPR = "coManageExpr";
   static int CO_MANAGE_EXPR_UPPER_LIMIT = -1;
   java.lang.String coManageExpr = "0";
   /**
    * 일반관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCoManageExpr() {
      return coManageExpr;
   }
   /**
    * 일반관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCoManageExpr(java.lang.String coManageExpr) throws wt.util.WTPropertyVetoException {
      coManageExprValidate(coManageExpr);
      this.coManageExpr = coManageExpr;
   }
   void coManageExprValidate(java.lang.String coManageExpr) throws wt.util.WTPropertyVetoException {
      if (CO_MANAGE_EXPR_UPPER_LIMIT < 1) {
         try { CO_MANAGE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coManageExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CO_MANAGE_EXPR_UPPER_LIMIT = 200; }
      }
      if (coManageExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(coManageExpr.toString(), CO_MANAGE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coManageExpr"), java.lang.String.valueOf(java.lang.Math.min(CO_MANAGE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coManageExpr", this.coManageExpr, coManageExpr));
   }

   /**
    * 일반관리비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CO_MANAGE_RATE = "coManageRate";
   static int CO_MANAGE_RATE_UPPER_LIMIT = -1;
   java.lang.String coManageRate = "0";
   /**
    * 일반관리비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCoManageRate() {
      return coManageRate;
   }
   /**
    * 일반관리비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCoManageRate(java.lang.String coManageRate) throws wt.util.WTPropertyVetoException {
      coManageRateValidate(coManageRate);
      this.coManageRate = coManageRate;
   }
   void coManageRateValidate(java.lang.String coManageRate) throws wt.util.WTPropertyVetoException {
      if (CO_MANAGE_RATE_UPPER_LIMIT < 1) {
         try { CO_MANAGE_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coManageRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CO_MANAGE_RATE_UPPER_LIMIT = 200; }
      }
      if (coManageRate != null && !wt.fc.PersistenceHelper.checkStoredLength(coManageRate.toString(), CO_MANAGE_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coManageRate"), java.lang.String.valueOf(java.lang.Math.min(CO_MANAGE_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coManageRate", this.coManageRate, coManageRate));
   }

   /**
    * 불량비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DEFECT_COST_EXPR = "defectCostExpr";
   static int DEFECT_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String defectCostExpr = "0";
   /**
    * 불량비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDefectCostExpr() {
      return defectCostExpr;
   }
   /**
    * 불량비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDefectCostExpr(java.lang.String defectCostExpr) throws wt.util.WTPropertyVetoException {
      defectCostExprValidate(defectCostExpr);
      this.defectCostExpr = defectCostExpr;
   }
   void defectCostExprValidate(java.lang.String defectCostExpr) throws wt.util.WTPropertyVetoException {
      if (DEFECT_COST_EXPR_UPPER_LIMIT < 1) {
         try { DEFECT_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (defectCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(defectCostExpr.toString(), DEFECT_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectCostExpr"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectCostExpr", this.defectCostExpr, defectCostExpr));
   }

   /**
    * 불량율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DEFECT_RATE = "defectRate";
   static int DEFECT_RATE_UPPER_LIMIT = -1;
   java.lang.String defectRate = "0";
   /**
    * 불량율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDefectRate() {
      return defectRate;
   }
   /**
    * 불량율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDefectRate(java.lang.String defectRate) throws wt.util.WTPropertyVetoException {
      defectRateValidate(defectRate);
      this.defectRate = defectRate;
   }
   void defectRateValidate(java.lang.String defectRate) throws wt.util.WTPropertyVetoException {
      if (DEFECT_RATE_UPPER_LIMIT < 1) {
         try { DEFECT_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_RATE_UPPER_LIMIT = 200; }
      }
      if (defectRate != null && !wt.fc.PersistenceHelper.checkStoredLength(defectRate.toString(), DEFECT_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectRate"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectRate", this.defectRate, defectRate));
   }

   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DIRECT_COST_EXPR = "directCostExpr";
   static int DIRECT_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String directCostExpr = "0";
   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDirectCostExpr() {
      return directCostExpr;
   }
   /**
    * 직접경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDirectCostExpr(java.lang.String directCostExpr) throws wt.util.WTPropertyVetoException {
      directCostExprValidate(directCostExpr);
      this.directCostExpr = directCostExpr;
   }
   void directCostExprValidate(java.lang.String directCostExpr) throws wt.util.WTPropertyVetoException {
      if (DIRECT_COST_EXPR_UPPER_LIMIT < 1) {
         try { DIRECT_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("directCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIRECT_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (directCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(directCostExpr.toString(), DIRECT_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "directCostExpr"), java.lang.String.valueOf(java.lang.Math.min(DIRECT_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "directCostExpr", this.directCostExpr, directCostExpr));
   }

   /**
    * 생산효율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String EFFICIENT_RATE = "efficientRate";
   static int EFFICIENT_RATE_UPPER_LIMIT = -1;
   java.lang.String efficientRate = "0";
   /**
    * 생산효율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEfficientRate() {
      return efficientRate;
   }
   /**
    * 생산효율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEfficientRate(java.lang.String efficientRate) throws wt.util.WTPropertyVetoException {
      efficientRateValidate(efficientRate);
      this.efficientRate = efficientRate;
   }
   void efficientRateValidate(java.lang.String efficientRate) throws wt.util.WTPropertyVetoException {
      if (EFFICIENT_RATE_UPPER_LIMIT < 1) {
         try { EFFICIENT_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("efficientRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EFFICIENT_RATE_UPPER_LIMIT = 200; }
      }
      if (efficientRate != null && !wt.fc.PersistenceHelper.checkStoredLength(efficientRate.toString(), EFFICIENT_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "efficientRate"), java.lang.String.valueOf(java.lang.Math.min(EFFICIENT_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "efficientRate", this.efficientRate, efficientRate));
   }

   /**
    * 표준전력비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ELEC_COST = "elecCost";
   static int ELEC_COST_UPPER_LIMIT = -1;
   java.lang.String elecCost = "0";
   /**
    * 표준전력비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getElecCost() {
      return elecCost;
   }
   /**
    * 표준전력비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setElecCost(java.lang.String elecCost) throws wt.util.WTPropertyVetoException {
      elecCostValidate(elecCost);
      this.elecCost = elecCost;
   }
   void elecCostValidate(java.lang.String elecCost) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_UPPER_LIMIT < 1) {
         try { ELEC_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_UPPER_LIMIT = 200; }
      }
      if (elecCost != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCost.toString(), ELEC_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCost"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCost", this.elecCost, elecCost));
   }

   /**
    * 표준전력비상승율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ELEC_COST_CLIMB_RATE = "elecCostClimbRate";
   static int ELEC_COST_CLIMB_RATE_UPPER_LIMIT = -1;
   java.lang.String elecCostClimbRate = "0";
   /**
    * 표준전력비상승율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getElecCostClimbRate() {
      return elecCostClimbRate;
   }
   /**
    * 표준전력비상승율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setElecCostClimbRate(java.lang.String elecCostClimbRate) throws wt.util.WTPropertyVetoException {
      elecCostClimbRateValidate(elecCostClimbRate);
      this.elecCostClimbRate = elecCostClimbRate;
   }
   void elecCostClimbRateValidate(java.lang.String elecCostClimbRate) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_CLIMB_RATE_UPPER_LIMIT < 1) {
         try { ELEC_COST_CLIMB_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCostClimbRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_CLIMB_RATE_UPPER_LIMIT = 200; }
      }
      if (elecCostClimbRate != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCostClimbRate.toString(), ELEC_COST_CLIMB_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCostClimbRate"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_CLIMB_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCostClimbRate", this.elecCostClimbRate, elecCostClimbRate));
   }

   /**
    * 전력비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ELEC_COST_EXPR = "elecCostExpr";
   static int ELEC_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String elecCostExpr = "0";
   /**
    * 전력비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getElecCostExpr() {
      return elecCostExpr;
   }
   /**
    * 전력비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setElecCostExpr(java.lang.String elecCostExpr) throws wt.util.WTPropertyVetoException {
      elecCostExprValidate(elecCostExpr);
      this.elecCostExpr = elecCostExpr;
   }
   void elecCostExprValidate(java.lang.String elecCostExpr) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_EXPR_UPPER_LIMIT < 1) {
         try { ELEC_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (elecCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCostExpr.toString(), ELEC_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCostExpr"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCostExpr", this.elecCostExpr, elecCostExpr));
   }

   /**
    * 표준전력비년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ELEC_COST_YEAR = "elecCostYear";
   static int ELEC_COST_YEAR_UPPER_LIMIT = -1;
   java.lang.String elecCostYear = "0";
   /**
    * 표준전력비년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getElecCostYear() {
      return elecCostYear;
   }
   /**
    * 표준전력비년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setElecCostYear(java.lang.String elecCostYear) throws wt.util.WTPropertyVetoException {
      elecCostYearValidate(elecCostYear);
      this.elecCostYear = elecCostYear;
   }
   void elecCostYearValidate(java.lang.String elecCostYear) throws wt.util.WTPropertyVetoException {
      if (ELEC_COST_YEAR_UPPER_LIMIT < 1) {
         try { ELEC_COST_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elecCostYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELEC_COST_YEAR_UPPER_LIMIT = 200; }
      }
      if (elecCostYear != null && !wt.fc.PersistenceHelper.checkStoredLength(elecCostYear.toString(), ELEC_COST_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elecCostYear"), java.lang.String.valueOf(java.lang.Math.min(ELEC_COST_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elecCostYear", this.elecCostYear, elecCostYear));
   }

   /**
    * 경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String EXPENSE_EXPR = "expenseExpr";
   static int EXPENSE_EXPR_UPPER_LIMIT = -1;
   java.lang.String expenseExpr = "0";
   /**
    * 경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getExpenseExpr() {
      return expenseExpr;
   }
   /**
    * 경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setExpenseExpr(java.lang.String expenseExpr) throws wt.util.WTPropertyVetoException {
      expenseExprValidate(expenseExpr);
      this.expenseExpr = expenseExpr;
   }
   void expenseExprValidate(java.lang.String expenseExpr) throws wt.util.WTPropertyVetoException {
      if (EXPENSE_EXPR_UPPER_LIMIT < 1) {
         try { EXPENSE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expenseExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPENSE_EXPR_UPPER_LIMIT = 200; }
      }
      if (expenseExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(expenseExpr.toString(), EXPENSE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expenseExpr"), java.lang.String.valueOf(java.lang.Math.min(EXPENSE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expenseExpr", this.expenseExpr, expenseExpr));
   }

   /**
    * 간접경비비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String IN_DIRECT_COST = "inDirectCost";
   static int IN_DIRECT_COST_UPPER_LIMIT = -1;
   java.lang.String inDirectCost = "0";
   /**
    * 간접경비비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getInDirectCost() {
      return inDirectCost;
   }
   /**
    * 간접경비비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setInDirectCost(java.lang.String inDirectCost) throws wt.util.WTPropertyVetoException {
      inDirectCostValidate(inDirectCost);
      this.inDirectCost = inDirectCost;
   }
   void inDirectCostValidate(java.lang.String inDirectCost) throws wt.util.WTPropertyVetoException {
      if (IN_DIRECT_COST_UPPER_LIMIT < 1) {
         try { IN_DIRECT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inDirectCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_DIRECT_COST_UPPER_LIMIT = 200; }
      }
      if (inDirectCost != null && !wt.fc.PersistenceHelper.checkStoredLength(inDirectCost.toString(), IN_DIRECT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inDirectCost"), java.lang.String.valueOf(java.lang.Math.min(IN_DIRECT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inDirectCost", this.inDirectCost, inDirectCost));
   }

   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String IN_DIRECT_COST_EXPR = "inDirectCostExpr";
   static int IN_DIRECT_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String inDirectCostExpr = "0";
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getInDirectCostExpr() {
      return inDirectCostExpr;
   }
   /**
    * 간접경비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setInDirectCostExpr(java.lang.String inDirectCostExpr) throws wt.util.WTPropertyVetoException {
      inDirectCostExprValidate(inDirectCostExpr);
      this.inDirectCostExpr = inDirectCostExpr;
   }
   void inDirectCostExprValidate(java.lang.String inDirectCostExpr) throws wt.util.WTPropertyVetoException {
      if (IN_DIRECT_COST_EXPR_UPPER_LIMIT < 1) {
         try { IN_DIRECT_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inDirectCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_DIRECT_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (inDirectCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(inDirectCostExpr.toString(), IN_DIRECT_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inDirectCostExpr"), java.lang.String.valueOf(java.lang.Math.min(IN_DIRECT_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inDirectCostExpr", this.inDirectCostExpr, inDirectCostExpr));
   }

   /**
    * 간접경비2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String IN_DIRECT_COST2_EXPR = "inDirectCost2Expr";
   static int IN_DIRECT_COST2_EXPR_UPPER_LIMIT = -1;
   java.lang.String inDirectCost2Expr = "0";
   /**
    * 간접경비2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getInDirectCost2Expr() {
      return inDirectCost2Expr;
   }
   /**
    * 간접경비2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setInDirectCost2Expr(java.lang.String inDirectCost2Expr) throws wt.util.WTPropertyVetoException {
      inDirectCost2ExprValidate(inDirectCost2Expr);
      this.inDirectCost2Expr = inDirectCost2Expr;
   }
   void inDirectCost2ExprValidate(java.lang.String inDirectCost2Expr) throws wt.util.WTPropertyVetoException {
      if (IN_DIRECT_COST2_EXPR_UPPER_LIMIT < 1) {
         try { IN_DIRECT_COST2_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("inDirectCost2Expr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IN_DIRECT_COST2_EXPR_UPPER_LIMIT = 200; }
      }
      if (inDirectCost2Expr != null && !wt.fc.PersistenceHelper.checkStoredLength(inDirectCost2Expr.toString(), IN_DIRECT_COST2_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "inDirectCost2Expr"), java.lang.String.valueOf(java.lang.Math.min(IN_DIRECT_COST2_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "inDirectCost2Expr", this.inDirectCost2Expr, inDirectCost2Expr));
   }

   /**
    * 간접경비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String INDIRECT_COST_RATE = "indirectCostRate";
   static int INDIRECT_COST_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectCostRate = "0";
   /**
    * 간접경비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getIndirectCostRate() {
      return indirectCostRate;
   }
   /**
    * 간접경비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setIndirectCostRate(java.lang.String indirectCostRate) throws wt.util.WTPropertyVetoException {
      indirectCostRateValidate(indirectCostRate);
      this.indirectCostRate = indirectCostRate;
   }
   void indirectCostRateValidate(java.lang.String indirectCostRate) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_COST_RATE_UPPER_LIMIT < 1) {
         try { INDIRECT_COST_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectCostRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_COST_RATE_UPPER_LIMIT = 200; }
      }
      if (indirectCostRate != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectCostRate.toString(), INDIRECT_COST_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectCostRate"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_COST_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectCostRate", this.indirectCostRate, indirectCostRate));
   }

   /**
    * BuyBack간접경비율(본사)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String BUY_BACK_INDIRECT_COST_RATE = "buyBackIndirectCostRate";
   static int BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT = -1;
   java.lang.String buyBackIndirectCostRate = "0";
   /**
    * BuyBack간접경비율(본사)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getBuyBackIndirectCostRate() {
      return buyBackIndirectCostRate;
   }
   /**
    * BuyBack간접경비율(본사)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setBuyBackIndirectCostRate(java.lang.String buyBackIndirectCostRate) throws wt.util.WTPropertyVetoException {
      buyBackIndirectCostRateValidate(buyBackIndirectCostRate);
      this.buyBackIndirectCostRate = buyBackIndirectCostRate;
   }
   void buyBackIndirectCostRateValidate(java.lang.String buyBackIndirectCostRate) throws wt.util.WTPropertyVetoException {
      if (BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT < 1) {
         try { BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("buyBackIndirectCostRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT = 200; }
      }
      if (buyBackIndirectCostRate != null && !wt.fc.PersistenceHelper.checkStoredLength(buyBackIndirectCostRate.toString(), BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "buyBackIndirectCostRate"), java.lang.String.valueOf(java.lang.Math.min(BUY_BACK_INDIRECT_COST_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "buyBackIndirectCostRate", this.buyBackIndirectCostRate, buyBackIndirectCostRate));
   }

   /**
    * 표준임율상승율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_COST_CLIMB_RATE = "laborCostClimbRate";
   static int LABOR_COST_CLIMB_RATE_UPPER_LIMIT = -1;
   java.lang.String laborCostClimbRate = "0";
   /**
    * 표준임율상승율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborCostClimbRate() {
      return laborCostClimbRate;
   }
   /**
    * 표준임율상승율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborCostClimbRate(java.lang.String laborCostClimbRate) throws wt.util.WTPropertyVetoException {
      laborCostClimbRateValidate(laborCostClimbRate);
      this.laborCostClimbRate = laborCostClimbRate;
   }
   void laborCostClimbRateValidate(java.lang.String laborCostClimbRate) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_CLIMB_RATE_UPPER_LIMIT < 1) {
         try { LABOR_COST_CLIMB_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostClimbRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_CLIMB_RATE_UPPER_LIMIT = 200; }
      }
      if (laborCostClimbRate != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostClimbRate.toString(), LABOR_COST_CLIMB_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostClimbRate"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_CLIMB_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostClimbRate", this.laborCostClimbRate, laborCostClimbRate));
   }

   /**
    * 노무비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_COST_EXPR = "laborCostExpr";
   static int LABOR_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String laborCostExpr = "0";
   /**
    * 노무비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborCostExpr() {
      return laborCostExpr;
   }
   /**
    * 노무비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborCostExpr(java.lang.String laborCostExpr) throws wt.util.WTPropertyVetoException {
      laborCostExprValidate(laborCostExpr);
      this.laborCostExpr = laborCostExpr;
   }
   void laborCostExprValidate(java.lang.String laborCostExpr) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_EXPR_UPPER_LIMIT < 1) {
         try { LABOR_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (laborCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostExpr.toString(), LABOR_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostExpr"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostExpr", this.laborCostExpr, laborCostExpr));
   }

   /**
    * 표준임율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_COST_RATE = "laborCostRate";
   static int LABOR_COST_RATE_UPPER_LIMIT = -1;
   java.lang.String laborCostRate = "0";
   /**
    * 표준임율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborCostRate() {
      return laborCostRate;
   }
   /**
    * 표준임율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborCostRate(java.lang.String laborCostRate) throws wt.util.WTPropertyVetoException {
      laborCostRateValidate(laborCostRate);
      this.laborCostRate = laborCostRate;
   }
   void laborCostRateValidate(java.lang.String laborCostRate) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_RATE_UPPER_LIMIT < 1) {
         try { LABOR_COST_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_RATE_UPPER_LIMIT = 200; }
      }
      if (laborCostRate != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostRate.toString(), LABOR_COST_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostRate"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostRate", this.laborCostRate, laborCostRate));
   }

   /**
    * 표준임율년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_COST_YEAR = "laborCostYear";
   static int LABOR_COST_YEAR_UPPER_LIMIT = -1;
   java.lang.String laborCostYear = "0";
   /**
    * 표준임율년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborCostYear() {
      return laborCostYear;
   }
   /**
    * 표준임율년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborCostYear(java.lang.String laborCostYear) throws wt.util.WTPropertyVetoException {
      laborCostYearValidate(laborCostYear);
      this.laborCostYear = laborCostYear;
   }
   void laborCostYearValidate(java.lang.String laborCostYear) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_YEAR_UPPER_LIMIT < 1) {
         try { LABOR_COST_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_YEAR_UPPER_LIMIT = 200; }
      }
      if (laborCostYear != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostYear.toString(), LABOR_COST_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostYear"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostYear", this.laborCostYear, laborCostYear));
   }

   /**
    * 임율(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_RATE_EXPR = "laborRateExpr";
   static int LABOR_RATE_EXPR_UPPER_LIMIT = -1;
   java.lang.String laborRateExpr = "0";
   /**
    * 임율(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborRateExpr() {
      return laborRateExpr;
   }
   /**
    * 임율(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborRateExpr(java.lang.String laborRateExpr) throws wt.util.WTPropertyVetoException {
      laborRateExprValidate(laborRateExpr);
      this.laborRateExpr = laborRateExpr;
   }
   void laborRateExprValidate(java.lang.String laborRateExpr) throws wt.util.WTPropertyVetoException {
      if (LABOR_RATE_EXPR_UPPER_LIMIT < 1) {
         try { LABOR_RATE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborRateExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_RATE_EXPR_UPPER_LIMIT = 200; }
      }
      if (laborRateExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(laborRateExpr.toString(), LABOR_RATE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborRateExpr"), java.lang.String.valueOf(java.lang.Math.min(LABOR_RATE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborRateExpr", this.laborRateExpr, laborRateExpr));
   }

   /**
    * 기계감가(사출,프레스)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MACHINE_DPC_COST = "machineDpcCost";
   static int MACHINE_DPC_COST_UPPER_LIMIT = -1;
   java.lang.String machineDpcCost = "0";
   /**
    * 기계감가(사출,프레스)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMachineDpcCost() {
      return machineDpcCost;
   }
   /**
    * 기계감가(사출,프레스)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMachineDpcCost(java.lang.String machineDpcCost) throws wt.util.WTPropertyVetoException {
      machineDpcCostValidate(machineDpcCost);
      this.machineDpcCost = machineDpcCost;
   }
   void machineDpcCostValidate(java.lang.String machineDpcCost) throws wt.util.WTPropertyVetoException {
      if (MACHINE_DPC_COST_UPPER_LIMIT < 1) {
         try { MACHINE_DPC_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineDpcCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_DPC_COST_UPPER_LIMIT = 200; }
      }
      if (machineDpcCost != null && !wt.fc.PersistenceHelper.checkStoredLength(machineDpcCost.toString(), MACHINE_DPC_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineDpcCost"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_DPC_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineDpcCost", this.machineDpcCost, machineDpcCost));
   }

   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MACHINE_DPC_COST_EXPR = "machineDpcCostExpr";
   static int MACHINE_DPC_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String machineDpcCostExpr = "0";
   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMachineDpcCostExpr() {
      return machineDpcCostExpr;
   }
   /**
    * 기계감가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMachineDpcCostExpr(java.lang.String machineDpcCostExpr) throws wt.util.WTPropertyVetoException {
      machineDpcCostExprValidate(machineDpcCostExpr);
      this.machineDpcCostExpr = machineDpcCostExpr;
   }
   void machineDpcCostExprValidate(java.lang.String machineDpcCostExpr) throws wt.util.WTPropertyVetoException {
      if (MACHINE_DPC_COST_EXPR_UPPER_LIMIT < 1) {
         try { MACHINE_DPC_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineDpcCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_DPC_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (machineDpcCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(machineDpcCostExpr.toString(), MACHINE_DPC_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineDpcCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_DPC_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineDpcCostExpr", this.machineDpcCostExpr, machineDpcCostExpr));
   }

   /**
    * 기계감가(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MACHINE_REDUCE_COST = "machineReduceCost";
   static int MACHINE_REDUCE_COST_UPPER_LIMIT = -1;
   java.lang.String machineReduceCost = "0";
   /**
    * 기계감가(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMachineReduceCost() {
      return machineReduceCost;
   }
   /**
    * 기계감가(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMachineReduceCost(java.lang.String machineReduceCost) throws wt.util.WTPropertyVetoException {
      machineReduceCostValidate(machineReduceCost);
      this.machineReduceCost = machineReduceCost;
   }
   void machineReduceCostValidate(java.lang.String machineReduceCost) throws wt.util.WTPropertyVetoException {
      if (MACHINE_REDUCE_COST_UPPER_LIMIT < 1) {
         try { MACHINE_REDUCE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineReduceCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_REDUCE_COST_UPPER_LIMIT = 200; }
      }
      if (machineReduceCost != null && !wt.fc.PersistenceHelper.checkStoredLength(machineReduceCost.toString(), MACHINE_REDUCE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineReduceCost"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_REDUCE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineReduceCost", this.machineReduceCost, machineReduceCost));
   }

   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MATERIAL_COST_EXPR = "materialCostExpr";
   static int MATERIAL_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String materialCostExpr = "0";
   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMaterialCostExpr() {
      return materialCostExpr;
   }
   /**
    * 재료비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMaterialCostExpr(java.lang.String materialCostExpr) throws wt.util.WTPropertyVetoException {
      materialCostExprValidate(materialCostExpr);
      this.materialCostExpr = materialCostExpr;
   }
   void materialCostExprValidate(java.lang.String materialCostExpr) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_COST_EXPR_UPPER_LIMIT < 1) {
         try { MATERIAL_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("materialCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (materialCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(materialCostExpr.toString(), MATERIAL_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "materialCostExpr", this.materialCostExpr, materialCostExpr));
   }

   /**
    * 비철스크랩판매
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_SCRAP_COST = "metalScrapCost";
   static int METAL_SCRAP_COST_UPPER_LIMIT = -1;
   java.lang.String metalScrapCost = "0";
   /**
    * 비철스크랩판매
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalScrapCost() {
      return metalScrapCost;
   }
   /**
    * 비철스크랩판매
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalScrapCost(java.lang.String metalScrapCost) throws wt.util.WTPropertyVetoException {
      metalScrapCostValidate(metalScrapCost);
      this.metalScrapCost = metalScrapCost;
   }
   void metalScrapCostValidate(java.lang.String metalScrapCost) throws wt.util.WTPropertyVetoException {
      if (METAL_SCRAP_COST_UPPER_LIMIT < 1) {
         try { METAL_SCRAP_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalScrapCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_SCRAP_COST_UPPER_LIMIT = 200; }
      }
      if (metalScrapCost != null && !wt.fc.PersistenceHelper.checkStoredLength(metalScrapCost.toString(), METAL_SCRAP_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalScrapCost"), java.lang.String.valueOf(java.lang.Math.min(METAL_SCRAP_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalScrapCost", this.metalScrapCost, metalScrapCost));
   }

   /**
    * 비철재생단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_SCRAP_RECYCLE = "metalScrapRecycle";
   static int METAL_SCRAP_RECYCLE_UPPER_LIMIT = -1;
   java.lang.String metalScrapRecycle = "0";
   /**
    * 비철재생단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalScrapRecycle() {
      return metalScrapRecycle;
   }
   /**
    * 비철재생단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalScrapRecycle(java.lang.String metalScrapRecycle) throws wt.util.WTPropertyVetoException {
      metalScrapRecycleValidate(metalScrapRecycle);
      this.metalScrapRecycle = metalScrapRecycle;
   }
   void metalScrapRecycleValidate(java.lang.String metalScrapRecycle) throws wt.util.WTPropertyVetoException {
      if (METAL_SCRAP_RECYCLE_UPPER_LIMIT < 1) {
         try { METAL_SCRAP_RECYCLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalScrapRecycle").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_SCRAP_RECYCLE_UPPER_LIMIT = 200; }
      }
      if (metalScrapRecycle != null && !wt.fc.PersistenceHelper.checkStoredLength(metalScrapRecycle.toString(), METAL_SCRAP_RECYCLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalScrapRecycle"), java.lang.String.valueOf(java.lang.Math.min(METAL_SCRAP_RECYCLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalScrapRecycle", this.metalScrapRecycle, metalScrapRecycle));
   }

   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MAINTENANCE = "moldMaintenance";
   static int MOLD_MAINTENANCE_UPPER_LIMIT = -1;
   java.lang.String moldMaintenance = "0";
   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMaintenance() {
      return moldMaintenance;
   }
   /**
    * 금형유지비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMaintenance(java.lang.String moldMaintenance) throws wt.util.WTPropertyVetoException {
      moldMaintenanceValidate(moldMaintenance);
      this.moldMaintenance = moldMaintenance;
   }
   void moldMaintenanceValidate(java.lang.String moldMaintenance) throws wt.util.WTPropertyVetoException {
      if (MOLD_MAINTENANCE_UPPER_LIMIT < 1) {
         try { MOLD_MAINTENANCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMaintenance").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MAINTENANCE_UPPER_LIMIT = 200; }
      }
      if (moldMaintenance != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMaintenance.toString(), MOLD_MAINTENANCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMaintenance"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MAINTENANCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMaintenance", this.moldMaintenance, moldMaintenance));
   }

   /**
    * 금형준비시간
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_READY_TIME = "moldReadyTime";
   static int MOLD_READY_TIME_UPPER_LIMIT = -1;
   java.lang.String moldReadyTime = "0";
   /**
    * 금형준비시간
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldReadyTime() {
      return moldReadyTime;
   }
   /**
    * 금형준비시간
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldReadyTime(java.lang.String moldReadyTime) throws wt.util.WTPropertyVetoException {
      moldReadyTimeValidate(moldReadyTime);
      this.moldReadyTime = moldReadyTime;
   }
   void moldReadyTimeValidate(java.lang.String moldReadyTime) throws wt.util.WTPropertyVetoException {
      if (MOLD_READY_TIME_UPPER_LIMIT < 1) {
         try { MOLD_READY_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldReadyTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_READY_TIME_UPPER_LIMIT = 200; }
      }
      if (moldReadyTime != null && !wt.fc.PersistenceHelper.checkStoredLength(moldReadyTime.toString(), MOLD_READY_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldReadyTime"), java.lang.String.valueOf(java.lang.Math.min(MOLD_READY_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldReadyTime", this.moldReadyTime, moldReadyTime));
   }

   /**
    * 공정물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_LT_COST_EXPR = "mtrLtCostExpr";
   static int MTR_LT_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String mtrLtCostExpr = "0";
   /**
    * 공정물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrLtCostExpr() {
      return mtrLtCostExpr;
   }
   /**
    * 공정물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrLtCostExpr(java.lang.String mtrLtCostExpr) throws wt.util.WTPropertyVetoException {
      mtrLtCostExprValidate(mtrLtCostExpr);
      this.mtrLtCostExpr = mtrLtCostExpr;
   }
   void mtrLtCostExprValidate(java.lang.String mtrLtCostExpr) throws wt.util.WTPropertyVetoException {
      if (MTR_LT_COST_EXPR_UPPER_LIMIT < 1) {
         try { MTR_LT_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrLtCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_LT_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (mtrLtCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrLtCostExpr.toString(), MTR_LT_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrLtCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MTR_LT_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrLtCostExpr", this.mtrLtCostExpr, mtrLtCostExpr));
   }

   /**
    * 공정관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_LT_RATE_EXPR = "mtrLtRateExpr";
   static int MTR_LT_RATE_EXPR_UPPER_LIMIT = -1;
   java.lang.String mtrLtRateExpr = "0";
   /**
    * 공정관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrLtRateExpr() {
      return mtrLtRateExpr;
   }
   /**
    * 공정관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrLtRateExpr(java.lang.String mtrLtRateExpr) throws wt.util.WTPropertyVetoException {
      mtrLtRateExprValidate(mtrLtRateExpr);
      this.mtrLtRateExpr = mtrLtRateExpr;
   }
   void mtrLtRateExprValidate(java.lang.String mtrLtRateExpr) throws wt.util.WTPropertyVetoException {
      if (MTR_LT_RATE_EXPR_UPPER_LIMIT < 1) {
         try { MTR_LT_RATE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrLtRateExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_LT_RATE_EXPR_UPPER_LIMIT = 200; }
      }
      if (mtrLtRateExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrLtRateExpr.toString(), MTR_LT_RATE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrLtRateExpr"), java.lang.String.valueOf(java.lang.Math.min(MTR_LT_RATE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrLtRateExpr", this.mtrLtRateExpr, mtrLtRateExpr));
   }

   /**
    * 재료관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_MANAGE_EXPR = "mtrManageExpr";
   static int MTR_MANAGE_EXPR_UPPER_LIMIT = -1;
   java.lang.String mtrManageExpr = "0";
   /**
    * 재료관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrManageExpr() {
      return mtrManageExpr;
   }
   /**
    * 재료관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrManageExpr(java.lang.String mtrManageExpr) throws wt.util.WTPropertyVetoException {
      mtrManageExprValidate(mtrManageExpr);
      this.mtrManageExpr = mtrManageExpr;
   }
   void mtrManageExprValidate(java.lang.String mtrManageExpr) throws wt.util.WTPropertyVetoException {
      if (MTR_MANAGE_EXPR_UPPER_LIMIT < 1) {
         try { MTR_MANAGE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrManageExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_MANAGE_EXPR_UPPER_LIMIT = 200; }
      }
      if (mtrManageExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrManageExpr.toString(), MTR_MANAGE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrManageExpr"), java.lang.String.valueOf(java.lang.Math.min(MTR_MANAGE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrManageExpr", this.mtrManageExpr, mtrManageExpr));
   }

   /**
    * 재료관리비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MTR_MANAGE_RATE = "mtrManageRate";
   static int MTR_MANAGE_RATE_UPPER_LIMIT = -1;
   java.lang.String mtrManageRate = "0";
   /**
    * 재료관리비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMtrManageRate() {
      return mtrManageRate;
   }
   /**
    * 재료관리비율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMtrManageRate(java.lang.String mtrManageRate) throws wt.util.WTPropertyVetoException {
      mtrManageRateValidate(mtrManageRate);
      this.mtrManageRate = mtrManageRate;
   }
   void mtrManageRateValidate(java.lang.String mtrManageRate) throws wt.util.WTPropertyVetoException {
      if (MTR_MANAGE_RATE_UPPER_LIMIT < 1) {
         try { MTR_MANAGE_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mtrManageRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MTR_MANAGE_RATE_UPPER_LIMIT = 200; }
      }
      if (mtrManageRate != null && !wt.fc.PersistenceHelper.checkStoredLength(mtrManageRate.toString(), MTR_MANAGE_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mtrManageRate"), java.lang.String.valueOf(java.lang.Math.min(MTR_MANAGE_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mtrManageRate", this.mtrManageRate, mtrManageRate));
   }

   /**
    * 생산량(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String OUTPUT_EXPR = "outputExpr";
   static int OUTPUT_EXPR_UPPER_LIMIT = -1;
   java.lang.String outputExpr = "0";
   /**
    * 생산량(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getOutputExpr() {
      return outputExpr;
   }
   /**
    * 생산량(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setOutputExpr(java.lang.String outputExpr) throws wt.util.WTPropertyVetoException {
      outputExprValidate(outputExpr);
      this.outputExpr = outputExpr;
   }
   void outputExprValidate(java.lang.String outputExpr) throws wt.util.WTPropertyVetoException {
      if (OUTPUT_EXPR_UPPER_LIMIT < 1) {
         try { OUTPUT_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outputExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUTPUT_EXPR_UPPER_LIMIT = 200; }
      }
      if (outputExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(outputExpr.toString(), OUTPUT_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputExpr"), java.lang.String.valueOf(java.lang.Math.min(OUTPUT_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outputExpr", this.outputExpr, outputExpr));
   }

   /**
    * 부품전체합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PART_TOTAL_COST = "partTotalCost";
   static int PART_TOTAL_COST_UPPER_LIMIT = -1;
   java.lang.String partTotalCost = "0";
   /**
    * 부품전체합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPartTotalCost() {
      return partTotalCost;
   }
   /**
    * 부품전체합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPartTotalCost(java.lang.String partTotalCost) throws wt.util.WTPropertyVetoException {
      partTotalCostValidate(partTotalCost);
      this.partTotalCost = partTotalCost;
   }
   void partTotalCostValidate(java.lang.String partTotalCost) throws wt.util.WTPropertyVetoException {
      if (PART_TOTAL_COST_UPPER_LIMIT < 1) {
         try { PART_TOTAL_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partTotalCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_TOTAL_COST_UPPER_LIMIT = 200; }
      }
      if (partTotalCost != null && !wt.fc.PersistenceHelper.checkStoredLength(partTotalCost.toString(), PART_TOTAL_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partTotalCost"), java.lang.String.valueOf(java.lang.Math.min(PART_TOTAL_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partTotalCost", this.partTotalCost, partTotalCost));
   }

   /**
    * 납입물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PAY_COST_LT_EXPR = "payCostLtExpr";
   static int PAY_COST_LT_EXPR_UPPER_LIMIT = -1;
   java.lang.String payCostLtExpr = "0";
   /**
    * 납입물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPayCostLtExpr() {
      return payCostLtExpr;
   }
   /**
    * 납입물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPayCostLtExpr(java.lang.String payCostLtExpr) throws wt.util.WTPropertyVetoException {
      payCostLtExprValidate(payCostLtExpr);
      this.payCostLtExpr = payCostLtExpr;
   }
   void payCostLtExprValidate(java.lang.String payCostLtExpr) throws wt.util.WTPropertyVetoException {
      if (PAY_COST_LT_EXPR_UPPER_LIMIT < 1) {
         try { PAY_COST_LT_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payCostLtExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_COST_LT_EXPR_UPPER_LIMIT = 200; }
      }
      if (payCostLtExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(payCostLtExpr.toString(), PAY_COST_LT_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payCostLtExpr"), java.lang.String.valueOf(java.lang.Math.min(PAY_COST_LT_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payCostLtExpr", this.payCostLtExpr, payCostLtExpr));
   }

   /**
    * 납입물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PAY_LT_COST = "payLtCost";
   static int PAY_LT_COST_UPPER_LIMIT = -1;
   java.lang.String payLtCost = "0";
   /**
    * 납입물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPayLtCost() {
      return payLtCost;
   }
   /**
    * 납입물류비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPayLtCost(java.lang.String payLtCost) throws wt.util.WTPropertyVetoException {
      payLtCostValidate(payLtCost);
      this.payLtCost = payLtCost;
   }
   void payLtCostValidate(java.lang.String payLtCost) throws wt.util.WTPropertyVetoException {
      if (PAY_LT_COST_UPPER_LIMIT < 1) {
         try { PAY_LT_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payLtCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_LT_COST_UPPER_LIMIT = 200; }
      }
      if (payLtCost != null && !wt.fc.PersistenceHelper.checkStoredLength(payLtCost.toString(), PAY_LT_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payLtCost"), java.lang.String.valueOf(java.lang.Math.min(PAY_LT_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payLtCost", this.payLtCost, payLtCost));
   }

   /**
    * 납입관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PAY_RATE_LT = "payRateLt";
   static int PAY_RATE_LT_UPPER_LIMIT = -1;
   java.lang.String payRateLt = "0";
   /**
    * 납입관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPayRateLt() {
      return payRateLt;
   }
   /**
    * 납입관세율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPayRateLt(java.lang.String payRateLt) throws wt.util.WTPropertyVetoException {
      payRateLtValidate(payRateLt);
      this.payRateLt = payRateLt;
   }
   void payRateLtValidate(java.lang.String payRateLt) throws wt.util.WTPropertyVetoException {
      if (PAY_RATE_LT_UPPER_LIMIT < 1) {
         try { PAY_RATE_LT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payRateLt").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_RATE_LT_UPPER_LIMIT = 200; }
      }
      if (payRateLt != null && !wt.fc.PersistenceHelper.checkStoredLength(payRateLt.toString(), PAY_RATE_LT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payRateLt"), java.lang.String.valueOf(java.lang.Math.min(PAY_RATE_LT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payRateLt", this.payRateLt, payRateLt));
   }

   /**
    * 납입관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PAY_RATE_LT_EXPR = "payRateLtExpr";
   static int PAY_RATE_LT_EXPR_UPPER_LIMIT = -1;
   java.lang.String payRateLtExpr = "0";
   /**
    * 납입관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPayRateLtExpr() {
      return payRateLtExpr;
   }
   /**
    * 납입관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPayRateLtExpr(java.lang.String payRateLtExpr) throws wt.util.WTPropertyVetoException {
      payRateLtExprValidate(payRateLtExpr);
      this.payRateLtExpr = payRateLtExpr;
   }
   void payRateLtExprValidate(java.lang.String payRateLtExpr) throws wt.util.WTPropertyVetoException {
      if (PAY_RATE_LT_EXPR_UPPER_LIMIT < 1) {
         try { PAY_RATE_LT_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payRateLtExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_RATE_LT_EXPR_UPPER_LIMIT = 200; }
      }
      if (payRateLtExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(payRateLtExpr.toString(), PAY_RATE_LT_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payRateLtExpr"), java.lang.String.valueOf(java.lang.Math.min(PAY_RATE_LT_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payRateLtExpr", this.payRateLtExpr, payRateLtExpr));
   }

   /**
    * 생산보유개월
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRODUCT_HAVE_MONTH = "productHaveMonth";
   static int PRODUCT_HAVE_MONTH_UPPER_LIMIT = -1;
   java.lang.String productHaveMonth = "0";
   /**
    * 생산보유개월
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getProductHaveMonth() {
      return productHaveMonth;
   }
   /**
    * 생산보유개월
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProductHaveMonth(java.lang.String productHaveMonth) throws wt.util.WTPropertyVetoException {
      productHaveMonthValidate(productHaveMonth);
      this.productHaveMonth = productHaveMonth;
   }
   void productHaveMonthValidate(java.lang.String productHaveMonth) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_HAVE_MONTH_UPPER_LIMIT < 1) {
         try { PRODUCT_HAVE_MONTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productHaveMonth").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_HAVE_MONTH_UPPER_LIMIT = 200; }
      }
      if (productHaveMonth != null && !wt.fc.PersistenceHelper.checkStoredLength(productHaveMonth.toString(), PRODUCT_HAVE_MONTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productHaveMonth"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_HAVE_MONTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productHaveMonth", this.productHaveMonth, productHaveMonth));
   }

   /**
    * 생산Loss
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRODUCT_LOSS_EXPR = "productLossExpr";
   static int PRODUCT_LOSS_EXPR_UPPER_LIMIT = -1;
   java.lang.String productLossExpr = "0";
   /**
    * 생산Loss
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getProductLossExpr() {
      return productLossExpr;
   }
   /**
    * 생산Loss
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProductLossExpr(java.lang.String productLossExpr) throws wt.util.WTPropertyVetoException {
      productLossExprValidate(productLossExpr);
      this.productLossExpr = productLossExpr;
   }
   void productLossExprValidate(java.lang.String productLossExpr) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_LOSS_EXPR_UPPER_LIMIT < 1) {
         try { PRODUCT_LOSS_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productLossExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_LOSS_EXPR_UPPER_LIMIT = 200; }
      }
      if (productLossExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(productLossExpr.toString(), PRODUCT_LOSS_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productLossExpr"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_LOSS_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productLossExpr", this.productLossExpr, productLossExpr));
   }

   /**
    * 구매단가환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String P_MONETARY_UNIT_CURRENCY = "pMonetaryUnitCurrency";
   static int P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String pMonetaryUnitCurrency = "0";
   /**
    * 구매단가환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPMonetaryUnitCurrency() {
      return pMonetaryUnitCurrency;
   }
   /**
    * 구매단가환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPMonetaryUnitCurrency(java.lang.String pMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      pMonetaryUnitCurrencyValidate(pMonetaryUnitCurrency);
      this.pMonetaryUnitCurrency = pMonetaryUnitCurrency;
   }
   void pMonetaryUnitCurrencyValidate(java.lang.String pMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pMonetaryUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (pMonetaryUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(pMonetaryUnitCurrency.toString(), P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pMonetaryUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(P_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pMonetaryUnitCurrency", this.pMonetaryUnitCurrency, pMonetaryUnitCurrency));
   }

   /**
    * 원재료비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String RAW_MATERIAL_COST_EXPR = "rawMaterialCostExpr";
   static int RAW_MATERIAL_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String rawMaterialCostExpr = "0";
   /**
    * 원재료비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRawMaterialCostExpr() {
      return rawMaterialCostExpr;
   }
   /**
    * 원재료비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRawMaterialCostExpr(java.lang.String rawMaterialCostExpr) throws wt.util.WTPropertyVetoException {
      rawMaterialCostExprValidate(rawMaterialCostExpr);
      this.rawMaterialCostExpr = rawMaterialCostExpr;
   }
   void rawMaterialCostExprValidate(java.lang.String rawMaterialCostExpr) throws wt.util.WTPropertyVetoException {
      if (RAW_MATERIAL_COST_EXPR_UPPER_LIMIT < 1) {
         try { RAW_MATERIAL_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rawMaterialCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RAW_MATERIAL_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (rawMaterialCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(rawMaterialCostExpr.toString(), RAW_MATERIAL_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rawMaterialCostExpr"), java.lang.String.valueOf(java.lang.Math.min(RAW_MATERIAL_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rawMaterialCostExpr", this.rawMaterialCostExpr, rawMaterialCostExpr));
   }

   /**
    * 감가비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String REDUCE_COST_EXPR = "reduceCostExpr";
   static int REDUCE_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String reduceCostExpr = "0";
   /**
    * 감가비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getReduceCostExpr() {
      return reduceCostExpr;
   }
   /**
    * 감가비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setReduceCostExpr(java.lang.String reduceCostExpr) throws wt.util.WTPropertyVetoException {
      reduceCostExprValidate(reduceCostExpr);
      this.reduceCostExpr = reduceCostExpr;
   }
   void reduceCostExprValidate(java.lang.String reduceCostExpr) throws wt.util.WTPropertyVetoException {
      if (REDUCE_COST_EXPR_UPPER_LIMIT < 1) {
         try { REDUCE_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reduceCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REDUCE_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (reduceCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(reduceCostExpr.toString(), REDUCE_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reduceCostExpr"), java.lang.String.valueOf(java.lang.Math.min(REDUCE_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reduceCostExpr", this.reduceCostExpr, reduceCostExpr));
   }

   /**
    * R&D율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String RND = "rnd";
   static int RND_UPPER_LIMIT = -1;
   java.lang.String rnd = "0";
   /**
    * R&D율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRnd() {
      return rnd;
   }
   /**
    * R&D율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRnd(java.lang.String rnd) throws wt.util.WTPropertyVetoException {
      rndValidate(rnd);
      this.rnd = rnd;
   }
   void rndValidate(java.lang.String rnd) throws wt.util.WTPropertyVetoException {
      if (RND_UPPER_LIMIT < 1) {
         try { RND_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rnd").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RND_UPPER_LIMIT = 200; }
      }
      if (rnd != null && !wt.fc.PersistenceHelper.checkStoredLength(rnd.toString(), RND_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rnd"), java.lang.String.valueOf(java.lang.Math.min(RND_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rnd", this.rnd, rnd));
   }

   /**
    * R&D
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String RND_EXPR = "rndExpr";
   static int RND_EXPR_UPPER_LIMIT = -1;
   java.lang.String rndExpr = "0";
   /**
    * R&D
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRndExpr() {
      return rndExpr;
   }
   /**
    * R&D
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRndExpr(java.lang.String rndExpr) throws wt.util.WTPropertyVetoException {
      rndExprValidate(rndExpr);
      this.rndExpr = rndExpr;
   }
   void rndExprValidate(java.lang.String rndExpr) throws wt.util.WTPropertyVetoException {
      if (RND_EXPR_UPPER_LIMIT < 1) {
         try { RND_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rndExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RND_EXPR_UPPER_LIMIT = 200; }
      }
      if (rndExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(rndExpr.toString(), RND_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rndExpr"), java.lang.String.valueOf(java.lang.Math.min(RND_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rndExpr", this.rndExpr, rndExpr));
   }

   /**
    * 스크랩판매비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SCRAP_SALES_COST_EXPR = "scrapSalesCostExpr";
   static int SCRAP_SALES_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String scrapSalesCostExpr = "0";
   /**
    * 스크랩판매비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getScrapSalesCostExpr() {
      return scrapSalesCostExpr;
   }
   /**
    * 스크랩판매비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setScrapSalesCostExpr(java.lang.String scrapSalesCostExpr) throws wt.util.WTPropertyVetoException {
      scrapSalesCostExprValidate(scrapSalesCostExpr);
      this.scrapSalesCostExpr = scrapSalesCostExpr;
   }
   void scrapSalesCostExprValidate(java.lang.String scrapSalesCostExpr) throws wt.util.WTPropertyVetoException {
      if (SCRAP_SALES_COST_EXPR_UPPER_LIMIT < 1) {
         try { SCRAP_SALES_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapSalesCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_SALES_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (scrapSalesCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapSalesCostExpr.toString(), SCRAP_SALES_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapSalesCostExpr"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_SALES_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapSalesCostExpr", this.scrapSalesCostExpr, scrapSalesCostExpr));
   }

   /**
    * 수지스크랩판매
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUJI_SCRAP_COST = "sujiScrapCost";
   static int SUJI_SCRAP_COST_UPPER_LIMIT = -1;
   java.lang.String sujiScrapCost = "0";
   /**
    * 수지스크랩판매
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSujiScrapCost() {
      return sujiScrapCost;
   }
   /**
    * 수지스크랩판매
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSujiScrapCost(java.lang.String sujiScrapCost) throws wt.util.WTPropertyVetoException {
      sujiScrapCostValidate(sujiScrapCost);
      this.sujiScrapCost = sujiScrapCost;
   }
   void sujiScrapCostValidate(java.lang.String sujiScrapCost) throws wt.util.WTPropertyVetoException {
      if (SUJI_SCRAP_COST_UPPER_LIMIT < 1) {
         try { SUJI_SCRAP_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiScrapCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_SCRAP_COST_UPPER_LIMIT = 200; }
      }
      if (sujiScrapCost != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiScrapCost.toString(), SUJI_SCRAP_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiScrapCost"), java.lang.String.valueOf(java.lang.Math.min(SUJI_SCRAP_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiScrapCost", this.sujiScrapCost, sujiScrapCost));
   }

   /**
    * 수지재생단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUJI_SCRAP_RECYCLE = "sujiScrapRecycle";
   static int SUJI_SCRAP_RECYCLE_UPPER_LIMIT = -1;
   java.lang.String sujiScrapRecycle = "0";
   /**
    * 수지재생단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSujiScrapRecycle() {
      return sujiScrapRecycle;
   }
   /**
    * 수지재생단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSujiScrapRecycle(java.lang.String sujiScrapRecycle) throws wt.util.WTPropertyVetoException {
      sujiScrapRecycleValidate(sujiScrapRecycle);
      this.sujiScrapRecycle = sujiScrapRecycle;
   }
   void sujiScrapRecycleValidate(java.lang.String sujiScrapRecycle) throws wt.util.WTPropertyVetoException {
      if (SUJI_SCRAP_RECYCLE_UPPER_LIMIT < 1) {
         try { SUJI_SCRAP_RECYCLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiScrapRecycle").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_SCRAP_RECYCLE_UPPER_LIMIT = 200; }
      }
      if (sujiScrapRecycle != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiScrapRecycle.toString(), SUJI_SCRAP_RECYCLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiScrapRecycle"), java.lang.String.valueOf(java.lang.Math.min(SUJI_SCRAP_RECYCLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiScrapRecycle", this.sujiScrapRecycle, sujiScrapRecycle));
   }

   /**
    * 타발유(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String TABARYU = "tabaryu";
   static int TABARYU_UPPER_LIMIT = -1;
   java.lang.String tabaryu = "0";
   /**
    * 타발유(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getTabaryu() {
      return tabaryu;
   }
   /**
    * 타발유(표준)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setTabaryu(java.lang.String tabaryu) throws wt.util.WTPropertyVetoException {
      tabaryuValidate(tabaryu);
      this.tabaryu = tabaryu;
   }
   void tabaryuValidate(java.lang.String tabaryu) throws wt.util.WTPropertyVetoException {
      if (TABARYU_UPPER_LIMIT < 1) {
         try { TABARYU_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tabaryu").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TABARYU_UPPER_LIMIT = 200; }
      }
      if (tabaryu != null && !wt.fc.PersistenceHelper.checkStoredLength(tabaryu.toString(), TABARYU_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tabaryu"), java.lang.String.valueOf(java.lang.Math.min(TABARYU_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tabaryu", this.tabaryu, tabaryu));
   }

   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String TABARYU_EXPR = "tabaryuExpr";
   static int TABARYU_EXPR_UPPER_LIMIT = -1;
   java.lang.String tabaryuExpr = "0";
   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getTabaryuExpr() {
      return tabaryuExpr;
   }
   /**
    * 타발유
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setTabaryuExpr(java.lang.String tabaryuExpr) throws wt.util.WTPropertyVetoException {
      tabaryuExprValidate(tabaryuExpr);
      this.tabaryuExpr = tabaryuExpr;
   }
   void tabaryuExprValidate(java.lang.String tabaryuExpr) throws wt.util.WTPropertyVetoException {
      if (TABARYU_EXPR_UPPER_LIMIT < 1) {
         try { TABARYU_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tabaryuExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TABARYU_EXPR_UPPER_LIMIT = 200; }
      }
      if (tabaryuExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(tabaryuExpr.toString(), TABARYU_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tabaryuExpr"), java.lang.String.valueOf(java.lang.Math.min(TABARYU_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tabaryuExpr", this.tabaryuExpr, tabaryuExpr));
   }

   /**
    * 관리대수
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String UNIT_MANAGE = "unitManage";
   static int UNIT_MANAGE_UPPER_LIMIT = -1;
   java.lang.String unitManage = "0";
   /**
    * 관리대수
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getUnitManage() {
      return unitManage;
   }
   /**
    * 관리대수
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setUnitManage(java.lang.String unitManage) throws wt.util.WTPropertyVetoException {
      unitManageValidate(unitManage);
      this.unitManage = unitManage;
   }
   void unitManageValidate(java.lang.String unitManage) throws wt.util.WTPropertyVetoException {
      if (UNIT_MANAGE_UPPER_LIMIT < 1) {
         try { UNIT_MANAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("unitManage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { UNIT_MANAGE_UPPER_LIMIT = 200; }
      }
      if (unitManage != null && !wt.fc.PersistenceHelper.checkStoredLength(unitManage.toString(), UNIT_MANAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "unitManage"), java.lang.String.valueOf(java.lang.Math.min(UNIT_MANAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "unitManage", this.unitManage, unitManage));
   }

   /**
    * 비철-비중
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_IMPORTANCE = "metalImportance";
   static int METAL_IMPORTANCE_UPPER_LIMIT = -1;
   java.lang.String metalImportance = "0";
   /**
    * 비철-비중
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalImportance() {
      return metalImportance;
   }
   /**
    * 비철-비중
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalImportance(java.lang.String metalImportance) throws wt.util.WTPropertyVetoException {
      metalImportanceValidate(metalImportance);
      this.metalImportance = metalImportance;
   }
   void metalImportanceValidate(java.lang.String metalImportance) throws wt.util.WTPropertyVetoException {
      if (METAL_IMPORTANCE_UPPER_LIMIT < 1) {
         try { METAL_IMPORTANCE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalImportance").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_IMPORTANCE_UPPER_LIMIT = 200; }
      }
      if (metalImportance != null && !wt.fc.PersistenceHelper.checkStoredLength(metalImportance.toString(), METAL_IMPORTANCE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalImportance"), java.lang.String.valueOf(java.lang.Math.min(METAL_IMPORTANCE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalImportance", this.metalImportance, metalImportance));
   }

   /**
    * 비철-시나리오
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_SCENARIO = "metalScenario";
   static int METAL_SCENARIO_UPPER_LIMIT = -1;
   java.lang.String metalScenario;
   /**
    * 비철-시나리오
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalScenario() {
      return metalScenario;
   }
   /**
    * 비철-시나리오
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalScenario(java.lang.String metalScenario) throws wt.util.WTPropertyVetoException {
      metalScenarioValidate(metalScenario);
      this.metalScenario = metalScenario;
   }
   void metalScenarioValidate(java.lang.String metalScenario) throws wt.util.WTPropertyVetoException {
      if (METAL_SCENARIO_UPPER_LIMIT < 1) {
         try { METAL_SCENARIO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalScenario").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_SCENARIO_UPPER_LIMIT = 200; }
      }
      if (metalScenario != null && !wt.fc.PersistenceHelper.checkStoredLength(metalScenario.toString(), METAL_SCENARIO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalScenario"), java.lang.String.valueOf(java.lang.Math.min(METAL_SCENARIO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalScenario", this.metalScenario, metalScenario));
   }

   /**
    * 비철-LME기준
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_LME_STD = "metalLmeStd";
   static int METAL_LME_STD_UPPER_LIMIT = -1;
   java.lang.String metalLmeStd = "0";
   /**
    * 비철-LME기준
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalLmeStd() {
      return metalLmeStd;
   }
   /**
    * 비철-LME기준
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalLmeStd(java.lang.String metalLmeStd) throws wt.util.WTPropertyVetoException {
      metalLmeStdValidate(metalLmeStd);
      this.metalLmeStd = metalLmeStd;
   }
   void metalLmeStdValidate(java.lang.String metalLmeStd) throws wt.util.WTPropertyVetoException {
      if (METAL_LME_STD_UPPER_LIMIT < 1) {
         try { METAL_LME_STD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalLmeStd").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_LME_STD_UPPER_LIMIT = 200; }
      }
      if (metalLmeStd != null && !wt.fc.PersistenceHelper.checkStoredLength(metalLmeStd.toString(), METAL_LME_STD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalLmeStd"), java.lang.String.valueOf(java.lang.Math.min(METAL_LME_STD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalLmeStd", this.metalLmeStd, metalLmeStd));
   }

   /**
    * 비철-LME금액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_LME_COST = "metalLmeCost";
   static int METAL_LME_COST_UPPER_LIMIT = -1;
   java.lang.String metalLmeCost = "0";
   /**
    * 비철-LME금액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalLmeCost() {
      return metalLmeCost;
   }
   /**
    * 비철-LME금액
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalLmeCost(java.lang.String metalLmeCost) throws wt.util.WTPropertyVetoException {
      metalLmeCostValidate(metalLmeCost);
      this.metalLmeCost = metalLmeCost;
   }
   void metalLmeCostValidate(java.lang.String metalLmeCost) throws wt.util.WTPropertyVetoException {
      if (METAL_LME_COST_UPPER_LIMIT < 1) {
         try { METAL_LME_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalLmeCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_LME_COST_UPPER_LIMIT = 200; }
      }
      if (metalLmeCost != null && !wt.fc.PersistenceHelper.checkStoredLength(metalLmeCost.toString(), METAL_LME_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalLmeCost"), java.lang.String.valueOf(java.lang.Math.min(METAL_LME_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalLmeCost", this.metalLmeCost, metalLmeCost));
   }

   /**
    * 비철-도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_PLATE_COST = "metalPlateCost";
   static int METAL_PLATE_COST_UPPER_LIMIT = -1;
   java.lang.String metalPlateCost = "0";
   /**
    * 비철-도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalPlateCost() {
      return metalPlateCost;
   }
   /**
    * 비철-도금단가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalPlateCost(java.lang.String metalPlateCost) throws wt.util.WTPropertyVetoException {
      metalPlateCostValidate(metalPlateCost);
      this.metalPlateCost = metalPlateCost;
   }
   void metalPlateCostValidate(java.lang.String metalPlateCost) throws wt.util.WTPropertyVetoException {
      if (METAL_PLATE_COST_UPPER_LIMIT < 1) {
         try { METAL_PLATE_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalPlateCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_PLATE_COST_UPPER_LIMIT = 200; }
      }
      if (metalPlateCost != null && !wt.fc.PersistenceHelper.checkStoredLength(metalPlateCost.toString(), METAL_PLATE_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalPlateCost"), java.lang.String.valueOf(java.lang.Math.min(METAL_PLATE_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalPlateCost", this.metalPlateCost, metalPlateCost));
   }

   /**
    * 비철-절단비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_CUTTING_COST = "metalCuttingCost";
   static int METAL_CUTTING_COST_UPPER_LIMIT = -1;
   java.lang.String metalCuttingCost = "0";
   /**
    * 비철-절단비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalCuttingCost() {
      return metalCuttingCost;
   }
   /**
    * 비철-절단비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalCuttingCost(java.lang.String metalCuttingCost) throws wt.util.WTPropertyVetoException {
      metalCuttingCostValidate(metalCuttingCost);
      this.metalCuttingCost = metalCuttingCost;
   }
   void metalCuttingCostValidate(java.lang.String metalCuttingCost) throws wt.util.WTPropertyVetoException {
      if (METAL_CUTTING_COST_UPPER_LIMIT < 1) {
         try { METAL_CUTTING_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalCuttingCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_CUTTING_COST_UPPER_LIMIT = 200; }
      }
      if (metalCuttingCost != null && !wt.fc.PersistenceHelper.checkStoredLength(metalCuttingCost.toString(), METAL_CUTTING_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalCuttingCost"), java.lang.String.valueOf(java.lang.Math.min(METAL_CUTTING_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalCuttingCost", this.metalCuttingCost, metalCuttingCost));
   }

   /**
    * 비철-DeTin
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_DE_TIN_COST = "metalDeTinCost";
   static int METAL_DE_TIN_COST_UPPER_LIMIT = -1;
   java.lang.String metalDeTinCost = "0";
   /**
    * 비철-DeTin
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalDeTinCost() {
      return metalDeTinCost;
   }
   /**
    * 비철-DeTin
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalDeTinCost(java.lang.String metalDeTinCost) throws wt.util.WTPropertyVetoException {
      metalDeTinCostValidate(metalDeTinCost);
      this.metalDeTinCost = metalDeTinCost;
   }
   void metalDeTinCostValidate(java.lang.String metalDeTinCost) throws wt.util.WTPropertyVetoException {
      if (METAL_DE_TIN_COST_UPPER_LIMIT < 1) {
         try { METAL_DE_TIN_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalDeTinCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_DE_TIN_COST_UPPER_LIMIT = 200; }
      }
      if (metalDeTinCost != null && !wt.fc.PersistenceHelper.checkStoredLength(metalDeTinCost.toString(), METAL_DE_TIN_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalDeTinCost"), java.lang.String.valueOf(java.lang.Math.min(METAL_DE_TIN_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalDeTinCost", this.metalDeTinCost, metalDeTinCost));
   }

   /**
    * 비철-임가공비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_TOLL_PRC_COST = "metalTollPrcCost";
   static int METAL_TOLL_PRC_COST_UPPER_LIMIT = -1;
   java.lang.String metalTollPrcCost = "0";
   /**
    * 비철-임가공비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalTollPrcCost() {
      return metalTollPrcCost;
   }
   /**
    * 비철-임가공비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalTollPrcCost(java.lang.String metalTollPrcCost) throws wt.util.WTPropertyVetoException {
      metalTollPrcCostValidate(metalTollPrcCost);
      this.metalTollPrcCost = metalTollPrcCost;
   }
   void metalTollPrcCostValidate(java.lang.String metalTollPrcCost) throws wt.util.WTPropertyVetoException {
      if (METAL_TOLL_PRC_COST_UPPER_LIMIT < 1) {
         try { METAL_TOLL_PRC_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalTollPrcCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_TOLL_PRC_COST_UPPER_LIMIT = 200; }
      }
      if (metalTollPrcCost != null && !wt.fc.PersistenceHelper.checkStoredLength(metalTollPrcCost.toString(), METAL_TOLL_PRC_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalTollPrcCost"), java.lang.String.valueOf(java.lang.Math.min(METAL_TOLL_PRC_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalTollPrcCost", this.metalTollPrcCost, metalTollPrcCost));
   }

   /**
    * 비철-원자재번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String METAL_PART_NO = "metalPartNo";
   static int METAL_PART_NO_UPPER_LIMIT = -1;
   java.lang.String metalPartNo;
   /**
    * 비철-원자재번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMetalPartNo() {
      return metalPartNo;
   }
   /**
    * 비철-원자재번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMetalPartNo(java.lang.String metalPartNo) throws wt.util.WTPropertyVetoException {
      metalPartNoValidate(metalPartNo);
      this.metalPartNo = metalPartNo;
   }
   void metalPartNoValidate(java.lang.String metalPartNo) throws wt.util.WTPropertyVetoException {
      if (METAL_PART_NO_UPPER_LIMIT < 1) {
         try { METAL_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("metalPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { METAL_PART_NO_UPPER_LIMIT = 200; }
      }
      if (metalPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(metalPartNo.toString(), METAL_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "metalPartNo"), java.lang.String.valueOf(java.lang.Math.min(METAL_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "metalPartNo", this.metalPartNo, metalPartNo));
   }

   /**
    * 수지-원자재번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUJI_PART_NO = "sujiPartNo";
   static int SUJI_PART_NO_UPPER_LIMIT = -1;
   java.lang.String sujiPartNo;
   /**
    * 수지-원자재번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSujiPartNo() {
      return sujiPartNo;
   }
   /**
    * 수지-원자재번호
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSujiPartNo(java.lang.String sujiPartNo) throws wt.util.WTPropertyVetoException {
      sujiPartNoValidate(sujiPartNo);
      this.sujiPartNo = sujiPartNo;
   }
   void sujiPartNoValidate(java.lang.String sujiPartNo) throws wt.util.WTPropertyVetoException {
      if (SUJI_PART_NO_UPPER_LIMIT < 1) {
         try { SUJI_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_PART_NO_UPPER_LIMIT = 200; }
      }
      if (sujiPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiPartNo.toString(), SUJI_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiPartNo"), java.lang.String.valueOf(java.lang.Math.min(SUJI_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiPartNo", this.sujiPartNo, sujiPartNo));
   }

   /**
    * 수지-원자재명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUJI_PART_NAME = "sujiPartName";
   static int SUJI_PART_NAME_UPPER_LIMIT = -1;
   java.lang.String sujiPartName;
   /**
    * 수지-원자재명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSujiPartName() {
      return sujiPartName;
   }
   /**
    * 수지-원자재명
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSujiPartName(java.lang.String sujiPartName) throws wt.util.WTPropertyVetoException {
      sujiPartNameValidate(sujiPartName);
      this.sujiPartName = sujiPartName;
   }
   void sujiPartNameValidate(java.lang.String sujiPartName) throws wt.util.WTPropertyVetoException {
      if (SUJI_PART_NAME_UPPER_LIMIT < 1) {
         try { SUJI_PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiPartName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_PART_NAME_UPPER_LIMIT = 200; }
      }
      if (sujiPartName != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiPartName.toString(), SUJI_PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiPartName"), java.lang.String.valueOf(java.lang.Math.min(SUJI_PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiPartName", this.sujiPartName, sujiPartName));
   }

   /**
    * 수지-Grade
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUJI_GRADE = "sujiGrade";
   static int SUJI_GRADE_UPPER_LIMIT = -1;
   java.lang.String sujiGrade;
   /**
    * 수지-Grade
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSujiGrade() {
      return sujiGrade;
   }
   /**
    * 수지-Grade
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSujiGrade(java.lang.String sujiGrade) throws wt.util.WTPropertyVetoException {
      sujiGradeValidate(sujiGrade);
      this.sujiGrade = sujiGrade;
   }
   void sujiGradeValidate(java.lang.String sujiGrade) throws wt.util.WTPropertyVetoException {
      if (SUJI_GRADE_UPPER_LIMIT < 1) {
         try { SUJI_GRADE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiGrade").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_GRADE_UPPER_LIMIT = 200; }
      }
      if (sujiGrade != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiGrade.toString(), SUJI_GRADE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiGrade"), java.lang.String.valueOf(java.lang.Math.min(SUJI_GRADE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiGrade", this.sujiGrade, sujiGrade));
   }

   /**
    * 수지-Color
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUJI_COLOR = "sujiColor";
   static int SUJI_COLOR_UPPER_LIMIT = -1;
   java.lang.String sujiColor;
   /**
    * 수지-Color
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSujiColor() {
      return sujiColor;
   }
   /**
    * 수지-Color
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSujiColor(java.lang.String sujiColor) throws wt.util.WTPropertyVetoException {
      sujiColorValidate(sujiColor);
      this.sujiColor = sujiColor;
   }
   void sujiColorValidate(java.lang.String sujiColor) throws wt.util.WTPropertyVetoException {
      if (SUJI_COLOR_UPPER_LIMIT < 1) {
         try { SUJI_COLOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sujiColor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUJI_COLOR_UPPER_LIMIT = 200; }
      }
      if (sujiColor != null && !wt.fc.PersistenceHelper.checkStoredLength(sujiColor.toString(), SUJI_COLOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sujiColor"), java.lang.String.valueOf(java.lang.Math.min(SUJI_COLOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sujiColor", this.sujiColor, sujiColor));
   }

   /**
    * 후도금단가 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String AP_MONETARY_UNIT_CURRENCY = "apMonetaryUnitCurrency";
   static int AP_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String apMonetaryUnitCurrency = "0";
   /**
    * 후도금단가 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getApMonetaryUnitCurrency() {
      return apMonetaryUnitCurrency;
   }
   /**
    * 후도금단가 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setApMonetaryUnitCurrency(java.lang.String apMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      apMonetaryUnitCurrencyValidate(apMonetaryUnitCurrency);
      this.apMonetaryUnitCurrency = apMonetaryUnitCurrency;
   }
   void apMonetaryUnitCurrencyValidate(java.lang.String apMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (AP_MONETARY_UNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { AP_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apMonetaryUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (apMonetaryUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(apMonetaryUnitCurrency.toString(), AP_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apMonetaryUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(AP_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apMonetaryUnitCurrency", this.apMonetaryUnitCurrency, apMonetaryUnitCurrency));
   }

   /**
    * 외주단가 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String OUT_MONETARY_UNIT_CURRENCY = "outMonetaryUnitCurrency";
   static int OUT_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String outMonetaryUnitCurrency = "0";
   /**
    * 외주단가 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getOutMonetaryUnitCurrency() {
      return outMonetaryUnitCurrency;
   }
   /**
    * 외주단가 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setOutMonetaryUnitCurrency(java.lang.String outMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      outMonetaryUnitCurrencyValidate(outMonetaryUnitCurrency);
      this.outMonetaryUnitCurrency = outMonetaryUnitCurrency;
   }
   void outMonetaryUnitCurrencyValidate(java.lang.String outMonetaryUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (OUT_MONETARY_UNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { OUT_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("outMonetaryUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OUT_MONETARY_UNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (outMonetaryUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(outMonetaryUnitCurrency.toString(), OUT_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outMonetaryUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(OUT_MONETARY_UNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "outMonetaryUnitCurrency", this.outMonetaryUnitCurrency, outMonetaryUnitCurrency));
   }

   /**
    * 금형투자비(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_NICMUNIT_CURRENCY = "moldNICMUnitCurrency";
   static int MOLD_NICMUNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String moldNICMUnitCurrency = "0";
   /**
    * 금형투자비(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldNICMUnitCurrency() {
      return moldNICMUnitCurrency;
   }
   /**
    * 금형투자비(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldNICMUnitCurrency(java.lang.String moldNICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      moldNICMUnitCurrencyValidate(moldNICMUnitCurrency);
      this.moldNICMUnitCurrency = moldNICMUnitCurrency;
   }
   void moldNICMUnitCurrencyValidate(java.lang.String moldNICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (MOLD_NICMUNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { MOLD_NICMUNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNICMUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NICMUNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (moldNICMUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNICMUnitCurrency.toString(), MOLD_NICMUNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNICMUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NICMUNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNICMUnitCurrency", this.moldNICMUnitCurrency, moldNICMUnitCurrency));
   }

   /**
    * 금형지급액(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_NPAY_MUNIT_CURRENCY = "moldNPayMUnitCurrency";
   static int MOLD_NPAY_MUNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String moldNPayMUnitCurrency = "0";
   /**
    * 금형지급액(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldNPayMUnitCurrency() {
      return moldNPayMUnitCurrency;
   }
   /**
    * 금형지급액(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldNPayMUnitCurrency(java.lang.String moldNPayMUnitCurrency) throws wt.util.WTPropertyVetoException {
      moldNPayMUnitCurrencyValidate(moldNPayMUnitCurrency);
      this.moldNPayMUnitCurrency = moldNPayMUnitCurrency;
   }
   void moldNPayMUnitCurrencyValidate(java.lang.String moldNPayMUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (MOLD_NPAY_MUNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { MOLD_NPAY_MUNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldNPayMUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_NPAY_MUNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (moldNPayMUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(moldNPayMUnitCurrency.toString(), MOLD_NPAY_MUNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldNPayMUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(MOLD_NPAY_MUNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldNPayMUnitCurrency", this.moldNPayMUnitCurrency, moldNPayMUnitCurrency));
   }

   /**
    * 금형투자비(양산) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_MPICMUNIT_CURRENCY = "moldMPICMUnitCurrency";
   static int MOLD_MPICMUNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String moldMPICMUnitCurrency = "0";
   /**
    * 금형투자비(양산) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldMPICMUnitCurrency() {
      return moldMPICMUnitCurrency;
   }
   /**
    * 금형투자비(양산) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldMPICMUnitCurrency(java.lang.String moldMPICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      moldMPICMUnitCurrencyValidate(moldMPICMUnitCurrency);
      this.moldMPICMUnitCurrency = moldMPICMUnitCurrency;
   }
   void moldMPICMUnitCurrencyValidate(java.lang.String moldMPICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (MOLD_MPICMUNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { MOLD_MPICMUNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldMPICMUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_MPICMUNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (moldMPICMUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(moldMPICMUnitCurrency.toString(), MOLD_MPICMUNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMPICMUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(MOLD_MPICMUNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldMPICMUnitCurrency", this.moldMPICMUnitCurrency, moldMPICMUnitCurrency));
   }

   /**
    * 설비투자비(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_NICMUNIT_CURRENCY = "facNICMUnitCurrency";
   static int FAC_NICMUNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String facNICMUnitCurrency = "0";
   /**
    * 설비투자비(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacNICMUnitCurrency() {
      return facNICMUnitCurrency;
   }
   /**
    * 설비투자비(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacNICMUnitCurrency(java.lang.String facNICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      facNICMUnitCurrencyValidate(facNICMUnitCurrency);
      this.facNICMUnitCurrency = facNICMUnitCurrency;
   }
   void facNICMUnitCurrencyValidate(java.lang.String facNICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (FAC_NICMUNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { FAC_NICMUNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facNICMUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_NICMUNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (facNICMUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(facNICMUnitCurrency.toString(), FAC_NICMUNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facNICMUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(FAC_NICMUNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facNICMUnitCurrency", this.facNICMUnitCurrency, facNICMUnitCurrency));
   }

   /**
    * 설비지급액(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_NPAY_MUNIT_CURRENCY = "facNPayMUnitCurrency";
   static int FAC_NPAY_MUNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String facNPayMUnitCurrency = "0";
   /**
    * 설비지급액(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacNPayMUnitCurrency() {
      return facNPayMUnitCurrency;
   }
   /**
    * 설비지급액(신규) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacNPayMUnitCurrency(java.lang.String facNPayMUnitCurrency) throws wt.util.WTPropertyVetoException {
      facNPayMUnitCurrencyValidate(facNPayMUnitCurrency);
      this.facNPayMUnitCurrency = facNPayMUnitCurrency;
   }
   void facNPayMUnitCurrencyValidate(java.lang.String facNPayMUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (FAC_NPAY_MUNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { FAC_NPAY_MUNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facNPayMUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_NPAY_MUNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (facNPayMUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(facNPayMUnitCurrency.toString(), FAC_NPAY_MUNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facNPayMUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(FAC_NPAY_MUNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facNPayMUnitCurrency", this.facNPayMUnitCurrency, facNPayMUnitCurrency));
   }

   /**
    * 설비투자비(양산) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_MPICMUNIT_CURRENCY = "facMPICMUnitCurrency";
   static int FAC_MPICMUNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String facMPICMUnitCurrency = "0";
   /**
    * 설비투자비(양산) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacMPICMUnitCurrency() {
      return facMPICMUnitCurrency;
   }
   /**
    * 설비투자비(양산) 환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacMPICMUnitCurrency(java.lang.String facMPICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      facMPICMUnitCurrencyValidate(facMPICMUnitCurrency);
      this.facMPICMUnitCurrency = facMPICMUnitCurrency;
   }
   void facMPICMUnitCurrencyValidate(java.lang.String facMPICMUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (FAC_MPICMUNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { FAC_MPICMUNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facMPICMUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_MPICMUNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (facMPICMUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(facMPICMUnitCurrency.toString(), FAC_MPICMUNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facMPICMUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(FAC_MPICMUNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facMPICMUnitCurrency", this.facMPICMUnitCurrency, facMPICMUnitCurrency));
   }

   /**
    * 선도금단가환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRE_PLATING_UNIT_CURRENCY = "prePlatingUnitCurrency";
   static int PRE_PLATING_UNIT_CURRENCY_UPPER_LIMIT = -1;
   java.lang.String prePlatingUnitCurrency = "0";
   /**
    * 선도금단가환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPrePlatingUnitCurrency() {
      return prePlatingUnitCurrency;
   }
   /**
    * 선도금단가환율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPrePlatingUnitCurrency(java.lang.String prePlatingUnitCurrency) throws wt.util.WTPropertyVetoException {
      prePlatingUnitCurrencyValidate(prePlatingUnitCurrency);
      this.prePlatingUnitCurrency = prePlatingUnitCurrency;
   }
   void prePlatingUnitCurrencyValidate(java.lang.String prePlatingUnitCurrency) throws wt.util.WTPropertyVetoException {
      if (PRE_PLATING_UNIT_CURRENCY_UPPER_LIMIT < 1) {
         try { PRE_PLATING_UNIT_CURRENCY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("prePlatingUnitCurrency").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRE_PLATING_UNIT_CURRENCY_UPPER_LIMIT = 200; }
      }
      if (prePlatingUnitCurrency != null && !wt.fc.PersistenceHelper.checkStoredLength(prePlatingUnitCurrency.toString(), PRE_PLATING_UNIT_CURRENCY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "prePlatingUnitCurrency"), java.lang.String.valueOf(java.lang.Math.min(PRE_PLATING_UNIT_CURRENCY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "prePlatingUnitCurrency", this.prePlatingUnitCurrency, prePlatingUnitCurrency));
   }

   /**
    * 산출기준(재료비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_STD_MATERIAL = "calcStdMaterial";
   static int CALC_STD_MATERIAL_UPPER_LIMIT = -1;
   java.lang.String calcStdMaterial;
   /**
    * 산출기준(재료비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcStdMaterial() {
      return calcStdMaterial;
   }
   /**
    * 산출기준(재료비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcStdMaterial(java.lang.String calcStdMaterial) throws wt.util.WTPropertyVetoException {
      calcStdMaterialValidate(calcStdMaterial);
      this.calcStdMaterial = calcStdMaterial;
   }
   void calcStdMaterialValidate(java.lang.String calcStdMaterial) throws wt.util.WTPropertyVetoException {
      if (CALC_STD_MATERIAL_UPPER_LIMIT < 1) {
         try { CALC_STD_MATERIAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcStdMaterial").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_STD_MATERIAL_UPPER_LIMIT = 200; }
      }
      if (calcStdMaterial != null && !wt.fc.PersistenceHelper.checkStoredLength(calcStdMaterial.toString(), CALC_STD_MATERIAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcStdMaterial"), java.lang.String.valueOf(java.lang.Math.min(CALC_STD_MATERIAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcStdMaterial", this.calcStdMaterial, calcStdMaterial));
   }

   /**
    * 산출기준(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_STD_LABOR = "calcStdLabor";
   static int CALC_STD_LABOR_UPPER_LIMIT = -1;
   java.lang.String calcStdLabor;
   /**
    * 산출기준(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcStdLabor() {
      return calcStdLabor;
   }
   /**
    * 산출기준(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcStdLabor(java.lang.String calcStdLabor) throws wt.util.WTPropertyVetoException {
      calcStdLaborValidate(calcStdLabor);
      this.calcStdLabor = calcStdLabor;
   }
   void calcStdLaborValidate(java.lang.String calcStdLabor) throws wt.util.WTPropertyVetoException {
      if (CALC_STD_LABOR_UPPER_LIMIT < 1) {
         try { CALC_STD_LABOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcStdLabor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_STD_LABOR_UPPER_LIMIT = 200; }
      }
      if (calcStdLabor != null && !wt.fc.PersistenceHelper.checkStoredLength(calcStdLabor.toString(), CALC_STD_LABOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcStdLabor"), java.lang.String.valueOf(java.lang.Math.min(CALC_STD_LABOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcStdLabor", this.calcStdLabor, calcStdLabor));
   }

   /**
    * 산출기준(경비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_STD_EXPENSE = "calcStdExpense";
   static int CALC_STD_EXPENSE_UPPER_LIMIT = -1;
   java.lang.String calcStdExpense;
   /**
    * 산출기준(경비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcStdExpense() {
      return calcStdExpense;
   }
   /**
    * 산출기준(경비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcStdExpense(java.lang.String calcStdExpense) throws wt.util.WTPropertyVetoException {
      calcStdExpenseValidate(calcStdExpense);
      this.calcStdExpense = calcStdExpense;
   }
   void calcStdExpenseValidate(java.lang.String calcStdExpense) throws wt.util.WTPropertyVetoException {
      if (CALC_STD_EXPENSE_UPPER_LIMIT < 1) {
         try { CALC_STD_EXPENSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcStdExpense").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_STD_EXPENSE_UPPER_LIMIT = 200; }
      }
      if (calcStdExpense != null && !wt.fc.PersistenceHelper.checkStoredLength(calcStdExpense.toString(), CALC_STD_EXPENSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcStdExpense"), java.lang.String.valueOf(java.lang.Math.min(CALC_STD_EXPENSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcStdExpense", this.calcStdExpense, calcStdExpense));
   }

   /**
    * 산출기준(관리비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_STD_MANAGE = "calcStdManage";
   static int CALC_STD_MANAGE_UPPER_LIMIT = -1;
   java.lang.String calcStdManage;
   /**
    * 산출기준(관리비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcStdManage() {
      return calcStdManage;
   }
   /**
    * 산출기준(관리비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcStdManage(java.lang.String calcStdManage) throws wt.util.WTPropertyVetoException {
      calcStdManageValidate(calcStdManage);
      this.calcStdManage = calcStdManage;
   }
   void calcStdManageValidate(java.lang.String calcStdManage) throws wt.util.WTPropertyVetoException {
      if (CALC_STD_MANAGE_UPPER_LIMIT < 1) {
         try { CALC_STD_MANAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcStdManage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_STD_MANAGE_UPPER_LIMIT = 200; }
      }
      if (calcStdManage != null && !wt.fc.PersistenceHelper.checkStoredLength(calcStdManage.toString(), CALC_STD_MANAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcStdManage"), java.lang.String.valueOf(java.lang.Math.min(CALC_STD_MANAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcStdManage", this.calcStdManage, calcStdManage));
   }

   /**
    * 산출기준(생산량)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_STD_OUT_PUT = "calcStdOutPut";
   static int CALC_STD_OUT_PUT_UPPER_LIMIT = -1;
   java.lang.String calcStdOutPut;
   /**
    * 산출기준(생산량)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcStdOutPut() {
      return calcStdOutPut;
   }
   /**
    * 산출기준(생산량)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcStdOutPut(java.lang.String calcStdOutPut) throws wt.util.WTPropertyVetoException {
      calcStdOutPutValidate(calcStdOutPut);
      this.calcStdOutPut = calcStdOutPut;
   }
   void calcStdOutPutValidate(java.lang.String calcStdOutPut) throws wt.util.WTPropertyVetoException {
      if (CALC_STD_OUT_PUT_UPPER_LIMIT < 1) {
         try { CALC_STD_OUT_PUT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcStdOutPut").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_STD_OUT_PUT_UPPER_LIMIT = 200; }
      }
      if (calcStdOutPut != null && !wt.fc.PersistenceHelper.checkStoredLength(calcStdOutPut.toString(), CALC_STD_OUT_PUT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcStdOutPut"), java.lang.String.valueOf(java.lang.Math.min(CALC_STD_OUT_PUT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcStdOutPut", this.calcStdOutPut, calcStdOutPut));
   }

   /**
    * 산출옵션(재료비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_OPTION_MATERIAL = "calcOptionMaterial";
   static int CALC_OPTION_MATERIAL_UPPER_LIMIT = -1;
   java.lang.String calcOptionMaterial;
   /**
    * 산출옵션(재료비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcOptionMaterial() {
      return calcOptionMaterial;
   }
   /**
    * 산출옵션(재료비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcOptionMaterial(java.lang.String calcOptionMaterial) throws wt.util.WTPropertyVetoException {
      calcOptionMaterialValidate(calcOptionMaterial);
      this.calcOptionMaterial = calcOptionMaterial;
   }
   void calcOptionMaterialValidate(java.lang.String calcOptionMaterial) throws wt.util.WTPropertyVetoException {
      if (CALC_OPTION_MATERIAL_UPPER_LIMIT < 1) {
         try { CALC_OPTION_MATERIAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcOptionMaterial").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_OPTION_MATERIAL_UPPER_LIMIT = 200; }
      }
      if (calcOptionMaterial != null && !wt.fc.PersistenceHelper.checkStoredLength(calcOptionMaterial.toString(), CALC_OPTION_MATERIAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcOptionMaterial"), java.lang.String.valueOf(java.lang.Math.min(CALC_OPTION_MATERIAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcOptionMaterial", this.calcOptionMaterial, calcOptionMaterial));
   }

   /**
    * 산출옵션(관리비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_OPTION_MANAGE = "calcOptionManage";
   static int CALC_OPTION_MANAGE_UPPER_LIMIT = -1;
   java.lang.String calcOptionManage;
   /**
    * 산출옵션(관리비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcOptionManage() {
      return calcOptionManage;
   }
   /**
    * 산출옵션(관리비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcOptionManage(java.lang.String calcOptionManage) throws wt.util.WTPropertyVetoException {
      calcOptionManageValidate(calcOptionManage);
      this.calcOptionManage = calcOptionManage;
   }
   void calcOptionManageValidate(java.lang.String calcOptionManage) throws wt.util.WTPropertyVetoException {
      if (CALC_OPTION_MANAGE_UPPER_LIMIT < 1) {
         try { CALC_OPTION_MANAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcOptionManage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_OPTION_MANAGE_UPPER_LIMIT = 200; }
      }
      if (calcOptionManage != null && !wt.fc.PersistenceHelper.checkStoredLength(calcOptionManage.toString(), CALC_OPTION_MANAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcOptionManage"), java.lang.String.valueOf(java.lang.Math.min(CALC_OPTION_MANAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcOptionManage", this.calcOptionManage, calcOptionManage));
   }

   /**
    * 산출옵션(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CALC_OPTION_LABOR = "calcOptionLabor";
   static int CALC_OPTION_LABOR_UPPER_LIMIT = -1;
   java.lang.String calcOptionLabor;
   /**
    * 산출옵션(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCalcOptionLabor() {
      return calcOptionLabor;
   }
   /**
    * 산출옵션(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCalcOptionLabor(java.lang.String calcOptionLabor) throws wt.util.WTPropertyVetoException {
      calcOptionLaborValidate(calcOptionLabor);
      this.calcOptionLabor = calcOptionLabor;
   }
   void calcOptionLaborValidate(java.lang.String calcOptionLabor) throws wt.util.WTPropertyVetoException {
      if (CALC_OPTION_LABOR_UPPER_LIMIT < 1) {
         try { CALC_OPTION_LABOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("calcOptionLabor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CALC_OPTION_LABOR_UPPER_LIMIT = 200; }
      }
      if (calcOptionLabor != null && !wt.fc.PersistenceHelper.checkStoredLength(calcOptionLabor.toString(), CALC_OPTION_LABOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "calcOptionLabor"), java.lang.String.valueOf(java.lang.Math.min(CALC_OPTION_LABOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "calcOptionLabor", this.calcOptionLabor, calcOptionLabor));
   }

   /**
    * 포장수량(Box)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PACK_BOX_UNIT = "packBoxUnit";
   static int PACK_BOX_UNIT_UPPER_LIMIT = -1;
   java.lang.String packBoxUnit = "0";
   /**
    * 포장수량(Box)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPackBoxUnit() {
      return packBoxUnit;
   }
   /**
    * 포장수량(Box)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPackBoxUnit(java.lang.String packBoxUnit) throws wt.util.WTPropertyVetoException {
      packBoxUnitValidate(packBoxUnit);
      this.packBoxUnit = packBoxUnit;
   }
   void packBoxUnitValidate(java.lang.String packBoxUnit) throws wt.util.WTPropertyVetoException {
      if (PACK_BOX_UNIT_UPPER_LIMIT < 1) {
         try { PACK_BOX_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packBoxUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_BOX_UNIT_UPPER_LIMIT = 200; }
      }
      if (packBoxUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(packBoxUnit.toString(), PACK_BOX_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packBoxUnit"), java.lang.String.valueOf(java.lang.Math.min(PACK_BOX_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packBoxUnit", this.packBoxUnit, packBoxUnit));
   }

   /**
    * 포장수량(pallet)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PACK_PALLET_UNIT = "packPalletUnit";
   static int PACK_PALLET_UNIT_UPPER_LIMIT = -1;
   java.lang.String packPalletUnit = "0";
   /**
    * 포장수량(pallet)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPackPalletUnit() {
      return packPalletUnit;
   }
   /**
    * 포장수량(pallet)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPackPalletUnit(java.lang.String packPalletUnit) throws wt.util.WTPropertyVetoException {
      packPalletUnitValidate(packPalletUnit);
      this.packPalletUnit = packPalletUnit;
   }
   void packPalletUnitValidate(java.lang.String packPalletUnit) throws wt.util.WTPropertyVetoException {
      if (PACK_PALLET_UNIT_UPPER_LIMIT < 1) {
         try { PACK_PALLET_UNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packPalletUnit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_PALLET_UNIT_UPPER_LIMIT = 200; }
      }
      if (packPalletUnit != null && !wt.fc.PersistenceHelper.checkStoredLength(packPalletUnit.toString(), PACK_PALLET_UNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packPalletUnit"), java.lang.String.valueOf(java.lang.Math.min(PACK_PALLET_UNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packPalletUnit", this.packPalletUnit, packPalletUnit));
   }

   /**
    * 개별포장비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PACK_UNIT_PRICE_SUM = "packUnitPriceSum";
   static int PACK_UNIT_PRICE_SUM_UPPER_LIMIT = -1;
   java.lang.String packUnitPriceSum = "0";
   /**
    * 개별포장비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPackUnitPriceSum() {
      return packUnitPriceSum;
   }
   /**
    * 개별포장비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPackUnitPriceSum(java.lang.String packUnitPriceSum) throws wt.util.WTPropertyVetoException {
      packUnitPriceSumValidate(packUnitPriceSum);
      this.packUnitPriceSum = packUnitPriceSum;
   }
   void packUnitPriceSumValidate(java.lang.String packUnitPriceSum) throws wt.util.WTPropertyVetoException {
      if (PACK_UNIT_PRICE_SUM_UPPER_LIMIT < 1) {
         try { PACK_UNIT_PRICE_SUM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packUnitPriceSum").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_UNIT_PRICE_SUM_UPPER_LIMIT = 200; }
      }
      if (packUnitPriceSum != null && !wt.fc.PersistenceHelper.checkStoredLength(packUnitPriceSum.toString(), PACK_UNIT_PRICE_SUM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packUnitPriceSum"), java.lang.String.valueOf(java.lang.Math.min(PACK_UNIT_PRICE_SUM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packUnitPriceSum", this.packUnitPriceSum, packUnitPriceSum));
   }

   /**
    * 예상수량합계(Total 수량)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String QUANTITY_TOTAL = "quantityTotal";
   static int QUANTITY_TOTAL_UPPER_LIMIT = -1;
   java.lang.String quantityTotal = "0";
   /**
    * 예상수량합계(Total 수량)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getQuantityTotal() {
      return quantityTotal;
   }
   /**
    * 예상수량합계(Total 수량)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setQuantityTotal(java.lang.String quantityTotal) throws wt.util.WTPropertyVetoException {
      quantityTotalValidate(quantityTotal);
      this.quantityTotal = quantityTotal;
   }
   void quantityTotalValidate(java.lang.String quantityTotal) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_TOTAL_UPPER_LIMIT < 1) {
         try { QUANTITY_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantityTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_TOTAL_UPPER_LIMIT = 200; }
      }
      if (quantityTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(quantityTotal.toString(), QUANTITY_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityTotal"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantityTotal", this.quantityTotal, quantityTotal));
   }

   /**
    * Avg 수량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String QUANTITY_AVG = "quantityAvg";
   static int QUANTITY_AVG_UPPER_LIMIT = -1;
   java.lang.String quantityAvg = "0";
   /**
    * Avg 수량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getQuantityAvg() {
      return quantityAvg;
   }
   /**
    * Avg 수량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setQuantityAvg(java.lang.String quantityAvg) throws wt.util.WTPropertyVetoException {
      quantityAvgValidate(quantityAvg);
      this.quantityAvg = quantityAvg;
   }
   void quantityAvgValidate(java.lang.String quantityAvg) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_AVG_UPPER_LIMIT < 1) {
         try { QUANTITY_AVG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantityAvg").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_AVG_UPPER_LIMIT = 200; }
      }
      if (quantityAvg != null && !wt.fc.PersistenceHelper.checkStoredLength(quantityAvg.toString(), QUANTITY_AVG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityAvg"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_AVG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantityAvg", this.quantityAvg, quantityAvg));
   }

   /**
    * Max 수량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String QUANTITY_MAX = "quantityMax";
   static int QUANTITY_MAX_UPPER_LIMIT = -1;
   java.lang.String quantityMax = "0";
   /**
    * Max 수량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getQuantityMax() {
      return quantityMax;
   }
   /**
    * Max 수량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setQuantityMax(java.lang.String quantityMax) throws wt.util.WTPropertyVetoException {
      quantityMaxValidate(quantityMax);
      this.quantityMax = quantityMax;
   }
   void quantityMaxValidate(java.lang.String quantityMax) throws wt.util.WTPropertyVetoException {
      if (QUANTITY_MAX_UPPER_LIMIT < 1) {
         try { QUANTITY_MAX_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("quantityMax").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { QUANTITY_MAX_UPPER_LIMIT = 200; }
      }
      if (quantityMax != null && !wt.fc.PersistenceHelper.checkStoredLength(quantityMax.toString(), QUANTITY_MAX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "quantityMax"), java.lang.String.valueOf(java.lang.Math.min(QUANTITY_MAX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "quantityMax", this.quantityMax, quantityMax));
   }

   /**
    * SOP년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SOP_YEAR = "sopYear";
   static int SOP_YEAR_UPPER_LIMIT = -1;
   java.lang.String sopYear;
   /**
    * SOP년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSopYear() {
      return sopYear;
   }
   /**
    * SOP년도
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSopYear(java.lang.String sopYear) throws wt.util.WTPropertyVetoException {
      sopYearValidate(sopYear);
      this.sopYear = sopYear;
   }
   void sopYearValidate(java.lang.String sopYear) throws wt.util.WTPropertyVetoException {
      if (SOP_YEAR_UPPER_LIMIT < 1) {
         try { SOP_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sopYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SOP_YEAR_UPPER_LIMIT = 200; }
      }
      if (sopYear != null && !wt.fc.PersistenceHelper.checkStoredLength(sopYear.toString(), SOP_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sopYear"), java.lang.String.valueOf(java.lang.Math.min(SOP_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sopYear", this.sopYear, sopYear));
   }

   /**
    * 완제품입고처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PAY_PLACE = "payPlace";
   static int PAY_PLACE_UPPER_LIMIT = -1;
   java.lang.String payPlace;
   /**
    * 완제품입고처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPayPlace() {
      return payPlace;
   }
   /**
    * 완제품입고처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPayPlace(java.lang.String payPlace) throws wt.util.WTPropertyVetoException {
      payPlaceValidate(payPlace);
      this.payPlace = payPlace;
   }
   void payPlaceValidate(java.lang.String payPlace) throws wt.util.WTPropertyVetoException {
      if (PAY_PLACE_UPPER_LIMIT < 1) {
         try { PAY_PLACE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("payPlace").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PAY_PLACE_UPPER_LIMIT = 200; }
      }
      if (payPlace != null && !wt.fc.PersistenceHelper.checkStoredLength(payPlace.toString(), PAY_PLACE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "payPlace"), java.lang.String.valueOf(java.lang.Math.min(PAY_PLACE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "payPlace", this.payPlace, payPlace));
   }

   /**
    * 총원가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String TOTAL_COST_EXPR = "totalCostExpr";
   static int TOTAL_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String totalCostExpr = "0";
   /**
    * 총원가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getTotalCostExpr() {
      return totalCostExpr;
   }
   /**
    * 총원가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setTotalCostExpr(java.lang.String totalCostExpr) throws wt.util.WTPropertyVetoException {
      totalCostExprValidate(totalCostExpr);
      this.totalCostExpr = totalCostExpr;
   }
   void totalCostExprValidate(java.lang.String totalCostExpr) throws wt.util.WTPropertyVetoException {
      if (TOTAL_COST_EXPR_UPPER_LIMIT < 1) {
         try { TOTAL_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("totalCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TOTAL_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (totalCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(totalCostExpr.toString(), TOTAL_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "totalCostExpr"), java.lang.String.valueOf(java.lang.Math.min(TOTAL_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "totalCostExpr", this.totalCostExpr, totalCostExpr));
   }

   /**
    * 관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MANAGE_COST_EXPR = "manageCostExpr";
   static int MANAGE_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String manageCostExpr = "0";
   /**
    * 관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getManageCostExpr() {
      return manageCostExpr;
   }
   /**
    * 관리비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setManageCostExpr(java.lang.String manageCostExpr) throws wt.util.WTPropertyVetoException {
      manageCostExprValidate(manageCostExpr);
      this.manageCostExpr = manageCostExpr;
   }
   void manageCostExprValidate(java.lang.String manageCostExpr) throws wt.util.WTPropertyVetoException {
      if (MANAGE_COST_EXPR_UPPER_LIMIT < 1) {
         try { MANAGE_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("manageCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGE_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (manageCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(manageCostExpr.toString(), MANAGE_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manageCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MANAGE_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "manageCostExpr", this.manageCostExpr, manageCostExpr));
   }

   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SALES_TARGET_COST_EXPR = "salesTargetCostExpr";
   static int SALES_TARGET_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String salesTargetCostExpr = "0";
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSalesTargetCostExpr() {
      return salesTargetCostExpr;
   }
   /**
    * 판매목표가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSalesTargetCostExpr(java.lang.String salesTargetCostExpr) throws wt.util.WTPropertyVetoException {
      salesTargetCostExprValidate(salesTargetCostExpr);
      this.salesTargetCostExpr = salesTargetCostExpr;
   }
   void salesTargetCostExprValidate(java.lang.String salesTargetCostExpr) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_COST_EXPR_UPPER_LIMIT < 1) {
         try { SALES_TARGET_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTargetCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (salesTargetCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTargetCostExpr.toString(), SALES_TARGET_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTargetCostExpr"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTargetCostExpr", this.salesTargetCostExpr, salesTargetCostExpr));
   }

   /**
    * 수익율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PROFIT_RATE_EXPR = "profitRateExpr";
   static int PROFIT_RATE_EXPR_UPPER_LIMIT = -1;
   java.lang.String profitRateExpr = "0";
   /**
    * 수익율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getProfitRateExpr() {
      return profitRateExpr;
   }
   /**
    * 수익율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProfitRateExpr(java.lang.String profitRateExpr) throws wt.util.WTPropertyVetoException {
      profitRateExprValidate(profitRateExpr);
      this.profitRateExpr = profitRateExpr;
   }
   void profitRateExprValidate(java.lang.String profitRateExpr) throws wt.util.WTPropertyVetoException {
      if (PROFIT_RATE_EXPR_UPPER_LIMIT < 1) {
         try { PROFIT_RATE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("profitRateExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROFIT_RATE_EXPR_UPPER_LIMIT = 200; }
      }
      if (profitRateExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(profitRateExpr.toString(), PROFIT_RATE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "profitRateExpr"), java.lang.String.valueOf(java.lang.Math.min(PROFIT_RATE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "profitRateExpr", this.profitRateExpr, profitRateExpr));
   }

   /**
    * 제조원가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MFC_COST_EXPR = "mfcCostExpr";
   static int MFC_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String mfcCostExpr = "0";
   /**
    * 제조원가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMfcCostExpr() {
      return mfcCostExpr;
   }
   /**
    * 제조원가
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMfcCostExpr(java.lang.String mfcCostExpr) throws wt.util.WTPropertyVetoException {
      mfcCostExprValidate(mfcCostExpr);
      this.mfcCostExpr = mfcCostExpr;
   }
   void mfcCostExprValidate(java.lang.String mfcCostExpr) throws wt.util.WTPropertyVetoException {
      if (MFC_COST_EXPR_UPPER_LIMIT < 1) {
         try { MFC_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mfcCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFC_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (mfcCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(mfcCostExpr.toString(), MFC_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mfcCostExpr"), java.lang.String.valueOf(java.lang.Math.min(MFC_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mfcCostExpr", this.mfcCostExpr, mfcCostExpr));
   }

   /**
    * 제품 원재료비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MATERIAL_COST_TOTAL = "materialCostTotal";
   static int MATERIAL_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String materialCostTotal = "0";
   /**
    * 제품 원재료비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMaterialCostTotal() {
      return materialCostTotal;
   }
   /**
    * 제품 원재료비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMaterialCostTotal(java.lang.String materialCostTotal) throws wt.util.WTPropertyVetoException {
      materialCostTotalValidate(materialCostTotal);
      this.materialCostTotal = materialCostTotal;
   }
   void materialCostTotalValidate(java.lang.String materialCostTotal) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_COST_TOTAL_UPPER_LIMIT < 1) {
         try { MATERIAL_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("materialCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (materialCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(materialCostTotal.toString(), MATERIAL_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialCostTotal"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "materialCostTotal", this.materialCostTotal, materialCostTotal));
   }

   /**
    * 제품 노무비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_COST_TOTAL = "laborCostTotal";
   static int LABOR_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String laborCostTotal = "0";
   /**
    * 제품 노무비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborCostTotal() {
      return laborCostTotal;
   }
   /**
    * 제품 노무비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborCostTotal(java.lang.String laborCostTotal) throws wt.util.WTPropertyVetoException {
      laborCostTotalValidate(laborCostTotal);
      this.laborCostTotal = laborCostTotal;
   }
   void laborCostTotalValidate(java.lang.String laborCostTotal) throws wt.util.WTPropertyVetoException {
      if (LABOR_COST_TOTAL_UPPER_LIMIT < 1) {
         try { LABOR_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (laborCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(laborCostTotal.toString(), LABOR_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborCostTotal"), java.lang.String.valueOf(java.lang.Math.min(LABOR_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborCostTotal", this.laborCostTotal, laborCostTotal));
   }

   /**
    * 제품 경비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String EXPENSE_TOTAL = "expenseTotal";
   static int EXPENSE_TOTAL_UPPER_LIMIT = -1;
   java.lang.String expenseTotal = "0";
   /**
    * 제품 경비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getExpenseTotal() {
      return expenseTotal;
   }
   /**
    * 제품 경비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setExpenseTotal(java.lang.String expenseTotal) throws wt.util.WTPropertyVetoException {
      expenseTotalValidate(expenseTotal);
      this.expenseTotal = expenseTotal;
   }
   void expenseTotalValidate(java.lang.String expenseTotal) throws wt.util.WTPropertyVetoException {
      if (EXPENSE_TOTAL_UPPER_LIMIT < 1) {
         try { EXPENSE_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("expenseTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EXPENSE_TOTAL_UPPER_LIMIT = 200; }
      }
      if (expenseTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(expenseTotal.toString(), EXPENSE_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "expenseTotal"), java.lang.String.valueOf(java.lang.Math.min(EXPENSE_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "expenseTotal", this.expenseTotal, expenseTotal));
   }

   /**
    * 제품 제조원가 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MFC_COST_TOTAL = "mfcCostTotal";
   static int MFC_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String mfcCostTotal = "0";
   /**
    * 제품 제조원가 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMfcCostTotal() {
      return mfcCostTotal;
   }
   /**
    * 제품 제조원가 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMfcCostTotal(java.lang.String mfcCostTotal) throws wt.util.WTPropertyVetoException {
      mfcCostTotalValidate(mfcCostTotal);
      this.mfcCostTotal = mfcCostTotal;
   }
   void mfcCostTotalValidate(java.lang.String mfcCostTotal) throws wt.util.WTPropertyVetoException {
      if (MFC_COST_TOTAL_UPPER_LIMIT < 1) {
         try { MFC_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mfcCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MFC_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (mfcCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(mfcCostTotal.toString(), MFC_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mfcCostTotal"), java.lang.String.valueOf(java.lang.Math.min(MFC_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mfcCostTotal", this.mfcCostTotal, mfcCostTotal));
   }

   /**
    * 제품 관리비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MANAGE_COST_TOTAL = "manageCostTotal";
   static int MANAGE_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String manageCostTotal = "0";
   /**
    * 제품 관리비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getManageCostTotal() {
      return manageCostTotal;
   }
   /**
    * 제품 관리비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setManageCostTotal(java.lang.String manageCostTotal) throws wt.util.WTPropertyVetoException {
      manageCostTotalValidate(manageCostTotal);
      this.manageCostTotal = manageCostTotal;
   }
   void manageCostTotalValidate(java.lang.String manageCostTotal) throws wt.util.WTPropertyVetoException {
      if (MANAGE_COST_TOTAL_UPPER_LIMIT < 1) {
         try { MANAGE_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("manageCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGE_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (manageCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(manageCostTotal.toString(), MANAGE_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manageCostTotal"), java.lang.String.valueOf(java.lang.Math.min(MANAGE_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "manageCostTotal", this.manageCostTotal, manageCostTotal));
   }

   /**
    * 제품 감가비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String REDUCE_COST_TOTAL = "reduceCostTotal";
   static int REDUCE_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String reduceCostTotal = "0";
   /**
    * 제품 감가비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getReduceCostTotal() {
      return reduceCostTotal;
   }
   /**
    * 제품 감가비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setReduceCostTotal(java.lang.String reduceCostTotal) throws wt.util.WTPropertyVetoException {
      reduceCostTotalValidate(reduceCostTotal);
      this.reduceCostTotal = reduceCostTotal;
   }
   void reduceCostTotalValidate(java.lang.String reduceCostTotal) throws wt.util.WTPropertyVetoException {
      if (REDUCE_COST_TOTAL_UPPER_LIMIT < 1) {
         try { REDUCE_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reduceCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REDUCE_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (reduceCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(reduceCostTotal.toString(), REDUCE_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reduceCostTotal"), java.lang.String.valueOf(java.lang.Math.min(REDUCE_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reduceCostTotal", this.reduceCostTotal, reduceCostTotal));
   }

   /**
    * 제품 총원가 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRODUCT_COST_TOTAL = "productCostTotal";
   static int PRODUCT_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String productCostTotal = "0";
   /**
    * 제품 총원가 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getProductCostTotal() {
      return productCostTotal;
   }
   /**
    * 제품 총원가 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProductCostTotal(java.lang.String productCostTotal) throws wt.util.WTPropertyVetoException {
      productCostTotalValidate(productCostTotal);
      this.productCostTotal = productCostTotal;
   }
   void productCostTotalValidate(java.lang.String productCostTotal) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_COST_TOTAL_UPPER_LIMIT < 1) {
         try { PRODUCT_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (productCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(productCostTotal.toString(), PRODUCT_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productCostTotal"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productCostTotal", this.productCostTotal, productCostTotal));
   }

   /**
    * 제품 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRODUCT_NINVEST_TOTAL = "productNInvestTotal";
   static int PRODUCT_NINVEST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String productNInvestTotal = "0";
   /**
    * 제품 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getProductNInvestTotal() {
      return productNInvestTotal;
   }
   /**
    * 제품 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProductNInvestTotal(java.lang.String productNInvestTotal) throws wt.util.WTPropertyVetoException {
      productNInvestTotalValidate(productNInvestTotal);
      this.productNInvestTotal = productNInvestTotal;
   }
   void productNInvestTotalValidate(java.lang.String productNInvestTotal) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_NINVEST_TOTAL_UPPER_LIMIT < 1) {
         try { PRODUCT_NINVEST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productNInvestTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_NINVEST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (productNInvestTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(productNInvestTotal.toString(), PRODUCT_NINVEST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productNInvestTotal"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_NINVEST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productNInvestTotal", this.productNInvestTotal, productNInvestTotal));
   }

   /**
    * 금형 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLDINVEST_PRICE_TOTAL = "moldinvestPriceTotal";
   static int MOLDINVEST_PRICE_TOTAL_UPPER_LIMIT = -1;
   java.lang.String moldinvestPriceTotal = "0";
   /**
    * 금형 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldinvestPriceTotal() {
      return moldinvestPriceTotal;
   }
   /**
    * 금형 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldinvestPriceTotal(java.lang.String moldinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      moldinvestPriceTotalValidate(moldinvestPriceTotal);
      this.moldinvestPriceTotal = moldinvestPriceTotal;
   }
   void moldinvestPriceTotalValidate(java.lang.String moldinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      if (MOLDINVEST_PRICE_TOTAL_UPPER_LIMIT < 1) {
         try { MOLDINVEST_PRICE_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldinvestPriceTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLDINVEST_PRICE_TOTAL_UPPER_LIMIT = 200; }
      }
      if (moldinvestPriceTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(moldinvestPriceTotal.toString(), MOLDINVEST_PRICE_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldinvestPriceTotal"), java.lang.String.valueOf(java.lang.Math.min(MOLDINVEST_PRICE_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldinvestPriceTotal", this.moldinvestPriceTotal, moldinvestPriceTotal));
   }

   /**
    * Press 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PRESSINVEST_PRICE_TOTAL = "pressinvestPriceTotal";
   static int PRESSINVEST_PRICE_TOTAL_UPPER_LIMIT = -1;
   java.lang.String pressinvestPriceTotal = "0";
   /**
    * Press 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPressinvestPriceTotal() {
      return pressinvestPriceTotal;
   }
   /**
    * Press 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPressinvestPriceTotal(java.lang.String pressinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      pressinvestPriceTotalValidate(pressinvestPriceTotal);
      this.pressinvestPriceTotal = pressinvestPriceTotal;
   }
   void pressinvestPriceTotalValidate(java.lang.String pressinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      if (PRESSINVEST_PRICE_TOTAL_UPPER_LIMIT < 1) {
         try { PRESSINVEST_PRICE_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pressinvestPriceTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRESSINVEST_PRICE_TOTAL_UPPER_LIMIT = 200; }
      }
      if (pressinvestPriceTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(pressinvestPriceTotal.toString(), PRESSINVEST_PRICE_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pressinvestPriceTotal"), java.lang.String.valueOf(java.lang.Math.min(PRESSINVEST_PRICE_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pressinvestPriceTotal", this.pressinvestPriceTotal, pressinvestPriceTotal));
   }

   /**
    * 조립 설비 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String EQUIPINVEST_PRICE_TOTAL = "equipinvestPriceTotal";
   static int EQUIPINVEST_PRICE_TOTAL_UPPER_LIMIT = -1;
   java.lang.String equipinvestPriceTotal = "0";
   /**
    * 조립 설비 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEquipinvestPriceTotal() {
      return equipinvestPriceTotal;
   }
   /**
    * 조립 설비 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEquipinvestPriceTotal(java.lang.String equipinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      equipinvestPriceTotalValidate(equipinvestPriceTotal);
      this.equipinvestPriceTotal = equipinvestPriceTotal;
   }
   void equipinvestPriceTotalValidate(java.lang.String equipinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      if (EQUIPINVEST_PRICE_TOTAL_UPPER_LIMIT < 1) {
         try { EQUIPINVEST_PRICE_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("equipinvestPriceTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EQUIPINVEST_PRICE_TOTAL_UPPER_LIMIT = 200; }
      }
      if (equipinvestPriceTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(equipinvestPriceTotal.toString(), EQUIPINVEST_PRICE_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "equipinvestPriceTotal"), java.lang.String.valueOf(java.lang.Math.min(EQUIPINVEST_PRICE_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "equipinvestPriceTotal", this.equipinvestPriceTotal, equipinvestPriceTotal));
   }

   /**
    * 구매 금형비 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PURCHASEINVEST_PRICE_TOTAL = "purchaseinvestPriceTotal";
   static int PURCHASEINVEST_PRICE_TOTAL_UPPER_LIMIT = -1;
   java.lang.String purchaseinvestPriceTotal = "0";
   /**
    * 구매 금형비 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPurchaseinvestPriceTotal() {
      return purchaseinvestPriceTotal;
   }
   /**
    * 구매 금형비 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPurchaseinvestPriceTotal(java.lang.String purchaseinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      purchaseinvestPriceTotalValidate(purchaseinvestPriceTotal);
      this.purchaseinvestPriceTotal = purchaseinvestPriceTotal;
   }
   void purchaseinvestPriceTotalValidate(java.lang.String purchaseinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      if (PURCHASEINVEST_PRICE_TOTAL_UPPER_LIMIT < 1) {
         try { PURCHASEINVEST_PRICE_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("purchaseinvestPriceTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PURCHASEINVEST_PRICE_TOTAL_UPPER_LIMIT = 200; }
      }
      if (purchaseinvestPriceTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(purchaseinvestPriceTotal.toString(), PURCHASEINVEST_PRICE_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "purchaseinvestPriceTotal"), java.lang.String.valueOf(java.lang.Math.min(PURCHASEINVEST_PRICE_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "purchaseinvestPriceTotal", this.purchaseinvestPriceTotal, purchaseinvestPriceTotal));
   }

   /**
    * 기타 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ETCINVEST_PRICE_TOTAL = "etcinvestPriceTotal";
   static int ETCINVEST_PRICE_TOTAL_UPPER_LIMIT = -1;
   java.lang.String etcinvestPriceTotal = "0";
   /**
    * 기타 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getEtcinvestPriceTotal() {
      return etcinvestPriceTotal;
   }
   /**
    * 기타 신규 투자비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setEtcinvestPriceTotal(java.lang.String etcinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      etcinvestPriceTotalValidate(etcinvestPriceTotal);
      this.etcinvestPriceTotal = etcinvestPriceTotal;
   }
   void etcinvestPriceTotalValidate(java.lang.String etcinvestPriceTotal) throws wt.util.WTPropertyVetoException {
      if (ETCINVEST_PRICE_TOTAL_UPPER_LIMIT < 1) {
         try { ETCINVEST_PRICE_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcinvestPriceTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETCINVEST_PRICE_TOTAL_UPPER_LIMIT = 200; }
      }
      if (etcinvestPriceTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(etcinvestPriceTotal.toString(), ETCINVEST_PRICE_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcinvestPriceTotal"), java.lang.String.valueOf(java.lang.Math.min(ETCINVEST_PRICE_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcinvestPriceTotal", this.etcinvestPriceTotal, etcinvestPriceTotal));
   }

   /**
    * case버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUB_VERSION = "subVersion";
   java.lang.Integer subVersion = 0;
   /**
    * case버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.Integer getSubVersion() {
      return subVersion;
   }
   /**
    * case버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSubVersion(java.lang.Integer subVersion) throws wt.util.WTPropertyVetoException {
      subVersionValidate(subVersion);
      this.subVersion = subVersion;
   }
   void subVersionValidate(java.lang.Integer subVersion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * case비고
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CASE_NOTE = "caseNote";
   static int CASE_NOTE_UPPER_LIMIT = -1;
   java.lang.String caseNote;
   /**
    * case비고
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCaseNote() {
      return caseNote;
   }
   /**
    * case비고
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCaseNote(java.lang.String caseNote) throws wt.util.WTPropertyVetoException {
      caseNoteValidate(caseNote);
      this.caseNote = caseNote;
   }
   void caseNoteValidate(java.lang.String caseNote) throws wt.util.WTPropertyVetoException {
      if (CASE_NOTE_UPPER_LIMIT < 1) {
         try { CASE_NOTE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("caseNote").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CASE_NOTE_UPPER_LIMIT = 200; }
      }
      if (caseNote != null && !wt.fc.PersistenceHelper.checkStoredLength(caseNote.toString(), CASE_NOTE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "caseNote"), java.lang.String.valueOf(java.lang.Math.min(CASE_NOTE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "caseNote", this.caseNote, caseNote));
   }

   /**
    * capa max물량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CAPA_MAX_QTY = "capaMaxQty";
   static int CAPA_MAX_QTY_UPPER_LIMIT = -1;
   java.lang.String capaMaxQty = "0";
   /**
    * capa max물량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCapaMaxQty() {
      return capaMaxQty;
   }
   /**
    * capa max물량
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCapaMaxQty(java.lang.String capaMaxQty) throws wt.util.WTPropertyVetoException {
      capaMaxQtyValidate(capaMaxQty);
      this.capaMaxQty = capaMaxQty;
   }
   void capaMaxQtyValidate(java.lang.String capaMaxQty) throws wt.util.WTPropertyVetoException {
      if (CAPA_MAX_QTY_UPPER_LIMIT < 1) {
         try { CAPA_MAX_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("capaMaxQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAPA_MAX_QTY_UPPER_LIMIT = 200; }
      }
      if (capaMaxQty != null && !wt.fc.PersistenceHelper.checkStoredLength(capaMaxQty.toString(), CAPA_MAX_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "capaMaxQty"), java.lang.String.valueOf(java.lang.Math.min(CAPA_MAX_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "capaMaxQty", this.capaMaxQty, capaMaxQty));
   }

   /**
    * case순서
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CASE_ORDER = "caseOrder";
   java.lang.Integer caseOrder;
   /**
    * case순서
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.Integer getCaseOrder() {
      return caseOrder;
   }
   /**
    * case순서
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCaseOrder(java.lang.Integer caseOrder) throws wt.util.WTPropertyVetoException {
      caseOrderValidate(caseOrder);
      this.caseOrder = caseOrder;
   }
   void caseOrderValidate(java.lang.Integer caseOrder) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LASTEST = "lastest";
   boolean lastest = false;
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public boolean isLastest() {
      return lastest;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLastest(boolean lastest) throws wt.util.WTPropertyVetoException {
      lastestValidate(lastest);
      this.lastest = lastest;
   }
   void lastestValidate(boolean lastest) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 부품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUB_COST_ALL_TOTAL = "subCostAllTotal";
   static int SUB_COST_ALL_TOTAL_UPPER_LIMIT = -1;
   java.lang.String subCostAllTotal = "0";
   /**
    * 부품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSubCostAllTotal() {
      return subCostAllTotal;
   }
   /**
    * 부품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSubCostAllTotal(java.lang.String subCostAllTotal) throws wt.util.WTPropertyVetoException {
      subCostAllTotalValidate(subCostAllTotal);
      this.subCostAllTotal = subCostAllTotal;
   }
   void subCostAllTotalValidate(java.lang.String subCostAllTotal) throws wt.util.WTPropertyVetoException {
      if (SUB_COST_ALL_TOTAL_UPPER_LIMIT < 1) {
         try { SUB_COST_ALL_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subCostAllTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_COST_ALL_TOTAL_UPPER_LIMIT = 200; }
      }
      if (subCostAllTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(subCostAllTotal.toString(), SUB_COST_ALL_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subCostAllTotal"), java.lang.String.valueOf(java.lang.Math.min(SUB_COST_ALL_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subCostAllTotal", this.subCostAllTotal, subCostAllTotal));
   }

   /**
    * 부품합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUB_COST_ALL_TOTAL_OPTION = "subCostAllTotalOption";
   static int SUB_COST_ALL_TOTAL_OPTION_UPPER_LIMIT = -1;
   java.lang.String subCostAllTotalOption = "0";
   /**
    * 부품합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSubCostAllTotalOption() {
      return subCostAllTotalOption;
   }
   /**
    * 부품합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSubCostAllTotalOption(java.lang.String subCostAllTotalOption) throws wt.util.WTPropertyVetoException {
      subCostAllTotalOptionValidate(subCostAllTotalOption);
      this.subCostAllTotalOption = subCostAllTotalOption;
   }
   void subCostAllTotalOptionValidate(java.lang.String subCostAllTotalOption) throws wt.util.WTPropertyVetoException {
      if (SUB_COST_ALL_TOTAL_OPTION_UPPER_LIMIT < 1) {
         try { SUB_COST_ALL_TOTAL_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subCostAllTotalOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_COST_ALL_TOTAL_OPTION_UPPER_LIMIT = 200; }
      }
      if (subCostAllTotalOption != null && !wt.fc.PersistenceHelper.checkStoredLength(subCostAllTotalOption.toString(), SUB_COST_ALL_TOTAL_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subCostAllTotalOption"), java.lang.String.valueOf(java.lang.Math.min(SUB_COST_ALL_TOTAL_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subCostAllTotalOption", this.subCostAllTotalOption, subCostAllTotalOption));
   }

   /**
    * 지정품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUB_COST_EXCEPT_TOTAL = "subCostExceptTotal";
   static int SUB_COST_EXCEPT_TOTAL_UPPER_LIMIT = -1;
   java.lang.String subCostExceptTotal = "0";
   /**
    * 지정품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSubCostExceptTotal() {
      return subCostExceptTotal;
   }
   /**
    * 지정품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSubCostExceptTotal(java.lang.String subCostExceptTotal) throws wt.util.WTPropertyVetoException {
      subCostExceptTotalValidate(subCostExceptTotal);
      this.subCostExceptTotal = subCostExceptTotal;
   }
   void subCostExceptTotalValidate(java.lang.String subCostExceptTotal) throws wt.util.WTPropertyVetoException {
      if (SUB_COST_EXCEPT_TOTAL_UPPER_LIMIT < 1) {
         try { SUB_COST_EXCEPT_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subCostExceptTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_COST_EXCEPT_TOTAL_UPPER_LIMIT = 200; }
      }
      if (subCostExceptTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(subCostExceptTotal.toString(), SUB_COST_EXCEPT_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subCostExceptTotal"), java.lang.String.valueOf(java.lang.Math.min(SUB_COST_EXCEPT_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subCostExceptTotal", this.subCostExceptTotal, subCostExceptTotal));
   }

   /**
    * 추가공수CT_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_CT_1 = "addManHourCt_1";
   static int ADD_MAN_HOUR_CT_1_UPPER_LIMIT = -1;
   java.lang.String addManHourCt_1 = "0";
   /**
    * 추가공수CT_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourCt_1() {
      return addManHourCt_1;
   }
   /**
    * 추가공수CT_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourCt_1(java.lang.String addManHourCt_1) throws wt.util.WTPropertyVetoException {
      addManHourCt_1Validate(addManHourCt_1);
      this.addManHourCt_1 = addManHourCt_1;
   }
   void addManHourCt_1Validate(java.lang.String addManHourCt_1) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_CT_1_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_CT_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourCt_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_CT_1_UPPER_LIMIT = 200; }
      }
      if (addManHourCt_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourCt_1.toString(), ADD_MAN_HOUR_CT_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourCt_1"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_CT_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourCt_1", this.addManHourCt_1, addManHourCt_1));
   }

   /**
    * 추가공수효율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_EFF_1 = "addManHourEff_1";
   static int ADD_MAN_HOUR_EFF_1_UPPER_LIMIT = -1;
   java.lang.String addManHourEff_1 = "0";
   /**
    * 추가공수효율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourEff_1() {
      return addManHourEff_1;
   }
   /**
    * 추가공수효율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourEff_1(java.lang.String addManHourEff_1) throws wt.util.WTPropertyVetoException {
      addManHourEff_1Validate(addManHourEff_1);
      this.addManHourEff_1 = addManHourEff_1;
   }
   void addManHourEff_1Validate(java.lang.String addManHourEff_1) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_EFF_1_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_EFF_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourEff_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_EFF_1_UPPER_LIMIT = 200; }
      }
      if (addManHourEff_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourEff_1.toString(), ADD_MAN_HOUR_EFF_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourEff_1"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_EFF_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourEff_1", this.addManHourEff_1, addManHourEff_1));
   }

   /**
    * 추가임율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_LB_1 = "addManHourLb_1";
   static int ADD_MAN_HOUR_LB_1_UPPER_LIMIT = -1;
   java.lang.String addManHourLb_1 = "0";
   /**
    * 추가임율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourLb_1() {
      return addManHourLb_1;
   }
   /**
    * 추가임율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourLb_1(java.lang.String addManHourLb_1) throws wt.util.WTPropertyVetoException {
      addManHourLb_1Validate(addManHourLb_1);
      this.addManHourLb_1 = addManHourLb_1;
   }
   void addManHourLb_1Validate(java.lang.String addManHourLb_1) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_LB_1_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_LB_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourLb_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_LB_1_UPPER_LIMIT = 200; }
      }
      if (addManHourLb_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourLb_1.toString(), ADD_MAN_HOUR_LB_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourLb_1"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_LB_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourLb_1", this.addManHourLb_1, addManHourLb_1));
   }

   /**
    * 추가임율상승율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_CLB_1 = "addManHourClb_1";
   static int ADD_MAN_HOUR_CLB_1_UPPER_LIMIT = -1;
   java.lang.String addManHourClb_1 = "0";
   /**
    * 추가임율상승율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourClb_1() {
      return addManHourClb_1;
   }
   /**
    * 추가임율상승율_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourClb_1(java.lang.String addManHourClb_1) throws wt.util.WTPropertyVetoException {
      addManHourClb_1Validate(addManHourClb_1);
      this.addManHourClb_1 = addManHourClb_1;
   }
   void addManHourClb_1Validate(java.lang.String addManHourClb_1) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_CLB_1_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_CLB_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourClb_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_CLB_1_UPPER_LIMIT = 200; }
      }
      if (addManHourClb_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourClb_1.toString(), ADD_MAN_HOUR_CLB_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourClb_1"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_CLB_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourClb_1", this.addManHourClb_1, addManHourClb_1));
   }

   /**
    * 추가임율년도_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_YEAR_1 = "addManHourYear_1";
   static int ADD_MAN_HOUR_YEAR_1_UPPER_LIMIT = -1;
   java.lang.String addManHourYear_1 = "0";
   /**
    * 추가임율년도_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourYear_1() {
      return addManHourYear_1;
   }
   /**
    * 추가임율년도_1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourYear_1(java.lang.String addManHourYear_1) throws wt.util.WTPropertyVetoException {
      addManHourYear_1Validate(addManHourYear_1);
      this.addManHourYear_1 = addManHourYear_1;
   }
   void addManHourYear_1Validate(java.lang.String addManHourYear_1) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_YEAR_1_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_YEAR_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourYear_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_YEAR_1_UPPER_LIMIT = 200; }
      }
      if (addManHourYear_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourYear_1.toString(), ADD_MAN_HOUR_YEAR_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourYear_1"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_YEAR_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourYear_1", this.addManHourYear_1, addManHourYear_1));
   }

   /**
    * 추가공수CT_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_CT_2 = "addManHourCt_2";
   static int ADD_MAN_HOUR_CT_2_UPPER_LIMIT = -1;
   java.lang.String addManHourCt_2 = "0";
   /**
    * 추가공수CT_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourCt_2() {
      return addManHourCt_2;
   }
   /**
    * 추가공수CT_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourCt_2(java.lang.String addManHourCt_2) throws wt.util.WTPropertyVetoException {
      addManHourCt_2Validate(addManHourCt_2);
      this.addManHourCt_2 = addManHourCt_2;
   }
   void addManHourCt_2Validate(java.lang.String addManHourCt_2) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_CT_2_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_CT_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourCt_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_CT_2_UPPER_LIMIT = 200; }
      }
      if (addManHourCt_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourCt_2.toString(), ADD_MAN_HOUR_CT_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourCt_2"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_CT_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourCt_2", this.addManHourCt_2, addManHourCt_2));
   }

   /**
    * 추가공수효율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_EFF_2 = "addManHourEff_2";
   static int ADD_MAN_HOUR_EFF_2_UPPER_LIMIT = -1;
   java.lang.String addManHourEff_2 = "0";
   /**
    * 추가공수효율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourEff_2() {
      return addManHourEff_2;
   }
   /**
    * 추가공수효율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourEff_2(java.lang.String addManHourEff_2) throws wt.util.WTPropertyVetoException {
      addManHourEff_2Validate(addManHourEff_2);
      this.addManHourEff_2 = addManHourEff_2;
   }
   void addManHourEff_2Validate(java.lang.String addManHourEff_2) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_EFF_2_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_EFF_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourEff_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_EFF_2_UPPER_LIMIT = 200; }
      }
      if (addManHourEff_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourEff_2.toString(), ADD_MAN_HOUR_EFF_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourEff_2"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_EFF_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourEff_2", this.addManHourEff_2, addManHourEff_2));
   }

   /**
    * 추가임율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_LB_2 = "addManHourLb_2";
   static int ADD_MAN_HOUR_LB_2_UPPER_LIMIT = -1;
   java.lang.String addManHourLb_2 = "0";
   /**
    * 추가임율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourLb_2() {
      return addManHourLb_2;
   }
   /**
    * 추가임율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourLb_2(java.lang.String addManHourLb_2) throws wt.util.WTPropertyVetoException {
      addManHourLb_2Validate(addManHourLb_2);
      this.addManHourLb_2 = addManHourLb_2;
   }
   void addManHourLb_2Validate(java.lang.String addManHourLb_2) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_LB_2_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_LB_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourLb_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_LB_2_UPPER_LIMIT = 200; }
      }
      if (addManHourLb_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourLb_2.toString(), ADD_MAN_HOUR_LB_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourLb_2"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_LB_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourLb_2", this.addManHourLb_2, addManHourLb_2));
   }

   /**
    * 추가임율상승율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_CLB_2 = "addManHourClb_2";
   static int ADD_MAN_HOUR_CLB_2_UPPER_LIMIT = -1;
   java.lang.String addManHourClb_2 = "0";
   /**
    * 추가임율상승율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourClb_2() {
      return addManHourClb_2;
   }
   /**
    * 추가임율상승율_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourClb_2(java.lang.String addManHourClb_2) throws wt.util.WTPropertyVetoException {
      addManHourClb_2Validate(addManHourClb_2);
      this.addManHourClb_2 = addManHourClb_2;
   }
   void addManHourClb_2Validate(java.lang.String addManHourClb_2) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_CLB_2_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_CLB_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourClb_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_CLB_2_UPPER_LIMIT = 200; }
      }
      if (addManHourClb_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourClb_2.toString(), ADD_MAN_HOUR_CLB_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourClb_2"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_CLB_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourClb_2", this.addManHourClb_2, addManHourClb_2));
   }

   /**
    * 추가임율년도_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ADD_MAN_HOUR_YEAR_2 = "addManHourYear_2";
   static int ADD_MAN_HOUR_YEAR_2_UPPER_LIMIT = -1;
   java.lang.String addManHourYear_2 = "0";
   /**
    * 추가임율년도_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAddManHourYear_2() {
      return addManHourYear_2;
   }
   /**
    * 추가임율년도_2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAddManHourYear_2(java.lang.String addManHourYear_2) throws wt.util.WTPropertyVetoException {
      addManHourYear_2Validate(addManHourYear_2);
      this.addManHourYear_2 = addManHourYear_2;
   }
   void addManHourYear_2Validate(java.lang.String addManHourYear_2) throws wt.util.WTPropertyVetoException {
      if (ADD_MAN_HOUR_YEAR_2_UPPER_LIMIT < 1) {
         try { ADD_MAN_HOUR_YEAR_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("addManHourYear_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADD_MAN_HOUR_YEAR_2_UPPER_LIMIT = 200; }
      }
      if (addManHourYear_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(addManHourYear_2.toString(), ADD_MAN_HOUR_YEAR_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "addManHourYear_2"), java.lang.String.valueOf(java.lang.Math.min(ADD_MAN_HOUR_YEAR_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "addManHourYear_2", this.addManHourYear_2, addManHourYear_2));
   }

   /**
    * 개별포장비 합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PACK_UNIT_PRICE_OPTION = "packUnitPriceOption";
   static int PACK_UNIT_PRICE_OPTION_UPPER_LIMIT = -1;
   java.lang.String packUnitPriceOption = "0";
   /**
    * 개별포장비 합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getPackUnitPriceOption() {
      return packUnitPriceOption;
   }
   /**
    * 개별포장비 합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setPackUnitPriceOption(java.lang.String packUnitPriceOption) throws wt.util.WTPropertyVetoException {
      packUnitPriceOptionValidate(packUnitPriceOption);
      this.packUnitPriceOption = packUnitPriceOption;
   }
   void packUnitPriceOptionValidate(java.lang.String packUnitPriceOption) throws wt.util.WTPropertyVetoException {
      if (PACK_UNIT_PRICE_OPTION_UPPER_LIMIT < 1) {
         try { PACK_UNIT_PRICE_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("packUnitPriceOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PACK_UNIT_PRICE_OPTION_UPPER_LIMIT = 200; }
      }
      if (packUnitPriceOption != null && !wt.fc.PersistenceHelper.checkStoredLength(packUnitPriceOption.toString(), PACK_UNIT_PRICE_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packUnitPriceOption"), java.lang.String.valueOf(java.lang.Math.min(PACK_UNIT_PRICE_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "packUnitPriceOption", this.packUnitPriceOption, packUnitPriceOption));
   }

   /**
    * 후도금비(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String AP_UNIT_COST_OPTION = "apUnitCostOption";
   static int AP_UNIT_COST_OPTION_UPPER_LIMIT = -1;
   java.lang.String apUnitCostOption = "0";
   /**
    * 후도금비(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getApUnitCostOption() {
      return apUnitCostOption;
   }
   /**
    * 후도금비(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setApUnitCostOption(java.lang.String apUnitCostOption) throws wt.util.WTPropertyVetoException {
      apUnitCostOptionValidate(apUnitCostOption);
      this.apUnitCostOption = apUnitCostOption;
   }
   void apUnitCostOptionValidate(java.lang.String apUnitCostOption) throws wt.util.WTPropertyVetoException {
      if (AP_UNIT_COST_OPTION_UPPER_LIMIT < 1) {
         try { AP_UNIT_COST_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("apUnitCostOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { AP_UNIT_COST_OPTION_UPPER_LIMIT = 200; }
      }
      if (apUnitCostOption != null && !wt.fc.PersistenceHelper.checkStoredLength(apUnitCostOption.toString(), AP_UNIT_COST_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "apUnitCostOption"), java.lang.String.valueOf(java.lang.Math.min(AP_UNIT_COST_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "apUnitCostOption", this.apUnitCostOption, apUnitCostOption));
   }

   /**
    * 일반관리비율(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CO_MANAGE_RATE_OPTION = "coManageRateOption";
   static int CO_MANAGE_RATE_OPTION_UPPER_LIMIT = -1;
   java.lang.String coManageRateOption = "0";
   /**
    * 일반관리비율(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCoManageRateOption() {
      return coManageRateOption;
   }
   /**
    * 일반관리비율(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCoManageRateOption(java.lang.String coManageRateOption) throws wt.util.WTPropertyVetoException {
      coManageRateOptionValidate(coManageRateOption);
      this.coManageRateOption = coManageRateOption;
   }
   void coManageRateOptionValidate(java.lang.String coManageRateOption) throws wt.util.WTPropertyVetoException {
      if (CO_MANAGE_RATE_OPTION_UPPER_LIMIT < 1) {
         try { CO_MANAGE_RATE_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coManageRateOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CO_MANAGE_RATE_OPTION_UPPER_LIMIT = 200; }
      }
      if (coManageRateOption != null && !wt.fc.PersistenceHelper.checkStoredLength(coManageRateOption.toString(), CO_MANAGE_RATE_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coManageRateOption"), java.lang.String.valueOf(java.lang.Math.min(CO_MANAGE_RATE_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coManageRateOption", this.coManageRateOption, coManageRateOption));
   }

   /**
    * 지정품합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUB_COST_EXCEPT_TOTAL_OPTION = "subCostExceptTotalOption";
   static int SUB_COST_EXCEPT_TOTAL_OPTION_UPPER_LIMIT = -1;
   java.lang.String subCostExceptTotalOption = "0";
   /**
    * 지정품합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSubCostExceptTotalOption() {
      return subCostExceptTotalOption;
   }
   /**
    * 지정품합계(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSubCostExceptTotalOption(java.lang.String subCostExceptTotalOption) throws wt.util.WTPropertyVetoException {
      subCostExceptTotalOptionValidate(subCostExceptTotalOption);
      this.subCostExceptTotalOption = subCostExceptTotalOption;
   }
   void subCostExceptTotalOptionValidate(java.lang.String subCostExceptTotalOption) throws wt.util.WTPropertyVetoException {
      if (SUB_COST_EXCEPT_TOTAL_OPTION_UPPER_LIMIT < 1) {
         try { SUB_COST_EXCEPT_TOTAL_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subCostExceptTotalOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_COST_EXCEPT_TOTAL_OPTION_UPPER_LIMIT = 200; }
      }
      if (subCostExceptTotalOption != null && !wt.fc.PersistenceHelper.checkStoredLength(subCostExceptTotalOption.toString(), SUB_COST_EXCEPT_TOTAL_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subCostExceptTotalOption"), java.lang.String.valueOf(java.lang.Math.min(SUB_COST_EXCEPT_TOTAL_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subCostExceptTotalOption", this.subCostExceptTotalOption, subCostExceptTotalOption));
   }

   /**
    * R&D율(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String RND_OPTION = "rndOption";
   static int RND_OPTION_UPPER_LIMIT = -1;
   java.lang.String rndOption = "0";
   /**
    * R&D율(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRndOption() {
      return rndOption;
   }
   /**
    * R&D율(옵션)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRndOption(java.lang.String rndOption) throws wt.util.WTPropertyVetoException {
      rndOptionValidate(rndOption);
      this.rndOption = rndOption;
   }
   void rndOptionValidate(java.lang.String rndOption) throws wt.util.WTPropertyVetoException {
      if (RND_OPTION_UPPER_LIMIT < 1) {
         try { RND_OPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rndOption").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RND_OPTION_UPPER_LIMIT = 200; }
      }
      if (rndOption != null && !wt.fc.PersistenceHelper.checkStoredLength(rndOption.toString(), RND_OPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rndOption"), java.lang.String.valueOf(java.lang.Math.min(RND_OPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rndOption", this.rndOption, rndOption));
   }

   /**
    * 적용년수(capa)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CAPA_YEAR = "capaYear";
   static int CAPA_YEAR_UPPER_LIMIT = -1;
   java.lang.String capaYear;
   /**
    * 적용년수(capa)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCapaYear() {
      return capaYear;
   }
   /**
    * 적용년수(capa)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCapaYear(java.lang.String capaYear) throws wt.util.WTPropertyVetoException {
      capaYearValidate(capaYear);
      this.capaYear = capaYear;
   }
   void capaYearValidate(java.lang.String capaYear) throws wt.util.WTPropertyVetoException {
      if (CAPA_YEAR_UPPER_LIMIT < 1) {
         try { CAPA_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("capaYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAPA_YEAR_UPPER_LIMIT = 200; }
      }
      if (capaYear != null && !wt.fc.PersistenceHelper.checkStoredLength(capaYear.toString(), CAPA_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "capaYear"), java.lang.String.valueOf(java.lang.Math.min(CAPA_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "capaYear", this.capaYear, capaYear));
   }

   /**
    * 보고서U/S
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String REPORT_US = "reportUS";
   static int REPORT_US_UPPER_LIMIT = -1;
   java.lang.String reportUS = "1";
   /**
    * 보고서U/S
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getReportUS() {
      return reportUS;
   }
   /**
    * 보고서U/S
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setReportUS(java.lang.String reportUS) throws wt.util.WTPropertyVetoException {
      reportUSValidate(reportUS);
      this.reportUS = reportUS;
   }
   void reportUSValidate(java.lang.String reportUS) throws wt.util.WTPropertyVetoException {
      if (REPORT_US_UPPER_LIMIT < 1) {
         try { REPORT_US_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reportUS").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REPORT_US_UPPER_LIMIT = 200; }
      }
      if (reportUS != null && !wt.fc.PersistenceHelper.checkStoredLength(reportUS.toString(), REPORT_US_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reportUS"), java.lang.String.valueOf(java.lang.Math.min(REPORT_US_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reportUS", this.reportUS, reportUS));
   }

   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DR_STEP = "drStep";
   static int DR_STEP_UPPER_LIMIT = -1;
   java.lang.String drStep;
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDrStep() {
      return drStep;
   }
   /**
    * DR단계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDrStep(java.lang.String drStep) throws wt.util.WTPropertyVetoException {
      drStepValidate(drStep);
      this.drStep = drStep;
   }
   void drStepValidate(java.lang.String drStep) throws wt.util.WTPropertyVetoException {
      if (DR_STEP_UPPER_LIMIT < 1) {
         try { DR_STEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("drStep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DR_STEP_UPPER_LIMIT = 200; }
      }
      if (drStep != null && !wt.fc.PersistenceHelper.checkStoredLength(drStep.toString(), DR_STEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "drStep"), java.lang.String.valueOf(java.lang.Math.min(DR_STEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "drStep", this.drStep, drStep));
   }

   /**
    * 무상 원재료비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FREE_RAW_MATERIAL_COST_TOTAL = "freeRawMaterialCostTotal";
   static int FREE_RAW_MATERIAL_COST_TOTAL_UPPER_LIMIT = -1;
   java.lang.String freeRawMaterialCostTotal = "0";
   /**
    * 무상 원재료비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFreeRawMaterialCostTotal() {
      return freeRawMaterialCostTotal;
   }
   /**
    * 무상 원재료비 합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFreeRawMaterialCostTotal(java.lang.String freeRawMaterialCostTotal) throws wt.util.WTPropertyVetoException {
      freeRawMaterialCostTotalValidate(freeRawMaterialCostTotal);
      this.freeRawMaterialCostTotal = freeRawMaterialCostTotal;
   }
   void freeRawMaterialCostTotalValidate(java.lang.String freeRawMaterialCostTotal) throws wt.util.WTPropertyVetoException {
      if (FREE_RAW_MATERIAL_COST_TOTAL_UPPER_LIMIT < 1) {
         try { FREE_RAW_MATERIAL_COST_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("freeRawMaterialCostTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FREE_RAW_MATERIAL_COST_TOTAL_UPPER_LIMIT = 200; }
      }
      if (freeRawMaterialCostTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(freeRawMaterialCostTotal.toString(), FREE_RAW_MATERIAL_COST_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "freeRawMaterialCostTotal"), java.lang.String.valueOf(java.lang.Math.min(FREE_RAW_MATERIAL_COST_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "freeRawMaterialCostTotal", this.freeRawMaterialCostTotal, freeRawMaterialCostTotal));
   }

   /**
    * 동기화여부
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String INIT_FLAG = "initFlag";
   static int INIT_FLAG_UPPER_LIMIT = -1;
   java.lang.String initFlag;
   /**
    * 동기화여부
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getInitFlag() {
      return initFlag;
   }
   /**
    * 동기화여부
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setInitFlag(java.lang.String initFlag) throws wt.util.WTPropertyVetoException {
      initFlagValidate(initFlag);
      this.initFlag = initFlag;
   }
   void initFlagValidate(java.lang.String initFlag) throws wt.util.WTPropertyVetoException {
      if (INIT_FLAG_UPPER_LIMIT < 1) {
         try { INIT_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("initFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INIT_FLAG_UPPER_LIMIT = 200; }
      }
      if (initFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(initFlag.toString(), INIT_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "initFlag"), java.lang.String.valueOf(java.lang.Math.min(INIT_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "initFlag", this.initFlag, initFlag));
   }

   /**
    * 수식 버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FORMULA_VERSION = "formulaVersion";
   java.lang.Integer formulaVersion = 0;
   /**
    * 수식 버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.Integer getFormulaVersion() {
      return formulaVersion;
   }
   /**
    * 수식 버전
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFormulaVersion(java.lang.Integer formulaVersion) throws wt.util.WTPropertyVetoException {
      formulaVersionValidate(formulaVersion);
      this.formulaVersion = formulaVersion;
   }
   void formulaVersionValidate(java.lang.Integer formulaVersion) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 품번
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String REAL_PART_NO = "realPartNo";
   static int REAL_PART_NO_UPPER_LIMIT = -1;
   java.lang.String realPartNo;
   /**
    * 품번
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getRealPartNo() {
      return realPartNo;
   }
   /**
    * 품번
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setRealPartNo(java.lang.String realPartNo) throws wt.util.WTPropertyVetoException {
      realPartNoValidate(realPartNo);
      this.realPartNo = realPartNo;
   }
   void realPartNoValidate(java.lang.String realPartNo) throws wt.util.WTPropertyVetoException {
      if (REAL_PART_NO_UPPER_LIMIT < 1) {
         try { REAL_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("realPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REAL_PART_NO_UPPER_LIMIT = 200; }
      }
      if (realPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(realPartNo.toString(), REAL_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "realPartNo"), java.lang.String.valueOf(java.lang.Math.min(REAL_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "realPartNo", this.realPartNo, realPartNo));
   }

   /**
    * 범용감가CT(SPM)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String FAC_REDUCE_CT_SPM = "facReduceCtSpm";
   static int FAC_REDUCE_CT_SPM_UPPER_LIMIT = -1;
   java.lang.String facReduceCtSpm = "0";
   /**
    * 범용감가CT(SPM)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getFacReduceCtSpm() {
      return facReduceCtSpm;
   }
   /**
    * 범용감가CT(SPM)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setFacReduceCtSpm(java.lang.String facReduceCtSpm) throws wt.util.WTPropertyVetoException {
      facReduceCtSpmValidate(facReduceCtSpm);
      this.facReduceCtSpm = facReduceCtSpm;
   }
   void facReduceCtSpmValidate(java.lang.String facReduceCtSpm) throws wt.util.WTPropertyVetoException {
      if (FAC_REDUCE_CT_SPM_UPPER_LIMIT < 1) {
         try { FAC_REDUCE_CT_SPM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("facReduceCtSpm").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FAC_REDUCE_CT_SPM_UPPER_LIMIT = 200; }
      }
      if (facReduceCtSpm != null && !wt.fc.PersistenceHelper.checkStoredLength(facReduceCtSpm.toString(), FAC_REDUCE_CT_SPM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "facReduceCtSpm"), java.lang.String.valueOf(java.lang.Math.min(FAC_REDUCE_CT_SPM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "facReduceCtSpm", this.facReduceCtSpm, facReduceCtSpm));
   }

   /**
    * 노무비_수식_생산조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_PRODUCT_ASSY_EXPR = "moldProductAssyExpr";
   static int MOLD_PRODUCT_ASSY_EXPR_UPPER_LIMIT = -1;
   java.lang.String moldProductAssyExpr = "0";
   /**
    * 노무비_수식_생산조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldProductAssyExpr() {
      return moldProductAssyExpr;
   }
   /**
    * 노무비_수식_생산조립
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldProductAssyExpr(java.lang.String moldProductAssyExpr) throws wt.util.WTPropertyVetoException {
      moldProductAssyExprValidate(moldProductAssyExpr);
      this.moldProductAssyExpr = moldProductAssyExpr;
   }
   void moldProductAssyExprValidate(java.lang.String moldProductAssyExpr) throws wt.util.WTPropertyVetoException {
      if (MOLD_PRODUCT_ASSY_EXPR_UPPER_LIMIT < 1) {
         try { MOLD_PRODUCT_ASSY_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldProductAssyExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_PRODUCT_ASSY_EXPR_UPPER_LIMIT = 200; }
      }
      if (moldProductAssyExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(moldProductAssyExpr.toString(), MOLD_PRODUCT_ASSY_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldProductAssyExpr"), java.lang.String.valueOf(java.lang.Math.min(MOLD_PRODUCT_ASSY_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldProductAssyExpr", this.moldProductAssyExpr, moldProductAssyExpr));
   }

   /**
    * 노무비_수식_금형준비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MOLD_PREPARE_EXPR = "moldPrepareExpr";
   static int MOLD_PREPARE_EXPR_UPPER_LIMIT = -1;
   java.lang.String moldPrepareExpr = "0";
   /**
    * 노무비_수식_금형준비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getMoldPrepareExpr() {
      return moldPrepareExpr;
   }
   /**
    * 노무비_수식_금형준비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMoldPrepareExpr(java.lang.String moldPrepareExpr) throws wt.util.WTPropertyVetoException {
      moldPrepareExprValidate(moldPrepareExpr);
      this.moldPrepareExpr = moldPrepareExpr;
   }
   void moldPrepareExprValidate(java.lang.String moldPrepareExpr) throws wt.util.WTPropertyVetoException {
      if (MOLD_PREPARE_EXPR_UPPER_LIMIT < 1) {
         try { MOLD_PREPARE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldPrepareExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_PREPARE_EXPR_UPPER_LIMIT = 200; }
      }
      if (moldPrepareExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(moldPrepareExpr.toString(), MOLD_PREPARE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldPrepareExpr"), java.lang.String.valueOf(java.lang.Math.min(MOLD_PREPARE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldPrepareExpr", this.moldPrepareExpr, moldPrepareExpr));
   }

   /**
    * 노무비_수식_추가공수1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_EXPENSE_ADD1_EXPR = "laborExpenseAdd1Expr";
   static int LABOR_EXPENSE_ADD1_EXPR_UPPER_LIMIT = -1;
   java.lang.String laborExpenseAdd1Expr = "0";
   /**
    * 노무비_수식_추가공수1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborExpenseAdd1Expr() {
      return laborExpenseAdd1Expr;
   }
   /**
    * 노무비_수식_추가공수1
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborExpenseAdd1Expr(java.lang.String laborExpenseAdd1Expr) throws wt.util.WTPropertyVetoException {
      laborExpenseAdd1ExprValidate(laborExpenseAdd1Expr);
      this.laborExpenseAdd1Expr = laborExpenseAdd1Expr;
   }
   void laborExpenseAdd1ExprValidate(java.lang.String laborExpenseAdd1Expr) throws wt.util.WTPropertyVetoException {
      if (LABOR_EXPENSE_ADD1_EXPR_UPPER_LIMIT < 1) {
         try { LABOR_EXPENSE_ADD1_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborExpenseAdd1Expr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_EXPENSE_ADD1_EXPR_UPPER_LIMIT = 200; }
      }
      if (laborExpenseAdd1Expr != null && !wt.fc.PersistenceHelper.checkStoredLength(laborExpenseAdd1Expr.toString(), LABOR_EXPENSE_ADD1_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborExpenseAdd1Expr"), java.lang.String.valueOf(java.lang.Math.min(LABOR_EXPENSE_ADD1_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborExpenseAdd1Expr", this.laborExpenseAdd1Expr, laborExpenseAdd1Expr));
   }

   /**
    * 노무비_수식_추가공수2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String LABOR_EXPENSE_ADD2_EXPR = "laborExpenseAdd2Expr";
   static int LABOR_EXPENSE_ADD2_EXPR_UPPER_LIMIT = -1;
   java.lang.String laborExpenseAdd2Expr = "0";
   /**
    * 노무비_수식_추가공수2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getLaborExpenseAdd2Expr() {
      return laborExpenseAdd2Expr;
   }
   /**
    * 노무비_수식_추가공수2
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setLaborExpenseAdd2Expr(java.lang.String laborExpenseAdd2Expr) throws wt.util.WTPropertyVetoException {
      laborExpenseAdd2ExprValidate(laborExpenseAdd2Expr);
      this.laborExpenseAdd2Expr = laborExpenseAdd2Expr;
   }
   void laborExpenseAdd2ExprValidate(java.lang.String laborExpenseAdd2Expr) throws wt.util.WTPropertyVetoException {
      if (LABOR_EXPENSE_ADD2_EXPR_UPPER_LIMIT < 1) {
         try { LABOR_EXPENSE_ADD2_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("laborExpenseAdd2Expr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LABOR_EXPENSE_ADD2_EXPR_UPPER_LIMIT = 200; }
      }
      if (laborExpenseAdd2Expr != null && !wt.fc.PersistenceHelper.checkStoredLength(laborExpenseAdd2Expr.toString(), LABOR_EXPENSE_ADD2_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "laborExpenseAdd2Expr"), java.lang.String.valueOf(java.lang.Math.min(LABOR_EXPENSE_ADD2_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "laborExpenseAdd2Expr", this.laborExpenseAdd2Expr, laborExpenseAdd2Expr));
   }

   /**
    * 관리비_수식_원재료물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MANAGE_MTR_LOGIS_EXPR = "manageMtrLogisExpr";
   static int MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT = -1;
   java.lang.String manageMtrLogisExpr = "0";
   /**
    * 관리비_수식_원재료물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getManageMtrLogisExpr() {
      return manageMtrLogisExpr;
   }
   /**
    * 관리비_수식_원재료물류비
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setManageMtrLogisExpr(java.lang.String manageMtrLogisExpr) throws wt.util.WTPropertyVetoException {
      manageMtrLogisExprValidate(manageMtrLogisExpr);
      this.manageMtrLogisExpr = manageMtrLogisExpr;
   }
   void manageMtrLogisExprValidate(java.lang.String manageMtrLogisExpr) throws wt.util.WTPropertyVetoException {
      if (MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT < 1) {
         try { MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("manageMtrLogisExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT = 200; }
      }
      if (manageMtrLogisExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(manageMtrLogisExpr.toString(), MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manageMtrLogisExpr"), java.lang.String.valueOf(java.lang.Math.min(MANAGE_MTR_LOGIS_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "manageMtrLogisExpr", this.manageMtrLogisExpr, manageMtrLogisExpr));
   }

   /**
    * 관리비_수식_원재료관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MANAGE_MTRDUTIE_EXPR = "manageMtrdutieExpr";
   static int MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT = -1;
   java.lang.String manageMtrdutieExpr = "0";
   /**
    * 관리비_수식_원재료관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getManageMtrdutieExpr() {
      return manageMtrdutieExpr;
   }
   /**
    * 관리비_수식_원재료관세
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setManageMtrdutieExpr(java.lang.String manageMtrdutieExpr) throws wt.util.WTPropertyVetoException {
      manageMtrdutieExprValidate(manageMtrdutieExpr);
      this.manageMtrdutieExpr = manageMtrdutieExpr;
   }
   void manageMtrdutieExprValidate(java.lang.String manageMtrdutieExpr) throws wt.util.WTPropertyVetoException {
      if (MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT < 1) {
         try { MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("manageMtrdutieExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT = 200; }
      }
      if (manageMtrdutieExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(manageMtrdutieExpr.toString(), MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "manageMtrdutieExpr"), java.lang.String.valueOf(java.lang.Math.min(MANAGE_MTRDUTIE_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "manageMtrdutieExpr", this.manageMtrdutieExpr, manageMtrdutieExpr));
   }

   /**
    * 원재료_pallet/Container
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String M_PALLET_CONTAINER = "m_pallet_container";
   static int M_PALLET_CONTAINER_UPPER_LIMIT = -1;
   java.lang.String m_pallet_container = "0";
   /**
    * 원재료_pallet/Container
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getM_pallet_container() {
      return m_pallet_container;
   }
   /**
    * 원재료_pallet/Container
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setM_pallet_container(java.lang.String m_pallet_container) throws wt.util.WTPropertyVetoException {
      m_pallet_containerValidate(m_pallet_container);
      this.m_pallet_container = m_pallet_container;
   }
   void m_pallet_containerValidate(java.lang.String m_pallet_container) throws wt.util.WTPropertyVetoException {
      if (M_PALLET_CONTAINER_UPPER_LIMIT < 1) {
         try { M_PALLET_CONTAINER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("m_pallet_container").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { M_PALLET_CONTAINER_UPPER_LIMIT = 200; }
      }
      if (m_pallet_container != null && !wt.fc.PersistenceHelper.checkStoredLength(m_pallet_container.toString(), M_PALLET_CONTAINER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "m_pallet_container"), java.lang.String.valueOf(java.lang.Math.min(M_PALLET_CONTAINER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "m_pallet_container", this.m_pallet_container, m_pallet_container));
   }

   /**
    * 부품_pallet/Container
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String P_PALLET_CONTAINER = "p_pallet_container";
   static int P_PALLET_CONTAINER_UPPER_LIMIT = -1;
   java.lang.String p_pallet_container = "0";
   /**
    * 부품_pallet/Container
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getP_pallet_container() {
      return p_pallet_container;
   }
   /**
    * 부품_pallet/Container
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setP_pallet_container(java.lang.String p_pallet_container) throws wt.util.WTPropertyVetoException {
      p_pallet_containerValidate(p_pallet_container);
      this.p_pallet_container = p_pallet_container;
   }
   void p_pallet_containerValidate(java.lang.String p_pallet_container) throws wt.util.WTPropertyVetoException {
      if (P_PALLET_CONTAINER_UPPER_LIMIT < 1) {
         try { P_PALLET_CONTAINER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("p_pallet_container").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { P_PALLET_CONTAINER_UPPER_LIMIT = 200; }
      }
      if (p_pallet_container != null && !wt.fc.PersistenceHelper.checkStoredLength(p_pallet_container.toString(), P_PALLET_CONTAINER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "p_pallet_container"), java.lang.String.valueOf(java.lang.Math.min(P_PALLET_CONTAINER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "p_pallet_container", this.p_pallet_container, p_pallet_container));
   }

   /**
    * 납입처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DELIVER_NAME = "deliverName";
   static int DELIVER_NAME_UPPER_LIMIT = -1;
   java.lang.String deliverName;
   /**
    * 납입처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDeliverName() {
      return deliverName;
   }
   /**
    * 납입처
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDeliverName(java.lang.String deliverName) throws wt.util.WTPropertyVetoException {
      deliverNameValidate(deliverName);
      this.deliverName = deliverName;
   }
   void deliverNameValidate(java.lang.String deliverName) throws wt.util.WTPropertyVetoException {
      if (DELIVER_NAME_UPPER_LIMIT < 1) {
         try { DELIVER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("deliverName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DELIVER_NAME_UPPER_LIMIT = 200; }
      }
      if (deliverName != null && !wt.fc.PersistenceHelper.checkStoredLength(deliverName.toString(), DELIVER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "deliverName"), java.lang.String.valueOf(java.lang.Math.min(DELIVER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "deliverName", this.deliverName, deliverName));
   }

   /**
    * C/R적용율(1회성-최초 원가산출시에만 사용됨)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DISPOSABLE_CR = "disposableCr";
   static int DISPOSABLE_CR_UPPER_LIMIT = -1;
   java.lang.String disposableCr = "0";
   /**
    * C/R적용율(1회성-최초 원가산출시에만 사용됨)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDisposableCr() {
      return disposableCr;
   }
   /**
    * C/R적용율(1회성-최초 원가산출시에만 사용됨)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDisposableCr(java.lang.String disposableCr) throws wt.util.WTPropertyVetoException {
      disposableCrValidate(disposableCr);
      this.disposableCr = disposableCr;
   }
   void disposableCrValidate(java.lang.String disposableCr) throws wt.util.WTPropertyVetoException {
      if (DISPOSABLE_CR_UPPER_LIMIT < 1) {
         try { DISPOSABLE_CR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("disposableCr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DISPOSABLE_CR_UPPER_LIMIT = 200; }
      }
      if (disposableCr != null && !wt.fc.PersistenceHelper.checkStoredLength(disposableCr.toString(), DISPOSABLE_CR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "disposableCr"), java.lang.String.valueOf(java.lang.Math.min(DISPOSABLE_CR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "disposableCr", this.disposableCr, disposableCr));
   }

   /**
    * C/R적용년수(1회성-최초 원가산출시에만 사용됨)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String DISPOSABLE_APPLY_YEAR = "disposableApplyYear";
   static int DISPOSABLE_APPLY_YEAR_UPPER_LIMIT = -1;
   java.lang.String disposableApplyYear = "0";
   /**
    * C/R적용년수(1회성-최초 원가산출시에만 사용됨)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getDisposableApplyYear() {
      return disposableApplyYear;
   }
   /**
    * C/R적용년수(1회성-최초 원가산출시에만 사용됨)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setDisposableApplyYear(java.lang.String disposableApplyYear) throws wt.util.WTPropertyVetoException {
      disposableApplyYearValidate(disposableApplyYear);
      this.disposableApplyYear = disposableApplyYear;
   }
   void disposableApplyYearValidate(java.lang.String disposableApplyYear) throws wt.util.WTPropertyVetoException {
      if (DISPOSABLE_APPLY_YEAR_UPPER_LIMIT < 1) {
         try { DISPOSABLE_APPLY_YEAR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("disposableApplyYear").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DISPOSABLE_APPLY_YEAR_UPPER_LIMIT = 200; }
      }
      if (disposableApplyYear != null && !wt.fc.PersistenceHelper.checkStoredLength(disposableApplyYear.toString(), DISPOSABLE_APPLY_YEAR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "disposableApplyYear"), java.lang.String.valueOf(java.lang.Math.min(DISPOSABLE_APPLY_YEAR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "disposableApplyYear", this.disposableApplyYear, disposableApplyYear));
   }

   /**
    * 원가산출 기준정보 Lock
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String ATTR_LOCKED = "attrLocked";
   static int ATTR_LOCKED_UPPER_LIMIT = -1;
   java.lang.String attrLocked;
   /**
    * 원가산출 기준정보 Lock
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getAttrLocked() {
      return attrLocked;
   }
   /**
    * 원가산출 기준정보 Lock
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setAttrLocked(java.lang.String attrLocked) throws wt.util.WTPropertyVetoException {
      attrLockedValidate(attrLocked);
      this.attrLocked = attrLocked;
   }
   void attrLockedValidate(java.lang.String attrLocked) throws wt.util.WTPropertyVetoException {
      if (ATTR_LOCKED_UPPER_LIMIT < 1) {
         try { ATTR_LOCKED_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attrLocked").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR_LOCKED_UPPER_LIMIT = 4000; }
      }
      if (attrLocked != null && !wt.fc.PersistenceHelper.checkStoredLength(attrLocked.toString(), ATTR_LOCKED_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attrLocked"), java.lang.String.valueOf(java.lang.Math.min(ATTR_LOCKED_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attrLocked", this.attrLocked, attrLocked));
   }

   /**
    * 판가구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SALES_TARGET_GB = "salesTargetGb";
   static int SALES_TARGET_GB_UPPER_LIMIT = -1;
   java.lang.String salesTargetGb;
   /**
    * 판가구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSalesTargetGb() {
      return salesTargetGb;
   }
   /**
    * 판가구분
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSalesTargetGb(java.lang.String salesTargetGb) throws wt.util.WTPropertyVetoException {
      salesTargetGbValidate(salesTargetGb);
      this.salesTargetGb = salesTargetGb;
   }
   void salesTargetGbValidate(java.lang.String salesTargetGb) throws wt.util.WTPropertyVetoException {
      if (SALES_TARGET_GB_UPPER_LIMIT < 1) {
         try { SALES_TARGET_GB_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("salesTargetGb").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SALES_TARGET_GB_UPPER_LIMIT = 200; }
      }
      if (salesTargetGb != null && !wt.fc.PersistenceHelper.checkStoredLength(salesTargetGb.toString(), SALES_TARGET_GB_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "salesTargetGb"), java.lang.String.valueOf(java.lang.Math.min(SALES_TARGET_GB_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "salesTargetGb", this.salesTargetGb, salesTargetGb));
   }

   /**
    * 불량률 제외 부품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SUB_COST_DEFECT_TOTAL = "subCostDefectTotal";
   static int SUB_COST_DEFECT_TOTAL_UPPER_LIMIT = -1;
   java.lang.String subCostDefectTotal = "0";
   /**
    * 불량률 제외 부품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getSubCostDefectTotal() {
      return subCostDefectTotal;
   }
   /**
    * 불량률 제외 부품합계
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSubCostDefectTotal(java.lang.String subCostDefectTotal) throws wt.util.WTPropertyVetoException {
      subCostDefectTotalValidate(subCostDefectTotal);
      this.subCostDefectTotal = subCostDefectTotal;
   }
   void subCostDefectTotalValidate(java.lang.String subCostDefectTotal) throws wt.util.WTPropertyVetoException {
      if (SUB_COST_DEFECT_TOTAL_UPPER_LIMIT < 1) {
         try { SUB_COST_DEFECT_TOTAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("subCostDefectTotal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUB_COST_DEFECT_TOTAL_UPPER_LIMIT = 200; }
      }
      if (subCostDefectTotal != null && !wt.fc.PersistenceHelper.checkStoredLength(subCostDefectTotal.toString(), SUB_COST_DEFECT_TOTAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subCostDefectTotal"), java.lang.String.valueOf(java.lang.Math.min(SUB_COST_DEFECT_TOTAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "subCostDefectTotal", this.subCostDefectTotal, subCostDefectTotal));
   }

   /**
    * 법인간마진율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CORPORATE_MARGIN_RATE = "corporateMarginRate";
   static int CORPORATE_MARGIN_RATE_UPPER_LIMIT = -1;
   java.lang.String corporateMarginRate = "0";
   /**
    * 법인간마진율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCorporateMarginRate() {
      return corporateMarginRate;
   }
   /**
    * 법인간마진율
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCorporateMarginRate(java.lang.String corporateMarginRate) throws wt.util.WTPropertyVetoException {
      corporateMarginRateValidate(corporateMarginRate);
      this.corporateMarginRate = corporateMarginRate;
   }
   void corporateMarginRateValidate(java.lang.String corporateMarginRate) throws wt.util.WTPropertyVetoException {
      if (CORPORATE_MARGIN_RATE_UPPER_LIMIT < 1) {
         try { CORPORATE_MARGIN_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("corporateMarginRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CORPORATE_MARGIN_RATE_UPPER_LIMIT = 200; }
      }
      if (corporateMarginRate != null && !wt.fc.PersistenceHelper.checkStoredLength(corporateMarginRate.toString(), CORPORATE_MARGIN_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "corporateMarginRate"), java.lang.String.valueOf(java.lang.Math.min(CORPORATE_MARGIN_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "corporateMarginRate", this.corporateMarginRate, corporateMarginRate));
   }

   /**
    * 법인간마진비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String CORPORATE_MARGIN_COST_EXPR = "corporateMarginCostExpr";
   static int CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT = -1;
   java.lang.String corporateMarginCostExpr = "0";
   /**
    * 법인간마진비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getCorporateMarginCostExpr() {
      return corporateMarginCostExpr;
   }
   /**
    * 법인간마진비용
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setCorporateMarginCostExpr(java.lang.String corporateMarginCostExpr) throws wt.util.WTPropertyVetoException {
      corporateMarginCostExprValidate(corporateMarginCostExpr);
      this.corporateMarginCostExpr = corporateMarginCostExpr;
   }
   void corporateMarginCostExprValidate(java.lang.String corporateMarginCostExpr) throws wt.util.WTPropertyVetoException {
      if (CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT < 1) {
         try { CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("corporateMarginCostExpr").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT = 200; }
      }
      if (corporateMarginCostExpr != null && !wt.fc.PersistenceHelper.checkStoredLength(corporateMarginCostExpr.toString(), CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "corporateMarginCostExpr"), java.lang.String.valueOf(java.lang.Math.min(CORPORATE_MARGIN_COST_EXPR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "corporateMarginCostExpr", this.corporateMarginCostExpr, corporateMarginCostExpr));
   }

   /**
    * 간접인건비비율(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String INDIRECT_LABOUR_RATE = "indirectLabourRate";
   static int INDIRECT_LABOUR_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectLabourRate = "0";
   /**
    * 간접인건비비율(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getIndirectLabourRate() {
      return indirectLabourRate;
   }
   /**
    * 간접인건비비율(노무비)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setIndirectLabourRate(java.lang.String indirectLabourRate) throws wt.util.WTPropertyVetoException {
      indirectLabourRateValidate(indirectLabourRate);
      this.indirectLabourRate = indirectLabourRate;
   }
   void indirectLabourRateValidate(java.lang.String indirectLabourRate) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_LABOUR_RATE_UPPER_LIMIT < 1) {
         try { INDIRECT_LABOUR_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectLabourRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_LABOUR_RATE_UPPER_LIMIT = 200; }
      }
      if (indirectLabourRate != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectLabourRate.toString(), INDIRECT_LABOUR_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectLabourRate"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_LABOUR_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectLabourRate", this.indirectLabourRate, indirectLabourRate));
   }

   /**
    * 간접인건비비율(R&D)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String INDIRECT_RND_RATE = "indirectRndRate";
   static int INDIRECT_RND_RATE_UPPER_LIMIT = -1;
   java.lang.String indirectRndRate = "0";
   /**
    * 간접인건비비율(R&D)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.String getIndirectRndRate() {
      return indirectRndRate;
   }
   /**
    * 간접인건비비율(R&D)
    *
    * @see ext.ket.cost.entity.CostPart
    */
   public void setIndirectRndRate(java.lang.String indirectRndRate) throws wt.util.WTPropertyVetoException {
      indirectRndRateValidate(indirectRndRate);
      this.indirectRndRate = indirectRndRate;
   }
   void indirectRndRateValidate(java.lang.String indirectRndRate) throws wt.util.WTPropertyVetoException {
      if (INDIRECT_RND_RATE_UPPER_LIMIT < 1) {
         try { INDIRECT_RND_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("indirectRndRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INDIRECT_RND_RATE_UPPER_LIMIT = 200; }
      }
      if (indirectRndRate != null && !wt.fc.PersistenceHelper.checkStoredLength(indirectRndRate.toString(), INDIRECT_RND_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "indirectRndRate"), java.lang.String.valueOf(java.lang.Math.min(INDIRECT_RND_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "indirectRndRate", this.indirectRndRate, indirectRndRate));
   }

   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String SORT_LOCATION = "sortLocation";
   java.lang.Integer sortLocation;
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public java.lang.Integer getSortLocation() {
      return sortLocation;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public void setSortLocation(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
      sortLocationValidate(sortLocation);
      this.sortLocation = sortLocation;
   }
   void sortLocationValidate(java.lang.Integer sortLocation) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MASTER = "master";
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String MASTER_REFERENCE = "masterReference";
   wt.fc.ObjectReference masterReference;
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public ext.ket.cost.entity.ProductMaster getMaster() {
      return (masterReference != null) ? (ext.ket.cost.entity.ProductMaster) masterReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public wt.fc.ObjectReference getMasterReference() {
      return masterReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMaster(ext.ket.cost.entity.ProductMaster the_master) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMasterReference(the_master == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.ProductMaster) the_master));
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public void setMasterReference(wt.fc.ObjectReference the_masterReference) throws wt.util.WTPropertyVetoException {
      masterReferenceValidate(the_masterReference);
      masterReference = (wt.fc.ObjectReference) the_masterReference;
   }
   void masterReferenceValidate(wt.fc.ObjectReference the_masterReference) throws wt.util.WTPropertyVetoException {
      if (the_masterReference != null && the_masterReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.ProductMaster.class.isAssignableFrom(the_masterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "masterReference", this.masterReference, masterReference));
   }

   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public e3ps.project.E3PSProjectMaster getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProjectMaster) projectReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public void setProject(e3ps.project.E3PSProjectMaster the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProjectMaster) the_project));
   }
   /**
    * @see ext.ket.cost.entity.CostPart
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
            !e3ps.project.E3PSProjectMaster.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
   }

   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String REPORT = "report";
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public static final java.lang.String REPORT_REFERENCE = "reportReference";
   wt.fc.ObjectReference reportReference;
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public ext.ket.cost.entity.CostReport getReport() {
      return (reportReference != null) ? (ext.ket.cost.entity.CostReport) reportReference.getObject() : null;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public wt.fc.ObjectReference getReportReference() {
      return reportReference;
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public void setReport(ext.ket.cost.entity.CostReport the_report) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setReportReference(the_report == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.cost.entity.CostReport) the_report));
   }
   /**
    * @see ext.ket.cost.entity.CostPart
    */
   public void setReportReference(wt.fc.ObjectReference the_reportReference) throws wt.util.WTPropertyVetoException {
      reportReferenceValidate(the_reportReference);
      reportReference = (wt.fc.ObjectReference) the_reportReference;
   }
   void reportReferenceValidate(wt.fc.ObjectReference the_reportReference) throws wt.util.WTPropertyVetoException {
      if (the_reportReference != null && the_reportReference.getReferencedClass() != null &&
            !ext.ket.cost.entity.CostReport.class.isAssignableFrom(the_reportReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reportReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "reportReference", this.reportReference, reportReference));
   }

   wt.fc.ObjectReference parentReference;
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent() {
      return (parentReference != null) ? (e3ps.common.impl.Tree) parentReference.getObject() : null;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference() {
      return parentReference;
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setParentReference(the_parent == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.impl.Tree) the_parent));
   }
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParentReference(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      parentReferenceValidate(the_parentReference);
      parentReference = (wt.fc.ObjectReference) the_parentReference;
   }
   void parentReferenceValidate(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException {
      if (the_parentReference == null || the_parentReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference") },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
      if (the_parentReference != null && the_parentReference.getReferencedClass() != null &&
            !e3ps.common.impl.Tree.class.isAssignableFrom(the_parentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "parentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "parentReference", this.parentReference, parentReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 37715106356876868L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( addManHourClb_1 );
      output.writeObject( addManHourClb_2 );
      output.writeObject( addManHourCt_1 );
      output.writeObject( addManHourCt_2 );
      output.writeObject( addManHourEff_1 );
      output.writeObject( addManHourEff_2 );
      output.writeObject( addManHourLb_1 );
      output.writeObject( addManHourLb_2 );
      output.writeObject( addManHourYear_1 );
      output.writeObject( addManHourYear_2 );
      output.writeObject( apMonetaryUnit );
      output.writeObject( apMonetaryUnitCurrency );
      output.writeObject( apPlatingSpec );
      output.writeObject( apUnitCost );
      output.writeObject( apUnitCostOption );
      output.writeObject( apUnitCostVal );
      output.writeObject( assyLossExpr );
      output.writeObject( assyLossRate );
      output.writeObject( attrLocked );
      output.writeObject( bomLevel );
      output.writeObject( buyBackIndirectCostRate );
      output.writeObject( calcOptionLabor );
      output.writeObject( calcOptionManage );
      output.writeObject( calcOptionMaterial );
      output.writeObject( calcStdExpense );
      output.writeObject( calcStdLabor );
      output.writeObject( calcStdManage );
      output.writeObject( calcStdMaterial );
      output.writeObject( calcStdOutPut );
      output.writeObject( capa );
      output.writeObject( capaMaxQty );
      output.writeObject( capaNote );
      output.writeObject( capaYear );
      output.writeObject( caseNote );
      output.writeObject( caseOrder );
      output.writeObject( coManageCost );
      output.writeObject( coManageExpr );
      output.writeObject( coManageRate );
      output.writeObject( coManageRateOption );
      output.writeObject( company );
      output.writeObject( corporateMarginCostExpr );
      output.writeObject( corporateMarginRate );
      output.writeObject( costDeliver );
      output.writeObject( ctSPMAssemble );
      output.writeObject( ctSPMMold );
      output.writeObject( cvAssemble );
      output.writeObject( cvMold );
      output.writeObject( defectCostExpr );
      output.writeObject( defectRate );
      output.writeObject( deliverName );
      output.writeObject( devType );
      output.writeObject( directCostExpr );
      output.writeObject( disposableApplyYear );
      output.writeObject( disposableCr );
      output.writeObject( distributionCost );
      output.writeObject( drStep );
      output.writeObject( duty );
      output.writeObject( eaOutput );
      output.writeObject( efficientRate );
      output.writeObject( elecCost );
      output.writeObject( elecCostClimbRate );
      output.writeObject( elecCostExpr );
      output.writeObject( elecCostYear );
      output.writeObject( equipinvestPriceTotal );
      output.writeObject( etcMPFactory );
      output.writeObject( etcMPIC );
      output.writeObject( etcMPQMax );
      output.writeObject( etcMPQTotal );
      output.writeObject( etcNIC );
      output.writeObject( etcNPFactory );
      output.writeObject( etcNPay );
      output.writeObject( etcReducePrice );
      output.writeObject( etcinvestPriceTotal );
      output.writeObject( expenseExpr );
      output.writeObject( expenseTotal );
      output.writeObject( facMPFactory );
      output.writeObject( facMPIC );
      output.writeObject( facMPICMUnit );
      output.writeObject( facMPICMUnitCurrency );
      output.writeObject( facMPQMax );
      output.writeObject( facMPQTotal );
      output.writeObject( facMftDivision );
      output.writeObject( facNFactory );
      output.writeObject( facNIC );
      output.writeObject( facNICMUnit );
      output.writeObject( facNICMUnitCurrency );
      output.writeObject( facNPay );
      output.writeObject( facNPayMUnit );
      output.writeObject( facNPayMUnitCurrency );
      output.writeObject( facOrder );
      output.writeObject( facReduceCtSpm );
      output.writeObject( facReducePrice );
      output.writeObject( facilities );
      output.writeObject( formulaVersion );
      output.writeObject( freeRawMaterialCostTotal );
      output.writeObject( inDirectCost );
      output.writeObject( inDirectCost2Expr );
      output.writeObject( inDirectCostExpr );
      output.writeObject( indirectCostRate );
      output.writeObject( indirectLabourRate );
      output.writeObject( indirectRndRate );
      output.writeObject( initFlag );
      output.writeObject( itemName );
      output.writeObject( laborCostClimbRate );
      output.writeObject( laborCostExpr );
      output.writeObject( laborCostRate );
      output.writeObject( laborCostTotal );
      output.writeObject( laborCostYear );
      output.writeObject( laborExpenseAdd1Expr );
      output.writeObject( laborExpenseAdd2Expr );
      output.writeObject( laborRateExpr );
      output.writeBoolean( lastest );
      output.writeObject( m_pallet_container );
      output.writeObject( machineDpcCost );
      output.writeObject( machineDpcCostExpr );
      output.writeObject( machineReduceCost );
      output.writeObject( manageCostExpr );
      output.writeObject( manageCostTotal );
      output.writeObject( manageMtrLogisExpr );
      output.writeObject( manageMtrdutieExpr );
      output.writeObject( masterReference );
      output.writeObject( materialCostExpr );
      output.writeObject( materialCostTotal );
      output.writeObject( metalCuttingCost );
      output.writeObject( metalDeTinCost );
      output.writeObject( metalImportance );
      output.writeObject( metalLmeCost );
      output.writeObject( metalLmeStd );
      output.writeObject( metalPartNo );
      output.writeObject( metalPlateCost );
      output.writeObject( metalScenario );
      output.writeObject( metalScrapCost );
      output.writeObject( metalScrapRecycle );
      output.writeObject( metalTollPrcCost );
      output.writeObject( mfcCostExpr );
      output.writeObject( mfcCostTotal );
      output.writeObject( mftFactory1 );
      output.writeObject( mftFactory2 );
      output.writeObject( moldDieNo );
      output.writeObject( moldMPFactory );
      output.writeObject( moldMPIC );
      output.writeObject( moldMPICMUnit );
      output.writeObject( moldMPICMUnitCurrency );
      output.writeObject( moldMPQMax );
      output.writeObject( moldMPQTotal );
      output.writeObject( moldMaintenance );
      output.writeObject( moldMftDivision );
      output.writeObject( moldNFactory );
      output.writeObject( moldNIC );
      output.writeObject( moldNICMUnit );
      output.writeObject( moldNICMUnitCurrency );
      output.writeObject( moldNPay );
      output.writeObject( moldNPayMUnit );
      output.writeObject( moldNPayMUnitCurrency );
      output.writeObject( moldOrder );
      output.writeObject( moldPrepareExpr );
      output.writeObject( moldProductAssyExpr );
      output.writeObject( moldReadyTime );
      output.writeObject( moldReducePrice );
      output.writeObject( moldStructure );
      output.writeObject( moldinvestPriceTotal );
      output.writeObject( mtrLtCost );
      output.writeObject( mtrLtCostExpr );
      output.writeObject( mtrLtRate );
      output.writeObject( mtrLtRateExpr );
      output.writeObject( mtrManageExpr );
      output.writeObject( mtrManageRate );
      output.writeObject( mtrMetalPrice );
      output.writeObject( mtrSujiPrice );
      output.writeObject( outMonetaryUnit );
      output.writeObject( outMonetaryUnitCurrency );
      output.writeObject( outUnitCost );
      output.writeObject( outUnitCostVal );
      output.writeObject( outputExpr );
      output.writeObject( pMonetaryUnit );
      output.writeObject( pMonetaryUnitCurrency );
      output.writeObject( pUnitCost );
      output.writeObject( p_pallet_container );
      output.writeObject( packBoxUnit );
      output.writeObject( packPalletUnit );
      output.writeObject( packUnitPriceOption );
      output.writeObject( packUnitPriceSum );
      output.writeObject( parentReference );
      output.writeObject( partLtCost );
      output.writeObject( partLtRate );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( partTotalCost );
      output.writeObject( partType );
      output.writeObject( payCostLtExpr );
      output.writeObject( payLtCost );
      output.writeObject( payPlace );
      output.writeObject( payRateLt );
      output.writeObject( payRateLtExpr );
      output.writeObject( prePlatingCost );
      output.writeObject( prePlatingSpec );
      output.writeObject( prePlatingUnit );
      output.writeObject( prePlatingUnitCurrency );
      output.writeObject( pressinvestPriceTotal );
      output.writeObject( productCostTotal );
      output.writeObject( productHaveMonth );
      output.writeObject( productLossExpr );
      output.writeObject( productLossRate );
      output.writeObject( productNInvestTotal );
      output.writeObject( productWeight );
      output.writeObject( profitRateExpr );
      output.writeObject( projectReference );
      output.writeObject( purchaseinvestPriceTotal );
      output.writeObject( quantityAvg );
      output.writeObject( quantityMax );
      output.writeObject( quantityTotal );
      output.writeObject( rMatNMetalName );
      output.writeObject( rMatNMetalP );
      output.writeObject( rMatNMetalT );
      output.writeObject( rMatNMetalW );
      output.writeObject( rawMaterialCostExpr );
      output.writeObject( realPartNo );
      output.writeObject( reduceCostExpr );
      output.writeObject( reduceCostTotal );
      output.writeObject( reportReference );
      output.writeObject( reportUS );
      output.writeObject( rnd );
      output.writeObject( rndExpr );
      output.writeObject( rndOption );
      output.writeObject( salesTargetCostExpr );
      output.writeObject( salesTargetGb );
      output.writeObject( scrapSalesCostExpr );
      output.writeObject( scrapWeight );
      output.writeObject( sizeH );
      output.writeObject( sizeL );
      output.writeObject( sizeW );
      output.writeObject( sopYear );
      output.writeObject( sortLocation );
      output.writeObject( subCostAllTotal );
      output.writeObject( subCostAllTotalOption );
      output.writeObject( subCostDefectTotal );
      output.writeObject( subCostExceptTotal );
      output.writeObject( subCostExceptTotalOption );
      output.writeObject( subVersion );
      output.writeObject( sujiColor );
      output.writeObject( sujiGrade );
      output.writeObject( sujiPartName );
      output.writeObject( sujiPartNo );
      output.writeObject( sujiScrapCost );
      output.writeObject( sujiScrapRecycle );
      output.writeObject( tabaryu );
      output.writeObject( tabaryuExpr );
      output.writeObject( thePersistInfo );
      output.writeObject( tos );
      output.writeObject( totalCostExpr );
      output.writeObject( totalWeight );
      output.writeObject( unitManage );
      output.writeObject( us );
      output.writeObject( version );
      output.writeObject( workDay );
      output.writeObject( workHour );
      output.writeObject( workYear );
      output.writeObject( worker );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostPart) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "addManHourClb_1", addManHourClb_1 );
      output.setString( "addManHourClb_2", addManHourClb_2 );
      output.setString( "addManHourCt_1", addManHourCt_1 );
      output.setString( "addManHourCt_2", addManHourCt_2 );
      output.setString( "addManHourEff_1", addManHourEff_1 );
      output.setString( "addManHourEff_2", addManHourEff_2 );
      output.setString( "addManHourLb_1", addManHourLb_1 );
      output.setString( "addManHourLb_2", addManHourLb_2 );
      output.setString( "addManHourYear_1", addManHourYear_1 );
      output.setString( "addManHourYear_2", addManHourYear_2 );
      output.setString( "apMonetaryUnit", apMonetaryUnit );
      output.setString( "apMonetaryUnitCurrency", apMonetaryUnitCurrency );
      output.setString( "apPlatingSpec", apPlatingSpec );
      output.setString( "apUnitCost", apUnitCost );
      output.setString( "apUnitCostOption", apUnitCostOption );
      output.setString( "apUnitCostVal", apUnitCostVal );
      output.setString( "assyLossExpr", assyLossExpr );
      output.setString( "assyLossRate", assyLossRate );
      output.setString( "attrLocked", attrLocked );
      output.setString( "bomLevel", bomLevel );
      output.setString( "buyBackIndirectCostRate", buyBackIndirectCostRate );
      output.setString( "calcOptionLabor", calcOptionLabor );
      output.setString( "calcOptionManage", calcOptionManage );
      output.setString( "calcOptionMaterial", calcOptionMaterial );
      output.setString( "calcStdExpense", calcStdExpense );
      output.setString( "calcStdLabor", calcStdLabor );
      output.setString( "calcStdManage", calcStdManage );
      output.setString( "calcStdMaterial", calcStdMaterial );
      output.setString( "calcStdOutPut", calcStdOutPut );
      output.setString( "capa", capa );
      output.setString( "capaMaxQty", capaMaxQty );
      output.setString( "capaNote", capaNote );
      output.setString( "capaYear", capaYear );
      output.setString( "caseNote", caseNote );
      output.setIntObject( "caseOrder", caseOrder );
      output.setString( "coManageCost", coManageCost );
      output.setString( "coManageExpr", coManageExpr );
      output.setString( "coManageRate", coManageRate );
      output.setString( "coManageRateOption", coManageRateOption );
      output.setString( "company", company );
      output.setString( "corporateMarginCostExpr", corporateMarginCostExpr );
      output.setString( "corporateMarginRate", corporateMarginRate );
      output.setString( "costDeliver", costDeliver );
      output.setString( "ctSPMAssemble", ctSPMAssemble );
      output.setString( "ctSPMMold", ctSPMMold );
      output.setString( "cvAssemble", cvAssemble );
      output.setString( "cvMold", cvMold );
      output.setString( "defectCostExpr", defectCostExpr );
      output.setString( "defectRate", defectRate );
      output.setString( "deliverName", deliverName );
      output.setString( "devType", devType );
      output.setString( "directCostExpr", directCostExpr );
      output.setString( "disposableApplyYear", disposableApplyYear );
      output.setString( "disposableCr", disposableCr );
      output.setString( "distributionCost", distributionCost );
      output.setString( "drStep", drStep );
      output.setString( "duty", duty );
      output.setString( "eaOutput", eaOutput );
      output.setString( "efficientRate", efficientRate );
      output.setString( "elecCost", elecCost );
      output.setString( "elecCostClimbRate", elecCostClimbRate );
      output.setString( "elecCostExpr", elecCostExpr );
      output.setString( "elecCostYear", elecCostYear );
      output.setString( "equipinvestPriceTotal", equipinvestPriceTotal );
      output.setString( "etcMPFactory", etcMPFactory );
      output.setString( "etcMPIC", etcMPIC );
      output.setString( "etcMPQMax", etcMPQMax );
      output.setString( "etcMPQTotal", etcMPQTotal );
      output.setString( "etcNIC", etcNIC );
      output.setString( "etcNPFactory", etcNPFactory );
      output.setString( "etcNPay", etcNPay );
      output.setString( "etcReducePrice", etcReducePrice );
      output.setString( "etcinvestPriceTotal", etcinvestPriceTotal );
      output.setString( "expenseExpr", expenseExpr );
      output.setString( "expenseTotal", expenseTotal );
      output.setString( "facMPFactory", facMPFactory );
      output.setString( "facMPIC", facMPIC );
      output.setString( "facMPICMUnit", facMPICMUnit );
      output.setString( "facMPICMUnitCurrency", facMPICMUnitCurrency );
      output.setString( "facMPQMax", facMPQMax );
      output.setString( "facMPQTotal", facMPQTotal );
      output.setString( "facMftDivision", facMftDivision );
      output.setString( "facNFactory", facNFactory );
      output.setString( "facNIC", facNIC );
      output.setString( "facNICMUnit", facNICMUnit );
      output.setString( "facNICMUnitCurrency", facNICMUnitCurrency );
      output.setString( "facNPay", facNPay );
      output.setString( "facNPayMUnit", facNPayMUnit );
      output.setString( "facNPayMUnitCurrency", facNPayMUnitCurrency );
      output.setString( "facOrder", facOrder );
      output.setString( "facReduceCtSpm", facReduceCtSpm );
      output.setString( "facReducePrice", facReducePrice );
      output.setString( "facilities", facilities );
      output.setIntObject( "formulaVersion", formulaVersion );
      output.setString( "freeRawMaterialCostTotal", freeRawMaterialCostTotal );
      output.setString( "inDirectCost", inDirectCost );
      output.setString( "inDirectCost2Expr", inDirectCost2Expr );
      output.setString( "inDirectCostExpr", inDirectCostExpr );
      output.setString( "indirectCostRate", indirectCostRate );
      output.setString( "indirectLabourRate", indirectLabourRate );
      output.setString( "indirectRndRate", indirectRndRate );
      output.setString( "initFlag", initFlag );
      output.setString( "itemName", itemName );
      output.setString( "laborCostClimbRate", laborCostClimbRate );
      output.setString( "laborCostExpr", laborCostExpr );
      output.setString( "laborCostRate", laborCostRate );
      output.setString( "laborCostTotal", laborCostTotal );
      output.setString( "laborCostYear", laborCostYear );
      output.setString( "laborExpenseAdd1Expr", laborExpenseAdd1Expr );
      output.setString( "laborExpenseAdd2Expr", laborExpenseAdd2Expr );
      output.setString( "laborRateExpr", laborRateExpr );
      output.setBoolean( "lastest", lastest );
      output.setString( "m_pallet_container", m_pallet_container );
      output.setString( "machineDpcCost", machineDpcCost );
      output.setString( "machineDpcCostExpr", machineDpcCostExpr );
      output.setString( "machineReduceCost", machineReduceCost );
      output.setString( "manageCostExpr", manageCostExpr );
      output.setString( "manageCostTotal", manageCostTotal );
      output.setString( "manageMtrLogisExpr", manageMtrLogisExpr );
      output.setString( "manageMtrdutieExpr", manageMtrdutieExpr );
      output.writeObject( "masterReference", masterReference, wt.fc.ObjectReference.class, true );
      output.setString( "materialCostExpr", materialCostExpr );
      output.setString( "materialCostTotal", materialCostTotal );
      output.setString( "metalCuttingCost", metalCuttingCost );
      output.setString( "metalDeTinCost", metalDeTinCost );
      output.setString( "metalImportance", metalImportance );
      output.setString( "metalLmeCost", metalLmeCost );
      output.setString( "metalLmeStd", metalLmeStd );
      output.setString( "metalPartNo", metalPartNo );
      output.setString( "metalPlateCost", metalPlateCost );
      output.setString( "metalScenario", metalScenario );
      output.setString( "metalScrapCost", metalScrapCost );
      output.setString( "metalScrapRecycle", metalScrapRecycle );
      output.setString( "metalTollPrcCost", metalTollPrcCost );
      output.setString( "mfcCostExpr", mfcCostExpr );
      output.setString( "mfcCostTotal", mfcCostTotal );
      output.setString( "mftFactory1", mftFactory1 );
      output.setString( "mftFactory2", mftFactory2 );
      output.setString( "moldDieNo", moldDieNo );
      output.setString( "moldMPFactory", moldMPFactory );
      output.setString( "moldMPIC", moldMPIC );
      output.setString( "moldMPICMUnit", moldMPICMUnit );
      output.setString( "moldMPICMUnitCurrency", moldMPICMUnitCurrency );
      output.setString( "moldMPQMax", moldMPQMax );
      output.setString( "moldMPQTotal", moldMPQTotal );
      output.setString( "moldMaintenance", moldMaintenance );
      output.setString( "moldMftDivision", moldMftDivision );
      output.setString( "moldNFactory", moldNFactory );
      output.setString( "moldNIC", moldNIC );
      output.setString( "moldNICMUnit", moldNICMUnit );
      output.setString( "moldNICMUnitCurrency", moldNICMUnitCurrency );
      output.setString( "moldNPay", moldNPay );
      output.setString( "moldNPayMUnit", moldNPayMUnit );
      output.setString( "moldNPayMUnitCurrency", moldNPayMUnitCurrency );
      output.setString( "moldOrder", moldOrder );
      output.setString( "moldPrepareExpr", moldPrepareExpr );
      output.setString( "moldProductAssyExpr", moldProductAssyExpr );
      output.setString( "moldReadyTime", moldReadyTime );
      output.setString( "moldReducePrice", moldReducePrice );
      output.setString( "moldStructure", moldStructure );
      output.setString( "moldinvestPriceTotal", moldinvestPriceTotal );
      output.setString( "mtrLtCost", mtrLtCost );
      output.setString( "mtrLtCostExpr", mtrLtCostExpr );
      output.setString( "mtrLtRate", mtrLtRate );
      output.setString( "mtrLtRateExpr", mtrLtRateExpr );
      output.setString( "mtrManageExpr", mtrManageExpr );
      output.setString( "mtrManageRate", mtrManageRate );
      output.setString( "mtrMetalPrice", mtrMetalPrice );
      output.setString( "mtrSujiPrice", mtrSujiPrice );
      output.setString( "outMonetaryUnit", outMonetaryUnit );
      output.setString( "outMonetaryUnitCurrency", outMonetaryUnitCurrency );
      output.setString( "outUnitCost", outUnitCost );
      output.setString( "outUnitCostVal", outUnitCostVal );
      output.setString( "outputExpr", outputExpr );
      output.setString( "pMonetaryUnit", pMonetaryUnit );
      output.setString( "pMonetaryUnitCurrency", pMonetaryUnitCurrency );
      output.setString( "pUnitCost", pUnitCost );
      output.setString( "p_pallet_container", p_pallet_container );
      output.setString( "packBoxUnit", packBoxUnit );
      output.setString( "packPalletUnit", packPalletUnit );
      output.setString( "packUnitPriceOption", packUnitPriceOption );
      output.setString( "packUnitPriceSum", packUnitPriceSum );
      output.writeObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      output.setString( "partLtCost", partLtCost );
      output.setString( "partLtRate", partLtRate );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "partTotalCost", partTotalCost );
      output.setString( "partType", partType );
      output.setString( "payCostLtExpr", payCostLtExpr );
      output.setString( "payLtCost", payLtCost );
      output.setString( "payPlace", payPlace );
      output.setString( "payRateLt", payRateLt );
      output.setString( "payRateLtExpr", payRateLtExpr );
      output.setString( "prePlatingCost", prePlatingCost );
      output.setString( "prePlatingSpec", prePlatingSpec );
      output.setString( "prePlatingUnit", prePlatingUnit );
      output.setString( "prePlatingUnitCurrency", prePlatingUnitCurrency );
      output.setString( "pressinvestPriceTotal", pressinvestPriceTotal );
      output.setString( "productCostTotal", productCostTotal );
      output.setString( "productHaveMonth", productHaveMonth );
      output.setString( "productLossExpr", productLossExpr );
      output.setString( "productLossRate", productLossRate );
      output.setString( "productNInvestTotal", productNInvestTotal );
      output.setString( "productWeight", productWeight );
      output.setString( "profitRateExpr", profitRateExpr );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "purchaseinvestPriceTotal", purchaseinvestPriceTotal );
      output.setString( "quantityAvg", quantityAvg );
      output.setString( "quantityMax", quantityMax );
      output.setString( "quantityTotal", quantityTotal );
      output.setString( "rMatNMetalName", rMatNMetalName );
      output.setString( "rMatNMetalP", rMatNMetalP );
      output.setString( "rMatNMetalT", rMatNMetalT );
      output.setString( "rMatNMetalW", rMatNMetalW );
      output.setString( "rawMaterialCostExpr", rawMaterialCostExpr );
      output.setString( "realPartNo", realPartNo );
      output.setString( "reduceCostExpr", reduceCostExpr );
      output.setString( "reduceCostTotal", reduceCostTotal );
      output.writeObject( "reportReference", reportReference, wt.fc.ObjectReference.class, true );
      output.setString( "reportUS", reportUS );
      output.setString( "rnd", rnd );
      output.setString( "rndExpr", rndExpr );
      output.setString( "rndOption", rndOption );
      output.setString( "salesTargetCostExpr", salesTargetCostExpr );
      output.setString( "salesTargetGb", salesTargetGb );
      output.setString( "scrapSalesCostExpr", scrapSalesCostExpr );
      output.setString( "scrapWeight", scrapWeight );
      output.setString( "sizeH", sizeH );
      output.setString( "sizeL", sizeL );
      output.setString( "sizeW", sizeW );
      output.setString( "sopYear", sopYear );
      output.setIntObject( "sortLocation", sortLocation );
      output.setString( "subCostAllTotal", subCostAllTotal );
      output.setString( "subCostAllTotalOption", subCostAllTotalOption );
      output.setString( "subCostDefectTotal", subCostDefectTotal );
      output.setString( "subCostExceptTotal", subCostExceptTotal );
      output.setString( "subCostExceptTotalOption", subCostExceptTotalOption );
      output.setIntObject( "subVersion", subVersion );
      output.setString( "sujiColor", sujiColor );
      output.setString( "sujiGrade", sujiGrade );
      output.setString( "sujiPartName", sujiPartName );
      output.setString( "sujiPartNo", sujiPartNo );
      output.setString( "sujiScrapCost", sujiScrapCost );
      output.setString( "sujiScrapRecycle", sujiScrapRecycle );
      output.setString( "tabaryu", tabaryu );
      output.setString( "tabaryuExpr", tabaryuExpr );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "tos", tos );
      output.setString( "totalCostExpr", totalCostExpr );
      output.setString( "totalWeight", totalWeight );
      output.setString( "unitManage", unitManage );
      output.setString( "us", us );
      output.setIntObject( "version", version );
      output.setString( "workDay", workDay );
      output.setString( "workHour", workHour );
      output.setString( "workYear", workYear );
      output.setString( "worker", worker );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      addManHourClb_1 = input.getString( "addManHourClb_1" );
      addManHourClb_2 = input.getString( "addManHourClb_2" );
      addManHourCt_1 = input.getString( "addManHourCt_1" );
      addManHourCt_2 = input.getString( "addManHourCt_2" );
      addManHourEff_1 = input.getString( "addManHourEff_1" );
      addManHourEff_2 = input.getString( "addManHourEff_2" );
      addManHourLb_1 = input.getString( "addManHourLb_1" );
      addManHourLb_2 = input.getString( "addManHourLb_2" );
      addManHourYear_1 = input.getString( "addManHourYear_1" );
      addManHourYear_2 = input.getString( "addManHourYear_2" );
      apMonetaryUnit = input.getString( "apMonetaryUnit" );
      apMonetaryUnitCurrency = input.getString( "apMonetaryUnitCurrency" );
      apPlatingSpec = input.getString( "apPlatingSpec" );
      apUnitCost = input.getString( "apUnitCost" );
      apUnitCostOption = input.getString( "apUnitCostOption" );
      apUnitCostVal = input.getString( "apUnitCostVal" );
      assyLossExpr = input.getString( "assyLossExpr" );
      assyLossRate = input.getString( "assyLossRate" );
      attrLocked = input.getString( "attrLocked" );
      bomLevel = input.getString( "bomLevel" );
      buyBackIndirectCostRate = input.getString( "buyBackIndirectCostRate" );
      calcOptionLabor = input.getString( "calcOptionLabor" );
      calcOptionManage = input.getString( "calcOptionManage" );
      calcOptionMaterial = input.getString( "calcOptionMaterial" );
      calcStdExpense = input.getString( "calcStdExpense" );
      calcStdLabor = input.getString( "calcStdLabor" );
      calcStdManage = input.getString( "calcStdManage" );
      calcStdMaterial = input.getString( "calcStdMaterial" );
      calcStdOutPut = input.getString( "calcStdOutPut" );
      capa = input.getString( "capa" );
      capaMaxQty = input.getString( "capaMaxQty" );
      capaNote = input.getString( "capaNote" );
      capaYear = input.getString( "capaYear" );
      caseNote = input.getString( "caseNote" );
      caseOrder = input.getIntObject( "caseOrder" );
      coManageCost = input.getString( "coManageCost" );
      coManageExpr = input.getString( "coManageExpr" );
      coManageRate = input.getString( "coManageRate" );
      coManageRateOption = input.getString( "coManageRateOption" );
      company = input.getString( "company" );
      corporateMarginCostExpr = input.getString( "corporateMarginCostExpr" );
      corporateMarginRate = input.getString( "corporateMarginRate" );
      costDeliver = input.getString( "costDeliver" );
      ctSPMAssemble = input.getString( "ctSPMAssemble" );
      ctSPMMold = input.getString( "ctSPMMold" );
      cvAssemble = input.getString( "cvAssemble" );
      cvMold = input.getString( "cvMold" );
      defectCostExpr = input.getString( "defectCostExpr" );
      defectRate = input.getString( "defectRate" );
      deliverName = input.getString( "deliverName" );
      devType = input.getString( "devType" );
      directCostExpr = input.getString( "directCostExpr" );
      disposableApplyYear = input.getString( "disposableApplyYear" );
      disposableCr = input.getString( "disposableCr" );
      distributionCost = input.getString( "distributionCost" );
      drStep = input.getString( "drStep" );
      duty = input.getString( "duty" );
      eaOutput = input.getString( "eaOutput" );
      efficientRate = input.getString( "efficientRate" );
      elecCost = input.getString( "elecCost" );
      elecCostClimbRate = input.getString( "elecCostClimbRate" );
      elecCostExpr = input.getString( "elecCostExpr" );
      elecCostYear = input.getString( "elecCostYear" );
      equipinvestPriceTotal = input.getString( "equipinvestPriceTotal" );
      etcMPFactory = input.getString( "etcMPFactory" );
      etcMPIC = input.getString( "etcMPIC" );
      etcMPQMax = input.getString( "etcMPQMax" );
      etcMPQTotal = input.getString( "etcMPQTotal" );
      etcNIC = input.getString( "etcNIC" );
      etcNPFactory = input.getString( "etcNPFactory" );
      etcNPay = input.getString( "etcNPay" );
      etcReducePrice = input.getString( "etcReducePrice" );
      etcinvestPriceTotal = input.getString( "etcinvestPriceTotal" );
      expenseExpr = input.getString( "expenseExpr" );
      expenseTotal = input.getString( "expenseTotal" );
      facMPFactory = input.getString( "facMPFactory" );
      facMPIC = input.getString( "facMPIC" );
      facMPICMUnit = input.getString( "facMPICMUnit" );
      facMPICMUnitCurrency = input.getString( "facMPICMUnitCurrency" );
      facMPQMax = input.getString( "facMPQMax" );
      facMPQTotal = input.getString( "facMPQTotal" );
      facMftDivision = input.getString( "facMftDivision" );
      facNFactory = input.getString( "facNFactory" );
      facNIC = input.getString( "facNIC" );
      facNICMUnit = input.getString( "facNICMUnit" );
      facNICMUnitCurrency = input.getString( "facNICMUnitCurrency" );
      facNPay = input.getString( "facNPay" );
      facNPayMUnit = input.getString( "facNPayMUnit" );
      facNPayMUnitCurrency = input.getString( "facNPayMUnitCurrency" );
      facOrder = input.getString( "facOrder" );
      facReduceCtSpm = input.getString( "facReduceCtSpm" );
      facReducePrice = input.getString( "facReducePrice" );
      facilities = input.getString( "facilities" );
      formulaVersion = input.getIntObject( "formulaVersion" );
      freeRawMaterialCostTotal = input.getString( "freeRawMaterialCostTotal" );
      inDirectCost = input.getString( "inDirectCost" );
      inDirectCost2Expr = input.getString( "inDirectCost2Expr" );
      inDirectCostExpr = input.getString( "inDirectCostExpr" );
      indirectCostRate = input.getString( "indirectCostRate" );
      indirectLabourRate = input.getString( "indirectLabourRate" );
      indirectRndRate = input.getString( "indirectRndRate" );
      initFlag = input.getString( "initFlag" );
      itemName = input.getString( "itemName" );
      laborCostClimbRate = input.getString( "laborCostClimbRate" );
      laborCostExpr = input.getString( "laborCostExpr" );
      laborCostRate = input.getString( "laborCostRate" );
      laborCostTotal = input.getString( "laborCostTotal" );
      laborCostYear = input.getString( "laborCostYear" );
      laborExpenseAdd1Expr = input.getString( "laborExpenseAdd1Expr" );
      laborExpenseAdd2Expr = input.getString( "laborExpenseAdd2Expr" );
      laborRateExpr = input.getString( "laborRateExpr" );
      lastest = input.getBoolean( "lastest" );
      m_pallet_container = input.getString( "m_pallet_container" );
      machineDpcCost = input.getString( "machineDpcCost" );
      machineDpcCostExpr = input.getString( "machineDpcCostExpr" );
      machineReduceCost = input.getString( "machineReduceCost" );
      manageCostExpr = input.getString( "manageCostExpr" );
      manageCostTotal = input.getString( "manageCostTotal" );
      manageMtrLogisExpr = input.getString( "manageMtrLogisExpr" );
      manageMtrdutieExpr = input.getString( "manageMtrdutieExpr" );
      masterReference = (wt.fc.ObjectReference) input.readObject( "masterReference", masterReference, wt.fc.ObjectReference.class, true );
      materialCostExpr = input.getString( "materialCostExpr" );
      materialCostTotal = input.getString( "materialCostTotal" );
      metalCuttingCost = input.getString( "metalCuttingCost" );
      metalDeTinCost = input.getString( "metalDeTinCost" );
      metalImportance = input.getString( "metalImportance" );
      metalLmeCost = input.getString( "metalLmeCost" );
      metalLmeStd = input.getString( "metalLmeStd" );
      metalPartNo = input.getString( "metalPartNo" );
      metalPlateCost = input.getString( "metalPlateCost" );
      metalScenario = input.getString( "metalScenario" );
      metalScrapCost = input.getString( "metalScrapCost" );
      metalScrapRecycle = input.getString( "metalScrapRecycle" );
      metalTollPrcCost = input.getString( "metalTollPrcCost" );
      mfcCostExpr = input.getString( "mfcCostExpr" );
      mfcCostTotal = input.getString( "mfcCostTotal" );
      mftFactory1 = input.getString( "mftFactory1" );
      mftFactory2 = input.getString( "mftFactory2" );
      moldDieNo = input.getString( "moldDieNo" );
      moldMPFactory = input.getString( "moldMPFactory" );
      moldMPIC = input.getString( "moldMPIC" );
      moldMPICMUnit = input.getString( "moldMPICMUnit" );
      moldMPICMUnitCurrency = input.getString( "moldMPICMUnitCurrency" );
      moldMPQMax = input.getString( "moldMPQMax" );
      moldMPQTotal = input.getString( "moldMPQTotal" );
      moldMaintenance = input.getString( "moldMaintenance" );
      moldMftDivision = input.getString( "moldMftDivision" );
      moldNFactory = input.getString( "moldNFactory" );
      moldNIC = input.getString( "moldNIC" );
      moldNICMUnit = input.getString( "moldNICMUnit" );
      moldNICMUnitCurrency = input.getString( "moldNICMUnitCurrency" );
      moldNPay = input.getString( "moldNPay" );
      moldNPayMUnit = input.getString( "moldNPayMUnit" );
      moldNPayMUnitCurrency = input.getString( "moldNPayMUnitCurrency" );
      moldOrder = input.getString( "moldOrder" );
      moldPrepareExpr = input.getString( "moldPrepareExpr" );
      moldProductAssyExpr = input.getString( "moldProductAssyExpr" );
      moldReadyTime = input.getString( "moldReadyTime" );
      moldReducePrice = input.getString( "moldReducePrice" );
      moldStructure = input.getString( "moldStructure" );
      moldinvestPriceTotal = input.getString( "moldinvestPriceTotal" );
      mtrLtCost = input.getString( "mtrLtCost" );
      mtrLtCostExpr = input.getString( "mtrLtCostExpr" );
      mtrLtRate = input.getString( "mtrLtRate" );
      mtrLtRateExpr = input.getString( "mtrLtRateExpr" );
      mtrManageExpr = input.getString( "mtrManageExpr" );
      mtrManageRate = input.getString( "mtrManageRate" );
      mtrMetalPrice = input.getString( "mtrMetalPrice" );
      mtrSujiPrice = input.getString( "mtrSujiPrice" );
      outMonetaryUnit = input.getString( "outMonetaryUnit" );
      outMonetaryUnitCurrency = input.getString( "outMonetaryUnitCurrency" );
      outUnitCost = input.getString( "outUnitCost" );
      outUnitCostVal = input.getString( "outUnitCostVal" );
      outputExpr = input.getString( "outputExpr" );
      pMonetaryUnit = input.getString( "pMonetaryUnit" );
      pMonetaryUnitCurrency = input.getString( "pMonetaryUnitCurrency" );
      pUnitCost = input.getString( "pUnitCost" );
      p_pallet_container = input.getString( "p_pallet_container" );
      packBoxUnit = input.getString( "packBoxUnit" );
      packPalletUnit = input.getString( "packPalletUnit" );
      packUnitPriceOption = input.getString( "packUnitPriceOption" );
      packUnitPriceSum = input.getString( "packUnitPriceSum" );
      parentReference = (wt.fc.ObjectReference) input.readObject( "parentReference", parentReference, wt.fc.ObjectReference.class, true );
      partLtCost = input.getString( "partLtCost" );
      partLtRate = input.getString( "partLtRate" );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      partTotalCost = input.getString( "partTotalCost" );
      partType = input.getString( "partType" );
      payCostLtExpr = input.getString( "payCostLtExpr" );
      payLtCost = input.getString( "payLtCost" );
      payPlace = input.getString( "payPlace" );
      payRateLt = input.getString( "payRateLt" );
      payRateLtExpr = input.getString( "payRateLtExpr" );
      prePlatingCost = input.getString( "prePlatingCost" );
      prePlatingSpec = input.getString( "prePlatingSpec" );
      prePlatingUnit = input.getString( "prePlatingUnit" );
      prePlatingUnitCurrency = input.getString( "prePlatingUnitCurrency" );
      pressinvestPriceTotal = input.getString( "pressinvestPriceTotal" );
      productCostTotal = input.getString( "productCostTotal" );
      productHaveMonth = input.getString( "productHaveMonth" );
      productLossExpr = input.getString( "productLossExpr" );
      productLossRate = input.getString( "productLossRate" );
      productNInvestTotal = input.getString( "productNInvestTotal" );
      productWeight = input.getString( "productWeight" );
      profitRateExpr = input.getString( "profitRateExpr" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      purchaseinvestPriceTotal = input.getString( "purchaseinvestPriceTotal" );
      quantityAvg = input.getString( "quantityAvg" );
      quantityMax = input.getString( "quantityMax" );
      quantityTotal = input.getString( "quantityTotal" );
      rMatNMetalName = input.getString( "rMatNMetalName" );
      rMatNMetalP = input.getString( "rMatNMetalP" );
      rMatNMetalT = input.getString( "rMatNMetalT" );
      rMatNMetalW = input.getString( "rMatNMetalW" );
      rawMaterialCostExpr = input.getString( "rawMaterialCostExpr" );
      realPartNo = input.getString( "realPartNo" );
      reduceCostExpr = input.getString( "reduceCostExpr" );
      reduceCostTotal = input.getString( "reduceCostTotal" );
      reportReference = (wt.fc.ObjectReference) input.readObject( "reportReference", reportReference, wt.fc.ObjectReference.class, true );
      reportUS = input.getString( "reportUS" );
      rnd = input.getString( "rnd" );
      rndExpr = input.getString( "rndExpr" );
      rndOption = input.getString( "rndOption" );
      salesTargetCostExpr = input.getString( "salesTargetCostExpr" );
      salesTargetGb = input.getString( "salesTargetGb" );
      scrapSalesCostExpr = input.getString( "scrapSalesCostExpr" );
      scrapWeight = input.getString( "scrapWeight" );
      sizeH = input.getString( "sizeH" );
      sizeL = input.getString( "sizeL" );
      sizeW = input.getString( "sizeW" );
      sopYear = input.getString( "sopYear" );
      sortLocation = input.getIntObject( "sortLocation" );
      subCostAllTotal = input.getString( "subCostAllTotal" );
      subCostAllTotalOption = input.getString( "subCostAllTotalOption" );
      subCostDefectTotal = input.getString( "subCostDefectTotal" );
      subCostExceptTotal = input.getString( "subCostExceptTotal" );
      subCostExceptTotalOption = input.getString( "subCostExceptTotalOption" );
      subVersion = input.getIntObject( "subVersion" );
      sujiColor = input.getString( "sujiColor" );
      sujiGrade = input.getString( "sujiGrade" );
      sujiPartName = input.getString( "sujiPartName" );
      sujiPartNo = input.getString( "sujiPartNo" );
      sujiScrapCost = input.getString( "sujiScrapCost" );
      sujiScrapRecycle = input.getString( "sujiScrapRecycle" );
      tabaryu = input.getString( "tabaryu" );
      tabaryuExpr = input.getString( "tabaryuExpr" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      tos = input.getString( "tos" );
      totalCostExpr = input.getString( "totalCostExpr" );
      totalWeight = input.getString( "totalWeight" );
      unitManage = input.getString( "unitManage" );
      us = input.getString( "us" );
      version = input.getIntObject( "version" );
      workDay = input.getString( "workDay" );
      workHour = input.getString( "workHour" );
      workYear = input.getString( "workYear" );
      worker = input.getString( "worker" );
   }

   boolean readVersion37715106356876868L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      addManHourClb_1 = (java.lang.String) input.readObject();
      addManHourClb_2 = (java.lang.String) input.readObject();
      addManHourCt_1 = (java.lang.String) input.readObject();
      addManHourCt_2 = (java.lang.String) input.readObject();
      addManHourEff_1 = (java.lang.String) input.readObject();
      addManHourEff_2 = (java.lang.String) input.readObject();
      addManHourLb_1 = (java.lang.String) input.readObject();
      addManHourLb_2 = (java.lang.String) input.readObject();
      addManHourYear_1 = (java.lang.String) input.readObject();
      addManHourYear_2 = (java.lang.String) input.readObject();
      apMonetaryUnit = (java.lang.String) input.readObject();
      apMonetaryUnitCurrency = (java.lang.String) input.readObject();
      apPlatingSpec = (java.lang.String) input.readObject();
      apUnitCost = (java.lang.String) input.readObject();
      apUnitCostOption = (java.lang.String) input.readObject();
      apUnitCostVal = (java.lang.String) input.readObject();
      assyLossExpr = (java.lang.String) input.readObject();
      assyLossRate = (java.lang.String) input.readObject();
      attrLocked = (java.lang.String) input.readObject();
      bomLevel = (java.lang.String) input.readObject();
      buyBackIndirectCostRate = (java.lang.String) input.readObject();
      calcOptionLabor = (java.lang.String) input.readObject();
      calcOptionManage = (java.lang.String) input.readObject();
      calcOptionMaterial = (java.lang.String) input.readObject();
      calcStdExpense = (java.lang.String) input.readObject();
      calcStdLabor = (java.lang.String) input.readObject();
      calcStdManage = (java.lang.String) input.readObject();
      calcStdMaterial = (java.lang.String) input.readObject();
      calcStdOutPut = (java.lang.String) input.readObject();
      capa = (java.lang.String) input.readObject();
      capaMaxQty = (java.lang.String) input.readObject();
      capaNote = (java.lang.String) input.readObject();
      capaYear = (java.lang.String) input.readObject();
      caseNote = (java.lang.String) input.readObject();
      caseOrder = (java.lang.Integer) input.readObject();
      coManageCost = (java.lang.String) input.readObject();
      coManageExpr = (java.lang.String) input.readObject();
      coManageRate = (java.lang.String) input.readObject();
      coManageRateOption = (java.lang.String) input.readObject();
      company = (java.lang.String) input.readObject();
      corporateMarginCostExpr = (java.lang.String) input.readObject();
      corporateMarginRate = (java.lang.String) input.readObject();
      costDeliver = (java.lang.String) input.readObject();
      ctSPMAssemble = (java.lang.String) input.readObject();
      ctSPMMold = (java.lang.String) input.readObject();
      cvAssemble = (java.lang.String) input.readObject();
      cvMold = (java.lang.String) input.readObject();
      defectCostExpr = (java.lang.String) input.readObject();
      defectRate = (java.lang.String) input.readObject();
      deliverName = (java.lang.String) input.readObject();
      devType = (java.lang.String) input.readObject();
      directCostExpr = (java.lang.String) input.readObject();
      disposableApplyYear = (java.lang.String) input.readObject();
      disposableCr = (java.lang.String) input.readObject();
      distributionCost = (java.lang.String) input.readObject();
      drStep = (java.lang.String) input.readObject();
      duty = (java.lang.String) input.readObject();
      eaOutput = (java.lang.String) input.readObject();
      efficientRate = (java.lang.String) input.readObject();
      elecCost = (java.lang.String) input.readObject();
      elecCostClimbRate = (java.lang.String) input.readObject();
      elecCostExpr = (java.lang.String) input.readObject();
      elecCostYear = (java.lang.String) input.readObject();
      equipinvestPriceTotal = (java.lang.String) input.readObject();
      etcMPFactory = (java.lang.String) input.readObject();
      etcMPIC = (java.lang.String) input.readObject();
      etcMPQMax = (java.lang.String) input.readObject();
      etcMPQTotal = (java.lang.String) input.readObject();
      etcNIC = (java.lang.String) input.readObject();
      etcNPFactory = (java.lang.String) input.readObject();
      etcNPay = (java.lang.String) input.readObject();
      etcReducePrice = (java.lang.String) input.readObject();
      etcinvestPriceTotal = (java.lang.String) input.readObject();
      expenseExpr = (java.lang.String) input.readObject();
      expenseTotal = (java.lang.String) input.readObject();
      facMPFactory = (java.lang.String) input.readObject();
      facMPIC = (java.lang.String) input.readObject();
      facMPICMUnit = (java.lang.String) input.readObject();
      facMPICMUnitCurrency = (java.lang.String) input.readObject();
      facMPQMax = (java.lang.String) input.readObject();
      facMPQTotal = (java.lang.String) input.readObject();
      facMftDivision = (java.lang.String) input.readObject();
      facNFactory = (java.lang.String) input.readObject();
      facNIC = (java.lang.String) input.readObject();
      facNICMUnit = (java.lang.String) input.readObject();
      facNICMUnitCurrency = (java.lang.String) input.readObject();
      facNPay = (java.lang.String) input.readObject();
      facNPayMUnit = (java.lang.String) input.readObject();
      facNPayMUnitCurrency = (java.lang.String) input.readObject();
      facOrder = (java.lang.String) input.readObject();
      facReduceCtSpm = (java.lang.String) input.readObject();
      facReducePrice = (java.lang.String) input.readObject();
      facilities = (java.lang.String) input.readObject();
      formulaVersion = (java.lang.Integer) input.readObject();
      freeRawMaterialCostTotal = (java.lang.String) input.readObject();
      inDirectCost = (java.lang.String) input.readObject();
      inDirectCost2Expr = (java.lang.String) input.readObject();
      inDirectCostExpr = (java.lang.String) input.readObject();
      indirectCostRate = (java.lang.String) input.readObject();
      indirectLabourRate = (java.lang.String) input.readObject();
      indirectRndRate = (java.lang.String) input.readObject();
      initFlag = (java.lang.String) input.readObject();
      itemName = (java.lang.String) input.readObject();
      laborCostClimbRate = (java.lang.String) input.readObject();
      laborCostExpr = (java.lang.String) input.readObject();
      laborCostRate = (java.lang.String) input.readObject();
      laborCostTotal = (java.lang.String) input.readObject();
      laborCostYear = (java.lang.String) input.readObject();
      laborExpenseAdd1Expr = (java.lang.String) input.readObject();
      laborExpenseAdd2Expr = (java.lang.String) input.readObject();
      laborRateExpr = (java.lang.String) input.readObject();
      lastest = input.readBoolean();
      m_pallet_container = (java.lang.String) input.readObject();
      machineDpcCost = (java.lang.String) input.readObject();
      machineDpcCostExpr = (java.lang.String) input.readObject();
      machineReduceCost = (java.lang.String) input.readObject();
      manageCostExpr = (java.lang.String) input.readObject();
      manageCostTotal = (java.lang.String) input.readObject();
      manageMtrLogisExpr = (java.lang.String) input.readObject();
      manageMtrdutieExpr = (java.lang.String) input.readObject();
      masterReference = (wt.fc.ObjectReference) input.readObject();
      materialCostExpr = (java.lang.String) input.readObject();
      materialCostTotal = (java.lang.String) input.readObject();
      metalCuttingCost = (java.lang.String) input.readObject();
      metalDeTinCost = (java.lang.String) input.readObject();
      metalImportance = (java.lang.String) input.readObject();
      metalLmeCost = (java.lang.String) input.readObject();
      metalLmeStd = (java.lang.String) input.readObject();
      metalPartNo = (java.lang.String) input.readObject();
      metalPlateCost = (java.lang.String) input.readObject();
      metalScenario = (java.lang.String) input.readObject();
      metalScrapCost = (java.lang.String) input.readObject();
      metalScrapRecycle = (java.lang.String) input.readObject();
      metalTollPrcCost = (java.lang.String) input.readObject();
      mfcCostExpr = (java.lang.String) input.readObject();
      mfcCostTotal = (java.lang.String) input.readObject();
      mftFactory1 = (java.lang.String) input.readObject();
      mftFactory2 = (java.lang.String) input.readObject();
      moldDieNo = (java.lang.String) input.readObject();
      moldMPFactory = (java.lang.String) input.readObject();
      moldMPIC = (java.lang.String) input.readObject();
      moldMPICMUnit = (java.lang.String) input.readObject();
      moldMPICMUnitCurrency = (java.lang.String) input.readObject();
      moldMPQMax = (java.lang.String) input.readObject();
      moldMPQTotal = (java.lang.String) input.readObject();
      moldMaintenance = (java.lang.String) input.readObject();
      moldMftDivision = (java.lang.String) input.readObject();
      moldNFactory = (java.lang.String) input.readObject();
      moldNIC = (java.lang.String) input.readObject();
      moldNICMUnit = (java.lang.String) input.readObject();
      moldNICMUnitCurrency = (java.lang.String) input.readObject();
      moldNPay = (java.lang.String) input.readObject();
      moldNPayMUnit = (java.lang.String) input.readObject();
      moldNPayMUnitCurrency = (java.lang.String) input.readObject();
      moldOrder = (java.lang.String) input.readObject();
      moldPrepareExpr = (java.lang.String) input.readObject();
      moldProductAssyExpr = (java.lang.String) input.readObject();
      moldReadyTime = (java.lang.String) input.readObject();
      moldReducePrice = (java.lang.String) input.readObject();
      moldStructure = (java.lang.String) input.readObject();
      moldinvestPriceTotal = (java.lang.String) input.readObject();
      mtrLtCost = (java.lang.String) input.readObject();
      mtrLtCostExpr = (java.lang.String) input.readObject();
      mtrLtRate = (java.lang.String) input.readObject();
      mtrLtRateExpr = (java.lang.String) input.readObject();
      mtrManageExpr = (java.lang.String) input.readObject();
      mtrManageRate = (java.lang.String) input.readObject();
      mtrMetalPrice = (java.lang.String) input.readObject();
      mtrSujiPrice = (java.lang.String) input.readObject();
      outMonetaryUnit = (java.lang.String) input.readObject();
      outMonetaryUnitCurrency = (java.lang.String) input.readObject();
      outUnitCost = (java.lang.String) input.readObject();
      outUnitCostVal = (java.lang.String) input.readObject();
      outputExpr = (java.lang.String) input.readObject();
      pMonetaryUnit = (java.lang.String) input.readObject();
      pMonetaryUnitCurrency = (java.lang.String) input.readObject();
      pUnitCost = (java.lang.String) input.readObject();
      p_pallet_container = (java.lang.String) input.readObject();
      packBoxUnit = (java.lang.String) input.readObject();
      packPalletUnit = (java.lang.String) input.readObject();
      packUnitPriceOption = (java.lang.String) input.readObject();
      packUnitPriceSum = (java.lang.String) input.readObject();
      parentReference = (wt.fc.ObjectReference) input.readObject();
      partLtCost = (java.lang.String) input.readObject();
      partLtRate = (java.lang.String) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      partTotalCost = (java.lang.String) input.readObject();
      partType = (java.lang.String) input.readObject();
      payCostLtExpr = (java.lang.String) input.readObject();
      payLtCost = (java.lang.String) input.readObject();
      payPlace = (java.lang.String) input.readObject();
      payRateLt = (java.lang.String) input.readObject();
      payRateLtExpr = (java.lang.String) input.readObject();
      prePlatingCost = (java.lang.String) input.readObject();
      prePlatingSpec = (java.lang.String) input.readObject();
      prePlatingUnit = (java.lang.String) input.readObject();
      prePlatingUnitCurrency = (java.lang.String) input.readObject();
      pressinvestPriceTotal = (java.lang.String) input.readObject();
      productCostTotal = (java.lang.String) input.readObject();
      productHaveMonth = (java.lang.String) input.readObject();
      productLossExpr = (java.lang.String) input.readObject();
      productLossRate = (java.lang.String) input.readObject();
      productNInvestTotal = (java.lang.String) input.readObject();
      productWeight = (java.lang.String) input.readObject();
      profitRateExpr = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      purchaseinvestPriceTotal = (java.lang.String) input.readObject();
      quantityAvg = (java.lang.String) input.readObject();
      quantityMax = (java.lang.String) input.readObject();
      quantityTotal = (java.lang.String) input.readObject();
      rMatNMetalName = (java.lang.String) input.readObject();
      rMatNMetalP = (java.lang.String) input.readObject();
      rMatNMetalT = (java.lang.String) input.readObject();
      rMatNMetalW = (java.lang.String) input.readObject();
      rawMaterialCostExpr = (java.lang.String) input.readObject();
      realPartNo = (java.lang.String) input.readObject();
      reduceCostExpr = (java.lang.String) input.readObject();
      reduceCostTotal = (java.lang.String) input.readObject();
      reportReference = (wt.fc.ObjectReference) input.readObject();
      reportUS = (java.lang.String) input.readObject();
      rnd = (java.lang.String) input.readObject();
      rndExpr = (java.lang.String) input.readObject();
      rndOption = (java.lang.String) input.readObject();
      salesTargetCostExpr = (java.lang.String) input.readObject();
      salesTargetGb = (java.lang.String) input.readObject();
      scrapSalesCostExpr = (java.lang.String) input.readObject();
      scrapWeight = (java.lang.String) input.readObject();
      sizeH = (java.lang.String) input.readObject();
      sizeL = (java.lang.String) input.readObject();
      sizeW = (java.lang.String) input.readObject();
      sopYear = (java.lang.String) input.readObject();
      sortLocation = (java.lang.Integer) input.readObject();
      subCostAllTotal = (java.lang.String) input.readObject();
      subCostAllTotalOption = (java.lang.String) input.readObject();
      subCostDefectTotal = (java.lang.String) input.readObject();
      subCostExceptTotal = (java.lang.String) input.readObject();
      subCostExceptTotalOption = (java.lang.String) input.readObject();
      subVersion = (java.lang.Integer) input.readObject();
      sujiColor = (java.lang.String) input.readObject();
      sujiGrade = (java.lang.String) input.readObject();
      sujiPartName = (java.lang.String) input.readObject();
      sujiPartNo = (java.lang.String) input.readObject();
      sujiScrapCost = (java.lang.String) input.readObject();
      sujiScrapRecycle = (java.lang.String) input.readObject();
      tabaryu = (java.lang.String) input.readObject();
      tabaryuExpr = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      tos = (java.lang.String) input.readObject();
      totalCostExpr = (java.lang.String) input.readObject();
      totalWeight = (java.lang.String) input.readObject();
      unitManage = (java.lang.String) input.readObject();
      us = (java.lang.String) input.readObject();
      version = (java.lang.Integer) input.readObject();
      workDay = (java.lang.String) input.readObject();
      workHour = (java.lang.String) input.readObject();
      workYear = (java.lang.String) input.readObject();
      worker = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( CostPart thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion37715106356876868L( input, readSerialVersionUID, superDone );
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
