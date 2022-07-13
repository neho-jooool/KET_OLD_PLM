<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*" %>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
  String oid = request.getParameter("oid");
  Object obj = CommonUtil.getObject(oid);
  String projectOid = oid;


  TemplateTask task = null;
  TemplateProject project = null;
  if ( obj instanceof TemplateProject )
  {
    project = (TemplateProject)obj;
  }
  else if ( obj instanceof TemplateTask )
  {
    task = (TemplateTask)obj;
  }

  if(project == null) {
    projectOid = (task.getProject()).getPersistInfo().getObjectIdentifier().getStringValue();
  }
%>
<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<style type="text/css">
<!--
.style1 {
    color: #FF0000
}
.layer1  {
    border:1px;
    border-style:outset;
    border-color:#5F9EA0;
    color:#000033;
    background-color:#ffffff;
    layer-background-color:#ffffff;
    position:absolute;
    top:100px;
    left:100px;
    width:460px;
    height:150px;
    z-index:9;
    visibility:hidden;
}
-->
</style>
<script language='javascript'>
<!--

//선행 Task 추가 --------------------------------------------------------------------------------------------------------------
function onDeletePreTask(linkid) {
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01848") %><%--선행 Task를 삭제 하시겠습니까?--%>")) {
    return;
  }
  onProgressBar();

  var param = "command=deletepretask&oid=<%=oid%>&linkOid=" + linkid;
  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp?" + param;
  callServer(url, onMessagePreTask);
}

function gotoPreTaskPage() {
  var url = "/plm/jsp/project/template/ProjectPreTaskSelect.jsp?mode=multi&oid=<%=oid%>";
  returnTask = window.showModalDialog(url,window,"help=no; scroll=yes; dialogWidth=420px; dialogHeight:400px; center:yes");
  if(typeof returnTask == "undefined" || returnTask == null) {
    return;
  }
  attachePreTask(returnTask);
}

function attachePreTask(arrObj) {

  if(arrObj.length == 0) {
    return;
  }

  var taskArr = new Array();

  var subarr;
  var tOid;
  for(var i = 0; i < arrObj.length; i++) {
    subarr = arrObj[i];
    tOid = subarr[0];

    if(!isDuplicatePreTask('preTaskOid', tOid)) {
      taskArr[i] = tOid;
    }
  }

  if(taskArr.length == 0) {
    return;
  }

  var param = "command=addpretask&oid=<%=oid%>";
  param += "&preTaskOid=" + encodeURIComponent(taskArr);

  onProgressBar();

  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp?" + param;
  callServer(url, onMessagePreTask);
}

function isDuplicatePreTask(objstr, compOid) {
  var tobj = document.getElementById(objstr);
  if(tobj == null) {
    return false;
  }

  len = tobj.length;
  if(len) {
    for(var i = 0; i < len;i++) {
      if(tobj[i].value == compOid) {
        return true;
      }
    }
  }
  else {
    if(tobj.value == compOid) {
      return true;
    }
  }

  return false;

}

function onMessagePreTask(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
  } else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
    return;
  }

  parent.parent.tree.document.location.href = "/plm/jsp/project/template/ProjectTempTaskTree.jsp?oid=<%=projectOid%>";

  var tasktable = document.getElementById("pretasklist");
  var len = tasktable.rows.length;
  for(var i=len; i > 1; i--) {
    tasktable.deleteRow(i-1);
  }

  showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_linkoid = showElements[0].getElementsByTagName("l_linkoid");

  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_duration = showElements[0].getElementsByTagName("l_duration");
  var l_chief = showElements[0].getElementsByTagName("l_chief");
  if(l_oid.length == 0) {
    newTr = tasktable.insertRow(1);
    newTd = newTr.insertCell(0);
    newTd.className = "tdwhiteM0";
    newTd.colSpan = "5";
    newTd.innerText = "<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>";
  }else{
    var trhtml = "";
    for(var i = 0; i < l_oid.length; i++) {
      newTr = tasktable.insertRow(tasktable.rows.length);
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = (i+1);

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = decodeURIComponent(l_name[i].text);

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = decodeURIComponent(l_duration[i].text);


      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.title = decodeURIComponent(l_chief[i].text);
      newTd.innerText = " ";


      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeletePreTask('" + decodeURIComponent(l_linkoid[i].text) + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='preTaskOid' value='"+ decodeURIComponent(l_oid[i].text) + "'>";
    }
  }
}
//선행 Task 추가 --------------------------------------------------------------------------------------------------------------

