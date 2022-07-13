<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="e3ps.project.*,
                 e3ps.project.beans.*,
                 e3ps.common.util.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request" />
<jsp:setProperty name="wtcontext" property="request" value="<%= request %>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String oid = request.getParameter("oid");					//E3PSProject OID
    String issue = request.getParameter("issue");				//일정이력 Type
    String description = request.getParameter("description");	//일정이력 설명

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    //HistoryManager history = ScheduleHistoryHelper.manager.saveHistory(project,issue,description);
    //ScheduleHistoryHelper.manager.saveHistory(project,history);
%>
<html>
<head>
</head>
<body onLoad="javascript:goPage()"></body>
</html>
<script language="JavaScript">
function goPage() {
    alert('<%=messageService.getString("e3ps.message.ket_message", "02272") %><%--이력이 저장되었습니다--%>');
    self.close();
}
</script>
