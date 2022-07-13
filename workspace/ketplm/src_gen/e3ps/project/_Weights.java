package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _Weights implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = Weights.class.getName();

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String QUALITY = "quality";
   int quality;
   /**
    * @see e3ps.project.Weights
    */
   public int getQuality() {
      return quality;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setQuality(int quality) throws wt.util.WTPropertyVetoException {
      qualityValidate(quality);
      this.quality = quality;
   }
   void qualityValidate(int quality) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String COST = "cost";
   int cost;
   /**
    * @see e3ps.project.Weights
    */
   public int getCost() {
      return cost;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setCost(int cost) throws wt.util.WTPropertyVetoException {
      costValidate(cost);
      this.cost = cost;
   }
   void costValidate(int cost) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String DELIVERY_ONE = "deliveryOne";
   int deliveryOne;
   /**
    * @see e3ps.project.Weights
    */
   public int getDeliveryOne() {
      return deliveryOne;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDeliveryOne(int deliveryOne) throws wt.util.WTPropertyVetoException {
      deliveryOneValidate(deliveryOne);
      this.deliveryOne = deliveryOne;
   }
   void deliveryOneValidate(int deliveryOne) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String DELIVERY_TWO = "deliveryTwo";
   int deliveryTwo;
   /**
    * @see e3ps.project.Weights
    */
   public int getDeliveryTwo() {
      return deliveryTwo;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDeliveryTwo(int deliveryTwo) throws wt.util.WTPropertyVetoException {
      deliveryTwoValidate(deliveryTwo);
      this.deliveryTwo = deliveryTwo;
   }
   void deliveryTwoValidate(int deliveryTwo) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String DELIVERY_THREE = "deliveryThree";
   int deliveryThree;
   /**
    * @see e3ps.project.Weights
    */
   public int getDeliveryThree() {
      return deliveryThree;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDeliveryThree(int deliveryThree) throws wt.util.WTPropertyVetoException {
      deliveryThreeValidate(deliveryThree);
      this.deliveryThree = deliveryThree;
   }
   void deliveryThreeValidate(int deliveryThree) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String TECHNICAL = "technical";
   int technical;
   /**
    * @see e3ps.project.Weights
    */
   public int getTechnical() {
      return technical;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTechnical(int technical) throws wt.util.WTPropertyVetoException {
      technicalValidate(technical);
      this.technical = technical;
   }
   void technicalValidate(int technical) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String W_TYPE = "wType";
   int wType;
   /**
    * @see e3ps.project.Weights
    */
   public int getWType() {
      return wType;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setWType(int wType) throws wt.util.WTPropertyVetoException {
      wTypeValidate(wType);
      this.wType = wType;
   }
   void wTypeValidate(int wType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String IS_PROJECT = "isProject";
   boolean isProject = false;
   /**
    * @see e3ps.project.Weights
    */
   public boolean isIsProject() {
      return isProject;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setIsProject(boolean isProject) throws wt.util.WTPropertyVetoException {
      isProjectValidate(isProject);
      this.isProject = isProject;
   }
   void isProjectValidate(boolean isProject) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String TOTAL_S = "totalS";
   int totalS;
   /**
    * @see e3ps.project.Weights
    */
   public int getTotalS() {
      return totalS;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTotalS(int totalS) throws wt.util.WTPropertyVetoException {
      totalSValidate(totalS);
      this.totalS = totalS;
   }
   void totalSValidate(int totalS) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String TOTAL_A = "totalA";
   int totalA;
   /**
    * @see e3ps.project.Weights
    */
   public int getTotalA() {
      return totalA;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTotalA(int totalA) throws wt.util.WTPropertyVetoException {
      totalAValidate(totalA);
      this.totalA = totalA;
   }
   void totalAValidate(int totalA) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String TOTAL_B = "totalB";
   int totalB;
   /**
    * @see e3ps.project.Weights
    */
   public int getTotalB() {
      return totalB;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTotalB(int totalB) throws wt.util.WTPropertyVetoException {
      totalBValidate(totalB);
      this.totalB = totalB;
   }
   void totalBValidate(int totalB) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String TOTAL_C = "totalC";
   int totalC;
   /**
    * @see e3ps.project.Weights
    */
   public int getTotalC() {
      return totalC;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTotalC(int totalC) throws wt.util.WTPropertyVetoException {
      totalCValidate(totalC);
      this.totalC = totalC;
   }
   void totalCValidate(int totalC) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String TOTAL_D = "totalD";
   int totalD;
   /**
    * @see e3ps.project.Weights
    */
   public int getTotalD() {
      return totalD;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTotalD(int totalD) throws wt.util.WTPropertyVetoException {
      totalDValidate(totalD);
      this.totalD = totalD;
   }
   void totalDValidate(int totalD) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String Q_S = "qS";
   static int Q_S_UPPER_LIMIT = -1;
   java.lang.String qS;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getQS() {
      return qS;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setQS(java.lang.String qS) throws wt.util.WTPropertyVetoException {
      qSValidate(qS);
      this.qS = qS;
   }
   void qSValidate(java.lang.String qS) throws wt.util.WTPropertyVetoException {
      if (Q_S_UPPER_LIMIT < 1) {
         try { Q_S_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("qS").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { Q_S_UPPER_LIMIT = 200; }
      }
      if (qS != null && !wt.fc.PersistenceHelper.checkStoredLength(qS.toString(), Q_S_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "qS"), java.lang.String.valueOf(java.lang.Math.min(Q_S_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "qS", this.qS, qS));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String Q_A = "qA";
   static int Q_A_UPPER_LIMIT = -1;
   java.lang.String qA;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getQA() {
      return qA;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setQA(java.lang.String qA) throws wt.util.WTPropertyVetoException {
      qAValidate(qA);
      this.qA = qA;
   }
   void qAValidate(java.lang.String qA) throws wt.util.WTPropertyVetoException {
      if (Q_A_UPPER_LIMIT < 1) {
         try { Q_A_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("qA").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { Q_A_UPPER_LIMIT = 200; }
      }
      if (qA != null && !wt.fc.PersistenceHelper.checkStoredLength(qA.toString(), Q_A_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "qA"), java.lang.String.valueOf(java.lang.Math.min(Q_A_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "qA", this.qA, qA));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String Q_B = "qB";
   static int Q_B_UPPER_LIMIT = -1;
   java.lang.String qB;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getQB() {
      return qB;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setQB(java.lang.String qB) throws wt.util.WTPropertyVetoException {
      qBValidate(qB);
      this.qB = qB;
   }
   void qBValidate(java.lang.String qB) throws wt.util.WTPropertyVetoException {
      if (Q_B_UPPER_LIMIT < 1) {
         try { Q_B_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("qB").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { Q_B_UPPER_LIMIT = 200; }
      }
      if (qB != null && !wt.fc.PersistenceHelper.checkStoredLength(qB.toString(), Q_B_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "qB"), java.lang.String.valueOf(java.lang.Math.min(Q_B_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "qB", this.qB, qB));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String Q_C = "qC";
   static int Q_C_UPPER_LIMIT = -1;
   java.lang.String qC;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getQC() {
      return qC;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setQC(java.lang.String qC) throws wt.util.WTPropertyVetoException {
      qCValidate(qC);
      this.qC = qC;
   }
   void qCValidate(java.lang.String qC) throws wt.util.WTPropertyVetoException {
      if (Q_C_UPPER_LIMIT < 1) {
         try { Q_C_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("qC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { Q_C_UPPER_LIMIT = 200; }
      }
      if (qC != null && !wt.fc.PersistenceHelper.checkStoredLength(qC.toString(), Q_C_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "qC"), java.lang.String.valueOf(java.lang.Math.min(Q_C_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "qC", this.qC, qC));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String Q_D = "qD";
   static int Q_D_UPPER_LIMIT = -1;
   java.lang.String qD;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getQD() {
      return qD;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setQD(java.lang.String qD) throws wt.util.WTPropertyVetoException {
      qDValidate(qD);
      this.qD = qD;
   }
   void qDValidate(java.lang.String qD) throws wt.util.WTPropertyVetoException {
      if (Q_D_UPPER_LIMIT < 1) {
         try { Q_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("qD").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { Q_D_UPPER_LIMIT = 200; }
      }
      if (qD != null && !wt.fc.PersistenceHelper.checkStoredLength(qD.toString(), Q_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "qD"), java.lang.String.valueOf(java.lang.Math.min(Q_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "qD", this.qD, qD));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C_S = "cS";
   static int C_S_UPPER_LIMIT = -1;
   java.lang.String cS;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getCS() {
      return cS;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setCS(java.lang.String cS) throws wt.util.WTPropertyVetoException {
      cSValidate(cS);
      this.cS = cS;
   }
   void cSValidate(java.lang.String cS) throws wt.util.WTPropertyVetoException {
      if (C_S_UPPER_LIMIT < 1) {
         try { C_S_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cS").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C_S_UPPER_LIMIT = 200; }
      }
      if (cS != null && !wt.fc.PersistenceHelper.checkStoredLength(cS.toString(), C_S_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cS"), java.lang.String.valueOf(java.lang.Math.min(C_S_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cS", this.cS, cS));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C_A = "cA";
   static int C_A_UPPER_LIMIT = -1;
   java.lang.String cA;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getCA() {
      return cA;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setCA(java.lang.String cA) throws wt.util.WTPropertyVetoException {
      cAValidate(cA);
      this.cA = cA;
   }
   void cAValidate(java.lang.String cA) throws wt.util.WTPropertyVetoException {
      if (C_A_UPPER_LIMIT < 1) {
         try { C_A_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cA").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C_A_UPPER_LIMIT = 200; }
      }
      if (cA != null && !wt.fc.PersistenceHelper.checkStoredLength(cA.toString(), C_A_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cA"), java.lang.String.valueOf(java.lang.Math.min(C_A_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cA", this.cA, cA));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C_B = "cB";
   static int C_B_UPPER_LIMIT = -1;
   java.lang.String cB;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getCB() {
      return cB;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setCB(java.lang.String cB) throws wt.util.WTPropertyVetoException {
      cBValidate(cB);
      this.cB = cB;
   }
   void cBValidate(java.lang.String cB) throws wt.util.WTPropertyVetoException {
      if (C_B_UPPER_LIMIT < 1) {
         try { C_B_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cB").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C_B_UPPER_LIMIT = 200; }
      }
      if (cB != null && !wt.fc.PersistenceHelper.checkStoredLength(cB.toString(), C_B_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cB"), java.lang.String.valueOf(java.lang.Math.min(C_B_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cB", this.cB, cB));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C_C = "cC";
   static int C_C_UPPER_LIMIT = -1;
   java.lang.String cC;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getCC() {
      return cC;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setCC(java.lang.String cC) throws wt.util.WTPropertyVetoException {
      cCValidate(cC);
      this.cC = cC;
   }
   void cCValidate(java.lang.String cC) throws wt.util.WTPropertyVetoException {
      if (C_C_UPPER_LIMIT < 1) {
         try { C_C_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C_C_UPPER_LIMIT = 200; }
      }
      if (cC != null && !wt.fc.PersistenceHelper.checkStoredLength(cC.toString(), C_C_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cC"), java.lang.String.valueOf(java.lang.Math.min(C_C_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cC", this.cC, cC));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C_D = "cD";
   static int C_D_UPPER_LIMIT = -1;
   java.lang.String cD;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getCD() {
      return cD;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setCD(java.lang.String cD) throws wt.util.WTPropertyVetoException {
      cDValidate(cD);
      this.cD = cD;
   }
   void cDValidate(java.lang.String cD) throws wt.util.WTPropertyVetoException {
      if (C_D_UPPER_LIMIT < 1) {
         try { C_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cD").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C_D_UPPER_LIMIT = 200; }
      }
      if (cD != null && !wt.fc.PersistenceHelper.checkStoredLength(cD.toString(), C_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cD"), java.lang.String.valueOf(java.lang.Math.min(C_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cD", this.cD, cD));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C2_S = "c2S";
   static int C2_S_UPPER_LIMIT = -1;
   java.lang.String c2S;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getC2S() {
      return c2S;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setC2S(java.lang.String c2S) throws wt.util.WTPropertyVetoException {
      c2SValidate(c2S);
      this.c2S = c2S;
   }
   void c2SValidate(java.lang.String c2S) throws wt.util.WTPropertyVetoException {
      if (C2_S_UPPER_LIMIT < 1) {
         try { C2_S_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("c2S").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C2_S_UPPER_LIMIT = 200; }
      }
      if (c2S != null && !wt.fc.PersistenceHelper.checkStoredLength(c2S.toString(), C2_S_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "c2S"), java.lang.String.valueOf(java.lang.Math.min(C2_S_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "c2S", this.c2S, c2S));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C2_A = "c2A";
   static int C2_A_UPPER_LIMIT = -1;
   java.lang.String c2A;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getC2A() {
      return c2A;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setC2A(java.lang.String c2A) throws wt.util.WTPropertyVetoException {
      c2AValidate(c2A);
      this.c2A = c2A;
   }
   void c2AValidate(java.lang.String c2A) throws wt.util.WTPropertyVetoException {
      if (C2_A_UPPER_LIMIT < 1) {
         try { C2_A_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("c2A").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C2_A_UPPER_LIMIT = 200; }
      }
      if (c2A != null && !wt.fc.PersistenceHelper.checkStoredLength(c2A.toString(), C2_A_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "c2A"), java.lang.String.valueOf(java.lang.Math.min(C2_A_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "c2A", this.c2A, c2A));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C2_B = "c2B";
   static int C2_B_UPPER_LIMIT = -1;
   java.lang.String c2B;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getC2B() {
      return c2B;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setC2B(java.lang.String c2B) throws wt.util.WTPropertyVetoException {
      c2BValidate(c2B);
      this.c2B = c2B;
   }
   void c2BValidate(java.lang.String c2B) throws wt.util.WTPropertyVetoException {
      if (C2_B_UPPER_LIMIT < 1) {
         try { C2_B_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("c2B").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C2_B_UPPER_LIMIT = 200; }
      }
      if (c2B != null && !wt.fc.PersistenceHelper.checkStoredLength(c2B.toString(), C2_B_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "c2B"), java.lang.String.valueOf(java.lang.Math.min(C2_B_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "c2B", this.c2B, c2B));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C2_C = "c2C";
   static int C2_C_UPPER_LIMIT = -1;
   java.lang.String c2C;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getC2C() {
      return c2C;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setC2C(java.lang.String c2C) throws wt.util.WTPropertyVetoException {
      c2CValidate(c2C);
      this.c2C = c2C;
   }
   void c2CValidate(java.lang.String c2C) throws wt.util.WTPropertyVetoException {
      if (C2_C_UPPER_LIMIT < 1) {
         try { C2_C_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("c2C").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C2_C_UPPER_LIMIT = 200; }
      }
      if (c2C != null && !wt.fc.PersistenceHelper.checkStoredLength(c2C.toString(), C2_C_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "c2C"), java.lang.String.valueOf(java.lang.Math.min(C2_C_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "c2C", this.c2C, c2C));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String C2_D = "c2D";
   static int C2_D_UPPER_LIMIT = -1;
   java.lang.String c2D;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getC2D() {
      return c2D;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setC2D(java.lang.String c2D) throws wt.util.WTPropertyVetoException {
      c2DValidate(c2D);
      this.c2D = c2D;
   }
   void c2DValidate(java.lang.String c2D) throws wt.util.WTPropertyVetoException {
      if (C2_D_UPPER_LIMIT < 1) {
         try { C2_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("c2D").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { C2_D_UPPER_LIMIT = 200; }
      }
      if (c2D != null && !wt.fc.PersistenceHelper.checkStoredLength(c2D.toString(), C2_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "c2D"), java.lang.String.valueOf(java.lang.Math.min(C2_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "c2D", this.c2D, c2D));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_ONE_S = "dOneS";
   static int D_ONE_S_UPPER_LIMIT = -1;
   java.lang.String dOneS;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDOneS() {
      return dOneS;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDOneS(java.lang.String dOneS) throws wt.util.WTPropertyVetoException {
      dOneSValidate(dOneS);
      this.dOneS = dOneS;
   }
   void dOneSValidate(java.lang.String dOneS) throws wt.util.WTPropertyVetoException {
      if (D_ONE_S_UPPER_LIMIT < 1) {
         try { D_ONE_S_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dOneS").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_ONE_S_UPPER_LIMIT = 200; }
      }
      if (dOneS != null && !wt.fc.PersistenceHelper.checkStoredLength(dOneS.toString(), D_ONE_S_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dOneS"), java.lang.String.valueOf(java.lang.Math.min(D_ONE_S_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dOneS", this.dOneS, dOneS));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_ONE_A = "dOneA";
   static int D_ONE_A_UPPER_LIMIT = -1;
   java.lang.String dOneA;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDOneA() {
      return dOneA;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDOneA(java.lang.String dOneA) throws wt.util.WTPropertyVetoException {
      dOneAValidate(dOneA);
      this.dOneA = dOneA;
   }
   void dOneAValidate(java.lang.String dOneA) throws wt.util.WTPropertyVetoException {
      if (D_ONE_A_UPPER_LIMIT < 1) {
         try { D_ONE_A_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dOneA").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_ONE_A_UPPER_LIMIT = 200; }
      }
      if (dOneA != null && !wt.fc.PersistenceHelper.checkStoredLength(dOneA.toString(), D_ONE_A_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dOneA"), java.lang.String.valueOf(java.lang.Math.min(D_ONE_A_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dOneA", this.dOneA, dOneA));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_ONE_B = "dOneB";
   static int D_ONE_B_UPPER_LIMIT = -1;
   java.lang.String dOneB;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDOneB() {
      return dOneB;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDOneB(java.lang.String dOneB) throws wt.util.WTPropertyVetoException {
      dOneBValidate(dOneB);
      this.dOneB = dOneB;
   }
   void dOneBValidate(java.lang.String dOneB) throws wt.util.WTPropertyVetoException {
      if (D_ONE_B_UPPER_LIMIT < 1) {
         try { D_ONE_B_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dOneB").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_ONE_B_UPPER_LIMIT = 200; }
      }
      if (dOneB != null && !wt.fc.PersistenceHelper.checkStoredLength(dOneB.toString(), D_ONE_B_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dOneB"), java.lang.String.valueOf(java.lang.Math.min(D_ONE_B_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dOneB", this.dOneB, dOneB));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_ONE_C = "dOneC";
   static int D_ONE_C_UPPER_LIMIT = -1;
   java.lang.String dOneC;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDOneC() {
      return dOneC;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDOneC(java.lang.String dOneC) throws wt.util.WTPropertyVetoException {
      dOneCValidate(dOneC);
      this.dOneC = dOneC;
   }
   void dOneCValidate(java.lang.String dOneC) throws wt.util.WTPropertyVetoException {
      if (D_ONE_C_UPPER_LIMIT < 1) {
         try { D_ONE_C_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dOneC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_ONE_C_UPPER_LIMIT = 200; }
      }
      if (dOneC != null && !wt.fc.PersistenceHelper.checkStoredLength(dOneC.toString(), D_ONE_C_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dOneC"), java.lang.String.valueOf(java.lang.Math.min(D_ONE_C_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dOneC", this.dOneC, dOneC));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_ONE_D = "dOneD";
   static int D_ONE_D_UPPER_LIMIT = -1;
   java.lang.String dOneD;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDOneD() {
      return dOneD;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDOneD(java.lang.String dOneD) throws wt.util.WTPropertyVetoException {
      dOneDValidate(dOneD);
      this.dOneD = dOneD;
   }
   void dOneDValidate(java.lang.String dOneD) throws wt.util.WTPropertyVetoException {
      if (D_ONE_D_UPPER_LIMIT < 1) {
         try { D_ONE_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dOneD").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_ONE_D_UPPER_LIMIT = 200; }
      }
      if (dOneD != null && !wt.fc.PersistenceHelper.checkStoredLength(dOneD.toString(), D_ONE_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dOneD"), java.lang.String.valueOf(java.lang.Math.min(D_ONE_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dOneD", this.dOneD, dOneD));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_TWO_S = "dTwoS";
   static int D_TWO_S_UPPER_LIMIT = -1;
   java.lang.String dTwoS;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDTwoS() {
      return dTwoS;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDTwoS(java.lang.String dTwoS) throws wt.util.WTPropertyVetoException {
      dTwoSValidate(dTwoS);
      this.dTwoS = dTwoS;
   }
   void dTwoSValidate(java.lang.String dTwoS) throws wt.util.WTPropertyVetoException {
      if (D_TWO_S_UPPER_LIMIT < 1) {
         try { D_TWO_S_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dTwoS").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_TWO_S_UPPER_LIMIT = 200; }
      }
      if (dTwoS != null && !wt.fc.PersistenceHelper.checkStoredLength(dTwoS.toString(), D_TWO_S_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dTwoS"), java.lang.String.valueOf(java.lang.Math.min(D_TWO_S_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dTwoS", this.dTwoS, dTwoS));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_TWO_A = "dTwoA";
   static int D_TWO_A_UPPER_LIMIT = -1;
   java.lang.String dTwoA;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDTwoA() {
      return dTwoA;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDTwoA(java.lang.String dTwoA) throws wt.util.WTPropertyVetoException {
      dTwoAValidate(dTwoA);
      this.dTwoA = dTwoA;
   }
   void dTwoAValidate(java.lang.String dTwoA) throws wt.util.WTPropertyVetoException {
      if (D_TWO_A_UPPER_LIMIT < 1) {
         try { D_TWO_A_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dTwoA").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_TWO_A_UPPER_LIMIT = 200; }
      }
      if (dTwoA != null && !wt.fc.PersistenceHelper.checkStoredLength(dTwoA.toString(), D_TWO_A_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dTwoA"), java.lang.String.valueOf(java.lang.Math.min(D_TWO_A_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dTwoA", this.dTwoA, dTwoA));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_TWO_B = "dTwoB";
   static int D_TWO_B_UPPER_LIMIT = -1;
   java.lang.String dTwoB;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDTwoB() {
      return dTwoB;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDTwoB(java.lang.String dTwoB) throws wt.util.WTPropertyVetoException {
      dTwoBValidate(dTwoB);
      this.dTwoB = dTwoB;
   }
   void dTwoBValidate(java.lang.String dTwoB) throws wt.util.WTPropertyVetoException {
      if (D_TWO_B_UPPER_LIMIT < 1) {
         try { D_TWO_B_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dTwoB").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_TWO_B_UPPER_LIMIT = 200; }
      }
      if (dTwoB != null && !wt.fc.PersistenceHelper.checkStoredLength(dTwoB.toString(), D_TWO_B_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dTwoB"), java.lang.String.valueOf(java.lang.Math.min(D_TWO_B_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dTwoB", this.dTwoB, dTwoB));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_TWO_C = "dTwoC";
   static int D_TWO_C_UPPER_LIMIT = -1;
   java.lang.String dTwoC;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDTwoC() {
      return dTwoC;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDTwoC(java.lang.String dTwoC) throws wt.util.WTPropertyVetoException {
      dTwoCValidate(dTwoC);
      this.dTwoC = dTwoC;
   }
   void dTwoCValidate(java.lang.String dTwoC) throws wt.util.WTPropertyVetoException {
      if (D_TWO_C_UPPER_LIMIT < 1) {
         try { D_TWO_C_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dTwoC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_TWO_C_UPPER_LIMIT = 200; }
      }
      if (dTwoC != null && !wt.fc.PersistenceHelper.checkStoredLength(dTwoC.toString(), D_TWO_C_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dTwoC"), java.lang.String.valueOf(java.lang.Math.min(D_TWO_C_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dTwoC", this.dTwoC, dTwoC));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_TWO_D = "dTwoD";
   static int D_TWO_D_UPPER_LIMIT = -1;
   java.lang.String dTwoD;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDTwoD() {
      return dTwoD;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDTwoD(java.lang.String dTwoD) throws wt.util.WTPropertyVetoException {
      dTwoDValidate(dTwoD);
      this.dTwoD = dTwoD;
   }
   void dTwoDValidate(java.lang.String dTwoD) throws wt.util.WTPropertyVetoException {
      if (D_TWO_D_UPPER_LIMIT < 1) {
         try { D_TWO_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dTwoD").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_TWO_D_UPPER_LIMIT = 200; }
      }
      if (dTwoD != null && !wt.fc.PersistenceHelper.checkStoredLength(dTwoD.toString(), D_TWO_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dTwoD"), java.lang.String.valueOf(java.lang.Math.min(D_TWO_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dTwoD", this.dTwoD, dTwoD));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_THREE_S = "dThreeS";
   static int D_THREE_S_UPPER_LIMIT = -1;
   java.lang.String dThreeS;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDThreeS() {
      return dThreeS;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDThreeS(java.lang.String dThreeS) throws wt.util.WTPropertyVetoException {
      dThreeSValidate(dThreeS);
      this.dThreeS = dThreeS;
   }
   void dThreeSValidate(java.lang.String dThreeS) throws wt.util.WTPropertyVetoException {
      if (D_THREE_S_UPPER_LIMIT < 1) {
         try { D_THREE_S_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dThreeS").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_THREE_S_UPPER_LIMIT = 200; }
      }
      if (dThreeS != null && !wt.fc.PersistenceHelper.checkStoredLength(dThreeS.toString(), D_THREE_S_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dThreeS"), java.lang.String.valueOf(java.lang.Math.min(D_THREE_S_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dThreeS", this.dThreeS, dThreeS));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_THREE_A = "dThreeA";
   static int D_THREE_A_UPPER_LIMIT = -1;
   java.lang.String dThreeA;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDThreeA() {
      return dThreeA;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDThreeA(java.lang.String dThreeA) throws wt.util.WTPropertyVetoException {
      dThreeAValidate(dThreeA);
      this.dThreeA = dThreeA;
   }
   void dThreeAValidate(java.lang.String dThreeA) throws wt.util.WTPropertyVetoException {
      if (D_THREE_A_UPPER_LIMIT < 1) {
         try { D_THREE_A_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dThreeA").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_THREE_A_UPPER_LIMIT = 200; }
      }
      if (dThreeA != null && !wt.fc.PersistenceHelper.checkStoredLength(dThreeA.toString(), D_THREE_A_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dThreeA"), java.lang.String.valueOf(java.lang.Math.min(D_THREE_A_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dThreeA", this.dThreeA, dThreeA));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_THREE_B = "dThreeB";
   static int D_THREE_B_UPPER_LIMIT = -1;
   java.lang.String dThreeB;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDThreeB() {
      return dThreeB;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDThreeB(java.lang.String dThreeB) throws wt.util.WTPropertyVetoException {
      dThreeBValidate(dThreeB);
      this.dThreeB = dThreeB;
   }
   void dThreeBValidate(java.lang.String dThreeB) throws wt.util.WTPropertyVetoException {
      if (D_THREE_B_UPPER_LIMIT < 1) {
         try { D_THREE_B_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dThreeB").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_THREE_B_UPPER_LIMIT = 200; }
      }
      if (dThreeB != null && !wt.fc.PersistenceHelper.checkStoredLength(dThreeB.toString(), D_THREE_B_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dThreeB"), java.lang.String.valueOf(java.lang.Math.min(D_THREE_B_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dThreeB", this.dThreeB, dThreeB));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_THREE_C = "dThreeC";
   static int D_THREE_C_UPPER_LIMIT = -1;
   java.lang.String dThreeC;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDThreeC() {
      return dThreeC;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDThreeC(java.lang.String dThreeC) throws wt.util.WTPropertyVetoException {
      dThreeCValidate(dThreeC);
      this.dThreeC = dThreeC;
   }
   void dThreeCValidate(java.lang.String dThreeC) throws wt.util.WTPropertyVetoException {
      if (D_THREE_C_UPPER_LIMIT < 1) {
         try { D_THREE_C_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dThreeC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_THREE_C_UPPER_LIMIT = 200; }
      }
      if (dThreeC != null && !wt.fc.PersistenceHelper.checkStoredLength(dThreeC.toString(), D_THREE_C_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dThreeC"), java.lang.String.valueOf(java.lang.Math.min(D_THREE_C_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dThreeC", this.dThreeC, dThreeC));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String D_THREE_D = "dThreeD";
   static int D_THREE_D_UPPER_LIMIT = -1;
   java.lang.String dThreeD;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getDThreeD() {
      return dThreeD;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setDThreeD(java.lang.String dThreeD) throws wt.util.WTPropertyVetoException {
      dThreeDValidate(dThreeD);
      this.dThreeD = dThreeD;
   }
   void dThreeDValidate(java.lang.String dThreeD) throws wt.util.WTPropertyVetoException {
      if (D_THREE_D_UPPER_LIMIT < 1) {
         try { D_THREE_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dThreeD").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { D_THREE_D_UPPER_LIMIT = 200; }
      }
      if (dThreeD != null && !wt.fc.PersistenceHelper.checkStoredLength(dThreeD.toString(), D_THREE_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dThreeD"), java.lang.String.valueOf(java.lang.Math.min(D_THREE_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dThreeD", this.dThreeD, dThreeD));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String T_S = "tS";
   static int T_S_UPPER_LIMIT = -1;
   java.lang.String tS;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getTS() {
      return tS;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTS(java.lang.String tS) throws wt.util.WTPropertyVetoException {
      tSValidate(tS);
      this.tS = tS;
   }
   void tSValidate(java.lang.String tS) throws wt.util.WTPropertyVetoException {
      if (T_S_UPPER_LIMIT < 1) {
         try { T_S_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tS").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_S_UPPER_LIMIT = 200; }
      }
      if (tS != null && !wt.fc.PersistenceHelper.checkStoredLength(tS.toString(), T_S_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tS"), java.lang.String.valueOf(java.lang.Math.min(T_S_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tS", this.tS, tS));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String T_A = "tA";
   static int T_A_UPPER_LIMIT = -1;
   java.lang.String tA;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getTA() {
      return tA;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTA(java.lang.String tA) throws wt.util.WTPropertyVetoException {
      tAValidate(tA);
      this.tA = tA;
   }
   void tAValidate(java.lang.String tA) throws wt.util.WTPropertyVetoException {
      if (T_A_UPPER_LIMIT < 1) {
         try { T_A_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tA").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_A_UPPER_LIMIT = 200; }
      }
      if (tA != null && !wt.fc.PersistenceHelper.checkStoredLength(tA.toString(), T_A_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tA"), java.lang.String.valueOf(java.lang.Math.min(T_A_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tA", this.tA, tA));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String T_B = "tB";
   static int T_B_UPPER_LIMIT = -1;
   java.lang.String tB;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getTB() {
      return tB;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTB(java.lang.String tB) throws wt.util.WTPropertyVetoException {
      tBValidate(tB);
      this.tB = tB;
   }
   void tBValidate(java.lang.String tB) throws wt.util.WTPropertyVetoException {
      if (T_B_UPPER_LIMIT < 1) {
         try { T_B_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tB").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_B_UPPER_LIMIT = 200; }
      }
      if (tB != null && !wt.fc.PersistenceHelper.checkStoredLength(tB.toString(), T_B_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tB"), java.lang.String.valueOf(java.lang.Math.min(T_B_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tB", this.tB, tB));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String T_C = "tC";
   static int T_C_UPPER_LIMIT = -1;
   java.lang.String tC;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getTC() {
      return tC;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTC(java.lang.String tC) throws wt.util.WTPropertyVetoException {
      tCValidate(tC);
      this.tC = tC;
   }
   void tCValidate(java.lang.String tC) throws wt.util.WTPropertyVetoException {
      if (T_C_UPPER_LIMIT < 1) {
         try { T_C_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tC").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_C_UPPER_LIMIT = 200; }
      }
      if (tC != null && !wt.fc.PersistenceHelper.checkStoredLength(tC.toString(), T_C_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tC"), java.lang.String.valueOf(java.lang.Math.min(T_C_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tC", this.tC, tC));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String T_D = "tD";
   static int T_D_UPPER_LIMIT = -1;
   java.lang.String tD;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getTD() {
      return tD;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setTD(java.lang.String tD) throws wt.util.WTPropertyVetoException {
      tDValidate(tD);
      this.tD = tD;
   }
   void tDValidate(java.lang.String tD) throws wt.util.WTPropertyVetoException {
      if (T_D_UPPER_LIMIT < 1) {
         try { T_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("tD").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_D_UPPER_LIMIT = 200; }
      }
      if (tD != null && !wt.fc.PersistenceHelper.checkStoredLength(tD.toString(), T_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "tD"), java.lang.String.valueOf(java.lang.Math.min(T_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "tD", this.tD, tD));
   }

   /**
    * @see e3ps.project.Weights
    */
   public static final java.lang.String KEY_NO = "keyNo";
   static int KEY_NO_UPPER_LIMIT = -1;
   java.lang.String keyNo;
   /**
    * @see e3ps.project.Weights
    */
   public java.lang.String getKeyNo() {
      return keyNo;
   }
   /**
    * @see e3ps.project.Weights
    */
   public void setKeyNo(java.lang.String keyNo) throws wt.util.WTPropertyVetoException {
      keyNoValidate(keyNo);
      this.keyNo = keyNo;
   }
   void keyNoValidate(java.lang.String keyNo) throws wt.util.WTPropertyVetoException {
      if (KEY_NO_UPPER_LIMIT < 1) {
         try { KEY_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("keyNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KEY_NO_UPPER_LIMIT = 200; }
      }
      if (keyNo != null && !wt.fc.PersistenceHelper.checkStoredLength(keyNo.toString(), KEY_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "keyNo"), java.lang.String.valueOf(java.lang.Math.min(KEY_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "keyNo", this.keyNo, keyNo));
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

   public static final long EXTERNALIZATION_VERSION_UID = -3954398909225086467L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( c2A );
      output.writeObject( c2B );
      output.writeObject( c2C );
      output.writeObject( c2D );
      output.writeObject( c2S );
      output.writeObject( cA );
      output.writeObject( cB );
      output.writeObject( cC );
      output.writeObject( cD );
      output.writeObject( cS );
      output.writeInt( cost );
      output.writeObject( dOneA );
      output.writeObject( dOneB );
      output.writeObject( dOneC );
      output.writeObject( dOneD );
      output.writeObject( dOneS );
      output.writeObject( dThreeA );
      output.writeObject( dThreeB );
      output.writeObject( dThreeC );
      output.writeObject( dThreeD );
      output.writeObject( dThreeS );
      output.writeObject( dTwoA );
      output.writeObject( dTwoB );
      output.writeObject( dTwoC );
      output.writeObject( dTwoD );
      output.writeObject( dTwoS );
      output.writeInt( deliveryOne );
      output.writeInt( deliveryThree );
      output.writeInt( deliveryTwo );
      output.writeBoolean( isProject );
      output.writeObject( keyNo );
      output.writeObject( owner );
      output.writeObject( qA );
      output.writeObject( qB );
      output.writeObject( qC );
      output.writeObject( qD );
      output.writeObject( qS );
      output.writeInt( quality );
      output.writeObject( tA );
      output.writeObject( tB );
      output.writeObject( tC );
      output.writeObject( tD );
      output.writeObject( tS );
      output.writeInt( technical );
      output.writeObject( thePersistInfo );
      output.writeInt( totalA );
      output.writeInt( totalB );
      output.writeInt( totalC );
      output.writeInt( totalD );
      output.writeInt( totalS );
      output.writeInt( wType );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.Weights) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "c2A", c2A );
      output.setString( "c2B", c2B );
      output.setString( "c2C", c2C );
      output.setString( "c2D", c2D );
      output.setString( "c2S", c2S );
      output.setString( "cA", cA );
      output.setString( "cB", cB );
      output.setString( "cC", cC );
      output.setString( "cD", cD );
      output.setString( "cS", cS );
      output.setInt( "cost", cost );
      output.setString( "dOneA", dOneA );
      output.setString( "dOneB", dOneB );
      output.setString( "dOneC", dOneC );
      output.setString( "dOneD", dOneD );
      output.setString( "dOneS", dOneS );
      output.setString( "dThreeA", dThreeA );
      output.setString( "dThreeB", dThreeB );
      output.setString( "dThreeC", dThreeC );
      output.setString( "dThreeD", dThreeD );
      output.setString( "dThreeS", dThreeS );
      output.setString( "dTwoA", dTwoA );
      output.setString( "dTwoB", dTwoB );
      output.setString( "dTwoC", dTwoC );
      output.setString( "dTwoD", dTwoD );
      output.setString( "dTwoS", dTwoS );
      output.setInt( "deliveryOne", deliveryOne );
      output.setInt( "deliveryThree", deliveryThree );
      output.setInt( "deliveryTwo", deliveryTwo );
      output.setBoolean( "isProject", isProject );
      output.setString( "keyNo", keyNo );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "qA", qA );
      output.setString( "qB", qB );
      output.setString( "qC", qC );
      output.setString( "qD", qD );
      output.setString( "qS", qS );
      output.setInt( "quality", quality );
      output.setString( "tA", tA );
      output.setString( "tB", tB );
      output.setString( "tC", tC );
      output.setString( "tD", tD );
      output.setString( "tS", tS );
      output.setInt( "technical", technical );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setInt( "totalA", totalA );
      output.setInt( "totalB", totalB );
      output.setInt( "totalC", totalC );
      output.setInt( "totalD", totalD );
      output.setInt( "totalS", totalS );
      output.setInt( "wType", wType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      c2A = input.getString( "c2A" );
      c2B = input.getString( "c2B" );
      c2C = input.getString( "c2C" );
      c2D = input.getString( "c2D" );
      c2S = input.getString( "c2S" );
      cA = input.getString( "cA" );
      cB = input.getString( "cB" );
      cC = input.getString( "cC" );
      cD = input.getString( "cD" );
      cS = input.getString( "cS" );
      cost = input.getInt( "cost" );
      dOneA = input.getString( "dOneA" );
      dOneB = input.getString( "dOneB" );
      dOneC = input.getString( "dOneC" );
      dOneD = input.getString( "dOneD" );
      dOneS = input.getString( "dOneS" );
      dThreeA = input.getString( "dThreeA" );
      dThreeB = input.getString( "dThreeB" );
      dThreeC = input.getString( "dThreeC" );
      dThreeD = input.getString( "dThreeD" );
      dThreeS = input.getString( "dThreeS" );
      dTwoA = input.getString( "dTwoA" );
      dTwoB = input.getString( "dTwoB" );
      dTwoC = input.getString( "dTwoC" );
      dTwoD = input.getString( "dTwoD" );
      dTwoS = input.getString( "dTwoS" );
      deliveryOne = input.getInt( "deliveryOne" );
      deliveryThree = input.getInt( "deliveryThree" );
      deliveryTwo = input.getInt( "deliveryTwo" );
      isProject = input.getBoolean( "isProject" );
      keyNo = input.getString( "keyNo" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      qA = input.getString( "qA" );
      qB = input.getString( "qB" );
      qC = input.getString( "qC" );
      qD = input.getString( "qD" );
      qS = input.getString( "qS" );
      quality = input.getInt( "quality" );
      tA = input.getString( "tA" );
      tB = input.getString( "tB" );
      tC = input.getString( "tC" );
      tD = input.getString( "tD" );
      tS = input.getString( "tS" );
      technical = input.getInt( "technical" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      totalA = input.getInt( "totalA" );
      totalB = input.getInt( "totalB" );
      totalC = input.getInt( "totalC" );
      totalD = input.getInt( "totalD" );
      totalS = input.getInt( "totalS" );
      wType = input.getInt( "wType" );
   }

   boolean readVersion_3954398909225086467L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      c2A = (java.lang.String) input.readObject();
      c2B = (java.lang.String) input.readObject();
      c2C = (java.lang.String) input.readObject();
      c2D = (java.lang.String) input.readObject();
      c2S = (java.lang.String) input.readObject();
      cA = (java.lang.String) input.readObject();
      cB = (java.lang.String) input.readObject();
      cC = (java.lang.String) input.readObject();
      cD = (java.lang.String) input.readObject();
      cS = (java.lang.String) input.readObject();
      cost = input.readInt();
      dOneA = (java.lang.String) input.readObject();
      dOneB = (java.lang.String) input.readObject();
      dOneC = (java.lang.String) input.readObject();
      dOneD = (java.lang.String) input.readObject();
      dOneS = (java.lang.String) input.readObject();
      dThreeA = (java.lang.String) input.readObject();
      dThreeB = (java.lang.String) input.readObject();
      dThreeC = (java.lang.String) input.readObject();
      dThreeD = (java.lang.String) input.readObject();
      dThreeS = (java.lang.String) input.readObject();
      dTwoA = (java.lang.String) input.readObject();
      dTwoB = (java.lang.String) input.readObject();
      dTwoC = (java.lang.String) input.readObject();
      dTwoD = (java.lang.String) input.readObject();
      dTwoS = (java.lang.String) input.readObject();
      deliveryOne = input.readInt();
      deliveryThree = input.readInt();
      deliveryTwo = input.readInt();
      isProject = input.readBoolean();
      keyNo = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      qA = (java.lang.String) input.readObject();
      qB = (java.lang.String) input.readObject();
      qC = (java.lang.String) input.readObject();
      qD = (java.lang.String) input.readObject();
      qS = (java.lang.String) input.readObject();
      quality = input.readInt();
      tA = (java.lang.String) input.readObject();
      tB = (java.lang.String) input.readObject();
      tC = (java.lang.String) input.readObject();
      tD = (java.lang.String) input.readObject();
      tS = (java.lang.String) input.readObject();
      technical = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      totalA = input.readInt();
      totalB = input.readInt();
      totalC = input.readInt();
      totalD = input.readInt();
      totalS = input.readInt();
      wType = input.readInt();
      return true;
   }

   protected boolean readVersion( Weights thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_3954398909225086467L( input, readSerialVersionUID, superDone );
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
