
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
	String targetType = request.getParameter("targetType");
    String oid = "";

    if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
        oid = request.getParameter("oid");
    }

    String completed = request.getParameter("completed");

    ProjectOutput output = (ProjectOutput)CommonUtil.getObject(oid);
    e3ps.project.E3PSTask task = (e3ps.project.E3PSTask) output.getTask();
    String reason = "";
    if(request.getParameter("reason") != null && request.getParameter("reason").trim().length() > 0){
        int com = 100;


        reason = request.getParameter("reason").trim();

        output.setComplete_reason(reason);
        output.setOwner(SessionHelper.manager.getPrincipalReference());
        //DateUtil.getCurrentTimestamp();

        if("completed".equals(completed)){
            output.setEtcCompletion(DateUtil.getCurrentTimestamp());
            output.setCompletion(100);
        }
        //실제 종료일 등록
        ProjectTaskHelper.manager.updateCompletion(output);
        //}
    }

%>

<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.TaskHelper"%>
<%@page import="java.util.Calendar"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%><%@page import="e3ps.project.ProjectOutput"%>
<%@page import="wt.session.SessionHelper"%>
<html>
<head>

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}
-->
</style>

</head>
<body>
<form>
<input type="hidden" name="reason" value="<%=reason %>"></input>

<script type="text/javascript">

alert("<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>");
<%
	if("task".equals(targetType)) {
%>
    opener.parent.document.location.href = "/plm/jsp/project/ProjectViewFrm.jsp?oid=<%=CommonUtil.getOIDString(task) %>&popup=popup";
<%
	}else {
%>
	opener.location.reload();
<%
	}
%>

self.close();
</script>

</form>
</body>
</html>
