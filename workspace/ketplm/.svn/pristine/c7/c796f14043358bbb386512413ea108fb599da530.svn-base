<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<%@page import="e3ps.load.groupware.LoadDepartment"%>
<%@page import="e3ps.load.groupware.LoadPeople"%>
<head>
<%

  LoadDepartment.interfaceTableSync();  // EHR Sync Department
  LoadPeople.interfaceTableSync();    // EHR Sync People

%>
<script>
    alert("<%=messageService.getString("e3ps.message.ket_message", "01302") %><%--동기화 되었습니다--%>");
    self.close();

</script>
<title>EHR Sync</title>
</head>
<body>

</body>
</html>
