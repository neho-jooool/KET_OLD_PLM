package wt.part;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _WTPartTypeInfo implements wt.fc.ObjectMappable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "wt.part.partResource";
   static final java.lang.String CLASSNAME = WTPartTypeInfo.class.getName();

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_1 = "ptc_str_1";
   static int PTC_STR_1_UPPER_LIMIT = -1;
   java.lang.String ptc_str_1;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_1() {
      return ptc_str_1;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_1(java.lang.String ptc_str_1) throws wt.util.WTPropertyVetoException {
      ptc_str_1Validate(ptc_str_1);
      this.ptc_str_1 = ptc_str_1;
   }
   void ptc_str_1Validate(java.lang.String ptc_str_1) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_1_UPPER_LIMIT < 1) {
         try { PTC_STR_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_1_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_1.toString(), PTC_STR_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_1"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_1", this.ptc_str_1, ptc_str_1));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_2 = "ptc_str_2";
   static int PTC_STR_2_UPPER_LIMIT = -1;
   java.lang.String ptc_str_2;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_2() {
      return ptc_str_2;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_2(java.lang.String ptc_str_2) throws wt.util.WTPropertyVetoException {
      ptc_str_2Validate(ptc_str_2);
      this.ptc_str_2 = ptc_str_2;
   }
   void ptc_str_2Validate(java.lang.String ptc_str_2) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_2_UPPER_LIMIT < 1) {
         try { PTC_STR_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_2_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_2.toString(), PTC_STR_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_2"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_2", this.ptc_str_2, ptc_str_2));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_3 = "ptc_str_3";
   static int PTC_STR_3_UPPER_LIMIT = -1;
   java.lang.String ptc_str_3;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_3() {
      return ptc_str_3;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_3(java.lang.String ptc_str_3) throws wt.util.WTPropertyVetoException {
      ptc_str_3Validate(ptc_str_3);
      this.ptc_str_3 = ptc_str_3;
   }
   void ptc_str_3Validate(java.lang.String ptc_str_3) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_3_UPPER_LIMIT < 1) {
         try { PTC_STR_3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_3_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_3 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_3.toString(), PTC_STR_3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_3"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_3", this.ptc_str_3, ptc_str_3));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_4 = "ptc_str_4";
   static int PTC_STR_4_UPPER_LIMIT = -1;
   java.lang.String ptc_str_4;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_4() {
      return ptc_str_4;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_4(java.lang.String ptc_str_4) throws wt.util.WTPropertyVetoException {
      ptc_str_4Validate(ptc_str_4);
      this.ptc_str_4 = ptc_str_4;
   }
   void ptc_str_4Validate(java.lang.String ptc_str_4) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_4_UPPER_LIMIT < 1) {
         try { PTC_STR_4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_4_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_4 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_4.toString(), PTC_STR_4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_4"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_4", this.ptc_str_4, ptc_str_4));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_5 = "ptc_str_5";
   static int PTC_STR_5_UPPER_LIMIT = -1;
   java.lang.String ptc_str_5;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_5() {
      return ptc_str_5;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_5(java.lang.String ptc_str_5) throws wt.util.WTPropertyVetoException {
      ptc_str_5Validate(ptc_str_5);
      this.ptc_str_5 = ptc_str_5;
   }
   void ptc_str_5Validate(java.lang.String ptc_str_5) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_5_UPPER_LIMIT < 1) {
         try { PTC_STR_5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_5_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_5 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_5.toString(), PTC_STR_5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_5"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_5", this.ptc_str_5, ptc_str_5));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_6 = "ptc_str_6";
   static int PTC_STR_6_UPPER_LIMIT = -1;
   java.lang.String ptc_str_6;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_6() {
      return ptc_str_6;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_6(java.lang.String ptc_str_6) throws wt.util.WTPropertyVetoException {
      ptc_str_6Validate(ptc_str_6);
      this.ptc_str_6 = ptc_str_6;
   }
   void ptc_str_6Validate(java.lang.String ptc_str_6) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_6_UPPER_LIMIT < 1) {
         try { PTC_STR_6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_6_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_6 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_6.toString(), PTC_STR_6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_6"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_6", this.ptc_str_6, ptc_str_6));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_7 = "ptc_str_7";
   static int PTC_STR_7_UPPER_LIMIT = -1;
   java.lang.String ptc_str_7;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_7() {
      return ptc_str_7;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_7(java.lang.String ptc_str_7) throws wt.util.WTPropertyVetoException {
      ptc_str_7Validate(ptc_str_7);
      this.ptc_str_7 = ptc_str_7;
   }
   void ptc_str_7Validate(java.lang.String ptc_str_7) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_7_UPPER_LIMIT < 1) {
         try { PTC_STR_7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_7_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_7 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_7.toString(), PTC_STR_7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_7"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_7", this.ptc_str_7, ptc_str_7));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_8 = "ptc_str_8";
   static int PTC_STR_8_UPPER_LIMIT = -1;
   java.lang.String ptc_str_8;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_8() {
      return ptc_str_8;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_8(java.lang.String ptc_str_8) throws wt.util.WTPropertyVetoException {
      ptc_str_8Validate(ptc_str_8);
      this.ptc_str_8 = ptc_str_8;
   }
   void ptc_str_8Validate(java.lang.String ptc_str_8) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_8_UPPER_LIMIT < 1) {
         try { PTC_STR_8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_8_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_8 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_8.toString(), PTC_STR_8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_8"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_8", this.ptc_str_8, ptc_str_8));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_9 = "ptc_str_9";
   static int PTC_STR_9_UPPER_LIMIT = -1;
   java.lang.String ptc_str_9;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_9() {
      return ptc_str_9;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_9(java.lang.String ptc_str_9) throws wt.util.WTPropertyVetoException {
      ptc_str_9Validate(ptc_str_9);
      this.ptc_str_9 = ptc_str_9;
   }
   void ptc_str_9Validate(java.lang.String ptc_str_9) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_9_UPPER_LIMIT < 1) {
         try { PTC_STR_9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_9_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_9 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_9.toString(), PTC_STR_9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_9"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_9", this.ptc_str_9, ptc_str_9));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_10 = "ptc_str_10";
   static int PTC_STR_10_UPPER_LIMIT = -1;
   java.lang.String ptc_str_10;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_10() {
      return ptc_str_10;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_10(java.lang.String ptc_str_10) throws wt.util.WTPropertyVetoException {
      ptc_str_10Validate(ptc_str_10);
      this.ptc_str_10 = ptc_str_10;
   }
   void ptc_str_10Validate(java.lang.String ptc_str_10) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_10_UPPER_LIMIT < 1) {
         try { PTC_STR_10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_10_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_10 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_10.toString(), PTC_STR_10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_10"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_10", this.ptc_str_10, ptc_str_10));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_11 = "ptc_str_11";
   static int PTC_STR_11_UPPER_LIMIT = -1;
   java.lang.String ptc_str_11;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_11() {
      return ptc_str_11;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_11(java.lang.String ptc_str_11) throws wt.util.WTPropertyVetoException {
      ptc_str_11Validate(ptc_str_11);
      this.ptc_str_11 = ptc_str_11;
   }
   void ptc_str_11Validate(java.lang.String ptc_str_11) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_11_UPPER_LIMIT < 1) {
         try { PTC_STR_11_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_11").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_11_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_11 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_11.toString(), PTC_STR_11_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_11"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_11_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_11", this.ptc_str_11, ptc_str_11));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_12 = "ptc_str_12";
   static int PTC_STR_12_UPPER_LIMIT = -1;
   java.lang.String ptc_str_12;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_12() {
      return ptc_str_12;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_12(java.lang.String ptc_str_12) throws wt.util.WTPropertyVetoException {
      ptc_str_12Validate(ptc_str_12);
      this.ptc_str_12 = ptc_str_12;
   }
   void ptc_str_12Validate(java.lang.String ptc_str_12) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_12_UPPER_LIMIT < 1) {
         try { PTC_STR_12_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_12").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_12_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_12 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_12.toString(), PTC_STR_12_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_12"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_12_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_12", this.ptc_str_12, ptc_str_12));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_13 = "ptc_str_13";
   static int PTC_STR_13_UPPER_LIMIT = -1;
   java.lang.String ptc_str_13;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_13() {
      return ptc_str_13;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_13(java.lang.String ptc_str_13) throws wt.util.WTPropertyVetoException {
      ptc_str_13Validate(ptc_str_13);
      this.ptc_str_13 = ptc_str_13;
   }
   void ptc_str_13Validate(java.lang.String ptc_str_13) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_13_UPPER_LIMIT < 1) {
         try { PTC_STR_13_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_13").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_13_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_13 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_13.toString(), PTC_STR_13_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_13"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_13_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_13", this.ptc_str_13, ptc_str_13));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_14 = "ptc_str_14";
   static int PTC_STR_14_UPPER_LIMIT = -1;
   java.lang.String ptc_str_14;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_14() {
      return ptc_str_14;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_14(java.lang.String ptc_str_14) throws wt.util.WTPropertyVetoException {
      ptc_str_14Validate(ptc_str_14);
      this.ptc_str_14 = ptc_str_14;
   }
   void ptc_str_14Validate(java.lang.String ptc_str_14) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_14_UPPER_LIMIT < 1) {
         try { PTC_STR_14_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_14").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_14_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_14 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_14.toString(), PTC_STR_14_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_14"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_14_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_14", this.ptc_str_14, ptc_str_14));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_15 = "ptc_str_15";
   static int PTC_STR_15_UPPER_LIMIT = -1;
   java.lang.String ptc_str_15;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_15() {
      return ptc_str_15;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_15(java.lang.String ptc_str_15) throws wt.util.WTPropertyVetoException {
      ptc_str_15Validate(ptc_str_15);
      this.ptc_str_15 = ptc_str_15;
   }
   void ptc_str_15Validate(java.lang.String ptc_str_15) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_15_UPPER_LIMIT < 1) {
         try { PTC_STR_15_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_15").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_15_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_15 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_15.toString(), PTC_STR_15_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_15"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_15_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_15", this.ptc_str_15, ptc_str_15));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_16 = "ptc_str_16";
   static int PTC_STR_16_UPPER_LIMIT = -1;
   java.lang.String ptc_str_16;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_16() {
      return ptc_str_16;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_16(java.lang.String ptc_str_16) throws wt.util.WTPropertyVetoException {
      ptc_str_16Validate(ptc_str_16);
      this.ptc_str_16 = ptc_str_16;
   }
   void ptc_str_16Validate(java.lang.String ptc_str_16) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_16_UPPER_LIMIT < 1) {
         try { PTC_STR_16_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_16").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_16_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_16 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_16.toString(), PTC_STR_16_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_16"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_16_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_16", this.ptc_str_16, ptc_str_16));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_17 = "ptc_str_17";
   static int PTC_STR_17_UPPER_LIMIT = -1;
   java.lang.String ptc_str_17;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_17() {
      return ptc_str_17;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_17(java.lang.String ptc_str_17) throws wt.util.WTPropertyVetoException {
      ptc_str_17Validate(ptc_str_17);
      this.ptc_str_17 = ptc_str_17;
   }
   void ptc_str_17Validate(java.lang.String ptc_str_17) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_17_UPPER_LIMIT < 1) {
         try { PTC_STR_17_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_17").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_17_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_17 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_17.toString(), PTC_STR_17_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_17"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_17_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_17", this.ptc_str_17, ptc_str_17));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_18 = "ptc_str_18";
   static int PTC_STR_18_UPPER_LIMIT = -1;
   java.lang.String ptc_str_18;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_18() {
      return ptc_str_18;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_18(java.lang.String ptc_str_18) throws wt.util.WTPropertyVetoException {
      ptc_str_18Validate(ptc_str_18);
      this.ptc_str_18 = ptc_str_18;
   }
   void ptc_str_18Validate(java.lang.String ptc_str_18) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_18_UPPER_LIMIT < 1) {
         try { PTC_STR_18_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_18").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_18_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_18 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_18.toString(), PTC_STR_18_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_18"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_18_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_18", this.ptc_str_18, ptc_str_18));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_19 = "ptc_str_19";
   static int PTC_STR_19_UPPER_LIMIT = -1;
   java.lang.String ptc_str_19;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_19() {
      return ptc_str_19;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_19(java.lang.String ptc_str_19) throws wt.util.WTPropertyVetoException {
      ptc_str_19Validate(ptc_str_19);
      this.ptc_str_19 = ptc_str_19;
   }
   void ptc_str_19Validate(java.lang.String ptc_str_19) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_19_UPPER_LIMIT < 1) {
         try { PTC_STR_19_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_19").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_19_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_19 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_19.toString(), PTC_STR_19_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_19"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_19_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_19", this.ptc_str_19, ptc_str_19));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_20 = "ptc_str_20";
   static int PTC_STR_20_UPPER_LIMIT = -1;
   java.lang.String ptc_str_20;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_20() {
      return ptc_str_20;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_20(java.lang.String ptc_str_20) throws wt.util.WTPropertyVetoException {
      ptc_str_20Validate(ptc_str_20);
      this.ptc_str_20 = ptc_str_20;
   }
   void ptc_str_20Validate(java.lang.String ptc_str_20) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_20_UPPER_LIMIT < 1) {
         try { PTC_STR_20_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_20").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_20_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_20 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_20.toString(), PTC_STR_20_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_20"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_20_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_20", this.ptc_str_20, ptc_str_20));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_21 = "ptc_str_21";
   static int PTC_STR_21_UPPER_LIMIT = -1;
   java.lang.String ptc_str_21;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_21() {
      return ptc_str_21;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_21(java.lang.String ptc_str_21) throws wt.util.WTPropertyVetoException {
      ptc_str_21Validate(ptc_str_21);
      this.ptc_str_21 = ptc_str_21;
   }
   void ptc_str_21Validate(java.lang.String ptc_str_21) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_21_UPPER_LIMIT < 1) {
         try { PTC_STR_21_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_21").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_21_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_21 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_21.toString(), PTC_STR_21_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_21"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_21_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_21", this.ptc_str_21, ptc_str_21));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_22 = "ptc_str_22";
   static int PTC_STR_22_UPPER_LIMIT = -1;
   java.lang.String ptc_str_22;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_22() {
      return ptc_str_22;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_22(java.lang.String ptc_str_22) throws wt.util.WTPropertyVetoException {
      ptc_str_22Validate(ptc_str_22);
      this.ptc_str_22 = ptc_str_22;
   }
   void ptc_str_22Validate(java.lang.String ptc_str_22) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_22_UPPER_LIMIT < 1) {
         try { PTC_STR_22_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_22").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_22_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_22 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_22.toString(), PTC_STR_22_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_22"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_22_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_22", this.ptc_str_22, ptc_str_22));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_23 = "ptc_str_23";
   static int PTC_STR_23_UPPER_LIMIT = -1;
   java.lang.String ptc_str_23;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_23() {
      return ptc_str_23;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_23(java.lang.String ptc_str_23) throws wt.util.WTPropertyVetoException {
      ptc_str_23Validate(ptc_str_23);
      this.ptc_str_23 = ptc_str_23;
   }
   void ptc_str_23Validate(java.lang.String ptc_str_23) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_23_UPPER_LIMIT < 1) {
         try { PTC_STR_23_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_23").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_23_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_23 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_23.toString(), PTC_STR_23_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_23"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_23_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_23", this.ptc_str_23, ptc_str_23));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_24 = "ptc_str_24";
   static int PTC_STR_24_UPPER_LIMIT = -1;
   java.lang.String ptc_str_24;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_24() {
      return ptc_str_24;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_24(java.lang.String ptc_str_24) throws wt.util.WTPropertyVetoException {
      ptc_str_24Validate(ptc_str_24);
      this.ptc_str_24 = ptc_str_24;
   }
   void ptc_str_24Validate(java.lang.String ptc_str_24) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_24_UPPER_LIMIT < 1) {
         try { PTC_STR_24_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_24").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_24_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_24 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_24.toString(), PTC_STR_24_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_24"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_24_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_24", this.ptc_str_24, ptc_str_24));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_25 = "ptc_str_25";
   static int PTC_STR_25_UPPER_LIMIT = -1;
   java.lang.String ptc_str_25;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_25() {
      return ptc_str_25;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_25(java.lang.String ptc_str_25) throws wt.util.WTPropertyVetoException {
      ptc_str_25Validate(ptc_str_25);
      this.ptc_str_25 = ptc_str_25;
   }
   void ptc_str_25Validate(java.lang.String ptc_str_25) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_25_UPPER_LIMIT < 1) {
         try { PTC_STR_25_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_25").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_25_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_25 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_25.toString(), PTC_STR_25_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_25"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_25_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_25", this.ptc_str_25, ptc_str_25));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_26 = "ptc_str_26";
   static int PTC_STR_26_UPPER_LIMIT = -1;
   java.lang.String ptc_str_26;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_26() {
      return ptc_str_26;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_26(java.lang.String ptc_str_26) throws wt.util.WTPropertyVetoException {
      ptc_str_26Validate(ptc_str_26);
      this.ptc_str_26 = ptc_str_26;
   }
   void ptc_str_26Validate(java.lang.String ptc_str_26) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_26_UPPER_LIMIT < 1) {
         try { PTC_STR_26_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_26").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_26_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_26 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_26.toString(), PTC_STR_26_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_26"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_26_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_26", this.ptc_str_26, ptc_str_26));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_27 = "ptc_str_27";
   static int PTC_STR_27_UPPER_LIMIT = -1;
   java.lang.String ptc_str_27;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_27() {
      return ptc_str_27;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_27(java.lang.String ptc_str_27) throws wt.util.WTPropertyVetoException {
      ptc_str_27Validate(ptc_str_27);
      this.ptc_str_27 = ptc_str_27;
   }
   void ptc_str_27Validate(java.lang.String ptc_str_27) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_27_UPPER_LIMIT < 1) {
         try { PTC_STR_27_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_27").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_27_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_27 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_27.toString(), PTC_STR_27_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_27"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_27_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_27", this.ptc_str_27, ptc_str_27));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_28 = "ptc_str_28";
   static int PTC_STR_28_UPPER_LIMIT = -1;
   java.lang.String ptc_str_28;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_28() {
      return ptc_str_28;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_28(java.lang.String ptc_str_28) throws wt.util.WTPropertyVetoException {
      ptc_str_28Validate(ptc_str_28);
      this.ptc_str_28 = ptc_str_28;
   }
   void ptc_str_28Validate(java.lang.String ptc_str_28) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_28_UPPER_LIMIT < 1) {
         try { PTC_STR_28_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_28").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_28_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_28 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_28.toString(), PTC_STR_28_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_28"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_28_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_28", this.ptc_str_28, ptc_str_28));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_29 = "ptc_str_29";
   static int PTC_STR_29_UPPER_LIMIT = -1;
   java.lang.String ptc_str_29;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_29() {
      return ptc_str_29;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_29(java.lang.String ptc_str_29) throws wt.util.WTPropertyVetoException {
      ptc_str_29Validate(ptc_str_29);
      this.ptc_str_29 = ptc_str_29;
   }
   void ptc_str_29Validate(java.lang.String ptc_str_29) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_29_UPPER_LIMIT < 1) {
         try { PTC_STR_29_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_29").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_29_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_29 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_29.toString(), PTC_STR_29_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_29"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_29_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_29", this.ptc_str_29, ptc_str_29));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_30 = "ptc_str_30";
   static int PTC_STR_30_UPPER_LIMIT = -1;
   java.lang.String ptc_str_30;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_30() {
      return ptc_str_30;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_30(java.lang.String ptc_str_30) throws wt.util.WTPropertyVetoException {
      ptc_str_30Validate(ptc_str_30);
      this.ptc_str_30 = ptc_str_30;
   }
   void ptc_str_30Validate(java.lang.String ptc_str_30) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_30_UPPER_LIMIT < 1) {
         try { PTC_STR_30_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_30").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_30_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_30 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_30.toString(), PTC_STR_30_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_30"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_30_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_30", this.ptc_str_30, ptc_str_30));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_31 = "ptc_str_31";
   static int PTC_STR_31_UPPER_LIMIT = -1;
   java.lang.String ptc_str_31;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_31() {
      return ptc_str_31;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_31(java.lang.String ptc_str_31) throws wt.util.WTPropertyVetoException {
      ptc_str_31Validate(ptc_str_31);
      this.ptc_str_31 = ptc_str_31;
   }
   void ptc_str_31Validate(java.lang.String ptc_str_31) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_31_UPPER_LIMIT < 1) {
         try { PTC_STR_31_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_31").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_31_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_31 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_31.toString(), PTC_STR_31_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_31"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_31_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_31", this.ptc_str_31, ptc_str_31));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_32 = "ptc_str_32";
   static int PTC_STR_32_UPPER_LIMIT = -1;
   java.lang.String ptc_str_32;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_32() {
      return ptc_str_32;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_32(java.lang.String ptc_str_32) throws wt.util.WTPropertyVetoException {
      ptc_str_32Validate(ptc_str_32);
      this.ptc_str_32 = ptc_str_32;
   }
   void ptc_str_32Validate(java.lang.String ptc_str_32) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_32_UPPER_LIMIT < 1) {
         try { PTC_STR_32_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_32").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_32_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_32 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_32.toString(), PTC_STR_32_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_32"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_32_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_32", this.ptc_str_32, ptc_str_32));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_33 = "ptc_str_33";
   static int PTC_STR_33_UPPER_LIMIT = -1;
   java.lang.String ptc_str_33;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_33() {
      return ptc_str_33;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_33(java.lang.String ptc_str_33) throws wt.util.WTPropertyVetoException {
      ptc_str_33Validate(ptc_str_33);
      this.ptc_str_33 = ptc_str_33;
   }
   void ptc_str_33Validate(java.lang.String ptc_str_33) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_33_UPPER_LIMIT < 1) {
         try { PTC_STR_33_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_33").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_33_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_33 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_33.toString(), PTC_STR_33_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_33"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_33_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_33", this.ptc_str_33, ptc_str_33));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_34 = "ptc_str_34";
   static int PTC_STR_34_UPPER_LIMIT = -1;
   java.lang.String ptc_str_34;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_34() {
      return ptc_str_34;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_34(java.lang.String ptc_str_34) throws wt.util.WTPropertyVetoException {
      ptc_str_34Validate(ptc_str_34);
      this.ptc_str_34 = ptc_str_34;
   }
   void ptc_str_34Validate(java.lang.String ptc_str_34) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_34_UPPER_LIMIT < 1) {
         try { PTC_STR_34_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_34").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_34_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_34 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_34.toString(), PTC_STR_34_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_34"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_34_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_34", this.ptc_str_34, ptc_str_34));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_35 = "ptc_str_35";
   static int PTC_STR_35_UPPER_LIMIT = -1;
   java.lang.String ptc_str_35;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_35() {
      return ptc_str_35;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_35(java.lang.String ptc_str_35) throws wt.util.WTPropertyVetoException {
      ptc_str_35Validate(ptc_str_35);
      this.ptc_str_35 = ptc_str_35;
   }
   void ptc_str_35Validate(java.lang.String ptc_str_35) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_35_UPPER_LIMIT < 1) {
         try { PTC_STR_35_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_35").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_35_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_35 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_35.toString(), PTC_STR_35_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_35"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_35_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_35", this.ptc_str_35, ptc_str_35));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_36 = "ptc_str_36";
   static int PTC_STR_36_UPPER_LIMIT = -1;
   java.lang.String ptc_str_36;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_36() {
      return ptc_str_36;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_36(java.lang.String ptc_str_36) throws wt.util.WTPropertyVetoException {
      ptc_str_36Validate(ptc_str_36);
      this.ptc_str_36 = ptc_str_36;
   }
   void ptc_str_36Validate(java.lang.String ptc_str_36) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_36_UPPER_LIMIT < 1) {
         try { PTC_STR_36_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_36").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_36_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_36 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_36.toString(), PTC_STR_36_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_36"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_36_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_36", this.ptc_str_36, ptc_str_36));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_37 = "ptc_str_37";
   static int PTC_STR_37_UPPER_LIMIT = -1;
   java.lang.String ptc_str_37;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_37() {
      return ptc_str_37;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_37(java.lang.String ptc_str_37) throws wt.util.WTPropertyVetoException {
      ptc_str_37Validate(ptc_str_37);
      this.ptc_str_37 = ptc_str_37;
   }
   void ptc_str_37Validate(java.lang.String ptc_str_37) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_37_UPPER_LIMIT < 1) {
         try { PTC_STR_37_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_37").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_37_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_37 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_37.toString(), PTC_STR_37_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_37"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_37_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_37", this.ptc_str_37, ptc_str_37));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_38 = "ptc_str_38";
   static int PTC_STR_38_UPPER_LIMIT = -1;
   java.lang.String ptc_str_38;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_38() {
      return ptc_str_38;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_38(java.lang.String ptc_str_38) throws wt.util.WTPropertyVetoException {
      ptc_str_38Validate(ptc_str_38);
      this.ptc_str_38 = ptc_str_38;
   }
   void ptc_str_38Validate(java.lang.String ptc_str_38) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_38_UPPER_LIMIT < 1) {
         try { PTC_STR_38_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_38").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_38_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_38 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_38.toString(), PTC_STR_38_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_38"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_38_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_38", this.ptc_str_38, ptc_str_38));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_39 = "ptc_str_39";
   static int PTC_STR_39_UPPER_LIMIT = -1;
   java.lang.String ptc_str_39;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_39() {
      return ptc_str_39;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_39(java.lang.String ptc_str_39) throws wt.util.WTPropertyVetoException {
      ptc_str_39Validate(ptc_str_39);
      this.ptc_str_39 = ptc_str_39;
   }
   void ptc_str_39Validate(java.lang.String ptc_str_39) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_39_UPPER_LIMIT < 1) {
         try { PTC_STR_39_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_39").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_39_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_39 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_39.toString(), PTC_STR_39_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_39"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_39_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_39", this.ptc_str_39, ptc_str_39));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_40 = "ptc_str_40";
   static int PTC_STR_40_UPPER_LIMIT = -1;
   java.lang.String ptc_str_40;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_40() {
      return ptc_str_40;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_40(java.lang.String ptc_str_40) throws wt.util.WTPropertyVetoException {
      ptc_str_40Validate(ptc_str_40);
      this.ptc_str_40 = ptc_str_40;
   }
   void ptc_str_40Validate(java.lang.String ptc_str_40) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_40_UPPER_LIMIT < 1) {
         try { PTC_STR_40_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_40").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_40_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_40 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_40.toString(), PTC_STR_40_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_40"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_40_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_40", this.ptc_str_40, ptc_str_40));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_41 = "ptc_str_41";
   static int PTC_STR_41_UPPER_LIMIT = -1;
   java.lang.String ptc_str_41;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_41() {
      return ptc_str_41;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_41(java.lang.String ptc_str_41) throws wt.util.WTPropertyVetoException {
      ptc_str_41Validate(ptc_str_41);
      this.ptc_str_41 = ptc_str_41;
   }
   void ptc_str_41Validate(java.lang.String ptc_str_41) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_41_UPPER_LIMIT < 1) {
         try { PTC_STR_41_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_41").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_41_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_41 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_41.toString(), PTC_STR_41_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_41"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_41_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_41", this.ptc_str_41, ptc_str_41));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_42 = "ptc_str_42";
   static int PTC_STR_42_UPPER_LIMIT = -1;
   java.lang.String ptc_str_42;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_42() {
      return ptc_str_42;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_42(java.lang.String ptc_str_42) throws wt.util.WTPropertyVetoException {
      ptc_str_42Validate(ptc_str_42);
      this.ptc_str_42 = ptc_str_42;
   }
   void ptc_str_42Validate(java.lang.String ptc_str_42) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_42_UPPER_LIMIT < 1) {
         try { PTC_STR_42_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_42").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_42_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_42 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_42.toString(), PTC_STR_42_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_42"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_42_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_42", this.ptc_str_42, ptc_str_42));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_43 = "ptc_str_43";
   static int PTC_STR_43_UPPER_LIMIT = -1;
   java.lang.String ptc_str_43;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_43() {
      return ptc_str_43;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_43(java.lang.String ptc_str_43) throws wt.util.WTPropertyVetoException {
      ptc_str_43Validate(ptc_str_43);
      this.ptc_str_43 = ptc_str_43;
   }
   void ptc_str_43Validate(java.lang.String ptc_str_43) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_43_UPPER_LIMIT < 1) {
         try { PTC_STR_43_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_43").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_43_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_43 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_43.toString(), PTC_STR_43_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_43"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_43_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_43", this.ptc_str_43, ptc_str_43));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_44 = "ptc_str_44";
   static int PTC_STR_44_UPPER_LIMIT = -1;
   java.lang.String ptc_str_44;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_44() {
      return ptc_str_44;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_44(java.lang.String ptc_str_44) throws wt.util.WTPropertyVetoException {
      ptc_str_44Validate(ptc_str_44);
      this.ptc_str_44 = ptc_str_44;
   }
   void ptc_str_44Validate(java.lang.String ptc_str_44) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_44_UPPER_LIMIT < 1) {
         try { PTC_STR_44_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_44").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_44_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_44 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_44.toString(), PTC_STR_44_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_44"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_44_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_44", this.ptc_str_44, ptc_str_44));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_45 = "ptc_str_45";
   static int PTC_STR_45_UPPER_LIMIT = -1;
   java.lang.String ptc_str_45;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_45() {
      return ptc_str_45;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_45(java.lang.String ptc_str_45) throws wt.util.WTPropertyVetoException {
      ptc_str_45Validate(ptc_str_45);
      this.ptc_str_45 = ptc_str_45;
   }
   void ptc_str_45Validate(java.lang.String ptc_str_45) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_45_UPPER_LIMIT < 1) {
         try { PTC_STR_45_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_45").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_45_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_45 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_45.toString(), PTC_STR_45_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_45"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_45_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_45", this.ptc_str_45, ptc_str_45));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_46 = "ptc_str_46";
   static int PTC_STR_46_UPPER_LIMIT = -1;
   java.lang.String ptc_str_46;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_46() {
      return ptc_str_46;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_46(java.lang.String ptc_str_46) throws wt.util.WTPropertyVetoException {
      ptc_str_46Validate(ptc_str_46);
      this.ptc_str_46 = ptc_str_46;
   }
   void ptc_str_46Validate(java.lang.String ptc_str_46) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_46_UPPER_LIMIT < 1) {
         try { PTC_STR_46_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_46").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_46_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_46 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_46.toString(), PTC_STR_46_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_46"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_46_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_46", this.ptc_str_46, ptc_str_46));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_47 = "ptc_str_47";
   static int PTC_STR_47_UPPER_LIMIT = -1;
   java.lang.String ptc_str_47;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_47() {
      return ptc_str_47;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_47(java.lang.String ptc_str_47) throws wt.util.WTPropertyVetoException {
      ptc_str_47Validate(ptc_str_47);
      this.ptc_str_47 = ptc_str_47;
   }
   void ptc_str_47Validate(java.lang.String ptc_str_47) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_47_UPPER_LIMIT < 1) {
         try { PTC_STR_47_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_47").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_47_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_47 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_47.toString(), PTC_STR_47_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_47"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_47_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_47", this.ptc_str_47, ptc_str_47));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_48 = "ptc_str_48";
   static int PTC_STR_48_UPPER_LIMIT = -1;
   java.lang.String ptc_str_48;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_48() {
      return ptc_str_48;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_48(java.lang.String ptc_str_48) throws wt.util.WTPropertyVetoException {
      ptc_str_48Validate(ptc_str_48);
      this.ptc_str_48 = ptc_str_48;
   }
   void ptc_str_48Validate(java.lang.String ptc_str_48) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_48_UPPER_LIMIT < 1) {
         try { PTC_STR_48_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_48").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_48_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_48 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_48.toString(), PTC_STR_48_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_48"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_48_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_48", this.ptc_str_48, ptc_str_48));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_49 = "ptc_str_49";
   static int PTC_STR_49_UPPER_LIMIT = -1;
   java.lang.String ptc_str_49;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_49() {
      return ptc_str_49;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_49(java.lang.String ptc_str_49) throws wt.util.WTPropertyVetoException {
      ptc_str_49Validate(ptc_str_49);
      this.ptc_str_49 = ptc_str_49;
   }
   void ptc_str_49Validate(java.lang.String ptc_str_49) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_49_UPPER_LIMIT < 1) {
         try { PTC_STR_49_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_49").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_49_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_49 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_49.toString(), PTC_STR_49_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_49"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_49_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_49", this.ptc_str_49, ptc_str_49));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_50 = "ptc_str_50";
   static int PTC_STR_50_UPPER_LIMIT = -1;
   java.lang.String ptc_str_50;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_50() {
      return ptc_str_50;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_50(java.lang.String ptc_str_50) throws wt.util.WTPropertyVetoException {
      ptc_str_50Validate(ptc_str_50);
      this.ptc_str_50 = ptc_str_50;
   }
   void ptc_str_50Validate(java.lang.String ptc_str_50) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_50_UPPER_LIMIT < 1) {
         try { PTC_STR_50_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_50").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_50_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_50 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_50.toString(), PTC_STR_50_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_50"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_50_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_50", this.ptc_str_50, ptc_str_50));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_51 = "ptc_str_51";
   static int PTC_STR_51_UPPER_LIMIT = -1;
   java.lang.String ptc_str_51;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_51() {
      return ptc_str_51;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_51(java.lang.String ptc_str_51) throws wt.util.WTPropertyVetoException {
      ptc_str_51Validate(ptc_str_51);
      this.ptc_str_51 = ptc_str_51;
   }
   void ptc_str_51Validate(java.lang.String ptc_str_51) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_51_UPPER_LIMIT < 1) {
         try { PTC_STR_51_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_51").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_51_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_51 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_51.toString(), PTC_STR_51_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_51"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_51_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_51", this.ptc_str_51, ptc_str_51));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_52 = "ptc_str_52";
   static int PTC_STR_52_UPPER_LIMIT = -1;
   java.lang.String ptc_str_52;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_52() {
      return ptc_str_52;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_52(java.lang.String ptc_str_52) throws wt.util.WTPropertyVetoException {
      ptc_str_52Validate(ptc_str_52);
      this.ptc_str_52 = ptc_str_52;
   }
   void ptc_str_52Validate(java.lang.String ptc_str_52) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_52_UPPER_LIMIT < 1) {
         try { PTC_STR_52_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_52").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_52_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_52 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_52.toString(), PTC_STR_52_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_52"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_52_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_52", this.ptc_str_52, ptc_str_52));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_53 = "ptc_str_53";
   static int PTC_STR_53_UPPER_LIMIT = -1;
   java.lang.String ptc_str_53;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_53() {
      return ptc_str_53;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_53(java.lang.String ptc_str_53) throws wt.util.WTPropertyVetoException {
      ptc_str_53Validate(ptc_str_53);
      this.ptc_str_53 = ptc_str_53;
   }
   void ptc_str_53Validate(java.lang.String ptc_str_53) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_53_UPPER_LIMIT < 1) {
         try { PTC_STR_53_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_53").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_53_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_53 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_53.toString(), PTC_STR_53_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_53"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_53_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_53", this.ptc_str_53, ptc_str_53));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_54 = "ptc_str_54";
   static int PTC_STR_54_UPPER_LIMIT = -1;
   java.lang.String ptc_str_54;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_54() {
      return ptc_str_54;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_54(java.lang.String ptc_str_54) throws wt.util.WTPropertyVetoException {
      ptc_str_54Validate(ptc_str_54);
      this.ptc_str_54 = ptc_str_54;
   }
   void ptc_str_54Validate(java.lang.String ptc_str_54) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_54_UPPER_LIMIT < 1) {
         try { PTC_STR_54_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_54").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_54_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_54 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_54.toString(), PTC_STR_54_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_54"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_54_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_54", this.ptc_str_54, ptc_str_54));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_55 = "ptc_str_55";
   static int PTC_STR_55_UPPER_LIMIT = -1;
   java.lang.String ptc_str_55;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_55() {
      return ptc_str_55;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_55(java.lang.String ptc_str_55) throws wt.util.WTPropertyVetoException {
      ptc_str_55Validate(ptc_str_55);
      this.ptc_str_55 = ptc_str_55;
   }
   void ptc_str_55Validate(java.lang.String ptc_str_55) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_55_UPPER_LIMIT < 1) {
         try { PTC_STR_55_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_55").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_55_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_55 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_55.toString(), PTC_STR_55_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_55"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_55_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_55", this.ptc_str_55, ptc_str_55));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_56 = "ptc_str_56";
   static int PTC_STR_56_UPPER_LIMIT = -1;
   java.lang.String ptc_str_56;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_56() {
      return ptc_str_56;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_56(java.lang.String ptc_str_56) throws wt.util.WTPropertyVetoException {
      ptc_str_56Validate(ptc_str_56);
      this.ptc_str_56 = ptc_str_56;
   }
   void ptc_str_56Validate(java.lang.String ptc_str_56) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_56_UPPER_LIMIT < 1) {
         try { PTC_STR_56_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_56").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_56_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_56 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_56.toString(), PTC_STR_56_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_56"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_56_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_56", this.ptc_str_56, ptc_str_56));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_57 = "ptc_str_57";
   static int PTC_STR_57_UPPER_LIMIT = -1;
   java.lang.String ptc_str_57;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_57() {
      return ptc_str_57;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_57(java.lang.String ptc_str_57) throws wt.util.WTPropertyVetoException {
      ptc_str_57Validate(ptc_str_57);
      this.ptc_str_57 = ptc_str_57;
   }
   void ptc_str_57Validate(java.lang.String ptc_str_57) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_57_UPPER_LIMIT < 1) {
         try { PTC_STR_57_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_57").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_57_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_57 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_57.toString(), PTC_STR_57_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_57"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_57_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_57", this.ptc_str_57, ptc_str_57));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_58 = "ptc_str_58";
   static int PTC_STR_58_UPPER_LIMIT = -1;
   java.lang.String ptc_str_58;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_58() {
      return ptc_str_58;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_58(java.lang.String ptc_str_58) throws wt.util.WTPropertyVetoException {
      ptc_str_58Validate(ptc_str_58);
      this.ptc_str_58 = ptc_str_58;
   }
   void ptc_str_58Validate(java.lang.String ptc_str_58) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_58_UPPER_LIMIT < 1) {
         try { PTC_STR_58_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_58").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_58_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_58 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_58.toString(), PTC_STR_58_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_58"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_58_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_58", this.ptc_str_58, ptc_str_58));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_59 = "ptc_str_59";
   static int PTC_STR_59_UPPER_LIMIT = -1;
   java.lang.String ptc_str_59;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_59() {
      return ptc_str_59;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_59(java.lang.String ptc_str_59) throws wt.util.WTPropertyVetoException {
      ptc_str_59Validate(ptc_str_59);
      this.ptc_str_59 = ptc_str_59;
   }
   void ptc_str_59Validate(java.lang.String ptc_str_59) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_59_UPPER_LIMIT < 1) {
         try { PTC_STR_59_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_59").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_59_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_59 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_59.toString(), PTC_STR_59_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_59"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_59_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_59", this.ptc_str_59, ptc_str_59));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_60 = "ptc_str_60";
   static int PTC_STR_60_UPPER_LIMIT = -1;
   java.lang.String ptc_str_60;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_60() {
      return ptc_str_60;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_60(java.lang.String ptc_str_60) throws wt.util.WTPropertyVetoException {
      ptc_str_60Validate(ptc_str_60);
      this.ptc_str_60 = ptc_str_60;
   }
   void ptc_str_60Validate(java.lang.String ptc_str_60) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_60_UPPER_LIMIT < 1) {
         try { PTC_STR_60_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_60").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_60_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_60 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_60.toString(), PTC_STR_60_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_60"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_60_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_60", this.ptc_str_60, ptc_str_60));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_61 = "ptc_str_61";
   static int PTC_STR_61_UPPER_LIMIT = -1;
   java.lang.String ptc_str_61;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_61() {
      return ptc_str_61;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_61(java.lang.String ptc_str_61) throws wt.util.WTPropertyVetoException {
      ptc_str_61Validate(ptc_str_61);
      this.ptc_str_61 = ptc_str_61;
   }
   void ptc_str_61Validate(java.lang.String ptc_str_61) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_61_UPPER_LIMIT < 1) {
         try { PTC_STR_61_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_61").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_61_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_61 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_61.toString(), PTC_STR_61_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_61"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_61_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_61", this.ptc_str_61, ptc_str_61));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_62 = "ptc_str_62";
   static int PTC_STR_62_UPPER_LIMIT = -1;
   java.lang.String ptc_str_62;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_62() {
      return ptc_str_62;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_62(java.lang.String ptc_str_62) throws wt.util.WTPropertyVetoException {
      ptc_str_62Validate(ptc_str_62);
      this.ptc_str_62 = ptc_str_62;
   }
   void ptc_str_62Validate(java.lang.String ptc_str_62) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_62_UPPER_LIMIT < 1) {
         try { PTC_STR_62_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_62").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_62_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_62 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_62.toString(), PTC_STR_62_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_62"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_62_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_62", this.ptc_str_62, ptc_str_62));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_63 = "ptc_str_63";
   static int PTC_STR_63_UPPER_LIMIT = -1;
   java.lang.String ptc_str_63;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_63() {
      return ptc_str_63;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_63(java.lang.String ptc_str_63) throws wt.util.WTPropertyVetoException {
      ptc_str_63Validate(ptc_str_63);
      this.ptc_str_63 = ptc_str_63;
   }
   void ptc_str_63Validate(java.lang.String ptc_str_63) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_63_UPPER_LIMIT < 1) {
         try { PTC_STR_63_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_63").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_63_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_63 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_63.toString(), PTC_STR_63_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_63"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_63_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_63", this.ptc_str_63, ptc_str_63));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_64 = "ptc_str_64";
   static int PTC_STR_64_UPPER_LIMIT = -1;
   java.lang.String ptc_str_64;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_64() {
      return ptc_str_64;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_64(java.lang.String ptc_str_64) throws wt.util.WTPropertyVetoException {
      ptc_str_64Validate(ptc_str_64);
      this.ptc_str_64 = ptc_str_64;
   }
   void ptc_str_64Validate(java.lang.String ptc_str_64) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_64_UPPER_LIMIT < 1) {
         try { PTC_STR_64_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_64").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_64_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_64 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_64.toString(), PTC_STR_64_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_64"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_64_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_64", this.ptc_str_64, ptc_str_64));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_65 = "ptc_str_65";
   static int PTC_STR_65_UPPER_LIMIT = -1;
   java.lang.String ptc_str_65;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_65() {
      return ptc_str_65;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_65(java.lang.String ptc_str_65) throws wt.util.WTPropertyVetoException {
      ptc_str_65Validate(ptc_str_65);
      this.ptc_str_65 = ptc_str_65;
   }
   void ptc_str_65Validate(java.lang.String ptc_str_65) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_65_UPPER_LIMIT < 1) {
         try { PTC_STR_65_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_65").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_65_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_65 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_65.toString(), PTC_STR_65_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_65"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_65_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_65", this.ptc_str_65, ptc_str_65));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_66 = "ptc_str_66";
   static int PTC_STR_66_UPPER_LIMIT = -1;
   java.lang.String ptc_str_66;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_66() {
      return ptc_str_66;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_66(java.lang.String ptc_str_66) throws wt.util.WTPropertyVetoException {
      ptc_str_66Validate(ptc_str_66);
      this.ptc_str_66 = ptc_str_66;
   }
   void ptc_str_66Validate(java.lang.String ptc_str_66) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_66_UPPER_LIMIT < 1) {
         try { PTC_STR_66_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_66").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_66_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_66 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_66.toString(), PTC_STR_66_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_66"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_66_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_66", this.ptc_str_66, ptc_str_66));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_67 = "ptc_str_67";
   static int PTC_STR_67_UPPER_LIMIT = -1;
   java.lang.String ptc_str_67;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_67() {
      return ptc_str_67;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_67(java.lang.String ptc_str_67) throws wt.util.WTPropertyVetoException {
      ptc_str_67Validate(ptc_str_67);
      this.ptc_str_67 = ptc_str_67;
   }
   void ptc_str_67Validate(java.lang.String ptc_str_67) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_67_UPPER_LIMIT < 1) {
         try { PTC_STR_67_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_67").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_67_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_67 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_67.toString(), PTC_STR_67_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_67"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_67_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_67", this.ptc_str_67, ptc_str_67));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_68 = "ptc_str_68";
   static int PTC_STR_68_UPPER_LIMIT = -1;
   java.lang.String ptc_str_68;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_68() {
      return ptc_str_68;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_68(java.lang.String ptc_str_68) throws wt.util.WTPropertyVetoException {
      ptc_str_68Validate(ptc_str_68);
      this.ptc_str_68 = ptc_str_68;
   }
   void ptc_str_68Validate(java.lang.String ptc_str_68) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_68_UPPER_LIMIT < 1) {
         try { PTC_STR_68_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_68").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_68_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_68 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_68.toString(), PTC_STR_68_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_68"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_68_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_68", this.ptc_str_68, ptc_str_68));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_69 = "ptc_str_69";
   static int PTC_STR_69_UPPER_LIMIT = -1;
   java.lang.String ptc_str_69;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_69() {
      return ptc_str_69;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_69(java.lang.String ptc_str_69) throws wt.util.WTPropertyVetoException {
      ptc_str_69Validate(ptc_str_69);
      this.ptc_str_69 = ptc_str_69;
   }
   void ptc_str_69Validate(java.lang.String ptc_str_69) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_69_UPPER_LIMIT < 1) {
         try { PTC_STR_69_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_69").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_69_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_69 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_69.toString(), PTC_STR_69_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_69"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_69_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_69", this.ptc_str_69, ptc_str_69));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_70 = "ptc_str_70";
   static int PTC_STR_70_UPPER_LIMIT = -1;
   java.lang.String ptc_str_70;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_70() {
      return ptc_str_70;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_70(java.lang.String ptc_str_70) throws wt.util.WTPropertyVetoException {
      ptc_str_70Validate(ptc_str_70);
      this.ptc_str_70 = ptc_str_70;
   }
   void ptc_str_70Validate(java.lang.String ptc_str_70) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_70_UPPER_LIMIT < 1) {
         try { PTC_STR_70_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_70").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_70_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_70 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_70.toString(), PTC_STR_70_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_70"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_70_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_70", this.ptc_str_70, ptc_str_70));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_71 = "ptc_str_71";
   static int PTC_STR_71_UPPER_LIMIT = -1;
   java.lang.String ptc_str_71;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_71() {
      return ptc_str_71;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_71(java.lang.String ptc_str_71) throws wt.util.WTPropertyVetoException {
      ptc_str_71Validate(ptc_str_71);
      this.ptc_str_71 = ptc_str_71;
   }
   void ptc_str_71Validate(java.lang.String ptc_str_71) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_71_UPPER_LIMIT < 1) {
         try { PTC_STR_71_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_71").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_71_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_71 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_71.toString(), PTC_STR_71_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_71"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_71_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_71", this.ptc_str_71, ptc_str_71));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_72 = "ptc_str_72";
   static int PTC_STR_72_UPPER_LIMIT = -1;
   java.lang.String ptc_str_72;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_72() {
      return ptc_str_72;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_72(java.lang.String ptc_str_72) throws wt.util.WTPropertyVetoException {
      ptc_str_72Validate(ptc_str_72);
      this.ptc_str_72 = ptc_str_72;
   }
   void ptc_str_72Validate(java.lang.String ptc_str_72) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_72_UPPER_LIMIT < 1) {
         try { PTC_STR_72_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_72").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_72_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_72 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_72.toString(), PTC_STR_72_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_72"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_72_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_72", this.ptc_str_72, ptc_str_72));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_73 = "ptc_str_73";
   static int PTC_STR_73_UPPER_LIMIT = -1;
   java.lang.String ptc_str_73;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_73() {
      return ptc_str_73;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_73(java.lang.String ptc_str_73) throws wt.util.WTPropertyVetoException {
      ptc_str_73Validate(ptc_str_73);
      this.ptc_str_73 = ptc_str_73;
   }
   void ptc_str_73Validate(java.lang.String ptc_str_73) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_73_UPPER_LIMIT < 1) {
         try { PTC_STR_73_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_73").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_73_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_73 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_73.toString(), PTC_STR_73_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_73"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_73_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_73", this.ptc_str_73, ptc_str_73));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_74 = "ptc_str_74";
   static int PTC_STR_74_UPPER_LIMIT = -1;
   java.lang.String ptc_str_74;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_74() {
      return ptc_str_74;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_74(java.lang.String ptc_str_74) throws wt.util.WTPropertyVetoException {
      ptc_str_74Validate(ptc_str_74);
      this.ptc_str_74 = ptc_str_74;
   }
   void ptc_str_74Validate(java.lang.String ptc_str_74) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_74_UPPER_LIMIT < 1) {
         try { PTC_STR_74_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_74").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_74_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_74 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_74.toString(), PTC_STR_74_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_74"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_74_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_74", this.ptc_str_74, ptc_str_74));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_75 = "ptc_str_75";
   static int PTC_STR_75_UPPER_LIMIT = -1;
   java.lang.String ptc_str_75;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_75() {
      return ptc_str_75;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_75(java.lang.String ptc_str_75) throws wt.util.WTPropertyVetoException {
      ptc_str_75Validate(ptc_str_75);
      this.ptc_str_75 = ptc_str_75;
   }
   void ptc_str_75Validate(java.lang.String ptc_str_75) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_75_UPPER_LIMIT < 1) {
         try { PTC_STR_75_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_75").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_75_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_75 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_75.toString(), PTC_STR_75_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_75"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_75_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_75", this.ptc_str_75, ptc_str_75));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_76 = "ptc_str_76";
   static int PTC_STR_76_UPPER_LIMIT = -1;
   java.lang.String ptc_str_76;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_76() {
      return ptc_str_76;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_76(java.lang.String ptc_str_76) throws wt.util.WTPropertyVetoException {
      ptc_str_76Validate(ptc_str_76);
      this.ptc_str_76 = ptc_str_76;
   }
   void ptc_str_76Validate(java.lang.String ptc_str_76) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_76_UPPER_LIMIT < 1) {
         try { PTC_STR_76_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_76").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_76_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_76 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_76.toString(), PTC_STR_76_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_76"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_76_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_76", this.ptc_str_76, ptc_str_76));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_77 = "ptc_str_77";
   static int PTC_STR_77_UPPER_LIMIT = -1;
   java.lang.String ptc_str_77;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_77() {
      return ptc_str_77;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_77(java.lang.String ptc_str_77) throws wt.util.WTPropertyVetoException {
      ptc_str_77Validate(ptc_str_77);
      this.ptc_str_77 = ptc_str_77;
   }
   void ptc_str_77Validate(java.lang.String ptc_str_77) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_77_UPPER_LIMIT < 1) {
         try { PTC_STR_77_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_77").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_77_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_77 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_77.toString(), PTC_STR_77_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_77"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_77_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_77", this.ptc_str_77, ptc_str_77));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_78 = "ptc_str_78";
   static int PTC_STR_78_UPPER_LIMIT = -1;
   java.lang.String ptc_str_78;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_78() {
      return ptc_str_78;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_78(java.lang.String ptc_str_78) throws wt.util.WTPropertyVetoException {
      ptc_str_78Validate(ptc_str_78);
      this.ptc_str_78 = ptc_str_78;
   }
   void ptc_str_78Validate(java.lang.String ptc_str_78) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_78_UPPER_LIMIT < 1) {
         try { PTC_STR_78_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_78").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_78_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_78 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_78.toString(), PTC_STR_78_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_78"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_78_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_78", this.ptc_str_78, ptc_str_78));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_79 = "ptc_str_79";
   static int PTC_STR_79_UPPER_LIMIT = -1;
   java.lang.String ptc_str_79;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_79() {
      return ptc_str_79;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_79(java.lang.String ptc_str_79) throws wt.util.WTPropertyVetoException {
      ptc_str_79Validate(ptc_str_79);
      this.ptc_str_79 = ptc_str_79;
   }
   void ptc_str_79Validate(java.lang.String ptc_str_79) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_79_UPPER_LIMIT < 1) {
         try { PTC_STR_79_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_79").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_79_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_79 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_79.toString(), PTC_STR_79_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_79"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_79_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_79", this.ptc_str_79, ptc_str_79));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_80 = "ptc_str_80";
   static int PTC_STR_80_UPPER_LIMIT = -1;
   java.lang.String ptc_str_80;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_80() {
      return ptc_str_80;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_80(java.lang.String ptc_str_80) throws wt.util.WTPropertyVetoException {
      ptc_str_80Validate(ptc_str_80);
      this.ptc_str_80 = ptc_str_80;
   }
   void ptc_str_80Validate(java.lang.String ptc_str_80) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_80_UPPER_LIMIT < 1) {
         try { PTC_STR_80_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_80").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_80_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_80 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_80.toString(), PTC_STR_80_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_80"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_80_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_80", this.ptc_str_80, ptc_str_80));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_81 = "ptc_str_81";
   static int PTC_STR_81_UPPER_LIMIT = -1;
   java.lang.String ptc_str_81;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_81() {
      return ptc_str_81;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_81(java.lang.String ptc_str_81) throws wt.util.WTPropertyVetoException {
      ptc_str_81Validate(ptc_str_81);
      this.ptc_str_81 = ptc_str_81;
   }
   void ptc_str_81Validate(java.lang.String ptc_str_81) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_81_UPPER_LIMIT < 1) {
         try { PTC_STR_81_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_81").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_81_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_81 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_81.toString(), PTC_STR_81_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_81"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_81_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_81", this.ptc_str_81, ptc_str_81));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_82 = "ptc_str_82";
   static int PTC_STR_82_UPPER_LIMIT = -1;
   java.lang.String ptc_str_82;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_82() {
      return ptc_str_82;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_82(java.lang.String ptc_str_82) throws wt.util.WTPropertyVetoException {
      ptc_str_82Validate(ptc_str_82);
      this.ptc_str_82 = ptc_str_82;
   }
   void ptc_str_82Validate(java.lang.String ptc_str_82) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_82_UPPER_LIMIT < 1) {
         try { PTC_STR_82_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_82").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_82_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_82 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_82.toString(), PTC_STR_82_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_82"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_82_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_82", this.ptc_str_82, ptc_str_82));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_83 = "ptc_str_83";
   static int PTC_STR_83_UPPER_LIMIT = -1;
   java.lang.String ptc_str_83;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_83() {
      return ptc_str_83;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_83(java.lang.String ptc_str_83) throws wt.util.WTPropertyVetoException {
      ptc_str_83Validate(ptc_str_83);
      this.ptc_str_83 = ptc_str_83;
   }
   void ptc_str_83Validate(java.lang.String ptc_str_83) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_83_UPPER_LIMIT < 1) {
         try { PTC_STR_83_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_83").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_83_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_83 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_83.toString(), PTC_STR_83_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_83"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_83_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_83", this.ptc_str_83, ptc_str_83));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_84 = "ptc_str_84";
   static int PTC_STR_84_UPPER_LIMIT = -1;
   java.lang.String ptc_str_84;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_84() {
      return ptc_str_84;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_84(java.lang.String ptc_str_84) throws wt.util.WTPropertyVetoException {
      ptc_str_84Validate(ptc_str_84);
      this.ptc_str_84 = ptc_str_84;
   }
   void ptc_str_84Validate(java.lang.String ptc_str_84) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_84_UPPER_LIMIT < 1) {
         try { PTC_STR_84_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_84").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_84_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_84 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_84.toString(), PTC_STR_84_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_84"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_84_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_84", this.ptc_str_84, ptc_str_84));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_85 = "ptc_str_85";
   static int PTC_STR_85_UPPER_LIMIT = -1;
   java.lang.String ptc_str_85;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_85() {
      return ptc_str_85;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_85(java.lang.String ptc_str_85) throws wt.util.WTPropertyVetoException {
      ptc_str_85Validate(ptc_str_85);
      this.ptc_str_85 = ptc_str_85;
   }
   void ptc_str_85Validate(java.lang.String ptc_str_85) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_85_UPPER_LIMIT < 1) {
         try { PTC_STR_85_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_85").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_85_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_85 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_85.toString(), PTC_STR_85_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_85"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_85_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_85", this.ptc_str_85, ptc_str_85));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_86 = "ptc_str_86";
   static int PTC_STR_86_UPPER_LIMIT = -1;
   java.lang.String ptc_str_86;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_86() {
      return ptc_str_86;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_86(java.lang.String ptc_str_86) throws wt.util.WTPropertyVetoException {
      ptc_str_86Validate(ptc_str_86);
      this.ptc_str_86 = ptc_str_86;
   }
   void ptc_str_86Validate(java.lang.String ptc_str_86) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_86_UPPER_LIMIT < 1) {
         try { PTC_STR_86_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_86").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_86_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_86 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_86.toString(), PTC_STR_86_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_86"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_86_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_86", this.ptc_str_86, ptc_str_86));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_87 = "ptc_str_87";
   static int PTC_STR_87_UPPER_LIMIT = -1;
   java.lang.String ptc_str_87;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_87() {
      return ptc_str_87;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_87(java.lang.String ptc_str_87) throws wt.util.WTPropertyVetoException {
      ptc_str_87Validate(ptc_str_87);
      this.ptc_str_87 = ptc_str_87;
   }
   void ptc_str_87Validate(java.lang.String ptc_str_87) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_87_UPPER_LIMIT < 1) {
         try { PTC_STR_87_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_87").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_87_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_87 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_87.toString(), PTC_STR_87_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_87"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_87_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_87", this.ptc_str_87, ptc_str_87));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_88 = "ptc_str_88";
   static int PTC_STR_88_UPPER_LIMIT = -1;
   java.lang.String ptc_str_88;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_88() {
      return ptc_str_88;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_88(java.lang.String ptc_str_88) throws wt.util.WTPropertyVetoException {
      ptc_str_88Validate(ptc_str_88);
      this.ptc_str_88 = ptc_str_88;
   }
   void ptc_str_88Validate(java.lang.String ptc_str_88) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_88_UPPER_LIMIT < 1) {
         try { PTC_STR_88_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_88").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_88_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_88 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_88.toString(), PTC_STR_88_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_88"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_88_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_88", this.ptc_str_88, ptc_str_88));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_89 = "ptc_str_89";
   static int PTC_STR_89_UPPER_LIMIT = -1;
   java.lang.String ptc_str_89;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_89() {
      return ptc_str_89;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_89(java.lang.String ptc_str_89) throws wt.util.WTPropertyVetoException {
      ptc_str_89Validate(ptc_str_89);
      this.ptc_str_89 = ptc_str_89;
   }
   void ptc_str_89Validate(java.lang.String ptc_str_89) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_89_UPPER_LIMIT < 1) {
         try { PTC_STR_89_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_89").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_89_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_89 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_89.toString(), PTC_STR_89_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_89"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_89_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_89", this.ptc_str_89, ptc_str_89));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_90 = "ptc_str_90";
   static int PTC_STR_90_UPPER_LIMIT = -1;
   java.lang.String ptc_str_90;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_90() {
      return ptc_str_90;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_90(java.lang.String ptc_str_90) throws wt.util.WTPropertyVetoException {
      ptc_str_90Validate(ptc_str_90);
      this.ptc_str_90 = ptc_str_90;
   }
   void ptc_str_90Validate(java.lang.String ptc_str_90) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_90_UPPER_LIMIT < 1) {
         try { PTC_STR_90_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_90").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_90_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_90 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_90.toString(), PTC_STR_90_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_90"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_90_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_90", this.ptc_str_90, ptc_str_90));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_91 = "ptc_str_91";
   static int PTC_STR_91_UPPER_LIMIT = -1;
   java.lang.String ptc_str_91;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_91() {
      return ptc_str_91;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_91(java.lang.String ptc_str_91) throws wt.util.WTPropertyVetoException {
      ptc_str_91Validate(ptc_str_91);
      this.ptc_str_91 = ptc_str_91;
   }
   void ptc_str_91Validate(java.lang.String ptc_str_91) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_91_UPPER_LIMIT < 1) {
         try { PTC_STR_91_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_91").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_91_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_91 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_91.toString(), PTC_STR_91_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_91"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_91_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_91", this.ptc_str_91, ptc_str_91));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_92 = "ptc_str_92";
   static int PTC_STR_92_UPPER_LIMIT = -1;
   java.lang.String ptc_str_92;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_92() {
      return ptc_str_92;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_92(java.lang.String ptc_str_92) throws wt.util.WTPropertyVetoException {
      ptc_str_92Validate(ptc_str_92);
      this.ptc_str_92 = ptc_str_92;
   }
   void ptc_str_92Validate(java.lang.String ptc_str_92) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_92_UPPER_LIMIT < 1) {
         try { PTC_STR_92_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_92").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_92_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_92 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_92.toString(), PTC_STR_92_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_92"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_92_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_92", this.ptc_str_92, ptc_str_92));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_93 = "ptc_str_93";
   static int PTC_STR_93_UPPER_LIMIT = -1;
   java.lang.String ptc_str_93;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_93() {
      return ptc_str_93;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_93(java.lang.String ptc_str_93) throws wt.util.WTPropertyVetoException {
      ptc_str_93Validate(ptc_str_93);
      this.ptc_str_93 = ptc_str_93;
   }
   void ptc_str_93Validate(java.lang.String ptc_str_93) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_93_UPPER_LIMIT < 1) {
         try { PTC_STR_93_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_93").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_93_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_93 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_93.toString(), PTC_STR_93_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_93"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_93_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_93", this.ptc_str_93, ptc_str_93));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_94 = "ptc_str_94";
   static int PTC_STR_94_UPPER_LIMIT = -1;
   java.lang.String ptc_str_94;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_94() {
      return ptc_str_94;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_94(java.lang.String ptc_str_94) throws wt.util.WTPropertyVetoException {
      ptc_str_94Validate(ptc_str_94);
      this.ptc_str_94 = ptc_str_94;
   }
   void ptc_str_94Validate(java.lang.String ptc_str_94) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_94_UPPER_LIMIT < 1) {
         try { PTC_STR_94_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_94").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_94_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_94 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_94.toString(), PTC_STR_94_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_94"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_94_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_94", this.ptc_str_94, ptc_str_94));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_95 = "ptc_str_95";
   static int PTC_STR_95_UPPER_LIMIT = -1;
   java.lang.String ptc_str_95;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_95() {
      return ptc_str_95;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_95(java.lang.String ptc_str_95) throws wt.util.WTPropertyVetoException {
      ptc_str_95Validate(ptc_str_95);
      this.ptc_str_95 = ptc_str_95;
   }
   void ptc_str_95Validate(java.lang.String ptc_str_95) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_95_UPPER_LIMIT < 1) {
         try { PTC_STR_95_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_95").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_95_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_95 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_95.toString(), PTC_STR_95_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_95"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_95_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_95", this.ptc_str_95, ptc_str_95));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_96 = "ptc_str_96";
   static int PTC_STR_96_UPPER_LIMIT = -1;
   java.lang.String ptc_str_96;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_96() {
      return ptc_str_96;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_96(java.lang.String ptc_str_96) throws wt.util.WTPropertyVetoException {
      ptc_str_96Validate(ptc_str_96);
      this.ptc_str_96 = ptc_str_96;
   }
   void ptc_str_96Validate(java.lang.String ptc_str_96) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_96_UPPER_LIMIT < 1) {
         try { PTC_STR_96_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_96").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_96_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_96 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_96.toString(), PTC_STR_96_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_96"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_96_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_96", this.ptc_str_96, ptc_str_96));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_97 = "ptc_str_97";
   static int PTC_STR_97_UPPER_LIMIT = -1;
   java.lang.String ptc_str_97;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_97() {
      return ptc_str_97;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_97(java.lang.String ptc_str_97) throws wt.util.WTPropertyVetoException {
      ptc_str_97Validate(ptc_str_97);
      this.ptc_str_97 = ptc_str_97;
   }
   void ptc_str_97Validate(java.lang.String ptc_str_97) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_97_UPPER_LIMIT < 1) {
         try { PTC_STR_97_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_97").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_97_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_97 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_97.toString(), PTC_STR_97_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_97"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_97_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_97", this.ptc_str_97, ptc_str_97));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_98 = "ptc_str_98";
   static int PTC_STR_98_UPPER_LIMIT = -1;
   java.lang.String ptc_str_98;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_98() {
      return ptc_str_98;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_98(java.lang.String ptc_str_98) throws wt.util.WTPropertyVetoException {
      ptc_str_98Validate(ptc_str_98);
      this.ptc_str_98 = ptc_str_98;
   }
   void ptc_str_98Validate(java.lang.String ptc_str_98) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_98_UPPER_LIMIT < 1) {
         try { PTC_STR_98_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_98").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_98_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_98 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_98.toString(), PTC_STR_98_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_98"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_98_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_98", this.ptc_str_98, ptc_str_98));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_99 = "ptc_str_99";
   static int PTC_STR_99_UPPER_LIMIT = -1;
   java.lang.String ptc_str_99;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_99() {
      return ptc_str_99;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_99(java.lang.String ptc_str_99) throws wt.util.WTPropertyVetoException {
      ptc_str_99Validate(ptc_str_99);
      this.ptc_str_99 = ptc_str_99;
   }
   void ptc_str_99Validate(java.lang.String ptc_str_99) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_99_UPPER_LIMIT < 1) {
         try { PTC_STR_99_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_99").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_99_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_99 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_99.toString(), PTC_STR_99_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_99"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_99_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_99", this.ptc_str_99, ptc_str_99));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_100 = "ptc_str_100";
   static int PTC_STR_100_UPPER_LIMIT = -1;
   java.lang.String ptc_str_100;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_100() {
      return ptc_str_100;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_100(java.lang.String ptc_str_100) throws wt.util.WTPropertyVetoException {
      ptc_str_100Validate(ptc_str_100);
      this.ptc_str_100 = ptc_str_100;
   }
   void ptc_str_100Validate(java.lang.String ptc_str_100) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_100_UPPER_LIMIT < 1) {
         try { PTC_STR_100_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_100").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_100_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_100 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_100.toString(), PTC_STR_100_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_100"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_100_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_100", this.ptc_str_100, ptc_str_100));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_101 = "ptc_str_101";
   static int PTC_STR_101_UPPER_LIMIT = -1;
   java.lang.String ptc_str_101;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_101() {
      return ptc_str_101;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_101(java.lang.String ptc_str_101) throws wt.util.WTPropertyVetoException {
      ptc_str_101Validate(ptc_str_101);
      this.ptc_str_101 = ptc_str_101;
   }
   void ptc_str_101Validate(java.lang.String ptc_str_101) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_101_UPPER_LIMIT < 1) {
         try { PTC_STR_101_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_101").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_101_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_101 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_101.toString(), PTC_STR_101_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_101"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_101_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_101", this.ptc_str_101, ptc_str_101));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_102 = "ptc_str_102";
   static int PTC_STR_102_UPPER_LIMIT = -1;
   java.lang.String ptc_str_102;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_102() {
      return ptc_str_102;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_102(java.lang.String ptc_str_102) throws wt.util.WTPropertyVetoException {
      ptc_str_102Validate(ptc_str_102);
      this.ptc_str_102 = ptc_str_102;
   }
   void ptc_str_102Validate(java.lang.String ptc_str_102) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_102_UPPER_LIMIT < 1) {
         try { PTC_STR_102_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_102").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_102_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_102 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_102.toString(), PTC_STR_102_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_102"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_102_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_102", this.ptc_str_102, ptc_str_102));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_103 = "ptc_str_103";
   static int PTC_STR_103_UPPER_LIMIT = -1;
   java.lang.String ptc_str_103;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_103() {
      return ptc_str_103;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_103(java.lang.String ptc_str_103) throws wt.util.WTPropertyVetoException {
      ptc_str_103Validate(ptc_str_103);
      this.ptc_str_103 = ptc_str_103;
   }
   void ptc_str_103Validate(java.lang.String ptc_str_103) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_103_UPPER_LIMIT < 1) {
         try { PTC_STR_103_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_103").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_103_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_103 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_103.toString(), PTC_STR_103_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_103"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_103_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_103", this.ptc_str_103, ptc_str_103));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_104 = "ptc_str_104";
   static int PTC_STR_104_UPPER_LIMIT = -1;
   java.lang.String ptc_str_104;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_104() {
      return ptc_str_104;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_104(java.lang.String ptc_str_104) throws wt.util.WTPropertyVetoException {
      ptc_str_104Validate(ptc_str_104);
      this.ptc_str_104 = ptc_str_104;
   }
   void ptc_str_104Validate(java.lang.String ptc_str_104) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_104_UPPER_LIMIT < 1) {
         try { PTC_STR_104_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_104").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_104_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_104 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_104.toString(), PTC_STR_104_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_104"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_104_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_104", this.ptc_str_104, ptc_str_104));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_105 = "ptc_str_105";
   static int PTC_STR_105_UPPER_LIMIT = -1;
   java.lang.String ptc_str_105;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_105() {
      return ptc_str_105;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_105(java.lang.String ptc_str_105) throws wt.util.WTPropertyVetoException {
      ptc_str_105Validate(ptc_str_105);
      this.ptc_str_105 = ptc_str_105;
   }
   void ptc_str_105Validate(java.lang.String ptc_str_105) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_105_UPPER_LIMIT < 1) {
         try { PTC_STR_105_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_105").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_105_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_105 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_105.toString(), PTC_STR_105_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_105"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_105_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_105", this.ptc_str_105, ptc_str_105));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_106 = "ptc_str_106";
   static int PTC_STR_106_UPPER_LIMIT = -1;
   java.lang.String ptc_str_106;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_106() {
      return ptc_str_106;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_106(java.lang.String ptc_str_106) throws wt.util.WTPropertyVetoException {
      ptc_str_106Validate(ptc_str_106);
      this.ptc_str_106 = ptc_str_106;
   }
   void ptc_str_106Validate(java.lang.String ptc_str_106) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_106_UPPER_LIMIT < 1) {
         try { PTC_STR_106_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_106").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_106_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_106 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_106.toString(), PTC_STR_106_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_106"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_106_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_106", this.ptc_str_106, ptc_str_106));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_107 = "ptc_str_107";
   static int PTC_STR_107_UPPER_LIMIT = -1;
   java.lang.String ptc_str_107;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_107() {
      return ptc_str_107;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_107(java.lang.String ptc_str_107) throws wt.util.WTPropertyVetoException {
      ptc_str_107Validate(ptc_str_107);
      this.ptc_str_107 = ptc_str_107;
   }
   void ptc_str_107Validate(java.lang.String ptc_str_107) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_107_UPPER_LIMIT < 1) {
         try { PTC_STR_107_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_107").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_107_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_107 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_107.toString(), PTC_STR_107_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_107"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_107_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_107", this.ptc_str_107, ptc_str_107));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_108 = "ptc_str_108";
   static int PTC_STR_108_UPPER_LIMIT = -1;
   java.lang.String ptc_str_108;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_108() {
      return ptc_str_108;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_108(java.lang.String ptc_str_108) throws wt.util.WTPropertyVetoException {
      ptc_str_108Validate(ptc_str_108);
      this.ptc_str_108 = ptc_str_108;
   }
   void ptc_str_108Validate(java.lang.String ptc_str_108) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_108_UPPER_LIMIT < 1) {
         try { PTC_STR_108_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_108").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_108_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_108 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_108.toString(), PTC_STR_108_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_108"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_108_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_108", this.ptc_str_108, ptc_str_108));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_109 = "ptc_str_109";
   static int PTC_STR_109_UPPER_LIMIT = -1;
   java.lang.String ptc_str_109;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_109() {
      return ptc_str_109;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_109(java.lang.String ptc_str_109) throws wt.util.WTPropertyVetoException {
      ptc_str_109Validate(ptc_str_109);
      this.ptc_str_109 = ptc_str_109;
   }
   void ptc_str_109Validate(java.lang.String ptc_str_109) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_109_UPPER_LIMIT < 1) {
         try { PTC_STR_109_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_109").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_109_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_109 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_109.toString(), PTC_STR_109_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_109"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_109_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_109", this.ptc_str_109, ptc_str_109));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_110 = "ptc_str_110";
   static int PTC_STR_110_UPPER_LIMIT = -1;
   java.lang.String ptc_str_110;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_110() {
      return ptc_str_110;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_110(java.lang.String ptc_str_110) throws wt.util.WTPropertyVetoException {
      ptc_str_110Validate(ptc_str_110);
      this.ptc_str_110 = ptc_str_110;
   }
   void ptc_str_110Validate(java.lang.String ptc_str_110) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_110_UPPER_LIMIT < 1) {
         try { PTC_STR_110_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_110").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_110_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_110 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_110.toString(), PTC_STR_110_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_110"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_110_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_110", this.ptc_str_110, ptc_str_110));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_111 = "ptc_str_111";
   static int PTC_STR_111_UPPER_LIMIT = -1;
   java.lang.String ptc_str_111;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_111() {
      return ptc_str_111;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_111(java.lang.String ptc_str_111) throws wt.util.WTPropertyVetoException {
      ptc_str_111Validate(ptc_str_111);
      this.ptc_str_111 = ptc_str_111;
   }
   void ptc_str_111Validate(java.lang.String ptc_str_111) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_111_UPPER_LIMIT < 1) {
         try { PTC_STR_111_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_111").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_111_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_111 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_111.toString(), PTC_STR_111_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_111"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_111_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_111", this.ptc_str_111, ptc_str_111));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_112 = "ptc_str_112";
   static int PTC_STR_112_UPPER_LIMIT = -1;
   java.lang.String ptc_str_112;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_112() {
      return ptc_str_112;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_112(java.lang.String ptc_str_112) throws wt.util.WTPropertyVetoException {
      ptc_str_112Validate(ptc_str_112);
      this.ptc_str_112 = ptc_str_112;
   }
   void ptc_str_112Validate(java.lang.String ptc_str_112) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_112_UPPER_LIMIT < 1) {
         try { PTC_STR_112_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_112").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_112_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_112 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_112.toString(), PTC_STR_112_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_112"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_112_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_112", this.ptc_str_112, ptc_str_112));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_113 = "ptc_str_113";
   static int PTC_STR_113_UPPER_LIMIT = -1;
   java.lang.String ptc_str_113;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_113() {
      return ptc_str_113;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_113(java.lang.String ptc_str_113) throws wt.util.WTPropertyVetoException {
      ptc_str_113Validate(ptc_str_113);
      this.ptc_str_113 = ptc_str_113;
   }
   void ptc_str_113Validate(java.lang.String ptc_str_113) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_113_UPPER_LIMIT < 1) {
         try { PTC_STR_113_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_113").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_113_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_113 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_113.toString(), PTC_STR_113_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_113"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_113_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_113", this.ptc_str_113, ptc_str_113));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_114 = "ptc_str_114";
   static int PTC_STR_114_UPPER_LIMIT = -1;
   java.lang.String ptc_str_114;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_114() {
      return ptc_str_114;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_114(java.lang.String ptc_str_114) throws wt.util.WTPropertyVetoException {
      ptc_str_114Validate(ptc_str_114);
      this.ptc_str_114 = ptc_str_114;
   }
   void ptc_str_114Validate(java.lang.String ptc_str_114) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_114_UPPER_LIMIT < 1) {
         try { PTC_STR_114_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_114").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_114_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_114 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_114.toString(), PTC_STR_114_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_114"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_114_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_114", this.ptc_str_114, ptc_str_114));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_115 = "ptc_str_115";
   static int PTC_STR_115_UPPER_LIMIT = -1;
   java.lang.String ptc_str_115;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_115() {
      return ptc_str_115;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_115(java.lang.String ptc_str_115) throws wt.util.WTPropertyVetoException {
      ptc_str_115Validate(ptc_str_115);
      this.ptc_str_115 = ptc_str_115;
   }
   void ptc_str_115Validate(java.lang.String ptc_str_115) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_115_UPPER_LIMIT < 1) {
         try { PTC_STR_115_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_115").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_115_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_115 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_115.toString(), PTC_STR_115_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_115"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_115_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_115", this.ptc_str_115, ptc_str_115));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_116 = "ptc_str_116";
   static int PTC_STR_116_UPPER_LIMIT = -1;
   java.lang.String ptc_str_116;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_116() {
      return ptc_str_116;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_116(java.lang.String ptc_str_116) throws wt.util.WTPropertyVetoException {
      ptc_str_116Validate(ptc_str_116);
      this.ptc_str_116 = ptc_str_116;
   }
   void ptc_str_116Validate(java.lang.String ptc_str_116) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_116_UPPER_LIMIT < 1) {
         try { PTC_STR_116_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_116").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_116_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_116 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_116.toString(), PTC_STR_116_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_116"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_116_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_116", this.ptc_str_116, ptc_str_116));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_117 = "ptc_str_117";
   static int PTC_STR_117_UPPER_LIMIT = -1;
   java.lang.String ptc_str_117;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_117() {
      return ptc_str_117;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_117(java.lang.String ptc_str_117) throws wt.util.WTPropertyVetoException {
      ptc_str_117Validate(ptc_str_117);
      this.ptc_str_117 = ptc_str_117;
   }
   void ptc_str_117Validate(java.lang.String ptc_str_117) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_117_UPPER_LIMIT < 1) {
         try { PTC_STR_117_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_117").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_117_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_117 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_117.toString(), PTC_STR_117_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_117"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_117_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_117", this.ptc_str_117, ptc_str_117));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_118 = "ptc_str_118";
   static int PTC_STR_118_UPPER_LIMIT = -1;
   java.lang.String ptc_str_118;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_118() {
      return ptc_str_118;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_118(java.lang.String ptc_str_118) throws wt.util.WTPropertyVetoException {
      ptc_str_118Validate(ptc_str_118);
      this.ptc_str_118 = ptc_str_118;
   }
   void ptc_str_118Validate(java.lang.String ptc_str_118) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_118_UPPER_LIMIT < 1) {
         try { PTC_STR_118_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_118").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_118_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_118 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_118.toString(), PTC_STR_118_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_118"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_118_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_118", this.ptc_str_118, ptc_str_118));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_119 = "ptc_str_119";
   static int PTC_STR_119_UPPER_LIMIT = -1;
   java.lang.String ptc_str_119;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_119() {
      return ptc_str_119;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_119(java.lang.String ptc_str_119) throws wt.util.WTPropertyVetoException {
      ptc_str_119Validate(ptc_str_119);
      this.ptc_str_119 = ptc_str_119;
   }
   void ptc_str_119Validate(java.lang.String ptc_str_119) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_119_UPPER_LIMIT < 1) {
         try { PTC_STR_119_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_119").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_119_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_119 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_119.toString(), PTC_STR_119_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_119"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_119_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_119", this.ptc_str_119, ptc_str_119));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_120 = "ptc_str_120";
   static int PTC_STR_120_UPPER_LIMIT = -1;
   java.lang.String ptc_str_120;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_120() {
      return ptc_str_120;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_120(java.lang.String ptc_str_120) throws wt.util.WTPropertyVetoException {
      ptc_str_120Validate(ptc_str_120);
      this.ptc_str_120 = ptc_str_120;
   }
   void ptc_str_120Validate(java.lang.String ptc_str_120) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_120_UPPER_LIMIT < 1) {
         try { PTC_STR_120_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_120").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_120_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_120 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_120.toString(), PTC_STR_120_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_120"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_120_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_120", this.ptc_str_120, ptc_str_120));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_121 = "ptc_str_121";
   static int PTC_STR_121_UPPER_LIMIT = -1;
   java.lang.String ptc_str_121;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_121() {
      return ptc_str_121;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_121(java.lang.String ptc_str_121) throws wt.util.WTPropertyVetoException {
      ptc_str_121Validate(ptc_str_121);
      this.ptc_str_121 = ptc_str_121;
   }
   void ptc_str_121Validate(java.lang.String ptc_str_121) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_121_UPPER_LIMIT < 1) {
         try { PTC_STR_121_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_121").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_121_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_121 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_121.toString(), PTC_STR_121_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_121"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_121_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_121", this.ptc_str_121, ptc_str_121));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_122 = "ptc_str_122";
   static int PTC_STR_122_UPPER_LIMIT = -1;
   java.lang.String ptc_str_122;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_122() {
      return ptc_str_122;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_122(java.lang.String ptc_str_122) throws wt.util.WTPropertyVetoException {
      ptc_str_122Validate(ptc_str_122);
      this.ptc_str_122 = ptc_str_122;
   }
   void ptc_str_122Validate(java.lang.String ptc_str_122) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_122_UPPER_LIMIT < 1) {
         try { PTC_STR_122_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_122").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_122_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_122 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_122.toString(), PTC_STR_122_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_122"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_122_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_122", this.ptc_str_122, ptc_str_122));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_123 = "ptc_str_123";
   static int PTC_STR_123_UPPER_LIMIT = -1;
   java.lang.String ptc_str_123;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_123() {
      return ptc_str_123;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_123(java.lang.String ptc_str_123) throws wt.util.WTPropertyVetoException {
      ptc_str_123Validate(ptc_str_123);
      this.ptc_str_123 = ptc_str_123;
   }
   void ptc_str_123Validate(java.lang.String ptc_str_123) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_123_UPPER_LIMIT < 1) {
         try { PTC_STR_123_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_123").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_123_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_123 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_123.toString(), PTC_STR_123_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_123"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_123_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_123", this.ptc_str_123, ptc_str_123));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_124 = "ptc_str_124";
   static int PTC_STR_124_UPPER_LIMIT = -1;
   java.lang.String ptc_str_124;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_124() {
      return ptc_str_124;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_124(java.lang.String ptc_str_124) throws wt.util.WTPropertyVetoException {
      ptc_str_124Validate(ptc_str_124);
      this.ptc_str_124 = ptc_str_124;
   }
   void ptc_str_124Validate(java.lang.String ptc_str_124) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_124_UPPER_LIMIT < 1) {
         try { PTC_STR_124_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_124").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_124_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_124 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_124.toString(), PTC_STR_124_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_124"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_124_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_124", this.ptc_str_124, ptc_str_124));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_125 = "ptc_str_125";
   static int PTC_STR_125_UPPER_LIMIT = -1;
   java.lang.String ptc_str_125;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_125() {
      return ptc_str_125;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_125(java.lang.String ptc_str_125) throws wt.util.WTPropertyVetoException {
      ptc_str_125Validate(ptc_str_125);
      this.ptc_str_125 = ptc_str_125;
   }
   void ptc_str_125Validate(java.lang.String ptc_str_125) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_125_UPPER_LIMIT < 1) {
         try { PTC_STR_125_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_125").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_125_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_125 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_125.toString(), PTC_STR_125_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_125"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_125_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_125", this.ptc_str_125, ptc_str_125));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_126 = "ptc_str_126";
   static int PTC_STR_126_UPPER_LIMIT = -1;
   java.lang.String ptc_str_126;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_126() {
      return ptc_str_126;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_126(java.lang.String ptc_str_126) throws wt.util.WTPropertyVetoException {
      ptc_str_126Validate(ptc_str_126);
      this.ptc_str_126 = ptc_str_126;
   }
   void ptc_str_126Validate(java.lang.String ptc_str_126) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_126_UPPER_LIMIT < 1) {
         try { PTC_STR_126_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_126").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_126_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_126 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_126.toString(), PTC_STR_126_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_126"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_126_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_126", this.ptc_str_126, ptc_str_126));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_127 = "ptc_str_127";
   static int PTC_STR_127_UPPER_LIMIT = -1;
   java.lang.String ptc_str_127;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_127() {
      return ptc_str_127;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_127(java.lang.String ptc_str_127) throws wt.util.WTPropertyVetoException {
      ptc_str_127Validate(ptc_str_127);
      this.ptc_str_127 = ptc_str_127;
   }
   void ptc_str_127Validate(java.lang.String ptc_str_127) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_127_UPPER_LIMIT < 1) {
         try { PTC_STR_127_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_127").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_127_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_127 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_127.toString(), PTC_STR_127_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_127"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_127_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_127", this.ptc_str_127, ptc_str_127));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_128 = "ptc_str_128";
   static int PTC_STR_128_UPPER_LIMIT = -1;
   java.lang.String ptc_str_128;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_128() {
      return ptc_str_128;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_128(java.lang.String ptc_str_128) throws wt.util.WTPropertyVetoException {
      ptc_str_128Validate(ptc_str_128);
      this.ptc_str_128 = ptc_str_128;
   }
   void ptc_str_128Validate(java.lang.String ptc_str_128) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_128_UPPER_LIMIT < 1) {
         try { PTC_STR_128_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_128").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_128_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_128 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_128.toString(), PTC_STR_128_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_128"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_128_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_128", this.ptc_str_128, ptc_str_128));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_129 = "ptc_str_129";
   static int PTC_STR_129_UPPER_LIMIT = -1;
   java.lang.String ptc_str_129;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_129() {
      return ptc_str_129;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_129(java.lang.String ptc_str_129) throws wt.util.WTPropertyVetoException {
      ptc_str_129Validate(ptc_str_129);
      this.ptc_str_129 = ptc_str_129;
   }
   void ptc_str_129Validate(java.lang.String ptc_str_129) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_129_UPPER_LIMIT < 1) {
         try { PTC_STR_129_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_129").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_129_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_129 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_129.toString(), PTC_STR_129_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_129"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_129_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_129", this.ptc_str_129, ptc_str_129));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_130 = "ptc_str_130";
   static int PTC_STR_130_UPPER_LIMIT = -1;
   java.lang.String ptc_str_130;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_130() {
      return ptc_str_130;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_130(java.lang.String ptc_str_130) throws wt.util.WTPropertyVetoException {
      ptc_str_130Validate(ptc_str_130);
      this.ptc_str_130 = ptc_str_130;
   }
   void ptc_str_130Validate(java.lang.String ptc_str_130) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_130_UPPER_LIMIT < 1) {
         try { PTC_STR_130_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_130").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_130_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_130 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_130.toString(), PTC_STR_130_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_130"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_130_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_130", this.ptc_str_130, ptc_str_130));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_131 = "ptc_str_131";
   static int PTC_STR_131_UPPER_LIMIT = -1;
   java.lang.String ptc_str_131;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_131() {
      return ptc_str_131;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_131(java.lang.String ptc_str_131) throws wt.util.WTPropertyVetoException {
      ptc_str_131Validate(ptc_str_131);
      this.ptc_str_131 = ptc_str_131;
   }
   void ptc_str_131Validate(java.lang.String ptc_str_131) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_131_UPPER_LIMIT < 1) {
         try { PTC_STR_131_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_131").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_131_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_131 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_131.toString(), PTC_STR_131_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_131"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_131_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_131", this.ptc_str_131, ptc_str_131));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_132 = "ptc_str_132";
   static int PTC_STR_132_UPPER_LIMIT = -1;
   java.lang.String ptc_str_132;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_132() {
      return ptc_str_132;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_132(java.lang.String ptc_str_132) throws wt.util.WTPropertyVetoException {
      ptc_str_132Validate(ptc_str_132);
      this.ptc_str_132 = ptc_str_132;
   }
   void ptc_str_132Validate(java.lang.String ptc_str_132) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_132_UPPER_LIMIT < 1) {
         try { PTC_STR_132_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_132").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_132_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_132 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_132.toString(), PTC_STR_132_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_132"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_132_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_132", this.ptc_str_132, ptc_str_132));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_133 = "ptc_str_133";
   static int PTC_STR_133_UPPER_LIMIT = -1;
   java.lang.String ptc_str_133;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_133() {
      return ptc_str_133;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_133(java.lang.String ptc_str_133) throws wt.util.WTPropertyVetoException {
      ptc_str_133Validate(ptc_str_133);
      this.ptc_str_133 = ptc_str_133;
   }
   void ptc_str_133Validate(java.lang.String ptc_str_133) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_133_UPPER_LIMIT < 1) {
         try { PTC_STR_133_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_133").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_133_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_133 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_133.toString(), PTC_STR_133_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_133"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_133_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_133", this.ptc_str_133, ptc_str_133));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_134 = "ptc_str_134";
   static int PTC_STR_134_UPPER_LIMIT = -1;
   java.lang.String ptc_str_134;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_134() {
      return ptc_str_134;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_134(java.lang.String ptc_str_134) throws wt.util.WTPropertyVetoException {
      ptc_str_134Validate(ptc_str_134);
      this.ptc_str_134 = ptc_str_134;
   }
   void ptc_str_134Validate(java.lang.String ptc_str_134) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_134_UPPER_LIMIT < 1) {
         try { PTC_STR_134_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_134").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_134_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_134 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_134.toString(), PTC_STR_134_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_134"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_134_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_134", this.ptc_str_134, ptc_str_134));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_135 = "ptc_str_135";
   static int PTC_STR_135_UPPER_LIMIT = -1;
   java.lang.String ptc_str_135;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_135() {
      return ptc_str_135;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_135(java.lang.String ptc_str_135) throws wt.util.WTPropertyVetoException {
      ptc_str_135Validate(ptc_str_135);
      this.ptc_str_135 = ptc_str_135;
   }
   void ptc_str_135Validate(java.lang.String ptc_str_135) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_135_UPPER_LIMIT < 1) {
         try { PTC_STR_135_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_135").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_135_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_135 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_135.toString(), PTC_STR_135_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_135"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_135_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_135", this.ptc_str_135, ptc_str_135));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_136 = "ptc_str_136";
   static int PTC_STR_136_UPPER_LIMIT = -1;
   java.lang.String ptc_str_136;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_136() {
      return ptc_str_136;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_136(java.lang.String ptc_str_136) throws wt.util.WTPropertyVetoException {
      ptc_str_136Validate(ptc_str_136);
      this.ptc_str_136 = ptc_str_136;
   }
   void ptc_str_136Validate(java.lang.String ptc_str_136) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_136_UPPER_LIMIT < 1) {
         try { PTC_STR_136_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_136").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_136_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_136 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_136.toString(), PTC_STR_136_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_136"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_136_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_136", this.ptc_str_136, ptc_str_136));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_137 = "ptc_str_137";
   static int PTC_STR_137_UPPER_LIMIT = -1;
   java.lang.String ptc_str_137;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_137() {
      return ptc_str_137;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_137(java.lang.String ptc_str_137) throws wt.util.WTPropertyVetoException {
      ptc_str_137Validate(ptc_str_137);
      this.ptc_str_137 = ptc_str_137;
   }
   void ptc_str_137Validate(java.lang.String ptc_str_137) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_137_UPPER_LIMIT < 1) {
         try { PTC_STR_137_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_137").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_137_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_137 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_137.toString(), PTC_STR_137_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_137"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_137_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_137", this.ptc_str_137, ptc_str_137));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_138 = "ptc_str_138";
   static int PTC_STR_138_UPPER_LIMIT = -1;
   java.lang.String ptc_str_138;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_138() {
      return ptc_str_138;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_138(java.lang.String ptc_str_138) throws wt.util.WTPropertyVetoException {
      ptc_str_138Validate(ptc_str_138);
      this.ptc_str_138 = ptc_str_138;
   }
   void ptc_str_138Validate(java.lang.String ptc_str_138) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_138_UPPER_LIMIT < 1) {
         try { PTC_STR_138_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_138").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_138_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_138 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_138.toString(), PTC_STR_138_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_138"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_138_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_138", this.ptc_str_138, ptc_str_138));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_139 = "ptc_str_139";
   static int PTC_STR_139_UPPER_LIMIT = -1;
   java.lang.String ptc_str_139;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_139() {
      return ptc_str_139;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_139(java.lang.String ptc_str_139) throws wt.util.WTPropertyVetoException {
      ptc_str_139Validate(ptc_str_139);
      this.ptc_str_139 = ptc_str_139;
   }
   void ptc_str_139Validate(java.lang.String ptc_str_139) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_139_UPPER_LIMIT < 1) {
         try { PTC_STR_139_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_139").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_139_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_139 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_139.toString(), PTC_STR_139_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_139"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_139_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_139", this.ptc_str_139, ptc_str_139));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_140 = "ptc_str_140";
   static int PTC_STR_140_UPPER_LIMIT = -1;
   java.lang.String ptc_str_140;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_140() {
      return ptc_str_140;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_140(java.lang.String ptc_str_140) throws wt.util.WTPropertyVetoException {
      ptc_str_140Validate(ptc_str_140);
      this.ptc_str_140 = ptc_str_140;
   }
   void ptc_str_140Validate(java.lang.String ptc_str_140) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_140_UPPER_LIMIT < 1) {
         try { PTC_STR_140_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_140").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_140_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_140 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_140.toString(), PTC_STR_140_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_140"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_140_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_140", this.ptc_str_140, ptc_str_140));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_141 = "ptc_str_141";
   static int PTC_STR_141_UPPER_LIMIT = -1;
   java.lang.String ptc_str_141;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_141() {
      return ptc_str_141;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_141(java.lang.String ptc_str_141) throws wt.util.WTPropertyVetoException {
      ptc_str_141Validate(ptc_str_141);
      this.ptc_str_141 = ptc_str_141;
   }
   void ptc_str_141Validate(java.lang.String ptc_str_141) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_141_UPPER_LIMIT < 1) {
         try { PTC_STR_141_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_141").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_141_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_141 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_141.toString(), PTC_STR_141_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_141"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_141_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_141", this.ptc_str_141, ptc_str_141));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_142 = "ptc_str_142";
   static int PTC_STR_142_UPPER_LIMIT = -1;
   java.lang.String ptc_str_142;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_142() {
      return ptc_str_142;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_142(java.lang.String ptc_str_142) throws wt.util.WTPropertyVetoException {
      ptc_str_142Validate(ptc_str_142);
      this.ptc_str_142 = ptc_str_142;
   }
   void ptc_str_142Validate(java.lang.String ptc_str_142) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_142_UPPER_LIMIT < 1) {
         try { PTC_STR_142_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_142").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_142_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_142 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_142.toString(), PTC_STR_142_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_142"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_142_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_142", this.ptc_str_142, ptc_str_142));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_143 = "ptc_str_143";
   static int PTC_STR_143_UPPER_LIMIT = -1;
   java.lang.String ptc_str_143;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_143() {
      return ptc_str_143;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_143(java.lang.String ptc_str_143) throws wt.util.WTPropertyVetoException {
      ptc_str_143Validate(ptc_str_143);
      this.ptc_str_143 = ptc_str_143;
   }
   void ptc_str_143Validate(java.lang.String ptc_str_143) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_143_UPPER_LIMIT < 1) {
         try { PTC_STR_143_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_143").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_143_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_143 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_143.toString(), PTC_STR_143_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_143"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_143_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_143", this.ptc_str_143, ptc_str_143));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_144 = "ptc_str_144";
   static int PTC_STR_144_UPPER_LIMIT = -1;
   java.lang.String ptc_str_144;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_144() {
      return ptc_str_144;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_144(java.lang.String ptc_str_144) throws wt.util.WTPropertyVetoException {
      ptc_str_144Validate(ptc_str_144);
      this.ptc_str_144 = ptc_str_144;
   }
   void ptc_str_144Validate(java.lang.String ptc_str_144) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_144_UPPER_LIMIT < 1) {
         try { PTC_STR_144_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_144").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_144_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_144 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_144.toString(), PTC_STR_144_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_144"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_144_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_144", this.ptc_str_144, ptc_str_144));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_145 = "ptc_str_145";
   static int PTC_STR_145_UPPER_LIMIT = -1;
   java.lang.String ptc_str_145;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_145() {
      return ptc_str_145;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_145(java.lang.String ptc_str_145) throws wt.util.WTPropertyVetoException {
      ptc_str_145Validate(ptc_str_145);
      this.ptc_str_145 = ptc_str_145;
   }
   void ptc_str_145Validate(java.lang.String ptc_str_145) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_145_UPPER_LIMIT < 1) {
         try { PTC_STR_145_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_145").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_145_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_145 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_145.toString(), PTC_STR_145_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_145"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_145_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_145", this.ptc_str_145, ptc_str_145));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_146 = "ptc_str_146";
   static int PTC_STR_146_UPPER_LIMIT = -1;
   java.lang.String ptc_str_146;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_146() {
      return ptc_str_146;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_146(java.lang.String ptc_str_146) throws wt.util.WTPropertyVetoException {
      ptc_str_146Validate(ptc_str_146);
      this.ptc_str_146 = ptc_str_146;
   }
   void ptc_str_146Validate(java.lang.String ptc_str_146) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_146_UPPER_LIMIT < 1) {
         try { PTC_STR_146_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_146").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_146_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_146 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_146.toString(), PTC_STR_146_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_146"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_146_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_146", this.ptc_str_146, ptc_str_146));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_147 = "ptc_str_147";
   static int PTC_STR_147_UPPER_LIMIT = -1;
   java.lang.String ptc_str_147;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_147() {
      return ptc_str_147;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_147(java.lang.String ptc_str_147) throws wt.util.WTPropertyVetoException {
      ptc_str_147Validate(ptc_str_147);
      this.ptc_str_147 = ptc_str_147;
   }
   void ptc_str_147Validate(java.lang.String ptc_str_147) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_147_UPPER_LIMIT < 1) {
         try { PTC_STR_147_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_147").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_147_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_147 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_147.toString(), PTC_STR_147_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_147"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_147_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_147", this.ptc_str_147, ptc_str_147));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_148 = "ptc_str_148";
   static int PTC_STR_148_UPPER_LIMIT = -1;
   java.lang.String ptc_str_148;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_148() {
      return ptc_str_148;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_148(java.lang.String ptc_str_148) throws wt.util.WTPropertyVetoException {
      ptc_str_148Validate(ptc_str_148);
      this.ptc_str_148 = ptc_str_148;
   }
   void ptc_str_148Validate(java.lang.String ptc_str_148) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_148_UPPER_LIMIT < 1) {
         try { PTC_STR_148_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_148").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_148_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_148 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_148.toString(), PTC_STR_148_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_148"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_148_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_148", this.ptc_str_148, ptc_str_148));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_149 = "ptc_str_149";
   static int PTC_STR_149_UPPER_LIMIT = -1;
   java.lang.String ptc_str_149;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_149() {
      return ptc_str_149;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_149(java.lang.String ptc_str_149) throws wt.util.WTPropertyVetoException {
      ptc_str_149Validate(ptc_str_149);
      this.ptc_str_149 = ptc_str_149;
   }
   void ptc_str_149Validate(java.lang.String ptc_str_149) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_149_UPPER_LIMIT < 1) {
         try { PTC_STR_149_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_149").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_149_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_149 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_149.toString(), PTC_STR_149_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_149"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_149_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_149", this.ptc_str_149, ptc_str_149));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_150 = "ptc_str_150";
   static int PTC_STR_150_UPPER_LIMIT = -1;
   java.lang.String ptc_str_150;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_150() {
      return ptc_str_150;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_150(java.lang.String ptc_str_150) throws wt.util.WTPropertyVetoException {
      ptc_str_150Validate(ptc_str_150);
      this.ptc_str_150 = ptc_str_150;
   }
   void ptc_str_150Validate(java.lang.String ptc_str_150) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_150_UPPER_LIMIT < 1) {
         try { PTC_STR_150_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_150").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_150_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_150 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_150.toString(), PTC_STR_150_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_150"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_150_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_150", this.ptc_str_150, ptc_str_150));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_151 = "ptc_str_151";
   static int PTC_STR_151_UPPER_LIMIT = -1;
   java.lang.String ptc_str_151;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_151() {
      return ptc_str_151;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_151(java.lang.String ptc_str_151) throws wt.util.WTPropertyVetoException {
      ptc_str_151Validate(ptc_str_151);
      this.ptc_str_151 = ptc_str_151;
   }
   void ptc_str_151Validate(java.lang.String ptc_str_151) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_151_UPPER_LIMIT < 1) {
         try { PTC_STR_151_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_151").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_151_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_151 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_151.toString(), PTC_STR_151_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_151"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_151_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_151", this.ptc_str_151, ptc_str_151));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_152 = "ptc_str_152";
   static int PTC_STR_152_UPPER_LIMIT = -1;
   java.lang.String ptc_str_152;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_152() {
      return ptc_str_152;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_152(java.lang.String ptc_str_152) throws wt.util.WTPropertyVetoException {
      ptc_str_152Validate(ptc_str_152);
      this.ptc_str_152 = ptc_str_152;
   }
   void ptc_str_152Validate(java.lang.String ptc_str_152) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_152_UPPER_LIMIT < 1) {
         try { PTC_STR_152_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_152").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_152_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_152 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_152.toString(), PTC_STR_152_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_152"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_152_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_152", this.ptc_str_152, ptc_str_152));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_153 = "ptc_str_153";
   static int PTC_STR_153_UPPER_LIMIT = -1;
   java.lang.String ptc_str_153;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_153() {
      return ptc_str_153;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_153(java.lang.String ptc_str_153) throws wt.util.WTPropertyVetoException {
      ptc_str_153Validate(ptc_str_153);
      this.ptc_str_153 = ptc_str_153;
   }
   void ptc_str_153Validate(java.lang.String ptc_str_153) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_153_UPPER_LIMIT < 1) {
         try { PTC_STR_153_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_153").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_153_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_153 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_153.toString(), PTC_STR_153_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_153"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_153_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_153", this.ptc_str_153, ptc_str_153));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_154 = "ptc_str_154";
   static int PTC_STR_154_UPPER_LIMIT = -1;
   java.lang.String ptc_str_154;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_154() {
      return ptc_str_154;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_154(java.lang.String ptc_str_154) throws wt.util.WTPropertyVetoException {
      ptc_str_154Validate(ptc_str_154);
      this.ptc_str_154 = ptc_str_154;
   }
   void ptc_str_154Validate(java.lang.String ptc_str_154) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_154_UPPER_LIMIT < 1) {
         try { PTC_STR_154_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_154").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_154_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_154 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_154.toString(), PTC_STR_154_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_154"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_154_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_154", this.ptc_str_154, ptc_str_154));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_155 = "ptc_str_155";
   static int PTC_STR_155_UPPER_LIMIT = -1;
   java.lang.String ptc_str_155;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_155() {
      return ptc_str_155;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_155(java.lang.String ptc_str_155) throws wt.util.WTPropertyVetoException {
      ptc_str_155Validate(ptc_str_155);
      this.ptc_str_155 = ptc_str_155;
   }
   void ptc_str_155Validate(java.lang.String ptc_str_155) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_155_UPPER_LIMIT < 1) {
         try { PTC_STR_155_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_155").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_155_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_155 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_155.toString(), PTC_STR_155_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_155"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_155_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_155", this.ptc_str_155, ptc_str_155));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_156 = "ptc_str_156";
   static int PTC_STR_156_UPPER_LIMIT = -1;
   java.lang.String ptc_str_156;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_156() {
      return ptc_str_156;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_156(java.lang.String ptc_str_156) throws wt.util.WTPropertyVetoException {
      ptc_str_156Validate(ptc_str_156);
      this.ptc_str_156 = ptc_str_156;
   }
   void ptc_str_156Validate(java.lang.String ptc_str_156) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_156_UPPER_LIMIT < 1) {
         try { PTC_STR_156_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_156").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_156_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_156 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_156.toString(), PTC_STR_156_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_156"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_156_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_156", this.ptc_str_156, ptc_str_156));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_157 = "ptc_str_157";
   static int PTC_STR_157_UPPER_LIMIT = -1;
   java.lang.String ptc_str_157;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_157() {
      return ptc_str_157;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_157(java.lang.String ptc_str_157) throws wt.util.WTPropertyVetoException {
      ptc_str_157Validate(ptc_str_157);
      this.ptc_str_157 = ptc_str_157;
   }
   void ptc_str_157Validate(java.lang.String ptc_str_157) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_157_UPPER_LIMIT < 1) {
         try { PTC_STR_157_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_157").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_157_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_157 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_157.toString(), PTC_STR_157_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_157"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_157_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_157", this.ptc_str_157, ptc_str_157));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_158 = "ptc_str_158";
   static int PTC_STR_158_UPPER_LIMIT = -1;
   java.lang.String ptc_str_158;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_158() {
      return ptc_str_158;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_158(java.lang.String ptc_str_158) throws wt.util.WTPropertyVetoException {
      ptc_str_158Validate(ptc_str_158);
      this.ptc_str_158 = ptc_str_158;
   }
   void ptc_str_158Validate(java.lang.String ptc_str_158) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_158_UPPER_LIMIT < 1) {
         try { PTC_STR_158_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_158").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_158_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_158 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_158.toString(), PTC_STR_158_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_158"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_158_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_158", this.ptc_str_158, ptc_str_158));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_159 = "ptc_str_159";
   static int PTC_STR_159_UPPER_LIMIT = -1;
   java.lang.String ptc_str_159;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_159() {
      return ptc_str_159;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_159(java.lang.String ptc_str_159) throws wt.util.WTPropertyVetoException {
      ptc_str_159Validate(ptc_str_159);
      this.ptc_str_159 = ptc_str_159;
   }
   void ptc_str_159Validate(java.lang.String ptc_str_159) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_159_UPPER_LIMIT < 1) {
         try { PTC_STR_159_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_159").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_159_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_159 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_159.toString(), PTC_STR_159_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_159"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_159_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_159", this.ptc_str_159, ptc_str_159));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_160 = "ptc_str_160";
   static int PTC_STR_160_UPPER_LIMIT = -1;
   java.lang.String ptc_str_160;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_160() {
      return ptc_str_160;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_160(java.lang.String ptc_str_160) throws wt.util.WTPropertyVetoException {
      ptc_str_160Validate(ptc_str_160);
      this.ptc_str_160 = ptc_str_160;
   }
   void ptc_str_160Validate(java.lang.String ptc_str_160) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_160_UPPER_LIMIT < 1) {
         try { PTC_STR_160_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_160").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_160_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_160 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_160.toString(), PTC_STR_160_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_160"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_160_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_160", this.ptc_str_160, ptc_str_160));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_161 = "ptc_str_161";
   static int PTC_STR_161_UPPER_LIMIT = -1;
   java.lang.String ptc_str_161;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_161() {
      return ptc_str_161;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_161(java.lang.String ptc_str_161) throws wt.util.WTPropertyVetoException {
      ptc_str_161Validate(ptc_str_161);
      this.ptc_str_161 = ptc_str_161;
   }
   void ptc_str_161Validate(java.lang.String ptc_str_161) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_161_UPPER_LIMIT < 1) {
         try { PTC_STR_161_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_161").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_161_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_161 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_161.toString(), PTC_STR_161_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_161"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_161_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_161", this.ptc_str_161, ptc_str_161));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_162 = "ptc_str_162";
   static int PTC_STR_162_UPPER_LIMIT = -1;
   java.lang.String ptc_str_162;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_162() {
      return ptc_str_162;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_162(java.lang.String ptc_str_162) throws wt.util.WTPropertyVetoException {
      ptc_str_162Validate(ptc_str_162);
      this.ptc_str_162 = ptc_str_162;
   }
   void ptc_str_162Validate(java.lang.String ptc_str_162) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_162_UPPER_LIMIT < 1) {
         try { PTC_STR_162_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_162").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_162_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_162 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_162.toString(), PTC_STR_162_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_162"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_162_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_162", this.ptc_str_162, ptc_str_162));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_163 = "ptc_str_163";
   static int PTC_STR_163_UPPER_LIMIT = -1;
   java.lang.String ptc_str_163;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_163() {
      return ptc_str_163;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_163(java.lang.String ptc_str_163) throws wt.util.WTPropertyVetoException {
      ptc_str_163Validate(ptc_str_163);
      this.ptc_str_163 = ptc_str_163;
   }
   void ptc_str_163Validate(java.lang.String ptc_str_163) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_163_UPPER_LIMIT < 1) {
         try { PTC_STR_163_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_163").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_163_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_163 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_163.toString(), PTC_STR_163_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_163"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_163_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_163", this.ptc_str_163, ptc_str_163));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_164 = "ptc_str_164";
   static int PTC_STR_164_UPPER_LIMIT = -1;
   java.lang.String ptc_str_164;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_164() {
      return ptc_str_164;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_164(java.lang.String ptc_str_164) throws wt.util.WTPropertyVetoException {
      ptc_str_164Validate(ptc_str_164);
      this.ptc_str_164 = ptc_str_164;
   }
   void ptc_str_164Validate(java.lang.String ptc_str_164) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_164_UPPER_LIMIT < 1) {
         try { PTC_STR_164_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_164").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_164_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_164 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_164.toString(), PTC_STR_164_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_164"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_164_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_164", this.ptc_str_164, ptc_str_164));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_165 = "ptc_str_165";
   static int PTC_STR_165_UPPER_LIMIT = -1;
   java.lang.String ptc_str_165;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_165() {
      return ptc_str_165;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_165(java.lang.String ptc_str_165) throws wt.util.WTPropertyVetoException {
      ptc_str_165Validate(ptc_str_165);
      this.ptc_str_165 = ptc_str_165;
   }
   void ptc_str_165Validate(java.lang.String ptc_str_165) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_165_UPPER_LIMIT < 1) {
         try { PTC_STR_165_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_165").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_165_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_165 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_165.toString(), PTC_STR_165_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_165"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_165_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_165", this.ptc_str_165, ptc_str_165));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_166 = "ptc_str_166";
   static int PTC_STR_166_UPPER_LIMIT = -1;
   java.lang.String ptc_str_166;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_166() {
      return ptc_str_166;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_166(java.lang.String ptc_str_166) throws wt.util.WTPropertyVetoException {
      ptc_str_166Validate(ptc_str_166);
      this.ptc_str_166 = ptc_str_166;
   }
   void ptc_str_166Validate(java.lang.String ptc_str_166) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_166_UPPER_LIMIT < 1) {
         try { PTC_STR_166_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_166").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_166_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_166 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_166.toString(), PTC_STR_166_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_166"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_166_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_166", this.ptc_str_166, ptc_str_166));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_167 = "ptc_str_167";
   static int PTC_STR_167_UPPER_LIMIT = -1;
   java.lang.String ptc_str_167;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_167() {
      return ptc_str_167;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_167(java.lang.String ptc_str_167) throws wt.util.WTPropertyVetoException {
      ptc_str_167Validate(ptc_str_167);
      this.ptc_str_167 = ptc_str_167;
   }
   void ptc_str_167Validate(java.lang.String ptc_str_167) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_167_UPPER_LIMIT < 1) {
         try { PTC_STR_167_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_167").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_167_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_167 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_167.toString(), PTC_STR_167_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_167"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_167_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_167", this.ptc_str_167, ptc_str_167));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_168 = "ptc_str_168";
   static int PTC_STR_168_UPPER_LIMIT = -1;
   java.lang.String ptc_str_168;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_168() {
      return ptc_str_168;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_168(java.lang.String ptc_str_168) throws wt.util.WTPropertyVetoException {
      ptc_str_168Validate(ptc_str_168);
      this.ptc_str_168 = ptc_str_168;
   }
   void ptc_str_168Validate(java.lang.String ptc_str_168) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_168_UPPER_LIMIT < 1) {
         try { PTC_STR_168_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_168").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_168_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_168 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_168.toString(), PTC_STR_168_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_168"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_168_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_168", this.ptc_str_168, ptc_str_168));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_169 = "ptc_str_169";
   static int PTC_STR_169_UPPER_LIMIT = -1;
   java.lang.String ptc_str_169;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_169() {
      return ptc_str_169;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_169(java.lang.String ptc_str_169) throws wt.util.WTPropertyVetoException {
      ptc_str_169Validate(ptc_str_169);
      this.ptc_str_169 = ptc_str_169;
   }
   void ptc_str_169Validate(java.lang.String ptc_str_169) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_169_UPPER_LIMIT < 1) {
         try { PTC_STR_169_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_169").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_169_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_169 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_169.toString(), PTC_STR_169_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_169"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_169_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_169", this.ptc_str_169, ptc_str_169));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_170 = "ptc_str_170";
   static int PTC_STR_170_UPPER_LIMIT = -1;
   java.lang.String ptc_str_170;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_170() {
      return ptc_str_170;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_170(java.lang.String ptc_str_170) throws wt.util.WTPropertyVetoException {
      ptc_str_170Validate(ptc_str_170);
      this.ptc_str_170 = ptc_str_170;
   }
   void ptc_str_170Validate(java.lang.String ptc_str_170) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_170_UPPER_LIMIT < 1) {
         try { PTC_STR_170_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_170").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_170_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_170 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_170.toString(), PTC_STR_170_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_170"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_170_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_170", this.ptc_str_170, ptc_str_170));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_171 = "ptc_str_171";
   static int PTC_STR_171_UPPER_LIMIT = -1;
   java.lang.String ptc_str_171;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_171() {
      return ptc_str_171;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_171(java.lang.String ptc_str_171) throws wt.util.WTPropertyVetoException {
      ptc_str_171Validate(ptc_str_171);
      this.ptc_str_171 = ptc_str_171;
   }
   void ptc_str_171Validate(java.lang.String ptc_str_171) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_171_UPPER_LIMIT < 1) {
         try { PTC_STR_171_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_171").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_171_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_171 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_171.toString(), PTC_STR_171_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_171"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_171_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_171", this.ptc_str_171, ptc_str_171));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_172 = "ptc_str_172";
   static int PTC_STR_172_UPPER_LIMIT = -1;
   java.lang.String ptc_str_172;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_172() {
      return ptc_str_172;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_172(java.lang.String ptc_str_172) throws wt.util.WTPropertyVetoException {
      ptc_str_172Validate(ptc_str_172);
      this.ptc_str_172 = ptc_str_172;
   }
   void ptc_str_172Validate(java.lang.String ptc_str_172) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_172_UPPER_LIMIT < 1) {
         try { PTC_STR_172_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_172").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_172_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_172 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_172.toString(), PTC_STR_172_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_172"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_172_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_172", this.ptc_str_172, ptc_str_172));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_173 = "ptc_str_173";
   static int PTC_STR_173_UPPER_LIMIT = -1;
   java.lang.String ptc_str_173;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_173() {
      return ptc_str_173;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_173(java.lang.String ptc_str_173) throws wt.util.WTPropertyVetoException {
      ptc_str_173Validate(ptc_str_173);
      this.ptc_str_173 = ptc_str_173;
   }
   void ptc_str_173Validate(java.lang.String ptc_str_173) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_173_UPPER_LIMIT < 1) {
         try { PTC_STR_173_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_173").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_173_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_173 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_173.toString(), PTC_STR_173_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_173"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_173_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_173", this.ptc_str_173, ptc_str_173));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_174 = "ptc_str_174";
   static int PTC_STR_174_UPPER_LIMIT = -1;
   java.lang.String ptc_str_174;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_174() {
      return ptc_str_174;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_174(java.lang.String ptc_str_174) throws wt.util.WTPropertyVetoException {
      ptc_str_174Validate(ptc_str_174);
      this.ptc_str_174 = ptc_str_174;
   }
   void ptc_str_174Validate(java.lang.String ptc_str_174) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_174_UPPER_LIMIT < 1) {
         try { PTC_STR_174_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_174").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_174_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_174 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_174.toString(), PTC_STR_174_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_174"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_174_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_174", this.ptc_str_174, ptc_str_174));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_175 = "ptc_str_175";
   static int PTC_STR_175_UPPER_LIMIT = -1;
   java.lang.String ptc_str_175;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_175() {
      return ptc_str_175;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_175(java.lang.String ptc_str_175) throws wt.util.WTPropertyVetoException {
      ptc_str_175Validate(ptc_str_175);
      this.ptc_str_175 = ptc_str_175;
   }
   void ptc_str_175Validate(java.lang.String ptc_str_175) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_175_UPPER_LIMIT < 1) {
         try { PTC_STR_175_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_175").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_175_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_175 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_175.toString(), PTC_STR_175_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_175"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_175_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_175", this.ptc_str_175, ptc_str_175));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_176 = "ptc_str_176";
   static int PTC_STR_176_UPPER_LIMIT = -1;
   java.lang.String ptc_str_176;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_176() {
      return ptc_str_176;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_176(java.lang.String ptc_str_176) throws wt.util.WTPropertyVetoException {
      ptc_str_176Validate(ptc_str_176);
      this.ptc_str_176 = ptc_str_176;
   }
   void ptc_str_176Validate(java.lang.String ptc_str_176) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_176_UPPER_LIMIT < 1) {
         try { PTC_STR_176_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_176").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_176_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_176 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_176.toString(), PTC_STR_176_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_176"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_176_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_176", this.ptc_str_176, ptc_str_176));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_177 = "ptc_str_177";
   static int PTC_STR_177_UPPER_LIMIT = -1;
   java.lang.String ptc_str_177;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_177() {
      return ptc_str_177;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_177(java.lang.String ptc_str_177) throws wt.util.WTPropertyVetoException {
      ptc_str_177Validate(ptc_str_177);
      this.ptc_str_177 = ptc_str_177;
   }
   void ptc_str_177Validate(java.lang.String ptc_str_177) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_177_UPPER_LIMIT < 1) {
         try { PTC_STR_177_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_177").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_177_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_177 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_177.toString(), PTC_STR_177_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_177"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_177_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_177", this.ptc_str_177, ptc_str_177));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_178 = "ptc_str_178";
   static int PTC_STR_178_UPPER_LIMIT = -1;
   java.lang.String ptc_str_178;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_178() {
      return ptc_str_178;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_178(java.lang.String ptc_str_178) throws wt.util.WTPropertyVetoException {
      ptc_str_178Validate(ptc_str_178);
      this.ptc_str_178 = ptc_str_178;
   }
   void ptc_str_178Validate(java.lang.String ptc_str_178) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_178_UPPER_LIMIT < 1) {
         try { PTC_STR_178_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_178").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_178_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_178 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_178.toString(), PTC_STR_178_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_178"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_178_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_178", this.ptc_str_178, ptc_str_178));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_179 = "ptc_str_179";
   static int PTC_STR_179_UPPER_LIMIT = -1;
   java.lang.String ptc_str_179;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_179() {
      return ptc_str_179;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_179(java.lang.String ptc_str_179) throws wt.util.WTPropertyVetoException {
      ptc_str_179Validate(ptc_str_179);
      this.ptc_str_179 = ptc_str_179;
   }
   void ptc_str_179Validate(java.lang.String ptc_str_179) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_179_UPPER_LIMIT < 1) {
         try { PTC_STR_179_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_179").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_179_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_179 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_179.toString(), PTC_STR_179_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_179"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_179_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_179", this.ptc_str_179, ptc_str_179));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_180 = "ptc_str_180";
   static int PTC_STR_180_UPPER_LIMIT = -1;
   java.lang.String ptc_str_180;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_180() {
      return ptc_str_180;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_180(java.lang.String ptc_str_180) throws wt.util.WTPropertyVetoException {
      ptc_str_180Validate(ptc_str_180);
      this.ptc_str_180 = ptc_str_180;
   }
   void ptc_str_180Validate(java.lang.String ptc_str_180) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_180_UPPER_LIMIT < 1) {
         try { PTC_STR_180_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_180").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_180_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_180 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_180.toString(), PTC_STR_180_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_180"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_180_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_180", this.ptc_str_180, ptc_str_180));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_181 = "ptc_str_181";
   static int PTC_STR_181_UPPER_LIMIT = -1;
   java.lang.String ptc_str_181;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_181() {
      return ptc_str_181;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_181(java.lang.String ptc_str_181) throws wt.util.WTPropertyVetoException {
      ptc_str_181Validate(ptc_str_181);
      this.ptc_str_181 = ptc_str_181;
   }
   void ptc_str_181Validate(java.lang.String ptc_str_181) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_181_UPPER_LIMIT < 1) {
         try { PTC_STR_181_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_181").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_181_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_181 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_181.toString(), PTC_STR_181_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_181"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_181_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_181", this.ptc_str_181, ptc_str_181));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_182 = "ptc_str_182";
   static int PTC_STR_182_UPPER_LIMIT = -1;
   java.lang.String ptc_str_182;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_182() {
      return ptc_str_182;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_182(java.lang.String ptc_str_182) throws wt.util.WTPropertyVetoException {
      ptc_str_182Validate(ptc_str_182);
      this.ptc_str_182 = ptc_str_182;
   }
   void ptc_str_182Validate(java.lang.String ptc_str_182) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_182_UPPER_LIMIT < 1) {
         try { PTC_STR_182_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_182").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_182_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_182 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_182.toString(), PTC_STR_182_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_182"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_182_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_182", this.ptc_str_182, ptc_str_182));
   }

   /**
    * @see wt.part.WTPartTypeInfo
    */
   public static final java.lang.String PTC_STR_183 = "ptc_str_183";
   static int PTC_STR_183_UPPER_LIMIT = -1;
   java.lang.String ptc_str_183;
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public java.lang.String getPtc_str_183() {
      return ptc_str_183;
   }
   /**
    * @see wt.part.WTPartTypeInfo
    */
   public void setPtc_str_183(java.lang.String ptc_str_183) throws wt.util.WTPropertyVetoException {
      ptc_str_183Validate(ptc_str_183);
      this.ptc_str_183 = ptc_str_183;
   }
   void ptc_str_183Validate(java.lang.String ptc_str_183) throws wt.util.WTPropertyVetoException {
      if (PTC_STR_183_UPPER_LIMIT < 1) {
         try { PTC_STR_183_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ptc_str_183").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PTC_STR_183_UPPER_LIMIT = 1500; }
      }
      if (ptc_str_183 != null && !wt.fc.PersistenceHelper.checkStoredLength(ptc_str_183.toString(), PTC_STR_183_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ptc_str_183"), java.lang.String.valueOf(java.lang.Math.min(PTC_STR_183_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ptc_str_183", this.ptc_str_183, ptc_str_183));
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


   public static final long EXTERNALIZATION_VERSION_UID = -1734968642587573775L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( ptc_str_1 );
      output.writeObject( ptc_str_10 );
      output.writeObject( ptc_str_100 );
      output.writeObject( ptc_str_101 );
      output.writeObject( ptc_str_102 );
      output.writeObject( ptc_str_103 );
      output.writeObject( ptc_str_104 );
      output.writeObject( ptc_str_105 );
      output.writeObject( ptc_str_106 );
      output.writeObject( ptc_str_107 );
      output.writeObject( ptc_str_108 );
      output.writeObject( ptc_str_109 );
      output.writeObject( ptc_str_11 );
      output.writeObject( ptc_str_110 );
      output.writeObject( ptc_str_111 );
      output.writeObject( ptc_str_112 );
      output.writeObject( ptc_str_113 );
      output.writeObject( ptc_str_114 );
      output.writeObject( ptc_str_115 );
      output.writeObject( ptc_str_116 );
      output.writeObject( ptc_str_117 );
      output.writeObject( ptc_str_118 );
      output.writeObject( ptc_str_119 );
      output.writeObject( ptc_str_12 );
      output.writeObject( ptc_str_120 );
      output.writeObject( ptc_str_121 );
      output.writeObject( ptc_str_122 );
      output.writeObject( ptc_str_123 );
      output.writeObject( ptc_str_124 );
      output.writeObject( ptc_str_125 );
      output.writeObject( ptc_str_126 );
      output.writeObject( ptc_str_127 );
      output.writeObject( ptc_str_128 );
      output.writeObject( ptc_str_129 );
      output.writeObject( ptc_str_13 );
      output.writeObject( ptc_str_130 );
      output.writeObject( ptc_str_131 );
      output.writeObject( ptc_str_132 );
      output.writeObject( ptc_str_133 );
      output.writeObject( ptc_str_134 );
      output.writeObject( ptc_str_135 );
      output.writeObject( ptc_str_136 );
      output.writeObject( ptc_str_137 );
      output.writeObject( ptc_str_138 );
      output.writeObject( ptc_str_139 );
      output.writeObject( ptc_str_14 );
      output.writeObject( ptc_str_140 );
      output.writeObject( ptc_str_141 );
      output.writeObject( ptc_str_142 );
      output.writeObject( ptc_str_143 );
      output.writeObject( ptc_str_144 );
      output.writeObject( ptc_str_145 );
      output.writeObject( ptc_str_146 );
      output.writeObject( ptc_str_147 );
      output.writeObject( ptc_str_148 );
      output.writeObject( ptc_str_149 );
      output.writeObject( ptc_str_15 );
      output.writeObject( ptc_str_150 );
      output.writeObject( ptc_str_151 );
      output.writeObject( ptc_str_152 );
      output.writeObject( ptc_str_153 );
      output.writeObject( ptc_str_154 );
      output.writeObject( ptc_str_155 );
      output.writeObject( ptc_str_156 );
      output.writeObject( ptc_str_157 );
      output.writeObject( ptc_str_158 );
      output.writeObject( ptc_str_159 );
      output.writeObject( ptc_str_16 );
      output.writeObject( ptc_str_160 );
      output.writeObject( ptc_str_161 );
      output.writeObject( ptc_str_162 );
      output.writeObject( ptc_str_163 );
      output.writeObject( ptc_str_164 );
      output.writeObject( ptc_str_165 );
      output.writeObject( ptc_str_166 );
      output.writeObject( ptc_str_167 );
      output.writeObject( ptc_str_168 );
      output.writeObject( ptc_str_169 );
      output.writeObject( ptc_str_17 );
      output.writeObject( ptc_str_170 );
      output.writeObject( ptc_str_171 );
      output.writeObject( ptc_str_172 );
      output.writeObject( ptc_str_173 );
      output.writeObject( ptc_str_174 );
      output.writeObject( ptc_str_175 );
      output.writeObject( ptc_str_176 );
      output.writeObject( ptc_str_177 );
      output.writeObject( ptc_str_178 );
      output.writeObject( ptc_str_179 );
      output.writeObject( ptc_str_18 );
      output.writeObject( ptc_str_180 );
      output.writeObject( ptc_str_181 );
      output.writeObject( ptc_str_182 );
      output.writeObject( ptc_str_183 );
      output.writeObject( ptc_str_19 );
      output.writeObject( ptc_str_2 );
      output.writeObject( ptc_str_20 );
      output.writeObject( ptc_str_21 );
      output.writeObject( ptc_str_22 );
      output.writeObject( ptc_str_23 );
      output.writeObject( ptc_str_24 );
      output.writeObject( ptc_str_25 );
      output.writeObject( ptc_str_26 );
      output.writeObject( ptc_str_27 );
      output.writeObject( ptc_str_28 );
      output.writeObject( ptc_str_29 );
      output.writeObject( ptc_str_3 );
      output.writeObject( ptc_str_30 );
      output.writeObject( ptc_str_31 );
      output.writeObject( ptc_str_32 );
      output.writeObject( ptc_str_33 );
      output.writeObject( ptc_str_34 );
      output.writeObject( ptc_str_35 );
      output.writeObject( ptc_str_36 );
      output.writeObject( ptc_str_37 );
      output.writeObject( ptc_str_38 );
      output.writeObject( ptc_str_39 );
      output.writeObject( ptc_str_4 );
      output.writeObject( ptc_str_40 );
      output.writeObject( ptc_str_41 );
      output.writeObject( ptc_str_42 );
      output.writeObject( ptc_str_43 );
      output.writeObject( ptc_str_44 );
      output.writeObject( ptc_str_45 );
      output.writeObject( ptc_str_46 );
      output.writeObject( ptc_str_47 );
      output.writeObject( ptc_str_48 );
      output.writeObject( ptc_str_49 );
      output.writeObject( ptc_str_5 );
      output.writeObject( ptc_str_50 );
      output.writeObject( ptc_str_51 );
      output.writeObject( ptc_str_52 );
      output.writeObject( ptc_str_53 );
      output.writeObject( ptc_str_54 );
      output.writeObject( ptc_str_55 );
      output.writeObject( ptc_str_56 );
      output.writeObject( ptc_str_57 );
      output.writeObject( ptc_str_58 );
      output.writeObject( ptc_str_59 );
      output.writeObject( ptc_str_6 );
      output.writeObject( ptc_str_60 );
      output.writeObject( ptc_str_61 );
      output.writeObject( ptc_str_62 );
      output.writeObject( ptc_str_63 );
      output.writeObject( ptc_str_64 );
      output.writeObject( ptc_str_65 );
      output.writeObject( ptc_str_66 );
      output.writeObject( ptc_str_67 );
      output.writeObject( ptc_str_68 );
      output.writeObject( ptc_str_69 );
      output.writeObject( ptc_str_7 );
      output.writeObject( ptc_str_70 );
      output.writeObject( ptc_str_71 );
      output.writeObject( ptc_str_72 );
      output.writeObject( ptc_str_73 );
      output.writeObject( ptc_str_74 );
      output.writeObject( ptc_str_75 );
      output.writeObject( ptc_str_76 );
      output.writeObject( ptc_str_77 );
      output.writeObject( ptc_str_78 );
      output.writeObject( ptc_str_79 );
      output.writeObject( ptc_str_8 );
      output.writeObject( ptc_str_80 );
      output.writeObject( ptc_str_81 );
      output.writeObject( ptc_str_82 );
      output.writeObject( ptc_str_83 );
      output.writeObject( ptc_str_84 );
      output.writeObject( ptc_str_85 );
      output.writeObject( ptc_str_86 );
      output.writeObject( ptc_str_87 );
      output.writeObject( ptc_str_88 );
      output.writeObject( ptc_str_89 );
      output.writeObject( ptc_str_9 );
      output.writeObject( ptc_str_90 );
      output.writeObject( ptc_str_91 );
      output.writeObject( ptc_str_92 );
      output.writeObject( ptc_str_93 );
      output.writeObject( ptc_str_94 );
      output.writeObject( ptc_str_95 );
      output.writeObject( ptc_str_96 );
      output.writeObject( ptc_str_97 );
      output.writeObject( ptc_str_98 );
      output.writeObject( ptc_str_99 );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (wt.part.WTPartTypeInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "ptc_str_1", ptc_str_1 );
      output.setString( "ptc_str_10", ptc_str_10 );
      output.setString( "ptc_str_100", ptc_str_100 );
      output.setString( "ptc_str_101", ptc_str_101 );
      output.setString( "ptc_str_102", ptc_str_102 );
      output.setString( "ptc_str_103", ptc_str_103 );
      output.setString( "ptc_str_104", ptc_str_104 );
      output.setString( "ptc_str_105", ptc_str_105 );
      output.setString( "ptc_str_106", ptc_str_106 );
      output.setString( "ptc_str_107", ptc_str_107 );
      output.setString( "ptc_str_108", ptc_str_108 );
      output.setString( "ptc_str_109", ptc_str_109 );
      output.setString( "ptc_str_11", ptc_str_11 );
      output.setString( "ptc_str_110", ptc_str_110 );
      output.setString( "ptc_str_111", ptc_str_111 );
      output.setString( "ptc_str_112", ptc_str_112 );
      output.setString( "ptc_str_113", ptc_str_113 );
      output.setString( "ptc_str_114", ptc_str_114 );
      output.setString( "ptc_str_115", ptc_str_115 );
      output.setString( "ptc_str_116", ptc_str_116 );
      output.setString( "ptc_str_117", ptc_str_117 );
      output.setString( "ptc_str_118", ptc_str_118 );
      output.setString( "ptc_str_119", ptc_str_119 );
      output.setString( "ptc_str_12", ptc_str_12 );
      output.setString( "ptc_str_120", ptc_str_120 );
      output.setString( "ptc_str_121", ptc_str_121 );
      output.setString( "ptc_str_122", ptc_str_122 );
      output.setString( "ptc_str_123", ptc_str_123 );
      output.setString( "ptc_str_124", ptc_str_124 );
      output.setString( "ptc_str_125", ptc_str_125 );
      output.setString( "ptc_str_126", ptc_str_126 );
      output.setString( "ptc_str_127", ptc_str_127 );
      output.setString( "ptc_str_128", ptc_str_128 );
      output.setString( "ptc_str_129", ptc_str_129 );
      output.setString( "ptc_str_13", ptc_str_13 );
      output.setString( "ptc_str_130", ptc_str_130 );
      output.setString( "ptc_str_131", ptc_str_131 );
      output.setString( "ptc_str_132", ptc_str_132 );
      output.setString( "ptc_str_133", ptc_str_133 );
      output.setString( "ptc_str_134", ptc_str_134 );
      output.setString( "ptc_str_135", ptc_str_135 );
      output.setString( "ptc_str_136", ptc_str_136 );
      output.setString( "ptc_str_137", ptc_str_137 );
      output.setString( "ptc_str_138", ptc_str_138 );
      output.setString( "ptc_str_139", ptc_str_139 );
      output.setString( "ptc_str_14", ptc_str_14 );
      output.setString( "ptc_str_140", ptc_str_140 );
      output.setString( "ptc_str_141", ptc_str_141 );
      output.setString( "ptc_str_142", ptc_str_142 );
      output.setString( "ptc_str_143", ptc_str_143 );
      output.setString( "ptc_str_144", ptc_str_144 );
      output.setString( "ptc_str_145", ptc_str_145 );
      output.setString( "ptc_str_146", ptc_str_146 );
      output.setString( "ptc_str_147", ptc_str_147 );
      output.setString( "ptc_str_148", ptc_str_148 );
      output.setString( "ptc_str_149", ptc_str_149 );
      output.setString( "ptc_str_15", ptc_str_15 );
      output.setString( "ptc_str_150", ptc_str_150 );
      output.setString( "ptc_str_151", ptc_str_151 );
      output.setString( "ptc_str_152", ptc_str_152 );
      output.setString( "ptc_str_153", ptc_str_153 );
      output.setString( "ptc_str_154", ptc_str_154 );
      output.setString( "ptc_str_155", ptc_str_155 );
      output.setString( "ptc_str_156", ptc_str_156 );
      output.setString( "ptc_str_157", ptc_str_157 );
      output.setString( "ptc_str_158", ptc_str_158 );
      output.setString( "ptc_str_159", ptc_str_159 );
      output.setString( "ptc_str_16", ptc_str_16 );
      output.setString( "ptc_str_160", ptc_str_160 );
      output.setString( "ptc_str_161", ptc_str_161 );
      output.setString( "ptc_str_162", ptc_str_162 );
      output.setString( "ptc_str_163", ptc_str_163 );
      output.setString( "ptc_str_164", ptc_str_164 );
      output.setString( "ptc_str_165", ptc_str_165 );
      output.setString( "ptc_str_166", ptc_str_166 );
      output.setString( "ptc_str_167", ptc_str_167 );
      output.setString( "ptc_str_168", ptc_str_168 );
      output.setString( "ptc_str_169", ptc_str_169 );
      output.setString( "ptc_str_17", ptc_str_17 );
      output.setString( "ptc_str_170", ptc_str_170 );
      output.setString( "ptc_str_171", ptc_str_171 );
      output.setString( "ptc_str_172", ptc_str_172 );
      output.setString( "ptc_str_173", ptc_str_173 );
      output.setString( "ptc_str_174", ptc_str_174 );
      output.setString( "ptc_str_175", ptc_str_175 );
      output.setString( "ptc_str_176", ptc_str_176 );
      output.setString( "ptc_str_177", ptc_str_177 );
      output.setString( "ptc_str_178", ptc_str_178 );
      output.setString( "ptc_str_179", ptc_str_179 );
      output.setString( "ptc_str_18", ptc_str_18 );
      output.setString( "ptc_str_180", ptc_str_180 );
      output.setString( "ptc_str_181", ptc_str_181 );
      output.setString( "ptc_str_182", ptc_str_182 );
      output.setString( "ptc_str_183", ptc_str_183 );
      output.setString( "ptc_str_19", ptc_str_19 );
      output.setString( "ptc_str_2", ptc_str_2 );
      output.setString( "ptc_str_20", ptc_str_20 );
      output.setString( "ptc_str_21", ptc_str_21 );
      output.setString( "ptc_str_22", ptc_str_22 );
      output.setString( "ptc_str_23", ptc_str_23 );
      output.setString( "ptc_str_24", ptc_str_24 );
      output.setString( "ptc_str_25", ptc_str_25 );
      output.setString( "ptc_str_26", ptc_str_26 );
      output.setString( "ptc_str_27", ptc_str_27 );
      output.setString( "ptc_str_28", ptc_str_28 );
      output.setString( "ptc_str_29", ptc_str_29 );
      output.setString( "ptc_str_3", ptc_str_3 );
      output.setString( "ptc_str_30", ptc_str_30 );
      output.setString( "ptc_str_31", ptc_str_31 );
      output.setString( "ptc_str_32", ptc_str_32 );
      output.setString( "ptc_str_33", ptc_str_33 );
      output.setString( "ptc_str_34", ptc_str_34 );
      output.setString( "ptc_str_35", ptc_str_35 );
      output.setString( "ptc_str_36", ptc_str_36 );
      output.setString( "ptc_str_37", ptc_str_37 );
      output.setString( "ptc_str_38", ptc_str_38 );
      output.setString( "ptc_str_39", ptc_str_39 );
      output.setString( "ptc_str_4", ptc_str_4 );
      output.setString( "ptc_str_40", ptc_str_40 );
      output.setString( "ptc_str_41", ptc_str_41 );
      output.setString( "ptc_str_42", ptc_str_42 );
      output.setString( "ptc_str_43", ptc_str_43 );
      output.setString( "ptc_str_44", ptc_str_44 );
      output.setString( "ptc_str_45", ptc_str_45 );
      output.setString( "ptc_str_46", ptc_str_46 );
      output.setString( "ptc_str_47", ptc_str_47 );
      output.setString( "ptc_str_48", ptc_str_48 );
      output.setString( "ptc_str_49", ptc_str_49 );
      output.setString( "ptc_str_5", ptc_str_5 );
      output.setString( "ptc_str_50", ptc_str_50 );
      output.setString( "ptc_str_51", ptc_str_51 );
      output.setString( "ptc_str_52", ptc_str_52 );
      output.setString( "ptc_str_53", ptc_str_53 );
      output.setString( "ptc_str_54", ptc_str_54 );
      output.setString( "ptc_str_55", ptc_str_55 );
      output.setString( "ptc_str_56", ptc_str_56 );
      output.setString( "ptc_str_57", ptc_str_57 );
      output.setString( "ptc_str_58", ptc_str_58 );
      output.setString( "ptc_str_59", ptc_str_59 );
      output.setString( "ptc_str_6", ptc_str_6 );
      output.setString( "ptc_str_60", ptc_str_60 );
      output.setString( "ptc_str_61", ptc_str_61 );
      output.setString( "ptc_str_62", ptc_str_62 );
      output.setString( "ptc_str_63", ptc_str_63 );
      output.setString( "ptc_str_64", ptc_str_64 );
      output.setString( "ptc_str_65", ptc_str_65 );
      output.setString( "ptc_str_66", ptc_str_66 );
      output.setString( "ptc_str_67", ptc_str_67 );
      output.setString( "ptc_str_68", ptc_str_68 );
      output.setString( "ptc_str_69", ptc_str_69 );
      output.setString( "ptc_str_7", ptc_str_7 );
      output.setString( "ptc_str_70", ptc_str_70 );
      output.setString( "ptc_str_71", ptc_str_71 );
      output.setString( "ptc_str_72", ptc_str_72 );
      output.setString( "ptc_str_73", ptc_str_73 );
      output.setString( "ptc_str_74", ptc_str_74 );
      output.setString( "ptc_str_75", ptc_str_75 );
      output.setString( "ptc_str_76", ptc_str_76 );
      output.setString( "ptc_str_77", ptc_str_77 );
      output.setString( "ptc_str_78", ptc_str_78 );
      output.setString( "ptc_str_79", ptc_str_79 );
      output.setString( "ptc_str_8", ptc_str_8 );
      output.setString( "ptc_str_80", ptc_str_80 );
      output.setString( "ptc_str_81", ptc_str_81 );
      output.setString( "ptc_str_82", ptc_str_82 );
      output.setString( "ptc_str_83", ptc_str_83 );
      output.setString( "ptc_str_84", ptc_str_84 );
      output.setString( "ptc_str_85", ptc_str_85 );
      output.setString( "ptc_str_86", ptc_str_86 );
      output.setString( "ptc_str_87", ptc_str_87 );
      output.setString( "ptc_str_88", ptc_str_88 );
      output.setString( "ptc_str_89", ptc_str_89 );
      output.setString( "ptc_str_9", ptc_str_9 );
      output.setString( "ptc_str_90", ptc_str_90 );
      output.setString( "ptc_str_91", ptc_str_91 );
      output.setString( "ptc_str_92", ptc_str_92 );
      output.setString( "ptc_str_93", ptc_str_93 );
      output.setString( "ptc_str_94", ptc_str_94 );
      output.setString( "ptc_str_95", ptc_str_95 );
      output.setString( "ptc_str_96", ptc_str_96 );
      output.setString( "ptc_str_97", ptc_str_97 );
      output.setString( "ptc_str_98", ptc_str_98 );
      output.setString( "ptc_str_99", ptc_str_99 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      ptc_str_1 = input.getString( "ptc_str_1" );
      ptc_str_10 = input.getString( "ptc_str_10" );
      ptc_str_100 = input.getString( "ptc_str_100" );
      ptc_str_101 = input.getString( "ptc_str_101" );
      ptc_str_102 = input.getString( "ptc_str_102" );
      ptc_str_103 = input.getString( "ptc_str_103" );
      ptc_str_104 = input.getString( "ptc_str_104" );
      ptc_str_105 = input.getString( "ptc_str_105" );
      ptc_str_106 = input.getString( "ptc_str_106" );
      ptc_str_107 = input.getString( "ptc_str_107" );
      ptc_str_108 = input.getString( "ptc_str_108" );
      ptc_str_109 = input.getString( "ptc_str_109" );
      ptc_str_11 = input.getString( "ptc_str_11" );
      ptc_str_110 = input.getString( "ptc_str_110" );
      ptc_str_111 = input.getString( "ptc_str_111" );
      ptc_str_112 = input.getString( "ptc_str_112" );
      ptc_str_113 = input.getString( "ptc_str_113" );
      ptc_str_114 = input.getString( "ptc_str_114" );
      ptc_str_115 = input.getString( "ptc_str_115" );
      ptc_str_116 = input.getString( "ptc_str_116" );
      ptc_str_117 = input.getString( "ptc_str_117" );
      ptc_str_118 = input.getString( "ptc_str_118" );
      ptc_str_119 = input.getString( "ptc_str_119" );
      ptc_str_12 = input.getString( "ptc_str_12" );
      ptc_str_120 = input.getString( "ptc_str_120" );
      ptc_str_121 = input.getString( "ptc_str_121" );
      ptc_str_122 = input.getString( "ptc_str_122" );
      ptc_str_123 = input.getString( "ptc_str_123" );
      ptc_str_124 = input.getString( "ptc_str_124" );
      ptc_str_125 = input.getString( "ptc_str_125" );
      ptc_str_126 = input.getString( "ptc_str_126" );
      ptc_str_127 = input.getString( "ptc_str_127" );
      ptc_str_128 = input.getString( "ptc_str_128" );
      ptc_str_129 = input.getString( "ptc_str_129" );
      ptc_str_13 = input.getString( "ptc_str_13" );
      ptc_str_130 = input.getString( "ptc_str_130" );
      ptc_str_131 = input.getString( "ptc_str_131" );
      ptc_str_132 = input.getString( "ptc_str_132" );
      ptc_str_133 = input.getString( "ptc_str_133" );
      ptc_str_134 = input.getString( "ptc_str_134" );
      ptc_str_135 = input.getString( "ptc_str_135" );
      ptc_str_136 = input.getString( "ptc_str_136" );
      ptc_str_137 = input.getString( "ptc_str_137" );
      ptc_str_138 = input.getString( "ptc_str_138" );
      ptc_str_139 = input.getString( "ptc_str_139" );
      ptc_str_14 = input.getString( "ptc_str_14" );
      ptc_str_140 = input.getString( "ptc_str_140" );
      ptc_str_141 = input.getString( "ptc_str_141" );
      ptc_str_142 = input.getString( "ptc_str_142" );
      ptc_str_143 = input.getString( "ptc_str_143" );
      ptc_str_144 = input.getString( "ptc_str_144" );
      ptc_str_145 = input.getString( "ptc_str_145" );
      ptc_str_146 = input.getString( "ptc_str_146" );
      ptc_str_147 = input.getString( "ptc_str_147" );
      ptc_str_148 = input.getString( "ptc_str_148" );
      ptc_str_149 = input.getString( "ptc_str_149" );
      ptc_str_15 = input.getString( "ptc_str_15" );
      ptc_str_150 = input.getString( "ptc_str_150" );
      ptc_str_151 = input.getString( "ptc_str_151" );
      ptc_str_152 = input.getString( "ptc_str_152" );
      ptc_str_153 = input.getString( "ptc_str_153" );
      ptc_str_154 = input.getString( "ptc_str_154" );
      ptc_str_155 = input.getString( "ptc_str_155" );
      ptc_str_156 = input.getString( "ptc_str_156" );
      ptc_str_157 = input.getString( "ptc_str_157" );
      ptc_str_158 = input.getString( "ptc_str_158" );
      ptc_str_159 = input.getString( "ptc_str_159" );
      ptc_str_16 = input.getString( "ptc_str_16" );
      ptc_str_160 = input.getString( "ptc_str_160" );
      ptc_str_161 = input.getString( "ptc_str_161" );
      ptc_str_162 = input.getString( "ptc_str_162" );
      ptc_str_163 = input.getString( "ptc_str_163" );
      ptc_str_164 = input.getString( "ptc_str_164" );
      ptc_str_165 = input.getString( "ptc_str_165" );
      ptc_str_166 = input.getString( "ptc_str_166" );
      ptc_str_167 = input.getString( "ptc_str_167" );
      ptc_str_168 = input.getString( "ptc_str_168" );
      ptc_str_169 = input.getString( "ptc_str_169" );
      ptc_str_17 = input.getString( "ptc_str_17" );
      ptc_str_170 = input.getString( "ptc_str_170" );
      ptc_str_171 = input.getString( "ptc_str_171" );
      ptc_str_172 = input.getString( "ptc_str_172" );
      ptc_str_173 = input.getString( "ptc_str_173" );
      ptc_str_174 = input.getString( "ptc_str_174" );
      ptc_str_175 = input.getString( "ptc_str_175" );
      ptc_str_176 = input.getString( "ptc_str_176" );
      ptc_str_177 = input.getString( "ptc_str_177" );
      ptc_str_178 = input.getString( "ptc_str_178" );
      ptc_str_179 = input.getString( "ptc_str_179" );
      ptc_str_18 = input.getString( "ptc_str_18" );
      ptc_str_180 = input.getString( "ptc_str_180" );
      ptc_str_181 = input.getString( "ptc_str_181" );
      ptc_str_182 = input.getString( "ptc_str_182" );
      ptc_str_183 = input.getString( "ptc_str_183" );
      ptc_str_19 = input.getString( "ptc_str_19" );
      ptc_str_2 = input.getString( "ptc_str_2" );
      ptc_str_20 = input.getString( "ptc_str_20" );
      ptc_str_21 = input.getString( "ptc_str_21" );
      ptc_str_22 = input.getString( "ptc_str_22" );
      ptc_str_23 = input.getString( "ptc_str_23" );
      ptc_str_24 = input.getString( "ptc_str_24" );
      ptc_str_25 = input.getString( "ptc_str_25" );
      ptc_str_26 = input.getString( "ptc_str_26" );
      ptc_str_27 = input.getString( "ptc_str_27" );
      ptc_str_28 = input.getString( "ptc_str_28" );
      ptc_str_29 = input.getString( "ptc_str_29" );
      ptc_str_3 = input.getString( "ptc_str_3" );
      ptc_str_30 = input.getString( "ptc_str_30" );
      ptc_str_31 = input.getString( "ptc_str_31" );
      ptc_str_32 = input.getString( "ptc_str_32" );
      ptc_str_33 = input.getString( "ptc_str_33" );
      ptc_str_34 = input.getString( "ptc_str_34" );
      ptc_str_35 = input.getString( "ptc_str_35" );
      ptc_str_36 = input.getString( "ptc_str_36" );
      ptc_str_37 = input.getString( "ptc_str_37" );
      ptc_str_38 = input.getString( "ptc_str_38" );
      ptc_str_39 = input.getString( "ptc_str_39" );
      ptc_str_4 = input.getString( "ptc_str_4" );
      ptc_str_40 = input.getString( "ptc_str_40" );
      ptc_str_41 = input.getString( "ptc_str_41" );
      ptc_str_42 = input.getString( "ptc_str_42" );
      ptc_str_43 = input.getString( "ptc_str_43" );
      ptc_str_44 = input.getString( "ptc_str_44" );
      ptc_str_45 = input.getString( "ptc_str_45" );
      ptc_str_46 = input.getString( "ptc_str_46" );
      ptc_str_47 = input.getString( "ptc_str_47" );
      ptc_str_48 = input.getString( "ptc_str_48" );
      ptc_str_49 = input.getString( "ptc_str_49" );
      ptc_str_5 = input.getString( "ptc_str_5" );
      ptc_str_50 = input.getString( "ptc_str_50" );
      ptc_str_51 = input.getString( "ptc_str_51" );
      ptc_str_52 = input.getString( "ptc_str_52" );
      ptc_str_53 = input.getString( "ptc_str_53" );
      ptc_str_54 = input.getString( "ptc_str_54" );
      ptc_str_55 = input.getString( "ptc_str_55" );
      ptc_str_56 = input.getString( "ptc_str_56" );
      ptc_str_57 = input.getString( "ptc_str_57" );
      ptc_str_58 = input.getString( "ptc_str_58" );
      ptc_str_59 = input.getString( "ptc_str_59" );
      ptc_str_6 = input.getString( "ptc_str_6" );
      ptc_str_60 = input.getString( "ptc_str_60" );
      ptc_str_61 = input.getString( "ptc_str_61" );
      ptc_str_62 = input.getString( "ptc_str_62" );
      ptc_str_63 = input.getString( "ptc_str_63" );
      ptc_str_64 = input.getString( "ptc_str_64" );
      ptc_str_65 = input.getString( "ptc_str_65" );
      ptc_str_66 = input.getString( "ptc_str_66" );
      ptc_str_67 = input.getString( "ptc_str_67" );
      ptc_str_68 = input.getString( "ptc_str_68" );
      ptc_str_69 = input.getString( "ptc_str_69" );
      ptc_str_7 = input.getString( "ptc_str_7" );
      ptc_str_70 = input.getString( "ptc_str_70" );
      ptc_str_71 = input.getString( "ptc_str_71" );
      ptc_str_72 = input.getString( "ptc_str_72" );
      ptc_str_73 = input.getString( "ptc_str_73" );
      ptc_str_74 = input.getString( "ptc_str_74" );
      ptc_str_75 = input.getString( "ptc_str_75" );
      ptc_str_76 = input.getString( "ptc_str_76" );
      ptc_str_77 = input.getString( "ptc_str_77" );
      ptc_str_78 = input.getString( "ptc_str_78" );
      ptc_str_79 = input.getString( "ptc_str_79" );
      ptc_str_8 = input.getString( "ptc_str_8" );
      ptc_str_80 = input.getString( "ptc_str_80" );
      ptc_str_81 = input.getString( "ptc_str_81" );
      ptc_str_82 = input.getString( "ptc_str_82" );
      ptc_str_83 = input.getString( "ptc_str_83" );
      ptc_str_84 = input.getString( "ptc_str_84" );
      ptc_str_85 = input.getString( "ptc_str_85" );
      ptc_str_86 = input.getString( "ptc_str_86" );
      ptc_str_87 = input.getString( "ptc_str_87" );
      ptc_str_88 = input.getString( "ptc_str_88" );
      ptc_str_89 = input.getString( "ptc_str_89" );
      ptc_str_9 = input.getString( "ptc_str_9" );
      ptc_str_90 = input.getString( "ptc_str_90" );
      ptc_str_91 = input.getString( "ptc_str_91" );
      ptc_str_92 = input.getString( "ptc_str_92" );
      ptc_str_93 = input.getString( "ptc_str_93" );
      ptc_str_94 = input.getString( "ptc_str_94" );
      ptc_str_95 = input.getString( "ptc_str_95" );
      ptc_str_96 = input.getString( "ptc_str_96" );
      ptc_str_97 = input.getString( "ptc_str_97" );
      ptc_str_98 = input.getString( "ptc_str_98" );
      ptc_str_99 = input.getString( "ptc_str_99" );
   }

   boolean readVersion_1734968642587573775L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      ptc_str_1 = (java.lang.String) input.readObject();
      ptc_str_10 = (java.lang.String) input.readObject();
      ptc_str_100 = (java.lang.String) input.readObject();
      ptc_str_101 = (java.lang.String) input.readObject();
      ptc_str_102 = (java.lang.String) input.readObject();
      ptc_str_103 = (java.lang.String) input.readObject();
      ptc_str_104 = (java.lang.String) input.readObject();
      ptc_str_105 = (java.lang.String) input.readObject();
      ptc_str_106 = (java.lang.String) input.readObject();
      ptc_str_107 = (java.lang.String) input.readObject();
      ptc_str_108 = (java.lang.String) input.readObject();
      ptc_str_109 = (java.lang.String) input.readObject();
      ptc_str_11 = (java.lang.String) input.readObject();
      ptc_str_110 = (java.lang.String) input.readObject();
      ptc_str_111 = (java.lang.String) input.readObject();
      ptc_str_112 = (java.lang.String) input.readObject();
      ptc_str_113 = (java.lang.String) input.readObject();
      ptc_str_114 = (java.lang.String) input.readObject();
      ptc_str_115 = (java.lang.String) input.readObject();
      ptc_str_116 = (java.lang.String) input.readObject();
      ptc_str_117 = (java.lang.String) input.readObject();
      ptc_str_118 = (java.lang.String) input.readObject();
      ptc_str_119 = (java.lang.String) input.readObject();
      ptc_str_12 = (java.lang.String) input.readObject();
      ptc_str_120 = (java.lang.String) input.readObject();
      ptc_str_121 = (java.lang.String) input.readObject();
      ptc_str_122 = (java.lang.String) input.readObject();
      ptc_str_123 = (java.lang.String) input.readObject();
      ptc_str_124 = (java.lang.String) input.readObject();
      ptc_str_125 = (java.lang.String) input.readObject();
      ptc_str_126 = (java.lang.String) input.readObject();
      ptc_str_127 = (java.lang.String) input.readObject();
      ptc_str_128 = (java.lang.String) input.readObject();
      ptc_str_129 = (java.lang.String) input.readObject();
      ptc_str_13 = (java.lang.String) input.readObject();
      ptc_str_130 = (java.lang.String) input.readObject();
      ptc_str_131 = (java.lang.String) input.readObject();
      ptc_str_132 = (java.lang.String) input.readObject();
      ptc_str_133 = (java.lang.String) input.readObject();
      ptc_str_134 = (java.lang.String) input.readObject();
      ptc_str_135 = (java.lang.String) input.readObject();
      ptc_str_136 = (java.lang.String) input.readObject();
      ptc_str_137 = (java.lang.String) input.readObject();
      ptc_str_138 = (java.lang.String) input.readObject();
      ptc_str_139 = (java.lang.String) input.readObject();
      ptc_str_14 = (java.lang.String) input.readObject();
      ptc_str_140 = (java.lang.String) input.readObject();
      ptc_str_141 = (java.lang.String) input.readObject();
      ptc_str_142 = (java.lang.String) input.readObject();
      ptc_str_143 = (java.lang.String) input.readObject();
      ptc_str_144 = (java.lang.String) input.readObject();
      ptc_str_145 = (java.lang.String) input.readObject();
      ptc_str_146 = (java.lang.String) input.readObject();
      ptc_str_147 = (java.lang.String) input.readObject();
      ptc_str_148 = (java.lang.String) input.readObject();
      ptc_str_149 = (java.lang.String) input.readObject();
      ptc_str_15 = (java.lang.String) input.readObject();
      ptc_str_150 = (java.lang.String) input.readObject();
      ptc_str_151 = (java.lang.String) input.readObject();
      ptc_str_152 = (java.lang.String) input.readObject();
      ptc_str_153 = (java.lang.String) input.readObject();
      ptc_str_154 = (java.lang.String) input.readObject();
      ptc_str_155 = (java.lang.String) input.readObject();
      ptc_str_156 = (java.lang.String) input.readObject();
      ptc_str_157 = (java.lang.String) input.readObject();
      ptc_str_158 = (java.lang.String) input.readObject();
      ptc_str_159 = (java.lang.String) input.readObject();
      ptc_str_16 = (java.lang.String) input.readObject();
      ptc_str_160 = (java.lang.String) input.readObject();
      ptc_str_161 = (java.lang.String) input.readObject();
      ptc_str_162 = (java.lang.String) input.readObject();
      ptc_str_163 = (java.lang.String) input.readObject();
      ptc_str_164 = (java.lang.String) input.readObject();
      ptc_str_165 = (java.lang.String) input.readObject();
      ptc_str_166 = (java.lang.String) input.readObject();
      ptc_str_167 = (java.lang.String) input.readObject();
      ptc_str_168 = (java.lang.String) input.readObject();
      ptc_str_169 = (java.lang.String) input.readObject();
      ptc_str_17 = (java.lang.String) input.readObject();
      ptc_str_170 = (java.lang.String) input.readObject();
      ptc_str_171 = (java.lang.String) input.readObject();
      ptc_str_172 = (java.lang.String) input.readObject();
      ptc_str_173 = (java.lang.String) input.readObject();
      ptc_str_174 = (java.lang.String) input.readObject();
      ptc_str_175 = (java.lang.String) input.readObject();
      ptc_str_176 = (java.lang.String) input.readObject();
      ptc_str_177 = (java.lang.String) input.readObject();
      ptc_str_178 = (java.lang.String) input.readObject();
      ptc_str_179 = (java.lang.String) input.readObject();
      ptc_str_18 = (java.lang.String) input.readObject();
      ptc_str_180 = (java.lang.String) input.readObject();
      ptc_str_181 = (java.lang.String) input.readObject();
      ptc_str_182 = (java.lang.String) input.readObject();
      ptc_str_183 = (java.lang.String) input.readObject();
      ptc_str_19 = (java.lang.String) input.readObject();
      ptc_str_2 = (java.lang.String) input.readObject();
      ptc_str_20 = (java.lang.String) input.readObject();
      ptc_str_21 = (java.lang.String) input.readObject();
      ptc_str_22 = (java.lang.String) input.readObject();
      ptc_str_23 = (java.lang.String) input.readObject();
      ptc_str_24 = (java.lang.String) input.readObject();
      ptc_str_25 = (java.lang.String) input.readObject();
      ptc_str_26 = (java.lang.String) input.readObject();
      ptc_str_27 = (java.lang.String) input.readObject();
      ptc_str_28 = (java.lang.String) input.readObject();
      ptc_str_29 = (java.lang.String) input.readObject();
      ptc_str_3 = (java.lang.String) input.readObject();
      ptc_str_30 = (java.lang.String) input.readObject();
      ptc_str_31 = (java.lang.String) input.readObject();
      ptc_str_32 = (java.lang.String) input.readObject();
      ptc_str_33 = (java.lang.String) input.readObject();
      ptc_str_34 = (java.lang.String) input.readObject();
      ptc_str_35 = (java.lang.String) input.readObject();
      ptc_str_36 = (java.lang.String) input.readObject();
      ptc_str_37 = (java.lang.String) input.readObject();
      ptc_str_38 = (java.lang.String) input.readObject();
      ptc_str_39 = (java.lang.String) input.readObject();
      ptc_str_4 = (java.lang.String) input.readObject();
      ptc_str_40 = (java.lang.String) input.readObject();
      ptc_str_41 = (java.lang.String) input.readObject();
      ptc_str_42 = (java.lang.String) input.readObject();
      ptc_str_43 = (java.lang.String) input.readObject();
      ptc_str_44 = (java.lang.String) input.readObject();
      ptc_str_45 = (java.lang.String) input.readObject();
      ptc_str_46 = (java.lang.String) input.readObject();
      ptc_str_47 = (java.lang.String) input.readObject();
      ptc_str_48 = (java.lang.String) input.readObject();
      ptc_str_49 = (java.lang.String) input.readObject();
      ptc_str_5 = (java.lang.String) input.readObject();
      ptc_str_50 = (java.lang.String) input.readObject();
      ptc_str_51 = (java.lang.String) input.readObject();
      ptc_str_52 = (java.lang.String) input.readObject();
      ptc_str_53 = (java.lang.String) input.readObject();
      ptc_str_54 = (java.lang.String) input.readObject();
      ptc_str_55 = (java.lang.String) input.readObject();
      ptc_str_56 = (java.lang.String) input.readObject();
      ptc_str_57 = (java.lang.String) input.readObject();
      ptc_str_58 = (java.lang.String) input.readObject();
      ptc_str_59 = (java.lang.String) input.readObject();
      ptc_str_6 = (java.lang.String) input.readObject();
      ptc_str_60 = (java.lang.String) input.readObject();
      ptc_str_61 = (java.lang.String) input.readObject();
      ptc_str_62 = (java.lang.String) input.readObject();
      ptc_str_63 = (java.lang.String) input.readObject();
      ptc_str_64 = (java.lang.String) input.readObject();
      ptc_str_65 = (java.lang.String) input.readObject();
      ptc_str_66 = (java.lang.String) input.readObject();
      ptc_str_67 = (java.lang.String) input.readObject();
      ptc_str_68 = (java.lang.String) input.readObject();
      ptc_str_69 = (java.lang.String) input.readObject();
      ptc_str_7 = (java.lang.String) input.readObject();
      ptc_str_70 = (java.lang.String) input.readObject();
      ptc_str_71 = (java.lang.String) input.readObject();
      ptc_str_72 = (java.lang.String) input.readObject();
      ptc_str_73 = (java.lang.String) input.readObject();
      ptc_str_74 = (java.lang.String) input.readObject();
      ptc_str_75 = (java.lang.String) input.readObject();
      ptc_str_76 = (java.lang.String) input.readObject();
      ptc_str_77 = (java.lang.String) input.readObject();
      ptc_str_78 = (java.lang.String) input.readObject();
      ptc_str_79 = (java.lang.String) input.readObject();
      ptc_str_8 = (java.lang.String) input.readObject();
      ptc_str_80 = (java.lang.String) input.readObject();
      ptc_str_81 = (java.lang.String) input.readObject();
      ptc_str_82 = (java.lang.String) input.readObject();
      ptc_str_83 = (java.lang.String) input.readObject();
      ptc_str_84 = (java.lang.String) input.readObject();
      ptc_str_85 = (java.lang.String) input.readObject();
      ptc_str_86 = (java.lang.String) input.readObject();
      ptc_str_87 = (java.lang.String) input.readObject();
      ptc_str_88 = (java.lang.String) input.readObject();
      ptc_str_89 = (java.lang.String) input.readObject();
      ptc_str_9 = (java.lang.String) input.readObject();
      ptc_str_90 = (java.lang.String) input.readObject();
      ptc_str_91 = (java.lang.String) input.readObject();
      ptc_str_92 = (java.lang.String) input.readObject();
      ptc_str_93 = (java.lang.String) input.readObject();
      ptc_str_94 = (java.lang.String) input.readObject();
      ptc_str_95 = (java.lang.String) input.readObject();
      ptc_str_96 = (java.lang.String) input.readObject();
      ptc_str_97 = (java.lang.String) input.readObject();
      ptc_str_98 = (java.lang.String) input.readObject();
      ptc_str_99 = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( WTPartTypeInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1734968642587573775L( input, readSerialVersionUID, superDone );
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
