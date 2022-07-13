<%@page import="wt.epm.EPMDocument"%>
<%@page import="e3ps.dms.entity.KETProjectDocument"%>
<%@page import="e3ps.wfm.util.WorkflowSearchHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="wt.enterprise.RevisionControlled"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  try{
%>
<%@page contentType="text/html; charset=UTF-8" buffer="16kb"%>
<%@page import = "wt.fc.*,
          wt.org.*,
          wt.vc.*,
          wt.vc.wip.*,
          java.util.*,
          java.text.*"%>
<%@page import="e3ps.groupware.company.*,
          e3ps.project.*,
          e3ps.project.beans.*,
          e3ps.common.util.*" %>


<%
  String oid =  request.getParameter("oid");
  String oldOid =  request.getParameter("compareTaskOid");


  E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);
  E3PSProject project = (E3PSProject)task.getProject();
  //out.println(project.getPjtType());
  String projectOid = CommonUtil.getOIDString(project);
  WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();

    // [START] [PLM System 1차개선] [수정] 선후행 관계 체크하여 산출물 진행률 입력 가능 여부 판단(FS는 입력 불가, SS는 입력 가능), 2013-08-13, BoLee
    boolean dependCheck = false;

    QueryResult dependListResult = TaskDependencyHelper.manager.getDependTaskList(task);
    TaskDependencyLink dependLink = null;
    E3PSTask ancestorTask = null;
    E3PSTaskData dependTaskData = null;
    ExtendScheduleData taskSchedule = null;

    while ( dependListResult.hasMoreElements() ) {

        dependLink = (TaskDependencyLink)dependListResult.nextElement();
        ancestorTask = (E3PSTask)dependLink.getDependTarget();

        dependTaskData = new E3PSTaskData(ancestorTask);

        taskSchedule = (ExtendScheduleData)task.getTaskSchedule().getObject();

        // 선행 Task가 진행중(0)이고 선후행 관계가 FS일 경우 산출물 진행률 입력 불가
        if ( dependTaskData.taskState == 0
            && (DateUtil.getTimeFormat(taskSchedule.getPlanStartDate(), "yyyy-MM-dd")).compareTo(DateUtil.getTimeFormat(dependTaskData.taskPlanEndDate, "yyyy-MM-dd")) > 0 ) {

            dependCheck = true;
        }
    }
    // [END] [PLM System 1차개선] [수정] 선후행 관계 체크하여 산출물 진행률 입력 가능 여부 판단(FS는 입력 불가, SS는 입력 가능), 2013-08-13, BoLee

  // 제품측정현황 정보
  String checkDescPoint = task.getCheckDescPoint();
  String nonPassPoint = task.getNonPassPoint();
  String checkResult = task.getCheckResult();
  String checkEtc = task.getCheckEtc();

  if(checkDescPoint == null){
    checkDescPoint = "";
  }

  if(nonPassPoint == null){
    nonPassPoint = "";
  }

  if(checkResult == null){
    checkResult = "";
  }

  if(checkEtc == null){
    checkEtc = "";
  }


  boolean isDate = false;//실제시작일 여부
  ExtendScheduleData testSchedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
  String ExeDate = request.getParameter("ExeDate");
  if(testSchedule.getExecStartDate() != null || ExeDate != null){
    isDate = true;
  }

  //태스크 링크 정보 수정
  String cmd = request.getParameter("cmd");
  if("linkModify".equals(cmd)){
    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);
    if(dependList != null) {
      Vector v = new Vector();
      while ( dependList.hasMoreElements() ) {
        TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
        String linkOid = CommonUtil.getOIDString(link);
        long loid = CommonUtil.getOIDLongValue(linkOid);
        String delayTime = request.getParameter(String.valueOf(loid));
        link.setDelayDuration(Integer.parseInt(delayTime));
        v.add(link);
      }
      ProjectTaskHelper.manager.updateTaskFromDependencyLink(task, v);
    }
  }
  try {
    String addTask = request.getParameter("addTask");
    if ( addTask != null && addTask.length() > 0) {
      TemplateTask dependTask = (TemplateTask)CommonUtil.getObject(addTask);
      if ( dependTask != null ) TaskDependencyHelper.manager.setDependTask(task, dependTask);
    }

    String deleteTask = request.getParameter("deleteTask");
    if ( deleteTask != null && deleteTask.length() > 0) {
      TaskDependencyLink link = (TaskDependencyLink)CommonUtil.getObject(deleteTask);
      if ( link != null ) TaskDependencyHelper.manager.deleteDependTask(task,link);
    }
  } catch (Exception ex) {
      Kogger.error(ex);
  }

  // 진행현황 % 입력
  String completion = request.getParameter("setCompletion");
  TaskViewButtonAuth buttonAuth = new TaskViewButtonAuth(task);
  if ( completion != null ) {
    try {
      int com = StringUtil.parseInt(completion, 0);
      if(com != 100){
        task.setTaskCompletion(com);
      }
      if(buttonAuth.isCompletionButton || CommonUtil.isAdmin() ){
        try{
          task = (E3PSTask)ProjectTaskHelper.updateCompletion(task);
        }catch(Exception ex){
            Kogger.error(ex);
        }
        task = (E3PSTask)PersistenceHelper.manager.refresh(task);

        buttonAuth = new TaskViewButtonAuth(task);

      }

    } catch ( Exception e ) { Kogger.error(e);}
  }



  // Admin 권한 실제 시작일 / 실제 종료일 설정

  String toDay = DateUtil.getToDay("yyyy-MM-dd");
  if("ExeDate".equals(ExeDate)){
    String taskExecStartDate = request.getParameter("taskExecStartDate");
    String taskExecEndDate = request.getParameter("taskExecEndDate");
    Calendar tempCal = Calendar.getInstance();
    ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
    boolean isException = false;
    String message = "";
    if(taskExecStartDate != null && taskExecStartDate.length() > 0){
      if(schedule.getPlanStartDate() != null){
        isException = true;
        message = messageService.getString("e3ps.message.ket_message", "03366")/*이미 실제 시작일이 입력 되어 있습니다.*/;
      }else if(toDay.compareTo(taskExecStartDate) < 0){
        isException = true;
        message = messageService.getString("e3ps.message.ket_message", "03367")/*미래 일자는 시작일이 될 수 없습니다.*/;
        return;
           }
    }
    // 실제 시작일
    if(taskExecStartDate != null && taskExecStartDate.length() > 0){
      tempCal.setTime(DateUtil.parseDateStr((String)taskExecStartDate));
      schedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
      schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
    }else{
      schedule.setExecStartDate(null);
      schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
    }
    // 실제 종료일
    if(taskExecEndDate != null && taskExecEndDate.length() > 0){
      tempCal.setTime(DateUtil.parseDateStr((String)taskExecEndDate));
      schedule.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
      schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
    }else{
      schedule.setExecEndDate(null);
      schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
    }
    try{
      task = (E3PSTask)ProjectTaskHelper.updateCompletion(task);
    }catch(Exception ex){
	Kogger.error(ex);
    }
    task = (E3PSTask)PersistenceHelper.manager.refresh(task);
    buttonAuth = new TaskViewButtonAuth(task);
  }

  // 종료 버튼 클릭시 종료일 설정
  String TaskAuto = request.getParameter("TaskAuto");
  if("Auto".equals(TaskAuto)){

    String TaskAutoStartDate = request.getParameter("TaskAutoStartDate");
    String TaskAutoEndDate = request.getParameter("TaskAutoEndDate");
    String AutoType = request.getParameter("AutoType");
    Calendar tempCal = Calendar.getInstance();

    ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();

    if(AutoType.equals("start")){
      if(TaskAutoStartDate != null && TaskAutoStartDate.length()>0){
        tempCal.setTime(DateUtil.parseDateStr((String)TaskAutoStartDate));
        schedule.setExecStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
        schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
      }
    }else{
      if(TaskAutoEndDate != null && TaskAutoEndDate.length() > 0){
        tempCal.setTime(DateUtil.parseDateStr((String)TaskAutoEndDate));
        schedule.setExecEndDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
        schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
      }
    }
    try{
      task = (E3PSTask)ProjectTaskHelper.updateCompletion(task);
    }catch(Exception ex){
	Kogger.error(ex);
    }
    task = (E3PSTask)PersistenceHelper.manager.refresh(task);
    buttonAuth = new TaskViewButtonAuth(task);
  }

  // Tean 가져오기
  String teamName = "";
  String pjtType = "";
  if(project != null){
    teamName = ProjectHelper.getProjectTeam(project.getPjtType());
    pjtType = "" + project.getPjtType();
  }
  TeamTemplate tempTeam = null;
  tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), teamName);
  Vector vecTeamStd = new Vector();
  if(tempTeam != null) {
    vecTeamStd = tempTeam.getRoles();
  }

  // 실제 시작일 / 실제 종료일 가져오기
  E3PSTaskData taskData = new E3PSTaskData(task);
  E3PSTaskAccessData accessData = taskData.getAccessState();
  String taskExecStartDateStr = "";
  String taskExecEndDateStr = "";
  if(taskData.taskExecStartDate != null){
    taskExecStartDateStr = DateUtil.getDateString(taskData.taskExecStartDate, "D");
  }
  if(taskData.taskExecEndDate != null){
    taskExecEndDateStr = DateUtil.getDateString(taskData.taskExecEndDate, "D");
  }

  //이슈 삭제
  String deleteIssue = request.getParameter("deleteIssue");
  if ( deleteIssue != null ) {
    ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
  }
  //팝업 여부
  String popup = request.getParameter("popup");

  /*
    ####################  권한 설정  ####################
  */

  boolean isEdit = false;
  boolean isElectron = false;
  boolean isPmo = CommonUtil.isMember("자동차PMO");
  boolean isPmo2 = CommonUtil.isMember("전자PMO");
  boolean isPmo3 = CommonUtil.isMember("KETS_PMO");
  boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
  boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                      //PLM Admin
  boolean isPM = ProjectUserHelper.manager.isPM(project);                      //isPM
  boolean isPL = ProjectUserHelper.manager.isPL(project);                      //isPL
  boolean isProjectContinue = (project.getPjtState()==ProjectStateFlag.PROJECT_STATE_PROGRESS);  //Project 상태
  boolean canManage = isPM;                                    //Manager
  boolean isContinue = accessData.isContinue();                          //true : 진행 , false : 태스크 진행 안됨
  boolean isChild  =  ProjectTaskHelper.isChild(task);
  boolean isSapTask = false;
  boolean isPurchesInfo = false;
  E3PSProjectData projectData = new E3PSProjectData(project);
  if(projectData.teamType.equals("전자 사업부") || projectData.teamType.equals("전자사업부")){
    isElectron = true;
  }
  //Task 책임자  설정
  if(buttonAuth != null){
    QueryResult masterList = TaskUserHelper.manager.getMaster(task);
    while ( masterList.hasMoreElements() ) {
      Object o[]=(Object[])masterList.nextElement();
      TaskMemberLink link = (TaskMemberLink)o[0];
      PeopleData data = new PeopleData(link.getMember().getMember());
      if(wtuser.getName().equals(data.id)){
        isEdit = true;
      }
    }
  }
  if(buttonAuth.isLatest && (CommonUtil.isAdmin() || buttonAuth.isPM || CommonUtil.isBizAdmin()) ){
    isEdit = true;
  }

