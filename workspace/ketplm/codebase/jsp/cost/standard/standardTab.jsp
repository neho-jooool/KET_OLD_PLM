<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.LoginManager"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%
	/*LoginManager loginManager = LoginManager.getInstance();
	if(!loginManager.isLogin(session.getId())){
		out.println("<script>alert('로그인을 하지 않았습니다.');top.location='/plm/jsp/cost/common/main.jsp';</script>");
	}
	String userName = loginManager.getUserName(session.getId());*/

	LoginManager loginManager = LoginManager.getInstance();
	String userName = "";
	//String userAuth = "";
	String cost_id = StringUtil.checkNull((String)session.getAttribute("cost_id"));
	if("".equals(cost_id) || cost_id == null){
		out.println("<script>alert('로그인을 하지 않았습니다.');top.location='/plm/jsp/cost/common/main.jsp';</script>");
	}else{
		//userName = loginManager.getUserName(cost_id);
		userName  = (String)session.getAttribute("Ename");
		//userAuth = (String)session.getAttribute("costAuth");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/plm/jsp/cost/js/jquery/jqueryTabUI.js"></script>
<link href="/plm/jsp/cost/css/jqueryUI/jqueryTab.css" rel="stylesheet" type="text/css">
<style>
	html {font-size:10px;}
	.iframetab {width:100%;height:auto;border:0px; margin:0px;position:relative;top:0px;}
	.ui-tabs-panel {padding:0px !important;}
	.openout {float:right;position:relative;top:-28px;left:-5px;}
</style>
	<script>
	$(document).ready(function() {
		var $tabs = $('#tabs').tabs();

		//get selected tab
		function getSelectedTabIndex() {
			return $tabs.tabs('option', 'selected');
		}

		//get tab contents
		beginTab = $("#tabs ul li:eq(" + getSelectedTabIndex() + ")").find("a");

		loadTabFrame($(beginTab).attr("href"),$(beginTab).attr("rel"));

		$("a.tabref").click(function() {
			loadTabFrame($(this).attr("href"),$(this).attr("rel"));
		});

		//tab switching function
		function loadTabFrame(tab, url) {
			if ($(tab).find("iframe").length == 0) {
				var html = [];
				html.push('<div class="tabIframeWrapper">');
				html.push('<div class="openout"></div><iframe class="iframetab" src="' + url + '">Load Failed?</iframe>');
				html.push('</div>');
				$(tab).append(html.join(""));
				$(tab).find("iframe").height($(window).height()-80);
			}
			return false;
		}
	});
</script>
</head>
<body>
	<div id="tabs">
		<ul>
			<li><a class="tabref" href="#tabs-1" rel="/plm/jsp/cost/standard/cuttingList.jsp">절단비</a></li>
			<li><a class="tabref" href="#tabs-2" rel="/plm/jsp/cost/standard/disList.jsp">물류흐름</a></li>
			<li><a class="tabref" href="#tabs-3" rel="/plm/jsp/cost/standard/lmeList.jsp">LME</a></li>
			<li><a class="tabref" href="#tabs-4" rel="/plm/jsp/cost/standard/mmakerList.jsp">수지원재료</a></li>
			<li><a class="tabref" href="#tabs-5" rel="/plm/jsp/cost/standard/platingList.jsp">도금비</a></li>
			<li><a class="tabref" href="#tabs-6" rel="/plm/jsp/cost/standard/pmakerList.jsp">비철원재료</a></li>
			<li><a class="tabref" href="#tabs-7" rel="/plm/jsp/cost/standard/processingList.jsp">가공비</a></li>
			<li><a class="tabref" href="#tabs-8" rel="/plm/jsp/cost/standard/reerList.jsp">환율정보</a></li>
		</ul>
		<div id="tabs-1" class="tabMain"></div>
		<div id="tabs-2"></div>
		<div id="tabs-3"></div>
		<div id="tabs-4"></div>
		<div id="tabs-5"></div>
		<div id="tabs-6"></div>
		<div id="tabs-7"></div>
		<div id="tabs-8"></div>
	</div>
</body>
</html>