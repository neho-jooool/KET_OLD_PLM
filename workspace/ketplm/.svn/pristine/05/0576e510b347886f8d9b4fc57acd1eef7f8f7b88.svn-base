<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Vector,
                java.util.List,
                java.util.HashMap,
                java.util.Map,
                java.util.Hashtable"%>

<%@page import="wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.State,
                wt.fc.QueryResult"%>

<%@page import="e3ps.ews.beans.EWSUtil,
                e3ps.common.code.NumberCode,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.StringUtil,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.web.PageControl"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request"/>
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

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 진행상태
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "EWSSTEP");
    List<Map<String, Object>> ewsStepNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 생산처 구분-사내/외주
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "VENDORFLAG");
    List<Map<String, Object>> vendorFlagNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    ArrayList inoutCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdinOut")), "," );   //  생산처 구분
    ArrayList stepCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("Hdstep")), "," );     // 진행상태
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="kr">
<head>
<meta charset="UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "02809") %><%--초기유동관리--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/jsp/ews/EWSCommon.js"></script>

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

    function hideProcessing() {
    }

    //달력 Open
    function openCal2(variableName) {
        var str = "/plm/jsp/common/calendar.jsp?obj=" + variableName + "&function=changeDate";
        var opts = "toolbar=0,location=0,directory=0,status=0,menubar=0,scrollbars=0,resizable=0,";
        leftpos = (screen.width - 224) / 2;
        toppos = (screen.height - 230) / 2;
        rest = "width=224,height=230,left=" + leftpos + ',top=' + toppos;

        var newwin = window.open(str, "calendar", opts + rest);
        newwin.focus();
    }

    //날짜형식 YYYY-MM-DD
    function changeDate(obj, Date) {
        var arrStartDate = Date.split("/");
        var change = arrStartDate[0] + '-' + arrStartDate[1] + '-' + arrStartDate[2];
        document.getElementById(obj).value = change;
    }

    //필드 값 초기화
    function deleteValueAll() {
        document.forms[0].ewsno.value = "";
        document.forms[0].pjtno.value = "";
        document.forms[0].partno.value = "";
        document.forms[0].partname.value = "";
        $("#inout").multiselect("uncheckAll");
        $("#step").multiselect("uncheckAll");
        document.forms[0].production.value = "";
        document.forms[0].fstcharge.value = "";
        document.forms[0].fstchargename.value = "";
        document.forms[0].creator.value = "";
        document.forms[0].creatorname.value = "";
        document.forms[0].createdate.value = "";

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    // 프로젝트 겁색 팝업
    function popupProject(targetId) {
        var url = "/plm/jsp/project/SearchPjtPop.jsp?status=P&type=P&modal=Y&mode=m"
        var selectedPartAry = window.showModalDialog(url, window, "help=no; resizable=no; status=no; scroll=yes; dialogWidth=1024px; dialogHeight:768px; center:yes");
        if (selectedPartAry != undefined && selectedPartAry.length > 0) {
            var isAppend = "Y";
            setProject(targetId, selectedPartAry, isAppend);
        }
    }

    function setProject(targetId, arrObj, isAppend) {
        var partData = new Array();
        for ( var i = 0; i < arrObj.length; i++) {
            // [0] -  oid       // [1] - project number
            var infoArr = arrObj[i];
            //partData[i] = infoArr[0];
            partData[i] = infoArr[1];
        }

        var tmpNo = new Array();
        if ($("#" + targetId).val() != "" && isAppend == "Y") {
            // ID 중복 제거
            tmpNo = $.merge($("#" + targetId).val().split(", "), partData);
            tmpNo = $.unique(tmpNo.sort());
        } else {
            tmpNo = partData.sort();
        }

        $("#" + targetId).val(tmpNo.join(", "));
    }

    function setProject1(objArr) {
        if (objArr.length == 0) {
            return;
        }

        var trArr;
        var str = "";
        var form = document.forms[0];
        for ( var i = 0; i < objArr.length; i++) {
            trArr = objArr[i];
            form.projectOid.value = trArr[0];
            form.pjtno.value = trArr[1];
        }
    }

    function doDeleteProject() {
        document.forms[0].pjtno.value = "";
    }

    function perPage(v) {
        document.forms[0].perPage.value = v;

        search();
    }

    // 검색
    function search() {
        showProcessing();     // 진행중 Bar 표시
        disabledAllBtn();     // 버튼 비활성화

        document.forms[0].HdinOut.value = $("#inout").val();
        document.forms[0].Hdstep.value = $("#step").val();

        document.forms[0].cmd.value = "search";
        document.forms[0].target = "_self";
        document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningServlet';
        document.forms[0].submit();
    }

    //엑셀저장
    function excelDown() {

        document.forms[0].HdinOut.value = $("#inout").val();
        document.forms[0].Hdstep.value = $("#step").val();

        document.forms[0].cmd.value = "excel";
        document.forms[0].action = '/plm/servlet/e3ps/EarlyWarningServlet';
        document.forms[0].submit();
    }

    //S등급 프로젝트 조회권한 제한
    function goAlert() {
        alert('<%=messageService.getString("e3ps.message.ket_message", "03186") %><%--해당 초기유동관리의 상세조회 권한이 없습니다--%>');
    }


    // ==  [Start] 사람 검색  == //
    function delUser(targetId) {
        $("#" + targetId).val("");
        $("#" + targetId + "name").val("");
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

            tmpName = $.merge($("#" + targetId + "name").val().split(", "), userName);
            tmpName = $.unique(tmpName.sort());
        }
        else {
            tmpId = userId.sort();
            tmpName = userName.sort();
        }

        $("#" + targetId).val(tmpId.join(", "));
        $("#" + targetId + "name").val(tmpName.join(", "));
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
    
    function go_to(url){
    	    //window.location=url;
     getOpenWindow2(url,'NotifyCreatePopup',950,700);
   }
    
    function register(){
    	getOpenWindow2('/plm/jsp/common/loading.jsp?url=/plm/jsp/ews/CreateEarlyWarning.jsp','NotifyRegisterPopup',950,700);
    }

    //Jquery
    $(document).ready(function(){
    	treeGridResize("#EarlyWarningSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    	
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#inout").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#step").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        CalendarUtil.dateInputFormat('createdate');
        SuggestUtil.bind('USER', 'creatorname', 'creator');
        SuggestUtil.bind('USER', 'fstchargename', 'fstcharge');

    });
//-->
</script>
</head>

<body class="body-space">
<form name="frm" method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" />
<input type="hidden" name="HdinOut" value="" />
<input type="hidden" name="Hdstep" value="" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<!-- hidden end -->

<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table  style="width: 100%; height: 28px;">
                <tr>
                    <td width="20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02816") %><%--초기유동관리검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02809") %><%--초기유동관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "02816") %><%--초기유동관리검색--%>
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
                                <a href="javascript:register();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01310") %><%--등록--%></a>

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
                                <!-- 검색 -->
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
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <table  style="width: 100%;">
        <tr>
            <td  class="tdblueL"  width="120px" ><%=messageService.getString("e3ps.message.ket_message", "00946") %><%--관리번호--%></td>
            <td  class="tdwhiteL"><input type="text" name="ewsno" style="width: 98%;" class="txt_field"  value="<%=StringUtil.checkNull( (String)searchCondition.get("ewsno") )%>"></td>
            <td class="tdblueL"  width="120px" ><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
            <td class="tdwhiteL0">
                <input type="text" name="pjtno" readonly class="txt_fieldRO" style="width:80%" value="<%=StringUtil.checkNull( (String)searchCondition.get("pjtno") )%>" id="pjtno">
                &nbsp;<a href="javascript:;" onClick="javascript:popupProject('pjtno');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                &nbsp;<a href="javascript:;" onClick="javascript:doDeleteProject();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                <input type="hidden" name="projectOid">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
            <td class="tdwhiteL">
                 <input type="text" id="partno" name="partno" class="txt_fieldRO" style="width:80%" value="<%=StringUtil.checkNull( (String)searchCondition.get("partno") )%>" readonly>
                 &nbsp;<a href="javascript:;" onClick="javascript:selectPartNo('partno');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
                 &nbsp;<a href="javascript:;" onClick="javascript:clearPartNo('partno');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td  class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
            <td  class="tdwhiteL0"><input type="text" name="partname" style="width: 98%;"  class="txt_field"   value="<%=StringUtil.checkNull( (String)searchCondition.get("partname") )%>" ></td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01792") %><%--생산처 구분--%></td>
            <td class="tdwhiteL">
                <select id="inout" name="inout" class="fm_jmp" style="width:270px" multiple="multiple">
                <%
                for ( int i=0; i<vendorFlagNumCode.size(); i++ ) {
                %>
                <option value="<%=vendorFlagNumCode.get(i).get("code") %>" <%=inoutCondList.contains( vendorFlagNumCode.get(i).get("code").toString() ) ? " selected" : "" %>><%=vendorFlagNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01791") %><%--생산처--%></td>
            <td class="tdwhiteL0"><input type="text" name="production" style="width:98%"  class="txt_field"   value="<%=StringUtil.checkNull( (String)searchCondition.get("production") )%>"   ></td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01973") %><%--수행담당자--%></td>
            <td class="tdwhiteL">
                <input type='hidden'  name='fstcharge'  id='fstcharge' value='<%=StringUtil.checkNull( (String)searchCondition.get("fstcharge") )%>'>
                <input type="text" name="fstchargename" id="fstchargename" class="txt_field"  value="<%=StringUtil.checkNull( (String)searchCondition.get("fstchargename") )%>"   style="width:200px;">
                &nbsp;<a href="javascript:addUser('fstcharge');"><img src="/plm/portal/images/icon_user.gif" border="0px"></a>
                &nbsp;<a href="javascript:delUser('fstcharge');"><img src="/plm/portal/images/icon_delete.gif" border="0px"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02729") %><%--진행단계--%></td>
            <td class="tdwhiteL0">
                <select id="step" name="step" class="fm_jmp"  style="width: 270px;"   multiple="multiple">
                <%
                for ( int i=0; i<ewsStepNumCode.size(); i++ ) {
                %>
                <option value="<%=ewsStepNumCode.get(i).get("code") %>" <%=stepCondList.contains( ewsStepNumCode.get(i).get("code").toString() ) ? " selected" : "" %>><%=ewsStepNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
                <input type='hidden' name='creator' id='creator'   value='<%=StringUtil.checkNull( (String)searchCondition.get("creator") )%>'>
                <input type="text" id="creatorname" name="creatorname" class="txt_field"  value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorname") )%>"  style="width:200px;">
                &nbsp;<a href="javascript:addUser('creator');"><img src="/plm/portal/images/icon_user.gif" border="0px"></a>
                &nbsp;<a href="javascript:delUser('creator');"><img src="/plm/portal/images/icon_delete.gif" border="0px"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
            <td class="tdwhiteL0">
                <input type="text" name="createdate" id="createdate"  class="txt_field"   value="<%=StringUtil.checkNull( (String)searchCondition.get("createdate") )%>"   style="width:85px;" >
                &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('createdate');" style="cursor: hand;">
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
                            Layout_Url="/plm/jsp/ews/SearchEarlyWarningGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
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
