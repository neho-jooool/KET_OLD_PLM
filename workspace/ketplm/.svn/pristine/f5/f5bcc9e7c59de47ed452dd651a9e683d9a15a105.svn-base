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

	String mode = "";
	if(request.getParameter("mode") != null && request.getParameter("mode").length() > 0){
		mode = request.getParameter("mode");
	}
	if(mode.equals("delete")){
		MachineHelper.deleteMachine(oid);
	}else{
		String moldType = request.getParameter("moldType");
		String maker = request.getParameter("maker");
		String type = request.getParameter("type");
		String ton = request.getParameter("ton");
		String model = request.getParameter("model");

		Kogger.debug("moldType = " + moldType);
		Kogger.debug("maker = " + maker);
		Kogger.debug("type = " + type);
		Kogger.debug("ton = " + ton);
		Kogger.debug("model = " + model);

		HashMap map = new HashMap();

		map.put("oid", oid);
	    map.put("moldType", moldType);
	    map.put("machineMaker", maker);
	    map.put("machineType", type);
	    map.put("ton", ton);
	    map.put("model", model);

	    MachineHelper.createMachine(map);
	}
%>

<%@page import="java.util.HashMap"%>
<%@page import="e3ps.project.machine.benas.MachineHelper"%>
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
	<%}else if(mode.equals("modify")){%>
	alert("<%=messageService.getString("e3ps.message.ket_message", "01947") %><%--수정되었습니다.--%>");
	location.href="/plm/jsp/project/machine/MachineView.jsp?oid=<%=oid%>";
	<%
	}else{
	%>
	alert('<%=messageService.getString("e3ps.message.ket_message", "01324") %><%--등록되었습니다.--%>');
	location.href="/plm/jsp/project/machine/ListMachine.jsp";
	<%}%>
</script>
</body>
</html>
