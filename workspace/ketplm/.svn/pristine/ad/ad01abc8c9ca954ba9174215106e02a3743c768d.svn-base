<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "e3ps.common.web.ParamUtil" %>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    String oid = ParamUtil.getParameter(request, "oid");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language='javascript'>
<!--

    function search(){
        var oid = document.forms[0].oid.value;

        document.forms[0].target = "list";
        document.forms[0].action = '/plm/jsp/ews/EarlyWarningEndList.jsp?oid='+oid;
        document.forms[0].submit();
    }

//-->
</script>
</head>
<body onload="javascript:search();">
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%>>
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="../../portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03194") %><%--해제신청서 목록--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td width="40" class="tdblueM">No</td>
                <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
                <td width="90" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02835") %><%--최근 수정일--%></td>
                <td width="80" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02729") %><%--진행단계--%></td>
                <td width="80" class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
              </tr>
              <tr>
                  <td colspan="6">
                      <iframe src="" id="list" name="list" frameborder="0" width="100%" height="220" style="margin:0;" scrolling="auto"></iframe>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space10"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
