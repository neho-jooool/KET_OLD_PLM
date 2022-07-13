<%@page import="e3ps.project.MoldItemInfo"%>
<%@page import="e3ps.project.MoldProject"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="wt.project.Role"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="java.util.Locale"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.beans.ProjectUserHelper"%>
<%@page import="e3ps.project.ProjectMemberLink"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectHelper"%>
<%@page import="e3ps.project.beans.E3PSProjectData"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.beans.ProjectIssueHelper"%>
<%@page import="e3ps.project.ProjectIssue"%>
<%@page import="e3ps.project.beans.ProjectIssueData"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="e3ps.project.beans.ProjectIssueAnswerData"%>
<%@page import="e3ps.common.content.ContentInfo"%>
<%@page import="java.util.Collections"%>
<%@page import="e3ps.project.beans.RoleComparator"%>
<%@page import="e3ps.project.beans.ProjectViewButtonAuth"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.beans.IssueAnswerComparator"%>
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/top_include.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<%
    String oid = request.getParameter("oid");

    String popup = "";
    if(request.getParameter("popup") != null && request.getParameter("popup").length() > 0){
        popup = request.getParameter("popup");
    }

    Kogger.debug("PPPPP ==== " + popup);

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);

    E3PSProjectData projectData = new E3PSProjectData(project);
    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                        //PLM Admin
    boolean isCS = CommonUtil.isMember("공정감사");
    
    //이슈 삭제
    String deleteIssue = request.getParameter("deleteIssue");
    if ( deleteIssue != null ) {
      ProjectIssueHelper.manager.deleteProjectIssue(deleteIssue);
    }
    
    boolean isPurchase = false;
    String tab1Title = messageService.getString("e3ps.message.ket_message", "02536");
    String titleInfo = messageService.getString("e3ps.message.ket_message", "01027");
    
    MoldProject moldProject = (MoldProject)CommonUtil.getObject(oid);
    MoldItemInfo moldInfo = moldProject.getMoldInfo();
    
    if(moldInfo != null){
        if("구매품".equals(moldInfo.getItemType())){
           isPurchase = true;
           tab1Title = "기본정보";
           titleInfo = "구매품 프로젝트 정보";
        }
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->



<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
-->
</style>
<%@include file="/jsp/common/processing.html" %>
<script src="/plm/portal/js/script.js"></script>
<script src="/plm/portal/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function goView(no){
    if(no == 1){
        //showProcessing();
        location.href = "/plm/jsp/project/MoldProjectView.jsp?oid=<%=oid%>&popup=<%=popup%>";
    }else{
        //showProcessing();
        location.href = "/plm/jsp/project/MoldProjectView_" + no + ".jsp?oid=<%=oid%>&popup=<%=popup%>";
    }
}

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
        addMemberRefresh('roleMemberTable', 'searchRoleMember','roleMemberDelete');
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
        acceptMember(xmlDoc);
    }else{
        location.reload();
        //acceptMember2(xmlDoc);
    }
}

