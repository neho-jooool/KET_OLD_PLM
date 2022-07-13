package e3ps.project.outputtype;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ModelPlan implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.outputtype.outputtypeResource";
   static final java.lang.String CLASSNAME = ModelPlan.class.getName();

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String MODEL_NAME = "modelName";
   static int MODEL_NAME_UPPER_LIMIT = -1;
   java.lang.String modelName;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getModelName() {
      return modelName;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setModelName(java.lang.String modelName) throws wt.util.WTPropertyVetoException {
      modelNameValidate(modelName);
      this.modelName = modelName;
   }
   void modelNameValidate(java.lang.String modelName) throws wt.util.WTPropertyVetoException {
      if (MODEL_NAME_UPPER_LIMIT < 1) {
         try { MODEL_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("modelName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MODEL_NAME_UPPER_LIMIT = 200; }
      }
      if (modelName != null && !wt.fc.PersistenceHelper.checkStoredLength(modelName.toString(), MODEL_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modelName"), java.lang.String.valueOf(java.lang.Math.min(MODEL_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "modelName", this.modelName, modelName));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD1 = "yield1";
   static int YIELD1_UPPER_LIMIT = -1;
   java.lang.String yield1;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield1() {
      return yield1;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield1(java.lang.String yield1) throws wt.util.WTPropertyVetoException {
      yield1Validate(yield1);
      this.yield1 = yield1;
   }
   void yield1Validate(java.lang.String yield1) throws wt.util.WTPropertyVetoException {
      if (YIELD1_UPPER_LIMIT < 1) {
         try { YIELD1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD1_UPPER_LIMIT = 200; }
      }
      if (yield1 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield1.toString(), YIELD1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield1"), java.lang.String.valueOf(java.lang.Math.min(YIELD1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield1", this.yield1, yield1));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD2 = "yield2";
   static int YIELD2_UPPER_LIMIT = -1;
   java.lang.String yield2;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield2() {
      return yield2;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield2(java.lang.String yield2) throws wt.util.WTPropertyVetoException {
      yield2Validate(yield2);
      this.yield2 = yield2;
   }
   void yield2Validate(java.lang.String yield2) throws wt.util.WTPropertyVetoException {
      if (YIELD2_UPPER_LIMIT < 1) {
         try { YIELD2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD2_UPPER_LIMIT = 200; }
      }
      if (yield2 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield2.toString(), YIELD2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield2"), java.lang.String.valueOf(java.lang.Math.min(YIELD2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield2", this.yield2, yield2));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD3 = "yield3";
   static int YIELD3_UPPER_LIMIT = -1;
   java.lang.String yield3;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield3() {
      return yield3;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield3(java.lang.String yield3) throws wt.util.WTPropertyVetoException {
      yield3Validate(yield3);
      this.yield3 = yield3;
   }
   void yield3Validate(java.lang.String yield3) throws wt.util.WTPropertyVetoException {
      if (YIELD3_UPPER_LIMIT < 1) {
         try { YIELD3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD3_UPPER_LIMIT = 200; }
      }
      if (yield3 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield3.toString(), YIELD3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield3"), java.lang.String.valueOf(java.lang.Math.min(YIELD3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield3", this.yield3, yield3));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD4 = "yield4";
   static int YIELD4_UPPER_LIMIT = -1;
   java.lang.String yield4;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield4() {
      return yield4;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield4(java.lang.String yield4) throws wt.util.WTPropertyVetoException {
      yield4Validate(yield4);
      this.yield4 = yield4;
   }
   void yield4Validate(java.lang.String yield4) throws wt.util.WTPropertyVetoException {
      if (YIELD4_UPPER_LIMIT < 1) {
         try { YIELD4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD4_UPPER_LIMIT = 200; }
      }
      if (yield4 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield4.toString(), YIELD4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield4"), java.lang.String.valueOf(java.lang.Math.min(YIELD4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield4", this.yield4, yield4));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD5 = "yield5";
   static int YIELD5_UPPER_LIMIT = -1;
   java.lang.String yield5;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield5() {
      return yield5;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield5(java.lang.String yield5) throws wt.util.WTPropertyVetoException {
      yield5Validate(yield5);
      this.yield5 = yield5;
   }
   void yield5Validate(java.lang.String yield5) throws wt.util.WTPropertyVetoException {
      if (YIELD5_UPPER_LIMIT < 1) {
         try { YIELD5_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield5").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD5_UPPER_LIMIT = 200; }
      }
      if (yield5 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield5.toString(), YIELD5_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield5"), java.lang.String.valueOf(java.lang.Math.min(YIELD5_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield5", this.yield5, yield5));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD6 = "yield6";
   static int YIELD6_UPPER_LIMIT = -1;
   java.lang.String yield6;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield6() {
      return yield6;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield6(java.lang.String yield6) throws wt.util.WTPropertyVetoException {
      yield6Validate(yield6);
      this.yield6 = yield6;
   }
   void yield6Validate(java.lang.String yield6) throws wt.util.WTPropertyVetoException {
      if (YIELD6_UPPER_LIMIT < 1) {
         try { YIELD6_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield6").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD6_UPPER_LIMIT = 200; }
      }
      if (yield6 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield6.toString(), YIELD6_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield6"), java.lang.String.valueOf(java.lang.Math.min(YIELD6_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield6", this.yield6, yield6));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD7 = "yield7";
   static int YIELD7_UPPER_LIMIT = -1;
   java.lang.String yield7;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield7() {
      return yield7;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield7(java.lang.String yield7) throws wt.util.WTPropertyVetoException {
      yield7Validate(yield7);
      this.yield7 = yield7;
   }
   void yield7Validate(java.lang.String yield7) throws wt.util.WTPropertyVetoException {
      if (YIELD7_UPPER_LIMIT < 1) {
         try { YIELD7_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield7").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD7_UPPER_LIMIT = 200; }
      }
      if (yield7 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield7.toString(), YIELD7_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield7"), java.lang.String.valueOf(java.lang.Math.min(YIELD7_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield7", this.yield7, yield7));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD8 = "yield8";
   static int YIELD8_UPPER_LIMIT = -1;
   java.lang.String yield8;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield8() {
      return yield8;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield8(java.lang.String yield8) throws wt.util.WTPropertyVetoException {
      yield8Validate(yield8);
      this.yield8 = yield8;
   }
   void yield8Validate(java.lang.String yield8) throws wt.util.WTPropertyVetoException {
      if (YIELD8_UPPER_LIMIT < 1) {
         try { YIELD8_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield8").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD8_UPPER_LIMIT = 200; }
      }
      if (yield8 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield8.toString(), YIELD8_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield8"), java.lang.String.valueOf(java.lang.Math.min(YIELD8_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield8", this.yield8, yield8));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD9 = "yield9";
   static int YIELD9_UPPER_LIMIT = -1;
   java.lang.String yield9;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield9() {
      return yield9;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield9(java.lang.String yield9) throws wt.util.WTPropertyVetoException {
      yield9Validate(yield9);
      this.yield9 = yield9;
   }
   void yield9Validate(java.lang.String yield9) throws wt.util.WTPropertyVetoException {
      if (YIELD9_UPPER_LIMIT < 1) {
         try { YIELD9_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield9").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD9_UPPER_LIMIT = 200; }
      }
      if (yield9 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield9.toString(), YIELD9_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield9"), java.lang.String.valueOf(java.lang.Math.min(YIELD9_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield9", this.yield9, yield9));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String YIELD10 = "yield10";
   static int YIELD10_UPPER_LIMIT = -1;
   java.lang.String yield10;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getYield10() {
      return yield10;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setYield10(java.lang.String yield10) throws wt.util.WTPropertyVetoException {
      yield10Validate(yield10);
      this.yield10 = yield10;
   }
   void yield10Validate(java.lang.String yield10) throws wt.util.WTPropertyVetoException {
      if (YIELD10_UPPER_LIMIT < 1) {
         try { YIELD10_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("yield10").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { YIELD10_UPPER_LIMIT = 200; }
      }
      if (yield10 != null && !wt.fc.PersistenceHelper.checkStoredLength(yield10.toString(), YIELD10_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "yield10"), java.lang.String.valueOf(java.lang.Math.min(YIELD10_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "yield10", this.yield10, yield10));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String TOTAL = "total";
   int total;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public int getTotal() {
      return total;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setTotal(int total) throws wt.util.WTPropertyVetoException {
      totalValidate(total);
      this.total = total;
   }
   void totalValidate(int total) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String SORT_NAME1 = "sortName1";
   java.sql.Timestamp sortName1;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.sql.Timestamp getSortName1() {
      return sortName1;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setSortName1(java.sql.Timestamp sortName1) throws wt.util.WTPropertyVetoException {
      sortName1Validate(sortName1);
      this.sortName1 = sortName1;
   }
   void sortName1Validate(java.sql.Timestamp sortName1) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String SORT_NAME2 = "sortName2";
   java.sql.Timestamp sortName2;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.sql.Timestamp getSortName2() {
      return sortName2;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setSortName2(java.sql.Timestamp sortName2) throws wt.util.WTPropertyVetoException {
      sortName2Validate(sortName2);
      this.sortName2 = sortName2;
   }
   void sortName2Validate(java.sql.Timestamp sortName2) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String SORT_NAME3 = "sortName3";
   java.sql.Timestamp sortName3;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.sql.Timestamp getSortName3() {
      return sortName3;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setSortName3(java.sql.Timestamp sortName3) throws wt.util.WTPropertyVetoException {
      sortName3Validate(sortName3);
      this.sortName3 = sortName3;
   }
   void sortName3Validate(java.sql.Timestamp sortName3) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String DESCRIPTION = "description";
   static int DESCRIPTION_UPPER_LIMIT = -1;
   java.lang.String description;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getDescription() {
      return description;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
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
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String PERSON = "person";
   static int PERSON_UPPER_LIMIT = -1;
   java.lang.String person;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getPerson() {
      return person;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setPerson(java.lang.String person) throws wt.util.WTPropertyVetoException {
      personValidate(person);
      this.person = person;
   }
   void personValidate(java.lang.String person) throws wt.util.WTPropertyVetoException {
      if (PERSON_UPPER_LIMIT < 1) {
         try { PERSON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("person").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PERSON_UPPER_LIMIT = 200; }
      }
      if (person != null && !wt.fc.PersistenceHelper.checkStoredLength(person.toString(), PERSON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "person"), java.lang.String.valueOf(java.lang.Math.min(PERSON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "person", this.person, person));
   }

   /**
    * 프로그램 No 기준
    *
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String PROGRAM_BASE_NO = "programBaseNo";
   static int PROGRAM_BASE_NO_UPPER_LIMIT = -1;
   java.lang.String programBaseNo;
   /**
    * 프로그램 No 기준
    *
    * @see e3ps.project.outputtype.ModelPlan
    */
   public java.lang.String getProgramBaseNo() {
      return programBaseNo;
   }
   /**
    * 프로그램 No 기준
    *
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setProgramBaseNo(java.lang.String programBaseNo) throws wt.util.WTPropertyVetoException {
      programBaseNoValidate(programBaseNo);
      this.programBaseNo = programBaseNo;
   }
   void programBaseNoValidate(java.lang.String programBaseNo) throws wt.util.WTPropertyVetoException {
      if (PROGRAM_BASE_NO_UPPER_LIMIT < 1) {
         try { PROGRAM_BASE_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("programBaseNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROGRAM_BASE_NO_UPPER_LIMIT = 200; }
      }
      if (programBaseNo != null && !wt.fc.PersistenceHelper.checkStoredLength(programBaseNo.toString(), PROGRAM_BASE_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "programBaseNo"), java.lang.String.valueOf(java.lang.Math.min(PROGRAM_BASE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "programBaseNo", this.programBaseNo, programBaseNo));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String FORM_TYPE = "formType";
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String FORM_TYPE_REFERENCE = "formTypeReference";
   wt.fc.ObjectReference formTypeReference;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public e3ps.common.code.NumberCode getFormType() {
      return (formTypeReference != null) ? (e3ps.common.code.NumberCode) formTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public wt.fc.ObjectReference getFormTypeReference() {
      return formTypeReference;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setFormType(e3ps.common.code.NumberCode the_formType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFormTypeReference(the_formType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_formType));
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setFormTypeReference(wt.fc.ObjectReference the_formTypeReference) throws wt.util.WTPropertyVetoException {
      formTypeReferenceValidate(the_formTypeReference);
      formTypeReference = (wt.fc.ObjectReference) the_formTypeReference;
   }
   void formTypeReferenceValidate(wt.fc.ObjectReference the_formTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_formTypeReference == null || the_formTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "formTypeReference") },
               new java.beans.PropertyChangeEvent(this, "formTypeReference", this.formTypeReference, formTypeReference));
      if (the_formTypeReference != null && the_formTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_formTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "formTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "formTypeReference", this.formTypeReference, formTypeReference));
   }

   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String CAR_TYPE = "carType";
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public static final java.lang.String CAR_TYPE_REFERENCE = "carTypeReference";
   wt.fc.ObjectReference carTypeReference;
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public e3ps.project.outputtype.OEMProjectType getCarType() {
      return (carTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) carTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public wt.fc.ObjectReference getCarTypeReference() {
      return carTypeReference;
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setCarType(e3ps.project.outputtype.OEMProjectType the_carType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCarTypeReference(the_carType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_carType));
   }
   /**
    * @see e3ps.project.outputtype.ModelPlan
    */
   public void setCarTypeReference(wt.fc.ObjectReference the_carTypeReference) throws wt.util.WTPropertyVetoException {
      carTypeReferenceValidate(the_carTypeReference);
      carTypeReference = (wt.fc.ObjectReference) the_carTypeReference;
   }
   void carTypeReferenceValidate(wt.fc.ObjectReference the_carTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_carTypeReference == null || the_carTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carTypeReference") },
               new java.beans.PropertyChangeEvent(this, "carTypeReference", this.carTypeReference, carTypeReference));
      if (the_carTypeReference != null && the_carTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_carTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "carTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "carTypeReference", this.carTypeReference, carTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -2111525192992371183L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( carTypeReference );
      output.writeObject( description );
      output.writeObject( formTypeReference );
      output.writeObject( modelName );
      output.writeObject( person );
      output.writeObject( programBaseNo );
      output.writeObject( sortName1 );
      output.writeObject( sortName2 );
      output.writeObject( sortName3 );
      output.writeObject( thePersistInfo );
      output.writeInt( total );
      output.writeObject( yield1 );
      output.writeObject( yield10 );
      output.writeObject( yield2 );
      output.writeObject( yield3 );
      output.writeObject( yield4 );
      output.writeObject( yield5 );
      output.writeObject( yield6 );
      output.writeObject( yield7 );
      output.writeObject( yield8 );
      output.writeObject( yield9 );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.outputtype.ModelPlan) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "description", description );
      output.writeObject( "formTypeReference", formTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "modelName", modelName );
      output.setString( "person", person );
      output.setString( "programBaseNo", programBaseNo );
      output.setTimestamp( "sortName1", sortName1 );
      output.setTimestamp( "sortName2", sortName2 );
      output.setTimestamp( "sortName3", sortName3 );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setInt( "total", total );
      output.setString( "yield1", yield1 );
      output.setString( "yield10", yield10 );
      output.setString( "yield2", yield2 );
      output.setString( "yield3", yield3 );
      output.setString( "yield4", yield4 );
      output.setString( "yield5", yield5 );
      output.setString( "yield6", yield6 );
      output.setString( "yield7", yield7 );
      output.setString( "yield8", yield8 );
      output.setString( "yield9", yield9 );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      carTypeReference = (wt.fc.ObjectReference) input.readObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      description = input.getString( "description" );
      formTypeReference = (wt.fc.ObjectReference) input.readObject( "formTypeReference", formTypeReference, wt.fc.ObjectReference.class, true );
      modelName = input.getString( "modelName" );
      person = input.getString( "person" );
      programBaseNo = input.getString( "programBaseNo" );
      sortName1 = input.getTimestamp( "sortName1" );
      sortName2 = input.getTimestamp( "sortName2" );
      sortName3 = input.getTimestamp( "sortName3" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      total = input.getInt( "total" );
      yield1 = input.getString( "yield1" );
      yield10 = input.getString( "yield10" );
      yield2 = input.getString( "yield2" );
      yield3 = input.getString( "yield3" );
      yield4 = input.getString( "yield4" );
      yield5 = input.getString( "yield5" );
      yield6 = input.getString( "yield6" );
      yield7 = input.getString( "yield7" );
      yield8 = input.getString( "yield8" );
      yield9 = input.getString( "yield9" );
   }

   boolean readVersion_2111525192992371183L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      carTypeReference = (wt.fc.ObjectReference) input.readObject();
      description = (java.lang.String) input.readObject();
      formTypeReference = (wt.fc.ObjectReference) input.readObject();
      modelName = (java.lang.String) input.readObject();
      person = (java.lang.String) input.readObject();
      programBaseNo = (java.lang.String) input.readObject();
      sortName1 = (java.sql.Timestamp) input.readObject();
      sortName2 = (java.sql.Timestamp) input.readObject();
      sortName3 = (java.sql.Timestamp) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      total = input.readInt();
      yield1 = (java.lang.String) input.readObject();
      yield10 = (java.lang.String) input.readObject();
      yield2 = (java.lang.String) input.readObject();
      yield3 = (java.lang.String) input.readObject();
      yield4 = (java.lang.String) input.readObject();
      yield5 = (java.lang.String) input.readObject();
      yield6 = (java.lang.String) input.readObject();
      yield7 = (java.lang.String) input.readObject();
      yield8 = (java.lang.String) input.readObject();
      yield9 = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( ModelPlan thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_2111525192992371183L( input, readSerialVersionUID, superDone );
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
