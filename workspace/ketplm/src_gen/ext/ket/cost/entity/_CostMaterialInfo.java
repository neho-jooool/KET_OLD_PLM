package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostMaterialInfo implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostMaterialInfo.class.getName();

   /**
    * 원재료 품번
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * 원재료 품번
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * 원재료 품번
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
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
    * 원재료명
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * 원재료명
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * 원재료명
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
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
    * 원재료 단가(수지, 비철)
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String MATERIAL_PRICE = "materialPrice";
   static int MATERIAL_PRICE_UPPER_LIMIT = -1;
   java.lang.String materialPrice = "0";
   /**
    * 원재료 단가(수지, 비철)
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getMaterialPrice() {
      return materialPrice;
   }
   /**
    * 원재료 단가(수지, 비철)
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public void setMaterialPrice(java.lang.String materialPrice) throws wt.util.WTPropertyVetoException {
      materialPriceValidate(materialPrice);
      this.materialPrice = materialPrice;
   }
   void materialPriceValidate(java.lang.String materialPrice) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_PRICE_UPPER_LIMIT < 1) {
         try { MATERIAL_PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("materialPrice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_PRICE_UPPER_LIMIT = 200; }
      }
      if (materialPrice != null && !wt.fc.PersistenceHelper.checkStoredLength(materialPrice.toString(), MATERIAL_PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialPrice"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "materialPrice", this.materialPrice, materialPrice));
   }

   /**
    * grade
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String GRADE = "grade";
   static int GRADE_UPPER_LIMIT = -1;
   java.lang.String grade;
   /**
    * grade
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getGrade() {
      return grade;
   }
   /**
    * grade
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
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
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String COLOR = "color";
   static int COLOR_UPPER_LIMIT = -1;
   java.lang.String color;
   /**
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getColor() {
      return color;
   }
   /**
    * @see ext.ket.cost.entity.CostMaterialInfo
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
    * 스크랩판매비
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String SCRAP_COST = "scrapCost";
   static int SCRAP_COST_UPPER_LIMIT = -1;
   java.lang.String scrapCost;
   /**
    * 스크랩판매비
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getScrapCost() {
      return scrapCost;
   }
   /**
    * 스크랩판매비
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public void setScrapCost(java.lang.String scrapCost) throws wt.util.WTPropertyVetoException {
      scrapCostValidate(scrapCost);
      this.scrapCost = scrapCost;
   }
   void scrapCostValidate(java.lang.String scrapCost) throws wt.util.WTPropertyVetoException {
      if (SCRAP_COST_UPPER_LIMIT < 1) {
         try { SCRAP_COST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapCost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_COST_UPPER_LIMIT = 200; }
      }
      if (scrapCost != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapCost.toString(), SCRAP_COST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapCost"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_COST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapCost", this.scrapCost, scrapCost));
   }

   /**
    * 스크랩재생비
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String SCRAP_RECYCLE = "scrapRecycle";
   static int SCRAP_RECYCLE_UPPER_LIMIT = -1;
   java.lang.String scrapRecycle;
   /**
    * 스크랩재생비
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getScrapRecycle() {
      return scrapRecycle;
   }
   /**
    * 스크랩재생비
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public void setScrapRecycle(java.lang.String scrapRecycle) throws wt.util.WTPropertyVetoException {
      scrapRecycleValidate(scrapRecycle);
      this.scrapRecycle = scrapRecycle;
   }
   void scrapRecycleValidate(java.lang.String scrapRecycle) throws wt.util.WTPropertyVetoException {
      if (SCRAP_RECYCLE_UPPER_LIMIT < 1) {
         try { SCRAP_RECYCLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("scrapRecycle").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SCRAP_RECYCLE_UPPER_LIMIT = 200; }
      }
      if (scrapRecycle != null && !wt.fc.PersistenceHelper.checkStoredLength(scrapRecycle.toString(), SCRAP_RECYCLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "scrapRecycle"), java.lang.String.valueOf(java.lang.Math.min(SCRAP_RECYCLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "scrapRecycle", this.scrapRecycle, scrapRecycle));
   }

   /**
    * 비율
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String RATE = "rate";
   static int RATE_UPPER_LIMIT = -1;
   java.lang.String rate;
   /**
    * 비율
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public java.lang.String getRate() {
      return rate;
   }
   /**
    * 비율
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public void setRate(java.lang.String rate) throws wt.util.WTPropertyVetoException {
      rateValidate(rate);
      this.rate = rate;
   }
   void rateValidate(java.lang.String rate) throws wt.util.WTPropertyVetoException {
      if (RATE_UPPER_LIMIT < 1) {
         try { RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("rate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RATE_UPPER_LIMIT = 200; }
      }
      if (rate != null && !wt.fc.PersistenceHelper.checkStoredLength(rate.toString(), RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rate"), java.lang.String.valueOf(java.lang.Math.min(RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "rate", this.rate, rate));
   }

   /**
    * 자재존재여부
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public static final java.lang.String ERP_CHECK = "erpCheck";
   boolean erpCheck = false;
   /**
    * 자재존재여부
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public boolean isErpCheck() {
      return erpCheck;
   }
   /**
    * 자재존재여부
    *
    * @see ext.ket.cost.entity.CostMaterialInfo
    */
   public void setErpCheck(boolean erpCheck) throws wt.util.WTPropertyVetoException {
      erpCheckValidate(erpCheck);
      this.erpCheck = erpCheck;
   }
   void erpCheckValidate(boolean erpCheck) throws wt.util.WTPropertyVetoException {
   }

   wt.org.WTPrincipalReference owner;
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public wt.org.WTPrincipalReference getOwner() {
      return owner;
   }
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      ownerValidate(owner);
      this.owner = owner;
   }
   void ownerValidate(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException {
      if (owner == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "owner") },
               new java.beans.PropertyChangeEvent(this, "owner", this.owner, owner));
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

   public static final long EXTERNALIZATION_VERSION_UID = 2533876780731778429L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( color );
      output.writeBoolean( erpCheck );
      output.writeObject( grade );
      output.writeObject( materialPrice );
      output.writeObject( owner );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( rate );
      output.writeObject( scrapCost );
      output.writeObject( scrapRecycle );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostMaterialInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "color", color );
      output.setBoolean( "erpCheck", erpCheck );
      output.setString( "grade", grade );
      output.setString( "materialPrice", materialPrice );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "rate", rate );
      output.setString( "scrapCost", scrapCost );
      output.setString( "scrapRecycle", scrapRecycle );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      color = input.getString( "color" );
      erpCheck = input.getBoolean( "erpCheck" );
      grade = input.getString( "grade" );
      materialPrice = input.getString( "materialPrice" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      rate = input.getString( "rate" );
      scrapCost = input.getString( "scrapCost" );
      scrapRecycle = input.getString( "scrapRecycle" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion2533876780731778429L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      color = (java.lang.String) input.readObject();
      erpCheck = input.readBoolean();
      grade = (java.lang.String) input.readObject();
      materialPrice = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      rate = (java.lang.String) input.readObject();
      scrapCost = (java.lang.String) input.readObject();
      scrapRecycle = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CostMaterialInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion2533876780731778429L( input, readSerialVersionUID, superDone );
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
