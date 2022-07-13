<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="e3ps.project.beans.CreateMold"%>
<html>
<%
	String mOid = request.getParameter("mOid");
	if(mOid == null || mOid.length() == 0){
		out.println("mOid=");
		return;
	}
	
	CreateMold.createMoldProject(mOid, "2010-11-02", "");
	out.println("success");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
