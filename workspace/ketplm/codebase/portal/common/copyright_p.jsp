<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
-->
</style>
</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="10"><img src="../../portal/images/copyr_1.gif"></td>
    <td align="center" valign="middle"><!--<img src="../../portal/images/copyright.gif">--></td>
    <td align="center" valign="middle"><!--<table border="0" align="right" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="../images/btn_5.gif"></td>
        <td background="../images/btn_bg3.gif"><a href="javascript:parent.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
        <td width="10"><img src="../images/btn_6.gif"></td>
      </tr>
    </table>--></td>
    <td width="1"><img src="../../portal/images/copyr_3.gif"></td>
  </tr>
</table>
</body>
</html>
