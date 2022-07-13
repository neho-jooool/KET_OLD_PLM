<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<%@include file="/jsp/common/multicombo.jsp" %>
<%String id = request.getParameter("j_username");%>
<script type="text/javascript">
<!--
function gologin(){
	retireForm.action = "/plm/extcore/logincheck.jsp?j_username="+"<%=id%>"+"&redirectUrl=main";
	retireForm.submit();	
}

//-->
</script>
</head>
<BODY onLoad="javascript:gologin();">
<input type="hidden" name="j_username" id="j_username" value="<%=id%>">
<form name="retireForm" method="POST" action="/plm/extcore/logincheck.jsp">
</form>
</body>
</html>