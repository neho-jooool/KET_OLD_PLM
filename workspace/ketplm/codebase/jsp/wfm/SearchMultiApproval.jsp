<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="wt.util.WTAttributeNameIfc"%>
<%@page import = "e3ps.common.util.*" %>
<%@page import = "e3ps.common.web.*" %>
<%@page import = "e3ps.groupware.company.*" %>
<%@page import = "e3ps.wfm.entity.*" %>
<%@page import = "e3ps.wfm.util.*" %>
<%@page import = "wt.session.*" %>
<%@page import = "wt.query.*" %>
<%@page import = "wt.fc.*" %>
<%@page import = "wt.org.*" %>
<%@page import = "wt.lifecycle.*" %>
<%@page import = "java.util.*" %>

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

  String reqName = searchCondition.getString("reqName");
  String creator = searchCondition.getString("creator");
  String creatorName = searchCondition.getString("creatorName");
  String reqStartDate = searchCondition.getString("reqStartDate");
  String reqEndDate = searchCondition.getString("reqEndDate");
  String[] stateAry = searchCondition.getStringArray("state");

%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "02339") %><%--일괄결재요청검색--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<%--
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
 --%>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>
<script type="text/javascript" src="/plm/jsp/groupware/js/board.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
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

function inputState(){
  var objSelect = document.getElementById("stateSelect");
  var inputVal = document.getElementById("state");
  var targetVal = objSelect.options[objSelect.selectedIndex].value;
  inputVal.value = targetVal;

}

function clearDate(str){
  var targetVal = document.getElementById(str);
  targetVal.value = "";
}

function clearAll(){
  clearUser('creator');
  clearDate("reqStartDate");
  clearDate("reqEndDate");
/*
  var objSelect = document.getElementById("stateSelect");
  objSelect.options[0].selected=true;
 */
  $("#state").multiselect("uncheckAll");
  var form01 = document.forms[0];
  form01.state.value="";
  form01.reqName.value="";

  //결과내재검색 체크해제
  $("input:checkbox[id=searchInSearch]").attr("checked", false);
}

function perPage(v) {
    document.forms[0].perPage.value = v;

    loadEjsGrid();
}

function search(){
  var form01 = document.forms[0];

  var predate = form01.reqStartDate.value;
  var postdate = form01.reqEndDate.value;

  if((predate!="") && (postdate!="")){
    if(predate>postdate){
      alert('<%=messageService.getString("e3ps.message.ket_message", "02430") %><%--작성일자의 범위가 잘못 지정되어 있습니다--%>');
      form01.reqStartDate.value="";
      form01.reqEndDate.value="";
      return;
    }
  }

  //showProcessing();     // 진행중 Bar 표시
  //disabledAllBtn();     // 버튼 비활성화

  //form01.cmd.value = "search";
  //form01.action = "/plm/servlet/e3ps/MultiApprovalServlet";
  //form01.submit();
  loadEjsGrid();
}

function loadEjsGrid(){
    
    try{
       
       var idx = 0;
       var D = Grids[idx].Data;
       var formName = "form01";
       
       D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
       
       D.Data.Url = "/plm/servlet/e3ps/MultiApprovalServlet";
       D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
       D.Data.Param.cmd = "searchGridData";
       
       D.Page.Url = "/plm/servlet/e3ps/MultiApprovalServlet";
       D.Page.Params =  D.Data.Params;
       D.Page.Param.cmd = "searchGridPage";
       
       Grids[idx].Reload(D);
       
       var S = document.getElementById("Status"+idx);
       if(S) S.innerHTML="Loading";
    
    }catch(e){
        alert(e.message);
    }
}

function excelDown() {
    var form01 = document.forms[0];

    form01.target = "_self";
    form01.cmd.value = "excel";
    form01.action = "/plm/servlet/e3ps/MultiApprovalServlet";
    form01.submit();
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
    
});

//]]>
</script>
</head>

<body class="body-space">
<form name="form01" method="post">
<input type="hidden" name="cmd">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />

