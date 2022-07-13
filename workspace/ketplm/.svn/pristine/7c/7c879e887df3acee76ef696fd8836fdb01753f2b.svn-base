<%try{ %>

<%@page contentType="text/html; charset=UTF-8"%>
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

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
  String oid =  request.getParameter("oid");
  String oldOid =  request.getParameter("compareTaskOid");

  if(oid == null){
    oid = "";
  }
  if(oldOid == null){
    oldOid = "";
  }

  String newVersion = request.getParameter("newVersion");
  String oldVersion = request.getParameter("oldVersion");
  String compareResult = request.getParameter("compareResult");

  String compareResultStr = "";
  if(compareResult.equals("2")){
    compareResultStr ="[ " + messageService.getString("e3ps.message.ket_message", "02863")/*추가 됨*/ + " ]";
  }
  if(compareResult.equals("-1")){
    compareResultStr ="[ " + messageService.getString("e3ps.message.ket_message", "01693")/*삭제 됨*/ + " ]";
  }
  if(compareResult.equals("1")){
    compareResultStr ="[ " + messageService.getString("e3ps.message.ket_message", "01941")/*수정 됨*/ + " ]";
  }
  if(compareResult.equals("0")){
    compareResultStr ="[ " + messageService.getString("e3ps.message.ket_message", "01513")/*변경없음*/ + "  ]";
  }

  WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
  boolean isAdmin = CommonUtil.isAdmin();

  E3PSTask task = null;
  TargetItem targetItem = null;
  E3PSProject project = null;
  E3PSProjectData projectData = null;
  E3PSTaskData taskData = null;
  E3PSTaskAccessData accessData = null;
  String taskExecStartDateStr = "";
  String taskExecEndDate_exStr = "";
  TaskViewButtonAuth buttonAuth = null;

  E3PSTask task2 = null;
  TargetItem targetItem2 = null;
  E3PSProject project2 = null;
  E3PSProjectData projectData2 = null;
  E3PSTaskData taskData2 = null;
  E3PSTaskAccessData accessData2 = null;
  String taskExecStartDateStr2 = "";
  String taskExecEndDate_exStr2 = "";
  TaskViewButtonAuth buttonAuth2 = null;

  boolean isPM = false;
  boolean isPL = false;
  boolean isProjectContinue = false;
  boolean canManage = false;
  boolean isComplete = false;
  boolean isContinue = false;
  boolean isStop = false;
  boolean isLast = false;

  boolean isPM2 = false;
  boolean isPL2 = false;
  boolean isProjectContinue2 = false;
  boolean canManage2 = false;
  boolean isComplete2 = false;
  boolean isContinue2 = false;
  boolean isStop2 = false;
  boolean isLast2 = false;



  //if(!oid.equals("")){
  if(StringUtil.checkString(oid)) {
     task = (E3PSTask)CommonUtil.getObject(oid);
     targetItem = ProjectTaskHelper.getTargetItem(task);
     project = (E3PSProject)task.getProject();
     projectData = new E3PSProjectData(project);
     taskData = new E3PSTaskData(task);
     accessData = taskData.getAccessState();
                                                    //PLM Admin
     isPM = ProjectUserHelper.manager.isPM(project);                        //isPM
     isPL = ProjectUserHelper.manager.isPL(project);                        //isPL
     isProjectContinue = (project.getPjtState()==ProjectStateFlag.PROJECT_STATE_PROGRESS);      //Project 상태
     canManage = isPM;                                        //Manager
     isComplete = accessData.isComplete();                              //true : 종료 , false : 태스크 종료 안됨
     isContinue = accessData.isContinue();                              //true : 진행 , false : 태스크 진행 안됨
     isStop = accessData.isStop();
     isLast = accessData.isLast();

     if(taskData.taskExecStartDate != null){
       taskExecStartDateStr = DateUtil.getDateString(taskData.taskExecStartDate, "D");
     }
     if(taskData.taskExecEndDate_ex != null){
      taskExecEndDate_exStr = DateUtil.getDateString(taskData.taskExecEndDate_ex, "D");
     }

     buttonAuth = new TaskViewButtonAuth(task);
  }

  //if(!oldOid.equals("")){
  if(StringUtil.checkString(oldOid)) {
     task2 = (E3PSTask)CommonUtil.getObject(oldOid);
     targetItem2 = ProjectTaskHelper.getTargetItem(task2);
     project2 = (E3PSProject)task2.getProject();
     projectData2 = new E3PSProjectData(project2);
     taskData2 = new E3PSTaskData(task2);
     accessData2 = taskData2.getAccessState();

     isPM2 = ProjectUserHelper.manager.isPM(project2);                        //isPM
     isPL2 = ProjectUserHelper.manager.isPL(project2);                        //isPL
     isProjectContinue2 = (project2.getPjtState()==ProjectStateFlag.PROJECT_STATE_PROGRESS);    //Project 상태
     isComplete2 = accessData2.isComplete();                            //true : 종료 , false : 태스크 종료 안됨
     isContinue2 = accessData2.isContinue();                            //true : 진행 , false : 태스크 진행 안됨
     isStop2 = accessData2.isStop();                                //
     isLast2 = accessData2.isLast();

     if(taskData2.taskExecStartDate != null){
       taskExecStartDateStr2 = DateUtil.getDateString(taskData2.taskExecStartDate, "D");
     }
     if(taskData2.taskExecEndDate_ex != null){
      taskExecEndDate_exStr2 = DateUtil.getDateString(taskData2.taskExecEndDate_ex, "D");
     }

     buttonAuth2 = new TaskViewButtonAuth(task2);
  }

%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="e3ps.equipment.UnitErrorProcess"%>
<%@page import="e3ps.equipment.ErrorProcessTaskLink"%>
<html>
<head>
<title></title>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<SCRIPT>
  function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","450","status=no,scrollbars=no,resizable=no");
  }

  function viewIssue(oid) {
    var url = "/plm/jsp/equipment/equipmentView.jsp?oid=" + oid;
    openSameName(url,"870","650","status=no,scrollbars=yes,resizable=no");
  }

  function outputPage(opid, isview, oid) {
    var modalHeight = '490px';
    var url = "/plm/jsp/project/ProjectOutputCreatePage.jsp?fromPage=TaskView&taskOid="+oid+"&oid=" + opid;
    if(isview == 'true') {
      url = "/plm/jsp/project/ProjectOutputViewPage.jsp?fromPage=TaskView&taskOid="+oid+"&oid=" + opid;

      modalHeight = '640px';
    }
    attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=640px; dialogHeight:" +modalHeight+ "; center:yes");
    if(typeof attache == "undefined" || attache == null) {
      attache = false;
    }

    if(isview == 'true') {
      return;
    }

    onProgressBar();

    var param = "command=searchOutput";
    param += "&oid="+oid;
    var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
    postCallServer(url, param, setOutputTable, true);
  }

  function setOutputTable(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    var errorMessage = showElements[0].getElementsByTagName("l_errorMessage")[0].text;

    if(msg == 'false') {
      alert(errorMessage);
      if(errorMessage != ""){
        alert(errorMessage);
      }else{
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      }
      return;
    }

    onProgressBar();

    var param = "command=searchTaskCompletions";
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectTaskAjaxAction.jsp";
    postCallServer(url, param, setTaskProgress, true);
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
    //document.location.reload();
  }

  function viewTask(v){
    var url = "/plm/jsp/project/template/TemplateTaskView.jsp?oid="+v;
    openSameName(url,"870","550","status=no,scrollbars=yes,resizable=no");
  }

