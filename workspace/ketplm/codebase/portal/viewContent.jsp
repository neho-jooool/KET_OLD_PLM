<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/jsp/common/context.jsp"%>
<%
	String leftSrc = request.getParameter("leftSrc");
	String contSrc = request.getParameter("contSrc");

	if(leftSrc == null) {
		leftSrc = "/plm/portal/common/groupware_submenu.jsp";
	}

	if(contSrc == null) {
		contSrc = "main.jsp";
	}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>PLM</title>
<script>

function movePaage(leftSrc, contSrc) {
	parent.movePaage(leftSrc, contSrc);
}

function gotoMainPage() {
	parent.gotoMainPage();
}

function viewContentPage(leftSrc, contSrc) {
	var menuObj = document.getElementById("bottomLeftFrame");
	menuObj.src = leftSrc;
	var mainObj = document.getElementById("bottomRightFrame");
	mainObj.src = contSrc;

	//parent.viewContentPage(leftSrc, contSrc);
}
function viewContentPage2(leftSrc, contSrc) {
	var menuObj = document.getElementById("bottomLeftFrame");
	menuObj.src = leftSrc;
	var mainObj = document.getElementById("bottomRightFrame");
	mainObj.src = contSrc;

	//parent.viewContentPage(leftSrc, contSrc);
}
</script>
</head>
<form method=post target=cen >
<input type=hidden name="menuPage">
<input type=hidden name="centerPage">
</form>
<!-- NEW Start #4682B4/#5F9EA0-->
<frameset cols=230,* framespacing='1px' frameborder=no id=bottomFrame>
	<frame name=bottomLeftFrame frameborder=no  scrolling='no' src="<%=leftSrc%>" id=bottomLeftFrame style="border-right-width:3px;border-right-style:solid;border-right-color:#191970">
	<frame name=contName frameborder=no id=bottomRightFrame src="<%=contSrc%>">
</frameset>
<!-- NEW End -->
</frameset>
<noframes><body>
</body>
</noframes></html>
