<%@page import="e3ps.ews.dao.PartnerDao"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import = "java.util.*,
                  wt.fc.*,
                  wt.org.*,
                  wt.team.*,
                  wt.project.Role,
                  wt.vc.*,
                  wt.part.*,
                  wt.lifecycle.*,
                  wt.workflow.work.WorkItem,
                  wt.workflow.engine.WfActivity,
                  wt.vc.wip.*"%>
<%@page import="e3ps.groupware.company.*,
        e3ps.common.util.*,
        e3ps.common.content.*,
        e3ps.common.jdf.log.Logger,
        e3ps.project.*,
        e3ps.project.beans.*,e3ps.sap.*" %>
<%@page import="e3ps.project.CheckoutLink"%>
<%@page import="e3ps.project.historyprocess.HistoryHelper"%>
<%@page import="e3ps.common.jdf.config.Config"%>
<%@page import="e3ps.common.jdf.config.ConfigImpl"%>
<%@page import="wt.lifecycle.LifeCycleHelper"%>
<%@page import="wt.lifecycle.State"%>
<%@page import="e3ps.common.query.*,
        wt.folder.*,
        wt.workflow.engine.WfProcess,
        wt.session.*"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="e3ps.project.outputtype.OEMPlan"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.wfm.entity.KETWfmApprovalMaster"%>
