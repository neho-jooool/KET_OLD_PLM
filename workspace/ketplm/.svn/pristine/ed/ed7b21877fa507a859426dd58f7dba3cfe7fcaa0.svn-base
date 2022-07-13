<%@ page contentType="text/html;charset=UTF-8"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<html>
<head>
<title>PLM</title>
</head>

<%
    String type = request.getParameter("type");

    //Kogger.debug("type =================="+ type);
%>
<form method=post target=contName >
</form>
    <frameset cols="170,*" border="0" bordercolor="#808080" frameborder="0" framespacing="0">
            <frame name="menu" scrolling="no" src="/plm/portal/common/project_submenu.jsp?name0=<%=type%>" noresize>
            <frame name="contName" scrolling="no" src="/plm/jsp/project/ListE3PSProject_AdvancedQuery.jsp?initType=<%=type%>" id=contName noresize>
    </frameset>
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>

