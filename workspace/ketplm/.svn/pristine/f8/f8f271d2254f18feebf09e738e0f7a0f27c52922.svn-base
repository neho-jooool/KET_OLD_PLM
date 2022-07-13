<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*,wt.query.*" %>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
  String oid = request.getParameter("oid");
  Object obj= CommonUtil.getObject(oid);
  String title = "";
  int duration = 0;
  TemplateTask task = null;
  TemplateProject project = null;
  TemplateTask displayTask = null;
  boolean isLevelOne = false;
  String readOnly = "";
  String tcoid = "";

  String projectType = "";

  if ( obj instanceof TemplateProject ) {
    isLevelOne = true;
    project = (TemplateProject)obj;
        title = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : " + project.getPjtName();
    duration = ((ScheduleData)project.getPjtSchedule().getObject()).getDuration();

    if(project instanceof e3ps.project.ProductTemplateProject)
      projectType = "product";
    else if(project instanceof e3ps.project.MoldTemplateProject)
      projectType = "mold";

  } else if ( obj instanceof TemplateTask ) {
    task = (TemplateTask)obj;
    displayTask = (TemplateTask)obj;
    title = messageService.getString("e3ps.message.ket_message", "02937")/*템플릿 프로젝트 명*/ + " : " + task.getProject().getPjtName();

    if(task.getParent() == null){
      readOnly = "READONLY";
    }

    tcoid = CommonUtil.getOIDString(task);
    duration = ((ScheduleData)task.getTaskSchedule().getObject()).getDuration();
    //qr = ProjectTaskHelper.manager.getChild(task);
    oid = CommonUtil.getOIDString(task);
  }

  boolean selectCanDelete = false;
  String coid = "";
  String taskName = "";
  String durationStr = "";
  String stdWorkStr = "";  //표준공수

  String optiontype = "Y"; //필수여부
  String milestone = "N";  //Milestone 여부
  String tasktype = "";    //Task 종류
  String drvalue = "";    //DR 목표 값

  if ( displayTask != null ) {
    TemplateTaskData displayData = new TemplateTaskData(displayTask);
    selectCanDelete = displayData.canDelete();
    coid = displayData.taskOID;
    taskName = displayData.name;
    durationStr = displayData.duration + "";
    stdWorkStr = displayData.stdWork + "";

    optiontype = displayData.optiontype;
    milestone = displayData.milestone;
    tasktype = displayData.tasktype;
    drvalue = displayData.drvalue;

    if(displayData.project instanceof e3ps.project.ProductTemplateProject)
      projectType = "product";
    else if(displayData.project instanceof e3ps.project.MoldTemplateProject)
      projectType = "mold";
  }


%>

<html>
<head>
<head>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<%@include file="/jsp/common/top_include.jsp" %>

<SCRIPT language=JavaScript  src="/plm/portal/js/ajax.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/script.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/common.js"></SCRIPT>
<SCRIPT language=JavaScript src="/plm/portal/js/org.js"></SCRIPT>
<SCRIPT language="JavaScript" src="/plm/portal/js/tableScriptChild2.js"></SCRIPT>

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>

