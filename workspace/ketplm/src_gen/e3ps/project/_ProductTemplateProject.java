package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _ProductTemplateProject extends e3ps.project.TemplateProject implements java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = ProductTemplateProject.class.getName();

   /**
    * 설계유무
    *
    * @see e3ps.project.ProductTemplateProject
    */
   public static final java.lang.String PLAN_TYPE = "planType";
   boolean planType;
   /**
    * 설계유무
    *
    * @see e3ps.project.ProductTemplateProject
    */
   public boolean isPlanType() {
      return planType;
   }
   /**
    * 설계유무
    *
    * @see e3ps.project.ProductTemplateProject
    */
   public void setPlanType(boolean planType) throws wt.util.WTPropertyVetoException {
      planTypeValidate(planType);
      this.planType = planType;
   }
   void planTypeValidate(boolean planType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 조립유무
    *
    * @see e3ps.project.ProductTemplateProject
    */
   public static final java.lang.String ASSEMBLING = "assembling";
   boolean assembling;
   /**
    * 조립유무
    *
    * @see e3ps.project.ProductTemplateProject
    */
   public boolean isAssembling() {
      return assembling;
   }
   /**
    * 조립유무
    *
    * @see e3ps.project.ProductTemplateProject
    */
   public void setAssembling(boolean assembling) throws wt.util.WTPropertyVetoException {
      assemblingValidate(assembling);
      this.assembling = assembling;
   }
   void assemblingValidate(boolean assembling) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.ProductTemplateProject
    */
   public static final java.lang.String DEVELOP_TYPE = "developType";
   int developType;
   /**
    * @see e3ps.project.ProductTemplateProject
    */
   public int getDevelopType() {
      return developType;
   }
   /**
    * @see e3ps.project.ProductTemplateProject
    */
   public void setDevelopType(int developType) throws wt.util.WTPropertyVetoException {
      developTypeValidate(developType);
      this.developType = developType;
   }
   void developTypeValidate(int developType) throws wt.util.WTPropertyVetoException {
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

   public static final long EXTERNALIZATION_VERSION_UID = -312271003749454143L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeBoolean( assembling );
      output.writeInt( developType );
      output.writeBoolean( planType );
   }

   protected void super_writeExternal_ProductTemplateProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.ProductTemplateProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_ProductTemplateProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setBoolean( "assembling", assembling );
      output.setInt( "developType", developType );
      output.setBoolean( "planType", planType );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      assembling = input.getBoolean( "assembling" );
      developType = input.getInt( "developType" );
      planType = input.getBoolean( "planType" );
   }

   boolean readVersion_312271003749454143L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      assembling = input.readBoolean();
      developType = input.readInt();
      planType = input.readBoolean();
      return true;
   }

   protected boolean readVersion( ProductTemplateProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_312271003749454143L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_ProductTemplateProject( _ProductTemplateProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
