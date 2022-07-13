<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="e3ps.common.web.ParamHash"%>
<%@page import="java.util.Vector"%>
<%@page import="e3ps.project.beans.ProjectTaskHelper"%>

<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="java.util.Hashtable"%>
<%@page import="e3ps.project.TemplateProject"%>
<%@page import="e3ps.project.E3PSProjectHelper"%>
<%@page import="wt.httpgw.WTURLEncoder"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>
<%@page import="e3ps.common.content.uploader.WBFile"%>
<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>
<%@page import="e3ps.project.wbsupload.WBSUpload"%>
<%@page import="e3ps.project.beans.ProjectScheduleHelper"%><html>


<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- jQuery -->
<script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script src="/plm/portal/js/multicombo/jquery-ui.min.js"></script>

<script language=JavaScript src="/plm/portal/js/org.js"></script>

</head>
<%
String type = request.getContentType();
if((type == null)||(!type.toLowerCase().startsWith("multipart/form-data"))) {
    throw new IOException("TEST : Posted content type isn't multipart/form-data");
 }



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


//Kogger.debug("file = " + file);


String message = "false";
String oid = "";
String divide = (String)param.get("divide");
String command = (String)param.get("command");
String name = (String)param.get("name");
String duration = (String)param.get("duration");
String description = (String)param.get("description");
String pjtNo = (String)param.get("pjtNo");
String imsi = (String)param.get("imsi");
String ptType = (String)param.get("ptType");
String planType = (String)param.get("planType");
String assembling = (String)param.get("assembling");
String developType = (String)param.get("developType");
String makeType = (String)param.get("makeType");
String productType = (String)param.get("productType");
/* 2014.07.10 추가 컬럼*/
String division = (String)param.get("division");
String devType = (String)param.get("devType");
String devStep = (String)param.get("devStep");
String category = (String)param.get("type");
String clientCompany = (String)param.get("clientCompany");
String makeOffice = (String)param.get("makeOffice");
String moldType = (String)param.get("moldType");
String making = (String)param.get("making");
String activeType = "비활성";

if(command == null) {
	command = "";
}
if(name == null) {
	name = "";
}
if(duration == null) {
	duration = "";
}
if(description == null) {
	description = "";
}
if(pjtNo == null) {
	pjtNo = "";
}
if(imsi == null) {
	imsi = "";
}
if(planType == null) {
	planType = "";
}
if(assembling == null) {
	assembling = "";
}
if(developType == null) {
	developType = "";
}
if(makeType == null) {
	makeType = "";
}
if(productType == null) {
	productType = "";
}

if(ptType == null) {
	ptType = request.getParameter("ptType");
}
if(division == null){
    division = "";
}

if(category == null){
    category = "";
}

if(clientCompany == null){
    clientCompany = "";
}

if(devType == null){
    devType = "";
}

if(devStep == null){
    devStep = "";
}
if(makeOffice == null){
    makeOffice = "";
}

if(moldType == null){
    moldType = "";
}

if(making == null){
    making = "";
}

//Kogger.debug("fff == " + ptType);
//Kogger.debug("command = " + command);
//Kogger.debug("name = " + name);
//Kogger.debug("duration = " + duration);
//Kogger.debug("description = " + description);
//Kogger.debug("pjtNo = " + pjtNo);
//Kogger.debug("imsi = " + imsi);
//Kogger.debug("planType = " + planType);
//Kogger.debug("assembling = " + assembling);
//Kogger.debug("developType = " + developType);
//Kogger.debug("makeType = " + makeType);
//Kogger.debug("productType = " + productType);
//name= java.net.URLDecoder.decode(name,"euc-kr");
//duration= java.net.URLDecoder.decode(duration,"euc-kr");


//description= WTURLEncoder.decode(description);
//pjtNo= java.net.URLDecoder.decode(pjtNo,"euc-kr");
String errorMessage = "";
String tempid = null;
boolean isOpen = true;
if("init".equals(command)) {//Project Template 생성
	tempid = (String)param.get("tempid");//request.getParameter("tempid");
	if(tempid == null) {
		isOpen = false;
		tempid = "";
	}

	//Kogger.debug("tempid = " + tempid);

	tempid= java.net.URLDecoder.decode(tempid,"euc-kr");

	Hashtable hash = new Hashtable();
	hash.put("NAME", name);
	hash.put("DURATION", duration);
	hash.put("DESCRIPTION", description);
	hash.put("TEMPID", tempid);
	hash.put("IMSI",imsi);
	hash.put("PTTYPE",ptType);
	hash.put("PLANTYPE",planType);
	hash.put("ASSEMBLING",assembling);
	hash.put("DEVELOPTYPE",developType);
	hash.put("MAKETYPE",makeType);
	hash.put("PRODUCTTYPE",productType);
	hash.put("DEVTYPE",devType);
	hash.put("DIVISION",division);
	hash.put("DEVSTEP",devStep);
	hash.put("CATEGORY",category);
	hash.put("CLIENTCOMPANY",clientCompany);
	hash.put("MAKEOFFICE", makeOffice);
	hash.put("MOLDTYPE", moldType);
	hash.put("MAKING", making);
	hash.put("ACTIVETYPE", activeType);
	if(file != null){
		hash.put("file", file);
	}
	TemplateProject templateProject = null;
	boolean isError = false;

	try {
		templateProject = E3PSProjectHelper.service.createTemplateProject(hash);
		oid = CommonUtil.getOIDString(templateProject);
	}
	catch(Exception e) {
	    Kogger.error(e);
		isError = true;

		errorMessage = e.getCause().getLocalizedMessage();



	}

	if(templateProject != null && file != null) {
		ProjectScheduleHelper.manager.post_modify_template_duration(templateProject);
	}
}
%>

<body>
<form id="searchForm"></form>
<script>
	<%if(oid != null && oid.length() > 0){ %>
	<%-- alert("<%=messageService.getString("e3ps.message.ket_message", "00564") %>WBS가 생성되었습니다\nWBS 편집 화면으로 이동 합니다"); --%>
            alert("WBS가 생성되었습니다.");
            opener.search();
            var taskUrl = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=<%=oid%>";
            var wbsContentUrl = "/plm/jsp/project/template/TemplateProjectView.jsp?oid=<%=oid%>&type=<%=divide%>";
            opener.searchPopup(taskUrl,wbsContentUrl);  // opener는 새창을 띄운 부모창을 의미합니다. 
            self.close();
			<%-- parent.parent.movePaage('/plm/jsp/project/template/TemplateProjectViewLeftFrm.jsp?oid=<%=oid%>', '/plm/jsp/project/template/TemplateProjectView.jsp?oid=<%=oid%>'); --%>

	<%}else{%>
	alert("<%= errorMessage%>");
	location.href = "/plm/jsp/project/template/ProjectTempCreatePage.jsp";
	<%} %>
</script>
</body>
</html>
