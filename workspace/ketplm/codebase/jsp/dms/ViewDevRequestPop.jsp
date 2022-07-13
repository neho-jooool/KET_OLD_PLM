<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%@page import = "e3ps.common.web.ParamUtil"%>

<%
    String oid = ParamUtil.getParameter(request, "oid");
    //var url="../../jsp/dms/ViewDevRequestPop.jsp?oid=e3ps.dms.entity.KETDevelopmentRequest:162120";
    //openWindow(url,"","830","600","status=1,scrollbars=no,resizable=no");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><iframe src='/plm/jsp/dms/ViewDevRequest.jsp?oid=<%=oid%>&isPop=Y' id="list" name="list" frameborder="0" width="100%" height="100%" style="margin:0;" scrolling="auto"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
