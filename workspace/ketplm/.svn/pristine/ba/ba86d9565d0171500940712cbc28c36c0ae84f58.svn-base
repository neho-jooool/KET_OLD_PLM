癤?%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.web.ParamUtil"%>
<%
String oid = request.getParameter("oid");
String from = ParamUtil.checkStrParameter(request.getParameter("from"));

if(oid==null){
    %>
            <jsp:forward page="/plm/jsp/groupware/help/faq/SearchBoard.jsp?from=<%=from %>"></jsp:forward>
    <%
    return;
}

%>
        <jsp:include page="/jsp/groupware/help/faq/ViewBoard.jsp?from=<%=from %>" flush="false"/>