<%@page import="wt.query.OrderBy"%>
<%@page import="wt.query.ClassAttribute"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="e3ps.common.mail.MailUtil"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->
<%

  WTUser sessionUser = (WTUser)SessionHelper.manager.getPrincipal();
  PeopleData pd = new PeopleData(sessionUser);
  //parameter
  String radioValue = request.getParameter("radioValue");
  if(radioValue == null){
    radioValue = "1";
  }
  String popup = StringUtil.checkNull( request.getParameter("popup") );
  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  ReviewProject rproject =(ReviewProject)CommonUtil.getObject(oid);
  String type = request.getParameter("type");
  boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
  boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;
  boolean canDelete = ProjectUserHelper.manager.isPM(project) || isAdmin;    //Delete(isPM || isAdmin)
  QueryResult wfresult = wt.workflow.engine.WfEngineHelper.service.getAssociatedProcesses((Persistable)project, null, WCUtil.getWTContainerRef());
  boolean isCS = CommonUtil.isMember("공정감사");

  // [START] [PLM System 1차개선] 일정변경구분 가져오기, 2013-08-05, BoLee
  int historyNoteType = project.getHistoryNoteType();
  // [END] [PLM System 1차개선] 일정변경구분 가져오기, 2013-08-05, BoLee

  // #####################################  wfProcessOid
  String wfProcessOid = "";
  wt.workflow.engine.WfProcess wfprocess = null;
  wt.workflow.engine.WfState wfState = null;
  while (wfresult.hasMoreElements()) {
    wfprocess = (wt.workflow.engine.WfProcess)wfresult.nextElement();
    wfProcessOid = wfprocess.getPersistInfo().getObjectIdentifier().getStringValue();
    wfState = wfprocess.getState();

  }

  //##################################### 성과 관리
  Performance pf = null;
  QueryResult qrtest = PerformanceHelper.manager.searchPerformance(project.getPjtNo());
  Object[] pobj = null;
  if(qrtest.hasMoreElements()){
    pobj = (Object[])qrtest.nextElement();
    pf = (Performance)pobj[0];
  }
  //##################################### 성과 관리

  ReviewDevelop rd = null;
  QuerySpec rdqs = null;
  QueryResult rdqr = null;
  try{
    rdqs = new QuerySpec(ReviewDevelop.class);
    rdqs.appendWhere(new SearchCondition(ReviewDevelop.class,"projectReference.key.id","=",CommonUtil.getOIDLongValue(project)), new int[0]);
    rdqr = PersistenceHelper.manager.find(rdqs);
    if(rdqr.hasMoreElements()){
      rd = (ReviewDevelop)rdqr.nextElement();
    }

  }catch(Exception e){
      Kogger.error(e);
  }

  String isReload = request.getParameter("isReload");
  if(StringUtil.checkString(isReload) && isReload.equals("true")) {
%>
    <script language="JavaScript">
      //parent.tree.location.href = "/plm/jsp/project/ProjectTree.jsp?oid=<%=oid%>";
      //parent.link.location.href = "/plm/jsp/project/ProjectLinkFrm.jsp?oid=<%=oid%>";
      parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>');
    </script>
<%
  }
  if(type==null||type.length()==0)
      type="member";

  //프로젝트
  String command = request.getParameter("command");
  if ("stateChange".equals(command)) {
    String state = request.getParameter("state");
    ProjectHelper.changeProjectState(project, state);
    //if("PROGRESS".equals(state))
      //ProjectHelper.manager.projectSendMail(project, "restart");
%>
    <script language="JavaScript">
      //parent.tree.location.href = "/plm/jsp/project/ProjectTree.jsp?oid=<%=oid%>";
      //parent.link.location.href = "/plm/jsp/project/ProjectLinkFrm.jsp?oid=<%=oid%>";
      <%if("popup".equals(popup)){%>
        parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=<%=popup%>";
      <%}else{%>
        parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>');
      <%}%>
    </script>
<%
  }
  // Template Code
  TemplateProject tp = null;
  TemplateProjectData tpdata = null;
  if(project.getTemplateCode() != null) {
    tp = ProjectHelper.getTemplate(project.getTemplateCode());
    tpdata = new TemplateProjectData(tp);
  }

  E3PSProjectData projectData = new E3PSProjectData(project);
  ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);


  boolean isCheck = project.isCheckOut();
  String cmd = request.getParameter("cmd");
  if(cmd == null){
    cmd = "";
  }

  String deleteIssue = request.getParameter("deleteIssue");
  if ( deleteIssue != null ) {
    ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
  }

  // ##################################### 일정 정보
  String projectStartDate = "";
  if(projectData.pjtExecStartDate != null){
    projectStartDate = DateUtil.getDateString(projectData.pjtExecStartDate, "D");
  }else{
    projectStartDate = DateUtil.getDateString(projectData.pjtPlanStartDate, "D");
  }

  String projectEndDate = "";
  if(projectData.pjtExecEndDate != null){
    projectEndDate = DateUtil.getDateString(projectData.pjtExecEndDate, "D");
  }else{
    projectEndDate = DateUtil.getDateString(projectData.pjtPlanEndDate, "D");
  }
  // ##################################### 일정 정보 끝


  if(cmd.equals("out")){


      CheckoutLink workCopyOid = null;
    String coMsg = "";
    try {
      workCopyOid = (CheckoutLink)HistoryHelper.checkOut(project, "일정변경", 1);
    }
    catch(Exception e) {
	Kogger.error(e);
      coMsg = e.getMessage();
    }

    if(coMsg.length() > 0) {
      coMsg = "\n" + coMsg;
    }
    if(workCopyOid == null){
    %>
    <script>
      alert("<%=messageService.getString("e3ps.message.ket_message", "00119") %><%--Check Out 실패 하였습니다--%>");
      document.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>";
    </script>
  <%
    }else{
      String copyOid = CommonUtil.getOIDString(workCopyOid.getWorkingCopy());
  %>
    <script>
      alert("<%=messageService.getString("e3ps.message.ket_message", "00118") %><%--Check Out 되었습니다--%>");
      <%if(popup != null && popup.length() > 0 ){ %>
      //parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=copyOid%>&popup=<%=popup%>";
      <%}else{%>
      parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=copyOid%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=copyOid%>&cmd=');
      <%}%>
    </script>
  <%  }
  }

  if(cmd.equals("In")){
    E3PSProject orn = null;
      String coMsg = "";
    try {
      orn = (E3PSProject)HistoryHelper.checkIn(project, "일정변경");
    }
    catch(Exception e) {
	Kogger.error(e);
      coMsg = e.getMessage();
    }

    if(orn == null){
    %>
    <script>
      alert("<%=messageService.getString("e3ps.message.ket_message", "00117") %><%--Check In 실패 하였습니다--%>");
      document.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>";
    </script>
  <%
    }else{
      //String copyOid = CommonUtil.getOIDString(workCopyOid.getWorkingCopy());
  %>
    <script>
      alert("<%=messageService.getString("e3ps.message.ket_message", "00116") %><%--Check In 되었습니다--%>");
      <%if(popup != null && popup.length() > 0 ){ %>
      //parent.document.location.href  = "/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=oid%>&popup=<%=popup%>";
      <%}else{%>
      parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>&cmd=');
      <%}%>
    </script>
  <%  }
  }
  WTUser wtuser = null;

    String wtUserOid = "";
  if(cmd.equals("dev")){
    String coMsg = "";
    try {
      if("전장모듈개발1팀".equals( projectData.devDept )){
        wtUserOid = "wt.org.WTUser:28088";
        wtuser = (WTUser)CommonUtil.getObject(wtUserOid);
      }else{
        wtuser = (WTUser)PeopleHelper.manager.getChiefUser(rproject.getDevDept());
      }
      if(wtuser != null){
        WTPrincipal wtuserPrin = (WTPrincipal)wtuser;
        e3ps.project.workprocess.ExpressionUtil.addPrincialToRole(project, "ASSIGNEE", wtuserPrin);
        project = (E3PSProject)LifeCycleHelper.service.setLifeCycleState((LifeCycleManaged)project, State.toState("DEVASSIGN"), true);
        PersistenceHelper.manager.refresh(project);
        ProjectHelper.manager.projectSendMailAssign(project, "assign", wtuser, true);
      }
      //PersistenceHelper.manager.refresh(project);
    }
    catch(Exception e) {
	Kogger.error(e);
      coMsg = e.getMessage();
    }

    if(wtuser == null){
    %>
    <script>
      alert('<%=messageService.getString("e3ps.message.ket_message", "01202") %><%--담당부서 팀장을 지정 하십시오--%>');
      document.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>";
    </script>
  <%
    }else{
      //String copyOid = CommonUtil.getOIDString(workCopyOid.getWorkingCopy());
  %>
    <script>
    alert('<%=messageService.getString("e3ps.message.ket_message", "00670") %><%--개발팀 이관 되었습니다.--%>');
      parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=CommonUtil.getOIDString(project)%>', '/plm/jsp/project/ReviewProjectView.jsp?oid=<%=CommonUtil.getOIDString(project)%>&cmd=')

    </script>
  <%  }
  }


  if("reassign".equals(cmd)) {
    String lifecycle = "";
    wt.lifecycle.LifeCycleManaged lcm = (wt.lifecycle.LifeCycleManaged)project;
    LifeCycleTemplate lct2 = (LifeCycleTemplate)lcm.getLifeCycleTemplate().getObject();
    lifecycle = lct2.getName();

    try {
      LifeCycleTemplate lct = LifeCycleHelper.service.getLifeCycleTemplate(lifecycle, WCUtil.getWTContainerRef());
      LifeCycleHelper.service.reassign((LifeCycleManaged)project, lct.getLifeCycleTemplateReference(),WCUtil.getWTContainerRef());
    }
    catch(Exception e) {
	Kogger.error(e);
    }
%>
  <script language="javascript">
    alert("<%=messageService.getString("e3ps.message.ket_message", "00441") %><%--Reassign 완료--%>");
    document.location.href = "/plm/jsp/project/ReviewProjectView.jsp?oid=<%=oid%>";
  </script>
<%
  }

  String projectTypeValue = "";
  if(rproject.getAttr1().equals("자동차사업부")){
    projectTypeValue = "자동차";
  }else if(rproject.getAttr1().equals("자동차")){
    projectTypeValue = "자동차";
  }
  if(rproject.getAttr1().equals("전자사업부")){
    projectTypeValue = "전자";
  }else if(rproject.getAttr1().equals("전자")){
    projectTypeValue = "전자";
  }
