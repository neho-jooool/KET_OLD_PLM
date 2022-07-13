<%@ page contentType="text/html;charset=UTF-8"%>

<%@page import = "e3ps.common.util.CommonUtil,
                  e3ps.ews.beans.EWSUtil"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

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
                  <td><img src="/plm/portal/images/subh_10.png"></td>
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
                  <!-- 초기유동관리검색 -->
                  <td><img src="/plm/portal//images/icon_2.gif"
                  ><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/ews/SearchEarlyWarning.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02816") %><%--초기유동관리검색--%></a></td>
                </tr>
              </table>
              <% if (EWSUtil.isQuality() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) { %>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10">&nbsp;</td>
                    <!-- 초기유동관리등록 -->
                    <td><img src="/plm/portal/images/icon_2.gif"
                    ><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/ews/CreateEarlyWarning.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02817") %><%--초기유동관리등록--%></a></td>
                  </tr>
                </table>
                <% } %>
                <% if(CommonUtil.isAdmin()) {  %>
                <table width="190" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10">&nbsp;</td>
                    <td><img src="/plm/portal/images/icon_2.gif"
                    ><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/ews/interfaceTest.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02329") %><%--인터페이스 테스트--%></a></td>
                  </tr>
                </table>
                <% } %>
                <table width="190" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10">&nbsp;</td>
                    <td><img src="/plm/portal/images/icon_2.gif"
                    ><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/lesson/CreateLessonLearn.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03444") %><%--습득교훈관리등록--%></a></td>
                  </tr>
                </table>
                <table width="190" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10">&nbsp;</td>
                    <td><img src="/plm/portal/images/icon_2.gif"
                    ><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/lesson/SearchLessonLearn.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03454") %><%--습득교훈관리 검색--%></a></td>
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
