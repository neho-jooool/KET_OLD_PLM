<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Map,
                java.util.List,
                java.util.HashMap,
                java.util.ArrayList"%>

<%@page import="e3ps.common.util.*,
                e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    //EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;

    if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

     pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

     pagingSize = pagingSizeDefault;
    }
    //EJS TreeGrid End

    if ( searchCondition.isEmpty() ) {
     Calendar cal = Calendar.getInstance();
     searchCondition.put("createEndDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
     cal.add(Calendar.MONTH, -1);
     searchCondition.put("createStartDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 사업부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONFLAG");
    List<Map<String, Object>> divisionCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    //단계구분 -  개발/양산구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVYN");
    List<Map<String, Object>>  devynCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // ECR 구분 - 신규부품유형
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "NEWPARTTYPE");
    List<Map<String, Object>> newPartTypeCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    // 금형변경
    parameter = new HashMap<String, Object>();
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MOLDCHANGE");
    List<Map<String, Object>> moldChangeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

    // 원가변동
    parameter = new HashMap<String, Object>();
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "COSTCHANGE");
    List<Map<String, Object>> costChangeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

    // 설계변경사유
    parameter = new HashMap<String, Object>();
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PRODCHAGEREASON");
    List<Map<String, Object>> prodChangeReasonList = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    ArrayList divisionCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdivisionFlag")), "," );               //  사업자 구분
    ArrayList prodMoldClsCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HprodMoldCls")), "," );      //  ECR 구분
    ArrayList devYnCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdevYn")), "," );                      //  단계구분
    ArrayList stateCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HsancStateFlag")), "," );               //  상태

    ArrayList moldChangeCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HmoldChange")), "," );               //  상태
    ArrayList costChangeCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HcostChange")), "," );               //  상태
    ArrayList prodChangeReasonCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HprodChangeReason")), "," );               //  상태

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
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/jsp/ews/EWSCommon.js"></script>

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

<script src="/plm/jsp/ecm/SearchEcr.js?ver=0.1"></script>

