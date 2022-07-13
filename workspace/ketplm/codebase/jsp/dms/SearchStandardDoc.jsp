<%@page import="e3ps.common.util.CommonUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList,
                java.util.Hashtable,
                java.util.List,
                java.util.Map,
                java.util.HashMap"%>

<%@page import="e3ps.common.util.PropertiesUtil,
                e3ps.common.util.StringUtil,
                e3ps.common.util.KETStringUtil,
                e3ps.common.code.NumberCodeHelper"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>
<jsp:useBean id="popup" class="java.lang.String" scope="request"/>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String pagingSizeDefault = PropertiesUtil.getSearchPagingSizeDefault();
    String pagingSize = null;

    if( searchCondition != null && !searchCondition.isEmpty() ) {
	   
	    Object obj = searchCondition.get("perPage");
	    pagingSize =  (obj == null)?"10":obj.toString();
    }

    if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

        pagingSize = pagingSizeDefault;
    }
    // EJS TreeGrid End

    Map<String, Object> parameter = new HashMap<String, Object>();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONFLAG");
    List<Map<String, Object>> divisionNumCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

    ArrayList divisionCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("divisionList")), "," );
    
    String popupType = request.getParameter("popup");
    String invokeMethod = StringUtil.checkNull(request.getParameter("invokeMethod"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>PLM Portal</title>
<base target="_self" />
<link rel="stylesheet" type="text/css" href="/plm/portal/css/e3ps.css" />

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script src="/plm/portal/js/org.js"></script>
<script src="/plm/portal/js/common.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
//<![CDATA[
    function hideProcessing() {}

    //필드 값 초기화
    function deleteValueAll(){
        $("#divisionCode").multiselect("uncheckAll");
        $("#deptCode").val("");
        $("#docName").val("");
        $("#docDesc").val("");

        // 결과내재검색 체크해제
        $("input:checkbox[id=searchInSearch]").attr("checked", false);
    }

    function perPage(v) {
        document.frm.perPage.value = v;

        search();
    }

    // 검색
    function search(){
        showProcessing();

        document.frm.divisionList.value  = $("#divisionCode").val();

        document.frm.action =  '/plm/servlet/e3ps/StandardDocServlet';
        document.frm.submit();
    }
    
    /* 2014.07.23  선택된 표준양식 값 부모창 함수 전달 및 호출*/
    function pickStandardForm(){
    	var temp = Grids[0];
        var R = temp.GetSelRows();
        var docOid = R[0].DocOid;
        var docName = R[0].DocName;
       // alert(docOid+"  "+docName);
        
       <%  if ( invokeMethod.length() == 0 ) {  %>
            //modal dialog
            selectModalDialog(docOid, docName);
        <%  } else {  %>
            //open window
            selectOpenWindow(docOid, docName);
        <%  }  %>
    }
    
    function selectModalDialog(docOid, docName) {
    	var dialog = window.dialogArguments;
    	dialog.setStandardForm(docOid, docName);
        self.close();
    }
    
    <%  if(invokeMethod.length() > 0) {  %>

    function selectOpenWindow(docOid, docName) {
    	
        //...이하 원하는 스크립트를 만들어서 작업...
        if(opener) {
            if(opener.<%=invokeMethod%>) {
                opener.<%=invokeMethod%>(docOid, docName);
            }
        }else if(parent.opener) {
            if(parent.opener.<%=invokeMethod%>) {
                parent.opener.<%=invokeMethod%>(docOid, docName);
            }
        }
        self.close();
    }

    <%  }  %>
    
    /* 2014.07.23 표준양식 등록 팝업 추가*/
    function standardFormRegister(){
    	var url = "/plm/jsp/dms/CreateStandardDoc.jsp";
    	openOtherName(url, "register", "1000", "400", "status=no,scrollbars=yes,resizable=no");
    }
    
    function searchPopup(url){
    	openOtherName(url, "register", "1000", "400", "status=no,scrollbars=yes,resizable=no");
    }
    
    function historySearch(oid){
        var url = "/plm/jsp/dms/CreateStandardHistorySearch.jsp?oid="+oid;
        window.open(url, "_blank","width=500, height=300,status=no,scrollbars=yes,resizable=no");
    }
    
    // Jquery
    $(document).ready(function(){
    	
    	treeGridResize("#SearchStandardDocGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
        // Enter 검색
        $("form[name=frm]").keypress(function(e) {
            if ( e.which == 13 ) {
                search();
                return false;
            }
        });

        // Multi Combo 생성
        $("#divisionCode").multiselect({
            noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
            checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
            uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
        });
    });
//]]>
</script>
</head>

<% if("yes".equals(popupType)){ %>
    <body class="popup-background02 popup-space">
<%} else{%>
<body class="body-space">
<%} %>
<form method="post" name="frm">
<c:set var="popup" value="<%=popupType%>" />
<!-- hidden begin -->
<input type="hidden" name="cmd" value="search" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<input type="hidden" name="divisionList" value="" />
<input type="hidden" name="popup" value="<%=popupType%>" />
<input type="hidden" name="invokeMethod" value="<%=invokeMethod%>" />
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
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03009") %><%--표준양식 검색--%></td>
                    <% if(!"yes".equals(popupType)){ %>
                    <td align="right">
                        <img src="/plm/portal/images/icon_navi.gif">Home
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "01406") %><%--문서관리--%>
                        <img src="/plm/portal/images/icon_navi.gif"><%=messageService.getString("e3ps.message.ket_message", "03009") %><%--표준양식 검색--%>
                    </td>
                    <%} %>
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
                    <%-- <td>
                        <input type="checkbox" id="searchInSearch" name="searchInSearch" value="searchInSearch" <%if ( searchCondition.getString("searchInSearch").equals(("searchInSearch")) ){%> checked <%} %>/><%=messageService.getString("e3ps.message.ket_message", "00749") %>결과 내 재검색
                    </td> --%>
                    <c:if test="${popup ne 'yes'}">
                    <%  if(CommonUtil.isAdmin()) {  %> 
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:standardFormRegister();" class="btn_blue">등록<%--등록--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <%} %>
                    </c:if>
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:deleteValueAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    <c:if test="${popup eq 'yes'}"> 
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:pickStandardForm();" class="btn_blue">선택<%--선택--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    </c:if>
                    <td style="width: 5px;">&nbsp;</td>
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
                    <c:if test="${popup eq 'yes'}"> 
                    <td style="width: 5px;">&nbsp;</td>
                    <td>
                        <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:window.close();" class="btn_blue">닫기<%--닫기--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
                    </c:if>
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
        <table style="width: 100%;">
        <tr>
            <td class="tdblueL"  style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "01662") %><%--사업부--%></td>
            <td class="tdwhiteL" style="width: 270px;">
                <select id="divisionCode" name="divisionCode" class="fm_jmp" style="width: 270px;" multiple="multiple">
                <option value="0" <%=divisionCondList.contains("0") ? " selected" : "" %>> <%=messageService.getString("e3ps.message.ket_message", "00895") %><%--공통--%> </option>
                <%
                for ( int i=0; i<divisionNumCode.size(); i++ ) {
                %>
                <option value="<%=divisionNumCode.get(i).get("code") %>" <%=divisionCondList.contains( divisionNumCode.get(i).get("code") ) ? " selected" : "" %>><%=divisionNumCode.get(i).get("value")%></option>
                <%
                }
                %>
                </select>
            </td>
            <td class="tdblueL"   style="width: 120px;"><%=messageService.getString("e3ps.message.ket_message", "00947") %><%--관리부서--%></td>
            <td class="tdwhiteL0" style="width: 270px;">
                <input type="text" id="deptCode" name="deptCode" class="txt_field" style="width: 98%;" value="<%=StringUtil.checkNull( (String)searchCondition.get("deptCode") ) %>">
            </td>
        </tr>
        <tr>
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01415") %><%--문서명--%></td>
            <td class="tdwhiteL">
                <input type="text" id="docName" name="docName" class="txt_field" style="width: 98%;" value="<%=StringUtil.checkNull( (String)searchCondition.get("docName") ) %>">
            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02102") %><%--양식설명--%></td>
            <td class="tdwhiteL0">
                <input type="text" id="docDesc" name="docDesc" class="txt_field" style="width: 98%;" value="<%=StringUtil.checkNull( (String)searchCondition.get("docDesc") ) %>">
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
                            Layout_Url="/plm/jsp/dms/SearchStandardDocGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Popup="${popup}"
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
