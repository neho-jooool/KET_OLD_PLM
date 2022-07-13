<%@page import="e3ps.project.WorkProject"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Vector,
                java.util.Hashtable,
                java.util.HashMap,
                java.util.Locale,
                java.util.Collections,
                wt.fc.QueryResult,
                wt.team.TeamTemplate,
                wt.team.TeamHelper,
        wt.project.Role,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.DateUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.WCUtil,
                e3ps.groupware.company.PeopleData,
                e3ps.project.E3PSProject,
                e3ps.project.ReviewProject,
                e3ps.project.ProductProject,
                e3ps.project.MoldProject,
                e3ps.project.E3PSTask,
                e3ps.project.ProjectMemberLink,
                e3ps.project.beans.E3PSProjectData,
                e3ps.project.beans.ProjectViewButtonAuth,
                e3ps.project.beans.ProjectUserHelper,
                e3ps.project.beans.RoleComparator,
                e3ps.project.beans.SearchPagingProjectHelper"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = request.getParameter("oid");
    String pjtType = "";
    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    E3PSProjectData projectData = new E3PSProjectData(project);

    ProjectViewButtonAuth auth = new ProjectViewButtonAuth(project);
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isAdmin = CommonUtil.isAdmin() || isbizAdmin;                   // PLM Admin
    boolean isCS = CommonUtil.isMember("공정감사");

    // Project Type
    if ( project instanceof ReviewProject ) {
        pjtType = "REVIEW";
    }
    else if ( project instanceof ProductProject ) {
        pjtType = "PRODUCT";
    }
    else if ( project instanceof MoldProject ) {
        pjtType = "MOLD";
    }
    else if ( project instanceof WorkProject ) {
        pjtType = "WORK";
    }
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><%=messageService.getString("e3ps.message.ket_message", "00403") %><%--Project 인원--%></title>

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<base target="_self">
<link rel="stylesheet" href="/plm/portal/css/e3ps.css">
<script language="javascript" src="/plm/portal/js/common.js"></SCRIPT>
<script language="javascript" src="/plm/portal/js/org.js"></SCRIPT>

<script language="javascript">
<!--
function excelDown() {
    location.href = "/plm/jsp/project/CFTRoleExcelDown.jsp?oid=<%=oid%>";
}

function excelUp() {
    var url = "/plm/jsp/project/CFTRoleUp.jsp?oid=<%=oid%>";
    openOtherName(url,"popup","500","230","status=no,scrollbars=auto,resizable=no");
}

// Role Member 추가
function addRoleMember(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;

    var url = "/plm/jsp/project/PjtRoleMemberModifyPop.jsp?oid=<%=oid%>";
    openOtherName(url, "addRoleMember", "800", "810", "status=no,scrollbars=no,resizeable=no");
}

function reload(){
	opener.gridReload();
}

function addMember(tableid, cmdstr, linkmsg) {
    targetTableId = tableid;
    targetCmdStr = cmdstr;
    targetLinkMsg = linkmsg;
    
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=saveMember";
    
    window.open(url,"addMember"+tableid,"top=100px, left=100px, height=710px, width=800px");
}

function saveMember(attacheMembers){
	if ( typeof attacheMembers == "undefined" || attacheMembers == null ) {
        return;
    }

    showProcessing();

    var param = "command=" + targetCmdStr;
    param += "&oid=<%=oid%>";

    for ( var i = 0; i < attacheMembers.length; i++ ) {
        param += "&userOid=" + encodeURIComponent(attacheMembers[i][0]);
    }

    var url = "/plm/jsp/project/ProjectAjaxAction.jsp";
    postCallServer(url, param, onMessage, true);
}

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

function viewTodo(oid) {
    var url = "/plm/jsp/project/ListMyProjectPop.jsp?command=MySearch&wtUser="+oid;
    openOtherName(url,"ViewTemplate","850","680","status=1,scrollbars=yes,resizable=1");
}

var targetTableId;
var targetCmdStr;
var targetLinkMsg;

