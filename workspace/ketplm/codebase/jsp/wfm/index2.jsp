<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.net.*"%>

<%
String leftSrc = request.getParameter("leftSrc");
String contSrc = request.getParameter("contSrc");

if( leftSrc == null ) {
	leftSrc = "/plm/portal/common/groupware_submenu.jsp";
}

if( contSrc == null ) {
	contSrc = "/plm/portal/main.jsp";
}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>:: PLM Portal ::</title>

<script>

function movePaage(leftSrc, contSrc) {
	var cont = document.getElementById("contentFrame");
	cont.src = "/plm/portal/content.jsp?leftSrc=" + leftSrc + "&contSrc=" + contSrc;
}

function gotoMainPage() {
	var cont = document.getElementById("contentFrame");
	cont.src = "/plm/portal/content.jsp?leftSrc=/plm/portal/common/main_submenu.jsp&contSrc=/plm/portal/main.jsp";
}

function viewContentPage(leftSrc, contSrc) {
	var cont = document.getElementById("contentFrame");
	cont.src = "/plm/portal/viewContent.jsp?leftSrc=" + leftSrc + "&contSrc=" + contSrc;
}

function viewContentPage2(leftSrc, contSrc) {
	var cont = document.getElementById("contentFrame");
	cont.src = "/plm/portal/viewContent2.jsp?leftSrc=" + leftSrc + "&contSrc=" + contSrc;
}

</script>
</head>
<form method=post target=cen >
<input type=hidden name="menuPage">
<input type=hidden name="centerPage">
</form>

<frameset rows="90,*" framespacing=0 frameborder=no border=0 id=topFrame>
  <frame name=topMenuFrame noresize frameborder=no scrolling=no id=topMenuFrame src="/plm/portal/common/top.jsp">
  <frame name=contentFrame noresize frameborder=no scrolling=no id=contentFrame src="/plm/portal/content.jsp?leftSrc=<%=leftSrc %>&contSrc=<%=URLEncoder.encode(contSrc) %>">
</frameset>

<noframes>
<body>
</body>
</noframes>
</html>
