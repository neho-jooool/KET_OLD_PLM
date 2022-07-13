<%@page contentType="text/html; charset=UTF-8"%>

<%@page import = "java.util.*"%>
<%@page import="
            wt.fc.*,
            wt.folder.*,
            wt.lifecycle.*,
            wt.org.*,
            wt.part.*,
            wt.project.Role,
            wt.query.*,
            wt.session.*,
            wt.team.*,
            wt.vc.*,
            wt.vc.wip.*,
            wt.workflow.engine.WfActivity,
            wt.workflow.engine.WfProcess,
            wt.workflow.work.WorkItem"%>
<%@page import="
            e3ps.common.content.*,
            e3ps.common.jdf.config.Config,
            e3ps.common.jdf.config.ConfigImpl,
            e3ps.common.jdf.log.Logger,
            e3ps.common.query.*,
            e3ps.common.util.*,
            e3ps.groupware.company.*,
            e3ps.project.*,
            e3ps.project.beans.*,
            e3ps.common.web.*,
            e3ps.project.historyprocess.HistoryHelper,
            e3ps.project.outputtype.OEMPlan,
            e3ps.project.outputtype.OEMProjectType,
            e3ps.sap.*,
            e3ps.wfm.entity.KETWfmApprovalMaster
            " %>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>

<%

	//********************** Department 가져오기************************/
	e3ps.groupware.company.Department department = null;
	String departmentName = "";
	String gateDrTabName = "";
	//로그인 사용자 정보 (OID)
	try {
	    WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal();
	    
	    QueryResult qr = PersistenceHelper.manager.navigate(wtuser, "people", e3ps.groupware.company.WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
	        e3ps.groupware.company.People people = (e3ps.groupware.company.People) qr.nextElement();
	        department = people.getDepartment();
	        departmentName = department.getName();
	    }   
	}catch(Exception e){
	    Kogger.error(e);
	}
	
	gateDrTabName = "평가관리";
	//********************** Department 가져오기************************/
	

  String oid = request.getParameter("oid");
  E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
  E3PSProjectData projectData = new E3PSProjectData(project);
  String popup = StringUtil.checkNull( request.getParameter("popup") );

  ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
  boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
  boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
  boolean isCS = CommonUtil.isMember("공정감사");
  //이슈 삭제
  String deleteIssue = request.getParameter("deleteIssue");
  if ( deleteIssue != null ) {
    ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
  }
%>

<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
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

