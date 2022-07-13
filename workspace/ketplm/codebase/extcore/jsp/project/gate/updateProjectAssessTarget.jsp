<%@page contentType="text/html; charset=UTF-8"%>
<%
String projectOid = request.getParameter("projectOid");
String oid = request.getParameter("oid");
%>
<script  language="javascript">
location.replace("/plm/ext/project/gate/updateProjectAssessForm.do?pjtOid=<%=oid%>&oid=<%=oid%>");
</script>