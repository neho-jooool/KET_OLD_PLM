<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.LoginManager"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
	LoginManager loginManager = LoginManager.getInstance();
	String cost_id = StringUtil.checkNull((String)session.getAttribute("cost_id"));

	if(!"".equals(cost_id)){
		response.sendRedirect("logOut.jsp");
	}
	//if(loginManager.isLogin(session.getId())){  //세션 아이디가 로그인 중이면
		//response.sendRedirect("logOut.jsp");
	//}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>한국단자 개발원가 시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/button/button.css"/>
<script language="JavaScript" type="text/JavaScript">
<!--
	function login(){
		if(document.forms[0].id.value != "" && document.forms[0].password.value!=""){
			document.forms[0].action = "/plm/jsp/cost/common/login_ok.jsp";
			//document.forms[0].action = "/plm/jsp/SSOLogin.jsp";
			document.forms[0].mode.value="cost";
			document.forms[0].method="post";
			document.forms[0].submit();
		}else{
			alert("아이디와 비밀번호를 입력하십시오.");
		}
	}

	function enterLogin(){
		if(event.keyCode == 13){
			login();
		}
	}

//-->
</script>
<style type="text/css">
<!--
.mainFont {
	color: #FFFFFF;
	font-weight: bold;
	font-size: 12px;
}

.mainBottomFont {font-size: 9pt;}
.mainBottomColor {color: #666666}
-->
</style>
</head>

<body>
<form name="login" method="post">
<input type="hidden" name="mode">
<p>&nbsp;</p>
<p>&nbsp;</p>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td><img src="/plm/jsp/cost/images/common/main.jpg" width="700" height="349"></td>
	</tr>
	<tr>
		<td height="68" valign="top" background="/plm/jsp/cost/images/common/login_bg.jpg">
			<br>
			<table width="695" height="50" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr valign="middle">
					<td height="60" align="center">
						<span class="mainFont">아이디 :</span>
						<input name="id" type="text" size="15">
						<span class="mainFont">비밀번호 :</span>
						<input name="password" type="password" size="15" maxlength="20" onkeydown="javascript:enterLogin()">&nbsp;&nbsp;&nbsp;
						<a href="javascript:login();" class="button black medium">로그인</a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br><br>
<div align="center">
  <span class="mainBottomFont">&nbsp&nbsp <span class="mainBottomColor">문의사항은 <b>경영기획본부 경영정보팀(<img border=0 src="/plm/jsp/cost/images/common/icon_phone.gif">1156)</b>으로
  문의 바랍니다.<br>
&nbsp&nbsp Copyright ⓒ 2001 Korea Electric Terminal Co., Ltd. All right reserved.&nbsp;  </span></span></div>
</form>
</body>
</html>