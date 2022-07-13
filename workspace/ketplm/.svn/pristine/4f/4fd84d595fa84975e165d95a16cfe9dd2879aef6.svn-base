<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.project.E3PSTask"%>
<%@page import="e3ps.project.E3PSProject"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<%
    String oid = request.getParameter("oid");
    String popup = request.getParameter("popup");

    Object obj= CommonUtil.getObject(oid);
    String title = messageService.getString("e3ps.message.ket_message", "01012"); // 금형 Try
    E3PSTask task = null;
    E3PSProject project = null;

    if ( obj instanceof E3PSProject ) {
        project = (E3PSProject)obj;
        title = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : " + project.getPjtName();
    } else if ( obj instanceof E3PSTask ) {
        task = (E3PSTask)obj;
        title = messageService.getString("e3ps.message.ket_message", "03113")/*프로젝트명*/ + " : " + task.getProject().getPjtName();
    }
%>
<title></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=title %></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
