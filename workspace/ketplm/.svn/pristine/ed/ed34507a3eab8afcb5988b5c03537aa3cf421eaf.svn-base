<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList
                ,java.util.List
                ,java.util.Map
                ,java.util.HashMap
                ,e3ps.common.util.StringUtil
                ,e3ps.common.util.KETStringUtil
                ,e3ps.common.util.PropertiesUtil
                ,e3ps.common.code.NumberCodeHelper" %>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

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

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 양산금형 구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MOLDPLANTYPE");
    List<Map<String, Object>> moldPlanTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 생산처 구분-사내/외주
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "VENDORFLAG");
    List<Map<String, Object>> vendorFlagNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 양산금형 측정구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MOLDPLANCHECKTYPE");
    List<Map<String, Object>> moldPlanCheckTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 양산금형 단계
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MOLDPLANSTAGE");
    List<Map<String, Object>> moldPlanStageNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 양산금형 상태
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MOLDPLANSTATUS");
    List<Map<String, Object>> moldPlanStatusNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);


    ArrayList planTypeCondList       = KETStringUtil.getToken( KETStringUtil.nullFilter(searchCondition.getString("planType")), ", " );
    ArrayList measureTypeCondList    = KETStringUtil.getToken( KETStringUtil.nullFilter(searchCondition.getString("measureType")), ", " );
    ArrayList currentProcessCondList = KETStringUtil.getToken( KETStringUtil.nullFilter(searchCondition.getString("currentProcess")), ", " );
    ArrayList statusCondList         = KETStringUtil.getToken( KETStringUtil.nullFilter(searchCondition.getString("status")), ", " );
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
<script src="/plm/portal/js/ajax.js"></script>

<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>

<style type="text/css">
/* body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 5px;
} */
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>



