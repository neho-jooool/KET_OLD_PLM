<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="e3ps.common.util.StringUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />
<jsp:useBean id="boardCnt" class="java.lang.String" scope="request" />

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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00423") %><%--Q&amp;A--%></title>

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
      form01.action = "/plm/servlet/e3ps/ManageQnaBoardServlet";
      form01.target = "_self";
      form01.submit();
  }

  function clearText(str) {
    var tartxt = document.getElementById(str);
    tartxt.value = "";
  }

  function go_to(url){
    //window.location=url;
	getOpenWindow2(url,'QnaCreatePopup',850,600);
  }

  function clearAll() {
    $("#title").val("");
    $("#webEditorText").val("");
    $("#predate").val("");
    $("#postdate").val("");
    $("#creator").val("");
    $("#creatorName").val("");

    $("#state").multiselect("uncheckAll");
    $("#classification1").multiselect("uncheckAll");
    $("#classification").multiselect("uncheckAll");
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

      $("#state").multiselect({
          noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
          checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
          uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
      });
      $("#classification1").multiselect({
          minWidth: 130,
          noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
          checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
          uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
      });
      $("#classification").multiselect({
          minWidth: 136,
          noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
          checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
          uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
      });
  });

  function onChangeClassification1() {
    var selectedAry = $("#classification1").val();
    $("#classification").empty();
    if (selectedAry != null && selectedAry != undefined && selectedAry.length > 0) {
      boardNumCodeAjax("QNATYPE", selectedAry.join(","), "classification", null, true);
    }
    $("#classification").multiselect("refresh");
  }

//]]>
  $(document).ready(function(){
	    // Calener field 설정
	    CalendarUtil.dateInputFormat('predate');
	    CalendarUtil.dateInputFormat('postdate');
	});

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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00430") %><%--Q&amp;A검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif">Help Desk
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00423") %><%--Q&amp;A--%>
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
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:go_to('/plm/jsp/groupware/help/qna/CreateQnaBoard_q.jsp?from=<%=from %>');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02739") %><%--질문등록--%></a>
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
        <table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
        <table style="width: 100%;">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <input type="text" name="title" id="title" value="<%=searchCondition.getString("title") %>" class="txt_field" style="width:98%;">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01606") %><%--분류--%></td>
            <td class="tdwhiteL0">
              <nobr>
                <!-- MultiCombo -->
                <select id="classification1" name="classification1" class="fm_jmp" multiple="multiple" onchange="javascript:onChangeClassification1();">
                <%
                Map<String, Object> parameter = new HashMap<String, Object>();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "QNATYPE");
                List<Map<String, Object>> classification1NumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                String[] classification1Ary = searchCondition.getStringArray("classification1");
                for ( int i=0; i<classification1NumCode.size(); i++ ) {
                  Object code = classification1NumCode.get(i).get("code");
                  Object value = classification1NumCode.get(i).get("value");
                %>
                    <option value="<%=code %>" <%=(KETParamMapUtil.contains(classification1Ary, code) ) ? " selected" : "" %>><%=value%></option>
                <%
                }
                %>
                </select>
                <select id="classification" name="classification" class="fm_jmp" multiple="multiple">
                <%
                String classification1 = searchCondition.getString("classification1");
                if (StringUtil.checkString(classification1)) {
                  parameter.clear();
                  parameter.put("locale",   messageService.getLocale());
                  parameter.put("codeType", "QNATYPE");
                  parameter.put("code",     classification1);
                  List<Map<String, Object>> classificationNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                  String[] classificationAry = searchCondition.getStringArray("classification");
                  for ( int i=0; i<classificationNumCode.size(); i++ ) {
                    Object code = classificationNumCode.get(i).get("code");
                    Object value = classificationNumCode.get(i).get("value");
                %>
                    <option value="<%=code %>" <%=(KETParamMapUtil.contains(classificationAry, code) ) ? " selected" : "" %>><%=value%></option>
                <%
                  }
                }
                %>
                </select>
              </nobr>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01173") %><%--내용--%></td>
            <td class="tdwhiteL">
                <input type="text" name="webEditorText" id="webEditorText" value="<%=searchCondition.getString("webEditorText") %>" class="txt_field" style="width:98%;"></td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
              <!-- MultiCombo -->
              <select id="state" name="state" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "QNASTATUS");
                List<Map<String, Object>> stateNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                String[] stateAry = searchCondition.getStringArray("state");
                for ( int i=0; i<stateNumCode.size(); i++ ) {
                  Object code = stateNumCode.get(i).get("code");
                  Object value = stateNumCode.get(i).get("value");
                %>
                    <option value="<%=code %>" <%=(KETParamMapUtil.contains(stateAry, code) ) ? " selected" : "" %>><%=value%></option>
                <%
                }
                %>
              </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdwhiteL">
                <input type="text" id="predate" name="predate" class="txt_field" style="width:73px;" value="<%=searchCondition.getString("predate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('predate');" style="cursor: hand;">
                &nbsp;~&nbsp;
                <input type="text" id="postdate" name="postdate" class="txt_field" style="width:73px;" value="<%=searchCondition.getString("postdate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('postdate');" style="cursor: hand;">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL0">
                <input type="hidden" name="creator" id="creator" value="<%=searchCondition.getString("creator") %>">
                <input type="text" name="creatorName" id="creatorName" class="txt_field" style="width: 222px;" value="<%=searchCondition.getString("creatorName") %>" engnum="engnum">
                <a href="javascript:selectUserId('creator');"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                &nbsp;<a href="javascript:clearUser('creator')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
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
                    <div style="WIDTH: 100%; HEIGHT: 100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/groupware/help/qna/SearchQnaBoardGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Boardcnt="<%=boardCnt %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                            Export_Data="TGData"
                            Export_Param_File="Search_TechDocument_List"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid End -->

    </td>
</tr>

</table>
</form>
</body>
</html>
