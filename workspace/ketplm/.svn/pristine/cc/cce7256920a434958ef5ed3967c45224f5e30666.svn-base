<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*" %>
<%@page import = "e3ps.common.util.*"%>
<%@page import = "e3ps.doc.*,e3ps.doc.beans.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>

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
    width:640px;
    height:400px;
    z-index:9;
    visibility:hidden;
}
-->
</style>
<script language='javascript'>
<!--
function onLayer(szDivID, iState) // 1 visible, 0 hidden
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
}

function onDelete(opid) {
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01903") %><%--산출물을 삭제하시겠습니까?--%>")) {
    return;
  }
  var param = "command=deleteTaskOutput&taskOid=<%=oid%>&oid=" + opid;
  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp?" + param;
  callServer(url, onMessage);
}


function onSave() {
  var doctypeid = selectNodeValue();

  var param = "command=subcheck&oid=" + doctypeid;
  var url = "/plm/jsp/project/template/DocTypeAjaxAction.jsp?" + param;
  callServer(url, onSaveAction);
}

function onSaveAction(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg == 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03143") %><%--하위문서분류를 선택하시기 바랍니다--%>');
    return;
  }

  form = document.forms[0];
  if(form.name.value == '') {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01434") %><%--문서제목을 입력하시기 바랍니다--%>');
    form.name.focus();
    return;
  }

  if(form.role.value == '') {
    alert("<%=messageService.getString("e3ps.message.ket_message", "00450") %><%--Role를 선택하시기 바랍니다--%>");
    form.role.focus();
    return;
  }

  form.docTypeOid.value = selectNodeValue();
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01894") %><%--산출물 정의를 저장하시겠습니까?--%>")){
    return;
  }

  onProgressBar();
  var param = "command=addTaskOutput&taskOid=<%=oid%>";
  param += "&docTypeOid=" + form.docTypeOid.value;
  param += "&name=" + form.name.value;
  param += "&description=" + form.description.value;
  param += "&role=" + form.role.value;
  param += getMemberParameters();
  param += getDeptParameters();
  var url = "/plm/jsp/project/template/ProjectTempAjaxAction.jsp";
  postCallServer(url, param, onMessage, false);
  //callServer(url, onMessage);
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

  var tasktable = document.getElementById("tablelist");
  var len = tasktable.rows.length;
  for(var i=len; i > 1; i--) {
    tasktable.deleteRow(i-1);
  }

  showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_location = showElements[0].getElementsByTagName("l_location");
  var l_role = showElements[0].getElementsByTagName("l_role");

  if(l_oid.length == 0) {
    newTr = tasktable.insertRow(1);
    newTd = newTr.insertCell(0);
    newTd.className = "tdwhiteM0";
    newTd.colSpan = "5";
    newTd.innerText = "<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>";
  }else{
    var trhtml = "";
    for(var i = 0; i < l_oid.length; i++) {
      loid = decodeURIComponent(l_oid[i].text);
      lname = decodeURIComponent(l_name[i].text);
      llocation = decodeURIComponent(l_location[i].text);
      lrole = decodeURIComponent(l_role[i].text);

      newTr = tasktable.insertRow(tasktable.rows.length);

      //No.
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = (i+1);

      //문서제목
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.title = lname;
      newTd.innerHTML = "<div style='width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><a href=\"#\" onClick=\"javascript:outputPage('" + loid + "');\">" + lname + "</a></nobr></div>";

      //문서분류
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.title = llocation;
      newTd.innerHTML = "<div style='width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>" + llocation + "</nobr></div>";

      //Role
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = lrole;

      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDelete('" + loid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
    }
  }

  onLayer('layer1',0);
}

