<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wt.fc.PagingQueryResult"%>
<%@page import="e3ps.common.util.CommonUtil,
                e3ps.common.util.DateUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.project.beans.TemplateProjectData,
                e3ps.project.outputtype.OEMProjectType,
                e3ps.project.TemplateProject"%>
<jsp:useBean id="searchCondition" class="java.util.Hashtable" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request" />
<jsp:useBean id="moldType" class="java.lang.String" scope="request" />
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");


    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;
    
    if( searchCondition != null && !searchCondition.isEmpty() && searchCondition.get("perPage") != null ) {

        pagingSize = searchCondition.get("perPage").toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title><%=messageService.getString("e3ps.message.ket_message", "00557") %><%--WBS 목록--%></title>
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />
<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->
<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/context.jsp"%>
<%@include file="/jsp/common/top_include.jsp"%>
<%@include file="/jsp/common/multicombo.jsp"%>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}

table {
    border-spacing: 0;
    border: 0px;
}

table th,table td {
    padding: 0
}

img {
    vertical-align: middle;
    border: 0;
}

input {
    vertical-align: middle;
    line-height: 22px;
}
-->
</style>

<%
    String divisionValue = request.getParameter("division");
    if(divisionValue == null){
        divisionValue = "";
    }

    String wbsTypeValue = request.getParameter("wbsType");
    if(wbsTypeValue == null){
        wbsTypeValue = "";
    }

    String planType = request.getParameter("planType");
    if(planType == null){
        planType = "";
    }

    String assembling = request.getParameter("assembling");
    if(assembling == null){
        assembling = "";
    }

    String developType = request.getParameter("developType");
    if(developType == null){
        developType = "";
    }

    String makeType = request.getParameter("makeType");
    if(makeType == null){
        makeType = "";
    }

    String productType = request.getParameter("productType");
    if(productType == null){
        productType = "";
    }
    
    String callBackFn = request.getParameter("callBackFn");
    if(callBackFn == null){
	    callBackFn = "";   
    }
%>


<script type="text/javascript">

