<%@page import="e3ps.project.moldPart.beans.MoldPartHelper"%>
<%@page import="java.io.File"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
	FormUploader uploader = FormUploader.newFormUploader(request);
	Hashtable param = uploader.getFormParameters();

	Vector v = uploader.getFiles();

	WBFile fileName = null;
	if(v != null && v.get(0) != null){
		fileName = (WBFile)v.get(0);
	}

	File file  = null;
	if(fileName != null){
		file  = fileName.getFile();
	}

	Hashtable hash = new Hashtable();
	if(file != null){
		Kogger.debug("filename == " + file.getName());
		hash = MoldPartHelper.uploadExcel(file);
	}

	String result = (String)hash.get("result");

	String projectOid = (String)param.get("projectOid");
	String managerOid = (String)param.get("managerOid");

%>
<html>
<head>
<title>Excel Upload</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">

<%
if("true".equals(result)){
%>
	alert("수정되었습니다.");
	opener.goViewPage('<%=projectOid %>', '<%=managerOid %>');
<%
}else{
	String message = (String)hash.get("message");
%>
	alert("<%=message %>");
<%
}
%>
self.close();

</script>
</head>
<body>
</body>
</html>
