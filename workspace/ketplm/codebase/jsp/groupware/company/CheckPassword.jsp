<%@page contentType="text/html; charset=UTF-8"%>
<%@ page import="e3ps.common.util.*,e3ps.groupware.company.*"%>
<%@include file="/jsp/common/context.jsp"%>
<%@taglib uri="http://www.ptc.com/infoengine/taglib/core" prefix="ie"%>
<html>
<head>
<title></title>
<SCRIPT LANGUAGE="JavaScript">
<!--
function changePw(){
	alert('<%=messageService.getString("e3ps.message.ket_message", "00790") %><%--경고!!\n패스워드 변경 경고를 3회 이상 무시하여 자동 로그아웃합니다\n관리자에게 문의하세요--%>');
	document.location.href='/';
/*
	showProcessing();

	document.forms[0].action = 'changePassword.jsp?cmd=logout';
	document.forms[0].method = 'post';
	document.forms[0].submit();
*/
}
function goto(warning)
{
	if(warning > 0 )
		alert('패스워드 변경 '+warning+ '차 경고!!\n패스워드 변경 경고를 3회 이상 무시하면 로그인이 불가능합니다.');
	document.location.href='/plm/portal/index2.html';
}
//-->
</SCRIPT>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<form>
<%
	e3ps.common.jdf.config.Config conf = e3ps.common.jdf.config.ConfigImpl.getInstance();
	int warning = 0;
	if(!CommonUtil.isAdmin() && conf.getBoolean("warning.password", false))
	{
	    wt.org.WTUser user = (wt.org.WTUser)wt.session.SessionHelper.manager.getPrincipal();
	    warning = e3ps.groupware.company.PeopleHelper.manager.checkPasswordWarning(user);
	    if( warning > 3)
	    {
%>
<%-- 	<input type=hidden name="instance" value="<%=CompanyState.ldapAdapter%>">
			<input type=hidden name="dbuser" value="<%=CompanyState.ldapUser%>">
			<input type=hidden name="passwd" value="<%=CompanyState.ldapPassword%>">
			<input type=hidden name="object" value="<%="uid="+user.getName()%>,<%=CompanyState.ldapDirectoryInfo%>">
			<input type=hidden name="modification" value="replace">
			<input type=hidden name="field" value="userPassword=<%=conf.getString("warning.password.default") %>">
			<input type=hidden name="id" value="<%=user.getName()%>">
--%>
			<SCRIPT>changePw()</SCRIPT>
<%	    return;
	    }
	}

//	response.sendRedirect("/plm/portal/index2.html");
%>
<SCRIPT LANGUAGE="JavaScript">goto('<%=warning%>')</SCRIPT>
</form>
</body>
</html>
