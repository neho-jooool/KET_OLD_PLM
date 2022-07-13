
<html>
<head>
<title>PLM Portal</title>
</head> 
<%
String oemtype = request.getParameter("oemtype");
String codetype = request.getParameter("codetype");

%>
<frameset rows="1*" cols="200,*" border="0" frameborder="1" framespacing="0">

	<frame name="tree" scrolling="auto" marginwidth="5" marginheight="5" namo_target_frame="detail" src='/plm/jsp/project/projectType/ManageOEMTree.jsp?oemtype=<%=oemtype%>&codetype=<%=codetype%>'>

	<frame name="main" scrolling="yes" marginwidth="10" marginheight="14" src="/plm/jsp/project/projectType/ViewOEMType.jsp?oemtype=<%=oemtype%>&codetype=<%=codetype%>">
</frameset>
</html>
