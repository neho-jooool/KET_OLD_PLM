<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
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

</script>
</head>
<body onLoad="">
<%

String iframeSrc = request.getParameter("url");
String title = "";
String partNo= "";
String partRev= "";
if(iframeSrc.indexOf("KETBomEditor.jsp")!=-1)
{
    title = "BOM "+messageService.getString("e3ps.message.ket_message", "09413");//편집기
}else if(iframeSrc.indexOf("KETBomReverse.jsp")!=-1)
{
    
    partNo = request.getParameter("partNumber");
    partRev = request.getParameter("partRev");
    
    iframeSrc +="?partNumber="+ partNo+"&partRev="+partRev;
    title = messageService.getString("e3ps.message.ket_message", "09414");//사용처 조회
}

%>
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
             <td height="10"></td>
        </tr>
      </table>
    </td>
</tr>
<tr>
    <td>      
       <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td height="100%"><iframe id="content" name="content" src="<%=iframeSrc %>" style="width:100%; height:600px;border:0px;" frameborder="0"></iframe></td>
        </tr>
      </table>
      </td>
</tr>
</table>
</body>
</html>