//Task 추가/삭제 --------------------------------------------------------------------------------------------------------------
function onTaskLayer(szDivID, iState) // 1 visible, 0 hidden
{
    if(document.layers)     //NN4+
    {
       document.layers[szDivID].visibility = iState ? "show" : "hide";
    }
    else if(document.getElementById)    //gecko(NN6) + IE 5+
    {
        var obj = document.getElementById(szDivID);
        obj.style.visibility = iState ? "visible" : "hidden";
    }
    else if(document.all)  // IE 4
    {
        document.all[szDivID].style.visibility = iState ? "visible" : "hidden";
    }

  initTask();
}

function initTask() {
  form = document.forms[0];
  form.name.value = '';
  form.duration.value = '1';
  form.description.value = '';
  form.childOid.value = '';
}

function addTask() {
  form = document.forms[0];
  if(form.name.value == '') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00483") %><%--Task 명을 입력하시기 바랍니다--%>");
    form.name.focus();
    return;
  }

  if(form.duration.value == '') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "01113") %><%--기간을 입력해 주십시오--%>");
    form.duration.focus();
    return;
  }

  var param = "command=addtask&oid=<%=oid%>";
  param += "&name=" + form.name.value;
  param += "&duration=" + form.duration.value;
  param += "&description=" + form.description.value;
  param += "&childOid=" + form.childOid.value;

  onProgressBar();

  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp?" + param;
  callServer(url, onMessage);
}

function onDeleteTask(taskid) {
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "00494") %><%--Task를 삭제하시겠습니까?--%>")) {
    return;
  }
  onProgressBar();

  var param = "command=deletetask&oid=<%=oid%>&taskOid=" + taskid;
  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp?" + param;
  callServer(url,onMessage);
}
//Task 추가/삭제 --------------------------------------------------------------------------------------------------------------

//WBS 추가 --------------------------------------------------------------------------------------------------------------
function gotoWbsPage() {
  var url = "/plm/jsp/project/wbs/ProjectWbsSelect.jsp?mode=multi";
  returnWbs = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:460px; center:yes");
  if(typeof returnWbs == "undefined" || returnWbs == null) {
    return;
  }
  attacheWbs(returnWbs);
}

function attacheWbs(arrObj) {

  if(arrObj.length == 0) {
    return;
  }

  var wbsArr = new Array();

  var subarr;
  var wbsOid;
  for(var i = 0; i < arrObj.length; i++) {
    subarr = arrObj[i];
    wbsOid = subarr[0];

    wbsArr[i] = wbsOid;
  }

  if(wbsArr.length == 0) {
    return;
  }

  var param = "command=addwbs&oid=<%=oid%>";
  param += "&wbs=" + encodeURIComponent(wbsArr);

  onProgressBar();

  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp?" + param;
  callServer(url, onMessage);
}

function onMessage(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "02780") %><%--처리 완료했습니다--%>');
  } else {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
    return;
  }

  parent.parent.tree.document.location.href = "/plm/jsp/project/template/ProjectTempTaskTree.jsp?oid=<%=projectOid%>";

  var tasktable = document.getElementById("tasklist");
  var len = tasktable.rows.length;
  for(var i=len; i > 1; i--) {
    tasktable.deleteRow(i-1);
  }

  showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_seq = showElements[0].getElementsByTagName("l_seq");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_duration = showElements[0].getElementsByTagName("l_duration");
  var l_description = showElements[0].getElementsByTagName("l_description");
  if(l_oid.length == 0) {
    newTr = tasktable.insertRow(1);
    newTd = newTr.insertCell(0);
    newTd.className = "tdwhiteM0";
    newTd.colSpan = "5";
    newTd.innerText = "<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>";
  }else{
    var trhtml = "";
    for(var i = 0; i < l_oid.length; i++) {
      newTr = tasktable.insertRow(tasktable.rows.length);
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = decodeURIComponent(l_seq[i].text);

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.innerText = decodeURIComponent(l_name[i].text);

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = decodeURIComponent(l_duration[i].text);


      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.title = decodeURIComponent(l_description[i].text);
      newTd.innerHTML = "<div style='width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>" + decodeURIComponent(l_description[i].text) + "</nobr></div>";


      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteTask('" + decodeURIComponent(l_oid[i].text) + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
    }
  }

  onTaskLayer('layer1',0);
}
//WBS 추가 --------------------------------------------------------------------------------------------------------------
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="ajaxProgress.html"%>
<form method="post">
<!-- hidden begin -->
<input type='hidden' name='childOid' value=''>
<!-- hidden end -->
<table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
  <tr>
    <td valign="top" style="padding-left:0px;padding-top:10px">
      <!-------------------------------------- 하위 Task 시작 //-------------------------------------->
      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
        <tr>
          <td>
            <table border=0 cellpadding=0 cellspacing=0 >
              <tr>
                <td><img src=/plm/portal/images/title2_left.gif></td>
                                <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "03139") %><%--하위 태스크 목록--%></td>
                <td><img src=/plm/portal/images/title2_right.gif></td>
              </tr>
            </table>
          </td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
