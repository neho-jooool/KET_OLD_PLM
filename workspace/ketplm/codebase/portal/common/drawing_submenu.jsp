<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>
<%
  String webAppName = CommonUtil.getWebAppName();
%>
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
<SCRIPT language=JavaScript src="/<%=webAppName%>/portal/js/common.js"></SCRIPT>
<script language="javascript">
<!-- //
function noDevAlert() {
  alert("<%=messageService.getString("e3ps.message.ket_message", "00595") %><%--개발 진행 중이거나 개발 전 기능입니다!!!--%>");
  return;
}
function goBatchEPM()
{
  var _url = "/<%=webAppName%>/jsp/edm/edmBatchCnt.jsp";
  openWindow(_url,"batchepm",300,300);
}
// -->
</script>
</head>
<body>
<tr>
  <table width="200" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="190" height="100%" valign="top"><table width="190" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="44"><table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/subh_15.png"></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td valign="top">
              <!-- 도면검색 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal//images/icon_2.gif">
                    <a href="/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/EDMServlet?command=openSearch" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01262") %><%--도면검색--%></a></td>
                </tr>
              </table>
              <!-- 제품도면등록 메뉴 -->
<%
      if( CommonUtil.isMember("자동차사업부_제품설계") || CommonUtil.isMember("전자사업부_제품설계") || CommonUtil.isAdmin()  || CommonUtil.isBizAdmin() )
            {
%>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                  <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&key=manageType&value=PRODUCT_DRAWING" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02587") %><%--제품도면 등록--%></a></td>
                </tr>
              </table>
<%
            }

            if( CommonUtil.isMember("자동차사업부_금형설계") || CommonUtil.isMember("전자사업부_금형설계") || CommonUtil.isAdmin()  || CommonUtil.isBizAdmin()  )
            {
%>
              <!-- 금형도면등록(단품) 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/edm/CreateEPMDocument.jsp&key=manageType&value=MOLD_DRAWING" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01061") %><%--금형도면등록(단품)--%></a></td>
                </tr>
              </table>
              <!-- 금형도면등록(일괄) 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="#" onClick="javascript:goBatchEPM();" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "01062") %><%--금형도면등록(일괄)--%></a></td>
                </tr>
              </table>
<%
            }

            if( CommonUtil.isMember("자동차사업부_제품설계") || CommonUtil.isMember("전자사업부_제품설계") ||
                CommonUtil.isMember("자동차사업부_금형설계") || CommonUtil.isMember("전자사업부_금형설계") ||
                CommonUtil.isAdmin()  || CommonUtil.isBizAdmin()  )
            {
            %>
         <!-- 일괄결재요청등록 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/wfm/CreateMultiApproval.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02340") %><%--일괄결재요청등록--%></a></td>
                </tr>
              </table>
<%
            }
%>
         <!-- 일괄결재요청검색 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/MultiApprovalServlet?cmd=search" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "02339") %><%--일괄결재요청검색--%></a></td>
                </tr>
              </table>
              <!-- 배포요청서 메뉴 -->
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/ext/edm/drawingDistRequestSearchList.do" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "03457") %><%--배포요청서--%></a></td>
                </tr>
              </table>
              <%if( CommonUtil.isAdmin() == true ){%>
              <table width="190" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="10">&nbsp;</td>
                  <td><img src="/plm/portal/images/icon_2.gif">
                    <a href="/plm/ext/edm/plmHpIfList.do" target="contName" class="leftmenu1">Interface 이력관리</a></td>
                </tr>
              </table>
              <%}%>              
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