<table style="width:100%; height:100%;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table style="width:100%;" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table style="width:100%; height:28px;" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td style="width:20px;"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02339") %><%--일괄결재요청검색--%></td>
                <!-- home > 도면관리 >  -->
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01263") %><%--도면관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02339") %><%--일괄결재요청검색--%></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>
            <table style="width:100%;" border="0" cellspacing="0" cellpadding="0" >
              <tr>
                <td>&nbsp;</td>
                <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                          <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                      </td>
                      <td style="width: 15px;">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:clearAll()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                      <td style="width:5px;">&nbsp;</td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:search()" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                            <td style="width:10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table>
       <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
              <tr>
                <td class="space5"></td>
              </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <tr>
          <td class="tdblueL" style="width:100px;"><%=messageService.getString("e3ps.message.ket_message", "02197") %><%--요청제목--%></td>
          <td class="tdwhiteL" ><input type="text" name="reqName" class="txt_field" style="width:98%;" id="reqName" value="<%=reqName %>"></td>
          <td class="tdblueL" style="width:100px;"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td class="tdwhiteL0">
            <select id="state" name="state" class="fm_jmp" style="width:240px;" multiple="multiple">
            <%
            String templateName = WFMProperties.getInstance().getString("wfm.template.common");
            LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate(templateName, WCUtil.getPDMLinkProduct().getContainerReference());
            Vector lcStates = LifeCycleHelper.service.findStates(LCtemplate);
            for(int i=0; i<lcStates.size(); i++){
              State lstate = (State)lcStates.elementAt(i);
            %>
              <option value=<%=lstate %> <%=(KETParamMapUtil.contains(stateAry, lstate.toString()) ) ? " selected" : "" %>><%=lstate.getDisplay(messageService.getLocale()) %></option>
            <%} %>

            </select>
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02195") %><%--요청일자--%></td>
          <td class="tdwhiteL">
            <input type="text" name="reqStartDate" class="txt_fieldRO" style="width:100px;" id="reqStartDate" value="<%= reqStartDate%>" readOnly>
            &nbsp;<img src="/plm/portal/images/icon_6.png" border="0" onclick="javascript:showCal(reqStartDate)" style="cursor:hand;">
            &nbsp;<a href="javascript:clearDate('reqStartDate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            &nbsp;~&nbsp;
            <input type="text" name="reqEndDate" class="txt_fieldRO" style="width:100px;" id="reqEndDate" value="<%=reqEndDate %>" readOnly>
            &nbsp;<img src="/plm/portal/images/icon_6.png" border="0" onclick="javascript:showCal(reqEndDate)" style="cursor:hand;">
            &nbsp;<a href="javascript:clearDate('reqEndDate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;</td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02196") %><%--요청자--%></td>
          <td class="tdwhiteL0">
            <input type="hidden" id="creator" name="creator" value="<%=creator %>">
            <input type="text" id="creatorName" name="creatorName" class="txt_fieldRO" style="width:80%;" value="<%=creatorName %>" readOnly>
            <a href="javascript:selectUserOid('creator')"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
            &nbsp;<a href="javascript:clearUser('creator')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

                <!-- EJS TreeGrid Start -->
                <div class="content-main">
                    <div class="container-fluid">
                        <div class="row-fluid">
                            <div style="WIDTH:100%;HEIGHT:100%">
                                <bdo
                                    <%--
                                    
                                    Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
                                    Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp"
	                                Debug='3'
	                                --%>
                                    Debug="1" AlertError="0"
	                                
                                    Layout_Url="/plm/jsp/wfm/SearchMultiApprovalGridLayout.jsp"                                    
                                    Layout_Param_Pagingsize="<%=pagingSize %>"
                                    
                                    Data_Url="/plm/servlet/e3ps/MultiApprovalServlet"
                                    Data_Format="Internal"
                                    Data_Data="TGData"
                                    Data_Param_Cmd="searchGridData"
                                    Data_Method="POST"
                                    
                                    Page_Url="/plm/servlet/e3ps/MultiApprovalServlet"
	                                Page_Format="Internal"
	                                Page_Data="TGData"
	                                Page_Param_Cmd="searchGridPage"
	                                Page_Method="POST"
	                                
                                    Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                                    Export_Data="TGData"
                                    Export_Param_File="Search_Multi_Approval_List"
                                ></bdo>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- EJS TreeGrid End -->

    </td>
  </tr>
  <tr>
    <td style="height:30px;" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" style="width:100%; height:24px;" frameborder="0" marginstyle="width:0px;" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table>
</form>
</body>
</html>