<script type="text/javascript">
<!--
    function hideProcessing() {}

    //필드 값 초기화
    function deleteValueAll(){
        $("#planType").multiselect("uncheckAll");
        $("#vendorFlag").multiselect("uncheckAll");
        $("#vendorFlag").multiselect("refresh");
        $("#measureType").multiselect("uncheckAll");
        $("#currentProcess").multiselect("uncheckAll");
        $("#status").multiselect("uncheckAll");
        $("#dieNo").val("");
        $("#partNo").val("");
        $("#partName").val("");
        $("#vendorCode").val("");
        $("#vendorName").val("");
        $("#prodOwnerName").val("");
        $("#prodOwnerOid").val("");
        $("#moldOwnerName").val("");
        $("#moldOwnerOid").val("");
        $("#prodDeptName").val("");
        $("#OwnerName").val("");
        $("#OwnerOid").val("");
        $("#fromPlanDate").val("");
        $("#toPlanDate").val("");
        $("#fromActualDate").val("");
        $("#toActualDate").val("");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    function search() {
        var form = document.frm;
        form.cmd.value = "Search";
        form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
        form.submit();
    }

    function excelDown() {
        var form = document.frm;
        form.cmd.value = "excelDown";
        form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
        form.submit();
    }

    function goView( oid ) {
//         var form = document.frm;
//         form.cmd.value = "View";
//         form.oid.value = oid;
//         form.action =  '/plm/servlet/e3ps/MoldPlanServlet';
//         form.submit();
        var url='/plm/servlet/e3ps/MoldPlanServlet?cmd=View&oid='+oid;
        //openWindow(url,"","860","800","status=1,scrollbars=yes,resizable=yes");
        openOtherName(url,"","890","800","status=no,scrollbars=yes,resizable=yes");
    }

    // ==  [Start] 생산처 검색  == //
    //생산처 초기화
    function clearVendor(){
        $("#vendorCode").val("");
        $("#vendorName").val("");
    }

    function popupVendor(){
        var form = document.forms[0];
        if(form.vendorFlag.value == "i") {//사내
            selectInnerDepartment();
        } else {//외주
            selectPartner();
        }
    }

    //협력사검색 팝업 Open
    function selectPartner() {
        var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=multi&method=linkPartner";
        openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
    }

    //협력사 검색결과 셋팅
    function linkPartner(arrObj){
        if ( arrObj.length == 0 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %><%--협력사를 선택하시기 바랍니다--%>');
            return;
        }

        var vendorCode = new Array();
        var vendorName = new Array();
        for ( var i=0; i < arrObj.length; i++ ) {
            var infoArr = arrObj[i];
            vendorCode[i] = infoArr[0];
            vendorName[i] = infoArr[1];
        }

        var tmpCode = new Array();
        var tmpName = new Array();
        if ( $("#vendorCode").val() != "" ) {
            tmpCode = $.merge($("#vendorCode").val().split(", "), vendorCode);
            tmpCode = $.unique(tmpCodes.sort());

            tmpName = $.merge($("#vendorName").val().split(", "), vendorName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpCode = vendorCode.sort();
            tmpName = vendorName.sort();
        }

        $("#vendorCode").val(tmpCode.join(", "));
        $("#vendorName").val(tmpName.join(", "));
    }

    //사내생산처 검색 팝업  Open
    function selectInnerDepartment() {
        var mode = "multi";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&codetype=PRODUCTIONDEPT&command=select&mode="+mode;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:480px; center:yes");
        if(typeof returnValue == "undefined" || returnValue == null) {
            return;
        }
        linkDept(returnValue);
    }

    //사내생산처 검색결과 셋팅
    function linkDept(arrObj){
        if ( arrObj.length == 0 ) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01553") %><%--부서를 선택하시기 바랍니다--%>');
            return;
        }

        var vendorCode = new Array();
        var vendorName = new Array();
        for ( var i=0; i < arrObj.length; i++ ) {
            var infoArr = arrObj[i];
            vendorCode[i] = infoArr[1];
            vendorName[i] = infoArr[2];
        }

        var tmpCode = new Array();
        var tmpName = new Array();
        if ( $("#vendorCode").val() != "" ) {
            tmpCode = $.merge($("#vendorCode").val().split(", "), vendorCode);
            tmpCode = $.unique(tmpCodes.sort());

            tmpName = $.merge($("#vendorName").val().split(", "), vendorName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpCode = vendorCode.sort();
            tmpName = vendorName.sort();
        }

        $("#vendorCode").val(tmpCode.join(", "));
        $("#vendorName").val(tmpName.join(", "));
    }
    // ==  [End] 생산처 검색  == //

    // ==  [Start] 사람 검색  == //
    function delUser(v) {
        if ( v == "W" ) {
            $("#OwnerOid").val("");
            $("#OwnerName").val("");
        }
        else if ( v == "P" ) {
            $("#prodOwnerOid").val("");
            $("#prodOwnerName").val("");
        }
        else if ( v== "M" ) {
            $("#moldOwnerOid").val("");
            $("#moldOwnerName").val("");
        }
    }

    function addUser(v) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
            return;
        }
        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "Y";
        acceptUser(v, acceptUsers, isAppend);
    }

    function acceptUser(v, arrObj, isAppend) {
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

        var targetId = "";
        var targetName = "";

        if ( v == "W" ) {
            targetId = "OwnerOid";
            targetName = "OwnerName";
        }
        else if ( v == "P" ) {
            targetId = "prodOwnerOid";
            targetName = "prodOwnerName";
        }
        else if ( v== "M" ) {
            targetId = "moldOwnerOid";
            targetName = "moldOwnerName";
        }

        var tmpId = new Array();
        var tmpName = new Array();
        if ( $("#" + targetId).val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#" + targetId).val().split(", "), userId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#" + targetName).val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#" + targetId).val(tmpId.join(", "));
        $("#" + targetName).val(tmpName.join(", "));
    }
    // ==  [End] 사람 검색  == //

    // ==  [Start] 부서 검색  == //
    function delDepartment(targetId) {
        $("#" + targetId).val("");
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

                callServer(url, acceptDept);
            }
        }
        else {
            var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;

            callServer(url, acceptDept);
        }
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

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "prodDeptName");
    }

    function deptMerge(deptOid, deptName, targetName) {
        if ( $("#"+targetName).val() == "" ) {
            $("#"+targetName).val( deptName );
        }
        else {
            var deptNameArr = $("#"+targetName).val().split(", ");
            // 선택된 부서 push
            deptNameArr.push(deptName);
            // 중복 부서 정리
            deptNameArr = $.unique(deptNameArr.sort());

            $("#"+targetName).val( deptNameArr.join(", ") );
        }
    }
    // ==  [End] 부서 검색  == //

    // ==  [Start] 부품검색 팝업(PartNo)  == //
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
        var partNameArr = new Array();
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - wtpart oid       // [1] - part number    // [2] - part name
            // [3] - part version     // [4] - part type      // [5] - die number
            // [6] - die name         // [7] - die cnt
            var infoArr = arrObj[i];
            partNoArr[i] = infoArr[1];
            partNameArr[i] = infoArr[1];
        }

        var tmpNo = new Array();
        var tmpName = new Array();
        if ( $("#"+targetId).val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpNo = $.merge($("#"+targetId).val().split(", "), partNoArr);
            tmpNo = $.unique(tmpNo.sort());

            tmpName = $.merge($("#"+targetId).val().split(", "), partNameArr);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpNo = partNoArr.sort();
            tmpName = partNameArr.sort();
        }

        $("#"+targetId).val(tmpNo.join(", "));
        $("#temp"+targetId).val(partNameArr.join(", "));
    }
    function clearPartNo(targetId) {
      $("#" + targetId).val("");
      $("#temp" + targetId).val("");
    }
    //==  [End] 부품검색 팝업(PartNo)  == //
    
    function createMoldPlan(){
    	var url = '/plm/jsp/common/loading.jsp?url=/plm/jsp/ecm/CreateMoldPlan.jsp';
    	getOpenWindow2(url,'CreateMoldPlan',950,600);
    }

    //Jquery
    $(document).ready(function(){
    	
    	// Die No
    	SuggestUtil.bind('DIENO', 'dieNo');
    	// Part No
    	SuggestUtil.bind('PARTNO', 'partNo');
    	// 개발담당부서
    	SuggestUtil.bind('DEPARTMENT', 'prodDeptName');
    	// 작성자
    	SuggestUtil.bind('USER', 'OwnerName', 'OwnerOid');
    	// 제품ECO담당
    	SuggestUtil.bind('USER', 'prodOwnerName', 'prodOwnerOid');
    	// 금형ECO 담당자
    	SuggestUtil.bind('USER', 'moldOwnerName', 'moldOwnerOid');
    	
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#planType").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#vendorFlag").multiselect({
            minWidth: 160,
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });

        $("#measureType").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#currentProcess").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#status").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        CalendarUtil.dateInputFormat('fromPlanDate');
        CalendarUtil.dateInputFormat('toPlanDate');
        CalendarUtil.dateInputFormat('fromActualDate');
        CalendarUtil.dateInputFormat('toActualDate');
        
        treeGridResize("#SearchMoldPlanGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        
    });