<script language="javascript">

  var ajax = new sack();

  function checkfirst(){
    var objBody = document.getElementById("taskTable");
    if(objBody.rows.length == 0){
      return;
    }

    var cbox=document.forms[0].check;

    if (cbox != null) {
        // 2이상 일때
          if (cbox.length) {
              // 체크된 리스트를 삭제한다.
          cbox[0].checked = true;
          }
          // 1명 일때
          else if (cbox)  {
        cbox.checked = true;
           }
      }
  }

  function reCheck(){
    var cbox=document.forms[0].check;

    if (cbox != null) {
        // 2이상 일때
          oid = null;
          if (cbox.length) {
              // 체크된 리스트를 삭제한다.
              for (var i = cbox.length; i > 0 ; i--)  {

                if(cbox[i - 1].checked == true){
                  oid = cbox[i - 1].value;
                  break;
                }
              }
          }
          // 1명 일때
          else if (cbox)  {
              // 체크된 리스트를 삭제한다.
        oid = cbox.value;
           }
           selectTask(oid);
      }
  }

  function checkClick(oid){

    var cbox=document.forms[0].check;

    if (cbox != null) {
        // 2이상 일때
          if (cbox.length) {
              // 체크된 리스트를 삭제한다.
              for (var i = cbox.length; i > 0 ; i--)  {
                  if (cbox[i - 1].value == oid) {
            cbox[i - 1].checked = true;
                  }
              }
          }
          // 1명 일때
          else if (cbox)  {
              // 체크된 리스트를 삭제한다.

              if (cbox.value == oid) {
          cbox.checked = true;
              }
           }
      }
  }

  function mainTask(){
    var oid = document.getElementById("oid");
    if(oid.value.length>0){
      ajax.requestFile = 'TemplateProjectTaskAjax.jsp?oid='+oid.value;
      ajax.onCompletion = mainList;
      ajax.runAJAX();
    }
  }

  function deleteRow()
  {
    var objBody = document.getElementById("taskTable");
    //alert(objBody.rows.length);

    var intseq = objBody.rows.length;
    for(var i = 0 ; i < intseq ; i++){
      objBody.deleteRow(0);
    }
  }

  function upDownTask(type){
    var oid = document.getElementById("oid");
    var check = chk=document.forms[0].check;
    var len = check.length;
    var selectedList;
    if(check != null){
      if(len > 1){
        for(var i=0 ; i<len ; i++)
        {
          if (check.check[i].checked==true)
            selectedList = check.check[i].value;
        }
      }else {
        if (check.checked==true) selectedList = check.value;
      }
    }

    if(check.value == ""){
      alert("<%=messageService.getString("e3ps.message.ket_message", "00495") %><%--Task를 선택 해주세요--%>");
      return;
    }

    if(len>0){
      disabledAllBtn();
      document.forms[0].command.value = type;
      ajax.requestFile = 'TemplateProjectTaskAjax.jsp';
      ajax.URLString = getPostData(document.forms[0]);
      ajax.onCompletion = Complated;
      ajax.runAJAX();
    }
  }

  function Complated()
  {
      if(ajax.response != ""){
        if( ajax.response != undefined) {
          var scriptString = ajax.response;
          var delStringIndex = scriptString.indexOf("<script language=\"javascript\">");
          if(delStringIndex > 0) {
            scriptString = scriptString.substring(delStringIndex+32);
            scriptString = scriptString.substring(0, scriptString.length-11);
            }
          eval(scriptString);
        }
        if(document.forms[0].command.value == "create" ){
        enabledAllBtn();
          return;
        }
      }

      ajax.requestFile = "TemplateTaskListAjax.jsp";
    ajax.URLString = getPostData(document.forms[0]);
    ajax.onCompletion = reload;
    ajax.runAJAX();
  }

  function ComplatedModify(){
    if(ajax.response != ""){
        eval(ajax.response);
      }
  }

  function reload(){
    var objBody = document.getElementById("taskTable");
    deleteRow();
    eval(ajax.response);
    enabledAllBtn();

    if(document.forms[0].command.value != ""){
      parent.tree.reload(document.forms[0].oid.value);
    }
  }

  function mainList()
  {
    var objBody = document.getElementById("taskTable");
    eval(ajax.response);
  }


var canDelete = <%=selectCanDelete%>;

function openCal(param){
  var url="/plm/jsp/common/calendar.jsp?form=ManageProjectTaskList&obj="+param;
  openOtherName(url,"calendar","200","250","status=no,scrollbars=no,resizable=no");
}



function settingTask(){
  if(ajax.response != ""){
        eval(ajax.response);
  }
}

function selectTask(oid) {
  var taskoid = oid;
  ajax.requestFile = "/plm/jsp/project/manage/ManageTemplateProjectTaskSelectAjax.jsp?taskoid=" + taskoid;
  ajax.onCompletion = settingTask;
  ajax.runAJAX();
}

function createTask(obj){
  <%//if(!isLevelOne){%>
  if( checkField(document.forms[0].cname, "TASK 명") ){
    return;
  }
  <%//}%>

  if( checkField(document.forms[0].duration, "기간") ){
    return;
  }

  //if( document.forms[0].cnameCheck.checked){
    //if(document.forms[0].wbsItem.value == ""){
      //alert("ERP 연계 테스크를 선택하십시오");
      //return;
    //}
  //}

  disabledAllBtn();

  document.forms[0].command.value = "create";
  ajax.requestFile = "/plm/servlet/e3ps/ManageTemplateProjectTaskServlet";

  ajax.URLString = getPostData(document.forms[0]);
  ajax.onCompletion = Complated;
  ajax.runAJAX();


}