<!--
    function viewProject(oid) {
        parent.document.location.href = "/plm/jsp/project/template/TemplateProjectViewFrm.jsp?oid="+oid;
    }

    function displayChange3(){
        if(wbsTable.rows.length > 2) {
            for(i = wbsTable.rows.length; i > 2; i--)
                wbsTable.deleteRow(i-1);
        }

        form = document.frm;
        var wbstypeValue = "";
        for(i=0; i<form.wbsType.length; i++) {
            if(form.wbsType[i].selected) wbstypeValue = form.wbsType[i].value;
        }

        if(wbstypeValue == "제품 Project") {
            var row1 = wbsTable.insertRow();

            onecell1 = row1.insertCell();
            onecell1.className = "tdblueL";
            onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "02565") %><%--제품Type--%>";

            onecell2 = row1.insertCell();
            onecell2.className = "tdwhiteL0";
            onecell2.colSpan = "3";
            onecell2.innerHTML = "<input type=hidden name=ptType value=3> <select name=\"productType\" class=\"fm_jmp\" style=\"width:130\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option><option value=\"1\" <%if(productType.equals("1")){%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01801") %><%--생활가전--%> </option><option value=\"2\" <%if(productType.equals("2")){%>selected<%}%>>협피치</option></select>";
            //"<input type=hidden name=ptType value=3> <input type=\"radio\" value=\"1\" name=\"productType\" checked/>생활가전 <input type=\"radio\" value=\"2\" name=\"productType\" />협피치";
        }
    }

    function displayChange2(){
        if(wbsTable.rows.length > 2) {
            for(i = wbsTable.rows.length; i > 2; i--)
                wbsTable.deleteRow(i-1);
        }

        form = document.frm;
        var wbstypeValue = "";
        for(i=0; i<form.wbsType.length; i++) {
            if(form.wbsType[i].selected) wbstypeValue = form.wbsType[i].value;
        }

        if(wbstypeValue == "제품 Project") {
            var row1 = wbsTable.insertRow();

            onecell1 = row1.insertCell();
            onecell1.className = "tdblueL";
            onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01843") %><%--설계구분--%>";

            onecell2 = row1.insertCell();
            onecell2.className = "tdwhiteL";
            onecell2.innerHTML = "<select name=\"planType\" class=\"fm_jmp\" style=\"width:130\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option><option value=\"true\" <%if(planType.equals("true")){%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01842") %><%--설계 有--%> </option><option value=\"false\" <%if(planType.equals("false")){%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01841") %><%--설계 無--%></option></select>";
            //"<input type=hidden name=ptType value=1> <input type=\"radio\" value=\"true\" name=\"planType\" checked/>설계 有 <input type=\"radio\" value=\"false\" name=\"planType\" />설계 無";

            onecell3 = row1.insertCell();
            onecell3.className = "tdblueL";
            onecell3.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "02640") %><%--조립구분--%>";

            onecell4 = row1.insertCell();
            onecell4.className = "tdwhiteL0";
            onecell4.innerHTML = "<select name=\"assembling\" class=\"fm_jmp\" style=\"width:130\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option><option value=\"true\" <%if(assembling.equals("true")){%>selected<%}%>>조립 有 </option><option value=\"false\" <%if(assembling.equals("false")){%>selected<%}%>>조립 無</option></select>";
            //"<input type=\"radio\" value=\"true\" name=\"assembling\" checked/>조립 有 <input type=\"radio\" value=\"false\" name=\"assembling\" />조립 無";

            var row2 = wbsTable.insertRow();

            onecell1 = row2.insertCell();
            onecell1.className = "tdblueL";
            onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%>";

            onecell2 = row2.insertCell();
            onecell2.className = "tdwhiteL0";
            onecell2.colSpan = "3";
            onecell2.innerHTML = "<select name=\"developType\" class=\"fm_jmp\" style=\"width:130\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option><option value=\"1\" <%if(developType.equals("1")){%>selected<%}%>>전략개발 </option><option value=\"2\" <%if(developType.equals("2")){%>selected<%}%>><%=messageService.getString("e3ps.message.ket_message", "01963") %><%--수주개발--%></option><option value=\"3\" <%if(developType.equals("3")){%>selected<%}%>>연구개발</option><option value=\"4\" <%if(developType.equals("4")){%>selected<%}%>>추가금형</option></select>";
            //"<input type=\"radio\" value=\"1\" name=\"developType\" checked/>전략개발 <input type=\"radio\" value=\"2\" name=\"developType\" />수주개발 <input type=\"radio\" value=\"3\" name=\"developType\" />연구개발";
        }else if(wbstypeValue == "금형 Project") {
            var row1 = wbsTable.insertRow();

            onecell1 = row1.insertCell();
            onecell1.className = "tdblueL";
            onecell1.innerHTML = "<%=messageService.getString("e3ps.message.ket_message", "01099") %><%--금형제작--%>";

            onecell2 = row1.insertCell();
            onecell2.className = "tdwhiteL0";
            onecell2.colSpan = "3";
            onecell2.innerHTML = "<input type=hidden name=ptType value=2> <select name=\"makeType\" class=\"fm_jmp\" style=\"width:130\"><option value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>]</option><option value=\"1\" <%if(makeType.equals("1")){%>selected<%}%>>사내 </option><option value=\"2\" <%if(makeType.equals("2")){%>selected<%}%>>외주</option><option value=\"3\" <%if(makeType.equals("3")){%>selected<%}%>>OEM</option></select>";
            //"<input type=hidden name=ptType value=2> <input type=\"radio\" value=\"1\" name=\"makeType\" checked/>사내 <input type=\"radio\" value=\"2\" name=\"makeType\" />외주 <input type=\"radio\" value=\"3\" name=\"makeType\" />OEM";
        }
    }

    <%-- function displayChange(){
        if(wbsTable.rows.length > 2) {
            for(i = wbsTable.rows.length; i > 2; i--)
                wbsTable.deleteRow(i - 1);
        }

        form = document.frm;
        var divisionValue = "";
        for(i=0; i<form.division.length; i++) {
            if(form.division[i].selected) divisionValue = form.division[i].value;
        }

        if(divisionValue == "") {
            wbsTypeTd.innerHTML = "&nbsp;";
        }else if(divisionValue == "자동차사업부") {
            wbsTypeTd.innerHTML = divisionValue + " / <select name=\"wbsType\" onchange=\"javascript:displayChange2();\" class=\"fm_jmp\" style=\"width:120\"><option  value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %>선택]</option><option  value=\"검토 Project\"><%=messageService.getString("e3ps.message.ket_message", "00717") %>검토 Project</option><option  value=\"제품 Project\"><%=messageService.getString("e3ps.message.ket_message", "02548") %>제품 프로젝트</option><option   value=\"금형 Project\"><%=messageService.getString("e3ps.message.ket_message", "01009") %>금형 Project</option></select>";
        }else {
            wbsTypeTd.innerHTML = divisionValue + " / <select name=\"wbsType\" onchange=\"javascript:displayChange3();\" class=\"fm_jmp\" style=\"width:130\"><option  value=\"\">[<%=messageService.getString("e3ps.message.ket_message", "01802") %>선택]</option><option  value=\"검토 Project\"><%=messageService.getString("e3ps.message.ket_message", "00717") %>검토 Project</option><option   value=\"제품 Project\"><%=messageService.getString("e3ps.message.ket_message", "02548") %>제품 프로젝트</option></select>";
        }
    } --%>

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    function search() {
//    onProgressBar();
       // alert("${selector}");
        showProcessing();     // 진행중 Bar 표시
        disabledAllBtn();     // 버튼 비활성화

        document.frm.action = "/plm/servlet/e3ps/SearchProjectTemplateServlet?popup=${popup}&selector=${selector}&origin=${origin}";
        document.frm.submit();
    }
    
    

      //Jquery
    $(document).ready(function(){
    	
        SuggestUtil.bind('CUSTOMEREVENT', 'clientCompany', '');
        
        var divide = "${searchCondition.moldType}";
        
        if(divide == "mold"){
        	
            $("#mold").attr("checked",true);
            
            if("${selector}" == "project"){
                $("#product").attr("disabled",true);
                $("#review").attr("disabled",true);
                $("#purchase").attr("disabled",true);
                $("#work").attr("disabled",true);
            }
            
        }else if(divide == "product"){
        	
            $("#product").attr("checked",true);
            
            if("${selector}" == "project"){
                $("#mold").attr("disabled",true);
                $("#review").attr("disabled",true);
                $("#purchase").attr("disabled",true);
                $("#work").attr("disabled",true);
            }
        }else if(divide == "purchase"){
            
            $("#purchase").attr("checked",true);
            
            if("${selector}" == "project"){
                $("#product").attr("disabled",true);
                $("#review").attr("disabled",true);
                $("#mold").attr("disabled",true);
                $("#work").attr("disabled",true);
            }
            
        }else if(divide == "work"){
            
            $("#work").attr("checked",true);
            
            if("${selector}" == "project"){
                $("#product").attr("disabled",true);
                $("#review").attr("disabled",true);
                $("#mold").attr("disabled",true);
                $("#purchase").attr("disabled",true);
            }
            
        }else{
            
            $("#review").attr("checked",true);
            
            if("${selector}" == "project"){
                $("#product").attr("disabled",true);
                $("#mold").attr("disabled",true);
                $("#purchase").attr("disabled",true);
            }
        }
        
        if("${selector}" == "project"){
            if("${activeType}" != null && "${activeType}" != ""){
                $("#activeType > option[value='${activeType}']").attr("selected", true);
                $("#activeType").attr("disabled",true);
            }
        }
        
        <%if(CommonUtil.isKETSUser()){%>
        	 $("#review").attr("disabled",true);
        <%}%>
        
        <!-- 검색값 유지 -->
        if("${searchCondition.activeType.activeType}" != ""){
            var activeType = "${searchCondition.activeType.activeType}";
            var activeTypeSplit = activeType.split(",");
            for(var i = 0; i < activeTypeSplit.length; i++){
                $("#activeType > option[value="+activeTypeSplit[i]+"]").attr("selected", true);  
            }
        }
        
        if("${searchCondition.division.division}" != ""){
            var division = "${searchCondition.division.division}";
            var divisionSplit = division.split(",");
            for(var i = 0; i < divisionSplit.length; i++){
                $("#division > option[value="+divisionSplit[i]+"]").attr("selected", true);  
            }
        }
        
        if("${searchCondition.devStep.devStep}" != ""){
            var devStep = "${searchCondition.devStep.devStep}";
            var devStepSplit = devStep.split(",");
            for(var i = 0 ; i < devStepSplit.length; i++){
                $("#devStep > option[value="+devStepSplit[i]+"]").attr("selected", true);  
            }
        } 
        
         if("${searchCondition.devCategory.devCategory}" != ""){
             var devCategory = "${searchCondition.devCategory.devCategory}";
             var devCategorySplit = devCategory.split(",");
             for(var i = 0; i < devCategorySplit.length; i++){
                 $("#devCategory > option[value="+devCategorySplit[i]+"]").attr("selected", true);  
             }
        }
        
          if("${searchCondition.making.making}" != ""){
             var making = "${searchCondition.making.making}";
             var makingSplit = making.split(",");
             for(var i = 0;  i < makingSplit.length; i++){
                 $("#making > option[value="+makingSplit[i]+"]").attr("selected", true);  
             }
        }
        
         if("${searchCondition.type.type}" != ""){
            var type = "${searchCondition.type.type}";
            var typeSplit = type.split(",");
            for(var i = 0; i < typeSplit.length; i++){
                $("#type > option[value="+typeSplit[i]+"]").attr("selected", true);  
            }
        } 
         
         if("${searchCondition.clientCompany}" != ""){
             $("#clientCompany").val("${searchCondition.clientCompany}");
         }
         
         if("${searchCondition.makeOffice}" != ""){
             $("#makeOffice").val("${searchCondition.makeOffice}");
         }
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });
        <c:if test="${popup eq 'yes'}">
        var width = 110;
        </c:if>
        <c:if test="${popup ne 'yes'}">
        var width = 150;
        </c:if>
        
       <!-- 2014.07.07 Multi Combo 생성 -->
        $("#activeType").multiselect({
            minWidth: width,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
         });
       
        $("#division").multiselect({
           minWidth: width,
           noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
           checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
           uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
       
        $("#devCategory").multiselect({
            minWidth: width,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        $("#devStep").multiselect({
            minWidth: width,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        $("#type").multiselect({
            minWidth: width,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        $("#making").multiselect({
            minWidth: width,
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        <!-- 2014.07.07 프로젝트 Type에 따른 검색 조건 -->  
        $("#review").click(function(){
            $("#review").attr("checked",true);
            init();
            search();
        });
        
        
        $("#product").click(function(){
            $("#product").attr("checked",true);
            init();
            search();
        });
        
        $("#mold").click(function(){
            $("#mold").attr("checked",true);
            init();
            search();
        });
        
        $("#purchase").click(function(){
            $("#purchase").attr("checked",true);
            init();
            search();
        });
        
        $("#work").click(function(){
            $("#work").attr("checked",true);
            init();
            search();
        });
        
        $(document).bind('keypress', function(e) {
            if (e.which == 13) {
                search();
            }
        });
        treeGridResize("#TemplateProjectListGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        
    });
      
    <!-- 2014.07.07 검색 조건 초기화 -->
    function init(){
        $('#activeType').multiselect("uncheckAll");
        $('#division').multiselect("uncheckAll");
        $('#devStep').multiselect("uncheckAll");
        $('#devCategory').multiselect("uncheckAll");
        $('#type').multiselect("uncheckAll");
        $('#making').multiselect("uncheckAll");
        $('#name').val("");
        $('#clientCompany').val("");
        $('#makeOffice').val("");
    }

    <!-- 2014.07.08 표준 WBS 등록 팝업-->
    function register(){
        var divide=$("[name=moldType]:checked").val();
        var url = "/plm/jsp/project/template/ProjectTempCreatePage.jsp?divide="+divide;
        openOtherName(url, "register", "1000", "400", "status=no,scrollbars=yes,resizable=no");
    }
    
    <!-- 2014.07.09 WBS Name 클릭시 조회팝업 -->
    function searchPopup(taskUrl, wbsContentUrl){
        var type = $(":radio[name=moldType]:checked").val();
        openOtherName(encodeURI(wbsContentUrl+"&taskUrl="+taskUrl+"&type="+type), "StandardWBSSearch", "1335", "768", "status=no,scrollbars=yes,resizable=yes");
        //openOtherName(a, "process", "950", "550", "status=no,scrollbars=yes,resizable=no");
    }
    
    function delValue(id){
        $("#"+id).val("");
    }
    
    <!-- 2014.07.15 기존 최종사용처(고객처) 추가-->
    function addValue(type, depth, viewType) {
    	SearchUtil.selectOneLastUsingBuyerPopup('insertLastUsingBuyer');
        
    }
    
    function insertLastUsingBuyer(arrObj){
        $('[name=clientCompany]').val(arrObj[0][2]);
    }

    <!-- 2014.07.18 copy WBS함수추가 -->
    function copyWBS(){
        var temp = Grids[0];
        var R = temp.GetSelRows();
        var value = R[0].ProjectOid;
        opener.tmpRegister(value);
        self.close();
    }
    
    
    function setCategory(selectCategory){
    	var temp = Grids[0];
        var R = temp.GetSelRows();
    	if ( typeof selectCategory == "undefined" || selectCategory == null ) {
            return;
        }
    	<%if(!"wbsReSetting".equals(callBackFn)){%>
        selectCategory[selectCategory.length] = {
            ProjectOid : R[0].ProjectOid,
            TempName : R[0].TempName,
            TempDu : R[0].TempDu,
            TempCreateDate : R[0].TempCreateDate,
            TempModifyDate : R[0].TempModifyDate            
        };
        <%}%>
        eval('opener.<%=callBackFn%>(selectCategory)');
        self.close();
    }
    
    <!-- 2014.08.12 WBS 재설정 팝업 -->
    function detailSelectWBS(){
        var temp = Grids[0];
        var R = temp.GetSelRows();
        if(R=='' || R.length == 0) {
            alert('<%=messageService.getString("e3ps.message.ket_message", "01818") %>');//선택한 목록이 없습니다.
            return;
        }
        var value = R[0].ProjectOid;
        var url = "/plm/jsp/project/template/DetailSelectWBSPopup.jsp?origin=${origin}&moldType=${moldType}&oid="+value+"&callBackFn=<%=callBackFn%>&invokeMethod=setCategory";
        //openOtherName(url, "detailSelectWBS", "600", "250", "status=no,scrollbars=no,resizable=no");
        window.open(url,'',"top=100px, left=100px, height=250px, width=600px")
        /* var selectCategory = window.showModalDialog(url,window,"help=no; resizable=yes; status=no; scroll=no; dialogWidth=600px; dialogHeight:250px; center:yes");
         */
        
    }
    
    function projectFromWbsCopy(){
        var temp = Grids[0];
        var R = temp.GetSelRows();
        var value = R[0].ProjectOid;
        
        $("#copyOid").val(value);
        
        showProcessing();
        
         $.ajax({
            type       : "POST",
            url        : "/plm/servlet/e3ps/SearchProjectTemplateServlet?mode=reSetting",
            data       : $("#frm").serialize(),
            dataType   : "json",
            success    : function(data){
                         hideProcessing();
                         window.opener.location.reload();
                         window.close();
                         
                    
                            
            },
            error    : function(xhr, status, error){
                         alert(xhr+"  "+status+"  "+error);
                         
            }
        });  
    }
    
    function openChangeWBSSchedule() {

        var screenWidth = screen.availWidth;
        var screenHeight = screen.availHeight;
        var url = "/plm/servlet/e3ps/ProjectScheduleServlet?oid=e3ps.project.TemplateProject:117484&cmd=wbs_search&width=" + screenWidth + "&height=" + screenHeight;
        openOtherName(url, "ChangeProjectSchedule", screenWidth, screenHeight, "status=no,scrollbars=no,resizable=no");
   }
    
    function selectPartner(){
        var url="/plm/jsp/ews/SelectPartnerPopup.jsp?mode=single&method=linkPartner";
        openWindow(url,"","760","500","status=1,scrollbars=no,resizable=no");
    }
    
    function linkPartner(arr){
        if(arr.length == 0) {
                alert('<%=messageService.getString("e3ps.message.ket_message", "03215") %>');
                return;
            }

            //document.getElementById("proteamNo").value = arr[0][0];
            document.getElementById("makeOffice").value = arr[0][1];
    }
//-->
</script>

<%
    boolean isAdmin = CommonUtil.isAdmin();
%>
</head>
<c:if test="${popup eq 'yes'}">
<body>
</c:if>
<c:if test="${popup ne 'yes'}">
<body class="body-space">
</c:if>
    <form name="frm" id="frm" method="POST">
        <!-- hidden begin -->
        <input type="hidden" name="originOid" value="${origin}"> 
        <input type="hidden" id="copyOid" name="copyOid" value="">
        <input type="hidden" name="command" value="search"> 
        <input type="hidden" name="perPage" value="<%=pagingSize%>" />
        <input type="hidden" name="callBackFn" value="<%=callBackFn%>" />
        <input type="hidden" name="initValue" value="noinit" />
        <!-- hidden end -->
        <table style="width: 100%; height: 100%; table-layout: fixed;" border="0">
            <tr>
                <td valign="top">
                    <!-- [START] title & position -->
                    <table style="width: 100%;">
                        <tr>
                            <td>
                                <table style="width: 100%; height: 28px;">
                                    <c:if test="${popup ne 'yes'}">
                                       <tr>
                                            <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07104") %><%--표준 WBS 2014.07.07 --%></td>
                                            <td align="right">
                                                <img src="/plm/portal/images/icon_navi.gif">Home
                                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><!-- 프로젝트관리 -->
                                                <img src="/plm/portal/images/icon_navi.gif">WBS
                                                <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "00557") %><%-- WBS 목록 --%>
                                            </td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${popup eq 'yes'}">
                                        <tr>
                                            <td>                                            
                                                <table style="width: 100%; height: 100%;">
                                                    <tr>
                                                        <td valign="top">
                                                            <table style="width: 100%;">
                                                                <tr>
                                                                    <td background="/plm/portal/images/logo_popupbg.png">
                                                                        <table style="height: 28px;">
                                                                          <tr>
                                                                            <td width="7"></td>
                                                                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                                                            <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "07339") %><%--표준 WBS 선택 2014.07.07 --%></td>
                                                                          </tr>
                                                                        </table>
                                                                    </td>
                                                                    <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </c:if>
                                </table>
                            </td>
                        </tr>
                        <c:if test="${popup ne 'yes'}">
                            <tr>
                                <td class="head_line"></td>
                            </tr>
                        </c:if>
                        <tr>
                            <td class="space10"></td>
                        </tr>
                    </table> <!-- [END] title & position --> <!-- [START] button -->
                    <table style="width: 100%;">
                        <tr>
                            <!--  <td>&nbsp;</td> -->
                            <td align="left">
                                <label><input type="radio" id="review" name="moldType" value="review"><%=messageService.getString("e3ps.message.ket_message","00716")%><%--검토--%></label>
                                <label><input type="radio" id="product" name="moldType" value="product"><%=messageService.getString("e3ps.message.ket_message","02536")%><%--제품--%></label>
                                <label><input type="radio" id="mold" name="moldType" value="mold"><%=messageService.getString("e3ps.message.ket_message","00997")%><%--금형--%></label>
                                <label><input type="radio" id="purchase" name="moldType" value="purchase">구매품<%--구매품--%></label>
                                <label><input type="radio" id="work" name="moldType" value="work">업무</label>
                            </td>
                            <td align="right">
                            <c:if test="${popup ne 'yes'}">
                                <table>
                                    <tr>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:register();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "07105") %><%--표준 WBS 등록--%></a>
                                        </td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:init();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message","02819")%><%--초기화--%></a>
                                        </td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
                                        </td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </c:if>
                            <c:if test="${popup eq 'yes'}">
                                <table>
                                    <tr>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a>
                                        </td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <c:if test="${selector ne 'project'}">
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:copyWBS();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message","01802")%><%--선택--%></a>
                                            </td>
                                        </c:if>
                                        <c:if test="${selector eq 'project' and !(searchCondition.moldType eq 'mold' or searchCondition.moldType eq 'purchase') }">
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:detailSelectWBS();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a>
                                            </td>
                                        </c:if>
                                        <c:if test="${selector eq 'project' and (searchCondition.moldType eq 'mold' or searchCondition.moldType eq 'purchase') }">
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:projectFromWbsCopy();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%></a>
                                            </td>
                                        </c:if>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                        <td width="5">&nbsp;</td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                            href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a>
                                        </td>
                                        <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                                    </tr>
                                </table>
                            </c:if>
                            </td>
                        </tr>
                    </table>
                    <table style="width: 100%;">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table> <!-- [END] button --> <!-- [START] search condition -->
                    <!-- 검색영역 collapse/expand -->
                    <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
                    <table style="width: 100%;">
                        <tr>
                            <td class="tab_btm2"></td>
                        </tr>
                    </table>
                    <table style="width: 100%; table-layout: fixed;" id="wbsTable">
                        <tr>
                            <td class="tdblueL" style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01760")%><%--상태--%></td>
                            <!--상태-->
                            <td class="tdwhiteL">
                                <select id="activeType" name="activeType" class="fm_jmp" multiple="multiple">
                                    <option value="활성"><%=messageService.getString("e3ps.message.ket_message", "07106")%><%--활성--%></option>
                                    <option value="비활성"><%=messageService.getString("e3ps.message.ket_message", "07107")%><%--비활성--%></option>
                                </select></td>
                            
                            
                            <c:choose>
                                <c:when test="${searchCondition.moldType eq 'work' }">
                                    <td class="tdblueL" style="width: 80px;" colspan="3"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
                                </c:when>
                                <c:otherwise>
                                    <td class="tdblueL" style="width: 80px;"><%=messageService.getString("e3ps.message.ket_message", "01662")%><%--사업부--%></td>
                                </c:otherwise>
                            </c:choose>
                            
                            <td class="tdwhiteL">
                                <select id="division" name="division" class="fm_jmp" multiple="multiple">
                                    <%if(!CommonUtil.isKETSUser()){ %>
                                    <option value="자동차사업부"><%=messageService.getString("e3ps.message.ket_message", "02401")%><%--자동차사업부--%></option>
                                    <option value="전자사업부"><%=messageService.getString("e3ps.message.ket_message", "02483")%><%--전자사업부--%></option>
                                    <%}else { %>
                                    <option value="KETS">KETS<%--KETS--%></option>
                                    <%} %>
                                </select></td>
                            <c:if test="${searchCondition.moldType eq 'review'}">
                                <td class="tdblueL" style="width: 80px;" id="type1"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                                <!--고객처-->
                                <td class="tdwhiteL0" id="typeAttr1"><input type="text" class="txt_field" name="clientCompany" id="clientCompany"
                                    value="" style="width: 68%" /> <a href="javascript:addValue('CUSTOMEREVENT', 1, '');" class="btn_blue"><img
                                        src="/plm/portal/images/icon_5.png"></a> <a href="javascript:delValue('clientCompany');"><img
                                        src="/plm/portal/images/icon_delete.gif"></a></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType eq 'product'}">
                                <td class="tdblueL" style="width: 80px;" id="type1"><%=messageService.getString("e3ps.message.ket_message", "00583")%><%--개발구분--%></td>
                                <!--개발구분-->
                                <td class="tdwhiteL0" id="typeAttr1"><select id="devCategory" name="devCategory" class="fm_jmp"
                                    multiple="multiple">
                                        <option value="전략개발"><%=messageService.getString("e3ps.message.ket_message", "02476")%><%--전략개발--%></option>
                                        <option value="수주개발"><%=messageService.getString("e3ps.message.ket_message", "01963")%><%--수주개발--%></option>
                                        <option value="연구개발"><%=messageService.getString("e3ps.message.ket_message", "02128")%><%--연구개발--%></option>
                                        <option value="추가금형"><%=messageService.getString("e3ps.message.ket_message", "02865")%><%--추가금형--%></option>
                                </select></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType eq 'mold' or searchCondition.moldType eq 'purchase'}">
                                <td class="tdblueL" style="width: 80px;" id="type1"><%=messageService.getString("e3ps.message.ket_message", "02533")%><%--제작처--%></td>
                                <!--제작처-->
                                <td class="tdwhiteL0" id="typeAttr1"><input type="text" class="txt_field" name="makeOffice" id="makeOffice" value=""
                                    style="width: 70%;" /> <a href="javascript:selectPartner();"><img
                                        src="/plm/portal/images/icon_5.png"></a> <a href="javascript:delValue('makeOffice');"><img
                                        src="/plm/portal/images/icon_delete.gif"></a></td>
                            </c:if>
                        </tr>
                        <c:if test="${searchCondition.moldType eq 'product'}">
                            <tr id="tr1">
                                <td class="tdblueL" style="width: 90px;" id="type2"><%=messageService.getString("e3ps.message.ket_message", "00859")%><%--고객처--%></td>
                                <!--고객사-->
                                <td class="tdwhiteL" id="typeAttr2"><input type="text" class="txt_field" name="clientCompany" id="clientCompany" value=""
                                    style="width: 64%;" /> <a href="javascript:addValue('CUSTOMEREVENT', 1, '');"><img
                                        src="/plm/portal/images/icon_5.png"></a> <a href="javascript:delValue('clientCompany');"><img
                                        src="/plm/portal/images/icon_delete.gif"></a></td>
                                <td class="tdblueL" style="width: 80px;" id="type3"><%=messageService.getString("e3ps.message.ket_message", "00629")%><%--개발단계--%></td>
                                <!--개발단계-->
                                <td class="tdwhiteL" id="typeAttr3">
                                    <select id="devStep" name="devStep" class="fm_jmp" multiple="multiple">
                                        <option value="pilot">PILOT</option>
                                        <option value="proto">PROTO</option>
                                </select></td>
                                <td class="tdblueL non-back" style="width: 80px;"></td>
                                <td class="tdwhiteL0"></td>
                            </tr>
                        </c:if>
                        <c:if test="${searchCondition.moldType eq 'mold' or searchCondition.moldType eq 'purchase'}">
                            <tr id="tr1">
                                <c:if test="${searchCondition.moldType eq 'mold' }">
                                <td class="tdblueL" style="width: 90px;" id="type2"><%=messageService.getString("e3ps.message.ket_message", "01019")%><%--금형구분--%></td>
                                <!--금형구분-->
                                <td class="tdwhiteL" id="typeAttr2"><select id="type" name="type" class="fm_jmp" multiple="multiple">
                                        <option value="mold">MOLD</option>
                                        <option value="press">PRESS</option>
                                </select></td>
                                </c:if>
                                <td class="tdblueL" style="width: 80px;" id="type3"><%=messageService.getString("e3ps.message.ket_message", "02532")%><%--제작구분--%></td>
                                <!--제작구분-->
                                <c:choose>
                                    <c:when test="${searchCondition.moldType eq 'purchase' }">
                                        <td class="tdwhiteL" id="typeAttr3" colspan="5">
                                    </c:when>
                                    <c:otherwise>
                                        <td class="tdwhiteL" id="typeAttr3" colspan="3">
                                    </c:otherwise>
                                </c:choose>
	                                <select id="making" name="making" class="fm_jmp" multiple="multiple">
	                                        <option value="사내"><%=messageService.getString("e3ps.message.ket_message", "01655")%><%--사내--%></option>
	                                        <option value="외주"><%=messageService.getString("e3ps.message.ket_message", "02184")%><%--외주--%></option>
	                                </select>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <c:if test="${searchCondition.moldType eq 'review'}">
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07108") %><%--표준 WBS Name --%></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType eq 'product'}">
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07108") %><%--표준 WBS Name --%></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType eq 'mold' or searchCondition.moldType eq 'purchase'}">
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07108") %><%--표준 WBS Name --%></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType eq 'work'}">
                                <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "07108") %><%--표준 WBS Name --%></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType eq 'review'}">
                                <td class="tdwhiteL" colspan="3"><input name="name" id="name"
                                    value="<%=(request.getParameter("name") == null) ? "" : request.getParameter("name")%>"
                                    class="txt_field" style="width: 98%; text-transform: 'uppercase'" /></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType ne 'review'}">
                                <td class="tdwhiteL0" colspan="5"><input name="name" id="name"
                                    value="<%=(request.getParameter("name") == null) ? "" : request.getParameter("name")%>"
                                    class="txt_field" style="width: 98%; text-transform: 'uppercase'" /></td>
                            </c:if>
                            <c:if test="${searchCondition.moldType eq 'review'}">
                                <td class="tdblueL" id="type2"><%=messageService.getString("e3ps.message.ket_message", "00583")%><%--개발구분--%></td>
                                <!--개발구분-->
                                <td class="tdwhiteL0" id="typeAttr2">
                                    <select id="devCategory" name="devCategory" class="fm_jmp" multiple="multiple">
                                        <option value="전략개발"><%=messageService.getString("e3ps.message.ket_message", "02476")%><%--전략개발--%></option>
                                        <option value="수주개발"><%=messageService.getString("e3ps.message.ket_message", "01963")%><%--수주개발--%></option>
                                        <option value="연구개발"><%=messageService.getString("e3ps.message.ket_message", "02128")%><%--연구개발--%></option>
                                        <option value="추가금형"><%=messageService.getString("e3ps.message.ket_message", "02865")%><%--추가금형--%></option>
                                </select></td>
                            </c:if>
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
                            <div id="listGrid" style="width: 100%; height:100%; min-height:200px;">
                                <bdo Debug="1" AlertError="0"
                                    Layout_Url="/plm/jsp/project/template/TemplateProjectListGridLayout.jsp?moldType=<%=moldType%>"
                                    Layout_Param_Pagingsize="<%=pagingSize%>" Layout_Param_Popup="${popup}"
                                    Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST" Data_Param_Result="<%=tgData%>"
                                    Data_Param_Pagingsize="<%=pagingSize%>" Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                                    Export_Data="TGData" Export_Param_File="Search_StandardDoc_List"></bdo>
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
