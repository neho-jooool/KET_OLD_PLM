<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="wt.org.*,wt.session.SessionHelper"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%WTUser user = (WTUser) SessionHelper.manager.getPrincipal();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/main.css" />
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/loading.css" />
<script type="text/javascript" src="/plm/extcore/extjs50/build/ext-all-debug.js"></script>
<%@include file="/jsp/common/multicombo.jsp"%>
<style type="text/css">
/* tab area border */
#tabs {
    /* padding: 0px;  */
    padding-right : 0px;
    background: none; 
    border-width: 0px; 
} 

/* tab title background color */
#tabs .ui-tabs-nav { 
    padding-left: 0px; 
    background: transparent; 
    border-width: 0px 0px 0px 0px; 
    border-radius: 0px; 
} 

/* tab content border */
#tabs .ui-tabs-panel {
    border-color : white;
    border-width: 0px 0px 0px 0px; 
    padding : 0px 0px 0px 0px;
    border-radius : 0px;
}

/* active tab */
#tabs .ui-tabs-active a {
   background-color : transparent;
   position: relative;
   z-index: 5;
   text-decoration: none !important;     /*To overwrite jquery-ui.css*/
   margin-bottom : 2px;
   padding-bottom : 2px;
}

#tabs .ui-widget-content{
   border-color : #82bad1;
   border-width: 1px 0px 0px 0px;
}

.tab-board02 .tab-part ul li{
   padding-top: 0px;
}

