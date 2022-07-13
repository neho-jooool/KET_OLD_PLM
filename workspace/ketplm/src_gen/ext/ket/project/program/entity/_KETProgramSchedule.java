package ext.ket.project.program.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETProgramSchedule extends wt.doc.WTDocument implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "ext.ket.project.program.entity.entityResource";
   static final java.lang.String CLASSNAME = KETProgramSchedule.class.getName();

   /**
    * 프로그램명
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String PROGRAM_NAME = "programName";
   static int PROGRAM_NAME_UPPER_LIMIT = -1;
   java.lang.String programName;
   /**
    * 프로그램명
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public java.lang.String getProgramName() {
      return programName;
   }
   /**
    * 프로그램명
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setProgramName(java.lang.String programName) throws wt.util.WTPropertyVetoException {
      programNameValidate(programName);
      this.programName = programName;
   }
   void programNameValidate(java.lang.String programName) throws wt.util.WTPropertyVetoException {
      if (PROGRAM_NAME_UPPER_LIMIT < 1) {
         try { PROGRAM_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("programName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROGRAM_NAME_UPPER_LIMIT = 200; }
      }
      if (programName != null && !wt.fc.PersistenceHelper.checkStoredLength(programName.toString(), PROGRAM_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "programName"), java.lang.String.valueOf(java.lang.Math.min(PROGRAM_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "programName", this.programName, programName));
   }

   /**
    * 프로그램 시작일
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String START_DATE = "startDate";
   java.sql.Timestamp startDate;
   /**
    * 프로그램 시작일
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public java.sql.Timestamp getStartDate() {
      return startDate;
   }
   /**
    * 프로그램 시작일
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setStartDate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
      startDateValidate(startDate);
      this.startDate = startDate;
   }
   void startDateValidate(java.sql.Timestamp startDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로그램 종료일
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String END_DATE = "endDate";
   java.sql.Timestamp endDate;
   /**
    * 프로그램 종료일
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public java.sql.Timestamp getEndDate() {
      return endDate;
   }
   /**
    * 프로그램 종료일
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setEndDate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
      endDateValidate(endDate);
      this.endDate = endDate;
   }
   void endDateValidate(java.sql.Timestamp endDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 사업부코드
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String DIVISION_CODE = "divisionCode";
   static int DIVISION_CODE_UPPER_LIMIT = -1;
   java.lang.String divisionCode;
   /**
    * 사업부코드
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public java.lang.String getDivisionCode() {
      return divisionCode;
   }
   /**
    * 사업부코드
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setDivisionCode(java.lang.String divisionCode) throws wt.util.WTPropertyVetoException {
      divisionCodeValidate(divisionCode);
      this.divisionCode = divisionCode;
   }
   void divisionCodeValidate(java.lang.String divisionCode) throws wt.util.WTPropertyVetoException {
      if (DIVISION_CODE_UPPER_LIMIT < 1) {
         try { DIVISION_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("divisionCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIVISION_CODE_UPPER_LIMIT = 200; }
      }
      if (divisionCode != null && !wt.fc.PersistenceHelper.checkStoredLength(divisionCode.toString(), DIVISION_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "divisionCode"), java.lang.String.valueOf(java.lang.Math.min(DIVISION_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "divisionCode", this.divisionCode, divisionCode));
   }

   /**
    * 공지유무
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String IS_NOTIFY = "isNotify";
   java.lang.Boolean isNotify = false;
   /**
    * 공지유무
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public java.lang.Boolean getIsNotify() {
      return isNotify;
   }
   /**
    * 공지유무
    *
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setIsNotify(java.lang.Boolean isNotify) throws wt.util.WTPropertyVetoException {
      isNotifyValidate(isNotify);
      this.isNotify = isNotify;
   }
   void isNotifyValidate(java.lang.Boolean isNotify) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String SUB_CONTRACTOR = "subContractor";
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String SUB_CONTRACTOR_REFERENCE = "subContractorReference";
   wt.fc.ObjectReference subContractorReference;
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public e3ps.common.code.NumberCode getSubContractor() {
      return (subContractorReference != null) ? (e3ps.common.code.NumberCode) subContractorReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public wt.fc.ObjectReference getSubContractorReference() {
      return subContractorReference;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setSubContractor(e3ps.common.code.NumberCode the_subContractor) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setSubContractorReference(the_subContractor == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_subContractor));
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setSubContractorReference(wt.fc.ObjectReference the_subContractorReference) throws wt.util.WTPropertyVetoException {
      subContractorReferenceValidate(the_subContractorReference);
      subContractorReference = (wt.fc.ObjectReference) the_subContractorReference;
   }
   void subContractorReferenceValidate(wt.fc.ObjectReference the_subContractorReference) throws wt.util.WTPropertyVetoException {
      if (the_subContractorReference == null || the_subContractorReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subContractorReference") },
               new java.beans.PropertyChangeEvent(this, "subContractorReference", this.subContractorReference, subContractorReference));
      if (the_subContractorReference != null && the_subContractorReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_subContractorReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "subContractorReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "subContractorReference", this.subContractorReference, subContractorReference));
   }

   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String LAST_USING_BUYER = "lastUsingBuyer";
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String LAST_USING_BUYER_REFERENCE = "lastUsingBuyerReference";
   wt.fc.ObjectReference lastUsingBuyerReference;
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public e3ps.common.code.NumberCode getLastUsingBuyer() {
      return (lastUsingBuyerReference != null) ? (e3ps.common.code.NumberCode) lastUsingBuyerReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public wt.fc.ObjectReference getLastUsingBuyerReference() {
      return lastUsingBuyerReference;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setLastUsingBuyer(e3ps.common.code.NumberCode the_lastUsingBuyer) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setLastUsingBuyerReference(the_lastUsingBuyer == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_lastUsingBuyer));
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setLastUsingBuyerReference(wt.fc.ObjectReference the_lastUsingBuyerReference) throws wt.util.WTPropertyVetoException {
      lastUsingBuyerReferenceValidate(the_lastUsingBuyerReference);
      lastUsingBuyerReference = (wt.fc.ObjectReference) the_lastUsingBuyerReference;
   }
   void lastUsingBuyerReferenceValidate(wt.fc.ObjectReference the_lastUsingBuyerReference) throws wt.util.WTPropertyVetoException {
      if (the_lastUsingBuyerReference == null || the_lastUsingBuyerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastUsingBuyerReference") },
               new java.beans.PropertyChangeEvent(this, "lastUsingBuyerReference", this.lastUsingBuyerReference, lastUsingBuyerReference));
      if (the_lastUsingBuyerReference != null && the_lastUsingBuyerReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_lastUsingBuyerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "lastUsingBuyerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "lastUsingBuyerReference", this.lastUsingBuyerReference, lastUsingBuyerReference));
   }

   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String FORM_TYPE = "formType";
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String FORM_TYPE_REFERENCE = "formTypeReference";
   wt.fc.ObjectReference formTypeReference;
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public e3ps.common.code.NumberCode getFormType() {
      return (formTypeReference != null) ? (e3ps.common.code.NumberCode) formTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public wt.fc.ObjectReference getFormTypeReference() {
      return formTypeReference;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setFormType(e3ps.common.code.NumberCode the_formType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFormTypeReference(the_formType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_formType));
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
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
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String CAR_TYPE = "carType";
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String CAR_TYPE_REFERENCE = "carTypeReference";
   wt.fc.ObjectReference carTypeReference;
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public e3ps.project.outputtype.OEMProjectType getCarType() {
      return (carTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) carTypeReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public wt.fc.ObjectReference getCarTypeReference() {
      return carTypeReference;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setCarType(e3ps.project.outputtype.OEMProjectType the_carType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCarTypeReference(the_carType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_carType));
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
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

   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String PROGRAM_ADMIN = "programAdmin";
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public static final java.lang.String PROGRAM_ADMIN_REFERENCE = "programAdminReference";
   wt.fc.ObjectReference programAdminReference;
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public wt.org.WTUser getProgramAdmin() {
      return (programAdminReference != null) ? (wt.org.WTUser) programAdminReference.getObject() : null;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public wt.fc.ObjectReference getProgramAdminReference() {
      return programAdminReference;
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setProgramAdmin(wt.org.WTUser the_programAdmin) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProgramAdminReference(the_programAdmin == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_programAdmin));
   }
   /**
    * @see ext.ket.project.program.entity.KETProgramSchedule
    */
   public void setProgramAdminReference(wt.fc.ObjectReference the_programAdminReference) throws wt.util.WTPropertyVetoException {
      programAdminReferenceValidate(the_programAdminReference);
      programAdminReference = (wt.fc.ObjectReference) the_programAdminReference;
   }
   void programAdminReferenceValidate(wt.fc.ObjectReference the_programAdminReference) throws wt.util.WTPropertyVetoException {
      if (the_programAdminReference == null || the_programAdminReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "programAdminReference") },
               new java.beans.PropertyChangeEvent(this, "programAdminReference", this.programAdminReference, programAdminReference));
      if (the_programAdminReference != null && the_programAdminReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_programAdminReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "programAdminReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "programAdminReference", this.programAdminReference, programAdminReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -157468076942533285L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( carTypeReference );
      output.writeObject( divisionCode );
      output.writeObject( endDate );
      output.writeObject( formTypeReference );
      output.writeObject( isNotify );
      output.writeObject( lastUsingBuyerReference );
      output.writeObject( programAdminReference );
      output.writeObject( programName );
      output.writeObject( startDate );
      output.writeObject( subContractorReference );
   }

   protected void super_writeExternal_KETProgramSchedule(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (ext.ket.project.program.entity.KETProgramSchedule) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_KETProgramSchedule(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.writeObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "divisionCode", divisionCode );
      output.setTimestamp( "endDate", endDate );
      output.writeObject( "formTypeReference", formTypeReference, wt.fc.ObjectReference.class, true );
      output.setBooleanObject( "isNotify", isNotify );
      output.writeObject( "lastUsingBuyerReference", lastUsingBuyerReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "programAdminReference", programAdminReference, wt.fc.ObjectReference.class, true );
      output.setString( "programName", programName );
      output.setTimestamp( "startDate", startDate );
      output.writeObject( "subContractorReference", subContractorReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      carTypeReference = (wt.fc.ObjectReference) input.readObject( "carTypeReference", carTypeReference, wt.fc.ObjectReference.class, true );
      divisionCode = input.getString( "divisionCode" );
      endDate = input.getTimestamp( "endDate" );
      formTypeReference = (wt.fc.ObjectReference) input.readObject( "formTypeReference", formTypeReference, wt.fc.ObjectReference.class, true );
      isNotify = input.getBooleanObject( "isNotify" );
      lastUsingBuyerReference = (wt.fc.ObjectReference) input.readObject( "lastUsingBuyerReference", lastUsingBuyerReference, wt.fc.ObjectReference.class, true );
      programAdminReference = (wt.fc.ObjectReference) input.readObject( "programAdminReference", programAdminReference, wt.fc.ObjectReference.class, true );
      programName = input.getString( "programName" );
      startDate = input.getTimestamp( "startDate" );
      subContractorReference = (wt.fc.ObjectReference) input.readObject( "subContractorReference", subContractorReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion_157468076942533285L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      carTypeReference = (wt.fc.ObjectReference) input.readObject();
      divisionCode = (java.lang.String) input.readObject();
      endDate = (java.sql.Timestamp) input.readObject();
      formTypeReference = (wt.fc.ObjectReference) input.readObject();
      isNotify = (java.lang.Boolean) input.readObject();
      lastUsingBuyerReference = (wt.fc.ObjectReference) input.readObject();
      programAdminReference = (wt.fc.ObjectReference) input.readObject();
      programName = (java.lang.String) input.readObject();
      startDate = (java.sql.Timestamp) input.readObject();
      subContractorReference = (wt.fc.ObjectReference) input.readObject();
      return true;
   }

   protected boolean readVersion( KETProgramSchedule thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_157468076942533285L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_KETProgramSchedule( _KETProgramSchedule thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
