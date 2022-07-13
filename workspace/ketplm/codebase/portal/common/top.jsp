<%@page import="java.text.DateFormat"%>
<%@page contentType="text/html;charset=UTF-8" buffer="16kb"%>
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
                e3ps.groupware.company.PeopleData"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%
    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();

    if (messageService != null) {
		String lang = request.getParameter("lang");
		boolean changed = false;
		if ("ko".equals(lang)) {
		    changed = messageService.setLocale(Locale.KOREAN);
		} else if ("en".equals(lang)) {
		    changed = messageService.setLocale(Locale.ENGLISH);
		} else if ("zh".equals(lang)) {
		    changed = messageService.setLocale(Locale.CHINA);
		}
		if (changed) {
%>
<script type="text/javascript">
    parent.contentFrame.location.reload();
</script>
<%
    }
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>PLM Portal</title>
<link href="../css/e3ps.css" rel="stylesheet" type="text/css">
<script src="../js/script.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

-->
#Layer1 {
	position: absolute;
	height: 30;
	z-index: 1;
	top: 65;
	width: 400;
	left: 350;
	visibility: hidden;
}

#Layer2 {
	position: absolute;
	width: 400;
	height: 30;
	z-index: 2;
	left: 470;
	top: 65;
	visibility: hidden;
}

#Layer3 {
	position: absolute;
	width: 500;
	z-index: 3;
	top: 65;
	left: 420;
	height: 30;
	visibility: hidden;
}

#Layer4 {
	position: absolute;
	width: 500;
	z-index: 4;
	top: 65;
	left: 550;
	height: 30;
	visibility: hidden;
}

#Layer5 {
	position: absolute;
	width: 350;
	z-index: 4;
	top: 65;
	left: 660;
	height: 30;
	visibility: hidden;
}
</style>
<script type="text/javascript">
    function MM_swapImgRestore() { //v3.0
        var i, x, a = document.MM_sr;
        for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++)
            x.src = x.oSrc;
    }
    function MM_preloadImages() { //v3.0
        var d = document;
        if (d.images) {
            if (!d.MM_p) d.MM_p = new Array();
            var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
            for (i = 0; i < a.length; i++)
                if (a[i].indexOf("#") != 0) {
                    d.MM_p[j] = new Image;
                    d.MM_p[j++].src = a[i];
                }
        }
    }

    function MM_findObj(n, d) { //v4.01
        var p, i, x;
        if (!d) d = document;
        if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
            d = parent.frames[n.substring(p + 1)].document;
            n = n.substring(0, p);
        }
        if (!(x = d[n]) && d.all) x = d.all[n];
        for (i = 0; !x && i < d.forms.length; i++)
            x = d.forms[i][n];
        for (i = 0; !x && d.layers && i < d.layers.length; i++)
            x = MM_findObj(n, d.layers[i].document);
        if (!x && d.getElementById) x = d.getElementById(n);
        return x;
    }

    function MM_swapImage() { //v3.0
        var i, j = 0, x, a = MM_swapImage.arguments;
        document.MM_sr = new Array;
        for (i = 0; i < (a.length - 2); i += 3)
            if ((x = MM_findObj(a[i])) != null) {
                document.MM_sr[j++] = x;
                if (!x.oSrc) x.oSrc = x.src;
                x.src = a[i + 2];
            }
    }

    function slogout_old() {
        document.logout.target = "_self";
        document.logout.command.value = "logout";
        document.logout.submit();
    }

    function setVisible(objStr) {//6
        var eid = "sub0" + objStr;
        var subMenu = document.getElementById(eid);
        subMenu.style.visibility = "visible";
        for ( var i = 1; i < 8; i++) {
            if (i == objStr) continue;
            var eeid = "sub0" + i;
            var h_subMenu = document.getElementById(eeid);
            //alert(h_subMenu);

            if (h_subMenu != null) h_subMenu.style.visibility = "hidden";
        }
    }

    function setHidden(objStr) {
        var subMenu = document.getElementById(objStr);
        subMenu.style.visibility = "hidden";
    }

    function slogout() {
        document.execCommand('ClearAuthenticationCache');
        parent.location.href = "/";
    }

    function fncClick(obj) {
        for ( var i = 0; i < document.images.length; i++) {
            if (document.images[i].clicked == true) {
                if (document.images[i].name == "Image11")
                    document.images[i].src = "/plm/portal/images/menu_1.png";
                else if (document.images[i].name == "Image12")
                    document.images[i].src = "/plm/portal/images/menu_2.png";
                else if (document.images[i].name == "Image13")
                    document.images[i].src = "/plm/portal/images/menu_3.png";
                else if (document.images[i].name == "Image14")
                    document.images[i].src = "/plm/portal/images/menu_4.png";
                else if (document.images[i].name == "Image15")
                    document.images[i].src = "/plm/portal/images/menu_5.png";
                else if (document.images[i].name == "Image16")
                    document.images[i].src = "/plm/portal/images/menu_6.png";
                else if (document.images[i].name == "Image17") document.images[i].src = "/plm/portal/images/menu_7.png";
                document.images[i].clicked = false;
            }
        }
        obj.clicked = true;
    }
    function subClick(obj, itype) {
        //fncClick(obj);
        obj.src = "/plm/portal/images/menu_" + itype + "_s.png";
    }
    function checkMouseOut(obj) {
        if (!obj.clicked) MM_swapImgRestore();
    }

    function changeLanguage() {
        document.langform.submit();
    }
