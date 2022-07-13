<%@ page contentType="text/html;charset=UTF-8"%>
<%
String left_url = "/plm/jsp/project/trySchdule/CalendarSub.jsp";
String main_url = "/plm/jsp/project/trySchdule/TryDaily.jsp?tagetDay="+e3ps.common.util.DateUtil.getToDay("yyyy-MM-dd");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>PLM</title>
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>
</head>
<table style="width: 100%;height:100%;valign:top" border="0" cellspacing="0" cellpadding="0">
	<tr valign="top">
	    <td width="15%">
			<iframe id=bottomLeftFrame name=bottomLeftFrame src="<%=left_url%>" frameborder="0" style="width: 100%; height: 100%" leftmargin="0" topmargin="0" scrolling="auto"></iframe>
	    </td>
	    <td width="85%">
			<iframe id=contName name=contName src="<%=main_url%>" frameborder="0" style="width: 100%; height: 100%" leftmargin="0" topmargin="0" scrolling="auto"></iframe>
	    </td>
	</tr>
</table>
<!-- NEW Start -->
<!-- <frameset cols=200,* framespacing=0 frameborder=no id=bottomFrame> -->
<%--     <frame id=bottomLeftFrame name=bottomLeftFrame noresize frameborder=no scrolling=no src="<%=left_url%>"> --%>
<%--     <frame id=contName name=contName frameborder=no src="<%=main_url %>"> --%>
<!-- </frameset> -->
<!-- NEW End -->
</html>