function setTableList(tableArr) {
  var tasktable = document.getElementById("tablelist");
  var len = tasktable.rows.length;
  for(var i=len; i > 1; i--) {
    tasktable.deleteRow(i-1);
  }

  if(tableArr.length == 0) {
    newTr = tasktable.insertRow(1);
    newTd = newTr.insertCell(0);
    newTd.className = "tdwhiteM0";
    newTd.colSpan = "5";
    newTd.innerText = "<%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%>";
  }else{
    var trArr;
    for(var i = 0; i < tableArr.length; i++) {
      trArr = tableArr[i];
      loid = trArr[0];
      lname = trArr[1];
      llocation = trArr[2];
      lrole = trArr[3];

      newTr = tasktable.insertRow(tasktable.rows.length);

      //No.
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = (i+1);

      //문서제목
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.title = lname;
      newTd.innerHTML = "<div style='width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr><a href=\"#\" onClick=\"javascript:outputPage('" + loid + "');\">" + lname + "</a></nobr></div>";

      //문서분류
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.title = llocation;
      newTd.innerHTML = "<div style='width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;'><nobr>" + llocation + "</nobr></div>";

      //Role
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";
      newTd.innerText = lrole;

      //삭제
      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM0";
      newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDelete('" + loid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a>";
    }
  }
}

//부서 가져오기 시작 ........................................................................................
//............................................................................................................
function addDepartment() {
  var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
  attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
  if(typeof attacheDept == "undefined" || attacheDept == null) {
    return;
  }

  onProgressBar();
  var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
  var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
  callServer(url, acceptDept);
}

function acceptDept(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;
  if(msg != 'true') {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
    return;
  }

  showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_code = showElements[0].getElementsByTagName("l_code");

  var targetTable = document.getElementById("depttable");

  for(var i = 0; i < l_oid.length; i++) {
    loid = decodeURIComponent(l_oid[i].text);
    lname = decodeURIComponent(l_name[i].text);
    //lcode = decodeURIComponent(l_code[i].text);
    if(isDeptCheck(loid)) {
      continue;
    }
    tableRows = targetTable.rows.length;
    newTr = targetTable.insertRow(tableRows);

    //부서
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteL";
    newTd.innerText = lname;

    //삭제
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteDept('" + loid + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='deptOid' value='" + loid + "'>";
  }

}

function isDeptCheck(deptid) {
  form = document.forms[0];
  if(form.deptOid == null || form.deptOid == 'undefined') {
    return false;
  }

  var deptLen = form.deptOid.length;
  if(deptLen) {
    for(var i = 0; i < deptLen; i++) {
      if(form.deptOid[i].value == deptid) {
        return true;
      }
    }
  }else {
    if(form.deptOid.value == deptid) {
      return true;
    }
  }

  return false;
}

function onDeleteDept(deptid) {
  form = document.forms[0];

  targetTable = document.getElementById("depttable");

  var deptLen = form.deptOid.length;
  if(deptLen) {
    for(var i = 0; i < deptLen; i++) {
      if(form.deptOid[i].value == deptid) {
        targetTable.deleteRow(i+1);
        return;
      }
    }
  }else {
    if(form.deptOid.value == deptid) {
      targetTable.deleteRow(1);
      return;
    }
  }
}

function getDeptParameters() {
  form = document.forms[0];

  if(form.deptOid == null || form.deptOid == 'undefined') {
    return "";
  }

  var deptParam = "";
  var deptLen = form.deptOid.length;
  if(deptLen) {
    for(var i = 0; i < deptLen; i++) {
      deptParam += "&deptOid=" + form.deptOid[i].value;
    }
  }else {
    deptParam += "&deptOid=" + form.deptOid.value;
  }

  return deptParam;
}

//부서 가져오기 끝 ........................................................................................
//............................................................................................................

//사용자 가져오기 시작 ........................................................................................
//............................................................................................................
function addMember() {
  var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
  attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
    return;
  }
  acceptMember(attacheMembers);
}

function acceptMember(objArr) {
  if(objArr.length == 0) {
    return;
  }

  /*
    subArr[0] = chkobj[i].value;//wtuser oid
    subArr[1] = chkobj[i].poid;//people oid
    subArr[2] = chkobj[i].doid;//dept oid
    subArr[3] = chkobj[i].uid;//uid
    subArr[4] = chkobj[i].sname;//name
    subArr[5] = chkobj[i].dname;//dept name
    subArr[6] = chkobj[i].duty;//duty
    subArr[7] = chkobj[i].dutycode;//duty code
    subArr[8] = chkobj[i].email;//email
  */

  var nonUserArr = new Array();
  for(var i = 0; i < objArr.length; i++) {
    infoArr = objArr[i];
    if(isMemberCheck(infoArr[0])) {
      continue;
    }

    nonUserArr[nonUserArr.length] = infoArr;
  }

  if(nonUserArr.length == 0) {
    return;
  }

  var targetTable = document.getElementById("membertable");

  for(var i = 0; i < nonUserArr.length; i++) {
    tableRows = targetTable.rows.length;

    infoArr = nonUserArr[i];
    newTr = targetTable.insertRow(tableRows);

    //이름
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerText = infoArr[4];

    //직위
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerText = infoArr[6];

    //부서
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerText = infoArr[5];

    //삭제
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM0";
    newTd.innerHTML = "<a href=\"#\" onClick=\"javascript:onDeleteMember('" + infoArr[0] + "');\"><p><img src=\"/plm/portal/icon/del.gif\" width='13' height='12' border='0'></p></a><input type='hidden' name='userOid' value='" + infoArr[0] + "'>";
  }

}

function isMemberCheck(wtuserid) {
  form = document.forms[0];
  if(form.userOid == null || form.userOid == 'undefined') {
    return false;
  }

  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      if(form.userOid[i].value == wtuserid) {
        return true;
      }
    }
  }else {
    if(form.userOid.value == wtuserid) {
      return true;
    }
  }

  return false;
}

