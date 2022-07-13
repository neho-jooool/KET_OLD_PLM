package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ModelInfo implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ModelInfo.class.getName();

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR1 = "year1";
   static int YEAR1_UPPER_LIMIT = -1;
   java.lang.String year1;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear1() {
      return year1;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear1(java.lang.String year1) throws wt.util.WTPropertyVetoException {
      year1Validate(year1);
      this.year1 = year1;
   }
   void year1Validate(java.lang.String year1) throws wt.util.WTPropertyVetoException {
      if (YEAR1_UPPER_LIMIT < 1) {
         try { YEAR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR1_UPPER_LIMIT = 200; }
      }
      if (year1 != null && !wt.fc.PersistenceHelper.checkStoredLength(year1.toString(), YEAR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year1"), java.lang.String.valueOf(java.lang.Math.min(YEAR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year1", this.year1, year1));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR2 = "year2";
   static int YEAR2_UPPER_LIMIT = -1;
   java.lang.String year2;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear2() {
      return year2;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear2(java.lang.String year2) throws wt.util.WTPropertyVetoException {
      year2Validate(year2);
      this.year2 = year2;
   }
   void year2Validate(java.lang.String year2) throws wt.util.WTPropertyVetoException {
      if (YEAR2_UPPER_LIMIT < 1) {
         try { YEAR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR2_UPPER_LIMIT = 200; }
      }
      if (year2 != null && !wt.fc.PersistenceHelper.checkStoredLength(year2.toString(), YEAR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year2"), java.lang.String.valueOf(java.lang.Math.min(YEAR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year2", this.year2, year2));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR3 = "year3";
   static int YEAR3_UPPER_LIMIT = -1;
   java.lang.String year3;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear3() {
      return year3;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear3(java.lang.String year3) throws wt.util.WTPropertyVetoException {
      year3Validate(year3);
      this.year3 = year3;
   }
   void year3Validate(java.lang.String year3) throws wt.util.WTPropertyVetoException {
      if (YEAR3_UPPER_LIMIT < 1) {
         try { YEAR3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR3_UPPER_LIMIT = 200; }
      }
      if (year3 != null && !wt.fc.PersistenceHelper.checkStoredLength(year3.toString(), YEAR3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year3"), java.lang.String.valueOf(java.lang.Math.min(YEAR3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year3", this.year3, year3));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR4 = "year4";
   static int YEAR4_UPPER_LIMIT = -1;
   java.lang.String year4;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear4() {
      return year4;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear4(java.lang.String year4) throws wt.util.WTPropertyVetoException {
      year4Validate(year4);
      this.year4 = year4;
   }
   void year4Validate(java.lang.String year4) throws wt.util.WTPropertyVetoException {
      if (YEAR4_UPPER_LIMIT < 1) {
         try { YEAR4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR4_UPPER_LIMIT = 200; }
      }
      if (year4 != null && !wt.fc.PersistenceHelper.checkStoredLength(year4.toString(), YEAR4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year4"), java.lang.String.valueOf(java.lang.Math.min(YEAR4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year4", this.year4, year4));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR5 = "year5";
   static int YEAR5_UPPER_LIMIT = -1;
   java.lang.String year5;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear5() {
      return year5;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear5(java.lang.String year5) throws wt.util.WTPropertyVetoException {
      year5Validate(year5);
      this.year5 = year5;
   }
   void year5Validate(java.lang.String year5) throws wt.util.WTPropertyVetoException {
      if (YEAR5_UPPER_LIMIT < 1) {
         try { YEAR5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR5_UPPER_LIMIT = 200; }
      }
      if (year5 != null && !wt.fc.PersistenceHelper.checkStoredLength(year5.toString(), YEAR5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year5"), java.lang.String.valueOf(java.lang.Math.min(YEAR5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year5", this.year5, year5));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR6 = "year6";
   static int YEAR6_UPPER_LIMIT = -1;
   java.lang.String year6;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear6() {
      return year6;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear6(java.lang.String year6) throws wt.util.WTPropertyVetoException {
      year6Validate(year6);
      this.year6 = year6;
   }
   void year6Validate(java.lang.String year6) throws wt.util.WTPropertyVetoException {
      if (YEAR6_UPPER_LIMIT < 1) {
         try { YEAR6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR6_UPPER_LIMIT = 200; }
      }
      if (year6 != null && !wt.fc.PersistenceHelper.checkStoredLength(year6.toString(), YEAR6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year6"), java.lang.String.valueOf(java.lang.Math.min(YEAR6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year6", this.year6, year6));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR7 = "year7";
   static int YEAR7_UPPER_LIMIT = -1;
   java.lang.String year7;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear7() {
      return year7;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear7(java.lang.String year7) throws wt.util.WTPropertyVetoException {
      year7Validate(year7);
      this.year7 = year7;
   }
   void year7Validate(java.lang.String year7) throws wt.util.WTPropertyVetoException {
      if (YEAR7_UPPER_LIMIT < 1) {
         try { YEAR7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR7_UPPER_LIMIT = 200; }
      }
      if (year7 != null && !wt.fc.PersistenceHelper.checkStoredLength(year7.toString(), YEAR7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year7"), java.lang.String.valueOf(java.lang.Math.min(YEAR7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year7", this.year7, year7));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR8 = "year8";
   static int YEAR8_UPPER_LIMIT = -1;
   java.lang.String year8;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear8() {
      return year8;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear8(java.lang.String year8) throws wt.util.WTPropertyVetoException {
      year8Validate(year8);
      this.year8 = year8;
   }
   void year8Validate(java.lang.String year8) throws wt.util.WTPropertyVetoException {
      if (YEAR8_UPPER_LIMIT < 1) {
         try { YEAR8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR8_UPPER_LIMIT = 200; }
      }
      if (year8 != null && !wt.fc.PersistenceHelper.checkStoredLength(year8.toString(), YEAR8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year8"), java.lang.String.valueOf(java.lang.Math.min(YEAR8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year8", this.year8, year8));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR9 = "year9";
   static int YEAR9_UPPER_LIMIT = -1;
   java.lang.String year9;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear9() {
      return year9;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear9(java.lang.String year9) throws wt.util.WTPropertyVetoException {
      year9Validate(year9);
      this.year9 = year9;
   }
   void year9Validate(java.lang.String year9) throws wt.util.WTPropertyVetoException {
      if (YEAR9_UPPER_LIMIT < 1) {
         try { YEAR9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR9_UPPER_LIMIT = 200; }
      }
      if (year9 != null && !wt.fc.PersistenceHelper.checkStoredLength(year9.toString(), YEAR9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year9"), java.lang.String.valueOf(java.lang.Math.min(YEAR9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year9", this.year9, year9));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String YEAR10 = "year10";
   static int YEAR10_UPPER_LIMIT = -1;
   java.lang.String year10;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getYear10() {
      return year10;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setYear10(java.lang.String year10) throws wt.util.WTPropertyVetoException {
      year10Validate(year10);
      this.year10 = year10;
   }
   void year10Validate(java.lang.String year10) throws wt.util.WTPropertyVetoException {
      if (YEAR10_UPPER_LIMIT < 1) {
         try { YEAR10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("year10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YEAR10_UPPER_LIMIT = 200; }
      }
      if (year10 != null && !wt.fc.PersistenceHelper.checkStoredLength(year10.toString(), YEAR10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "year10"), java.lang.String.valueOf(java.lang.Math.min(YEAR10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "year10", this.year10, year10));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String USAGE = "usage";
   static int USAGE_UPPER_LIMIT = -1;
   java.lang.String usage;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getUsage() {
      return usage;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setUsage(java.lang.String usage) throws wt.util.WTPropertyVetoException {
      usageValidate(usage);
      this.usage = usage;
   }
   void usageValidate(java.lang.String usage) throws wt.util.WTPropertyVetoException {
      if (USAGE_UPPER_LIMIT < 1) {
         try { USAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("usage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { USAGE_UPPER_LIMIT = 200; }
      }
      if (usage != null && !wt.fc.PersistenceHelper.checkStoredLength(usage.toString(), USAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "usage"), java.lang.String.valueOf(java.lang.Math.min(USAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "usage", this.usage, usage));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String OPTION_RATE = "optionRate";
   static int OPTION_RATE_UPPER_LIMIT = -1;
   java.lang.String optionRate;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getOptionRate() {
      return optionRate;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setOptionRate(java.lang.String optionRate) throws wt.util.WTPropertyVetoException {
      optionRateValidate(optionRate);
      this.optionRate = optionRate;
   }
   void optionRateValidate(java.lang.String optionRate) throws wt.util.WTPropertyVetoException {
      if (OPTION_RATE_UPPER_LIMIT < 1) {
         try { OPTION_RATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("optionRate").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OPTION_RATE_UPPER_LIMIT = 200; }
      }
      if (optionRate != null && !wt.fc.PersistenceHelper.checkStoredLength(optionRate.toString(), OPTION_RATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "optionRate"), java.lang.String.valueOf(java.lang.Math.min(OPTION_RATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "optionRate", this.optionRate, optionRate));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see e3ps.project.ModelInfo
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setName(java.lang.String name) throws wt.util.WTPropertyVetoException {
      nameValidate(name);
      this.name = name;
   }
   void nameValidate(java.lang.String name) throws wt.util.WTPropertyVetoException {
      if (NAME_UPPER_LIMIT < 1) {
         try { NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("name").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAME_UPPER_LIMIT = 200; }
      }
      if (name != null && !wt.fc.PersistenceHelper.checkStoredLength(name.toString(), NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "name"), java.lang.String.valueOf(java.lang.Math.min(NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "name", this.name, name));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String MODEL = "model";
   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String MODEL_REFERENCE = "modelReference";
   wt.fc.ObjectReference modelReference;
   /**
    * @see e3ps.project.ModelInfo
    */
   public e3ps.project.outputtype.OEMProjectType getModel() {
      return (modelReference != null) ? (e3ps.project.outputtype.OEMProjectType) modelReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public wt.fc.ObjectReference getModelReference() {
      return modelReference;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setModel(e3ps.project.outputtype.OEMProjectType the_model) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setModelReference(the_model == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_model));
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setModelReference(wt.fc.ObjectReference the_modelReference) throws wt.util.WTPropertyVetoException {
      modelReferenceValidate(the_modelReference);
      modelReference = (wt.fc.ObjectReference) the_modelReference;
   }
   void modelReferenceValidate(wt.fc.ObjectReference the_modelReference) throws wt.util.WTPropertyVetoException {
      if (the_modelReference == null || the_modelReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modelReference") },
               new java.beans.PropertyChangeEvent(this, "modelReference", this.modelReference, modelReference));
      if (the_modelReference != null && the_modelReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_modelReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modelReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "modelReference", this.modelReference, modelReference));
   }

   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String PRODUCT = "product";
   /**
    * @see e3ps.project.ModelInfo
    */
   public static final java.lang.String PRODUCT_REFERENCE = "productReference";
   wt.fc.ObjectReference productReference;
   /**
    * @see e3ps.project.ModelInfo
    */
   public e3ps.project.ProductInfo getProduct() {
      return (productReference != null) ? (e3ps.project.ProductInfo) productReference.getObject() : null;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public wt.fc.ObjectReference getProductReference() {
      return productReference;
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setProduct(e3ps.project.ProductInfo the_product) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProductReference(the_product == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProductInfo) the_product));
   }
   /**
    * @see e3ps.project.ModelInfo
    */
   public void setProductReference(wt.fc.ObjectReference the_productReference) throws wt.util.WTPropertyVetoException {
      productReferenceValidate(the_productReference);
      productReference = (wt.fc.ObjectReference) the_productReference;
   }
   void productReferenceValidate(wt.fc.ObjectReference the_productReference) throws wt.util.WTPropertyVetoException {
      if (the_productReference == null || the_productReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productReference") },
               new java.beans.PropertyChangeEvent(this, "productReference", this.productReference, productReference));
      if (the_productReference != null && the_productReference.getReferencedClass() != null &&
            !e3ps.project.ProductInfo.class.isAssignableFrom(the_productReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "productReference", this.productReference, productReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 3526337241145265290L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( modelReference );
      output.writeObject( name );
      output.writeObject( optionRate );
      output.writeObject( productReference );
      output.writeObject( thePersistInfo );
      output.writeObject( usage );
      output.writeObject( year1 );
      output.writeObject( year10 );
      output.writeObject( year2 );
      output.writeObject( year3 );
      output.writeObject( year4 );
      output.writeObject( year5 );
      output.writeObject( year6 );
      output.writeObject( year7 );
      output.writeObject( year8 );
      output.writeObject( year9 );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ModelInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "modelReference", modelReference, wt.fc.ObjectReference.class, true );
      output.setString( "name", name );
      output.setString( "optionRate", optionRate );
      output.writeObject( "productReference", productReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "usage", usage );
      output.setString( "year1", year1 );
      output.setString( "year10", year10 );
      output.setString( "year2", year2 );
      output.setString( "year3", year3 );
      output.setString( "year4", year4 );
      output.setString( "year5", year5 );
      output.setString( "year6", year6 );
      output.setString( "year7", year7 );
      output.setString( "year8", year8 );
      output.setString( "year9", year9 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      modelReference = (wt.fc.ObjectReference) input.readObject( "modelReference", modelReference, wt.fc.ObjectReference.class, true );
      name = input.getString( "name" );
      optionRate = input.getString( "optionRate" );
      productReference = (wt.fc.ObjectReference) input.readObject( "productReference", productReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      usage = input.getString( "usage" );
      year1 = input.getString( "year1" );
      year10 = input.getString( "year10" );
      year2 = input.getString( "year2" );
      year3 = input.getString( "year3" );
      year4 = input.getString( "year4" );
      year5 = input.getString( "year5" );
      year6 = input.getString( "year6" );
      year7 = input.getString( "year7" );
      year8 = input.getString( "year8" );
      year9 = input.getString( "year9" );
   }

   boolean readVersion3526337241145265290L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      modelReference = (wt.fc.ObjectReference) input.readObject();
      name = (java.lang.String) input.readObject();
      optionRate = (java.lang.String) input.readObject();
      productReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      usage = (java.lang.String) input.readObject();
      year1 = (java.lang.String) input.readObject();
      year10 = (java.lang.String) input.readObject();
      year2 = (java.lang.String) input.readObject();
      year3 = (java.lang.String) input.readObject();
      year4 = (java.lang.String) input.readObject();
      year5 = (java.lang.String) input.readObject();
      year6 = (java.lang.String) input.readObject();
      year7 = (java.lang.String) input.readObject();
      year8 = (java.lang.String) input.readObject();
      year9 = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ModelInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion3526337241145265290L( input, readSerialVersionUID, superDone );
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
