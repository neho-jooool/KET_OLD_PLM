<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "e3ps.common.web.ParamUtil"%>

<%
    //WBS TASK로 부터의 문서등록시 문서등록화면의 팝업으로 나타난다.
	String projectOid =  request.getParameter("outputOid");
	String taskOid =  request.getParameter("taskOid");
	if( (projectOid == null) || (projectOid.trim().length() == 0)) projectOid = "0";
	//대외비변수
	String security = request.getParameter("security");
	if(security == null)
	{
		security = "";
	}
	else if(security.equals("S"))
	{
		security = "S2";
	}

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
</head>
<body style="overflow-x:hidden;overflow-y:auto;">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top">
    	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    		<tr>
    			<td><iframe src='/plm/jsp/dms/CreateDocument.jsp?projectOid=<%=projectOid%>&taskOid=<%=taskOid %>&isPop=Y&security=<%=security %>' id="list" name="list" frameborder="0" width="100%" height="90%" style="margin:0;" scrolling="auto"></iframe></td>
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