function thisUpdate(obj){
  if(isNullData(document.forms[0].tcoid.value)){
    alert("<%=messageService.getString("e3ps.message.ket_message", "01960") %><%--수정할 태스크를 선택해 주세요--%>");
    return;
  }
  //alert(document.forms[0].tcoid.value);
    //disabledAllBtn();



    document.forms[0].command.value = "updateTask";
    ajax.requestFile = "/plm/servlet/e3ps/ManageTemplateProjectTaskServlet";

    ajax.URLString = getPostData(document.forms[0]);

    ajax.onCompletion = ComplatedModify;
    ajax.runAJAX();
}

function updateTask(obj){
    if(isNullData(document.forms[0].coid.value)){
      alert("<%=messageService.getString("e3ps.message.ket_message", "01960") %><%--수정할 태스크를 선택해 주세요--%>");
      return;
    }

    <%if(!isLevelOne){%>
    if( checkField(document.forms[0].cname, "TASK 명") ){
      return;
    }
    <%}%>

    if( checkField(document.forms[0].duration, "기간") ){
      return;
    }

    //if( document.forms[0].cnameCheck.checked){
      //if(document.forms[0].wbsItem.value == ""){
        //alert("ERP 연계 테스크를 선택하십시오");
        //return;
      //}
    //}

    disabledAllBtn();

    document.forms[0].command.value = "update";
    ajax.requestFile = "/plm/servlet/e3ps/ManageTemplateProjectTaskServlet";

    ajax.URLString = getPostData(document.forms[0]);

    ajax.onCompletion = Complated;
    ajax.runAJAX();
}

function deleteTask(obj){
  if(isNullData(document.forms[0].coid.value)){
    alert("<%=messageService.getString("e3ps.message.ket_message", "01714") %><%--삭제할 태스크를 선택해 주세요--%>");
    return;
  }

  if ( confirm('<%=messageService.getString("e3ps.message.ket_message", "00494") %><%--Task를 삭제하시겠습니까?--%>') ) {
    disabledAllBtn();

    document.forms[0].command.value = "delete";
    ajax.requestFile = "/plm/servlet/e3ps/ManageTemplateProjectTaskServlet";
    ajax.URLString = getPostData(document.forms[0]);

    ajax.onCompletion = Complated;
    ajax.runAJAX();
  }
}

function close(){
  parent.close();
}

function thiscolse(){
  //parent.opener.document.location.href = parent.opener.document.URL;
  parent.close();
}

//parent.tree.document.location.href = parent.tree.document.URL;

function cnameClear(){

  //if (document.forms[0].cnameCheck.checked){
    //document.forms[0].cname.value = "";
  //}else{
    //document.forms[0].wbsItem.value = "";
    //reCheck();
  //}
}

///////////////////////////////
///////wbs Item Search/////////
var txtObj;
var valueObj;
var set_number;
function taskNameInput(tObj, vstr, ltdlen) {
    //if(!document.forms[0].cnameCheck.checked){
      //return;
    //}

  form = document.forms[0];

  txtObj = tObj;
  valueObj = document.getElementById(vstr);

  var dpjtcode = txtObj.value;
  valueObj.value = '';

  if(dpjtcode.length < ltdlen) {
    offLayer('layer0');
    return;
  }

  if (window.event.keyCode == 13) {
    set_number = true;
  }
  else {
    set_number = false;
  }

  rectObj = tObj.getBoundingClientRect();
  onLayer('layer0', rectObj.left, rectObj.bottom, 250, lheight);

  form = document.forms[0];
  var param = "command=autoComplete";
  param += "&cname=" + txtObj.value;
  var url = "/plm/jsp/project/manage/WBSITemSearchAjax.jsp";


  ajax.requestFile = url;
  ajax.URLString = param;
  ajax.onCompletion = setLayerMember;
  ajax.runAJAX();

  //callServer(url, setLayerMember);
  //postCallServer(url, param, setLayerMember, false);
}

