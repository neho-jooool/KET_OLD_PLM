<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.util.CommonUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  // EJS TreeGrid Start
  response.addHeader("Cache-Control","max-age=1, must-revalidate");

  String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
  String pagingSize = null;

  if( searchCondition != null && !searchCondition.isEmpty() ) {
      pagingSize = searchCondition.getString("perPage");
  }

  if ( pagingSize == null || pagingSize.trim().length() == 0 ) {
      pagingSize = pagingSizeDefault;
  }
  // EJS TreeGrid End

  String from = searchCondition.getString("from");

%>

<html>
<head>
<title><%=messageService.getString("e3ps.message.ket_message", "00891") %><%--공지사항 목록--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/jsp/groupware/js/board.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">

table {
    border-spacing: 0;
    border: 0px;
}
table th, table td {padding: 0}
img {
    vertical-align: middle;
}
input {
    vertical-align:middle;line-height:22px;
}
</style>

<script type="text/javascript">
//<![CDATA[

function perPage(v) {
  document.forms[0].perPage.value = v;

  search();
}

function search() {
    var form01 = document.forms[0];
    form01.action = "/plm/servlet/e3ps/ManageNotifyServlet";
    form01.command.value = "search";
    form01.submit();
}

function notifyuserlist(oid) {
  var str="/plm/jsp/groupware/board/notifyreadlist.jsp?oid="+oid;
  openSameName(str,"450","400","status=no,scrollbars=yes,resizable=no");
}

function go_to(url){
  //window.location=url;
  getOpenWindow2(url,'NotifyCreatePopup',800,600);
}

function deleteValueAll() {
  $("#title").val("");
  $("#predate").val("");
  $("#postdate").val("");
  $("#creator").val("");
  $("#creatorName").val("");
}

  //Jquery
  $(document).ready(function(){
      // Enter 검색
      $("form[name=form01]").keypress(function(e) {
          if ( e.which == 13 ) {
              search();
              return false;
          }
      });
      CalendarUtil.dateInputFormat('predate');
      CalendarUtil.dateInputFormat('postdate');
  });
//]]>
</script>
</head>

<body class="body-space" scroll="auto" style="overflow-x:hidden">
<form name="form01" method="post">

<!-- hidden begin -->
<input type="hidden" name="command" value="search">
<input type="hidden" name="from" value="<%=from %>">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<input type="hidden" name="userlang" value="<%=messageService.getLocale().getLanguage() %>" />
<!-- hidden end -->

<table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table style="width:100%;height:28px;" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00891") %><%--공지사항 목록--%></td>
             <td align="right">
                 <img src="/plm/portal/images/icon_navi.gif">Home
                 <img src="/plm/portal/images/icon_navi.gif">Help Desk
                 <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00423") %><%--공지사항--%>
             </td>
          </tr>
          
        </table></td>
        <td></td>
      </tr>
      <tr>
            <td class="head_line"></td>
        </tr>
        <tr>
            <td class="space10"></td>
        </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table style="width:100%; height:100%;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td valign="top"><table style="width:100%;" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <%
              if(CommonUtil.isAdmin()) {
            %>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:go_to('/plm/jsp/groupware/board/notifycreate.jsp?from=<%=from %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td>&nbsp;</td>
                      <%  }
            %>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteValueAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                      <td>&nbsp;</td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                      <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
            <table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
            <table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="tdblueL" style="width:110px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
                <td class="tdwhiteL0" colspan="3">
                  <input type="text" name="title" id="title" value="<%=searchCondition.getString("title") %>" class="txt_field" style="width:98%">
                </td>
              </tr>
              <tr>
                <td class="tdblueL"  style="width:110px;"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                <td class="tdwhiteL" style="width:265px;">
                  <input TYPE="hidden" name="creator" id="creator" value="<%=searchCondition.getString("creator") %>">
                  <input type="text" name="creatorName" id="creatorName" class="txt_fieldRO"  style="width:215px" value="<%=searchCondition.getString("creatorName") %>" engnum="engnum" readonly>
                  <a href="javascript:selectUserId('creator');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                  &nbsp;<a href="JavaScript:clearUser('creator')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                </td>
                <td class="tdblueL" style="width:110px;"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
                <td class="tdwhiteL0" style="width:265px;">
                <input type="text" id="predate" name="predate" class="txt_field" style="width:68px;" value="<%=StringUtil.checkNull( searchCondition.getString("predate") ) %>" onChange="javascript:changeDate1()">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('predate');" style="cursor: hand;">
                &nbsp;~&nbsp;
                <input type="text" id="postdate" name="postdate" class="txt_field" style="width:68px;" value="<%=StringUtil.checkNull( searchCondition.getString("postdate") ) %>" onChange="javascript:changeDate2()">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('postdate');" style="cursor: hand;">
                </td>
              </tr>
            </table>
            <table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH: 100%; HEIGHT: 100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/groupware/board/notifylistGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_TechDocument_List"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->

            </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
