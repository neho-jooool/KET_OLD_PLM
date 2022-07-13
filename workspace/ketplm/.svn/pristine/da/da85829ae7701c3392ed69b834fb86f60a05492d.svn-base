<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "e3ps.common.web.ParamUtil"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
	String oid = ParamUtil.getParameter(request, "oid");
	String buttenView = ParamUtil.getParameter(request, "buttenView");
	if( (buttenView == null) || (buttenView.trim().length() == 0)){
		buttenView = "F";
	}
	
	String print =  ParamUtil.getParameter(request, "print");
	if( (print == null) || (print.trim().length() == 0)){
	    print = "N";
	}
	
	//var url="../../jsp/dms/ViewTechDocumentPop.jsp?oid=";
	//openWindow(url,"","830","600","status=1,scrollbars=no,resizable=no");
	
	// ViewDocumentPrint.jsp
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
<!--
window.onunload=function(){
	try{
		//opener.document.location.reload();
	}catch(e){}

}
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
    	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    		<tr>
    		   <% if("Y".equals(print)){ %>
    			<td><iframe src='/plm/jsp/dms/ViewTechDocumentPrint.jsp?oid=<%=oid%>&isIframe=true&isPop=Y&buttenView=<%=buttenView%>' id="list" name="list" frameborder="0" width="100%" height="100%" style="margin:0;" scrolling="auto"></iframe></td>
    		   <% }else{ %>
    			<td><iframe src='/plm/jsp/dms/ViewTechDocument.jsp?oid=<%=oid%>&isIframe=true&isPop=Y&buttenView=<%=buttenView%>' id="list" name="list" frameborder="0" width="100%" height="100%" style="margin:0;" scrolling="auto"></iframe></td>
    		   <% } %>
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
