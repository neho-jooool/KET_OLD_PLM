<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.*,
				wt.util.*" %>
<%@page import="e3ps.common.util.*,
				e3ps.project.*" %>
<%@include file="/jsp/common/context.jsp" %>
<%
	String oid = request.getParameter("oid");
	Hashtable hash = new Hashtable();
	hash.put("OID",oid);
	Object obj = (Object)CommonUtil.getObject(oid);
	if(obj instanceof E3PSProject) {
		E3PSProjectHelper.service.deleteE3PSProject(hash);
	} else if(obj instanceof TemplateProject) {
		E3PSProjectHelper.service.deleteTemplateProject(hash);
	}
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00511") %><%--Template 삭제--%></title>
</head>
<body onLoad="javascript:goPage()">
<form method="post">
</form>
</body>
</html>
<script language="JavaScript">
<!--
function goPage() {
<%	//if (project instanceof E3PSProject ) {	%>
	parent.document.location.href = "/plm/portal/main.jsp";
<%	//} else {	%>
	//parent.document.location.href = "/plm/portal/main.jsp";
<%	//}	%>
	self.close();
}
//-->
</script>
