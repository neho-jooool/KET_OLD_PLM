<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
<tr>
  <table width="200" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="190" height="100%" valign="top"><table width="190" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="44"><table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/subh_14.png"></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td valign="top">
                <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal//images/icon_2.gif">
                    <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/dms/SearchDevRequest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00600") %><%--개발(검토)의뢰 검색--%></a></td>
                </tr>
              </table>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10">&nbsp;</td>
                    <td><img src="/plm/portal/images/icon_2.gif">
                        <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/dms/CreateDevRequest.jsp&key=step&value=R" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00618") %><%--개발검토의뢰 등록--%></a></td>
                  </tr>
                </table>
                <table width="190" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10">&nbsp;</td>
                    <td><img src="/plm/portal/images/icon_2.gif">
                        <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/dms/CreateDevRequest.jsp&key=step&value=D" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00657") %><%--개발의뢰 등록--%></a></td>
                  </tr>
                </table>
            </td>
          </tr>
          <tr>
            <td height="118" align="center"><table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/banner_1.gif"></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
                <tr>
                  <td height="70" align="center" valign="top" background="../images/banner_2.gif"><table width="150" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                      <td height="31">&nbsp;</td>
                    </tr>
                    <tr>
                      <td>(032)850-1369(PMS)</td>
                    </tr>
                    <tr>
                      <td>(032)850-1304(PDM)</td>
                    </tr>
                    <tr>
                      <td><%=messageService.getString("e3ps.message.ket_message", "00001") %><%--(032)850-1154(시스템)--%></td>
                    </tr>
                </table></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
      <td width="1" bgcolor="#c6c6c6"></td>
    </tr>
  </table>
</body>
</html>
