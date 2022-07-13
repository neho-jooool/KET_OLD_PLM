<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
	String targetType = request.getParameter("targetType");
    String oid = "";


    if(request.getParameter("oid") != null && request.getParameter("oid").length() > 0){
        oid = request.getParameter("oid");
    }

    String completed = request.getParameter("completed");
    if(completed == null){
        completed = "";
    }

    ProjectOutput output = (ProjectOutput)CommonUtil.getObject(oid);
    String reson = output.getComplete_reason();
    if(reson == null){
        reson = "";
    }

%>
<%@page import="e3ps.project.ProjectOutput"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00992") %><%--근거사유--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 15px;
    margin-bottom: 5px;
}
-->
</style>
<script type="text/javascript">
function onSave(){
    var reasonValue = document.forms[0].reason;
    if(reasonValue.value == ""){
        alert("<%=messageService.getString("e3ps.message.ket_message", "00993") %><%--근거사유를 입력하세요--%>");
        return;
    }

    document.forms[0].action = "/plm/jsp/project/EtcOutputReasonAction.jsp";
    document.forms[0].method = "post";
    document.forms[0].submit();


    /*
    var reasonValue = document.forms[0].reason;
    if(reasonValue.value == ""){
        window.returnValue = null;
    }else{
        window.returnValue = reasonValue.value;
    }
    window.close();*/
}

function closePopup(){
    //window.returnValue = null;
    self.close();
}
</script>
</head>
<body>
<form>
<input type="hidden" name="targetType" value="<%=targetType %>"></input>
<input type="hidden" name="oid" value="<%=oid %>"></input>
<input type="hidden" name="completed" value="<%=completed %>"></input>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00992") %><%--근거사유--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" >
              <tr><td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <td><table border="0" cellspacing="0" cellpadding="0">
                  <tr>

                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" onclick="javascript:onSave();" class="btn_blue"><%=reson.length()==0?messageService.getString("e3ps.message.ket_message", "02453")/*저장*/:messageService.getString("e3ps.message.ket_message", "01936") /*수정*/ %></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>

                  </tr>
                </table></td>
              </table></td></tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <textarea name="reason" style="width:100%" rows="30" class="fm_area"><%=reson%></textarea>
                <!-- <input type="text" name="reason" class="txt_field"  style="width:100%;height:100%" id="reason" value=""> -->
              </tr>

            </table>
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td class="space15"></td>
              </tr>
            </table>
            </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="30" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="460" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table></td>
  </tr>
</table>

</form>
</body>
</html>