%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="wt.project.Role"%>
<%@page import="e3ps.common.obj.ObjectData"%>

<%@page import="e3ps.ecm.beans.EcmSearchHelper"%><%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="wt.content.FormatContentHolder"%>
<%@page import="wt.content.ContentItem"%>
<%@page import="wt.content.ContentHelper"%>
<%@page import="wt.content.ContentHolder"%>
<%@page import="e3ps.common.content.ContentUtil"%>
<html>
<head>
<title></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/portal/js/viewObject.js"></script>
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
<script language="JavaScript"><!--

  var ajax = new sack();

  function purchase(projectNo){
    var url="/plm/jsp/project/SapPurchaseInterfaceView.jsp?projectNo="+projectNo;
    openOtherName(url,"purchase","1000","600","status=no,scrollbars=yes,resizable=yes");
  }


  function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","450","status=no,scrollbars=no,resizable=yes");
  }

  function openCompleteReson(oid){
    var url="/plm/jsp/project/EtcOutputReasonView.jsp?oid="+oid;
    openOtherName(url,"Reson","500","360","status=no,scrollbars=no,resizable=yes");
  }

  function taskComplete(v){
    if ( v == '<%=ProjectStateFlag.TASK_STATE_COMPLETE%>' ) {
      if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03337") %><%--종료된 Task는 재진행 할수 없습니다.\n종료하시겠습니까?--%>") ) {
        var url = "/plm/jsp/project/TaskStateCheck.jsp?state="+v+"&oid=<%=oid%>";
        newWin = window.open(url,"compWindow","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=600,resizable=yes,top="+screenHeight+",left="+screenWidth);
        newWin.resizeTo(800,600);
        newWin.focus();
      }
    }
  }
  function setTargetValue(){
    if( checkField(document.forms[0].targetValue, "Current Status") ){
        return;
    }
    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?setTargetValue=save";
    document.forms[0].method = "post";
    disabledAllBtn();
    showProcessing();
    document.forms[0].submit();
  }

  // Task 진척률 입력
  function setCompletion(){

    if(checkStartEndDate()){

      alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
      return;
    }

    var v = document.forms[0].completion.value;

    if(parseInt(v) == 100){

        // [START] [PLM System 1차개선] Admin이 아닐 경우 FS 선행 Task 미완료 시 후행 Task 완료(진척률 100% 입력) 불가, 2013-08-23, BoLee
        <%
            if ( !isAdmin && dependCheck ) {
        %>
        alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
        return;
        <%
            }
        %>
        // [END] [PLM System 1차개선] Admin이 아닐 경우 FS 선행 Task 미완료 시 후행 Task 완료(진척률 100% 입력) 불가, 2013-08-23, BoLee

      if(checkStartEndDate()){

        alert('<%=messageService.getString("e3ps.message.ket_message", "02060") %><%--실제 시작일을 입력 하셔야 \n 태스크를 완료 할 수 있습니다--%>');
        return;
      }else{
        inputEtc();
      }
    }else if(!isNotNumData(v) && parseInt(v)<100){

      document.forms[0].action = "/plm/jsp/project/TaskView.jsp?setCompletion="+v;
      document.forms[0].method = "post";
      disabledAllBtn();
      showProcessing();
      document.forms[0].submit();
    }else {
      alert("<%=messageService.getString("e3ps.message.ket_message", "00008") %><%--{0} 입력값이 잘못되었습니다\n입력값은 0~100 사이어야 합니다--%>");
      document.forms[0].completion.focus();
      return;
    }



  }
  function reasonPopup(){
	  var url = "/plm/jsp/project/PopupReason.jsp?oid=<%=oid%>&cmd=update";
	    //etcData = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=500px; dialogHeight:200px; center:yes");
	    openOtherName(url, "", 500, 300, "status=yes,scrollbars=no,resizable=yes");
  }

  function updataTask(){
    var url="/plm/jsp/project/TaskUpdate.jsp?oid=<%=oid%>";
    openSameName(url,"500","520","status=no,scrollbars=no,resizable=yes");
  }

  function editTask(oid){
    var url = "/plm/jsp/project/manage/ManageProjectTaskFrm.jsp?oid="+oid;
    openOtherName(url,"manageTask","900","700","status=1,scrollbars=no,resizable=1");
  }

  //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
  //산출물 정의  관련 시작 .............................................................................
  function outputPage(opid, isview) {

    var modalHeight = '400px';
    var modalWidth = '600px';

    var url = "/plm/jsp/project/ProjectOutputCreatePage.jsp?fromPage=TaskView&taskOid=<%=oid%>&oid=" + opid;

    if(isview == 'true') {
      url = "/plm/jsp/project/ProjectOutputViewPage.jsp?fromPage=TaskView&taskOid=<%=oid%>&oid=" + opid;
      modalWidth = '670px';
      modalHeight = '570px';
    }
    //openOtherName(url,"manageTask","600","360","status=1,scrollbars=no,resizable=1");

    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth:"+modalWidth+"; dialogHeight:" +modalHeight+ "; center:yes");
    if(typeof attache == "undefined" || attache == null) {
      attache = false;
      return;
    }

    if(isview == 'true') {
      return;
    }


    //onProgressBar();

    var param = "command=searchOutput";
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
  }


  function setOutputTable(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    var errorMessage = showElements[0].getElementsByTagName("l_errorMessage")[0].text;

    document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";

    //if(msg == 'false') {
    //  alert(errorMessage);
    //  if(errorMessage != ""){
    //    alert(errorMessage);
    //  }else{
    //    alert("다시 시도하시기 바랍니다.");
    //  }
    //  return;
    //}

    //onProgressBar();

    //var param = "command=searchTaskCompletions";
    //param += "&oid=<%=oid%>";
    //var url = "/plm/jsp/project/ProjectTaskAjaxAction.jsp";
    //postCallServer(url, param, setTaskProgress, true);

  }

  function registryOutput(v){
    var url = "/plm/jsp/project/ProjectOutputRegistry.jsp?oid="+v;
    openOtherName(url,"window","850","750","status=no,scrollbars=no,resizable=yes");
    //newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=820,height=600,resizable=yes,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(800,600);
    //newWin.focus();
  }

  function inputOutputResult(opid, otype, pjtDwg){
    <%if( dependCheck){%>
      alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
      return;
    <%}%>

    if(checkStartEndDate() && <%=!buttonAuth.isChild %>){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02057") %><%--실제 시작일을 입력 하셔야 \n 산출물을 등록 할 수 있습니다--%>');
      return;
    }
    if(otype=="DOC"){
    <%
      if(!project.getPjtTypeName().equals("금형"))
      {
    %>
      var url = "/plm/jsp/dms/CreateDocumentPop.jsp?outputOid=" + opid + "&buttonView=T&security=<%=project.getRank()==null?"":project.getRank().getName()%>";
    <%
      }
      else
      {
    %>
      var url = "/plm/jsp/dms/CreateDocumentPop.jsp?outputOid=" + opid + "&buttonView=T&security=<%=project.getRank()==null?"":project.getRank().getName()%>";
    <%
      }
    %>
    }else if(otype=="DWG"){
      var url ="/plm/jsp/edm/CreateEPMDocument.jsp?outputOid=" + opid;
      if(pjtDwg == "true"){
        url ="/plm/jsp/edm/CreateEPMDocument.jsp?outputOid=" + opid + "&pjtDwg=2";
      }else if(pjtDwg == "false"){
        url ="/plm/jsp/edm/CreateEPMDocument.jsp?outputOid=" + opid + "&pjtDwg=1";
      }
    }else if(otype=="ETC"){
      var url = "/plm/jsp/project/EtcOutputReason.jsp?oid=" + opid;
      openOtherName(url,"window","500","300","status=no,scrollbars=no,resizable=yes");
      return;
    }

    //OLD - Start
    //attache = window.showModalDialog(url, window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=820px; dialogHeight:800px; center:yes");
    //if(typeof attache == "undefined" || attache == null) {
    //  attache = false;
    //}
    //OLD - End

    //NEW - Start
    openOtherName(url,"window","1024","650","status=no,scrollbars=yes,resizable=yes");
    //NEW - End

    //onProgressBar();

    //var param = "command=searchOutput";
    //param += "&oid=<%=oid%>";
    //var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    //postCallServer(url, param, setOutputTable, true);
    //document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
    //document.forms[0].submit();
  }

  function etcComplete(opid){

    var url = "/plm/jsp/project/EtcOutputReason.jsp?oid=" + opid + "&completed=completed";
    openOtherName(url,"window","500","300","status=no,scrollbars=no,resizable=yes");
    return;
  }

  function registryLinkOutput(oid){
    var url = "/plm/jsp/project/ProjectOutputDocRegistryLinkForm.jsp?oid="+oid;
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=600,height=400,resizable=yes,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(700, 500);
    newWin.focus();
  }

  function inputLinkOutput(opid,otype){

    <%if( dependCheck){%>
      alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
      return;
    <%}%>

    if(checkStartEndDate() && <%=!buttonAuth.isChild %>){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02058") %><%--실제 시작일을 입력 하셔야 \n 산출물을 링크 등록 할 수 있습니다--%>');
      return;
    }


    //alert(otype);
    if(otype=="DOC"){
      var url = "/plm/jsp/project/ProjectOutputResultLink.jsp?oid=" + opid;
      attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=720px; dialogHeight:585px; center:yes");
      if(typeof attache == "undefined" || attache == null) {
        attache = false;
        return;
      }

    }else if(otype=="DWG"){
      var url = "/plm/jsp/project/ProjectOutputDwgResultLink.jsp?oid=" + opid;
      attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=750px; dialogHeight:620px; center:yes");
      if(typeof attache == "undefined" || attache == null) {
        attache = false;
        return;
      }

    }
    //var url = "/plm/jsp/project/ProjectOutputResultLink.jsp?oid=" + opid;
    //openOtherName(url,"window","690","605","status=no,scrollbars=yes,resizable=yes");

    //onProgressBar();

    var param = "command=searchOutput";
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);

    //document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";

  }


  function deleteOutput(opid){
    if ( !confirm("<%=messageService.getString("e3ps.message.ket_message", "01918") %><%--산출물을 삭제 합니다.\n등록된 산출물의 경우 프로젝트에서는 삭제되지만,\n문서관리에서 남아 있습니다.\n삭제하시겠습니까?--%>") ) {
      return;
    }

    var param = "command=deleteOutput";
    param += "&oid=<%=oid%>";
    param += "&outputOid=" + encodeURIComponent(opid);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
  }

  function deleteDepart(opid){
    if ( !confirm("<%=messageService.getString("e3ps.message.ket_message", "03310") %><%--부서를 삭제 시겠습니까?--%>") ) {
      return;
    }

    var param = "command=refDeptDelete";
    param += "&oid=<%=oid%>";
    param += "&taskviewoid=" + encodeURIComponent(opid);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setDepartTable, true);
  }

  function setDepartTable(){
    alert("depart delete");
  }

  // 산출물 진척률 입력
  function outputProgress(objkey, objType) {

    var oprogress = document.forms[0].outputCompletion;

    if(checkStartEndDate()){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02059") %><%--실제 시작일을 입력 하셔야 \n 입력 할 수 있습니다--%>');
      return;
    }

    if(oprogress == null || oprogress == 'undefined') {
      return;
    }

    var oprogressvalue = 0;
    var len = oprogress.length;

    if(len) {
      for(var i = 0; i < len; i++) {
        if(oprogress[i].progresskey == objkey) {
          oprogressvalue = oprogress[i].value;
          break;
        }
      }
    }
    else {
      if(oprogress.progresskey == objkey) {
        oprogressvalue = oprogress.value;

      }
    }
    if(oprogressvalue > 100) {
      alert('<%=messageService.getString("e3ps.message.ket_message", "02732") %><%--진행율을 100미만으로 입력하시기 바랍니다--%>');
      return;
    }
    //if(check){

    if( oprogressvalue == 100 ){

        // [START] [PLM System 1차개선] [수정] FS 선행 Task 미완료 시 후행 Task 완료(산출물 진척률 100% 입력) 불가, 2013-08-23, BoLee
        <%
            if ( dependCheck ) {
        %>
        alert("<%=messageService.getString("e3ps.message.ket_message", "01828") %><%--선행 Task가 완료되지 않아, 후행 Task완료처리가 불가합니다\n선행 Task 진행현황 확인 후 작업완료 해주시기 바랍니다--%>");
        return;
        <%
            }
        %>
        // [END] [PLM System 1차개선] [수정] FS 선행 Task 미완료 시 후행 Task 완료(산출물 진척률 100% 입력) 불가, 2013-08-23, BoLee

      if(objType == "ETC"){
        etcComplete(objkey);
        return;
      }else{
        alert("<%=messageService.getString("e3ps.message.ket_message", "01720") %><%--산출물 등록 및 결재 승인됨 상태가 되면 자동 입력됩니다--%>");
        return;
      }

    }
    //}

    //onProgressBar();

    var param = "command=inputOutputProgress";
    param += "&oid=<%=oid%>";
    param += "&outputOid=" + encodeURIComponent(objkey);
    param += "&outputCompletion=" + encodeURIComponent(oprogressvalue);
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
  }


  function setTaskProgress(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

    document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";


    /*
    var showElements = xmlDoc.selectNodes("//data_info");
    var l_taskCompletion = showElements[0].getElementsByTagName("l_taskCompletion");
    var l_taskPlanStartDate = showElements[0].getElementsByTagName("l_taskPlanStartDate");
    var l_taskPlanEndDate = showElements[0].getElementsByTagName("l_taskPlanEndDate");



    ltaskCompletion = decodeURIComponent(l_taskCompletion[0].text);
    ltaskPlanStartDate = decodeURIComponent(l_taskPlanStartDate[0].text);
    ltaskPlanEndDate = decodeURIComponent(l_taskPlanEndDate[0].text);


    var startTD = document.getElementById("taskPlanStartDateTD");
    var endTD = document.getElementById("taskPlanEndDateTD");
    var completionBarTD = document.getElementById("taskCompletionBarTD");
    var completionTD = document.getElementById("taskCompletionTD");

    var bathtmlstr = "";
    bathtmlstr += "<table border=0 width=100% cellpadding=0 cellspacing=0>";
    bathtmlstr += "<tr>";
    bathtmlstr += "<td align=right width='"+ltaskCompletion+"%'><img src=\"/plm/portal/icon/bar_arrow1.gif\"></td>";
    bathtmlstr += "<td align=left width='(100-"+ltaskCompletion+")%'><img src=\"/plm/portal/icon/bar_arrow2.gif\"></td>";
    bathtmlstr += "</tr>";
    bathtmlstr += "<tr height=10>";

    bathtmlstr += "<td ";
    if(ltaskCompletion != 0) {
      bathtmlstr += "background=\"/plm/portal/icon/bar_full.gif\"";
    }
    bathtmlstr += "></td>";

    bathtmlstr += "<td ";
    if(ltaskCompletion != 100) {
      bathtmlstr += "background=\"/plm/portal/icon/bar_blank.gif\"";
    }

    bathtmlstr += "></td>";
    bathtmlstr += "</tr>";
    bathtmlstr += "</table>";

    startTD.innerText = "(" + ltaskPlanStartDate + ")";
    endTD.innerText = "(" + ltaskPlanEndDate + ")";
    completionTD.innerHTML = "<b>" + ltaskCompletion + "%</b>";
    completionBarTD.innerHTML = bathtmlstr;
    */
  }


