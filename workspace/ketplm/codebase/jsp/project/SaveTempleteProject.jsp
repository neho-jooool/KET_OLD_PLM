<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@page import="java.util.*,
                wt.util.*" %>
<%@page import="e3ps.common.util.*,
                e3ps.project.*,
                e3ps.project.beans.*" %>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="request"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>"/>
<%
    Hashtable hash = new Hashtable();
    String oid = CharUtil.E2K(request.getParameter("oid"));
    String checkNull = "";
    E3PSProjectData projectData = new E3PSProjectData((E3PSProject)CommonUtil.getObject(oid));
    hash.put("NAME",request.getParameter("name"));
    hash.put("TEMPID",request.getParameter("oid"));

    TemplateProject templateproject =  E3PSProjectHelper.service.createTemplateProject(hash);
    if(templateproject == null){
        checkNull = "true";
    }


%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "03079") %><%--프로젝트 생성중--%></title>
</head>
<body onLoad="javascript:goPage()">
</body>
</html>
<script language="JavaScript">
<!--
function goPage() {
<%
    if(checkNull.equals("true")){
%>
    alert("<%=messageService.getString("e3ps.message.ket_message", "00515") %><%--Template 저장이 실패 하였습니다--%>");
    opener.document.location.reload();
    self.close();
<%
    }else{
%>
    alert("<%=messageService.getString("e3ps.message.ket_message", "00514") %><%--Template 저장 되었습니다--%>");
    opener.document.location.reload();
    self.close();
<%
    }
%>
}
//-->
</script>
