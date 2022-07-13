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
    String mode = request.getParameter("mode");
    String modal = request.getParameter("modal");
    String fncall = request.getParameter("fncall");
    
    if ((mode == null) || (mode.trim().length() == 0)) {
        mode = "one";
    }
    
    if ((modal == null) || (modal.trim().length() == 0)) {
        modal = "N";
    }

    if ( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End
    
    String radioValue = "";
    String status = request.getParameter("status");
    String type = request.getParameter("type");
    String projectType = request.getParameter("project_type");
    if(projectType == null && searchCondition.get("project_type") == null){
        if (type != null && type.equals("P")) {
    	    radioValue = "2";
        } else if (type != null && type.equals("M")) {
    	    radioValue = "3";
        } else if (type != null && type.equals("D")) {
    	    radioValue = "1";
        }else{
    	    radioValue = "2";      
        }
    }else{
	   radioValue = projectType;
    }
    
    

    // Cookie 생성
    //WebUtil.setCookie(response, "radioValue", radioValue);

    boolean isType0 = CommonUtil.isMember("전자사업부");
    boolean isbizAdmin = CommonUtil.isMember("Business Administrators");
    boolean isPMO = CommonUtil.isMember("자동차PMO") || CommonUtil.isMember("전자PMO");

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
    
    String paramRadio = StringUtil.checkNull(request.getParameter("paramRadio"));
    String pjtState = StringUtil.checkNull(request.getParameter("pjtState"));
    String pjtStateValue = StringUtil.checkNull(request.getParameter("pjtStateValue"));
    
    if("1".equals(paramRadio)){
	   parameter.put("name", "검토");
    }else if("2".equals(paramRadio)){
	   parameter.put("name", "개발");
	}else if("3".equals(paramRadio)){
	   parameter.put("name", "금형");
	}
    
    List<Map<String, Object>> projectTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 개발구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVELOPENTTYPE");
    List<Map<String, Object>> developTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // Rank
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    if ( radioValue.equals("3") ) {
        parameter.put("codeType", "RANK");
        parameter.put("code",     "RAN3000");
    }
    else {
        if ( isType0 ) {
            parameter.put("codeType", "RANK");
            parameter.put("code",     "RAN2000");
        }
        else {
            parameter.put("codeType", "RANK");
            parameter.put("code",     "RAN1000");
        }
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
    List<Map<String, Object>> designTypeNumCode        = new ArrayList<Map<String, Object>>();
    if ( radioValue.equals("3") ) {
        // 금형구분
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "DIETYPE");
        moldItemTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        if( searchCondition != null && searchCondition.get("itemType3") != null ) {
            parameter.put("codeType", "MOLDPRODUCTSTYPE");
            if ( searchCondition.get("itemType3").equals("Mold") ) {
                parameter.put("code", "MOL100");
            }
            else if ( searchCondition.get("itemType3").equals("Press") ) {
                parameter.put("code", "MOL200");
            }
        }
        else {
            parameter.put("codeType",   "MOLDPRODUCTSTYPE");
            parameter.put("depthLevel", "child");
            parameter.put("exclude",    "MOL300");
        }
        // 금형분류
        moldProductTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

        // 금형유형
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "MOLDTYPE");
        moldTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

        // 제작구분
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "MAKINGTYPE");
        makingTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    }
    else {
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
        
        //설계구분
        parameter.clear();
        parameter.put("locale",   messageService.getLocale());
        parameter.put("codeType", "DESIGNTYPE");
        designTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
        
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

        cal.add(Calendar.MONTH, -3);
        searchCondition.put("planStartStartDate1", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        searchCondition.put("planStartStartDate2", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        searchCondition.put("planStartStartDate3", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));

        developDateTypeCondList.add(0, "DEVELOPDATESTART");
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>PLM Portal</title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<!-- callServer function 사용시 include -->
<%@include file="/jsp/project/template/ajaxProgress.html"%>

<%@include file="/jsp/common/context.jsp" %>
<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/common/multicombo.jsp" %>
<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
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
    var publicUserId = "";
    
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
    	if($('[name=modal]').val() == 'N'){
            location.href="/plm/jsp/project/SearchPjtPop.jsp?changeRadio=Y&project_type="+page+"&modal=N&fncall="+'<%=fncall%>';
    	}else{
    		$('[name=project_type]').val(page);
    		document.ProjectSearch.action = "/plm/jsp/project/SearchPjtPop.jsp";
    		document.ProjectSearch.target = 'selfWin';
    		window.name = 'selfWin';
    		document.ProjectSearch.submit();
    	}
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
            $('form')[0].reset();
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
            $("#devUserDept1").val("");
            $("#devUserDeptOid1").val("");
            $("#pjtName2").val("");
            $("#developType2").multiselect("uncheckAll");
            $("#developDateType2").multiselect("uncheckAll");
            $("#planStartStartDate2").val("");
            $("#planStartEndDate2").val("");
            $("#assembledType2").multiselect("uncheckAll");
            $("#rank2").multiselect("uncheckAll");
            $("#subcontractor2").val("");
            $("#carTypeInfo2").val("");
            $('form')[0].reset();
            // 사업부변경시 functioin 호출
            dsChange($("#dType2 option:selected").val());
            // 제품구분변경시 function 호출
            changeProductType();
        }
        else if ( "<%=radioValue %>" == "3" ) {
            $("#dieNo3").val("");
            $("#pjtNo3").val("");
            $("#partNo3").val("");
            $("#developType3").multiselect("uncheckAll");
            $("#partName3").val("");
            $("#carTypeInfo3").val("");
            $("#itemType3").multiselect("uncheckAll");
            $("#moldProductType3").multiselect("uncheckAll");
            $("#moldType3").multiselect("uncheckAll");
            $("#rank3").multiselect("uncheckAll");
            $("#pjtState3").multiselect("uncheckAll");
            $("#tempsetMoldPm").val("");
            $("#setMoldPm").val("");
            $("#making3").multiselect("uncheckAll");
            $("#outsourcing3").val("");
            $("#devUserDept3").val("");
            $("#devUserDeptOid3").val("");
            $("#developDateType3").multiselect("uncheckAll");
            $("#planStartStartDate3").val("");
            $("#planStartEndDate3").val("");
            $("#tempsetPm3").val("");
            $("#setPm3").val("");
            $('form')[0].reset();
            // 금형분류변경시 function 호출
            itemTypeChagne();
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
        if(Grids[0].LoadedCount == 0){
            return;
        }
        showProcessing();
        document.ProjectSearch.command.value = "excelDownProject";
        $.fileDownload('/plm/servlet/e3ps/ProjectServlet?'+$('[name=ProjectSearch]').serialize(),{
             successCallback : function(){
                 hideProcessing();   
             },
             failCallback: function(responseHtml, url) {
                 hideProcessing();
             }
        });
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
    	publicUserId = targetId;
        SearchUtil.selectOneUser('','','acceptUser');
    }

    function acceptUser(arrObj) {
    	var targetId = publicUserId;
    	var isAppend = "Y";
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
    var deptTargetId = "";
    
    function deptCallBack(attacheDept){

        var Code="devUserDeptOid";
        var Name="devUserDept";
        
        if ( deptTargetId == "devUserDept1" ) {
            Code = Code + "1";
            Name = Name + "1";
        }else if( deptTargetId == "devUserDept2" ){
            Code = Code + "2";
            Name = Name + "2";                   
        }else if ( deptTargetId == "devUserDept3" ) {
            Code = Code + "3";
            Name = Name + "3";
        }
        
        var departOid = "";
        var departName = "";
        
        for(var i= 0 ; i < attacheDept.length; i++){
            if(i == attacheDept.length-1){
                departOid += attacheDept[i].OID;
                departName += attacheDept[i].NAME;
            }else{
                departOid += attacheDept[i].OID + ", ";
                departName += attacheDept[i].NAME+ ", ";    
            }
            
        }
        deptMerge(departOid, departName, Code, Name);
    }
    
    function addDepartment(targetId) {
        deptTargetId = targetId;
        var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?type=all&mode=m&invokeMethod=deptCallBack";
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, 430, 465, opts);
        
    }

    function deptMerge(deptOid, deptName, targetId, targetName) {
        if ( $("#"+targetId).val() == "" ) {
            $("#"+targetId).val( deptOid );
            $("#"+targetName).val( deptName );
        }
        else {
            var deptOidArr = $.merge(deptOid.split(", "), $("#"+targetId).val().split(", "));
            deptOidArr = $.unique(deptOidArr.sort());
            
            var deptNameArr = $.merge(deptName.split(", "), $("#"+targetName).val().split(", "));
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
    	
    	 //검토 Project No suggest
        SuggestUtil.bind('REVIEWPROJNO', 'pjtNo1', null, 'pjtName1');
        //제품 Project No suggest
        //SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo2', null, 'pjtName2');
        SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo2');
        //제품 Project No suggest
        SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo3');            
        //PM suggest
        SuggestUtil.bind('USER', 'tempsetPm1', 'setPm1');
        SuggestUtil.bind('USER', 'tempsetPm2', 'setPm2');
        SuggestUtil.bind('USER', 'tempsetPm3', 'setPm3');
        SuggestUtil.bind('USER', 'tempsetMoldPm', 'setMoldPm');
        //개발담당부서 suggest
        SuggestUtil.bind('DEPARTMENT', 'devUserDept1', 'devUserDeptOid1');
        SuggestUtil.bind('DEPARTMENT', 'devUserDept2', 'devUserDeptOid2');
        SuggestUtil.bind('DEPARTMENT', 'devUserDept3', 'devUserDeptOid3');
        //고객처(사) suggest
        SuggestUtil.bind('CUSTOMER', 'subcontractor2', null);
        //차종 suggest
        SuggestUtil.bind('CARTYPE', 'carTypeInfo2');
        //부품 suggest
        SuggestUtil.bind('PARTNO', 'partNo2');
        //부품 suggest
        SuggestUtil.bind('PARTNO', 'partNo3');
        //Die no suggest
        SuggestUtil.bind('DIENO', 'dieNo3');
        //최종사용처 suggest
        //SuggestUtil.bind('CUSTOMEREVENT', 'customerEvent1Txt', 'customerEvent1');
        //최종사용처 suggest
        //SuggestUtil.bind('CUSTOMEREVENT', 'customerEvent2Txt', 'customerEvent2');
    	
    	
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
        
        // 제작구분
        $("#designType").multiselect({
            minWidth: 110,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        
        CalendarUtil.dateInputFormat('planStartStartDate1','planStartEndDate1'); //기한 설정시 start와 end field를 설정한다.
        CalendarUtil.dateInputFormat('planStartStartDate2','planStartEndDate2'); //기한 설정시 start와 end field를 설정한다.
        CalendarUtil.dateInputFormat('planStartStartDate3','planStartEndDate3'); //기한 설정시 start와 end field를 설정한다.
        
        var pjtState = '<%=pjtState%>';
        var pjtStateValue = '<%=pjtStateValue%>';
        if(pjtState != '' && pjtStateValue != ''){
        	$("#"+pjtState).val(pjtStateValue);
            $("#"+pjtState).multiselect('refresh');
            $("select[name="+pjtState+"]").siblings().attr('disabled', 'disabled');	
        }
        
        var gridId = "";
        
        var pjtType = '<%=radioValue%>';
        
        if(pjtType == '1'){
            gridId = "#ProjectSearchReviewGrid";
        }
        
        if(pjtType == '2' || pjtType == '4'){
            gridId = "#ProjectSearchProdGrid";
        }
        
        if(pjtType == '3'){
            gridId = "#ProjectSearchMoldGrid";
        }
        
        treeGridResize(gridId,"#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        
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
    function regReviewPrjPopup() {
        openWindow('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/CreateReviewProject.jsp', '',800,600);
    }

    function regProductPrjPopup() {
        openWindow('/plm/jsp/common/loading2.jsp?url=/plm/jsp/project/CreateProductProject.jsp', '',800,600);
    }
    
    function selectPartCategory(checkedNode){
        var nodeOIdStr='', nodeNameStr='';
        for(var i=0; i < checkedNode.length; i++){
            if(i == checkedNode.length - 1){
                nodeOIdStr += checkedNode[i].id;
                nodeNameStr += checkedNode[i].name;
            }else{
                nodeOIdStr += checkedNode[i].id+','; 
                nodeNameStr += checkedNode[i].name+',';
            }
        }
        //초기화
        $('[name=productTypeLevel2]').val('');
        $('[name=productTypeLevel2Name]').val('');
        //재맵핑
        $('[name=productTypeLevel2]').val(nodeOIdStr);
        $('[name=productTypeLevel2Name]').val(nodeNameStr);
    }
    
    function selectLastUsingBuyer1(returnValue){
        $('[name=customerEvent1Txt]').val('');
        $('[name=customerEvent1]').val('');
        var customerEvent1Txt = '';
        var customerEvent1 = '';        
        for(var i=0; i<returnValue.length;i++){
            if(i != returnValue.length-1){
                customerEvent1Txt += returnValue[i][2]+',';         
                customerEvent1 += returnValue[i][0]+',';            
            }else{
                customerEvent1Txt += returnValue[i][2];         
                customerEvent1 += returnValue[i][0];            
            }
        }
        $('[name=customerEvent1Txt]').val(customerEvent1Txt);           
        $('[name=customerEvent1]').val(customerEvent1);             
    }
    
    function selectLastUsingBuyer2(returnValue){
        $('[name=customerEvent2Txt]').val('');
        $('[name=customerEvent2]').val('');
        var customerEvent2Txt = '';
        var customerEvent2 = '';        
        for(var i=0; i<returnValue.length;i++){
            if(i != returnValue.length-1){
                customerEvent2Txt += returnValue[i][2]+',';         
                customerEvent2 += getLonginOid(returnValue[i][0])+',';          
            }else{
                customerEvent2Txt += returnValue[i][2];         
                customerEvent2 += getLonginOid(returnValue[i][0]);          
            }
        }
        $('[name=customerEvent2Txt]').val(customerEvent2Txt);           
        $('[name=customerEvent2]').val(customerEvent2);             
    }
    
    function getLonginOid(fullOid){
        if(!fullOid || fullOid == 'undefined'){
            return '';
        }
        var oids = fullOid.split(':');
        return oids[1];
    }
    
    // 확인
    function confirm(isCheckData) {
        var G = Grids[0];
          var arr = new Array();
          var subArr = new Array();
          var idx = 0;

          if( G != null ){

              var R = G.GetSelRows();

              if( R.length == 0 ){
                  alert('<%= messageService.getString("e3ps.message.ket_message", "03111") %>');<%--프로젝트를 선택해 주십시오--%>
                  return;
              }

              for ( var i = 0; i < R.length; i++) {
                  subArr = new Array();
                  subArr[0] = R[i].PjtOid; //project oid
                  subArr[1] = R[i].PjtNo; //project_no
                  subArr[2] = R[i].PjtName; //project name
                  subArr[3] = DateToString(R[i].PlanStartDate,'yyyy-M-dd'); //plan_start_date
                  subArr[4] = DateToString(R[i].PlanEndDate,'yyyy-M-dd'); //plan_end_date
                  subArr[5] = R[i].Statestate; //State
                  subArr[6] = R[i].Domain; //domain
                  subArr[7] = R[i].Maker; //maker
                  subArr[8] = R[i].Category; //category
                  subArr[9] = R[i].Rank; //rank
                  subArr[10] = R[i].Pm; //pm
                  subArr[14] = ""; // task_name 들어갈 배열순서
                  subArr[15] = R[i].customerCode;
                  subArr[16] = R[i].customerName;
                  subArr[17] = R[i].reviewResult;
                  
                  // ECM에서 프로젝트 선택시 물고와야 하는 정보에 대한 처리
                  subArr[11] = null;  // 개발단계 = 단계구분
                  subArr[12] = null;  // 대표 차종
                  subArr[13] = null;  // 제품정보 

                  try {
                      // 제품정보를 위한 선처리
                      var arrParts = null;
                      // 프로젝트 상태
                      var projectState = "";
                      
                      var projectInfos4ECM = R[i].projectInfos4ECM;
                      var arrPInfos = projectInfos4ECM.split("↕");
                      var arrPInfosLength = (arrPInfos != null) ? arrPInfos.length : 0;
                      for(var l=0; l < arrPInfosLength; l++) {
                          
                          var arrPartInfo13 = null;
                          
                          var arrP = arrPInfos[l].split("↔");
                          var arrPLength = (arrP != null) ? arrP.length : 0;
                     
                          // 프로젝트 상태
                          if(l == 0) { 
                              projectState = arrP[0];
                         
                          }
                          // 개발단계 = 단계구분
                          else if(l == 1 && arrPLength == 3) { 
                              var devYnOid = arrP[0];
                              var devYnName = arrP[1];
                              var devYnCode = arrP[2];
                              
                              subArr[11] = [devYnOid, devYnName, devYnCode];
                              //alert('subArr[11] is '+ subArr[11]);
                          } 
                          // 대표 차종
                          else if(l == 2 && arrPLength == 3) {
                              var carTypeOid = arrP[0];
                              var carTypeName = arrP[1];
                              var carTypeCode = arrP[2];
                              
                              subArr[12] = [carTypeOid, carTypeName, carTypeCode];
                              //alert('subArr[12] is '+ subArr[12]);
                          } 
                          // 제품정보 
                          else if(l >= 3 && arrPLength == 4) {
                              var relPartOid = arrP[0];
                              var pNum = arrP[1];
                              var pName = arrP[2];
                              var relPartVersion = arrP[3];
                              
                              arrPartInfo13 = [relPartOid, pNum, pName, relPartVersion];
                              //alert('arrPartInfo13 is '+ arrPartInfo13);
                          }
                      
                          
                          if(arrPartInfo13 != null) {
                              if(arrParts == null) arrParts = new Array();
                              arrParts[arrParts.length] = arrPartInfo13;
                              //alert('arrParts['+ (arrParts.length - 1) +'] is '+ arrParts[ (arrParts.length - 1) ]);
                          }
                          
                      }
                      
                      // 제품정보를 위한 후처리
                      subArr[13] = arrParts;
                      //alert(subArr[13]);
                      
                  } catch(e) { alert(e); }
                  
                  arr[idx++] = subArr;
              }
          }
          if(isCheckData){
        	  var data = '';
        	  for(var i in arr){
            	  data += 'subArr['+i+'][0] ='+ arr[i][0]+"\n"; //project oid
            	  data += 'subArr['+i+'][1] ='+ arr[i][1]+"\n"; //project_no
            	  data += 'subArr['+i+'][2] ='+ arr[i][2]+"\n"; //project name
            	  data += 'subArr['+i+'][3] ='+ arr[i][3]+"\n"; //plan_start_date
            	  data += 'subArr['+i+'][4] ='+ arr[i][4]+"\n"; //plan_end_date
            	  data += 'subArr['+i+'][5] ='+ arr[i][5]+"\n"; //State
            	  data += 'subArr['+i+'][6] ='+ arr[i][6]+"\n"; //domain
            	  data += 'subArr['+i+'][7] ='+ arr[i][7]+"\n"; //maker
            	  data += 'subArr['+i+'][8] ='+ arr[i][8]+"\n"; //category
            	  data += 'subArr['+i+'][9] ='+ arr[i][9]+"\n"; //rank
            	  data += 'subArr['+i+'][10] ='+ arr[i][10]+"\n"; //pm
            	  data += 'subArr['+i+'][14] ='+ arr[i][14]+"\n"; // task_name 들어갈 배열순서
            	  data += 'subArr['+i+'][15] ='+ arr[i][15]+"\n";
            	  data += 'subArr['+i+'][16] ='+ arr[i][16]+"\n";
            	  data += 'subArr['+i+'][11] ='+ arr[i][11]+"\n";  // 개발단계 = 단계구분
            	  data += 'subArr['+i+'][12] ='+ arr[i][12]+"\n";  // 대표 차종
            	  data += 'subArr['+i+'][13] ='+ arr[i][13]+"\n";  // 제품정보 
        	  }
        	  alert(data);
        	  return;
          }
          if (document.forms[0].modal.value == 'N') {
              try{
            	  var fncall = document.forms[0].fncall.value;
            	  if(opener && opener.eval(fncall)){
            		  opener.eval(fncall)(arr);
            	  }else{
            		  opener.setProject(arr);
                      /* if(!flag){
                          window.close();
                      }   */
            	  }
            	  
            	  
            	  opener.hideProcessing();
              }catch(e){
            	  opener.hideProcessing();
                  //opener.setProject(arr);
                  window.close();
              }
          } else {
              window.returnValue = arr;
              //window.close();
          }
          window.close();
      }

      function delAllConditions() {
    	  $('[name=ProjectSearch]')[0].reset();
      }
      
      $(window).unload( function () { 
    	    
    	     try {
    	           hideProcessing();
    	     } catch(e) {}
    	        
    	     try {
    	           opener.hideProcessing();
    	     } catch(e) {}
    	 
    	  }
      );
    
/*     $(document).ready(function(){
       
    }); */
//-->
</script>
</head>
<body onLoad="javascript:displayChange('<%=radioValue%>');">

<form name="ProjectSearch" method="post">
<input type="hidden" name="command" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<input type="hidden" name="radioValue" value="<%=radioValue %>" />
<input type="hidden" name="dev_flag">
<input type="hidden" name="status"> 
<input type="hidden" name="type"> 
<input type="hidden" name="div_type"> 
<input type="hidden" name="project_type" value="<%=projectType!=null?projectType:""%>"> 
<input type="hidden" name="mode" value="<%=mode%>"> 
<input type="hidden" name="modal" value="<%=modal%>">
<input type="hidden" name="fncall" value="<%=fncall%>">
<input type="hidden" name="searchPopup" value="true">
<!-- [START] title & position -->
<table style="width: 100%;">
    <tr>
        <td height="50" valign="top">
            <table style="width: 100%;">
                <tr>
                    <td background="/plm/portal/images/logo_popupbg.png">
                        <table style="height: 28px;">
                            <tr>
                                <td width="7"></td>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03059")%><%--프로젝트 검색--%></td>
                            </tr>
                        </table>
                    </td>
                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">
        <!-- [START] title & position -->
    <table style="width: 100%; height: 20px;">
    <tr>
        <tr>
            <td class="space10"></td>
        </tr>
            </td>
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
                                if("1".equals(projectTypeNumCode.get(i).get("code")) && (CommonUtil.isMember("KETS") || CommonUtil.isMember("KETS_PMO"))){
                                    out.println("");
                                }else{
                        %>
                        <input name="radio" type="radio" class="Checkbox" value="<%=projectTypeNumCode.get(i).get("code") %>" <%if ( radioValue.equals( projectTypeNumCode.get(i).get("code") ) ){%>checked<%}%> onclick="javascript:onPage('<%=projectTypeNumCode.get(i).get("code")%>')"><%=projectTypeNumCode.get(i).get("value")%>&nbsp;
                        <%
                                }
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
                        <!-- <td>
                            <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:confirm(true);" class="btn_blue">선택확인(개발자용)</a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td> -->
                        <td width="5">&nbsp;</td>
                        <td>
                            <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:delAllConditions();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td>
                            <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td align="center">
                            <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:confirm();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><%--확인--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                </tr>
                            </table>
                        </td>
                        <td width="5">&nbsp;</td>
                        <td align="center">
                            <table>
                                <tr>
                                    <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                    <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                        href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                    <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
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
        <%
        if ( radioValue.equals("1") ) {
        %>
        <!-- ####################  #################### ####################   검토  검색 #################### ####################-->
        <table style="width: 100%;" id="reviewSearchDisplay" style="display:none">
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "03104") %><%-- 프로젝트NO --%></td>
            <td class="tdwhiteL">
                <input type="text" id="pjtNo1" name=pjtNo1 class="txt_field" style="width: 98%;" value="<%=searchCondition.getString("pjtNo1") %>">
            </td>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%-- 프로젝트명 --%></td>
            <td class="tdwhiteL0" colspan="3" >
                <input type="text" id="pjtName1" name="pjtName1" class="txt_field" style="width: 98%;" value="<%=searchCondition.getString("pjtName1") %>">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00370") %><%-- PM --%></td>
            <td class="tdwhiteL">
                <input style="width:70%;" type="text" id="tempsetPm1" name="tempsetPm1" class="txt_field" value="<%=searchCondition.getString("tempsetPm1") %>">
                <input type="hidden" id="setPm1" name="setPm1" value="<%=searchCondition.getString("setPm1") %>">
                <a href="javascript:addUser('setPm1');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('setPm1');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
            <td class="tdwhiteL">
                <input style="width:70%;" type="text" id="devUserDept1" name="devUserDept1" class="txt_field" value="<%=searchCondition.getString("devUserDept1") %>">
                <input type=hidden id="devUserDeptOid1" name="devUserDeptOid1" value="<%=searchCondition.getString("devUserDeptOid1") %>">
                <a href="javascript:addDepartment('devUserDept1');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment('devUserDeptOid1','devUserDept1');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL0">
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
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
            <td class="tdwhiteL">
                <select id="developType1" name="developType1" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<developTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=developTypeNumCode.get(i).get("code") %>" <%=developTypeCondList.contains( developTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=developTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
            <td class="tdwhiteL">
                <input type="text" name="productTypeLevel2Name" class="txt_field" style="width: 70%">
                <input type="hidden" name="productTypeLevel2" value="">
                <a href="javascript:SearchUtil.selectPartClaz('selectPartCategory','onlyLeaf=Y&openAll=N&depthLevel2=Y');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('productTypeLevel2','productTypeLevel2Name');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02640") %><%--조립구분--%></td>
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
            <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL">
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
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
            <td class="tdwhiteL">
                <input type="text" id="subcontractor1" name="subcontractor1" class="txt_field" style="width: 98%;" value="<%=searchCondition.getString("subcontractor2") %>">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
            <td class="tdwhiteL0">
                <input type="text" name="customerEvent1Txt" class="txt_field" style="width: 70%">
                <input type="hidden" name="customerEvent1"><a href="javascript:SearchUtil.selectLastUsingBuyer('selectLastUsingBuyer1');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('lastUsingBuyerTxt','lastUsingBuyer');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
        </tr>
        </table>
        <%
        }else if ( radioValue.equals("2") ) {
        %>
        <!-- ####################  #################### ####################   제품 검색  #################### ####################-->
        <table style="width: 100%;" id="productSearchDisplay" style="display:none">
                <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "03104") %><%-- 프로젝트NO --%></td>
            <td class="tdwhiteL" >
                <input type="text" id="pjtNo2" name="pjtNo2" class="txt_field" style="width: 98%;" value="<%=searchCondition.getString("pjtNo2") %>">
            </td>
            <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%-- 프로젝트명 --%></td>
            <td class="tdwhiteL0" colspan="3" >
                <input type="text" id="pjtName2" name="pjtName2" class="txt_field" style="width: 98%;" value="<%=searchCondition.getString("pjtName2") %>">
            </td>
        </tr>
        <tr>
            <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00370") %><%-- PM --%></td>
            <td class="tdwhiteL">
                <input style="width:70%;" type="text" id="tempsetPm2" name="tempsetPm2" class="txt_field" value="<%=searchCondition.getString("tempsetPm2") %>">
                <input type="hidden" id="setPm2" name="setPm2" value="<%=searchCondition.getString("setPm2") %>">
                <a href="javascript:addUser('setPm2');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('setPm2');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
            <td class="tdwhiteL">
                <input style="width:70%;" type="text" id="devUserDept2" name="devUserDept2" class="txt_field" value="<%=searchCondition.getString("devUserDept2") %>">
                <input type=hidden id="devUserDeptOid2" name="devUserDeptOid2" value="<%=searchCondition.getString("devUserDeptOid1") %>">
                <a href="javascript:addDepartment('devUserDept2');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment('devUserDeptOid2','devUserDept2');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL0">
                <%if(CommonUtil.isMember("KETS") || CommonUtil.isMember("KETS_PMO")){ %>
                <input type="hidden" name="dType2" value="3">KETS
                <%}else{ %>
                <select id="dType2" name="dType2" class="fm_jmp" style="width: 160px;" onchange="javascript:dsChange(this);" multiple="multiple">
                <%
                for ( int i=0; i<divisionNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionNumCode.get(i).get("code") %>" <%=divisionNumCode.get(i).get("code").equals("2") && isType0 ? " selected" : "" %> <%=divisionNumCode.get(i).get("code").equals("1") && !isType0 ? " selected" : "" %>><%=divisionNumCode.get(i).get("value")%></option>
                <%
                }
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
            <td class="tdwhiteL">
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
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%></td>
            <td class="tdwhiteL">
                <input type="text" name="productTypeLevel2Name" class="txt_field" style="width: 70%">
                <input type="hidden" name="productTypeLevel2" value="">
                <a href="javascript:SearchUtil.selectPartClaz('selectPartCategory','onlyLeaf=Y&openAll=N&depthLevel2=Y');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('productTypeLevel2','productTypeLevel2Name');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL0">
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
        </tr>
         <tr>
            <td class="tdblueL">Part No</td>
            <td class="tdwhiteL">
                <input type="text" id="partNo2" name="partNo2" class="txt_field" style="width:70%" value="<%=searchCondition.getString("partNo2") %>">
                <a href="javascript:selectPartNo('partNo2');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('partNo2');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>       
            <td class="tdblueL"  >
                <span id="modleLabel" ><%if( !isType0 ){ %><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%><%}else{ %><%=messageService.getString("e3ps.message.ket_message", "02466") %><%--적용기기--%><%} %></span>
            </td>
            <td class="tdwhiteL" >
                <input type="text" id="carTypeInfo2" name="carTypeInfo2" class="txt_field" style="width: 98%;" value="<%=searchCondition.getString("carTypeInfo2") %>">
            </td>
            <td class="tdblueL" >Rank</td>
            <td class="tdwhiteL0" >
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
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
            <td class="tdwhiteL">
                <input type="text" name="customerEvent2Txt" class="txt_field" style="width: 70%">
                <input type="hidden" name="customerEvent2"><a href="javascript:SearchUtil.selectLastUsingBuyer('selectLastUsingBuyer2');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('lastUsingBuyerTxt','lastUsingBuyer');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00859") %><%--고객처--%></td>
            <td class="tdwhiteL">
                <input type="text" id="subcontractor2" name="subcontractor2" class="txt_field" style="width: 98%;" value="<%=searchCondition.getString("subcontractor2") %>">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01843") %><%--설계구분--%></td>
            <td class="tdwhiteL0">
                <select id="designType" name="designType" class="fm_jmp" style="width:160px">
                <option value=""><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></option>
                <%
                parameter.clear();
                parameter.put("locale",   messageService.getLocale());
                parameter.put("codeType", "DESIGNTYPE");
                designTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

                for ( int i=0; i<designTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=designTypeNumCode.get(i).get("code") %>"><%=designTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        </table>
        <%
        }
        else if ( radioValue.equals("3") ) {
        %>
        <!-- ####################  #################### ####################   금형 검색  #################### ####################-->
        <table style="width: 100%;" id="moldSearchDisplay" style="display:none">
        <tr>
            <td class="tdblueL"   style="width: 110px;">Die No</td>
            <td class="tdwhiteL"  style="width: 420px;" colspan="3">
                <input type="text" id="dieNo3" name="dieNo3" value="<%=searchCondition.getString("dieNo3") %>" class="txt_field" style="width: 340px;">
                <a href="javascript:selectPartNo('dieNo3');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('dieNo3');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"   style="width: 110px;">Project No</td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input type="text" id="pjtNo3" name="pjtNo3" value="<%=searchCondition.getString("pjtNo3") %>" class="txt_field" style="width: 156px;">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;">Part No</td>
            <td class="tdwhiteL" style="width: 420px;" colspan="3">
                <input type="text" id="partNo3" name="partNo3" value="<%=searchCondition.getString("partNo3") %>" class="txt_field" style="width: 340px;">
                <a href="javascript:selectPartNo('partNo3');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:clearPartNo('partNo3');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <select id="developType3" name="developType3" class="fm_jmp" style="width: 160px;" multiple="multiple">
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
            <td class="tdblueL"  style="width: 110px;">Part Name</td>
            <td class="tdwhiteL" colspan="3">
                <input type="text" id="partName3" name="partName3" value="<%=searchCondition.getString("partName3") %>" class="txt_field" style="width: 340px;" />
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01248") %><%--대표차종--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input type="text" id="carTypeInfo3" name="carTypeInfo3" value="<%=searchCondition.getString("carTypeInfo3") %>" class="txt_field"  style="width: 156px;">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01051") %><%--금형구분--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="itemType3" name="itemType3" class="fm_jmp" style="width: 160px;" onChange="javascript:itemTypeChagne();" multiple="multiple">
                <%
                for ( int i=0; i<moldItemTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=moldItemTypeNumCode.get(i).get("code") %>" <%=itemTypeCondList.contains( moldItemTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=moldItemTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"  style="width: 70px;"><%=messageService.getString("e3ps.message.ket_message", "01078") %><%--금형분류--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="moldProductType3" name="moldProductType3" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<moldProductTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=moldProductTypeNumCode.get(i).get("code") %>" <%=moldProductTypeCondList.contains( moldProductTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=moldProductTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01094") %><%--금형유형--%></td>
            <td class="tdwhiteL0">
                <select id="moldType3" name="moldType3" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<moldTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=moldTypeNumCode.get(i).get("code") %>" <%=moldTypeCondList.contains( moldTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=moldTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01010") %><%--금형 Rank--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="rank3" name="rank3" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<rankNumCode.size(); i++ ) {
                %>
                <option value="<%=rankNumCode.get(i).get("code") %>" <%=rankCondList.contains( rankNumCode.get(i).get("code") ) ? " selected" : "" %>><%=rankNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"  style="width: 70px;"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="pjtState3" name="pjtState3" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                states = ProjectHelper.getSearchState(ProjectHelper.MOLDPROJECT_STATE);
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
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "01056") %><%--금형담당(PM)--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input style="width:110px;" type="text" id="tempsetMoldPm" name="tempsetMoldPm" class="txt_field" value="<%=searchCondition.getString("tempsetMoldPm") %>">
                <input type="hidden" id="setMoldPm" name="setMoldPm" value="<%=searchCondition.getString("setMoldPm") %>">
                <a href="javascript:addUser('setMoldPm');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="javascript:delUser('setMoldPm');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"  style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "02532") %><%--제작구분--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <select id="making3" name="making3" class="fm_jmp" style="width: 160px;" multiple="multiple">
                <%
                for ( int i=0; i<makingTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=makingTypeNumCode.get(i).get("code") %>" <%=makingCondList.contains( makingTypeNumCode.get(i).get("code") ) ? " selected" : "" %>><%=makingTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"  style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "02533") %><%--제작처--%></td>
            <td class="tdwhiteL" style="width: 160px;">
                <input type="text" id="outsourcing3" name="outsourcing3" value="<%=searchCondition.getString("outsourcing3") %>" class="txt_field"  style="width: 156px;">
            </td>
            <td class="tdblueL"   style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00632") %><%--개발담당부서--%></td>
            <td class="tdwhiteL0" style="width: 160px;">
                <input style="width:110px;" type="text" id="devUserDept3" name="devUserDept3" class="txt_field" value="<%=searchCondition.getString("devUserDept3") %>">
                <input type="hidden" id="devUserDeptOid3" name="devUserDeptOid3" value="<%=searchCondition.getString("devUserDeptOid3") %>">
                <a href="javascript:addDepartment('devUserDept3');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:delDepartment('devUserDeptOid3','devUserDept3');"><img src="/plm/portal/images/icon_delete.gif"></a>
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
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%;min-height:200px;">
                    <%
                    if ( radioValue.equals("1") ) {
                    %>
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/ProjectSearchReviewGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Sapauth="<%=sapAuth %>"
                            Layout_Param_Sapcheck="<%=sapCheck %>"
                            Layout_Param_Searchpopup="Y"
                            Layout_Param_Mode="<%=mode%>"
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
                            Layout_Param_Searchpopup="Y"
                            Layout_Param_Mode="<%=mode%>"
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
                    else if ( radioValue.equals("3") ) {
                    %>
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/ProjectSearchMoldGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Sapauth="<%=sapAuth %>"
                            Layout_Param_Sapcheck="<%=sapCheck %>"
                            Layout_Param_Searchpopup="Y"
                            Layout_Param_Mode="<%=mode%>"
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
</table>
</form>
</body>
</html>
