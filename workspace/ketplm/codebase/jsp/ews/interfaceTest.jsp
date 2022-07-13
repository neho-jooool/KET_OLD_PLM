<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
-->
</style>

<script language='javascript'>
<!--

    // 협력사 인터페이스
    function vendorInterface(){
        disabledAllBtn();
        showProcessing();

        document.forms[0].action =  '/plm/servlet/e3ps/PartnerServlet?cmd=interface';
        document.forms[0].submit();
    }

    // 불량유형 인터페이스
    function problemCodeInterface(){
        disabledAllBtn();
        showProcessing();

        document.forms[0].action =  '/plm/servlet/e3ps/EarlyWarningResultServlet?cmd=interface';
        document.forms[0].submit();
    }

//-->
</script>

</head>
<body">
<form method="post">
<table width="780" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
        <table width="780" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
              <table width="780" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="../../portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02329") %><%--인터페이스 테스트--%></td>
                <td align="right"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
      <table width="780" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="center">
              <table border="0" cellspacing="0" cellpadding="0">
                <tr height="30">
                    <td></td>
                </tr>
                <tr>
                  <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:vendorInterface();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03212") %><%--협력사 인터페이스--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                     </td>
                </tr>
                <tr height="30">
                    <td></td>
                </tr>
                <tr>
                    <td>
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:problemCodeInterface()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01625") %><%--불량유형 인터페이스--%></a></td>
                          <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                        </tr>
                      </table>
                  </td>
                </tr>
              </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
