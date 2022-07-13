<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Map,
                java.util.List,
                java.util.HashMap,
                java.util.Vector,
                java.util.ArrayList,
                java.text.SimpleDateFormat,
                java.util.Calendar"%>

<%@page import="wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.State,
                wt.session.SessionHelper,
                wt.org.WTUser"%>

<%@page import="java.util.Hashtable,
                e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.KETParamMapUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

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
    searchCondition.put("createEndDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    cal.add(Calendar.MONTH, -1);
    searchCondition.put("createStartDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
}

// 로그인한 사용자
WTUser loginUser = (WTUser)SessionHelper.manager.getPrincipal();

//사업부
Map<String, Object> parameter = new HashMap<String, Object>();
parameter.put("locale",   messageService.getLocale());
parameter.put("codeType", "DIVISIONFLAG");
List<Map<String, Object>> divisionCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
//단계구분 -  개발/양산구분
parameter.clear();
parameter.put("locale",   messageService.getLocale());
parameter.put("codeType", "DEVYN");
List<Map<String, Object>>  devynCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

ArrayList divisionCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdivisionFlag")), "," );               //  사업자 구분
ArrayList prodMoldClsCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HprodMoldCls")), "," );           //  ECO 구분
ArrayList devYnCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdevYn")), "," );                      //  단계구분
ArrayList stateCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HsancStateFlag")), "," );               //  상태

String createStartDate = StringUtil.checkNull(request.getParameter("createStartDate"));
String createEndDate = StringUtil.checkNull(request.getParameter("createEndDate"));
String autoSearch = StringUtil.checkNull(request.getParameter("autoSearch"));
//out.println(autoSearch);

String divisionFlag = "";
if ( CommonUtil.isMember("전자사업부") ) {
    divisionFlag = "E";
}
else if ( CommonUtil.isMember("자동차사업부") ) {
    divisionFlag = "C";
}
else if ( CommonUtil.isKETSUser() ) {
    divisionFlag = "K";
}

if ( divisionCondList.size() == 0 ) {
    if ( CommonUtil.isMember("전자사업부") ) {
        divisionCondList.add(0, "E");
    }
    else if ( CommonUtil.isMember("자동차사업부") ) {
        divisionCondList.add(0, "C");
    }
    else if ( CommonUtil.isKETSUser() ) {
        divisionCondList.add(0, "K");
    }
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><%=messageService.getString("e3ps.message.ket_message", "00184") %><%--ECO 검색--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 5px;
    margin-right: 10px;
    margin-bottom: 5px;
    
    min-width: 1000px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script src="/plm/jsp/ecm/SearchEco.js?ver=0.1"></script>

<script type="text/javascript">
//초기화
function doCancel(){
	document.forms[0].ecoId.value = "";
    document.forms[0].ecoName.value = "";
    document.forms[0].partNo.value = "";
    document.forms[0].partOid.value = "";
    document.forms[0].partName.value = "";
    document.forms[0].projectNo.value = "";
    document.forms[0].projectOid.value = "";
    document.forms[0].projectName.value = "";
    document.forms[0].createStartDate.value = "";
    document.forms[0].createEndDate.value = "";
    
    
    $("#prodMoldCls").multiselect("uncheckAll");//
    $("#sancStateFlag").multiselect("uncheckAll");
    
    // 결과내재검색 체크해제
    /* 
    $("input:checkbox[id=searchInSearch]").attr("checked", false);
    */
}

//담당자검색 팝업
function popupUser(){
    var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o";
    acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
    if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
        return;
    }
    // isAppend : Y - 이전 값에 현재 선택된 값 추가
    // isAppend : N - 현재 선택 된 값만 추가
    var isAppend = "Y";
    acceptUser(acceptUsers, isAppend);
}

function acceptUser(arrObj, isAppend) {
    var userId = new Array();     // Id
    var userName = new Array();   // Nmae
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] - wtuser oid // [1] - people oid // [2] - dept oid
        // [3] - user id    // [4] - name       // [5] - dept name
        // [6] - duty       // [7] - duty code  // [8] - email

        var infoArr = arrObj[i];
        userId[i] = infoArr[0];
        userName[i] = infoArr[4];
    }

    var tmpId = new Array();
    var tmpName = new Array();
    if ( $("#creatorOid").val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpId = $.merge($("#creatorOid").val().split(", "), userId);
        tmpId = $.unique(tmpId.sort());

        tmpName = $.merge($("#creatorName").val().split(", "), userName);
        tmpName = $.unique(tmpName.sort());
    }
    else {
        tmpId = userId.sort();
        tmpName = userName.sort();
    }

    $("#creatorOid").val(tmpId.join(", "));
    $("#creatorName").val(tmpName.join(", "));
}
// ==  [End] 사람 검색  == //


