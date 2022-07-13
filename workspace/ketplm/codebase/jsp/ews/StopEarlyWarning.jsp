<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "e3ps.common.web.ParamUtil"%>

<%
    String stepOid = ParamUtil.getParameter(request, "stepOid");
    String oid = ParamUtil.getParameter(request, "oid");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<style type="text/css">
body {
    margin-left: 5px;
    margin-top: 0px;
    margin-right: 5px;
    margin-bottom: 0px;
}
</style>
<script language='javascript'>
<!--
  //저장
  function save(){
      if (isNullData(document.forms[0].stopReason.value)){
          alert('<%=messageService.getString("e3ps.message.ket_message", "02690") %><%--중단사유를 입력하시기 바랍니다--%>');
          return;
      }

      if(confirm('<%=messageService.getString("e3ps.message.ket_message", "02692") %><%--중단하시겠습니까?--%>')){
          showProcessing();
          disabledAllBtn();

          document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningStepServlet?cmd=stop';
          document.forms[0].encoding = 'multipart/form-data';
          document.forms[0].submit();
      }
  }
//-->
</script>

</head>
<body>
<form method="post">

<!-- hidden begin -->
<input type='hidden' name='oid' value=<%=oid%> >
<input type='hidden' name='stepOid' value=<%=stepOid%> >
<!-- hidden end -->

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="../../portal/images/logo_popupbg.png">
                <table height="28" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="7"></td>
                    <td width="20"><img src="../../portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02689") %><%--중단 사유--%></td>
                  </tr>
                </table>
              </td>
            <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
          </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:save();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                    <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                  </tr>
                </table>
              </td>
          </tr>
        </table>
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
              <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td  class="space5"></td>
              </tr>
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td width="100" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02689") %><%--중단 사유--%></td>
                <td width="*" colspan="3" class="tdwhiteL0" style="height:200">
                    <textarea name="stopReason" class="txt_field" style="width:100%; height:96%"></textarea>
                </td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
        <table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
