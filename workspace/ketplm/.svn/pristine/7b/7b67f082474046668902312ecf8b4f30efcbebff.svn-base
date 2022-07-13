<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/jsp/common/context.jsp"%>

<%
	String leftSrc = request.getParameter("leftSrc");
	String contSrc = request.getParameter("contSrc");
	String name0 = request.getParameter("name0");
	String selectoid = request.getParameter("selectoid");

	String docSrc = "";
	if( name0 != null ) {
		docSrc = contSrc + "&name0=" + name0 + "&selectoid=" + selectoid;
	} else {
		docSrc = contSrc;
	}

	if( leftSrc == null ) {
		leftSrc = "/plm/portal/common/main_submenu.jsp";
	}

	if( contSrc == null ) {
		docSrc = "/plm/portal/main.jsp";
	}
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>PLM</title>
<script>

function movePaage(leftSrc, contSrc) {
	// Modified by MJOH, 2011-03-10
	// 메인화면에서 more 버튼 클릭후, 뒤로가기 버튼 클릭시 2단계로 history back 되는 오류 수정

	//var menuObj = document.getElementById("bottomLeftFrame");
	//menuObj.src = leftSrc;
	//var mainObj = document.getElementById("contName");
	//mainObj.src = contSrc;

	parent.movePaage(leftSrc, contSrc);
}

function gotoMainPage() {
	//var menuObj = document.getElementById("bottomLeftFrame");
	//menuObj.src = '/plm/portal/common/main_submenu.jsp';
	//var mainObj = document.getElementById("bottomRightFrame");
	//mainObj.src = '/plm/portal/main.jsp';

	parent.gotoMainPage();
}

function viewContentPage(leftSrc, contSrc) {
	parent.viewContentPage(leftSrc, contSrc);
}
</script>
</head>

<form method=post target=cen >
<input type=hidden name="menuPage">
<input type=hidden name="centerPage">
</form>

<!-- NEW Start -->
<frameset cols=200,* framespacing=0 frameborder=no id=bottomFrame>
	<frame name=bottomLeftFrame noresize frameborder=no scrolling=no src="<%=leftSrc%>" id=bottomLeftFrame>
	<frame name=contName frameborder=no id=bottomRightFrame src="<%=docSrc%>">
</frameset>
<!-- NEW End -->

<noframes>
<body>
</body>
</noframes>
</html>
