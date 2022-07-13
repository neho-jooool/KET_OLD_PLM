<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.util.ArrayList,
                        java.util.Vector"%>
<%@page import="wt.lifecycle.LifeCycleHelper,
                        wt.lifecycle.LifeCycleTemplate,
                        wt.lifecycle.State"%>

<%@page import = "wt.fc.QueryResult,
                  java.util.Map,
                  java.util.List,
                  java.util.HashMap,
                  java.util.ArrayList,
                  java.util.Hashtable,
                  java.util.Calendar,
                  java.text.SimpleDateFormat,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.common.util.*,
                  e3ps.common.code.NumberCode,
                  e3ps.common.code.NumberCodeHelper,
                  e3ps.common.util.StringUtil,
                  e3ps.common.util.CommonUtil,
                  e3ps.common.util.PropertiesUtil,
                  e3ps.common.util.KETStringUtil,
                  e3ps.common.web.PageControl"%>


<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%

     //EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");


String prodMoldCls = StringUtil.checkNull(request.getParameter("prodMoldCls"));
if("".equals(prodMoldCls)) {
    prodMoldCls = "";//(1:Prod 2:Mold)
}

String mode = StringUtil.checkNull(request.getParameter("mode"));
if("".equals(mode)) {
    mode = "single";//(single:한건선택 multi:다건선택)
}
String titleChk = "선택";
if("multi".equals(mode)) {
    titleChk = "<input type=\"checkbox\" name=\"chkAllRelEcr\" onclick=\"javascript:checkAll('forms[0]', 'oid', 'chkAllRelEcr');\">";
}

String sortColumn = StringUtil.checkNull(request.getParameter("sortColumn"));
if("".equals(sortColumn)) {
    sortColumn = "1 ASC";
}

String statusAll = StringUtil.checkNull(request.getParameter("statusAll"));

//[START] [PLM System 1차 고도화] 처음 로딩시 검색조건 '작성일자'를 기본값으로 세팅, 2014-06-13, 김태현
if ( searchCondition.isEmpty() ) {
    Calendar cal = Calendar.getInstance();
    searchCondition.put("createEndDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
    cal.add(Calendar.MONTH, -3);
    searchCondition.put("createStartDate", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
}
//[START] [PLM System 1차 고도화] 처음 로딩시 검색조건 '작성일자'를 기본값으로 세팅, 2014-06-13, 김태현

Map<String, Object> parameter = new HashMap<String, Object>();
    // 사업부
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DIVISIONFLAG");
    List<Map<String, Object>> divisionCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    //단계구분 -  개발/양산구분
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "DEVYN");
    List<Map<String, Object>>  devynCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    // ECR 구분 - 신규부품유형
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "NEWPARTTYPE");
    List<Map<String, Object>> newPartTypeCode = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    // 금형변경
    parameter = new HashMap<String, Object>();
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "MOLDCHANGE");
    List<Map<String, Object>> moldChangeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

    // 원가변동
    parameter = new HashMap<String, Object>();
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "COSTCHANGE");
    List<Map<String, Object>> costChangeList = NumberCodeHelper.manager.getNumberCodeList(parameter);

 // 설계변경사유
    parameter = new HashMap<String, Object>();
    parameter.clear();
    parameter.put("locale",   messageService.getLocale());
    parameter.put("codeType", "PRODCHAGEREASON");
    List<Map<String, Object>> prodChangeReasonList = NumberCodeHelper.manager.getNumberCodeList(parameter);
    
    ArrayList divisionCondList = KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdivisionFlag")), "," );               //  사업자 구분
    ArrayList prodMoldClsCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HprodMoldCls")), "," );      //  ECR 구분
    ArrayList devYnCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HdevYn")), "," );                      //  단계구분
    ArrayList stateCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HsancStateFlag")), "," );               //  상태

    ArrayList moldChangeCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HmoldChange")), "," );               //  상태
    ArrayList costChangeCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HcostChange")), "," );               //  상태
    ArrayList prodChangeReasonCondList =  KETStringUtil.getToken( KETStringUtil.nullFilter((String)searchCondition.get("HprodChangeReason")), "," );               //  상태

    String divisionFlag = "";
    if ( CommonUtil.isMember("전자사업부") ) {
        divisionFlag = "E";
    }
    else if ( CommonUtil.isMember("자동차사업부") ) {
        divisionFlag = "C";
    }
    else if ( CommonUtil.isKETSUser() ) {
        divisionFlag = "K";
    }

    if ( divisionCondList.size() == 0 ) {
        if ( CommonUtil.isMember("전자사업부") ) {
            divisionCondList.add(0, "E");
        }
        else if ( CommonUtil.isMember("자동차사업부") ) {
            divisionCondList.add(0, "C");
        }
        else if ( CommonUtil.isKETSUser() ) {
            divisionCondList.add(0, "K");
        }
    }

