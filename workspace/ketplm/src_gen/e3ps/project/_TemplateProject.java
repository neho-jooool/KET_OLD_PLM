package e3ps.project;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public abstract class _TemplateProject extends wt.enterprise.Managed implements wt.inf.container.WTContained, wt.content.ContentHolder, java.io.Externalizable {
   static final long serialVersionUID = 1;

   static final java.lang.String RESOURCE = "e3ps.project.projectResource";
   static final java.lang.String CLASSNAME = TemplateProject.class.getName();

   /**
    * 프로젝트 순서
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_SEQ = "pjtSeq";
   int pjtSeq;
   /**
    * 프로젝트 순서
    *
    * @see e3ps.project.TemplateProject
    */
   public int getPjtSeq() {
      return pjtSeq;
   }
   /**
    * 프로젝트 순서
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtSeq(int pjtSeq) throws wt.util.WTPropertyVetoException {
      pjtSeqValidate(pjtSeq);
      this.pjtSeq = pjtSeq;
   }
   void pjtSeqValidate(int pjtSeq) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트 설명
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_DESC = "pjtDesc";
   static int PJT_DESC_UPPER_LIMIT = -1;
   java.lang.String pjtDesc;
   /**
    * 프로젝트 설명
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getPjtDesc() {
      return pjtDesc;
   }
   /**
    * 프로젝트 설명
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtDesc(java.lang.String pjtDesc) throws wt.util.WTPropertyVetoException {
      pjtDescValidate(pjtDesc);
      this.pjtDesc = pjtDesc;
   }
   void pjtDescValidate(java.lang.String pjtDesc) throws wt.util.WTPropertyVetoException {
      if (PJT_DESC_UPPER_LIMIT < 1) {
         try { PJT_DESC_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtDesc").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_DESC_UPPER_LIMIT = 5000; }
      }
      if (pjtDesc != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtDesc.toString(), PJT_DESC_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtDesc"), java.lang.String.valueOf(java.lang.Math.min(PJT_DESC_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtDesc", this.pjtDesc, pjtDesc));
   }

   /**
    * 프로젝트 일정
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_SCHEDULE = "pjtSchedule";
   wt.fc.ObjectReference pjtSchedule;
   /**
    * 프로젝트 일정
    *
    * @see e3ps.project.TemplateProject
    */
   public wt.fc.ObjectReference getPjtSchedule() {
      return pjtSchedule;
   }
   /**
    * 프로젝트 일정
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtSchedule(wt.fc.ObjectReference pjtSchedule) throws wt.util.WTPropertyVetoException {
      pjtScheduleValidate(pjtSchedule);
      this.pjtSchedule = pjtSchedule;
   }
   void pjtScheduleValidate(wt.fc.ObjectReference pjtSchedule) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트 종류≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_TYPE = "pjtType";
   int pjtType;
   /**
    * 프로젝트 종류≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발
    *
    * @see e3ps.project.TemplateProject
    */
   public int getPjtType() {
      return pjtType;
   }
   /**
    * 프로젝트 종류≪≫≪≫0: 사전개발계획(영업수주)≪≫1: 양산≪≫2: 개발
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtType(int pjtType) throws wt.util.WTPropertyVetoException {
      pjtTypeValidate(pjtType);
      this.pjtType = pjtType;
   }
   void pjtTypeValidate(int pjtType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_HISTORY = "pjtHistory";
   int pjtHistory;
   /**
    * 프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TemplateProject
    */
   public int getPjtHistory() {
      return pjtHistory;
   }
   /**
    * 프로젝트 이력≪≫≪≫0: 최초생성 시≪≫1~N: 갱신 시
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtHistory(int pjtHistory) throws wt.util.WTPropertyVetoException {
      pjtHistoryValidate(pjtHistory);
      this.pjtHistory = pjtHistory;
   }
   void pjtHistoryValidate(int pjtHistory) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 1(true): List에 나타남≪≫0(false): List에 나타나지 않음
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String LASTEST = "lastest";
   boolean lastest = true;
   /**
    * 1(true): List에 나타남≪≫0(false): List에 나타나지 않음
    *
    * @see e3ps.project.TemplateProject
    */
   public boolean isLastest() {
      return lastest;
   }
   /**
    * 1(true): List에 나타남≪≫0(false): List에 나타나지 않음
    *
    * @see e3ps.project.TemplateProject
    */
   public void setLastest(boolean lastest) throws wt.util.WTPropertyVetoException {
      lastestValidate(lastest);
      this.lastest = lastest;
   }
   void lastestValidate(boolean lastest) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String CHECK_OUT = "checkOut";
   boolean checkOut;
   /**
    * @see e3ps.project.TemplateProject
    */
   public boolean isCheckOut() {
      return checkOut;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setCheckOut(boolean checkOut) throws wt.util.WTPropertyVetoException {
      checkOutValidate(checkOut);
      this.checkOut = checkOut;
   }
   void checkOutValidate(boolean checkOut) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String WORKING_COPY = "workingCopy";
   boolean workingCopy;
   /**
    * @see e3ps.project.TemplateProject
    */
   public boolean isWorkingCopy() {
      return workingCopy;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setWorkingCopy(boolean workingCopy) throws wt.util.WTPropertyVetoException {
      workingCopyValidate(workingCopy);
      this.workingCopy = workingCopy;
   }
   void workingCopyValidate(boolean workingCopy) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String CHECK_OUT_STATE = "checkOutState";
   static int CHECK_OUT_STATE_UPPER_LIMIT = -1;
   java.lang.String checkOutState = "c/i";
   /**
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getCheckOutState() {
      return checkOutState;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setCheckOutState(java.lang.String checkOutState) throws wt.util.WTPropertyVetoException {
      checkOutStateValidate(checkOutState);
      this.checkOutState = checkOutState;
   }
   void checkOutStateValidate(java.lang.String checkOutState) throws wt.util.WTPropertyVetoException {
      if (CHECK_OUT_STATE_UPPER_LIMIT < 1) {
         try { CHECK_OUT_STATE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("checkOutState").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CHECK_OUT_STATE_UPPER_LIMIT = 200; }
      }
      if (checkOutState != null && !wt.fc.PersistenceHelper.checkStoredLength(checkOutState.toString(), CHECK_OUT_STATE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "checkOutState"), java.lang.String.valueOf(java.lang.Math.min(CHECK_OUT_STATE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "checkOutState", this.checkOutState, checkOutState));
   }

   /**
    * OLD/NEW PLM 구분자≪≫true: NEW PLM≪≫false: OLD PLM
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String DIFF_PLM = "diffPLM";
   boolean diffPLM = true;
   /**
    * OLD/NEW PLM 구분자≪≫true: NEW PLM≪≫false: OLD PLM
    *
    * @see e3ps.project.TemplateProject
    */
   public boolean isDiffPLM() {
      return diffPLM;
   }
   /**
    * OLD/NEW PLM 구분자≪≫true: NEW PLM≪≫false: OLD PLM
    *
    * @see e3ps.project.TemplateProject
    */
   public void setDiffPLM(boolean diffPLM) throws wt.util.WTPropertyVetoException {
      diffPLMValidate(diffPLM);
      this.diffPLM = diffPLM;
   }
   void diffPLMValidate(boolean diffPLM) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 1(true) : LH [Default]≪≫0(false) : RH
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String IS_DIRECTION = "isDirection";
   boolean isDirection = true;
   /**
    * 1(true) : LH [Default]≪≫0(false) : RH
    *
    * @see e3ps.project.TemplateProject
    */
   public boolean isIsDirection() {
      return isDirection;
   }
   /**
    * 1(true) : LH [Default]≪≫0(false) : RH
    *
    * @see e3ps.project.TemplateProject
    */
   public void setIsDirection(boolean isDirection) throws wt.util.WTPropertyVetoException {
      isDirectionValidate(isDirection);
      this.isDirection = isDirection;
   }
   void isDirectionValidate(boolean isDirection) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String HISTORY_NOTE = "historyNote";
   static int HISTORY_NOTE_UPPER_LIMIT = -1;
   java.lang.String historyNote;
   /**
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getHistoryNote() {
      return historyNote;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setHistoryNote(java.lang.String historyNote) throws wt.util.WTPropertyVetoException {
      historyNoteValidate(historyNote);
      this.historyNote = historyNote;
   }
   void historyNoteValidate(java.lang.String historyNote) throws wt.util.WTPropertyVetoException {
      if (HISTORY_NOTE_UPPER_LIMIT < 1) {
         try { HISTORY_NOTE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("historyNote").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HISTORY_NOTE_UPPER_LIMIT = 200; }
      }
      if (historyNote != null && !wt.fc.PersistenceHelper.checkStoredLength(historyNote.toString(), HISTORY_NOTE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "historyNote"), java.lang.String.valueOf(java.lang.Math.min(HISTORY_NOTE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "historyNote", this.historyNote, historyNote));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_ID = "pjtID";
   static int PJT_ID_UPPER_LIMIT = -1;
   java.lang.String pjtID;
   /**
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getPjtID() {
      return pjtID;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setPjtID(java.lang.String pjtID) throws wt.util.WTPropertyVetoException {
      pjtIDValidate(pjtID);
      this.pjtID = pjtID;
   }
   void pjtIDValidate(java.lang.String pjtID) throws wt.util.WTPropertyVetoException {
      if (PJT_ID_UPPER_LIMIT < 1) {
         try { PJT_ID_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pjtID").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PJT_ID_UPPER_LIMIT = 200; }
      }
      if (pjtID != null && !wt.fc.PersistenceHelper.checkStoredLength(pjtID.toString(), PJT_ID_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "pjtID"), java.lang.String.valueOf(java.lang.Math.min(PJT_ID_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "pjtID", this.pjtID, pjtID));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String HISTORY_NOTE_TYPE = "historyNoteType";
   int historyNoteType;
   /**
    * @see e3ps.project.TemplateProject
    */
   public int getHistoryNoteType() {
      return historyNoteType;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setHistoryNoteType(int historyNoteType) throws wt.util.WTPropertyVetoException {
      historyNoteTypeValidate(historyNoteType);
      this.historyNoteType = historyNoteType;
   }
   void historyNoteTypeValidate(int historyNoteType) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 수주 프로젝트 시 "수주/미수주" 확인용 필드
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String ORDER_NAME = "orderName";
   static int ORDER_NAME_UPPER_LIMIT = -1;
   java.lang.String orderName;
   /**
    * 수주 프로젝트 시 "수주/미수주" 확인용 필드
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getOrderName() {
      return orderName;
   }
   /**
    * 수주 프로젝트 시 "수주/미수주" 확인용 필드
    *
    * @see e3ps.project.TemplateProject
    */
   public void setOrderName(java.lang.String orderName) throws wt.util.WTPropertyVetoException {
      orderNameValidate(orderName);
      this.orderName = orderName;
   }
   void orderNameValidate(java.lang.String orderName) throws wt.util.WTPropertyVetoException {
      if (ORDER_NAME_UPPER_LIMIT < 1) {
         try { ORDER_NAME_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("orderName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ORDER_NAME_UPPER_LIMIT = 200; }
      }
      if (orderName != null && !wt.fc.PersistenceHelper.checkStoredLength(orderName.toString(), ORDER_NAME_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "orderName"), java.lang.String.valueOf(java.lang.Math.min(ORDER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "orderName", this.orderName, orderName));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String STOPED_DETIL = "stopedDetil";
   static int STOPED_DETIL_UPPER_LIMIT = -1;
   java.lang.String stopedDetil;
   /**
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getStopedDetil() {
      return stopedDetil;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setStopedDetil(java.lang.String stopedDetil) throws wt.util.WTPropertyVetoException {
      stopedDetilValidate(stopedDetil);
      this.stopedDetil = stopedDetil;
   }
   void stopedDetilValidate(java.lang.String stopedDetil) throws wt.util.WTPropertyVetoException {
      if (STOPED_DETIL_UPPER_LIMIT < 1) {
         try { STOPED_DETIL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("stopedDetil").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { STOPED_DETIL_UPPER_LIMIT = 3000; }
      }
      if (stopedDetil != null && !wt.fc.PersistenceHelper.checkStoredLength(stopedDetil.toString(), STOPED_DETIL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "stopedDetil"), java.lang.String.valueOf(java.lang.Math.min(STOPED_DETIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "stopedDetil", this.stopedDetil, stopedDetil));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String RE_START_DETIL = "reStartDetil";
   static int RE_START_DETIL_UPPER_LIMIT = -1;
   java.lang.String reStartDetil;
   /**
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getReStartDetil() {
      return reStartDetil;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setReStartDetil(java.lang.String reStartDetil) throws wt.util.WTPropertyVetoException {
      reStartDetilValidate(reStartDetil);
      this.reStartDetil = reStartDetil;
   }
   void reStartDetilValidate(java.lang.String reStartDetil) throws wt.util.WTPropertyVetoException {
      if (RE_START_DETIL_UPPER_LIMIT < 1) {
         try { RE_START_DETIL_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("reStartDetil").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { RE_START_DETIL_UPPER_LIMIT = 3000; }
      }
      if (reStartDetil != null && !wt.fc.PersistenceHelper.checkStoredLength(reStartDetil.toString(), RE_START_DETIL_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "reStartDetil"), java.lang.String.valueOf(java.lang.Math.min(RE_START_DETIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "reStartDetil", this.reStartDetil, reStartDetil));
   }

   /**
    * HistoryNote Web Editor
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String HISTORY_NOTE_WEB_EDITOR = "historyNoteWebEditor";
   java.lang.Object historyNoteWebEditor;
   /**
    * HistoryNote Web Editor
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.Object getHistoryNoteWebEditor() {
      return historyNoteWebEditor;
   }
   /**
    * HistoryNote Web Editor
    *
    * @see e3ps.project.TemplateProject
    */
   public void setHistoryNoteWebEditor(java.lang.Object historyNoteWebEditor) throws wt.util.WTPropertyVetoException {
      historyNoteWebEditorValidate(historyNoteWebEditor);
      this.historyNoteWebEditor = historyNoteWebEditor;
   }
   void historyNoteWebEditorValidate(java.lang.Object historyNoteWebEditor) throws wt.util.WTPropertyVetoException {
   }

   /**
    * HistoryNote Web Editor Text
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String HISTORY_NOTE_WEB_EDITOR_TEXT = "historyNoteWebEditorText";
   java.lang.Object historyNoteWebEditorText;
   /**
    * HistoryNote Web Editor Text
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.Object getHistoryNoteWebEditorText() {
      return historyNoteWebEditorText;
   }
   /**
    * HistoryNote Web Editor Text
    *
    * @see e3ps.project.TemplateProject
    */
   public void setHistoryNoteWebEditorText(java.lang.Object historyNoteWebEditorText) throws wt.util.WTPropertyVetoException {
      historyNoteWebEditorTextValidate(historyNoteWebEditorText);
      this.historyNoteWebEditorText = historyNoteWebEditorText;
   }
   void historyNoteWebEditorTextValidate(java.lang.Object historyNoteWebEditorText) throws wt.util.WTPropertyVetoException {
   }

   /**
    * 방수여부
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String WATER_POOF = "waterPoof";
   static int WATER_POOF_UPPER_LIMIT = -1;
   java.lang.String waterPoof;
   /**
    * 방수여부
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getWaterPoof() {
      return waterPoof;
   }
   /**
    * 방수여부
    *
    * @see e3ps.project.TemplateProject
    */
   public void setWaterPoof(java.lang.String waterPoof) throws wt.util.WTPropertyVetoException {
      waterPoofValidate(waterPoof);
      this.waterPoof = waterPoof;
   }
   void waterPoofValidate(java.lang.String waterPoof) throws wt.util.WTPropertyVetoException {
      if (WATER_POOF_UPPER_LIMIT < 1) {
         try { WATER_POOF_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("waterPoof").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { WATER_POOF_UPPER_LIMIT = 200; }
      }
      if (waterPoof != null && !wt.fc.PersistenceHelper.checkStoredLength(waterPoof.toString(), WATER_POOF_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "waterPoof"), java.lang.String.valueOf(java.lang.Math.min(WATER_POOF_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "waterPoof", this.waterPoof, waterPoof));
   }

   /**
    * 시리즈
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String SERIES = "series";
   static int SERIES_UPPER_LIMIT = -1;
   java.lang.String series;
   /**
    * 시리즈
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getSeries() {
      return series;
   }
   /**
    * 시리즈
    *
    * @see e3ps.project.TemplateProject
    */
   public void setSeries(java.lang.String series) throws wt.util.WTPropertyVetoException {
      seriesValidate(series);
      this.series = series;
   }
   void seriesValidate(java.lang.String series) throws wt.util.WTPropertyVetoException {
      if (SERIES_UPPER_LIMIT < 1) {
         try { SERIES_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("series").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { SERIES_UPPER_LIMIT = 200; }
      }
      if (series != null && !wt.fc.PersistenceHelper.checkStoredLength(series.toString(), SERIES_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "series"), java.lang.String.valueOf(java.lang.Math.min(SERIES_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "series", this.series, series));
   }

   /**
    * 프로젝트 이력 Iteration≪≫≪≫0 : 프로젝트 이력(pjtHistory) 최초 생성 시, 결재 프로세스 통해서 일정 변경 시≪≫1~N : 결재 프로세스 없이 일정 변경 시
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_ITERATION = "pjtIteration";
   int pjtIteration;
   /**
    * 프로젝트 이력 Iteration≪≫≪≫0 : 프로젝트 이력(pjtHistory) 최초 생성 시, 결재 프로세스 통해서 일정 변경 시≪≫1~N : 결재 프로세스 없이 일정 변경 시
    *
    * @see e3ps.project.TemplateProject
    */
   public int getPjtIteration() {
      return pjtIteration;
   }
   /**
    * 프로젝트 이력 Iteration≪≫≪≫0 : 프로젝트 이력(pjtHistory) 최초 생성 시, 결재 프로세스 통해서 일정 변경 시≪≫1~N : 결재 프로세스 없이 일정 변경 시
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtIteration(int pjtIteration) throws wt.util.WTPropertyVetoException {
      pjtIterationValidate(pjtIteration);
      this.pjtIteration = pjtIteration;
   }
   void pjtIterationValidate(int pjtIteration) throws wt.util.WTPropertyVetoException {
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String HISTORY_ROLE = "historyRole";
   static int HISTORY_ROLE_UPPER_LIMIT = -1;
   java.lang.String historyRole;
   /**
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getHistoryRole() {
      return historyRole;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setHistoryRole(java.lang.String historyRole) throws wt.util.WTPropertyVetoException {
      historyRoleValidate(historyRole);
      this.historyRole = historyRole;
   }
   void historyRoleValidate(java.lang.String historyRole) throws wt.util.WTPropertyVetoException {
      if (HISTORY_ROLE_UPPER_LIMIT < 1) {
         try { HISTORY_ROLE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("historyRole").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { HISTORY_ROLE_UPPER_LIMIT = 2000; }
      }
      if (historyRole != null && !wt.fc.PersistenceHelper.checkStoredLength(historyRole.toString(), HISTORY_ROLE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "historyRole"), java.lang.String.valueOf(java.lang.Math.min(HISTORY_ROLE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "historyRole", this.historyRole, historyRole));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PRODUCT_TYPE_LEVEL2 = "productTypeLevel2";
   static int PRODUCT_TYPE_LEVEL2_UPPER_LIMIT = -1;
   java.lang.String productTypeLevel2;
   /**
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getProductTypeLevel2() {
      return productTypeLevel2;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setProductTypeLevel2(java.lang.String productTypeLevel2) throws wt.util.WTPropertyVetoException {
      productTypeLevel2Validate(productTypeLevel2);
      this.productTypeLevel2 = productTypeLevel2;
   }
   void productTypeLevel2Validate(java.lang.String productTypeLevel2) throws wt.util.WTPropertyVetoException {
      if (PRODUCT_TYPE_LEVEL2_UPPER_LIMIT < 1) {
         try { PRODUCT_TYPE_LEVEL2_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("productTypeLevel2").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { PRODUCT_TYPE_LEVEL2_UPPER_LIMIT = 200; }
      }
      if (productTypeLevel2 != null && !wt.fc.PersistenceHelper.checkStoredLength(productTypeLevel2.toString(), PRODUCT_TYPE_LEVEL2_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "productTypeLevel2"), java.lang.String.valueOf(java.lang.Math.min(PRODUCT_TYPE_LEVEL2_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "productTypeLevel2", this.productTypeLevel2, productTypeLevel2));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String MODIFIER = "modifier";
   wt.org.WTPrincipalReference modifier;
   /**
    * @see e3ps.project.TemplateProject
    */
   public wt.org.WTPrincipalReference getModifier() {
      return modifier;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setModifier(wt.org.WTPrincipalReference modifier) throws wt.util.WTPropertyVetoException {
      modifierValidate(modifier);
      this.modifier = modifier;
   }
   void modifierValidate(wt.org.WTPrincipalReference modifier) throws wt.util.WTPropertyVetoException {
      if (modifier == null)
         throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "modifier") },
               new java.beans.PropertyChangeEvent(this, "modifier", this.modifier, modifier));
   }

   /**
    * 상태
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String ACTIVE_TYPE = "activeType";
   static int ACTIVE_TYPE_UPPER_LIMIT = -1;
   java.lang.String activeType;
   /**
    * 상태
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getActiveType() {
      return activeType;
   }
   /**
    * 상태
    *
    * @see e3ps.project.TemplateProject
    */
   public void setActiveType(java.lang.String activeType) throws wt.util.WTPropertyVetoException {
      activeTypeValidate(activeType);
      this.activeType = activeType;
   }
   void activeTypeValidate(java.lang.String activeType) throws wt.util.WTPropertyVetoException {
      if (ACTIVE_TYPE_UPPER_LIMIT < 1) {
         try { ACTIVE_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("activeType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { ACTIVE_TYPE_UPPER_LIMIT = 200; }
      }
      if (activeType != null && !wt.fc.PersistenceHelper.checkStoredLength(activeType.toString(), ACTIVE_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "activeType"), java.lang.String.valueOf(java.lang.Math.min(ACTIVE_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "activeType", this.activeType, activeType));
   }

   /**
    * 개발구분
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String DEV_TYPE = "devType";
   static int DEV_TYPE_UPPER_LIMIT = -1;
   java.lang.String devType;
   /**
    * 개발구분
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getDevType() {
      return devType;
   }
   /**
    * 개발구분
    *
    * @see e3ps.project.TemplateProject
    */
   public void setDevType(java.lang.String devType) throws wt.util.WTPropertyVetoException {
      devTypeValidate(devType);
      this.devType = devType;
   }
   void devTypeValidate(java.lang.String devType) throws wt.util.WTPropertyVetoException {
      if (DEV_TYPE_UPPER_LIMIT < 1) {
         try { DEV_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_TYPE_UPPER_LIMIT = 200; }
      }
      if (devType != null && !wt.fc.PersistenceHelper.checkStoredLength(devType.toString(), DEV_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devType"), java.lang.String.valueOf(java.lang.Math.min(DEV_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devType", this.devType, devType));
   }

   /**
    * 고객처
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String CLIENT_COMPANY = "clientCompany";
   static int CLIENT_COMPANY_UPPER_LIMIT = -1;
   java.lang.String clientCompany;
   /**
    * 고객처
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getClientCompany() {
      return clientCompany;
   }
   /**
    * 고객처
    *
    * @see e3ps.project.TemplateProject
    */
   public void setClientCompany(java.lang.String clientCompany) throws wt.util.WTPropertyVetoException {
      clientCompanyValidate(clientCompany);
      this.clientCompany = clientCompany;
   }
   void clientCompanyValidate(java.lang.String clientCompany) throws wt.util.WTPropertyVetoException {
      if (CLIENT_COMPANY_UPPER_LIMIT < 1) {
         try { CLIENT_COMPANY_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("clientCompany").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { CLIENT_COMPANY_UPPER_LIMIT = 200; }
      }
      if (clientCompany != null && !wt.fc.PersistenceHelper.checkStoredLength(clientCompany.toString(), CLIENT_COMPANY_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "clientCompany"), java.lang.String.valueOf(java.lang.Math.min(CLIENT_COMPANY_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "clientCompany", this.clientCompany, clientCompany));
   }

   /**
    * 개발단계
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String DEV_STEP = "devStep";
   static int DEV_STEP_UPPER_LIMIT = -1;
   java.lang.String devStep;
   /**
    * 개발단계
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getDevStep() {
      return devStep;
   }
   /**
    * 개발단계
    *
    * @see e3ps.project.TemplateProject
    */
   public void setDevStep(java.lang.String devStep) throws wt.util.WTPropertyVetoException {
      devStepValidate(devStep);
      this.devStep = devStep;
   }
   void devStepValidate(java.lang.String devStep) throws wt.util.WTPropertyVetoException {
      if (DEV_STEP_UPPER_LIMIT < 1) {
         try { DEV_STEP_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("devStep").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DEV_STEP_UPPER_LIMIT = 200; }
      }
      if (devStep != null && !wt.fc.PersistenceHelper.checkStoredLength(devStep.toString(), DEV_STEP_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "devStep"), java.lang.String.valueOf(java.lang.Math.min(DEV_STEP_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "devStep", this.devStep, devStep));
   }

   /**
    * 제작처
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String MAKE_OFFICE = "makeOffice";
   static int MAKE_OFFICE_UPPER_LIMIT = -1;
   java.lang.String makeOffice;
   /**
    * 제작처
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getMakeOffice() {
      return makeOffice;
   }
   /**
    * 제작처
    *
    * @see e3ps.project.TemplateProject
    */
   public void setMakeOffice(java.lang.String makeOffice) throws wt.util.WTPropertyVetoException {
      makeOfficeValidate(makeOffice);
      this.makeOffice = makeOffice;
   }
   void makeOfficeValidate(java.lang.String makeOffice) throws wt.util.WTPropertyVetoException {
      if (MAKE_OFFICE_UPPER_LIMIT < 1) {
         try { MAKE_OFFICE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("makeOffice").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAKE_OFFICE_UPPER_LIMIT = 200; }
      }
      if (makeOffice != null && !wt.fc.PersistenceHelper.checkStoredLength(makeOffice.toString(), MAKE_OFFICE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "makeOffice"), java.lang.String.valueOf(java.lang.Math.min(MAKE_OFFICE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "makeOffice", this.makeOffice, makeOffice));
   }

   /**
    * 금형구분
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String MOLD_TYPE = "moldType";
   static int MOLD_TYPE_UPPER_LIMIT = -1;
   java.lang.String moldType;
   /**
    * 금형구분
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getMoldType() {
      return moldType;
   }
   /**
    * 금형구분
    *
    * @see e3ps.project.TemplateProject
    */
   public void setMoldType(java.lang.String moldType) throws wt.util.WTPropertyVetoException {
      moldTypeValidate(moldType);
      this.moldType = moldType;
   }
   void moldTypeValidate(java.lang.String moldType) throws wt.util.WTPropertyVetoException {
      if (MOLD_TYPE_UPPER_LIMIT < 1) {
         try { MOLD_TYPE_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("moldType").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MOLD_TYPE_UPPER_LIMIT = 200; }
      }
      if (moldType != null && !wt.fc.PersistenceHelper.checkStoredLength(moldType.toString(), MOLD_TYPE_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "moldType"), java.lang.String.valueOf(java.lang.Math.min(MOLD_TYPE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "moldType", this.moldType, moldType));
   }

   /**
    * 제작구분
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String MAKING = "making";
   static int MAKING_UPPER_LIMIT = -1;
   java.lang.String making;
   /**
    * 제작구분
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getMaking() {
      return making;
   }
   /**
    * 제작구분
    *
    * @see e3ps.project.TemplateProject
    */
   public void setMaking(java.lang.String making) throws wt.util.WTPropertyVetoException {
      makingValidate(making);
      this.making = making;
   }
   void makingValidate(java.lang.String making) throws wt.util.WTPropertyVetoException {
      if (MAKING_UPPER_LIMIT < 1) {
         try { MAKING_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("making").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { MAKING_UPPER_LIMIT = 200; }
      }
      if (making != null && !wt.fc.PersistenceHelper.checkStoredLength(making.toString(), MAKING_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "making"), java.lang.String.valueOf(java.lang.Math.min(MAKING_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "making", this.making, making));
   }

   /**
    * 사업부
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String DIVISION = "division";
   static int DIVISION_UPPER_LIMIT = -1;
   java.lang.String division;
   /**
    * 사업부
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getDivision() {
      return division;
   }
   /**
    * 사업부
    *
    * @see e3ps.project.TemplateProject
    */
   public void setDivision(java.lang.String division) throws wt.util.WTPropertyVetoException {
      divisionValidate(division);
      this.division = division;
   }
   void divisionValidate(java.lang.String division) throws wt.util.WTPropertyVetoException {
      if (DIVISION_UPPER_LIMIT < 1) {
         try { DIVISION_UPPER_LIMIT = (java.lang.Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("division").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT); }
         catch (wt.introspection.WTIntrospectionException e) { DIVISION_UPPER_LIMIT = 200; }
      }
      if (division != null && !wt.fc.PersistenceHelper.checkStoredLength(division.toString(), DIVISION_UPPER_LIMIT, true))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "division"), java.lang.String.valueOf(java.lang.Math.min(DIVISION_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE/wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR)) },
               new java.beans.PropertyChangeEvent(this, "division", this.division, division));
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_NO = "master>pjtNo";
   /**
    * Derived from {@link e3ps.project.ProjectMaster#getPjtNo()}
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getPjtNo() {
      try { return (java.lang.String) ((e3ps.project.ProjectMaster) getMaster()).getPjtNo(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }
   /**
    * Derived from {@link e3ps.project.ProjectMaster#setPjtNo(java.lang.String)}
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtNo(java.lang.String pjtNo) throws wt.util.WTPropertyVetoException {
      ((e3ps.project.ProjectMaster) getMaster()).setPjtNo(pjtNo);
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_NAME = "master>pjtName";
   /**
    * Derived from {@link e3ps.project.ProjectMaster#getPjtName()}
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getPjtName() {
      try { return (java.lang.String) ((e3ps.project.ProjectMaster) getMaster()).getPjtName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }
   /**
    * Derived from {@link e3ps.project.ProjectMaster#setPjtName(java.lang.String)}
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtName(java.lang.String pjtName) throws wt.util.WTPropertyVetoException {
      ((e3ps.project.ProjectMaster) getMaster()).setPjtName(pjtName);
   }

   /**
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String PJT_TYPE_NAME = "master>pjtTypeName";
   /**
    * Derived from {@link e3ps.project.ProjectMaster#getPjtTypeName()}
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public java.lang.String getPjtTypeName() {
      try { return (java.lang.String) ((e3ps.project.ProjectMaster) getMaster()).getPjtTypeName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }
   /**
    * Derived from {@link e3ps.project.ProjectMaster#setPjtTypeName(java.lang.String)}
    * <p>
    * <b>Supported API: </b>true
    *
    * @see e3ps.project.TemplateProject
    */
   public void setPjtTypeName(java.lang.String pjtTypeName) throws wt.util.WTPropertyVetoException {
      ((e3ps.project.ProjectMaster) getMaster()).setPjtTypeName(pjtTypeName);
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String MASTER = "master";
   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String MASTER_REFERENCE = "masterReference";
   wt.fc.ObjectReference masterReference;
   /**
    * @see e3ps.project.TemplateProject
    */
   public e3ps.project.ProjectMaster getMaster() {
      return (masterReference != null) ? (e3ps.project.ProjectMaster) masterReference.getObject() : null;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public wt.fc.ObjectReference getMasterReference() {
      return masterReference;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setMaster(e3ps.project.ProjectMaster the_master) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setMasterReference(the_master == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.ProjectMaster) the_master));
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setMasterReference(wt.fc.ObjectReference the_masterReference) throws wt.util.WTPropertyVetoException {
      masterReferenceValidate(the_masterReference);
      masterReference = (wt.fc.ObjectReference) the_masterReference;
   }
   void masterReferenceValidate(wt.fc.ObjectReference the_masterReference) throws wt.util.WTPropertyVetoException {
      if (the_masterReference == null || the_masterReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterReference") },
               new java.beans.PropertyChangeEvent(this, "masterReference", this.masterReference, masterReference));
      if (the_masterReference != null && the_masterReference.getReferencedClass() != null &&
            !e3ps.project.ProjectMaster.class.isAssignableFrom(the_masterReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "masterReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "masterReference", this.masterReference, masterReference));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String OUTPUT_TYPE = "outputType";
   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String OUTPUT_TYPE_REFERENCE = "outputTypeReference";
   wt.fc.ObjectReference outputTypeReference;
   /**
    * @see e3ps.project.TemplateProject
    */
   public e3ps.project.outputtype.ProjectOutPutType getOutputType() {
      return (outputTypeReference != null) ? (e3ps.project.outputtype.ProjectOutPutType) outputTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public wt.fc.ObjectReference getOutputTypeReference() {
      return outputTypeReference;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setOutputType(e3ps.project.outputtype.ProjectOutPutType the_outputType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOutputTypeReference(the_outputType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.ProjectOutPutType) the_outputType));
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setOutputTypeReference(wt.fc.ObjectReference the_outputTypeReference) throws wt.util.WTPropertyVetoException {
      outputTypeReferenceValidate(the_outputTypeReference);
      outputTypeReference = (wt.fc.ObjectReference) the_outputTypeReference;
   }
   void outputTypeReferenceValidate(wt.fc.ObjectReference the_outputTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_outputTypeReference == null || the_outputTypeReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputTypeReference") },
               new java.beans.PropertyChangeEvent(this, "outputTypeReference", this.outputTypeReference, outputTypeReference));
      if (the_outputTypeReference != null && the_outputTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.ProjectOutPutType.class.isAssignableFrom(the_outputTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "outputTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "outputTypeReference", this.outputTypeReference, outputTypeReference));
   }

   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String OEM_TYPE = "oemType";
   /**
    * @see e3ps.project.TemplateProject
    */
   public static final java.lang.String OEM_TYPE_REFERENCE = "oemTypeReference";
   wt.fc.ObjectReference oemTypeReference;
   /**
    * @see e3ps.project.TemplateProject
    */
   public e3ps.project.outputtype.OEMProjectType getOemType() {
      return (oemTypeReference != null) ? (e3ps.project.outputtype.OEMProjectType) oemTypeReference.getObject() : null;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public wt.fc.ObjectReference getOemTypeReference() {
      return oemTypeReference;
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setOemType(e3ps.project.outputtype.OEMProjectType the_oemType) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setOemTypeReference(the_oemType == null ? null : wt.fc.ObjectReference.newObjectReference((e3ps.project.outputtype.OEMProjectType) the_oemType));
   }
   /**
    * @see e3ps.project.TemplateProject
    */
   public void setOemTypeReference(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      oemTypeReferenceValidate(the_oemTypeReference);
      oemTypeReference = (wt.fc.ObjectReference) the_oemTypeReference;
   }
   void oemTypeReferenceValidate(wt.fc.ObjectReference the_oemTypeReference) throws wt.util.WTPropertyVetoException {
      if (the_oemTypeReference != null && the_oemTypeReference.getReferencedClass() != null &&
            !e3ps.project.outputtype.OEMProjectType.class.isAssignableFrom(the_oemTypeReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "oemTypeReference"), "ObjectReference" },
               new java.beans.PropertyChangeEvent(this, "oemTypeReference", this.oemTypeReference, oemTypeReference));
   }

   /**
    * Derived from {@link wt.inf.container.WTContainerRef#getName()}
    *
    * @see wt.inf.container.WTContained
    */
   public java.lang.String getContainerName() {
      try { return (java.lang.String) ((wt.inf.container.WTContainerRef) getContainerReference()).getName(); }
      catch (java.lang.NullPointerException npe) { return null; }
   }

   wt.inf.container.WTContainerRef containerReference;
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainer getContainer() {
      return (containerReference != null) ? (wt.inf.container.WTContainer) containerReference.getObject() : null;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public wt.inf.container.WTContainerRef getContainerReference() {
      return containerReference;
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainer(wt.inf.container.WTContainer the_container) throws wt.util.WTPropertyVetoException, wt.util.WTException {
      setContainerReference(the_container == null ? null : wt.inf.container.WTContainerRef.newWTContainerRef((wt.inf.container.WTContainer) the_container));
   }
   /**
    * @see wt.inf.container.WTContained
    */
   public void setContainerReference(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      containerReferenceValidate(the_containerReference);
      containerReference = (wt.inf.container.WTContainerRef) the_containerReference;
   }
   void containerReferenceValidate(wt.inf.container.WTContainerRef the_containerReference) throws wt.util.WTPropertyVetoException {
      if (the_containerReference == null || the_containerReference.getReferencedClass() == null)
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.REQUIRED_ATTRIBUTE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference") },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
      if (the_containerReference != null && the_containerReference.getReferencedClass() != null &&
            !wt.inf.container.WTContainer.class.isAssignableFrom(the_containerReference.getReferencedClass()))
         throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.WRONG_TYPE,
               new java.lang.Object[] { new wt.introspection.PropertyDisplayName(CLASSNAME, "containerReference"), "WTContainerRef" },
               new java.beans.PropertyChangeEvent(this, "containerReference", this.containerReference, containerReference));
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

   public static final long EXTERNALIZATION_VERSION_UID = -4439125743726952906L;

   public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
      output.writeLong( EXTERNALIZATION_VERSION_UID );

      super.writeExternal( output );

      output.writeObject( activeType );
      output.writeBoolean( checkOut );
      output.writeObject( checkOutState );
      output.writeObject( clientCompany );
      output.writeObject( containerReference );
      output.writeObject( devStep );
      output.writeObject( devType );
      output.writeBoolean( diffPLM );
      output.writeObject( division );
      output.writeObject( historyNote );
      output.writeInt( historyNoteType );
      output.writeObject( historyNoteWebEditor );
      output.writeObject( historyNoteWebEditorText );
      output.writeObject( historyRole );
      output.writeBoolean( isDirection );
      output.writeBoolean( lastest );
      output.writeObject( makeOffice );
      output.writeObject( making );
      output.writeObject( masterReference );
      output.writeObject( modifier );
      output.writeObject( moldType );
      output.writeObject( oemTypeReference );
      output.writeObject( orderName );
      output.writeObject( outputTypeReference );
      output.writeObject( pjtDesc );
      output.writeInt( pjtHistory );
      output.writeObject( pjtID );
      output.writeInt( pjtIteration );
      output.writeObject( pjtSchedule );
      output.writeInt( pjtSeq );
      output.writeInt( pjtType );
      output.writeObject( productTypeLevel2 );
      output.writeObject( reStartDetil );
      output.writeObject( series );
      output.writeObject( stopedDetil );
      output.writeObject( waterPoof );
      output.writeBoolean( workingCopy );

      if (!(output instanceof wt.pds.PDSObjectOutput)) {
         output.writeObject( contentVector );
         output.writeBoolean( hasContents );
         output.writeObject( httpVector );
         output.writeObject( operation );
      }

   }

   protected void super_writeExternal_TemplateProject(java.io.ObjectOutput output) throws java.io.IOException {
      super.writeExternal(output);
   }

   public void readExternal(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      long readSerialVersionUID = input.readLong();
      readVersion( (e3ps.project.TemplateProject) this, input, readSerialVersionUID, false, false );
   }
   protected void super_readExternal_TemplateProject(java.io.ObjectInput input) throws java.io.IOException, java.lang.ClassNotFoundException {
      super.readExternal(input);
   }

   public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.writeExternal( output );

      output.setString( "activeType", activeType );
      output.setBoolean( "checkOut", checkOut );
      output.setString( "checkOutState", checkOutState );
      output.setString( "clientCompany", clientCompany );
      output.writeObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      output.setString( "devStep", devStep );
      output.setString( "devType", devType );
      output.setBoolean( "diffPLM", diffPLM );
      output.setString( "division", division );
      output.setString( "historyNote", historyNote );
      output.setInt( "historyNoteType", historyNoteType );
      output.setObject( "historyNoteWebEditor", historyNoteWebEditor );
      output.setObject( "historyNoteWebEditorText", historyNoteWebEditorText );
      output.setString( "historyRole", historyRole );
      output.setBoolean( "isDirection", isDirection );
      output.setBoolean( "lastest", lastest );
      output.setString( "makeOffice", makeOffice );
      output.setString( "making", making );
      output.writeObject( "masterReference", masterReference, wt.fc.ObjectReference.class, true );
      output.writeObject( "modifier", modifier, wt.org.WTPrincipalReference.class, true );
      output.setString( "moldType", moldType );
      output.writeObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "orderName", orderName );
      output.writeObject( "outputTypeReference", outputTypeReference, wt.fc.ObjectReference.class, true );
      output.setString( "pjtDesc", pjtDesc );
      output.setInt( "pjtHistory", pjtHistory );
      output.setString( "pjtID", pjtID );
      output.setInt( "pjtIteration", pjtIteration );
      output.writeObject( "pjtSchedule", pjtSchedule, wt.fc.ObjectReference.class, true );
      output.setInt( "pjtSeq", pjtSeq );
      output.setInt( "pjtType", pjtType );
      output.setString( "productTypeLevel2", productTypeLevel2 );
      output.setString( "reStartDetil", reStartDetil );
      output.setString( "series", series );
      output.setString( "stopedDetil", stopedDetil );
      output.setString( "waterPoof", waterPoof );
      output.setBoolean( "workingCopy", workingCopy );
   }

   public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
      super.readExternal( input );

      activeType = input.getString( "activeType" );
      checkOut = input.getBoolean( "checkOut" );
      checkOutState = input.getString( "checkOutState" );
      clientCompany = input.getString( "clientCompany" );
      containerReference = (wt.inf.container.WTContainerRef) input.readObject( "containerReference", containerReference, wt.inf.container.WTContainerRef.class, true );
      devStep = input.getString( "devStep" );
      devType = input.getString( "devType" );
      diffPLM = input.getBoolean( "diffPLM" );
      division = input.getString( "division" );
      historyNote = input.getString( "historyNote" );
      historyNoteType = input.getInt( "historyNoteType" );
      historyNoteWebEditor = (java.lang.Object) input.getObject( "historyNoteWebEditor" );
      historyNoteWebEditorText = (java.lang.Object) input.getObject( "historyNoteWebEditorText" );
      historyRole = input.getString( "historyRole" );
      isDirection = input.getBoolean( "isDirection" );
      lastest = input.getBoolean( "lastest" );
      makeOffice = input.getString( "makeOffice" );
      making = input.getString( "making" );
      masterReference = (wt.fc.ObjectReference) input.readObject( "masterReference", masterReference, wt.fc.ObjectReference.class, true );
      modifier = (wt.org.WTPrincipalReference) input.readObject( "modifier", modifier, wt.org.WTPrincipalReference.class, true );
      moldType = input.getString( "moldType" );
      oemTypeReference = (wt.fc.ObjectReference) input.readObject( "oemTypeReference", oemTypeReference, wt.fc.ObjectReference.class, true );
      orderName = input.getString( "orderName" );
      outputTypeReference = (wt.fc.ObjectReference) input.readObject( "outputTypeReference", outputTypeReference, wt.fc.ObjectReference.class, true );
      pjtDesc = input.getString( "pjtDesc" );
      pjtHistory = input.getInt( "pjtHistory" );
      pjtID = input.getString( "pjtID" );
      pjtIteration = input.getInt( "pjtIteration" );
      pjtSchedule = (wt.fc.ObjectReference) input.readObject( "pjtSchedule", pjtSchedule, wt.fc.ObjectReference.class, true );
      pjtSeq = input.getInt( "pjtSeq" );
      pjtType = input.getInt( "pjtType" );
      productTypeLevel2 = input.getString( "productTypeLevel2" );
      reStartDetil = input.getString( "reStartDetil" );
      series = input.getString( "series" );
      stopedDetil = input.getString( "stopedDetil" );
      waterPoof = input.getString( "waterPoof" );
      workingCopy = input.getBoolean( "workingCopy" );
   }

   boolean readVersion_4439125743726952906L( java.io.ObjectInput input, long readSerialVersionUID, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      if ( !superDone )
         super.readExternal( input );

      activeType = (java.lang.String) input.readObject();
      checkOut = input.readBoolean();
      checkOutState = (java.lang.String) input.readObject();
      clientCompany = (java.lang.String) input.readObject();
      containerReference = (wt.inf.container.WTContainerRef) input.readObject();
      devStep = (java.lang.String) input.readObject();
      devType = (java.lang.String) input.readObject();
      diffPLM = input.readBoolean();
      division = (java.lang.String) input.readObject();
      historyNote = (java.lang.String) input.readObject();
      historyNoteType = input.readInt();
      historyNoteWebEditor = (java.lang.Object) input.readObject();
      historyNoteWebEditorText = (java.lang.Object) input.readObject();
      historyRole = (java.lang.String) input.readObject();
      isDirection = input.readBoolean();
      lastest = input.readBoolean();
      makeOffice = (java.lang.String) input.readObject();
      making = (java.lang.String) input.readObject();
      masterReference = (wt.fc.ObjectReference) input.readObject();
      modifier = (wt.org.WTPrincipalReference) input.readObject();
      moldType = (java.lang.String) input.readObject();
      oemTypeReference = (wt.fc.ObjectReference) input.readObject();
      orderName = (java.lang.String) input.readObject();
      outputTypeReference = (wt.fc.ObjectReference) input.readObject();
      pjtDesc = (java.lang.String) input.readObject();
      pjtHistory = input.readInt();
      pjtID = (java.lang.String) input.readObject();
      pjtIteration = input.readInt();
      pjtSchedule = (wt.fc.ObjectReference) input.readObject();
      pjtSeq = input.readInt();
      pjtType = input.readInt();
      productTypeLevel2 = (java.lang.String) input.readObject();
      reStartDetil = (java.lang.String) input.readObject();
      series = (java.lang.String) input.readObject();
      stopedDetil = (java.lang.String) input.readObject();
      waterPoof = (java.lang.String) input.readObject();
      workingCopy = input.readBoolean();

      if (!(input instanceof wt.pds.PDSObjectInput)) {
            contentVector = (java.util.Vector) input.readObject();
            hasContents = input.readBoolean();
            httpVector = (java.util.Vector) input.readObject();
            operation = (wt.content.HttpContentOperation) input.readObject();
      }

      return true;
   }

   protected boolean readVersion( TemplateProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      boolean success = true;

      if ( readSerialVersionUID == EXTERNALIZATION_VERSION_UID )
         return readVersion_4439125743726952906L( input, readSerialVersionUID, superDone );
      else
         success = readOldVersion( input, readSerialVersionUID, passThrough, superDone );

      if (input instanceof wt.pds.PDSObjectInput)
         wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

      return success;
   }
   protected boolean super_readVersion_TemplateProject( _TemplateProject thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
   }

   boolean readOldVersion( java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone ) throws java.io.IOException, java.lang.ClassNotFoundException {
      throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID="+readSerialVersionUID+" local class externalizationVersionUID="+EXTERNALIZATION_VERSION_UID);
   }
}
