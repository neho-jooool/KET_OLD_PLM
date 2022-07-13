<%@page import="wt.vc.VersionControlHelper"%>
<%@page import="e3ps.dms.entity.KETStandardTemplate"%>
<%@page import="wt.enterprise.Master"%>
<%@page import="wt.enterprise.RevisionControlled"%>
<%@page import="e3ps.common.obj.ObjectUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,wt.org.*"%>
<%@page import="e3ps.groupware.company.*,e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*,wt.content.*,e3ps.common.content.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<%
  String oid = request.getParameter("oid");
  TemplateTask task = (TemplateTask) CommonUtil.getObject(oid);

  String cmd = request.getParameter("cmd");
  if(cmd != null){
    cmd = "";
  }

  if("linkModify".equals(cmd)){
    QueryResult dependList = TaskDependencyHelper.manager.getDependTaskList(task);
    if(dependList != null) {
      Vector v = new Vector();
      while ( dependList.hasMoreElements() ) {
        TaskDependencyLink link = (TaskDependencyLink)dependList.nextElement();
        String linkOid = CommonUtil.getOIDString(link);
        long loid = CommonUtil.getOIDLongValue(linkOid);
        String delayTime = request.getParameter(String.valueOf(loid));
        if(StringUtil.checkString(delayTime)) {
          link.setDelayDuration(Integer.parseInt(delayTime));
        } else {
          link.setDelayDuration(0);
        }
        v.add(link);
      }
      ProjectTaskHelper.manager.updateTaskFromDependencyLink(task, v);
    }
  }

  TemplateTaskData taskData = new TemplateTaskData(task);
  TemplateProjectData projectData = new TemplateProjectData(task.getProject());

  TemplateViewButtonAuth auth = new TemplateViewButtonAuth(task.getProject());
  TemplateProject template = (TemplateProject)task.getProject();
  boolean isAdmin = CommonUtil.isAdmin();
  boolean canManage = isAdmin;
  boolean isEdit = auth.isOutput;//Task 관련 정보 수정 권한 여부.    //PLM Admin
  boolean isPM = ProjectUserHelper.manager.isPM(taskData.project);
  boolean isCheckOut = template.isCheckOut();

  boolean isLastAddTask = ProjectTaskHelper.manager.isChild(task);

  String oemType = request.getParameter("oemType");

  String oem = request.getParameter("oem");
  if("oemSave".equals(oem)){
    if(oemType != null){
      OEMProjectType otype = (OEMProjectType)CommonUtil.getObject(oemType);
      task.setOemType(otype);
      task = (TemplateTask)PersistenceHelper.manager.modify(task);
    }else{
      task.setOemType(null);
      task = (TemplateTask)PersistenceHelper.manager.modify(task);
    }
  }

  //String deleteIssue = request.getParameter("deleteIssue");
  //if ( deleteIssue != null ) {
    //ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
  //}

  Vector vecTeamStd = null;

  TeamTemplate tempTeam = null;

  boolean isMold = false;

  if(template instanceof MoldTemplateProject){
    tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(),"Mold Project");
    isMold = true;
  }else if(template instanceof ProductTemplateProject){
    tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(),"Product Project");
  }else if(template instanceof ElectronTemplateProject){
    tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(),"Electron Project");
  }else{
    tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(),"Review Project");
  }

  if(tempTeam != null) {
    vecTeamStd = tempTeam.getRoles();
  }

  String taskType = "일반";
  if(taskData.tasktype != null && taskData.tasktype.length() > 0){
    taskType = taskData.tasktype;
  }
%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.outputtype.OEMProjectType"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="wt.query.SearchCondition"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="wt.project.Role"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.common.query.SearchUtil"%>

<%@page import="java.util.Collections"%><html>
<head>
<title></title>
<%@include file="/jsp/common/top_include.jsp"%>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<SCRIPT language=JavaScript src="/plm/portal/js/ajax.js"></SCRIPT>
<script language="JavaScript">
<!--

var ajax = new sack();

function viewPeople(oid) {
  var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
  openSameName(url,"550","400","status=no,scrollbars=no,resizable=no");
}

function updataTask() {
  var url="/plm/jsp/project/template/TemplateTaskUpdate.jsp?oid=<%=oid%>";
  openSameName(url,"510","480","status=no,scrollbars=no,resizable=no");
}


