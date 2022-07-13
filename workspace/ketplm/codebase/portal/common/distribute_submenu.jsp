<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="e3ps.common.util.DateUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=messageService.getString("e3ps.message.ket_message", "01254") %><%--도면 배포 관리--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<style type="text/css">
<!--

-->
</style>
</head>
<%
  wt.org.WTUser user = (wt.org.WTUser)wt.session.SessionHelper.manager.getPrincipal();
  String str_isAdmin = "key=isadmin&value="+e3ps.common.util.CommonUtil.isAdmin()+"&key=username&value="+user.getFullName();
  String server = e3ps.common.jdf.config.ConfigEx.getInstance("distribute").getString("distribute.server.url");
  String auth = request.getHeader("authorization");
  auth = auth.substring( "Basic ".length() );
  String authDecoded = com.infoengine.util.Base64.decode(auth);
  String usr = authDecoded.substring( 0, authDecoded.indexOf(":") );
  String pwd = authDecoded.substring( authDecoded.indexOf(":")+1 );
  System.out.println(user + ":" + pwd);
  System.out.println(server);
%>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="../images/img_default/left/left_img_top2.gif" width="100%" height="24"></td>
  </tr>
</table>
<table width="100%" height="33" border="0" cellpadding="0" cellspacing="0" bgcolor="#A67C51" style="margin:0px 0px 10px 0px">
  <tr>
    <td width="30" align="center"><img src="../images/img_default/icon/icon_06.gif" width="11" height="11"></td>
    <td class="font_06leftmenu"><%=messageService.getString("e3ps.message.ket_message", "01254") %><%--도면 배포 관리--%></td>
    <td width="10"><!--img src="../images/img_default/left/side_01.gif" width="10" height="33"--></td>
  </tr>
</table>


<table width="170" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">

  <%
    boolean isAdmin = e3ps.common.util.CommonUtil.isAdmin();
    boolean isExecutiveAuth = e3ps.auth.beans.E3PSAuthHelper.manager.isExecutiveAuth();

    boolean isDst = e3ps.auth.beans.E3PSAuthHelper.manager.isAuth("DST", "도면배포", "A");
    if(isDst || isExecutiveAuth || isAdmin) {
  %>
  <!-- 도면배포 -->
  <tr>
    <td height="20" style="padding-left:10">
      <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
      <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/cpc/CreateDistProcess.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01478") %><%--배포등록--%></a>
    </td>
  </tr>
  <tr>
    <td height="20" style="padding-left:10">
      <img src="../images/img_default/icon/arrow_02.gif" width="11" height="11">
      <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/cpc/ListDistribute.jsp?mode=all" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01477") %><%--배포 전체 목록--%></a>
    </td>
  </tr>


  <%  }
  %>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:10px">
  <tr>
    <td><img src="../images/img_default/left/left_bg_line.gif" width="100%" height="3"></td>
  </tr>
</table>
</body>
</html>
