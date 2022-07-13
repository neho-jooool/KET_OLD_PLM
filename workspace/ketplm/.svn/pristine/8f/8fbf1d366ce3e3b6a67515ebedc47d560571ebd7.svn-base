
<html>
<head>
<title></title>
</head> 
<%
String oemtype = request.getParameter("oemtype");
String codetype = request.getParameter("codetype");
String mode = request.getParameter("mode");
String cbMethod = request.getParameter("cbMethod");
String valueField = request.getParameter("valueField");
String displayField = request.getParameter("displayField");
%>
<frameset rows="1*" cols="200,*" border="0" frameborder="1" framespacing="0">

	<frame name="tree" scrolling="auto" marginwidth="5" noresize marginheight="5" namo_target_frame="detail" src="/plm/jsp/project/projectType/SelectOEMTree.jsp?oemtype=<%=oemtype %>&codetype=<%=codetype%>&mode=<%=mode%>&cbMethod=<%=cbMethod%>&valueField=<%=valueField%>&displayField=<%=displayField%>">

	<frame name="main" scrolling="yes" marginwidth="10" noresize marginheight="14" src="/plm/jsp/project/projectType/SelectOEMTypeList.jsp?oemtype=<%=oemtype%>&codetype=<%=codetype%>&mode=<%=mode%>&cbMethod=<%=cbMethod%>&valueField=<%=valueField%>&displayField=<%=displayField%>">
</frameset>
</html>