-->
</script>
</head>
<body class="body-space">

<form name="frm" method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" value="Search">
<input type="hidden" name="oid">
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02089") %><%--양산금형 일정 관리--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02089") %><%--양산금형 일정관리--%>
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
                        <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                    </td>
                    <td style="width: 15px;">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:createMoldPlan();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02090") %><%--양산금형 일정 등록--%></a>
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
                                <a href="javascript:deleteValueAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
            <td class="tdblueL"  style="width: 100px;">Die No</td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="dieNo" name="dieNo" class="txt_field" style="width:70%;" value="<%=searchCondition.getString("dieNo") %>">
                <a href="javascript:selectPartNo('dieNo');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('dieNo');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"  style="width: 90px;"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="planType" name="planType" class="fm_jmp" style="width:160px;" multiple="multiple">
                <%
                for ( int i=0; i<moldPlanTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=moldPlanTypeNumCode.get(i).get("code") %>" <%=planTypeCondList.contains( moldPlanTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=moldPlanTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input type="text" id="OwnerName" name="OwnerName" class="txt_field" style="width:120px;"  value="<%=searchCondition.getString("OwnerName") %>">
                <input type="hidden" id="OwnerOid" name="OwnerOid"  value="<%=searchCondition.getString("OwnerOid") %>">
                <a href="javascript:addUser('W');" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('W');"  onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
            <td class="tdwhiteL">
                <input type="text" id="partNo" name="partNo" class="txt_field" style="width:70%;" value="<%=searchCondition.getString("partNo") %>">
                <a href="javascript:selectPartNo('partNo');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('partNo');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
            <td class="tdwhiteL">
                <input type="text" id="prodDeptName" name="prodDeptName" class="txt_field" style="width:70%;" value="<%=searchCondition.getString("prodDeptName") %>">
                <a href="javascript:addDepartment()"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment('prodDeptName');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02562") %><%--제품ECO담당--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="prodOwnerName" name="prodOwnerName" class="txt_field" style="width:120px;"  value="<%=searchCondition.getString("prodOwnerName") %>">
                <input type="hidden" id="prodOwnerOid" name="prodOwnerOid"  value="<%=searchCondition.getString("prodOwnerOid") %>">
                <a href="javascript:addUser('P');" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('P');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
            <td class="tdwhiteL" colspan="3">
                <input type="text" name="partName" class="txt_field" style="width:98%;" ></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01030") %><%--금형ECO 담당자--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="moldOwnerName" name="moldOwnerName" class="txt_field" style="width:120px;"  value="<%=searchCondition.getString("moldOwnerName") %>">
                <input type="hidden" id="moldOwnerOid" name="moldOwnerOid"  value="<%=searchCondition.getString("moldOwnerOid") %>">
                <a href="javascript:addUser('M');" onfocus="this.blur();"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('M');" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
            <td class="tdwhiteL" colspan="3">
                <select id="vendorFlag" name="vendorFlag" class="fm_jmp" style="width:100px;" multiple="multiple">
                <%
                for ( int i=0; i<vendorFlagNumCode.size(); i++ ) {
                %>
                <option value="<%=vendorFlagNumCode.get(i).get("code") %>" <%=searchCondition.getString("vendorFlag").contains( vendorFlagNumCode.get(i).get("code").toString() ) ? " selected" : "" %>><%=vendorFlagNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>&nbsp;
                <input type="text" id="vendorName" name="vendorName" class="txt_fieldRO" style="width:210px;" readonly value="<%=searchCondition.getString("vendorName") %>">
                <input type="hidden" id="vendorCode" name="vendorCode"  value="<%=searchCondition.getString("vendorCode") %>">
                <a href="javascript:popupVendor();"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearVendor();"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02899") %><%--측정구분--%></td>
            <td class="tdwhiteL0">
                <select id="measureType" name="measureType" class="fm_jmp" style="width:160px;" multiple="multiple">
                <%
                for ( int i=0; i<moldPlanCheckTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=moldPlanCheckTypeNumCode.get(i).get("code") %>" <%=measureTypeCondList.contains( moldPlanCheckTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=moldPlanCheckTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00824") %><%--계획일자--%></td>
            <td class="tdwhiteL" colspan="3">
                <input type="text" id="fromPlanDate" name="fromPlanDate" class="txt_field"  style="width:97px" value="<%=searchCondition.getString("fromPlanDate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('fromPlanDate');" style="cursor: hand;">&nbsp;~&nbsp;
                <input type="text" id="toPlanDate" name="toPlanDate" class="txt_field"  style="width:97px" value="<%=searchCondition.getString("toPlanDate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('toPlanDate');" style="cursor: hand;">
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01190") %><%--단계--%></td>
            <td class="tdwhiteL0">
                <select id="currentProcess" name="currentProcess" class="fm_jmp" style="width:160px;" multiple="multiple">
                <%
                for ( int i=0; i<moldPlanStageNumCode.size(); i++ ) {
                %>
                <option value="<%=moldPlanStageNumCode.get(i).get("code") %>" <%=currentProcessCondList.contains( moldPlanStageNumCode.get(i).get("code") ) ? " selected" : "" %>><%=moldPlanStageNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02046") %><%--실적일자--%></td>
            <td class="tdwhiteL" colspan="3">
                <input type="text" id="fromActualDate" name="fromActualDate" class="txt_field"  style="width:97px" value="<%=searchCondition.getString("fromActualDate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('fromActualDate');" style="cursor: hand;">&nbsp;~&nbsp;
                <input type="text" id="toActualDate" name="toActualDate" class="txt_field"  style="width:97px" value="<%=searchCondition.getString("toActualDate") %>">
                <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('toActualDate');" style="cursor: hand;">
            </td>
            <td width="100" class="tdblueL"  ><%=messageService.getString("e3ps.message.ket_message", "02731") %><%--진행상태--%></td>
            <td class="tdwhiteL0">
                <select id="status" name="status" class="fm_jmp" style="width:160px;" multiple="multiple">
                <%
                for ( int i=0; i<moldPlanStatusNumCode.size(); i++ ) {
                %>
                <option value="<%=moldPlanStatusNumCode.get(i).get("code") %>" <%=statusCondList.contains( moldPlanStatusNumCode.get(i).get("code") ) ? " selected" : "" %>><%=moldPlanStatusNumCode.get(i).get("value")%></option>
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
        <!-- [END] search condition -->

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/ecm/SearchMoldPlanGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_StandardDoc_List"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid Start -->
    </td>
</tr>

<!-- [START] copyright -->
<tr>
    <td style="height: 30px" valign="bottom">
        <table style="width: 100%;">
        <tr>
            <td style="width: 10px">&nbsp;</td>
            <td style="height: 30px">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 100%; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
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
