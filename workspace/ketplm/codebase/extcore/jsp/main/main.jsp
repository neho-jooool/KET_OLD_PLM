<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.DateFormat"%>
<%@page
  import="java.net.URLEncoder,
                java.util.Locale,
                wt.org.*,
                wt.fc.QueryResult,
                wt.fc.PersistenceHelper,
                wt.session.SessionHelper,
                wt.util.WTException,
                java.util.Calendar,
                e3ps.ews.beans.EWSUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.KETObjectUtil,
                e3ps.common.message.KETMessageService,
                e3ps.common.jdf.config.Config,
                e3ps.common.jdf.config.ConfigImpl,
                e3ps.project.dao.ProjectReportDao,
                e3ps.groupware.board.beans.MyTestOption,
                e3ps.groupware.company.PeopleData"
%>
<!-- 로그 설정 임포트 시작 -->
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<!-- 로그 설정 임포트 끝 -->

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%
    Config conf = ConfigImpl.getInstance();
    try {
        String command = request.getParameter("command");
        if ("logout".equals(command)) {
            String redirectUrl = "/";
            response.setHeader("Location", redirectUrl);
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            throw new WTException(messageService.getString("e3ps.message.ket_message", "01348")/*로그아웃 상태입니다*/);

        }
    } catch (Exception ex) {
	    Kogger.error(ex);
    }

    Calendar ca = Calendar.getInstance();
    String today;
    Locale lo = messageService.getLocale();
    if (Locale.KOREAN.equals(lo.getLanguage())) {
        today = DateFormat.getDateInstance(DateFormat.LONG, lo).format(ca.getTime());
    } else {
        today = DateFormat.getDateInstance(DateFormat.FULL, lo).format(ca.getTime());
    }

    boolean isAdmin = CommonUtil.isAdmin();
    boolean isType0 = CommonUtil.isMember("전자사업부");
    boolean isPmo = false;
    if (CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO") || CommonUtil.isMember("KETS_PMO")) {
        isPmo = true;
    }

    boolean isDevReqMember = false;
    boolean isSqMember = false;
    if ((CommonUtil.isMember("자동차사업부_영업")) || (CommonUtil.isMember("자동차사업부_제품설계")) || (CommonUtil.isMember("자동차PMO")) || (CommonUtil.isMember("자동차사업부_기획"))) {
        isDevReqMember = true;
    }
    if ((CommonUtil.isMember("전자사업부_영업")) || (CommonUtil.isMember("전자사업부_제품설계")) || (CommonUtil.isMember("전자사업부_기획")) || (CommonUtil.isMember("전자PMO"))) {
        isDevReqMember = true;
    }

    if ((!CommonUtil.isMember("공정감사"))) {
        isDevReqMember = true;
    }
    
    if ((CommonUtil.isMember("SQ인증감사"))) {
	   isSqMember = true;
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="shortcut icon" type="image/x-icon" href="/plm/portal/icon/favicon.ico" />
<!-- ExtJS Start -->
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/ext-theme-classic-all-debug.css"/>
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/custom-extjs.css" />
<script type="text/javascript" src="/plm/extcore/extjs50/build/ext-all-debug.js"></script>
<!-- ExtJS End -->
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/main.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery.loadmask.css" />
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/loading.css" />
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/icon.css" />
<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';

<%
WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
String userName = user.getFullName();
if (messageService != null) {
    String lang = request.getParameter("lang");
    
    boolean changed = false;
    if ("ko".equals(lang)) {
        changed = messageService.setLocale(Locale.KOREAN);
    } else if ("en".equals(lang)) {
        changed = messageService.setLocale(Locale.ENGLISH);
    } else if ("zh".equals(lang)) {
        changed = messageService.setLocale(Locale.CHINA);
    } else if ("pl".equals(lang)) {
        changed = messageService.setLocale(new Locale("pl", "PL"));
    } else if ("vi".equals(lang)) {
        changed = messageService.setLocale(new Locale("vi", "VN"));
    } else if ("ja".equals(lang)) {
        changed = messageService.setLocale(Locale.JAPANESE);
    }
    if (changed) {
%>
    location.href=document.location.href;
<%
    }
}
%>
</script>
<script src="/plm/portal/js/multicombo/jquery.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<!-- <script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script> -->
<script type="text/javascript" src="/plm/extcore/js/main/onReady.js"></script>
<script type="text/javascript">
	function notice_getCookie(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				return getCookieVal(j);
			i = document.cookie.indexOf("", i) + 1;
			if (i == 0)
				break;
		}
		return null;
	}
	function getCookieVal(offset) {
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1)
			endstr = document.cookie.length;
		return unescape(document.cookie.substring(offset, endstr));
	}
	function openMultiWindow(url, name, width, height, indent) {
		// modified from common.js, openWindow()

		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=0,resizable=0,";
		if (width == 'full') {
			leftpos = (screen.availWidth - screen.availWidth * 0.9) / 2;
			toppos = (screen.availHeight - screen.availHeight * 0.9 - 30) / 2;

			leftpos = leftpos + indent;
			toppos = toppos + indent;

			rest = "width=" + (screen.availWidth * 0.9) + ",height="
					+ (screen.availHeight * 0.9) + ',left=' + leftpos + ',top='
					+ toppos;
		} else {
			leftpos = (screen.availWidth - width) / 2;
			toppos = (screen.availHeight - 60 - height) / 2;

			leftpos = leftpos + indent;
			toppos = toppos + indent;

			/* rest = "width=" + width + ",height=" + height + ',left=' + leftpos
					+ ',top=' + toppos; */
			rest = "dialogWidth:" + width+"px;" + "dialogHeight:" + height+"px;center=:yes;";
		}
		var newwin = open(url, name, opts + rest);
		//var newwin = showModelessDialog(url, name,  rest); 
		newwin.focus();
		return newwin;
	}
	function openNoti(oids,isLoginCheck) {		
		
		if(isLoginCheck != 'ok'){
			alert("EP에 로그인된 아이디와 PLM 접속 계정이 일치하지 않습니다.\r\n아직 로그인을 안하셨다면 EP로그인 후 재시도 바랍니다.");
			window.open("about:blank","_self").close();
		}
		
		var userName = '<%=userName%>';
		
		if(userName == '한국단자공업'){
			alert("현재 접속한 사용자는 PLM계정이 없으며 일부 메뉴에 대한 조회권한만 있습니다.아이디 신청은 연구지원팀에 문의바랍니다.");
		}
		
		if (oids.length > 0) {
			var oidAry = oids.split(',');
			var oid, idpart;
			for ( var i = 0; i < oidAry.length; ++i) {
				oid = oidAry[i];
				idpart = oid.split(':')[1];
				if (notice_getCookie("renewalPopup" + idpart) != "no") {
					openMultiWindow('/plm/jsp/groupware/board/notifyviewPopup.jsp?oid='+ oid, 'renewalPopup' + i, 850, 900, i * 15);
				}
			}
		}
		
	}
	var isAdmin = <%=CommonUtil.isAdmin()%>;
	var isKETSUser = <%=CommonUtil.isKETSUser()%>;
	var isSqMember = <%=isSqMember%>;
