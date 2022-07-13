<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
	String oid = "";
	if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
		oid = request.getParameter("oid");
	}
//	Kogger.debug("oid = " + oid);
	String mode = "";

	if(request.getParameter("mode") != null && request.getParameter("mode").length() > 0){
		mode = request.getParameter("mode");
	}
//	Kogger.debug("mode = " + mode);
	if(mode.equals("delete")){
		MaterialHelper.deleteMaterial(oid);
	}else{
		String material = request.getParameter("material");
		String maker = request.getParameter("maker");
		String type = request.getParameter("type");
		String grade = request.getParameter("grade");

//		Kogger.debug("material = " + material);
//		Kogger.debug("maker = " + maker);
//		Kogger.debug("type = " + type);
//		Kogger.debug("grade = " + grade);

		HashMap map = new HashMap();

		map.put("oid", oid);
	    map.put("material", material);
	    map.put("materialMaker", maker);
	    map.put("materialType", type);
	    map.put("grade", grade);

	    MaterialHelper.createMaterial(map);
	}
%>

<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.material.beans.MaterialHelper"%>
<html>
<head>
<title></title>
</head>
<body>
<script>
	<%if(mode.equals("delete")){
	%>
		alert("<%=messageService.getString("e3ps.message.ket_message", "01699") %><%--삭제되었습니다.--%>");
		self.close();
	<%
	}else if(mode.equals("modify")){
	%>
	alert("<%=messageService.getString("e3ps.message.ket_message", "01947") %><%--수정되었습니다.--%>");
	location.href="/plm/jsp/project/material/MaterialView.jsp?oid=<%=oid%>";
	<%
	}else{
	%>
	alert('<%=messageService.getString("e3ps.message.ket_message", "01324") %><%--등록되었습니다.--%>');
	location.href="/plm/jsp/project/material/ListMaterial.jsp";
	<%}%>
</script>
</body>
</html>
