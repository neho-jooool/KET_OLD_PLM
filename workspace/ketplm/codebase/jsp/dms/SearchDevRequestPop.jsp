<%@page import="e3ps.common.util.KETStringUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="e3ps.groupware.company.PeopleData"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="e3ps.common.util.StringUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<%
    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    //EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");
    String pagingSize = null;

    if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    

    String method = StringUtil.trimToEmpty(request.getParameter("method"));
    if( (method == null) || (method.trim().length() == 0)){
        method = "setDevRequest";
    }

    String mode = StringUtil.trimToEmpty(request.getParameter("mode"));
    if( (mode == null) || (mode == "multi") || (mode.trim().length() == 0)){
        mode = "multi";
    }else{
        mode = "one";
    }

    String developmentStep = StringUtil.trimToEmpty(request.getParameter("developmentStep"));
    if( (developmentStep == null) || (developmentStep.trim().length() == 0)){
        developmentStep = "R";
    }

    String modal = StringUtil.trimToEmpty(request.getParameter("modal"));
    if( (modal == null) || (modal.trim().length() == 0)){
        modal = "N";
    }
    
    WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
    String userName = user.getFullName();
    PeopleData peoData = new PeopleData(user);
    String deptName = peoData.departmentName;
    String divCode = "";
    String projectType = "";

    if(CommonUtil.isMember("전자사업부") && !deptName.equals("전장IT개발팀")){
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
    String isPopup = StringUtil.checkNull(request.getParameter("isPopup"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<%@include file="/jsp/common/multicombo.jsp"%>
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<%@include file="/jsp/common/processing.html" %>

<script type="text/javascript">
<!--

function perPage(v) {
    document.form01.perPage.value = v;

    doSubmit();
}

function doSubmit(){
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

function doSelect() {

    var G = Grids[0];
    var arr = new Array();
    var subarr = new Array();//0:code, 1:name
    var idx = 0;

    if( G != null ){
        var R = G.GetSelRows();

        if( R.length == 0 ){
            alert('<%=messageService.getString("e3ps.message.ket_message", "02255") %><%--의뢰서가 선택되지 않았습니다--%>');
            return;
        }

        for(var i=0,del=0;i<R.length;i++){
            subarr = new Array();
            subarr[0] = R[i].Oid;
            subarr[1] = R[i].RequestNo;
            subarr[2] = R[i].DevProdName;
            subarr[3] = R[i].Step;
            arr[idx++] = subarr;
        }
    }

    if( document.form01.modal.value == 'N' ){
        if(opener) {
            if(opener.<%=method%>) {
                opener.<%=method%>(arr);
            }
        }
        window.close();
    }else{
        window.returnValue = arr;
        window.close();
    }
}


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

function insertRequestBuyer() {
    var fm = document.form01
    var arrObj0;
    var arrObj;

    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SUBCONTRACTOR&command=select&mode=one";

    arrObj0 = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:500px; center:yes");
    if(typeof arrObj0 == "undefined" || arrObj0 == null) {
      return;
    }
    arrObj = arrObj0[0];
    //alert("arrObj==>"+arrObj[0]+", "+arrObj[1]+", "+arrObj[2]);
    fm.RequestBuyer.value= arrObj[2];
}

function deleteRequestBuyer() {
    var fm = document.form01;

    fm.RequestBuyer.value= "";
}

function insertLastUsingBuyer() {
    var fm = document.form01;
    var arrObj0;
    var arrObj;

    var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=1&selectedDepth=1&codetype=CUSTOMEREVENT&command=select&mode=one&designTeam=";
    arrObj0 = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
    if(typeof arrObj0 == "undefined" || arrObj0 == null) {
      return;
    }
    arrObj = arrObj0[0];
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
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
    attacheMembers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if ( typeof attacheMembers == "undefined" || attacheMembers == null ) {
        return;
    }
    // isAppend : Y - 이전 값에 현재 선택된 값 추가
    // isAppend : N - 현재 선택 된 값만 추가
    var isAppend = "N";
    acceptMember(attacheMembers, isAppend);
}

function acceptMember(arrObj, isAppend) {
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

function selectPartCategory(checkedNode){
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

function doClear1(){
    document.form01.reset();
}
$(document).ready(function(){
    $("form[name=form01]").keypress(function(e) {
        //Enter key
        if ( e.which == 13 ) {
        	doSubmit();
            return false;
        }
    });
    
    $(function(){
        $("#DevReviewTypeCode").multiselect({
            minWidth : 130,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#ProductCategoryCode").multiselect({
            minWidth : 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#authorStatus").multiselect({
            minWidth : 80,
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
    });
    treeGridResize("#CommisionSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});
//-->
</script>
</head>

<body>
<form name="form01" method="post" >
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
<input type="hidden" name="modal" value="<%=modal%>">
<input type="hidden" name="isPopup" value="<%=isPopup%>">
<table style="width: 100%; height: 100%;">
    <tr>
        <td height="50" valign="top">
            <table style="width: 100%;">
            <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                    <table style="height: 28px;">
                    <tr>
                        <td width="7px"></td>
                        <td width="20px"><img src="/plm/portal/images/icon_3.png"></td>
                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00600") %><%--개발(검토)의뢰 검색--%></td>
                    </tr>
                    </table>
                </td>
                <td width="136" align="right">
                   <img src="/plm/portal/images/logo_popup.png">
                </td>
            </tr>
            </table>
        </td>
    </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
                <table>
                    <tr>
                        <td>
                            <table>
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doClear1();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td>
                            <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td>
                            <table>
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSubmit();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td>
                            <table>
                            <tr>
                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                            </tr>
                            </table>
                        </td>
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
      <table border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
        <tr>
          <td width="70px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td class="tdwhiteL"><input type="hidden" name="DevelopmentStep" id="DevelopmentStep" value="<%=developmentStep%>">
            <%=developmentStep.equals("R") ? messageService.getString("e3ps.message.ket_message", "00729"):messageService.getString("e3ps.message.ket_message", "00656")%>
          </td>
          <td width="70px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02249") %><%--의뢰번호--%></td>
          <td class="tdwhiteL"><input name="RequestNo" class="txt_field"  style="width:98%" value=""></td>
          <td width="90px" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00664") %><%--개발제품명--%></td>
          <td class="tdwhiteL0"><input type="text" name="DevProductName" class="txt_field" style="width:98%" value="" ></td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02261") %><%--의뢰처--%></td>
          <td class="tdwhiteL"><input type="text" name="RequestBuyer" class="txt_field" style="width:68%" value="">
            <a href="javascript:insertRequestBuyer()"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="javascript:deleteRequestBuyer()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
          <td class="tdwhiteL"><input type="text" name="LastUsingBuyer" class="txt_field" style="width:67%" value="">
            &nbsp;<a href="javascript:insertLastUsingBuyer()"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="javascript:deleteLastUsingBuyer()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02248") %><%--의뢰담당자--%></td>
            <td class="tdwhiteL0">
                <input type="text" name="userName"  id="userName" class="txt_field" style="width:68%" value="">
                <input type="hidden" name="creator" id="creator" value="">
                &nbsp;<a href="javascript:selectUser()"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                <a href="javascript:deleteUser()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
            <td class="tdwhiteL">
                <input type="text" name="ProductCategoryName" class="txt_field" style="width: 68%">
                <input type="hidden" name="ProductCategoryCode" value="">
                <a href="javascript:SearchUtil.selectPartClaz(selectPartCategory,'onlyLeaf=Y&openAll=N&depthLevel2=Y');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('ProductCategoryCode','ProductCategoryName');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"> <%if(divCode.equals("CA")){%><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%><%}else{%><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%}%></td>
            <td class="tdwhiteL">
                <input type="text" name="RepCarType" class="txt_field"  style="width:98%" value="">
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
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="100%">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
        <div id="listGrid" style="WIDTH:100%;HEIGHT:100%;min-height:200px;">
            <bdo Debug="1" AlertError="0"
                Layout_Url="/plm/jsp/dms/SearchCommisionGridLayout.jsp"
                Layout_Param_Pagingsize="<%=pagingSize %>"
                Layout_Param_Searchpopup="Y"
                Layout_Param_Mode="<%=mode%>"
                Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
                Data_Method="POST"
                Data_Param_Result="<%=tgData %>"
                Data_Param_Pagingsize="<%=pagingSize %>"
                Data_Param_Result="<%=tgData %>"
                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_DevRequest_List"
            ></bdo>
        </div>
  </td>
  </tr>
</table>
</form>
</body>
</html>