%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><%=messageService.getString("e3ps.message.ket_message", "00203") %><%--ECR 검색 팝업--%></title>
<base target="_self">

<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">
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
table th, table td {padding: 0}

img {
    vertical-align: middle;
}

input {
    vertical-align:middle;line-height:22px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }

.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.nameCut{
width:175;
overflow:hidden;
text-overflow:ellipsis;
white-space:nowrap;
}
-->
</style>

<%@include file="/jsp/common/processing.html" %>
<%@include file="/jsp/project/template/ajaxProgress.html"%>
<%@include file="/jsp/common/multicombo.jsp" %>
<script language=JavaScript src="/plm/portal/js/common.js"></script>
<script language=JavaScript src="/plm/jsp/ecm/EcmUtil.js"></script>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language="javascript" src="/plm/portal/js/util.js"></script>
<script language="javascript" src="/plm/portal/js/Calendar.js"></script>
<script src="/plm/portal/js/jquery/jquery-maskedinput-1.3.1.min.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/commonUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/js/shared/calendarUtil.js"></script>

<!-- EJS TreeGrid Start -->
<script src="../../portal/js/treegrid/GridE.js"></script>
<script src="../../portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<script language=JavaScript src="/plm/jsp/ecm/SearchEcrPopup.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	SuggestUtil.bind('ECRNO', 'ecrId');
    
	//자동완성, 달력 bind 시작
    CalendarUtil.dateInputFormat('createStartDate', 'createEndDate');

    SuggestUtil.bind('DEPARTMENT', 'orgName', 'orgOid');
    SuggestUtil.bind('PARTNO', 'partNo');
    SuggestUtil.bind('ECRNO', 'ecrId');
    SuggestUtil.bind('USER', 'creatorName', 'creatorOid');
    SuggestUtil.bind('CARTYPE', 'carTypeName', 'carTypeCode');
    
    // Project 구분 radio click event
    $("[name=searchPjtType]").click(function() {
        CommonUtil.deleteValue('searchPjtNoTxt', 'projectNo', 'projectOid');
        
        if("제품" == $(this).val()){
            SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', 'projectOid');
        }
        else if("금형" == $(this).val()){
            SuggestUtil.bind('DIENO', 'projectNo', 'projectOid');
        }
    });
    
    var searchPjtType = '<%=StringUtil.checkNull( (String)searchCondition.get("searchPjtType") )%>';
    
    $('input[name="searchPjtType"]').each(function(){
        if("제품" == $(this).val()){
           if(searchPjtType == $(this).val() || searchPjtType == ''){
               SuggestUtil.bind('PRODUCTPROJNO', 'projectNo', 'projectOid');
               $(this).attr('checked',true);
           }
        }
        else if("금형" == $(this).val()){
            if(searchPjtType == $(this).val()){
              SuggestUtil.bind('DIENO', 'projectNo', 'projectOid');
              $(this).attr('checked',true);
            }
        }
    });
    
    $(document).bind('keypress', function(e) {
        if (e.which == 13) {
            doSearch();
        }
    });
    
    
    <%
    if(CommonUtil.isKETSUser()) {
    %>
        $("#divisionFlag").multiselect({
            minWidth: 180,
            multiple: false,
            header: "",
            noneSelectedText: "",
            selectedList: 1
        });
    <%    
    }
    else {
    %>    
	    $("#divisionFlag").multiselect({
	        minWidth: 180,
	        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
	        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
	        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
	    });
    <%
    }
    %>
    
    
    $("#prodMoldCls").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    $("#devYn").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    $("#sancStateFlag").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });

    // 금형변경
    $("#moldChange").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
    
    // 원가변동
    $("#costChange").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });

    // 설계변경사유
    $("#prodChangeReason").multiselect({
        minWidth: 180,
        noneSelectedText: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
        checkAllText    : '<%=messageService.getString("e3ps.message.ket_message", "02498") %><%--전체선택--%>',
        uncheckAllText  : '<%=messageService.getString("e3ps.message.ket_message", "02500") %><%--전체해제--%>'
    });
 
})

