<%@ page contentType="text/html;charset=UTF-8"%>

<%@page import = "e3ps.common.web.ParamUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    String oid = ParamUtil.getParameter(request, "oid");
    String buttenView = ParamUtil.getParameter(request, "buttenView");
    String isRefresh = ParamUtil.getParameter(request, "isRefresh");
    if( (buttenView == null) || (buttenView.trim().length() == 0)){
        buttenView = "F";
    }
    //var url="../../jsp/dms/ViewDocumentPop.jsp?oid=e3ps.dms.entity.KETProjectDocument:178044";
    //openWindow(url,"","830","600","status=1,scrollbars=no,resizable=no");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--
<% if (isRefresh == null || !isRefresh.equals("N")){ %>
window.onunload=function(){
    opener.document.location.reload();
}
<% } %>
-->
</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="7"></td>
            <td width="20"><img src="../../portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01397") %><%--문서 상세조회--%></td>
          </tr>
        </table></td>
        <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top">
        <table width="100%" height="900px" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td><iframe src='/plm/jsp/dms/ViewDocument.jsp?oid=<%=oid%>&isPop=Y&buttenView=<%=buttenView%>&isRefresh=<%=isRefresh%>' id="list" name="list" frameborder="0" width="100%" height="100%" style="margin:0;" scrolling="auto"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="30" valign="bottom">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="10">&nbsp;</td>
          <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
