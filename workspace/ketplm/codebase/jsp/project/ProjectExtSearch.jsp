<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Map,
                java.util.List,
                java.util.HashMap,
                java.util.Vector,
                java.text.SimpleDateFormat,
                java.util.Calendar"%>

<%@page import="wt.lifecycle.State"%>

<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.web.WebUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.KETParamMapUtil,
                e3ps.project.beans.ProjectHelper"%>

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>
<jsp:useBean id="searchList" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="hashMap" class="java.util.HashMap" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="resultStr" class="java.lang.String" scope="request"/>

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

    // 검토, 제품, 금형 구분
    String radioValue = StringUtil.checkNull(request.getParameter("radio"));
    if ( radioValue.equals("") ) {
        radioValue = WebUtil.getCookie(request, "radioValue");
        if ( radioValue == null || radioValue.length() == 0 ) {
            radioValue = "2";
        }
    }

    // Cookie 생성
    WebUtil.setCookie(response, "radioValue", radioValue);

    boolean isType0 = CommonUtil.isMember("전자사업부");
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isPMO = CommonUtil.isMember("자동차PMO");
    // 전자사업부 권한이면
    if ( searchCondition != null && searchCondition.get("bizUnit") != null ) {
        if ( searchCondition.get("bizUnit").equals("1") ) {
            isType0 = false;
        }
        else {
            isType0 = true;
        }
    }

    // 비용 Checkbox 표시 되는 권한처리
    String sapAuth = "0";
    if ( isbizAdmin || isPMO || CommonUtil.isAdmin() ) {
        sapAuth = "1";
    }
    // 비용 Check 여부
    String sapCheck = "0";
    if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("sap") != null && searchCondition.get("sap").equals("sap") ) {
        sapCheck = "1";
    }

    // Project Type 변경시 결과내재검색 초기화
    String changeRadio = request.getParameter("changeRadio");
    if ( changeRadio != null && changeRadio == "Y" ) {
        HttpSession s = request.getSession();
        s.setAttribute("projectSearchConditionList", null);
    }

    // 상태
    Vector states = null;

    Map<String, Object> parameter = new HashMap<String, Object>();
    // Project Type
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PROJECTTYPE");
    List<Map<String, Object>> projectTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 개발구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVELOPENTTYPE");
    List<Map<String, Object>> developTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // Rank
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    if ( isType0 ) {
        parameter.put("codeType", "RANK");
        parameter.put("code",     "RAN2000");
    }
    else {
        parameter.put("codeType", "RANK");
        parameter.put("code",     "RAN1000");
    }
    List<Map<String, Object>> rankNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 개발 날짜 구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVELOPDATETYPE");
    List<Map<String, Object>> developDateTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    List<Map<String, Object>> moldItemTypeNumCode      = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> moldProductTypeNumCode   = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> moldTypeNumCode          = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> makingTypeNumCode        = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> divisionNumCode          = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> productTypeNumCode       = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> assembledTypeNumCode     = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> customerEventNumCode     = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> productTypeLevel2NumCode = new ArrayList<Map<String, Object>>();
    List<Map<String, Object>> sealedNumCode            = new ArrayList<Map<String, Object>>();

    // 사업부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONNUMBER");
    divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    if ( isType0 ) {
        parameter.put("codeType", "PRODUCTTYPE");
        parameter.put("code",     "PRO2000");
    }
    else {
        parameter.put("codeType", "PRODUCTTYPE");
        parameter.put("code",     "PRO1000");
    }
    //제품구분
    productTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    // 조립구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ASSEMBLEDTYPE");
    assembledTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    // 최종사용처
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType",   "CUSTOMEREVENT");
    parameter.put("depthLevel", "child");
    customerEventNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    if ( radioValue.equals("2") ) {

        if( hashMap != null && hashMap.get("productType") != null ) {
            // 시리즈
            parameter.clear();
            parameter.put("locale",   messageService.getLocale());
            parameter.put("codeType", "PRODUCTTYPELEVEL2");
            parameter.put("code",     hashMap.get("productType"));
            productTypeLevel2NumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
        }
        // 방수여부
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "SEALED");
        sealedNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    }

    ArrayList pjtStateCondList          = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("pjtState")), ", " );
    ArrayList rankCondList              = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("rank")), ", " );
    ArrayList developDateTypeCondList   = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("developDateType")), ", " );
    ArrayList productTypeCondList       = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("productType")), ", " );
    ArrayList productTypeLevel2CondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("productTypeLevel2")), ", " );
    ArrayList developTypeCondList       = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("developType")), ", " );
    ArrayList assembledTypeCondList     = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("assembledType")), ", " );
    ArrayList customerEventCondList     = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("customerEvent")), ", " );
    ArrayList seriesCondList            = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("series")), ", " );
    ArrayList sealedCondList            = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("sealed")), ", " );
    ArrayList moldProductTypeCondList   = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("moldProductType")), ", " );
    ArrayList moldTypeCondList          = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("moldType")), ", " );
    ArrayList makingCondList            = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("making")), ", " );
    ArrayList itemTypeCondList          = KETStringUtil.getToken( KETStringUtil.nullFilter((String)hashMap.get("itemType")), ", " );

    if ( searchCondition.isEmpty() ) {
        Calendar cal = Calendar.getInstance();
        searchCondition.put("planStartEndDate1", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        searchCondition.put("planStartEndDate2", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        searchCondition.put("planStartEndDate3", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));

        cal.add(Calendar.MONTH, -1);
        searchCondition.put("planStartStartDate1", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        searchCondition.put("planStartStartDate2", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        searchCondition.put("planStartStartDate3", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));

        developDateTypeCondList.add(0, "DEVELOPDATESTART");
    }
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<style type="text/css">
/* jquery autocomplete ui */
.ui-autocomplete { height: 100px; overflow-y: scroll; overflow-x: hidden; font-size: 11px;}
</style>
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/ajax.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/suggestUtil.js"></script>
<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%@include file="/jsp/common/context.jsp" %>
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
<!--
    <%-- 서버 페이징시 불필요 
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
    function perPage(v) {
        document.forms[0].perPage.value = v;
        //search();
        loadEjsGrid();
    }
    
    function requestPop(oid){
        var url = '/plm/jsp/dms/ViewDevRequestPop.jsp?oid='+oid;
        window.open(url,'의뢰서',"status=1, menu=no, width=830, height=800");
    }

    function onPage(page){
        location.href="/plm/jsp/project/ProjectSearch.jsp?radio=" + page + "&changeRadio=Y";
    }

    function displayChange(type) {
        // Type : 1 검토, 2 제품, 3 금형
        if ( type == '1' ) {
            $("#reviewSearchDisplay").show();
            $("#productSearchDisplay").hide();
            $("#moldSearchDisplay").hide();
        } else if(type == '2') {
            $("#reviewSearchDisplay").hide();
            $("#productSearchDisplay").show();
            $("#moldSearchDisplay").hide();
        } else if(type =='3'){
            $("#reviewSearchDisplay").hide();
            $("#productSearchDisplay").hide();
            $("#moldSearchDisplay").show();
        }
    }

    // Number Code Ajax
    function numCodeAjax(codeType, code, targetId) {
        $.ajax( {
            url : "/plm/servlet/e3ps/NumberCodeAjax",
            type : "POST",
            data : {codeType:codeType, code:code},
            dataType : 'json',
            async : false,
            success: function(data) {
                $.each(data.numCode, function() {
                    $("#"+targetId).append("<option value='"+this.code+"' >"+ this.value +"</option>");
                });
            }
        });
    }

    // 사업부 변경시
    function dsChange(obj){
        var pjtType = $("input[name=radio]:radio:checked").val();
        var dsobj = "";

        if ( typeof(obj) == "object" ) {
            dsobj = obj.value;
        }
        else {
            dsobj = obj;
        }

        $("#productType" + pjtType).empty().data('options');
        $("#rank" + pjtType).empty().data('options');
        numCodeAjax("PRODUCTTYPE", "PRO"+dsobj+"000", "productType"+pjtType);
        numCodeAjax("RANK", "RAN"+dsobj+"000", "rank"+pjtType);
        $("#productType" + pjtType).multiselect("refresh");
        $("#rank" + pjtType).multiselect("refresh");

        if ( dsobj == "1") {
            $("#modleLabel").html("<%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%>");
        }
        else if (dsobj == "2") {
            $("#modleLabel").html('<%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%>');
        }
    }

    // 금형구분 변경시
    function itemTypeChagne() {
        var itemType = $("#itemType3").val();
        $("#moldProductType3").empty().data('options');
        if ( itemType == "Mold" ) {
            numCodeAjax("MOLDPRODUCTSTYPE", "MOL100", "moldProductType3");
        }
        else if ( itemType == "Press" ) {
            numCodeAjax("MOLDPRODUCTSTYPE", "MOL200", "moldProductType3");
        }
        else {
            numCodeAjax("MOLDPRODUCTSTYPE", "MOL100", "moldProductType3");
            numCodeAjax("MOLDPRODUCTSTYPE", "MOL200", "moldProductType3");
        }
        $("#moldProductType3").multiselect("refresh");
    }

    // 제품구분 변경시
    function changeProductType() {
        $("#productTypeLevel2").empty().data('options');
        if ( $("#productType2").val() != null ) {
            numCodeAjax("PRODUCTTYPELEVEL2", $("#productType2").val().join(","), "productTypeLevel2");
        }
        $("#productTypeLevel2").multiselect("refresh");
    }

    function clearAll() {
        if ( "<%=radioValue %>" == "1" ) {
            $("#pjtNo1").val("");
            if ( "<%=CommonUtil.isMember("전자사업부") %>" != "" ) {
                $("#dType1").val("1").attr('selected','selected');
            }
            else {
                $("#dType1").val("2").attr('selected','selected');
            }
            $("#dType1").multiselect("refresh");
            $("#tempsetPm1").val("");
            $("#setPm1").val("");
            $("#pjtState1").multiselect("uncheckAll");
            $("#productType1").multiselect("uncheckAll");
            $("#developType1").multiselect("uncheckAll");
            $("#pjtName1").val("");
            $("#devUserDept1").val("");
            $("#devUserDeptOid1").val("");
            $("#developDateType1").multiselect("uncheckAll");
            $("#planStartStartDate1").val("");
            $("#planStartEndDate1").val("");
            $("#assembledType1").multiselect("uncheckAll");
            $("#customerEvent1").multiselect("uncheckAll");
            $("#subcontractor1").val("");
            $("#rank1").multiselect("uncheckAll");

            // 사업부변경시 functioin 호출
            dsChange($("#dType1 option:selected").val());
        }
        else if ( "<%=radioValue %>" == "2" ) {
            $("#pjtNo2").val("");
            if ( "<%=CommonUtil.isMember("전자사업부") %>" != "" ) {
                $("#dType2").val("1").attr('selected','selected');
            }
            else {
                $("#dType2").val("2").attr('selected','selected');
            }
            $("#dType2").multiselect("refresh");
            $("#tempsetPm2").val("");
            $("#setPm2").val("");
            $("#productType2").multiselect("uncheckAll");
            $("#series2").multiselect("uncheckAll");
            $("#sealed2").multiselect("uncheckAll");
            $("#partNo2").val("");
            $("#pjtState2").multiselect("uncheckAll");
            $("#devUserDept2").val("");
            $("#devUserDeptOid2").val("");
            $("#pjtName2").val("");
            $("#developType2").multiselect("uncheckAll");
            $("#customerEvent2").multiselect("uncheckAll");
            $("#developDateType2").multiselect("uncheckAll");
            $("#planStartStartDate2").val("");
            $("#planStartEndDate2").val("");
            $("#assembledType2").multiselect("uncheckAll");
            $("#rank2").multiselect("uncheckAll");
            $("#subcontractor2").val("");
            $("#carTypeInfo2").val("");

            // 사업부변경시 functioin 호출
            dsChange($("#dType2 option:selected").val());
            // 제품구분변경시 function 호출
            changeProductType();
        }

        // 비용
        var G = Grids[0];
        G.SetValue(G.Toolbar, "Sap", "0", 1);
        document.ProjectSearch.sap.value = "";

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function perPage(v) {
        document.ProjectSearch.perPage.value = v;

        search();
    }

    function search(){
        //showProcessing();     // 진행중 Bar 표시
        //disabledAllBtn();     // 버튼 비활성화
        //document.ProjectSearch.command.value = "search";
        //document.ProjectSearch.action =  "/plm/servlet/e3ps/ProjectServlet";
        //document.ProjectSearch.submit();
        
        loadEjsGrid();
    }
    
    function loadEjsGrid(){
        try{
           
           var idx = 0;
           var D = Grids[idx].Data;
           var formName = "ProjectSearch";
           
           D.Layout.Param.Pagingsize = $("input[name='perPage']").val();
           
           D.Data.Url = "/plm/servlet/e3ps/ProjectServlet";
           D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
           D.Data.Param.command = "searchGridData";
           
           D.Page.Url = "/plm/servlet/e3ps/ProjectServlet";
           D.Page.Params =  D.Data.Params;
           D.Page.Param.command = "searchGridPage";
           
           Grids[idx].Reload(D);
           
           var S = document.getElementById("Status"+idx);
           if(S) S.innerHTML="Loading";
        
        }catch(e){
            alert(e.message);
        }
    }

    function excelDown() {
        <%if(!CommonUtil.isAdmin()){ %>
            if(!excelTimeCheck() ){
                alert("엑셀 다운 가능 시간은 \n\n 07:00 ~ 08:00 \n 12:00 ~ 13:00 \n 17:00 ~ 17:30 \n\n 입니다.");
                return;
            }
        <%}%>

        document.ProjectSearch.command.value = "excelDownProject";
        document.ProjectSearch.action =  "/plm/servlet/e3ps/ProjectServlet";
        document.ProjectSearch.submit();
    }

    function sapValue() {
        var G = Grids[0];
        if ( G.Toolbar.Sap == "1" ) {
            document.ProjectSearch.sap.value = "sap";
        }
        else {
            document.ProjectSearch.sap.value = "";
        }
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
    function delDepartment(targetId, targetName) {
        $("#" + targetId).val("");
        $("#" + targetName).val("");
    }

    function addDepartment(targetId) {
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

                if ( targetId == "devUserDept1" ) {
                    callServer(url, acceptDept1);
                }
                else if ( targetId == "devUserDept2" ) {
                    callServer(url, acceptDept2);
                }
                else if ( targetId == "devUserDept3" ) {
                    callServer(url, acceptDept3);
                }
            }
        }
        else {
            var param = "command=deptInfo&deptOid=" + attacheDept[0][0];
            var url = "/plm/jsp/project/ProjectGroupWareAjaxAction.jsp?" + param;

            if ( targetId == "devUserDept1" ) {
                callServer(url, acceptDept1);
            }
            else if ( targetId == "devUserDept2" ) {
                callServer(url, acceptDept2);
            }
            else if ( targetId == "devUserDept3" ) {
                callServer(url, acceptDept3);
            }
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

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "devUserDeptOid1", "devUserDept1");
    }

    function acceptDept2(req) {
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

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "devUserDeptOid2", "devUserDept2");
    }

    function acceptDept3(req) {
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

        deptMerge(decodeURIComponent(l_oid[0].text), decodeURIComponent(l_name[0].text), "devUserDeptOid3", "devUserDept3");
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
        var pType = "";
        if ( targetId == "dieNo3" ) {
            pType = "D";
        }
        else if ( targetId == "partNo3" ) {
            pType = "P";
        }
        else {
            pType = "A"
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

    // Jquery
    $(document).ready(function(){
        $("form[name=ProjectSearch]").keypress(function(e) {
            //Enter key
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        // Multi Combo 생성
        $("#dType1").multiselect({
            minWidth: 80,
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });

        $("#dType2").multiselect({
            minWidth: 80,
            multiple: false,
            header: false,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>',
            selectedList: 1
        });

        // 제품구분
        $("#productType1").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#productType2").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#productType2").multiselect({
            close: function(event, ui) {
                // event handler here
                changeProductType();
            }
        });

        $("#productTypeLevel2").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#series2").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#sealed2").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 상태
        $("#pjtState1").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#pjtState2").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#pjtState3").multiselect({
            minWidth: 60,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 개발구분
        $("#developType1").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#developType2").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#developType3").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 최종사용처
        $("#customerEvent1").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#customerEvent2").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
         });
        // 조립구분
        $("#assembledType1").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#assembledType2").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // Rank
        $("#rank1").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#rank2").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#rank3").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 개발날짜구분
        $("#developDateType1").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#developDateType2").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        $("#developDateType3").multiselect({
            minWidth: 80,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 금형구분
        $("#itemType3").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 금형분류
        $("#moldProductType3").multiselect({
            minWidth: 60,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 금형유형
        $("#moldType3").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        // 제작구분
        $("#making3").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
    });

    function excelTimeCheck(){
        var xmlHttp;
        if(window.XMLHttpRequest){                          // IE 이외에서  XMLHttpRequest 생성
            req = new XMLHttpRequest();
        }else if(window.ActiveXObject){                     // IE에서  XMLHttpRequest 생성
            req = new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlHttp = req;
        xmlHttp.open('HEAD',window.location.href.toString(),false);
        xmlHttp.setRequestHeader("Content-Type", "text/html");
        xmlHttp.send('');
        var st = xmlHttp.getResponseHeader("Date");
        var cal = new Date(st);

        var hour = cal.getHours();  //시 구하기
        var min = cal.getMinutes(); //분 구하기
        var day = cal.getDay();     //요일구하기 0-> 일요일
        if(min <10)      {
         min = "0"+min;          //1분이어도 01로 표시되게
        }
        //시간과 분을 문자열로 변환
        var hourTxt = hour.toString();
        var minTxt = min.toString();
        //시간과 분을 문자열로 붙인다음 *1을 하여 다시 숫자형으로 변환, 요일도 숫자형으로 변환
        var nowTimeTxt = hourTxt+minTxt;
        var nowTimeNum = nowTimeTxt * 1;
        var nowDayNum = day * 1;
        var minCheck = 659;     //최소시간  7시
        var maxCheck = 800;     //최대시간  8시

        var timeCheck = false;

        if( nowTimeNum > minCheck && nowTimeNum < maxCheck)     {
            timeCheck = true;
        }

        minCheck = 1159; //최소시간 12시
        maxCheck = 1400; //최대시간 1시

        if(!timeCheck){
         if( nowTimeNum > minCheck && nowTimeNum < maxCheck)    {
             timeCheck = true;
         }
        }
        minCheck = 1659; //최소시간 5시
        maxCheck = 1731; //최소시간 5시 30분

        if(!timeCheck){
            if( nowTimeNum > minCheck && nowTimeNum < maxCheck)     {
                timeCheck = true;
            }
        }
        return timeCheck;
    }

$(document).ready(function(){
    //------------ start suggest binding ------------//
    //개발담당자 suggest
    SuggestUtil.bind('USER', 'tempsetPm2', 'setPm2');
    //개발담당부서 suggest
    SuggestUtil.bind('DEPARTMENT', 'devUserDept2', 'devUserDeptOid2');
    //부품 suggest
    SuggestUtil.bind('PARTNO', 'partNo2');
    //고객처 suggest
    SuggestUtil.bind('CUSTOMER', 'subcontractor2', '');
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'customerEvent2', '');
    
    //사용자 suggest
    SuggestUtil.bind('USER', 'fstChargeName', 'fstCharge');
    //부서 suggest
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    //Die no suggest
    SuggestUtil.bind('DIENO', 'dieNumber');
    //검토 Project No suggest
    SuggestUtil.bind('REVIEWPROJNO', 'pjtNo1');
    //제품 Project No suggest
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo2');
    //차종 suggest
    SuggestUtil.bind('CARTYPE', 'carTypeText', 'carType');
    //개발산출물 문서 분류 suggest
    SuggestUtil.bind('PROJECTDOCTYPE', 'projectDocTypeTxt', 'projectDocType');
    //기술 문서 분류 suggest
    SuggestUtil.bind('TECHDOCTYPE', 'techDocTypeTxt', 'techDocType');
    //고객사 suggest
    SuggestUtil.bind('CUSTOMER', 'oemTypeTxt', 'oemType');
    //부품 suggest
    SuggestUtil.bind('PARTNO', 'partNumber');
    //제품분류 suggest
    SuggestUtil.bind('PRODUCTTYPE', 'productTypeText', 'productType');
    //ECO no suggest
    SuggestUtil.bind('ECONO', 'ecoNumber', 'ecoOid');
    //ECR no suggest
    SuggestUtil.bind('ECRNO', 'ecrNumber', 'ecrOid');
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'lastUsingBuyerTxt', 'lastUsingBuyer');
    //------------ end suggest binding ------------//
});

function regReviewPrjPopup() {
    //if ( oid=="" ) return;
    //openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1050,800);
	openWindow('/plm/jsp/common/loading.jsp?url=/plm/jsp/project/CreateReviewProject.jsp', '',860,1000);
}

function regProductPrjPopup() {
    openWindow('/plm/jsp/common/loading.jsp?url=/plm/jsp/project/CreateProductProject.jsp', '',800,1000);
}

//-->
</script>
</head>
<%-- <body onLoad="javascript:displayChange('<%=radioValue%>');"> --%>
<body onLoad="javascript:displayChange('<%=radioValue%>');" bgcolor="white" text="black" link="blue" vlink="purple" alink="red" bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="ProjectSearch" method="post">
<input type="hidden" name="command" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<input type="hidden" name="radioValue" value="<%=radioValue %>" />
<input type="hidden" name="sap" />
<!-- <table style="width: 780px; height: 100%;"> -->
<table style="width: 95%; height: 40; align:center; border:0" >
<tr>
    <td valign="top">
        <!-- [START] title & position -->
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%; height: 28px;">
                <tr>
                    <td style="width: 20px;"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03059") %><%--프로젝트 검색--%>
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
        <table style="width: 100%;" >
        <tr>
            <td>
                <table>
                <tr>
                    <td>
                        <%
                        for ( int i=0; i<projectTypeNumCode.size(); i++ ) {
                            String tpCd = (String)projectTypeNumCode.get(i).get("code");
                            if("3".equals(tpCd)) continue;
                        %>
                        <input name="radio" type="radio" class="Checkbox" value="<%=projectTypeNumCode.get(i).get("code") %>" <%if ( radioValue.equals( projectTypeNumCode.get(i).get("code") ) ){%>checked<%}%> onclick="javascript:onPage('<%=projectTypeNumCode.get(i).get("code")%>')"><%=projectTypeNumCode.get(i).get("value")%>&nbsp;
                        <%
                        }
                        %>
                    </td>
                </tr>
                </table>
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
                                <a href="javascript:regReviewPrjPopup();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00723") %><%--검토 프로젝트 등록--%></a>
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
                                <a href="javascript:regProductPrjPopup();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02549") %><%--제품 프로젝트 등록--%></a>
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
                                <a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
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
        <table style="width: 100%;">
        <tr>
            <td class="tab_btm2"></td>
        </tr>
        </table>
        <%
        if ( radioValue.equals("1") ) {
        %>
        <!-- ####################  #################### ####################   검토  검색 #################### ####################-->
        <table style="width: 100%;" id="reviewSearchDisplay" style="display:none">
        <tr>
            <td class="tdblueL"  style="width: 110px;">Project No</td>
            <td class="tdwhiteL" style="width: 160px;" >
                <input type="text" id="pjtNo1" name="pjtNo1" class="txt_field" style="width: 156px;" value="<%=searchCondition.getString("pjtNo1") %>">
            </td>
            <td class="tdblueL"  style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="dType1" name="dType1" class="fm_jmp" style="width: 160px;" onchange="javascript:dsChange(this);" multiple="multiple">
                <%
                for ( int i=0; i<divisionNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionNumCode.get(i).get("code") %>" <%=divisionNumCode.get(i).get("code").equals("2") && isType0 ? " selected" : "" %> <%=divisionNumCode.get(i).get("code").equals("1") && !isType0 ? " selected" : "" %>><%=divisionNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00585") %><%--개발 담당자--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input style="width:110px;" type="text" id="tempsetPm1" name="tempsetPm1" class="txt_fieldRO" readonly value="<%=searchCondition.getString("tempsetPm1") %>">
                <input type="hidden" id="setPm1" name="setPm1" value="<%=searchCondition.getString("setPm1") %>">
                <a href="javascript:addUser('setPm1');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('setPm1');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="pjtState1" name="pjtState1" class="fm_jmp" style="width: 160px;" multiple="multiple">
                    <%
                    states = ProjectHelper.getSearchState(ProjectHelper.REVIEWPROJECT_STATE);
                    for ( int i = 0; i < states.size(); i++ ) {
                        State lcState = (State)states.get(i);
                        String key = lcState.toString();

                        String display = lcState.getDisplay(messageService.getLocale());

                        if ( key.equals("UNDERREVIEW") ) {
                               display = messageService.getString("e3ps.message.ket_message", "00786")/*결재중*/;
                        }
                    %>
                    <option value="<%=key%>" <%=pjtStateCondList.contains( key ) ? " selected" : "" %>><%=display%> </option>
                    <%
                    }
                    %>
                    <option value="delay" <%=pjtStateCondList.contains( "delay" ) ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></option>
                </select>
            </td>
            <td class="tdblueL"  style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="productType1" name="productType1" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<productTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=productTypeNumCode.get(i).get("code") %>" <%=productTypeCondList.contains( productTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=productTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <select id="developType1" name="developType1" class="fm_jmp" style="width: 160px;"  multiple="multiple">
                <%
                for ( int i=0; i<developTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=developTypeNumCode.get(i).get("code") %>" <%=developTypeCondList.contains( developTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=developTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;">Project Name</td>
            <td class="tdwhiteL" colspan="3" >
                <input type="text" id="pjtName1" name="pjtName1" class="txt_field"  style="width:360px" value="<%=searchCondition.getString("pjtName1") %>" />
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input style="width:110px;" type="text" id="devUserDept1" name="devUserDept1" class="txt_fieldRO" readonly value="<%=searchCondition.getString("devUserDept1") %>">
                <input type=hidden id="devUserDeptOid1" name="devUserDeptOid1" value="<%=searchCondition.getString("devUserDeptOid1") %>">
                <a href="javascript:addDepartment('devUserDept1');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment('devUserDeptOid1','devUserDept1');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;">
                <select id="developDateType1" name="developDateType1" class="fm_jmp" style="width: 90px;" multiple="multiple">
                <%
                for ( int i=0; i<developDateTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=developDateTypeNumCode.get(i).get("code") %>" <%=developDateTypeCondList.contains( developDateTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=developDateTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdwhiteL" colspan="3">
                <input id="planStartStartDate1" name="planStartStartDate1" value="<%=searchCondition.getString("planStartStartDate1") %>" class="txt_field" style="width: 80px;" maxlength="10" />
                <a href="#" onclick="javascript:showCal(planStartStartDate1);"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planStartStartDate1')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;~&nbsp;
                <input id="planStartEndDate1" name="planStartEndDate1" value="<%=searchCondition.getString("planStartEndDate1") %>" class="txt_field" style="width: 80px;" maxlength="10" />
                <a href="#" onclick="javascript:showCal(planStartEndDate1);"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planStartEndDate1')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;&nbsp;(yyyy-mm-dd)
            </td>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02640") %><%--조립구분--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <select id="assembledType1" name="assembledType1" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<assembledTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=assembledTypeNumCode.get(i).get("code") %>" <%=assembledTypeCondList.contains( assembledTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=assembledTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="customerEvent1" name="customerEvent1" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<customerEventNumCode.size(); i++ ) {
                %>
                <option value="<%=customerEventNumCode.get(i).get("ida2a2") %>" <%=customerEventCondList.contains( customerEventNumCode.get(i).get("ida2a2") ) ? " selected" : "" %>><%=customerEventNumCode.get(i).get("value")%></option>
                <%
                }
                %>
               </select>
            </td>
            <td class="tdblueL"   style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
            <td class="tdwhiteL" >
                <input type="text" id="subcontractor1" name="subcontractor1" class="txt_field"  style="width: 156px;" value="<%=searchCondition.getString("subcontractor1") %>">
            </td>
            <td class="tdblueL"  style="width: 110px;">Rank</td>
            <td class="tdwhiteL0" style="width: 160px;">
                <select id="rank1" name="rank1" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<rankNumCode.size(); i++ ) {
                %>
                <option value="<%=rankNumCode.get(i).get("code") %>" <%=rankCondList.contains( rankNumCode.get(i).get("code") ) ? " selected" : "" %>><%=rankNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        </table>
        <%
        }
        else if ( radioValue.equals("2") ) {
        %>
        <!-- ####################  #################### ####################   제품 검색  #################### ####################-->
        <table style="width: 100%;" id="productSearchDisplay" style="display:none">
        <tr>
            <td class="tdblueL"  style="width: 110px;">Project No</td>
            <td class="tdwhiteL" style="width: 160px;">
                <!--  input type="text" id="pjtNo2" name="pjtNo2" class="txt_field"  style="width: 156px;" value=""-->
                <input type="text" id="pjtNo2" name="pjtNo2" class="txt_field" style="width:110px" value="<%=searchCondition.getString("pjtNo2") %>">
                <a href="javascript:selectPartNo('pjtNo2');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('pjtNo2');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"  style="width: 80px;">Project Name</td>
            <td class="tdwhiteL" style="width: 160px;" colspan="3">
                <input type="text" id="pjtName2" name="pjtName2" class="txt_field" style="width: 506px;" value="<%=searchCondition.getString("pjtName2") %>">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00585") %><%--개발 담당자--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <input style="width: 110px;" type="text" id="tempsetPm2" name="tempsetPm2" class="txt_field" value="<%=searchCondition.getString("tempsetPm2") %>">
                <input type="hidden" id="setPm2" name="setPm2" value="<%=searchCondition.getString("setPm2") %>">
                <a href="javascript:addUser('setPm2');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('setPm2');"><img src="/plm/portal/images/icon_delete.gif"></a>
                
            </td>
            <td class="tdblueL"  style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <input style="width: 110px;" type="text" id="devUserDept2" name="devUserDept2" value="<%=searchCondition.getString("devUserDept2") %>" class="txt_field">
                <input type=hidden id="devUserDeptOid2" name="devUserDeptOid2" value="<%=searchCondition.getString("devUserDeptOid2") %>">
                <a href="javascript:addDepartment('devUserDept2');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment('devUserDeptOid2','devUserDept2');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <select id="dType2" name="dType2" class="fm_jmp" style="width: 160px;" onchange="javascript:dsChange(this);" multiple="multiple">
                <%
                for ( int i=0; i<divisionNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionNumCode.get(i).get("code") %>" <%=divisionNumCode.get(i).get("code").equals("2") && isType0 ? " selected" : "" %> <%=divisionNumCode.get(i).get("code").equals("1") && !isType0 ? " selected" : "" %>><%=divisionNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="developType2" name="developType2" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<developTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=developTypeNumCode.get(i).get("code") %>" <%=developTypeCondList.contains( developTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=developTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"  style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
            <td class="tdwhiteL0" style="width: 160px;" colspan="3">
                <select id="productType2" name="productType2" class="fm_jmp" style="width: 200px;" multiple="multiple" >
                <%
                for ( int i=0; i<productTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=productTypeNumCode.get(i).get("code") %>" <%=productTypeCondList.contains( productTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=productTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
                <select id="productTypeLevel2" name="productTypeLevel2" class="fm_jmp" style="width: 193px;" multiple="multiple">
                <%
                for ( int i=0; i<productTypeLevel2NumCode.size(); i++ ) {
                %>
                <option value="<%=productTypeLevel2NumCode.get(i).get("code") %>" <%=productTypeLevel2CondList.contains( productTypeLevel2NumCode.get(i).get("code") ) ? " selected" : "" %>><%=productTypeLevel2NumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;">Part No</td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="partNo2" name="partNo2" class="txt_field" style="width:110px" value="<%=searchCondition.getString("partNo2") %>">
                <a href="javascript:selectPartNo('partNo2');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('partNo2');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"  style="width: 80px;">
                <span id="modleLabel" ><%if( !isType0 ){ %><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%} %></span>
            </td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="carTypeInfo2" name="carTypeInfo2" class="txt_field" style="width: 156px;" value="<%=searchCondition.getString("carTypeInfo2") %>">
            </td>
            <td class="tdblueL"   style="width: 110px;">Rank</td>
            <td class="tdwhiteL0" style="width: 160px;">
                <select id="rank2" name="rank2" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<rankNumCode.size(); i++ ) {
                %>
                <option value="<%=rankNumCode.get(i).get("code") %>" <%=rankCondList.contains( rankNumCode.get(i).get("code") ) ? " selected" : "" %>><%=rankNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="pjtState2" name="pjtState2" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                states = ProjectHelper.getSearchState(ProjectHelper.PRODUCTPROJECT_STATE);
                for ( int i = 0; i < states.size(); i++ ) {
                    State lcState = (State)states.get(i);
                    String key = lcState.toString();

                    String display = lcState.getDisplay(messageService.getLocale());

                    if ( key.equals("UNDERREVIEW") ) {
                        display = messageService.getString("e3ps.message.ket_message", "00786")/*결재중*/;
                    }
                %>
                <option value="<%=key%>" <%=pjtStateCondList.contains( key ) ? " selected" : "" %>><%=display%> </option>
                <%}%>
                <option value="delay" <%=pjtStateCondList.contains( "delay" ) ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></option>
                </select>
            </td>
            <td class="tdblueL"  style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%>
            </td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="subcontractor2" name="subcontractor2" class="txt_field" style="width: 156px;" value="<%=searchCondition.getString("subcontractor2") %>">
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <select id="customerEvent2" name="customerEvent2" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<customerEventNumCode.size(); i++ ) {
                %>
                <option value="<%=customerEventNumCode.get(i).get("ida2a2") %>" <%=customerEventCondList.contains( customerEventNumCode.get(i).get("ida2a2") ) ? " selected" : "" %>><%=customerEventNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;">
                <select id="developDateType2" name="developDateType2" class="fm_jmp" style="width: 90px;" multiple="multiple">
                <%
                for ( int i=0; i<developDateTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=developDateTypeNumCode.get(i).get("code") %>" <%=developDateTypeCondList.contains( developDateTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=developDateTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdwhiteL" colspan="5">
                <input id="planStartStartDate2" name="planStartStartDate2" value="<%=searchCondition.getString("planStartStartDate2") %>" class="txt_field" style="width: 80px;" maxlength="10"/>
                <a href="#" onclick="javascript:showCal(planStartStartDate2);"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planStartStartDate2')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;~&nbsp;
                <input id="planStartEndDate2" name="planStartEndDate2" value="<%=searchCondition.getString("planStartEndDate2") %>" class="txt_field" style="width: 80px;" maxlength="10"/>
                <a href="#" onclick="javascript:showCal(planStartEndDate2);"><img src="/plm/portal/images/icon_6.png"></a>
                <a href="JavaScript:clearDate('planStartEndDate2')"><img src="/plm/portal/images/icon_delete.gif"></a>&nbsp;&nbsp;(yyyy-mm-dd)
            </td>
        </tr>
        
        </table>
        
        <%
        }
        %>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:100%;HEIGHT:100%">
                    <%
                    if ( radioValue.equals("1") ) {
                    %>
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/ProjectSearchReviewGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Sapauth="<%=sapAuth %>"
                            Layout_Param_Sapcheck="<%=sapCheck %>"
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
                    <%
                    }
                    else if ( radioValue.equals("2") ) {
                    %>
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/ProjectSearchProdGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Sapauth="<%=sapAuth %>"
                            Layout_Param_Sapcheck="<%=sapCheck %>"
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

                    <%
                    }
                    %>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid Start -->
    </td>
</tr>
<tr>
    <td height="30" valign="bottom"><iframe src="/plm/portal/common/copyright.html" name="copyright" style="width: 780px; height:24px;" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
</tr>
</table>
</form>
</body>
</html>
