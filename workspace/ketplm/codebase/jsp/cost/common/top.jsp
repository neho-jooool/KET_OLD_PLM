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
<link rel="stylesheet" type="text/css" href="/plm/jsp/cost/css/common/dock-style.css"/>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/interface.js"></script><!-- 상단메뉴 jquery -->
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/top.js"></script><!-- 상단메뉴 jquery -->
<script type="text/javascript" src="/plm/jsp/cost/js/common.js"></script>
<!--[if lt IE 7]>
 <style type="text/css">
 div, img { behavior: url(iepngfix.htc) }
 </style>
<![endif]-->
</head>
<body>
<div class="dock" id="dock">
	<div class="dock-container">
		<a class="dock-item" href="javascript:parent.movePage('mainList');"><img src="/plm/jsp/cost/images/list/home.png" alt="home" /><span>개발원가</span></a>
		<%if(userAuth.equals("A")){ %>
		<a class="dock-item" href="javascript:parent.movePage('auth');"><img src="/plm/jsp/cost/images/list/email.png" alt="contact" /><span>권한관리</span></a>
		<a class="dock-item" href="javascript:parent.movePage('standard');"><img src="/plm/jsp/cost/images/list/portfolio.png" alt="portfolio" /><span>기준단가</span></a>
		<%}%>
		<a class="dock-item" href="javascript:parent.movePage('newRequest')"><img src="/plm/jsp/cost/images/list/music.png" alt="music" /><span>원가요청</span></a>
		<!-- <a class="dock-item" href="#"><img src="/plm/jsp/cost/images/list/video.png" alt="video" /><span>Video</span></a>
		<a class="dock-item" href="#"><img src="/plm/jsp/cost/images/list/history.png" alt="history" /><span>History</span></a>
		<a class="dock-item" href="#"><img src="/plm/jsp/cost/images/list/calendar.png" alt="calendar" /><span>Calendar</span></a>
		<a class="dock-item" href="#"><img src="/plm/jsp/cost/images/list/rss.png" alt="rss" /><span>RSS</span></a>
		 -->
	</div>
</div>
<div id="bglayer"></div>
<div id="loginlayer"><%=userName%>으로 접속하였습니다.</div>
</body>
</html>