</script>
</head>
<body onload="javascript:openNoti('${notiOid}','${isLoginCheck}');">
    <!-- loading start -->
    <div id="loading-mask" class="loadmask"></div>
    <div id="loading" class="loadmask-msg">
        <div class="loading-indicator">
            <span id="loading-msg">Loading...</span>
        </div>
    </div>
    <!-- loading end -->
    <div id="main"></div>
    <!-- header start -->
    <div id="header" style="display: none">
        <form name="LangForm" method="post">
            <div class="top-bar box-size">
                <h1 class="logo"><img src="/plm/extcore/image/logo.png" /></h1>
            </div>
            <div id="main_Search" class="clearfix">
            	<div class="admin-info"><%=messageService.getString("e3ps.message.ket_message", "00027", user.getFullName(), today)%>
                <span id="logoutAnchor" class="b-style01 ml16">logout</span></div>
                <div class="select-language">
                    <select id="localeChange" name="lang">
                        <option value="ko" <%=(Locale.KOREAN.equals(messageService.getLocale())) ? "selected" : ""%>>한국어</option>
                        <option value="en" <%=(Locale.ENGLISH.equals(messageService.getLocale())) ? "selected" : ""%>>English</option>
                        <option value="zh" <%=(Locale.CHINA.equals(messageService.getLocale())) ? "selected" : ""%>>中国语</option>
                        <option value="ja" <%=(Locale.JAPANESE.equals(messageService.getLocale())) ? "selected" : ""%>>日本語</option>
