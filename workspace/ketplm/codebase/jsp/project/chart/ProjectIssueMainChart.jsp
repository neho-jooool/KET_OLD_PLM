<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.project.Role"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="wt.query.QuerySpec"%>
<%@page import="e3ps.project.ProjectManager"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.ProjectManagerData"%>
<%@page import="e3ps.project.JELProject"%>
<%@page import="e3ps.project.beans.ProjectIssueHelper"%>
<%@page import="e3ps.project.beans.JELProjectData"%>
<%@page import="e3ps.groupware.company.Department"%>
<%@page import="e3ps.project.ProjectIssue"%>
<%@page import="e3ps.project.beans.ProjectIssueData"%>
<%@page import="e3ps.common.web.QueryBroker"%>
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.beans.JELTaskData"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
String oid = request.getParameter("oid");

QueryBroker broker  = null;
QuerySpec qs = null;
QueryResult result = null;

ProjectManager manager = null;
ProjectManagerData managerData = null;

if(StringUtil.checkString(oid)) {
    manager = (ProjectManager) CommonUtil.getObject(oid);
    managerData = new ProjectManagerData(manager);
} else {
    qs = new QuerySpec();

    Class target = ProjectManager.class;
    int idx_target = qs.addClassList(target, true);

    result = broker.search(qs, false);
}
%>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03037") %><%--프로그램 별 ISSUE 현황--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">

<script language=JavaScript>
function viewProjectIssue(oid, managerOid) {
    //oid : Project OID
    //managerOid : Program OID
    var url = "/plm/jsp/project/chart/ProjectIssueChart.jsp?oid="+oid+"&managerOid="+managerOid;
    getOpenWindow(url, "viewProjectIssueDetailPopUp", "1100", "700");
}

    function viewProgram(oid) {

        var url = "/plm/jsp/project/ViewProgram.jsp?oid="+oid+"&popup=popup";
        getOpenWindow(url, "viewProgramPopup", "800", "600");
    }

    function projectViewpopup(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", 'view',1150,800);
    }

    function viewTask(oid){
        var url = "/plm/jsp/project/TaskView.jsp?oid="+oid;
        openOtherName(url,"","800","600","status=1,scrollbars=yes,resizable=1");
    }

