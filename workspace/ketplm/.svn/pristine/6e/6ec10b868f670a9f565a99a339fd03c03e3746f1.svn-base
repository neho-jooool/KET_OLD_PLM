<%@page contentType="text/html; charset=UTF-8"%>

<%@page import="wt.team.TeamTemplate"%>
<%@page import="wt.team.TeamHelper"%>
<%@page import="e3ps.common.util.WCUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="wt.project.Role"%>
<%@page import="java.util.Locale"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.web.PageQueryBroker"%>
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
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.beans.JELTaskData"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
String oid = request.getParameter("oid");

//PageQueryBroker broker  = null;
//QuerySpec qs = null;
//QueryResult result = null;

ProjectManager manager = null;
ProjectManagerData managerData = null;

//if(StringUtil.checkString(oid)) {
    manager = (ProjectManager) CommonUtil.getObject(oid);
    managerData = new ProjectManagerData(manager);
//} else {
    //qs = new QuerySpec();

    //Class target = ProjectManager.class;
    //int idx_target = qs.addClassList(target, true);

    //broker = new PageQueryBroker(request, qs);
    //result = broker.search();
//}
%>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<title><%=messageService.getString("e3ps.message.ket_message", "03039") %><%--프로그램 별 지연 현황--%></title>
<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">

<script language=JavaScript>
function viewDelayTask(oid, managerOid) {
    //oid : Project OID
    //managerOid : Program OID
    var url = "/plm/jsp/project/chart/ProjectDelayTaskChart.jsp?oid="+oid+"&managerOid="+managerOid;
    getOpenWindow(url, "viewProjectDelayTaskPopUp", "1100", "700");
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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03039") %><%--프로그램 별 지연 현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "03039") %><%--프로그램 별 지연 현황--%></td>
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
                    <%
                    //out.println("Result Null!!!");

                    Vector delayVec = managerData.getDelayTask(manager);
                    //out.println("delayVec>>> " + delayVec.size());
                    %>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <col width='3%'><col width='20%'><col width='20%'><col width='7%'>
                    <% //for(int i = 0; i < delayVec.size(); i++) { %>
                    <col width='50<%//=50/delayVec.size()%>%'>
                    <% //} %>
                        <tr>
                            <td class="tdblueM" rowspan=2>NO</td>
                            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03044") %><%--프로그램명--%></td>
                            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                            <td class="tdblueM" rowspan=2>Total</td>
                            <!--td class="tdblueM" colspan=<%//=delayTaskVec.size() %>>지연 TASK현황</td-->
                            <td class="tdblueM" rowspan=2><%=messageService.getString("e3ps.message.ket_message", "02704") %><%--지연 TASK현황--%></td>
                        </tr>
                        <tr>
                            <td class="tdblueM">
                            <% for(int j = 0; j < delayVec.size(); j++) { %>
                            <%=((JELTask)delayVec.get(j)).getDepartment().getName() %>
                            <% } %>
                            </td>
                        </tr>

                        <%
                            managerData = new ProjectManagerData(manager);
                        %>
                            <% if(managerData.jelProjectVec.size() == 1) {  %>
                                <%
                                JELProject pjt = (JELProject) managerData.jelProjectVec.get(0);
                                JELProjectData pjtData = new JELProjectData(pjt);

                                Vector delayTaskVec = managerData.getDelayTaskForPJT(pjt);
                                //out.println("delayTaskVec>>> " +delayTaskVec.size());

                                int i = 1;
                                %>
                        <tr>
                            <td class="tdwhiteM">&nbsp;<%=i %></td>
                            <td class="tdwhiteM">&nbsp;<%=managerData.managerName %></td>
                            <td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewDelayTask('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <%=delayTaskVec.size()==0?0:delayTaskVec.size() %>
                                </a>
                            </td>
                            <td class="tdwhiteM">&nbsp;
                            <%
                                for(int k = 0; k < delayTaskVec.size(); k++) {
                                    JELTask task = (JELTask) delayTaskVec.get(k);
                                    JELTaskData taskData = new JELTaskData(task);
                            %>
                            <%=taskData.taskName %>[<%=task.getDepartment()==null?"":task.getDepartment().getName() %>]
                            <% if(k < delayTaskVec.size()-1) out.println(" || "); %>
                            <% if(k%2 == 1) out.println("<br>"); %>
                            <% } %>
                            </td>
                        </tr>
                            <% } else { %>
                        <tr>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%//=i %></td>
                            <td class="tdwhiteM" rowspan="<%=managerData.jelProjectVec.size() %>">&nbsp;<%=managerData.managerName %></td>
                                <% for(int j = 0; j < managerData.jelProjectVec.size(); j++) { %>
                                <%
                                    JELProject pjt = (JELProject) managerData.jelProjectVec.get(j);
                                    JELProjectData pjtData = new JELProjectData(pjt);

                                    Vector delayTaskVec = managerData.getDelayTaskForPJT(pjt);
                                    //out.println("delayTaskVec>>> " +delayTaskVec.size());
                                %>
                            <td class="tdwhiteM">&nbsp;<%=pjtData.pjtName %></td>
                            <td class="tdwhiteM">&nbsp;
                                <a href="javascript:viewDelayTask('<%=pjtData.jelPjtOID %>', '<%=CommonUtil.getOIDString(manager) %>')">
                                <%=delayTaskVec.size()==0?0:delayTaskVec.size() %>
                                </a>
                            </td>
                            <td class="tdwhiteM">&nbsp;
                                    <% for(int k = 0; k < delayTaskVec.size(); k++) { %>
                                        <%
                                        JELTask task = (JELTask) delayTaskVec.get(k);
                                        JELTaskData taskData = new JELTaskData(task);

                                        if( (taskData.jelProjectOID).equals(pjtData.jelPjtOID) ) {
                                            //out.println("Equal!!!["+pjtData.pjtName+", "+taskData.taskName+", "+task.getDepartment().getName()+"]");
                                        } else {
                                            //out.println("Not Equal!!["+pjtData.pjtName+", "+taskData.taskName+", "+task.getDepartment().getName()+"]");
                                        }
                                        %>
                            <%=taskData.taskName %>[<%=task.getDepartment()==null?"":task.getDepartment().getName() %>]
                            <% if(k < delayTaskVec.size()-1) { %> || <% } %>
                                    <% } %>
                            </td>
                        </tr>
                                <% } %>
                            <% } %>
                    </table>

            </td>
        </tr>
    </table>
</form>
</body>
</html>