<%
  if(project != null) {
%>
                <td class=fixLeft></td>
                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "00561") %><%--WBS 추가--%>" onClick="Javascript:gotoWbsPage();"></td>
                <td class=fixRight></td>
<%
  }

  if(task != null) {
%>
                <td class=fixLeft></td>
                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "00492") %><%--Task 추가--%>" onClick="onTaskLayer('layer1',1);"></td>
                <td class=fixRight></td>
<%
  }
%>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01" id='tasklist'>
        <tr>
                    <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "01983") %><%--순차--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
          <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
          <td class="tdblueM" width="200"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
          <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
        </tr>
<%
  QueryResult qr = null;
  if(project != null) {
    qr = ProjectTaskHelper.manager.getChild(project);
  }

  if(task != null) {
    qr = ProjectTaskHelper.manager.getChild(task);
  }

  if(qr == null || qr.size() == 0) {
%>
        <tr>
          <td class="tdwhiteM0" colspan='5'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
        </tr>
<%
  }
  else {
    TemplateTaskData data = null;
    boolean canDelete = false;
    String desc = null;

    Object robj[] = null;
    while(qr.hasMoreElements()) {
      robj = (Object[])qr.nextElement();
      data = new TemplateTaskData((TemplateTask)robj[0]);

      canDelete = data.canDelete();
      desc = data.description;
      if(desc != null){
        desc = replace(desc,"\n","\\n");
        desc = replace(desc,"'","\\\'");
      } else {
        desc = "";
      }
%>
        <tr>
          <td class="tdwhiteM"><%=data.seq%></td>
          <td class="tdwhiteL"><%=data.name%></td>
          <td class="tdwhiteM"><%=data.duration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
          <td class="tdwhiteL" title="<%=desc%>">
            <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr><%=desc%></nobr>
            </div>

          </td>
          <td class="tdwhiteM0">
            <a href="#" onClick="javascript:onDeleteTask('<%=data.taskOID%>');"><p>
              <img src="/plm/portal/icon/del.gif" width="13" height="12" border="0">
            </p></a>
          </td>
        </tr>

<%
    }
  }
