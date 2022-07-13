<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.*"%>
<%@page import = "wt.fc.*"%>
<%@page import = "wt.org.*"%>
<%@page import = "e3ps.access.*,e3ps.access.service.*"%>
<%@page import = "e3ps.common.util.*"%>
<%@page import = "e3ps.groupware.company.*"%>
<%@page import = "e3ps.doc.*,e3ps.doc.beans.*"%>
<%@page import = "e3ps.project.*,e3ps.project.beans.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%
  ProjectOutput output = null;
  TemplateTask task = null;
  TemplateProject project = null;

  String oid = request.getParameter("oid");
  String taskOid = request.getParameter("taskOid");
  if(oid == null) {
    oid = "";
  }

  if(taskOid == null) {
    taskOid = "";
  }

  ReferenceFactory rf = new ReferenceFactory();
  if(oid.length() > 0) {
    output = (ProjectOutput)rf.getReference(oid).getObject();
    task = output.getTask();
    project = output.getProject();
  }

  if(output == null && taskOid.length() > 0) {
    task = (TemplateTask)rf.getReference(taskOid).getObject();
    project = task.getProject();
  }


  String name = "";
  String description = "";
  String role = "";
  String location = "";
  if(output != null) {
    ProjectOutputData data = new ProjectOutputData(output);
    name = data.name;
    description = data.description;
    role = data.role_en;
    location = data.location;
  }

  DocCodeType docType = null;
  if(location.length() == 0) {
    docType = DocCodeTypeHelper.getRoot();
  }
  else {
    docType = DocCodeTypeHelper.getDocCodeTypeToPath(location);
  }

  ArrayList fullPath = DocCodeTypeHelper.getDocTypeParentPath(docType);
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
-->
</style>
<script language='javascript'>
<!--
/*
 * 문서 분류 관련 스크립스 시작 ...........................................................................
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
 * 문서 분류 관련 스크립스 끝 ...........................................................................
 */


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


//SAVE ACTION BEGIN @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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
    alert('<%=messageService.getString("e3ps.message.ket_message", "00450") %><%--Role를 선택하시기 바랍니다--%>');
    form.role.focus();
    return;
  }

  form.docTypeOid.value = selectNodeValue();
  if(!confirm("<%=messageService.getString("e3ps.message.ket_message", "01894") %><%--산출물 정의를 저장하시겠습니까?--%>")){
    return;
  }

  onProgressBar();
  var param = "command=addTaskOutput&taskOid=<%=taskOid%>&oid=<%=oid%>";
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

  var tableArr = new Array();
  var trArr = new Array();

  showElements = xmlDoc.selectNodes("//data_info");
  var l_oid = showElements[0].getElementsByTagName("l_oid");
  var l_name = showElements[0].getElementsByTagName("l_name");
  var l_location = showElements[0].getElementsByTagName("l_location");
  var l_role = showElements[0].getElementsByTagName("l_role");

  if(l_oid.length > 0) {
    for(var i = 0; i < l_oid.length; i++) {
      trArr = new Array();
      trArr[0] = decodeURIComponent(l_oid[i].text);
      trArr[1] = decodeURIComponent(l_name[i].text);
      trArr[2] = decodeURIComponent(l_location[i].text);
      trArr[3] = decodeURIComponent(l_role[i].text);
      tableArr[i] = trArr;
    }
  }

  window.returnValue = tableArr;
  window.close();


}
//SAVE ACTION END @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<%@include file="ajaxProgress.html"%>
<form method="post">
<!-- hidden begin -->
<input type='hidden' name='docTypeOid' value=''>
<input type='hidden' name='taskOid' value='<%=taskOid%>'>
<input type='hidden' name='oid' value='<%=oid%>'><!-- ProjectOutput OID -->
<!-- hidden end -->
<!-- 산출물 정의 등록 layer 시작 -->
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
              <input type='button' value='<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>' onClick="javascript:self.close();" class="s_font">
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
<%
  int defaultLength = 4;
  int pathLength = defaultLength;
  if(fullPath.size() > pathLength) {
    pathLength = fullPath.size();
  }

  if(pathLength == defaultLength) {
    ++pathLength;
  }


  ArrayList childLevels = null;
  DocCodeType childType = null;
  DocCodeType parentType = null;

  String docTypeName = "";
  String docTypeOid = "";
  String currentOid = "";
  String selected = "";

  for(int i = 0; i < pathLength; i++) {
    parentType = null;
    if(i < fullPath.size()) {
      parentType = (DocCodeType)fullPath.get(i);
    }

    currentOid = "";
    childLevels = null;
    if(parentType != null) {
      if(i < (fullPath.size()-1)) {
        currentOid = ((DocCodeType)fullPath.get(i+1)).getPersistInfo().getObjectIdentifier().getStringValue();
      }
      childLevels = DocCodeTypeHelper.getChildCode(parentType);
    }

    if(childLevels == null) {
      childLevels = new ArrayList();
    }

    if(i == (pathLength-1) && childLevels.size() == 0) {
      continue;
    }
%>
              <span>
                <select name="docType" selectlevel=<%=i%> class="fm_jmp" style="width:110" onChange="javascript:onChangeFolder(this);">
                  <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%
    if(childLevels.size() > 0) {
      for(int j = 0; j < childLevels.size(); j++) {
        childType = (DocCodeType)childLevels.get(j);
        docTypeName = childType.getName();
        docTypeOid = childType.getPersistInfo().getObjectIdentifier().getStringValue();
        selected = "";
        if(currentOid.equals(docTypeOid)) {
          selected = "selected";
        }
%>
                  <option value="<%=docTypeOid%>" <%=selected%>><%=docTypeName%></option>
<%
      }
    }
%>
                </select>
              </span>
<%
  }