%>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--

  function openViewScroll(url)
  {
    var screenWidth = screen.availWidth/2-320;
    var screenHeight = screen.availHeight/2-300;
    var windowWin = window.open(url,"","status=no,resizable=yes, scrollbars=yes,width=835,height=600,top="+screenHeight/2+",left="+screenWidth/2);
    windowWin.focus();
  }
  function viewTemplate(oid){
    var url = "/plm/jsp/project/template/TemplateProjectView.jsp?oid="+oid;
    openOtherName(url,"ViewTemplate","800","500","status=1,scrollbars=no,resizable=1");
  }

  function purchase(projectNo){
  var url="/plm/jsp/project/SapPurchaseInterfaceView.jsp?projectNo="+projectNo;
  openOtherName(url,"purchase","1000","600","status=no,scrollbars=yes,resizable=no");
  }

  function approvalAction(oid){
    var url="/plm/jsp/wfm/RequestApproval.jsp?pboOid="+oid+ "&popup=<%=popup%>";
    openOtherName(url,"approval","800","700","status=no,scrollbars=yes,resizable=no");
  }

  function changeHistory(oid){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03325") %><%--일정 변경을 하시겠습니까?--%>")) {
      return;
    }

    disabledAllBtn();
    onProgressBar();
    document.forms[0].cmd.value = "out";
    document.forms[0].oid.value = oid;
    document.forms[0].submit();
  }

  function ChangeHistorySave(){
    var url="/plm/jsp/project/ProjectHistoryList.jsp?oid=<%=oid%>";
    openOtherName(url,"process","900","800","status=no,scrollbars=no,resizable=no");
  }

  function changeHistoryIn(oid){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03271") %><%--Check In 하시겠습니까?--%>")) {
      return;
    }

    disabledAllBtn();
    onProgressBar();
    document.forms[0].cmd.value = "In";
    document.forms[0].oid.value = oid;
    document.forms[0].submit();
  }
  function devAction(oid){
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "00671") %><%--개발팀 이관을 하시겠습니까?--%>')) {
      return;
    }

    disabledAllBtn();
    onProgressBar();
    document.forms[0].cmd.value = "dev";
    document.forms[0].oid.value = oid;
    document.forms[0].submit();

  }

  function onReassign(oid){
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03276") %><%--LC ReAssign 하시겠습니까?--%>")) {
      return;
    }

    disabledAllBtn();
    onProgressBar();
    document.forms[0].cmd.value = "reassign";
    document.forms[0].oid.value = oid;
    document.forms[0].submit();

  }

  function ChangeHistory(){
    var url="/plm/jsp/project/ProjectHistoryList.jsp?oid=<%=oid%>";
    openOtherName(url,"process","900","800","status=no,scrollbars=no,resizable=no");
  }

    // [START] [PLM System 1차개선] 일정 변경 이력 팝업 사이즈 변경, 2013-07-07, BoLee
  function projectHistory(){
    var url="/plm/jsp/project/ProjectHistoryList.jsp?oid=<%=oid%>";
    openOtherName(url,"process","950","550","status=no,scrollbars=no,resizable=no");
  }
    // [END] [PLM System 1차개선] 일정 변경 이력 팝업 사이즈 변경, 2013-07-07, BoLee

  function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","400","status=no,scrollbars=no,resizable=no");
  }

  function viewCurrentChart(oid){
    width = 0;
    height = 0;
    var screenWidth = 0 ;
    var screenHeight = 0 ;
    var maxWidth = screen.availWidth;
    var maxHeight = screen.availHeight;
    var windowWin = window.open("/plm/jnlp/ProjectChart.jsp?oid="+oid,"chart","status=no,scrollbars=yes,resizable,width="+maxWidth+",height="+maxHeight+",top="+screenHeight+",left="+screenWidth);
    //windowWin.focus();
  }

  function viewChart(){
    width = 600;
    height = 400;
    var screenWidth = (screen.availWidth-width)/2;
    var screenHeight = (screen.availHeight-height)/2;
    var url = "/plm/jsp/project/ProjectChartView.jsp?projectNo=<%=projectData.pjtNo%>";
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=600,height=400,resizable=no,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(600,300);
    newWin.focus();
  }


  function updatePLMProject(){
    width = 830;
    height = 700;
    var screenWidth = (screen.availWidth-width)/2;
    var screenHeight = (screen.availHeight-height)/2;
    var url = "/plm/jsp/project/UpdateReviewItemInfo.jsp?oid=<%=oid%>";
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width="+width+"height="+height+",resizable=yes,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(830,700);
    newWin.focus();
  }

  function openView( url ) {
    newWin = window.open(url,"window","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=700,resizable=no,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(800,700);
    newWin.focus();
  }
  function addIssue() {
    var url = "/plm/jsp/project/projectIssueCreate.jsp?oid=<%=oid%>&review=review";
    openOtherName(url,"addIssue","760","700","status=no,scrollbars=yes,resizable=yes");
  }
  function projectComplete(v){
    width = 600;
    height = 400;
    var screenWidth = (screen.availWidth-width)/2;
    var screenHeight = (screen.availHeight-height)/2;
    if ( v == '<%=ProjectStateFlag.PROJECT_STATE_COMPLETE%>' ) {
      if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03103") %><%--프로젝트를 종료합니다.\n종료된 프로젝트는 재진행 할수 없습니다.\n종료하시겠습니까?--%>') ) {
        var url = "/plm/jsp/project/ProjectStateCheck.jsp?state="+v+"&oid=<%=oid%>";
        newWin =       window.open(url,"compWindow","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=800,height=600,resizable=no,top="+screenHeight+",left="+screenWidth);
        //newWin.resizeTo(800,600);
        newWin.focus();
      }
    }
  }

  function stateChangeStop(oid){
    var url = "/plm/jsp/project/StopHistory.jsp?oid="+oid;
    openOtherName(url,"ChangeStop","780","730","status=no,scrollbars=yes,resizable=no");
  }
  function stateChangeStopView(oid){
    var url = "/plm/jsp/project/StopHistoryList.jsp?oid="+oid;
    openSameName(url,"620","500","status=no,scrollbars=yes,resizable=yes");
  }

  function stateChangeReStart(oid){
    var url = "/plm/jsp/project/ReStartHistory.jsp?oid="+oid;
    openOtherName(url,"ChangeStop","780","730","status=no,scrollbars=yes,resizable=no");
  }

  function stateChangeResult(oid){
    var url = "/plm/jsp/project/CreateReviewDevelop.jsp?oid="+oid;
    openSameName(url,"800","550","status=no,scrollbars=no,resizable=no");
  /*
    if ( confirm("프로젝트를 " + message + "상태로 변경합니다.\n변경하시겠습니까?") ) {
        document.forms[0].action = "/plm/jsp/project/ReviewProjectView.jsp";
        document.forms[0].command.value = "stateChange";
        document.forms[0].state.value = statevalue;
        document.forms[0].method = "post";
        document.forms[0].submit();
    }
  */

  }


  function stateChange(statevalue, message){
    if ( confirm("프로젝트를 " + message + "상태로 변경합니다.\n변경하시겠습니까?") ) {
        document.forms[0].action = "/plm/jsp/project/ReviewProjectView.jsp";
        document.forms[0].command.value = "stateChange";
        document.forms[0].state.value = statevalue;
        document.forms[0].method = "post";

        document.forms[0].submit();
        return;
    }
  }

  function deleteIssue(v) {
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
      document.forms[0].action = "/plm/jsp/project/ReviewProjectView.jsp?deleteIssue="+ v;
      document.forms[0].command.value = "deleteIssue";
      document.forms[0].method = "post";
      document.forms[0].submit();
    }
  }

  function projectStart(v) {
    if ( v == '<%=ProjectStateFlag.PROJECT_STATE_READY%>' ) {
      if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03345") %><%--프로젝트를 시작하려고 합니다..\n시작하시겠습니까?--%>") ) {
        document.forms[0].action = "/plm/jsp/project/ReviewProjectView.jsp?isReady="+v;
        document.forms[0].method = "post";
        document.forms[0].submit();
        return;
      }
    }
  }

  function listScheduleHistory() {
    //var url = "/plm/jsp/project/SaveProjectScheduleForm.jsp?oid=<%=oid%>";
    width = 600;
    height = 400;
    var screenWidth = (screen.availWidth-width)/2;
    var screenHeight = (screen.availHeight-height)/2;
    var url = "/plm/jsp/project/ProjectChartView.jsp?projectNo=<%=projectData.pjtNo%>&oid=<%=oid%>";
    newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=600,height=400,resizable=no,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(600,300);
    newWin.focus();
  }

  function saveTempleteProject() {
    var screenWidth = screen.availWidth/2-150;
    var screenHeight = screen.availHeight/2-75;
    var url = "/plm/jsp/project/SaveTempleteProjectForm.jsp?oid=<%=oid%>";
    newWin = window.open(url,"window", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=550,height=350,resizable=no,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(350,150);
    newWin.focus();
  }

  function reloadTree() {
    //parent.tree.location.reload();
    parent.tree.document.forms[0].submit();

  }

  <%
      String deleteMsg = "";

      if(project.isCheckOut() == true) {
        deleteMsg = messageService.getString("e3ps.message.ket_message", "00789")/*경고!!\n일정변경을 취소하시겠습니까?*/;
      }
      else {
        deleteMsg = messageService.getString("e3ps.message.ket_message", "00791")/*경고!!\n프로젝트를 삭제하시겠습니까?*/;
      }
  %>
  function deleteProject(){
    if ( confirm('<%=deleteMsg%>') ) {
      if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "03288") %><%--경고!!\n삭제된 정보는 복구할 수 없습니다.--%>') ) {
        document.forms[0].action = "/plm/servlet/e3ps/ProjectServlet";
        document.forms[0].method = "post";
        document.forms[0].command.value = "delete";
        //disabledAllBtn();
        //showProcessing();
        document.forms[0].submit();
      }
    }
  }
  <% //} %>

  function viewECO(projectno) {
    var screenWidth = screen.availWidth/2-900;
    var screenHeight = screen.availHeight/2-400;
    var url = "/plm/servlet/e3ps.change.servlet.ManageECOServlet?projectno="+projectno+"&mode=popup";
    newWin = window.open(url,"window", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=350,height=150,resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.resizeTo(900,400);
    newWin.focus();
  }

  function saveTask(oid){
    var url = "/plm/jsp/project/SaveProjectTask.jsp?oid="+oid;
    openOtherName(url,"saveTask","800","500","status=1,scrollbars=no,resizable=1");
  }

  //@@@@@@@@@@@@@@@@@ Role Member 수정 시작 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

  //@@@@@@@@@@@@@@@@@ Role Member 수정 끝 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
  var targetTableId;
  var targetCmdStr;
  var targetLinkMsg;
  function onDeleteTableRow(tableid, cmdstr, objid) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = cmdstr;

    onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&linkOid=" + encodeURIComponent(objid);
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);

    if(targetCmdStr == 'memberDelete') {
      //addMemberRefresh('roleMemberTable', 'searchRoleMember','roleMemberDelete');
    }
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
    }else if(targetTableId == "membertable") {
      //acceptMember(xmlDoc);
      document.forms[0].radioValue.value = "2";
      document.forms[0].submit();
    }else{

      document.forms[0].radioValue.value = "2";
      document.forms[0].submit();
      //acceptMember2(xmlDoc);
    }
  }

  function modifyPM(){

    //var url = "/plm/jsp/project/SelectProjectPeopleList.jsp?oid=<%=oid%>&mode=s&function=<%="modifyPM"%>";
    //openSameName(url,"600","400","status=no,scrollbars=yes,resizable=no");
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }

    document.forms[0].action = "/plm/jsp/project/projectModifyPM.jsp?userOid=" + attacheMembers[0][0];  ;
    document.forms[0].command.value = "targetCmdStr";
    document.forms[0].method = "post";
    document.forms[0].submit();
  }

   //사용자 가져오기 시작 ........................................................................................
  //............................................................................................................
  function addMember(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }

    onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    for(var i = 0; i < attacheMembers.length; i++) {
      param += "&userOid=" + encodeURIComponent(attacheMembers[i][0]);
    }

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, true);
  }

  function acceptMember(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//data_info");

    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_duty = showElements[0].getElementsByTagName("l_duty");
    var l_departmentName = showElements[0].getElementsByTagName("l_departmentName");
    var l_email = showElements[0].getElementsByTagName("l_email");
    var l_peopleOid = showElements[0].getElementsByTagName("l_peopleOid");
    var l_linkOid = showElements[0].getElementsByTagName("l_linkOid");
    var l_roleName;
    if(targetTableId == "roleMemberTable") {
      l_roleName = showElements[0].getElementsByTagName("l_roleName");
    }

    var targetTable = document.getElementById(targetTableId);

    var len = targetTable.rows.length;
    for(var i=len; i > 1; i--) {
      targetTable.deleteRow(i-1);
    }

    var loid = false;
    var lname = false;
    var lduty = false;
    var ldepartmentName = false;
    var lemail = false;
    var lpeopleOid = false;
    var llinkOid = false;
    var lroleName;
    var deleteHtml;
    var nameHtml;
    for(var i = 0; i < l_oid.length; i++) {
      loid = decodeURIComponent(l_oid[i].text);
      lname = decodeURIComponent(l_name[i].text);
      lduty = decodeURIComponent(l_duty[i].text);
      ldepartmentName = decodeURIComponent(l_departmentName[i].text);
      lemail = decodeURIComponent(l_email[i].text);
      lpeopleOid = decodeURIComponent(l_peopleOid[i].text);
      llinkOid = decodeURIComponent(l_linkOid[i].text);
      if(l_roleName) {
        lroleName = decodeURIComponent(l_roleName[i].text);
      }

      if(lname.length == 0) {
        nameHtml = "&nbsp;";
      }else{
        nameHtml = "<a href=\"JavaScript:viewPeople('"+lpeopleOid+"')\">" + lname + "</a>";
      }

      if(lduty.length == 0) {
        lduty = " ";
      }

      if(ldepartmentName.length == 0) {
        ldepartmentName = " ";
      }

      if(lemail.length == 0) {
        lemail = " ";
      }

      if(llinkOid.length == 0) {
        deleteHtml = "&nbsp;";
      }else{
        deleteHtml = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"','"+targetLinkMsg+"','" + llinkOid + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a>";
      }



      tableRows = targetTable.rows.length;
      newTr = targetTable.insertRow(tableRows);

      if(lroleName) {
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerText = lroleName;
      }

      //이름
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerHTML = nameHtml;

      //직위
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = lduty;

      //부서
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = ldepartmentName;

      //E-Mail
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = lemail;

      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = deleteHtml;
    }
  }

  function acceptMember2(xmlDoc) {
    var showElements = xmlDoc.selectNodes("//data_info");

    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_duty = showElements[0].getElementsByTagName("l_duty");
    var l_departmentName = showElements[0].getElementsByTagName("l_departmentName");
    var l_email = showElements[0].getElementsByTagName("l_email");
    var l_peopleOid = showElements[0].getElementsByTagName("l_peopleOid");
    var l_linkOid = showElements[0].getElementsByTagName("l_linkOid");
    var l_roleName;
    if(targetTableId == "roleMemberTable") {
      l_roleName = showElements[0].getElementsByTagName("l_roleName");
    }

    var targetTable = document.getElementById(targetTableId);

    var len = targetTable.rows.length;

    for(var i=len; i > 1; i--) {
      targetTable.deleteRow(i-1);
    }

    var loid = false;
    var lname = false;
    var lpeopleOid = false;
    var llinkOid = false;
    var lroleName = "&nbsp;";
    var deleteHtml = "&nbsp;";
    var nameHtml = "&nbsp;";

    var loid2 = false;
    var lname2 = false;
    var lpeopleOid2 = false;
    var llinkOid2 = false;
    var lroleName2 = "&nbsp;";
    var deleteHtml2 = "&nbsp;";
    var nameHtml = "&nbsp;";
    var index = 0 ;
    var cols = 1;
    for(var i = 0 ; i < l_oid.length ; i++) {

      if(i%2==0){
        loid = decodeURIComponent(l_oid[i].text);
        lname = decodeURIComponent(l_name[i].text);
        lpeopleOid = decodeURIComponent(l_peopleOid[i].text);
        llinkOid = decodeURIComponent(l_linkOid[i].text);

        if(l_roleName) {
          lroleName = decodeURIComponent(l_roleName[i].text);
        }

        if(lname.length == 0) {
          nameHtml = "&nbsp;";
        }else{
          nameHtml = "<a href=\"JavaScript:viewPeople('"+lpeopleOid+"')\">" + lname + "</a>&nbsp;<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"','"+targetLinkMsg+"','" + llinkOid + "');\"><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></a>";
        }
        if(llinkOid.length == 0) {
          deleteHtml = "&nbsp;";
        }else{
          deleteHtml = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"','"+targetLinkMsg+"','" + llinkOid + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a>";
        }

      }else{
        loid2 = decodeURIComponent(l_oid[i].text);
        lname2 = decodeURIComponent(l_name[i].text);
        lpeopleOid2 = decodeURIComponent(l_peopleOid[i].text);
        llinkOid2 = decodeURIComponent(l_linkOid[i].text);

        if(l_roleName) {
          lroleName2 = decodeURIComponent(l_roleName[i].text);
        }

        if(lname2.length == 0) {
          nameHtml2 = "&nbsp;";
        }else{
          nameHtml2 = "<a href=\"JavaScript:viewPeople('"+lpeopleOid+"')\">" + lname2 + "</a>&nbsp;<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"','"+targetLinkMsg+"','" + llinkOid2 + "');\"><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></a>";
        }
        if(llinkOid2.length == 0) {
          deleteHtml2 = "&nbsp;";
        }else{
          deleteHtml2 = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"','"+targetLinkMsg+"','" + llinkOid2 + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a>";
        }

      }

      if(i%2==1){
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);

        if(lroleName) {
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdblueL";
          newTd.innerText = lroleName;
        }
        //이름
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = nameHtml;

        if(lroleName2) {
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdblueL";
          newTd.innerText = lroleName2;
        }
        //이름
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.innerHTML = nameHtml2;

      }else if( (l_oid.length == i+1) && (i%2==0) ){
        tableRows = targetTable.rows.length;
        newTr = targetTable.insertRow(tableRows);

        if(lroleName) {
          newTd = newTr.insertCell(newTr.cells.length);
          newTd.className = "tdblueL";
          newTd.innerText = lroleName;
        }
        //이름
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteL";
        newTd.colSpan = 3;
        newTd.innerHTML = nameHtml;
      }


    }
  }
  //사용자 가져오기 끝 ........................................................................................

  //Veiw 권한 부서 가져오기 시작 ........................................................................................
  var deptRole;
  var rowIndex;
  var colIndex;
  function addDepartment(role, row, col) {
    deptRole = role;
    rowIndex = row;
    colIndex = col;
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?isModal=true&isOpen=true&invokeMethod=openerCall&mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
      return;
    }

  }

  function addDepartmentdelete(role) {
    onProgressBar();

    var param = "command=settingDeptRole";

    param += "&oid=<%=oid%>";
    param += "&deptOid=";
    param += "&role=" + deptRole;

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";


    postCallServer(url, param, setTaskRefDef, true);

  }


  function openerCall(oArr) {
    onProgressBar();

    var param = "command=settingDeptRole";

    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(oArr[0][0]);
    param += "&deptRole=" + deptRole;

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";


    postCallServer(url, param, setTaskRefDef, true);
  }

  function setTaskRefDef(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      return;
    }
    var lisRegistry = false;
    var showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var targetTable = document.getElementById("refDeptTable");
    var len = targetTable.rows.length;
    if(l_oid.length > 0){
      loid = (l_oid[0].text);
      lname = (l_name[0].text);
      rIndex = 1 + parseInt(rowIndex);
      tr = targetTable.rows[rIndex];
      cIndex = parseInt(colIndex);
      tr.cells[cIndex].innerHTML = "<div style=\"width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + lname + "</nobr></div>";
    }
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
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"', '"+targetLinkMsg+"', '" + llinkOid + "');\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a>";
    }
  }
  //Veiw 권한 부서 가져오기 끝 ........................................................................................
  //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
  //Role Member 추가 시작
  function addRoleMember(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    var url = "/plm/jsp/project/PjtRoleMemberModifyPop.jsp?oid=<%=oid%>";
    rtnMsg = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=740px; dialogHeight:500px; center:yes");
    if(typeof rtnMsg == "undefined" || rtnMsg == null) {
      rtnMsg = true;
    }

    onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);

    addMemberRefresh('membertable', 'searchMember','memberDelete');
  }
  //Role Member 추가 끝
  //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
  function addMemberRefresh(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);
  }


  //상태 변경 ...................................................................... begin
  var ajaxType;
  function onStateChange(s_obj, lcm_oid) {
    if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "03314") %><%--상태를 변경하시겠습니까?--%>")) {
      return;
    }

    if(s_obj.value == '') {
      return;
    }

    var param = "?command=stateChange&oid=" + lcm_oid + "&state=" + s_obj.value;
    ajaxType = "stateChange";
    onProgressBar();
    var url = "/plm/jsp/project/WorkProcessAjaxAction.jsp" + param;
    callServer(url, onStateMessage);
  }



  function onStateMessage(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;

    var alertMsg = "";
    if(msg == 'true') {
      if(ajaxType == "stateChange") {
        alertMsg = "<%=messageService.getString("e3ps.message.ket_message", "01764") %><%--상태변경을 완료했습니다\n화면을 다시 로드하겠습니다--%>";
      }
    } else {
      if(ajaxType == "stateChange") {
        alertMsg = "<%=messageService.getString("e3ps.message.ket_message", "01763") %><%--상태변경을 완료하지 못했습니다\n다시 시도하시기 바랍니다\n화면을 다시 로드하겠습니다--%>";
      }
    }


    alert(alertMsg);

    document.forms[0].submit();
  }
  //상태 변경 ...................................................................... end

  function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","760","700","status=no,scrollbars=yes,resizable=yes");
  }
  function gotoPMView(oid){
    document.forms[0].action="/plm/jsp/project/ViewProgram.jsp";
    document.forms[0].oid.value = oid;
    document.forms[0].submit();
  }

  function allProjectView(oid){
    var screenWidth = 0 ;
    var screenHeight = 0 ;
    var url =  "/plm/jsp/project/ProjectViewFrm.jsp?oid="+oid+"&popup=popup";
    newWin = window.open(url,"window","scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=1200,height=800,resizable=no,top="+screenHeight+",left="+screenWidth);
    newWin.focus();

  }

  function viewReview(oid, option){
    if(option == 'false'){
      var url = "/plm/jsp/project/CreateReviewDevelop.jsp?oid="+oid;
      openSameName(url,"800","550","status=no,scrollbars=no,resizable=no");
    }else{
      var url = "/plm/jsp/project/CreateReviewDevelop.jsp?oid="+oid+"&rdOid=<%=CommonUtil.getOIDString(rd)%>";
      openSameName(url,"800","550","status=no,scrollbars=no,resizable=no");
    }
  }

  <%if(pf == null){%>
  function viewPerformance(oid){
      var url = "/plm/jsp/project/performance/CreatePerformance.jsp?oid="+oid+"&pOid=<%=(pf!= null)?CommonUtil.getOIDString(pf):""%>";
      openSameName(url,"820","690","status=no,scrollbars=no,resizable=no");
  }
  <%}else{%>
  function viewPerformance(oid){
      var url = "/plm/jsp/project/performance/ViewPerformance.jsp?oid="+oid+"&pOid=<%=(pf!= null)?CommonUtil.getOIDString(pf):""%>";
      openSameName(url,"850","730","status=no,scrollbars=no,resizable=no");
  }
  <%}%>

  function viewHistory(pboOid){
    var url = '/plm/jsp/wfm/ApprovalHistory.jsp?pboOid='+pboOid;
    window.open(url,'결재이력',"status=1, menu=no, width=730, height=500");
  }

  function requestPop(oid){
    var url = '/plm/jsp/dms/ViewDevRequestPop.jsp?oid='+oid;
    window.open(url,'의뢰서',"status=1, menu=no, width=830, height=800");
  }

  function openCostHistory(oid){
    var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
    openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
  }
  
    // [START] [PLM System 1차개선] [일정변경] 버튼 클릭 시 confirm message 표시 후 일정변경(PLANCHANGE) 상태 변경, 2013-06-24, BoLee
    function historyChange(oid) {

        if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03281") %><%--Project 일정을 변경하시겠습니까?\n확인 시 Project 상태가 [일정변경] 상태로 변경됩니다.--%>") ) {

            showProcessing();   // 진행 상태바 표시 (processing.html)
            disabledAllBtn();   // 버튼 비활성화

            document.forms[0].action = "/plm/servlet/e3ps/ProjectScheduleServlet";
            document.forms[0].cmd.value = "change";
            document.forms[0].target = "_self";
            document.forms[0].submit();
        }
    }
    // [END] [PLM System 1차개선] [일정변경] 버튼 클릭 시 confirm message 표시 후 일정변경(PLANCHANGE) 상태 변경, 2013-06-24, BoLee

  function excelDown(){
    location.href = "/plm/jsp/project/CFTRoleExcelDown.jsp?oid=<%=oid%>";
  }

  function excelUp() {
    var url = "/plm/jsp/project/CFTRoleUp.jsp?oid=<%=oid%>";
    openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");
  }

  function viewTodo(oid){
    var url = "/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser="+oid;
    openOtherName(url,"ViewTemplate","850","680","status=1,scrollbars=yes,resizable=1");
  }


  function linkUrlOpen(linkUrl){
    if(linkUrl.length > 0) {
        window.open(linkUrl,'',"status=1, menu=no, width=1030, height=930");
    }
  }
