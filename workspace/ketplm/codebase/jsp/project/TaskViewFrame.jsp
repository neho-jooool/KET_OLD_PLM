<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/jsp/common/context.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>PLM</title>
<%
String oid = request.getParameter("oid");
%>
</head>
<form method=post target=cen >
<input type=hidden name="menuPage">
<input type=hidden name="centerPage">
</form>
<frameset rows="77,*" framespacing="0" frameborder="no" border="0">

  <frame src="/plm/portal/common/top.jsp" frameborder="no" scrolling="no" noresize="noresize" marginwidth="0" marginheight="0" />
  <frame name="cen" src="ProjectViewFrm.jsp?oid=<%=oid%>" frameborder="no" scrolling="yes" noresize="noresize" marginwidth="0" marginheight="0" id="cen" />
  <frame src="/plm/portal/common/footer.jsp" frameborder="no" scrolling="no" noresize="noresize" marginwidth="0" marginheight="0" id="footer" />
  </frameset>

</frameset>
<noframes><body>
</body>
</noframes></html>