-->
</style>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

  var targetTableId;
  var targetCmdStr;
  var targetLinkMsg;
  function onDeleteTableRow(tableid, cmdstr, objid) {
    if(!confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>')) {
      return;
    }

    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = cmdstr;

    showProcessing();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    param += "&linkOid=" + encodeURIComponent(objid);
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);

    if(targetCmdStr == 'memberDelete') {
      addMemberRefresh('roleMemberTable', 'searchRoleMember','roleMemberDelete');
    }
  }
  
  window.getTagText = function(xmlDoc){
      return xmlDoc.textContent || xmlDoc.text || '';
  }

  function onMessage(req) {
    var xmlDoc = req.responseXML;
    //var showElements = xmlDoc.selectNodes("//message");
    var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);

    if(msg == 'false' && xmlDoc.getElementsByTagName("l_result") != null && getTagText(xmlDoc.getElementsByTagName("l_result")[0]) != ""){
      alert(getTagText(xmlDoc.getElementsByTagName("l_result")[0]));
      hideProcessing();
      return;
    }

    if(msg == 'false') {
      alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
      hideProcessing();
      return;
    }

    if(targetTableId == "refDeptTable") {
      acceptDept(xmlDoc);
    }else if(targetTableId == "membertable") {
      //acceptMember(xmlDoc);
      location.reload();
    }else{
      location.reload();
      //acceptMember2(xmlDoc);
    }
  }
  
  
  var saveTargetCmdStr = "";
  
  function saveMember(attacheMembers){
          
	  if(typeof attacheMembers == "undefined" || attacheMembers == null) {
	      return;
	    }

	    showProcessing();

	    var param = "command=" + saveTargetCmdStr;
	    param += "&oid=<%=oid%>";
	    for(var i = 0; i < attacheMembers.length; i++) {
	      param += "&userOid=" + encodeURIComponent(attacheMembers[i][0]);
	    }

	    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
	    postCallServer(url, param, onMessage, true);
  }

   //사용자 가져오기 시작 ........................................................................................
  //............................................................................................................
  function addMember(tableid, cmdstr, linkmsg) {
	  
    saveTargetCmdStr = cmdstr;
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=saveMember";
    
    window.open(url,"addMember"+tableid,"top=100px, left=100px, height=710px, width=800px");
    
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

    if(targetTable) {
      var len = targetTable.rows.length;
      for(var i=len; i > 1; i--) {
        targetTable.deleteRow(i-1);
      }
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
        nameHtml = "<a href=\"JavaScript:viewTodo('"+lpeopleOid+"')\">" + lname + "</a>";
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

      if(targetTable) {
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
        newTd.className = "tdwhiteM";
        newTd.innerText = lemail;

        //삭제
        newTd = newTr.insertCell(newTr.cells.length);
        newTd.className = "tdwhiteM";
        newTd.innerHTML = deleteHtml;
      }
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
          nameHtml = "<a href=\"JavaScript:viewTodo('"+lpeopleOid+"')\">" + lname + "</a>&nbsp;<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"','"+targetLinkMsg+"','" + llinkOid + "');\"><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></a>";
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
          nameHtml2 = "<a href=\"JavaScript:viewTodo('"+lpeopleOid+"')\">" + lname2 + "</a>&nbsp;<a href=\"#\" onClick=\"javascript:onDeleteTableRow('"+targetTableId+"','"+targetLinkMsg+"','" + llinkOid2 + "');\"><img src=\"/plm/portal/images/icon_delete.gif\" width='13' height='12' border='0'></a>";
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
  
  function roleRefresh(rtnMsg){
	  showProcessing();
	  
	  location.reload();
	  <%-- var param = "command=" + targetCmdStr;
	  param += "&oid=<%=oid%>";

	  var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
	  postCallServer(url, param, onMessage, false);

	  addMemberRefresh('membertable', 'searchMember','memberDelete'); --%>
  }

  //Role Member 추가 시작
  function addRoleMember(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    <%-- var url = "/plm/jsp/project/PjtRoleMemberModifyPop.jsp?invokeMethod=roleRefresh&oid=<%=oid%>";
    window.open(url,'<%=oid%>','top=100px, left=100px, width=800px, height=810px') --%>
    
    var url = "/plm/jsp/project/PjtRoleMemberModifyPop.jsp?oid=<%=oid%>";
    openOtherName(url, "addRoleMember", "800", "810", "status=no,scrollbars=no,resizeable=no");

  }
  //Role Member 추가 끝

  function addMemberRefresh(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    showProcessing();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";
    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, false);
  }

  function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","400","status=no,scrollbars=no,resizable=no");
  }

  function addIssue() {
    var url = "/plm/jsp/project/projectIssueCreate.jsp?oid=<%=oid%>";
    openOtherName(url,"addIssue","750","650","status=no,scrollbars=yes,resizable=no");
  }

  function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","750","700","status=no,scrollbars=yes,resizable=no");
  }

  function modifyPM(){
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
      return;
    }

    document.forms[0].action = "/plm/jsp/project/projectModifyPM.jsp?oid=<%=oid%>&userOid=" + attacheMembers[0][0];  ;
    document.forms[0].command.value = "targetCmdStr";
    document.forms[0].method = "post";
    document.forms[0].submit();
  }


  function excelDown(){
    location.href = "/plm/jsp/project/CFTRoleExcelDown.jsp?oid=<%=oid%>";
  }

  function excelUp() {
    var url = "/plm/jsp/project/CFTRoleUp.jsp?oid=<%=oid%>";
    openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");
  }


  function deleteIssue(v) {
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
      document.forms[0].action = "/plm/jsp/project/ProjectView2.jsp?deleteIssue="+v+"&oid=<%=oid%>";
      document.forms[0].method = "post";
      document.forms[0].submit();
    }
  }

  function viewTodo(oid){
    var url = "/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser="+oid;
    openOtherName(url,"ViewTemplate","1100","680","status=1,scrollbars=yes,resizable=1");
  }
  
  function openCostHistory(oid){
	  var url = "/plm/ext/cost/viewCostHistoryPopup.jsp?oid="+oid;
      //openSameName(url,"1600","720","status=no,scrollbars=yes,resizable=yes");
      
      getOpenWindow2(url, oid, 'full', 10);
  }
//-->
</script>
</head>
<%@include file="/jsp/common/processing.html" %>
<body>
    <form>
        <input type='hidden' name='popup' value="<%=popup%>"> <input type='hidden' name='command' value="">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td valign="top">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02551")%><%--제품 프로젝트 상세정보--%></td>
                                    </tr>
                                </table></td>
                        </tr>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="5"></td>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "01120")%><%--기본정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table>
                                            </td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView2.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "02609")%><%--제품정보--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원--%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isCS) {
                                        %>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView4.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01640")%><%--비용--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView10.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                        href="ProjectExtView5.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">CFT요청</a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>&popup=<%=popup%>"
                                                        class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "01381")%><%--목표--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="/plm/ext/project/gate/viewProjectGateDRForm.do?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09500") %><%--평가관리--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="ProjectExtView7.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "03034")%><%--프로그램--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png""></td>
                                                </tr>
                                            </table></td>
