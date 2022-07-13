<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.List,
                java.util.HashMap,
                java.util.Map"%>

<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.KETParamMapUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

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
    // EJS TreeGrid End

    ArrayList prodMoldClsCondList      = KETStringUtil.getToken( searchCondition.getString("prodMoldCls"), ", " );
    ArrayList stateCondList            = KETStringUtil.getToken( searchCondition.getString("sancStateFlag"), ", " );
    ArrayList devYnCondList            = KETStringUtil.getToken( searchCondition.getString("devYn"), ", " );
    ArrayList divisionCondList         = KETStringUtil.getToken( searchCondition.getString("divisionFlag"), ", " );
    ArrayList dieTypeCondList          = KETStringUtil.getToken( searchCondition.getString("dieType"), ", " );
    ArrayList ecoReasonTypeClsCondList = KETStringUtil.getToken( searchCondition.getString("ecoReasonTypeCls"), ", " );
    ArrayList domestic_ynCondList      = KETStringUtil.getToken( searchCondition.getString("domestic_yn"), ", " );
    ArrayList carMakerCondList         = KETStringUtil.getToken( searchCondition.getString("car_maker"), ", " );
    ArrayList carCategoryCondList      = KETStringUtil.getToken( searchCondition.getString("car_category"), ", " );
    ArrayList increaseProdTypeCondList = KETStringUtil.getToken( searchCondition.getString("increaseProdType"), ", " );
    ArrayList changeReasonCondList     = KETStringUtil.getToken( searchCondition.getString("changeReason"), ", " );

    if ( prodMoldClsCondList.size() == 0 ) {
        prodMoldClsCondList.add(0, "PT001");
    }

    if ( ecoReasonTypeClsCondList.size() == 0 ) {
        ecoReasonTypeClsCondList.add(0, "C");
    }

    String divisionCode = "";
    if ( CommonUtil.isMember("전자사업부") ) {
        divisionCode = "E";
    }
    else if ( CommonUtil.isMember("자동차사업부") ) {
        divisionCode = "C";
    }
    else if ( CommonUtil.isKETSUser() ) {
        divisionCode = "K";
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

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 사업부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONFLAG");
    List<Map<String, Object>> divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 개발구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVYN");
    parameter.put("exclude",  "N");
    List<Map<String, Object>> devYnNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // ECO 구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "NEWPARTTYPE");
    List<Map<String, Object>> newPartTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 설계변경 사유
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    if ( prodMoldClsCondList.contains( "PT001" ) ) {
        parameter.put("codeType", "PRODECOREASON");
    }
    else {
        parameter.put("codeType", "CHANGEREASON");
    }
    List<Map<String, Object>> prodecoReasonNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);


    List<Map<String, Object>> increaseProdTypeNumCode   = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> dieTypeNumCode = new ArrayList<Map<String, Object>>();
    if ( prodMoldClsCondList.contains( "PT002" ) ) {
        // 생산성향상유형
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "INCREASEPRODTYPE");
        increaseProdTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
        // 금형 Type
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "DIETYPE");
        dieTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    }
    // 상태
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ECOREPORTSTATE");
    List<Map<String, Object>> ecoReportStateNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 조회결과보기
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ECOREASONTYPECLS");
    if ( prodMoldClsCondList.contains( "PT001" ) ) {
        parameter.put("exclude", "I");
    }
    List<Map<String, Object>> ecoReasonTypeClsNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 고객사
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "CUSTOMEREVENT");
    List<Map<String, Object>> customerViewTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    List<Map<String, Object>> catMakerNumCode = new ArrayList<Map<String, Object>>();
    if ( domestic_ynCondList.size() > 0 ) {
        parameter.clear();
        parameter.put("locale",     messageService.getLocale());
        parameter.put("codeType",   "CUSTOMEREVENT");
        parameter.put("code",       searchCondition.getString("domestic_yn"));
        parameter.put("venderCode", "자동차");
        catMakerNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    }
    // 차종
    List<Map<String, Object>> carCategoryNumCode = new ArrayList<Map<String, Object>>();
    if ( carMakerCondList.size() > 0 ) {
        parameter.clear();
        parameter.put("locale",      messageService.getLocale());
        parameter.put("carCategory", searchCondition.getString("car_maker"));
        carCategoryNumCode = NumberCodeHelper.manager.getCatCategoryList(parameter);
    }
%>


