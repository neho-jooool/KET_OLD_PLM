<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String mode = "";

    if(request.getParameter("mode") != null && request.getParameter("mode").length() > 0){
        mode = request.getParameter("mode");
    }

    String etc = "";
    if(request.getParameter("etc") != null && request.getParameter("etc").length() > 0){
        etc = request.getParameter("etc");
    }

    boolean isView = false;
    if("view".equals(mode)){
        isView = true;
    }
%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 10px;
    margin-right: 10px;
    margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">
function onSave(){
    var etcValue = document.forms[0].etc;
    window.returnValue = etcValue.value;
    window.close();
}

function closePopup(){
    window.returnValue= null;
    self.close();
}

</script>
</head>
<body>
<form>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01632") %><%--비고--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="470" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="460" border="0" cellspacing="0" cellpadding="0" >
              <tr><td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <%if(!isView){ %>
                <td><table border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onSave();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453") %><%--저장--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                </table></td>
                <td width="5">&nbsp;</td>
                <%} %>

              </table></td></tr>
            </table>
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
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                  <textarea name="etc" style="width:100%" rows="5" class="fm_area" id="etc" <%if(isView){ %>readOnly<%} %>></textarea>

                <!-- <input type="text" name="etc" class="txt_field"  style="width:100%;height:100%" id="etc" value="<%=etc%>" <%if(isView){ %>readOnly<%} %>> -->
              </tr>

            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="460">
              <tr>
                <td class="space15"></td>
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
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>
<script>
document.forms[0].etc.value = window.dialogArguments;
</script>
</form>
</body>
</html>
