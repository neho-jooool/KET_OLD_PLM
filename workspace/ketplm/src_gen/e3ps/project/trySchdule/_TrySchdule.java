package e3ps.project.trySchdule;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TrySchdule implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.trySchdule.trySchduleResource";
   static final java.lang.String CLASSNAME = TrySchdule.class.getName();

   /**
    * Try단계
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String TRY_TYPE = "tryType";
   static int TRY_TYPE_UPPER_LIMIT = -1;
   java.lang.String tryType;
   /**
    * Try단계
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getTryType() {
      return tryType;
   }
   /**
    * Try단계
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setTryType(java.lang.String tryType) throws wt.util.WTPropertyVetoException {
      tryTypeValidate(tryType);
      this.tryType = tryType;
   }
   void tryTypeValidate(java.lang.String tryType) throws wt.util.WTPropertyVetoException {
      if (TRY_TYPE_UPPER_LIMIT < 1) {
         try { TRY_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tryType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRY_TYPE_UPPER_LIMIT = 200; }
      }
      if (tryType != null && !wt.fc.PersistenceHelper.checkStoredLength(tryType.toString(), TRY_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tryType"), java.lang.String.valueOf(java.lang.Math.min(TRY_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tryType", this.tryType, tryType));
   }

   /**
    * 상태
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String STATE = "state";
   static int STATE_UPPER_LIMIT = -1;
   java.lang.String state;
   /**
    * 상태
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getState() {
      return state;
   }
   /**
    * 상태
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setState(java.lang.String state) throws wt.util.WTPropertyVetoException {
      stateValidate(state);
      this.state = state;
   }
   void stateValidate(java.lang.String state) throws wt.util.WTPropertyVetoException {
      if (STATE_UPPER_LIMIT < 1) {
         try { STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("state").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STATE_UPPER_LIMIT = 200; }
      }
      if (state != null && !wt.fc.PersistenceHelper.checkStoredLength(state.toString(), STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "state"), java.lang.String.valueOf(java.lang.Math.min(STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "state", this.state, state));
   }

   /**
    * 수정내용
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String DES = "des";
   static int DES_UPPER_LIMIT = -1;
   java.lang.String des;
   /**
    * 수정내용
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getDes() {
      return des;
   }
   /**
    * 수정내용
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setDes(java.lang.String des) throws wt.util.WTPropertyVetoException {
      desValidate(des);
      this.des = des;
   }
   void desValidate(java.lang.String des) throws wt.util.WTPropertyVetoException {
      if (DES_UPPER_LIMIT < 1) {
         try { DES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("des").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DES_UPPER_LIMIT = 2000; }
      }
      if (des != null && !wt.fc.PersistenceHelper.checkStoredLength(des.toString(), DES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "des"), java.lang.String.valueOf(java.lang.Math.min(DES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "des", this.des, des));
   }

   /**
    * 설비(TON)
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String TON = "ton";
   static int TON_UPPER_LIMIT = -1;
   java.lang.String ton;
   /**
    * 설비(TON)
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getTon() {
      return ton;
   }
   /**
    * 설비(TON)
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setTon(java.lang.String ton) throws wt.util.WTPropertyVetoException {
      tonValidate(ton);
      this.ton = ton;
   }
   void tonValidate(java.lang.String ton) throws wt.util.WTPropertyVetoException {
      if (TON_UPPER_LIMIT < 1) {
         try { TON_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ton").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TON_UPPER_LIMIT = 200; }
      }
      if (ton != null && !wt.fc.PersistenceHelper.checkStoredLength(ton.toString(), TON_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ton"), java.lang.String.valueOf(java.lang.Math.min(TON_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ton", this.ton, ton));
   }

   /**
    * 수량
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String QUANTITY = "quantity";
   static int QUANTITY_UPPER_LIMIT = -1;
   java.lang.String quantity;
   /**
    * 수량
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getQuantity() {
      return quantity;
   }
   /**
    * 수량
    *
    * @see e3ps.project.trySchdule.TrySchdule
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
    * 계획
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String PLAN_DATE = "planDate";
   java.sql.Timestamp planDate;
   /**
    * 계획
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.sql.Timestamp getPlanDate() {
      return planDate;
   }
   /**
    * 계획
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setPlanDate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
      planDateValidate(planDate);
      this.planDate = planDate;
   }
   void planDateValidate(java.sql.Timestamp planDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String COMPLETED = "completed";
   boolean completed;
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public boolean isCompleted() {
      return completed;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setCompleted(boolean completed) throws wt.util.WTPropertyVetoException {
      completedValidate(completed);
      this.completed = completed;
   }
   void completedValidate(boolean completed) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String TRY_PLAN = "tryPlan";
   boolean tryPlan;
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public boolean isTryPlan() {
      return tryPlan;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setTryPlan(boolean tryPlan) throws wt.util.WTPropertyVetoException {
      tryPlanValidate(tryPlan);
      this.tryPlan = tryPlan;
   }
   void tryPlanValidate(boolean tryPlan) throws wt.util.WTPropertyVetoException {
   }

   /**
    * Try장소
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String TRY_PLACE = "tryPlace";
   static int TRY_PLACE_UPPER_LIMIT = -1;
   java.lang.String tryPlace;
   /**
    * Try장소
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getTryPlace() {
      return tryPlace;
   }
   /**
    * Try장소
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setTryPlace(java.lang.String tryPlace) throws wt.util.WTPropertyVetoException {
      tryPlaceValidate(tryPlace);
      this.tryPlace = tryPlace;
   }
   void tryPlaceValidate(java.lang.String tryPlace) throws wt.util.WTPropertyVetoException {
      if (TRY_PLACE_UPPER_LIMIT < 1) {
         try { TRY_PLACE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tryPlace").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TRY_PLACE_UPPER_LIMIT = 200; }
      }
      if (tryPlace != null && !wt.fc.PersistenceHelper.checkStoredLength(tryPlace.toString(), TRY_PLACE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tryPlace"), java.lang.String.valueOf(java.lang.Math.min(TRY_PLACE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tryPlace", this.tryPlace, tryPlace));
   }

   /**
    * 원재료(비철) 폭
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String WIDTH = "width";
   static int WIDTH_UPPER_LIMIT = -1;
   java.lang.String width;
   /**
    * 원재료(비철) 폭
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getWidth() {
      return width;
   }
   /**
    * 원재료(비철) 폭
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setWidth(java.lang.String width) throws wt.util.WTPropertyVetoException {
      widthValidate(width);
      this.width = width;
   }
   void widthValidate(java.lang.String width) throws wt.util.WTPropertyVetoException {
      if (WIDTH_UPPER_LIMIT < 1) {
         try { WIDTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("width").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WIDTH_UPPER_LIMIT = 200; }
      }
      if (width != null && !wt.fc.PersistenceHelper.checkStoredLength(width.toString(), WIDTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "width"), java.lang.String.valueOf(java.lang.Math.min(WIDTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "width", this.width, width));
   }

   /**
    * 원재료(비철) 두께
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String THICKNESS = "thickness";
   static int THICKNESS_UPPER_LIMIT = -1;
   java.lang.String thickness;
   /**
    * 원재료(비철) 두께
    *
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public java.lang.String getThickness() {
      return thickness;
   }
   /**
    * 원재료(비철) 두께
    *
    * @see e3ps.project.trySchdule.TrySchdule
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
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String REQUESTER = "requester";
   wt.org.WTPrincipalReference requester;
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public wt.org.WTPrincipalReference getRequester() {
      return requester;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setRequester(wt.org.WTPrincipalReference requester) throws wt.util.WTPropertyVetoException {
      requesterValidate(requester);
      this.requester = requester;
   }
   void requesterValidate(wt.org.WTPrincipalReference requester) throws wt.util.WTPropertyVetoException {
      if (requester == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "requester") },
               new java.beans.PropertyChangeEvent(this, "requester", this.requester, requester));
   }

   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String CREATOR = "creator";
   wt.org.WTPrincipalReference creator;
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public wt.org.WTPrincipalReference getCreator() {
      return creator;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setCreator(wt.org.WTPrincipalReference creator) throws wt.util.WTPropertyVetoException {
      creatorValidate(creator);
      this.creator = creator;
   }
   void creatorValidate(wt.org.WTPrincipalReference creator) throws wt.util.WTPropertyVetoException {
      if (creator == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creator") },
               new java.beans.PropertyChangeEvent(this, "creator", this.creator, creator));
   }

   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String MATERIAL = "material";
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String MATERIAL_REFERENCE = "materialReference";
   wt.fc.ObjectReference materialReference;
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public e3ps.project.material.MoldMaterial getMaterial() {
      return (materialReference != null) ? (e3ps.project.material.MoldMaterial) materialReference.getObject() : null;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public wt.fc.ObjectReference getMaterialReference() {
      return materialReference;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setMaterial(e3ps.project.material.MoldMaterial the_material) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMaterialReference(the_material == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.material.MoldMaterial) the_material));
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setMaterialReference(wt.fc.ObjectReference the_materialReference) throws wt.util.WTPropertyVetoException {
      materialReferenceValidate(the_materialReference);
      materialReference = (wt.fc.ObjectReference) the_materialReference;
   }
   void materialReferenceValidate(wt.fc.ObjectReference the_materialReference) throws wt.util.WTPropertyVetoException {
      if (the_materialReference != null && the_materialReference.getReferencedClass() != null &&
            !e3ps.project.material.MoldMaterial.class.isAssignableFrom(the_materialReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "materialReference", this.materialReference, materialReference));
   }

   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String PROPERTY = "property";
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String PROPERTY_REFERENCE = "propertyReference";
   wt.fc.ObjectReference propertyReference;
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public e3ps.common.code.NumberCode getProperty() {
      return (propertyReference != null) ? (e3ps.common.code.NumberCode) propertyReference.getObject() : null;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public wt.fc.ObjectReference getPropertyReference() {
      return propertyReference;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setProperty(e3ps.common.code.NumberCode the_property) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPropertyReference(the_property == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_property));
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setPropertyReference(wt.fc.ObjectReference the_propertyReference) throws wt.util.WTPropertyVetoException {
      propertyReferenceValidate(the_propertyReference);
      propertyReference = (wt.fc.ObjectReference) the_propertyReference;
   }
   void propertyReferenceValidate(wt.fc.ObjectReference the_propertyReference) throws wt.util.WTPropertyVetoException {
      if (the_propertyReference != null && the_propertyReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_propertyReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "propertyReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "propertyReference", this.propertyReference, propertyReference));
   }

   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String MOLD_MASTER = "moldMaster";
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public static final java.lang.String MOLD_MASTER_REFERENCE = "moldMasterReference";
   wt.fc.ObjectReference moldMasterReference;
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public e3ps.project.E3PSProjectMaster getMoldMaster() {
      return (moldMasterReference != null) ? (e3ps.project.E3PSProjectMaster) moldMasterReference.getObject() : null;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public wt.fc.ObjectReference getMoldMasterReference() {
      return moldMasterReference;
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setMoldMaster(e3ps.project.E3PSProjectMaster the_moldMaster) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMoldMasterReference(the_moldMaster == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProjectMaster) the_moldMaster));
   }
   /**
    * @see e3ps.project.trySchdule.TrySchdule
    */
   public void setMoldMasterReference(wt.fc.ObjectReference the_moldMasterReference) throws wt.util.WTPropertyVetoException {
      moldMasterReferenceValidate(the_moldMasterReference);
      moldMasterReference = (wt.fc.ObjectReference) the_moldMasterReference;
   }
   void moldMasterReferenceValidate(wt.fc.ObjectReference the_moldMasterReference) throws wt.util.WTPropertyVetoException {
      if (the_moldMasterReference != null && the_moldMasterReference.getReferencedClass() != null &&
            !e3ps.project.E3PSProjectMaster.class.isAssignableFrom(the_moldMasterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldMasterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "moldMasterReference", this.moldMasterReference, moldMasterReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -3936307358686664733L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeBoolean( completed );
      output.writeObject( creator );
      output.writeObject( des );
      output.writeObject( materialReference );
      output.writeObject( moldMasterReference );
      output.writeObject( owner );
      output.writeObject( planDate );
      output.writeObject( propertyReference );
      output.writeObject( quantity );
      output.writeObject( requester );
      output.writeObject( state );
      output.writeObject( thePersistInfo );
      output.writeObject( thickness );
      output.writeObject( ton );
      output.writeObject( tryPlace );
      output.writeBoolean( tryPlan );
      output.writeObject( tryType );
      output.writeObject( width );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.trySchdule.TrySchdule) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setBoolean( "completed", completed );
      output.writeObject( "creator", creator, wt.org.WTPrincipalReference.class, true );
      output.setString( "des", des );
      output.writeObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "moldMasterReference", moldMasterReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setTimestamp( "planDate", planDate );
      output.writeObject( "propertyReference", propertyReference, wt.fc.ObjectReference.class, true );
      output.setString( "quantity", quantity );
      output.writeObject( "requester", requester, wt.org.WTPrincipalReference.class, true );
      output.setString( "state", state );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "thickness", thickness );
      output.setString( "ton", ton );
      output.setString( "tryPlace", tryPlace );
      output.setBoolean( "tryPlan", tryPlan );
      output.setString( "tryType", tryType );
      output.setString( "width", width );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      completed = input.getBoolean( "completed" );
      creator = (wt.org.WTPrincipalReference) input.readObject( "creator", creator, wt.org.WTPrincipalReference.class, true );
      des = input.getString( "des" );
      materialReference = (wt.fc.ObjectReference) input.readObject( "materialReference", materialReference, wt.fc.ObjectReference.class, true );
      moldMasterReference = (wt.fc.ObjectReference) input.readObject( "moldMasterReference", moldMasterReference, wt.fc.ObjectReference.class, true );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      planDate = input.getTimestamp( "planDate" );
      propertyReference = (wt.fc.ObjectReference) input.readObject( "propertyReference", propertyReference, wt.fc.ObjectReference.class, true );
      quantity = input.getString( "quantity" );
      requester = (wt.org.WTPrincipalReference) input.readObject( "requester", requester, wt.org.WTPrincipalReference.class, true );
      state = input.getString( "state" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      thickness = input.getString( "thickness" );
      ton = input.getString( "ton" );
      tryPlace = input.getString( "tryPlace" );
      tryPlan = input.getBoolean( "tryPlan" );
      tryType = input.getString( "tryType" );
      width = input.getString( "width" );
   }

   boolean readVersion_3936307358686664733L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      completed = input.readBoolean();
      creator = (wt.org.WTPrincipalReference) input.readObject();
      des = (java.lang.String) input.readObject();
      materialReference = (wt.fc.ObjectReference) input.readObject();
      moldMasterReference = (wt.fc.ObjectReference) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      planDate = (java.sql.Timestamp) input.readObject();
      propertyReference = (wt.fc.ObjectReference) input.readObject();
      quantity = (java.lang.String) input.readObject();
      requester = (wt.org.WTPrincipalReference) input.readObject();
      state = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      thickness = (java.lang.String) input.readObject();
      ton = (java.lang.String) input.readObject();
      tryPlace = (java.lang.String) input.readObject();
      tryPlan = input.readBoolean();
      tryType = (java.lang.String) input.readObject();
      width = (java.lang.String) input.readObject();
      return true;
   }

   protected boolean readVersion( TrySchdule thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3936307358686664733L( input, readSerialVersionUID, superDone );
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
