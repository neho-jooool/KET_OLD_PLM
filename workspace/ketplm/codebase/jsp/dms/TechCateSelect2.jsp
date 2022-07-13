<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="e3ps.dms.service.KETDmsHelper,
                e3ps.common.util.StringUtil,
                java.util.ArrayList"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
	String cmd = StringUtil.checkNull(request.getParameter("cmd"));
	String categoryCode = StringUtil.checkNull(request.getParameter("oid"));
    String categoryName = null;
    if ( !categoryCode.equals("") ) {
        //categoryCode의 하위 카테고리를 배열형태로 구한다.
        ArrayList optList = KETDmsHelper.service.selectChildDocCategory(categoryCode);
%>
        <c:set var="optList" value="<%=optList %>" />
<%} %>

var pos = 0;
if ( "<%=cmd %>" == "create" ) {
    pos = 1;
}
<c:forEach var="items" items="${optList}" varStatus="i">
    document.form01.category3.length = pos + 1;
    document.form01.category3.options[pos].value  = "<c:out value="${items[0]}" />";
    document.form01.category3.options[pos++].text = "<c:out value="${items[1]}" />";
</c:forEach>