function onDeleteTableRow(tableid, cmdstr, objid) {
    if ( !confirm('<%=messageService.getString("e3ps.message.ket_message", "01697") %><%--삭제 하시겠습니까?--%>') ) {
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

    if ( targetCmdStr == 'memberDelete' ) {
        addMemberRefresh('roleMemberTable', 'searchRoleMember','roleMemberDelete');
    }
}

function modifyPM() {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");

    if ( typeof attacheMembers == "undefined" || attacheMembers == null ) {
        return;
    }

    document.forms[0].action = "/plm/jsp/project/projectModifyPM.jsp?oid=<%=oid%>&userOid=" + attacheMembers[0][0]; ;
    //document.forms[0].command.value = "targetCmdStr";
    document.forms[0].method = "post";
    document.forms[0].submit();
}

window.getTagText = function(xmlDoc){
    return xmlDoc.textContent || xmlDoc.text || '';
}

function onMessage(req) {
    var xmlDoc = req.responseXML;
    var msg = getTagText(xmlDoc.getElementsByTagName("l_message")[0]);

    if(msg == 'false' && xmlDoc.getElementsByTagName("l_result") != null && getTagText(xmlDoc.getElementsByTagName("l_result")[0]) != ""){
        alert(getTagText(xmlDoc.getElementsByTagName("l_result")[0]));
        hideProcessing();
        return;
    }

    if ( msg == 'false' ) {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    if ( targetTableId == "refDeptTable" ) {
        acceptDept(xmlDoc);
    }
    else if ( targetTableId == "membertable" ) {
        location.reload();
    }
    else {
        location.reload();
    }
}
// -->
</script>

<style type="text/css">
<!--
body {
  margin-top: 0px;
  margin-left: 10px;
  margin-right: 15px;
  margin-bottom: 10px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
-->
</style>
</head>

<body>
<%@include file="/jsp/common/processing.html" %>
<form method="post">

<input type="hidden" name="oid" value="<%= oid %>">

<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                            <tr>
                                <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00403") %><%--Project 인원--%></td>
                                <td style="width: 10px;"></td>
                            </tr>
                        </table>
                    </td>
                    <td style="width: 136px;"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <!-- [END] title & position -->
        <table border="0" cellspacing="0" cellpadding="0" style="width: 800px;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <table style="width: 800px;" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">
                <table style="width: 800px;" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                    <td style="width: 20px;"><img src="/plm/portal/images/icon_4.png"></td>
                    <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "00111") %><%--CFT 업무--%></td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                        <%
                            // Excel Role Download & Upload, Role 담당자 수정 권한
                            // - 검토 Project : PM 또는 Admin(Biz Admin 포함), 최초 버전일 경우 PMO
                            // - 제품, 금형 Project : PM 또는 Admin(Biz Admin 포함)
                            if ( "REVIEW".equals(pjtType) && (auth.isLatest && (auth.isPM || isAdmin) || (auth.isFirst && auth.isPMOInWork && auth.isPMO))
                                    || ("PRODUCT".equals(pjtType) || "MOLD".equals(pjtType) || "WORK".equals(pjtType)) && (auth.isPM && auth.isLatest || isAdmin) ) {
                        %>
                            <td><a href="#" onclick="javaScript:excelDown()"><img src="/plm/portal/images/iocn_excel.png" border="0"></a></td>
                            <td style="width: 5px;">&nbsp;</td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:excelUp();" class="btn_blue">RoleLoad</a></td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                            <td style="width: 5px;">&nbsp;</td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:addRoleMember('roleMemberTable', 'searchRoleMember','roleMemberDelete');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00445") %><%--Role 담당자 수정--%></a></td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                            <td style="width: 5px;">&nbsp;</td>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                href="#" onclick="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        <%
                            }
                        %>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" style="width: 800px;">
                <tr>
                    <td class="space5"></td>
                </tr>
                </table>
                <table border="0" cellspacing="0" cellpadding="0" style="width: 800px;">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
                </table>
                <%
                    String teamType = "";
                    Vector vecTeamStd = null;
                    TeamTemplate tempTeam = null;

                    if ( "REVIEW".equals(pjtType) ) {           // 검토 Project Team 정보

                        tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Review Project");
                    }
                    else if ( "PRODUCT".equals(pjtType) ) {     // 제품 Project Team 정보

                        teamType = ((ProductProject)project).getTeamType();

                        if ( "자동차 사업부".equals(teamType) || "KETS".equals(teamType) || "KETS_PMO".equals(teamType) ) {
                            tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Product Project");
                        }
                        else {
                            tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Electron Project");
                        }
                    }
                    else if ( "MOLD".equals(pjtType) ) {        // 금형 Project Team 정보

                        tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Mold Project");
                    }
                    else if ( "WORK".equals(pjtType) ) {        // 업무 Project Team 정보

                        tempTeam = TeamHelper.service.getTeamTemplate(WCUtil.getWTContainerRef(), "Work Project");
                    }
                    
                    // Role 정보
                    if ( tempTeam != null ) {
                        vecTeamStd = tempTeam.getRoles();
                    }
                %>
                <table border="0" cellspacing="0" cellpadding="0" style="width: 800px;" id="roleMemberTable">
                <tr>
                    <td style="width: 130px;" class="tdblueM">Role</td>
                    <td style="width: 130px;" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td style="width: 130px;" class="tdblueM">Role</td>
                    <td style="width: 130px;" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                    <td style="width: 130px;" class="tdblueM">Role</td>
                    <td style="width: 130px;" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                </tr>
                <tr>
                <%
                    if ( vecTeamStd != null ) {

                        Collections.sort(vecTeamStd, new RoleComparator(true,true));
                        Hashtable roleHash = new Hashtable();

                        for ( int i = vecTeamStd.size() - 1; i > -1; i-- ) {

                            Role role = (Role) vecTeamStd.get(i);
                            String roleName_en = role.toString();
                            QueryResult roleQr = ProjectUserHelper.manager.getProjectRoleMember(project, null, roleName_en);

                            if ( roleQr.hasMoreElements() ) {
                                ProjectMemberLink link = (ProjectMemberLink)roleQr.nextElement();
                                roleHash.put(roleName_en, link);
                            }
                            else {
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

                        for ( int i = vecTeamStd.size() - 1; i > -1; i-- ) {

                            role = (Role) vecTeamStd.get(i);
                            roleName_en = role.toString();
                            roleName_ko = role.getDisplay(Locale.KOREA);

                            if ( roleIndex % 3 == 0 ) {
                                out.println("<tr>");
                            }
                %>
                    <td style="width: 80px;" class="tdwhiteL text-center"><%=roleName_ko%></td>
                    <td style="width: 130px;" <%if(roleIndex%3 == 2) {%>class="tdwhiteL0"<%}else {%>class="tdwhiteL"<%}%> colspan="<%=colspan%>">&nbsp;
                <%
                    ProjectMemberLink link = (ProjectMemberLink)roleHash.get(roleName_en);
                    data = new PeopleData(link.getMember());
                    String memOid = CommonUtil.getOIDString(link.getMember());
                %>
                        <a href="JavaScript:viewTodo('<%=memOid%>')"><%=data.name%></a>
                        <a href="#" onClick="javascript:onDeleteTableRow('roleMemberTable', 'roleMemberDelete', '<%=link.getPersistInfo().getObjectIdentifier().getStringValue()%>');">
                            <img src="/plm/portal/images/icon_delete.gif" style="width: 13px; height: 12px;" border='0'>
                        </a>
                    </td>
                <%
                            if ( i == 0 ) {
                                if ( roleIndex % 3 == 0) {      // 2명
                %>
                    <td style="width: 130px;" class="tdwhiteL">&nbsp;</td>
                    <td style="width: 130px;" class="tdwhiteL">&nbsp;</td>
                    <td style="width: 130px;" class="tdwhiteL">&nbsp;</td>
                    <td style="width: 130px;" class="tdwhiteL0">&nbsp;</td>
                <%
                                }
                                else if ( roleIndex % 3 == 1) { // 1명
                %>
                    <td style="width: 130px;" class="tdwhiteL">&nbsp;</td>
                    <td style="width: 130px;" class="tdwhiteL0">&nbsp;</td>
                <%
                                }
                            }

                            if ( roleIndex%3 == 2 || i == 0 ) {
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
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03"><%=messageService.getString("e3ps.message.ket_message", "02757")%><%--참여자--%></td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <%
                                    if ( auth.isPM && auth.isLatest || isAdmin || auth.isPMO ) {
                                %>
                                    <%-- <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:modifyPM();" class="btn_blue">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00374") %>PM 수정&nbsp;</a></td>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td> --%>
                                    <td style="width: 10px;">&nbsp;</td>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onClick="javascript:addMember('membertable', 'addMember','memberDelete');" class="btn_blue">&nbsp;&nbsp;&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02760") %><%--참여자 추가--%></a></td>
                                    <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                <%
                                    }
                                %>
                                </tr>
                                </table>
                            </td>
                        </tr>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" style="width: 680px;">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <table style="width: 100%;" cellpadding="0" cellspacing="0" class="table_border" >
                        <COL width="13%"><COL width="10%"><COL width="10%"><COL width="18%"><COL width="19%"><COL width="25%"><COL width="5%">
                        <tr>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02276") %><%--이름--%></td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02715") %><%--직위--%></td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01555") %><%--부서명--%></td>
                            <td class="tdblueM">E-Mail</td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03207") %><%--현재업무 Task--%></td>
                            <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></td>
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

                            QueryResult qResult =  SearchPagingProjectHelper.openPagingSession3(map, 0, 10);

                            while ( qResult.hasMoreElements() ) {

                                Object[] o = (Object[])qResult.nextElement();
                                E3PSTask eTask = null;
                                eTask = (E3PSTask)o[0];
                                work = eTask.getTaskName();
                                workLink = CommonUtil.getOIDString(eTask);
                                break;
                            }
                        %>
                        <tr>
                            <td class="tdwhiteM">PM</td>
                            <td class="tdwhiteM"><a href="JavaScript:viewTodo('<%=pOID%>')"><%=pData.name%></td>
                            <td class="tdwhiteM">&nbsp;<%=pData.duty%></td>
                            <td class="tdwhiteM">&nbsp;<%=pData.departmentName%></td>
                            <td class="tdwhiteL">&nbsp;<a href="mailto:<%=pData.email%>"><%=pData.email%></a></td>
                            <td class="tdwhiteM"><a href="#" onclick="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=workLink%>', '/plm/jsp/project/TaskView.jsp?oid=<%=workLink%>');"><%=work %></a>&nbsp;</td>
                            <td class="tdwhiteM0">&nbsp;</td>
                        </tr>
                        <%
                            ProjectMemberLink link = null;
                            PeopleData memberdata = null;
                            String memverOID = "";

                            QueryResult results = ProjectUserHelper.manager.getOnlyAppendMember(project);
                            Object[] obj = null;

                            while ( results.hasMoreElements() ) {

                                obj = (Object[])results.nextElement();
                                link = (ProjectMemberLink)obj[0];
                                memberdata = new PeopleData(link.getMember());
                                memverOID = CommonUtil.getOIDString(link.getMember());
                                String userOid = memberdata.wtuserOID;

                                if ( memberdata != null ) {

                                    work = "";
                                    workLink = "";

                                    map.put("getUser", userOid);

                                    qResult =  SearchPagingProjectHelper.openPagingSession3(map, 0, 10);

                                    while ( qResult.hasMoreElements() ) {

                                        Object[] o = (Object[])qResult.nextElement();
                                        E3PSTask eTask = null;
                                        eTask = (E3PSTask)o[0];
                                        work = eTask.getTaskName();
                                        workLink = CommonUtil.getOIDString(eTask);
                                        break;
                                    }
                        %>
                        <tr>
                            <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02757") %><%--참여자--%></td>
                            <td class="tdwhiteM"><a href="JavaScript:viewTodo('<%=memverOID%>')"><%=memberdata.name%></a></td>
                            <td class="tdwhiteM">&nbsp;<%=memberdata.duty%></td>
                            <td class="tdwhiteM">&nbsp;<%=memberdata.departmentName%></td>
                            <td class="tdwhiteL">&nbsp;<a href="mailto:<%=memberdata.email%>"><%=memberdata.email%></a></td>
                            <td class="tdwhiteM"><a href="#" onclick="javascript:parent.movePaage('/plm/jsp/project/ProjectViewLeftFrm.jsp?oid=<%=workLink%>', '/plm/jsp/project/TaskView.jsp?oid=<%=workLink%>');"><%=work %></a>&nbsp;</td>
                            <td class="tdwhiteM0">
                                <a href="#" onClick="javascript:onDeleteTableRow('membertable', 'memberDelete', '<%=link.getPersistInfo().getObjectIdentifier().getStringValue()%>');">
                                    <img src="/plm/portal/images/icon_delete.gif" style="width: 13px; height: 12px;" border='0'>
                                </a>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" style="width: 680px;">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </table>
    </td>
</tr>
</table>

</form>
</body>
</html>
