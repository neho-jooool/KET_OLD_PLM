package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _RivalPartInfo implements wt.content.ContentHolder, e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = RivalPartInfo.class.getName();

   /**
    * 경쟁사
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String COMPANY_NAME = "companyName";
   static int COMPANY_NAME_UPPER_LIMIT = -1;
   java.lang.String companyName;
   /**
    * 경쟁사
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getCompanyName() {
      return companyName;
   }
   /**
    * 경쟁사
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setCompanyName(java.lang.String companyName) throws wt.util.WTPropertyVetoException {
      companyNameValidate(companyName);
      this.companyName = companyName;
   }
   void companyNameValidate(java.lang.String companyName) throws wt.util.WTPropertyVetoException {
      if (COMPANY_NAME_UPPER_LIMIT < 1) {
         try { COMPANY_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("companyName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMPANY_NAME_UPPER_LIMIT = 200; }
      }
      if (companyName != null && !wt.fc.PersistenceHelper.checkStoredLength(companyName.toString(), COMPANY_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "companyName"), java.lang.String.valueOf(java.lang.Math.min(COMPANY_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "companyName", this.companyName, companyName));
   }

   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String COMPANY_CODE = "companyCode";
   static int COMPANY_CODE_UPPER_LIMIT = -1;
   java.lang.String companyCode;
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getCompanyCode() {
      return companyCode;
   }
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setCompanyCode(java.lang.String companyCode) throws wt.util.WTPropertyVetoException {
      companyCodeValidate(companyCode);
      this.companyCode = companyCode;
   }
   void companyCodeValidate(java.lang.String companyCode) throws wt.util.WTPropertyVetoException {
      if (COMPANY_CODE_UPPER_LIMIT < 1) {
         try { COMPANY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("companyCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { COMPANY_CODE_UPPER_LIMIT = 200; }
      }
      if (companyCode != null && !wt.fc.PersistenceHelper.checkStoredLength(companyCode.toString(), COMPANY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "companyCode"), java.lang.String.valueOf(java.lang.Math.min(COMPANY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "companyCode", this.companyCode, companyCode));
   }

   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setPartNo(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      partNoValidate(partNo);
      this.partNo = partNo;
   }
   void partNoValidate(java.lang.String partNo) throws wt.util.WTPropertyVetoException {
      if (PART_NO_UPPER_LIMIT < 1) {
         try { PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NO_UPPER_LIMIT = 200; }
      }
      if (partNo != null && !wt.fc.PersistenceHelper.checkStoredLength(partNo.toString(), PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partNo"), java.lang.String.valueOf(java.lang.Math.min(PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partNo", this.partNo, partNo));
   }

   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String PART_NAME = "partName";
   static int PART_NAME_UPPER_LIMIT = -1;
   java.lang.String partName;
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getPartName() {
      return partName;
   }
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setPartName(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      partNameValidate(partName);
      this.partName = partName;
   }
   void partNameValidate(java.lang.String partName) throws wt.util.WTPropertyVetoException {
      if (PART_NAME_UPPER_LIMIT < 1) {
         try { PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("partName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NAME_UPPER_LIMIT = 200; }
      }
      if (partName != null && !wt.fc.PersistenceHelper.checkStoredLength(partName.toString(), PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "partName"), java.lang.String.valueOf(java.lang.Math.min(PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "partName", this.partName, partName));
   }

   /**
    * 방수,비방수
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String WATER_PROOF = "waterProof";
   static int WATER_PROOF_UPPER_LIMIT = -1;
   java.lang.String waterProof;
   /**
    * 방수,비방수
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getWaterProof() {
      return waterProof;
   }
   /**
    * 방수,비방수
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setWaterProof(java.lang.String waterProof) throws wt.util.WTPropertyVetoException {
      waterProofValidate(waterProof);
      this.waterProof = waterProof;
   }
   void waterProofValidate(java.lang.String waterProof) throws wt.util.WTPropertyVetoException {
      if (WATER_PROOF_UPPER_LIMIT < 1) {
         try { WATER_PROOF_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("waterProof").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WATER_PROOF_UPPER_LIMIT = 200; }
      }
      if (waterProof != null && !wt.fc.PersistenceHelper.checkStoredLength(waterProof.toString(), WATER_PROOF_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "waterProof"), java.lang.String.valueOf(java.lang.Math.min(WATER_PROOF_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "waterProof", this.waterProof, waterProof));
   }

   /**
    * Male,Female
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String MF_TYPE = "mfType";
   static int MF_TYPE_UPPER_LIMIT = -1;
   java.lang.String mfType;
   /**
    * Male,Female
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getMfType() {
      return mfType;
   }
   /**
    * Male,Female
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setMfType(java.lang.String mfType) throws wt.util.WTPropertyVetoException {
      mfTypeValidate(mfType);
      this.mfType = mfType;
   }
   void mfTypeValidate(java.lang.String mfType) throws wt.util.WTPropertyVetoException {
      if (MF_TYPE_UPPER_LIMIT < 1) {
         try { MF_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("mfType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MF_TYPE_UPPER_LIMIT = 200; }
      }
      if (mfType != null && !wt.fc.PersistenceHelper.checkStoredLength(mfType.toString(), MF_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "mfType"), java.lang.String.valueOf(java.lang.Math.min(MF_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "mfType", this.mfType, mfType));
   }

   /**
    * 극수
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String POLE = "pole";
   static int POLE_UPPER_LIMIT = -1;
   java.lang.String pole;
   /**
    * 극수
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getPole() {
      return pole;
   }
   /**
    * 극수
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setPole(java.lang.String pole) throws wt.util.WTPropertyVetoException {
      poleValidate(pole);
      this.pole = pole;
   }
   void poleValidate(java.lang.String pole) throws wt.util.WTPropertyVetoException {
      if (POLE_UPPER_LIMIT < 1) {
         try { POLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { POLE_UPPER_LIMIT = 200; }
      }
      if (pole != null && !wt.fc.PersistenceHelper.checkStoredLength(pole.toString(), POLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pole"), java.lang.String.valueOf(java.lang.Math.min(POLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pole", this.pole, pole));
   }

   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String SERIES_CODE = "seriesCode";
   static int SERIES_CODE_UPPER_LIMIT = -1;
   java.lang.String seriesCode;
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getSeriesCode() {
      return seriesCode;
   }
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setSeriesCode(java.lang.String seriesCode) throws wt.util.WTPropertyVetoException {
      seriesCodeValidate(seriesCode);
      this.seriesCode = seriesCode;
   }
   void seriesCodeValidate(java.lang.String seriesCode) throws wt.util.WTPropertyVetoException {
      if (SERIES_CODE_UPPER_LIMIT < 1) {
         try { SERIES_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("seriesCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SERIES_CODE_UPPER_LIMIT = 200; }
      }
      if (seriesCode != null && !wt.fc.PersistenceHelper.checkStoredLength(seriesCode.toString(), SERIES_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "seriesCode"), java.lang.String.valueOf(java.lang.Math.min(SERIES_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "seriesCode", this.seriesCode, seriesCode));
   }

   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String SERIES_NAME = "seriesName";
   static int SERIES_NAME_UPPER_LIMIT = -1;
   java.lang.String seriesName;
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getSeriesName() {
      return seriesName;
   }
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setSeriesName(java.lang.String seriesName) throws wt.util.WTPropertyVetoException {
      seriesNameValidate(seriesName);
      this.seriesName = seriesName;
   }
   void seriesNameValidate(java.lang.String seriesName) throws wt.util.WTPropertyVetoException {
      if (SERIES_NAME_UPPER_LIMIT < 1) {
         try { SERIES_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("seriesName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SERIES_NAME_UPPER_LIMIT = 200; }
      }
      if (seriesName != null && !wt.fc.PersistenceHelper.checkStoredLength(seriesName.toString(), SERIES_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "seriesName"), java.lang.String.valueOf(java.lang.Math.min(SERIES_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "seriesName", this.seriesName, seriesName));
   }

   /**
    * 매칭터미널
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String MATCH_TERMINAL = "matchTerminal";
   static int MATCH_TERMINAL_UPPER_LIMIT = -1;
   java.lang.String matchTerminal;
   /**
    * 매칭터미널
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getMatchTerminal() {
      return matchTerminal;
   }
   /**
    * 매칭터미널
    *
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setMatchTerminal(java.lang.String matchTerminal) throws wt.util.WTPropertyVetoException {
      matchTerminalValidate(matchTerminal);
      this.matchTerminal = matchTerminal;
   }
   void matchTerminalValidate(java.lang.String matchTerminal) throws wt.util.WTPropertyVetoException {
      if (MATCH_TERMINAL_UPPER_LIMIT < 1) {
         try { MATCH_TERMINAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("matchTerminal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATCH_TERMINAL_UPPER_LIMIT = 4000; }
      }
      if (matchTerminal != null && !wt.fc.PersistenceHelper.checkStoredLength(matchTerminal.toString(), MATCH_TERMINAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "matchTerminal"), java.lang.String.valueOf(java.lang.Math.min(MATCH_TERMINAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "matchTerminal", this.matchTerminal, matchTerminal));
   }

   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public static final java.lang.String BIGO = "bigo";
   static int BIGO_UPPER_LIMIT = -1;
   java.lang.String bigo;
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public java.lang.String getBigo() {
      return bigo;
   }
   /**
    * @see ext.ket.part.entity.RivalPartInfo
    */
   public void setBigo(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      bigoValidate(bigo);
      this.bigo = bigo;
   }
   void bigoValidate(java.lang.String bigo) throws wt.util.WTPropertyVetoException {
      if (BIGO_UPPER_LIMIT < 1) {
         try { BIGO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bigo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BIGO_UPPER_LIMIT = 4000; }
      }
      if (bigo != null && !wt.fc.PersistenceHelper.checkStoredLength(bigo.toString(), BIGO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bigo"), java.lang.String.valueOf(java.lang.Math.min(BIGO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bigo", this.bigo, bigo));
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

   public static final long EXTERNALIZATION_VERSION_UID = 8712372742316443563L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( bigo );
      output.writeObject( companyCode );
      output.writeObject( companyName );
      output.writeObject( matchTerminal );
      output.writeObject( mfType );
      output.writeObject( owner );
      output.writeObject( partName );
      output.writeObject( partNo );
      output.writeObject( pole );
      output.writeObject( seriesCode );
      output.writeObject( seriesName );
      output.writeObject( thePersistInfo );
      output.writeObject( waterProof );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.RivalPartInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "bigo", bigo );
      output.setString( "companyCode", companyCode );
      output.setString( "companyName", companyName );
      output.setString( "matchTerminal", matchTerminal );
      output.setString( "mfType", mfType );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "partName", partName );
      output.setString( "partNo", partNo );
      output.setString( "pole", pole );
      output.setString( "seriesCode", seriesCode );
      output.setString( "seriesName", seriesName );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.setString( "waterProof", waterProof );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      bigo = input.getString( "bigo" );
      companyCode = input.getString( "companyCode" );
      companyName = input.getString( "companyName" );
      matchTerminal = input.getString( "matchTerminal" );
      mfType = input.getString( "mfType" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      partName = input.getString( "partName" );
      partNo = input.getString( "partNo" );
      pole = input.getString( "pole" );
      seriesCode = input.getString( "seriesCode" );
      seriesName = input.getString( "seriesName" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      waterProof = input.getString( "waterProof" );
   }

   boolean readVersion8712372742316443563L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      bigo = (java.lang.String) input.readObject();
      companyCode = (java.lang.String) input.readObject();
      companyName = (java.lang.String) input.readObject();
      matchTerminal = (java.lang.String) input.readObject();
      mfType = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      partName = (java.lang.String) input.readObject();
      partNo = (java.lang.String) input.readObject();
      pole = (java.lang.String) input.readObject();
      seriesCode = (java.lang.String) input.readObject();
      seriesName = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      waterProof = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( RivalPartInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion8712372742316443563L( input, readSerialVersionUID, superDone );
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