<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><%=messageService.getString("e3ps.message.ket_message", "01849") %><%--설계변경 유형별 ECO 현황 조회--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/jsp/ecm/EcmUtil.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>

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

<script type="text/javascript">
//<![CDATA[

    //부품 상세조회 팝업
    function viewPart2(v_poid){
        var url = "/plm/jsp/bom/ViewPart_eco.jsp?poid=" + v_poid;
        openWindow(url,"","750","650","scrollbars=no,resizable=no,top=200,left=250");
    }

    // ==  [Start] 부품검색 팝업(PartNo)  == //
    function selectPartNo(targetId){
        
        _callBackFuncTargetId = targetId;
        showProcessing();
        SearchUtil.selectPart("callBackFuncPartPopup", "pType=" + pType);
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

    // Number Code Ajax
    function numCodeAjax(codeType, code, venderCode, carCategory, targetId) {
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codeType, code:code, venderCode:venderCode, carCategory:carCategory},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.numCode, function() {
                    $("#"+targetId).append("<option value='"+this.code+"' >"+ this.value +"</option>");
                });
            }
        });
    }

    function changeEcoType() {
        var selectedAry = $("#prodMoldCls").val();
        $("#changeReason").empty().data('options');
        $("#dieType").empty().data('options');
        $("#increaseProdType").empty().data('options');
        if ( selectedAry == "PT001" ) {
            numCodeAjax("PRODECOREASON", null, null, null, "changeReason");
        }
        else if ( selectedAry == "PT002" ) {
            numCodeAjax("CHANGEREASON", null, null, null, "changeReason");
            numCodeAjax("DIETYPE", null, null, null, "dieType");
            numCodeAjax("INCREASEPRODTYPE", null, null, null, "increaseProdType");
        }
        $("#changeReason").multiselect("refresh");
        $("#dieType").multiselect("refresh");
        $("#increaseProdType").multiselect("refresh");
    }

    function changeCustomerEvent() {
        $("#car_maker").empty().data('options');
        var selectedAry = $("#domestic_yn").val();
        if ( selectedAry != null && selectedAry != undefined && selectedAry.length > 0 ) {
            numCodeAjax("CUSTOMEREVENT", selectedAry.join(","), "자동차", null, "car_maker");
        }
        $("#car_maker").multiselect("refresh");
    }

    function changeCarCategory() {
        $("#car_category").empty().data('options');
        var selectedAry = $("#car_maker").val();
        if ( selectedAry != null && selectedAry != undefined && selectedAry.length > 0 ) {
            numCodeAjax(null, null, null, selectedAry.join(","),"car_category");
        }
        $("#car_category").multiselect("refresh");
    }

    function doCancel(){
        $("#devYn").multiselect("uncheckAll");
        $("#divisionFlag").multiselect("uncheckAll");
        if ( "<%=divisionCode %>" != "" ) {
            $("#divisionFlag").val("<%=divisionCode %>").attr('selected','selected');
        }
        $("#divisionFlag").multiselect("refresh");
        $("#prodMoldCls").val("PT001").attr('selected','selected');
        $("#prodMoldCls").multiselect("refresh");
        $("#partNo").val("");
        $("#temppartNo").val("");
        $("#changeReason").multiselect("uncheckAll");
        $("#increaseProdType").multiselect("uncheckAll");
        $("#createStartDate").val("");
        $("#createEndDate").val("");
        $("#sancStateFlag").multiselect("uncheckAll");
        $("#prodMoldCls").val("C").attr('selected','selected');
        $("#prodMoldCls").multiselect("refresh");
        $("#dieType").multiselect("uncheckAll");
        $("#domestic_yn").multiselect("uncheckAll");
        $("#car_maker").multiselect("uncheckAll");
        $("#car_category").multiselect("uncheckAll");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);

        // ECO구분 변경시 연결 된 것 변경
        changeEcoType();
        changeCustomerEvent();
        changeCarCategory();
    }

    //입력항목 체크
    function checkValidate() {
        var form = document.forms[0];
        if ( form.prodMoldCls.value == "PT001" ) {//제품
            form.ecoReasonTypeCls.value = "C";
        }
        return true;
    }

    function perPage(v) {
        document.theForm.perPage.value = v;

        search();
    }

    function search() {
        if ( checkValidate() ) {
            document.forms[0].cmd.value = "searchTypeEcoReport";
            document.forms[0].target = "_self";
            document.forms[0].action = "/plm/servlet/e3ps/SearchEcoReportServlet";
            disabledAllBtn();
            showProcessing();
            document.forms[0].submit();
        }
    }

    //엑셀저장
    function excelDown(){
        if(checkValidate()){
            document.forms[0].cmd.value = "excelDownTypeEcoReport";
            document.forms[0].action = "/plm/servlet/e3ps/SearchEcoReportServlet";
            document.forms[0].submit();
        }
    }

    //Jquery
    $(document).ready(function(){

        $("form[name=theForm]").keypress(function(e) {
            //Enter key
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#devYn").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#prodMoldCls").multiselect({
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });

        $("#divisionFlag").multiselect({
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });

        $("#changeReason").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#increaseProdType").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#sancStateFlag").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#ecoReasonTypeCls").multiselect({
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });

        $("#dieType").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#domestic_yn").multiselect({
            minWidth: 130,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#car_maker").multiselect({
            minWidth: 130,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#car_category").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    });