<%--조회--%>
function doView(url) {
    
    var name = "ViewECR";
    if(url.indexOf('e3ps.ecm.entity.KETProdChangeRequest') > 0) {
        name += 'PROD';
    } else {
        name += 'MOLD';
    }
    var opt = launchCenter(1024, 768);
    opt += ", resizable=yes";
    var windowWin = window.open(url,name,opt);
    
    //windowWin.resizeTo(width,height);
    windowWin.focus();
    
}

function selectData(){
	var G = Grids[0];

    var arr = new Array();
    var subArr = new Array();
    var idx = 0;

    // PLM 1차 개선 프로젝트
    // 06-20 남현승
    // 체크된 값을 가져온다.
    if( G != null ){

        var R = G.GetSelRows();

        if( R.length == 0 ){
            alert("코드를 선택하십시오.");
            return;
        }
        
          // 다건일경우
          for(var i=0,del=0;i<R.length;i++){
                subArr = new Array();

                subArr[0] = R[i].Oid; // oid
                subArr[1] = R[i].EcrNo; // ecrNo
                subArr[2] = R[i].DeptName; // 작성부서
                subArr[3] = R[i].Creator; // 작성자
                subArr[4] = R[i].CompDate; // 승인일자
                subArr[5] = R[i].EcrName;    // ecr 제목
                subArr[6] = R[i].changeReason;  //설계변경사유
                arr[idx++] = subArr;
          }
    }
    parent.opener.setRelEcr(arr);
    parent.close();
}
</script>
<script language="javascript">
var mode = "<%=mode%>";

</script>
</head>
<body>
<form name="theForm">
<input type="hidden" name="cmd" value="popupEcrSearch">
<input type="hidden" name="prodMoldCls" value="<%=prodMoldCls%>">
<input type="hidden" name="sortColumn" value="<%=sortColumn%>">

<input type="hidden" name="param" value="parent.">
<input type="hidden" name="autoSearch" value="Y">
<input type="hidden" name="oid" value="">

<input type="hidden" name="HdivisionFlag" value="">
<input type="hidden" name="HprodMoldCls" value="">
<input type="hidden" name="HdevYn" value="">
<input type="hidden" name="HsancStateFlag" value="">

