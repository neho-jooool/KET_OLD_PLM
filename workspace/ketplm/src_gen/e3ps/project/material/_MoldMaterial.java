package e3ps.project.material;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _MoldMaterial implements wt.fc.Persistable, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.material.materialResource";
   static final java.lang.String CLASSNAME = MoldMaterial.class.getName();

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String MATERIAL = "material";
   static int MATERIAL_UPPER_LIMIT = -1;
   java.lang.String material;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getMaterial() {
      return material;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setMaterial(java.lang.String material) throws wt.util.WTPropertyVetoException {
      materialValidate(material);
      this.material = material;
   }
   void materialValidate(java.lang.String material) throws wt.util.WTPropertyVetoException {
      if (MATERIAL_UPPER_LIMIT < 1) {
         try { MATERIAL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("material").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MATERIAL_UPPER_LIMIT = 200; }
      }
      if (material != null && !wt.fc.PersistenceHelper.checkStoredLength(material.toString(), MATERIAL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "material"), java.lang.String.valueOf(java.lang.Math.min(MATERIAL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "material", this.material, material));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String GRADE = "grade";
   static int GRADE_UPPER_LIMIT = -1;
   java.lang.String grade;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getGrade() {
      return grade;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setGrade(java.lang.String grade) throws wt.util.WTPropertyVetoException {
      gradeValidate(grade);
      this.grade = grade;
   }
   void gradeValidate(java.lang.String grade) throws wt.util.WTPropertyVetoException {
      if (GRADE_UPPER_LIMIT < 1) {
         try { GRADE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("grade").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GRADE_UPPER_LIMIT = 200; }
      }
      if (grade != null && !wt.fc.PersistenceHelper.checkStoredLength(grade.toString(), GRADE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "grade"), java.lang.String.valueOf(java.lang.Math.min(GRADE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "grade", this.grade, grade));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String SPEC_NO = "spec_no";
   static int SPEC_NO_UPPER_LIMIT = -1;
   java.lang.String spec_no;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getSpec_no() {
      return spec_no;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setSpec_no(java.lang.String spec_no) throws wt.util.WTPropertyVetoException {
      spec_noValidate(spec_no);
      this.spec_no = spec_no;
   }
   void spec_noValidate(java.lang.String spec_no) throws wt.util.WTPropertyVetoException {
      if (SPEC_NO_UPPER_LIMIT < 1) {
         try { SPEC_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("spec_no").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SPEC_NO_UPPER_LIMIT = 200; }
      }
      if (spec_no != null && !wt.fc.PersistenceHelper.checkStoredLength(spec_no.toString(), SPEC_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "spec_no"), java.lang.String.valueOf(java.lang.Math.min(SPEC_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "spec_no", this.spec_no, spec_no));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String TEMPER = "temper";
   static int TEMPER_UPPER_LIMIT = -1;
   java.lang.String temper;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getTemper() {
      return temper;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setTemper(java.lang.String temper) throws wt.util.WTPropertyVetoException {
      temperValidate(temper);
      this.temper = temper;
   }
   void temperValidate(java.lang.String temper) throws wt.util.WTPropertyVetoException {
      if (TEMPER_UPPER_LIMIT < 1) {
         try { TEMPER_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("temper").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { TEMPER_UPPER_LIMIT = 200; }
      }
      if (temper != null && !wt.fc.PersistenceHelper.checkStoredLength(temper.toString(), TEMPER_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "temper"), java.lang.String.valueOf(java.lang.Math.min(TEMPER_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "temper", this.temper, temper));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String S_GRAVITY = "s_gravity";
   static int S_GRAVITY_UPPER_LIMIT = -1;
   java.lang.String s_gravity;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getS_gravity() {
      return s_gravity;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setS_gravity(java.lang.String s_gravity) throws wt.util.WTPropertyVetoException {
      s_gravityValidate(s_gravity);
      this.s_gravity = s_gravity;
   }
   void s_gravityValidate(java.lang.String s_gravity) throws wt.util.WTPropertyVetoException {
      if (S_GRAVITY_UPPER_LIMIT < 1) {
         try { S_GRAVITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("s_gravity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { S_GRAVITY_UPPER_LIMIT = 200; }
      }
      if (s_gravity != null && !wt.fc.PersistenceHelper.checkStoredLength(s_gravity.toString(), S_GRAVITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "s_gravity"), java.lang.String.valueOf(java.lang.Math.min(S_GRAVITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "s_gravity", this.s_gravity, s_gravity));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String M_ELASTICITY = "m_elasticity";
   static int M_ELASTICITY_UPPER_LIMIT = -1;
   java.lang.String m_elasticity;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getM_elasticity() {
      return m_elasticity;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setM_elasticity(java.lang.String m_elasticity) throws wt.util.WTPropertyVetoException {
      m_elasticityValidate(m_elasticity);
      this.m_elasticity = m_elasticity;
   }
   void m_elasticityValidate(java.lang.String m_elasticity) throws wt.util.WTPropertyVetoException {
      if (M_ELASTICITY_UPPER_LIMIT < 1) {
         try { M_ELASTICITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("m_elasticity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { M_ELASTICITY_UPPER_LIMIT = 200; }
      }
      if (m_elasticity != null && !wt.fc.PersistenceHelper.checkStoredLength(m_elasticity.toString(), M_ELASTICITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "m_elasticity"), java.lang.String.valueOf(java.lang.Math.min(M_ELASTICITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "m_elasticity", this.m_elasticity, m_elasticity));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String E_CONDUCTIVITY = "e_conductivity";
   static int E_CONDUCTIVITY_UPPER_LIMIT = -1;
   java.lang.String e_conductivity;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getE_conductivity() {
      return e_conductivity;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setE_conductivity(java.lang.String e_conductivity) throws wt.util.WTPropertyVetoException {
      e_conductivityValidate(e_conductivity);
      this.e_conductivity = e_conductivity;
   }
   void e_conductivityValidate(java.lang.String e_conductivity) throws wt.util.WTPropertyVetoException {
      if (E_CONDUCTIVITY_UPPER_LIMIT < 1) {
         try { E_CONDUCTIVITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("e_conductivity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { E_CONDUCTIVITY_UPPER_LIMIT = 200; }
      }
      if (e_conductivity != null && !wt.fc.PersistenceHelper.checkStoredLength(e_conductivity.toString(), E_CONDUCTIVITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "e_conductivity"), java.lang.String.valueOf(java.lang.Math.min(E_CONDUCTIVITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "e_conductivity", this.e_conductivity, e_conductivity));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String HARDNESS = "hardness";
   static int HARDNESS_UPPER_LIMIT = -1;
   java.lang.String hardness;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getHardness() {
      return hardness;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setHardness(java.lang.String hardness) throws wt.util.WTPropertyVetoException {
      hardnessValidate(hardness);
      this.hardness = hardness;
   }
   void hardnessValidate(java.lang.String hardness) throws wt.util.WTPropertyVetoException {
      if (HARDNESS_UPPER_LIMIT < 1) {
         try { HARDNESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("hardness").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HARDNESS_UPPER_LIMIT = 200; }
      }
      if (hardness != null && !wt.fc.PersistenceHelper.checkStoredLength(hardness.toString(), HARDNESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "hardness"), java.lang.String.valueOf(java.lang.Math.min(HARDNESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "hardness", this.hardness, hardness));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String STRENGTH = "strength";
   static int STRENGTH_UPPER_LIMIT = -1;
   java.lang.String strength;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getStrength() {
      return strength;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setStrength(java.lang.String strength) throws wt.util.WTPropertyVetoException {
      strengthValidate(strength);
      this.strength = strength;
   }
   void strengthValidate(java.lang.String strength) throws wt.util.WTPropertyVetoException {
      if (STRENGTH_UPPER_LIMIT < 1) {
         try { STRENGTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("strength").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STRENGTH_UPPER_LIMIT = 200; }
      }
      if (strength != null && !wt.fc.PersistenceHelper.checkStoredLength(strength.toString(), STRENGTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "strength"), java.lang.String.valueOf(java.lang.Math.min(STRENGTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "strength", this.strength, strength));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String ELONG = "elong";
   static int ELONG_UPPER_LIMIT = -1;
   java.lang.String elong;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getElong() {
      return elong;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setElong(java.lang.String elong) throws wt.util.WTPropertyVetoException {
      elongValidate(elong);
      this.elong = elong;
   }
   void elongValidate(java.lang.String elong) throws wt.util.WTPropertyVetoException {
      if (ELONG_UPPER_LIMIT < 1) {
         try { ELONG_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("elong").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ELONG_UPPER_LIMIT = 200; }
      }
      if (elong != null && !wt.fc.PersistenceHelper.checkStoredLength(elong.toString(), ELONG_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "elong"), java.lang.String.valueOf(java.lang.Math.min(ELONG_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "elong", this.elong, elong));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String FORMABILITY = "formability";
   static int FORMABILITY_UPPER_LIMIT = -1;
   java.lang.String formability;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getFormability() {
      return formability;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setFormability(java.lang.String formability) throws wt.util.WTPropertyVetoException {
      formabilityValidate(formability);
      this.formability = formability;
   }
   void formabilityValidate(java.lang.String formability) throws wt.util.WTPropertyVetoException {
      if (FORMABILITY_UPPER_LIMIT < 1) {
         try { FORMABILITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("formability").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FORMABILITY_UPPER_LIMIT = 200; }
      }
      if (formability != null && !wt.fc.PersistenceHelper.checkStoredLength(formability.toString(), FORMABILITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "formability"), java.lang.String.valueOf(java.lang.Math.min(FORMABILITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "formability", this.formability, formability));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String RESIDUAL_STRESS = "residual_stress";
   static int RESIDUAL_STRESS_UPPER_LIMIT = -1;
   java.lang.String residual_stress;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getResidual_stress() {
      return residual_stress;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setResidual_stress(java.lang.String residual_stress) throws wt.util.WTPropertyVetoException {
      residual_stressValidate(residual_stress);
      this.residual_stress = residual_stress;
   }
   void residual_stressValidate(java.lang.String residual_stress) throws wt.util.WTPropertyVetoException {
      if (RESIDUAL_STRESS_UPPER_LIMIT < 1) {
         try { RESIDUAL_STRESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("residual_stress").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RESIDUAL_STRESS_UPPER_LIMIT = 200; }
      }
      if (residual_stress != null && !wt.fc.PersistenceHelper.checkStoredLength(residual_stress.toString(), RESIDUAL_STRESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "residual_stress"), java.lang.String.valueOf(java.lang.Math.min(RESIDUAL_STRESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "residual_stress", this.residual_stress, residual_stress));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String T_MELT = "t_melt";
   static int T_MELT_UPPER_LIMIT = -1;
   java.lang.String t_melt;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getT_melt() {
      return t_melt;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setT_melt(java.lang.String t_melt) throws wt.util.WTPropertyVetoException {
      t_meltValidate(t_melt);
      this.t_melt = t_melt;
   }
   void t_meltValidate(java.lang.String t_melt) throws wt.util.WTPropertyVetoException {
      if (T_MELT_UPPER_LIMIT < 1) {
         try { T_MELT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("t_melt").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_MELT_UPPER_LIMIT = 200; }
      }
      if (t_melt != null && !wt.fc.PersistenceHelper.checkStoredLength(t_melt.toString(), T_MELT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "t_melt"), java.lang.String.valueOf(java.lang.Math.min(T_MELT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "t_melt", this.t_melt, t_melt));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String T_SOFT = "t_soft";
   static int T_SOFT_UPPER_LIMIT = -1;
   java.lang.String t_soft;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getT_soft() {
      return t_soft;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setT_soft(java.lang.String t_soft) throws wt.util.WTPropertyVetoException {
      t_softValidate(t_soft);
      this.t_soft = t_soft;
   }
   void t_softValidate(java.lang.String t_soft) throws wt.util.WTPropertyVetoException {
      if (T_SOFT_UPPER_LIMIT < 1) {
         try { T_SOFT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("t_soft").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_SOFT_UPPER_LIMIT = 200; }
      }
      if (t_soft != null && !wt.fc.PersistenceHelper.checkStoredLength(t_soft.toString(), T_SOFT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "t_soft"), java.lang.String.valueOf(java.lang.Math.min(T_SOFT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "t_soft", this.t_soft, t_soft));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String T_CONDUCTIVITY = "t_conductivity";
   static int T_CONDUCTIVITY_UPPER_LIMIT = -1;
   java.lang.String t_conductivity;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getT_conductivity() {
      return t_conductivity;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setT_conductivity(java.lang.String t_conductivity) throws wt.util.WTPropertyVetoException {
      t_conductivityValidate(t_conductivity);
      this.t_conductivity = t_conductivity;
   }
   void t_conductivityValidate(java.lang.String t_conductivity) throws wt.util.WTPropertyVetoException {
      if (T_CONDUCTIVITY_UPPER_LIMIT < 1) {
         try { T_CONDUCTIVITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("t_conductivity").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_CONDUCTIVITY_UPPER_LIMIT = 200; }
      }
      if (t_conductivity != null && !wt.fc.PersistenceHelper.checkStoredLength(t_conductivity.toString(), T_CONDUCTIVITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "t_conductivity"), java.lang.String.valueOf(java.lang.Math.min(T_CONDUCTIVITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "t_conductivity", this.t_conductivity, t_conductivity));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String R_Y = "r_y";
   static int R_Y_UPPER_LIMIT = -1;
   java.lang.String r_y;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getR_y() {
      return r_y;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setR_y(java.lang.String r_y) throws wt.util.WTPropertyVetoException {
      r_yValidate(r_y);
      this.r_y = r_y;
   }
   void r_yValidate(java.lang.String r_y) throws wt.util.WTPropertyVetoException {
      if (R_Y_UPPER_LIMIT < 1) {
         try { R_Y_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("r_y").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_Y_UPPER_LIMIT = 200; }
      }
      if (r_y != null && !wt.fc.PersistenceHelper.checkStoredLength(r_y.toString(), R_Y_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "r_y"), java.lang.String.valueOf(java.lang.Math.min(R_Y_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "r_y", this.r_y, r_y));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String R_M = "r_m";
   static int R_M_UPPER_LIMIT = -1;
   java.lang.String r_m;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getR_m() {
      return r_m;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setR_m(java.lang.String r_m) throws wt.util.WTPropertyVetoException {
      r_mValidate(r_m);
      this.r_m = r_m;
   }
   void r_mValidate(java.lang.String r_m) throws wt.util.WTPropertyVetoException {
      if (R_M_UPPER_LIMIT < 1) {
         try { R_M_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("r_m").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_M_UPPER_LIMIT = 200; }
      }
      if (r_m != null && !wt.fc.PersistenceHelper.checkStoredLength(r_m.toString(), R_M_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "r_m"), java.lang.String.valueOf(java.lang.Math.min(R_M_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "r_m", this.r_m, r_m));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String R_D = "r_d";
   static int R_D_UPPER_LIMIT = -1;
   java.lang.String r_d;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getR_d() {
      return r_d;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setR_d(java.lang.String r_d) throws wt.util.WTPropertyVetoException {
      r_dValidate(r_d);
      this.r_d = r_d;
   }
   void r_dValidate(java.lang.String r_d) throws wt.util.WTPropertyVetoException {
      if (R_D_UPPER_LIMIT < 1) {
         try { R_D_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("r_d").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { R_D_UPPER_LIMIT = 200; }
      }
      if (r_d != null && !wt.fc.PersistenceHelper.checkStoredLength(r_d.toString(), R_D_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "r_d"), java.lang.String.valueOf(java.lang.Math.min(R_D_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "r_d", this.r_d, r_d));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String MELT_POINT = "melt_point";
   static int MELT_POINT_UPPER_LIMIT = -1;
   java.lang.String melt_point;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getMelt_point() {
      return melt_point;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setMelt_point(java.lang.String melt_point) throws wt.util.WTPropertyVetoException {
      melt_pointValidate(melt_point);
      this.melt_point = melt_point;
   }
   void melt_pointValidate(java.lang.String melt_point) throws wt.util.WTPropertyVetoException {
      if (MELT_POINT_UPPER_LIMIT < 1) {
         try { MELT_POINT_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("melt_point").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MELT_POINT_UPPER_LIMIT = 200; }
      }
      if (melt_point != null && !wt.fc.PersistenceHelper.checkStoredLength(melt_point.toString(), MELT_POINT_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "melt_point"), java.lang.String.valueOf(java.lang.Math.min(MELT_POINT_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "melt_point", this.melt_point, melt_point));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String M_INDEX = "m_index";
   static int M_INDEX_UPPER_LIMIT = -1;
   java.lang.String m_index;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getM_index() {
      return m_index;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setM_index(java.lang.String m_index) throws wt.util.WTPropertyVetoException {
      m_indexValidate(m_index);
      this.m_index = m_index;
   }
   void m_indexValidate(java.lang.String m_index) throws wt.util.WTPropertyVetoException {
      if (M_INDEX_UPPER_LIMIT < 1) {
         try { M_INDEX_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("m_index").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { M_INDEX_UPPER_LIMIT = 200; }
      }
      if (m_index != null && !wt.fc.PersistenceHelper.checkStoredLength(m_index.toString(), M_INDEX_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "m_index"), java.lang.String.valueOf(java.lang.Math.min(M_INDEX_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "m_index", this.m_index, m_index));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String M_SHRINKAGE = "m_shrinkage";
   static int M_SHRINKAGE_UPPER_LIMIT = -1;
   java.lang.String m_shrinkage;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getM_shrinkage() {
      return m_shrinkage;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setM_shrinkage(java.lang.String m_shrinkage) throws wt.util.WTPropertyVetoException {
      m_shrinkageValidate(m_shrinkage);
      this.m_shrinkage = m_shrinkage;
   }
   void m_shrinkageValidate(java.lang.String m_shrinkage) throws wt.util.WTPropertyVetoException {
      if (M_SHRINKAGE_UPPER_LIMIT < 1) {
         try { M_SHRINKAGE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("m_shrinkage").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { M_SHRINKAGE_UPPER_LIMIT = 200; }
      }
      if (m_shrinkage != null && !wt.fc.PersistenceHelper.checkStoredLength(m_shrinkage.toString(), M_SHRINKAGE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "m_shrinkage"), java.lang.String.valueOf(java.lang.Math.min(M_SHRINKAGE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "m_shrinkage", this.m_shrinkage, m_shrinkage));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String ABSORB = "absorb";
   static int ABSORB_UPPER_LIMIT = -1;
   java.lang.String absorb;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getAbsorb() {
      return absorb;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setAbsorb(java.lang.String absorb) throws wt.util.WTPropertyVetoException {
      absorbValidate(absorb);
      this.absorb = absorb;
   }
   void absorbValidate(java.lang.String absorb) throws wt.util.WTPropertyVetoException {
      if (ABSORB_UPPER_LIMIT < 1) {
         try { ABSORB_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("absorb").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ABSORB_UPPER_LIMIT = 200; }
      }
      if (absorb != null && !wt.fc.PersistenceHelper.checkStoredLength(absorb.toString(), ABSORB_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "absorb"), java.lang.String.valueOf(java.lang.Math.min(ABSORB_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "absorb", this.absorb, absorb));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String T_STRENGTH = "t_strength";
   static int T_STRENGTH_UPPER_LIMIT = -1;
   java.lang.String t_strength;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getT_strength() {
      return t_strength;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setT_strength(java.lang.String t_strength) throws wt.util.WTPropertyVetoException {
      t_strengthValidate(t_strength);
      this.t_strength = t_strength;
   }
   void t_strengthValidate(java.lang.String t_strength) throws wt.util.WTPropertyVetoException {
      if (T_STRENGTH_UPPER_LIMIT < 1) {
         try { T_STRENGTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("t_strength").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { T_STRENGTH_UPPER_LIMIT = 200; }
      }
      if (t_strength != null && !wt.fc.PersistenceHelper.checkStoredLength(t_strength.toString(), T_STRENGTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "t_strength"), java.lang.String.valueOf(java.lang.Math.min(T_STRENGTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "t_strength", this.t_strength, t_strength));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String F_STRINGTH = "f_stringth";
   static int F_STRINGTH_UPPER_LIMIT = -1;
   java.lang.String f_stringth;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getF_stringth() {
      return f_stringth;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setF_stringth(java.lang.String f_stringth) throws wt.util.WTPropertyVetoException {
      f_stringthValidate(f_stringth);
      this.f_stringth = f_stringth;
   }
   void f_stringthValidate(java.lang.String f_stringth) throws wt.util.WTPropertyVetoException {
      if (F_STRINGTH_UPPER_LIMIT < 1) {
         try { F_STRINGTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("f_stringth").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { F_STRINGTH_UPPER_LIMIT = 200; }
      }
      if (f_stringth != null && !wt.fc.PersistenceHelper.checkStoredLength(f_stringth.toString(), F_STRINGTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "f_stringth"), java.lang.String.valueOf(java.lang.Math.min(F_STRINGTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "f_stringth", this.f_stringth, f_stringth));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String F_MODULUS = "f_modulus";
   static int F_MODULUS_UPPER_LIMIT = -1;
   java.lang.String f_modulus;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getF_modulus() {
      return f_modulus;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setF_modulus(java.lang.String f_modulus) throws wt.util.WTPropertyVetoException {
      f_modulusValidate(f_modulus);
      this.f_modulus = f_modulus;
   }
   void f_modulusValidate(java.lang.String f_modulus) throws wt.util.WTPropertyVetoException {
      if (F_MODULUS_UPPER_LIMIT < 1) {
         try { F_MODULUS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("f_modulus").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { F_MODULUS_UPPER_LIMIT = 200; }
      }
      if (f_modulus != null && !wt.fc.PersistenceHelper.checkStoredLength(f_modulus.toString(), F_MODULUS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "f_modulus"), java.lang.String.valueOf(java.lang.Math.min(F_MODULUS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "f_modulus", this.f_modulus, f_modulus));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String I_STRENGTH = "i_strength";
   static int I_STRENGTH_UPPER_LIMIT = -1;
   java.lang.String i_strength;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getI_strength() {
      return i_strength;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setI_strength(java.lang.String i_strength) throws wt.util.WTPropertyVetoException {
      i_strengthValidate(i_strength);
      this.i_strength = i_strength;
   }
   void i_strengthValidate(java.lang.String i_strength) throws wt.util.WTPropertyVetoException {
      if (I_STRENGTH_UPPER_LIMIT < 1) {
         try { I_STRENGTH_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("i_strength").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { I_STRENGTH_UPPER_LIMIT = 200; }
      }
      if (i_strength != null && !wt.fc.PersistenceHelper.checkStoredLength(i_strength.toString(), I_STRENGTH_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "i_strength"), java.lang.String.valueOf(java.lang.Math.min(I_STRENGTH_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "i_strength", this.i_strength, i_strength));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String H_DIS_TEMP_18 = "h_dis_temp_18";
   static int H_DIS_TEMP_18_UPPER_LIMIT = -1;
   java.lang.String h_dis_temp_18;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getH_dis_temp_18() {
      return h_dis_temp_18;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setH_dis_temp_18(java.lang.String h_dis_temp_18) throws wt.util.WTPropertyVetoException {
      h_dis_temp_18Validate(h_dis_temp_18);
      this.h_dis_temp_18 = h_dis_temp_18;
   }
   void h_dis_temp_18Validate(java.lang.String h_dis_temp_18) throws wt.util.WTPropertyVetoException {
      if (H_DIS_TEMP_18_UPPER_LIMIT < 1) {
         try { H_DIS_TEMP_18_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("h_dis_temp_18").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { H_DIS_TEMP_18_UPPER_LIMIT = 200; }
      }
      if (h_dis_temp_18 != null && !wt.fc.PersistenceHelper.checkStoredLength(h_dis_temp_18.toString(), H_DIS_TEMP_18_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "h_dis_temp_18"), java.lang.String.valueOf(java.lang.Math.min(H_DIS_TEMP_18_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "h_dis_temp_18", this.h_dis_temp_18, h_dis_temp_18));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String H_DIS_TEMP_4 = "h_dis_temp_4";
   static int H_DIS_TEMP_4_UPPER_LIMIT = -1;
   java.lang.String h_dis_temp_4;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getH_dis_temp_4() {
      return h_dis_temp_4;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setH_dis_temp_4(java.lang.String h_dis_temp_4) throws wt.util.WTPropertyVetoException {
      h_dis_temp_4Validate(h_dis_temp_4);
      this.h_dis_temp_4 = h_dis_temp_4;
   }
   void h_dis_temp_4Validate(java.lang.String h_dis_temp_4) throws wt.util.WTPropertyVetoException {
      if (H_DIS_TEMP_4_UPPER_LIMIT < 1) {
         try { H_DIS_TEMP_4_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("h_dis_temp_4").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { H_DIS_TEMP_4_UPPER_LIMIT = 200; }
      }
      if (h_dis_temp_4 != null && !wt.fc.PersistenceHelper.checkStoredLength(h_dis_temp_4.toString(), H_DIS_TEMP_4_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "h_dis_temp_4"), java.lang.String.valueOf(java.lang.Math.min(H_DIS_TEMP_4_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "h_dis_temp_4", this.h_dis_temp_4, h_dis_temp_4));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String FLAMMABILITY = "flammability";
   static int FLAMMABILITY_UPPER_LIMIT = -1;
   java.lang.String flammability;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getFlammability() {
      return flammability;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setFlammability(java.lang.String flammability) throws wt.util.WTPropertyVetoException {
      flammabilityValidate(flammability);
      this.flammability = flammability;
   }
   void flammabilityValidate(java.lang.String flammability) throws wt.util.WTPropertyVetoException {
      if (FLAMMABILITY_UPPER_LIMIT < 1) {
         try { FLAMMABILITY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("flammability").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { FLAMMABILITY_UPPER_LIMIT = 200; }
      }
      if (flammability != null && !wt.fc.PersistenceHelper.checkStoredLength(flammability.toString(), FLAMMABILITY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "flammability"), java.lang.String.valueOf(java.lang.Math.min(FLAMMABILITY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "flammability", this.flammability, flammability));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String S_FLOW = "s_flow";
   static int S_FLOW_UPPER_LIMIT = -1;
   java.lang.String s_flow;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getS_flow() {
      return s_flow;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setS_flow(java.lang.String s_flow) throws wt.util.WTPropertyVetoException {
      s_flowValidate(s_flow);
      this.s_flow = s_flow;
   }
   void s_flowValidate(java.lang.String s_flow) throws wt.util.WTPropertyVetoException {
      if (S_FLOW_UPPER_LIMIT < 1) {
         try { S_FLOW_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("s_flow").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { S_FLOW_UPPER_LIMIT = 200; }
      }
      if (s_flow != null && !wt.fc.PersistenceHelper.checkStoredLength(s_flow.toString(), S_FLOW_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "s_flow"), java.lang.String.valueOf(java.lang.Math.min(S_FLOW_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "s_flow", this.s_flow, s_flow));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String MEMO = "memo";
   static int MEMO_UPPER_LIMIT = -1;
   java.lang.String memo;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getMemo() {
      return memo;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setMemo(java.lang.String memo) throws wt.util.WTPropertyVetoException {
      memoValidate(memo);
      this.memo = memo;
   }
   void memoValidate(java.lang.String memo) throws wt.util.WTPropertyVetoException {
      if (MEMO_UPPER_LIMIT < 1) {
         try { MEMO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("memo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MEMO_UPPER_LIMIT = 200; }
      }
      if (memo != null && !wt.fc.PersistenceHelper.checkStoredLength(memo.toString(), MEMO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "memo"), java.lang.String.valueOf(java.lang.Math.min(MEMO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "memo", this.memo, memo));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String PRICE = "price";
   static int PRICE_UPPER_LIMIT = -1;
   java.lang.String price;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public java.lang.String getPrice() {
      return price;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setPrice(java.lang.String price) throws wt.util.WTPropertyVetoException {
      priceValidate(price);
      this.price = price;
   }
   void priceValidate(java.lang.String price) throws wt.util.WTPropertyVetoException {
      if (PRICE_UPPER_LIMIT < 1) {
         try { PRICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("price").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRICE_UPPER_LIMIT = 200; }
      }
      if (price != null && !wt.fc.PersistenceHelper.checkStoredLength(price.toString(), PRICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "price"), java.lang.String.valueOf(java.lang.Math.min(PRICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "price", this.price, price));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String MATERIAL_MAKER = "materialMaker";
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String MATERIAL_MAKER_REFERENCE = "materialMakerReference";
   wt.fc.ObjectReference materialMakerReference;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public e3ps.common.code.NumberCode getMaterialMaker() {
      return (materialMakerReference != null) ? (e3ps.common.code.NumberCode) materialMakerReference.getObject() : null;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public wt.fc.ObjectReference getMaterialMakerReference() {
      return materialMakerReference;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setMaterialMaker(e3ps.common.code.NumberCode the_materialMaker) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMaterialMakerReference(the_materialMaker == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_materialMaker));
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setMaterialMakerReference(wt.fc.ObjectReference the_materialMakerReference) throws wt.util.WTPropertyVetoException {
      materialMakerReferenceValidate(the_materialMakerReference);
      materialMakerReference = (wt.fc.ObjectReference) the_materialMakerReference;
   }
   void materialMakerReferenceValidate(wt.fc.ObjectReference the_materialMakerReference) throws wt.util.WTPropertyVetoException {
      if (the_materialMakerReference == null || the_materialMakerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialMakerReference") },
               new java.beans.PropertyChangeEvent(this, "materialMakerReference", this.materialMakerReference, materialMakerReference));
      if (the_materialMakerReference != null && the_materialMakerReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_materialMakerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialMakerReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "materialMakerReference", this.materialMakerReference, materialMakerReference));
   }

   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String MATERIAL_TYPE = "materialType";
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public static final java.lang.String MATERIAL_TYPE_REFERENCE = "materialTypeReference";
   wt.fc.ObjectReference materialTypeReference;
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public e3ps.common.code.NumberCode getMaterialType() {
      return (materialTypeReference != null) ? (e3ps.common.code.NumberCode) materialTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public wt.fc.ObjectReference getMaterialTypeReference() {
      return materialTypeReference;
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setMaterialType(e3ps.common.code.NumberCode the_materialType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMaterialTypeReference(the_materialType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_materialType));
   }
   /**
    * @see e3ps.project.material.MoldMaterial
    */
   public void setMaterialTypeReference(wt.fc.ObjectReference the_materialTypeReference) throws wt.util.WTPropertyVetoException {
      materialTypeReferenceValidate(the_materialTypeReference);
      materialTypeReference = (wt.fc.ObjectReference) the_materialTypeReference;
   }
   void materialTypeReferenceValidate(wt.fc.ObjectReference the_materialTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_materialTypeReference == null || the_materialTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialTypeReference") },
               new java.beans.PropertyChangeEvent(this, "materialTypeReference", this.materialTypeReference, materialTypeReference));
      if (the_materialTypeReference != null && the_materialTypeReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_materialTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "materialTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "materialTypeReference", this.materialTypeReference, materialTypeReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 3091342184200906316L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( absorb );
      output.writeObject( e_conductivity );
      output.writeObject( elong );
      output.writeObject( f_modulus );
      output.writeObject( f_stringth );
      output.writeObject( flammability );
      output.writeObject( formability );
      output.writeObject( grade );
      output.writeObject( h_dis_temp_18 );
      output.writeObject( h_dis_temp_4 );
      output.writeObject( hardness );
      output.writeObject( i_strength );
      output.writeObject( m_elasticity );
      output.writeObject( m_index );
      output.writeObject( m_shrinkage );
      output.writeObject( material );
      output.writeObject( materialMakerReference );
      output.writeObject( materialTypeReference );
      output.writeObject( melt_point );
      output.writeObject( memo );
      output.writeObject( price );
      output.writeObject( r_d );
      output.writeObject( r_m );
      output.writeObject( r_y );
      output.writeObject( residual_stress );
      output.writeObject( s_flow );
      output.writeObject( s_gravity );
      output.writeObject( spec_no );
      output.writeObject( strength );
      output.writeObject( t_conductivity );
      output.writeObject( t_melt );
      output.writeObject( t_soft );
      output.writeObject( t_strength );
      output.writeObject( temper );
      output.writeObject( thePersistInfo );
   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.material.MoldMaterial) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "absorb", absorb );
      output.setString( "e_conductivity", e_conductivity );
      output.setString( "elong", elong );
      output.setString( "f_modulus", f_modulus );
      output.setString( "f_stringth", f_stringth );
      output.setString( "flammability", flammability );
      output.setString( "formability", formability );
      output.setString( "grade", grade );
      output.setString( "h_dis_temp_18", h_dis_temp_18 );
      output.setString( "h_dis_temp_4", h_dis_temp_4 );
      output.setString( "hardness", hardness );
      output.setString( "i_strength", i_strength );
      output.setString( "m_elasticity", m_elasticity );
      output.setString( "m_index", m_index );
      output.setString( "m_shrinkage", m_shrinkage );
      output.setString( "material", material );
      output.writeObject( "materialMakerReference", materialMakerReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "materialTypeReference", materialTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "melt_point", melt_point );
      output.setString( "memo", memo );
      output.setString( "price", price );
      output.setString( "r_d", r_d );
      output.setString( "r_m", r_m );
      output.setString( "r_y", r_y );
      output.setString( "residual_stress", residual_stress );
      output.setString( "s_flow", s_flow );
      output.setString( "s_gravity", s_gravity );
      output.setString( "spec_no", spec_no );
      output.setString( "strength", strength );
      output.setString( "t_conductivity", t_conductivity );
      output.setString( "t_melt", t_melt );
      output.setString( "t_soft", t_soft );
      output.setString( "t_strength", t_strength );
      output.setString( "temper", temper );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      absorb = input.getString( "absorb" );
      e_conductivity = input.getString( "e_conductivity" );
      elong = input.getString( "elong" );
      f_modulus = input.getString( "f_modulus" );
      f_stringth = input.getString( "f_stringth" );
      flammability = input.getString( "flammability" );
      formability = input.getString( "formability" );
      grade = input.getString( "grade" );
      h_dis_temp_18 = input.getString( "h_dis_temp_18" );
      h_dis_temp_4 = input.getString( "h_dis_temp_4" );
      hardness = input.getString( "hardness" );
      i_strength = input.getString( "i_strength" );
      m_elasticity = input.getString( "m_elasticity" );
      m_index = input.getString( "m_index" );
      m_shrinkage = input.getString( "m_shrinkage" );
      material = input.getString( "material" );
      materialMakerReference = (wt.fc.ObjectReference) input.readObject( "materialMakerReference", materialMakerReference, wt.fc.ObjectReference.class, true );
      materialTypeReference = (wt.fc.ObjectReference) input.readObject( "materialTypeReference", materialTypeReference, wt.fc.ObjectReference.class, true );
      melt_point = input.getString( "melt_point" );
      memo = input.getString( "memo" );
      price = input.getString( "price" );
      r_d = input.getString( "r_d" );
      r_m = input.getString( "r_m" );
      r_y = input.getString( "r_y" );
      residual_stress = input.getString( "residual_stress" );
      s_flow = input.getString( "s_flow" );
      s_gravity = input.getString( "s_gravity" );
      spec_no = input.getString( "spec_no" );
      strength = input.getString( "strength" );
      t_conductivity = input.getString( "t_conductivity" );
      t_melt = input.getString( "t_melt" );
      t_soft = input.getString( "t_soft" );
      t_strength = input.getString( "t_strength" );
      temper = input.getString( "temper" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
   }

   boolean readVersion3091342184200906316L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      absorb = (java.lang.String) input.readObject();
      e_conductivity = (java.lang.String) input.readObject();
      elong = (java.lang.String) input.readObject();
      f_modulus = (java.lang.String) input.readObject();
      f_stringth = (java.lang.String) input.readObject();
      flammability = (java.lang.String) input.readObject();
      formability = (java.lang.String) input.readObject();
      grade = (java.lang.String) input.readObject();
      h_dis_temp_18 = (java.lang.String) input.readObject();
      h_dis_temp_4 = (java.lang.String) input.readObject();
      hardness = (java.lang.String) input.readObject();
      i_strength = (java.lang.String) input.readObject();
      m_elasticity = (java.lang.String) input.readObject();
      m_index = (java.lang.String) input.readObject();
      m_shrinkage = (java.lang.String) input.readObject();
      material = (java.lang.String) input.readObject();
      materialMakerReference = (wt.fc.ObjectReference) input.readObject();
      materialTypeReference = (wt.fc.ObjectReference) input.readObject();
      melt_point = (java.lang.String) input.readObject();
      memo = (java.lang.String) input.readObject();
      price = (java.lang.String) input.readObject();
      r_d = (java.lang.String) input.readObject();
      r_m = (java.lang.String) input.readObject();
      r_y = (java.lang.String) input.readObject();
      residual_stress = (java.lang.String) input.readObject();
      s_flow = (java.lang.String) input.readObject();
      s_gravity = (java.lang.String) input.readObject();
      spec_no = (java.lang.String) input.readObject();
      strength = (java.lang.String) input.readObject();
      t_conductivity = (java.lang.String) input.readObject();
      t_melt = (java.lang.String) input.readObject();
      t_soft = (java.lang.String) input.readObject();
      t_strength = (java.lang.String) input.readObject();
      temper = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      return true;
   }

   protected boolean readVersion( MoldMaterial thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion3091342184200906316L( input, readSerialVersionUID, superDone );
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
