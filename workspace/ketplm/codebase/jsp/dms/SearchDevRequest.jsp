<%@page import="ext.ket.shared.code.service.CodeHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Hashtable,
                java.util.Vector,
                java.util.List,
                java.util.HashMap,
                java.util.Map,
                java.text.SimpleDateFormat,
                java.util.Calendar"%>

<%@page import="wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.State"%>

<%@page import="e3ps.common.util.WCUtil,
                wt.org.WTUser,
                wt.session.SessionHelper,
                e3ps.groupware.company.PeopleData,
                e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.KETStringUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;

    if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End

    if ( searchCondition.isEmpty() ) {
        Calendar cal = Calendar.getInstance();
        searchCondition.put("postdate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        searchCondition.put("predate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }

    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
    String userName = user.getFullName();
    PeopleData peoData = new PeopleData(user);
    String deptName = peoData.departmentName;
    String divCode = "";
    String projectType = "";

    if(CommonUtil.isMember("전자사업부")){
      projectType = "전자";
      divCode = "ER";
    }else{
      projectType = "자동차";
      divCode = "CA";
    }

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 개발(검토)유형
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVREVIEWTYPE");
    List<Map<String, Object>> devReviewTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    // 제품군
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PRODCATEGORYCODE");
    List<Map<String, Object>> prodCategoryNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    ArrayList devStepCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdDevelopmentStep")), "," );   //  구분(개발의뢰,검토의뢰)
    ArrayList devReTypeCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdDevReviewTypeCode")), "," );     //  개발유형
    ArrayList prodCatCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdProductCategoryCode")), "," );     //  제품군
    ArrayList authStatusCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdAuthorStatus")), "," );     //  결재유형

    String RequestNo = StringUtil.checkNull(request.getParameter("RequestNo"));
    String SDevelopmentStep = StringUtil.checkNull(request.getParameter("DevelopmentStep"));
    String SDevProductName = StringUtil.checkNull(request.getParameter("DevProductName"));
    String SRequestBuyer = StringUtil.checkNull(request.getParameter("RequestBuyer"));
    String SLastUsingBuyer = StringUtil.checkNull(request.getParameter("LastUsingBuyer"));
    String SuserName = StringUtil.checkNull(request.getParameter("userName"));
    String SRepCarType = StringUtil.checkNull(request.getParameter("RepCarType"));
    String SauthorStatus = StringUtil.checkNull(request.getParameter("authorStatus"));
    String SDevReviewTypeCode = StringUtil.checkNull(request.getParameter("DevReviewTypeCode"));
    String SProductCategoryCode = StringUtil.checkNull(request.getParameter("ProductCategoryCode"));
    String Spredate = StringUtil.checkNull(request.getParameter("predate"));
    String Spostdate = StringUtil.checkNull(request.getParameter("postdate"));
    String SsortKey = StringUtil.checkNull(request.getParameter("sortKey"));
    String SsortKeyD = StringUtil.checkNull(request.getParameter("sortKeyD"));
    String SpageCnt = StringUtil.checkNull(request.getParameter("pageCnt"));
    String Spage = StringUtil.checkNull(request.getParameter("page"));
    String SnowBlock = StringUtil.checkNull(request.getParameter("nowBlock"));
    String isSer = StringUtil.checkNull(request.getParameter("isSer"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script> <!-- To-Be JSP에서는 추가 하지 않습니다. -->
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script language="JavaScript">
<!--

function isNull(str) {
    if(str==null||str==""){
      return true;
    }
    return false;
}

function isNotDigit(str) {
    var pattern = /^[0-9]+$/;

    if(!pattern.test(str)){
      return true;
    }
    return false;
}

function tblCls(){
    var tBody = document.getElementById("iDrTable");
    var tLength = tBody.rows.length;

    for(var i=0; i<tLength; i++){
      tBody.deleteRow(0);
    }
}

function selectSingleSubContractor(arrObj0){
	var fm = document.form01;
	var arrObj = arrObj0[0];
    //alert("arrObj==>"+arrObj[0]+", "+arrObj[1]+", "+arrObj[2]);
    fm.RequestBuyer.value= arrObj[2];
}

function deleteRequestBuyer() {
    var fm = document.form01;

    fm.RequestBuyer.value= "";
}


function insertLastUsingBuyer(arrObj0) {
    var fm = document.form01;
    
    var arrObj = arrObj0[0];
    //alert("arrObj==>"+arrObj[0]+", "+arrObj[1]+", "+arrObj[2]);
    fm.LastUsingBuyer.value= arrObj[2];
}

function deleteLastUsingBuyer() {
    var fm = document.form01;
    fm.LastUsingBuyer.value= "";
}


//==  [Seart] 사람 검색  == //
function deleteUser() {
    $("#creator").val("");
    $("#userName").val("");
}

function selectUser() {
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=acceptMember";
    
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
    getOpenWindow2(url,name, 800, 710, opts);
    
}

function acceptMember(arrObj) {
	var isAppend = "N";
    var userId = new Array();     // Id
    var userName = new Array();   // Nmae
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtuser oid // [1] - people oid // [2] - dept oid
        // [3] - user id    // [4] - name       // [5] - dept name
        // [6] - duty       // [7] - duty code  // [8] - email

        var infoArr = arrObj[i];
        //[START][KET PLM 고도화 프로젝트] OID로 넘겨받게 변경, 2014. 6. 17. Jason, Han
        userId[i] = infoArr[0];
        //[END][KET PLM 고도화 프로젝트] OID로 넘겨받게 변경, 2014. 6. 17. Jason, Han
        userName[i] = infoArr[4];
    }

    var tmpId = new Array();
    var tmpName = new Array();
    if ( $("#creator").val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpId = $.merge($("#creator").val().split(", "), userId);
        tmpId = $.unique(tmpId.sort());

        tmpName = $.merge($("#userName").val().split(", "), userName);
        tmpName = $.unique(tmpName.sort());
    }
    else {
        tmpId = userId.sort();
        tmpName = userName.sort();
    }

    $("#creator").val(tmpId.join(", "));
    $("#userName").val(tmpName.join(", "));
}
// ==  [End] 사람 검색  == //



function excelDown(){
	$('[name=command]').val("excel");
	if(Grids[0].LoadedCount == 0){
		return;
	}
	showProcessing();
	$.fileDownload('/plm/servlet/e3ps/CommissonSearchServlet?'+$('[name=form01]').serialize(),{
		 successCallback : function(){
			 hideProcessing();	 
		 },
		 failCallback: function(responseHtml, url) {
			 hideProcessing();
		 }
	});
    /* document.form01.target = "";
    document.form01.cmd.value = "excel";
    document.form01.action = '/plm/servlet/e3ps/CommissonSearchServlet';
    document.form01.method = "post";
    document.form01.submit(); */
}

function perPage(v) {
    document.form01.perPage.value = v;

    doSubmit();
}

function doSubmit(){
    if(!valiDate()) return;

    /* showProcessing();     // 진행중 Bar 표시
    disabledAllBtn();     // 버튼 비활성화

    document.form01.HdDevelopmentStep.value  = $("#DevelopmentStep").val(); // 구분
    document.form01.HdDevReviewTypeCode.value  = $("#DevReviewTypeCode").val(); // 검토유형
    document.form01.HdProductCategoryCode.value  = $("#ProductCategoryCode").val(); // 제품군
    document.form01.HdAuthorStatus.value  = $("#authorStatus").val(); // 결재상태

    document.form01.cmd.value = "search";
    document.form01.target = "_self";
    document.form01.action = '/plm/servlet/e3ps/CommissonSearchServlet';
    document.form01.method = "post";
    document.form01.submit(); */
    
    loadEjsGrid();
}

function loadEjsGrid(){
    try{
       
       var idx = 0;
       var D = Grids[idx].Data;
       var formName = "form01";
       
       D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
       
       D.Data.Url = "/plm/servlet/e3ps/CommissonSearchServlet";
       D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
       D.Data.Param.command = "searchGridData";
       
      /*  D.Page.Url = "/plm/servlet/e3ps/CommissonSearchServlet";
       D.Page.Params =  D.Data.Params;
       D.Page.Param.command = "searchGridPage"; */
       //D.Page.Param.page = D.GetPageNum(row);
       
       Grids[idx].Reload(D);
       
       var S = document.getElementById("Status"+idx);
       if(S) S.innerHTML="Loading";
    
    }catch(e){
        alert(e.message);
    }
}

function doClear(){
    var fm = document.form01;
    fm.RequestNo.value = "";
    fm.DevProductName.value = "";
    fm.RequestBuyer.value = "";
    fm.LastUsingBuyer.value = "";
    fm.RepCarType.value = "";
    //fm.predate.value = "";
    //fm.postdate.value = "";
    deleteUser();

    $("#DevelopmentStep").multiselect("uncheckAll");
    $("#DevReviewTypeCode").multiselect("uncheckAll");
    $("#ProductCategoryCode").multiselect("uncheckAll");
    $("#authorStatus").multiselect("uncheckAll");

    //결과내재검색 체크해제
    $("input:checkbox[id=searchInSearch]").attr("checked", false);
}

function selectDocu(){
    var url="/plm/jsp/dms/SearchDevRequestPop.jsp?method=selDr&developmentStep=D";
    openWindow(url,"","800","600","status=1,scrollbars=no,resizable=no");
}

function selDr(arr){
    for (var i = 0; i < arr.length; i++){
       var drOid = arr[i];
    }
}

function changeDate1(){
    var startDate;
    startDate = document.form01.predate.value;

    if (startDate != ""){
      if (!dateCheck(startDate,"-")){
              alert('<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>');
        document.form01.predate.value = "";
      }
    }
}

function changeDate2(){
    var endDate;
    endDate = document.form01.postdate.value;
    if (endDate != ""){
      if (!dateCheck(endDate,"-")){
              alert('<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>');
        document.form01.postdate.value = "";
      }
    }
}

function valiDate(){
    var startDate;
    var endDate;

    startDate = document.form01.predate.value;
    endDate = document.form01.postdate.value;

    if ((startDate != "")&&(endDate != "")){
      if(startDate>endDate){
        alert('<%=messageService.getString("e3ps.message.ket_message", "01166") %><%--날짜순서가 맞지 않습니다--%>');
        return false;
      }
    }
    return true;
}

function deleteValue(param){
    document.getElementById(param).value = "";
}

window.onload=function(){
    if(document.forms[0].isSer.value=="T"){
        doSubmit();
    }
};

function goCreateDevReviewRequest(){
	getOpenWindow2('/plm/jsp/dms/CreateDevRequest.jsp?step=R','DevRevRequestCreatePopup',1024,768);
}

function goCreateDevRequest(){
	getOpenWindow2('/plm/jsp/dms/CreateDevRequest.jsp?step=D','DevRequestCreatePopup',1024,768);
}

function setPartClaz(checkedNode){
    var nodeOIdStr='', nodeNameStr='';
    for(var i=0; i < checkedNode.length; i++){
        if(i == checkedNode.length - 1){
            nodeOIdStr += checkedNode[i].id;
            nodeNameStr += checkedNode[i].name;
        }else{
            nodeOIdStr += checkedNode[i].id+','; 
            nodeNameStr += checkedNode[i].name+',';
        }
    }
    //초기화
    $('[name=ProductCategoryCode]').val('');
    $('[name=ProductCategoryName]').val('');
    //재맵핑
    $('[name=ProductCategoryCode]').val(nodeOIdStr);
    $('[name=ProductCategoryName]').val(nodeNameStr);
}

$(document).ready(function(){
	$("#DevelopmentStep").multiselect({
        minWidth : 170,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    $("#DevReviewTypeCode").multiselect({
        minWidth : 170,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    $("#ProductCategoryCode").multiselect({
        minWidth : 170,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    $("#authorStatus").multiselect({
        minWidth : 170,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    // Calener field 설정
    CalendarUtil.dateInputFormat('predate','postdate');
    //고객사 suggest
    SuggestUtil.bind('CUSTOMER', 'RequestBuyer', null);
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'LastUsingBuyer', null);
    //사용자 suggest
    SuggestUtil.bind('USER', 'userName', 'creator');
    //대표차종
    SuggestUtil.bind('CARTYPE', 'RepCarType');
    
    $('input').keypress(function (e) {
      if (e.which == 13) {
          doSubmit();
          return false;
      }
    });
    
    treeGridResize("#CommisionSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    
});

$(function(){
    
});
-->
</script>

</head>

<body class="body-space">
<form name="form01" >
<input type="hidden" name="isSer" value="<%=isSer%>">
<input type="hidden" name="page" value="<%=Spage%>">
<input type="hidden" name="nowBlock" value="<%=SnowBlock%>">
<input type="hidden" name="sortKeyD" value="<%=SsortKeyD%>">
<input type="hidden" name="divCode" value="<%=divCode%>">

<input type="hidden" name="HdDevelopmentStep" value="">
<input type="hidden" name="HdDevReviewTypeCode" value="">
<input type="hidden" name="HdProductCategoryCode" value="">
<input type="hidden" name="HdAuthorStatus"        value="">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />

<input type="hidden" name="command" value="search">


<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00600") %><%--개발(검토)의뢰 검색--%></td>
                <td align="right"><img src="/plm/portal/images/icon_navi.gif">Home<img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00601") %><%--개발(검토)의뢰 관리--%><img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02252") %><%--의뢰서 검색--%></td>
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
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td><!-- <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch"
                            <%if (searchCondition.getString("searchInSearch").equals(("searchInSearch"))) {%> checked
                            <%}%> /><%=messageService.getString("e3ps.message.ket_message", "00749")%><%--결과 내 재검색--%> --></td>
                        <%
                        if(CommonUtil.isMember("자동차사업부_영업") || CommonUtil.isMember("전자사업부_영업") || CommonUtil.isMember("제품프로젝트등록") || CommonUtil.isMember("전자PMO") || CommonUtil.isAdmin()){
                        %>    
                        <td style="width: 15px;">&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10px"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:goCreateDevReviewRequest();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00618")%><%--개발검토의뢰 등록--%></a></td>
                                    <td width="10px"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10px"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:goCreateDevRequest();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00657")%><%--개발의뢰 등록--%></a></td>
                                    <td width="10px"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <%
                        }
                        %>
                        <td width="5">&nbsp;</td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10px"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:doClear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a></td>
                                    <td width="10px"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="10px"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:doSubmit();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                    <td width="10px"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table></td>
                    </tr>
                </table>
            </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <!-- 검색영역 collapse/expand -->
      <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td width="90px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td class="tdwhiteL">
            <select name="DevelopmentStep"  id="DevelopmentStep" class="fm_jmp" multiple="multiple">
              <option value="R" <%=devStepCondList.contains("R") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "00729") %><%--검토의뢰--%></option>
              <option value="D" <%=devStepCondList.contains("D") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "00656") %><%--개발의뢰--%></option>
            </select>
          </td>
          <td width="90px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></td>
          <td class="tdwhiteL"><input name="RequestNo" class="txt_field"  style="width:98%" value="<%=RequestNo%>"></td>
          <td width="90px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%></td>
          <td class="tdwhiteL0"><input type="text" name="DevProductName" class="txt_field" style="width:98%" value="<%=SDevProductName%>" ></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--접점고객--%></td>
          <td class="tdwhiteL"><input type="text" name="RequestBuyer" class="txt_field" style="width:70%" value="<%=SRequestBuyer%>">
            <a href="javascript:SearchUtil.selectOneSubContractorPopUp('selectSingleSubContractor');"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="javascript:deleteRequestBuyer()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
          <td width="90px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
          <td class="tdwhiteL"><input type="text" name="LastUsingBuyer" class="txt_field" style="width:70%" value="<%=SLastUsingBuyer%>">
            &nbsp;<a href="javascript:SearchUtil.selectOneLastUsingBuyerPopup('insertLastUsingBuyer')"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="javascript:deleteLastUsingBuyer()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02248") %><%--의뢰담당자--%></td>
            <td class="tdwhiteL0">
                <input type="text" name="userName"  id="userName" class="txt_field" style="width:70%" value="<%=SuserName%>">
                <input type="hidden" name="creator" id="creator" value="">
                &nbsp;<a href="javascript:selectUser()"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                <a href="javascript:deleteUser()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
            <td class="tdwhiteL">
                <input type="text" name="ProductCategoryName" class="txt_field" style="width: 70%">
                <input type="hidden" name="ProductCategoryCode" value="">
                <a href="javascript:SearchUtil.selectOnePartClazPopUp('setPartClaz', 'onlyLeaf=Y&openAll=N&depthLevel2=Y');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('ProductCategoryCode','ProductCategoryName');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"> <%if(divCode.equals("CA") || deptName.equals("전장IT개발팀") || deptName.equals("전장IT영업팀")){%><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}%></td>
            <td class="tdwhiteL">
                <input type="text" name="RepCarType" class="txt_field"  style="width:98%" value="<%=SRepCarType%>">
            </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00598") %><%--개발(검토)유형--%></td>
          <td class="tdwhiteL0">
                <select name="DevReviewTypeCode"  id="DevReviewTypeCode" class="fm_jmp" multiple="multiple">
                <%
                for ( int i=0; i<devReviewTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=devReviewTypeNumCode.get(i).get("oid") %>" <%=devReTypeCondList.contains( devReviewTypeNumCode.get(i).get("oid") ) ? " selected" : "" %>><%=devReviewTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
          <td class="tdwhiteL" colspan="3"><input type="text" name="predate"  id="predate" class="txt_field"  style="width:100px"   value="<%=searchCondition.getString("predate")%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('predate');" style="cursor: hand;">&nbsp;~&nbsp;
            <input type="text" name="postdate" id="postdate" class="txt_field"  style="width:100px"  value="<%=searchCondition.getString("postdate")%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('postdate');" style="cursor: hand;"></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
          <td class="tdwhiteL0">
            <select id="authorStatus" name="authorStatus" class="fm_jmp" style="width:100px" multiple="multiple">
                <%
                LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", WCUtil.getPDMLinkProduct().getContainerReference());
                Vector lcStates = LifeCycleHelper.service.findStates(LCtemplate);
                State lstate = null;

                for ( int i=0; i<lcStates.size(); i++ ) {

                    lstate = (State)lcStates.elementAt(i);
                    if ( authStatusCondList.size() > 0 ) {
                %>
                    <option value="<%=lstate %>"<%=authStatusCondList.contains(lstate.toString()) ? " selected" : "" %>><%=lstate.getDisplay(messageService.getLocale()) %></option>
                <%
                    } else {
                %>
                    <option value="<%=lstate %>"><%=lstate.getDisplay(messageService.getLocale()) %></option>
                <%
                    }
                }
                %>
                </select>
            </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%;min-height:200px;">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/dms/SearchCommisionGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Data_Param_Result="<%=tgData %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_DevRequest_List"
                        ></bdo>
                            <!-- Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp"
                            Page_Format="Internal"
                            Page_Data="TGData"
                            Page_Method="POST" -->
                    </div>
                </div>
            </div>
        </div>
  </td>
  </tr>
</table>
</form>
</body>
</html>
