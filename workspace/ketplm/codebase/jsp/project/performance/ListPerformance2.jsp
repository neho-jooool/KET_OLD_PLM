<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.List,
                java.util.Map,
                java.util.ArrayList,
                java.util.HashMap"%>

<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.KETParamMapUtil,
                e3ps.common.code.NumberCodeHelper"%>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;

    if( searchCondition != null && !searchCondition.isEmpty() ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End

    // Project Type 변경시 결과내재검색 초기화
    String changeRadio = request.getParameter("changeRadio");
    if ( changeRadio != null && changeRadio == "Y" ) {
        HttpSession s = request.getSession();
        s.setAttribute("performanceSearchConditionList", null);
    }

    Map<String, Object> parameter = new HashMap<String, Object>();
    // Project Type
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PERFORMANCEPJTTYPE");
    List<Map<String, Object>> performanceTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 개발구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVELOPENTTYPE");
    List<Map<String, Object>> developTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 금형분류
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType",   "MOLDPRODUCTSTYPE");
    parameter.put("exclude",    "MOL300");
    parameter.put("depthLevel", "child");
    List<Map<String, Object>> muldProductNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 금형 Rank
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "RANK");
    parameter.put("code",     "RAN3000");
    List<Map<String, Object>> rankNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 제작구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MAKINGTYPE");
    List<Map<String, Object>> makingTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    //parameter
    String radioValue = request.getParameter("radioValue");
    if ( radioValue == null ) {
        radioValue = "1";
    }
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<!-- <script src="/plm/portal/js/treegrid/Grid_KET.js"></script> -->
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/ajax.js"></script>

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
    function onPage(page){
        location.href="/plm/jsp/project/performance/ListPerformance"+page+".jsp?radioValue=" + page + "&changeRadio=Y";
    }

    function viewProject(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
    }

    // ==  [Start] 사람 검색  == //
    function delUser(targetId) {
        $("#" + targetId).val("");
        $("#temp" + targetId).val("");
    }

    function addUser(targetId) {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        acceptUsers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if ( typeof acceptUsers == "undefined" || acceptUsers == null ) {
            return;
        }
        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "Y";
        acceptUser(targetId, acceptUsers, isAppend);
    }

    function acceptUser(targetId, arrObj, isAppend) {
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
        if ( $("#" + targetId).val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#" + targetId).val().split(", "), userId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#temp" + targetId).val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#" + targetId).val(tmpId.join(", "));
        $("#temp" + targetId).val(tmpName.join(", "));
    }
    // ==  [End] 사람 검색  == //

    // ==  [Start] 부서 검색  == //
    function delDepartment() {
        $("#devDeptOid").val("");
        $("#devUserDept").val("");
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

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text));
    }

    function deptMerge(deptOid, deptName) {
        if ( document.frm.devDeptOid.value == "" ) {
            document.frm.devDeptOid.value = deptOid;
            document.frm.devUserDept.value = deptName;
        }
        else {
            var deptOidArr = document.frm.devDeptOid.value.split(", ");
            var deptNameArr = document.frm.devUserDept.value.split(", ");
            // 선택된 부서 push
            deptOidArr.push(deptOid);
            deptNameArr.push(deptName);
            // 중복 부서 정리
            deptOidArr = $.unique(deptOidArr.sort());
            deptNameArr = $.unique(deptNameArr.sort());

            document.frm.devDeptOid.value = deptOidArr.join(", ");
            document.frm.devUserDept.value = deptNameArr.join(", ");
        }
    }
    // ==  [End] 부서 검색  == //

    //==  [Start] 부품검색 팝업(PartNo)  == //
    // mode = m / s
    // pType=A(전체)/P(제품)/D(다이)/M(금형)
    function selectPartNo(targetId){
        var pType = "";
        if ( targetId == "searchDieNo" ) {
            pType = "D"
        }
        else {
            pType = "A";
        }
        
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

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    function search() {

//         showProcessing();     // 진행중 Bar 표시
//         disabledAllBtn();     // 버튼 비활성화

//         document.frm.action = "/plm/servlet/e3ps/PerformanceServlet?command=searchMold";
//         document.frm.target = "_self";
//         document.frm.submit();
        
	    loadEjsGrid();
	}
	
	// function 추가
	function loadEjsGrid(){
	    try{
	
	       var idx = 0;
	       var D = Grids[idx].Data;
	       var formName = "frm";    //ProjectSearch 
	
	       D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
	
	       D.Data.Url = "/plm/servlet/e3ps/PerformanceServlet"; 
	       D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
	       D.Data.Param.command = "searchMoldGridData";
	
	       D.Page.Url = "/plm/servlet/e3ps/PerformanceServlet";
	       D.Page.Params =  D.Data.Params;
	       D.Page.Param.command = "searchMoldGridPage";
	
	       Grids[idx].Reload(D);
	
	       var S = document.getElementById("Status"+idx);
	       if(S) S.innerHTML="Loading";
	
	    }catch(e){
	        alert(e.message);
	    }
	}

    function clearAll(){
        $("#searchDieNo").val("");
        $("#searchProductPart").val("");
        $("#searchProductPjtNo").val("");
        $("#searchProductPartName").val("");
        $("#searchDEVELOPENTTYPE").multiselect("uncheckAll");
        $("#moldProductType").multiselect("uncheckAll");
        $("#moldRank").multiselect("uncheckAll");
        $("#making").multiselect("uncheckAll");
        $("#outsourcing").val("");
        $("#searchPm").val("");
        $("#tempsearchPm").val("");
        $("#planStartStartDate").val("");
        $("#planStartEndDate").val("");
        $("#devDeptOid").val("");
        $("#devUserDept").val("");
        $("#planEndStartDate").val("");
        $("#planEndEndDate").val("");
        $("#searchProductPm").val("");
        $("#tempsearchProductPm").val("");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function excelDown(){
        document.frm.action = "/plm/servlet/e3ps/PerformanceServlet?command=excelDown";
        document.frm.submit();
    }

    //Jquery
    $(document).ready(function(){
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#searchDEVELOPENTTYPE").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#moldProductType").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#moldRank").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#making").multiselect({
            minWidth: 160,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    });
//]]>
</script>
</head>


<body >
<form name="frm" method="POST" >

<!-- hidden begin -->
<input type="hidden" name="radioValue" value="<%=radioValue%>" />
<input type="hidden" name="perPage" value="<%=pagingSize %>">
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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01898") %><%--성과관리 검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00411") %><%--Project관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01898") %><%--성과관리 검색--%>
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
            <td align="left">
                <%
                if ( CommonUtil.isAdmin() ) {
                    for ( int i=0; i<performanceTypeNumCode.size(); i++ ) {
                    %>
                        <input name="radio" type="radio" class="Checkbox" value="<%=performanceTypeNumCode.get(i).get("code") %>" <%if ( radioValue.equals( performanceTypeNumCode.get(i).get("code") ) ){%>checked<%}%> onclick="javascript:onPage('<%=performanceTypeNumCode.get(i).get("code")%>')"><%=performanceTypeNumCode.get(i).get("value")%>&nbsp;
                    <%
                    }
                }
                else {
                    for ( int i=0; i<performanceTypeNumCode.size(); i++ ) {
                        if ( !performanceTypeNumCode.get(i).get("code").equals("3") ) {
                    %>
                        <input name="radio" type="radio" class="Checkbox" value="<%=performanceTypeNumCode.get(i).get("code") %>" <%if ( radioValue.equals( performanceTypeNumCode.get(i).get("code") ) ){%>checked<%}%> onclick="javascript:onPage('<%=performanceTypeNumCode.get(i).get("code")%>')"><%=performanceTypeNumCode.get(i).get("value")%>&nbsp;
                    <%
                        }
                    }
                }
                %>
            </td>
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
                                <a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
            <td class="tdblueL"  style="width: 100px;">Die No</td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="searchDieNo" name="searchDieNo" value="<%=searchCondition.getString("searchDieNo") %>" class="txt_fieldRO" readOnly style="width: 110px;">
                <a href="javascript:selectPartNo('searchDieNo');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('searchDieNo');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"  style="width: 100px;">Part No</td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="searchProductPart" name="searchProductPart" value="<%=searchCondition.getString("searchProductPart") %>" class="txt_fieldRO" readOnly style="width: 110px;">
                <a href="javascript:selectPartNo('searchProductPart');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('searchProductPart');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"   style="width: 100px;">Project No</td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input type="text" id="searchProductPjtNo" name="searchProductPjtNo" value="<%=searchCondition.getString("searchProductPjtNo") %>" class="txt_field" style="width: 160px;">
            </td>
        </tr>
        <tr>
            <td class="tdblueL">Part Name</td>
            <td class="tdwhiteL0" colspan="5">
                <input type="text" id="searchProductPartName" name="searchProductPartName" value="<%=searchCondition.getString("searchProductPartName") %>" class="txt_field" style="width:100%">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
            <td class="tdwhiteL">
                <select id="searchDEVELOPENTTYPE" name="searchDEVELOPENTTYPE" class="fm_jmp" style="width: 160px;" multiple="multiple">
                    <%
                    String[] developType = searchCondition.getStringArray("searchDEVELOPENTTYPE");
                    for ( int i=0; i<developTypeNumCode.size(); i++ ) {
                    %>
                    <option value="<%=developTypeNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(developType, developTypeNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=developTypeNumCode.get(i).get("value")%></option>
                    <%
                    }
                    %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01078") %><%--금형분류--%></td>
            <td class="tdwhiteL">
                <select id="moldProductType" name="moldProductType" class="fm_jmp" style="width: 160px;" multiple="multiple">
                    <%
                    String[] moldProductType = searchCondition.getStringArray("moldProductType");
                    for ( int i=0; i<muldProductNumCode.size(); i++ ) {
                    %>
                    <option value="<%=muldProductNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(moldProductType, muldProductNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=muldProductNumCode.get(i).get("value")%></option>
                    <%
                    }
                    %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01010") %><%--금형 Rank--%></td>
            <td class="tdwhiteL0">
                <select id="moldRank" name="moldRank" class="fm_jmp" style="width: 160px;" multiple="multiple">
                    <%
                    String[] moldRank = searchCondition.getStringArray("moldRank");
                    for ( int i=0; i<rankNumCode.size(); i++ ) {
                    %>
                    <option value="<%=rankNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(moldRank, rankNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=rankNumCode.get(i).get("value")%></option>
                    <%
                    }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%></td>
            <td class="tdwhiteL">
                <select id="making" name="making" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                String[] making = searchCondition.getStringArray("making");

                for ( int i=0; i<makingTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=makingTypeNumCode.get(i).get("code") %>" <%=KETParamMapUtil.contains(making, makingTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=makingTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
            <td class="tdwhiteL">
                <input type="text" id="outsourcing" name="outsourcing" value="<%=searchCondition.getString("outsourcing") %>" class="txt_field" style="width: 160px;">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01058") %><%--금형담당자--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="tempsearchPm" name="tempsearchPm" value="<%=searchCondition.getString("tempsearchPm") %>" class="txt_fieldRO" readonly style="width: 110px;">
                <input type="hidden" id="searchPm" name='searchPm' value='<%=searchCondition.getString("searchPm") %>'>
                <a href="javascript:addUser('searchPm');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('searchPm');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
            <td class="tdwhiteL" colspan="3">
                <input id="planStartStartDate" name="planStartStartDate" value="<%=searchCondition.getString("planStartStartDate") %>" class="txt_field" size="12" maxlength=15 /> <!-- readonly onclick="javascript:showCal(planStartStartDate);" -->
                <a href="javascript:;" onclick="javascript:showCal('planStartStartDate');"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planStartStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;~&nbsp;
                <input id="planStartEndDate" name="planStartEndDate" value="<%=searchCondition.getString("planStartStartDate") %>" class="txt_field" size="12" maxlength=15/> <!--  readonly onclick="javascript:showCal(planStartStartDate);" -->
                <a href="javascript:;" onclick="javascript:showCal('planStartEndDate');"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planStartEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;&nbsp;(yyyy-mm-dd)
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="devUserDept" name="devUserDept" value="<%=searchCondition.getString("devUserDept") %>" class="txt_fieldRO" readonly style="width:110px;" >
                <input type="hidden" id="devDeptOid" name="devDeptOid" value="<%=searchCondition.getString("devDeptOid") %>">
                <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment();"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
            <td class="tdwhiteL" colspan="3">
                <input id="planEndStartDate" name="planEndStartDate" value="<%=searchCondition.getString("planEndStartDate") %>" class="txt_field" size="12" maxlength=15/> <!--  readonly onclick="javascript:showCal(planEndStartDate);" -->
                <a href="javascript:;" onclick="javascript:showCal('planEndStartDate');"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planEndStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;~&nbsp;
                <input id="planEndEndDate" name="planEndEndDate" value="<%=searchCondition.getString("planEndEndDate") %>" class="txt_field" size="12" maxlength=15/> <!--  readonly onclick="javascript:showCal(planEndEndDate);" -->
                <a href="javascript:;" onclick="javascript:showCal('planEndEndDate');"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planEndEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;&nbsp;(yyyy-mm-dd)
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00585") %><%--개발 담당자--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="tempsearchProductPm" name="tempsearchProductPm" value="<%=searchCondition.getString("tempsearchProductPm") %>" class="txt_fieldRO" readonly style="width:110px;" >
                <input type="hidden" id="searchProductPm" name="searchProductPm" value="<%=searchCondition.getString("searchProductPm") %>">
                <a href="javascript:addUser('searchProductPm');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('searchProductPm');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        </table>
        <table style="width: 780px;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- [END] search condition -->
        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/performance/ListPerformance2GridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>" 
						    Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp" 
						    Data_Method="POST" 
						    Data_Param_Result="<%=tgData %>" 
						    Data_Param_Pagingsize="<%=pagingSize %>" 
						    Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Project_List" 
						
						    Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp" 
						    Page_Format="Internal" 
						    Page_Data="TGData" 
						    Page_Method="POST" 
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
</form>
</body>
</html>
