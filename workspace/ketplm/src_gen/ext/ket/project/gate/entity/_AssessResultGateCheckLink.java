package ext.ket.project.gate.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _AssessResultGateCheckLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.gate.entity.entityResource";
   static final java.lang.String CLASSNAME = AssessResultGateCheckLink.class.getName();

   /**
    * 실적
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT = "sheetResult";
   int sheetResult;
   /**
    * 실적
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public int getSheetResult() {
      return sheetResult;
   }
   /**
    * 실적
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult(int sheetResult) throws wt.util.WTPropertyVetoException {
      sheetResultValidate(sheetResult);
      this.sheetResult = sheetResult;
   }
   void sheetResultValidate(int sheetResult) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Check
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK = "sheetCheck";
   static int SHEET_CHECK_UPPER_LIMIT = -1;
   java.lang.String sheetCheck;
   /**
    * Check
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck() {
      return sheetCheck;
   }
   /**
    * Check
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck(java.lang.String sheetCheck) throws wt.util.WTPropertyVetoException {
      sheetCheckValidate(sheetCheck);
      this.sheetCheck = sheetCheck;
   }
   void sheetCheckValidate(java.lang.String sheetCheck) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK_UPPER_LIMIT < 1) {
         try { SHEET_CHECK_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK_UPPER_LIMIT = 200; }
      }
      if (sheetCheck != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck.toString(), SHEET_CHECK_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck", this.sheetCheck, sheetCheck));
   }

   /**
    * sheetCheck1
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK1 = "sheetCheck1";
   static int SHEET_CHECK1_UPPER_LIMIT = -1;
   java.lang.String sheetCheck1;
   /**
    * sheetCheck1
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck1() {
      return sheetCheck1;
   }
   /**
    * sheetCheck1
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck1(java.lang.String sheetCheck1) throws wt.util.WTPropertyVetoException {
      sheetCheck1Validate(sheetCheck1);
      this.sheetCheck1 = sheetCheck1;
   }
   void sheetCheck1Validate(java.lang.String sheetCheck1) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK1_UPPER_LIMIT < 1) {
         try { SHEET_CHECK1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK1_UPPER_LIMIT = 200; }
      }
      if (sheetCheck1 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck1.toString(), SHEET_CHECK1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck1"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck1", this.sheetCheck1, sheetCheck1));
   }

   /**
    * sheetCheck2
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK2 = "sheetCheck2";
   static int SHEET_CHECK2_UPPER_LIMIT = -1;
   java.lang.String sheetCheck2;
   /**
    * sheetCheck2
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck2() {
      return sheetCheck2;
   }
   /**
    * sheetCheck2
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck2(java.lang.String sheetCheck2) throws wt.util.WTPropertyVetoException {
      sheetCheck2Validate(sheetCheck2);
      this.sheetCheck2 = sheetCheck2;
   }
   void sheetCheck2Validate(java.lang.String sheetCheck2) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK2_UPPER_LIMIT < 1) {
         try { SHEET_CHECK2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK2_UPPER_LIMIT = 200; }
      }
      if (sheetCheck2 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck2.toString(), SHEET_CHECK2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck2"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck2", this.sheetCheck2, sheetCheck2));
   }

   /**
    * sheetCheck3
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK3 = "sheetCheck3";
   static int SHEET_CHECK3_UPPER_LIMIT = -1;
   java.lang.String sheetCheck3;
   /**
    * sheetCheck3
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck3() {
      return sheetCheck3;
   }
   /**
    * sheetCheck3
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck3(java.lang.String sheetCheck3) throws wt.util.WTPropertyVetoException {
      sheetCheck3Validate(sheetCheck3);
      this.sheetCheck3 = sheetCheck3;
   }
   void sheetCheck3Validate(java.lang.String sheetCheck3) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK3_UPPER_LIMIT < 1) {
         try { SHEET_CHECK3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK3_UPPER_LIMIT = 200; }
      }
      if (sheetCheck3 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck3.toString(), SHEET_CHECK3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck3"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck3", this.sheetCheck3, sheetCheck3));
   }

   /**
    * sheetCheck4
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK4 = "sheetCheck4";
   static int SHEET_CHECK4_UPPER_LIMIT = -1;
   java.lang.String sheetCheck4;
   /**
    * sheetCheck4
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck4() {
      return sheetCheck4;
   }
   /**
    * sheetCheck4
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck4(java.lang.String sheetCheck4) throws wt.util.WTPropertyVetoException {
      sheetCheck4Validate(sheetCheck4);
      this.sheetCheck4 = sheetCheck4;
   }
   void sheetCheck4Validate(java.lang.String sheetCheck4) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK4_UPPER_LIMIT < 1) {
         try { SHEET_CHECK4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK4_UPPER_LIMIT = 200; }
      }
      if (sheetCheck4 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck4.toString(), SHEET_CHECK4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck4"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck4", this.sheetCheck4, sheetCheck4));
   }

   /**
    * sheetCheck5
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK5 = "sheetCheck5";
   static int SHEET_CHECK5_UPPER_LIMIT = -1;
   java.lang.String sheetCheck5;
   /**
    * sheetCheck5
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck5() {
      return sheetCheck5;
   }
   /**
    * sheetCheck5
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck5(java.lang.String sheetCheck5) throws wt.util.WTPropertyVetoException {
      sheetCheck5Validate(sheetCheck5);
      this.sheetCheck5 = sheetCheck5;
   }
   void sheetCheck5Validate(java.lang.String sheetCheck5) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK5_UPPER_LIMIT < 1) {
         try { SHEET_CHECK5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK5_UPPER_LIMIT = 200; }
      }
      if (sheetCheck5 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck5.toString(), SHEET_CHECK5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck5"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck5", this.sheetCheck5, sheetCheck5));
   }

   /**
    * sheetCheck6
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK6 = "sheetCheck6";
   static int SHEET_CHECK6_UPPER_LIMIT = -1;
   java.lang.String sheetCheck6;
   /**
    * sheetCheck6
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck6() {
      return sheetCheck6;
   }
   /**
    * sheetCheck6
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck6(java.lang.String sheetCheck6) throws wt.util.WTPropertyVetoException {
      sheetCheck6Validate(sheetCheck6);
      this.sheetCheck6 = sheetCheck6;
   }
   void sheetCheck6Validate(java.lang.String sheetCheck6) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK6_UPPER_LIMIT < 1) {
         try { SHEET_CHECK6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK6_UPPER_LIMIT = 200; }
      }
      if (sheetCheck6 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck6.toString(), SHEET_CHECK6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck6"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck6", this.sheetCheck6, sheetCheck6));
   }

   /**
    * sheetCheck7
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK7 = "sheetCheck7";
   static int SHEET_CHECK7_UPPER_LIMIT = -1;
   java.lang.String sheetCheck7;
   /**
    * sheetCheck7
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck7() {
      return sheetCheck7;
   }
   /**
    * sheetCheck7
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck7(java.lang.String sheetCheck7) throws wt.util.WTPropertyVetoException {
      sheetCheck7Validate(sheetCheck7);
      this.sheetCheck7 = sheetCheck7;
   }
   void sheetCheck7Validate(java.lang.String sheetCheck7) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK7_UPPER_LIMIT < 1) {
         try { SHEET_CHECK7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK7_UPPER_LIMIT = 200; }
      }
      if (sheetCheck7 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck7.toString(), SHEET_CHECK7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck7"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck7", this.sheetCheck7, sheetCheck7));
   }

   /**
    * sheetCheck8
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK8 = "sheetCheck8";
   static int SHEET_CHECK8_UPPER_LIMIT = -1;
   java.lang.String sheetCheck8;
   /**
    * sheetCheck8
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck8() {
      return sheetCheck8;
   }
   /**
    * sheetCheck8
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck8(java.lang.String sheetCheck8) throws wt.util.WTPropertyVetoException {
      sheetCheck8Validate(sheetCheck8);
      this.sheetCheck8 = sheetCheck8;
   }
   void sheetCheck8Validate(java.lang.String sheetCheck8) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK8_UPPER_LIMIT < 1) {
         try { SHEET_CHECK8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK8_UPPER_LIMIT = 200; }
      }
      if (sheetCheck8 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck8.toString(), SHEET_CHECK8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck8"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck8", this.sheetCheck8, sheetCheck8));
   }

   /**
    * sheetCheck9
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK9 = "sheetCheck9";
   static int SHEET_CHECK9_UPPER_LIMIT = -1;
   java.lang.String sheetCheck9;
   /**
    * sheetCheck9
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck9() {
      return sheetCheck9;
   }
   /**
    * sheetCheck9
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck9(java.lang.String sheetCheck9) throws wt.util.WTPropertyVetoException {
      sheetCheck9Validate(sheetCheck9);
      this.sheetCheck9 = sheetCheck9;
   }
   void sheetCheck9Validate(java.lang.String sheetCheck9) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK9_UPPER_LIMIT < 1) {
         try { SHEET_CHECK9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK9_UPPER_LIMIT = 200; }
      }
      if (sheetCheck9 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck9.toString(), SHEET_CHECK9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck9"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck9", this.sheetCheck9, sheetCheck9));
   }

   /**
    * sheetCheck10
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK10 = "sheetCheck10";
   static int SHEET_CHECK10_UPPER_LIMIT = -1;
   java.lang.String sheetCheck10;
   /**
    * sheetCheck10
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck10() {
      return sheetCheck10;
   }
   /**
    * sheetCheck10
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck10(java.lang.String sheetCheck10) throws wt.util.WTPropertyVetoException {
      sheetCheck10Validate(sheetCheck10);
      this.sheetCheck10 = sheetCheck10;
   }
   void sheetCheck10Validate(java.lang.String sheetCheck10) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK10_UPPER_LIMIT < 1) {
         try { SHEET_CHECK10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK10_UPPER_LIMIT = 200; }
      }
      if (sheetCheck10 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck10.toString(), SHEET_CHECK10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck10"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck10", this.sheetCheck10, sheetCheck10));
   }

   /**
    * sheetCheck11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK11 = "sheetCheck11";
   static int SHEET_CHECK11_UPPER_LIMIT = -1;
   java.lang.String sheetCheck11;
   /**
    * sheetCheck11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck11() {
      return sheetCheck11;
   }
   /**
    * sheetCheck11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck11(java.lang.String sheetCheck11) throws wt.util.WTPropertyVetoException {
      sheetCheck11Validate(sheetCheck11);
      this.sheetCheck11 = sheetCheck11;
   }
   void sheetCheck11Validate(java.lang.String sheetCheck11) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK11_UPPER_LIMIT < 1) {
         try { SHEET_CHECK11_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck11").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK11_UPPER_LIMIT = 200; }
      }
      if (sheetCheck11 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck11.toString(), SHEET_CHECK11_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck11"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK11_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck11", this.sheetCheck11, sheetCheck11));
   }

   /**
    * sheetCheck12
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK12 = "sheetCheck12";
   static int SHEET_CHECK12_UPPER_LIMIT = -1;
   java.lang.String sheetCheck12;
   /**
    * sheetCheck12
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck12() {
      return sheetCheck12;
   }
   /**
    * sheetCheck12
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck12(java.lang.String sheetCheck12) throws wt.util.WTPropertyVetoException {
      sheetCheck12Validate(sheetCheck12);
      this.sheetCheck12 = sheetCheck12;
   }
   void sheetCheck12Validate(java.lang.String sheetCheck12) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK12_UPPER_LIMIT < 1) {
         try { SHEET_CHECK12_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck12").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK12_UPPER_LIMIT = 200; }
      }
      if (sheetCheck12 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck12.toString(), SHEET_CHECK12_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck12"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK12_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck12", this.sheetCheck12, sheetCheck12));
   }

   /**
    * sheetCheck13
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK13 = "sheetCheck13";
   static int SHEET_CHECK13_UPPER_LIMIT = -1;
   java.lang.String sheetCheck13;
   /**
    * sheetCheck13
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck13() {
      return sheetCheck13;
   }
   /**
    * sheetCheck13
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck13(java.lang.String sheetCheck13) throws wt.util.WTPropertyVetoException {
      sheetCheck13Validate(sheetCheck13);
      this.sheetCheck13 = sheetCheck13;
   }
   void sheetCheck13Validate(java.lang.String sheetCheck13) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK13_UPPER_LIMIT < 1) {
         try { SHEET_CHECK13_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck13").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK13_UPPER_LIMIT = 200; }
      }
      if (sheetCheck13 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck13.toString(), SHEET_CHECK13_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck13"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK13_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck13", this.sheetCheck13, sheetCheck13));
   }

   /**
    * sheetCheck14
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK14 = "sheetCheck14";
   static int SHEET_CHECK14_UPPER_LIMIT = -1;
   java.lang.String sheetCheck14;
   /**
    * sheetCheck14
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck14() {
      return sheetCheck14;
   }
   /**
    * sheetCheck14
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck14(java.lang.String sheetCheck14) throws wt.util.WTPropertyVetoException {
      sheetCheck14Validate(sheetCheck14);
      this.sheetCheck14 = sheetCheck14;
   }
   void sheetCheck14Validate(java.lang.String sheetCheck14) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK14_UPPER_LIMIT < 1) {
         try { SHEET_CHECK14_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck14").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK14_UPPER_LIMIT = 200; }
      }
      if (sheetCheck14 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck14.toString(), SHEET_CHECK14_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck14"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK14_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck14", this.sheetCheck14, sheetCheck14));
   }

   /**
    * sheetCheck15
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK15 = "sheetCheck15";
   static int SHEET_CHECK15_UPPER_LIMIT = -1;
   java.lang.String sheetCheck15;
   /**
    * sheetCheck15
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck15() {
      return sheetCheck15;
   }
   /**
    * sheetCheck15
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck15(java.lang.String sheetCheck15) throws wt.util.WTPropertyVetoException {
      sheetCheck15Validate(sheetCheck15);
      this.sheetCheck15 = sheetCheck15;
   }
   void sheetCheck15Validate(java.lang.String sheetCheck15) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK15_UPPER_LIMIT < 1) {
         try { SHEET_CHECK15_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck15").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK15_UPPER_LIMIT = 200; }
      }
      if (sheetCheck15 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck15.toString(), SHEET_CHECK15_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck15"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK15_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck15", this.sheetCheck15, sheetCheck15));
   }

   /**
    * sheetCheck16
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK16 = "sheetCheck16";
   static int SHEET_CHECK16_UPPER_LIMIT = -1;
   java.lang.String sheetCheck16;
   /**
    * sheetCheck16
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck16() {
      return sheetCheck16;
   }
   /**
    * sheetCheck16
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck16(java.lang.String sheetCheck16) throws wt.util.WTPropertyVetoException {
      sheetCheck16Validate(sheetCheck16);
      this.sheetCheck16 = sheetCheck16;
   }
   void sheetCheck16Validate(java.lang.String sheetCheck16) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK16_UPPER_LIMIT < 1) {
         try { SHEET_CHECK16_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck16").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK16_UPPER_LIMIT = 200; }
      }
      if (sheetCheck16 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck16.toString(), SHEET_CHECK16_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck16"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK16_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck16", this.sheetCheck16, sheetCheck16));
   }

   /**
    * sheetCheck17
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK17 = "sheetCheck17";
   static int SHEET_CHECK17_UPPER_LIMIT = -1;
   java.lang.String sheetCheck17;
   /**
    * sheetCheck17
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck17() {
      return sheetCheck17;
   }
   /**
    * sheetCheck17
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck17(java.lang.String sheetCheck17) throws wt.util.WTPropertyVetoException {
      sheetCheck17Validate(sheetCheck17);
      this.sheetCheck17 = sheetCheck17;
   }
   void sheetCheck17Validate(java.lang.String sheetCheck17) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK17_UPPER_LIMIT < 1) {
         try { SHEET_CHECK17_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck17").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK17_UPPER_LIMIT = 200; }
      }
      if (sheetCheck17 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck17.toString(), SHEET_CHECK17_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck17"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK17_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck17", this.sheetCheck17, sheetCheck17));
   }

   /**
    * sheetCheck18
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK18 = "sheetCheck18";
   static int SHEET_CHECK18_UPPER_LIMIT = -1;
   java.lang.String sheetCheck18;
   /**
    * sheetCheck18
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck18() {
      return sheetCheck18;
   }
   /**
    * sheetCheck18
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck18(java.lang.String sheetCheck18) throws wt.util.WTPropertyVetoException {
      sheetCheck18Validate(sheetCheck18);
      this.sheetCheck18 = sheetCheck18;
   }
   void sheetCheck18Validate(java.lang.String sheetCheck18) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK18_UPPER_LIMIT < 1) {
         try { SHEET_CHECK18_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck18").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK18_UPPER_LIMIT = 200; }
      }
      if (sheetCheck18 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck18.toString(), SHEET_CHECK18_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck18"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK18_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck18", this.sheetCheck18, sheetCheck18));
   }

   /**
    * sheetCheck11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK19 = "sheetCheck19";
   static int SHEET_CHECK19_UPPER_LIMIT = -1;
   java.lang.String sheetCheck19;
   /**
    * sheetCheck11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck19() {
      return sheetCheck19;
   }
   /**
    * sheetCheck11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck19(java.lang.String sheetCheck19) throws wt.util.WTPropertyVetoException {
      sheetCheck19Validate(sheetCheck19);
      this.sheetCheck19 = sheetCheck19;
   }
   void sheetCheck19Validate(java.lang.String sheetCheck19) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK19_UPPER_LIMIT < 1) {
         try { SHEET_CHECK19_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck19").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK19_UPPER_LIMIT = 200; }
      }
      if (sheetCheck19 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck19.toString(), SHEET_CHECK19_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck19"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK19_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck19", this.sheetCheck19, sheetCheck19));
   }

   /**
    * sheetCheck20
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_CHECK20 = "sheetCheck20";
   static int SHEET_CHECK20_UPPER_LIMIT = -1;
   java.lang.String sheetCheck20;
   /**
    * sheetCheck20
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetCheck20() {
      return sheetCheck20;
   }
   /**
    * sheetCheck20
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetCheck20(java.lang.String sheetCheck20) throws wt.util.WTPropertyVetoException {
      sheetCheck20Validate(sheetCheck20);
      this.sheetCheck20 = sheetCheck20;
   }
   void sheetCheck20Validate(java.lang.String sheetCheck20) throws wt.util.WTPropertyVetoException {
      if (SHEET_CHECK20_UPPER_LIMIT < 1) {
         try { SHEET_CHECK20_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetCheck20").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_CHECK20_UPPER_LIMIT = 200; }
      }
      if (sheetCheck20 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetCheck20.toString(), SHEET_CHECK20_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetCheck20"), java.lang.String.valueOf(java.lang.Math.min(SHEET_CHECK20_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetCheck20", this.sheetCheck20, sheetCheck20));
   }

   /**
    * sheetResult1
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT1 = "sheetResult1";
   static int SHEET_RESULT1_UPPER_LIMIT = -1;
   java.lang.String sheetResult1;
   /**
    * sheetResult1
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult1() {
      return sheetResult1;
   }
   /**
    * sheetResult1
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult1(java.lang.String sheetResult1) throws wt.util.WTPropertyVetoException {
      sheetResult1Validate(sheetResult1);
      this.sheetResult1 = sheetResult1;
   }
   void sheetResult1Validate(java.lang.String sheetResult1) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT1_UPPER_LIMIT < 1) {
         try { SHEET_RESULT1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT1_UPPER_LIMIT = 200; }
      }
      if (sheetResult1 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult1.toString(), SHEET_RESULT1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult1"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult1", this.sheetResult1, sheetResult1));
   }

   /**
    * sheetResult2
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT2 = "sheetResult2";
   static int SHEET_RESULT2_UPPER_LIMIT = -1;
   java.lang.String sheetResult2;
   /**
    * sheetResult2
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult2() {
      return sheetResult2;
   }
   /**
    * sheetResult2
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult2(java.lang.String sheetResult2) throws wt.util.WTPropertyVetoException {
      sheetResult2Validate(sheetResult2);
      this.sheetResult2 = sheetResult2;
   }
   void sheetResult2Validate(java.lang.String sheetResult2) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT2_UPPER_LIMIT < 1) {
         try { SHEET_RESULT2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT2_UPPER_LIMIT = 200; }
      }
      if (sheetResult2 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult2.toString(), SHEET_RESULT2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult2"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult2", this.sheetResult2, sheetResult2));
   }

   /**
    * sheetResult3
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT3 = "sheetResult3";
   static int SHEET_RESULT3_UPPER_LIMIT = -1;
   java.lang.String sheetResult3;
   /**
    * sheetResult3
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult3() {
      return sheetResult3;
   }
   /**
    * sheetResult3
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult3(java.lang.String sheetResult3) throws wt.util.WTPropertyVetoException {
      sheetResult3Validate(sheetResult3);
      this.sheetResult3 = sheetResult3;
   }
   void sheetResult3Validate(java.lang.String sheetResult3) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT3_UPPER_LIMIT < 1) {
         try { SHEET_RESULT3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT3_UPPER_LIMIT = 200; }
      }
      if (sheetResult3 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult3.toString(), SHEET_RESULT3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult3"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult3", this.sheetResult3, sheetResult3));
   }

   /**
    * sheetResult4
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT4 = "sheetResult4";
   static int SHEET_RESULT4_UPPER_LIMIT = -1;
   java.lang.String sheetResult4;
   /**
    * sheetResult4
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult4() {
      return sheetResult4;
   }
   /**
    * sheetResult4
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult4(java.lang.String sheetResult4) throws wt.util.WTPropertyVetoException {
      sheetResult4Validate(sheetResult4);
      this.sheetResult4 = sheetResult4;
   }
   void sheetResult4Validate(java.lang.String sheetResult4) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT4_UPPER_LIMIT < 1) {
         try { SHEET_RESULT4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT4_UPPER_LIMIT = 200; }
      }
      if (sheetResult4 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult4.toString(), SHEET_RESULT4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult4"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult4", this.sheetResult4, sheetResult4));
   }

   /**
    * sheetResult5
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT5 = "sheetResult5";
   static int SHEET_RESULT5_UPPER_LIMIT = -1;
   java.lang.String sheetResult5;
   /**
    * sheetResult5
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult5() {
      return sheetResult5;
   }
   /**
    * sheetResult5
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult5(java.lang.String sheetResult5) throws wt.util.WTPropertyVetoException {
      sheetResult5Validate(sheetResult5);
      this.sheetResult5 = sheetResult5;
   }
   void sheetResult5Validate(java.lang.String sheetResult5) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT5_UPPER_LIMIT < 1) {
         try { SHEET_RESULT5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT5_UPPER_LIMIT = 200; }
      }
      if (sheetResult5 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult5.toString(), SHEET_RESULT5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult5"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult5", this.sheetResult5, sheetResult5));
   }

   /**
    * sheetResult6
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT6 = "sheetResult6";
   static int SHEET_RESULT6_UPPER_LIMIT = -1;
   java.lang.String sheetResult6;
   /**
    * sheetResult6
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult6() {
      return sheetResult6;
   }
   /**
    * sheetResult6
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult6(java.lang.String sheetResult6) throws wt.util.WTPropertyVetoException {
      sheetResult6Validate(sheetResult6);
      this.sheetResult6 = sheetResult6;
   }
   void sheetResult6Validate(java.lang.String sheetResult6) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT6_UPPER_LIMIT < 1) {
         try { SHEET_RESULT6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT6_UPPER_LIMIT = 200; }
      }
      if (sheetResult6 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult6.toString(), SHEET_RESULT6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult6"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult6", this.sheetResult6, sheetResult6));
   }

   /**
    * sheetResult7
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT7 = "sheetResult7";
   static int SHEET_RESULT7_UPPER_LIMIT = -1;
   java.lang.String sheetResult7;
   /**
    * sheetResult7
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult7() {
      return sheetResult7;
   }
   /**
    * sheetResult7
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult7(java.lang.String sheetResult7) throws wt.util.WTPropertyVetoException {
      sheetResult7Validate(sheetResult7);
      this.sheetResult7 = sheetResult7;
   }
   void sheetResult7Validate(java.lang.String sheetResult7) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT7_UPPER_LIMIT < 1) {
         try { SHEET_RESULT7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT7_UPPER_LIMIT = 200; }
      }
      if (sheetResult7 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult7.toString(), SHEET_RESULT7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult7"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult7", this.sheetResult7, sheetResult7));
   }

   /**
    * sheetResult8
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT8 = "sheetResult8";
   static int SHEET_RESULT8_UPPER_LIMIT = -1;
   java.lang.String sheetResult8;
   /**
    * sheetResult8
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult8() {
      return sheetResult8;
   }
   /**
    * sheetResult8
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult8(java.lang.String sheetResult8) throws wt.util.WTPropertyVetoException {
      sheetResult8Validate(sheetResult8);
      this.sheetResult8 = sheetResult8;
   }
   void sheetResult8Validate(java.lang.String sheetResult8) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT8_UPPER_LIMIT < 1) {
         try { SHEET_RESULT8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT8_UPPER_LIMIT = 200; }
      }
      if (sheetResult8 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult8.toString(), SHEET_RESULT8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult8"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult8", this.sheetResult8, sheetResult8));
   }

   /**
    * sheetResult9
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT9 = "sheetResult9";
   static int SHEET_RESULT9_UPPER_LIMIT = -1;
   java.lang.String sheetResult9;
   /**
    * sheetResult9
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult9() {
      return sheetResult9;
   }
   /**
    * sheetResult9
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult9(java.lang.String sheetResult9) throws wt.util.WTPropertyVetoException {
      sheetResult9Validate(sheetResult9);
      this.sheetResult9 = sheetResult9;
   }
   void sheetResult9Validate(java.lang.String sheetResult9) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT9_UPPER_LIMIT < 1) {
         try { SHEET_RESULT9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT9_UPPER_LIMIT = 200; }
      }
      if (sheetResult9 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult9.toString(), SHEET_RESULT9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult9"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult9", this.sheetResult9, sheetResult9));
   }

   /**
    * sheetResult10
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT10 = "sheetResult10";
   static int SHEET_RESULT10_UPPER_LIMIT = -1;
   java.lang.String sheetResult10;
   /**
    * sheetResult10
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult10() {
      return sheetResult10;
   }
   /**
    * sheetResult10
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult10(java.lang.String sheetResult10) throws wt.util.WTPropertyVetoException {
      sheetResult10Validate(sheetResult10);
      this.sheetResult10 = sheetResult10;
   }
   void sheetResult10Validate(java.lang.String sheetResult10) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT10_UPPER_LIMIT < 1) {
         try { SHEET_RESULT10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT10_UPPER_LIMIT = 200; }
      }
      if (sheetResult10 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult10.toString(), SHEET_RESULT10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult10"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult10", this.sheetResult10, sheetResult10));
   }

   /**
    * sheetResult11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT11 = "sheetResult11";
   static int SHEET_RESULT11_UPPER_LIMIT = -1;
   java.lang.String sheetResult11;
   /**
    * sheetResult11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult11() {
      return sheetResult11;
   }
   /**
    * sheetResult11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult11(java.lang.String sheetResult11) throws wt.util.WTPropertyVetoException {
      sheetResult11Validate(sheetResult11);
      this.sheetResult11 = sheetResult11;
   }
   void sheetResult11Validate(java.lang.String sheetResult11) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT11_UPPER_LIMIT < 1) {
         try { SHEET_RESULT11_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult11").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT11_UPPER_LIMIT = 200; }
      }
      if (sheetResult11 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult11.toString(), SHEET_RESULT11_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult11"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT11_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult11", this.sheetResult11, sheetResult11));
   }

   /**
    * sheetResult12
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT12 = "sheetResult12";
   static int SHEET_RESULT12_UPPER_LIMIT = -1;
   java.lang.String sheetResult12;
   /**
    * sheetResult12
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult12() {
      return sheetResult12;
   }
   /**
    * sheetResult12
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult12(java.lang.String sheetResult12) throws wt.util.WTPropertyVetoException {
      sheetResult12Validate(sheetResult12);
      this.sheetResult12 = sheetResult12;
   }
   void sheetResult12Validate(java.lang.String sheetResult12) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT12_UPPER_LIMIT < 1) {
         try { SHEET_RESULT12_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult12").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT12_UPPER_LIMIT = 200; }
      }
      if (sheetResult12 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult12.toString(), SHEET_RESULT12_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult12"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT12_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult12", this.sheetResult12, sheetResult12));
   }

   /**
    * sheetResult13
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT13 = "sheetResult13";
   static int SHEET_RESULT13_UPPER_LIMIT = -1;
   java.lang.String sheetResult13;
   /**
    * sheetResult13
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult13() {
      return sheetResult13;
   }
   /**
    * sheetResult13
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult13(java.lang.String sheetResult13) throws wt.util.WTPropertyVetoException {
      sheetResult13Validate(sheetResult13);
      this.sheetResult13 = sheetResult13;
   }
   void sheetResult13Validate(java.lang.String sheetResult13) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT13_UPPER_LIMIT < 1) {
         try { SHEET_RESULT13_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult13").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT13_UPPER_LIMIT = 200; }
      }
      if (sheetResult13 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult13.toString(), SHEET_RESULT13_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult13"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT13_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult13", this.sheetResult13, sheetResult13));
   }

   /**
    * sheetResult14
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT14 = "sheetResult14";
   static int SHEET_RESULT14_UPPER_LIMIT = -1;
   java.lang.String sheetResult14;
   /**
    * sheetResult14
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult14() {
      return sheetResult14;
   }
   /**
    * sheetResult14
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult14(java.lang.String sheetResult14) throws wt.util.WTPropertyVetoException {
      sheetResult14Validate(sheetResult14);
      this.sheetResult14 = sheetResult14;
   }
   void sheetResult14Validate(java.lang.String sheetResult14) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT14_UPPER_LIMIT < 1) {
         try { SHEET_RESULT14_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult14").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT14_UPPER_LIMIT = 200; }
      }
      if (sheetResult14 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult14.toString(), SHEET_RESULT14_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult14"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT14_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult14", this.sheetResult14, sheetResult14));
   }

   /**
    * sheetResult15
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT15 = "sheetResult15";
   static int SHEET_RESULT15_UPPER_LIMIT = -1;
   java.lang.String sheetResult15;
   /**
    * sheetResult15
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult15() {
      return sheetResult15;
   }
   /**
    * sheetResult15
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult15(java.lang.String sheetResult15) throws wt.util.WTPropertyVetoException {
      sheetResult15Validate(sheetResult15);
      this.sheetResult15 = sheetResult15;
   }
   void sheetResult15Validate(java.lang.String sheetResult15) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT15_UPPER_LIMIT < 1) {
         try { SHEET_RESULT15_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult15").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT15_UPPER_LIMIT = 200; }
      }
      if (sheetResult15 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult15.toString(), SHEET_RESULT15_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult15"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT15_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult15", this.sheetResult15, sheetResult15));
   }

   /**
    * sheetResult16
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT16 = "sheetResult16";
   static int SHEET_RESULT16_UPPER_LIMIT = -1;
   java.lang.String sheetResult16;
   /**
    * sheetResult16
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult16() {
      return sheetResult16;
   }
   /**
    * sheetResult16
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult16(java.lang.String sheetResult16) throws wt.util.WTPropertyVetoException {
      sheetResult16Validate(sheetResult16);
      this.sheetResult16 = sheetResult16;
   }
   void sheetResult16Validate(java.lang.String sheetResult16) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT16_UPPER_LIMIT < 1) {
         try { SHEET_RESULT16_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult16").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT16_UPPER_LIMIT = 200; }
      }
      if (sheetResult16 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult16.toString(), SHEET_RESULT16_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult16"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT16_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult16", this.sheetResult16, sheetResult16));
   }

   /**
    * sheetResult17
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT17 = "sheetResult17";
   static int SHEET_RESULT17_UPPER_LIMIT = -1;
   java.lang.String sheetResult17;
   /**
    * sheetResult17
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult17() {
      return sheetResult17;
   }
   /**
    * sheetResult17
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult17(java.lang.String sheetResult17) throws wt.util.WTPropertyVetoException {
      sheetResult17Validate(sheetResult17);
      this.sheetResult17 = sheetResult17;
   }
   void sheetResult17Validate(java.lang.String sheetResult17) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT17_UPPER_LIMIT < 1) {
         try { SHEET_RESULT17_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult17").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT17_UPPER_LIMIT = 200; }
      }
      if (sheetResult17 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult17.toString(), SHEET_RESULT17_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult17"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT17_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult17", this.sheetResult17, sheetResult17));
   }

   /**
    * sheetResult18
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT18 = "sheetResult18";
   static int SHEET_RESULT18_UPPER_LIMIT = -1;
   java.lang.String sheetResult18;
   /**
    * sheetResult18
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult18() {
      return sheetResult18;
   }
   /**
    * sheetResult18
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult18(java.lang.String sheetResult18) throws wt.util.WTPropertyVetoException {
      sheetResult18Validate(sheetResult18);
      this.sheetResult18 = sheetResult18;
   }
   void sheetResult18Validate(java.lang.String sheetResult18) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT18_UPPER_LIMIT < 1) {
         try { SHEET_RESULT18_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult18").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT18_UPPER_LIMIT = 200; }
      }
      if (sheetResult18 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult18.toString(), SHEET_RESULT18_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult18"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT18_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult18", this.sheetResult18, sheetResult18));
   }

   /**
    * sheetResult11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT19 = "sheetResult19";
   static int SHEET_RESULT19_UPPER_LIMIT = -1;
   java.lang.String sheetResult19;
   /**
    * sheetResult11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult19() {
      return sheetResult19;
   }
   /**
    * sheetResult11
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult19(java.lang.String sheetResult19) throws wt.util.WTPropertyVetoException {
      sheetResult19Validate(sheetResult19);
      this.sheetResult19 = sheetResult19;
   }
   void sheetResult19Validate(java.lang.String sheetResult19) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT19_UPPER_LIMIT < 1) {
         try { SHEET_RESULT19_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult19").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT19_UPPER_LIMIT = 200; }
      }
      if (sheetResult19 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult19.toString(), SHEET_RESULT19_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult19"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT19_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult19", this.sheetResult19, sheetResult19));
   }

   /**
    * sheetResult20
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String SHEET_RESULT20 = "sheetResult20";
   static int SHEET_RESULT20_UPPER_LIMIT = -1;
   java.lang.String sheetResult20;
   /**
    * sheetResult20
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getSheetResult20() {
      return sheetResult20;
   }
   /**
    * sheetResult20
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setSheetResult20(java.lang.String sheetResult20) throws wt.util.WTPropertyVetoException {
      sheetResult20Validate(sheetResult20);
      this.sheetResult20 = sheetResult20;
   }
   void sheetResult20Validate(java.lang.String sheetResult20) throws wt.util.WTPropertyVetoException {
      if (SHEET_RESULT20_UPPER_LIMIT < 1) {
         try { SHEET_RESULT20_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sheetResult20").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SHEET_RESULT20_UPPER_LIMIT = 200; }
      }
      if (sheetResult20 != null && !wt.fc.PersistenceHelper.checkStoredLength(sheetResult20.toString(), SHEET_RESULT20_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sheetResult20"), java.lang.String.valueOf(java.lang.Math.min(SHEET_RESULT20_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sheetResult20", this.sheetResult20, sheetResult20));
   }

   /**
    * assessSheetOid
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String ASSESS_SHEET_OID = "assessSheetOid";
   static int ASSESS_SHEET_OID_UPPER_LIMIT = -1;
   java.lang.String assessSheetOid;
   /**
    * assessSheetOid
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getAssessSheetOid() {
      return assessSheetOid;
   }
   /**
    * assessSheetOid
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setAssessSheetOid(java.lang.String assessSheetOid) throws wt.util.WTPropertyVetoException {
      assessSheetOidValidate(assessSheetOid);
      this.assessSheetOid = assessSheetOid;
   }
   void assessSheetOidValidate(java.lang.String assessSheetOid) throws wt.util.WTPropertyVetoException {
      if (ASSESS_SHEET_OID_UPPER_LIMIT < 1) {
         try { ASSESS_SHEET_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("assessSheetOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ASSESS_SHEET_OID_UPPER_LIMIT = 200; }
      }
      if (assessSheetOid != null && !wt.fc.PersistenceHelper.checkStoredLength(assessSheetOid.toString(), ASSESS_SHEET_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "assessSheetOid"), java.lang.String.valueOf(java.lang.Math.min(ASSESS_SHEET_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "assessSheetOid", this.assessSheetOid, assessSheetOid));
   }

   /**
    * managerDeptCode
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String MANAGER_DEPT_CODE = "managerDeptCode";
   static int MANAGER_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String managerDeptCode;
   /**
    * managerDeptCode
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getManagerDeptCode() {
      return managerDeptCode;
   }
   /**
    * managerDeptCode
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setManagerDeptCode(java.lang.String managerDeptCode) throws wt.util.WTPropertyVetoException {
      managerDeptCodeValidate(managerDeptCode);
      this.managerDeptCode = managerDeptCode;
   }
   void managerDeptCodeValidate(java.lang.String managerDeptCode) throws wt.util.WTPropertyVetoException {
      if (MANAGER_DEPT_CODE_UPPER_LIMIT < 1) {
         try { MANAGER_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("managerDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGER_DEPT_CODE_UPPER_LIMIT = 4000; }
      }
      if (managerDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(managerDeptCode.toString(), MANAGER_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerDeptCode"), java.lang.String.valueOf(java.lang.Math.min(MANAGER_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "managerDeptCode", this.managerDeptCode, managerDeptCode));
   }

   /**
    * managerDeptName
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String MANAGER_DEPT_NAME = "managerDeptName";
   static int MANAGER_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String managerDeptName;
   /**
    * managerDeptName
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public java.lang.String getManagerDeptName() {
      return managerDeptName;
   }
   /**
    * managerDeptName
    *
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setManagerDeptName(java.lang.String managerDeptName) throws wt.util.WTPropertyVetoException {
      managerDeptNameValidate(managerDeptName);
      this.managerDeptName = managerDeptName;
   }
   void managerDeptNameValidate(java.lang.String managerDeptName) throws wt.util.WTPropertyVetoException {
      if (MANAGER_DEPT_NAME_UPPER_LIMIT < 1) {
         try { MANAGER_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("managerDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MANAGER_DEPT_NAME_UPPER_LIMIT = 4000; }
      }
      if (managerDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(managerDeptName.toString(), MANAGER_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "managerDeptName"), java.lang.String.valueOf(java.lang.Math.min(MANAGER_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "managerDeptName", this.managerDeptName, managerDeptName));
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String REV = "rev";
   int rev;
   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public int getRev() {
      return rev;
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setRev(int rev) throws wt.util.WTPropertyVetoException {
      revValidate(rev);
      this.rev = rev;
   }
   void revValidate(int rev) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String CHECK_ROLE = "check";
   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public ext.ket.project.gate.entity.GateCheckSheet getCheck() {
      return (ext.ket.project.gate.entity.GateCheckSheet) getRoleAObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setCheck(ext.ket.project.gate.entity.GateCheckSheet the_check) throws wt.util.WTPropertyVetoException {
      setRoleAObject((wt.fc.Persistable) the_check);
   }

   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public static final java.lang.String ASSESS_ROLE = "assess";
   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public ext.ket.project.gate.entity.GateAssessResult getAssess() {
      return (ext.ket.project.gate.entity.GateAssessResult) getRoleBObject();
   }
   /**
    * @see ext.ket.project.gate.entity.AssessResultGateCheckLink
    */
   public void setAssess(ext.ket.project.gate.entity.GateAssessResult the_assess) throws wt.util.WTPropertyVetoException {
      setRoleBObject((wt.fc.Persistable) the_assess);
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

   public static final long EXTERNALIZATION_VERSION_UID = 4022222997661002292L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( assessSheetOid );
      output.writeObject( managerDeptCode );
      output.writeObject( managerDeptName );
      output.writeInt( rev );
      output.writeObject( sheetCheck );
      output.writeObject( sheetCheck1 );
      output.writeObject( sheetCheck10 );
      output.writeObject( sheetCheck11 );
      output.writeObject( sheetCheck12 );
      output.writeObject( sheetCheck13 );
      output.writeObject( sheetCheck14 );
      output.writeObject( sheetCheck15 );
      output.writeObject( sheetCheck16 );
      output.writeObject( sheetCheck17 );
      output.writeObject( sheetCheck18 );
      output.writeObject( sheetCheck19 );
      output.writeObject( sheetCheck2 );
      output.writeObject( sheetCheck20 );
      output.writeObject( sheetCheck3 );
      output.writeObject( sheetCheck4 );
      output.writeObject( sheetCheck5 );
      output.writeObject( sheetCheck6 );
      output.writeObject( sheetCheck7 );
      output.writeObject( sheetCheck8 );
      output.writeObject( sheetCheck9 );
      output.writeInt( sheetResult );
      output.writeObject( sheetResult1 );
      output.writeObject( sheetResult10 );
      output.writeObject( sheetResult11 );
      output.writeObject( sheetResult12 );
      output.writeObject( sheetResult13 );
      output.writeObject( sheetResult14 );
      output.writeObject( sheetResult15 );
      output.writeObject( sheetResult16 );
      output.writeObject( sheetResult17 );
      output.writeObject( sheetResult18 );
      output.writeObject( sheetResult19 );
      output.writeObject( sheetResult2 );
      output.writeObject( sheetResult20 );
      output.writeObject( sheetResult3 );
      output.writeObject( sheetResult4 );
      output.writeObject( sheetResult5 );
      output.writeObject( sheetResult6 );
      output.writeObject( sheetResult7 );
      output.writeObject( sheetResult8 );
      output.writeObject( sheetResult9 );
   }

   protected void super_writeExternal_AssessResultGateCheckLink(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.gate.entity.AssessResultGateCheckLink) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_AssessResultGateCheckLink(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "assessSheetOid", assessSheetOid );
      output.setString( "managerDeptCode", managerDeptCode );
      output.setString( "managerDeptName", managerDeptName );
      output.setInt( "rev", rev );
      output.setString( "sheetCheck", sheetCheck );
      output.setString( "sheetCheck1", sheetCheck1 );
      output.setString( "sheetCheck10", sheetCheck10 );
      output.setString( "sheetCheck11", sheetCheck11 );
      output.setString( "sheetCheck12", sheetCheck12 );
      output.setString( "sheetCheck13", sheetCheck13 );
      output.setString( "sheetCheck14", sheetCheck14 );
      output.setString( "sheetCheck15", sheetCheck15 );
      output.setString( "sheetCheck16", sheetCheck16 );
      output.setString( "sheetCheck17", sheetCheck17 );
      output.setString( "sheetCheck18", sheetCheck18 );
      output.setString( "sheetCheck19", sheetCheck19 );
      output.setString( "sheetCheck2", sheetCheck2 );
      output.setString( "sheetCheck20", sheetCheck20 );
      output.setString( "sheetCheck3", sheetCheck3 );
      output.setString( "sheetCheck4", sheetCheck4 );
      output.setString( "sheetCheck5", sheetCheck5 );
      output.setString( "sheetCheck6", sheetCheck6 );
      output.setString( "sheetCheck7", sheetCheck7 );
      output.setString( "sheetCheck8", sheetCheck8 );
      output.setString( "sheetCheck9", sheetCheck9 );
      output.setInt( "sheetResult", sheetResult );
      output.setString( "sheetResult1", sheetResult1 );
      output.setString( "sheetResult10", sheetResult10 );
      output.setString( "sheetResult11", sheetResult11 );
      output.setString( "sheetResult12", sheetResult12 );
      output.setString( "sheetResult13", sheetResult13 );
      output.setString( "sheetResult14", sheetResult14 );
      output.setString( "sheetResult15", sheetResult15 );
      output.setString( "sheetResult16", sheetResult16 );
      output.setString( "sheetResult17", sheetResult17 );
      output.setString( "sheetResult18", sheetResult18 );
      output.setString( "sheetResult19", sheetResult19 );
      output.setString( "sheetResult2", sheetResult2 );
      output.setString( "sheetResult20", sheetResult20 );
      output.setString( "sheetResult3", sheetResult3 );
      output.setString( "sheetResult4", sheetResult4 );
      output.setString( "sheetResult5", sheetResult5 );
      output.setString( "sheetResult6", sheetResult6 );
      output.setString( "sheetResult7", sheetResult7 );
      output.setString( "sheetResult8", sheetResult8 );
      output.setString( "sheetResult9", sheetResult9 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      assessSheetOid = input.getString( "assessSheetOid" );
      managerDeptCode = input.getString( "managerDeptCode" );
      managerDeptName = input.getString( "managerDeptName" );
      rev = input.getInt( "rev" );
      sheetCheck = input.getString( "sheetCheck" );
      sheetCheck1 = input.getString( "sheetCheck1" );
      sheetCheck10 = input.getString( "sheetCheck10" );
      sheetCheck11 = input.getString( "sheetCheck11" );
      sheetCheck12 = input.getString( "sheetCheck12" );
      sheetCheck13 = input.getString( "sheetCheck13" );
      sheetCheck14 = input.getString( "sheetCheck14" );
      sheetCheck15 = input.getString( "sheetCheck15" );
      sheetCheck16 = input.getString( "sheetCheck16" );
      sheetCheck17 = input.getString( "sheetCheck17" );
      sheetCheck18 = input.getString( "sheetCheck18" );
      sheetCheck19 = input.getString( "sheetCheck19" );
      sheetCheck2 = input.getString( "sheetCheck2" );
      sheetCheck20 = input.getString( "sheetCheck20" );
      sheetCheck3 = input.getString( "sheetCheck3" );
      sheetCheck4 = input.getString( "sheetCheck4" );
      sheetCheck5 = input.getString( "sheetCheck5" );
      sheetCheck6 = input.getString( "sheetCheck6" );
      sheetCheck7 = input.getString( "sheetCheck7" );
      sheetCheck8 = input.getString( "sheetCheck8" );
      sheetCheck9 = input.getString( "sheetCheck9" );
      sheetResult = input.getInt( "sheetResult" );
      sheetResult1 = input.getString( "sheetResult1" );
      sheetResult10 = input.getString( "sheetResult10" );
      sheetResult11 = input.getString( "sheetResult11" );
      sheetResult12 = input.getString( "sheetResult12" );
      sheetResult13 = input.getString( "sheetResult13" );
      sheetResult14 = input.getString( "sheetResult14" );
      sheetResult15 = input.getString( "sheetResult15" );
      sheetResult16 = input.getString( "sheetResult16" );
      sheetResult17 = input.getString( "sheetResult17" );
      sheetResult18 = input.getString( "sheetResult18" );
      sheetResult19 = input.getString( "sheetResult19" );
      sheetResult2 = input.getString( "sheetResult2" );
      sheetResult20 = input.getString( "sheetResult20" );
      sheetResult3 = input.getString( "sheetResult3" );
      sheetResult4 = input.getString( "sheetResult4" );
      sheetResult5 = input.getString( "sheetResult5" );
      sheetResult6 = input.getString( "sheetResult6" );
      sheetResult7 = input.getString( "sheetResult7" );
      sheetResult8 = input.getString( "sheetResult8" );
      sheetResult9 = input.getString( "sheetResult9" );
   }

   boolean readVersion4022222997661002292L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      assessSheetOid = (java.lang.String) input.readObject();
      managerDeptCode = (java.lang.String) input.readObject();
      managerDeptName = (java.lang.String) input.readObject();
      rev = input.readInt();
      sheetCheck = (java.lang.String) input.readObject();
      sheetCheck1 = (java.lang.String) input.readObject();
      sheetCheck10 = (java.lang.String) input.readObject();
      sheetCheck11 = (java.lang.String) input.readObject();
      sheetCheck12 = (java.lang.String) input.readObject();
      sheetCheck13 = (java.lang.String) input.readObject();
      sheetCheck14 = (java.lang.String) input.readObject();
      sheetCheck15 = (java.lang.String) input.readObject();
      sheetCheck16 = (java.lang.String) input.readObject();
      sheetCheck17 = (java.lang.String) input.readObject();
      sheetCheck18 = (java.lang.String) input.readObject();
      sheetCheck19 = (java.lang.String) input.readObject();
      sheetCheck2 = (java.lang.String) input.readObject();
      sheetCheck20 = (java.lang.String) input.readObject();
      sheetCheck3 = (java.lang.String) input.readObject();
      sheetCheck4 = (java.lang.String) input.readObject();
      sheetCheck5 = (java.lang.String) input.readObject();
      sheetCheck6 = (java.lang.String) input.readObject();
      sheetCheck7 = (java.lang.String) input.readObject();
      sheetCheck8 = (java.lang.String) input.readObject();
      sheetCheck9 = (java.lang.String) input.readObject();
      sheetResult = input.readInt();
      sheetResult1 = (java.lang.String) input.readObject();
      sheetResult10 = (java.lang.String) input.readObject();
      sheetResult11 = (java.lang.String) input.readObject();
      sheetResult12 = (java.lang.String) input.readObject();
      sheetResult13 = (java.lang.String) input.readObject();
      sheetResult14 = (java.lang.String) input.readObject();
      sheetResult15 = (java.lang.String) input.readObject();
      sheetResult16 = (java.lang.String) input.readObject();
      sheetResult17 = (java.lang.String) input.readObject();
      sheetResult18 = (java.lang.String) input.readObject();
      sheetResult19 = (java.lang.String) input.readObject();
      sheetResult2 = (java.lang.String) input.readObject();
      sheetResult20 = (java.lang.String) input.readObject();
      sheetResult3 = (java.lang.String) input.readObject();
      sheetResult4 = (java.lang.String) input.readObject();
      sheetResult5 = (java.lang.String) input.readObject();
      sheetResult6 = (java.lang.String) input.readObject();
      sheetResult7 = (java.lang.String) input.readObject();
      sheetResult8 = (java.lang.String) input.readObject();
      sheetResult9 = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( AssessResultGateCheckLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4022222997661002292L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_AssessResultGateCheckLink( _AssessResultGateCheckLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