function modifyPM(){


    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if(typeof attacheMembers == "undefined" || attacheMembers == null) {
        return;
    }



    document.forms[0].action = "/plm/jsp/project/projectModifyPM.jsp?userOid=" + attacheMembers[0][0];
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

function viewPeople(oid) {
    var url = "/plm/jsp/groupware/company/selectPeopleView.jsp?viewtype=open&oid="+oid;
    openSameName(url,"550","450","status=no,scrollbars=auto,resizable=no");
}

//이슈관리
function addIssue() {
    var url = "/plm/jsp/project/projectIssueCreate.jsp?oid=<%=oid%>";
    openOtherName(url,"addIssue","750","650","status=no,scrollbars=yes,resizable=no");
}

function viewIssue(v) {
    var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
    openOtherName(url,"","750","700","status=no,scrollbars=yes,resizable=no");
}

function excelDown(){
    location.href = "/plm/jsp/project/CFTRoleExcelDown.jsp?oid=<%=oid%>";
}

function deleteIssue(v) {
    if ( confirm("<%=messageService.getString("e3ps.message.ket_message", "03323") %><%--이슈를 삭제 합니다.\n삭제하시겠습니까?--%>") ) {
      document.forms[0].action = "/plm/jsp/project/MoldProjectView_5.jsp?deleteIssue="+v+"&oid=<%=oid%>";
      document.forms[0].method = "post";
      document.forms[0].submit();
    }
}

function excelUp() {
    var url = "/plm/jsp/project/CFTRoleUp.jsp?oid=<%=oid%>";
    openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");
}

function viewTodo(oid){
    var url = "/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser="+oid;
    openOtherName(url,"ViewTemplate","850","680","status=1,scrollbars=yes,resizable=1");
}

var loadFlag = true;
//Jquery
$(document).ready(function(){
    Grids.OnRenderFinish = function(){
        if(loadFlag){
            loadFlag = false;
            loadEjsGrid();
        }
        
    }
});

function loadEjsGrid(){
    
    try{
        var idx = 0;
        var D = Grids[idx].Data;
        var formName = "frm";    //ProjectSearch 

        //D.Layout.Param.Pagingsize = $("input[name='perPage']").val();

        D.Data.Url = "/plm/servlet/e3ps/IssueServlet"; 
        D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
        D.Data.Param.command = "searchDataByProjectNo";
        
        D.Page.Url = "/plm/servlet/e3ps/IssueServlet";
        D.Page.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
        D.Page.Param.command = "searchDataByProjectNoPage";
        
        Grids[idx].Reload(D);
     }catch(e){
         alert(e.message);
     }
}
//-->
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
    <form name="frm" method="post">
    <input type="hidden" name="oid" value="<%=oid%>"></input> 
    <input type="hidden" name="popup" value="<%=popup%>"></input>
        <table style="width: 100%; height: 100%;">
            <tr>
                <td valign="top">
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <table style="width: 100%; height: 28px;">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=titleInfo%><%--금형 프로젝트 상세정보--%></td>
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
                            <td>
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="5"></td>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="javascript:goView('1');" class="btn_tab"><%=tab1Title%><%--제품--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            if (!isPurchase) {
                                        %>    
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="javascript:goView('2');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <%} %>
                                        <%
                                            if (!isCS && !isPurchase) {
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a
                                                        href="javascript:goView('3');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02354")%><%--일정/비용--%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <%
                                            }
                                        %>
                                        <td><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_4.png"></td>
                                                    <td background="/plm/portal/images/tab_6.png" class="btn_tab" onclick="showProcessing();"><a href="#"
                                                        onclick="javascript:goView('4');" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "02327")%><%--인원 --%></a></td>
                                                    <td><img src="/plm/portal/images/tab_5.png"></td>
                                                </tr>
                                            </table></td>
                                        <td align="left"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><img src="/plm/portal/images/tab_1.png"></td>
                                                    <td background="/plm/portal/images/tab_3.png" class="btn_tab"><%=messageService.getString("e3ps.message.ket_message", "00264")%><%--Issue --%></td>
                                                    <td><img src="/plm/portal/images/tab_2.png"></td>
                                                </tr>
                                            </table></td>
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
                            <td width="10" height="10"><img src="/plm/portal/images/box_9.gif"></td>
                            <td height="10" background="/plm/portal/images/box_14.gif"></td>
                            <td width="10" height="10"><img src="/plm/portal/images/box_10.gif"></td>
                        </tr>
                        <tr>
                            <td width="10" background="/plm/portal/images/box_13.gif">&nbsp;</td>
                            <td valign="top">
                                <!--내용-->
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00267")%><%--Issue 관리--%></td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                        href="javascript:addIssue();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02861")%><%--추가--%></a></td>
                                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="780">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>  <!-- EJS TreeGrid Start -->
                                <div class="content-main">
                                    <div class="container-fluid">
                                        <div class="row-fluid">
                                            <div style="WIDTH: 100%; HEIGHT: 500px">
                                                <bdo Debug="1" AlertError="0" 
                                                     Layout_Url="/plm/jsp/project/ProjectViewIssueGridLayout.jsp"
                                                     Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
                                                     Data_Method="POST"
                                                     Data_Param_Result="<%=tgData %>"
                                                     Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_StandardDoc_List"
                                                     
                                                     Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp" 
                                                     Page_Format="Internal" 
                                                     Page_Data="TGData" 
                                                     Page_Method="POST" 
                                                     >
                                                </bdo>
                                            </div>
                                        </div>
                                    </div>
                                </div> <!-- EJS TreeGrid Start -->
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