</script>
</head>
<%
    Config conf = ConfigImpl.getInstance();
    //String produceproject = conf.getString("produceproject.value");
    //String devproject = conf.getString("devproject.value");

    //e3ps.groupware.company.PeopleData pdata = new e3ps.groupware.company.PeopleData();
    try {
		String command = request.getParameter("command");
		if ("logout".equals(command)) {
		    /*
		    response.setHeader("authorization",null);
		    session.invalidate();
		    wtcontext.destroy();
		    wtcontext.setSession(false);
		    wtcontext.setAuthentication(null);

		    Cookie mycookie = new Cookie ("AuthCookie","");
		    mycookie.setPath("/");
		    response.addCookie(mycookie);

		    out.println("<script>top.document.location.href='/';</script>");
		    return;
		     */
		    String redirectUrl = "/";
		    response.setHeader("Location", redirectUrl);
		    response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		    throw new WTException(messageService.getString("e3ps.message.ket_message", "01348")/*로그아웃 상태입니다*/);

		}
    } catch (Exception ex) {
		ex.printStackTrace();
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
    if (CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO")) {
		isPmo = true;
    }

    boolean isDevReqMember = false;
    if ((CommonUtil.isMember("자동차사업부_영업")) || (CommonUtil.isMember("자동차사업부_제품설계")) || (CommonUtil.isMember("자동차PMO")) || (CommonUtil.isMember("자동차사업부_기획"))) {
		isDevReqMember = true;
    }
    if ((CommonUtil.isMember("전자사업부_영업")) || (CommonUtil.isMember("전자사업부_제품설계")) || (CommonUtil.isMember("전자사업부_기획")) || (CommonUtil.isMember("전자PMO"))) {
		isDevReqMember = true;
    }

    if ((!CommonUtil.isMember("공정감사"))) {
		isDevReqMember = true;
    }
%>
<body>
  <form name="langform">
    <table width="990" height="110" background="/plm/portal/images/top_bg.png" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top"><table width="990" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="200" valign="top"><table width="200" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="20">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td valign="top"><a href="JavaScript:parent.gotoMainPage();" target="_self"><img src="/plm/portal/images/logo_plm.png" alt="HOME" border="0"></a></td>
                  </tr>
                </table></td>
              <td width="790" valign="top"><table width="790" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="24" align="right" valign="middle"><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <%
                              if (user.getName().startsWith("PLMTFT") || user.getName().startsWith("kplus1")) {
                          %>
                          <!--td>한국단자 PLM시스템에 접속하셨습니다. <%=today%> </font></td-->
                          <td><%=messageService.getString("e3ps.message.ket_message", "03148", today)%><%--한국단자 PLM시스템에 접속하셨습니다. {0}--%> </font></td>
                          <%
                              } else {
                          %>
                          <!--td><%=user.getFullName()%>님께서 접속하셨습니다. <%=today%> </font></td-->
                          <td><%=messageService.getString("e3ps.message.ket_message", "00027", user.getFullName(), today)%><%--{0}님께서 접속하셨습니다. [{1}]--%></td>
                          <%
                              }
                          %>
                          <!-- //[START] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, logout 추가 2014-06-10, Jason Han -->
                          <td width="20"><a href="javascript:slogout()">logout</a></td>
                          <!-- //[END] [PLM System 고도화] Windchill 10.2 Upgrade 후 에러 처리, logout 추가 2014-06-10, Jason Han -->
                          <td><select name="lang" class="fm_jmp" onchange="javascript:changeLanguage()">
                              <option value="ko" <%=(messageService.isSameLanguage(Locale.KOREAN)) ? "selected" : ""%>>한국어</option>
                              <%--                     <option value="en" <%=(messageService.isSameLanguage(Locale.ENGLISH))?"selected":"" %>>English</option> --%>
                              <option value="zh" <%=(messageService.isSameLanguage(Locale.CHINA)) ? "selected" : ""%>>中国语</option>
                          </select></td>
                          <td width="20"></td>
                          <td><a href="JavaScript:parent.gotoMainPage();" target="_self" onMouseOut="MM_swapImgRestore()"
                            onMouseOver="MM_swapImage('Image3','','/plm/portal/images/btn_home_s.gif',1)"><img src="/plm/portal/images/btn_home.gif" name="Image3"
                              border="0"></a></td>
                          <td><img src="/plm/portal/images/dotline_1.gif"></td>
                          <td><a href="javascript:;"
                            onclick="window.open('/plm/servlet/e3ps/ManageNotifyServlet','Notice','width=820px,height=630px,menubar=no,toolbar=no,status=no,resizable=no,scrollbars=yes')"
                            onfocus="blur" target="_self" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image7','','/plm/portal/images/btn_notice_s.png',1)"><img
                              src="/plm/portal/images/btn_notice.png" name="Image7" border="0"></a></td>
                          <td><img src="/plm/portal/images/dotline_1.gif"></td>
                          <td><a href="javascript:parent.movePaage('/plm/jsp/groupware/help/menu.jsp', '/plm/jsp/groupware/help/main.jsp');" onMouseOut="MM_swapImgRestore()"
                            onMouseOver="MM_swapImage('Image8','','/plm/portal/images/btn_help_s.gif',1)"><img src="/plm/portal/images/btn_help.gif" name="Image8"
                              border="0"></a></td>
                          <td><img src="/plm/portal/images/dotline_1.gif""></td>
                          <%
                              if (CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) {
                          %>
                          <td><a href="/plm/portal/admin/index.jsp?u=39" target="_blank" onMouseOut="MM_swapImgRestore()"
                            onMouseOver="MM_swapImage('Image9','','/plm/portal/images/btn_admin_s.gif',1)"><img src="/plm/portal/images/btn_admin.gif" name="Image9"
                              border="0"></a></td>
                          <td><img src="/plm/portal/images/dotline_1.gif""></td>
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/sample_submenu.jsp', '/plm/ext/sample/list.do')" target="_self">Sample</a></td>
                          <td><img src="/plm/portal/images/dotline_1.gif""></td>
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/program_submenu.jsp', '/plm/ext/sample/list.do')" target="_self">프로그램</a></td>
                          <%
                              } else {
                          %>
                          <td><a href="javascript:alert('<%=messageService.getString("e3ps.message.ket_message", "00950")%><%--관리자가 아닙니다--%>')" onMouseOut="MM_swapImgRestore()"
                            onMouseOver="MM_swapImage('Image9','','/plm/portal/images/btn_admin_s.gif',1)"><img src="/plm/portal/images/btn_admin.gif" name="Image9"
                              border="0"></a></td>
                          <%
                              }
                          %>
                          <td width="10"></td>
                        </tr>
                      </table></td>
                  </tr>
                  <tr>
                    <td height="33" align="center" valign="middle"><table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <!-- 작업공간 메뉴 -->
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/groupware_submenu.jsp',
                '/plm/ext/wfm/workflow/listWorkItem.do');" target="_self"
                            onMouseOver="MM_swapImage('Image11','','/plm/portal/images/menu_1_s.png',1)"> <img src="/plm/portal/images/menu_1.png" name="Image11" border="0"
                              onMouseOut="checkMouseOut(this)" onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide')"
                              onClick="fncClick(this)"></a></td>
                          <td width="38" align="center"><img src="/plm/portal/images/menu_dot.png"></td>
                          <!-- 프로젝트관리 메뉴 -->
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp',
                '/plm/jsp/project/ProjectSearch.jsp');" target="_self"
                            onMouseOver="MM_swapImage('Image12','','/plm/portal/images/menu_2_s.png',1)"> <img src="/plm/portal/images/menu_2.png" name="Image12"
                              border="0" onMouseOut="checkMouseOut(this)"
                              onMouseOver="MM_showHideLayers('Layer1','','show','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide')" onClick="fncClick(this)"></a></td>
                          <td width="38" align="center"><img src="/plm/portal/images/menu_dot.png"></td>
                          <!-- 문서관리 메뉴 -->
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/dms_submenu.jsp', '/plm/jsp/dms/SearchDocument.jsp');" target="_self"
                            onMouseOver="MM_swapImage('Image13','','/plm/portal/images/menu_3_s.png',1)"> <img src="/plm/portal/images/menu_3.png" name="Image13"
                              border="0" onMouseOut="checkMouseOut(this)"
                              onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','show','Layer3','','hide','Layer4','','hide','Layer5','','hide')" onClick="fncClick(this)"></a></td>
                          <td width="38" align="center"><img src="/plm/portal/images/menu_dot.png"></td>
                          <!-- 도면관리 메뉴 -->
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/drawing_submenu.jsp',
                '/plm/servlet/e3ps/EDMServlet?command=openSearch');" target="_self"
                            onMouseOver="MM_swapImage('Image14','','/plm/portal/images/menu_4_s.png',1)"> <img src="/plm/portal/images/menu_4.png" name="Image14"
                              border="0" onMouseOut="checkMouseOut(this)"
                              onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide')" onClick="fncClick(this)"></a></td>
                          <td width="38" align="center"><img src="/plm/portal/images/menu_dot.png"></td>
                          <!-- BOM관리 메뉴 -->
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/bom_submenu.jsp?name0=',
                '/plm/servlet/e3ps/PartServlet?cmd=openSearch');" target="_self"
                            onMouseOver="MM_swapImage('Image15','','/plm/portal/images/menu_5_s.png',1)"> <img src="/plm/portal/images/menu_5.png" name="Image15"
                              border="0" onMouseOut="checkMouseOut(this)"
                              onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide')" onClick="fncClick(this)"></a></td>
                          <!--td><a href="/plm/jsp/bom/BOMEditorLoading.jsp" target="BOMEditor"
                onMouseOver="MM_swapImage('Image15','','/plm/portal/images/menu_5_s.png',1)">
          <img src="/plm/portal/images/menu_5.png" name="Image15" border="0" onMouseOut="checkMouseOut(this)"
                onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide')" onClick="fncClick(this)"></a></td-->
                          <td width="38" align="center"><img src="/plm/portal/images/menu_dot.png"></td>
                          <!-- 설계변경관리 메뉴 -->
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/eco_submenu.jsp',
                '/plm/jsp/ecm/SearchEcoForm.jsp');" target="_self"
                            onMouseOver="MM_swapImage('Image16','','/plm/portal/images/menu_6_s.png',1)"> <img src="/plm/portal/images/menu_6.png" name="Image16"
                              border="0" onMouseOut="checkMouseOut(this)"
                              onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','show')" onClick="fncClick(this)"></a></td>
                          <%
                              String userGroup = KETObjectUtil.getCurrentUserGroup();
                              if (userGroup.equals("자동차사업부") || EWSUtil.isProduction() || EWSUtil.isPurchase() || EWSUtil.isQuality() || CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators")) {
                          %>
                          <td width="38" align="center"><img src="/plm/portal/images/menu_dot.png"></td>
                          <!-- 초기유동관리 메뉴 -->
                          <td><a href="javascript:parent.movePaage('/plm/portal/common/ews_submenu.jsp', '/plm/jsp/ews/SearchEarlyWarning.jsp?')" target="_self"
                            onMouseOver="MM_swapImage('Image17','','/plm/portal/images/menu_7_s.png',1)"> <img src="/plm/portal/images/menu_7.png" name="Image17"
                              border="0" onMouseOut="checkMouseOut(this)"
                              onMouseOver="MM_showHideLayers('Layer1','','hide','Layer2','','hide','Layer3','','hide','Layer4','','hide','Layer5','','hide')" onClick="fncClick(this)"></a></td>
                          <%
                              }
                          %>
                        </tr>
                      </table></td>
                  </tr>
                  <tr>
                    <td height="31">&nbsp;</td>
                  </tr>
                </table></td>
            </tr>
          </table></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin: 0px 0px 1px 0px">
      <tr>
        <td width="180"></td>
        <td bgcolor="#4F4F4F">
          <!-- 프로젝트관리 SubMenu -->
          <div class="topmenu" id="Layer1">
            <a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp', '/plm/jsp/project/ProjectSearch.jsp');" class="topmenu2" target="_self"
              onClick="subClick(document.images[10],2)"><%=messageService.getString("e3ps.message.ket_message", "03046")%><%--프로젝트--%></a>&nbsp;
            <%
                if (isType0 || isPmo || isAdmin || (CommonUtil.isMember("자동차사업부_기획"))) {
            %>
            <a href="javascript:parent.movePaage('/plm/portal/common/project_submenu.jsp?name0=template', '/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps/SearchProjectTemplateServlet');"
              target="_self" onClick="subClick(document.images[10],2)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "00551")%><%--WBS--%></a>&nbsp;
            <%
                }
            %>
            <%
                if (isDevReqMember || isAdmin) {
            %>
            <a href="javascript:parent.movePaage('/plm/portal/common/dev_submenu.jsp','/plm/jsp/dms/SearchDevRequest.jsp');" target="_self" onClick="subClick(document.images[10],2)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "00599")%><%--개발(검토)의뢰--%></a>&nbsp;
            <%
                }
            %>
            <a href="javascript:parent.movePaage('/plm/portal/common/report_submenu.jsp','/plm/jsp/project/ProjectReport.jsp');" target="_self" onClick="subClick(document.images[10],2)"
              class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "02664")%><%--종합현황--%></a>&nbsp;
          </div> <!-- 문서관리 SubMenu -->
          <div id="Layer2">
            <span class="topmenu"> <a href="javascript:parent.movePaage('/plm/portal/common/dms_submenu.jsp','/plm/jsp/dms/SearchDocument.jsp');" target="_self"
              onClick="subClick(document.images[12],3)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "00638")%><%--개발산출문서--%></a>&nbsp; <%
     if (isDevReqMember) {
 %> <a href="javascript:parent.movePaage('/plm/portal/common/dms_tech_submenu.jsp','/plm/jsp/dms/SearchTechDocument.jsp');" target="_self" onClick="subClick(document.images[12],3)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "01123")%><%--기술문서--%></a>&nbsp;
              <%
     }
 %>
            </span>
          </div> <!-- 도면 관리 SubMenu -->
          <div id="Layer3">
            <span class="topmenu"> <a href="javascript:parent.movePaage('/plm/portal/common/drawing_submenu.jsp','/plm/servlet/e3ps/EDMServlet?command=openSearch');" target="_self"
              onClick="subClick(document.images[14],4)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "01262")%><%--도면검색--%></a>&nbsp; <a
              href="javascript:parent.movePaage('/plm/portal/common/drawing_submenu.jsp','/plm/jsp/edm/CreateEPMDocument.jsp');" target="_self" onClick="subClick(document.images[14],4)"
              class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "01266")%><%--도면등록--%></a> &nbsp; <a
              href="javascript:parent.movePaage('/plm/portal/common/drawing_submenu.jsp','/plm/servlet/e3ps/EDMServlet?command=openSearch');" target="_self" onClick="subClick(document.images[14],4)"
              class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "01291")%><%--도면일괄수정--%></a> &nbsp; <a
              href="javascript:parent.movePaage('/plm/portal/common/drawing_submenu.jsp','/plm/servlet/e3ps/EDMServlet?command=openSearch');" target="_self" onClick="subClick(document.images[14],4)"
              class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "02339")%><%--일괄결재요청검색--%></a> &nbsp;
            </span>
          </div> <!-- BOM관리  SubMenu -->
          <div id="Layer4">
            <span class="topmenu"> <a href="javascript:parent.movePaage('/plm/portal/common/bom_submenu.jsp?name0=','/plm/servlet/e3ps/PartServlet?cmd=openSearch');" target="_self"
              onClick="subClick(document.images[16],5)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "01565")%><%--부품 검색--%></a>&nbsp; <a
              href="javascript:parent.movePaage('/plm/portal/common/bom_submenu.jsp?name0=','/plm/jsp/common/loading.jsp?url=/plm/jsp/part/CreatePart.jsp');" target="_self"
              onClick="subClick(document.images[16],5)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "01598")%><%--부품채번관리--%></a>&nbsp; <a
              href="javascript:window.open( '/plm/jsp/bom/BOMEditorLoading.jsp','BOMEditor','width=400,height=400,menubar=no,toolbar=no,location=no,scrollbars=no,status=no' );" target="BOMEditor"
              onClick="subClick(document.images[16],5)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "00090")%><%--BOM편집--%></a> &nbsp;
            </span>
          </div> <!-- 설계변경관리 SubMenu -->
          <div id="Layer5">
            <span class="topmenu"> <a href="javascript:parent.movePaage('/plm/portal/common/ecr_submenu.jsp','/plm/jsp/common/loading.jsp?url=/plm/jsp/ecm/SearchEcrForm.jsp');" target="_self"
              onClick="subClick(document.images[18],6)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "00204")%><%--ECR 관리--%></a>&nbsp; <a
              href="javascript:parent.movePaage('/plm/portal/common/eco_submenu.jsp','/plm/jsp/common/loading.jsp?url=/plm/jsp/ecm/SearchEcoForm.jsp');" target="_self"
              onClick="subClick(document.images[18],6)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%></a> &nbsp; <a
              href="javascript:parent.movePaage('/plm/portal/common/eco_report_submenu.jsp','/plm/jsp/common/loading.jsp?url=/plm/jsp/ecm/SearchMonthlyEcoReportForm.jsp');" target="_self"
              onClick="subClick(document.images[18],6)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "00195") %><%--ECO 현황--%></a> &nbsp; <a
              href="javascript:parent.movePaage('/plm/portal/common/moldplan_submenu.jsp','/plm/jsp/common/loading.jsp?url=/plm/jsp/ecm/SearchMoldPlan.jsp');" target="_self"
              onClick="subClick(document.images[18],6)" class="topmenu2"><%=messageService.getString("e3ps.message.ket_message", "02089") %><%--양산금형 일정관리--%></a>
            </span>
          </div>
        </td>
      </tr>
    </table>
    <!--"CONVERTED_APPLET">
  <!-- HTML CONVERTER -->
    <!--OBJECT classid = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
          codebase = "http://java.sun.com/update/1.6.0/jinstall-6u18-windows-i586.cab#Version=6,0,0,7" WIDTH = "1" HEIGHT = "1" >
  <PARAM NAME = "CODE" VALUE = "e3ps/bom/BOMRegisterApplet.class" >
  <PARAM NAME = "CODEBASE" VALUE = "/plm">
  <PARAM NAME = "cache_archive" VALUE = "lib/SBOMRegister.jar">
  <PARAM NAME = "cache_archive_ex" VALUE = "lib/SBOMRegister.jar;preload">
  <PARAM NAME = "cache_option" VALUE = "Plugin">
  <PARAM NAME = "MAYSCRIPT" VALUE = "true">
  <PARAM NAME = "is_preloading" VALUE = "Y">
  <PARAM NAME = "wc_server_codebase" VALUE = "/plm">
  <PARAM NAME = "type" VALUE = "application/x-java-applet;version=1.6">
  <PARAM NAME = "scriptable" VALUE = "false">

  <COMMENT>
  <EMBED TYPE = "application/x-java-applet;version=1.6"  CODE = "e3ps/bom/BOMRegisterApplet.class" JAVA_CODEBASE = "/plm" ARCHIVE = "ib/SBOMRegister.jar" WIDTH = 1 HEIGHT = 1  scriptable = false pluginspage = "http://java.sun.com/products/plugin/index.html#download">
  <NOEMBED>
  </NOEMBED>
  </EMBED>
  </COMMENT>
  </OBJECT>
  <!--"END_CONVERTED_APPLET"-->
  </form>
</body>
</html>
