package ext.ket.project.trycondition.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETTryMold extends ext.ket.project.trycondition.entity.KETTryCondition implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.trycondition.entity.entityResource";
   static final java.lang.String CLASSNAME = KETTryMold.class.getName();

   /**
    * 종류/Grade
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String GRADE = "grade";
   static int GRADE_UPPER_LIMIT = -1;
   java.lang.String grade;
   /**
    * 종류/Grade
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getGrade() {
      return grade;
   }
   /**
    * 종류/Grade
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setGrade(java.lang.String grade) throws wt.util.WTPropertyVetoException {
      gradeValidate(grade);
      this.grade = grade;
   }
   void gradeValidate(java.lang.String grade) throws wt.util.WTPropertyVetoException {
      if (GRADE_UPPER_LIMIT < 1) {
         try { GRADE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("grade").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GRADE_UPPER_LIMIT = 200; }
      }
      if (grade != null && !wt.fc.PersistenceHelper.checkStoredLength(grade.toString(), GRADE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "grade"), java.lang.String.valueOf(java.lang.Math.min(GRADE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "grade", this.grade, grade));
   }

   /**
    * color
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String COLOR = "color";
   static int COLOR_UPPER_LIMIT = -1;
   java.lang.String color;
   /**
    * color
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getColor() {
      return color;
   }
   /**
    * color
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setColor(java.lang.String color) throws wt.util.WTPropertyVetoException {
      colorValidate(color);
      this.color = color;
   }
   void colorValidate(java.lang.String color) throws wt.util.WTPropertyVetoException {
      if (COLOR_UPPER_LIMIT < 1) {
         try { COLOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("color").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COLOR_UPPER_LIMIT = 200; }
      }
      if (color != null && !wt.fc.PersistenceHelper.checkStoredLength(color.toString(), COLOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "color"), java.lang.String.valueOf(java.lang.Math.min(COLOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "color", this.color, color));
   }

   /**
    * 건조온도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String DRY_TEMP = "dryTemp";
   static int DRY_TEMP_UPPER_LIMIT = -1;
   java.lang.String dryTemp;
   /**
    * 건조온도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getDryTemp() {
      return dryTemp;
   }
   /**
    * 건조온도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setDryTemp(java.lang.String dryTemp) throws wt.util.WTPropertyVetoException {
      dryTempValidate(dryTemp);
      this.dryTemp = dryTemp;
   }
   void dryTempValidate(java.lang.String dryTemp) throws wt.util.WTPropertyVetoException {
      if (DRY_TEMP_UPPER_LIMIT < 1) {
         try { DRY_TEMP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dryTemp").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DRY_TEMP_UPPER_LIMIT = 200; }
      }
      if (dryTemp != null && !wt.fc.PersistenceHelper.checkStoredLength(dryTemp.toString(), DRY_TEMP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dryTemp"), java.lang.String.valueOf(java.lang.Math.min(DRY_TEMP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dryTemp", this.dryTemp, dryTemp));
   }

   /**
    * 건조시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String DRY_TIME = "dryTime";
   static int DRY_TIME_UPPER_LIMIT = -1;
   java.lang.String dryTime;
   /**
    * 건조시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getDryTime() {
      return dryTime;
   }
   /**
    * 건조시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setDryTime(java.lang.String dryTime) throws wt.util.WTPropertyVetoException {
      dryTimeValidate(dryTime);
      this.dryTime = dryTime;
   }
   void dryTimeValidate(java.lang.String dryTime) throws wt.util.WTPropertyVetoException {
      if (DRY_TIME_UPPER_LIMIT < 1) {
         try { DRY_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dryTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DRY_TIME_UPPER_LIMIT = 200; }
      }
      if (dryTime != null && !wt.fc.PersistenceHelper.checkStoredLength(dryTime.toString(), DRY_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dryTime"), java.lang.String.valueOf(java.lang.Math.min(DRY_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dryTime", this.dryTime, dryTime));
   }

   /**
    * 수분율
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOISTURE_RATE = "moistureRate";
   static int MOISTURE_RATE_UPPER_LIMIT = -1;
   java.lang.String moistureRate;
   /**
    * 수분율
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoistureRate() {
      return moistureRate;
   }
   /**
    * 수분율
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoistureRate(java.lang.String moistureRate) throws wt.util.WTPropertyVetoException {
      moistureRateValidate(moistureRate);
      this.moistureRate = moistureRate;
   }
   void moistureRateValidate(java.lang.String moistureRate) throws wt.util.WTPropertyVetoException {
      if (MOISTURE_RATE_UPPER_LIMIT < 1) {
         try { MOISTURE_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moistureRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOISTURE_RATE_UPPER_LIMIT = 200; }
      }
      if (moistureRate != null && !wt.fc.PersistenceHelper.checkStoredLength(moistureRate.toString(), MOISTURE_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moistureRate"), java.lang.String.valueOf(java.lang.Math.min(MOISTURE_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moistureRate", this.moistureRate, moistureRate));
   }

   /**
    * 금형구조(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_STRUC_ETC = "moldStrucEtc";
   static int MOLD_STRUC_ETC_UPPER_LIMIT = -1;
   java.lang.String moldStrucEtc;
   /**
    * 금형구조(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldStrucEtc() {
      return moldStrucEtc;
   }
   /**
    * 금형구조(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
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
    * MoldBaseSize(가로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_BASE_SIZE_WIDTH = "moldBaseSizeWidth";
   static int MOLD_BASE_SIZE_WIDTH_UPPER_LIMIT = -1;
   java.lang.String moldBaseSizeWidth;
   /**
    * MoldBaseSize(가로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldBaseSizeWidth() {
      return moldBaseSizeWidth;
   }
   /**
    * MoldBaseSize(가로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldBaseSizeWidth(java.lang.String moldBaseSizeWidth) throws wt.util.WTPropertyVetoException {
      moldBaseSizeWidthValidate(moldBaseSizeWidth);
      this.moldBaseSizeWidth = moldBaseSizeWidth;
   }
   void moldBaseSizeWidthValidate(java.lang.String moldBaseSizeWidth) throws wt.util.WTPropertyVetoException {
      if (MOLD_BASE_SIZE_WIDTH_UPPER_LIMIT < 1) {
         try { MOLD_BASE_SIZE_WIDTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldBaseSizeWidth").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_BASE_SIZE_WIDTH_UPPER_LIMIT = 200; }
      }
      if (moldBaseSizeWidth != null && !wt.fc.PersistenceHelper.checkStoredLength(moldBaseSizeWidth.toString(), MOLD_BASE_SIZE_WIDTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldBaseSizeWidth"), java.lang.String.valueOf(java.lang.Math.min(MOLD_BASE_SIZE_WIDTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldBaseSizeWidth", this.moldBaseSizeWidth, moldBaseSizeWidth));
   }

   /**
    * MoldBaseSize(세로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_BASE_SIZE_LENGTH = "moldBaseSizeLength";
   static int MOLD_BASE_SIZE_LENGTH_UPPER_LIMIT = -1;
   java.lang.String moldBaseSizeLength;
   /**
    * MoldBaseSize(세로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldBaseSizeLength() {
      return moldBaseSizeLength;
   }
   /**
    * MoldBaseSize(세로)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldBaseSizeLength(java.lang.String moldBaseSizeLength) throws wt.util.WTPropertyVetoException {
      moldBaseSizeLengthValidate(moldBaseSizeLength);
      this.moldBaseSizeLength = moldBaseSizeLength;
   }
   void moldBaseSizeLengthValidate(java.lang.String moldBaseSizeLength) throws wt.util.WTPropertyVetoException {
      if (MOLD_BASE_SIZE_LENGTH_UPPER_LIMIT < 1) {
         try { MOLD_BASE_SIZE_LENGTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldBaseSizeLength").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_BASE_SIZE_LENGTH_UPPER_LIMIT = 200; }
      }
      if (moldBaseSizeLength != null && !wt.fc.PersistenceHelper.checkStoredLength(moldBaseSizeLength.toString(), MOLD_BASE_SIZE_LENGTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldBaseSizeLength"), java.lang.String.valueOf(java.lang.Math.min(MOLD_BASE_SIZE_LENGTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldBaseSizeLength", this.moldBaseSizeLength, moldBaseSizeLength));
   }

   /**
    * MoldBaseSize(높이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_BASE_SIZE_HEIGHT = "moldBaseSizeHeight";
   static int MOLD_BASE_SIZE_HEIGHT_UPPER_LIMIT = -1;
   java.lang.String moldBaseSizeHeight;
   /**
    * MoldBaseSize(높이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldBaseSizeHeight() {
      return moldBaseSizeHeight;
   }
   /**
    * MoldBaseSize(높이)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldBaseSizeHeight(java.lang.String moldBaseSizeHeight) throws wt.util.WTPropertyVetoException {
      moldBaseSizeHeightValidate(moldBaseSizeHeight);
      this.moldBaseSizeHeight = moldBaseSizeHeight;
   }
   void moldBaseSizeHeightValidate(java.lang.String moldBaseSizeHeight) throws wt.util.WTPropertyVetoException {
      if (MOLD_BASE_SIZE_HEIGHT_UPPER_LIMIT < 1) {
         try { MOLD_BASE_SIZE_HEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldBaseSizeHeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_BASE_SIZE_HEIGHT_UPPER_LIMIT = 200; }
      }
      if (moldBaseSizeHeight != null && !wt.fc.PersistenceHelper.checkStoredLength(moldBaseSizeHeight.toString(), MOLD_BASE_SIZE_HEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldBaseSizeHeight"), java.lang.String.valueOf(java.lang.Math.min(MOLD_BASE_SIZE_HEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldBaseSizeHeight", this.moldBaseSizeHeight, moldBaseSizeHeight));
   }

   /**
    * 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String WEIGHT = "weight";
   static int WEIGHT_UPPER_LIMIT = -1;
   java.lang.String weight;
   /**
    * 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getWeight() {
      return weight;
   }
   /**
    * 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setWeight(java.lang.String weight) throws wt.util.WTPropertyVetoException {
      weightValidate(weight);
      this.weight = weight;
   }
   void weightValidate(java.lang.String weight) throws wt.util.WTPropertyVetoException {
      if (WEIGHT_UPPER_LIMIT < 1) {
         try { WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("weight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WEIGHT_UPPER_LIMIT = 200; }
      }
      if (weight != null && !wt.fc.PersistenceHelper.checkStoredLength(weight.toString(), WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "weight"), java.lang.String.valueOf(java.lang.Math.min(WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "weight", this.weight, weight));
   }

   /**
    * cavity수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CAVITY_COUNT = "cavityCount";
   static int CAVITY_COUNT_UPPER_LIMIT = -1;
   java.lang.String cavityCount;
   /**
    * cavity수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCavityCount() {
      return cavityCount;
   }
   /**
    * cavity수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCavityCount(java.lang.String cavityCount) throws wt.util.WTPropertyVetoException {
      cavityCountValidate(cavityCount);
      this.cavityCount = cavityCount;
   }
   void cavityCountValidate(java.lang.String cavityCount) throws wt.util.WTPropertyVetoException {
      if (CAVITY_COUNT_UPPER_LIMIT < 1) {
         try { CAVITY_COUNT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cavityCount").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAVITY_COUNT_UPPER_LIMIT = 200; }
      }
      if (cavityCount != null && !wt.fc.PersistenceHelper.checkStoredLength(cavityCount.toString(), CAVITY_COUNT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cavityCount"), java.lang.String.valueOf(java.lang.Math.min(CAVITY_COUNT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cavityCount", this.cavityCount, cavityCount));
   }

   /**
    * 취부판두께(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOUNT_THICKNESS_ETC = "mountThicknessEtc";
   static int MOUNT_THICKNESS_ETC_UPPER_LIMIT = -1;
   java.lang.String mountThicknessEtc;
   /**
    * 취부판두께(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMountThicknessEtc() {
      return mountThicknessEtc;
   }
   /**
    * 취부판두께(기타)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMountThicknessEtc(java.lang.String mountThicknessEtc) throws wt.util.WTPropertyVetoException {
      mountThicknessEtcValidate(mountThicknessEtc);
      this.mountThicknessEtc = mountThicknessEtc;
   }
   void mountThicknessEtcValidate(java.lang.String mountThicknessEtc) throws wt.util.WTPropertyVetoException {
      if (MOUNT_THICKNESS_ETC_UPPER_LIMIT < 1) {
         try { MOUNT_THICKNESS_ETC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mountThicknessEtc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOUNT_THICKNESS_ETC_UPPER_LIMIT = 200; }
      }
      if (mountThicknessEtc != null && !wt.fc.PersistenceHelper.checkStoredLength(mountThicknessEtc.toString(), MOUNT_THICKNESS_ETC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mountThicknessEtc"), java.lang.String.valueOf(java.lang.Math.min(MOUNT_THICKNESS_ETC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mountThicknessEtc", this.mountThicknessEtc, mountThicknessEtc));
   }

   /**
    * 기계사양
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MACHINE_SPEC = "machineSpec";
   static int MACHINE_SPEC_UPPER_LIMIT = -1;
   java.lang.String machineSpec;
   /**
    * 기계사양
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMachineSpec() {
      return machineSpec;
   }
   /**
    * 기계사양
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMachineSpec(java.lang.String machineSpec) throws wt.util.WTPropertyVetoException {
      machineSpecValidate(machineSpec);
      this.machineSpec = machineSpec;
   }
   void machineSpecValidate(java.lang.String machineSpec) throws wt.util.WTPropertyVetoException {
      if (MACHINE_SPEC_UPPER_LIMIT < 1) {
         try { MACHINE_SPEC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("machineSpec").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MACHINE_SPEC_UPPER_LIMIT = 200; }
      }
      if (machineSpec != null && !wt.fc.PersistenceHelper.checkStoredLength(machineSpec.toString(), MACHINE_SPEC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "machineSpec"), java.lang.String.valueOf(java.lang.Math.min(MACHINE_SPEC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "machineSpec", this.machineSpec, machineSpec));
   }

   /**
    * Screw직경
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SCREW_DIAMETER = "screwDiameter";
   static int SCREW_DIAMETER_UPPER_LIMIT = -1;
   java.lang.String screwDiameter;
   /**
    * Screw직경
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getScrewDiameter() {
      return screwDiameter;
   }
   /**
    * Screw직경
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setScrewDiameter(java.lang.String screwDiameter) throws wt.util.WTPropertyVetoException {
      screwDiameterValidate(screwDiameter);
      this.screwDiameter = screwDiameter;
   }
   void screwDiameterValidate(java.lang.String screwDiameter) throws wt.util.WTPropertyVetoException {
      if (SCREW_DIAMETER_UPPER_LIMIT < 1) {
         try { SCREW_DIAMETER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("screwDiameter").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCREW_DIAMETER_UPPER_LIMIT = 200; }
      }
      if (screwDiameter != null && !wt.fc.PersistenceHelper.checkStoredLength(screwDiameter.toString(), SCREW_DIAMETER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "screwDiameter"), java.lang.String.valueOf(java.lang.Math.min(SCREW_DIAMETER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "screwDiameter", this.screwDiameter, screwDiameter));
   }

   /**
    * 노즐타입
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String NOZZLE_TYPE = "nozzleType";
   static int NOZZLE_TYPE_UPPER_LIMIT = -1;
   java.lang.String nozzleType;
   /**
    * 노즐타입
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getNozzleType() {
      return nozzleType;
   }
   /**
    * 노즐타입
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setNozzleType(java.lang.String nozzleType) throws wt.util.WTPropertyVetoException {
      nozzleTypeValidate(nozzleType);
      this.nozzleType = nozzleType;
   }
   void nozzleTypeValidate(java.lang.String nozzleType) throws wt.util.WTPropertyVetoException {
      if (NOZZLE_TYPE_UPPER_LIMIT < 1) {
         try { NOZZLE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("nozzleType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NOZZLE_TYPE_UPPER_LIMIT = 200; }
      }
      if (nozzleType != null && !wt.fc.PersistenceHelper.checkStoredLength(nozzleType.toString(), NOZZLE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "nozzleType"), java.lang.String.valueOf(java.lang.Math.min(NOZZLE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "nozzleType", this.nozzleType, nozzleType));
   }

   /**
    * 유온
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String OIL_TEMP = "oilTemp";
   static int OIL_TEMP_UPPER_LIMIT = -1;
   java.lang.String oilTemp;
   /**
    * 유온
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getOilTemp() {
      return oilTemp;
   }
   /**
    * 유온
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setOilTemp(java.lang.String oilTemp) throws wt.util.WTPropertyVetoException {
      oilTempValidate(oilTemp);
      this.oilTemp = oilTemp;
   }
   void oilTempValidate(java.lang.String oilTemp) throws wt.util.WTPropertyVetoException {
      if (OIL_TEMP_UPPER_LIMIT < 1) {
         try { OIL_TEMP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("oilTemp").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OIL_TEMP_UPPER_LIMIT = 200; }
      }
      if (oilTemp != null && !wt.fc.PersistenceHelper.checkStoredLength(oilTemp.toString(), OIL_TEMP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oilTemp"), java.lang.String.valueOf(java.lang.Math.min(OIL_TEMP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "oilTemp", this.oilTemp, oilTemp));
   }

   /**
    * 톤수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String TONE = "tone";
   static int TONE_UPPER_LIMIT = -1;
   java.lang.String tone;
   /**
    * 톤수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getTone() {
      return tone;
   }
   /**
    * 톤수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
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
    * 타이바 간격
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String TIEBAR_INTERVAL = "tiebarInterval";
   static int TIEBAR_INTERVAL_UPPER_LIMIT = -1;
   java.lang.String tiebarInterval;
   /**
    * 타이바 간격
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getTiebarInterval() {
      return tiebarInterval;
   }
   /**
    * 타이바 간격
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setTiebarInterval(java.lang.String tiebarInterval) throws wt.util.WTPropertyVetoException {
      tiebarIntervalValidate(tiebarInterval);
      this.tiebarInterval = tiebarInterval;
   }
   void tiebarIntervalValidate(java.lang.String tiebarInterval) throws wt.util.WTPropertyVetoException {
      if (TIEBAR_INTERVAL_UPPER_LIMIT < 1) {
         try { TIEBAR_INTERVAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tiebarInterval").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TIEBAR_INTERVAL_UPPER_LIMIT = 200; }
      }
      if (tiebarInterval != null && !wt.fc.PersistenceHelper.checkStoredLength(tiebarInterval.toString(), TIEBAR_INTERVAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tiebarInterval"), java.lang.String.valueOf(java.lang.Math.min(TIEBAR_INTERVAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tiebarInterval", this.tiebarInterval, tiebarInterval));
   }

   /**
    * 실린더온도(NH)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CYLINDER_TEMP_NH = "cylinderTempNH";
   static int CYLINDER_TEMP_NH_UPPER_LIMIT = -1;
   java.lang.String cylinderTempNH;
   /**
    * 실린더온도(NH)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCylinderTempNH() {
      return cylinderTempNH;
   }
   /**
    * 실린더온도(NH)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCylinderTempNH(java.lang.String cylinderTempNH) throws wt.util.WTPropertyVetoException {
      cylinderTempNHValidate(cylinderTempNH);
      this.cylinderTempNH = cylinderTempNH;
   }
   void cylinderTempNHValidate(java.lang.String cylinderTempNH) throws wt.util.WTPropertyVetoException {
      if (CYLINDER_TEMP_NH_UPPER_LIMIT < 1) {
         try { CYLINDER_TEMP_NH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cylinderTempNH").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CYLINDER_TEMP_NH_UPPER_LIMIT = 200; }
      }
      if (cylinderTempNH != null && !wt.fc.PersistenceHelper.checkStoredLength(cylinderTempNH.toString(), CYLINDER_TEMP_NH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cylinderTempNH"), java.lang.String.valueOf(java.lang.Math.min(CYLINDER_TEMP_NH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cylinderTempNH", this.cylinderTempNH, cylinderTempNH));
   }

   /**
    * 실린더온도(N1)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CYLINDER_TEMP_N1 = "cylinderTempN1";
   static int CYLINDER_TEMP_N1_UPPER_LIMIT = -1;
   java.lang.String cylinderTempN1;
   /**
    * 실린더온도(N1)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCylinderTempN1() {
      return cylinderTempN1;
   }
   /**
    * 실린더온도(N1)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCylinderTempN1(java.lang.String cylinderTempN1) throws wt.util.WTPropertyVetoException {
      cylinderTempN1Validate(cylinderTempN1);
      this.cylinderTempN1 = cylinderTempN1;
   }
   void cylinderTempN1Validate(java.lang.String cylinderTempN1) throws wt.util.WTPropertyVetoException {
      if (CYLINDER_TEMP_N1_UPPER_LIMIT < 1) {
         try { CYLINDER_TEMP_N1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cylinderTempN1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CYLINDER_TEMP_N1_UPPER_LIMIT = 200; }
      }
      if (cylinderTempN1 != null && !wt.fc.PersistenceHelper.checkStoredLength(cylinderTempN1.toString(), CYLINDER_TEMP_N1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cylinderTempN1"), java.lang.String.valueOf(java.lang.Math.min(CYLINDER_TEMP_N1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cylinderTempN1", this.cylinderTempN1, cylinderTempN1));
   }

   /**
    * 실린더온도(N2)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CYLINDER_TEMP_N2 = "cylinderTempN2";
   static int CYLINDER_TEMP_N2_UPPER_LIMIT = -1;
   java.lang.String cylinderTempN2;
   /**
    * 실린더온도(N2)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCylinderTempN2() {
      return cylinderTempN2;
   }
   /**
    * 실린더온도(N2)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCylinderTempN2(java.lang.String cylinderTempN2) throws wt.util.WTPropertyVetoException {
      cylinderTempN2Validate(cylinderTempN2);
      this.cylinderTempN2 = cylinderTempN2;
   }
   void cylinderTempN2Validate(java.lang.String cylinderTempN2) throws wt.util.WTPropertyVetoException {
      if (CYLINDER_TEMP_N2_UPPER_LIMIT < 1) {
         try { CYLINDER_TEMP_N2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cylinderTempN2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CYLINDER_TEMP_N2_UPPER_LIMIT = 200; }
      }
      if (cylinderTempN2 != null && !wt.fc.PersistenceHelper.checkStoredLength(cylinderTempN2.toString(), CYLINDER_TEMP_N2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cylinderTempN2"), java.lang.String.valueOf(java.lang.Math.min(CYLINDER_TEMP_N2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cylinderTempN2", this.cylinderTempN2, cylinderTempN2));
   }

   /**
    * 실린더온도(N3)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CYLINDER_TEMP_N3 = "cylinderTempN3";
   static int CYLINDER_TEMP_N3_UPPER_LIMIT = -1;
   java.lang.String cylinderTempN3;
   /**
    * 실린더온도(N3)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCylinderTempN3() {
      return cylinderTempN3;
   }
   /**
    * 실린더온도(N3)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCylinderTempN3(java.lang.String cylinderTempN3) throws wt.util.WTPropertyVetoException {
      cylinderTempN3Validate(cylinderTempN3);
      this.cylinderTempN3 = cylinderTempN3;
   }
   void cylinderTempN3Validate(java.lang.String cylinderTempN3) throws wt.util.WTPropertyVetoException {
      if (CYLINDER_TEMP_N3_UPPER_LIMIT < 1) {
         try { CYLINDER_TEMP_N3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cylinderTempN3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CYLINDER_TEMP_N3_UPPER_LIMIT = 200; }
      }
      if (cylinderTempN3 != null && !wt.fc.PersistenceHelper.checkStoredLength(cylinderTempN3.toString(), CYLINDER_TEMP_N3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cylinderTempN3"), java.lang.String.valueOf(java.lang.Math.min(CYLINDER_TEMP_N3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cylinderTempN3", this.cylinderTempN3, cylinderTempN3));
   }

   /**
    * 실린더온도(N4)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CYLINDER_TEMP_N4 = "cylinderTempN4";
   static int CYLINDER_TEMP_N4_UPPER_LIMIT = -1;
   java.lang.String cylinderTempN4;
   /**
    * 실린더온도(N4)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCylinderTempN4() {
      return cylinderTempN4;
   }
   /**
    * 실린더온도(N4)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCylinderTempN4(java.lang.String cylinderTempN4) throws wt.util.WTPropertyVetoException {
      cylinderTempN4Validate(cylinderTempN4);
      this.cylinderTempN4 = cylinderTempN4;
   }
   void cylinderTempN4Validate(java.lang.String cylinderTempN4) throws wt.util.WTPropertyVetoException {
      if (CYLINDER_TEMP_N4_UPPER_LIMIT < 1) {
         try { CYLINDER_TEMP_N4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cylinderTempN4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CYLINDER_TEMP_N4_UPPER_LIMIT = 200; }
      }
      if (cylinderTempN4 != null && !wt.fc.PersistenceHelper.checkStoredLength(cylinderTempN4.toString(), CYLINDER_TEMP_N4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cylinderTempN4"), java.lang.String.valueOf(java.lang.Math.min(CYLINDER_TEMP_N4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cylinderTempN4", this.cylinderTempN4, cylinderTempN4));
   }

   /**
    * 냉각수온도(상)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String COOL_WATER_TEMP_HIGH = "coolWaterTempHigh";
   static int COOL_WATER_TEMP_HIGH_UPPER_LIMIT = -1;
   java.lang.String coolWaterTempHigh;
   /**
    * 냉각수온도(상)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCoolWaterTempHigh() {
      return coolWaterTempHigh;
   }
   /**
    * 냉각수온도(상)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCoolWaterTempHigh(java.lang.String coolWaterTempHigh) throws wt.util.WTPropertyVetoException {
      coolWaterTempHighValidate(coolWaterTempHigh);
      this.coolWaterTempHigh = coolWaterTempHigh;
   }
   void coolWaterTempHighValidate(java.lang.String coolWaterTempHigh) throws wt.util.WTPropertyVetoException {
      if (COOL_WATER_TEMP_HIGH_UPPER_LIMIT < 1) {
         try { COOL_WATER_TEMP_HIGH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coolWaterTempHigh").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COOL_WATER_TEMP_HIGH_UPPER_LIMIT = 200; }
      }
      if (coolWaterTempHigh != null && !wt.fc.PersistenceHelper.checkStoredLength(coolWaterTempHigh.toString(), COOL_WATER_TEMP_HIGH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coolWaterTempHigh"), java.lang.String.valueOf(java.lang.Math.min(COOL_WATER_TEMP_HIGH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coolWaterTempHigh", this.coolWaterTempHigh, coolWaterTempHigh));
   }

   /**
    * 냉각수온도(하)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String COOL_WATER_TEMP_LOW = "coolWaterTempLow";
   static int COOL_WATER_TEMP_LOW_UPPER_LIMIT = -1;
   java.lang.String coolWaterTempLow;
   /**
    * 냉각수온도(하)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCoolWaterTempLow() {
      return coolWaterTempLow;
   }
   /**
    * 냉각수온도(하)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCoolWaterTempLow(java.lang.String coolWaterTempLow) throws wt.util.WTPropertyVetoException {
      coolWaterTempLowValidate(coolWaterTempLow);
      this.coolWaterTempLow = coolWaterTempLow;
   }
   void coolWaterTempLowValidate(java.lang.String coolWaterTempLow) throws wt.util.WTPropertyVetoException {
      if (COOL_WATER_TEMP_LOW_UPPER_LIMIT < 1) {
         try { COOL_WATER_TEMP_LOW_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coolWaterTempLow").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COOL_WATER_TEMP_LOW_UPPER_LIMIT = 200; }
      }
      if (coolWaterTempLow != null && !wt.fc.PersistenceHelper.checkStoredLength(coolWaterTempLow.toString(), COOL_WATER_TEMP_LOW_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coolWaterTempLow"), java.lang.String.valueOf(java.lang.Math.min(COOL_WATER_TEMP_LOW_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coolWaterTempLow", this.coolWaterTempLow, coolWaterTempLow));
   }

   /**
    * 사출압력(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_PRESS1 = "injectPress1";
   static int INJECT_PRESS1_UPPER_LIMIT = -1;
   java.lang.String injectPress1;
   /**
    * 사출압력(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectPress1() {
      return injectPress1;
   }
   /**
    * 사출압력(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectPress1(java.lang.String injectPress1) throws wt.util.WTPropertyVetoException {
      injectPress1Validate(injectPress1);
      this.injectPress1 = injectPress1;
   }
   void injectPress1Validate(java.lang.String injectPress1) throws wt.util.WTPropertyVetoException {
      if (INJECT_PRESS1_UPPER_LIMIT < 1) {
         try { INJECT_PRESS1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectPress1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_PRESS1_UPPER_LIMIT = 200; }
      }
      if (injectPress1 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectPress1.toString(), INJECT_PRESS1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectPress1"), java.lang.String.valueOf(java.lang.Math.min(INJECT_PRESS1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectPress1", this.injectPress1, injectPress1));
   }

   /**
    * 사출압력(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_PRESS2 = "injectPress2";
   static int INJECT_PRESS2_UPPER_LIMIT = -1;
   java.lang.String injectPress2;
   /**
    * 사출압력(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectPress2() {
      return injectPress2;
   }
   /**
    * 사출압력(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectPress2(java.lang.String injectPress2) throws wt.util.WTPropertyVetoException {
      injectPress2Validate(injectPress2);
      this.injectPress2 = injectPress2;
   }
   void injectPress2Validate(java.lang.String injectPress2) throws wt.util.WTPropertyVetoException {
      if (INJECT_PRESS2_UPPER_LIMIT < 1) {
         try { INJECT_PRESS2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectPress2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_PRESS2_UPPER_LIMIT = 200; }
      }
      if (injectPress2 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectPress2.toString(), INJECT_PRESS2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectPress2"), java.lang.String.valueOf(java.lang.Math.min(INJECT_PRESS2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectPress2", this.injectPress2, injectPress2));
   }

   /**
    * 사출압력(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_PRESS3 = "injectPress3";
   static int INJECT_PRESS3_UPPER_LIMIT = -1;
   java.lang.String injectPress3;
   /**
    * 사출압력(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectPress3() {
      return injectPress3;
   }
   /**
    * 사출압력(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectPress3(java.lang.String injectPress3) throws wt.util.WTPropertyVetoException {
      injectPress3Validate(injectPress3);
      this.injectPress3 = injectPress3;
   }
   void injectPress3Validate(java.lang.String injectPress3) throws wt.util.WTPropertyVetoException {
      if (INJECT_PRESS3_UPPER_LIMIT < 1) {
         try { INJECT_PRESS3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectPress3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_PRESS3_UPPER_LIMIT = 200; }
      }
      if (injectPress3 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectPress3.toString(), INJECT_PRESS3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectPress3"), java.lang.String.valueOf(java.lang.Math.min(INJECT_PRESS3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectPress3", this.injectPress3, injectPress3));
   }

   /**
    * 사출압력(4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_PRESS4 = "injectPress4";
   static int INJECT_PRESS4_UPPER_LIMIT = -1;
   java.lang.String injectPress4;
   /**
    * 사출압력(4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectPress4() {
      return injectPress4;
   }
   /**
    * 사출압력(4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectPress4(java.lang.String injectPress4) throws wt.util.WTPropertyVetoException {
      injectPress4Validate(injectPress4);
      this.injectPress4 = injectPress4;
   }
   void injectPress4Validate(java.lang.String injectPress4) throws wt.util.WTPropertyVetoException {
      if (INJECT_PRESS4_UPPER_LIMIT < 1) {
         try { INJECT_PRESS4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectPress4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_PRESS4_UPPER_LIMIT = 200; }
      }
      if (injectPress4 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectPress4.toString(), INJECT_PRESS4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectPress4"), java.lang.String.valueOf(java.lang.Math.min(INJECT_PRESS4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectPress4", this.injectPress4, injectPress4));
   }

   /**
    * 사출압력(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_PRESS5 = "injectPress5";
   static int INJECT_PRESS5_UPPER_LIMIT = -1;
   java.lang.String injectPress5;
   /**
    * 사출압력(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectPress5() {
      return injectPress5;
   }
   /**
    * 사출압력(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectPress5(java.lang.String injectPress5) throws wt.util.WTPropertyVetoException {
      injectPress5Validate(injectPress5);
      this.injectPress5 = injectPress5;
   }
   void injectPress5Validate(java.lang.String injectPress5) throws wt.util.WTPropertyVetoException {
      if (INJECT_PRESS5_UPPER_LIMIT < 1) {
         try { INJECT_PRESS5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectPress5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_PRESS5_UPPER_LIMIT = 200; }
      }
      if (injectPress5 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectPress5.toString(), INJECT_PRESS5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectPress5"), java.lang.String.valueOf(java.lang.Math.min(INJECT_PRESS5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectPress5", this.injectPress5, injectPress5));
   }

   /**
    * 사출속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_SPEED1 = "injectSpeed1";
   static int INJECT_SPEED1_UPPER_LIMIT = -1;
   java.lang.String injectSpeed1;
   /**
    * 사출속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectSpeed1() {
      return injectSpeed1;
   }
   /**
    * 사출속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectSpeed1(java.lang.String injectSpeed1) throws wt.util.WTPropertyVetoException {
      injectSpeed1Validate(injectSpeed1);
      this.injectSpeed1 = injectSpeed1;
   }
   void injectSpeed1Validate(java.lang.String injectSpeed1) throws wt.util.WTPropertyVetoException {
      if (INJECT_SPEED1_UPPER_LIMIT < 1) {
         try { INJECT_SPEED1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectSpeed1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_SPEED1_UPPER_LIMIT = 200; }
      }
      if (injectSpeed1 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectSpeed1.toString(), INJECT_SPEED1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectSpeed1"), java.lang.String.valueOf(java.lang.Math.min(INJECT_SPEED1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectSpeed1", this.injectSpeed1, injectSpeed1));
   }

   /**
    * 사출속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_SPEED2 = "injectSpeed2";
   static int INJECT_SPEED2_UPPER_LIMIT = -1;
   java.lang.String injectSpeed2;
   /**
    * 사출속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectSpeed2() {
      return injectSpeed2;
   }
   /**
    * 사출속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectSpeed2(java.lang.String injectSpeed2) throws wt.util.WTPropertyVetoException {
      injectSpeed2Validate(injectSpeed2);
      this.injectSpeed2 = injectSpeed2;
   }
   void injectSpeed2Validate(java.lang.String injectSpeed2) throws wt.util.WTPropertyVetoException {
      if (INJECT_SPEED2_UPPER_LIMIT < 1) {
         try { INJECT_SPEED2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectSpeed2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_SPEED2_UPPER_LIMIT = 200; }
      }
      if (injectSpeed2 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectSpeed2.toString(), INJECT_SPEED2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectSpeed2"), java.lang.String.valueOf(java.lang.Math.min(INJECT_SPEED2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectSpeed2", this.injectSpeed2, injectSpeed2));
   }

   /**
    * 사출속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_SPEED3 = "injectSpeed3";
   static int INJECT_SPEED3_UPPER_LIMIT = -1;
   java.lang.String injectSpeed3;
   /**
    * 사출속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectSpeed3() {
      return injectSpeed3;
   }
   /**
    * 사출속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectSpeed3(java.lang.String injectSpeed3) throws wt.util.WTPropertyVetoException {
      injectSpeed3Validate(injectSpeed3);
      this.injectSpeed3 = injectSpeed3;
   }
   void injectSpeed3Validate(java.lang.String injectSpeed3) throws wt.util.WTPropertyVetoException {
      if (INJECT_SPEED3_UPPER_LIMIT < 1) {
         try { INJECT_SPEED3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectSpeed3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_SPEED3_UPPER_LIMIT = 200; }
      }
      if (injectSpeed3 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectSpeed3.toString(), INJECT_SPEED3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectSpeed3"), java.lang.String.valueOf(java.lang.Math.min(INJECT_SPEED3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectSpeed3", this.injectSpeed3, injectSpeed3));
   }

   /**
    * 사출속도4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_SPEED4 = "injectSpeed4";
   static int INJECT_SPEED4_UPPER_LIMIT = -1;
   java.lang.String injectSpeed4;
   /**
    * 사출속도4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectSpeed4() {
      return injectSpeed4;
   }
   /**
    * 사출속도4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectSpeed4(java.lang.String injectSpeed4) throws wt.util.WTPropertyVetoException {
      injectSpeed4Validate(injectSpeed4);
      this.injectSpeed4 = injectSpeed4;
   }
   void injectSpeed4Validate(java.lang.String injectSpeed4) throws wt.util.WTPropertyVetoException {
      if (INJECT_SPEED4_UPPER_LIMIT < 1) {
         try { INJECT_SPEED4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectSpeed4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_SPEED4_UPPER_LIMIT = 200; }
      }
      if (injectSpeed4 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectSpeed4.toString(), INJECT_SPEED4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectSpeed4"), java.lang.String.valueOf(java.lang.Math.min(INJECT_SPEED4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectSpeed4", this.injectSpeed4, injectSpeed4));
   }

   /**
    * 사출속도(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_SPEED5 = "injectSpeed5";
   static int INJECT_SPEED5_UPPER_LIMIT = -1;
   java.lang.String injectSpeed5;
   /**
    * 사출속도(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInjectSpeed5() {
      return injectSpeed5;
   }
   /**
    * 사출속도(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectSpeed5(java.lang.String injectSpeed5) throws wt.util.WTPropertyVetoException {
      injectSpeed5Validate(injectSpeed5);
      this.injectSpeed5 = injectSpeed5;
   }
   void injectSpeed5Validate(java.lang.String injectSpeed5) throws wt.util.WTPropertyVetoException {
      if (INJECT_SPEED5_UPPER_LIMIT < 1) {
         try { INJECT_SPEED5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("injectSpeed5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INJECT_SPEED5_UPPER_LIMIT = 200; }
      }
      if (injectSpeed5 != null && !wt.fc.PersistenceHelper.checkStoredLength(injectSpeed5.toString(), INJECT_SPEED5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectSpeed5"), java.lang.String.valueOf(java.lang.Math.min(INJECT_SPEED5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "injectSpeed5", this.injectSpeed5, injectSpeed5));
   }

   /**
    * 다단거리전환(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SHORT_TRANSITION1 = "shortTransition1";
   static int SHORT_TRANSITION1_UPPER_LIMIT = -1;
   java.lang.String shortTransition1;
   /**
    * 다단거리전환(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getShortTransition1() {
      return shortTransition1;
   }
   /**
    * 다단거리전환(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setShortTransition1(java.lang.String shortTransition1) throws wt.util.WTPropertyVetoException {
      shortTransition1Validate(shortTransition1);
      this.shortTransition1 = shortTransition1;
   }
   void shortTransition1Validate(java.lang.String shortTransition1) throws wt.util.WTPropertyVetoException {
      if (SHORT_TRANSITION1_UPPER_LIMIT < 1) {
         try { SHORT_TRANSITION1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shortTransition1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHORT_TRANSITION1_UPPER_LIMIT = 200; }
      }
      if (shortTransition1 != null && !wt.fc.PersistenceHelper.checkStoredLength(shortTransition1.toString(), SHORT_TRANSITION1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shortTransition1"), java.lang.String.valueOf(java.lang.Math.min(SHORT_TRANSITION1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shortTransition1", this.shortTransition1, shortTransition1));
   }

   /**
    * 다단거리전환(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SHORT_TRANSITION2 = "shortTransition2";
   static int SHORT_TRANSITION2_UPPER_LIMIT = -1;
   java.lang.String shortTransition2;
   /**
    * 다단거리전환(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getShortTransition2() {
      return shortTransition2;
   }
   /**
    * 다단거리전환(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setShortTransition2(java.lang.String shortTransition2) throws wt.util.WTPropertyVetoException {
      shortTransition2Validate(shortTransition2);
      this.shortTransition2 = shortTransition2;
   }
   void shortTransition2Validate(java.lang.String shortTransition2) throws wt.util.WTPropertyVetoException {
      if (SHORT_TRANSITION2_UPPER_LIMIT < 1) {
         try { SHORT_TRANSITION2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shortTransition2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHORT_TRANSITION2_UPPER_LIMIT = 200; }
      }
      if (shortTransition2 != null && !wt.fc.PersistenceHelper.checkStoredLength(shortTransition2.toString(), SHORT_TRANSITION2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shortTransition2"), java.lang.String.valueOf(java.lang.Math.min(SHORT_TRANSITION2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shortTransition2", this.shortTransition2, shortTransition2));
   }

   /**
    * 다단거리전환(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SHORT_TRANSITION3 = "shortTransition3";
   static int SHORT_TRANSITION3_UPPER_LIMIT = -1;
   java.lang.String shortTransition3;
   /**
    * 다단거리전환(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getShortTransition3() {
      return shortTransition3;
   }
   /**
    * 다단거리전환(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setShortTransition3(java.lang.String shortTransition3) throws wt.util.WTPropertyVetoException {
      shortTransition3Validate(shortTransition3);
      this.shortTransition3 = shortTransition3;
   }
   void shortTransition3Validate(java.lang.String shortTransition3) throws wt.util.WTPropertyVetoException {
      if (SHORT_TRANSITION3_UPPER_LIMIT < 1) {
         try { SHORT_TRANSITION3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shortTransition3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHORT_TRANSITION3_UPPER_LIMIT = 200; }
      }
      if (shortTransition3 != null && !wt.fc.PersistenceHelper.checkStoredLength(shortTransition3.toString(), SHORT_TRANSITION3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shortTransition3"), java.lang.String.valueOf(java.lang.Math.min(SHORT_TRANSITION3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shortTransition3", this.shortTransition3, shortTransition3));
   }

   /**
    * 다단거리전환(4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SHORT_TRANSITION4 = "shortTransition4";
   static int SHORT_TRANSITION4_UPPER_LIMIT = -1;
   java.lang.String shortTransition4;
   /**
    * 다단거리전환(4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getShortTransition4() {
      return shortTransition4;
   }
   /**
    * 다단거리전환(4단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setShortTransition4(java.lang.String shortTransition4) throws wt.util.WTPropertyVetoException {
      shortTransition4Validate(shortTransition4);
      this.shortTransition4 = shortTransition4;
   }
   void shortTransition4Validate(java.lang.String shortTransition4) throws wt.util.WTPropertyVetoException {
      if (SHORT_TRANSITION4_UPPER_LIMIT < 1) {
         try { SHORT_TRANSITION4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shortTransition4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHORT_TRANSITION4_UPPER_LIMIT = 200; }
      }
      if (shortTransition4 != null && !wt.fc.PersistenceHelper.checkStoredLength(shortTransition4.toString(), SHORT_TRANSITION4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shortTransition4"), java.lang.String.valueOf(java.lang.Math.min(SHORT_TRANSITION4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shortTransition4", this.shortTransition4, shortTransition4));
   }

   /**
    * 다단거리전환(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SHORT_TRANSITION5 = "shortTransition5";
   static int SHORT_TRANSITION5_UPPER_LIMIT = -1;
   java.lang.String shortTransition5;
   /**
    * 다단거리전환(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getShortTransition5() {
      return shortTransition5;
   }
   /**
    * 다단거리전환(5단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setShortTransition5(java.lang.String shortTransition5) throws wt.util.WTPropertyVetoException {
      shortTransition5Validate(shortTransition5);
      this.shortTransition5 = shortTransition5;
   }
   void shortTransition5Validate(java.lang.String shortTransition5) throws wt.util.WTPropertyVetoException {
      if (SHORT_TRANSITION5_UPPER_LIMIT < 1) {
         try { SHORT_TRANSITION5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shortTransition5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHORT_TRANSITION5_UPPER_LIMIT = 200; }
      }
      if (shortTransition5 != null && !wt.fc.PersistenceHelper.checkStoredLength(shortTransition5.toString(), SHORT_TRANSITION5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shortTransition5"), java.lang.String.valueOf(java.lang.Math.min(SHORT_TRANSITION5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shortTransition5", this.shortTransition5, shortTransition5));
   }

   /**
    * 보압(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS1 = "holdPress1";
   static int HOLD_PRESS1_UPPER_LIMIT = -1;
   java.lang.String holdPress1;
   /**
    * 보압(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPress1() {
      return holdPress1;
   }
   /**
    * 보압(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPress1(java.lang.String holdPress1) throws wt.util.WTPropertyVetoException {
      holdPress1Validate(holdPress1);
      this.holdPress1 = holdPress1;
   }
   void holdPress1Validate(java.lang.String holdPress1) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS1_UPPER_LIMIT < 1) {
         try { HOLD_PRESS1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPress1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS1_UPPER_LIMIT = 200; }
      }
      if (holdPress1 != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPress1.toString(), HOLD_PRESS1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPress1"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPress1", this.holdPress1, holdPress1));
   }

   /**
    * 보압(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS2 = "holdPress2";
   static int HOLD_PRESS2_UPPER_LIMIT = -1;
   java.lang.String holdPress2;
   /**
    * 보압(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPress2() {
      return holdPress2;
   }
   /**
    * 보압(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPress2(java.lang.String holdPress2) throws wt.util.WTPropertyVetoException {
      holdPress2Validate(holdPress2);
      this.holdPress2 = holdPress2;
   }
   void holdPress2Validate(java.lang.String holdPress2) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS2_UPPER_LIMIT < 1) {
         try { HOLD_PRESS2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPress2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS2_UPPER_LIMIT = 200; }
      }
      if (holdPress2 != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPress2.toString(), HOLD_PRESS2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPress2"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPress2", this.holdPress2, holdPress2));
   }

   /**
    * 보압(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS3 = "holdPress3";
   static int HOLD_PRESS3_UPPER_LIMIT = -1;
   java.lang.String holdPress3;
   /**
    * 보압(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPress3() {
      return holdPress3;
   }
   /**
    * 보압(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPress3(java.lang.String holdPress3) throws wt.util.WTPropertyVetoException {
      holdPress3Validate(holdPress3);
      this.holdPress3 = holdPress3;
   }
   void holdPress3Validate(java.lang.String holdPress3) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS3_UPPER_LIMIT < 1) {
         try { HOLD_PRESS3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPress3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS3_UPPER_LIMIT = 200; }
      }
      if (holdPress3 != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPress3.toString(), HOLD_PRESS3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPress3"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPress3", this.holdPress3, holdPress3));
   }

   /**
    * 보압속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS_SPEED1 = "holdPressSpeed1";
   static int HOLD_PRESS_SPEED1_UPPER_LIMIT = -1;
   java.lang.String holdPressSpeed1;
   /**
    * 보압속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPressSpeed1() {
      return holdPressSpeed1;
   }
   /**
    * 보압속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPressSpeed1(java.lang.String holdPressSpeed1) throws wt.util.WTPropertyVetoException {
      holdPressSpeed1Validate(holdPressSpeed1);
      this.holdPressSpeed1 = holdPressSpeed1;
   }
   void holdPressSpeed1Validate(java.lang.String holdPressSpeed1) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS_SPEED1_UPPER_LIMIT < 1) {
         try { HOLD_PRESS_SPEED1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPressSpeed1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS_SPEED1_UPPER_LIMIT = 200; }
      }
      if (holdPressSpeed1 != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPressSpeed1.toString(), HOLD_PRESS_SPEED1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPressSpeed1"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS_SPEED1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPressSpeed1", this.holdPressSpeed1, holdPressSpeed1));
   }

   /**
    * 보압속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS_SPEED2 = "holdPressSpeed2";
   static int HOLD_PRESS_SPEED2_UPPER_LIMIT = -1;
   java.lang.String holdPressSpeed2;
   /**
    * 보압속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPressSpeed2() {
      return holdPressSpeed2;
   }
   /**
    * 보압속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPressSpeed2(java.lang.String holdPressSpeed2) throws wt.util.WTPropertyVetoException {
      holdPressSpeed2Validate(holdPressSpeed2);
      this.holdPressSpeed2 = holdPressSpeed2;
   }
   void holdPressSpeed2Validate(java.lang.String holdPressSpeed2) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS_SPEED2_UPPER_LIMIT < 1) {
         try { HOLD_PRESS_SPEED2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPressSpeed2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS_SPEED2_UPPER_LIMIT = 200; }
      }
      if (holdPressSpeed2 != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPressSpeed2.toString(), HOLD_PRESS_SPEED2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPressSpeed2"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS_SPEED2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPressSpeed2", this.holdPressSpeed2, holdPressSpeed2));
   }

   /**
    * 보압속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS_SPEED3 = "holdPressSpeed3";
   static int HOLD_PRESS_SPEED3_UPPER_LIMIT = -1;
   java.lang.String holdPressSpeed3;
   /**
    * 보압속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPressSpeed3() {
      return holdPressSpeed3;
   }
   /**
    * 보압속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPressSpeed3(java.lang.String holdPressSpeed3) throws wt.util.WTPropertyVetoException {
      holdPressSpeed3Validate(holdPressSpeed3);
      this.holdPressSpeed3 = holdPressSpeed3;
   }
   void holdPressSpeed3Validate(java.lang.String holdPressSpeed3) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS_SPEED3_UPPER_LIMIT < 1) {
         try { HOLD_PRESS_SPEED3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPressSpeed3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS_SPEED3_UPPER_LIMIT = 200; }
      }
      if (holdPressSpeed3 != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPressSpeed3.toString(), HOLD_PRESS_SPEED3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPressSpeed3"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS_SPEED3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPressSpeed3", this.holdPressSpeed3, holdPressSpeed3));
   }

   /**
    * 헝개속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_OPEN_SPEED1 = "moldOpenSpeed1";
   static int MOLD_OPEN_SPEED1_UPPER_LIMIT = -1;
   java.lang.String moldOpenSpeed1;
   /**
    * 헝개속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldOpenSpeed1() {
      return moldOpenSpeed1;
   }
   /**
    * 헝개속도(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldOpenSpeed1(java.lang.String moldOpenSpeed1) throws wt.util.WTPropertyVetoException {
      moldOpenSpeed1Validate(moldOpenSpeed1);
      this.moldOpenSpeed1 = moldOpenSpeed1;
   }
   void moldOpenSpeed1Validate(java.lang.String moldOpenSpeed1) throws wt.util.WTPropertyVetoException {
      if (MOLD_OPEN_SPEED1_UPPER_LIMIT < 1) {
         try { MOLD_OPEN_SPEED1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldOpenSpeed1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_OPEN_SPEED1_UPPER_LIMIT = 200; }
      }
      if (moldOpenSpeed1 != null && !wt.fc.PersistenceHelper.checkStoredLength(moldOpenSpeed1.toString(), MOLD_OPEN_SPEED1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldOpenSpeed1"), java.lang.String.valueOf(java.lang.Math.min(MOLD_OPEN_SPEED1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldOpenSpeed1", this.moldOpenSpeed1, moldOpenSpeed1));
   }

   /**
    * 헝개속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_OPEN_SPEED2 = "moldOpenSpeed2";
   static int MOLD_OPEN_SPEED2_UPPER_LIMIT = -1;
   java.lang.String moldOpenSpeed2;
   /**
    * 헝개속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldOpenSpeed2() {
      return moldOpenSpeed2;
   }
   /**
    * 헝개속도(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldOpenSpeed2(java.lang.String moldOpenSpeed2) throws wt.util.WTPropertyVetoException {
      moldOpenSpeed2Validate(moldOpenSpeed2);
      this.moldOpenSpeed2 = moldOpenSpeed2;
   }
   void moldOpenSpeed2Validate(java.lang.String moldOpenSpeed2) throws wt.util.WTPropertyVetoException {
      if (MOLD_OPEN_SPEED2_UPPER_LIMIT < 1) {
         try { MOLD_OPEN_SPEED2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldOpenSpeed2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_OPEN_SPEED2_UPPER_LIMIT = 200; }
      }
      if (moldOpenSpeed2 != null && !wt.fc.PersistenceHelper.checkStoredLength(moldOpenSpeed2.toString(), MOLD_OPEN_SPEED2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldOpenSpeed2"), java.lang.String.valueOf(java.lang.Math.min(MOLD_OPEN_SPEED2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldOpenSpeed2", this.moldOpenSpeed2, moldOpenSpeed2));
   }

   /**
    * 헝개속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_OPEN_SPEED3 = "moldOpenSpeed3";
   static int MOLD_OPEN_SPEED3_UPPER_LIMIT = -1;
   java.lang.String moldOpenSpeed3;
   /**
    * 헝개속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldOpenSpeed3() {
      return moldOpenSpeed3;
   }
   /**
    * 헝개속도(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldOpenSpeed3(java.lang.String moldOpenSpeed3) throws wt.util.WTPropertyVetoException {
      moldOpenSpeed3Validate(moldOpenSpeed3);
      this.moldOpenSpeed3 = moldOpenSpeed3;
   }
   void moldOpenSpeed3Validate(java.lang.String moldOpenSpeed3) throws wt.util.WTPropertyVetoException {
      if (MOLD_OPEN_SPEED3_UPPER_LIMIT < 1) {
         try { MOLD_OPEN_SPEED3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldOpenSpeed3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_OPEN_SPEED3_UPPER_LIMIT = 200; }
      }
      if (moldOpenSpeed3 != null && !wt.fc.PersistenceHelper.checkStoredLength(moldOpenSpeed3.toString(), MOLD_OPEN_SPEED3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldOpenSpeed3"), java.lang.String.valueOf(java.lang.Math.min(MOLD_OPEN_SPEED3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldOpenSpeed3", this.moldOpenSpeed3, moldOpenSpeed3));
   }

   /**
    * 헝개거리(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_OPEN_DIST1 = "moldOpenDist1";
   static int MOLD_OPEN_DIST1_UPPER_LIMIT = -1;
   java.lang.String moldOpenDist1;
   /**
    * 헝개거리(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldOpenDist1() {
      return moldOpenDist1;
   }
   /**
    * 헝개거리(1단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldOpenDist1(java.lang.String moldOpenDist1) throws wt.util.WTPropertyVetoException {
      moldOpenDist1Validate(moldOpenDist1);
      this.moldOpenDist1 = moldOpenDist1;
   }
   void moldOpenDist1Validate(java.lang.String moldOpenDist1) throws wt.util.WTPropertyVetoException {
      if (MOLD_OPEN_DIST1_UPPER_LIMIT < 1) {
         try { MOLD_OPEN_DIST1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldOpenDist1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_OPEN_DIST1_UPPER_LIMIT = 200; }
      }
      if (moldOpenDist1 != null && !wt.fc.PersistenceHelper.checkStoredLength(moldOpenDist1.toString(), MOLD_OPEN_DIST1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldOpenDist1"), java.lang.String.valueOf(java.lang.Math.min(MOLD_OPEN_DIST1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldOpenDist1", this.moldOpenDist1, moldOpenDist1));
   }

   /**
    * 헝개거리(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_OPEN_DIST2 = "moldOpenDist2";
   static int MOLD_OPEN_DIST2_UPPER_LIMIT = -1;
   java.lang.String moldOpenDist2;
   /**
    * 헝개거리(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldOpenDist2() {
      return moldOpenDist2;
   }
   /**
    * 헝개거리(2단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldOpenDist2(java.lang.String moldOpenDist2) throws wt.util.WTPropertyVetoException {
      moldOpenDist2Validate(moldOpenDist2);
      this.moldOpenDist2 = moldOpenDist2;
   }
   void moldOpenDist2Validate(java.lang.String moldOpenDist2) throws wt.util.WTPropertyVetoException {
      if (MOLD_OPEN_DIST2_UPPER_LIMIT < 1) {
         try { MOLD_OPEN_DIST2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldOpenDist2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_OPEN_DIST2_UPPER_LIMIT = 200; }
      }
      if (moldOpenDist2 != null && !wt.fc.PersistenceHelper.checkStoredLength(moldOpenDist2.toString(), MOLD_OPEN_DIST2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldOpenDist2"), java.lang.String.valueOf(java.lang.Math.min(MOLD_OPEN_DIST2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldOpenDist2", this.moldOpenDist2, moldOpenDist2));
   }

   /**
    * 헝개거리(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_OPEN_DIST3 = "moldOpenDist3";
   static int MOLD_OPEN_DIST3_UPPER_LIMIT = -1;
   java.lang.String moldOpenDist3;
   /**
    * 헝개거리(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldOpenDist3() {
      return moldOpenDist3;
   }
   /**
    * 헝개거리(3단)
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldOpenDist3(java.lang.String moldOpenDist3) throws wt.util.WTPropertyVetoException {
      moldOpenDist3Validate(moldOpenDist3);
      this.moldOpenDist3 = moldOpenDist3;
   }
   void moldOpenDist3Validate(java.lang.String moldOpenDist3) throws wt.util.WTPropertyVetoException {
      if (MOLD_OPEN_DIST3_UPPER_LIMIT < 1) {
         try { MOLD_OPEN_DIST3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldOpenDist3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_OPEN_DIST3_UPPER_LIMIT = 200; }
      }
      if (moldOpenDist3 != null && !wt.fc.PersistenceHelper.checkStoredLength(moldOpenDist3.toString(), MOLD_OPEN_DIST3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldOpenDist3"), java.lang.String.valueOf(java.lang.Math.min(MOLD_OPEN_DIST3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldOpenDist3", this.moldOpenDist3, moldOpenDist3));
   }

   /**
    * stroke
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String STROKE = "stroke";
   static int STROKE_UPPER_LIMIT = -1;
   java.lang.String stroke;
   /**
    * stroke
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getStroke() {
      return stroke;
   }
   /**
    * stroke
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
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
    * 속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SPEED = "speed";
   static int SPEED_UPPER_LIMIT = -1;
   java.lang.String speed;
   /**
    * 속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getSpeed() {
      return speed;
   }
   /**
    * 속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setSpeed(java.lang.String speed) throws wt.util.WTPropertyVetoException {
      speedValidate(speed);
      this.speed = speed;
   }
   void speedValidate(java.lang.String speed) throws wt.util.WTPropertyVetoException {
      if (SPEED_UPPER_LIMIT < 1) {
         try { SPEED_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("speed").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPEED_UPPER_LIMIT = 200; }
      }
      if (speed != null && !wt.fc.PersistenceHelper.checkStoredLength(speed.toString(), SPEED_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "speed"), java.lang.String.valueOf(java.lang.Math.min(SPEED_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "speed", this.speed, speed));
   }

   /**
    * 압력
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String PRESSURES = "pressures";
   static int PRESSURES_UPPER_LIMIT = -1;
   java.lang.String pressures;
   /**
    * 압력
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getPressures() {
      return pressures;
   }
   /**
    * 압력
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setPressures(java.lang.String pressures) throws wt.util.WTPropertyVetoException {
      pressuresValidate(pressures);
      this.pressures = pressures;
   }
   void pressuresValidate(java.lang.String pressures) throws wt.util.WTPropertyVetoException {
      if (PRESSURES_UPPER_LIMIT < 1) {
         try { PRESSURES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pressures").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRESSURES_UPPER_LIMIT = 200; }
      }
      if (pressures != null && !wt.fc.PersistenceHelper.checkStoredLength(pressures.toString(), PRESSURES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pressures"), java.lang.String.valueOf(java.lang.Math.min(PRESSURES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pressures", this.pressures, pressures));
   }

   /**
    * 횟수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String PRESS_COUNT = "pressCount";
   static int PRESS_COUNT_UPPER_LIMIT = -1;
   java.lang.String pressCount;
   /**
    * 횟수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getPressCount() {
      return pressCount;
   }
   /**
    * 횟수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setPressCount(java.lang.String pressCount) throws wt.util.WTPropertyVetoException {
      pressCountValidate(pressCount);
      this.pressCount = pressCount;
   }
   void pressCountValidate(java.lang.String pressCount) throws wt.util.WTPropertyVetoException {
      if (PRESS_COUNT_UPPER_LIMIT < 1) {
         try { PRESS_COUNT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pressCount").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRESS_COUNT_UPPER_LIMIT = 200; }
      }
      if (pressCount != null && !wt.fc.PersistenceHelper.checkStoredLength(pressCount.toString(), PRESS_COUNT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pressCount"), java.lang.String.valueOf(java.lang.Math.min(PRESS_COUNT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pressCount", this.pressCount, pressCount));
   }

   /**
    * 전진지연시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String DELAY_TIME = "delayTime";
   static int DELAY_TIME_UPPER_LIMIT = -1;
   java.lang.String delayTime;
   /**
    * 전진지연시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getDelayTime() {
      return delayTime;
   }
   /**
    * 전진지연시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setDelayTime(java.lang.String delayTime) throws wt.util.WTPropertyVetoException {
      delayTimeValidate(delayTime);
      this.delayTime = delayTime;
   }
   void delayTimeValidate(java.lang.String delayTime) throws wt.util.WTPropertyVetoException {
      if (DELAY_TIME_UPPER_LIMIT < 1) {
         try { DELAY_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("delayTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DELAY_TIME_UPPER_LIMIT = 200; }
      }
      if (delayTime != null && !wt.fc.PersistenceHelper.checkStoredLength(delayTime.toString(), DELAY_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "delayTime"), java.lang.String.valueOf(java.lang.Math.min(DELAY_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "delayTime", this.delayTime, delayTime));
   }

   /**
    * Cycle time
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CYCLE_TIME = "cycleTime";
   static int CYCLE_TIME_UPPER_LIMIT = -1;
   java.lang.String cycleTime;
   /**
    * Cycle time
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCycleTime() {
      return cycleTime;
   }
   /**
    * Cycle time
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCycleTime(java.lang.String cycleTime) throws wt.util.WTPropertyVetoException {
      cycleTimeValidate(cycleTime);
      this.cycleTime = cycleTime;
   }
   void cycleTimeValidate(java.lang.String cycleTime) throws wt.util.WTPropertyVetoException {
      if (CYCLE_TIME_UPPER_LIMIT < 1) {
         try { CYCLE_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cycleTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CYCLE_TIME_UPPER_LIMIT = 200; }
      }
      if (cycleTime != null && !wt.fc.PersistenceHelper.checkStoredLength(cycleTime.toString(), CYCLE_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cycleTime"), java.lang.String.valueOf(java.lang.Math.min(CYCLE_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cycleTime", this.cycleTime, cycleTime));
   }

   /**
    * 사출시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String PRESS_TIME = "pressTime";
   static int PRESS_TIME_UPPER_LIMIT = -1;
   java.lang.String pressTime;
   /**
    * 사출시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getPressTime() {
      return pressTime;
   }
   /**
    * 사출시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setPressTime(java.lang.String pressTime) throws wt.util.WTPropertyVetoException {
      pressTimeValidate(pressTime);
      this.pressTime = pressTime;
   }
   void pressTimeValidate(java.lang.String pressTime) throws wt.util.WTPropertyVetoException {
      if (PRESS_TIME_UPPER_LIMIT < 1) {
         try { PRESS_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pressTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRESS_TIME_UPPER_LIMIT = 200; }
      }
      if (pressTime != null && !wt.fc.PersistenceHelper.checkStoredLength(pressTime.toString(), PRESS_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pressTime"), java.lang.String.valueOf(java.lang.Math.min(PRESS_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pressTime", this.pressTime, pressTime));
   }

   /**
    * 냉각시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String COOLING_TIME = "coolingTime";
   static int COOLING_TIME_UPPER_LIMIT = -1;
   java.lang.String coolingTime;
   /**
    * 냉각시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCoolingTime() {
      return coolingTime;
   }
   /**
    * 냉각시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCoolingTime(java.lang.String coolingTime) throws wt.util.WTPropertyVetoException {
      coolingTimeValidate(coolingTime);
      this.coolingTime = coolingTime;
   }
   void coolingTimeValidate(java.lang.String coolingTime) throws wt.util.WTPropertyVetoException {
      if (COOLING_TIME_UPPER_LIMIT < 1) {
         try { COOLING_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("coolingTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COOLING_TIME_UPPER_LIMIT = 200; }
      }
      if (coolingTime != null && !wt.fc.PersistenceHelper.checkStoredLength(coolingTime.toString(), COOLING_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "coolingTime"), java.lang.String.valueOf(java.lang.Math.min(COOLING_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "coolingTime", this.coolingTime, coolingTime));
   }

   /**
    * 보압시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS_TIME = "holdPressTime";
   static int HOLD_PRESS_TIME_UPPER_LIMIT = -1;
   java.lang.String holdPressTime;
   /**
    * 보압시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPressTime() {
      return holdPressTime;
   }
   /**
    * 보압시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPressTime(java.lang.String holdPressTime) throws wt.util.WTPropertyVetoException {
      holdPressTimeValidate(holdPressTime);
      this.holdPressTime = holdPressTime;
   }
   void holdPressTimeValidate(java.lang.String holdPressTime) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS_TIME_UPPER_LIMIT < 1) {
         try { HOLD_PRESS_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPressTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS_TIME_UPPER_LIMIT = 200; }
      }
      if (holdPressTime != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPressTime.toString(), HOLD_PRESS_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPressTime"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPressTime", this.holdPressTime, holdPressTime));
   }

   /**
    * 형폐시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_CLOSE_TIME = "moldCloseTime";
   static int MOLD_CLOSE_TIME_UPPER_LIMIT = -1;
   java.lang.String moldCloseTime;
   /**
    * 형폐시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMoldCloseTime() {
      return moldCloseTime;
   }
   /**
    * 형폐시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldCloseTime(java.lang.String moldCloseTime) throws wt.util.WTPropertyVetoException {
      moldCloseTimeValidate(moldCloseTime);
      this.moldCloseTime = moldCloseTime;
   }
   void moldCloseTimeValidate(java.lang.String moldCloseTime) throws wt.util.WTPropertyVetoException {
      if (MOLD_CLOSE_TIME_UPPER_LIMIT < 1) {
         try { MOLD_CLOSE_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldCloseTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_CLOSE_TIME_UPPER_LIMIT = 200; }
      }
      if (moldCloseTime != null && !wt.fc.PersistenceHelper.checkStoredLength(moldCloseTime.toString(), MOLD_CLOSE_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldCloseTime"), java.lang.String.valueOf(java.lang.Math.min(MOLD_CLOSE_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldCloseTime", this.moldCloseTime, moldCloseTime));
   }

   /**
    * Shot 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SHOT_WEIGHT = "shotWeight";
   static int SHOT_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String shotWeight;
   /**
    * Shot 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getShotWeight() {
      return shotWeight;
   }
   /**
    * Shot 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setShotWeight(java.lang.String shotWeight) throws wt.util.WTPropertyVetoException {
      shotWeightValidate(shotWeight);
      this.shotWeight = shotWeight;
   }
   void shotWeightValidate(java.lang.String shotWeight) throws wt.util.WTPropertyVetoException {
      if (SHOT_WEIGHT_UPPER_LIMIT < 1) {
         try { SHOT_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shotWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHOT_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (shotWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(shotWeight.toString(), SHOT_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shotWeight"), java.lang.String.valueOf(java.lang.Math.min(SHOT_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shotWeight", this.shotWeight, shotWeight));
   }

   /**
    * Spure
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SPRUE = "sprue";
   static int SPRUE_UPPER_LIMIT = -1;
   java.lang.String sprue;
   /**
    * Spure
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getSprue() {
      return sprue;
   }
   /**
    * Spure
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setSprue(java.lang.String sprue) throws wt.util.WTPropertyVetoException {
      sprueValidate(sprue);
      this.sprue = sprue;
   }
   void sprueValidate(java.lang.String sprue) throws wt.util.WTPropertyVetoException {
      if (SPRUE_UPPER_LIMIT < 1) {
         try { SPRUE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sprue").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPRUE_UPPER_LIMIT = 200; }
      }
      if (sprue != null && !wt.fc.PersistenceHelper.checkStoredLength(sprue.toString(), SPRUE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sprue"), java.lang.String.valueOf(java.lang.Math.min(SPRUE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sprue", this.sprue, sprue));
   }

   /**
    * C/V 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CV_WEIGHT = "cvWeight";
   static int CV_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String cvWeight;
   /**
    * C/V 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCvWeight() {
      return cvWeight;
   }
   /**
    * C/V 중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCvWeight(java.lang.String cvWeight) throws wt.util.WTPropertyVetoException {
      cvWeightValidate(cvWeight);
      this.cvWeight = cvWeight;
   }
   void cvWeightValidate(java.lang.String cvWeight) throws wt.util.WTPropertyVetoException {
      if (CV_WEIGHT_UPPER_LIMIT < 1) {
         try { CV_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cvWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CV_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (cvWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(cvWeight.toString(), CV_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cvWeight"), java.lang.String.valueOf(java.lang.Math.min(CV_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cvWeight", this.cvWeight, cvWeight));
   }

   /**
    * 고정측 입력값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String FIXED_SIDE_TEMP_INPUT = "fixedSideTempInput";
   static int FIXED_SIDE_TEMP_INPUT_UPPER_LIMIT = -1;
   java.lang.String fixedSideTempInput;
   /**
    * 고정측 입력값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getFixedSideTempInput() {
      return fixedSideTempInput;
   }
   /**
    * 고정측 입력값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setFixedSideTempInput(java.lang.String fixedSideTempInput) throws wt.util.WTPropertyVetoException {
      fixedSideTempInputValidate(fixedSideTempInput);
      this.fixedSideTempInput = fixedSideTempInput;
   }
   void fixedSideTempInputValidate(java.lang.String fixedSideTempInput) throws wt.util.WTPropertyVetoException {
      if (FIXED_SIDE_TEMP_INPUT_UPPER_LIMIT < 1) {
         try { FIXED_SIDE_TEMP_INPUT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("fixedSideTempInput").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FIXED_SIDE_TEMP_INPUT_UPPER_LIMIT = 200; }
      }
      if (fixedSideTempInput != null && !wt.fc.PersistenceHelper.checkStoredLength(fixedSideTempInput.toString(), FIXED_SIDE_TEMP_INPUT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "fixedSideTempInput"), java.lang.String.valueOf(java.lang.Math.min(FIXED_SIDE_TEMP_INPUT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "fixedSideTempInput", this.fixedSideTempInput, fixedSideTempInput));
   }

   /**
    * 고정측 실제값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String FIXED_SIDE_TEMP = "fixedSideTemp";
   static int FIXED_SIDE_TEMP_UPPER_LIMIT = -1;
   java.lang.String fixedSideTemp;
   /**
    * 고정측 실제값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getFixedSideTemp() {
      return fixedSideTemp;
   }
   /**
    * 고정측 실제값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setFixedSideTemp(java.lang.String fixedSideTemp) throws wt.util.WTPropertyVetoException {
      fixedSideTempValidate(fixedSideTemp);
      this.fixedSideTemp = fixedSideTemp;
   }
   void fixedSideTempValidate(java.lang.String fixedSideTemp) throws wt.util.WTPropertyVetoException {
      if (FIXED_SIDE_TEMP_UPPER_LIMIT < 1) {
         try { FIXED_SIDE_TEMP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("fixedSideTemp").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FIXED_SIDE_TEMP_UPPER_LIMIT = 200; }
      }
      if (fixedSideTemp != null && !wt.fc.PersistenceHelper.checkStoredLength(fixedSideTemp.toString(), FIXED_SIDE_TEMP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "fixedSideTemp"), java.lang.String.valueOf(java.lang.Math.min(FIXED_SIDE_TEMP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "fixedSideTemp", this.fixedSideTemp, fixedSideTemp));
   }

   /**
    * 이동측 입력값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOVING_SIDE_TEMP_INPUT = "movingSideTempInput";
   static int MOVING_SIDE_TEMP_INPUT_UPPER_LIMIT = -1;
   java.lang.String movingSideTempInput;
   /**
    * 이동측 입력값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMovingSideTempInput() {
      return movingSideTempInput;
   }
   /**
    * 이동측 입력값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMovingSideTempInput(java.lang.String movingSideTempInput) throws wt.util.WTPropertyVetoException {
      movingSideTempInputValidate(movingSideTempInput);
      this.movingSideTempInput = movingSideTempInput;
   }
   void movingSideTempInputValidate(java.lang.String movingSideTempInput) throws wt.util.WTPropertyVetoException {
      if (MOVING_SIDE_TEMP_INPUT_UPPER_LIMIT < 1) {
         try { MOVING_SIDE_TEMP_INPUT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("movingSideTempInput").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOVING_SIDE_TEMP_INPUT_UPPER_LIMIT = 200; }
      }
      if (movingSideTempInput != null && !wt.fc.PersistenceHelper.checkStoredLength(movingSideTempInput.toString(), MOVING_SIDE_TEMP_INPUT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "movingSideTempInput"), java.lang.String.valueOf(java.lang.Math.min(MOVING_SIDE_TEMP_INPUT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "movingSideTempInput", this.movingSideTempInput, movingSideTempInput));
   }

   /**
    * 이동측 실제값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOVING_SIDE_TEMP = "movingSideTemp";
   static int MOVING_SIDE_TEMP_UPPER_LIMIT = -1;
   java.lang.String movingSideTemp;
   /**
    * 이동측 실제값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMovingSideTemp() {
      return movingSideTemp;
   }
   /**
    * 이동측 실제값
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMovingSideTemp(java.lang.String movingSideTemp) throws wt.util.WTPropertyVetoException {
      movingSideTempValidate(movingSideTemp);
      this.movingSideTemp = movingSideTemp;
   }
   void movingSideTempValidate(java.lang.String movingSideTemp) throws wt.util.WTPropertyVetoException {
      if (MOVING_SIDE_TEMP_UPPER_LIMIT < 1) {
         try { MOVING_SIDE_TEMP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("movingSideTemp").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOVING_SIDE_TEMP_UPPER_LIMIT = 200; }
      }
      if (movingSideTemp != null && !wt.fc.PersistenceHelper.checkStoredLength(movingSideTemp.toString(), MOVING_SIDE_TEMP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "movingSideTemp"), java.lang.String.valueOf(java.lang.Math.min(MOVING_SIDE_TEMP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "movingSideTemp", this.movingSideTemp, movingSideTemp));
   }

   /**
    * 배압
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String BACK_PRESS = "backPress";
   static int BACK_PRESS_UPPER_LIMIT = -1;
   java.lang.String backPress;
   /**
    * 배압
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getBackPress() {
      return backPress;
   }
   /**
    * 배압
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setBackPress(java.lang.String backPress) throws wt.util.WTPropertyVetoException {
      backPressValidate(backPress);
      this.backPress = backPress;
   }
   void backPressValidate(java.lang.String backPress) throws wt.util.WTPropertyVetoException {
      if (BACK_PRESS_UPPER_LIMIT < 1) {
         try { BACK_PRESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("backPress").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BACK_PRESS_UPPER_LIMIT = 200; }
      }
      if (backPress != null && !wt.fc.PersistenceHelper.checkStoredLength(backPress.toString(), BACK_PRESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "backPress"), java.lang.String.valueOf(java.lang.Math.min(BACK_PRESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "backPress", this.backPress, backPress));
   }

   /**
    * 계량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MENSURATION = "mensuration";
   static int MENSURATION_UPPER_LIMIT = -1;
   java.lang.String mensuration;
   /**
    * 계량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMensuration() {
      return mensuration;
   }
   /**
    * 계량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMensuration(java.lang.String mensuration) throws wt.util.WTPropertyVetoException {
      mensurationValidate(mensuration);
      this.mensuration = mensuration;
   }
   void mensurationValidate(java.lang.String mensuration) throws wt.util.WTPropertyVetoException {
      if (MENSURATION_UPPER_LIMIT < 1) {
         try { MENSURATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mensuration").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MENSURATION_UPPER_LIMIT = 200; }
      }
      if (mensuration != null && !wt.fc.PersistenceHelper.checkStoredLength(mensuration.toString(), MENSURATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mensuration"), java.lang.String.valueOf(java.lang.Math.min(MENSURATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mensuration", this.mensuration, mensuration));
   }

   /**
    * 저압형체
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String LOW_PRESS_SHAPE = "lowPressShape";
   static int LOW_PRESS_SHAPE_UPPER_LIMIT = -1;
   java.lang.String lowPressShape;
   /**
    * 저압형체
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getLowPressShape() {
      return lowPressShape;
   }
   /**
    * 저압형체
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setLowPressShape(java.lang.String lowPressShape) throws wt.util.WTPropertyVetoException {
      lowPressShapeValidate(lowPressShape);
      this.lowPressShape = lowPressShape;
   }
   void lowPressShapeValidate(java.lang.String lowPressShape) throws wt.util.WTPropertyVetoException {
      if (LOW_PRESS_SHAPE_UPPER_LIMIT < 1) {
         try { LOW_PRESS_SHAPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("lowPressShape").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LOW_PRESS_SHAPE_UPPER_LIMIT = 200; }
      }
      if (lowPressShape != null && !wt.fc.PersistenceHelper.checkStoredLength(lowPressShape.toString(), LOW_PRESS_SHAPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lowPressShape"), java.lang.String.valueOf(java.lang.Math.min(LOW_PRESS_SHAPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "lowPressShape", this.lowPressShape, lowPressShape));
   }

   /**
    * 보압전환점
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HOLD_PRESS_TURN_POINT = "holdPressTurnPoint";
   static int HOLD_PRESS_TURN_POINT_UPPER_LIMIT = -1;
   java.lang.String holdPressTurnPoint;
   /**
    * 보압전환점
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHoldPressTurnPoint() {
      return holdPressTurnPoint;
   }
   /**
    * 보압전환점
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHoldPressTurnPoint(java.lang.String holdPressTurnPoint) throws wt.util.WTPropertyVetoException {
      holdPressTurnPointValidate(holdPressTurnPoint);
      this.holdPressTurnPoint = holdPressTurnPoint;
   }
   void holdPressTurnPointValidate(java.lang.String holdPressTurnPoint) throws wt.util.WTPropertyVetoException {
      if (HOLD_PRESS_TURN_POINT_UPPER_LIMIT < 1) {
         try { HOLD_PRESS_TURN_POINT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("holdPressTurnPoint").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOLD_PRESS_TURN_POINT_UPPER_LIMIT = 200; }
      }
      if (holdPressTurnPoint != null && !wt.fc.PersistenceHelper.checkStoredLength(holdPressTurnPoint.toString(), HOLD_PRESS_TURN_POINT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "holdPressTurnPoint"), java.lang.String.valueOf(java.lang.Math.min(HOLD_PRESS_TURN_POINT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "holdPressTurnPoint", this.holdPressTurnPoint, holdPressTurnPoint));
   }

   /**
    * 계량시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MENSURATION_TIME = "mensurationTime";
   static int MENSURATION_TIME_UPPER_LIMIT = -1;
   java.lang.String mensurationTime;
   /**
    * 계량시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMensurationTime() {
      return mensurationTime;
   }
   /**
    * 계량시간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMensurationTime(java.lang.String mensurationTime) throws wt.util.WTPropertyVetoException {
      mensurationTimeValidate(mensurationTime);
      this.mensurationTime = mensurationTime;
   }
   void mensurationTimeValidate(java.lang.String mensurationTime) throws wt.util.WTPropertyVetoException {
      if (MENSURATION_TIME_UPPER_LIMIT < 1) {
         try { MENSURATION_TIME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mensurationTime").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MENSURATION_TIME_UPPER_LIMIT = 200; }
      }
      if (mensurationTime != null && !wt.fc.PersistenceHelper.checkStoredLength(mensurationTime.toString(), MENSURATION_TIME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mensurationTime"), java.lang.String.valueOf(java.lang.Math.min(MENSURATION_TIME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mensurationTime", this.mensurationTime, mensurationTime));
   }

   /**
    * 고압형체구간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String HIGH_PRESS_SHAPE_SECTION = "highPressShapeSection";
   static int HIGH_PRESS_SHAPE_SECTION_UPPER_LIMIT = -1;
   java.lang.String highPressShapeSection;
   /**
    * 고압형체구간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getHighPressShapeSection() {
      return highPressShapeSection;
   }
   /**
    * 고압형체구간
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setHighPressShapeSection(java.lang.String highPressShapeSection) throws wt.util.WTPropertyVetoException {
      highPressShapeSectionValidate(highPressShapeSection);
      this.highPressShapeSection = highPressShapeSection;
   }
   void highPressShapeSectionValidate(java.lang.String highPressShapeSection) throws wt.util.WTPropertyVetoException {
      if (HIGH_PRESS_SHAPE_SECTION_UPPER_LIMIT < 1) {
         try { HIGH_PRESS_SHAPE_SECTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("highPressShapeSection").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HIGH_PRESS_SHAPE_SECTION_UPPER_LIMIT = 200; }
      }
      if (highPressShapeSection != null && !wt.fc.PersistenceHelper.checkStoredLength(highPressShapeSection.toString(), HIGH_PRESS_SHAPE_SECTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "highPressShapeSection"), java.lang.String.valueOf(java.lang.Math.min(HIGH_PRESS_SHAPE_SECTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "highPressShapeSection", this.highPressShapeSection, highPressShapeSection));
   }

   /**
    * 쿠션량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CUSHION_AMOUNT = "cushionAmount";
   static int CUSHION_AMOUNT_UPPER_LIMIT = -1;
   java.lang.String cushionAmount;
   /**
    * 쿠션량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCushionAmount() {
      return cushionAmount;
   }
   /**
    * 쿠션량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCushionAmount(java.lang.String cushionAmount) throws wt.util.WTPropertyVetoException {
      cushionAmountValidate(cushionAmount);
      this.cushionAmount = cushionAmount;
   }
   void cushionAmountValidate(java.lang.String cushionAmount) throws wt.util.WTPropertyVetoException {
      if (CUSHION_AMOUNT_UPPER_LIMIT < 1) {
         try { CUSHION_AMOUNT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cushionAmount").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSHION_AMOUNT_UPPER_LIMIT = 200; }
      }
      if (cushionAmount != null && !wt.fc.PersistenceHelper.checkStoredLength(cushionAmount.toString(), CUSHION_AMOUNT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cushionAmount"), java.lang.String.valueOf(java.lang.Math.min(CUSHION_AMOUNT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cushionAmount", this.cushionAmount, cushionAmount));
   }

   /**
    * 흘림방지설정
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SPILL_RESISTENT_CFG = "spillResistentCfg";
   static int SPILL_RESISTENT_CFG_UPPER_LIMIT = -1;
   java.lang.String spillResistentCfg;
   /**
    * 흘림방지설정
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getSpillResistentCfg() {
      return spillResistentCfg;
   }
   /**
    * 흘림방지설정
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setSpillResistentCfg(java.lang.String spillResistentCfg) throws wt.util.WTPropertyVetoException {
      spillResistentCfgValidate(spillResistentCfg);
      this.spillResistentCfg = spillResistentCfg;
   }
   void spillResistentCfgValidate(java.lang.String spillResistentCfg) throws wt.util.WTPropertyVetoException {
      if (SPILL_RESISTENT_CFG_UPPER_LIMIT < 1) {
         try { SPILL_RESISTENT_CFG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("spillResistentCfg").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPILL_RESISTENT_CFG_UPPER_LIMIT = 200; }
      }
      if (spillResistentCfg != null && !wt.fc.PersistenceHelper.checkStoredLength(spillResistentCfg.toString(), SPILL_RESISTENT_CFG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "spillResistentCfg"), java.lang.String.valueOf(java.lang.Math.min(SPILL_RESISTENT_CFG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "spillResistentCfg", this.spillResistentCfg, spillResistentCfg));
   }

   /**
    * Shot 수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SHOT_COUNT = "shotCount";
   static int SHOT_COUNT_UPPER_LIMIT = -1;
   java.lang.String shotCount;
   /**
    * Shot 수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getShotCount() {
      return shotCount;
   }
   /**
    * Shot 수
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setShotCount(java.lang.String shotCount) throws wt.util.WTPropertyVetoException {
      shotCountValidate(shotCount);
      this.shotCount = shotCount;
   }
   void shotCountValidate(java.lang.String shotCount) throws wt.util.WTPropertyVetoException {
      if (SHOT_COUNT_UPPER_LIMIT < 1) {
         try { SHOT_COUNT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("shotCount").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHOT_COUNT_UPPER_LIMIT = 200; }
      }
      if (shotCount != null && !wt.fc.PersistenceHelper.checkStoredLength(shotCount.toString(), SHOT_COUNT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "shotCount"), java.lang.String.valueOf(java.lang.Math.min(SHOT_COUNT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "shotCount", this.shotCount, shotCount));
   }

   /**
    * 계량거리
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MENSURATION_DIST = "mensurationDist";
   static int MENSURATION_DIST_UPPER_LIMIT = -1;
   java.lang.String mensurationDist;
   /**
    * 계량거리
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMensurationDist() {
      return mensurationDist;
   }
   /**
    * 계량거리
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMensurationDist(java.lang.String mensurationDist) throws wt.util.WTPropertyVetoException {
      mensurationDistValidate(mensurationDist);
      this.mensurationDist = mensurationDist;
   }
   void mensurationDistValidate(java.lang.String mensurationDist) throws wt.util.WTPropertyVetoException {
      if (MENSURATION_DIST_UPPER_LIMIT < 1) {
         try { MENSURATION_DIST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mensurationDist").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MENSURATION_DIST_UPPER_LIMIT = 200; }
      }
      if (mensurationDist != null && !wt.fc.PersistenceHelper.checkStoredLength(mensurationDist.toString(), MENSURATION_DIST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mensurationDist"), java.lang.String.valueOf(java.lang.Math.min(MENSURATION_DIST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mensurationDist", this.mensurationDist, mensurationDist));
   }

   /**
    * 흘림방지속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String SPILL_RESISTANT_SPEED = "spillResistantSpeed";
   static int SPILL_RESISTANT_SPEED_UPPER_LIMIT = -1;
   java.lang.String spillResistantSpeed;
   /**
    * 흘림방지속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getSpillResistantSpeed() {
      return spillResistantSpeed;
   }
   /**
    * 흘림방지속도
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setSpillResistantSpeed(java.lang.String spillResistantSpeed) throws wt.util.WTPropertyVetoException {
      spillResistantSpeedValidate(spillResistantSpeed);
      this.spillResistantSpeed = spillResistantSpeed;
   }
   void spillResistantSpeedValidate(java.lang.String spillResistantSpeed) throws wt.util.WTPropertyVetoException {
      if (SPILL_RESISTANT_SPEED_UPPER_LIMIT < 1) {
         try { SPILL_RESISTANT_SPEED_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("spillResistantSpeed").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPILL_RESISTANT_SPEED_UPPER_LIMIT = 200; }
      }
      if (spillResistantSpeed != null && !wt.fc.PersistenceHelper.checkStoredLength(spillResistantSpeed.toString(), SPILL_RESISTANT_SPEED_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "spillResistantSpeed"), java.lang.String.valueOf(java.lang.Math.min(SPILL_RESISTANT_SPEED_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "spillResistantSpeed", this.spillResistantSpeed, spillResistantSpeed));
   }

   /**
    * 최대충진압
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MAX_PRESS = "maxPress";
   static int MAX_PRESS_UPPER_LIMIT = -1;
   java.lang.String maxPress;
   /**
    * 최대충진압
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getMaxPress() {
      return maxPress;
   }
   /**
    * 최대충진압
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMaxPress(java.lang.String maxPress) throws wt.util.WTPropertyVetoException {
      maxPressValidate(maxPress);
      this.maxPress = maxPress;
   }
   void maxPressValidate(java.lang.String maxPress) throws wt.util.WTPropertyVetoException {
      if (MAX_PRESS_UPPER_LIMIT < 1) {
         try { MAX_PRESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("maxPress").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAX_PRESS_UPPER_LIMIT = 200; }
      }
      if (maxPress != null && !wt.fc.PersistenceHelper.checkStoredLength(maxPress.toString(), MAX_PRESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "maxPress"), java.lang.String.valueOf(java.lang.Math.min(MAX_PRESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "maxPress", this.maxPress, maxPress));
   }

   /**
    * Insert중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INSERT_WEIGHT = "insertWeight";
   static int INSERT_WEIGHT_UPPER_LIMIT = -1;
   java.lang.String insertWeight;
   /**
    * Insert중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getInsertWeight() {
      return insertWeight;
   }
   /**
    * Insert중량
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInsertWeight(java.lang.String insertWeight) throws wt.util.WTPropertyVetoException {
      insertWeightValidate(insertWeight);
      this.insertWeight = insertWeight;
   }
   void insertWeightValidate(java.lang.String insertWeight) throws wt.util.WTPropertyVetoException {
      if (INSERT_WEIGHT_UPPER_LIMIT < 1) {
         try { INSERT_WEIGHT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("insertWeight").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INSERT_WEIGHT_UPPER_LIMIT = 200; }
      }
      if (insertWeight != null && !wt.fc.PersistenceHelper.checkStoredLength(insertWeight.toString(), INSERT_WEIGHT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "insertWeight"), java.lang.String.valueOf(java.lang.Math.min(INSERT_WEIGHT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "insertWeight", this.insertWeight, insertWeight));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String CAVITY_BIGO = "cavityBigo";
   static int CAVITY_BIGO_UPPER_LIMIT = -1;
   java.lang.String cavityBigo;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public java.lang.String getCavityBigo() {
      return cavityBigo;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setCavityBigo(java.lang.String cavityBigo) throws wt.util.WTPropertyVetoException {
      cavityBigoValidate(cavityBigo);
      this.cavityBigo = cavityBigo;
   }
   void cavityBigoValidate(java.lang.String cavityBigo) throws wt.util.WTPropertyVetoException {
      if (CAVITY_BIGO_UPPER_LIMIT < 1) {
         try { CAVITY_BIGO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cavityBigo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAVITY_BIGO_UPPER_LIMIT = 200; }
      }
      if (cavityBigo != null && !wt.fc.PersistenceHelper.checkStoredLength(cavityBigo.toString(), CAVITY_BIGO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cavityBigo"), java.lang.String.valueOf(java.lang.Math.min(CAVITY_BIGO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cavityBigo", this.cavityBigo, cavityBigo));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MATERIAL = "material";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MATERIAL_REFERENCE = "materialReference";
   wt.fc.ObjectReference materialReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.project.material.MoldMaterial getMaterial() {
      return (materialReference != null) ? (e3ps.project.material.MoldMaterial) materialReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getMaterialReference() {
      return materialReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMaterial(e3ps.project.material.MoldMaterial the_material) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMaterialReference(the_material == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.material.MoldMaterial) the_material));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
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
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String GATE_TYPE = "gateType";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String GATE_TYPE_REFERENCE = "gateTypeReference";
   wt.fc.ObjectReference gateTypeReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getGateType() {
      return (gateTypeReference != null) ? (e3ps.common.code.NumberCode) gateTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getGateTypeReference() {
      return gateTypeReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setGateType(e3ps.common.code.NumberCode the_gateType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setGateTypeReference(the_gateType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_gateType));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setGateTypeReference(wt.fc.ObjectReference the_gateTypeReference) throws wt.util.WTPropertyVetoException {
      gateTypeReferenceValidate(the_gateTypeReference);
      gateTypeReference = (wt.fc.ObjectReference) the_gateTypeReference;
   }
   void gateTypeReferenceValidate(wt.fc.ObjectReference the_gateTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_gateTypeReference == null || the_gateTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "gateTypeReference") },
               new java.beans.PropertyChangeEvent(this, "gateTypeReference", this.gateTypeReference, gateTypeReference));
      if (the_gateTypeReference != null && the_gateTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_gateTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "gateTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "gateTypeReference", this.gateTypeReference, gateTypeReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOUNT_THICKNESS = "mountThickness";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOUNT_THICKNESS_REFERENCE = "mountThicknessReference";
   wt.fc.ObjectReference mountThicknessReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getMountThickness() {
      return (mountThicknessReference != null) ? (e3ps.common.code.NumberCode) mountThicknessReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getMountThicknessReference() {
      return mountThicknessReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMountThickness(e3ps.common.code.NumberCode the_mountThickness) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMountThicknessReference(the_mountThickness == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_mountThickness));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMountThicknessReference(wt.fc.ObjectReference the_mountThicknessReference) throws wt.util.WTPropertyVetoException {
      mountThicknessReferenceValidate(the_mountThicknessReference);
      mountThicknessReference = (wt.fc.ObjectReference) the_mountThicknessReference;
   }
   void mountThicknessReferenceValidate(wt.fc.ObjectReference the_mountThicknessReference) throws wt.util.WTPropertyVetoException {
      if (the_mountThicknessReference == null || the_mountThicknessReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mountThicknessReference") },
               new java.beans.PropertyChangeEvent(this, "mountThicknessReference", this.mountThicknessReference, mountThicknessReference));
      if (the_mountThicknessReference != null && the_mountThicknessReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_mountThicknessReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mountThicknessReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "mountThicknessReference", this.mountThicknessReference, mountThicknessReference));
   }

   /**
    * 온도센서
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String TEMPERATURE_SENSOR = "temperatureSensor";
   /**
    * 온도센서
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String TEMPERATURE_SENSOR_REFERENCE = "temperatureSensorReference";
   wt.fc.ObjectReference temperatureSensorReference;
   /**
    * 온도센서
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getTemperatureSensor() {
      return (temperatureSensorReference != null) ? (e3ps.common.code.NumberCode) temperatureSensorReference.getObject() : null;
   }
   /**
    * 온도센서
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getTemperatureSensorReference() {
      return temperatureSensorReference;
   }
   /**
    * 온도센서
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setTemperatureSensor(e3ps.common.code.NumberCode the_temperatureSensor) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setTemperatureSensorReference(the_temperatureSensor == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_temperatureSensor));
   }
   /**
    * 온도센서
    *
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setTemperatureSensorReference(wt.fc.ObjectReference the_temperatureSensorReference) throws wt.util.WTPropertyVetoException {
      temperatureSensorReferenceValidate(the_temperatureSensorReference);
      temperatureSensorReference = (wt.fc.ObjectReference) the_temperatureSensorReference;
   }
   void temperatureSensorReferenceValidate(wt.fc.ObjectReference the_temperatureSensorReference) throws wt.util.WTPropertyVetoException {
      if (the_temperatureSensorReference == null || the_temperatureSensorReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "temperatureSensorReference") },
               new java.beans.PropertyChangeEvent(this, "temperatureSensorReference", this.temperatureSensorReference, temperatureSensorReference));
      if (the_temperatureSensorReference != null && the_temperatureSensorReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_temperatureSensorReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "temperatureSensorReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "temperatureSensorReference", this.temperatureSensorReference, temperatureSensorReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_PRESS_UNIT = "injectPressUnit";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String INJECT_PRESS_UNIT_REFERENCE = "injectPressUnitReference";
   wt.fc.ObjectReference injectPressUnitReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getInjectPressUnit() {
      return (injectPressUnitReference != null) ? (e3ps.common.code.NumberCode) injectPressUnitReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getInjectPressUnitReference() {
      return injectPressUnitReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectPressUnit(e3ps.common.code.NumberCode the_injectPressUnit) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setInjectPressUnitReference(the_injectPressUnit == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_injectPressUnit));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setInjectPressUnitReference(wt.fc.ObjectReference the_injectPressUnitReference) throws wt.util.WTPropertyVetoException {
      injectPressUnitReferenceValidate(the_injectPressUnitReference);
      injectPressUnitReference = (wt.fc.ObjectReference) the_injectPressUnitReference;
   }
   void injectPressUnitReferenceValidate(wt.fc.ObjectReference the_injectPressUnitReference) throws wt.util.WTPropertyVetoException {
      if (the_injectPressUnitReference == null || the_injectPressUnitReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectPressUnitReference") },
               new java.beans.PropertyChangeEvent(this, "injectPressUnitReference", this.injectPressUnitReference, injectPressUnitReference));
      if (the_injectPressUnitReference != null && the_injectPressUnitReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_injectPressUnitReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "injectPressUnitReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "injectPressUnitReference", this.injectPressUnitReference, injectPressUnitReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String PACKING_PRESS_UNIT = "packingPressUnit";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String PACKING_PRESS_UNIT_REFERENCE = "packingPressUnitReference";
   wt.fc.ObjectReference packingPressUnitReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getPackingPressUnit() {
      return (packingPressUnitReference != null) ? (e3ps.common.code.NumberCode) packingPressUnitReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getPackingPressUnitReference() {
      return packingPressUnitReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setPackingPressUnit(e3ps.common.code.NumberCode the_packingPressUnit) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPackingPressUnitReference(the_packingPressUnit == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_packingPressUnit));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setPackingPressUnitReference(wt.fc.ObjectReference the_packingPressUnitReference) throws wt.util.WTPropertyVetoException {
      packingPressUnitReferenceValidate(the_packingPressUnitReference);
      packingPressUnitReference = (wt.fc.ObjectReference) the_packingPressUnitReference;
   }
   void packingPressUnitReferenceValidate(wt.fc.ObjectReference the_packingPressUnitReference) throws wt.util.WTPropertyVetoException {
      if (the_packingPressUnitReference == null || the_packingPressUnitReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packingPressUnitReference") },
               new java.beans.PropertyChangeEvent(this, "packingPressUnitReference", this.packingPressUnitReference, packingPressUnitReference));
      if (the_packingPressUnitReference != null && the_packingPressUnitReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_packingPressUnitReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "packingPressUnitReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "packingPressUnitReference", this.packingPressUnitReference, packingPressUnitReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String LOW_PRESS_SHAPE_UNIT = "lowPressShapeUnit";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String LOW_PRESS_SHAPE_UNIT_REFERENCE = "lowPressShapeUnitReference";
   wt.fc.ObjectReference lowPressShapeUnitReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getLowPressShapeUnit() {
      return (lowPressShapeUnitReference != null) ? (e3ps.common.code.NumberCode) lowPressShapeUnitReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getLowPressShapeUnitReference() {
      return lowPressShapeUnitReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setLowPressShapeUnit(e3ps.common.code.NumberCode the_lowPressShapeUnit) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setLowPressShapeUnitReference(the_lowPressShapeUnit == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_lowPressShapeUnit));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setLowPressShapeUnitReference(wt.fc.ObjectReference the_lowPressShapeUnitReference) throws wt.util.WTPropertyVetoException {
      lowPressShapeUnitReferenceValidate(the_lowPressShapeUnitReference);
      lowPressShapeUnitReference = (wt.fc.ObjectReference) the_lowPressShapeUnitReference;
   }
   void lowPressShapeUnitReferenceValidate(wt.fc.ObjectReference the_lowPressShapeUnitReference) throws wt.util.WTPropertyVetoException {
      if (the_lowPressShapeUnitReference == null || the_lowPressShapeUnitReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lowPressShapeUnitReference") },
               new java.beans.PropertyChangeEvent(this, "lowPressShapeUnitReference", this.lowPressShapeUnitReference, lowPressShapeUnitReference));
      if (the_lowPressShapeUnitReference != null && the_lowPressShapeUnitReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_lowPressShapeUnitReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lowPressShapeUnitReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "lowPressShapeUnitReference", this.lowPressShapeUnitReference, lowPressShapeUnitReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String BACK_PRESS_UNIT = "backPressUnit";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String BACK_PRESS_UNIT_REFERENCE = "backPressUnitReference";
   wt.fc.ObjectReference backPressUnitReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getBackPressUnit() {
      return (backPressUnitReference != null) ? (e3ps.common.code.NumberCode) backPressUnitReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getBackPressUnitReference() {
      return backPressUnitReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setBackPressUnit(e3ps.common.code.NumberCode the_backPressUnit) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setBackPressUnitReference(the_backPressUnit == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_backPressUnit));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setBackPressUnitReference(wt.fc.ObjectReference the_backPressUnitReference) throws wt.util.WTPropertyVetoException {
      backPressUnitReferenceValidate(the_backPressUnitReference);
      backPressUnitReference = (wt.fc.ObjectReference) the_backPressUnitReference;
   }
   void backPressUnitReferenceValidate(wt.fc.ObjectReference the_backPressUnitReference) throws wt.util.WTPropertyVetoException {
      if (the_backPressUnitReference == null || the_backPressUnitReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "backPressUnitReference") },
               new java.beans.PropertyChangeEvent(this, "backPressUnitReference", this.backPressUnitReference, backPressUnitReference));
      if (the_backPressUnitReference != null && the_backPressUnitReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_backPressUnitReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "backPressUnitReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "backPressUnitReference", this.backPressUnitReference, backPressUnitReference));
   }

   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_STRUC = "moldStruc";
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public static final java.lang.String MOLD_STRUC_REFERENCE = "moldStrucReference";
   wt.fc.ObjectReference moldStrucReference;
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public e3ps.common.code.NumberCode getMoldStruc() {
      return (moldStrucReference != null) ? (e3ps.common.code.NumberCode) moldStrucReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public wt.fc.ObjectReference getMoldStrucReference() {
      return moldStrucReference;
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
    */
   public void setMoldStruc(e3ps.common.code.NumberCode the_moldStruc) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldStrucReference(the_moldStruc == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_moldStruc));
   }
   /**
    * @see ext.ket.project.trycondition.entity.KETTryMold
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

   public static final long EXTERNALIZATION_VERSION_UID = -6509891109043753488L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( backPress );
      output.writeObject( backPressUnitReference );
      output.writeObject( cavityBigo );
      output.writeObject( cavityCount );
      output.writeObject( color );
      output.writeObject( coolWaterTempHigh );
      output.writeObject( coolWaterTempLow );
      output.writeObject( coolingTime );
      output.writeObject( cushionAmount );
      output.writeObject( cvWeight );
      output.writeObject( cycleTime );
      output.writeObject( cylinderTempN1 );
      output.writeObject( cylinderTempN2 );
      output.writeObject( cylinderTempN3 );
      output.writeObject( cylinderTempN4 );
      output.writeObject( cylinderTempNH );
      output.writeObject( delayTime );
      output.writeObject( dryTemp );
      output.writeObject( dryTime );
      output.writeObject( fixedSideTemp );
      output.writeObject( fixedSideTempInput );
      output.writeObject( gateTypeReference );
      output.writeObject( grade );
      output.writeObject( highPressShapeSection );
      output.writeObject( holdPress1 );
      output.writeObject( holdPress2 );
      output.writeObject( holdPress3 );
      output.writeObject( holdPressSpeed1 );
      output.writeObject( holdPressSpeed2 );
      output.writeObject( holdPressSpeed3 );
      output.writeObject( holdPressTime );
      output.writeObject( holdPressTurnPoint );
      output.writeObject( injectPress1 );
      output.writeObject( injectPress2 );
      output.writeObject( injectPress3 );
      output.writeObject( injectPress4 );
      output.writeObject( injectPress5 );
      output.writeObject( injectPressUnitReference );
      output.writeObject( injectSpeed1 );
      output.writeObject( injectSpeed2 );
      output.writeObject( injectSpeed3 );
      output.writeObject( injectSpeed4 );
      output.writeObject( injectSpeed5 );
      output.writeObject( insertWeight );
      output.writeObject( lowPressShape );
      output.writeObject( lowPressShapeUnitReference );
      output.writeObject( machineSpec );
      output.writeObject( materialReference );
      output.writeObject( maxPress );
      output.writeObject( mensuration );
      output.writeObject( mensurationDist );
      output.writeObject( mensurationTime );
      output.writeObject( moistureRate );
      output.writeObject( moldBaseSizeHeight );
      output.writeObject( moldBaseSizeLength );
      output.writeObject( moldBaseSizeWidth );
      output.writeObject( moldCloseTime );
      output.writeObject( moldOpenDist1 );
      output.writeObject( moldOpenDist2 );
      output.writeObject( moldOpenDist3 );
      output.writeObject( moldOpenSpeed1 );
      output.writeObject( moldOpenSpeed2 );
      output.writeObject( moldOpenSpeed3 );
      output.writeObject( moldStrucEtc );
      output.writeObject( moldStrucReference );
      output.writeObject( mountThicknessEtc );
      output.writeObject( mountThicknessReference );
      output.writeObject( movingSideTemp );
      output.writeObject( movingSideTempInput );
      output.writeObject( nozzleType );
      output.writeObject( oilTemp );
      output.writeObject( packingPressUnitReference );
      output.writeObject( pressCount );
      output.writeObject( pressTime );
      output.writeObject( pressures );
      output.writeObject( screwDiameter );
      output.writeObject( shortTransition1 );
      output.writeObject( shortTransition2 );
      output.writeObject( shortTransition3 );
      output.writeObject( shortTransition4 );
      output.writeObject( shortTransition5 );
      output.writeObject( shotCount );
      output.writeObject( shotWeight );
      output.writeObject( speed );
      output.writeObject( spillResistantSpeed );
      output.writeObject( spillResistentCfg );
      output.writeObject( sprue );
      output.writeObject( stroke );
      output.writeObject( temperatureSensorReference );
      output.writeObject( tiebarInterval );
      output.writeObject( tone );
      output.writeObject( weight );
   }

   protected void super_writeExternal_KETTryMold(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.trycondition.entity.KETTryMold) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETTryMold(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "backPress", backPress );
      output.writeObject( "backPressUnitReference", backPressUnitReference, wt.fc.ObjectReference.class, true );
      output.setString( "cavityBigo", cavityBigo );
      output.setString( "cavityCount", cavityCount );
      output.setString( "color", color );
      output.setString( "coolWaterTempHigh", coolWaterTempHigh );
      output.setString( "coolWaterTempLow", coolWaterTempLow );
      output.setString( "coolingTime", coolingTime );
      output.setString( "cushionAmount", cushionAmount );
      output.setString( "cvWeight", cvWeight );
      output.setString( "cycleTime", cycleTime );
      output.setString( "cylinderTempN1", cylinderTempN1 );
      output.setString( "cylinderTempN2", cylinderTempN2 );
      output.setString( "cylinderTempN3", cylinderTempN3 );
      output.setString( "cylinderTempN4", cylinderTempN4 );
      output.setString( "cylinderTempNH", cylinderTempNH );
      output.setString( "delayTime", delayTime );
      output.setString( "dryTemp", dryTemp );
      output.setString( "dryTime", dryTime );
      output.setString( "fixedSideTemp", fixedSideTemp );
      output.setString( "fixedSideTempInput", fixedSideTempInput );
      output.writeObject( "gateTypeReference", gateTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "grade", grade );
      output.setString( "highPressShapeSection", highPressShapeSection );
      output.setString( "holdPress1", holdPress1 );
      output.setString( "holdPress2", holdPress2 );
      output.setString( "holdPress3", holdPress3 );
      output.setString( "holdPressSpeed1", holdPressSpeed1 );
      output.setString( "holdPressSpeed2", holdPressSpeed2 );
      output.setString( "holdPressSpeed3", holdPressSpeed3 );
      output.setString( "holdPressTime", holdPressTime );
      output.setString( "holdPressTurnPoint", holdPressTurnPoint );
      output.setString( "injectPress1", injectPress1 );
      output.setString( "injectPress2", injectPress2 );
      output.setString( "injectPress3", injectPress3 );
      output.setString( "injectPress4", injectPress4 );
      output.setString( "injectPress5", injectPress5 );
      output.writeObject( "injectPressUnitReference", injectPressUnitReference, wt.fc.ObjectReference.class, true );
      output.setString( "injectSpeed1", injectSpeed1 );
      output.setString( "injectSpeed2", injectSpeed2 );
      output.setString( "injectSpeed3", injectSpeed3 );
      output.setString( "injectSpeed4", injectSpeed4 );
      output.setString( "injectSpeed5", injectSpeed5 );
      output.setString( "insertWeight", insertWeight );
      output.setString( "lowPressShape", lowPressShape );
      output.writeObject( "lowPressShapeUnitReference", lowPressShapeUnitReference, wt.fc.ObjectReference.class, true );
      output.setString( "machineSpec", machineSpec );
      output.writeObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      output.setString( "maxPress", maxPress );
      output.setString( "mensuration", mensuration );
      output.setString( "mensurationDist", mensurationDist );
      output.setString( "mensurationTime", mensurationTime );
      output.setString( "moistureRate", moistureRate );
      output.setString( "moldBaseSizeHeight", moldBaseSizeHeight );
      output.setString( "moldBaseSizeLength", moldBaseSizeLength );
      output.setString( "moldBaseSizeWidth", moldBaseSizeWidth );
      output.setString( "moldCloseTime", moldCloseTime );
      output.setString( "moldOpenDist1", moldOpenDist1 );
      output.setString( "moldOpenDist2", moldOpenDist2 );
      output.setString( "moldOpenDist3", moldOpenDist3 );
      output.setString( "moldOpenSpeed1", moldOpenSpeed1 );
      output.setString( "moldOpenSpeed2", moldOpenSpeed2 );
      output.setString( "moldOpenSpeed3", moldOpenSpeed3 );
      output.setString( "moldStrucEtc", moldStrucEtc );
      output.writeObject( "moldStrucReference", moldStrucReference, wt.fc.ObjectReference.class, true );
      output.setString( "mountThicknessEtc", mountThicknessEtc );
      output.writeObject( "mountThicknessReference", mountThicknessReference, wt.fc.ObjectReference.class, true );
      output.setString( "movingSideTemp", movingSideTemp );
      output.setString( "movingSideTempInput", movingSideTempInput );
      output.setString( "nozzleType", nozzleType );
      output.setString( "oilTemp", oilTemp );
      output.writeObject( "packingPressUnitReference", packingPressUnitReference, wt.fc.ObjectReference.class, true );
      output.setString( "pressCount", pressCount );
      output.setString( "pressTime", pressTime );
      output.setString( "pressures", pressures );
      output.setString( "screwDiameter", screwDiameter );
      output.setString( "shortTransition1", shortTransition1 );
      output.setString( "shortTransition2", shortTransition2 );
      output.setString( "shortTransition3", shortTransition3 );
      output.setString( "shortTransition4", shortTransition4 );
      output.setString( "shortTransition5", shortTransition5 );
      output.setString( "shotCount", shotCount );
      output.setString( "shotWeight", shotWeight );
      output.setString( "speed", speed );
      output.setString( "spillResistantSpeed", spillResistantSpeed );
      output.setString( "spillResistentCfg", spillResistentCfg );
      output.setString( "sprue", sprue );
      output.setString( "stroke", stroke );
      output.writeObject( "temperatureSensorReference", temperatureSensorReference, wt.fc.ObjectReference.class, true );
      output.setString( "tiebarInterval", tiebarInterval );
      output.setString( "tone", tone );
      output.setString( "weight", weight );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      backPress = input.getString( "backPress" );
      backPressUnitReference = (wt.fc.ObjectReference) input.readObject( "backPressUnitReference", backPressUnitReference, wt.fc.ObjectReference.class, true );
      cavityBigo = input.getString( "cavityBigo" );
      cavityCount = input.getString( "cavityCount" );
      color = input.getString( "color" );
      coolWaterTempHigh = input.getString( "coolWaterTempHigh" );
      coolWaterTempLow = input.getString( "coolWaterTempLow" );
      coolingTime = input.getString( "coolingTime" );
      cushionAmount = input.getString( "cushionAmount" );
      cvWeight = input.getString( "cvWeight" );
      cycleTime = input.getString( "cycleTime" );
      cylinderTempN1 = input.getString( "cylinderTempN1" );
      cylinderTempN2 = input.getString( "cylinderTempN2" );
      cylinderTempN3 = input.getString( "cylinderTempN3" );
      cylinderTempN4 = input.getString( "cylinderTempN4" );
      cylinderTempNH = input.getString( "cylinderTempNH" );
      delayTime = input.getString( "delayTime" );
      dryTemp = input.getString( "dryTemp" );
      dryTime = input.getString( "dryTime" );
      fixedSideTemp = input.getString( "fixedSideTemp" );
      fixedSideTempInput = input.getString( "fixedSideTempInput" );
      gateTypeReference = (wt.fc.ObjectReference) input.readObject( "gateTypeReference", gateTypeReference, wt.fc.ObjectReference.class, true );
      grade = input.getString( "grade" );
      highPressShapeSection = input.getString( "highPressShapeSection" );
      holdPress1 = input.getString( "holdPress1" );
      holdPress2 = input.getString( "holdPress2" );
      holdPress3 = input.getString( "holdPress3" );
      holdPressSpeed1 = input.getString( "holdPressSpeed1" );
      holdPressSpeed2 = input.getString( "holdPressSpeed2" );
      holdPressSpeed3 = input.getString( "holdPressSpeed3" );
      holdPressTime = input.getString( "holdPressTime" );
      holdPressTurnPoint = input.getString( "holdPressTurnPoint" );
      injectPress1 = input.getString( "injectPress1" );
      injectPress2 = input.getString( "injectPress2" );
      injectPress3 = input.getString( "injectPress3" );
      injectPress4 = input.getString( "injectPress4" );
      injectPress5 = input.getString( "injectPress5" );
      injectPressUnitReference = (wt.fc.ObjectReference) input.readObject( "injectPressUnitReference", injectPressUnitReference, wt.fc.ObjectReference.class, true );
      injectSpeed1 = input.getString( "injectSpeed1" );
      injectSpeed2 = input.getString( "injectSpeed2" );
      injectSpeed3 = input.getString( "injectSpeed3" );
      injectSpeed4 = input.getString( "injectSpeed4" );
      injectSpeed5 = input.getString( "injectSpeed5" );
      insertWeight = input.getString( "insertWeight" );
      lowPressShape = input.getString( "lowPressShape" );
      lowPressShapeUnitReference = (wt.fc.ObjectReference) input.readObject( "lowPressShapeUnitReference", lowPressShapeUnitReference, wt.fc.ObjectReference.class, true );
      machineSpec = input.getString( "machineSpec" );
      materialReference = (wt.fc.ObjectReference) input.readObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      maxPress = input.getString( "maxPress" );
      mensuration = input.getString( "mensuration" );
      mensurationDist = input.getString( "mensurationDist" );
      mensurationTime = input.getString( "mensurationTime" );
      moistureRate = input.getString( "moistureRate" );
      moldBaseSizeHeight = input.getString( "moldBaseSizeHeight" );
      moldBaseSizeLength = input.getString( "moldBaseSizeLength" );
      moldBaseSizeWidth = input.getString( "moldBaseSizeWidth" );
      moldCloseTime = input.getString( "moldCloseTime" );
      moldOpenDist1 = input.getString( "moldOpenDist1" );
      moldOpenDist2 = input.getString( "moldOpenDist2" );
      moldOpenDist3 = input.getString( "moldOpenDist3" );
      moldOpenSpeed1 = input.getString( "moldOpenSpeed1" );
      moldOpenSpeed2 = input.getString( "moldOpenSpeed2" );
      moldOpenSpeed3 = input.getString( "moldOpenSpeed3" );
      moldStrucEtc = input.getString( "moldStrucEtc" );
      moldStrucReference = (wt.fc.ObjectReference) input.readObject( "moldStrucReference", moldStrucReference, wt.fc.ObjectReference.class, true );
      mountThicknessEtc = input.getString( "mountThicknessEtc" );
      mountThicknessReference = (wt.fc.ObjectReference) input.readObject( "mountThicknessReference", mountThicknessReference, wt.fc.ObjectReference.class, true );
      movingSideTemp = input.getString( "movingSideTemp" );
      movingSideTempInput = input.getString( "movingSideTempInput" );
      nozzleType = input.getString( "nozzleType" );
      oilTemp = input.getString( "oilTemp" );
      packingPressUnitReference = (wt.fc.ObjectReference) input.readObject( "packingPressUnitReference", packingPressUnitReference, wt.fc.ObjectReference.class, true );
      pressCount = input.getString( "pressCount" );
      pressTime = input.getString( "pressTime" );
      pressures = input.getString( "pressures" );
      screwDiameter = input.getString( "screwDiameter" );
      shortTransition1 = input.getString( "shortTransition1" );
      shortTransition2 = input.getString( "shortTransition2" );
      shortTransition3 = input.getString( "shortTransition3" );
      shortTransition4 = input.getString( "shortTransition4" );
      shortTransition5 = input.getString( "shortTransition5" );
      shotCount = input.getString( "shotCount" );
      shotWeight = input.getString( "shotWeight" );
      speed = input.getString( "speed" );
      spillResistantSpeed = input.getString( "spillResistantSpeed" );
      spillResistentCfg = input.getString( "spillResistentCfg" );
      sprue = input.getString( "sprue" );
      stroke = input.getString( "stroke" );
      temperatureSensorReference = (wt.fc.ObjectReference) input.readObject( "temperatureSensorReference", temperatureSensorReference, wt.fc.ObjectReference.class, true );
      tiebarInterval = input.getString( "tiebarInterval" );
      tone = input.getString( "tone" );
      weight = input.getString( "weight" );
   }

   boolean readVersion_6509891109043753488L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      backPress = (java.lang.String) input.readObject();
      backPressUnitReference = (wt.fc.ObjectReference) input.readObject();
      cavityBigo = (java.lang.String) input.readObject();
      cavityCount = (java.lang.String) input.readObject();
      color = (java.lang.String) input.readObject();
      coolWaterTempHigh = (java.lang.String) input.readObject();
      coolWaterTempLow = (java.lang.String) input.readObject();
      coolingTime = (java.lang.String) input.readObject();
      cushionAmount = (java.lang.String) input.readObject();
      cvWeight = (java.lang.String) input.readObject();
      cycleTime = (java.lang.String) input.readObject();
      cylinderTempN1 = (java.lang.String) input.readObject();
      cylinderTempN2 = (java.lang.String) input.readObject();
      cylinderTempN3 = (java.lang.String) input.readObject();
      cylinderTempN4 = (java.lang.String) input.readObject();
      cylinderTempNH = (java.lang.String) input.readObject();
      delayTime = (java.lang.String) input.readObject();
      dryTemp = (java.lang.String) input.readObject();
      dryTime = (java.lang.String) input.readObject();
      fixedSideTemp = (java.lang.String) input.readObject();
      fixedSideTempInput = (java.lang.String) input.readObject();
      gateTypeReference = (wt.fc.ObjectReference) input.readObject();
      grade = (java.lang.String) input.readObject();
      highPressShapeSection = (java.lang.String) input.readObject();
      holdPress1 = (java.lang.String) input.readObject();
      holdPress2 = (java.lang.String) input.readObject();
      holdPress3 = (java.lang.String) input.readObject();
      holdPressSpeed1 = (java.lang.String) input.readObject();
      holdPressSpeed2 = (java.lang.String) input.readObject();
      holdPressSpeed3 = (java.lang.String) input.readObject();
      holdPressTime = (java.lang.String) input.readObject();
      holdPressTurnPoint = (java.lang.String) input.readObject();
      injectPress1 = (java.lang.String) input.readObject();
      injectPress2 = (java.lang.String) input.readObject();
      injectPress3 = (java.lang.String) input.readObject();
      injectPress4 = (java.lang.String) input.readObject();
      injectPress5 = (java.lang.String) input.readObject();
      injectPressUnitReference = (wt.fc.ObjectReference) input.readObject();
      injectSpeed1 = (java.lang.String) input.readObject();
      injectSpeed2 = (java.lang.String) input.readObject();
      injectSpeed3 = (java.lang.String) input.readObject();
      injectSpeed4 = (java.lang.String) input.readObject();
      injectSpeed5 = (java.lang.String) input.readObject();
      insertWeight = (java.lang.String) input.readObject();
      lowPressShape = (java.lang.String) input.readObject();
      lowPressShapeUnitReference = (wt.fc.ObjectReference) input.readObject();
      machineSpec = (java.lang.String) input.readObject();
      materialReference = (wt.fc.ObjectReference) input.readObject();
      maxPress = (java.lang.String) input.readObject();
      mensuration = (java.lang.String) input.readObject();
      mensurationDist = (java.lang.String) input.readObject();
      mensurationTime = (java.lang.String) input.readObject();
      moistureRate = (java.lang.String) input.readObject();
      moldBaseSizeHeight = (java.lang.String) input.readObject();
      moldBaseSizeLength = (java.lang.String) input.readObject();
      moldBaseSizeWidth = (java.lang.String) input.readObject();
      moldCloseTime = (java.lang.String) input.readObject();
      moldOpenDist1 = (java.lang.String) input.readObject();
      moldOpenDist2 = (java.lang.String) input.readObject();
      moldOpenDist3 = (java.lang.String) input.readObject();
      moldOpenSpeed1 = (java.lang.String) input.readObject();
      moldOpenSpeed2 = (java.lang.String) input.readObject();
      moldOpenSpeed3 = (java.lang.String) input.readObject();
      moldStrucEtc = (java.lang.String) input.readObject();
      moldStrucReference = (wt.fc.ObjectReference) input.readObject();
      mountThicknessEtc = (java.lang.String) input.readObject();
      mountThicknessReference = (wt.fc.ObjectReference) input.readObject();
      movingSideTemp = (java.lang.String) input.readObject();
      movingSideTempInput = (java.lang.String) input.readObject();
      nozzleType = (java.lang.String) input.readObject();
      oilTemp = (java.lang.String) input.readObject();
      packingPressUnitReference = (wt.fc.ObjectReference) input.readObject();
      pressCount = (java.lang.String) input.readObject();
      pressTime = (java.lang.String) input.readObject();
      pressures = (java.lang.String) input.readObject();
      screwDiameter = (java.lang.String) input.readObject();
      shortTransition1 = (java.lang.String) input.readObject();
      shortTransition2 = (java.lang.String) input.readObject();
      shortTransition3 = (java.lang.String) input.readObject();
      shortTransition4 = (java.lang.String) input.readObject();
      shortTransition5 = (java.lang.String) input.readObject();
      shotCount = (java.lang.String) input.readObject();
      shotWeight = (java.lang.String) input.readObject();
      speed = (java.lang.String) input.readObject();
      spillResistantSpeed = (java.lang.String) input.readObject();
      spillResistentCfg = (java.lang.String) input.readObject();
      sprue = (java.lang.String) input.readObject();
      stroke = (java.lang.String) input.readObject();
      temperatureSensorReference = (wt.fc.ObjectReference) input.readObject();
      tiebarInterval = (java.lang.String) input.readObject();
      tone = (java.lang.String) input.readObject();
      weight = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETTryMold thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_6509891109043753488L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETTryMold( _KETTryMold thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