//연계ECR 검색 팝업 호출
function popupRelEcr(targetId) {
    var url = "/plm/jsp/ecm/ModalForm.jsp?url=/plm/jsp/ecm/SearchEcrPopupForm.jsp";
    url += "&obj=mode^m&obj=statusAll^Y";
    attache = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=1000px; dialogHeight:670px; center:yes");
    if (attache != undefined && attache.length > 0) {
        var isAppend = "Y";
        setRelEcr(targetId, attache, isAppend);
    }
}

function setRelEcr(targetId, arrObj, isAppend) {
    var partData = new Array();
    for ( var i=0; i < arrObj.length; i++ ) {
        // [0] -  oid       // [1] - ECR number
        var infoArr = arrObj[i];
        partData[i] = infoArr[1];
    }

    var tmpNo = new Array();
    if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
        // ID 중복 제거
        tmpNo = $.merge($("#"+targetId).val().split(", "), partData);
        tmpNo = $.unique(tmpNo.sort());
    }
    else {
        tmpNo = partData.sort();
    }

    $("#"+targetId).val(tmpNo.join(", "));
}


function activeCheck() {
    var form = document.forms[0];
    var active = form.acti.checked;
    if(active) {

        form.acti.value = "true";
        form.prodMoldCls.value = "1";
        form.sancStateFlag.value ="APPROVED";

    }else if(!active) {
        form.acti.value = "false";
        form.prodMoldCls.value = "";
        form.sancStateFlag.value = "";
    }
}

//==  [Start] 부서 검색  == //
function delDepartment(targetId, targetName) {
    $("#" + targetId).val("");
    $("#" + targetName).val("");
}

function addDepartment() {
    var url = "/plm/jsp/project/AddProjectDeptFrm.jsp?mode=m";
    attacheDept = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=430px; dialogHeight:465px; center:yes");
    if(typeof attacheDept == "undefined" || attacheDept == null) {
        return;
    }

    if ( typeof(attacheDept) != "object" ) {
        var deptSplit = attacheDept.split(",");
        for ( var i=0; i<deptSplit.length-1; i++ ) {
            var param = "command=deptInfo&deptOid=" + deptSplit[i];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
            callServer(url, acceptDept1);
        }
    }
    else {
        var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
        var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;
        callServer(url, acceptDept1);
    }
}

function acceptDept1(req) {
    var xmlDoc = req.responseXML;
    var showElements = xmlDoc.selectNodes("//message");
    var msg = showElements[0].getElementsByTagName("l_message")[0].text;
    if(msg != 'true') {
        alert('<%=messageService.getString("e3ps.message.ket_message", "01184") %><%--다시 시도하시기 바랍니다--%>');
        return;
    }

    showElements = xmlDoc.selectNodes("//data_info");
    var l_oid = showElements[0].getElementsByTagName("l_oid");
    var l_name = showElements[0].getElementsByTagName("l_name");
    var l_code = showElements[0].getElementsByTagName("l_code");
    var l_chiefOid = showElements[0].getElementsByTagName("l_chiefOid");
    var l_chiefName = showElements[0].getElementsByTagName("l_chiefName");

    deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "orgOid", "orgName");
}