function onDeleteMember(wtuserid) {
  form = document.forms[0];

  targetTable = document.getElementById("membertable");

  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      if(form.userOid[i].value == wtuserid) {
        targetTable.deleteRow(i+1);
        return;
      }
    }
  }else {
    if(form.userOid.value == wtuserid) {
      targetTable.deleteRow(1);
      return;
    }
  }
}

function getMemberParameters() {
  form = document.forms[0];

  if(form.userOid == null || form.userOid == 'undefined') {
    return "";
  }

  var userParam = "";
  var memberLen = form.userOid.length;
  if(memberLen) {
    for(var i = 0; i < memberLen; i++) {
      userParam += "&userOid=" + form.userOid[i].value;
    }
  }else {
    userParam += "&userOid=" + form.userOid.value;
  }

  return userParam;
}
//사용자 가져오기 끝 ........................................................................................
//............................................................................................................

/*
 * 산출물 등록 관련 스크립스 시작 ...........................................................................
 */
var selectLevelIndex = 0;
var initIndex = 0;
var targetSelectTD = "selectTD";
function onChangeFolder(fObj) {
  setSelectTree(fObj);
}

function setSelectTree(fObj) {
  form = document.forms[0];

  selectLevelIndex = new Number(fObj.selectlevel);
  removeSelect(selectLevelIndex);

  if(fObj.value != '') {
    selectSearch(fObj.value);
  }
}

function selectSearch(svalue) {
  onProgressBar();

  var param = "command=select&oid=" + svalue;
  var url = "/plm/jsp/project/template/DocTypeAjaxAction.jsp?" + param;
  callServer(url, onSelectOption);
}

function removeSelect(selectlevel) {
  var fTD = document.all.item(targetSelectTD);
  var spans = fTD.all.tags("SPAN");

  initIndex = new Number(fTD.initIndex);
  len = spans.length;
  for(var i = (len-1); i > selectlevel; i--) {
    var rspan = fTD.all.tags("SPAN")[i];
    if(i > (initIndex-1)) {
      fTD.removeChild(rspan);
    }else{
      var cSelect = rspan.children(0);
      selectNodeInit(cSelect);
    }
  }
}

function selectNodeValue() {
  var fTD = document.all.item(targetSelectTD);
  var spans = fTD.all.tags("SPAN");
  len = spans.length;
  for(var i = len; i > 0; i--) {
    var rspan = spans[i-1];
    var cSelect = rspan.children(0);
    if(cSelect.value.length > 0) {
      return cSelect.value;
    }
  }

  return '';
}

function selectNodeInit(selObj) {
  var selLen = selObj.length;
  for(var i = selLen; i > 1; i--) {
    var selectOption = selObj.options[i-1];
    selObj.removeChild(selectOption);
  }
}

function onSelectOption(req) {
  var xmlDoc = req.responseXML;
  var showElements = xmlDoc.selectNodes("//message");
  var msg = showElements[0].getElementsByTagName("l_message")[0].text;

  showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_name = showElements[0].getElementsByTagName("l_name");

  var arr = new Array();
  for(var i = 0; i < l_oid.length; i++) {
    subArr = new Array();
    subArr[0] = decodeURIComponent(l_oid[i].text);
    subArr[1] = decodeURIComponent(l_name[i].text);
    arr[i] = subArr;
  }

  addSelectNode(arr);
}