%>
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01433") %><%--문서제목--%> <font color="red">*</font></td>
            <td class="tdwhiteL0">
              <input name="name" class="txt_field" style="width:99%;" value="<%=name%>">
            </td>
          </tr>
          <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
            <td class="tdwhiteL0">
              <textarea name="description" cols="75" rows="5" class="fm_area"onKeyUp="common_CheckStrLength(this, 666)" onChange="common_CheckStrLength(this, 666)"><%=description%></textarea>
            </td>
          </tr>
          <tr>
            <td class="tdblueL">Role <font color="red">*</font></td>
            <td class="tdwhiteL0">
              <select name='role' class="fm_jmp" style="width:110">
                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
<%  // 담당을 '사용자'에서 'Role'로 변경
    wt.team.TeamTemplate tempTeam = wt.team.TeamHelper.service.getTeamTemplate("Team_Project");
    if(tempTeam != null) {
      Vector vec = tempTeam.getRoles();
      selected = "";
      for (int i = vec.size() - 1; i > -1; i--)
      {
        wt.project.Role opRole = (wt.project.Role) vec.get(i);

        selected = "";
        if(role.equals(opRole.toString())) {
          selected = "selected";
        }
%>
                <option value='<%=opRole.toString()%>' <%=selected%>><%=opRole.getDisplay(messageService.getLocale())%></option>
<%
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
<%
  if(output != null) {
    WTUser wtuser = null;
    PeopleData userData = null;
    QueryResult userAuths = AccessControlHelper.getAccessUsers(output);

    Object userAuthObj[] = null;
    while(userAuths.hasMoreElements()) {
      userAuthObj = (Object[])userAuths.nextElement();
      wtuser = (WTUser)userAuthObj[0];
      userData = new PeopleData(wtuser);
%>
                  <tr>
                    <td class="tdwhiteM"><%=userData.name%></td>
                    <td class="tdwhiteM"><%=userData.duty%></td>
                    <td class="tdwhiteM"><%=userData.departmentName%></td>
                    <td class="tdwhiteM0"><a href="#" onClick="javascript:onDeleteMember('<%=userData.wtuserOID%>');"><p><img src="/plm/portal/icon/del.gif" width='13' height='12' border='0'></p></a><input type='hidden' name='userOid' value='<%=userData.wtuserOID%>'></td>
                  </tr>


<%
    }
  }

%>
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
<%
  if(output != null) {
    Department department = null;
    String deptName = "";
    String deptOid = "";
    QueryResult deptAuths = AccessControlHelper.getAccessDepts(output);
    Object deptAuthObj[] = null;
    while(deptAuths.hasMoreElements()) {
      deptAuthObj = (Object[])deptAuths.nextElement();
      department = (Department)deptAuthObj[0];
      deptName = department.getName();
      deptOid = department.getPersistInfo().getObjectIdentifier().getStringValue();
%>
                  <tr>
                    <td class="tdwhiteL"><%=deptName%></td>
                    <td class="tdwhiteM0"><a href="#" onClick="javascript:onDeleteDept('<%=deptOid%>');"><p><img src="/plm/portal/icon/del.gif" width='13' height='12' border='0'></p></a><input type='hidden' name='deptOid' value='<%=deptOid%>'></td>
                  </tr>

<%
    }
  }
%>
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
<!-- 산출물 정의 등록 layer 끝 -->
<!-- ############################################################################################################################## -->
</form>
</body>
</html>
