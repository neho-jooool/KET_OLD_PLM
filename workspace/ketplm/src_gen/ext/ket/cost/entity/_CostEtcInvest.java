package ext.ket.cost.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CostEtcInvest implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.cost.entity.entityResource";
   static final java.lang.String CLASSNAME = CostEtcInvest.class.getName();

   /**
    * 항목
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ITEM_NAME = "itemName";
   static int ITEM_NAME_UPPER_LIMIT = -1;
   java.lang.String itemName;
   /**
    * 항목
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getItemName() {
      return itemName;
   }
   /**
    * 항목
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setItemName(java.lang.String itemName) throws wt.util.WTPropertyVetoException {
      itemNameValidate(itemName);
      this.itemName = itemName;
   }
   void itemNameValidate(java.lang.String itemName) throws wt.util.WTPropertyVetoException {
      if (ITEM_NAME_UPPER_LIMIT < 1) {
         try { ITEM_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("itemName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ITEM_NAME_UPPER_LIMIT = 200; }
      }
      if (itemName != null && !wt.fc.PersistenceHelper.checkStoredLength(itemName.toString(), ITEM_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "itemName"), java.lang.String.valueOf(java.lang.Math.min(ITEM_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "itemName", this.itemName, itemName));
   }

   /**
    * 신규 제작처
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_NFACTORY = "etcNfactory";
   static int ETC_NFACTORY_UPPER_LIMIT = -1;
   java.lang.String etcNfactory;
   /**
    * 신규 제작처
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcNfactory() {
      return etcNfactory;
   }
   /**
    * 신규 제작처
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcNfactory(java.lang.String etcNfactory) throws wt.util.WTPropertyVetoException {
      etcNfactoryValidate(etcNfactory);
      this.etcNfactory = etcNfactory;
   }
   void etcNfactoryValidate(java.lang.String etcNfactory) throws wt.util.WTPropertyVetoException {
      if (ETC_NFACTORY_UPPER_LIMIT < 1) {
         try { ETC_NFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNfactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NFACTORY_UPPER_LIMIT = 200; }
      }
      if (etcNfactory != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNfactory.toString(), ETC_NFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNfactory"), java.lang.String.valueOf(java.lang.Math.min(ETC_NFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNfactory", this.etcNfactory, etcNfactory));
   }

   /**
    * 신규 투자비
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_NCOST = "etcNcost";
   static int ETC_NCOST_UPPER_LIMIT = -1;
   java.lang.String etcNcost;
   /**
    * 신규 투자비
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcNcost() {
      return etcNcost;
   }
   /**
    * 신규 투자비
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcNcost(java.lang.String etcNcost) throws wt.util.WTPropertyVetoException {
      etcNcostValidate(etcNcost);
      this.etcNcost = etcNcost;
   }
   void etcNcostValidate(java.lang.String etcNcost) throws wt.util.WTPropertyVetoException {
      if (ETC_NCOST_UPPER_LIMIT < 1) {
         try { ETC_NCOST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNcost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NCOST_UPPER_LIMIT = 200; }
      }
      if (etcNcost != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNcost.toString(), ETC_NCOST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNcost"), java.lang.String.valueOf(java.lang.Math.min(ETC_NCOST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNcost", this.etcNcost, etcNcost));
   }

   /**
    * 신규 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_NUNIT_1 = "etcNunit_1";
   static int ETC_NUNIT_1_UPPER_LIMIT = -1;
   java.lang.String etcNunit_1;
   /**
    * 신규 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcNunit_1() {
      return etcNunit_1;
   }
   /**
    * 신규 투자비 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcNunit_1(java.lang.String etcNunit_1) throws wt.util.WTPropertyVetoException {
      etcNunit_1Validate(etcNunit_1);
      this.etcNunit_1 = etcNunit_1;
   }
   void etcNunit_1Validate(java.lang.String etcNunit_1) throws wt.util.WTPropertyVetoException {
      if (ETC_NUNIT_1_UPPER_LIMIT < 1) {
         try { ETC_NUNIT_1_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNunit_1").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NUNIT_1_UPPER_LIMIT = 200; }
      }
      if (etcNunit_1 != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNunit_1.toString(), ETC_NUNIT_1_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNunit_1"), java.lang.String.valueOf(java.lang.Math.min(ETC_NUNIT_1_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNunit_1", this.etcNunit_1, etcNunit_1));
   }

   /**
    * 신규 지급액
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_NPAY = "etcNpay";
   static int ETC_NPAY_UPPER_LIMIT = -1;
   java.lang.String etcNpay;
   /**
    * 신규 지급액
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcNpay() {
      return etcNpay;
   }
   /**
    * 신규 지급액
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcNpay(java.lang.String etcNpay) throws wt.util.WTPropertyVetoException {
      etcNpayValidate(etcNpay);
      this.etcNpay = etcNpay;
   }
   void etcNpayValidate(java.lang.String etcNpay) throws wt.util.WTPropertyVetoException {
      if (ETC_NPAY_UPPER_LIMIT < 1) {
         try { ETC_NPAY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNpay").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NPAY_UPPER_LIMIT = 200; }
      }
      if (etcNpay != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNpay.toString(), ETC_NPAY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNpay"), java.lang.String.valueOf(java.lang.Math.min(ETC_NPAY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNpay", this.etcNpay, etcNpay));
   }

   /**
    * 신규 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_NUNIT_2 = "etcNunit_2";
   static int ETC_NUNIT_2_UPPER_LIMIT = -1;
   java.lang.String etcNunit_2;
   /**
    * 신규 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcNunit_2() {
      return etcNunit_2;
   }
   /**
    * 신규 지급액 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcNunit_2(java.lang.String etcNunit_2) throws wt.util.WTPropertyVetoException {
      etcNunit_2Validate(etcNunit_2);
      this.etcNunit_2 = etcNunit_2;
   }
   void etcNunit_2Validate(java.lang.String etcNunit_2) throws wt.util.WTPropertyVetoException {
      if (ETC_NUNIT_2_UPPER_LIMIT < 1) {
         try { ETC_NUNIT_2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcNunit_2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_NUNIT_2_UPPER_LIMIT = 200; }
      }
      if (etcNunit_2 != null && !wt.fc.PersistenceHelper.checkStoredLength(etcNunit_2.toString(), ETC_NUNIT_2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcNunit_2"), java.lang.String.valueOf(java.lang.Math.min(ETC_NUNIT_2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcNunit_2", this.etcNunit_2, etcNunit_2));
   }

   /**
    * 양산 제작처
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_PFACTORY = "etcPfactory";
   static int ETC_PFACTORY_UPPER_LIMIT = -1;
   java.lang.String etcPfactory;
   /**
    * 양산 제작처
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcPfactory() {
      return etcPfactory;
   }
   /**
    * 양산 제작처
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcPfactory(java.lang.String etcPfactory) throws wt.util.WTPropertyVetoException {
      etcPfactoryValidate(etcPfactory);
      this.etcPfactory = etcPfactory;
   }
   void etcPfactoryValidate(java.lang.String etcPfactory) throws wt.util.WTPropertyVetoException {
      if (ETC_PFACTORY_UPPER_LIMIT < 1) {
         try { ETC_PFACTORY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcPfactory").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_PFACTORY_UPPER_LIMIT = 200; }
      }
      if (etcPfactory != null && !wt.fc.PersistenceHelper.checkStoredLength(etcPfactory.toString(), ETC_PFACTORY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcPfactory"), java.lang.String.valueOf(java.lang.Math.min(ETC_PFACTORY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcPfactory", this.etcPfactory, etcPfactory));
   }

   /**
    * 양산 투자비
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_PCOST = "etcPcost";
   static int ETC_PCOST_UPPER_LIMIT = -1;
   java.lang.String etcPcost;
   /**
    * 양산 투자비
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcPcost() {
      return etcPcost;
   }
   /**
    * 양산 투자비
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcPcost(java.lang.String etcPcost) throws wt.util.WTPropertyVetoException {
      etcPcostValidate(etcPcost);
      this.etcPcost = etcPcost;
   }
   void etcPcostValidate(java.lang.String etcPcost) throws wt.util.WTPropertyVetoException {
      if (ETC_PCOST_UPPER_LIMIT < 1) {
         try { ETC_PCOST_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcPcost").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_PCOST_UPPER_LIMIT = 200; }
      }
      if (etcPcost != null && !wt.fc.PersistenceHelper.checkStoredLength(etcPcost.toString(), ETC_PCOST_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcPcost"), java.lang.String.valueOf(java.lang.Math.min(ETC_PCOST_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcPcost", this.etcPcost, etcPcost));
   }

   /**
    * 양산 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_PUNIT = "etcPunit";
   static int ETC_PUNIT_UPPER_LIMIT = -1;
   java.lang.String etcPunit;
   /**
    * 양산 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcPunit() {
      return etcPunit;
   }
   /**
    * 양산 화폐단위
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcPunit(java.lang.String etcPunit) throws wt.util.WTPropertyVetoException {
      etcPunitValidate(etcPunit);
      this.etcPunit = etcPunit;
   }
   void etcPunitValidate(java.lang.String etcPunit) throws wt.util.WTPropertyVetoException {
      if (ETC_PUNIT_UPPER_LIMIT < 1) {
         try { ETC_PUNIT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcPunit").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_PUNIT_UPPER_LIMIT = 200; }
      }
      if (etcPunit != null && !wt.fc.PersistenceHelper.checkStoredLength(etcPunit.toString(), ETC_PUNIT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcPunit"), java.lang.String.valueOf(java.lang.Math.min(ETC_PUNIT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcPunit", this.etcPunit, etcPunit));
   }

   /**
    * 양산수량 Total
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_TOTAL_QTY = "etcTotalQty";
   static int ETC_TOTAL_QTY_UPPER_LIMIT = -1;
   java.lang.String etcTotalQty;
   /**
    * 양산수량 Total
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcTotalQty() {
      return etcTotalQty;
   }
   /**
    * 양산수량 Total
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcTotalQty(java.lang.String etcTotalQty) throws wt.util.WTPropertyVetoException {
      etcTotalQtyValidate(etcTotalQty);
      this.etcTotalQty = etcTotalQty;
   }
   void etcTotalQtyValidate(java.lang.String etcTotalQty) throws wt.util.WTPropertyVetoException {
      if (ETC_TOTAL_QTY_UPPER_LIMIT < 1) {
         try { ETC_TOTAL_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcTotalQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_TOTAL_QTY_UPPER_LIMIT = 200; }
      }
      if (etcTotalQty != null && !wt.fc.PersistenceHelper.checkStoredLength(etcTotalQty.toString(), ETC_TOTAL_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcTotalQty"), java.lang.String.valueOf(java.lang.Math.min(ETC_TOTAL_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcTotalQty", this.etcTotalQty, etcTotalQty));
   }

   /**
    * 양산수량 MAX
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public static final java.lang.String ETC_MAX_QTY = "etcMaxQty";
   static int ETC_MAX_QTY_UPPER_LIMIT = -1;
   java.lang.String etcMaxQty;
   /**
    * 양산수량 MAX
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public java.lang.String getEtcMaxQty() {
      return etcMaxQty;
   }
   /**
    * 양산수량 MAX
    *
    * @see ext.ket.cost.entity.CostEtcInvest
    */
   public void setEtcMaxQty(java.lang.String etcMaxQty) throws wt.util.WTPropertyVetoException {
      etcMaxQtyValidate(etcMaxQty);
      this.etcMaxQty = etcMaxQty;
   }
   void etcMaxQtyValidate(java.lang.String etcMaxQty) throws wt.util.WTPropertyVetoException {
      if (ETC_MAX_QTY_UPPER_LIMIT < 1) {
         try { ETC_MAX_QTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("etcMaxQty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ETC_MAX_QTY_UPPER_LIMIT = 200; }
      }
      if (etcMaxQty != null && !wt.fc.PersistenceHelper.checkStoredLength(etcMaxQty.toString(), ETC_MAX_QTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "etcMaxQty"), java.lang.String.valueOf(java.lang.Math.min(ETC_MAX_QTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "etcMaxQty", this.etcMaxQty, etcMaxQty));
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

   public static final long EXTERNALIZATION_VERSION_UID = -1430628569625412610L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( etcMaxQty );
      output.writeObject( etcNcost );
      output.writeObject( etcNfactory );
      output.writeObject( etcNpay );
      output.writeObject( etcNunit_1 );
      output.writeObject( etcNunit_2 );
      output.writeObject( etcPcost );
      output.writeObject( etcPfactory );
      output.writeObject( etcPunit );
      output.writeObject( etcTotalQty );
      output.writeObject( itemName );
      output.writeObject( owner );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.cost.entity.CostEtcInvest) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "etcMaxQty", etcMaxQty );
      output.setString( "etcNcost", etcNcost );
      output.setString( "etcNfactory", etcNfactory );
      output.setString( "etcNpay", etcNpay );
      output.setString( "etcNunit_1", etcNunit_1 );
      output.setString( "etcNunit_2", etcNunit_2 );
      output.setString( "etcPcost", etcPcost );
      output.setString( "etcPfactory", etcPfactory );
      output.setString( "etcPunit", etcPunit );
      output.setString( "etcTotalQty", etcTotalQty );
      output.setString( "itemName", itemName );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      etcMaxQty = input.getString( "etcMaxQty" );
      etcNcost = input.getString( "etcNcost" );
      etcNfactory = input.getString( "etcNfactory" );
      etcNpay = input.getString( "etcNpay" );
      etcNunit_1 = input.getString( "etcNunit_1" );
      etcNunit_2 = input.getString( "etcNunit_2" );
      etcPcost = input.getString( "etcPcost" );
      etcPfactory = input.getString( "etcPfactory" );
      etcPunit = input.getString( "etcPunit" );
      etcTotalQty = input.getString( "etcTotalQty" );
      itemName = input.getString( "itemName" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion_1430628569625412610L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      etcMaxQty = (java.lang.String) input.readObject();
      etcNcost = (java.lang.String) input.readObject();
      etcNfactory = (java.lang.String) input.readObject();
      etcNpay = (java.lang.String) input.readObject();
      etcNunit_1 = (java.lang.String) input.readObject();
      etcNunit_2 = (java.lang.String) input.readObject();
      etcPcost = (java.lang.String) input.readObject();
      etcPfactory = (java.lang.String) input.readObject();
      etcPunit = (java.lang.String) input.readObject();
      etcTotalQty = (java.lang.String) input.readObject();
      itemName = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CostEtcInvest thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_1430628569625412610L( input, readSerialVersionUID, superDone );
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
