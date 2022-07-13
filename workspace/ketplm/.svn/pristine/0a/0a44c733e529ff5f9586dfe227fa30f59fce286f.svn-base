<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.LoginManager"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
	LoginManager loginManager = LoginManager.getInstance();
	String userName = "";
	String userAuth = "";
	String cost_id = StringUtil.checkNull((String)session.getAttribute("cost_id"));
	if("".equals(cost_id) || cost_id == null){
		out.println("<script>alert('로그인을 하지 않았습니다.');top.location='/plm/jsp/cost/common/main.jsp';</script>");
	}else{
		//userName = loginManager.getUserName(cost_id);
		userName  = (String)session.getAttribute("Ename");
		userAuth = (String)session.getAttribute("costAuth");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>한국단자 개발원가 시스템</title>
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/common/top.css"/>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/top1.js"></script><!-- 상단메뉴 jquery -->
<script type="text/javascript" src="/plm/jsp/cost/js/common.js"></script>
</head>
<body>
<ul class="menu">
	<li id="li1">개발원가</li>
	<%if(userAuth.equals("A")){ %>
	<li id="li2">권한관리</li>
	<%} %>
	<li id="li3">기준단가</li>
</ul>
<div id="loginlayer"><%=userName%>님으로 접속하였습니다.</div>
</body>
</html>
