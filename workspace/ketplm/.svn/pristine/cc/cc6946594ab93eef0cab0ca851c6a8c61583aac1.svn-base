<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/jsp/common/context.jsp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.JELProject"%>
<%@page import="e3ps.project.beans.JELProjectData"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="e3ps.project.TemplateTask"%>
<%@page import="e3ps.project.beans.TemplateTaskData"%>
<%@page import="wt.fc.QueryResult"%>
<%@page import="e3ps.project.JELTask"%>
<%@page import="e3ps.project.beans.JELTaskData"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<%
String oid = request.getParameter("oid");
JELProject project = (JELProject) CommonUtil.getObject(oid);


%>
<head>

<title><%=messageService.getString("e3ps.message.ket_message", "01559") %><%--부서별 현황--%></title>
<style type="text/css">

body {
    background-image: url(/plm/portal/images/img_default/background2.gif);
    background-repeat:repeat-x;
    background-color: #ffffff;
    margin-top: 24px;
    margin-left:15px;
    margin-right:10px;

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

</style>
</head>



<link rel="stylesheet" href="/plm/portal/css/e3ps.css" type="text/css">
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<script language=JavaScript>
    function search() {
        document.forms[0].projectState.value = document.forms[0].tmpState[document.forms[0].tmpState.selectedIndex].value;

        onProgressBar();

        document.forms[0].command.value = "search";
        document.forms[0].method = "post";
        document.forms[0].action = "/plm/jsp/project/chart/ProjectChart.jsp";
        document.forms[0].submit();
    }
    function projectViewpopup(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", 'view',1150,800);
    }

</script>




<body>

<form>
<input type='hidden' name='command'>
<input type='hidden' name='projectState'>
<input type='hidden' name='oid' value="<%=oid %>">




<table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
            <td valign="top" style="padding:24px 0px 15px 15px">
                <table width="100%" border="0" cellpadding="0" cellspacing="0" background="/plm/portal/images/img_default/title_bar_a02.gif" style="margin:0px 0px 15px 0px">
                  <tr>
                    <td width="16"><img src="/plm/portal/images/img_default/title_bar_a01.gif" width="23" height="30" /></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01559") %><%--부서별 현황--%></td>
                    <td align="right" style="padding:8px 0px 0px 0px">Home &gt; <%=messageService.getString("e3ps.message.ket_message", "03211") %><%--현황관리--%> &gt; <%=messageService.getString("e3ps.message.ket_message", "01559") %><%--부서별 현황--%></td>
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
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <col width='20%'><col width='10%'><col width='10%'><col width='10%'>
                        <tr>

                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
                            <td class="tdblueM">Sub PM</td>
                            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00629") %><%--개발단계--%></td>
                            <%

                            QueryResult tmpQr = ProjectTaskHelper.manager.getChild(project);
                            int aa = 0;
                            if(tmpQr.size() > 0) {
                                while(tmpQr.hasMoreElements()) {
                                    Object[] obj = (Object[]) tmpQr.nextElement();
                                    TemplateTask tmpTask = (TemplateTask) obj[0];
                                    TemplateTaskData tmpTaskData = new TemplateTaskData(tmpTask);
                            %>
                            <td class="tdblueM"><%=tmpTaskData.name %></td>
                            <%
                                    aa++;
                                }
                            }
                            %>
                        </tr>
                        <% if(project == null) { %>
                        <tr>
                            <td class="tdwhiteM" colspan=<%=aa+5 %>>&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00751") %><%--결과가 없습니다--%>.</td>
                        </tr>

                        <% } else { %>
                        <%

                            JELProjectData projectData = new JELProjectData(project);
                            if(project != null) {
                        %>
                        <tr>

                            <td class="tdwhiteM" rowspan="2">&nbsp;
                            <a href="javascript:projectViewpopup('<%=CommonUtil.getOIDString(project)%>')">
                            <B><%=projectData.pjtName %></B></a>
                            </td>
                            <td class="tdwhiteM" rowspan="2">&nbsp;<%=projectData.getStateStr(projectData.stateKorea) %></td>
                            <td class="tdwhiteM" rowspan="2">&nbsp;<%=projectData.pjtPmName %></td>
                            <td class="tdwhiteM">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "00798") %><%--계획--%></td>
                            <%
                            Vector taskVecA = ProjectTaskHelper.manager.getLevelOneTask(project);
                            for(int a = 0; a < taskVecA.size(); a++) {
                                JELTask task = (JELTask) CommonUtil.getObject((String)taskVecA.get(a));
                                JELTaskData taskData = new JELTaskData(task);
                            %>
                            <td class="tdwhiteM">
                                <%=DateUtil.getDateString(taskData.taskPlanStartDate, "d") %> ~ <%=DateUtil.getDateString(taskData.taskPlanEndDate, "d") %>
                            </td>
                            <%
                            }
                            %>
                        </tr>

                        <tr>
                            <td class="tdwhiteM">&nbsp;<%=messageService.getString("e3ps.message.ket_message", "02724") %><%--진척--%></td>
                            <%
                            Vector taskVecC = ProjectTaskHelper.manager.getLevelOneTask(project);
                            for(int c = 0; c < taskVecC.size(); c++) {
                                JELTask task = (JELTask) CommonUtil.getObject((String)taskVecC.get(c));
                                JELTaskData taskData = new JELTaskData(task);
                            %>
                            <td class="tdwhiteM">
                                <!-- 진행률 그림 -->
                                <jsp:include page="/jsp/project/ProjectProgressBarView.jsp" flush="false">
                                    <jsp:param name="planValue" value="<%=taskData.getPreferCompStr()%>"/>
                                    <jsp:param name="workValue" value="<%=taskData.taskCompletion%>"/>
                                </jsp:include>
                                <!-- //진행률 그림 -->
                            </td>
                            <%
                            }
                            %>
                        </tr>
                    <%	} %>






                    <%} %>
                </table>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
