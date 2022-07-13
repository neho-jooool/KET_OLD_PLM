package e3ps.project.moldPart;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MoldPartManager implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.moldPart.moldPartResource";
   static final java.lang.String CLASSNAME = MoldPartManager.class.getName();

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String DIE_NO = "dieNo";
   static int DIE_NO_UPPER_LIMIT = -1;
   java.lang.String dieNo;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getDieNo() {
      return dieNo;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
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
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String ECO_NO = "ecoNo";
   static int ECO_NO_UPPER_LIMIT = -1;
   java.lang.String ecoNo;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getEcoNo() {
      return ecoNo;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setEcoNo(java.lang.String ecoNo) throws wt.util.WTPropertyVetoException {
      ecoNoValidate(ecoNo);
      this.ecoNo = ecoNo;
   }
   void ecoNoValidate(java.lang.String ecoNo) throws wt.util.WTPropertyVetoException {
      if (ECO_NO_UPPER_LIMIT < 1) {
         try { ECO_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ecoNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ECO_NO_UPPER_LIMIT = 200; }
      }
      if (ecoNo != null && !wt.fc.PersistenceHelper.checkStoredLength(ecoNo.toString(), ECO_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ecoNo"), java.lang.String.valueOf(java.lang.Math.min(ECO_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ecoNo", this.ecoNo, ecoNo));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String LEVEL_TYPE = "levelType";
   static int LEVEL_TYPE_UPPER_LIMIT = -1;
   java.lang.String levelType;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getLevelType() {
      return levelType;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setLevelType(java.lang.String levelType) throws wt.util.WTPropertyVetoException {
      levelTypeValidate(levelType);
      this.levelType = levelType;
   }
   void levelTypeValidate(java.lang.String levelType) throws wt.util.WTPropertyVetoException {
      if (LEVEL_TYPE_UPPER_LIMIT < 1) {
         try { LEVEL_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("levelType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LEVEL_TYPE_UPPER_LIMIT = 200; }
      }
      if (levelType != null && !wt.fc.PersistenceHelper.checkStoredLength(levelType.toString(), LEVEL_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "levelType"), java.lang.String.valueOf(java.lang.Math.min(LEVEL_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "levelType", this.levelType, levelType));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String CREATE_TYPE = "createType";
   static int CREATE_TYPE_UPPER_LIMIT = -1;
   java.lang.String createType;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getCreateType() {
      return createType;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setCreateType(java.lang.String createType) throws wt.util.WTPropertyVetoException {
      createTypeValidate(createType);
      this.createType = createType;
   }
   void createTypeValidate(java.lang.String createType) throws wt.util.WTPropertyVetoException {
      if (CREATE_TYPE_UPPER_LIMIT < 1) {
         try { CREATE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("createType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATE_TYPE_UPPER_LIMIT = 200; }
      }
      if (createType != null && !wt.fc.PersistenceHelper.checkStoredLength(createType.toString(), CREATE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "createType"), java.lang.String.valueOf(java.lang.Math.min(CREATE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "createType", this.createType, createType));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String MOLD_DATE = "moldDate";
   java.sql.Timestamp moldDate;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.sql.Timestamp getMoldDate() {
      return moldDate;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setMoldDate(java.sql.Timestamp moldDate) throws wt.util.WTPropertyVetoException {
      moldDateValidate(moldDate);
      this.moldDate = moldDate;
   }
   void moldDateValidate(java.sql.Timestamp moldDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String LEVEL_MOLD = "levelMold";
   static int LEVEL_MOLD_UPPER_LIMIT = -1;
   java.lang.String levelMold;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getLevelMold() {
      return levelMold;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setLevelMold(java.lang.String levelMold) throws wt.util.WTPropertyVetoException {
      levelMoldValidate(levelMold);
      this.levelMold = levelMold;
   }
   void levelMoldValidate(java.lang.String levelMold) throws wt.util.WTPropertyVetoException {
      if (LEVEL_MOLD_UPPER_LIMIT < 1) {
         try { LEVEL_MOLD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("levelMold").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { LEVEL_MOLD_UPPER_LIMIT = 200; }
      }
      if (levelMold != null && !wt.fc.PersistenceHelper.checkStoredLength(levelMold.toString(), LEVEL_MOLD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "levelMold"), java.lang.String.valueOf(java.lang.Math.min(LEVEL_MOLD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "levelMold", this.levelMold, levelMold));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String MOLD_STATE = "moldState";
   static int MOLD_STATE_UPPER_LIMIT = -1;
   java.lang.String moldState;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getMoldState() {
      return moldState;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setMoldState(java.lang.String moldState) throws wt.util.WTPropertyVetoException {
      moldStateValidate(moldState);
      this.moldState = moldState;
   }
   void moldStateValidate(java.lang.String moldState) throws wt.util.WTPropertyVetoException {
      if (MOLD_STATE_UPPER_LIMIT < 1) {
         try { MOLD_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_STATE_UPPER_LIMIT = 200; }
      }
      if (moldState != null && !wt.fc.PersistenceHelper.checkStoredLength(moldState.toString(), MOLD_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldState"), java.lang.String.valueOf(java.lang.Math.min(MOLD_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldState", this.moldState, moldState));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String SORT_NO = "sortNo";
   static int SORT_NO_UPPER_LIMIT = -1;
   java.lang.String sortNo;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getSortNo() {
      return sortNo;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setSortNo(java.lang.String sortNo) throws wt.util.WTPropertyVetoException {
      sortNoValidate(sortNo);
      this.sortNo = sortNo;
   }
   void sortNoValidate(java.lang.String sortNo) throws wt.util.WTPropertyVetoException {
      if (SORT_NO_UPPER_LIMIT < 1) {
         try { SORT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("sortNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SORT_NO_UPPER_LIMIT = 200; }
      }
      if (sortNo != null && !wt.fc.PersistenceHelper.checkStoredLength(sortNo.toString(), SORT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sortNo"), java.lang.String.valueOf(java.lang.Math.min(SORT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "sortNo", this.sortNo, sortNo));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String ATTR1 = "attr1";
   static int ATTR1_UPPER_LIMIT = -1;
   java.lang.String attr1;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getAttr1() {
      return attr1;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setAttr1(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      attr1Validate(attr1);
      this.attr1 = attr1;
   }
   void attr1Validate(java.lang.String attr1) throws wt.util.WTPropertyVetoException {
      if (ATTR1_UPPER_LIMIT < 1) {
         try { ATTR1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR1_UPPER_LIMIT = 200; }
      }
      if (attr1 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr1.toString(), ATTR1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr1"), java.lang.String.valueOf(java.lang.Math.min(ATTR1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr1", this.attr1, attr1));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String ATTR2 = "attr2";
   static int ATTR2_UPPER_LIMIT = -1;
   java.lang.String attr2;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getAttr2() {
      return attr2;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setAttr2(java.lang.String attr2) throws wt.util.WTPropertyVetoException {
      attr2Validate(attr2);
      this.attr2 = attr2;
   }
   void attr2Validate(java.lang.String attr2) throws wt.util.WTPropertyVetoException {
      if (ATTR2_UPPER_LIMIT < 1) {
         try { ATTR2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR2_UPPER_LIMIT = 200; }
      }
      if (attr2 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr2.toString(), ATTR2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr2"), java.lang.String.valueOf(java.lang.Math.min(ATTR2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr2", this.attr2, attr2));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String ATTR3 = "attr3";
   static int ATTR3_UPPER_LIMIT = -1;
   java.lang.String attr3;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.lang.String getAttr3() {
      return attr3;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setAttr3(java.lang.String attr3) throws wt.util.WTPropertyVetoException {
      attr3Validate(attr3);
      this.attr3 = attr3;
   }
   void attr3Validate(java.lang.String attr3) throws wt.util.WTPropertyVetoException {
      if (ATTR3_UPPER_LIMIT < 1) {
         try { ATTR3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("attr3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ATTR3_UPPER_LIMIT = 200; }
      }
      if (attr3 != null && !wt.fc.PersistenceHelper.checkStoredLength(attr3.toString(), ATTR3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "attr3"), java.lang.String.valueOf(java.lang.Math.min(ATTR3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "attr3", this.attr3, attr3));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String END_DATE = "endDate";
   java.sql.Timestamp endDate;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public java.sql.Timestamp getEndDate() {
      return endDate;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setEndDate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String CHA = "cha";
   int cha;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public int getCha() {
      return cha;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setCha(int cha) throws wt.util.WTPropertyVetoException {
      chaValidate(cha);
      this.cha = cha;
   }
   void chaValidate(int cha) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String SUB_CHA = "subCha";
   int subCha;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public int getSubCha() {
      return subCha;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setSubCha(int subCha) throws wt.util.WTPropertyVetoException {
      subChaValidate(subCha);
      this.subCha = subCha;
   }
   void subChaValidate(int subCha) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String PART_USER = "partUser";
   wt.org.WTPrincipalReference partUser;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public wt.org.WTPrincipalReference getPartUser() {
      return partUser;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setPartUser(wt.org.WTPrincipalReference partUser) throws wt.util.WTPropertyVetoException {
      partUserValidate(partUser);
      this.partUser = partUser;
   }
   void partUserValidate(wt.org.WTPrincipalReference partUser) throws wt.util.WTPropertyVetoException {
      if (partUser == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partUser") },
               new java.beans.PropertyChangeEvent(this, "partUser", this.partUser, partUser));
   }

   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public e3ps.project.MoldProject getProject() {
      return (projectReference != null) ? (e3ps.project.MoldProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
    */
   public void setProject(e3ps.project.MoldProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.MoldProject) the_project));
   }
   /**
    * @see e3ps.project.moldPart.MoldPartManager
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
            !e3ps.project.MoldProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 4599675687304683335L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( attr1 );
      output.writeObject( attr2 );
      output.writeObject( attr3 );
      output.writeInt( cha );
      output.writeObject( createType );
      output.writeObject( dieNo );
      output.writeObject( ecoNo );
      output.writeObject( endDate );
      output.writeObject( levelMold );
      output.writeObject( levelType );
      output.writeObject( moldDate );
      output.writeObject( moldState );
      output.writeObject( owner );
      output.writeObject( partUser );
      output.writeObject( planDate );
      output.writeObject( projectReference );
      output.writeObject( sortNo );
      output.writeInt( subCha );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.moldPart.MoldPartManager) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "attr1", attr1 );
      output.setString( "attr2", attr2 );
      output.setString( "attr3", attr3 );
      output.setInt( "cha", cha );
      output.setString( "createType", createType );
      output.setString( "dieNo", dieNo );
      output.setString( "ecoNo", ecoNo );
      output.setTimestamp( "endDate", endDate );
      output.setString( "levelMold", levelMold );
      output.setString( "levelType", levelType );
      output.setTimestamp( "moldDate", moldDate );
      output.setString( "moldState", moldState );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "partUser", partUser, wt.org.WTPrincipalReference.class, true );
      output.setTimestamp( "planDate", planDate );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.setString( "sortNo", sortNo );
      output.setInt( "subCha", subCha );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      attr1 = input.getString( "attr1" );
      attr2 = input.getString( "attr2" );
      attr3 = input.getString( "attr3" );
      cha = input.getInt( "cha" );
      createType = input.getString( "createType" );
      dieNo = input.getString( "dieNo" );
      ecoNo = input.getString( "ecoNo" );
      endDate = input.getTimestamp( "endDate" );
      levelMold = input.getString( "levelMold" );
      levelType = input.getString( "levelType" );
      moldDate = input.getTimestamp( "moldDate" );
      moldState = input.getString( "moldState" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partUser = (wt.org.WTPrincipalReference) input.readObject( "partUser", partUser, wt.org.WTPrincipalReference.class, true );
      planDate = input.getTimestamp( "planDate" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      sortNo = input.getString( "sortNo" );
      subCha = input.getInt( "subCha" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion4599675687304683335L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      attr1 = (java.lang.String) input.readObject();
      attr2 = (java.lang.String) input.readObject();
      attr3 = (java.lang.String) input.readObject();
      cha = input.readInt();
      createType = (java.lang.String) input.readObject();
      dieNo = (java.lang.String) input.readObject();
      ecoNo = (java.lang.String) input.readObject();
      endDate = (java.sql.Timestamp) input.readObject();
      levelMold = (java.lang.String) input.readObject();
      levelType = (java.lang.String) input.readObject();
      moldDate = (java.sql.Timestamp) input.readObject();
      moldState = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partUser = (wt.org.WTPrincipalReference) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      sortNo = (java.lang.String) input.readObject();
      subCha = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( MoldPartManager thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion4599675687304683335L( input, readSerialVersionUID, superDone );
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