function deptMerge(deptOid, deptName, targetId, targetName) {
    if ( $("#"+targetId).val() == "" ) {
        $("#"+targetId).val( deptOid );
        $("#"+targetName).val( deptName );
    }
    else {
        var deptOidArr = $("#"+targetId).val().split(", ");
        var deptNameArr = $("#"+targetName).val().split(", ");
        // 선택된 부서 push
        deptOidArr.push(deptOid);
        deptNameArr.push(deptName);
        // 중복 부서 정리
        deptOidArr = $.unique(deptOidArr.sort());
        deptNameArr = $.unique(deptNameArr.sort());

        $("#"+targetId).val( deptOidArr.join(", ") );
        $("#"+targetName).val( deptNameArr.join(", ") );
    }
}
// ==  [End] 부서 검색  == //

function perPage(v) {
    document.forms[0].perPage.value = v;

    //doSearch();
    loadEjsGrid();
}


//자료 검색
function doSearch(){

  var url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
  
      //showProcessing();     // 진행중 Bar 표시
      //disabledAllBtn();     // 버튼 비활성화

      loadEjsGrid();
  
}


function loadEjsGrid(){
  
  try{
     
     var idx = 0;
     var D = Grids[idx].Data;
     var formName = "form01";
     
     D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
     
     D.Data.Url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
     D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
     D.Data.Param.cmd = "searchGridData4MyEcm";
     //D.Data.Params = "command=searchGridData&create_start=" + $("#create_start").val() + "&create_end=" + $("#create_end").val() + "&perPage="+ $("input[name='perPage']").val();
     
     D.Page.Url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
     D.Page.Params =  D.Data.Params;
     D.Page.Param.cmd = "searchGridPage4MyEcm";
     
     Grids[idx].Reload(D);
     //Grids[idx].ReloadBody(null,idx,"Search");
     
     var S = document.getElementById("Status"+idx);
     if(S) S.innerHTML="Loading";
  
  }catch(e){
      alert(e.message);
  }
}

function checkAfterPopupProject(targetId){
    $('input[name="searchPjtType"]').each(function(){
        if($(this).prop('checked')){
            if("제품" == $(this).val()){
                popupProjectProject(targetId);
            }
            else if("금형" == $(this).val()){
                popupMoldProject(targetId);
            }
        }
    });
}

var loadFlag = true;
//Jquery
$(document).ready(function(){
	treeGridResize("#EcoListSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	//자동완성, 달력 bind 시작
    CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');

    SuggestUtil.bind('ECONO', 'ecoId');
    SuggestUtil.bind('DEPARTMENT', 'orgName', 'orgOid');
    SuggestUtil.bind('PARTNO', 'partNo', 'partOid');
    SuggestUtil.bind('ECRNO', 'ecrNumber', 'ecrOid');
    SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
    
    
 // Project 구분 radio click event
    $("[name=searchPjtType]").click(function() {
        CommonUtil.deleteValue('searchPjtNoTxt', 'projectNo', 'projectOid');
        
        if("제품" == $(this).val()){
            SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', 'projectOid');
        }
        else if("금형" == $(this).val()){
            SuggestUtil.bind('DIENO', 'projectNo', 'projectOid');
        }
    });
    
    
    var searchPjtType = '<%=StringUtil.checkNull( (String)searchCondition.get("searchPjtType") )%>';
    
    $('input[name="searchPjtType"]').each(function(){
        if("제품" == $(this).val()){
           if(searchPjtType == $(this).val() || searchPjtType == ''){
               SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', 'projectOid');
               $(this).attr('checked',true);
           }
        }
        else if("금형" == $(this).val()){
            if(searchPjtType == $(this).val()){
              SuggestUtil.bind('DIENO', 'projectNo', 'projectOid');
              $(this).attr('checked',true);
            }
        }
    });
    //자동완성, 달력 bind 끝

    $("#divisionFlag").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });

    $("#prodMoldCls").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    $("#devYn").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    $("#sancStateFlag").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });


    $("form[name=form01]").keypress(function(e) {
        if ( e.which == 13 ) {
            doSearch();
            return false;
        }
    });

    $("#acti").click( function() {
        if ( $(this).is(':checked') == true ) {
            $("#prodMoldCls").val("1").attr("selected", true);
            $("#prodMoldCls").multiselect("refresh");
            $("#sancStateFlag").val("APPROVED").attr("selected", true);
            $("#sancStateFlag").multiselect("refresh");
        }else {
            $("#prodMoldCls").multiselect("uncheckAll");
            $("#prodMoldCls").multiselect("refresh");
            $("#sancStateFlag").multiselect("uncheckAll");
            $("#sancStateFlag").multiselect("refresh");
        }
    });
    Grids.OnRenderFinish = function(){
        if(loadFlag){
            loadFlag = false;
            loadEjsGrid();
        }
    }
    //loadEjsGrid();
    //doSearch();
});



