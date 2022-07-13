<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import = "e3ps.common.util.CommonUtil,
          e3ps.common.util.StringUtil,
          e3ps.common.jdf.config.Config,
          e3ps.common.jdf.config.ConfigImpl"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%@include file="/jsp/common/context.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PLM Portal</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<script src="/plm/portal/js/script.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
  margin-left: 0px;
  margin-top: 0px;
  margin-right: 0px;
  margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>
</head>

<%
  String oneselectname = request.getParameter("name0");
  if(oneselectname==null) oneselectname = "devproject";
  boolean isType0 = CommonUtil.isMember("전자사업부");
  boolean isPlan = CommonUtil.isMember("자동차일정");
  boolean isPmo = CommonUtil.isMember("자동차PMO");
  boolean isAdmin = CommonUtil.isAdmin() || CommonUtil.isMember("Business Administrators");
  boolean isMoldPart = CommonUtil.isMember("자동차사업부_금형부품");
  boolean isCS = CommonUtil.isMember("공정감사");
  boolean isCarSearch = false;

  if(CommonUtil.isMember("전자사업부_영업") || CommonUtil.isMember("전자사업부_제품설계") || CommonUtil.isMember("전자사업부_임원") || CommonUtil.isMember("전자사업부_기획")){
    isCarSearch = true;
  }

  String checkPerformance = "1";
  if(isType0){
    checkPerformance = "3";
  }
