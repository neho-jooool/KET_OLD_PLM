<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/jsp/common/context.jsp"%>
<%
	String menuPage = request.getParameter("menuPage");
	String centerPage = request.getParameter("centerPage");
%>
<head>
<title>PLM</title>
</head>
<form>

</form>
    <frameset cols="170,*" framespacing="0" frameborder="no" border="0">
      <frame name="menu" src="<%=menuPage%>" frameborder="no" scrolling="no" marginwidth="0" marginheight="0" id="left" noresize />
      <frame name="contName" src="<%=centerPage%>" frameborder="no" scrolling="yes" marginwidth="0" marginheight="0" id="cont" noresize />
    </frameset> 

<noframes><body>
</body>
</noframes></html>
