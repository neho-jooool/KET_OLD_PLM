package ext.ket.dqm.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETDqmRaise extends wt.enterprise.Managed implements wt.content.FormatContentHolder, wt.inf.container.WTContained, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.dqm.entity.entityResource";
   static final java.lang.String CLASSNAME = KETDqmRaise.class.getName();

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PJTNO = "pjtno";
   static int PJTNO_UPPER_LIMIT = -1;
   java.lang.String pjtno;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getPjtno() {
      return pjtno;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPjtno(java.lang.String pjtno) throws wt.util.WTPropertyVetoException {
      pjtnoValidate(pjtno);
      this.pjtno = pjtno;
   }
   void pjtnoValidate(java.lang.String pjtno) throws wt.util.WTPropertyVetoException {
      if (PJTNO_UPPER_LIMIT < 1) {
         try { PJTNO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtno").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJTNO_UPPER_LIMIT = 200; }
      }
      if (pjtno != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtno.toString(), PJTNO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtno"), java.lang.String.valueOf(java.lang.Math.min(PJTNO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtno", this.pjtno, pjtno));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PJTNAME = "pjtname";
   static int PJTNAME_UPPER_LIMIT = -1;
   java.lang.String pjtname;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getPjtname() {
      return pjtname;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPjtname(java.lang.String pjtname) throws wt.util.WTPropertyVetoException {
      pjtnameValidate(pjtname);
      this.pjtname = pjtname;
   }
   void pjtnameValidate(java.lang.String pjtname) throws wt.util.WTPropertyVetoException {
      if (PJTNAME_UPPER_LIMIT < 1) {
         try { PJTNAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtname").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJTNAME_UPPER_LIMIT = 200; }
      }
      if (pjtname != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtname.toString(), PJTNAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtname"), java.lang.String.valueOf(java.lang.Math.min(PJTNAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtname", this.pjtname, pjtname));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String WEB_EDITOR = "webEditor";
   java.lang.Object webEditor;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.Object getWebEditor() {
      return webEditor;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setWebEditor(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
      webEditorValidate(webEditor);
      this.webEditor = webEditor;
   }
   void webEditorValidate(java.lang.Object webEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String WEB_EDITOR_TEXT = "webEditorText";
   java.lang.Object webEditorText;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.Object getWebEditorText() {
      return webEditorText;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setWebEditorText(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
      webEditorTextValidate(webEditorText);
      this.webEditorText = webEditorText;
   }
   void webEditorTextValidate(java.lang.Object webEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CUSTOMER_NAME = "customerName";
   static int CUSTOMER_NAME_UPPER_LIMIT = -1;
   java.lang.String customerName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getCustomerName() {
      return customerName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCustomerName(java.lang.String customerName) throws wt.util.WTPropertyVetoException {
      customerNameValidate(customerName);
      this.customerName = customerName;
   }
   void customerNameValidate(java.lang.String customerName) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_NAME_UPPER_LIMIT < 1) {
         try { CUSTOMER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customerName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_NAME_UPPER_LIMIT = 4000; }
      }
      if (customerName != null && !wt.fc.PersistenceHelper.checkStoredLength(customerName.toString(), CUSTOMER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerName"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customerName", this.customerName, customerName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CUSTOMER_CODE = "customerCode";
   static int CUSTOMER_CODE_UPPER_LIMIT = -1;
   java.lang.String customerCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getCustomerCode() {
      return customerCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCustomerCode(java.lang.String customerCode) throws wt.util.WTPropertyVetoException {
      customerCodeValidate(customerCode);
      this.customerCode = customerCode;
   }
   void customerCodeValidate(java.lang.String customerCode) throws wt.util.WTPropertyVetoException {
      if (CUSTOMER_CODE_UPPER_LIMIT < 1) {
         try { CUSTOMER_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("customerCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CUSTOMER_CODE_UPPER_LIMIT = 4000; }
      }
      if (customerCode != null && !wt.fc.PersistenceHelper.checkStoredLength(customerCode.toString(), CUSTOMER_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerCode"), java.lang.String.valueOf(java.lang.Math.min(CUSTOMER_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "customerCode", this.customerCode, customerCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CARTYPE_NAME = "cartypeName";
   static int CARTYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String cartypeName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getCartypeName() {
      return cartypeName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCartypeName(java.lang.String cartypeName) throws wt.util.WTPropertyVetoException {
      cartypeNameValidate(cartypeName);
      this.cartypeName = cartypeName;
   }
   void cartypeNameValidate(java.lang.String cartypeName) throws wt.util.WTPropertyVetoException {
      if (CARTYPE_NAME_UPPER_LIMIT < 1) {
         try { CARTYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cartypeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CARTYPE_NAME_UPPER_LIMIT = 200; }
      }
      if (cartypeName != null && !wt.fc.PersistenceHelper.checkStoredLength(cartypeName.toString(), CARTYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cartypeName"), java.lang.String.valueOf(java.lang.Math.min(CARTYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cartypeName", this.cartypeName, cartypeName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CARTYPE_CODE = "cartypeCode";
   static int CARTYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String cartypeCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getCartypeCode() {
      return cartypeCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCartypeCode(java.lang.String cartypeCode) throws wt.util.WTPropertyVetoException {
      cartypeCodeValidate(cartypeCode);
      this.cartypeCode = cartypeCode;
   }
   void cartypeCodeValidate(java.lang.String cartypeCode) throws wt.util.WTPropertyVetoException {
      if (CARTYPE_CODE_UPPER_LIMIT < 1) {
         try { CARTYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cartypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CARTYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (cartypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(cartypeCode.toString(), CARTYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cartypeCode"), java.lang.String.valueOf(java.lang.Math.min(CARTYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cartypeCode", this.cartypeCode, cartypeCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PROBLEM_TYPE_NAME = "problemTypeName";
   static int PROBLEM_TYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String problemTypeName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getProblemTypeName() {
      return problemTypeName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setProblemTypeName(java.lang.String problemTypeName) throws wt.util.WTPropertyVetoException {
      problemTypeNameValidate(problemTypeName);
      this.problemTypeName = problemTypeName;
   }
   void problemTypeNameValidate(java.lang.String problemTypeName) throws wt.util.WTPropertyVetoException {
      if (PROBLEM_TYPE_NAME_UPPER_LIMIT < 1) {
         try { PROBLEM_TYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemTypeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEM_TYPE_NAME_UPPER_LIMIT = 200; }
      }
      if (problemTypeName != null && !wt.fc.PersistenceHelper.checkStoredLength(problemTypeName.toString(), PROBLEM_TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemTypeName"), java.lang.String.valueOf(java.lang.Math.min(PROBLEM_TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemTypeName", this.problemTypeName, problemTypeName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PROBLEM_TYPE_CODE = "problemTypeCode";
   static int PROBLEM_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String problemTypeCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getProblemTypeCode() {
      return problemTypeCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setProblemTypeCode(java.lang.String problemTypeCode) throws wt.util.WTPropertyVetoException {
      problemTypeCodeValidate(problemTypeCode);
      this.problemTypeCode = problemTypeCode;
   }
   void problemTypeCodeValidate(java.lang.String problemTypeCode) throws wt.util.WTPropertyVetoException {
      if (PROBLEM_TYPE_CODE_UPPER_LIMIT < 1) {
         try { PROBLEM_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEM_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (problemTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(problemTypeCode.toString(), PROBLEM_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemTypeCode"), java.lang.String.valueOf(java.lang.Math.min(PROBLEM_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemTypeCode", this.problemTypeCode, problemTypeCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String URGENCY_NAME = "urgencyName";
   static int URGENCY_NAME_UPPER_LIMIT = -1;
   java.lang.String urgencyName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getUrgencyName() {
      return urgencyName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setUrgencyName(java.lang.String urgencyName) throws wt.util.WTPropertyVetoException {
      urgencyNameValidate(urgencyName);
      this.urgencyName = urgencyName;
   }
   void urgencyNameValidate(java.lang.String urgencyName) throws wt.util.WTPropertyVetoException {
      if (URGENCY_NAME_UPPER_LIMIT < 1) {
         try { URGENCY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("urgencyName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { URGENCY_NAME_UPPER_LIMIT = 200; }
      }
      if (urgencyName != null && !wt.fc.PersistenceHelper.checkStoredLength(urgencyName.toString(), URGENCY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "urgencyName"), java.lang.String.valueOf(java.lang.Math.min(URGENCY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "urgencyName", this.urgencyName, urgencyName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String URGENCY_CODE = "urgencyCode";
   static int URGENCY_CODE_UPPER_LIMIT = -1;
   java.lang.String urgencyCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getUrgencyCode() {
      return urgencyCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setUrgencyCode(java.lang.String urgencyCode) throws wt.util.WTPropertyVetoException {
      urgencyCodeValidate(urgencyCode);
      this.urgencyCode = urgencyCode;
   }
   void urgencyCodeValidate(java.lang.String urgencyCode) throws wt.util.WTPropertyVetoException {
      if (URGENCY_CODE_UPPER_LIMIT < 1) {
         try { URGENCY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("urgencyCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { URGENCY_CODE_UPPER_LIMIT = 200; }
      }
      if (urgencyCode != null && !wt.fc.PersistenceHelper.checkStoredLength(urgencyCode.toString(), URGENCY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "urgencyCode"), java.lang.String.valueOf(java.lang.Math.min(URGENCY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "urgencyCode", this.urgencyCode, urgencyCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PART_CATEGORY_NAME = "partCategoryName";
   static int PART_CATEGORY_NAME_UPPER_LIMIT = -1;
   java.lang.String partCategoryName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getPartCategoryName() {
      return partCategoryName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPartCategoryName(java.lang.String partCategoryName) throws wt.util.WTPropertyVetoException {
      partCategoryNameValidate(partCategoryName);
      this.partCategoryName = partCategoryName;
   }
   void partCategoryNameValidate(java.lang.String partCategoryName) throws wt.util.WTPropertyVetoException {
      if (PART_CATEGORY_NAME_UPPER_LIMIT < 1) {
         try { PART_CATEGORY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partCategoryName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_CATEGORY_NAME_UPPER_LIMIT = 200; }
      }
      if (partCategoryName != null && !wt.fc.PersistenceHelper.checkStoredLength(partCategoryName.toString(), PART_CATEGORY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partCategoryName"), java.lang.String.valueOf(java.lang.Math.min(PART_CATEGORY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partCategoryName", this.partCategoryName, partCategoryName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PART_CATEGORY_CODE = "partCategoryCode";
   static int PART_CATEGORY_CODE_UPPER_LIMIT = -1;
   java.lang.String partCategoryCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getPartCategoryCode() {
      return partCategoryCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPartCategoryCode(java.lang.String partCategoryCode) throws wt.util.WTPropertyVetoException {
      partCategoryCodeValidate(partCategoryCode);
      this.partCategoryCode = partCategoryCode;
   }
   void partCategoryCodeValidate(java.lang.String partCategoryCode) throws wt.util.WTPropertyVetoException {
      if (PART_CATEGORY_CODE_UPPER_LIMIT < 1) {
         try { PART_CATEGORY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partCategoryCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_CATEGORY_CODE_UPPER_LIMIT = 200; }
      }
      if (partCategoryCode != null && !wt.fc.PersistenceHelper.checkStoredLength(partCategoryCode.toString(), PART_CATEGORY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partCategoryCode"), java.lang.String.valueOf(java.lang.Math.min(PART_CATEGORY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partCategoryCode", this.partCategoryCode, partCategoryCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_DIV_NAME = "occurDivName";
   static int OCCUR_DIV_NAME_UPPER_LIMIT = -1;
   java.lang.String occurDivName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurDivName() {
      return occurDivName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurDivName(java.lang.String occurDivName) throws wt.util.WTPropertyVetoException {
      occurDivNameValidate(occurDivName);
      this.occurDivName = occurDivName;
   }
   void occurDivNameValidate(java.lang.String occurDivName) throws wt.util.WTPropertyVetoException {
      if (OCCUR_DIV_NAME_UPPER_LIMIT < 1) {
         try { OCCUR_DIV_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurDivName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_DIV_NAME_UPPER_LIMIT = 200; }
      }
      if (occurDivName != null && !wt.fc.PersistenceHelper.checkStoredLength(occurDivName.toString(), OCCUR_DIV_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurDivName"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_DIV_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurDivName", this.occurDivName, occurDivName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_DIV_CODE = "occurDivCode";
   static int OCCUR_DIV_CODE_UPPER_LIMIT = -1;
   java.lang.String occurDivCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurDivCode() {
      return occurDivCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurDivCode(java.lang.String occurDivCode) throws wt.util.WTPropertyVetoException {
      occurDivCodeValidate(occurDivCode);
      this.occurDivCode = occurDivCode;
   }
   void occurDivCodeValidate(java.lang.String occurDivCode) throws wt.util.WTPropertyVetoException {
      if (OCCUR_DIV_CODE_UPPER_LIMIT < 1) {
         try { OCCUR_DIV_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurDivCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_DIV_CODE_UPPER_LIMIT = 200; }
      }
      if (occurDivCode != null && !wt.fc.PersistenceHelper.checkStoredLength(occurDivCode.toString(), OCCUR_DIV_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurDivCode"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_DIV_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurDivCode", this.occurDivCode, occurDivCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_STEP_NAME = "occurStepName";
   static int OCCUR_STEP_NAME_UPPER_LIMIT = -1;
   java.lang.String occurStepName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurStepName() {
      return occurStepName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurStepName(java.lang.String occurStepName) throws wt.util.WTPropertyVetoException {
      occurStepNameValidate(occurStepName);
      this.occurStepName = occurStepName;
   }
   void occurStepNameValidate(java.lang.String occurStepName) throws wt.util.WTPropertyVetoException {
      if (OCCUR_STEP_NAME_UPPER_LIMIT < 1) {
         try { OCCUR_STEP_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurStepName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_STEP_NAME_UPPER_LIMIT = 200; }
      }
      if (occurStepName != null && !wt.fc.PersistenceHelper.checkStoredLength(occurStepName.toString(), OCCUR_STEP_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurStepName"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_STEP_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurStepName", this.occurStepName, occurStepName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_STEP_CODE = "occurStepCode";
   static int OCCUR_STEP_CODE_UPPER_LIMIT = -1;
   java.lang.String occurStepCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurStepCode() {
      return occurStepCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurStepCode(java.lang.String occurStepCode) throws wt.util.WTPropertyVetoException {
      occurStepCodeValidate(occurStepCode);
      this.occurStepCode = occurStepCode;
   }
   void occurStepCodeValidate(java.lang.String occurStepCode) throws wt.util.WTPropertyVetoException {
      if (OCCUR_STEP_CODE_UPPER_LIMIT < 1) {
         try { OCCUR_STEP_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurStepCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_STEP_CODE_UPPER_LIMIT = 200; }
      }
      if (occurStepCode != null && !wt.fc.PersistenceHelper.checkStoredLength(occurStepCode.toString(), OCCUR_STEP_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurStepCode"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_STEP_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurStepCode", this.occurStepCode, occurStepCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_NAME = "occurName";
   static int OCCUR_NAME_UPPER_LIMIT = -1;
   java.lang.String occurName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurName() {
      return occurName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurName(java.lang.String occurName) throws wt.util.WTPropertyVetoException {
      occurNameValidate(occurName);
      this.occurName = occurName;
   }
   void occurNameValidate(java.lang.String occurName) throws wt.util.WTPropertyVetoException {
      if (OCCUR_NAME_UPPER_LIMIT < 1) {
         try { OCCUR_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_NAME_UPPER_LIMIT = 200; }
      }
      if (occurName != null && !wt.fc.PersistenceHelper.checkStoredLength(occurName.toString(), OCCUR_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurName"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurName", this.occurName, occurName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_CODE = "occurCode";
   static int OCCUR_CODE_UPPER_LIMIT = -1;
   java.lang.String occurCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurCode() {
      return occurCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurCode(java.lang.String occurCode) throws wt.util.WTPropertyVetoException {
      occurCodeValidate(occurCode);
      this.occurCode = occurCode;
   }
   void occurCodeValidate(java.lang.String occurCode) throws wt.util.WTPropertyVetoException {
      if (OCCUR_CODE_UPPER_LIMIT < 1) {
         try { OCCUR_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_CODE_UPPER_LIMIT = 200; }
      }
      if (occurCode != null && !wt.fc.PersistenceHelper.checkStoredLength(occurCode.toString(), OCCUR_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurCode"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurCode", this.occurCode, occurCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_DATE = "occurDate";
   java.sql.Timestamp occurDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.sql.Timestamp getOccurDate() {
      return occurDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurDate(java.sql.Timestamp occurDate) throws wt.util.WTPropertyVetoException {
      occurDateValidate(occurDate);
      this.occurDate = occurDate;
   }
   void occurDateValidate(java.sql.Timestamp occurDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ACTION_DEPT_NAME = "actionDeptName";
   static int ACTION_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String actionDeptName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getActionDeptName() {
      return actionDeptName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setActionDeptName(java.lang.String actionDeptName) throws wt.util.WTPropertyVetoException {
      actionDeptNameValidate(actionDeptName);
      this.actionDeptName = actionDeptName;
   }
   void actionDeptNameValidate(java.lang.String actionDeptName) throws wt.util.WTPropertyVetoException {
      if (ACTION_DEPT_NAME_UPPER_LIMIT < 1) {
         try { ACTION_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("actionDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTION_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (actionDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(actionDeptName.toString(), ACTION_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionDeptName"), java.lang.String.valueOf(java.lang.Math.min(ACTION_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "actionDeptName", this.actionDeptName, actionDeptName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ACTION_USER_DEPT_NAME = "actionUserDeptName";
   static int ACTION_USER_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String actionUserDeptName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getActionUserDeptName() {
      return actionUserDeptName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setActionUserDeptName(java.lang.String actionUserDeptName) throws wt.util.WTPropertyVetoException {
      actionUserDeptNameValidate(actionUserDeptName);
      this.actionUserDeptName = actionUserDeptName;
   }
   void actionUserDeptNameValidate(java.lang.String actionUserDeptName) throws wt.util.WTPropertyVetoException {
      if (ACTION_USER_DEPT_NAME_UPPER_LIMIT < 1) {
         try { ACTION_USER_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("actionUserDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTION_USER_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (actionUserDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(actionUserDeptName.toString(), ACTION_USER_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionUserDeptName"), java.lang.String.valueOf(java.lang.Math.min(ACTION_USER_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "actionUserDeptName", this.actionUserDeptName, actionUserDeptName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ACTION_DEPT_CODE = "actionDeptCode";
   static int ACTION_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String actionDeptCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getActionDeptCode() {
      return actionDeptCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setActionDeptCode(java.lang.String actionDeptCode) throws wt.util.WTPropertyVetoException {
      actionDeptCodeValidate(actionDeptCode);
      this.actionDeptCode = actionDeptCode;
   }
   void actionDeptCodeValidate(java.lang.String actionDeptCode) throws wt.util.WTPropertyVetoException {
      if (ACTION_DEPT_CODE_UPPER_LIMIT < 1) {
         try { ACTION_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("actionDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTION_DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (actionDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(actionDeptCode.toString(), ACTION_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionDeptCode"), java.lang.String.valueOf(java.lang.Math.min(ACTION_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "actionDeptCode", this.actionDeptCode, actionDeptCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ACTION_USER_NAME = "actionUserName";
   static int ACTION_USER_NAME_UPPER_LIMIT = -1;
   java.lang.String actionUserName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getActionUserName() {
      return actionUserName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setActionUserName(java.lang.String actionUserName) throws wt.util.WTPropertyVetoException {
      actionUserNameValidate(actionUserName);
      this.actionUserName = actionUserName;
   }
   void actionUserNameValidate(java.lang.String actionUserName) throws wt.util.WTPropertyVetoException {
      if (ACTION_USER_NAME_UPPER_LIMIT < 1) {
         try { ACTION_USER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("actionUserName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTION_USER_NAME_UPPER_LIMIT = 200; }
      }
      if (actionUserName != null && !wt.fc.PersistenceHelper.checkStoredLength(actionUserName.toString(), ACTION_USER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionUserName"), java.lang.String.valueOf(java.lang.Math.min(ACTION_USER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "actionUserName", this.actionUserName, actionUserName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CREATOR_DEPT_NAME = "creatorDeptName";
   static int CREATOR_DEPT_NAME_UPPER_LIMIT = -1;
   java.lang.String creatorDeptName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getCreatorDeptName() {
      return creatorDeptName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCreatorDeptName(java.lang.String creatorDeptName) throws wt.util.WTPropertyVetoException {
      creatorDeptNameValidate(creatorDeptName);
      this.creatorDeptName = creatorDeptName;
   }
   void creatorDeptNameValidate(java.lang.String creatorDeptName) throws wt.util.WTPropertyVetoException {
      if (CREATOR_DEPT_NAME_UPPER_LIMIT < 1) {
         try { CREATOR_DEPT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorDeptName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_DEPT_NAME_UPPER_LIMIT = 200; }
      }
      if (creatorDeptName != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorDeptName.toString(), CREATOR_DEPT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorDeptName"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_DEPT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorDeptName", this.creatorDeptName, creatorDeptName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CREATOR_DEPT_CODE = "creatorDeptCode";
   static int CREATOR_DEPT_CODE_UPPER_LIMIT = -1;
   java.lang.String creatorDeptCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getCreatorDeptCode() {
      return creatorDeptCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCreatorDeptCode(java.lang.String creatorDeptCode) throws wt.util.WTPropertyVetoException {
      creatorDeptCodeValidate(creatorDeptCode);
      this.creatorDeptCode = creatorDeptCode;
   }
   void creatorDeptCodeValidate(java.lang.String creatorDeptCode) throws wt.util.WTPropertyVetoException {
      if (CREATOR_DEPT_CODE_UPPER_LIMIT < 1) {
         try { CREATOR_DEPT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorDeptCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_DEPT_CODE_UPPER_LIMIT = 200; }
      }
      if (creatorDeptCode != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorDeptCode.toString(), CREATOR_DEPT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorDeptCode"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_DEPT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorDeptCode", this.creatorDeptCode, creatorDeptCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CREATOR_USER_NAME = "creatorUserName";
   static int CREATOR_USER_NAME_UPPER_LIMIT = -1;
   java.lang.String creatorUserName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getCreatorUserName() {
      return creatorUserName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCreatorUserName(java.lang.String creatorUserName) throws wt.util.WTPropertyVetoException {
      creatorUserNameValidate(creatorUserName);
      this.creatorUserName = creatorUserName;
   }
   void creatorUserNameValidate(java.lang.String creatorUserName) throws wt.util.WTPropertyVetoException {
      if (CREATOR_USER_NAME_UPPER_LIMIT < 1) {
         try { CREATOR_USER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("creatorUserName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CREATOR_USER_NAME_UPPER_LIMIT = 200; }
      }
      if (creatorUserName != null && !wt.fc.PersistenceHelper.checkStoredLength(creatorUserName.toString(), CREATOR_USER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "creatorUserName"), java.lang.String.valueOf(java.lang.Math.min(CREATOR_USER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "creatorUserName", this.creatorUserName, creatorUserName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String REQUEST_DATE = "requestDate";
   java.sql.Timestamp requestDate;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.sql.Timestamp getRequestDate() {
      return requestDate;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setRequestDate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
      requestDateValidate(requestDate);
      this.requestDate = requestDate;
   }
   void requestDateValidate(java.sql.Timestamp requestDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String DEFECT_DIV_CODE = "defectDivCode";
   static int DEFECT_DIV_CODE_UPPER_LIMIT = -1;
   java.lang.String defectDivCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getDefectDivCode() {
      return defectDivCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setDefectDivCode(java.lang.String defectDivCode) throws wt.util.WTPropertyVetoException {
      defectDivCodeValidate(defectDivCode);
      this.defectDivCode = defectDivCode;
   }
   void defectDivCodeValidate(java.lang.String defectDivCode) throws wt.util.WTPropertyVetoException {
      if (DEFECT_DIV_CODE_UPPER_LIMIT < 1) {
         try { DEFECT_DIV_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectDivCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_DIV_CODE_UPPER_LIMIT = 200; }
      }
      if (defectDivCode != null && !wt.fc.PersistenceHelper.checkStoredLength(defectDivCode.toString(), DEFECT_DIV_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectDivCode"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_DIV_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectDivCode", this.defectDivCode, defectDivCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String DEFECT_DIV_NAME = "defectDivName";
   static int DEFECT_DIV_NAME_UPPER_LIMIT = -1;
   java.lang.String defectDivName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getDefectDivName() {
      return defectDivName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setDefectDivName(java.lang.String defectDivName) throws wt.util.WTPropertyVetoException {
      defectDivNameValidate(defectDivName);
      this.defectDivName = defectDivName;
   }
   void defectDivNameValidate(java.lang.String defectDivName) throws wt.util.WTPropertyVetoException {
      if (DEFECT_DIV_NAME_UPPER_LIMIT < 1) {
         try { DEFECT_DIV_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectDivName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_DIV_NAME_UPPER_LIMIT = 200; }
      }
      if (defectDivName != null && !wt.fc.PersistenceHelper.checkStoredLength(defectDivName.toString(), DEFECT_DIV_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectDivName"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_DIV_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectDivName", this.defectDivName, defectDivName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String DEFECT_TYPE_CODE = "defectTypeCode";
   static int DEFECT_TYPE_CODE_UPPER_LIMIT = -1;
   java.lang.String defectTypeCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getDefectTypeCode() {
      return defectTypeCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setDefectTypeCode(java.lang.String defectTypeCode) throws wt.util.WTPropertyVetoException {
      defectTypeCodeValidate(defectTypeCode);
      this.defectTypeCode = defectTypeCode;
   }
   void defectTypeCodeValidate(java.lang.String defectTypeCode) throws wt.util.WTPropertyVetoException {
      if (DEFECT_TYPE_CODE_UPPER_LIMIT < 1) {
         try { DEFECT_TYPE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectTypeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_TYPE_CODE_UPPER_LIMIT = 200; }
      }
      if (defectTypeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(defectTypeCode.toString(), DEFECT_TYPE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectTypeCode"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_TYPE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectTypeCode", this.defectTypeCode, defectTypeCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String DEFECT_TYPE_NAME = "defectTypeName";
   static int DEFECT_TYPE_NAME_UPPER_LIMIT = -1;
   java.lang.String defectTypeName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getDefectTypeName() {
      return defectTypeName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setDefectTypeName(java.lang.String defectTypeName) throws wt.util.WTPropertyVetoException {
      defectTypeNameValidate(defectTypeName);
      this.defectTypeName = defectTypeName;
   }
   void defectTypeNameValidate(java.lang.String defectTypeName) throws wt.util.WTPropertyVetoException {
      if (DEFECT_TYPE_NAME_UPPER_LIMIT < 1) {
         try { DEFECT_TYPE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("defectTypeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEFECT_TYPE_NAME_UPPER_LIMIT = 200; }
      }
      if (defectTypeName != null && !wt.fc.PersistenceHelper.checkStoredLength(defectTypeName.toString(), DEFECT_TYPE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "defectTypeName"), java.lang.String.valueOf(java.lang.Math.min(DEFECT_TYPE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "defectTypeName", this.defectTypeName, defectTypeName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String APPLY_AREA1 = "applyArea1";
   static int APPLY_AREA1_UPPER_LIMIT = -1;
   java.lang.String applyArea1;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getApplyArea1() {
      return applyArea1;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setApplyArea1(java.lang.String applyArea1) throws wt.util.WTPropertyVetoException {
      applyArea1Validate(applyArea1);
      this.applyArea1 = applyArea1;
   }
   void applyArea1Validate(java.lang.String applyArea1) throws wt.util.WTPropertyVetoException {
      if (APPLY_AREA1_UPPER_LIMIT < 1) {
         try { APPLY_AREA1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("applyArea1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPLY_AREA1_UPPER_LIMIT = 200; }
      }
      if (applyArea1 != null && !wt.fc.PersistenceHelper.checkStoredLength(applyArea1.toString(), APPLY_AREA1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "applyArea1"), java.lang.String.valueOf(java.lang.Math.min(APPLY_AREA1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "applyArea1", this.applyArea1, applyArea1));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String APPLY_AREA2 = "applyArea2";
   static int APPLY_AREA2_UPPER_LIMIT = -1;
   java.lang.String applyArea2;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getApplyArea2() {
      return applyArea2;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setApplyArea2(java.lang.String applyArea2) throws wt.util.WTPropertyVetoException {
      applyArea2Validate(applyArea2);
      this.applyArea2 = applyArea2;
   }
   void applyArea2Validate(java.lang.String applyArea2) throws wt.util.WTPropertyVetoException {
      if (APPLY_AREA2_UPPER_LIMIT < 1) {
         try { APPLY_AREA2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("applyArea2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPLY_AREA2_UPPER_LIMIT = 200; }
      }
      if (applyArea2 != null && !wt.fc.PersistenceHelper.checkStoredLength(applyArea2.toString(), APPLY_AREA2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "applyArea2"), java.lang.String.valueOf(java.lang.Math.min(APPLY_AREA2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "applyArea2", this.applyArea2, applyArea2));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String APPLY_AREA3 = "applyArea3";
   static int APPLY_AREA3_UPPER_LIMIT = -1;
   java.lang.String applyArea3;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getApplyArea3() {
      return applyArea3;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setApplyArea3(java.lang.String applyArea3) throws wt.util.WTPropertyVetoException {
      applyArea3Validate(applyArea3);
      this.applyArea3 = applyArea3;
   }
   void applyArea3Validate(java.lang.String applyArea3) throws wt.util.WTPropertyVetoException {
      if (APPLY_AREA3_UPPER_LIMIT < 1) {
         try { APPLY_AREA3_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("applyArea3").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { APPLY_AREA3_UPPER_LIMIT = 200; }
      }
      if (applyArea3 != null && !wt.fc.PersistenceHelper.checkStoredLength(applyArea3.toString(), APPLY_AREA3_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "applyArea3"), java.lang.String.valueOf(java.lang.Math.min(APPLY_AREA3_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "applyArea3", this.applyArea3, applyArea3));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_PLACE_NAME = "occurPlaceName";
   static int OCCUR_PLACE_NAME_UPPER_LIMIT = -1;
   java.lang.String occurPlaceName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurPlaceName() {
      return occurPlaceName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurPlaceName(java.lang.String occurPlaceName) throws wt.util.WTPropertyVetoException {
      occurPlaceNameValidate(occurPlaceName);
      this.occurPlaceName = occurPlaceName;
   }
   void occurPlaceNameValidate(java.lang.String occurPlaceName) throws wt.util.WTPropertyVetoException {
      if (OCCUR_PLACE_NAME_UPPER_LIMIT < 1) {
         try { OCCUR_PLACE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurPlaceName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_PLACE_NAME_UPPER_LIMIT = 200; }
      }
      if (occurPlaceName != null && !wt.fc.PersistenceHelper.checkStoredLength(occurPlaceName.toString(), OCCUR_PLACE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurPlaceName"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_PLACE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurPlaceName", this.occurPlaceName, occurPlaceName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_PLACE_CODE = "occurPlaceCode";
   static int OCCUR_PLACE_CODE_UPPER_LIMIT = -1;
   java.lang.String occurPlaceCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurPlaceCode() {
      return occurPlaceCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurPlaceCode(java.lang.String occurPlaceCode) throws wt.util.WTPropertyVetoException {
      occurPlaceCodeValidate(occurPlaceCode);
      this.occurPlaceCode = occurPlaceCode;
   }
   void occurPlaceCodeValidate(java.lang.String occurPlaceCode) throws wt.util.WTPropertyVetoException {
      if (OCCUR_PLACE_CODE_UPPER_LIMIT < 1) {
         try { OCCUR_PLACE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurPlaceCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_PLACE_CODE_UPPER_LIMIT = 200; }
      }
      if (occurPlaceCode != null && !wt.fc.PersistenceHelper.checkStoredLength(occurPlaceCode.toString(), OCCUR_PLACE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurPlaceCode"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_PLACE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurPlaceCode", this.occurPlaceCode, occurPlaceCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String SERIES = "series";
   static int SERIES_UPPER_LIMIT = -1;
   java.lang.String series;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getSeries() {
      return series;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setSeries(java.lang.String series) throws wt.util.WTPropertyVetoException {
      seriesValidate(series);
      this.series = series;
   }
   void seriesValidate(java.lang.String series) throws wt.util.WTPropertyVetoException {
      if (SERIES_UPPER_LIMIT < 1) {
         try { SERIES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("series").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SERIES_UPPER_LIMIT = 200; }
      }
      if (series != null && !wt.fc.PersistenceHelper.checkStoredLength(series.toString(), SERIES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "series"), java.lang.String.valueOf(java.lang.Math.min(SERIES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "series", this.series, series));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ISSUE_CODE = "issueCode";
   static int ISSUE_CODE_UPPER_LIMIT = -1;
   java.lang.String issueCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getIssueCode() {
      return issueCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setIssueCode(java.lang.String issueCode) throws wt.util.WTPropertyVetoException {
      issueCodeValidate(issueCode);
      this.issueCode = issueCode;
   }
   void issueCodeValidate(java.lang.String issueCode) throws wt.util.WTPropertyVetoException {
      if (ISSUE_CODE_UPPER_LIMIT < 1) {
         try { ISSUE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("issueCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ISSUE_CODE_UPPER_LIMIT = 200; }
      }
      if (issueCode != null && !wt.fc.PersistenceHelper.checkStoredLength(issueCode.toString(), ISSUE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "issueCode"), java.lang.String.valueOf(java.lang.Math.min(ISSUE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "issueCode", this.issueCode, issueCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ISSUE_NAME = "issueName";
   static int ISSUE_NAME_UPPER_LIMIT = -1;
   java.lang.String issueName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getIssueName() {
      return issueName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setIssueName(java.lang.String issueName) throws wt.util.WTPropertyVetoException {
      issueNameValidate(issueName);
      this.issueName = issueName;
   }
   void issueNameValidate(java.lang.String issueName) throws wt.util.WTPropertyVetoException {
      if (ISSUE_NAME_UPPER_LIMIT < 1) {
         try { ISSUE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("issueName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ISSUE_NAME_UPPER_LIMIT = 200; }
      }
      if (issueName != null && !wt.fc.PersistenceHelper.checkStoredLength(issueName.toString(), ISSUE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "issueName"), java.lang.String.valueOf(java.lang.Math.min(ISSUE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "issueName", this.issueName, issueName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_POINT_CODE = "occurPointCode";
   static int OCCUR_POINT_CODE_UPPER_LIMIT = -1;
   java.lang.String occurPointCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurPointCode() {
      return occurPointCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurPointCode(java.lang.String occurPointCode) throws wt.util.WTPropertyVetoException {
      occurPointCodeValidate(occurPointCode);
      this.occurPointCode = occurPointCode;
   }
   void occurPointCodeValidate(java.lang.String occurPointCode) throws wt.util.WTPropertyVetoException {
      if (OCCUR_POINT_CODE_UPPER_LIMIT < 1) {
         try { OCCUR_POINT_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurPointCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_POINT_CODE_UPPER_LIMIT = 200; }
      }
      if (occurPointCode != null && !wt.fc.PersistenceHelper.checkStoredLength(occurPointCode.toString(), OCCUR_POINT_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurPointCode"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_POINT_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurPointCode", this.occurPointCode, occurPointCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String OCCUR_POINT_NAME = "occurPointName";
   static int OCCUR_POINT_NAME_UPPER_LIMIT = -1;
   java.lang.String occurPointName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getOccurPointName() {
      return occurPointName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setOccurPointName(java.lang.String occurPointName) throws wt.util.WTPropertyVetoException {
      occurPointNameValidate(occurPointName);
      this.occurPointName = occurPointName;
   }
   void occurPointNameValidate(java.lang.String occurPointName) throws wt.util.WTPropertyVetoException {
      if (OCCUR_POINT_NAME_UPPER_LIMIT < 1) {
         try { OCCUR_POINT_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("occurPointName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OCCUR_POINT_NAME_UPPER_LIMIT = 200; }
      }
      if (occurPointName != null && !wt.fc.PersistenceHelper.checkStoredLength(occurPointName.toString(), OCCUR_POINT_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "occurPointName"), java.lang.String.valueOf(java.lang.Math.min(OCCUR_POINT_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "occurPointName", this.occurPointName, occurPointName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String IMPORTANCE_CODE = "importanceCode";
   static int IMPORTANCE_CODE_UPPER_LIMIT = -1;
   java.lang.String importanceCode;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getImportanceCode() {
      return importanceCode;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setImportanceCode(java.lang.String importanceCode) throws wt.util.WTPropertyVetoException {
      importanceCodeValidate(importanceCode);
      this.importanceCode = importanceCode;
   }
   void importanceCodeValidate(java.lang.String importanceCode) throws wt.util.WTPropertyVetoException {
      if (IMPORTANCE_CODE_UPPER_LIMIT < 1) {
         try { IMPORTANCE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("importanceCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IMPORTANCE_CODE_UPPER_LIMIT = 200; }
      }
      if (importanceCode != null && !wt.fc.PersistenceHelper.checkStoredLength(importanceCode.toString(), IMPORTANCE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "importanceCode"), java.lang.String.valueOf(java.lang.Math.min(IMPORTANCE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "importanceCode", this.importanceCode, importanceCode));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String IMPORTANCE_NAME = "importanceName";
   static int IMPORTANCE_NAME_UPPER_LIMIT = -1;
   java.lang.String importanceName;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public java.lang.String getImportanceName() {
      return importanceName;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setImportanceName(java.lang.String importanceName) throws wt.util.WTPropertyVetoException {
      importanceNameValidate(importanceName);
      this.importanceName = importanceName;
   }
   void importanceNameValidate(java.lang.String importanceName) throws wt.util.WTPropertyVetoException {
      if (IMPORTANCE_NAME_UPPER_LIMIT < 1) {
         try { IMPORTANCE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("importanceName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IMPORTANCE_NAME_UPPER_LIMIT = 200; }
      }
      if (importanceName != null && !wt.fc.PersistenceHelper.checkStoredLength(importanceName.toString(), IMPORTANCE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "importanceName"), java.lang.String.valueOf(java.lang.Math.min(IMPORTANCE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "importanceName", this.importanceName, importanceName));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String USER = "user";
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PART = "part";
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PART_REFERENCE = "partReference";
   wt.fc.ObjectReference partReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.part.WTPart getPart() {
      return (partReference != null) ? (wt.part.WTPart) partReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.fc.ObjectReference getPartReference() {
      return partReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPart(wt.part.WTPart the_part) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartReference(the_part == null ? null : wt.fc.ObjectReference.newObjectReference((wt.part.WTPart) the_part));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPartReference(wt.fc.ObjectReference the_partReference) throws wt.util.WTPropertyVetoException {
      partReferenceValidate(the_partReference);
      partReference = (wt.fc.ObjectReference) the_partReference;
   }
   void partReferenceValidate(wt.fc.ObjectReference the_partReference) throws wt.util.WTPropertyVetoException {
      if (the_partReference != null && the_partReference.getReferencedClass() != null &&
            !wt.part.WTPart.class.isAssignableFrom(the_partReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "partReference", this.partReference, partReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PM_USER = "pmUser";
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PM_USER_REFERENCE = "pmUserReference";
   wt.fc.ObjectReference pmUserReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.org.WTUser getPmUser() {
      return (pmUserReference != null) ? (wt.org.WTUser) pmUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.fc.ObjectReference getPmUserReference() {
      return pmUserReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPmUser(wt.org.WTUser the_pmUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPmUserReference(the_pmUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_pmUser));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setPmUserReference(wt.fc.ObjectReference the_pmUserReference) throws wt.util.WTPropertyVetoException {
      pmUserReferenceValidate(the_pmUserReference);
      pmUserReference = (wt.fc.ObjectReference) the_pmUserReference;
   }
   void pmUserReferenceValidate(wt.fc.ObjectReference the_pmUserReference) throws wt.util.WTPropertyVetoException {
      if (the_pmUserReference != null && the_pmUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_pmUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pmUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "pmUserReference", this.pmUserReference, pmUserReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PRODUCT_PROJECT = "productProject";
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String PRODUCT_PROJECT_REFERENCE = "productProjectReference";
   wt.fc.ObjectReference productProjectReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public e3ps.project.ProductProject getProductProject() {
      return (productProjectReference != null) ? (e3ps.project.ProductProject) productProjectReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.fc.ObjectReference getProductProjectReference() {
      return productProjectReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setProductProject(e3ps.project.ProductProject the_productProject) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProductProjectReference(the_productProject == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProductProject) the_productProject));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setProductProjectReference(wt.fc.ObjectReference the_productProjectReference) throws wt.util.WTPropertyVetoException {
      productProjectReferenceValidate(the_productProjectReference);
      productProjectReference = (wt.fc.ObjectReference) the_productProjectReference;
   }
   void productProjectReferenceValidate(wt.fc.ObjectReference the_productProjectReference) throws wt.util.WTPropertyVetoException {
      if (the_productProjectReference != null && the_productProjectReference.getReferencedClass() != null &&
            !e3ps.project.ProductProject.class.isAssignableFrom(the_productProjectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productProjectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "productProjectReference", this.productProjectReference, productProjectReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CLOSE_USER = "closeUser";
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String CLOSE_USER_REFERENCE = "closeUserReference";
   wt.fc.ObjectReference closeUserReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.org.WTUser getCloseUser() {
      return (closeUserReference != null) ? (wt.org.WTUser) closeUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.fc.ObjectReference getCloseUserReference() {
      return closeUserReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCloseUser(wt.org.WTUser the_closeUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCloseUserReference(the_closeUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_closeUser));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setCloseUserReference(wt.fc.ObjectReference the_closeUserReference) throws wt.util.WTPropertyVetoException {
      closeUserReferenceValidate(the_closeUserReference);
      closeUserReference = (wt.fc.ObjectReference) the_closeUserReference;
   }
   void closeUserReferenceValidate(wt.fc.ObjectReference the_closeUserReference) throws wt.util.WTPropertyVetoException {
      if (the_closeUserReference != null && the_closeUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_closeUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "closeUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "closeUserReference", this.closeUserReference, closeUserReference));
   }

   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ACTION_USER = "actionUser";
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public static final java.lang.String ACTION_USER_REFERENCE = "actionUserReference";
   wt.fc.ObjectReference actionUserReference;
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.org.WTUser getActionUser() {
      return (actionUserReference != null) ? (wt.org.WTUser) actionUserReference.getObject() : null;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public wt.fc.ObjectReference getActionUserReference() {
      return actionUserReference;
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setActionUser(wt.org.WTUser the_actionUser) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setActionUserReference(the_actionUser == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_actionUser));
   }
   /**
    * @see ext.ket.dqm.entity.KETDqmRaise
    */
   public void setActionUserReference(wt.fc.ObjectReference the_actionUserReference) throws wt.util.WTPropertyVetoException {
      actionUserReferenceValidate(the_actionUserReference);
      actionUserReference = (wt.fc.ObjectReference) the_actionUserReference;
   }
   void actionUserReferenceValidate(wt.fc.ObjectReference the_actionUserReference) throws wt.util.WTPropertyVetoException {
      if (the_actionUserReference != null && the_actionUserReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_actionUserReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "actionUserReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "actionUserReference", this.actionUserReference, actionUserReference));
   }

   wt.content.ContentItem primary;
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public wt.content.ContentItem getPrimary() {
      return primary;
   }
   /**
    * This is a non-persistent ContentItem for FormatContentHolders that is used to pass the primary content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.FormatContentHolder
    */
   public void setPrimary(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
      primaryValidate(primary);
      this.primary = primary;
   }
   void primaryValidate(wt.content.ContentItem primary) throws wt.util.WTPropertyVetoException {
   }

   wt.content.DataFormatReference format;
   /**
    * @see wt.content.FormatContentHolder
    */
   public wt.content.DataFormatReference getFormat() {
      return format;
   }
   /**
    * @see wt.content.FormatContentHolder
    */
   public void setFormat(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      formatValidate(format);
      this.format = format;
   }
   void formatValidate(wt.content.DataFormatReference format) throws wt.util.WTPropertyVetoException {
      if (format == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "format") },
               new java.beans.PropertyChangeEvent(this, "format", this.format, format));
   }

   /**
    * Derived from {@link wt.content.DataFormatReference#getFormatName()}
    *
    * @see wt.content.FormatContentHolder
    */
   public java.lang.String getFormatName() {
      try { return (java.lang.String) ((wt.content.DataFormatReference) getFormat()).getFormatName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   java.util.Vector contentVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getContentVector() {
      return contentVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setContentVector(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
      contentVectorValidate(contentVector);
      this.contentVector = contentVector;
   }
   void contentVectorValidate(java.util.Vector contentVector) throws wt.util.WTPropertyVetoException {
   }

   boolean hasContents;
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public boolean isHasContents() {
      return hasContents;
   }
   /**
    * This is a non-persistent variable that is used to pass information from server to client.  Should not be directly accessed by the client
    *
    * @see wt.content.ContentHolder
    */
   public void setHasContents(boolean hasContents) throws wt.util.WTPropertyVetoException {
      hasContentsValidate(hasContents);
      this.hasContents = hasContents;
   }
   void hasContentsValidate(boolean hasContents) throws wt.util.WTPropertyVetoException {
   }

   wt.content.HttpContentOperation operation;
   /**
    * @see wt.content.ContentHolder
    */
   public wt.content.HttpContentOperation getOperation() {
      return operation;
   }
   /**
    * @see wt.content.ContentHolder
    */
   public void setOperation(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
      operationValidate(operation);
      this.operation = operation;
   }
   void operationValidate(wt.content.HttpContentOperation operation) throws wt.util.WTPropertyVetoException {
   }

   java.util.Vector httpVector;
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public java.util.Vector getHttpVector() {
      return httpVector;
   }
   /**
    * This is a non-persistent vector that is used to pass content from server to client.  Should not be directly accessed by the client.
    *
    * @see wt.content.ContentHolder
    */
   public void setHttpVector(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
      httpVectorValidate(httpVector);
      this.httpVector = httpVector;
   }
   void httpVectorValidate(java.util.Vector httpVector) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -7238715873701051391L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( actionDeptCode );
      output.writeObject( actionDeptName );
      output.writeObject( actionUserDeptName );
      output.writeObject( actionUserName );
      output.writeObject( actionUserReference );
      output.writeObject( applyArea1 );
      output.writeObject( applyArea2 );
      output.writeObject( applyArea3 );
      output.writeObject( cartypeCode );
      output.writeObject( cartypeName );
      output.writeObject( closeUserReference );
      output.writeObject( containerReference );
      output.writeObject( creatorDeptCode );
      output.writeObject( creatorDeptName );
      output.writeObject( creatorUserName );
      output.writeObject( customerCode );
      output.writeObject( customerName );
      output.writeObject( defectDivCode );
      output.writeObject( defectDivName );
      output.writeObject( defectTypeCode );
      output.writeObject( defectTypeName );
      output.writeObject( format );
      output.writeObject( importanceCode );
      output.writeObject( importanceName );
      output.writeObject( issueCode );
      output.writeObject( issueName );
      output.writeObject( occurCode );
      output.writeObject( occurDate );
      output.writeObject( occurDivCode );
      output.writeObject( occurDivName );
      output.writeObject( occurName );
      output.writeObject( occurPlaceCode );
      output.writeObject( occurPlaceName );
      output.writeObject( occurPointCode );
      output.writeObject( occurPointName );
      output.writeObject( occurStepCode );
      output.writeObject( occurStepName );
      output.writeObject( partCategoryCode );
      output.writeObject( partCategoryName );
      output.writeObject( partReference );
      output.writeObject( pjtname );
      output.writeObject( pjtno );
      output.writeObject( pmUserReference );
      output.writeObject( problemTypeCode );
      output.writeObject( problemTypeName );
      output.writeObject( productProjectReference );
      output.writeObject( requestDate );
      output.writeObject( series );
      output.writeObject( urgencyCode );
      output.writeObject( urgencyName );
      output.writeObject( userReference );
      output.writeObject( webEditor );
      output.writeObject( webEditorText );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
         output.writeObject( primary );
      }

   }

   protected void super_writeExternal_KETDqmRaise(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.dqm.entity.KETDqmRaise) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETDqmRaise(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "actionDeptCode", actionDeptCode );
      output.setString( "actionDeptName", actionDeptName );
      output.setString( "actionUserDeptName", actionUserDeptName );
      output.setString( "actionUserName", actionUserName );
      output.writeObject( "actionUserReference", actionUserReference, wt.fc.ObjectReference.class, true );
      output.setString( "applyArea1", applyArea1 );
      output.setString( "applyArea2", applyArea2 );
      output.setString( "applyArea3", applyArea3 );
      output.setString( "cartypeCode", cartypeCode );
      output.setString( "cartypeName", cartypeName );
      output.writeObject( "closeUserReference", closeUserReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "creatorDeptCode", creatorDeptCode );
      output.setString( "creatorDeptName", creatorDeptName );
      output.setString( "creatorUserName", creatorUserName );
      output.setString( "customerCode", customerCode );
      output.setString( "customerName", customerName );
      output.setString( "defectDivCode", defectDivCode );
      output.setString( "defectDivName", defectDivName );
      output.setString( "defectTypeCode", defectTypeCode );
      output.setString( "defectTypeName", defectTypeName );
      output.writeObject( "format", format, wt.content.DataFormatReference.class, true );
      output.setString( "importanceCode", importanceCode );
      output.setString( "importanceName", importanceName );
      output.setString( "issueCode", issueCode );
      output.setString( "issueName", issueName );
      output.setString( "occurCode", occurCode );
      output.setTimestamp( "occurDate", occurDate );
      output.setString( "occurDivCode", occurDivCode );
      output.setString( "occurDivName", occurDivName );
      output.setString( "occurName", occurName );
      output.setString( "occurPlaceCode", occurPlaceCode );
      output.setString( "occurPlaceName", occurPlaceName );
      output.setString( "occurPointCode", occurPointCode );
      output.setString( "occurPointName", occurPointName );
      output.setString( "occurStepCode", occurStepCode );
      output.setString( "occurStepName", occurStepName );
      output.setString( "partCategoryCode", partCategoryCode );
      output.setString( "partCategoryName", partCategoryName );
      output.writeObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      output.setString( "pjtname", pjtname );
      output.setString( "pjtno", pjtno );
      output.writeObject( "pmUserReference", pmUserReference, wt.fc.ObjectReference.class, true );
      output.setString( "problemTypeCode", problemTypeCode );
      output.setString( "problemTypeName", problemTypeName );
      output.writeObject( "productProjectReference", productProjectReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "requestDate", requestDate );
      output.setString( "series", series );
      output.setString( "urgencyCode", urgencyCode );
      output.setString( "urgencyName", urgencyName );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      output.setObject( "webEditor", webEditor );
      output.setObject( "webEditorText", webEditorText );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      actionDeptCode = input.getString( "actionDeptCode" );
      actionDeptName = input.getString( "actionDeptName" );
      actionUserDeptName = input.getString( "actionUserDeptName" );
      actionUserName = input.getString( "actionUserName" );
      actionUserReference = (wt.fc.ObjectReference) input.readObject( "actionUserReference", actionUserReference, wt.fc.ObjectReference.class, true );
      applyArea1 = input.getString( "applyArea1" );
      applyArea2 = input.getString( "applyArea2" );
      applyArea3 = input.getString( "applyArea3" );
      cartypeCode = input.getString( "cartypeCode" );
      cartypeName = input.getString( "cartypeName" );
      closeUserReference = (wt.fc.ObjectReference) input.readObject( "closeUserReference", closeUserReference, wt.fc.ObjectReference.class, true );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      creatorDeptCode = input.getString( "creatorDeptCode" );
      creatorDeptName = input.getString( "creatorDeptName" );
      creatorUserName = input.getString( "creatorUserName" );
      customerCode = input.getString( "customerCode" );
      customerName = input.getString( "customerName" );
      defectDivCode = input.getString( "defectDivCode" );
      defectDivName = input.getString( "defectDivName" );
      defectTypeCode = input.getString( "defectTypeCode" );
      defectTypeName = input.getString( "defectTypeName" );
      format = (wt.content.DataFormatReference) input.readObject( "format", format, wt.content.DataFormatReference.class, true );
      importanceCode = input.getString( "importanceCode" );
      importanceName = input.getString( "importanceName" );
      issueCode = input.getString( "issueCode" );
      issueName = input.getString( "issueName" );
      occurCode = input.getString( "occurCode" );
      occurDate = input.getTimestamp( "occurDate" );
      occurDivCode = input.getString( "occurDivCode" );
      occurDivName = input.getString( "occurDivName" );
      occurName = input.getString( "occurName" );
      occurPlaceCode = input.getString( "occurPlaceCode" );
      occurPlaceName = input.getString( "occurPlaceName" );
      occurPointCode = input.getString( "occurPointCode" );
      occurPointName = input.getString( "occurPointName" );
      occurStepCode = input.getString( "occurStepCode" );
      occurStepName = input.getString( "occurStepName" );
      partCategoryCode = input.getString( "partCategoryCode" );
      partCategoryName = input.getString( "partCategoryName" );
      partReference = (wt.fc.ObjectReference) input.readObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      pjtname = input.getString( "pjtname" );
      pjtno = input.getString( "pjtno" );
      pmUserReference = (wt.fc.ObjectReference) input.readObject( "pmUserReference", pmUserReference, wt.fc.ObjectReference.class, true );
      problemTypeCode = input.getString( "problemTypeCode" );
      problemTypeName = input.getString( "problemTypeName" );
      productProjectReference = (wt.fc.ObjectReference) input.readObject( "productProjectReference", productProjectReference, wt.fc.ObjectReference.class, true );
      requestDate = input.getTimestamp( "requestDate" );
      series = input.getString( "series" );
      urgencyCode = input.getString( "urgencyCode" );
      urgencyName = input.getString( "urgencyName" );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      webEditor = (java.lang.Object) input.getObject( "webEditor" );
      webEditorText = (java.lang.Object) input.getObject( "webEditorText" );
   }

   boolean readVersion_7238715873701051391L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      actionDeptCode = (java.lang.String) input.readObject();
      actionDeptName = (java.lang.String) input.readObject();
      actionUserDeptName = (java.lang.String) input.readObject();
      actionUserName = (java.lang.String) input.readObject();
      actionUserReference = (wt.fc.ObjectReference) input.readObject();
      applyArea1 = (java.lang.String) input.readObject();
      applyArea2 = (java.lang.String) input.readObject();
      applyArea3 = (java.lang.String) input.readObject();
      cartypeCode = (java.lang.String) input.readObject();
      cartypeName = (java.lang.String) input.readObject();
      closeUserReference = (wt.fc.ObjectReference) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      creatorDeptCode = (java.lang.String) input.readObject();
      creatorDeptName = (java.lang.String) input.readObject();
      creatorUserName = (java.lang.String) input.readObject();
      customerCode = (java.lang.String) input.readObject();
      customerName = (java.lang.String) input.readObject();
      defectDivCode = (java.lang.String) input.readObject();
      defectDivName = (java.lang.String) input.readObject();
      defectTypeCode = (java.lang.String) input.readObject();
      defectTypeName = (java.lang.String) input.readObject();
      format = (wt.content.DataFormatReference) input.readObject();
      importanceCode = (java.lang.String) input.readObject();
      importanceName = (java.lang.String) input.readObject();
      issueCode = (java.lang.String) input.readObject();
      issueName = (java.lang.String) input.readObject();
      occurCode = (java.lang.String) input.readObject();
      occurDate = (java.sql.Timestamp) input.readObject();
      occurDivCode = (java.lang.String) input.readObject();
      occurDivName = (java.lang.String) input.readObject();
      occurName = (java.lang.String) input.readObject();
      occurPlaceCode = (java.lang.String) input.readObject();
      occurPlaceName = (java.lang.String) input.readObject();
      occurPointCode = (java.lang.String) input.readObject();
      occurPointName = (java.lang.String) input.readObject();
      occurStepCode = (java.lang.String) input.readObject();
      occurStepName = (java.lang.String) input.readObject();
      partCategoryCode = (java.lang.String) input.readObject();
      partCategoryName = (java.lang.String) input.readObject();
      partReference = (wt.fc.ObjectReference) input.readObject();
      pjtname = (java.lang.String) input.readObject();
      pjtno = (java.lang.String) input.readObject();
      pmUserReference = (wt.fc.ObjectReference) input.readObject();
      problemTypeCode = (java.lang.String) input.readObject();
      problemTypeName = (java.lang.String) input.readObject();
      productProjectReference = (wt.fc.ObjectReference) input.readObject();
      requestDate = (java.sql.Timestamp) input.readObject();
      series = (java.lang.String) input.readObject();
      urgencyCode = (java.lang.String) input.readObject();
      urgencyName = (java.lang.String) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      webEditor = (java.lang.Object) input.readObject();
      webEditorText = (java.lang.Object) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
            primary = (wt.content.ContentItem) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( KETDqmRaise thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_7238715873701051391L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETDqmRaise( _KETDqmRaise thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