//체크박스 관련
/*function allCheckNCancel()
{
  var checkBoxAll = document.getElementById("allCheck");
  var allCheckBox = document.forms[0].LID;
  if(checkBoxAll == null || checkBoxAll == undefined || allCheckBox == null || allCheckBox == undefined)
  return;

  for(var i = 0; i < allCheckBox.length; i++)
  {
  if(checkBoxAll.checked)
  allCheckBox[i].checked = true;
  else
  allCheckBox[i].checked = false;
  }
}

function checkNCancelAllCheckBox()
{
  var checkBoxAll = document.getElementById("allCheck");
  var allCheckBox = document.forms[0].LID;
  if(checkBoxAll == null || checkBoxAll == undefined || allCheckBox == null || allCheckBox == undefined)
  return;

  var bCheck = true;
  for(var i = 0; i < allCheckBox.length; i++)
  {
  if(!allCheckBox[i].checked)
  bCheck = false;
  }

  if(!checkBoxAll.checked && bCheck)
  checkBoxAll.checked = true;
  else
  checkBoxAll.checked = false;
}
*/

function allapprovalPage(cbox){

  var checkval = false;
  var selectList = "";

  if(cbox == null || cbox == "undefinded") {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01741") %><%--산출물을 등록해야 결재 가능합니다--%>");
    return;
  }

  selectList = getSelectedList(cbox);
  if(selectList == "") {
    alert('<%=messageService.getString("e3ps.message.ket_message", "03136") %><%--하나 이상의 산출물을 체크해야 합니다--%>');
    return;
  }

  var leng = cbox.length;
  if(leng){
    for(var i=0; i<leng; i++) {
      if(cbox[i].checked == true) {
        if(cbox[i].value == '') {
          alert("<%=messageService.getString("e3ps.message.ket_message", "01741") %><%--산출물을 등록해야 결재 가능합니다--%>");
          return;
        }else{
          checkval = true;
        }
      }
    }
  }else{
    if(cbox.checked == true && cbox.value == '') {
      if(cbox.value == '') {
        alert("<%=messageService.getString("e3ps.message.ket_message", "01741") %><%--산출물을 등록해야 결재 가능합니다--%>");
        return;
      }else{
        checkval = true;
      }
    }
  }

  if(checkval) {
    var url = "/plm/jsp/project/allapprovalPage.jsp?taskOid=<%=oid%>&" + selectList;
    newWin = window.open(url,"approvalWindow","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=600,resizable=yes,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(850,600);
    newWin.focus();
  }
}


//산출물 관련 끝 ................................................
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

// ###################     이슈  등록      ########################
  function IssueCreate(){
    var url="/plm/jsp/project/IssueCreate.jsp?oid=<%=oid%>";
    openOtherName(url,"IssueCreate","870","800","status=no,scrollbars=yes,resizable=yes");
  }


  function onDeleteTableRow(tableid, chk_name, chk_value) {
    targetTable = document.getElementById(tableid);
    var chkTag = document.all.item(chk_name);
    alert(chk_name);
    var chkLen = chkTag.length;
    if(chkLen) {
      for(var i = 0; i < chkLen; i++) {
        if(chkTag[i].value == chk_value) {
          targetTable.deleteRow(i+1);
          return;
        }
      }
    }else {
      if(chkTag.value == chk_value) {
        targetTable.deleteRow(1);
        return;
      }
    }
  }


  function onDeleteDepartTableRow(tableid, cmdstr, objid) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = cmdstr;

    //onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(objid);
    //alert(param);
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);
  }

  function onMessage(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;

    if(msg == 'false' && showElements[0].getElementsByTagName("l_result") != null && showElements[0].getElementsByTagName("l_result")[0].text != ""){
      alert(showElements[0].getElementsByTagName("l_result")[0].text);
      return;
    }

    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }

    if(targetTableId == "refDeptTable") {
      acceptDept(xmlDoc);
    }else{
      acceptMember(xmlDoc);
    }
  }

  function IssueAdd(issueOid){
  var url="/plm/jsp/project/IssueCreate.jsp?oid=<%=oid%>&cmd=modify&issueOid="+issueOid;
  openOtherName(url,"IssueCreate","870","500","status=no,scrollbars=yes,resizable=yes");
  }


  function issueDelete(issueOid){
    document.forms[0].cmd.value="delete";
    document.forms[0].issueOid.value = issueOid;
    ajax.requestFile = "/plm/jsp/project/ProjectIssueAjaxAction.jsp";
    ajax.URLString = getPostData(document.forms[0]);
    ajax.onCompletion = reload;
    ajax.runAJAX();
  }

  function reload()
  {
    eval(ajax.response);
    enabledAllBtn();
  }

  //function viewIssue(oid) {
    //var url = "/plm/jsp/equipment/equipmentView.jsp?oid=" + oid;
    //openSameName(url,"870","650","status=no,scrollbars=yes,resizable=yes");
  //}