</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
    <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td valign="top" style="padding:0px 0px 0px 0px">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                  <tr>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03037") %><%--프로그램 별 ISSUE 현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03037") %><%--프로그램 별 ISSUE 현황--%></td>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a03.gif" width="13" height="30" /></td>
                  </tr>
                </table>
                <!-- Search -->
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="right">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    &nbsp;&nbsp;
                                    <a href="#" onClick="javascript:self.close();">
                                    <img src="/plm/portal/images/img_default/button/board_btn_close.gif" alt="<%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%>" width="62" height="20" border="0">
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </tr>
                </table>
                <!-- Search -->
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td class="tab_btm2"></td>
                    </tr>
                </table>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td  class="tab_btm1"></td>
                    </tr>
                </table>
                <% if(result != null) { %>
                    <%
                    //out.println("Result Not Null!!!");

                    Vector managerVec = new Vector();
                    Vector departVec1 = null;

                    int a = 0;
                    if(result != null) {
                        while (result.hasMoreElements()) {
                            Object[] obj = (Object[]) result.nextElement();
                            manager = (ProjectManager) obj[0];
                            managerVec.add(manager);

                            managerData = new ProjectManagerData(manager);
                            departVec1 = managerData.getDepartmentForTask(manager, "", "");

                            a = departVec1.size();
                            //out.println("A>>>> " +a);
                        }
                    }
                    %>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <col width='3%'><col width='20%'><col width='20%'><col width='7%'>
                    <% for(int b = 0; b < a; b++) { %>
                    <col width='<%=50/a%>%'>
                    <% } %>
                        <tr>
                            <td class="tdblueM" rowspan=2>NO</td>
                            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
                            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                            <td class="tdblueM" rowspan=2>Total</td>
                            <td class="tdblueM" colspan=<%=a %>><%=messageService.getString("e3ps.message.ket_message", "02320") %><%--이슈현황--%></td>
                        </tr>
                        <tr>
                            <% for(int c = 0; c < a; c++) { %>
                            <td class="tdblueM"><%=((Department)departVec1.get(c)).getName() %></td>
                            <% } %>
                        </tr>

                        <%
                        boolean checkOut = false;
                        int i = 1;
                        int issueCount = 0;

                        if(result != null) {
                            //out.println("Result Not Null[1]!!!");
                            for(int j = 0; j < managerVec.size(); j++) {
                                manager = (ProjectManager) managerVec.get(j);
                                managerData = new ProjectManagerData(manager);

                                departVec1 = managerData.getDepartmentForTask(manager, "", "");
                        %>
                            <% if(managerData.jelProjectVec.size() > 0 && managerData.jelProjectVec.size() < 2) {  %>
                                <%
                                JELProject pjt = (JELProject) managerData.jelProjectVec.get(0);
                                JELProjectData pjtData = new JELProjectData(pjt);

                                QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                issueCount = pjtResult.size();

                                ProjectIssue pjtIssue = null;
                                ProjectIssueData pjtIssueData = null;
                                JELTask task = null;
                                JELTaskData taskData = null;
                                Department tmpDepart = null;

                                for(int aa = 0; aa < pjtResult.size(); aa++) {
                                    Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                    pjtIssue = (ProjectIssue) tmpObj[0];
                                    pjtIssueData = new ProjectIssueData(pjtIssue);

                                    task = (JELTask) pjtIssueData.task;
                                    taskData = new JELTaskData(task);

                                    tmpDepart = task.getDepartment();
                                    //if(tmpDepart != null) out.println("Department<<<<<<<<<<<<<< " + tmpDepart.getName());
                                }
                                %>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<%=i %></td>
                            <td class="tdwhiteM">&nbsp;
                            <a href="javascript:viewProgram('<%=CommonUtil.getOIDString(manager)%>')">
                            <B><%=managerData.managerName %></B></a>
                            </td>
                            <td class="tdwhiteM">&nbsp;
                            <a href="javascript:projectViewpopup('<%=CommonUtil.getOIDString(pjt)%>')">
                            <B><%=pjtData.pjtName %></B></a>
                            </td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                            <%
                            if(departVec1.size() > 0) {
                                for(int k = 0; k < departVec1.size(); k++) {
                            %>
                            <td class="tdwhiteM">&nbsp;<%=((Department)departVec1.get(k)).getName() %></td>
                            <%
                                }
                            } else {
                            %>
                            <td class="tdwhiteM0">&nbsp;</td>
                            <% } %>
                        </tr>
                            <% } else { %>
                        <tr>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=i %></td>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;
                            <a href="javascript:viewProgram('<%=CommonUtil.getOIDString(manager)%>')">
                            <B><%=managerData.managerName %></B></a>
                            </td>
                                <% for(int l = 0; l < managerData.jelProjectVec.size(); l++) { %>
                                <%
                                    JELProject pjt = (JELProject) managerData.jelProjectVec.get(l);
                                    JELProjectData pjtData = new JELProjectData(pjt);

                                    QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                    issueCount = pjtResult.size();

                                    ProjectIssue pjtIssue = null;
                                    ProjectIssueData pjtIssueData = null;
                                    JELTask task = null;
                                    JELTaskData taskData = null;
                                    Department tmpDepart = null;

                                    for(int aa = 0; aa < pjtResult.size(); aa++) {
                                        Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                        pjtIssue = (ProjectIssue) tmpObj[0];
                                        pjtIssueData = new ProjectIssueData(pjtIssue);

                                        task = (JELTask) pjtIssueData.task;
                                        taskData = new JELTaskData(task);

                                        tmpDepart = task.getDepartment();
                                        //if(tmpDepart != null) out.println("Department<<<<<<<<<<<<<< " + tmpDepart.getName());
                                    }
                                %>
                            <td class="tdwhiteM">&nbsp;
                            <a href="javascript:projectViewpopup('<%=CommonUtil.getOIDString(pjt)%>')">
                            <B><%=pjtData.pjtName %></B></a>
                            </td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                                    <%
                                        if(departVec1.size() > 0) {
                                            for(int m = 0; m < departVec1.size(); m++) {
                                                Department depart = (Department) departVec1.get(m);
                                    %>
                                        <%
                                                Vector tVec = managerData.getIssueForDepartment(manager, pjt);
                                                int aa = 1;
                                                String tName = "";
                                                for(int n = 0; n < tVec.size(); n++) {
                                                    ProjectIssue issue = (ProjectIssue) tVec.get(n);
                                                    ProjectIssueData issueData = new ProjectIssueData(issue);

                                                    Department tmpDepart1 = issueData.task.getDepartment();

                                                    if( (depart.getName()).equals( (tmpDepart1.getName()) ) && issueCount > 0 ) tName = ""+aa++;
                                                    else tName = "";
                                                }
                                        %>
                            <td class="tdwhiteM">&nbsp;<%=tName %><br></td>
                                    <%
                                            }
                                        }
                                    %>
                        </tr>
                                <% } %>
                            <% } %>
                        <%
                                i++;
                            }
                        } else {
                            //out.println("Result Null[1]!!!");
                            managerData = new ProjectManagerData(manager);
                        %>
                            <% if(managerData.jelProjectVec.size() > 0 && managerData.jelProjectVec.size() < 2) {  %>
                                <%
                                JELProject pjt = (JELProject) managerData.jelProjectVec.get(0);
                                JELProjectData pjtData = new JELProjectData(pjt);

                                QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                issueCount = pjtResult.size();

                                ProjectIssue pjtIssue = null;
                                ProjectIssueData pjtIssueData = null;
                                JELTask task = null;
                                JELTaskData taskData = null;
                                Department tmpDepart = null;
                                String[] departStr = new String[pjtResult.size()];

                                for(int aa = 0; aa < pjtResult.size(); aa++) {
                                    Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                    pjtIssue = (ProjectIssue) tmpObj[0];
                                    pjtIssueData = new ProjectIssueData(pjtIssue);

                                    task = (JELTask) pjtIssueData.task;
                                    taskData = new JELTaskData(task);

                                    tmpDepart = task.getDepartment();
                                    if(tmpDepart != null) {
                                        departStr[aa] = tmpDepart.getName();
                                    }
                                }
                                %>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<%=i %></td>
                            <td class="tdwhiteM">&nbsp;
                            <a href="javascript:viewProgram('<%=CommonUtil.getOIDString(manager)%>')">
                            <B><%=managerData.managerName %></B></a>
                            </td>
                            <td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                            <%
                            if(departVec1.size() > 0) {

                                for(int k = 0; k < departVec1.size(); k++) {
                                    int tCount = 0;
                                    for(int l = 0; l < departStr.length; l++) {
                                        String t1 = departStr[l];
                                        String t2 = ((Department)departVec1.get(k)).getName();

                                        if( t2.equals(t1) ) {
                                            tCount++;
                                        }
                                    }
                            %>
                            <td class="tdwhiteM">&nbsp;<%=tCount==0?"":tCount %></td>
                            <%
                                }
                            } else {
                            %>
                            <td class="tdwhiteM0">&nbsp;</td>
                            <% } %>
                        </tr>
                            <% } else { %>
                        <tr>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=i %></td>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=managerData.managerName %></td>
                                <% for(int j = 0; j < managerData.jelProjectVec.size(); j++) { %>
                                <%
                                    JELProject pjt = (JELProject) managerData.jelProjectVec.get(j);
                                    JELProjectData pjtData = new JELProjectData(pjt);

                                    QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                    issueCount = pjtResult.size();

                                    ProjectIssue pjtIssue = null;
                                    ProjectIssueData pjtIssueData = null;
                                    JELTask task = null;
                                    JELTaskData taskData = null;
                                    Department tmpDepart = null;
                                    String[] departStr = new String[pjtResult.size()];

                                    for(int aa = 0; aa < pjtResult.size(); aa++) {
                                        Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                        pjtIssue = (ProjectIssue) tmpObj[0];
                                        pjtIssueData = new ProjectIssueData(pjtIssue);

                                        task = (JELTask) pjtIssueData.task;
                                        taskData = new JELTaskData(task);

                                        tmpDepart = task.getDepartment();
                                        if(tmpDepart != null) {
                                            departStr[aa] = tmpDepart.getName();
                                        }
                                    }
                                %>
                            <td class="tdwhiteM">&nbsp;
                            <a href="javascript:projectViewpopup('<%=CommonUtil.getOIDString(pjt)%>')">
                            <B><%=pjtData.pjtName %></B></a>
                            </td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                            <%
                            if(departVec1.size() > 0) {

                                for(int k = 0; k < departVec1.size(); k++) {
                                    int tCount = 0;
                                    for(int l = 0; l < departStr.length; l++) {
                                        String t1 = departStr[l];
                                        String t2 = ((Department)departVec1.get(k)).getName();

                                        if( t2.equals(t1) ) {
                                            tCount++;
                                        }
                                    }
                            %>
                            <td class="tdwhiteM">&nbsp;<%=tCount==0?"":tCount %></td>
                            <%
                                }
                            } else {
                            %>
                            <td class="tdwhiteM0">&nbsp;</td>
                            <% } %>
                        </tr>
                                <% } %>
                            <% } %>
                        <%
                        }
                        %>
                    </table>
                <% } else { %>
                    <%
                    //out.println("Result Null!!!");

                    Vector departVec = managerData.getDepartmentForTask(manager, "", "");
                    %>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <col width='3%'><col width='20%'><col width='20%'><col width='7%'>
                    <% for(int i = 0; i < departVec.size(); i++) { %>
                    <col width='<%=50/departVec.size()%>%'>
                    <% } %>
                        <tr>
                            <td class="tdblueM" rowspan=2>NO</td>
                            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
                            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                            <td class="tdblueM" rowspan=2>Total</td>
                            <td class="tdblueM" colspan=<%=departVec.size() %>><%=messageService.getString("e3ps.message.ket_message", "02320") %><%--이슈현황--%></td>
                        </tr>
                        <tr>
                            <%
                            Role role = null;
                            for(int j = 0; j < departVec.size(); j++) {
                            %>
                            <td class="tdblueM"><%=((Department)departVec.get(j)).getName() %></td>
                            <% } %>
                        </tr>

                        <%
                        boolean checkOut = false;
                        int i = 1;
                        int issueCount = 0;

                        if(result != null) {
                            //out.println("Result Not-Null[2]!!!!");
                            while (result.hasMoreElements()) {
                                Object[] obj = (Object[]) result.nextElement();
                                manager = (ProjectManager) obj[0];
                                managerData = new ProjectManagerData(manager);
                        %>
                            <% if(managerData.jelProjectVec.size() > 0 && managerData.jelProjectVec.size() < 2) {  %>
                                <%
                                JELProject pjt = (JELProject) managerData.jelProjectVec.get(0);
                                JELProjectData pjtData = new JELProjectData(pjt);

                                QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                issueCount = pjtResult.size();

                                ProjectIssue pjtIssue = null;
                                ProjectIssueData pjtIssueData = null;
                                JELTask task = null;
                                JELTaskData taskData = null;
                                Department tmpDepart = null;
                                String[] departStr = new String[pjtResult.size()];

                                for(int aa = 0; aa < pjtResult.size(); aa++) {
                                    Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                    pjtIssue = (ProjectIssue) tmpObj[0];
                                    pjtIssueData = new ProjectIssueData(pjtIssue);

                                    task = (JELTask) pjtIssueData.task;
                                    taskData = new JELTaskData(task);

                                    tmpDepart = task.getDepartment();
                                    if(tmpDepart != null) {
                                        departStr[aa] = tmpDepart.getName();
                                    }
                                }
                                %>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<%=i %></td>
                            <td class="tdwhiteM">&nbsp;<%=managerData.managerName %></td>
                            <td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                            <%
                            if(departVec.size() > 0) {

                                for(int k = 0; k < departVec.size(); k++) {
                                    int tCount = 0;
                                    for(int l = 0; l < departStr.length; l++) {
                                        String t1 = departStr[l];
                                        String t2 = ((Department)departVec.get(k)).getName();

                                        if( t2.equals(t1) ) {
                                            tCount++;
                                        }
                                    }
                            %>
                            <td class="tdwhiteM">&nbsp;<%=tCount==0?"":tCount %></td>
                            <%
                                }
                            } else {
                            %>
                            <td class="tdwhiteM0">&nbsp;</td>
                            <% } %>
                        </tr>
                            <% } else { %>
                        <tr>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=i %></td>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=managerData.managerName %></td>
                                <% for(int j = 0; j < managerData.jelProjectVec.size(); j++) { %>
                                <%
                                    JELProject pjt = (JELProject) managerData.jelProjectVec.get(j);
                                    JELProjectData pjtData = new JELProjectData(pjt);

                                    QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                    issueCount = pjtResult.size();

                                    ProjectIssue pjtIssue = null;
                                    ProjectIssueData pjtIssueData = null;
                                    JELTask task = null;
                                    JELTaskData taskData = null;
                                    Department tmpDepart = null;
                                    String[] departStr = new String[pjtResult.size()];

                                    for(int aa = 0; aa < pjtResult.size(); aa++) {
                                        Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                        pjtIssue = (ProjectIssue) tmpObj[0];
                                        pjtIssueData = new ProjectIssueData(pjtIssue);

                                        task = (JELTask) pjtIssueData.task;
                                        taskData = new JELTaskData(task);

                                        tmpDepart = task.getDepartment();
                                        if(tmpDepart != null) {
                                            departStr[aa] = tmpDepart.getName();
                                        }
                                    }
                                %>
                            <td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                            <%
                            if(departVec.size() > 0) {

                                for(int k = 0; k < departVec.size(); k++) {
                                    int tCount = 0;
                                    for(int l = 0; l < departStr.length; l++) {
                                        String t1 = departStr[l];
                                        String t2 = ((Department)departVec.get(k)).getName();

                                        if( t2.equals(t1) ) {
                                            tCount++;
                                        }
                                    }
                            %>
                            <td class="tdwhiteM">&nbsp;<%=tCount==0?"":tCount %></td>
                            <%
                                }
                            } else {
                            %>
                            <td class="tdwhiteM0">&nbsp;</td>
                            <% } %>
                        </tr>
                                <% } %>
                            <% } %>
                        <%
                                i++;
                            }
                        } else {
                            //out.println("Result Null[2]!!!!");
                            managerData = new ProjectManagerData(manager);
                        %>
                            <% if(managerData.jelProjectVec.size() > 0 && managerData.jelProjectVec.size() < 2) {  %>
                                <%
                                JELProject pjt = (JELProject) managerData.jelProjectVec.get(0);
                                JELProjectData pjtData = new JELProjectData(pjt);

                                QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                issueCount = pjtResult.size();

                                ProjectIssue pjtIssue = null;
                                ProjectIssueData pjtIssueData = null;
                                JELTask task = null;
                                JELTaskData taskData = null;
                                Department tmpDepart = null;
                                String[] departStr = new String[pjtResult.size()];

                                for(int aa = 0; aa < pjtResult.size(); aa++) {
                                    Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                    pjtIssue = (ProjectIssue) tmpObj[0];
                                    pjtIssueData = new ProjectIssueData(pjtIssue);

                                    task = (JELTask) pjtIssueData.task;
                                    taskData = new JELTaskData(task);

                                    tmpDepart = task.getDepartment();
                                    if(tmpDepart != null) {
                                        departStr[aa] = tmpDepart.getName();
                                    }
                                }
                                %>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<%=i %></td>
                            <td class="tdwhiteM">&nbsp;<%=managerData.managerName %></td>
                            <td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                            <%
                            if(departVec.size() > 0) {

                                for(int k = 0; k < departVec.size(); k++) {
                                    int tCount = 0;
                                    for(int l = 0; l < departStr.length; l++) {
                                        String t1 = departStr[l];
                                        String t2 = ((Department)departVec.get(k)).getName();

                                        if( t2.equals(t1) ) {
                                            tCount++;
                                        }
                                    }
                            %>
                            <td class="tdwhiteM">&nbsp;<%=tCount==0?"":tCount %></td>
                            <%
                                }
                            } else {
                            %>
                            <td class="tdwhiteM0">&nbsp;</td>
                            <% } %>
                        </tr>
                            <% } else { %>
                        <tr>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=i %></td>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=managerData.managerName %></td>
                                <% for(int j = 0; j < managerData.jelProjectVec.size(); j++) { %>
                                <%
                                    JELProject pjt = (JELProject) managerData.jelProjectVec.get(j);
                                    JELProjectData pjtData = new JELProjectData(pjt);

                                    QueryResult pjtResult = ProjectIssueHelper.manager.getProjectIssue(pjt);
                                    issueCount = pjtResult.size();

                                    ProjectIssue pjtIssue = null;
                                    ProjectIssueData pjtIssueData = null;
                                    JELTask task = null;
                                    JELTaskData taskData = null;
                                    Department tmpDepart = null;
                                    String[] departStr = new String[pjtResult.size()];

                                    for(int aa = 0; aa < pjtResult.size(); aa++) {
                                        Object[] tmpObj = (Object[]) pjtResult.nextElement();
                                        pjtIssue = (ProjectIssue) tmpObj[0];
                                        pjtIssueData = new ProjectIssueData(pjtIssue);

                                        task = (JELTask) pjtIssueData.task;
                                        taskData = new JELTaskData(task);

                                        tmpDepart = task.getDepartment();
                                        if(tmpDepart != null) {
                                            departStr[aa] = tmpDepart.getName();
                                        }
                                    }
                                %>
                            <td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewProjectIssue('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <B><%=issueCount==0?"":issueCount %></B>
                                </a>
                            </td>
                                    <%
                                    if(departVec.size() > 0) {

                                        for(int k = 0; k < departVec.size(); k++) {
                                            int tCount = 0;
                                            for(int l = 0; l < departStr.length; l++) {
                                                String t1 = departStr[l];
                                                String t2 = ((Department)departVec.get(k)).getName();

                                                if( t2.equals(t1) ) {
                                                    tCount++;
                                                }
                                            }
                                    %>
                                    <td class="tdwhiteM">&nbsp;<%=tCount==0?"":tCount %></td>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <td class="tdwhiteM0">&nbsp;</td>
                                    <% } %>
                        </tr>
                                <% } %>
                            <% } %>
                        <%
                        }
                        %>
                    </table>
                <% } %>

            </td>
        </tr>
    </table>
</form>
</body>
</html>
