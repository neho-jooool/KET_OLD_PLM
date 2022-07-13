package e3ps.project.customerPlan;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _CustomerPlan implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.customerPlan.customerPlanResource";
   static final java.lang.String CLASSNAME = CustomerPlan.class.getName();

   /**
    * 초도품
    *
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public static final java.lang.String INI__SAMPLE = "ini_Sample";
   static int INI__SAMPLE_UPPER_LIMIT = -1;
   java.lang.String ini_Sample;
   /**
    * 초도품
    *
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public java.lang.String getIni_Sample() {
      return ini_Sample;
   }
   /**
    * 초도품
    *
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public void setIni_Sample(java.lang.String ini_Sample) throws wt.util.WTPropertyVetoException {
      ini_SampleValidate(ini_Sample);
      this.ini_Sample = ini_Sample;
   }
   void ini_SampleValidate(java.lang.String ini_Sample) throws wt.util.WTPropertyVetoException {
      if (INI__SAMPLE_UPPER_LIMIT < 1) {
         try { INI__SAMPLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ini_Sample").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INI__SAMPLE_UPPER_LIMIT = 4000; }
      }
      if (ini_Sample != null && !wt.fc.PersistenceHelper.checkStoredLength(ini_Sample.toString(), INI__SAMPLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ini_Sample"), java.lang.String.valueOf(java.lang.Math.min(INI__SAMPLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ini_Sample", this.ini_Sample, ini_Sample));
   }

   /**
    * 초도품 일정
    *
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public static final java.lang.String INI__DATE = "ini_Date";
   static int INI__DATE_UPPER_LIMIT = -1;
   java.lang.String ini_Date;
   /**
    * 초도품 일정
    *
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public java.lang.String getIni_Date() {
      return ini_Date;
   }
   /**
    * 초도품 일정
    *
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public void setIni_Date(java.lang.String ini_Date) throws wt.util.WTPropertyVetoException {
      ini_DateValidate(ini_Date);
      this.ini_Date = ini_Date;
   }
   void ini_DateValidate(java.lang.String ini_Date) throws wt.util.WTPropertyVetoException {
      if (INI__DATE_UPPER_LIMIT < 1) {
         try { INI__DATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ini_Date").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { INI__DATE_UPPER_LIMIT = 4000; }
      }
      if (ini_Date != null && !wt.fc.PersistenceHelper.checkStoredLength(ini_Date.toString(), INI__DATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ini_Date"), java.lang.String.valueOf(java.lang.Math.min(INI__DATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ini_Date", this.ini_Date, ini_Date));
   }

   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public static final java.lang.String CUSTOMER = "customer";
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public static final java.lang.String CUSTOMER_REFERENCE = "customerReference";
   wt.fc.ObjectReference customerReference;
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public e3ps.common.code.NumberCode getCustomer() {
      return (customerReference != null) ? (e3ps.common.code.NumberCode) customerReference.getObject() : null;
   }
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public wt.fc.ObjectReference getCustomerReference() {
      return customerReference;
   }
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public void setCustomer(e3ps.common.code.NumberCode the_customer) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCustomerReference(the_customer == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_customer));
   }
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public void setCustomerReference(wt.fc.ObjectReference the_customerReference) throws wt.util.WTPropertyVetoException {
      customerReferenceValidate(the_customerReference);
      customerReference = (wt.fc.ObjectReference) the_customerReference;
   }
   void customerReferenceValidate(wt.fc.ObjectReference the_customerReference) throws wt.util.WTPropertyVetoException {
      if (the_customerReference == null || the_customerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerReference") },
               new java.beans.PropertyChangeEvent(this, "customerReference", this.customerReference, customerReference));
      if (the_customerReference != null && the_customerReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_customerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "customerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "customerReference", this.customerReference, customerReference));
   }

   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public static final java.lang.String PROJECT = "project";
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public static final java.lang.String PROJECT_REFERENCE = "projectReference";
   wt.fc.ObjectReference projectReference;
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public e3ps.project.E3PSProject getProject() {
      return (projectReference != null) ? (e3ps.project.E3PSProject) projectReference.getObject() : null;
   }
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public wt.fc.ObjectReference getProjectReference() {
      return projectReference;
   }
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
    */
   public void setProject(e3ps.project.E3PSProject the_project) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProjectReference(the_project == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.E3PSProject) the_project));
   }
   /**
    * @see e3ps.project.customerPlan.CustomerPlan
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
            !e3ps.project.E3PSProject.class.isAssignableFrom(the_projectReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "projectReference", this.projectReference, projectReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 1886873420995202975L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( customerReference );
      output.writeObject( ini_Date );
      output.writeObject( ini_Sample );
      output.writeObject( projectReference );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.customerPlan.CustomerPlan) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.writeObject( "customerReference", customerReference, wt.fc.ObjectReference.class, true );
      output.setString( "ini_Date", ini_Date );
      output.setString( "ini_Sample", ini_Sample );
      output.writeObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      customerReference = (wt.fc.ObjectReference) input.readObject( "customerReference", customerReference, wt.fc.ObjectReference.class, true );
      ini_Date = input.getString( "ini_Date" );
      ini_Sample = input.getString( "ini_Sample" );
      projectReference = (wt.fc.ObjectReference) input.readObject( "projectReference", projectReference, wt.fc.ObjectReference.class, true );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion1886873420995202975L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      customerReference = (wt.fc.ObjectReference) input.readObject();
      ini_Date = (java.lang.String) input.readObject();
      ini_Sample = (java.lang.String) input.readObject();
      projectReference = (wt.fc.ObjectReference) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( CustomerPlan thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion1886873420995202975L( input, readSerialVersionUID, superDone );
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
