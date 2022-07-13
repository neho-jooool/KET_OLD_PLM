<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.editor.CommonFileUploader" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
    request.setCharacterEncoding("utf-8");
    String uploadUrl = CommonFileUploader.upload(request);
%>

<head>
<script language="javascript">
    function fnUploadResultSend() {

        parent.fnUploadResult("<%=uploadUrl%>");
    }
</script>
</head>

<body onload="javascript:fnUploadResultSend();">
</body>
</html>