function addSelectNode(vArr) {
  var fTD = document.all.item(targetSelectTD);
  initIndex = new Number(fTD.initIndex);

  if(selectLevelIndex >= (initIndex-1)) {
    if(vArr.length == 0){
      return;
    }
  }

  if(selectLevelIndex == (initIndex-1)) {
    var newSpan = document.createElement("SPAN");
    var htmlStr = "<select name='selFolder' selectlevel=" + ++selectLevelIndex + " class='fm_jmp' style='width:110' onChange='javascript:onChangeFolder(this);'><option value=''><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%><\/option>";
    for(var i = 0; i < vArr.length; i++) {
      htmlStr += "<option value=" + vArr[i][0] + ">" + vArr[i][1] + "<\/option>";
    }
    htmlStr += "<\/select>&nbsp;";
    newSpan.innerHTML = htmlStr;
    fTD.appendChild(newSpan);
  }else{
    var newSpan = fTD.all.tags("SPAN")[selectLevelIndex+1];
    var newSelect = newSpan.children(0);
    for(var i = 0; i < vArr.length; i++) {
      newOpt = document.createElement("OPTION");
      newOpt.text = vArr[i][1];
      newOpt.value = vArr[i][0];
      newSelect.add(newOpt);
    }
  }
}
/*
 * 산출물 등록 관련 스크립스 끝 ...........................................................................
 */

 /*
  * 산출물 정의 수정 시작 ........................
  */
  function outputPage(opid) {
  var url = "/plm/jsp/project/template/ProjectTempOutputRegister.jsp?taskOid=<%=oid%>&oid=" + opid;
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=640px; dialogHeight:490px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    return;
  }
  setTableList(attache);
}
/*
  * 산출물 정의 수정 끝 ........................
  */
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="ajaxProgress.html"%>
<form method="post">
<!-- hidden begin -->
<input type='hidden' name='docTypeOid' value=''>
<!-- hidden end -->
<table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
  <tr>
    <td valign="top" style="padding-left:0px;padding-top:10px">
      <div id='outputlist' style='border:0px;margin:0px;padding:0px;'>
      <!-------------------------------------- Output 목록 시작 //-------------------------------------->
      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01">
        <tr>
          <td>
            <table border=0 cellpadding=0 cellspacing=0 >
              <tr>
                <td><img src=/plm/portal/images/title2_left.gif></td>
                <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01721") %><%--산출물 목록--%></td>
                <td><img src=/plm/portal/images/title2_right.gif></td>
              </tr>
            </table>
          </td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class=fixLeft></td>
                <td ><input type=button class="btnTras" value="<%=messageService.getString("e3ps.message.ket_message", "01738") %><%--산출물 추가--%>" onClick="onLayer('layer1',1);"></td>
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

      <table border="0" cellspacing="0" cellpadding="0" class="tab_w01" id='tablelist'>
        <tr>
          <td class="tdblueM" width="50">No.</td>
          <td class="tdblueM" width="200"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%></td>
          <td class="tdblueM" width="250"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
          <td class="tdblueM" width="60">Role</td>
          <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
        </tr>
<%
    QueryResult result = ProjectOutputHelper.manager.getTaskOutput(task);
    if(result == null || result.size() == 0) {
%>
        <tr>
          <td class="tdwhiteM0" colspan='5'><%=messageService.getString("e3ps.message.ket_message", "00708") %><%--검색결과가 없습니다--%></td>
        </tr>

<%
    }
    else {
      ProjectOutput output = null;
      ProjectOutputData data = null;
      int output_idx = 0;
      Object objArr[] = null;
      while(result.hasMoreElements()) {
        objArr = (Object[])result.nextElement();
        output = (ProjectOutput)objArr[0];
        data = new ProjectOutputData(output);
%>
        <tr>
          <td class="tdwhiteM"><%=++output_idx%></td>
          <td class="tdwhiteL">
            <div style="width:200;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr><a href="#" onClick="javascript:outputPage('<%=data.oid%>');"><%=data.name%></a></nobr>
            </div>

          </td>
          <td class="tdwhiteL">
            <div style="width:250;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
              <nobr><%=data.locationStr%></nobr>
            </div>
          </td>
          <td class="tdwhiteM"><%=data.role_ko%></td>
          <td class="tdwhiteM0">
            <a href="#" onClick="javascript:onDelete('<%=data.oid%>');"><p>
              <img src="/plm/portal/icon/del.gif" width="13" height="12" border="0">
            </p></a>
          </td>
        </tr>
<%
      }
    }
