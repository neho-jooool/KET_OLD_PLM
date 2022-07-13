package ext.ket.part.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETPartInfo implements e3ps.common.impl.OwnPersistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.part.entity.entityResource";
   static final java.lang.String CLASSNAME = KETPartInfo.class.getName();

   /**
    * 호환여부
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String REPLACE_GB = "replaceGb";
   static int REPLACE_GB_UPPER_LIMIT = -1;
   java.lang.String replaceGb;
   /**
    * 호환여부
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getReplaceGb() {
      return replaceGb;
   }
   /**
    * 호환여부
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setReplaceGb(java.lang.String replaceGb) throws wt.util.WTPropertyVetoException {
      replaceGbValidate(replaceGb);
      this.replaceGb = replaceGb;
   }
   void replaceGbValidate(java.lang.String replaceGb) throws wt.util.WTPropertyVetoException {
      if (REPLACE_GB_UPPER_LIMIT < 1) {
         try { REPLACE_GB_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("replaceGb").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { REPLACE_GB_UPPER_LIMIT = 200; }
      }
      if (replaceGb != null && !wt.fc.PersistenceHelper.checkStoredLength(replaceGb.toString(), REPLACE_GB_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "replaceGb"), java.lang.String.valueOf(java.lang.Math.min(REPLACE_GB_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "replaceGb", this.replaceGb, replaceGb));
   }

   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String KET_PART_NO = "ketPartNo";
   static int KET_PART_NO_UPPER_LIMIT = -1;
   java.lang.String ketPartNo;
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getKetPartNo() {
      return ketPartNo;
   }
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setKetPartNo(java.lang.String ketPartNo) throws wt.util.WTPropertyVetoException {
      ketPartNoValidate(ketPartNo);
      this.ketPartNo = ketPartNo;
   }
   void ketPartNoValidate(java.lang.String ketPartNo) throws wt.util.WTPropertyVetoException {
      if (KET_PART_NO_UPPER_LIMIT < 1) {
         try { KET_PART_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ketPartNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KET_PART_NO_UPPER_LIMIT = 200; }
      }
      if (ketPartNo != null && !wt.fc.PersistenceHelper.checkStoredLength(ketPartNo.toString(), KET_PART_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketPartNo"), java.lang.String.valueOf(java.lang.Math.min(KET_PART_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ketPartNo", this.ketPartNo, ketPartNo));
   }

   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String KET_PART_NAME = "ketPartName";
   static int KET_PART_NAME_UPPER_LIMIT = -1;
   java.lang.String ketPartName;
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getKetPartName() {
      return ketPartName;
   }
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setKetPartName(java.lang.String ketPartName) throws wt.util.WTPropertyVetoException {
      ketPartNameValidate(ketPartName);
      this.ketPartName = ketPartName;
   }
   void ketPartNameValidate(java.lang.String ketPartName) throws wt.util.WTPropertyVetoException {
      if (KET_PART_NAME_UPPER_LIMIT < 1) {
         try { KET_PART_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ketPartName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KET_PART_NAME_UPPER_LIMIT = 200; }
      }
      if (ketPartName != null && !wt.fc.PersistenceHelper.checkStoredLength(ketPartName.toString(), KET_PART_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketPartName"), java.lang.String.valueOf(java.lang.Math.min(KET_PART_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ketPartName", this.ketPartName, ketPartName));
   }

   /**
    * 방수,비방수
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String KET_WATER_PROOF = "ketWaterProof";
   static int KET_WATER_PROOF_UPPER_LIMIT = -1;
   java.lang.String ketWaterProof;
   /**
    * 방수,비방수
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getKetWaterProof() {
      return ketWaterProof;
   }
   /**
    * 방수,비방수
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setKetWaterProof(java.lang.String ketWaterProof) throws wt.util.WTPropertyVetoException {
      ketWaterProofValidate(ketWaterProof);
      this.ketWaterProof = ketWaterProof;
   }
   void ketWaterProofValidate(java.lang.String ketWaterProof) throws wt.util.WTPropertyVetoException {
      if (KET_WATER_PROOF_UPPER_LIMIT < 1) {
         try { KET_WATER_PROOF_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ketWaterProof").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KET_WATER_PROOF_UPPER_LIMIT = 200; }
      }
      if (ketWaterProof != null && !wt.fc.PersistenceHelper.checkStoredLength(ketWaterProof.toString(), KET_WATER_PROOF_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketWaterProof"), java.lang.String.valueOf(java.lang.Math.min(KET_WATER_PROOF_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ketWaterProof", this.ketWaterProof, ketWaterProof));
   }

   /**
    * 분류체계
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String CLASSIFICATION = "classification";
   static int CLASSIFICATION_UPPER_LIMIT = -1;
   java.lang.String classification;
   /**
    * 분류체계
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getClassification() {
      return classification;
   }
   /**
    * 분류체계
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setClassification(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      classificationValidate(classification);
      this.classification = classification;
   }
   void classificationValidate(java.lang.String classification) throws wt.util.WTPropertyVetoException {
      if (CLASSIFICATION_UPPER_LIMIT < 1) {
         try { CLASSIFICATION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("classification").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLASSIFICATION_UPPER_LIMIT = 200; }
      }
      if (classification != null && !wt.fc.PersistenceHelper.checkStoredLength(classification.toString(), CLASSIFICATION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "classification"), java.lang.String.valueOf(java.lang.Math.min(CLASSIFICATION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "classification", this.classification, classification));
   }

   /**
    * 극수
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String KET_POLE = "ketPole";
   static int KET_POLE_UPPER_LIMIT = -1;
   java.lang.String ketPole;
   /**
    * 극수
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getKetPole() {
      return ketPole;
   }
   /**
    * 극수
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setKetPole(java.lang.String ketPole) throws wt.util.WTPropertyVetoException {
      ketPoleValidate(ketPole);
      this.ketPole = ketPole;
   }
   void ketPoleValidate(java.lang.String ketPole) throws wt.util.WTPropertyVetoException {
      if (KET_POLE_UPPER_LIMIT < 1) {
         try { KET_POLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ketPole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KET_POLE_UPPER_LIMIT = 200; }
      }
      if (ketPole != null && !wt.fc.PersistenceHelper.checkStoredLength(ketPole.toString(), KET_POLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketPole"), java.lang.String.valueOf(java.lang.Math.min(KET_POLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ketPole", this.ketPole, ketPole));
   }

   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String KET_SERIES = "ketSeries";
   static int KET_SERIES_UPPER_LIMIT = -1;
   java.lang.String ketSeries;
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getKetSeries() {
      return ketSeries;
   }
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setKetSeries(java.lang.String ketSeries) throws wt.util.WTPropertyVetoException {
      ketSeriesValidate(ketSeries);
      this.ketSeries = ketSeries;
   }
   void ketSeriesValidate(java.lang.String ketSeries) throws wt.util.WTPropertyVetoException {
      if (KET_SERIES_UPPER_LIMIT < 1) {
         try { KET_SERIES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ketSeries").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KET_SERIES_UPPER_LIMIT = 200; }
      }
      if (ketSeries != null && !wt.fc.PersistenceHelper.checkStoredLength(ketSeries.toString(), KET_SERIES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketSeries"), java.lang.String.valueOf(java.lang.Math.min(KET_SERIES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ketSeries", this.ketSeries, ketSeries));
   }

   /**
    * 매칭터미널
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String KET_MATCH_TERMINAL = "ketMatchTerminal";
   static int KET_MATCH_TERMINAL_UPPER_LIMIT = -1;
   java.lang.String ketMatchTerminal;
   /**
    * 매칭터미널
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getKetMatchTerminal() {
      return ketMatchTerminal;
   }
   /**
    * 매칭터미널
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setKetMatchTerminal(java.lang.String ketMatchTerminal) throws wt.util.WTPropertyVetoException {
      ketMatchTerminalValidate(ketMatchTerminal);
      this.ketMatchTerminal = ketMatchTerminal;
   }
   void ketMatchTerminalValidate(java.lang.String ketMatchTerminal) throws wt.util.WTPropertyVetoException {
      if (KET_MATCH_TERMINAL_UPPER_LIMIT < 1) {
         try { KET_MATCH_TERMINAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ketMatchTerminal").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KET_MATCH_TERMINAL_UPPER_LIMIT = 4000; }
      }
      if (ketMatchTerminal != null && !wt.fc.PersistenceHelper.checkStoredLength(ketMatchTerminal.toString(), KET_MATCH_TERMINAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketMatchTerminal"), java.lang.String.valueOf(java.lang.Math.min(KET_MATCH_TERMINAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ketMatchTerminal", this.ketMatchTerminal, ketMatchTerminal));
   }

   /**
    * 매칭커넥터
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String KET_MATCH_CONNECTOR = "ketMatchConnector";
   static int KET_MATCH_CONNECTOR_UPPER_LIMIT = -1;
   java.lang.String ketMatchConnector;
   /**
    * 매칭커넥터
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public java.lang.String getKetMatchConnector() {
      return ketMatchConnector;
   }
   /**
    * 매칭커넥터
    *
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setKetMatchConnector(java.lang.String ketMatchConnector) throws wt.util.WTPropertyVetoException {
      ketMatchConnectorValidate(ketMatchConnector);
      this.ketMatchConnector = ketMatchConnector;
   }
   void ketMatchConnectorValidate(java.lang.String ketMatchConnector) throws wt.util.WTPropertyVetoException {
      if (KET_MATCH_CONNECTOR_UPPER_LIMIT < 1) {
         try { KET_MATCH_CONNECTOR_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ketMatchConnector").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { KET_MATCH_CONNECTOR_UPPER_LIMIT = 4000; }
      }
      if (ketMatchConnector != null && !wt.fc.PersistenceHelper.checkStoredLength(ketMatchConnector.toString(), KET_MATCH_CONNECTOR_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ketMatchConnector"), java.lang.String.valueOf(java.lang.Math.min(KET_MATCH_CONNECTOR_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ketMatchConnector", this.ketMatchConnector, ketMatchConnector));
   }

   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String RIVAL_PART = "rivalPart";
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public static final java.lang.String RIVAL_PART_REFERENCE = "rivalPartReference";
   wt.fc.ObjectReference rivalPartReference;
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public ext.ket.part.entity.RivalPartInfo getRivalPart() {
      return (rivalPartReference != null) ? (ext.ket.part.entity.RivalPartInfo) rivalPartReference.getObject() : null;
   }
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public wt.fc.ObjectReference getRivalPartReference() {
      return rivalPartReference;
   }
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setRivalPart(ext.ket.part.entity.RivalPartInfo the_rivalPart) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setRivalPartReference(the_rivalPart == null ? null : wt.fc.ObjectReference.newObjectReference((ext.ket.part.entity.RivalPartInfo) the_rivalPart));
   }
   /**
    * @see ext.ket.part.entity.KETPartInfo
    */
   public void setRivalPartReference(wt.fc.ObjectReference the_rivalPartReference) throws wt.util.WTPropertyVetoException {
      rivalPartReferenceValidate(the_rivalPartReference);
      rivalPartReference = (wt.fc.ObjectReference) the_rivalPartReference;
   }
   void rivalPartReferenceValidate(wt.fc.ObjectReference the_rivalPartReference) throws wt.util.WTPropertyVetoException {
      if (the_rivalPartReference == null || the_rivalPartReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rivalPartReference") },
               new java.beans.PropertyChangeEvent(this, "rivalPartReference", this.rivalPartReference, rivalPartReference));
      if (the_rivalPartReference != null && the_rivalPartReference.getReferencedClass() != null &&
            !ext.ket.part.entity.RivalPartInfo.class.isAssignableFrom(the_rivalPartReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "rivalPartReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "rivalPartReference", this.rivalPartReference, rivalPartReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 3812238810875174538L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( classification );
      output.writeObject( ketMatchConnector );
      output.writeObject( ketMatchTerminal );
      output.writeObject( ketPartName );
      output.writeObject( ketPartNo );
      output.writeObject( ketPole );
      output.writeObject( ketSeries );
      output.writeObject( ketWaterProof );
      output.writeObject( owner );
      output.writeObject( replaceGb );
      output.writeObject( rivalPartReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.part.entity.KETPartInfo) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "classification", classification );
      output.setString( "ketMatchConnector", ketMatchConnector );
      output.setString( "ketMatchTerminal", ketMatchTerminal );
      output.setString( "ketPartName", ketPartName );
      output.setString( "ketPartNo", ketPartNo );
      output.setString( "ketPole", ketPole );
      output.setString( "ketSeries", ketSeries );
      output.setString( "ketWaterProof", ketWaterProof );
      output.writeObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      output.setString( "replaceGb", replaceGb );
      output.writeObject( "rivalPartReference", rivalPartReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      classification = input.getString( "classification" );
      ketMatchConnector = input.getString( "ketMatchConnector" );
      ketMatchTerminal = input.getString( "ketMatchTerminal" );
      ketPartName = input.getString( "ketPartName" );
      ketPartNo = input.getString( "ketPartNo" );
      ketPole = input.getString( "ketPole" );
      ketSeries = input.getString( "ketSeries" );
      ketWaterProof = input.getString( "ketWaterProof" );
      owner = (wt.org.WTPrincipalReference) input.readObject( "owner", owner, wt.org.WTPrincipalReference.class, true );
      replaceGb = input.getString( "replaceGb" );
      rivalPartReference = (wt.fc.ObjectReference) input.readObject( "rivalPartReference", rivalPartReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion3812238810875174538L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      classification = (java.lang.String) input.readObject();
      ketMatchConnector = (java.lang.String) input.readObject();
      ketMatchTerminal = (java.lang.String) input.readObject();
      ketPartName = (java.lang.String) input.readObject();
      ketPartNo = (java.lang.String) input.readObject();
      ketPole = (java.lang.String) input.readObject();
      ketSeries = (java.lang.String) input.readObject();
      ketWaterProof = (java.lang.String) input.readObject();
      owner = (wt.org.WTPrincipalReference) input.readObject();
      replaceGb = (java.lang.String) input.readObject();
      rivalPartReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETPartInfo thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion3812238810875174538L( input, readSerialVersionUID, superDone );
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