<%--                                         <%if(CommonUtil.isAdmin() || CommonUtil.isMember("원가_임원")){%>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td><img src="/plm/portal/images/tab_4.png"></td>
                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
                                                    href="ProjectExtView8.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">원가</a></td>
                                                <td><img src="/plm/portal/images/tab_5.png""></td>
                                            </tr>
                                        </table></td>
                                        <%} %> --%>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
		                                    <tr>
		                                        <td><img src="/plm/portal/images/tab_4.png"></td>
		                                        <td background="/plm/portal/images/tab_6.png" class="btn_tab"><a
		                                            href="ProjectExtView9.jsp?oid=<%=oid%>&popup=<%=popup%>" class="btn_tab" onclick="showProcessing();">ATFT</a></td>
		                                        <td><img src="/plm/portal/images/tab_5.png""></td>
		                                    </tr>
		                                </table></td>
		                                <td>
	                                        <table border="0" cellspacing="0" cellpadding="0">
	                                            <tr>
	                                                <td><img src="/plm/portal/images/tab_4.png"></td>
	                                                <td background="/plm/portal/images/tab_6.png" class="btn_tab">
	                                                <a href="javascript:openCostHistory('<%=oid %>')" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "09518") %><%--개발원가--%></a></td>
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
                    </table>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10" height="10"><img src="../../portal/images/box_9.gif"></td>
                            <td height="10" background="../../portal/images/box_14.gif"></td>
                            <td width="10" height="10"><img src="../../portal/images/box_10.gif""></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <!--내용-->
                                <table width="100%" border="0" cellspacing="0" cellpadding="10">
                                    <tr>
                                        <td valign="top">
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00111")%><%--CFT 업무--%></td>
                                                    <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <%
                                                                WTUser user = (wt.org.WTUser) SessionHelper.manager.getPrincipal();
                                                                String id = user.getName();//일시적으로 구본경 대리에게 권한부여
                                                                
                                                                    if ("gubk".equals(id) || (auth.isLatest && !auth.isCompleted && (auth.isPM || isAdmin || isbizAdmin || auth.isPMO))) {
                                                                %>
                                                                <td><a href="#" onclick="javaScript:excelDown()"><img
                                                                        src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                                                                <td width="10">&nbsp;</td>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="#" onclick="javascript:excelUp();" class="btn_blue">RoleLoad</a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                                <td width="10">&nbsp;</td>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="#"
                                                                    onClick="javascript:addRoleMember('roleMemberTable', 'searchRoleMember','roleMemberDelete');"
                                                                    class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00445")%><%--Role 담당자 수정--%></a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                <%
                                                                    }
                                                                %>
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
                                                    <td class="tab_btm2"></td>
                                                </tr>
                                            </table> 
                                            <%
                                                 Vector vecTeamStd = null;
                                                 TeamTemplate tempTeam = null;
                                                 if ("자동차 사업부".equals(((ProductProject) project).getTeamType())) {
                                             		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
                                                 } else if ("KETS".equals(((ProductProject) project).getTeamType()) || "KETS_PMO".equals(((ProductProject) project).getTeamType())) {
                                             		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
                                                 } else {
                                             		tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
                                                 }
                                            
                                                 if (tempTeam != null) {
                                             		vecTeamStd = tempTeam.getRoles();
                                                 }
                                             %>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%" id="roleMemberTable">
                                                <tr>
                                                    <td width="130" class="tdblueM">Role</td>
                                                    <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                                    <td width="130" class="tdblueM">Role</td>
                                                    <td width="130" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                                    <td width="130" class="tdblueM">Role</td>
                                                    <td width="130" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%></td>
                                                </tr>
                                                <tr>
                                                    <%
                                                        if (vecTeamStd != null) {
                                                    		Collections.sort(vecTeamStd, new RoleComparator(true, true));
                                                    		Hashtable roleHash = new Hashtable();
                                                    		for (int i = vecTeamStd.size() - 1; i > -1; i--) {
                                                    		    Role role = (Role) vecTeamStd.get(i);
                                                    		    String roleName_en = role.toString();
                                                    		    QueryResult roleQr = ProjectUserHelper.manager.getProjectRoleMember(project, null, roleName_en);
                                                    		    if (roleQr.hasMoreElements()) {
                                                    			ProjectMemberLink link = (ProjectMemberLink) roleQr.nextElement();
                                                    			roleHash.put(roleName_en, link);
                                                    		    } else {
                                                    			vecTeamStd.remove(i);
                                                    		    }
                                                    		}

                                                    		Role role = null;
                                                    		String roleName_en = null;
                                                    		String roleName_ko = null;

                                                    		PeopleData data = null;
                                                    		String peopleOID = "";

                                                    		int roleIndex = 0;
                                                    		int colspan = 1;
                                                    		for (int i = vecTeamStd.size() - 1; i > -1; i--) {
                                                    		    role = (Role) vecTeamStd.get(i);
                                                    		    roleName_en = role.toString();
                                                    		    roleName_ko = role.getDisplay(messageService.getLocale());

                                                    		    if (roleIndex % 3 == 0) {
                                                    			out.println("<tr>");
                                                    		    }
                                                    %>
                                                    <td width="130" class="tdwhiteM"><%=roleName_ko%></td>
                                                    <td width="130" <%if (roleIndex % 3 == 2) {%> class="tdwhiteM0" <%} else {%>
                                                        class="tdwhiteM" <%}%> colspan="<%=colspan%>">&nbsp; 
                                                    <%
                                                        ProjectMemberLink link = (ProjectMemberLink) roleHash.get(roleName_en);
                                             		    data = new PeopleData(link.getMember());
                                             		    String memOid = CommonUtil.getOIDString(link.getMember());
                                                     %> 
                                                        <a href="JavaScript:viewTodo('<%=memOid%>')"><%=data.name%></a> <a href="#"
                                                        onClick="javascript:onDeleteTableRow('roleMemberTable', 'roleMemberDelete', '<%=link.getPersistInfo().getObjectIdentifier().getStringValue()%>');"><img
                                                            src="/plm/portal/images/icon_delete.gif" width='13' height='12' border='0'></a>
                                                    </td>
                                                    <%
                                                        if (i == 0) {
                                                    			if (roleIndex % 3 == 0) { //2명
                                                    %>
                                                    <td width="130" class="tdwhiteM">&nbsp;</td>
                                                    <td width="130" class="tdwhiteM">&nbsp;</td>
                                                    <td width="130" class="tdwhiteM">&nbsp;</td>
                                                    <td width="130" class="tdwhiteM0">&nbsp;</td>
                                                    <%
                                                        } else if (roleIndex % 3 == 1) { // 1명
                                                    %>
                                                    <td width="130" class="tdwhiteM">&nbsp;</td>
                                                    <td width="130" class="tdwhiteM0">&nbsp;</td>
                                                    <%
                                                        }
                                                    		    }

                                                    		    if (roleIndex % 3 == 2 || i == 0) {
                                                    			out.println("</tr>");
                                                    		    }
                                                    		    roleIndex++;
                                                    		}
                                                        }
                                                    %>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td class="space15"></td>
                                                </tr>
                                            </table>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="20"><img src="../../portal/images/icon_4.png"></td>
                                                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02757")%><%--참여자--%></td>
                                                    <td align="right">
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td>&nbsp;</td>
                                                                <%
                                                                    if (auth.isLatest && !auth.isCompleted && (auth.isPM || isAdmin || isbizAdmin || auth.isPMO)) {
                                                                %>
                                                                <td width="10">&nbsp;</td>
                                                                <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="#"
                                                                    onClick="javascript:addMember('membertable', 'addMember','memberDelete');"
                                                                    class="btn_blue">&nbsp;&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02760")%><!-- 참여자 추가 -->
                                                                </a></td>
                                                                <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                                                <%
                                                                    }
                                                                %>
                                                            </tr>
                                                        </table></td>
                                                </tr>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="680">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table>
                                            <table width="100%" cellpadding="0" cellspacing="0" class="table_border">
                                                <COL width="8%">
                                                <COL width="15%">
                                                <COL width="10%">
                                                <COL width="8%">
                                                <COL width="15%">
                                                <COL width="10%">
                                                <COL width="8%">
                                                <COL width="15%">
                                                <COL width="10%">
                                                <tr>
                                                    <td class="tdblueM">
                                                        <%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%>
                                                    </td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%-- 부서 --%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                                    <td class="tdblueM">
                                                        <%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%>
                                                    </td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%-- 부서 --%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                                    <td class="tdblueM">
                                                        <%=messageService.getString("e3ps.message.ket_message", "02276")%><%--이름--%>
                                                    </td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01538") %><%-- 부서 --%></td>
                                                    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                                                </tr>
                                                <%
                                                    PeopleData pData = new PeopleData(projectData.pjtPm);
                                                    String pOID = CommonUtil.getOIDString(projectData.pjtPm);

                                                    String work = "";
                                                    String workLink = "";
                                                    HashMap map = new HashMap();
                                                    map.put("command", "search");
                                                    map.put("searchPjtNo", project.getPjtNo()); // Project No
                                                    map.put("searchPjtState", "PROGRESS"); // Project 상태 - 진행중
                                                    map.put("searchState", "0"); // Task 진행중
                                                    map.put("planStartEndDate", DateUtil.getToDay("yyyy-MM-dd")); // 오늘 날짜
                                                    map.put("getUser", pOID); // 유저
                                                    map.put("sortKey", "planStartDate"); // 계획 시작일 정렬
                                                    map.put("dsc", "false"); // 오름차순

                                                    QueryResult qResult = SearchPagingProjectHelper.openPagingSession3(map, 0, 10);
                                                    while (qResult.hasMoreElements()) {
                                                		Object[] o = (Object[]) qResult.nextElement();
                                                		E3PSTask eTask = null;
                                                		eTask = (E3PSTask) o[0];
                                                		work = eTask.getTaskName();
                                                		workLink = CommonUtil.getOIDString(eTask);
                                                		break;
                                                    }
                                                    ProjectMemberLink link = null;
                                                    PeopleData memberdata = null;
                                                    String memverOID = "";

                                                    QueryResult results = ProjectUserHelper.manager.getOnlyAppendMember(project);
                                                    Object[] obj = null;
                                                    int roleIndex = 0;
                                                    int colspan = 1;
                                                    for (int i = results.size() - 1; i > -1; i--) {
                                                    //while (results.hasMoreElements()) {
                                                		//obj = (Object[]) results.nextElement();
                                                        obj = (Object[]) results.nextElement();
                                                		link = (ProjectMemberLink) obj[0];
                                                		memberdata = new PeopleData(link.getMember());
                                                		memverOID = CommonUtil.getOIDString(link.getMember());//(memberdata.people).getPersistInfo().getObjectIdentifier().getStringValue();
                                                		String userOid = memberdata.wtuserOID;
                                                		if (memberdata != null) {
                                                		    work = "";
                                                		    workLink = "";
                                                		    map.put("getUser", userOid);
                                                		    qResult = SearchPagingProjectHelper.openPagingSession3(map, 0, 10);
                                                		    while (qResult.hasMoreElements()) {
                                                			Object[] o = (Object[]) qResult.nextElement();
                                                			E3PSTask eTask = null;
                                                			eTask = (E3PSTask) o[0];
                                                			work = eTask.getTaskName();
                                                			workLink = CommonUtil.getOIDString(eTask);
                                                			break;
                                                		}
                                                            
                                            		    if (roleIndex % 3 == 0) {
                                            		        out.println("<tr>");
                                                        }    
                                                %>
                                                    <td class="tdwhiteM"><a href="JavaScript:viewTodo('<%=memverOID%>')"><%=memberdata.name%></a>
                                                    <a href="#" onClick="javascript:onDeleteTableRow('membertable', 'memberDelete', '<%=link.getPersistInfo().getObjectIdentifier().getStringValue()%>');"><img src="/plm/portal/images/icon_delete.gif"></a></td>
                                                    <td class="tdwhiteM">&nbsp;<%=memberdata.departmentName%></td>
                                                    <td class="tdwhiteM">&nbsp;<%=memberdata.duty%></td>
                                                    <%
                                                        if (i == 0) {
                                                                if (roleIndex % 3 == 0) { //2명
                                                    %>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <%
                                                                } else if (roleIndex % 3 == 1) { // 1명
                                                    %>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <td class="tdwhiteM">&nbsp;</td>
                                                    <%
                                                                }
                                                            }

                                                            if (roleIndex % 3 == 2 || i == 0) {
                                                            out.println("</tr>");
                                                            }
                                                            roleIndex++;
                                                        }
                                                    }
                                                    %>
                                            </table>
                                            <table border="0" cellspacing="0" cellpadding="0" width="680">
                                                <tr>
                                                    <td class="space5"></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                            </td>
                            <td width="10" background="/plm/portal/images/box_15.gif">&nbsp;</td>
                        <tr>
                            <td width="10" height="10"><img src="../../portal/images/box_11.gif"></td>
                            <td height="10" background="../../portal/images/box_16.gif"></td>
                            <td width="10" height="10"><img src="../../portal/images/box_12.gif"></td>
                        </tr>
                    </table></td>
            </tr>
        </table>
    </form>
</body>
</html>
