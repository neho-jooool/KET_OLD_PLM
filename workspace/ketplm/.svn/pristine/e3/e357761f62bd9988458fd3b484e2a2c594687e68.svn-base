<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.beans.TaskHelper"%>
<%@page import="java.util.Calendar"%>
<%@page import="e3ps.project.ExtendScheduleData"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="wt.fc.PersistenceHelper"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>
<%@page import="java.sql.Timestamp"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    String oid = "";

    if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
        oid = request.getParameter("oid");
    }

    E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);

    String reason = "";
    if(request.getParameter("reason") != null && request.getParameter("reason").length() > 0){

    	reason = request.getParameter("reason");
        task.setCompReason(reason);

        if(!"update".equals(request.getParameter("cmd"))){
        	int com = 100;
            task.setTaskCompletion(com);

            //종료일
            ExtendScheduleData schedule = (ExtendScheduleData)task.getTaskSchedule().getObject();
            Timestamp ts = DateUtil.getCurrentTimestamp();
            schedule.setExecEndDate(ts);
            schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);

            task = (E3PSTask)ProjectTaskHelper.updateCompletion(task);
        }


        task = (E3PSTask)TaskHelper.manager.modifyTask(task);
        //Kogger.debug("modi = " + task);
    }
%>

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
opener.document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
window.close();

</script>

</form>
</body>
</html>