// ################################# 이슈 관리  #############################################
  //function IssueErrorCreate(){
    //var url="/plm/jsp/equipment/equipmentCreate.jsp?oid=<%//=oid%>";
    //openOtherName(url,"IssueCreate","870","700","status=no,scrollbars=yes,resizable=yes");
  //}


  //function IssueAdd(issueOid){
    //var url="/plm/jsp/equipment/UnitErrorAssignWorkComfirm.jsp?oid=" + issueOid;
    //openOtherName(url,"IssueCreate","900","700","status=no,scrollbars=yes,resizable=yes");
  //}

  //Veiw 권한 부서 가져오기 시작 ........................................................................................
  function addDepartment(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?isModal=true&isOpen=false&invokeMethod=openerCall&mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
      return;
    }

    //onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(attacheDept[0][0]);

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, true);

  }


  function changeDeptRow(){

    var param = "command=changeDeptRow";


    param += "&oid=<%=oid%>";
    param += "&role=" + document.forms[0].deptRole.value;

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";

    postCallServer(url, param, onMessage, true);
  }

  function onMessage() {
    document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
  }

  function openerCall(oArr) {

    //onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(oArr[0][0]);

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";

    alert(param);
    postCallServer(url, param, onMessage, true);

  }

  function acceptDept(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_code = showElements[0].getElementsByTagName("l_code");
    var l_linkOid = showElements[0].getElementsByTagName("l_linkOid");

    var targetTable = document.getElementById(targetTableId);
    var len = targetTable.rows.length;
    for(var i=len; i > 1; i--) {
      targetTable.deleteRow(i-1);
    }

    for(var i = 0; i < l_oid.length; i++) {
      //loid = decodeURIComponent(l_oid[i].text);
      lname = decodeURIComponent(l_name[i].text);
      //lcode = decodeURIComponent(l_code[i].text);
      llinkOid = decodeURIComponent(l_linkOid[i].text);

      tableRows = targetTable.rows.length;
      newTr = targetTable.insertRow(tableRows);

      //부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = lname;

      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"', '"+targetLinkMsg+"', '" + llinkOid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
    }
  }

  function addIssue() {
    var url = "/plm/jsp/project/projectIssueCreate.jsp?oid=<%=oid%>";
    openOtherName(url,"addIssue","760","700","status=no,scrollbars=yes,resizable=yes");
  }

  function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","760","700","status=no,scrollbars=yes,resizable=yes");
  }

  function deleteIssue(v) {
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
      document.forms[0].action = "/plm/jsp/project/TaskView.jsp?deleteIssue="+v;
      document.forms[0].method = "post";
      document.forms[0].submit();
    }
  }
  function setOEMSave(){

    //if( document.forms[0].oemType.value ==''){
    //  alert("OEM 타입을 선택해 주십시오.");
    //  return;
    //}

    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oem=oemSave";
    document.forms[0].method = "post";
    document.forms[0].submit();
  }

  function setExeDateSave() {

      // [PLM System 1차개선] 실제 시작일자에 날짜를 입력하지 않았을 경우 alert message 표시, 2013-08-15, BoLee
      if ( document.getElementById("taskExecStartDate").value == "" ) {
          alert("<%=messageService.getString("e3ps.message.ket_message", "02347") %><%--일자를 입력해주세요--%>");
          document.getElementById("taskExecStartDate").focus();
          return;
      }

      if(checkToday()){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01447") %><%--미래일자를 실제 시작일로 입력할 수 없습니다--%>");
      document.getElementById("taskExecStartDate").value = "";
      return;
    }

    if(confirm(document.forms[0].taskExecStartDate.value + "<%=messageService.getString("e3ps.message.ket_message", "03270") %><%--{0}을 실제 시작일로 입력하시겠습니까?--%>")){
      //document.forms[0].method = "post";
      document.forms[0].ExeDate.value = "ExeDate";
      document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
      document.forms[0].submit();
    }else{
      alert('<%=messageService.getString("e3ps.message.ket_message", "02386") %><%--입력을 취소했습니다--%>');
      document.forms[0].taskExecStartDate.value = "";
    }
  }
  function setExeDateSave2(){
    if(checkToday2()){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01448") %><%--미래일자를 실제 종료일로 입력할 수 없습니다--%>");
      document.getElementById("taskExecEndDate").value = "";
      return;
    }
    //if(confirm(document.forms[0].taskExecEndDate.value + "을 실제 종료일로 입력하시겠습니까?")){
      document.forms[0].ExeDate.value = "ExeDate";
      //document.forms[0].method = "post";
      document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
      document.forms[0].submit();
    //}else{
    //  alert("입력을 취소했습니다.");
    //  document.forms[0].taskExecEndDate.value = "";
    //}
  }

  function setExeDateSave3(){
    if(checkToday2()){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01447") %><%--미래일자를 실제 시작일로 입력할 수 없습니다--%>");
      document.getElementById("taskExecStartDate").value = "";
      return;
    }
    //if(confirm(document.forms[0].taskExecStartDate.value + "을 실제 시작일로 입력하시겠습니까?")){
      document.forms[0].ExeDate.value = "ExeDate";
      //document.forms[0].method = "post";
      document.forms[0].action = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
      document.forms[0].submit();
    //}else{
    //  alert("입력을 취소했습니다.");
    //  document.forms[0].taskExecEndDate.value = "";
    //}
  }

  function checkToday(){
    obj = document.getElementById("taskExecStartDate");
    var now = new Date();

      var today = now.getYear()+'-'+fncLPAD((now.getMonth()+1))+'-'+fncLPAD(now.getDate());

      if(today < obj.value) {
        return true;
    }
    return false;
  }
  function checkToday2(){
    obj = document.getElementById("taskExecEndDate");
    var now = new Date();

      var today = now.getYear()+'-'+fncLPAD((now.getMonth()+1))+'-'+fncLPAD(now.getDate());

      if(today < obj.value) {
        return true;
    }
    return false;
  }
  function fncLPAD(num)
  {
      if(num<10) return '0'+num;
      else return ''+num;
  }

  // 실제 시작일 ,실제 종료일
  function autoTask(arg){
    document.forms[0].action = "/plm/jsp/project/TaskView.jsp?TaskAuto=Auto&AutoType="+arg;
    document.forms[0].method = "post";
    document.forms[0].submit();

  }



  function clearDate(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
  }

  function checkStartEndDate(){
    var form = document.forms[0];

    //if(form.taskExecEndDate.value == ''){
    //  return true;
    //}
    if(form.taskExecStartDate.value == ''){
      return true;
    }
    return false;
  }

  function doViewDoc(_oid){
    openView(_oid, null, null, false);
  }
  function doViewEpm(dieNo){
    var url = "/plm/jsp/project/ProjectOutputDwgDieNoLink.jsp?dieNo="+dieNo;
    openOtherName(url, "popup", 660, 530, "status=yes,scrollbars=no,resizable=yes");
  }

  function inputEtc(){

    var url = "/plm/jsp/project/PopupReason.jsp?oid=<%=oid%>";
    //etcData = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=500px; dialogHeight:200px; center:yes");
    openOtherName(url, "", 500, 300, "status=yes,scrollbars=no,resizable=yes");

    /*
    if(typeof etcData == "undefined" || etcData == null) {
      return false;
    }else{
      document.forms[0].compReason.value = etcData;
      return true;
    }*/
  }

  function ViewDoc(oid){
    openView(oid, 870, null, null);
  }

  function ViewReason(){
    var url="/plm/jsp/project/TaskReasonView.jsp?oid=<%=oid%>";
    openOtherName(url,"Reason","500","300","status=no,scrollbars=no,resizable=yes");
  }

  function viewTry(oid){
    openWindow('/plm/jsp/project/trySchdule/TryViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
  }

  function viewMoldPart(oid){
    var url = "/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid="+oid+"&popup=popup";
    //openWindow('/plm/jsp/project/moldPart/MoldPartView.jsp?projectOid='+oid+"&popup=popup", '',820,800);
    openOtherName(url,"","840","800","scrollbars=yes,resizable=yes,top=200,left=250,center=yes");
  }

  function savePoint(){
    document.forms[0].action = "/plm/jsp/project/TaskViewAction.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit();
  }

  // 설계변경 상세조회 팝업
  function viewEcoPop(ecoOid, ecoType){
    if(ecoOid.indexOf("Prod") > -1){
      ecoType = "P";
    }
    var url = "";
    //alert(ecoType);
    if (ecoType != null && ecoType == "P") {// 제품
      url = "/plm/servlet/e3ps/ProdEcoServlet?cmd=popupview&oid=" + ecoOid;
    }else {                  // 금형
      url = "/plm/servlet/e3ps/MoldEcoServlet?cmd=popupview&oid=" + ecoOid;
    }

    openOtherName(url,"","1024","768","scrollbars=yes,resizable=yes,top=200,left=250,center=yes");
  }

  function isValidDate(obj, maxLength) {
      var retVal = true;
      var msg    = ' "yyyymmdd" ' + '<%=messageService.getString("e3ps.message.ket_message", "03222") %><%--형식으로 다시 입력 해주세요--%>';
     //document.forms[0].duration.value = "";

    if(obj.value == "") {
      return;
    }

    val=obj.value;
    re=/[^0-9]/gi;
    obj.value=val.replace(re,"");

      var inputDate = funcReplaceStrAll(obj.value,  '<%=messageService.getString("e3ps.message.ket_message", "00015") %><%--{0}년--%>', '');
      inputDate     = funcReplaceStrAll(inputDate,  '<%=messageService.getString("e3ps.message.ket_message", "00039") %><%--{0}월--%>', '');
      inputDate     = funcReplaceStrAll(inputDate,  '<%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>', '');

      var yyyy = inputDate.substring(0, 4);
      var mm   = (maxLength >= 6)?inputDate.substring(4, 6):"01";
      var dd   = (maxLength == 8)?inputDate.substring(6, 8):"01";

      if (isNaN(yyyy) || parseInt(yyyy) < 1000) return viewErrMsg(obj, msg);
      if (isNaN(mm) || parseFloat(mm) > 12 || parseFloat(mm) < 1) return viewErrMsg(obj, msg);
      if (isNaN(dd) || parseFloat(dd) < 1 || (parseFloat(dd) > getEndOfMonthDay(parseFloat(yyyy.substring(2,4)), parseFloat(mm))) ) return viewErrMsg(obj, msg);

    if(inputDate.length == 8) {
        inputDate = inputDate.substring(0, 8); //미봉책
    }else{
      alert('<%=messageService.getString("e3ps.message.ket_message", "02383") %><%--입력된 값이 8자리 숫자가 아닙니다 8자리 숫자를 입력해주세요--%>');
      return;
    }

    obj.value = yyyy+ "-" +mm+ "-" +dd;
      return true;
  }
  function viewErrMsg(obj,msg) {
    alert(obj.value + " " + msg);
  }
  //********************************************************************
  //문자열 일괄전환 함수
  function funcReplaceStrAll(org_str, find_str, replace_str) {
      var pos = org_str.indexOf(find_str);
      while(pos != -1) {
          pre_str  = org_str.substring(0, pos);
          post_str = org_str.substring(pos + find_str.length, org_str.length);
          org_str  = pre_str + replace_str + post_str;
          pos = org_str.indexOf(find_str);
      }
      return org_str;
  }

  //*******************************************************************
  //년월 입력시 마지막 일자
  function  getEndOfMonthDay( yy, mm ) {
      var max_days=0;
      if(mm == 1) {
          max_days = 31 ;
      } else if(mm == 2) {
          if ((( yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0))  max_days = 29;
          else                                                         max_days = 28;
      }
      else if (mm == 3)   max_days = 31;
      else if (mm == 4)   max_days = 30;
      else if (mm == 5)   max_days = 31;
      else if (mm == 6)   max_days = 30;
      else if (mm == 7)   max_days = 31;
      else if (mm == 8)   max_days = 31;
      else if (mm == 9)   max_days = 30;
      else if (mm == 10)  max_days = 31;
      else if (mm == 11)  max_days = 30;
      else if (mm == 12)  max_days = 31;
      else                return '';

      return max_days;
  }

-->
</script>
<%@include file="/jsp/common/processing.html"%>
<script src="/plm/portal/js/script.js"></script>
<style type="text/css">

body {
  margin-left: 12px;
  margin-top: 15px;
  margin-right: 0px;
  margin-bottom: 5px;

  overflow-x:auto;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;
}

</style>
</head>
<body>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form>
<!-- Hidden Value -->
<input type=hidden name=taskOid value='<%=oid%>'>
<input type=hidden name=oid value='<%=oid%>'>
<input type=hidden name=cmd >
<input type=hidden name=issueOid>
<input type=hidden name=popup value='<%=popup%>'>
<input type=hidden name=ExeDate>
<!-- //Hidden Value -->

<table border="0" cellpadding="0" cellspacing="0" width="780">
  <tr>
    <td valign="top">
    <!-------------------------------------- 상단버튼 시작 //-------------------------------------->
       <table width="780"  border="0" cellspacing="0" cellpadding="0">
         <tr>
          <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>

                  <table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                      <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02927") %><%--태스크 정보--%></td>
                      <% if( popup == null) { %>
                      <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02927") %><%--태스크 정보--%></td>
                    <%} %>
                    </tr>
                  </table>
                </td>
            </tr>
            <tr>
              <td  class="head_line"></td>
            </tr>
            <tr>
              <td class="space10"></td>
          </tr>
          </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
              <td align="right">&nbsp;
                   <%

                   if(!buttonAuth.isComplated && buttonAuth.isTaskInfoModify || isAdmin){ %>
            <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:updataTask();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02926") %><%--태스크 수정--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   </tr>
                 </table>
                <%}%>
              </td>
            </tr>
        </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <!-- TASK 기본정보 -->
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <COL width="10%"><COL width="20%"><COL width="12%"><COL width="20%"><COL width="13%"><COL width="20%">

        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></td>
          <td class="tdwhiteL">&nbsp;<%=taskData.taskName%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
          <td class="tdwhiteL" >
            <%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%>&nbsp;
          </td>

          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
          <td class="tdwhiteL">
            <%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%>&nbsp;
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02921") %><%--태스크 기간--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=taskData.taskDuration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02061") %><%--실제 시작일자--%></td>
          <td class="tdwhiteL">
            <%if(!isDate || isAdmin|| isPmo || isPmo2 || isPmo3){%>
                <input id="taskExecStartDate" name="taskExecStartDate" size="12" value="<%=taskExecStartDateStr%>" maxlength=15 onChange="javascript:isValidDate(this, 8);" >
                <a href="#" onclick="javascript:showCal2(taskExecStartDate, 'setExeDateSave');"><img src="/plm/portal/images/icon_6.png"border="0"></a>
                <input type="button" id="taskExecStartDateBtn" value="<%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%>" onclick="JavaScript:setExeDateSave();" class="s_font">
            <%}else{%>
                <%=taskExecStartDateStr %><input type="hidden" name="taskExecStartDate" value="<%=taskExecStartDateStr%>">
            <%}%>


          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02063") %><%--실제 종료일자--%></td>
          <td class="tdwhiteL">
            <% if(isAdmin || isPmo || isPmo2 || isPmo3){%>
            <input name=taskExecEndDate size="12" value="<%=taskExecEndDateStr %>" maxlength=15 onChange="javascript:isValidDate(this, 8);">
            <a href="#" onclick="javascript:showCal('taskExecEndDate');"><img src="/plm/portal/images/icon_6.png" border=0></a>
            <a href="JavaScript:clearDate('taskExecEndDate')"><img src="/plm/portal/images/icon_delete.gif" border=0></a>
            <input type="button" value="<%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%>" onclick="JavaScript:setExeDateSave2();" class="s_font">

            <%}else{%>
            <%=taskExecEndDateStr%><input type=hidden name=taskExecEndDate size="12" value="<%=taskExecEndDateStr%>" >
            <%} %>
          &nbsp;
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03128") %><%--필수 / Type--%></td>
          <td class="tdwhiteL"><%if(task.isOptionType()) {
               out.print("Y / ");
              }else{
              out.print("N / ");
              }
              %><%=task.getTaskType() == null?messageService.getString("e3ps.message.ket_message", "02345")/*일반*/:task.getTaskType() %>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdwhiteL" colspan=3 title="<%=task.getTaskDesc()==null?"":task.getTaskDesc()%>">&nbsp;<a href="#" onclick="javascript:ViewReason();">
            <%=(taskData.taskDesc.trim()=="")?"":taskData.taskDesc.trim()%></a>
          </td>
        </tr>
        <%if(project.getPjtType() == 3 && task.getNcha() > 0 && task.isDebug_n()){%>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03349") %><%--디버깅 사유--%></td>
          <td class="tdwhiteL">
            <%
            int reason = 0;
            try{
              reason = Integer.parseInt(task.getReason());
            }catch(Exception e){

            }
            switch(reason){
            case 1:
              out.print(messageService.getString("e3ps.message.ket_message", "00850")/*고객요청*/);
              break;
            case 2:
              out.print(messageService.getString("e3ps.message.ket_message", "03027")/*품질문제*/);
              break;
            case 3:
              out.print(messageService.getString("e3ps.message.ket_message", "02601") /*제품설계변경*/);
              break;
            case 4:
              out.print(messageService.getString("e3ps.message.ket_message", "01063")/*금형보완*/);
              break;
            case 5:
              out.print(messageService.getString("e3ps.message.ket_message", "01136")/*기타*/);
              break;
            case 6:
              out.print(messageService.getString("e3ps.message.ket_message", "01784")/*생산성{0} 향상*/);
              break;
            default:
              out.print("&nbsp;");
              break;
            }

            %>
          </td>

          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03350") %><%--특이사항--%></td>
          <td class="tdwhiteL0" colspan=3>
            <textarea name="detail" style="width:100%" rows="3" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" readOnly><%=task.getSpecial()==null?"":task.getSpecial() %></textarea>
          </td>
        </tr>
        <%}%>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->

      <!-- 진행현황 시작 -->
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02736") %><%--진행현황--%></td>
              <td align="right">&nbsp;
              </td>
            </tr>
        </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <COL width="12%"><COL width="10%"><COL width="78%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td class="tdwhiteL">
            <%=taskData.getStateStr()%>
          </td>
          <td class="tdwhiteL0">
            <!-- 진행률 그림 -->
            <table width="100%" border=0 cellspacing="0" cellpadding="0">
              <tr>
                <td width="13%" id="taskPlanStartDateTD" align='left'> (<%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%>)</td>
                <td width="74%" id="taskCompletionBarTD" align='left'>
                  <jsp:include page="/jsp/project/ProjectProgressBarView.jsp" flush="false">
                    <jsp:param name="planValue" value="<%=taskData.getPreferCompStr()%>"/>
                    <jsp:param name="workValue" value="<%=taskData.taskCompletion%>"/>
                  </jsp:include>
                </td>

                <td width="13%" id="taskPlanEndDateTD" align='left'>(<%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%>)</td>
              </tr>
            </table>
            <!-- //진행률 그림 -->
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%></td>
          <td class="tdwhiteL" id="taskCompletionTD"><b><%=taskData.taskCompletion%> / <%=taskData.getPreferCompStr()%></b></td>
          <td class="tdwhiteL0" >
        <% if(buttonAuth.isCompletionButton || isAdmin ){
            String reason = task.getCompReason();
            if(reason != null && reason.length() >0){
              reason = StringUtil.changeString(reason, "\n", "<br>");
            }else{
              reason = "&nbsp;";
            }
          %>
          <input type="text" name="completion" value="<%=taskData.taskCompletion%>" size="5">%&nbsp;&nbsp;<input type="button" value="<%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%>" onclick="JavaScript:setCompletion()" class="s_font"><%=reason%><input type="hidden" name="compReason"></input>
          <%if(isAdmin){ %>
            <input type="button" value="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" onclick="JavaScript:reasonPopup();" class="s_font">
          <%} %>

        <%}else if(taskData.taskCompletion == 100){
          String reason = task.getCompReason();
          if(reason != null && reason.length() >0){
            reason = StringUtil.changeString(reason, "\n", "<br>");
          }else{
            reason = "&nbsp;";
          }
          %>
            <%=reason%>

          <%if(buttonAuth.isPM || buttonAuth.isTaskManager){ %>
            <input type="button" value="<%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%>" onclick="JavaScript:reasonPopup();" class="s_font">
          <%} %>
        <%
        }
        %>
        <%
          if(  (taskData.taskExecStartDate == null  )){
        %>
          <!-- <input type="button" value="시작" onclick="JavaScript:autoTask('start')" class="s_font"> -->
          <input type="hidden" name ="TaskAutoStartDate" value="<%=toDay %>" >
        <%} %>

        <%
          if( taskData.taskExecEndDate ==null && taskData.taskCompletion == 100 ){
        %>
          <input type="button" value="<%=messageService.getString("e3ps.message.ket_message", "02656") %><%--종료--%>" onclick="JavaScript:autoTask('end')" class="s_font">
          <input type="hidden" name ="TaskAutoEndDate" value="<%=toDay %>" >
        <%} %>
<%
//  if (isLast) {
    if(buttonAuth.isComplatedButton) {
%>
      <!--   <input type='button' value='종료' onclick="JavaScript:taskComplete('<%=ProjectStateFlag.TASK_STATE_COMPLETE%>')" class="s_font">  -->
<%
    }

%>
    <%//=taskData.getStateMessage()%>
<%

  //} else {
//    out.println("&nbsp;");
//  }
%>
          &nbsp;</td>
        </tr>
      </table>
<%
try{
  QueryResult qrChild = ProjectTaskHelper.manager.getChild(task);

  if(qrChild.size() != 0 && false){
    Collections.sort(qrChild.getObjectVectorIfc().getVector(), new TaskPlanComparator());



%>
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "03138") %><%--하위 태스크 진행현황--%></td>
          <td  align="right"> (<%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%><font color="blue"> <b>/</b></font> <%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%>) </td>

        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <COL width="15%">

        <COL width="10%">
        <COL width="10%">
        <COL width="10%">

        <COL width="10%">
        <COL width="10%">

        <COL width="15%">
        <COL width="10%">


        <tr>
          <td class="tdblueL"><b><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></b></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03351") %><%--기간/표준공수--%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02064") %><%--실제시작일--%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02067") %><%--실제종료일--%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>

        </tr>
      </table>

<%
    while(qrChild.hasMoreElements()){
      Object[] taskObj = (Object[]) qrChild.nextElement();
      TemplateTask child = (TemplateTask) taskObj[0];
      E3PSTaskData dependData = new E3PSTaskData( (E3PSTask)child);

      String childtaskExecStartDateStr = "&nbsp;";
      String childtaskExecEndDateStr = "&nbsp;";

      String childPlanStartStr = DateUtil.getDateString(dependData.taskPlanStartDate, "D");
      String childPlanEndStr = DateUtil.getDateString(dependData.taskPlanEndDate, "D");

      if(dependData.taskExecStartDate != null){
        childtaskExecStartDateStr = DateUtil.getDateString(dependData.taskExecStartDate, "D");
      }
      if(dependData.taskExecEndDate != null){
        childtaskExecEndDateStr = DateUtil.getDateString(dependData.taskExecEndDate, "D");
      }

%>
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <COL width="15%">

        <COL width="10%">
        <COL width="10%">
        <COL width="10%">

        <COL width="10%">
        <COL width="10%">

        <COL width="15%">
        <COL width="10%">

        <tr>
          <td class="tdwhiteL" title="<%=dependData.taskName%>">
              <b><%=dependData.taskName%></b>
          </td>
          <td class="tdwhiteL"><%=childPlanStartStr%></td>
          <td class="tdwhiteL"><%=childPlanEndStr%></td>
          <td class="tdwhiteL"><%=dependData.taskDuration %>/<%=dependData.taskStdWork%></td>
          <td class="tdwhiteL"><%=childtaskExecStartDateStr%></td>
          <td class="tdwhiteL"><%=childtaskExecEndDateStr%></td>
          <td class="tdwhiteL"><%=dependData.taskCompletion%> / <%=dependData.getPreferCompStr()%> </td>
          <td class="tdwhiteL"><%=dependData.getStateStr()%></td>
        </tr>


    </table>
    <table border="0" cellpadding="0" cellspacing="0" width="780">
      <tr>
        <td class="tab_btm1"></td>
      </tr>
    </table>
<%
    }
  }
}catch(Exception ee){
    Kogger.error(ee);
}
%>      <!-- space -->


      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->
  <!-- ####################  부서 권한 추가 Start ##################################-->
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02929") %><%--태스크 책임자--%></td>
              <td align="right">&nbsp;
              </td>
            </tr>
        </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
      <COL width="15%"><COL width="85%">
        <tr>
