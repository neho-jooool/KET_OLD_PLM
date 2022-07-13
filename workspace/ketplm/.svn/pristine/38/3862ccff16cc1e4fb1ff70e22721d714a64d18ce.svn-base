<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="e3ps.common.file.ProjectScheduleFileUploader" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<%
	String fileFullPath = ProjectScheduleFileUploader.upload(request);

    fileFullPath = fileFullPath.replaceAll("\\\\", "/");
%>
<html>
<head>
<script type="text/javascript">
	function fnUploadResultSend() {

		parent.fnUploadResult("<%=fileFullPath%>");
	}
</script>
</head>

<body onload="javascript:fnUploadResultSend();">
</body>
</html>