</SCRIPT>

</head>
<body bgcolor="white" leftmargin="0" marginwidth="0" topmargin="5" marginheight="0">
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<form>

<!-- //Hidden Value -->

<table border="0" cellpadding="1" cellspacing="1" width="100%">
  <tr>
    <td valign="top" style="padding-left:12">
    <!-------------------------------------- 상단버튼 시작 //-------------------------------------->
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <a href="javascript:parent.self.close();">
            <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
            </a>
            <!--table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class=fixLeft></td>
                <td ><input type=button class="btnTras" value="닫기" onClick="parent.self.close()"></td>
                <td class=fixRight></td>
              </tr>
            </table-->
          </td>
        </tr>
      </table>

      <!-------------------------------------- 상단버튼 끝 //-------------------------------------->
      <!------------------------------ 본문 시작 //------------------------------>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>

      <!-- TASK 기본정보 비교 -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "00487") %><%--TASK 정보 비교--%></td>
          <td align="right">
            &nbsp;
          </td>
        </tr>
      </table>
      <% if(task != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- TASK 기본정보 (NEW Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="15%"><COL width="20%"><COL width="10%"><COL width="20%"><COL width="10%"><COL width="20%">
        <tr>
          <td class="tdblueM0" colspan=6>
            <img src='/plm/portal/icon/task.gif'>&nbsp;<B><SPAN style="FONT-SIZE: 11pt">[<%=taskData.taskName%>] <%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%> (<%=projectData.pjtHistory%>) <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%></SPAN></B>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00481") %><%--TASK 기간--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=taskData.taskDuration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02061") %><%--실제 시작일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData.taskExecStartDate, "D")%>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00493") %><%--TASK 표준공수--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=taskData.taskStdWork%>&nbsp;Hr
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02063") %><%--실제 종료일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData.taskExecEndDate, "D")%>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdwhiteL0" colspan=5>
            <textarea name="detail" style="width:100%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" readOnly><%=(taskData.taskDesc.trim()=="")?"":taskData.taskDesc.trim()%></textarea>
          </td>
        </tr>
      </table>
      <!-- TASK 기본정보 (NEW Version) -->
      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <!-- space -->
      <!-- TargetItem (NEW Version) -->
      <%
      if(targetItem!= null){
        String targetValue = targetItem.getValue();
        if(targetValue == null){
          targetValue = "";
        }
        String userOid = "";
        String userName = "";
        //Kogger.debug(targetItem.getUpdator());
        if(targetItem.getUpdator() != null){
           userName = targetItem.getUpdator().getFullName();
           userOid = CommonUtil.getOIDString(targetItem.getUpdator().getObject());
        }

        if(userName == null){
          userName = "";
        }
      %>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL">Target Item</td>
          <td class="tdwhiteL0" title = "<%=targetItem.getTarget()%>">
            <div style="width:600;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr>
              &nbsp;<%=targetItem.getTarget() %>
              </nobr>
            </div>
          </td>
        </tr>
        <tr>
          <td class="tdblueL">Current Status</td>
          <td class="tdwhiteL0">
            <input name="targetValue" class="txt_field" size="3" value="<%=targetValue%>"  style="width:90%;IME-MODE: disabled"/>
            &nbsp;
            <a href="javascript:setTargetValue();">
            <img src="/plm/portal/images/img_default/button/board_btn_input.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>' width="62" height="20" border="0">
            </a>
            <!--input type=button class="btnTras" value='저장' id=innerbutton onclick="JavaScript:setTargetValue()"-->
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdwhiteL0" >
            <%=targetItem.getDescription()==null? "&nbsp;":targetItem.getDescription()%>
          </td>
        </tr>
      </table>
      <% } %>
      <!-- TargetItem (NEW Version) -->
      <% } %>

      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <!-- space -->

      <% if(task2 != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- TASK 기본정보 (OLD Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="15%"><COL width="20%"><COL width="10%"><COL width="20%"><COL width="10%"><COL width="20%">
        <tr>
          <td class="tdblueM0" colspan=6>
            <img src='/plm/portal/icon/task.gif'>&nbsp;<B><SPAN style="FONT-SIZE: 11pt">[<%=taskData2.taskName%>] <%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%> (<%=projectData2.pjtHistory%>) <%=messageService.getString("e3ps.message.ket_message", "02512") %><%--정보--%></SPAN></B>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00481") %><%--TASK 기간--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=taskData2.taskDuration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData2.taskPlanStartDate, "D")%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02061") %><%--실제 시작일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData2.taskExecStartDate, "D")%>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00493") %><%--TASK 표준공수--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=taskData2.taskStdWork%>&nbsp;Hr
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData2.taskPlanEndDate, "D")%>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02063") %><%--실제 종료일자--%></td>
          <td class="tdwhiteL">
            &nbsp;<%=DateUtil.getDateString(taskData2.taskExecEndDate, "D")%>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdwhiteL0" colspan=5>
            <textarea name="detail" style="width:100%" rows="5" class="fm_area" onKeyUp="common_CheckStrLength(this, 1500)" onChange="common_CheckStrLength(this, 1500)" readOnly><%=(taskData2.taskDesc.trim()=="")?"":taskData2.taskDesc.trim()%></textarea>
          </td>
        </tr>
      </table>
      <!-- TASK 기본정보 (OLD Version) -->
      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <!-- space -->
      <!-- TargetItem (OLD Version) -->
      <%
      if(targetItem2 != null){
        String targetValue2 = targetItem2.getValue();
        if(targetValue2 == null){
          targetValue2 = "";
        }
        String userOid2 = "";
        String userName2 = "";
        //Kogger.debug(targetItem2.getUpdator());
        if(targetItem2.getUpdator() != null){
           userName2 = targetItem2.getUpdator().getFullName();
           userOid2 = CommonUtil.getOIDString(targetItem2.getUpdator().getObject());
        }

        if(userName2 == null){
          userName2 = "";
        }
      %>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL">Target Item</td>
          <td class="tdwhiteL0" title = "<%=targetItem2.getTarget()%>">
            <div style="width:600;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr>
                &nbsp;<%=targetItem2.getTarget() %>
              </nobr>
            </div>
          </td>
        </tr>
        <tr>
          <td class="tdblueL">Current Status</td>
          <td class="tdwhiteL0">
            <input name="targetValue" class="txt_field" size="3" value="<%=targetValue2%>"  style="width:90%;IME-MODE: disabled"/>
            &nbsp;
            <a href="javascript:setTargetValue();">
            <img src="/plm/portal/images/img_default/button/board_btn_input.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>' width="62" height="20" border="0">
            </a>
            <!--input type=button class="btnTras" value='저장' id=innerbutton onclick="JavaScript:setTargetValue()"-->
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdwhiteL0" >
            <%=targetItem2.getDescription()==null? "&nbsp;":targetItem2.getDescription()%>
          </td>
        </tr>
      </table>
      <% } %>
      <!-- TargetItem (OLD Version) -->
      <% } %>
      <!-- TASK 기본정보 비교 -->

      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->


      <!-- 진행현황 비교 -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "02736") %><%--진행현황--%></td>
          <td  align="right"> (<%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%><font color="blue"> <b>/</b></font> <%=messageService.getString("e3ps.message.ket_message", "02032") %><%--실적--%>) </td>
        </tr>
      </table>
      <% if(task != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 진행현황 (NEW Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="12%"><COL width="10%"><COL width="78%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td class="tdwhiteL">
            <%=taskData.getEndNodeStateStr()%>
          </td>
          <td class="tdwhiteL0">
            <!-- 진행률 그림 -->
            <table width="100%" border=0 cellspacing="0" cellpadding="0">
              <tr>
                <td width="23%" id="taskPlanStartDateTD" align='right'> (<%=DateUtil.getDateString(taskData.taskPlanStartDate, "D")%> <font color="blue"><b>/</b></font> <%=taskExecStartDateStr%>)</td>
                <td width="54%" id="taskCompletionBarTD" align='center'>
                  <jsp:include page="/jsp/project/ProjectProgressBarView.jsp" flush="false">
                    <jsp:param name="planValue" value="<%=taskData.getPreferCompStr()%>"/>
                    <jsp:param name="workValue" value="<%=taskData.taskCompletion%>"/>
                  </jsp:include>
                </td>
                <td width="23%" id="taskPlanEndDateTD" align='left'>(<%=DateUtil.getDateString(taskData.taskPlanEndDate, "D")%><font color="blue"> <b>/</b></font> <%=taskExecEndDate_exStr%>)</td>
              </tr>
            </table>
            <!-- //진행률 그림 -->
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%></td>
          <td class="tdwhiteL" id="taskCompletionTD"><b><%=taskData.taskCompletion%> / <%=taskData.getPreferCompStr()%></b></td>
          <td class="tdwhiteL0" colspan=2>
            <%=taskData.getStateMessage()%>
          </td>
        </tr>
      </table>
      <!-- 진행현황 (NEW Version) -->
      <%
      }
      %>
      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <!-- space -->
      <%
      if(task2 != null) {
      %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 진행현황 (OLD Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="12%"><COL width="10%"><COL width="78%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td class="tdwhiteL">
            <%=taskData2.getEndNodeStateStr()%>
          </td>
          <td class="tdwhiteL0">
            <!-- 진행률 그림 -->
            <table width="100%" border=0 cellspacing="0" cellpadding="0">
              <tr>
                <td width="23%" id="taskPlanStartDateTD" align='right'> (<%=DateUtil.getDateString(taskData2.taskPlanStartDate, "D")%> <font color="blue"><b>/</b></font> <%=taskExecStartDateStr2%>)</td>
                <td width="54%" id="taskCompletionBarTD" align='center'>
                  <jsp:include page="/jsp/project/ProjectProgressBarView.jsp" flush="false">
                    <jsp:param name="planValue" value="<%=taskData2.getPreferCompStr()%>"/>
                    <jsp:param name="workValue" value="<%=taskData2.taskCompletion%>"/>
                  </jsp:include>
                </td>
                <td width="23%" id="taskPlanEndDateTD" align='left'>(<%=DateUtil.getDateString(taskData2.taskPlanEndDate, "D")%><font color="blue"> <b>/</b></font> <%=taskExecEndDate_exStr2%>)</td>
              </tr>
            </table>
            <!-- //진행률 그림 -->
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03206") %><%--현재/적정(%)--%></td>
          <td class="tdwhiteL" id="taskCompletionTD"><b><%=taskData2.taskCompletion%> / <%=taskData2.getPreferCompStr()%></b></td>
          <td class="tdwhiteL0" colspan=2>
            <%=taskData2.getStateMessage()%>
          </td>
        </tr>
      </table>
      <!-- 진행현황 (OLD Version) -->
      <% } %>
      <!-- 진행현황 비교 -->


      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->


      <!-- 담당부서/TASK 책임자 비교 -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "01203") %><%--담당부서/TASK 책임자--%></td>
          <td align="right">
            &nbsp;
          </td>
        </tr>
      </table>
      <% if(task != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 담당부서/TASK 책임자 (NEW Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL" valign="top">
            <%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%><br>
            <%//if(buttonAuth.isViewDepartment){ %>
            <a href="javascript:addDepartment('refDeptTable', 'addTaskRefDept','refDeptDelete');">
            <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" width="62" height="20" border="0">
            </a>
            <!--input type='button' value='추가' onClick="javascript:addDepartment('refDeptTable', 'addRefDept','refDeptDelete');" class="s_font"-->
            <%//}%>
          </td>
          <td class="tdwhiteL0">
            <div id="refDeptDiv" style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
              <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refDeptTable">
                <tr>
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                  <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                </tr>
                <% if(task.getDepartment() !=null) { %>
                <tr>
                  <td class="tdwhiteL"><%=task.getDepartment().getName()%></td>
                  <td class="tdwhiteM0">
                    <a href="#" onClick="javascript:onDeleteDepartTableRow('refDeptTable', 'refTaskDeptDelete', '<%=task.getDepartment().getPersistInfo().getObjectIdentifier().getStringValue()%>');">
                      <p><img src="/plm/portal/icon/del.gif" width='13' height='12' border='0'></p>
                    </a>
                  </td>
                </tr>
                <% } %>
              </table>
            </div>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00491") %><%--TASK 책임자--%></td>
          <td class="tdwhiteL">
          <%
            String addMaster = null;//request.getParameter("addMaster");
            String deleteMaster = null;//request.getParameter("deleteMaster");
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
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00490") %><%--TASK 참여자--%></td>
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
              <jsp:param name="deleteMaster" value="<%=deleteMaster%>"/>
            </jsp:include>
          </td>
        </tr>
      </table>
      <!-- 담당부서/TASK 책임자 (NEW Version) -->
      <% } %>
      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <!-- space -->
      <% if(task2 != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 담당부서/TASK 책임자 (OLD Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL" valign="top">
            <%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%><br>
            <%//if(buttonAuth.isViewDepartment){ %>
            <a href="javascript:addDepartment('refDeptTable', 'addTaskRefDept','refDeptDelete');">
            <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" width="62" height="20" border="0">
            </a>
            <!--input type='button' value='추가' onClick="javascript:addDepartment('refDeptTable', 'addRefDept','refDeptDelete');" class="s_font"-->
            <%//}%>
          </td>
          <td class="tdwhiteL0">
            <div id="refDeptDiv" style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
              <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refDeptTable">
                <tr>
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                  <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                </tr>
                <% if(task2.getDepartment() !=null) { %>
                <tr>
                  <td class="tdwhiteL"><%=task2.getDepartment().getName()%></td>
                  <td class="tdwhiteM0">
                    <a href="#" onClick="javascript:onDeleteDepartTableRow('refDeptTable', 'refTaskDeptDelete', '<%=task2.getDepartment().getPersistInfo().getObjectIdentifier().getStringValue()%>');">
                      <p><img src="/plm/portal/icon/del.gif" width='13' height='12' border='0'></p>
                    </a>
                  </td>
                </tr>
                <% } %>
              </table>
            </div>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00491") %><%--TASK 책임자--%></td>
          <td class="tdwhiteL">
          <%
            String addMaster2 = null;//request.getParameter("addMaster");
            String deleteMaster2 = null;//request.getParameter("deleteMaster");
            if(addMaster2 == null){
              addMaster2 = "";
            }
            if(deleteMaster2 == null){
              deleteMaster2 = "";
            }
          %>
            <jsp:include page="/jsp/project/TaskMasterInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oldOid%>"/>
              <jsp:param name="addMaster" value="<%=addMaster2%>"/>
              <jsp:param name="deleteMaster" value="<%=deleteMaster2%>"/>
            </jsp:include>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00490") %><%--TASK 참여자--%></td>
          <td class="tdwhiteL">
          <%
            if(addMaster2 == null){
              addMaster2 = "";
            }
            if(deleteMaster2 == null){
              deleteMaster2 = "";
            }
          %>
            <jsp:include page="/jsp/project/TaskMemberInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oldOid%>"/>
              <jsp:param name="addMaster" value="<%=addMaster2%>"/>
              <jsp:param name="deleteMaster" value="<%=deleteMaster2%>"/>
            </jsp:include>
          </td>
        </tr>
      </table>
      <!-- 담당부서/TASK 책임자 (OLD Version) -->
      <% } %>
      <!-- 담당부서/TASK 책임자 비교 -->


      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->


      <!-- 산출물 비교 -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="titleD"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
          <td align="right">
            &nbsp;
          </td>
        </tr>
      </table>
      <% if(task != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 산출물 (NEW Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL" valign="top">
            <%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%>
            <% //if(buttonAuth.isOuputDocAdd) { %>
            <!--br>
            <a href="javascript:outputPage('', 'false');">
            <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%>" width="62" height="20" border="0">
            </a-->
            <!--input type='button' value='추가' onClick="javascript:outputPage('', 'false');" class="s_font"-->
            <% //} %>
          </td>
          <td class="tdwhiteL0"  valign="top">
            <div style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:3px 1px;">
              <table border="0" cellspacing="0" cellpadding="0" width="100%" id="outputTable">
                <tr>
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                  <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
                  <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "02795") %><%--첨부여부--%></td>
                  <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                  <td class="tdblueM" width="30"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                  <td class="tdblueM" width="130"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
                  <% //if(isEdit) {  %>
                  <!--td class="tdblueM0" width="30">삭제</td-->
                  <% //} %>
                </tr>
                <%
                ProjectOutput output = null;
                ProjectOutputData outputData = null;
                PeopleData peopleData = null;
                WTUser outputUser = null;
                String deptName = "";
                String outputChargerName = "";
                String version = "";
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

                  outputChargerName = "&nbsp;";
                  deptName = "&nbsp;";
                  version = "&nbsp;";
                  state = "&nbsp;";
                  modifyDate = "&nbsp;";
                  peopleOID = "&nbsp;";
                  peopleData = null;
                  if(outputUser != null) {
                    outputChargerName = outputUser.getFullName();
                    peopleData = new PeopleData(outputUser);
                    deptName = peopleData.departmentName;
                    peopleOID = (peopleData.people).getPersistInfo().getObjectIdentifier().getStringValue();
                  }

                  if(outputData.isRegistry) {
                    version = outputData.currentDocument.getVersionDisplayIdentifier().toString();
                    state = outputData.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
                    modifyDate = DateUtil.getDateString(outputData.currentDocument.getPersistInfo().getModifyStamp(), "D");
                  }

                  isOwner = false;
                  if(outputUser != null && (wtuser.getName()).equals(outputUser.getName())) {
                    isOwner = true;
                  }
              %>
                  <tr>
                    <td class="tdwhiteL" title="<%=outputData.name%>">
                    <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr>
                      <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>', '<%=outputData.isRegistry%>');"><%=outputData.name%></a>
                      </nobr>
                    </div>
                  </td>
                  <td class="tdwhiteL" title="<%=outputData.objType%>">
                    <div style="width:50;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr><%=outputData.objType%></nobr>
                    </div>
                  </td>

                  <td class="tdwhiteL" title="<%=outputData.isPrimary%>">
                    <div style="width:50;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr><%=outputData.isPrimary%></nobr>
                    </div>
                  </td>
                  <td class="tdwhiteL" title="<%=outputData.locationStr%>">
                    <div style="width:50;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr><%=outputData.locationStr%></nobr>
                    </div>
                  </td>
                  <td class="tdwhiteM" title="<%=deptName%>">
                  <% if(peopleData == null) { %>
                    &nbsp;
                  <% } else { %>
                    <a href="JavaScript:viewPeople('<%=peopleOID%>')"><%=outputChargerName%></a>
                  <% } %>
                  </td>
                  <% if (outputData.isRegistry ) { %>
                  <td class="tdwhiteM"><%=version%></td>
                  <td class="tdwhiteM"><%=state%></td>
                  <td class="tdwhiteM"><%=modifyDate%></td>
                  <% } else { %>
                  <td class="tdwhiteM" colspan="3"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01333") %><%--등록안됨--%></font></td>
                  <%
                     }
                  %>
                  <td class="tdwhiteM">
                <%
                  int outputCompletionAuthType = buttonAuth.isOutputCompletion(output);
                  //Kogger.debug("outputCompletionAuthType============> " + outputCompletionAuthType);
                  if(outputCompletionAuthType == 0) { %>
                    <input name="outputCompletion" class="txt_field" style="width:30%;text-align:right;" size="3" maxlength="3" value="<%=output.getCompletion()%>" progresskey="<%=outputData.oid%>" onkeyup ="SetNum(this)"/><b>%</b>&nbsp;
                    <a href="javascript:outputProgress('<%=outputData.oid%>', false);">
                    <img src="/plm/portal/images/img_default/button/board_btn_input.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%>' width="62" height="20" border="0">
                    </a>
                <%  }

                  if(outputCompletionAuthType == 1) { %>
                    <input name="outputCompletion" class="txt_field" style="width:30%;text-align:right;" size="3" maxlength="3" value="<%=output.getCompletion()%>" progresskey="<%=outputData.oid%>" onkeyup ="SetNum(this)"/><b>%</b>&nbsp;
                    <a href="javascript:outputProgress('<%=outputData.oid%>', true);">
                    <img src="/plm/portal/images/img_default/button/board_btn_input.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%>' width="62" height="20" border="0">
                    </a>
                <%  } else if(outputCompletionAuthType == 3) {
                    out.println(output.getCompletion() + "%");
                  } else if(outputCompletionAuthType == 4) {
                    out.println(output.getCompletion() + "%");
                  } else if(outputCompletionAuthType == 2) {
                    out.println("-");
                  } %>
                  </td>
                  <% if( buttonAuth.isDeleteOutput(output)) { %>
                  <td class="tdwhiteM">
                    <a href="JavaScript:deleteOutput('<%=outputData.oid%>')"><p><img src="/plm/portal/icon/del.gif" width="13" height="12" border="0"></p></a>
                  </td>
                  <% } %>
                </tr>
                <% } %>
                </table>
              </div>
          </td>
        </tr>
      </table>
      <!-- 산출물 (NEW Version) -->
      <% } %>
      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <!-- space -->
      <% if(task2 != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 산출물 (OLD Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL" valign="top">
            <%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%>
            <% //if(buttonAuth.isOuputDocAdd) { %>
            <!--br>
            <a href="javascript:outputPage('', 'false');">
            <img src="/plm/portal/images/img_default/button/board_btn_insert.gif" alt="추가" width="62" height="20" border="0">
            </a-->
            <!--input type='button' value='추가' onClick="javascript:outputPage('', 'false');" class="s_font"-->
            <% //} %>
          </td>
          <td class="tdwhiteL0"  valign="top">
            <div style="width:99%;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:3px 1px;">
              <table border="0" cellspacing="0" cellpadding="0" width="100%" id="outputTable">
                <tr>
                  <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
                  <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "02918") %><%--타입--%></td>
                  <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "02795") %><%--첨부여부--%></td>
                  <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
                  <td class="tdblueM" width="30"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                  <td class="tdblueM" width="80"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                  <td class="tdblueM" width="130"><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
                  <% //if(isEdit) {  %>
                  <!--td class="tdblueM0" width="30">삭제</td-->
                  <% //} %>
                </tr>
                <%
                ProjectOutput output2 = null;
                ProjectOutputData outputData2 = null;
                PeopleData peopleData2 = null;
                WTUser outputUser2 = null;
                String deptName2 = "";
                String outputChargerName2 = "";
                String version2 = "";
                String state2 = "";
                String modifyDate2 = "";
                String peopleOID2 = "";

                boolean isOwner2 = false;
                boolean isState2 = false;
                Object[] opObj2 = null;

                QueryResult outputQr2 = ProjectOutputHelper.manager.getTaskOutput(task2);
                while ( outputQr2.hasMoreElements() ) {
                  opObj2 = (Object[])outputQr2.nextElement();
                  output2 = (ProjectOutput)opObj2[0];
                  outputUser2 = output2.getOwner() == null? null:(WTUser)output2.getOwner().getPrincipal();
                  outputData2 = new ProjectOutputData (output2);

                  outputChargerName2 = "&nbsp;";
                  deptName2 = "&nbsp;";
                  version2 = "&nbsp;";
                  state2 = "&nbsp;";
                  modifyDate2 = "&nbsp;";
                  peopleOID2 = "&nbsp;";
                  peopleData2 = null;
                  if(outputUser2 != null) {
                    outputChargerName2 = outputUser2.getFullName();
                    peopleData2 = new PeopleData(outputUser2);
                    deptName2 = peopleData2.departmentName;
                    peopleOID2 = (peopleData2.people).getPersistInfo().getObjectIdentifier().getStringValue();
                  }

                  if(outputData2.isRegistry) {
                    version2 = outputData2.currentDocument.getVersionDisplayIdentifier().toString();
                    state2 = outputData2.currentDocument.getLifeCycleState().getLocalizedMessage(Locale.KOREAN);
                    modifyDate2 = DateUtil.getDateString(outputData2.currentDocument.getPersistInfo().getModifyStamp(), "D");
                  }

                  isOwner2 = false;
                  if(outputUser2 != null && (wtuser.getName()).equals(outputUser2.getName())) {
                    isOwner2 = true;
                  }
              %>
                  <tr>
                    <td class="tdwhiteL" title="<%=outputData2.name%>">
                    <div style="width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr>
                      <a href="#" onClick="javascript:outputPage('<%=outputData2.oid%>', '<%=outputData2.isRegistry%>');"><%=outputData2.name%></a>
                      </nobr>
                    </div>
                  </td>
                  <td class="tdwhiteL" title="<%=outputData2.objType%>">
                    <div style="width:50;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr><%=outputData2.objType%></nobr>
                    </div>
                  </td>

                  <td class="tdwhiteL" title="<%=outputData2.isPrimary%>">
                    <div style="width:50;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr><%=outputData2.isPrimary%></nobr>
                    </div>
                  </td>
                  <td class="tdwhiteL" title="<%=outputData2.locationStr%>">
                    <div style="width:50;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
                      <nobr><%=outputData2.locationStr%></nobr>
                    </div>
                  </td>
                  <td class="tdwhiteM" title="<%=deptName2%>">
                  <% if(peopleData2 == null) { %>
                    &nbsp;
                  <% } else { %>
                    <a href="JavaScript:viewPeople('<%=peopleOID2%>')"><%=outputChargerName2%></a>
                  <% } %>
                  </td>
                  <% if (outputData2.isRegistry ) { %>
                  <td class="tdwhiteM"><%=version2%></td>
                  <td class="tdwhiteM"><%=state2%></td>
                  <td class="tdwhiteM"><%=modifyDate2%></td>
                  <% } else { %>
                  <td class="tdwhiteM" colspan="3"><font color="red"><%=messageService.getString("e3ps.message.ket_message", "01333") %><%--등록안됨--%></font></td>
                  <%
                     }
                  %>
                  <td class="tdwhiteM">
                <%
                  int outputCompletionAuthType2 = buttonAuth.isOutputCompletion(output2);
                  //Kogger.debug("outputCompletionAuthType============> " + outputCompletionAuthType);
                  if(outputCompletionAuthType2 == 0) { %>
                    <input name="outputCompletion" class="txt_field" style="width:30%;text-align:right;" size="3" maxlength="3" value="<%=output2.getCompletion()%>" progresskey="<%=outputData2.oid%>" onkeyup ="SetNum(this)"/><b>%</b>&nbsp;
                    <a href="javascript:outputProgress('<%=outputData2.oid%>', false);">
                    <img src="/plm/portal/images/img_default/button/board_btn_input.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%>' width="62" height="20" border="0">
                    </a>
                <%  }

                  if(outputCompletionAuthType2 == 1) { %>
                    <input name="outputCompletion" class="txt_field" style="width:30%;text-align:right;" size="3" maxlength="3" value="<%=output2.getCompletion()%>" progresskey="<%=outputData2.oid%>" onkeyup ="SetNum(this)"/><b>%</b>&nbsp;
                    <a href="javascript:outputProgress('<%=outputData2.oid%>', true);">
                    <img src="/plm/portal/images/img_default/button/board_btn_input.gif" alt='<%=messageService.getString("e3ps.message.ket_message", "01352") %><%--링크등록--%>' width="62" height="20" border="0">
                    </a>
                <%  } else if(outputCompletionAuthType2 == 3) {
                    out.println(output2.getCompletion() + "%");
                  } else if(outputCompletionAuthType2 == 4) {
                    out.println(output2.getCompletion() + "%");
                  } else if(outputCompletionAuthType2 == 2) {
                    out.println("-");
                  } %>
                  </td>
                  <% if( buttonAuth.isDeleteOutput(output2)) { %>
                  <td class="tdwhiteM">
                    <a href="JavaScript:deleteOutput('<%=outputData2.oid%>')"><p><img src="/plm/portal/icon/del.gif" width="13" height="12" border="0"></p></a>
                  </td>
                  <% } %>
                </tr>
                <% } %>
                </table>
              </div>
          </td>
        </tr>
      </table>
      <!-- 산출물 (OLD Version) -->
      <% } %>
      <!-- 산출물 비교 -->


      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space20"></td>
        </tr>
      </table>
      <!-- space -->


      <!-- 선행태스크 비교 -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 선행태스크 (NEW Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
          <td class="tdwhiteL">
            <jsp:include page="/jsp/project/TaskDependencyInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oid%>"/>
              <jsp:param name="addTask" value=""/>
              <jsp:param name="deleteTask" value=""/>
            </jsp:include>
          </td>
        </tr>

      </table>
      <!-- 선행태스크 (NEW Version) -->

      <!-- space -->
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <!-- space -->
      <% if(task2 != null) { %>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm5"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <!-- 선행태스크 (OLD Version) -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
      <COL width="15%"><COL width="85%">
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01837") %><%--선행태스크--%></td>
          <td class="tdwhiteL">
            <jsp:include page="/jsp/project/TaskDependencyInfo_include.jsp" flush="false">
              <jsp:param name="oid" value="<%=oldOid%>"/>
              <jsp:param name="addTask" value=""/>
              <jsp:param name="deleteTask" value=""/>
            </jsp:include>
          </td>
        </tr>

      </table>
      <!-- 선행태스크 (OLD Version) -->
      <% } %>
      <!-- 선행태스크 비교 -->

      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <jsp:include page="/jsp/common/space_include.jsp" flush="false"/>
      <!------------------------------ 본문 끝 //------------------------------>
    </td>
  </tr>
</table>
<!-- 본문외관테두리 끝 //-->
</form>
</body>
<script type="text/javascript">
function viewTask(oid){
    openView(oid);
}
</script>
</html>
<%
}catch(Exception  e){
    Kogger.error(e);
}
%>
