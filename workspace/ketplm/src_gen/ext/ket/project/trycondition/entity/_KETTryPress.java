package ext.ket.project.trycondition.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETTryPress extends ext.ket.project.trycondition.entity.KETTryCondition implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.trycondition.entity.entityResource";
   static final java.lang.String CLASSNAME = KETTryPress.class.getName();

   /**
    * 두께/폭
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String THICKNESS = "thickness";
   static int THICKNESS_UPPER_LIMIT = -1;
   java.lang.String thickness;
   /**
    * 두께/폭
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getThickness() {
      return thickness;
   }
   /**
    * 두께/폭
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
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
    * 도금
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PLATING = "plating";
   static int PLATING_UPPER_LIMIT = -1;
   java.lang.String plating;
   /**
    * 도금
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getPlating() {
      return plating;
   }
   /**
    * 도금
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setPlating(java.lang.String plating) throws wt.util.WTPropertyVetoException {
      platingValidate(plating);
      this.plating = plating;
   }
   void platingValidate(java.lang.String plating) throws wt.util.WTPropertyVetoException {
      if (PLATING_UPPER_LIMIT < 1) {
         try { PLATING_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("plating").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PLATING_UPPER_LIMIT = 200; }
      }
      if (plating != null && !wt.fc.PersistenceHelper.checkStoredLength(plating.toString(), PLATING_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "plating"), java.lang.String.valueOf(java.lang.Math.min(PLATING_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "plating", this.plating, plating));
   }

   /**
    * 금형구조(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_STRUC_ETC = "moldStrucEtc";
   static int MOLD_STRUC_ETC_UPPER_LIMIT = -1;
   java.lang.String moldStrucEtc;
   /**
    * 금형구조(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getMoldStrucEtc() {
      return moldStrucEtc;
   }
   /**
    * 금형구조(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldStrucEtc(java.lang.String moldStrucEtc) throws wt.util.WTPropertyVetoException {
      moldStrucEtcValidate(moldStrucEtc);
      this.moldStrucEtc = moldStrucEtc;
   }
   void moldStrucEtcValidate(java.lang.String moldStrucEtc) throws wt.util.WTPropertyVetoException {
      if (MOLD_STRUC_ETC_UPPER_LIMIT < 1) {
         try { MOLD_STRUC_ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldStrucEtc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_STRUC_ETC_UPPER_LIMIT = 200; }
      }
      if (moldStrucEtc != null && !wt.fc.PersistenceHelper.checkStoredLength(moldStrucEtc.toString(), MOLD_STRUC_ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldStrucEtc"), java.lang.String.valueOf(java.lang.Math.min(MOLD_STRUC_ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldStrucEtc", this.moldStrucEtc, moldStrucEtc));
   }

   /**
    * 제품방식(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PRODUCT_METHOD_ETC = "productMethodEtc";
   static int PRODUCT_METHOD_ETC_UPPER_LIMIT = -1;
   java.lang.String productMethodEtc;
   /**
    * 제품방식(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getProductMethodEtc() {
      return productMethodEtc;
   }
   /**
    * 제품방식(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setProductMethodEtc(java.lang.String productMethodEtc) throws wt.util.WTPropertyVetoException {
      productMethodEtcValidate(productMethodEtc);
      this.productMethodEtc = productMethodEtc;
   }
   void productMethodEtcValidate(java.lang.String productMethodEtc) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_METHOD_ETC_UPPER_LIMIT < 1) {
         try { PRODUCT_METHOD_ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productMethodEtc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_METHOD_ETC_UPPER_LIMIT = 200; }
      }
      if (productMethodEtc != null && !wt.fc.PersistenceHelper.checkStoredLength(productMethodEtc.toString(), PRODUCT_METHOD_ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productMethodEtc"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_METHOD_ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productMethodEtc", this.productMethodEtc, productMethodEtc));
   }

   /**
    * 금형Size가로
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_SIZE_WIDTH = "moldSizeWidth";
   static int MOLD_SIZE_WIDTH_UPPER_LIMIT = -1;
   java.lang.String moldSizeWidth;
   /**
    * 금형Size가로
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getMoldSizeWidth() {
      return moldSizeWidth;
   }
   /**
    * 금형Size가로
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldSizeWidth(java.lang.String moldSizeWidth) throws wt.util.WTPropertyVetoException {
      moldSizeWidthValidate(moldSizeWidth);
      this.moldSizeWidth = moldSizeWidth;
   }
   void moldSizeWidthValidate(java.lang.String moldSizeWidth) throws wt.util.WTPropertyVetoException {
      if (MOLD_SIZE_WIDTH_UPPER_LIMIT < 1) {
         try { MOLD_SIZE_WIDTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldSizeWidth").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_SIZE_WIDTH_UPPER_LIMIT = 200; }
      }
      if (moldSizeWidth != null && !wt.fc.PersistenceHelper.checkStoredLength(moldSizeWidth.toString(), MOLD_SIZE_WIDTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldSizeWidth"), java.lang.String.valueOf(java.lang.Math.min(MOLD_SIZE_WIDTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldSizeWidth", this.moldSizeWidth, moldSizeWidth));
   }

   /**
    * 금형Size(길이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_SIZE_LENGTH = "moldSizeLength";
   static int MOLD_SIZE_LENGTH_UPPER_LIMIT = -1;
   java.lang.String moldSizeLength;
   /**
    * 금형Size(길이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getMoldSizeLength() {
      return moldSizeLength;
   }
   /**
    * 금형Size(길이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldSizeLength(java.lang.String moldSizeLength) throws wt.util.WTPropertyVetoException {
      moldSizeLengthValidate(moldSizeLength);
      this.moldSizeLength = moldSizeLength;
   }
   void moldSizeLengthValidate(java.lang.String moldSizeLength) throws wt.util.WTPropertyVetoException {
      if (MOLD_SIZE_LENGTH_UPPER_LIMIT < 1) {
         try { MOLD_SIZE_LENGTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldSizeLength").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_SIZE_LENGTH_UPPER_LIMIT = 200; }
      }
      if (moldSizeLength != null && !wt.fc.PersistenceHelper.checkStoredLength(moldSizeLength.toString(), MOLD_SIZE_LENGTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldSizeLength"), java.lang.String.valueOf(java.lang.Math.min(MOLD_SIZE_LENGTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldSizeLength", this.moldSizeLength, moldSizeLength));
   }

   /**
    * 금형Size(높이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_SIZE_HEIGHT = "moldSizeHeight";
   static int MOLD_SIZE_HEIGHT_UPPER_LIMIT = -1;
   java.lang.String moldSizeHeight;
   /**
    * 금형Size(높이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getMoldSizeHeight() {
      return moldSizeHeight;
   }
   /**
    * 금형Size(높이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldSizeHeight(java.lang.String moldSizeHeight) throws wt.util.WTPropertyVetoException {
      moldSizeHeightValidate(moldSizeHeight);
      this.moldSizeHeight = moldSizeHeight;
   }
   void moldSizeHeightValidate(java.lang.String moldSizeHeight) throws wt.util.WTPropertyVetoException {
      if (MOLD_SIZE_HEIGHT_UPPER_LIMIT < 1) {
         try { MOLD_SIZE_HEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldSizeHeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_SIZE_HEIGHT_UPPER_LIMIT = 200; }
      }
      if (moldSizeHeight != null && !wt.fc.PersistenceHelper.checkStoredLength(moldSizeHeight.toString(), MOLD_SIZE_HEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldSizeHeight"), java.lang.String.valueOf(java.lang.Math.min(MOLD_SIZE_HEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldSizeHeight", this.moldSizeHeight, moldSizeHeight));
   }

   /**
    * 금형중량(상형)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_WEIGHT_TOP = "moldWeightTop";
   static int MOLD_WEIGHT_TOP_UPPER_LIMIT = -1;
   java.lang.String moldWeightTop;
   /**
    * 금형중량(상형)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getMoldWeightTop() {
      return moldWeightTop;
   }
   /**
    * 금형중량(상형)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldWeightTop(java.lang.String moldWeightTop) throws wt.util.WTPropertyVetoException {
      moldWeightTopValidate(moldWeightTop);
      this.moldWeightTop = moldWeightTop;
   }
   void moldWeightTopValidate(java.lang.String moldWeightTop) throws wt.util.WTPropertyVetoException {
      if (MOLD_WEIGHT_TOP_UPPER_LIMIT < 1) {
         try { MOLD_WEIGHT_TOP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldWeightTop").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_WEIGHT_TOP_UPPER_LIMIT = 200; }
      }
      if (moldWeightTop != null && !wt.fc.PersistenceHelper.checkStoredLength(moldWeightTop.toString(), MOLD_WEIGHT_TOP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldWeightTop"), java.lang.String.valueOf(java.lang.Math.min(MOLD_WEIGHT_TOP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldWeightTop", this.moldWeightTop, moldWeightTop));
   }

   /**
    * 금형중량(하형)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_WEIGHT_LOWER = "moldWeightLower";
   static int MOLD_WEIGHT_LOWER_UPPER_LIMIT = -1;
   java.lang.String moldWeightLower;
   /**
    * 금형중량(하형)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getMoldWeightLower() {
      return moldWeightLower;
   }
   /**
    * 금형중량(하형)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldWeightLower(java.lang.String moldWeightLower) throws wt.util.WTPropertyVetoException {
      moldWeightLowerValidate(moldWeightLower);
      this.moldWeightLower = moldWeightLower;
   }
   void moldWeightLowerValidate(java.lang.String moldWeightLower) throws wt.util.WTPropertyVetoException {
      if (MOLD_WEIGHT_LOWER_UPPER_LIMIT < 1) {
         try { MOLD_WEIGHT_LOWER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldWeightLower").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_WEIGHT_LOWER_UPPER_LIMIT = 200; }
      }
      if (moldWeightLower != null && !wt.fc.PersistenceHelper.checkStoredLength(moldWeightLower.toString(), MOLD_WEIGHT_LOWER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldWeightLower"), java.lang.String.valueOf(java.lang.Math.min(MOLD_WEIGHT_LOWER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldWeightLower", this.moldWeightLower, moldWeightLower));
   }

   /**
    * Die Height
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String DIE_HEIGHT = "dieHeight";
   static int DIE_HEIGHT_UPPER_LIMIT = -1;
   java.lang.String dieHeight;
   /**
    * Die Height
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getDieHeight() {
      return dieHeight;
   }
   /**
    * Die Height
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setDieHeight(java.lang.String dieHeight) throws wt.util.WTPropertyVetoException {
      dieHeightValidate(dieHeight);
      this.dieHeight = dieHeight;
   }
   void dieHeightValidate(java.lang.String dieHeight) throws wt.util.WTPropertyVetoException {
      if (DIE_HEIGHT_UPPER_LIMIT < 1) {
         try { DIE_HEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dieHeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIE_HEIGHT_UPPER_LIMIT = 200; }
      }
      if (dieHeight != null && !wt.fc.PersistenceHelper.checkStoredLength(dieHeight.toString(), DIE_HEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dieHeight"), java.lang.String.valueOf(java.lang.Math.min(DIE_HEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dieHeight", this.dieHeight, dieHeight));
   }

   /**
    * Pitch
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PITCH = "pitch";
   static int PITCH_UPPER_LIMIT = -1;
   java.lang.String pitch;
   /**
    * Pitch
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getPitch() {
      return pitch;
   }
   /**
    * Pitch
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setPitch(java.lang.String pitch) throws wt.util.WTPropertyVetoException {
      pitchValidate(pitch);
      this.pitch = pitch;
   }
   void pitchValidate(java.lang.String pitch) throws wt.util.WTPropertyVetoException {
      if (PITCH_UPPER_LIMIT < 1) {
         try { PITCH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pitch").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PITCH_UPPER_LIMIT = 200; }
      }
      if (pitch != null && !wt.fc.PersistenceHelper.checkStoredLength(pitch.toString(), PITCH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pitch"), java.lang.String.valueOf(java.lang.Math.min(PITCH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pitch", this.pitch, pitch));
   }

   /**
    * 타발속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PUNCHING_SPEED = "punchingSpeed";
   static int PUNCHING_SPEED_UPPER_LIMIT = -1;
   java.lang.String punchingSpeed;
   /**
    * 타발속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getPunchingSpeed() {
      return punchingSpeed;
   }
   /**
    * 타발속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setPunchingSpeed(java.lang.String punchingSpeed) throws wt.util.WTPropertyVetoException {
      punchingSpeedValidate(punchingSpeed);
      this.punchingSpeed = punchingSpeed;
   }
   void punchingSpeedValidate(java.lang.String punchingSpeed) throws wt.util.WTPropertyVetoException {
      if (PUNCHING_SPEED_UPPER_LIMIT < 1) {
         try { PUNCHING_SPEED_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("punchingSpeed").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PUNCHING_SPEED_UPPER_LIMIT = 200; }
      }
      if (punchingSpeed != null && !wt.fc.PersistenceHelper.checkStoredLength(punchingSpeed.toString(), PUNCHING_SPEED_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "punchingSpeed"), java.lang.String.valueOf(java.lang.Math.min(PUNCHING_SPEED_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "punchingSpeed", this.punchingSpeed, punchingSpeed));
   }

   /**
    * 타발수량
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PUNCHING_COUNT = "punchingCount";
   static int PUNCHING_COUNT_UPPER_LIMIT = -1;
   java.lang.String punchingCount;
   /**
    * 타발수량
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getPunchingCount() {
      return punchingCount;
   }
   /**
    * 타발수량
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setPunchingCount(java.lang.String punchingCount) throws wt.util.WTPropertyVetoException {
      punchingCountValidate(punchingCount);
      this.punchingCount = punchingCount;
   }
   void punchingCountValidate(java.lang.String punchingCount) throws wt.util.WTPropertyVetoException {
      if (PUNCHING_COUNT_UPPER_LIMIT < 1) {
         try { PUNCHING_COUNT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("punchingCount").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PUNCHING_COUNT_UPPER_LIMIT = 200; }
      }
      if (punchingCount != null && !wt.fc.PersistenceHelper.checkStoredLength(punchingCount.toString(), PUNCHING_COUNT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "punchingCount"), java.lang.String.valueOf(java.lang.Math.min(PUNCHING_COUNT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "punchingCount", this.punchingCount, punchingCount));
   }

   /**
    * 제품 취출수(열)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PRODUCT_OUTPUT_COL = "productOutputCol";
   static int PRODUCT_OUTPUT_COL_UPPER_LIMIT = -1;
   java.lang.String productOutputCol;
   /**
    * 제품 취출수(열)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getProductOutputCol() {
      return productOutputCol;
   }
   /**
    * 제품 취출수(열)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setProductOutputCol(java.lang.String productOutputCol) throws wt.util.WTPropertyVetoException {
      productOutputColValidate(productOutputCol);
      this.productOutputCol = productOutputCol;
   }
   void productOutputColValidate(java.lang.String productOutputCol) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_OUTPUT_COL_UPPER_LIMIT < 1) {
         try { PRODUCT_OUTPUT_COL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productOutputCol").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_OUTPUT_COL_UPPER_LIMIT = 200; }
      }
      if (productOutputCol != null && !wt.fc.PersistenceHelper.checkStoredLength(productOutputCol.toString(), PRODUCT_OUTPUT_COL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productOutputCol"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_OUTPUT_COL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productOutputCol", this.productOutputCol, productOutputCol));
   }

   /**
    * 제품취출수(피치)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PRODUCT_OUTPUT_PITCH = "productOutputPitch";
   static int PRODUCT_OUTPUT_PITCH_UPPER_LIMIT = -1;
   java.lang.String productOutputPitch;
   /**
    * 제품취출수(피치)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getProductOutputPitch() {
      return productOutputPitch;
   }
   /**
    * 제품취출수(피치)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setProductOutputPitch(java.lang.String productOutputPitch) throws wt.util.WTPropertyVetoException {
      productOutputPitchValidate(productOutputPitch);
      this.productOutputPitch = productOutputPitch;
   }
   void productOutputPitchValidate(java.lang.String productOutputPitch) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_OUTPUT_PITCH_UPPER_LIMIT < 1) {
         try { PRODUCT_OUTPUT_PITCH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productOutputPitch").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_OUTPUT_PITCH_UPPER_LIMIT = 200; }
      }
      if (productOutputPitch != null && !wt.fc.PersistenceHelper.checkStoredLength(productOutputPitch.toString(), PRODUCT_OUTPUT_PITCH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productOutputPitch"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_OUTPUT_PITCH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productOutputPitch", this.productOutputPitch, productOutputPitch));
   }

   /**
    * 안전센서(중복선택)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String SAFTY_SENSOR = "saftySensor";
   static int SAFTY_SENSOR_UPPER_LIMIT = -1;
   java.lang.String saftySensor;
   /**
    * 안전센서(중복선택)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getSaftySensor() {
      return saftySensor;
   }
   /**
    * 안전센서(중복선택)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setSaftySensor(java.lang.String saftySensor) throws wt.util.WTPropertyVetoException {
      saftySensorValidate(saftySensor);
      this.saftySensor = saftySensor;
   }
   void saftySensorValidate(java.lang.String saftySensor) throws wt.util.WTPropertyVetoException {
      if (SAFTY_SENSOR_UPPER_LIMIT < 1) {
         try { SAFTY_SENSOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("saftySensor").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SAFTY_SENSOR_UPPER_LIMIT = 200; }
      }
      if (saftySensor != null && !wt.fc.PersistenceHelper.checkStoredLength(saftySensor.toString(), SAFTY_SENSOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "saftySensor"), java.lang.String.valueOf(java.lang.Math.min(SAFTY_SENSOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "saftySensor", this.saftySensor, saftySensor));
   }

   /**
    * Press
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PRESS = "press";
   static int PRESS_UPPER_LIMIT = -1;
   java.lang.String press;
   /**
    * Press
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getPress() {
      return press;
   }
   /**
    * Press
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setPress(java.lang.String press) throws wt.util.WTPropertyVetoException {
      pressValidate(press);
      this.press = press;
   }
   void pressValidate(java.lang.String press) throws wt.util.WTPropertyVetoException {
      if (PRESS_UPPER_LIMIT < 1) {
         try { PRESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("press").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRESS_UPPER_LIMIT = 200; }
      }
      if (press != null && !wt.fc.PersistenceHelper.checkStoredLength(press.toString(), PRESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "press"), java.lang.String.valueOf(java.lang.Math.min(PRESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "press", this.press, press));
   }

   /**
    * 톤수
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String TONE = "tone";
   static int TONE_UPPER_LIMIT = -1;
   java.lang.String tone;
   /**
    * 톤수
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getTone() {
      return tone;
   }
   /**
    * 톤수
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setTone(java.lang.String tone) throws wt.util.WTPropertyVetoException {
      toneValidate(tone);
      this.tone = tone;
   }
   void toneValidate(java.lang.String tone) throws wt.util.WTPropertyVetoException {
      if (TONE_UPPER_LIMIT < 1) {
         try { TONE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tone").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TONE_UPPER_LIMIT = 200; }
      }
      if (tone != null && !wt.fc.PersistenceHelper.checkStoredLength(tone.toString(), TONE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tone"), java.lang.String.valueOf(java.lang.Math.min(TONE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tone", this.tone, tone));
   }

   /**
    * 설비정보(Stroke)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String STROKE = "stroke";
   static int STROKE_UPPER_LIMIT = -1;
   java.lang.String stroke;
   /**
    * 설비정보(Stroke)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getStroke() {
      return stroke;
   }
   /**
    * 설비정보(Stroke)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setStroke(java.lang.String stroke) throws wt.util.WTPropertyVetoException {
      strokeValidate(stroke);
      this.stroke = stroke;
   }
   void strokeValidate(java.lang.String stroke) throws wt.util.WTPropertyVetoException {
      if (STROKE_UPPER_LIMIT < 1) {
         try { STROKE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("stroke").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STROKE_UPPER_LIMIT = 200; }
      }
      if (stroke != null && !wt.fc.PersistenceHelper.checkStoredLength(stroke.toString(), STROKE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "stroke"), java.lang.String.valueOf(java.lang.Math.min(STROKE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "stroke", this.stroke, stroke));
   }

   /**
    * 설비정보(SPM)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String SPM = "spm";
   static int SPM_UPPER_LIMIT = -1;
   java.lang.String spm;
   /**
    * 설비정보(SPM)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getSpm() {
      return spm;
   }
   /**
    * 설비정보(SPM)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setSpm(java.lang.String spm) throws wt.util.WTPropertyVetoException {
      spmValidate(spm);
      this.spm = spm;
   }
   void spmValidate(java.lang.String spm) throws wt.util.WTPropertyVetoException {
      if (SPM_UPPER_LIMIT < 1) {
         try { SPM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("spm").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPM_UPPER_LIMIT = 200; }
      }
      if (spm != null && !wt.fc.PersistenceHelper.checkStoredLength(spm.toString(), SPM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "spm"), java.lang.String.valueOf(java.lang.Math.min(SPM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "spm", this.spm, spm));
   }

   /**
    * Bolster정보(가로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String BOLSTER_WIDTH = "bolsterWidth";
   static int BOLSTER_WIDTH_UPPER_LIMIT = -1;
   java.lang.String bolsterWidth;
   /**
    * Bolster정보(가로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getBolsterWidth() {
      return bolsterWidth;
   }
   /**
    * Bolster정보(가로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setBolsterWidth(java.lang.String bolsterWidth) throws wt.util.WTPropertyVetoException {
      bolsterWidthValidate(bolsterWidth);
      this.bolsterWidth = bolsterWidth;
   }
   void bolsterWidthValidate(java.lang.String bolsterWidth) throws wt.util.WTPropertyVetoException {
      if (BOLSTER_WIDTH_UPPER_LIMIT < 1) {
         try { BOLSTER_WIDTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bolsterWidth").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOLSTER_WIDTH_UPPER_LIMIT = 200; }
      }
      if (bolsterWidth != null && !wt.fc.PersistenceHelper.checkStoredLength(bolsterWidth.toString(), BOLSTER_WIDTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bolsterWidth"), java.lang.String.valueOf(java.lang.Math.min(BOLSTER_WIDTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bolsterWidth", this.bolsterWidth, bolsterWidth));
   }

   /**
    * Bolster정보(세로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String BOLSTER_HEIGHT = "bolsterHeight";
   static int BOLSTER_HEIGHT_UPPER_LIMIT = -1;
   java.lang.String bolsterHeight;
   /**
    * Bolster정보(세로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getBolsterHeight() {
      return bolsterHeight;
   }
   /**
    * Bolster정보(세로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setBolsterHeight(java.lang.String bolsterHeight) throws wt.util.WTPropertyVetoException {
      bolsterHeightValidate(bolsterHeight);
      this.bolsterHeight = bolsterHeight;
   }
   void bolsterHeightValidate(java.lang.String bolsterHeight) throws wt.util.WTPropertyVetoException {
      if (BOLSTER_HEIGHT_UPPER_LIMIT < 1) {
         try { BOLSTER_HEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bolsterHeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOLSTER_HEIGHT_UPPER_LIMIT = 200; }
      }
      if (bolsterHeight != null && !wt.fc.PersistenceHelper.checkStoredLength(bolsterHeight.toString(), BOLSTER_HEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bolsterHeight"), java.lang.String.valueOf(java.lang.Math.min(BOLSTER_HEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bolsterHeight", this.bolsterHeight, bolsterHeight));
   }

   /**
    * 피더기(중복선택)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String FEEDER_MACHINE = "feederMachine";
   static int FEEDER_MACHINE_UPPER_LIMIT = -1;
   java.lang.String feederMachine;
   /**
    * 피더기(중복선택)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getFeederMachine() {
      return feederMachine;
   }
   /**
    * 피더기(중복선택)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setFeederMachine(java.lang.String feederMachine) throws wt.util.WTPropertyVetoException {
      feederMachineValidate(feederMachine);
      this.feederMachine = feederMachine;
   }
   void feederMachineValidate(java.lang.String feederMachine) throws wt.util.WTPropertyVetoException {
      if (FEEDER_MACHINE_UPPER_LIMIT < 1) {
         try { FEEDER_MACHINE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("feederMachine").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FEEDER_MACHINE_UPPER_LIMIT = 2000; }
      }
      if (feederMachine != null && !wt.fc.PersistenceHelper.checkStoredLength(feederMachine.toString(), FEEDER_MACHINE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "feederMachine"), java.lang.String.valueOf(java.lang.Math.min(FEEDER_MACHINE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "feederMachine", this.feederMachine, feederMachine));
   }

   /**
    * 피더기(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String FEEDER_MACHINE_ETC = "feederMachineEtc";
   static int FEEDER_MACHINE_ETC_UPPER_LIMIT = -1;
   java.lang.String feederMachineEtc;
   /**
    * 피더기(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getFeederMachineEtc() {
      return feederMachineEtc;
   }
   /**
    * 피더기(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setFeederMachineEtc(java.lang.String feederMachineEtc) throws wt.util.WTPropertyVetoException {
      feederMachineEtcValidate(feederMachineEtc);
      this.feederMachineEtc = feederMachineEtc;
   }
   void feederMachineEtcValidate(java.lang.String feederMachineEtc) throws wt.util.WTPropertyVetoException {
      if (FEEDER_MACHINE_ETC_UPPER_LIMIT < 1) {
         try { FEEDER_MACHINE_ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("feederMachineEtc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FEEDER_MACHINE_ETC_UPPER_LIMIT = 200; }
      }
      if (feederMachineEtc != null && !wt.fc.PersistenceHelper.checkStoredLength(feederMachineEtc.toString(), FEEDER_MACHINE_ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "feederMachineEtc"), java.lang.String.valueOf(java.lang.Math.min(FEEDER_MACHINE_ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "feederMachineEtc", this.feederMachineEtc, feederMachineEtc));
   }

   /**
    * 검사결과
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String TEST_RESULT = "testResult";
   static int TEST_RESULT_UPPER_LIMIT = -1;
   java.lang.String testResult;
   /**
    * 검사결과
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public java.lang.String getTestResult() {
      return testResult;
   }
   /**
    * 검사결과
    *
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setTestResult(java.lang.String testResult) throws wt.util.WTPropertyVetoException {
      testResultValidate(testResult);
      this.testResult = testResult;
   }
   void testResultValidate(java.lang.String testResult) throws wt.util.WTPropertyVetoException {
      if (TEST_RESULT_UPPER_LIMIT < 1) {
         try { TEST_RESULT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("testResult").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TEST_RESULT_UPPER_LIMIT = 200; }
      }
      if (testResult != null && !wt.fc.PersistenceHelper.checkStoredLength(testResult.toString(), TEST_RESULT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "testResult"), java.lang.String.valueOf(java.lang.Math.min(TEST_RESULT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "testResult", this.testResult, testResult));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_STRUC = "moldStruc";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MOLD_STRUC_REFERENCE = "moldStrucReference";
   wt.fc.ObjectReference moldStrucReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public e3ps.common.code.NumberCode getMoldStruc() {
      return (moldStrucReference != null) ? (e3ps.common.code.NumberCode) moldStrucReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public wt.fc.ObjectReference getMoldStrucReference() {
      return moldStrucReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldStruc(e3ps.common.code.NumberCode the_moldStruc) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldStrucReference(the_moldStruc == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_moldStruc));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMoldStrucReference(wt.fc.ObjectReference the_moldStrucReference) throws wt.util.WTPropertyVetoException {
      moldStrucReferenceValidate(the_moldStrucReference);
      moldStrucReference = (wt.fc.ObjectReference) the_moldStrucReference;
   }
   void moldStrucReferenceValidate(wt.fc.ObjectReference the_moldStrucReference) throws wt.util.WTPropertyVetoException {
      if (the_moldStrucReference == null || the_moldStrucReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldStrucReference") },
               new java.beans.PropertyChangeEvent(this, "moldStrucReference", this.moldStrucReference, moldStrucReference));
      if (the_moldStrucReference != null && the_moldStrucReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_moldStrucReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldStrucReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldStrucReference", this.moldStrucReference, moldStrucReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PRODUCT_METHOD = "productMethod";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PRODUCT_METHOD_REFERENCE = "productMethodReference";
   wt.fc.ObjectReference productMethodReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public e3ps.common.code.NumberCode getProductMethod() {
      return (productMethodReference != null) ? (e3ps.common.code.NumberCode) productMethodReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public wt.fc.ObjectReference getProductMethodReference() {
      return productMethodReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setProductMethod(e3ps.common.code.NumberCode the_productMethod) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProductMethodReference(the_productMethod == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_productMethod));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setProductMethodReference(wt.fc.ObjectReference the_productMethodReference) throws wt.util.WTPropertyVetoException {
      productMethodReferenceValidate(the_productMethodReference);
      productMethodReference = (wt.fc.ObjectReference) the_productMethodReference;
   }
   void productMethodReferenceValidate(wt.fc.ObjectReference the_productMethodReference) throws wt.util.WTPropertyVetoException {
      if (the_productMethodReference == null || the_productMethodReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productMethodReference") },
               new java.beans.PropertyChangeEvent(this, "productMethodReference", this.productMethodReference, productMethodReference));
      if (the_productMethodReference != null && the_productMethodReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_productMethodReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productMethodReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "productMethodReference", this.productMethodReference, productMethodReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String SCRAP_PROCESS = "scrapProcess";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String SCRAP_PROCESS_REFERENCE = "scrapProcessReference";
   wt.fc.ObjectReference scrapProcessReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public e3ps.common.code.NumberCode getScrapProcess() {
      return (scrapProcessReference != null) ? (e3ps.common.code.NumberCode) scrapProcessReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public wt.fc.ObjectReference getScrapProcessReference() {
      return scrapProcessReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setScrapProcess(e3ps.common.code.NumberCode the_scrapProcess) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setScrapProcessReference(the_scrapProcess == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_scrapProcess));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setScrapProcessReference(wt.fc.ObjectReference the_scrapProcessReference) throws wt.util.WTPropertyVetoException {
      scrapProcessReferenceValidate(the_scrapProcessReference);
      scrapProcessReference = (wt.fc.ObjectReference) the_scrapProcessReference;
   }
   void scrapProcessReferenceValidate(wt.fc.ObjectReference the_scrapProcessReference) throws wt.util.WTPropertyVetoException {
      if (the_scrapProcessReference == null || the_scrapProcessReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapProcessReference") },
               new java.beans.PropertyChangeEvent(this, "scrapProcessReference", this.scrapProcessReference, scrapProcessReference));
      if (the_scrapProcessReference != null && the_scrapProcessReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_scrapProcessReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapProcessReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "scrapProcessReference", this.scrapProcessReference, scrapProcessReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MATERIAL = "material";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String MATERIAL_REFERENCE = "materialReference";
   wt.fc.ObjectReference materialReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public e3ps.project.material.MoldMaterial getMaterial() {
      return (materialReference != null) ? (e3ps.project.material.MoldMaterial) materialReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public wt.fc.ObjectReference getMaterialReference() {
      return materialReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMaterial(e3ps.project.material.MoldMaterial the_material) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMaterialReference(the_material == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.material.MoldMaterial) the_material));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setMaterialReference(wt.fc.ObjectReference the_materialReference) throws wt.util.WTPropertyVetoException {
      materialReferenceValidate(the_materialReference);
      materialReference = (wt.fc.ObjectReference) the_materialReference;
   }
   void materialReferenceValidate(wt.fc.ObjectReference the_materialReference) throws wt.util.WTPropertyVetoException {
      if (the_materialReference == null || the_materialReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialReference") },
               new java.beans.PropertyChangeEvent(this, "materialReference", this.materialReference, materialReference));
      if (the_materialReference != null && the_materialReference.getReferencedClass() != null &&
            !e3ps.project.material.MoldMaterial.class.isAssignableFrom(the_materialReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "materialReference", this.materialReference, materialReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PUNCHING_OIL = "punchingOil";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public static final java.lang.String PUNCHING_OIL_REFERENCE = "punchingOilReference";
   wt.fc.ObjectReference punchingOilReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public e3ps.common.code.NumberCode getPunchingOil() {
      return (punchingOilReference != null) ? (e3ps.common.code.NumberCode) punchingOilReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public wt.fc.ObjectReference getPunchingOilReference() {
      return punchingOilReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setPunchingOil(e3ps.common.code.NumberCode the_punchingOil) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPunchingOilReference(the_punchingOil == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_punchingOil));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryPress
    */
   public void setPunchingOilReference(wt.fc.ObjectReference the_punchingOilReference) throws wt.util.WTPropertyVetoException {
      punchingOilReferenceValidate(the_punchingOilReference);
      punchingOilReference = (wt.fc.ObjectReference) the_punchingOilReference;
   }
   void punchingOilReferenceValidate(wt.fc.ObjectReference the_punchingOilReference) throws wt.util.WTPropertyVetoException {
      if (the_punchingOilReference == null || the_punchingOilReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "punchingOilReference") },
               new java.beans.PropertyChangeEvent(this, "punchingOilReference", this.punchingOilReference, punchingOilReference));
      if (the_punchingOilReference != null && the_punchingOilReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_punchingOilReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "punchingOilReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "punchingOilReference", this.punchingOilReference, punchingOilReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -4745081748411951392L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( bolsterHeight );
      output.writeObject( bolsterWidth );
      output.writeObject( dieHeight );
      output.writeObject( feederMachine );
      output.writeObject( feederMachineEtc );
      output.writeObject( materialReference );
      output.writeObject( moldSizeHeight );
      output.writeObject( moldSizeLength );
      output.writeObject( moldSizeWidth );
      output.writeObject( moldStrucEtc );
      output.writeObject( moldStrucReference );
      output.writeObject( moldWeightLower );
      output.writeObject( moldWeightTop );
      output.writeObject( pitch );
      output.writeObject( plating );
      output.writeObject( press );
      output.writeObject( productMethodEtc );
      output.writeObject( productMethodReference );
      output.writeObject( productOutputCol );
      output.writeObject( productOutputPitch );
      output.writeObject( punchingCount );
      output.writeObject( punchingOilReference );
      output.writeObject( punchingSpeed );
      output.writeObject( saftySensor );
      output.writeObject( scrapProcessReference );
      output.writeObject( spm );
      output.writeObject( stroke );
      output.writeObject( testResult );
      output.writeObject( thickness );
      output.writeObject( tone );
   }

   protected void super_writeExternal_KETTryPress(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.trycondition.entity.KETTryPress) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETTryPress(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "bolsterHeight", bolsterHeight );
      output.setString( "bolsterWidth", bolsterWidth );
      output.setString( "dieHeight", dieHeight );
      output.setString( "feederMachine", feederMachine );
      output.setString( "feederMachineEtc", feederMachineEtc );
      output.writeObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      output.setString( "moldSizeHeight", moldSizeHeight );
      output.setString( "moldSizeLength", moldSizeLength );
      output.setString( "moldSizeWidth", moldSizeWidth );
      output.setString( "moldStrucEtc", moldStrucEtc );
      output.writeObject( "moldStrucReference", moldStrucReference, wt.fc.ObjectReference.class, true );
      output.setString( "moldWeightLower", moldWeightLower );
      output.setString( "moldWeightTop", moldWeightTop );
      output.setString( "pitch", pitch );
      output.setString( "plating", plating );
      output.setString( "press", press );
      output.setString( "productMethodEtc", productMethodEtc );
      output.writeObject( "productMethodReference", productMethodReference, wt.fc.ObjectReference.class, true );
      output.setString( "productOutputCol", productOutputCol );
      output.setString( "productOutputPitch", productOutputPitch );
      output.setString( "punchingCount", punchingCount );
      output.writeObject( "punchingOilReference", punchingOilReference, wt.fc.ObjectReference.class, true );
      output.setString( "punchingSpeed", punchingSpeed );
      output.setString( "saftySensor", saftySensor );
      output.writeObject( "scrapProcessReference", scrapProcessReference, wt.fc.ObjectReference.class, true );
      output.setString( "spm", spm );
      output.setString( "stroke", stroke );
      output.setString( "testResult", testResult );
      output.setString( "thickness", thickness );
      output.setString( "tone", tone );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      bolsterHeight = input.getString( "bolsterHeight" );
      bolsterWidth = input.getString( "bolsterWidth" );
      dieHeight = input.getString( "dieHeight" );
      feederMachine = input.getString( "feederMachine" );
      feederMachineEtc = input.getString( "feederMachineEtc" );
      materialReference = (wt.fc.ObjectReference) input.readObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      moldSizeHeight = input.getString( "moldSizeHeight" );
      moldSizeLength = input.getString( "moldSizeLength" );
      moldSizeWidth = input.getString( "moldSizeWidth" );
      moldStrucEtc = input.getString( "moldStrucEtc" );
      moldStrucReference = (wt.fc.ObjectReference) input.readObject( "moldStrucReference", moldStrucReference, wt.fc.ObjectReference.class, true );
      moldWeightLower = input.getString( "moldWeightLower" );
      moldWeightTop = input.getString( "moldWeightTop" );
      pitch = input.getString( "pitch" );
      plating = input.getString( "plating" );
      press = input.getString( "press" );
      productMethodEtc = input.getString( "productMethodEtc" );
      productMethodReference = (wt.fc.ObjectReference) input.readObject( "productMethodReference", productMethodReference, wt.fc.ObjectReference.class, true );
      productOutputCol = input.getString( "productOutputCol" );
      productOutputPitch = input.getString( "productOutputPitch" );
      punchingCount = input.getString( "punchingCount" );
      punchingOilReference = (wt.fc.ObjectReference) input.readObject( "punchingOilReference", punchingOilReference, wt.fc.ObjectReference.class, true );
      punchingSpeed = input.getString( "punchingSpeed" );
      saftySensor = input.getString( "saftySensor" );
      scrapProcessReference = (wt.fc.ObjectReference) input.readObject( "scrapProcessReference", scrapProcessReference, wt.fc.ObjectReference.class, true );
      spm = input.getString( "spm" );
      stroke = input.getString( "stroke" );
      testResult = input.getString( "testResult" );
      thickness = input.getString( "thickness" );
      tone = input.getString( "tone" );
   }

   boolean readVersion_4745081748411951392L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      bolsterHeight = (java.lang.String) input.readObject();
      bolsterWidth = (java.lang.String) input.readObject();
      dieHeight = (java.lang.String) input.readObject();
      feederMachine = (java.lang.String) input.readObject();
      feederMachineEtc = (java.lang.String) input.readObject();
      materialReference = (wt.fc.ObjectReference) input.readObject();
      moldSizeHeight = (java.lang.String) input.readObject();
      moldSizeLength = (java.lang.String) input.readObject();
      moldSizeWidth = (java.lang.String) input.readObject();
      moldStrucEtc = (java.lang.String) input.readObject();
      moldStrucReference = (wt.fc.ObjectReference) input.readObject();
      moldWeightLower = (java.lang.String) input.readObject();
      moldWeightTop = (java.lang.String) input.readObject();
      pitch = (java.lang.String) input.readObject();
      plating = (java.lang.String) input.readObject();
      press = (java.lang.String) input.readObject();
      productMethodEtc = (java.lang.String) input.readObject();
      productMethodReference = (wt.fc.ObjectReference) input.readObject();
      productOutputCol = (java.lang.String) input.readObject();
      productOutputPitch = (java.lang.String) input.readObject();
      punchingCount = (java.lang.String) input.readObject();
      punchingOilReference = (wt.fc.ObjectReference) input.readObject();
      punchingSpeed = (java.lang.String) input.readObject();
      saftySensor = (java.lang.String) input.readObject();
      scrapProcessReference = (wt.fc.ObjectReference) input.readObject();
      spm = (java.lang.String) input.readObject();
      stroke = (java.lang.String) input.readObject();
      testResult = (java.lang.String) input.readObject();
      thickness = (java.lang.String) input.readObject();
      tone = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETTryPress thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4745081748411951392L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETTryPress( _KETTryPress thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