function lfn_toggleConditions() {
	if($("#divToggleConditions").css("display") == "none"){
		$("#divToggleConditions").show();
		   
	} else {
		$("#divToggleConditions").hide();
        
	}
}
-->
</script>

<script type="text/javascript">

    <%--등록--%>
    function doCreate(type) {
        
        var url = "";
        if(type == 'PROD') {
            url = "/plm/jsp/ecm/CreateProdEcoForm.jsp";
        
        } else if(type == 'MOLD') {
            url = "/plm/jsp/ecm/CreateMoldEcoForm.jsp";
            
        }
        
        var name = "CreateECO"+ type;
        var opt = launchCenter(1024, 768);
        opt += ", resizable=yes";
        var windowWin = window.open(url,name,opt);
        
        //windowWin.resizeTo(width,height);
        windowWin.focus();
        
    }

    <%--조회--%>
    function doView(url) {
        
        var name = "ViewECO";
        if(url.indexOf('e3ps.ecm.entity.KETProdChangeOrder') > 0) {
            name += 'PROD';
        } else {
            name += 'MOLD';
        }
        var opt = launchCenter(1024, 768);
        opt += ", resizable=yes";
        var windowWin = window.open(url,name,opt);
        
        //windowWin.resizeTo(width,height);
        windowWin.focus();
        
    }

    <%--CRUD 팝업에서 호출되는 Function--%>
    function feedbackAfterPopup(obj) {
        //alert('obj is '+ obj);
        if(typeof obj != 'undefined') {
            
            if(obj == 'doReSearching') {
            	doSearch();
            }
            
            
        }
    }    
    
</script>

</head>

<body class="body-space">
<form name="form01" method="post" action="">
<input type="hidden" name="cmd" value="search">
<input type="hidden" name="param" value="parent.">
<input type="hidden" name="autoSearch" value="Y">
<input type="hidden" name="oid" value="">

<input type="hidden" name="HdivisionFlag" value="">
<input type="hidden" name="HprodMoldCls" value="">
<input type="hidden" name="HdevYn" value="">
<input type="hidden" name="HsancStateFlag" value="">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />

<table width="100%" height="100%">
  <tr>
    <td valign="top">
      <table style="width: 100%;">
        <tr>
          <td>
          <table style="width: 100%; height: 28px;">
              <tr>
                <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png" onclick="javascript:lfn_toggleConditions();"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "04850") %><%--My ECM--%></td>
                <!-- td align="right">
                  <img src="/plm/portal/images/icon_navi.gif">Home
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00186") %><%--ECO 관리--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00184") %><%--ECO 검색--%>
                </td -->
                <td align="right">
                  <img src="/plm/portal/images/icon_navi.gif">Home
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02435") %><%--작업공간--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "04850") %><%--My ECM--%>
                </td>                
              </tr>
            </table>
        </td>
        </tr>
        <tr>
          <td  class="head_line"></td>
        </tr>
        <tr>
          <td class="space10"></td>
        </tr>
      </table>


    <div id="divToggleConditions" name="divToggleConditions">
      <table style="width: 100%;">
        <tr>
        
        
          <!-- td align="left">
                <table>
                <tr>
                    <td>
                       <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:doCreate('PROD');" class="btn_blue">제품등록<%--제품등록--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                       <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:doCreate('MOLD');" class="btn_blue">금형등록<%--금형등록--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                </tr>
                </table>
          </td -->
            
                  
          <td align="right">
          <table>
              <tr>
                <!-- td>
                  <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                </td>
                <td style="width: 15px;">&nbsp;</td -->
                <td>
                <table>
                    <tr>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doCancel();"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                <td>
                <table>
                    <tr>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doSearch();"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <table style="width: 100%;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <!-- 검색영역 collapse/expand -->
      <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
      <table style="width: 100%;">
        <tr>
          <td  class="tab_btm2"></td>
        </tr>
      </table>
      
      <table style="width: 100%;">
      
        <input type="hidden" id="creatorOid" name="creatorOid" value="<%=CommonUtil.getOIDString(loginUser) %>">
            
        <tr>
          <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
          <td class="tdwhiteL">
            <select name="prodMoldCls" id="prodMoldCls" class="fm_jmp" multiple="multiple">
              <option value="1" <%=prodMoldClsCondList.contains("1") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04860") %><%--제품 ECO--%></option>
              <option value="2" <%=prodMoldClsCondList.contains("2") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04870") %><%--금형 ECO--%></option>
              <option value="3" <%=prodMoldClsCondList.contains("3") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04880") %><%--제품 ECR--%></option>
              <option value="4" <%=prodMoldClsCondList.contains("4") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04890") %><%--금형 ECR--%></option>
            </select>
          </td>
          <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01486") %><%--No--%></td>
          <td class="tdwhiteL"><input type="text" name="ecoId" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecoId") )%>" style="width:98%"></td>
          <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
          <td class="tdwhiteL0"><input type="text" name="ecoName" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecoName") )%>" style="width:98%;" id="textfield4"></td>
        </tr>  
        <tr>
          <td class="tdblueL">Project No</td>
          <td colspan="3" class="tdwhiteL">
            <input type="radio" name="searchPjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
            <input type="radio" name="searchPjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%> 
            <input type="text" name="projectNo" id="projectNo" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("projectNo") )%>" style="width:220px">
            <input type="hidden" name="projectOid" id="projectOid" value="<%=StringUtil.checkNull( (String)searchCondition.get("projectOid") )%>">
            <a href="javascript:checkAfterPopupProject('projectNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
            <a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(2);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
          <td class="tdblueL">Project Name</td>
          <td class="tdwhiteL0">
            <input type="text" name="projectName" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("projectName") )%>" style="width:98%">
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
          <td colspan="3" class="tdwhiteL">
            <!--<input type="text" name="createStartDate" id="createStartDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createStartDate") )%>">  -->
            <input type="text" name="createStartDate" id="createStartDate" class="txt_field"  style="width:70px" value="">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createStartDate');" style="cursor: hand;">
                &nbsp;~&nbsp;
            <!--<input type="text" name="createEndDate" id="createEndDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createEndDate") )%>">-->    
            <input type="text" name="createEndDate" id="createEndDate" class="txt_field"  style="width:70px" value="">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createEndDate');" style="cursor: hand;">
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
          <td class="tdwhiteL0">
             <select name="sancStateFlag" id="sancStateFlag" class="fm_jmp" multiple="multiple">
                <option value="INWORK"  selected <%=stateCondList.contains("INWORK") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02441") %><%--작업중--%></option>
                <option value="APPROVEDFILING" selected  <%=stateCondList.contains("APPROVEDFILING") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04830") %><%--제기승인--%></option>
                <option value="CONSIDER" selected <%=stateCondList.contains("CONSIDER") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04840") %><%--검토--%></option>
                <option value="PLANNING" selected <%=stateCondList.contains("PLANNING") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "00815") %><%--계획수립--%></option>
                <option value="EXCUTEACTIVITY" selected <%=stateCondList.contains("EXCUTEACTIVITY") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "03243") %><%--활동수행--%></option>
                <option value="ACTIVITYCOMPLETED" selected <%=stateCondList.contains("ACTIVITYCOMPLETED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "03244") %><%--활동완료--%></option>
                <option value="UNDERREVIEW" selected <%=stateCondList.contains("UNDERREVIEW") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "00732") %><%--검토중--%></option>
                <option value="APPROVED" <%=stateCondList.contains("APPROVED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "01996") %><%--승인완료--%></option>
                <option value="REJECTED" <%=stateCondList.contains("REJECTED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "01468") %><%--반려됨--%></option>
                <option value="REWORK" selected <%=stateCondList.contains("REWORK") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02451") %><%--재작업--%></option>
              </select>
          </td>          
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
          <td class="tdwhiteL">
            <input type="text" name="partNo" id="partNo" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("partNo") )%>" style="width:75%">
            <input type="hidden" name="partOid" id="partOid">
            <a href="javascript:popupPart('partNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
            <a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(1);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
          <td class="tdwhiteL"><input type="text" name="partName" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("partName") )%>" style="width:98%"></td>
          <!--  단계구분  -->
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
          <td class="tdwhiteL0">
            <select name="devYn" id="devYn" class="fm_jmp" multiple="multiple">
            <%
            for ( int i=0; i<devynCode.size(); i++ ) {
            %>
              <option value="<%=devynCode.get(i).get("code") %>" <%=devYnCondList.contains( devynCode.get(i).get("code") ) ? " selected" : "" %>><%=devynCode.get(i).get("value")%></option>
            <%
            }
            %>
            </select>
          </td>
        </tr>

      </table>
      <table style="width: 100%;">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>

    </div>


              <!-- div class="content-main">
                <div class="container-fluid">
                    <div class="row-fluid" -->
                    
                        <div id="listGrid" style="WIDTH:100%;HEIGHT:100%">
                            <bdo 
                                <%--
                                Debug="1" AlertError="0"
                                Layout_Url="/plm/jsp/ecm/SearchMyEcmListGridLayout.jsp"
                                Layout_Param_Pagingsize="<%=pagingSize %>"
                                Data_Url="/plm/jsp/common/searchGridData.jsp"
                                Data_Method="POST"
                                Data_Param_Result="<%=tgData %>"
                                Data_Param_Pagingsize="<%=pagingSize %>"
                                
                                Debug='3' 
                                --%>
                                Debug="1"
                                AlertError="0"
                                
                                Layout_Url="/plm/jsp/ecm/reform/SearchMyEcmListGridLayout.jsp"
                                Layout_Param_Pagingsize="<%=pagingSize %>"

                                Data_Url="/plm/servlet/e3ps/SearchMoldEcoServlet?cmd=searchGridData4MyEcm&creatorOid=<%=CommonUtil.getOIDString(loginUser) %>&createStartDate=<%=StringUtil.checkNull( (String)searchCondition.get("createStartDate") )%>&createEndDate=<%=StringUtil.checkNull( (String)searchCondition.get("createEndDate") )%>"
                                Data_Format="Internal"
                                Data_Data="TGData"
                                Data_Method="POST"
                                
                                Page_Url="/plm/servlet/e3ps/SearchMoldEcoServlet?cmd=searchGridPage4MyEcm&creatorOid=<%=CommonUtil.getOIDString(loginUser) %>&createStartDate=<%=StringUtil.checkNull( (String)searchCondition.get("createStartDate") )%>&createEndDate=<%=StringUtil.checkNull( (String)searchCondition.get("createEndDate") )%>"
                                Page_Format="Internal"
                                Page_Data="TGData"
                                Page_Method="POST"
                                                            
                            ></bdo>
                        </div>
                        
                    <!-- /div>
                </div>
            </div -->

    </td>
  </tr>
  <!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" width="780" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr -->
</table>
<iframe name="download" align="center" width="0" height="0" border="0" style="display:none"></iframe>
</form>
</body>
</html>
