<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList,
                java.util.Map,
                java.util.HashMap,
                java.util.List"%>
<%@page import="e3ps.common.util.KETParamMapUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.code.NumberCodeHelper"%>
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

    if( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 완료여부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ISSUECOMPLETE");
    List<Map<String, Object>> issueCompleteNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/multicombo/jquery-ui-1.10.3.custom.css" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
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

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
</style>
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript">
var locale = '<%=messageService.getLocale()%>';
</script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<%@include file="/jsp/common/top_include.jsp"%>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
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

table th,table td {
	padding: 0
}

img {
	vertical-align: middle;
	border: 0;
}

input {
	vertical-align: middle;
	line-height: 22px;
}
</style>
<script type="text/javascript">
//<![CDATA[
    function viewIssue(v) {
        var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
        openOtherName(url,"","750","700","status=yes,scrollbars=yes,resizable=no");
    }

    function viewProject(oid, type){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup&issueType="+type, '',1150,800);
    }

    function clearAll(){
        $("#startCreateDate").val("");
        $("#endCreateDate").val("");
        $("#myIssueType").multiselect("uncheckAll");
        $("#myIssueType").val("MyAnswerIssue").prop('selected','selected');
        $("#myIssueType").multiselect("refresh");
        $("#category").multiselect("uncheckAll");
        $("#category").multiselect("refresh");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    function search() {
        if ( $("#myIssueType").val() == "MyIssue" ) {
             //MY 제기 이슈
            document.forms[0].myIssueList.value = "myIssueList";
        }
        else if ( $("#myIssueType").val() == "MyAnswerIssue" ) {
             //MY 담당 이슈
            document.forms[0].myAnswerList.value = "myAnswerList";
        }
        else {
            //all
            document.forms[0].myIssueList.value = "all";
        }

        document.frm.action = "/plm/servlet/e3ps/IssueServlet?command=searchMyIssue";
        document.frm.target = "_self";
        document.frm.submit();
    }

    function excelDown() {
        if ( $("#myIssueType").val() == "MyIssue" ) {
            //MY 제기 이슈
            document.forms[0].myIssueList.value = "myIssueList";
        }
        else if ( $("#myIssueType").val() == "MyAnswerIssue" ) {
            //MY 담당 이슈
            document.forms[0].myAnswerList.value = "myAnswerList";
        }
        else {
           //all
           document.forms[0].myIssueList.value = "all";
        }
        document.forms[0].action = "/plm/servlet/e3ps/IssueServlet?command=excelDownMyIssue";
        document.forms[0].submit();
    }

    //Jquery
    $(document).ready(function(){
    	treeGridResize("#ProjectIssueTotalListGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        CalendarUtil.dateInputFormat('startCreateDate', 'endCreateDate');
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#myIssueType").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#category").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    });
//]]>
</script>
</head>
<body class="body-space">
  <form name="frm" method="POST">
    <!-- hidden begin -->
    <input type="hidden" name="perPage" value="<%=pagingSize %>" />
    <input type="hidden" name="myIssueList" />
    <input type="hidden" name="myAnswerList" />
    <input type="hidden" name="searchYN" value="Y" />
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
                    <td class="font_01">My CFT요청</td>
                    <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435")%><%--작업공간--%>
                      <img src="/plm/portal/images/icon_navi.gif">My Issue</td>
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
          </table> <!-- [END] title & position --> <!-- [START] button -->
          <table style="width: 100%;">
            <tr>
              <td>&nbsp;</td>
              <td align="right">
                <table>
                  <tr>
                    <%-- <td><input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%>
                        checked <%} %>
                      /><%=messageService.getString("e3ps.message.ket_message", "00749") %>결과 내 재검색</td>
                    <td style="width: 15px;">&nbsp;</td> --%>
                    <td>
                      <table>
                        <tr>
                          <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
                          <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
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
          </table> <!-- [END] button -->
          <!-- 검색영역 collapse/expand -->
          <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div> 
          <!-- [START] search condition -->
          <table style="width: 100%;">
            <tr>
              <td class="tab_btm2"></td>
            </tr>
          </table>
          <table style="width: 100%;">
            <tr>
              <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "00265") %><%--Issue Type--%></td>
              <td class="tdwhiteL" style="width: 270px;"><select id="myIssueType" name="myIssueType" class="fm_jmp" style="width: 270px;" multiple="multiple">
                  <%
                String[] myIssueType = searchCondition.getStringArray("myIssueType");
                String myIssueList = searchCondition.getString("myIssueList");
                %>
                  <option value="MyIssue" <%=(KETParamMapUtil.contains(myIssueType, "MyIssue") || "all".equals(myIssueList) ) ? " selected" : "" %>>발송 CFT요청</option>
                  <option value="MyAnswerIssue" <%=(KETParamMapUtil.contains(myIssueType, "MyAnswerIssue") || "all".equals(myIssueList) ) ? " selected" : "" %>>수신 CFT요청</option>
              </select></td>
              <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
              <td class="tdwhiteL0" style="width: 270px;"><select id="category" name="category" class="fm_jmp" style="width: 270px;" multiple="multiple">
                  <%
                String[] category = searchCondition.getStringArray("category");

                for ( int i=0; i<issueCompleteNumCode.size(); i++ ) {
                %>
                <option value="<%=issueCompleteNumCode.get(i).get("code") %>" <%=KETParamMapUtil.contains(category, issueCompleteNumCode.get(i).get("code") ) ? " selected" : "" %>><%=issueCompleteNumCode.get(i).get("value")%></option>
                <%
                }
                %>
              </select></td>
            </tr>
            <tr>
              <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02521") %><%--제기일--%></td>
              <td class="tdwhiteL0" colspan="3">
                <input type="text" name="startCreateDate" id="startCreateDate" class="txt_field" style="width: 70px" value="">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('startCreateDate');" style="cursor: hand;">
                &nbsp;~&nbsp;
                <input type="text" name="endCreateDate" id="endCreateDate" class="txt_field" style="width: 70px" value="">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('endCreateDate');" style="cursor: hand;">
              </td>
            </tr>
          </table>
          <table style="width: 100%;">
            <tr>
              <td class="space5"></td>
            </tr>
          </table> <!-- [END] search condition --> <!-- EJS TreeGrid Start -->
          <div class="content-main">
            <div class="container-fluid">
              <div class="row-fluid">
                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%">
                  <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/project/ProjectIssueTotalListGridLayout.jsp" Layout_Param_Pagingsize="<%=pagingSize %>"
                    Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST" Data_Param_Result="<%=tgData %>" Data_Param_Pagingsize="<%=pagingSize %>"
                    Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_StandardDoc_List"
                  ></bdo>
                </div>
              </div>
            </div>
          </div> <!-- EJS TreeGrid Start -->
        </td>
      </tr>
    </table>
  </form>
</body>
</html>
