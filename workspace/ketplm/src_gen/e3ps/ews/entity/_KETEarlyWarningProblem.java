package e3ps.ews.entity;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _KETEarlyWarningProblem implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.ews.entity.entityResource";
   static final java.lang.String CLASSNAME = KETEarlyWarningProblem.class.getName();

   /**
    * 문제점 종류
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String PROBLEMKIND = "problemkind";
   static int PROBLEMKIND_UPPER_LIMIT = -1;
   java.lang.String problemkind;
   /**
    * 문제점 종류
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.lang.String getProblemkind() {
      return problemkind;
   }
   /**
    * 문제점 종류
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setProblemkind(java.lang.String problemkind) throws wt.util.WTPropertyVetoException {
      problemkindValidate(problemkind);
      this.problemkind = problemkind;
   }
   void problemkindValidate(java.lang.String problemkind) throws wt.util.WTPropertyVetoException {
      if (PROBLEMKIND_UPPER_LIMIT < 1) {
         try { PROBLEMKIND_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemkind").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEMKIND_UPPER_LIMIT = 200; }
      }
      if (problemkind != null && !wt.fc.PersistenceHelper.checkStoredLength(problemkind.toString(), PROBLEMKIND_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemkind"), java.lang.String.valueOf(java.lang.Math.min(PROBLEMKIND_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemkind", this.problemkind, problemkind));
   }

   /**
    * 공정
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String PROBLEMSTEP = "problemstep";
   static int PROBLEMSTEP_UPPER_LIMIT = -1;
   java.lang.String problemstep;
   /**
    * 공정
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.lang.String getProblemstep() {
      return problemstep;
   }
   /**
    * 공정
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setProblemstep(java.lang.String problemstep) throws wt.util.WTPropertyVetoException {
      problemstepValidate(problemstep);
      this.problemstep = problemstep;
   }
   void problemstepValidate(java.lang.String problemstep) throws wt.util.WTPropertyVetoException {
      if (PROBLEMSTEP_UPPER_LIMIT < 1) {
         try { PROBLEMSTEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemstep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEMSTEP_UPPER_LIMIT = 1000; }
      }
      if (problemstep != null && !wt.fc.PersistenceHelper.checkStoredLength(problemstep.toString(), PROBLEMSTEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemstep"), java.lang.String.valueOf(java.lang.Math.min(PROBLEMSTEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemstep", this.problemstep, problemstep));
   }

   /**
    * 유형
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String PROBLEMTYPE = "problemtype";
   static int PROBLEMTYPE_UPPER_LIMIT = -1;
   java.lang.String problemtype;
   /**
    * 유형
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.lang.String getProblemtype() {
      return problemtype;
   }
   /**
    * 유형
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setProblemtype(java.lang.String problemtype) throws wt.util.WTPropertyVetoException {
      problemtypeValidate(problemtype);
      this.problemtype = problemtype;
   }
   void problemtypeValidate(java.lang.String problemtype) throws wt.util.WTPropertyVetoException {
      if (PROBLEMTYPE_UPPER_LIMIT < 1) {
         try { PROBLEMTYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemtype").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEMTYPE_UPPER_LIMIT = 200; }
      }
      if (problemtype != null && !wt.fc.PersistenceHelper.checkStoredLength(problemtype.toString(), PROBLEMTYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemtype"), java.lang.String.valueOf(java.lang.Math.min(PROBLEMTYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemtype", this.problemtype, problemtype));
   }

   /**
    * 문제점
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String PROBLEMDESC = "problemdesc";
   static int PROBLEMDESC_UPPER_LIMIT = -1;
   java.lang.String problemdesc;
   /**
    * 문제점
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.lang.String getProblemdesc() {
      return problemdesc;
   }
   /**
    * 문제점
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setProblemdesc(java.lang.String problemdesc) throws wt.util.WTPropertyVetoException {
      problemdescValidate(problemdesc);
      this.problemdesc = problemdesc;
   }
   void problemdescValidate(java.lang.String problemdesc) throws wt.util.WTPropertyVetoException {
      if (PROBLEMDESC_UPPER_LIMIT < 1) {
         try { PROBLEMDESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problemdesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEMDESC_UPPER_LIMIT = 4000; }
      }
      if (problemdesc != null && !wt.fc.PersistenceHelper.checkStoredLength(problemdesc.toString(), PROBLEMDESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problemdesc"), java.lang.String.valueOf(java.lang.Math.min(PROBLEMDESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problemdesc", this.problemdesc, problemdesc));
   }

   /**
    * 대책
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String MEASURE = "measure";
   static int MEASURE_UPPER_LIMIT = -1;
   java.lang.String measure;
   /**
    * 대책
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.lang.String getMeasure() {
      return measure;
   }
   /**
    * 대책
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setMeasure(java.lang.String measure) throws wt.util.WTPropertyVetoException {
      measureValidate(measure);
      this.measure = measure;
   }
   void measureValidate(java.lang.String measure) throws wt.util.WTPropertyVetoException {
      if (MEASURE_UPPER_LIMIT < 1) {
         try { MEASURE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("measure").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEASURE_UPPER_LIMIT = 4000; }
      }
      if (measure != null && !wt.fc.PersistenceHelper.checkStoredLength(measure.toString(), MEASURE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "measure"), java.lang.String.valueOf(java.lang.Math.min(MEASURE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "measure", this.measure, measure));
   }

   /**
    * 완료일
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String ENDDATE = "enddate";
   java.sql.Timestamp enddate;
   /**
    * 완료일
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.sql.Timestamp getEnddate() {
      return enddate;
   }
   /**
    * 완료일
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setEnddate(java.sql.Timestamp enddate) throws wt.util.WTPropertyVetoException {
      enddateValidate(enddate);
      this.enddate = enddate;
   }
   void enddateValidate(java.sql.Timestamp enddate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 담당
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String CHARGE = "charge";
   static int CHARGE_UPPER_LIMIT = -1;
   java.lang.String charge;
   /**
    * 담당
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.lang.String getCharge() {
      return charge;
   }
   /**
    * 담당
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setCharge(java.lang.String charge) throws wt.util.WTPropertyVetoException {
      chargeValidate(charge);
      this.charge = charge;
   }
   void chargeValidate(java.lang.String charge) throws wt.util.WTPropertyVetoException {
      if (CHARGE_UPPER_LIMIT < 1) {
         try { CHARGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("charge").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHARGE_UPPER_LIMIT = 200; }
      }
      if (charge != null && !wt.fc.PersistenceHelper.checkStoredLength(charge.toString(), CHARGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "charge"), java.lang.String.valueOf(java.lang.Math.min(CHARGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "charge", this.charge, charge));
   }

   /**
    * 조치여부
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public static final java.lang.String ISEND = "isend";
   static int ISEND_UPPER_LIMIT = -1;
   java.lang.String isend;
   /**
    * 조치여부
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public java.lang.String getIsend() {
      return isend;
   }
   /**
    * 조치여부
    *
    * @see e3ps.ews.entity.KETEarlyWarningProblem
    */
   public void setIsend(java.lang.String isend) throws wt.util.WTPropertyVetoException {
      isendValidate(isend);
      this.isend = isend;
   }
   void isendValidate(java.lang.String isend) throws wt.util.WTPropertyVetoException {
      if (ISEND_UPPER_LIMIT < 1) {
         try { ISEND_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("isend").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ISEND_UPPER_LIMIT = 200; }
      }
      if (isend != null && !wt.fc.PersistenceHelper.checkStoredLength(isend.toString(), ISEND_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "isend"), java.lang.String.valueOf(java.lang.Math.min(ISEND_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "isend", this.isend, isend));
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

   public static final long EXTERNALIZATION_VERSION_UID = 3412690418044223593L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( charge );
      output.writeObject( enddate );
      output.writeObject( isend );
      output.writeObject( measure );
      output.writeObject( problemdesc );
      output.writeObject( problemkind );
      output.writeObject( problemstep );
      output.writeObject( problemtype );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.ews.entity.KETEarlyWarningProblem) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "charge", charge );
      output.setTimestamp( "enddate", enddate );
      output.setString( "isend", isend );
      output.setString( "measure", measure );
      output.setString( "problemdesc", problemdesc );
      output.setString( "problemkind", problemkind );
      output.setString( "problemstep", problemstep );
      output.setString( "problemtype", problemtype );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      charge = input.getString( "charge" );
      enddate = input.getTimestamp( "enddate" );
      isend = input.getString( "isend" );
      measure = input.getString( "measure" );
      problemdesc = input.getString( "problemdesc" );
      problemkind = input.getString( "problemkind" );
      problemstep = input.getString( "problemstep" );
      problemtype = input.getString( "problemtype" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion3412690418044223593L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      charge = (java.lang.String) input.readObject();
      enddate = (java.sql.Timestamp) input.readObject();
      isend = (java.lang.String) input.readObject();
      measure = (java.lang.String) input.readObject();
      problemdesc = (java.lang.String) input.readObject();
      problemkind = (java.lang.String) input.readObject();
      problemstep = (java.lang.String) input.readObject();
      problemtype = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( KETEarlyWarningProblem thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion3412690418044223593L( input, readSerialVersionUID, superDone );
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
