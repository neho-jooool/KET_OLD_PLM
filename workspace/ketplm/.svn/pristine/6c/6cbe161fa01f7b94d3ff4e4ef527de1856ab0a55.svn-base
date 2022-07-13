<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.Date,
                java.util.*,
                java.text.*,
                wt.fc.*" %>
<%@page import="e3ps.project.*,
                e3ps.project.beans.*,
                e3ps.common.util.*" %>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<%!
    private final SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd");
%>
<%
    String oid = request.getParameter("oid");
    String projectNo = request.getParameter("projectNo");
    QueryResult qr = ProjectHistoryManagerHelper.manager.getQueryHistoryManager(projectNo);
%>
<html>
<head>
<TITLE><%=messageService.getString("e3ps.message.ket_message", "01519") %><%--변경이력 목록--%></TITLE>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="JavaScript">
<!--
function saveScheduleHistory() {
    var screenWidth = screen.availWidth/2-150;
    var screenHeight = screen.availHeight/2-75;
    var url = "/plm/jsp/project/SaveProjectScheduleForm.jsp?oid=<%=oid%>";
    newWin = window.open(url,"window", "scrollbars=no,status=no,menubar=no,toolbar=no,location=no,directories=no,width=350,height=220,resizable=no,top="+screenHeight+",left="+screenWidth);
    //newWin.resizeTo(350,250);
    newWin.focus();
}

function viewChart(oid, winName, projectOid){
    var screenWidth = screen.availWidth/2;
    var screenHeight = screen.availHeight/2;
    openOtherName("/plm/jsp/project/ProjectChartHistoryViewPerMonth.jsp?oid="+oid+"&projectOid="+projectOid,winName,"1100","800","status=no,scrollbars=yes,resizable=no");
    //var windowWin = window.open("/plm/jsp/project/ProjectChartHistoryViewPerMonth.jsp?oid="+oid,"chart","status=no,scrollbars=yes,resizable,width=1100,height=800");
    //windowWin.focus();
}

function closeForm() {
    self.close();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="95%" height=40 align=center border=0>
    <tr>
        <td width=100%>
            <table border=0 cellpadding=0 cellspacing=0 >
                <tr>
                    <td><img src=/plm/portal/images/title2_left.gif></td>
                    <td background=/plm/portal/images/title_back.gif><%=messageService.getString("e3ps.message.ket_message", "01519") %><%--변경이력 목록--%></td>
                    <td><img src=/plm/portal/images/title2_right.gif></td>
            </table>
        </td>
        <%
        E3PSProject jp = (E3PSProject)CommonUtil.getObject(oid);
        boolean isPm =  ProjectUserHelper.manager.isPM(jp);
        if(isPm){ %>
        <td align=right width=70>
            <input type=button class="btnTras" onclick="javascript:saveScheduleHistory()" value='<%=messageService.getString("e3ps.message.ket_message", "02273") %><%--이력저장--%>' id=button>
        </td>&nbsp;
        <%} %>
        <td align=right width=70>
            <input type=button class="btnTras" onclick="javascript:closeForm()" value="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" id=button>
        </td>
    </tr>
</table>
<table width="95%" border="0" cellpadding="2" cellspacing="1" bgcolor=AABDC6 align=center>
    <tr bgcolor="#D8E0E7" align=center>
        <td width=30 id=textblue rowspan=2>NO</td>
        <td id=textblue><%=messageService.getString("e3ps.message.ket_message", "01518") %><%--변경이력--%></td>
        <td id=textblue><%=messageService.getString("e3ps.message.ket_message", "00819") %><%--계획{0}시작일자--%></td>
        <td id=textblue><%=messageService.getString("e3ps.message.ket_message", "00827") %><%--계획{0}종료일자--%></td>
        <td id=textblue><%=messageService.getString("e3ps.message.ket_message", "01111") %><%--기간--%></td>
        <td id=textblue><%=messageService.getString("e3ps.message.ket_message", "02730") %><%--진행률--%></td>
    </tr>
    <tr bgcolor="#D8E0E7" align=center>
        <td colspan=5><%=messageService.getString("e3ps.message.ket_message", "01866") %><%--설명--%></td>
    </tr>
<%
    if ( qr != null ) {
        int count = 0;
        ProjectHistoryManagerData historyData = null;
        E3PSProjectData projectData = null;
        while ( qr.hasMoreElements() ) {
            Object objArr[]=(Object[])qr.nextElement();
            historyData = new ProjectHistoryManagerData( ( (HistoryManagerProjectLink)objArr[0] ) );
            projectData = new E3PSProjectData(historyData.historyProject);
%>
    <tr bgcolor="#FFFFFF" align=center>
        <td width=30 rowspan=2><%=count++%></td>
        <td id=textblue><a href="javascript:viewChart('<%=historyData.historyMgmtOID%>', 'cahrt<%=count%>','<%=CommonUtil.getOIDString(historyData.historyProject)%>');"><%=historyData.historyTypeName%></A></td>
        <td><%=DateUtil.getDateString(projectData.pjtPlanStartDate,"D")%></td>
        <td><%=DateUtil.getDateString(projectData.pjtPlanEndDate,"D")%></td>
        <td><%=projectData.pjtDuration%>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00135") %><%--{0}일--%></td>
        <td><%=projectData.pjtCompletion%>&nbsp;%</td>
    </tr>
    <tr bgcolor="#FFFFFF" align=center>
        <td colspan=5><%=historyData.historyDesc%></td>
    </tr>
<%
        }
%>
<%
    }
%>
</table>
</body>
</html>