-->
</script>
<script src="/plm/portal/js/script.js"></script>
<script src="/plm/portal/js/menu.js"></script>
<script src="/plm/portal/js/common.js"></script>
<!-- [START] [PLM System 1차개선] 진행 상태 바 표시, 2013-07-15, BoLee -->
<%@include file="/jsp/common/processing.html" %>
<!-- [END] [PLM System 1차개선] 진행 상태 바 표시, 2013-07-15, BoLee -->
<!-- [START] [PLM System 1차개선] 일정변경 완료 처리 function 호출을 위한 Include, 2013-08-05, BoLee -->
<%@include file="/jsp/project/schedule/ProjectScheduleJs.jsp" %>
<!-- [END] [PLM System 1차개선] 일정변경 완료 처리 function 호출을 위한 Include, 2013-08-05, BoLee -->
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<%@include file="/jsp/common/multicombo.jsp"%>
</head>
<body>
    <%@include file="/jsp/project/template/ajaxProgress.html"%>
    <form method="post">
        <!-- Hidden Value -->
        <input type="hidden" name="radioValue" value="<%=radioValue%>"> 
        <input type=hidden name=command> 
        <input type=hidden name=cmd>
        <input type=hidden name=state>
        <input type=hidden name=oid value='<%=oid%>'> 
        <input type=hidden name=pmoid value='<%=request.getParameter("pmoid")%>'>
        <input type=hidden name=popup value='<%=popup%>'>
        <!-- //HIdden Value -->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">

                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00724")%><%--검토 프로젝트 상세정보--%>
                                        </td>
                                        <td align="right"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="5"></td>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                                        <a href="ReviewProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>" onclick="showProcessing();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab">
                                                        <%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table>
                                        </td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                                        <a href="ReviewProjectView3.jsp?oid=<%=oid%>&popup=<%=popup%>" onclick="showProcessing();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab">
                                                        <a href="ReviewProjectView4.jsp?oid=<%=oid%>&popup=<%=popup%>" onclick="showProcessing();" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00278")%><%--Issue--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
					                            <tr>
					                                <td><img src="/plm/portal/images/tab_4.png"></td>
					                                <td background="/plm/portal/images/tab_6.png" class="btn_tab">
					                                <a href="javascript:openCostHistory('<%=oid %>')" class="btn_tab">개발원가</a></td>
					                                <td><img src="/plm/portal/images/tab_5.png""></td>
					                            </tr>
					                        </table>
	                                    </td>
                                    </tr>
                                </table>
                            </td>
                            <td align="right">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="#" onClick="javascript:top.close();"
                                            class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><!-- 닫기 --></a></td>
                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table> <!-- ////////////////////////////////////////////////////////////////////////////////////// 제품 -->
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                            <td height="10" background="/plm/portal/images/box_14.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_10.gif""></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <table width="100%" height=20 " border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>
                                            <%
                                                 if (!isCS) {
                                                 QuerySpec qs = new QuerySpec();
                                            
                                                    int idxpi = qs.appendClassList(ProductInfo.class, true);
                                            
                                                    SearchCondition cs = new SearchCondition(ProductInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(oid));
                                                    qs.appendWhere(cs, new int[] { idxpi });
                                                    SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "thePersistInfo.theObjectIdentifier.id", "idA2A2", false);
                                                    //SearchUtil.setOrderBy(qs, ProductInfo.class, idxpi, "pName", "pName", false);
                                            
                                                    QueryResult qrpi = PersistenceHelper.manager.find(qs);
                                             %>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></td>
                                                    <td align="right">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <!-- PLM 정보수정 -->
                                                                <%
                                                                    if (!auth.isCheckOut && auth.isLatest && (auth.isPM || auth.isPMO || isAdmin)) {
                                                                %>
                                                                <td>
                                                                    <table border="0" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                                href="javascript:updatePLMProject();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936")%><%--수정(프로젝트 수정)--%></a></td>
                                                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                                <%
                                                                    }
                                                                %>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td width="30" class="tdblueM">No</td>
                                                    <!-- <td width="90" rowspan="2" class="tdblueM">Part No</td> -->
                                                    <td class="tdblueM">Part Name</td>
                                                    <td width="90" class="tdblueM">
                                                        <%
                                                            if (projectTypeValue.equals("자동차")) {
                                                        %><%=messageService.getString("e3ps.message.ket_message", "02745")%><%--차종--%>
                                                        <%
                                                            } else {
                                                        %><%=messageService.getString("e3ps.message.ket_message", "02466")%><%--적용기기--%>
                                                        <%
                                                            }
                                                        %>
                                                    </td>
                                                    <!-- <td width="70" rowspan="2" class="tdblueM">적용부위</td> -->
                                                    <td width="100" class="tdblueM">조립처</td>
                                                    <td width="100" class="tdblueM">조립구분</td>
                                                    <td width="60" class="tdblueM">U/S</td>
                                                </tr>
                                                <%
                                                    int no = 1;
                                                    PartnerDao partnerDao = new PartnerDao();
                                                    while (qrpi.hasMoreElements()) {
                                                        Object o[] = (Object[]) qrpi.nextElement();
                                                        ProductInfo pi = (ProductInfo) o[0];

                                                        int totalYield = 0;
                                                        int y1 = Integer.parseInt(pi.getYear1() == null ? "0" : pi.getYear1());
                                                        int y2 = Integer.parseInt(pi.getYear2() == null ? "0" : pi.getYear2());
                                                        int y3 = Integer.parseInt(pi.getYear3() == null ? "0" : pi.getYear3());
                                                        int y4 = Integer.parseInt(pi.getYear4() == null ? "0" : pi.getYear4());
                                                        int y5 = Integer.parseInt(pi.getYear5() == null ? "0" : pi.getYear5());
                                                        int y6 = Integer.parseInt(pi.getYear6() == null ? "0" : pi.getYear6());
                                                        int y7 = Integer.parseInt(pi.getYear7() == null ? "0" : pi.getYear7());
                                                        int y8 = Integer.parseInt(pi.getYear8() == null ? "0" : pi.getYear8());
                                                        int y9 = Integer.parseInt(pi.getYear9() == null ? "0" : pi.getYear9());
                                                        int y10 = Integer.parseInt(pi.getYear10() == null ? "0" : pi.getYear10());
                                                        totalYield += y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10;

                                                        String carName = ProductHelper.getCarName(pi.getPersistInfo().getObjectIdentifier().toString());
                                                        String carName2 = "";
                                                        String assemblyPlaceName = "";
                                                        String assemblyPlace = "";
                                                        
                                                        try {
                                                    	   if(pi.getAssemblyPlaceType() != null){
                                                                if(pi.getAssemblyPlaceType() != null && pi.getAssemblyPlaceType().equals("사내")){
                                                                    assemblyPlaceName = pi.getAssemblyPlace().getName();
                                                                    assemblyPlace = CommonUtil.getOIDString(pi.getAssemblyPlace());
                                                                }else{
                                                                    assemblyPlaceName = partnerDao.ViewPartnerName(pi.getAssemblyPartnerNo());
                                                                    assemblyPlace = pi.getAssemblyPartnerNo();
                                                                }
                                                            }
                                                    	    carName2 = ProductHelper.getCarName2(pi.getPersistInfo().getObjectIdentifier().toString());
                                                        } catch (Exception e) {
                                                            carName2 = "";
                                                            Kogger.error(e);
                                                        }

                                                        Hashtable hash = ProductHelper.getCostIFLinkUrl(pi);
                                                        String linkUrl = (String) hash.get("linkUrl");
                                                        if (linkUrl == null)
                                                        linkUrl = "";
                                                %>
                                                <tr>
                                                    <td class="tdwhiteM"><%=no++%></td>
                                                    <%-- <td class="tdwhiteM"><%=pi.getPNum() == null ? "" : pi.getPNum()%>&nbsp;</td> --%>
                                                    <td class="tdwhiteM" style="text-align: left"><%=pi.getPName() == null ? "" : pi.getPName()%>&nbsp;</td>
                                                    <td class="tdwhiteM"><%=carName == "" ? carName2 : carName%>&nbsp;</td>
                                                    <td class="tdwhiteM"><%=pi.getAssemblyPlaceType()!=null?pi.getAssemblyPlaceType()+"/"+assemblyPlaceName:"&nbsp;"%></td>
                                                    <td class="tdwhiteM"><%=pi.getAssembledType()!=null?pi.getAssembledType().getName():""%></td>
                                                    <td class="tdwhiteM"><%=pi.getUsage() == null ? "" : pi.getUsage()%>&nbsp;</td>
                                                </tr>
                                                <%
                                                    }
                                                    if(qrpi.size() == 0 ){
                                                  %>
                                                <tr>
                                                    <td class='tdwhiteM0' align='center' colspan='11'><%=messageService.getString("e3ps.message.ket_message", "01326") %><%--등록된 결과가 없습니다.--%>
                                                    </td>
                                                </tr>
                                                <%} %>
                                            </table> <%} %></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="10" height="10"><img src="/plm/portal/images/box_11.gif"></td>
                            <td height="10" background="/plm/portal/images/box_16.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_12.gif"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

    </form>
</body>
</html>
