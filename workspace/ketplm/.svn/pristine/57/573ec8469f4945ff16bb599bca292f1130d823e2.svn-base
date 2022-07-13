<%@page import="ext.ket.wfm.service.KETWorkflowHelper"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="e3ps.common.util.StringUtil"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="e3ps.common.util.DateUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="java.util.ArrayList"%>
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

  String predate = searchCondition.getString("predate");
  String postdate = searchCondition.getString("postdate");
  String filterClass = searchCondition.getString("filterClass");
  //out.print(predate);
  //out.print(postdate);
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "05160") %><%--참조함--%></title>
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
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/jsp/wfm/viewPBO.js"></script>
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript">
//<![CDATA[
           
$(document).ready(function(e) {
	treeGridResize("#ReferenceWorkList","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    $(function(){
        $("#filterClass").multiselect({
            minWidth : 200,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802")%><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498")%><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500")%><%--전체해제--%>'
        });
    });
    // Calener field 설정
    CalendarUtil.dateInputFormat('predate', 'postdate');
    // default 기간 설정
    $('[name=predate]').val('<%= predate%>');
    $('[name=postdate]').val('<%= postdate%>');

});
    
function perPage(v) {
    document.form01.perPage.value = v;
    search();
}

function search(){
  var form01 = document.forms[0];

  var predate = form01.predate.value;
  var postdate = form01.postdate.value;

  if((predate!="") && (postdate!="")){
    if(predate>postdate){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02430") %>');<%--작성일자의 범위가 잘못 지정되어 있습니다--%>
                form01.predate.value = "";
                form01.postdate.value = "";
                return;
            }
        }
        form01.method = "post";
        form01.action = "/plm/servlet/e3ps/WorkspaceServlet?cmd=searchRefDoc";
        form01.submit();
}
    //]]>
</script>
<body class="body-space"><form name="form01" method="post">
  <input type="hidden" name="perPage" value="<%=pagingSize%>" />
  <table style="width: 100%; height: 100%;" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td valign="top"><table style="width: 100%;" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <table style="width: 100%; height: 28px;" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                  <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "05160")%><%--참조함--%></td>
                  <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00788")%><%--결재함--%>
                    <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "05160")%><%--참조함--%></td>
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
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td>
              <table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><!-- 검색 --></a></td>
                  <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td class="space5"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td class="tab_btm2"></td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <colgroup>
            <col width="15%">
            <col width="35%">
            <col width="15%">
            <col width="35%">
          </colgroup>
          <tr>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02242")%><!-- 유형 --></td>
            <td class="tdwhiteL0">
              <select name="filterClass" id="filterClass" class="fm_jmp" multiple="multiple">
              <%
                Map<String, String> filterMap = KETWorkflowHelper.manager.getTypeList();
                Iterator<String> iter = filterMap.keySet().iterator();
                while(iter.hasNext()){
                    String key = iter.next();
              %>
                  <option value="<%= key %>" <%= (filterClass.equals(key))?"SELECTED":"" %>><%= filterMap.get(key) %></option>
              <%
                }
              %>
              </select>
            </td>
            <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "01300")%><%--도착일--%></td>
            <td class="tdwhiteL0">
              <input type="text" id="predate" name="predate" class="txt_field" style="width: 100px;" value=""> 
              <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('predate');" style="cursor: hand;" > ~ 
              <input type="text" id="postdate" name="postdate" class="txt_field" style="width: 100px;">
              <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('postdate');" style="cursor: hand;">
            </td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;">
          <tr>
            <td class="space5"></td>
          </tr>
        </table> <!-- EJS TreeGrid Start -->
        <div class="content-main">
          <div class="container-fluid">
            <div class="row-fluid">
              <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%">
                <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/wfm/ReferenceWorkListGridLayout.jsp" Layout_Param_Pagingsize="<%=pagingSize%>" Data_Url="/plm/jsp/common/searchGridData.jsp"
                  Data_Method="POST" Data_Param_Result="<%=tgData%>" Data_Param_Pagingsize="<%=pagingSize%>" Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData"
                  Export_Param_File="Reference_Work_List"
                ></bdo>
              </div>
            </div>
          </div>
        </div> <!-- EJS TreeGrid End --></td>
    </tr>
  </table>
</form>
</body>
</html>