var outputPageView;
//산출물 관련 시작 .............................................................................
function outputPage(opid, isview) {
  outputPageView = isview;
  var modalHeight = '400px';
  var url = "/plm/jsp/project/template/TemplateProjectOutputCreatePage.jsp?fromPage=TaskView&taskOid=<%=oid%>&oid=" + opid;
  
  openSameName(url,"500","500","status=no,scrollbars=no,resizable=no");
}

window.getTagText = function(xmlDoc){
    return xmlDoc.textContent || xmlDoc.text || '';
}

function setOutputTable(req) {
  var xmlDoc = req.responseXML;
  
  var msg =getTagText(xmlDoc.getElementsByTagName("l_message")[0]);
  if(msg == 'false') {
    alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
    return;
  }
  var lisRegistry = false;
  
  var l_oid = xmlDoc.getElementsByTagName("l_oid");
  var l_name = xmlDoc.getElementsByTagName("l_name");
  var l_location = xmlDoc.getElementsByTagName("l_location");
  //var l_role = showElements[0].getElementsByTagName("l_role");
  var l_objType = xmlDoc.getElementsByTagName("l_objType");
  var l_isPrimary = xmlDoc.getElementsByTagName("l_isPrimary");
  var l_docName = xmlDoc.getElementsByTagName("l_docName");
  var l_docOid = xmlDoc.getElementsByTagName("l_docOid");
  var l_subjectType = xmlDoc.getElementsByTagName("l_subjectType");
  var targetTable = document.getElementById("outputTable");
  var len = targetTable.rows.length;
  for(var i=len; i > 1; i--) {
    targetTable.deleteRow(i-1);
  }

  for(var i = 0; i < l_oid.length; i++) {
    loid = getTagText(l_oid[i]);
    lname = getTagText(l_name[i]);
    llocation = getTagText(l_location[i]);
    //lrole = (l_role[i].text);
    objType = getTagText(l_objType[i]);
    isPrimary = getTagText(l_isPrimary[i]);
    docName = getTagText(l_docName[i]);
    docOid = getTagText(l_docOid[i]);
    subjectType = getTagText(l_subjectType[i]);
    tableRows = targetTable.rows.length;
    newTr = targetTable.insertRow(tableRows);

    //문서제목
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteL";
    newTd.title = lname;
    newTd.innerHTML = "<div style=\"border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr><a href=\"#\" onClick=\"javascript:outputPage('"+loid+"', '"+lisRegistry+"');\">"+lname+"</a></nobr></div>";

    //산출물 타입
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.title = objType;
    newTd.innerHTML =  "<div style=\"border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>"+objType+"</nobr></div>";

    //주첨부파일
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.title = isPrimary;
    newTd.innerHTML = "<div style=\"border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>"+isPrimary+"</nobr></div>";

    //문서분류
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteL";
    newTd.title = llocation;
    newTd.innerHTML = "<div style=\"border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>"+llocation+"</nobr></div>";

    //Role
    //newTd = newTr.insertCell(newTr.cells.length);
    //newTd.className = "tdwhiteM";
    //newTd.title = lrole;
    //newTd.innerHTML = lrole;

<% if(isEdit) { %>
    //삭제
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.innerHTML = "<a href=\"JavaScript:deleteOutput('"+loid+"')\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a>";
<% } %>

	//Template
	newTd = newTr.insertCell(newTr.cells.length);
	newTd.className = "tdwhiteM";
	newTd.title = docName;
	if(docOid != null && docOid != ""){
	newTd.innerHTML = "<a href=\"JavaScript:linkStandardForm('"+docOid+"')\"><nobr>"+ docName+"</nobr></a>";
	}else{
		newTd.innerHTML = "<div style=\"border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr></nobr></div>";	
	}
	//Gate대상
    newTd = newTr.insertCell(newTr.cells.length);
    newTd.className = "tdwhiteM";
    newTd.title = subjectType;
    if(subjectType == "null"){
    	subjectType = "-";
    }
    newTd.innerHTML = "<div style=\"border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>"+subjectType+"</nobr></div>";

  }
}

function registryOutput(v) {
  var url = "/plm/jsp/project/ProjectOutputRegistry.jsp?oid="+v;
  openOtherName(url,"window","850","650","status=no,scrollbars=no,resizable=no");
  //newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=820,height=600,resizable=no,top="+screenHeight+",left="+screenWidth);
  //newWin.resizeTo(800,600);
  //newWin.focus();
}