%>
<body>
  <table width=200 height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="190" height="100%" valign="top"><table width="190" height="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="44"><table width="189" border="0" cellspacing="0" cellpadding="0">
                <tr>
                 <%
                  if(oneselectname.equals("devproject")){
                  %>
                  <td><img src="/plm/portal/images/subh_8.png"></td>
                   <%
                  }else if(oneselectname.equals("template")){
                  %>
                   <td><img src="/plm/portal/images/subh_7.png"></td>
                   <%
                  }
                  %>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td align="center" valign="top"><!--menu -->

               <%

  if( oneselectname.equals("devproject")) {

    %>
               <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif">To-Do List </td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps/IssueServlet&key=command&value=searchMyIssue" class="font_07leftmenu" target="contName">My Issue</a></td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps/ProjectServlet&key=command&value=searchMyTask" class="font_07leftmenu" target="contName">My Task</a></td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps/ProjectServlet&key=command&value=searchMyProject" class="font_07leftmenu" target="contName">My Project</a></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table>
              <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif"><%=messageService.getString("e3ps.message.ket_message", "02633") %><%--제품프로젝트관리--%></td>
                </tr>
                <%if(isType0 || isPmo || isAdmin || CommonUtil.isMember("자동차사업부_기획")){ %>
                <tr>
                  <td height="17" style="padding-left:3"><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/CreateReviewProject.jsp" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "00723") %><%--검토 프로젝트 등록--%></a></td>
                </tr>
                <%} %>
                <%if(isType0 || isPmo || isAdmin){ %>
                <tr>
                  <td height="17" style="padding-left:3"><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/CreateProductProject.jsp" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "02549") %><%--제품 프로젝트 등록--%></a></td>
                </tr>
                <%} %>
                <tr>
                  <td height="17" style="padding-left:3"><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/ProjectSearch.jsp&key=radio&value=2" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%></a></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table>
              <%if((isPmo || isAdmin || !isType0 || isPlan || isCarSearch) && !isCS){ %>
              <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif"><%=messageService.getString("e3ps.message.ket_message", "02410") %><%--자동차일정관리--%></td>
                </tr>
                <%if(isPmo || isAdmin || isPlan){%>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/CreateProgram.jsp" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "02407") %><%--자동차일정 등록--%></a></td>
                </tr>
                <%} %>
                <%if(!isType0 || isAdmin || isPlan || isCarSearch){%>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps/CarServlet" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "02406") %><%--자동차일정 검색--%></a></td>
                </tr>
                <%} %>
                <tr>
                  <td height="10"></td>
                </tr>
              </table>
              <%} %>
              <%if(!isType0 || isAdmin){ %>
              <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif"><%=messageService.getString("e3ps.message.ket_message", "01110") %><%--금형프로젝트관리--%></td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/ProjectSearch.jsp&key=radio&value=3" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "01026") %><%--금형 프로젝트 검색--%></a></td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="javascript:parent.movePaage('/plm/jsp/project/trySchdule/Calendar.jsp', '/plm/jsp/project/trySchdule/TryCalendar.jsp');" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "01017") %><%--금형 Try관리--%></a></td>
                </tr>
                <%if(isMoldPart || isAdmin){ %>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/moldPart/CreateMoldPart.jsp" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "01071") %><%--금형부품관리 등록--%></a></td>
                </tr>
                <%} %>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading2.jsp?url=/plm/servlet/e3ps/MoldPartSearchServlet?command=openSearch" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "01069") %><%--금형부품관리 검색--%></a></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table>
              <%} %>
              <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif"><%=messageService.getString("e3ps.message.ket_message", "00267") %><%--Issue 관리--%></td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/ProjectIssueTotalList.jsp" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "02487") %><%--전체 Issue 검색--%></a></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table>
              <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif"><%=messageService.getString("e3ps.message.ket_message", "01896") %><%--성과관리--%></td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/performance/ListPerformance<%=checkPerformance%>.jsp" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "01898") %><%--성과관리 검색--%></a></td>
                </tr>
                <%if(isType0 ||isPmo || isAdmin){ %>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/performance/ListValuation.jsp" class="font_07leftmenu" target="contName"><%=messageService.getString("e3ps.message.ket_message", "01901") %><%--성과관리 평가기준--%></a></td>
                </tr>
                <%} %>
              </table>
              <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="10"></td>
                </tr>
                <tr>
                  <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif">프로그램 관리</td>
                </tr>
                <tr>
                  <td height="17" style="padding-left:3"> <a href="/plm/jsp/common/loading.jsp?url=/plm/ext/project/program/listProgram.do" class="font_07leftmenu" target="contName">프로그램 검색</a></td>
                </tr>
              </table>

              <%
  } else if(oneselectname.equals("template")) {
              %>
              <table width="165" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/template/ProjectTempCreatePage.jsp"  target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00554") %><%--WBS 등록--%></a></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
                <tr>
                  <td><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/servlet/e3ps/SearchProjectTemplateServlet" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00557") %><%--WBS 목록--%></a></td>
                </tr>
                <tr>
                  <td height="10"></td>
                </tr>
              </table>
              <%
  }
              %>
    <%if(CommonUtil.isAdmin()){ %>
         <table width="165" border="0" cellspacing="0" cellpadding="0">
         <tr>
                  <td height="10"></td>
                </tr>
                <tr>
                   <td class="font_08leftmenu"><img src="/plm/portal/images/icon_2.gif"><a href="/plm/jsp/common/loading.jsp?url=/plm/jsp/project/ManageMainPage.jsp" target="contName" class="leftmenu1"><%=messageService.getString("e3ps.message.ket_message", "00945") %><%--관리메뉴--%></td>
                </tr>
                </table>
    <%} %>
              <!--menu --></td>
          </tr>

           <tr>
            <td height="118" align="center"><table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="../images/banner_1.gif"></td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
              <tr>
                <td height="70" align="center" valign="top" background="../images/banner_2.gif"><table width="150" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                      <td height="31">&nbsp;</td>
                    </tr>
                    <tr>
                      <td>(032)850-1369(PMS)</td>
                    </tr>
                    <tr>
                      <td>(032)850-1304(PDM)</td>
                    </tr>
                    <tr>
                      <td><%=messageService.getString("e3ps.message.ket_message", "00001") %><%--(032)850-1154(시스템)--%></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table></td>
          </tr>
       </table></td>
      <td width="1" bgcolor="#c6c6c6"></td>
    </tr>
  </table>
</body>
</html>