function setLayerMember(req) {
    //alert(ajax.response);
  var xmlDoc = ajax.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'false') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03094") %><%--프로젝트 정보를 가져오는 중 에러가 발생했습니다\n다시 시도하시기 바랍니다--%>');
    return;
  }

  var showElements = xmlDoc.selectNodes("//data_info");
  var l_code = showElements[0].getElementsByTagName("l_code");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_oid = showElements[0].getElementsByTagName("l_oid");


  if(set_number && l_code.length == 1) {
    valueObj.value = decodeURIComponent(l_oid[0].text);
    txtObj.value = decodeURIComponent(l_name[0].text);
    offLayer('layer0');
    return;
  }

  var htmlStr = "";
  if(l_code != null && l_code.length > 0) {
    for(var i = 0; i < l_code.length; i++) {
      r_code = decodeURIComponent(l_code[i].text);
      r_name = decodeURIComponent(l_name[i].text);
      r_oid = decodeURIComponent(l_oid[i].text);

      s_name = r_name;
      if(s_name.length > 20) {
        s_name = s_name.substring(0, 17) + "...";
      }

      v_code = r_code;
      if(set_number != null && set_number == 'true') {
        v_code = v_code.substring(2);
      }
      //J-12345678 -->> 12345678
      htmlStr +="<li title='"+r_name+"'><a href='#' onclick=\"javascript:setLayerData('"+r_oid+"','" + r_name + "');\">"+ r_code + "&nbsp;|&nbsp;" + s_name +"</a></li>";
    }
  }
  else {
    htmlStr += "<li style='text-align:center;'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></li>";
  }

  onLayerClear("layer0content");
  var layerContent = document.getElementById("layer0content");
  layerContent.innerHTML = "<ul>" + htmlStr + "</ul>";
}


function setLayerData(keystr, display) {
  valueObj.value = keystr;
  txtObj.value = display;

  offLayer('layer0');
}
///////////////////////////////////////////

  function displayChange(){
    form = document.forms[0];
    var tasktypeValue = "";
    for(i=0; i<form.taskType.length; i++) {
      if(form.taskType[i].selected) tasktypeValue = form.taskType[i].value;
    }

    if(tasktypeValue == "DR") {
      drvalueTd1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%>";
      drvalueTd2.innerHTML = "<INPUT id=i size=7 name=drValue value=\"<%=drvalue%>\" onkeyup =\"SetNum(this)\" style='IME-MODE: disabled'>";
    }else {
      drvalueTd1.innerHTML = "&nbsp;";
      drvalueTd2.innerHTML = "&nbsp;";
    }
  }



</script>
</head>
<body onload ="Complated();">
<form method="post" name="test">
<input type="hidden" name="command">
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="coid" value="<%=coid%>">
<input type="hidden" name="tcoid" value="<%=tcoid%>">
<input type="hidden" name="cseq">

<table width="700" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
  <%