function inputOutputResult(opid) {
  var url = "/plm/jsp/project/ProjectOutputResultRegistry.jsp?oid=" + opid;
  //openOtherName(url,"window","850","650","status=no,scrollbars=yes,resizable=yes");

  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=yes; dialogWidth=656px; dialogHeight:400px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    attache = false;
  }

  onProgressBar();

  var param = "command=searchOutput";
  param += "&oid=<%=oid%>";
  var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
  postCallServer(url, param, setOutputTable, true);
}

function registryLinkOutput(oid) {
  var url = "/plm/jsp/project/ProjectOutputDocRegistryLinkForm.jsp?oid="+oid;
  newWin = window.open(url,"window", "scrollbars=yes,status=no,menubar=no,toolbar=no,location=no,directories=no,width=600,height=400,resizable=no,top="+screenHeight+",left="+screenWidth);
  newWin.resizeTo(700, 500);
  newWin.focus();
}

function inputLinkOutput(opid) {
  var url = "/plm/jsp/project/ProjectOutputResultLink.jsp?oid=" + opid;
  //openOtherName(url,"window","850","650","status=no,scrollbars=yes,resizable=yes");
  attache = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=640px; dialogHeight:585px; center:yes");
  if(typeof attache == "undefined" || attache == null) {
    attache = false;
  }

  onProgressBar();

  var param = "command=searchOutput";
  param += "&oid=<%=oid%>";
  var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
  postCallServer(url, param, setOutputTable, true);
}

function deleteOutput(opid) {
  if ( !confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>') ) {
    return;
  }

  var param = "command=deleteOutputTemplate";
  param += "&oid=<%=oid%>";
  param += "&outputOid=" + encodeURIComponent(opid);
  var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
  postCallServer(url, param, setOutputTable, true);
}

function linkStandardForm(docOid){
	 var url = "/plm/jsp/dms/ViewStandardDoc.jsp?oid="+docOid;
	 openOtherName(url,"window","850","650","status=no,scrollbars=no,resizable=no");
}
//산출물 관련 끝 ................................................

//Veiw 권한 부서 가져오기 시작 ........................................................................................
  function addDepartment(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?isModal=true&isOpen=true&invokeMethod=openerCall&mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=400px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
      return;
    }

    onProgressBar();

    var param = "command=seartTaskRefDef";
    param += "&oid=<%=oid%>";

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";

    postCallServer(url, param, setTaskRefDef, true);

    //onProgressBar();
      //var param = "command=searchOutputTemplate";
      //param += "&oid=<%=oid%>";
      //var url = "/plm/jsp/project/ProjectOutputAjaxAction.jsp";
      //postCallServer(url, param, setOutputTable, true);
  }

  function changeDeptRow(){

    var param = "command=changeDeptRow";



    param += "&oid=<%=oid%>";
    param += "&role=" + document.forms[0].deptRole.value;

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

    var deptRole = showElements[0].getElementsByTagName("deptRole");


    var targetTable = document.getElementById("refDeptTable");
    var len = targetTable.rows.length;


        if(l_oid.length > 0){

      document.forms[0].deptRole[0].selected = true;

      loid = (l_oid[0].text);
      lname = (l_name[0].text);

      tr = targetTable.rows[1];
//      tr.cells[1].innerHTML = "<div style=\"width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + lname + "</nobr></div>";
    }else{

      tr = targetTable.rows[1];
//      tr.cells[1].innerHTML = "";
      if(deptRole.length > 0){
          alert('<%=messageService.getString("e3ps.message.ket_message", "01496") %><%--변경 되었습니다--%>');
      }

    }

/*
    for(var i = 0; i < l_oid.length; i++) {
      loid = (l_oid[i].text);
      lname = (l_name[i].text);

      tableRows = targetTable.rows.length;

      targetTable.getRow(1);


      newTr = targetTable.insertRow(tableRows);

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.title = "deptRole";

      newTd.innerHTML = "<select name=\"deptRole\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option></select>";


      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteL";
      newTd.title = lname;




      newTd.innderHTML = "<div style=\"width:150;border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;\"><nobr>" + lname + "</nobr></div>";

      newTd = newTr.insertCell(newTr.cells.length);
      newTd.className = "tdwhiteM";

      newTd.innerHTML = "<a href=\"JavaScript:onDeleteDepartTableRow('refDeptTable','refTaskDeptDelete','"+loid+"')\"><p><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></p></a>"
    }*/
  }

  //function onMessage() {
  //  alert(".....end ....");
  //}

  function openerCall(oArr) {

    onProgressBar();

    var param = "command=" + targetCmdStr;

    param += "&oid=<%=oid%>";
    param += "&deptOid=" + encodeURIComponent(oArr[0][0]);

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";

    //alert(param);
    postCallServer(url, param, setTaskRefDef, true);
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

  function onDeleteDepartTableRow(tableid, cmdstr, objid) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = cmdstr;

    onProgressBar();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&linkOid=" + encodeURIComponent(objid);
    //alert(param);
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, setTaskRefDef, false);
  }

  function setOEMSave(){

    //if( document.forms[0].oemType.value ==''){
    //  alert("OEM 타입을 선택해 주십시오.");
    //  return;
    //}

    document.forms[0].action = "/plm/jsp/project/template/TemplateTaskView.jsp?oem=oemSave";
    document.forms[0].method = "post";
    //document.forms[0].cmd.value = "oemSave";
    document.forms[0].submit();
  }