%>
      </table>
      <!-------------------------------------- Output 목록 끝 //-------------------------------------->
      </div>
      <table border="0" cellpadding="0" cellspacing="0" class="tab_w01">
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<!-- ############################################################################################################################## -->
<!-- 산출물 정의 등록 layer 시작 -->
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
            <td class='titleP'><%=messageService.getString("e3ps.message.ket_message", "01732") %><%--산출물 정의--%></td>
            <td align="right">
                            <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>' onClick="javascript:onSave();" class="s_font">
              <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="javascript:onLayer('layer1',0);" class="s_font">
            </td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="tab_popup02">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="tab_popup02">
          <tr>
            <td class="tab_btm1"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" class="tab_popup02">
          <col width='20%'><col width='80%'>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01431") %><%--문서위치--%> <font color="red">*</font></td>
            <td id='selectTD' class="tdwhiteL0" initIndex=4>
              <span>
                <select name="docType" selectlevel=0 class="fm_jmp" style="width:110" onChange="javascript:onChangeFolder(this);">
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
  DocCodeType root = DocCodeTypeHelper.getRoot();
  ArrayList list = DocCodeTypeHelper.getChildCode(root);

  String docTypeName = "";
  String docTypeOid = "";
  DocCodeType docType = null;
  for(int i = 0; i < list.size(); i++) {
    docType = (DocCodeType)list.get(i);

    docTypeName = docType.getName();
    docTypeOid = docType.getPersistInfo().getObjectIdentifier().getStringValue();
%>
                  <option value="<%=docTypeOid%>"><%=docTypeName%></option>
<%
  }

%>
                </select>
              </span>
              <span>
                <select name="docType" selectlevel=1 class="fm_jmp" style="width:110" onChange="javascript:onChangeFolder(this);">
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                </select>
              </span>
              <span>
                <select name="docType" selectlevel=2 class="fm_jmp" style="width:110" onChange="javascript:onChangeFolder(this);">
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                </select>
              </span>
              <span>
                <select name="docType" selectlevel=3 class="fm_jmp" style="width:110" onChange="javascript:onChangeFolder(this);">
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                </select>
              </span>
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%> <font color="red">*</font></td>
            <td class="tdwhiteL0">
              <input name="name" class="txt_field" style="width:99%;" value="">
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
            <td class="tdwhiteL0">
              <textarea name="description" cols="75" rows="5" class="fm_area"onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"></textarea>
            </td>
          </tr>
          <tr>
            <td class="tdblueL">Role <font color="red">*</font></td>
            <td class="tdwhiteL0">
              <select name='role' class="fm_jmp" style="width:110">
                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
          <%  // 담당을 '사용자'에서 'Role'로 변경
              wt.team.TeamTemplate tempTeam = wt.team.TeamHelper.service.getTeamTemplate("Team_Project");
              if(tempTeam != null)
              {
                Vector vec = tempTeam.getRoles();

                for (int i = vec.size() - 1; i > -1; i--)
                {
                  wt.project.Role role = (wt.project.Role) vec.get(i);
                  out.print("<option value='"+role.toString()+"'>"+role.getDisplay(messageService.getLocale())+"</option>");
                }
              }
          %>
              </select>
            </td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="100%">
          <tr>
            <td class="space20"></td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class='titleP'><%=messageService.getString("e3ps.message.ket_message", "00988") %><%--권한 설정--%></td>
            <td align="right">
              <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01673") %><%--사용자--%>' onClick="javascript:addMember();" class="s_font2">
              <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%>' onClick="javascript:addDepartment();" class="s_font">
            </td>
          </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="tab_popup02">
        <tr>
          <td class="tab_btm2"></td>
        </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" class="tab_popup02">
          <tr>
            <td class="tab_btm1"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" class="tab_popup02">
          <col width='20%'><col width='80%'>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01673") %><%--사용자--%></td>
            <td class="tdwhiteL0">
              <div style="height:112px;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px;">
                <table border="0" cellspacing="0" cellpadding="0" width="100%" id="membertable">
                  <tr>
                                        <td class="tdblueM" width="150"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                                        <td class="tdblueM" width="100"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                    <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                  </tr>
                </table>
              </div>
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%--부서--%></td>
            <td class="tdwhiteL0">
              <div style="height:112px;overflow-x:hidden;overflow-y:auto;border:1px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px;">
                <table border="0" cellspacing="0" cellpadding="0" width="100%" id="depttable">
                  <tr>
                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                    <td class="tdblueM0" width="30"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                  </tr>
                </table>
              </div>
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
<!-- 산출물 정의 등록 layer 끝 -->
<!-- ############################################################################################################################## -->
</form>
</body>
</html>