//]]>
</script>

</head>

<body>
<form name="theForm" method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<!-- hidden end -->

<table style="width: 780px; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 780px;">
        <tr>
            <td>
                <table style="width: 780px; height: 28px;">
                <tr>
                    <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01850") %><%--설계변경 유형별 현황조회--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01845") %><%--설계변경 관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00195") %><%--ECO 현황--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01850") %><%--설계변경 유형별 현황조회--%>
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
        <table style="width: 780px;">
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
                                <a href="javascript:doCancel();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
        <table style="width: 780px;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] button -->
        <!-- [START] search condition -->
        <table style="width: 780px;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 780px;">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td><!-- 단계구분 -->
            <td class="tdwhiteL" style="width: 270px;">
                <select id="devYn" name="devYn" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                for ( int i=0; i<devYnNumCode.size(); i++ ) {
                %>
                <option value="<%=devYnNumCode.get(i).get("code") %>" <%=devYnCondList.contains( devYnNumCode.get(i).get("code") ) ? " selected" : "" %>><%=devYnNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <select id="divisionFlag" name="divisionFlag" class="fm_jmp" style="width:270px;" multiple="multiple" >
                <%
                for ( int i=0; i<divisionNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionNumCode.get(i).get("code") %>" <%=divisionCondList.contains( divisionNumCode.get(i).get("code") ) ? " selected" : "" %>><%=divisionNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00197") %><%--ECO구분--%></td>
            <td class="tdwhiteL">
                <select id="prodMoldCls" name="prodMoldCls" class="fm_jmp" style="width:270px" multiple="multiple" onchange="javascript:changeEcoType();">
                <%
                for ( int i=0; i<newPartTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=newPartTypeNumCode.get(i).get("code") %>" <%=prodMoldClsCondList.contains( newPartTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=newPartTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="temppartNo" name="temppartNo" class="txt_fieldRO" style="width:210px;" readonly value="<%=searchCondition.getString("temppartNo") %>">
                <input type="hidden" id="partNo" name="partNo" value="<%=searchCondition.getString("partNo") %>">
                <a href="javascript:selectPartNo('partNo');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('partNo');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%></td>
              <td class="tdwhiteL">
                  <select id="changeReason" name="changeReason" class="fm_jmp" style="width:270px" multiple="multiple">
                  <%
                for ( int i=0; i<prodecoReasonNumCode.size(); i++ ) {
                %>
                <option value="<%=prodecoReasonNumCode.get(i).get("code") %>" <%=changeReasonCondList.contains( prodecoReasonNumCode.get(i).get("code") ) ? " selected" : "" %>><%=prodecoReasonNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01786") %><%--생산성향상유형--%></td>
            <td class="tdwhiteL0">
                <select id="increaseProdType" name="increaseProdType" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                for ( int i=0; i<increaseProdTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=increaseProdTypeNumCode.get(i).get("code") %>" <%=increaseProdTypeCondList.contains( increaseProdTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=increaseProdTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02428") %><%--작성일--%></td>
            <td class="tdwhiteL">
                <input type="text" readonly id="createStartDate" name="createStartDate" class="txt_fieldRO" style="width:70px;" value="<%=searchCondition.getString("createStartDate") %>" onChange="javascript:changeDate1()">
                &nbsp;<img src="/plm/portal/images/icon_6.png"border="0" onClick="javascript:showCal(createStartDate)" style="cursor:hand;">
                <a href="javascript:clearDate('createStartDate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                &nbsp;~&nbsp;
                <input type="text" readonly id="createEndDate" name="createEndDate" class="txt_fieldRO"  style="width:70px;" value="<%=searchCondition.getString("createEndDate") %>" onChange="javascript:changeDate2()">
                &nbsp;<img src="/plm/portal/images/icon_6.png"border="0" onClick="javascript:showCal(createEndDate)" style="cursor:hand;">
                <a href="javascript:clearDate('createEndDate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL0">
                <select name="sancStateFlag"  id="sancStateFlag" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                for ( int i=0; i<ecoReportStateNumCode.size(); i++ ) {
                %>
                <option value="<%=ecoReportStateNumCode.get(i).get("code") %>" <%=stateCondList.contains( ecoReportStateNumCode.get(i).get("code") ) ? " selected" : "" %>><%=ecoReportStateNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02653") %><%--조회결과보기--%></td>
            <td class="tdwhiteL">
                <select name="ecoReasonTypeCls"  id="ecoReasonTypeCls" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                for ( int i=0; i<ecoReasonTypeClsNumCode.size(); i++ ) {
                %>
                <option value="<%=ecoReasonTypeClsNumCode.get(i).get("code") %>" <%=ecoReasonTypeClsCondList.contains( ecoReasonTypeClsNumCode.get(i).get("code") ) ? " selected" : "" %>><%=ecoReasonTypeClsNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01018") %><%--금형 Type--%></td>
            <td class="tdwhiteL0">
                <select id="dieType" name="dieType" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                for ( int i=0; i<dieTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=dieTypeNumCode.get(i).get("code") %>" <%=dieTypeCondList.contains( dieTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=dieTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
            <td class="tdwhiteL">
                  <select id="domestic_yn" name="domestic_yn" class="fm_jmp" style="width:130px" onChange="javascript:changeCustomerEvent();" multiple="multiple">
                  <%
                for ( int i=0; i<customerViewTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=customerViewTypeNumCode.get(i).get("code") %>" <%=domestic_ynCondList.contains( customerViewTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=customerViewTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                  </select>
                <select id="car_maker" name="car_maker" class="fm_jmp" style="width:135px" onchange="javascript:changeCarCategory();" multiple="multiple">
                <%
                for ( int i=0; i<catMakerNumCode.size(); i++ ) {
                %>
                <option value="<%=catMakerNumCode.get(i).get("code") %>" <%=carMakerCondList.contains( catMakerNumCode.get(i).get("code") ) ? " selected" : "" %>><%=catMakerNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
            <td class="tdwhiteL0">
                  <select id="car_category" name="car_category" class="fm_jmp" style="width:270px" multiple="multiple">
                  <%
                for ( int i=0; i<carCategoryNumCode.size(); i++ ) {
                %>
                <option value="<%=carCategoryNumCode.get(i).get("code") %>" <%=carCategoryCondList.contains( carCategoryNumCode.get(i).get("code") ) ? " selected" : "" %>><%=carCategoryNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        </table>
        <table style="width: 780px;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] Page Size -->

        <!-- EJS TreeGrid Start -->
        <%
        if ( ecoReasonTypeClsCondList.contains("C") ) {
            if ( prodMoldClsCondList.contains("PT001") ) {
        %>
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/ecm/SearchTypeEcoReportFormCProdGridLayout.jsp"
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
        <%
            }
            else if ( prodMoldClsCondList.contains("PT002") ) {
        %>
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/ecm/SearchTypeEcoReportFormCMoldGridLayout.jsp"
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
        <%
            }
        } else if ( ecoReasonTypeClsCondList.contains("I") ) {
        %>
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/ecm/SearchTypeEcoReportFormIGridLayout.jsp"
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
        <%} %>
        <!-- EJS TreeGrid Start -->
    </td>
</tr>

<!-- [START] copyright -->
<tr>
    <td style="height: 30px" valign="bottom">
        <table style="width: 780px;">
        <tr>
            <td style="width: 10px">&nbsp;</td>
            <td style="height: 30px">
                <iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 780px; height: 24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
            </td>
        </tr>
        </table>
    </td>
</tr>
<!-- [END] copyright -->

</table>

<!-- Upload Form Action Area : Start -->
<iframe name="ifrmDownload" src="" style="display:none" width="0" height="0" frameborder="0" marginheight="0" marginwidth="0"></iframe>
<!-- Upload Form Action Area : End -->
</form>
</body>
</html>
