<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.ArrayList,
                        java.util.Vector"%>
<%@page import="wt.lifecycle.LifeCycleHelper,
                        wt.lifecycle.LifeCycleTemplate,
                        wt.lifecycle.State"%>

<%@page import = "wt.fc.QueryResult,
                  java.util.ArrayList,
                  java.util.Hashtable,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.ews.dao.PartnerDao,
                  java.net.URLEncoder,
                  java.net.URLDecoder,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.ParamUtil,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.PropertiesUtil,
                  e3ps.common.util.KETStringUtil,
                  e3ps.common.web.PageControl"%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="java.util.Hashtable" scope="request"/>
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

//EJS TreeGrid Start
response.addHeader("Cache-Control","max-age=1, must-revalidate");

String pagingSize = null;

if( searchCondition != null && !searchCondition.isEmpty() ) {

 pagingSize = searchCondition.get("perPage").toString();
}

if ( pagingSize == null || pagingSize.trim().length() == 0 ) {

 pagingSize = "100";

}

  Hashtable condition = (Hashtable)request.getAttribute("condition");
    ArrayList list = (ArrayList)request.getAttribute("list");
    String mode =  ParamUtil.getParameter(request, "mode");
    String method =  ParamUtil.getParameter(request, "method");
    String modal = request.getParameter("modal");

    if( (modal == null) || (modal.trim().length() == 0)){
        modal = "N";
    }

    if( condition != null && !condition.isEmpty() ){
        mode = (String)condition.get("mode");
        method = (String)condition.get("method");
        modal = (String)condition.get("modal");
    }

    ArrayList partnerCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HpartnerType")), "," );     // 진행상태
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "03217") %><%--협력업체 검색--%></title>
<base target="_self" />
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- EJS TreeGrid Start -->
<script src="../../portal/js/treegrid/GridE.js"></script>
<script src="../../portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script language="javascript" src="/plm/jsp/ews/EWSCommon.js"></script>

<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/SearchEcoPopup.js"></script>

<%@include file="/jsp/common/processing.html"%>
<%@include file="/jsp/common/multicombo.jsp"%>

<style type="text/css">
body {
	margin-left: 10px;
	margin-top: 0px;
	margin-right: 0px;
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
}

input {
	vertical-align: middle;
	line-height: 22px;
}

