<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<html>
<body>
    <form name="mainform" method="post" action="">
        <input type="hidden" name="oid" value="<%=request.getParameter("oid")%>" />
    </form>
</body>
<script language="javascript">
    var form = document.forms[0];
    form.method="post";
    form.action="/plm/jsp/edm/ViewEPMDocument.jsp";
    form.submit();
</script>
</html>