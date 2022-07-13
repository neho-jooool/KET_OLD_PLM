<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@ include file="/jsp/common/InitMsgSvc.jsp"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ page import="e3ps.common.util.CommonUtil"%>
<%@ page import="e3ps.common.util.StringUtil"%>
<%
String modelOid = request.getParameter("modelOid");
String iframeSrc = "KETCad2Bom.jsp?modelOid="+modelOid;
String title = "CAD To BOM";

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>
<script type="text/javascript">
function saveBom()
{
	document.content.saveFn('<%=modelOid%>');
}
</script>
</head>
<body onLoad="">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td valign="top">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td background="/plm/portal/images/logo_popupbg.png">
              <table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=title%></td>
              </table>
             </td>
             <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
               </tr>
        <tr>
             <td height="10" colspan="2"></td>
        </tr>
      </table>
    </td>
</tr>

<tr>
  <td align="right">
     <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td align="right">
              <table border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:saveBom();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "09433") %><%--적용--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window,close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                  </tr>
                 </table></td>                
              </tr>
            </table>
          </td>
        </tr>
      </table>
   </td>
</tr>
<tr><td height="10"></td></tr>
<tr>
    <td>      
       <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td height="100%"><iframe id="content" name="content" src="<%=iframeSrc %>" style="width:100%; height:400px;border:0px;" frameborder="0"></iframe></td>
        </tr>
      </table>
      </td>
</tr>
</table>
</body>
</html>