<%--                         <option value="pl" <%=(new Locale("pl", "PL").equals(messageService.getLocale())) ? "selected" : ""%>>Język polski</option>
                        <option value="vi" <%=(new Locale("vi", "VN").equals(messageService.getLocale())) ? "selected" : ""%>>Tiếng Việt</option>  --%>
                    </select>
                    <div class="language box-size">
                        <%=
                        	    (messageService.getLocale() == Locale.KOREAN)  ? "한국어"    :
                                (messageService.getLocale() == Locale.ENGLISH)  ? "English" :
                                (messageService.getLocale() == Locale.CHINA)      ? "中国语" :
                                (messageService.getLocale() == Locale.JAPANESE) ? "日本語" :
                                (messageService.getLocale().getCountry().equals(new Locale("pl", "PL").getCountry()) ) ? "Język polski" :
                                (messageService.getLocale().getCountry().equals(new Locale("vi", "VN").getCountry())) ? "Tiếng Việt"     :
                                 ""
                         %>
                     </div>
                </div>
                <%
                if(!CommonUtil.isKETSUser()){
                    if(!user.getName().startsWith("kplus2")){

                %>
                <div class="main-search02">
                    <div class="main-select01 box-size" id="searchCategoryText"><%=messageService.getString("e3ps.message.ket_message", "09359") %><!-- 모든유형 --></div>
                </div>
                <div id="searchCategoryDiv" class="select-check box-size x-window x-layer x-window-default x-plain x-window-plain x-window-default-plain x-border-box" style="display: none; top: 300px">
                    <ul>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="" onclick="selectAllCategory(this)"/></span><%=messageService.getString("e3ps.message.ket_message", "09359") %><!-- 모든유형 --></li>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="PROJECT" /></span><%=messageService.getString("e3ps.message.ket_message", "03046") %><!-- 프로젝트 --></li>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="DEVDOC" /></span><%=messageService.getString("e3ps.message.ket_message", "00638") %><!-- 개발산출문서 --></li>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="DRAWING" /></span><%=messageService.getString("e3ps.message.ket_message", "01253") %><!-- 도면 --></li>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="PART" /></span><%=messageService.getString("e3ps.message.ket_message", "01564") %><!-- 부품 --></li>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="ECO" /></span><%=messageService.getString("e3ps.message.ket_message", "01844") %><!-- 설계변경 --></li>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="DQM" /></span><%=messageService.getString("e3ps.message.ket_message", "07231") %><!-- 개발품질문제 --></li>
                        <li><span class="select-check-space"><input name="searchCategory" type="checkbox" value="SAMPLE" /></span><%=messageService.getString("e3ps.message.ket_message", "08032") %><!-- Sample요청 --></li>
                    </ul>
                </div>
                <div class="main-search03">
                    <input id="searchKeyword" name="searchKeyword" type="text" value="" class="main-search-input box-size" />
                   <!-- <div class="main-select02 box-size">검색조건</div> -->
                </div>
                <div class="b-main-search"><span class="b-search"><img id="goSearch" src="/plm/extcore/image/ico_main_search.png" /></span></div>
                <%}} %>
            </div>
        </form>
    </div>
    <!-- header end -->
</body>
</html>