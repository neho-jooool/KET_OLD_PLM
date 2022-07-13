<%@page contentType="text/html; charset=UTF-8"%>
<%@page import = "java.util.ArrayList,
                java.util.Vector"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="../../portal/js/org.js"></script>
<script language=JavaScript src="../../portal/js/common.js"></script>
<link rel="stylesheet" href="../../portal/css/e3ps.css" type="text/css">
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
<script language="JavaScript">
<!--

function isNull(str) {
    if(str==null||str==""){
        return true;
    }
    return false;
}


function doSave(){
//저장버튼 클릭시 valcheck체크후 hidden값인 dRCheckPoint값과 buyerCodeStr값을 지정하고 multipart/form-data형태로 submit한다.

    var d = document.forms[0];
    d.encoding = 'multipart/form-data';
    d.action = "/plm/jsp/dms/MigProjectDocumentExec.jsp";
    showProcessing();
    disabledAllBtn();
    d.submit();

}

//-->
</script>
<script id='dummy'></script>
</head>
<body>
<form name=form01 method="post" >
<table width="780" height="100" border="0" cellspacingf="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="780" border="0" cellspacing="0" cellpadding="0" >
       <tr>
          <td  class="head_line"></td>
       </tr>
       <tr>
          <td class="tdwhiteL0">&nbsp;</td>
          <td class="tdwhiteL0">&nbsp;</td>
          <td class="tdwhiteL0">&nbsp;</td>
       </tr>
       <tr>
          <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02118") %><%--엑셀파일선택--%></td>
          <td class="tdwhiteL"><table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <input name="iFile0" type="file" class="txt_field" style="width:570;">
                  </tr>
                </table></td>

              </tr>
          </table></td>
          <td>
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSave()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%>&nbsp;</a></td>
                      <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
        </tr>
        </table>
     </td>
  </tr>
  <tr>
      <td class="tdwhiteL" style="height:500"><textarea name="rOutput" class="txt_field" style="width:770; height:99%"></textarea></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><iframe src="../../portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
