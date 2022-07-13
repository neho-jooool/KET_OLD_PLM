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
                wt.lifecycle.State"%>

<%@page import="java.util.Hashtable,
                e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.KETObjectUtil,
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
<title><%=messageService.getString("e3ps.message.ket_message", "04610") %><%--ECN 검색--%></title>
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

<script src="/plm/jsp/ecm/SearchEco.js"></script>

<script type="text/javascript">
//초기화
function doCancel(){
	$("input[name='ecoId']").val("");

	$("input[name='ecoName']").val("");
	$("input[name='creatorName']").val("");
	$("input[name='creatorOid']").val("");
	$("input[name='creator']").val("");
	$("input[name='projectNo']").val("");
	$("input[name='projectOid']").val("");
	$("input[name='projectName']").val("");
	$("input[name='createStartDate']").val("");
	$("input[name='createEndDate']").val("");
	$("input[name='sancStateFlag']").val("");
	$("input[name='completeStartDate']").val("");
	$("input[name='completeEndDate']").val("");
	$("input[name='ecnNumber']").val("");
	$("input[name='ecnOid']").val("");
	
    $("#sancStateFlag").multiselect("uncheckAll");
    
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
       D.Data.Param.cmd = "searchGridData4Ecn";
       //D.Data.Params = "command=searchGridData&create_start=" + $("#create_start").val() + "&create_end=" + $("#create_end").val() + "&perPage="+ $("input[name='perPage']").val();
       
       D.Page.Url = "/plm/servlet/e3ps/SearchMoldEcoServlet";
       D.Page.Params =  D.Data.Params;
       D.Page.Param.cmd = "searchGridPage4Ecn";
       
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

//Jquery
$(document).ready(function(){
	treeGridResize("#EcoListSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
	//자동완성, 달력 bind 시작
    CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');
    CalendarUtil.dateInputFormat('completeStartDate', 'completeEndDate');

    SuggestUtil.bind('ECONO', 'ecoId');
    SuggestUtil.bind('ECNNO', 'ecnNumber', 'ecnOid');
    SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
    SuggestUtil.bind('USER', 'ecnUserName', 'ecnUserOid');
    SuggestUtil.bind('DEPARTMENT', 'deptCodeName', 'deptCode');
    
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

    <%--중지--%>
    function doStopEcn() {
    	var param = "";
        var G = Grids[0];
        if( G != null ){

            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('<%= messageService.getString("e3ps.message.ket_message", "04680") %>');<%--ECN을 선택해 주십시오--%>
                return;
            }

            for ( var i = 0; i < R.length; i++) {
            	param += "&ecnOid="+ R[i].EcnOid;
                
            }
        }

        var url = "/plm/servlet/e3ps/ProdEcoServlet"
                + "?cmd=stopEcn"
                + param
                ;
        
        showProcessing();     // 진행중 Bar 표시
        //disabledAllBtn();     // 버튼 비활성화
        
        document.forms[0].action= url;
        document.forms[0].target='download';
        document.forms[0].submit();
        
    }
    function lfn_feedbackAfterStopEcn() {
    	hideProcessing();
    	doSearch();
    }
    
    <%--재시작--%>
    function doRestartEcn() {
        var param = "";
        var G = Grids[0];
        if( G != null ){

            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert('<%= messageService.getString("e3ps.message.ket_message", "04680") %>');<%--ECN을 선택해 주십시오--%>
                return;
            }

            for ( var i = 0; i < R.length; i++) {
                param += "&ecnOid="+ R[i].EcnOid;
                
            }
        }

        var url = "/plm/servlet/e3ps/ProdEcoServlet"
                + "?cmd=restartEcn"
                + param
                ;
        
        showProcessing();     // 진행중 Bar 표시
        //disabledAllBtn();     // 버튼 비활성화
        
        document.forms[0].action= url;
        document.forms[0].target='download';
        document.forms[0].submit();
        
    }
    function lfn_feedbackAfterRestartEcn() {
        hideProcessing();
        doSearch();
    }
    
    <%--ECO 조회--%>
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

    function addAllDepartment(valueField, displayField, root){
    	SearchUtil.addDepartment(false,valueField, displayField);
    }
    
    function delDepartment(targetId, targetName) {
        $("#" + targetId).val("");
        $("#" + targetName).val("");
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
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "04610") %><%--ECN 검색--%></td>
                <td align="right">
                  <img src="/plm/portal/images/icon_navi.gif">Home
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "ECM") %><%--ECM--%>
                  <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "04111") %><%--ECN--%>
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
        
          <td align="left">
          <table>
             <tr>
                <% 
                if(KETObjectUtil.isAdmin()) {
                %>
                <td>
                <table>
                    <tr>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doStopEcn();"><%=messageService.getString("e3ps.message.ket_message", "02695") %><%--중지--%></a></td>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>
                
                <td>
                <table>
                    <tr>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#" class="btn_blue" onfocus="this.blur();" onClick="javascript:doRestartEcn();"><%=messageService.getString("e3ps.message.ket_message", "02446") %><%--재시작--%></a></td>
                      <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table></td>
                <td width="5">&nbsp;</td>  
                <%
                }
                %>              
             </tr>
          </table>
          </td>
                   
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
        <tr>
          <td class="tdblueL" style="width: 110px;">ECO No</td>
          <td class="tdwhiteL"><input type="text" name="ecoId" class="txt_field" value="" style="width:98%"></td>
          <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00193") %><%--ECO 제목--%></td>
          <td class="tdwhiteL"><input type="text" name="ecoName" class="txt_field" value="" style="width:98%;" id="textfield4"></td>
          <td class="tdblueL">ECO <%=messageService.getString("e3ps.message.ket_message", "02431") %> <%--작성자--%></td>
          <td class="tdwhiteL0">
            <input type="text" name="creatorName" id="creatorName" class="txt_field" value="" style="width:75%">
            <input type="hidden" id="creatorOid" name="creatorOid" value="">
            <input type="hidden" id="creator" name="creator">
            <a href="javascript:SearchUtil.selectOneUser('creatorOid','creatorName');"> <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('creatorOid','creatorName');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a>          </td>
        </tr>  
        <tr>
          <td class="tdblueL">Project No</td>
          <td colspan="3" class="tdwhiteL">
            <input type="radio" name="searchPjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
            <input type="radio" name="searchPjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%> 
            <input type="text" name="projectNo" id="projectNo" class="txt_field" value="" style="width:220px">
            <input type="hidden" name="projectOid" id="projectOid" value="">
            <a href="javascript:checkAfterPopupProject('projectNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
            <a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(2);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
          </td>
          <td class="tdblueL">Project Name</td>
          <td class="tdwhiteL0">
            <input type="text" name="projectName" class="txt_field" value="" style="width:98%">
          </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04590") %><%--ECO 등록일--%></td>
          <td colspan="3" class="tdwhiteL">
            <input type="text" name="createStartDate" id="createStartDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createStartDate") )%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createStartDate');" style="cursor: hand;">
                &nbsp;~&nbsp;
            <input type="text" name="createEndDate" id="createEndDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createEndDate") )%>">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createEndDate');" style="cursor: hand;">
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04570") %><%--ECN 진행상태--%></td>
          <td class="tdwhiteL0">
             <select name="sancStateFlag" id="sancStateFlag" class="fm_jmp" multiple="multiple">
                <option value="DELAY" <%=stateCondList.contains("DELAY") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></option>
                <option value="PLANNING" <%=stateCondList.contains("PLANNING") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "00815") %><%--계획수립--%></option>
                <option value="EXCUTEACTIVITY" <%=stateCondList.contains("EXCUTEACTIVITY") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "03243") %><%--활동수행--%></option>
                <option value="ACTIVITYCOMPLETED" <%=stateCondList.contains("ACTIVITYCOMPLETED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "03244") %><%--활동완료--%></option>
                <option value="STOPED" <%=stateCondList.contains("STOPED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02700") %><%--중지됨--%></option>
              </select>
          </td>          
        </tr>
        <tr>
          <td class="tdblueL">ECN <%=messageService.getString("e3ps.message.ket_message", "04600") %><%--완료일--%></td>
          <td colspan="3" class="tdwhiteL">
            <input type="text" name="completeStartDate" id="completeStartDate" class="txt_field"  style="width:70px" value="">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('completeStartDate');" style="cursor: hand;">
                &nbsp;~&nbsp;
            <input type="text" name="completeEndDate" id="completeEndDate" class="txt_field"  style="width:70px" value="">
            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('completeEndDate');" style="cursor: hand;">
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04530") %><%--ECN 번호--%></td>
          <td class="tdwhiteL0"><input type="text" name="ecnNumber" class="txt_field" value="" style="width:98%"></td> 
        </tr>
        
        <tr>
          
          <td class="tdblueL">ECN <%=messageService.getString("e3ps.message.ket_message", "01206") %><%--담당자부서--%></td>
          <td class="tdwhiteL" colspan="3">
            <input style="width:70%;" type="text" id="deptCodeName" name="deptCodeName" class="txt_field" value="">
            
            <input type=hidden id="deptCode" name="deptCode" value="">
            
            
            <a href="javascript:addAllDepartment('deptCode', 'deptCodeName',true);">
            
            
            
            <img src="/plm/portal/images/icon_5.png"></a>
            <a href="javascript:delDepartment('deptCode','deptCodeName');"><img src="/plm/portal/images/icon_delete.gif"></a>
          </td>
          <td class="tdblueL">ECN <%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
          <td class="tdwhiteL">
            <input type="text" name="ecnUserName" id="ecnUserName" class="txt_field" value="" style="width:75%">
            <input type="hidden" id="ecnUserOid" name="ecnUserOid" value="">
            <input type="hidden" id="ecnUser" name="ecnUser">
            <a href="javascript:SearchUtil.selectOneUser('ecnUserOid','ecnUserName');"> <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
            <a href="javascript:CommonUtil.deleteValue('ecnUserOid','ecnUserName');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
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
                                Layout_Url="/plm/jsp/ecm/SearchEcoListGridLayout.jsp"
                                Layout_Param_Pagingsize="<%=pagingSize %>"
                                Data_Url="/plm/jsp/common/searchGridData.jsp"
                                Data_Method="POST"
                                Data_Param_Result="<%=tgData %>"
                                Data_Param_Pagingsize="<%=pagingSize %>"
                                
                                Debug='3' 
                                --%>
                                Debug="1"
                                AlertError="0"
                                
                                Layout_Url="/plm/jsp/ecm/reform/SearchEcnListGridLayout.jsp"
                                Layout_Param_Pagingsize="<%=pagingSize %>"

                                Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
                                Data_Format="Internal"
                                Data_Data="TGData"
                                Data_Method="POST"
                                
                                Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp"
                                Page_Format="Internal"
                                Page_Data="TGData"
                                Page_Method="POST"
                                                  
                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Ecn_List"          
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
