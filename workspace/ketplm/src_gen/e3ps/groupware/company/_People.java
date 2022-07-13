package e3ps.groupware.company;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _People implements wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.groupware.company.companyResource";
   static final java.lang.String CLASSNAME = People.class.getName();

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String ID = "id";
   static int ID_UPPER_LIMIT = -1;
   java.lang.String id;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getId() {
      return id;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setId(java.lang.String id) throws wt.util.WTPropertyVetoException {
      idValidate(id);
      this.id = id;
   }
   void idValidate(java.lang.String id) throws wt.util.WTPropertyVetoException {
      if (ID_UPPER_LIMIT < 1) {
         try { ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("id").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ID_UPPER_LIMIT = 200; }
      }
      if (id != null && !wt.fc.PersistenceHelper.checkStoredLength(id.toString(), ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "id"), java.lang.String.valueOf(java.lang.Math.min(ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "id", this.id, id));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String NAME = "name";
   static int NAME_UPPER_LIMIT = -1;
   java.lang.String name;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getName() {
      return name;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setName(java.lang.String name) throws wt.util.WTPropertyVetoException {
      nameValidate(name);
      this.name = name;
   }
   void nameValidate(java.lang.String name) throws wt.util.WTPropertyVetoException {
      if (NAME_UPPER_LIMIT < 1) {
         try { NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("name").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NAME_UPPER_LIMIT = 200; }
      }
      if (name != null && !wt.fc.PersistenceHelper.checkStoredLength(name.toString(), NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "name"), java.lang.String.valueOf(java.lang.Math.min(NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "name", this.name, name));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String EMAIL = "email";
   static int EMAIL_UPPER_LIMIT = -1;
   java.lang.String email;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getEmail() {
      return email;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setEmail(java.lang.String email) throws wt.util.WTPropertyVetoException {
      emailValidate(email);
      this.email = email;
   }
   void emailValidate(java.lang.String email) throws wt.util.WTPropertyVetoException {
      if (EMAIL_UPPER_LIMIT < 1) {
         try { EMAIL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("email").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { EMAIL_UPPER_LIMIT = 200; }
      }
      if (email != null && !wt.fc.PersistenceHelper.checkStoredLength(email.toString(), EMAIL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "email"), java.lang.String.valueOf(java.lang.Math.min(EMAIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "email", this.email, email));
   }

   /**
    * 직위 : eSolution.properties 파일에 정의된 직위체계를 따른다.
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String DUTY = "duty";
   static int DUTY_UPPER_LIMIT = -1;
   java.lang.String duty;
   /**
    * 직위 : eSolution.properties 파일에 정의된 직위체계를 따른다.
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getDuty() {
      return duty;
   }
   /**
    * 직위 : eSolution.properties 파일에 정의된 직위체계를 따른다.
    *
    * @see e3ps.groupware.company.People
    */
   public void setDuty(java.lang.String duty) throws wt.util.WTPropertyVetoException {
      dutyValidate(duty);
      this.duty = duty;
   }
   void dutyValidate(java.lang.String duty) throws wt.util.WTPropertyVetoException {
      if (DUTY_UPPER_LIMIT < 1) {
         try { DUTY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("duty").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DUTY_UPPER_LIMIT = 200; }
      }
      if (duty != null && !wt.fc.PersistenceHelper.checkStoredLength(duty.toString(), DUTY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "duty"), java.lang.String.valueOf(java.lang.Math.min(DUTY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "duty", this.duty, duty));
   }

   /**
    * 사내 서열을 표현하기 위한 필드(낮을수록 서열이 높다)
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String DUTY_CODE = "dutyCode";
   static int DUTY_CODE_UPPER_LIMIT = -1;
   java.lang.String dutyCode;
   /**
    * 사내 서열을 표현하기 위한 필드(낮을수록 서열이 높다)
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getDutyCode() {
      return dutyCode;
   }
   /**
    * 사내 서열을 표현하기 위한 필드(낮을수록 서열이 높다)
    *
    * @see e3ps.groupware.company.People
    */
   public void setDutyCode(java.lang.String dutyCode) throws wt.util.WTPropertyVetoException {
      dutyCodeValidate(dutyCode);
      this.dutyCode = dutyCode;
   }
   void dutyCodeValidate(java.lang.String dutyCode) throws wt.util.WTPropertyVetoException {
      if (DUTY_CODE_UPPER_LIMIT < 1) {
         try { DUTY_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("dutyCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DUTY_CODE_UPPER_LIMIT = 200; }
      }
      if (dutyCode != null && !wt.fc.PersistenceHelper.checkStoredLength(dutyCode.toString(), DUTY_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "dutyCode"), java.lang.String.valueOf(java.lang.Math.min(DUTY_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "dutyCode", this.dutyCode, dutyCode));
   }

   /**
    * 회사전화번호
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String OFFICE_TEL = "officeTel";
   static int OFFICE_TEL_UPPER_LIMIT = -1;
   java.lang.String officeTel;
   /**
    * 회사전화번호
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getOfficeTel() {
      return officeTel;
   }
   /**
    * 회사전화번호
    *
    * @see e3ps.groupware.company.People
    */
   public void setOfficeTel(java.lang.String officeTel) throws wt.util.WTPropertyVetoException {
      officeTelValidate(officeTel);
      this.officeTel = officeTel;
   }
   void officeTelValidate(java.lang.String officeTel) throws wt.util.WTPropertyVetoException {
      if (OFFICE_TEL_UPPER_LIMIT < 1) {
         try { OFFICE_TEL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("officeTel").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { OFFICE_TEL_UPPER_LIMIT = 200; }
      }
      if (officeTel != null && !wt.fc.PersistenceHelper.checkStoredLength(officeTel.toString(), OFFICE_TEL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "officeTel"), java.lang.String.valueOf(java.lang.Math.min(OFFICE_TEL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "officeTel", this.officeTel, officeTel));
   }

   /**
    * 집전화번호
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String HOME_TEL = "homeTel";
   static int HOME_TEL_UPPER_LIMIT = -1;
   java.lang.String homeTel;
   /**
    * 집전화번호
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getHomeTel() {
      return homeTel;
   }
   /**
    * 집전화번호
    *
    * @see e3ps.groupware.company.People
    */
   public void setHomeTel(java.lang.String homeTel) throws wt.util.WTPropertyVetoException {
      homeTelValidate(homeTel);
      this.homeTel = homeTel;
   }
   void homeTelValidate(java.lang.String homeTel) throws wt.util.WTPropertyVetoException {
      if (HOME_TEL_UPPER_LIMIT < 1) {
         try { HOME_TEL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("homeTel").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HOME_TEL_UPPER_LIMIT = 200; }
      }
      if (homeTel != null && !wt.fc.PersistenceHelper.checkStoredLength(homeTel.toString(), HOME_TEL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "homeTel"), java.lang.String.valueOf(java.lang.Math.min(HOME_TEL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "homeTel", this.homeTel, homeTel));
   }

   /**
    * 핸드폰번호
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String CELL_TEL = "cellTel";
   static int CELL_TEL_UPPER_LIMIT = -1;
   java.lang.String cellTel;
   /**
    * 핸드폰번호
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getCellTel() {
      return cellTel;
   }
   /**
    * 핸드폰번호
    *
    * @see e3ps.groupware.company.People
    */
   public void setCellTel(java.lang.String cellTel) throws wt.util.WTPropertyVetoException {
      cellTelValidate(cellTel);
      this.cellTel = cellTel;
   }
   void cellTelValidate(java.lang.String cellTel) throws wt.util.WTPropertyVetoException {
      if (CELL_TEL_UPPER_LIMIT < 1) {
         try { CELL_TEL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("cellTel").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CELL_TEL_UPPER_LIMIT = 200; }
      }
      if (cellTel != null && !wt.fc.PersistenceHelper.checkStoredLength(cellTel.toString(), CELL_TEL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "cellTel"), java.lang.String.valueOf(java.lang.Math.min(CELL_TEL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "cellTel", this.cellTel, cellTel));
   }

   /**
    * 주소
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String ADDRESS = "address";
   static int ADDRESS_UPPER_LIMIT = -1;
   java.lang.String address;
   /**
    * 주소
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getAddress() {
      return address;
   }
   /**
    * 주소
    *
    * @see e3ps.groupware.company.People
    */
   public void setAddress(java.lang.String address) throws wt.util.WTPropertyVetoException {
      addressValidate(address);
      this.address = address;
   }
   void addressValidate(java.lang.String address) throws wt.util.WTPropertyVetoException {
      if (ADDRESS_UPPER_LIMIT < 1) {
         try { ADDRESS_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("address").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ADDRESS_UPPER_LIMIT = 200; }
      }
      if (address != null && !wt.fc.PersistenceHelper.checkStoredLength(address.toString(), ADDRESS_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "address"), java.lang.String.valueOf(java.lang.Math.min(ADDRESS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "address", this.address, address));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String PRIORITY = "priority";
   int priority;
   /**
    * @see e3ps.groupware.company.People
    */
   public int getPriority() {
      return priority;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setPriority(int priority) throws wt.util.WTPropertyVetoException {
      priorityValidate(priority);
      this.priority = priority;
   }
   void priorityValidate(int priority) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String PASSWORD = "password";
   static int PASSWORD_UPPER_LIMIT = -1;
   java.lang.String password;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getPassword() {
      return password;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setPassword(java.lang.String password) throws wt.util.WTPropertyVetoException {
      passwordValidate(password);
      this.password = password;
   }
   void passwordValidate(java.lang.String password) throws wt.util.WTPropertyVetoException {
      if (PASSWORD_UPPER_LIMIT < 1) {
         try { PASSWORD_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("password").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PASSWORD_UPPER_LIMIT = 200; }
      }
      if (password != null && !wt.fc.PersistenceHelper.checkStoredLength(password.toString(), PASSWORD_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "password"), java.lang.String.valueOf(java.lang.Math.min(PASSWORD_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "password", this.password, password));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String IS_DISABLE = "isDisable";
   boolean isDisable = false;
   /**
    * @see e3ps.groupware.company.People
    */
   public boolean isIsDisable() {
      return isDisable;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setIsDisable(boolean isDisable) throws wt.util.WTPropertyVetoException {
      isDisableValidate(isDisable);
      this.isDisable = isDisable;
   }
   void isDisableValidate(boolean isDisable) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String SORT_NUM = "sortNum";
   int sortNum = 0;
   /**
    * @see e3ps.groupware.company.People
    */
   public int getSortNum() {
      return sortNum;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setSortNum(int sortNum) throws wt.util.WTPropertyVetoException {
      sortNumValidate(sortNum);
      this.sortNum = sortNum;
   }
   void sortNumValidate(int sortNum) throws wt.util.WTPropertyVetoException {
      if (sortNum > 30)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "sortNum"), "30" },
               new java.beans.PropertyChangeEvent(this, "sortNum", this.sortNum, sortNum));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String PROXY = "proxy";
   wt.fc.ObjectReference proxy;
   /**
    * @see e3ps.groupware.company.People
    */
   public wt.fc.ObjectReference getProxy() {
      return proxy;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setProxy(wt.fc.ObjectReference proxy) throws wt.util.WTPropertyVetoException {
      proxyValidate(proxy);
      this.proxy = proxy;
   }
   void proxyValidate(wt.fc.ObjectReference proxy) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String WARNING_COUNT = "warningCount";
   int warningCount = 0;
   /**
    * @see e3ps.groupware.company.People
    */
   public int getWarningCount() {
      return warningCount;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setWarningCount(int warningCount) throws wt.util.WTPropertyVetoException {
      warningCountValidate(warningCount);
      this.warningCount = warningCount;
   }
   void warningCountValidate(int warningCount) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String PW_CHANGE_DATE = "pwChangeDate";
   java.sql.Timestamp pwChangeDate;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.sql.Timestamp getPwChangeDate() {
      return pwChangeDate;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setPwChangeDate(java.sql.Timestamp pwChangeDate) throws wt.util.WTPropertyVetoException {
      pwChangeDateValidate(pwChangeDate);
      this.pwChangeDate = pwChangeDate;
   }
   void pwChangeDateValidate(java.sql.Timestamp pwChangeDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 입사일≪≫
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String ENTER_DATE = "enterDate";
   java.sql.Timestamp enterDate;
   /**
    * 입사일≪≫
    *
    * @see e3ps.groupware.company.People
    */
   public java.sql.Timestamp getEnterDate() {
      return enterDate;
   }
   /**
    * 입사일≪≫
    *
    * @see e3ps.groupware.company.People
    */
   public void setEnterDate(java.sql.Timestamp enterDate) throws wt.util.WTPropertyVetoException {
      enterDateValidate(enterDate);
      this.enterDate = enterDate;
   }
   void enterDateValidate(java.sql.Timestamp enterDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 퇴사일
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String RETRIRE_DATE = "retrireDate";
   java.sql.Timestamp retrireDate;
   /**
    * 퇴사일
    *
    * @see e3ps.groupware.company.People
    */
   public java.sql.Timestamp getRetrireDate() {
      return retrireDate;
   }
   /**
    * 퇴사일
    *
    * @see e3ps.groupware.company.People
    */
   public void setRetrireDate(java.sql.Timestamp retrireDate) throws wt.util.WTPropertyVetoException {
      retrireDateValidate(retrireDate);
      this.retrireDate = retrireDate;
   }
   void retrireDateValidate(java.sql.Timestamp retrireDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String GRADE_NAME = "gradeName";
   static int GRADE_NAME_UPPER_LIMIT = -1;
   java.lang.String gradeName;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getGradeName() {
      return gradeName;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setGradeName(java.lang.String gradeName) throws wt.util.WTPropertyVetoException {
      gradeNameValidate(gradeName);
      this.gradeName = gradeName;
   }
   void gradeNameValidate(java.lang.String gradeName) throws wt.util.WTPropertyVetoException {
      if (GRADE_NAME_UPPER_LIMIT < 1) {
         try { GRADE_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("gradeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GRADE_NAME_UPPER_LIMIT = 200; }
      }
      if (gradeName != null && !wt.fc.PersistenceHelper.checkStoredLength(gradeName.toString(), GRADE_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "gradeName"), java.lang.String.valueOf(java.lang.Math.min(GRADE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "gradeName", this.gradeName, gradeName));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String GRADE_CODE = "gradeCode";
   static int GRADE_CODE_UPPER_LIMIT = -1;
   java.lang.String gradeCode;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getGradeCode() {
      return gradeCode;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setGradeCode(java.lang.String gradeCode) throws wt.util.WTPropertyVetoException {
      gradeCodeValidate(gradeCode);
      this.gradeCode = gradeCode;
   }
   void gradeCodeValidate(java.lang.String gradeCode) throws wt.util.WTPropertyVetoException {
      if (GRADE_CODE_UPPER_LIMIT < 1) {
         try { GRADE_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("gradeCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { GRADE_CODE_UPPER_LIMIT = 200; }
      }
      if (gradeCode != null && !wt.fc.PersistenceHelper.checkStoredLength(gradeCode.toString(), GRADE_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "gradeCode"), java.lang.String.valueOf(java.lang.Math.min(GRADE_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "gradeCode", this.gradeCode, gradeCode));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String CC_CODE = "ccCode";
   static int CC_CODE_UPPER_LIMIT = -1;
   java.lang.String ccCode;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getCcCode() {
      return ccCode;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setCcCode(java.lang.String ccCode) throws wt.util.WTPropertyVetoException {
      ccCodeValidate(ccCode);
      this.ccCode = ccCode;
   }
   void ccCodeValidate(java.lang.String ccCode) throws wt.util.WTPropertyVetoException {
      if (CC_CODE_UPPER_LIMIT < 1) {
         try { CC_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("ccCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CC_CODE_UPPER_LIMIT = 200; }
      }
      if (ccCode != null && !wt.fc.PersistenceHelper.checkStoredLength(ccCode.toString(), CC_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "ccCode"), java.lang.String.valueOf(java.lang.Math.min(CC_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "ccCode", this.ccCode, ccCode));
   }

   /**
    * 주민번호
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String PER_NO = "perNo";
   static int PER_NO_UPPER_LIMIT = -1;
   java.lang.String perNo;
   /**
    * 주민번호
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getPerNo() {
      return perNo;
   }
   /**
    * 주민번호
    *
    * @see e3ps.groupware.company.People
    */
   public void setPerNo(java.lang.String perNo) throws wt.util.WTPropertyVetoException {
      perNoValidate(perNo);
      this.perNo = perNo;
   }
   void perNoValidate(java.lang.String perNo) throws wt.util.WTPropertyVetoException {
      if (PER_NO_UPPER_LIMIT < 1) {
         try { PER_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("perNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PER_NO_UPPER_LIMIT = 200; }
      }
      if (perNo != null && !wt.fc.PersistenceHelper.checkStoredLength(perNo.toString(), PER_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "perNo"), java.lang.String.valueOf(java.lang.Math.min(PER_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "perNo", this.perNo, perNo));
   }

   /**
    * 은행코드
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String BANK_CODE = "bankCode";
   static int BANK_CODE_UPPER_LIMIT = -1;
   java.lang.String bankCode;
   /**
    * 은행코드
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getBankCode() {
      return bankCode;
   }
   /**
    * 은행코드
    *
    * @see e3ps.groupware.company.People
    */
   public void setBankCode(java.lang.String bankCode) throws wt.util.WTPropertyVetoException {
      bankCodeValidate(bankCode);
      this.bankCode = bankCode;
   }
   void bankCodeValidate(java.lang.String bankCode) throws wt.util.WTPropertyVetoException {
      if (BANK_CODE_UPPER_LIMIT < 1) {
         try { BANK_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("bankCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { BANK_CODE_UPPER_LIMIT = 200; }
      }
      if (bankCode != null && !wt.fc.PersistenceHelper.checkStoredLength(bankCode.toString(), BANK_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "bankCode"), java.lang.String.valueOf(java.lang.Math.min(BANK_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "bankCode", this.bankCode, bankCode));
   }

   /**
    * 국가코드
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String NATIONAL_CODE = "nationalCode";
   static int NATIONAL_CODE_UPPER_LIMIT = -1;
   java.lang.String nationalCode;
   /**
    * 국가코드
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getNationalCode() {
      return nationalCode;
   }
   /**
    * 국가코드
    *
    * @see e3ps.groupware.company.People
    */
   public void setNationalCode(java.lang.String nationalCode) throws wt.util.WTPropertyVetoException {
      nationalCodeValidate(nationalCode);
      this.nationalCode = nationalCode;
   }
   void nationalCodeValidate(java.lang.String nationalCode) throws wt.util.WTPropertyVetoException {
      if (NATIONAL_CODE_UPPER_LIMIT < 1) {
         try { NATIONAL_CODE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("nationalCode").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { NATIONAL_CODE_UPPER_LIMIT = 200; }
      }
      if (nationalCode != null && !wt.fc.PersistenceHelper.checkStoredLength(nationalCode.toString(), NATIONAL_CODE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "nationalCode"), java.lang.String.valueOf(java.lang.Math.min(NATIONAL_CODE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "nationalCode", this.nationalCode, nationalCode));
   }

   /**
    * 계좌번호
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String ACCOUNT_NO = "accountNo";
   static int ACCOUNT_NO_UPPER_LIMIT = -1;
   java.lang.String accountNo;
   /**
    * 계좌번호
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getAccountNo() {
      return accountNo;
   }
   /**
    * 계좌번호
    *
    * @see e3ps.groupware.company.People
    */
   public void setAccountNo(java.lang.String accountNo) throws wt.util.WTPropertyVetoException {
      accountNoValidate(accountNo);
      this.accountNo = accountNo;
   }
   void accountNoValidate(java.lang.String accountNo) throws wt.util.WTPropertyVetoException {
      if (ACCOUNT_NO_UPPER_LIMIT < 1) {
         try { ACCOUNT_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("accountNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACCOUNT_NO_UPPER_LIMIT = 200; }
      }
      if (accountNo != null && !wt.fc.PersistenceHelper.checkStoredLength(accountNo.toString(), ACCOUNT_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "accountNo"), java.lang.String.valueOf(java.lang.Math.min(ACCOUNT_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "accountNo", this.accountNo, accountNo));
   }

   /**
    * 우편번호
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String ZIP_NO = "zipNo";
   static int ZIP_NO_UPPER_LIMIT = -1;
   java.lang.String zipNo;
   /**
    * 우편번호
    *
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getZipNo() {
      return zipNo;
   }
   /**
    * 우편번호
    *
    * @see e3ps.groupware.company.People
    */
   public void setZipNo(java.lang.String zipNo) throws wt.util.WTPropertyVetoException {
      zipNoValidate(zipNo);
      this.zipNo = zipNo;
   }
   void zipNoValidate(java.lang.String zipNo) throws wt.util.WTPropertyVetoException {
      if (ZIP_NO_UPPER_LIMIT < 1) {
         try { ZIP_NO_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("zipNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ZIP_NO_UPPER_LIMIT = 200; }
      }
      if (zipNo != null && !wt.fc.PersistenceHelper.checkStoredLength(zipNo.toString(), ZIP_NO_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "zipNo"), java.lang.String.valueOf(java.lang.Math.min(ZIP_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "zipNo", this.zipNo, zipNo));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String CHIEF = "chief";
   static int CHIEF_UPPER_LIMIT = -1;
   java.lang.String chief;
   /**
    * @see e3ps.groupware.company.People
    */
   public java.lang.String getChief() {
      return chief;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setChief(java.lang.String chief) throws wt.util.WTPropertyVetoException {
      chiefValidate(chief);
      this.chief = chief;
   }
   void chiefValidate(java.lang.String chief) throws wt.util.WTPropertyVetoException {
      if (CHIEF_UPPER_LIMIT < 1) {
         try { CHIEF_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("chief").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHIEF_UPPER_LIMIT = 200; }
      }
      if (chief != null && !wt.fc.PersistenceHelper.checkStoredLength(chief.toString(), CHIEF_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "chief"), java.lang.String.valueOf(java.lang.Math.min(CHIEF_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "chief", this.chief, chief));
   }

   /**
    * 퇴사예정일
    *
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String RETIRE_TARGET_DATE = "retireTargetDate";
   java.sql.Timestamp retireTargetDate;
   /**
    * 퇴사예정일
    *
    * @see e3ps.groupware.company.People
    */
   public java.sql.Timestamp getRetireTargetDate() {
      return retireTargetDate;
   }
   /**
    * 퇴사예정일
    *
    * @see e3ps.groupware.company.People
    */
   public void setRetireTargetDate(java.sql.Timestamp retireTargetDate) throws wt.util.WTPropertyVetoException {
      retireTargetDateValidate(retireTargetDate);
      this.retireTargetDate = retireTargetDate;
   }
   void retireTargetDateValidate(java.sql.Timestamp retireTargetDate) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String USER = "user";
   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String USER_REFERENCE = "userReference";
   wt.fc.ObjectReference userReference;
   /**
    * @see e3ps.groupware.company.People
    */
   public wt.org.WTUser getUser() {
      return (userReference != null) ? (wt.org.WTUser) userReference.getObject() : null;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public wt.fc.ObjectReference getUserReference() {
      return userReference;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setUser(wt.org.WTUser the_user) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setUserReference(the_user == null ? null : wt.fc.ObjectReference.newObjectReference((wt.org.WTUser) the_user));
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setUserReference(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      userReferenceValidate(the_userReference);
      userReference = (wt.fc.ObjectReference) the_userReference;
   }
   void userReferenceValidate(wt.fc.ObjectReference the_userReference) throws wt.util.WTPropertyVetoException {
      if (the_userReference == null || the_userReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference") },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
      if (the_userReference != null && the_userReference.getReferencedClass() != null &&
            !wt.org.WTUser.class.isAssignableFrom(the_userReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "userReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "userReference", this.userReference, userReference));
   }

   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String DEPARTMENT = "department";
   /**
    * @see e3ps.groupware.company.People
    */
   public static final java.lang.String DEPARTMENT_REFERENCE = "departmentReference";
   wt.fc.ObjectReference departmentReference;
   /**
    * @see e3ps.groupware.company.People
    */
   public e3ps.groupware.company.Department getDepartment() {
      return (departmentReference != null) ? (e3ps.groupware.company.Department) departmentReference.getObject() : null;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public wt.fc.ObjectReference getDepartmentReference() {
      return departmentReference;
   }
   /**
    * @see e3ps.groupware.company.People
    */
   public void setDepartment(e3ps.groupware.company.Department the_department) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setDepartmentReference(the_department == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.groupware.company.Department) the_department));
   }
   /**
    * @see e3ps.groupware.company.People
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

   public static final long EXTERNALIZATION_VERSION_UID = 7604956960925960517L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      output.writeObject( accountNo );
      output.writeObject( address );
      output.writeObject( bankCode );
      output.writeObject( ccCode );
      output.writeObject( cellTel );
      output.writeObject( chief );
      output.writeObject( departmentReference );
      output.writeObject( duty );
      output.writeObject( dutyCode );
      output.writeObject( email );
      output.writeObject( enterDate );
      output.writeObject( gradeCode );
      output.writeObject( gradeName );
      output.writeObject( homeTel );
      output.writeObject( id );
      output.writeBoolean( isDisable );
      output.writeObject( name );
      output.writeObject( nationalCode );
      output.writeObject( officeTel );
      output.writeObject( password );
      output.writeObject( perNo );
      output.writeInt( priority );
      output.writeObject( proxy );
      output.writeObject( pwChangeDate );
      output.writeObject( retireTargetDate );
      output.writeObject( retrireDate );
      output.writeInt( sortNum );
      output.writeObject( thePersistInfo );
      output.writeObject( userReference );
      output.writeInt( warningCount );
      output.writeObject( zipNo );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }


   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.groupware.company.People) this, input, readSerialVersionUID, false, false );
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      output.setString( "accountNo", accountNo );
      output.setString( "address", address );
      output.setString( "bankCode", bankCode );
      output.setString( "ccCode", ccCode );
      output.setString( "cellTel", cellTel );
      output.setString( "chief", chief );
      output.writeObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      output.setString( "duty", duty );
      output.setString( "dutyCode", dutyCode );
      output.setString( "email", email );
      output.setTimestamp( "enterDate", enterDate );
      output.setString( "gradeCode", gradeCode );
      output.setString( "gradeName", gradeName );
      output.setString( "homeTel", homeTel );
      output.setString( "id", id );
      output.setBoolean( "isDisable", isDisable );
      output.setString( "name", name );
      output.setString( "nationalCode", nationalCode );
      output.setString( "officeTel", officeTel );
      output.setString( "password", password );
      output.setString( "perNo", perNo );
      output.setInt( "priority", priority );
      output.writeObject( "proxy", proxy, wt.fc.ObjectReference.class, true );
      output.setTimestamp( "pwChangeDate", pwChangeDate );
      output.setTimestamp( "retireTargetDate", retireTargetDate );
      output.setTimestamp( "retrireDate", retrireDate );
      output.setInt( "sortNum", sortNum );
      output.writeObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      output.writeObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      output.setInt( "warningCount", warningCount );
      output.setString( "zipNo", zipNo );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      accountNo = input.getString( "accountNo" );
      address = input.getString( "address" );
      bankCode = input.getString( "bankCode" );
      ccCode = input.getString( "ccCode" );
      cellTel = input.getString( "cellTel" );
      chief = input.getString( "chief" );
      departmentReference = (wt.fc.ObjectReference) input.readObject( "departmentReference", departmentReference, wt.fc.ObjectReference.class, true );
      duty = input.getString( "duty" );
      dutyCode = input.getString( "dutyCode" );
      email = input.getString( "email" );
      enterDate = input.getTimestamp( "enterDate" );
      gradeCode = input.getString( "gradeCode" );
      gradeName = input.getString( "gradeName" );
      homeTel = input.getString( "homeTel" );
      id = input.getString( "id" );
      isDisable = input.getBoolean( "isDisable" );
      name = input.getString( "name" );
      nationalCode = input.getString( "nationalCode" );
      officeTel = input.getString( "officeTel" );
      password = input.getString( "password" );
      perNo = input.getString( "perNo" );
      priority = input.getInt( "priority" );
      proxy = (wt.fc.ObjectReference) input.readObject( "proxy", proxy, wt.fc.ObjectReference.class, true );
      pwChangeDate = input.getTimestamp( "pwChangeDate" );
      retireTargetDate = input.getTimestamp( "retireTargetDate" );
      retrireDate = input.getTimestamp( "retrireDate" );
      sortNum = input.getInt( "sortNum" );
      thePersistInfo = (wt.fc.PersistInfo) input.readObject( "thePersistInfo", thePersistInfo, wt.fc.PersistInfo.class, true );
      userReference = (wt.fc.ObjectReference) input.readObject( "userReference", userReference, wt.fc.ObjectReference.class, true );
      warningCount = input.getInt( "warningCount" );
      zipNo = input.getString( "zipNo" );
   }

   boolean readVersion7604956960925960517L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      accountNo = (java.lang.String) input.readObject();
      address = (java.lang.String) input.readObject();
      bankCode = (java.lang.String) input.readObject();
      ccCode = (java.lang.String) input.readObject();
      cellTel = (java.lang.String) input.readObject();
      chief = (java.lang.String) input.readObject();
      departmentReference = (wt.fc.ObjectReference) input.readObject();
      duty = (java.lang.String) input.readObject();
      dutyCode = (java.lang.String) input.readObject();
      email = (java.lang.String) input.readObject();
      enterDate = (java.sql.Timestamp) input.readObject();
      gradeCode = (java.lang.String) input.readObject();
      gradeName = (java.lang.String) input.readObject();
      homeTel = (java.lang.String) input.readObject();
      id = (java.lang.String) input.readObject();
      isDisable = input.readBoolean();
      name = (java.lang.String) input.readObject();
      nationalCode = (java.lang.String) input.readObject();
      officeTel = (java.lang.String) input.readObject();
      password = (java.lang.String) input.readObject();
      perNo = (java.lang.String) input.readObject();
      priority = input.readInt();
      proxy = (wt.fc.ObjectReference) input.readObject();
      pwChangeDate = (java.sql.Timestamp) input.readObject();
      retireTargetDate = (java.sql.Timestamp) input.readObject();
      retrireDate = (java.sql.Timestamp) input.readObject();
      sortNum = input.readInt();
      thePersistInfo = (wt.fc.PersistInfo) input.readObject();
      userReference = (wt.fc.ObjectReference) input.readObject();
      warningCount = input.readInt();
      zipNo = (java.lang.String) input.readObject();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( People thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion7604956960925960517L( input, readSerialVersionUID, superDone );
      else if ( readSerialVersionUID == -5802461581330891587L )
         return ((People) this).readVersion_5802461581330891587L( input, readSerialVersionUID, superDone );
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
