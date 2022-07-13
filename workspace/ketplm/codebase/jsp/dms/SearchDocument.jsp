<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.Map,
                java.util.List,
                java.util.HashMap,
                java.util.Vector,
                java.util.ArrayList,
                java.util.Calendar,
                java.text.SimpleDateFormat"%>

<%@page import="wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.State"%>

<%@page import="e3ps.common.util.WCUtil,
                e3ps.dms.entity.KETDocumentCategory,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.KETParamMapUtil,
                e3ps.dms.beans.DMSUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="resultStr" class="java.lang.String" scope="request"/>

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

    if ( searchCondition.isEmpty() ) {
        Calendar cal = Calendar.getInstance();
        	searchCondition.put("postdate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		cal.add(Calendar.MONTH, -1);
		searchCondition.put("predate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 사업부
    parameter.clear();
    parameter.put("locale", messageService.getLocale());
    parameter.put("codeType", "DIVISIONTYPE");
    List<Map<String, Object>> divisionTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 품질확보절차
    parameter.clear();
    parameter.put("locale", messageService.getLocale());
    parameter.put("codeType", "DOCUMENTQUALITY");
    List<Map<String, Object>> qualityNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 고객 제출자료
    parameter.clear();
    parameter.put("locale", messageService.getLocale());
    parameter.put("codeType", "BUYERSUMMIT");
    List<Map<String, Object>> buyersummitNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 버전
    parameter.clear();
    parameter.put("locale", messageService.getLocale());
    parameter.put("codeType", "VERSION");
    List<Map<String, Object>> versionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    // 화면 처음 들어 올 경우 라디오버튼 기본 값 선택
    if (StringUtil.checkNull((String) searchCondition.get("islastversion")) == "") {
		searchCondition.put("isBuyerSummit", "0");
    }
    if (StringUtil.checkNull((String) searchCondition.get("islastversion")) == "") {
		searchCondition.put("islastversion", "LATEST");
    }

    String categoryCode = "";
    String categoryName = messageService.getString("e3ps.message.ket_message", "01445")/*미등록되어 있습니다*/;
    String docTypeCode = null;
    String categoryCode1 = "";
    String categoryCode2 = "";

    KETDocumentCategory docCate = null;

    // 1 Level 분류체계 찾기
    parameter.clear();
    parameter.put("docTypeCode", "PD");
    parameter.put("locale", messageService.getLocale().toString());
    parameter.put("parentCode", "ROOT");

    List<Map<String, Object>> categoryList = DMSUtil.getDocumentCategory(parameter);
    if (categoryList.size() > 0) {
		categoryCode1 = categoryList.get(0).get("categoryCode").toString();
		categoryName = categoryList.get(0).get("categoryName").toString();
    }

    String divisionCode = "";
    if (CommonUtil.isMember("전자사업부")) {
		divisionCode = "ER";
    } else if (CommonUtil.isMember("자동차사업부")) {
		divisionCode = "CA";
    }

    if (searchCondition.getStringArray("divisionCode").length == 0) {
		searchCondition.put("divisionCode", divisionCode);
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
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<script type="text/javascript" src="/plm/portal/js/org.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/processing.html" %>
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

    <%--
    // Grid Rendering 시 호출 - 검색 결과 제한 건수 이상일 경우 alert message 표시
    Grids.OnRenderStart = function(G) {
        <%
            if ( "T".equals(StringUtil.checkNull(resultStr)) ) {
        %>
        alert("검색 결과 제한 건수가 초과되어 제한 건수만큼 표시됩니다.\n[제한 건수 : " + "<%= PropertiesUtil.getSearchGridCountLimit() %>" + "]");
        <%
            }
        %>
    }
    --%>

    function hideProcessing() {
        var div1 = document.getElementById('div1');
        var div2 = document.getElementById('div2');
        div1.style.display = "none";
        div2.style.display = "none";
    }

    //Document Category Ajax
    function numCodeAjax(docTypeCode, parentCode, targetId) {
        $.ajax( {
            url : "/plm/servlet/e3ps/DocumentCategoryAjax",
            type : "POST",
            data : {docTypeCode:docTypeCode, parentCode:parentCode},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.returnObj, function() {
                    $("#"+targetId).append("<option value='"+this.categoryCode+"'>"+ this.categoryName +"</option>");
                });
            }
        });
    }

    function changeCategory2() {

        $("#category3").multiselect("uncheckAll");
        $("#category3").empty().data('options');

        if ( $("#category2").val() != null ) {
             //2단계 분류체계변경시 CateSelect.jsp를 호출하여 3단계를 화면에 나타내준다.
            numCodeAjax("PD", $("#category2").val().join(","), "category3");
        }
    }

    // ==  [Start] 고객사 검색  == //
    function deleteCustomLine() {
        $("#buyerCodeStr").val("");
        $("#buyerCode").val("");
    }

    function insertCustomLine() {
        var mode = "multi";
        var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth=0&selectedDepth=0&codetype=SAPSUBCONTRACTOR&command=select&mode="+mode;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=850px; dialogHeight:500px; center:yes");
        if ( typeof returnValue == "undefined" || returnValue == null ) {
            return;
        }

        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "Y";
        acceptBuyer(returnValue, isAppend);
    }

    function acceptBuyer(arrObj, isAppend) {
        var buyerId = new Array();     // Id
        var buyerName = new Array();   // Nmae
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - value      // [1] - codecode   // [2] - codename
            // [3] - codedesc   // [4] - codelong

            var infoArr = arrObj[i];
            buyerId[i] = infoArr[0];
            buyerName[i] = infoArr[2];
        }

        var tmpId = new Array();
        var tmpName = new Array();
        if ( $("#buyerCodeStr").val() != "" && isAppend == "Y" ) {
            // ID 중복 제거
            tmpId = $.merge($("#buyerCodeStr").val().split(", "), buyerId);
            tmpId = $.unique(tmpId.sort());

            tmpName = $.merge($("#buyerCode").val().split(", "), buyerName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = buyerId.sort();
            tmpName = buyerName.sort();
        }

        $("#buyerCodeStr").val(tmpId.join(", "));
        $("#buyerCode").val(tmpName.join(", "));

    }
    // ==  [End] 고객사 검색  == //


    function perPage(v) {
        if ( !valiDate() ) return;

        document.frm.perPage.value = v;

        //search();
        loadEjsGrid();
    }

    function search() {
        if ( !valiDate() ) return;

        //showProcessing();     // 진행중 Bar 표시
        //disabledAllBtn();     // 버튼 비활성화

        //document.frm.cmd.value = "search";
        //document.frm.action = "/plm/servlet/e3ps/ProjectDocumentServlet";
        //document.frm.submit();
        loadEjsGrid();
    }
    
 // function 추가
    function loadEjsGrid(){
        try{

           var idx = 0;
           var D = Grids[idx].Data;
           var formName = "frm"; 

           D.Layout.Param.Pagingsize = $("input[name='perPage']").val();

           D.Data.Url = "/plm/servlet/e3ps/ProjectDocumentServlet"; 
           D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
           D.Data.Param.cmd = "searchGridData";

           D.Page.Url = "/plm/servlet/e3ps/ProjectDocumentServlet";
           D.Page.Params =  D.Data.Params;
           D.Page.Param.cmd = "searchGridPage";

           Grids[idx].Reload(D);

           var S = document.getElementById("Status"+idx);
           if(S) S.innerHTML="Loading";

        }catch(e){
            alert(e.message);
        }
    }

    function clearAll(){
        $("#category2").multiselect("uncheckAll");
        $("#category3").multiselect("uncheckAll");
        $("#category3").empty().data('options');
        $("#documentNo").val("");
        $("#divisionCode").multiselect("uncheckAll");
        if ( "<%=divisionCode %>" != "" ) {
            $("#divisionCode").val("<%=divisionCode %>").attr('selected','selected');
        }
        $("#divisionCode").multiselect("refresh");
        $("#documentName").val("");
        $("#authorStatus").multiselect("uncheckAll");
        $("#creator").val("");
        $("#creatorName").val("");
        $("#predate").val("");
        $("#postdate").val("");
        $("#isBuyerSummit").eq(0).attr("checked", true);
        $("#buyerCode").val("");
        $("#buyerCodeStr").val("");
        $("#projectNo").val("");
        $("#projectName").val("");
        $("#islastversion").eq(0).attr("checked", true);
        $("#quality").multiselect("uncheckAll");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);

        // Category Disable
        categoryDisable();
    }

    function excelDown(){
        document.frm.cmd.value = "excelDown";

        document.frm.action = "/plm/servlet/e3ps/ProjectDocumentServlet";
        document.frm.submit();
    }

    function changeDate1(){
        var startDate;
        startDate = document.frm.predate.value;

        if (startDate != "" ) {
            if ( !dateCheck(startDate,"-") ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>");
                document.frm.predate.value = "";
            }
        }
    }

    function changeDate2(){
        var endDate;
        endDate = document.frm.postdate.value;
        if ( endDate != "" ) {
            if (!dateCheck(endDate,"-")){
                alert("<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>");
                document.frm.postdate.value = "";
            }
        }
    }

    function valiDate(){
        if( $("#predate").val() != "" && ( $("#postdate").val() != "" ) ) {
            if( $("#predate").val() > $("#postdate").val() ) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "02430") %><%--작성일자의 범위가 잘못 지정되어 있습니다--%>');
                $("#predate").val("");
                $("#postdate").val("");
                return false;
            }
        }
        return true;
    }


    // ==  [Start] 사람 검색  == //
    function clearUser() {
        $("#creator").val("");
        $("#creatorName").val("");
    }

    function selectUser() {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m";
        attacheMembers = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=720px; dialogHeight:510px; center:yes");
        if ( typeof attacheMembers == "undefined" || attacheMembers == null ) {
            return;
        }
        // isAppend : Y - 이전 값에 현재 선택된 값 추가
        // isAppend : N - 현재 선택 된 값만 추가
        var isAppend = "Y";
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
            userId[i] = infoArr[3];
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
    // ==  [End] 사람 검색  == //

    function selProjectNo(){

        var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&modal=Y";
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes");

        if ( returnValue == null ) {
            return;
        }
        var objArr = returnValue;
        var trArr = objArr[0];
        $("#projectNo").val( trArr[1] );
        $("#projectName").val( trArr[2] );
    }

    function delProjectNo(){
        $("#projectNo").val("");
        $("#projectName").val("");
    }

    function categoryDisable() {
        if ( $("#category2 option").size() == 0 ) {
            $("#category2").length = 0;
            $("#category2").multiselect('disable');
        }
        else {
            $("#category2").multiselect('enable');
        }

        if ( $("#category3 option").size() == 0 ) {
            $("#category3").length = 0;
            $("#category3").multiselect('disable');
        }
        else {
            $("#category3").multiselect('enable');
        }
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

        $("#category2").multiselect({
            minWidth: 150,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#category2").multiselect({
            close: function(event, ui) {
                // event handler here
                $("#category3").multiselect('refresh');

                // Category 활성화
                categoryDisable();
            }
        });

        $("#category3").multiselect({
            minWidth: 170,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#divisionCode").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#authorStatus").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#quality").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        categoryDisable();
    });
//]]>
</script>
</head>

<body>
<form name="frm" method="POST" >

<!-- hidden begin -->
<input type="hidden" name="cmd" />
<input type="hidden" name="perPage" value="<%=pagingSize %>">
<!-- hidden end -->

<table style="width: 780px; height: 100%;">
<tr>
    <td valign="top">
        <table style="width: 780px;">
        <tr>
            <td>
                <table style="width: 780px; height: 28px;">
                <tr>
                    <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01404") %><%--문서검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01406") %><%--문서관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00640") %><%--개발산출문서 관리--%>
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
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <td width="5">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
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
        <table style="width: 780px;">
        <tr>
            <td  class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 780px;">
        <tr>
            <td class="tdblueL" width="120px;"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
            <td colspan="3" class="tdwhiteL0">
                <input type="text" readonly id="category1" name="category1" value="<%=categoryName%>" class="txt_fieldRO"  style="width:190px">
                <select id="category2" name="category2" class="fm_jmp" multiple="multiple" style="width:190px" onchange="javascript:changeCategory2();">
                <%
                parameter.clear();
                parameter.put("docTypeCode", "PD");
                parameter.put("locale",      messageService.getLocale().toString());
                parameter.put("parentCode",  categoryCode1);

                categoryList = DMSUtil.getDocumentCategory(parameter);

                for ( Map<String, Object> map : categoryList ) {
                    categoryCode = map.get("categoryCode").toString();
                    categoryName = map.get("categoryName").toString();
                %>
                <option value="<%=categoryCode%>" <%= KETParamMapUtil.contains(searchCondition.getStringArray("category2"), categoryCode) ? " selected" : "" %>><%=categoryName%></option>
                <%
                }

                if ( searchCondition.getStringArray("category2").length > 0  ) {
                    categoryCode2 = KETParamMapUtil.toString(searchCondition.getStringArray("category2"));
                }
                %>
                </select>
                <select id="category3" name="category3" class="fm_jmp" multiple="multiple" style="width:265px">
                <%
                if ( categoryCode2.length() > 0 ) {
                    parameter.clear();
                    parameter.put("docTypeCode", "PD");
                    parameter.put("locale",      messageService.getLocale().toString());
                    parameter.put("parentCode",  categoryCode2);

                    categoryList = DMSUtil.getDocumentCategory(parameter);

                    for ( Map<String, Object> map : categoryList ) {
                        categoryCode = map.get("categoryCode").toString();
                        categoryName = map.get("categoryName").toString();
                %>
                <option value="<%=categoryCode%>" <%=KETParamMapUtil.contains(searchCondition.getStringArray("category3"), categoryCode) ? " selected" : "" %>><%=categoryName%></option>
                <%
                    }
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL" width="120px;" ><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
            <td class="tdwhiteL" width="270px;" >
                <input type="text" id="documentNo" name="documentNo" class="txt_field"  style="width:270px;" value="<%=searchCondition.getString("documentNo") %>">
            </td>
            <td class="tdblueL" width="120px;" ><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL0" width="270px;" >
                <select id="divisionCode" name="divisionCode" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                for ( int i=0; i<divisionTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionTypeNumCode.get(i).get("code") %>" <%=KETParamMapUtil.contains(searchCondition.getStringArray("divisionCode"), divisionTypeNumCode.get(i).get("code")) ? " selected" : "" %>><%=divisionTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
            <td class="tdwhiteL"><input type="text" id="documentName" name="documentName" class="txt_field"  style="width:270px" value="<%=searchCondition.getString("documentName") %>"></td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
            <td class="tdwhiteL0">
                <select id="authorStatus" name="authorStatus" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                String[] authorStatus = searchCondition.getStringArray("authorStatus");

                LifeCycleTemplate LCtemplate = LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", WCUtil.getPDMLinkProduct().getContainerReference());
                Vector lcStates = LifeCycleHelper.service.findStates(LCtemplate);
                State lstate = null;

                for ( int i=0; i<lcStates.size(); i++ ) {
                    lstate = (State)lcStates.elementAt(i);
                %>
                    <option value="<%=lstate %>" <%=(KETParamMapUtil.contains(authorStatus, lstate.toString()) ) ? " selected" : "" %>><%=lstate.getDisplay(messageService.getLocale()) %></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
                <input type="hidden" id="creator" name="creator" value="<%=searchCondition.getString("creator") %>">
                <input type="text" id="creatorName" name="creatorName" readonly class="txt_fieldRO"  style="width:220px;" value="<%=searchCondition.getString("creatorName") %>">
                &nbsp;<a href="javascript:selectUser()"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                &nbsp;<a href="JavaScript:clearUser()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
            <td class="tdwhiteL0">
                <input type="text" readonly id="predate" name="predate" class="txt_fieldRO" style="width:70px;" value="<%=searchCondition.getString("predate") %>" onChange="javascript:changeDate1()">
                &nbsp;<img src="/plm/portal/images/icon_6.png"border="0" onClick="javascript:showCal(predate)" style="cursor:hand;">
                <a href="javascript:clearDate('predate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                &nbsp;~&nbsp;
                <input type="text" readonly id="postdate" name="postdate" class="txt_fieldRO"  style="width:70px;" value="<%=searchCondition.getString("postdate") %>" onChange="javascript:changeDate2()">
                &nbsp;<img src="/plm/portal/images/icon_6.png"border="0" onClick="javascript:showCal(postdate)" style="cursor:hand;">
                <a href="javascript:clearDate('postdate')"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00830") %><%--고객 제출자료--%></td>
            <td class="tdwhiteL">
                <%
                for ( int i=0; i<buyersummitNumCode.size(); i++ ) {
                %>
                <input id="isBuyerSummit" name="isBuyerSummit" type="radio" class="Checkbox" value="<%=buyersummitNumCode.get(i).get("code") %>" <%if ( searchCondition.getString("isBuyerSummit").equals( buyersummitNumCode.get(i).get("code") ) ){%>checked<%}%> ><%=buyersummitNumCode.get(i).get("value")%>
                <%
                }
                %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
            <td class="tdwhiteL0">
                <input type="hidden" id="buyerCodeStr" name="buyerCodeStr" value="<%=searchCondition.getString("buyerCodeStr")%>">
                <input type="text" readonly id="buyerCode" name="buyerCode" class="txt_fieldRO"  style="width:215px;" value="<%=searchCondition.getString("buyerCode")%>">
                &nbsp;<a href="javascript:insertCustomLine()"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                <a href="javascript:deleteCustomLine()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03115") %><%--프로젝트번호/명--%></td>
            <td colspan="3" class="tdwhiteL0">
                <input type="text" id="projectNo" name="projectNo" class="txt_fieldRO" readonly style="width:180px;" value="<%=searchCondition.getString("projectNo")%>">
                <input type="text" id="projectName" name="projectName" class="txt_fieldRO" readonly style="width:420px;" value="<%=searchCondition.getString("projectName")%>">
                &nbsp;<a href="javascript:selProjectNo()"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                <a href="javascript:delProjectNo()"><img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
            <td class="tdwhiteL">
                <%
                for ( int i=0; i<versionNumCode.size(); i++ ) {
                %>
                <input id="islastversion" name="islastversion" type="radio" class="Checkbox" value="<%=versionNumCode.get(i).get("code") %>" <%if ( searchCondition.getString("islastversion").equals( versionNumCode.get(i).get("code") ) ){%>checked<%}%> ><%=versionNumCode.get(i).get("value")%>
                <%
                }
                %>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03030") %><%--품질확보절차--%></td>
            <td class="tdwhiteL0">
                <select id="quality" name="quality" class="fm_jmp" style="width: 270px;" multiple="multiple">
                <%
                String[] quality = searchCondition.getStringArray("quality");
                for ( int i=0; i<qualityNumCode.size(); i++ ) {
                %>
                <option value="<%=qualityNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(quality, qualityNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=qualityNumCode.get(i).get("value")%></option>
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

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                        <bdo 
                            <%-- 
                            Debug="3" 
                            --%>
                            Debug="1" AlertError="0" 
						    Layout_Url="/plm/jsp/dms/SearchDocumentGridLayout.jsp"
						    Layout_Param_Pagingsize="<%=pagingSize %>" 
						    
						    Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp" 
						    Data_Format="Internal"
						    Data_Data="TGData"
						    Data_Method="POST" 
						
						    Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp"
						    Page_Format="Internal" 
						    Page_Data="TGData" 
						    Page_Method="POST" 
                            
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Document_List"
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
