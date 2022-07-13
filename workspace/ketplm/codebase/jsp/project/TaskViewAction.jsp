<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    //String mode = request.getParameter("mode");

    String popup = request.getParameter("popup");

    String printPopup = "";

    if("popup".equals(popup)){
        printPopup = "&popup=popup";
    }

    String oid = request.getParameter("oid");
    String checkDescPoint = request.getParameter("checkDescPoint");
    String nonPassPoint = request.getParameter("nonPassPoint");
    String checkResult = request.getParameter("checkResult");
    
    String checkDescPoint_i = request.getParameter("checkDescPoint_i");
    String nonPassPoint_i = request.getParameter("nonPassPoint_i");
    String checkResult_i = request.getParameter("checkResult_i");
    
    String checkEtc = request.getParameter("checkEtc");


    E3PSTask task = (E3PSTask)CommonUtil.getObject(oid);

    if(checkDescPoint != null && checkDescPoint.length() > 0){
        task.setCheckDescPoint(checkDescPoint);
    }else{
        task.setCheckDescPoint("");
    }

    if(nonPassPoint != null && nonPassPoint.length() > 0){
        task.setNonPassPoint(nonPassPoint);
    }else{
        task.setNonPassPoint("");
    }

    if(checkResult != null && checkResult.length() > 0){
        task.setCheckResult(checkResult);
    }else{
        task.setCheckResult("");
    }
    
    if(checkDescPoint_i != null && checkDescPoint_i.length() > 0){
        task.setCheckDescPoint_i(checkDescPoint_i);
    }else{
        task.setCheckDescPoint_i("");
    }

    if(nonPassPoint_i != null && nonPassPoint_i.length() > 0){
        task.setNonPassPoint_i(nonPassPoint_i);
    }else{
        task.setNonPassPoint_i("");
    }

    if(checkResult_i != null && checkResult_i.length() > 0){
        task.setCheckResult_i(checkResult_i);
    }else{
        task.setCheckResult_i("");
    }

    if(checkEtc != null && checkEtc.length() > 0){
        task.setCheckEtc(checkEtc);
    }else{
        task.setCheckEtc("");
    }

    task = (E3PSTask)TaskHelper.manager.modifyTask(task);

%>


<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.beans.TaskHelper"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<html>
<head>
<title></title>
</head>
<body>
<form>
<script>

        alert("<%=messageService.getString("e3ps.message.ket_message", "02460") %><%--저장되었습니다--%>");

        location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%><%=printPopup%>";

</script>
</form>
</body>
</html>
