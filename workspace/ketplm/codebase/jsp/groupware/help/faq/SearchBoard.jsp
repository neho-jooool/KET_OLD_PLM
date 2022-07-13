<%@page import="e3ps.common.util.SqlRowMap"%>
<%@page import="e3ps.common.util.KETQueryUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.groupware.board.Board"%>
<%@page import="e3ps.common.treegrid.TreeGridStringBuffer"%>
<%@page import="e3ps.common.util.PlmDBUtil"%>
<%@page import="java.sql.Connection"%>
<%@page import="e3ps.groupware.board.beans.HelpBoardUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="e3ps.common.content.fileuploader.FormUploader"%>

<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
  FormUploader uploader = FormUploader.newFormUploader(request, response, getServletContext());
  KETParamMapUtil searchCondition = KETParamMapUtil.getMap(uploader);

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

  String oid = searchCondition.getString("oid");
  String menu = searchCondition.getString("menu");
  String module = searchCondition.getString("module");
  String subMenu = searchCondition.getString("subMenu");

  String nameValue = searchCondition.getString("name");
  String contValue = searchCondition.getString("cont");
  String creator = searchCondition.getString("creator");
  String tempcreator = searchCondition.getString("tempcreator");
  String from = searchCondition.getString("from");

  StringBuilder qrybuf = new StringBuilder();
  qrybuf.append(" select classnamea2a2||':'||ida2a2 as docOid    \n");
  qrybuf.append(" from board    \n");
  qrybuf.append(" where 1=1      \n");
  if (StringUtil.checkString(nameValue)) {
//    qrybuf.append("   and upper(title) like '").append(HelpBoardUtil.makeQueryForLike(nameValue.toUpperCase())).append("'       \n");
    qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("title", nameValue)).append("    \n");
  }
  if (StringUtil.checkString(contValue)) {
//    qrybuf.append("   and upper(utl_raw.cast_to_varchar2(dbms_lob.substr(webEditorText, 1500))) like '%").append(contValue.toUpperCase()).append("%'    \n");
    qrybuf.append("   and ").append(HelpBoardUtil.getSqlQueryForHelpBoardMultiSearch("utl_raw.cast_to_varchar2(dbms_lob.substr(webEditorText, 1500))", contValue)).append("    \n");
  }
  qrybuf.append(" order by preferred desc, createstampa2 desc    \n");
//  Kogger.debug("qrybuf=" + qrybuf.toString());

  List<SqlRowMap> recordList = null;
  Connection conn = null;
  try {
    conn = PlmDBUtil.getConnection();
    recordList = KETQueryUtil.getSqlResultList(qrybuf.toString(), conn);
  }
  finally {
    PlmDBUtil.close(conn);
  }

  int listCount = recordList.size();

  TreeGridStringBuffer strBuffer = new TreeGridStringBuffer();
  for (SqlRowMap record : recordList) {
    String docOid = record.getString("docOid");
    Board board = (Board) CommonUtil.getObject(docOid);
    int docNumber = (listCount--);
    String title = board.getTitle();
    String viewUrl = "/plm/jsp/groupware/help/faq/ViewBoard.jsp?oid=" + docOid + "&from=list";
    String writer = board.getOwner().getFullName();
    Timestamp writeDate = board.getPersistInfo().getCreateStamp();
    Kogger.debug("writeDate=[" + writeDate + "]"); // DO NOT DELETE THIS LINE! (0000 문제)
    int readCount = board.getReadCount();
    boolean preferred = (board.getPreferred() == 1);
    boolean isNew = HelpBoardUtil.isNewDoc(writeDate);
    String newIcon = (isNew)?"&nbsp;<img src='"+e3ps.common.web.E3PSWebFlag.ICONURL + "/i_new.gif'/>":"";
    String titleType = (isNew || preferred)?"Html":"Text";

    String docNumberStr;
    if (preferred) {
        title = "<b>" + title + "</b>";
        docNumberStr = "";
    }
    else {
        docNumberStr = String.valueOf(docNumber);
    }

    String titleFinal = title + newIcon;

    strBuffer.append( "<I ");
    strBuffer.appendRepl( " NoColor=\"2\" CanDelete=\"0\"" );
    strBuffer.appendRepl( " DocNumber=\"" + docNumberStr + "\"" );
    strBuffer.appendRepl( " Title=\"").appendContent(titleFinal).appendRepl("\"" + " TitleCanSelect=\"2\"" + " TitleType=\""+titleType+"\"" + " TitleOnClick=\"javascript:go_to('" + viewUrl + "');\""
            + " TitleExportValue=\"").appendContent(board.getTitle()).appendRepl("\""
            + " TitleHtmlPrefix=\"<font color='" + PropertiesUtil.getSearchGridLinkColor() + "'>\" TitleHtmlPostfix=\"</font>\"" );
    strBuffer.appendRepl( " Writer=\"" + writer + "\"" );
    strBuffer.appendRepl( " WriteDate=\"" + DateUtil.getDateString( writeDate, "d") + "\"" );
    strBuffer.appendRepl( " ReadCount=\"" + readCount + "\"" );
    strBuffer.append( "/>" );
  }
  String tgData = strBuffer.toString();
