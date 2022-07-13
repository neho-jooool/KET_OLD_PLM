<%@page import="e3ps.common.util.KETParamMapUtil"%>
<%@page import="e3ps.dms.beans.DMSUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Vector,
                java.util.List,
                java.util.HashMap,
                java.util.Map,
                java.text.SimpleDateFormat,
                java.util.Calendar"%>

<%@page import="wt.lifecycle.LifeCycleHelper,
                wt.lifecycle.LifeCycleTemplate,
                wt.lifecycle.State"%>

<%@page import="e3ps.dms.service.KETDmsHelper,
                e3ps.dms.entity.KETDocumentCategory,
                e3ps.common.util.CommonUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.WCUtil,
                e3ps.common.code.NumberCodeHelper"%>
                
<%--로그 설정 임포트 시작--%>
<%@ page import="ext.ket.shared.log.Kogger"%>
<%@ page import="ext.ket.shared.log.Dogger"%>
<%--로그 설정 임포트 끝--%>                

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

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

    if ( searchCondition.isEmpty() ) {
        Calendar cal = Calendar.getInstance();
        searchCondition.put("postdate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.add(Calendar.MONTH, -3);
        searchCondition.put("predate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }else{
	   Kogger.debug("SearchTechDocument", null, null, "start date:" + StringUtil.checkNull( (String)searchCondition.get("predate") ));
	   Kogger.debug("SearchTechDocument", null, null, "  end date:" + StringUtil.checkNull( (String)searchCondition.get("postdate") ));
    }

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 사업부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONTYPE");
    List<Map<String, Object>> divisionTypeNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 버전
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "VERSION");
    List<Map<String, Object>> versionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    // 화면 처음 들어 올 경우 라디오버튼 기본 값 선택
    if ( StringUtil.checkNull( (String)searchCondition.get("islastversion") ) == "" ) {
        searchCondition.put("islastversion", "LATEST");
    }

    ArrayList divisionCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("divisionList")), "," );
    ArrayList stateCondList    = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("lcState")), "," );
    
    /*
    ArrayList category1List    = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("category1List")), "," );
    ArrayList category2List    = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("category2List")), "," );
    ArrayList category3List    = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("category3List")), "," );

    String categoryCode = "";
    String categoryName = messageService.getString("e3ps.message.ket_message", "01445")/*미등록되어 있습니다* /;
   
    String categoryCode0 = "";
    String categoryCode1 = "";
    String categoryCode2 = "";
    String docTypeCode = null;

    // 1 Level 분류체계 찾기
    parameter.clear();
    parameter.put("docTypeCode", "TD");
    parameter.put("locale",      messageService.getLocale().toString());
    parameter.put("parentCode",  "ROOT");

    List<Map<String, Object>> categoryList = DMSUtil.getDocumentCategory(parameter);
    if ( categoryList.size() > 0 ) {
        categoryCode0 = categoryList.get(0).get("categoryCode").toString();
        categoryName = categoryList.get(0).get("categoryName").toString();
    }
    */

    String divisionCode = "";
    if ( CommonUtil.isMember("전자사업부") ) {
        divisionCode = "ER";
    }
    else if ( CommonUtil.isMember("자동차사업부") ) {
        divisionCode = "CA";
    }

    if ( searchCondition.getStringArray("divisionCode").length == 0 ) {
        searchCondition.put("divisionCode", divisionCode);
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01404") %><%--문서검색--%></title>

<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/jsp/dms/js/techdocFile.js"></script>
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

    function perPage(v) {
        if ( !valiDate() ) return;

        document.form01.perPage.value = v;

        search();
    }

    <%-- 등록 --%>
    function register() {
        
        var url = "/plm/jsp/dms/CreateTechDocu.jsp";
        
        var name = "CreateTechDocu";
        var opt = launchCenter(950, 768);
        opt += ", resizable=no, scroll=yes";
        var windowWin = window.open(url,name,opt);
        
        //windowWin.resizeTo(width,height);
        windowWin.focus();
        
    }
    
    function search() {
        if ( !valiDate() ) return;

        showProcessing();     // 진행중 Bar 표시
        disabledAllBtn();     // 버튼 비활성화

        document.form01.cmd.value = "search";
        document.form01.action = "/plm/servlet/e3ps/TechDocumentServlet";
        document.form01.target = "_self";
        document.form01.submit();
    }

    function clearAll() {
    	<%--
        $("#category1").multiselect("uncheckAll");
        $("#category2").multiselect("uncheckAll");
        $("#category2").empty().data('options');
        $("#category3").multiselect("uncheckAll");
        $("#category3").empty().data('options');
    	--%>
        $("#documentNo").val("");
        $("#divisionCode").multiselect("uncheckAll");
        if ( "<%=divisionCode %>" != "" ) {
            $("#divisionCode").val("<%=divisionCode %>").attr('selected','selected');
        }
        $("#divisionCode").multiselect('refresh');
        $("#documentName").val("");
        $("#authorStatus").multiselect("uncheckAll");
        $("#creator").val("");
        $("#deptName").val("");
        $("#creatorName").val("");
        $("#predate").val("");
        $("#postdate").val("");
        $("#islastversion").eq(0).attr("checked", true);
        
        $('[name=techDocType]').val("");
        $('[name=techDocTypeTxt]').val("");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);

        // Category
        //categoryDisable();
    }

    function excelDown() {

        document.form01.cmd.value = "excelDown";

        document.form01.target = "";
        document.form01.action = "/plm/servlet/e3ps/TechDocumentServlet";
        document.form01.submit();
    }

    function changeDate1() {
        var startDate = document.form01.predate.value;

        if ( startDate != "" ) {
            if ( !dateCheck(startDate,"-") ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>");
                document.form01.predate.value = "";
            }
        }
    }

    function changeDate2 (){
        var endDate = document.form01.postdate.value;
        if ( endDate != "" ) {
            if ( !dateCheck(endDate,"-") ) {
                alert("<%=messageService.getString("e3ps.message.ket_message", "01165") %><%--날짜 형식이 올바르지 않습니다--%>");
                document.form01.postdate.value = "";
            }
        }
    }

    function valiDate() {
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
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=acceptMember";
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,'searchTechDoc', 790, 610, opts);
    }

    function acceptMember(arrObj) {
    	var isAppend = "Y";
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

    //Jquery
    $(document).ready(function(){
    	
    	treeGridResize("#SearchTechDocumentGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        // Enter 검색
        $("form[name=form01]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
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

        //categoryDisable();
        CalendarUtil.dateInputFormat('predate');
        CalendarUtil.dateInputFormat('postdate');
        
        //------------ start suggest binding ------------//
        SuggestUtil.bind('USER', 'creatorName', 'creator');
        SuggestUtil.bind('TECHDOCTYPE', 'techDocTypeTxt', 'categoryCode');
        SuggestUtil.bind('DEPARTMENT', 'deptName');
        
        //------------ end suggest binding ------------//
        
    });
    
    function setTechDocCategory (checkedNode) {
        var nodeIdStr='', nodeNameStr='';
        for(var i=0; i < checkedNode.length; i++){
            if(i == checkedNode.length - 1){
                nodeIdStr += checkedNode[i].id;
                nodeNameStr += checkedNode[i].name;
            }else{
                nodeIdStr += checkedNode[i].id+','; 
                nodeNameStr += checkedNode[i].name+',';
            }
        }
        $('[name=categoryCode]').val(nodeIdStr);
        $('[name=techDocTypeTxt]').val(nodeNameStr);
    }

    function goOpen(url){
    	PLM_FILE_DOWNLOAD2(url);
    	//window.open(url,"download");
    }
    function fileOpenAjax(oid,appDataOid) {
		var start_con = "";
		var bat_file  = "";
		var filePath  = "";
		var end_con   = "";
	    $.ajax( {
	        url  : "/plm/servlet/e3ps/TechDocumentServlet?cmd=designFileOpen&oid="+oid+"&appDataOid="+appDataOid,
	        type : "POST",
	        async : false,
	        dataType : 'json',
	        success: function(data) {
	        	$.each(data.returnObj, function() {
	        		start_con = this.start_con;
	        		bat_file  = this.bat_file;
	        		filePath  = this.filePath;
	        		fileName  = this.fileName;
	        		end_con   = this.end_con;
                });
	        	
	        	start_con = decode(start_con);
	        	bat_file = decode(bat_file);
	        	filePath = decode(filePath)+fileName;
	        	end_con = decode(end_con);
	        	fnRunConnectFile(start_con, bat_file, filePath, end_con);
	        }
	    });
	}
-->
function addDepartment(){
    var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?mode=s&invokeMethod=setDepartMent";
    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=430,height=465";
    getOpenWindow2(url,'csList', 430, 465, opts);
 }

function setDepartMent(rsltArrayObject){
    var departName = rsltArrayObject[0].NAME;

    $('[name=deptName]').val(departName);
}

</script>
</head>

<body class="body-space">
<form name="form01" method="post">

<!-- hidden begin -->
<input type="hidden" name="cmd" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<!-- hidden end -->

<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">
        <table style="width: 100%;">
        <tr>
            <td>
                <table style="width: 100%; height: 28px;">
                <tr>
                    <td style="width: 20px"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "01124") %><%--기술문서 검색--%></td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01406") %><%--문서관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01124") %><%--기술문서 검색--%>
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
        <table style="width: 100%;">
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <table>
                <tr>
                    <!-- td>
                        <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %><%--결과 내 재검색--%>
                    </td>
                    <td style="width: 15px;">&nbsp;</td -->
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:register();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "등록") %><%--등록--%></a></a>
                            </td>
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
                                <a href="javascript:clearAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
                            </td>
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
                                <a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></a>
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
        <!-- 검색영역 collapse/expand -->
        <div id="collapseDiv" align="center"><img src="/plm/extcore/extjs50/build/packages/ext-theme-classic/build/resources/images/layout/mini-top.gif" class="collapseIcon"></div>
        <table style="width: 100%;">
        <tr>
            <td  class="tab_btm2"></td>
        </tr>
        </table>
        <table style="width: 100%;">
            <col width="10%">
            <col width="23%">
            <col width="10%">
            <col width="23%">
            <col width="10%">
            <col width="24%">
        <tr>  <!--  문서분류 -->
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01424") %><%--문서분류--%></td>
            <td class="tdwhiteL">
                <input type="text" name="techDocTypeTxt" class="txt_field" style="width: 80%" value="<%=StringUtil.checkNull( (String)searchCondition.get("techDocTypeTxt") ) %>">
                <input type="hidden" name="categoryCode" value="<%=StringUtil.checkNull( (String)searchCondition.get("categoryCode") ) %>">
                <a href="javascript:SearchUtil.selectTechDocCategory('setTechDocCategory');">
                <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                <a href="javascript:CommonUtil.deleteValue('techDocTypeTxt','categoryCode');">
                <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
           </td>
           <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01420") %><%--문서번호--%></td>
           <td class="tdwhiteL">
               <input type="text" id="documentNo" name="documentNo" class="txt_field"  style="width:80%;" value="<%=StringUtil.checkNull( (String)searchCondition.get("documentNo") ) %>">
           </td>
           <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
           <td class="tdwhiteL">
               <input type="text" id="documentName" name="documentName" class="txt_field"  style="width:80%;" value="<%=StringUtil.checkNull( (String)searchCondition.get("documentName") ) %>">
           </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
            <td class="tdwhiteL">
                <input type="hidden" id="creator" name="creator" value="<%=StringUtil.checkNull( (String)searchCondition.get("creator") ) %>" />
                <input type="text" id="creatorName" name="creatorName" class="txt_field"  style="width:60%;" id="textfield4" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorName") ) %>" />
                &nbsp;<a href="javascript:selectUser();"><img src="/plm/portal/images/icon_user.gif" border="0"></a>
                &nbsp;<a href="javascript:CommonUtil.deleteValue('creator','creatorName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
            <td class="tdwhiteL">
                <input type="text" id="deptName" name="deptName" class="txt_field"  style="width:80%;" value="<%=StringUtil.checkNull( (String)searchCondition.get("deptName") ) %>">
                <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:CommonUtil.deleteValue('deptName');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
            <td class="tdwhiteL" >
                <input type="text" id="predate" name="predate" class="txt_field" style="width:70px;" id="textfield9" value="<%=StringUtil.checkNull( (String)searchCondition.get("predate") ) %>" onChange="javascript:changeDate1()">
                &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('predate');" style="cursor: hand;">
                &nbsp;~&nbsp;
                <input type="text" id="postdate" name="postdate" class="txt_field"  style="width:70px;" id="textfield10" value="<%=StringUtil.checkNull( (String)searchCondition.get("postdate") ) %>" onChange="javascript:changeDate2()">
                &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('postdate');" style="cursor: hand;">
            </td>
        </tr>
        <tr>
            <td class="tdblueL" ><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL">
                <select id="divisionCode" name="divisionCode" class="fm_jmp" style="" multiple="multiple">
                <%
                for ( int i=0; i<divisionTypeNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionTypeNumCode.get(i).get("code") %>" <%=KETParamMapUtil.contains(searchCondition.getStringArray("divisionCode"), divisionTypeNumCode.get(i).get("code")) ? " selected" : "" %>><%=divisionTypeNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%></td>
            <td class="tdwhiteL">
                <select id="authorStatus" name="authorStatus" class="fm_jmp" style="" multiple="multiple">
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
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
            <td class="tdwhiteL">
                <%
                for ( int i=0; i<versionNumCode.size(); i++ ) {
                %>
                <input id="islastversion" name="islastversion" type="radio" class="Checkbox" value="<%=versionNumCode.get(i).get("code") %>" <%if ( StringUtil.checkNull( (String)searchCondition.get("islastversion") ).equals( versionNumCode.get(i).get("code") ) ){%>checked<%}%> ><%=versionNumCode.get(i).get("value")%>
                <%
                }
                %>
            </td>
        </tr>
        </table>
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>

        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%">
                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/dms/SearchTechDocumentGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Data_Url="/plm/jsp/common/searchGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_TechDocument_List"
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
        <!-- download target iframe -->
        <iframe id="download" name="download" src="#" style="width:0px;height:0px;border:0px solid #fff;"></iframe>
    </td>
</tr>
<!-- [END] copyright -->

</table>
</form>
</body>
</html>
