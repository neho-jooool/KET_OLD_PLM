package e3ps.lesson;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _LessonLearn implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.lesson.lessonResource";
   static final java.lang.String CLASSNAME = LessonLearn.class.getName();

   /**
    * 문제점
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PROBLEM = "problem";
   static int PROBLEM_UPPER_LIMIT = -1;
   java.lang.String problem;
   /**
    * 문제점
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getProblem() {
      return problem;
   }
   /**
    * 문제점
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setProblem(java.lang.String problem) throws wt.util.WTPropertyVetoException {
      problemValidate(problem);
      this.problem = problem;
   }
   void problemValidate(java.lang.String problem) throws wt.util.WTPropertyVetoException {
      if (PROBLEM_UPPER_LIMIT < 1) {
         try { PROBLEM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("problem").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROBLEM_UPPER_LIMIT = 4000; }
      }
      if (problem != null && !wt.fc.PersistenceHelper.checkStoredLength(problem.toString(), PROBLEM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "problem"), java.lang.String.valueOf(java.lang.Math.min(PROBLEM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "problem", this.problem, problem));
   }

   /**
    * 원인
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String CAUSE = "cause";
   static int CAUSE_UPPER_LIMIT = -1;
   java.lang.String cause;
   /**
    * 원인
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getCause() {
      return cause;
   }
   /**
    * 원인
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setCause(java.lang.String cause) throws wt.util.WTPropertyVetoException {
      causeValidate(cause);
      this.cause = cause;
   }
   void causeValidate(java.lang.String cause) throws wt.util.WTPropertyVetoException {
      if (CAUSE_UPPER_LIMIT < 1) {
         try { CAUSE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cause").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAUSE_UPPER_LIMIT = 4000; }
      }
      if (cause != null && !wt.fc.PersistenceHelper.checkStoredLength(cause.toString(), CAUSE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cause"), java.lang.String.valueOf(java.lang.Math.min(CAUSE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cause", this.cause, cause));
   }

   /**
    * 개선대책
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String IMPROVE = "improve";
   static int IMPROVE_UPPER_LIMIT = -1;
   java.lang.String improve;
   /**
    * 개선대책
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getImprove() {
      return improve;
   }
   /**
    * 개선대책
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setImprove(java.lang.String improve) throws wt.util.WTPropertyVetoException {
      improveValidate(improve);
      this.improve = improve;
   }
   void improveValidate(java.lang.String improve) throws wt.util.WTPropertyVetoException {
      if (IMPROVE_UPPER_LIMIT < 1) {
         try { IMPROVE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("improve").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IMPROVE_UPPER_LIMIT = 4000; }
      }
      if (improve != null && !wt.fc.PersistenceHelper.checkStoredLength(improve.toString(), IMPROVE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "improve"), java.lang.String.valueOf(java.lang.Math.min(IMPROVE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "improve", this.improve, improve));
   }

   /**
    * 입력일자
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String INSERT_DATE = "insert_date";
   java.sql.Timestamp insert_date;
   /**
    * 입력일자
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.sql.Timestamp getInsert_date() {
      return insert_date;
   }
   /**
    * 입력일자
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setInsert_date(java.sql.Timestamp insert_date) throws wt.util.WTPropertyVetoException {
      insert_dateValidate(insert_date);
      this.insert_date = insert_date;
   }
   void insert_dateValidate(java.sql.Timestamp insert_date) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 설비번호
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String EQUIP_NO = "equip_no";
   static int EQUIP_NO_UPPER_LIMIT = -1;
   java.lang.String equip_no;
   /**
    * 설비번호
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getEquip_no() {
      return equip_no;
   }
   /**
    * 설비번호
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setEquip_no(java.lang.String equip_no) throws wt.util.WTPropertyVetoException {
      equip_noValidate(equip_no);
      this.equip_no = equip_no;
   }
   void equip_noValidate(java.lang.String equip_no) throws wt.util.WTPropertyVetoException {
      if (EQUIP_NO_UPPER_LIMIT < 1) {
         try { EQUIP_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("equip_no").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EQUIP_NO_UPPER_LIMIT = 4000; }
      }
      if (equip_no != null && !wt.fc.PersistenceHelper.checkStoredLength(equip_no.toString(), EQUIP_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "equip_no"), java.lang.String.valueOf(java.lang.Math.min(EQUIP_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "equip_no", this.equip_no, equip_no));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PROJECT_OID = "projectOid";
   static int PROJECT_OID_UPPER_LIMIT = -1;
   java.lang.String projectOid;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getProjectOid() {
      return projectOid;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setProjectOid(java.lang.String projectOid) throws wt.util.WTPropertyVetoException {
      projectOidValidate(projectOid);
      this.projectOid = projectOid;
   }
   void projectOidValidate(java.lang.String projectOid) throws wt.util.WTPropertyVetoException {
      if (PROJECT_OID_UPPER_LIMIT < 1) {
         try { PROJECT_OID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("projectOid").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PROJECT_OID_UPPER_LIMIT = 200; }
      }
      if (projectOid != null && !wt.fc.PersistenceHelper.checkStoredLength(projectOid.toString(), PROJECT_OID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "projectOid"), java.lang.String.valueOf(java.lang.Math.min(PROJECT_OID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "projectOid", this.projectOid, projectOid));
   }

   /**
    * 발생일자
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String OCCUR_DATE = "occur_date";
   java.sql.Timestamp occur_date;
   /**
    * 발생일자
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.sql.Timestamp getOccur_date() {
      return occur_date;
   }
   /**
    * 발생일자
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setOccur_date(java.sql.Timestamp occur_date) throws wt.util.WTPropertyVetoException {
      occur_dateValidate(occur_date);
      this.occur_date = occur_date;
   }
   void occur_dateValidate(java.sql.Timestamp occur_date) throws wt.util.WTPropertyVetoException {
   }

   /**
    * dieNo
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String DIE_NO = "dieNo";
   static int DIE_NO_UPPER_LIMIT = -1;
   java.lang.String dieNo;
   /**
    * dieNo
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getDieNo() {
      return dieNo;
   }
   /**
    * dieNo
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setDieNo(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      dieNoValidate(dieNo);
      this.dieNo = dieNo;
   }
   void dieNoValidate(java.lang.String dieNo) throws wt.util.WTPropertyVetoException {
      if (DIE_NO_UPPER_LIMIT < 1) {
         try { DIE_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dieNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIE_NO_UPPER_LIMIT = 200; }
      }
      if (dieNo != null && !wt.fc.PersistenceHelper.checkStoredLength(dieNo.toString(), DIE_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dieNo"), java.lang.String.valueOf(java.lang.Math.min(DIE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dieNo", this.dieNo, dieNo));
   }

   /**
    * partNo
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PART_NO = "partNo";
   static int PART_NO_UPPER_LIMIT = -1;
   java.lang.String partNo;
   /**
    * partNo
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getPartNo() {
      return partNo;
   }
   /**
    * partNo
    *
    * @see e3ps.lesson.LessonLearn
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
    * 원인구분
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String CAUSE_GB = "cause_gb";
   static int CAUSE_GB_UPPER_LIMIT = -1;
   java.lang.String cause_gb;
   /**
    * 원인구분
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getCause_gb() {
      return cause_gb;
   }
   /**
    * 원인구분
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setCause_gb(java.lang.String cause_gb) throws wt.util.WTPropertyVetoException {
      cause_gbValidate(cause_gb);
      this.cause_gb = cause_gb;
   }
   void cause_gbValidate(java.lang.String cause_gb) throws wt.util.WTPropertyVetoException {
      if (CAUSE_GB_UPPER_LIMIT < 1) {
         try { CAUSE_GB_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cause_gb").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CAUSE_GB_UPPER_LIMIT = 200; }
      }
      if (cause_gb != null && !wt.fc.PersistenceHelper.checkStoredLength(cause_gb.toString(), CAUSE_GB_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cause_gb"), java.lang.String.valueOf(java.lang.Math.min(CAUSE_GB_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cause_gb", this.cause_gb, cause_gb));
   }

   /**
    * 개선대책구분
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String IMPROVE_GB = "improve_gb";
   static int IMPROVE_GB_UPPER_LIMIT = -1;
   java.lang.String improve_gb;
   /**
    * 개선대책구분
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getImprove_gb() {
      return improve_gb;
   }
   /**
    * 개선대책구분
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setImprove_gb(java.lang.String improve_gb) throws wt.util.WTPropertyVetoException {
      improve_gbValidate(improve_gb);
      this.improve_gb = improve_gb;
   }
   void improve_gbValidate(java.lang.String improve_gb) throws wt.util.WTPropertyVetoException {
      if (IMPROVE_GB_UPPER_LIMIT < 1) {
         try { IMPROVE_GB_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("improve_gb").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { IMPROVE_GB_UPPER_LIMIT = 200; }
      }
      if (improve_gb != null && !wt.fc.PersistenceHelper.checkStoredLength(improve_gb.toString(), IMPROVE_GB_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "improve_gb"), java.lang.String.valueOf(java.lang.Math.min(IMPROVE_GB_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "improve_gb", this.improve_gb, improve_gb));
   }

   /**
    * 설비명
    *
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String EQUIP_NM = "equip_nm";
   static int EQUIP_NM_UPPER_LIMIT = -1;
   java.lang.String equip_nm;
   /**
    * 설비명
    *
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getEquip_nm() {
      return equip_nm;
   }
   /**
    * 설비명
    *
    * @see e3ps.lesson.LessonLearn
    */
   public void setEquip_nm(java.lang.String equip_nm) throws wt.util.WTPropertyVetoException {
      equip_nmValidate(equip_nm);
      this.equip_nm = equip_nm;
   }
   void equip_nmValidate(java.lang.String equip_nm) throws wt.util.WTPropertyVetoException {
      if (EQUIP_NM_UPPER_LIMIT < 1) {
         try { EQUIP_NM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("equip_nm").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EQUIP_NM_UPPER_LIMIT = 4000; }
      }
      if (equip_nm != null && !wt.fc.PersistenceHelper.checkStoredLength(equip_nm.toString(), EQUIP_NM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "equip_nm"), java.lang.String.valueOf(java.lang.Math.min(EQUIP_NM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "equip_nm", this.equip_nm, equip_nm));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String DIE_NM = "die_nm";
   static int DIE_NM_UPPER_LIMIT = -1;
   java.lang.String die_nm;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getDie_nm() {
      return die_nm;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setDie_nm(java.lang.String die_nm) throws wt.util.WTPropertyVetoException {
      die_nmValidate(die_nm);
      this.die_nm = die_nm;
   }
   void die_nmValidate(java.lang.String die_nm) throws wt.util.WTPropertyVetoException {
      if (DIE_NM_UPPER_LIMIT < 1) {
         try { DIE_NM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("die_nm").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIE_NM_UPPER_LIMIT = 200; }
      }
      if (die_nm != null && !wt.fc.PersistenceHelper.checkStoredLength(die_nm.toString(), DIE_NM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "die_nm"), java.lang.String.valueOf(java.lang.Math.min(DIE_NM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "die_nm", this.die_nm, die_nm));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PART_NM = "part_nm";
   static int PART_NM_UPPER_LIMIT = -1;
   java.lang.String part_nm;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public java.lang.String getPart_nm() {
      return part_nm;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setPart_nm(java.lang.String part_nm) throws wt.util.WTPropertyVetoException {
      part_nmValidate(part_nm);
      this.part_nm = part_nm;
   }
   void part_nmValidate(java.lang.String part_nm) throws wt.util.WTPropertyVetoException {
      if (PART_NM_UPPER_LIMIT < 1) {
         try { PART_NM_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("part_nm").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PART_NM_UPPER_LIMIT = 200; }
      }
      if (part_nm != null && !wt.fc.PersistenceHelper.checkStoredLength(part_nm.toString(), PART_NM_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "part_nm"), java.lang.String.valueOf(java.lang.Math.min(PART_NM_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "part_nm", this.part_nm, part_nm));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String USER = "user";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see e3ps.lesson.LessonLearn
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
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String DEPARTMENT = "department";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String DEPARTMENT_REFERENCE = "departmentReference";
   wt.fc.ObjectReference departmentReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public e3ps.groupware.company.Department getDepartment() {
      return (departmentReference != null) ? (e3ps.groupware.company.Department) departmentReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getDepartmentReference() {
      return departmentReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setDepartment(e3ps.groupware.company.Department the_department) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDepartmentReference(the_department == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_department));
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setDepartmentReference(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      departmentReferenceValidate(the_departmentReference);
      departmentReference = (wt.fc.ObjectReference) the_departmentReference;
   }
   void departmentReferenceValidate(wt.fc.ObjectReference the_departmentReference) throws wt.util.WTPropertyVetoException {
      if (the_departmentReference != null && the_departmentReference.getReferencedClass() != null &&
            !e3ps.groupware.company.Department.class.isAssignableFrom(the_departmentReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "departmentReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "departmentReference", this.departmentReference, departmentReference));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PART = "part";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PART_REFERENCE = "partReference";
   wt.fc.ObjectReference partReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.part.WTPart getPart() {
      return (partReference != null) ? (wt.part.WTPart) partReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getPartReference() {
      return partReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setPart(wt.part.WTPart the_part) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setPartReference(the_part == null ? null : wt.fc.ObjectReference.newObjectReference((wt.part.WTPart) the_part));
   }
   /**
    * @see e3ps.lesson.LessonLearn
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
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String DIE = "die";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String DIE_REFERENCE = "dieReference";
   wt.fc.ObjectReference dieReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.part.WTPart getDie() {
      return (dieReference != null) ? (wt.part.WTPart) dieReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getDieReference() {
      return dieReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setDie(wt.part.WTPart the_die) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDieReference(the_die == null ? null : wt.fc.ObjectReference.newObjectReference((wt.part.WTPart) the_die));
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setDieReference(wt.fc.ObjectReference the_dieReference) throws wt.util.WTPropertyVetoException {
      dieReferenceValidate(the_dieReference);
      dieReference = (wt.fc.ObjectReference) the_dieReference;
   }
   void dieReferenceValidate(wt.fc.ObjectReference the_dieReference) throws wt.util.WTPropertyVetoException {
      if (the_dieReference != null && the_dieReference.getReferencedClass() != null &&
            !wt.part.WTPart.class.isAssignableFrom(the_dieReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dieReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "dieReference", this.dieReference, dieReference));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String FACTORY = "factory";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String FACTORY_REFERENCE = "factoryReference";
   wt.fc.ObjectReference factoryReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public e3ps.common.code.NumberCode getFactory() {
      return (factoryReference != null) ? (e3ps.common.code.NumberCode) factoryReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getFactoryReference() {
      return factoryReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setFactory(e3ps.common.code.NumberCode the_factory) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setFactoryReference(the_factory == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_factory));
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setFactoryReference(wt.fc.ObjectReference the_factoryReference) throws wt.util.WTPropertyVetoException {
      factoryReferenceValidate(the_factoryReference);
      factoryReference = (wt.fc.ObjectReference) the_factoryReference;
   }
   void factoryReferenceValidate(wt.fc.ObjectReference the_factoryReference) throws wt.util.WTPropertyVetoException {
      if (the_factoryReference != null && the_factoryReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_factoryReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "factoryReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "factoryReference", this.factoryReference, factoryReference));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String GUBUN = "gubun";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String GUBUN_REFERENCE = "gubunReference";
   wt.fc.ObjectReference gubunReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public e3ps.common.code.NumberCode getGubun() {
      return (gubunReference != null) ? (e3ps.common.code.NumberCode) gubunReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getGubunReference() {
      return gubunReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setGubun(e3ps.common.code.NumberCode the_gubun) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setGubunReference(the_gubun == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_gubun));
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setGubunReference(wt.fc.ObjectReference the_gubunReference) throws wt.util.WTPropertyVetoException {
      gubunReferenceValidate(the_gubunReference);
      gubunReference = (wt.fc.ObjectReference) the_gubunReference;
   }
   void gubunReferenceValidate(wt.fc.ObjectReference the_gubunReference) throws wt.util.WTPropertyVetoException {
      if (the_gubunReference != null && the_gubunReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_gubunReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "gubunReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "gubunReference", this.gubunReference, gubunReference));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PRODUCT_GUBUN = "product_gubun";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String PRODUCT_GUBUN_REFERENCE = "product_gubunReference";
   wt.fc.ObjectReference product_gubunReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public e3ps.common.code.NumberCode getProduct_gubun() {
      return (product_gubunReference != null) ? (e3ps.common.code.NumberCode) product_gubunReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getProduct_gubunReference() {
      return product_gubunReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setProduct_gubun(e3ps.common.code.NumberCode the_product_gubun) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setProduct_gubunReference(the_product_gubun == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_product_gubun));
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setProduct_gubunReference(wt.fc.ObjectReference the_product_gubunReference) throws wt.util.WTPropertyVetoException {
      product_gubunReferenceValidate(the_product_gubunReference);
      product_gubunReference = (wt.fc.ObjectReference) the_product_gubunReference;
   }
   void product_gubunReferenceValidate(wt.fc.ObjectReference the_product_gubunReference) throws wt.util.WTPropertyVetoException {
      if (the_product_gubunReference != null && the_product_gubunReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_product_gubunReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "product_gubunReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "product_gubunReference", this.product_gubunReference, product_gubunReference));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String CAUSE_GUBUN = "cause_gubun";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String CAUSE_GUBUN_REFERENCE = "cause_gubunReference";
   wt.fc.ObjectReference cause_gubunReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public e3ps.common.code.NumberCode getCause_gubun() {
      return (cause_gubunReference != null) ? (e3ps.common.code.NumberCode) cause_gubunReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getCause_gubunReference() {
      return cause_gubunReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setCause_gubun(e3ps.common.code.NumberCode the_cause_gubun) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setCause_gubunReference(the_cause_gubun == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_cause_gubun));
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setCause_gubunReference(wt.fc.ObjectReference the_cause_gubunReference) throws wt.util.WTPropertyVetoException {
      cause_gubunReferenceValidate(the_cause_gubunReference);
      cause_gubunReference = (wt.fc.ObjectReference) the_cause_gubunReference;
   }
   void cause_gubunReferenceValidate(wt.fc.ObjectReference the_cause_gubunReference) throws wt.util.WTPropertyVetoException {
      if (the_cause_gubunReference != null && the_cause_gubunReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_cause_gubunReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cause_gubunReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "cause_gubunReference", this.cause_gubunReference, cause_gubunReference));
   }

   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String IMPROVE_GUBUN = "improve_gubun";
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public static final java.lang.String IMPROVE_GUBUN_REFERENCE = "improve_gubunReference";
   wt.fc.ObjectReference improve_gubunReference;
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public e3ps.common.code.NumberCode getImprove_gubun() {
      return (improve_gubunReference != null) ? (e3ps.common.code.NumberCode) improve_gubunReference.getObject() : null;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public wt.fc.ObjectReference getImprove_gubunReference() {
      return improve_gubunReference;
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setImprove_gubun(e3ps.common.code.NumberCode the_improve_gubun) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setImprove_gubunReference(the_improve_gubun == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.common.code.NumberCode) the_improve_gubun));
   }
   /**
    * @see e3ps.lesson.LessonLearn
    */
   public void setImprove_gubunReference(wt.fc.ObjectReference the_improve_gubunReference) throws wt.util.WTPropertyVetoException {
      improve_gubunReferenceValidate(the_improve_gubunReference);
      improve_gubunReference = (wt.fc.ObjectReference) the_improve_gubunReference;
   }
   void improve_gubunReferenceValidate(wt.fc.ObjectReference the_improve_gubunReference) throws wt.util.WTPropertyVetoException {
      if (the_improve_gubunReference != null && the_improve_gubunReference.getReferencedClass() != null &&
            !e3ps.common.code.NumberCode.class.isAssignableFrom(the_improve_gubunReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "improve_gubunReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "improve_gubunReference", this.improve_gubunReference, improve_gubunReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = 7433127048463050232L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( cause );
      output.writeObject( cause_gb );
      output.writeObject( cause_gubunReference );
      output.writeObject( departmentReference );
      output.writeObject( dieNo );
      output.writeObject( dieReference );
      output.writeObject( die_nm );
      output.writeObject( equip_nm );
      output.writeObject( equip_no );
      output.writeObject( factoryReference );
      output.writeObject( gubunReference );
      output.writeObject( improve );
      output.writeObject( improve_gb );
      output.writeObject( improve_gubunReference );
      output.writeObject( insert_date );
      output.writeObject( occur_date );
      output.writeObject( partNo );
      output.writeObject( partReference );
      output.writeObject( part_nm );
      output.writeObject( problem );
      output.writeObject( product_gubunReference );
      output.writeObject( projectOid );
      output.writeObject( thePersistInfo );
      output.writeObject( userReference );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.lesson.LessonLearn) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "cause", cause );
      output.setString( "cause_gb", cause_gb );
      output.writeObject( "cause_gubunReference", cause_gubunReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      output.setString( "dieNo", dieNo );
      output.writeObject( "dieReference", dieReference, wt.fc.ObjectReference.class, true );
      output.setString( "die_nm", die_nm );
      output.setString( "equip_nm", equip_nm );
      output.setString( "equip_no", equip_no );
      output.writeObject( "factoryReference", factoryReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "gubunReference", gubunReference, wt.fc.ObjectReference.class, true );
      output.setString( "improve", improve );
      output.setString( "improve_gb", improve_gb );
      output.writeObject( "improve_gubunReference", improve_gubunReference, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "insert_date", insert_date );
      output.setTimestamp( "occur_date", occur_date );
      output.setString( "partNo", partNo );
      output.writeObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      output.setString( "part_nm", part_nm );
      output.setString( "problem", problem );
      output.writeObject( "product_gubunReference", product_gubunReference, wt.fc.ObjectReference.class, true );
      output.setString( "projectOid", projectOid );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      cause = input.getString( "cause" );
      cause_gb = input.getString( "cause_gb" );
      cause_gubunReference = (wt.fc.ObjectReference) input.readObject( "cause_gubunReference", cause_gubunReference, wt.fc.ObjectReference.class, true );
      departmentReference = (wt.fc.ObjectReference) input.readObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      dieNo = input.getString( "dieNo" );
      dieReference = (wt.fc.ObjectReference) input.readObject( "dieReference", dieReference, wt.fc.ObjectReference.class, true );
      die_nm = input.getString( "die_nm" );
      equip_nm = input.getString( "equip_nm" );
      equip_no = input.getString( "equip_no" );
      factoryReference = (wt.fc.ObjectReference) input.readObject( "factoryReference", factoryReference, wt.fc.ObjectReference.class, true );
      gubunReference = (wt.fc.ObjectReference) input.readObject( "gubunReference", gubunReference, wt.fc.ObjectReference.class, true );
      improve = input.getString( "improve" );
      improve_gb = input.getString( "improve_gb" );
      improve_gubunReference = (wt.fc.ObjectReference) input.readObject( "improve_gubunReference", improve_gubunReference, wt.fc.ObjectReference.class, true );
      insert_date = input.getTimestamp( "insert_date" );
      occur_date = input.getTimestamp( "occur_date" );
      partNo = input.getString( "partNo" );
      partReference = (wt.fc.ObjectReference) input.readObject( "partReference", partReference, wt.fc.ObjectReference.class, true );
      part_nm = input.getString( "part_nm" );
      problem = input.getString( "problem" );
      product_gubunReference = (wt.fc.ObjectReference) input.readObject( "product_gubunReference", product_gubunReference, wt.fc.ObjectReference.class, true );
      projectOid = input.getString( "projectOid" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
   }

   boolean readVersion7433127048463050232L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      cause = (java.lang.String) input.readObject();
      cause_gb = (java.lang.String) input.readObject();
      cause_gubunReference = (wt.fc.ObjectReference) input.readObject();
      departmentReference = (wt.fc.ObjectReference) input.readObject();
      dieNo = (java.lang.String) input.readObject();
      dieReference = (wt.fc.ObjectReference) input.readObject();
      die_nm = (java.lang.String) input.readObject();
      equip_nm = (java.lang.String) input.readObject();
      equip_no = (java.lang.String) input.readObject();
      factoryReference = (wt.fc.ObjectReference) input.readObject();
      gubunReference = (wt.fc.ObjectReference) input.readObject();
      improve = (java.lang.String) input.readObject();
      improve_gb = (java.lang.String) input.readObject();
      improve_gubunReference = (wt.fc.ObjectReference) input.readObject();
      insert_date = (java.sql.Timestamp) input.readObject();
      occur_date = (java.sql.Timestamp) input.readObject();
      partNo = (java.lang.String) input.readObject();
      partReference = (wt.fc.ObjectReference) input.readObject();
      part_nm = (java.lang.String) input.readObject();
      problem = (java.lang.String) input.readObject();
      product_gubunReference = (wt.fc.ObjectReference) input.readObject();
      projectOid = (java.lang.String) input.readObject();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( LessonLearn thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7433127048463050232L( input, readSerialVersionUID, superDone );
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
