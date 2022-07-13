<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Locale,
                java.util.List,
                java.util.Map,
                java.util.HashMap,
                java.text.SimpleDateFormat,
                java.util.Calendar"%>

<%@page import="e3ps.project.IssueType,
                e3ps.common.util.KETParamMapUtil,
                e3ps.common.util.PropertiesUtil,
                e3ps.common.code.NumberCodeHelper,
                e3ps.common.util.CommonUtil"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

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

    if ( searchCondition.isEmpty() ) {
        Calendar cal = Calendar.getInstance();
        searchCondition.put("endCreateDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        searchCondition.put("startCreateDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    }
    
    boolean isType0 = CommonUtil.isMember("전자사업부");

    Map<String, Object> parameter = new HashMap<String, Object>();
    // 완료여부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ISSUECOMPLETE");
    List<Map<String, Object>> issueCompleteNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 긴급도
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ISSUEURGENCY");
    List<Map<String, Object>> issueUrgencyNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // 중요도
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "ISSUEIMPORTANCE");
    List<Map<String, Object>> issueImportanceNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    //사업부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONNUMBER");
    List<Map<String, Object>> divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    
    String viewtype = request.getParameter("viewtype");
    
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
<!-- <script src="/plm/portal/js/treegrid/Grid_KET.js"></script> -->
<script src="/plm/portal/js/treegrid/Grid_KET_S.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/common.js"></script>
<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/Calendar.js"></script>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/top_include.jsp"%>
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
//<![CDATA[

    function viewProject(oid){
        openWindow('/plm/jsp/project/ProjectViewFrm.jsp?oid='+oid+"&popup=popup", '',1150,800);
    }

    function viewIssue(v) {
        var url = "/plm/jsp/project/projectIssueView.jsp?oid="+v;
        openOtherName(url,"","750","700","status=no,scrollbars=yes,resizable=no");
    }

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    function search() {

//         showProcessing();     // 진행중 Bar 표시
//         disabledAllBtn();     // 버튼 비활성화

//         document.frm.action = "/plm/servlet/e3ps/IssueServlet?command=search";
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

           D.Data.Url = "/plm/servlet/e3ps/IssueServlet"; 
           D.Data.Params = decodeURIComponent($('[name=' + formName + ']').serialize());
           D.Data.Param.command = "searchData";

           D.Page.Url = "/plm/servlet/e3ps/IssueServlet";
           D.Page.Params =  D.Data.Params;
           D.Page.Param.command = "searchPage";

           Grids[idx].Reload(D);

           var S = document.getElementById("Status"+idx);
           if(S) S.innerHTML="Loading";

        }catch(e){
            alert(e.message);
        }
    }


    function excelDown(){
        document.frm.action = "/plm/servlet/e3ps/IssueServlet?command=excelDown";
        document.frm.submit();
    }

    function clearAll(){
        $("#projectNo").val("");
        $("#category").multiselect("uncheckAll");
        $("#projectName").val("");
        onDeleteMember();
        $("#tempmanager").val("");
        $("#manager").val("");
        $("#subject").val("");
        $("#tempowner").val("");
        $("#owner").val("");
        $("#startCreateDate").val("");
        $("#endCreateDate").val("");
        $("#type").multiselect("uncheckAll");
        $("#urgency").multiselect("uncheckAll");
        $("#importance").multiselect("uncheckAll");
        $("#dType1").multiselect("dType1");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    // ==  [Start] 사람 검색  == //
    function deleteRoleMember(rname) {
        document.getElementById("temp" + rname).value = "";
        document.getElementById(rname).value = "";
    }
    
    var memberId = "";

    function addRoleMember(rname) {
    	memberId = rname;
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=acceptMember";
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=800,height=600";
        
        getOpenWindow2(url,rname, 800, 600, opts);
    }

    function acceptMember(arrObj) {
    	if ( typeof arrObj == "undefined" || arrObj == null ) {
            return;
        }
    	var isAppend = 'N';
    	var rname = memberId;
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
        if ( isAppend == "Y" ) {
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

        document.getElementById(rname).value= tmpId.join(", ");
        document.getElementById("temp" + rname).value = tmpName.join(", ");
    }
    // ==  [End] 사람 검색  == //

    // ==  [Start] 부서 검색  == //
    function addDepartmentInfo(objValue) {
    	
    	var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?type=all&mode=s&invokeMethod=deptCallBack";
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, 430, 465, opts);
    }
    
    function deptCallBack(attacheDept){

        document.frm.orgName.value = attacheDept[0].NAME;
        document.frm.orgOid.value = attacheDept[0].OID;
    }

    function orgName(req) {
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
        document.frm.orgName.value = decodeURIComponent(l_name[0].text);
        document.frm.orgOid.value = decodeURIComponent(l_oid[0].text);
    }

    function onDeleteMember(){
        document.frm.orgName.value = "";
        document.frm.orgOid.value = "";
    }
    // ==  [End] 부서 검색  == //

    //Jquery
    $(document).ready(function(){
    	// 담당부서 
    	SuggestUtil.bind('DEPARTMENT', 'orgName', 'orgOid');
    	// 담당자
    	SuggestUtil.bind('USER', 'tempmanager', 'manager');
    	// 제기자 
    	SuggestUtil.bind('USER', 'tempowner', 'owner');
    	
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        $("#category").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#type").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#urgency").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });

        $("#importance").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        $("#dType1").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
        
        
        CalendarUtil.dateInputFormat('startCreateDate');
        CalendarUtil.dateInputFormat('endCreateDate');
        
        var viewtype = $('[name=viewtype').val();
        if(viewtype == 'INCOMPLETE_ISSUE'){
        	$("#category").val("0");
            $("#category").multiselect('refresh');
            
            $("#type").val("CUSTOMER");
            $("#type").multiselect('refresh');
            
            $('[name=startCreateDate').val('');
            $('[name=endCreateDate').val('');
        } 
        Grids.OnRenderFinish = function(){
            
            if($('[name=viewtype').val() == 'INCOMPLETE_ISSUE'){
                $('[name=viewtype').val('');
                loadEjsGrid();
            }
        }
        
        treeGridResize("#ProjectIssueTotalListGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    });
    
//]]>
</script>

</head>

<body class="body-space">
<form name="frm" method="POST" >

<!-- hidden begin -->
<input type="hidden" name="perPage" value="<%=pagingSize %>">
<input type="hidden" name="viewtype" value="<%=viewtype %>">

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
                    <td class="font_01">전체 CFT요청 목록</td>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03108") %><%--프로젝트관리--%>
                        <img src="/plm/portal/images/icon_navi.gif">전체 CFT요청
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
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <input type="text" id="projectNo" name="projectNo" class="txt_field"  style="width:98%;" value="<%=searchCondition.getString("projectNo") %>">
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "02176") %><%--완료여부--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <select id="category" name="category" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                String[] category = searchCondition.getStringArray("category");

                for ( int i=0; i<issueCompleteNumCode.size(); i++ ) {
                %>
                <option value="<%=issueCompleteNumCode.get(i).get("code") %>" <%=KETParamMapUtil.contains(category, issueCompleteNumCode.get(i).get("code") ) ? " selected" : "" %>><%=issueCompleteNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="projectName" name="projectName" class="txt_field" style="width:98%;" value="<%=searchCondition.getString("projectName") %>">
            </td>
            
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL0">
                <select id="dType1" name="dType1" class="fm_jmp" style="width: 160px;" multiple="multiple">
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
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01201") %><%--담당부서--%></td>
            <td class="tdwhiteL">
                <input type="text" id="orgName" name='orgName'  class="txt_field" style="width:80%;" value="<%=searchCondition.getString("orgName") %>">
                <input type="hidden" id="orgOid" name="orgOid" value="<%=searchCondition.getString("orgOid") %>">
                <a href="javascript:addDepartmentInfo('orgName');"><img src="/plm/portal/images/icon_5.png"></a>
                <a href="javascript:onDeleteMember();"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="tempmanager" name="tempmanager"  class="txt_field"  style="width:80%" value="<%=searchCondition.getString("tempmanager") %>">
                <input type="hidden" id="manager" name="manager" value="<%=searchCondition.getString("manager") %>">
                <a href="#" onClick="javascript:addRoleMember('manager');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="#" onClick="javascript:deleteRoleMember('manager');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%></td>
            <td class="tdwhiteL">
                <input type="text" id="subject" name="subject" class="txt_field"  style="width:80%;" value="<%=searchCondition.getString("subject") %>">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02523") %><%--제기자--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="tempowner" name="tempowner"  class="txt_field"  style="width:80%;" value="<%=searchCondition.getString("tempowner") %>">
                <input type="hidden" id="owner" name="owner" value="<%=searchCondition.getString("owner") %>">
                <a href="#" onClick="javascript:addRoleMember('owner');"><img src="/plm/portal/images/icon_user.gif"></a>
                <a href="#" onClick="javascript:deleteRoleMember('owner');"><img src="/plm/portal/images/icon_delete.gif"></a>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02521") %><%--제기일--%></td>
            <td class="tdwhiteL">
                <input type="text" id="startCreateDate" name="startCreateDate" class="txt_field" style="width:70px;" value="<%=searchCondition.getString("startCreateDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('startCreateDate');" style="cursor: hand;">
                &nbsp;~&nbsp;
                <input type="text" id="endCreateDate" name="endCreateDate" class="txt_field"  style="width:70px;" value="<%=searchCondition.getString("endCreateDate") %>">
                &nbsp;<img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endCreateDate');" style="cursor: hand;">
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
            <td class="tdwhiteL0">
                <select id="type" name="type" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                IssueType[] issueTypeList = IssueType.getIssueTypeSet();
                String[] type = searchCondition.getStringArray("type");

                for ( int i=0; i<issueTypeList.length; i++ ) {
                    IssueType it = issueTypeList[i].toIssueType(issueTypeList[i].getDisplay(Locale.ENGLISH) );
                    if( !issueTypeList[i].isSelectable() ) continue;
                %>
                <option value="<%=it%>" <%=(KETParamMapUtil.contains(type, issueTypeList[i].getDisplay(Locale.ENGLISH)) ) ? " selected" : "" %>><%=issueTypeList[i].getDisplay(messageService.getLocale())%></option>
                <%
                }
                %>
                </select>
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01159") %><%--긴급여부--%></td>
            <td class="tdwhiteL">
                <select id="urgency" name="urgency" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                String[] urgency = searchCondition.getStringArray("urgency");

                for ( int i=0; i<issueUrgencyNumCode.size(); i++ ) {
                %>
                <option value="<%=issueUrgencyNumCode.get(i).get("code") %>" <%=KETParamMapUtil.contains(urgency, issueUrgencyNumCode.get(i).get("code") ) ? " selected" : "" %>><%=issueUrgencyNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02693") %><%--중요도--%></td>
            <td class="tdwhiteL0">
                <select id="importance" name="importance" class="fm_jmp" style="width:270px;" multiple="multiple">
                <%
                String[] importance = searchCondition.getStringArray("importance");

                for ( int i=0; i<issueImportanceNumCode.size(); i++ ) {
                %>
                <option value="<%=issueImportanceNumCode.get(i).get("code") %>" <%=KETParamMapUtil.contains(importance, issueImportanceNumCode.get(i).get("code") ) ? " selected" : "" %>><%=issueImportanceNumCode.get(i).get("value")%></option>
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
                    <div id="listGrid" style="WIDTH:100%;HEIGHT:100%; min-height:200px;">
                        <bdo Debug="1" AlertError="0" 
                            Layout_Url="/plm/jsp/project/ProjectIssueTotalListGridPagingLayout.jsp"
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
</table>
</form>
</body>
</html>