%>
      </table>
      <!-------------------------------------- 하위 Task 끝 //-------------------------------------->
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
<%
  if(task != null) {
%>
<!-------------------------------------- 선행 Task 시작 //-------------------------------------->
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>

      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
        <tr>
          <td>
            <table border=0 cellpadding=0 cellspacing=0 >
              <tr>
                <td><img src=/plm/portal/images/title2_left.gif></td>
                <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01826") %><%--선행 Task 목록--%></td>
                <td><img src=/plm/portal/images/title2_right.gif></td>
              </tr>
            </table>
          </td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>

                <td class=fixLeft></td>
                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01831") %><%--선행Task 추가--%>" onClick="gotoPreTaskPage();"></td>
                <td class=fixRight></td>

              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="tab_btm1"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01" id='pretasklist'>
        <tr>
                    <td class="tdblueM" width="50"><%=messageService.getString("e3ps.message.ket_message", "01983") %><%--순차--%></td>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%></td>
          <td class="tdblueM" width="60"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
                    <td class="tdblueM" width="200"><%=messageService.getString("e3ps.message.ket_message", "02773") %><%--책임자--%></td>
          <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
        </tr>
<%
    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);
    if(dependList == null || dependList.size() == 0) {
%>

        <tr>
          <td class="tdwhiteM0" colspan='5'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
        </tr>

<%
    }
    else
    {
      int pre_idx = 0;
      String linkOid = "";
      String taskOID = "";
      String taskName = "";
      String taskDuration = "";
      String taskUserStr = "";
      String taskCompletion = "";

      TaskDependencyLink link = null;
      TemplateTaskData dependData = null;
      while ( dependList.hasMoreElements() ) {
        link = (TaskDependencyLink)dependList.nextElement();
        linkOid = link.getPersistInfo().getObjectIdentifier().getStringValue();
        taskOID = "";
        taskName = "";
        taskDuration = "";
        taskUserStr = "";
        taskCompletion = "";

        dependData = new TemplateTaskData(link.getDependTarget());
        taskOID = dependData.taskOID;
        taskName = dependData.name;
        taskDuration = dependData.duration+"";
        taskUserStr = "&nbsp;";//dependData.getManagerStr();
%>
        <input type="hidden" name="preTaskOid" value="<%=taskOID%>">
        <tr>
          <td class="tdwhiteM"><%=++pre_idx%></td>
          <td class="tdwhiteL"><%=taskName%></td>
          <td class="tdwhiteM"><%=taskDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
          <td class="tdwhiteM"><%=taskUserStr%></td>
          <td class="tdwhiteM0">
            <a href="#" onClick="javascript:onDeletePreTask('<%=linkOid%>');"><p>
              <img src="/plm/portal/icon/del.gif" width="13" height="12" border="0">
            </p></a>
          </td>
        </tr>
<%
      }
    }
%>

      </table>
<!-------------------------------------- 선행 Task 끝 //-------------------------------------->
<%
  }
%>
    </td>
  </tr>
</table>
<!-- ########################################################################################################## -->
<!-- Task 등록 layer 시작 -->
<div ID="layer1" class="layer1">

  <table border="0" cellpadding="0" cellspacing="0" class="popBox">
    <tr>
      <td class="boxTLeft"></td>
      <td class="boxTCenter"></td>
      <td class="boxTRight"></td>
    </tr>
    <tr>
      <td class="boxLeft"></td>
      <td>

<!------------------------------------- 본문 시작 //----------------------------------------->
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td align="right">
                            <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>' onClick="javascript:addTask();" class="s_font">
              <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="javascript:onTaskLayer('layer1',0);" class="s_font">
            </td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="tab_w03">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="tab_w03">
          <tr>
            <td class="tab_btm1"></td>
          </tr>
        </table>
           <table border="0" cellspacing="0" cellpadding="0" class="tab_w03">
            <col width='30%'><col width='70%'>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00496") %><%--Task명--%> <font color="red">*</font></td>
              <td class="tdwhiteL0">
                <input name="name" class="txt_field" style="width:99%;" value="">
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%><font color="red">*</font></td>
              <td class="tdwhiteL0">
                <input name="duration" class="txt_field" style="width:10%;text-align:right;" value="1"> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%>
              </td>
            </tr>
            <tr>
              <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
              <td class="tdwhiteL0">
                <textarea name="description" cols="55" rows="3" class="fm_area"onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"></textarea>
              </td>
            </tr>
          </table>

<!------------------------------------- 본문 끝 //----------------------------------------->
      </td>
      <td class="boxRight"></td>
    </tr>
    <tr>
      <td class="boxBLeft"></td>
      <td valign="bottom" class="boxBCenter"></td>
      <td class="boxBRight"></td>
    </tr>
  </table>
</div>
<!-- Task 등록 layer 끝 -->
<!-- ########################################################################################################## -->
</form>
</body>
</html>
<%!
private String replace(String str, String pattern, String replace) {
    if(str==null){
      return str;
    }
    int s = 0;
    int e = 0;
    StringBuffer result = new StringBuffer();
    while((e=str.indexOf(pattern,s))>=0){
      result.append(str.substring(s,e));
      result.append(replace);
      s=e+pattern.length();
    }
    result.append(str.substring(s));
    return result.toString();
}
%>