<%
  HashMap legacyMap = new HashMap();
%>
          <td class="tdblueL" valign="top">Role</td>
          <td class="tdwhiteL">
          <%
          String deptRole2 = task.getPersonRole();
          Role role2 = null;
          if(deptRole2 != null){
            role2 = Role.toRole(deptRole2);
          }
          if(buttonAuth.isRoleChangeSelected || isAdmin){%>

          <select name="deptRole" class="fm_jmp" style="width:200" onchange='javaScript:changeDeptRow()'>
            <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option>
            <option value="PM" <%if("PM".equals(task.getPersonRole())) {%>selected<%}%>><%if(project instanceof MoldProject){ %><%=messageService.getString("e3ps.message.ket_message", "03352") %><%--금형개발--%><%}else{ %>PM<%} %></option>
            <%

            if(vecTeamStd!=null){

              Collections.sort(vecTeamStd, new RoleComparator());

              //roleLink = null;
              for (int i = 0; i < vecTeamStd.size(); i++) {
                Role role = (Role) vecTeamStd.get(i);
                String roleKey = role.toString();
                String selected = "";
                if(roleKey.equals(deptRole2)){
                  selected = "selected";
                }
                String roleName_ko = role.getDisplay(messageService.getLocale());




            %>
                 <option value="<%=roleKey%>" <%=selected%>><%=roleName_ko%></option>
            <%  }
            }%>
          </select>
          <%}else{%>

            <%=role2 != null ? ("PM".equals(role2.getDisplay(Locale.KOREA)) && project instanceof MoldProject?messageService.getString("e3ps.message.ket_message", "03352")/*금형개발*/:role2.getDisplay(messageService.getLocale())) : "-"%>
          <%}%>
          </td>

        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02929") %><%--태스크 책임자--%></td>
          <td class="tdwhiteL">
          <%
            String addMaster = request.getParameter("addMaster");
            String deleteMaster = request.getParameter("deleteMaster");
            if(addMaster == null){
              addMaster = "";
            }
            if(deleteMaster == null){
              deleteMaster = "";
            }
          %>
            <jsp:include page="/jsp/project/TaskMasterInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oid%>"/>
              <jsp:param name="addMaster" value="<%=addMaster%>"/>
              <jsp:param name="deleteMaster" value="<%=deleteMaster%>"/>
            </jsp:include>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02932") %><%--태스크참여자--%></td>
          <td class="tdwhiteL">
          <%

            if(addMaster == null){
              addMaster = "";
            }
            if(deleteMaster == null){
              deleteMaster = "";
            }
          %>
            <jsp:include page="/jsp/project/TaskMemberInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oid%>"/>
              <jsp:param name="addMaster" value="<%=addMaster%>"/>
              <jsp:param name="deleteMember" value="<%=deleteMaster%>"/>
            </jsp:include>
          </td>
        </tr>
      </table>

    <!-- ####################  부서 권한 추가 end  ##################################-->

      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->
      <!-- 산출물 시작 -->
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
           <tr>
             <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
             <td class="font_03" width="90"><%=messageService.getString("e3ps.message.ket_message", "01718") %><%--산출물 관리--%></td>
             <td valign="bottom">
               <%if("Try".equals(task.getTaskType())){%>
                 <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_3.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg3.gif"><a href="#" onclick="javascript:viewTry('<%=oid %>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_4.gif"></td>
                   </tr>
                 </table>
               <%} %>
                <%
                boolean isCheckMoldProject = false;
                if(project instanceof MoldProject){
                 if(ProjectHelper.getMoldPartManagerCount((MoldProject)project) > 0){
                   isCheckMoldProject = true;
                 }
                }

                if("금형부품".equals(task.getTaskName()) && isCheckMoldProject){ %>
                  <table border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td width="10"><img src="/plm/portal/images/btn_3.gif"></td>
                     <td class="btn_blue" background="/plm/portal/images/btn_bg3.gif"><a href="#" onclick="javascript:viewMoldPart('<%=projectOid %>')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01068") %><%--금형부품관리--%></a></td>
                     <td width="10"><img src="/plm/portal/images/btn_4.gif"></td>
                   </tr>
                 </table>
                <%} %>
             </td>
             <td align="right">