//  Kogger.debug("tgData=" + tgData);

  boolean isAdmin = CommonUtil.isAdmin();
  boolean isBizAdmin = CommonUtil.isBizAdmin();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00238") %><%--FAQ--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
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
      form01.action = "/plm/jsp/groupware/help/faq/SearchBoard.jsp?command=search&from=<%=from %>";
      form01.target = "_self";
      form01.submit();
  }

  function clearText(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
  }

  // ==  [Start] 사람 검색  == //
  function clearUser() {
      $("#creator").val("");
      $("#creatorName").val("");
  }

  function selectUser() {
      var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
      attacheMembers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
      if ( typeof attacheMembers == "undefined" || attacheMembers == null ) {
          return;
      }
      // isAppend : Y - 이전 값에 현재 선택된 값 추가
      // isAppend : N - 현재 선택 된 값만 추가
      var isAppend = "Y";
      acceptMember(attacheMembers, isAppend);
  }

  function acceptMember(arrObj, isAppend) {
      var userId = new Array();     // Id
      var userName = new Array();   // Nmae
      var userIdName = new Array(); // Name+"_"+Id
      for ( var i=0; i < arrObj.length; i++ ) {
          // [0] - wtuser oid // [1] - people oid // [2] - dept oid
          // [3] - user id    // [4] - name       // [5] - dept name
          // [6] - duty       // [7] - duty code  // [8] - email

          var infoArr = arrObj[i];
          userId[i] = infoArr[3];
          userName[i] = infoArr[4];
          userIdName[i] = infoArr[4]+"_"+infoArr[3];
      }

      var tmpId = new Array();
      var tmpName = new Array();
      if ( $("#creator").val() != "" && isAppend == "Y" ) {
          // ID 중복 제거
          tmpId = $.merge($("#creator").val().split(", "), userId);
          tmpId = $.unique(tmpId.sort());

          // 동명이인 떄문에...
          var splitId = $("#creator").val().split(", ");
          var splitNmae = $("#creatorName").val().split(", ");
          var len = userIdName.length;
          for ( var i=0; i < splitId.length; i++ ) {
              userIdName[len + i] = splitNmae[i]+"_"+splitId[i];
          }
          userIdName = $.unique(userIdName.sort());

          for ( var i=0; i < userIdName.length; i++ ) {
              userName[i] = userIdName[i].substring(0, userIdName[i].indexOf("_"));
          }
          tmpName = userName;
      }
      else {
          tmpId = userId.sort();
          tmpName = userName.sort();
      }

      $("#creator").val(tmpId.join(", "));
      $("#creatorName").val(tmpName.join(", "));
  }
  // ==  [End] 사람 검색  == //

  function clearDate(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
  }

  function go_to(url){
    //window.location=url;
	  getOpenWindow2(url,'FaqCreatePopup',850,600);
  }

  function clearAll() {
    $("#name").val("");
    $("#cont").val("");
  }

  //Jquery
  $(document).ready(function(){
	  
	  treeGridResize("#SearchFaqBoard","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
      // Enter 검색
      $("form[name=form01]").keypress(function(e) {
          if ( e.which == 13 ) {
              search();
              return false;
          }
      });
  });
//]]>
</script>
</head>

<body class="body-space">
<form name="form01" method="post">

<!-- hidden begin -->
<input type="hidden" name="command" value="search">
<input type="hidden" name="from" value="<%=from %>">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<input type="hidden" name="userlang" value="<%=messageService.getLocale().getLanguage() %>" />
<!-- hidden end -->

<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">

        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%; height: 28px;">
                <tr>
                    <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00239") %><%--FAQ 검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif">Help Desk
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00238") %><%--FAQ--%>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td class="head_line"></td>
        </tr>
        <tr>
            <td class="space10"></td>
        </tr>
        </table>
        <!-- [END] title & position -->

        <!-- [START] button -->
        <table style="width: 100%;">
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <table>
                <tr>
<%
  if (isAdmin == true || isBizAdmin == true) {
%>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:go_to('/plm/jsp/groupware/help/faq/CreateBoard.jsp?from=<%=from %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td style="width: 5px;">&nbsp;</td>
<%
  }
%>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
            </td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] button -->

        <!-- [START] search condition -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <input type="text" name="name" id="name" value="<%=nameValue%>" class="txt_field" style="width:98%;">
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <input type="text" name="cont" id="cont" value="<%=contValue%>" class="txt_field" style="width:98%";"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] search condition -->
        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/groupware/help/faq/SearchBoardGridLayout.jsp"
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

<!-- [START] copyright -->
<tr>
    <td style="height: 30px" valign="bottom">
        <table style="width: 100%;">
        <tr>
            <td style="width: 10px">&nbsp;</td>
            <td style="height: 30px">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 100%; height: 24px;" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr>
<!-- [END] copyright -->

</table>
</form>
</body>
</html>