-->
</script>
<script src="/plm/portal/js/script.js"></script>
<style type="text/css">
<!--
body {
  margin-left: 10px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 5px;

  overflow-x:hidden;
  overflow-y:auto;
  scrollbar-highlight-color:#f4f6fb;
  scrollbar-3dlight-color:#c7d0e6;
  scrollbar-face-color:#f4f6fb;
  scrollbar-shadow-color:#f4f6fb;
  scrollbar-darkshadow-color:#c7d0e6;
  scrollbar-track-color:#f4f6fb;
  scrollbar-arrow-color:#607ddb;
}
-->
</style>
</head>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<body class="popup-background02 popup-space">
<form>
<!-- Hidden Value -->
<input type="hidden" name="oid" value="<%=oid%>">
<input type="hidden" name="cmd">
<!-- //Hidden Value -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01">WBS</td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <%-- <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:updataTask();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02926") %>태스크 수정</a></td>
                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td colspan="8" class="tdblueM0"><B>[TASK]</B><%=taskData.name%>&nbsp;<B><%=messageService.getString("e3ps.message.ket_message", "01120") %>기본정보</B></td>
        </tr>
        <tr>
          <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00481") %>TASK 기간</td>
          <td colspan="1" class="tdwhiteL">&nbsp;<%=taskData.duration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %>{0}일</td>
          <!--2014.07.14 컬럼추가 및 이동  -->
          <td width="100" class="tdblueM">계획작업시간계획작업시간</td>
          <td class="tdwhiteL">&nbsp;<%=task.getPlanWorkTime()%></td>  
          <td width="100" class="tdblueM">CFT Role</td>
          <td width="695" colspan="5" class="tdwhiteL0">
	        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refDeptTable">
	          <tr>
	           <!--  <td class="tdwhiteL"> -->
	              <select name="deptRole" class="fm_jmp" style="width:200" onchange='javaScript:changeDeptRow()'>
	                <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %>]</option>
	                <option value="PM" <%if("PM".equals(task.getPersonRole())) {%>selected<%}%>><%if(isMold){ %>금형개발<%}else{ %>PM<%} %></option>
	                <%
	                String deptRole = task.getPersonRole();
	
	                if(vecTeamStd!=null){
	                  Collections.sort(vecTeamStd, new RoleComparator(true, true));
	                  for (int i = vecTeamStd.size() - 1; i > -1; i--) {
	                    Role role = (Role) vecTeamStd.get(i);
	                    String roleKey = role.toString();
	                    String selected = "";
	                    if(roleKey.equals(deptRole)){
	                      selected = "selected";
	                    }
	                    String roleName_ko = role.getDisplay(Locale.KOREA);
	                %>
	                     <option value="<%=roleKey%>" <%=selected%>><%=roleName_ko%></option>
	                <%  }
	                }%>
	              </select>
	            <!-- </td> -->
	          </tr>
	        </table> 
        </td>  
        </tr>
        <tr>
          <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03133") %>필수여부</td>
          <td width="130" class="tdwhiteL">&nbsp;<%=taskData.optiontype%></td>
          <td width="85" class="tdblueM">Milestone</td>
          <td width="130" class="tdwhiteL">&nbsp;<%=taskData.milestone%></td>

          <%
          if("DR".equals(taskType)){

          %>
          <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00502") %>Task종류</td>
          <td width="130" class="tdwhiteL0">&nbsp;<%=taskType%></td>
          <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00164") %>DR목표값</td>
          <td width="130" class="tdwhiteL0">&nbsp;<%=taskData.drvalue%></td>
          <%
          }else{
          %>
          <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00502") %>Task종류</td>
          <td width="130" class="tdwhiteL0" colspan="3">&nbsp;<%=taskType%></td>
          <%
          }
          %>
        </tr>
        <tr>
          <td width="85" class="tdblueM" valign="top"><%=messageService.getString("e3ps.message.ket_message", "00500") %>TASK설명</td>
          <td colspan="3" valign="top"  class="tdwhiteL"><pre><%=taskData.description==""?"&nbsp;":taskData.description.trim()%><pre></td>
          <td width="85" class="tdblueM">유형유형</td>
          <td width="130" class="tdwhiteL0" colspan="3">&nbsp;<%=StringUtil.checkNull(task.getTaskTypeCategory())%></td>
        </tr>
      </table> --%>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <!-- <tr>
          <td class="space15"></td>
        </tr> -->
      </table>
      <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03"><%=taskData.name%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01748") %><%--상세정보--%></td>
          <td align="right">&nbsp;</td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <%if(!isLastAddTask){ %>
        <tr>
          <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01825") %><%--선행 Task--%></td>
          <td width="695" class="tdwhiteM0">
      <% String addTask = request.getParameter("addTask");
        if(addTask == null){
          addTask = "";
        }

        String deleteTask = request.getParameter("deleteTask");

        if(deleteTask == null){
          deleteTask = "";
        }
      %>
      <jsp:include page="/jsp/project/TaskDependencyInfo_include.jsp" flush="false">
        <jsp:param name="oid" value="<%=oid%>" />
        <jsp:param name="addTask" value="<%=addTask %>" />
        <jsp:param name="deleteTask" value="<%=deleteTask%>" />
      </jsp:include>
          </td>
        </tr>
        <tr>
          <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03259") %><%--후행 Task--%></td>
          <td class="tdwhiteM0"><jsp:include page="/jsp/project/TaskDependencyInfo_include1.jsp" flush="false">
              <jsp:param name="oid" value="<%=oid%>" />
            </jsp:include>
      </td>
        </tr>
        <%} %>
        <tr>
          <td width="85" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01716") %><%--산출물--%></td>
          <td width="695" class="tdwhiteM0"><table border="0" cellspacing="0" cellpadding="0" width="675">
            <tr>
              <td class="space5"></td>
            </tr>
          </table>
            <table width="675" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:outputPage('', 'false');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="675">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="90%" cellpadding="0" cellspacing="0" class="table_border" id="outputTable">
              <tr>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01739") %><%--산출물 타입--%></td>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "03133") %><%--필수여부--%></td>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
                <td  class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
                <td  class="tdgrayM">Template<%--Template--%></td>
                <td  class="tdgrayM">Gate대상<%--Gate대상--%></td>
              </tr>
              <%
        ProjectOutput output = null;
        ProjectOutputData outputData = null;
        Object[] opObj = null;
        QueryResult outputQr = ProjectOutputHelper.manager.getTaskOutput(task);
        while (outputQr.hasMoreElements()) {
          opObj = (Object[]) outputQr.nextElement();
          output = (ProjectOutput) opObj[0];
          //outputUser = output.getOwner() == null ? null : (WTUser) output.getOwner().getPrincipal();
          outputData = new ProjectOutputData(output);
          /* ContentHolder holder = (ContentHolder) output;
          Vector secondaryFiles = ContentUtil.getSecondaryContents(holder);
          int size = secondaryFiles.size(); */
          
          
          KETStandardTemplate ketStandardTemplate = (KETStandardTemplate)CommonUtil.getObject(output.getOutputDocOid());
          KETStandardTemplate latestInfo = null;
          
          if(ketStandardTemplate != null){
          RevisionControlled latestVersion = ObjectUtil.getLatestVersion(ketStandardTemplate);
          
              latestInfo = (KETStandardTemplate) latestVersion;
          }else{
              latestInfo = null;
          }
      %>
        <tr>
        <td class="tdwhiteL" title="<%=outputData.name%>">
          <div style="border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr>
            <a href="#" onClick="javascript:outputPage('<%=outputData.oid%>','<%=outputData.isRegistry%>');"><%=outputData.name%></a>
            </nobr>
          </div>
        </td>
        <td class="tdwhiteM" title="<%=outputData.objType%>">
          <div style="border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr><%=outputData.objType%></nobr>
          </div>
        </td>
        <td class="tdwhiteM" title="<%=outputData.isPrimary%>">
          <div style="border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr><%=outputData.isPrimary%></nobr>
          </div>
        </td>
        <td class="tdwhiteL" title="<%=outputData.locationStr%>">
          <div style="border:0;padding:0;margin:0;text-overflow:ellipsis;overflow:hidden;">
            <nobr><%=outputData.locationStr%></nobr>
          </div>
        </td>
      <%
        if(isEdit) {
      %>
        <td  class="tdwhiteM">
          <a href="JavaScript:deleteOutput('<%=outputData.oid%>')"><p><img src="/plm/portal/images/icon_delete.gif" width="13" height="12" border="0"></p></a>
        </td>
      <%
        }
      %>
        <td  class="tdwhiteM">
           <nobr><a href="JavaScript:linkStandardForm('<%=latestInfo == null ? "" : latestInfo.toString()%>')"><%=latestInfo == null ? "" : latestInfo.getTitle()%></a></nobr> 
        </td>
        <td  class="tdwhiteM"><%=output.getSubjectType() != null ? output.getSubjectType() : "-"%></td>
      </tr>
    <%
      }
    %>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="675">
              <tr>
                <td class="space5"></td>
              </tr>
          </table></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
     <!--  <table width="100%" height=20" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
          <td class="font_03">Role</td>
          <td align="right">&nbsp;</td>
        </tr>
      </table> -->
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <%-- <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="85" class="tdblueM">CFT Role</td>
          <td width="695" colspan="5" class="tdwhiteL0">

      <div id="refDeptDiv" style="width:99%;overflow-x:hidden;overflow-y:auto;border:0px;border-style:solid;border-color:#5F9EA0;padding:0px;margin:1px 1px 5px;">
        <table border="0" cellspacing="0" cellpadding="0" width="100%" id="refDeptTable">
          <tr>
            <td class="tdwhiteL">
              <select name="deptRole" class="fm_jmp" style="width:200" onchange='javaScript:changeDeptRow()'>
                <option value="">[<%=messageService.getString("e3ps.message.ket_message", "01802") %>선택]</option>
                <option value="PM" <%if("PM".equals(task.getPersonRole())) {%>selected<%}%>><%if(isMold){ %>금형개발<%}else{ %>PM<%} %></option>
                <%
                String deptRole = task.getPersonRole();

                if(vecTeamStd!=null){
                  Collections.sort(vecTeamStd, new RoleComparator(true, true));
                  for (int i = vecTeamStd.size() - 1; i > -1; i--) {
                    Role role = (Role) vecTeamStd.get(i);
                    String roleKey = role.toString();
                    String selected = "";
                    if(roleKey.equals(deptRole)){
                      selected = "selected";
                    }
                    String roleName_ko = role.getDisplay(Locale.KOREA);
                %>
                     <option value="<%=roleKey%>" <%=selected%>><%=roleName_ko%></option>
                <%  }
                }%>
              </select>
            </td>

          </tr>
        </table>
      </div>
     </td>
        </tr>
      </table> --%>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                <td class="font_03"><%=projectData.tempName%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "01120") %><%--기본정보--%></td>
          <td align="right">
            &nbsp;
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
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">

      <tr>
        <td class="tdblueL" width="150"><%=messageService.getString("e3ps.message.ket_message", "00553") %><%--WBS 기간--%></td>
        <td class="tdwhiteL0" colspan="3">&nbsp;<%=projectData.tempDuration%> <%=messageService.getString("e3ps.message.ket_message", "00138") %><%--{0}일--%></td>
      </tr>
      <tr>
                <td class="tdblueL" width="150"><%=messageService.getString("e3ps.message.ket_message", "02859") %><%--최초등록일--%></td>
        <td class="tdwhiteL" width"="240">&nbsp;<%=DateUtil.getDateString(projectData.tempCreateDate, "D")%></td>
                <td class="tdblueL" width="150"><%=messageService.getString("e3ps.message.ket_message", "02852") %><%--최종수정일--%></td>
        <td class="tdwhiteL0" width="240">&nbsp;<%=DateUtil.getDateString(projectData.tempModifyDate, "D")%></td>
      </tr>
      <tr>
        <td class="tdblueL" width="150" valign="top"><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
        <td class="tdwhiteL0" colspan="3" valign="top"><pre><%=projectData.tempDesc==""?"&nbsp;":projectData.tempDesc%><pre></td>

      </tr>
    </table>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space15"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
<script language="JavaScript">
function viewTask(oid){
    var url = '/plm/jsp/project/template/TemplateTaskView.jsp?oid='+oid;
                
    var defaultWidth = 800;
    var defaultHeight = 600;
    var opts = ',scrollbars=0,resizable=1';
    
    open(url, '', 'width=800,height=600');
    
}


</script>
</html>