<%
  if(buttonAuth.isOuputDocAdd || isEdit){
%>
             <table border="0" cellspacing="0" cellpadding="0">
                 <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                 <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:allapprovalPage(document.forms[0].LID);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02334") %><%--일괄결재--%></a></td>
                 <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                   <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                   <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:outputPage('', 'false');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                   <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                 </tr>

             </table>
<%}else{ %>
&nbsp;
<%} %>
             </td>
           </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" width="780" id="outputTable">
                <tr>
                  <td class="tdblueM" width="30"><input type="checkbox" name="allCheck" onclick="selectAll(document.forms[0].allCheck, document.forms[0].LID)"/></td>
                  <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                  <td class="tdblueM" width="120"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
                  <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "03127") %><%--필수--%></td>
                  <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                  <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "02856") %><%--최종작성자--%></td>
                  <td class="tdblueM" width="40"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                  <td class="tdblueM" width="30"><%=messageService.getString("e3ps.message.ket_message", "02957") %><%--파일--%></td>
                  <td class="tdblueM" width="70"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
<%
  if(isEdit || true) {
%>
                  <td class="tdblueM0" width="50"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
<%
  }
%>
                </tr>
<%
  ProjectOutput output = null;
  ProjectOutputData outputData = null;
  PeopleData peopleData = null;
  WTUser outputUser = null;
  String deptName = "";
  String outputChargerName = "";
  String version = "";
  String lastVersion = "";
  String versionOid = "";
  String lastVersionOid = "";

  String state = "";
  String modifyDate = "";
  String peopleOID = "";

  boolean isOwner = false;
  boolean isState = false;
  Object[] opObj = null;

  QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);

  while ( outputQr.hasMoreElements() ) {


    opObj = (Object[])outputQr.nextElement();
    output = (ProjectOutput)opObj[0];
    outputUser = output.getOwner() == null? null:(WTUser)output.getOwner().getPrincipal();
    outputData = new ProjectOutputData (output);
    String objType = "";
    if(output.getObjType() != null){
      objType = output.getObjType();
    }

    outputChargerName = "&nbsp;";
    deptName = "&nbsp;";
    version = "&nbsp;";
    lastVersion = "&nbsp;";
    state = "&nbsp;";
    modifyDate = "&nbsp;";
    peopleOID = "&nbsp;";
    peopleData = null;
    String fileUrl = "";
    String icon = "";
    ContentInfo info = null;
    ContentItem item = null;

     String loginOid = "";
      String security = "";
     Boolean isSecu = false;
    boolean isEtc = "ETC".equals(objType);
    if(outputUser != null) {
      /*
      outputChargerName = outputUser.getFullName();
      peopleData = new PeopleData(outputUser);
      deptName = peopleData.departmentName;
      peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
      */
    }

    if(!isEtc && outputData.isRegistry) {

      version = outputData.currentDocument.getVersionDisplayIdentifier().toString();
      versionOid = CommonUtil.getOIDString(outputData.currentDocument);
      lastVersion = outputData.LastDocument.getVersionDisplayIdentifier().toString();
      lastVersionOid = CommonUtil.getOIDString(outputData.LastDocument);
      state = outputData.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
      modifyDate = DateUtil.getDateString(outputData.currentDocument.getPersistInfo().getModifyStamp(), "D");
      modifyDate = modifyDate.substring(2, 10);
      ObjectData data = new ObjectData(outputData.LastDocument); /* 임영근g 최신것으로 바꿔주길 요청 */
      outputChargerName = outputData.currentDocument.getCreatorFullName();
      WTUser duser = null;
      //duser = (WTUser)outputData.currentDocument.getCreator().getPrincipal(); /* 최종작성자로 변경*/
      duser = (WTUser)outputData.LastDocument.getCreator().getPrincipal();
      //out.println(duser);
      if(duser != null){
        //peopleData = new PeopleData(outputUser);
        peopleData = new PeopleData(duser);
        deptName = peopleData.departmentName;
        //out.println(peopleData.name);
        peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
      }

      WTUser sessionUser  = (WTUser) SessionHelper.manager.getPrincipal();


// 산출물 열람 권한 추가 시작 #####################################
      if(CommonUtil.isMember("자동차PMO", sessionUser)) {
        isSecu = true;
      }else if(CommonUtil.isMember("자동차사업부_임원", sessionUser)) {
        isSecu = true;
      }else if(CommonUtil.isMember("전자사업부_임원", sessionUser)) {
        isSecu = true;
      }

// 산출물 열람 권한 추가 종료 ########################################


      loginOid = CommonUtil.getOIDString(sessionUser);
      if(loginOid.equals(outputData.LastDocument.getIterationInfo().getCreator().toString())){
        isSecu = true;
      }
      if(isSecu==false){
        isSecu = WorkflowSearchHelper.manager.userInApproval(outputData.LastDocument,sessionUser.getName());
      }

      //out.println(outputData.LastDocument);
      if(outputData.LastDocument instanceof KETProjectDocument)
      {
      security =  StringUtil.checkNull(((KETProjectDocument)outputData.LastDocument).getSecurity());
        if(!security.equals("S2")){
          isSecu = true;
        }
      }
      //out.println(isSecu);



      if(isSecu==false){
        isSecu = ProjectUserHelper.manager.isProjectRoleUser((TemplateProject)outputData.project);
      }
       if(isSecu==false){
         isSecu = ProjectUserHelper.manager.isProjectUser((TemplateProject)outputData.project);
       }

      if(CommonUtil.isAdmin())
      {
        isSecu = true;
      }

      if(project.getPjtType() == 3)
      {
        isSecu = true;
      }

      fileUrl = data.getFileUrl();

      icon = data.getIcon();

      FormatContentHolder holder = (FormatContentHolder) ContentHelper.service.getContents((ContentHolder) outputData.LastDocument);
      item = holder.getPrimary();

            if(holder != null){
              info = ContentUtil.getContentInfo(holder , item);
              if(item != null){
          icon = info.getIconURLStr();
          icon = icon.substring(icon.indexOf("<img"));
              }
          }

      if(fileUrl == null && data.getSecondary() != null){
        for(int i = 0; i < data.getSecondary().length; i++){
          Object oo[] = (Object[])data.getSecondary()[i];
            if(oo == null){
              continue;
            }else{
               fileUrl = (String)oo[0];
              break;
            }
        }



      }
      if(fileUrl == null){
        fileUrl = "";
        icon = "&nbsp;";
      }

    }

    //out.println(fileUrl);
    isOwner = false;
    if(outputUser != null && (wtuser.getName()).equals(outputUser.getName())) {
      isOwner = true;
    }

    KETProjectDocument docu = null;
    EPMDocument epm = null;
    FormatContentHolder cholder = null;
    ContentInfo cinfo = null;

    boolean isMoldProject = false;
    if(project instanceof MoldProject) {
      isMoldProject = true;
    }
%>

    <tr>
     <%
       if(outputData.document != null) {
         if( "승인완료".equals(state)) { %>
         <td class="tdwitheL"><input type="checkbox" name="LID" disabled/></td>
         <%}else{%>
         <td class="tdwitheL"><input type="checkbox" name="LID" value="<%=lastVersionOid%>"/></td>
         <%}
         if(isMoldProject) {
     %>
           <td class="tdwhiteL" title="<%=outputData.name%>" valign="middle">
          <div style="width:150;height:27;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr>
            <%if(buttonAuth.isPM && !outputData.isRegistry || isAdmin){ %>
              <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=outputData.name%></a>
            <%}else if(outputData.isRegistry){ %>
              <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=outputData.name%></a>
            <%}else{ %>
              <%=outputData.name%>
            <%} %>
            </nobr>
          </div>
        </td>
     <%
         }else{

           if(lastVersionOid != null) {
             if(outputData.objType == "문서") {
               docu = (KETProjectDocument)CommonUtil.getObject(lastVersionOid);
               cholder = (FormatContentHolder)docu;
              cinfo = ContentUtil.getPrimaryContent(cholder);
             }else if(outputData.objType == "도면") {
               epm = (EPMDocument)CommonUtil.getObject(lastVersionOid);
               cholder = (FormatContentHolder)epm;
              cinfo = ContentUtil.getPrimaryContent(cholder);
             }
       %>
          <td class="tdwhiteL" title="<%=cinfo != null ? cinfo.getName() : outputData.name%>" valign="middle">
            <div style="width:150;height:27;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr>
              <%if(buttonAuth.isPM && !outputData.isRegistry || isAdmin){ %>
                <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=cinfo != null ? cinfo.getName() : outputData.name%></a>
              <%}else if(outputData.isRegistry){ %>
                <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=cinfo != null ? cinfo.getName() : outputData.name%></a>
              <%}else{ %>
                <%=cinfo != null ? cinfo.getName() : outputData.name%>
              <%} %>
              </nobr>
            </div>
          </td>
      <%}
         }
    }else{%>
      <%if( "승인완료".equals(state)) { %>
        <td class="tdwitheL"><input type="checkbox" name="LID" disabled/></td>
      <%}else{%>
        <td class="tdwitheL"><input type="checkbox" name="LID" value=""/></td>
      <%} %>
      <td class="tdwhiteL" title="<%=outputData.name%>" valign="middle">
          <div style="width:150;height:27;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr>
            <%if(buttonAuth.isPM && !outputData.isRegistry || isAdmin){ %>
              <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=outputData.name%></a>
            <%}else if(outputData.isRegistry){ %>
              <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=outputData.name%></a>
            <%}else{ %>
              <%=outputData.name%>
            <%} %>
            </nobr>
          </div>
        </td>

    <%} %>
        <td class="tdwhiteM" title="<%=outputData.objType%>">
          <%=outputData.objType%>&nbsp;
          <%if(outputData.document != null){%><%if (lastVersionOid != null){%>
          <a href="#" onClick="javascript:doViewDoc('<%=lastVersionOid%>')">[<%=messageService.getString("e3ps.message.ket_message", "01527") %><%--보기--%>]</a>
          <%}else{ %>
          <a href="#" onClick="javascript:doViewDoc('<%=CommonUtil.getOIDString(outputData.document)%>')">[<%=messageService.getString("e3ps.message.ket_message", "01527") %><%--보기--%>]</a>
          <%}
          }%>
        </td>

        <td class="tdwhiteL" title="<%=outputData.isPrimary%>">
          <div style="width:50;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr><%=outputData.isPrimary%></nobr>
          </div>
        </td>
        <%if(outputData.objType.equals("문서")){ %>
        <td class="tdwhiteL" title="<%=(outputData.location==null)?"":outputData.location%>">
          <div style="width:100;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr><%=(outputData.location==null)?"":outputData.location%></nobr>
          </div>
        </td>
        <%}else{ %>
        <td class="tdwhiteL" title="">
        <%if(outputData.document != null && project.getPjtType() == 3){%><a href="#" onClick="javascript:doViewEpm('<%=project.getPjtNo()%>')">[<%=messageService.getString("e3ps.message.ket_message", "00926") %><%--관련도면--%>]</a><%}%>
        &nbsp;</td>
        <%} %>
        <td class="tdwhiteM" title="<%=deptName%>">
<%
    if(peopleData == null) {
%>
                    &nbsp;
<%
    } else {
%>
                    <a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=peopleData.name%></a>
<%
    }
%>
                  </td>
<%
    if (outputData.isRegistry ) {

        if(!isEtc){

        Iterated pre = VersionControlHelper.service.predecessorOf(outputData.LastDocument);
        String preVer = null;

        String preOid = null;

        if(preOid == null || preOid.length() == 0){
          preOid = versionOid;
        }

        if(preVer == null || preVer.length() == 0){
          preVer = version;
        }
        if(pre instanceof RevisionControlled){
          RevisionControlled preRc = (RevisionControlled)pre;
          preVer = preRc.getVersionIdentifier().getValue();
          preOid = preRc.getPersistInfo().getObjectIdentifier().getStringValue();
        }
%>
        <td class="tdwhiteM">
        <!-- 최신버전만 보여지도록 이전버전 주석처리 -->
        <!-- <a href="#" onClick="javascript:ViewDoc('<--%=preOid%>')"><font color="blue" ><--%=preVer%><--%//=version%></font></a>/ -->
        <a href="#" onClick="javascript:ViewDoc('<%=lastVersionOid%>')"><font color="blue" ><%=lastVersion%></font></a></td>
        <td class="tdwhiteM"><%=state%></td>
        <td class="tdwhiteM"><%=modifyDate%></td>
           <td class="tdwhiteM"><a href="<%=fileUrl%>"><%=icon%></a></td>
    <%}else{

      String completeReson =  output.getComplete_reason();
      if(completeReson == null){
        completeReson = "";
      }
      completeReson = completeReson.trim();

    %>
        <td class="tdwhiteM" colspan="4" width="230">
          <a href="#" onClick="javascript:openCompleteReson('<%=outputData.oid%>');"> <%=StringUtil.getLeft(completeReson, 15)%></a>
          <%if("ECO_No.기입".equals(outputData.name)){
              String isProduct = "";
              if(project instanceof ProductProject){
                isProduct = "P";
              }
              String ecoOid = EcmSearchHelper.manager.getEcoObjectOid(completeReson);
          %>
            <%if(ecoOid != null && ecoOid.length() > 0){ %>
              <%if("제품도".equals(taskData.taskName)){ %>
              <a href="javascript:;" onClick="javascript:viewEcoPop('<%=ecoOid %>', 'P');"> [<%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%>]</a>
              <%}else{ %>
              <a href="javascript:;" onClick="javascript:viewEcoPop('<%=ecoOid %>', '');"> [<%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%>]</a>
              <%} %>
            <%} %>
          <%} %>
        </td>
    <%}%>
<%
    }
    else {



      int docCreateOrLinkType = buttonAuth.isOutputDocCreateOrLink(output);
      if( docCreateOrLinkType == TaskViewButtonAuth.CREATEORLINK || CommonUtil.isBizAdmin() ) {

      if(objType.equals("DOC")){
%>
        <td class="tdwhiteM" colspan="4" width="230">
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
              <td align="right" >
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
                   </td>
              <td width="5">&nbsp;</td>
              <td>
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
               </td>
            </tr>
          </table>
        </td>
      <%}else if(objType.equals("ETC")){%>
       <td class="tdwhiteM" colspan="4" width="230">
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01172") %><%--내역입력--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
               </td>
            </tr>
          </table>
        </td>

      <%}else if(isElectron && objType.equals("DWG") && !( pjtType.equals("0")|| pjtType.equals("1") ) ){%>
        <td class="tdwhiteM" colspan="4" width="230">
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
              <td align="right" >
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>', 'true');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
                   </td>
              <td width="2">&nbsp;</td>
              <td>
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>', 'false');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
               </td>
              <td width="5">&nbsp;</td>
              <td>
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01349") %><%--링크--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
               </td>
            </tr>
          </table>
        </td>

      <%}else{ %>
        <td class="tdwhiteM" colspan="4" width="230">
          <table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>&nbsp;</td>
              <%if("제품도출도".equals(taskData.taskName)){
              %>

                   <td align="right" >
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>', 'false');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
                   </td>
              <%}else{ %>
              <td align="right" >
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputOutputResult('<%=outputData.oid%>','<%=objType%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02722") %><%--직접작성--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
                   </td>
                   <%} %>
              <td width="5">&nbsp;</td>
              <td>
              <table border="0" cellspacing="0" cellpadding="0">
                     <tr>
                       <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                       <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:inputLinkOutput('<%=outputData.oid%>','<%=objType%>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%></a></td>
                       <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                     </tr>
                   </table>
               </td>
            </tr>
          </table>
        </td>
      <%} %>
<%
      }
      else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_CREATE){
%>
        <td class="tdwhiteM" colspan="4"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01333") %><%--등록안됨--%></font></td>
<%
      }else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_TASK_MEMBER){
%>
        <td class="tdwhiteM" colspan="4"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01210") %><%--담당자 지정안됨--%></font></td>
<%
      }else if(false && docCreateOrLinkType == TaskViewButtonAuth.NOT_PROGRESS){
%>
        <td class="tdwhiteM" colspan="4"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01463") %><%--미진행상태--%></font></td>
<%
      }else{
%>
        <td class="tdwhiteM" colspan="4"><font color="red">-</font></td>
<%       }
    }
%>
        <td class="tdwhiteM" width="150">
<%



    int outputCompletionAuthType = buttonAuth.isOutputCompletion(output);



    if(outputCompletionAuthType == TaskViewButtonAuth.ACTIONCOMPLECTION) {
%>
        <input name="outputCompletion" class="txt_field" style="width:30%;text-align:right;" size="3" maxlength="3" value="<%=output.getCompletion()%>" progresskey="<%=outputData.oid%>" onkeyup ="SetNum(this)"/><b>%</b>&nbsp;

        <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "02380") %><%--입력--%>' onClick="javascript:outputProgress('<%=outputData.oid%>' , '<%=objType %>');" class="s_font">
<%
    }
    else if(outputCompletionAuthType == TaskViewButtonAuth.NOT_PROGRESS){
      out.println(output.getCompletion() + "%");
    }
    else if(outputCompletionAuthType == TaskViewButtonAuth.NOTACTIONCOMPLECTION){

      out.println(output.getCompletion() + "%");
    }

    else if(outputCompletionAuthType == TaskViewButtonAuth.NOTEXISTCOMPLETION){

      out.println("-");
    }
