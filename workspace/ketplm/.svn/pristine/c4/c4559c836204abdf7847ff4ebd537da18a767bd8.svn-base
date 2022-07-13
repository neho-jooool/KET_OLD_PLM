
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    String oid = "";

    if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
        oid = request.getParameter("oid");
    }

    E3PSProject project = (E3PSProject)CommonUtil.getObject(oid);
    ProjectMaster projectMaster = null;
    if(project != null){
        projectMaster = project.getMaster();
    }
    if(projectMaster == null){
    %>
        <script type="text/javascript">
        alert('<%=messageService.getString("e3ps.message.ket_message", "03067") %><%--프로젝트 마스터 정보가 업습니다--%>');
        //opener.document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
        self.close();
        </script>
    <%
    }


    String reason = "";
    if(request.getParameter("reason") != null && request.getParameter("reason").length() > 0){
        ProductHistoryChage pc = ProductHistoryChage.newProductHistoryChage();
        pc.setMasterChange(projectMaster);
        pc.setChageDetil(request.getParameter("reason"));
        pc.setChageDate(DateUtil.getCurrentTimestamp());
        pc = (ProductHistoryChage)PersistenceHelper.manager.save(pc);
    }

%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSProject"%>
<%@page import="e3ps.project.ProjectMaster"%>
<%@page import="e3ps.project.ProductHistoryChage"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="wt.fc.PersistenceHelper"%>
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
//opener.document.location.href = "/plm/jsp/project/TaskView.jsp?oid=<%=oid%>";
self.close();
</script>

</form>
</body>
</html>