<script type="text/javascript">

    // 사용자 검색 팝업
    function addUser() {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
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
        if ( $("#creator").val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#creator").val().split(", "), userId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#creatorName").val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#creator").val(tmpId.join(", "));
        $("#creatorName").val(tmpName.join(", "));
    }

 // ==  [Start] 부서 검색  == //
    function delDepartment(targetId, targetName) {
        $("#" + targetId).val("");
        $("#" + targetName).val("");
    }
 
    function addDepartmentCallBack(rsltArrayObject){
        
        var code = "";
        var name = "";
        for(var i= 0 ; i < rsltArrayObject.length; i++){
            if(i != 0){
                code += ",";
                name += ",";
            }
            code += rsltArrayObject[i].CODE;
            name += rsltArrayObject[i].NAME;
        }
        deptMerge(code,name,'orgOid','orgName');
    }

    function addDepartment() {
    	
    	SearchUtil.addDepartmentAfterFunc(true,'addDepartmentCallBack');
    }

    function acceptDept(req) {
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

    //==  [Start] 부품검색 팝업(PartNo)  == //
    // mode = m / s
    // pType=A(전체)/P(제품)/D(다이)/M(금형)
    function selectPartNo(targetId){

    	_callBackFuncTargetId = targetId;
        showProcessing();
        SearchUtil.selectPart("callBackFuncPartPopup");
    }
    
    var _callBackFuncTargetId;
    function callBackFuncPartPopup(selectedPartAry){
        if ( typeof selectedPartAry != "undefined" && selectedPartAry.length > 0) {
            var isAppend = "Y";
            acceptPartNo(_callBackFuncTargetId, selectedPartAry, isAppend);
        }
    }
    
    function acceptPartNo(targetId, arrObj, isAppend) {
        var partNoArr = new Array();
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - wtpart oid       // [1] - part number    // [2] - part name
            // [3] - part version     // [4] - part type      // [5] - die number
            // [6] - die name         // [7] - die cnt
            var infoArr = arrObj[i];
            partNoArr[i] = infoArr[1];
        }

        var tmpNo = new Array();
        var tmpName = new Array();
        if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
            tmpNo = $.merge($("#"+targetId).val().split(", "), partNoArr);
            tmpNo = $.unique(tmpNo.sort());
        }
        else {
            tmpNo = partNoArr.sort();
        }

        $("#"+targetId).val(tmpNo.join(", "));
    }
    function clearPartNo(targetId) {
      $("#" + targetId).val("");
    }
    //==  [End] 부품검색 팝업(PartNo)  == //

    //초기화
    function doCancel(){
        document.forms[0].ecrName.value="";
        document.forms[0].partNo.value="";
        document.forms[0].partName.value="";
        document.forms[0].projectNo.value="";
        document.forms[0].projectName.value="";
        document.forms[0].projectNo.value="";
        document.forms[0].orgName.value="";
        document.forms[0].orgOid.value="";
        document.forms[0].ecrId.value="";
        /* 
        document.forms[0].createStartDate.value="";
        document.forms[0].createEndDate.value="";
        */
        $("#divisionFlag").multiselect("uncheckAll");
        $("#prodMoldCls").multiselect("uncheckAll");
        $("#devYn").multiselect("uncheckAll");
        $("#sancStateFlag").multiselect("uncheckAll");
        $("#prodChangeReason").multiselect("uncheckAll");
        
        
        $("#creatorName").val("");// creatorOid
        $("#creatorOid").val("");

        if ( "<%=divisionFlag %>" != "" ) {
            $("#divisionCode").val("<%=divisionFlag %>").attr('selected','selected');
        }
        $("#divisionCode").multiselect("refresh");

        // 결과내재검색 체크해제
        /* 
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
        */
    }


    function loadEjsGrid(){
        
        try{
           
           var idx = 0;
           var D = Grids[idx].Data;
           var formName = "frm";
           
           D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
           
           D.Data.Url = "/plm/servlet/e3ps/SearchMoldEcrServlet";
           D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
           D.Data.Param.cmd = "search";
           //D.Data.Params = "command=searchGridData&create_start=" + $("#create_start").val() + "&create_end=" + $("#create_end").val() + "&perPage="+ $("input[name='perPage']").val();
           
           /*
           D.Page.Url = "/plm/servlet/e3ps/SearchMoldEcrServlet";
           D.Page.Params =  D.Data.Params;
           D.Page.Param.cmd = "searchGridPage";
           */
           
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


    // Jquery
    $(document).ready(function(){

    	treeGridResize("#EcrListSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    	
    	CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');

        SuggestUtil.bind('DEPARTMENT', 'orgName', 'orgOid');
        SuggestUtil.bind('PARTNO', 'partNo');
        SuggestUtil.bind('ECRNO', 'ecrId');
        SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
        SuggestUtil.bind('CARTYPE', 'carTypeName', 'carTypeCode');
        
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
        
        
    	
        $("form[name=frm]").keypress(function(e) {
            //Enter key
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        
        <%
        if(CommonUtil.isKETSUser()) {
        %>
            $("#divisionFlag").multiselect({
                minWidth: 180,
                multiple: false,
                header: "",
                noneSelectedText: "",
                selectedList: 1
            });
        <%    
        }
        else {
        %>
	        $("#divisionFlag").multiselect({
	        	minWidth: 180,
	            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	        });
        <%
        }
        %>
        
        
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

        // 금형변경
        $("#moldChange").multiselect({
            minWidth: 180,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        // 원가변동
        $("#costChange").multiselect({
            minWidth: 180,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        // 설계변경사유
        $("#prodChangeReason").multiselect({
            minWidth: 180,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

    });
</script>


<script type="text/javascript">

    <%--등록--%>
    function doCreate(type) {
        
    	var url = "";
    	if(type == 'PROD') {
    	    url = "/plm/jsp/ecm/CreateProdEcr.jsp";
        
    	} else if(type == 'MOLD') {
    		url = "/plm/jsp/ecm/CreateMoldEcrForm.jsp";
            
    	}
    	
        var name = "CreateECR"+ type;
    	var opt = launchCenter(1024, 768);
    	opt += ", resizable=yes,toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        var windowWin = window.open(url,name,opt);
        
        //windowWin.resizeTo(width,height);
        windowWin.focus();
    	
    }

    <%--조회--%>
    function doView(url) {
        
    	var name = "ViewECR";
    	if(url.indexOf('e3ps.ecm.entity.KETProdChangeRequest') > 0) {
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
    			search();
    		}
    		
    		
    	}
    }    
    

    function lfn_toggleConditions() {
        if($("#divToggleConditions").css("display") == "none"){
            $("#divToggleConditions").show();
               
        } else {
            $("#divToggleConditions").hide();
            
        }
    }    
</script>


</head>

<body class="body-space">
<form name="frm" method="post" action="">
<input type="hidden" name="cmd" value="search">

<input type="hidden" name="param" value="parent.">
<input type="hidden" name="autoSearch" value="Y">
<input type="hidden" name="oid" value="">

<input type="hidden" name="HdivisionFlag" value="">
<input type="hidden" name="HprodMoldCls" value="">
<input type="hidden" name="HdevYn" value="">
<input type="hidden" name="HsancStateFlag" value="">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />

<input type="hidden" name="HmoldChange" value="">
<input type="hidden" name="HcostChange" value="">
<input type="hidden" name="HprodChangeReason" value="">

<table style="width: 100%; height: 100%;" border="0">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%; height: 28px;">
                <tr>
                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png" onclick="javascript:lfn_toggleConditions();"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00202") %><%--ECR 검색--%></td>
                    <!-- td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00204") %><%--ECR 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00202") %><%--ECR 검색--%>
                    </td -->
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "ECM") %><%--ECM--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "ECR") %><%--ECR--%>
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
        <!-- [END] title & position -->
        

<div id="divToggleConditions" name="divToggleConditions">        
        <!-- [START] button -->
        <table style="width: 100%;" >
        <tr>
            <td align="right">
                <table>
                <tr>
                    <!-- td>
                        <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                    </td>
                    <td style="width: 15px;">&nbsp;</td -->
                    <%
                    if(KETObjectUtil.isAdmin() || KETObjectUtil.isMember("자동차사업부") || KETObjectUtil.isMember("전자사업부")) {
                    %>
                    <td>
                       <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:doCreate('PROD');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04910") %><%--제품ECR등록--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <%
                    }
                    %>
                    <%
                    if(KETObjectUtil.isAdmin() || KETObjectUtil.isMember("자동차사업부") || KETObjectUtil.isMember("전자사업부")) {
                    %>                    
                    <td>
                       <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:doCreate('MOLD');" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "04920") %><%--금형ECR등록--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>                    
                    <td width="5">&nbsp;</td>
                    <%
                    }
                    %>
                    <td>
                       <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:doCancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
        <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00210") %><%--ECR구분--%></td>
            <td class="tdwhiteL">
                <select name="prodMoldCls" id="prodMoldCls" class="fm_jmp" multiple="multiple">
                <option value="1"  <%=prodMoldClsCondList.contains("1") ? " selected" : "" %> ><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></option>
                <option value="2"  <%=prodMoldClsCondList.contains("2") ? " selected" : "" %> ><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></option>
                </select>
            </td>
            <td class="tdblueL" style="width:110px;">ECR No</td>
            <td class="tdwhiteL">
                <input type="text" name="ecrId" class="txt_field" style="width:98%" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecrId") )%>" >
            </td> 
            <td class="tdblueL" style="width:110px;"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
            <td class="tdwhiteL0">
                <input type="text" name="ecrName" class="txt_field" style="width:98%" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecrName") )%>"  id="textfield4">
            </td>
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
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00395") %><%--Project Name--%></td>
            <td class="tdwhiteL0">
                <input type="text" name="projectName" class="txt_field"  value="<%=StringUtil.checkNull( (String)searchCondition.get("projectName") )%>"  style="width:98%" >
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
            <td class="tdwhiteL">
			  <input type="text" id="orgName" name="orgName" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("orgName") )%>" style="width:75%" >
			  <input type="hidden" id="orgOid" name="orgOid" value="<%=StringUtil.checkNull( (String)searchCondition.get("orgOid") )%>">
			  <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png"></a>
			  <a href="javascript:delDepartment('orgOid','orgName');"><img src="/plm/portal/images/icon_delete.gif"></a>
			</td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
			  <input type="text" name="creatorName" id="creatorName" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorName") )%>" style="width:75%">
			  <input type="hidden" id="creatorOid" name="creatorOid" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorOid") )%>">
			  <input type="hidden" id="creator" name="creator">
			  <a href="javascript:SearchUtil.selectOneUser('creatorOid','creatorName');"> <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
              <a href="javascript:CommonUtil.deleteValue('creatorOid','creatorName');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
			</td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL0">
                <select name="sancStateFlag" id="sancStateFlag" class="fm_jmp" multiple="multiple">
                   <option value="INWORK"  <%=stateCondList.contains("INWORK") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02441") %><%--작업중--%></option>
                   <option value="APPROVEDFILING"  <%=stateCondList.contains("APPROVEDFILING") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04830") %><%--제기승인--%></option>
                   <option value="CONSIDER"  <%=stateCondList.contains("CONSIDER") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04840") %><%--검토--%></option>
                   <option value="UNDERREVIEW"  <%=stateCondList.contains("UNDERREVIEW") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "00732") %><%--검토중--%></option>
                   <option value="APPROVED"  <%=stateCondList.contains("APPROVED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04980") %><%--검토완료--%></option>
                   <option value="REJECTED"  <%=stateCondList.contains("REJECTED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "01468") %><%--반려됨--%></option>
                   <!-- option value="REWORK"  <%=stateCondList.contains("REWORK") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02451") %><%--재작업--%></option -->
                </select>
            </td>
        </tr>     
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
            <td colspan="3" class="tdwhiteL">
               <input type="text" name="createStartDate" id="createStartDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createStartDate") )%>">
               <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createStartDate');" style="cursor: hand;">
                   &nbsp;~&nbsp;
               <input type="text" name="createEndDate" id="createEndDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createEndDate") )%>">
               <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createEndDate');" style="cursor: hand;">
            </td>
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
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품 번호--%></td>
            <td class="tdwhiteL">
				<input type="text" name="partNo" id="partNo" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("partNo") )%>" style="width:75%">
				<input type="hidden" name="partOid" id="partOid">
				<a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
				<a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(1);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
			</td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
            <td class="tdwhiteL">
                <input type="text" name="partName" class="txt_field"   value="<%=StringUtil.checkNull( (String)searchCondition.get("partName") )%>"  style="width:98%" >
            </td>
           <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04122") %><%--차종--%></td>
           <td class="tdwhiteL0">
               <input type="text" name="carTypeName" class="txt_field" style="width: 70%" value="<%=StringUtil.checkNull( (String)searchCondition.get("carTypeName") )%>"> 
               <input type="hidden" name="carTypeCode">
               <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
               <img src="/plm/portal/images/icon_5.png" border="0"></a> 
               <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
               <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
           </td>
        </tr>
        <tr>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04124") %><%--금형변경--%></td>
          <td class="tdwhiteL">
            <select name="moldChange" id="moldChange" class="fm_jmp" multiple="multiple">
            <%
            for ( int i=0; i < moldChangeList.size(); i++ ) {
            %>
            <option value="<%=moldChangeList.get(i).get("code") %>" <%=moldChangeCondList.contains( moldChangeList.get(i).get("code") ) ? " selected" : "" %>><%=moldChangeList.get(i).get("value")%></option>
            <%
            }
            %>
            </select>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04125") %><%--원가변동--%></td>
          <td class="tdwhiteL">
            <select name="costChange" id="costChange" class="fm_jmp" multiple="multiple">
            <%
            for ( int i=0; i < costChangeList.size(); i++ ) {
            %>
            <option value="<%=costChangeList.get(i).get("code") %>" <%=costChangeCondList.contains( costChangeList.get(i).get("code") ) ? " selected" : "" %>><%=costChangeList.get(i).get("value")%></option>
            <%
            }
            %>
            </select>
          </td>
          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01859") %><%--설계변경요청사유--%></td>
          <td class="tdwhiteL0">
            <select name="prodChangeReason" id="prodChangeReason" class="fm_jmp" multiple="multiple">
            <%
            for ( int i=0; i < prodChangeReasonList.size(); i++ ) {
            %>
            <option value="<%=prodChangeReasonList.get(i).get("code") %>" <%=prodChangeReasonCondList.contains( prodChangeReasonList.get(i).get("code") ) ? " selected" : "" %>><%=prodChangeReasonList.get(i).get("value")%></option>
            <%
            }
            %>
            </select>
          </td>                       
        </tr>   
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
            <td class="tdwhiteL">
                <select name="divisionFlag" id="divisionFlag" class="fm_jmp" multiple="multiple">
                    <%
                    for ( int i=0; i<divisionCode.size(); i++ ) {
                        if(CommonUtil.isKETSUser() && !divisionCode.get(i).get("code").equals("K")) {
                            // Do nothing..!!
                        } else {                	
                    %>
                    <option value="<%=divisionCode.get(i).get("code") %>" <%=divisionCondList.contains( divisionCode.get(i).get("code") ) ? " selected" : "" %>><%=divisionCode.get(i).get("value")%></option>
                    <%
                        }
                    }
                    %>
                </select>
            </td>
            <td colspan="5" class="tdwhiteL0">&nbsp;</td>                       
        </tr>
              
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] search condition -->

</div>

        
        <!-- EJS TreeGrid Start -->
        <!-- div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid" -->
                
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/ecm/SearchEcrListGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Ecr_List"
                        ></bdo>
                    </div>
                    
                <!-- /div>
            </div>
        </div -->
        <!-- EJS TreeGrid End -->
    </td>
</tr>
<!-- tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 100%; height:24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
</tr -->
</table>
</form>
</body>
</html>