%>
    </td>
<%
    if( (task.getTaskState() != ProjectStateFlag.TASK_STATE_COMPLETE && buttonAuth.isDeleteOutput(output)) && buttonAuth.isOuputDocAdd || isAdmin || isEdit){ //buttonAuth.isDeleteOutput(output)) {
%>
      <td class="tdwhiteM">
        <a href="JavaScript:deleteOutput('<%=outputData.oid%>')"><p><img src="/plm/portal/images/icon_delete.gif" width="13" height="12" border="0"></p></a>
      </td>
<%
    }else{%>
      <td class="tdwhiteM">
       -
      </td>
    <%}%>


    </tr>
<%
  }
%>
      </table>
      <!-- 산출물 끝 -->
      <%if("DR".equals(task.getTaskType())){ %>
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
          <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00154") %><%--DR 관리--%></td>
            </tr>
        </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="780">
      <%
      e3ps.dms.entity.KETProjectDocument drDocument = ProjectOutputHelper.manager.getDRTaskOutput(task);
      int drPoint = 0;
      String drApprovalResult = "";
      String lastComment = "";
      if(drDocument != null){
        drPoint = drDocument.getDRCheckPoint();

        if(drDocument != null) {

          if(drDocument.getApprovalResult() != null) drApprovalResult = drDocument.getApprovalResult();
          if("승인".equals(drApprovalResult)) drApprovalResult = "GOOD";
          else if("반려".equals(drApprovalResult)) drApprovalResult = "<span class='red'>NG</span>";
        }
        if(drDocument.getLastApprovalComment() != null){
          lastComment = drDocument.getLastApprovalComment();
        }
      }

      String dept = "&nbsp;";

      QueryResult rs = TaskUserHelper.manager.getTaskUser(task, TaskUserHelper.PL);

      while(rs.hasMoreElements()){
        Object o = ((Object[])rs.nextElement())[0];
        TaskMemberLink link = (TaskMemberLink)o;

        WTUser user = link.getMember().getMember();
        PeopleData pData = new PeopleData(user);

        dept = pData.departmentName;
      }


      %>
      <COL width="10%"><COL width="15%"><COL width="10%"><COL width="15%">
      <COL width="10%"><COL width="15%"><COL width="10%"><COL width="15%">
        <tr>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02672") %><%--주관부서--%></td>
          <td class="tdwhiteM"><%=dept %></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%></td>
          <td class="tdwhiteM"><%=(task.getDrValue()==null)?"":task.getDrValue() %></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00157") %><%--DR 점수--%></td>
          <td class="tdwhiteM"><%=drPoint%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02970") %><%--판정결과--%></td>
          <td class="tdwhiteM"><%=drApprovalResult%>&nbsp;</td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01988") %><%--승인 의견--%></td>
          <td class="tdwhiteL0" colspan=7>
            <textarea name="appDesc" style="width:100%" rows="3" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" readOnly><%=lastComment%>&nbsp;</textarea>
          </td>
        </tr>
      </table>
      <%} %>
      <%
      String taskName = task.getTaskName();
      boolean isTask = taskName.startsWith("제품측정");


      if(isTask || "제품 측정".equals(taskName)){ %>
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02627") %><%--제품측정현황--%></td>
              <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <%
                  String readOnlySelect = "";
                  String disFalse = "";
                  if(buttonAuth.isTaskInfoModify|| CommonUtil.isAdmin()){
                  %>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:savePoint();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  <%
                  }else{
                    readOnlySelect = "readOnly";
                    disFalse = "disabled";
                  }
                  %>
                  </tr>
                </table></td>
            </tr>
          </table>
          <table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>
          <table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td  class="tab_btm2"></td>
            </tr>
          </table>
          <table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02897") %><%--측정Point--%></td>
          <td width="130" class="tdwhiteL"><input type="text" name="checkDescPoint" value="<%=checkDescPoint %>" style="width=90" <%=readOnlySelect%> ></input> Point</td>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01630") %><%--불합격Point--%></td>
          <td width="130" class="tdwhiteL"><input type="text" name="nonPassPoint" value="<%=nonPassPoint %>" style="width=90;color=red" <%=readOnlySelect%> ></input> Point</td>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02898") %><%--측정결과--%></td>
          <td width="130" class="tdwhiteL0">
            <select name="checkResult" class="fm_jmp" style="width:100%">
              <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
              <option value="합격" style="color=blue" <%if("합격".equals(checkResult)){ %>selected<%} %>  <%=disFalse %>><%=messageService.getString("e3ps.message.ket_message", "03150") %><%--합격--%></option>
              <option value="불합격" style="color=red" <%if("불합격".equals(checkResult)){ %>selected<%} %>  <%=disFalse %>><%=messageService.getString("e3ps.message.ket_message", "01627") %><%--불합격--%></option>
            </select>
          </td>
        </tr>
        <tr>
          <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
          <td colspan="5" class="tdwhiteL0"><textarea name="checkEtc" id="checkEtc" rows="3" class="fm_area" style="width:100%" <%=readOnlySelect%> ><%=checkEtc %></textarea></td>
        </tr>
          </table>
      <%} %>
      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->
      <!-- TASK 상세정보 -->
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>

      <%if(!buttonAuth.isChild){ %>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
          <td class="tdwhiteL">
            <jsp:include page="/jsp/project/TaskDependencyInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oid%>"/>
              <jsp:param name="addTask" value=""/>
              <jsp:param name="deleteTask" value=""/>
              <jsp:param name="popup" value="<%=popup%>"/>
            </jsp:include>
          </td>
        </tr>
      </table>

      <%}%>

      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!------------------------------ 본문 끝 //------------------------------>

      <!-- 개발 프로젝트 -->

      <!-- ####################  이슈관리 Start  ##################################-->
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
            <tr>
              <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
              <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00112") %><%--CFT 요청사항--%></td>
              <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                  <%if(buttonAuth.isLatest && !buttonAuth.isCheckOutOrg && buttonAuth.isProgress && buttonAuth.isMember){ %>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:addIssue();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  <%} %>
                  </tr>
                </table></td>
            </tr>
          </table>
          <table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>
          <table border="0" cellspacing="0" cellpadding="0" width="780">
            <tr>
              <td  class="tab_btm2"></td>
            </tr>
          </table>
      <table border="0" cellspacing="0" cellpadding="0" width="780">
                    <tr>
                      <td width="40" class="tdblueM">No</td>
                      <td width="60" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                      <td width="200" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                      <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
                      <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                      <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                      <td width="70" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                      <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02180") %><%--완료처리여부--%></td>
                      <td width="40" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02794") %><%--첨부--%></td>
                      <td width="40" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                    </tr>
              <%
          QueryResult qr = ProjectIssueHelper.manager.getTaskIssue(task);
              int count = qr.size();
          while ( qr.hasMoreElements() ) {
            Object[] objArr = (Object[])qr.nextElement();
            ProjectIssue issue = (ProjectIssue)objArr[0];
            ProjectIssueData data = new ProjectIssueData(issue);
          %>
                    <tr>
                      <td width="40" class="tdwhiteM"><%=count--%></td>
                      <td width="60" class="tdwhiteM">&nbsp;<%=data.issueType%></td>
                      <td width="200" class="tdwhiteL">&nbsp;<a href="javascript:viewIssue('<%=data.oid%>');"><%=data.subject%></a></td>
                      <td width="70" class="tdwhiteM">&nbsp;<%=data.questionUser.name%></td>
                      <td width="90" class="tdwhiteM">&nbsp;<%=StringUtil.checkNull(data.questionUser.departmentName)%></td>
                      <td width="70" class="tdwhiteM">&nbsp;<%=DateUtil.getTimeFormat(data.questionDate, "yy-MM-dd")%></td>
                      <td width="70" class="tdwhiteM">&nbsp;<%=data.managerUser.name%></td>
                      <td width="100" class="tdwhiteM">&nbsp;<%=data.isCheck?messageService.getString("e3ps.message.ket_message", "02171")/*완료*/:messageService.getString("e3ps.message.ket_message", "01454")/*미완료*/%></td>
                      <td width="40" class="tdwhiteM"><%if(data.isQuestionAttache){%><img src="/plm/portal/images/icon_file.gif" border="0"><%}else{%>&nbsp;<%}%></td>
                      <td width="40" class="tdwhiteM0">
                      <%if((data.isQuestionUser)&& !data.isCheck || isAdmin){%>
                      <a href="JavaScript:deleteIssue('<%=data.oid%>')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      <%}else{%>&nbsp;<%}%></td>
                    </tr>
              <%
            Vector answerVec = data.issueAnswer;
                Collections.sort(answerVec, new IssueAnswerComparator(false));
                for ( int i = 0 ; i < answerVec.size() ; i++ ) {
              ProjectIssueAnswerData answerData = (ProjectIssueAnswerData)answerVec.get(i);
          %>
                    <tr>
                      <td width="40" class="tdwhiteM">&nbsp;</td>
                      <td width="60" class="tdwhiteM">&nbsp;<% %></td>
                      <td width="200" class="tdwhiteL">&nbsp;
                      <a href="javascript:viewIssue('<%=answerData.oid%>');"><img src="/plm/portal/images/icon_re.gif" border="0">Re ) <%=StringUtil.getLeft(answerData.answerStr,20)%></a></td>
                      <td width="70" class="tdwhiteM">&nbsp;<%=data.questionUser.name%></td>
                      <td width="90" class="tdwhiteM">&nbsp;<%=StringUtil.checkNull(answerData.answerUser.departmentName)%></td>
                      <td width="70" class="tdwhiteM">&nbsp;<%=DateUtil.getTimeFormat(answerData.answerDate, "yy-MM-dd")%></td>
                      <td width="70" class="tdwhiteM">&nbsp;<%=answerData.answerUser.name%></td>
                      <td width="100" class="tdwhiteM">&nbsp;<%=answerData.answer.isIsCheck()?messageService.getString("e3ps.message.ket_message", "02171")/*완료*/:messageService.getString("e3ps.message.ket_message", "01454")/*미완료*/%></td>
                      <td width="40" class="tdwhiteM"><%if(answerData.isAnswerAttache){%><img src="/plm/portal/images/icon_file.gif" border="0"><%}else{%>&nbsp;<%}%></td>
                      <td width="40" class="tdwhiteM0">
                      <%if((answerData.isAnswerUser)&& !data.isCheck || isAdmin){%>
                      <a href="JavaScript:deleteIssue('<%=answerData.oid%>')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                      <%}else{%>&nbsp;<%}%></td>
                    </tr>
              <%
            }
          }
              %>
                  </table>
          </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="780">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>

    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright<%Kogger.debug("popup = " + popup);if(popup != null && !("null".equals(popup))){ %>_t<%} %>.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
</html>
<%}catch(Exception e){
}

%>