.tab-board02 .tab-part ul li {
    height:35px;
    padding:4px 5px 5px;
}
</style>
<script src="/plm/extcore/js/main/mainContents.js"></script>
<script>
function openTask(url){
	getOpenWindow2(url,'TaskViewPopup', 1024, 900);
}
function goTaskTab(){
	parent.goTaskTab();
}
$(document).ready(function(e) {
    // My Task
	// My Project
	// My Issue
	// 결재완료함
	MainContents.doLoadListCompletedWorkItem();
	var tabs = CommonUtil.tabs('tabs');
	$(tabs).tabs({
		beforeLoad : function(event, ui) {
			if (ui.tab.data("loaded")) {
                event.preventDefault();
                return;
            }
			ui.jqXHR.success(function() {
                ui.tab.data( "loaded", true );
            }),
			$(ui.panel).css('height','500px');
			$(ui.panel).loadMask('loading...');
			ui.jqXHR.error(function() {
				ui.panel.html('<center>페이지 로딩중 오류가 발생하였습니다.</center>');
			});
		},
		load : function(event, ui){
			$('#tabs-1').unLoadMask();
			$('#tabs-1').hide();
			$(ui.panel).unLoadMask();
		}
	});    
	$('#tabs-1').css('height','500px');
	$('#tabs-1').loadMask('loading...');
	$('ul.clearfix li').click(function(e) {
		$('ul.clearfix').children().removeClass('activate');
		$(this).addClass('activate');
	});
});
</script>
</head>
<body>
<%@include file="/jsp/common/processing.html"%>
<%if(!user.getName().startsWith("kplus2")){ %>   
    <div id="wrap">
        <!-- 왼쪽 사이드 메뉴 끝 -->
        <div id="contents_Wrap">
            <div class="contents-box">
                <div class="contents box-size">
                    <div class="main-info clearfix">
                        <!-- My Task -->
                        <div class="mytask">
                            <div class="mytask-title">
                                <div>
                                    <img src="/plm/extcore/image/bar_main_title01_left.gif" />
                                </div>
                                <div>My Task</div>
                                <div>
                                    <a href="#" onclick="parent.goTaskTab()"><img src="/plm/extcore/image/more_main_title01.gif" /></a>
                                </div>
                            </div>
                            <ul>
                                <li><span class="bul-task01"><img src="/plm/extcore/image/bul_main_title01.gif" /></span><%=messageService.getString("e3ps.message.ket_message", "00798")%><!-- 계획 -->
                                    : <span class="point-color01"><a href="#" onclick="parent.goTaskTab('plan')">${myTask.PLAN }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                                <li><span class="bul-task01"><img src="/plm/extcore/image/bul_main_title01.gif" /></span><%=messageService.getString("e3ps.message.ket_message", "02726")%><!-- 진행 -->
                                    : <span class="point-color01"><a href="#" onclick="parent.goTaskTab('progress')">${myTask.PROGRESS }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                                <li><span class="bul-task01"><img src="/plm/extcore/image/bul_main_title01.gif" /></span><%=messageService.getString("e3ps.message.ket_message", "02703")%><!-- 지연 -->
                                    : <span class="point-color01"><a href="#" onclick="parent.goTaskTab('delay')">${myTask.DELAY }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                            </ul>
                        </div>
                        <!-- My Task 끝 -->
                        <!-- My Project -->
                        <div class="myproject">
                            <div class="myproject-title">
                                <div>
                                    <img src="/plm/extcore/image/bar_main_title02_left.gif" />
                                </div>
                                <div>My Project</div>
                                <div>
                                    <a href="#" onclick="parent.goProjectTaskTab()"><img src="/plm/extcore/image/more_main_title02.gif" /></a>
                                </div>
                            </div>
                            <ul>
                                <li><span class="bul-project01"><img src="/plm/extcore/image/bul_main_title02.gif" /></span><%=messageService.getString("e3ps.message.ket_message", "00716")%><!-- 검토 -->
                                    : <span class="point-color01"><a href="#" onclick="parent.goProjectTaskTab('review')">${myProject.REVIEWPROJECT }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                                <li><span class="bul-project01"><img src="/plm/extcore/image/bul_main_title02.gif" /></span><%=messageService.getString("e3ps.message.ket_message", "02536")%><!-- 제품 -->
                                    : <span class="point-color01"><a href="#" onclick="parent.goProjectTaskTab('product')">${myProject.PRODUCTPROJECT }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                                <li><span class="bul-project01"><img src="/plm/extcore/image/bul_main_title02.gif" /></span><%=messageService.getString("e3ps.message.ket_message", "00997")%><!-- 금형 -->
                                    : <span class="point-color01"><a href="#" onclick="parent.goProjectTaskTab('mold')">${myProject.MOLDPROJECT }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                            </ul>
                        </div>
                        <!-- My Project 끝 -->
                        <!-- My Todo -->
                        <div class="myissue">
                            <div class="myissue-title">
                                <div>
                                    <img src="/plm/extcore/image/bar_main_title03_left.gif" />
                                </div>
                                <div>My Todo</div>
                                <div>
                                    <a href="#" onclick="parent.goTodoTaskTab()"><img src="/plm/extcore/image/more_main_title03.gif" /></a>
                                </div>
                            </div>
                            <ul>
                                <li><span class="bul-issue01"><img src="/plm/extcore/image/bul_main_title03.gif" /></span><%=messageService.getString("e3ps.message.ket_message", "03046")%><!-- 프로젝트 -->
                                    : <span class="point-color01"><a href="#" onclick="parent.goTodoTaskTab('project')">${myTodo.PROJECT }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                                <li><span class="bul-issue01"><img src="/plm/extcore/image/bul_main_title03.gif" /></span>ECM : <span
                                    class="point-color01"><a href="#" onclick="parent.goTodoTaskTab('ecm')">${myTodo.ECM }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                                <li><span class="bul-issue01"><img src="/plm/extcore/image/bul_main_title03.gif" /></span>Issue
                                    : <span class="point-color01"><a href="#" onclick="parent.goTodoTaskTab('dqm')">${myTodo.DQM }</a></span><%=messageService.getString("e3ps.message.ket_message", "00698")%><!-- 건 --></li>
                            </ul>
                        </div>
                        <!-- My Issue 끝 -->
                    </div>
                    <!--  두번째 게시판 탭 리스트 부분 -->
                    <div class="tab-board01"></div>
                    <!-- 두번째 게시판 탭 리스트 부분 끝 -->
                    <!-- 세번째 게시판 탭 리스트 -->
                    <div class="tab-board02">
                        <div id="tabs" class="tab-part">
                            <ul class="clearfix">
                                <li class="activate"><a href="/plm/ext/main/listWorkItem.do"><%=messageService.getString("e3ps.message.ket_message", "00760")%><!-- 결재대기함 --></a></li>
                                <li><a class="tabref" href="/plm/ext/main/listInProgressWorkItem.do"><%=messageService.getString("e3ps.message.ket_message", "00787")%><!-- 결재진행함 --></a></li>
                                <li><a class="tabref" href="/plm/ext/main/listCompletedWorkItem.do"><%=messageService.getString("e3ps.message.ket_message", "00777")%><!-- 결재완료함 --></a></li>
                                <li><a class="tabref" href="/plm/ext/main/listMyDocument.do">My Document</a></li>
                                <li><a class="tabref" href="/plm/ext/main/listMyDrawing.do">My Drawing</a></li>
                                <li><a class="tabref" href="/plm/ext/main/listMyPart.do">My Part</a></li>
                                <li><a class="tabref" href="/plm/ext/main/listMyECM.do">My ECM</a></li>
                                <li><a class="tabref" href="/plm/ext/main/listMyIssue.do">My CFT</a></li>
                            </ul>
                            <div id="tabs-1"></div>
                            <div id="tabs-2"></div>
                            <div id="tabs-3"></div>
                            <div id="tabs-4"></div>
                            <div id="tabs-5"></div>
                            <div id="tabs-6"></div>
                            <div id="tabs-7"></div>
                            <div id="tabs-8"></div>
                        </div>
                        <!-- 세번째 게시판 탭 리스트 부분 -->
                    </div>
                </div>
            </div>
        </div>
    </div>
<%} %>
</body>
</html>