.nameCut {
	width: 165;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.typeCut {
	width: 100;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.addrCut {
	width: 195;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>

<script language='javascript'>
<!--

  function hideProcessing() {}

    function allcheck() {
        var isCheck;
        if (document.getElementById("partnerTable").rows.length == 1){
            return;
        }
        if (document.getElementById("partnerTable").rows.length > 1){
            isCheck = document.forms[0].allCheck.checked;
        }

        var partnerCheck = document.forms[0].partnerCheck;

        if (isCheck) {
            for (var inx = 0; inx < partnerCheck.length; inx++) {
                partnerCheck[inx].checked = true;
            }
        }else {
            for (var inx = 0; inx < partnerCheck.length; inx++) {
                partnerCheck[inx].checked = false;
            }
        }
    }

    //선택된 항목이 있는지 확인
    function isCheckedCheckBox() {
        form = document.forms[0];
        if(form.partnerCheck == null) {
            return;
        }

        len = form.partnerCheck.length;
        if(len) {
            for(var i = 0; i < len;i++) {
                if(form.partnerCheck[i].checked == true) {
                    return true;
                }
            }
        } else {
            if(form.partnerCheck.checked == true) {
                return true;
            }
        }
        return false;

    }



    //필드 값 초기화
  function deleteValueAll(){
      document.SearchPartner.partnerName.value = "";
      $("#partnerType").multiselect("uncheckAll");
  }



    function search(){
        document.forms[0].HpartnerType.value  = $("#partnerType").val();

        document.forms[0].action = '/plm/servlet/e3ps/PartnerServlet';
        document.forms[0].submit();
    }

    //페이지 조회
    function gotoPage(pageno){
        document.forms[0].page.value = pageno;
        search();
    }

    //선택된 항목 반환
    function checkList() {

        form = document.forms[0];

        var arr = new Array();
        var subarr = new Array();//0:code, 1:name

        if(!isCheckedCheckBox()) {
            return arr;
        }

        len = form.partnerCheck.length;

        var idx = 0;
        if(len) {
            for(var i = 0; i < len; i++) {
                if(form.partnerCheck[i].checked == true) {
                    subarr = new Array();
                    subarr[0] = form.partnerCheck[i].pcode;//code
                    subarr[1] = form.partnerCheck[i].pname;//name
                    arr[idx++] = subarr;
                }
            }
        } else {
            if(form.partnerCheck.checked == true) {
                subarr = new Array();
                subarr[0] = form.partnerCheck.pcode;//code
                subarr[1] = form.partnerCheck.pname;//name
                arr[idx++] = subarr;
            }
        }
        return arr;
    }



    function doSelect() {

        var G = Grids[0];
        var arr = new Array();
        var subarr = new Array();//0:code, 1:name
        var idx = 0;

        // PLM 1차 개선 프로젝트
        // 06-20 남현승
        // 체크된 값을 가져온다.
        if( G != null ){

            var R = G.GetSelRows();

            if( R.length == 0 ){
                alert("<%=messageService.getString("e3ps.message.ket_message", "02912") %><%--코드를 선택하십시오--%>");
                return;
            }

            if(R.length > 1){
                  // mode 가 멀티일경우 적용
                  for(var i=0,del=0;i<R.length;i++){
                       subarr = new Array();
                        subarr[0] = R[i].PartnerNo;//code
                        subarr[1] = R[i].PartnerName;//name
                        arr[idx++] = subarr;
                  }
            }else{
                // mode 가 싱글일 경우 적용
                subarr = new Array();
                subarr[0] = R[0].PartnerNo;//code
                subarr[1] = R[0].PartnerName;//name
                arr[idx++] = subarr;
            }
        }

        if( document.forms[0].modal.value == 'N' ){
            if(opener) {
                if(opener.<%=method%>) {
                    opener.<%=method%>(arr);
                }
            }
            window.close();
        }else{
            window.returnValue = arr;
            window.close();
        }

  }

  // 초기값 셋팅
    function initPage(){
<% if( condition != null && !condition.isEmpty() ){ %>
       document.forms[0].partnerName.value = '<%=condition.get("partnerName")%>';
            for(var inx=0; inx< document.forms[0].partnerType.options.length ; inx++){
                if( document.forms[0].partnerType.options[inx].value == '<%=condition.get("partnerType")%>'){
                    document.forms[0].partnerType.options[inx].selected = true;
                }
            }
<% } %>
    }

    //
    function checkRow(row) {
        alert(row.PartnerName);
    }

//-->
</script>
<script type="text/javascript">
<!--
$(document).ready(function(){

    $("#partnerType").multiselect({
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    treeGridResize("#SelectPartnerPopupSearchGrid","#listGrid");//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
});


$("form[name=SearchPartner]").keypress(function(e) {
    //Enter key

    if ( e.which == 13 ) {
        alert("dd");
          search();
        return false;
    }
});


-->
</script>

</head>
<body onload="initPage();">
    <form name="SearchPartner" method="post">
        <!-- hidden begin -->
        <input type='hidden' name='mode' value=<%=mode%>> <input type='hidden' name='method' value=<%=method%>> <input
            type="hidden" name="modal" value='<%=modal%>'> <input type="hidden" name="page" value=""> <input type="hidden"
            name="HpartnerType" value=""> <input type="hidden" name="totalCount" value="0">
        <!-- hidden end -->
        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td background="../../portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0"
                                    cellspacing="0">
                                    <tr>
                                        <td width="7"></td>
                                        <td width="20"><img src="../../portal/images/icon_3.png"></td>
                                        <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "03217")%><%--협력업체 검색--%></td>
                                    </tr>
                                </table></td>
                            <td width="136" align="right"><img src="../../portal/images/logo_popup.png"></td>
                        </tr>
                    </table></td>
            </tr>
            <tr>
                <td valign="top">
                    <table width="740px" height="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="10px">&nbsp;</td>
                            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10px"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:deleteValueAll();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819")%><%--초기화--%></a></td>
                                                                <td width="10px"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10px"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:doSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226")%><%--확인--%></a></td>
                                                                <td width="10px"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td width="5px">&nbsp;</td>
                                                    <td><table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10px"><img src="../../portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a
                                                                    href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705")%><%--검색--%></a></td>
                                                                <td width="10px"><img src="../../portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table></td>
                                                    <td>&nbsp;</td>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0">
                                                            <tr>
                                                                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                                    href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></td>
                                                                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="tab_btm2"></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td width="100px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03216")%><%--협력사명--%></td>
                                        <td width="260px" class="tdwhiteL"><input type="text" name="partnerName" class="txt_field"
                                            value="<%=StringUtil.checkNull((String) searchCondition.get("partnerName"))%>"
                                            style="width: 250px"></td>

                                        <td width="100px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02662")%><%--종목--%></td>
                                        <%
                                            PartnerDao partnerDao = new PartnerDao();
                                            ArrayList typeList = partnerDao.ViewPartnerType();
                                            String partnerType = null;
                                        %>
                                        <td width="260px" class="tdwhiteL0"><select name="partnerType" id="partnerType" class='fm_jmp'
                                            style='width: 250px;' multiple="multiple">

                                                <%
                                                    if (typeList != null) {
                                                		for (int inx = 0; inx < typeList.size(); inx++) {
                                                		    partnerType = (String) typeList.get(inx);
                                                		    if (partnerType != null) {
                                                %>
                                                <option value="<%=partnerType%>"
                                                    <%=partnerCondList.contains(partnerType) ? " selected" : ""%>>
                                                    <%=partnerType%>
                                                </option>
                                                <%
                                                    }
                                                		}
                                                    }
                                                %>
                                        </select></td>
                                    </tr>
                                </table>
                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                    <tr>
                                        <td class="space5"></td>
                                    </tr>
                                </table>
                                <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%">
                                    <bdo Debug="1" AlertError="0" Layout_Url="/plm/jsp/ews/SelectPartnerPopupGridLayout.jsp"
                                        Layout_Param_Mode="<%=mode%>" Data_Url="/plm/jsp/common/searchGridData.jsp" Data_Method="POST"
                                        Data_Param_Result="<%=tgData%>" Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp"
                                        Export_Data="TGData" Export_Param_File=""></bdo>
                                </div></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