String durationStr2 = "";
if (obj instanceof TemplateTask) {
  TemplateTaskData taskData = new TemplateTaskData(task);
  durationStr2 = ""+ taskData.duration;

%>
  <table width="700" height=20" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
        <td class="font_03"><p><%=messageService.getString("e3ps.message.ket_message", "03205") %><%--현재 태스크 정보--%></p></td>
        <td align="right">&nbsp;</td>
      </tr>
    </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%></td>
          <td width="250" class="tdwhiteM">&nbsp;<INPUT id=i style="width:100%;border:0;" name=tcname readOnly value="<%=task.getTaskName()%>"></td>
          <td width="150" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
          <td width="150" class="tdwhiteM0">
          <INPUT id=i size=7 name=tduration value="<%=(durationStr2.equals("") )?"1":durationStr2%>" style="border:0;" readOnly> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
        </tr>
        <tr>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02925") %><%--태스크 설명--%></td>
          <td colspan="3" class="tdwhiteL0" style="height:100">&nbsp;<textarea name="tdesc" rows="4" readOnly id=input style="width:99%; height:96%;border:0"><%=StringUtil.checkNull(task.getTaskDesc()) %></textarea><!--<textarea name="textfield3" class="txt_field" id="textfield3" style="width:100%; height:96%"></textarea>--></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
<%
}
%>
      <table width="700" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03139") %><%--하위 태스크 목록--%></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:upDownTask('up');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02229") %><%--위로--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:upDownTask('down');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02069") %><%--아래로--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <div class=sbar style='width:700px;height:290px;overflow:auto'>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr>
            <td width="10%" class="tdblueM">&nbsp;</td>
              <td width="10%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01983") %><%--순차--%></td>
            <td width="25%" class="tdblueM">Task</td>
            <td width="20%" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
            <td width="15%" class="tdblueM">Milestone</td>
              <td width="20%" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02928") %><%--태스크 종류--%></td>
          </tr>
          <tBody id="taskTable">
      <tBody>
        </table>
      </div>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
      <table width="700" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "03140") %><%--하위 태스크 편집 화면--%></td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:createTask(this);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:updateTask(this);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01936") %><%--수정--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
              <td width="5">&nbsp;</td>
              <td><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteTask(this);" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="700">
        <tr>
          <td width="175" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02920") %><%--태스크--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0">&nbsp;<INPUT id=i style="width:50%" name=cname value="<%=taskName%>"></td>
        </tr>
        <tr>
          <td width="175" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%><span class="red">*</span></td>
          <td colspan="3" class="tdwhiteL0">&nbsp;<INPUT id=i size=7 name=duration value="<%=durationStr==""? "1":durationStr%>" onkeyup ="SetNum(this)" style='IME-MODE: disabled'>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%></td>
        </tr>
        <tr>
          <td width="175" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%></td>
          <td width="175" class="tdwhiteL">
      &nbsp;
      <input type="radio" value="true" name="optionType" <%if("Y".equals(optiontype)) {%>checked<%}%>/>Y
      <input type="radio" value="false" name="optionType" <%if("N".equals(optiontype)) {%>checked<%}%>/>N
      </td>
          <td width="175" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00324") %><%--Milestone여부--%></td>
          <td width="175" class="tdwhiteL0">
      &nbsp;
      <input type="radio" value="true" name="mileStone" <%if("Y".equals(milestone)) {%>checked<%}%>/>Y
      <input type="radio" value="false" name="mileStone" <%if("N".equals(milestone)) {%>checked<%}%>/>N
      </td>
        </tr>
        <tr>
          <td width="175" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02928") %><%--태스크 종류--%></td>
          <td width="195" class="tdwhiteL">
        &nbsp;
      <select name="taskType" onchange="javascript:displayChange();" style="width:160">
                <option value="일반" <%if("일반".equals(tasktype)) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "02345") %><%--일반--%></option>
        <%
        if("mold".equals(projectType)) { %>
          <option value="Try" <%if("Try".equals(tasktype)) {%>selected<%}%>>Try</option>
          <%if (!(obj instanceof TemplateTask)) {  %>
                        <option value="디버깅" <%if("디버깅".equals(tasktype)) {%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01339") %><%--디버깅--%></option>
          <%} %>
        <%
        }
        if(!"mold".equals(projectType)) { %>
          <option value="DR" <%if("DR".equals(tasktype)) {%>selected<%}%>>DR</option>
        <%
        } %>
      </select>
      </td>
          <td width="175" class="tdblueM" id="drvalueTd1"><%if("DR".equals(tasktype)) {%><%=messageService.getString("e3ps.message.ket_message", "00156") %><%--DR 목표 값--%><%}%></td>
          <td width="175" class="tdwhiteL0" id="drvalueTd2">
      &nbsp;
      <%if("DR".equals(tasktype)) {%>
        <INPUT id=i size=7 name=drValue value="<%=drvalue%>" onkeyup ="SetNum(this)" style='IME-MODE: disabled;'>
      <%}%></td>
        </tr>
        <tr>
          <td width="175" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02925") %><%--태스크 설명--%></td>
          <td colspan="3" class="tdwhiteL0" style="height:100">
      &nbsp;<textarea name="desc" rows="4" onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)" id=input style="width:99%; height:96%"></textarea>
      <!--<textarea name="textfield" class="txt_field" id="textfield" style="width:100%; height:96%"></textarea>--></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="700" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>

</form>
</body>
<!-- %@include file="/jsp/common/AutoCompleteLayer.jsp"%-->
</html>
