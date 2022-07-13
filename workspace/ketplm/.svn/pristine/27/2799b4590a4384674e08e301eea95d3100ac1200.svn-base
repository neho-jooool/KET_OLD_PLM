<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="wt.fc.*,java.util.*,wt.query.*" %>
<%@page import = "e3ps.project.*,e3ps.project.beans.*,e3ps.common.util.*"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp" %>

<html>
<head>
<%@include file="/jsp/common/top_include.jsp" %>
<LINK rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css">
<script language="JavaScript">
function thiscolse(){
    //parent.opener.document.location.href = parent.opener.document.URL;
    parent.close();
}
</script>
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 5px;
    margin-right: 0px;
    margin-bottom: 0px;
}
-->
</style>
</head>
<body>

<%
String oid = request.getParameter("oid");
Object obj= CommonUtil.getObject(oid);
String title = "";
E3PSTask task = null;
E3PSProject project = null;
String pjtName = "";
if ( obj instanceof E3PSProject ) {
    project = (E3PSProject)obj;
    if(project.getPjtName() != null){
        pjtName = project.getPjtName();
    }
    title = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : " + pjtName;
} else if ( obj instanceof E3PSTask ) {
    task = (E3PSTask)obj;
    if(task.getProject().getPjtName() != null){
        pjtName = task.getProject().getPjtName();
    }
    title = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : " + pjtName;
}
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
        <td class="font_01"><%=title%></td>
        <td width="20"></td>
      </tr>
    </table></td>
    <td width="219"><img src="/plm/portal/images/logo_popup2.png"></td>
  </tr>
</table>
</body>
