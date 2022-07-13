<%@page import="java.util.HashMap"%>
<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.util.CommonUtil"%>
<%@page import="e3ps.common.util.PropertiesUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


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
    // Rank
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "RANK");
    parameter.put("code",     "RAN1000");
    List<Map<String, Object>> rankNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    //parameter
    String radioValue = request.getParameter("radioValue");
    if ( radioValue == null ) {
        radioValue = "3";
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

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/context.jsp"%>
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

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    function search() {

//         showProcessing();     // 진행중 Bar 표시
//         disabledAllBtn();     // 버튼 비활성화

//         document.frm.action = "/plm/servlet/e3ps/PerformanceServlet?command=searchProd";
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
           D.Data.Param.command = "searchProdGridData";

           D.Page.Url = "/plm/servlet/e3ps/PerformanceServlet";
           D.Page.Params =  D.Data.Params;
           D.Page.Param.command = "searchProdGridPage";

           Grids[idx].Reload(D);

           var S = document.getElementById("Status"+idx);
           if(S) S.innerHTML="Loading";

        }catch(e){
            alert(e.message);
        }
    }

    function clearAll(){
        $("#searchPjtNo").val("");
        $("#searchPart").val("");
        $("#tempsearchPm").val("");
        $("#searchPm").val("");
        $("#searchPjtName").val("");
        $("#searchDEVELOPENTTYPE").multiselect("uncheckAll");
        $("#planStartStartDate").val("");
        $("#planStartEndDate").val("");
        $("#searchRank").multiselect("uncheckAll");
        $("#planEndStartDate").val("");
        $("#planEndEndDate").val("");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function excelDown(){
        document.frm.action = "/plm/servlet/e3ps/PerformanceServlet?command=excelDown";
        document.frm.submit();
    }

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

        $("#searchRank").multiselect({
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
<input type="hidden" name="radioValue" value="<%=radioValue%>">
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
                else { %>
                    <input name="radio" type="hidden" value="3">
                <%
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
            <td class="tdblueL"  style="width: 100px;">Project No</td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="searchPjtNo" name="searchPjtNo" value="<%=searchCondition.getString("searchPjtNo") %>" class="txt_field"  style="width:160px;">
            </td>
            <td class="tdblueL"  style="width: 100px;">Part No</td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="searchPart" name="searchPart" value="<%=searchCondition.getString("searchPart") %>" class="txt_fieldRO" readOnly style="width:100px;">
                <a href="javascript:selectPartNo('searchPart');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('searchPart');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"   style="width: 100px;">PM</td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input type="text" style="width:110px;" id="tempsearchPm" name="tempsearchPm" value="<%=searchCondition.getString("tempsearchPm") %>" class="txt_fieldRO" readonly>
                <input type="hidden" id="searchPm" name="searchPm" value=<%=searchCondition.getString("searchPm") %>>
                <a href="javascript:addUser('searchPm');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('searchPm');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL">Project Name</td>
            <td class="tdwhiteL"colspan="3">
                <input type="text" id="searchPjtName" name="searchPjtName" value="<%=searchCondition.getString("searchPjtName") %>" class="txt_field" style="width:100%">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
            <td class="tdwhiteL0">
                <select id="searchDEVELOPENTTYPE" name="searchDEVELOPENTTYPE" class="fm_jmp" style="width:160px;" multiple="multiple">
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
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00817") %><%--계획{0}시작일--%></td>
            <td class="tdwhiteL"colspan="3">
                <input type="text" readonly id="planStartStartDate" name="planStartStartDate" class="txt_fieldRO" style="width:70px;" value="<%=searchCondition.getString("planStartStartDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png"onClick="javascript:showCal(planStartStartDate)" style="cursor:hand;">
                <a href="javascript:clearDate('planStartStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
                &nbsp;~&nbsp;
                <input type="text" readonly id="planStartEndDate" name="planStartEndDate" class="txt_fieldRO"  style="width:70px;" value="<%=searchCondition.getString("planStartEndDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png"onClick="javascript:showCal(planStartEndDate)" style="cursor:hand;">
                <a href="javascript:clearDate('planStartEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;&nbsp;(yyyy-mm-dd)
            </td>
            <td class="tdblueL">Rank</td>
            <td class="tdwhiteL0">
                <select id="searchRank" name="searchRank" class="fm_jmp" style="width:160px;" multiple="multiple">
                    <%
                    String[] rank = searchCondition.getStringArray("searchRank");
                    for ( int i=0; i<rankNumCode.size(); i++ ) {
                    %>
                    <option value="<%=rankNumCode.get(i).get("code") %>" <%=(KETParamMapUtil.contains(rank, rankNumCode.get(i).get("code")) ) ? " selected" : "" %>><%=rankNumCode.get(i).get("value")%></option>
                    <%
                    }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00826") %><%--계획{0}종료일--%></td>
            <td class="tdwhiteL0" colspan="5">
                <input type="text" readonly id="planEndStartDate" name="planEndStartDate" class="txt_fieldRO" style="width:70px;" value="<%=searchCondition.getString("planEndStartDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png"onClick="javascript:showCal(planEndStartDate)" style="cursor:hand;">
                <a href="javascript:clearDate('planEndStartDate')"><img src="/plm/portal/images/icon_delete.gif"></a>
                &nbsp;~&nbsp;
                <input type="text" readonly id="planEndEndDate" name="planEndEndDate" class="txt_fieldRO"  style="width:70px;" value="<%=searchCondition.getString("planEndEndDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_6.png"onClick="javascript:showCal(planEndEndDate)" style="cursor:hand;">
                <a href="javascript:clearDate('planEndEndDate')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;&nbsp;(yyyy-mm-dd)
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
                            Layout_Url="/plm/jsp/project/performance/ListPerformance3GridLayout.jsp" 
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