<input type="hidden" name="HmoldChange" value="">
<input type="hidden" name="HcostChange" value="">
<input type="hidden" name="HprodChangeReason" value="">
<% if("Y".equals(statusAll)) { %>
    <input type="hidden" name="sancStateFlag" value="">
<% }else{ %>
    <input type="hidden" name="sancStateFlag" value="APPROVED">
<% } %>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="50" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/plm/portal/images/logo_popupbg.png"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="7"></td>
                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "00202") %><%--ECR 검색--%></td>
              </tr>
            </table></td>
          <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td valign="top">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <!-- td width="10">&nbsp;</td -->
          <td valign="top">
              <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                <tr>
                  <td>&nbsp;</td>
                  <td align="right">
                      <table border="0" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doClear();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02819") %><%--초기화--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                          <td width="5">&nbsp;</td>
                          <td>
                              <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="10"><img src="../../portal/images/btn_1.gif"></td>
                                  <td class="btn_blue" background="../../portal/images/btn_bg1.gif"><a href="javascript:doSearch();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a></td>
                                  <td width="10"><img src="../../portal/images/btn_2.gif"></td>
                                </tr>
                              </table>
                            </td>
                          <td width="5">&nbsp;</td>
			              <td align="center"><table border="0" cellspacing="0" cellpadding="0">
			                <tr>
			                  <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                  <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:selectData();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
			                  <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                </tr>
			              </table></td>                            
                        </tr>
                      </table>
                  </td>
                </tr>
            </table>
            <table width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>
            <table width="100%">
              <tr>
                <td  class="tab_btm2"></td>
              </tr>
            </table>
            <table style="width: 100%;">
	        <tr>
	            <td class="tdblueL" style="width: 110px;"><%=messageService.getString("e3ps.message.ket_message", "00210") %><%--ECR구분--%></td>
	            <td class="tdwhiteL">
	                <%
	                if(prodMoldCls.equals("1") ){
	                %>
	                <select name="prodMoldCls" id="prodMoldCls" class="fm_jmp" multiple="multiple" disabled="disabled">
                    <option value="1"  selected="selected" ><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></option>
                    <option value="2"  ><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></option>
                    </select>
	                <%    
	                }
	                else if(prodMoldCls.equals("2") ){
                    %>
                    <select name="prodMoldCls" id="prodMoldCls" class="fm_jmp" multiple="multiple" disabled="disabled">
                    <option value="1"  ><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></option>
                    <option value="2"  selected="selected" ><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></option>
                    </select>
                    <%    
                    }
                    else {
                    %>
                    <select name="prodMoldCls" id="prodMoldCls" class="fm_jmp" multiple="multiple">
                    <option value="1"  <%=prodMoldClsCondList.contains("1") ? " selected" : "" %> ><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%></option>
                    <option value="2"  <%=prodMoldClsCondList.contains("2") ? " selected" : "" %> ><%=messageService.getString("e3ps.message.ket_message", "00997") %><%--금형--%></option>
                    </select>
                    <%    
                    }
                    %>
	                
	            </td>
	            <td class="tdblueL" style="width:110px;">ECR No</td>
	            <td class="tdwhiteL">
	                <input type="text" name="ecrId" class="txt_field" style="width:98%" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecrId") )%>" >
	            </td> 
	            <td class="tdblueL" style="width:110px;"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
	            <td class="tdwhiteL0">
	                <input type="text" name="ecrName" class="txt_field" style="width:98%" value="<%=StringUtil.checkNull( (String)searchCondition.get("ecrName") )%>"  id="textfield4">
	            </td>
	        </tr>
	        <tr>
	            <td class="tdblueL">Project No</td>
	            <td colspan="3" class="tdwhiteL">
	              <input type="radio" name="searchPjtType" value="제품"><%=messageService.getString("e3ps.message.ket_message", "02536")%><%--제품--%> 
	              <input type="radio" name="searchPjtType" value="금형"><%=messageService.getString("e3ps.message.ket_message", "00997")%><%--금형--%> 
	              <input type="text" name="projectNo" id="projectNo" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("projectNo") )%>" style="width:220px">
	              <input type="hidden" name="projectOid" id="projectOid" value="<%=StringUtil.checkNull( (String)searchCondition.get("projectOid") )%>">
	              <a href="javascript:checkAfterPopupProject('projectNo');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
	              <a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(2);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	            </td>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00395") %><%--Project Name--%></td>
	            <td class="tdwhiteL0">
	                <input type="text" name="projectName" class="txt_field"  value="<%=StringUtil.checkNull( (String)searchCondition.get("projectName") )%>"  style="width:98%" >
	            </td>
	        </tr>
	        <tr>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
	            <td class="tdwhiteL">
	              <input type="text" id="orgName" name="orgName" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("orgName") )%>" style="width:75%" >
	              <input type="hidden" id="orgOid" name="orgOid" value="<%=StringUtil.checkNull( (String)searchCondition.get("orgOid") )%>">
	              <a href="javascript:addDepartment();"><img src="/plm/portal/images/icon_5.png"></a>
	              <a href="javascript:delDepartment('orgOid','orgName');"><img src="/plm/portal/images/icon_delete.gif"></a>
	            </td>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
	            <td class="tdwhiteL">
	              <input type="text" name="creatorName" id="creatorName" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorName") )%>" style="width:75%">
	              <input type="hidden" id="creatorOid" name="creatorOid" value="<%=StringUtil.checkNull( (String)searchCondition.get("creatorOid") )%>">
	              <input type="hidden" id="creator" name="creator">
	              <a href="javascript:SearchUtil.selectOneUser('creatorOid','creatorName');"> <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
	              <a href="javascript:CommonUtil.deleteValue('creatorOid','creatorName');"> <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	            </td>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
	            <td class="tdwhiteL0">
	                <select name="sancStateFlag" id="sancStateFlag" class="fm_jmp" multiple="multiple">
	                   <option value="INWORK"  <%=stateCondList.contains("INWORK") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02441") %><%--작업중--%></option>
	                   <option value="APPROVEDFILING"  <%=stateCondList.contains("APPROVEDFILING") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04830") %><%--제기승인--%></option>
	                   <option value="CONSIDER"  <%=stateCondList.contains("CONSIDER") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04840") %><%--검토--%></option>
	                   <option value="UNDERREVIEW"  <%=stateCondList.contains("UNDERREVIEW") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "00732") %><%--검토중--%></option>
	                   <option value="APPROVED"  <%=stateCondList.contains("APPROVED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "04980") %><%--검토완료--%></option>
	                   <option value="REJECTED"  <%=stateCondList.contains("REJECTED") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "01468") %><%--반려됨--%></option>
	                   <!-- option value="REWORK"  <%=stateCondList.contains("REWORK") ? " selected" : "" %>><%=messageService.getString("e3ps.message.ket_message", "02451") %><%--재작업--%></option -->
	                </select>
	            </td>
	        </tr>     
	        <tr>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02429") %><%--작성일자--%></td>
	            <td colspan="3" class="tdwhiteL">
	               <input type="text" name="createStartDate" id="createStartDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createStartDate") )%>">
	               <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createStartDate');" style="cursor: hand;">
	                   &nbsp;~&nbsp;
	               <input type="text" name="createEndDate" id="createEndDate" class="txt_field"  style="width:70px" value="<%=StringUtil.checkNull( (String)searchCondition.get("createEndDate") )%>">
	               <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteValue('createEndDate');" style="cursor: hand;">
	            </td>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%></td>
	            <td class="tdwhiteL0">
	                <select name="devYn" id="devYn" class="fm_jmp" multiple="multiple">
	                <%
	                for ( int i=0; i<devynCode.size(); i++ ) {
	                %>
	                <option value="<%=devynCode.get(i).get("code") %>" <%=devYnCondList.contains( devynCode.get(i).get("code") ) ? " selected" : "" %>><%=devynCode.get(i).get("value")%></option>
	                <%
	                }
	                %>
	                </select>
	            </td>            
	        </tr>              
	        <tr>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품 번호--%></td>
	            <td class="tdwhiteL">
	                <input type="text" name="partNo" id="partNo" class="txt_field" value="<%=StringUtil.checkNull( (String)searchCondition.get("partNo") )%>" style="width:75%">
	                <input type="hidden" name="partOid" id="partOid">
	                <a href="javascript:showProcessing();SearchUtil.selectPart('selectPartAfterFunc');"><img src="/plm/portal/images/icon_5.png" border="0"></a>
	                <a href="#" onfocus="this.blur();" onClick="javascript:clearCondition(1);"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	            </td>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
	            <td class="tdwhiteL">
	                <input type="text" name="partName" class="txt_field"   value="<%=StringUtil.checkNull( (String)searchCondition.get("partName") )%>"  style="width:98%" >
	            </td>
	           <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04122") %><%--차종--%></td>
	           <td class="tdwhiteL0">
	               <input type="text" name="carTypeName" class="txt_field" style="width: 70%" value="<%=StringUtil.checkNull( (String)searchCondition.get("carTypeName") )%>"> 
	               <input type="hidden" name="carTypeCode">
	               <a href="javascript:SearchUtil.selectCarType('carTypeCode','carTypeName');">
	               <img src="/plm/portal/images/icon_5.png" border="0"></a> 
	               <a href="javascript:CommonUtil.deleteValue('carTypeCode','carTypeName');">
	               <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
	           </td>
	        </tr>
	        <tr>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04124") %><%--금형변경--%></td>
	          <td class="tdwhiteL">
	            <select name="moldChange" id="moldChange" class="fm_jmp" multiple="multiple">
	            <%
	            for ( int i=0; i < moldChangeList.size(); i++ ) {
	            %>
	            <option value="<%=moldChangeList.get(i).get("code") %>" <%=moldChangeCondList.contains( moldChangeList.get(i).get("code") ) ? " selected" : "" %>><%=moldChangeList.get(i).get("value")%></option>
	            <%
	            }
	            %>
	            </select>
	          </td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04125") %><%--원가변동--%></td>
	          <td class="tdwhiteL">
	            <select name="costChange" id="costChange" class="fm_jmp" multiple="multiple">
	            <%
	            for ( int i=0; i < costChangeList.size(); i++ ) {
	            %>
	            <option value="<%=costChangeList.get(i).get("code") %>" <%=costChangeCondList.contains( costChangeList.get(i).get("code") ) ? " selected" : "" %>><%=costChangeList.get(i).get("value")%></option>
	            <%
	            }
	            %>
	            </select>
	          </td>
	          <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01859") %><%--설계변경요청사유--%></td>
	          <td class="tdwhiteL0">
	            <select name="prodChangeReason" id="prodChangeReason" class="fm_jmp" multiple="multiple">
	            <%
	            for ( int i=0; i < prodChangeReasonList.size(); i++ ) {
	            %>
	            <option value="<%=prodChangeReasonList.get(i).get("code") %>" <%=prodChangeReasonCondList.contains( prodChangeReasonList.get(i).get("code") ) ? " selected" : "" %>><%=prodChangeReasonList.get(i).get("value")%></option>
	            <%
	            }
	            %>
	            </select>
	          </td>                       
	        </tr>   
	        <tr>
	            <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%></td>
	            <td class="tdwhiteL">
	                <select name="divisionFlag" id="divisionFlag" class="fm_jmp" multiple="multiple">
	                    <%
	                    String[] division = searchCondition.getStringArray("divisionFlag");
	                    for ( int i=0; i<divisionCode.size(); i++ ) {
	                        if(CommonUtil.isKETSUser() && !divisionCode.get(i).get("code").equals("K")) {
	                            // Do nothing..!!
	                        } else {   	                	
	                    %>
	                    <option value="<%=divisionCode.get(i).get("code") %>" <%=divisionCondList.contains( divisionCode.get(i).get("code") ) ? " selected" : "" %>><%=divisionCode.get(i).get("value")%></option>
                        <%
	                        }
	                    }
	                    %>
	                </select>
	            </td>
	            <td colspan="5" class="tdwhiteL0">&nbsp;</td>                       
	        </tr>
	              
	        </table>
            <table width="100%">
              <tr>
                <td class="space5"></td>
              </tr>
            </table>


                        <div style="WIDTH:100%;HEIGHT:100%">
                            <bdo Debug="1" AlertError="0"
                                Layout_Url="/plm/jsp/ecm/SearchEcrPartPopupGridLayout.jsp"
                                Layout_Param_Mode="<%=mode %>"
                                Data_Url="/plm/jsp/common/searchGridData.jsp"
                                Data_Method="POST"
                                Data_Param_Result="<%=tgData %>"
                                Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File=""
                            ></bdo>
                        </div>

      </table></td>
  </tr>
</table>
<!-- table width="470px">
  <tr>
    <td width="10">&nbsp;</td>
    <td height="30"><iframe src="/plm/portal/common/copyright_p.jsp" name="copyright" width="100%" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
  </tr>
</table -->
</form>
</body>
<iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</html>
