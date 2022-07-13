package e3ps.bom.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETBomEcoHeader extends wt.enterprise.CabinetManaged implements wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.bom.entity.entityResource";
   static final java.lang.String CLASSNAME = KETBomEcoHeader.class.getName();

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE1 = "attribute1";
   static int ATTRIBUTE1_UPPER_LIMIT = -1;
   java.lang.String attribute1;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute1() {
      return attribute1;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE2 = "attribute2";
   static int ATTRIBUTE2_UPPER_LIMIT = -1;
   java.lang.String attribute2;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute2() {
      return attribute2;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE3 = "attribute3";
   static int ATTRIBUTE3_UPPER_LIMIT = -1;
   java.lang.String attribute3;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute3() {
      return attribute3;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE4 = "attribute4";
   static int ATTRIBUTE4_UPPER_LIMIT = -1;
   java.lang.String attribute4;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute4() {
      return attribute4;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE5 = "attribute5";
   static int ATTRIBUTE5_UPPER_LIMIT = -1;
   java.lang.String attribute5;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute5() {
      return attribute5;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE6 = "attribute6";
   static int ATTRIBUTE6_UPPER_LIMIT = -1;
   java.lang.String attribute6;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute6() {
      return attribute6;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE7 = "attribute7";
   static int ATTRIBUTE7_UPPER_LIMIT = -1;
   java.lang.String attribute7;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute7() {
      return attribute7;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE8 = "attribute8";
   static int ATTRIBUTE8_UPPER_LIMIT = -1;
   java.lang.String attribute8;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute8() {
      return attribute8;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE9 = "attribute9";
   static int ATTRIBUTE9_UPPER_LIMIT = -1;
   java.lang.String attribute9;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute9() {
      return attribute9;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ATTRIBUTE10 = "attribute10";
   static int ATTRIBUTE10_UPPER_LIMIT = -1;
   java.lang.String attribute10;
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getAttribute10() {
      return attribute10;
   }
   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ECO_HEADER_NUMBER = "ecoHeaderNumber";
   static int ECO_HEADER_NUMBER_UPPER_LIMIT = -1;
   java.lang.String ecoHeaderNumber;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getEcoHeaderNumber() {
      return ecoHeaderNumber;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setEcoHeaderNumber(java.lang.String ecoHeaderNumber) throws wt.util.WTPropertyVetoException {
      ecoHeaderNumberValidate(ecoHeaderNumber);
      this.ecoHeaderNumber = ecoHeaderNumber;
   }
   void ecoHeaderNumberValidate(java.lang.String ecoHeaderNumber) throws wt.util.WTPropertyVetoException {
      if (ECO_HEADER_NUMBER_UPPER_LIMIT < 1) {
         try { ECO_HEADER_NUMBER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoHeaderNumber").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_HEADER_NUMBER_UPPER_LIMIT = 200; }
      }
      if (ecoHeaderNumber != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoHeaderNumber.toString(), ECO_HEADER_NUMBER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoHeaderNumber"), java.lang.String.valueOf(java.lang.Math.min(ECO_HEADER_NUMBER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoHeaderNumber", this.ecoHeaderNumber, ecoHeaderNumber));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String BOMVERSION = "BOMVersion";
   static int BOMVERSION_UPPER_LIMIT = -1;
   java.lang.String BOMVersion;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getBOMVersion() {
      return BOMVersion;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setBOMVersion(java.lang.String BOMVersion) throws wt.util.WTPropertyVetoException {
      BOMVersionValidate(BOMVersion);
      this.BOMVersion = BOMVersion;
   }
   void BOMVersionValidate(java.lang.String BOMVersion) throws wt.util.WTPropertyVetoException {
      if (BOMVERSION_UPPER_LIMIT < 1) {
         try { BOMVERSION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("BOMVersion").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOMVERSION_UPPER_LIMIT = 200; }
      }
      if (BOMVersion != null && !wt.fc.PersistenceHelper.checkStoredLength(BOMVersion.toString(), BOMVERSION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "BOMVersion"), java.lang.String.valueOf(java.lang.Math.min(BOMVERSION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "BOMVersion", this.BOMVersion, BOMVersion));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String CREATOR_DEPT = "creatorDept";
   static int CREATOR_DEPT_UPPER_LIMIT = -1;
   java.lang.String creatorDept;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getCreatorDept() {
      return creatorDept;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setCreatorDept(java.lang.String creatorDept) throws wt.util.WTPropertyVetoException {
      creatorDeptValidate(creatorDept);
      this.creatorDept = creatorDept;
   }
   void creatorDeptValidate(java.lang.String creatorDept) throws wt.util.WTPropertyVetoException {
      if (CREATOR_DEPT_UPPER_LIMIT < 1) {
         try { CREATOR_DEPT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorDept").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_DEPT_UPPER_LIMIT = 200; }
      }
      if (creatorDept != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorDept.toString(), CREATOR_DEPT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorDept"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_DEPT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorDept", this.creatorDept, creatorDept));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String APPROVER_ID = "approverId";
   static int APPROVER_ID_UPPER_LIMIT = -1;
   java.lang.String approverId;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getApproverId() {
      return approverId;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setApproverId(java.lang.String approverId) throws wt.util.WTPropertyVetoException {
      approverIdValidate(approverId);
      this.approverId = approverId;
   }
   void approverIdValidate(java.lang.String approverId) throws wt.util.WTPropertyVetoException {
      if (APPROVER_ID_UPPER_LIMIT < 1) {
         try { APPROVER_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approverId").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVER_ID_UPPER_LIMIT = 200; }
      }
      if (approverId != null && !wt.fc.PersistenceHelper.checkStoredLength(approverId.toString(), APPROVER_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approverId"), java.lang.String.valueOf(java.lang.Math.min(APPROVER_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approverId", this.approverId, approverId));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String APPROVER_NAME = "approverName";
   static int APPROVER_NAME_UPPER_LIMIT = -1;
   java.lang.String approverName;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getApproverName() {
      return approverName;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setApproverName(java.lang.String approverName) throws wt.util.WTPropertyVetoException {
      approverNameValidate(approverName);
      this.approverName = approverName;
   }
   void approverNameValidate(java.lang.String approverName) throws wt.util.WTPropertyVetoException {
      if (APPROVER_NAME_UPPER_LIMIT < 1) {
         try { APPROVER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approverName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVER_NAME_UPPER_LIMIT = 200; }
      }
      if (approverName != null && !wt.fc.PersistenceHelper.checkStoredLength(approverName.toString(), APPROVER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approverName"), java.lang.String.valueOf(java.lang.Math.min(APPROVER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approverName", this.approverName, approverName));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String APPROVER_DEPT = "approverDept";
   static int APPROVER_DEPT_UPPER_LIMIT = -1;
   java.lang.String approverDept;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getApproverDept() {
      return approverDept;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setApproverDept(java.lang.String approverDept) throws wt.util.WTPropertyVetoException {
      approverDeptValidate(approverDept);
      this.approverDept = approverDept;
   }
   void approverDeptValidate(java.lang.String approverDept) throws wt.util.WTPropertyVetoException {
      if (APPROVER_DEPT_UPPER_LIMIT < 1) {
         try { APPROVER_DEPT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("approverDept").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPROVER_DEPT_UPPER_LIMIT = 200; }
      }
      if (approverDept != null && !wt.fc.PersistenceHelper.checkStoredLength(approverDept.toString(), APPROVER_DEPT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "approverDept"), java.lang.String.valueOf(java.lang.Math.min(APPROVER_DEPT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "approverDept", this.approverDept, approverDept));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String APPROVE_DATE = "approveDate";
   java.sql.Timestamp approveDate;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.sql.Timestamp getApproveDate() {
      return approveDate;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setApproveDate(java.sql.Timestamp approveDate) throws wt.util.WTPropertyVetoException {
      approveDateValidate(approveDate);
      this.approveDate = approveDate;
   }
   void approveDateValidate(java.sql.Timestamp approveDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ORG_CODE = "orgCode";
   static int ORG_CODE_UPPER_LIMIT = -1;
   java.lang.String orgCode;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getOrgCode() {
      return orgCode;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setOrgCode(java.lang.String orgCode) throws wt.util.WTPropertyVetoException {
      orgCodeValidate(orgCode);
      this.orgCode = orgCode;
   }
   void orgCodeValidate(java.lang.String orgCode) throws wt.util.WTPropertyVetoException {
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ORG_NAME = "orgName";
   static int ORG_NAME_UPPER_LIMIT = -1;
   java.lang.String orgName;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getOrgName() {
      return orgName;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setOrgName(java.lang.String orgName) throws wt.util.WTPropertyVetoException {
      orgNameValidate(orgName);
      this.orgName = orgName;
   }
   void orgNameValidate(java.lang.String orgName) throws wt.util.WTPropertyVetoException {
      if (ORG_NAME_UPPER_LIMIT < 1) {
         try { ORG_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("orgName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ORG_NAME_UPPER_LIMIT = 200; }
      }
      if (orgName != null && !wt.fc.PersistenceHelper.checkStoredLength(orgName.toString(), ORG_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "orgName"), java.lang.String.valueOf(java.lang.Math.min(ORG_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "orgName", this.orgName, orgName));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String CHECKOUT_STATUS = "checkoutStatus";
   static int CHECKOUT_STATUS_UPPER_LIMIT = -1;
   java.lang.String checkoutStatus;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getCheckoutStatus() {
      return checkoutStatus;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setCheckoutStatus(java.lang.String checkoutStatus) throws wt.util.WTPropertyVetoException {
      checkoutStatusValidate(checkoutStatus);
      this.checkoutStatus = checkoutStatus;
   }
   void checkoutStatusValidate(java.lang.String checkoutStatus) throws wt.util.WTPropertyVetoException {
      if (CHECKOUT_STATUS_UPPER_LIMIT < 1) {
         try { CHECKOUT_STATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkoutStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECKOUT_STATUS_UPPER_LIMIT = 200; }
      }
      if (checkoutStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(checkoutStatus.toString(), CHECKOUT_STATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkoutStatus"), java.lang.String.valueOf(java.lang.Math.min(CHECKOUT_STATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkoutStatus", this.checkoutStatus, checkoutStatus));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String SUBSTITUDE_BOM = "substitudeBOM";
   static int SUBSTITUDE_BOM_UPPER_LIMIT = -1;
   java.lang.String substitudeBOM;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getSubstitudeBOM() {
      return substitudeBOM;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setSubstitudeBOM(java.lang.String substitudeBOM) throws wt.util.WTPropertyVetoException {
      substitudeBOMValidate(substitudeBOM);
      this.substitudeBOM = substitudeBOM;
   }
   void substitudeBOMValidate(java.lang.String substitudeBOM) throws wt.util.WTPropertyVetoException {
      if (SUBSTITUDE_BOM_UPPER_LIMIT < 1) {
         try { SUBSTITUDE_BOM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("substitudeBOM").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUBSTITUDE_BOM_UPPER_LIMIT = 200; }
      }
      if (substitudeBOM != null && !wt.fc.PersistenceHelper.checkStoredLength(substitudeBOM.toString(), SUBSTITUDE_BOM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "substitudeBOM"), java.lang.String.valueOf(java.lang.Math.min(SUBSTITUDE_BOM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "substitudeBOM", this.substitudeBOM, substitudeBOM));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String BOMUSE = "BOMUse";
   static int BOMUSE_UPPER_LIMIT = -1;
   java.lang.String BOMUse;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getBOMUse() {
      return BOMUse;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setBOMUse(java.lang.String BOMUse) throws wt.util.WTPropertyVetoException {
      BOMUseValidate(BOMUse);
      this.BOMUse = BOMUse;
   }
   void BOMUseValidate(java.lang.String BOMUse) throws wt.util.WTPropertyVetoException {
      if (BOMUSE_UPPER_LIMIT < 1) {
         try { BOMUSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("BOMUse").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOMUSE_UPPER_LIMIT = 200; }
      }
      if (BOMUse != null && !wt.fc.PersistenceHelper.checkStoredLength(BOMUse.toString(), BOMUSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "BOMUse"), java.lang.String.valueOf(java.lang.Math.min(BOMUSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "BOMUse", this.BOMUse, BOMUse));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String BOMTEXT = "BOMText";
   static int BOMTEXT_UPPER_LIMIT = -1;
   java.lang.String BOMText;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getBOMText() {
      return BOMText;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setBOMText(java.lang.String BOMText) throws wt.util.WTPropertyVetoException {
      BOMTextValidate(BOMText);
      this.BOMText = BOMText;
   }
   void BOMTextValidate(java.lang.String BOMText) throws wt.util.WTPropertyVetoException {
      if (BOMTEXT_UPPER_LIMIT < 1) {
         try { BOMTEXT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("BOMText").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOMTEXT_UPPER_LIMIT = 200; }
      }
      if (BOMText != null && !wt.fc.PersistenceHelper.checkStoredLength(BOMText.toString(), BOMTEXT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "BOMText"), java.lang.String.valueOf(java.lang.Math.min(BOMTEXT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "BOMText", this.BOMText, BOMText));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String SUBSTITUDE_TEXT = "substitudeText";
   static int SUBSTITUDE_TEXT_UPPER_LIMIT = -1;
   java.lang.String substitudeText;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getSubstitudeText() {
      return substitudeText;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setSubstitudeText(java.lang.String substitudeText) throws wt.util.WTPropertyVetoException {
      substitudeTextValidate(substitudeText);
      this.substitudeText = substitudeText;
   }
   void substitudeTextValidate(java.lang.String substitudeText) throws wt.util.WTPropertyVetoException {
      if (SUBSTITUDE_TEXT_UPPER_LIMIT < 1) {
         try { SUBSTITUDE_TEXT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("substitudeText").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SUBSTITUDE_TEXT_UPPER_LIMIT = 200; }
      }
      if (substitudeText != null && !wt.fc.PersistenceHelper.checkStoredLength(substitudeText.toString(), SUBSTITUDE_TEXT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "substitudeText"), java.lang.String.valueOf(java.lang.Math.min(SUBSTITUDE_TEXT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "substitudeText", this.substitudeText, substitudeText));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String QUANTITY = "quantity";
   static int QUANTITY_UPPER_LIMIT = -1;
   java.lang.String quantity;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getQuantity() {
      return quantity;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setQuantity(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
      quantityValidate(quantity);
      this.quantity = quantity;
   }
   void quantityValidate(java.lang.String quantity) throws wt.util.WTPropertyVetoException {
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String UNIT_OF_QUANTITY = "unitOfQuantity";
   static int UNIT_OF_QUANTITY_UPPER_LIMIT = -1;
   java.lang.String unitOfQuantity;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getUnitOfQuantity() {
      return unitOfQuantity;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setUnitOfQuantity(java.lang.String unitOfQuantity) throws wt.util.WTPropertyVetoException {
      unitOfQuantityValidate(unitOfQuantity);
      this.unitOfQuantity = unitOfQuantity;
   }
   void unitOfQuantityValidate(java.lang.String unitOfQuantity) throws wt.util.WTPropertyVetoException {
      if (UNIT_OF_QUANTITY_UPPER_LIMIT < 1) {
         try { UNIT_OF_QUANTITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("unitOfQuantity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { UNIT_OF_QUANTITY_UPPER_LIMIT = 200; }
      }
      if (unitOfQuantity != null && !wt.fc.PersistenceHelper.checkStoredLength(unitOfQuantity.toString(), UNIT_OF_QUANTITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "unitOfQuantity"), java.lang.String.valueOf(java.lang.Math.min(UNIT_OF_QUANTITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "unitOfQuantity", this.unitOfQuantity, unitOfQuantity));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String BOMSTATUS = "BOMStatus";
   static int BOMSTATUS_UPPER_LIMIT = -1;
   java.lang.String BOMStatus;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getBOMStatus() {
      return BOMStatus;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setBOMStatus(java.lang.String BOMStatus) throws wt.util.WTPropertyVetoException {
      BOMStatusValidate(BOMStatus);
      this.BOMStatus = BOMStatus;
   }
   void BOMStatusValidate(java.lang.String BOMStatus) throws wt.util.WTPropertyVetoException {
      if (BOMSTATUS_UPPER_LIMIT < 1) {
         try { BOMSTATUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("BOMStatus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BOMSTATUS_UPPER_LIMIT = 200; }
      }
      if (BOMStatus != null && !wt.fc.PersistenceHelper.checkStoredLength(BOMStatus.toString(), BOMSTATUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "BOMStatus"), java.lang.String.valueOf(java.lang.Math.min(BOMSTATUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "BOMStatus", this.BOMStatus, BOMStatus));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String TRANSFER_FLAG = "transferFlag";
   static int TRANSFER_FLAG_UPPER_LIMIT = -1;
   java.lang.String transferFlag;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getTransferFlag() {
      return transferFlag;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setTransferFlag(java.lang.String transferFlag) throws wt.util.WTPropertyVetoException {
      transferFlagValidate(transferFlag);
      this.transferFlag = transferFlag;
   }
   void transferFlagValidate(java.lang.String transferFlag) throws wt.util.WTPropertyVetoException {
      if (TRANSFER_FLAG_UPPER_LIMIT < 1) {
         try { TRANSFER_FLAG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("transferFlag").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRANSFER_FLAG_UPPER_LIMIT = 200; }
      }
      if (transferFlag != null && !wt.fc.PersistenceHelper.checkStoredLength(transferFlag.toString(), TRANSFER_FLAG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "transferFlag"), java.lang.String.valueOf(java.lang.Math.min(TRANSFER_FLAG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "transferFlag", this.transferFlag, transferFlag));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String BOX_QUANTITY = "boxQuantity";
   static int BOX_QUANTITY_UPPER_LIMIT = -1;
   java.lang.String boxQuantity;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getBoxQuantity() {
      return boxQuantity;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
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
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setDescription(java.lang.String description) throws wt.util.WTPropertyVetoException {
      descriptionValidate(description);
      this.description = description;
   }
   void descriptionValidate(java.lang.String description) throws wt.util.WTPropertyVetoException {
      if (DESCRIPTION_UPPER_LIMIT < 1) {
         try { DESCRIPTION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("description").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DESCRIPTION_UPPER_LIMIT = 200; }
      }
      if (description != null && !wt.fc.PersistenceHelper.checkStoredLength(description.toString(), DESCRIPTION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "description"), java.lang.String.valueOf(java.lang.Math.min(DESCRIPTION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "description", this.description, description));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String IS_MULTIPLE = "isMultiple";
   static int IS_MULTIPLE_UPPER_LIMIT = -1;
   java.lang.String isMultiple;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getIsMultiple() {
      return isMultiple;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setIsMultiple(java.lang.String isMultiple) throws wt.util.WTPropertyVetoException {
      isMultipleValidate(isMultiple);
      this.isMultiple = isMultiple;
   }
   void isMultipleValidate(java.lang.String isMultiple) throws wt.util.WTPropertyVetoException {
      if (IS_MULTIPLE_UPPER_LIMIT < 1) {
         try { IS_MULTIPLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("isMultiple").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IS_MULTIPLE_UPPER_LIMIT = 200; }
      }
      if (isMultiple != null && !wt.fc.PersistenceHelper.checkStoredLength(isMultiple.toString(), IS_MULTIPLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "isMultiple"), java.lang.String.valueOf(java.lang.Math.min(IS_MULTIPLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "isMultiple", this.isMultiple, isMultiple));
   }

   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public static final java.lang.String ECO_ITEM_CODE = "ecoItemCode";
   static int ECO_ITEM_CODE_UPPER_LIMIT = -1;
   java.lang.String ecoItemCode;
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public java.lang.String getEcoItemCode() {
      return ecoItemCode;
   }
   /**
    * @see e3ps.bom.entity.KETBomEcoHeader
    */
   public void setEcoItemCode(java.lang.String ecoItemCode) throws wt.util.WTPropertyVetoException {
      ecoItemCodeValidate(ecoItemCode);
      this.ecoItemCode = ecoItemCode;
   }
   void ecoItemCodeValidate(java.lang.String ecoItemCode) throws wt.util.WTPropertyVetoException {
      if (ECO_ITEM_CODE_UPPER_LIMIT < 1) {
         try { ECO_ITEM_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoItemCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_ITEM_CODE_UPPER_LIMIT = 200; }
      }
      if (ecoItemCode != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoItemCode.toString(), ECO_ITEM_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoItemCode"), java.lang.String.valueOf(java.lang.Math.min(ECO_ITEM_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoItemCode", this.ecoItemCode, ecoItemCode));
   }

   /**
    * Derived from {@link wt.inf.container.WTContainerRef#getName()}
    *
    * @see wt.inf.container.WTContained
    */
   public java.lang.String getContainerName() {
      try { return (java.lang.String) ((wt.inf.container.WTContainerRef) getContainerReference()).getName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   wt.inf.container.WTContainerRef containerReference;
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainer getContainer() {
      return (containerReference != null) ? (wt.inf.container.WTContainer) containerReference.getObject() : null;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainerRef getContainerReference() {
      return containerReference;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainer(wt.inf.container.WTContainer the_container) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setContainerReference(the_container == null ? null : wt.inf.container.WTContainerRef.newWTContainerRef((wt.inf.container.WTContainer) the_container));
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainerReference(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      containerReferenceValidate(the_containerReference);
      containerReference = (wt.inf.container.WTContainerRef) the_containerReference;
   }
   void containerReferenceValidate(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      if (the_containerReference == null || the_containerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference") },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
      if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
            !wt.inf.container.WTContainer.class.isAssignableFrom(the_containerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference"), "WTContainerRef" },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 8086173259599553091L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( BOMStatus );
      output.writeObject( BOMText );
      output.writeObject( BOMUse );
      output.writeObject( BOMVersion );
      output.writeObject( approveDate );
      output.writeObject( approverDept );
      output.writeObject( approverId );
      output.writeObject( approverName );
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
      output.writeObject( boxQuantity );
      output.writeObject( checkoutStatus );
      output.writeObject( containerReference );
      output.writeObject( creatorDept );
      output.writeObject( description );
      output.writeObject( ecoHeaderNumber );
      output.writeObject( ecoItemCode );
      output.writeObject( isMultiple );
      output.writeObject( orgCode );
      output.writeObject( orgName );
      output.writeObject( quantity );
      output.writeObject( substitudeBOM );
      output.writeObject( substitudeText );
      output.writeObject( transferFlag );
      output.writeObject( unitOfQuantity );
   }

   protected void super_writeExternal_KETBomEcoHeader(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.bom.entity.KETBomEcoHeader) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETBomEcoHeader(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "BOMStatus", BOMStatus );
      output.setString( "BOMText", BOMText );
      output.setString( "BOMUse", BOMUse );
      output.setString( "BOMVersion", BOMVersion );
      output.setTimestamp( "approveDate", approveDate );
      output.setString( "approverDept", approverDept );
      output.setString( "approverId", approverId );
      output.setString( "approverName", approverName );
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
      output.setString( "boxQuantity", boxQuantity );
      output.setString( "checkoutStatus", checkoutStatus );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "creatorDept", creatorDept );
      output.setString( "description", description );
      output.setString( "ecoHeaderNumber", ecoHeaderNumber );
      output.setString( "ecoItemCode", ecoItemCode );
      output.setString( "isMultiple", isMultiple );
      output.setString( "orgCode", orgCode );
      output.setString( "orgName", orgName );
      output.setString( "quantity", quantity );
      output.setString( "substitudeBOM", substitudeBOM );
      output.setString( "substitudeText", substitudeText );
      output.setString( "transferFlag", transferFlag );
      output.setString( "unitOfQuantity", unitOfQuantity );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      BOMStatus = input.getString( "BOMStatus" );
      BOMText = input.getString( "BOMText" );
      BOMUse = input.getString( "BOMUse" );
      BOMVersion = input.getString( "BOMVersion" );
      approveDate = input.getTimestamp( "approveDate" );
      approverDept = input.getString( "approverDept" );
      approverId = input.getString( "approverId" );
      approverName = input.getString( "approverName" );
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
      boxQuantity = input.getString( "boxQuantity" );
      checkoutStatus = input.getString( "checkoutStatus" );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      creatorDept = input.getString( "creatorDept" );
      description = input.getString( "description" );
      ecoHeaderNumber = input.getString( "ecoHeaderNumber" );
      ecoItemCode = input.getString( "ecoItemCode" );
      isMultiple = input.getString( "isMultiple" );
      orgCode = input.getString( "orgCode" );
      orgName = input.getString( "orgName" );
      quantity = input.getString( "quantity" );
      substitudeBOM = input.getString( "substitudeBOM" );
      substitudeText = input.getString( "substitudeText" );
      transferFlag = input.getString( "transferFlag" );
      unitOfQuantity = input.getString( "unitOfQuantity" );
   }

   boolean readVersion8086173259599553091L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      BOMStatus = (java.lang.String) input.readObject();
      BOMText = (java.lang.String) input.readObject();
      BOMUse = (java.lang.String) input.readObject();
      BOMVersion = (java.lang.String) input.readObject();
      approveDate = (java.sql.Timestamp) input.readObject();
      approverDept = (java.lang.String) input.readObject();
      approverId = (java.lang.String) input.readObject();
      approverName = (java.lang.String) input.readObject();
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
      boxQuantity = (java.lang.String) input.readObject();
      checkoutStatus = (java.lang.String) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      creatorDept = (java.lang.String) input.readObject();
      description = (java.lang.String) input.readObject();
      ecoHeaderNumber = (java.lang.String) input.readObject();
      ecoItemCode = (java.lang.String) input.readObject();
      isMultiple = (java.lang.String) input.readObject();
      orgCode = (java.lang.String) input.readObject();
      orgName = (java.lang.String) input.readObject();
      quantity = (java.lang.String) input.readObject();
      substitudeBOM = (java.lang.String) input.readObject();
      substitudeText = (java.lang.String) input.readObject();
      transferFlag = (java.lang.String) input.readObject();
      unitOfQuantity = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( KETBomEcoHeader thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8086173259599553091L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETBomEcoHeader( _KETBomEcoHeader thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
